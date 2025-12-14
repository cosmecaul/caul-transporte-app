package cl.transporte.caul.controller;

import cl.transporte.caul.dto.UsuarioCreateRequest;
import cl.transporte.caul.dto.UsuarioResponse;
import cl.transporte.caul.dto.UsuarioUpdateRequest;
import cl.transporte.caul.dto.ClienteResponse;
import cl.transporte.caul.service.UsuarioService;
import cl.transporte.caul.service.ClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/usuarios")
public class AdminUsuarioController {

    private final UsuarioService usuarioService;
    private final ClienteService clienteService;

    public AdminUsuarioController(UsuarioService usuarioService,
                                  ClienteService clienteService) {
        this.usuarioService = usuarioService;
        this.clienteService = clienteService;
    }

    // LISTA
    @GetMapping
    public String listar(Model model) {

        List<UsuarioResponse> usuarios = usuarioService.listUsuarios();
        List<ClienteResponse> clientes = clienteService.listClientes(true);

        model.addAttribute("usuarios", usuarios);
        model.addAttribute("clientes", clientes);
        model.addAttribute("pageTitle", "Usuarios · Panel Administración");

        return "admin/usuarios-lista";
    }

    // FORM NUEVO (sigue igual, usa usuarios-nuevo.html)
    @GetMapping("/nuevo")
    public String nuevoForm(Model model) {
        UsuarioCreateRequest form = new UsuarioCreateRequest();
        form.setActivo(true); // por defecto activo

        List<ClienteResponse> clientes = clienteService.listClientes(true);

        model.addAttribute("form", form);
        model.addAttribute("clientes", clientes);
        model.addAttribute("pageTitle", "Nuevo usuario · Panel Administración");

        return "admin/usuarios-nuevo";
    }

    // GUARDAR NUEVO
    @PostMapping("/nuevo")
    public String crear(@ModelAttribute("form") UsuarioCreateRequest form,
                        RedirectAttributes redirectAttributes) {

        try {
            if (form.getRol() == null || form.getRol().isBlank()) {
                form.setRol("ADMIN"); // por si acaso
            }
            if (form.getActivo() == null) {
                form.setActivo(true);
            }

            usuarioService.createUsuario(form);
            redirectAttributes.addFlashAttribute("messageSuccess", "Usuario creado correctamente.");

        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("messageError", "Error al crear usuario: " + ex.getMessage());
            return "redirect:/admin/usuarios/nuevo";
        }

        return "redirect:/admin/usuarios";
    }

    // ========= NUEVO: FORM EDITAR =========
    @GetMapping("/{id}/editar")
    public String editarForm(@PathVariable Long id, Model model,
                             RedirectAttributes redirectAttributes) {
        try {
            UsuarioResponse usuario = usuarioService.getUsuarioById(id);

            UsuarioUpdateRequest form = new UsuarioUpdateRequest();
            form.setNombreCompleto(usuario.getNombreCompleto());
            form.setEmail(usuario.getEmail());
            form.setRut(usuario.getRut());
            form.setRol(usuario.getRol());
            form.setIdCliente(usuario.getIdCliente());
            form.setActivo(usuario.getActivo());
            // passwordNuevo se deja null

            List<ClienteResponse> clientes = clienteService.listClientes(true);

            model.addAttribute("usuarioId", id);
            model.addAttribute("usuarioUsername", usuario.getNombreUsuario());
            model.addAttribute("form", form);
            model.addAttribute("clientes", clientes);
            model.addAttribute("pageTitle", "Editar usuario · Panel Administración");

            return "admin/usuarios-form";

        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("messageError",
                    "No se pudo cargar el usuario: " + ex.getMessage());
            return "redirect:/admin/usuarios";
        }
    }

    // ========= NUEVO: GUARDAR EDICIÓN =========
    @PostMapping("/{id}/editar")
    public String actualizar(@PathVariable Long id,
                             @ModelAttribute("form") UsuarioUpdateRequest form,
                             RedirectAttributes redirectAttributes) {
        try {
            usuarioService.updateUsuario(id, form);
            redirectAttributes.addFlashAttribute("messageSuccess", "Usuario actualizado correctamente.");
            return "redirect:/admin/usuarios";
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("messageError", "Error al actualizar usuario: " + ex.getMessage());
            return "redirect:/admin/usuarios/" + id + "/editar";
        }
    }
    
    @PostMapping("/{id}/eliminar")
    public String deleteUsuario(@PathVariable Long id,
                                RedirectAttributes redirectAttributes) {
        try {
            usuarioService.deleteUsuario(id);
            redirectAttributes.addFlashAttribute("messageSuccess", "Usuario eliminado correctamente.");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("messageError",
                    "No se pudo eliminar el usuario: " + ex.getMessage());
        }
        return "redirect:/admin/usuarios";
    }
    
}
