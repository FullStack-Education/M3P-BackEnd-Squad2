package projetofinal.com.labpcp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projetofinal.com.labpcp.controller.dto.request.CadastroRequest;
import projetofinal.com.labpcp.controller.dto.response.CadastroResponse;
import projetofinal.com.labpcp.service.UsuarioService;

import java.util.Arrays;
import java.util.List;

import static projetofinal.com.labpcp.infra.Util.AcessoUtil.verificarPermicao;

@Slf4j
@RestController
@RequestMapping("usuarios")
public class UsuarioController {


    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping("cadastro")
    public ResponseEntity<CadastroResponse> cadastrarUsuario(@RequestBody CadastroRequest cadastro) {


        log.info("cadastrando usuário");

        return ResponseEntity.status(201).body(service.cadastrarUsuario(cadastro));
    }
}
