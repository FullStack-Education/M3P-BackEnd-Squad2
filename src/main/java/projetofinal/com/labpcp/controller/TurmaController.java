package projetofinal.com.labpcp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projetofinal.com.labpcp.controller.dto.request.TurmaRequest;
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

    @Override
    @GetMapping("buscar/{id}")
    public ResponseEntity<TurmaResponse> buscarId(@RequestHeader(name = "Authorization") String token, @PathVariable Long id) throws Exception {
        verificarPermicao(token, List.of("administrador", "docente", "aluno"));

        log.info("Buscando turma com id {}", id);

        return ResponseEntity.status(200).body(service.buscarPorId(id));
    }

    @Override
    @GetMapping("buscar")
    public ResponseEntity<List<TurmaResponse>> buscarTodas(@RequestHeader(name = "Authorization") String token) {
        verificarPermicao(token, List.of("administrador", "docente", "aluno"));

        log.info("Buscando todos as Turmas");

        return ResponseEntity.status(200).body(service.buscarTodos());
    }

}
