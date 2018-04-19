/*
 * EstadoProducto.java
 *
 * Created on 18 de marzo de 2008, 05:31 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.cofar.bean;

/**
 *
 * @author Wilson Choquehuanca Gonzales
 * @company COFAR
 */
public class EstadoSolicitudMantenimiento {
    
    /** Creates a new instance of EstadoProducto */
    private String codEstadoSolicitudMantenimiento="";
    private String nombreEstadoSolicitudMantenimiento="";

    public String getCodEstadoSolicitudMantenimiento() {
        return codEstadoSolicitudMantenimiento;
    }

    public void setCodEstadoSolicitudMantenimiento(String codEstadoSolicitudMantenimiento) {
        this.codEstadoSolicitudMantenimiento = codEstadoSolicitudMantenimiento;
    }

    public String getNombreEstadoSolicitudMantenimiento() {
        return nombreEstadoSolicitudMantenimiento;
    }

    public void setNombreEstadoSolicitudMantenimiento(String nombreEstadoSolicitudMantenimiento) {
        this.nombreEstadoSolicitudMantenimiento = nombreEstadoSolicitudMantenimiento;
    }

  

}
