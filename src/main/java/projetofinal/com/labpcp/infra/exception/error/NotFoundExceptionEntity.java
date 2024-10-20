package projetofinal.com.labpcp.infra.exception.error;

import lombok.extern.slf4j.Slf4j;
///import projetofinal.com.labpcp.infra.exception.NotFoundException;

@Slf4j
public class NotFoundExceptionEntity extends NotFoundException{

    public NotFoundExceptionEntity(String entity, Long id) {
        super(entity + " com id " + id + " não encontrado");
        log.error("{} com id {} não encontrado", entity,id);
    }

    public NotFoundExceptionEntity(String entity, String nome) {
        super(entity + " com nome " + nome + " não encontrado");
        log.error("{} com nome {} não encontrado", entity, nome);
    }
}
