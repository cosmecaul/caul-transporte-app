package cl.transporte.caul.controller;

import cl.transporte.caul.dto.RutaCreateRequest;
import cl.transporte.caul.dto.RutaUpdateRequest;
import cl.transporte.caul.dto.RutaResponse;
import cl.transporte.caul.dto.ClienteResponse;
import cl.transporte.caul.service.RutaService;
import cl.transporte.caul.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/rutas")
public class AdminRutaController {

    private final RutaService rutaService;
    private final ClienteService clienteService;

    public AdminRutaController(RutaService rutaService,
                               ClienteService clienteService) {
        this.rutaService = rutaService;
        this.clienteService = clienteService;
    }

    // =========================
    // LISTADO + FILTRO ACTIVAS
    // =========================
    @GetMapping
    public String list(@RequestParam(required = false) Boolean soloActivas,
                       Model model) {

        List<RutaResponse> rutas = rutaService.listRutas(soloActivas);

        model.addAttribute("pageTitle", "Admin · Rutas");
        model.addAttribute("menu", "rutas");
        model.addAttribute("rutas", rutas);
        model.addAttribute("soloActivas", soloActivas != null && soloActivas);

        return "admin/rutas";
    }

    // =========================
    // NUEVA RUTA (GET)
    // =========================
    @GetMapping("/nuevo")
    public String nuevoForm(Model model) {
        RutaCreateRequest form = new RutaCreateRequest();

        model.addAttribute("pageTitle", "Nueva ruta");
        model.addAttribute("menu", "rutas");
        model.addAttribute("form", form);
        model.addAttribute("formAction", "/admin/rutas/nuevo");

        cargarClientes(model);

        return "admin/ruta-form";
    }

    // =========================
    // NUEVA RUTA (POST)
    // =========================
    @PostMapping("/nuevo")
    public String crear(@ModelAttribute("form") @Valid RutaCreateRequest form,
                        BindingResult bindingResult,
                        RedirectAttributes redirectAttributes,
                        Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", "Nueva ruta");
            model.addAttribute("menu", "rutas");
            model.addAttribute("formAction", "/admin/rutas/nuevo");
            cargarClientes(model);
            return "admin/ruta-form";
        }

        rutaService.createRuta(form);
        redirectAttributes.addFlashAttribute("message", "Ruta creada correctamente");
        return "redirect:/admin/rutas";
    }

    // =========================
    // EDITAR RUTA (GET)
    // =========================
    @GetMapping("/{id}/editar")
    public String editarForm(@PathVariable Long id,
                             Model model) {

        RutaResponse r = rutaService.getRutaById(id);

        RutaUpdateRequest form = new RutaUpdateRequest();
        form.setClienteId(r.getClienteId());
        form.setNombre(r.getNombre());
        form.setDescripcion(r.getDescripcion());
        form.setKmEstimados(r.getKmEstimados());
        form.setActivo(r.getActivo());
        form.setObservaciones(r.getObservaciones());

        model.addAttribute("pageTitle", "Editar ruta · " + r.getNombre());
        model.addAttribute("menu", "rutas");
        model.addAttribute("form", form);
        model.addAttribute("rutaId", id);
        model.addAttribute("formAction", "/admin/rutas/" + id + "/editar");

        cargarClientes(model);

        return "admin/ruta-form";
    }

    // =========================
    // EDITAR RUTA (POST)
    // =========================
    @PostMapping("/{id}/editar")
    public String actualizar(@PathVariable Long id,
                             @ModelAttribute("form") @Valid RutaUpdateRequest form,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", "Editar ruta");
            model.addAttribute("menu", "rutas");
            model.addAttribute("rutaId", id);
            model.addAttribute("formAction", "/admin/rutas/" + id + "/editar");
            cargarClientes(model);
            return "admin/ruta-form";
        }

        rutaService.updateRuta(id, form);
        redirectAttributes.addFlashAttribute("message", "Ruta actualizada correctamente");
        return "redirect:/admin/rutas";
    }

    // =========================
    // HELPERS
    // =========================
    private void cargarClientes(Model model) {
        // Asumo que tienes listClientes(Boolean soloActivos)
        List<ClienteResponse> clientes = clienteService.listClientes(true);
        model.addAttribute("clientes", clientes);
    }
}
