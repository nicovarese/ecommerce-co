package com.coder.ecommerce.services;

import com.coder.ecommerce.entities.Client;
import com.coder.ecommerce.repositories.ClientsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientsService {

    // Declaración del campo que será inyectado y es final para inmutabilidad
    private final ClientsRepository repository;

    // Constructor donde se inyecta la dependencia
    @Autowired
    public ClientsService (ClientsRepository repository){
        this.repository = repository;
    }

    public Client saveClient(Client client){
        return repository.save(client);
    }

    public List<Client> getAllClients(){
        return repository.findAll();
    }

    public Optional<Client> getClientById(Long id){
        return repository.findById(id);
    }

    public void deleteClientById(Long id){
        repository.deleteById(id);
    }
}
