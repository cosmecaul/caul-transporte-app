package cl.transporte.caul.repository;

import cl.transporte.caul.model.RutaPunto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RutaPuntoRepository extends JpaRepository<RutaPunto, Long> {

    List<RutaPunto> findByRutaIdOrderByOrdenAsc(Long rutaId);
}
