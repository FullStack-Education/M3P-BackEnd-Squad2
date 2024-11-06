package projetofinal.com.labpcp.service.serviceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import projetofinal.com.labpcp.controller.dto.request.MateriaRequest;
import projetofinal.com.labpcp.controller.dto.response.MateriaResponse;
import projetofinal.com.labpcp.entity.CursoEntity;
import projetofinal.com.labpcp.entity.MateriaEntity;
import projetofinal.com.labpcp.infra.exception.error.NotFoundException;
import projetofinal.com.labpcp.infra.generic.GenericServiceImpl;
import projetofinal.com.labpcp.repository.CursoRepository;
import projetofinal.com.labpcp.repository.MateriaRepository;
import projetofinal.com.labpcp.service.MateriaService;

@Slf4j
@Service
public class MateriaServiceImpl extends GenericServiceImpl<MateriaEntity, MateriaResponse, MateriaRequest> implements MateriaService {
    private final CursoRepository cursoRepository;

    protected MateriaServiceImpl(MateriaRepository repository, CursoRepository cursoRepository) {
        super(repository);
        this.cursoRepository = cursoRepository;
    }


    @Override
    protected MateriaResponse paraDto(MateriaEntity entity) {

        log.info("convertendo entidade de materia para dto");
        return new MateriaResponse(entity.getId(), entity.getNome(), entity.getCurso().getId());
    }

    @Override
    protected MateriaEntity paraEntity(MateriaRequest requestDto) {

        log.info("Buscando curso pelo id");
        CursoEntity cursoCadastrado = cursoRepository.findById(requestDto.curso_id())
                .orElseThrow(() -> new NotFoundException("Curso com id: '" + requestDto.curso_id() + "' não encontrado"));

        log.info("convertendo dto de materia para entidade");
        return new MateriaEntity(requestDto.nome(), cursoCadastrado);
    }
}
