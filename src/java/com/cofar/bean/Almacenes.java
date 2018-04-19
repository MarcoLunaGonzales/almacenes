/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.bean;

/**
 *
 * @author hvaldivia
 */
public class Almacenes extends AbstractBean{
    
    int codAlmacen = 0;
    int codFilial = 0;
    String nombreAlmacen = "";
    String obsAlmacen = "";
    
    Filiales filiales = new Filiales();
    
    EstadoReferencial estadoReferencial = new EstadoReferencial();
    private boolean aplicaGestionUbicaciones = false;
    //para mostrar existencia de materiales en el almacen 
    private Double cantidadExistente=0d;
    
    
    public int getCodAlmacen() {
        return codAlmacen;
    }

    public void setCodAlmacen(int codAlmacen) {
        this.codAlmacen = codAlmacen;
    }

    public int getCodFilial() {
        return codFilial;
    }

    public void setCodFilial(int codFilial) {
        this.codFilial = codFilial;
    }

    public EstadoReferencial getEstadoReferencial() {
        return estadoReferencial;
    }

    public void setEstadoReferencial(EstadoReferencial estadoReferencial) {
        this.estadoReferencial = estadoReferencial;
    }

    public String getNombreAlmacen() {
        return nombreAlmacen;
    }

    public void setNombreAlmacen(String nombreAlmacen) {
        this.nombreAlmacen = nombreAlmacen;
    }

    public String getObsAlmacen() {
        return obsAlmacen;
    }

    public void setObsAlmacen(String obsAlmacen) {
        this.obsAlmacen = obsAlmacen;
    }

    public Filiales getFiliales() {
        return filiales;
    }

    public void setFiliales(Filiales filiales) {
        this.filiales = filiales;
    }

    public Double getCantidadExistente() {
        return cantidadExistente;
    }

    public void setCantidadExistente(Double cantidadExistente) {
        this.cantidadExistente = cantidadExistente;
    }

    public boolean isAplicaGestionUbicaciones() {
        return aplicaGestionUbicaciones;
    }

    public void setAplicaGestionUbicaciones(boolean aplicaGestionUbicaciones) {
        this.aplicaGestionUbicaciones = aplicaGestionUbicaciones;
    }
    
    
    
    

}
