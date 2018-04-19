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
public class PartesMaquinaria {
    
    /** Creates a new instance of LineaMKT */
    
    private String codParteMaquina="";
    private Maquinaria maquinaria=new Maquinaria();
    private String codigo="";
    private String obsParteMaquina="";
    private String nombreParteMaquina="";
    private Boolean checked=new Boolean(false);
    private TiposEquiposMaquinaria tiposEquiposMaquinaria=new TiposEquiposMaquinaria();

    public String getCodParteMaquina() {
        return codParteMaquina;
    }

    public void setCodParteMaquina(String codParteMaquina) {
        this.codParteMaquina = codParteMaquina;
    }

    public String getObsParteMaquina() {
        return obsParteMaquina;
    }

    public void setObsParteMaquina(String obsParteMaquina) {
        this.obsParteMaquina = obsParteMaquina;
    }

    public String getNombreParteMaquina() {
        return nombreParteMaquina;
    }

    public void setNombreParteMaquina(String nombreParteMaquina) {
        this.nombreParteMaquina = nombreParteMaquina;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public TiposEquiposMaquinaria getTiposEquiposMaquinaria() {
        return tiposEquiposMaquinaria;
    }

    public void setTiposEquiposMaquinaria(TiposEquiposMaquinaria tiposEquiposMaquinaria) {
        this.tiposEquiposMaquinaria = tiposEquiposMaquinaria;
    }

    public Maquinaria getMaquinaria() {
        return maquinaria;
    }

    public void setMaquinaria(Maquinaria maquinaria) {
        this.maquinaria = maquinaria;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
   
   
}
