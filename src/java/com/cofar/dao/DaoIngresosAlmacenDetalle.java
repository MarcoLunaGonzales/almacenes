/*
 * To change this license header, choose License Headeres in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofar.dao;

import com.cofar.bean.IngresosAlmacen;
import com.cofar.bean.IngresosAlmacenDetalle;
import com.cofar.bean.IngresosAlmacenDetalleEstado;
import com.cofar.util.Util;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author DASISAQ
 */
public class DaoIngresosAlmacenDetalle extends DaoBase{

    public DaoIngresosAlmacenDetalle() {
        this.LOGGER = LogManager.getRootLogger();
    }
    
    public DaoIngresosAlmacenDetalle(Logger LOGGER) {
        this.LOGGER = LOGGER;
    }
    
    
    public List<IngresosAlmacenDetalle> listarPorIngresoAlmacen(IngresosAlmacen ingresosAlmacen){
        List<IngresosAlmacenDetalle> ingresosDetalleList = new ArrayList<>();
        DaoIngresosAlmacenDetalleEstado daoIngresosDetalleEstado = new DaoIngresosAlmacenDetalleEstado();
        try {
            con = Util.openConnection(con);
            StringBuilder consulta = new StringBuilder(" SELECT iad.COD_INGRESO_ALMACEN,m.COD_MATERIAL,m.NOMBRE_MATERIAL,s.COD_SECCION,s.NOMBRE_SECCION,iad.NRO_UNIDADES_EMPAQUE ") 
                                    .append(" , iad.CANT_TOTAL_INGRESO,iad.CANT_TOTAL_INGRESO_FISICO,um.COD_UNIDAD_MEDIDA,um.NOMBRE_UNIDAD_MEDIDA,um.ABREVIATURA,iad.PRECIO_TOTAL_MATERIAL " )
                                    .append(" , iad.PRECIO_UNITARIO_MATERIAL,iad.COSTO_UNITARIO,iad.observacion,iad.COSTO_PROMEDIO,iad.PRECIO_NETO " )
                                    .append(" , i_ant.cantidad_anterior , ocd.CANTIDAD_INGRESO_ALMACEN , ocd.CANTIDAD_NETA, ocd.COD_UNIDAD_MEDIDA as  COD_UNIDAD_MEDIDA_OC" )
                                    .append(" , umoc.NOMBRE_UNIDAD_MEDIDA as NOMBRE_UNIDAD_MEDIDA_OC, umoc.ABREVIATURA as ABREVIATURA_OC ,gfv.APLICA_FECHA_VENCIMIENTO")
                                    .append(" ,g.COD_GRUPO,g.NOMBRE_GRUPO,c.COD_CAPITULO,c.NOMBRE_CAPITULO" )
                            .append(" FROM INGRESOS_ALMACEN_DETALLE iad")
                                    .append(" inner join materiales m on m.COD_MATERIAL = iad.COD_MATERIAL " )
                                    .append(" inner join grupos g on g.COD_GRUPO = M.COD_GRUPO")
                                    .append(" inner join capitulos c on c.COD_CAPITULO = g.COD_CAPITULO")
                                    .append(" inner join SECCIONES s on s.COD_SECCION = iad.COD_SECCION " )
                                    .append(" inner join UNIDADES_MEDIDA um on um.COD_UNIDAD_MEDIDA = iad.COD_UNIDAD_MEDIDA ")
                                    .append(" LEFT OUTER JOIN GRUPOS_FECHA_VENCIMIENTO gfv on gfv.COD_GRUPO = m.COD_GRUPO")
                                    .append(" left join INGRESOS_ALMACEN ia on ia.COD_INGRESO_ALMACEN = iad.COD_INGRESO_ALMACEN")
                                    .append(" left join ORDENES_COMPRA oc on oc.COD_ORDEN_COMPRA = ia.COD_ORDEN_COMPRA")
                                    .append(" left join ORDENES_COMPRA_DETALLE ocd on ocd.COD_ORDEN_COMPRA = oc.COD_ORDEN_COMPRA and ocd.COD_MATERIAL = iad.COD_MATERIAL")
                                    .append(" left join UNIDADES_MEDIDA umoc on umoc.COD_UNIDAD_MEDIDA = ocd.COD_UNIDAD_MEDIDA")
                                    .append(" left join (")
                                        .append(" SELECT iad.COD_MATERIAL, SUM(iad.CANT_TOTAL_INGRESO_FISICO) as cantidad_anterior")
                                        .append(" FROM INGRESOS_ALMACEN_DETALLE iad ")
                                        .append(" left join INGRESOS_ALMACEN ia on ia.COD_INGRESO_ALMACEN = iad.COD_INGRESO_ALMACEN")
                                        .append(" WHERE 1 = 1 ")
                                        .append(" AND ia.COD_ORDEN_COMPRA = ").append(ingresosAlmacen.getOrdenesCompra().getCodOrdenCompra() > 0? ingresosAlmacen.getOrdenesCompra().getCodOrdenCompra() : null)
                                        .append(" AND ia.COD_ESTADO_INGRESO_ALMACEN = 1")
                                        .append(" AND ia.COD_INGRESO_ALMACEN != '").append(ingresosAlmacen.getCodIngresoAlmacen()).append("'")
                                        .append(" GROUP BY (iad.COD_MATERIAL) ")
                                    .append(" ) i_ant on iad.COD_MATERIAL = i_ant.COD_MATERIAL" )
                            .append(" WHERE iad.COD_INGRESO_ALMACEN = '").append(ingresosAlmacen.getCodIngresoAlmacen()).append("' ");
            LOGGER.info("consulta " + consulta);
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet res=st.executeQuery(consulta.toString());
            while(res.next()){
                IngresosAlmacenDetalle ingresosAlmacenDetalle = new  IngresosAlmacenDetalle();
                ingresosAlmacenDetalle.getIngresosAlmacen().setCodIngresoAlmacen(res.getInt("COD_INGRESO_ALMACEN"));
                ingresosAlmacenDetalle.getMateriales().setCodMaterial(res.getString("COD_MATERIAL"));
                ingresosAlmacenDetalle.getMateriales().setNombreMaterial(res.getString("NOMBRE_MATERIAL"));
                ingresosAlmacenDetalle.getSecciones().setCodSeccion(res.getInt("COD_SECCION"));
                ingresosAlmacenDetalle.getSecciones().setNombreSeccion(res.getString("NOMBRE_SECCION"));
                ingresosAlmacenDetalle.setNroUnidadesEmpaque(res.getInt("NRO_UNIDADES_EMPAQUE"));
                ingresosAlmacenDetalle.setCantTotalIngreso(res.getFloat("CANT_TOTAL_INGRESO"));
                ingresosAlmacenDetalle.setCantTotalIngresoFisico(res.getFloat("CANT_TOTAL_INGRESO_FISICO"));
                ingresosAlmacenDetalle.getUnidadesMedida().setCodUnidadMedida(res.getInt("COD_UNIDAD_MEDIDA"));
                ingresosAlmacenDetalle.getUnidadesMedida().setNombreUnidadMedida(res.getString("NOMBRE_UNIDAD_MEDIDA"));
                ingresosAlmacenDetalle.getUnidadesMedida().setAbreviatura(res.getString("ABREVIATURA"));
                ingresosAlmacenDetalle.setPrecioTotalMaterial(res.getFloat("PRECIO_TOTAL_MATERIAL"));
                ingresosAlmacenDetalle.setPrecioUnitarioMaterial(res.getFloat("PRECIO_UNITARIO_MATERIAL"));
                ingresosAlmacenDetalle.setCostoUnitario(res.getFloat("COSTO_UNITARIO"));
                ingresosAlmacenDetalle.setObservacion(res.getString("observacion"));
                ingresosAlmacenDetalle.setCostoPromedio(res.getFloat("COSTO_PROMEDIO"));
                ingresosAlmacenDetalle.setPrecioNeto(res.getFloat("PRECIO_NETO"));
                ingresosAlmacenDetalle.getOrdenesCompraDetalle().getUnidadesMedida().setCodUnidadMedida(res.getInt("COD_UNIDAD_MEDIDA_OC"));
                ingresosAlmacenDetalle.getOrdenesCompraDetalle().getUnidadesMedida().setNombreUnidadMedida(res.getString("NOMBRE_UNIDAD_MEDIDA_OC"));
                ingresosAlmacenDetalle.getOrdenesCompraDetalle().getUnidadesMedida().setAbreviatura(res.getString("ABREVIATURA_OC"));
                ingresosAlmacenDetalle.getOrdenesCompraDetalle().setCantidadNeta(res.getFloat("CANTIDAD_NETA"));
                ingresosAlmacenDetalle.getOrdenesCompraDetalle().setCantidadIngresoAlmacen(res.getFloat("CANTIDAD_INGRESO_ALMACEN"));
                ingresosAlmacenDetalle.getOrdenesCompraDetalle().setSumaParcialCantidadesIngresosDetalles(res.getFloat("cantidad_anterior"));
                ingresosAlmacenDetalle.getOrdenesCompraDetalle().setSumaTotalCantidadesIngresosDetalles(res.getFloat("cantidad_anterior")+res.getFloat("CANT_TOTAL_INGRESO_FISICO"));
                ingresosAlmacenDetalle.getMateriales().getGrupos().getGruposFechaVencimiento().setAplicaFechaVencimiento(res.getInt("APLICA_FECHA_VENCIMIENTO") > 0);
                ingresosAlmacenDetalle.getMateriales().getGrupos().setCodGrupo(res.getInt("COD_GRUPO"));
                ingresosAlmacenDetalle.getMateriales().getGrupos().getCapitulos().setCodCapitulo(res.getInt("COD_CAPITULO"));
                ingresosAlmacenDetalle.setIngresosAlmacenDetalleEstadoList(daoIngresosDetalleEstado.listarPorIngresoAlmacenDetalle(ingresosAlmacenDetalle));
                ingresosDetalleList.add(ingresosAlmacenDetalle);
            }
            LOGGER.debug("consulta cargar ");
            
            while(res.next())
            {
            }
        } catch (SQLException ex) {
            LOGGER.warn(ex);
        } catch (Exception ex) {
            LOGGER.warn(ex);
        } finally {
            this.cerrarConexion();
        }
        return ingresosDetalleList;
    }
    
    
}
