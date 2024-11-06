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
import projetofinal.com.labpcp.controller.dto.request.MateriaRequest;
import projetofinal.com.labpcp.controller.dto.request.MateriaRequest;
import projetofinal.com.labpcp.controller.dto.response.AlunoResponse;
import projetofinal.com.labpcp.controller.dto.response.MateriaResponse;
import projetofinal.com.labpcp.controller.dto.response.MateriaResponse;
import projetofinal.com.labpcp.controller.dto.response.TurmaResponse;
import projetofinal.com.labpcp.infra.generic.GenericController;
import projetofinal.com.labpcp.service.MateriaService;

import java.util.List;

import static projetofinal.com.labpcp.infra.Util.AcessoUtil.verificarPermicao;

@RestController
@RequestMapping("materias")
@Slf4j
public class MateriaController extends GenericController<MateriaService, MateriaResponse, MateriaRequest> {
    protected MateriaController() {
        super(List.of("administrador", "docente"));
    }

    @Operation(summary = "buscar matéria por id da matéria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "matéria encontrada", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AlunoResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "matéria não encontrada", content = @Content),
            @ApiResponse(responseCode = "401", description = "acesso não autorizado", content = @Content)
    })
    @Override
    @GetMapping("buscar/{id}")
    public ResponseEntity<MateriaResponse> buscarId(@Parameter(hidden = true) @RequestHeader(name = "Authorization") String token, @PathVariable Long id) throws Exception {
        verificarPermicao(token, List.of("administrador", "docente", "aluno"));

        log.info("Buscando matéria com id {}", id);

        return ResponseEntity.status(200).body(service.buscarPorId(id));
    }

    @Operation(summary = "buscar todos as matérias")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "matérias encontradas", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AlunoResponse.class))
            }),
            @ApiResponse(responseCode = "401", description = "acesso não autorizado", content = @Content)
    })
    @Override
    @GetMapping("buscar")
    public ResponseEntity<List<MateriaResponse>> buscarTodas(@Parameter(hidden = true) @RequestHeader(name = "Authorization") String token) {
        verificarPermicao(token, List.of("administrador", "docente", "aluno"));

        log.info("Buscando todos as Matérias");

        return ResponseEntity.status(200).body(service.buscarTodos());
    }

    @Operation(summary = "criar nova matéria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "matéria criada", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MateriaResponse.class))
            }),
            @ApiResponse(responseCode = "401", description = "acesso não autorizado", content = @Content)
    })
    @Override
    @PostMapping("criar")
    public ResponseEntity<MateriaResponse> criar(@Parameter(hidden = true) @RequestHeader(name = "Authorization") String token, @RequestBody MateriaRequest requestDto) {
        return super.criar(token, requestDto);
    }

    @Operation(summary = "deletar matéria por id da matéria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "matéria deletada", content = @Content),
            @ApiResponse(responseCode = "404", description = "matéria não encontrada", content = @Content),
            @ApiResponse(responseCode = "401", description = "acesso não autorizado", content = @Content)
    })
    @Override
    @DeleteMapping("deletar/{id}")
    public ResponseEntity<Object> deleter(@Parameter(hidden = true) @RequestHeader(name = "Authorization") String token, @PathVariable Long id) throws Exception {
        return super.deleter(token, id);
    }

    @Operation(summary = "atualizar matéria por id da matéria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "matéria atualizada", content = @Content),
            @ApiResponse(responseCode = "404", description = "matéria não encontrada", content = @Content),
            @ApiResponse(responseCode = "401", description = "acesso não autorizado", content = @Content)
    })
    @Override
    @PutMapping("atualizar/{id}")
    public ResponseEntity<Object> atualizar(@Parameter(hidden = true) @RequestHeader(name = "Authorization") String token, @RequestBody MateriaRequest requestDto, @PathVariable Long id) throws Exception {
        return super.atualizar(token, requestDto, id);
    }

}
