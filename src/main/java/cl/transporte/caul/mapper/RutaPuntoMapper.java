package cl.transporte.caul.mapper;

import java.util.List;

import cl.transporte.caul.model.RutaPunto;

public interface RutaPuntoMapper {

    List<RutaPunto> findByRuta(Long idRuta);

    RutaPunto findById(Long id);

    void insert(RutaPunto punto);

    void update(RutaPunto punto);

    void delete(Long id);

    void deleteByRuta(Long idRuta);
}
