/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.bean;

/**
 *
 * @author hvaldivia
 */
public class Grupos extends AbstractBean {
    int codGrupo = 0;
    Capitulos capitulos = new Capitulos();
    String nombreGrupo = "";
    String obsGrupo = "";
    EstadoReferencial estadoReferencial = new EstadoReferencial();
    int grupoAlmacen = 0;
    PlanDeCuentas planDeCuentas = new PlanDeCuentas();
    private GruposFechaVencimiento gruposFechaVencimiento = new GruposFechaVencimiento();
    public Capitulos getCapitulos() {
        return capitulos;
    }

    public void setCapitulos(Capitulos capitulos) {
        this.capitulos = capitulos;
    }

    public int getCodGrupo() {
        return codGrupo;
    }

    public void setCodGrupo(int codGrupo) {
        this.codGrupo = codGrupo;
    }

    public EstadoReferencial getEstadoReferencial() {
        return estadoReferencial;
    }

    public void setEstadoReferencial(EstadoReferencial estadoReferencial) {
        this.estadoReferencial = estadoReferencial;
    }

    public int getGrupoAlmacen() {
        return grupoAlmacen;
    }

    public void setGrupoAlmacen(int grupoAlmacen) {
        this.grupoAlmacen = grupoAlmacen;
    }

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

    public String getObsGrupo() {
        return obsGrupo;
    }

    public void setObsGrupo(String obsGrupo) {
        this.obsGrupo = obsGrupo;
    }

    public PlanDeCuentas getPlanDeCuentas() {
        return planDeCuentas;
    }

    public void setPlanDeCuentas(PlanDeCuentas planDeCuentas) {
        this.planDeCuentas = planDeCuentas;
    }

    public GruposFechaVencimiento getGruposFechaVencimiento() {
        return gruposFechaVencimiento;
    }

    public void setGruposFechaVencimiento(GruposFechaVencimiento gruposFechaVencimiento) {
        this.gruposFechaVencimiento = gruposFechaVencimiento;
    }
    

    

}
