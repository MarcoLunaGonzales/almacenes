/*
 * EnvasesSecundarios.java
 *
 * Created on 18 de marzo de 2008, 17:38
 */

package com.cofar.bean;

import java.util.Date;

/**
 *
 * @author Wilson Choquehuanca Gonzales
 * @company COFAR
 */
public class ProgramaProduccion extends AbstractBean{
    
    /** Creates a new instance of TiposMercaderia */
    private String codProgramaProduccion="";
    private String codCompProd="";
    private String codLoteProduccion="";
    private String codLoteProduccionAnterior="";
    private String fechaInicio="";
    private String fechaFinal="";
    private String codEstadoPrograma="";
    private String observacion="";   
    private String versionLote="";
    private String  materialTransito="";
    private String styleClass="";
    private int estadoProductoSimulacion=0;
    
    private String cantidadLote="";                  
    private double totalLote = 0;
    
    private FormulaMaestra formulaMaestra=new FormulaMaestra();
    private TiposProgramaProduccion tiposProgramaProduccion=new TiposProgramaProduccion();
    private EstadoProgramaProduccion estadoProgramaProduccion=new EstadoProgramaProduccion();
    private CategoriasCompProd categoriasCompProd=new CategoriasCompProd();
    private PresentacionesProducto presentacionesProducto = new PresentacionesProducto();
    private double cantIngresoAcond = 0;
    private int codFormulaMaestraVersion=0;
    private int codCompProdVersion=0;
    private TiposProduccion tiposProduccion=new TiposProduccion();
    public String getCodProgramaProduccion() 
    {
        return codProgramaProduccion;
    }

    public void setCodProgramaProduccion(String codProgramaProduccion) {
        this.codProgramaProduccion = codProgramaProduccion;
    }

    public String getCodLoteProduccion() {
        return codLoteProduccion;
    }

    public void setCodLoteProduccion(String codLoteProduccion) {
        this.codLoteProduccion = codLoteProduccion;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(String fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public String getCodEstadoPrograma() {
        return codEstadoPrograma;
    }

    public void setCodEstadoPrograma(String codEstadoPrograma) {
        this.codEstadoPrograma = codEstadoPrograma;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public FormulaMaestra getFormulaMaestra() {
        return formulaMaestra;
    }

    public void setFormulaMaestra(FormulaMaestra formulaMaestra) {
        this.formulaMaestra = formulaMaestra;
    }

    public EstadoProgramaProduccion getEstadoProgramaProduccion() {
        return estadoProgramaProduccion;
    }

    public void setEstadoProgramaProduccion(EstadoProgramaProduccion estadoProgramaProduccion) {
        this.estadoProgramaProduccion = estadoProgramaProduccion;
    }

    public String getCantidadLote() {
        return cantidadLote;
    }

    public void setCantidadLote(String cantidadLote) {
        this.cantidadLote = cantidadLote;
    }

    public String getVersionLote() {
        return versionLote;
    }

    public void setVersionLote(String versionLote) {
        this.versionLote = versionLote;
    }

    public int getEstadoProductoSimulacion() {
        return estadoProductoSimulacion;
    }

    public void setEstadoProductoSimulacion(int estadoProductoSimulacion) {
        this.estadoProductoSimulacion = estadoProductoSimulacion;
    }

    public String getCodCompProd() {
        return codCompProd;
    }

    public void setCodCompProd(String codCompProd) {
        this.codCompProd = codCompProd;
    }

    public String getCodLoteProduccionAnterior() {
        return codLoteProduccionAnterior;
    }

    public void setCodLoteProduccionAnterior(String codLoteProduccionAnterior) {
        this.codLoteProduccionAnterior = codLoteProduccionAnterior;
    }

    public TiposProgramaProduccion getTiposProgramaProduccion() {
        return tiposProgramaProduccion;
    }

    public void setTiposProgramaProduccion(TiposProgramaProduccion tiposProgramaProduccion) {
        this.tiposProgramaProduccion = tiposProgramaProduccion;
    }

    public CategoriasCompProd getCategoriasCompProd() {
        return categoriasCompProd;
    }

    public void setCategoriasCompProd(CategoriasCompProd categoriasCompProd) {
        this.categoriasCompProd = categoriasCompProd;
    }

    public String getMaterialTransito() {
        return materialTransito;
    }

    public void setMaterialTransito(String materialTransito) {
        this.materialTransito = materialTransito;
    }

    public String getStyleClass() {
        return styleClass;
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    public double getTotalLote() {
        return totalLote;
    }

    public void setTotalLote(double totalLote) {
        this.totalLote = totalLote;
    }

    public PresentacionesProducto getPresentacionesProducto() {
        return presentacionesProducto;
    }

    public void setPresentacionesProducto(PresentacionesProducto presentacionesProducto) {
        this.presentacionesProducto = presentacionesProducto;
    }

    public double getCantIngresoAcond() {
        return cantIngresoAcond;
    }

    public void setCantIngresoAcond(double cantIngresoAcond) {
        this.cantIngresoAcond = cantIngresoAcond;
    }

    public int getCodFormulaMaestraVersion() {
        return codFormulaMaestraVersion;
    }

    public void setCodFormulaMaestraVersion(int codFormulaMaestraVersion) {
        this.codFormulaMaestraVersion = codFormulaMaestraVersion;
    }

    public TiposProduccion getTiposProduccion() {
        return tiposProduccion;
    }

    public void setTiposProduccion(TiposProduccion tiposProduccion) {
        this.tiposProduccion = tiposProduccion;
    }

    public int getCodCompProdVersion() {
        return codCompProdVersion;
    }

    public void setCodCompProdVersion(int codCompProdVersion) {
        this.codCompProdVersion = codCompProdVersion;
    }

  private   int nro_version;

    public int getNro_version() {
        return nro_version;
    }

    public void setNro_version(int nro_version) {
        this.nro_version = nro_version;
    }
  
    

}
