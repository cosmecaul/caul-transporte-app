package cl.transporte.caul.service;

import cl.transporte.caul.dto.ConductorHistoricoViewDTO;

import java.time.LocalDate;
import java.util.Date;

public interface ConductorHistoricoService {
    ConductorHistoricoViewDTO getHistoricoConductor(Long idConductor, Date desde, Date hasta);
}
