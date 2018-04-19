/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.bean;

/**
 *
 * @author hvaldivia
 */
public class OrdenesCompraDetalle {
    OrdenesCompra ordenesCompra = new OrdenesCompra();
    int codOrdenCompraDetalle = 0;
    Materiales materiales = new Materiales();
    float cantidadNeta = 0;
    UnidadesMedida unidadesMedida = new UnidadesMedida();
    float precioUnitario = 0;
    float cantidadIngresoAlmacen = 0;
    float precioTotal = 0;
    String descripcion = "";
    int materialAlmacen = 0;
    
    //variable de apoyo
    //Es la SUMA de las cantidades de Los Detalles de Ingresos relacionados a la OC
    private float sumaTotalCantidadesIngresosDetalles = 0;
    private float sumaParcialCantidadesIngresosDetalles = 0;

    public float getCantidadIngresoAlmacen() {
        return cantidadIngresoAlmacen;
    }

    public void setCantidadIngresoAlmacen(float cantidadIngresoAlmacen) {
        this.cantidadIngresoAlmacen = cantidadIngresoAlmacen;
    }

    public float getCantidadNeta() {
        return cantidadNeta;
    }

    public void setCantidadNeta(float cantidadNeta) {
        this.cantidadNeta = cantidadNeta;
    }

    public int getCodOrdenCompraDetalle() {
        return codOrdenCompraDetalle;
    }

    public void setCodOrdenCompraDetalle(int codOrdenCompraDetalle) {
        this.codOrdenCompraDetalle = codOrdenCompraDetalle;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Materiales getMateriales() {
        return materiales;
    }

    public void setMateriales(Materiales materiales) {
        this.materiales = materiales;
    }

    public OrdenesCompra getOrdenesCompra() {
        return ordenesCompra;
    }

    public void setOrdenesCompra(OrdenesCompra ordenesCompra) {
        this.ordenesCompra = ordenesCompra;
    }

    public float getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(float precioTotal) {
        this.precioTotal = precioTotal;
    }

    public float getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(float precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public UnidadesMedida getUnidadesMedida() {
        return unidadesMedida;
    }

    public void setUnidadesMedida(UnidadesMedida unidadesMedida) {
        this.unidadesMedida = unidadesMedida;
    }

    public int getMaterialAlmacen() {
        return materialAlmacen;
    }

    public void setMaterialAlmacen(int materialAlmacen) {
        this.materialAlmacen = materialAlmacen;
    }

    public float getSumaTotalCantidadesIngresosDetalles() {
        return sumaTotalCantidadesIngresosDetalles;
    }

    public void setSumaTotalCantidadesIngresosDetalles(float sumaTotalCantidadesIngresosDetalles) {
        this.sumaTotalCantidadesIngresosDetalles = sumaTotalCantidadesIngresosDetalles;
    }

    public float getSumaParcialCantidadesIngresosDetalles() {
        return sumaParcialCantidadesIngresosDetalles;
    }

    public void setSumaParcialCantidadesIngresosDetalles(float sumaParcialCantidadesIngresosDetalles) {
        this.sumaParcialCantidadesIngresosDetalles = sumaParcialCantidadesIngresosDetalles;
    }


    

    

}
