package cl.transporte.caul.mapper;

import java.util.List;
import cl.transporte.caul.model.Vehiculo;

public interface VehiculoMapper {

    List<Vehiculo> findAll();

    Vehiculo findById(Long id);

    void insert(Vehiculo vehiculo);

    void update(Vehiculo vehiculo);

    void delete(Long id);
}
