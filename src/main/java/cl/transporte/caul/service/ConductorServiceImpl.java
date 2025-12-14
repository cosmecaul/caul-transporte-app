package cl.transporte.caul.service;

import cl.transporte.caul.dto.ConductorCreateRequest;
import cl.transporte.caul.dto.ConductorUpdateRequest;
import cl.transporte.caul.dto.ConductorResponse;
import cl.transporte.caul.model.Conductor;
import cl.transporte.caul.repository.ConductorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional
public class ConductorServiceImpl implements ConductorService {

    private final ConductorRepository conductorRepository;

    public ConductorServiceImpl(ConductorRepository conductorRepository) {
        this.conductorRepository = conductorRepository;
    }

    @Override
    public ConductorResponse createConductor(ConductorCreateRequest request) {
        String rutNormalizado = normalizarRut(request.getRut());
        validarRut(rutNormalizado);

        if (conductorRepository.existsByRut(rutNormalizado)) {
            throw new IllegalStateException("Ya existe un conductor con el RUT " + rutNormalizado);
        }

        Conductor c = new Conductor();
        c.setNombreCompleto(trimOrNull(request.getNombreCompleto()));
        c.setRut(rutNormalizado);
        c.setTelefono(trimOrNull(request.getTelefono()));
        c.setEmail(trimOrNull(request.getEmail()));
        c.setLicenciaClase(trimOrNull(request.getLicenciaClase()));
        c.setLicenciaVencimiento(request.getLicenciaVencimiento());
        c.setActivo(request.getActivo() != null ? request.getActivo() : Boolean.TRUE);
        c.setObservaciones(trimOrNull(request.getObservaciones()));

        LocalDateTime now = LocalDateTime.now();
        c.setCreatedAt(now);
        c.setUpdatedAt(now);

        Conductor saved = conductorRepository.save(c);
        return mapToResponse(saved);
    }

    @Override
    public ConductorResponse updateConductor(Long id, ConductorUpdateRequest request) {
        Conductor c = conductorRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Conductor no encontrado con id " + id));

        if (request.getRut() != null && !request.getRut().isBlank()) {
            String rutNormalizado = normalizarRut(request.getRut());
            validarRut(rutNormalizado);

            if (!rutNormalizado.equalsIgnoreCase(c.getRut())
                    && conductorRepository.existsByRut(rutNormalizado)) {
                throw new IllegalStateException("Ya existe otro conductor con el RUT " + rutNormalizado);
            }
            c.setRut(rutNormalizado);
        }

        if (request.getNombreCompleto() != null) {
            c.setNombreCompleto(trimOrNull(request.getNombreCompleto()));
        }
        if (request.getTelefono() != null) {
            c.setTelefono(trimOrNull(request.getTelefono()));
        }
        if (request.getEmail() != null) {
            c.setEmail(trimOrNull(request.getEmail()));
        }
        if (request.getLicenciaClase() != null) {
            c.setLicenciaClase(trimOrNull(request.getLicenciaClase()));
        }
        if (request.getLicenciaVencimiento() != null) {
            c.setLicenciaVencimiento(request.getLicenciaVencimiento());
        }
        if (request.getActivo() != null) {
            c.setActivo(request.getActivo());
        }
        if (request.getObservaciones() != null) {
            c.setObservaciones(trimOrNull(request.getObservaciones()));
        }

        c.setUpdatedAt(LocalDateTime.now());
        Conductor saved = conductorRepository.save(c);
        return mapToResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ConductorResponse getConductorById(Long id) {
        Conductor c = conductorRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Conductor no encontrado con id " + id));
        return mapToResponse(c);
    }

    @Override
    @Transactional(readOnly = true)
    public ConductorResponse getConductorByRut(String rut) {
        String rutNormalizado = normalizarRut(rut);
        Conductor c = conductorRepository.findByRut(rutNormalizado)
                .orElseThrow(() -> new NoSuchElementException("Conductor no encontrado con RUT " + rutNormalizado));
        return mapToResponse(c);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ConductorResponse> listConductores(Boolean soloActivos) {
        List<Conductor> lista;

        if (Boolean.TRUE.equals(soloActivos)) {
            lista = conductorRepository.findByActivoTrue();
        } else {
            lista = conductorRepository.findAll();
        }

        return lista.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // --------- helpers ----------

    private ConductorResponse mapToResponse(Conductor c) {
        ConductorResponse r = new ConductorResponse();
        r.setId(c.getId());
        r.setNombreCompleto(c.getNombreCompleto());
        r.setRut(c.getRut());
        r.setTelefono(c.getTelefono());
        r.setEmail(c.getEmail());
        r.setLicenciaClase(c.getLicenciaClase());
        r.setLicenciaVencimiento(c.getLicenciaVencimiento());
        r.setActivo(c.getActivo());
        r.setObservaciones(c.getObservaciones());
        r.setCreatedAt(c.getCreatedAt());
        r.setUpdatedAt(c.getUpdatedAt());
        return r;
    }

    private String trimOrNull(String value) {
        if (value == null) return null;
        String t = value.trim();
        return t.isEmpty() ? null : t;
    }

    private String normalizarRut(String rutRaw) {
        if (rutRaw == null) {
            throw new IllegalArgumentException("El RUT no puede ser nulo");
        }
        String rut = rutRaw.trim().toUpperCase();
        rut = rut.replace(".", "").replace("-", "");
        return rut;
    }

    private void validarRut(String rut) {
        if (rut.length() < 2) {
            throw new IllegalArgumentException("RUT inválido");
        }

        String cuerpo = rut.substring(0, rut.length() - 1);
        char dvIngresado = rut.charAt(rut.length() - 1);

        if (!cuerpo.chars().allMatch(Character::isDigit)) {
            throw new IllegalArgumentException("RUT inválido");
        }

        int suma = 0;
        int factor = 2;
        for (int i = cuerpo.length() - 1; i >= 0; i--) {
            int num = Character.getNumericValue(cuerpo.charAt(i));
            suma += num * factor;
            factor++;
            if (factor > 7) {
                factor = 2;
            }
        }

        int resto = suma % 11;
        int dvCalcNum = 11 - resto;
        char dvCalculado;

        if (dvCalcNum == 11) {
            dvCalculado = '0';
        } else if (dvCalcNum == 10) {
            dvCalculado = 'K';
        } else {
            dvCalculado = (char) ('0' + dvCalcNum);
        }

        if (dvIngresado != dvCalculado) {
            throw new IllegalArgumentException("RUT inválido");
        }
    }
}
