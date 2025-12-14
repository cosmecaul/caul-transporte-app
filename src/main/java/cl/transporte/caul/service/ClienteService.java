package cl.transporte.caul.service;

import cl.transporte.caul.dto.ClienteCreateRequest;
import cl.transporte.caul.dto.ClienteUpdateRequest;
import cl.transporte.caul.dto.ClienteResponse;

import java.util.List;

public interface ClienteService {

    ClienteResponse createCliente(ClienteCreateRequest request);

    ClienteResponse updateCliente(Long id, ClienteUpdateRequest request);

    ClienteResponse getClienteById(Long id);

    ClienteResponse getClienteByRut(String rut);

    List<ClienteResponse> listClientes(Boolean soloActivos);
}
