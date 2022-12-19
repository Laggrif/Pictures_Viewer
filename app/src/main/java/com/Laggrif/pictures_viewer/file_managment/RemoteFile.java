package com.Laggrif.pictures_viewer.file_managment;

import com.hierynomus.msfscc.FileAttributes;
import com.hierynomus.msfscc.fileinformation.FileIdBothDirectoryInformation;
import com.hierynomus.smbj.share.DiskShare;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import static com.hierynomus.protocol.commons.EnumWithValue.EnumUtils.isSet;
import static java.lang.String.join;

public class RemoteFile implements file_managment.Source<RemoteFile> {
    private final DiskShare share;
    private final String path;
    private final String parent;

    public RemoteFile(DiskShare share) {
        this.share = share;
        this.path = "/";
        this.parent = null;
    }

    public RemoteFile(DiskShare share, String path) {
        this.share = share;
        this.path = path;
        if (!Objects.equals(path, "/")) {
            String[] s = path.split("(?<=/)");
            this.parent = (s.length > 1) ? join("", Arrays.copyOf(s, s.length - 1)) : null;
        } else {
            this.parent = null;
        }
    }

    @Override
    public RemoteFile[] listDir() {
        ArrayList<RemoteFile> dirs = new ArrayList<>();
        for (FileIdBothDirectoryInformation dir : share.list(path)) {
            String filename = dir.getFileName();
            if (isSet(dir.getFileAttributes(), FileAttributes.FILE_ATTRIBUTE_DIRECTORY) && !filename.matches("\\.|\\.\\.")) {
                dirs.add(new RemoteFile(share, join("/", new String[]{(!path.equals("/")) ? path : "", filename})));
            }
        }
        return dirs.toArray(new RemoteFile[0]);
    }

    @Override
    public RemoteFile[] listFile() {
        ArrayList<RemoteFile> files = new ArrayList<>();
        for (FileIdBothDirectoryInformation file : share.list(path)) {
            String filename = file.getFileName();
            if (!isSet(file.getFileAttributes(), FileAttributes.FILE_ATTRIBUTE_DIRECTORY) && !filename.matches("\\.|\\.\\.")) {
                files.add(new RemoteFile(share, join("/", new String[]{(!path.equals("/")) ? path : "", filename})));
            }
        }
        return files.toArray(new RemoteFile[0]);
    }

    @Override
    public boolean isFile() {
        return !isSet(share.getFileInformation(path).getBasicInformation().getFileAttributes(), FileAttributes.FILE_ATTRIBUTE_DIRECTORY);
    }

    @Override
    public boolean isDir() {
        return isSet(share.getFileInformation(path).getBasicInformation().getFileAttributes(), FileAttributes.FILE_ATTRIBUTE_DIRECTORY);
    }

    @Override
    public RemoteFile getParent() {
        return (parent != null) ? new RemoteFile(share, parent) : this;
    }

    @Override
    public String getName() {
        String[] s = path.split("/");
        int l = s.length;
        return (l != 0) ? s[l - 1] : "/";
    }

    @Override
    public String getExtension() {
        if (isDir()) { throw new IllegalArgumentException("Directories don't have extensions"); }
        String[] str = getName().split("\\.");
        return (str[0].length() > 0 && str.length >= 2) ? ".".concat(str[str.length - 1]) : "";
    }

    @Override
    public String toString() {
        return path;
    }
}
