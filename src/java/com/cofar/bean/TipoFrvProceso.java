/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofar.bean;

/**
 *
 * @author DASISAQ
 */
public class TipoFrvProceso extends AbstractBean{
    private int codTipoFrvProceso = 0;
    private String nombreTipoFrvProceso = "";

    public TipoFrvProceso() {
    }

    public int getCodTipoFrvProceso() {
        return codTipoFrvProceso;
    }

    public void setCodTipoFrvProceso(int codTipoFrvProceso) {
        this.codTipoFrvProceso = codTipoFrvProceso;
    }

    public String getNombreTipoFrvProceso() {
        return nombreTipoFrvProceso;
    }

    public void setNombreTipoFrvProceso(String nombreTipoFrvProceso) {
        this.nombreTipoFrvProceso = nombreTipoFrvProceso;
    }
    
    
}
