package cl.transporte.caul.service.impl;

import cl.transporte.caul.dto.PasajeroServicioRequest;
import cl.transporte.caul.dto.PasajeroServicioResponse;
import cl.transporte.caul.model.PasajeroServicio;
import cl.transporte.caul.repository.PasajeroServicioRepository;
import cl.transporte.caul.repository.ServicioRepository;
import cl.transporte.caul.service.PasajeroServicioService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional
public class PasajeroServicioServiceImpl implements PasajeroServicioService {

    private final PasajeroServicioRepository repo;
    private final ServicioRepository servicioRepo;

    public PasajeroServicioServiceImpl(
            PasajeroServicioRepository repo,
            ServicioRepository servicioRepo) {
        this.repo = repo;
        this.servicioRepo = servicioRepo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PasajeroServicioResponse> listarPorServicio(Long servicioId) {

        if (!servicioRepo.existsById(servicioId)) {
            throw new NoSuchElementException("Servicio no existe: " + servicioId);
        }

        return repo.findByServicioId(servicioId)
                   .stream()
                   .map(this::map)
                   .collect(Collectors.toList());
    }

    @Override
    public PasajeroServicioResponse crear(Long servicioId, PasajeroServicioRequest req) {

        if (!servicioRepo.existsById(servicioId)) {
            throw new NoSuchElementException("Servicio no existe: " + servicioId);
        }

        PasajeroServicio p = new PasajeroServicio();
        
        p.setId(servicioId);

        p.setNombrePasajero(trim(req.getNombrePasajero()));
        p.setTelefonoContacto(req.getTelefonoContacto());
        p.setRutPasajero(trim(req.getRutPasajero()));
        p.setTelefonoContacto(req.getTelefonoContacto());
        p.setPuntoSubida(trim(req.getPuntoSubida()));
        p.setPuntoBajada(trim(req.getPuntoBajada()));
        p.setObservaciones(trim(req.getObservaciones()));

       
        return map(repo.save(p));
    }

    @Override
    public void eliminar(Long id) {
        if (!repo.existsById(id)) {
            throw new NoSuchElementException("Pasajero no existe: " + id);
        }
        repo.deleteById(id);
    }

    @Override
    public void eliminarPorServicio(Long servicioId) {
        if (!servicioRepo.existsById(servicioId)) {
            throw new NoSuchElementException("Servicio no existe: " + servicioId);
        }
        repo.deleteByServicioId(servicioId);
    }

    // =============================
    // MAP / UTILIDADES
    // =============================

    private PasajeroServicioResponse map(PasajeroServicio p) {
        PasajeroServicioResponse r = new PasajeroServicioResponse();

        r.setId(p.getId());
        r.setServicioId(p.getId());
        r.setNombrePasajero(p.getNombrePasajero());
        r.setTelefonoContacto(p.getTelefonoContacto());
        r.setRutPasajero(p.getRutPasajero());
        r.setPuntoSubida(p.getPuntoSubida());
        r.setPuntoBajada(p.getPuntoBajada());
        r.setObservaciones(p.getObservaciones());
       
        return r;
    }

    private String trim(String val) {
        if (val == null) return null;
        val = val.trim();
        return val.isEmpty() ? null : val;
    }
}
