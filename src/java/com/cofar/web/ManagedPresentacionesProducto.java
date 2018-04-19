/*
 * ManagedPresentacionesProducto.java
 *
 *
 */

package com.cofar.web;

import com.cofar.bean.CategoriasProducto;
import com.cofar.bean.PresentacionesProducto;
import com.cofar.bean.ComponentesProd;
import com.cofar.util.CofarConnection;

import com.cofar.util.Util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.component.ValueHolder;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.ResultDataModel;
import javax.faces.model.SelectItem;
import org.richfaces.component.html.HtmlDataTable;


/**
 *
 * Autor Guery Garcia Jaldin 
 * 29/9/2010
 */
public class ManagedPresentacionesProducto extends ManagedBean{
    
    /** Creates a new instance of ManagedPersonal */
    private List presentacionesProductoList=new ArrayList();
    private List presentacionesProductoEli=new ArrayList();
    private List presentacionesProductoEli2=new ArrayList();
    private PresentacionesProducto presentacionesProducto=new PresentacionesProducto();
    private ComponentesProd componentesProd=new ComponentesProd();
    private Connection con;
    private List estadoRegistro=new  ArrayList();
    private List productos=new  ArrayList();
    private List tiposMercaderia=new  ArrayList();
    private List envasesSecundarios=new  ArrayList();
    private List lineasMKTList=new ArrayList();
    private List formasFarList=new ArrayList();
    private List envasesTerciarios=new ArrayList();
    private List cartonesCorrugados=new ArrayList();
    private List tiposPresentacion=new  ArrayList();
    private String codigo="";
    private ResultDataModel componentesList;
    //*datos de los componentes
    private List<ComponentesProd> listaComponentesBuscar=new ArrayList<ComponentesProd>();
    private List<ComponentesProd> listaComponentesSeleccionados=new ArrayList<ComponentesProd>();
    private List listaComponentesLista=new ArrayList();
    private List eliminaComponentesSeleccionados=new ArrayList();
    private boolean swEliminaSi;
    private boolean swEliminaNo;
    private String principioActivo="";
    private List componentespresentaciones=new ArrayList();
    private ComponentesProd editarComponente = new ComponentesProd();
    private HtmlDataTable componentesSeleccionadosDataTable =new HtmlDataTable();
    private List componenteEditarList=new ArrayList();
    private String codComponenteEditar="";
    private String editarCodCompprod="";
    private String editarNombreProdSemiterminado="";
    private String editarCantidadCompprod="";
    private HtmlDataTable componentesBuscarDataTable =new HtmlDataTable();
    private List categoriaList = new ArrayList();
    private HtmlSelectOneMenu componentesEditarSelectOneMenu = new HtmlSelectOneMenu();    


    public List getPresentacionesProductoList() {
        return presentacionesProductoList;
    }

    public void setPresentacionesProductoList(List presentacionesProductoList) {
        this.presentacionesProductoList = presentacionesProductoList;
    }

    public List getPresentacionesProductoEli() {
        return presentacionesProductoEli;
    }

    public void setPresentacionesProductoEli(List presentacionesProductoEli) {
        this.presentacionesProductoEli = presentacionesProductoEli;
    }

    public List getPresentacionesProductoEli2() {
        return presentacionesProductoEli2;
    }

    public void setPresentacionesProductoEli2(List presentacionesProductoEli2) {
        this.presentacionesProductoEli2 = presentacionesProductoEli2;
    }

    public PresentacionesProducto getPresentacionesProducto() {
        return presentacionesProducto;
    }

