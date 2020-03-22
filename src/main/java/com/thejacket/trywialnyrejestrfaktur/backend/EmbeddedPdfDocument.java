package com.thejacket.trywialnyrejestrfaktur.backend;

import com.thejacket.trywialnyrejestrfaktur.utils.StreamResourceUtility;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.server.StreamResource;
import org.apache.commons.io.FilenameUtils;
import org.apache.fop.apps.*;
import org.xml.sax.SAXException;

import javax.xml.transform.*;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Queue;

/*
 * Vaadin component, PDF-specific
 */

@Tag("object")
public class EmbeddedPdfDocument extends Component implements HasSize {

    Queue<Path> queueOfConvertedFiles = new LinkedList<Path>();

    public EmbeddedPdfDocument(String url) {
        this();
        getElement().setAttribute("data", url);
    }

    protected EmbeddedPdfDocument() {
        getElement().setAttribute("type", "application/pdf");
        setSizeFull();
    }

    public void setEmbeddedPdfDivDataAttribute(StreamResource resource) {
        // Super simple naive optimalization. Hold converted files paths in queue. If queue exceeds size 10,
        // release disk memory by deleting 3rd pdf file in order of visiting. (.fo file is not deleted)
        // If user exits, queue information is discarded but files persist.
        try {
            if (!queueOfConvertedFiles.isEmpty() && queueOfConvertedFiles.size() > 2) {
                Files.delete(queueOfConvertedFiles.poll());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // resource type of 'application/octet-stream' when unsupported doesn't tell much,
        // resolving file extension with getName instead
        String resourceExtension = FilenameUtils.getExtension(resource.getName());
        // empty value if not a .pdf or .fo, show error on other unsupported format
        if (resourceExtension.equals("")) {
            getElement().setAttribute("data", "");
        } else if (resourceExtension.equals("pdf")) {
            getElement().setAttribute("data", resource);
        } else if (resourceExtension.equals("fo")) {
            Path newPath = Paths.get(
                    System.getProperty("user.dir"),
                    "/pdfFiles",
                    resource.getName().replace(".fo", ".pdf")
            );
            if (Files.notExists(newPath)) {
                convertXMLFOToPdfFormat(resource);
            }
            queueOfConvertedFiles.add(newPath);

            resource = StreamResourceUtility.StreamResourceMaker(String.valueOf(newPath));
            getElement().setAttribute("data", resource);
        } else {
            Notification.show("BŁĄD: Ten format jest obecnie nieobsługiwany.");
            getElement().setAttribute("data", "");
        }
    }

    // Initiate Apache FOP conversion from XML-FO to PDF
    // Saves converted file in the same directory
    private void convertXMLFOToPdfFormat(StreamResource resource) {
        FopFactory fopFactory = null;
        try {
            fopFactory = FopFactory.newInstance(new File("fop.xml"));
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }

        OutputStream outStream = null;
        try {
            String newFilePath = String.valueOf(Paths.get(System.getProperty("user.dir"), "/pdfFiles", resource.getName().replace(".fo", ".pdf")));
            outStream = new BufferedOutputStream(new FileOutputStream(new File(newFilePath)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, outStream);

            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();

            Source src = new StreamSource(new File(String.valueOf(Paths.get(System.getProperty("user.dir"), "/pdfFiles", resource.getName()))));
            Result result = new SAXResult(fop.getDefaultHandler());

            transformer.transform(src, result);

        } catch (TransformerException | FOPException e) {
            e.printStackTrace();
        } finally {
            // Clean-up
            try {
                outStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}