package cl.transporte.caul.dto;

public class ResumenPeriodoDTO {
    private String periodo; // ej: "2025-W50" o "2025-12"
    private long totalServicios;
    private long finalizados;
    private long programados;
    private long enCurso;

    public ResumenPeriodoDTO() {}

    public ResumenPeriodoDTO(String periodo, long totalServicios, long finalizados, long programados, long enCurso) {
        this.periodo = periodo;
        this.totalServicios = totalServicios;
        this.finalizados = finalizados;
        this.programados = programados;
        this.enCurso = enCurso;
    }

    public String getPeriodo() { return periodo; }
    public void setPeriodo(String periodo) { this.periodo = periodo; }

    public long getTotalServicios() { return totalServicios; }
    public void setTotalServicios(long totalServicios) { this.totalServicios = totalServicios; }

    public long getFinalizados() { return finalizados; }
    public void setFinalizados(long finalizados) { this.finalizados = finalizados; }

    public long getProgramados() { return programados; }
    public void setProgramados(long programados) { this.programados = programados; }

    public long getEnCurso() { return enCurso; }
    public void setEnCurso(long enCurso) { this.enCurso = enCurso; }
}
