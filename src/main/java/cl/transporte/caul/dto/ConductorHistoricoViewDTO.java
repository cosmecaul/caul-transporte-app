package cl.transporte.caul.dto;

import java.util.ArrayList;
import java.util.List;

public class ConductorHistoricoViewDTO {
    private List<ServicioCardDTO> servicios = new ArrayList<>();
    private List<ResumenPeriodoDTO> resumenSemanal = new ArrayList<>();
    private List<ResumenPeriodoDTO> resumenMensual = new ArrayList<>();

    public List<ServicioCardDTO> getServicios() { return servicios; }
    public void setServicios(List<ServicioCardDTO> items) { this.servicios = items; }

    public List<ResumenPeriodoDTO> getResumenSemanal() { return resumenSemanal; }
    public void setResumenSemanal(List<ResumenPeriodoDTO> resumenSemanal) { this.resumenSemanal = resumenSemanal; }

    public List<ResumenPeriodoDTO> getResumenMensual() { return resumenMensual; }
    public void setResumenMensual(List<ResumenPeriodoDTO> resumenMensual) { this.resumenMensual = resumenMensual; }
    
    
    
}
