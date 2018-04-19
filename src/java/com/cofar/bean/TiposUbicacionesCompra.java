/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.bean;

/**
 *
 * @author hvaldivia
 */
public class TiposUbicacionesCompra {
    int codTipoUbicacion = 0;
    int codTipoTransporte = 0;
    String nombreTipoUbicacion = "";
    int ordenUbicacion = 0;
    String obsTipoUbicacion = "";
    EstadoReferencial estadoReferencial = new EstadoReferencial();

    public int getCodTipoTransporte() {
        return codTipoTransporte;
    }

    public void setCodTipoTransporte(int codTipoTransporte) {
        this.codTipoTransporte = codTipoTransporte;
    }

    public int getCodTipoUbicacion() {
        return codTipoUbicacion;
    }

    public void setCodTipoUbicacion(int codTipoUbicacion) {
        this.codTipoUbicacion = codTipoUbicacion;
    }

    public EstadoReferencial getEstadoReferencial() {
        return estadoReferencial;
    }

    public void setEstadoReferencial(EstadoReferencial estadoReferencial) {
        this.estadoReferencial = estadoReferencial;
    }

    public String getNombreTipoUbicacion() {
        return nombreTipoUbicacion;
    }

    public void setNombreTipoUbicacion(String nombreTipoUbicacion) {
        this.nombreTipoUbicacion = nombreTipoUbicacion;
    }

    public String getObsTipoUbicacion() {
        return obsTipoUbicacion;
    }

    public void setObsTipoUbicacion(String obsTipoUbicacion) {
        this.obsTipoUbicacion = obsTipoUbicacion;
    }

    public int getOrdenUbicacion() {
        return ordenUbicacion;
    }

    public void setOrdenUbicacion(int ordenUbicacion) {
        this.ordenUbicacion = ordenUbicacion;
    }

    

}
