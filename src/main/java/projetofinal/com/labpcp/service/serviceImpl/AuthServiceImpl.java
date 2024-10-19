package projetofinal.com.labpcp.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements projetofinal.com.labpcp.service.AuthService {

    private final JwtDecoder jwtDecoder;

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
