package com.segurosbolivar.polizas_api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class CoreMockController {

    private static final Logger log = LoggerFactory.getLogger(CoreMockController.class);

    @PostMapping("/core-mock/evento")
    public ResponseEntity<Map<String, Object>> registrarEvento(@RequestBody Map<String, Object> body) {
        log.info("Evento enviado al CORE: {}", body);
        return ResponseEntity.ok(Map.of("status", "recibido"));
    }
}
