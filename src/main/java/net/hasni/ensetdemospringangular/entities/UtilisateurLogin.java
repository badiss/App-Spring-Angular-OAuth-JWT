package net.hasni.ensetdemospringangular.entities;

import jakarta.persistence.*;
import lombok.*;
import net.hasni.ensetdemospringangular.enums.RoleType;

import java.util.List;

@Entity
@AllArgsConstructor @NoArgsConstructor @Getter @Setter @ToString @Builder
public class UtilisateurLogin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private List<RoleType> roles;
    private String username;

}
