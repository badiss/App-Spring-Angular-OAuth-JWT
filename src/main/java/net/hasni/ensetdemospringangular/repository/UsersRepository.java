package net.hasni.ensetdemospringangular.repository;

import net.hasni.ensetdemospringangular.entities.Student;
import net.hasni.ensetdemospringangular.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, String> {

    Users findByUsername(String username);
}
