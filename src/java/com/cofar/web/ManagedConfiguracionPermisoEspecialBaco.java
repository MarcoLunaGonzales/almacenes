/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofar.web;

import com.cofar.bean.Almacenes;
import com.cofar.bean.ConfiguracionPermisoEspecialBaco;
import com.cofar.bean.Personal;
import com.cofar.bean.TipoPermisoEspecialBaco;
import com.cofar.util.Util;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author rcondori
 */
public class ManagedConfiguracionPermisoEspecialBaco extends ManagedBean {
    /*Declaracion de variables*/
    private Logger LOGGER;
    private List<ConfiguracionPermisoEspecialBaco> configuracionesPermisoEspecialBacoList = new ArrayList<ConfiguracionPermisoEspecialBaco>();
    private List<ConfiguracionPermisoEspecialBaco> configuracionesPermisoEspecialBacoPersonalList = new ArrayList<ConfiguracionPermisoEspecialBaco>();
    private List tiposPermisoEspecialBacoList = new ArrayList();
    private List almacenesList = new ArrayList();
    private List personalList = new ArrayList();
    private ConfiguracionPermisoEspecialBaco configuracionPermisoEspecialBacoGestionar = new ConfiguracionPermisoEspecialBaco();
//    private ConfiguracionPermisoEspecialBaco configuracionPermisoEspecialBacoSeleccionado = new ConfiguracionPermisoEspecialBaco();
    private int indicePermisoEspecialBaco = 0;
    
    

    public ManagedConfiguracionPermisoEspecialBaco() {
        LOGGER = LogManager.getRootLogger();
        mensaje= "";
        registradoCorrectamente = false;
    }
    
    public String getCargarConfiguracionPermisoEspecialBaco() throws SQLException{
        configuracionesPermisoEspecialBacoList = listarConfiguracionPermisoEspecialBaco();
        return null;
    }
    
    
    public List<ConfiguracionPermisoEspecialBaco> listarConfiguracionPermisoEspecialBaco() throws SQLException{
        List<ConfiguracionPermisoEspecialBaco> listaConfiguraciones = new ArrayList<ConfiguracionPermisoEspecialBaco>();
        Connection con = null;
        try {
            con = Util.openConnection(con);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            StringBuilder consulta 
                    = new StringBuilder("SELECT DISTINCT p.COD_PERSONAL, p.NOMBRES_PERSONAL, p.nombre2_personal, p.AP_PATERNO_PERSONAL, p.AP_MATERNO_PERSONAL, a.ALMACENES_ASIGNADOS");
                    consulta.append(" from CONFIGURACION_PERMISO_ESPECIAL_BACO cpeb")
                            .append(" left outer join PERSONAL p on p.COD_PERSONAL = cpeb.COD_PERSONAL")
                            .append(" left outer join (")
                                .append(" Select distinct cpeb2.COD_PERSONAL, ")
                                    .append(" substring(")
                                    .append(" (   Select ', '+a1.NOMBRE_ALMACEN  AS [text()]")
                                        .append(" from (select DISTINCT x.COD_PERSONAL, x.COD_ALMACEN from CONFIGURACION_PERMISO_ESPECIAL_BACO x) cpeb1")
                                        .append(" left outer join ALMACENES a1 on a1.COD_ALMACEN = cpeb1.COD_ALMACEN")
                                        .append(" Where cpeb1.COD_PERSONAL = cpeb2.COD_PERSONAL")
                                        .append(" ORDER BY cpeb1.COD_PERSONAL")
                                        .append(" For XML PATH ('')")
                                    .append(" ), 2, 1000) as ALMACENES_ASIGNADOS")
                                .append(" from CONFIGURACION_PERMISO_ESPECIAL_BACO cpeb2")
                            .append(" ) a on a.COD_PERSONAL = cpeb.COD_PERSONAL")
                            .append(" order by p.AP_PATERNO_PERSONAL,p.AP_MATERNO_PERSONAL,NOMBRES_PERSONAL");
            LOGGER.info("consulta: "+consulta.toString());
            ResultSet res = st.executeQuery(consulta.toString());
            while (res.next()) {
                ConfiguracionPermisoEspecialBaco configuracionPermisoEspecialBaco = new ConfiguracionPermisoEspecialBaco();
                configuracionPermisoEspecialBaco.getPersonal().setCodPersonal(res.getString("COD_PERSONAL"));
                configuracionPermisoEspecialBaco.getPersonal().setNombrePersonal(res.getString("NOMBRES_PERSONAL"));
                configuracionPermisoEspecialBaco.getPersonal().setNombre2Personal(res.getString("nombre2_personal"));
                configuracionPermisoEspecialBaco.getPersonal().setApPaternoPersonal(res.getString("AP_PATERNO_PERSONAL"));
                configuracionPermisoEspecialBaco.getPersonal().setApMaternoPersonal(res.getString("AP_MATERNO_PERSONAL"));
                configuracionPermisoEspecialBaco.getAlmacenes().setNombreAlmacen(res.getString("ALMACENES_ASIGNADOS"));
                listaConfiguraciones.add(configuracionPermisoEspecialBaco);
            }
   
        } 
        catch (Exception e) {
            LOGGER.warn(e);
        }
        finally {
            con.close();
        }
    
        return listaConfiguraciones;    
    
    }
    
