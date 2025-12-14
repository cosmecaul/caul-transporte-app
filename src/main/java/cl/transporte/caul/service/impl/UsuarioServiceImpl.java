package cl.transporte.caul.service.impl;

import cl.transporte.caul.dto.UsuarioCreateRequest;
import cl.transporte.caul.dto.UsuarioResponse;
import cl.transporte.caul.dto.UsuarioUpdateRequest;
import cl.transporte.caul.mapper.UsuarioMapper;
import cl.transporte.caul.model.Cliente;
import cl.transporte.caul.model.Usuario;
import cl.transporte.caul.model.Vehiculo;
import cl.transporte.caul.repository.ClienteRepository;
import cl.transporte.caul.repository.UsuarioRepository;
import cl.transporte.caul.service.UsuarioService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;


    public UsuarioServiceImpl(UsuarioRepository usuarioRepository,
    		UsuarioMapper usuarioMapper,
                              PasswordEncoder passwordEncoder) {
        this.usuarioMapper = usuarioMapper;
        this.passwordEncoder = passwordEncoder;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioResponse> listUsuarios() {
        return usuarioMapper.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponse getUsuarioById(Long id) {
        Usuario usuario = usuarioMapper.findById(id);
        if (usuario == null) {
            throw new NoSuchElementException("Usuario no encontrado: " + id);
        }
        return mapToResponse(usuario);
    }

    @Override
    public UsuarioResponse createUsuario(UsuarioCreateRequest request) {
    	
    	boolean exists = usuarioMapper.existsByUsername(request.getNombreUsuario());

        if (exists) {
            throw new IllegalArgumentException("El nombre de usuario ya existe: " + request.getNombreUsuario());
        }

        Usuario u = new Usuario();
         
        
        u.setNombreUsuario(trimOrNull(request.getNombreUsuario()));
        u.setPasswordHash(trimOrNull(passwordEncoder.encode(request.getPassword())));
        u.setNombreCompleto(trimOrNull(request.getNombreCompleto()));
        u.setEmail(trimOrNull(request.getEmail()));
        u.setRut(trimOrNull(request.getRut()));
        u.setRol(trimOrNull(request.getRol()));
        u.setClienteId(request.getIdCliente());
        u.setActivo(request.getActivo() != null ? request.getActivo() : Boolean.TRUE);
        u.setCreatedAt(new Date());
        u.setUpdatedAt(new Date());

        
        Usuario saved = usuarioRepository.save(u);
       // usuarioMapper.insertUsuario(u);

        return mapToResponse(saved);
    }

    @Override
    public UsuarioResponse updateUsuario(Long id, UsuarioUpdateRequest request) {
        Usuario existente = usuarioMapper.findById(id);
        if (existente == null) {
            throw new NoSuchElementException("Usuario no encontrado: " + id);
        }

        if (request.getNombreCompleto() != null) {
            existente.setNombreCompleto(request.getNombreCompleto());
        }
        if (request.getEmail() != null) {
            existente.setEmail(request.getEmail());
        }
        if (request.getRut() != null) {
            existente.setRut(request.getRut());
        }
        if (request.getRol() != null) {
            existente.setRol(request.getRol());
        }
        if (request.getIdCliente() != null) {
            existente.setClienteId(request.getIdCliente());
        }
        if (request.getActivo() != null) {
            existente.setActivo(request.getActivo());
        }
        if (request.getPasswordNuevo() != null && !request.getPasswordNuevo().isBlank()) {
            existente.setPasswordHash(passwordEncoder.encode(request.getPasswordNuevo()));
        }

        existente.setUpdatedAt(new Date());
        //usuarioMapper.updateUsuario(existente,id);
        
        Usuario saved = usuarioRepository.save(existente);
        return mapToResponse(saved);

        //return mapToResponse(existente);
    }

    private UsuarioResponse mapToResponse(Usuario u) {
        UsuarioResponse r = new UsuarioResponse();
        r.setId(u.getId());
        r.setNombreUsuario(u.getNombreUsuario());
        r.setNombreCompleto(u.getNombreCompleto());
        r.setEmail(u.getEmail());
        r.setRut(u.getRut());
        r.setRol(u.getRol());
        r.setIdCliente(u.getClienteId());
        r.setActivo(u.getActivo());
        r.setCreatedAt(u.getCreatedAt());
        r.setUpdatedAt(u.getUpdatedAt());
        return r;
    }
    private String trimOrNull(String value) {
        if (value == null) return null;
        String t = value.trim();
        return t.isEmpty() ? null : t;
    }
    
    @Override
    public void deleteUsuario(Long id) {
    	
        Usuario existente = usuarioMapper.findById(id);
        
        if (existente == null) {
            throw new NoSuchElementException("Usuario no encontrado: " + id);
        }
        else {
    	usuarioRepository.delete(existente);
        } 
    }
    
}

