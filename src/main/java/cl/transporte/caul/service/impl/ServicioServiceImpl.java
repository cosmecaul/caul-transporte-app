package cl.transporte.caul.service.impl;

import cl.transporte.caul.dto.*;
import cl.transporte.caul.model.PasajeroServicio;
import cl.transporte.caul.model.Servicio;
import cl.transporte.caul.repository.ClienteRepository;
import cl.transporte.caul.repository.ConductorRepository;
import cl.transporte.caul.repository.PasajeroServicioRepository;
import cl.transporte.caul.repository.RutaRepository;
import cl.transporte.caul.repository.ServicioRepository;
import cl.transporte.caul.repository.VehiculoRepository;
import cl.transporte.caul.service.ServicioService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.HashMap;
import java.time.ZoneId;



@Service
@Transactional
public class ServicioServiceImpl implements ServicioService {

    private static final String ESTADO_PROGRAMADO   = "PROGRAMADO";
    private static final String ESTADO_EN_CURSO     = "EN_CURSO";
    private static final String ESTADO_FINALIZADO   = "FINALIZADO";
    private static final String ESTADO_CANCELADO    = "CANCELADO";
    private static final String ESTADO_NO_REALIZADO = "NO_REALIZADO";
    
    private final ServicioRepository servicioRepository;
    private final ClienteRepository clienteRepository;
    private final VehiculoRepository vehiculoRepository;
    private final ConductorRepository conductorRepository;
    private final RutaRepository rutaRepository;
    private final PasajeroServicioRepository pasajeroServicioRepository;

    public ServicioServiceImpl(ServicioRepository servicioRepository,
                               ClienteRepository clienteRepository,
                               VehiculoRepository vehiculoRepository,
                               ConductorRepository conductorRepository,
                               RutaRepository rutaRepository,
                               PasajeroServicioRepository pasajeroServicioRepository) {
    	
        this.servicioRepository = servicioRepository;
        this.clienteRepository = clienteRepository;
        this.vehiculoRepository = vehiculoRepository;
        this.conductorRepository = conductorRepository;
        this.rutaRepository = rutaRepository;
        this.pasajeroServicioRepository = pasajeroServicioRepository;
    }

    // ---------------------------------------------------------
    // Helpers
    // ---------------------------------------------------------

    @Override
    @Transactional
    public void eliminarPasajero(Long servicioId, Long pasajeroId) {
        // opcionalmente validar que el pasajero pertenece a ese servicio
    	pasajeroServicioRepository.deleteById(pasajeroId);
    }
    
    
    @Override
    public Servicio buscarPorId(Long id) {
        return servicioRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Servicio no encontrado. Id = " + id));
    }
    
    @Override
    @Transactional
    public void actualizarServicio(Long id, ServicioUpdateRequest request) {
        Servicio servicio = servicioRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Servicio no encontrado. Id = " + id));

        // ⚠️ Solo actualizamos campos básicos.
        // NO tocamos servicio.getPasajeros(), por lo tanto NO se borran.
        servicio.setClienteId(request.getClienteId());
        servicio.setVehiculoId(request.getVehiculoId());
        servicio.setConductorId(request.getConductorId());
        servicio.setRutaId(request.getRutaId());
        servicio.setFecha(request.getFecha());
        servicio.setHoraInicioProgramada(request.getHoraInicioProgramada());
        servicio.setHoraFinProgramada(request.getHoraFinProgramada());

        servicioRepository.save(servicio);
    }
    
    private static final ZoneId ZONE_CL = ZoneId.of("America/Santiago");

    private LocalDate toLocalDate(Date date) {
        if (date == null) return null;
        return date.toInstant().atZone(ZONE_CL).toLocalDate();
    }

    private Date toDate(LocalDate localDate) {
        if (localDate == null) return null;
        return Date.from(localDate.atStartOfDay(ZONE_CL).toInstant());
    }
    
    
    private boolean horasSeSolapan(LocalTime ini1, LocalTime fin1,
                                   LocalTime ini2, LocalTime fin2) {
        // [ini1, fin1) y [ini2, fin2) se solapan si ini1 < fin2 y ini2 < fin1
        return ini1.isBefore(fin2) && ini2.isBefore(fin1);
    }

