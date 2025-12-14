package cl.transporte.caul.dto;

public class ServicioHistoricoResumenDTO {
    private String periodo;     // "2025-W49" o "2025-12"
    private int totalServicios;
    private int kmTotales;      // suma de (kmFin-kmInicio) cuando exista
    private int totalFinalizados;
    private int totalProgramados;
    private int totalEnCurso;

    public ServicioHistoricoResumenDTO() {}

    public ServicioHistoricoResumenDTO(String periodo, int totalServicios, int kmTotales,
                                       int totalFinalizados, int totalProgramados, int totalEnCurso) {
        this.periodo = periodo;
        this.totalServicios = totalServicios;
        this.kmTotales = kmTotales;
        this.totalFinalizados = totalFinalizados;
        this.totalProgramados = totalProgramados;
        this.totalEnCurso = totalEnCurso;
    }

    public String getPeriodo() { return periodo; }
    public void setPeriodo(String periodo) { this.periodo = periodo; }

    public int getTotalServicios() { return totalServicios; }
    public void setTotalServicios(int totalServicios) { this.totalServicios = totalServicios; }

    public int getKmTotales() { return kmTotales; }
    public void setKmTotales(int kmTotales) { this.kmTotales = kmTotales; }

    public int getTotalFinalizados() { return totalFinalizados; }
    public void setTotalFinalizados(int totalFinalizados) { this.totalFinalizados = totalFinalizados; }

    public int getTotalProgramados() { return totalProgramados; }
    public void setTotalProgramados(int totalProgramados) { this.totalProgramados = totalProgramados; }

    public int getTotalEnCurso() { return totalEnCurso; }
    public void setTotalEnCurso(int totalEnCurso) { this.totalEnCurso = totalEnCurso; }
}
