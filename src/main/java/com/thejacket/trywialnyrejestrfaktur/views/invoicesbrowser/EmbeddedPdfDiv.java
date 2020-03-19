package com.thejacket.trywialnyrejestrfaktur.views.invoicesbrowser;

import com.thejacket.trywialnyrejestrfaktur.backend.EmbeddedPdfDocument;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.StreamResource;
import org.apache.commons.io.FilenameUtils;
import java.io.*;
import java.util.Random;

/*
*   This class extends Div container to prepare for loading PDFs dynamically.
*   After loading it with FileInputStream data is exposed through a StreamResource
*   At the end data setter from EmbeddedPdfDocument is called so PDF can be displayed to user
 */


@Route("testPdf")
public class EmbeddedPdfDiv extends Div {

    private EmbeddedPdfDocument pdfFile;

    public EmbeddedPdfDiv(){
        pdfFile = new EmbeddedPdfDocument("");
        add(pdfFile);
    }

    private InputStream getPdfInputStream(String pdfPath) throws FileNotFoundException {
        return new FileInputStream(pdfPath);
    }

    public void setPdfDataAttribute(String pdfPath, String invoiceNumber) {
        StreamResource resource = new StreamResource(FilenameUtils.getName(pdfPath),
                        () -> {
                            try {
                                return getPdfInputStream(pdfPath);
                            } catch (FileNotFoundException e) {
                                // return empty array, will be displayed as could not load
                                return new ByteArrayInputStream(new byte[]{});
                            }
                        });
        Random random = new Random();
        int i = random.nextInt(10);
        // 20% chance of showing a notification instead of resource
        if (i + 10 > 12) {
            pdfFile.setEmbeddedPdfDivDataAttribute(resource);
            setHeight("100%");
        } else {
            Notification.show(String.format("Nie masz wymaganych uprawnień do wyświetlenia treści faktury %s", invoiceNumber));
        }
    }
}