/*
 * ManagedTipoCliente.java
 * Created on 19 de febrero de 2008, 16:50
 */

package com.cofar.web;

import com.cofar.bean.AreasInstalacionesModulos;
import com.cofar.bean.FormulaMaestraDetalleEP;
import com.cofar.bean.Materiales;
import com.cofar.bean.AreasInstalacionesModulos;
import com.cofar.util.Util;
import java.beans.Beans;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.model.SelectItem;

/**
 *
 *  @author Wilson Choquehuanca
 *  @company COFAR
 */
public class ManagedAreasInstalacionesModulos {
    
    /** Creates a new instance of ManagedTipoCliente */
    private AreasInstalacionesModulos areasInstalacionesModulosbean=new AreasInstalacionesModulos();
    private List areasInstalacionesModulosList=new ArrayList();
    private List areasInstalacionesModulosAdicionarList=new ArrayList();
    private Connection con;
    private String codigo="";
    private String codigoAreaEmpresa="";
    private boolean swSi=false;
    private boolean swNo=false;
    private String nombreInstalacion="";
    private String nombreAreasEmpresa="";
    private String codigoAreaInstalacion="";
    
    
    public ManagedAreasInstalacionesModulos() {
        
    }
    public String getObtenerCodigo(){
        
        //String cod=Util.getParameter("codigo");
        String cod1=Util.getParameter("codigo");
        System.out.println("cxxxxxxxxxxxxxxxxxxxxxxxod:"+cod1);

        if(cod1!=null){
            setCodigo(cod1);
        }
        getAreasInstalacionesModulosList().clear();
        cargarAreasInstalacionesModulos();
        cargarNombreAreaInstalacion();
        return "";
        
    }
    public String cargarNombreAreaInstalacion(){
        try {
            setCon(Util.openConnection(getCon()));
            
            String sql=" select A.NOMBRE_AREA_INSTALACION,A.CODIGO,AE.NOMBRE_AREA_EMPRESA";
            sql+=" from AREAS_INSTALACIONES A,AREAS_EMPRESA AE";
            sql+=" WHERE A.COD_AREA_EMPRESA=AE.COD_AREA_EMPRESA AND A.COD_AREA_INSTALACION='"+getCodigo()+"'";
            System.out.println("sql:-----------:"+sql);
            PreparedStatement st=getCon().prepareStatement(sql);
            ResultSet rs=st.executeQuery();
            while (rs.next()){
                nombreInstalacion=rs.getString(1);
                codigoAreaInstalacion=rs.getString(2);
                nombreAreasEmpresa=rs.getString(3);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("nombreMaquinari:"+nombreInstalacion+codigoAreaInstalacion+nombreAreasEmpresa);
        return  "";
    }
    
    /**
     * metodo que genera los codigos
     * correlativamente
     */
    
    /**
     * Metodo para cargar los datos en
     * el datatable
     */
    
    public void cargarAreasInstalacionesModulos(){
        
        try {
            
            System.out.println("codigo:"+getCodigo());
            String sql="SELECT AI.COD_AREA_INSTALACION,AI.COD_MODULO_INSTALACION,AI.CODIGO,M.NOMBRE_MODULO_INSTALACION";
            sql+=" FROM AREAS_INSTALACIONES_MODULOS AI,AREAS_INSTALACIONES A,MODULOS_INSTALACIONES M";
            sql+=" WHERE AI.COD_AREA_INSTALACION=A.COD_AREA_INSTALACION AND M.COD_MODULO_INSTALACION=AI.COD_MODULO_INSTALACION";
            sql+=" AND AI.COD_AREA_INSTALACION='"+codigo+"'";
            sql+=" order by M.NOMBRE_MODULO_INSTALACION";
            System.out.println("sql_navegador"+sql);
            setCon(Util.openConnection(getCon()));
            Statement st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs=st.executeQuery(sql);
            rs.last();
            int rows=rs.getRow();
            areasInstalacionesModulosList.clear();
            rs.first();
            for(int i=0;i<rows;i++){
                AreasInstalacionesModulos bean=new AreasInstalacionesModulos();
                bean.getAreasInstalaciones().setCodAreaInstalacion(rs.getString(1));
                //bean.getModulosInstalaciones().setCodModuloInstalacion(rs.getString(2));
                bean.setCodigo(rs.getString(3));
                //bean.getModulosInstalaciones().setNombreModuloInstalacion(rs.getString(4));
                areasInstalacionesModulosList.add(bean);
                rs.next();
            }
            
            if(rs!=null){
                rs.close();
                st.close();
            }
            
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public String actionAgregar(){
        System.out.println("entro4656532");
        try {
            setCon(Util.openConnection(getCon()));
            areasInstalacionesModulosAdicionarList.clear();
            String sql="SELECT M.COD_MODULO_INSTALACION,M.NOMBRE_MODULO_INSTALACION";
            sql+=" FROM MODULOS_INSTALACIONES M";
            sql+=" WHERE M.COD_MODULO_INSTALACION <> ALL ";
            sql+=" (SELECT A.COD_MODULO_INSTALACION FROM AREAS_INSTALACIONES_MODULOS A WHERE A.COD_AREA_INSTALACION='"+codigo+"')";
            ResultSet rs=null;
            Statement st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            rs=st.executeQuery(sql);
            while (rs.next()){
                AreasInstalacionesModulos bean=new AreasInstalacionesModulos();
                //bean.getModulosInstalaciones().setCodModuloInstalacion(rs.getString(1));
                //bean.getModulosInstalaciones().setNombreModuloInstalacion(rs.getString(2));
                areasInstalacionesModulosAdicionarList.add(bean);
            }
            if(rs!=null){
                rs.close();st.close();
                rs=null;st=null;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "actionAgregarAreasInstalacionesModulos";
    }
    
    
    ////////////// Guardar ////////////////////////
    public String guardarAreasInstalacionesModulos(){
        
        System.out.println("xxxxxxxxxxxxxxxx:"+getCodigo());
        try {
            Iterator index=areasInstalacionesModulosAdicionarList.iterator();
            String sql="";
            int result=0;
            
            while (index.hasNext()){
                AreasInstalacionesModulos bean=(AreasInstalacionesModulos)index.next();
                if(bean.getChecked().booleanValue()){
                    String codInstalacionModulo=codigoAreaInstalacion+"-"+bean.getCodigo();
                    setCon(Util.openConnection(getCon()));
//                    sql="insert into AREAS_INSTALACIONES_MODULOS(COD_AREA_INSTALACION,COD_MODULO_INSTALACION,CODIGO)values(" ;
//                    sql+="'"+codigo+"','"+bean.getModulosInstalaciones().getCodModuloInstalacion()+"','"+codInstalacionModulo+"')";
//                    System.out.println("sql:"+sql);
                    PreparedStatement st1=getCon().prepareStatement(sql);
                    result=st1.executeUpdate();
                    System.out.println("result:"+result);
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        cargarAreasInstalacionesModulos();
        return "navegadorAreasInstalacionesModulos";
    }
   /* public String actionEditar(){
        cargarTiposEquipos("",null);
        Iterator index=AreasInstalacionesModulosList.iterator();
        String sql="";
        int result=0;
        
        while (index.hasNext()){
            AreasInstalacionesModulos bean=(AreasInstalacionesModulos)index.next();
            if(bean.getChecked().booleanValue()){
                System.out.println("qwiuweiruweiopru");
                AreasInstalacionesModulosbean=bean;
                String codigo[]=AreasInstalacionesModulosbean.getCodigo().split("-");
                AreasInstalacionesModulosbean.setCodigo(codigo[2]);
            }
        }
        
        return "actionEditarAreasInstalacionesModulos";
    }
    
    public String guardarModificarAreasInstalacionesModulos(){
        
        System.out.println("xxxxxxxxxxxxxxxx:"+getCodigo());
        String sql="";
        int result=0;
        try {
            
            String codigoParteMaquina=getCodigo()+"-"+AreasInstalacionesModulosbean.getCodigo();
            setCon(Util.openConnection(getCon()));
            sql="update partes_maquinaria set " ;
            sql+=" codigo='"+codigoParteMaquina+"',";
            sql+=" cod_tipo_equipo='"+AreasInstalacionesModulosbean.getTiposEquiposMaquinaria().getCodTipoEquipo()+"',";
            sql+=" nombre_parte_maquina='"+AreasInstalacionesModulosbean.getNombreParteMaquina()+"',";
            sql+=" obs_parte_maquina='"+AreasInstalacionesModulosbean.getObsParteMaquina()+"'";
            sql+=" where cod_parte_maquina='"+AreasInstalacionesModulosbean.getCodParteMaquina()+"'";
            System.out.println("sql:"+sql);
            PreparedStatement st1=getCon().prepareStatement(sql);
            result=st1.executeUpdate();
            System.out.println("result:"+result);
            
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        cargarAreasInstalacionesModulos();
        return "navegadorAreasInstalacionesModulos";
    }*/
    
    public String cancelar(){
        cargarAreasInstalacionesModulos();
        return "navegadorAreasInstalacionesModulos";
    }

    public String guardarEliminarAreasInstalacionesModulos(){
        
        System.out.println("xxxxxxxxxxxxxxxx:"+getCodigo());
        //System.out.println("area_inferior:"+codigoAreaInferior);
        Iterator index=areasInstalacionesModulosList.iterator();
        String sql="";
        int result=0;
        while (index.hasNext()){
            
            try {
                AreasInstalacionesModulos bean=(AreasInstalacionesModulos)index.next();
                if(bean.getChecked().booleanValue()){
                    setCon(Util.openConnection(getCon()));
//                    sql="delete from AREAS_INSTALACIONES_MODULOS" +
//                            " where COD_AREA_INSTALACION='"+codigo+"' and COD_MODULO_INSTALACION='"+bean.getModulosInstalaciones().getCodModuloInstalacion()+"'" ;
//                    System.out.println("sql Eliminar:"+sql);
                    PreparedStatement st1=getCon().prepareStatement(sql);
                    result=st1.executeUpdate();
                    System.out.println("result:"+result);
                }
                
            } catch (SQLException e) {
                e.printStackTrace();
            }
            
            
            
        }
        cargarAreasInstalacionesModulos();
        return "navegadorAreasInstalacionesModulos";
    }
    
    
   /* public String guardarEditarFormulaMaestraDetalleEP(){
    
        System.out.println("xxxxxxxxxxxxxxxx:"+getCodigo());
        //System.out.println("area_inferior:"+codigoAreaInferior);
        Iterator index=formulaMaestraDetalleEPEditarList.iterator();
        String sql="";
        int result=0;
        while (index.hasNext()){
    
            try {
                FormulaMaestraDetalleEP bean=(FormulaMaestraDetalleEP)index.next();
    
                setCon(Util.openConnection(getCon()));
                sql="update FORMULA_MAESTRA_DETALLE_EP set" +
                        " CANTIDAD="+bean.getCantidad()+"" +
                        " where COD_FORMULA_MAESTRA='"+getCodigo()+"' " +
                        " and COD_MATERIAL='"+bean.getMateriales().getCodMaterial()+"'" +
                        " and cod_presentacion_primaria='"+getCodigoPresPrim()+"'" ;
    
                System.out.println("sql:"+sql);
                PreparedStatement st1=getCon().prepareStatement(sql);
                result=st1.executeUpdate();
                System.out.println("result:"+result);
    
    
            } catch (SQLException e) {
                e.printStackTrace();
            }
    
    
    
        }
        cargarFormulaMaestraDetalleEP();
        return "navegadorAreasInstalacionesModulos";
    }
    
    public String cancelar(){
        cargarAreasInstalacionesModulos();
        return "navegadorAreasInstalacionesModulos";
    }
    public String cancelar1(){
    
        return "cancelarEP";
    }
    public String actionEliminar(){
        Iterator index=formulaMaestraDetalleEPList.iterator();
        String sql="";
        int result=0;
        formulaMaestraDetalleEPEliminarList.clear();
        while (index.hasNext()){
            FormulaMaestraDetalleEP bean=(FormulaMaestraDetalleEP)index.next();
            if(bean.getChecked().booleanValue()){
                System.out.println("qwiuweiruweiopru"+bean.getUnidadesMedida().getCodUnidadMedida());
                formulaMaestraDetalleEPEliminarList.add(bean);
            }
        }
    
        return "actionEliminarFormulaMaestraDetalleEP";
    }
    
    public String guardarEliminarFormulaMaestraDetalleEP(){
    
        System.out.println("xxxxxxxxxxxxxxxx:"+getCodigo());
        //System.out.println("area_inferior:"+codigoAreaInferior);
        Iterator index=formulaMaestraDetalleEPEliminarList.iterator();
        String sql="";
        int result=0;
        while (index.hasNext()){
    
            try {
                FormulaMaestraDetalleEP bean=(FormulaMaestraDetalleEP)index.next();
                if(bean.getChecked().booleanValue()){
                    setCon(Util.openConnection(getCon()));
                    sql="delete from FORMULA_MAESTRA_DETALLE_EP" +
                            " where COD_FORMULA_MAESTRA='"+getCodigo()+"' " +
                            " and COD_MATERIAL='"+bean.getMateriales().getCodMaterial()+"'" +
                            " and cod_presentacion_primaria='"+getCodigoPresPrim()+"'" ;
                    System.out.println("sql Eliminar:"+sql);
                    PreparedStatement st1=getCon().prepareStatement(sql);
                    result=st1.executeUpdate();
                    System.out.println("result:"+result);
                }
    
            } catch (SQLException e) {
                e.printStackTrace();
            }
    
    
    
        }
        cargarFormulaMaestraDetalleEP();
        return "navegadorAreasInstalacionesModulos";
    }*/
    
/////////////////////////////////////////////////////////////
    
    /**
     * Metodo que cierra la conexion
     */
    public String getCloseConnection() throws SQLException{
        if(getCon()!=null){
            getCon().close();
        }
        return "";
    }
    /**
     * Métodos de la Clase
     */
    
    public AreasInstalacionesModulos getAreasInstalacionesModulosbean() {
        return areasInstalacionesModulosbean;
    }
    
    public void setAreasInstalacionesModulosbean(AreasInstalacionesModulos areasInstalacionesModulosbean) {
        this.areasInstalacionesModulosbean = areasInstalacionesModulosbean;
    }
    
    public List getAreasInstalacionesModulosList() {
        return areasInstalacionesModulosList;
    }
    
    public void setAreasInstalacionesModulosList(List areasInstalacionesModulosList) {
        this.areasInstalacionesModulosList = areasInstalacionesModulosList;
    }
    
    public List getAreasInstalacionesModulosAdicionarList() {
        return areasInstalacionesModulosAdicionarList;
    }
    
    public void setAreasInstalacionesModulosAdicionarList(List areasInstalacionesModulosAdicionarList) {
        this.areasInstalacionesModulosAdicionarList = areasInstalacionesModulosAdicionarList;
    }
    
    public Connection getCon() {
        return con;
    }
    
    public void setCon(Connection con) {
        this.con = con;
    }
    
    public String getCodigo() {
        return codigo;
    }
    
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    public String getCodigoAreaEmpresa() {
        return codigoAreaEmpresa;
    }
    
    public void setCodigoAreaEmpresa(String codigoAreaEmpresa) {
        this.codigoAreaEmpresa = codigoAreaEmpresa;
    }
    
    public boolean isSwSi() {
        return swSi;
    }
    
    public void setSwSi(boolean swSi) {
        this.swSi = swSi;
    }
    
    public boolean isSwNo() {
        return swNo;
    }
    
    public void setSwNo(boolean swNo) {
        this.swNo = swNo;
    }
    
    public String getNombreInstalacion() {
        return nombreInstalacion;
    }
    
    public void setNombreInstalacion(String nombreInstalacion) {
        this.nombreInstalacion = nombreInstalacion;
    }
    
    public String getNombreAreasEmpresa() {
        return nombreAreasEmpresa;
    }
    
    public void setNombreAreasEmpresa(String nombreAreasEmpresa) {
        this.nombreAreasEmpresa = nombreAreasEmpresa;
    }
    
    public String getCodigoAreaInstalacion() {
        return codigoAreaInstalacion;
    }
    
    public void setCodigoAreaInstalacion(String codigoAreaInstalacion) {
        this.codigoAreaInstalacion = codigoAreaInstalacion;
    }
    
    
    
}
