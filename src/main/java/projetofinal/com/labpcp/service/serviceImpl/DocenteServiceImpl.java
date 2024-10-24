package projetofinal.com.labpcp.service.serviceImpl;

import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import projetofinal.com.labpcp.controller.dto.request.DocenteRequest;
import projetofinal.com.labpcp.controller.dto.response.CadastroResponse;
import projetofinal.com.labpcp.controller.dto.response.DocenteResponse;
import projetofinal.com.labpcp.entity.DocenteEntity;
import projetofinal.com.labpcp.entity.PerfilEntity;
import projetofinal.com.labpcp.entity.UsuarioEntity;
import projetofinal.com.labpcp.infra.exception.error.EntityAlreadyExists;
import projetofinal.com.labpcp.infra.exception.error.NotFoundException;
import projetofinal.com.labpcp.infra.generic.GenericServiceImpl;
import projetofinal.com.labpcp.repository.DocenteRepository;
import projetofinal.com.labpcp.repository.PerfilRepository;
import projetofinal.com.labpcp.repository.UsuarioRepository;
import projetofinal.com.labpcp.service.DocenteService;

import java.util.Date;
import java.util.Random;

@Slf4j
@Service
public class DocenteServiceImpl extends GenericServiceImpl<DocenteEntity, DocenteResponse, DocenteRequest> implements DocenteService {
    private final UsuarioRepository usuarioRepository;
    private final PerfilRepository perfilRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int LENGTH = 5;
    private static final Random RANDOM = new Random();

    protected DocenteServiceImpl(DocenteRepository repository, UsuarioRepository usuarioRepository, PerfilRepository perfilRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        super(repository);
        this.usuarioRepository = usuarioRepository;
        this.perfilRepository = perfilRepository;
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
                retornoUsuario
        );
    }

    @Override
    protected DocenteEntity paraEntity(DocenteRequest requestDto) {
        log.info("Criando usuário para o docente");

        String perfilDocente = "docente";
        PerfilEntity perfil = perfilRepository.findByNome(perfilDocente)
                .orElseThrow(() -> new NotFoundException("perfil '" + perfilDocente + "' não encontrado"));

        String senha = bCryptPasswordEncoder.encode("docente123");

        String emailGenerateDocente = requestDto.nome().toLowerCase().replace(" ", "") + generateRandomString() + "@docente.com";

        UsuarioEntity usuario = new UsuarioEntity(emailGenerateDocente, senha, perfil);

        usuarioRepository.save(usuario);

        log.info("entidade usuário para o docente criada com sucesso");

        Date dataNascimento = new Date(requestDto.dataNascimento().getTime());

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
                usuario
        );
    }

    private static String generateRandomString() {
        StringBuilder sb = new StringBuilder(LENGTH);
        for (int i = 0; i < LENGTH; i++) {
            sb.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }
}
