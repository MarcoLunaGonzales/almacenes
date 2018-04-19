/*
 * Producto.java
 *
 * Created on 19 de marzo de 2008, 04:38 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.cofar.bean;

import java.util.Date;

/**
 *
 * @author Rene  Ergueta Illanes
 * @company COFAR
 */
public class Producto extends AbstractBean{    
    /** Creates a new instance of Producto */
    private String codProducto="";
    private String nombreProducto="";
    private String prodControlado="";      
    private String loteMinimoProd="";
    private String loteMaximoProd="";
    private FormasFarmaceuticas formafarmaceutica=new FormasFarmaceuticas();
    private String rnProd="";
    private Date vigenciaProd=new Date();
    private String vigencia="";
    private String expiracion="";
    private Date expiracionProd=new Date();        
    private String tamSubLote="";
    private String tamanoLoteProd="";
    private String obsProd="";
    private EstadoProducto estadoProducto=new EstadoProducto();
    private Boolean checked=new Boolean(false);    
    
    public Producto() {
    }

    public String getCodProducto() {
        return codProducto;
    }

    public void setCodProducto(String codProducto) {
        this.codProducto = codProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getProdControlado() {
        return prodControlado;
    }

    public void setProdControlado(String prodControlado) {
        this.prodControlado = prodControlado;
    }   

    public String getLoteMinimoProd() {
        return loteMinimoProd;
    }

    public void setLoteMinimoProd(String loteMinimoProd) {
        this.loteMinimoProd = loteMinimoProd;
    }

    public String getLoteMaximoProd() {
        return loteMaximoProd;
    }

    public void setLoteMaximoProd(String loteMaximoProd) {
        this.loteMaximoProd = loteMaximoProd;
    }

    public String getTamSubLote() {
        return tamSubLote;
    }

    public void setTamSubLote(String tamSubLote) {
        this.tamSubLote = tamSubLote;
    }

    public String getTamanoLoteProd() {
        return tamanoLoteProd;
    }

    public void setTamanoLoteProd(String tamanoLoteProd) {
        this.tamanoLoteProd = tamanoLoteProd;
    }

    public String getObsProd() {
        return obsProd;
    }

    public void setObsProd(String obsProd) {
        this.obsProd = obsProd;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public FormasFarmaceuticas getFormafarmaceutica() {
        return formafarmaceutica;
    }

    public void setFormafarmaceutica(FormasFarmaceuticas formafarmaceutica) {
        this.formafarmaceutica = formafarmaceutica;
    }

    public String getRnProd() {
        return rnProd;
    }

    public void setRnProd(String rnProd) {
        this.rnProd = rnProd;
    }   

    public Date getVigenciaProd() {
        return vigenciaProd;
    }

    public void setVigenciaProd(Date vigenciaProd) {
        this.vigenciaProd = vigenciaProd;
    }

    public Date getExpiracionProd() {
        return expiracionProd;
    }

    public void setExpiracionProd(Date expiracionProd) {
        this.expiracionProd = expiracionProd;
    }  

    public EstadoProducto getEstadoProducto() {
        return estadoProducto;
    }

    public void setEstadoProducto(EstadoProducto estadoProducto) {
        this.estadoProducto = estadoProducto;
    }

    public String getVigencia() {
        return vigencia;
    }

    public void setVigencia(String vigencia) {
        this.vigencia = vigencia;
    }

    public String getExpiracion() {
        return expiracion;
    }

    public void setExpiracion(String expiracion) {
        this.expiracion = expiracion;
    }
}