    public String nuevaConfiguracionPerisoEspecialBaco_action() throws SQLException{
        personalList = listarPersonal();
        almacenesList = listarAlmacenes();        
        tiposPermisoEspecialBacoList = listarTiposPermisoEspecialBaco();
        configuracionesPermisoEspecialBacoPersonalList = new ArrayList<ConfiguracionPermisoEspecialBaco>();
        return null;
    }
    
    public String editarConfiguracionPerisoEspecialBaco_action() throws SQLException{
        almacenesList = listarAlmacenes();        
        tiposPermisoEspecialBacoList = listarTiposPermisoEspecialBaco();
        configuracionesPermisoEspecialBacoPersonalList = listarConfiguracionPermisoEspecialBacoPorPesonal(configuracionPermisoEspecialBacoGestionar.getPersonal().getCodPersonal());        
    
        return null;
    }
    
    public List<ConfiguracionPermisoEspecialBaco> listarConfiguracionPermisoEspecialBacoPorPesonal(String codPersonal) throws SQLException{
        List<ConfiguracionPermisoEspecialBaco> listaConfiguraciones = new ArrayList<ConfiguracionPermisoEspecialBaco>();
        Connection con = null;
        try {
            con = Util.openConnection(con);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            StringBuilder consulta 
                    = new StringBuilder("SELECT cpeb.COD_CONFIGURACION_PERMISO_ESPECIAL_BACO, a.COD_ALMACEN, a.NOMBRE_ALMACEN, cpeb.COD_TIPO_PERMISO_ESPECIAL_BACO, tpeb.NOMBRE_TIPO_PERMISO_ESPECIAL_BACO");
                    consulta.append(" FROM CONFIGURACION_PERMISO_ESPECIAL_BACO cpeb ")
                            .append(" left outer join	ALMACENES a on a.COD_ALMACEN = cpeb.COD_ALMACEN ")
                            .append(" left outer join TIPO_PERMISO_ESPECIAL_BACO tpeb on cpeb.COD_TIPO_PERMISO_ESPECIAL_BACO = tpeb.COD_TIPO_PERMISO_ESPECIAL_BACO")
                            .append(" where a.COD_ESTADO_REGISTRO = 1")
                            .append(" and cpeb.COD_PERSONAL = '").append(codPersonal).append("'")
                            .append(" order by cpeb.COD_ALMACEN, cpeb.COD_TIPO_PERMISO_ESPECIAL_BACO");
            LOGGER.info("consulta: "+consulta.toString());
            ResultSet res = st.executeQuery(consulta.toString());
            while (res.next()) {
                ConfiguracionPermisoEspecialBaco configuracionPermisoEspecialBaco = new ConfiguracionPermisoEspecialBaco();
                configuracionPermisoEspecialBaco.setCodConfiguracionPermisoEspecialBaco(res.getInt("COD_CONFIGURACION_PERMISO_ESPECIAL_BACO"));
                configuracionPermisoEspecialBaco.getPersonal().setCodPersonal(codPersonal);
                configuracionPermisoEspecialBaco.getAlmacenes().setCodAlmacen(res.getInt("COD_ALMACEN"));
                configuracionPermisoEspecialBaco.getAlmacenes().setNombreAlmacen(res.getString("NOMBRE_ALMACEN"));
                configuracionPermisoEspecialBaco.getTipoPermisoEspecialBaco().setCodTipoPermisoEspecialBaco(res.getInt("COD_TIPO_PERMISO_ESPECIAL_BACO"));
                configuracionPermisoEspecialBaco.getTipoPermisoEspecialBaco().setNombreTipoPermisoEspecialBaco(res.getString("NOMBRE_TIPO_PERMISO_ESPECIAL_BACO"));
                
                listaConfiguraciones.add(configuracionPermisoEspecialBaco);
            }
   
        } 
        catch (Exception e) {
            LOGGER.warn(e);
        }
        finally {
            con.close();
        }
    
        return listaConfiguraciones;        
    }
    
