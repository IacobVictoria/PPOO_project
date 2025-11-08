package org.example.project_ppoo_java;

public class AudioFile extends MediaFile{
    private final AudioTypeFormat format;

    protected AudioFile(String path,String name, long size, DirectoryLocation directory, MediaType type,AudioTypeFormat format) {
        super(path,name, size, directory, type);
        this.format = format;
    }

    public AudioTypeFormat getFormat() {
        return format;
    }

    @Override
    public String toString() {
        return "AudioFile{" +
                "name='" + getName() + '\'' +
                ", path='" + getPath() + '\'' +
                ", size=" + getSize() +
                ", directory=" + getDirectory() +
                ", type=" + getType() +
                ", format=" + format +
                '}';
    }

}
