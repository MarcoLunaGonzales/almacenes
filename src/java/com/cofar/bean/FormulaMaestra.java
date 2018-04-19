/*
 * ComponentesProd.java
 *
 * Created on 25 de mayo de 2008, 19:26
 */

package com.cofar.bean;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Wilson Choquehuanca Gonzales
 * @company COFAR
 */
public class FormulaMaestra extends AbstractBean {
    
    /** Creates a new instance of ComponentesProd */
    private String codFormulaMaestra="";
    private ComponentesProd componentesProd=new ComponentesProd();
    private String cantidadLote="";
    private String codEstadoSistema="";
    private EstadoReferencial estadoRegistro=new EstadoReferencial();

    public String getCodFormulaMaestra() {
        return codFormulaMaestra;
    }

    public void setCodFormulaMaestra(String codFormulaMaestra) {
        this.codFormulaMaestra = codFormulaMaestra;
    }

    public ComponentesProd getComponentesProd() {
        return componentesProd;
    }

    public void setComponentesProd(ComponentesProd componentesProd) {
        this.componentesProd = componentesProd;
    }

    public String getCantidadLote() {
        return cantidadLote;
    }

    public void setCantidadLote(String cantidadLote) {
        this.cantidadLote = cantidadLote;
    }

    public String getCodEstadoSistema() {
        return codEstadoSistema;
    }

    public void setCodEstadoSistema(String codEstadoSistema) {
        this.codEstadoSistema = codEstadoSistema;
    }

    public EstadoReferencial getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(EstadoReferencial estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }
    
    
}
