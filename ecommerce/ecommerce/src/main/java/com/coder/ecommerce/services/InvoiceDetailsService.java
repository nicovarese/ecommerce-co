package com.coder.ecommerce.services;

import com.coder.ecommerce.entities.InvoiceDetails;
import com.coder.ecommerce.repositories.InvoiceDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceDetailsService {

    private final InvoiceDetailsRepository repository;

    @Autowired
    public InvoiceDetailsService(InvoiceDetailsRepository repository) {
        this.repository = repository;
    }

    public InvoiceDetails saveInvoiceDetails(InvoiceDetails invoiceDetails) {
        return repository.save(invoiceDetails);
    }

    public List<InvoiceDetails> getAllInvoiceDetails(){
        return repository.findAll();
    }

    public Optional<InvoiceDetails> getInvoiceDetailsById(Long id){
        return repository.findById(id);
    }

}

