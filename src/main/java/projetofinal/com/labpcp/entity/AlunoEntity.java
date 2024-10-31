package projetofinal.com.labpcp.entity;

import jakarta.persistence.*;
import lombok.Data;
import projetofinal.com.labpcp.infra.generic.GenericEntity;

import java.time.LocalDate;


@Data
@Entity
@Table(name = "alunos")
public class AlunoEntity extends GenericEntity {

    private String nome;
    private String telefone;
    private String genero;
    private LocalDate dataNascimento;
    private String cpf;
    private String rg;
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

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "usuario_id")
    private UsuarioEntity usuario;

    public AlunoEntity() {}
    public AlunoEntity(String nome,String telefone, String genero, LocalDate dataNascimento, String cpf, String rg,  String naturalidade,  String cep, String logradouro, String numero, String complemento, String bairro, String uf, String referencia, TurmaEntity turma, UsuarioEntity usuario) {
        this.nome = nome;
        this.telefone = telefone;
        this.genero = genero;
        this.dataNascimento = dataNascimento;
        this.cpf = cpf;
        this.rg = rg;
        this.naturalidade = naturalidade;
        this.cep = cep;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.uf = uf;
        this.referencia = referencia;
        this.turma = turma;
        this.usuario = usuario;

    }

}
