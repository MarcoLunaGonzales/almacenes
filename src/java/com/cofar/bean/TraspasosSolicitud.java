/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.bean;

import java.util.Date;

/**
 *
 * @author jaime chura
 */
public class TraspasosSolicitud {
    private EstadosTraspaso estadosTraspaso = new EstadosTraspaso();
    private Almacenes almacenOrigen = new Almacenes();
    private Almacenes almacenDestino = new Almacenes();
    private Date fechaTraspaso = new Date();
    private int cod_salida_almacen;
    private int cod_form_salida;

    public int getCod_salida_almacen() {
        return cod_salida_almacen;
    }

    public void setCod_salida_almacen(int cod_salida_almacen) {
        this.cod_salida_almacen = cod_salida_almacen;
    }

    public int getCod_form_salida() {
        return cod_form_salida;
    }

    public void setCod_form_salida(int cod_form_salida) {
        this.cod_form_salida = cod_form_salida;
    }
    
    
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


    public TiposTraspasos getTipoTraspaso() {
        return tipoTraspaso;
    }

    public void setTipoTraspaso(TiposTraspasos tipoTraspaso) {
        this.tipoTraspaso = tipoTraspaso;
    }
    
}
