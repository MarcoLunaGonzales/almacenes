/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofar.dao;

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
public class DaoIngresosAlmacenDetalleEstado extends DaoBase{

    public DaoIngresosAlmacenDetalleEstado() {
        this.LOGGER = LogManager.getRootLogger();
    }
    
    public DaoIngresosAlmacenDetalleEstado(Logger  LOGGER) {
        this.LOGGER = LOGGER;
    }
    
    public void verificarLoteMaterialProveedorAnterior(IngresosAlmacenDetalleEstado ingresoEtiqueta)
    {
        int codCapituloMaterialPrima = 2;
        int codCapituloEmpaquePrimario = 3;
        SimpleDateFormat sdfMesAnio = new SimpleDateFormat("MM/yyyy");
        if(ingresoEtiqueta.getLoteMaterialProveedor().trim().length()>0 &&(
                ingresoEtiqueta.getMateriales().getGrupos().getCapitulos().getCodCapitulo() == codCapituloMaterialPrima
                || ingresoEtiqueta.getMateriales().getGrupos().getCapitulos().getCodCapitulo() == codCapituloEmpaquePrimario))
        {
            Connection conexion = null;
            try {
                conexion = Util.openConnection(conexion);
                Statement st =conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                StringBuilder consulta = new StringBuilder("select iade.COD_ESTADO_MATERIAL,iade.FECHA_VENCIMIENTO")
                                                            .append(",iade.FECHA_REANALISIS")
                                                    .append(" from INGRESOS_ALMACEN ia")
                                                            .append(" inner join INGRESOS_ALMACEN_DETALLE_ESTADO iade on iade.COD_INGRESO_ALMACEN=ia.COD_INGRESO_ALMACEN")
                                                    .append(" where ia.COD_ESTADO_INGRESO_ALMACEN =1")
                                                            .append(" and iade.COD_MATERIAL = ").append(ingresoEtiqueta.getMateriales().getCodMaterial())
                                                            .append(" and iade.COD_INGRESO_ALMACEN<>").append(ingresoEtiqueta.getIngresosAlmacen().getCodIngresoAlmacen())
                                                            .append(" and iade.LOTE_MATERIAL_PROVEEDOR = '").append(ingresoEtiqueta.getLoteMaterialProveedor().trim()).append("'");
                                                            
                LOGGER.debug("consulta verificar fecha de vencimiento: "+consulta.toString());
                ResultSet res =st.executeQuery(consulta.toString());
                ingresoEtiqueta.setLoteProveedorEncontradoIngresoAnterior(false);
                if(res.next()){
                    ingresoEtiqueta.setLoteProveedorEncontradoIngresoAnterior(true);
                    ingresoEtiqueta.setFechaVencimiento(res.getTimestamp("FECHA_VENCIMIENTO"));
                    ingresoEtiqueta.setFechaReanalisis(res.getTimestamp("FECHA_REANALISIS"));
                    ingresoEtiqueta.setFechaVencimientoFormatoMMYY(sdfMesAnio.format(res.getTimestamp("FECHA_VENCIMIENTO")));
                    LOGGER.debug("fecha de vencimiento encontrada");
                }
                consulta = new StringBuilder("select distinct em.COD_ESTADO_MATERIAL,em.NOMBRE_ESTADO_MATERIAL")
                                    .append(" from INGRESOS_ALMACEN ia")
                                            .append(" inner join INGRESOS_ALMACEN_DETALLE_ESTADO iade on iade.COD_INGRESO_ALMACEN = ia.COD_INGRESO_ALMACEN")
                                            .append(" inner join ESTADOS_MATERIAL em on em.COD_ESTADO_MATERIAL =iade.COD_ESTADO_MATERIAL")
                                    .append(" where ia.COD_ESTADO_INGRESO_ALMACEN = 1")
                                            .append(" and iade.COD_MATERIAL = ").append(ingresoEtiqueta.getMateriales().getCodMaterial())
                                            .append(" and iade.LOTE_MATERIAL_PROVEEDOR = '").append(ingresoEtiqueta.getLoteMaterialProveedor()).append("'")
                                    .append(" order by em.NOMBRE_ESTADO_MATERIAL");
                LOGGER.debug("consulta obtener posibles estados material "+consulta.toString());
                res = st.executeQuery(consulta.toString());
                ingresoEtiqueta.setEstadosMaterialSelectList(new ArrayList<SelectItem>());
                LOGGER.info("estado "+ingresoEtiqueta.getEstadosMaterial().getCodEstadoMaterial());
                ingresoEtiqueta.getEstadosMaterialSelectList().add(new SelectItem(ingresoEtiqueta.getEstadosMaterial().getCodEstadoMaterial(),ingresoEtiqueta.getEstadosMaterial().getNombreEstadoMaterial()));
                while(res.next())
                {
                    ingresoEtiqueta.getEstadosMaterialSelectList().add(new SelectItem(res.getInt("COD_ESTADO_MATERIAL"),res.getString("NOMBRE_ESTADO_MATERIAL")));
                }

            } catch (SQLException ex) {
                LOGGER.warn(ex);
            }
            finally
            {
                try
                {
                conexion.close();
                }
                catch(SQLException ex)
                {
                    LOGGER.warn(ex);
                }
            }
        }
    }
    
