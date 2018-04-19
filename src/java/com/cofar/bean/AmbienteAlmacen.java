/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.bean;

/**
 *
 * @author USER
 */
public class AmbienteAlmacen extends AbstractBean {
    Almacenes almacen = new Almacenes();
    int codAmbiente = 0;
    String nombreAmbiente = "";
    String observacion = "";

    public Almacenes getAlmacen() {
        return almacen;
    }

    public void setAlmacen(Almacenes almacen) {
        this.almacen = almacen;
    }

    public int getCodAmbiente() {
        return codAmbiente;
    }

    public void setCodAmbiente(int codAmbiente) {
        this.codAmbiente = codAmbiente;
    }

    public String getNombreAmbiente() {
        return nombreAmbiente;
    }

    public void setNombreAmbiente(String nombreAmbiente) {
        this.nombreAmbiente = nombreAmbiente;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }


    

    

    


}
