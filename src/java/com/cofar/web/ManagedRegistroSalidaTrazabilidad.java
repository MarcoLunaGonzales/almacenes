/*
 *To change this license header, choose License Headers in Project Properties.
 *To change this template file, choose Tools | Templates
 *and open the template in the editor.
 */
package com.cofar.web;

import com.cofar.bean.IngresosAlmacenDetalleEstado;
import com.cofar.bean.LoteProveedorTotales;
import com.cofar.bean.Materiales;
import com.cofar.bean.SalidasAlmacenDetalle;
import com.cofar.bean.SalidasAlmacenDetalleIngreso;
import com.cofar.util.Util;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import org.richfaces.component.html.HtmlDataTable;

/**
 *
 * @author Jaime Chura
 * @version BACO 2.0 date 19-10-2015 time 03:31:08 PM
 */
@SessionScoped
public class ManagedRegistroSalidaTrazabilidad extends ManagedRegistroSalidaAlmacen {

    @Override
    @SuppressWarnings({"CallToPrintStackTrace", "null"})
    public String detallarCantidadSalida_action() {
        try {

            salidasAlmacenDetalleAgregar = new SalidasAlmacenDetalle();
            listLoteProveedor=new ArrayList<LoteProveedorTotales>();
            listIngresosDetalleEstado=new ArrayList<IngresosAlmacenDetalleEstado>();
            listLoteProveedorDestino=new ArrayList<SelectItem>();
            listLoteProveedorSelected=new ArrayList<LoteProveedorTotales>();
            NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
            DecimalFormat form = (DecimalFormat) nf;
            form.applyPattern("#.###");

            // resetear el estilo de las filas
            Iterator i = materialesBuscarList.iterator();
            while (i.hasNext()) {
                Materiales materialesItem = (Materiales) i.next();
                materialesItem.setColorFila("");
            }

            Materiales materialesItem = (Materiales) materialesBuscarDataTable.getRowData();
            materialesItem.setColorFila("#FFCC99");
            salidasAlmacenDetalleAgregar.setMateriales(materialesItem);

            //salidasAlmacenDetalleAgregar.getEstadosMaterial().setCodEstadoMaterial(2);//Aprobado
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " exec [USP_GET_LISTA_LOTES_PROVEEDOR_SALDO] " + salidasAlmacenDetalleAgregar.getMateriales().getCodMaterial()
                    + ", "+salidasAlmacen.getAlmacenes().getCodAlmacen();
                   
            System.out.println("consulta" + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            listLoteProveedor = new ArrayList();
            while (rs.next()) {
                //salidasAlmacenDetalleAgregar.getMateriales().setCodMaterial(rs.getString("COD_MATERIAL"));
                LoteProveedorTotales registro = new LoteProveedorTotales();
                registro.setCodLoteProveedor(rs.getString("LOTE_MATERIAL_PROVEEDOR"));
                registro.setCod_material(rs.getInt("cod_material"));
                registro.setCantidad_total(rs.getFloat("cantidad_total"));
                registro.setCantidad_salida(rs.getFloat("cantidad_total"));
                registro.getUnidadMedida().setNombreUnidadMedida(rs.getString("nombre_unidad_medida"));
                if (registro.getCodLoteProveedor() == null) {
                    registro.setCodLoteProveedor("");
                }
                listLoteProveedor.add(registro);
            }
            /*
             ingresosAlmacenDetalleEstado.getEstadosMaterial().setCodEstadoMaterial(2); // en estado aprobado para la busqueda de cantidad disponible
             //System.out.println("antes de redondear " + this.cantidadDisponibleMaterial(salidasAlmacenDetalleAgregar));

             //salidasAlmacenDetalleAgregar.setCantidadDisponible(Utiles.redondear(this.cantidadDisponibleMaterial(salidasAlmacenDetalleAgregar),3));
             salidasAlmacenDetalleAgregar.setCantidadDisponible(Float.valueOf(form.format(this.cantidadDisponibleMaterial(salidasAlmacenDetalleAgregar))));

             System.out.println("cantidad_disponible : " + salidasAlmacenDetalleAgregar.getCantidadDisponible());
             salidasAlmacenDetalleAgregar.setEquivalencias(this.buscaEquivalencia(salidasAlmacenDetalleAgregar));
             */
            if (rs != null) {
                rs.close();
                st.close();
                con.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<LoteProveedorTotales> listLoteProveedor = new ArrayList();

    public List<LoteProveedorTotales> getListLoteProveedor() {
        return listLoteProveedor;
    }

    public void setListLoteProveedor(List<LoteProveedorTotales> listLoteProveedor) {
        this.listLoteProveedor = listLoteProveedor;
    }

    private List<SelectItem> listLoteProveedorDestino = new ArrayList();
    private List<LoteProveedorTotales> listLoteProveedorSelected = new ArrayList();

    public List<SelectItem> getListLoteProveedorDestino() {
        return listLoteProveedorDestino;
    }

    public void setListLoteProveedorDestino(List<SelectItem> listLoteProveedorDestino) {
        this.listLoteProveedorDestino = listLoteProveedorDestino;
    }

    public List<LoteProveedorTotales> getListLoteProveedorSelected() {
        return listLoteProveedorSelected;
    }

    public void setListLoteProveedorSelected(List<LoteProveedorTotales> listLoteProveedorSelected) {
        this.listLoteProveedorSelected = listLoteProveedorSelected;
    }

    @SuppressWarnings({"CallToPrintStackTrace", "null"})
    public String verEtiquetasLote_action() {
        try {

            NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
            DecimalFormat form = (DecimalFormat) nf;
            form.applyPattern("#.###");

            listLoteProveedorDestino = new ArrayList();
            listLoteProveedorSelected = new ArrayList();
            LoteProveedorTotales loteItem = (LoteProveedorTotales) lotesEncontrados.getRowData();
            if (loteItem.getColorFila().equals("")) {
                loteItem.setColorFila("#FFCC99");
            }
            else {
                loteItem.setColorFila("");
            }
            for (LoteProveedorTotales reg : listLoteProveedor) {
                if (reg.getColorFila().equals("")) {
                    listLoteProveedorDestino.add(new SelectItem(reg.getCodLoteProveedor(), reg.getCodLoteProveedor()));
                } 
                else {
                    listLoteProveedorSelected.add(reg);
                }
            }
            lote_origen = loteItem.getCodLoteProveedor();
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = "select iade.cod_material, iade.lote_material_proveedor"
                    + ", iade.fecha_vencimiento, iade.fecha_reanalisis, iade.cantidad_parcial"
                    + ", iade.cantidad_restante, em.nombre_estado_material"
                    + ", um.nombre_unidad_medida, es.nombre_empaque_secundario_externo"
                    + ", ia.nro_ingreso_almacen, iade.etiqueta"
                    + " from INGRESOS_ALMACEN_DETALLE_ESTADO iade"
                    + " inner join ingresos_almacen_detalle iad on iad.cod_ingreso_almacen=iade.cod_ingreso_almacen and iad.cod_material=iade.cod_material"
                    + " inner join ingresos_almacen ia on ia.cod_ingreso_almacen=iad.cod_ingreso_almacen"
                    + " inner join estados_material em on em.cod_estado_material=iade.cod_estado_material"
                    + " inner join unidades_medida um on iad.cod_unidad_medida=um.cod_unidad_medida"
                    + " inner join EMPAQUES_SECUNDARIO_EXTERNO es on iade.cod_empaque_secundario_externo=es.cod_empaque_secundario_externo"
                    + " where iade.LOTE_MATERIAL_PROVEEDOR='" + loteItem.getCodLoteProveedor() + "'"
                    + " and iade.COD_MATERIAL=" + salidasAlmacenDetalleAgregar.getMateriales().getCodMaterial()
                    + " and iade.CANTIDAD_RESTANTE>0.1"
                    + " and ia.cod_estado_ingreso_almacen=1"
                    + " and ia.cod_almacen="+salidasAlmacen.getAlmacenes().getCodAlmacen();
            System.out.println("consulta" + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            listIngresosDetalleEstado = new ArrayList();
            while (rs.next()) {
                IngresosAlmacenDetalleEstado registro = new IngresosAlmacenDetalleEstado();
                registro.setLoteMaterialProveedor(rs.getString("LOTE_MATERIAL_PROVEEDOR"));
                registro.getMateriales().setCodMaterial(rs.getString("cod_material"));
                registro.setCantidadRestante(rs.getFloat("cantidad_restante"));
                registro.setFechaVencimiento(rs.getTimestamp("fecha_vencimiento"));
                registro.setCantidadParcial(rs.getFloat("cantidad_parcial"));
                registro.setFechaReanalisis(rs.getTimestamp("fecha_reanalisis"));
                registro.getEstadosMaterial().setNombreEstadoMaterial(rs.getString("nombre_estado_material"));
                registro.getMateriales().getUnidadesMedida().setNombreUnidadMedida(rs.getString("nombre_unidad_medida"));
                registro.getEmpaqueSecundarioExterno().setNombreEmpaqueSecundarioExterno(rs.getString("nombre_empaque_secundario_externo"));
                registro.getIngresosAlmacen().setNroIngresoAlmacen(rs.getInt("nro_ingreso_almacen"));
                registro.setEtiqueta(rs.getInt("etiqueta"));
                listIngresosDetalleEstado.add(registro);
            }
            if (rs != null) {
                rs.close();
                st.close();
                con.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected HtmlDataTable lotesEncontrados = new HtmlDataTable();

    public HtmlDataTable getLotesEncontrados() {
        return lotesEncontrados;
    }

    public void setLotesEncontrados(HtmlDataTable lotesEncontrados) {
        this.lotesEncontrados = lotesEncontrados;
    }

    private List<IngresosAlmacenDetalleEstado> listIngresosDetalleEstado = new ArrayList<IngresosAlmacenDetalleEstado>();

    public List<IngresosAlmacenDetalleEstado> getListIngresosDetalleEstado() {
        return listIngresosDetalleEstado;
    }

    public void setListIngresosDetalleEstado(List<IngresosAlmacenDetalleEstado> listIngresosDetalleEstado) {
        this.listIngresosDetalleEstado = listIngresosDetalleEstado;
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public String getAgregarItem_action() {
        try {
            materialesBuscarList.clear();
            buscarMaterial = new Materiales();
            ingresosAlmacenDetalleEstado = new IngresosAlmacenDetalleEstado();
            salidasAlmacenDetalleAgregar = new SalidasAlmacenDetalle();
            listIngresosDetalleEstado = new ArrayList<IngresosAlmacenDetalleEstado>();
            listLoteProveedor = new ArrayList<LoteProveedorTotales>();
            listLoteProveedorDestino = new ArrayList<SelectItem>();
            listLoteProveedorSelected = new ArrayList<LoteProveedorTotales>();
            lote_origen = null;
            lote_destino = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String guardarSalidaAlmacen_action() {
        LOGGER.info("--------------------------------------INICIO REGISTRO TRAZABILIDAD---------------------------");
        this.transaccionExitosa = false;
        Connection con = null;
        try {
            con = Util.openConnection(con);
            con.setAutoCommit(false);
            ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            if (this.verificaTransaccionCerradaAlmacen() == true) {
                this.mostrarMensajeTransaccionFallida("Las transacciones para este mes fueron cerradas ");
                return null;
            }
            if (salidasAlmacen.getAreasEmpresa().getCodAreaEmpresa().equals("0")) {
                this.mostrarMensajeTransaccionFallida("Debe de seleccionar el Area/Departamento destino");
                return null;
            }
            //final ale verificar salida
            if(listLoteProveedorSelected.isEmpty()){
                this.mostrarMensajeTransaccionFallida("Debe Seleccionar al menos un lote de origen.");
                return null; 
            }
            if (lote_destino == null){
                this.mostrarMensajeTransaccionFallida("Debe Seleccionar un Lote de Destino (Lote no válido).");
                return null;
            }
            if (lote_destino.equals("0")) {
                this.mostrarMensajeTransaccionFallida("Debe Seleccionar un Lote de Destino");
                return null;
            }
            Statement stdelete = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            String query = "delete from LOTE_PROVEEDOR_TRAZABILIDAD where cod_personal=" + salidasAlmacen.getPersonal().getCodPersonal();
            LOGGER.debug("consulta delete " + query);
            stdelete.executeUpdate(query);
            for (LoteProveedorTotales reg : listLoteProveedorSelected) {
                query = " INSERT INTO [LOTE_PROVEEDOR_TRAZABILIDAD]"
                        + " ([LOTE_MATERIAL_PROVEEDOR]"
                        + "  ,[COD_PERSONAL]"
                        + "  ,[CANTIDAD_TOTAL]"
                        + "  ,[CANTIDAD_SALIDA]"
                        + ")"
                        + "  VALUES('"
                        +reg.getCodLoteProveedor()+
                        "',"
                        +salidasAlmacen.getPersonal().getCodPersonal()
                        +","+reg.getCantidad_total()
                        +","+reg.getCantidad_salida()
                        +")";
                LOGGER.debug("consulta insert " + query);
                stdelete.executeUpdate(query);
            }
            String consulta = " exec [USP_INSERT_SALIDAS_ALMACEN_X_TRAZABILIDAD] "
                    + salidasAlmacen.getPersonal().getCodPersonal() + ","
                    + "'" + lote_destino + "',"
                    + salidasAlmacenDetalleAgregar.getMateriales().getCodMaterial() + ","
                    + salidasAlmacen.getAlmacenes().getCodAlmacen() + ","
                    + "'" + salidasAlmacen.getObsSalidaAlmacen() + "',"
                    + salidasAlmacen.getAreasEmpresa().getCodAreaEmpresa();
            LOGGER.debug("consulta procedimiento:" + consulta);
            st.executeUpdate(consulta);
            this.mostrarMensajeTransaccionExitosa("Se registro satisfactoriamente la transacción por trazabilidad");
            con.commit();
        }
        catch(SQLException ex){
            LOGGER.warn(ex);
            this.mostrarMensajeTransaccionFallida("Ocurrio un error al momento de registrar la transacción, intente de nuevo");
            this.rollbackConexion(con);
        }
        catch (Exception e) {
            LOGGER.warn(e);
            this.mostrarMensajeTransaccionFallida("Ocurrio un error al momento de registrar la transacción, intente de nuevo");
        } finally {
            this.cerrarConexion(con);
        }
        LOGGER.info("--------------------------------------TERMINO REGISTRO TRAZABILIDAD---------------------------");
        return null;
    }

    private String lote_origen;
    private String lote_destino;

    public String getLote_origen() {
        return lote_origen;
    }

    public void setLote_origen(String lote_origen) {
        this.lote_origen = lote_origen;
    }

    public String getLote_destino() {
        return lote_destino;
    }

    public void setLote_destino(String lote_destino) {
        this.lote_destino = lote_destino;
    }
    @Override
    public String buscarMaterial_action(){
        super.buscarMaterial_action();
        listIngresosDetalleEstado=new ArrayList<IngresosAlmacenDetalleEstado>();
        listLoteProveedorDestino=new ArrayList<SelectItem>();
        listLoteProveedor=new ArrayList<LoteProveedorTotales>();
        listLoteProveedorSelected=new ArrayList<LoteProveedorTotales>();
        return null;
    }
}
