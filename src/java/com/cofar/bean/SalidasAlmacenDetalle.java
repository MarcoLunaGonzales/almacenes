/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.bean;

import com.cofar.web.ManagedBean;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hvaldivia
 */
public class SalidasAlmacenDetalle extends AbstractBean implements Cloneable{

    SalidasAlmacen salidasAlmacen = new SalidasAlmacen();
    Materiales materiales = new Materiales();
    float cantidadSalidaAlmacen = 0;
    UnidadesMedida unidadesMedida = new UnidadesMedida();
    EstadosMaterial estadosMaterial = new EstadosMaterial();
    float cantidadDisponible = 0;
    List salidasAlmacenDetalleIngresoList = new ArrayList();
    Equivalencias equivalencias = new Equivalencias();
    float cantidadSalidaAlmacenEquivalente = 0;
    double cantidadExistenciaReanalisis=0d;//para controlar la cantidad que se encuentra en reanalisis que debe de salir primero

    
    //variables de apoyo
    private IngresosAlmacenDetalle ingresoAlmacenDetalleTraspaso = new IngresosAlmacenDetalle(); //Variable auxililar para manipular datos del ingreso equivalente en el almacen destino

    public float getCantidadSalidaAlmacen() {
        return cantidadSalidaAlmacen;
    }

    public void setCantidadSalidaAlmacen(float cantidadSalidaAlmacen) {
        this.cantidadSalidaAlmacen = cantidadSalidaAlmacen;
    }

    public EstadosMaterial getEstadosMaterial() {
        return estadosMaterial;
    }

    public void setEstadosMaterial(EstadosMaterial estadosMaterial) {
        this.estadosMaterial = estadosMaterial;
    }

    public Materiales getMateriales() {
        return materiales;
    }

    public void setMateriales(Materiales materiales) {
        this.materiales = materiales;
    }

    public SalidasAlmacen getSalidasAlmacen() {
        return salidasAlmacen;
    }

    public void setSalidasAlmacen(SalidasAlmacen salidasAlmacen) {
        this.salidasAlmacen = salidasAlmacen;
    }

    public UnidadesMedida getUnidadesMedida() {
        return unidadesMedida;
    }

    public void setUnidadesMedida(UnidadesMedida unidadesMedida) {
        this.unidadesMedida = unidadesMedida;
    }

    public float getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(float cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }

    public List getSalidasAlmacenDetalleIngresoList() {
        return salidasAlmacenDetalleIngresoList;
    }
    
    public Float getSalidasAlmacenDetalleIngresoListSumaCantidadDetallada() {
        Float cantidadDetallada = 0f;
        if(salidasAlmacenDetalleIngresoList != null){
            for(SalidasAlmacenDetalleIngreso bean  : (List<SalidasAlmacenDetalleIngreso>)salidasAlmacenDetalleIngresoList){
                cantidadDetallada += bean.getCantidadSacar();
            }
        }
        return cantidadDetallada;
    }

    public void setSalidasAlmacenDetalleIngresoList(List salidasAlmacenDetalleIngresoList) {
        this.salidasAlmacenDetalleIngresoList = salidasAlmacenDetalleIngresoList;
    }

    public Equivalencias getEquivalencias() {
        return equivalencias;
    }

    public void setEquivalencias(Equivalencias equivalencias) {
        this.equivalencias = equivalencias;
    }

    public float getCantidadSalidaAlmacenEquivalente() {
        return cantidadSalidaAlmacenEquivalente;
    }

    public void setCantidadSalidaAlmacenEquivalente(float cantidadSalidaAlmacenEquivalente) {
        this.cantidadSalidaAlmacenEquivalente = cantidadSalidaAlmacenEquivalente;
    }
    public Object clone(){
        Object obj=null;
        try{
            obj=super.clone();
        }catch(CloneNotSupportedException ex){
            System.out.println(" no se puede duplicar");
        }
        return obj;
    }

    public double getCantidadExistenciaReanalisis() {
        return cantidadExistenciaReanalisis;
    }

    public void setCantidadExistenciaReanalisis(double cantidadExistenciaReanalisis) {
        this.cantidadExistenciaReanalisis = cantidadExistenciaReanalisis;
    }

    
    private double cantidadSalidaProgProd;

    public double getCantidadSalidaProgProd() {
        return cantidadSalidaProgProd;
    }

    public void setCantidadSalidaProgProd(double cantidadSalidaProgProd) {
        this.cantidadSalidaProgProd = cantidadSalidaProgProd;
    }
    
  
    private int tipo_material;

    public int getTipo_material() {
        return tipo_material;
    }

    public void setTipo_material(int tipo_material) {
        this.tipo_material = tipo_material;
    }

    public IngresosAlmacenDetalle getIngresoAlmacenDetalleTraspaso() {
        return ingresoAlmacenDetalleTraspaso;
    }

    public void setIngresoAlmacenDetalleTraspaso(IngresosAlmacenDetalle ingresoAlmacenDetalleTraspaso) {
        this.ingresoAlmacenDetalleTraspaso = ingresoAlmacenDetalleTraspaso;
    }

}
