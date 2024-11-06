package projetofinal.com.labpcp.infra.exception.error;

public class EntityAlreadyExists extends RuntimeException {

    public EntityAlreadyExists(String tabela, String campo, String valor) {
        super("tabela '" + tabela + "' com campo '" + campo + "' com valor igual a '" + valor + "' já existe");
    }
}
