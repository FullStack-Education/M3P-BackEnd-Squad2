package projetofinal.com.labpcp.infra.generic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.ParameterizedType;

import java.util.List;

import static projetofinal.com.labpcp.infra.Util.AcessoUtil.verificarPermicao;

@Slf4j
public abstract class GenericController<S extends GenericService<RES, REQ>, RES, REQ> {

    @Autowired
    protected S service;
    private final List<String> permicoes;
    private final String nomeEntity;

    protected GenericController(List<String> acessos) {
        this.permicoes = acessos;
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        this.nomeEntity = parameterizedType.getActualTypeArguments()[0].getTypeName().substring(32).replace("Service", "");
    }

    @GetMapping("buscar/{id}")
    public ResponseEntity<RES> buscarId(@RequestHeader(name = "Authorization") String token, @PathVariable Long id) throws Exception {
        verificarPermicao(token, permicoes);

        log.info("buscando {} com id {}", nomeEntity, id);

        return ResponseEntity.status(200).body(service.buscarPorId(id));
    }

    @GetMapping("buscar")
    public ResponseEntity<List<RES>> buscarTodas(@RequestHeader(name = "Authorization") String token) {
        verificarPermicao(token, permicoes);

        log.info("buscando todas as entidades {}", nomeEntity);

        return ResponseEntity.status(200).body(service.buscarTodos());
    }

    @PostMapping("criar")
    public ResponseEntity<RES> criar(@RequestHeader(name = "Authorization") String token, @RequestBody REQ requestDto) {
        verificarPermicao(token, permicoes);

        log.info("criando {}", nomeEntity);

        return ResponseEntity.status(201).body(service.criar(requestDto));
    }

    @DeleteMapping("deletar/{id}")
    public ResponseEntity<Object> deleter(@RequestHeader(name = "Authorization") String token, @PathVariable Long id) throws Exception {
        verificarPermicao(token, List.of("administrador"));

        log.info("deletando {} com id {}", nomeEntity, id);

        service.deletar(id);
        return ResponseEntity.status(204).build();
    }

    @PutMapping("atualizar/{id}")
    public ResponseEntity<Object> atualizar(@RequestHeader(name = "Authorization") String token, @RequestBody REQ requestDto, @PathVariable Long id) throws Exception {
        verificarPermicao(token, permicoes);

        log.info("atualizando {} com id {}", nomeEntity, id);

        service.atualizar(requestDto, id);
        return ResponseEntity.status(204).build();
    }

}
