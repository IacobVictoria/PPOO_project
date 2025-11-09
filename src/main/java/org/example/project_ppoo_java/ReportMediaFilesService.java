package org.example.project_ppoo_java;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReportMediaFilesService implements IReportService {

    private static final DateTimeFormatter REPORT_DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    /**
     * Report of the entire AUDIO and IMAGE aria of files
     *
     * @param files
     * @param countByType
     * @param sizeByLocationAndType
     * @param directories
     * @return
     */
    @Override
    public String generateSummaryReport(List<MediaFile> files,
                                        int[] countByType,
                                        int[][] sizeByLocationAndType,
                                        List<DirectoryLocation> directories) {

        StringBuilder sb = new StringBuilder();
        sb.append("Report generated at: ")
                .append(LocalDateTime.now().format(REPORT_DATE_FORMATTER))
                .append(System.lineSeparator());

        sb.append("Final Report").append(System.lineSeparator());


        sb.append("Total media files: ").append(files.size()).append("\n");

        if (countByType != null && countByType.length >= 2) {
            sb.append("Audio files: ").append(countByType[0]).append("\n");
            sb.append("Image files: ").append(countByType[1]).append("\n");
        }

        if (sizeByLocationAndType != null &&
                directories != null &&
                sizeByLocationAndType.length == directories.size()) {

            sb.append("\nSize by directory and type in bytes:\n");

            for (int i = 0; i < directories.size(); i++) {
                DirectoryLocation dir = directories.get(i);

                int audioSize = sizeByLocationAndType[i][0];
                int imageSize = sizeByLocationAndType[i][1];

                sb.append(String.format(
                        "In directory \"%s\", a total of %d bytes of AUDIO files and %d bytes of IMAGE files were found.%n",
                        dir.getPath(),
                        audioSize,
                        imageSize
                ));

            }
        }

        return sb.toString();
    }

    /**
     * Generate report per type of media file : AUDIO OR IMAGE
     *
     * @param files
     * @param type
     * @param countByType
     * @param sizeByLocationAndType
     * @param directories
     * @return
     */
    @Override
    public String generateByTypeReport(List<MediaFile> files,
                                       MediaType type,
                                       int[] countByType,
                                       int[][] sizeByLocationAndType,
                                       List<DirectoryLocation> directories) {

        StringBuilder sb = new StringBuilder();
        sb.append("Report generated at: ")
                .append(LocalDateTime.now().format(REPORT_DATE_FORMATTER))
                .append(System.lineSeparator());

        sb.append("Report per type ").append(type).append(System.lineSeparator());


        int index = -1;
        if (type == MediaType.AUDIO) {
            index = 0;
        } else if (type == MediaType.IMAGE) {
            index = 1;
        }


        if (index != -1 &&
                countByType != null &&
                countByType.length > index) {

            sb.append("Total files of this type: ")
                    .append(countByType[index])
                    .append("\n");
        }


        if (index != -1 &&
                sizeByLocationAndType != null &&
                directories != null &&
                sizeByLocationAndType.length == directories.size()) {

            sb.append("\nSize by directory in bytes:\n");

            for (int i = 0; i < directories.size(); i++) {
                int sizeForType = sizeByLocationAndType[i][index];

                if (sizeForType > 0) {
                    sb.append(String.format(
                            "Directory \"%s\" contains a total of %d bytes for this media type.%n",
                            directories.get(i).getPath(),
                            sizeForType
                    ));
                }

            }
        }

        sb.append("\nFiles of type ").append(type).append(":\n");

        for (MediaFile file : files) {
            if (file.getType() == type) {
                sb.append(file.getDirectory().getPath())
                        .append("/")
                        .append(file.getName())
                        .append(" (")
                        .append(file.getSize())
                        .append(" bytes)\n");
            }
        }

        return sb.toString();
    }

    public void saveReportToFile(String content, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
        } catch (IOException e) {
            System.out.println("Could not save the report to file");
        }

    }
}
