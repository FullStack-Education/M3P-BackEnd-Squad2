package projetofinal.com.labpcp.infra.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import projetofinal.com.labpcp.infra.exception.error.*;

@Slf4j
@ControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handleAuthenticationException(AuthenticationException ex) {
        Erro erro = Erro.builder()
                .codigo("401 (Unauthorized)")
                .mensagem("Credenciais inválidas. O usuário não está autorizado a acessar o sistema.")
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(erro);
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<?> handleAuthException(AuthException ex) {
        Erro erro = Erro.builder()
                .codigo("401 (Unauthorized)")
                .mensagem(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(erro);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException ex) {
        Erro erro = Erro.builder()
                .codigo("403 (Forbidden)")
                .mensagem("Acesso proibido. O usuário não tem permissão para acessar este recurso.")
                .build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(erro);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handler(Exception e) {
        Erro erro = Erro.builder()
                .codigo("400 (Bad Request)")
                .mensagem("Requisição inválida, por exemplo, dados ausentes ou incorretos.")
                .build();
        return ResponseEntity.status(400).body(erro);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handler(NotFoundException e) {
        Erro erro = Erro.builder()
                .codigo("404 (Not Found)")
                .mensagem(e.getMessage())
                .build();
        return ResponseEntity.status(404).body(erro);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handler(BadRequestException e) {
        Erro erro = Erro.builder()
                .codigo("400 (Bad Request)")
                .mensagem(e.getMessage())
                .build();
        return ResponseEntity.status(400).body(erro);
    }

    @ExceptionHandler(EntityAlreadyExists.class)
    public ResponseEntity<?> handler(EntityAlreadyExists e) {
        Erro erro = Erro.builder()
                .codigo("400 (Bad Request)")
                .mensagem(e.getMessage())
                .build();
        return ResponseEntity.status(400).body(erro);
    }
}
