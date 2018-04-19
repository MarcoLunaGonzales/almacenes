/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.bean;

/**
 *
 * @author wchoquehuanca
 */
public class SolicitudDevoluciones extends AbstractBean {
    private String codSolicitudDevolucion="";
    private SalidasAlmacen salidaAlmacen= new SalidasAlmacen();
    private EstadosSolicitudDevolucion estadoSolicitudDevolucion= new EstadosSolicitudDevolucion();
    private java.util.Date fechaSolicitud= new java.util.Date();
    private Personal personalSolicitante= new Personal();
    private String Observacion="";
    private Almacenes almacenDestino=new Almacenes();
    public SolicitudDevoluciones()
    {
    }

    /**
     * @return the codSolicitudDevolucion
     */
    public String getCodSolicitudDevolucion() {
        return codSolicitudDevolucion;
    }

    /**
     * @param codSolicitudDevolucion the codSolicitudDevolucion to set
     */
    public void setCodSolicitudDevolucion(String codSolicitudDevolucion) {
        this.codSolicitudDevolucion = codSolicitudDevolucion;
    }

   

    /**
     * @return the estadoSolicitudDevolucion
     */
    public EstadosSolicitudDevolucion getEstadoSolicitudDevolucion() {
        return estadoSolicitudDevolucion;
    }

    /**
     * @param estadoSolicitudDevolucion the estadoSolicitudDevolucion to set
     */
    public void setEstadoSolicitudDevolucion(EstadosSolicitudDevolucion estadoSolicitudDevolucion) {
        this.estadoSolicitudDevolucion = estadoSolicitudDevolucion;
    }

    /**
     * @return the fechaSolicitud
     */
    public java.util.Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    /**
     * @param fechaSolicitud the fechaSolicitud to set
     */
    public void setFechaSolicitud(java.util.Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    /**
     * @return the personalSolicitante
     */
    public Personal getPersonalSolicitante() {
        return personalSolicitante;
    }

    /**
     * @param personalSolicitante the personalSolicitante to set
     */
    public void setPersonalSolicitante(Personal personalSolicitante) {
        this.personalSolicitante = personalSolicitante;
    }

    /**
     * @return the Observacion
     */
    public String getObservacion() {
        return Observacion;
    }

    /**
     * @param Observacion the Observacion to set
     */
    public void setObservacion(String Observacion) {
        this.Observacion = Observacion;
    }

    /**
     * @return the salidaAlmacen
     */
    public SalidasAlmacen getSalidaAlmacen() {
        return salidaAlmacen;
    }

    /**
     * @param salidaAlmacen the salidaAlmacen to set
     */
    public void setSalidaAlmacen(SalidasAlmacen salidaAlmacen) {
        this.salidaAlmacen = salidaAlmacen;
    }

    public Almacenes getAlmacenDestino() {
        return almacenDestino;
    }

    public void setAlmacenDestino(Almacenes almacenDestino) {
        this.almacenDestino = almacenDestino;
    }

  
    



}
