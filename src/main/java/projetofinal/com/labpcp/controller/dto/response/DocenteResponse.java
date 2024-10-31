package projetofinal.com.labpcp.controller.dto.response;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public record DocenteResponse(Long id, String nome, String telefone, String genero, String estadoCivil, LocalDate dataNascimento, String cpf, String rg, String naturalidade, String cep, String logradouro, String numero, String complemento, String bairro, String uf, String referencia, CadastroResponse usuario, List<MateriaResponse> materias) {
}
