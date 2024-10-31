package projetofinal.com.labpcp.controller.dto.request;


import projetofinal.com.labpcp.entity.UsuarioEntity;

import java.time.LocalDate;

public record AlunoRequest(String nome, String telefone, String genero, LocalDate dataNascimento, String cpf, String rg, String naturalidade, String cep, String logradouro, String numero, String complemento, String bairro, String uf, String referencia, Long turma,String email, String senha,  UsuarioEntity usuario) { }
