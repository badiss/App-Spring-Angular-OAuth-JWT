package net.hasni.ensetdemospringangular.services;

import net.hasni.ensetdemospringangular.Exception.ApiNotFoundException;
import net.hasni.ensetdemospringangular.Exception.ApiRequestException;
import net.hasni.ensetdemospringangular.dto.FactureDTO;
import net.hasni.ensetdemospringangular.dto.PaymentDTO;
import net.hasni.ensetdemospringangular.entities.Facture;
import net.hasni.ensetdemospringangular.entities.Payment;

import java.util.List;

public interface FactureService {

    List<Facture> listFacture();
    List<Facture> getFacturesByPayment(Long paymentId) throws ApiNotFoundException;
    void updateFactures(List<FactureDTO> listDto) throws ApiNotFoundException;
}
