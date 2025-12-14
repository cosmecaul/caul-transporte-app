package cl.transporte.caul.service;

import cl.transporte.caul.dto.ClienteCreateRequest;
import cl.transporte.caul.dto.ClienteUpdateRequest;
import cl.transporte.caul.dto.ClienteResponse;
import cl.transporte.caul.model.Cliente;
import cl.transporte.caul.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public ClienteResponse createCliente(ClienteCreateRequest request) {
        String rutNormalizado = normalizarRut(request.getRut());
        validarRut(rutNormalizado);

        if (clienteRepository.existsByRut(rutNormalizado)) {
            throw new IllegalStateException("Ya existe un cliente con el RUT " + rutNormalizado);
        }

        Cliente cliente = new Cliente();
        cliente.setRazonSocial(trimOrNull(request.getRazonSocial()));
        cliente.setRut(rutNormalizado);
        cliente.setGiro(trimOrNull(request.getGiro()));
        cliente.setDireccion(trimOrNull(request.getDireccion()));
        cliente.setComuna(trimOrNull(request.getComuna()));
        cliente.setCiudad(trimOrNull(request.getCiudad()));
        cliente.setContactoNombre(trimOrNull(request.getContactoNombre()));
        cliente.setContactoEmail(trimOrNull(request.getContactoEmail()));
        cliente.setContactoFono(trimOrNull(request.getContactoFono()));
        cliente.setActivo(request.getActivo() != null ? request.getActivo() : Boolean.TRUE);

        LocalDateTime now = LocalDateTime.now();
        cliente.setCreatedAt(now);
        cliente.setUpdatedAt(now);

        Cliente saved = clienteRepository.save(cliente);
        return mapToResponse(saved);
    }

    @Override
    public ClienteResponse updateCliente(Long id, ClienteUpdateRequest request) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cliente no encontrado con id " + id));

        if (request.getRut() != null && !request.getRut().isBlank()) {
            String rutNormalizado = normalizarRut(request.getRut());
            validarRut(rutNormalizado);

            if (!rutNormalizado.equalsIgnoreCase(cliente.getRut())
                    && clienteRepository.existsByRut(rutNormalizado)) {
                throw new IllegalStateException("Ya existe otro cliente con el RUT " + rutNormalizado);
            }
            cliente.setRut(rutNormalizado);
        }

        if (request.getRazonSocial() != null) {
            cliente.setRazonSocial(trimOrNull(request.getRazonSocial()));
        }
        if (request.getGiro() != null) {
            cliente.setGiro(trimOrNull(request.getGiro()));
        }
        if (request.getDireccion() != null) {
            cliente.setDireccion(trimOrNull(request.getDireccion()));
        }
        if (request.getComuna() != null) {
            cliente.setComuna(trimOrNull(request.getComuna()));
        }
        if (request.getCiudad() != null) {
            cliente.setCiudad(trimOrNull(request.getCiudad()));
        }
        if (request.getContactoNombre() != null) {
            cliente.setContactoNombre(trimOrNull(request.getContactoNombre()));
        }
        if (request.getContactoEmail() != null) {
            cliente.setContactoEmail(trimOrNull(request.getContactoEmail()));
        }
        if (request.getContactoFono() != null) {
            cliente.setContactoFono(trimOrNull(request.getContactoFono()));
        }
        if (request.getActivo() != null) {
            cliente.setActivo(request.getActivo());
        }

        cliente.setUpdatedAt(LocalDateTime.now());

        Cliente saved = clienteRepository.save(cliente);
        return mapToResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteResponse getClienteById(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cliente no encontrado con id " + id));
        return mapToResponse(cliente);
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteResponse getClienteByRut(String rut) {
        String rutNormalizado = normalizarRut(rut);
        Cliente cliente = clienteRepository.findByRut(rutNormalizado)
                .orElseThrow(() -> new NoSuchElementException("Cliente no encontrado con RUT " + rutNormalizado));
        return mapToResponse(cliente);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClienteResponse> listClientes(Boolean soloActivos) {
        List<Cliente> clientes;

        if (Boolean.TRUE.equals(soloActivos)) {
            clientes = clienteRepository.findByActivoTrue();
        } else {
            clientes = clienteRepository.findAll();
        }

        return clientes.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ---------- Métodos auxiliares privados ----------

    private ClienteResponse mapToResponse(Cliente cliente) {
        ClienteResponse resp = new ClienteResponse();
        resp.setId(cliente.getId());
        resp.setRazonSocial(cliente.getRazonSocial());
        resp.setRut(cliente.getRut());
        resp.setGiro(cliente.getGiro());
        resp.setDireccion(cliente.getDireccion());
        resp.setComuna(cliente.getComuna());
        resp.setCiudad(cliente.getCiudad());
        resp.setContactoNombre(cliente.getContactoNombre());
        resp.setContactoEmail(cliente.getContactoEmail());
        resp.setContactoFono(cliente.getContactoFono());
        resp.setActivo(cliente.getActivo());
        resp.setCreatedAt(cliente.getCreatedAt());
        resp.setUpdatedAt(cliente.getUpdatedAt());
        return resp;
    }

    private String trimOrNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    private String normalizarRut(String rutRaw) {
        if (rutRaw == null) {
            throw new IllegalArgumentException("El RUT no puede ser nulo");
        }
        String rut = rutRaw.trim().toUpperCase();
        rut = rut.replace(".", "").replace("-", "");
        return rut;
    }

    private void validarRut(String rut) {
        if (rut.length() < 2) {
            throw new IllegalArgumentException("RUT inválido");
        }

        String cuerpo = rut.substring(0, rut.length() - 1);
        char dvIngresado = rut.charAt(rut.length() - 1);

        if (!cuerpo.chars().allMatch(Character::isDigit)) {
            throw new IllegalArgumentException("RUT inválido");
        }

        int suma = 0;
        int factor = 2;
        for (int i = cuerpo.length() - 1; i >= 0; i--) {
            int num = Character.getNumericValue(cuerpo.charAt(i));
            suma += num * factor;
            factor++;
            if (factor > 7) {
                factor = 2;
            }
        }

        int resto = suma % 11;
        int dvCalcNum = 11 - resto;
        char dvCalculado;

        if (dvCalcNum == 11) {
            dvCalculado = '0';
        } else if (dvCalcNum == 10) {
            dvCalculado = 'K';
        } else {
            dvCalculado = (char) ('0' + dvCalcNum);
        }

        if (dvIngresado != dvCalculado) {
            throw new IllegalArgumentException("RUT inválido");
        }
    }
}