    public void setPresentacionesProducto(PresentacionesProducto presentacionesProducto) {
        this.presentacionesProducto = presentacionesProducto;
    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public List getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(List estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

    public List getProductos() {
        return productos;
    }

    public void setProductos(List productos) {
        this.productos = productos;
    }

    public List getTiposMercaderia() {
        return tiposMercaderia;
    }

    public void setTiposMercaderia(List tiposMercaderia) {
        this.tiposMercaderia = tiposMercaderia;
    }

    public List getEnvasesSecundarios() {
        return envasesSecundarios;
    }

    public void setEnvasesSecundarios(List envasesSecundarios) {
        this.envasesSecundarios = envasesSecundarios;
    }

    public List getLineasMKTList() {
        return lineasMKTList;
    }

    public void setLineasMKTList(List lineasMKTList) {
        this.lineasMKTList = lineasMKTList;
    }

    public List getFormasFarList() {
        return formasFarList;
    }

    public void setFormasFarList(List formasFarList) {
        this.formasFarList = formasFarList;
    }

    public List getEnvasesTerciarios() {
        return envasesTerciarios;
    }

    public void setEnvasesTerciarios(List envasesTerciarios) {
        this.envasesTerciarios = envasesTerciarios;
    }

    public List getCartonesCorrugados() {
        return cartonesCorrugados;
    }

    public void setCartonesCorrugados(List cartonesCorrugados) {
        this.cartonesCorrugados = cartonesCorrugados;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public ResultDataModel getComponentesList() {
        return componentesList;
    }

    public void setComponentesList(ResultDataModel componentesList) {
        this.componentesList = componentesList;
    }

    public List<ComponentesProd> getListaComponentesBuscar() {
        return listaComponentesBuscar;
    }

    public void setListaComponentesBuscar(List<ComponentesProd> listaComponentesBuscar) {
        this.listaComponentesBuscar = listaComponentesBuscar;
    }

    public List<ComponentesProd> getListaComponentesSeleccionados() {
        return listaComponentesSeleccionados;
    }

    public void setListaComponentesSeleccionados(List<ComponentesProd> listaComponentesSeleccionados) {
        this.listaComponentesSeleccionados = listaComponentesSeleccionados;
    }


    public List getEliminaComponentesSeleccionados() {
        return eliminaComponentesSeleccionados;
    }

    public void setEliminaComponentesSeleccionados(List eliminaComponentesSeleccionados) {
        this.eliminaComponentesSeleccionados = eliminaComponentesSeleccionados;
    }

    public boolean isSwEliminaSi() {
        return swEliminaSi;
    }

    public void setSwEliminaSi(boolean swEliminaSi) {
        this.swEliminaSi = swEliminaSi;
    }

    public boolean isSwEliminaNo() {
        return swEliminaNo;
    }

    public void setSwEliminaNo(boolean swEliminaNo) {
        this.swEliminaNo = swEliminaNo;
    }

    public String getPrincipioActivo() {
        return principioActivo;
    }

    public void setPrincipioActivo(String principioActivo) {
        this.principioActivo = principioActivo;
    }

    public List getListaComponentesLista() {
        return listaComponentesLista;
    }

    public void setListaComponentesLista(List listaComponentesLista) {
        this.listaComponentesLista = listaComponentesLista;
    }

    public List getComponentespresentaciones() {
        return componentespresentaciones;
    }

    public void setComponentespresentaciones(List componentespresentaciones) {
        this.componentespresentaciones = componentespresentaciones;
    }

    public List getTiposPresentacion() {
        return tiposPresentacion;
    }

    public void setTiposPresentacion(List tiposPresentacion) {
        this.tiposPresentacion = tiposPresentacion;
    }

    public HtmlDataTable getComponentesSeleccionadosDataTable() {
        return componentesSeleccionadosDataTable;
    }

    public void setComponentesSeleccionadosDataTable(HtmlDataTable componentesSeleccionadosDataTable) {
        this.componentesSeleccionadosDataTable = componentesSeleccionadosDataTable;
    }

    public ComponentesProd getEditarComponente() {
        return editarComponente;
    }

    public void setEditarComponente(ComponentesProd editarComponente) {
        this.editarComponente = editarComponente;
    }

    public List getComponenteEditarList() {
        return componenteEditarList;
    }

    public void setComponenteEditarList(List componenteEditarList) {
        this.componenteEditarList = componenteEditarList;
    }

    public String getEditarCantidadCompprod() {
        return editarCantidadCompprod;
    }

    public void setEditarCantidadCompprod(String editarCantidadCompprod) {
        this.editarCantidadCompprod = editarCantidadCompprod;
    }

    public String getEditarCodCompprod() {
        return editarCodCompprod;
    }

    public void setEditarCodCompprod(String editarCodCompprod) {
        this.editarCodCompprod = editarCodCompprod;
    }

    public String getEditarNombreProdSemiterminado() {
        return editarNombreProdSemiterminado;
    }

    public void setEditarNombreProdSemiterminado(String editarNombreProdSemiterminado) {
        this.editarNombreProdSemiterminado = editarNombreProdSemiterminado;
    }

    public HtmlDataTable getComponentesBuscarDataTable() {
        return componentesBuscarDataTable;
    }

    public void setComponentesBuscarDataTable(HtmlDataTable componentesBuscarDataTable) {
        this.componentesBuscarDataTable = componentesBuscarDataTable;
    }

    public List getCategoriaList() {
        return categoriaList;
    }

    public void setCategoriaList(List categoriaList) {
        this.categoriaList = categoriaList;
    }

    public HtmlSelectOneMenu getComponentesEditarSelectOneMenu() {
        return componentesEditarSelectOneMenu;
    }

    public void setComponentesEditarSelectOneMenu(HtmlSelectOneMenu componentesEditarSelectOneMenu) {
        this.componentesEditarSelectOneMenu = componentesEditarSelectOneMenu;
    }    

    public String getCargarComponentespresentaciones(){
        String codpresentacion=Util.getParameter("codpresentacion");
        
        try {
            String sql="select c.cod_compprod,c.cod_envaseprim,c.cod_colorpresprimaria,c.volumenpeso_envaseprim," +
                    " c.cantidad_compprod,c.cod_sabor from componentes_prod c,componentes_presprod cp";
            sql+="  where  c.cod_compprod=cp.cod_compprod and cp.cod_presentacion="+codpresentacion;
            System.out.println("sql:"+sql);
            con=Util.openConnection(con);
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs=st.executeQuery(sql);
            componentespresentaciones.clear();
            
            while (rs.next()){
                componentesProd=new ComponentesProd();
                String cod="";
                componentesProd.setCodCompprod(rs.getString(1));
                cargarPrincipiosActivos(componentesProd.getCodCompprod());
                cod=rs.getString(2);
                System.out.println("sql:"+componentesProd.getCodCompprod());
                cod=(cod==null)?"":cod;
                cargarEnvasePrimario(cod);
                System.out.println("sql:"+componentesProd.getEnvasesPrimarios().getNombreEnvasePrim());
                cod=rs.getString(3);
                cod=(cod==null)?"":cod;
                System.out.println("st xxx:"+st);
                //cargarColor(cod);
                System.out.println("sql:"+componentesProd.getColoresPresentacion().getNombreColor());
                componentesProd.setVolumenPesoEnvasePrim(rs.getString(4));
                System.out.println("sql:"+componentesProd.getVolumenPesoEnvasePrim());                componentesProd.setCantidadCompprod(rs.getString(5));
                System.out.println("sql:"+componentesProd.getCantidadCompprod());
                cod=rs.getString(6);
                cod=(cod==null)?"":cod;
                cargarSabor(cod);
                principioActivo=componentesProd.getSaboresProductos().getNombreSabor()+principioActivo;
                componentesProd.getSaboresProductos().setNombreSabor(principioActivo);
                System.out.println("sql:"+componentesProd.getSaboresProductos().getNombreSabor());
                System.out.println("principios:"+componentesProd.getSaboresProductos().getNombreSabor());
                componentespresentaciones.add(componentesProd);
            }
            if(rs!=null){
                rs.close();
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return "";
    }
    public ManagedPresentacionesProducto() {
        //cargarPresentacionesProducto();
        //cargarLineaMKT("",null);
        //cargarTipoMercaderia("",null);
        //cargarTiposPresentacion();
    }
    public String getCargarDatosAgregarPresentacionesProducto(){
        cargarLineaMKT("",null);
        cargarTipoMercaderia("",null);
        cargarTiposPresentacion();
        listaComponentesSeleccionados.clear();
        return null;
    }
    
    public String getObtenerCodigo(){
        String cod=Util.getParameter("codigo");
        System.out.println("CodProd :"+cod);
        if(cod!=null){
            setCodigo(cod);
        }
        // cargarProdFormasFar();
        return "";
    }
    public void cargarTiposPresentacion(){
        try {
            setCon(Util.openConnection(getCon()));
            String sql=" select TP.COD_TIPO_PRESENTACION, TP.NOMBRE_TIPO_PRESENTACION from TIPOS_PRESENTACION TP ";
            System.out.println("entro cargar tipos de presentacion:" + sql);
            ResultSet rs=null;
            Statement st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            rs=st.executeQuery(sql);
            tiposPresentacion.clear();
            while (rs.next())
                tiposPresentacion.add(new SelectItem(rs.getString("COD_TIPO_PRESENTACION"),rs.getString("NOMBRE_TIPO_PRESENTACION")) );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * -------------------------------------------------------------------------
     * CARGAR DATOS PRESENTACIONES PRODUCTO
     * -------------------------------------------------------------------------
     **/
    public String cargarNombrePresentacion(String cod){
        String nombre="";
        String sql_aux = "";
        try{
            sql_aux=" select (select sp.nombre_sabor from SABORES_PRODUCTO sp where sp.COD_SABOR =cp.COD_SABOR and cp.cod_sabor<>0) as nombreSabor,";
            sql_aux+=" cp.VOLUMENPESO_ENVASEPRIM,";
            sql_aux+=" (select es.NOMBRE_ENVASESEC from ENVASES_SECUNDARIOS es where es.COD_ENVASESEC = pp.COD_ENVASESEC) as nombreEnvaseSec,";
            sql_aux+=" pp.cantidad_presentacion,";
            sql_aux+=" (select ff.nombre_forma from FORMAS_FARMACEUTICAS ff where ff.cod_forma= cp.cod_forma) as nombreForma ";            
            sql_aux+=" from COMPONENTES_PROD cp, COMPONENTES_PRESPROD cpp, PRESENTACIONES_PRODUCTO pp";
            sql_aux+=" where cp.COD_COMPPROD = cpp.COD_COMPPROD";
            sql_aux+=" and cpp.COD_PRESENTACION = pp.cod_presentacion";            
            sql_aux+=" and pp.cod_presentacion='"+cod+"'";
            
            System.out.println("PresentacionesProducto:sql:"+sql_aux);
            con=Util.openConnection(con);
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs=st.executeQuery(sql_aux);
            while (rs.next()){                
                String aux=rs.getString(1);
                System.out.println("aux:"+aux);
                if(aux!=null){
                    nombre+=aux+" ";
                }
                nombre+=rs.getString(2)+" "+rs.getString(3)+" x "+rs.getString(4)+" "+rs.getString(5);
            }
            if(rs!=null){
                rs.close();
                st.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nombre;
    }
    
    public String obtenerFormaFarmaceutica(String codprod) throws SQLException{
        String sql=" select ff.nombre_forma from FORMAS_FARMACEUTICAS ff,COMPONENTES_PROD cp where ff.cod_forma= cp.cod_forma and cod_prod="+codprod;
        Statement st=con.createStatement();
        ResultSet rs=st.executeQuery(sql);
        String nombre_forma="";
        while (rs.next())
            nombre_forma+=rs.getString(1)+" ";
        rs.close();
        st.close();
        return nombre_forma;
    }
    
    
    public String obtenerSabor(String codprod) throws SQLException{
        String sql=" select sp.nombre_sabor from SABORES_PRODUCTO sp,COMPONENTES_PROD cp where sp.COD_SABOR =cp.COD_SABOR and cod_prod="+codprod;
        Statement st=con.createStatement();
        ResultSet rs=st.executeQuery(sql);
        String nombre_sabor="";
        while (rs.next())
            nombre_sabor+=rs.getString(1)+" ";
        rs.close();
        st.close();
        return nombre_sabor;
    }
    
    public String obtenerNombreColorpresprimaria(String codprod) throws SQLException{
        String sql="select cp1.nombre_colorpresprimaria from COLORES_PRESPRIMARIA cp1,COMPONENTES_PROD cp  where cp1.COD_COLORPRESPRIMARIA = cp.COD_COLORPRESPRIMARIA and cod_prod="+codprod;
        Statement st=con.createStatement();
        ResultSet rs=st.executeQuery(sql);
        String nombre_colorpresprimaria="";
        while (rs.next())
            nombre_colorpresprimaria+=rs.getString(1)+" ";
        rs.close();
        st.close();
        return nombre_colorpresprimaria;
    }
    
    public String obtenerNombreEnvaseprim(String codprod) throws SQLException{
        String sql="select ep.nombre_envaseprim from ENVASES_PRIMARIOS ep, COMPONENTES_PROD cp where  ep.cod_envaseprim = cp.cod_envaseprim and cod_prod="+codprod;
        Statement st=con.createStatement();
        ResultSet rs=st.executeQuery(sql);
        String nombre_envaseprim="";
        while (rs.next())
            nombre_envaseprim+=rs.getString(1)+" ";
        rs.close();
        st.close();
        return nombre_envaseprim; 
    }
    
    public void cargarPresentacionesProducto(){
        getPresentacionesProductoList().clear();
        try {
            String sql=" select * from (select ROW_NUMBER() over(order by nombre_producto_presentacion ASC ) as 'FILAS', cod_prod,(select nombre_prod from PRODUCTOS p where p.cod_prod=pp.cod_prod) as nombre_prod,";
            sql+=" COD_PRESENTACION, ";
            sql+=" cant_envase_secundario,COD_ENVASESEC,COD_ENVASETERCIARIO,COD_LINEAMKT,cantidad_presentacion, ";
            sql+=" cod_tipomercaderia,COD_CARTON,OBS_PRESENTACION,cod_estado_registro,cod_anterior, ";
            sql+=" (select nombre_lineamkt from lineas_mkt mkt where  mkt.cod_estado_registro=1 and mkt.COD_LINEAMKT=pp.COD_LINEAMKT) AS nombre_lineamkt ";
            sql+=" ,(select nombre_tipomercaderia from tipos_mercaderia tm where tm.cod_estado_registro=1 and tm.cod_tipomercaderia=pp.cod_tipomercaderia) AS nombre_tipomercaderia ";
            sql+=" ,(select nombre_carton from cartones_corrugados ct where ct.cod_estado_registro=1 and ct.COD_CARTON=pp.COD_CARTON) AS nombre_carton ";
            sql+=" ,(select nombre_estado_registro from estados_referenciales er  where er.cod_estado_registro<>3 and er.COD_ESTADO_REGISTRO=pp.cod_estado_registro) AS nombre_estado_registro ";
            sql+=" ,nombre_producto_presentacion" +
                 " ,cod_tipo_presentacion,cod_categoria ";
            sql+=" from PRESENTACIONES_PRODUCTO pp  ";
            
            if(!getPresentacionesProducto().getEstadoReferencial().getCodEstadoRegistro().equals("") && !getPresentacionesProducto().getEstadoReferencial().getCodEstadoRegistro().equals("3")){
                sql+=" where cod_estado_registro="+getPresentacionesProducto().getEstadoReferencial().getCodEstadoRegistro();
            }
            if(!getPresentacionesProducto().getLineaMKT().getCodLineaMKT().equals("") && !getPresentacionesProducto().getLineaMKT().getCodLineaMKT().equals("0")){
                sql+=" and cod_lineamkt="+getPresentacionesProducto().getLineaMKT().getCodLineaMKT();
            }
            if(!getPresentacionesProducto().getTiposMercaderia().getCodTipoMercaderia().equals("") && !getPresentacionesProducto().getTiposMercaderia().getCodTipoMercaderia().equals("0")){
                sql+=" and cod_tipomercaderia="+getPresentacionesProducto().getTiposMercaderia().getCodTipoMercaderia();
            }            
            sql+=" ) AS PRESENTACIONES_PRODUCTO_LISTADO WHERE FILAS BETWEEN 1 AND 400 ";
            
            System.out.println("PresentacionesProducto:sql:"+sql);
            System.out.println("HHHHHHHHHHH----->>>>>>>>>" +getPresentacionesProducto().getEstadoReferencial().getCodEstadoRegistro() );
            
            con=Util.openConnection(con);
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs=st.executeQuery(sql);
            String cod="";
            presentacionesProductoList.clear();
            cantidadfilas=0;
            while (rs.next()){
                cantidadfilas++;
                PresentacionesProducto bean=new PresentacionesProducto();
                //String data[]=new String [10];
                String cod_prod=rs.getString(2);//cod_prod
                String nombre_prod=rs.getString(3);//nombre_prod
                //data[2]=rs.getString(3);//nombreEnvasePrim
                String nombreEnvasePrim=obtenerNombreEnvaseprim(cod_prod);
                String nombreColor=obtenerNombreColorpresprimaria(cod_prod);                
                String nombreSabor=obtenerSabor(cod_prod);
                String nombreEnvaseSec=obtenerNombreEnvaseprim(cod_prod);
                String cod_presentacion=rs.getString(4);//COD_PRESENTACION                
                String nombreForma=obtenerFormaFarmaceutica(cod_prod);
                bean.getProducto().setNombreProducto(nombre_prod);
                bean.getProducto().setCodProducto(cod_prod);
                bean.setCantEnvaseSecundario(rs.getString(5));
                bean.setCodPresentacion(cod_presentacion);
                bean.getEnvasesSecundarios().setCodEnvaseSec(rs.getString(6));//COD_ENVASESEC
                rs.getString(7);//COD_ENVASETERCIARIO
                String COD_LINEAMKT=rs.getString(8);//COD_LINEAMKT
                String cantidad_presentacion=rs.getString(9);//cantidad_presentacion
                String cod_tipomercaderia=rs.getString(10);//cod_tipomercaderia
                String COD_CARTON =rs.getString(11);//COD_CARTON
                rs.getString(12);//OBS_PRESENTACION
                rs.getString(13);//cod_estado_registro
                bean.setCodAnterior(rs.getString(14));//cod_anterior
                bean.getLineaMKT().setNombreLineaMKT(rs.getString(15));
                bean.getLineaMKT().setCodLineaMKT(COD_LINEAMKT);
                bean.setCantidadPresentacion(cantidad_presentacion);
                bean.getTiposMercaderia().setNombreTipoMercaderia(rs.getString(16));
                bean.getTiposMercaderia().setCodTipoMercaderia(cod_tipomercaderia);
                bean.getCartonesCorrugados().setNombreCarton(rs.getString(17));
                bean.getCartonesCorrugados().setCodCaton(COD_CARTON);
                bean.getEstadoReferencial().setNombreEstadoRegistro(rs.getString(18));
                bean.getEstadoReferencial().setCodEstadoRegistro(cod);
                bean.setNombreProductoPresentacion(rs.getString(19));
                bean.getTiposPresentacion().setCodTipoPresentacion(rs.getInt("cod_tipo_presentacion"));
                bean.getCategoriasProducto().setCodCategoria(rs.getInt("cod_categoria"));
                bean.setObsPresentacion(rs.getString("OBS_PRESENTACION"));
                bean.setCodAnterior(rs.getString("cod_anterior"));
                
                presentacionesProductoList.add(bean);
            }
            if(rs!=null){
                rs.close();st.close();
                rs=null;st=null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * -------------------------------------------------------------------------
     * ESTADO REGISTRO
     * -------------------------------------------------------------------------
     **/
    public void changeEvent(ValueChangeEvent event){
        System.out.println("event:"+event.getNewValue());
        getPresentacionesProducto().getEstadoReferencial().setCodEstadoRegistro(event.getNewValue().toString());
        //cargarPresentacionesProducto();
        reset();
    }
    public void changeEventLinea(ValueChangeEvent event){
        System.out.println("event:"+event.getNewValue());
        getPresentacionesProducto().getLineaMKT().setCodLineaMKT(event.getNewValue().toString());
        //cargarPresentacionesProducto();
        reset();
    }
    public void changeEventTipoMer(ValueChangeEvent event){
        System.out.println("event:"+event.getNewValue());
        getPresentacionesProducto().getTiposMercaderia().setCodTipoMercaderia(event.getNewValue().toString());
        //cargarPresentacionesProducto();
        reset();
    }
    /**
     * -------------------------------------------------------------------------
     * CARGAR ESTADO REGISTRO
     * -------------------------------------------------------------------------
     **/
    public void cargarEstadoRegistro(String codigo,PresentacionesProducto bean){
        try {
            setCon(Util.openConnection(getCon()));
            String sql="select cod_estado_registro,nombre_estado_registro from estados_referenciales where cod_estado_registro<>3";
            ResultSet rs=null;
            
            Statement st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            if(!codigo.equals("")){
                sql+=" and cod_estado_registro="+codigo;
                rs=st.executeQuery(sql);
                if(rs.next()){
                    bean.getEstadoReferencial().setCodEstadoRegistro(rs.getString(1));
                    bean.getEstadoReferencial().setNombreEstadoRegistro(rs.getString(2));
                }
            } else{
                getEstadoRegistro().clear();
                rs=st.executeQuery(sql);
                while (rs.next())
                    getEstadoRegistro().add(new SelectItem(rs.getString(1),rs.getString(2)));
            }
            
            if(rs!=null){
                rs.close();st.close();
                rs=null;st=null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void cargarCartonCorrugado(String codigo,PresentacionesProducto bean){
        try {
            setCon(Util.openConnection(getCon()));
            String sql="select cod_carton,nombre_carton from cartones_corrugados where cod_estado_registro=1";
            ResultSet rs=null;
            Statement st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            if(!codigo.equals("")){
                sql+=" and cod_carton="+codigo;
                rs=st.executeQuery(sql);
                if(rs.next()){
                    bean.getCartonesCorrugados().setCodCaton(rs.getString(1));
                    bean.getCartonesCorrugados().setNombreCarton(rs.getString(2));
                }
            } else{
                getCartonesCorrugados().clear();
                rs=st.executeQuery(sql);
                getCartonesCorrugados().add(new SelectItem("0"," "));
                while (rs.next())
                    getCartonesCorrugados().add(new SelectItem(rs.getString(1),rs.getString(2)));
            }
            if(rs!=null){
                rs.close();st.close();
                rs=null;st=null;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
    public void cargarEnvaseSecundario(String codigo,PresentacionesProducto bean){
        try {
            setCon(Util.openConnection(getCon()));
            String sql="select cod_envasesec,nombre_envasesec from envases_secundarios where cod_estado_registro=1";
            ResultSet rs=null;
            Statement st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            if(!codigo.equals("")){
                sql+=" and cod_envasesec="+codigo;
                rs=st.executeQuery(sql);
                if(rs.next()){
                    bean.getEnvasesSecundarios().setCodEnvaseSec(rs.getString(1));
                    bean.getEnvasesSecundarios().setNombreEnvaseSec(rs.getString(2));
                }
            } else{
                getEnvasesSecundarios().clear();
                rs=st.executeQuery(sql);
                getEnvasesSecundarios().add(new SelectItem("0","            "));
                while (rs.next())
                    getEnvasesSecundarios().add(new SelectItem(rs.getString(1),rs.getString(2)));
            }
       
            
            if(rs!=null){
                rs.close();st.close();
                rs=null;st=null;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
    public void cargarEnvaseTerciario(String codigo,PresentacionesProducto bean){
        try {
            setCon(Util.openConnection(getCon()));
            String sql="select cod_envaseterciario,nombre_envaseterciario from envases_terciarios where cod_estado_registro=1";
            ResultSet rs=null;
            Statement st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            if(!codigo.equals("")){
                sql+=" and cod_envaseterciario="+codigo;
                rs=st.executeQuery(sql);
                if(rs.next()){
                    bean.getEnvasesTerciarios().setCodEnvaseTerciario(rs.getString(1));
                    bean.getEnvasesTerciarios().setNombreEnvaseTerciario(rs.getString(2));
                }
            } else{
                getEnvasesTerciarios().clear();
                rs=st.executeQuery(sql);
                getEnvasesTerciarios().add(new SelectItem("0","            "));
                while (rs.next())
                    getEnvasesTerciarios().add(new SelectItem(rs.getString(1),rs.getString(2)));
            }
            if(rs!=null){
                rs.close();st.close();
                rs=null;st=null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
    public void cargarProducto(String codigo,PresentacionesProducto bean){
        try {
            setCon(Util.openConnection(getCon()));
            String sql="select cod_prod,nombre_prod from productos";
            ResultSet rs=null;
            
            Statement st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            if(!codigo.equals("")){
                sql+=" where cod_prod="+codigo;
                sql+=" order by nombre_prod";
                System.out.println("GGGGGGGGGGGGGGGGGG---->" + sql);
                rs=st.executeQuery(sql);
                if(rs.next()){
                    bean.getProducto().setCodProducto(rs.getString(1));
                    bean.getProducto().setNombreProducto(rs.getString(2));
                }
            } else{
                sql+=" order by nombre_prod";
                getProductos().clear();
                rs=st.executeQuery(sql);
                getProductos().add(new SelectItem("0"," "));
                while (rs.next())
                    getProductos().add(new SelectItem(rs.getString(1),rs.getString(2)));
            }
            if(rs!=null){
                rs.close();st.close();
                rs=null;st=null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void cargarLineaMKT(String codigo,PresentacionesProducto bean){
        try {
            con=Util.openConnection(con);
            String sql="select cod_lineamkt,nombre_lineamkt from lineas_mkt where cod_estado_registro=1";
            ResultSet rs=null;
            
            Statement st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            if(!codigo.equals("")){
                sql+=" and cod_lineamkt="+codigo;
                sql+=" order by nombre_lineamkt";
                rs=st.executeQuery(sql);
                if(rs.next()){
                    bean.getLineaMKT().setCodLineaMKT(rs.getString(1));
                    bean.getLineaMKT().setNombreLineaMKT(rs.getString(2));
                }
                
            } else{
                sql+=" order by nombre_lineamkt";
                getLineasMKTList().clear();
                rs=st.executeQuery(sql);
                getLineasMKTList().add(new SelectItem("0","TODOS"));
                while (rs.next())
                    getLineasMKTList().add(new SelectItem(rs.getString(1),rs.getString(2)));
            }
            if(rs!=null){
                rs.close();st.close();
                rs=null;st=null;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void cargarTipoMercaderia(String codigo,PresentacionesProducto bean){
        try {
            setCon(Util.openConnection(getCon()));
            String sql="select cod_tipomercaderia,nombre_tipomercaderia from tipos_mercaderia where cod_estado_registro=1 and cod_tipomercaderia in (1,5,8,7)";
            ResultSet rs=null;
            
            Statement st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            if(!codigo.equals("")){
                sql+=" and cod_tipomercaderia="+codigo;
                rs=st.executeQuery(sql);
                if(rs.next()){
                    bean.getTiposMercaderia().setCodTipoMercaderia(rs.getString(1));
                    bean.getTiposMercaderia().setNombreTipoMercaderia(rs.getString(2));
                }
            } else{
                getTiposMercaderia().clear();
                rs=st.executeQuery(sql);
                getTiposMercaderia().add(new SelectItem("0","TODOS"));
                while (rs.next())
                    getTiposMercaderia().add(new SelectItem(rs.getString(1),rs.getString(2)));
            }
            if(rs!=null){
                rs.close();st.close();
                rs=null;st=null;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void generarCodigo(){
        try {
            String  sql="select max(cod_presentacion)+1 from presentaciones_producto";
            setCon(Util.openConnection(getCon()));
            Statement st=getCon().createStatement();
            ResultSet rs=st.executeQuery(sql);
            if(rs.next()){
                String cod=rs.getString(1);
                if(cod==null)
                    getPresentacionesProducto().setCodPresentacion("1");
                else
                    getPresentacionesProducto().setCodPresentacion(cod);
            }
            if(rs!=null){
                rs.close();
                st.close();
            }       
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    
    
    
    ////////////////////////////////////////////////////
    public String actionSavePresentacionesProducto(){
        clear();
        componentesProd=new ComponentesProd();
        getListaComponentesSeleccionados().add(componentesProd);
        //listaComponentesSeleccionados.clear();
        presentacionesProducto= new PresentacionesProducto();
        cargarProducto("",null);
        cargarTipoMercaderia("",null);
        cargarEnvaseSecundario("",null);
        cargarEnvaseTerciario("",null);
        cargarCartonCorrugado("",null);
        cargarEstadoRegistro("",null);
        cargarLineaMKT("",null);
        this.cargarCategoria();
        return "actionSave";
    }
    ////////////////////////////////////////////////////
    
    public void cargarCategoria(){
        try {
            categoriaList.clear();            
            con = Util.openConnection(con);
            String sql="SELECT cp.COD_CATEGORIA,cp.NOMBRE_CATEGORIA FROM CATEGORIAS_PRODUCTO cp";
            ResultSet rs=null;
            
            Statement st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                rs=st.executeQuery(sql);
                while(rs.next()){
                    categoriaList.add(new SelectItem( rs.getString("COD_CATEGORIA"),rs.getString("NOMBRE_CATEGORIA")));
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String actionCancelar(){
        cargarPresentacionesProducto();
        this.redireccionar("navegadorpresentacionesproducto.jsf");
        return null;
    }
    public String actionMostrarNombre(){
        try {
            System.out.println("entro a mostrar nombre");

            Iterator i=getListaComponentesSeleccionados().iterator();
            String nombreComercial="", nombreEnvaseSecundario="", volumenPeso="", nombreColor="", nombreEnvasePrimario="", nombreSabor="", nombreForma="";
            String cantidadPresentacion="", codEnvasePrimario="", codForma="";
            while (i.hasNext()){
                ComponentesProd bean=(ComponentesProd)i.next();
                String sql="select cp.COD_COMPPROD, cp.VOLUMENPESO_ENVASEPRIM, p.nombre_prod, cp.cod_envaseprim, cp.cod_forma, " +
                        "(select c.NOMBRE_COLORPRESPRIMARIA from COLORES_PRESPRIMARIA c where c.COD_COLORPRESPRIMARIA=cp.cod_colorpresprimaria) as nombre_color," +
                        "(select e.nombre_envaseprim from ENVASES_PRIMARIOS e where e.cod_envaseprim=cp.cod_envaseprim) as nombre_envaseprimario, " +
                        "(select s.NOMBRE_SABOR from SABORES_PRODUCTO s where s.COD_SABOR=cp.cod_sabor) as nombre_sabor, " +
                        "(select f.nombre_forma from FORMAS_FARMACEUTICAS f where f.cod_forma=cp.cod_forma) as forma_farmaceutica " +
                        "from COMPONENTES_PROD cp, productos p where p.cod_prod=cp.cod_prod and cp.cod_compprod="+bean.getCodCompprod();
                
                System.out.println("rrrrrrrrrrrrrrrrrrrrr----->>>>>" + sql);
                
                Statement st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs=st.executeQuery(sql);
                while(rs.next()){
                    volumenPeso=rs.getString("volumenpeso_envaseprim");
                    nombreComercial=rs.getString("nombre_prod");
                    codEnvasePrimario=rs.getString("cod_envaseprim");
                    codForma=rs.getString("cod_forma");
                    nombreColor=rs.getString("nombre_color");
                    nombreEnvasePrimario=rs.getString("nombre_envaseprimario");
                    nombreSabor=rs.getString("nombre_sabor");
                    if(nombreSabor==null){
                        nombreSabor="";
                    }
                    nombreForma=rs.getString("forma_farmaceutica");
                }
            }
            String sqlPresentaciones="select nombre_envasesec from envases_secundarios " +
                    "where cod_envasesec="+presentacionesProducto.getEnvasesSecundarios().getCodEnvaseSec();
            System.out.println("SQLPRESENTACIONES:"+sqlPresentaciones);
            Statement stPresentaciones=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rsPresentaciones=stPresentaciones.executeQuery(sqlPresentaciones);
            while(rsPresentaciones.next()){
                nombreEnvaseSecundario=rsPresentaciones.getString("nombre_envasesec");
            }
            cantidadPresentacion=getPresentacionesProducto().getCantidadPresentacion();
            System.out.println("nombreproducto: "+nombreComercial);
            System.out.println("envase: "+nombreEnvaseSecundario);
            System.out.println("CantidadPres: "+cantidadPresentacion);
            if(codForma.equals("1") || codForma.equals("6") || codForma.equals("8") || codForma.equals("9") || codForma.equals("15") || codForma.equals("19") || codForma.equals("22") || codForma.equals("23") || codForma.equals("30")) {
                getPresentacionesProducto().setNombreProductoPresentacion(nombreComercial+" "+nombreEnvaseSecundario+" x "+cantidadPresentacion+" "+nombreForma);
            }
            if(codForma.equals("2")){
                getPresentacionesProducto().setNombreProductoPresentacion(nombreComercial+" "+nombreEnvaseSecundario+" x "+cantidadPresentacion+" "+nombreForma+" DE "+volumenPeso);
            }
            if(codForma.equals("7") || codForma.equals("10") || codForma.equals("14") || codForma.equals("16") || codForma.equals("17") || codForma.equals("28") || codForma.equals("29") || codForma.equals("18") || codForma.equals("25") || codForma.equals("26") || codForma.equals("27")){
                getPresentacionesProducto().setNombreProductoPresentacion(nombreComercial+" "+nombreForma+" "+nombreEnvasePrimario+" x "+volumenPeso);
            }
            if(codForma.equals("12") || codForma.equals("13") || codForma.equals("21")){
                getPresentacionesProducto().setNombreProductoPresentacion(nombreComercial+" "+nombreEnvasePrimario+" x "+volumenPeso);
            }
            if(codForma.equals("20")){
                getPresentacionesProducto().setNombreProductoPresentacion(nombreComercial+" "+nombreForma+" x "+cantidadPresentacion+" "+nombreEnvasePrimario+"S");
            }
            if(codForma.equals("11")){
                getPresentacionesProducto().setNombreProductoPresentacion(nombreComercial+" "+nombreForma+" "+nombreEnvaseSecundario+" x "+cantidadPresentacion+" "+nombreEnvasePrimario);
            }
        } catch (Exception e) {
            System.out.println("Excepcion:"+ e);
        }
        return "";
    }
    public String cambiarNombres(){
        try {
            String sqlComponentes="select cod_compprod from componentes_prod";
            Connection con=CofarConnection.getConnectionJsp();
            Statement stComponentes=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rsComponentes=stComponentes.executeQuery(sqlComponentes);
            
            String nombreComercial="", nombreEnvaseSecundario="", volumenPeso="", nombreColor="", nombreEnvasePrimario="", nombreSabor="", nombreForma="";
            String cantidadPresentacion="", codEnvasePrimario="", codForma="";
            String codComponente="";
            while (rsComponentes.next()){
                codComponente=rsComponentes.getString("cod_compprod");
                String sql="select cp.COD_COMPPROD, cp.VOLUMENPESO_ENVASEPRIM, p.nombre_prod, cp.cod_envaseprim, cp.cod_forma, " +
                        "(select c.NOMBRE_COLORPRESPRIMARIA from COLORES_PRESPRIMARIA c where c.COD_COLORPRESPRIMARIA=cp.cod_colorpresprimaria) as nombre_color," +
                        "(select e.nombre_envaseprim from ENVASES_PRIMARIOS e where e.cod_envaseprim=cp.cod_envaseprim) as nombre_envaseprimario, " +
                        "(select s.NOMBRE_SABOR from SABORES_PRODUCTO s where s.COD_SABOR=cp.cod_sabor) as nombre_sabor, " +
                        "(select f.nombre_forma from FORMAS_FARMACEUTICAS f where f.cod_forma=cp.cod_forma) as forma_farmaceutica " +
                        "from COMPONENTES_PROD cp, productos p where p.cod_prod=cp.cod_prod and cp.cod_compprod="+codComponente;
                Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs=st.executeQuery(sql);
                while(rs.next()){
                    volumenPeso=rs.getString("volumenpeso_envaseprim");
                    nombreComercial=rs.getString("nombre_prod");
                    codEnvasePrimario=rs.getString("cod_envaseprim");
                    codForma=rs.getString("cod_forma");
                    nombreColor=rs.getString("nombre_color");
                    nombreEnvasePrimario=rs.getString("nombre_envaseprimario");
                    nombreSabor=rs.getString("nombre_sabor");
                    if(nombreSabor==null){
                        nombreSabor="";
                    }
                    nombreForma=rs.getString("forma_farmaceutica");
                }
                String sqlPresProducto="select cp.cod_presentacion, p.cod_envasesec, p.cantidad_presentacion from componentes_presprod cp, presentaciones_producto p " +
                        "where cp.cod_presentacion=p.cod_presentacion and cod_compprod="+codComponente;
                Statement stPresProducto=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rsPresProducto=stPresProducto.executeQuery(sqlPresProducto);
                System.out.println("sql presproducto: "+sqlPresProducto);
                while(rsPresProducto.next()){
                    System.out.println("entro");
                    String codPresentacion=rsPresProducto.getString("cod_presentacion");
                    String codEnvaseSec=rsPresProducto.getString("cod_envasesec");
                    cantidadPresentacion=rsPresProducto.getString("cantidad_presentacion");
                    
                    String sqlPresentaciones="select nombre_envasesec from envases_secundarios " +
                            "where cod_envasesec="+codEnvaseSec;
                    
                    System.out.println("SQLPRESENTACIONES:"+sqlPresentaciones);
                    
                    Statement stPresentaciones=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    ResultSet rsPresentaciones=stPresentaciones.executeQuery(sqlPresentaciones);
                    while(rsPresentaciones.next()){
                        nombreEnvaseSecundario=rsPresentaciones.getString("nombre_envasesec");
                    }
                    System.out.println("nombreproducto: "+nombreComercial);
                    System.out.println("envase: "+nombreEnvaseSecundario);
                    System.out.println("CantidadPres: "+cantidadPresentacion);
                    String nombrePresentacionProducto="";
                    if(codForma.equals("1") || codForma.equals("6") || codForma.equals("8") || codForma.equals("9") || codForma.equals("15") || codForma.equals("19") || codForma.equals("22") || codForma.equals("23") || codForma.equals("30")) {
                        System.out.println(nombreComercial+" "+nombreEnvaseSecundario+" x "+cantidadPresentacion+" "+nombreForma);
                        nombrePresentacionProducto=nombreComercial+" "+nombreEnvaseSecundario+" x "+cantidadPresentacion+" "+nombreForma;
                    }
                    if(codForma.equals("2")){
                        System.out.println(nombreComercial+" "+nombreEnvaseSecundario+" x "+cantidadPresentacion+" "+nombreForma+" DE "+volumenPeso);
                        nombrePresentacionProducto=nombreComercial+" "+nombreEnvaseSecundario+" x "+cantidadPresentacion+" "+nombreForma+" DE "+volumenPeso;
                    }
                    if(codForma.equals("7") || codForma.equals("10") || codForma.equals("14") || codForma.equals("16") || codForma.equals("17") || codForma.equals("28") || codForma.equals("29") || codForma.equals("18") || codForma.equals("25") || codForma.equals("26") || codForma.equals("27")){
                        System.out.println(nombreComercial+" "+nombreForma+" "+nombreEnvasePrimario+" x "+volumenPeso);
                        nombrePresentacionProducto=nombreComercial+" "+nombreForma+" "+nombreEnvasePrimario+" x "+volumenPeso;
                    }
                    if(codForma.equals("12") || codForma.equals("13") || codForma.equals("21")){
                        System.out.println(nombreComercial+" "+nombreEnvasePrimario+" x "+volumenPeso);
                        nombrePresentacionProducto=nombreComercial+" "+nombreEnvasePrimario+" x "+volumenPeso;
                    }
                    if(codForma.equals("20")){
                        System.out.println(nombreComercial+" "+nombreForma+" x "+cantidadPresentacion+" "+nombreEnvasePrimario);
                        nombrePresentacionProducto=nombreComercial+" "+nombreForma+" x "+cantidadPresentacion+" "+nombreEnvasePrimario+"S";
                    }
                    if(codForma.equals("11")){
                        System.out.println(nombreComercial+" "+nombreForma+" "+nombreEnvaseSecundario+" x "+cantidadPresentacion+" "+nombreEnvasePrimario);
                        nombrePresentacionProducto=nombreComercial+" "+nombreForma+" "+nombreEnvaseSecundario+" x "+cantidadPresentacion+" "+nombreEnvasePrimario;
                    }
                    String sqlUpdate="update presentaciones_producto set " +
                            "nombre_producto_presentacion='"+nombrePresentacionProducto+"' where cod_presentacion="+codPresentacion;
                    Statement stUpdate=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    stUpdate.executeUpdate(sqlUpdate);
                }
            }
            
        } catch (Exception e) {
            System.out.println("Excepcion:"+ e);
        }
        return "";
    }
    public static void main(String[] args) {
        ManagedPresentacionesProducto p=new ManagedPresentacionesProducto();
        p.cambiarNombres();
    }
    public String savePresentacionesProducto(){
        try {
            generarCodigo();
            Iterator i=getListaComponentesSeleccionados().iterator();
            while (i.hasNext()){
                System.out.println("3:");
                ComponentesProd bean=(ComponentesProd)i.next();
                String sql="insert into componentes_presprod(cod_compprod,cod_presentacion,cant_compprod)values(";
                sql+=""+bean.getCodCompprod()+",";
                sql+=""+getPresentacionesProducto().getCodPresentacion()+","+
                        "'"+bean.getCantidadComponente()+"')";
                setCon(Util.openConnection(getCon()));
                System.out.println("sql:insert:"+sql);
                PreparedStatement st=getCon().prepareStatement(sql);
                int result=st.executeUpdate();
            }
            
          
            
            String sql="insert into presentaciones_producto(cod_presentacion,cod_prod,cod_envasesec,"+
                    " cod_lineamkt,cantidad_presentacion,"+
                    " cod_tipomercaderia,obs_presentacion,cod_anterior,cod_estado_registro,nombre_producto_presentacion," +
                    "cod_tipo_presentacion,cod_categoria)values(";
            sql+=""+getPresentacionesProducto().getCodPresentacion()+",";
            sql+=""+getPresentacionesProducto().getProducto().getCodProducto()+",";            
            sql+="'"+getPresentacionesProducto().getEnvasesSecundarios().getCodEnvaseSec()+"',";            
            sql+="'"+getPresentacionesProducto().getLineaMKT().getCodLineaMKT()+"',";
            sql+="'"+getPresentacionesProducto().getCantidadPresentacion()+"',";
            sql+="'"+getPresentacionesProducto().getTiposMercaderia().getCodTipoMercaderia()+"',";            
            sql+="'"+getPresentacionesProducto().getObsPresentacion()+"'," +
                    "'"+getPresentacionesProducto().getCodAnterior()+"',1,"+
                    "'"+getPresentacionesProducto().getNombreProductoPresentacion()+"'," +
                    "'"+getPresentacionesProducto().getTiposPresentacion().getCodTipoPresentacion()+"'," +
                    "'"+getPresentacionesProducto().getCategoriasProducto().getCodCategoria()+"')";
            System.out.println("sql:insert:"+sql);
            setCon(Util.openConnection(getCon()));
            PreparedStatement st=getCon().prepareStatement(sql);
            int result=st.executeUpdate();
            st.close();
            clear();
            if(result>0){
                cargarPresentacionesProducto();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "navegadorpresentacionesproducto";
    }
    public void clear(){
        PresentacionesProducto tm=new PresentacionesProducto();
        setPresentacionesProducto(tm);
    }
    
    
    /*procedimiento que me permite dar de baja a marcar la casilla checkbox*/
    public String actionDarDeBaja(){
        try{ 
        String sql = ""; 
        int rsa = 0;
        setCon(Util.openConnection(getCon()));
        Iterator i=getPresentacionesProductoList().iterator();
        while (i.hasNext()){
            PresentacionesProducto objBean=(PresentacionesProducto)i.next();
            //Actualizo el estado de la tabla presentaciones_producto a estado inactivo
            if(objBean.getChecked().booleanValue()){//verifico si la casilla esta selecionada
                sql  = " update presentaciones_producto set ";
                sql += " cod_estado_registro= '2' ";
                sql += " where cod_presentacion = " + objBean.getCodPresentacion();
                System.out.println("sql<<<<<--------------->>>>>>>>>:" + sql);   
                System.out.println("88888888888888888>>>>>" +  objBean.getCodPresentacion());
                Statement stmt = getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                rsa = stmt.executeUpdate(sql);
          }
        }//end de While
        if(rsa > 0){
           cargarPresentacionesProducto();
       }   
       } catch (Exception e) {
            e.printStackTrace();
       }
         return "navegadorpresentacionesproductoEstado";  
       }
    
    /*procedimiento que me permite dar de Alta a marcar la casilla checkbox*/
     public String actionDarDeAlta(){
        try{
        String sql = "";    
        int rsa = 0;  
        setCon(Util.openConnection(getCon()));
        Iterator i=getPresentacionesProductoList().iterator();
        while (i.hasNext()){
            PresentacionesProducto objBean=(PresentacionesProducto)i.next();
            //Actualizo el estado de la tabla presentaciones_producto a estado activo
            if(objBean.getChecked().booleanValue()){  //verifico si la casilla esta selecionada
                sql  = " update presentaciones_producto set ";
                sql += " cod_estado_registro= '1' ";
                sql += " where cod_presentacion=" + objBean.getCodPresentacion();
                System.out.println("sqlXXXXXX----------->>>>>>>:" + sql);
                System.out.println("Codigo de campo--------->"  + objBean.getCodPresentacion());
                Statement stmt = getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                rsa = stmt.executeUpdate(sql);
          }            
        }//end de while 
        if(rsa > 0){
           cargarPresentacionesProducto();
        } 
       } catch (Exception e) {
            e.printStackTrace();
       }
         return "navegadorpresentacionesproductoEstado";  
       }
    
    ////////////////////////////////////////////////////
    public String actionEditPresentacionesProducto(){

        clear();
        cargarProducto("",null);
        cargarTipoMercaderia("",null);
        cargarEnvaseSecundario("",null);
        cargarEnvaseTerciario("",null);
        cargarCartonCorrugado("",null);
        cargarEstadoRegistro("",null);
        cargarLineaMKT("",null);
        
        Iterator i=getPresentacionesProductoList().iterator();
        while (i.hasNext()){
            PresentacionesProducto bean=(PresentacionesProducto)i.next();
            if(bean.getChecked().booleanValue()){
                System.out.println("bean:"+bean.getCantidadPresentacion());                
                setPresentacionesProducto(bean);
                break;
            }
        }
        
      //*carga los componenetes
        try {
            String sql="select cod_compprod,nombre_prod_semiterminado from componentes_prod" ;
            sql+="  where  cod_prod="+getPresentacionesProducto().getProducto().getCodProducto();

            sql=" select cp.cod_compprod, cp.nombre_prod_semiterminado from componentes_prod cp " +
            " where cp.COD_COMPPROD in ( SELECT CPPR.COD_COMPPROD FROM COMPONENTES_PRESPROD  CPPR WHERE CPPR.COD_PRESENTACION = '"+getPresentacionesProducto().getCodPresentacion()+"' )";

            sql=" SELECT CP.COD_COMPPROD,CP.nombre_prod_semiterminado,CPPR.CANT_COMPPROD FROM COMPONENTES_PROD CP " +
                " INNER JOIN  COMPONENTES_PRESPROD CPPR ON CP.COD_COMPPROD=CPPR.COD_COMPPROD "+
                " WHERE CPPR.COD_PRESENTACION='"+getPresentacionesProducto().getCodPresentacion()+"' ";
            
            listaComponentesSeleccionados.clear();
            System.out.println("sql detalle de componentes:"+sql);
            System.out.println("CODIGO PRESENTACION: "+getPresentacionesProducto().getCodPresentacion());
            con=Util.openConnection(con);
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs=st.executeQuery(sql);
            System.out.println("sql:"+sql);
            while (rs.next()){
                componentesProd=new ComponentesProd();
                String cod="";
                componentesProd.setCodCompprod(rs.getString(1));
                componentesProd.setNombreProdSemiterminado(rs.getString(2));
                componentesProd.setCantidadCompprod(rs.getString("CANT_COMPPROD")+"");
                getListaComponentesSeleccionados().add(componentesProd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "actionEdit";
    }
  ////////////////////////////////////////////////////  
    
    public String editPresentacionesProducto(){
        try {
            //elimina las anteriores componentes
            String sql="delete from componentes_presprod " +
                    "where cod_presentacion="+getPresentacionesProducto().getCodPresentacion();
            System.out.println("DELETE COMPONENTES PRES PROD:sql:"+sql);
            setCon(Util.openConnection(getCon()));
            Statement st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            //st.executeUpdate(sql);
            //inserta los nuevos componentes
            Iterator i=getListaComponentesSeleccionados().iterator();
            while (i.hasNext()){
                System.out.println("3:");
                ComponentesProd bean=(ComponentesProd)i.next();
                sql="insert into componentes_presprod(cod_compprod,cod_presentacion,cant_compprod) values(";
                sql+=""+bean.getCodCompprod()+",";
                sql+=""+getPresentacionesProducto().getCodPresentacion()+"," +
                     "'"+bean.getCantidadCompprod()+"')";
                setCon(Util.openConnection(getCon()));
                System.out.println("sql INSERT COMPONENTES PRES PROD:"+sql);
                PreparedStatement st1=getCon().prepareStatement(sql);
                //int result=st1.executeUpdate();
            }
            sql="update presentaciones_producto set ";
            sql+="cod_prod="+getPresentacionesProducto().getProducto().getCodProducto()+",";           
            sql+="cod_envasesec="+getPresentacionesProducto().getEnvasesSecundarios().getCodEnvaseSec()+",";           
            sql+="cod_lineamkt="+getPresentacionesProducto().getLineaMKT().getCodLineaMKT()+",";
            sql+="cantidad_presentacion="+getPresentacionesProducto().getCantidadPresentacion()+",";
            sql+="cod_tipomercaderia="+getPresentacionesProducto().getTiposMercaderia().getCodTipoMercaderia()+",";           
            sql+="obs_presentacion='"+getPresentacionesProducto().getObsPresentacion()+"',";
            sql+="cod_estado_registro="+getPresentacionesProducto().getEstadoReferencial().getCodEstadoRegistro()+",";
            sql+="cod_anterior='"+getPresentacionesProducto().getCodAnterior()+"',";
            sql+="nombre_producto_presentacion='"+getPresentacionesProducto().getNombreProductoPresentacion()+"',";
            sql+="cod_tipo_presentacion='"+getPresentacionesProducto().getTiposPresentacion().getCodTipoPresentacion()+"',";
            sql+="cod_categoria='"+getPresentacionesProducto().getCategoriasProducto().getCodCategoria()+"'";
            sql+=" where cod_presentacion="+getPresentacionesProducto().getCodPresentacion();
            System.out.println("sql:Update:"+sql);            
            setCon(Util.openConnection(getCon()));
            PreparedStatement st1=getCon().prepareStatement(sql);
            //int result=st1.executeUpdate();
            st.close();
            clear();
//            if(result>0){
//                cargarPresentacionesProducto();
//            }
            this.redireccionar("navegadorpresentacionesproducto.jsf");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    

    ////////////////////////////////////////////////////
    public String actionDeletePresentacionesProducto(){
        getPresentacionesProductoEli().clear();
        getPresentacionesProductoEli2().clear();
        setSwEliminaSi(false);
        setSwEliminaNo(false);
        int bandera=0;
        Iterator i=getPresentacionesProductoList().iterator();
        while (i.hasNext()){
            PresentacionesProducto bean=(PresentacionesProducto)i.next();
            if(bean.getChecked().booleanValue()){
                try {
                    String sql=" select cod_presentacion from presentaciones_producto_datoscomerciales" +
                               " where cod_presentacion="+bean.getCodPresentacion();
                    System.out.println("presentaciones_producto_datoscomerciales:"+sql);
                    setCon(Util.openConnection(getCon()));
                    Statement st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                    ResultSet rs=st.executeQuery(sql);
                    rs.last();                    
                    if(rs.getRow()==0){
                        bandera=1;
                    }
                    if(bandera==1){
                        sql="select i.COD_PRESENTACION from  ingresos_detalleventas i where i.COD_PRESENTACION="+bean.getCodPresentacion();
                        System.out.println("ingresos_detalleventas:"+sql);
                        st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                        rs=st.executeQuery(sql);
                        rs.last();
                        if(rs.getRow()==0){
                            bandera=1;
                        }else{
                            bandera=0;
                        }
                    }
                    if(bandera==1){
                        sql="select i.COD_PRESENTACION from  SALIDAS_DETALLEVENTAS i where i.COD_PRESENTACION="+bean.getCodPresentacion();
                        System.out.println("SALIDAS_DETALLEVENTAS:"+sql);
                        st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                        rs=st.executeQuery(sql);
                        rs.last();
                        if(rs.getRow()==0){
                            bandera=1;
                        }else{
                            bandera=0;
                        }
                    }
                    if (bandera==1){
                        getPresentacionesProductoEli().add(bean);
                        setSwEliminaSi(true);
                        System.out.println("ENTRO ELIMINAR");
                    }else{
                        getPresentacionesProductoEli2().add(bean);
                        setSwEliminaNo(true);
                        System.out.println("ENTRO NO ELIMINAR");
                    }
                    if(rs!=null){
                        rs.close();st.close();
                        rs=null;st=null;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return "actionDelete";
    }
    
    ////////////////////////////////////////////////////
    public String deletePresentacionesProducto(){
        try {
            Iterator i=getPresentacionesProductoEli().iterator();
            int result=0;
            while (i.hasNext()){
                PresentacionesProducto bean=(PresentacionesProducto)i.next();
                //elimina las anteriores componentes
                String sql="delete from componentes_presprod " +
                        "where cod_presentacion="+bean.getCodPresentacion();
                System.out.println("DELETE COMPONENTES PRESPROD:sql:"+sql);
                setCon(Util.openConnection(getCon()));
                Statement st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                st.executeUpdate(sql);
                sql="delete from PRESENTACIONES_PRODUCTO_DATOSCOMERCIALES where cod_presentacion="+bean.getCodPresentacion();
                st.executeUpdate(sql);    
                sql="delete from presentaciones_producto " +
                        "where cod_presentacion="+bean.getCodPresentacion();
                System.out.println("DELETE PRESENTACIONES:sql: "+sql);
                setCon(Util.openConnection(getCon()));
                st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                result=result+st.executeUpdate(sql);
            }
                
            getPresentacionesProductoEli().clear();
            if(result>0){
                cargarPresentacionesProducto();
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "navegadorpresentacionesproducto";
    }
    public String changeEventProducto(ActionEvent event){
        try {
        System.out.println("limpia");
        
        ValueHolder input = (ValueHolder)event.getComponent().getParent();
        String valor = input.getValue().toString();
        System.out.println("entro a limpiar producto" + presentacionesProducto.getProducto().getCodProducto() +"="+ valor);        
        getListaComponentesSeleccionados().clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    public String changeEventTipoPresentacion(ActionEvent event){
        try {
        ValueHolder input = (ValueHolder)event.getComponent().getParent();
        String valor = input.getValue().toString();
        System.out.println("entro a limpiar tipo presentacion "+ presentacionesProducto.getTiposPresentacion().getCodTipoPresentacion() +"="+ valor);
        getListaComponentesSeleccionados().clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    
    public String cargarComponentes(){
        System.out.println("1:");
      //getListaComponentesSeleccionados().clear();
        System.out.println("2:");
        Iterator i=getListaComponentesBuscar().iterator();
        
        while (i.hasNext()){
            //System.out.println("3:");
            ComponentesProd bean=(ComponentesProd)i.next();
            if(bean.getChecked().booleanValue()){
                bean.setChecked(false);                
                System.out.println("entramos a modificar el estadoo del registro " + bean.getChecked());
                getListaComponentesSeleccionados().add(bean);
            }
        }
        return "";
        
    }
    public String eliminaComponentes(){
        System.out.println("1:");
        System.out.println("el tamanio de la lista al momento de eliminar a<ddfgdfgdfgdfgdfgdfgdfgdfgdfgfasdf" + getListaComponentesSeleccionados().size());
        getEliminaComponentesSeleccionados().clear();
        System.out.println("2:");        
        Iterator i=getListaComponentesSeleccionados().iterator();
        while (i.hasNext()){
            System.out.println("3:");
            ComponentesProd bean=(ComponentesProd)i.next();
            if(bean.getChecked().booleanValue()){
            }else{
                getEliminaComponentesSeleccionados().add(bean);
            }
        }
        getListaComponentesSeleccionados().clear();
        i=getEliminaComponentesSeleccionados().iterator();
        while (i.hasNext()){
            System.out.println("3:");
            ComponentesProd bean=(ComponentesProd)i.next();
            if(bean.getChecked().booleanValue()){
            }else{
                getListaComponentesSeleccionados().add(bean);
            }
        }
        return "";
    }
    
    
    public String buscarComponentes(){
        listaComponentesBuscar.clear();
        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        
        System.out.println("presentacionesProducto.producto.codProducto:"+presentacionesProducto.getProducto().getCodProducto());
        System.out.println("presentacionesProducto.producto.codProducto:"+presentacionesProducto.getProducto().getNombreProducto());
        try {
            // se debe buscar dependiendo del tipo de presentacion seleccionado
            if(presentacionesProducto.getTiposPresentacion().getCodTipoPresentacion()==1){ //presentacion normal
                    this.cargaComponentesNormal();
            }else{
                this.cargaComponentesCombinado();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public void cargaComponentesNormal(){
        try {
            String sql = " select cod_compprod,nombre_prod_semiterminado from componentes_prod ";
                   sql+= " where  cod_prod = " + getPresentacionesProducto().getProducto().getCodProducto();
            Iterator i=getListaComponentesSeleccionados().iterator();
            while (i.hasNext()){
                ComponentesProd bean=(ComponentesProd)i.next();
                sql+=" and cod_compprod <> " + bean.getCodCompprod();
            }
            System.out.println("sql:"+sql);
            con=Util.openConnection(con);
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs=st.executeQuery(sql);
            System.out.println("sql:"+sql);

            while (rs.next()){
                componentesProd=new ComponentesProd();
                String cod="";
                componentesProd.setCodCompprod(rs.getString(1));
                componentesProd.setNombreProdSemiterminado(rs.getString(2));
                getListaComponentesBuscar().add(componentesProd);
                //getPresentacionesProductoList().add(bean1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cargaComponentesCombinado(){
        try {
            String sql = " select cod_compprod,nombre_prod_semiterminado from componentes_prod where 0=0 ";
            Iterator i=getListaComponentesSeleccionados().iterator();
            while (i.hasNext()){
                ComponentesProd bean=(ComponentesProd)i.next();
                sql+=" and cod_compprod <> " + bean.getCodCompprod();
            }
            sql+=" order by nombre_prod_semiterminado";
               
            System.out.println("sql:"+sql);
            con=Util.openConnection(con);
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs=st.executeQuery(sql);
            System.out.println("sql:"+sql);

            while (rs.next()){
                componentesProd=new ComponentesProd();
                String cod="";
                componentesProd.setCodCompprod(rs.getString(1));
                componentesProd.setNombreProdSemiterminado(rs.getString(2));
                getListaComponentesBuscar().add(componentesProd);
                //getPresentacionesProductoList().add(bean1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public String listaComponentes(String codigo,PresentacionesProducto bean1){
        try {
            String sql="select c.cod_compprod,c.cod_envaseprim,c.cod_colorpresprimaria,c.volumenpeso_envaseprim," +
                    " c.cantidad_compprod,c.cod_sabor from componentes_prod c,componentes_presprod cp";
            sql+="  where  c.cod_compprod=cp.cod_compprod and cp.cod_presentacion="+codigo;
            
            System.out.println("sql:"+sql);
            con=Util.openConnection(con);
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs=st.executeQuery(sql);
            System.out.println("sql:"+sql);
            
            
            while (rs.next()){
                componentesProd=new ComponentesProd();
                String cod="";
                componentesProd.setCodCompprod(rs.getString(1));
                cargarPrincipiosActivos(componentesProd.getCodCompprod());
                cod=rs.getString(2);
                System.out.println("sql:"+componentesProd.getCodCompprod());
                cod=(cod==null)?"":cod;
                cargarEnvasePrimario(cod);
                System.out.println("sql:"+componentesProd.getEnvasesPrimarios().getNombreEnvasePrim());
                cod=rs.getString(3);
                cod=(cod==null)?"":cod;
                System.out.println("st xxx:"+st);
                //cargarColor(cod);
                System.out.println("sql:"+componentesProd.getColoresPresentacion().getNombreColor());
                componentesProd.setVolumenPesoEnvasePrim(rs.getString(4));
                System.out.println("sql:"+componentesProd.getVolumenPesoEnvasePrim());
                componentesProd.setCantidadCompprod(rs.getString(5));
                System.out.println("sql:"+componentesProd.getCantidadCompprod());
                cod=rs.getString(6);
                cod=(cod==null)?"":cod;
                cargarSabor(cod);
                principioActivo=componentesProd.getSaboresProductos().getNombreSabor()+principioActivo;
                componentesProd.getSaboresProductos().setNombreSabor(principioActivo);
                System.out.println("sql:"+componentesProd.getSaboresProductos().getNombreSabor());
                System.out.println("principios:"+componentesProd.getSaboresProductos().getNombreSabor());
                bean1.getComponentesList().add(componentesProd);
            }
            if(rs!=null){
                rs.close();
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }
    
    public void cargarEnvasePrimario(String codigo){
        try {
            con=Util.openConnection(con);
            String sql="select nombre_envaseprim from envases_primarios where cod_envaseprim="+codigo;
            ResultSet rs=null;
            Statement st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            rs=st.executeQuery(sql);
            if(rs.next()){
                componentesProd.getEnvasesPrimarios().setNombreEnvasePrim(rs.getString(1));
            }
            if(rs!=null){
                rs.close();st.close();
                rs=null;st=null;
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void cargarPrincipiosActivos(String codigo){
        try {
            con=Util.openConnection(con);
            String sql="select pa.nombre_principio_activo,cd.concentracion from principios_activos pa,componentes_proddetalle cd where pa.cod_principio_activo=cd.cod_principio_activo and cd.cod_compprod="+codigo;
            System.out.println("cargar  princi"+sql);
            ResultSet rs=null;
            Statement st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            rs=st.executeQuery(sql);
            principioActivo="(";
            while(rs.next()){
                principioActivo+=rs.getString(1)+" "+rs.getString(2)+",";
            }
            principioActivo+=")";
            if(rs!=null){
                rs.close();st.close();
                rs=null;st=null;
            }
        
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void cargarColor(String codigo){
        try {
            setCon(Util.openConnection(getCon()));
            String sql="select nombre_colorpresprimaria from colores_presprimaria where cod_colorpresprimaria="+codigo;
            ResultSet rs=null;
            Statement st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            rs=st.executeQuery(sql);
            if(rs.next()){
                componentesProd.getColoresPresentacion().setNombreColor(rs.getString(1));
            }
            if(rs!=null){
                rs.close();st.close();
                rs=null;st=null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void cargarSabor(String codigo){
        try {
            con=Util.openConnection(con);
            String sql="select nombre_sabor from sabores_producto where cod_sabor="+codigo;
            ResultSet rs=null;
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            rs=st.executeQuery(sql);
            if(rs.next()){
                componentesProd.getSaboresProductos().setNombreSabor(rs.getString(1));
            }
            if(rs!=null){
                rs.close();st.close();
                rs=null;st=null;
            }
            
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void cargarForma(String codigo){
        try {
            con=Util.openConnection(con);
            String sql="select nombre_forma from formas_farmaceuticas where cod_forma="+codigo;
            ResultSet rs=null;
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            rs=st.executeQuery(sql);
            if(rs.next()){
                componentesProd.getForma().setNombreForma(rs.getString("nombre_forma"));
            }
            if(rs!=null){
                rs.close();st.close();
                rs=null;st=null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public String getCloseConnection() throws SQLException{
        if(getCon()!=null){
            getCon().close();
        }
        return "";
    }
        
    

    public String generaNombrePresentacion(){
        try {
            con=Util.openConnection(con);
            String sql="select nombre_prod from productos where cod_prod="+getPresentacionesProducto().getProducto().getCodProducto();
            System.out.println("sqlGenerarNombre:"+sql);
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs=st.executeQuery(sql);
            String nombrePresentacion="";
            if(rs.next()){
                nombrePresentacion=rs.getString(1);
            }
            nombrePresentacion=nombrePresentacion+" "+getPresentacionesProducto().getCantidadPresentacion();
            /******************ENVASE SECUNDARIO**************/
            String sql1="select nombre_envasesec from envases_secundarios where cod_envasesec="+getPresentacionesProducto().getEnvasesSecundarios().getCodEnvaseSec();
            System.out.println("sq11:"+sql1);
            Statement st1=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs1=st1.executeQuery(sql1);
            if(rs1.next()){
                nombrePresentacion=nombrePresentacion+" "+rs1.getString(1);
            }
            /********************ENVASE SECUNDARIO************/
            String sql2="select nombre_envaseterciario from envases_terciarios where cod_envaseterciario="+getPresentacionesProducto().getEnvasesSecundarios().getCodEnvaseSec();
            System.out.println("sq12:"+sql2);
            Statement st2=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs2=st2.executeQuery(sql2);
            if(rs2.next()){
                nombrePresentacion=nombrePresentacion+" "+rs2.getString(1);
            }
            nombrePresentacion=nombrePresentacion+" x "+getPresentacionesProducto().getCantEnvaseSecundario();
            getPresentacionesProducto().setNombreProductoPresentacion(nombrePresentacion);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }
    
    public String next(){
        super.next();
        return "";
    }
    
    public String back(){
        super.back();
        return "";
    }
    public String getCargarProductosPresentaciones(){
        cargarPresentacionesProducto();
        return "";
    }
    

    public void cargaComponentesEditar(){
        try {
            String sql = " select cod_compprod,nombre_prod_semiterminado from componentes_prod where 0=0 ";
            Iterator i=getListaComponentesSeleccionados().iterator();
            while (i.hasNext()){
                ComponentesProd bean=(ComponentesProd)i.next();                
                if(!bean.getCodCompprod().equals(editarComponente.getCodCompprod()))
                   sql+=" and cod_compprod <> " + bean.getCodCompprod();
            }
            sql+=" order by nombre_prod_semiterminado";

            System.out.println("sql:"+sql);
            con=Util.openConnection(con);
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs=st.executeQuery(sql);
            System.out.println("sql:"+sql);
            componenteEditarList.clear();
            while (rs.next()){                
                componenteEditarList.add(new SelectItem(rs.getString("cod_compprod"),rs.getString("nombre_prod_semiterminado")));                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
public String cancelarEdicion_action(){
    
    return null;
}


public String aceptarEdicionComponente_action(){
    try {


//        System.out.println("el valor cantidad del aceptar edicion" + valorCantidadComponente);

        Iterator i=listaComponentesSeleccionados.iterator();

        //verificar el componente seleccionado para edicion
        
        while (i.hasNext()){
            ComponentesProd bean=(ComponentesProd)i.next();        
            if(bean.getChecked().booleanValue()==true){
                bean.setCodCompprod(editarComponente.getCodCompprod());                
                bean.setNombreProdSemiterminado(this.recuperaNombreComponente(editarComponente.getCodCompprod()));
                bean.setCantidadCompprod(editarComponente.getCantidadCompprod());
                //editarComponente=bean;
                break;
            }
        }


//        ComponentesProd iteraComponentes = new ComponentesProd();
//        editarComponente=new ComponentesProd();
//
//            for (int i = 0; i < componentesSeleccionadosDataTable.getRowCount(); i++) {
//                // solo los materiales con el codComponente
//                componentesSeleccionadosDataTable.setRowIndex(i);
//
//                iteraComponentes = (ComponentesProd) componentesSeleccionadosDataTable.getRowData();
//
//                if(iteraComponentes.getCodCompprod().equals(codComponenteEditar)){
//                    iteraComponentes.setCodCompprod(valorCodCompProd);
//                    iteraComponentes.setNombreProdSemiterminado(valorNombreComponente);
//                    iteraComponentes.setCantidadCompprod(valorCantidadComponente);
//
//                    listaComponentesSeleccionados.set(i, iteraComponentes);
//                    break;
//                }
//                System.out.println("iterando los componentes" + iteraComponentes.getCodCompprod() + " " + iteraComponentes.getNombreProdSemiterminado() + " " + iteraComponentes.getCantidadCompprod());
//                }
//
//
        
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}


public String recuperaNombreComponente(String codCompProd){
    String nombreComponente ="";
        try {
            con=Util.openConnection(con);
            String sql="select cp.COD_COMPPROD,cp.nombre_prod_semiterminado from  componentes_prod cp where cp.COD_COMPPROD = " + codCompProd;
            ResultSet rs=null;
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            rs=st.executeQuery(sql);
            if(rs.next()){
            nombreComponente = rs.getString("nombre_prod_semiterminado");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
      return nombreComponente;
    }

public String editarPresentacionesProducto_action(){
        
        
        clear();
        cargarProducto("",null);
        cargarTipoMercaderia("",null);
        cargarEnvaseSecundario("",null);
        cargarEnvaseTerciario("",null);
        cargarCartonCorrugado("",null);
        cargarEstadoRegistro("",null);
        cargarLineaMKT("",null);
        cargarTiposPresentacion();
        this.cargarCategoria();

        Iterator i=getPresentacionesProductoList().iterator();
        while (i.hasNext()){
            PresentacionesProducto bean=(PresentacionesProducto)i.next();
            if(bean.getChecked().booleanValue()){
                System.out.println("bean:"+bean.getCantidadPresentacion());
                setPresentacionesProducto(bean);
                break;
            }
        }

      //*carga los componenetes
        try {
            String sql="select cod_compprod,nombre_prod_semiterminado from componentes_prod" ;
            sql+="  where  cod_prod="+getPresentacionesProducto().getProducto().getCodProducto();

            sql=" select cp.cod_compprod, cp.nombre_prod_semiterminado from componentes_prod cp " +
            " where cp.COD_COMPPROD in ( SELECT CPPR.COD_COMPPROD FROM COMPONENTES_PRESPROD  CPPR WHERE CPPR.COD_PRESENTACION = '"+getPresentacionesProducto().getCodPresentacion()+"' )";

            sql=" SELECT CP.COD_COMPPROD,CP.nombre_prod_semiterminado,CPPR.CANT_COMPPROD FROM COMPONENTES_PROD CP " +
                " INNER JOIN  COMPONENTES_PRESPROD CPPR ON CP.COD_COMPPROD=CPPR.COD_COMPPROD "+
                " WHERE CPPR.COD_PRESENTACION='"+getPresentacionesProducto().getCodPresentacion()+"' ";

            listaComponentesSeleccionados.clear();
            System.out.println("sql detalle de componentes:"+sql);
            System.out.println("CODIGO PRESENTACION: "+getPresentacionesProducto().getCodPresentacion());
            con=Util.openConnection(con);
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs=st.executeQuery(sql);
            System.out.println("sql:"+sql);
            while (rs.next()){
                componentesProd=new ComponentesProd();
                String cod="";
                componentesProd.setCodCompprod(rs.getString(1));
                componentesProd.setNombreProdSemiterminado(rs.getString(2));
                componentesProd.setCantidadCompprod(rs.getString("CANT_COMPPROD") ==null?"0": rs.getString("CANT_COMPPROD") );
                listaComponentesSeleccionados.add(componentesProd);
            }
            System.out.println("antes de que se resete" + componentesSeleccionadosDataTable.getRowCount() + " " + listaComponentesSeleccionados.size());

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        this.redireccionar("modificarpresentacionesproductos.jsf");
        return null;
    }
    public String redireccionar(String direccion) {
        try {

            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext ext = facesContext.getExternalContext();
            ext.redirect(direccion);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
     public String componente_action(ActionEvent evt) {
        System.out.println("el valor   ......" + evt.getComponent().getParent());
        ValueHolder input = (ValueHolder)evt.getComponent().getParent();
        System.out.println("el valor   ......" + input.getValue());
        //editarCodCompprod = input.getValue().toString();
                
        return null;
    }

     public String editarComponente_action(){

        Iterator i=listaComponentesSeleccionados.iterator();

        while (i.hasNext()){

            ComponentesProd bean=(ComponentesProd)i.next();
            System.out.println("datos para ser comprobados " +  bean.getCodCompprod() + " " + bean.getNombreProdSemiterminado() + " " +bean.getChecked() );

            if(bean.getChecked().booleanValue()){
                editarComponente.setCodCompprod(bean.getCodCompprod());
                System.out.println("al principio para ser comproadoasdfasdf"+editarComponente.getCodCompprod());
                editarCodCompprod=bean.getCodCompprod();
                editarComponente.setNombreProdSemiterminado(bean.getNombreProdSemiterminado());
                editarNombreProdSemiterminado=bean.getNombreProdSemiterminado();
                editarCantidadCompprod=bean.getCantidadCompprod();
                editarComponente.setCantidadCompprod(bean.getCantidadCompprod());
                codComponenteEditar=bean.getCodCompprod();
                break;
                //editarComponente=bean;
            }
        }
//
//try {
//        System.out.println("entro a editar el componente" + componentesSeleccionadosDataTable.getRowCount() + " " + listaComponentesSeleccionados.size());
//
//        System.out.println("entro a editar el componente" + getComponentesSeleccionadosDataTable().getRowCount() + " " + getListaComponentesSeleccionados().size());
//
//        ComponentesProd iteraComponentes = new ComponentesProd();
//        editarComponente=new ComponentesProd();
//
//
//            for (int i = 0; i < componentesSeleccionadosDataTable.getRowCount(); i++) {
//                // solo los materiales con el codComponente
//                componentesSeleccionadosDataTable.setRowIndex(i);
//                iteraComponentes = (ComponentesProd) componentesSeleccionadosDataTable.getRowData();
//                if(iteraComponentes.getChecked().booleanValue()==true){
//
//                editarComponente.setCodCompprod(iteraComponentes.getCodCompprod());
//                editarCodCompprod=iteraComponentes.getCodCompprod();
//                editarComponente.setNombreProdSemiterminado(iteraComponentes.getNombreProdSemiterminado());
//                editarNombreProdSemiterminado=iteraComponentes.getNombreProdSemiterminado();
//                editarCantidadCompprod=iteraComponentes.getCantidadCompprod();
//                editarComponente.setCantidadCompprod(iteraComponentes.getCantidadCompprod());
//                codComponenteEditar=iteraComponentes.getCodCompprod();
//
//                    System.out.println("editarComponente" + editarComponente +"editarComponente.getCodCompprod() : " + editarComponente.getCodCompprod());
//                    break;
//                }
//                System.out.println("datos iterados!!!!!!" + iteraComponentes.getCodCompprod());
//                }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        this.cargaComponentesEditar();

        return null;
    }
     public String clickCheckComponenteSeleccionado_action(ActionEvent event){
         try {
                ValueHolder input = (ValueHolder)event.getComponent().getParent();
                String valor = input.getValue().toString();                
                ComponentesProd componente = (ComponentesProd) componentesSeleccionadosDataTable.getRowData();
                System.out.println("se esta realizando el chaeck + "+componente.getChecked());                


         } catch (Exception e) {
             e.printStackTrace();
         }
         return null;
     }
     
     public String editarComponentes_action(){
         try {
                
                Iterator i=getListaComponentesSeleccionados().iterator();
                while (i.hasNext()){
                    ComponentesProd bean=(ComponentesProd)i.next();
                    if(bean.getChecked().booleanValue()){                    
                        editarComponente=bean;
                    }
                }
                this.cargaComponentesEditar();

         } catch (Exception e) {
             e.printStackTrace();
         }
         return null;
     }

}
