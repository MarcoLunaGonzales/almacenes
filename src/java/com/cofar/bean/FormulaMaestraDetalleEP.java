/*
 * ComponentesProd.java
 *
 * Created on 25 de mayo de 2008, 19:26
 */

package com.cofar.bean;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Wilson Choquehuanca Gonzales
 * @company COFAR
 */
public class FormulaMaestraDetalleEP extends AbstractBean {
    
    /** Creates a new instance of ComponentesProd */
    private FormulaMaestra formulaMaestra=new FormulaMaestra();
    private PresentacionesPrimarias presentacionesPrimarias=new PresentacionesPrimarias();
    private Materiales materiales=new Materiales();
    private float cantidad=0;
  
    
  
    private UnidadesMedida unidadesMedida=new UnidadesMedida();

    public FormulaMaestra getFormulaMaestra() {
        return formulaMaestra;
    }

    public void setFormulaMaestra(FormulaMaestra formulaMaestra) {
        this.formulaMaestra = formulaMaestra;
    }

    public PresentacionesPrimarias getPresentacionesPrimarias() {
        return presentacionesPrimarias;
    }

    public void setPresentacionesPrimarias(PresentacionesPrimarias presentacionesPrimarias) {
        this.presentacionesPrimarias = presentacionesPrimarias;
    }

    public Materiales getMateriales() {
        return materiales;
    }

    public void setMateriales(Materiales materiales) {
        this.materiales = materiales;
    }

    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

   

    
   
    public UnidadesMedida getUnidadesMedida() {
        return unidadesMedida;
    }

    public void setUnidadesMedida(UnidadesMedida unidadesMedida) {
        this.unidadesMedida = unidadesMedida;
    }
/*
    public static void main(String[] args) {
        String a="65,000.0";
        a=a.replace(",","");
        
       float x=Float.parseFloat(a);
      System.out.println("z:"+x);
    }
*/
  
}
