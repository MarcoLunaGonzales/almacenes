/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.bean;

/**
 *
 * @author hvaldivia
 */
public class TiposIngresoAlmacen extends AbstractBean{
    int codTipoIngresoAlmacen = 0;
    String nombreTipoIngresoAlmacen = "";
    String obsTipoIngresoAlmacen = "";
    EstadoReferencial estadoReferencial = new EstadoReferencial();
    private boolean editable;
    private boolean automatico;

    public int getCodTipoIngresoAlmacen() {
        return codTipoIngresoAlmacen;
    }

    public void setCodTipoIngresoAlmacen(int codTipoIngresoAlmacen) {
        this.codTipoIngresoAlmacen = codTipoIngresoAlmacen;
    }

    public EstadoReferencial getEstadoReferencial() {
        return estadoReferencial;
    }

    public void setEstadoReferencial(EstadoReferencial estadoReferencial) {
        this.estadoReferencial = estadoReferencial;
    }

    public String getNombreTipoIngresoAlmacen() {
        return nombreTipoIngresoAlmacen;
    }

    public void setNombreTipoIngresoAlmacen(String nombreTipoIngresoAlmacen) {
        this.nombreTipoIngresoAlmacen = nombreTipoIngresoAlmacen;
    }

    public String getObsTipoIngresoAlmacen() {
        return obsTipoIngresoAlmacen;
    }

    public void setObsTipoIngresoAlmacen(String obsTipoIngresoAlmacen) {
        this.obsTipoIngresoAlmacen = obsTipoIngresoAlmacen;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public boolean isAutomatico() {
        return automatico;
    }

    public void setAutomatico(boolean automatico) {
        this.automatico = automatico;
    }

    

}
