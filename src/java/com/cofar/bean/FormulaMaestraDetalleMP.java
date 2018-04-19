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
public class FormulaMaestraDetalleMP extends AbstractBean {
    
    /** Creates a new instance of ComponentesProd */
    private FormulaMaestra formulaMaestra=new FormulaMaestra();
    private Materiales materiales=new Materiales();
    private Double cantidad=0d;
    private UnidadesMedida unidadesMedida=new UnidadesMedida();
    private int nroPreparaciones=1;
    private int nroDecimalesAlmacen=0;
    private boolean swSi=false;
    private boolean swNo=false;
    private List fraccionesDetalleList = new ArrayList();
    
    public FormulaMaestra getFormulaMaestra() {
        return formulaMaestra;
    }
    
    public void setFormulaMaestra(FormulaMaestra formulaMaestra) {
        this.formulaMaestra = formulaMaestra;
    }
    
    public Materiales getMateriales() {
        return materiales;
    }
    
    public void setMateriales(Materiales materiales) {
        this.materiales = materiales;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

   

    
    
  
    
    public UnidadesMedida getUnidadesMedida() {
        return unidadesMedida;
    }
    
    public void setUnidadesMedida(UnidadesMedida unidadesMedida) {
        this.unidadesMedida = unidadesMedida;
    }
    
    public int getNroPreparaciones() {
        return nroPreparaciones;
    }
    
    public void setNroPreparaciones(int nroPreparaciones) {
        this.nroPreparaciones = nroPreparaciones;
    }

    public boolean isSwSi() {
        return swSi;
    }

    public void setSwSi(boolean swSi) {
        this.swSi = swSi;
    }

    public boolean isSwNo() {
        return swNo;
    }

    public void setSwNo(boolean swNo) {
        this.swNo = swNo;
    }

    /**
     * @return the fraccionesDetalleList
     */
    public List getFraccionesDetalleList() {
        return fraccionesDetalleList;
    }

    /**
     * @param fraccionesDetalleList the fraccionesDetalleList to set
     */
    public void setFraccionesDetalleList(List fraccionesDetalleList) {
        this.fraccionesDetalleList = fraccionesDetalleList;
    }

    public int getNroDecimalesAlmacen() {
        return nroDecimalesAlmacen;
    }

    public void setNroDecimalesAlmacen(int nroDecimalesAlmacen) {
        this.nroDecimalesAlmacen = nroDecimalesAlmacen;
    }
    
    
}
