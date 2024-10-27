package projetofinal.com.labpcp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import projetofinal.com.labpcp.controller.dto.request.AlunoRequest;
import projetofinal.com.labpcp.controller.dto.response.AlunoResponse;
import projetofinal.com.labpcp.infra.generic.GenericController;
import projetofinal.com.labpcp.service.AlunoService;

import java.util.List;

@RestController
@RequestMapping("alunos")
public class AlunoController extends GenericController<AlunoService, AlunoResponse, AlunoRequest> {

    protected AlunoController() {
        super(List.of("administrador"));
    }

}
