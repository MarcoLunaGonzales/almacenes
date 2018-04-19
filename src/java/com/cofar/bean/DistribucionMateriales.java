/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.bean;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hvaldivia
 */
public class DistribucionMateriales {
    Materiales materiales = new Materiales();
    int cantidad =0;
    List distribucionMaterialesPersonal = new ArrayList();

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public List getDistribucionMaterialesPersonal() {
        return distribucionMaterialesPersonal;
    }

    public void setDistribucionMaterialesPersonal(List distribucionMaterialesPersonal) {
        this.distribucionMaterialesPersonal = distribucionMaterialesPersonal;
    }

    public Materiales getMateriales() {
        return materiales;
    }

    public void setMateriales(Materiales materiales) {
        this.materiales = materiales;
    }
    
}
