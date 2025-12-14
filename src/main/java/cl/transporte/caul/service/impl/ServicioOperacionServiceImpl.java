package cl.transporte.caul.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;

import cl.transporte.caul.model.*;
import cl.transporte.caul.repository.ServicioRepository;
import cl.transporte.caul.service.ServicioOperacionService;

@Service
public class ServicioOperacionServiceImpl implements ServicioOperacionService {

    private final ServicioRepository servicioRepository;

    public ServicioOperacionServiceImpl(ServicioRepository servicioRepository) {
        this.servicioRepository = servicioRepository;
    }

    @Override
    public void iniciarServicio(Long servicioId, Long conductorId) {
        Servicio s = servicioRepository.findById(servicioId)
                .orElseThrow(() -> new IllegalArgumentException("Servicio no existe"));

        // 🔐 Seguridad de dominio
        if (!s.getConductorId().equals(conductorId)) {
            throw new SecurityException("No autorizado para este servicio");
        }

        if (!"PROGRAMADO".equals(s.getEstado())) {
            throw new IllegalStateException("Servicio no se puede iniciar");
        }

        s.setEstado("EN_CURSO");
        s.setFechaHoraInicioReal(new Date());
        servicioRepository.save(s);
    }

    @Override
    public void finalizarServicio(Long servicioId, Long conductorId) {
        Servicio s = servicioRepository.findById(servicioId)
                .orElseThrow(() -> new IllegalArgumentException("Servicio no existe"));

        if (!s.getConductorId().equals(conductorId)) {
            throw new SecurityException("No autorizado para este servicio");
        }

        if (!"EN_CURSO".equals(s.getEstado())) {
            throw new IllegalStateException("Servicio no se puede finalizar");
        }

        s.setEstado("FINALIZADO");
        s.setFechaHoraFinReal(new Date());
        servicioRepository.save(s);
    }
}
