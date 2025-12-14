package cl.transporte.caul.controller;

import cl.transporte.caul.dto.ServicioResponse;
import cl.transporte.caul.service.ServicioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/web")
public class DashboardController {

    private final ServicioService servicioService;

    public DashboardController(ServicioService servicioService) {
        this.servicioService = servicioService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        List<ServicioResponse> serviciosHoy = servicioService.listServiciosHoy();

        model.addAttribute("hoy", LocalDate.now());
        model.addAttribute("servicios", serviciosHoy);

        return "dashboard"; // templates/dashboard.html
    }
}
