package cl.transporte.caul.controller;

import cl.transporte.caul.dto.VehiculoCreateRequest;
import cl.transporte.caul.dto.VehiculoUpdateRequest;
import cl.transporte.caul.dto.VehiculoResponse;
import cl.transporte.caul.service.VehiculoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/vehiculos")
public class AdminVehiculoController {

    private final VehiculoService vehiculoService;

    public AdminVehiculoController(VehiculoService vehiculoService) {
        this.vehiculoService = vehiculoService;
    }

    // LISTADO + FILTRO "solo activos"
    @GetMapping
    public String list(@RequestParam(required = false) Boolean soloActivos,
                       Model model) {

        List<VehiculoResponse> vehiculos =
                vehiculoService.listVehiculos(soloActivos);

        model.addAttribute("pageTitle", "Admin · Vehículos");
        model.addAttribute("menu", "vehiculos");
        model.addAttribute("vehiculos", vehiculos);
        model.addAttribute("soloActivos", soloActivos != null && soloActivos);

        return "admin/vehiculos";
    }

    // FORM NUEVO
    @GetMapping("/nuevo")
    public String nuevoForm(Model model) {
        model.addAttribute("pageTitle", "Nuevo vehículo");
        model.addAttribute("menu", "vehiculos");
        model.addAttribute("form", new VehiculoCreateRequest());
        return "admin/vehiculo-form";
    }

    // GUARDAR NUEVO
    @PostMapping("/nuevo")
    public String crear(@ModelAttribute("form") @Valid VehiculoCreateRequest form,
                        BindingResult bindingResult,
                        RedirectAttributes redirectAttributes,
                        Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", "Nuevo vehículo");
            model.addAttribute("menu", "vehiculos");
            return "admin/vehiculo-form";
        }

        vehiculoService.createVehiculo(form);
        redirectAttributes.addFlashAttribute("message", "Vehículo creado correctamente");
        return "redirect:/admin/vehiculos";
    }

    // FORM EDITAR
    @GetMapping("/{id}/editar")
    public String editarForm(@PathVariable Long id,
                             Model model) {

        VehiculoResponse v = vehiculoService.getVehiculoById(id);

        VehiculoUpdateRequest form = new VehiculoUpdateRequest();
        form.setPatente(v.getPatente());
        form.setMarcaModelo(v.getMarcaModelo());
        form.setTipoVehiculo(v.getTipoVehiculo());
        form.setAnio(v.getAnio());
        form.setCapacidad(v.getCapacidad());
        form.setDecreto80Vencimiento(v.getDecreto80Vencimiento());
        form.setGpsDeviceId(v.getGpsDeviceId());
        form.setGpsProveedor(v.getGpsProveedor());
        form.setGpsActivo(v.getGpsActivo());
        form.setObservaciones(v.getObservaciones());
        

        model.addAttribute("pageTitle", "Editar vehículo · " + v.getPatente());
        model.addAttribute("menu", "vehiculos");
        model.addAttribute("form", form);
        model.addAttribute("vehiculoId", id);

        return "admin/vehiculo-form";
    }

    // GUARDAR EDICIÓN
    @PostMapping("/{id}/editar")
    public String actualizar(@PathVariable Long id,
                             @ModelAttribute("form") @Valid VehiculoUpdateRequest form,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", "Editar vehículo");
            model.addAttribute("menu", "vehiculos");
            model.addAttribute("vehiculoId", id);
            return "admin/vehiculo-form";
        }

        vehiculoService.updateVehiculo(id, form);
        redirectAttributes.addFlashAttribute("message", "Vehículo actualizado correctamente");
        return "redirect:/admin/vehiculos";
    }
}
