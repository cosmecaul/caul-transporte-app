// src/main/java/cl/transporte/caul/service/DashboardService.java
package cl.transporte.caul.service;

import cl.transporte.caul.dto.DashboardStatsDTO;
import cl.transporte.caul.dto.ServicioResumenDTO;

import java.util.List;

public interface DashboardService {

    DashboardStatsDTO obtenerStatsDashboard();

    /**
     * Rango: HOY, AYER, ULTIMOS_7, ULTIMOS_30
     */
    List<ServicioResumenDTO> obtenerServiciosPorRango(String rango);
}
