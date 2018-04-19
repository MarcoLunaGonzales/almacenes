/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.bean;

/**
 *
 * @author hvaldivia
 */
public class TiposSalidasAlmacen extends AbstractBean{
    int codTipoSalidaAlmacen = 0;
    String nombreTipoSalidaAlmacen = "";
    String obsTipoSalidaAlmacen = "";
    EstadoReferencial estadoReferencial = new EstadoReferencial();
    private boolean editable;
    private boolean automatico;
    private boolean registraProducto;
    private boolean registraPresentacion;
    private boolean registraLote;
    private boolean anulableAutomatico;

    public int getCodTipoSalidaAlmacen() {
        return codTipoSalidaAlmacen;
    }

    public void setCodTipoSalidaAlmacen(int codTipoSalidaAlmacen) {
        this.codTipoSalidaAlmacen = codTipoSalidaAlmacen;
    }

    public EstadoReferencial getEstadoReferencial() {
        return estadoReferencial;
    }

    public void setEstadoReferencial(EstadoReferencial estadoReferencial) {
        this.estadoReferencial = estadoReferencial;
    }

    public String getNombreTipoSalidaAlmacen() {
        return nombreTipoSalidaAlmacen;
    }

    public void setNombreTipoSalidaAlmacen(String nombreTipoSalidaAlmacen) {
        this.nombreTipoSalidaAlmacen = nombreTipoSalidaAlmacen;
    }

    public String getObsTipoSalidaAlmacen() {
        return obsTipoSalidaAlmacen;
    }

    public void setObsTipoSalidaAlmacen(String obsTipoSalidaAlmacen) {
        this.obsTipoSalidaAlmacen = obsTipoSalidaAlmacen;
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

    public boolean isRegistraProducto() {
        return registraProducto;
    }

    public void setRegistraProducto(boolean registraProducto) {
        this.registraProducto = registraProducto;
    }

    public boolean isRegistraPresentacion() {
        return registraPresentacion;
    }

    public void setRegistraPresentacion(boolean registraPresentacion) {
        this.registraPresentacion = registraPresentacion;
    }

    public boolean isRegistraLote() {
        return registraLote;
    }

    public void setRegistraLote(boolean registraLote) {
        this.registraLote = registraLote;
    }

    public boolean isAnulableAutomatico() {
        return anulableAutomatico;
    }

    public void setAnulableAutomatico(boolean anulableAutomatico) {
        this.anulableAutomatico = anulableAutomatico;
    }



}
