package com.coder.ecommerce.controllers;


import com.coder.ecommerce.entities.InvoiceDetails;
import com.coder.ecommerce.services.InvoiceDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("api/v1/carts")
public class InvoiceDetailsController {

    private final InvoiceDetailsService service;

    @Autowired
    public InvoiceDetailsController(InvoiceDetailsService service)  {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> createInvoiceDetails(@RequestParam Long invoiceId,
                                                  @RequestParam Long productId,
                                                  @RequestParam Integer amount) {
        try {
            InvoiceDetails savedInvoiceDetails = service.createInvoiceDetails(invoiceId, productId, amount);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedInvoiceDetails);
        } catch (IllegalArgumentException e) {
            // Manejo de argumentos inválidos
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            // Manejo de otros errores de negocio
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating the invoice detail. Please try again later.");
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteInvoiceDetailById(@PathVariable Long id) {
        try {
            service.deleteInvoiceDetailById(id);
            return ResponseEntity.ok("Invoice Detail successfully deleted");
        } catch (Exception e) {
            /// Log para debuggear el error.
            System.out.println(e);
            // Retorno un mensaje genérico.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting Invoice Detail");
        }
    }

}
