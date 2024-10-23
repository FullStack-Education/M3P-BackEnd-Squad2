package projetofinal.com.labpcp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import projetofinal.com.labpcp.infra.generic.GenericEntity;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = "docentes")
public class DocenteEntity extends GenericEntity {

    private String nome;
    private String telefone;
    private String genero;
    private String estadoCivil;
    private Date dataNascimento;
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

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private UsuarioEntity usuario;

    public DocenteEntity() {
    }

    public DocenteEntity(String nome, String telefone, String genero, String estadoCivil, Date dataNascimento, String cpf, String rg, String naturalidade, String cep, String logradouro, String numero, String complemento, String bairro, String uf, String referencia, UsuarioEntity usuario) {
        this.nome = nome;
        this.telefone = telefone;
        this.genero = genero;
        this.estadoCivil = estadoCivil;
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
        this.usuario = usuario;
    }
}
