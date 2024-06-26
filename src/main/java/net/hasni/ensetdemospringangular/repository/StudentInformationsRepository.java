package net.hasni.ensetdemospringangular.repository;

import net.hasni.ensetdemospringangular.entities.StudentInformations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentInformationsRepository extends JpaRepository<StudentInformations, Integer> {

}
