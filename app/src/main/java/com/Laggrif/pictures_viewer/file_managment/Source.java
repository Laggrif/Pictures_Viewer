package com.Laggrif.pictures_viewer.file_managment;

import java.io.File;

public interface Source<E> {
    default E[] listDir() { return null; }

    default File[] listFile() { return null; }

    default boolean isFile(File file) { return false; }

    default boolean isDir(File file) {return false; }
}
