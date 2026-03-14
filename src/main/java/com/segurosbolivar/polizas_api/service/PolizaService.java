package com.segurosbolivar.polizas_api.service;

import com.segurosbolivar.polizas_api.exception.BusinessException;
import com.segurosbolivar.polizas_api.model.Poliza;
import com.segurosbolivar.polizas_api.model.Riesgo;
import com.segurosbolivar.polizas_api.repository.PolizaRepository;
import com.segurosbolivar.polizas_api.repository.RiesgoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class PolizaService {

    private final PolizaRepository polizaRepository;
    private final RiesgoRepository riesgoRepository;

    public PolizaService(PolizaRepository polizaRepository, RiesgoRepository riesgoRepository) {
        this.polizaRepository = polizaRepository;
        this.riesgoRepository = riesgoRepository;
    }

    public List<Poliza> listarPorTipoYEstado(String tipo, String estado) {
        return polizaRepository.findByTipoAndEstado(tipo, estado);
    }

    public List<Riesgo> listarRiesgos(Long polizaId) {
        return riesgoRepository.findByPolizaId(polizaId);
    }

    @Transactional
    public Poliza renovar(Long polizaId, BigDecimal ipc) {
        Poliza poliza = polizaRepository.findById(polizaId)
                .orElseThrow(() -> new BusinessException("Póliza no encontrada"));

        if ("CANCELADA".equalsIgnoreCase(poliza.getEstado())) {
            throw new BusinessException("No se puede renovar una póliza cancelada");
        }

        BigDecimal factor = BigDecimal.ONE.add(ipc);
        poliza.setCanonMensual(poliza.getCanonMensual().multiply(factor).setScale(2, RoundingMode.HALF_UP));
        poliza.setPrima(poliza.getPrima().multiply(factor).setScale(2, RoundingMode.HALF_UP));
        poliza.setEstado("RENOVADA");

        return polizaRepository.save(poliza);
    }

    @Transactional
    public void cancelar(Long polizaId) {
        Poliza poliza = polizaRepository.findById(polizaId)
                .orElseThrow(() -> new BusinessException("Póliza no encontrada"));

        // Cancelar todos los riesgos asociados
        List<Riesgo> riesgos = riesgoRepository.findByPolizaId(polizaId);
        for (Riesgo r : riesgos) {
            r.setEstado("CANCELADO");
        }
        riesgoRepository.saveAll(riesgos);

        poliza.setEstado("CANCELADA");
        polizaRepository.save(poliza);
    }
}
