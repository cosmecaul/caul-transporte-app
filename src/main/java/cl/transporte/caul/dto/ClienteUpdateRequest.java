package cl.transporte.caul.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class ClienteUpdateRequest {

    @Size(max = 200)
    private String razonSocial;

    private String nombreCliente;
    
    @Size(max = 20)
    private String rut;

    @Size(max = 200)
    private String giro;

    @Size(max = 200)
    private String direccion;

    @Size(max = 100)
    private String comuna;

    @Size(max = 100)
    private String ciudad;

    @Size(max = 150)
    private String contactoNombre;

    @Email
    @Size(max = 150)
    private String contactoEmail;

    @Size(max = 50)
    private String contactoFono;

    // Permite activar / desactivar cliente
    private Boolean activo;

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getGiro() {
        return giro;
    }

    public void setGiro(String giro) {
        this.giro = giro;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getComuna() {
        return comuna;
    }

    public void setComuna(String comuna) {
        this.comuna = comuna;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getContactoNombre() {
        return contactoNombre;
    }

    public void setContactoNombre(String contactoNombre) {
        this.contactoNombre = contactoNombre;
    }

    public String getContactoEmail() {
        return contactoEmail;
    }

    public void setContactoEmail(String contactoEmail) {
        this.contactoEmail = contactoEmail;
    }

    public String getContactoFono() {
        return contactoFono;
    }

    public void setContactoFono(String contactoFono) {
        this.contactoFono = contactoFono;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

	public String getNombreCliente() {
		return nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}
}
