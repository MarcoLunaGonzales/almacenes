/*
 * IngresosAlmacenDetalleAcond.java
 *
 * Created on 23 de marzo de 2008, 18:36
 */

package com.cofar.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.event.ValueChangeEvent;

/**
 *
 * @author Wilmer Manzaneda chavez
 * @company COFAR
 */
public class IngresosAlmacenDetalleAcond extends AbstractBean{
    
    /** Creates a new instance of IngresosAlmacenDetalleAcond */
    private IngresosAcondicionamiento ingresosAcondicionamiento;
    private String cantTotalIngreso="";
    private String cantIngresoBuenos="";
    private String cantIngresoFallados="";
    private String obsIngresoDetalleAcond="";
    private String codLoteProduccion="";
    private float cantRestante=0;
    private int codTemp=0;
    private Date fechaVencimiento= new Date();
    private List formaFarmaceuticaList=new ArrayList();
    private ProductosFormasFar productosformas;
    private ComponentesProd componentesProd=new ComponentesProd();
    private String cantIngresoProduccion="0";
    private String pesoProduccion="0";
    private String pesoConfirmado="0";
    private List cantidadEnvases=new ArrayList();
    private String cantidadEnvase="";
    private List tiposEnvases=new ArrayList();
    private TiposEnvase tiposEnvase=new TiposEnvase();
    private int cantidadAproximado=0;
    private String volumenPesoAproximado=""; 
    private String ingresoCantidadPeso="c";    
    private String cantAuxiliar="0";
    private String pesoAuxiliar="0";
    private String cantidadReferencial="0";    
    private int vijenciaLote=0;
    private String fechaVencimientoDetalle="";
    
    
    private List<IngresosdetalleCantidadPeso> listadoCantidadPeso=new ArrayList<IngresosdetalleCantidadPeso>();
    
    
    /* -------------------------------------------------------------------------
     /* -------------------------------------------------------------------------
     /* -------------------------------------------------------------------------*/
     
    public IngresosAlmacenDetalleAcond() {
    }
     
    public IngresosAcondicionamiento getIngresosAcondicionamiento() {
        if(ingresosAcondicionamiento==null){
            ingresosAcondicionamiento=new IngresosAcondicionamiento();
        }
        return ingresosAcondicionamiento;
    }
     
    public void setIngresosAcondicionamiento(IngresosAcondicionamiento ingresosAcondicionamiento) {
        this.ingresosAcondicionamiento = ingresosAcondicionamiento;
    }
     
    public String getCantTotalIngreso() {
        return cantTotalIngreso;
    }
     
    public void setCantTotalIngreso(String cantTotalIngreso) {
        this.cantTotalIngreso = cantTotalIngreso;
    }
     
    public String getCantIngresoBuenos() {
        return cantIngresoBuenos;
    }
     
    public void setCantIngresoBuenos(String cantIngresoBuenos) {
        this.cantIngresoBuenos = cantIngresoBuenos;
    }
     
    public String getCantIngresoFallados() {
        return cantIngresoFallados;
    }
     
    public void setCantIngresoFallados(String cantIngresoFallados) {
        this.cantIngresoFallados = cantIngresoFallados;
    }
     
    public String getObsIngresoDetalleAcond() {
        return obsIngresoDetalleAcond;
    }
     
    public void setObsIngresoDetalleAcond(String obsIngresoDetalleAcond) {
        this.obsIngresoDetalleAcond = obsIngresoDetalleAcond;
    }
    public int getCodTemp() {
        return codTemp;
    }
     
    public void setCodTemp(int codTemp) {
        this.codTemp = codTemp;
    }
     
    public String getCodLoteProduccion() {
        return codLoteProduccion;
    }
     
    public void setCodLoteProduccion(String codLoteProduccion) {
        this.codLoteProduccion = codLoteProduccion;
    }
     
    public List getFormaFarmaceuticaList() {
        return formaFarmaceuticaList;
    }
     
