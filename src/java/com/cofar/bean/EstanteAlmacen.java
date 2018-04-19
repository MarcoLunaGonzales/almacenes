/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.bean;

/**
 *
 * @author USER
 */
public class EstanteAlmacen extends AbstractBean  implements Cloneable{
    AmbienteAlmacen ambienteAlmacen = new AmbienteAlmacen();
    int codEstante =0 ;
    String nombreEstante = "";
    String observacion = "";

    public AmbienteAlmacen getAmbienteAlmacen() {
        return ambienteAlmacen;
    }

    public void setAmbienteAlmacen(AmbienteAlmacen ambienteAlmacen) {
        this.ambienteAlmacen = ambienteAlmacen;
    }

    public int getCodEstante() {
        return codEstante;
    }

    public void setCodEstante(int codEstante) {
        this.codEstante = codEstante;
    }

    public String getNombreEstante() {
        return nombreEstante;
    }

    public void setNombreEstante(String nombreEstante) {
        this.nombreEstante = nombreEstante;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
    
    
    public EstanteAlmacen clone()
    {
        EstanteAlmacen clone=null;
        try
        {
            clone = (EstanteAlmacen)super.clone();
        }
        catch(CloneNotSupportedException ex)
        {
            ex.printStackTrace();
            System.out.println("no se puede clonar");
        }
        return clone;
    }


}
