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
 * @author Wilson Choquehuanca
 */
public class MaquinariaActividadesFormula {
    
    /** Creates a new instance of LineaMKT */
    
    private String codMaquinariaActividad="";
    private String horasMaquina="";
    private String horasHombre="";
    private Boolean checked=new Boolean(false);
    private Maquinaria maquinaria=new Maquinaria();
    private ActividadesFormulaMaestra actividadesFormulaMaestra=new ActividadesFormulaMaestra();

    public String getCodMaquinariaActividad() {
        return codMaquinariaActividad;
    }

    public void setCodMaquinariaActividad(String codMaquinariaActividad) {
        this.codMaquinariaActividad = codMaquinariaActividad;
    }

    public String getHorasMaquina() {
        return horasMaquina;
    }

    public void setHorasMaquina(String horasMaquina) {
        this.horasMaquina = horasMaquina;
    }

    public String getHorasHombre() {
        return horasHombre;
    }

    public void setHorasHombre(String horasHombre) {
        this.horasHombre = horasHombre;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Maquinaria getMaquinaria() {
        return maquinaria;
    }

    public void setMaquinaria(Maquinaria maquinaria) {
        this.maquinaria = maquinaria;
    }

    public ActividadesFormulaMaestra getActividadesFormulaMaestra() {
        return actividadesFormulaMaestra;
    }

    public void setActividadesFormulaMaestra(ActividadesFormulaMaestra actividadesFormulaMaestra) {
        this.actividadesFormulaMaestra = actividadesFormulaMaestra;
    }
   

 
}
