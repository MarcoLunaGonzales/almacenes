/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.bean;

import java.util.Date;

public class ProgramaProduccionDevolucionMaterial extends AbstractBean{
    
    private String codProgramaProduccionDevolucionMaterial="";
    private ProgramaProduccion programaProduccion=new ProgramaProduccion();
    private EstadoReferencial estadoProgramaProduccionDevolucion= new EstadoReferencial();
    private Date fechaRegistro= new Date();
    public ProgramaProduccionDevolucionMaterial() {
    }

    public ProgramaProduccion getProgramaProduccion() {
        return programaProduccion;
    }

    public void setProgramaProduccion(ProgramaProduccion programaProduccion) {
        this.programaProduccion = programaProduccion;
    }

    public String getCodProgramaProduccionDevolucionMaterial() {
        return codProgramaProduccionDevolucionMaterial;
    }

    public void setCodProgramaProduccionDevolucionMaterial(String codProgramaProduccionDevolucionMaterial) {
        this.codProgramaProduccionDevolucionMaterial = codProgramaProduccionDevolucionMaterial;
    }

    public EstadoReferencial getEstadoProgramaProduccionDevolucion() {
        return estadoProgramaProduccionDevolucion;
    }

    public void setEstadoProgramaProduccionDevolucion(EstadoReferencial estadoProgramaProduccionDevolucion) {
        this.estadoProgramaProduccionDevolucion = estadoProgramaProduccionDevolucion;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    
}
