/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.bean;

import java.util.Date;

/**
 *
 * @author hvaldivia
 */
public class OrdenesCompra extends AbstractBean {
    int codOrdenCompra = 0;
    int nroOrdenCompra = 0;
    Gestiones gestiones = new Gestiones();
    Proveedores proveedores = new Proveedores();
    Representantes representantes = new Representantes();
    PreOrdenesCompra preOrdenesCompra = new PreOrdenesCompra();
    EstadosCompra estadosCompra = new EstadosCompra();
    TiposTransporte tiposTransporte = new TiposTransporte();
    TiposCompra tiposCompra = new TiposCompra();
    TiposCondicionesPrecio tiposCondicionesPrecio = new TiposCondicionesPrecio();
    TiposPago tiposPago = new TiposPago();
    Monedas monedas = new Monedas();
    int codCotizacion = 0;
    MediosPago mediosPago = new MediosPago();
    Personal responsableCompras = new Personal();
    DivisionCompras divisionCompras = new DivisionCompras();
    Date fechaEmision = new Date();
    Date fechaEntrega = new Date();
    String descFechaEntrega = "";
    Date fechaAlerta = new Date();
    Date fechaDespacho = new Date();
    int creditoFiscalSiNo = 0;
    String obsOrdenCompra = "";
    int estadoSistema = 0;
    String emitirChequeaNombreDe = "";
    PlanDeCuentas planDeCuentas = new PlanDeCuentas();
    Almacenes almacenEntrega = new Almacenes();
    int diasTerminoPago = 0;
    TiposDocumentos terminoPago = new TiposDocumentos();    
    String obsOrdenCompraAprobacion = "";
    ProyectosCompras proyectosCompras = new ProyectosCompras();
    Date fechaEnvioProveedor = new Date();
    Date fechaFinalSeguimiento = new Date();
    String obsOcTransito = "";
    String nroDocTransporte = "";
    CondicionesPrecioLugaresEntrega condicionesPrecioLugaresEntrega = new CondicionesPrecioLugaresEntrega();
    TiposUbicacionesCompra tiposUbicacionesCompra = new TiposUbicacionesCompra();

    public Almacenes getAlmacenEntrega() {
        return almacenEntrega;
    }

    public void setAlmacenEntrega(Almacenes almacenEntrega) {
        this.almacenEntrega = almacenEntrega;
    }

    public int getCodCotizacion() {
        return codCotizacion;
    }

    public void setCodCotizacion(int codCotizacion) {
        this.codCotizacion = codCotizacion;
    }

    public int getCodOrdenCompra() {
        return codOrdenCompra;
    }

    public void setCodOrdenCompra(int codOrdenCompra) {
        this.codOrdenCompra = codOrdenCompra;
    }

    public int getCreditoFiscalSiNo() {
        return creditoFiscalSiNo;
    }

    public void setCreditoFiscalSiNo(int creditoFiscalSiNo) {
        this.creditoFiscalSiNo = creditoFiscalSiNo;
    }

    public String getDescFechaEntrega() {
        return descFechaEntrega;
    }

    public void setDescFechaEntrega(String descFechaEntrega) {
        this.descFechaEntrega = descFechaEntrega;
    }

    public int getDiasTerminoPago() {
        return diasTerminoPago;
    }

    public void setDiasTerminoPago(int diasTerminoPago) {
        this.diasTerminoPago = diasTerminoPago;
    }

    public DivisionCompras getDivisionCompras() {
        return divisionCompras;
    }

    public void setDivisionCompras(DivisionCompras divisionCompras) {
        this.divisionCompras = divisionCompras;
    }

    public String getEmitirChequeaNombreDe() {
        return emitirChequeaNombreDe;
    }

    public void setEmitirChequeaNombreDe(String emitirChequeaNombreDe) {
        this.emitirChequeaNombreDe = emitirChequeaNombreDe;
    }

    public int getEstadoSistema() {
        return estadoSistema;
    }

    public void setEstadoSistema(int estadoSistema) {
        this.estadoSistema = estadoSistema;
    }

    public EstadosCompra getEstadosCompra() {
        return estadosCompra;
    }

    public void setEstadosCompra(EstadosCompra estadosCompra) {
        this.estadosCompra = estadosCompra;
    }

    public Date getFechaAlerta() {
        return fechaAlerta;
    }

    public void setFechaAlerta(Date fechaAlerta) {
        this.fechaAlerta = fechaAlerta;
    }

    public Date getFechaDespacho() {
        return fechaDespacho;
    }

