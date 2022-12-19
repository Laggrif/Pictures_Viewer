package com.Laggrif.pictures_viewer.file_managment;

import java.io.File;
import java.util.ArrayList;

public class LocalFile implements Source<LocalFile> {
    private final File path;
    private final File parent;

    public LocalFile() {
        this.path = new File("/");
        this.parent = null;
    }

    public LocalFile(String path) {
        this.path = new File(path);
        this.parent = this.path.getParentFile();
    }

    public LocalFile(File path) {
        this.path = path;
        this.parent = this.path.getParentFile();
    }

    private LocalFile(File path, File parent) {
        this.path = path;
        this.parent = parent;
    }

    @Override
    public LocalFile[] listDir() {
        File[] dirs = path.listFiles(File::isDirectory);
        if (dirs == null) { return new LocalFile[0]; }

        ArrayList<LocalFile> d = new ArrayList<>();
        for (File f: dirs) {
            d.add(new LocalFile(f, this.parent));
        }
        return d.toArray(new LocalFile[0]);
    }

    @Override
    public LocalFile[] listFile() {
        File[] files = path.listFiles(File::isFile);
        if (files == null) { return new LocalFile[0]; }

        ArrayList<LocalFile> localFiles = new ArrayList<>();
        for (File f: files) {
            localFiles.add(new LocalFile(f, this.parent));
        }

        return localFiles.toArray(new LocalFile[0]);
    }

    @Override
    public boolean isFile() {
        return this.path.isFile();
    }

    @Override
    public boolean isDir() {
        return this.path.isDirectory();
    }

    @Override
    public LocalFile getParent() { return (this.parent != null) ? new LocalFile(this.parent.getAbsolutePath()) : this; }

    @Override
    public String getExtension() {
        if (isDir()) { throw new IllegalArgumentException("Directories don't have extensions"); }
        String[] str = this.getName().split("\\.");
        return (str[0].length() > 0 && str.length >= 2 || str.length >= 3) ? ".".concat(str[str.length - 1]) : "";
    }

    @Override
    public String getName() { return (this.parent != null) ? this.path.getName() : this.path.getAbsolutePath(); }

    @Override
    public String toString() { return path.getAbsolutePath(); }
}
