/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.bean;

/**
 *
 * @author rcondori
 */
public class EstadoTransaccionSolicitud extends AbstractBean {
    private int codEstadoTransaccionSolicitud;
    private String nombreEstadoTransaccionSolicitud = "";

    public int getCodEstadoTransaccionSolicitud() {
        return codEstadoTransaccionSolicitud;
    }

    public void setCodEstadoTransaccionSolicitud(int codEstadoTransaccionSolicitud) {
        this.codEstadoTransaccionSolicitud = codEstadoTransaccionSolicitud;
    }

    public String getNombreEstadoTransaccionSolicitud() {
        return nombreEstadoTransaccionSolicitud;
    }

    public void setNombreEstadoTransaccionSolicitud(String nombreEstadoTransaccionSolicitud) {
        this.nombreEstadoTransaccionSolicitud = nombreEstadoTransaccionSolicitud;
    }



    
    

}
