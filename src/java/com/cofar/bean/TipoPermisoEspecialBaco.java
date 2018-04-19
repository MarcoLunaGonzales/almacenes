/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofar.bean;

/**
 *
 * @author rcondori
 */
public class TipoPermisoEspecialBaco extends AbstractBean{
    private int codTipoPermisoEspecialBaco ;
    private String nombreTipoPermisoEspecialBaco = "";

    public int getCodTipoPermisoEspecialBaco() {
        return codTipoPermisoEspecialBaco;
    }

    public void setCodTipoPermisoEspecialBaco(int codTipoPermisoEspecialBaco) {
        this.codTipoPermisoEspecialBaco = codTipoPermisoEspecialBaco;
    }

    public String getNombreTipoPermisoEspecialBaco() {
        return nombreTipoPermisoEspecialBaco;
    }

    public void setNombreTipoPermisoEspecialBaco(String nombreTipoPermisoEspecialBaco) {
        this.nombreTipoPermisoEspecialBaco = nombreTipoPermisoEspecialBaco;
    }
    
    
    
}