    private LocalTime parseHora(String hora) {
        if (hora == null) {
            throw new IllegalArgumentException("Hora nula");
        }
        if (hora.length() == 5) { // HH:mm
            return LocalTime.parse(hora, DateTimeFormatter.ofPattern("HH:mm"));
        } else {
            return LocalTime.parse(hora, DateTimeFormatter.ofPattern("HH:mm:ss"));
        }
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    private boolean estadoValido(String estado) {
        return ESTADO_PROGRAMADO.equals(estado) ||
               ESTADO_EN_CURSO.equals(estado) ||
               ESTADO_FINALIZADO.equals(estado) ||
               ESTADO_CANCELADO.equals(estado) ||
               ESTADO_NO_REALIZADO.equals(estado);
    }

    private void validarExistenciaRelaciones(Long clienteId,
                                             Long vehiculoId,
                                             Long conductorId,
                                             Long rutaId) {

        if (clienteId == null || !clienteRepository.existsById(clienteId)) {
            throw new NoSuchElementException("Cliente no existe: " + clienteId);
        }
        if (vehiculoId == null || !vehiculoRepository.existsById(vehiculoId)) {
            throw new NoSuchElementException("Vehículo no existe: " + vehiculoId);
        }
        if (conductorId == null || !conductorRepository.existsById(conductorId)) {
            throw new NoSuchElementException("Conductor no existe: " + conductorId);
        }
        if (rutaId != null && !rutaRepository.existsById(rutaId)) {
            throw new NoSuchElementException("Ruta no existe: " + rutaId);
        }
    }

    
    public void eliminarServicio(Long id) {
        Servicio servicio = servicioRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(
                        "Servicio no encontrado con id " + id
                ));

        // Si la relación Servicio -> Pasajeros tiene cascade = CascadeType.ALL
        // basta con eliminar el servicio y JPA se encarga de los pasajeros.
        pasajeroServicioRepository.deleteByServicioId(id);
        servicioRepository.delete(servicio);

        // Si NO tienes cascade, podrías hacer algo tipo:
        // pasajeroRepository.deleteByServicioId(id);
        // servicioRepository.delete(servicio);
    }
    
    
    private void validarSolapamiento(
            Long vehiculoId,
            Long conductorId,
            Date fecha,
            String nuevoIniStr,
            String nuevoFinStr,
            Long idServicioActual) {

        if (nuevoIniStr == null || nuevoFinStr == null) return;

        LocalTime nuevoIni = parseHora(nuevoIniStr);
        LocalTime nuevoFin = parseHora(nuevoFinStr);

        if (nuevoFin.isBefore(nuevoIni) || nuevoFin.equals(nuevoIni)) {
            throw new IllegalArgumentException("La hora fin no puede ser menor o igual que la hora inicio.");
        }

        List<Servicio> vehiculoServicios =
                servicioRepository.findByVehiculoIdAndFecha(vehiculoId, fecha);

        List<Servicio> conductorServicios =
                servicioRepository.findByConductorIdAndFecha(conductorId, fecha);

        List<String> estadosConsiderados = Arrays.asList(
                ESTADO_PROGRAMADO,
                ESTADO_EN_CURSO
        );

        // Vehículo
        vehiculoServicios.stream()
                .filter(s -> idServicioActual == null || !s.getId().equals(idServicioActual))
                .filter(s -> estadosConsiderados.contains(s.getEstado()))
                .forEach(s -> {
                    LocalTime ini = parseHora(s.getHoraInicioProgramada());
                    LocalTime fin = parseHora(s.getHoraFinProgramada());
                    if (horasSeSolapan(nuevoIni, nuevoFin, ini, fin)) {
                        throw new IllegalArgumentException(
                                "El vehículo ya tiene un servicio entre " + ini + " y " + fin);
                    }
                });

        // Conductor
        conductorServicios.stream()
                .filter(s -> idServicioActual == null || !s.getId().equals(idServicioActual))
                .filter(s -> estadosConsiderados.contains(s.getEstado()))
                .forEach(s -> {
                    LocalTime ini = parseHora(s.getHoraInicioProgramada());
                    LocalTime fin = parseHora(s.getHoraFinProgramada());
                    if (horasSeSolapan(nuevoIni, nuevoFin, ini, fin)) {
                        throw new IllegalArgumentException(
                                "El conductor ya tiene un servicio entre " + ini + " y " + fin);
                    }
                });
    }

