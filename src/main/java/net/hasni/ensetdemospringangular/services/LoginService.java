package net.hasni.ensetdemospringangular.services;

import java.util.Map;

public interface LoginService {

    Map<String, String> loginUser (String username, String password);
}
