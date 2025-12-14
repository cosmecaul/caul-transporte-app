package cl.transporte.caul.service.impl;

import cl.transporte.caul.dto.ServicioHistoricoResumenDTO;
import cl.transporte.caul.model.Servicio;
import cl.transporte.caul.repository.ServicioRepository;
import cl.transporte.caul.service.ServicioHistoricoService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;
import java.time.ZoneId;
import java.util.Date;

@Service
public class ServicioHistoricoServiceImpl implements ServicioHistoricoService {

    private final ServicioRepository servicioRepository;

    public ServicioHistoricoServiceImpl(ServicioRepository servicioRepository) {
        this.servicioRepository = servicioRepository;
    }

    @Override
    public List<Servicio> listarServiciosConductor(
            Long conductorId,
            LocalDate desde,
            LocalDate hasta
    ) {

        LocalDate d;
        LocalDate h;

        if (desde == null || hasta == null) {
            // por defecto últimos 30 días
            h = LocalDate.now();
            d = h.minusDays(30);
        } else {
            d = desde;
            h = hasta;
        }

        // 🔁 Conversión LocalDate → Date
        Date desdeDate = java.sql.Date.valueOf(d);
        Date hastaDate = java.sql.Date.valueOf(h);

        return servicioRepository
                .findByConductorIdAndFechaBetweenOrderByFechaDescHoraInicioProgramadaDesc(
                        conductorId,
                        desdeDate,
                        hastaDate
                );
    }

    
    private static final ZoneId ZONE_CL = ZoneId.of("America/Santiago"); // o ZoneId.systemDefault()

    private LocalDate toLocalDate(Date d) {
        if (d == null) return null;
        return d.toInstant().atZone(ZONE_CL).toLocalDate();
    }
    
    @Override
    public List<ServicioHistoricoResumenDTO> resumenPorSemana(Long conductorId, LocalDate desde, LocalDate hasta) {
        List<Servicio> servicios = listarServiciosConductor(conductorId, desde, hasta);

        WeekFields wf = WeekFields.ISO;


        Map<String, List<Servicio>> agrupado = servicios.stream()
                .filter(s -> s.getFecha() != null)
                .collect(Collectors.groupingBy(s -> {
                    LocalDate f = toLocalDate(s.getFecha());
                    int week = f.get(wf.weekOfWeekBasedYear());
                    int year = f.get(wf.weekBasedYear());
                    return year + "-W" + String.format("%02d", week);
                }));

        return agrupado.entrySet().stream()
                .sorted(Map.Entry.comparingByKey()) // orden cronológico
                .map(e -> buildResumen(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ServicioHistoricoResumenDTO> resumenPorMes(Long conductorId, LocalDate desde, LocalDate hasta) {
        List<Servicio> servicios = listarServiciosConductor(conductorId, desde, hasta);

        Map<YearMonth, List<Servicio>> agrupado = servicios.stream()
                .filter(s -> s.getFecha() != null)
                .collect(Collectors.groupingBy(s -> YearMonth.from(toLocalDate(s.getFecha()))));

        return agrupado.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> buildResumen(e.getKey().toString(), e.getValue())) // "2025-12"
                .collect(Collectors.toList());
    }

    private ServicioHistoricoResumenDTO buildResumen(String periodo, List<Servicio> lista) {
        int total = lista.size();

        int kmTotales = lista.stream()
                .mapToInt(s -> {
                    Integer ini = s.getKmInicio();
                    Integer fin = s.getKmFin();
                    if (ini == null || fin == null) return 0;
                    return Math.max(0, fin - ini);
                })
                .sum();

        int fin = (int) lista.stream().filter(s -> "FINALIZADO".equalsIgnoreCase(s.getEstado())).count();
        int prog = (int) lista.stream().filter(s -> "PROGRAMADO".equalsIgnoreCase(s.getEstado())).count();
        int enc = (int) lista.stream().filter(s -> "EN_CURSO".equalsIgnoreCase(s.getEstado())).count();

        return new ServicioHistoricoResumenDTO(periodo, total, kmTotales, fin, prog, enc);
    }
}
