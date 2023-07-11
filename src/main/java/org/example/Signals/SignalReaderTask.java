package org.example.Signals;

public class SignalReaderTask implements Runnable {
    private SignalReader signalReader;
    public SignalReaderTask(SignalReader signalReader) {
        this.signalReader = signalReader;
    }

    @Override
    public void run() {
        while (true) {
            signalReader.readFromFile();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
