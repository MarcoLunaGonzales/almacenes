/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofar.dao;

import com.cofar.bean.Capitulos;
import com.cofar.util.Util;
import com.cofar.web.ManagedAccesoSistema;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
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
 * @author DASISAQ
 */
public class DaoCapitulos extends DaoBase{

    public DaoCapitulos(Logger logger) {
        this.LOGGER = logger;
    }
    public DaoCapitulos() {
        this.LOGGER = LogManager.getRootLogger();
    }
    
    public boolean guardar(Capitulos capitulos){
        boolean guardado = false;
        try {
            con = Util.openConnection(con);
            con.setAutoCommit(false);
            StringBuilder consulta=new StringBuilder("INSERT INTO CAPITULOS(NOMBRE_CAPITULO, OBS_CAPITULO,COD_ESTADO_REGISTRO, CAPITULO_ALMACEN)")
                                            .append(" VALUES (")
                                                    .append("?,")
                                                    .append("?,")
                                                    .append(capitulos.getEstadoReferencial().getCodEstadoRegistro()).append(",")
                                                    .append(capitulos.getCapituloAlmacen())
                                            .append(")");
            LOGGER.debug("consulta registrar capitulo: "+consulta.toString());
            PreparedStatement pst=con.prepareStatement(consulta.toString(),PreparedStatement.RETURN_GENERATED_KEYS);
            pst.setString(1,capitulos.getNombreCapitulo().trim());LOGGER.info("p1: "+capitulos.getNombreCapitulo().trim());
            pst.setString(2,capitulos.getObsCapitulo().trim());LOGGER.info("p1: "+capitulos.getObsCapitulo().trim());
            if(pst.executeUpdate()>0)LOGGER.info("se registro el capitulo");
            ResultSet res = pst.getGeneratedKeys();
            res.next();
            capitulos.setCodCapitulo(res.getInt(1));
            //<editor-fold defaultstate="collapsed" desc="registro de log">
                ManagedAccesoSistema managed =(ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
                consulta = new StringBuilder(" EXEC PAB_REGISTRO_CAPITULOS_LOG ")
                                .append(capitulos.getCodCapitulo()).append(",")
                                .append(managed.getUsuarioModuloBean().getCodUsuarioGlobal()).append(",")
                                .append(COD_TIPO_TRANSACCION_LOG_AGREGAR).append(",")
                                .append("'registro de nuevo capitulo 3'");
                LOGGER.debug("consulta registrar log capitulo " +consulta.toString());
                CallableStatement call = con.prepareCall(consulta.toString());
                call.executeUpdate();
            //</editor-fold>
            con.commit();
            guardado = true;
        } 
        catch (SQLException ex){
            guardado = false;
            LOGGER.warn(ex);
            this.rollbackConexion();
        }
        catch (Exception ex){
            guardado = false;
            LOGGER.warn(ex);
            this.rollbackConexion();
        }
        finally{
            this.cerrarConexion();
        }
        return guardado;
    }
    public boolean modificar(Capitulos capitulos){
        boolean modificado = false;
        try {
            con = Util.openConnection(con);
            con.setAutoCommit(false);
            StringBuilder consulta=new StringBuilder("UPDATE CAPITULOS ")
                                                .append("SET NOMBRE_CAPITULO = ?")
                                                        .append(" ,OBS_CAPITULO = ?")
                                                        .append(" ,COD_ESTADO_REGISTRO = ").append(capitulos.getEstadoReferencial().getCodEstadoRegistro())
                                                        .append(" ,CAPITULO_ALMACEN = ").append(capitulos.getCapituloAlmacen())
                                                .append(" WHERE COD_CAPITULO = ").append(capitulos.getCodCapitulo());
            LOGGER.debug("consulta modificar capitulo: "+consulta.toString());
            PreparedStatement pst=con.prepareStatement(consulta.toString());
            pst.setString(1,capitulos.getNombreCapitulo().trim());LOGGER.info("p1: "+capitulos.getNombreCapitulo().trim());
            pst.setString(2,capitulos.getObsCapitulo().trim());LOGGER.info("p1: "+capitulos.getObsCapitulo().trim());
            if(pst.executeUpdate()>0)LOGGER.info("se modifico el capitulo");
            
            //<editor-fold defaultstate="collapsed" desc="registro de log">
                ManagedAccesoSistema managed =(ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
                consulta = new StringBuilder(" EXEC PAB_REGISTRO_CAPITULOS_LOG ")
                                .append(capitulos.getCodCapitulo()).append(",")
                                .append(managed.getUsuarioModuloBean().getCodUsuarioGlobal()).append(",")
                                .append(COD_TIPO_TRANSACCION_LOG_EDICION).append(",")
                                .append("'edicion de capitulo'");
                LOGGER.debug("consulta registrar log capitulo " +consulta.toString());
                CallableStatement call = con.prepareCall(consulta.toString());
                call.executeUpdate();
            //</editor-fold>
            
            con.commit();
            modificado = true;
        } 
        catch (SQLException ex){
            modificado = false;
            LOGGER.warn(ex);
            this.rollbackConexion();
        }
        catch (Exception ex){
            modificado = false;
            LOGGER.warn(ex);
            this.rollbackConexion();
        }
        finally{
            this.cerrarConexion();
        }
        return modificado;
    }
    
    public boolean eliminar(int codCapitulo){
        boolean eliminado = false;
        try {
            con = Util.openConnection(con);
            con.setAutoCommit(false);
            StringBuilder consulta=new StringBuilder("DELETE CAPITULOS ")
                                                .append(" WHERE COD_CAPITULO = ").append(codCapitulo);
            LOGGER.debug("consulta eliminar capitulo: "+consulta.toString());
            PreparedStatement pst=con.prepareStatement(consulta.toString());
            if(pst.executeUpdate()>0)LOGGER.info("se elimino el capitulo");
            
            //<editor-fold defaultstate="collapsed" desc="registro de log">
                ManagedAccesoSistema managed =(ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
                consulta = new StringBuilder(" EXEC PAB_REGISTRO_CAPITULOS_LOG ")
                                .append(codCapitulo).append(",")
                                .append(managed.getUsuarioModuloBean().getCodUsuarioGlobal()).append(",")
                                .append(COD_TIPO_TRANSACCION_LOG_ELIMINAR).append(",")
                                .append("''");
                LOGGER.debug("consulta registrar log capitulo " +consulta.toString());
                CallableStatement call = con.prepareCall(consulta.toString());
                call.executeUpdate();
            //</editor-fold>
            
            con.commit();
            eliminado = true;
        } 
        catch (SQLException ex){
            eliminado = false;
            LOGGER.warn(ex);
            this.rollbackConexion();
        }
        catch (Exception ex){
            eliminado = false;
            LOGGER.warn(ex);
            this.rollbackConexion();
        }
        finally{
            this.cerrarConexion();
        }
        return eliminado;
    }
    public List<SelectItem> listarActivosSelectItem(){
        List<SelectItem> capitulosSelectList = new ArrayList<>();
        try {
            con = Util.openConnection(con);
            StringBuilder consulta = new StringBuilder("select c.COD_CAPITULO,c.NOMBRE_CAPITULO")
                                                .append(" from capitulos c ")
                                                .append(" where c.COD_ESTADO_REGISTRO=1")
                                                .append(" order by c.NOMBRE_CAPITULO");
            LOGGER.debug("consulta cargar capitulos select: "+consulta.toString());
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet res=st.executeQuery(consulta.toString());
            while(res.next()){
                capitulosSelectList.add(new SelectItem(res.getInt("COD_CAPITULO"),res.getString("NOMBRE_CAPITULO")));
            }
        } catch (SQLException ex) {
            LOGGER.warn(ex);
        } catch (Exception ex) {
            LOGGER.warn(ex);
        } finally {
            this.cerrarConexion();
        }
        return capitulosSelectList;
    }
    public List<Capitulos> listar(Capitulos capituloCriterio){
        List<Capitulos> capitulosList = new ArrayList<>();
        try {
            con = Util.openConnection(con);
            StringBuilder consulta = new StringBuilder("select c.COD_CAPITULO,c.NOMBRE_CAPITULO,er.COD_ESTADO_REGISTRO,er.NOMBRE_ESTADO_REGISTRO,c.OBS_CAPITULO,")
                                                        .append(" c.CAPITULO_ALMACEN,gruposRelacionados.cantidadDatosRelacionados,")
                                                        .append(" gruposRelacionadosActivos.cantidadDatosRelacionadosActivos")
                                                .append(" from capitulos c")
                                                        .append(" inner join ESTADOS_REFERENCIALES er on er.COD_ESTADO_REGISTRO= c.COD_ESTADO_REGISTRO")
                                                        .append(" left join(")
                                                                .append(" select g.COD_CAPITULO,count(*) as cantidadDatosRelacionados")
                                                                .append(" from grupos g")
                                                                .append(" group by g.COD_CAPITULO")
                                                        .append(" ) as gruposRelacionados on gruposRelacionados.COD_CAPITULO = c.COD_CAPITULO")
                                                        .append(" left join(")
                                                                .append(" select g.COD_CAPITULO,count(*) as cantidadDatosRelacionadosActivos")
                                                                .append(" from grupos g")
                                                                .append(" where g.COD_ESTADO_REGISTRO = 1")
                                                                .append(" group by g.COD_CAPITULO")
                                                        .append(" ) as gruposRelacionadosActivos on gruposRelacionadosActivos.COD_CAPITULO = c.COD_CAPITULO")
                                                .append(" where 1=1")
                                                .append(" order by c.NOMBRE_CAPITULO");
            LOGGER.debug("consulta listar capitulos: "+consulta.toString());
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet res=st.executeQuery(consulta.toString());
            while(res.next()){
                Capitulos capitulo = new Capitulos();
                capitulo.setCodCapitulo(res.getInt("COD_CAPITULO"));
                capitulo.setNombreCapitulo(res.getString("NOMBRE_CAPITULO"));
                capitulo.setObsCapitulo(res.getString("OBS_CAPITULO"));
                capitulo.setCapituloAlmacen(res.getInt("CAPITULO_ALMACEN"));
                capitulo.getEstadoReferencial().setCodEstadoRegistro(res.getString("COD_ESTADO_REGISTRO"));
                capitulo.getEstadoReferencial().setNombreEstadoRegistro(res.getString("NOMBRE_ESTADO_REGISTRO"));
                capitulo.setCantidadDatosRelacionados(res.getInt("cantidadDatosRelacionados"));
                capitulo.setCantidadDatosRelacionadosActivos(res.getInt("cantidadDatosRelacionadosActivos"));
                capitulosList.add(capitulo);
            }
        } catch (SQLException ex) {
            LOGGER.warn(ex);
        } catch (Exception ex) {
            LOGGER.warn(ex);
        } finally {
            this.cerrarConexion();
        }
        return capitulosList;
    }
}
