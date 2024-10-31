package projetofinal.com.labpcp.service.serviceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import projetofinal.com.labpcp.controller.dto.request.AvaliacaoRequest;
import projetofinal.com.labpcp.controller.dto.request.CursoRequest;
import projetofinal.com.labpcp.controller.dto.response.AvaliacaoResponse;
import projetofinal.com.labpcp.entity.*;
import projetofinal.com.labpcp.infra.exception.error.NotFoundException;
import projetofinal.com.labpcp.repository.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class AvaliacaoServiceImplTest {

    @InjectMocks
    private AvaliacaoServiceImpl avaliacaoService;

    @Mock
    private AvaliacaoRepository avaliacaoRepository;

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

    private TurmaEntity turma;
    private AvaliacaoEntity avaliacao;

    @BeforeEach
    public void setUp() {

        PerfilEntity perfil = new PerfilEntity("docente");
        perfil.setId(1L);
        PerfilEntity perfilAluno = new PerfilEntity("aluno");
        perfilAluno.setId(2L);

        UsuarioEntity usuario = new UsuarioEntity("docente@teste.com", "123", perfil);
        usuario.setId(1L);

        UsuarioEntity usuarioAluno = new UsuarioEntity("aluno@teste.com", "123", perfilAluno);
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

        AlunoEntity aluno = new AlunoEntity(
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

        avaliacao = new AvaliacaoEntity("Av 01", new BigDecimal("9.9"), LocalDate.now(), aluno, docente, materia);
        avaliacao.setId(1L);


        lenient().when(docenteRepository.findById(1L)).thenReturn(Optional.of(docente));
        lenient().when(cursoRepository.findById(1L)).thenReturn(Optional.of(curso));
        lenient().when(turmaRepository.findById(1L)).thenReturn(Optional.of(turma));
        lenient().when(turmaRepository.existsById(1L)).thenReturn(true);
        lenient().when(avaliacaoRepository.save(any(AvaliacaoEntity.class))).thenReturn(avaliacao);
        lenient().when(avaliacaoRepository.existsById(1L)).thenReturn(true);
        lenient().when(avaliacaoRepository.findAll()).thenReturn(Arrays.asList(avaliacao));
        lenient().when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));
        lenient().when(materiaRepository.findById(1L)).thenReturn(Optional.of(materia));
        lenient().when(avaliacaoRepository.findById(1L)).thenReturn(Optional.of(avaliacao));


    }

    @Test
    void criarAvaliacao() {
        when(avaliacaoRepository.save(any(AvaliacaoEntity.class))).thenReturn(avaliacao);
        AvaliacaoRequest request = new AvaliacaoRequest("Av 01", new BigDecimal("9.9"), LocalDate.now(), 1L, 1L, 1L);

        AvaliacaoResponse response = avaliacaoService.criar(request);

        assertNotNull(response);
        assertEquals("Av 01", response.nome());
        verify(avaliacaoRepository, times(1)).save(any(AvaliacaoEntity.class));
    }


    @Test
    void buscarAvaliacaoPorId() {

        AvaliacaoResponse response = avaliacaoService.buscarPorId(1L);

        assertNotNull(response);
        assertEquals("Av 01", response.nome());
        assertEquals(1L, response.alunoId());
        assertEquals(1L, response.docenteId());
        assertEquals(1L, response.materiaId());
        verify(avaliacaoRepository, times(1)).findById(1L);
    }


    @Test
    void buscarTodasAsAvaliacoes() {

        when(avaliacaoRepository.findAll()).thenReturn(Arrays.asList(avaliacao));

        List<AvaliacaoResponse> response = avaliacaoService.buscarTodos();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals("Av 01", response.get(0).nome());
        assertEquals(1L, response.get(0).docenteId());
        assertEquals(1L, response.get(0).alunoId());
        assertEquals(1L, response.get(0).materiaId());

        verify(avaliacaoRepository, times(1)).findAll();
    }


    @Test
    void atualizarAvaliacao() {

        AvaliacaoRequest request = new AvaliacaoRequest("Av 01 Atualizada", new BigDecimal("9.9"), LocalDate.now(), 1L, 1L, 1L);

        avaliacaoService.atualizar(request, 1L);

        verify(avaliacaoRepository, times(1)).save(any(AvaliacaoEntity.class));
    }


    @Test
    void deletarAvaliacao() {

        when(avaliacaoRepository.existsById(1L)).thenReturn(true);

        avaliacaoService.deletar(1L);

        verify(avaliacaoRepository, times(1)).deleteById(1L);
    }


    @Test
    void docenteNaoEncontrado() {

        AvaliacaoRequest request = new AvaliacaoRequest("Av 01", new BigDecimal("9.9"), LocalDate.now(), 1L, 1L, 1L);
        when(docenteRepository.findById(request.docenteId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            avaliacaoService.paraEntity(request);
        });
    }

    @Test
    public void alunoNaoEncontrado() {

        AvaliacaoRequest request = new AvaliacaoRequest("Av 01", new BigDecimal("9.9"), LocalDate.now(), 1L, 1L, 1L);

        when(alunoRepository.findById(request.alunoId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            avaliacaoService.paraEntity(request);
        });
    }

}