package net.hasni.ensetdemospringangular.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor @NoArgsConstructor @Getter @Setter @ToString @Builder
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Integer studentId;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(unique = true, nullable = false)
    private String code;
    @Column(name = "program_id", nullable = false)
    private String programId;
    private String photo;
    @OneToOne  @JoinColumn( name="id_student_informations", nullable=false )
    private StudentInformations studentInformations;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable( name = "Student_Cours_Association",
            joinColumns = @JoinColumn( name = "student_id" ),
            inverseJoinColumns = @JoinColumn( name = "id_cours" ) )
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Cours> cours = new ArrayList<>();

}
