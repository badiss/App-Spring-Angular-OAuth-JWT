package net.hasni.ensetdemospringangular.repository;

import net.hasni.ensetdemospringangular.entities.Cours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoursRepository extends JpaRepository<Cours, Integer> {

}
