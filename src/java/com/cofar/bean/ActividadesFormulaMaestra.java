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
 * @author rodrigo
 */
public class ActividadesFormulaMaestra {
    
    /** Creates a new instance of LineaMKT */
    
    private String codActividadFormula="";
    private String ordenActividad="";
    private Boolean checked=new Boolean(false);
    private FormulaMaestra formulaMaestra=new FormulaMaestra();
    private ActividadesProduccion actividadesProduccion=new ActividadesProduccion();
    private EstadoReferencial estadoReferencial=new EstadoReferencial();

    public String getCodActividadFormula() {
        return codActividadFormula;
    }

    public void setCodActividadFormula(String codActividadFormula) {
        this.codActividadFormula = codActividadFormula;
    }

    public String getOrdenActividad() {
        return ordenActividad;
    }

    public void setOrdenActividad(String ordenActividad) {
        this.ordenActividad = ordenActividad;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public FormulaMaestra getFormulaMaestra() {
        return formulaMaestra;
    }

    public void setFormulaMaestra(FormulaMaestra formulaMaestra) {
        this.formulaMaestra = formulaMaestra;
    }

    public ActividadesProduccion getActividadesProduccion() {
        return actividadesProduccion;
    }

    public void setActividadesProduccion(ActividadesProduccion actividadesProduccion) {
        this.actividadesProduccion = actividadesProduccion;
    }

    public EstadoReferencial getEstadoReferencial() {
        return estadoReferencial;
    }

    public void setEstadoReferencial(EstadoReferencial estadoReferencial) {
        this.estadoReferencial = estadoReferencial;
    }

 
}
