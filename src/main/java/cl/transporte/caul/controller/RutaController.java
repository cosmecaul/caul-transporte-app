package cl.transporte.caul.controller;

import cl.transporte.caul.dto.*;
import cl.transporte.caul.service.RutaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/rutas")
public class RutaController {

    private final RutaService rutaService;

    public RutaController(RutaService rutaService) {
        this.rutaService = rutaService;
    }

    @PostMapping
    public ResponseEntity<RutaResponse> createRuta(
            @Valid @RequestBody RutaCreateRequest request) {

        RutaResponse created = rutaService.createRuta(request);
        URI location = URI.create("/api/rutas/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public RutaResponse updateRuta(
            @PathVariable Long id,
            @Valid @RequestBody RutaUpdateRequest request) {
        return rutaService.updateRuta(id, request);
    }

    @GetMapping("/{id}")
    public RutaResponse getRutaById(@PathVariable Long id) {
        return rutaService.getRutaById(id);
    }

    @GetMapping
    public List<RutaResponse> listRutas(
            @RequestParam(required = false) Boolean soloActivos) {
        return rutaService.listRutas(soloActivos);
    }

    @GetMapping("/cliente/{clienteId}")
    public List<RutaResponse> listRutasByCliente(@PathVariable Long clienteId) {
        return rutaService.listRutasByCliente(clienteId);
    }
}
