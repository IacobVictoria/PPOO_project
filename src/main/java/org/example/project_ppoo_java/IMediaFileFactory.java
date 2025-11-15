package org.example.project_ppoo_java;

public interface IMediaFileFactory {

    /**
     * Create the media file (audio or image) using Factory pattern
     * @param path
     * @param directory
     * @return
     * @throws UnsupportedExtensionException
     */
    MediaFile createFile(String path, DirectoryLocation directory) throws UnsupportedExtensionException, InvalidDirectoryException;
}