    public List listarTiposPermisoEspecialBaco() throws SQLException{
        List listaTipos = new ArrayList();
        Connection con = null;
        try {
            con = Util.openConnection(con);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            StringBuilder consulta 
                    = new StringBuilder("select tpeb.COD_TIPO_PERMISO_ESPECIAL_BACO, tpeb.NOMBRE_TIPO_PERMISO_ESPECIAL_BACO");
                    consulta.append(" from TIPO_PERMISO_ESPECIAL_BACO tpeb");
            LOGGER.info("consulta: "+consulta.toString());
            ResultSet res = st.executeQuery(consulta.toString());            
            while (res.next()) {
                TipoPermisoEspecialBaco tipoPermisoEspecialBaco = new TipoPermisoEspecialBaco();
                tipoPermisoEspecialBaco.setCodTipoPermisoEspecialBaco(res.getInt("COD_TIPO_PERMISO_ESPECIAL_BACO"));
                tipoPermisoEspecialBaco.setNombreTipoPermisoEspecialBaco(res.getString("NOMBRE_TIPO_PERMISO_ESPECIAL_BACO"));   
                listaTipos.add(new SelectItem(tipoPermisoEspecialBaco.getCodTipoPermisoEspecialBaco(), tipoPermisoEspecialBaco.getNombreTipoPermisoEspecialBaco()));
//                listaTipos.add(tipoPermisoEspecialBaco);
            }
   
        } 
        catch (Exception e) {
            LOGGER.warn(e);
        }
        finally {
            con.close();
        }
    
        return listaTipos;        
    }
    
    public List listarAlmacenes() throws SQLException{
        List listaAlmacenes = new ArrayList();
        Connection con = null;
        try {
            con = Util.openConnection(con);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            StringBuilder consulta 
                    = new StringBuilder("select a.COD_ALMACEN, a.NOMBRE_ALMACEN");
                    consulta.append(" from ALMACENES a where a.COD_ESTADO_REGISTRO = 1");
            LOGGER.info("consulta: "+consulta.toString());
            ResultSet res = st.executeQuery(consulta.toString());
            while (res.next()) {
                Almacenes almacenes = new Almacenes();
                almacenes.setCodAlmacen(res.getInt("COD_ALMACEN"));
                almacenes.setNombreAlmacen(res.getString("NOMBRE_ALMACEN"));  
                listaAlmacenes.add(new SelectItem(almacenes.getCodAlmacen(), almacenes.getNombreAlmacen()));
//                listaAlmacenes.add(almacenes);
            }
   
        } 
        catch (Exception e) {
            LOGGER.warn(e);
        }
        finally {
            con.close();
        }
    
        return listaAlmacenes;        
    }
    
