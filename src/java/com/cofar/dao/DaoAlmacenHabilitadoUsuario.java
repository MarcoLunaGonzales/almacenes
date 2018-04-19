/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofar.dao;

import com.cofar.bean.Almacenes;
import com.cofar.util.Util;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author DASISAQ
 */
public class DaoAlmacenHabilitadoUsuario extends DaoBase{

    public DaoAlmacenHabilitadoUsuario() {
        this.LOGGER = LogManager.getRootLogger();
    }
    public DaoAlmacenHabilitadoUsuario(Logger logger) {
        this.LOGGER = logger;
    }
    public List<Almacenes> listarPorUsuario(int codPersonal){
        List<Almacenes> almacenesList = new ArrayList<>();
        try {
            con = Util.openConnection(con);
            StringBuilder consulta = new StringBuilder("select a.COD_ALMACEN,a.NOMBRE_ALMACEN,a.COD_ESTADO_REGISTRO,a.APLICA_GESTION_UBICACIONES")
                                                .append(" from ALMACEN_HABILITADO_USUARIO ahu  ")
                                                        .append(" inner join ALMACENES a on a.COD_ALMACEN = ahu.COD_ALMACEN ")
                                                .append(" where ahu.COD_PERSONAL = ").append(codPersonal)
                                                        .append(" and ahu.COD_ESTADO = 1 ");
            LOGGER.debug("consulta cargar almacenes habilitados: "+consulta.toString());
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet res=st.executeQuery(consulta.toString());
            while(res.next()){
                Almacenes almacen = new Almacenes();
                almacen.setCodAlmacen(res.getInt("COD_ALMACEN"));
                almacen.setNombreAlmacen(res.getString("NOMBRE_ALMACEN"));
                almacen.setAplicaGestionUbicaciones(res.getBoolean("APLICA_GESTION_UBICACIONES"));
                almacenesList.add(almacen);
            }
        } catch (SQLException ex) {
            LOGGER.warn(ex);
        } catch (Exception ex) {
            LOGGER.warn(ex);
        } finally {
            this.cerrarConexion();
        }
        return almacenesList;
    }
    
    
}
