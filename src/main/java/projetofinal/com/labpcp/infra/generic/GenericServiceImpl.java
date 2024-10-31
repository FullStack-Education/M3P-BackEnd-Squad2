package projetofinal.com.labpcp.infra.generic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import projetofinal.com.labpcp.infra.exception.error.NotFoundExceptionEntity;

import java.lang.reflect.ParameterizedType;
import java.util.List;

@Slf4j
public abstract class GenericServiceImpl<E extends GenericEntity, RES, REQ> implements GenericService<RES, REQ> {

    private final JpaRepository<E, Long> repository;
    private final String nomeEntity;

    protected GenericServiceImpl(JpaRepository<E, Long> repository) {
        this.repository = repository;
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        this.nomeEntity = parameterizedType.getActualTypeArguments()[0].getTypeName().substring(28).replace("Entity", "");
    }

    protected abstract RES paraDto(E entity);

    protected abstract E paraEntity(REQ requestDto);

    public RES buscarPorId(Long id) {
        return paraDto(buscarEntityPorId(id));
    }

    public E buscarEntityPorId(Long id) {

        entidadeExiste(id);

        log.info("entidade {} encontrada com sucesso", nomeEntity);

        return repository.findById(id).get();
    }

    public List<RES> buscarTodos() {
        List<E> entidades= repository.findAll();

        log.info("todas as entidades {} encontradas com sucesso", nomeEntity);

        return entidades.stream().map(this::paraDto).toList();
    }

    public RES criar (REQ requestDto) {
        E entity = paraEntity(requestDto);
        entity.setId(null);

        repository.save(entity);

        log.info("entidade {} criada com sucesso", nomeEntity);

        return paraDto(entity);
    }

    public void deletar (Long id) {
        entidadeExiste(id);
        repository.deleteById(id);

        log.info("entidade {} deletada com sucesso", nomeEntity);
    }

    public void atualizar (REQ requestDto, Long id) {
        entidadeExiste(id);
        E entity = paraEntity(requestDto);

        entity.setId(id);

        repository.save(entity);

        log.info("entidade {} atualizada com sucesso", nomeEntity);
    }

    public void entidadeExiste(Long id) {

        log.info("verificando se {} com id {} existe", nomeEntity, id);

        if (!repository.existsById(id)) {
            throw new NotFoundExceptionEntity(nomeEntity, id);
        }

        log.info("{} com id {} existe", nomeEntity, id);
    }
}