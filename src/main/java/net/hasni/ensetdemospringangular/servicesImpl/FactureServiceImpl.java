package net.hasni.ensetdemospringangular.servicesImpl;

import jakarta.transaction.Transactional;
import net.hasni.ensetdemospringangular.Exception.ApiNotFoundException;
import net.hasni.ensetdemospringangular.Exception.ApiRequestException;
import net.hasni.ensetdemospringangular.dto.FactureDTO;
import net.hasni.ensetdemospringangular.entities.Facture;
import net.hasni.ensetdemospringangular.entities.Payment;
import net.hasni.ensetdemospringangular.enums.FactureStatus;
import net.hasni.ensetdemospringangular.enums.PaymentStatus;
import net.hasni.ensetdemospringangular.repository.FactureRepository;
import net.hasni.ensetdemospringangular.repository.PaymentRepository;
import net.hasni.ensetdemospringangular.services.FactureService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class FactureServiceImpl implements FactureService {

    private FactureRepository factureRepository;
    private PaymentRepository paymentRepository;

    public FactureServiceImpl(FactureRepository factureRepository) {

        this.factureRepository = factureRepository;
        this.paymentRepository = paymentRepository;
    }
    @Override
    public List<Facture> listFacture() {
        return factureRepository.findAll();
    }

    @Override
    public List<Facture> getFacturesByPayment(Long paymentId) throws ApiNotFoundException {
        return factureRepository.findByPaymentId(paymentId);
    }

    @Override
    public void updateFactures(List<FactureDTO> listDto) throws ApiNotFoundException {

        double somme = 0;

        for(FactureDTO item: listDto) {
            Facture facture = factureRepository.findById(Math.toIntExact(item.getIdFacture())).orElseThrow(() -> new ApiNotFoundException("Pas de facture avec cet id"));
            facture.setStatus(item.getStatus());
            factureRepository.save(facture);
            somme += item.getMontant();
        }

        Payment payment = listDto.get(0).getPayment();
        if (payment == null) {
            throw new ApiNotFoundException("Il n'y a pas un payment pour ces factures");
        } else {
            payment.setAmount(somme);
            paymentRepository.save(payment);
        }


    }

}
