/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.bean;

/**
 *
 * @author hvaldivia
 */
public class SolicitudesCompraDetalle {
    SolicitudesCompra solicitudesCompra = new SolicitudesCompra();
    Materiales materiales = new Materiales();
    float cantSolicitada = 0;
    UnidadesMedida unidadesMedida = new UnidadesMedida();
    String obsMaterialSolicitud = "";

    public float getCantSolicitada() {
        return cantSolicitada;
    }

    public void setCantSolicitada(float cantSolicitada) {
        this.cantSolicitada = cantSolicitada;
    }

    public Materiales getMateriales() {
        return materiales;
    }

    public void setMateriales(Materiales materiales) {
        this.materiales = materiales;
    }

    public String getObsMaterialSolicitud() {
        return obsMaterialSolicitud;
    }

    public void setObsMaterialSolicitud(String obsMaterialSolicitud) {
        this.obsMaterialSolicitud = obsMaterialSolicitud;
    }

    public SolicitudesCompra getSolicitudesCompra() {
        return solicitudesCompra;
    }

    public void setSolicitudesCompra(SolicitudesCompra solicitudesCompra) {
        this.solicitudesCompra = solicitudesCompra;
    }

    public UnidadesMedida getUnidadesMedida() {
        return unidadesMedida;
    }

    public void setUnidadesMedida(UnidadesMedida unidadesMedida) {
        this.unidadesMedida = unidadesMedida;
    }

    

}
