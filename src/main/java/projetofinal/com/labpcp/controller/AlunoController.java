package projetofinal.com.labpcp.controller;

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


    @Override
    @GetMapping("buscar/{id}")
    public ResponseEntity<AlunoResponse> buscarId(@RequestHeader(name = "Authorization") String token, @PathVariable Long id) throws Exception {
        verificarPermicao(token, List.of("administrador", "docente", "aluno"));

        log.info("Buscando Aluno com id {}", id);

        return ResponseEntity.status(200).body(service.buscarPorId(id));
    }

    @Override
    @GetMapping("buscar")
    public ResponseEntity<List<AlunoResponse>> buscarTodas(@RequestHeader(name = "Authorization") String token) {
        verificarPermicao(token, List.of("administrador", "docente", "aluno"));

        log.info("Buscando todos os Alunos");

        return ResponseEntity.status(200).body(service.buscarTodos());
    }


    @GetMapping("/{idAluno}/notas")
    public List<AvaliacaoResponse> listarAvaliacoesPorAluno(@PathVariable Long idAluno) {
        log.info("Buscando todas as notas do Aluno");
        return service.listarAvaliacoesPorAluno(idAluno);
    }

}
