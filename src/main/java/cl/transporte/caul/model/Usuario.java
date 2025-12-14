package cl.transporte.caul.model;

import jakarta.persistence.*;
import java.util.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

@SuppressWarnings("unused")
@Entity
@Table(name = "usuario") 
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    @Column(name = "nombre_usuario", unique = true, nullable = false)
    private String nombreUsuario;

    
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "nombre_completo")
    private String nombreCompleto;

    private String email;
    private String rut;
    
    @Column(name = "id_cliente")
    private Long clienteId;

    @Column(name = "rol", nullable = false)
    private String rol;   // ADMIN, OPERADOR, CONDUCTOR, CLIENTE_CORP
    
    private Boolean activo;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;


    public Long getId(){return this.id; }
    public void setId(Long id) {this.id=id;}

    public String getNombreUsuario(){return this.nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) {this.nombreUsuario=nombreUsuario;}
        
    public String getPasswordHash(){return this.passwordHash; }
    public void setPasswordHash(String passwordHash) {this.passwordHash=passwordHash;}
    
    public String getNombreCompleto(){return this.nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) {this.nombreCompleto=nombreCompleto;}

    public String getEmail(){return this.email; }
    public void setEmail(String email) {this.email=email;}
    
    public String getRut(){return this.rut; }
    public void setRut(String rut) {this.rut=rut;}
    
    public Long getClienteId(){return this.clienteId; }
    public void setClienteId(Long clienteId) {this.clienteId=clienteId;}
    
        
    public String getRol(){return this.rol; }
    public void setRol(String rol) {this.rol=rol;}

    public Boolean getActivo(){return this.activo; }
    public void setActivo(Boolean activo) {this.activo=activo;}
    
    public Date getCreatedAt(){return this.createdAt; }
    public void setCreatedAt(Date localDateTime) {this.createdAt=localDateTime;}
    
    public Date getUpdatedAt(){return this.updatedAt; }
    public void setUpdatedAt(Date localDateTime) {this.updatedAt=localDateTime;}
    
    
}
