package com.diasonti.descriptiontinder.data.util;

public class DataFile {

    private final String filename;

    private final byte[] content;

    public DataFile(String filename, byte[] content) {
        this.filename = filename;
        this.content = content;
    }

    public String getFilename() {
        return filename;
    }

    public byte[] getContent() {
        return content;
    }
}
