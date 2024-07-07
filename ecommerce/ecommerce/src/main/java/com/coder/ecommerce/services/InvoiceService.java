package com.coder.ecommerce.services;

import com.coder.ecommerce.entities.Invoice;
import com.coder.ecommerce.repositories.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {

    private final InvoiceRepository repository;

    @Autowired
    public InvoiceService(InvoiceRepository repository){
        this.repository = repository;
    }

    public Invoice saveInvoice(Invoice invoice){
        return repository.save(invoice);
    }

    public List<Invoice> getAllInvoices() {
     return repository.findAll();
    }

    public Optional<Invoice> getInvoiceById(Long id){
        return repository.findById(id);
    }

    public void deleteInvoiceById(Long id){
        repository.deleteById(id);
    }

}
