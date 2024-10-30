package projetofinal.com.labpcp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projetofinal.com.labpcp.controller.dto.DashboardDto;
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
    @GetMapping
    public DashboardDto getDashboard(@RequestHeader(name = "Authorization") String token) throws Exception{
        verificarPermicao(token, permicoes);

        return dashboardService.getDashboardData();
    }

}
