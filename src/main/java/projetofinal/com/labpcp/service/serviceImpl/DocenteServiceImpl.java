package projetofinal.com.labpcp.service.serviceImpl;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import projetofinal.com.labpcp.controller.dto.request.CadastroRequest;
import projetofinal.com.labpcp.controller.dto.request.DocenteRequest;
import projetofinal.com.labpcp.controller.dto.response.CadastroResponse;
import projetofinal.com.labpcp.controller.dto.response.DocenteResponse;

import projetofinal.com.labpcp.controller.dto.response.MateriaResponse;
import projetofinal.com.labpcp.entity.DocenteEntity;
import projetofinal.com.labpcp.entity.MateriaEntity;
import projetofinal.com.labpcp.entity.UsuarioEntity;
import projetofinal.com.labpcp.infra.exception.error.BadRequestException;
import projetofinal.com.labpcp.infra.exception.error.EntityAlreadyExists;
import projetofinal.com.labpcp.infra.exception.error.NotFoundException;
import projetofinal.com.labpcp.infra.generic.GenericServiceImpl;
import projetofinal.com.labpcp.repository.*;
import projetofinal.com.labpcp.service.DocenteService;
import projetofinal.com.labpcp.service.UsuarioService;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DocenteServiceImpl extends GenericServiceImpl<DocenteEntity, DocenteResponse, DocenteRequest> implements DocenteService {
    private final UsuarioRepository usuarioRepository;
    private final MateriaRepository materiaRepository;
    private final DocenteRepository repository;
    private final TurmaRepository turmaRepository;
    private final UsuarioService usuarioService;



    protected DocenteServiceImpl(DocenteRepository repository, UsuarioRepository usuarioRepository, MateriaRepository materiaRepository, TurmaRepository turmaRepository, UsuarioService usuarioService) {
        super(repository);
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
        this.materiaRepository = materiaRepository;
        this.turmaRepository = turmaRepository;
        this.usuarioService = usuarioService;
    }

    @Override
    protected DocenteResponse paraDto(DocenteEntity entity) {
        log.info("Buscando usuário pelo email");

        UsuarioEntity usuarioCadastrado = usuarioRepository.findByEmail(entity.getUsuario().getEmail())
                .orElseThrow(() -> new NotFoundException("Usuário com email: '" + entity.getUsuario().getEmail() + "' não encontrado"));

        CadastroResponse retornoUsuario = new CadastroResponse(
                usuarioCadastrado.getId(),
                usuarioCadastrado.getEmail(),
                usuarioCadastrado.getPerfil().getNome()
        );

        log.info("convertendo entidade de docente para dto");


        List<MateriaResponse> materias = entity.getMaterias().stream()
                .map(materia -> new MateriaResponse(materia.getId(), materia.getNome(), materia.getCurso().getId()))
                .collect(Collectors.toList());

        return new DocenteResponse(
                entity.getId(),
                entity.getNome(),
                entity.getTelefone(),
                entity.getGenero(),
                entity.getEstadoCivil(),
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
                retornoUsuario,
                materias
        );
    }

    @Override
    protected DocenteEntity paraEntity(DocenteRequest requestDto) {

        log.info("convertendo dto de docente para entidade");

        List<MateriaEntity> materias = requestDto.materiasIds().stream()
                .map(materiaId -> materiaRepository.findById(materiaId)
                        .orElseThrow(() -> new NotFoundException("Matéria com id: " + materiaId + " não encontrada")))
                .collect(Collectors.toList());

        return new DocenteEntity(
                requestDto.nome(),
                requestDto.telefone(),
                requestDto.genero(),
                requestDto.estadoCivil(),
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
                requestDto.usuario(),
                materias
        );
    }

    @Override
    public DocenteResponse criar(DocenteRequest requestDto) {

        log.info("Criando usuário para o docente");

        CadastroResponse cadastroResponse = usuarioService.cadastrarUsuario(new CadastroRequest(requestDto.email(), requestDto.senha(), "docente"));

        UsuarioEntity usuario = usuarioRepository.findById(cadastroResponse.id()).orElseThrow(() -> new NotFoundException("usuário para docente não esta sendo criado corretamente"));

        DocenteRequest superRequest = new DocenteRequest(
                requestDto.nome(),
                requestDto.telefone(),
                requestDto.genero(),
                requestDto.estadoCivil(),
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
                requestDto.materiasIds(),
                null,
                null,
                usuario
        );

        return super.criar(superRequest);
    }

    @Override
    public List<DocenteResponse> buscarTodos() {
        List<DocenteEntity> docentes = repository.findByUsuarioPerfilNome("docente");

        log.info("todas as entidades de docente encontradas com sucesso");

        return docentes.stream().map(this::paraDto).collect(Collectors.toList());
    }

    @Override
    public void atualizar(DocenteRequest requestDto, Long id) {
        entidadeExiste(id);

        DocenteEntity existingDocente = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Docente com id: '" + id + "' não encontrado"));


        String email = requestDto.email();
        UsuarioEntity usuario = existingDocente.getUsuario();

        if (!email.equals(existingDocente.getUsuario().getEmail())) {
            usuarioRepository.findByEmail(email)
                    .ifPresent(usuarioEmail -> {
                        throw new EntityAlreadyExists("usuarios", "email", email);
                    });
            usuario.setEmail(email);
            usuarioRepository.save(usuario);
        }

        DocenteRequest superRequest = new DocenteRequest(
                requestDto.nome(),
                requestDto.telefone(),
                requestDto.genero(),
                requestDto.estadoCivil(),
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
                requestDto.materiasIds(),
                null,
                null,
                usuario
        );

        super.atualizar(superRequest, id);
    }


    @Override
    public void deletar(Long id) {
        entidadeExiste(id);
        DocenteEntity existeDocente = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Docente com id: '" + id + "' não encontrado"));


        if (turmaRepository.existsByDocenteId(id)) {
            log.error("Não é possível deletar o docente com id {}. Ele possui turmas associadas.", id);
            throw new BadRequestException("Não é possível deletar o docente enquanto ele tiver turmas associadas.");
        }


        if (existeDocente.getMaterias() != null && !existeDocente.getMaterias().isEmpty()) {
            for (MateriaEntity materia : existeDocente.getMaterias()) {
                log.info("Removendo docente de matéria com id {}", materia.getId());
                materia.getDocentes().remove(existeDocente);
                materiaRepository.save(materia);
            }
        }

        UsuarioEntity usuario = existeDocente.getUsuario();
        repository.delete(existeDocente);
        log.info("Docente com id {} deletado com sucesso", id);

        if (usuario != null) {
            usuarioRepository.delete(usuario);
            log.info("Usuário com email {} deletado com sucesso", usuario.getEmail());
        } else {
            log.warn("Usuário associado ao docente não encontrado");
        }
    }

}
