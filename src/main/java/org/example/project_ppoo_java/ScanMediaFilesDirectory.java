package org.example.project_ppoo_java;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ScanMediaFilesDirectory implements IScanFilesDirectory {
    private final IMediaFileFactory factory;
    private final LogService logService;

    public ScanMediaFilesDirectory(IMediaFileFactory factory, LogService logService) {
        this.factory = factory;
        this.logService = logService;
    }

    /**
     * Scan the given directory for media files
     * @param directory
     * @return
     */
    @Override
    public List<MediaFile> scanForFiles(DirectoryLocation directory) {
        List<MediaFile> mediaFiles = new ArrayList<>();

        if (directory == null) {
            logService.logMessage("Null directory");
            return mediaFiles;
        }

        File dir = new File(directory.getPath());
        if (!dir.exists() || !dir.isDirectory()) {
            logService.logMessage("scanForFiles method: directory does not exist or is not a directory " + directory.getPath());
            return mediaFiles;
        }

        scanDirectoryRecursive(dir, directory, mediaFiles);

        return mediaFiles;
    }

    /**
     * Scan the primary directory recursively in order to find media files
     * @param currentFolder
     * @param directory
     * @param result
     */
    private void scanDirectoryRecursive(File currentFolder,
                                        DirectoryLocation directory,
                                        List<MediaFile> result) {

        File[] files = currentFolder.listFiles();
        if (files == null) {
            logService.logMessage("Cannot list files");
            return;
        }

        for (File file : files) {
            if (file.isDirectory()) { // daca e folder mergem recursiv iar
                scanDirectoryRecursive(file, directory, result);
            } else if (file.isFile()) {
                try {
                    MediaFile media = factory.createFile(file.getAbsolutePath(), directory);
                    if (media != null) {
                        result.add(media);
                        logService.logMessage("Media added: " + file.getAbsolutePath());
                    }
                } catch (UnsupportedExtensionException | InvalidDirectoryException e) {
                    logService.logMessage("Unsupported file: " + file.getAbsolutePath());
                }
            }
        }
    }

}
