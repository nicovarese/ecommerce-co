package com.coder.ecommerce.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Entity
@Table(name = "clients")
@NoArgsConstructor @ToString @EqualsAndHashCode
public class Client {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    //Anotación con el mensaje en caso de que no se agregue el nombre al body
    @Getter @Setter @NotBlank(message = "Name is mandatory")
    private String name;

    @Getter @Setter @NotBlank(message = "Surname is mandatory")
    private String surname;

    @Getter @Setter @NotNull(message = "DNI is mandatory")
    private Integer dni;


    public Client(String name, String surname){
        this.name = name;
        this.surname = surname;
    }
}
