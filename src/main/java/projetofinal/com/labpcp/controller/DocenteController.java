package projetofinal.com.labpcp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import projetofinal.com.labpcp.controller.dto.request.DocenteRequest;
import projetofinal.com.labpcp.controller.dto.response.DocenteResponse;
import projetofinal.com.labpcp.infra.generic.GenericController;
import projetofinal.com.labpcp.service.DocenteService;

import java.util.List;

@RestController
@RequestMapping("docentes")
public class DocenteController extends GenericController<DocenteService, DocenteResponse, DocenteRequest> {
    protected DocenteController() {
        super(List.of("administrador"));
    }
}
