package org.example.project_ppoo_java;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class LogService {
    private final File loggingFile;

    public LogService(String filePath) {
        this.loggingFile = new File(filePath);
    }

    /**
     * Write the messages in a logging file , for history purposes, tracking the app
     * @param message
     */
    public void logMessage(String message) {
        try (FileWriter fw = new FileWriter(loggingFile, true)) {
            fw.write(LocalDateTime.now() + " :: " + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