    private void validarTransicionEstado(String estadoActual,
                                         String nuevoEstado,
                                         Servicio s) {

        if (!estadoValido(nuevoEstado)) {
            throw new IllegalArgumentException("Estado inválido: " + nuevoEstado);
        }

        switch (estadoActual) {
            case ESTADO_PROGRAMADO -> {
                if (!(nuevoEstado.equals(ESTADO_EN_CURSO) ||
                      nuevoEstado.equals(ESTADO_CANCELADO) ||
                      nuevoEstado.equals(ESTADO_NO_REALIZADO))) {
                    throw new IllegalStateException(
                            "Desde PROGRAMADO solo se puede pasar a EN_CURSO, CANCELADO o NO_REALIZADO");
                }
            }
            case ESTADO_EN_CURSO -> {
                if (!(nuevoEstado.equals(ESTADO_FINALIZADO) ||
                      nuevoEstado.equals(ESTADO_NO_REALIZADO))) {
                    throw new IllegalStateException(
                            "Desde EN_CURSO solo se puede pasar a FINALIZADO o NO_REALIZADO");
                }
            }
            case ESTADO_FINALIZADO, ESTADO_CANCELADO, ESTADO_NO_REALIZADO -> {
                throw new IllegalStateException(
                        "El servicio ya está cerrado con estado: " + estadoActual);
            }
        }

        if (nuevoEstado.equals(ESTADO_FINALIZADO)) {
            // Regla suave: si tenemos inicio y fin reales, que sean consistentes
            if (s.getFechaHoraInicioReal() != null &&
                s.getFechaHoraFinReal() != null &&
                s.getFechaHoraFinReal().before(s.getFechaHoraInicioReal())) {

                throw new IllegalStateException(
                        "La fecha/hora de fin real no puede ser anterior al inicio real para finalizar el servicio.");
            }
        }
    }

    // ---------------------------------------------------------
    // Mappers
    // ---------------------------------------------------------

    private ServicioResponse map(Servicio s) {
        ServicioResponse r = new ServicioResponse();

        r.setId(s.getId());
        r.setClienteId(s.getClienteId());
        r.setVehiculoId(s.getVehiculoId());
        r.setConductorId(s.getConductorId());
        r.setRutaId(s.getRutaId());

        r.setFecha(s.getFecha());
        r.setHoraInicioProgramada(s.getHoraInicioProgramada());
        r.setHoraFinProgramada(s.getHoraFinProgramada());

        r.setFechaHoraInicioReal(s.getFechaHoraInicioReal());
        r.setFechaHoraFinReal(s.getFechaHoraFinReal());

        r.setTurno(s.getTurno());
        r.setOrigen(s.getOrigen());
        r.setDestino(s.getDestino());

        r.setEstado(s.getEstado());
        r.setCodigoPublico(s.getCodigoPublico());
        r.setVelocidadMaxKmh(s.getVelocidadMaxKmh());

        r.setObservaciones(s.getObservaciones());
        r.setObservacionOperacion(s.getObservacionOperacion());

        r.setCreatedAt(s.getCreatedAt());
        r.setUpdatedAt(s.getUpdatedAt());

        return r;
    }

    private ServicioMovilHoyResponse mapMovil(Servicio s) {
        ServicioMovilHoyResponse r = new ServicioMovilHoyResponse();

        r.setId(s.getId());
        r.setFecha(s.getFecha());
        r.setHoraInicioProgramada(s.getHoraInicioProgramada());
        r.setHoraFinProgramada(s.getHoraFinProgramada());

        r.setOrigen(s.getOrigen());
        r.setDestino(s.getDestino());
        r.setEstado(s.getEstado());

        r.setClienteId(s.getClienteId());
        r.setVehiculoId(s.getVehiculoId());
        r.setConductorId(s.getConductorId());
        r.setRutaId(s.getRutaId());

        return r;
    }

