/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.service;

import com.cofar.service.impl.TiposIngresoAlmacenImpl;
import com.cofar.util.Util;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;

/**
 *
 * @author hvaldivia
 */
public class TiposIngresoAlmacenService implements TiposIngresoAlmacenImpl {
    public List cargarTiposIngresoAlmacen() {
        List tiposIngresosAlmacenList = new ArrayList();
        try {
            
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select t.COD_TIPO_INGRESO_ALMACEN,t.NOMBRE_TIPO_INGRESO_ALMACEN " +
                    " from TIPOS_INGRESO_ALMACEN t where t.COD_ESTADO_REGISTRO = 1 " +
                    " and t.cod_tipo_ingreso_almacen<>1 and t.cod_tipo_ingreso_almacen<>3 " +
                    " and t.cod_tipo_ingreso_almacen<>4 and t.cod_tipo_ingreso_almacen<>6 and t.cod_tipo_ingreso_almacen<>7 ";
            
            ResultSet rs = null;
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            tiposIngresosAlmacenList.clear();
            rs = st.executeQuery(consulta);
            while (rs.next()) {
                tiposIngresosAlmacenList.add(new SelectItem(rs.getString("COD_TIPO_INGRESO_ALMACEN"), rs.getString("NOMBRE_TIPO_INGRESO_ALMACEN")));
            }

            if (rs != null) {
                rs.close();
                st.close();
                rs = null;
                st = null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return tiposIngresosAlmacenList;
    }

}
