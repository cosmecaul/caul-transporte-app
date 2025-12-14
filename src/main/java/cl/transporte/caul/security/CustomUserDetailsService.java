package cl.transporte.caul.security;

import cl.transporte.caul.mapper.UsuarioMapper;
import cl.transporte.caul.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("🔍 [Auth] Buscando usuario: " + username);

        Usuario usuario = usuarioMapper.findByUsername(username);

        if (usuario == null) {
            System.out.println("❌ [Auth] Usuario NO encontrado");
            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
        }

        String rawRole = (usuario.getRol() == null) ? "" : usuario.getRol().trim().toUpperCase();

        // A prueba de balas: si viene "ADMIN" => "ROLE_ADMIN"
        // si viene "ROLE_ADMIN" => se deja tal cual
        String springRole = rawRole.startsWith("ROLE_") ? rawRole : "ROLE_" + rawRole;

        System.out.println("✅ [Auth] Usuario encontrado: " + usuario.getNombreUsuario()
                + " | rolDB=" + usuario.getRol()
                + " | authority=" + springRole
                + " | activo=" + usuario.getActivo());

        List<SimpleGrantedAuthority> authorities =
                List.of(new SimpleGrantedAuthority(springRole));

        return new org.springframework.security.core.userdetails.User(
                usuario.getNombreUsuario(),
                usuario.getPasswordHash(),
                Boolean.TRUE.equals(usuario.getActivo()),
                true,
                true,
                true,
                authorities
        );
    }

}
