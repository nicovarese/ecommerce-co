package com.coder.ecommerce.services;

import com.coder.ecommerce.entities.Invoice;
import com.coder.ecommerce.entities.InvoiceDetails;
import com.coder.ecommerce.entities.Product;
import com.coder.ecommerce.repositories.InvoiceDetailsRepository;
import com.coder.ecommerce.repositories.InvoiceRepository;
import com.coder.ecommerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceDetailsService {

    private final InvoiceDetailsRepository invoiceDetailsRepository;
    private final InvoiceRepository invoiceRepository;
    private final ProductRepository productRepository;

    @Autowired
    public InvoiceDetailsService(InvoiceDetailsRepository invoiceDetailsRepository, InvoiceRepository invoiceRepository, ProductRepository productRepository) {
        this.invoiceDetailsRepository = invoiceDetailsRepository;
        this.invoiceRepository = invoiceRepository;
        this.productRepository = productRepository;
    }

    public InvoiceDetails createInvoiceDetails(Long invoiceId, Long productId, Integer amount) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new IllegalArgumentException("Invoice with ID " + invoiceId + " does not exist"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product with ID " + productId + " does not exist"));

        InvoiceDetails invoiceDetails = new InvoiceDetails();
        invoiceDetails.setInvoice(invoice);
        invoiceDetails.setProduct(product);
        invoiceDetails.setAmount(amount);
        invoiceDetails.setPrice(amount * product.getPrice());

        InvoiceDetails savedInvoiceDetails = invoiceDetailsRepository.save(invoiceDetails);

        // Recalcular el total de la factura
        recalculateInvoiceTotal(invoice);

        return savedInvoiceDetails;
    }

    private void recalculateInvoiceTotal(Invoice invoice) {
        double total = invoice.getInvoiceDetails().stream()
                .mapToDouble(InvoiceDetails::getPrice)
                .sum();
        invoice.setTotal(total);
        invoiceRepository.save(invoice);
    }

    private void validateInvoiceDetails(InvoiceDetails invoiceDetails) {
        if (invoiceDetails.getAmount() == null || invoiceDetails.getAmount() <= 0) {
            throw new IllegalArgumentException("Amount is mandatory and must be greater than zero");
        }
        if (invoiceDetails.getPrice() == null || invoiceDetails.getPrice() <= 0) {
            throw new IllegalArgumentException("Price is mandatory and must be greater than zero");
        }
    }

    public List<InvoiceDetails> getAllInvoiceDetails(){
        return invoiceDetailsRepository.findAll();
    }

    public Optional<InvoiceDetails> getInvoiceDetailsById(Long id){
        return invoiceDetailsRepository.findById(id);
    }

    public void deleteInvoiceDetailById(Long id){
        invoiceDetailsRepository.deleteById(id);
    }

}

