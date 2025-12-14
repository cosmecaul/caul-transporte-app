package cl.transporte.caul.service.impl;

import cl.transporte.caul.dto.VehiculoCreateRequest;
import cl.transporte.caul.dto.VehiculoUpdateRequest;
import cl.transporte.caul.dto.VehiculoResponse;
import cl.transporte.caul.model.Vehiculo;
import cl.transporte.caul.repository.VehiculoRepository;
import cl.transporte.caul.service.VehiculoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional
public class VehiculoServiceImpl implements VehiculoService {

    private final VehiculoRepository vehiculoRepository;

    public VehiculoServiceImpl(VehiculoRepository vehiculoRepository) {
        this.vehiculoRepository = vehiculoRepository;
    }

    @Override
    public VehiculoResponse createVehiculo(VehiculoCreateRequest request) {
        String patenteNorm = normalizarPatente(request.getPatente());

        if (vehiculoRepository.existsByPatente(patenteNorm)) {
            throw new IllegalStateException("Ya existe un vehículo con la patente " + patenteNorm);
        }

        Vehiculo v = new Vehiculo();
        v.setPatente(patenteNorm);
        v.setMarcaModelo(trimOrNull(request.getMarcaModelo()));
        v.setTipoVehiculo(request.getTipoVehiculo());
        v.setAnio(request.getAnio());
        v.setCapacidad(request.getCapacidad());
        v.setDecreto80Vencimiento(request.getDecreto80Vencimiento());
        v.setGpsDeviceId(trimOrNull(request.getGpsDeviceId()));
        v.setGpsProveedor(trimOrNull(request.getGpsProveedor()));
        v.setGpsActivo(request.getGpsActivo() != null ? request.getGpsActivo() : Boolean.FALSE);
        v.setObservaciones(trimOrNull(request.getObservaciones()));

        Date now = new Date();
        v.setCreatedAt(now);
        v.setUpdatedAt(now);

        Vehiculo saved = vehiculoRepository.save(v);
        return mapToResponse(saved);
    }

    @Override
    public VehiculoResponse updateVehiculo(Long id, VehiculoUpdateRequest request) {
        Vehiculo v = vehiculoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Vehículo no encontrado con id " + id));

        if (request.getPatente() != null && !request.getPatente().isBlank()) {
            String patenteNorm = normalizarPatente(request.getPatente());
            if (!patenteNorm.equalsIgnoreCase(v.getPatente())
                    && vehiculoRepository.existsByPatente(patenteNorm)) {
                throw new IllegalStateException("Ya existe otro vehículo con la patente " + patenteNorm);
            }
            v.setPatente(patenteNorm);
        }

        if (request.getTipoVehiculo() != null) {
            v.setTipoVehiculo(request.getTipoVehiculo());}
        
        if (request.getMarcaModelo() != null) {
            v.setMarcaModelo(trimOrNull(request.getMarcaModelo()));
        }
        if (request.getAnio() != null) {
            v.setAnio(request.getAnio());
        }
        if (request.getCapacidad() != null) {
            v.setCapacidad(request.getCapacidad());
        }
        if (request.getDecreto80Vencimiento() != null) {
            v.setDecreto80Vencimiento(request.getDecreto80Vencimiento());
        }
        if (request.getGpsDeviceId() != null) {
            v.setGpsDeviceId(trimOrNull(request.getGpsDeviceId()));
        }
        if (request.getGpsProveedor() != null) {
            v.setGpsProveedor(trimOrNull(request.getGpsProveedor()));
        }
        if (request.getGpsActivo() != null) {
            v.setGpsActivo(request.getGpsActivo());
        }
        if (request.getObservaciones() != null) {
            v.setObservaciones(trimOrNull(request.getObservaciones()));
        }

        v.setUpdatedAt(new Date());

        Vehiculo saved = vehiculoRepository.save(v);
        return mapToResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public VehiculoResponse getVehiculoById(Long id) {
        Vehiculo v = vehiculoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Vehículo no encontrado con id " + id));
        return mapToResponse(v);
    }

    @Override
    @Transactional(readOnly = true)
    public VehiculoResponse getVehiculoByPatente(String patente) {
        String patenteNorm = normalizarPatente(patente);
        Vehiculo v = vehiculoRepository.findByPatente(patenteNorm)
                .orElseThrow(() -> new NoSuchElementException("Vehículo no encontrado con patente " + patenteNorm));
        return mapToResponse(v);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehiculoResponse> listVehiculos(Boolean soloActivos) {
        // Como Vehiculo no tiene campo 'activo', siempre listamos todos
        List<Vehiculo> lista = vehiculoRepository.findAll();

        return lista.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ------- helpers --------

    private VehiculoResponse mapToResponse(Vehiculo v) {
        VehiculoResponse r = new VehiculoResponse();
        r.setId(v.getId());
        r.setPatente(v.getPatente());
        r.setMarcaModelo(v.getMarcaModelo());
        r.setTipoVehiculo(v.getTipoVehiculo());
        r.setAnio(v.getAnio());
        r.setCapacidad(v.getCapacidad());
        r.setDecreto80Vencimiento(v.getDecreto80Vencimiento());
        r.setGpsDeviceId(v.getGpsDeviceId());
        r.setGpsProveedor(v.getGpsProveedor());
        r.setGpsActivo(v.getGpsActivo());
        r.setObservaciones(v.getObservaciones());
        r.setCreatedAt(v.getCreatedAt());
        r.setUpdatedAt(v.getUpdatedAt());
        return r;
    }

    private String trimOrNull(String value) {
        if (value == null) return null;
        String t = value.trim();
        return t.isEmpty() ? null : t;
    }

    private String normalizarPatente(String patenteRaw) {
        if (patenteRaw == null) {
            throw new IllegalArgumentException("La patente no puede ser nula");
        }
        String p = patenteRaw.trim().toUpperCase();
        return p.replace(" ", "");
    }
}
