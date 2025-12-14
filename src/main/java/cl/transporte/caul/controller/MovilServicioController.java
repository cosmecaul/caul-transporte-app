package cl.transporte.caul.controller;

import cl.transporte.caul.dto.ServicioMovilHoyResponse;
import cl.transporte.caul.service.ServicioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movil/servicios")
@CrossOrigin(origins = "*")
public class MovilServicioController {

    private final ServicioService servicioService;

    public MovilServicioController(ServicioService servicioService) {
        this.servicioService = servicioService;
    }

    // GET /api/movil/servicios/hoy?conductorId=5
    @GetMapping("/hoy")
    public ResponseEntity<List<ServicioMovilHoyResponse>> getServiciosHoy(
            @RequestParam("conductorId") Long conductorId) {

        List<ServicioMovilHoyResponse> lista =
                servicioService.listServiciosHoyPorConductor(conductorId);

        return ResponseEntity.ok(lista);
    }
    
    
    
    
}
