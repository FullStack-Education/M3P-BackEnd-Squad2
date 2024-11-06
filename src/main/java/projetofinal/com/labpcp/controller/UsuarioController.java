package projetofinal.com.labpcp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projetofinal.com.labpcp.controller.dto.request.CadastroRequest;
import projetofinal.com.labpcp.controller.dto.request.LoginRequest;
import projetofinal.com.labpcp.controller.dto.response.AlunoResponse;
import projetofinal.com.labpcp.controller.dto.response.CadastroResponse;
import projetofinal.com.labpcp.controller.dto.response.LoginResponse;
import projetofinal.com.labpcp.controller.dto.response.TurmaResponse;
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

    @Operation(summary = "cadastrar novo usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "usuário criada", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CadastroResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "usuário com esse e-mail já existe", content = @Content),
            @ApiResponse(responseCode = "404", description = "perfil não encontrado", content = @Content),
    })
    @PostMapping("cadastro")
    public ResponseEntity<CadastroResponse> cadastrarUsuario(@RequestHeader(name = "Authorization") String token, @RequestBody CadastroRequest cadastro) {
        verificarPermicao(token ,List.of("administrador"));

        log.info("cadastrando usuário");

        return ResponseEntity.status(201).body(service.cadastrarUsuario(cadastro));
    }

    @Operation(summary = "logar com usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "usuário logado", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LoginResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "senha ou e-mail inválidos", content = @Content),
    })
    @PostMapping ("login")
    public ResponseEntity<LoginResponse> logar(@RequestBody LoginRequest login) {

        log.info("logando usuário com email {}", login.email());

        return ResponseEntity.status(201).body(service.login(login));
    }
}
