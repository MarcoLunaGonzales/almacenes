/*
 * LineaMKT.java
 *
 * Created on 21 de abril de 2008, 10:14 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.cofar.bean;

/**
 *
 * @author Osmar Hinojosa Miranda
 */
public class EstadosFlujo {
    
    /** Creates a new instance of LineaMKT */
    private String codEstadoFlujo="0";
    private String nombreEstadoFlujo="";
    private String abreviatura="";

    /**
     * @return the codEstadoFlujo
     */
    public String getCodEstadoFlujo() {
        return codEstadoFlujo;
    }

    /**
     * @param codEstadoFlujo the codEstadoFlujo to set
     */
    public void setCodEstadoFlujo(String codEstadoFlujo) {
        this.codEstadoFlujo = codEstadoFlujo;
    }

    /**
     * @return the nombreEstadoFlujo
     */
    public String getNombreEstadoFlujo() {
        return nombreEstadoFlujo;
    }

    /**
     * @param nombreEstadoFlujo the nombreEstadoFlujo to set
     */
    public void setNombreEstadoFlujo(String nombreEstadoFlujo) {
        this.nombreEstadoFlujo = nombreEstadoFlujo;
    }

    /**
     * @return the abreviatura
     */
    public String getAbreviatura() {
        return abreviatura;
    }

    /**
     * @param abreviatura the abreviatura to set
     */
    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }
        
}
