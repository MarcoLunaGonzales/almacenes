/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.bean;

import java.util.Date;

/**
 *
 * @author rcondori
 */
public class IngresoTransaccionSolicitud extends AbstractBean {
    private int codIngresoTransaccionSolicitud = 0;
    private int codIngresoAlmacen = 0;
    private Personal personalSolicitante = new Personal();
    private Personal personalValidador = new Personal();
    private TipoTransaccionSolicitudAlmacen tipoTransaccionSolicitudAlmacen = new TipoTransaccionSolicitudAlmacen();
    private EstadoTransaccionSolicitud estadoTransaccionSolicitud = new EstadoTransaccionSolicitud();
    private Date fechaSolicitud = null;
    private Date FechaValidacion = null;
    private String observacionSolicitante= "";
    private String observacionValidador= "";
    
    //variables de apoyo
    private Date fechaSolicitudFin = null;
    private Date fechaValidacionFin = null;
    private int tipoRevision = 2;

    public int getCodIngresoTransaccionSolicitud() {
        return codIngresoTransaccionSolicitud;
    }

    public void setCodIngresoTransaccionSolicitud(int codIngresoTransaccionSolicitud) {
        this.codIngresoTransaccionSolicitud = codIngresoTransaccionSolicitud;
    }

    public int getCodIngresoAlmacen() {
        return codIngresoAlmacen;
    }

    public void setCodIngresoAlmacen(int codIngresoAlmacen) {
        this.codIngresoAlmacen = codIngresoAlmacen;
    }

    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public String getObservacionSolicitante() {
        return observacionSolicitante;
    }

    public void setObservacionSolicitante(String observacionSolicitante) {
        this.observacionSolicitante = observacionSolicitante;
    }

    public String getObservacionValidador() {
        return observacionValidador;
    }

    public void setObservacionValidador(String observacionValidador) {
        this.observacionValidador = observacionValidador;
    }

    public EstadoTransaccionSolicitud getEstadoTransaccionSolicitud() {
        return estadoTransaccionSolicitud;
    }

    public void setEstadoTransaccionSolicitud(EstadoTransaccionSolicitud estadoTransaccionSolicitud) {
        this.estadoTransaccionSolicitud = estadoTransaccionSolicitud;
    }

    public Personal getPersonalSolicitante() {
        return personalSolicitante;
    }

    public void setPersonalSolicitante(Personal personalSolicitante) {
        this.personalSolicitante = personalSolicitante;
    }

    public Personal getPersonalValidador() {
        return personalValidador;
    }

    public void setPersonalValidador(Personal personalValidador) {
        this.personalValidador = personalValidador;
    }

    public TipoTransaccionSolicitudAlmacen getTipoTransaccionSolicitudAlmacen() {
        return tipoTransaccionSolicitudAlmacen;
    }

    public void setTipoTransaccionSolicitudAlmacen(TipoTransaccionSolicitudAlmacen tipoTransaccionSolicitudAlmacen) {
        this.tipoTransaccionSolicitudAlmacen = tipoTransaccionSolicitudAlmacen;
    }

    public Date getFechaValidacion() {
        return FechaValidacion;
    }

    public void setFechaValidacion(Date FechaValidacion) {
        this.FechaValidacion = FechaValidacion;
    }

    public Date getFechaSolicitudFin() {
        return fechaSolicitudFin;
    }

    public void setFechaSolicitudFin(Date fechaSolicitudFin) {
        this.fechaSolicitudFin = fechaSolicitudFin;
    }

    public Date getFechaValidacionFin() {
        return fechaValidacionFin;
    }

    public void setFechaValidacionFin(Date fechaValidacionFin) {
        this.fechaValidacionFin = fechaValidacionFin;
    }

    public int getTipoRevision() {
        return tipoRevision;
    }

    public void setTipoRevision(int tipoRevision) {
        this.tipoRevision = tipoRevision;
    }


    
    

}
