package com.Laggrif.pictures_viewer.file_managment;

import java.io.File;
import java.util.ArrayList;

public class LocalFile implements Source<LocalFile> {
    File path;
    File parent;


    public LocalFile(File path, File parent) {
        this.path = path;
        this.parent = parent;
    }

    @Override
    public LocalFile[] listDir() {
        File[] dirs = path.listFiles(File::isDirectory);
        if (dirs == null) { return new LocalFile[0]; }

        ArrayList<LocalFile> d = new ArrayList<>();
        for (File f: dirs) {
            d.add(new LocalFile(f, this.path));
        }
        return d.toArray(new LocalFile[0]);
    }

    @Override
    public File[] listFile() {
        File[] files = path.listFiles(File::isFile);
        if (files == null) { return new File[0]; }
        return files;
    }

    @Override
    public boolean isFile(File file) {
        return file.isFile();
    }

    @Override
    public boolean isDir(File file) {
        return file.isDirectory();
    }

}
