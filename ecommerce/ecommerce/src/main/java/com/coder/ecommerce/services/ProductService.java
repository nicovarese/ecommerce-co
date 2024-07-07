package com.coder.ecommerce.services;

import com.coder.ecommerce.entities.Product;
import com.coder.ecommerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    //Inyección de repositorio a través de una instancia de
    //ProductService para mantener Inmutabilidad
    private final ProductRepository repository;

    @Autowired
    public ProductService (ProductRepository repository){
        this.repository= repository;
    }

    //Métodos para la lógica de Productos.
    public Product saveProduct(Product product){
        return repository.save(product);
    }

    public List<Product> getAllProducts(){
        return repository.findAll();
    }

    public Optional<Product> getProductById(Long id){
        return repository.findById(id);
    }

    public void deleteProductById(Long id){
        repository.deleteById(id);
    }

}
