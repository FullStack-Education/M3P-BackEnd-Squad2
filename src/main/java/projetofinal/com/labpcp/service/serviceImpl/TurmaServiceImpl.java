package projetofinal.com.labpcp.service.serviceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import projetofinal.com.labpcp.controller.dto.request.TurmaRequest;
import projetofinal.com.labpcp.controller.dto.response.TurmaResponse;
import projetofinal.com.labpcp.entity.CursoEntity;
import projetofinal.com.labpcp.entity.DocenteEntity;
import projetofinal.com.labpcp.entity.TurmaEntity;
import projetofinal.com.labpcp.infra.exception.error.BadRequestException;
import projetofinal.com.labpcp.infra.exception.error.NotFoundException;
import projetofinal.com.labpcp.infra.generic.GenericServiceImpl;
import projetofinal.com.labpcp.repository.CursoRepository;
import projetofinal.com.labpcp.repository.DocenteRepository;
import projetofinal.com.labpcp.repository.TurmaRepository;
import projetofinal.com.labpcp.service.TurmaService;



@Slf4j
@Service
public class TurmaServiceImpl extends GenericServiceImpl<TurmaEntity, TurmaResponse, TurmaRequest> implements TurmaService {

    private final DocenteRepository docenteRepository;
    private final CursoRepository cursoRepository;

    private final TurmaRepository turmaRepository;


    public TurmaServiceImpl(TurmaRepository turmaRepository, DocenteRepository docenteRepository, CursoRepository cursoRepository) {
        super(turmaRepository);
        this.turmaRepository = turmaRepository;
        this.docenteRepository = docenteRepository;
        this.cursoRepository = cursoRepository;
    }

    @Override
    protected TurmaResponse paraDto(TurmaEntity entity) {

        log.info("convertendo entidade de turma para dto");

        return new TurmaResponse(
                entity.getId(),
                entity.getNome(),
                entity.getDataInicio(),
                entity.getDataTermino(),
                entity.getHorario(),
                entity.getDocente().getId(),
                entity.getCurso().getId()
        );
    }

    @Override
    protected TurmaEntity paraEntity(TurmaRequest requestDto) {
        log.info("Iniciando a conversão de TurmaRequest para TurmaEntity.");

        log.debug("Buscando docente com ID: {}", requestDto.docenteId());
        DocenteEntity docente = docenteRepository.findById(requestDto.docenteId())
                .orElseThrow(() -> {
                    log.error("Docente com id: '{}' não encontrado.", requestDto.docenteId());
                    return new NotFoundException("Docente com id: '" + requestDto.docenteId() + "' não encontrado");
                });

        log.debug("Buscando curso com ID: {}", requestDto.cursoId());
        CursoEntity curso = cursoRepository.findById(requestDto.cursoId())
                .orElseThrow(() -> {
                    log.error("Curso com id: '{}' não encontrado.", requestDto.cursoId());
                    return new NotFoundException("Curso com id: '" + requestDto.cursoId() + "' não encontrado");
                });

        log.debug("Verificando se o docente ministra matérias do curso: {}", curso.getId());
        boolean docenteMinistraCurso = docente.getMaterias().stream()
                .anyMatch(materia -> materia.getCurso().equals(curso));

        if (!docenteMinistraCurso) {
            log.error("O docente com ID: '{}' não ministra matérias deste curso com ID: '{}'.", docente.getId(), curso.getId());
            throw new BadRequestException("O docente não ministra matérias deste curso.");
        }

        log.info("Criando a entidade Turma com as informações recebidas.");
        TurmaEntity turma = new TurmaEntity();
        turma.setNome(requestDto.nome());
        turma.setDataInicio(requestDto.dataInicio());
        turma.setDataTermino(requestDto.dataTermino());
        turma.setHorario(requestDto.horario());
        turma.setDocente(docente);
        turma.setCurso(curso);

        log.info("Turma criada com sucesso. ID do Docente: {}, ID do Curso: {}", docente.getId(), curso.getId());
        return turma;
    }

}

