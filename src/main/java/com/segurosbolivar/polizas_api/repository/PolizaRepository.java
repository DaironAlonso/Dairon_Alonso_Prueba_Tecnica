package com.segurosbolivar.polizas_api.repository;

import com.segurosbolivar.polizas_api.model.Poliza;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PolizaRepository extends JpaRepository<Poliza, Long> {

    List<Poliza> findByTipoAndEstado(String tipo, String estado);
}

