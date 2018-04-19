

package com.cofar.web;


import com.cofar.bean.ActividadesProduccion;
import com.cofar.bean.CartonesCorrugados;
import com.cofar.util.Util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

/**
 *
 *  @author Guery Garcia Jaldin
 *  @company COFAR
 */
public class ManagedActividadesProduccion{
    
    private ActividadesProduccion actividadesProduccionbean=new ActividadesProduccion();
    private List actividadesProduccionList=new ArrayList();
    private List estadoRegistro=new ArrayList();
    private List actividadesProduccionEliminar=new ArrayList();
    private List actividadesProduccionNoEliminar=new ArrayList();
    private Connection con;
    private boolean swEliminaSi;
    private boolean swEliminaNo;
    private List tiposActividadProduccionList = new ArrayList();
    
    
    public ManagedActividadesProduccion() {
        //cargarActividadesProduccion();
    }
    /**
     * metodo que genera los codigos
     * correlativamente
     */
    public String getCodigoActividadesProduccion(){
        String codigo="1";
        try {
            setCon(Util.openConnection(getCon()));
            String sql="select max(cod_actividad)+1 from actividades_produccion";
            PreparedStatement st=getCon().prepareStatement(sql);
            System.out.println("sql:MAX:"+sql);
            ResultSet rs=st.executeQuery();
            while (rs.next())
                codigo=rs.getString(1);
            if(codigo==null)
                codigo="1";
            
            actividadesProduccionbean.setCodActividad(codigo);
            System.out.println("coiogogo:"+codigo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  "";
    }
    
    public void cargarEstadoRegistro(String codigo,ActividadesProduccion bean){
        try {
            setCon(Util.openConnection(getCon()));
            String sql="select cod_estado_registro,nombre_estado_registro from estados_referenciales where cod_estado_registro<>3";
            ResultSet rs=null;
            Statement st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            if(!codigo.equals("")){
                sql+=" and cod_estado_registro="+codigo;
                System.out.println("update:"+sql);
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
    /**
     * Metodo para cargar los datos en
     * el datatable
     */

      public void cargarActividadesProduccion(){
        try {
            String sql="select cod_actividad,nombre_actividad,obs_actividad,cod_estado_registro" +
                    " from actividades_produccion where 0=0";
            if(!actividadesProduccionbean.getEstadoReferencial().getCodEstadoRegistro().equals("") && !actividadesProduccionbean.getEstadoReferencial().getCodEstadoRegistro().equals("3")){
                sql+=" and cod_estado_registro="+actividadesProduccionbean.getEstadoReferencial().getCodEstadoRegistro();
            }
            if(!actividadesProduccionbean.getTiposActividadProduccion().getCodTipoActividadProduccion().equals("")){
                sql+=" and cod_tipo_actividad_produccion="+actividadesProduccionbean.getTiposActividadProduccion().getCodTipoActividadProduccion();
            }
            sql+=" order by nombre_actividad asc";
            System.out.println("cargar:"+sql);
            setCon(Util.openConnection(getCon()));
            Statement st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs=st.executeQuery(sql);
            rs.last();
            int rows=rs.getRow();
            actividadesProduccionList.clear();
            rs.first();
            String cod="";
            for(int i=0;i<rows;i++){
                ActividadesProduccion bean=new ActividadesProduccion();
                bean.setCodActividad(rs.getString(1));
                bean.setNombreActividad(rs.getString(2));
                bean.setObsActividad(rs.getString(3));
                cod=rs.getString(4);
                cod=(cod==null)?"":cod;
                System.out.println("st xxx:"+cod);
                cargarEstadoRegistro(cod,bean);
                actividadesProduccionList.add(bean);
                rs.next();
            }
            if(rs!=null){
                rs.close();
                st.close();
            }
            this.cargarTiposActividadProduccion();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

     public String getIniciarCargado(){
        try {
            actividadesProduccionbean.getEstadoReferencial().setCodEstadoRegistro("1");
            actividadesProduccionbean.getTiposActividadProduccion().setCodTipoActividadProduccion("1");
            this.cargarActividadesProduccion();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public void cargarTiposActividadProduccion(){
    try {
            con=Util.openConnection(con);
            String sql="select tappr.COD_TIPO_ACTIVIDAD_PRODUCCION,tappr.NOMBRE_TIPO_ACTIVIDAD_PRODUCCION from TIPOS_ACTIVIDAD_PRODUCCION tappr  ";
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs=st.executeQuery(sql);
            tiposActividadProduccionList.clear();
            while (rs.next()){
                String codigo=rs.getString("COD_TIPO_ACTIVIDAD_PRODUCCION");
                String nombre=rs.getString("NOMBRE_TIPO_ACTIVIDAD_PRODUCCION");
                tiposActividadProduccionList.add(new SelectItem(codigo,nombre));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String Guardar(){
        clearActividadesProduccion();
        return"actionAgregarActividadesProduccion";
    }
    
    
    public String actionEditar(){
        cargarEstadoRegistro("",null);
        Iterator i=actividadesProduccionList.iterator();
        while (i.hasNext()){
            ActividadesProduccion bean=(ActividadesProduccion)i.next();
            if(bean.getChecked().booleanValue()){
                actividadesProduccionbean=bean;
                break;
            }
        }
        return "actionEditarActividadesProduccion";
    }
    
    
    public String actionEliminar(){
        setSwEliminaSi(false);
        setSwEliminaNo(false);
        actividadesProduccionEliminar.clear();
        actividadesProduccionNoEliminar.clear();
        int bandera=0;
        Iterator i=actividadesProduccionList.iterator();
        while (i.hasNext()){
            ActividadesProduccion bean=(ActividadesProduccion)i.next();
            if(bean.getChecked().booleanValue()){
                actividadesProduccionEliminar.add(bean);
                setSwEliminaSi(true);
            }
        }
        return "actionEliminarActividadesProduccion";
    }
    
    public void clearActividadesProduccion(){
        actividadesProduccionbean.setCodActividad("");
        actividadesProduccionbean.setNombreActividad("");
        actividadesProduccionbean.setObsActividad("");        
    }
    
    public String guardarActividadesProduccion(){
        try {
            String sql="insert into actividades_produccion(cod_actividad,nombre_actividad,obs_actividad,cod_estado_registro,cod_tipo_actividad_produccion)values(";
            sql+="'"+actividadesProduccionbean.getCodActividad()+"',";
            sql+="'"+actividadesProduccionbean.getNombreActividad().toUpperCase()+"',";
            sql+="'"+actividadesProduccionbean.getObsActividad()+"',1," +
                   " '"+actividadesProduccionbean.getTiposActividadProduccion().getCodTipoActividadProduccion()+"')";
            System.out.println("inset:"+sql);
            setCon(Util.openConnection(getCon()));
            PreparedStatement st=getCon().prepareStatement(sql);
            int result=st.executeUpdate();
            if(result>0){
                cargarActividadesProduccion();
                clearActividadesProduccion();
            }
            System.out.println("result:"+result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return "navegadorActividadesProduccion";
    }
    public String modificarActividadesProduccion(){
        try {
            setCon(Util.openConnection(getCon()));
            String  sql="update actividades_produccion set";
            sql+=" nombre_actividad='"+actividadesProduccionbean.getNombreActividad().toUpperCase()+"',";
            sql+=" obs_actividad='"+actividadesProduccionbean.getObsActividad()+"',";
            sql+=" cod_estado_registro='"+actividadesProduccionbean.getEstadoReferencial().getCodEstadoRegistro()+"'";
            sql+=" where cod_actividad="+actividadesProduccionbean.getCodActividad();
            System.out.println("modifi:"+sql);
            PreparedStatement st=getCon().prepareStatement(sql);
            int result=st.executeUpdate();
            if(result>0){
                cargarActividadesProduccion();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return "navegadorActividadesProduccion";
    }
    public String eliminarActividadesProduccion(){
        try {
            Iterator i=actividadesProduccionEliminar.iterator();
            int result=0;
            while (i.hasNext()){
                ActividadesProduccion bean=(ActividadesProduccion)i.next();
                String sql="delete from actividades_produccion  ";
                       sql+=" where cod_Actividad="+bean.getCodActividad();
                System.out.println("deleteActividades:sql:"+sql);
                setCon(Util.openConnection(getCon()));
                Statement st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                result=result+st.executeUpdate(sql);
            }
            actividadesProduccionEliminar.clear();
            actividadesProduccionNoEliminar.clear();
            if(result>0){
                cargarActividadesProduccion();
            }

            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "navegadorActividadesProduccion";
    }
    public String Cancelar(){
        actividadesProduccionList.clear();
        cargarActividadesProduccion();
        return "navegadorActividadesProduccion";
    }
    
    
    /**********ESTADO REGISTRO****************/
    public void changeEvent(ValueChangeEvent event){
        System.out.println("event:"+event.getNewValue());
        actividadesProduccionbean.getEstadoReferencial().setCodEstadoRegistro(event.getNewValue().toString());
        cargarActividadesProduccion();
    }
    public void tipoActividadProduccion_changeEvent(ValueChangeEvent event){
        System.out.println("event:"+event.getNewValue());
        actividadesProduccionbean.getTiposActividadProduccion().setCodTipoActividadProduccion(event.getNewValue().toString());
        cargarActividadesProduccion();
    }
//Metodo que cierra la conexion
    
    public String getCloseConnection() throws SQLException{
        if(getCon()!=null){
            getCon().close();
        }
        return "";
    }
    /**
     * Métodos de la Clase
     */
    
    public ActividadesProduccion getActividadesProduccionbean() {
        return actividadesProduccionbean;
    }
    
    public void setActividadesProduccionbean(ActividadesProduccion actividadesProduccionbean) {
        this.actividadesProduccionbean = actividadesProduccionbean;
    }
    
    public List getActividadesProduccionList() {
        return actividadesProduccionList;
    }
    
    public void setActividadesProduccionList(List actividadesProduccionList) {
        this.actividadesProduccionList = actividadesProduccionList;
    }
    
    public List getEstadoRegistro() {
        return estadoRegistro;
    }
    
    public void setEstadoRegistro(List estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }
    
    public List getActividadesProduccionEliminar() {
        return actividadesProduccionEliminar;
    }
    
    public void setActividadesProduccionEliminar(List actividadesProduccionEliminar) {
        this.actividadesProduccionEliminar = actividadesProduccionEliminar;
    }
    
    public List getActividadesProduccionNoEliminar() {
        return actividadesProduccionNoEliminar;
    }
    
    public void setActividadesProduccionNoEliminar(List actividadesProduccionNoEliminar) {
        this.actividadesProduccionNoEliminar = actividadesProduccionNoEliminar;
    }
    
    public Connection getCon() {
        return con;
    }
    
    public void setCon(Connection con) {
        this.con = con;
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

    public List getTiposActividadProduccionList() {
        return tiposActividadProduccionList;
    }

    public void setTiposActividadProduccionList(List tiposActividadProduccionList) {
        this.tiposActividadProduccionList = tiposActividadProduccionList;
    }

    
    
}
