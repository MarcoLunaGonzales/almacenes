/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.bean;

/**
 *
 * @author hvaldivia
 */
public class Capitulos extends AbstractBean {
    int codCapitulo = 0;
    String nombreCapitulo = "";
    String obsCapitulo = "";
    EstadoReferencial estadoReferencial = new EstadoReferencial();
    int capituloAlmacen = 0;

    public int getCapituloAlmacen() {
        return capituloAlmacen;
    }

    public void setCapituloAlmacen(int capituloAlmacen) {
        this.capituloAlmacen = capituloAlmacen;
    }

    public int getCodCapitulo() {
        return codCapitulo;
    }

    public void setCodCapitulo(int codCapitulo) {
        this.codCapitulo = codCapitulo;
    }

    public EstadoReferencial getEstadoReferencial() {
        return estadoReferencial;
    }

    public void setEstadoReferencial(EstadoReferencial estadoReferencial) {
        this.estadoReferencial = estadoReferencial;
    }

    public String getNombreCapitulo() {
        return nombreCapitulo;
    }

    public void setNombreCapitulo(String nombreCapitulo) {
        this.nombreCapitulo = nombreCapitulo;
    }

    public String getObsCapitulo() {
        return obsCapitulo;
    }

    public void setObsCapitulo(String obsCapitulo) {
        this.obsCapitulo = obsCapitulo;
    }

    

}
