package projetofinal.com.labpcp.service;

import org.junit.jupiter.api.BeforeEach;
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
import projetofinal.com.labpcp.repository.CursoRepository;
import projetofinal.com.labpcp.repository.MateriaRepository;
import projetofinal.com.labpcp.service.serviceImpl.MateriaServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
        materia = new MateriaEntity("POO", curso);
    }

    @Test
    void cadastrarMateria() {
        when(cursoRepository.findById(curso.getId()))
                .thenReturn(Optional.of(curso));

        MateriaResponse resultado = materiaService.criar(new MateriaRequest(materia.getNome(), curso.getId()));

        assertEquals(materia.getNome(), resultado.nome());
        assertEquals(materia.getCurso().getId(), resultado.curso_id());
    }
}
