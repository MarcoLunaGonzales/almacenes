/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofar.dao;

import com.cofar.bean.EstadosMaterial;
import com.cofar.bean.Gestiones;
import com.cofar.util.Util;
import com.cofar.web.ManagedAccesoSistema;
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
public class DaoEstadosMaterial extends DaoBase{

    public DaoEstadosMaterial() {
        this.LOGGER = LogManager.getRootLogger();
    }
    public DaoEstadosMaterial(Logger LOGGER){
        this.LOGGER = LOGGER;
    }
    
    public List<EstadosMaterial> listarExistencias(int codAlmacen,String codMaterial){
        List<EstadosMaterial> estadosMaterialList = new ArrayList<>();
        try {
            con = Util.openConnection(con);
            StringBuilder consulta = new StringBuilder("select em.COD_ESTADO_MATERIAL,em.NOMBRE_ESTADO_MATERIAL,existenciaAlmacen.cantidadRestante,cse.COD_ESTADO_MATERIAL as permitido")
                                            .append(" from ESTADOS_MATERIAL em ")
                                                    .append(" left outer join CONFIGURACION_SALIDA_ESTADO_MATERIAL cse on cse.COD_ESTADO_MATERIAL=em.COD_ESTADO_MATERIAL")
                                                    .append(" and cse.COD_ALMACEN=").append(codAlmacen)
                                                    .append(" left join (")
                                                            .append(" select iade.COD_ESTADO_MATERIAL,sum(iade.CANTIDAD_RESTANTE) as cantidadRestante")
                                                            .append(" from INGRESOS_ALMACEN ia")
                                                            .append(" inner join INGRESOS_ALMACEN_DETALLE_ESTADO iade on iade.COD_INGRESO_ALMACEN=ia.COD_INGRESO_ALMACEN")
                                                            .append(" where ia.COD_ESTADO_INGRESO_ALMACEN=1")
                                                            .append(" and iade.COD_MATERIAL=").append(codMaterial)
                                                            .append(" and iade.CANTIDAD_RESTANTE>0")
                                                            .append(" and ia.COD_ALMACEN=").append(codAlmacen)
                                                            .append(" group by iade.COD_ESTADO_MATERIAL   ")
                                                    .append(" ) as existenciaAlmacen on existenciaAlmacen.COD_ESTADO_MATERIAL=em.COD_ESTADO_MATERIAL")
                                                    .append(" order by em.NOMBRE_ESTADO_MATERIAL");
            LOGGER.info("consulta existencia "+consulta.toString());
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet res=st.executeQuery(consulta.toString());
            while(res.next()){
                EstadosMaterial estadosMaterial=new EstadosMaterial();
                estadosMaterial.setCodEstadoMaterial(res.getInt("COD_ESTADO_MATERIAL"));
                estadosMaterial.setNombreEstadoMaterial(res.getString("NOMBRE_ESTADO_MATERIAL"));
                estadosMaterial.setCantidadExistente(res.getDouble("cantidadRestante"));
                estadosMaterial.setEstadoSalidaPermitido(res.getInt("permitido")>0);
                estadosMaterialList.add(estadosMaterial);
            }
        } catch (SQLException ex) {
            LOGGER.warn(ex.getMessage());
        } catch (Exception ex) {
            LOGGER.warn(ex.getMessage());
        } finally {
            this.cerrarConexion();
        }
        return estadosMaterialList;
    }
    
}
