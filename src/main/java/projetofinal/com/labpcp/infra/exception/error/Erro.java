package projetofinal.com.labpcp.infra.exception.error;

import lombok.Builder;
import lombok.Data;

import javax.swing.*;

@Data
@Builder
public class Erro {
    private String codigo;
    private String mensagem;
}
