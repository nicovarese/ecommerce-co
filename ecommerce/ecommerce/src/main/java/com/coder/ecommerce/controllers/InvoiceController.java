package com.coder.ecommerce.controllers;


import com.coder.ecommerce.entities.Invoice;
import com.coder.ecommerce.services.InvoiceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping ("api/v1/invoices")
public class InvoiceController {

    private final InvoiceService service;

    @Autowired
    public InvoiceController(InvoiceService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> createInvoice(@RequestParam Long clientId) {
        try {
            Invoice savedInvoice = service.saveInvoice(clientId);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedInvoice);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating the invoice. Please try again later.");
        }
    }

    //Agrego una ruta para que quede más clara la api que estoy obteniendo todos los invoices.
    @GetMapping("/all")
    public ResponseEntity<?> getAllInvoices() {
        try {
            List<Invoice> invoices = service.getAllInvoices();
            return ResponseEntity.ok(invoices);
        } catch (Exception e) {
            /// Log para debuggear el error.
            System.out.println("Error retrieving invoices: " + e);
            // Retorno un mensaje genérico.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while retrieving invoices. Please try again later.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getInvoiceById(@PathVariable Long id) {
        try {
            Optional<Invoice> optInvoice = service.getInvoiceById(id);
            if (optInvoice.isPresent()) {
                //En caso de que exista el cliente, retorna un 200 y devuelve el invoice como respuesta.
                Invoice invoice = optInvoice.get();
                return ResponseEntity.ok(invoice);
            } else {
                //Responde que no existe el invoice con ese ID
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invoice doesn't exist");
            }
        } catch (Exception e) {
            /// Log para debuggear el error.
            System.out.println(e);
            // Retorno un mensaje genérico.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving invoice");
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteInvoiceById(@PathVariable Long id) {
        try {
            service.deleteInvoiceById(id);
            return ResponseEntity.ok("Invoice successfully deleted");
        } catch (Exception e) {
            /// Log para debuggear el error.
            System.out.println(e);
            // Retorno un mensaje genérico.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting invoice");
        }
    }

    }



