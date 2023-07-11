package org.example.Signals;
import org.example.Printer.FileWriterService;

public class DoorSignal implements SignalReader {
    private boolean doorOpen;
    private final FileWriterService fileReader = new FileWriterService();

    @Override
    public void readFromFile() {
        String doorOpened = fileReader.readFromFile("signals/doorStatus.txt");
        boolean doorOpen = Boolean.parseBoolean(doorOpened);
        setDoorOpen(doorOpen);
    }

    public void setDoorOpen(boolean doorOpen) {
        this.doorOpen = doorOpen;
    }
    public boolean isDoorOpen() {
        return doorOpen;
    }

}