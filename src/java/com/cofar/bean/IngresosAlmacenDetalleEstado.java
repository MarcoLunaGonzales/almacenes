/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.bean;

import java.util.Date;
import java.util.List;
import javax.faces.model.SelectItem;

/**
 *
 * @author hvaldivia
 */
public class IngresosAlmacenDetalleEstado extends AbstractBean implements Cloneable{
    IngresosAlmacen ingresosAlmacen = new IngresosAlmacen();
    IngresosAlmacenDetalle ingresosAlmacenDetalle = new IngresosAlmacenDetalle();
    Materiales materiales = new Materiales();
    EstadosMaterial estadosMaterial = new EstadosMaterial();
    int etiqueta = 0;
    private EmpaqueSecundarioExterno empaqueSecundarioExterno = new EmpaqueSecundarioExterno();
    float cantidadParcial = 0;
    float cantidadRestante = 0;
    Date fechaVencimiento = new Date();
    String loteMaterialProveedor = "";
    String loteInterno = "";
    Date fechaManufactura = new Date();
    String observaciones = "";
    int obsControlCalidad = 0;
    Date fechaVencimiento1 = new Date();
    Date fechaReanalisis1 = new Date();
    Date fechaVencimiento2 = new Date();
    Date fechaReanalisis2 = new Date();
    Date fechaReanalisis = new Date();
    float tara = 0;
    boolean conFechaVencimiento = false;
    boolean conFechaReanalisis = false;
    EstanteAlmacen estanteAlmacen = new EstanteAlmacen();
    String fila = "";
    String columna = "";
    float densidad = 0;
    private IngresosAlmacenDetalleEstadoFechaVencimiento ingresosAlmacenDetalleEstadoFechaVencimiento;
    //para almacenar fechas de vencimiento
    private String fechaVencimientoFormatoMMYY="";
    
    //variable lote encontrado ingreso anterior
    private boolean loteProveedorEncontradoIngresoAnterior = false;
    //variable listar posible estado lote material encontrado anterior
    private List<SelectItem> estadosMaterialSelectList;
    private List<SelectItem> estantesSelectList;
    
    public IngresosAlmacenDetalleEstado() {
    }

    
    public float getCantidadParcial() {
        return cantidadParcial;
    }

    public void setCantidadParcial(float cantidadParcial) {
        this.cantidadParcial = cantidadParcial;
    }

    public float getCantidadRestante() {
        return cantidadRestante;
    }

    public void setCantidadRestante(float cantidadRestante) {
        this.cantidadRestante = cantidadRestante;
    }

    public EmpaqueSecundarioExterno getEmpaqueSecundarioExterno() {
        return empaqueSecundarioExterno;
    }

    public void setEmpaqueSecundarioExterno(EmpaqueSecundarioExterno empaqueSecundarioExterno) {
        this.empaqueSecundarioExterno = empaqueSecundarioExterno;
    }

    public EstadosMaterial getEstadosMaterial() {
        return estadosMaterial;
    }

    public void setEstadosMaterial(EstadosMaterial estadosMaterial) {
        this.estadosMaterial = estadosMaterial;
    }

    public int getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(int etiqueta) {
        this.etiqueta = etiqueta;
    }

    public Date getFechaManufactura() {
        return fechaManufactura;
    }

    public void setFechaManufactura(Date fechaManufactura) {
        this.fechaManufactura = fechaManufactura;
    }


    public Date getFechaReanalisis1() {
        return fechaReanalisis1;
    }

    public void setFechaReanalisis1(Date fechaReanalisis1) {
        this.fechaReanalisis1 = fechaReanalisis1;
    }

    public Date getFechaReanalisis2() {
        return fechaReanalisis2;
    }

