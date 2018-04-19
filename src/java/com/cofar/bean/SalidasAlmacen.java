/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.bean;

import com.cofar.web.ManagedBean;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author hvaldivia
 */
public class SalidasAlmacen extends AbstractBean {
    int codSalidaAlmacen = 0;
    Gestiones gestiones = new Gestiones();
    int codOrdenRepesada = 0;
    int codFormSalida = 0;
    Producto producto = new Producto();
    TiposSalidasAlmacen tiposSalidasAlmacen = new TiposSalidasAlmacen();
    AreasEmpresa areasEmpresa = new AreasEmpresa();
    int nroSalidaAlmacen = 0;
    Date fechaSalidaAlmacen = new Date();
    String obsSalidaAlmacen= "";
    int estadoSistema = 0;
    Almacenes almacenes = new Almacenes();
    OrdenesCompra ordenesCompra = new OrdenesCompra();
    Personal personal = new Personal();
    EstadosSalidasAlmacen estadosSalidasAlmacen = new EstadosSalidasAlmacen();
    String codLoteProduccion = "";
    int codEstadoSalidaCosto = 0;
    int codProdAnt = 0;
    String ordenTrabajo = "";
    PresentacionesProducto presentacionesProducto = new PresentacionesProducto();
    ComponentesProd componentesProd = new ComponentesProd();
    ComponentesProd componentesProd1 = new ComponentesProd();
    private Personal personalAnula=new Personal();
    private Date fechaAnulacion=new Date();
    private EstadosTransaccionSalida estadosTransaccionSalida=new EstadosTransaccionSalida();
    Maquinaria maquinaria = new Maquinaria();
    AreasInstalaciones areasInstalaciones = new AreasInstalaciones();
    private SalidaTransaccionSolicitud salidaTransaccionSolicitud = new SalidaTransaccionSolicitud();
    
    
    //Variables de apoyo
    private boolean tieneDevolucion = false;
    
    public Almacenes getAlmacenes() {
        return almacenes;
    }

    public void setAlmacenes(Almacenes almacenes) {
        this.almacenes = almacenes;
    }

    public AreasEmpresa getAreasEmpresa() {
        return areasEmpresa;
    }

    public void setAreasEmpresa(AreasEmpresa areasEmpresa) {
        this.areasEmpresa = areasEmpresa;
    }

    public int getCodEstadoSalidaCosto() {
        return codEstadoSalidaCosto;
    }

    public void setCodEstadoSalidaCosto(int codEstadoSalidaCosto) {
        this.codEstadoSalidaCosto = codEstadoSalidaCosto;
    }

    public int getCodFormSalida() {
        return codFormSalida;
    }

    public void setCodFormSalida(int codFormSalida) {
        this.codFormSalida = codFormSalida;
    }

    public String getCodLoteProduccion() {
        return codLoteProduccion;
    }

    public void setCodLoteProduccion(String codLoteProduccion) {
        this.codLoteProduccion = codLoteProduccion;
    }

    public int getCodOrdenRepesada() {
        return codOrdenRepesada;
    }

    public void setCodOrdenRepesada(int codOrdenRepesada) {
        this.codOrdenRepesada = codOrdenRepesada;
    }

    public int getCodProdAnt() {
        return codProdAnt;
    }

    public void setCodProdAnt(int codProdAnt) {
        this.codProdAnt = codProdAnt;
    }

    public int getCodSalidaAlmacen() {
        return codSalidaAlmacen;
    }

    public void setCodSalidaAlmacen(int codSalidaAlmacen) {
        this.codSalidaAlmacen = codSalidaAlmacen;
    }

    public int getEstadoSistema() {
        return estadoSistema;
    }

    public void setEstadoSistema(int estadoSistema) {
        this.estadoSistema = estadoSistema;
    }

    public EstadosSalidasAlmacen getEstadosSalidasAlmacen() {
        return estadosSalidasAlmacen;
    }

    public void setEstadosSalidasAlmacen(EstadosSalidasAlmacen estadosSalidasAlmacen) {
        this.estadosSalidasAlmacen = estadosSalidasAlmacen;
    }

    public Date getFechaSalidaAlmacen() {
        return fechaSalidaAlmacen;
    }

    public void setFechaSalidaAlmacen(Date fechaSalidaAlmacen) {
        this.fechaSalidaAlmacen = fechaSalidaAlmacen;
    }

    public Gestiones getGestiones() {
        return gestiones;
    }

    public void setGestiones(Gestiones gestiones) {
        this.gestiones = gestiones;
    }

    public int getNroSalidaAlmacen() {
        return nroSalidaAlmacen;
    }

    public void setNroSalidaAlmacen(int nroSalidaAlmacen) {
        this.nroSalidaAlmacen = nroSalidaAlmacen;
    }
    

    public String getObsSalidaAlmacen() {
        return obsSalidaAlmacen;
    }

    public void setObsSalidaAlmacen(String obsSalidaAlmacen) {
        this.obsSalidaAlmacen = obsSalidaAlmacen;
    }

    public String getOrdenTrabajo() {
        return ordenTrabajo;
    }

    public void setOrdenTrabajo(String ordenTrabajo) {
        this.ordenTrabajo = ordenTrabajo;
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

    public PresentacionesProducto getPresentacionesProducto() {
        return presentacionesProducto;
    }

    public void setPresentacionesProducto(PresentacionesProducto presentacionesProducto) {
        this.presentacionesProducto = presentacionesProducto;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
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

    public ComponentesProd getComponentesProd1() {
        return componentesProd1;
    }

    public void setComponentesProd1(ComponentesProd componentesProd1) {
        this.componentesProd1 = componentesProd1;
    }

    public Date getFechaAnulacion() {
        return fechaAnulacion;
    }

    public void setFechaAnulacion(Date fechaAnulacion) {
        this.fechaAnulacion = fechaAnulacion;
    }

    public Personal getPersonalAnula() {
        return personalAnula;
    }

    public void setPersonalAnula(Personal personalAnula) {
        this.personalAnula = personalAnula;
    }

    public EstadosTransaccionSalida getEstadosTransaccionSalida() {
        return estadosTransaccionSalida;
    }

    public void setEstadosTransaccionSalida(EstadosTransaccionSalida estadosTransaccionSalida) {
        this.estadosTransaccionSalida = estadosTransaccionSalida;
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

    public SalidaTransaccionSolicitud getSalidaTransaccionSolicitud() {
        return salidaTransaccionSolicitud;
    }

    public void setSalidaTransaccionSolicitud(SalidaTransaccionSolicitud salidaTransaccionSolicitud) {
        this.salidaTransaccionSolicitud = salidaTransaccionSolicitud;
    }

    public boolean isTieneDevolucion() {
        return tieneDevolucion;
    }

    public void setTieneDevolucion(boolean tieneDevolucion) {
        this.tieneDevolucion = tieneDevolucion;
    }

    public boolean isFechaCreacionHoy() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        return sdf.format(fechaSalidaAlmacen).compareTo(sdf.format(new Date()))==0;
    }

}
