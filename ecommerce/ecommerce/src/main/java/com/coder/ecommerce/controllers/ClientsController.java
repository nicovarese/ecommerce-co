package com.coder.ecommerce.controllers;

import com.coder.ecommerce.entities.Client;
import com.coder.ecommerce.services.ClientsService;
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
@RequestMapping("api/v1/clients")
public class ClientsController {

    // Declaración del campo que será inyectado y es final para inmutabilidad
    private final ClientsService service;

    // Constructor donde se inyecta la dependencia
    @Autowired
    public ClientsController(ClientsService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> createClient(@Valid @RequestBody Client client, BindingResult result) {
        try {
            // En el caso de que no venga desde el body los valores correspondiente,
            // la respuesta va a ser una BadRequest y avisará que campos son obligatorios.
            if (result.hasErrors()) {
                Map<String, String> errors = new HashMap<>();
                result.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
                return ResponseEntity.badRequest().body(errors);
            }
            // Lógica para guardar el cliente
            return ResponseEntity.status(HttpStatus.CREATED).body(client);
        } catch (Exception e) {
            // Log para debuggear el error.
            System.out.println("Error creating a client: " + e);
            // Retorno un mensaje genérico.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating a client. Please try again later.");
        }
    }

    //Agrego una ruta para que quede más clara la api que estoy obteniendo todos los clientes.
    @GetMapping("/all")
    public ResponseEntity<?> getAllClients() {
        try {
            List<Client> clients = service.getAllClients();
            return ResponseEntity.ok(clients);
        } catch (Exception e) {
            /// Log para debuggear el error.
            System.out.println("Error retrieving clients: " + e);
            // Retorno un mensaje genérico.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while retrieving clients. Please try again later.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getClientById(@PathVariable Long id) {
        try {
            Optional<Client> optClient = service.getClientById(id);
            if (optClient.isPresent()) {
                //En caso de que exista el cliente, retorna un 200 y devuelve el cliente como respuesta.
                Client client = optClient.get();
                return ResponseEntity.ok(client);
            } else {
                //Responde que no existe el cliente con ese ID
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client doesn't exist");
            }
        } catch (Exception e) {
            /// Log para debuggear el error.
            System.out.println(e);
            // Retorno un mensaje genérico.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving client");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClientById(@PathVariable Long id) {
        try {
            service.deleteClientById(id);
            return ResponseEntity.ok("Cliente borrado exitosamente");
        } catch (Exception e) {
            /// Log para debuggear el error.
            System.out.println(e);
            // Retorno un mensaje genérico.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting client");
        }
    }
}
