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
public class AreasInstalaciones {
    

    private String codAreaInstalacion="";
    private String nombreAreaInstalacion="";
    private String codigo="";
    private AreasEmpresa areasEmpresa=new AreasEmpresa();
    private Boolean checked=new Boolean (false);

    public String getCodAreaInstalacion() {
        return codAreaInstalacion;
    }

    public void setCodAreaInstalacion(String codAreaInstalacion) {
        this.codAreaInstalacion = codAreaInstalacion;
    }

    public String getNombreAreaInstalacion() {
        return nombreAreaInstalacion;
    }

    public void setNombreAreaInstalacion(String nombreAreaInstalacion) {
        this.nombreAreaInstalacion = nombreAreaInstalacion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public AreasEmpresa getAreasEmpresa() {
        return areasEmpresa;
    }

    public void setAreasEmpresa(AreasEmpresa areasEmpresa) {
        this.areasEmpresa = areasEmpresa;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

   
   
}
