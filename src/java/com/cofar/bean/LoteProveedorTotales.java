/*
 *To change this license header, choose License Headers in Project Properties.
 *To change this template file, choose Tools | Templates
 *and open the template in the editor.
 */

package com.cofar.bean;

/**
 *
 * @author Jaime Chura
 * @version BACO 2.0
 * date 19-10-2015
 * time 06:31:49 PM
 */
public class LoteProveedorTotales {
    private String codLoteProveedor;
    private float cantidad_total;
    private int cod_material;

    public String getCodLoteProveedor() {
        return codLoteProveedor;
    }

    public void setCodLoteProveedor(String codLoteProveedor) {
        this.codLoteProveedor = codLoteProveedor;
    }

    public float getCantidad_total() {
        return cantidad_total;
    }

    public void setCantidad_total(float cantidad_total) {
        this.cantidad_total = cantidad_total;
    }

    public int getCod_material() {
        return cod_material;
    }

    public void setCod_material(int cod_material) {
        this.cod_material = cod_material;
    }
    private String colorFila="";

    public String getColorFila() {
        return colorFila;
    }

    public void setColorFila(String colorFila) {
        this.colorFila = colorFila;
    }
    private UnidadesMedida unidadMedida=new UnidadesMedida();

    public UnidadesMedida getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(UnidadesMedida unidadMedida) {
        this.unidadMedida = unidadMedida;
    }
    
    private float cantidad_salida;

    public float getCantidad_salida() {
        return cantidad_salida;
    }

    public void setCantidad_salida(float cantidad_salida) {
        this.cantidad_salida = cantidad_salida;
    }
    
}
