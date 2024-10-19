package projetofinal.com.labpcp.infra.exception.error;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String message) {
        super(message);
    }
}
