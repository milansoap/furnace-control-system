package org.example.Signals;

import org.example.Printer.FileWriterService;

public class FurnaceErrorSignal implements SignalReader {
    private boolean error;
    private final FileWriterService fileReader = new FileWriterService();

    @Override
    public void readFromFile() {
        String error = fileReader.readFromFile("signals/furnaceStatusError.txt");
        boolean isError = Boolean.parseBoolean(error);
        setError(isError);
    }

    public void setError(boolean error) {
        this.error = error;
    }
    public boolean isError() {
        return error;
    }

}
