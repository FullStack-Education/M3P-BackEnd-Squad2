package projetofinal.com.labpcp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projetofinal.com.labpcp.controller.dto.request.CadastroRequest;
import projetofinal.com.labpcp.controller.dto.request.LoginRequest;
import projetofinal.com.labpcp.controller.dto.response.CadastroResponse;
import projetofinal.com.labpcp.controller.dto.response.LoginResponse;
import projetofinal.com.labpcp.service.UsuarioService;

import java.util.List;

import static projetofinal.com.labpcp.infra.Util.AcessoUtil.verificarPermicao;

@Slf4j
@RestController
@RequestMapping("")
public class UsuarioController {


    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping("cadastro")
    public ResponseEntity<CadastroResponse> cadastrarUsuario(@RequestHeader(name = "Authorization") String token, @RequestBody CadastroRequest cadastro) {
        verificarPermicao(token ,List.of("administrador"));

        log.info("cadastrando usuário");

        return ResponseEntity.status(201).body(service.cadastrarUsuario(cadastro));
    }

    @PostMapping ("login")
    public ResponseEntity<LoginResponse> logar(@RequestBody LoginRequest login) {

        log.info("logando usuário com email {}", login.email());

        return ResponseEntity.status(201).body(service.login(login));
    }
}
