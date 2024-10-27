package projetofinal.com.labpcp.entity;

import jakarta.persistence.*;
import lombok.Data;
import projetofinal.com.labpcp.infra.generic.GenericEntity;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "docentes")
public class DocenteEntity extends GenericEntity {

    private String nome;
    private String telefone;
    private String genero;
    private String estadoCivil;
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



    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioEntity usuario;


    @ManyToMany
    @JoinTable(
            name = "docentes_materia",
            joinColumns = @JoinColumn(name = "docente_id"),
            inverseJoinColumns = @JoinColumn(name = "materia_id")
    )
    private List<MateriaEntity> materias;

    public DocenteEntity() {
    }

    public DocenteEntity(String nome, String telefone, String genero, String estadoCivil, LocalDate dataNascimento, String cpf, String rg, String naturalidade, String cep, String logradouro, String numero, String complemento, String bairro, String uf, String referencia, UsuarioEntity usuario,  List<MateriaEntity> materias) {
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
        this.materias = materias;
    }
}
