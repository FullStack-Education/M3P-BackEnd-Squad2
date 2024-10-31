package projetofinal.com.labpcp.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import projetofinal.com.labpcp.controller.dto.request.CursoRequest;
import projetofinal.com.labpcp.controller.dto.request.MateriaRequest;
import projetofinal.com.labpcp.controller.dto.response.MateriaResponse;
import projetofinal.com.labpcp.entity.CursoEntity;
import projetofinal.com.labpcp.entity.MateriaEntity;
import projetofinal.com.labpcp.infra.exception.error.NotFoundException;
import projetofinal.com.labpcp.infra.exception.error.NotFoundExceptionEntity;
import projetofinal.com.labpcp.repository.CursoRepository;
import projetofinal.com.labpcp.repository.MateriaRepository;
import projetofinal.com.labpcp.service.serviceImpl.MateriaServiceImpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MateriaServiceTest {

    static CursoEntity curso;
    static MateriaEntity materia;

    @Mock
    private MateriaRepository materiaRepository;
    @Mock
    private CursoRepository cursoRepository;
    @InjectMocks
    private MateriaServiceImpl materiaService;

    @BeforeEach
    void init() {
        curso = new CursoEntity(new CursoRequest("Engenharia de Software", "30"));
        curso.setId(1L);
        materia = new MateriaEntity("POO", curso);
        materia.setId(1L);
        lenient().when(materiaRepository.findById(1L)).thenReturn(Optional.of(materia));
        lenient().when(materiaRepository.findAll()).thenReturn(Collections.singletonList(materia));
        lenient().when(materiaRepository.existsById(1L)).thenReturn(true);
        lenient().when(cursoRepository.findById(1L)).thenReturn(Optional.of(curso));
    }

    @Test
    @DisplayName("Deve cadastrar materia com sucesso")
    void deveCadastrarMateriaComSucesso() {
        when(cursoRepository.findById(curso.getId()))
                .thenReturn(Optional.of(curso));

        MateriaResponse resultado = materiaService.criar(new MateriaRequest(materia.getNome(), curso.getId()));

        assertEquals(materia.getNome(), resultado.nome());
        assertEquals(materia.getCurso().getId(), resultado.curso_id());
    }

    @Test
    @DisplayName("Deve lançar exceção quando curso não existir ao cadastrar materia")
    void deveLancarExcecaoQuandoCursoNaoExistir() {
        when(cursoRepository.findById(curso.getId()))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            materiaService.criar(new MateriaRequest(materia.getNome(), curso.getId()));
        });
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar materia por id não encontrado")
    void deveLancarExcecaoQuandoMateriaNaoForEncontradaPeloId() {
        assertThrows(NotFoundExceptionEntity.class, () -> { materiaService.buscarPorId(0L); });
    }

    @Test
    @DisplayName("Deve retornar materia por id")
    void deveRetornarMateriaPorId() {
        when(materiaRepository.findById(1L))
                .thenReturn(Optional.of(materia));

        MateriaResponse resultado = materiaService.buscarPorId(materia.getId());

        assertEquals(materia.getNome(), resultado.nome());
    }

    @Test
    @DisplayName("Deve atualizar materia")
    void deveAtualizarMateria() {
        MateriaRequest materiaRequest = new MateriaRequest("atualizado", curso.getId());
        materiaService.atualizar(materiaRequest, materia.getId());

        assertNotNull(materia);
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar materia não encontrado")
    void deveLancarExcecaoQuandoMateriaNaoForEncontradaAoAtualizar() {
        assertThrows(NotFoundExceptionEntity.class, () -> { materiaService.atualizar(new MateriaRequest("atualizado", curso.getId()), 0L); });
    }

    @Test
    @DisplayName("Deve deletar materia")
    void deveDeletarMateria() {
        materiaService.deletar(materia.getId());

        assertNotNull(materia);
    }

    @Test
    @DisplayName("Deve lançar exceção ao deletar materia não encontrado")
    void deveLancarExcecaoQuandoMateriaNaoForEncontradaAoDeletar() {
        assertThrows(NotFoundExceptionEntity.class, () -> { materiaService.deletar(0L); });
    }

    @Test
    @DisplayName("Deve retornar lista de materias")
    void deveRetornarListaDeMaterias() {
        when(materiaRepository.findAll())
                .thenReturn(List.of(materia));

        List<MateriaResponse> resultado = materiaService.buscarTodos();

        assertEquals(materia.getNome(), resultado.get(0).nome());
        assertEquals(materia.getCurso().getId(), resultado.get(0).curso_id());
    }
}
