package cl.transporte.caul.service;

import cl.transporte.caul.dto.*;
import java.util.List;

public interface RutaPuntoService {

    RutaPuntoResponse createRutaPunto(RutaPuntoCreateRequest request);

    RutaPuntoResponse updateRutaPunto(Long id, RutaPuntoUpdateRequest request);

    RutaPuntoResponse getRutaPuntoById(Long id);

    List<RutaPuntoResponse> listRutaPuntosByRuta(Long rutaId);

    void deleteRutaPunto(Long id);
}
