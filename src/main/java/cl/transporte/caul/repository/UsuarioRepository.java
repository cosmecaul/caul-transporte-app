package cl.transporte.caul.repository;

import cl.transporte.caul.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findById(Long Id);
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);

    List<Usuario> findByActivoTrue();
}
