/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author hvaldivia
 */
public class IngresosAlmacenDetalle extends AbstractBean {
    IngresosAlmacen ingresosAlmacen = new IngresosAlmacen();
    Materiales materiales = new Materiales();
    int codIngresoAlmacenDetalle = 0;
    Secciones secciones = new Secciones();
    int nroUnidadesEmpaque = 0;
    float cantTotalIngreso = 0;
    float cantTotalIngresoFisico = 0;
    float cantEquivalente = 0;
    UnidadesMedida unidadesMedida = new UnidadesMedida();
    float precioTotalMaterial = 0;
    float precioUnitarioMaterial = 0;
    float costoUnitario = 0;
    String observacion = "";
    float precioNeto = 0;
    float costoPromedio = 0;
    float costoUnitarioActualizado = 0;
    Date fechaActualizacion = new Date();
    float costoUnitarioActualizadoFinal = 0;
    List<IngresosAlmacenDetalleEstado> ingresosAlmacenDetalleEstadoList = new ArrayList<IngresosAlmacenDetalleEstado>();
    OrdenesCompraDetalle ordenesCompraDetalle = new OrdenesCompraDetalle(); //para la relacion entre detalle de almacen y ordenes de compra
    float valorEquivalencia = 0;
    UnidadesMedida unidadesMedidaEquivalencia = new UnidadesMedida();
    
    float cantEnviadaTraspaso = 0;
    UnidadesMedida unidadesMedidaTraspaso = new UnidadesMedida();
    
    //variable auxiliar
    private Boolean aplicaFechaVencimiento = false;
    

    public float getCantTotalIngreso() {
        return cantTotalIngreso;
    }

    public void setCantTotalIngreso(float cantTotalIngreso) {
        this.cantTotalIngreso = cantTotalIngreso;
    }

    public float getCantTotalIngresoFisico() {
        return cantTotalIngresoFisico;
    }

    public void setCantTotalIngresoFisico(float cantTotalIngresoFisico) {
        this.cantTotalIngresoFisico = cantTotalIngresoFisico;
    }

    public float getCostoPromedio() {
        return costoPromedio;
    }

    public void setCostoPromedio(float costoPromedio) {
        this.costoPromedio = costoPromedio;
    }

    public float getCostoUnitario() {
        return costoUnitario;
    }

    public void setCostoUnitario(float costoUnitario) {
        this.costoUnitario = costoUnitario;
    }

    public float getCostoUnitarioActualizado() {
        return costoUnitarioActualizado;
    }

    public void setCostoUnitarioActualizado(float costoUnitarioActualizado) {
        this.costoUnitarioActualizado = costoUnitarioActualizado;
    }

    public float getCostoUnitarioActualizadoFinal() {
        return costoUnitarioActualizadoFinal;
    }

