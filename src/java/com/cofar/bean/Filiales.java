/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.bean;

/**
 *
 * @author hvaldivia
 */
public class Filiales {
    int codFilial = 0;
    String nombreFilial = "";
    String obsFilial= "";
    EstadoReferencial estadoReferencial = new EstadoReferencial();
    Territorios territorios = new Territorios();

    public int getCodFilial() {
        return codFilial;
    }

    public void setCodFilial(int codFilial) {
        this.codFilial = codFilial;
    }

    public EstadoReferencial getEstadoReferencial() {
        return estadoReferencial;
    }

    public void setEstadoReferencial(EstadoReferencial estadoReferencial) {
        this.estadoReferencial = estadoReferencial;
    }

    public String getNombreFilial() {
        return nombreFilial;
    }

    public void setNombreFilial(String nombreFilial) {
        this.nombreFilial = nombreFilial;
    }

    public String getObsFilial() {
        return obsFilial;
    }

    public void setObsFilial(String obsFilial) {
        this.obsFilial = obsFilial;
    }

    public Territorios getTerritorios() {
        return territorios;
    }

    public void setTerritorios(Territorios territorios) {
        this.territorios = territorios;
    }

    


}
