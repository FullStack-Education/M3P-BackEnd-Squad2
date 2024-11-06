package projetofinal.com.labpcp.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import projetofinal.com.labpcp.controller.dto.request.LoginRequest;
import projetofinal.com.labpcp.controller.dto.response.LoginResponse;
import projetofinal.com.labpcp.entity.UsuarioEntity;
import projetofinal.com.labpcp.infra.exception.error.NotFoundException;
import projetofinal.com.labpcp.repository.UsuarioRepository;

import java.time.Instant;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements projetofinal.com.labpcp.service.AuthService {

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    private static long TEMPO_EXPIRACAO = 36000L; //contante de tempo de expiração em segundos

    public LoginResponse gerarToken(UsuarioEntity usuarioEntity) {

        Instant now = Instant.now();
        Long idUsuario = usuarioEntity.getId();

        String scope = usuarioEntity.getPerfil().toString();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("labPCP")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(TEMPO_EXPIRACAO))
                .subject(idUsuario.toString())
                .claim("scope", scope)
                .claim("userId", idUsuario)
                .build();

        String valorJWT = jwtEncoder.encode(
                        JwtEncoderParameters.from(claims)
                )
                .getTokenValue();

        return new LoginResponse(valorJWT, TEMPO_EXPIRACAO);
    }

    public String buscaCampoToken(String token, String claim) {

        String papel = jwtDecoder
                .decode(token)
                .getClaims()
                .get(claim)
                .toString();


        int startIndex = papel.indexOf("nome=") + 5;
        int endIndex = papel.indexOf(")", startIndex);
        return papel.substring(startIndex, endIndex);
    }
}
