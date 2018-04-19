/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author hvaldivia
 */
public class KardexMovimiento {
    Almacenes almacenes = new Almacenes();
    Materiales materiales = new Materiales();
    Date fechaInicio = new Date();
    Date fechaFinal = new Date();
    double saldo = 0;
    double monto = 0;
    double ufv = 0;
    double costoUnitario = 0;
    List kardexItemMovimientoList = new ArrayList();

    public Almacenes getAlmacenes() {
        return almacenes;
    }

    public void setAlmacenes(Almacenes almacenes) {
        this.almacenes = almacenes;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public List getKardexItemMovimientoList() {
        return kardexItemMovimientoList;
    }

    public void setKardexItemMovimientoList(List kardexItemMovimientoList) {
        this.kardexItemMovimientoList = kardexItemMovimientoList;
    }

    public Materiales getMateriales() {
        return materiales;
    }

    public void setMateriales(Materiales materiales) {
        this.materiales = materiales;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public double getUfv() {
        return ufv;
    }

    public void setUfv(double ufv) {
        this.ufv = ufv;
    }

    public double getCostoUnitario() {
        return costoUnitario;
    }

    public void setCostoUnitario(double costoUnitario) {
        this.costoUnitario = costoUnitario;
    }

    
    

}
