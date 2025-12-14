package cl.transporte.caul.service.impl;

import cl.transporte.caul.dto.ConductorHistoricoViewDTO;
import cl.transporte.caul.dto.ResumenPeriodoDTO;
import cl.transporte.caul.dto.ServicioCardDTO;
import cl.transporte.caul.dto.ServicioHistoricoItemDTO;
import cl.transporte.caul.model.Servicio;
import cl.transporte.caul.repository.ServicioRepository;
import cl.transporte.caul.service.ConductorHistoricoService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.IsoFields;
import java.time.temporal.TemporalAccessor;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConductorHistoricoServiceImpl implements ConductorHistoricoService {

    private final ServicioRepository servicioRepository;

    public ConductorHistoricoServiceImpl(ServicioRepository servicioRepository) {
        this.servicioRepository = servicioRepository;
    }

    @Override
    public ConductorHistoricoViewDTO getHistoricoConductor(Long idConductor, Date desde, Date hasta) {

        // normaliza rango si viene invertido
        if (desde != null && hasta != null && desde.after(hasta)) {
            Date tmp = desde;
            desde = hasta;
            hasta = tmp;
        }
        List<Servicio> servicios;
        // 1) Consulta según rol (idConductor null => TODOS)
        if (idConductor == null) {
            // ADMIN / OPERADOR → todos los servicios en rango
            servicios = servicioRepository
                    .findByFechaBetweenOrderByFechaAscHoraInicioProgramadaAsc(desde, hasta);
        } else {
            // CONDUCTOR → solo sus servicios
            servicios = servicioRepository
                    .findByConductorIdAndFechaBetweenOrderByFechaAscHoraInicioProgramadaAsc(
                            idConductor, desde, hasta
                    );
        }
        // 2) Mapea detalle
        List<ServicioHistoricoItemDTO> items = servicios.stream()
                .map(s -> new ServicioHistoricoItemDTO(
                        s.getFecha(),
                        s.getHoraInicioProgramada(),
                        String.valueOf(s.getEstado()),
                        s.getOrigen(),
                        s.getDestino()
                ))
                .collect(Collectors.toList());

        // 3) Resumen semanal
        List<ResumenPeriodoDTO> semanal = servicios.stream()
                .collect(Collectors.groupingBy(s -> {
                    Date d = s.getFecha();
                    int week = ((TemporalAccessor) d).get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
                    int year = ((TemporalAccessor) d).get(IsoFields.WEEK_BASED_YEAR);
                    return year + "-W" + String.format("%02d", week);
                }, LinkedHashMap::new, Collectors.toList()))
                .entrySet().stream()
                .map(e -> resumen(e.getKey(), e.getValue()))
                .sorted(Comparator.comparing(ResumenPeriodoDTO::getPeriodo).reversed())
                .collect(Collectors.toList());

        // 4) Resumen mensual
        List<ServicioCardDTO> items2 = servicios.stream()
                .map(s -> {
                    ServicioCardDTO c = new ServicioCardDTO();
                    c.setFecha(s.getFecha());
                    c.setHora(s.getHoraInicioProgramada()); // ajusta el setter real
                    c.setEstado(String.valueOf(s.getEstado()));
                    c.setOrigen(s.getOrigen());
                    c.setDestino(s.getDestino());
                    return c;
                })
                .collect(Collectors.toList());
        // 5) DTO final
        ConductorHistoricoViewDTO dto = new ConductorHistoricoViewDTO();
        dto.setServicios(items2);
        dto.setResumenSemanal(semanal);
       // dto.setResumenMensual(mensual);
        return dto;
    }

    private ResumenPeriodoDTO resumen(String periodo, List<Servicio> list) {
        long total = list.size();
        long finalizados = list.stream()
                .filter(s -> "FINALIZADO".equals(String.valueOf(s.getEstado())))
                .count();
        long programados = list.stream()
                .filter(s -> "PROGRAMADO".equals(String.valueOf(s.getEstado())))
                .count();
        long enCurso = list.stream()
                .filter(s -> "EN_CURSO".equals(String.valueOf(s.getEstado())))
                .count();

        ResumenPeriodoDTO r = new ResumenPeriodoDTO();
        r.setPeriodo(periodo);
        r.setTotalServicios(total);
        r.setFinalizados(finalizados);
        r.setProgramados(programados);
        r.setEnCurso(enCurso);
        return r;
    }
}
