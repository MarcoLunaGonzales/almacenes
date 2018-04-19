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
public class ActividadesProduccion {
    
    /** Creates a new instance of LineaMKT */
    
    private String codActividad="";
    private String nombreActividad="";
    private String obsActividad="";
    private Boolean checked=new Boolean(false);    
    private EstadoReferencial estadoReferencial=new EstadoReferencial();
    private TiposActividadProduccion tiposActividadProduccion = new TiposActividadProduccion();

    public String getCodActividad() {
        return codActividad;
    }

    public void setCodActividad(String codActividad) {
        this.codActividad = codActividad;
    }

    public String getNombreActividad() {
        return nombreActividad;
    }

    public void setNombreActividad(String nombreActividad) {
        this.nombreActividad = nombreActividad;
    }

    public String getObsActividad() {
        return obsActividad;
    }

    public void setObsActividad(String obsActividad) {
        this.obsActividad = obsActividad;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public EstadoReferencial getEstadoReferencial() {
        return estadoReferencial;
    }

    public void setEstadoReferencial(EstadoReferencial estadoReferencial) {
        this.estadoReferencial = estadoReferencial;
    }

    public TiposActividadProduccion getTiposActividadProduccion() {
        return tiposActividadProduccion;
    }

    public void setTiposActividadProduccion(TiposActividadProduccion tiposActividadProduccion) {
        this.tiposActividadProduccion = tiposActividadProduccion;
    }
    
}
