package cl.transporte.caul.service;

import cl.transporte.caul.dto.ServicioHistoricoResumenDTO;
import cl.transporte.caul.model.Servicio;

import java.time.LocalDate;
import java.util.List;

public interface ServicioHistoricoService {

    List<Servicio> listarServiciosConductor(Long conductorId, LocalDate desde, LocalDate hasta);

    List<ServicioHistoricoResumenDTO> resumenPorSemana(Long conductorId, LocalDate desde, LocalDate hasta);

    List<ServicioHistoricoResumenDTO> resumenPorMes(Long conductorId, LocalDate desde, LocalDate hasta);
}