    public void setFechaDespacho(Date fechaDespacho) {
        this.fechaDespacho = fechaDespacho;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public Date getFechaEnvioProveedor() {
        return fechaEnvioProveedor;
    }

    public void setFechaEnvioProveedor(Date fechaEnvioProveedor) {
        this.fechaEnvioProveedor = fechaEnvioProveedor;
    }

    public Date getFechaFinalSeguimiento() {
        return fechaFinalSeguimiento;
    }

    public void setFechaFinalSeguimiento(Date fechaFinalSeguimiento) {
        this.fechaFinalSeguimiento = fechaFinalSeguimiento;
    }

    public Gestiones getGestiones() {
        return gestiones;
    }

    public void setGestiones(Gestiones gestiones) {
        this.gestiones = gestiones;
    }

    public MediosPago getMediosPago() {
        return mediosPago;
    }

    public void setMediosPago(MediosPago mediosPago) {
        this.mediosPago = mediosPago;
    }

    public Monedas getMonedas() {
        return monedas;
    }

    public void setMonedas(Monedas monedas) {
        this.monedas = monedas;
    }

    public String getNroDocTransporte() {
        return nroDocTransporte;
    }

    public void setNroDocTransporte(String nroDocTransporte) {
        this.nroDocTransporte = nroDocTransporte;
    }

    public int getNroOrdenCompra() {
        return nroOrdenCompra;
    }

    public void setNroOrdenCompra(int nroOrdenCompra) {
        this.nroOrdenCompra = nroOrdenCompra;
    }

    public String getObsOcTransito() {
        return obsOcTransito;
    }

    public void setObsOcTransito(String obsOcTransito) {
        this.obsOcTransito = obsOcTransito;
    }

    public String getObsOrdenCompra() {
        return obsOrdenCompra;
    }

    public void setObsOrdenCompra(String obsOrdenCompra) {
        this.obsOrdenCompra = obsOrdenCompra;
    }

    public String getObsOrdenCompraAprobacion() {
        return obsOrdenCompraAprobacion;
    }

    public void setObsOrdenCompraAprobacion(String obsOrdenCompraAprobacion) {
        this.obsOrdenCompraAprobacion = obsOrdenCompraAprobacion;
    }

    public PlanDeCuentas getPlanDeCuentas() {
        return planDeCuentas;
    }

    public void setPlanDeCuentas(PlanDeCuentas planDeCuentas) {
        this.planDeCuentas = planDeCuentas;
    }

    public PreOrdenesCompra getPreOrdenesCompra() {
        return preOrdenesCompra;
    }

    public void setPreOrdenesCompra(PreOrdenesCompra preOrdenesCompra) {
        this.preOrdenesCompra = preOrdenesCompra;
    }

    public Proveedores getProveedores() {
        return proveedores;
    }

    public void setProveedores(Proveedores proveedores) {
        this.proveedores = proveedores;
    }

    public ProyectosCompras getProyectosCompras() {
        return proyectosCompras;
    }

    public void setProyectosCompras(ProyectosCompras proyectosCompras) {
        this.proyectosCompras = proyectosCompras;
    }

    public Representantes getRepresentantes() {
        return representantes;
    }

    public void setRepresentantes(Representantes representantes) {
        this.representantes = representantes;
    }

    public Personal getResponsableCompras() {
        return responsableCompras;
    }

    public void setResponsableCompras(Personal responsableCompras) {
        this.responsableCompras = responsableCompras;
    }

    public TiposDocumentos getTerminoPago() {
        return terminoPago;
    }

    public void setTerminoPago(TiposDocumentos terminoPago) {
        this.terminoPago = terminoPago;
    }

    public TiposCompra getTiposCompra() {
        return tiposCompra;
    }

    public void setTiposCompra(TiposCompra tiposCompra) {
        this.tiposCompra = tiposCompra;
    }

    public TiposCondicionesPrecio getTiposCondicionesPrecio() {
        return tiposCondicionesPrecio;
    }

    public void setTiposCondicionesPrecio(TiposCondicionesPrecio tiposCondicionesPrecio) {
        this.tiposCondicionesPrecio = tiposCondicionesPrecio;
    }

    public TiposPago getTiposPago() {
        return tiposPago;
    }

    public void setTiposPago(TiposPago tiposPago) {
        this.tiposPago = tiposPago;
    }

    public TiposTransporte getTiposTransporte() {
        return tiposTransporte;
    }

    public void setTiposTransporte(TiposTransporte tiposTransporte) {
        this.tiposTransporte = tiposTransporte;
    }

    public CondicionesPrecioLugaresEntrega getCondicionesPrecioLugaresEntrega() {
        return condicionesPrecioLugaresEntrega;
    }

    public void setCondicionesPrecioLugaresEntrega(CondicionesPrecioLugaresEntrega condicionesPrecioLugaresEntrega) {
        this.condicionesPrecioLugaresEntrega = condicionesPrecioLugaresEntrega;
    }

    public TiposUbicacionesCompra getTiposUbicacionesCompra() {
        return tiposUbicacionesCompra;
    }

    public void setTiposUbicacionesCompra(TiposUbicacionesCompra tiposUbicacionesCompra) {
        this.tiposUbicacionesCompra = tiposUbicacionesCompra;
    }

    

    
    

}
