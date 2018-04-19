/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofar.dao;
;
import com.cofar.bean.Grupos;
import com.cofar.util.Util;
import com.cofar.web.ManagedAccesoSistema;
import java.sql.CallableStatement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import static jdk.nashorn.internal.objects.NativeFunction.call;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author DASISAQ
 */
public class DaoGrupos extends DaoBase{

    public DaoGrupos(Logger logger) {
        this.LOGGER = logger;
    }
    public DaoGrupos() {
        this.LOGGER = LogManager.getRootLogger();
    }
    
    public boolean guardar(Grupos grupos){
        boolean guardado = false;
        try {
            con = Util.openConnection(con);
            con.setAutoCommit(false);
            StringBuilder consulta = new StringBuilder(" INSERT INTO GRUPOS(COD_CAPITULO, NOMBRE_GRUPO, OBS_GRUPO,")
                                                    .append(" COD_ESTADO_REGISTRO, GRUPO_ALMACEN, COD_PLAN_CUENTA)")
                                            .append(" VALUES (")
                                                    .append(grupos.getCapitulos().getCodCapitulo()).append(",")
                                                    .append("?,")
                                                    .append("?,")
                                                    .append(grupos.getEstadoReferencial().getCodEstadoRegistro()).append(",")
                                                    .append(grupos.getGrupoAlmacen()).append(",")
                                                    .append(grupos.getPlanDeCuentas().getCodPlanCuenta())
                                            .append(" )");
            LOGGER.debug("consulta registrar grupo: "+consulta.toString());
            PreparedStatement pst=con.prepareStatement(consulta.toString(),PreparedStatement.RETURN_GENERATED_KEYS);
            pst.setString(1,grupos.getNombreGrupo().trim());LOGGER.info("p1: "+grupos.getNombreGrupo().trim());
            pst.setString(2,grupos.getObsGrupo().trim());LOGGER.info("p2: "+grupos.getObsGrupo().trim());
            if(pst.executeUpdate()>0)LOGGER.info("se registro el grupo");
            ResultSet res = pst.getGeneratedKeys();
            res.next();
            grupos.setCodGrupo(res.getInt(1));
            //<editor-fold defaultstate="collapsed" desc="registro de log">
                ManagedAccesoSistema managed =(ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
                consulta = new StringBuilder(" EXEC PAB_REGISTRO_GRUPOS_LOG ")
                                .append(grupos.getCodGrupo()).append(",")
                                .append(managed.getUsuarioModuloBean().getCodUsuarioGlobal()).append(",")
                                .append(COD_TIPO_TRANSACCION_LOG_AGREGAR).append(",")
                                .append("'registro de nuevo grupo'");
                LOGGER.debug("consulta registrar log grupo: " +consulta.toString());
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
    public boolean modificar(Grupos grupos){
        boolean modificado = false;
        try {
            con = Util.openConnection(con);
            con.setAutoCommit(false);
            StringBuilder consulta=new StringBuilder("UPDATE GRUPOS ")
                                            .append(" SET COD_CAPITULO = ").append(grupos.getCapitulos().getCodCapitulo())
                                                    .append(" ,NOMBRE_GRUPO = ?")
                                                    .append(" ,OBS_GRUPO = ?")
                                                    .append(" ,COD_ESTADO_REGISTRO = ").append(grupos.getEstadoReferencial().getCodEstadoRegistro())
                                                    .append(" ,GRUPO_ALMACEN = ").append(grupos.getGrupoAlmacen())
                                                    .append(" ,COD_PLAN_CUENTA = ").append(grupos.getPlanDeCuentas().getCodPlanCuenta())
                                            .append(" WHERE COD_GRUPO = ").append(grupos.getCodGrupo());
            LOGGER.debug("consulta modificar grupo: "+consulta.toString());
            PreparedStatement pst=con.prepareStatement(consulta.toString());
            pst.setString(1,grupos.getNombreGrupo().trim());LOGGER.info("p1: "+grupos.getNombreGrupo().trim());
            pst.setString(2,grupos.getObsGrupo().trim());LOGGER.info("p2: "+grupos.getObsGrupo().trim());
            if(pst.executeUpdate()>0)LOGGER.info("se modifico el grupo");
            //<editor-fold defaultstate="collapsed" desc="registro de log">
                ManagedAccesoSistema managed =(ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
                consulta = new StringBuilder(" EXEC PAB_REGISTRO_GRUPOS_LOG ")
                                .append(grupos.getCodGrupo()).append(",")
                                .append(managed.getUsuarioModuloBean().getCodUsuarioGlobal()).append(",")
                                .append(COD_TIPO_TRANSACCION_LOG_EDICION).append(",")
                                .append("'edicion de grupo'");
                LOGGER.debug("consulta registrar log grupo: " +consulta.toString());
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
    
    public boolean eliminar(int codGrupo){
        boolean eliminado = false;
        try {
            con = Util.openConnection(con);
            con.setAutoCommit(false);
            StringBuilder consulta = new StringBuilder("DELETE GRUPOS_FECHA_VENCIMIENTO")
                                    .append(" where COD_GRUPO=").append(codGrupo);
            LOGGER.debug("consulta eliminar grupo fecha vencimiento: "+consulta.toString());
            PreparedStatement pst=con.prepareStatement(consulta.toString());
            if(pst.executeUpdate() > 0)LOGGER.info("se elimino grupo fecha de vencimiento");

            consulta=new StringBuilder("DELETE GRUPOS ")
                                .append(" WHERE COD_GRUPO = ").append(codGrupo);
            LOGGER.debug("consulta eliminar grupo: "+consulta.toString());
            pst=con.prepareStatement(consulta.toString());
            if(pst.executeUpdate()>0)LOGGER.info("se elimino el capitulo");
            
            //<editor-fold defaultstate="collapsed" desc="registro de log">
                ManagedAccesoSistema managed =(ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
                consulta = new StringBuilder(" EXEC PAB_REGISTRO_GRUPOS_LOG ")
                                .append(codGrupo).append(",")
                                .append(managed.getUsuarioModuloBean().getCodUsuarioGlobal()).append(",")
                                .append(COD_TIPO_TRANSACCION_LOG_ELIMINAR).append(",")
                                .append("''");
                LOGGER.debug("consulta registrar log grupo: " +consulta.toString());
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
    public List<Grupos> listar(Grupos grupoCriterio){
        List<Grupos> gruposList = new ArrayList<>();
        try {
            con = Util.openConnection(con);
            StringBuilder consulta = new StringBuilder("select g.COD_GRUPO,g.NOMBRE_GRUPO,g.COD_ESTADO_REGISTRO,er.NOMBRE_ESTADO_REGISTRO,")
                                                            .append(" g.COD_CAPITULO,c.NOMBRE_CAPITULO,g.OBS_GRUPO,")
                                                            .append(" g.COD_PLAN_CUENTA,pc.NOMBRE_CUENTA,")
                                                            .append(" datosMaterialesRelacionados.cantidadDatosRelacionados,")
                                                            .append(" datosMaterialesRelacionados.cantidadDatosRelacionadosActivos")
                                                .append(" from GRUPOS g")
                                                        .append(" inner join ESTADOS_REFERENCIALES er on er.COD_ESTADO_REGISTRO = g.COD_ESTADO_REGISTRO")
                                                        .append(" inner join capitulos c on c.COD_CAPITULO = g.COD_CAPITULO")
                                                        .append(" left outer join PLAN_DE_CUENTAS pc on pc.COD_PLAN_CUENTA = g.COD_PLAN_CUENTA")
                                                        .append(" left join (")
                                                                .append(" select count(*) as cantidadDatosRelacionados,m.COD_GRUPO,")
                                                                        .append(" sum(case when m.COD_ESTADO_REGISTRO=1 then 1 else 0 end) as cantidadDatosRelacionadosActivos")
                                                                .append(" from materiales m ")
                                                                .append(" group by m.COD_GRUPO")
                                                        .append(" ) as datosMaterialesRelacionados on datosMaterialesRelacionados.COD_GRUPO= g.COD_GRUPO")
                                                .append(" where 1=1")
                                                .append(" order by g.NOMBRE_GRUPO");
            LOGGER.debug("consulta listar grupos: "+consulta.toString());
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet res=st.executeQuery(consulta.toString());
            while(res.next()){
                Grupos grupos = new Grupos();
                grupos.setCodGrupo(res.getInt("COD_GRUPO"));
                grupos.setNombreGrupo(res.getString("NOMBRE_GRUPO"));
                grupos.setObsGrupo(res.getString("OBS_GRUPO"));
                grupos.getCapitulos().setCodCapitulo(res.getInt("COD_CAPITULO"));
                grupos.getCapitulos().setNombreCapitulo(res.getString("NOMBRE_CAPITULO"));
                grupos.getEstadoReferencial().setCodEstadoRegistro(res.getString("COD_ESTADO_REGISTRO"));
                grupos.getEstadoReferencial().setNombreEstadoRegistro(res.getString("NOMBRE_ESTADO_REGISTRO"));
                grupos.getPlanDeCuentas().setCodPlanCuenta(res.getInt("COD_PLAN_CUENTA"));
                grupos.getPlanDeCuentas().setNombreCuenta(res.getString("NOMBRE_CUENTA"));
                grupos.setCantidadDatosRelacionados(res.getInt("cantidadDatosRelacionados"));
                grupos.setCantidadDatosRelacionadosActivos(res.getInt("cantidadDatosRelacionadosActivos"));
                gruposList.add(grupos);
            }
        } catch (SQLException ex) {
            LOGGER.warn(ex);
        } catch (Exception ex) {
            LOGGER.warn(ex);
        } finally {
            this.cerrarConexion();
        }
        return gruposList;
    }
}
