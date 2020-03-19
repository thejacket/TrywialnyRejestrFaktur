package com.thejacket.trywialnyrejestrfaktur.backend;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "INVOICE")
public class Invoice {

    @JsonIgnore
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "invoice_number")
    private String invoiceNumber;
    @Column(name = "invoice_issue_date")
    private LocalDate invoiceIssueDate;
    @Column(name = "invoice_gross_value")
    private String invoiceGrossValue;
    @Column(name = "invoice_resource_path")
    private String invoiceResourcePath;

    // no need for builder pattern as every field is obligatory
    public Invoice(Long id, String invoiceNumber, LocalDate invoiceIssueDate, String invoiceGrossValue, String invoiceResourcePath) {
        super();
        this.id = id;
        this.invoiceNumber = invoiceNumber;
        this.invoiceIssueDate = invoiceIssueDate;
        this.invoiceGrossValue = invoiceGrossValue;
        this.invoiceResourcePath = invoiceResourcePath;
    }

    public Invoice() {
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public LocalDate getInvoiceIssueDate() {
        return invoiceIssueDate;
    }

    public void setInvoiceIssueDate(LocalDate invoiceIssueDate) {
        this.invoiceIssueDate = invoiceIssueDate;
    }

    public String getInvoiceGrossValue() {
        return invoiceGrossValue;
    }

    public void setInvoiceGrossValue(String invoiceGrossValue) {
        this.invoiceGrossValue = invoiceGrossValue;
    }

    @Override
    public String toString() {
        return invoiceNumber + " " + invoiceIssueDate + "(" + invoiceGrossValue + ")";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInvoiceResourcePath() {
        return invoiceResourcePath;
    }

    public void setInvoiceResourcePath(String invoiceResourcePath) {
        this.invoiceResourcePath = invoiceResourcePath;
    }

    @Override
    public int hashCode() {
        if (id == null) {
            return super.hashCode();
        } else {
            return id.intValue();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || id == null) {
            return false;
        }
        if (!(obj instanceof Invoice)) {
            return false;
        }

        if (id.equals(((Invoice) obj).id)) {
            return true;
        }
        return false;
    }
}
