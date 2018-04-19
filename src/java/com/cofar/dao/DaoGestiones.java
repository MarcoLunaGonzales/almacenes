/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofar.dao;

import com.cofar.bean.Gestiones;
import com.cofar.util.Util;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author DASISAQ
 */
public class DaoGestiones extends DaoBase{

    public DaoGestiones() {
        this.LOGGER = LogManager.getRootLogger();
    }
    public DaoGestiones(Logger LOGGER){
        this.LOGGER = LOGGER;
    }
    
    public Gestiones gestionActual(){
        Gestiones gestion = new Gestiones();
        try {
            con = Util.openConnection(con);
            StringBuilder consulta = new StringBuilder(" select g.COD_GESTION,g.NOMBRE_GESTION")
                                                .append(" from GESTIONES g ")
                                                .append(" where getdate() between g.FECHA_INI and g.FECHA_FIN");
            LOGGER.debug("consulta cargar gestion activa: "+consulta.toString());
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet res=st.executeQuery(consulta.toString());
            if(res.next()){
                gestion.setCodGestion(res.getString("COD_GESTION"));
                gestion.setNombreGestion(res.getString("NOMBRE_GESTION"));
            }
        } catch (SQLException ex) {
            LOGGER.warn(ex.getMessage());
        } catch (Exception ex) {
            LOGGER.warn(ex.getMessage());
        } finally {
            this.cerrarConexion();
        }
        return gestion;
    }
    
}
