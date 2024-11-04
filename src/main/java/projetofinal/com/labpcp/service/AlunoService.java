package projetofinal.com.labpcp.service;

import projetofinal.com.labpcp.controller.dto.request.AlunoRequest;
import projetofinal.com.labpcp.controller.dto.response.AlunoResponse;
import projetofinal.com.labpcp.controller.dto.response.AvaliacaoResponse;
import projetofinal.com.labpcp.infra.generic.GenericService;

import java.util.List;

public interface AlunoService extends GenericService<AlunoResponse, AlunoRequest> {

    List<AvaliacaoResponse> listarAvaliacoesPorAluno(Long idAluno);

    double pontuacaoTotalAluno(Long idAluno);
}
