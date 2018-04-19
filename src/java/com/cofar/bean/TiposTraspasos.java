/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.bean;

/**
 *
 * @author aquispe
 */
public class TiposTraspasos extends AbstractBean{
    private String codTipoTraspaso="";
    private String nombreTipoTraspaso="";

    public TiposTraspasos() {
    }

    public String getCodTipoTraspaso() {
        return codTipoTraspaso;
    }

    public void setCodTipoTraspaso(String codTipoTraspaso) {
        this.codTipoTraspaso = codTipoTraspaso;
    }

    public String getNombreTipoTraspaso() {
        return nombreTipoTraspaso;
    }

    public void setNombreTipoTraspaso(String nombreTipoTraspaso) {
        this.nombreTipoTraspaso = nombreTipoTraspaso;
    }

    

}
