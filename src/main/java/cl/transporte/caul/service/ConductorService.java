package cl.transporte.caul.service;

import cl.transporte.caul.dto.ConductorCreateRequest;
import cl.transporte.caul.dto.ConductorUpdateRequest;
import cl.transporte.caul.dto.ConductorResponse;

import java.util.List;

public interface ConductorService {

    ConductorResponse createConductor(ConductorCreateRequest request);

    ConductorResponse updateConductor(Long id, ConductorUpdateRequest request);

    ConductorResponse getConductorById(Long id);

    ConductorResponse getConductorByRut(String rut);

    List<ConductorResponse> listConductores(Boolean soloActivos);
}
