/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.service;

import com.cofar.service.impl.TiposCompraImpl;
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
public class TiposCompraService implements TiposCompraImpl {
    public List cargarTiposCompra() {
        List tiposIngresosAlmacenList = new ArrayList();
        try {

            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select t.COD_TIPO_COMPRA,t.NOMBRE_TIPO_COMPRA from TIPOS_COMPRA t where t.COD_ESTADO_REGISTRO = 1 ";

            ResultSet rs = null;
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            tiposIngresosAlmacenList.clear();
            rs = st.executeQuery(consulta);
            while (rs.next()) {
                tiposIngresosAlmacenList.add(new SelectItem(rs.getString("COD_TIPO_COMPRA"), rs.getString("NOMBRE_TIPO_COMPRA")));
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
