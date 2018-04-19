/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.bean;

import java.util.Date;

/**
 *
 * @author hvaldivia
 */
public class DevolucionesDetalleEtiquetas {
    Devoluciones devoluciones = new Devoluciones();
    IngresosAlmacen ingresosAlmacen = new IngresosAlmacen();
    Materiales materiales = new Materiales();
    int etiqueta = 0;
    float cantidadSalida = 0;
    float cantidadDevuelta = 0;
    float cantidadFallados = 0;
    Date fechaVencimiento = new Date();
    float cantidadesDevueltas = 0;
    float cantidadDevueltaB = 0;
    float cantidadFalladosB = 0;
    float cantidadFalladosProveedor=0;

    private String loteMaterialProveedor="";
    private Double costoSalidaAplActualizado=0d;
    private TipoFrvProceso tipoFrvProceso=new TipoFrvProceso();
    private Double costoFrvConMp=0d;
    private Double costoFrvConMpEp=0d;
    private Double costoFrvRecubrimiento = 0d;
            
    public float getCantidadSalida() {
        return cantidadSalida;
    }

    public void setCantidadSalida(float cantidadSalida) {
        this.cantidadSalida = cantidadSalida;
    }


    public float getCantidadDevuelta() {
        return cantidadDevuelta;
    }

    public void setCantidadDevuelta(float cantidadDevuelta) {
        this.cantidadDevuelta = cantidadDevuelta;
    }

    public float getCantidadFallados() {
        return cantidadFallados;
    }

    public void setCantidadFallados(float cantidadFallados) {
        this.cantidadFallados = cantidadFallados;
    }

    public Devoluciones getDevoluciones() {
        return devoluciones;
    }

    public void setDevoluciones(Devoluciones devoluciones) {
        this.devoluciones = devoluciones;
    }

    public int getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(int etiqueta) {
        this.etiqueta = etiqueta;
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

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public float getCantidadesDevueltas() {
        return cantidadesDevueltas;
    }

    public void setCantidadesDevueltas(float cantidadesDevueltas) {
        this.cantidadesDevueltas = cantidadesDevueltas;
    }

    public float getCantidadDevueltaB() {
        return cantidadDevueltaB;
    }

    public void setCantidadDevueltaB(float cantidadDevueltaB) {
        this.cantidadDevueltaB = cantidadDevueltaB;
    }

    public float getCantidadFalladosB() {
        return cantidadFalladosB;
    }

    public void setCantidadFalladosB(float cantidadFalladosB) {
        this.cantidadFalladosB = cantidadFalladosB;
    }

    public float getCantidadFalladosProveedor() {
        return cantidadFalladosProveedor;
    }

    public void setCantidadFalladosProveedor(float cantidadFalladosProveedor) {
        this.cantidadFalladosProveedor = cantidadFalladosProveedor;
    }

    public String getLoteMaterialProveedor() {
        return loteMaterialProveedor;
    }

    public void setLoteMaterialProveedor(String loteMaterialProveedor) {
        this.loteMaterialProveedor = loteMaterialProveedor;
    }
    
    private float cantidad_solicitud_devolucion;

    public float getCantidad_solicitud_devolucion() {
        return cantidad_solicitud_devolucion;
    }

    public void setCantidad_solicitud_devolucion(float cantidad_solicitud_devolucion) {
        this.cantidad_solicitud_devolucion = cantidad_solicitud_devolucion;
    }

    public Double getCostoSalidaAplActualizado() {
        return costoSalidaAplActualizado;
    }

    public void setCostoSalidaAplActualizado(Double costoSalidaAplActualizado) {
        this.costoSalidaAplActualizado = costoSalidaAplActualizado;
    }

    public TipoFrvProceso getTipoFrvProceso() {
        return tipoFrvProceso;
    }

    public void setTipoFrvProceso(TipoFrvProceso tipoFrvProceso) {
        this.tipoFrvProceso = tipoFrvProceso;
    }

    public Double getCostoFrvConMp() {
        return costoFrvConMp;
    }

    public void setCostoFrvConMp(Double costoFrvConMp) {
        this.costoFrvConMp = costoFrvConMp;
    }

    public Double getCostoFrvConMpEp() {
        return costoFrvConMpEp;
    }

    public void setCostoFrvConMpEp(Double costoFrvConMpEp) {
        this.costoFrvConMpEp = costoFrvConMpEp;
    }

    public Double getCostoFrvRecubrimiento() {
        return costoFrvRecubrimiento;
    }

    public void setCostoFrvRecubrimiento(Double costoFrvRecubrimiento) {
        this.costoFrvRecubrimiento = costoFrvRecubrimiento;
    }
    
    
    
}
