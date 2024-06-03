package net.hasni.ensetdemospringangular.repository;

import net.hasni.ensetdemospringangular.entities.Payment;
import net.hasni.ensetdemospringangular.enums.PaymentStatus;
import net.hasni.ensetdemospringangular.enums.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByStudentCode(String code);
    List<Payment> findByStatus (PaymentStatus status);
    List<Payment> findByType (PaymentType type);
}
