package org.example.project_ppoo_java;

public abstract class PathItem {

    private static long NEXT_ID = 1;

    private final long id;
    private String path;
    private String name;
    private long size;

    protected PathItem(String path, String name, long size) {
        this.id = NEXT_ID++;
        this.path = path;
        this.name = name;
        this.size = size;
    }
    protected PathItem(String path) {
        this.id = NEXT_ID++;
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    protected void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    protected void setSize(long size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "PathItem{" +
                "id=" + id +
                ", path='" + path + '\'' +
                ", name='" + name + '\'' +
                ", size=" + size +
                '}';
    }
}
