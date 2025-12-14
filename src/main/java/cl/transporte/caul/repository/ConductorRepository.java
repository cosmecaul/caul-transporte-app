package cl.transporte.caul.repository;

import cl.transporte.caul.model.Conductor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConductorRepository extends JpaRepository<Conductor, Long> {

    Optional<Conductor> findByRut(String rut);

    boolean existsByRut(String rut);
    
    

    List<Conductor> findByActivoTrue();
}
