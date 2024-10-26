package projetofinal.com.labpcp.controller.dto.request;

import java.util.Date;

public record AlunoRequest(String nome, String genero, Date dataNascimento,
                           String cpf, String rg, String telefone, String naturalidade,
                           String cep, String logradouro, String numero, String complemento,
                           String bairro, String uf, String referencia,
                           Long turmaId,
                           String email, String senha) {

}