    public List listarPersonal() throws SQLException{
        List listaPersonal = new ArrayList();
        Connection con = null;
        try {
            con = Util.openConnection(con);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            StringBuilder consulta 
                    = new StringBuilder("select p.COD_PERSONAL, p.NOMBRES_PERSONAL, p.nombre2_personal, p.AP_PATERNO_PERSONAL, p.AP_MATERNO_PERSONAL");
                    consulta.append(" from PERSONAL p ");
                    consulta.append(" where p.COD_ESTADO_PERSONA = 1");
                    consulta.append(" and p.COD_PERSONAL not in (select distinct cod_personal from CONFIGURACION_PERMISO_ESPECIAL_BACO)");                    
                    consulta.append(" order by p.AP_PATERNO_PERSONAL,p.AP_MATERNO_PERSONAL,NOMBRES_PERSONAL");
            LOGGER.info("consulta: "+consulta.toString());
            ResultSet res = st.executeQuery(consulta.toString());
            while (res.next()) {
                Personal personal = new Personal();
                personal.setCodPersonal(res.getString("COD_PERSONAL"));
                personal.setNombrePersonal(res.getString("NOMBRES_PERSONAL"));                
                personal.setNombre2Personal(res.getString("nombre2_personal"));                
                personal.setApPaternoPersonal(res.getString("AP_PATERNO_PERSONAL"));                
                personal.setApMaternoPersonal(res.getString("AP_MATERNO_PERSONAL"));  
                listaPersonal.add(new SelectItem(personal.getCodPersonal(), personal.getNombreCompleto2()));
//                listaPersonal.add(personal);
            }
   
        } 
        catch (Exception e) {
            LOGGER.warn(e);
        }
        finally {
            con.close();
        }
    
        return listaPersonal;        
    }
    
    public String agregarPermisoEspecialBacoPersonal_action(){
        configuracionesPermisoEspecialBacoPersonalList.add(new ConfiguracionPermisoEspecialBaco());    
        return null;  
    }
    public String quitarPermisoEspecialBacoPersonal_action(){
        System.out.println("indicePermisoEspecialBaco"+indicePermisoEspecialBaco);
        configuracionesPermisoEspecialBacoPersonalList.remove(indicePermisoEspecialBaco);    
        return null;  
    }
    
    public String guardarNuevoPermisoEspecialBaco_action() throws SQLException{
        mensaje="";
        registradoCorrectamente=false;
        Connection con = null;
        try {
            con = Util.openConnection(con);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            for (ConfiguracionPermisoEspecialBaco configuracionesPermisoEspecialBaco : configuracionesPermisoEspecialBacoPersonalList) {
                System.out.println("---->"+configuracionesPermisoEspecialBaco.getAlmacenes().getCodAlmacen());
                System.out.println("---->"+configuracionesPermisoEspecialBaco.getTipoPermisoEspecialBaco().getCodTipoPermisoEspecialBaco());
                StringBuilder consulta 
                    = new StringBuilder("INSERT INTO CONFIGURACION_PERMISO_ESPECIAL_BACO(COD_ALMACEN, COD_TIPO_PERMISO_ESPECIAL_BACO, COD_PERSONAL)");
                    consulta.append(" VALUES ( '").append(configuracionesPermisoEspecialBaco.getAlmacenes().getCodAlmacen()).append("'");
                    consulta.append(" ,'").append(configuracionesPermisoEspecialBaco.getTipoPermisoEspecialBaco().getCodTipoPermisoEspecialBaco()).append("'");
                    consulta.append(" ,'").append(configuracionPermisoEspecialBacoGestionar.getPersonal().getCodPersonal()).append("'");
                    consulta.append(" )");
                LOGGER.info("consulta: "+consulta.toString());
                if (st.executeUpdate(consulta.toString())>0) {
                    LOGGER.info("registrado creado");
                }
            }
            mensaje="Registrado correctamente.";
            registradoCorrectamente = true;
   
        } 
        catch (Exception e) {
            LOGGER.warn(e);
            mensaje="Ocurrió un error.";
        }
        finally {
            con.close();
        } 
        configuracionesPermisoEspecialBacoList = listarConfiguracionPermisoEspecialBaco();
        return null;      
    }

