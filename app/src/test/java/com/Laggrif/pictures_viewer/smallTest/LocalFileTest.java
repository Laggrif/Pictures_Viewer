package com.Laggrif.pictures_viewer.smallTest;

import com.Laggrif.pictures_viewer.file_managment.LocalFile;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;

public class LocalFileTest {
    @Test
    public void main() {
        LocalFile lf = new LocalFile(new File("/home/un"));
        System.out.println(Arrays.toString(lf.listDir()));
    }
}
