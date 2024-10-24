package projetofinal.com.labpcp.controller.dto.response;

import java.util.List;

public record CursoResponse(Long id, String nome, String duracao, List<MateriaResponse> materias) {
}
