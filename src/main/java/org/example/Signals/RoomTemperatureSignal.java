package org.example.Signals;
import org.example.Printer.FileWriterService;

public class RoomTemperatureSignal implements SignalReader{
    private float roomTemperature;
    private final FileWriterService fileReader = new FileWriterService();

    @Override
    public void readFromFile() {
        String temperature = fileReader.readFromFile("signals/currentRoomTemperature.txt");
        float roomTemperature = Float.parseFloat(temperature);
        setRoomTemperature(roomTemperature);
    }

    public void setRoomTemperature(float roomTemperature) {
        this.roomTemperature = roomTemperature;
    }
    public float getRoomTemperature() {
        return roomTemperature;
    }


}
