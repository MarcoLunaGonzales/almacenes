/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.bean;

/**
 *
 * @author hvaldivia
 */
public class Representantes {
    int codRepresentante = 0;
    String nombreRepresentante = "";
    Paises paises = new Paises();
    String ciudadRepresentante = "";
    String direccionRepresentante = "";
    String telefonoRepresentante = "";
    String emailRepresentante = "";
    String faxRepresentante = "";
    String paginaWebRepresentante = "";
    String obsRepresentante = "";
    EstadoReferencial estadoReferencial = new EstadoReferencial();
    int estadoSistema = 0;

    public String getCiudadRepresentante() {
        return ciudadRepresentante;
    }

    public void setCiudadRepresentante(String ciudadRepresentante) {
        this.ciudadRepresentante = ciudadRepresentante;
    }

    public int getCodRepresentante() {
        return codRepresentante;
    }

    public void setCodRepresentante(int codRepresentante) {
        this.codRepresentante = codRepresentante;
    }

    public String getDireccionRepresentante() {
        return direccionRepresentante;
    }

    public void setDireccionRepresentante(String direccionRepresentante) {
        this.direccionRepresentante = direccionRepresentante;
    }

    public String getEmailRepresentante() {
        return emailRepresentante;
    }

    public void setEmailRepresentante(String emailRepresentante) {
        this.emailRepresentante = emailRepresentante;
    }

    public EstadoReferencial getEstadoReferencial() {
        return estadoReferencial;
    }

    public void setEstadoReferencial(EstadoReferencial estadoReferencial) {
        this.estadoReferencial = estadoReferencial;
    }

    public int getEstadoSistema() {
        return estadoSistema;
    }

    public void setEstadoSistema(int estadoSistema) {
        this.estadoSistema = estadoSistema;
    }

    public String getFaxRepresentante() {
        return faxRepresentante;
    }

    public void setFaxRepresentante(String faxRepresentante) {
        this.faxRepresentante = faxRepresentante;
    }

    public String getNombreRepresentante() {
        return nombreRepresentante;
    }

    public void setNombreRepresentante(String nombreRepresentante) {
        this.nombreRepresentante = nombreRepresentante;
    }

    public String getObsRepresentante() {
        return obsRepresentante;
    }

    public void setObsRepresentante(String obsRepresentante) {
        this.obsRepresentante = obsRepresentante;
    }

    public String getPaginaWebRepresentante() {
        return paginaWebRepresentante;
    }

    public void setPaginaWebRepresentante(String paginaWebRepresentante) {
        this.paginaWebRepresentante = paginaWebRepresentante;
    }

    public Paises getPaises() {
        return paises;
    }

    public void setPaises(Paises paises) {
        this.paises = paises;
    }

    public String getTelefonoRepresentante() {
        return telefonoRepresentante;
    }

    public void setTelefonoRepresentante(String telefonoRepresentante) {
        this.telefonoRepresentante = telefonoRepresentante;
    }

    

}
