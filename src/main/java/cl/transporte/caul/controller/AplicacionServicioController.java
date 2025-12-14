package cl.transporte.caul.controller;

import cl.transporte.caul.dto.ClienteResponse;
import cl.transporte.caul.dto.ServicioDiaViewDTO;
import cl.transporte.caul.dto.ServicioResponse;
import cl.transporte.caul.dto.ServicioFinalizarRequest;
import cl.transporte.caul.dto.ServicioIniciarRequest;
import cl.transporte.caul.dto.PasajeroServicioViewDTO;

import cl.transporte.caul.model.PasajeroServicio;
import cl.transporte.caul.model.Usuario;

import cl.transporte.caul.repository.ClienteRepository;
import cl.transporte.caul.repository.ConductorRepository;
import cl.transporte.caul.repository.PasajeroServicioRepository;
import cl.transporte.caul.repository.UsuarioRepository;
import cl.transporte.caul.repository.VehiculoRepository;

import cl.transporte.caul.service.ClienteService;
import cl.transporte.caul.service.ServicioOperacionService;
import cl.transporte.caul.service.ServicioService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/aplicacion/servicios")
public class AplicacionServicioController {

    private final ServicioService servicioService;
    private final ClienteService  clienteService;

    private final ClienteRepository clienteRepository;
    private final ConductorRepository conductorRepository;
    private final VehiculoRepository vehiculoRepository;
    private final PasajeroServicioRepository pasajeroServicioRepository;

    private final ServicioOperacionService operacionService;
  
    private final UsuarioRepository usuarioRepository;

    public AplicacionServicioController(ServicioService servicioService,
                                        ClienteService clienteService,
                                        ClienteRepository clienteRepository,
                                        ConductorRepository conductorRepository,
                                        VehiculoRepository vehiculoRepository,
                                        PasajeroServicioRepository pasajeroServicioRepository,
                                        UsuarioRepository usuarioRepository,
                                        ServicioOperacionService operacionService) {
        this.servicioService = servicioService;
        this.clienteService  = clienteService;
        this.clienteRepository = clienteRepository;
        this.conductorRepository = conductorRepository;
        this.vehiculoRepository = vehiculoRepository;
        this.pasajeroServicioRepository = pasajeroServicioRepository;
        this.usuarioRepository = usuarioRepository;
        this.operacionService = operacionService;
      
     
    }

    // =========================================================
    //          LISTA DE SERVICIOS (vista conductor)
    // =========================================================
    @GetMapping
    public String listServiciosAplicacion(
            @RequestParam(required = false) String fecha,
            @RequestParam(required = false) Long clienteId,
            @RequestParam(required = false) String estado,
            @RequestParam(required = false, name = "q") String busqueda,
            Authentication authentication,
            Model model
    ) {

        // 1) Base: hoy o fecha específica
        List<ServicioResponse> servicios;
        if (fecha != null && !fecha.isBlank()) {
            servicios = servicioService.listServiciosPorFecha(fecha);
        } else {
            servicios = servicioService.listServiciosHoy();
        }

        // 1.1) Si es CONDUCTOR: filtrar por sus servicios asignados
        if (hasRole(authentication, "ROLE_CONDUCTOR")) {
            Long conductorId = resolveConductorId(authentication);
            if (conductorId == null) {
                // si no hay match usuario->conductor, no mostramos nada (y evitamos filtrar mal)
                servicios = List.of();
            } else {
                servicios = servicios.stream()
                        .filter(s -> conductorId.equals(s.getConductorId()))
                        .collect(Collectors.toList());
            }
        }

        // 2) Filtro por cliente (solo si no eres conductor, o si quieres permitirlo)
        if (clienteId != null) {
            servicios = servicios.stream()
                    .filter(s -> clienteId.equals(s.getClienteId()))
                    .collect(Collectors.toList());
        }

        // 3) Filtro por estado
        if (estado != null && !estado.isBlank() && !"TODOS".equalsIgnoreCase(estado)) {
            String estUpper = estado.toUpperCase();
            servicios = servicios.stream()
                    .filter(s -> estUpper.equalsIgnoreCase(s.getEstado()))
                    .collect(Collectors.toList());
        }

        // 4) Búsqueda simple
        if (busqueda != null && !busqueda.isBlank()) {
            String qLower = busqueda.toLowerCase();
            servicios = servicios.stream()
                    .filter(s ->
                            (s.getOrigen() != null && s.getOrigen().toLowerCase().contains(qLower)) ||
                            (s.getDestino() != null && s.getDestino().toLowerCase().contains(qLower)) ||
                            (s.getCodigoPublico() != null && s.getCodigoPublico().toLowerCase().contains(qLower))
                    )
                    .collect(Collectors.toList());
        }

        // 5) DTO de vista enriquecido (nombres + pasajeros)
        List<ServicioDiaViewDTO> serviciosView = servicios.stream()
                .map(this::mapToDiaViewDTO)
                .collect(Collectors.toList());

        // Lista de clientes para combo
        List<ClienteResponse> clientes = clienteService.listClientes(true);

        model.addAttribute("servicios", serviciosView);
        model.addAttribute("clientes", clientes);

        model.addAttribute("filtroFecha", fecha);
        model.addAttribute("filtroClienteId", clienteId);
        model.addAttribute("filtroEstado", estado);
        model.addAttribute("busqueda", busqueda);

        // para la vista (ocultar/mostrar cosas)
        model.addAttribute("isConductor", hasRole(authentication, "ROLE_CONDUCTOR"));

        model.addAttribute("pageTitle", "Servicios · Panel Operación");
        return "aplicacion/servicios";
    }

