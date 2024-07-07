package com.coder.ecommerce.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Entity
@Table (name = "products")
@NoArgsConstructor @ToString @EqualsAndHashCode
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @Getter @Setter
    private String description;

    @Getter @Setter @NotBlank(message = "No ingresó el código del producto")
    private String code;

    @Getter @Setter @NotNull(message = "No ingresó el stock del producto.")
    private int stock;

    @Getter @Setter @NotNull(message = "No ingresó el precio del producto.")
    private double price;

}
