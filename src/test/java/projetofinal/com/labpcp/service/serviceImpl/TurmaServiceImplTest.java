package projetofinal.com.labpcp.service.serviceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import projetofinal.com.labpcp.controller.dto.request.CursoRequest;
import projetofinal.com.labpcp.controller.dto.request.TurmaRequest;
import projetofinal.com.labpcp.controller.dto.response.TurmaResponse;
import projetofinal.com.labpcp.entity.*;
import projetofinal.com.labpcp.repository.CursoRepository;
import projetofinal.com.labpcp.repository.DocenteRepository;
import projetofinal.com.labpcp.repository.TurmaRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class TurmaServiceImplTest {

    @InjectMocks
    private TurmaServiceImpl turmaService;

    @Mock
    private TurmaRepository turmaRepository;

    @Mock
    private DocenteRepository docenteRepository;
    @Mock
    private CursoRepository cursoRepository;
    private TurmaEntity turma;

    @BeforeEach
    public void setUp() {

        PerfilEntity perfil = new PerfilEntity("docente");
        perfil.setId(1L);

        UsuarioEntity usuario = new UsuarioEntity("teste@teste.com", "123", perfil);
        usuario.setId(1L);

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

        lenient().when(docenteRepository.findById(1L)).thenReturn(Optional.of(docente));
        lenient().when(cursoRepository.findById(1L)).thenReturn(Optional.of(curso));
        lenient().when(turmaRepository.findById(1L)).thenReturn(Optional.of(turma));
        lenient().when(turmaRepository.existsById(1L)).thenReturn(true);
        lenient().when(turmaRepository.save(any(TurmaEntity.class))).thenReturn(turma);
        lenient().when(turmaRepository.findAll()).thenReturn(Arrays.asList(turma));

    }

    @Test
    void criarTurma() {

        when(turmaRepository.save(any(TurmaEntity.class))).thenReturn(turma);
        TurmaRequest request = new TurmaRequest("Turma 1", LocalDate.now(), LocalDate.now().plusMonths(6), "08:00 - 10:00", 1L, 1L);
        TurmaResponse response = turmaService.criar(request);

        assertNotNull(response);
        assertEquals("Turma 1", response.nome());
        verify(turmaRepository, times(1)).save(any(TurmaEntity.class));
    }

    @Test
    void buscarTurmaPorId() {

        TurmaResponse response = turmaService.buscarPorId(1L);

        assertNotNull(response);
        assertEquals("Turma 1", response.nome());
        assertEquals(1L, response.docenteId());

        assertEquals(1L, response.cursoId());
        verify(turmaRepository, times(1)).findById(1L);
    }

    @Test
    void buscarTodasAsTurmas() {

        when(turmaRepository.findAll()).thenReturn(Arrays.asList(turma));

        List<TurmaResponse> response = turmaService.buscarTodos();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals("Turma 1", response.get(0).nome());
        assertEquals(1L, response.get(0).docenteId());
        assertEquals(1L, response.get(0).cursoId());

        verify(turmaRepository, times(1)).findAll();
    }

    @Test
    void atualizarTurma() {

        TurmaRequest request = new TurmaRequest("Turma Atualizada", LocalDate.now(), LocalDate.now().plusMonths(6), "08:00 - 10:00", 1L, 1L);

        turmaService.atualizar(request, 1L);

        verify(turmaRepository, times(1)).save(any(TurmaEntity.class));
    }

    @Test
    void deletarTurma() {

        when(turmaRepository.existsById(1L)).thenReturn(true);

        turmaService.deletar(1L);

        verify(turmaRepository, times(1)).deleteById(1L);
    }

}