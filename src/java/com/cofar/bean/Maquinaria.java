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
public class Maquinaria {
    
    /** Creates a new instance of LineaMKT */
    
    private String codMaquina="0";
    private String nombreMaquina="";
    private String obsMaquina="";
    private String fechaCompra="";
    private String codigo="";
    private String GMP="";     
    private Boolean checked=new Boolean(false);
    private EstadoReferencial estadoReferencial=new EstadoReferencial();
    private TiposEquiposMaquinaria tiposEquiposMaquinaria=new TiposEquiposMaquinaria();
    private String codAreaMaquina="";
    private String nombreAreaMaquina="";
    
    public String getCodMaquina() {
        return codMaquina;
    }

    public void setCodMaquina(String codMaquina) {
        this.codMaquina = codMaquina;
    }

    public String getNombreMaquina() {
        return nombreMaquina;
    }

    public void setNombreMaquina(String nombreMaquina) {
        this.nombreMaquina = nombreMaquina;
    }

    public String getObsMaquina() {
        return obsMaquina;
    }

    public void setObsMaquina(String obsMaquina) {
        this.obsMaquina = obsMaquina;
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

    public String getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(String fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public TiposEquiposMaquinaria getTiposEquiposMaquinaria() {
        return tiposEquiposMaquinaria;
    }

    public void setTiposEquiposMaquinaria(TiposEquiposMaquinaria tiposEquiposMaquinaria) {
        this.tiposEquiposMaquinaria = tiposEquiposMaquinaria;
    }

    public String getGMP() {
        return GMP;
    }

    public void setGMP(String GMP) {
        this.GMP = GMP;
    }

    public String getCodAreaMaquina() {
        return codAreaMaquina;
    }

    public void setCodAreaMaquina(String codAreaMaquina) {
        this.codAreaMaquina = codAreaMaquina;
    }

    public String getNombreAreaMaquina() {
        return nombreAreaMaquina;
    }

    public void setNombreAreaMaquina(String nombreAreaMaquina) {
        this.nombreAreaMaquina = nombreAreaMaquina;
    }

   

  
   
}
