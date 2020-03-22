package com.thejacket.trywialnyrejestrfaktur.utils;

import com.vaadin.flow.server.StreamResource;
import org.apache.commons.io.FilenameUtils;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class StreamResourceUtility {

    public static StreamResource StreamResourceMaker(String filePath){
        return new StreamResource(FilenameUtils.getName(filePath),
                () -> {
                    try {
                        return getPdfInputStream(filePath);
                    } catch (FileNotFoundException e) {
                        // return empty array, will be displayed as could not load
                        return new ByteArrayInputStream(new byte[]{});
                    }
                });
    }

    private static InputStream getPdfInputStream(String pdfPath) throws FileNotFoundException {
        return new FileInputStream(pdfPath);
    }
}
