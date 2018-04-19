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
 * date 10-11-2015
 * time 10:23:09 AM
 */
public class MaterialesConfiguracionSolicitudSalida {
    private int codMaterial;
    private String nombreMaterial;
    private boolean materialHabilitado;
    private String color;

    public int getCodMaterial() {
        return codMaterial;
    }

    public void setCodMaterial(int codMaterial) {
        this.codMaterial = codMaterial;
    }

    public String getNombreMaterial() {
        return nombreMaterial;
    }

    public void setNombreMaterial(String nombreMaterial) {
        this.nombreMaterial = nombreMaterial;
    }

    public boolean isMaterialHabilitado() {
        return materialHabilitado;
    }

    public void setMaterialHabilitado(boolean materialHabilitado) {
        this.materialHabilitado = materialHabilitado;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    
}
