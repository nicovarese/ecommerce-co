package com.coder.ecommerce.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "invoice")
@NoArgsConstructor @ToString @EqualsAndHashCode
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter @Getter
    private Long id;

    @ManyToOne
    @JoinColumn (name = "client_id")
    @Getter @Setter
    private Client client;

    @Getter @Setter @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Getter @Setter
    private double total;

}
