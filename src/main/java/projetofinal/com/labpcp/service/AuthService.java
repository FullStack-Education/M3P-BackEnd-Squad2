package projetofinal.com.labpcp.service;

import projetofinal.com.labpcp.controller.dto.response.LoginResponse;
import projetofinal.com.labpcp.entity.UsuarioEntity;

public interface AuthService {
    String buscaCampoToken(String token, String claim);

    LoginResponse gerarToken(UsuarioEntity usuarioEntity);
}
