package com.thejacket.trywialnyrejestrfaktur.views.invoicesbrowser;

import com.thejacket.trywialnyrejestrfaktur.backend.Invoice;
import com.thejacket.trywialnyrejestrfaktur.backend.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.thejacket.trywialnyrejestrfaktur.views.main.MainView;

/*
* The forefront of the app's user interface. Contains configuration through Java Vaadin API
* Data is populated from database on view's start by implementing afterNavigation method
* and calling JPA findAll method from InvoiceService
* StaticInvoiceService can be used to go without employing database.
 */

@Route(value = "invoices-browser", layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
@PageTitle("invoices-browser")
@CssImport("styles/views/invoicesbrowser/invoices-browser.css")
public class InvoicesBrowserView extends Div implements AfterNavigationObserver {

    //@Autowired
    //private StaticInvoiceService service;

    @Autowired
    private InvoiceService invoiceService;

    private Grid<Invoice> invoices;

    private EmbeddedPdfDiv embeddedPdfDiv;

    public InvoicesBrowserView() {
        setId("invoices-browser");

        // Configure Grid
        invoices = new Grid<>();
        invoices.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        invoices.setHeightFull();
        invoices.addColumn(Invoice::getInvoiceNumber).setHeader("Numer faktury");
        invoices.addColumn(Invoice::getInvoiceIssueDate).setHeader("Data wystawienia");
        invoices.addColumn(Invoice::getInvoiceGrossValue).setHeader("Wartość brutto");

        // add listener for all grid rows - on click, pass JPA entity data to EmbeddedPdfDiv's setter
        invoices.asSingleSelect().addValueChangeListener(event -> embeddedPdfDiv.setPdfDataAttribute(
                event.getValue().getInvoiceResourcePath(),
                event.getValue().getInvoiceNumber()));

        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();
        createGridLayout(splitLayout);
        embeddedPdfDiv = createEmbeddedPdfLayout(splitLayout);

        add(splitLayout);
    }

    private EmbeddedPdfDiv createEmbeddedPdfLayout(SplitLayout splitLayout) {
        EmbeddedPdfDiv editorDiv = new EmbeddedPdfDiv();
        editorDiv.setId("side-layout");
        splitLayout.addToSecondary(editorDiv);
        return editorDiv;
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setId("wrapper");
        wrapper.setWidthFull();
        splitLayout.addToPrimary(wrapper);
        wrapper.add(invoices);
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {

        // Lazy data initialization
        //invoices.setItems(service.getInvoices());
        invoices.setItems(invoiceService.findAll());
    }

}
