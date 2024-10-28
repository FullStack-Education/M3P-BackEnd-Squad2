package projetofinal.com.labpcp.controller.dto.response;


import java.time.LocalDate;

public record AlunoResponse(Long id, String nome, String telefone, String genero, LocalDate dataNascimento, String cpf, String rg, String naturalidade, String cep, String logradouro, String numero, String complemento, String bairro, String uf, String referencia, TurmaResponse turma, CadastroResponse usuario) {
}
