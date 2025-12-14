package cl.transporte.caul.controller;

import cl.transporte.caul.service.ServicioHistoricoService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/admin/conductores")
public class ConductorHistoricoController {

    private final ServicioHistoricoService historicoService;

    public ConductorHistoricoController(ServicioHistoricoService historicoService) {
        this.historicoService = historicoService;
    }

    @GetMapping("/{id}/historico")
    public String historicoConductor(
            @PathVariable("id") Long conductorId,
            @RequestParam(value = "desde", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam(value = "hasta", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta,
            Model model
    ) {
        model.addAttribute("conductorId", conductorId);
        model.addAttribute("desde", desde);
        model.addAttribute("hasta", hasta);

        model.addAttribute("servicios", historicoService.listarServiciosConductor(conductorId, desde, hasta));
        model.addAttribute("resumenSemanal", historicoService.resumenPorSemana(conductorId, desde, hasta));
        model.addAttribute("resumenMensual", historicoService.resumenPorMes(conductorId, desde, hasta));

        return "admin/conductores/historico";
    }
}
