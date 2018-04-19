/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.bean;

/**
 *
 * @author hvaldivia
 */
public class SolicitudesSalidaDetalle extends AbstractBean{
    SolicitudesSalida solicitudesSalida = new SolicitudesSalida();
    Materiales materiales = new Materiales();
    UnidadesMedida unidadesMedida = new UnidadesMedida();
    float cantidad = 0;
    float cantidadEntregada = 0;
    double disponible = 0;
    double cantidadMaximaSolicitud = 0;
    
    //variables de apoyo
    private boolean adicionarPorcentaje = false;
    private float porcentajeAdicionado = 0;
    
    
    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    public float getCantidadEntregada() {
        return cantidadEntregada;
    }

    public void setCantidadEntregada(float cantidadEntregada) {
        this.cantidadEntregada = cantidadEntregada;
    }

    public SolicitudesSalida getSolicitudesSalida() {
        return solicitudesSalida;
    }

    public void setSolicitudesSalida(SolicitudesSalida solicitudesSalida) {
        this.solicitudesSalida = solicitudesSalida;
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

    public double getDisponible() {
        return disponible;
    }

    public void setDisponible(double disponible) {
        this.disponible = disponible;
    }

    public double getCantidadMaximaSolicitud() {
        return cantidadMaximaSolicitud;
    }

    public void setCantidadMaximaSolicitud(double cantidadMaximaSolicitud) {
        this.cantidadMaximaSolicitud = cantidadMaximaSolicitud;
    }

    public boolean isAdicionarPorcentaje() {
        return adicionarPorcentaje;
    }

    public void setAdicionarPorcentaje(boolean adicionarPorcentaje) {
        this.adicionarPorcentaje = adicionarPorcentaje;
    }

    public float getPorcentajeAdicionado() {
        return porcentajeAdicionado;
    }

    public void setPorcentajeAdicionado(float porcentajeAdicionado) {
        this.porcentajeAdicionado = porcentajeAdicionado;
    }
    

}
