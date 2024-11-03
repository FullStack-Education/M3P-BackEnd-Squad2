package projetofinal.com.labpcp.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projetofinal.com.labpcp.controller.dto.request.CursoRequest;
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

    @GetMapping("/{idCurso}/materias")
    public List<MateriaResponse> listarMateriasPorCurso(@PathVariable Long idCurso) {
        return service.listarMateriasPorCurso(idCurso);
    }

    @Override
    @GetMapping("buscar/{id}")
    public ResponseEntity<CursoResponse> buscarId(@RequestHeader(name = "Authorization") String token, @PathVariable Long id) throws Exception {
        verificarPermicao(token, List.of("administrador", "docente", "aluno"));

        log.info("Buscando curso com id {}", id);

        return ResponseEntity.status(200).body(service.buscarPorId(id));
    }

    @Override
    @GetMapping("buscar")
    public ResponseEntity<List<CursoResponse>> buscarTodas(@RequestHeader(name = "Authorization") String token) {
        verificarPermicao(token, List.of("administrador", "docente", "aluno"));

        log.info("Buscando todos os cursos");

        return ResponseEntity.status(200).body(service.buscarTodos());
    }
}
