package projetofinal.com.labpcp.infra.exception.error;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String message) {
        super(message);
    }
}
