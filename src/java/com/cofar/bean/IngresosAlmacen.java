/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author hvaldivia
 */
public class IngresosAlmacen extends AbstractBean {
    int codIngresoAlmacen = 0;
    TiposIngresoAlmacen tiposIngresoAlmacen = new TiposIngresoAlmacen();
    OrdenesCompra ordenesCompra = new OrdenesCompra();
    Gestiones gestiones = new Gestiones();
    EstadosIngresoAlmacen estadosIngresoAlmacen = new  EstadosIngresoAlmacen();
    Devoluciones devoluciones = new Devoluciones();
    Date fechaIngresoAlmacen = new Date();
    TiposDocumentos tiposDocumentos = new TiposDocumentos();
    String nroDocumento = "";
    Date fechaDocumento = new Date();
    int creditoFiscalSiNo = 0;
    String obsIngresoAlmacen= "";
    int nroIngresoAlmacen = 0;
    Proveedores proveedores = new Proveedores();
    int estadoSistema = 0;
    TiposCompra tiposCompra = new TiposCompra();
    Almacenes almacenes = new Almacenes();
    SalidasAlmacen salidasAlmacen = new SalidasAlmacen();
    Personal personal = new Personal();
    EstadosIngresosLiquidacion estadosIngresosLiquidacion = new EstadosIngresosLiquidacion();
    Date fechaLiquidacion = new Date();
    int codSalidaAlmacenDevolucion = 0;
    float cantidadRestante = 0;
    private IngresoTransaccionSolicitud ingresoTransaccionSolicitud = new IngresoTransaccionSolicitud();
    
    //variables de apoyo
    private boolean tieneItemsRechazados;

    
    public Almacenes getAlmacenes() {
        return almacenes;
    }

    public void setAlmacenes(Almacenes almacenes) {
        this.almacenes = almacenes;
    }

    public int getCodIngresoAlmacen() {
        return codIngresoAlmacen;
    }

    public void setCodIngresoAlmacen(int codIngresoAlmacen) {
        this.codIngresoAlmacen = codIngresoAlmacen;
    }

    public int getCodSalidaAlmacenDevolucion() {
        return codSalidaAlmacenDevolucion;
    }

    public void setCodSalidaAlmacenDevolucion(int codSalidaAlmacenDevolucion) {
        this.codSalidaAlmacenDevolucion = codSalidaAlmacenDevolucion;
    }

    public int getCreditoFiscalSiNo() {
        return creditoFiscalSiNo;
    }

    public void setCreditoFiscalSiNo(int creditoFiscalSiNo) {
        this.creditoFiscalSiNo = creditoFiscalSiNo;
    }

    public Devoluciones getDevoluciones() {
        return devoluciones;
    }

    public void setDevoluciones(Devoluciones devoluciones) {
        this.devoluciones = devoluciones;
    }

    public int getEstadoSistema() {
        return estadoSistema;
    }

    public void setEstadoSistema(int estadoSistema) {
        this.estadoSistema = estadoSistema;
    }

    public EstadosIngresoAlmacen getEstadosIngresoAlmacen() {
        return estadosIngresoAlmacen;
    }

    public void setEstadosIngresoAlmacen(EstadosIngresoAlmacen estadosIngresoAlmacen) {
        this.estadosIngresoAlmacen = estadosIngresoAlmacen;
    }

    public EstadosIngresosLiquidacion getEstadosIngresosLiquidacion() {
        return estadosIngresosLiquidacion;
    }

    public void setEstadosIngresosLiquidacion(EstadosIngresosLiquidacion estadosIngresosLiquidacion) {
        this.estadosIngresosLiquidacion = estadosIngresosLiquidacion;
    }

    public Date getFechaDocumento() {
        return fechaDocumento;
    }

    public void setFechaDocumento(Date fechaDocumento) {
        this.fechaDocumento = fechaDocumento;
    }

    public Date getFechaIngresoAlmacen() {
        return fechaIngresoAlmacen;
    }

