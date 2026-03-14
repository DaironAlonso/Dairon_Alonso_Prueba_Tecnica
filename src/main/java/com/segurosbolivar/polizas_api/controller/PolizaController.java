package com.segurosbolivar.polizas_api.controller;

import com.segurosbolivar.polizas_api.model.Poliza;
import com.segurosbolivar.polizas_api.model.Riesgo;
import com.segurosbolivar.polizas_api.service.PolizaService;
import com.segurosbolivar.polizas_api.service.RiesgoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/polizas")
public class PolizaController {

    private final PolizaService polizaService;
    private final RiesgoService riesgoService;

    public PolizaController(PolizaService polizaService, RiesgoService riesgoService) {
        this.polizaService = polizaService;
        this.riesgoService = riesgoService;
    }

    // GET /polizas?tipo=&estado=
    @GetMapping
    public List<Poliza> listar(@RequestParam String tipo,
                               @RequestParam String estado) {
        return polizaService.listarPorTipoYEstado(tipo, estado);
    }

    // GET /polizas/{id}/riesgos
    @GetMapping("/{id}/riesgos")
    public List<Riesgo> listarRiesgos(@PathVariable Long id) {
        return polizaService.listarRiesgos(id);
    }

    // POST /polizas/{id}/renovar
    @PostMapping("/{id}/renovar")
    public Poliza renovar(@PathVariable Long id,
                          @RequestBody Map<String, BigDecimal> body) {
        BigDecimal ipc = body.getOrDefault("ipc", BigDecimal.ZERO);
        return polizaService.renovar(id, ipc);
    }

    // POST /polizas/{id}/cancelar
    @PostMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelar(@PathVariable Long id) {
        polizaService.cancelar(id);
        return ResponseEntity.noContent().build();
    }

    // POST /polizas/{id}/riesgos
    @PostMapping("/{id}/riesgos")
    public Riesgo agregarRiesgo(@PathVariable Long id,
                                @RequestBody Map<String, String> body) {
        String descripcion = body.getOrDefault("descripcion", "Riesgo");
        return riesgoService.agregarRiesgo(id, descripcion);
    }
}

