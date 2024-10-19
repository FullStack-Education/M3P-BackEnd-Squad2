package projetofinal.com.labpcp.service.serviceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import projetofinal.com.labpcp.controller.dto.request.CadastroRequest;
import projetofinal.com.labpcp.controller.dto.response.CadastroResponse;
import projetofinal.com.labpcp.entity.PerfilEntity;
import projetofinal.com.labpcp.entity.UsuarioEntity;
import projetofinal.com.labpcp.infra.exception.error.EntityAlreadyExists;
import projetofinal.com.labpcp.infra.exception.error.NotFoundException;
import projetofinal.com.labpcp.repository.PerfilRepository;
import projetofinal.com.labpcp.repository.UsuarioRepository;
import projetofinal.com.labpcp.service.UsuarioService;

@Slf4j
@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PerfilRepository perfilRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UsuarioServiceImpl(UsuarioRepository repository, PerfilRepository perfilRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.usuarioRepository = repository;
        this.perfilRepository = perfilRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public CadastroResponse cadastrarUsuario(CadastroRequest cadastroRequest) {

        usuarioRepository.findByEmail(cadastroRequest.email())
                .ifPresent(usuario -> {
                    throw new EntityAlreadyExists("usuarios", "email", cadastroRequest.email());
                });

        PerfilEntity perfil  = perfilRepository.findByNome(cadastroRequest.perfil().toLowerCase())
                .orElseThrow(() -> new NotFoundException("perfil '" + cadastroRequest.perfil() +  "' não encontrado"));

        String senha = bCryptPasswordEncoder.encode(cadastroRequest.senha());

        UsuarioEntity usuario = new UsuarioEntity(cadastroRequest.email(), senha, perfil);

        usuarioRepository.save(usuario);

        log.info("entidade usuário criada com sucesso");

        return paraCadastroDto(usuario);
    }

    private CadastroResponse paraCadastroDto(UsuarioEntity usuario) {
        return new CadastroResponse(usuario.getId(), usuario.getEmail(), usuario.getPerfil().getNome());
    }
}
