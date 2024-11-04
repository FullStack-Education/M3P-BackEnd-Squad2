package projetofinal.com.labpcp.service.serviceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import projetofinal.com.labpcp.controller.dto.request.AlunoRequest;
import projetofinal.com.labpcp.controller.dto.request.CadastroRequest;
import projetofinal.com.labpcp.controller.dto.request.CursoRequest;
import projetofinal.com.labpcp.controller.dto.response.AlunoResponse;
import projetofinal.com.labpcp.controller.dto.response.CadastroResponse;
import projetofinal.com.labpcp.entity.*;
import projetofinal.com.labpcp.repository.*;
import projetofinal.com.labpcp.service.UsuarioService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class AlunoServiceImplTest {

    @InjectMocks
    private AlunoServiceImpl alunoService;

    @Mock
    private DocenteRepository docenteRepository;
    @Mock
    private CursoRepository cursoRepository;
    @Mock
    private TurmaRepository turmaRepository;
    @Mock
    private AlunoRepository alunoRepository;
    @Mock
    private MateriaRepository materiaRepository;
    @Mock
    private UsuarioService usuarioService;
    @Mock
    private UsuarioRepository usuarioRepository;

    private TurmaEntity turma;
    private AlunoEntity aluno;
    private UsuarioEntity usuarioAluno;

    @BeforeEach
    public void setUp() {
        PerfilEntity perfil = new PerfilEntity("docente");
        perfil.setId(1L);
        PerfilEntity perfilAluno = new PerfilEntity("aluno");
        perfilAluno.setId(2L);

        UsuarioEntity usuario = new UsuarioEntity("docente@teste.com", "123", perfil);
        usuario.setId(1L);

        usuarioAluno = new UsuarioEntity("aluno@teste.com", "123", perfilAluno);
        usuarioAluno.setId(2L);

        CursoRequest cursoRequest = new CursoRequest("Curso Teste", "10 meses");
        CursoEntity curso = new CursoEntity(cursoRequest);
        curso.setId(1L);

        MateriaEntity materia = new MateriaEntity("POO", curso);
        materia.setId(1L);
        curso.getMaterias().add(materia);

        DocenteEntity docente = new DocenteEntity(
                "Docente de Teste",
                "1234-5678",
                "Masculino",
                "Solteiro",
                LocalDate.of(1990, 1, 1),
                "123.456.789-00",
                "12.345.678-9",
                "São Paulo",
                "12345-678",
                "Rua das Flores",
                "123",
                "Apto 1",
                "Jardim das Rosas",
                "SP",
                "Referência teste",
                usuario,
                Arrays.asList(materia)
        );
        docente.setId(1L);

        turma = new TurmaEntity("Turma 1", LocalDate.now(), LocalDate.now().plusMonths(6), "08:00 - 10:00", docente, curso);
        turma.setId(1L);

        aluno = new AlunoEntity(
                "Aluno de Teste",
                "1234-5678",
                "Masculino",
                LocalDate.of(1990, 1, 1),
                "123.456.789-00",
                "12.345.678-9",
                "São Paulo",
                "12345-678",
                "Rua das Flores",
                "123",
                "Apto 1",
                "Jardim das Rosas",
                "SP",
                "Referência teste",
                turma,
                usuarioAluno
        );
        aluno.setId(1L);


        lenient().when(docenteRepository.findById(1L)).thenReturn(Optional.of(docente));
        lenient().when(cursoRepository.findById(1L)).thenReturn(Optional.of(curso));
        lenient().when(turmaRepository.findById(1L)).thenReturn(Optional.of(turma));
        lenient().when(turmaRepository.existsById(1L)).thenReturn(true);
        lenient().when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));
        lenient().when(materiaRepository.findById(1L)).thenReturn(Optional.of(materia));
        lenient().when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));
        lenient().when(usuarioRepository.findByEmail("aluno@teste.com")).thenReturn(Optional.of(usuarioAluno));
        lenient().when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));
        lenient().when(usuarioRepository.findByEmail("aluno@teste.com")).thenReturn(Optional.of(usuarioAluno));
        lenient().when(usuarioService.cadastrarUsuario(any(CadastroRequest.class))).thenReturn(new CadastroResponse(usuarioAluno.getId(), usuarioAluno.getEmail(), "aluno"));

        CadastroRequest cadastroRequest = new CadastroRequest("aluno@teste.com", "123", "aluno");
        CadastroResponse cadastroResponse = new CadastroResponse(usuarioAluno.getId(), usuarioAluno.getEmail(), "aluno");
        lenient().when(usuarioService.cadastrarUsuario(any(CadastroRequest.class))).thenReturn(cadastroResponse);


        lenient().when(usuarioRepository.findById(usuarioAluno.getId())).thenReturn(Optional.of(usuarioAluno));
        lenient().when(usuarioRepository.findByEmail("aluno@teste.com")).thenReturn(Optional.of(usuarioAluno));
    }

    @Test
    void criarAluno() {
        when(alunoRepository.save(any(AlunoEntity.class))).thenReturn(aluno);

        AlunoRequest request = new AlunoRequest(
                "Aluno de Teste",
                "1234-5678",
                "Masculino",
                LocalDate.of(1990, 1, 1),
                "123.456.789-00",
                "12.345.678-9",
                "São Paulo",
                "12345-678",
                "Rua das Flores",
                "123",
                "Apto 1",
                "Jardim das Rosas",
                "SP",
                "Referência teste",
                1L,
                "aluno@teste.com",
                "123",
                usuarioAluno
        );

        AlunoResponse response = alunoService.criar(request);

        assertNotNull(response);
        assertEquals("Aluno de Teste", response.nome());
        verify(alunoRepository, times(1)).save(any(AlunoEntity.class));
        verify(usuarioService, times(1)).cadastrarUsuario(any(CadastroRequest.class));
    }

    @Test
    void buscarTodosOsAlunos() {
        when(alunoRepository.findAll()).thenReturn(Arrays.asList(aluno));

        List<AlunoResponse> response = alunoService.buscarTodos();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals("Aluno de Teste", response.get(0).nome());


        verify(alunoRepository, times(1)).findAll();
    }

    @Test
    void buscarTodosOsAlunosVazio() {
        when(alunoRepository.findAll()).thenReturn(Collections.emptyList());

        List<AlunoResponse> response = alunoService.buscarTodos();

        assertNotNull(response);
        assertTrue(response.isEmpty());

        verify(alunoRepository, times(1)).findAll();
    }

}