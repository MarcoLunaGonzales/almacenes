/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.bean;

/**
 *
 * @author aquispe
 */
public class BiMensual extends AbstractBean {

    private int codBiMensual=0;
    private String descripcion="";
    private EstadoReferencial estado=new EstadoReferencial();

    public BiMensual() {
    }

    public int getCodBiMensual() {
        return codBiMensual;
    }

    public void setCodBiMensual(int codBiMensual) {
        this.codBiMensual = codBiMensual;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public EstadoReferencial getEstado() {
        return estado;
    }

    public void setEstado(EstadoReferencial estado) {
        this.estado = estado;
    }
    



}
