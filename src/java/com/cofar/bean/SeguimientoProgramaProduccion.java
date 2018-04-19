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
public class SeguimientoProgramaProduccion {
    
    /** Creates a new instance of LineaMKT */
    
    private String codSeguimientoPrograma="";
    private String horasMaquina="";
    private String horasHombre="";
    private String fechaInicio="";
    private String fechaFinal="";
    private String horaInicio="";
    private String horaFinal="";
    private Boolean checked=new Boolean(false);
    private ProgramaProduccion programaProduccion=new ProgramaProduccion();
    private ActividadesProduccion actividadesProduccion=new ActividadesProduccion();
    private EstadoReferencial estadoReferencial=new EstadoReferencial();
    private Maquinaria maquinaria = new Maquinaria();
    
    public String getCodSeguimientoPrograma() {
        return codSeguimientoPrograma;
    }
    
    public void setCodSeguimientoPrograma(String codSeguimientoPrograma) {
        this.codSeguimientoPrograma = codSeguimientoPrograma;
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
    
    public String getFechaInicio() {
        return fechaInicio;
    }
    
    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    
    public String getFechaFinal() {
        return fechaFinal;
    }
    
    public void setFechaFinal(String fechaFinal) {
        this.fechaFinal = fechaFinal;
    }
    
    public Boolean getChecked() {
        return checked;
    }
    
    public void setChecked(Boolean checked) {
        this.checked = checked;
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
    
    public ProgramaProduccion getProgramaProduccion() {
        return programaProduccion;
    }
    
    public void setProgramaProduccion(ProgramaProduccion programaProduccion) {
        this.programaProduccion = programaProduccion;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(String horaFinal) {
        this.horaFinal = horaFinal;
    }

    public Maquinaria getMaquinaria() {
        return maquinaria;
    }

    public void setMaquinaria(Maquinaria maquinaria) {
        this.maquinaria = maquinaria;
    }

    
}
