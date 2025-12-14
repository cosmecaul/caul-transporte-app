package cl.transporte.caul.controller;

import cl.transporte.caul.dto.ClienteCreateRequest;
import cl.transporte.caul.dto.ClienteResponse;
import cl.transporte.caul.dto.ClienteUpdateRequest;
import cl.transporte.caul.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    /**
     * Listar clientes.
     * Ej: GET /api/clientes?soloActivos=true
     */
    @GetMapping
    public List<ClienteResponse> listClientes(
            @RequestParam(name = "soloActivos", required = false) Boolean soloActivos) {
        return clienteService.listClientes(soloActivos);
    }

    /**
     * Obtener cliente por ID.
     * Ej: GET /api/clientes/1
     */
    @GetMapping("/{id}")
    public ClienteResponse getClienteById(@PathVariable Long id) {
        return clienteService.getClienteById(id);
    }

    /**
     * Obtener cliente por RUT.
     * Ej: GET /api/clientes/rut/12345678K
     *     (rut sin puntos, con o sin guión en la URL)
     */
    @GetMapping("/rut/{rut}")
    public ClienteResponse getClienteByRut(@PathVariable String rut) {
        return clienteService.getClienteByRut(rut);
    }

    /**
     * Crear nuevo cliente.
     * Ej: POST /api/clientes
     * Body (JSON):
     * {
     *   "razonSocial": "Empresa X",
     *   "rut": "12345678-9",
     *   "giro": "Transporte",
     *   ...
     * }
     */
    @PostMapping
    public ResponseEntity<ClienteResponse> createCliente(
            @Valid @RequestBody ClienteCreateRequest request) {

        ClienteResponse created = clienteService.createCliente(request);

        // Construimos location: /api/clientes/{id}
        URI location = URI.create(String.format("/api/clientes/%d", created.getId()));
        return ResponseEntity.created(location).body(created);
    }

    /**
     * Actualizar cliente.
     * Ej: PUT /api/clientes/1
     */
    @PutMapping("/{id}")
    public ClienteResponse updateCliente(
            @PathVariable Long id,
            @Valid @RequestBody ClienteUpdateRequest request) {
        return clienteService.updateCliente(id, request);
    }
}