    public void setCostoUnitarioActualizadoFinal(float costoUnitarioActualizadoFinal) {
        this.costoUnitarioActualizadoFinal = costoUnitarioActualizadoFinal;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public IngresosAlmacen getIngresosAlmacen() {
        return ingresosAlmacen;
    }

    public void setIngresosAlmacen(IngresosAlmacen ingresosAlmacen) {
        this.ingresosAlmacen = ingresosAlmacen;
    }

    public Materiales getMateriales() {
        return materiales;
    }

    public void setMateriales(Materiales materiales) {
        this.materiales = materiales;
    }

    public int getNroUnidadesEmpaque() {
        return nroUnidadesEmpaque;
    }

    public void setNroUnidadesEmpaque(int nroUnidadesEmpaque) {
        this.nroUnidadesEmpaque = nroUnidadesEmpaque;
    }   

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public float getPrecioNeto() {
        return precioNeto;
    }

    public void setPrecioNeto(float precioNeto) {
        this.precioNeto = precioNeto;
    }

    public float getPrecioTotalMaterial() {
        return precioTotalMaterial;
    }

    public void setPrecioTotalMaterial(float precioTotalMaterial) {
        this.precioTotalMaterial = precioTotalMaterial;
    }

    public float getPrecioUnitarioMaterial() {
        return precioUnitarioMaterial;
    }

    public void setPrecioUnitarioMaterial(float precioUnitarioMaterial) {
        this.precioUnitarioMaterial = precioUnitarioMaterial;
    }

    public Secciones getSecciones() {
        return secciones;
    }

    public void setSecciones(Secciones secciones) {
        this.secciones = secciones;
    }

    public UnidadesMedida getUnidadesMedida() {
        return unidadesMedida;
    }

    public void setUnidadesMedida(UnidadesMedida unidadesMedida) {
        this.unidadesMedida = unidadesMedida;
    }

    public List<IngresosAlmacenDetalleEstado> getIngresosAlmacenDetalleEstadoList() {
        return ingresosAlmacenDetalleEstadoList;
    }

    public void setIngresosAlmacenDetalleEstadoList(List<IngresosAlmacenDetalleEstado> ingresosAlmacenDetalleEstadoList) {
        this.ingresosAlmacenDetalleEstadoList = ingresosAlmacenDetalleEstadoList;
    }

    

    public OrdenesCompraDetalle getOrdenesCompraDetalle() {
        return ordenesCompraDetalle;
    }

    public void setOrdenesCompraDetalle(OrdenesCompraDetalle ordenesCompraDetalle) {
        this.ordenesCompraDetalle = ordenesCompraDetalle;
    }

    public float getCantEquivalente() {
        return cantEquivalente;
    }

    public void setCantEquivalente(float cantEquivalente) {
        this.cantEquivalente = cantEquivalente;
    }

    public float getValorEquivalencia() {
        return valorEquivalencia;
    }

    public void setValorEquivalencia(float valorEquivalencia) {
        this.valorEquivalencia = valorEquivalencia;
    }

    public UnidadesMedida getUnidadesMedidaEquivalencia() {
        return unidadesMedidaEquivalencia;
    }

    public void setUnidadesMedidaEquivalencia(UnidadesMedida unidadesMedidaEquivalencia) {
        this.unidadesMedidaEquivalencia = unidadesMedidaEquivalencia;
    }

    public int getCodIngresoAlmacenDetalle() {
        return codIngresoAlmacenDetalle;
    }

    public void setCodIngresoAlmacenDetalle(int codIngresoAlmacenDetalle) {
        this.codIngresoAlmacenDetalle = codIngresoAlmacenDetalle;
    }

    public float getCantEnviadaTraspaso() {
        return cantEnviadaTraspaso;
    }

    public void setCantEnviadaTraspaso(float cantEnviadaTraspaso) {
        this.cantEnviadaTraspaso = cantEnviadaTraspaso;
    }

    public UnidadesMedida getUnidadesMedidaTraspaso() {
        return unidadesMedidaTraspaso;
    }

    public void setUnidadesMedidaTraspaso(UnidadesMedida unidadesMedidaTraspaso) {
        this.unidadesMedidaTraspaso = unidadesMedidaTraspaso;
    }
    
    public int getIngresosAlmacenDetalleEstadoListSize()
    {
        return ingresosAlmacenDetalleEstadoList.size();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (this.ingresosAlmacen != null ? this.ingresosAlmacen.hashCode() : 0);
        hash = 67 * hash + (this.materiales != null ? this.materiales.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final IngresosAlmacenDetalle other = (IngresosAlmacenDetalle) obj;
        if (this.ingresosAlmacen != other.ingresosAlmacen && (this.ingresosAlmacen == null || !this.ingresosAlmacen.equals(other.ingresosAlmacen))) {
            return false;
        }
        if (this.materiales != other.materiales && (this.materiales == null || !this.materiales.equals(other.materiales))) {
            return false;
        }
        return true;
    }

    public Boolean getAplicaFechaVencimiento() {
        return aplicaFechaVencimiento;
    }

    public void setAplicaFechaVencimiento(Boolean aplicaFechaVencimiento) {
        this.aplicaFechaVencimiento = aplicaFechaVencimiento;
    }
    
    
    

}
