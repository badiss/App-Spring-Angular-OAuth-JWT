package net.hasni.ensetdemospringangular.repository;

import net.hasni.ensetdemospringangular.entities.Facture;
import net.hasni.ensetdemospringangular.entities.Payment;
import net.hasni.ensetdemospringangular.entities.StudentInformations;
import net.hasni.ensetdemospringangular.enums.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FactureRepository extends JpaRepository<Facture, Integer> {

    List<Facture> findByPaymentId (Long id);

}
