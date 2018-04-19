/*
 * ManagedTipoCliente.java
 * Created on 19 de febrero de 2008, 16:50
 */

package com.cofar.web;

import com.cofar.bean.FormulaMaestraDetalleEP;
import com.cofar.bean.Materiales;
import com.cofar.bean.PartesMaquinaria;
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
 *  @author Wilmer Manzaneda Chavez
 *  @company COFAR
 */
public class ManagedPartesMaquinaria {
    
    /** Creates a new instance of ManagedTipoCliente */
    private PartesMaquinaria partesMaquinariabean=new PartesMaquinaria();
    private List partesMaquinariaList=new ArrayList();
    private List tiposEquiposList=new ArrayList();
    private Connection con;
    private String codMaquinaria="";
    private String codigo="";
    private boolean swSi=false;
    private boolean swNo=false;
    private String nombreMaquinaria="";
    
    
    public ManagedPartesMaquinaria() {
        
    }
    public String getObtenerCodigo(){
        
        //String cod=Util.getParameter("codigo");
        String cod=Util.getParameter("cod_maquina");
        String cod1=Util.getParameter("codigo");
        System.out.println("cxxxxxxxxxxxxxxxxxxxxxxxod:"+cod);
        if(cod!=null){
            setCodMaquinaria(cod);
        }
        if(cod1!=null){
            setCodigo(cod1);
        }
        partesMaquinariaList.clear();
        cargarPartesMaquinaria();
        cargarNombreMaquinaria();
        return "";
        
    }
    public String cargarNombreMaquinaria(){
        try {
            setCon(Util.openConnection(getCon()));
            String sql=" select nombre_maquina";
            sql+=" from maquinarias";
            sql+=" where cod_maquina='"+getCodMaquinaria()+"'";
            System.out.println("sql:-----------:"+sql);
            PreparedStatement st=getCon().prepareStatement(sql);
            ResultSet rs=st.executeQuery();
            while (rs.next()){
                setNombreMaquinaria(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("nombreMaquinari:"+nombreMaquinaria);
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
    
    public void cargarPartesMaquinaria(){
        
        try {
            
            System.out.println("codigo:"+getCodigo());
            String sql="select pm.cod_parte_maquina,pm.cod_maquina,pm.codigo,pm.cod_tipo_equipo,pm.nombre_parte_maquina,pm.obs_parte_maquina,te.NOMBRE_TIPO_EQUIPO";
            sql+=" from partes_maquinaria pm,TIPOS_EQUIPOS_MAQUINARIA te";
            sql+=" where cod_maquina="+codMaquinaria+" and te.COD_TIPO_EQUIPO=pm.cod_tipo_equipo";
            sql+=" order by pm.nombre_parte_maquina";
            System.out.println("sql_navegador"+sql);
            setCon(Util.openConnection(getCon()));
            Statement st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs=st.executeQuery(sql);
            rs.last();
            int rows=rs.getRow();
            partesMaquinariaList.clear();
            rs.first();
            for(int i=0;i<rows;i++){
                PartesMaquinaria bean=new PartesMaquinaria();
                bean.setCodParteMaquina(rs.getString(1));
                bean.getMaquinaria().setCodMaquina(rs.getString(2));
                bean.setCodigo(rs.getString(3));
                bean.getTiposEquiposMaquinaria().setCodTipoEquipo(rs.getString(4));
                bean.setNombreParteMaquina(rs.getString(5));
                bean.setObsParteMaquina(rs.getString(6));
                bean.getTiposEquiposMaquinaria().setNombreTipoEquipo(rs.getString(7));
                partesMaquinariaList.add(bean);
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
        cargarTiposEquipos("",null);
        //cargarUnidadesMedida("",null);
        return "actionAgregarPartesMaquina";
    }
    public void cargarTiposEquipos(String codigo,PartesMaquinaria bean){
        try {
            setCon(Util.openConnection(getCon()));
            String sql="select COD_TIPO_EQUIPO,NOMBRE_TIPO_EQUIPO from TIPOS_EQUIPOS_MAQUINARIA where cod_estado_registro=1";
            ResultSet rs=null;
            
            Statement st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            if(!codigo.equals("")){
            } else{
                tiposEquiposList.clear();
                rs=st.executeQuery(sql);
                while (rs.next())
                    tiposEquiposList.add(new SelectItem(rs.getString(1),rs.getString(2)));
            }
            if(rs!=null){
                rs.close();st.close();
                rs=null;st=null;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public String getCodigoMaquinaria(){
        String codigo="1";
        try {
            setCon(Util.openConnection(getCon()));
            String sql="select max(COD_PARTE_MAQUINA)+1 from partes_maquinaria";
            PreparedStatement st=getCon().prepareStatement(sql);
            ResultSet rs=st.executeQuery();
            while (rs.next())
                codigo=rs.getString(1);
            if(codigo==null)
                codigo="1";
            
            partesMaquinariabean.setCodParteMaquina(codigo);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  "";
    }
    ////////////// Guardar ////////////////////////
    public String guardarPartesMaquina(){
        
        System.out.println("xxxxxxxxxxxxxxxx:"+getCodigo());
        String sql="";
        int result=0;
        try {
            getCodigoMaquinaria();
            String codigoParteMaquina=codigo+"-"+partesMaquinariabean.getCodigo();
            setCon(Util.openConnection(getCon()));
            sql="insert into partes_maquinaria(cod_parte_maquina,cod_maquina,codigo,cod_tipo_equipo,nombre_parte_maquina,obs_parte_maquina)values(" ;
            sql+="'"+partesMaquinariabean.getCodParteMaquina()+"','"+codMaquinaria+"','"+codigoParteMaquina+"','"+partesMaquinariabean.getTiposEquiposMaquinaria().getCodTipoEquipo()+"'," ;
            sql+="'"+partesMaquinariabean.getNombreParteMaquina()+"','"+partesMaquinariabean.getObsParteMaquina()+"')";
            System.out.println("sql:"+sql);
            PreparedStatement st1=getCon().prepareStatement(sql);
            result=st1.executeUpdate();
            System.out.println("result:"+result);
            
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        cargarPartesMaquinaria();
        return "navegadorPartesMaquina";
    }
    public String actionEditar(){
        cargarTiposEquipos("",null);
        
        Iterator index=partesMaquinariaList.iterator();
        String sql="";
        int result=0;
        
        while (index.hasNext()){
            PartesMaquinaria bean=(PartesMaquinaria)index.next();
            if(bean.getChecked().booleanValue()){
                System.out.println("qwiuweiruweiopru");
                partesMaquinariabean=bean;
                String codigo[]=partesMaquinariabean.getCodigo().split("-");
                partesMaquinariabean.setCodigo(codigo[2]);
            }
        }
        
        return "actionEditarPartesMaquina";
    }


    

    public String guardarModificarPartesMaquina(){
        
        System.out.println("xxxxxxxxxxxxxxxx:"+getCodigo());
        String sql="";
        int result=0;
        try {
            
            String codigoParteMaquina=codigo+"-"+partesMaquinariabean.getCodigo();
            setCon(Util.openConnection(getCon()));
            sql="update partes_maquinaria set " ;
            sql+=" codigo='"+codigoParteMaquina+"',";
            sql+=" cod_tipo_equipo='"+partesMaquinariabean.getTiposEquiposMaquinaria().getCodTipoEquipo()+"',";
            sql+=" nombre_parte_maquina='"+partesMaquinariabean.getNombreParteMaquina()+"',";
            sql+=" obs_parte_maquina='"+partesMaquinariabean.getObsParteMaquina()+"'";
            sql+=" where cod_parte_maquina='"+partesMaquinariabean.getCodParteMaquina()+"'";
            System.out.println("sql:"+sql);
            PreparedStatement st1=getCon().prepareStatement(sql);
            result=st1.executeUpdate();
            System.out.println("result:"+result);
            
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        cargarPartesMaquinaria();
        return "navegadorPartesMaquina";
    }
    
    public String cancelar(){
        cargarPartesMaquinaria();
        return "navegadorPartesMaquina";
    }
    public String cancelar1(){
        
        return "navegadorMaquina";
    }
    public String guardarEliminarPartesMaquina(){
        
        System.out.println("xxxxxxxxxxxxxxxx:"+getCodigo());
        //System.out.println("area_inferior:"+codigoAreaInferior);
        Iterator index=partesMaquinariaList.iterator();
        String sql="";
        int result=0;
        while (index.hasNext()){
            
            try {
                PartesMaquinaria bean=(PartesMaquinaria)index.next();
                if(bean.getChecked().booleanValue()){
                    setCon(Util.openConnection(getCon()));
                    sql="delete from partes_maquinaria" +
                            " where cod_parte_maquina='"+bean.getCodParteMaquina()+"' " ;
                    System.out.println("sql Eliminar:"+sql);
                    PreparedStatement st1=getCon().prepareStatement(sql);
                    result=st1.executeUpdate();
                    System.out.println("result:"+result);
                }
                
            } catch (SQLException e) {
                e.printStackTrace();
            }
            
            
            
        }
        cargarPartesMaquinaria();
        return "navegadorPartesMaquina";
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
        return "navegadorPartesMaquina";
    }
    
    public String cancelar(){
        cargarPartesMaquinaria();
        return "navegadorPartesMaquina";
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
        return "navegadorPartesMaquina";
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
    
    public PartesMaquinaria getPartesMaquinariabean() {
        return partesMaquinariabean;
    }
    
    public void setPartesMaquinariabean(PartesMaquinaria partesMaquinariabean) {
        this.partesMaquinariabean = partesMaquinariabean;
    }
    
    public List getPartesMaquinariaList() {
        return partesMaquinariaList;
    }
    
    public void setPartesMaquinariaList(List partesMaquinariaList) {
        this.partesMaquinariaList = partesMaquinariaList;
    }
    
    public List getTiposEquiposList() {
        return tiposEquiposList;
    }
    
    public void setTiposEquiposList(List tiposEquiposList) {
        this.tiposEquiposList = tiposEquiposList;
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
    
    public String getNombreMaquinaria() {
        return nombreMaquinaria;
    }
    
    public void setNombreMaquinaria(String nombreMaquinaria) {
        this.nombreMaquinaria = nombreMaquinaria;
    }
    
    public String getCodMaquinaria() {
        return codMaquinaria;
    }
    
    public void setCodMaquinaria(String codMaquinaria) {
        this.codMaquinaria = codMaquinaria;
    }
    
    
    
}
