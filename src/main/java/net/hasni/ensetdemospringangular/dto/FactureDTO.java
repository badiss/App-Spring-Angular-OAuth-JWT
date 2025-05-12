package net.hasni.ensetdemospringangular.dto;

import lombok.*;
import net.hasni.ensetdemospringangular.entities.Payment;
import net.hasni.ensetdemospringangular.enums.FactureStatus;
import net.hasni.ensetdemospringangular.enums.PaymentStatus;
import net.hasni.ensetdemospringangular.enums.PaymentType;
import net.hasni.ensetdemospringangular.enums.TrimestreType;

import java.time.LocalDate;

@AllArgsConstructor @NoArgsConstructor @Getter @Setter @ToString @Builder
public class FactureDTO {
    private Long idFacture;
    private String code;
    private TrimestreType trimestre;
    private double montant;
    private FactureStatus status;
    private Payment payment;
}
