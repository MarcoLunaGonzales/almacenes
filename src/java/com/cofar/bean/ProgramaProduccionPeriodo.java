/*
 * ProgramaProduccionPeriodo.java
 *
 * Created on 18 de noviembre de 2009, 03:28 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.cofar.bean;

/**
 *
 * @author mluna
 */
public class ProgramaProduccionPeriodo extends AbstractBean{

    private String codProgramaProduccion="";
    private String nombreProgramaProduccion="";
    private String obsProgramaProduccion="";    
    private Boolean checked=new Boolean(false);    
    private EstadoProgramaProduccion estadoProgramaProduccion = new EstadoProgramaProduccion();
    private TiposProduccion tiposProduccion = new TiposProduccion();

    /** Creates a new instance of ProgramaProduccionPeriodo */
    public ProgramaProduccionPeriodo() {
    }

    public String getCodProgramaProduccion() {
        return codProgramaProduccion;
    }

    public void setCodProgramaProduccion(String codProgramaProduccion) {
        this.codProgramaProduccion = codProgramaProduccion;
    }

    public EstadoProgramaProduccion getEstadoProgramaProduccion() {
        return estadoProgramaProduccion;
    }

    public void setEstadoProgramaProduccion(EstadoProgramaProduccion estadoProgramaProduccion) {
        this.estadoProgramaProduccion = estadoProgramaProduccion;
    }

    public String getNombreProgramaProduccion() {
        return nombreProgramaProduccion;
    }

    public void setNombreProgramaProduccion(String nombreProgramaProduccion) {
        this.nombreProgramaProduccion = nombreProgramaProduccion;
    }

    public String getObsProgramaProduccion() {
        return obsProgramaProduccion;
    }

    public void setObsProgramaProduccion(String obsProgramaProduccion) {
        this.obsProgramaProduccion = obsProgramaProduccion;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public TiposProduccion getTiposProduccion() {
        return tiposProduccion;
    }

    public void setTiposProduccion(TiposProduccion tiposProduccion) {
        this.tiposProduccion = tiposProduccion;
    }

   
    
    
}
