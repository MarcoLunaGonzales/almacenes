/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofar.dao;

import com.cofar.bean.Gestiones;
import com.cofar.bean.Meses;
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
public class DaoMeses extends DaoBase{

    public DaoMeses() {
        this.LOGGER = LogManager.getRootLogger();
    }
    public DaoMeses(Logger LOGGER){
        this.LOGGER = LOGGER;
    }
    
    public List<SelectItem> listarMesCalendario(){
        List<SelectItem> mesesSelectList = new ArrayList<>();
        try {
            con = Util.openConnection(con);
            StringBuilder consulta = new StringBuilder(" select m.COD_MES,m.NOMBRE_MES")
                                                .append(" from meses m ")
                                                .append(" where m.MES_CALENDARIO =1")
                                                .append(" order by m.ORDEN_MES");
            LOGGER.debug("consulta cargar meses calendario "+consulta.toString());
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet res=st.executeQuery(consulta.toString());
            if(res.next()){
                mesesSelectList.add(new SelectItem(res.getInt("COD_MES"), res.getString("NOMBRE_MES")));
            }
        } catch (SQLException ex) {
            LOGGER.warn(ex.getMessage());
        } catch (Exception ex) {
            LOGGER.warn(ex.getMessage());
        } finally {
            this.cerrarConexion();
        }
        return mesesSelectList;
    }
    
}
