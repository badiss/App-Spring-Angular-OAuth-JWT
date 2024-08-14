package net.hasni.ensetdemospringangular.util;

import jakarta.persistence.Entity;
import lombok.*;

import java.rmi.server.UID;
import java.util.UUID;

public class UserCode {

    public static String getCode() {
        return UUID.randomUUID().toString();
    }


}
