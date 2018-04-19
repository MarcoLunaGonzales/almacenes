/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.bean;

/**
 *
 * @author hvaldivia
 */
public class Territorios {
    int codTerritorio = 0;
    String nombreTerritorio = "";
    String obsTerritorio = "";
    EstadoReferencial estadoReferencial = new EstadoReferencial();

    public int getCodTerritorio() {
        return codTerritorio;
    }

    public void setCodTerritorio(int codTerritorio) {
        this.codTerritorio = codTerritorio;
    }

    public EstadoReferencial getEstadoReferencial() {
        return estadoReferencial;
    }

    public void setEstadoReferencial(EstadoReferencial estadoReferencial) {
        this.estadoReferencial = estadoReferencial;
    }

    public String getNombreTerritorio() {
        return nombreTerritorio;
    }

    public void setNombreTerritorio(String nombreTerritorio) {
        this.nombreTerritorio = nombreTerritorio;
    }

    public String getObsTerritorio() {
        return obsTerritorio;
    }

    public void setObsTerritorio(String obsTerritorio) {
        this.obsTerritorio = obsTerritorio;
    }

    

}
