package net.hasni.ensetdemospringangular.services;

import net.hasni.ensetdemospringangular.entities.Mail;

public interface EmailService {

    void sendPasswordByCode(Mail mail);
}
