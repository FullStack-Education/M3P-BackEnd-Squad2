package projetofinal.com.labpcp.service.serviceImpl;

import lombok.extern.slf4j.Slf4j;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import projetofinal.com.labpcp.controller.dto.request.DocenteRequest;
import projetofinal.com.labpcp.controller.dto.response.CadastroResponse;
import projetofinal.com.labpcp.controller.dto.response.DocenteResponse;

import projetofinal.com.labpcp.controller.dto.response.MateriaResponse;
import projetofinal.com.labpcp.entity.DocenteEntity;
import projetofinal.com.labpcp.entity.MateriaEntity;
import projetofinal.com.labpcp.entity.PerfilEntity;
import projetofinal.com.labpcp.entity.UsuarioEntity;
import projetofinal.com.labpcp.infra.exception.error.EntityAlreadyExists;
import projetofinal.com.labpcp.infra.exception.error.NotFoundException;
import projetofinal.com.labpcp.infra.generic.GenericServiceImpl;
import projetofinal.com.labpcp.repository.DocenteRepository;
import projetofinal.com.labpcp.repository.MateriaRepository;
import projetofinal.com.labpcp.repository.PerfilRepository;
import projetofinal.com.labpcp.repository.UsuarioRepository;
import projetofinal.com.labpcp.service.DocenteService;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DocenteServiceImpl extends GenericServiceImpl<DocenteEntity, DocenteResponse, DocenteRequest> implements DocenteService {
    private final UsuarioRepository usuarioRepository;
    private final PerfilRepository perfilRepository;
    private final MateriaRepository materiaRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final DocenteRepository repository;



    protected DocenteServiceImpl(DocenteRepository repository,
                                 UsuarioRepository usuarioRepository,
                                 PerfilRepository perfilRepository,
                                 MateriaRepository materiaRepository,
                                 BCryptPasswordEncoder bCryptPasswordEncoder) {
        super(repository);
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
        this.perfilRepository = perfilRepository;
        this.materiaRepository = materiaRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
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

        Date dataNascimento = new Date(entity.getDataNascimento().getTime());

        List<MateriaResponse> materias = entity.getMaterias().stream()
                .map(materia -> new MateriaResponse(materia.getId(), materia.getNome(), materia.getCurso().getId()))
                .collect(Collectors.toList());

        return new DocenteResponse(
                entity.getId(),
                entity.getNome(),
                entity.getTelefone(),
                entity.getGenero(),
                entity.getEstadoCivil(),
                dataNascimento,
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
        log.info("Criando usuário para o docente");

        String perfilDocente = "docente";
        PerfilEntity perfil = perfilRepository.findByNome(perfilDocente)
                .orElseThrow(() -> new NotFoundException("perfil '" + perfilDocente + "' não encontrado"));

        String senha = bCryptPasswordEncoder.encode(requestDto.senha());
        String email = requestDto.email();


        UsuarioEntity usuario = new UsuarioEntity(email, senha, perfil);
        usuarioRepository.findByEmail(usuario.getEmail())
                .ifPresent(usuarios -> {
                    throw new EntityAlreadyExists("usuarios", "email", usuario.getEmail());
                });

        usuarioRepository.save(usuario);
        log.info("entidade usuário para o docente criada com sucesso");

        Date dataNascimento = new Date(requestDto.dataNascimento().getTime());
        List<MateriaEntity> materias = requestDto.materiasIds().stream()
                .map(materiaId -> materiaRepository.findById(materiaId)
                        .orElseThrow(() -> new NotFoundException("Matéria com id: " + materiaId + " não encontrada")))
                .collect(Collectors.toList());

        log.info("convertendo dto de docente para entidade");
        return new DocenteEntity(
                requestDto.nome(),
                requestDto.telefone(),
                requestDto.genero(),
                requestDto.estadoCivil(),
                dataNascimento,
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
                usuario,
                materias
        );
    }

    @Override
    public void atualizar(DocenteRequest requestDto, Long id) {
        entidadeExiste(id);

        DocenteEntity existingDocente = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Docente com id: '" + id + "' não encontrado"));


        String email = requestDto.email();
        if (!email.equals(existingDocente.getUsuario().getEmail())) {
            usuarioRepository.findByEmail(email)
                    .ifPresent(usuario -> {
                        throw new EntityAlreadyExists("usuarios", "email", email);
                    });
        }


        existingDocente.setNome(requestDto.nome());
        existingDocente.setTelefone(requestDto.telefone());
        existingDocente.setGenero(requestDto.genero());
        existingDocente.setEstadoCivil(requestDto.estadoCivil());
        existingDocente.setDataNascimento(new Date(requestDto.dataNascimento().getTime()));
        existingDocente.setCpf(requestDto.cpf());
        existingDocente.setRg(requestDto.rg());
        existingDocente.setNaturalidade(requestDto.naturalidade());
        existingDocente.setCep(requestDto.cep());
        existingDocente.setLogradouro(requestDto.logradouro());
        existingDocente.setNumero(requestDto.numero());
        existingDocente.setComplemento(requestDto.complemento());
        existingDocente.setBairro(requestDto.bairro());
        existingDocente.setUf(requestDto.uf());
        existingDocente.setReferencia(requestDto.referencia());


        if (!email.equals(existingDocente.getUsuario().getEmail())) {
            UsuarioEntity usuario = existingDocente.getUsuario();
            usuario.setEmail(email);
            usuarioRepository.save(usuario);
        }


        List<MateriaEntity> novasMaterias = requestDto.materiasIds().stream()
                .map(materiaId -> materiaRepository.findById(materiaId)
                        .orElseThrow(() -> new NotFoundException("Matéria com id: '" + materiaId + "' não encontrada")))
                .collect(Collectors.toList());

        existingDocente.setMaterias(novasMaterias);

        repository.save(existingDocente);
    }

    @Override
    public void deletar(Long id) {

        DocenteEntity existeDocente = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Docente com id: '" + id + "' não encontrado"));


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
