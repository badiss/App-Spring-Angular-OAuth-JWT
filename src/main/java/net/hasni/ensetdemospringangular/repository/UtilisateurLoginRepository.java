package net.hasni.ensetdemospringangular.repository;

import net.hasni.ensetdemospringangular.entities.UtilisateurLogin;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UtilisateurLoginRepository extends JpaRepository<UtilisateurLogin, Long> {

    UtilisateurLogin findByUsername(String username);
}
