package cl.transporte.caul.controller;

import cl.transporte.caul.dto.UsuarioCreateRequest;
import cl.transporte.caul.dto.UsuarioResponse;
import cl.transporte.caul.dto.UsuarioUpdateRequest;
import cl.transporte.caul.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // GET /api/usuarios
    @GetMapping
    public List<UsuarioResponse> listUsuarios() {
        return usuarioService.listUsuarios();
    }

    // GET /api/usuarios/{id}
    @GetMapping("/{id}")
    public UsuarioResponse getUsuarioById(@PathVariable Long id) {
        return usuarioService.getUsuarioById(id);
    }

    // POST /api/usuarios
    @PostMapping
    public ResponseEntity<UsuarioResponse> createUsuario(
            @Valid @RequestBody UsuarioCreateRequest request) {

        UsuarioResponse created = usuarioService.createUsuario(request);
        URI location = URI.create(String.format("/api/usuarios/%d", created.getId()));
        return ResponseEntity.created(location).body(created);
    }

    // PUT /api/usuarios/{id}
    @PutMapping("/{id}")
    public UsuarioResponse updateUsuario(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioUpdateRequest request) {
        return usuarioService.updateUsuario(id, request);
    }
}
