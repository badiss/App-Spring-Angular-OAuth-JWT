package net.hasni.ensetdemospringangular.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor @NoArgsConstructor @Getter @Setter @ToString @Builder
@Table(name = "Student_Informations")
public class StudentInformations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_student_informations")
    private Integer idInformations;
    private String address;
    private String city;
    private String email;
    @Column(name = "phone_Number")
    private String phoneNumber;
    private String classe;
}
