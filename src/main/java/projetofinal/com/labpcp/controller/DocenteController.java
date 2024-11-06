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
import projetofinal.com.labpcp.controller.dto.request.DocenteRequest;
import projetofinal.com.labpcp.controller.dto.request.DocenteRequest;
import projetofinal.com.labpcp.controller.dto.response.DocenteResponse;
import projetofinal.com.labpcp.controller.dto.response.DocenteResponse;
import projetofinal.com.labpcp.infra.generic.GenericController;
import projetofinal.com.labpcp.service.DocenteService;

import java.util.List;

import static projetofinal.com.labpcp.infra.Util.AcessoUtil.verificarPermicao;

@RestController
@RequestMapping("docentes")
@Slf4j
public class DocenteController extends GenericController<DocenteService, DocenteResponse, DocenteRequest> {
    protected DocenteController() {
        super(List.of("administrador"));
    }

    @Operation(summary = "buscar docente por id do docente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "docente encontrado", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DocenteResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "docente não encontrado", content = @Content),
            @ApiResponse(responseCode = "401", description = "acesso não autorizado", content = @Content)
    })
    @Override
    @GetMapping("buscar/{id}")
    public ResponseEntity<DocenteResponse> buscarId(@Parameter(hidden = true)  @RequestHeader(name = "Authorization") String token, @PathVariable Long id) throws Exception {
        verificarPermicao(token, List.of("administrador", "docente", "aluno"));

        log.info("Buscando Docente com id {}", id);

        return ResponseEntity.status(200).body(service.buscarPorId(id));
    }

    @Operation(summary = "buscar todos os docentes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "docentes encontrados", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DocenteResponse.class))
            }),
            @ApiResponse(responseCode = "401", description = "acesso não autorizado", content = @Content)
    })
    @Override
    @GetMapping("buscar")
    public ResponseEntity<List<DocenteResponse>> buscarTodas(@Parameter(hidden = true) @RequestHeader(name = "Authorization") String token) {
        verificarPermicao(token, List.of("administrador", "docente", "aluno"));

        log.info("Buscando todos os Docentes");

        return ResponseEntity.status(200).body(service.buscarTodos());
    }

    @Operation(summary = "criar novo docente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "docente criado", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DocenteResponse.class))
            }),
            @ApiResponse(responseCode = "401", description = "acesso não autorizado", content = @Content)
    })
    @Override
    @PostMapping("criar")
    public ResponseEntity<DocenteResponse> criar(@Parameter(hidden = true) @RequestHeader(name = "Authorization") String token, @RequestBody DocenteRequest requestDto) {
        return super.criar(token, requestDto);
    }

    @Operation(summary = "deletar docente por id do docente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "docente deletado", content = @Content),
            @ApiResponse(responseCode = "404", description = "docente não encontrado", content = @Content),
            @ApiResponse(responseCode = "401", description = "acesso não autorizado", content = @Content)
    })
    @Override
    @DeleteMapping("deletar/{id}")
    public ResponseEntity<Object> deleter(@Parameter(hidden = true) @RequestHeader(name = "Authorization") String token, @PathVariable Long id) throws Exception {
        return super.deleter(token, id);
    }

    @Operation(summary = "atualizar docente por id do docente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "docente atualizado", content = @Content),
            @ApiResponse(responseCode = "404", description = "docente não encontrado", content = @Content),
            @ApiResponse(responseCode = "401", description = "acesso não autorizado", content = @Content)
    })
    @Override
    @PutMapping("atualizar/{id}")
    public ResponseEntity<Object> atualizar(@Parameter(hidden = true) @RequestHeader(name = "Authorization") String token, @RequestBody DocenteRequest requestDto, @PathVariable Long id) throws Exception {
        return super.atualizar(token, requestDto, id);
    }
    
}
