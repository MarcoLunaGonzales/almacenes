/*
 * TiposMercaderia.java
 *
 * Created on 18 de marzo de 2008, 17:38
 */

package com.cofar.bean;

import java.util.Date;

/**
 *
 * @author Wilson Choquehuanca Gonzales
 * @company COFAR
 */
public class ModulosInstalaciones {
    

    private String codModuloInstalacion="";
    private String nombreModuloInstalacion="";
    private String obsModuloInstalacion="";
    private EstadoReferencial estadoReferencial=new EstadoReferencial ();
    private Boolean checked=new Boolean (false);
    AreasInstalacionesModulos areasInstalacionesModulos = new AreasInstalacionesModulos();

    public String getCodModuloInstalacion() {
        return codModuloInstalacion;
    }

    public void setCodModuloInstalacion(String codModuloInstalacion) {
        this.codModuloInstalacion = codModuloInstalacion;
    }

    public String getNombreModuloInstalacion() {
        return nombreModuloInstalacion;
    }

    public void setNombreModuloInstalacion(String nombreModuloInstalacion) {
        this.nombreModuloInstalacion = nombreModuloInstalacion;
    }

    public String getObsModuloInstalacion() {
        return obsModuloInstalacion;
    }

    public void setObsModuloInstalacion(String obsModuloInstalacion) {
        this.obsModuloInstalacion = obsModuloInstalacion;
    }

    public EstadoReferencial getEstadoReferencial() {
        return estadoReferencial;
    }

    public void setEstadoReferencial(EstadoReferencial estadoReferencial) {
        this.estadoReferencial = estadoReferencial;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public AreasInstalacionesModulos getAreasInstalacionesModulos() {
        return areasInstalacionesModulos;
    }

    public void setAreasInstalacionesModulos(AreasInstalacionesModulos areasInstalacionesModulos) {
        this.areasInstalacionesModulos = areasInstalacionesModulos;
    }
    

   
   
}
