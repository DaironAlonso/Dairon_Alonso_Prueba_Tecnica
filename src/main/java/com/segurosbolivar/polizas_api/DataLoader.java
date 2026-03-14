package com.segurosbolivar.polizas_api;

import com.segurosbolivar.polizas_api.model.Poliza;
import com.segurosbolivar.polizas_api.model.Riesgo;
import com.segurosbolivar.polizas_api.repository.PolizaRepository;
import com.segurosbolivar.polizas_api.repository.RiesgoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final PolizaRepository polizaRepository;
    private final RiesgoRepository riesgoRepository;

    public DataLoader(PolizaRepository polizaRepository, RiesgoRepository riesgoRepository) {
        this.polizaRepository = polizaRepository;
        this.riesgoRepository = riesgoRepository;
    }

    @Override
    public void run(String... args) {
        // Póliza INDIVIDUAL con 1 riesgo
        Poliza individual = new Poliza();
        individual.setTipo("INDIVIDUAL");
        individual.setEstado("ACTIVA");
        individual.setVigenciaInicio(LocalDate.now());
        individual.setVigenciaFin(LocalDate.now().plusYears(1));
        individual.setCanonMensual(new BigDecimal("1000000"));
        individual.setPrima(new BigDecimal("12000000"));
        individual = polizaRepository.save(individual);

        Riesgo riesgoInd = new Riesgo();
        riesgoInd.setDescripcion("Riesgo póliza individual");
        riesgoInd.setEstado("ACTIVO");
        riesgoInd.setPoliza(individual);
        riesgoRepository.save(riesgoInd);

        // Póliza COLECTIVA sin riesgos
        Poliza colectiva = new Poliza();
        colectiva.setTipo("COLECTIVA");
        colectiva.setEstado("ACTIVA");
        colectiva.setVigenciaInicio(LocalDate.now());
        colectiva.setVigenciaFin(LocalDate.now().plusYears(1));
        colectiva.setCanonMensual(new BigDecimal("2000000"));
        colectiva.setPrima(new BigDecimal("24000000"));
        colectiva = polizaRepository.save(colectiva);
    }
}

