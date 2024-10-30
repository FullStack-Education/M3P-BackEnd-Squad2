package projetofinal.com.labpcp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projetofinal.com.labpcp.controller.dto.request.DocenteRequest;
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

    @Override
    @GetMapping("buscar/{id}")
    public ResponseEntity<DocenteResponse> buscarId(@RequestHeader(name = "Authorization") String token, @PathVariable Long id) throws Exception {
        verificarPermicao(token, List.of("administrador", "docente", "aluno"));

        log.info("Buscando Docente com id {}", id);

        return ResponseEntity.status(200).body(service.buscarPorId(id));
    }

    @Override
    @GetMapping("buscar")
    public ResponseEntity<List<DocenteResponse>> buscarTodas(@RequestHeader(name = "Authorization") String token) {
        verificarPermicao(token, List.of("administrador", "docente", "aluno"));

        log.info("Buscando todos os Docentes");

        return ResponseEntity.status(200).body(service.buscarTodos());
    }
}
