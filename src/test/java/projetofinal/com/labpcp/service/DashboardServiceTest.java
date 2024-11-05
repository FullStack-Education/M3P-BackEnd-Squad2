package projetofinal.com.labpcp.service;

import jakarta.persistence.Table;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import projetofinal.com.labpcp.controller.dto.DashboardDto;
import projetofinal.com.labpcp.repository.AlunoRepository;
import projetofinal.com.labpcp.repository.DocenteRepository;
import projetofinal.com.labpcp.repository.TurmaRepository;
import projetofinal.com.labpcp.service.serviceImpl.DashboardServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DashboardServiceTest {

    @Mock
    private AlunoRepository alunoRepository;

    @Mock
    private TurmaRepository turmaRepository;

    @Mock
    private DocenteRepository docenteRepository;

    @InjectMocks
    private DashboardServiceImpl service;

    @Test
    void getDashboardData() {

        when(alunoRepository.count()).thenReturn(100L);
        when(turmaRepository.count()).thenReturn(10L);
        when(docenteRepository.countByUsuarioPerfilNome("docente")).thenReturn(5L);

        DashboardDto dashboardDto = service.getDashboardData();

        assertNotNull(dashboardDto);
        assertEquals(100L, dashboardDto.totalAlunos());
        assertEquals(10L, dashboardDto.totalTurmas());
        assertEquals(5L, dashboardDto.totalDocentes());
    }
}