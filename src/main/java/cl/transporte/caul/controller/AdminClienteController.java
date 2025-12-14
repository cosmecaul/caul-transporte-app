package cl.transporte.caul.controller;

import cl.transporte.caul.dto.ClienteCreateRequest;
import cl.transporte.caul.dto.ClienteUpdateRequest;
import cl.transporte.caul.dto.ClienteResponse;
import cl.transporte.caul.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/clientes")
public class AdminClienteController {

    private final ClienteService clienteService;

    public AdminClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }
    
    @GetMapping
    public String list(@RequestParam(required = false) Boolean soloActivos,
                       Model model) {

        List<ClienteResponse> clientes =
                clienteService.listClientes(soloActivos);

        model.addAttribute("pageTitle", "Admin · Clientes");
        model.addAttribute("menu", "clientes");
        model.addAttribute("clientes", clientes);
        model.addAttribute("soloActivos", soloActivos != null && soloActivos);

        return "admin/clientes";
    }

 // FORM NUEVO
    @GetMapping("/nuevo")
    public String nuevoForm(Model model) {
        model.addAttribute("pageTitle", "Nuevo cliente");
        model.addAttribute("menu", "clientes");
        model.addAttribute("form", new ClienteCreateRequest());
        model.addAttribute("clienteId", null); // 👈 importante para el th:action
        return "admin/cliente-form";
    }

    // GUARDAR NUEVO
    @PostMapping("/nuevo")
    public String crear(@ModelAttribute("form") @Valid ClienteCreateRequest form,
                        BindingResult bindingResult,
                        RedirectAttributes redirectAttributes,
                        Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", "Nuevo cliente");
            model.addAttribute("menu", "clientes");
            return "admin/cliente-form";
        }

        clienteService.createCliente(form);
        redirectAttributes.addFlashAttribute("message", "Cliente creado correctamente");
        return "redirect:/admin/clientes";
    }

    // FORM EDITAR
    @GetMapping("/{id}/editar")
    public String editarForm(@PathVariable Long id,
                             Model model) {

        ClienteResponse c = clienteService.getClienteById(id);

        ClienteUpdateRequest form = new ClienteUpdateRequest();
        form.setRazonSocial(c.getRazonSocial());
        form.setRut(c.getRut());
        form.setGiro(c.getGiro());
        form.setDireccion(c.getDireccion());
        form.setComuna(c.getComuna());
        form.setCiudad(c.getCiudad());
        form.setContactoNombre(c.getContactoNombre());
        form.setContactoEmail(c.getContactoEmail());
        form.setContactoFono(c.getContactoFono());
        form.setActivo(c.getActivo());
     

        // 👇 aquí estaba el problema: antes usaba c.getNombre()
        model.addAttribute("pageTitle", "Editar cliente · " + c.getRazonSocial());
        model.addAttribute("menu", "clientes");
        model.addAttribute("form", form);
        model.addAttribute("clienteId", id);

        return "admin/cliente-form";
    }

    // GUARDAR EDICIÓN
    @PostMapping("/{id}/editar")
    public String actualizar(@PathVariable Long id,
                             @ModelAttribute("form") @Valid ClienteUpdateRequest form,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", "Editar cliente");
            model.addAttribute("menu", "clientes");
            model.addAttribute("clienteId", id);
            return "admin/cliente-form";
        }

        clienteService.updateCliente(id, form);
        redirectAttributes.addFlashAttribute("message", "Cliente actualizado correctamente");
        return "redirect:/admin/clientes";
    }
}
