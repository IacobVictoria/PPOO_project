package org.example.project_ppoo_java;

public class ImageFile extends MediaFile {

    private final ImageTypeFormat format;

    protected ImageFile(String path, String name, long size, DirectoryLocation directory, MediaType type, ImageTypeFormat format) {
        super(path, name, size, directory, type);
        this.format = format;
    }

    public ImageTypeFormat getFormat() {
        return format;
    }

    @Override
    public String toString() {
        return super.toString() +
                "ImageFile{" +
                "name='" + getName() + '\'' +
                ", path='" + getPath() + '\'' +
                ", size=" + getSize() +
                ", directory=" + getDirectory() +
                ", type=" + getType() +
                ", format=" + format +
                '}';
    }
}
