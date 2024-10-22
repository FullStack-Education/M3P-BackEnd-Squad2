package projetofinal.com.labpcp.service.serviceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import projetofinal.com.labpcp.controller.dto.request.CursoRequest;
import projetofinal.com.labpcp.controller.dto.response.CursoResponse;
import projetofinal.com.labpcp.entity.CursoEntity;
import projetofinal.com.labpcp.infra.generic.GenericServiceImpl;
import projetofinal.com.labpcp.repository.CursoRepository;
import projetofinal.com.labpcp.service.CursoService;

@Slf4j
@Service
public class CursoServiceImpl extends GenericServiceImpl<CursoEntity, CursoResponse, CursoRequest> implements CursoService {
    protected CursoServiceImpl(CursoRepository repository) {
        super(repository);
    }

    @Override
    protected CursoResponse paraDto(CursoEntity entity) {

        log.info("convertendo entidade de curso para dto");
        return new CursoResponse(entity.getId(), entity.getNome(), entity.getDuracao());
    }

    @Override
    protected CursoEntity paraEntity(CursoRequest requestDto) {

        log.info("convertendo dto de curso para entidade");
        return new CursoEntity(requestDto);
    }
}
