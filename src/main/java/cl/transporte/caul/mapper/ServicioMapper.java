package cl.transporte.caul.mapper;

import java.util.List;
import java.util.Date;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cl.transporte.caul.model.Servicio;



@Mapper
public interface ServicioMapper {

    List<Servicio> findAll();

    Servicio findById(Long id);

    void insert(Servicio servicio);

    void update(Servicio servicio);

    void delete(Long id);
    
    List<Servicio> findByConductorAndRangoFechas(
            @Param("idConductor") Long idConductor,
            @Param("desde") Date desde,
            @Param("hasta") Date hasta
    );

    List<Servicio> findByConductorAll(@Param("idConductor") Long idConductor);
    
}
