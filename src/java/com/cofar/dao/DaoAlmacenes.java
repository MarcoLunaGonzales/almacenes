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
public class DaoAlmacenes extends DaoBase{

    public DaoAlmacenes() {
        this.LOGGER = LogManager.getRootLogger();
    }
    
    public DaoAlmacenes(Logger logger) {
        this.LOGGER = logger;
    }
    public Almacenes listarPorId(int codAlmacen){
        Almacenes almacenCriterio = new Almacenes();
        almacenCriterio.setCodAlmacen(codAlmacen);
        return this.listar(almacenCriterio).get(0);
    }
    public List<Almacenes> listar(Almacenes almacenes){
        List<Almacenes> almacenesList = new ArrayList<>();
        try {
            con = Util.openConnection(con);
            StringBuilder consulta = new StringBuilder("select a.COD_ALMACEN,a.NOMBRE_ALMACEN,er.COD_ESTADO_REGISTRO,er.NOMBRE_ESTADO_REGISTRO,")
                                                        .append(" a.APLICA_GESTION_UBICACIONES")
                                                .append(" from almacenes a")
                                                        .append(" inner join ESTADOS_REFERENCIALES er on er.COD_ESTADO_REGISTRO = a.COD_ESTADO_REGISTRO")
                                                .append(" where 1=1 ");
                                                    if(almacenes.getCodAlmacen() > 0)
                                                        consulta.append(" and a.COD_ALMACEN =").append(almacenes.getCodAlmacen());
                                        consulta.append(" order by a.NOMBRE_ALMACEN");
            LOGGER.debug("consulta cargar almacenes: "+consulta.toString());
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet res=st.executeQuery(consulta.toString());
            while(res.next()){
                Almacenes almacen = new Almacenes();
                almacen.setCodAlmacen(res.getInt("COD_ALMACEN"));
                almacen.setNombreAlmacen(res.getString("NOMBRE_ALMACEN"));
                almacen.getEstadoReferencial().setCodEstadoRegistro(res.getString("COD_ESTADO_REGISTRO"));
                almacen.getEstadoReferencial().setNombreEstadoRegistro(res.getString("NOMBRE_ESTADO_REGISTRO"));
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
