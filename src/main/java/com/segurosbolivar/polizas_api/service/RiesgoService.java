package com.segurosbolivar.polizas_api.service;

import com.segurosbolivar.polizas_api.exception.BusinessException;
import com.segurosbolivar.polizas_api.model.Poliza;
import com.segurosbolivar.polizas_api.model.Riesgo;
import com.segurosbolivar.polizas_api.repository.PolizaRepository;
import com.segurosbolivar.polizas_api.repository.RiesgoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RiesgoService {

    private final RiesgoRepository riesgoRepository;
    private final PolizaRepository polizaRepository;

    public RiesgoService(RiesgoRepository riesgoRepository, PolizaRepository polizaRepository) {
        this.riesgoRepository = riesgoRepository;
        this.polizaRepository = polizaRepository;
    }

    @Transactional
    public Riesgo agregarRiesgo(Long polizaId, String descripcion) {
        Poliza poliza = polizaRepository.findById(polizaId)
                .orElseThrow(() -> new BusinessException("Póliza no encontrada"));

        if ("INDIVIDUAL".equalsIgnoreCase(poliza.getTipo())) {
            long count = riesgoRepository.countByPolizaId(polizaId);
            if (count >= 1) {
                throw new BusinessException("Una póliza individual solo puede tener 1 riesgo");
            }
        }

        if (!"COLECTIVA".equalsIgnoreCase(poliza.getTipo()) && !"INDIVIDUAL".equalsIgnoreCase(poliza.getTipo())) {
            throw new BusinessException("Tipo de póliza desconocido");
        }

        Riesgo riesgo = new Riesgo();
        riesgo.setDescripcion(descripcion);
        riesgo.setEstado("ACTIVO");
        riesgo.setPoliza(poliza);

        return riesgoRepository.save(riesgo);
    }

    @Transactional
    public void cancelarRiesgo(Long riesgoId) {
        Riesgo riesgo = riesgoRepository.findById(riesgoId)
                .orElseThrow(() -> new BusinessException("Riesgo no encontrado"));
        riesgo.setEstado("CANCELADO");
        riesgoRepository.save(riesgo);
    }
}
