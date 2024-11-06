package projetofinal.com.labpcp.service.serviceImpl;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projetofinal.com.labpcp.controller.dto.DashboardDto;
import projetofinal.com.labpcp.repository.AlunoRepository;
import projetofinal.com.labpcp.repository.DocenteRepository;
import projetofinal.com.labpcp.repository.TurmaRepository;
import projetofinal.com.labpcp.service.DashboardService;

@Slf4j
@Service
public class DashboardServiceImpl implements DashboardService {
    @Autowired
    private AlunoRepository alunoRepository;
    @Autowired
    private TurmaRepository turmaRepository;
    @Autowired
    private DocenteRepository docenteRepository;

    @Override
    public DashboardDto getDashboardData() {
        Long totalAlunos = alunoRepository.count();
        Long totalTurmas = turmaRepository.count();
        Long totalDocentes = docenteRepository.countByUsuarioPerfilNome("docente");

        return new DashboardDto(totalAlunos, totalTurmas, totalDocentes);
    }
}
