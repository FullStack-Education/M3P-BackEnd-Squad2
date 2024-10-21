package projetofinal.com.labpcp.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "perfis")
public class PerfilEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    public PerfilEntity() {}

    public PerfilEntity(String nome) {
        this.nome = nome;
    }
}
