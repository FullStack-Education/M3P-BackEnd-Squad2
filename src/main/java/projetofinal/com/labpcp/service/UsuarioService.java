package projetofinal.com.labpcp.service;

import projetofinal.com.labpcp.controller.dto.request.CadastroRequest;
import projetofinal.com.labpcp.controller.dto.response.CadastroResponse;

public interface UsuarioService {
    CadastroResponse cadastrarUsuario(CadastroRequest cadastroRequest);
}
