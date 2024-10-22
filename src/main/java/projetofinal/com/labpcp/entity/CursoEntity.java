package projetofinal.com.labpcp.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import projetofinal.com.labpcp.controller.dto.request.CursoRequest;
import projetofinal.com.labpcp.infra.generic.GenericEntity;

@Data
@Entity
@Table(name = "cursos")
public class CursoEntity extends GenericEntity {

    private String nome;
    private String duracao;

    public CursoEntity () {}

    public CursoEntity (CursoRequest request) {
        this.nome = request.nome();
        this.duracao = request.duracao();
    }
}
