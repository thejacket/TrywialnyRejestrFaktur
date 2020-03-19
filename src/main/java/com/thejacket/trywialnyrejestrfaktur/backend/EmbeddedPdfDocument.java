package com.thejacket.trywialnyrejestrfaktur.backend;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.server.StreamResource;

/*
* Vaadin component, PDF-specific
 */

@Tag("object")
public class EmbeddedPdfDocument extends Component implements HasSize {

    public EmbeddedPdfDocument(StreamResource resource) {
        this();
        getElement().setAttribute("data", resource);
    }

    public EmbeddedPdfDocument(String url) {
        this();
        getElement().setAttribute("data", url);
    }

    protected EmbeddedPdfDocument() {
        getElement().setAttribute("type", "application/pdf");
        setSizeFull();
    }

    public void setEmbeddedPdfDivDataAttribute(StreamResource resource) {
        if (resource != null) {
            getElement().setAttribute("data", resource);
        } else {
            getElement().setAttribute("data", "");
        }
    }

}