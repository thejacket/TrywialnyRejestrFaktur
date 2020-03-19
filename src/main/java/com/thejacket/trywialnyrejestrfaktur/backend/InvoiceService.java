package com.thejacket.trywialnyrejestrfaktur.backend;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceService extends JpaRepository<Invoice, Integer> {
}

