package projetofinal.com.labpcp.controller.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AvaliacaoRequest(String nome, BigDecimal valor, LocalDate dataAvaliacao, Long alunoId, Long docenteId, Long materiaId) {}
