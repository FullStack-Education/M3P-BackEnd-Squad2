package projetofinal.com.labpcp.service.serviceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import projetofinal.com.labpcp.controller.dto.request.AlunoRequest;
import projetofinal.com.labpcp.controller.dto.response.AlunoResponse;
import projetofinal.com.labpcp.controller.dto.response.CadastroResponse;
import projetofinal.com.labpcp.entity.*;
import projetofinal.com.labpcp.infra.exception.error.BadRequestException;
import projetofinal.com.labpcp.infra.exception.error.EntityAlreadyExists;
import projetofinal.com.labpcp.infra.exception.error.NotFoundException;
import projetofinal.com.labpcp.infra.generic.GenericServiceImpl;
import projetofinal.com.labpcp.repository.AlunoRepository;
import projetofinal.com.labpcp.repository.PerfilRepository;
import projetofinal.com.labpcp.repository.TurmaRepository;
import projetofinal.com.labpcp.repository.UsuarioRepository;
import projetofinal.com.labpcp.service.AlunoService;

import java.util.Date;

@Slf4j
@Service
public class AlunoServiceImpl extends GenericServiceImpl<AlunoEntity, AlunoResponse, AlunoRequest> implements AlunoService {

    private final AlunoRepository alunoRepository;
    private final TurmaServiceImpl turmaService;
    private final UsuarioRepository usuarioRepository;
    private final PerfilRepository perfilRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AlunoServiceImpl(AlunoRepository repository,
                            UsuarioRepository usuarioRepository,
                            TurmaServiceImpl turmaService, PerfilRepository perfilRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        super(repository);
        this.usuarioRepository = usuarioRepository;
        this.turmaService = turmaService;
        this.alunoRepository = repository;
        this.perfilRepository = perfilRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
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

        Date dataNascimento = new Date(entity.getDataNascimento().getTime());

        return new AlunoResponse(entity.getId(), entity.getNome(), entity.getGenero(), dataNascimento,
                entity.getCpf(),
                entity.getRg(),
                entity.getTelefone(),
                entity.getNaturalidade(),
                entity.getCep(),
                entity.getLogradouro(),
                entity.getNumero(),
                entity.getComplemento(),
                entity.getBairro(),
                entity.getUf(),
                entity.getReferencia(),
                retornoUsuario,
                entity.getTurma().getId());
    }

    @Override
    protected AlunoEntity paraEntity(AlunoRequest requestDto) {

        log.info("Criando usuário para o aluno");

        String perfilAluno = "aluno";
        PerfilEntity perfil = perfilRepository.findByNome(perfilAluno)
                .orElseThrow(() -> new NotFoundException("perfil '" + perfilAluno + "' não encontrado"));

        String senha = bCryptPasswordEncoder.encode(requestDto.senha());
        String email = requestDto.email();

        UsuarioEntity usuario = new UsuarioEntity(email, senha, perfil);
        usuarioRepository.findByEmail(usuario.getEmail())
                .ifPresent(usuarios -> {
                    throw new EntityAlreadyExists("usuarios", "email", usuario.getEmail());
                });

        TurmaEntity turma = turmaService.buscarEntityPorId(requestDto.turmaId());
        if(turma.getId() != null) {
            usuarioRepository.save(usuario);
            log.info("entidade usuário para o aluno criada com sucesso");
        } else {
            log.error("Não é possível encontrar a turma com id {}.", requestDto.turmaId());
        }

        log.info("convertendo dto de aluno para entidade");
        return new AlunoEntity(requestDto,
                turma,
                usuario);
    }

    @Override
    public void atualizar(AlunoRequest requestDto, Long id) {
        entidadeExiste(id);

        AlunoEntity existingAluno = alunoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Aluno com id: '" + id + "' não encontrado"));

        String email = requestDto.email();
        if (!email.equals(existingAluno.getUsuario().getEmail())) {
            usuarioRepository.findByEmail(email)
                    .ifPresent(usuario -> {
                        throw new EntityAlreadyExists("usuarios", "email", email);
                    });
        }


        existingAluno.setNome(requestDto.nome());
        existingAluno.setTelefone(requestDto.telefone());
        existingAluno.setGenero(requestDto.genero());
        existingAluno.setDataNascimento(new Date(requestDto.dataNascimento().getTime()));
        existingAluno.setCpf(requestDto.cpf());
        existingAluno.setRg(requestDto.rg());
        existingAluno.setNaturalidade(requestDto.naturalidade());
        existingAluno.setCep(requestDto.cep());
        existingAluno.setLogradouro(requestDto.logradouro());
        existingAluno.setNumero(requestDto.numero());
        existingAluno.setComplemento(requestDto.complemento());
        existingAluno.setBairro(requestDto.bairro());
        existingAluno.setUf(requestDto.uf());
        existingAluno.setReferencia(requestDto.referencia());

        if (!email.equals(existingAluno.getUsuario().getEmail())) {
            UsuarioEntity usuario = existingAluno.getUsuario();
            usuario.setEmail(email);
            usuarioRepository.save(usuario);
        }

        if (!requestDto.senha().isEmpty()){
            String senha = bCryptPasswordEncoder.encode(requestDto.senha());
            UsuarioEntity usuario = existingAluno.getUsuario();
            usuario.setSenha(senha);
            usuarioRepository.save(usuario);
        }



        TurmaEntity turma = turmaService.buscarEntityPorId(requestDto.turmaId());
        if(turma.getId() != null) {
            existingAluno.setTurma(turma);
        } else {
            log.error("Não é possível encontrar a turma com id {}.", requestDto.turmaId());
        }

        alunoRepository.save(existingAluno);
    }


    @Override
    public void deletar(Long id) {
        entidadeExiste(id);
        AlunoEntity existeAluno = alunoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Aluno com id: '" + id + "' não encontrado"));

        UsuarioEntity usuario = existeAluno.getUsuario();
        alunoRepository.delete(existeAluno);
        log.info("Aluno com id {} deletado com sucesso", id);

        if (usuario != null) {
            usuarioRepository.delete(usuario);
            log.info("Usuário com email {} deletado com sucesso", usuario.getEmail());
        } else {
            log.warn("Usuário associado ao aluno não encontrado");
        }
    }
}
