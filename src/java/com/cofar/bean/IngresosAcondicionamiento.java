/*
 * IngresosAcondicionamiento.java
 *
 * Created on 19 de marzo de 2008, 11:02
 */

package com.cofar.bean;

import java.util.Date;

/**
 *
 * @author Wilmer Manzaneda chavez
 * @company COFAR
 */
public class IngresosAcondicionamiento extends AbstractBean {
    
    /** Creates a new instance of IngresosAcondicionamiento */
    private String codIngresoAcond="";
    private TipoIngresoAcond tipoIngresoAcond=new TipoIngresoAcond();
    private Gestiones gestion=new Gestiones ();
    private String codEstadoIngresoAcond;
    private Date fechaIngresoAcond=new Date();
    private int nroIngresoAcond=1;
    private AlmacenAcond almacen=new AlmacenAcond();
    private String obsIngresoAcond="";
    private String nroDocIngresoAcond="";
    private String nombreEstadoIngresoAcond="";
    private int idIA=0; 
    private String colorVisibility="";
    private String nombreImg="";
    private EstadosIngresoAcond estadosIngresoAcond = new EstadosIngresoAcond();
    
    
    public IngresosAcondicionamiento() {
    }

    public String getCodIngresoAcond() {
        return codIngresoAcond;
    }

    public void setCodIngresoAcond(String codIngresoAcond) {
        this.codIngresoAcond = codIngresoAcond;
    }

    public TipoIngresoAcond getTipoIngresoAcond() {
        return tipoIngresoAcond;
    }

    public void setTipoIngresoAcond(TipoIngresoAcond tipoIngresoAcond) {
        this.tipoIngresoAcond = tipoIngresoAcond;
    }

    public Gestiones getGestion() {
        return gestion;
    }

    public void setGestion(Gestiones gestion) {
        this.gestion = gestion;
    }

    public String getCodEstadoIngresoAcond() {
        return codEstadoIngresoAcond;
    }

    public void setCodEstadoIngresoAcond(String codEstadoIngresoAcond) {
        this.codEstadoIngresoAcond = codEstadoIngresoAcond;
    }

    public Date getFechaIngresoAcond() {
        return fechaIngresoAcond;
    }

    public void setFechaIngresoAcond(Date fechaIngresoAcond) {
        this.fechaIngresoAcond = fechaIngresoAcond;
    }

    public int getNroIngresoAcond() {
        return nroIngresoAcond;
    }

    public void setNroIngresoAcond(int nroIngresoAcond) {
        this.nroIngresoAcond = nroIngresoAcond;
    }

    public AlmacenAcond getAlmacen() {
        return almacen;
    }

    public void setAlmacen(AlmacenAcond almacen) {
        this.almacen = almacen;
    }

    public String getObsIngresoAcond() {
        return obsIngresoAcond;
    }

    public void setObsIngresoAcond(String obsIngresoAcond) {
        this.obsIngresoAcond = obsIngresoAcond;
    }

    public String getNroDocIngresoAcond() {
        return nroDocIngresoAcond;
    }

    public void setNroDocIngresoAcond(String nroDocIngresoAcond) {
        this.nroDocIngresoAcond = nroDocIngresoAcond;
    }

    public String getNombreEstadoIngresoAcond() {
        return nombreEstadoIngresoAcond;
    }

    public void setNombreEstadoIngresoAcond(String nombreEstadoIngresoAcond) {
        this.nombreEstadoIngresoAcond = nombreEstadoIngresoAcond;
    }

    public int getIdIA() {
        return idIA;
    }

    public void setIdIA(int idIA) {
        this.idIA = idIA;
    }

    public String getColorVisibility() {
        return colorVisibility;
    }

    public void setColorVisibility(String colorVisibility) {
        this.colorVisibility = colorVisibility;
    }

    public String getNombreImg() {
        return nombreImg;
    }

    public void setNombreImg(String nombreImg) {
        this.nombreImg = nombreImg;
    }

    public EstadosIngresoAcond getEstadosIngresoAcond() {
        return estadosIngresoAcond;
    }

    public void setEstadosIngresoAcond(EstadosIngresoAcond estadosIngresoAcond) {
        this.estadosIngresoAcond = estadosIngresoAcond;
    }
    
}
