package projetofinal.com.labpcp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import projetofinal.com.labpcp.controller.dto.request.MateriaRequest;
import projetofinal.com.labpcp.controller.dto.response.MateriaResponse;
import projetofinal.com.labpcp.infra.generic.GenericController;
import projetofinal.com.labpcp.service.MateriaService;

import java.util.List;

@RestController
@RequestMapping("materias")
public class MateriaController extends GenericController<MateriaService, MateriaResponse, MateriaRequest> {
    protected MateriaController() {
        super(List.of("administrador", "docente"));
    }
}
