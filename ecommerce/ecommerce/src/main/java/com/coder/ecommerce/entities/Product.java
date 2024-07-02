package com.coder.ecommerce.entities;


import jakarta.persistence.*;
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

    @Getter @Setter
    private String code;

    @Getter @Setter
    private int stock;

    @Getter @Setter
    private double price;

}
