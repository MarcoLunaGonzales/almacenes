/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.bean;

/**
 *
 * @author hvaldivia
 */
public class TiposTransporte {
    int codTipoTransporte = 0;
    String nombreTipoTransporte = "";
    String obsTipoTransporte = "";
    EstadoReferencial estadoReferencial = new EstadoReferencial();
    int diasLlegada = 0;

    public int getCodTipoTransporte() {
        return codTipoTransporte;
    }

    public void setCodTipoTransporte(int codTipoTransporte) {
        this.codTipoTransporte = codTipoTransporte;
    }

    public int getDiasLlegada() {
        return diasLlegada;
    }

    public void setDiasLlegada(int diasLlegada) {
        this.diasLlegada = diasLlegada;
    }

    public EstadoReferencial getEstadoReferencial() {
        return estadoReferencial;
    }

    public void setEstadoReferencial(EstadoReferencial estadoReferencial) {
        this.estadoReferencial = estadoReferencial;
    }

    public String getNombreTipoTransporte() {
        return nombreTipoTransporte;
    }

    public void setNombreTipoTransporte(String nombreTipoTransporte) {
        this.nombreTipoTransporte = nombreTipoTransporte;
    }

    public String getObsTipoTransporte() {
        return obsTipoTransporte;
    }

    public void setObsTipoTransporte(String obsTipoTransporte) {
        this.obsTipoTransporte = obsTipoTransporte;
    }


    

}
