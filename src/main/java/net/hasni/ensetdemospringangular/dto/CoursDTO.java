package net.hasni.ensetdemospringangular.dto;

import lombok.*;
import net.hasni.ensetdemospringangular.entities.Student;

import java.util.List;

@AllArgsConstructor @NoArgsConstructor @Getter @Setter @ToString @Builder
public class CoursDTO {
    private Integer idCours;
    private String titre;
    private String date;
    private String heure;
    private Boolean obligatoire;
    private List<Student> students;
}
