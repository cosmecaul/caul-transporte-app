package cl.transporte.caul.service.impl;

import cl.transporte.caul.dto.*;
import cl.transporte.caul.model.RutaPunto;
import cl.transporte.caul.repository.RutaPuntoRepository;
import cl.transporte.caul.service.RutaPuntoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional
public class RutaPuntoServiceImpl implements RutaPuntoService {

    private final RutaPuntoRepository rutaPuntoRepository;

    public RutaPuntoServiceImpl(RutaPuntoRepository rutaPuntoRepository) {
        this.rutaPuntoRepository = rutaPuntoRepository;
    }

    @Override
    public RutaPuntoResponse createRutaPunto(RutaPuntoCreateRequest request) {

        RutaPunto p = new RutaPunto();
        p.setRutaId(request.getRutaId());
        p.setOrden(request.getOrden());
        p.setNombrePunto(request.getNombrePunto());
        p.setDireccion(request.getDireccion());
        p.setLatitud(request.getLatitud());
        p.setLongitud(request.getLongitud());
        p.setHoraProgramada(request.getHoraProgramada());
        p.setObservaciones(request.getObservaciones());

        Date now = new Date();
        p.setCreatedAt(now);
        p.setUpdatedAt(now);

        return map(rutaPuntoRepository.save(p));
    }

    @Override
    public RutaPuntoResponse updateRutaPunto(Long id, RutaPuntoUpdateRequest request) {

        RutaPunto p = rutaPuntoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Punto de ruta no encontrado con id " + id));

        if (request.getOrden() != null) p.setOrden(request.getOrden());
        if (request.getNombrePunto() != null) p.setNombrePunto(request.getNombrePunto());
        if (request.getDireccion() != null) p.setDireccion(request.getDireccion());
        if (request.getLatitud() != null) p.setLatitud(request.getLatitud());
        if (request.getLongitud() != null) p.setLongitud(request.getLongitud());
        if (request.getHoraProgramada() != null) p.setHoraProgramada(request.getHoraProgramada());
        if (request.getObservaciones() != null) p.setObservaciones(request.getObservaciones());

        p.setUpdatedAt(new Date());

        return map(rutaPuntoRepository.save(p));
    }

    @Override
    @Transactional(readOnly = true)
    public RutaPuntoResponse getRutaPuntoById(Long id) {
        RutaPunto p = rutaPuntoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Punto de ruta no encontrado con id " + id));
        return map(p);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RutaPuntoResponse> listRutaPuntosByRuta(Long rutaId) {
        return rutaPuntoRepository.findByRutaIdOrderByOrdenAsc(rutaId)
                .stream().map(this::map).collect(Collectors.toList());
    }

    @Override
    public void deleteRutaPunto(Long id) {
        rutaPuntoRepository.deleteById(id);
    }

    private RutaPuntoResponse map(RutaPunto p) {
        RutaPuntoResponse r = new RutaPuntoResponse();
        r.setId(p.getId());
        r.setRutaId(p.getRutaId());
        r.setOrden(p.getOrden());
        r.setNombrePunto(p.getNombrePunto());
        r.setDireccion(p.getDireccion());
        r.setLatitud(p.getLatitud());
        r.setLongitud(p.getLongitud());
        r.setHoraProgramada(p.getHoraProgramada());
        r.setObservaciones(p.getObservaciones());
        r.setCreatedAt(p.getCreatedAt());
        r.setUpdatedAt(p.getUpdatedAt());
        return r;
    }
}
