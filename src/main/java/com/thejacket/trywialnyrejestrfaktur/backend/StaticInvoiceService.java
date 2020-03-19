package com.thejacket.trywialnyrejestrfaktur.backend;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.stereotype.Service;

/*
* Use when database is disabled
 */

@Service
public class StaticInvoiceService {

    private List<Invoice> invoices;

    {
        // Initialize sample data

        Long id = 42L;

        invoices = new ArrayList<>();
        long minDay = LocalDate.of(2015, 1, 1).toEpochDay();
        long maxDay = LocalDate.of(2020, 1, 1).toEpochDay();

        for(int i = 1; i < 10; i++) {
            long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
            invoices.add(new Invoice(
                    id++,
                    "F2020/01/00001",
                    LocalDate.ofEpochDay(randomDay),
                    "10000",
                    String.format("pdfFiles/invoice%d.pdf", i)));
        }
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

}
