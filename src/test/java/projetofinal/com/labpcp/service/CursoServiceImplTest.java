package projetofinal.com.labpcp.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import projetofinal.com.labpcp.controller.dto.request.CursoRequest;
import projetofinal.com.labpcp.controller.dto.response.CursoResponse;
import projetofinal.com.labpcp.controller.dto.response.MateriaResponse;
import projetofinal.com.labpcp.entity.CursoEntity;
import projetofinal.com.labpcp.entity.MateriaEntity;
import projetofinal.com.labpcp.infra.exception.error.BadRequestException;
import projetofinal.com.labpcp.infra.exception.error.NotFoundException;
import projetofinal.com.labpcp.repository.CursoRepository;
import projetofinal.com.labpcp.repository.MateriaRepository;
import projetofinal.com.labpcp.service.serviceImpl.CursoServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CursoServiceImplTest {

    @Mock
    private CursoRepository repository;

    @Mock
    private MateriaRepository materiaRepository;

    @InjectMocks
    private CursoServiceImpl service;

    @Test
    void criar() {
        CursoRequest cursoRequest = new CursoRequest("nome", "duracao");
        CursoEntity cursoEntity = new CursoEntity(cursoRequest.nome(),cursoRequest.duracao());

        when(repository.save(any(CursoEntity.class))).thenReturn(cursoEntity);

        CursoResponse retorno = service.criar(cursoRequest);

        assertNotNull(retorno);
        assertEquals(cursoRequest.nome(), retorno.nome());
        assertEquals(cursoRequest.duracao(),retorno.duracao());
    }

    @Test
    void atualizar() {
        Long id = 1L;
        CursoRequest cursoRequest = new CursoRequest("nome", "duracao");
        CursoEntity cursoEntity = new CursoEntity(cursoRequest);
        cursoEntity.setId(id);

        when(repository.existsById(id)).thenReturn(true);
        when(repository.save(any(CursoEntity.class))).thenReturn(cursoEntity);

        service.atualizar(cursoRequest, id);

        verify(repository).save(cursoEntity);

        assertEquals(id, cursoEntity.getId());
        assertEquals(cursoRequest.nome(), cursoEntity.getNome());
        assertEquals(cursoRequest.duracao(), cursoEntity.getDuracao());
    }

    @Test
    void deletarCursoComSucesso() {
        Long id = 1L;
        CursoEntity cursoEntity = new CursoEntity("nome", "duracao");
        cursoEntity.setId(id);
        cursoEntity.setMaterias(new ArrayList<>()); // Certifique-se de que a lista de matérias está vazia

        when(repository.findById(id)).thenReturn(Optional.of(cursoEntity));

        service.deletar(id);

        verify(repository).delete(cursoEntity);
    }

    @Test
    void deletarCursoNaoEncontrado() {
        Long id = 1L;

        when(repository.findById(id)).thenReturn(Optional.empty());

        NotFoundException thrown = assertThrows(
                NotFoundException.class,
                () -> service.deletar(id),
                "Curso com id: '" + id + "' não encontrado"
        );

        assertEquals("Curso com id: '" + id + "' não encontrado", thrown.getMessage());
    }

    @Test
    void deletarCursoComMateriasAssociadas() {
        Long id = 1L;
        CursoEntity cursoEntity = new CursoEntity("nome", "duracao");
        cursoEntity.setId(id);
        List<MateriaEntity> materias = new ArrayList<>();
        materias.add(new MateriaEntity());
        cursoEntity.setMaterias(materias);

        when(repository.findById(id)).thenReturn(Optional.of(cursoEntity));

        BadRequestException thrown = assertThrows(
                BadRequestException.class,
                () -> service.deletar(id),
                "Não é possível deletar este curso por ter matérias associadas a ele"
        );

        assertEquals("Não é possível deletar este curso por ter matérias associadas a ele", thrown.getMessage());
    }

    @Test
    void listarMateriasPorCurso() {
        Long idCurso = 1L;
        CursoRequest request = new CursoRequest("nome", "duracao");
        CursoEntity cursoEntity = new CursoEntity(request);
        cursoEntity.setId(idCurso);

        MateriaEntity materia1 = new MateriaEntity("Materia 1", cursoEntity);
        materia1.setId(1L);

        MateriaEntity materia2 = new MateriaEntity("Materia 2", cursoEntity);
        materia2.setId(2L);

        List<MateriaEntity> materias = Arrays.asList(materia1, materia2);
        cursoEntity.setMaterias(materias);  // Certifique-se de definir as matérias no curso

        when(repository.findById(idCurso)).thenReturn(Optional.of(cursoEntity));
        when(materiaRepository.findByCursoId(idCurso)).thenReturn(materias);

        List<MateriaResponse> result = service.listarMateriasPorCurso(idCurso);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Materia 1", result.get(0).nome());
        assertEquals("Materia 2", result.get(1).nome());
    }

    @Test
    void listarMateriasPorCursoNaoEncontrado() {
        Long idCurso = 1L;

        when(repository.findById(idCurso)).thenReturn(Optional.empty());

        NotFoundException thrown = assertThrows(
                NotFoundException.class,
                () -> service.listarMateriasPorCurso(idCurso),
                "Curso com id: '" + idCurso + "' não encontrado"
        );

        assertEquals("Curso com id: '" + idCurso + "' não encontrado", thrown.getMessage());
    }
}