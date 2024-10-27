package projetofinal.com.labpcp.infra.Util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import projetofinal.com.labpcp.infra.ApplicationContext.ApplicationContextProvider;
import projetofinal.com.labpcp.infra.exception.error.AuthException;
import projetofinal.com.labpcp.service.serviceImpl.AuthServiceImpl;

import java.util.List;

@Slf4j
public class AcessoUtil {

    public static void verificarPermicao(String token, List<String> permicoes) {
        log.info("Iniciando verificação de autenticação!");

        ApplicationContext context = ApplicationContextProvider.getApplicationContext();
        AuthServiceImpl authService =  context.getBean(AuthServiceImpl.class);

        String papelToken =  authService.buscaCampoToken(token.substring(7), "scope");

        for (String permicao : permicoes) {
            if (permicao.equalsIgnoreCase(papelToken)){
                log.info("Usuario tem acesso a essa funcionalidade!");
                return;
            }
        }

        log.error("Usuario não tem acesso a essa funcionalidade!");
        throw new AuthException("Usuario não tem acesso a essa funcionalidade");
    }
}