    // ---------------------------------------------------------
    // CREAR SERVICIO (simple)
    // ---------------------------------------------------------
    @Override
    public ServicioResponse createServicio(ServicioCreateRequest req) {

        validarExistenciaRelaciones(req.getClienteId(),
                                    req.getVehiculoId(),
                                    req.getConductorId(),
                                    req.getRutaId());

        if (req.getFecha() == null) {
            throw new IllegalArgumentException("La fecha del servicio es obligatoria");
        }
        if (isBlank(req.getHoraInicioProgramada()) || isBlank(req.getHoraFinProgramada())) {
            throw new IllegalArgumentException("Las horas inicio/fin programadas son obligatorias");
        }

        LocalTime ini = parseHora(req.getHoraInicioProgramada());
        LocalTime fin = parseHora(req.getHoraFinProgramada());
        if (!fin.isAfter(ini)) {
            throw new IllegalArgumentException("La hora fin programada debe ser posterior a la hora inicio");
        }

        // Validar solapamiento para vehículo y conductor
        validarSolapamiento(
                req.getVehiculoId(),
                req.getConductorId(),
                req.getFecha(),
                req.getHoraInicioProgramada(),
                req.getHoraFinProgramada(),
                null // es creación, todavía no hay id
        );

        Date now = new Date();

        Servicio s = new Servicio();
        s.setClienteId(req.getClienteId());
        s.setVehiculoId(req.getVehiculoId());
        s.setConductorId(req.getConductorId());
        s.setRutaId(req.getRutaId());

        s.setFecha(req.getFecha());
        s.setHoraInicioProgramada(req.getHoraInicioProgramada());
        s.setHoraFinProgramada(req.getHoraFinProgramada());

        s.setKmInicio(req.getKmInicio());
        s.setTurno(req.getTurno());
        s.setOrigen(req.getOrigen());
        s.setDestino(req.getDestino());
        s.setCodigoPublico(req.getCodigoPublico());
        s.setVelocidadMaxKmh(req.getVelocidadMaxKmh());
        s.setObservaciones(req.getObservaciones());

        s.setEstado(ESTADO_PROGRAMADO);
        s.setCreatedAt(now);
        s.setUpdatedAt(now);

        Servicio guardado = servicioRepository.save(s);
        return map(guardado);
    }

    // ---------------------------------------------------------
    // CREAR SERVICIO + PASAJEROS
    // ---------------------------------------------------------
    @Override
    public ServicioResponse crearServicioConPasajeros(ServicioCreateRequest req) {

        // 1) creamos el servicio normal
        ServicioResponse servicioCreado = createServicio(req);

        // 2) si no vienen pasajeros, terminamos
        if (req.getPasajeros() == null || req.getPasajeros().isEmpty()) {
            return servicioCreado;
        }

        Servicio servicio = servicioRepository.findById(servicioCreado.getId())
                .orElseThrow(() -> new NoSuchElementException("Servicio no encontrado tras crear: " + servicioCreado.getId()));

        // 3) Mapear y guardar pasajeros
        List<PasajeroServicio> entidades = req.getPasajeros().stream()
                .filter(p -> p.getNombrePasajero() != null && !p.getNombrePasajero().isBlank())
                .map(p -> {
                    PasajeroServicio ent = new PasajeroServicio();
                    ent.setServicio(servicio);
                    ent.setNombrePasajero(p.getNombrePasajero());
                    ent.setTelefonoContacto(p.getTelefonoContacto());
                    ent.setRutPasajero(p.getRutPasajero());
                    ent.setPuntoSubida(p.getPuntoSubida());
                    ent.setPuntoBajada(p.getPuntoBajada());
                    ent.setObservaciones(p.getObservaciones());
                    return ent;
                })
                .collect(Collectors.toList());

        pasajeroServicioRepository.saveAll(entidades);

        return servicioCreado;
    }

