package projetofinal.com.labpcp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projetofinal.com.labpcp.controller.dto.DashboardDto;
import projetofinal.com.labpcp.controller.dto.response.CursoResponse;
import projetofinal.com.labpcp.service.DashboardService;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;

import static projetofinal.com.labpcp.infra.Util.AcessoUtil.verificarPermicao;

@Slf4j
@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private DashboardService dashboardService;
    private final List<String> permicoes;

    @Autowired
    protected DashboardController(DashboardService dashboardService) {
        this.permicoes = Arrays.asList("administrador");
        this.dashboardService = dashboardService;
    }

    @Operation(summary = "buscar dados do dashboard")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "dados do dashboard encontrados", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DashboardDto.class))
            }),
            @ApiResponse(responseCode = "401", description = "acesso não autorizado", content = @Content)
    })
    @GetMapping
    public DashboardDto getDashboard(@Parameter(hidden = true)  @RequestHeader(name = "Authorization") String token) throws Exception{
        verificarPermicao(token, permicoes);

        return dashboardService.getDashboardData();
    }

}
