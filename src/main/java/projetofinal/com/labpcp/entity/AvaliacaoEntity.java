package projetofinal.com.labpcp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import projetofinal.com.labpcp.infra.generic.GenericEntity;

import java.math.BigDecimal;
import java.time.LocalDate;


@Data
@Entity
@Table(name = "avaliacoes")
public class AvaliacaoEntity extends GenericEntity {

    private String nome;

    private BigDecimal valor;


    private LocalDate dataAvaliacao;

    @ManyToOne
    @JoinColumn(name = "aluno_id")
    private AlunoEntity aluno;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private DocenteEntity docente;

    @ManyToOne
    @JoinColumn(name = "materia_id")
    private MateriaEntity materia;

    public AvaliacaoEntity() {
    }

    public AvaliacaoEntity(String nome, BigDecimal valor, LocalDate dataAvaliacao, AlunoEntity aluno, DocenteEntity docente, MateriaEntity materia) {
        this.nome = nome;
        this.valor = valor;
        this.dataAvaliacao = dataAvaliacao;
        this.aluno = aluno;
        this.docente = docente;
        this.materia = materia;
    }
}
