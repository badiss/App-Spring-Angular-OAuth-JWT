package net.hasni.ensetdemospringangular.entities;

import jakarta.persistence.*;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor @NoArgsConstructor @Getter @Setter @ToString @Builder
@Table(name = "cours")
public class Cours {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cours")
    private Integer idCours;
    @Column(nullable = false)
    private String titre;
    @Column(nullable = false)
    private String date;
    @Column(nullable = false)
    private String heure;
    @Column(name = "is_obligatoire", nullable = false)
    private Boolean obligatoire;
    /* @ManyToMany(fetch = FetchType.LAZY)
     @JoinTable( name = "Student_Cours_Association",
             joinColumns = @JoinColumn( name = "id_cours" ),
             inverseJoinColumns = @JoinColumn( name = "student_id" ) )*/
    @ManyToMany(mappedBy = "cours" ,fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Student> students = new ArrayList<>();
}
