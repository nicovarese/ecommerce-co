package com.coder.ecommerce.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "invoice")
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@Setter
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    @NotNull(message = "Client is mandatory")
    private Client client;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    //No es null por que se va a actualizar solo con el Invoice Details.
    private double total;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference
    private List<InvoiceDetails> invoiceDetails;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        calculateTotal();
    }

    @PreUpdate
    public void preUpdate() {
        calculateTotal();
    }

    private void calculateTotal() {
        if (invoiceDetails != null && !invoiceDetails.isEmpty()) {
            this.total = invoiceDetails.stream()
                    .mapToDouble(InvoiceDetails::getPrice)
                    .sum();
        } else {
            this.total = 0;
        }
    }
}
