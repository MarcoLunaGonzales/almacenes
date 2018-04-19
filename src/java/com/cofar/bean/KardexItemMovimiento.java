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
public class KardexItemMovimiento implements Comparable<KardexItemMovimiento>{
    int codReporte = 0;
    Personal personal = new Personal();
    Almacenes almacenes = new Almacenes();
    String tipo="";
    int codigo = 0;
    int numero = 0;
    Date fecha = new Date();
    Materiales materiales = new Materiales();
    UnidadesMedida unidadesMedida = new UnidadesMedida();
    double cantidadIngreso = 0;
    double cantidadSalida = 0;
    String tipoIngresoSalida = "";
    String obsIngresoSalida="";
    String destinoIngresoSalida = "";
    double saldo = 0;
    double cantidadRestanteAnterior = 0;
    Date fechaInicioReporte = new Date();
    Date fechaFinalReporte = new Date();
    String codLoteProduccion = "";
    AreasEmpresa areasEmpresa = new AreasEmpresa();
    double costoUnitario = 0;
    double debe = 0;
    double haber = 0;
    double saldoDinero = 0;
    double tipoCambio = 0;
    double costoPromedio = 0;
    double valorUfv = 0;
    double diferenciaActualizado = 0;
    double costoIngreso = 0;
    int codTipoIngresoSalida = 0;
    String producto="";
    int cantidadIngresoCajas = 0;
    int cantidadSalidaCajas = 0;
    String codigoStr = "";
    

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

    public double getCantidadIngreso() {
        return cantidadIngreso;
    }

    public void setCantidadIngreso(double cantidadIngreso) {
        this.cantidadIngreso = cantidadIngreso;
    }

    public int getCantidadIngresoCajas() {
        return cantidadIngresoCajas;
    }

    public void setCantidadIngresoCajas(int cantidadIngresoCajas) {
        this.cantidadIngresoCajas = cantidadIngresoCajas;
    }

    public double getCantidadRestanteAnterior() {
        return cantidadRestanteAnterior;
    }

    public void setCantidadRestanteAnterior(double cantidadRestanteAnterior) {
        this.cantidadRestanteAnterior = cantidadRestanteAnterior;
    }

    public double getCantidadSalida() {
        return cantidadSalida;
    }

    public void setCantidadSalida(double cantidadSalida) {
        this.cantidadSalida = cantidadSalida;
    }

    public int getCantidadSalidaCajas() {
        return cantidadSalidaCajas;
    }

    public void setCantidadSalidaCajas(int cantidadSalidaCajas) {
        this.cantidadSalidaCajas = cantidadSalidaCajas;
    }

    public String getCodLoteProduccion() {
        return codLoteProduccion;
    }

    public void setCodLoteProduccion(String codLoteProduccion) {
        this.codLoteProduccion = codLoteProduccion;
    }

    public int getCodReporte() {
        return codReporte;
    }

    public void setCodReporte(int codReporte) {
        this.codReporte = codReporte;
    }

    public int getCodTipoIngresoSalida() {
        return codTipoIngresoSalida;
    }

    public void setCodTipoIngresoSalida(int codTipoIngresoSalida) {
        this.codTipoIngresoSalida = codTipoIngresoSalida;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getCodigoStr() {
        return codigoStr;
    }

    public void setCodigoStr(String codigoStr) {
        this.codigoStr = codigoStr;
    }

    public double getCostoIngreso() {
        return costoIngreso;
    }

    public void setCostoIngreso(double costoIngreso) {
        this.costoIngreso = costoIngreso;
    }

    public double getCostoPromedio() {
        return costoPromedio;
    }

    public void setCostoPromedio(double costoPromedio) {
        this.costoPromedio = costoPromedio;
    }

    public double getCostoUnitario() {
        return costoUnitario;
    }

    public void setCostoUnitario(double costoUnitario) {
        this.costoUnitario = costoUnitario;
    }

    public double getDebe() {
        return debe;
    }

    public void setDebe(double debe) {
        this.debe = debe;
    }

    public String getDestinoIngresoSalida() {
        return destinoIngresoSalida;
    }

    public void setDestinoIngresoSalida(String destinoIngresoSalida) {
        this.destinoIngresoSalida = destinoIngresoSalida;
    }

    public double getDiferenciaActualizado() {
        return diferenciaActualizado;
    }

    public void setDiferenciaActualizado(double diferenciaActualizado) {
        this.diferenciaActualizado = diferenciaActualizado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    
   

    public Date getFechaFinalReporte() {
        return fechaFinalReporte;
    }

    public void setFechaFinalReporte(Date fechaFinalReporte) {
        this.fechaFinalReporte = fechaFinalReporte;
    }

    public Date getFechaInicioReporte() {
        return fechaInicioReporte;
    }

    public void setFechaInicioReporte(Date fechaInicioReporte) {
        this.fechaInicioReporte = fechaInicioReporte;
    }

    public double getHaber() {
        return haber;
    }

    public void setHaber(double haber) {
        this.haber = haber;
    }

    public Materiales getMateriales() {
        return materiales;
    }

    public void setMateriales(Materiales materiales) {
        this.materiales = materiales;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getObsIngresoSalida() {
        return obsIngresoSalida;
    }

    public void setObsIngresoSalida(String obsIngresoSalida) {
        this.obsIngresoSalida = obsIngresoSalida;
    }

    public Personal getPersonal() {
        return personal;
    }

    public void setPersonal(Personal personal) {
        this.personal = personal;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public double getSaldoDinero() {
        return saldoDinero;
    }

    public void setSaldoDinero(double saldoDinero) {
        this.saldoDinero = saldoDinero;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getTipoCambio() {
        return tipoCambio;
    }

    public void setTipoCambio(double tipoCambio) {
        this.tipoCambio = tipoCambio;
    }

    public String getTipoIngresoSalida() {
        return tipoIngresoSalida;
    }

    public void setTipoIngresoSalida(String tipoIngresoSalida) {
        this.tipoIngresoSalida = tipoIngresoSalida;
    }

    public UnidadesMedida getUnidadesMedida() {
        return unidadesMedida;
    }

    public void setUnidadesMedida(UnidadesMedida unidadesMedida) {
        this.unidadesMedida = unidadesMedida;
    }

    public double getValorUfv() {
        return valorUfv;
    }

    public void setValorUfv(double valorUfv) {
        this.valorUfv = valorUfv;
    }

    
    @Override
    public int compareTo(KardexItemMovimiento kardexItemMovimiento){
        //return  kardexItemMovimiento.getFecha().compareTo(this.getFecha());
        //System.out.println("compare to" + this.getFecha().compareTo(kardexItemMovimiento.getFecha()));
        int comparacionFecha =  this.getFecha().compareTo(kardexItemMovimiento.getFecha());
        if(comparacionFecha!=0){
            return comparacionFecha;
        }else{
            return this.getNumero() - kardexItemMovimiento.getNumero();
        }
    }
}
