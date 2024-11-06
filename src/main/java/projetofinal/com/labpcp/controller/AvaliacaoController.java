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
import projetofinal.com.labpcp.controller.dto.request.AvaliacaoRequest;
import projetofinal.com.labpcp.controller.dto.request.AvaliacaoRequest;
import projetofinal.com.labpcp.controller.dto.response.AlunoResponse;
import projetofinal.com.labpcp.controller.dto.response.AvaliacaoResponse;
import projetofinal.com.labpcp.controller.dto.response.AvaliacaoResponse;
import projetofinal.com.labpcp.infra.generic.GenericController;
import projetofinal.com.labpcp.service.AvaliacaoService;

import java.util.List;

import static projetofinal.com.labpcp.infra.Util.AcessoUtil.verificarPermicao;

@RestController
@RequestMapping("notas")
@Slf4j
public class AvaliacaoController extends GenericController<AvaliacaoService, AvaliacaoResponse, AvaliacaoRequest> {
    protected AvaliacaoController() {
        super(List.of("administrador", "docente"));
    }

    @Operation(summary = "buscar avaliação por id da avaliação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "avaliação encontrada", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AlunoResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "avaliação não encontrada", content = @Content),
            @ApiResponse(responseCode = "401", description = "acesso não autorizado", content = @Content)
    })
    @Override
    @GetMapping("buscar/{id}")
    public ResponseEntity<AvaliacaoResponse> buscarId(@Parameter(hidden = true) @RequestHeader(name = "Authorization") String token, @PathVariable Long id) throws Exception {
        verificarPermicao(token, List.of("administrador", "docente", "aluno"));

        log.info("Buscando Avaliacao com id {}", id);

        return ResponseEntity.status(200).body(service.buscarPorId(id));
    }

    @Operation(summary = "buscar todos as avaliações")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "avaliações encontradas", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AlunoResponse.class))
            }),
            @ApiResponse(responseCode = "401", description = "acesso não autorizado", content = @Content)
    })
    @Override
    @GetMapping("buscar")
    public ResponseEntity<List<AvaliacaoResponse>> buscarTodas(@Parameter(hidden = true) @RequestHeader(name = "Authorization") String token) {
        verificarPermicao(token, List.of("administrador", "docente", "aluno"));

        log.info("Buscando todos os Avaliacaos");

        return ResponseEntity.status(200).body(service.buscarTodos());
    }

    @Operation(summary = "criar nova avaliação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "avaliação criada", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AvaliacaoResponse.class))
            }),
            @ApiResponse(responseCode = "401", description = "acesso não autorizado", content = @Content)
    })
    @Override
    @PostMapping("criar")
    public ResponseEntity<AvaliacaoResponse> criar(@Parameter(hidden = true) @RequestHeader(name = "Authorization") String token, @RequestBody AvaliacaoRequest requestDto) {
        return super.criar(token, requestDto);
    }

    @Operation(summary = "deletar avaliação por id da avaliação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "avaliação deletada", content = @Content),
            @ApiResponse(responseCode = "404", description = "avaliação não encontrada", content = @Content),
            @ApiResponse(responseCode = "401", description = "acesso não autorizado", content = @Content)
    })
    @Override
    @DeleteMapping("deletar/{id}")
    public ResponseEntity<Object> deleter(@Parameter(hidden = true) @RequestHeader(name = "Authorization") String token, @PathVariable Long id) throws Exception {
        return super.deleter(token, id);
    }

    @Operation(summary = "atualizar avaliação por id da avaliação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "avaliação atualizada", content = @Content),
            @ApiResponse(responseCode = "404", description = "avaliação não encontrada", content = @Content),
            @ApiResponse(responseCode = "401", description = "acesso não autorizado", content = @Content)
    })
    @Override
    @PutMapping("atualizar/{id}")
    public ResponseEntity<Object> atualizar(@Parameter(hidden = true) @RequestHeader(name = "Authorization") String token, @RequestBody AvaliacaoRequest requestDto, @PathVariable Long id) throws Exception {
        return super.atualizar(token, requestDto, id);
    }

}
