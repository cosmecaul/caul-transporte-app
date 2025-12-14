package cl.transporte.caul.dto;

public class DashboardStatsDTO {

	private long mañana;
	private long futuro;
    private long serviciosHoy;
    private long serviciosAyer;
    private long serviciosUltimos7;
    private long serviciosUltimos30;

    private long conductoresActivos;
    private long vehiculosDisponibles;

    private double puntualidadEstimadapct;

    public long getServiciosHoy() {
        return serviciosHoy;
    }

    public void setServiciosHoy(long serviciosHoy) {
        this.serviciosHoy = serviciosHoy;
    }

    public long getServiciosAyer() {
        return serviciosAyer;
    }

    public void setServiciosAyer(long serviciosAyer) {
        this.serviciosAyer = serviciosAyer;
    }

    public long getServiciosUltimos7() {
        return serviciosUltimos7;
    }

    public void setServiciosUltimos7(long serviciosUltimos7) {
        this.serviciosUltimos7 = serviciosUltimos7;
    }

    public long getServiciosUltimos30() {
        return serviciosUltimos30;
    }

    public void setServiciosUltimos30(long serviciosUltimos30) {
        this.serviciosUltimos30 = serviciosUltimos30;
    }

    public long getConductoresActivos() {
        return conductoresActivos;
    }

    public void setConductoresActivos(long conductoresActivos) {
        this.conductoresActivos = conductoresActivos;
    }

    public long getVehiculosDisponibles() {
        return vehiculosDisponibles;
    }

    public void setVehiculosDisponibles(long vehiculosDisponibles) {
        this.vehiculosDisponibles = vehiculosDisponibles;
    }

    public double getPuntualidadEstimadapct() {
        return puntualidadEstimadapct;
    }

    public void setPuntualidadEstimadapct(double puntualidadEstimadapct) {
        this.puntualidadEstimadapct = puntualidadEstimadapct;
    }

	public long getMañana() {
		return mañana;
	}

	public void setMañana(long mañana) {
		this.mañana = mañana;
	}

	public long getFuturo() {
		return futuro;
	}

	public void setFuturo(long futuro) {
		this.futuro = futuro;
	}
}
