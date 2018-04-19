/*
 * ComponentesProd.java
 *
 * Created on 25 de mayo de 2008, 19:26
 */

package com.cofar.bean;

import java.util.Date;
import java.util.logging.Logger;

/**
 *
 * @author Wilson Choquehuanca Gonzales
 * @company COFAR
 */
public class Materiales extends AbstractBean implements Cloneable{
    
    /** Creates a new instance of ComponentesProd */
    private String codMaterial="";
    private String nombreMaterial="";
    private UnidadesMedida unidadesMedida=new UnidadesMedida();
    private EstadoReferencial estadoRegistro=new EstadoReferencial();    
    Grupos grupos = new Grupos();
    private int materialAlmacen = 0;
    private float pesoAsociado = 0;
    private UnidadesMedida unidadesMedidaPesoAsociado = new UnidadesMedida();
    private UnidadesMedida unidadesMedidaCompra = new UnidadesMedida();
    private double stockMaximoMaterial = 0;
    private double stockMinimoMaterial = 0;
    private double stockSeguridad = 0;
    private String funcionMaterial = "";
    private String nombreComercialMaterial = "";
    private String caracteristicasMaterial = "";
    private String tamanioMaterial = "";
    private String tipoImpresion = "";
    private String obsMaterial = "";
    private String acabado = "";
    private String color = "";
    private String capacidad = "";
    private String gramaje = "";
    //private int materialAlmacen = 0;
    private int materialMuestreo = 0;
    private float cantidadMinimaMuestreo = 0;
    private float cantidadMaximaMuestreo = 0;
    private int materialProduccion = 0;
    private GruposStockMateriales gruposStockMateriales = new GruposStockMateriales();
    private float costosUnitario = 0;
    private String codigoAntoguo = "";
    private int movimientoItem = 0;
    private Date fechaCreacion=new Date();
    private Personal personal = new Personal();
    private int tiempoReposicion = 0;
    private int premio = 0;
    private TiposPremioPromocional tiposPremioPromocional = new TiposPremioPromocional();
    private int codMaterialInsumo = 0;
    private int codCategoria = 0;    
    private String nombres1="";
    private int codTipoApoyoSeleccionado = 0;
    private int codLineaApoyoSeleccionado = 0;
    
    public String getCodMaterial() {
        return codMaterial;
    }

    public void setCodMaterial(String codMaterial) {
        this.codMaterial = codMaterial;
    }

    public String getNombreMaterial() {
        return nombreMaterial;
    }

    public void setNombreMaterial(String nombreMaterial) {
        this.nombreMaterial = nombreMaterial;
    }

    public UnidadesMedida getUnidadesMedida() {
        return unidadesMedida;
    }

    public void setUnidadesMedida(UnidadesMedida unidadesMedida) {
        this.unidadesMedida = unidadesMedida;
    }

