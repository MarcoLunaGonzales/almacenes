/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.bean;

/**
 *
 * @author sistemas1
 */
public class DivisionCompras extends AbstractBean {
        int codDivision = 0;
        String nombreDivision = "";

    public int getCodDivision() {
        return codDivision;
    }

    public void setCodDivision(int codDivision) {
        this.codDivision = codDivision;
    }

    public String getNombreDivision() {
        return nombreDivision;
    }

    public void setNombreDivision(String nombreDivision) {
        this.nombreDivision = nombreDivision;
    }

        
}
