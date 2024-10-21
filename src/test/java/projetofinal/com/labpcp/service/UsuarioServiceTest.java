package projetofinal.com.labpcp.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import projetofinal.com.labpcp.controller.dto.request.CadastroRequest;
import projetofinal.com.labpcp.controller.dto.request.LoginRequest;
import projetofinal.com.labpcp.controller.dto.response.CadastroResponse;
import projetofinal.com.labpcp.controller.dto.response.LoginResponse;
import projetofinal.com.labpcp.entity.PerfilEntity;
import projetofinal.com.labpcp.entity.UsuarioEntity;
import projetofinal.com.labpcp.infra.exception.error.EntityAlreadyExists;
import projetofinal.com.labpcp.infra.exception.error.NotFoundException;
import projetofinal.com.labpcp.repository.PerfilRepository;
import projetofinal.com.labpcp.repository.UsuarioRepository;
import projetofinal.com.labpcp.service.serviceImpl.UsuarioServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private PerfilRepository perfilRepository;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Mock
    private AuthService authService;
    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @Test
    void cadastrarUsuarioSuccess() {
        PerfilEntity perfil = new PerfilEntity("teste");
        CadastroRequest cadastroRequest = new CadastroRequest("teste@email.com", "senha", "teste");

        when(usuarioRepository.findByEmail("teste@email.com")).thenReturn(Optional.empty());
        when(perfilRepository.findByNome("teste")).thenReturn(Optional.of(perfil));
        when(bCryptPasswordEncoder.encode("senha")).thenReturn("senha encriptografada");

        CadastroResponse response = usuarioService.cadastrarUsuario(cadastroRequest);

        assertEquals(response.email(), cadastroRequest.email());
        assertEquals(response.perfil(), cadastroRequest.perfil());
    }

    @Test
    void cadastrarUsuarioUsuarioJaExiste() {
        UsuarioEntity usuario = new UsuarioEntity("teste@email.com", "senha encriptografada", null);
        CadastroRequest cadastroRequest = new CadastroRequest("teste@email.com", "senha", "teste");

        when(usuarioRepository.findByEmail("teste@email.com")).thenReturn(Optional.of(usuario));

        assertThrows(EntityAlreadyExists.class, () -> usuarioService.cadastrarUsuario(cadastroRequest));
    }

    @Test
    void cadastrarUsuarioPerfilNaoEncontrado() {
        CadastroRequest cadastroRequest = new CadastroRequest("teste@email.com", "senha", "teste");

        when(usuarioRepository.findByEmail("teste@email.com")).thenReturn(Optional.empty());
        when(perfilRepository.findByNome("teste")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> usuarioService.cadastrarUsuario(cadastroRequest));
    }

    @Test
    void loginSuccess() {
        PerfilEntity perfil = new PerfilEntity("teste");
        UsuarioEntity usuario = new UsuarioEntity("teste@email.com", "senha encriptografada", perfil);
        LoginResponse authLoginResponse = new LoginResponse("token", 36000L);
        LoginRequest loginRequest = new LoginRequest("teste@email.com", "senha");

        when(usuarioRepository.findByEmail("teste@email.com")).thenReturn(Optional.of(usuario));
        when(bCryptPasswordEncoder.matches("senha", "senha encriptografada")).thenReturn(true);
        when(authService.gerarToken(usuario)).thenReturn(authLoginResponse);

        LoginResponse response = usuarioService.login(loginRequest);

        assertEquals(response, authLoginResponse);
    }

    @Test
    void loginNaoPossuiCadastro() {
        LoginRequest loginRequest = new LoginRequest("teste@email.com", "senha");

        when(usuarioRepository.findByEmail("teste@email.com")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> usuarioService.login(loginRequest));
    }

    @Test
    void loginSenhaInvalida() {
        UsuarioEntity usuario = new UsuarioEntity("teste@email.com", "senha encriptografada", null);
        LoginRequest loginRequest = new LoginRequest("teste@email.com", "senha");

        when(usuarioRepository.findByEmail("teste@email.com")).thenReturn(Optional.of(usuario));
        when(bCryptPasswordEncoder.matches("senha", "senha encriptografada")).thenReturn(false);

        assertThrows(RuntimeException.class, () -> usuarioService.login(loginRequest));
    }
}