    // ---------------------------------------------------------
    // INICIAR SERVICIO
    // ---------------------------------------------------------
    @Override
    @Transactional
    public ServicioResponse iniciarServicio(Long id, ServicioIniciarRequest request) {
        Servicio s = servicioRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Servicio no encontrado con id " + id));

        // Solo se puede iniciar si está PROGRAMADO
        if (!ESTADO_PROGRAMADO.equals(s.getEstado())) {
            throw new IllegalStateException("Solo se pueden INICIAR servicios en estado PROGRAMADO.");
        }

        // Si ya tenía fechaHoraInicioReal seteada, evitamos doble inicio
        if (s.getFechaHoraInicioReal() != null) {
            throw new IllegalStateException("El servicio ya fue iniciado anteriormente.");
        }

        // Seteamos fecha/hora real (si no viene, usamos "ahora")
        s.setFechaHoraInicioReal(
                request.getFechaHoraInicioReal() != null
                        ? request.getFechaHoraInicioReal()
                        : new Date()
        );

        // Observación de operación (acumula)
        if (request.getObservacionOperacion() != null && !request.getObservacionOperacion().isBlank()) {
            String obs = s.getObservacionOperacion();
            if (obs == null) obs = "";
            if (!obs.isBlank()) {
                obs += " | ";
            }
            obs += "[INICIO] " + request.getObservacionOperacion().trim();
            s.setObservacionOperacion(obs);
        }

        s.setEstado(ESTADO_EN_CURSO);
        s.setUpdatedAt(new Date());

        Servicio saved = servicioRepository.save(s);
        return map(saved);
    }

    // ---------------------------------------------------------
    // FINALIZAR SERVICIO
    // ---------------------------------------------------------
    @Override
    @Transactional
    public ServicioResponse finalizarServicio(Long id, ServicioFinalizarRequest request) {
        Servicio s = servicioRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Servicio no encontrado con id " + id));

        if (!ESTADO_EN_CURSO.equals(s.getEstado())) {
            throw new IllegalStateException("Solo se pueden FINALIZAR servicios en estado EN_CURSO.");
        }

        Date fechaFin = request.getFechaHoraFinReal() != null
                ? request.getFechaHoraFinReal()
                : new Date();

        if (s.getFechaHoraInicioReal() != null && fechaFin.before(s.getFechaHoraInicioReal())) {
            throw new IllegalArgumentException("La fecha/hora de fin no puede ser anterior al inicio real.");
        }

        s.setFechaHoraFinReal(fechaFin);

        // Observación (acumula)
        if (request.getObservacionOperacion() != null && !request.getObservacionOperacion().isBlank()) {
            String obs = s.getObservacionOperacion();
            if (obs == null) obs = "";
            if (!obs.isBlank()) {
                obs += " | ";
            }
            obs += "[FIN] " + request.getObservacionOperacion().trim();
            s.setObservacionOperacion(obs);
        }

        s.setEstado(ESTADO_FINALIZADO);
        s.setUpdatedAt(new Date());

        Servicio saved = servicioRepository.save(s);
        return map(saved);
    }
    
    
    // ---------------------------------------------------------
    // ACTUALIZAR SERVICIO Eliminar pasajeros
    // ---------------------------------------------------------
    

    

