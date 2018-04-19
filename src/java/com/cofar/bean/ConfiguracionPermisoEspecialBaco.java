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
public class ConfiguracionPermisoEspecialBaco extends AbstractBean{
    private int codConfiguracionPermisoEspecialBaco;
    private Almacenes almacenes = new Almacenes();
    private TipoPermisoEspecialBaco tipoPermisoEspecialBaco = new TipoPermisoEspecialBaco();
    private Personal personal = new Personal();

    public int getCodConfiguracionPermisoEspecialBaco() {
        return codConfiguracionPermisoEspecialBaco;
    }

    public void setCodConfiguracionPermisoEspecialBaco(int codConfiguracionPermisoEspecialBaco) {
        this.codConfiguracionPermisoEspecialBaco = codConfiguracionPermisoEspecialBaco;
    }

    public Almacenes getAlmacenes() {
        return almacenes;
    }

    public void setAlmacenes(Almacenes almacenes) {
        this.almacenes = almacenes;
    }

    public TipoPermisoEspecialBaco getTipoPermisoEspecialBaco() {
        return tipoPermisoEspecialBaco;
    }

    public void setTipoPermisoEspecialBaco(TipoPermisoEspecialBaco tipoPermisoEspecialBaco) {
        this.tipoPermisoEspecialBaco = tipoPermisoEspecialBaco;
    }

    public Personal getPersonal() {
        return personal;
    }

    public void setPersonal(Personal personal) {
        this.personal = personal;
    }
    
    
}
