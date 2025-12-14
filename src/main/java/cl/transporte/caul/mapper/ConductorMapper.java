package cl.transporte.caul.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import cl.transporte.caul.model.Conductor;

@Mapper
public interface ConductorMapper {

    List<Conductor> findAll();

    List<Conductor> findActivos();

    Conductor findById(Long id);

    void insert(Conductor conductor);

    void update(Conductor conductor);

    void deleteById(Long id);
    
    Long findIdByRut(@Param("rut") String rut);
    
    
}
