package org.example.project_ppoo_java;

public abstract class MediaFile extends PathItem {
    private final MediaType type;
    private final DirectoryLocation directory;

    protected MediaFile(String path, String name, long size, DirectoryLocation directory, MediaType type) {
        super(path, name, size);
        this.directory = directory;
        this.type = type;
    }

    public MediaType getType() {
        return type;
    }

    public DirectoryLocation getDirectory() {
        return directory;
    }

    @Override
    public String toString() {
        return
                "Type=" + type +
                        "Directory=" + directory +
                        "Name = " + super.getName() + "Size=" + super.getSize() +
                        '}';
    }
}
