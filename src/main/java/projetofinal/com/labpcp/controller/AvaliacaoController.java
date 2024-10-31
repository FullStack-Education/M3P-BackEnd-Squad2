package projetofinal.com.labpcp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projetofinal.com.labpcp.controller.dto.request.AvaliacaoRequest;
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


    @Override
    @GetMapping("buscar/{id}")
    public ResponseEntity<AvaliacaoResponse> buscarId(@RequestHeader(name = "Authorization") String token, @PathVariable Long id) throws Exception {
        verificarPermicao(token, List.of("administrador", "docente", "aluno"));

        log.info("Buscando Avaliacao com id {}", id);

        return ResponseEntity.status(200).body(service.buscarPorId(id));
    }

    @Override
    @GetMapping("buscar")
    public ResponseEntity<List<AvaliacaoResponse>> buscarTodas(@RequestHeader(name = "Authorization") String token) {
        verificarPermicao(token, List.of("administrador", "docente", "aluno"));

        log.info("Buscando todos os Avaliacaos");

        return ResponseEntity.status(200).body(service.buscarTodos());
    }
}
