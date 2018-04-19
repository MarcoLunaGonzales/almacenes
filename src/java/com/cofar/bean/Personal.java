/*
 * Personal.java
 *
 * Created on 4 de marzo de 2008, 04:17 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.cofar.bean;

/**
 *
 *  @author Rene Ergueta Illanes
 *  @company COFAR
 */
public class Personal {
    
    /** Creates a new instance of Personal */
    private String codPersonal="";
    private String nombrePersonal="";
    private String nombre2Personal="";
    private String apPaternoPersonal="";
    private String apMaternoPersonal="";
    public Personal() {
    }

    public String getCodPersonal() {
        return codPersonal;
    }

    public void setCodPersonal(String codPersonal) {
        this.codPersonal = codPersonal;
    }

    public String getNombrePersonal() {
        return nombrePersonal;
    }

    public void setNombrePersonal(String nombrePersonal) {
        this.nombrePersonal = nombrePersonal;
    }

    public String getApPaternoPersonal() {
        return apPaternoPersonal;
    }

    public void setApPaternoPersonal(String apPaternoPersonal) {
        this.apPaternoPersonal = apPaternoPersonal;
    }

    public String getApMaternoPersonal() {
        return apMaternoPersonal;
    }

    public void setApMaternoPersonal(String apMaternoPersonal) {
        this.apMaternoPersonal = apMaternoPersonal;
    }
    
    public String getNombreCompleto(){
        return (nombrePersonal!=null && !nombrePersonal.equals("")? " "+nombrePersonal:"") 
            +(nombre2Personal!=null && !nombre2Personal.equals("")? " "+nombre2Personal:"")
            +(apPaternoPersonal!=null && !apPaternoPersonal.equals("")? " "+apPaternoPersonal:"")
            +(apMaternoPersonal!=null && !apMaternoPersonal.equals("")? " "+apMaternoPersonal:"");                
    }
    
    public String getNombreCompleto2(){
        return (apPaternoPersonal!=null && !apPaternoPersonal.equals("")? " "+apPaternoPersonal:"")
            +(apMaternoPersonal!=null && !apMaternoPersonal.equals("")? " "+apMaternoPersonal:"")
            +(nombrePersonal!=null && !nombrePersonal.equals("")? " "+nombrePersonal:"") 
            +(nombre2Personal!=null && !nombre2Personal.equals("")? " "+nombre2Personal:"");                
    }

    public String getNombre2Personal() {
        return nombre2Personal;
    }

    public void setNombre2Personal(String nombre2Personal) {
        this.nombre2Personal = nombre2Personal;
    }
    
}
