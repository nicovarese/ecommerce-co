package com.coder.ecommerce.services;

import com.coder.ecommerce.entities.Client;
import com.coder.ecommerce.entities.Invoice;
import com.coder.ecommerce.entities.InvoiceDetails;
import com.coder.ecommerce.repositories.ClientRepository;
import com.coder.ecommerce.repositories.InvoiceRepository;
import com.coder.ecommerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository, ClientRepository clientRepository, ProductRepository productRepository) {
        this.invoiceRepository = invoiceRepository;
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
    }

    public Invoice saveInvoice(Long clientId) {
        // Verificar si el cliente existe
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Client with ID " + clientId + " does not exist"));

        // Crear una nueva instancia de Invoice
        Invoice invoice = new Invoice();
        invoice.setClient(client);

        // Guardar la factura
        return invoiceRepository.save(invoice);
    }

    private void validateInvoice(Invoice invoice) {
        if (invoice.getTotal() <= 0) {
            throw new IllegalArgumentException("Total amount must be greater than zero");
        }
        if (invoice.getInvoiceDetails() != null && !invoice.getInvoiceDetails().isEmpty()) {
            for (InvoiceDetails details : invoice.getInvoiceDetails()) {
                if (details.getProduct() == null || !productRepository.existsById(details.getProduct().getId())) {
                    throw new IllegalArgumentException("Product in InvoiceDetails is mandatory and must exist");
                }
                if (details.getAmount() <= 0) {
                    throw new IllegalArgumentException("Amount in InvoiceDetails must be greater than zero");
                }
                if (details.getPrice() <= 0) {
                    throw new IllegalArgumentException("Price in InvoiceDetails must be greater than zero");
                }
            }
        }
    }

    public List<Invoice> getAllInvoices() {
     return invoiceRepository.findAll();
    }

    public Optional<Invoice> getInvoiceById(Long id){
        return invoiceRepository.findById(id);
    }

    public void deleteInvoiceById(Long id){
        invoiceRepository.deleteById(id);
    }



}
