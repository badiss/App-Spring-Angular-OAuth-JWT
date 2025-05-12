package net.hasni.ensetdemospringangular.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import net.hasni.ensetdemospringangular.enums.FactureStatus;
import net.hasni.ensetdemospringangular.enums.PaymentStatus;
import net.hasni.ensetdemospringangular.enums.PaymentType;
import net.hasni.ensetdemospringangular.enums.TrimestreType;

import java.time.LocalDate;

@Entity
@AllArgsConstructor @NoArgsConstructor @Getter @Setter @ToString @Builder
public class Facture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_facture")
    private Long idFacture;
    @Column(nullable = false)
    private String code;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TrimestreType trimestre;
    @Column(nullable = false)
    private double montant;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FactureStatus status;
    @ManyToOne @JoinColumn( name="id_payment", nullable=false)
    private Payment payment;
}
