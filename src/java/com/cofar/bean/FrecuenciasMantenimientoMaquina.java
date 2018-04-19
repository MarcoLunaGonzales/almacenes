/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.bean;

/**
 *
 * @author sistemas1
 */
public class FrecuenciasMantenimientoMaquina extends AbstractBean {
    int codFrecuencia = 0;
    Maquinaria maquinaria = new Maquinaria();
    TiposPeriodo tiposPeriodo = new TiposPeriodo();
    float horasFrecuencia = 0f;

    public int getCodFrecuencia() {
        return codFrecuencia;
    }

    public void setCodFrecuencia(int codFrecuencia) {
        this.codFrecuencia = codFrecuencia;
    }

    public float getHorasFrecuencia() {
        return horasFrecuencia;
    }

    public void setHorasFrecuencia(float horasFrecuencia) {
        this.horasFrecuencia = horasFrecuencia;
    }

    public Maquinaria getMaquinaria() {
        return maquinaria;
    }

    public void setMaquinaria(Maquinaria maquinaria) {
        this.maquinaria = maquinaria;
    }

    public TiposPeriodo getTiposPeriodo() {
        return tiposPeriodo;
    }

    public void setTiposPeriodo(TiposPeriodo tiposPeriodo) {
        this.tiposPeriodo = tiposPeriodo;
    }

}
