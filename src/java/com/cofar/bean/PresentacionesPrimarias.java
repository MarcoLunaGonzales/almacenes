/*
 * Permiso.java
 *
 * Created on 4 de marzo de 2008, 10:08 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.cofar.bean;

/**
 *
 *  @author Rene Ergueta Illanes
 *  @company COFAR
 */public class PresentacionesPrimarias {
    
    /**
     * Creates a new instance of Permiso
     */
    private String codPresentacionPrimaria="0";
    private ComponentesProd componentesProd=new ComponentesProd();
    private EnvasesPrimarios envasesPrimarios=new EnvasesPrimarios();
    private int cantidad=0;
    private Boolean checked=new Boolean(false);
    public PresentacionesPrimarias() {
    }

    public ComponentesProd getComponentesProd() {
        return componentesProd;
    }

    public void setComponentesProd(ComponentesProd componentesProd) {
        this.componentesProd = componentesProd;
    }

    public EnvasesPrimarios getEnvasesPrimarios() {
        return envasesPrimarios;
    }

    public void setEnvasesPrimarios(EnvasesPrimarios envasesPrimarios) {
        this.envasesPrimarios = envasesPrimarios;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public String getCodPresentacionPrimaria() {
        return codPresentacionPrimaria;
    }

    public void setCodPresentacionPrimaria(String codPresentacionPrimaria) {
        this.codPresentacionPrimaria = codPresentacionPrimaria;
    }

}
