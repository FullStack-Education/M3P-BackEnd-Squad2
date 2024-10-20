package projetofinal.com.labpcp.controller;


import org.springframework.web.bind.annotation.*;
import projetofinal.com.labpcp.controller.dto.request.CursoRequest;
import projetofinal.com.labpcp.controller.dto.response.CursoResponse;
import projetofinal.com.labpcp.infra.generic.GenericController;
import projetofinal.com.labpcp.service.CursoService;

import java.util.List;


@RestController
@RequestMapping ("cursos")
public class CursoController extends GenericController<CursoService, CursoResponse, CursoRequest> {
    protected CursoController() {
        super(List.of("administrador", "docente"));
    }
}
