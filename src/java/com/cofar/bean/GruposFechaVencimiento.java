/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofar.bean;

/**
 *
 * @author DASISAQ
 */
public class GruposFechaVencimiento {
    private Boolean aplicaFechaVencimiento = false;
    // no declarar instancia de grupo ya que provoca perm space
    public GruposFechaVencimiento() {
    }

    public Boolean getAplicaFechaVencimiento() {
        return aplicaFechaVencimiento;
    }

    public void setAplicaFechaVencimiento(Boolean aplicaFechaVencimiento) {
        this.aplicaFechaVencimiento = aplicaFechaVencimiento;
    }
    
    
}
