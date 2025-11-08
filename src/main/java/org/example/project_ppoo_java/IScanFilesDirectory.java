package org.example.project_ppoo_java;

import java.util.List;

public interface IScanFilesDirectory {

    /**
     * Scan the given directory for media files
     * @param directory
     * @return
     */
    List<MediaFile> scanForFiles(DirectoryLocation directory);
}
