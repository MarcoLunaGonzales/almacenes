/*
 * Personal.java
 *
 * Created on 6 de marzo de 2008, 11:15
 */

package com.cofar.bean;

import java.util.Date;

/**
 *
 * @author Gabriela Quelali Siñani
 * @company COFAR
 */
public class UnidadesMedida extends AbstractBean implements Cloneable{
    /** Creates a new instance of EnvasesPrimarios */

   private int codUnidadMedida = 0;
   private TiposMedida tiposMedida = new TiposMedida();
   private String nombreUnidadMedida = "";
   private String obsUnidadMedida = "";
   private EstadoReferencial estadoReferencial = new EstadoReferencial();
   private String abreviatura = "";
   int unidadClave = 0;

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    public int getCodUnidadMedida() {
        return codUnidadMedida;
    }

    public void setCodUnidadMedida(int codUnidadMedida) {
        this.codUnidadMedida = codUnidadMedida;
    }

    public EstadoReferencial getEstadoReferencial() {
        return estadoReferencial;
    }

    public void setEstadoReferencial(EstadoReferencial estadoReferencial) {
        this.estadoReferencial = estadoReferencial;
    }

    public String getNombreUnidadMedida() {
        return nombreUnidadMedida;
    }

    public void setNombreUnidadMedida(String nombreUnidadMedida) {
        this.nombreUnidadMedida = nombreUnidadMedida;
    }

    public String getObsUnidadMedida() {
        return obsUnidadMedida;
    }

    public void setObsUnidadMedida(String obsUnidadMedida) {
        this.obsUnidadMedida = obsUnidadMedida;
    }

    public TiposMedida getTiposMedida() {
        return tiposMedida;
    }

    public void setTiposMedida(TiposMedida tiposMedida) {
        this.tiposMedida = tiposMedida;
    }

    public int getUnidadClave() {
        return unidadClave;
    }

    public void setUnidadClave(int unidadClave) {
        this.unidadClave = unidadClave;
    }
   
    
    public UnidadesMedida clone()
    {
        UnidadesMedida clone =null;
        try
        {
            clone =  (UnidadesMedida)super.clone();
        }
        catch(CloneNotSupportedException ex)
        {
            ex.printStackTrace();
            System.out.println("no se puede clonar");
        }
        return clone;
    }
    
}
