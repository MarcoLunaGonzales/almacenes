/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.bean;

import java.util.List;

/**
 *
 * @author hvaldivia
 */
public class Devoluciones extends AbstractBean{
    int codDevolucion = 0;
    int nroDevolucion = 0;
    int codFormularioDev = 0;
    Almacenes almacenes = new Almacenes();
    SalidasAlmacen salidasAlmacen = new SalidasAlmacen();
    Gestiones gestiones = new Gestiones();
    EstadosDevoluciones estadosDevoluciones = new EstadosDevoluciones();
    int estadoSistema = 0;
    String obsDevolucion = "";
    int codSalidaAlmacenAux = 0;
    private List<DevolucionesDetalle> devolucionesDetalleList;
    
    public Almacenes getAlmacenes() {
        return almacenes;
    }

    public void setAlmacenes(Almacenes almacenes) {
        this.almacenes = almacenes;
    }

    public int getCodDevolucion() {
        return codDevolucion;
    }

    public void setCodDevolucion(int codDevolucion) {
        this.codDevolucion = codDevolucion;
    }

    public int getCodFormularioDev() {
        return codFormularioDev;
    }

    public void setCodFormularioDev(int codFormularioDev) {
        this.codFormularioDev = codFormularioDev;
    }

    public int getCodSalidaAlmacenAux() {
        return codSalidaAlmacenAux;
    }

    public void setCodSalidaAlmacenAux(int codSalidaAlmacenAux) {
        this.codSalidaAlmacenAux = codSalidaAlmacenAux;
    }

    public int getEstadoSistema() {
        return estadoSistema;
    }

    public void setEstadoSistema(int estadoSistema) {
        this.estadoSistema = estadoSistema;
    }

    public EstadosDevoluciones getEstadosDevoluciones() {
        return estadosDevoluciones;
    }

    public void setEstadosDevoluciones(EstadosDevoluciones estadosDevoluciones) {
        this.estadosDevoluciones = estadosDevoluciones;
    }

    public Gestiones getGestiones() {
        return gestiones;
    }

    public void setGestiones(Gestiones gestiones) {
        this.gestiones = gestiones;
    }

    public int getNroDevolucion() {
        return nroDevolucion;
    }

    public void setNroDevolucion(int nroDevolucion) {
        this.nroDevolucion = nroDevolucion;
    }

    public String getObsDevolucion() {
        return obsDevolucion;
    }

    public void setObsDevolucion(String obsDevolucion) {
        this.obsDevolucion = obsDevolucion;
    }

    public SalidasAlmacen getSalidasAlmacen() {
        return salidasAlmacen;
    }

    public void setSalidasAlmacen(SalidasAlmacen salidasAlmacen) {
        this.salidasAlmacen = salidasAlmacen;
    }

    public List<DevolucionesDetalle> getDevolucionesDetalleList() {
        return devolucionesDetalleList;
    }

    public void setDevolucionesDetalleList(List<DevolucionesDetalle> devolucionesDetalleList) {
        this.devolucionesDetalleList = devolucionesDetalleList;
    }

    
    


}
