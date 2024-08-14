package net.hasni.ensetdemospringangular.servicesImpl;

import jakarta.transaction.Transactional;
import net.hasni.ensetdemospringangular.entities.Users;
import net.hasni.ensetdemospringangular.repository.UsersRepository;
import net.hasni.ensetdemospringangular.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UsersRepository usersRepository;

    public UserServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }
    @Override
    public Users ifUserExists(String username) {
        Users user = usersRepository.findByUsername(username);
        if (user == null) throw new RuntimeException();
        return user;
    }

    @Override
    public Users updateUserPassword(Users user, String mdp) {

        PasswordEncoder passwordEncoder = passwordEncoder1();

        if (user == null) throw new RuntimeException();
        user.setPassword(passwordEncoder.encode(mdp));
        usersRepository.save(user);
        return user;
    }

    public PasswordEncoder passwordEncoder1() {
        return new BCryptPasswordEncoder();
    }
}
