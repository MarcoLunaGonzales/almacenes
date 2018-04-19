/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.bean;

/**
 *
 * @author DASISAQ-
 */
public class EstadosTransaccionSalida extends AbstractBean{
    private int codEstadoTransaccionSalida=0;
    private String nombreEstadoTransaccionSalida="";

    public EstadosTransaccionSalida() {
    }
    
    public int getCodEstadoTransaccionSalida() {
        return codEstadoTransaccionSalida;
    }

    public void setCodEstadoTransaccionSalida(int codEstadoTransaccionSalida) {
        this.codEstadoTransaccionSalida = codEstadoTransaccionSalida;
    }

    public String getNombreEstadoTransaccionSalida() {
        return nombreEstadoTransaccionSalida;
    }

    public void setNombreEstadoTransaccionSalida(String nombreEstadoTransaccionSalida) {
        this.nombreEstadoTransaccionSalida = nombreEstadoTransaccionSalida;
    }


}
