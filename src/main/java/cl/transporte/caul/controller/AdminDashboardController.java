package cl.transporte.caul.controller;

import cl.transporte.caul.dto.DashboardStatsDTO;
import cl.transporte.caul.dto.ServicioResumenDTO;
import cl.transporte.caul.service.DashboardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class AdminDashboardController {

    private final DashboardService dashboardService;

    public AdminDashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/admin/dashboard")
    public String verDashboard(
            @RequestParam(name = "rango", defaultValue = "HOY") String rango,
            Model model
    ) {

        DashboardStatsDTO stats = dashboardService.obtenerStatsDashboard();
        List<ServicioResumenDTO> servicios = dashboardService.obtenerServiciosPorRango(rango);

        LocalDate hoy = LocalDate.now();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        String rangoTexto;
	        switch (rango) {
	        case "FUTURO":
		       	 rangoTexto = "futuro";
		       	 break;
             case "MAÑANA":
            	 rangoTexto = "Mañana";
            	 break;
            case "AYER":
                rangoTexto = "Ayer (" + hoy.minusDays(1).format(fmt) + ")";
                break;
            case "ULTIMOS_7":
                rangoTexto = "Últimos 7 días";
                break;
            case "ULTIMOS_30":
                rangoTexto = "Últimos 30 días";
                break;
            case "HOY":
            default:
                rangoTexto = "Hoy (" + hoy.format(fmt) + ")";
                break;
        }

        // 👇 SIEMPRE cargamos estos atributos
        model.addAttribute("stats", stats); //admin-dashboard.html
        model.addAttribute("servicios", servicios);
        model.addAttribute("rango", rango);
        model.addAttribute("rangoTexto", rangoTexto);
        model.addAttribute("fechaOperacion", hoy.format(fmt));
        

        // 👇 Debe coincidir con el nombre del template: admin-dashboard.html
        return "admin-dashboard";
    }
}
