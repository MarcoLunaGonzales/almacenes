/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.bean;

/**
 *
 * @author sistemas1
 */
public class TiposPago {
    int codTipoPago = 0;
    String nombreTipoPago = "";
    String obsTipoPago = "";    
    EstadoReferencial estadoReferencial = new EstadoReferencial();
    String abreviatura = "";

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    public int getCodTipoPago() {
        return codTipoPago;
    }

    public void setCodTipoPago(int codTipoPago) {
        this.codTipoPago = codTipoPago;
    }

    public EstadoReferencial getEstadoReferencial() {
        return estadoReferencial;
    }

    public void setEstadoReferencial(EstadoReferencial estadoReferencial) {
        this.estadoReferencial = estadoReferencial;
    }

    public String getNombreTipoPago() {
        return nombreTipoPago;
    }

    public void setNombreTipoPago(String nombreTipoPago) {
        this.nombreTipoPago = nombreTipoPago;
    }

    public String getObsTipoPago() {
        return obsTipoPago;
    }

    public void setObsTipoPago(String obsTipoPago) {
        this.obsTipoPago = obsTipoPago;
    }

    


}
