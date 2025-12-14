package cl.transporte.caul.controller;

import cl.transporte.caul.dto.VehiculoCreateRequest;
import cl.transporte.caul.dto.VehiculoResponse;
import cl.transporte.caul.dto.VehiculoUpdateRequest;
import cl.transporte.caul.service.VehiculoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {

    private final VehiculoService vehiculoService;

    public VehiculoController(VehiculoService vehiculoService) {
        this.vehiculoService = vehiculoService;
    }

    @GetMapping
    public List<VehiculoResponse> listVehiculos(
            @RequestParam(name = "soloActivos", required = false) Boolean soloActivos) {
        return vehiculoService.listVehiculos(soloActivos);
    }

    @GetMapping("/{id}")
    public VehiculoResponse getVehiculoById(@PathVariable Long id) {
        return vehiculoService.getVehiculoById(id);
    }

    @GetMapping("/patente/{patente}")
    public VehiculoResponse getVehiculoByPatente(@PathVariable String patente) {
        return vehiculoService.getVehiculoByPatente(patente);
    }

    @PostMapping
    public ResponseEntity<VehiculoResponse> createVehiculo(
            @Valid @RequestBody VehiculoCreateRequest request) {

        VehiculoResponse created = vehiculoService.createVehiculo(request);
        URI location = URI.create(String.format("/api/vehiculos/%d", created.getId()));
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public VehiculoResponse updateVehiculo(
            @PathVariable Long id,
            @Valid @RequestBody VehiculoUpdateRequest request) {
        return vehiculoService.updateVehiculo(id, request);
    }
}
