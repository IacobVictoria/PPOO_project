package org.example.project_ppoo_java;

import java.util.List;
import java.util.Set;

public interface IFileStorage {

    /**
     * Get all the directories from locations.txt file
     * @return
     */
    Set<DirectoryLocation> getDirectories();

    /**
     * Save the directories in the "locations.txt" file when exiting the app
     * @param directories
     */
    void saveDirectories(Set<DirectoryLocation> directories);
}
