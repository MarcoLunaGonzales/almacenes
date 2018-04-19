/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofar.bean;

/**
 *
 * @author rergueta
 */
public class FormulaMaestraDetalleMPfracciones {

    private FormulaMaestra formulaMaestra = new FormulaMaestra();
    private Materiales materiales = new Materiales();
    private String codFormulaMaestraFracciones = "0";
    private double cantidad = 0d;
    private int rows = 0;

    /**
     * @return the formulaMaestra
     */
    public FormulaMaestra getFormulaMaestra() {
        return formulaMaestra;
    }

    /**
     * @param formulaMaestra the formulaMaestra to set
     */
    public void setFormulaMaestra(FormulaMaestra formulaMaestra) {
        this.formulaMaestra = formulaMaestra;
    }

    /**
     * @return the materiales
     */
    public Materiales getMateriales() {
        return materiales;
    }

    /**
     * @param materiales the materiales to set
     */
    public void setMateriales(Materiales materiales) {
        this.materiales = materiales;
    }

    /**
     * @return the codFormulaMaestraFracciones
     */
    public String getCodFormulaMaestraFracciones() {
        return codFormulaMaestraFracciones;
    }

    /**
     * @param codFormulaMaestraFracciones the codFormulaMaestraFracciones to set
     */
    public void setCodFormulaMaestraFracciones(String codFormulaMaestraFracciones) {
        this.codFormulaMaestraFracciones = codFormulaMaestraFracciones;
    }

    /**
     * @return the cantidad
     */
    public double getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * @return the rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * @param rows the rows to set
     */
    public void setRows(int rows) {
        this.rows = rows;
    }
}
