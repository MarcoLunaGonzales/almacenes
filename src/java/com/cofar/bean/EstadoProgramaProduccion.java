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
 * @author Rene Ergueta Illanes
 * @company COFAR
 */
public class EstadoProgramaProduccion {
    
    /** Creates a new instance of EstadoProducto */
    private String codEstadoProgramaProd="";
    private String nombreEstadoProgramaProd="";

    public String getCodEstadoProgramaProd() {
        return codEstadoProgramaProd;
    }

    public void setCodEstadoProgramaProd(String codEstadoProgramaProd) {
        this.codEstadoProgramaProd = codEstadoProgramaProd;
    }

    public String getNombreEstadoProgramaProd() {
        return nombreEstadoProgramaProd;
    }

    public void setNombreEstadoProgramaProd(String nombreEstadoProgramaProd) {
        this.nombreEstadoProgramaProd = nombreEstadoProgramaProd;
    }



}
