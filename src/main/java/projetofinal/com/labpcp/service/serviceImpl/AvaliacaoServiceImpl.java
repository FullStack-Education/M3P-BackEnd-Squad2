package projetofinal.com.labpcp.service.serviceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import projetofinal.com.labpcp.controller.dto.request.AvaliacaoRequest;
import projetofinal.com.labpcp.controller.dto.response.AvaliacaoResponse;
import projetofinal.com.labpcp.entity.*;
import projetofinal.com.labpcp.infra.exception.error.NotFoundException;
import projetofinal.com.labpcp.infra.generic.GenericServiceImpl;
import projetofinal.com.labpcp.repository.*;
import projetofinal.com.labpcp.service.AvaliacaoService;


@Slf4j
@Service
public class AvaliacaoServiceImpl extends GenericServiceImpl<AvaliacaoEntity, AvaliacaoResponse, AvaliacaoRequest> implements AvaliacaoService {

    private final DocenteRepository docenteRepository;
    private final AlunoRepository alunoRepository;
    private final MateriaRepository materiaRepository;


    public AvaliacaoServiceImpl(AvaliacaoRepository avaliacaoRepository, DocenteRepository docenteRepository, AlunoRepository alunoRepository, MateriaRepository materiaRepository) {
        super(avaliacaoRepository);
        this.docenteRepository = docenteRepository;
        this.alunoRepository = alunoRepository;
        this.materiaRepository = materiaRepository;
    }


    @Override
    protected AvaliacaoResponse paraDto(AvaliacaoEntity entity) {

        log.info("convertendo entidade de avaliação para dto");
        return new AvaliacaoResponse(entity.getId(), entity.getNome(), entity.getValor(), entity.getDataAvaliacao(), entity.getAluno().getId(), entity.getDocente().getId(), entity.getMateria().getId());
    }

    @Override
    protected AvaliacaoEntity paraEntity(AvaliacaoRequest requestDto) {

        log.info("Iniciando a conversão de AvaliacaoRequest para AvalicaoEntity.");

        log.debug("Buscando docente com ID: {}", requestDto.docenteId());
        DocenteEntity docente = docenteRepository.findById(requestDto.docenteId())
                .orElseThrow(() -> {
                    log.error("Docente com id: '{}' não encontrado.", requestDto.docenteId());
                    return new NotFoundException("Docente com id: '" + requestDto.docenteId() + "' não encontrado");
                });

        log.debug("Buscando aluno com ID: {}", requestDto.alunoId());
        AlunoEntity aluno = alunoRepository.findById(requestDto.alunoId())
                .orElseThrow(() -> {
                    log.error("Aluno com id: '{}' não encontrado.", requestDto.alunoId());
                    return new NotFoundException("Aluno com id: '" + requestDto.alunoId() + "' não encontrado");
                });

        log.debug("Buscando matéria com ID: {}", requestDto.materiaId());
        MateriaEntity materia = materiaRepository.findById(requestDto.materiaId())
                .orElseThrow(() -> {
                    log.error("Matéria com id: '{}' não encontrada.", requestDto.materiaId());
                    return new NotFoundException("Matéria com id: '" + requestDto.materiaId() + "' não encontrada");
                });

        log.info("Criando a entidade Avaliação com as informações recebidas.");
        AvaliacaoEntity avaliacao = new AvaliacaoEntity();
        avaliacao.setNome(requestDto.nome());
        avaliacao.setValor(requestDto.valor());
        avaliacao.setDataAvaliacao(requestDto.dataAvaliacao());
        avaliacao.setAluno(aluno);
        avaliacao.setDocente(docente);
        avaliacao.setMateria(materia);

        log.info("Avaliação criada com sucesso. ID do Docente: {}, ID do Aluno: {}, ID da Matéria: {}", docente.getId(), aluno.getId(), materia.getId());
        return avaliacao;
    }
}



