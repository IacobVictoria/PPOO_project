package org.example.project_ppoo_java;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//SINGLETON
public class MainManager {
    private static MainManager instance;

    private final IFileStorage fileStorage;
    private final IScanFilesDirectory filesScanner;
    private final IReportService reportService;
    private final LogService logService;

    private final Set<DirectoryLocation> directorySet;
    private final List<MediaFile> mediaFiles;

    private int[] countByType = new int[2];
    private int[][] sizeByLocationAndType;

    private MainManager() {
        this.logService = new LogService("app.log");
        this.fileStorage = new MainFileStorage("locations.txt", logService);
        IMediaFileFactory factory = new MediaFileFactory();
        this.filesScanner = new ScanMediaFilesDirectory(factory, logService);
        this.reportService = new ReportMediaFilesService();


        this.directorySet = new HashSet<>();
        this.mediaFiles = new ArrayList<>();
    }

    public static MainManager getInstance() {
        if (instance == null) {
            instance = new MainManager();
        }
        return instance;
    }

    /**
     * 1. Save all the directories from app in file "location.txt"
     */
    public void loadDirectories() {
        directorySet.clear();
        Set<DirectoryLocation> loadedDirectories = fileStorage.getDirectories();
        directorySet.addAll(loadedDirectories);
        logService.logMessage("Loaded directories: " + directorySet.size());
    }

    /**
     * 2. Save when closing the app all the directories in the final file
     */
    public void saveDirectories() {
        fileStorage.saveDirectories(directorySet);
        logService.logMessage("Saved directories to file!");
    }

    /**
     * 3. Add new directories , atention if it exists or if is duplicated
     *
     * @param path
     * @throws InvalidDirectoryException
     */
    public void addDirectory(String path) throws InvalidDirectoryException {
        if (path == null) {
            throw new InvalidDirectoryException("Directory path is empty");
        }

        File directory = new File(path);
        if (!directory.exists() || !directory.isDirectory()) {
            throw new InvalidDirectoryException("Directory does not exist with path " + path);
        }

        DirectoryLocation newLoc = new DirectoryLocation(path);

        if (directorySet.contains(newLoc)) { //foloseste equals/hashCode custom, ca sa nu compare doar dupa referinta
            throw new DuplicateDirectoryException("Directory already in the list: " + path);
        }

        directorySet.add(newLoc);
        logService.logMessage("Added directory " + path);
    }

    /**
     * Remove the given directory by path
     *
     * @param path
     * @return
     */
    public boolean removeDirectory(String path) {
        if (path == null || path.isBlank()) {
            logService.logMessage("Tried to remove directory with empty path");
            return false;
        }

        boolean removed = directorySet.removeIf(directory -> directory.getPath().equals(path));

        if (removed) {
            logService.logMessage("Removed directory: " + path);
        } else {
            logService.logMessage("Tried to remove non-existent directory: " + path);
        }

        return removed;
    }

    /**
     * Scan all the directories for multimedia files
     */
    public void scanAll() {
        mediaFiles.clear();

        for (DirectoryLocation directory : directorySet) {
            List<MediaFile> foundMultiMediaFiles = filesScanner.scanForFiles(directory);
            mediaFiles.addAll(foundMultiMediaFiles);
        }

        calculateMediaCountByType();
        calculateDirectoryTypeSizes();

        logService.logMessage("Scanned all directories. Found " + mediaFiles.size() + " media files.");
    }


    /**
     * Calculate the statistics for report purposes
     * Calculate the number of the file by each type
     */
    private void calculateMediaCountByType() {
        countByType[0] = 0; // audio
        countByType[1] = 0; // image

        for (MediaFile file : mediaFiles) {
            if (file.getType() == MediaType.AUDIO) {
                countByType[0]++;
            } else if (file.getType() == MediaType.IMAGE) {
                countByType[1]++;
            }
        }
    }

    /**
     * Calculate the size of each directory
     */
    private void calculateDirectoryTypeSizes() {
        List<DirectoryLocation> directories = new ArrayList<>(directorySet);
        int nrOfDirectories = directories.size();
        sizeByLocationAndType = new int[nrOfDirectories][2];
// [directory][audio/image]

        for (int i = 0; i < nrOfDirectories; i++) {
            DirectoryLocation dir = directories.get(i);
            for (MediaFile file : mediaFiles) {
                if (file.getDirectory().getPath().equals(dir.getPath())) { // string equals string atat
                    int size = (int) file.getSize();
                    if (file.getType() == MediaType.AUDIO) {
                        sizeByLocationAndType[i][0] += size;
                    } else if (file.getType() == MediaType.IMAGE) {
                        sizeByLocationAndType[i][1] += size;
                    }
                }
            }
        }
    }

    /**
     * Summary Reports
     */

    public String getSummaryReport() {
        return reportService.generateSummaryReport(mediaFiles, countByType, sizeByLocationAndType, new ArrayList<>(directorySet));
    }

    public String getTypeReport(MediaType type) {
        return reportService.generateByTypeReport(mediaFiles, type, countByType, sizeByLocationAndType, new ArrayList<>(directorySet));
    }

    /**
     * Save final report to file
     *
     * @param filePath
     */
    public void saveSummaryReportToFile(String filePath) {
        String report = getSummaryReport();

        reportService.saveReportToFile(report, filePath);
        logService.logMessage("Saved summary report to: " + filePath);
    }

    /**
     * Save report type to file
     *
     * @param type
     * @param filePath
     */
    public void saveTypeReportToFile(MediaType type, String filePath) {
        String report = getTypeReport(type);

        reportService.saveReportToFile(report, filePath);
        logService.logMessage("Saved " + type + " report to: " + filePath);

    }

    public List<DirectoryLocation> getDirectories() {
        return new ArrayList<>(directorySet);
    }

    public List<MediaFile> getMediaFiles() {
        return new ArrayList<>(mediaFiles);
    }

    /**
     * Get media files per directory
     *
     * @param directory
     * @return
     */
    public List<MediaFile> getMediaFilesForDirectory(DirectoryLocation directory) {
        List<MediaFile> result = new ArrayList<>();
        if (directory == null) {
            return result;
        }
        for (MediaFile file : mediaFiles) {
            if (file.getDirectory() != null &&
                    file.getDirectory().getPath().equals(directory.getPath())) {
                result.add(file);
            }
        }
        return result;
    }

}
