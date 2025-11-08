package org.example.project_ppoo_java;

import java.io.File;
import java.util.Objects;

public class DirectoryLocation extends PathItem {

    protected DirectoryLocation(String path) throws InvalidDirectoryException {
        super(path);

        File directory = new File(path);
        if (!directory.exists() || !directory.isDirectory()) {
            throw new InvalidDirectoryException("Invalid directory found " + path);
        }
    }

    @Override
    public String toString() {
        return "Directory: " + getPath();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DirectoryLocation)) return false;
        return getPath().equalsIgnoreCase(((DirectoryLocation) o).getPath());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPath().toLowerCase());
    }
}
