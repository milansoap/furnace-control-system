package org.example.FurnaceControl;

import org.example.Printer.FileWriterService;

import java.io.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FurnaceControl {
    private int errorCounter = 0;
    FileWriterService fileWriter = new FileWriterService();
    private ExecutorService executor;
    private Future<?> turnOffTask;
    private Future<?> turnOnTask;
    private static final long THREE_MINUTES_IN_MILLIS = 10 * 20 * 1000;
    private Deque<Long> resetTimestamps = new ArrayDeque<>();


    public void turnOn() {
        if (turnOnTask == null || turnOnTask.isDone()) {
            executor = Executors.newFixedThreadPool(1);
            turnOnTask = executor.submit(() -> {
                System.out.println("Starting the furnace, please wait 5 seconds ...");
                try {
                    Thread.sleep(5000);
                    // rewrite the status of the furnace (simulation)
                    fileWriter.writeToFile("true","signals/furnaceStatusRunning.txt");
                    System.out.println("Furnace ON!");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }


    public void turnOff() {
        if (turnOffTask == null || turnOffTask.isDone()) {
            executor = Executors.newFixedThreadPool(1);
            turnOffTask = executor.submit(() -> {
                System.out.println("Turning off the furnace ..");
                try {
                    Thread.sleep(5000);
                    fileWriter.writeToFile("false","signals/furnaceStatusRunning.txt");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public void reset() {

        long now = System.currentTimeMillis();
        // remove timestamps more than 3 minutes old
        while (!resetTimestamps.isEmpty() && now - resetTimestamps.peekFirst() > THREE_MINUTES_IN_MILLIS) {
            resetTimestamps.removeFirst();
        }
        resetTimestamps.addLast(now);
        // if reset has been called 3 times in last 3 minutes, send alarm and return
        if (resetTimestamps.size() >= 3) {
            sendAlarm();
            return;
        }

        this.turnOff();
        try {
            // wait for turnoff to complete
            turnOffTask.get(); // this will block until turnOffTask is completed
            fileWriter.writeToFile("false","signals/furnaceStatusError.txt");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        this.turnOn();
        try {
            // wait for turnOn to complete
            turnOnTask.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("There has been " + resetTimestamps.size() + " resets in last 3 minutes");
    }

    void sendAlarm() {
        for (int i = 0; i < 10; i++) {
            System.out.println("Alarm! Alarm!");
        }
    }
    public void errorDisplay(){
        System.out.println("There is an error on the furnace!!! Please do not turn it on");
    }

}
