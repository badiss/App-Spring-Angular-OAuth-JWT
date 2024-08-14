package net.hasni.ensetdemospringangular.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder

public class ResetPasswordDTO {

    private String user;
    private String email;
}
