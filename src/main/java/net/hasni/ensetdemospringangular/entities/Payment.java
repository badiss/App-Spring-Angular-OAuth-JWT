package net.hasni.ensetdemospringangular.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import net.hasni.ensetdemospringangular.enums.PaymentStatus;
import net.hasni.ensetdemospringangular.enums.PaymentType;
import org.apache.catalina.LifecycleState;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor @NoArgsConstructor @Getter @Setter @ToString @Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private LocalDate date;
    @Column(nullable = false)
    private double amount;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentType type;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;
    private String file;
    @ManyToOne @JoinColumn( name="studentId", nullable=false)
    // Chaque étudient à plusieurs paiement
    private Student student;
    @OneToMany(targetEntity=Facture.class, mappedBy = "payment", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Facture> factures = new ArrayList<>();
}
