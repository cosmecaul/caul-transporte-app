package cl.transporte.caul.service;

import cl.transporte.caul.dto.VehiculoCreateRequest;
import cl.transporte.caul.dto.VehiculoUpdateRequest;
import cl.transporte.caul.dto.VehiculoResponse;

import java.util.List;

public interface VehiculoService {

	VehiculoResponse createVehiculo(VehiculoCreateRequest request);
	VehiculoResponse updateVehiculo(Long id, VehiculoUpdateRequest request);
	VehiculoResponse getVehiculoById(Long id);
	VehiculoResponse getVehiculoByPatente(String patente);
	List<VehiculoResponse> listVehiculos(Boolean soloActivos);
}
