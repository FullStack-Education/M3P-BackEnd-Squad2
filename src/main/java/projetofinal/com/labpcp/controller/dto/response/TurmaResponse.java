package projetofinal.com.labpcp.controller.dto.response;

import java.time.LocalDate;

public record TurmaResponse( Long id, String nome, LocalDate dataInicio, LocalDate dataTermino, String horario, Long docenteId, Long cursoId) {}