    //-------EDITAR
    public String guardarEditarPermisoEspecialBaco_action() throws SQLException{
        mensaje="";
        registradoCorrectamente=false;
        Connection con = null;
        try {
            con = Util.openConnection(con);
            con.setAutoCommit(false);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            StringBuilder consulta = new StringBuilder("DELETE FROM CONFIGURACION_PERMISO_ESPECIAL_BACO ");
                    consulta.append(" WHERE COD_PERSONAL = '").append(configuracionPermisoEspecialBacoGestionar.getPersonal().getCodPersonal()).append("'");
            if (st.executeUpdate(consulta.toString())>0) LOGGER.info("registro eliminado");
            
            for (ConfiguracionPermisoEspecialBaco configuracionesPermisoEspecialBaco : configuracionesPermisoEspecialBacoPersonalList) {
                consulta 
                    = new StringBuilder("INSERT INTO CONFIGURACION_PERMISO_ESPECIAL_BACO(COD_ALMACEN, COD_TIPO_PERMISO_ESPECIAL_BACO, COD_PERSONAL)");
                    consulta.append(" VALUES ( '").append(configuracionesPermisoEspecialBaco.getAlmacenes().getCodAlmacen()).append("'");
                    consulta.append(" ,'").append(configuracionesPermisoEspecialBaco.getTipoPermisoEspecialBaco().getCodTipoPermisoEspecialBaco()).append("'");
                    consulta.append(" ,'").append(configuracionPermisoEspecialBacoGestionar.getPersonal().getCodPersonal()).append("'");
                    consulta.append(" )");                    
                LOGGER.info("consulta: "+consulta.toString());
                if (st.executeUpdate(consulta.toString())>0) LOGGER.info("registrado creado");
            }
            con.commit();
            mensaje="Registrado correctamente.";
            registradoCorrectamente = true;
   
        } 
        catch (Exception e) {
            LOGGER.warn(e);
            mensaje="Ocurrió un error.";
        }
        finally {
            con.close();
        } 
        configuracionesPermisoEspecialBacoList = listarConfiguracionPermisoEspecialBaco();
        return null;      
    }
    
    

    public ConfiguracionPermisoEspecialBaco getConfiguracionPermisoEspecialBacoGestionar() {
        return configuracionPermisoEspecialBacoGestionar;
    }

    public void setConfiguracionPermisoEspecialBacoGestionar(ConfiguracionPermisoEspecialBaco configuracionPermisoEspecialBacoGestionar) {
        this.configuracionPermisoEspecialBacoGestionar = configuracionPermisoEspecialBacoGestionar;
    }

    public List<TipoPermisoEspecialBaco> getTiposPermisoEspecialBacoList() {
        return tiposPermisoEspecialBacoList;
    }

    public void setTiposPermisoEspecialBacoList(List<TipoPermisoEspecialBaco> tiposPermisoEspecialBacoList) {
        this.tiposPermisoEspecialBacoList = tiposPermisoEspecialBacoList;
    }

    public List<Almacenes> getAlmacenesList() {
        return almacenesList;
    }

    public void setAlmacenesList(List<Almacenes> almacenesList) {
        this.almacenesList = almacenesList;
    }

    public List<Personal> getPersonalList() {
        return personalList;
    }

    public void setPersonalList(List<Personal> personalList) {
        this.personalList = personalList;
    }
   
    public int getIndicePermisoEspecialBaco() {
        return indicePermisoEspecialBaco;
    }

    public void setIndicePermisoEspecialBaco(int indicePermisoEspecialBaco) {
        this.indicePermisoEspecialBaco = indicePermisoEspecialBaco;
    }

    public List<ConfiguracionPermisoEspecialBaco> getConfiguracionesPermisoEspecialBacoList() {
        return configuracionesPermisoEspecialBacoList;
    }

    public void setConfiguracionesPermisoEspecialBacoList(List<ConfiguracionPermisoEspecialBaco> configuracionesPermisoEspecialBacoList) {
        this.configuracionesPermisoEspecialBacoList = configuracionesPermisoEspecialBacoList;
    }

//    public ConfiguracionPermisoEspecialBaco getConfiguracionPermisoEspecialBacoSeleccionado() {
//        return configuracionPermisoEspecialBacoSeleccionado;
//    }
//
//    public void setConfiguracionPermisoEspecialBacoSeleccionado(ConfiguracionPermisoEspecialBaco configuracionPermisoEspecialBacoSeleccionado) {
//        this.configuracionPermisoEspecialBacoSeleccionado = configuracionPermisoEspecialBacoSeleccionado;
//    }

    public List<ConfiguracionPermisoEspecialBaco> getConfiguracionesPermisoEspecialBacoPersonalList() {
        return configuracionesPermisoEspecialBacoPersonalList;
    }

    public void setConfiguracionesPermisoEspecialBacoPersonalList(List<ConfiguracionPermisoEspecialBaco> configuracionesPermisoEspecialBacoPersonalList) {
        this.configuracionesPermisoEspecialBacoPersonalList = configuracionesPermisoEspecialBacoPersonalList;
    }


    
    
}
