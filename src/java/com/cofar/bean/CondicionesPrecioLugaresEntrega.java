/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.bean;

/**
 *
 * @author hvaldivia
 */
public class CondicionesPrecioLugaresEntrega {
    int codCondicionPrecio = 0;
    int codLugarEntrega = 0;
    String nombreLugarEntrega = "";
    EstadoReferencial estadoReferencial = new EstadoReferencial();
    String obsLugarEntrega = "";

    public int getCodCondicionPrecio() {
        return codCondicionPrecio;
    }

    public void setCodCondicionPrecio(int codCondicionPrecio) {
        this.codCondicionPrecio = codCondicionPrecio;
    }

    public int getCodLugarEntrega() {
        return codLugarEntrega;
    }

    public void setCodLugarEntrega(int codLugarEntrega) {
        this.codLugarEntrega = codLugarEntrega;
    }

    public EstadoReferencial getEstadoReferencial() {
        return estadoReferencial;
    }

    public void setEstadoReferencial(EstadoReferencial estadoReferencial) {
        this.estadoReferencial = estadoReferencial;
    }

    public String getNombreLugarEntrega() {
        return nombreLugarEntrega;
    }

    public void setNombreLugarEntrega(String nombreLugarEntrega) {
        this.nombreLugarEntrega = nombreLugarEntrega;
    }

    public String getObsLugarEntrega() {
        return obsLugarEntrega;
    }

    public void setObsLugarEntrega(String obsLugarEntrega) {
        this.obsLugarEntrega = obsLugarEntrega;
    }

    

}
