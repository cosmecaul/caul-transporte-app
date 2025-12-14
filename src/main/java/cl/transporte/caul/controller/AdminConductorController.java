package cl.transporte.caul.controller;

import cl.transporte.caul.dto.ConductorCreateRequest;
import cl.transporte.caul.service.ConductorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/admin/conductores")
public class AdminConductorController {

    @Autowired
    private ConductorService conductorService;

    @GetMapping
    public String listarConductores(Model model) {
        model.addAttribute("conductores", conductorService.listConductores(false));
        // 👇 carpeta admin + archivo admin-conductores.html
        return "admin/admin-conductores";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        ConductorCreateRequest form = new ConductorCreateRequest();
        form.setActivo(true);
        model.addAttribute("conductorForm", form);
        // 👇 carpeta admin + archivo admin-conductor-form.html
        return "admin/admin-conductor-form";
    }

    @PostMapping
    public String crearConductor(
            @ModelAttribute("conductorForm") ConductorCreateRequest form,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            return "admin/admin-conductor-form";
        }

        try {
            conductorService.createConductor(form);
        } catch (IllegalArgumentException | IllegalStateException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "admin/admin-conductor-form";
        }

        return "redirect:/admin/conductores";
    }
}
