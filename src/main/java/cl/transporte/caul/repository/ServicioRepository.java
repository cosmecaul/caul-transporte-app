package cl.transporte.caul.repository;

import cl.transporte.caul.model.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Long> {

    // ---- KPIs Dashboard ----
    long countByFecha(Date fecha);

    long countByFechaBetween(Date inicio, Date fin);
    
    
    List<Servicio> findByVehiculoIdAndFecha(Long vehiculoId, Date fecha);

    // ---- Listado por rango ordenado ----
    List<Servicio> findByFechaBetweenOrderByFechaAscHoraInicioProgramadaAsc(
            Date inicio,
            Date fin
    );

    List<Servicio> findByConductorIdAndFecha(Long conductorId, Date fecha);

    
    List<Servicio> findByFecha(Date fecha);
    
    List<Servicio> findByConductorIdAndFechaAndEstadoIn(
            Long conductorId,
            Date fecha,
            List<String> estados
    );
    
    List<Servicio> findByConductorIdAndFechaBetweenOrderByFechaDescHoraInicioProgramadaDesc(
            Long conductorId,
            Date d,
            Date h
    );
    
    List<Servicio> findByConductorIdAndFechaBetweenOrderByFechaAscHoraInicioProgramadaAsc(
            Long conductorId,
            Date inicio,
            Date fin
    );
    
}