    public EstadoReferencial getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(EstadoReferencial estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

    public Grupos getGrupos() {
        return grupos;
    }

    public void setGrupos(Grupos grupos) {
        this.grupos = grupos;
    }

    public int getMaterialAlmacen() {
        return materialAlmacen;
    }

    public void setMaterialAlmacen(int materialAlmacen) {
        this.materialAlmacen = materialAlmacen;
    }

    public UnidadesMedida getUnidadesMedidaPesoAsociado() {
        return unidadesMedidaPesoAsociado;
    }

    public void setUnidadesMedidaPesoAsociado(UnidadesMedida unidadesMedidaPesoAsociado) {
        this.unidadesMedidaPesoAsociado = unidadesMedidaPesoAsociado;
    }

    public float getPesoAsociado() {
        return pesoAsociado;
    }

    public void setPesoAsociado(float pesoAsociado) {
        this.pesoAsociado = pesoAsociado;
    }

    public UnidadesMedida getUnidadesMedidaCompra() {
        return unidadesMedidaCompra;
    }

    public void setUnidadesMedidaCompra(UnidadesMedida unidadesMedidaCompra) {
        this.unidadesMedidaCompra = unidadesMedidaCompra;
    }

    public String getAcabado() {
        return acabado;
    }

    public void setAcabado(String acabado) {
        this.acabado = acabado;
    }

    public float getCantidadMaximaMuestreo() {
        return cantidadMaximaMuestreo;
    }

    public void setCantidadMaximaMuestreo(float cantidadMaximaMuestreo) {
        this.cantidadMaximaMuestreo = cantidadMaximaMuestreo;
    }

    public float getCantidadMinimaMuestreo() {
        return cantidadMinimaMuestreo;
    }

    public void setCantidadMinimaMuestreo(float cantidadMinimaMuestreo) {
        this.cantidadMinimaMuestreo = cantidadMinimaMuestreo;
    }

    public String getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(String capacidad) {
        this.capacidad = capacidad;
    }

    public String getCaracteristicasMaterial() {
        return caracteristicasMaterial;
    }

    public void setCaracteristicasMaterial(String caracteristicasMaterial) {
        this.caracteristicasMaterial = caracteristicasMaterial;
    }

    public int getCodCategoria() {
        return codCategoria;
    }

    public void setCodCategoria(int codCategoria) {
        this.codCategoria = codCategoria;
    }

    public int getCodMaterialInsumo() {
        return codMaterialInsumo;
    }

    public void setCodMaterialInsumo(int codMaterialInsumo) {
        this.codMaterialInsumo = codMaterialInsumo;
    }

    public String getCodigoAntoguo() {
        return codigoAntoguo;
    }

    public void setCodigoAntoguo(String codigoAntoguo) {
        this.codigoAntoguo = codigoAntoguo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public float getCostosUnitario() {
        return costosUnitario;
    }

    public void setCostosUnitario(float costosUnitario) {
        this.costosUnitario = costosUnitario;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getFuncionMaterial() {
        return funcionMaterial;
    }

    public void setFuncionMaterial(String funcionMaterial) {
        this.funcionMaterial = funcionMaterial;
    }

    public String getGramaje() {
        return gramaje;
    }

    public void setGramaje(String gramaje) {
        this.gramaje = gramaje;
    }

    public GruposStockMateriales getGruposStockMateriales() {
        return gruposStockMateriales;
    }

    public void setGruposStockMateriales(GruposStockMateriales gruposStockMateriales) {
        this.gruposStockMateriales = gruposStockMateriales;
    }

    public int getMaterialMuestreo() {
        return materialMuestreo;
    }

    public void setMaterialMuestreo(int materialMuestreo) {
        this.materialMuestreo = materialMuestreo;
    }

    public int getMaterialProduccion() {
        return materialProduccion;
    }

    public void setMaterialProduccion(int materialProduccion) {
        this.materialProduccion = materialProduccion;
    }

    public int getMovimientoItem() {
        return movimientoItem;
    }

    public void setMovimientoItem(int movimientoItem) {
        this.movimientoItem = movimientoItem;
    }

    public String getNombreComercialMaterial() {
        return nombreComercialMaterial;
    }

    public void setNombreComercialMaterial(String nombreComercialMaterial) {
        this.nombreComercialMaterial = nombreComercialMaterial;
    }

    public String getObsMaterial() {
        return obsMaterial;
    }

    public void setObsMaterial(String obsMaterial) {
        this.obsMaterial = obsMaterial;
    }

    public Personal getPersonal() {
        return personal;
    }

    public void setPersonal(Personal personal) {
        this.personal = personal;
    }

    public int getPremio() {
        return premio;
    }

    public void setPremio(int premio) {
        this.premio = premio;
    }

    public double getStockMaximoMaterial() {
        return stockMaximoMaterial;
    }

    public void setStockMaximoMaterial(double stockMaximoMaterial) {
        this.stockMaximoMaterial = stockMaximoMaterial;
    }

    public double getStockMinimoMaterial() {
        return stockMinimoMaterial;
    }

    public void setStockMinimoMaterial(double stockMinimoMaterial) {
        this.stockMinimoMaterial = stockMinimoMaterial;
    }

    public double getStockSeguridad() {
        return stockSeguridad;
    }

    public void setStockSeguridad(double stockSeguridad) {
        this.stockSeguridad = stockSeguridad;
    }

    public String getTamanioMaterial() {
        return tamanioMaterial;
    }

    public void setTamanioMaterial(String tamanioMaterial) {
        this.tamanioMaterial = tamanioMaterial;
    }

    public int getTiempoReposicion() {
        return tiempoReposicion;
    }

    public void setTiempoReposicion(int tiempoReposicion) {
        this.tiempoReposicion = tiempoReposicion;
    }

    public String getTipoImpresion() {
        return tipoImpresion;
    }

    public void setTipoImpresion(String tipoImpresion) {
        this.tipoImpresion = tipoImpresion;
    }

    public TiposPremioPromocional getTiposPremioPromocional() {
        return tiposPremioPromocional;
    }

    public void setTiposPremioPromocional(TiposPremioPromocional tiposPremioPromocional) {
        this.tiposPremioPromocional = tiposPremioPromocional;
    }    

    public String getNombres1() {
        return nombres1;
    }

    public void setNombres1(String nombres1) {
        this.nombres1 = nombres1;
    }

    public Materiales() {
        nombreMaterial="";
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + (this.codMaterial != null ? this.codMaterial.hashCode() : 0);
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
        final Materiales other = (Materiales) obj;
        if ((this.codMaterial == null) ? (other.codMaterial != null) : !this.codMaterial.equals(other.codMaterial)) {
            return false;
        }
        return true;
    }
    
    public Materiales clone()
    {
        Materiales clone =null;
        try
        {
            clone =  (Materiales)super.clone();
        }
        catch(CloneNotSupportedException ex)
        {
            ex.printStackTrace();
            System.out.println("no se puede clonar");
        }
        
        return clone;
    }

    public int getCodTipoApoyoSeleccionado() {
        return codTipoApoyoSeleccionado;
    }

    public void setCodTipoApoyoSeleccionado(int codTipoApoyoSeleccionado) {
        this.codTipoApoyoSeleccionado = codTipoApoyoSeleccionado;
    }

    public int getCodLineaApoyoSeleccionado() {
        return codLineaApoyoSeleccionado;
    }

    public void setCodLineaApoyoSeleccionado(int codLineaApoyoSeleccionado) {
        this.codLineaApoyoSeleccionado = codLineaApoyoSeleccionado;
    }

    
    
    
    
}
