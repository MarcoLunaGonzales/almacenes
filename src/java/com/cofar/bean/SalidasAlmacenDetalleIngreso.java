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
public class SalidasAlmacenDetalleIngreso extends AbstractBean{
    SalidasAlmacen salidasAlmacen = new SalidasAlmacen();
    Materiales materiales = new Materiales();
    IngresosAlmacen ingresosAlmacen = new IngresosAlmacen();
    int etiqueta = 0;
    float costoSalida = 0;
    Date fechaVencimiento = new Date();
    float cantidad = 0;
    float costoSalidaActualizado = 0;
    Date fechaActualizacion = new Date();
    float costoSalidaActualizadoFinal = 0;
    IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstado = new IngresosAlmacenDetalleEstado();
    float cantidadDisponible = 0;
    float cantidadSacar = 0;
    float tara = 0;

    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    public float getCostoSalida() {
        return costoSalida;
    }

    public void setCostoSalida(float costoSalida) {
        this.costoSalida = costoSalida;
    }

    public float getCostoSalidaActualizado() {
        return costoSalidaActualizado;
    }

    public void setCostoSalidaActualizado(float costoSalidaActualizado) {
        this.costoSalidaActualizado = costoSalidaActualizado;
    }

    public float getCostoSalidaActualizadoFinal() {
        return costoSalidaActualizadoFinal;
    }

    public void setCostoSalidaActualizadoFinal(float costoSalidaActualizadoFinal) {
        this.costoSalidaActualizadoFinal = costoSalidaActualizadoFinal;
    }

    public int getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(int etiqueta) {
        this.etiqueta = etiqueta;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
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

    public SalidasAlmacen getSalidasAlmacen() {
        return salidasAlmacen;
    }

    public void setSalidasAlmacen(SalidasAlmacen salidasAlmacen) {
        this.salidasAlmacen = salidasAlmacen;
    }

    public IngresosAlmacenDetalleEstado getIngresosAlmacenDetalleEstado() {
        return ingresosAlmacenDetalleEstado;
    }

    public void setIngresosAlmacenDetalleEstado(IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstado) {
        this.ingresosAlmacenDetalleEstado = ingresosAlmacenDetalleEstado;
    }

    public float getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(float cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }

    public float getCantidadSacar() {
        return cantidadSacar;
    }

    public void setCantidadSacar(float cantidadSacar) {
        this.cantidadSacar = cantidadSacar;
    }

    public float getTara() {
        return tara;
    }

    public void setTara(float tara) {
        this.tara = tara;
    }
    private float cantidadRestanteValorado;
    private float valoracionCCPorcentual;

    public float getCantidadRestanteValorado() {
        return cantidadRestanteValorado;
    }

    public void setCantidadRestanteValorado(float cantidadRestanteValorado) {
        this.cantidadRestanteValorado = cantidadRestanteValorado;
    }

    public float getValoracionCCPorcentual() {
        return valoracionCCPorcentual;
    }

    public void setValoracionCCPorcentual(float valoracionCCPorcentual) {
        this.valoracionCCPorcentual = valoracionCCPorcentual;
    }
    
    private double cantidadProgProd;

    public double getCantidadProgProd() {
        return cantidadProgProd;
    }

    public void setCantidadProgProd(double cantidadProgProd) {
        this.cantidadProgProd = cantidadProgProd;
    }

}