    public void setFechaReanalisis2(Date fechaReanalisis2) {
        this.fechaReanalisis2 = fechaReanalisis2;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public Date getFechaVencimiento1() {
        return fechaVencimiento1;
    }

    public void setFechaVencimiento1(Date fechaVencimiento1) {
        this.fechaVencimiento1 = fechaVencimiento1;
    }

    public Date getFechaVencimiento2() {
        return fechaVencimiento2;
    }

    public void setFechaVencimiento2(Date fechaVencimiento2) {
        this.fechaVencimiento2 = fechaVencimiento2;
    }

    public IngresosAlmacen getIngresosAlmacen() {
        return ingresosAlmacen;
    }

    public void setIngresosAlmacen(IngresosAlmacen ingresosAlmacen) {
        this.ingresosAlmacen = ingresosAlmacen;
    }

    public String getLoteInterno() {
        return loteInterno;
    }

    public void setLoteInterno(String loteInterno) {
        this.loteInterno = loteInterno;
    }

    public String getLoteMaterialProveedor() {
        return loteMaterialProveedor;
    }

    public void setLoteMaterialProveedor(String loteMaterialProveedor) {
        this.loteMaterialProveedor = loteMaterialProveedor;
    }

    public Materiales getMateriales() {
        return materiales;
    }

    public void setMateriales(Materiales materiales) {
        this.materiales = materiales;
    }

    public int getObsControlCalidad() {
        return obsControlCalidad;
    }

    public void setObsControlCalidad(int obsControlCalidad) {
        this.obsControlCalidad = obsControlCalidad;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public IngresosAlmacenDetalle getIngresosAlmacenDetalle() {
        return ingresosAlmacenDetalle;
    }

    public void setIngresosAlmacenDetalle(IngresosAlmacenDetalle ingresosAlmacenDetalle) {
        this.ingresosAlmacenDetalle = ingresosAlmacenDetalle;
    }

    public Date getFechaReanalisis() {
        return fechaReanalisis;
    }

    public void setFechaReanalisis(Date fechaReanalisis) {
        this.fechaReanalisis = fechaReanalisis;
    }
    

    public float getTara() {
        return tara;
    }

    public void setTara(float tara) {
        this.tara = tara;
    }

    public boolean getConFechaReanalisis() {
        return conFechaReanalisis;
    }

    public void setConFechaReanalisis(boolean conFechaReanalisis) {
        this.conFechaReanalisis = conFechaReanalisis;
    }

    public boolean getConFechaVencimiento() {
        return conFechaVencimiento;
    }

    public void setConFechaVencimiento(boolean conFechaVencimiento) {
        this.conFechaVencimiento = conFechaVencimiento;
    }

    public EstanteAlmacen getEstanteAlmacen() {
        return estanteAlmacen;
    }

    public void setEstanteAlmacen(EstanteAlmacen estanteAlmacen) {
        this.estanteAlmacen = estanteAlmacen;
    }

    public String getColumna() {
        return columna;
    }

    public void setColumna(String columna) {
        this.columna = columna;
    }

    public String getFila() {
        return fila;
    }

    public void setFila(String fila) {
        this.fila = fila;
    }

    public float getDensidad() {
        return densidad;
    }

    public void setDensidad(float densidad) {
        this.densidad = densidad;
    }

    public IngresosAlmacenDetalleEstadoFechaVencimiento getIngresosAlmacenDetalleEstadoFechaVencimiento() {
        return ingresosAlmacenDetalleEstadoFechaVencimiento;
    }

    public void setIngresosAlmacenDetalleEstadoFechaVencimiento(IngresosAlmacenDetalleEstadoFechaVencimiento ingresosAlmacenDetalleEstadoFechaVencimiento) {
        this.ingresosAlmacenDetalleEstadoFechaVencimiento = ingresosAlmacenDetalleEstadoFechaVencimiento;
    }
    
    private float cantidadRestanteValorado;
    private float valoracionCCPorcentual;

    public float getCantidadRestanteValorado() {
        return cantidadRestanteValorado;
    }

    public void setCantidadRestanteValorado(float cantidadRestanteValorado) {
        this.cantidadRestanteValorado = cantidadRestanteValorado;
    }

    public float getValoracionCCPorcentual() {
        return valoracionCCPorcentual;
    }

    public void setValoracionCCPorcentual(float valoracionCCPorcentual) {
        this.valoracionCCPorcentual = valoracionCCPorcentual;
    }

    public boolean isLoteProveedorEncontradoIngresoAnterior() {
        return loteProveedorEncontradoIngresoAnterior;
    }

    public void setLoteProveedorEncontradoIngresoAnterior(boolean loteProveedorEncontradoIngresoAnterior) {
        this.loteProveedorEncontradoIngresoAnterior = loteProveedorEncontradoIngresoAnterior;
    }

    public List<SelectItem> getEstadosMaterialSelectList() {
        return estadosMaterialSelectList;
    }

    public void setEstadosMaterialSelectList(List<SelectItem> estadosMaterialSelectList) {
        this.estadosMaterialSelectList = estadosMaterialSelectList;
    }

    public List<SelectItem> getEstantesSelectList() {
        return estantesSelectList;
    }

    public void setEstantesSelectList(List<SelectItem> estantesSelectList) {
        this.estantesSelectList = estantesSelectList;
    }

    public String getFechaVencimientoFormatoMMYY() {
        return fechaVencimientoFormatoMMYY;
    }

    public void setFechaVencimientoFormatoMMYY(String fechaVencimientoFormatoMMYY) {
        this.fechaVencimientoFormatoMMYY = fechaVencimientoFormatoMMYY;
    }

    @Override
    public IngresosAlmacenDetalleEstado clone() throws CloneNotSupportedException
    {
        IngresosAlmacenDetalleEstado clone = null;
        try
        {
            clone = (IngresosAlmacenDetalleEstado)super.clone();
        }
        catch(CloneNotSupportedException ex)
        {
            System.out.println("no se puede clonar");
        }
        clone.setEmpaqueSecundarioExterno(this.empaqueSecundarioExterno.clone());
        clone.setEstanteAlmacen(this.estanteAlmacen.clone());
        clone.setEstadosMaterial(this.estadosMaterial.clone());
        return clone;
    }
    
}
