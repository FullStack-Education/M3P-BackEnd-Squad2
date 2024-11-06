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
import projetofinal.com.labpcp.controller.dto.request.CursoRequest;
import projetofinal.com.labpcp.controller.dto.request.CursoRequest;
import projetofinal.com.labpcp.controller.dto.response.CursoResponse;
import projetofinal.com.labpcp.controller.dto.response.CursoResponse;
import projetofinal.com.labpcp.controller.dto.response.MateriaResponse;
import projetofinal.com.labpcp.infra.generic.GenericController;
import projetofinal.com.labpcp.service.CursoService;

import java.util.List;

import static projetofinal.com.labpcp.infra.Util.AcessoUtil.verificarPermicao;


@RestController
@RequestMapping ("cursos")
@Slf4j
public class CursoController extends GenericController<CursoService, CursoResponse, CursoRequest> {

    private final CursoService service;


    protected CursoController(CursoService service) {
        super(List.of("administrador", "docente"));
        this.service = service;

    }

    @Operation(summary = "buscar matérias por id de curso")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "matéria encontrada ", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MateriaResponse.class))
            }),
    })
    @GetMapping("/{idCurso}/materias")
    public List<MateriaResponse> listarMateriasPorCurso(@PathVariable Long idCurso) {
        return service.listarMateriasPorCurso(idCurso);
    }

    @Operation(summary = "buscar curso por id do curso")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "curso encontrado", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CursoResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "curso não encontrado", content = @Content),
            @ApiResponse(responseCode = "401", description = "acesso não autorizado", content = @Content)
    })
    @Override
    @GetMapping("buscar/{id}")
    public ResponseEntity<CursoResponse> buscarId(@Parameter(hidden = true)  @RequestHeader(name = "Authorization") String token, @PathVariable Long id) throws Exception {
        verificarPermicao(token, List.of("administrador", "docente", "aluno"));

        log.info("Buscando curso com id {}", id);

        return ResponseEntity.status(200).body(service.buscarPorId(id));
    }

    @Operation(summary = "buscar todos os cursos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "cursos encontrados", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CursoResponse.class))
            }),
            @ApiResponse(responseCode = "401", description = "acesso não autorizado", content = @Content)
    })
    @Override
    @GetMapping("buscar")
    public ResponseEntity<List<CursoResponse>> buscarTodas(@Parameter(hidden = true) @RequestHeader(name = "Authorization") String token) {
        verificarPermicao(token, List.of("administrador", "docente", "aluno"));

        log.info("Buscando todos os cursos");

        return ResponseEntity.status(200).body(service.buscarTodos());
    }

    @Operation(summary = "criar novo curso")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "curso criado", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CursoResponse.class))
            }),
            @ApiResponse(responseCode = "401", description = "acesso não autorizado", content = @Content)
    })
    @Override
    @PostMapping("criar")
    public ResponseEntity<CursoResponse> criar(@Parameter(hidden = true) @RequestHeader(name = "Authorization") String token, @RequestBody CursoRequest requestDto) {
        return super.criar(token, requestDto);
    }

    @Operation(summary = "deletar curso por id do curso")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "curso deletado", content = @Content),
            @ApiResponse(responseCode = "404", description = "curso não encontrado", content = @Content),
            @ApiResponse(responseCode = "401", description = "acesso não autorizado", content = @Content)
    })
    @Override
    @DeleteMapping("deletar/{id}")
    public ResponseEntity<Object> deleter(@Parameter(hidden = true) @RequestHeader(name = "Authorization") String token, @PathVariable Long id) throws Exception {
        return super.deleter(token, id);
    }

    @Operation(summary = "atualizar curso por id do curso")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "curso atualizado", content = @Content),
            @ApiResponse(responseCode = "404", description = "curso não encontrado", content = @Content),
            @ApiResponse(responseCode = "401", description = "acesso não autorizado", content = @Content)
    })
    @Override
    @PutMapping("atualizar/{id}")
    public ResponseEntity<Object> atualizar(@Parameter(hidden = true) @RequestHeader(name = "Authorization") String token, @RequestBody CursoRequest requestDto, @PathVariable Long id) throws Exception {
        return super.atualizar(token, requestDto, id);
    }

}
