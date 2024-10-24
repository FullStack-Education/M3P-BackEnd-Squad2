package projetofinal.com.labpcp.controller;

import org.springframework.web.bind.annotation.*;
import projetofinal.com.labpcp.controller.dto.request.TurmaRequest;
import projetofinal.com.labpcp.controller.dto.response.TurmaResponse;
import projetofinal.com.labpcp.infra.generic.GenericController;
import projetofinal.com.labpcp.service.TurmaService;

import java.util.List;

@RestController
@RequestMapping("turmas")
public class TurmaController extends GenericController<TurmaService, TurmaResponse, TurmaRequest> {

    public TurmaController() {
        super(List.of("administrador", "docente"));

    }

}
