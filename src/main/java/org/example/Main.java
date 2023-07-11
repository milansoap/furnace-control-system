package org.example;

import org.example.ControlSystem.ControlSystem;
import org.example.FurnaceControl.FurnaceControl;
import org.example.Signals.*;

public class Main {
    public static void main(String[] args) {

        RoomTemperatureSignal temperatureSignal = new RoomTemperatureSignal();
        DoorSignal doorSignal = new DoorSignal();
        DesiredTemperatureSignal desiredTemperatureSignal = new DesiredTemperatureSignal();
        FurnaceRunningSignal furnaceRunningSignal = new FurnaceRunningSignal();
        FurnaceErrorSignal furnaceErrorSignal = new FurnaceErrorSignal();

        // creating control system with our signals and running thread
        ControlSystem controlSystem = new ControlSystem(temperatureSignal, doorSignal, desiredTemperatureSignal, furnaceRunningSignal, furnaceErrorSignal);
        controlSystem.startSignalReaders();

        // wait for 1 second to give the signal reader threads possibility to
        // get the right data signals for the first time
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        controlSystem.run();
    }
}