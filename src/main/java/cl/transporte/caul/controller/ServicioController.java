package cl.transporte.caul.controller;

import cl.transporte.caul.dto.ServicioCreateRequest;
import cl.transporte.caul.dto.ServicioUpdateRequest;
import cl.transporte.caul.dto.ServicioIniciarRequest;
import cl.transporte.caul.dto.ServicioFinalizarRequest;
import cl.transporte.caul.dto.ServicioResponse;
import cl.transporte.caul.service.ServicioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import cl.transporte.caul.model.Servicio;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



import java.util.List;

@Controller
@RequestMapping("/servicios")
@CrossOrigin(origins = "*")
public class ServicioController {

    private final ServicioService servicioService;

    public ServicioController(ServicioService servicioService) {
        this.servicioService = servicioService;
    }

    // =========================================================
    //                     CRUD BÁSICO
    // =========================================================
    @PostMapping("/{servicioId}/pasajeros/{pasajeroId}/eliminar")
    public String eliminarPasajero(
            @PathVariable Long servicioId,
            @PathVariable Long pasajeroId,
            RedirectAttributes redirectAttributes) {

        servicioService.eliminarPasajero(servicioId, pasajeroId);

        redirectAttributes.addFlashAttribute("success", "Pasajero eliminado correctamente.");
        // Volvemos a la misma pantalla de edición del servicio
        return "redirect:/admin/servicios/" + servicioId + "/editar";
    }
       
    
    
    @PostMapping("/{id}/editar")
    public String actualizarServicio(
            @PathVariable Long id,
            @ModelAttribute("servicioForm") ServicioUpdateRequest form,
            RedirectAttributes redirectAttributes) {

        servicioService.actualizarServicio(id, form);

        redirectAttributes.addFlashAttribute("success",
                "Servicio actualizado correctamente (pasajeros se mantienen).");

        return "redirect:/servicios";
    }
    
    @GetMapping("/{id}/editar")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model) {
        Servicio servicio = servicioService.buscarPorId(id);

        ServicioUpdateRequest form = new ServicioUpdateRequest();
        form.setId(servicio.getId());
        form.setClienteId(servicio.getClienteId());
        form.setVehiculoId(servicio.getVehiculoId());
        form.setConductorId(servicio.getConductorId());
        form.setRutaId(servicio.getRutaId());
        form.setFecha(servicio.getFecha());
        form.setHoraInicioProgramada(servicio.getHoraInicioProgramada());
        form.setHoraFinProgramada(servicio.getHoraFinProgramada());

        model.addAttribute("servicioForm", form);

        // Si quieres mostrar pasajeros en la vista, hazlo por separado:
        model.addAttribute("pasajeros", servicio.getPasajeros());
        

        return "servicios/editar"; // tu plantilla Thymeleaf
    }
    
    
    
    
    @PostMapping
    public ResponseEntity<ServicioResponse> create(@RequestBody ServicioCreateRequest request) {
        ServicioResponse response = servicioService.createServicio(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServicioResponse> update(
            @PathVariable Long id,
            @RequestBody ServicioUpdateRequest request) {

        ServicioResponse response = servicioService.updateServicio(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicioResponse> getById(@PathVariable Long id) {
        ServicioResponse response = servicioService.getServicioById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ServicioResponse>> listAll() {
        List<ServicioResponse> lista = servicioService.listServicios();
        return ResponseEntity.ok(lista);
    }

    // =========================================================
    //                    QUERIES ESPECIALES
    // =========================================================

    // Por fecha: /api/servicios/fecha/2025-11-30
    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<ServicioResponse>> listByFecha(@PathVariable String fecha) {
        List<ServicioResponse> lista = servicioService.listServiciosPorFecha(fecha);
        return ResponseEntity.ok(lista);
    }

    // Por cliente: /api/servicios/cliente/1
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<ServicioResponse>> listByCliente(@PathVariable Long clienteId) {
        List<ServicioResponse> lista = servicioService.listServiciosPorCliente(clienteId);
        return ResponseEntity.ok(lista);
    }

    // =========================================================
    //            OPERACIÓN: INICIAR / FINALIZAR / ESTADO
    // =========================================================

    // Iniciar servicio: POST /api/servicios/{id}/iniciar
    @PostMapping("/{id}/iniciar")
    public ResponseEntity<ServicioResponse> iniciar(
            @PathVariable Long id,
            @RequestBody ServicioIniciarRequest request) {

        ServicioResponse response = servicioService.iniciarServicio(id, request);
        return ResponseEntity.ok(response);
    }

    // Finalizar servicio: POST /api/servicios/{id}/finalizar
    @PostMapping("/{id}/finalizar")
    public ResponseEntity<ServicioResponse> finalizar(
            @PathVariable Long id,
            @RequestBody ServicioFinalizarRequest request) {

        ServicioResponse response = servicioService.finalizarServicio(id, request);
        return ResponseEntity.ok(response);
    }

    // Cambiar estado directo (ej: CANCELADO, NO_REALIZADO)
    // PATCH /api/servicios/{id}/estado?estado=CANCELADO
    @PatchMapping("/{id}/estado")
    public ResponseEntity<Void> cambiarEstado(
            @PathVariable Long id,
            @RequestParam("estado") String nuevoEstado) {

        servicioService.cambiarEstado(id, nuevoEstado);
        return ResponseEntity.noContent().build();
    }
}
