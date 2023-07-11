package org.example.Signals;
import org.example.Printer.FileWriterService;

public class DesiredTemperatureSignal implements SignalReader{

    private float desiredTemperature;
    private FileWriterService fileReader = new FileWriterService();
    @Override
    public void readFromFile() {
        String temperatureString = fileReader.readFromFile("signals/desiredTemperature.txt");
        float desiredTemperature = Float.parseFloat(temperatureString);
        setDesiredTemperature(desiredTemperature);
    }

    public float getDesiredTemperature() {
        return desiredTemperature;
    }
    public void setDesiredTemperature(float desiredTemperature) {
        this.desiredTemperature = desiredTemperature;
    }
}
