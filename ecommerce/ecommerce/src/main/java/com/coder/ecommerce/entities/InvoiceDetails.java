package com.coder.ecommerce.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
    @JoinColumn(name = "invoice_id")
    @NotNull(message = "Invoice is mandatory")
    @Getter @Setter
    @JsonBackReference
    private Invoice invoice;

    @Getter @Setter
    @NotNull(message = "Amount is mandatory")
    private Integer amount;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @NotNull(message = "Product is mandatory")
    @Getter @Setter
    private Product product;

    @Getter @Setter
    @NotNull(message = "Price is mandatory")
    private Double price;
}
