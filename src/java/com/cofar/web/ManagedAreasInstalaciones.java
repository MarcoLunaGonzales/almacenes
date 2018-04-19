/*
 * ManagedTiposMercaderia.java
 *
 * Created on 18 de marzo de 2008, 17:30
 */

package com.cofar.web;

import com.cofar.bean.ModulosInstalaciones;

import com.cofar.bean.AreasInstalaciones;
import com.cofar.util.Util;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import org.apache.taglibs.standard.lang.jstl.ModulusOperator;

/**
 *
 * @author Wilson Choquehuanca Gonzales
 * @company COFAR
 */
public class ManagedAreasInstalaciones extends ManagedBean{
    
    /** Creates a new instance of ManagedPersonal */
    private List areasInstalacionesList=new ArrayList();
    private List areasInstalacionesEliminarList=new ArrayList();
    private List areasInstalacionesNoEliminarList=new ArrayList();
    private AreasInstalaciones areasInstalacionesbean=new AreasInstalaciones();
    private Connection con;
    private List estadoRegistro=new  ArrayList();
    private List areasEmpresaList=new  ArrayList();
    private boolean swElimina1;
    private boolean swElimina2;
    public ManagedAreasInstalaciones() {
        cargarAreasInstalaciones();
    }
    
    public void cargarAreasEmpresa(String codigo, AreasInstalaciones bean) {
        try {
            setCon(Util.openConnection(getCon()));
            String sql="select cod_area_empresa,nombre_area_empresa from areas_empresa " +
                    " where cod_area_empresa <> all (select ai.COD_AREA_EMPRESA from AREAS_INSTALACIONES ai)" +
                    " order by nombre_area_empresa";
            ResultSet rs = null;
            
            Statement st = getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            if (!codigo.equals("")) {
                
            } else {
                areasEmpresaList.clear();
                rs = st.executeQuery(sql);
                while (rs.next()) {
                    areasEmpresaList.add(new SelectItem(rs.getString(1), rs.getString(2)));
                }
            }
            if (rs != null) {
                rs.close();
                st.close();
                rs = null;
                st = null;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
   /* public void cargarAreasMaquina(){
    
        try {
    
            String sql="select cod_area_empresa,nombre_area_empresa from areas_empresa order by nombre_area_empresa";
            System.out.println("sql:"+sql);
            setCon(Util.openConnection(getCon()));
            Statement st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs=st.executeQuery(sql);
            rs.last();
            int rows=rs.getRow();
            areasEmpresaList.clear();
            rs.first();
            for(int i=0;i<rows;i++){
                AreasInstalaciones bean=new AreasInstalaciones();
                bean.getMaquinaria().setCodMaquina(rs.getString(1));
                bean.getMaquinaria().setNombreMaquina(rs.getString(2));
                bean.getMaquinaria().setCodigo(rs.getString(3));
                areasMaquinaList.add(bean);
                rs.next();
            }
    
            if(rs!=null){
                rs.close();
                st.close();
            }
    
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/
    public void cargarAreasInstalaciones(){
        try {
            String sql=" select ai.COD_AREA_INSTALACION,ai.COD_AREA_EMPRESA,ai.NOMBRE_AREA_INSTALACION,ai.CODIGO,ae.NOMBRE_AREA_EMPRESA";
            sql+=" from AREAS_EMPRESA ae,AREAS_INSTALACIONES ai where ae.COD_AREA_EMPRESA=ai.COD_AREA_EMPRESA";
            sql+=" order by ae.NOMBRE_AREA_EMPRESA";
            System.out.println("AREAS_INSTALACIONES:"+sql);
            setCon(Util.openConnection(getCon()));
            Statement st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs=st.executeQuery(sql);
            String cod="";
            areasInstalacionesList.clear();
            while (rs.next()){
                AreasInstalaciones bean=new AreasInstalaciones();
                bean.setCodAreaInstalacion(rs.getString(1));
                bean.getAreasEmpresa().setCodAreaEmpresa(rs.getString(2));
                bean.setNombreAreaInstalacion(rs.getString(3));
                bean.setCodigo(rs.getString(4));
                bean.getAreasEmpresa().setNombreAreaEmpresa(rs.getString(5));
                areasInstalacionesList.add(bean);
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
    
    public void cargarEstadoRegistro(String codigo,ModulosInstalaciones bean){
        try {
            setCon(Util.openConnection(getCon()));
            String sql="select cod_estado_registro,nombre_estado_registro from estados_referenciales where cod_estado_registro<>3";
            ResultSet rs=null;
            
            Statement st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            if(!codigo.equals("")){
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
    public void generarCodigo(){
        try {
            String  sql="select max(COD_AREA_INSTALACION)+1 from AREAS_INSTALACIONES";
            setCon(Util.openConnection(getCon()));
            Statement st=getCon().createStatement();
            ResultSet rs=st.executeQuery(sql);
            if(rs.next()){
                String cod=rs.getString(1);
                if(cod==null)
                    getAreasInstalacionesbean().setCodAreaInstalacion("1");
                else
                    getAreasInstalacionesbean().setCodAreaInstalacion(cod);
            }
            if(rs!=null){
                rs.close();
                st.close();
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public String actionSaveAreasInstalaciones(){
        clear();
        cargarAreasEmpresa("",null);
        return "actionSaveAreasInstalaciones";
    }
    public String actionCancelar(){
        clear();
        cargarAreasInstalaciones();
        return "navegadorAreasInstalaciones";
    }
    public String saveAreasInstalaciones(){
        try {
            generarCodigo();
            
            String sql="insert into AREAS_INSTALACIONES(COD_AREA_INSTALACION,COD_AREA_EMPRESA,NOMBRE_AREA_INSTALACION,CODIGO)values(";
            sql+=""+getAreasInstalacionesbean().getCodAreaInstalacion()+",";
            sql+="'"+getAreasInstalacionesbean().getAreasEmpresa().getCodAreaEmpresa()+"',";
            sql+="'"+getAreasInstalacionesbean().getNombreAreaInstalacion()+"','"+getAreasInstalacionesbean().getCodigo()+"')";
            System.out.println("sql:insert:"+sql);
            setCon(Util.openConnection(getCon()));
            PreparedStatement st=getCon().prepareStatement(sql);
            int result=st.executeUpdate();
            st.close();
            clear();
            if(result>0){
                
                cargarAreasInstalaciones();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return "navegadorAreasInstalaciones";
    }
    public void clear(){
        AreasInstalaciones tm=new AreasInstalaciones();
        setAreasInstalacionesbean(tm);
    }
    
    public String actionEditAreasInstalaciones(){
        cargarAreasEmpresa("",null);
        Iterator i=areasInstalacionesList.iterator();
        while (i.hasNext()){
            AreasInstalaciones bean=(AreasInstalaciones)i.next();
            if(bean.getChecked().booleanValue()){
                setAreasInstalacionesbean(bean);
                break;
            }
            
        }
        return "actionEditAreasInstalaciones";
    }
    
    public String editModulosInstalaciones(){
        try {
            String sql="update AREAS_INSTALACIONES set ";
            sql+="NOMBRE_AREA_INSTALACION='"+getAreasInstalacionesbean().getNombreAreaInstalacion()+"',";
            sql+="CODIGO='"+getAreasInstalacionesbean().getCodigo()+"'";
            sql+="where COD_AREA_INSTALACION="+getAreasInstalacionesbean().getCodAreaInstalacion();
            System.out.println("sql:Update:"+sql);
            setCon(Util.openConnection(getCon()));
            PreparedStatement st=getCon().prepareStatement(sql);
            int result=st.executeUpdate();
            st.close();
            clear();
            if(result>0){
                cargarAreasInstalaciones();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "navegadorAreasInstalaciones";
    }
    
    public String actionDeleteAreasInstalaciones(){
        setSwElimina2(false);
        setSwElimina1(false);
        areasInstalacionesEliminarList.clear();
        areasInstalacionesNoEliminarList.clear();
        int bandera=0;
        Iterator i=areasInstalacionesList.iterator();
        while (i.hasNext()){
            AreasInstalaciones bean=(AreasInstalaciones)i.next();
            if(bean.getChecked().booleanValue()){
                
                areasInstalacionesEliminarList.add(bean);
                setSwElimina1(true);
                System.out.println("entro  eliminarrrrrrrrrrrr");
            }
        }
        return "actionDeleteAreasInstalaciones";
    }
    
    public String deleteAreasInstalaciones(){
        try {
            
            Iterator i=areasInstalacionesEliminarList.iterator();
            int result=0;
            while (i.hasNext()){
                AreasInstalaciones bean=(AreasInstalaciones)i.next();
                String sql="delete from AREAS_INSTALACIONES " +
                        "where COD_AREA_INSTALACION="+bean.getCodAreaInstalacion();
                System.out.println("deleteAREAS_INSTALACIONES:"+sql);
                setCon(Util.openConnection(getCon()));
                Statement st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                result=result+st.executeUpdate(sql);
                sql="delete from AREAS_INSTALACIONES_MODULOS " +
                        "where COD_AREA_INSTALACION="+bean.getCodAreaInstalacion();
                System.out.println("delete AREAS_INSTALACIONES_MODULOS:"+sql);
                setCon(Util.openConnection(getCon()));
                st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                result=result+st.executeUpdate(sql);
            }
            getAreasInstalacionesEliminarList().clear();
            getAreasInstalacionesNoEliminarList().clear();
            if(result>0){
                cargarAreasInstalaciones();
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "navegadorAreasInstalaciones";
    }
    
 /*
  
  
  
    /**************************************************************/
    
    public List getAreasInstalacionesList() {
        return areasInstalacionesList;
    }
    
    public void setAreasInstalacionesList(List areasInstalacionesList) {
        this.areasInstalacionesList = areasInstalacionesList;
    }
    
    public List getAreasInstalacionesEliminarList() {
        return areasInstalacionesEliminarList;
    }
    
    public void setAreasInstalacionesEliminarList(List areasInstalacionesEliminarList) {
        this.areasInstalacionesEliminarList = areasInstalacionesEliminarList;
    }
    
    public List getAreasInstalacionesNoEliminarList() {
        return areasInstalacionesNoEliminarList;
    }
    
    public void setAreasInstalacionesNoEliminarList(List areasInstalacionesNoEliminarList) {
        this.areasInstalacionesNoEliminarList = areasInstalacionesNoEliminarList;
    }
    
    public AreasInstalaciones getAreasInstalacionesbean() {
        return areasInstalacionesbean;
    }
    
    public void setAreasInstalacionesbean(AreasInstalaciones areasInstalacionesbean) {
        this.areasInstalacionesbean = areasInstalacionesbean;
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
    
    public List getAreasEmpresaList() {
        return areasEmpresaList;
    }
    
    public void setAreasEmpresaList(List areasEmpresaList) {
        this.areasEmpresaList = areasEmpresaList;
    }
    
    public boolean isSwElimina1() {
        return swElimina1;
    }
    
    public void setSwElimina1(boolean swElimina1) {
        this.swElimina1 = swElimina1;
    }
    
    public boolean isSwElimina2() {
        return swElimina2;
    }
    
    public void setSwElimina2(boolean swElimina2) {
        this.swElimina2 = swElimina2;
    }
    
    
    
}