    private boolean hasRole(Authentication auth, String role) {
        if (auth == null) return false;
        return auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role::equals);
    }

    /**
     * Resuelve el conductorId a partir del usuario logueado:
     * username -> Usuario(nombre_usuario) -> rut -> Conductor(rut) -> id
     */
    private Long resolveConductorId(Authentication auth) {
        if (auth == null) return null;

        String username = auth.getName(); // nombre_usuario
        Optional<Usuario> u = usuarioRepository.findByNombreUsuario(username);
        if (u.isEmpty()) return null;

        String rut = u.get().getRut();
        if (rut == null || rut.isBlank()) return null;

        return conductorRepository.findByRut(rut)
                .map(c -> c.getId())
                .orElse(null);
    }

    /**
     * Mapper a tu DTO de vista
     * (dejo tu lógica tal cual venía, solo la mantengo aquí)
     */
    private ServicioDiaViewDTO mapToDiaViewDTO(ServicioResponse s) {
        ServicioDiaViewDTO dto = new ServicioDiaViewDTO();

        dto.setId(s.getId());
        dto.setFecha(s.getFecha());
        dto.setHoraInicio(s.getHoraInicioProgramada());
        dto.setOrigen(s.getOrigen());
        dto.setDestino(s.getDestino());
        dto.setEstado(s.getEstado());
        dto.setClienteId(s.getClienteId());
        dto.setVehiculoId(s.getVehiculoId());
        dto.setConductorId(s.getConductorId());

        // Cliente nombre
        if (s.getClienteId() != null) {
            clienteRepository.findById(s.getClienteId()).ifPresent(c -> {
                String nombre = c.getRazonSocial();
                dto.setClienteNombre((nombre != null && !nombre.isBlank()) ? nombre : "N/D");
            });
        }

        // Conductor nombre
        if (s.getConductorId() != null) {
            conductorRepository.findById(s.getConductorId()).ifPresent(c -> {
                String nombre = c.getNombreCompleto();
                dto.setConductorNombre((nombre != null && !nombre.isBlank()) ? nombre : "N/D");
            });
        }

        // Vehículo patente
        if (s.getVehiculoId() != null) {
            vehiculoRepository.findById(s.getVehiculoId()).ifPresent(v -> {
                String patente = v.getPatente();
                dto.setPatente((patente != null && !patente.isBlank()) ? patente : "N/D");
            });
        }

        // Pasajeros del servicio
        List<PasajeroServicio> pasajeros = pasajeroServicioRepository.findByServicioIdOrderByIdAsc(s.getId());
        List<PasajeroServicioViewDTO> pasajerosView = pasajeros.stream().map(p -> {
            PasajeroServicioViewDTO pv = new PasajeroServicioViewDTO();
            pv.setId(p.getId());
            pv.setNombrePasajero(p.getNombrePasajero());
            pv.setRutPasajero(p.getRutPasajero());
            pv.setTelefonoContacto(p.getTelefonoContacto());
            pv.setPuntoSubida(p.getPuntoSubida());
            pv.setPuntoBajada(p.getPuntoBajada());
            pv.setObservaciones(p.getObservaciones());
            return pv;
        }).collect(Collectors.toList());

        dto.setPasajeros(pasajerosView);

        return dto;
    }

    // =========================================================
    //            OPERACIÓN: INICIAR / FINALIZAR / ESTADO
    // =========================================================

    // Iniciar servicio: POST /api/servicios/{id}/iniciar
    @PostMapping("/{id}/iniciar")
    public ResponseEntity<ServicioResponse> iniciar(
            @PathVariable Long id,
            @RequestBody ServicioIniciarRequest request) {

        ServicioResponse response = servicioService.iniciarServicio(id, request);
        return ResponseEntity.ok(response);
    }

    // Finalizar servicio: POST /api/servicios/{id}/finalizar
    @PostMapping("/{id}/finalizar")
    public ResponseEntity<ServicioResponse> finalizar(
            @PathVariable Long id,
            @RequestBody ServicioFinalizarRequest request) {

        ServicioResponse response = servicioService.finalizarServicio(id, request);
        return ResponseEntity.ok(response);
    }

    // Cambiar estado directo (ej: CANCELADO, NO_REALIZADO)
   

    
    
    
}
