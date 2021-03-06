/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.bean;

import java.util.Date;
import java.util.List;

/**
 *
 * @author hvaldivia
 */
public class SolicitudesSalida extends AbstractBean {
    int codFormSalida = 0;
    Gestiones gestiones = new Gestiones();
    TiposSalidasAlmacen tiposSalidasAlmacen = new TiposSalidasAlmacen();
    EstadosSolicitudSalidasAlmacen estadosSolicitudSalidasAlmacen = new EstadosSolicitudSalidasAlmacen();
    Personal solicitante = new Personal();
    AreasEmpresa areaDestinoSalida = new AreasEmpresa();
    Date fechaSolicitud = new Date();
    String codLoteProduccion = "";
    String obsSolicitud = "";
    int estadoSistema = 0;
    int controlCalidad = 0;
    int codIngresoAlmacen = 0;
    Almacenes almacenes = new Almacenes();
    String ordenTrabajo = "";
    ComponentesProd componentesProd = new ComponentesProd();
    PresentacionesProducto presentacionesProducto = new PresentacionesProducto();
    Maquinaria maquinaria = new Maquinaria();
    AreasInstalaciones areasInstalaciones = new AreasInstalaciones();
    private boolean solicitudPorLoteProveedor=false;
    private Almacenes almacenesDestino = new Almacenes();
    //para solicitudes de salida reacondicionamiento
    private List<SolicitudesSalidaDetalle> solicitudesSalidaDetalleList;
    // para solicitudes de traspaso
    

    public Almacenes getAlmacenes() {
        return almacenes;
    }

    public void setAlmacenes(Almacenes almacenes) {
        this.almacenes = almacenes;
    }

    public AreasEmpresa getAreaDestinoSalida() {
        return areaDestinoSalida;
    }

    public void setAreaDestinoSalida(AreasEmpresa areaDestinoSalida) {
        this.areaDestinoSalida = areaDestinoSalida;
    }

    public int getCodFormSalida() {
        return codFormSalida;
    }

    public void setCodFormSalida(int codFormSalida) {
        this.codFormSalida = codFormSalida;
    }

    public int getCodIngresoAlmacen() {
        return codIngresoAlmacen;
    }

    public void setCodIngresoAlmacen(int codIngresoAlmacen) {
        this.codIngresoAlmacen = codIngresoAlmacen;
    }

    public String getCodLoteProduccion() {
        return codLoteProduccion;
    }

    public void setCodLoteProduccion(String codLoteProduccion) {
        this.codLoteProduccion = codLoteProduccion;
    }

    public int getControlCalidad() {
        return controlCalidad;
    }

    public void setControlCalidad(int controlCalidad) {
        this.controlCalidad = controlCalidad;
    }

    public int getEstadoSistema() {
        return estadoSistema;
    }

    public void setEstadoSistema(int estadoSistema) {
        this.estadoSistema = estadoSistema;
    }

    public EstadosSolicitudSalidasAlmacen getEstadosSolicitudSalidasAlmacen() {
        return estadosSolicitudSalidasAlmacen;
    }

    public void setEstadosSolicitudSalidasAlmacen(EstadosSolicitudSalidasAlmacen estadosSolicitudSalidasAlmacen) {
        this.estadosSolicitudSalidasAlmacen = estadosSolicitudSalidasAlmacen;
    }

    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public Gestiones getGestiones() {
        return gestiones;
    }

    public void setGestiones(Gestiones gestiones) {
        this.gestiones = gestiones;
    }

    public String getObsSolicitud() {
        return obsSolicitud;
    }

    public void setObsSolicitud(String obsSolicitud) {
        this.obsSolicitud = obsSolicitud;
    }

    public String getOrdenTrabajo() {
        return ordenTrabajo;
    }

    public void setOrdenTrabajo(String ordenTrabajo) {
        this.ordenTrabajo = ordenTrabajo;
    }

    public Personal getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(Personal solicitante) {
        this.solicitante = solicitante;
    }

    public TiposSalidasAlmacen getTiposSalidasAlmacen() {
        return tiposSalidasAlmacen;
    }

    public void setTiposSalidasAlmacen(TiposSalidasAlmacen tiposSalidasAlmacen) {
        this.tiposSalidasAlmacen = tiposSalidasAlmacen;
    }

    public ComponentesProd getComponentesProd() {
        return componentesProd;
    }

    public void setComponentesProd(ComponentesProd componentesProd) {
        this.componentesProd = componentesProd;
    }

    public PresentacionesProducto getPresentacionesProducto() {
        return presentacionesProducto;
    }

    public void setPresentacionesProducto(PresentacionesProducto presentacionesProducto) {
        this.presentacionesProducto = presentacionesProducto;
    }

    public Maquinaria getMaquinaria() {
        return maquinaria;
    }

    public void setMaquinaria(Maquinaria maquinaria) {
        this.maquinaria = maquinaria;
    }

    public AreasInstalaciones getAreasInstalaciones() {
        return areasInstalaciones;
    }

    public void setAreasInstalaciones(AreasInstalaciones areasInstalaciones) {
        this.areasInstalaciones = areasInstalaciones;
    }

    public boolean isSolicitudPorLoteProveedor() {
        return solicitudPorLoteProveedor;
    }

    public void setSolicitudPorLoteProveedor(boolean solicitudPorLoteProveedor) {
        this.solicitudPorLoteProveedor = solicitudPorLoteProveedor;
    }
    
    private TiposSolicitudSalidaAlmacen tipoSolicitud=new TiposSolicitudSalidaAlmacen();

    public TiposSolicitudSalidaAlmacen getTipoSolicitud() {
        return tipoSolicitud;
    }

    public void setTipoSolicitud(TiposSolicitudSalidaAlmacen tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }

    public List<SolicitudesSalidaDetalle> getSolicitudesSalidaDetalleList() {
        return solicitudesSalidaDetalleList;
    }

    public void setSolicitudesSalidaDetalleList(List<SolicitudesSalidaDetalle> solicitudesSalidaDetalleList) {
        this.solicitudesSalidaDetalleList = solicitudesSalidaDetalleList;
    }

    public Almacenes getAlmacenesDestino() {
        return almacenesDestino;
    }

    public void setAlmacenesDestino(Almacenes almacenesDestino) {
        this.almacenesDestino = almacenesDestino;
    }



   
}
