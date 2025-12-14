package cl.transporte.caul.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pasajero_servicio")
public class PasajeroServicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
        // FK a servicio

   
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_servicio", nullable = false)
    private Servicio servicio;

    @Column(name = "nombre_pasajero", length = 200)
    private String nombrePasajero;
    
    @Column(name = "telefono_contacto", length = 20)
    private String telefonoContacto;  
    
    @Column(name = "rut_pasajero", length = 20)
    private String rutPasajero;

    @Column(name = "punto_subida", length = 200)
    private String puntoSubida;

    @Column(name = "punto_bajada", length = 200)
    private String puntoBajada;

    @Column(name = "observaciones", length = 500)
    private String observaciones;

    // GETTERS & SETTERS

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Servicio getServicio() { return servicio; }
    public void setServicio(Servicio servicio) { this.servicio = servicio; }

    public String getNombrePasajero() { return nombrePasajero; }
    public void setNombrePasajero(String nombrePasajero) { this.nombrePasajero = nombrePasajero; }

    public String getTelefonoContacto() { return telefonoContacto; }
    public void setTelefonoContacto(String telefonoContacto) { this.telefonoContacto = telefonoContacto; }
    
    public String getRutPasajero() { return rutPasajero; }
    public void setRutPasajero(String rutPasajero) { this.rutPasajero = rutPasajero; }

    public String getPuntoSubida() { return puntoSubida; }
    public void setPuntoSubida(String puntoSubida) { this.puntoSubida = puntoSubida; }

    public String getPuntoBajada() { return puntoBajada; }
    public void setPuntoBajada(String puntoBajada) { this.puntoBajada = puntoBajada; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
}
