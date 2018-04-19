/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.bean;

/**
 *
 * @author sistemas1
 */
public class EstadosCompra {
    int codEstadoCompra=0;
    String nombreEstadoCompra = "";
    String nombreEstadoCompraAlmacen = "";
    String obsEstadoCompra = "";
    int codEstadoRegistro =0;
    String abreviatura = "";

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    public int getCodEstadoCompra() {
        return codEstadoCompra;
    }

    public void setCodEstadoCompra(int codEstadoCompra) {
        this.codEstadoCompra = codEstadoCompra;
    }

    public int getCodEstadoRegistro() {
        return codEstadoRegistro;
    }

    public void setCodEstadoRegistro(int codEstadoRegistro) {
        this.codEstadoRegistro = codEstadoRegistro;
    }

    public String getNombreEstadoCompra() {
        return nombreEstadoCompra;
    }

    public void setNombreEstadoCompra(String nombreEstadoCompra) {
        this.nombreEstadoCompra = nombreEstadoCompra;
    }

    public String getNombreEstadoCompraAlmacen() {
        return nombreEstadoCompraAlmacen;
    }

    public void setNombreEstadoCompraAlmacen(String nombreEstadoCompraAlmacen) {
        this.nombreEstadoCompraAlmacen = nombreEstadoCompraAlmacen;
    }

    public String getObsEstadoCompra() {
        return obsEstadoCompra;
    }

    public void setObsEstadoCompra(String obsEstadoCompra) {
        this.obsEstadoCompra = obsEstadoCompra;
    }

    
}
