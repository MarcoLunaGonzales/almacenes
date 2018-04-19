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
public class SolicitudesCompra {
    int codSolicitudCompra = 0;
    Gestiones gestiones = new Gestiones();
    tiposSolicitudCompra tiposSolicitudCompra = new tiposSolicitudCompra();
    Personal responsableCompras = new Personal();
    EstadosSolicitudCompra estadosSolicitudCompra = new EstadosSolicitudCompra();
    int estadoSistema = 0;
    Personal personal = new Personal();
    AreasEmpresa areasEmpresa = new AreasEmpresa();
    Date fechaSolicitudCompra = new Date();
    String obsSolicitudCompra = "";
    Date fechaEnvio = new Date();

    public AreasEmpresa getAreasEmpresa() {
        return areasEmpresa;
    }

    public void setAreasEmpresa(AreasEmpresa areasEmpresa) {
        this.areasEmpresa = areasEmpresa;
    }

    public int getCodSolicitudCompra() {
        return codSolicitudCompra;
    }

    public void setCodSolicitudCompra(int codSolicitudCompra) {
        this.codSolicitudCompra = codSolicitudCompra;
    }

    public int getEstadoSistema() {
        return estadoSistema;
    }

    public void setEstadoSistema(int estadoSistema) {
        this.estadoSistema = estadoSistema;
    }

    public EstadosSolicitudCompra getEstadosSolicitudCompra() {
        return estadosSolicitudCompra;
    }

    public void setEstadosSolicitudCompra(EstadosSolicitudCompra estadosSolicitudCompra) {
        this.estadosSolicitudCompra = estadosSolicitudCompra;
    }

    public Date getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(Date fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public Date getFechaSolicitudCompra() {
        return fechaSolicitudCompra;
    }

    public void setFechaSolicitudCompra(Date fechaSolicitudCompra) {
        this.fechaSolicitudCompra = fechaSolicitudCompra;
    }

    public Gestiones getGestiones() {
        return gestiones;
    }

    public void setGestiones(Gestiones gestiones) {
        this.gestiones = gestiones;
    }

    public String getObsSolicitudCompra() {
        return obsSolicitudCompra;
    }

    public void setObsSolicitudCompra(String obsSolicitudCompra) {
        this.obsSolicitudCompra = obsSolicitudCompra;
    }

    public Personal getPersonal() {
        return personal;
    }

    public void setPersonal(Personal personal) {
        this.personal = personal;
    }

    public Personal getResponsableCompras() {
        return responsableCompras;
    }

    public void setResponsableCompras(Personal responsableCompras) {
        this.responsableCompras = responsableCompras;
    }

    public tiposSolicitudCompra getTiposSolicitudCompra() {
        return tiposSolicitudCompra;
    }

    public void setTiposSolicitudCompra(tiposSolicitudCompra tiposSolicitudCompra) {
        this.tiposSolicitudCompra = tiposSolicitudCompra;
    }

}
