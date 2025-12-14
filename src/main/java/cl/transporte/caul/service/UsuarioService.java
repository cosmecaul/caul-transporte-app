package cl.transporte.caul.service;

import cl.transporte.caul.dto.UsuarioCreateRequest;
import cl.transporte.caul.dto.UsuarioResponse;
import cl.transporte.caul.dto.UsuarioUpdateRequest;

import java.util.List;

public interface UsuarioService {

    List<UsuarioResponse> listUsuarios();

    UsuarioResponse getUsuarioById(Long id);

    UsuarioResponse createUsuario(UsuarioCreateRequest request);

    UsuarioResponse updateUsuario(Long id, UsuarioUpdateRequest request);
    
    void deleteUsuario(Long id);
}
