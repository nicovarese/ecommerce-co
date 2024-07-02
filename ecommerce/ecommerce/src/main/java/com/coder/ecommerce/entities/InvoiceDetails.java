package com.coder.ecommerce.entities;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "invoice_details")
@NoArgsConstructor @ToString @EqualsAndHashCode
public class InvoiceDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @ManyToOne
    @JoinColumn (name = "invoice_id")
    @Getter @Setter
    private Invoice invoice;

    @Getter @Setter
    private int amount;

    @ManyToOne
    @JoinColumn (name = "product_id")
    @Getter @Setter
    private Product product;

    @Getter @Setter
    private double price;

}
