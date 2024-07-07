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
@RequestMapping (name = "api/v1/invoices")
public class InvoiceController {

    private final InvoiceService service;

    @Autowired
    public InvoiceController(InvoiceService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> saveInvoice(@Valid @RequestBody Invoice data, BindingResult result){
        try {
        // En el caso de que no venga desde el body los valores correspondiente,
        // la respuesta va a ser una BadRequest y avisará que campos son obligatorios.
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }
        // Lógica para guardar el detalle de comprobante
        Invoice invoice = service.saveInvoice(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(invoice);
        } catch (Exception e) {
        // Log para debuggear el error.
        System.out.println("Error creating an Invoice: " + e);
        // Retorno un mensaje genérico.
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating a Invoice. Please try again later.");
        }
    }

    //Agrego una ruta para que quede más clara la api que estoy obteniendo todos los clientes.
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



