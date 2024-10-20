package projetofinal.com.labpcp.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "usuarios")
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String senha;

    @ManyToOne
    @JoinColumn(name = "perfil_id")
    private PerfilEntity perfil;

    public UsuarioEntity() {};
    public UsuarioEntity(String email, String senha, PerfilEntity perfil){
        this.email = email;
        this.senha = senha;
        this.perfil = perfil;
    };
}
