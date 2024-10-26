package projetofinal.com.labpcp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import projetofinal.com.labpcp.controller.dto.request.AlunoRequest;
import projetofinal.com.labpcp.infra.generic.GenericEntity;

import java.util.Date;

@Data
@Entity
@DynamicInsert
@Table(name = "alunos")
public class AlunoEntity extends GenericEntity {

    private String nome;
    private String genero;
    private Date dataNascimento;
    private String cpf;
    private String rg;
    private String telefone;
    private String naturalidade;
    private String cep;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String uf;
    private String referencia;

    @ManyToOne
    @JoinColumn(name = "turma_id")
    private TurmaEntity turma;

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioEntity usuario;

    public AlunoEntity() {}

    public AlunoEntity(AlunoRequest request, TurmaEntity turma, UsuarioEntity usuario) {
        this.nome = request.nome();
        this.genero = request.genero();
        this.dataNascimento = request.dataNascimento();
        this.cpf = request.cpf();
        this.rg = request.rg();
        this.telefone = request.telefone();
        this.naturalidade = request.naturalidade();
        this.cep = request.cep();
        this.logradouro = request.logradouro();
        this.numero = request.numero();
        this.complemento = request.complemento();
        this.bairro = request.bairro();
        this.uf = request.uf();
        this.referencia = request.referencia();
        this.usuario = usuario;
        this.turma = turma;
    }

}
