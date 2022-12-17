package com.Laggrif.pictures_viewer.smallTest;

import com.Laggrif.pictures_viewer.file_managment.LocalFile;

import java.io.File;
import java.util.Arrays;

public class LocalFileTest {
    public static void main(String[] args) {
        LocalFile lf = new LocalFile(new File("/home/un"), new File("/home"));
        System.out.println(Arrays.toString(lf.listDir()));

    }
}