    public void setFechaIngresoAlmacen(Date fechaIngresoAlmacen) {
        this.fechaIngresoAlmacen = fechaIngresoAlmacen;
    }

    public Date getFechaLiquidacion() {
        return fechaLiquidacion;
    }

    public void setFechaLiquidacion(Date fechaLiquidacion) {
        this.fechaLiquidacion = fechaLiquidacion;
    }

    public Gestiones getGestiones() {
        return gestiones;
    }

    public void setGestiones(Gestiones gestiones) {
        this.gestiones = gestiones;
    }

    public String getNroDocumento() {
        return nroDocumento;
    }

    public void setNroDocumento(String nroDocumento) {
        this.nroDocumento = nroDocumento;
    }

    public int getNroIngresoAlmacen() {
        return nroIngresoAlmacen;
    }

    public void setNroIngresoAlmacen(int nroIngresoAlmacen) {
        this.nroIngresoAlmacen = nroIngresoAlmacen;
    }

    public String getObsIngresoAlmacen() {
        return obsIngresoAlmacen;
    }

    public void setObsIngresoAlmacen(String obsIngresoAlmacen) {
        this.obsIngresoAlmacen = obsIngresoAlmacen;
    }

    public OrdenesCompra getOrdenesCompra() {
        return ordenesCompra;
    }

    public void setOrdenesCompra(OrdenesCompra ordenesCompra) {
        this.ordenesCompra = ordenesCompra;
    }

    public Personal getPersonal() {
        return personal;
    }

    public void setPersonal(Personal personal) {
        this.personal = personal;
    }

    public Proveedores getProveedores() {
        return proveedores;
    }

    public void setProveedores(Proveedores proveedores) {
        this.proveedores = proveedores;
    }

    public SalidasAlmacen getSalidasAlmacen() {
        return salidasAlmacen;
    }

    public void setSalidasAlmacen(SalidasAlmacen salidasAlmacen) {
        this.salidasAlmacen = salidasAlmacen;
    }

    public TiposCompra getTiposCompra() {
        return tiposCompra;
    }

    public void setTiposCompra(TiposCompra tiposCompra) {
        this.tiposCompra = tiposCompra;
    }

    public TiposDocumentos getTiposDocumentos() {
        return tiposDocumentos;
    }

    public void setTiposDocumentos(TiposDocumentos tiposDocumentos) {
        this.tiposDocumentos = tiposDocumentos;
    }

    public TiposIngresoAlmacen getTiposIngresoAlmacen() {
        return tiposIngresoAlmacen;
    }

    public void setTiposIngresoAlmacen(TiposIngresoAlmacen tiposIngresoAlmacen) {
        this.tiposIngresoAlmacen = tiposIngresoAlmacen;
    }

    public float getCantidadRestante() {
        return cantidadRestante;
    }

    public void setCantidadRestante(float cantidadRestante) {
        this.cantidadRestante = cantidadRestante;
    }

    public IngresoTransaccionSolicitud getIngresoTransaccionSolicitud() {
        return ingresoTransaccionSolicitud;
    }

    public void setIngresoTransaccionSolicitud(IngresoTransaccionSolicitud ingresoTransaccionSolicitud) {
        this.ingresoTransaccionSolicitud = ingresoTransaccionSolicitud;
    }
    


    public boolean isTieneItemsRechazados() {
        return tieneItemsRechazados;
    }

    public void setTieneItemsRechazados(boolean tieneItemsRechazados) {
        this.tieneItemsRechazados = tieneItemsRechazados;
    }

    public boolean isFechaCreacionHoy() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        return sdf.format(fechaIngresoAlmacen).compareTo(sdf.format(new Date()))==0;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + this.codIngresoAlmacen;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final IngresosAlmacen other = (IngresosAlmacen) obj;
        if (this.codIngresoAlmacen != other.codIngresoAlmacen) {
            return false;
        }
        return true;
    }
      
    
}
