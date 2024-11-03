package projetofinal.com.labpcp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projetofinal.com.labpcp.controller.dto.request.MateriaRequest;
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

    @Override
    @GetMapping("buscar/{id}")
    public ResponseEntity<MateriaResponse> buscarId(@RequestHeader(name = "Authorization") String token, @PathVariable Long id) throws Exception {
        verificarPermicao(token, List.of("administrador", "docente", "aluno"));

        log.info("Buscando matéria com id {}", id);

        return ResponseEntity.status(200).body(service.buscarPorId(id));
    }

    @Override
    @GetMapping("buscar")
    public ResponseEntity<List<MateriaResponse>> buscarTodas(@RequestHeader(name = "Authorization") String token) {
        verificarPermicao(token, List.of("administrador", "docente", "aluno"));

        log.info("Buscando todos as Matérias");

        return ResponseEntity.status(200).body(service.buscarTodos());
    }

}
