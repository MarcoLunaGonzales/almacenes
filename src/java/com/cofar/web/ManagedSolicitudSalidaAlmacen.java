/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofar.web;

import com.cofar.bean.Almacenes;
import com.cofar.bean.AreasEmpresa;
import com.cofar.bean.Equivalencias;
import com.cofar.bean.IngresosAlmacenDetalle;
import com.cofar.bean.IngresosAlmacenDetalleEstado;
import com.cofar.bean.Materiales;
import com.cofar.bean.ProgramaProduccion;
import com.cofar.bean.SalidasAlmacen;
import com.cofar.bean.SalidasAlmacenDetalle;
import com.cofar.bean.SalidasAlmacenDetalleIngreso;
import com.cofar.bean.SolicitudMantenimiento;
import com.cofar.bean.SolicitudesSalida;
import com.cofar.bean.SolicitudesSalidaDetalle;
import com.cofar.bean.TraspasosSolicitud;
import com.cofar.dao.DaoSalidasAlmacenDetalleIngreso;
import com.cofar.util.Util;
import com.cofar.util.Utiles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.apache.logging.log4j.LogManager;
import org.richfaces.component.html.HtmlDataTable;

/**
 *
 * @author hvaldivia
 */
public class ManagedSolicitudSalidaAlmacen extends ManagedBean {

    public static final int COD_TIPO_SALIDA_TRASPASO = 3;
    public static final int COD_ESTADO_TRASPASO_EN_TRANSITO = 1;
    List solicitudesSalidaAlmacenList = new ArrayList();
    SolicitudesSalida solicitudesSalida = new SolicitudesSalida();
    SalidasAlmacen salidasAlmacen = new SalidasAlmacen();
    List areasEmpresaList = new ArrayList();
    List tiposSalidasAlmacenList = new ArrayList();
    List componentesProdList = new ArrayList();
    List presentacionesList = new ArrayList();
    List salidasAlmacenDetalleList = new ArrayList();
    List salidasAlmacenDetalleIngresoList = new ArrayList();
    SalidasAlmacenDetalle salidasAlmacenDetalle = new SalidasAlmacenDetalle();
    HtmlDataTable salidasAlmacenDetalleDataTable = new HtmlDataTable();
    String mensaje = "";
    List capitulosList = new ArrayList();
    List gruposList = new ArrayList();
    SalidasAlmacenDetalle salidasAlmacenDetalleAgregar = new SalidasAlmacenDetalle();
    List materialesList = new ArrayList();
    private Materiales buscarMaterial = new Materiales();
    private List materialesBuscarList = new ArrayList();
    private HtmlDataTable materialesBuscarDataTable = new HtmlDataTable();
    List estadosMaterialList = new ArrayList();
    IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstado = new IngresosAlmacenDetalleEstado();
    SolicitudesSalida solicitudesSalidaBuscar = new SolicitudesSalida();
    List estadosSolicitudSalidaList = new ArrayList();
    List personalSolicitanteList = new ArrayList();
    HtmlDataTable solicitudesSalidaAlmacenDataTable = new HtmlDataTable();
    private List<SelectItem> listaCapitulos = new ArrayList<SelectItem>();
    private List<SelectItem> listaGrupos = new ArrayList<SelectItem>();
    private List<SelectItem> listaItems = new ArrayList<SelectItem>();
    private Materiales materialBuscar = new Materiales();
    SolicitudMantenimiento solicitudMantenimiento = new SolicitudMantenimiento();
    ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
    List solicitudesSalidaDetalleList = new ArrayList();
    List maquinariasList = new ArrayList();
    private boolean administradorAlmacen = false;

    //para salidas por lote Proovedor
    private List<IngresosAlmacenDetalle> ingresosAlmacenDetalleSalidaList = null;

    public String guardarSalidaAlmacenLoteProveedor_action() throws SQLException {
        Connection con = null;
        mensaje = "";
        try {

            ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            if (this.verificaTransaccionCerradaAlmacen() == true) {
                mensaje = "Las transacciones para este mes fueron cerradas ";
                return null;
            }
            for (IngresosAlmacenDetalle bean : ingresosAlmacenDetalleSalidaList) {
                for (IngresosAlmacenDetalleEstado bean1 : ((List<IngresosAlmacenDetalleEstado>) bean.getIngresosAlmacenDetalleEstadoList())) {
                    if (!bean1.getChecked()) {
                        mensaje = "Uno o mas detalles no tiene existencia suficiente";
                        return null;
                    }
                }
            }
            con = Util.openConnection(con);
            con.setAutoCommit(false);
            salidasAlmacen.setCodSalidaAlmacen(this.generaCodSalidaAlmacen());
            salidasAlmacen.setNroSalidaAlmacen(this.generaNroIngresoAlmacen());

            String consulta = " INSERT INTO SALIDAS_ALMACEN ( COD_GESTION, COD_SALIDA_ALMACEN, COD_ORDEN_PESADA,  COD_FORM_SALIDA, COD_PROD, "
                    + "   COD_TIPO_SALIDA_ALMACEN,  COD_AREA_EMPRESA,   NRO_SALIDA_ALMACEN,   FECHA_SALIDA_ALMACEN,   OBS_SALIDA_ALMACEN, "
                    + "  ESTADO_SISTEMA,   COD_ALMACEN,   COD_ORDEN_COMPRA,   COD_PERSONAL,    COD_ESTADO_SALIDA_ALMACEN,   COD_LOTE_PRODUCCION, "
                    + "   COD_ESTADO_SALIDA_COSTO,   cod_prod_ant,   orden_trabajo,   COD_PRESENTACION ,COD_MAQUINA,COD_AREA_INSTALACION) VALUES ( "
                    + "  '" + salidasAlmacen.getGestiones().getCodGestion() + "', '" + salidasAlmacen.getCodSalidaAlmacen() + "', '" + salidasAlmacen.getCodOrdenRepesada() + "',"
                    + "  '" + solicitudesSalida.getCodFormSalida() + "','" + salidasAlmacen.getComponentesProd().getCodCompprod() + "', '" + salidasAlmacen.getTiposSalidasAlmacen().getCodTipoSalidaAlmacen() + "', "
                    + "   '" + salidasAlmacen.getAreasEmpresa().getCodAreaEmpresa() + "', '" + salidasAlmacen.getNroSalidaAlmacen() + "','" + sdf.format(salidasAlmacen.getFechaSalidaAlmacen()) + "',"
                    + "  '" + salidasAlmacen.getObsSalidaAlmacen() + "',  '" + salidasAlmacen.getEstadoSistema() + "','" + salidasAlmacen.getAlmacenes().getCodAlmacen() + "',"
                    + "  '" + salidasAlmacen.getOrdenesCompra().getCodOrdenCompra() + "', '" + salidasAlmacen.getPersonal().getCodPersonal() + "',  '" + salidasAlmacen.getEstadosSalidasAlmacen().getCodEstadoSalidaAlmacen() + "',"
                    + "  '" + salidasAlmacen.getCodLoteProduccion() + "','" + salidasAlmacen.getCodEstadoSalidaCosto() + "',"
                    + "  '" + salidasAlmacen.getCodProdAnt() + "', '" + salidasAlmacen.getOrdenTrabajo() + "', '" + salidasAlmacen.getPresentacionesProducto().getCodPresentacion() + "','" + (salidasAlmacen.getCodLoteProduccion().equals("") ? salidasAlmacen.getMaquinaria().getCodMaquina() : "") + "','" + salidasAlmacen.getAreasInstalaciones().getCodAreaInstalacion() + "'); ";
            System.out.println("consulta " + consulta);
            PreparedStatement pst = con.prepareStatement(consulta);
            if (pst.executeUpdate() > 0) {
                System.out.println("se registro la salida");
            }
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet res = null;
            for (IngresosAlmacenDetalle bean : ingresosAlmacenDetalleSalidaList) {
                int codEstado = 0;
                //sector comnetado y reemplazado por cod J
                          
                for (IngresosAlmacenDetalleEstado bean1 : ((List<IngresosAlmacenDetalleEstado>) bean.getIngresosAlmacenDetalleEstadoList())) {
                    codEstado = bean1.getEstadosMaterial().getCodEstadoMaterial();
                    consulta = "select i.COD_INGRESO_ALMACEN,i.CANTIDAD_RESTANTE,i.ETIQUETA,i.FECHA_VENCIMIENTO"
                            + " from INGRESOS_ALMACEN_DETALLE_ESTADO i where i.COD_INGRESO_ALMACEN='" + bean1.getIngresosAlmacen().getCodIngresoAlmacen() + "' and "
                            + " i.LOTE_MATERIAL_PROVEEDOR='" + bean1.getLoteMaterialProveedor() + "'"
                            + " and i.COD_ESTADO_MATERIAL='" + bean1.getEstadosMaterial().getCodEstadoMaterial() + "'"
                            + " and i.COD_MATERIAL='" + bean.getMateriales().getCodMaterial() + "'"
                            + " and i.CANTIDAD_RESTANTE>0.1"
                            + " order by case when i.CANTIDAD_RESTANTE!=i.CANTIDAD_RESTANTE then 1 else 2 end,"
                            + " i.FECHA_VENCIMIENTO";
                    System.out.println("consulta ingresos detalle quitar " + consulta);
                    res = st.executeQuery(consulta);
                    float cantidadFaltanteSacar = bean1.getCantidadParcial();
                    while (res.next()) {
                        if (cantidadFaltanteSacar >= 0.1) {
                            float cantidadSacar = (res.getFloat("CANTIDAD_RESTANTE") <= cantidadFaltanteSacar ? res.getFloat("CANTIDAD_RESTANTE") : cantidadFaltanteSacar);
                            consulta = "  INSERT INTO SALIDAS_ALMACEN_DETALLE_INGRESO ( COD_SALIDA_ALMACEN,  COD_MATERIAL, COD_INGRESO_ALMACEN,  ETIQUETA, "
                                    + " COSTO_SALIDA,   FECHA_VENCIMIENTO,   CANTIDAD,   COSTO_SALIDA_ACTUALIZADO,   FECHA_ACTUALIZACION,   COSTO_SALIDA_ACTUALIZADO_FINAL )  "
                                    + " VALUES ( '" + salidasAlmacen.getCodSalidaAlmacen() + "',   '" + bean.getMateriales().getCodMaterial() + "',   "
                                    + " '" + bean1.getIngresosAlmacen().getCodIngresoAlmacen() + "', "
                                    + " '" + res.getInt("ETIQUETA") + "', 0, '" + sdf.format(res.getTimestamp("FECHA_VENCIMIENTO")) + "', "
                                    + " '" + cantidadSacar + "', '0', "
                                    + " null,  '0' ) ";
                            System.out.println("consulta detalle ingreso" + consulta);
                            pst = con.prepareStatement(consulta);
                            if (pst.executeUpdate() > 0) {
                                System.out.println("se registro el detalle ingreso");
                            }
                            consulta = "update INGRESOS_ALMACEN_DETALLE_ESTADO set CANTIDAD_RESTANTE-='" + cantidadSacar + "'"
                                    + " where COD_INGRESO_ALMACEN='" + bean1.getIngresosAlmacen().getCodIngresoAlmacen() + "'"
                                    + " and ETIQUETA='" + res.getInt("ETIQUETA") + "'"
                                    + " and COD_MATERIAL='" + bean.getMateriales().getCodMaterial() + "'";
                            System.out.println("consulta update detalle ingreso " + consulta);
                            pst = con.prepareStatement(consulta);
                            if (pst.executeUpdate() > 0) {
                                System.out.println("se actualizo el detalle");
                            }
                            cantidadFaltanteSacar -= cantidadSacar;
                        }
                    }
                }
                consulta = " INSERT INTO SALIDAS_ALMACEN_DETALLE ( COD_SALIDA_ALMACEN, COD_MATERIAL, CANTIDAD_SALIDA_ALMACEN, COD_UNIDAD_MEDIDA, "
                        + "  COD_ESTADO_MATERIAL )  VALUES (   '" + salidasAlmacen.getCodSalidaAlmacen() + "', '" + bean.getMateriales().getCodMaterial() + "',"
                        + "  '" + bean.getCantTotalIngreso() + "',   '" + bean.getUnidadesMedida().getCodUnidadMedida() + "', "
                        + "   '" + codEstado + "' ) ";
                System.out.println("consulta " + consulta);
                pst = con.prepareStatement(consulta);
                if (pst.executeUpdate() > 0) {
                    System.out.println("se registro detalle");
                }
                /*
                //jaime_ corrección de actualización de etiquetas
                SalidasAlmacenDetalle salidasAlmacenDetalleItem=new SalidasAlmacenDetalle();
                salidasAlmacenDetalleItem.setSalidasAlmacen(salidasAlmacen);
                salidasAlmacenDetalleItem.setMateriales(bean.getMateriales());
                etiquetasSalidaAlmacen(salidasAlmacenDetalle);
                Iterator i1 = salidasAlmacenDetalleItem.getSalidasAlmacenDetalleIngresoList().iterator();

                while (i1.hasNext()) {
                    SalidasAlmacenDetalleIngreso salidasAlmacenDetalleIngreso = (SalidasAlmacenDetalleIngreso) i1.next();

                    if (salidasAlmacenDetalleIngreso.getCantidadSacar() > 0) {
                        consulta = "  INSERT INTO SALIDAS_ALMACEN_DETALLE_INGRESO ( COD_SALIDA_ALMACEN,  COD_MATERIAL, COD_INGRESO_ALMACEN,  ETIQUETA, "
                                + " COSTO_SALIDA,   FECHA_VENCIMIENTO,   CANTIDAD,   COSTO_SALIDA_ACTUALIZADO,   FECHA_ACTUALIZACION,   COSTO_SALIDA_ACTUALIZADO_FINAL "
                                + ", COD_ESTANTE, FILA, COLUMNA)  "
                                + " VALUES ( '" + salidasAlmacen.getCodSalidaAlmacen() + "',   '" + salidasAlmacenDetalleItem.getMateriales().getCodMaterial() + "',   "
                                + " '" + salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getIngresosAlmacen().getCodIngresoAlmacen() + "', "
                                + " '" + salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getEtiqueta() + "', '" + salidasAlmacenDetalleIngreso.getCostoSalida() + "', '" + salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getFechaVencimiento() + "', "
                                + " '" + salidasAlmacenDetalleIngreso.getCantidadSacar() + "', '" + salidasAlmacenDetalleIngreso.getCostoSalidaActualizado() + "', "
                                + " '" + sdf.format(salidasAlmacenDetalleIngreso.getFechaActualizacion()) + "',  '" + salidasAlmacenDetalleIngreso.getCostoSalidaActualizadoFinal() + "'"
                                + ",'" + salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getEstanteAlmacen().getCodEstante()
                                + "','" + salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getFila()
                                + "','" + salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getColumna()
                                + "' ) ";
                        System.out.println("consulta LOTE PROV JAIME" + consulta);
                        st.executeUpdate(consulta);
                        consulta = " update ingresos_almacen_detalle_estado set cantidad_restante=cantidad_restante - " + salidasAlmacenDetalleIngreso.getCantidadSacar() + " "
                                + " where cod_material=" + salidasAlmacenDetalleItem.getMateriales().getCodMaterial() + " and etiqueta=" + salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getEtiqueta() + " "
                                + " and cod_ingreso_almacen='" + salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getIngresosAlmacen().getCodIngresoAlmacen() + "' ";
                        System.out.println("J consulta " + consulta);
                        st.executeUpdate(consulta);
                    }
                }
                //fin corrección*/
                
            }
            //EJECUTAR SP LOG
            consulta = " exec [PAA_REGISTRO_SALIDA_ALMACEN_LOG] " + salidasAlmacen.getCodSalidaAlmacen() 
                    + " , "+ usuario.getUsuarioModuloBean().getCodUsuarioGlobal()
                    + " , 3";
            System.out.println("consulta ejecutar: " + consulta); 
            st.executeUpdate(consulta);
            
            consulta = " update SOLICITUDES_SALIDA_DETALLE set CANTIDAD_ENTREGADA=cantidad"
                    + " where COD_FORM_SALIDA='" + solicitudesSalida.getCodFormSalida() + "'";
            System.out.println("consulta " + consulta);
            pst = con.prepareStatement(consulta);
            if (pst.executeUpdate() > 0) {
                System.out.println("se actualizo el detalles");
            }
            consulta = " UPDATE SOLICITUDES_SALIDA SET COD_ESTADO_SOLICITUD_SALIDA_ALMACEN = 2 WHERE COD_FORM_SALIDA = '" + solicitudesSalida.getCodFormSalida() + "' ";
            pst = con.prepareStatement(consulta);
            if (pst.executeUpdate() > 0) {
                System.out.println("se actualizo la solicitud");
            }
            con.commit();
            mensaje = "1";
            st.close();

        } catch (Exception e) {
            e.printStackTrace();
            con.rollback();
            mensaje = "Ocurrio un error al momento de guardar la salida, intente de nuevo";
        } finally {
            con.close();
        }
        
        //costeo de la salida por aprobacion de solicitud de lote de proveedor
        return null;
    }

