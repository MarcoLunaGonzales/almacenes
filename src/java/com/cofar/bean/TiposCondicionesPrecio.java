/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.bean;

/**
 *
 * @author hvaldivia
 */
public class TiposCondicionesPrecio {
    int codCondicionPrecio = 0;
    String nombreCondicionPrecio = "";
    TiposTransporte tiposTransporte = new TiposTransporte();
    String obsCondicionPrecio = "";
    EstadoReferencial estadoReferencial = new EstadoReferencial();

    public int getCodCondicionPrecio() {
        return codCondicionPrecio;
    }

    public void setCodCondicionPrecio(int codCondicionPrecio) {
        this.codCondicionPrecio = codCondicionPrecio;
    }

    public EstadoReferencial getEstadoReferencial() {
        return estadoReferencial;
    }

    public void setEstadoReferencial(EstadoReferencial estadoReferencial) {
        this.estadoReferencial = estadoReferencial;
    }

    public String getNombreCondicionPrecio() {
        return nombreCondicionPrecio;
    }

    public void setNombreCondicionPrecio(String nombreCondicionPrecio) {
        this.nombreCondicionPrecio = nombreCondicionPrecio;
    }

    public String getObsCondicionPrecio() {
        return obsCondicionPrecio;
    }

    public void setObsCondicionPrecio(String obsCondicionPrecio) {
        this.obsCondicionPrecio = obsCondicionPrecio;
    }

    public TiposTransporte getTiposTransporte() {
        return tiposTransporte;
    }

    public void setTiposTransporte(TiposTransporte tiposTransporte) {
        this.tiposTransporte = tiposTransporte;
    }
    
}
