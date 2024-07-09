package com.coder.ecommerce.repositories;

import com.coder.ecommerce.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
//Le paso la entidad que necesitamos para el repositorio, y el tipo de dato del ID
public interface ClientRepository extends JpaRepository<Client, Long> {
}
