package net.hasni.ensetdemospringangular.services;

import net.hasni.ensetdemospringangular.Exception.ApiNotFoundException;
import net.hasni.ensetdemospringangular.Exception.ApiRequestException;
import net.hasni.ensetdemospringangular.dto.PaymentDTO;
import net.hasni.ensetdemospringangular.entities.Payment;
import net.hasni.ensetdemospringangular.enums.PaymentStatus;
import net.hasni.ensetdemospringangular.enums.PaymentType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface PaymentService {

    Payment updatePaymentStatus (PaymentStatus statut, Long id);
    Payment savePayment(MultipartFile file, PaymentDTO paymentDTO) throws IOException;
    Payment updatePayment(PaymentDTO paymentDTO, Long id) throws ApiNotFoundException;
    byte[] getFilePayment (Long paymentId) throws ApiNotFoundException, IOException;
    void deletePayment(Long PaymentId);
    List<Payment> listPayments();
    List<Payment> getPaymentsByStudent(String code) throws ApiNotFoundException;
    List<Payment> getPaymentsByStatus(PaymentStatus status);
    List<Payment> getPaymentsByType(PaymentType type);
    Payment getPaymentByID(Long id) throws ApiNotFoundException;
}