    public void setFormaFarmaceuticaList(List formaFarmaceuticaList) {
        this.formaFarmaceuticaList = formaFarmaceuticaList;
    }
    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }
     
    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }
     
    public ProductosFormasFar getProductosformas() {
        if(productosformas==null)
            productosformas=new ProductosFormasFar();
     
        return productosformas;
    }
     
    public void setProductosformas(ProductosFormasFar productosformas) {
        this.productosformas = productosformas;
    }

    public float getCantRestante() {
        return cantRestante;
    }

    public void setCantRestante(float cantRestante) {
        this.cantRestante = cantRestante;
    }
    
     
    public ComponentesProd getComponentesProd() {
        return componentesProd;
    }
     
    public void setComponentesProd(ComponentesProd componentesProd) {
        this.componentesProd = componentesProd;
    }
     
    public String getCantIngresoProduccion() {
        return cantIngresoProduccion;
    }
     
    public void setCantIngresoProduccion(String cantIngresoProduccion) {
        this.cantIngresoProduccion = cantIngresoProduccion;
    }
     
    public String getPesoProduccion() {
        return pesoProduccion;
    }
     
    public void setPesoProduccion(String pesoProduccion) {
        this.pesoProduccion = pesoProduccion;
    }
     
    public String getPesoConfirmado() {
        return pesoConfirmado;
    }
     
    public void setPesoConfirmado(String pesoConfirmado) {
        this.pesoConfirmado = pesoConfirmado;
    }
     
    public List getCantidadEnvases() {
        return cantidadEnvases;
    }
     
    public void setCantidadEnvases(List cantidadEnvases) {
        this.cantidadEnvases = cantidadEnvases;
    }
     
    public List getTiposEnvases() {
        return tiposEnvases;
    }
     
    public void setTiposEnvases(List tiposEnvases) {
        this.tiposEnvases = tiposEnvases;
    }
     
    public TiposEnvase getTiposEnvase() {
        return tiposEnvase;
    }
     
    public void setTiposEnvase(TiposEnvase tiposEnvase) {
        this.tiposEnvase = tiposEnvase;
    }
     
    public String getCantidadEnvase() {
        return cantidadEnvase;
    }
     
    public void setCantidadEnvase(String cantidadEnvase) {
        this.cantidadEnvase = cantidadEnvase;
    }
     
    public String getVolumenPesoAproximado() {
        return volumenPesoAproximado;
    }
     
    public void setVolumenPesoAproximado(String volumenPesoAproximado) {
        this.volumenPesoAproximado = volumenPesoAproximado;
    }
     
    public int getCantidadAproximado() {
        return cantidadAproximado;
    }
     
    public void setCantidadAproximado(int cantidadAproximado) {
        this.cantidadAproximado = cantidadAproximado;
    }

    public String getIngresoCantidadPeso() {
        return ingresoCantidadPeso;
    }

    public void setIngresoCantidadPeso(String ingresoCantidadPeso) {
        this.ingresoCantidadPeso = ingresoCantidadPeso;
    }

    public String getCantAuxiliar() {
        return cantAuxiliar;
    }

    public void setCantAuxiliar(String cantAuxiliar) {
        this.cantAuxiliar = cantAuxiliar;
    }

    public String getPesoAuxiliar() {
        return pesoAuxiliar;
    }

    public void setPesoAuxiliar(String pesoAuxiliar) {
        this.pesoAuxiliar = pesoAuxiliar;
    }

    public String getCantidadReferencial() {
        return cantidadReferencial;
    }

    public void setCantidadReferencial(String cantidadReferencial) {
        this.cantidadReferencial = cantidadReferencial;
    }

    public int getVijenciaLote() {
        return vijenciaLote;
    }

    public void setVijenciaLote(int vijenciaLote) {
        this.vijenciaLote = vijenciaLote;
    }

    public String getFechaVencimientoDetalle() {
        return fechaVencimientoDetalle;
    }

    public void setFechaVencimientoDetalle(String fechaVencimientoDetalle) {
        this.fechaVencimientoDetalle = fechaVencimientoDetalle;
    }

    public List<IngresosdetalleCantidadPeso> getListadoCantidadPeso() {
        return listadoCantidadPeso;
    }

    public void setListadoCantidadPeso(List<IngresosdetalleCantidadPeso> listadoCantidadPeso) {
        this.listadoCantidadPeso = listadoCantidadPeso;
    }

   

}
