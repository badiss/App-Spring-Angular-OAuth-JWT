package net.hasni.ensetdemospringangular.services;

import net.hasni.ensetdemospringangular.entities.Users;

public interface UserService {

    Users ifUserExists(String username);

    Users updateUserPassword(Users user, String mdp);
}
