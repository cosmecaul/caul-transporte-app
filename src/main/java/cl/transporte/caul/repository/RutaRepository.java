package cl.transporte.caul.repository;

import cl.transporte.caul.model.Ruta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RutaRepository extends JpaRepository<Ruta, Long> {

    List<Ruta> findByClienteId(Long clienteId);

    List<Ruta> findByActivoTrue();
}
