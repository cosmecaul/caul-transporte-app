package cl.transporte.caul.controller;

import cl.transporte.caul.dto.ConductorHistoricoViewDTO;
import cl.transporte.caul.mapper.ConductorMapper;
import cl.transporte.caul.service.ConductorHistoricoService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/aplicacion")
public class ConductorAppController {

    private final ConductorHistoricoService historicoService;
    private final ConductorMapper conductorMapper;

    public ConductorAppController(ConductorHistoricoService historicoService,
                                  ConductorMapper conductorMapper) {
        this.historicoService = historicoService;
        this.conductorMapper = conductorMapper;
    }

    @GetMapping("/servicios/historico")
    public String historico(
            Authentication auth,
            @RequestParam(required = false) Long conductorId, // SOLO admin/operador
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta,
            Model model
    ) {
        Set<String> roles = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        boolean isConductor = roles.contains("ROLE_CONDUCTOR");
        boolean isAdminOperador = roles.contains("ROLE_ADMIN") || roles.contains("ROLE_OPERADOR");

        Long idConductorFinal;

        if (isConductor) {
            String rut = auth.getName();
            idConductorFinal = conductorMapper.findIdByRut(rut); // fuerza el conductor logueado
        } else if (isAdminOperador) {
            idConductorFinal = conductorId; // null => todos
        } else {
            return "redirect:/login";
        }

        // ✅ Convertimos LocalDate -> Date para calzar con tu service
        Date desdeDate = toDate(desde);
        Date hastaDate = toDate(hasta);

        ConductorHistoricoViewDTO dto =
                historicoService.getHistoricoConductor(idConductorFinal, desdeDate, hastaDate);

        model.addAttribute("data", dto);
        model.addAttribute("desde", desde);
        model.addAttribute("hasta", hasta);

        model.addAttribute("isConductor", isConductor);
        model.addAttribute("isAdminOperador", isAdminOperador);
        model.addAttribute("conductorId", conductorId);

        return "aplicacion/servicios_historico";
    }

    private Date toDate(LocalDate localDate) {
        if (localDate == null) return null;
        return Date.from(localDate.atStartOfDay(ZoneId.of("America/Santiago")).toInstant());
    }
}
