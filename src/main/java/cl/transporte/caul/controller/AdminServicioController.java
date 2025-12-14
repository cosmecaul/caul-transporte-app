///////////////////////////////////////////////
//------ codigo para admin-ashboard.html   ////
///////////////////////////////////////////////


package cl.transporte.caul.controller;

import cl.transporte.caul.dto.PasajeroServicioEditRequest;
import cl.transporte.caul.dto.PasajeroServicioRequest;
import cl.transporte.caul.dto.ServicioCreateRequest;
import cl.transporte.caul.dto.ServicioEditRequest;
import cl.transporte.caul.service.ServicioService;
import cl.transporte.caul.repository.ClienteRepository;
import cl.transporte.caul.repository.ConductorRepository;
import cl.transporte.caul.repository.VehiculoRepository;
import cl.transporte.caul.repository.RutaRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.NoSuchElementException;

@Controller
@RequestMapping("/admin/servicios")
public class AdminServicioController {

    private final ServicioService servicioService;
    private final ClienteRepository clienteRepository;
    private final VehiculoRepository vehiculoRepository;
    private final ConductorRepository conductorRepository;
    private final RutaRepository rutaRepository;

    public AdminServicioController(ServicioService servicioService,
                                   ClienteRepository clienteRepository,
                                   VehiculoRepository vehiculoRepository,
                                   ConductorRepository conductorRepository,
                                   RutaRepository rutaRepository) {
        this.servicioService = servicioService;
        this.clienteRepository = clienteRepository;
        this.vehiculoRepository = vehiculoRepository;
        this.conductorRepository = conductorRepository;
        this.rutaRepository = rutaRepository;
    }

    // =========================================================
    //            MÉTODO PRIVADO: LISTAS MAESTRAS
    // =========================================================
    private void cargarListasMaestras(Model model) {
        model.addAttribute("clientes", clienteRepository.findAll());
        model.addAttribute("vehiculos", vehiculoRepository.findAll());
        model.addAttribute("conductores", conductorRepository.findAll());
        model.addAttribute("rutas", rutaRepository.findAll());
    }

    // =========================================================
    //                    CREAR SERVICIO
    // =========================================================
    @GetMapping("/nuevo")
    public String mostrarFormNuevoServicio(Model model) {
        ServicioCreateRequest form = new ServicioCreateRequest();
        model.addAttribute("form", form);
        cargarListasMaestras(model);
        model.addAttribute("pageTitle", "Nuevo servicio");
        return "admin-servicio-nuevo";
    }

    @PostMapping("/nuevo")
    public String crearServicio(
            @Valid @ModelAttribute("form") ServicioCreateRequest form,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            cargarListasMaestras(model);
            model.addAttribute("pageTitle", "Nuevo servicio");
            return "admin-servicio-nuevo";
        }

        try {
            servicioService.crearServicioConPasajeros(form);
            redirectAttributes.addFlashAttribute("messageSuccess", "Servicio creado correctamente");
            return "redirect:/aplicacion/servicios";
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("messageError", "Error al crear servicio: " + ex.getMessage());
            return "redirect:/admin/servicios/nuevo";
        }
    }

    // =========================================================
    //                    EDITAR SERVICIO
    // =========================================================

    /**
     * GET /admin/servicios/{id}/editar
     */
    @GetMapping("/{id}/editar")
    public String mostrarFormEditarServicio(@PathVariable Long id,
                                            Model model,
                                            RedirectAttributes redirectAttributes) {

        try {
            ServicioEditRequest form = servicioService.getServicioEditById(id);
            model.addAttribute("form", form);

            cargarListasMaestras(model);
            model.addAttribute("pageTitle", "Editar servicio #" + id);
            model.addAttribute("id", id);

            // Si tienes historial, puedes cargarlo aquí:
            try {
               // model.addAttribute("historial", servicioService.getHistorialServicio(id));
            } catch (Exception e) {
                // opcional: no romper si no existe implementación de historial
                model.addAttribute("historial", null);
            }

            return "admin-servicio-editar";
        } catch (NoSuchElementException ex) {
            redirectAttributes.addFlashAttribute("messageError", "Servicio no encontrado con id " + id);
            return "redirect:/aplicacion/servicios";
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("messageError", "Error al cargar servicio: " + ex.getMessage());
            return "redirect:/aplicacion/servicios";
        }
    }

    // =========================================================
    //                    ELIMINAR SERVICIO
    // =========================================================

    @PostMapping("/{id}/eliminar")
    public String eliminarServicio(@PathVariable Long id,
                                   RedirectAttributes redirectAttributes) {
        try {
            servicioService.eliminarServicio(id);
            redirectAttributes.addFlashAttribute("messageSuccess",
                    "Servicio #" + id + " eliminado correctamente.");
        } catch (NoSuchElementException ex) {
            redirectAttributes.addFlashAttribute("messageError",
                    "No se encontró el servicio con id " + id);
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("messageError",
                    "Error al eliminar servicio: " + ex.getMessage());
        }

        // a donde quieras volver después de eliminar:
        return "redirect:/admin/dashboard";
    }

    /**
     * POST /admin/servicios/{id}/editar
     * Aquí se actualiza el servicio y su lista de pasajeros.
     * Los pasajeros que vengan con eliminar = true en el form
     * deben ser borrados en el método servicioService.updateServicioConPasajeros(...)
     */
    @PostMapping("/{id}/editar")
    public String actualizarServicio(
            @PathVariable Long id,
            @Valid @ModelAttribute("form") ServicioEditRequest form,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            cargarListasMaestras(model);
            model.addAttribute("pageTitle", "Editar servicio #" + id);
            model.addAttribute("id", id);
            return "admin-servicio-editar";
        }

        try {
           
        	
        	for (PasajeroServicioEditRequest pDto : form.getPasajeros()) {
        	    if (Boolean.TRUE.equals(pDto.getEliminar())) {
        	        // borrar de la BD si tiene id
        	        if (pDto.getId() != null) {
        	            servicioService.eliminarPasajero(id,pDto.getId());
        	        }
        	    }
             }
        	       	
            servicioService.updateServicioConPasajeros(id, form);

            redirectAttributes.addFlashAttribute("messageSuccess",
                    "Servicio y pasajeros actualizados correctamente.");

            // Te dejo en la MISMA vista de edición (no en el dashboard)
            return "redirect:/admin/servicios/" + id + "/editar";

        } catch (NoSuchElementException ex) {
            redirectAttributes.addFlashAttribute("messageError",
                    "Servicio no encontrado con id " + id);
            return "redirect:/admin/servicios/" + id + "/editar";
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("messageError",
                    "Error al actualizar servicio: " + ex.getMessage());
            return "redirect:/admin/servicios/" + id + "/editar";
        }
    }
}
