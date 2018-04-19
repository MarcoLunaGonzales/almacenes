/*
 * LineaMKT.java
 *
 * Created on 21 de abril de 2008, 10:14 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.cofar.bean;

/**
 *
 * @author Osmar Hinojosa Miranda
 */
public class PlanDeCuentas {
    
    /** Creates a new instance of LineaMKT */
    private int codPlanCuenta = 0;
    private String codPlanCuentaPadre="";
    private String codCuenta="";
    private String codCuentaPadre="";
    private String nombreCuenta="";
    private Monedas monedas=new Monedas();
    private EstadoReferencial estadoReferencial=new EstadoReferencial();    
    private Boolean estadoCuentas=new Boolean(false);
    private Boolean ajustable=new Boolean(false);
    private Boolean movimiento=new Boolean(false);
    private Boolean costos=new Boolean(false);
    private Boolean checked=new Boolean(false);
    private int nivel=0;
    private int distribucionGasto=0;
    private EstadosFlujo estadosFlujo=new EstadosFlujo();
    private String descripcion ="";
    private int estadoCronos = 0;
    private DivisionCompras divisionCompras = new DivisionCompras();

    public int getCodPlanCuenta() {
        return codPlanCuenta;
    }

    public void setCodPlanCuenta(int codPlanCuenta) {
        this.codPlanCuenta = codPlanCuenta;
    }
    
    public String getCodCuenta() {
        return codCuenta;
    }

    public void setCodCuenta(String codCuenta) {
        this.codCuenta = codCuenta;
    }

    public String getNombreCuenta() {
        return nombreCuenta;
    }

    public void setNombreCuenta(String nombreCuenta) {
        this.nombreCuenta = nombreCuenta;
    }

    public Monedas getMonedas() {
        return monedas;
    }

    public void setMonedas(Monedas monedas) {
        this.monedas = monedas;
    }

    public EstadoReferencial getEstadoReferencial() {
        return estadoReferencial;
    }

    public void setEstadoReferencial(EstadoReferencial estadoReferencial) {
        this.estadoReferencial = estadoReferencial;
    }
    
    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    
    public Boolean getAjustable() {
        return ajustable;
    }

    public void setAjustable(Boolean ajustable) {
        this.ajustable = ajustable;
    }

    public Boolean getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(Boolean movimiento) {
        this.movimiento = movimiento;
    }

    public Boolean getCostos() {
        return costos;
    }

    public void setCostos(Boolean costos) {
        this.costos = costos;
    }

    public String getCodPlanCuentaPadre() {
        return codPlanCuentaPadre;
    }

    public void setCodPlanCuentaPadre(String codPlanCuentaPadre) {
        this.codPlanCuentaPadre = codPlanCuentaPadre;
    }

    public String getCodCuentaPadre() {
        return codCuentaPadre;
    }

    public void setCodCuentaPadre(String codCuentaPadre) {
        this.codCuentaPadre = codCuentaPadre;
    }

    public Boolean getEstadoCuentas() {
        return estadoCuentas;
    }

    public void setEstadoCuentas(Boolean estadoCuentas) {
        this.estadoCuentas = estadoCuentas;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    /**
     * @return the distribucionGasto
     */
    public int getDistribucionGasto() {
        return distribucionGasto;
    }

    /**
     * @param distribucionGasto the distribucionGasto to set
     */
    public void setDistribucionGasto(int distribucionGasto) {
        this.distribucionGasto = distribucionGasto;
    }

    /**
     * @return the estadosFlujo
     */
    public EstadosFlujo getEstadosFlujo() {
        return estadosFlujo;
    }

    /**
     * @param estadosFlujo the estadosFlujo to set
     */
    public void setEstadosFlujo(EstadosFlujo estadosFlujo) {
        this.estadosFlujo = estadosFlujo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getEstadoCronos() {
        return estadoCronos;
    }

    public void setEstadoCronos(int estadoCronos) {
        this.estadoCronos = estadoCronos;
    }

    public DivisionCompras getDivisionCompras() {
        return divisionCompras;
    }

    public void setDivisionCompras(DivisionCompras divisionCompras) {
        this.divisionCompras = divisionCompras;
    }

   
    

}
