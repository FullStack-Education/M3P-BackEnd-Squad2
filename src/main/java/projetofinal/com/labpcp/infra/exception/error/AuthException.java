package projetofinal.com.labpcp.infra.exception.error;

public class AuthException extends RuntimeException{

    public AuthException(String perfil) {
        super("usuário com perfil: " + perfil + " não tem acesso a essa funcionalidade");
    }
}