    private void cargarDetalleAgregarSalidaPorLoteProveedor()
    {
        ingresosAlmacenDetalleSalidaList=new ArrayList<IngresosAlmacenDetalle>();
        Connection con=null;
        try {
           con = Util.openConnection(con);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String consulta = "select ssd.CANTIDAD,ssd.COD_MATERIAL,m.NOMBRE_MATERIAL,ssd.COD_UNIDAD_MEDIDA,um.ABREVIATURA"+
                              " from SOLICITUDES_SALIDA_DETALLE ssd inner join materiales m on m.COD_MATERIAL=ssd.COD_MATERIAL"+
                              " left outer join UNIDADES_MEDIDA um on um.COD_UNIDAD_MEDIDA=ssd.COD_UNIDAD_MEDIDA"+
                              " where ssd.COD_FORM_SALIDA='"+solicitudesSalida.getCodFormSalida()+"'"+
                              " order by m.NOMBRE_MATERIAL";
            ResultSet res = st.executeQuery(consulta);
            Statement stDetalle=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet resDetalle=null;
            while (res.next()) 
            {
                IngresosAlmacenDetalle nuevo=new IngresosAlmacenDetalle();
                nuevo.getMateriales().setCodMaterial(res.getString("COD_MATERIAL"));
                nuevo.getMateriales().setNombreMaterial(res.getString("NOMBRE_MATERIAL"));
                nuevo.getUnidadesMedida().setCodUnidadMedida(res.getInt("COD_UNIDAD_MEDIDA"));
                nuevo.getUnidadesMedida().setAbreviatura(res.getString("ABREVIATURA"));
                nuevo.setCantTotalIngreso(res.getFloat("CANTIDAD"));
                consulta="select ia.COD_INGRESO_ALMACEN,ia.NRO_INGRESO_ALMACEN,ia.FECHA_INGRESO_ALMACEN,s.CANTIDAD_SOLICITUD" +
                        " ,s.COD_ESTADO_MATERIAL,em.NOMBRE_ESTADO_MATERIAL,s.COD_LOTE_PROVEEDOR" +
                        " ,(select sum(iade.CANTIDAD_RESTANTE)"+
                         "   from INGRESOS_ALMACEN_DETALLE_ESTADO iade where iade.COD_INGRESO_ALMACEN=ia.COD_INGRESO_ALMACEN"+
                         "  and iade.COD_ESTADO_MATERIAL=s.COD_ESTADO_MATERIAL and iade.COD_MATERIAL=s.COD_MATERIAL"+
                         "  and iade.LOTE_MATERIAL_PROVEEDOR=s.COD_LOTE_PROVEEDOR) as cantidadRestante" +
                         " from SOLICITUDES_SALIDA_DETALLE_LOTE_PROVEEDOR s inner join INGRESOS_ALMACEN ia "+
                         " on s.COD_INGRESO_ALMACEN=ia.COD_INGRESO_ALMACEN "+
                         " inner join ESTADOS_MATERIAL em on em.COD_ESTADO_MATERIAL=s.COD_ESTADO_MATERIAL"+
                         " where s.COD_MATERIAL='"+res.getInt("COD_MATERIAL")+"' and s.COD_FORM_SALIDA='"+solicitudesSalida.getCodFormSalida()+"'"+
                         " order by ia.FECHA_INGRESO_ALMACEN";
                System.out.println("consulta detalle "+consulta);
                resDetalle=stDetalle.executeQuery(consulta);
                while(resDetalle.next())
                {
                    IngresosAlmacenDetalleEstado detalle=new IngresosAlmacenDetalleEstado();
                    detalle.getIngresosAlmacen().setCodIngresoAlmacen(resDetalle.getInt("COD_INGRESO_ALMACEN"));
                    detalle.getIngresosAlmacen().setNroIngresoAlmacen(resDetalle.getInt("NRO_INGRESO_ALMACEN"));
                    detalle.getIngresosAlmacen().setFechaIngresoAlmacen(resDetalle.getTimestamp("FECHA_INGRESO_ALMACEN"));
                    detalle.setCantidadParcial(resDetalle.getFloat("CANTIDAD_SOLICITUD"));
                    detalle.getEstadosMaterial().setCodEstadoMaterial(resDetalle.getInt("COD_ESTADO_MATERIAL"));
                    detalle.getEstadosMaterial().setNombreEstadoMaterial(resDetalle.getString("NOMBRE_ESTADO_MATERIAL"));
                    detalle.setLoteMaterialProveedor(resDetalle.getString("COD_LOTE_PROVEEDOR"));
                    detalle.setCantidadRestante(resDetalle.getFloat("cantidadRestante"));
                    detalle.setChecked(resDetalle.getFloat("CANTIDAD_SOLICITUD")<=resDetalle.getFloat("cantidadRestante"));
                    nuevo.getIngresosAlmacenDetalleEstadoList().add(detalle);
                    
                }
                ingresosAlmacenDetalleSalidaList.add(nuevo);
            }
            res.close();
            st.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="para buscar y seleccionar lotes de produccion">

    //</editor-fold>
    public List getSolicitudesSalidaAlmacenList() {
        return solicitudesSalidaAlmacenList;
    }

    public void setSolicitudesSalidaAlmacenList(List solicitudesSalidaAlmacenList) {
        this.solicitudesSalidaAlmacenList = solicitudesSalidaAlmacenList;
    }

    public List getAreasEmpresaList() {
        return areasEmpresaList;
    }

    public void setAreasEmpresaList(List areasEmpresaList) {
        this.areasEmpresaList = areasEmpresaList;
    }

    public Materiales getBuscarMaterial() {
        return buscarMaterial;
    }

    public void setBuscarMaterial(Materiales buscarMaterial) {
        this.buscarMaterial = buscarMaterial;
    }

    public List getCapitulosList() {
        return capitulosList;
    }

    public void setCapitulosList(List capitulosList) {
        this.capitulosList = capitulosList;
    }

    public List getComponentesProdList() {
        return componentesProdList;
    }

    public void setComponentesProdList(List componentesProdList) {
        this.componentesProdList = componentesProdList;
    }

    public List getEstadosMaterialList() {
        return estadosMaterialList;
    }

    public void setEstadosMaterialList(List estadosMaterialList) {
        this.estadosMaterialList = estadosMaterialList;
    }

    public List getGruposList() {
        return gruposList;
    }

    public void setGruposList(List gruposList) {
        this.gruposList = gruposList;
    }

    public IngresosAlmacenDetalleEstado getIngresosAlmacenDetalleEstado() {
        return ingresosAlmacenDetalleEstado;
    }

    public void setIngresosAlmacenDetalleEstado(IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstado) {
        this.ingresosAlmacenDetalleEstado = ingresosAlmacenDetalleEstado;
    }

    public HtmlDataTable getMaterialesBuscarDataTable() {
        return materialesBuscarDataTable;
    }

    public void setMaterialesBuscarDataTable(HtmlDataTable materialesBuscarDataTable) {
        this.materialesBuscarDataTable = materialesBuscarDataTable;
    }

    public List getMaterialesBuscarList() {
        return materialesBuscarList;
    }

    public void setMaterialesBuscarList(List materialesBuscarList) {
        this.materialesBuscarList = materialesBuscarList;
    }

    public List getMaterialesList() {
        return materialesList;
    }

    public void setMaterialesList(List materialesList) {
        this.materialesList = materialesList;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List getPresentacionesList() {
        return presentacionesList;
    }

    public void setPresentacionesList(List presentacionesList) {
        this.presentacionesList = presentacionesList;
    }

    public SalidasAlmacen getSalidasAlmacen() {
        return salidasAlmacen;
    }

    public void setSalidasAlmacen(SalidasAlmacen salidasAlmacen) {
        this.salidasAlmacen = salidasAlmacen;
    }

    public SalidasAlmacenDetalle getSalidasAlmacenDetalle() {
        return salidasAlmacenDetalle;
    }

    public void setSalidasAlmacenDetalle(SalidasAlmacenDetalle salidasAlmacenDetalle) {
        this.salidasAlmacenDetalle = salidasAlmacenDetalle;
    }

    public SalidasAlmacenDetalle getSalidasAlmacenDetalleAgregar() {
        return salidasAlmacenDetalleAgregar;
    }

    public void setSalidasAlmacenDetalleAgregar(SalidasAlmacenDetalle salidasAlmacenDetalleAgregar) {
        this.salidasAlmacenDetalleAgregar = salidasAlmacenDetalleAgregar;
    }

    public HtmlDataTable getSalidasAlmacenDetalleDataTable() {
        return salidasAlmacenDetalleDataTable;
    }

    public void setSalidasAlmacenDetalleDataTable(HtmlDataTable salidasAlmacenDetalleDataTable) {
        this.salidasAlmacenDetalleDataTable = salidasAlmacenDetalleDataTable;
    }

    public List getSalidasAlmacenDetalleIngresoList() {
        return salidasAlmacenDetalleIngresoList;
    }

    public void setSalidasAlmacenDetalleIngresoList(List salidasAlmacenDetalleIngresoList) {
        this.salidasAlmacenDetalleIngresoList = salidasAlmacenDetalleIngresoList;
    }

    public List getSalidasAlmacenDetalleList() {
        return salidasAlmacenDetalleList;
    }

    public void setSalidasAlmacenDetalleList(List salidasAlmacenDetalleList) {
        this.salidasAlmacenDetalleList = salidasAlmacenDetalleList;
    }

    public SolicitudesSalida getSolicitudesSalida() {
        return solicitudesSalida;
    }

    public void setSolicitudesSalida(SolicitudesSalida solicitudesSalida) {
        this.solicitudesSalida = solicitudesSalida;
    }

    public List getTiposSalidasAlmacenList() {
        return tiposSalidasAlmacenList;
    }

    public void setTiposSalidasAlmacenList(List tiposSalidasAlmacenList) {
        this.tiposSalidasAlmacenList = tiposSalidasAlmacenList;
    }

    public SolicitudesSalida getSolicitudesSalidaBuscar() {
        return solicitudesSalidaBuscar;
    }

    public void setSolicitudesSalidaBuscar(SolicitudesSalida solicitudesSalidaBuscar) {
        this.solicitudesSalidaBuscar = solicitudesSalidaBuscar;
    }

    public HtmlDataTable getSolicitudesSalidaAlmacenDataTable() {
        return solicitudesSalidaAlmacenDataTable;
    }

    public void setSolicitudesSalidaAlmacenDataTable(HtmlDataTable solicitudesSalidaAlmacenDataTable) {
        this.solicitudesSalidaAlmacenDataTable = solicitudesSalidaAlmacenDataTable;
    }

    public List<SelectItem> getListaCapitulos() {
        return listaCapitulos;
    }

    public void setListaCapitulos(List<SelectItem> listaCapitulos) {
        this.listaCapitulos = listaCapitulos;
    }

    public List<SelectItem> getListaGrupos() {
        return listaGrupos;
    }

    public void setListaGrupos(List<SelectItem> listaGrupos) {
        this.listaGrupos = listaGrupos;
    }

    public List<SelectItem> getListaItems() {
        return listaItems;
    }

    public void setListaItems(List<SelectItem> listaItems) {
        this.listaItems = listaItems;
    }

    public Materiales getMaterialBuscar() {
        return materialBuscar;
    }

    public void setMaterialBuscar(Materiales materialBuscar) {
        this.materialBuscar = materialBuscar;
    }

    public SolicitudMantenimiento getSolicitudMantenimiento() {
        return solicitudMantenimiento;
    }

    public void setSolicitudMantenimiento(SolicitudMantenimiento solicitudMantenimiento) {
        this.solicitudMantenimiento = solicitudMantenimiento;
    }

    public List getSolicitudesSalidaDetalleList() {
        return solicitudesSalidaDetalleList;
    }

    public void setSolicitudesSalidaDetalleList(List solicitudesSalidaDetalleList) {
        this.solicitudesSalidaDetalleList = solicitudesSalidaDetalleList;
    }

    public ManagedAccesoSistema getUsuario() {
        return usuario;
    }

    public void setUsuario(ManagedAccesoSistema usuario) {
        this.usuario = usuario;
    }

    /**
     * Creates a new instance of ManagedSolicitudesSalidaAlmacen
     */
    public ManagedSolicitudSalidaAlmacen() {
        this.LOGGER = LogManager.getRootLogger();
    }

    private void cargarPermisoPersonal() {
        administradorAlmacen = false;
        try {
            Connection con = null;
            con = Util.openConnection(con);
            ManagedAccesoSistema managed = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            StringBuilder consulta = new StringBuilder("select a.ADMINISTRADOR_ALMACEN");
            consulta.append(" from ALMACEN_HABILITADO_USUARIO a ");
            consulta.append(" where a.COD_ALMACEN=").append(managed.getAlmacenesGlobal().getCodAlmacen());
            consulta.append(" and a.COD_PERSONAL=").append(managed.getUsuarioModuloBean().getCodUsuarioGlobal());
            System.out.println("consulta verificar administrador sistema " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet res = st.executeQuery(consulta.toString());
            while (res.next()) {
                administradorAlmacen = (res.getInt("ADMINISTRADOR_ALMACEN") > 0);
            }
            st.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public String getCargarContenidoSolicitudesSalidaAlmacen() {
        this.cargarPermisoPersonal();
        //inicio ale buscar
        this.cargarBuscarMaterial();
        //final ael buscar
        solicitudesSalidaBuscar.setFechaSolicitud(null);
        solicitudesSalidaAlmacenList = this.cargarSolicitudesSalidaAlmacen();

        personalSolicitanteList = this.cargarPersonal();
        areasEmpresaList = this.cargarAreasEmpresa();
        tiposSalidasAlmacenList = this.cargarTiposSalidasAlmacen();
        estadosSolicitudSalidaList = this.cargarEstadosSolicitudSalidaAlmacen();
        return null;
    }

    public List getEstadosSolicitudSalidaList() {
        return estadosSolicitudSalidaList;
    }

    public void setEstadosSolicitudSalidaList(List estadosSolicitudSalidaList) {
        this.estadosSolicitudSalidaList = estadosSolicitudSalidaList;
    }

    public List getPersonalSolicitanteList() {
        return personalSolicitanteList;
    }

    public void setPersonalSolicitanteList(List personalSolicitanteList) {
        this.personalSolicitanteList = personalSolicitanteList;
    }

    public List cargarSolicitudesSalidaAlmacen() {
        List solicitudesSalidaAlmacenList = new ArrayList();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            Connection con = null;
            con = Util.openConnection(con);
            ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            String consulta = " select * from (select ROW_NUMBER() OVER (ORDER BY s.COD_FORM_SALIDA desc) as 'FILAS', s.COD_FORM_SALIDA,s.orden_trabajo ,s.FECHA_SOLICITUD,ae.COD_AREA_EMPRESA,ae.NOMBRE_AREA_EMPRESA "
                                    + " ,t.COD_TIPO_SALIDA_ALMACEN,t.NOMBRE_TIPO_SALIDA_ALMACEN,p.NOMBRE_PILA,p.AP_PATERNO_PERSONAL,p.AP_MATERNO_PERSONAL,s.COD_LOTE_PRODUCCION, "
                                    + " e.COD_ESTADO_SOLICITUD_SALIDA_ALMACEN,e.NOMBRE_ESTADO_SOLICITUD_SALIDA_ALMACEN,s.OBS_SOLICITUD,s.cod_compprod,s.cod_presentacion,cp.nombre_prod_semiterminado,prp.NOMBRE_PRODUCTO_PRESENTACION,isnull(m.cod_maquina,'') cod_maquina,isnull(m.nombre_maquina,'') nombre_maquina,isnull(ai.cod_area_instalacion,'') cod_area_instalacion,isnull(ai.nombre_area_instalacion,'') nombre_area_instalacion "
                                    + " ,s.SOLICITUD_POR_LOTE_PROVEEDOR, tss.NOMBRE_TIPO_SOLICITUD_SALIDA,tss.cod_tipo_solicitud_salida"
                                    + " ,isnull(ad.NOMBRE_ALMACEN,'') as nombreAlmacenDestino,isnull(s.COD_ALMACEN_DESTINO,0) as COD_ALMACEN_DESTINO"
                            + " from SOLICITUDES_SALIDA s "
                                    + " inner join GESTIONES gest on gest.COD_GESTION = s.COD_GESTION "
                                    + " inner join TIPOS_SALIDAS_ALMACEN t on t.COD_TIPO_SALIDA_ALMACEN = s.COD_TIPO_SALIDA_ALMACEN "
                                    + " inner join ESTADOS_SOLICITUD_SALIDAS_ALMACEN e on e.COD_ESTADO_SOLICITUD_SALIDA_ALMACEN = s.COD_ESTADO_SOLICITUD_SALIDA_ALMACEN "
                                    + " inner join PERSONAL p on p.COD_PERSONAL = s.SOLICITANTE "
                                    + " inner join AREAS_EMPRESA ae on ae.COD_AREA_EMPRESA = s.AREA_DESTINO_SALIDA "
                                    + " left outer join TIPOS_SOLICITUD_SALIDA_ALMACEN tss on tss.cod_tipo_solicitud_salida=s.cod_tipo_solicitud_salida"
                                    + " left outer join INGRESOS_ALMACEN i on i.COD_INGRESO_ALMACEN = s.COD_INGRESO_ALMACEN"
                                    + " left outer join componentes_prod cp on cp.cod_compprod = s.cod_compprod"
                                    + " left outer join presentaciones_producto prp on prp.cod_presentacion = s.cod_presentacion"
                                    + " left outer join maquinarias m on m.cod_maquina = s.cod_maquina left outer join areas_instalaciones ai on ai.cod_area_instalacion = s.cod_area_instalacion "
                                    + " left outer join almacenes ad on ad.COD_ALMACEN = s.COD_ALMACEN_DESTINO"
                            + " where s.cod_almacen = '" + usuario.getAlmacenesGlobal().getCodAlmacen() + "' ";
            if (solicitudesSalidaBuscar.getCodFormSalida() > 0) {
                consulta = consulta + " and cod_form_salida='" + solicitudesSalidaBuscar.getCodFormSalida() + "'  ";
            }
            if (!solicitudesSalidaBuscar.getOrdenTrabajo().equals("")) {
                consulta = consulta + " and s.orden_trabajo='" + solicitudesSalidaBuscar.getOrdenTrabajo() + "'  ";
            }
            if (solicitudesSalidaBuscar.getFechaSolicitud() != null) {
                consulta = consulta + " and s.fecha_solicitud='" + sdf.format(solicitudesSalidaBuscar.getFechaSolicitud()) + "'  ";
            }
            if (solicitudesSalidaBuscar.getTiposSalidasAlmacen().getCodTipoSalidaAlmacen() > 0) {
                consulta = consulta + " and s.cod_tipo_salida_almacen='" + solicitudesSalidaBuscar.getTiposSalidasAlmacen().getCodTipoSalidaAlmacen() + "'  ";
            }
            if (!solicitudesSalidaBuscar.getSolicitante().getCodPersonal().equals("") && !solicitudesSalidaBuscar.getSolicitante().getCodPersonal().equals("0")) {
                consulta = consulta + " and s.solicitante='" + solicitudesSalidaBuscar.getSolicitante().getCodPersonal() + "'  ";
            }
            if (solicitudesSalidaBuscar.getEstadosSolicitudSalidasAlmacen().getCodEstadoSolicitudSalidaAlmacen() > 0) {
                consulta = consulta + " and s.cod_estado_solicitud_salida_almacen='" + solicitudesSalidaBuscar.getEstadosSolicitudSalidasAlmacen().getCodEstadoSolicitudSalidaAlmacen() + "'  ";
            }
            if(!solicitudesSalidaBuscar.getAreaDestinoSalida().getCodAreaEmpresa().equals("0") && !solicitudesSalidaBuscar.getAreaDestinoSalida().getCodAreaEmpresa().equals("")){
                consulta = consulta + " and s.AREA_DESTINO_SALIDA = '"+solicitudesSalidaBuscar.getAreaDestinoSalida().getCodAreaEmpresa() +"' ";
            }
            //inicio ale buscar
            if (materialBuscar.getGrupos().getCapitulos().getCodCapitulo() != -1) {
                if (materialBuscar.getGrupos().getCodGrupo() != -1) {
                    if (!materialBuscar.getCodMaterial().equals("-1")) {
                        consulta += " and s.COD_FORM_SALIDA in (select  ssd.COD_FORM_SALIDA from SOLICITUDES_SALIDA_DETALLE ssd where ssd.COD_MATERIAL in('" + materialBuscar.getCodMaterial() + "')) ";
                    } else {
                        consulta += " and s.COD_FORM_SALIDA in (select  ssd.COD_FORM_SALIDA from SOLICITUDES_SALIDA_DETALLE ssd where ssd.COD_MATERIAL in(select m1.COD_MATERIAL from MATERIALES m1 where m1.COD_GRUPO in('" + materialBuscar.getGrupos().getCodGrupo() + "')))";
                    }
                } else {
                    consulta += " and s.COD_FORM_SALIDA in (select  ssd.COD_FORM_SALIDA from SOLICITUDES_SALIDA_DETALLE ssd where ssd.COD_MATERIAL in(select m1.COD_MATERIAL from MATERIALES m1 where m1.COD_GRUPO in(select g.COD_GRUPO from GRUPOS g where g.COD_CAPITULO IN('" + materialBuscar.getGrupos().getCapitulos().getCodCapitulo() + "'))))";
                }
            }

                    //final ale buscar
            consulta = consulta + " ) as listado_solicitudes_salida_almacen "
                    + " where filas between " + begin + " and " + end + "  ";
            System.out.println("consulta " + consulta);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(consulta);
            solicitudesSalidaAlmacenList.clear();
            while (rs.next()) {
                SolicitudesSalida solicitudesSalida = new SolicitudesSalida();
                solicitudesSalida.getAlmacenesDestino().setCodAlmacen(rs.getInt("COD_ALMACEN_DESTINO"));
                solicitudesSalida.getAlmacenesDestino().setNombreAlmacen(rs.getString("nombreAlmacenDestino"));
                solicitudesSalida.setSolicitudPorLoteProveedor(rs.getInt("SOLICITUD_POR_LOTE_PROVEEDOR") > 0);
                solicitudesSalida.setCodFormSalida(rs.getInt("COD_FORM_SALIDA"));
                solicitudesSalida.setOrdenTrabajo(rs.getString("orden_trabajo"));
                solicitudesSalida.setFechaSolicitud(rs.getDate("FECHA_SOLICITUD"));
                solicitudesSalida.getAreaDestinoSalida().setCodAreaEmpresa(rs.getString("COD_AREA_EMPRESA"));
                solicitudesSalida.getAreaDestinoSalida().setNombreAreaEmpresa(rs.getString("NOMBRE_AREA_EMPRESA"));
                solicitudesSalida.getTiposSalidasAlmacen().setCodTipoSalidaAlmacen(rs.getInt("COD_TIPO_SALIDA_ALMACEN"));
                solicitudesSalida.getTiposSalidasAlmacen().setNombreTipoSalidaAlmacen(rs.getString("NOMBRE_TIPO_SALIDA_ALMACEN"));
                solicitudesSalida.getSolicitante().setNombrePersonal(rs.getString("NOMBRE_PILA"));
                solicitudesSalida.getSolicitante().setApPaternoPersonal(rs.getString("AP_PATERNO_PERSONAL"));
                solicitudesSalida.getSolicitante().setApMaternoPersonal(rs.getString("AP_MATERNO_PERSONAL"));
                solicitudesSalida.setCodLoteProduccion(rs.getString("COD_LOTE_PRODUCCION"));
                solicitudesSalida.getEstadosSolicitudSalidasAlmacen().setCodEstadoSolicitudSalidaAlmacen(rs.getInt("COD_ESTADO_SOLICITUD_SALIDA_ALMACEN"));
                solicitudesSalida.getEstadosSolicitudSalidasAlmacen().setNombreEstadoSolicitudSalidaAlmacen(rs.getString("NOMBRE_ESTADO_SOLICITUD_SALIDA_ALMACEN"));
                solicitudesSalida.setObsSolicitud(rs.getString("OBS_SOLICITUD"));
                solicitudesSalida.getComponentesProd().setCodCompprod(rs.getString("cod_compprod"));
                solicitudesSalida.getPresentacionesProducto().setCodPresentacion(rs.getString("cod_presentacion"));
                solicitudesSalida.getComponentesProd().setNombreProdSemiterminado(rs.getString("nombre_prod_semiterminado"));
                solicitudesSalida.getPresentacionesProducto().setNombreProductoPresentacion(rs.getString("NOMBRE_PRODUCTO_PRESENTACION"));
                solicitudesSalida.getMaquinaria().setCodMaquina(rs.getString("cod_maquina"));
                solicitudesSalida.getMaquinaria().setNombreMaquina(rs.getString("nombre_maquina"));
                solicitudesSalida.getAreasInstalaciones().setCodAreaInstalacion(rs.getString("cod_area_instalacion"));
                solicitudesSalida.getAreasInstalaciones().setNombreAreaInstalacion(rs.getString("nombre_area_instalacion"));
                solicitudesSalida.getTipoSolicitud().setCodTipoSolicitudSalida(rs.getInt("cod_tipo_solicitud_salida"));
                solicitudesSalida.getTipoSolicitud().setNombreTipoSolicitudSalida(rs.getString("nombre_tipo_solicitud_salida"));
                
                solicitudesSalidaAlmacenList.add(solicitudesSalida);
            }
            cantidadfilas = solicitudesSalidaAlmacenList.size();
            rs.close();
            st.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return solicitudesSalidaAlmacenList;
    }
    
    public String generarSalidaAlmacen_action() {
        try {
            mensaje = "";

            Iterator i = solicitudesSalidaAlmacenList.iterator();
            while (i.hasNext()) {
                solicitudesSalida = (SolicitudesSalida) i.next();
                if (solicitudesSalida.getChecked().booleanValue() == true) {
                    break;
                }
            }
            if (solicitudesSalida.getEstadosSolicitudSalidasAlmacen().getCodEstadoSolicitudSalidaAlmacen() == 4
                    || solicitudesSalida.getEstadosSolicitudSalidasAlmacen().getCodEstadoSolicitudSalidaAlmacen() == 5) {
                mensaje = "no se puede generar la salida por que la solicitud se encuentra en estado " + solicitudesSalida.getEstadosSolicitudSalidasAlmacen().getNombreEstadoSolicitudSalidaAlmacen();
                System.out.println(mensaje);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getCargarContenidoSalidaAlmacen() {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String nroLote_change() {
        try {
            mensaje = "";
            //if(salidasAlmacen.getCodLoteProduccion().length()==1){
            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement();
            String consulta = " select a.COD_AREA_EMPRESA,a.NOMBRE_AREA_EMPRESA,count(*) "
                    + " from FORMAS_FARMACEUTICAS_LOTE f  "
                    + " inner join componentes_prod cp on cp.COD_FORMA = f.COD_FORMA "
                    + " inner join AREAS_EMPRESA a on a.COD_AREA_EMPRESA = cp.COD_AREA_EMPRESA "
                    + " where f.COD_LOTE_FORMA = '" + (salidasAlmacen.getCodLoteProduccion().length() > 0 ? salidasAlmacen.getCodLoteProduccion().substring(0, 1) : "") + "' "
                    + " group by a.cod_area_empresa, a.nombre_area_empresa order by count(*) desc ";
            System.out.println("consulta lote forma farmaceutica area empresa " + consulta);
            ResultSet rs = st.executeQuery(consulta);
            if (rs.next()) {
                salidasAlmacen.getAreasEmpresa().setCodAreaEmpresa(rs.getString("COD_AREA_EMPRESA"));
                salidasAlmacen.getAreasEmpresa().setNombreAreaEmpresa(rs.getString("NOMBRE_AREA_EMPRESA"));
                mensaje = "el codigo de lote pertenece al area de " + salidasAlmacen.getAreasEmpresa().getNombreAreaEmpresa();
            }
            rs.close();
            st.close();
            con.close();
            //}
            this.cargarComponentesProd(salidasAlmacen.getAreasEmpresa());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String producto_changed() {
        try {

            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select p.cod_presentacion,p.NOMBRE_PRODUCTO_PRESENTACION  "
                    + " from PRESENTACIONES_PRODUCTO p,COMPONENTES_PRESPROD c  "
                    + " where p.COD_PRESENTACION=c.COD_PRESENTACION  "
                    + " and c.COD_COMPPROD='" + salidasAlmacen.getComponentesProd().getCodCompprod() + "' ";
            System.out.println("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            presentacionesList.clear();
            presentacionesList.add(new SelectItem("0", "-NINGUNO-"));
            while (rs.next()) {
                presentacionesList.add(new SelectItem(rs.getString("cod_presentacion"), rs.getString("NOMBRE_PRODUCTO_PRESENTACION")));
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

    public String detallarMateriales_action() {
        try {

            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " SELECT FMDMP.COD_MATERIAL,M.NOMBRE_MATERIAL,FMDMP.CANTIDAD,FMDMP.COD_UNIDAD_MEDIDA,U.ABREVIATURA  "
                    + " FROM FORMULA_MAESTRA FM "
                    + " INNER JOIN FORMULA_MAESTRA_DETALLE_MP FMDMP ON FM.COD_FORMULA_MAESTRA = FMDMP.COD_FORMULA_MAESTRA "
                    + " INNER JOIN UNIDADES_MEDIDA U ON U.COD_UNIDAD_MEDIDA = FMDMP.COD_UNIDAD_MEDIDA "
                    + " INNER JOIN MATERIALES M ON M.COD_MATERIAL = FMDMP.COD_MATERIAL "
                    + " WHERE FM.COD_COMPPROD = '" + salidasAlmacen.getComponentesProd().getCodCompprod() + "' ";
            /*
             " UNION ALL " +
             " select  fmdep.COD_MATERIAL,m.NOMBRE_MATERIAL,fmdep.CANTIDAD,fmdep.COD_UNIDAD_MEDIDA,u.ABREVIATURA " +
             " from FORMULA_MAESTRA fm " +
             " inner join FORMULA_MAESTRA_DETALLE_EP fmdep on fm.COD_FORMULA_MAESTRA = fmdep.COD_FORMULA_MAESTRA " +
             " inner join UNIDADES_MEDIDA u on u.COD_UNIDAD_MEDIDA = fmdep.COD_UNIDAD_MEDIDA " +
             " inner join MATERIALES m on m.COD_MATERIAL = fmdep.COD_MATERIAL " +
             " where fm.COD_COMPPROD = '"+salidasAlmacen.getComponentesProd().getCodCompprod()+"'" ;
             */

            System.out.println("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);

            salidasAlmacenDetalleList.clear();

            while (rs.next()) {
                SalidasAlmacenDetalle salidasAlmacenDetalleItem = new SalidasAlmacenDetalle();
                salidasAlmacenDetalleItem.getMateriales().setCodMaterial(rs.getString("COD_MATERIAL"));
                salidasAlmacenDetalleItem.getMateriales().setNombreMaterial(rs.getString("NOMBRE_MATERIAL"));
                salidasAlmacenDetalleItem.setCantidadSalidaAlmacen(rs.getFloat("CANTIDAD"));
                salidasAlmacenDetalleItem.getUnidadesMedida().setCodUnidadMedida(rs.getInt("COD_UNIDAD_MEDIDA"));
                salidasAlmacenDetalleItem.getUnidadesMedida().setAbreviatura(rs.getString("ABREVIATURA"));
                //salidasAlmacenDetalleItem.setCantidadDisponible(this.cantidadDisponibleMaterial(salidasAlmacenDetalleItem));
                this.etiquetasSalidaAlmacen(salidasAlmacenDetalleItem);
                salidasAlmacenDetalleItem.setEquivalencias(this.buscaEquivalencia(salidasAlmacenDetalleItem));
                salidasAlmacenDetalleItem.getEstadosMaterial().setCodEstadoMaterial(2); //estado aprobado
                this.detalleEtiquetasSalidaAlmacen(salidasAlmacenDetalleItem);
                if (salidasAlmacenDetalleItem.getCantidadSalidaAlmacen() > salidasAlmacenDetalleItem.getCantidadDisponible()) {
                    salidasAlmacenDetalleItem.setColorFila("#FF0000");
                } else {
                    salidasAlmacenDetalleItem.setColorFila("#00FF00");
                }

                salidasAlmacenDetalleList.add(salidasAlmacenDetalleItem);
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

    public Equivalencias buscaEquivalencia(SalidasAlmacenDetalle salidasAlmacenDetalle) {
        Equivalencias equivalencias = new Equivalencias();
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select m.COD_MATERIAL,m.NOMBRE_MATERIAL,u.COD_UNIDAD_MEDIDA,u.NOMBRE_UNIDAD_MEDIDA NOMBRE_UNIDAD_MEDIDA,u.ABREVIATURA,u1.COD_UNIDAD_MEDIDA COD_UNIDAD_MEDIDA1,u1.NOMBRE_UNIDAD_MEDIDA NOMBRE_UNIDAD_MEDIDA1,u1.ABREVIATURA ABREVIATURA1,e.VALOR_EQUIVALENCIA "
                    + " from materiales m  "
                    + " inner join UNIDADES_MEDIDA u on m.COD_UNIDAD_MEDIDA = u.COD_UNIDAD_MEDIDA "
                    + " inner join TIPOS_MEDIDA ti on ti.cod_tipo_medida  = u.COD_TIPO_MEDIDA "
                    + " inner join UNIDADES_MEDIDA u1 on u1.COD_TIPO_MEDIDA = u.COD_TIPO_MEDIDA "
                    + " left outer join EQUIVALENCIAS e on (e.COD_UNIDAD_MEDIDA = u.COD_UNIDAD_MEDIDA and e.COD_UNIDAD_MEDIDA2 = u1.COD_UNIDAD_MEDIDA) or "
                    + " (e.COD_UNIDAD_MEDIDA2 = u.COD_UNIDAD_MEDIDA and e.COD_UNIDAD_MEDIDA = u1.COD_UNIDAD_MEDIDA) where u1.UNIDAD_CLAVE=1 "
                    + " and m.COD_MATERIAL = '" + salidasAlmacenDetalle.getMateriales().getCodMaterial() + "' ";

            System.out.println("consulta" + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            if (rs.next()) {
                equivalencias.setValorEquivalencia(rs.getFloat("VALOR_EQUIVALENCIA"));
                equivalencias.getUnidadesMedida1().setCodUnidadMedida(rs.getInt("COD_UNIDAD_MEDIDA"));
                equivalencias.getUnidadesMedida1().setNombreUnidadMedida(rs.getString("NOMBRE_UNIDAD_MEDIDA"));
                equivalencias.getUnidadesMedida1().setAbreviatura(rs.getString("ABREVIATURA"));
                equivalencias.getUnidadesMedida2().setCodUnidadMedida(rs.getInt("COD_UNIDAD_MEDIDA1"));
                equivalencias.getUnidadesMedida2().setNombreUnidadMedida(rs.getString("NOMBRE_UNIDAD_MEDIDA1"));
                equivalencias.getUnidadesMedida2().setAbreviatura(rs.getString("ABREVIATURA1"));

                if (equivalencias.getUnidadesMedida1().getCodUnidadMedida() == equivalencias.getUnidadesMedida2().getCodUnidadMedida()) {
                    equivalencias.setValorEquivalencia(1);

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return equivalencias;
    }

    public void etiquetasSalidaAlmacen(SalidasAlmacenDetalle salidasAlmacenDetalle) {
        salidasAlmacenDetalle.setSalidasAlmacen(salidasAlmacen);
        
        DaoSalidasAlmacenDetalleIngreso daoEtiquetas = new DaoSalidasAlmacenDetalleIngreso(LOGGER);
        salidasAlmacenDetalle.setSalidasAlmacenDetalleIngresoList(daoEtiquetas.listarAgregar(salidasAlmacenDetalle));
    }

    public void etiquetasSalidaAlmacenPredeterminado(SalidasAlmacenDetalle salidasAlmacenDetalle, SolicitudesSalida solicitudesSalida) {
        try {
            ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = "  select iade.cod_ingreso_almacen, iade.cod_material, iade.etiqueta,  iade.cod_estado_material, iade.cod_empaque_secundario_externo, "
                    + " iade.CANTIDAD_RESTANTE cantidad_r, iade.fecha_vencimiento, iade.lote_material_proveedor "
                    + " ,e.nombre_estado_material,ia.nro_ingreso_almacen,sec.nombre_seccion,ese.nombre_empaque_secundario_externo ,"
                    + " (select sadi1.CANTIDAD from SALIDAS_ALMACEN_DETALLE_INGRESO sadi1 where sadi1.COD_INGRESO_ALMACEN=iade.COD_INGRESO_ALMACEN and  sadi1.COD_MATERIAL = iade.COD_MATERIAL and sadi1.ETIQUETA = iade.ETIQUETA"
                    + "  and sadi1.COD_SALIDA_ALMACEN = '" + salidasAlmacenDetalle.getSalidasAlmacen().getCodSalidaAlmacen() + "') cantidad_sacar"
                    + " from INGRESOS_ALMACEN_DETALLE_ESTADO iade "
                    + " inner join INGRESOS_ALMACEN_DETALLE iad on iad.COD_INGRESO_ALMACEN = iade.COD_INGRESO_ALMACEN and iad.COD_MATERIAL = iade.COD_MATERIAL"
                    + " inner join INGRESOS_ALMACEN ia on ia.COD_INGRESO_ALMACEN = iad.COD_INGRESO_ALMACEN"
                    + " inner join ALMACENES a on a.COD_ALMACEN = ia.COD_ALMACEN"
                    + " inner join ESTADOS_MATERIAL e on e.COD_ESTADO_MATERIAL = iade.COD_ESTADO_MATERIAL"
                    + " inner join EMPAQUES_SECUNDARIO_EXTERNO ese on ese.COD_EMPAQUE_SECUNDARIO_EXTERNO = iade.COD_EMPAQUE_SECUNDARIO_EXTERNO "
                    + " inner join secciones sec on sec.COD_SECCION = iad.COD_SECCION "
                    + " where a.COD_ALMACEN = '" + usuario.getAlmacenesGlobal().getCodAlmacen() + "'  and ia.COD_ESTADO_INGRESO_ALMACEN = 1 and ia.ESTADO_SISTEMA = 1  "
                    + " and iade.CANTIDAD_RESTANTE>0 and iade.COD_ESTADO_MATERIAL in (1,2,6,8) " + //
                    " and iade.COD_MATERIAL = '" + salidasAlmacenDetalle.getMateriales().getCodMaterial() + "' and ia.ESTADO_SISTEMA = 1 "
                    + //" and ia.COD_INGRESO_ALMACEN in ( select s.COD_INGRESO_ALMACEN from " +
                    //" SOLICITUDES_SALIDA_DETALLE_SOLICITUD s where s.COD_FORM_SALIDA = '"+solicitudesSalida.getCodFormSalida()+"') " +
                    " order by  iade.fecha_vencimiento asc,ia.nro_ingreso_almacen asc"; //and iade.COD_ESTADO_MATERIAL = 2

            System.out.println("-consulta predeterminado " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            salidasAlmacenDetalle.getSalidasAlmacenDetalleIngresoList().clear();
            salidasAlmacenDetalle.setCantidadDisponible(0);
            while (rs.next()) {

                SalidasAlmacenDetalleIngreso salidasAlmacenDetalleIngreso = new SalidasAlmacenDetalleIngreso();
                salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getIngresosAlmacen().setCodIngresoAlmacen(rs.getInt("cod_ingreso_almacen"));
                salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getMateriales().setCodMaterial(rs.getString("cod_material"));
                salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().setEtiqueta(rs.getInt("etiqueta"));
                salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getEstadosMaterial().setCodEstadoMaterial(rs.getInt("cod_estado_material"));
                salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getEstadosMaterial().setNombreEstadoMaterial(rs.getString("nombre_estado_material"));
                salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getEmpaqueSecundarioExterno().setCodEmpaqueSecundarioExterno(rs.getInt("cod_empaque_secundario_externo"));
                salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().setFechaVencimiento(rs.getDate("fecha_vencimiento"));
                salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getIngresosAlmacen().setNroIngresoAlmacen(rs.getInt("nro_ingreso_almacen"));
                salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getIngresosAlmacenDetalle().getSecciones().setNombreSeccion(rs.getString("nombre_seccion"));
                salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getEmpaqueSecundarioExterno().setNombreEmpaqueSecundarioExterno(rs.getString("nombre_empaque_secundario_externo"));
                salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().setLoteMaterialProveedor(rs.getString("lote_material_proveedor"));
                salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getIngresosAlmacenDetalle().setCostoUnitario(0);

                salidasAlmacenDetalleIngreso.setCantidad(rs.getFloat("cantidad_r"));
                salidasAlmacenDetalleIngreso.setCantidadDisponible(rs.getFloat("cantidad_r"));

                if (Utiles.redondear(salidasAlmacenDetalleIngreso.getCantidadDisponible(), 2) > 0) {

                    salidasAlmacenDetalle.getSalidasAlmacenDetalleIngresoList().add(salidasAlmacenDetalleIngreso);
                    salidasAlmacenDetalle.setCantidadDisponible(salidasAlmacenDetalle.getCantidadDisponible() + salidasAlmacenDetalleIngreso.getCantidadDisponible());
                }
            }
            rs.close();
            st.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void detalleEtiquetasSalidaAlmacen(SalidasAlmacenDetalle salidasAlmacenDetalleItem) {
        try {

            float cantidadSalidaAlmacen = salidasAlmacenDetalleItem.getCantidadSalidaAlmacen();
            Iterator i = salidasAlmacenDetalleItem.getSalidasAlmacenDetalleIngresoList().iterator();
            while (i.hasNext()) {
                SalidasAlmacenDetalleIngreso salidasAlmacenDetalleIngreso = (SalidasAlmacenDetalleIngreso) i.next();
                if (cantidadSalidaAlmacen > 0) {
                    if (cantidadSalidaAlmacen >= salidasAlmacenDetalleIngreso.getCantidadDisponible()) {
                        salidasAlmacenDetalleIngreso.setCantidadSacar(salidasAlmacenDetalleIngreso.getCantidadDisponible());
                        cantidadSalidaAlmacen = cantidadSalidaAlmacen - salidasAlmacenDetalleIngreso.getCantidadDisponible();
                    } else {
                        salidasAlmacenDetalleIngreso.setCantidadSacar(cantidadSalidaAlmacen);
                        cantidadSalidaAlmacen = cantidadSalidaAlmacen - salidasAlmacenDetalleIngreso.getCantidadDisponible();
                    }
                } else {
                    salidasAlmacenDetalleIngreso.setCantidadSacar(0);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String agregarItem_action() {
        try {
            materialesBuscarList.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public String editarCantidadSalidasAlmacenDetalleAction() {
            if (salidasAlmacenDetalle.getEquivalencias().getValorEquivalencia() > 0) {
                salidasAlmacenDetalle.setCantidadSalidaAlmacenEquivalente(salidasAlmacenDetalle.getCantidadSalidaAlmacen()
                        / salidasAlmacenDetalle.getEquivalencias().getValorEquivalencia());
            }
        return null;
    }

    public String editarCantidadSalidasAlmacenDetalle_action() {
        System.out.println("editar");
        try {
            Iterator i = salidasAlmacenDetalleList.iterator();
            while (i.hasNext()) {
                SalidasAlmacenDetalle salidasAlmacenDetalleItem = (SalidasAlmacenDetalle) i.next();
                if (salidasAlmacenDetalleItem.getChecked().booleanValue() == true) {
                    salidasAlmacenDetalle = salidasAlmacenDetalleItem;
                    break;
                }
            }
            if (salidasAlmacenDetalle.getEquivalencias().getValorEquivalencia() > 0) {
                salidasAlmacenDetalle.setCantidadSalidaAlmacenEquivalente(salidasAlmacenDetalle.getCantidadSalidaAlmacen()
                        / salidasAlmacenDetalle.getEquivalencias().getValorEquivalencia());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public String eliminarSalidaAlmacenDetalleAction(){
        salidasAlmacenDetalleList.remove(salidasAlmacenDetalle);
        return null;
    }

    public String eliminarDetalle_action() {
        try {
            List auxList = new ArrayList();
            Iterator i = salidasAlmacenDetalleList.iterator();
            while (i.hasNext()) {
                SalidasAlmacenDetalle salidasAlmacenDetalleItem = (SalidasAlmacenDetalle) i.next();
                if (salidasAlmacenDetalleItem.getChecked() != true) {
                    auxList.add(salidasAlmacenDetalleItem);
                }
            }
            salidasAlmacenDetalleList = auxList;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String guardarSalidaAlmacen_action(){
        this.transaccionExitosa = false;
        if (this.verificaTransaccionCerradaAlmacen() == true) {
            this.mostrarMensajeTransaccionFallida("Las transacciones para este mes fueron cerradas ");
            return null;
        }
        if (salidasAlmacenDetalleList.size() == 0) {
            this.mostrarMensajeTransaccionFallida(" No existe detalle de salida ");
            return null;
        }
        if (this.detalleItems(salidasAlmacenDetalleList) == false) {
            this.mostrarMensajeTransaccionFallida(" No existe detalle de items ");
            return null;
        }
        if (this.validaCantidadSalidaCantidadDisponible(salidasAlmacenDetalleList) == false) {
            this.mostrarMensajeTransaccionFallida(" No existe cantidad de salida suficiente en uno o varios items ");
            return null;
        }
        Iterator iterador = salidasAlmacenDetalleList.iterator();
        while (iterador.hasNext()) {
            SalidasAlmacenDetalle salidasAlmacenDetalleItem = (SalidasAlmacenDetalle) iterador.next();
            if(salidasAlmacenDetalleItem.getCantidadSalidaAlmacen() <= 0){
                this.mostrarMensajeTransaccionFallida("No se puede registrar una salida con cantidad 0 en el item :\n "+salidasAlmacenDetalleItem.getMateriales().getNombreMaterial());
                return null;
            }
            if(salidasAlmacenDetalleItem.getCantidadSalidaAlmacen() > salidasAlmacenDetalleItem.getSalidasAlmacenDetalleIngresoListSumaCantidadDetallada()+0.01
                        || salidasAlmacenDetalleItem.getCantidadSalidaAlmacen() < salidasAlmacenDetalleItem.getSalidasAlmacenDetalleIngresoListSumaCantidadDetallada()-0.01)
            {
                this.mostrarMensajeTransaccionFallida("La cantidad detallada para el item "+salidasAlmacenDetalleItem.getMateriales().getNombreMaterial()+
                        " \\nNo coincide con la cantidad a sacar, favor revise las etiquetas"+
                        " \\nCantidad a sacar: "+Utiles.redondear(salidasAlmacenDetalleItem.getCantidadSalidaAlmacen(), 2)+
                        " \\nCantidad detallada: "+Utiles.redondear(salidasAlmacenDetalleItem.getSalidasAlmacenDetalleIngresoListSumaCantidadDetallada(), 2)+
                        ", favor revise las etiquetas");
                return null;
            }

        }
        
        Connection con = null;
        
        try {
            con = Util.openConnection(con);
            ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            salidasAlmacen.setCodSalidaAlmacen(this.generaCodSalidaAlmacen());
            salidasAlmacen.setNroSalidaAlmacen(this.generaNroIngresoAlmacen());
            con.setAutoCommit(false);
            String consulta = " INSERT INTO SALIDAS_ALMACEN ( COD_GESTION, COD_SALIDA_ALMACEN, COD_ORDEN_PESADA,  COD_FORM_SALIDA, COD_PROD, "
                    + "   COD_TIPO_SALIDA_ALMACEN,  COD_AREA_EMPRESA,   NRO_SALIDA_ALMACEN,   FECHA_SALIDA_ALMACEN,   OBS_SALIDA_ALMACEN, "
                    + "  ESTADO_SISTEMA,   COD_ALMACEN,   COD_ORDEN_COMPRA,   COD_PERSONAL,    COD_ESTADO_SALIDA_ALMACEN,   COD_LOTE_PRODUCCION, "
                    + "   COD_ESTADO_SALIDA_COSTO,   cod_prod_ant,   orden_trabajo,   COD_PRESENTACION ,COD_MAQUINA,COD_AREA_INSTALACION) VALUES ( "
                    + "  '" + salidasAlmacen.getGestiones().getCodGestion() + "', '" + salidasAlmacen.getCodSalidaAlmacen() + "', '" + salidasAlmacen.getCodOrdenRepesada() + "',"
                    + "  '" + salidasAlmacen.getCodFormSalida() + "','" + (salidasAlmacen.getComponentesProd().getCodCompprod()==null?"0":salidasAlmacen.getComponentesProd().getCodCompprod()) + "', '" + salidasAlmacen.getTiposSalidasAlmacen().getCodTipoSalidaAlmacen() + "', "
                    + "   '" + salidasAlmacen.getAreasEmpresa().getCodAreaEmpresa() + "', '" + salidasAlmacen.getNroSalidaAlmacen() + "','" + sdf.format(salidasAlmacen.getFechaSalidaAlmacen()) + "',"
                    + "  '" + salidasAlmacen.getObsSalidaAlmacen() + "',  '" + salidasAlmacen.getEstadoSistema() + "','" + salidasAlmacen.getAlmacenes().getCodAlmacen() + "',"
                    + "  '" + salidasAlmacen.getOrdenesCompra().getCodOrdenCompra() + "', '" + salidasAlmacen.getPersonal().getCodPersonal() + "',  '" + salidasAlmacen.getEstadosSalidasAlmacen().getCodEstadoSalidaAlmacen() + "',"
                    + "  '" + salidasAlmacen.getCodLoteProduccion() + "','" + salidasAlmacen.getCodEstadoSalidaCosto() + "',"
                    + "  '" + salidasAlmacen.getCodProdAnt() + "', '" + salidasAlmacen.getOrdenTrabajo() + "', '" + (salidasAlmacen.getPresentacionesProducto().getCodPresentacion()==null?"0":salidasAlmacen.getPresentacionesProducto().getCodPresentacion()) + "','" + (salidasAlmacen.getCodLoteProduccion().equals("") ? salidasAlmacen.getMaquinaria().getCodMaquina() : "") + "','" + salidasAlmacen.getAreasInstalaciones().getCodAreaInstalacion() + "'); ";
            System.out.println("consulta " + consulta);
            st.executeUpdate(consulta);
            //iteracion de detalle
            Iterator i = salidasAlmacenDetalleList.iterator();

            while (i.hasNext()) {
                SalidasAlmacenDetalle salidasAlmacenDetalleItem = (SalidasAlmacenDetalle) i.next();
                
                consulta = " INSERT INTO SALIDAS_ALMACEN_DETALLE ( COD_SALIDA_ALMACEN, COD_MATERIAL, CANTIDAD_SALIDA_ALMACEN, COD_UNIDAD_MEDIDA, "
                        + "  COD_ESTADO_MATERIAL )  VALUES (   '" + salidasAlmacen.getCodSalidaAlmacen() + "', '" + salidasAlmacenDetalleItem.getMateriales().getCodMaterial() + "',"
                        + "  '" + salidasAlmacenDetalleItem.getCantidadSalidaAlmacen() + "',   '" + salidasAlmacenDetalleItem.getUnidadesMedida().getCodUnidadMedida() + "', "
                        + "   '" + salidasAlmacenDetalleItem.getEstadosMaterial().getCodEstadoMaterial() + "' ) ";
                System.out.println("consulta " + consulta);
                st.executeUpdate(consulta);
                Iterator i1 = salidasAlmacenDetalleItem.getSalidasAlmacenDetalleIngresoList().iterator();

                while (i1.hasNext()) {
                    SalidasAlmacenDetalleIngreso salidasAlmacenDetalleIngreso = (SalidasAlmacenDetalleIngreso) i1.next();
                    if (salidasAlmacenDetalleIngreso.getCantidadSacar() > 0) {
                        consulta = "  INSERT INTO SALIDAS_ALMACEN_DETALLE_INGRESO ( COD_SALIDA_ALMACEN,  COD_MATERIAL, COD_INGRESO_ALMACEN,  ETIQUETA, "
                                            + " COSTO_SALIDA,   FECHA_VENCIMIENTO,   CANTIDAD,   COSTO_SALIDA_ACTUALIZADO,   FECHA_ACTUALIZACION,   COSTO_SALIDA_ACTUALIZADO_FINAL"
                                            + ", COD_ESTANTE, FILA, COLUMNA)"
                                    + " VALUES ( '" + salidasAlmacen.getCodSalidaAlmacen() + "',   '" + salidasAlmacenDetalleItem.getMateriales().getCodMaterial() + "',   "
                                            + " '" + salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getIngresosAlmacen().getCodIngresoAlmacen() + "', "
                                            + " '" + salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getEtiqueta() + "', '" + salidasAlmacenDetalleIngreso.getCostoSalida() + "',"
                                            + (salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getFechaVencimiento() != null ? " '" + salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getFechaVencimiento() + "'":"null")+","
                                            + " '" + salidasAlmacenDetalleIngreso.getCantidadSacar() + "', '" + salidasAlmacenDetalleIngreso.getCostoSalidaActualizado() + "', "
                                            + " '" + sdf.format(salidasAlmacenDetalleIngreso.getFechaActualizacion()) + "',  '" + salidasAlmacenDetalleIngreso.getCostoSalidaActualizadoFinal() + "'"
                                            + ",'" + salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getEstanteAlmacen().getCodEstante()+"'"
                                            + ",'" + salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getFila()+"'"
                                            + ",'" + salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getColumna()+"'"
                                    + " ) ";
                        System.out.println("consulta " + consulta);
                        st.executeUpdate(consulta);
                        consulta = " update ingresos_almacen_detalle_estado set cantidad_restante=cantidad_restante - " + salidasAlmacenDetalleIngreso.getCantidadSacar() + " "
                                + " where cod_material=" + salidasAlmacenDetalleItem.getMateriales().getCodMaterial() + " and etiqueta=" + salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getEtiqueta() + " "
                                + " and cod_ingreso_almacen='" + salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getIngresosAlmacen().getCodIngresoAlmacen() + "' ";
                        System.out.println("consulta " + consulta);
                        st.executeUpdate(consulta);
                    }
                }
                
                consulta = " UPDATE SOLICITUDES_SALIDA_DETALLE  SET  CANTIDAD_ENTREGADA = CANTIDAD_ENTREGADA + '" + salidasAlmacenDetalleItem.getCantidadSalidaAlmacen() + "' "
                        + " WHERE COD_FORM_SALIDA = '" + solicitudesSalida.getCodFormSalida() + "' "
                        + " AND COD_MATERIAL = '" + salidasAlmacenDetalleItem.getMateriales().getCodMaterial() + "' ;";
                System.out.println("consulta " + consulta);
                st.executeUpdate(consulta);
            }
            
            if(salidasAlmacen.getTiposSalidasAlmacen().getCodTipoSalidaAlmacen() == COD_TIPO_SALIDA_TRASPASO)
            {
                consulta = " INSERT INTO TRASPASOS(COD_SALIDA_ALMACEN, COD_ESTADO_TRASPASO,COD_ALMACEN_ORIGEN,"
                                                + " COD_ALMACEN_DESTINO, FECHA_TRASPASO, COD_INGRESO_ALMACEN,COD_TIPO_TRASPASO)"
                                                +"  VALUES ("
                                                    + salidasAlmacen.getCodSalidaAlmacen()+","
                                                    + COD_ESTADO_TRASPASO_EN_TRANSITO + ","
                                                    + salidasAlmacen.getAlmacenes().getCodAlmacen()+","
                                                    + solicitudesSalida.getAlmacenesDestino().getCodAlmacen()+","
                                                    + "getdate(),"
                                                    + "0,"//cod ingreso 0 ya que no se confirmo aun
                                                    + "1"
                                                + ")";
                System.out.println("consulta registrar traspaso desde solicitud "+consulta);
                if(st.executeUpdate(consulta) > 0)System.out.println("se registro el traspaso");
                                                    //+salidasAlmacen. , '2', '17', '2017/07/21', '0', 1);"
            }
            //EJECUTAR SP LOG
            consulta = " exec [PAA_REGISTRO_SALIDA_ALMACEN_LOG] " + salidasAlmacen.getCodSalidaAlmacen() 
                    + " , "+ usuario.getUsuarioModuloBean().getCodUsuarioGlobal()
                    + " , 3";
            System.out.println("consulta ejecutar: " + consulta); 
            st.executeUpdate(consulta);
            //si se completo con la entrega de todos los items solicitados se debe cambiar el estado de la solicitud a completado
            consulta = " update solicitudes_salida set  COD_ESTADO_SOLICITUD_SALIDA_ALMACEN = (case when "
                        +" (select SUM(CASE WHEN SD.CANTIDAD> SD.CANTIDAD_ENTREGADA THEN 1 ELSE 0 END ) from SOLICITUDES_SALIDA_DETALLE sd where sd.COD_FORM_SALIDA= SOLICITUDES_SALIDA.COD_FORM_SALIDA) = 0 then 2 else 3 end )"
                        +" where COD_FORM_SALIDA = "+solicitudesSalida.getCodFormSalida();
            System.out.println("consulta actualizar solicitud salida "+consulta);
            st.executeUpdate(consulta);
            if(solicitudesSalida.getTipoSolicitud().getCodTipoSolicitudSalida()==3){
                consulta = " INSERT INTO TRASPASOS ( COD_SALIDA_ALMACEN,  COD_ESTADO_TRASPASO,   COD_ALMACEN_ORIGEN,   COD_ALMACEN_DESTINO, " +
                    "  FECHA_TRASPASO, COD_TIPO_TRASPASO)  values(    '"+salidasAlmacen.getCodSalidaAlmacen()+"'," +
                    " '"+"1"+"',   '"+salidasAlmacen.getAlmacenes().getCodAlmacen()+"', " +
                    "  '"+traspasos_solicitud.getAlmacenDestino().getCodAlmacen()+"',getdate(),"
                        +traspasos_solicitud.getTipoTraspaso().getCodTipoTraspaso()+" )";
               System.out.println("consulta *j* " + consulta);
                st.executeUpdate(consulta);
            }
            
            

            con.commit();
            this.mostrarMensajeTransaccionExitosa("Se registro satisfactoriamente la salida por solicitud");
            st.close();
            
        } catch (Exception e) {
            LOGGER.warn("error", e);
            this.rollbackConexion(con);
            this.mostrarMensajeTransaccionFallida("Ocurrio un error al momento de aprobar la solicitud, intente de nuevo");
            
        } finally {
            this.cerrarConexion(con);
        }
        return null;
    }

    public HashMap cantidadesSolicitudSalidaAlmacen(SolicitudesSalida solicitudesSalida, Statement st) {
        HashMap hashMap = new HashMap();
        try {
            Connection con = null;
            con = Util.openConnection(con);
            //Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String consulta = " select sum(s.cantidad) cantidad, sum(s.cantidad_entregada) cantidad_entregada from SOLICITUDES_SALIDA_DETALLE s "
                    + " where s.COD_FORM_SALIDA = '" + solicitudesSalida.getCodFormSalida() + "' group by s.cod_form_salida ";
            System.out.println("consulta " + consulta);
            ResultSet rs = st.executeQuery(consulta);
            if (rs.next()) {
                hashMap.put("cantidad", rs.getDouble("cantidad"));
                hashMap.put("cantidad_entregada", rs.getDouble("cantidad_entregada"));
                //System.out.println("datos " + rs.getDouble("cantidad") + " " + rs.getDouble("cantidad_entregada"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hashMap;
    }

    public String guardarSalidasAlmacenDetalleIngreso_action() {
        this.transaccionExitosa = false;
        
        try {
            Iterator i = salidasAlmacenDetalle.getSalidasAlmacenDetalleIngresoList().iterator();
            float cantidadSacar = 0;
            while (i.hasNext()) {
                SalidasAlmacenDetalleIngreso salidasAlmacenDetalleEstado = (SalidasAlmacenDetalleIngreso) i.next();
                cantidadSacar = cantidadSacar + salidasAlmacenDetalleEstado.getCantidadSacar();
            }
            if (cantidadSacar != salidasAlmacenDetalle.getCantidadSalidaAlmacen()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("la sumatoria de las cantidades parciales no es igual a la cantidad de salida"));
                this.transaccionExitosa = false;
            }
            else{
                this.transaccionExitosa = true;
            }

        } catch (Exception e) {
            LOGGER.warn("error", e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Ocurrio un error al momento de registrar el destalle, intente de nuevo"));
        }
        return null;
    }

    public String cargarGrupos() {
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select gr.COD_GRUPO,gr.NOMBRE_GRUPO from grupos gr where gr.COD_ESTADO_REGISTRO = 1 "
                    + " and gr.COD_CAPITULO = '" + salidasAlmacenDetalleAgregar.getMateriales().getGrupos().getCapitulos().getCodCapitulo() + "' ";

            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            gruposList.clear();
            gruposList.add(new SelectItem("0", "-NINGUNO-"));
            while (rs.next()) {
                gruposList.add(new SelectItem(rs.getString("COD_GRUPO"), rs.getString("NOMBRE_GRUPO")));
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

    public String grupos_change() {
        materialesList = this.cargarMateriales(salidasAlmacenDetalleAgregar.getMateriales().getGrupos().getCodGrupo());
        return null;
    }

    public List cargarMateriales(int codGrupo) {
        List materialesList = new ArrayList();
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select m.COD_MATERIAL,m.NOMBRE_MATERIAL from MATERIALES m where m.COD_ESTADO_REGISTRO = 1 "
                    + " and m.COD_GRUPO = '" + codGrupo + "' "
                    + " order by m.NOMBRE_MATERIAL ASC ";
            System.out.println("consulta" + consulta);

            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            materialesList.clear();
            materialesList.add(new SelectItem("0", "-NINGUNO-"));
            while (rs.next()) {
                materialesList.add(new SelectItem(rs.getString("COD_MATERIAL"), rs.getString("NOMBRE_MATERIAL")));
            }
            if (rs != null) {
                rs.close();
                st.close();
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return materialesList;
    }

    public String detallarCantidadSalida_action() {
        try {

            salidasAlmacenDetalleAgregar = new SalidasAlmacenDetalle();

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
            salidasAlmacenDetalleAgregar.getEstadosMaterial().setCodEstadoMaterial(2);//Aprobado

            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select m.COD_MATERIAL,m.NOMBRE_MATERIAL,u.COD_UNIDAD_MEDIDA,u.NOMBRE_UNIDAD_MEDIDA,u.ABREVIATURA "
                    + " from materiales m  "
                    + " inner join UNIDADES_MEDIDA u on m.COD_UNIDAD_MEDIDA = u.COD_UNIDAD_MEDIDA "
                    + " where m.cod_material = '" + salidasAlmacenDetalleAgregar.getMateriales().getCodMaterial() + "' ";
            System.out.println("consulta" + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            if (rs.next()) {
                //salidasAlmacenDetalleAgregar.getMateriales().setCodMaterial(rs.getString("COD_MATERIAL"));
                salidasAlmacenDetalleAgregar.getMateriales().setNombreMaterial(rs.getString("NOMBRE_MATERIAL"));
                salidasAlmacenDetalleAgregar.getMateriales().getUnidadesMedida().setCodUnidadMedida(rs.getInt("COD_UNIDAD_MEDIDA"));
                salidasAlmacenDetalleAgregar.getUnidadesMedida().setCodUnidadMedida(rs.getInt("COD_UNIDAD_MEDIDA"));
                salidasAlmacenDetalleAgregar.getUnidadesMedida().setNombreUnidadMedida(rs.getString("NOMBRE_UNIDAD_MEDIDA"));
                salidasAlmacenDetalleAgregar.getUnidadesMedida().setAbreviatura(rs.getString("ABREVIATURA"));
            }

            ingresosAlmacenDetalleEstado.getEstadosMaterial().setCodEstadoMaterial(2); // en estado aprobado para la busqueda de cantidad disponible
            //System.out.println("antes de redondear " + this.cantidadDisponibleMaterial(salidasAlmacenDetalleAgregar));

            //salidasAlmacenDetalleAgregar.setCantidadDisponible(Utiles.redondear(this.cantidadDisponibleMaterial(salidasAlmacenDetalleAgregar),3));
            ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            salidasAlmacenDetalleAgregar.setCantidadDisponible(Float.valueOf(form.format(this.cantidadDisponibleMaterial(salidasAlmacenDetalleAgregar, ingresosAlmacenDetalleEstado, usuario.getAlmacenesGlobal()))));

            System.out.println("cantidad_disponible : " + salidasAlmacenDetalleAgregar.getCantidadDisponible());
            salidasAlmacenDetalleAgregar.setEquivalencias(this.buscaEquivalencia(salidasAlmacenDetalleAgregar));

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

    public String guardarAgregarSalidaAlmacenDetalle_action() {
        try {
            mensaje = "";
            if (salidasAlmacenDetalleAgregar.getCantidadDisponible() == 0) {
                mensaje = " no existe cantidad disponible ";
                return null;
            }
            if (salidasAlmacenDetalleAgregar.getCantidadSalidaAlmacen() > salidasAlmacenDetalleAgregar.getCantidadDisponible()) {
                mensaje = " la cantidad de salida de almacen es mayor a la cantidad disponible ";
                return null;
            }
            if (ingresosAlmacenDetalleEstado.getEstadosMaterial().getCodEstadoMaterial() != 2 && ingresosAlmacenDetalleEstado.getEstadosMaterial().getCodEstadoMaterial() != 6) {
                mensaje = "no se puede registrar salidas con materiales con el estado seleccionado ";
                return null;
            }

            salidasAlmacenDetalleAgregar.setChecked(false);
            this.etiquetasSalidaAlmacen(salidasAlmacenDetalleAgregar);

            if (salidasAlmacenDetalleAgregar.getCantidadSalidaAlmacen() > salidasAlmacenDetalleAgregar.getCantidadDisponible()) {
                salidasAlmacenDetalleAgregar.setColorFila("#FF0000");
            } else {
                salidasAlmacenDetalleAgregar.setColorFila("#00FF00");
            }
            this.detalleEtiquetasSalidaAlmacen(salidasAlmacenDetalleAgregar);
            salidasAlmacenDetalleList.add(salidasAlmacenDetalleAgregar);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String guardarEdicionCantidadMaterial_action() {
        System.out.println("guardar editar");
        try {

            this.detalleEtiquetasSalidaAlmacen(salidasAlmacenDetalle);

            if (salidasAlmacenDetalle.getCantidadSalidaAlmacen() > salidasAlmacenDetalle.getCantidadDisponible()) {
                salidasAlmacenDetalle.setColorFila("#FF0000");
            } else {
                salidasAlmacenDetalle.setColorFila("#00FF00");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String capitulos_change() {
        try {
            gruposList = this.cargarGrupos(buscarMaterial.getGrupos().getCapitulos().getCodCapitulo());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String buscarMaterial_action() {
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select m.COD_MATERIAL,m.NOMBRE_MATERIAL,gr.NOMBRE_GRUPO,cap.NOMBRE_CAPITULO,"
                    + " m.COD_UNIDAD_MEDIDA,u1.ABREVIATURA ABREVIATURA1 ,m.COD_UNIDAD_MEDIDA_COMPRA,u2.ABREVIATURA ABREVIATURA2  "
                    + " from MATERIALES m  "
                    + " inner join GRUPOS gr on gr.COD_GRUPO = m.COD_GRUPO "
                    + " inner join CAPITULOS cap on cap.COD_CAPITULO = gr.COD_CAPITULO"
                    + " inner join UNIDADES_MEDIDA u1 on u1.COD_UNIDAD_MEDIDA = m.COD_UNIDAD_MEDIDA "
                    + " inner join UNIDADES_MEDIDA u2 on u2.COD_UNIDAD_MEDIDA = m.COD_UNIDAD_MEDIDA_COMPRA  "
                    + " where m.NOMBRE_MATERIAL like '%" + buscarMaterial.getNombreMaterial() + "%' ";
            if (buscarMaterial.getGrupos().getCodGrupo() > 0) {
                consulta = consulta + " and gr.COD_GRUPO = '" + buscarMaterial.getGrupos().getCodGrupo() + "' ";
            }
            if (buscarMaterial.getGrupos().getCapitulos().getCodCapitulo() > 0) {
                consulta = consulta + " and cap.COD_CAPITULO = '" + buscarMaterial.getGrupos().getCapitulos().getCodCapitulo() + "' ";
            }
            consulta = consulta + " and m.MOVIMIENTO_ITEM = 1 and m.COD_ESTADO_REGISTRO = 1 and m.COD_MATERIAL not in (" + this.itemsSeleccionadosSalidaAlmacen() + ") ";
            System.out.println("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            materialesBuscarList.clear();
            while (rs.next()) {
                Materiales materialesItem = new Materiales();
                materialesItem.setCodMaterial(rs.getString("COD_MATERIAL"));
                materialesItem.setNombreMaterial(rs.getString("NOMBRE_MATERIAL"));
                materialesItem.getGrupos().setNombreGrupo(rs.getString("NOMBRE_GRUPO"));
                materialesItem.getGrupos().getCapitulos().setNombreCapitulo(rs.getString("NOMBRE_CAPITULO"));
                materialesItem.getUnidadesMedida().setCodUnidadMedida(rs.getInt("COD_UNIDAD_MEDIDA"));
                materialesItem.getUnidadesMedida().setAbreviatura(rs.getString("ABREVIATURA1"));
                materialesItem.getUnidadesMedidaCompra().setCodUnidadMedida(rs.getInt("COD_UNIDAD_MEDIDA_COMPRA"));
                materialesItem.getUnidadesMedidaCompra().setAbreviatura(rs.getString("ABREVIATURA2"));

                materialesBuscarList.add(materialesItem);
            }
            rs.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String estadoMaterial_change() {
        try {
            ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");

            NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
            DecimalFormat form = (DecimalFormat) nf;
            form.applyPattern("#.###");

            salidasAlmacenDetalleAgregar.setCantidadDisponible(Float.valueOf(form.format(this.cantidadDisponibleMaterial(salidasAlmacenDetalleAgregar, ingresosAlmacenDetalleEstado, usuario.getAlmacenesGlobal()))));

            System.out.println("cantidad_disponible : " + salidasAlmacenDetalleAgregar.getCantidadDisponible());
            salidasAlmacenDetalleAgregar.setEquivalencias(this.buscaEquivalencia(salidasAlmacenDetalleAgregar));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean verificaTransaccionCerradaAlmacen() {
        boolean transaccionCerradaAlmacen = false;
        Date fechaActual = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        try {
            Connection con = null;
            con = Util.openConnection(con);
            ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            String consulta = " select * from estados_transacciones_almacen "
                    + " where cod_gestion='" + usuario.getGestionesGlobal().getCodGestion() + "' "
                    + " and cod_mes = '" + Integer.valueOf(sdf.format(fechaActual)) + "'  "
                    + " and estado=1 ";
            System.out.println("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            if (rs.next()) {
                transaccionCerradaAlmacen = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return transaccionCerradaAlmacen;
    }

    public boolean detalleItems(List salidasAlmacenDetalleList) {
        boolean itemsDetallados = true;
        try {
            Iterator i = salidasAlmacenDetalleList.iterator();
            while (i.hasNext()) {
                SalidasAlmacenDetalle salidasAlmacenDetalleItem = (SalidasAlmacenDetalle) i.next();
                //System.out.println("size: " + salidasAlmacenDetalleItem.getSalidasAlmacenDetalleIngresoList().size());
                if (salidasAlmacenDetalleItem.getSalidasAlmacenDetalleIngresoList().size() == 0) {
                    itemsDetallados = false;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemsDetallados;
    }

    public boolean validaCantidadSalidaCantidadDisponible(List salidasAlmacenDetalleList) {
        boolean cantidadValidada = true;
        try {
            Iterator i = salidasAlmacenDetalleList.iterator();
            while (i.hasNext()) {
                SalidasAlmacenDetalle salidasAlmacenDetalleItem = (SalidasAlmacenDetalle) i.next();
                if (salidasAlmacenDetalleItem.getCantidadDisponible() < salidasAlmacenDetalleItem.getCantidadSalidaAlmacen()) {
                    cantidadValidada = false;
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return cantidadValidada;
    }

    public int generaCodSalidaAlmacen() {
        int codSalidaAlmacen = 0;
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select isnull(max(s.COD_SALIDA_ALMACEN),0)+1 COD_SALIDA_ALMACEN from SALIDAS_ALMACEN s  ";
            System.out.println("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            if (rs.next()) {
                codSalidaAlmacen = rs.getInt("COD_SALIDA_ALMACEN");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return codSalidaAlmacen;
    }

    public float cantidadDisponibleMaterial(SalidasAlmacenDetalle salidasAlmacenDetalle, IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstado, Almacenes almacenes) {
        float cantidadDisponible = 0;
        try {
            ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select iade.cantidad_parcial - "
                    + " ( select ISNULL(sum(sadi.cantidad), 0) "
                    + "  from SALIDAS_ALMACEN_DETALLE_INGRESO sadi "
                    + "  where sadi.cod_material = iade.cod_material and "
                    + "  sadi.cod_ingreso_almacen = iade.cod_ingreso_almacen and "
                    + "  sadi.etiqueta = iade.etiqueta and "
                    + "  cod_salida_almacen in ( "
                    + "  select cod_salida_almacen "
                    + "  from SALIDAS_ALMACEN "
                    + "  where cod_estado_salida_almacen = 1 and "
                    + "  estado_sistema = 1 and cod_almacen = '" + usuario.getAlmacenesGlobal().getCodAlmacen() + "' ) ) + "
                    + " ( select isnull(sum(cantidad_devuelta), 0) "
                    + "  from DEVOLUCIONES_DETALLE_ETIQUETAS dde "
                    + "  where dde.cod_material = iade.cod_material and "
                    + "  dde.cod_ingreso_almacen = iade.cod_ingreso_almacen and "
                    + "  dde.etiqueta = iade.etiqueta "
                    + "  and dde.cod_devolucion in ( "
                    + "  select cod_devolucion from DEVOLUCIONES where cod_estado_devolucion = 1 and "
                    + "  estado_sistema = 1 and cod_almacen = '" + usuario.getAlmacenesGlobal().getCodAlmacen() + "' ) ) as cantidad_r "
                    + "  from ingresos_almacen_detalle_estado iade,ingresos_almacen_detalle  iad,secciones s,almacenes a,ingresos_almacen ia "
                    + " WHERE a.cod_almacen='" + usuario.getAlmacenesGlobal().getCodAlmacen() + "'  and iade.cod_material=iad.cod_material  "
                    + " and iade.cod_ingreso_almacen=iad.cod_ingreso_almacen  "
                    + " and ia.estado_sistema=1 "
                    + " and ia.cod_ingreso_almacen=iad.cod_ingreso_almacen  "
                    + " and ia.cod_estado_ingreso_almacen=1  "
                    + " and ia.cod_almacen=a.cod_almacen "
                    + " and iad.cod_seccion=s.cod_seccion  "
                    + " and s.cod_almacen=a.cod_almacen "
                    + " and iade.cantidad_restante>0   "
                    + " and iad.cod_seccion=s.cod_seccion  "
                    + " and s.cod_almacen=a.cod_almacen and iade.COD_MATERIAL = " + salidasAlmacenDetalle.getMateriales().getCodMaterial() + " "
                    + " and iade.COD_ESTADO_MATERIAL = 2  ";

            consulta = " select iade.cantidad_parcial - ( "
                    + "  select ISNULL(sum(sadi.cantidad), 0) from SALIDAS_ALMACEN_DETALLE_INGRESO sadi inner join SALIDAS_ALMACEN_DETALLE sad on sad.COD_SALIDA_ALMACEN = sadi.COD_SALIDA_ALMACEN"
                    + "  and sad.COD_MATERIAL = sadi.COD_MATERIAL inner join SALIDAS_ALMACEN s on s.COD_SALIDA_ALMACEN = sad.COD_SALIDA_ALMACEN "
                    + "  where sadi.cod_material = iade.cod_material and sadi.cod_ingreso_almacen = iade.cod_ingreso_almacen and  sadi.etiqueta = iade.etiqueta and "
                    + "  s.ESTADO_SISTEMA = 1 and s.COD_ALMACEN = '" + usuario.getAlmacenesGlobal().getCodAlmacen() + "'  and s.COD_ESTADO_SALIDA_ALMACEN = 1 ) + "
                    + " ( select isnull(sum(dde.cantidad_devuelta), 0) from DEVOLUCIONES_DETALLE_ETIQUETAS dde inner join DEVOLUCIONES_DETALLE dd on dd.COD_DEVOLUCION = dde.COD_DEVOLUCION "
                    + "  and dd.COD_MATERIAL = dde.COD_MATERIAL inner join DEVOLUCIONES d on d.COD_DEVOLUCION = dde.COD_DEVOLUCION  where dde.cod_material = iade.cod_material "
                    + "  and dde.cod_ingreso_almacen = iade.cod_ingreso_almacen and dde.etiqueta = iade.etiqueta "
                    + "  and d.COD_ESTADO_DEVOLUCION = 1 and d.ESTADO_SISTEMA = 1 and d.COD_ALMACEN = '" + usuario.getAlmacenesGlobal().getCodAlmacen() + "' ) as cantidad_r "
                    + " from ingresos_almacen_detalle_estado iade, ingresos_almacen_detalle iad, secciones s, almacenes a, ingresos_almacen ia "
                    + " WHERE a.cod_almacen = '" + usuario.getAlmacenesGlobal().getCodAlmacen() + "'  and iade.cod_material = iad.cod_material and iade.cod_ingreso_almacen = iad.cod_ingreso_almacen "
                    + " and ia.estado_sistema = 1 and ia.cod_ingreso_almacen = iad.cod_ingreso_almacen and ia.cod_estado_ingreso_almacen = 1 "
                    + " and ia.cod_almacen = a.cod_almacen and iad.cod_seccion = s.cod_seccion "
                    + " and s.cod_almacen = a.cod_almacen and iade.cantidad_restante > 0 and iad.cod_seccion = s.cod_seccion "
                    + " and s.cod_almacen = a.cod_almacen and iade.COD_MATERIAL = '" + salidasAlmacenDetalle.getMateriales().getCodMaterial() + "' and iade.COD_ESTADO_MATERIAL = '" + ingresosAlmacenDetalleEstado.getEstadosMaterial().getCodEstadoMaterial() + "'";

            consulta = " select iade.cantidad_restante as cantidad_r "
                    + " from ingresos_almacen_detalle_estado iade, ingresos_almacen_detalle iad, secciones s, almacenes a, ingresos_almacen ia "
                    + " WHERE a.cod_almacen = '" + almacenes.getCodAlmacen() + "'  and iade.cod_material = iad.cod_material and iade.cod_ingreso_almacen = iad.cod_ingreso_almacen "
                    + " and ia.estado_sistema = 1 and ia.cod_ingreso_almacen = iad.cod_ingreso_almacen and ia.cod_estado_ingreso_almacen = 1 "
                    + " and ia.cod_almacen = a.cod_almacen and iad.cod_seccion = s.cod_seccion "
                    + " and s.cod_almacen = a.cod_almacen and iade.cantidad_restante > 0 and iad.cod_seccion = s.cod_seccion "
                    + " and s.cod_almacen = a.cod_almacen and iade.COD_MATERIAL = '" + salidasAlmacenDetalle.getMateriales().getCodMaterial() + "' and iade.COD_ESTADO_MATERIAL = '" + ingresosAlmacenDetalleEstado.getEstadosMaterial().getCodEstadoMaterial() + "'"; //usuario.getAlmacenesGlobal().getCodAlmacen()

            System.out.println("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            while (rs.next()) {
                cantidadDisponible = cantidadDisponible + rs.getFloat("cantidad_r");
            }
            rs.close();
            st.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return cantidadDisponible;
    }

    public List cargarGrupos(int codCapitulo) {
        List gruposList = new ArrayList();
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " SELECT GR.COD_GRUPO,GR.NOMBRE_GRUPO FROM GRUPOS GR WHERE GR.COD_CAPITULO = '" + codCapitulo + "' AND GR.COD_ESTADO_REGISTRO = 1 ";
            System.out.println("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            gruposList.clear();
            gruposList.add(new SelectItem("0", "-NINGUNO-"));
            while (rs.next()) {
                gruposList.add(new SelectItem(rs.getString("COD_GRUPO"), rs.getString("NOMBRE_GRUPO")));
            }
            rs.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return gruposList;
    }

    public String itemsSeleccionadosSalidaAlmacen() {
        String itemsSalidaSalidaAlmacen = "-1";
        try {
            Iterator i = salidasAlmacenDetalleList.iterator();
            while (i.hasNext()) {
                SalidasAlmacenDetalle salidasAlmacenDetalleItem = (SalidasAlmacenDetalle) i.next();
                itemsSalidaSalidaAlmacen = itemsSalidaSalidaAlmacen + "," + salidasAlmacenDetalleItem.getMateriales().getCodMaterial();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemsSalidaSalidaAlmacen;

    }

    public String getCargarContenidoRegistroSalidaAlmacenPorLoteProveedor() {
        try {
            ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            salidasAlmacen = new SalidasAlmacen();
            salidasAlmacen.setNroSalidaAlmacen(this.generaNroIngresoAlmacen());
            salidasAlmacen.getGestiones().setCodGestion(usuario.getGestionesGlobal().getCodGestion());
            salidasAlmacen.getPersonal().setCodPersonal(usuario.getUsuarioModuloBean().getCodUsuarioGlobal());
            salidasAlmacen.getAlmacenes().setCodAlmacen(usuario.getAlmacenesGlobal().getCodAlmacen());
            salidasAlmacen.setAreasEmpresa(solicitudesSalida.getAreaDestinoSalida());
            salidasAlmacen.setTiposSalidasAlmacen(solicitudesSalida.getTiposSalidasAlmacen());
            salidasAlmacen.setOrdenTrabajo(solicitudesSalida.getOrdenTrabajo());
            salidasAlmacen.setCodLoteProduccion(solicitudesSalida.getCodLoteProduccion());
            salidasAlmacen.setMaquinaria(solicitudesSalida.getMaquinaria());
            salidasAlmacen.setAreasInstalaciones(solicitudesSalida.getAreasInstalaciones());
            salidasAlmacen.getEstadosSalidasAlmacen().setCodEstadoSalidaAlmacen(1);
            salidasAlmacen.setEstadoSistema(1);
            tiposSalidasAlmacenList = this.cargarTiposSalidasAlmacen();
            salidasAlmacenDetalleList.clear();
            cargarDetalleAgregarSalidaPorLoteProveedor();
            salidasAlmacen.setComponentesProd(solicitudesSalida.getComponentesProd());
            salidasAlmacen.setPresentacionesProducto(solicitudesSalida.getPresentacionesProducto());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getCargarContenidoRegistroSalidaAlmacen() {
        try {
            ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");

            salidasAlmacen = new SalidasAlmacen();
            salidasAlmacen.setNroSalidaAlmacen(this.generaNroIngresoAlmacen());
             System.out.println("nro salida "+salidasAlmacen.getNroSalidaAlmacen());
            salidasAlmacen.getGestiones().setCodGestion(usuario.getGestionesGlobal().getCodGestion());
            salidasAlmacen.getPersonal().setCodPersonal(usuario.getUsuarioModuloBean().getCodUsuarioGlobal());
            salidasAlmacen.getAlmacenes().setCodAlmacen(usuario.getAlmacenesGlobal().getCodAlmacen());
            salidasAlmacen.setAreasEmpresa(solicitudesSalida.getAreaDestinoSalida());
            salidasAlmacen.setTiposSalidasAlmacen(solicitudesSalida.getTiposSalidasAlmacen());
            salidasAlmacen.setOrdenTrabajo(solicitudesSalida.getOrdenTrabajo());
            salidasAlmacen.setCodLoteProduccion(solicitudesSalida.getCodLoteProduccion());
            salidasAlmacen.setMaquinaria(solicitudesSalida.getMaquinaria());
            salidasAlmacen.setAreasInstalaciones(solicitudesSalida.getAreasInstalaciones());
            salidasAlmacen.getEstadosSalidasAlmacen().setCodEstadoSalidaAlmacen(1);
            salidasAlmacen.setEstadoSistema(1);
            //J set cod form salida
            System.out.println("J solicitud salida");
               salidasAlmacen.setCodFormSalida(solicitudesSalida.getCodFormSalida()); 
            areasEmpresaList = this.cargarAreasEmpresa();
            this.cargarComponentesProd(new AreasEmpresa());
            tiposSalidasAlmacenList = this.cargarTiposSalidasAlmacen();
            capitulosList = this.cargarCapitulos();
            gruposList.clear();
            gruposList.add(new SelectItem("0", "-NINGUNO-"));
            materialesList.add(new SelectItem("0", "-NINGUNO-"));
            presentacionesList.add(new SelectItem("0", "-NINGUNO-"));
            salidasAlmacenDetalleList.clear();
            estadosMaterialList = this.cargarEstadosMaterial();
            //ingresosAlmacenDetalleEstado.getEstadosMaterial().setCodEstadoMaterial(solicitudesSalida.getAreaDestinoSalida().getCodAreaEmpresa().equals("40")?1:2); //estado aprobado
            ingresosAlmacenDetalleEstado.getEstadosMaterial().setCodEstadoMaterial(solicitudesSalida.getAreaDestinoSalida().getCodAreaEmpresa().equals("40") ? 2 : 2); //estado aprobado
            //ingresosAlmacenDetalleEstado.getEstadosMaterial().setCodEstadoMaterial(ingresosAlmacenDetalleEstado.getEstadosMaterial().getCodEstadoMaterial());
            salidasAlmacenDetalleList = this.cargarSalidaAlmacenDetalle(solicitudesSalida);
            //this.cargarComponentesProd(salidasAlmacen.getAreasEmpresa());
            salidasAlmacen.setComponentesProd(solicitudesSalida.getComponentesProd());
            salidasAlmacen.setPresentacionesProducto(solicitudesSalida.getPresentacionesProducto());
            this.producto_changed();

//            // verificacion si existe algun objeto de donde se pueda realizar una salida de almacen
//            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
//            Map<String, Object> sessionMap = externalContext.getSessionMap();
//            ProgramaProduccion programaProduccion =(ProgramaProduccion)sessionMap.get("programaProduccion");
//            List materiaPrimaList = (ArrayList)sessionMap.get("materiaPrimaList");
//            List empaquePrimarioList = (ArrayList)sessionMap.get("empaquePrimarioList");
//            List empaqueSecundarioList = (ArrayList)sessionMap.get("empaqueSecundarioList");
//
//
//            if(programaProduccion!=null){
//                ingresosAlmacenDetalleEstado.getEstadosMaterial().setCodEstadoMaterial(2); //estado aprobado
//                salidasAlmacen.setCodLoteProduccion(programaProduccion.getCodLoteProduccion());
//                salidasAlmacen.getComponentesProd().setCodCompprod(programaProduccion.getFormulaMaestra().getComponentesProd().getCodCompprod());
//                this.cargaSalidasAlmacenProgramaProduccion(programaProduccion,materiaPrimaList,empaquePrimarioList,empaqueSecundarioList);
//                this.producto_changed();
//                sessionMap.put("programaProduccion",null);
//            }
            traspasos_solicitud=cargarTraspaso(solicitudesSalida);
             cargarAlmacenes();
             System.out.println("nro salida "+salidasAlmacen.getNroSalidaAlmacen());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List cargarSalidaAlmacenDetalle(SolicitudesSalida solicitudesSalida) {
        List salidasAlmacenDetalleList = new ArrayList();
        try {
            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement();
            String consulta = " select m.COD_MATERIAL,m.NOMBRE_MATERIAL,sd.CANTIDAD,sd.CANTIDAD_ENTREGADA,u.COD_UNIDAD_MEDIDA,u.NOMBRE_UNIDAD_MEDIDA,u.ABREVIATURA  "
                    + " from SOLICITUDES_SALIDA_DETALLE sd inner join MATERIALES m on m.COD_MATERIAL = sd.COD_MATERIAL "
                    + " inner join UNIDADES_MEDIDA u on u.COD_UNIDAD_MEDIDA = sd.COD_UNIDAD_MEDIDA "
                    + " where sd.COD_FORM_SALIDA = '" + solicitudesSalida.getCodFormSalida() + "'";
            System.out.println("consulta " + consulta);
            ResultSet rs = st.executeQuery(consulta);
            salidasAlmacenDetalleList.clear();
            while (rs.next()) {
                SalidasAlmacenDetalle salidasAlmacenDetalle = new SalidasAlmacenDetalle();
                salidasAlmacenDetalle.getMateriales().setCodMaterial(rs.getString("COD_MATERIAL"));
                salidasAlmacenDetalle.getMateriales().setNombreMaterial(rs.getString("NOMBRE_MATERIAL"));
                salidasAlmacenDetalle.setCantidadSalidaAlmacen(rs.getFloat("CANTIDAD") - rs.getFloat("CANTIDAD_ENTREGADA"));
                salidasAlmacenDetalle.getUnidadesMedida().setCodUnidadMedida(rs.getInt("COD_UNIDAD_MEDIDA"));
                salidasAlmacenDetalle.getUnidadesMedida().setNombreUnidadMedida(rs.getString("NOMBRE_UNIDAD_MEDIDA"));
                salidasAlmacenDetalle.getUnidadesMedida().setAbreviatura(rs.getString("ABREVIATURA"));
                salidasAlmacenDetalle.getEstadosMaterial().setCodEstadoMaterial(2); //aprobado
                //if (this.solicitudConEtiquetas(solicitudesSalida) == false) {
                //    System.out.println("entro 1");
                    this.etiquetasSalidaAlmacen(salidasAlmacenDetalle);//aqui se debe filtrar el detalle de etiquetas para la solicitude de salida -cantidad predeterminada
                //} else {
                  //  System.out.println("entro 2");
                    //this.etiquetasSalidaAlmacenPredeterminado(salidasAlmacenDetalle, solicitudesSalida);
                //}
                this.detalleEtiquetasSalidaAlmacen(salidasAlmacenDetalle);
                //List solicitudesSalidaAlmacenDetalleSolicitudList = this.solicitudesSalidaAlmacenDetalleSolicitud(salidasAlmacenDetalle, solicitudesSalida);
                /*if (solicitudesSalidaAlmacenDetalleSolicitudList.size() > 0) {
                    salidasAlmacenDetalle.setSalidasAlmacenDetalleIngresoList(solicitudesSalidaAlmacenDetalleSolicitudList);
                }*/
                if (salidasAlmacenDetalle.getCantidadSalidaAlmacen() > salidasAlmacenDetalle.getCantidadDisponible()) {
                    salidasAlmacenDetalle.setColorFila("#FF0000");
                } else {
                    salidasAlmacenDetalle.setColorFila("#00FF00");
                }
                salidasAlmacenDetalleList.add(salidasAlmacenDetalle);

                //SolicitudesSalidaDetalle solicitudesSalidaDetalle = new SolicitudesSalidaDetalle();
                //solicitudesSalidaDetalle.getMateriales().setCodMaterial(rs.getString("COD_MATERIAL"));
                //solicitudesSalidaDetalle.getMateriales().setNombreMaterial(rs.getString("NOMBRE_MATERIAL"));
                //solicitudesSalidaDetalle.setCantidad(rs.getFloat("CANTIDAD"));
                //solicitudesSalidaDetalle.getUnidadesMedida().setCodUnidadMedida(rs.getInt("COD_UNIDAD_MEDIDA"));
                //solicitudesSalidaDetalle.getUnidadesMedida().setNombreUnidadMedida(rs.getString("NOMBRE_UNIDAD_MEDIDA"));
                //solicitudesSalidaDetalle.setCantidadEntregada(rs.getFloat("CANTIDAD_ENTREGADA"));
                //solicitudesSalidaDetalleList.add(solicitudesSalidaDetalle);
            }
            rs.close();
            st.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return salidasAlmacenDetalleList;
    }
    
   
    public boolean solicitudConEtiquetas(SolicitudesSalida solicitudesSalida) {
        boolean solicitudConEtiquetas = false;
        try {
            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement();
            String consulta = " select top 1 * from SOLICITUDES_SALIDA_DETALLE_SOLICITUD s where s.COD_FORM_SALIDA = '" + solicitudesSalida.getCodFormSalida() + "' ";
            ResultSet rs = st.executeQuery(consulta);
            if (rs.next()) {
                solicitudConEtiquetas = true;
            }
            rs.close();
            st.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return solicitudConEtiquetas;
    }

    public List solicitudesSalidaAlmacenDetalleSolicitud(SalidasAlmacenDetalle salidasAlmacenDetalle, SolicitudesSalida solicitudesSalida) {
        List salidaAlmacenDetalleIngresoList = new ArrayList();
        try {
            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement();
            String consulta = "  SELECT COD_FORM_SALIDA,  COD_INGRESO_ALMACEN,  COD_MATERIAL,  ETIQUETA,  CANTIDAD FROM "
                    + " SOLICITUDES_SALIDA_DETALLE_SOLICITUD where cod_form_salida = '" + solicitudesSalida.getCodFormSalida() + "'";
            System.out.println("consulta " + consulta);
            ResultSet rs = st.executeQuery(consulta);
            while (rs.next()) {
                Iterator i = salidasAlmacenDetalle.getSalidasAlmacenDetalleIngresoList().iterator();
                while (i.hasNext()) {
                    SalidasAlmacenDetalleIngreso salidasAlmacenDetalleIngreso = (SalidasAlmacenDetalleIngreso) i.next();
//                        System.out.println("valores comparados" +
//                                salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getIngresosAlmacen().getCodIngresoAlmacen()
//                                +" " +rs.getInt("cod_ingreso_almacen")
//                                +"  "+ salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getMateriales().getCodMaterial()
//                                +" "+rs.getString("cod_material")
//                                +" "+ salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getEtiqueta()+"  "+rs.getInt("etiqueta"));
                    if (salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getIngresosAlmacen().getCodIngresoAlmacen() == rs.getInt("cod_ingreso_almacen")
                            && salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getMateriales().getCodMaterial().equals(rs.getString("cod_material"))
                            && salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getEtiqueta() == rs.getInt("etiqueta")) {
                        //salidasAlmacenDetalleIngreso.setCantidad(rs.getFloat("cantidad"));
                        SalidasAlmacenDetalleIngreso copia = new SalidasAlmacenDetalleIngreso();
                        copia.getIngresosAlmacenDetalleEstado().getMateriales().setCodMaterial(salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getMateriales().getCodMaterial());
                        copia.getIngresosAlmacenDetalleEstado().setEtiqueta(salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getEtiqueta());
                        copia.getIngresosAlmacenDetalleEstado().getEstadosMaterial().setCodEstadoMaterial(salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getEstadosMaterial().getCodEstadoMaterial());
                        copia.getIngresosAlmacenDetalleEstado().getEstadosMaterial().setNombreEstadoMaterial(salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getEstadosMaterial().getNombreEstadoMaterial());
                        copia.getIngresosAlmacenDetalleEstado().getEmpaqueSecundarioExterno().setCodEmpaqueSecundarioExterno(salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getEmpaqueSecundarioExterno().getCodEmpaqueSecundarioExterno());
                        copia.getIngresosAlmacenDetalleEstado().setFechaVencimiento(salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getFechaVencimiento());
                        copia.getIngresosAlmacenDetalleEstado().getIngresosAlmacen().setNroIngresoAlmacen(salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getIngresosAlmacen().getNroIngresoAlmacen());
                        copia.getIngresosAlmacenDetalleEstado().getIngresosAlmacenDetalle().getSecciones().setNombreSeccion(salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getIngresosAlmacenDetalle().getSecciones().getNombreSeccion());
                        copia.getIngresosAlmacenDetalleEstado().getEmpaqueSecundarioExterno().setNombreEmpaqueSecundarioExterno(salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getEmpaqueSecundarioExterno().getNombreEmpaqueSecundarioExterno());
                        copia.getIngresosAlmacenDetalleEstado().setLoteMaterialProveedor(salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getLoteMaterialProveedor());
                        copia.getIngresosAlmacenDetalleEstado().getIngresosAlmacenDetalle().setCostoUnitario(0);
                        copia.setCantidad(salidasAlmacenDetalleIngreso.getCantidad());
                        copia.setCantidadDisponible(salidasAlmacenDetalleIngreso.getCantidadDisponible());
                        copia.getIngresosAlmacenDetalleEstado().getEstanteAlmacen().setNombreEstante(salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getEstanteAlmacen().getNombreEstante());
                        copia.getIngresosAlmacenDetalleEstado().setFila(salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getFila());
                        copia.getIngresosAlmacenDetalleEstado().setColumna(salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getColumna());
                        copia.setCantidadSacar(salidasAlmacenDetalleIngreso.getCantidadSacar());
                        copia.getIngresosAlmacenDetalleEstado().getIngresosAlmacen().setCodIngresoAlmacen(salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getIngresosAlmacen().getCodIngresoAlmacen());
                        copia.setCantidadSacar(rs.getFloat("cantidad"));
                        salidaAlmacenDetalleIngresoList.add(copia);

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return salidaAlmacenDetalleIngresoList;
    }

    public int generaNroIngresoAlmacen() {
        int nroIngresoAlmacen = 0;
        ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select isnull(MAX(nro_salida_almacen),0)+1 nro_salida_almacen from salidas_almacen "
                    + " where cod_gestion='" + usuario.getGestionesGlobal().getCodGestion() + "' "
                    + " and cod_almacen='" + usuario.getAlmacenesGlobal().getCodAlmacen() + "' and estado_sistema=1   ";
            System.out.println("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            if (rs.next()) {
                nroIngresoAlmacen = rs.getInt("nro_salida_almacen");
            }
            con.close();
            st.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nroIngresoAlmacen;
    }

    public List cargarAreasEmpresa() {
        List areasEmpresaList = new ArrayList();
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String sql = "  select a.COD_AREA_EMPRESA,a.NOMBRE_AREA_EMPRESA  "
                    + " from AREAS_EMPRESA a  where a.COD_ESTADO_REGISTRO = 1 order by a.NOMBRE_AREA_EMPRESA asc";

            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(sql);
            areasEmpresaList.clear();
            areasEmpresaList.add(new SelectItem("0", "-NINGUNO-"));
            while (rs.next()) {
                areasEmpresaList.add(new SelectItem(rs.getString("COD_AREA_EMPRESA"), rs.getString("NOMBRE_AREA_EMPRESA")));
            }

            if (rs != null) {
                rs.close();
                st.close();
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return areasEmpresaList;
    }

    public void cargarComponentesProd(AreasEmpresa areasEmpresa) {
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String sql = " select cp.COD_COMPPROD,cp.nombre_prod_semiterminado  "
                    + " from COMPONENTES_PROD cp where cp.COD_ESTADO_COMPPROD=1";
            if (!areasEmpresa.getCodAreaEmpresa().equals("")) {
                sql = sql + " and cp.cod_area_empresa = '" + areasEmpresa.getCodAreaEmpresa() + "' order by nombre_prod_semiterminado ";
            };
            System.out.println("consulta " + sql);

            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(sql);
            componentesProdList.clear();
            componentesProdList.add(new SelectItem("0", "-NINGUNO-"));
            while (rs.next()) {
                componentesProdList.add(new SelectItem(rs.getString("COD_COMPPROD"), rs.getString("nombre_prod_semiterminado")));
            }
            if (rs != null) {
                rs.close();
                st.close();
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List cargarTiposSalidasAlmacen() {
        List tiposSalidasAlmacenList = new ArrayList();
        try {
            Connection con = null;
            con = Util.openConnection(con);

            String sql = " select s.COD_TIPO_SALIDA_ALMACEN,s.NOMBRE_TIPO_SALIDA_ALMACEN  "
                    + " from tipos_salidas_almacen s "
                    + " order by nombre_tipo_salida_almacen ";

            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(sql);
            tiposSalidasAlmacenList.clear();
            tiposSalidasAlmacenList.add(new SelectItem("0", "-NINGUNO-"));
            while (rs.next()) {
                tiposSalidasAlmacenList.add(new SelectItem(rs.getString("COD_TIPO_SALIDA_ALMACEN"), rs.getString("NOMBRE_TIPO_SALIDA_ALMACEN")));
            }

            if (rs != null) {
                rs.close();
                st.close();
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tiposSalidasAlmacenList;
    }

    public List cargarCapitulos() {
        List capitulosList = new ArrayList();
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select c.COD_CAPITULO,c.NOMBRE_CAPITULO  from CAPITULOS c where c.COD_ESTADO_REGISTRO = 1 ";

            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            capitulosList.clear();
            capitulosList.add(new SelectItem("0", "-NINGUNO-"));
            while (rs.next()) {
                capitulosList.add(new SelectItem(rs.getString("COD_CAPITULO"), rs.getString("NOMBRE_CAPITULO")));
            }
            if (rs != null) {
                rs.close();
                st.close();
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return capitulosList;
    }

    public List cargarEstadosMaterial() {
        List estadosMaterialList = new ArrayList();
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select cod_estado_material, nombre_estado_material "
                    + " from estados_material where cod_estado_registro=1 and cod_estado_material<>7 ";

            System.out.println("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            estadosMaterialList.clear();
            estadosMaterialList.add(new SelectItem("0", "-NINGUNO-"));
            while (rs.next()) {
                estadosMaterialList.add(new SelectItem(rs.getString("cod_estado_material"), rs.getString("nombre_estado_material")));
            }
            rs.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return estadosMaterialList;
    }

    public String verDetalleEtiquetasSalidaAlmacen_action() {
        try {

            salidasAlmacenDetalle = (SalidasAlmacenDetalle) salidasAlmacenDetalleDataTable.getRowData();
            float cantidadSalidaAlmacen = salidasAlmacenDetalle.getCantidadSalidaAlmacen();
            /*
             Iterator i =  salidasAlmacenDetalle.getSalidasAlmacenDetalleIngresoList().iterator();
             while(i.hasNext()){
             SalidasAlmacenDetalleIngreso salidasAlmacenDetalleIngreso = (SalidasAlmacenDetalleIngreso)i.next();
             if(cantidadSalidaAlmacen>0){
             if(cantidadSalidaAlmacen>=salidasAlmacenDetalleIngreso.getCantidadDisponible()){
             salidasAlmacenDetalleIngreso.setCantidadSacar(salidasAlmacenDetalleIngreso.getCantidadDisponible());
             cantidadSalidaAlmacen = cantidadSalidaAlmacen-salidasAlmacenDetalleIngreso.getCantidadDisponible();
             }else{
             salidasAlmacenDetalleIngreso.setCantidadSacar(cantidadSalidaAlmacen);
             cantidadSalidaAlmacen = cantidadSalidaAlmacen-salidasAlmacenDetalleIngreso.getCantidadDisponible();
             }
             }else{
             salidasAlmacenDetalleIngreso.setCantidadSacar(0);
             }
             }
             */

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String siguiente_action() {
        super.next();
        solicitudesSalidaAlmacenList = this.cargarSolicitudesSalidaAlmacen();
        // this.cargarContenidoIngresoAlmacenOrdenCompra();
        return "";
    }

    public String atras_action() {
        super.back();
        solicitudesSalidaAlmacenList = this.cargarSolicitudesSalidaAlmacen();
        // this.cargarContenidoIngresoAlmacenOrdenCompra();
        return "";
    }

    public List cargarPersonal() {
        List personalList = new ArrayList();
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select p.COD_PERSONAL,p.NOMBRE_PILA,p.AP_PATERNO_PERSONAL,p.AP_MATERNO_PERSONAL from personal p where p.COD_ESTADO_PERSONA = 1 order by p.ap_paterno_personal asc";
            System.out.println("consulta" + consulta);

            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            personalList.clear();
            personalList.add(new SelectItem("0", "-NINGUNO-"));
            while (rs.next()) {
                personalList.add(new SelectItem(rs.getString("cod_personal"), rs.getString("ap_paterno_personal") + " " + rs.getString("ap_materno_personal") + " " + rs.getString("nombre_pila")));
            }
            if (rs != null) {
                rs.close();
                st.close();
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return personalList;
    }

    public List cargarEstadosSolicitudSalidaAlmacen() {
        List estadosSolicitudList = new ArrayList();
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select s.COD_ESTADO_SOLICITUD_SALIDA_ALMACEN,s.NOMBRE_ESTADO_SOLICITUD_SALIDA_ALMACEN from ESTADOS_SOLICITUD_SALIDAS_ALMACEN s where s.COD_ESTADO_REGISTRO = 1 ";
            System.out.println("consulta" + consulta);

            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            estadosSolicitudList.clear();
            estadosSolicitudList.add(new SelectItem("0", "-NINGUNO-"));
            while (rs.next()) {
                estadosSolicitudList.add(new SelectItem(rs.getString("cod_estado_solicitud_salida_almacen"), rs.getString("nombre_estado_solicitud_salida_almacen")));
            }
            if (rs != null) {
                rs.close();
                st.close();
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return estadosSolicitudList;
    }

    public String buscarSolicitudesSalidaAlmacen_action() {
        solicitudesSalidaAlmacenList = this.cargarSolicitudesSalidaAlmacen();
        return null;
    }

    public String verReporteSolicitudSalidaAlmacen_action() {
        SolicitudesSalida solicitudesSalida = (SolicitudesSalida) solicitudesSalidaAlmacenDataTable.getRowData();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext ec = facesContext.getExternalContext();
        Map map = ec.getSessionMap();
        map.put("solicitudesSalida", solicitudesSalida);
        return null;
    }

    //inicio ale buscar

    private void cargarBuscarMaterial() {
        try {
            String consulta = "select c.COD_CAPITULO,c.NOMBRE_CAPITULO from CAPITULOS c where c.COD_ESTADO_REGISTRO=1 order by c.NOMBRE_CAPITULO";
            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet res = st.executeQuery(consulta);
            listaCapitulos.clear();
            listaCapitulos.add(new SelectItem(-1, "-TODOS-"));
            listaGrupos.clear();
            listaItems.clear();
            listaGrupos.add(new SelectItem(-1, "-TODOS-"));
            listaItems.add(new SelectItem("-1", "-TODOS-"));
            while (res.next()) {
                listaCapitulos.add(new SelectItem(res.getInt("COD_CAPITULO"), res.getString("NOMBRE_CAPITULO")));
            }
            materialBuscar.setCodMaterial("-1");
            materialBuscar.getGrupos().setCodGrupo(-1);
            materialBuscar.getGrupos().getCapitulos().setCodCapitulo(-1);
            res.close();
            st.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public String onChangeCapitulo() {
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = "select g.COD_GRUPO,g.NOMBRE_GRUPO from grupos g where g.COD_CAPITULO='" + materialBuscar.getGrupos().getCapitulos().getCodCapitulo() + "' order by g.NOMBRE_GRUPO";
            System.out.println("consulta grupos " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet res = st.executeQuery(consulta);
            listaGrupos.clear();
            listaItems.clear();
            listaGrupos.add(new SelectItem(-1, "-TODOS-"));
            listaItems.add(new SelectItem("-1", "-TODOS-"));
            while (res.next()) {
                listaGrupos.add(new SelectItem(res.getInt("COD_GRUPO"), res.getString("NOMBRE_GRUPO")));
            }
            res.close();
            st.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public String onChangeGrupo() {
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = "select m.COD_MATERIAL,m.NOMBRE_MATERIAL from MATERIALES m where m.COD_GRUPO='" + materialBuscar.getGrupos().getCodGrupo() + "' order by m.NOMBRE_MATERIAL";
            System.out.println("consulta materiales " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            System.out.println("consulta items " + consulta);
            ResultSet res = st.executeQuery(consulta);
            listaItems.clear();
            listaItems.add(new SelectItem("-1", "-TODOS-"));
            while (res.next()) {
                listaItems.add(new SelectItem(res.getString("COD_MATERIAL"), res.getString("NOMBRE_MATERIAL")));
            }
            res.close();
            st.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    //final ale buscar

    public String getCargarAgregarSolicitudMaterialesOT() {
        try {
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            Map<String, Object> sessionMap = externalContext.getSessionMap();
            solicitudMantenimiento = (SolicitudMantenimiento) sessionMap.get("solicitudMantenimiento");
            String consulta = " SELECT S.CANTIDAD,M.COD_UNIDAD_MEDIDA,M.COD_MATERIAL, M.NOMBRE_MATERIAL,U.COD_UNIDAD_MEDIDA,U.NOMBRE_UNIDAD_MEDIDA,U.ABREVIATURA,S.DESCRIPCION"
                    + "  FROM SOLICITUDES_MANTENIMIENTO_DETALLE_MATERIALES S "
                    + " INNER JOIN MATERIALES M ON M.COD_MATERIAL = S.COD_MATERIAL"
                    + " INNER JOIN UNIDADES_MEDIDA U ON U.COD_UNIDAD_MEDIDA = S.COD_UNIDAD_MEDIDA"
                    + " WHERE S.COD_SOLICITUD_MANTENIMIENTO ='" + solicitudMantenimiento.getCodSolicitudMantenimiento() + "' ";
            System.out.println("consulta " + consulta);
            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(consulta);
            solicitudesSalidaDetalleList.clear();
            while (rs.next()) {
                SolicitudesSalidaDetalle s = new SolicitudesSalidaDetalle();
                s.setCantidad(rs.getFloat("cantidad"));
                s.getMateriales().setCodMaterial(rs.getString("cod_material"));
                s.getMateriales().setNombreMaterial(rs.getString("nombre_material"));
                s.getUnidadesMedida().setCodUnidadMedida(rs.getInt("cod_unidad_medida"));
                s.getUnidadesMedida().setNombreUnidadMedida(rs.getString("nombre_unidad_medida"));
                s.getUnidadesMedida().setAbreviatura(rs.getString("abreviatura"));
                //s.setDescr(rs.getString("descripcion"));
                solicitudesSalidaDetalleList.add(s);
            }
            rs.close();
            st.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String generarSolicitudMateriales_action() {
        try {
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            Map<String, Object> sessionMap = externalContext.getSessionMap();
            sessionMap.put("solicitudesSalidaDetalleList", solicitudesSalidaDetalleList);
            sessionMap.put("solicitudMantenimiento", solicitudMantenimiento);
            Utiles.direccionar("../solicitudesSalidaAlmacen/agregarSolicitudSalidaAlmacen.jsf?cod=" + Math.random());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<IngresosAlmacenDetalle> getIngresosAlmacenDetalleSalidaList() {
        return ingresosAlmacenDetalleSalidaList;
    }

    public boolean isAdministradorAlmacen() {
        return administradorAlmacen;
    }

    public void setAdministradorAlmacen(boolean administradorAlmacen) {
        this.administradorAlmacen = administradorAlmacen;
    }

    private TraspasosSolicitud traspasos_solicitud = new TraspasosSolicitud();

    public TraspasosSolicitud getTraspasos_solicitud() {
        return traspasos_solicitud;
    }

    public void setTraspasos_solicitud(TraspasosSolicitud traspasos_solicitud) {
        this.traspasos_solicitud = traspasos_solicitud;
    }
     public TraspasosSolicitud cargarTraspaso(SolicitudesSalida solicitudesSalida) {
        TraspasosSolicitud traspaso = new TraspasosSolicitud();
        try {
            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement();
            String consulta = " select cod_form_salida, cod_almacen_destino"
                    + ", cod_tipo_traspaso,cod_estado_solicitud_traspaso "
                    + " from TRASPASOS_SOLICITUDES"
                    + " where COD_FORM_SALIDA = '" + solicitudesSalida.getCodFormSalida() + "'";
            System.out.println("consulta " + consulta);
            ResultSet rs = st.executeQuery(consulta);
          
            while (rs.next()) {
                traspaso.getAlmacenDestino().setCodAlmacen(rs.getInt("cod_almacen_destino"));
                traspaso.setCod_form_salida(rs.getInt("cod_form_salida"));
                traspaso.getEstadosTraspaso().setCodEstadoTraspaso(rs.getInt("cod_estado_solicitud_traspaso"));
                traspaso.getTipoTraspaso().setCodTipoTraspaso(rs.getString("cod_tipo_traspaso"));
            }
            rs.close();
            st.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return traspaso;
    }
     
     private List<SelectItem> almacenesList = new ArrayList<SelectItem>();

    public List<SelectItem> getAlmacenesList() {
        return almacenesList;
    }

    public void setAlmacenesList(List<SelectItem> almacenesList) {
        this.almacenesList = almacenesList;
    }
     
public void cargarAlmacenes() {
        try {
 Connection con = null;
            con = Util.openConnection(con);
            String sql = "select a.COD_ALMACEN,a.NOMBRE_ALMACEN from ALMACENES a";
            sql += " where a.COD_ESTADO_REGISTRO=1 order by a.NOMBRE_ALMACEN";
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(sql);
            getAlmacenesList().clear();
            getAlmacenesList().add(new SelectItem("0", "-TODOS-"));
            while (rs.next()) {
                getAlmacenesList().add(new SelectItem(rs.getString("COD_ALMACEN"), rs.getString("NOMBRE_ALMACEN")));
            }
            if (rs != null) {
                rs.close();
                st.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
