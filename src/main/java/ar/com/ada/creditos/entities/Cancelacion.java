package ar.com.ada.creditos.entities;

import java.util.*;
import javax.persistence.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.NaturalId;
import java.math.*;

import ar.com.ada.creditos.excepciones.*;

@Entity
@Table(name = "cancelacion")
public class Cancelacion {

    @Id
    @Column(name = "cancelacion_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cancelacionId;

    
    @ManyToOne
    @JoinColumn(name = "prestamo_id", referencedColumnName = "prestamo_id")
    private Prestamo prestamo;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_cancelacion")
    private Date fechaCancelacion;

    private BigDecimal importe;

    private int cuota;

    public int getCancelacionId() {
        return cancelacionId;
    }
    public void setCancelacionId(int cancelacionId) {
        this.cancelacionId = cancelacionId;
    }

    public Prestamo getPrestamo(){
        return prestamo;
    }
    public void setPrestamo(Prestamo prestamo){
        this.prestamo = prestamo;
        this.prestamo.agregarCancelacion(this);
    }

    public Date getFechaCancelacion(){
        return fechaCancelacion;
    }
    public void setFechaCancelacion(Date fechaCancelacion){
        this.fechaCancelacion = fechaCancelacion;
    }

    public BigDecimal getImporte() {
        return importe;
    }
    public void setImporte(BigDecimal importe){
        this.importe = importe;
    }

    public int getCuota(){
        return cuota;
    }
    public void setCuota(int cuota){
        this.cuota = cuota;
    }
    






    
}
