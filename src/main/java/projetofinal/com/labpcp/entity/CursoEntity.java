package projetofinal.com.labpcp.entity;


import jakarta.persistence.*;
import lombok.Data;
import projetofinal.com.labpcp.controller.dto.request.CursoRequest;
import projetofinal.com.labpcp.infra.generic.GenericEntity;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "cursos")
public class CursoEntity extends GenericEntity {

    private String nome;
    private String duracao;

    @OneToMany(mappedBy = "curso", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<MateriaEntity> materias = new ArrayList<>();

    public CursoEntity () {}

    public CursoEntity (String nome, String duracao) {}

    public CursoEntity (CursoRequest request) {
        this.nome = request.nome();
        this.duracao = request.duracao();
    }
}