    public List<IngresosAlmacenDetalleEstado> listarPorIngresoAlmacenDetalle(IngresosAlmacenDetalle ingresosAlmacenDetalle){
        List<IngresosAlmacenDetalleEstado> ingresosAlmacenDetalleEstadoList = new ArrayList<>();
        try {
            con = Util.openConnection(con);
            Statement st = con.createStatement();
            StringBuilder consulta = new StringBuilder(" select iade.COD_INGRESO_ALMACEN,iade.ETIQUETA,e.COD_ESTADO_MATERIAL,e.NOMBRE_ESTADO_MATERIAL,ese.COD_EMPAQUE_SECUNDARIO_EXTERNO,ese.NOMBRE_EMPAQUE_SECUNDARIO_EXTERNO, ")
                                                        .append(" iade.CANTIDAD_PARCIAL,iade.CANTIDAD_RESTANTE,iade.FECHA_VENCIMIENTO,iade.LOTE_MATERIAL_PROVEEDOR,iade.LOTE_INTERNO,iade.FECHA_MANUFACTURA,iade.FECHA_REANALISIS,iade.OBSERVACIONES,iade.OBS_CONTROL_CALIDAD,est.cod_estante,est.nombre_estante,iade.fila,iade.columna ")
                                                .append(" from INGRESOS_ALMACEN_DETALLE_ESTADO iade " )
                                                        .append(" inner join ESTADOS_MATERIAL e on e.COD_ESTADO_MATERIAL = iade.COD_ESTADO_MATERIAL " )
                                                        .append(" inner join EMPAQUES_SECUNDARIO_EXTERNO ese on ese.COD_EMPAQUE_SECUNDARIO_EXTERNO = iade.COD_EMPAQUE_SECUNDARIO_EXTERNO" )
                                                        .append(" left outer join estante_ambiente est on est.cod_estante = iade.cod_estante " )
                                                .append(" where iade.COD_INGRESO_ALMACEN = '").append(ingresosAlmacenDetalle.getIngresosAlmacen().getCodIngresoAlmacen()).append("' ")
                                                        .append(" and iade.COD_MATERIAL = '").append(ingresosAlmacenDetalle.getMateriales().getCodMaterial()).append("'")
                                                .append(" order by iade.ETIQUETA");
            LOGGER.info("consulta cargar detalle estado: " + consulta.toString());
            ResultSet rs = st.executeQuery(consulta.toString());
            ingresosAlmacenDetalleEstadoList.clear();
            SimpleDateFormat sdfMesAnio= new SimpleDateFormat("MM/yyyy");
            boolean registrarFechaVencimiento = false;
            while(rs.next()){
                IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstado = new IngresosAlmacenDetalleEstado();
                ingresosAlmacenDetalleEstado.getIngresosAlmacen().setCodIngresoAlmacen(rs.getInt("COD_INGRESO_ALMACEN"));
                ingresosAlmacenDetalleEstado.setEtiqueta(rs.getInt("ETIQUETA"));
                ingresosAlmacenDetalleEstado.getEstadosMaterial().setCodEstadoMaterial(rs.getInt("COD_ESTADO_MATERIAL"));
                ingresosAlmacenDetalleEstado.getEstadosMaterial().setNombreEstadoMaterial(rs.getString("NOMBRE_ESTADO_MATERIAL"));
                ingresosAlmacenDetalleEstado.getEmpaqueSecundarioExterno().setCodEmpaqueSecundarioExterno(rs.getInt("COD_EMPAQUE_SECUNDARIO_EXTERNO"));
                ingresosAlmacenDetalleEstado.getEmpaqueSecundarioExterno().setNombreEmpaqueSecundarioExterno(rs.getString("NOMBRE_EMPAQUE_SECUNDARIO_EXTERNO"));
                ingresosAlmacenDetalleEstado.setCantidadParcial(rs.getFloat("CANTIDAD_PARCIAL"));
                ingresosAlmacenDetalleEstado.setCantidadRestante(rs.getFloat("CANTIDAD_RESTANTE"));
                ingresosAlmacenDetalleEstado.setFechaVencimiento(rs.getDate("FECHA_VENCIMIENTO"));
                registrarFechaVencimiento = (registrarFechaVencimiento || (rs.getDate("FECHA_VENCIMIENTO") != null));
                ingresosAlmacenDetalleEstado.setLoteMaterialProveedor(rs.getString("LOTE_MATERIAL_PROVEEDOR"));
                ingresosAlmacenDetalleEstado.setLoteInterno(rs.getString("LOTE_INTERNO"));
                ingresosAlmacenDetalleEstado.setFechaManufactura(rs.getDate("FECHA_MANUFACTURA"));
                ingresosAlmacenDetalleEstado.setFechaReanalisis(rs.getDate("FECHA_REANALISIS"));
                ingresosAlmacenDetalleEstado.setObservaciones(rs.getString("OBSERVACIONES"));
                ingresosAlmacenDetalleEstado.setObsControlCalidad(rs.getInt("OBS_CONTROL_CALIDAD"));
                ingresosAlmacenDetalleEstado.getEstanteAlmacen().setCodEstante(rs.getInt("cod_estante"));
                ingresosAlmacenDetalleEstado.getEstanteAlmacen().setNombreEstante(rs.getString("nombre_estante"));
                ingresosAlmacenDetalleEstado.setFila(rs.getString("fila"));
                ingresosAlmacenDetalleEstado.setColumna(rs.getString("columna"));
                ingresosAlmacenDetalleEstado.setFechaVencimientoFormatoMMYY(sdfMesAnio.format(rs.getTimestamp("FECHA_VENCIMIENTO")));
                ingresosAlmacenDetalleEstado.setMateriales(ingresosAlmacenDetalle.getMateriales().clone());
                ingresosAlmacenDetalleEstado.getIngresosAlmacen().setCodIngresoAlmacen(ingresosAlmacenDetalle.getIngresosAlmacen().getCodIngresoAlmacen());
                this.verificarLoteMaterialProveedorAnterior(ingresosAlmacenDetalleEstado);
                ingresosAlmacenDetalleEstadoList.add(ingresosAlmacenDetalleEstado);
            }
            ingresosAlmacenDetalle.setAplicaFechaVencimiento(registrarFechaVencimiento);
        } catch (Exception e) {
            
            LOGGER.warn(e);
        }
        return ingresosAlmacenDetalleEstadoList;
    }
    
}
