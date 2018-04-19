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
public class Traspasos extends AbstractBean {
    SalidasAlmacen salidasAlmacen = new SalidasAlmacen();
    EstadosTraspaso estadosTraspaso = new EstadosTraspaso();
    Almacenes almacenOrigen = new Almacenes();
    Almacenes almacenDestino = new Almacenes();
    Date fechaTraspaso = new Date();
    IngresosAlmacen ingresosAlmacen = new IngresosAlmacen();
    //inicio ale traspaso}
    private TiposTraspasos tipoTraspaso= new TiposTraspasos();
    //final ale traspaso
    

    public Almacenes getAlmacenDestino() {
        return almacenDestino;
    }

    public void setAlmacenDestino(Almacenes almacenDestino) {
        this.almacenDestino = almacenDestino;
    }

    public Almacenes getAlmacenOrigen() {
        return almacenOrigen;
    }

    public void setAlmacenOrigen(Almacenes almacenOrigen) {
        this.almacenOrigen = almacenOrigen;
    }

    public EstadosTraspaso getEstadosTraspaso() {
        return estadosTraspaso;
    }

    public void setEstadosTraspaso(EstadosTraspaso estadosTraspaso) {
        this.estadosTraspaso = estadosTraspaso;
    }

    public Date getFechaTraspaso() {
        return fechaTraspaso;
    }

    public void setFechaTraspaso(Date fechaTraspaso) {
        this.fechaTraspaso = fechaTraspaso;
    }

    public IngresosAlmacen getIngresosAlmacen() {
        return ingresosAlmacen;
    }

    public void setIngresosAlmacen(IngresosAlmacen ingresosAlmacen) {
        this.ingresosAlmacen = ingresosAlmacen;
    }

    public SalidasAlmacen getSalidasAlmacen() {
        return salidasAlmacen;
    }

    public void setSalidasAlmacen(SalidasAlmacen salidasAlmacen) {
        this.salidasAlmacen = salidasAlmacen;
    }

    public TiposTraspasos getTipoTraspaso() {
        return tipoTraspaso;
    }

    public void setTipoTraspaso(TiposTraspasos tipoTraspaso) {
        this.tipoTraspaso = tipoTraspaso;
    }
    
}
