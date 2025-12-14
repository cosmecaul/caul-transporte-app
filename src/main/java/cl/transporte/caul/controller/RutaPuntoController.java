package cl.transporte.caul.controller;

import cl.transporte.caul.dto.*;
import cl.transporte.caul.service.RutaPuntoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/rutas/puntos")
public class RutaPuntoController {

    private final RutaPuntoService rutaPuntoService;

    public RutaPuntoController(RutaPuntoService rutaPuntoService) {
        this.rutaPuntoService = rutaPuntoService;
    }

    @PostMapping
    public ResponseEntity<RutaPuntoResponse> create(@Valid @RequestBody RutaPuntoCreateRequest request) {
        RutaPuntoResponse created = rutaPuntoService.createRutaPunto(request);
        return ResponseEntity.created(URI.create("/api/rutas/puntos/" + created.getId()))
                .body(created);
    }

    @PutMapping("/{id}")
    public RutaPuntoResponse update(
            @PathVariable Long id,
            @Valid @RequestBody RutaPuntoUpdateRequest request) {
        return rutaPuntoService.updateRutaPunto(id, request);
    }

    @GetMapping("/{id}")
    public RutaPuntoResponse getById(@PathVariable Long id) {
        return rutaPuntoService.getRutaPuntoById(id);
    }

    @GetMapping("/ruta/{rutaId}")
    public List<RutaPuntoResponse> listByRuta(@PathVariable Long rutaId) {
        return rutaPuntoService.listRutaPuntosByRuta(rutaId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        rutaPuntoService.deleteRutaPunto(id);
        return ResponseEntity.noContent().build();
    }
}
