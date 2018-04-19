/*
 * To change this license header, choose License Headeres in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofar.dao;

import com.cofar.bean.SalidasAlmacenDetalle;
import com.cofar.bean.SalidasAlmacenDetalleIngreso;
import com.cofar.util.Util;
import com.cofar.util.Utiles;
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
public class DaoSalidasAlmacenDetalleIngreso extends DaoBase{

    public DaoSalidasAlmacenDetalleIngreso() {
        this.LOGGER = LogManager.getRootLogger();
    }
    public DaoSalidasAlmacenDetalleIngreso(Logger LOGGER) {
        this.LOGGER = LOGGER;
    }
    
    public List<SalidasAlmacenDetalleIngreso> listarAgregar(SalidasAlmacenDetalle salidasAlmacenDetalle){
        List<SalidasAlmacenDetalleIngreso> salidasDetalleIngresoList = new ArrayList<>();
        try {
            con = Util.openConnection(con);
            StringBuilder consulta = new StringBuilder(" select * ")
                                        .append(" from VISTA_INGRESOS_ALMACEN_DETALLE_ESTADO_PARA_SALIDA viad")
                                        .append(" where viad.COD_ALMACEN =").append(salidasAlmacenDetalle.getSalidasAlmacen().getAlmacenes().getCodAlmacen())
                                                .append(" and viad.COD_MATERIAL = ").append(salidasAlmacenDetalle.getMateriales().getCodMaterial())
                                        .append(" order by viad.ORDEN_SALIDA,viad.ordenSaldo,viad.fecha_vencimiento,viad.etiqueta");
            LOGGER.debug("consulta cargar "+consulta.toString());
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet res=st.executeQuery(consulta.toString());
            salidasAlmacenDetalle.setCantidadDisponible(0f);
            while(res.next()){
                SalidasAlmacenDetalleIngreso salidasAlmacenDetalleIngreso = new SalidasAlmacenDetalleIngreso();
                salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getIngresosAlmacen().setCodIngresoAlmacen(res.getInt("cod_ingreso_almacen"));
                salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getMateriales().setCodMaterial(res.getString("cod_material"));
                salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().setEtiqueta(res.getInt("etiqueta"));
                salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getEstadosMaterial().setCodEstadoMaterial(res.getInt("cod_estado_material"));
                salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getEstadosMaterial().setNombreEstadoMaterial(res.getString("nombre_estado_material"));
                salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getEmpaqueSecundarioExterno().setCodEmpaqueSecundarioExterno(res.getInt("cod_empaque_secundario_externo"));
                salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().setFechaVencimiento(res.getDate("fecha_vencimiento"));
                salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getIngresosAlmacen().setNroIngresoAlmacen(res.getInt("nro_ingreso_almacen"));
                //salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getIngresosAlmacenDetalle().getSecciones().setNombreSeccion(res.getString("nombre_seccion"));
                salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getEmpaqueSecundarioExterno().setNombreEmpaqueSecundarioExterno(res.getString("nombre_empaque_secundario_externo"));
                salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().setLoteMaterialProveedor(res.getString("lote_material_proveedor"));
                salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getIngresosAlmacenDetalle().setCostoUnitario(0);
                salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getEstanteAlmacen().setCodEstante(res.getInt("cod_estante"));
                salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getEstanteAlmacen().setNombreEstante(res.getString("nombre_estante"));
                salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getEstanteAlmacen().getAmbienteAlmacen().setCodAmbiente(res.getInt("COD_AMBIENTE"));
                salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getEstanteAlmacen().getAmbienteAlmacen().setNombreAmbiente(res.getString("NOMBRE_AMBIENTE"));
                salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().setFila(res.getString("fila"));
                salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().setColumna(res.getString("columna"));

                salidasAlmacenDetalleIngreso.setCantidad(res.getFloat("cantidad_r"));
                salidasAlmacenDetalleIngreso.setCantidadDisponible(res.getFloat("cantidad_r"));
                salidasAlmacenDetalleIngreso.setCantidadRestanteValorado(res.getFloat("cantidad_restante_valorada"));
                salidasAlmacenDetalleIngreso.setValoracionCCPorcentual(res.getFloat("valoracion_cc_porcentual"));
                if (Utiles.redondear(salidasAlmacenDetalleIngreso.getCantidadDisponible(), 2) > 0) {
                    salidasAlmacenDetalle.setCantidadDisponible(salidasAlmacenDetalle.getCantidadDisponible() + salidasAlmacenDetalleIngreso.getCantidadDisponible());
                    salidasDetalleIngresoList.add(salidasAlmacenDetalleIngreso);
                }
            }
        } catch (SQLException ex) {
            LOGGER.warn(ex);
        } catch (Exception ex) {
            LOGGER.warn(ex);
        } finally {
            this.cerrarConexion();
        }
        return salidasDetalleIngresoList;
    }
    
    
    
}
