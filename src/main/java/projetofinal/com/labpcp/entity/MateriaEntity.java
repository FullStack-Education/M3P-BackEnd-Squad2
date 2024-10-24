package projetofinal.com.labpcp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import projetofinal.com.labpcp.infra.generic.GenericEntity;

@Data
@Entity
@Table(name = "materias")
public class MateriaEntity extends GenericEntity {

    private String nome;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    private CursoEntity curso;

    public MateriaEntity() {
    }

    public MateriaEntity(String nome, CursoEntity curso) {
        this.nome = nome;
        this.curso = curso;
    }
}
