package cl.transporte.caul.mapper;

import java.util.List;

import cl.transporte.caul.model.Ruta;

public interface RutaMapper {

    List<Ruta> findAll();

    Ruta findById(Long id);

    List<Ruta> findByCliente(Long idCliente);

    void insert(Ruta ruta);

    void update(Ruta ruta);

    void delete(Long id);
}
