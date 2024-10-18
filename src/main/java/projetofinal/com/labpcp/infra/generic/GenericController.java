package projetofinal.com.labpcp.infra.generic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
public abstract class GenericController<S extends GenericService<RES, REQ>, RES, REQ> {

    private S service;

    @GetMapping("buscar/{id}")
    public ResponseEntity<RES> buscarId(@PathVariable Long id) throws Exception{

        log.info("buscando com id {}", id);

        return ResponseEntity.status(200).body(service.buscarPorId(id));
    }

    @GetMapping("buscar")
    public ResponseEntity<List<RES>> buscarTodas() {

        log.info("buscando todas as entidades");

        return ResponseEntity.status(200).body(service.buscarTodos());
    }

    @PostMapping("criar")
    public ResponseEntity<RES> criar(@RequestBody REQ requestDto) {

        log.info("criando entidade");

        return ResponseEntity.status(201).body(service.criar(requestDto));
    }

    @DeleteMapping("deletar/{id}")
    public ResponseEntity<Object> deleter(@PathVariable Long id) throws Exception{

        log.info("deletando o id {}", id);

        service.deletar(id);
        return ResponseEntity.status(204).build();
    }
    @PutMapping("atualizar/{id}")
    public ResponseEntity<Object> atualizar(@RequestBody REQ requestDto, @PathVariable Long id) throws Exception{

        log.info("atualizando o id {}", id);

        service.atualizar(requestDto, id);
        return ResponseEntity.status(204).build();
    }

}
