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
public class Monedas {
    
    /** Creates a new instance of LineaMKT */
    private String codMoneda="";
    private String nombreMoneda="";
    private String abreviaturaMoneda="";

    public String getCodMoneda() {
        return codMoneda;
    }

    public void setCodMoneda(String codMoneda) {
        this.codMoneda = codMoneda;
    }

    public String getNombreMoneda() {
        return nombreMoneda;
    }

    public void setNombreMoneda(String nombreMoneda) {
        this.nombreMoneda = nombreMoneda;
    }

    public String getAbreviaturaMoneda() {
        return abreviaturaMoneda;
    }

    public void setAbreviaturaMoneda(String abreviaturaMoneda) {
        this.abreviaturaMoneda = abreviaturaMoneda;
    }
        
}
