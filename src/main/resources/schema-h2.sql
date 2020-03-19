DROP TABLE IF EXISTS INVOICE;
CREATE TABLE INVOICE
        (
        id NUMBER(20),
        invoice_number VARCHAR(50),
        invoice_issue_date DATE,
        invoice_gross_value VARCHAR(50),
        invoice_resource_path VARCHAR(50),
        PRIMARY KEY (id, invoice_number)
        );