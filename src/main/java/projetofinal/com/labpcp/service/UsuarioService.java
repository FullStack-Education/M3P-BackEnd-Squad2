package projetofinal.com.labpcp.service;

import projetofinal.com.labpcp.controller.dto.request.CadastroRequest;
import projetofinal.com.labpcp.controller.dto.request.LoginRequest;
import projetofinal.com.labpcp.controller.dto.response.CadastroResponse;
import projetofinal.com.labpcp.controller.dto.response.LoginResponse;

public interface UsuarioService {
    CadastroResponse cadastrarUsuario(CadastroRequest cadastroRequest);
    LoginResponse login(LoginRequest loginRequest);

}
