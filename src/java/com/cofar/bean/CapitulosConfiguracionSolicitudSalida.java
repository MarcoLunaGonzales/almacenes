/*
 *To change this license header, choose License Headers in Project Properties.
 *To change this template file, choose Tools | Templates
 *and open the template in the editor.
 */

package com.cofar.bean;

/**
 *
 * @author Jaime Chura
 * @version BACO 2.0
 * date 04-11-2015
 * time 06:56:34 PM
 */
public class CapitulosConfiguracionSolicitudSalida extends Capitulos{
    private boolean capituloCompleto;
    private boolean capituloParcial;

    public boolean isCapituloCompleto() {
        return capituloCompleto;
    }

    public void setCapituloCompleto(boolean capituloCompleto) {
        this.capituloCompleto = capituloCompleto;
    }

    public boolean isCapituloParcial() {
        return capituloParcial;
    }

    public void setCapituloParcial(boolean capituloParcial) {
        this.capituloParcial = capituloParcial;
    }

    private String color;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    
    
}
