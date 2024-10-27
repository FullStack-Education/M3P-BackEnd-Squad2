package projetofinal.com.labpcp.controller.dto.request;

import projetofinal.com.labpcp.entity.UsuarioEntity;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public record DocenteRequest(String nome, String telefone, String genero, String estadoCivil, LocalDate dataNascimento,
                             String cpf, String rg, String naturalidade, String cep, String logradouro,
                             String numero, String complemento, String bairro, String uf, String referencia,
                             List<Long> materiasIds, String email, String senha, UsuarioEntity usuario) {
}
