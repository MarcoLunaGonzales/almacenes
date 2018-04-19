/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofar.dao;

import com.cofar.util.Util;
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
public class DaoEstadosReferenciales extends DaoBase{

    public DaoEstadosReferenciales() {
        this.LOGGER = LogManager.getRootLogger();
    }
    public DaoEstadosReferenciales(Logger logger) {
        this.LOGGER = logger;
    }
    
    public List<SelectItem> listarActivosRegistro(){
        List<SelectItem> estadosSelectList = new ArrayList<>();
        try {
            con = Util.openConnection(con);
            StringBuilder consulta = new StringBuilder("select er.COD_ESTADO_REGISTRO,er.NOMBRE_ESTADO_REGISTRO")
                                                .append(" from ESTADOS_REFERENCIALES er ")
                                                .append(" where er.ESTADO_VALIDO_REGISTRO = 1")
                                                .append(" order by er.NOMBRE_ESTADO_REGISTRO");
            LOGGER.debug("consulta cargar estados validos : "+consulta.toString());
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet res=st.executeQuery(consulta.toString());
            while(res.next()){
                estadosSelectList.add(new SelectItem(res.getString("COD_ESTADO_REGISTRO"),res.getString("NOMBRE_ESTADO_REGISTRO")));
            }
        } catch (SQLException ex) {
            LOGGER.warn(ex);
        } catch (Exception ex) {
            LOGGER.warn(ex);
        } finally {
            this.cerrarConexion();
        }
        return estadosSelectList;
    }
    
    
    
    
}
