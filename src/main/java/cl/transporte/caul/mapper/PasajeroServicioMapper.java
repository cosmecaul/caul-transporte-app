package cl.transporte.caul.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cl.transporte.caul.model.PasajeroServicio;

@Mapper
public interface PasajeroServicioMapper {

    List<PasajeroServicio> findByServicioId(Long idServicio);

    PasajeroServicio findById(Long id);

    void insert(PasajeroServicio pasajeroServicio);

    void deleteById(Long id);

    void deleteByServicioId(Long idServicio);
    
  }
