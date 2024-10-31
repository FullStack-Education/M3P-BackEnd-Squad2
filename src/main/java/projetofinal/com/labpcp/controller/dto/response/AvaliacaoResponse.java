package projetofinal.com.labpcp.controller.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AvaliacaoResponse(Long id, String nome, BigDecimal valor, LocalDate dataAvaliacao, Long alunoId, Long docenteId, Long materiaId) {}
