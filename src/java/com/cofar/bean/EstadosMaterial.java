/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.bean;

/**
 *
 * @author hvaldivia
 */
public class EstadosMaterial  extends AbstractBean implements Cloneable{
    int codEstadoMaterial = 0;
    String nombreEstadoMaterial = "";
    String obsEstadoMaterial = "";
    EstadoReferencial estadoReferencial = new EstadoReferencial();
    //inicio alejandro
    private String codColorEstadoMaterial="";
    //variable para mostrar existencia de material
    private Double cantidadExistente=0d;
    private Boolean estadoSalidaPermitido=false;
    //final alejandro
    public String getCodColorEstadoMaterial() {
        return codColorEstadoMaterial;
    }

    public void setCodColorEstadoMaterial(String codColorEstadoMaterial) {
        this.codColorEstadoMaterial = codColorEstadoMaterial;
    }

    //final alejandro
    public int getCodEstadoMaterial() {
        return codEstadoMaterial;
    }

    public void setCodEstadoMaterial(int codEstadoMaterial) {
        this.codEstadoMaterial = codEstadoMaterial;
    }

    public EstadoReferencial getEstadoReferencial() {
        return estadoReferencial;
    }

    public void setEstadoReferencial(EstadoReferencial estadoReferencial) {
        this.estadoReferencial = estadoReferencial;
    }

    public String getNombreEstadoMaterial() {
        return nombreEstadoMaterial;
    }

    public void setNombreEstadoMaterial(String nombreEstadoMaterial) {
        this.nombreEstadoMaterial = nombreEstadoMaterial;
    }

    public String getObsEstadoMaterial() {
        return obsEstadoMaterial;
    }

    public void setObsEstadoMaterial(String obsEstadoMaterial) {
        this.obsEstadoMaterial = obsEstadoMaterial;
    }

    public Double getCantidadExistente() {
        return cantidadExistente;
    }

    public void setCantidadExistente(Double cantidadExistente) {
        this.cantidadExistente = cantidadExistente;
    }

    public Boolean getEstadoSalidaPermitido() {
        return estadoSalidaPermitido;
    }

    public void setEstadoSalidaPermitido(Boolean estadoSalidaPermitido) {
        this.estadoSalidaPermitido = estadoSalidaPermitido;
    }

    
    
    @Override
    public EstadosMaterial clone() throws CloneNotSupportedException
    {
        EstadosMaterial clone = null;
        try
        {
            clone = (EstadosMaterial)super.clone();
        }
        catch(CloneNotSupportedException ex)
        {
            System.out.println("no se puede clonar");
        }
        return clone;
    }

}
