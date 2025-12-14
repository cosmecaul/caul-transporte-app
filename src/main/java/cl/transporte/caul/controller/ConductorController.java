package cl.transporte.caul.controller;

import cl.transporte.caul.dto.ConductorCreateRequest;
import cl.transporte.caul.dto.ConductorResponse;
import cl.transporte.caul.dto.ConductorUpdateRequest;
import cl.transporte.caul.service.ConductorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/conductores")
public class ConductorController {

    private final ConductorService conductorService;

    public ConductorController(ConductorService conductorService) {
        this.conductorService = conductorService;
    }

    @GetMapping
    public List<ConductorResponse> listConductores(
            @RequestParam(name = "soloActivos", required = false) Boolean soloActivos) {
        return conductorService.listConductores(soloActivos);
    }

    @GetMapping("/{id}")
    public ConductorResponse getConductorById(@PathVariable Long id) {
        return conductorService.getConductorById(id);
    }

    @GetMapping("/rut/{rut}")
    public ConductorResponse getConductorByRut(@PathVariable String rut) {
        return conductorService.getConductorByRut(rut);
    }

    @PostMapping
    public ResponseEntity<ConductorResponse> createConductor(
            @Valid @RequestBody ConductorCreateRequest request) {

        ConductorResponse created = conductorService.createConductor(request);
        URI location = URI.create(String.format("/api/conductores/%d", created.getId()));
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ConductorResponse updateConductor(
            @PathVariable Long id,
            @Valid @RequestBody ConductorUpdateRequest request) {
        return conductorService.updateConductor(id, request);
    }
}
