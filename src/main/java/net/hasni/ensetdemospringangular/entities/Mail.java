package net.hasni.ensetdemospringangular.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;


@AllArgsConstructor @NoArgsConstructor @Getter @Setter @ToString @Builder
public class Mail {

    private String to;
    private String code;

}
