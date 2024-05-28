package net.hasni.ensetdemospringangular.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.*;
import net.hasni.ensetdemospringangular.entities.Student;
import net.hasni.ensetdemospringangular.enums.PaymentStatus;
import net.hasni.ensetdemospringangular.enums.PaymentType;

import java.time.LocalDate;

@AllArgsConstructor @NoArgsConstructor @Getter @Setter @ToString @Builder
public class PaymentDTO {
    //private Long id;
    private LocalDate date;
    private double amount;
    private PaymentType type;
    private PaymentStatus status;
    private String studentCode;
}
