package org.example.Signals;

import org.example.Printer.FileWriterService;

public class FurnaceRunningSignal implements SignalReader {
    private boolean isRunning;
    private final FileWriterService fileReader = new FileWriterService();

    @Override
    public void readFromFile() {
        String running = fileReader.readFromFile("signals/furnaceStatusRunning.txt");
        boolean isRunning = Boolean.parseBoolean(running);
        setRunning(isRunning);
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }
    public boolean isRunning() {
        return isRunning;
    }

}
