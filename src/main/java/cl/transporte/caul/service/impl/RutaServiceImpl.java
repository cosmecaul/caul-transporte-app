package cl.transporte.caul.service.impl;

import cl.transporte.caul.dto.*;
import cl.transporte.caul.model.Ruta;
import cl.transporte.caul.repository.RutaRepository;
import cl.transporte.caul.service.RutaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional
public class RutaServiceImpl implements RutaService {

    private final RutaRepository rutaRepository;

    public RutaServiceImpl(RutaRepository rutaRepository) {
        this.rutaRepository = rutaRepository;
    }

    @Override
    public RutaResponse createRuta(RutaCreateRequest request) {

        Ruta r = new Ruta();
        r.setClienteId(request.getClienteId());
        r.setNombre(request.getNombre().trim());
        r.setDescripcion(request.getDescripcion());
        r.setKmEstimados(request.getKmEstimados());
        r.setActivo(request.getActivo());
   

        Date now = new Date();
        r.setCreatedAt(now);
        r.setUpdatedAt(now);

        Ruta saved = rutaRepository.save(r);
        return mapToResponse(saved);
    }

    @Override
    public RutaResponse updateRuta(Long id, RutaUpdateRequest request) {

        Ruta r = rutaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Ruta no encontrada con id " + id));

        if (request.getClienteId() != null) r.setClienteId(request.getClienteId());
        if (request.getNombre() != null) r.setNombre(request.getNombre().trim());
        if (request.getDescripcion() != null) r.setDescripcion(request.getDescripcion());
        if (request.getKmEstimados() != null) r.setKmEstimados(request.getKmEstimados());
        if (request.getActivo() != null) r.setActivo(request.getActivo());
       

        r.setUpdatedAt(new Date());

        return mapToResponse(rutaRepository.save(r));
    }

    @Override
    @Transactional(readOnly = true)
    public RutaResponse getRutaById(Long id) {
        Ruta r = rutaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Ruta no encontrada con id " + id));
        return mapToResponse(r);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RutaResponse> listRutas(Boolean soloActivos) {

        List<Ruta> rutas =
                Boolean.TRUE.equals(soloActivos)
                        ? rutaRepository.findByActivoTrue()
                        : rutaRepository.findAll();

        return rutas.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RutaResponse> listRutasByCliente(Long clienteId) {

        return rutaRepository.findByClienteId(clienteId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private RutaResponse mapToResponse(Ruta r) {
        RutaResponse resp = new RutaResponse();
        resp.setId(r.getId());
        resp.setClienteId(r.getClienteId());
        resp.setNombre(r.getNombre());
        resp.setDescripcion(r.getDescripcion());
        resp.setKmEstimados(r.getKmEstimados());
        resp.setActivo(r.getActivo());
        resp.setCreatedAt(r.getCreatedAt());
        resp.setUpdatedAt(r.getUpdatedAt());
        return resp;
    }
}
