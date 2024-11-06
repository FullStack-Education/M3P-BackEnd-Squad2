package projetofinal.com.labpcp.service.serviceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import projetofinal.com.labpcp.controller.dto.request.CursoRequest;
import projetofinal.com.labpcp.controller.dto.response.CursoResponse;
import projetofinal.com.labpcp.controller.dto.response.MateriaResponse;
import projetofinal.com.labpcp.entity.CursoEntity;
import projetofinal.com.labpcp.infra.exception.error.BadRequestException;
import projetofinal.com.labpcp.infra.exception.error.NotFoundException;
import projetofinal.com.labpcp.infra.generic.GenericServiceImpl;
import projetofinal.com.labpcp.repository.CursoRepository;
import projetofinal.com.labpcp.repository.MateriaRepository;
import projetofinal.com.labpcp.service.CursoService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CursoServiceImpl extends GenericServiceImpl<CursoEntity, CursoResponse, CursoRequest> implements CursoService {

    private final CursoRepository cursoRepository;
    private final MateriaRepository materiaRepository;

    protected CursoServiceImpl(CursoRepository cursoRepository, MateriaRepository materiaRepository) {
        super(cursoRepository);
        this.cursoRepository = cursoRepository;
        this.materiaRepository = materiaRepository;

    }

    @Override
    protected CursoResponse paraDto(CursoEntity entity) {
        log.info("convertendo entidade de curso para dto");


        List<MateriaResponse> materias = entity.getMaterias().stream()
                .map(materia -> new MateriaResponse(materia.getId(), materia.getNome(), materia.getCurso().getId()))
                .collect(Collectors.toList());

        return new CursoResponse(entity.getId(), entity.getNome(), entity.getDuracao(), materias);
    }

    @Override
    protected CursoEntity paraEntity(CursoRequest requestDto) {

        log.info("convertendo dto de curso para entidade");
        return new CursoEntity(requestDto);
    }

    @Override
    public void deletar(Long id) {
        CursoEntity curso = cursoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Curso com id: '" + id + "' não encontrado"));

        if (!curso.getMaterias().isEmpty()) {
            throw new BadRequestException("Não é possível deletar este curso por ter matérias associadas a ele");
        }

        cursoRepository.delete(curso);
        log.info("Curso com id {} deletado com sucesso", id);
    }

    @Override
    public List<MateriaResponse> listarMateriasPorCurso(Long idCurso) {
        log.info("Listando matérias para o curso com id {}", idCurso);

        CursoEntity curso = cursoRepository.findById(idCurso)
                .orElseThrow(() -> new NotFoundException("Curso com id: '" + idCurso + "' não encontrado"));

        return materiaRepository.findByCursoId(idCurso).stream()
                .map(materia -> new MateriaResponse(materia.getId(), materia.getNome(), materia.getCurso().getId()))
                .collect(Collectors.toList());
    }

}
