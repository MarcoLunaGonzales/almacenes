/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.bean;

/**
 *
 * @author hvaldivia
 */
public class SeccionesDetalle extends  AbstractBean {
    Secciones secciones = new Secciones();
    Capitulos capitulos = new Capitulos();
    Grupos grupos = new Grupos();
    Materiales materiales = new Materiales();

    public Capitulos getCapitulos() {
        return capitulos;
    }

    public void setCapitulos(Capitulos capitulos) {
        this.capitulos = capitulos;
    }

    public Grupos getGrupos() {
        return grupos;
    }

    public void setGrupos(Grupos grupos) {
        this.grupos = grupos;
    }

    public Materiales getMateriales() {
        return materiales;
    }

    public void setMateriales(Materiales materiales) {
        this.materiales = materiales;
    }

    public Secciones getSecciones() {
        return secciones;
    }

    public void setSecciones(Secciones secciones) {
        this.secciones = secciones;
    }

    

}
