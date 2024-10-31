package projetofinal.com.labpcp.service.serviceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import projetofinal.com.labpcp.controller.dto.request.AlunoRequest;
import projetofinal.com.labpcp.controller.dto.request.CadastroRequest;
import projetofinal.com.labpcp.controller.dto.response.*;
import projetofinal.com.labpcp.entity.*;
import projetofinal.com.labpcp.infra.exception.error.EntityAlreadyExists;
import projetofinal.com.labpcp.infra.exception.error.NotFoundException;
import projetofinal.com.labpcp.infra.generic.GenericServiceImpl;
import projetofinal.com.labpcp.repository.*;
import projetofinal.com.labpcp.service.AlunoService;
import projetofinal.com.labpcp.service.UsuarioService;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
public class AlunoServiceImpl extends GenericServiceImpl<AlunoEntity, AlunoResponse, AlunoRequest> implements AlunoService {
    private final UsuarioRepository usuarioRepository;

    private final AlunoRepository repository;
    private final TurmaRepository turmaRepository;
    private final UsuarioService usuarioService;

    private final AvaliacaoRepository avaliacaoRepository;



    protected AlunoServiceImpl(AlunoRepository repository, UsuarioRepository usuarioRepository, TurmaRepository turmaRepository, UsuarioService usuarioService, AvaliacaoRepository avaliacaoRepository) {
        super(repository);
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
        this.turmaRepository = turmaRepository;
        this.usuarioService = usuarioService;
        this.avaliacaoRepository = avaliacaoRepository;
    }

    @Override
    protected AlunoResponse paraDto(AlunoEntity entity) {
        log.info("Buscando usuário pelo email");

        UsuarioEntity usuarioCadastrado = usuarioRepository.findByEmail(entity.getUsuario().getEmail())
                .orElseThrow(() -> new NotFoundException("Usuário com email: '" + entity.getUsuario().getEmail() + "' não encontrado"));

        CadastroResponse retornoUsuario = new CadastroResponse(
                usuarioCadastrado.getId(),
                usuarioCadastrado.getEmail(),
                usuarioCadastrado.getPerfil().getNome()
        );

        log.info("convertendo entidade de aluno para dto");

        TurmaEntity turmaEntity = turmaRepository.findById(entity.getTurma().getId())
                .orElseThrow(() -> new NotFoundException("Turma com ID: '" + entity.getTurma().getId() + "' não encontrada"));


        TurmaResponse turma = new TurmaResponse(
                turmaEntity.getId(),
                turmaEntity.getNome(),
                turmaEntity.getDataInicio(),
                turmaEntity.getDataTermino(),
                turmaEntity.getHorario(),
                turmaEntity.getDocente().getId(),
                turmaEntity.getCurso().getId()
        );


        return new AlunoResponse(
                entity.getId(),
                entity.getNome(),
                entity.getTelefone(),
                entity.getGenero(),
                entity.getDataNascimento(),
                entity.getCpf(),
                entity.getRg(),
                entity.getNaturalidade(),
                entity.getCep(),
                entity.getLogradouro(),
                entity.getNumero(),
                entity.getComplemento(),
                entity.getBairro(),
                entity.getUf(),
                entity.getReferencia(),
                turma,
                retornoUsuario
        );
    }

    @Override
    protected AlunoEntity paraEntity(AlunoRequest requestDto) {

        log.info("convertendo dto de aluno para entidade");



        TurmaEntity turma = turmaRepository.findById(requestDto.turma())
                .orElseThrow(() -> new NotFoundException("Turma com ID: '" + requestDto.turma() + "' não encontrada"));

        return new AlunoEntity(
                requestDto.nome(),
                requestDto.telefone(),
                requestDto.genero(),
                requestDto.dataNascimento(),
                requestDto.cpf(),
                requestDto.rg(),
                requestDto.naturalidade(),
                requestDto.cep(),
                requestDto.logradouro(),
                requestDto.numero(),
                requestDto.complemento(),
                requestDto.bairro(),
                requestDto.uf(),
                requestDto.referencia(),
                turma,
                requestDto.usuario()

        );
    }

    @Override
    public AlunoResponse criar(AlunoRequest requestDto) {

        log.info("Criando usuário para o aluno");

        CadastroResponse cadastroResponse = usuarioService.cadastrarUsuario(new CadastroRequest(requestDto.email(), requestDto.senha(), "aluno"));

        UsuarioEntity usuario = usuarioRepository.findById(cadastroResponse.id()).orElseThrow(() -> new NotFoundException("usuário para docente não esta sendo criado corretamente"));

        AlunoRequest superRequest = new AlunoRequest(
                requestDto.nome(),
                requestDto.telefone(),
                requestDto.genero(),
                requestDto.dataNascimento(),
                requestDto.cpf(),
                requestDto.rg(),
                requestDto.naturalidade(),
                requestDto.cep(),
                requestDto.logradouro(),
                requestDto.numero(),
                requestDto.complemento(),
                requestDto.bairro(),
                requestDto.uf(),
                requestDto.referencia(),
                requestDto.turma(),
                null,
                null,
                usuario
        );

        return super.criar(superRequest);
    }

    @Override
    public void atualizar(AlunoRequest requestDto, Long id) {
        entidadeExiste(id);

        AlunoEntity existingAluno = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Aluno com id: '" + id + "' não encontrado"));


        String email = requestDto.email();
        UsuarioEntity usuario = existingAluno.getUsuario();

        if (!email.equals(existingAluno.getUsuario().getEmail())) {
            usuarioRepository.findByEmail(email)
                    .ifPresent(usuarioEmail -> {
                        throw new EntityAlreadyExists("usuarios", "email", email);
                    });
            usuario.setEmail(email);
            usuarioRepository.save(usuario);
        }

        AlunoRequest superRequest = new AlunoRequest(
                requestDto.nome(),
                requestDto.telefone(),
                requestDto.genero(),
                requestDto.dataNascimento(),
                requestDto.cpf(),
                requestDto.rg(),
                requestDto.naturalidade(),
                requestDto.cep(),
                requestDto.logradouro(),
                requestDto.numero(),
                requestDto.complemento(),
                requestDto.bairro(),
                requestDto.uf(),
                requestDto.referencia(),
                requestDto.turma(),
                null,
                null,
                usuario
        );

        super.atualizar(superRequest, id);
    }


    @Override
    public void deletar(Long id) {
        entidadeExiste(id);
        AlunoEntity existeAluno = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Aluno com id: '" + id + "' não encontrado"));

        UsuarioEntity usuario = existeAluno.getUsuario();
        repository.delete(existeAluno);
        log.info("Aluno com id {} deletado com sucesso", id);

        if (usuario != null) {
            usuarioRepository.delete(usuario);
            log.info("Usuário com email {} deletado com sucesso", usuario.getEmail());
        } else {
            log.warn("Usuário associado ao docente não encontrado");
        }
    }

    @Override
    public List<AvaliacaoResponse> listarAvaliacoesPorAluno(Long idAluno) {
        log.info("Listando avaliações para o aluno com id {}", idAluno);

        AlunoEntity aluno = repository.findById(idAluno)
                .orElseThrow(() -> new NotFoundException("Aluno com id: '" + idAluno + "' não encontrado"));

        return avaliacaoRepository.findByAlunoId(idAluno).stream()
                .map(a -> new AvaliacaoResponse(
                        a.getId(),
                        a.getNome(),
                        a.getValor(),
                        a.getDataAvaliacao(),
                        a.getAluno().getId(),
                        a.getDocente().getId(),
                        a.getMateria().getId()))
                .collect(Collectors.toList());
    }


}
