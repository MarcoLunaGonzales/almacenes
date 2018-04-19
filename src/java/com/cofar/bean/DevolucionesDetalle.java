/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.bean;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hvaldivia
 */
public class DevolucionesDetalle {
    Devoluciones devoluciones = new Devoluciones();
    Materiales materiales = new Materiales();
    float cantidadEntregada = 0;
    float cantidadDevuelta = 0;
    float cantidadDevueltaFallados = 0;
    float cantidadDevueltaFalladosProveedor = 0;
    UnidadesMedida unidadesMedida = new UnidadesMedida();
    List devolucionesDetalleEtiquetasList = new ArrayList();
    //inicio ale devoluciones
    float cantidadDevueltaRef=0;
    float cantidadDevueltaFalladosRef=0;
    Double costoSolicitudDevolucion = 0d;
    private Double costoSolicitudDevolucionFrv = 0d;
    private Double costoSolicitudDevolucionFrvProveedor = 0d;
    private Almacenes almacenDestinoFrv=new Almacenes();
    private Almacenes almacenDestinoFrvProveedor=new Almacenes();
    //final ale devoluciones
    SalidasAlmacen salidasAlmacen = new SalidasAlmacen();

    public float getCantidadEntregada() {
        return cantidadEntregada;
    }

    public void setCantidadEntregada(float cantidadEntregada) {
        this.cantidadEntregada = cantidadEntregada;
    }
    

    public float getCantidadDevuelta() {
        return cantidadDevuelta;
    }

    public void setCantidadDevuelta(float cantidadDevuelta) {
        this.cantidadDevuelta = cantidadDevuelta;
    }

    public Devoluciones getDevoluciones() {
        return devoluciones;
    }

    public void setDevoluciones(Devoluciones devoluciones) {
        this.devoluciones = devoluciones;
    }

    public Materiales getMateriales() {
        return materiales;
    }

    public void setMateriales(Materiales materiales) {
        this.materiales = materiales;
    }

    public UnidadesMedida getUnidadesMedida() {
        return unidadesMedida;
    }

    public void setUnidadesMedida(UnidadesMedida unidadesMedida) {
        this.unidadesMedida = unidadesMedida;
    }

    public List getDevolucionesDetalleEtiquetasList() {
        return devolucionesDetalleEtiquetasList;
    }

    public void setDevolucionesDetalleEtiquetasList(List devolucionesDetalleEtiquetasList) {
        this.devolucionesDetalleEtiquetasList = devolucionesDetalleEtiquetasList;
    }

    public float getCantidadDevueltaFallados() {
        return cantidadDevueltaFallados;
    }

    public void setCantidadDevueltaFallados(float cantidadDevueltaFallados) {
        this.cantidadDevueltaFallados = cantidadDevueltaFallados;
    }

    public float getCantidadDevueltaFalladosProveedor() {
        return cantidadDevueltaFalladosProveedor;
    }

    public void setCantidadDevueltaFalladosProveedor(float cantidadDevueltaFalladosProveedor) {
        this.cantidadDevueltaFalladosProveedor = cantidadDevueltaFalladosProveedor;
    }

    public float getCantidadDevueltaFalladosRef() {
        return cantidadDevueltaFalladosRef;
    }

    public void setCantidadDevueltaFalladosRef(float cantidadDevueltaFalladosRef) {
        this.cantidadDevueltaFalladosRef = cantidadDevueltaFalladosRef;
    }

    public float getCantidadDevueltaRef() {
        return cantidadDevueltaRef;
    }

    public void setCantidadDevueltaRef(float cantidadDevueltaRef) {
        this.cantidadDevueltaRef = cantidadDevueltaRef;
    }

    public SalidasAlmacen getSalidasAlmacen() {
        return salidasAlmacen;
    }

    public void setSalidasAlmacen(SalidasAlmacen salidasAlmacen) {
        this.salidasAlmacen = salidasAlmacen;
    }
    private boolean checked=false;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Double getCostoSolicitudDevolucion() {
        return costoSolicitudDevolucion;
    }

    public void setCostoSolicitudDevolucion(Double costoSolicitudDevolucion) {
        this.costoSolicitudDevolucion = costoSolicitudDevolucion;
    }

    public Almacenes getAlmacenDestinoFrv() {
        return almacenDestinoFrv;
    }

    public void setAlmacenDestinoFrv(Almacenes almacenDestinoFrv) {
        this.almacenDestinoFrv = almacenDestinoFrv;
    }

    public Almacenes getAlmacenDestinoFrvProveedor() {
        return almacenDestinoFrvProveedor;
    }

    public void setAlmacenDestinoFrvProveedor(Almacenes almacenDestinoFrvProveedor) {
        this.almacenDestinoFrvProveedor = almacenDestinoFrvProveedor;
    }

    public Double getCostoSolicitudDevolucionFrv() {
        return costoSolicitudDevolucionFrv;
    }

    public void setCostoSolicitudDevolucionFrv(Double costoSolicitudDevolucionFrv) {
        this.costoSolicitudDevolucionFrv = costoSolicitudDevolucionFrv;
    }

    public Double getCostoSolicitudDevolucionFrvProveedor() {
        return costoSolicitudDevolucionFrvProveedor;
    }

    public void setCostoSolicitudDevolucionFrvProveedor(Double costoSolicitudDevolucionFrvProveedor) {
        this.costoSolicitudDevolucionFrvProveedor = costoSolicitudDevolucionFrvProveedor;
    }

    
    
    
}
