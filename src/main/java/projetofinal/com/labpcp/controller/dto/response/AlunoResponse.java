package projetofinal.com.labpcp.controller.dto.response;

import projetofinal.com.labpcp.entity.TurmaEntity;

import java.util.Date;

public record AlunoResponse(Long id,
                            String nome, String genero, Date dataNascimento,
                            String cpf, String rg, String telefone, String naturalidade,
                            String cep, String logradouro, String numero, String complemento,
                            String bairro, String uf, String referencia,
                            CadastroResponse usuario,
                            Long turmaId) {
}
