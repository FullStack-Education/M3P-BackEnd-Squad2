package projetofinal.com.labpcp.entity;

import jakarta.persistence.*;
import lombok.Data;
import projetofinal.com.labpcp.infra.generic.GenericEntity;

import java.util.List;

@Data
@Entity
@Table(name = "materias")
public class MateriaEntity extends GenericEntity {

    private String nome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id", nullable = false)
    private CursoEntity curso;

    @ManyToMany(mappedBy = "materias",fetch = FetchType.LAZY)
    private List<DocenteEntity> docentes;

    public MateriaEntity() {
    }

    public MateriaEntity(String nome, CursoEntity curso) {
        this.nome = nome;
        this.curso = curso;
    }
}
