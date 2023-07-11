package org.example.ControlSystem;

import org.example.FurnaceControl.FurnaceControl;
import org.example.Signals.*;

public class ControlSystem {
    private final FurnaceControl furnaceControl = new FurnaceControl();
    private final RoomTemperatureSignal roomTemperatureSignal;
    private final DoorSignal doorSignal;
    private final DesiredTemperatureSignal desiredTemperatureSignal;
    private final FurnaceRunningSignal furnaceRunningSignal;
    private final FurnaceErrorSignal furnaceErrorSignal;
    private int doorCounter = 0;
    private static final float MIN_SAFE_TEMPERATURE = 6.0f;
    private static final float MAX_DEVIATION_ABOVE_TARGET = 1.0f;
    private static final float MAX_DEVIATION_BELOW_TARGET = 0.5f;
    private static final int DOOR_OPEN_MAX_COUNT = 120;
    private static final int THREAD_SLEEP_TIME = 3000;


    public ControlSystem(RoomTemperatureSignal roomTemperatureSignal, DoorSignal doorSignal, DesiredTemperatureSignal desiredTemperatureSignal, FurnaceRunningSignal furnaceRunningSignal, FurnaceErrorSignal furnaceErrorSignal) {
        this.roomTemperatureSignal = roomTemperatureSignal;
        this.doorSignal = doorSignal;
        this.desiredTemperatureSignal = desiredTemperatureSignal;
        this.furnaceRunningSignal = furnaceRunningSignal;
        this.furnaceErrorSignal = furnaceErrorSignal;
    }

    public void startSignalReaders() {
        new Thread(new SignalReaderTask(roomTemperatureSignal)).start();
        new Thread(new SignalReaderTask(doorSignal)).start();
        new Thread(new SignalReaderTask(desiredTemperatureSignal)).start();
        new Thread(new SignalReaderTask(furnaceRunningSignal)).start();
        new Thread(new SignalReaderTask(furnaceErrorSignal)).start();
    }

    private void sleepForInterval() {
        try {
            Thread.sleep(THREAD_SLEEP_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (true) {

            // extracting signals to variables;
            float currentRoomTemp = roomTemperatureSignal.getRoomTemperature();
            float desiredTemp = desiredTemperatureSignal.getDesiredTemperature();
            boolean doorOpened = doorSignal.isDoorOpen();
            boolean furnaceError = furnaceErrorSignal.isError();
            boolean furnaceRunning = furnaceRunningSignal.isRunning();

            // printing status
            System.out.println("Current Room Temperature: " + currentRoomTemp);
            System.out.println("Desired Temperature: " + desiredTemp);
            System.out.println("Door Status: " + doorOpened);
            System.out.println("Furnace Error Status: " + furnaceError);
            System.out.println("Furnace Running Status: " + furnaceRunning);

            if (currentRoomTemp < MIN_SAFE_TEMPERATURE && !furnaceRunning) {
                System.out.println("Turning on the furnace to avoid frozing");
                furnaceControl.turnOn();
            }

            // the furnace will keep working only if temperature is over 6.0 to avoid frozing
            // even though the doors are open
            if (doorOpened) {
                if (doorCounter >= DOOR_OPEN_MAX_COUNT && currentRoomTemp > MIN_SAFE_TEMPERATURE && furnaceRunning) {
                    furnaceControl.turnOff();
                } else {
                    doorCounter = doorCounter + (THREAD_SLEEP_TIME/1000); // sleep time is 3s so we increment by 3
                    System.out.println("Door is currently opened for: " + doorCounter + " seconds");
                }
            } else {
                System.out.println("Door is currently closed");
                doorCounter = 0;
            }

            if (currentRoomTemp > desiredTemp + MAX_DEVIATION_ABOVE_TARGET &&
                    currentRoomTemp > MIN_SAFE_TEMPERATURE && furnaceRunning) {
                System.out.println("Turning off the furnace because it is 1.0 or more higher than desired");
                furnaceControl.turnOff();
            }

            if (currentRoomTemp < desiredTemp - MAX_DEVIATION_BELOW_TARGET &&
                    currentRoomTemp > MIN_SAFE_TEMPERATURE && !furnaceRunning) {
                System.out.println("Turning on the furnace because it is 0.5 or lower than desired");
                furnaceControl.turnOn();
            }

            if (furnaceError && !furnaceRunning) { // warning to not turn on
                furnaceControl.errorDisplay();
            }

            if (furnaceError && furnaceRunning) { // we can reset the furnace only when running
                furnaceControl.reset();
            }

            System.out.println("----------------------------------------------------------------------------");
            // wait for 3 seconds before the next iteration
            sleepForInterval();
        }
    }
}
