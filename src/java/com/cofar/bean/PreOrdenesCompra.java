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
public class PreOrdenesCompra {
    int codPreOrdenCompra = 0;
    Gestiones gestion = new Gestiones();
    Proveedores proveedores = new Proveedores();
    Monedas monedas = new Monedas();
    int codEstadoPreOrdenCompra = 0;
    String division = "";
    Date fechaEmision = new Date();
    int tiempoEntregaDias = 0;
    int tiempoEntregaMeses = 0;
    int codCondicionPrecio = 0;
    TiposPago tiposPago = new TiposPago();
    TiposTransporte tiposTransporte = new TiposTransporte();
    String obsPreOrden = "";

    public int getCodCondicionPrecio() {
        return codCondicionPrecio;
    }

    public void setCodCondicionPrecio(int codCondicionPrecio) {
        this.codCondicionPrecio = codCondicionPrecio;
    }

    public int getCodEstadoPreOrdenCompra() {
        return codEstadoPreOrdenCompra;
    }

    public void setCodEstadoPreOrdenCompra(int codEstadoPreOrdenCompra) {
        this.codEstadoPreOrdenCompra = codEstadoPreOrdenCompra;
    }

    public int getCodPreOrdenCompra() {
        return codPreOrdenCompra;
    }

    public void setCodPreOrdenCompra(int codPreOrdenCompra) {
        this.codPreOrdenCompra = codPreOrdenCompra;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public Gestiones getGestion() {
        return gestion;
    }

    public void setGestion(Gestiones gestion) {
        this.gestion = gestion;
    }

    public Monedas getMonedas() {
        return monedas;
    }

    public void setMonedas(Monedas monedas) {
        this.monedas = monedas;
    }

    public String getObsPreOrden() {
        return obsPreOrden;
    }

    public void setObsPreOrden(String obsPreOrden) {
        this.obsPreOrden = obsPreOrden;
    }

    public Proveedores getProveedores() {
        return proveedores;
    }

    public void setProveedores(Proveedores proveedores) {
        this.proveedores = proveedores;
    }

    public int getTiempoEntregaDias() {
        return tiempoEntregaDias;
    }

    public void setTiempoEntregaDias(int tiempoEntregaDias) {
        this.tiempoEntregaDias = tiempoEntregaDias;
    }

    public int getTiempoEntregaMeses() {
        return tiempoEntregaMeses;
    }

    public void setTiempoEntregaMeses(int tiempoEntregaMeses) {
        this.tiempoEntregaMeses = tiempoEntregaMeses;
    }

    public TiposPago getTiposPago() {
        return tiposPago;
    }

    public void setTiposPago(TiposPago tiposPago) {
        this.tiposPago = tiposPago;
    }

    public TiposTransporte getTiposTransporte() {
        return tiposTransporte;
    }

    public void setTiposTransporte(TiposTransporte tiposTransporte) {
        this.tiposTransporte = tiposTransporte;
    }





}
