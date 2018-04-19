/*
 * ComponentesProd.java
 *
 * Created on 25 de mayo de 2008, 19:26
 */

package com.cofar.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Wilmer Manzaneda chavez
 * @company COFAR
 */
public class ComponentesProd extends AbstractBean {
    
    /** Creates a new instance of ComponentesProd */
    private String codCompprod="";
    private String nombreProdSemiterminado="";
    private Producto producto=new Producto();
    private FormasFarmaceuticas forma=new FormasFarmaceuticas();
    private String concentracionForma="";
    private String codEnvaseprim="";
    private String codColorpresPrimaria="";
    private String volumenPesoEnvasePrim="";
    private String cantidadCompprod="";
    private int codTemp=0;
    private SaboresProducto saboresProductos=new SaboresProducto();
    private PrincipiosActivosProducto principiosActivosProducto=new PrincipiosActivosProducto();
    private AccionesTerapeuticas accionesTerapeuticas=new AccionesTerapeuticas();
    private AreasEmpresa areasEmpresa=new AreasEmpresa();
    private EnvasesPrimarios envasesPrimarios=new EnvasesPrimarios();
    private ColoresPresentacion coloresPresentacion=new ColoresPresentacion();
    private EstadoCompProd estadoCompProd=new EstadoCompProd();
    private List principiosList=new ArrayList();
    private int codTemp0=0;
    private int cantidadComponente=0;
    private String codcompuestoprod="";
    private String columnStyle="";
    private String nombreGenerico="";
    private String regSanitario="";
    private String vidaUtil="";
    private Date fechaVencimientoRS=new Date();
    private TiposProduccion tiposProduccion=new TiposProduccion();
    


    
    public ComponentesProd() {
    }
    
    public String getCodCompprod() {
        return codCompprod;
    }
    
    public void setCodCompprod(String codCompprod) {
        this.codCompprod = codCompprod;
    }
    
    public Producto getProducto() {
        return producto;
    }
    
    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    
    public FormasFarmaceuticas getForma() {
        return forma;
    }
    
    public void setForma(FormasFarmaceuticas forma) {
        this.forma = forma;
    }
    
    public String getConcentracionForma() {
        return concentracionForma;
    }
    
    public void setConcentracionForma(String concentracionForma) {
        this.concentracionForma = concentracionForma;
    }
    
    public String getCodEnvaseprim() {
        return codEnvaseprim;
    }
    
    public void setCodEnvaseprim(String codEnvaseprim) {
        this.codEnvaseprim = codEnvaseprim;
    }
    
    public String getCodColorpresPrimaria() {
        return codColorpresPrimaria;
    }
    
    public void setCodColorpresPrimaria(String codColorpresPrimaria) {
        this.codColorpresPrimaria = codColorpresPrimaria;
    }
    
    public String getVolumenPesoEnvasePrim() {
        return volumenPesoEnvasePrim;
    }
    
    public void setVolumenPesoEnvasePrim(String volumenPesoEnvasePrim) {
        this.volumenPesoEnvasePrim = volumenPesoEnvasePrim;
    }
    
    public String getCantidadCompprod() {
        return cantidadCompprod;
    }
    
    public void setCantidadCompprod(String cantidadCompprod) {
        this.cantidadCompprod = cantidadCompprod;
    }
    
    public SaboresProducto getSaboresProductos() {
        return saboresProductos;
    }
    
    public void setSaboresProductos(SaboresProducto saboresProductos) {
        this.saboresProductos = saboresProductos;
    }
    
    public EnvasesPrimarios getEnvasesPrimarios() {
        return envasesPrimarios;
    }
    
    public void setEnvasesPrimarios(EnvasesPrimarios envasesPrimarios) {
        this.envasesPrimarios = envasesPrimarios;
    }
    
    public ColoresPresentacion getColoresPresentacion() {
        
        return coloresPresentacion;
    }
    
    public void setColoresPresentacion(ColoresPresentacion coloresPresentacion) {
        this.coloresPresentacion = coloresPresentacion;
    }
    
    public AreasEmpresa getAreasEmpresa() {
        return areasEmpresa;
    }
    
    public void setAreasEmpresa(AreasEmpresa areasEmpresa) {
        this.areasEmpresa = areasEmpresa;
    }
    
    public int getCodTemp() {
        return codTemp;
    }
    
    public void setCodTemp(int codTemp) {
        this.codTemp = codTemp;
    }
    
    public PrincipiosActivosProducto getPrincipiosActivosProducto() {
        return principiosActivosProducto;
    }
    
    public void setPrincipiosActivosProducto(PrincipiosActivosProducto principiosActivosProducto) {
        this.principiosActivosProducto = principiosActivosProducto;
    }
    
    public List getPrincipiosList() {
        return principiosList;
    }
    
    public void setPrincipiosList(List principiosList) {
        this.principiosList = principiosList;
    }
    
    public int getCodTemp0() {
        return codTemp0;
    }
    
    public void setCodTemp0(int codTemp0) {
        this.codTemp0 = codTemp0;
    }
    
    public int getCantidadComponente() {
        return cantidadComponente;
    }
    
    public void setCantidadComponente(int cantidadComponente) {
        this.cantidadComponente = cantidadComponente;
    }
    
    public String getCodcompuestoprod() {
        
        return codcompuestoprod;
    }
    
    public void setCodcompuestoprod(String codcompuestoprod) {
        System.out.println("setCodcompuestoprod:"+codcompuestoprod);
        this.codcompuestoprod = codcompuestoprod;
    }
    
    public String getColumnStyle() {
        return columnStyle;
    }
    
    public void setColumnStyle(String columnStyle) {
        this.columnStyle = columnStyle;
    }
    
    public String getNombreProdSemiterminado() {
        return nombreProdSemiterminado;
    }
    
    public void setNombreProdSemiterminado(String nombreProdSemiterminado) {
        this.nombreProdSemiterminado = nombreProdSemiterminado;
    }
    
    public String getNombreGenerico() {
        return nombreGenerico;
    }
    
    public void setNombreGenerico(String nombreGenerico) {
        this.nombreGenerico = nombreGenerico;
    }
    
    public String getRegSanitario() {
        return regSanitario;
    }
    
    public void setRegSanitario(String regSanitario) {
        this.regSanitario = regSanitario;
    }
    
    public String getVidaUtil() {
        return vidaUtil;
    }
    
    public void setVidaUtil(String vidaUtil) {
        this.vidaUtil = vidaUtil;
    }
    
    public AccionesTerapeuticas getAccionesTerapeuticas() {
        return accionesTerapeuticas;
    }
    
    public void setAccionesTerapeuticas(AccionesTerapeuticas accionesTerapeuticas) {
        this.accionesTerapeuticas = accionesTerapeuticas;
    }
    
    /**
     * @return the fechaVencimientoRS
     */
    public Date getFechaVencimientoRS() {
        return fechaVencimientoRS;
    }
    
    /**
     * @param fechaVencimientoRS the fechaVencimientoRS to set
     */
    public void setFechaVencimientoRS(Date fechaVencimientoRS) {
        this.fechaVencimientoRS = fechaVencimientoRS;
    }

    public EstadoCompProd getEstadoCompProd() {
        return estadoCompProd;
    }

    public void setEstadoCompProd(EstadoCompProd estadoCompProd) {
        this.estadoCompProd = estadoCompProd;
    }

    public TiposProduccion getTiposProduccion() {
        return tiposProduccion;
    }

    public void setTiposProduccion(TiposProduccion tiposProduccion) {
        this.tiposProduccion = tiposProduccion;
    }
 
    
    
}
