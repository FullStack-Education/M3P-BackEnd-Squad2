package projetofinal.com.labpcp.controller.dto.request;

import java.time.LocalDate;

public record TurmaRequest(String nome, LocalDate dataInicio, LocalDate dataTermino, String horario, Long docenteId, Long cursoId) {}
