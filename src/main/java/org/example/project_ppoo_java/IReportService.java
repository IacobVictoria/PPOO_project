package org.example.project_ppoo_java;

import java.util.List;

public interface IReportService {

    /**
     *Generates an overall report with information about all media files and their distribution by type and directory.**
     * @param files
     * @param countByType
     * @param sizeByLocationAndType
     * @param directories
     * @return
     */
    String generateSummaryReport(List<MediaFile> files,
                                 int[] countByType,
                                 int[][] sizeByLocationAndType,
                                 List<DirectoryLocation> directories);

    /**
     * Generates a report for a specific media type (AUDIO or IMAGE), using the list of files and the associated statistics.
     * @param files
     * @param type
     * @param countByType
     * @param sizeByLocationAndType
     * @param directories
     * @return
     */
    String generateByTypeReport(List<MediaFile> files,
                                MediaType type,
                                int[] countByType,
                                int[][] sizeByLocationAndType,
                                List<DirectoryLocation> directories);

    /**
     * Save report content to filePath
     * @param content
     * @param filePath
     */
   void saveReportToFile(String content, String filePath);
}
