/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofar.dao;

import java.sql.Connection;
import java.sql.SQLException;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author DASISAQ
 */
public class DaoBase {
    protected Connection con=null;
    protected Logger LOGGER;
    protected final int COD_TIPO_TRANSACCION_LOG_EDICION = 1;
    protected final int COD_TIPO_TRANSACCION_LOG_AGREGAR = 3;
    protected final int COD_TIPO_TRANSACCION_LOG_ELIMINAR = 2;
    

    public DaoBase() {
    }
    protected void cerrarConexion()
    {
        try
        {
            if(con!=null&&!con.isClosed())
            {
                con.close();
            }
        }
        catch(SQLException ex)
        {
            LOGGER.warn("error", ex);
        }
    }
    protected void rollbackConexion()
    {
        try
        {
            if(con!=null && !con.isClosed() && (!con.getAutoCommit()))
            {
                con.rollback();
            }
        }
        catch(SQLException ex)
        {
            LOGGER.warn("error", ex);
        }
    }
}
