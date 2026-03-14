package com.segurosbolivar.polizas_api.controller;

import com.segurosbolivar.polizas_api.service.RiesgoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/riesgos")
public class RiesgoController {

    private final RiesgoService riesgoService;

    public RiesgoController(RiesgoService riesgoService) {
        this.riesgoService = riesgoService;
    }

    // POST /riesgos/{id}/cancelar
    @PostMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelar(@PathVariable Long id) {
        riesgoService.cancelarRiesgo(id);
        return ResponseEntity.noContent().build();
    }
}
