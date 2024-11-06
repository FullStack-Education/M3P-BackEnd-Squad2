package projetofinal.com.labpcp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projetofinal.com.labpcp.controller.dto.request.TurmaRequest;
import projetofinal.com.labpcp.controller.dto.request.TurmaRequest;
import projetofinal.com.labpcp.controller.dto.response.AlunoResponse;
import projetofinal.com.labpcp.controller.dto.response.TurmaResponse;
import projetofinal.com.labpcp.controller.dto.response.TurmaResponse;
import projetofinal.com.labpcp.infra.generic.GenericController;
import projetofinal.com.labpcp.service.TurmaService;

import java.util.List;

import static projetofinal.com.labpcp.infra.Util.AcessoUtil.verificarPermicao;

@RestController
@RequestMapping("turmas")
@Slf4j
public class TurmaController extends GenericController<TurmaService, TurmaResponse, TurmaRequest> {

    public TurmaController() {
        super(List.of("administrador", "docente"));

    }

    @Operation(summary = "buscar turma por id da turma")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "turma encontrada", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AlunoResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "turma não encontrada", content = @Content),
            @ApiResponse(responseCode = "401", description = "acesso não autorizado", content = @Content)
    })
    @Override
    @GetMapping("buscar/{id}")
    public ResponseEntity<TurmaResponse> buscarId(@RequestHeader(name = "Authorization") String token, @PathVariable Long id) throws Exception {
        verificarPermicao(token, List.of("administrador", "docente", "aluno"));

        log.info("Buscando turma com id {}", id);

        return ResponseEntity.status(200).body(service.buscarPorId(id));
    }

    @Operation(summary = "buscar todos as turmas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "turmas encontradas", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AlunoResponse.class))
            }),
            @ApiResponse(responseCode = "401", description = "acesso não autorizado", content = @Content)
    })
    @Override
    @GetMapping("buscar")
    public ResponseEntity<List<TurmaResponse>> buscarTodas(@RequestHeader(name = "Authorization") String token) {
        verificarPermicao(token, List.of("administrador", "docente", "aluno"));

        log.info("Buscando todos as Turmas");

        return ResponseEntity.status(200).body(service.buscarTodos());
    }
    @Operation(summary = "criar nova turma")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "turma criada", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TurmaResponse.class))
            }),
            @ApiResponse(responseCode = "401", description = "acesso não autorizado", content = @Content)
    })
    @Override
    @PostMapping("criar")
    public ResponseEntity<TurmaResponse> criar(@Parameter(hidden = true) @RequestHeader(name = "Authorization") String token, @RequestBody TurmaRequest requestDto) {
        return super.criar(token, requestDto);
    }

    @Operation(summary = "deletar turma por id da turma")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "turma deletada", content = @Content),
            @ApiResponse(responseCode = "404", description = "turma não encontrada", content = @Content),
            @ApiResponse(responseCode = "401", description = "acesso não autorizado", content = @Content)
    })
    @Override
    @DeleteMapping("deletar/{id}")
    public ResponseEntity<Object> deleter(@Parameter(hidden = true) @RequestHeader(name = "Authorization") String token, @PathVariable Long id) throws Exception {
        return super.deleter(token, id);
    }

    @Operation(summary = "atualizar turma por id da turma")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "turma atualizada", content = @Content),
            @ApiResponse(responseCode = "404", description = "turma não encontrada", content = @Content),
            @ApiResponse(responseCode = "401", description = "acesso não autorizado", content = @Content)
    })
    @Override
    @PutMapping("atualizar/{id}")
    public ResponseEntity<Object> atualizar(@Parameter(hidden = true) @RequestHeader(name = "Authorization") String token, @RequestBody TurmaRequest requestDto, @PathVariable Long id) throws Exception {
        return super.atualizar(token, requestDto, id);
    }

}
