/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.bean;


/**
 *
 * @author sistemas1
 */
public class FormulaMaestraVersion extends FormulaMaestra{
    private int codVersion =  0;
    private int nroVersion = 0;

    public int getCodVersion() {
        return codVersion;
    }

    public void setCodVersion(int codVersion) {
        this.codVersion = codVersion;
    }

    public int getNroVersion() {
        return nroVersion;
    }

    public void setNroVersion(int nroVersion) {
        this.nroVersion = nroVersion;
    }

}
