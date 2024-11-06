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
import projetofinal.com.labpcp.controller.dto.request.AlunoRequest;
import projetofinal.com.labpcp.controller.dto.response.AlunoResponse;
import projetofinal.com.labpcp.controller.dto.response.AvaliacaoResponse;
import projetofinal.com.labpcp.infra.generic.GenericController;
import projetofinal.com.labpcp.service.AlunoService;

import java.util.List;

import static projetofinal.com.labpcp.infra.Util.AcessoUtil.verificarPermicao;

@RestController
@RequestMapping("alunos")
@Slf4j
public class AlunoController extends GenericController<AlunoService, AlunoResponse, AlunoRequest> {
    protected AlunoController() {
        super(List.of("administrador"));
    }

    @Operation(summary = "buscar aluno por id do aluno")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "aluno encontrado", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AlunoResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "aluno não encontrado", content = @Content),
            @ApiResponse(responseCode = "401", description = "acesso não autorizado", content = @Content)
    })
    @Override
    @GetMapping("buscar/{id}")
    public ResponseEntity<AlunoResponse> buscarId(@Parameter(hidden = true) @RequestHeader(name = "Authorization") String token, @PathVariable Long id) throws Exception {
        verificarPermicao(token, List.of("administrador", "docente", "aluno"));

        log.info("Buscando Aluno com id {}", id);

        return ResponseEntity.status(200).body(service.buscarPorId(id));
    }

    @Operation(summary = "buscar todos os alunos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "alunos encontrados", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AlunoResponse.class))
            }),
            @ApiResponse(responseCode = "401", description = "acesso não autorizado", content = @Content)
    })
    @Override
    @GetMapping("buscar")
    public ResponseEntity<List<AlunoResponse>> buscarTodas(@Parameter(hidden = true) @RequestHeader(name = "Authorization") String token) {
        verificarPermicao(token, List.of("administrador", "docente", "aluno"));

        log.info("Buscando todos os Alunos");

        return ResponseEntity.status(200).body(service.buscarTodos());
    }

    @Operation(summary = "criar novo aluno")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "aluno criado", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AlunoResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "turma não encontrada", content = @Content),
            @ApiResponse(responseCode = "401", description = "acesso não autorizado", content = @Content)
    })
    @Override
    @PostMapping("criar")
    public ResponseEntity<AlunoResponse> criar(@Parameter(hidden = true) @RequestHeader(name = "Authorization") String token, @RequestBody AlunoRequest requestDto) {
        return super.criar(token, requestDto);
    }

    @Operation(summary = "deletar aluno por id do aluno")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "aluno deletado", content = @Content),
            @ApiResponse(responseCode = "404", description = "aluno não encontrado", content = @Content),
            @ApiResponse(responseCode = "401", description = "acesso não autorizado", content = @Content)
    })
    @Override
    @DeleteMapping("deletar/{id}")
    public ResponseEntity<Object> deleter(@Parameter(hidden = true) @RequestHeader(name = "Authorization") String token, @PathVariable Long id) throws Exception {
        return super.deleter(token, id);
    }

    @Operation(summary = "atualizar aluno por id do aluno")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "aluno atualizado", content = @Content),
            @ApiResponse(responseCode = "404", description = "aluno não encontrado", content = @Content),
            @ApiResponse(responseCode = "400", description = "entidade com esse e-mail já existe", content = @Content),
            @ApiResponse(responseCode = "401", description = "acesso não autorizado", content = @Content)
    })
    @Override
    @PutMapping("atualizar/{id}")
    public ResponseEntity<Object> atualizar(@Parameter(hidden = true) @RequestHeader(name = "Authorization") String token, @RequestBody AlunoRequest requestDto, @PathVariable Long id) throws Exception {
        return super.atualizar(token, requestDto, id);
    }

    @Operation(summary = "buscar todas as avaliações de aluno por id do aluno")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "aluno encontrado", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AvaliacaoResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "aluno não encontrado", content = @Content)
    })
    @GetMapping("/{idAluno}/notas")
    public List<AvaliacaoResponse> listarAvaliacoesPorAluno(@PathVariable Long idAluno) {
        log.info("Buscando todas as notas do Aluno");
        return service.listarAvaliacoesPorAluno(idAluno);
    }

    @Operation(summary = "buscar pontuação de aluno por id do aluno")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "pontuação encontrada", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Double.class))
            }),
            @ApiResponse(responseCode = "401", description = "acesso não autorizado", content = @Content)
    })
    @GetMapping("/{id}/pontuacao")
    public ResponseEntity<Double> obterPontuacao(@Parameter(hidden = true) @RequestHeader(name = "Authorization") String token, @PathVariable Long id) {
        verificarPermicao(token, List.of("aluno"));

        log.info("Obtendo pontuação para o Aluno com id {}", id);

        return ResponseEntity.status(200).body(service.pontuacaoTotalAluno(id));
    }
}
