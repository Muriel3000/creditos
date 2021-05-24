package ar.com.ada.creditos.entities;

import java.util.*;
import java.lang.annotation.Inherited;
import java.math.*;
import javax.persistence.*;

@Entity
@Table(name = "prestamo")
public class Prestamo {
    
    /// completar

    @Id
    @Column(name = "prestamo_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int prestamoId;

    @Temporal(TemporalType.DATE)
    private Date fecha;

    public int getPrestamoId() {
        return prestamoId;
    }

    public void setPrestamoId(int prestamoId) {
        this.prestamoId = prestamoId;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public int getCuotas() {
        return cuotas;
    }

    public void setCuotas(int cuotas) {
        this.cuotas = cuotas;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    private BigDecimal importe;
    private int cuotas;

    @Column(name = "fecha_alta")
    private Date fechaAlta;

    private Cliente cliente;


}