    // ---------------------------------------------------------
    // UPDATE SERVICIO (sin pasajeros)
    // ---------------------------------------------------------
    @Override
    public ServicioResponse updateServicio(Long id, ServicioUpdateRequest req) {
        Servicio s = servicioRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Servicio no encontrado: " + id));

        Date   nuevaFecha       = req.getFecha() != null ? req.getFecha() : s.getFecha();
        String nuevaHoraIniStr  = req.getHoraInicioProgramada() != null
                ? req.getHoraInicioProgramada() : s.getHoraInicioProgramada();
        String nuevaHoraFinStr  = req.getHoraFinProgramada() != null
                ? req.getHoraFinProgramada() : s.getHoraFinProgramada();

        if (req.getFecha() != null ||
            req.getHoraInicioProgramada() != null ||
            req.getHoraFinProgramada() != null) {

            LocalTime ini = parseHora(nuevaHoraIniStr);
            LocalTime fin = parseHora(nuevaHoraFinStr);
            if (!fin.isAfter(ini)) {
                throw new IllegalArgumentException("La hora fin programada debe ser posterior a la hora inicio");
            }
        }

        // Actualizamos datos básicos
        if (req.getFecha() != null) s.setFecha(req.getFecha());
        if (req.getHoraInicioProgramada() != null) s.setHoraInicioProgramada(req.getHoraInicioProgramada());
        if (req.getHoraFinProgramada() != null) s.setHoraFinProgramada(req.getHoraFinProgramada());

        if (req.getFechaHoraInicioReal() != null) s.setFechaHoraInicioReal(req.getFechaHoraInicioReal());
        if (req.getFechaHoraFinReal() != null) s.setFechaHoraFinReal(req.getFechaHoraFinReal());

        if (req.getTurno() != null) s.setTurno(req.getTurno());
        if (req.getOrigen() != null) s.setOrigen(req.getOrigen());
        if (req.getDestino() != null) s.setDestino(req.getDestino());

        if (req.getCodigoPublico() != null) s.setCodigoPublico(req.getCodigoPublico());
        if (req.getVelocidadMaxKmh() != null) s.setVelocidadMaxKmh(req.getVelocidadMaxKmh());
        if (req.getObservaciones() != null) s.setObservaciones(req.getObservaciones());
        if (req.getObservacionOperacion() != null) s.setObservacionOperacion(req.getObservacionOperacion());

        if (req.getEstado() != null) {
            validarTransicionEstado(s.getEstado(), req.getEstado(), s);
            s.setEstado(req.getEstado());
        }

        s.setUpdatedAt(new Date());

        // Validar solapamiento con nuevos valores
        validarSolapamiento(
                s.getVehiculoId(),
                s.getConductorId(),
                nuevaFecha,
                nuevaHoraIniStr,
                nuevaHoraFinStr,
                s.getId() // evita conflicto consigo mismo
        );

        Servicio guardado = servicioRepository.save(s);
        return map(guardado);
    }
    
    
    
    

    // ---------------------------------------------------------
    // UPDATE SERVICIO + PASAJEROS
    // ---------------------------------------------------------
    @Override
    @Transactional
    public void updateServicioConPasajeros(Long id, ServicioEditRequest req) {

        Servicio s = servicioRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Servicio no encontrado: " + id));

        // ==========================
        //   Actualizar datos base
        // ==========================
        if (req.getClienteId() != null)   s.setClienteId(req.getClienteId());
        if (req.getVehiculoId() != null)  s.setVehiculoId(req.getVehiculoId());
        if (req.getConductorId() != null) s.setConductorId(req.getConductorId());
        if (req.getRutaId() != null)      s.setRutaId(req.getRutaId());

        if (req.getFecha() != null)               s.setFecha(req.getFecha());
        if (req.getHoraInicioProgramada() != null) s.setHoraInicioProgramada(req.getHoraInicioProgramada());
        if (req.getHoraFinProgramada() != null)    s.setHoraFinProgramada(req.getHoraFinProgramada());
        if (req.getTurno() != null)               s.setTurno(req.getTurno());
        if (req.getOrigen() != null)              s.setOrigen(req.getOrigen());
        if (req.getDestino() != null)             s.setDestino(req.getDestino());
        if (req.getCodigoPublico() != null)       s.setCodigoPublico(req.getCodigoPublico());
        if (req.getVelocidadMaxKmh() != null)     s.setVelocidadMaxKmh(req.getVelocidadMaxKmh());
        if (req.getObservaciones() != null)       s.setObservaciones(req.getObservaciones());
        if (req.getEstado() != null)              s.setEstado(req.getEstado());

        s.setUpdatedAt(new Date());
        servicioRepository.save(s);

        // ==================================
        //   Sin pasajeros en el request
        // ==================================
        List<PasajeroServicioEditRequest> pasajerosReq = req.getPasajeros();
        if (pasajerosReq == null) {
            return; // solo se actualizó el servicio
        }

        // ==================================
        //   Pasajeros actuales en BD
        // ==================================
        List<PasajeroServicio> existentes = pasajeroServicioRepository.findByServicioId(id);
        Map<Long, PasajeroServicio> existentesById = new HashMap<>();
        for (PasajeroServicio p : existentes) {
            existentesById.put(p.getId(), p);
        }

        // ==================================
        //   Procesar request
        // ==================================
        for (PasajeroServicioEditRequest pReq : pasajerosReq) {

            Long pasajeroId = pReq.getId();
            boolean eliminar = Boolean.TRUE.equals(pReq.isMarcadoParaEliminar());

            // ---- Caso 1: pasajero existente ----
            if (pasajeroId != null) {
                PasajeroServicio entidad = existentesById.get(pasajeroId);
                if (entidad == null) {
                    // request trae un id que no está en BD → lo ignoramos o lanzamos error suave
                    continue;
                }

                if (eliminar) {
                    pasajeroServicioRepository.delete(entidad);
                    continue;
                }

                // actualizar datos
                entidad.setNombrePasajero(pReq.getNombrePasajero());
                entidad.setRutPasajero(pReq.getRutPasajero());
                entidad.setTelefonoContacto(pReq.getTelefonoContacto());
                entidad.setPuntoSubida(pReq.getPuntoSubida());
                entidad.setPuntoBajada(pReq.getPuntoBajada());
                entidad.setObservaciones(pReq.getObservaciones());

                pasajeroServicioRepository.save(entidad);
            }
            // ---- Caso 2: nuevo pasajero ----
            else {
                // Si viene marcado para eliminar o todos los campos vacíos, lo ignoramos
                boolean todosVacios =
                        (pReq.getNombrePasajero() == null || pReq.getNombrePasajero().isBlank()) &&
                        (pReq.getRutPasajero() == null || pReq.getRutPasajero().isBlank()) &&
                        (pReq.getPuntoSubida() == null || pReq.getPuntoSubida().isBlank()) &&
                        (pReq.getPuntoBajada() == null || pReq.getPuntoBajada().isBlank()) &&
                        (pReq.getObservaciones() == null || pReq.getObservaciones().isBlank());

                if (eliminar || todosVacios) {
                    continue;
                }

                PasajeroServicio nuevo = new PasajeroServicio();
                nuevo.setServicio(s);
                nuevo.setNombrePasajero(pReq.getNombrePasajero());
                nuevo.setTelefonoContacto(pReq.getTelefonoContacto());
                nuevo.setRutPasajero(pReq.getRutPasajero());
                nuevo.setPuntoSubida(pReq.getPuntoSubida());
                nuevo.setPuntoBajada(pReq.getPuntoBajada());
                nuevo.setObservaciones(pReq.getObservaciones());

                pasajeroServicioRepository.save(nuevo);
            }
        }
    }


