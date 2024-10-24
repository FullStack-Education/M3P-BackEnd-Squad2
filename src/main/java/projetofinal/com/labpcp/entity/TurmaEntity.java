package projetofinal.com.labpcp.entity;


import jakarta.persistence.*;
import lombok.Data;
import projetofinal.com.labpcp.infra.generic.GenericEntity;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "turmas")
public class TurmaEntity extends GenericEntity {

    private String nome;

    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "data_termino", nullable = false)
    private LocalDate dataTermino;

    @Column(name = "horario", nullable = false)
    private String horario;

    @ManyToOne
    @JoinColumn(name = "docente_id", nullable = false)
    private DocenteEntity docente;

    @ManyToOne
    @JoinColumn(name = "curso_id", nullable = false)
    private CursoEntity curso;

    public TurmaEntity() {
    }
    public TurmaEntity(String nome, LocalDate dataInicio, LocalDate dataTermino, String horario, DocenteEntity docente, CursoEntity curso) {
        this.nome = nome;
        this.dataInicio = dataInicio;
        this.dataTermino = dataTermino;
        this.horario = horario;
        this.docente = docente;
        this.curso = curso;
    }
}
