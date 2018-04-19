/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.bean;

/**
 *
 * @author hvaldivia
 */
public class Equivalencias extends AbstractBean{
    UnidadesMedida unidadesMedida1 = new UnidadesMedida();
    UnidadesMedida unidadesMedida2 = new UnidadesMedida();
    float valorEquivalencia = 0;
    EstadoReferencial estadoReferencial = new EstadoReferencial();

    public EstadoReferencial getEstadoReferencial() {
        return estadoReferencial;
    }

    public void setEstadoReferencial(EstadoReferencial estadoReferencial) {
        this.estadoReferencial = estadoReferencial;
    }

    public UnidadesMedida getUnidadesMedida1() {
        return unidadesMedida1;
    }

    public void setUnidadesMedida1(UnidadesMedida unidadesMedida1) {
        this.unidadesMedida1 = unidadesMedida1;
    }

    public UnidadesMedida getUnidadesMedida2() {
        return unidadesMedida2;
    }

    public void setUnidadesMedida2(UnidadesMedida unidadesMedida2) {
        this.unidadesMedida2 = unidadesMedida2;
    }

    public float getValorEquivalencia() {
        return valorEquivalencia;
    }

    public void setValorEquivalencia(float valorEquivalencia) {
        this.valorEquivalencia = valorEquivalencia;
    }

    

    
    

}
