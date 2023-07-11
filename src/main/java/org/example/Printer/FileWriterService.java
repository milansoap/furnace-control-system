package org.example.Printer;

import java.io.*;

public class FileWriterService {

    public FileWriterService() {
    }

    public void writeToFile(String content, String filePath) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, false))) {
            writer.println(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readFromFile(String filePath) {
        String result = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            result = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


}
