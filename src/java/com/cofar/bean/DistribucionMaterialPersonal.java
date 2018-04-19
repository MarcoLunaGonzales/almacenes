/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.bean;

import java.util.Date;


/**
 *
 * @author hvaldivia
 */
public class DistribucionMaterialPersonal extends AbstractBean{
     SalidasAlmacen salidasAlmacen = new SalidasAlmacen();
     Personal personal = new Personal();
     Date fechaDistribucion = new Date();
     Personal personalEntrega = new Personal();
     String observaciones = "";
     int cantidadEntregada = 0;

    public int getCantidadEntregada() {
        return cantidadEntregada;
    }

    public void setCantidadEntregada(int cantidadEntregada) {
        this.cantidadEntregada = cantidadEntregada;
    }

    public Date getFechaDistribucion() {
        return fechaDistribucion;
    }

    public void setFechaDistribucion(Date fechaDistribucion) {
        this.fechaDistribucion = fechaDistribucion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Personal getPersonal() {
        return personal;
    }

    public void setPersonal(Personal personal) {
        this.personal = personal;
    }

    public Personal getPersonalEntrega() {
        return personalEntrega;
    }

    public void setPersonalEntrega(Personal personalEntrega) {
        this.personalEntrega = personalEntrega;
    }

    public SalidasAlmacen getSalidasAlmacen() {
        return salidasAlmacen;
    }

    public void setSalidasAlmacen(SalidasAlmacen salidasAlmacen) {
        this.salidasAlmacen = salidasAlmacen;
    }

     
    
    
     
}
