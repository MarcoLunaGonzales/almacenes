/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.bean;

/**
 *
 * @author hvaldivia
 */
public class MediosPago {
    int codMedioPago = 0;
    String nombreMedioPago = "";
    EstadoReferencial estadoReferencial = new EstadoReferencial();
    String observacionMedioPago = "";

    public int getCodMedioPago() {
        return codMedioPago;
    }

    public void setCodMedioPago(int codMedioPago) {
        this.codMedioPago = codMedioPago;
    }

    public EstadoReferencial getEstadoReferencial() {
        return estadoReferencial;
    }

    public void setEstadoReferencial(EstadoReferencial estadoReferencial) {
        this.estadoReferencial = estadoReferencial;
    }

    public String getNombreMedioPago() {
        return nombreMedioPago;
    }

    public void setNombreMedioPago(String nombreMedioPago) {
        this.nombreMedioPago = nombreMedioPago;
    }

    public String getObservacionMedioPago() {
        return observacionMedioPago;
    }

    public void setObservacionMedioPago(String observacionMedioPago) {
        this.observacionMedioPago = observacionMedioPago;
    }

    

}
