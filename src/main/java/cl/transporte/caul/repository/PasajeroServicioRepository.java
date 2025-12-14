package cl.transporte.caul.repository;

import cl.transporte.caul.model.PasajeroServicio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PasajeroServicioRepository extends JpaRepository<PasajeroServicio, Long> {

    // Sin orden (el que ya tenías)
    List<PasajeroServicio> findByServicioId(Long servicioId);

    // ✅ Con orden por id asc (el que te falta)
    List<PasajeroServicio> findByServicioIdOrderByIdAsc(Long servicioId);

    // Si quieres borrar todos los pasajeros de un servicio
    void deleteByServicioId(Long servicioId);
}
