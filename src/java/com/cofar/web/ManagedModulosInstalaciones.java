/*
 * ManagedTiposMercaderia.java
 *
 * Created on 18 de marzo de 2008, 17:30
 */

package com.cofar.web;

import com.cofar.bean.ModulosInstalaciones;

import com.cofar.bean.TiposMercaderia;
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
public class ManagedModulosInstalaciones extends ManagedBean{
    
    /** Creates a new instance of ManagedPersonal */
    private List modulosInstalacionesList=new ArrayList();
    private List modulosInstalacionesEliminarList=new ArrayList();
    private List modulosInstalacionesNoEliminarList=new ArrayList();
    private ModulosInstalaciones modulosInstalacionesbean=new ModulosInstalaciones();
    private Connection con;
    private List estadoRegistro=new  ArrayList();
    private boolean swElimina1;
    private boolean swElimina2;
    public ManagedModulosInstalaciones() {
        cargarModulosInstalaciones();
    }
    public void cargarModulosInstalaciones(){
        try {
            String sql="SELECT  M.COD_MODULO_INSTALACION,M.NOMBRE_MODULO_INSTALACION,M.OBS_MODULO_INSTALACION,";
            sql+=" M.COD_ESTADO_REGISTRO,ER.NOMBRE_ESTADO_REGISTRO";
            sql+=" FROM MODULOS_INSTALACIONES M,ESTADOS_REFERENCIALES ER";
            sql+=" WHERE M.COD_ESTADO_REGISTRO=ER.COD_ESTADO_REGISTRO";
            if(!getModulosInstalacionesbean().getEstadoReferencial().getCodEstadoRegistro().equals("") && !getModulosInstalacionesbean().getEstadoReferencial().getCodEstadoRegistro().equals("3")){
                sql+=" and  er.cod_estado_registro="+getModulosInstalacionesbean().getEstadoReferencial().getCodEstadoRegistro();
            }
            sql+=" ORDER BY M.NOMBRE_MODULO_INSTALACION";
            System.out.println("cargarMODULOS_INSTALACIONES:"+sql);
            setCon(Util.openConnection(getCon()));
            Statement st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs=st.executeQuery(sql);
            String cod="";
            modulosInstalacionesList.clear();
            while (rs.next()){
                ModulosInstalaciones bean=new ModulosInstalaciones();
                bean.setCodModuloInstalacion(rs.getString(1));
                bean.setNombreModuloInstalacion(rs.getString(2));
                bean.setObsModuloInstalacion(rs.getString(3));
                bean.getEstadoReferencial().setCodEstadoRegistro(rs.getString(4));
                bean.getEstadoReferencial().setNombreEstadoRegistro(rs.getString(5));
                modulosInstalacionesList.add(bean);
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
        getModulosInstalacionesbean().getEstadoReferencial().setCodEstadoRegistro(event.getNewValue().toString());
        cargarModulosInstalaciones();
    }
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
            String  sql="select max(COD_MODULO_INSTALACION)+1 from MODULOS_INSTALACIONES";
            setCon(Util.openConnection(getCon()));
            Statement st=getCon().createStatement();
            ResultSet rs=st.executeQuery(sql);
            if(rs.next()){
                String cod=rs.getString(1);
                if(cod==null)
                    getModulosInstalacionesbean().setCodModuloInstalacion("1");
                else
                    getModulosInstalacionesbean().setCodModuloInstalacion(cod);
            }
            if(rs!=null){
                rs.close();
                st.close();
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public String actionSaveModulosInstalaciones(){
        clear();
        cargarEstadoRegistro("",null);
        return "actionSaveModulosInstalaciones";
    }
    public String actionCancelar(){
        clear();
        cargarModulosInstalaciones();
        return "navegadorModulosInstalaciones";
    }
    public String saveModulosInstalaciones(){
        try {
            generarCodigo();
            
            String sql="insert into MODULOS_INSTALACIONES(COD_MODULO_INSTALACION,NOMBRE_MODULO_INSTALACION,OBS_MODULO_INSTALACION,COD_ESTADO_REGISTRO)values(";
            sql+=""+getModulosInstalacionesbean().getCodModuloInstalacion()+",";
            sql+="'"+getModulosInstalacionesbean().getNombreModuloInstalacion()+"',";
            sql+="'"+getModulosInstalacionesbean().getObsModuloInstalacion()+"',1)";
            System.out.println("sql:insert:"+sql);
            setCon(Util.openConnection(getCon()));
            PreparedStatement st=getCon().prepareStatement(sql);
            int result=st.executeUpdate();
            st.close();
            clear();
            if(result>0){
                
                cargarModulosInstalaciones();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return "navegadorModulosInstalaciones";
    }
    public void clear(){
        ModulosInstalaciones tm=new ModulosInstalaciones();
        setModulosInstalacionesbean(tm);
    }
    
    public String actionEditModulosInstalaciones(){
        cargarEstadoRegistro("",null);
        Iterator i=getModulosInstalacionesList().iterator();
        while (i.hasNext()){
            ModulosInstalaciones bean=(ModulosInstalaciones)i.next();
            if(bean.getChecked().booleanValue()){
                setModulosInstalacionesbean(bean);
                break;
            }
            
        }
        return "actionEditModulosInstalaciones";
    }
    public String editModulosInstalaciones(){
        try {
            String sql="update MODULOS_INSTALACIONES set ";
            sql+="NOMBRE_MODULO_INSTALACION='"+getModulosInstalacionesbean().getNombreModuloInstalacion()+"',";
            sql+="OBS_MODULO_INSTALACION='"+getModulosInstalacionesbean().getObsModuloInstalacion()+"',";
            sql+="COD_ESTADO_REGISTRO="+getModulosInstalacionesbean().getEstadoReferencial().getCodEstadoRegistro();
            sql+="where COD_MODULO_INSTALACION="+getModulosInstalacionesbean().getCodModuloInstalacion();
            System.out.println("sql:Update:"+sql);
            setCon(Util.openConnection(getCon()));
            PreparedStatement st=getCon().prepareStatement(sql);
            int result=st.executeUpdate();
            st.close();
            clear();
            if(result>0){
                cargarModulosInstalaciones();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "navegadorModulosInstalaciones";
    }
    public String actionDeleteModulosInstalaciones(){
        setSwElimina2(false);
        setSwElimina1(false);
        getModulosInstalacionesEliminarList().clear();
        getModulosInstalacionesNoEliminarList().clear();
        int bandera=0;
        Iterator i=getModulosInstalacionesList().iterator();
        while (i.hasNext()){
            ModulosInstalaciones bean=(ModulosInstalaciones)i.next();
            if(bean.getChecked().booleanValue()){
                
                modulosInstalacionesEliminarList.add(bean);
                setSwElimina1(true);
                System.out.println("entro  eliminarrrrrrrrrrrr");
            }
        }
        return "actionDeleteModulosInstalaciones";
    }
    public String deleteModulosInstalaciones(){
        try {
            
            Iterator i=modulosInstalacionesEliminarList.iterator();
            int result=0;
            while (i.hasNext()){
                ModulosInstalaciones bean=(ModulosInstalaciones)i.next();
                String sql="delete from MODULOS_INSTALACIONES " +
                        "where COD_MODULO_INSTALACION="+bean.getCodModuloInstalacion();
                System.out.println("deleteMODULOS_INSTALACIONES:sql:"+sql);
                setCon(Util.openConnection(getCon()));
                Statement st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                result=result+st.executeUpdate(sql);
            }
            getModulosInstalacionesEliminarList().clear();
            getModulosInstalacionesNoEliminarList().clear();
            if(result>0){
                cargarModulosInstalaciones();
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "navegadorModulosInstalaciones";
    }
    /**************************************************************/
    
    public List getModulosInstalacionesList() {
        return modulosInstalacionesList;
    }
    
    public void setModulosInstalacionesList(List modulosInstalacionesList) {
        this.modulosInstalacionesList = modulosInstalacionesList;
    }
    
    public List getModulosInstalacionesEliminarList() {
        return modulosInstalacionesEliminarList;
    }
    
    public void setModulosInstalacionesEliminarList(List modulosInstalacionesEliminarList) {
        this.modulosInstalacionesEliminarList = modulosInstalacionesEliminarList;
    }
    
    public List getModulosInstalacionesNoEliminarList() {
        return modulosInstalacionesNoEliminarList;
    }
    
    public void setModulosInstalacionesNoEliminarList(List modulosInstalacionesNoEliminarList) {
        this.modulosInstalacionesNoEliminarList = modulosInstalacionesNoEliminarList;
    }
    
    public ModulosInstalaciones getModulosInstalacionesbean() {
        return modulosInstalacionesbean;
    }
    
    public void setModulosInstalacionesbean(ModulosInstalaciones modulosInstalacionesbean) {
        this.modulosInstalacionesbean = modulosInstalacionesbean;
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
