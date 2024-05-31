package net.hasni.ensetdemospringangular.repository;

import net.hasni.ensetdemospringangular.entities.StudentInformations;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StudentInformationsRepository extends JpaRepository<StudentInformations, Integer> {

}
