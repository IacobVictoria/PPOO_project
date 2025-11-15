package org.example.project_ppoo_java;

import java.io.File;

public class MediaFileFactory implements IMediaFileFactory {

    /**
     * Create the file from the given path based on the extension of the media file
     * @param path
     * @param directory
     * @return
     * @throws UnsupportedExtensionException
     */
    @Override
    public MediaFile createFile(String path, DirectoryLocation directory) throws UnsupportedExtensionException, InvalidDirectoryException {
        if (path == null || directory == null) {
            throw new InvalidDirectoryException("Path or directory is null");
        }

        String lower = path.toLowerCase();
        File file = new File(path);
        String name = file.getName();
        long size = file.length();

        if (lower.endsWith(".mp3")) {
            return new AudioFile(path, name, size, directory, MediaType.AUDIO, AudioTypeFormat.MP3);
        }

        if (lower.endsWith(".wav")) {
            return new AudioFile(path, name, size, directory, MediaType.AUDIO, AudioTypeFormat.WAV);
        }

        if (lower.endsWith(".jpg")) {
            return new ImageFile(path, name, size, directory, MediaType.IMAGE, ImageTypeFormat.JPG);
        }

        if (lower.endsWith(".png")) {
            return new ImageFile(path, name, size, directory, MediaType.IMAGE, ImageTypeFormat.PNG);
        }
        throw new UnsupportedExtensionException("Unsupported extension file: " + path);
    }
}
