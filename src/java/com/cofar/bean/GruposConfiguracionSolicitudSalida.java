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
 * date 09-11-2015
 * time 05:22:13 PM
 */
public class GruposConfiguracionSolicitudSalida {
    private int codGrupo;
    private String nombreGrupo;
    private boolean grupoCompleto;
    private boolean grupoParcial;
    private String color;

    public int getCodGrupo() {
        return codGrupo;
    }

    public void setCodGrupo(int codGrupo) {
        this.codGrupo = codGrupo;
    }

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

    public boolean isGrupoCompleto() {
        return grupoCompleto;
    }

    public void setGrupoCompleto(boolean grupoCompleto) {
        this.grupoCompleto = grupoCompleto;
    }

    public boolean isGrupoParcial() {
        return grupoParcial;
    }

    public void setGrupoParcial(boolean grupoParcial) {
        this.grupoParcial = grupoParcial;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    
    
    
}
