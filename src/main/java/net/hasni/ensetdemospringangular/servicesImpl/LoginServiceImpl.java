package net.hasni.ensetdemospringangular.servicesImpl;

import jakarta.transaction.Transactional;
import net.hasni.ensetdemospringangular.services.LoginService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class LoginServiceImpl implements LoginService {

    private AuthenticationManager authenticationManager;
    private org.springframework.security.oauth2.jwt.JwtEncoder JwtEncoder;

    public LoginServiceImpl(AuthenticationManager authenticationManager, JwtEncoder JwtEncoder) {
        this.authenticationManager = authenticationManager;
        this.JwtEncoder = JwtEncoder;
    }

    public Map<String, String> loginUser (String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        // générer le JWT
        Instant instant = Instant.now();
        //Récupérer les roles de user authentifier
        String scope = authentication.getAuthorities().stream().map(a->a.getAuthority()).collect(Collectors.joining(" "));
        JwtClaimsSet jwtClaimsSet=JwtClaimsSet.builder()
                .issuedAt(instant)
                .expiresAt(instant.plus(10, ChronoUnit.MINUTES))
                .subject(username)
                .claim("scope", scope)
                .build();

        JwtEncoderParameters jwtEncoderParameters =
                JwtEncoderParameters.from(
                        JwsHeader.with(MacAlgorithm.HS512).build(),
                        jwtClaimsSet
                );
        String jwt = JwtEncoder.encode(jwtEncoderParameters).getTokenValue();
        return Map.of("access-token", jwt);
    }

}