    // ---------------------------------------------------------
    // QUERIES
    // ---------------------------------------------------------
    @Override
    @Transactional(readOnly = true)
    public ServicioResponse getServicioById(Long id) {
        Servicio s = servicioRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Servicio no encontrado: " + id));
        return map(s);
    }

    // *** NUEVO: obtener DTO para pantalla de edición ***
    @Override
    @Transactional(readOnly = true)
    public ServicioEditRequest getServicioEditById(Long id) {
        Servicio s = servicioRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Servicio no encontrado: " + id));

        ServicioEditRequest dto = new ServicioEditRequest();
        dto.setClienteId(s.getClienteId());
        dto.setVehiculoId(s.getVehiculoId());
        dto.setConductorId(s.getConductorId());
        dto.setRutaId(s.getRutaId());

        dto.setFecha(s.getFecha());
        dto.setHoraInicioProgramada(s.getHoraInicioProgramada());
        dto.setHoraFinProgramada(s.getHoraFinProgramada());

        dto.setTurno(s.getTurno());
        dto.setOrigen(s.getOrigen());
        dto.setDestino(s.getDestino());
        dto.setCodigoPublico(s.getCodigoPublico());
        dto.setVelocidadMaxKmh(s.getVelocidadMaxKmh());
        dto.setObservaciones(s.getObservaciones());
        dto.setEstado(s.getEstado());

        // Pasajeros
        List<PasajeroServicio> pasajeros = pasajeroServicioRepository.findByServicioId(id);
        List<PasajeroServicioEditRequest> pasajeroDtos = pasajeros.stream()
                .map(p -> {
                    PasajeroServicioEditRequest pd = new PasajeroServicioEditRequest();
                    pd.setId(p.getId());
                    pd.setNombrePasajero(p.getNombrePasajero());
                    pd.setTelefonoContacto(p.getTelefonoContacto());
                    pd.setRutPasajero(p.getRutPasajero());
                    pd.setPuntoSubida(p.getPuntoSubida());
                    pd.setPuntoBajada(p.getPuntoBajada());
                    pd.setObservaciones(p.getObservaciones());
                    return pd;
                })
                .collect(Collectors.toList());

        dto.setPasajeros(pasajeroDtos);

        return dto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServicioResponse> listServicios() {
        return servicioRepository.findAll().stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServicioResponse> listServiciosPorFecha(String fechaStr) {
        try {
            Date fecha = java.sql.Date.valueOf(fechaStr); // formato yyyy-MM-dd
            return servicioRepository.findByFecha(fecha).stream()
                    .map(this::map)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Formato de fecha inválido (use yyyy-MM-dd)");
        }
    }

   
    // ---------------------------------------------------------
    // CAMBIO DE ESTADO DIRECTO
    // ---------------------------------------------------------
    @Override
    public void cambiarEstado(Long id, String nuevoEstado) {
        Servicio s = servicioRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Servicio no encontrado: " + id));

        validarTransicionEstado(s.getEstado(), nuevoEstado, s);

        s.setEstado(nuevoEstado);
        s.setUpdatedAt(new Date());

        if (ESTADO_EN_CURSO.equals(nuevoEstado) && s.getFechaHoraInicioReal() == null) {
            s.setFechaHoraInicioReal(new Date());
        }
        if (ESTADO_FINALIZADO.equals(nuevoEstado) && s.getFechaHoraFinReal() == null) {
            s.setFechaHoraFinReal(new Date());
        }

        servicioRepository.save(s);
    }

    // ---------------------------------------------------------
    // SERVICIOS PARA APP MÓVIL / DASHBOARD
    // ---------------------------------------------------------
    @Override
    @Transactional(readOnly = true)
    public List<ServicioMovilHoyResponse> listServiciosHoyPorConductor(Long conductorId) {

        if (conductorId == null) {
            throw new IllegalArgumentException("conductorId es obligatorio");
        }

        LocalDate hoyLocal = LocalDate.now();
        Date hoy = java.sql.Date.valueOf(hoyLocal);

        List<String> estados = Arrays.asList(
                ESTADO_PROGRAMADO,
                ESTADO_EN_CURSO
        );

        List<Servicio> servicios = servicioRepository
                .findByConductorIdAndFechaAndEstadoIn(conductorId, hoy, estados);

        return servicios.stream()
                .map(this::mapMovil)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServicioResponse> listServiciosHoy() {
        LocalDate hoyLocal = LocalDate.now();
        Date hoy = java.sql.Date.valueOf(hoyLocal);

        return servicioRepository.findByFecha(hoy).stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    // *** NUEVO: servicios de hoy + pasajeros (para vista tipo Uber) ***
    @Override
    @Transactional(readOnly = true)
    public List<ServicioDiaViewDTO> listServiciosHoyConPasajeros() {
        LocalDate hoyLocal = LocalDate.now();
        Date hoy = java.sql.Date.valueOf(hoyLocal);

        List<Servicio> servicios = servicioRepository.findByFecha(hoy);

        return servicios.stream()
                .map(s -> {
                    ServicioDiaViewDTO dto = new ServicioDiaViewDTO();

                    dto.setId(s.getId());
                    dto.setFecha(s.getFecha());
                    dto.setHoraInicio(s.getHoraInicioProgramada());
                    //dto.setHoraFin(s.getHoraFinProgramada());
                    dto.setOrigen(s.getOrigen());
                    dto.setDestino(s.getDestino());
                    dto.setEstado(s.getEstado());

                    dto.setClienteId(s.getClienteId());
                    dto.setVehiculoId(s.getVehiculoId());
                    dto.setConductorId(s.getConductorId());

                    // Si tu DTO tiene campos extra (ej. nombreConductor, vehiculo, etc.)
                    // puedes rellenarlos aquí usando los repositorios de maestro.

                    return dto;
                })
                .collect(Collectors.toList());
    }

	@Override
	public List<ServicioResponse> listServiciosPorCliente(Long clienteId) {
		// TODO Auto-generated method stub
		return null;
	}
}
