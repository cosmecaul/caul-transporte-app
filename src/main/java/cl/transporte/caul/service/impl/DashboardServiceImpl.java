///////////////////////////////////////////////
//------ codigo para admin-ashboard.html   ////
///////////////////////////////////////////////


package cl.transporte.caul.service.impl;

import cl.transporte.caul.dto.DashboardStatsDTO;
import cl.transporte.caul.dto.ServicioResumenDTO;
import cl.transporte.caul.model.Cliente;
import cl.transporte.caul.model.Conductor;
import cl.transporte.caul.model.Servicio;
import cl.transporte.caul.repository.ServicioRepository;
import cl.transporte.caul.repository.ClienteRepository;
import cl.transporte.caul.repository.ConductorRepository;
import cl.transporte.caul.repository.VehiculoRepository;
import cl.transporte.caul.service.DashboardService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final ServicioRepository servicioRepository;
    private final ConductorRepository conductorRepository;
    private final VehiculoRepository vehiculoRepository;
    private final ClienteRepository clienteRepository;


    public DashboardServiceImpl(ServicioRepository servicioRepository,
                                ConductorRepository conductorRepository,
                                VehiculoRepository vehiculoRepository,
                                ClienteRepository clienteRepository) {
        this.servicioRepository = servicioRepository;
        this.conductorRepository = conductorRepository;
        this.vehiculoRepository = vehiculoRepository;
        this.clienteRepository = clienteRepository;
 
    }

    @Override
    public DashboardStatsDTO obtenerStatsDashboard() {

    	LocalDate hoy = LocalDate.now();
        LocalDate mañana = hoy.plusDays(1);
        LocalDate futuro = hoy.plusDays(10);
        LocalDate ayer = hoy.minusDays(1);
        LocalDate hace7 = hoy.minusDays(7);
        LocalDate hace30 = hoy.minusDays(30);

        Date hoyDate    = java.sql.Date.valueOf(hoy);
        Date mañanaDate = java.sql.Date.valueOf(mañana);
        Date futuroDate = java.sql.Date.valueOf(futuro);
        Date ayerDate   = java.sql.Date.valueOf(ayer);
        Date hace7Date  = java.sql.Date.valueOf(hace7);
        Date hace30Date = java.sql.Date.valueOf(hace30);

        DashboardStatsDTO dto = new DashboardStatsDTO();

        // KPI servicios por rango
        dto.setServiciosHoy(servicioRepository.countByFecha(hoyDate));
        dto.setMañana(servicioRepository.countByFecha(mañanaDate));
        dto.setServiciosAyer(servicioRepository.countByFecha(ayerDate));
        dto.setServiciosUltimos7(servicioRepository.countByFechaBetween(hace7Date, hoyDate));
        dto.setServiciosUltimos30(servicioRepository.countByFechaBetween(hace30Date, hoyDate));

        // Por ahora: total conductores y vehículos (simple)
        dto.setConductoresActivos(conductorRepository.count());
        dto.setVehiculosDisponibles(vehiculoRepository.count());

        // Más adelante calculas esto con datos reales
        dto.setPuntualidadEstimadapct(100.0);

        return dto;
    }

    @Override
    public List<ServicioResumenDTO> obtenerServiciosPorRango(String rango) {

        LocalDate hoy = LocalDate.now();
        LocalDate inicio;
        LocalDate fin;

        switch (rango) {
        
	        case "FUTURO":
	            inicio = hoy;
	            fin = hoy.plusDays(10);
	            break;
        
            case "AYER":
                inicio = hoy.minusDays(1);
                fin = hoy.minusDays(1);
                break;
            case "ULTIMOS_7":
                inicio = hoy.minusDays(7);
                fin = hoy;
                break;
            case "ULTIMOS_30":
                inicio = hoy.minusDays(30);
                fin = hoy;
                break;
            case "MAÑANA":
            	inicio = hoy.plusDays(1);
            	fin = hoy.plusDays(1);
            	 break;
            case "HOY":
            default:
                inicio = hoy;
                fin = hoy;
                break;
        }

        Date inicioDate = java.sql.Date.valueOf(inicio);
        Date finDate    = java.sql.Date.valueOf(fin);

        List<Servicio> servicios =
                servicioRepository.findByFechaBetweenOrderByFechaAscHoraInicioProgramadaAsc(inicioDate, finDate);

        return servicios.stream()
                .map(this::mapToResumen)
                .collect(Collectors.toList());
    }

   
    private ServicioResumenDTO mapToResumen(Servicio s) {
        ServicioResumenDTO dto = new ServicioResumenDTO();

        dto.setId(s.getId());
        dto.setFecha(s.getFecha());
        dto.setHoraProgramada(s.getHoraInicioProgramada());

        dto.setOrigen(s.getOrigen());
        dto.setDestino(s.getDestino());

        // ----- Conductor -----
        String nombreConductor = "N/D";
        if (s.getConductorId() != null) {
            nombreConductor = conductorRepository.findById(s.getConductorId())
                    .map(c -> {
                        String nombreCompleto = c.getNombreCompleto() != null ? c.getNombreCompleto() : "";
                        return nombreCompleto.trim();
                    })
                    .orElse("N/D");
        }
        dto.setNombreConductor(nombreConductor);

        // ----- Cliente -----
        String nombreCliente = "N/D";
        if (s.getClienteId() != null) {
        	nombreCliente = clienteRepository.findById(s.getClienteId())
                    .map(Cliente::getRazonSocial)
                    .orElse("N/D");
        }
        dto.setNombreCliente(nombreCliente);

        // ----- Vehículo -----
        String vehiculo = "N/D";
        if (s.getVehiculoId() != null) {
            vehiculo = vehiculoRepository.findById(s.getVehiculoId())
                    .map(v -> v.getPatente())
                    .orElse("N/D");
        }
        dto.setVehiculo(vehiculo);

        
        dto.setEstado(s.getEstado());

        return dto;
    }

    
}
