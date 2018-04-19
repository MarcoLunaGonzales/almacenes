/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.bean;

/**
 *
 * @author aquispe
 */
public class Meses extends AbstractBean{

    private int codMes=0;
    private String nombreMes="";
    private int ordenMes=0;
    private String abreviaturaMes="";
    private BiMensual biMensual= new BiMensual();
    public Meses() {
    }

    public String getAbreviaturaMes() {
        return abreviaturaMes;
    }

    public void setAbreviaturaMes(String abreviaturaMes) {
        this.abreviaturaMes = abreviaturaMes;
    }

    public BiMensual getBiMensual() {
        return biMensual;
    }

    public void setBiMensual(BiMensual biMensual) {
        this.biMensual = biMensual;
    }

    public int getCodMes() {
        return codMes;
    }

    public void setCodMes(int codMes) {
        this.codMes = codMes;
    }

    public String getNombreMes() {
        return nombreMes;
    }

    public void setNombreMes(String nombreMes) {
        this.nombreMes = nombreMes;
    }

    public int getOrdenMes() {
        return ordenMes;
    }

    public void setOrdenMes(int ordenMes) {
        this.ordenMes = ordenMes;
    }
    


}
