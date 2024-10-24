package projetofinal.com.labpcp.controller.dto.request;

import java.util.Date;

public record DocenteRequest(String nome, String telefone, String genero, String estadoCivil, Date dataNascimento, String cpf, String rg, String naturalidade, String cep, String logradouro, String numero, String complemento, String bairro, String uf, String referencia) {
}
