package org.example.project_ppoo_java;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class MainFileStorage implements IFileStorage {
    private final File file;
    private final LogService logService;

    public MainFileStorage(String filePath, LogService logService) {
        this.file = new File(filePath);
        this.logService = logService;
    }

    /**
     * Get the directories from txt file
     * @return  Set<DirectoryLocation>
     */
    @Override
    public Set<DirectoryLocation> getDirectories() {
        Set<DirectoryLocation> directories = new LinkedHashSet<>(); // ordine cu linkedhashset

        if (!this.file.exists()) {
            logService.logMessage("locations.txt not found. Starting with empty directory list.");
            return directories;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(this.file))) {
            String line;

            while ((line = br.readLine()) != null) {
                line = line.trim(); //path-ul

                if (line.isEmpty()) {
                    continue;
                }

                try {
                    DirectoryLocation directory = new DirectoryLocation(line);
//duplicate din fisier
                    if (!directories.add(directory)) {

                        logService.logMessage("Duplicate directory found in locations.txt (ignored): " + line);
                    }

                } catch (InvalidDirectoryException e) {
                    logService.logMessage("Invalid directory in locations.txt: " + line);
                }
            }

        } catch (IOException e) {
            logService.logMessage("Error reading locations.txt: " + e.getMessage());
        }

        return directories;
    }

    /**
     * Save the directories in the txt file after exiting the program
     * @param directories
     */
    @Override
    public void saveDirectories(Set<DirectoryLocation> directories) {
        //false ca sa pot sa golesc si supracriu
        try (PrintWriter printwriter = new PrintWriter(new FileWriter(file, false))) {
            for (DirectoryLocation directory : directories) {
                printwriter.println(directory.getPath());
            }
            logService.logMessage("Saved " + directories.size() + " directories!");
        } catch (IOException e) {
            logService.logMessage("Error saving in locations.txt " + e.getMessage());
        }
    }
}
