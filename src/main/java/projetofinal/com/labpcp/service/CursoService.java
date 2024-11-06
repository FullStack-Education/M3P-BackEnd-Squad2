package projetofinal.com.labpcp.service;


import projetofinal.com.labpcp.controller.dto.request.CursoRequest;
import projetofinal.com.labpcp.controller.dto.response.CursoResponse;
import projetofinal.com.labpcp.controller.dto.response.MateriaResponse;
import projetofinal.com.labpcp.infra.generic.GenericService;

import java.util.List;

public interface CursoService extends GenericService<CursoResponse, CursoRequest> {

    List<MateriaResponse> listarMateriasPorCurso(Long idCurso);
}
