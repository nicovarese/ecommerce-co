package com.coder.ecommerce.controllers;

import com.coder.ecommerce.entities.Client;
import com.coder.ecommerce.entities.Product;
import com.coder.ecommerce.services.ProductService;
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
@RequestMapping("api/v1/products")
public class ProductController {

    private final ProductService service;

    @Autowired
    public ProductController(ProductService service){
        this.service = service;
    }


    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody Product data, BindingResult result) {
        try {
            // En el caso de que no venga desde el body los valores correspondiente,
            // la respuesta va a ser una BadRequest y avisará que campos son obligatorios.
            if (result.hasErrors()) {
                Map<String, String> errors = new HashMap<>();
                result.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
                return ResponseEntity.badRequest().body(errors);
            }
            // Lógica para guardar el cliente
            Product product = service.saveProduct(data);
            return ResponseEntity.status(HttpStatus.CREATED).body(product);
        } catch (Exception e) {
            // Log para debuggear el error.
            System.out.println("Error creating a product: " + e);
            // Retorno un mensaje genérico.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating a product. Please try again later.");
        }
    }

    //Agrego una ruta para que quede más clara la api que estoy obteniendo todos los clientes.
    @GetMapping("/all")
    public ResponseEntity<?> getAllProduct() {
        try {
            List<Product> products = service.getAllProducts();
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            /// Log para debuggear el error.
            System.out.println("Error retrieving products: " + e);
            // Retorno un mensaje genérico.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while retrieving products. Please try again later.");
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        try {
            Optional<Product> optProduct = service.getProductById(id);
            if (optProduct.isPresent()) {
                //En caso de que exista el cliente, retorna un 200 y devuelve el producto como respuesta.
                Product product = optProduct.get();
                return ResponseEntity.ok(product);
            } else {
                //Responde que no existe el invoice con ese ID
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product doesn't exist");
            }
        } catch (Exception e) {
            /// Log para debuggear el error.
            System.out.println(e);
            // Retorno un mensaje genérico.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving product");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable Long id) {
        try {
            service.deleteProductById(id);
            return ResponseEntity.ok("Product successfully deleted");
        } catch (Exception e) {
            /// Log para debuggear el error.
            System.out.println(e);
            // Retorno un mensaje genérico.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting product");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @Valid @RequestBody Product data, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }

        try {
            // Verifica si el producto existe
            Optional<Product> existingProduct = service.getProductById(id);
            if (!existingProduct.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
            }

            // Actualiza el producto
            Product product = service.updateProduct(id, data);
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            // Log para debuggear el error.
            System.out.println("Error updating a product: " + e);
            // Retorno un mensaje genérico.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the product. Please try again later.");
        }
    }

}
