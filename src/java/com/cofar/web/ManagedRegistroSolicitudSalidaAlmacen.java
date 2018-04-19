/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofar.web;

import com.cofar.bean.ComponentesProd;
import com.cofar.bean.Equivalencias;
import com.cofar.bean.EstadosMaterial;
import com.cofar.bean.FormulaMaestraDetalleEP;
import com.cofar.bean.FormulaMaestraDetalleES;
import com.cofar.bean.FormulaMaestraDetalleMP;
import com.cofar.bean.IngresosAlmacenDetalle;
import com.cofar.bean.IngresosAlmacenDetalleEstado;
import com.cofar.bean.Materiales;
import com.cofar.bean.ProgramaProduccion;
import com.cofar.bean.SalidasAlmacenDetalle;
import com.cofar.bean.SolicitudMantenimiento;
import com.cofar.bean.SolicitudesSalida;
import com.cofar.bean.SolicitudesSalidaDetalle;
import com.cofar.bean.TraspasosSolicitud;
import com.cofar.dao.DaoEstadosMaterial;
import com.cofar.util.Util;
import java.math.BigDecimal;
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
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//import org.codehaus.groovy.ast.stmt.CatchStatement;
import org.richfaces.component.html.HtmlDataTable;

/**
 *
 * @author hvaldivia
 */
@SuppressWarnings("CallToPrintStackTrace")
public class ManagedRegistroSolicitudSalidaAlmacen extends ManagedBean {

    private Logger LOGGER; 
    private static int COD_TIPO_SOLICITUD_MANUAL = 2;
    private static int COD_TIPO_SOLICITUD_TRASPASO = 3;
    private Materiales materialSeleccionado;
    private List<EstadosMaterial> estadosMaterialExistenciaList = new ArrayList<>();
    List solicitudesSalidaAlmacenList = new ArrayList();
    List tiposSolicitudesSalidaAlmacenList = new ArrayList();
    int codTipoSolicitudSalidaAlmacen = 0;
    String direccionPagina = "";
    List areasEmpresaList = new ArrayList();
    ManagedSalidaAlmacen managedSalidaAlmacen = new ManagedSalidaAlmacen();
    SolicitudesSalida solicitudesSalida = new SolicitudesSalida();
    List capitulosList = new ArrayList();
    private Materiales buscarMaterial = new Materiales();
    List gruposList = new ArrayList();
    private List materialesBuscarList = new ArrayList();
    List solicitudesSalidaAlmacenDetalleList = new ArrayList();
    private HtmlDataTable materialesBuscarDataTable = new HtmlDataTable();
    SalidasAlmacenDetalle salidasAlmacenDetalleAgregar = new SalidasAlmacenDetalle();
    IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstado = new IngresosAlmacenDetalleEstado();
    List estadosMaterialList = new ArrayList();
    ManagedSolicitudSalidaAlmacen managedSolicitudSalidaAlmacen = new ManagedSolicitudSalidaAlmacen();
    String mensaje = "";
    SalidasAlmacenDetalle salidasAlmacenDetalle = new SalidasAlmacenDetalle();
    HtmlDataTable solicitudesSalidaAlmacenDataTable = new HtmlDataTable();
    List componentesProdList = new ArrayList();
    List presentacionesList = new ArrayList();
    SolicitudesSalidaDetalle solicitudesSalidaDetalle = new SolicitudesSalidaDetalle();

    private List<SelectItem> gestionesList = new ArrayList<SelectItem>();
    private List<SelectItem> tiposSalidasList2 = new ArrayList<SelectItem>();
    private List<SelectItem> estadosSolicitudList = new ArrayList<SelectItem>();
    private List<SelectItem> areasEmpresaList2 = new ArrayList<SelectItem>();
    private SolicitudesSalida solicitudesSalidaBuscar = new SolicitudesSalida();
    private Date fechaInicio = new Date();
    private java.util.Date fechaFinal = new Date();
    private List<SelectItem> almacenesList = new ArrayList<SelectItem>();
    private List almacenSolicitudList = new ArrayList();
    ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
    private boolean registroManual = false;
    private boolean solicitudTraspaso = false;
    private ProgramaProduccion programaProduccion = new ProgramaProduccion();
    private SolicitudMantenimiento solicitudMantenimiento = new SolicitudMantenimiento();
    private List solicitudesSalidaAlmacenAprobarList = new ArrayList();
    private HtmlDataTable solicitudesSalidaAlmacenAprobarDataTable = new HtmlDataTable();
    private List maquinariaList = new ArrayList();
    //solicitud de salida por lote proovedor
    private SolicitudesSalida solicitudesSalidaLoteProovedorAgregar = null;
    private String loteProovedor = "";
    private List<SelectItem> materialesSelectList = new ArrayList<SelectItem>();
    private List<IngresosAlmacenDetalleEstado> ingresosAlmacenDetalleEstadoList = null;
    private List<IngresosAlmacenDetalle> solicitudDetalleEstadoList = null;
    private List<SelectItem> estadosMaterialSelectList = new ArrayList<SelectItem>();
    private int codEstadoMaterial = 0;
    private int codExplosionEmpaqueSecundarioAlmacen = 0;
    private List<SelectItem> almacenDestinoTraspasoList;
    // <editor-fold defaultstate="collapsed" desc="variables para solicitud por reacondicionamiento">
        private List<SelectItem> presentacionesProductoSelectList;
        private SolicitudesSalida solicitudesSalidaReacondicionamiento;
        private SolicitudesSalidaDetalle solicitudesSalidaDetalleEliminar;
    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="para buscar y seleccionar lote">
    private ProgramaProduccion programaProduccionBuscar;
    private List<ProgramaProduccion> programaProduccionList;
    private HtmlDataTable programaProduccionDataTable = new HtmlDataTable();

    public String seleccionLoteProduccion_action() {
        ProgramaProduccion bean = (ProgramaProduccion) programaProduccionDataTable.getRowData();
        if (bean.getFormulaMaestra().getComponentesProd().getCodCompprod().length() > 0) {
            solicitudesSalida.setCodLoteProduccion(bean.getCodLoteProduccion());
            solicitudesSalida.getComponentesProd().setNombreProdSemiterminado(bean.getFormulaMaestra().getComponentesProd().getNombreProdSemiterminado());
            solicitudesSalida.getComponentesProd().setCodCompprod(bean.getFormulaMaestra().getComponentesProd().getCodCompprod());
            solicitudesSalida.getAreaDestinoSalida().setCodAreaEmpresa(bean.getFormulaMaestra().getComponentesProd().getAreasEmpresa().getCodAreaEmpresa());
            solicitudesSalida.getAreaDestinoSalida().setNombreAreaEmpresa(bean.getFormulaMaestra().getComponentesProd().getAreasEmpresa().getNombreAreaEmpresa());
            area_change();
        } else {
            solicitudesSalida.getAreaDestinoSalida().setCodAreaEmpresa("0");
            solicitudesSalida.getAreaDestinoSalida().setNombreAreaEmpresa("");
            solicitudesSalida.setCodLoteProduccion("");
            solicitudesSalida.getComponentesProd().setNombreProdSemiterminado("");
            solicitudesSalida.getComponentesProd().setCodCompprod("");
        }
        producto_changed();
        return null;
    }

    public String buscarLoteProduccion_action() {
        try {
            Connection con = null;
            con = Util.openConnection(con);
            StringBuilder consulta = new StringBuilder("select ppp.COD_PROGRAMA_PROD,ppp.NOMBRE_PROGRAMA_PROD,cp.COD_COMPPROD,cp.nombre_prod_semiterminado,pp.COD_LOTE_PRODUCCION,pp.CANT_LOTE_PRODUCCION");
            consulta.append(" ,ae.COD_AREA_EMPRESA,ae.NOMBRE_AREA_EMPRESA");
            consulta.append(" from PROGRAMA_PRODUCCION pp inner join PROGRAMA_PRODUCCION_PERIODO ppp on pp.COD_PROGRAMA_PROD=ppp.COD_PROGRAMA_PROD");
            consulta.append(" inner join COMPONENTES_PROD cp on cp.COD_COMPPROD=pp.COD_COMPPROD");
            consulta.append(" inner join AREAS_EMPRESA ae on ae.COD_AREA_EMPRESA=cp.COD_AREA_EMPRESA");
            consulta.append(" where pp.COD_LOTE_PRODUCCION like '%").append(programaProduccionBuscar.getCodLoteProduccion()).append("%'");
            consulta.append(" and ppp.COD_ESTADO_PROGRAMA<>4");
            consulta.append(" order by cp.nombre_prod_semiterminado");
            System.out.println("consulta buscar datos lote " + consulta.toString());
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet res = st.executeQuery(consulta.toString());
            programaProduccionList = new ArrayList<ProgramaProduccion>();
            ProgramaProduccion bean = new ProgramaProduccion();
            bean.setCodLoteProduccion("Sin Lote");
            programaProduccionList.add(bean);
            while (res.next()) {
                ProgramaProduccion nuevo = new ProgramaProduccion();
                nuevo.setCodLoteProduccion(res.getString("COD_LOTE_PRODUCCION"));
                nuevo.getFormulaMaestra().getComponentesProd().setCodCompprod(res.getString("COD_COMPPROD"));
                nuevo.getFormulaMaestra().getComponentesProd().setNombreProdSemiterminado(res.getString("nombre_prod_semiterminado"));
                nuevo.setCantidadLote(res.getString("CANT_LOTE_PRODUCCION"));
                nuevo.getFormulaMaestra().getComponentesProd().getAreasEmpresa().setCodAreaEmpresa(res.getString("COD_AREA_EMPRESA"));
                nuevo.getFormulaMaestra().getComponentesProd().getAreasEmpresa().setNombreAreaEmpresa(res.getString("NOMBRE_AREA_EMPRESA"));
                programaProduccionList.add(nuevo);
            }
            st.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    //</editor-fold>

    private void cargarAlmacenDestinoTraspasoList(){
        Connection con = null;
        ManagedAccesoSistema managed = (ManagedAccesoSistema)Util.getSessionBean("ManagedAccesoSistema");
        try {
            con = Util.openConnection(con);
            StringBuilder consulta = new StringBuilder("SELECT a.COD_ALMACEN,a.NOMBRE_ALMACEN")
                                            .append(" FROM ALMACENES a")
                                            .append(" where a.COD_ESTADO_REGISTRO=1")
                                                .append(" and a.COD_ALMACEN<>").append(managed.getAlmacenesGlobal().getCodAlmacen())
                                            .append(" order by a.NOMBRE_ALMACEN");
            LOGGER.debug("consulta cargar almacenes traspaso : "+consulta.toString());
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet res=st.executeQuery(consulta.toString());
            almacenDestinoTraspasoList = new ArrayList<>();
            while(res.next()){
                almacenDestinoTraspasoList.add(new SelectItem(res.getInt("COD_ALMACEN"),res.getString("NOMBRE_ALMACEN")));
            }
            con.close();
        } catch (SQLException ex) {
            LOGGER.warn(ex.getMessage());
        } catch (Exception ex) {
            LOGGER.warn(ex.getMessage());
        }
    }
    private void cargarEstadosMaterialSelectList() {
        Connection con = null;
        try {
            con = Util.openConnection(con);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String consulta = "select e.COD_ESTADO_MATERIAL,e.NOMBRE_ESTADO_MATERIAL from ESTADOS_MATERIAL e where e.COD_ESTADO_MATERIAL not in (7)"
                    + " order by e.NOMBRE_ESTADO_MATERIAL";
            ResultSet res = st.executeQuery(consulta);
            estadosMaterialSelectList.clear();
            while (res.next()) {
                estadosMaterialSelectList.add(new SelectItem(res.getInt("COD_ESTADO_MATERIAL"), res.getString("NOMBRE_ESTADO_MATERIAL")));
            }
            res.close();
            st.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public String agregarIngresoAlmacenDetalleEstado_action() {
        buscarMaterial = new Materiales();
        codEstadoMaterial = 1;
        this.onChangeCapitulo();
        ingresosAlmacenDetalleEstadoList = new ArrayList<IngresosAlmacenDetalleEstado>();
        ingresosAlmacenDetalleEstadoList.clear();
        return null;
    }

    public String guardarSolicitudSalidaLoteProveedor_action() throws SQLException {
        Connection con = null;
        try {
            con = Util.openConnection(con);

            ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            int codSolicitud = this.generaCodSolicitudSalidaAlmacen();
            con.setAutoCommit(false);
            mensaje = "";
            String consulta = " INSERT INTO SOLICITUDES_SALIDA(  COD_GESTION,  COD_FORM_SALIDA,  COD_TIPO_SALIDA_ALMACEN,  COD_ESTADO_SOLICITUD_SALIDA_ALMACEN,"
                    + "  SOLICITANTE,  AREA_DESTINO_SALIDA,  FECHA_SOLICITUD,  COD_LOTE_PRODUCCION,  OBS_SOLICITUD,  ESTADO_SISTEMA,"
                    + "  CONTROL_CALIDAD,  COD_INGRESO_ALMACEN,  COD_ALMACEN,  orden_trabajo,cod_compprod,cod_presentacion,SOLICITUD_POR_LOTE_PROVEEDOR) "
                    + "VALUES (  '" + usuario.getGestionesGlobal().getCodGestion() + "','" + codSolicitud + "','"+solicitudesSalidaLoteProovedorAgregar.getTipoSolicitud().getCodTipoSolicitudSalida()+"'"
                    + ",'1','" + usuario.getUsuarioModuloBean().getCodUsuarioGlobal() + "',  '" + solicitudesSalidaLoteProovedorAgregar.getAreaDestinoSalida().getCodAreaEmpresa() + "'"
                    + ", '" + sdf.format(new Date()) + "', '', '" + solicitudesSalidaLoteProovedorAgregar.getObsSolicitud() + "', 1,"
                    + "  0,  0, '" + usuario.getAlmacenesGlobal().getCodAlmacen() + "' , ''"
                    + ",'0','0',1); ";
            System.out.println("consulta " + consulta);
            PreparedStatement pst = con.prepareStatement(consulta);
            if (pst.executeUpdate() > 0) {
                System.out.println("se registro la solicitud");
            }
            for (IngresosAlmacenDetalle bean : solicitudDetalleEstadoList) {
                consulta = " INSERT INTO SOLICITUDES_SALIDA_DETALLE(  COD_FORM_SALIDA,  COD_MATERIAL,  CANTIDAD,  CANTIDAD_ENTREGADA,"
                        + "  COD_UNIDAD_MEDIDA) VALUES (  '" + codSolicitud + "', '" + bean.getMateriales().getCodMaterial() + "',"
                        + "'" + bean.getCantTotalIngreso() + "',  0,'" + bean.getMateriales().getUnidadesMedida().getCodUnidadMedida() + "')";
                System.out.println("consulta " + consulta);
                pst = con.prepareStatement(consulta);
                if (pst.executeUpdate() > 0) {
                    System.out.println("se reigstro ls solicitud");
                }
                for (IngresosAlmacenDetalleEstado beanDetalle : ((List<IngresosAlmacenDetalleEstado>) bean.getIngresosAlmacenDetalleEstadoList())) {
                    consulta = "INSERT INTO dbo.SOLICITUDES_SALIDA_DETALLE_LOTE_PROVEEDOR(COD_FORM_SALIDA,"
                            + " COD_MATERIAL, COD_INGRESO_ALMACEN, COD_ESTADO_MATERIAL, COD_LOTE_PROVEEDOR,"
                            + " CANTIDAD_SOLICITUD)"
                            + " VALUES ('" + codSolicitud + "','" + beanDetalle.getMateriales().getCodMaterial() + "',"
                            + " '" + beanDetalle.getIngresosAlmacen().getCodIngresoAlmacen() + "',"
                            + " '" + beanDetalle.getEstadosMaterial().getCodEstadoMaterial() + "',"
                            + " '" + beanDetalle.getLoteMaterialProveedor() + "','" + beanDetalle.getCantidadParcial() + "')";
                    System.out.println("consulta registrar detalle proovedor " + consulta);
                    pst = con.prepareStatement(consulta);
                    if (pst.executeUpdate() > 0) {
                        System.out.println("se registro la transaccion");
                    }
                }
            }
            con.commit();
            con.close();
            mensaje = "1";
        } catch (SQLException ex) {
            mensaje = "Ocurrio un error al momento de guardar la solicitud,intente de nuevo";
            con.rollback();
            con.close();
            ex.printStackTrace();
        }

        return null;
    }

    public String onchangeGrupoCapitulo() {
        try {
            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String codigosMateriales = "";
            for (IngresosAlmacenDetalle bean : solicitudDetalleEstadoList) {
                codigosMateriales += (codigosMateriales.equals("") ? "" : ",") + bean.getMateriales().getCodMaterial();
            }

            String consulta = "select m.COD_MATERIAL,m.NOMBRE_MATERIAL"
                    + " from materiales m inner join grupos g on g.COD_GRUPO=m.COD_GRUPO"
                    + " where m.COD_MATERIAL not in (-1)"
                    + (buscarMaterial.getGrupos().getCodGrupo() > 0 ? " and m.COD_GRUPO='" + buscarMaterial.getGrupos().getCodGrupo() + "'" : "")
                    + (buscarMaterial.getGrupos().getCapitulos().getCodCapitulo() > 0 ? " and g.COD_CAPITULO='" + buscarMaterial.getGrupos().getCapitulos().getCodCapitulo() + "'" : "")
                    + (codigosMateriales.equals("") ? "" : " and m.COD_MATERIAL not IN (" + codigosMateriales + ")")
                    + " order by m.NOMBRE_MATERIAL";
            System.out.println("consulta" + consulta);
            ResultSet res = st.executeQuery(consulta);
            materialesSelectList.clear();
            while (res.next()) {
                materialesSelectList.add(new SelectItem(res.getString("COD_MATERIAL"), res.getString("NOMBRE_MATERIAL")));
            }
            res.close();
            st.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public String adicionarDetalleLoteSalida_action() {
        IngresosAlmacenDetalle nuevo = new IngresosAlmacenDetalle();
        for (IngresosAlmacenDetalleEstado bean : ingresosAlmacenDetalleEstadoList) {
            if (bean.getChecked()) {
                nuevo.getMateriales().setCodMaterial(bean.getMateriales().getCodMaterial());
                nuevo.getMateriales().setNombreMaterial(bean.getMateriales().getNombreMaterial());
                nuevo.getMateriales().getUnidadesMedida().setCodUnidadMedida(bean.getMateriales().getUnidadesMedida().getCodUnidadMedida());
                nuevo.getMateriales().getUnidadesMedida().setAbreviatura(bean.getMateriales().getUnidadesMedida().getAbreviatura());
                nuevo.getIngresosAlmacenDetalleEstadoList().add(bean);
                nuevo.setCantTotalIngreso(nuevo.getCantTotalIngreso() + bean.getCantidadParcial());
            }
        }
        solicitudDetalleEstadoList.add(nuevo);
        return null;
    }

    public String buscarMaterialLoteProovedor_action() {
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

    private void cargarCapitulosLoteProveedor(ManagedAccesoSistema usuario) {
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select c.COD_CAPITULO,c.NOMBRE_CAPITULO "
                    + " from CAPITULOS c "
                    + "where c.COD_CAPITULO IN (" + (usuario.getAlmacenesGlobal().getCodAlmacen() == 1 ? "2,3,18" : "4,8") + ") ";
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            capitulosList.clear();
            capitulosList.add(new SelectItem("0", "-NINGUNO-"));
            while (rs.next()) {
                capitulosList.add(new SelectItem(rs.getString("COD_CAPITULO"), rs.getString("NOMBRE_CAPITULO")));
            }
            rs.close();
            st.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String eliminarSolicitudDetalle_action() {
        int cont = 0;
        for (IngresosAlmacenDetalle bean : solicitudDetalleEstadoList) {
            if (bean.getChecked()) {
                break;
            }
            cont++;
        }
        solicitudDetalleEstadoList.remove(cont);
        return null;
    }

    public String buscarIngresosEstado_action() {
        try {
            ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            Connection con = null;
            con = Util.openConnection(con);
            String codMaterial = "";
            for (IngresosAlmacenDetalle bean : solicitudDetalleEstadoList) {
                codMaterial += (codMaterial.equals("") ? "" : ",") + bean.getMateriales().getCodMaterial();
            }

            String consulta = "select ia.COD_INGRESO_ALMACEN,ia.NRO_INGRESO_ALMACEN,ia.FECHA_INGRESO_ALMACEN,sum(iade.CANTIDAD_RESTANTE) as cantidadRestante"
                    + " ,em.NOMBRE_ESTADO_MATERIAL,iade.COD_ESTADO_MATERIAL,iade.LOTE_MATERIAL_PROVEEDOR"
                    + " ,m.NOMBRE_MATERIAL,iade.COD_MATERIAL,um.COD_UNIDAD_MEDIDA,um.ABREVIATURA"
                    + " from INGRESOS_ALMACEN_DETALLE_ESTADO iade inner join INGRESOS_ALMACEN ia"
                    + " on  ia.COD_INGRESO_ALMACEN=iade.COD_INGRESO_ALMACEN"
                    + "  inner join MATERIALES m on m.COD_MATERIAL=iade.COD_MATERIAL"
                    + "  inner join grupos g on g.COD_GRUPO=m.COD_GRUPO"
                    + "  inner join capitulos c on c.COD_CAPITULO=g.COD_CAPITULO"
                    + "  inner join ESTADOS_MATERIAL em on em.COD_ESTADO_MATERIAL=iade.COD_ESTADO_MATERIAL"
                    + " and iade.CANTIDAD_RESTANTE>0.1"
                    + " inner join INGRESOS_ALMACEN_DETALLE iad on iad.COD_INGRESO_ALMACEN=iade.COD_INGRESO_ALMACEN"
                    + " and iade.COD_MATERIAL=iad.COD_MATERIAL"
                    + " left outer join UNIDADES_MEDIDA um on um.COD_UNIDAD_MEDIDA =iad.COD_UNIDAD_MEDIDA"
                    + "  where ia.COD_ESTADO_INGRESO_ALMACEN<>2"
                    + (buscarMaterial.getNombreMaterial().equals("") ? "" : " and  m.NOMBRE_MATERIAL like '%" + buscarMaterial.getNombreMaterial() + "%'")
                    + "  and ia.COD_ALMACEN='" + usuario.getAlmacenesGlobal().getCodAlmacen() + "'"
                    + (loteProovedor.equals("") ? "" : " and LOTE_MATERIAL_PROVEEDOR like '%" + loteProovedor + "%'")
                    + (codEstadoMaterial > 0 ? " and iade.COD_ESTADO_MATERIAL='" + codEstadoMaterial + "'" : "")
                    + (buscarMaterial.getGrupos().getCapitulos().getCodCapitulo() > 0 ? " and g.COD_CAPITULO='" + buscarMaterial.getGrupos().getCapitulos().getCodCapitulo() + "'"
                            : (usuario.getAlmacenesGlobal().getCodAlmacen() == 1 ? " and g.COD_CAPITULO in (2,3,18)" : "and g.COD_CAPITULO in (4,8)"))
                    + (buscarMaterial.getGrupos().getCodGrupo() > 0 ? " and g.COD_GRUPO='" + buscarMaterial.getGrupos().getCodGrupo() + "'" : "")
                    + (codMaterial.equals("") ? "" : " and m.COD_MATERIAL NOT IN (" + codMaterial + ")")
                    + " group by ia.COD_INGRESO_ALMACEN,ia.NRO_INGRESO_ALMACEN,ia.FECHA_INGRESO_ALMACEN"
                    + " ,em.NOMBRE_ESTADO_MATERIAL,iade.COD_ESTADO_MATERIAL,iade.LOTE_MATERIAL_PROVEEDOR"
                    + " ,m.NOMBRE_MATERIAL,iade.COD_MATERIAL,um.COD_UNIDAD_MEDIDA,um.ABREVIATURA"
                    + " order by ia.FECHA_INGRESO_ALMACEN";
            System.out.println("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet res = st.executeQuery(consulta);
            ingresosAlmacenDetalleEstadoList = new ArrayList<IngresosAlmacenDetalleEstado>();
            ingresosAlmacenDetalleEstadoList.clear();
            while (res.next()) {
                IngresosAlmacenDetalleEstado nuevo = new IngresosAlmacenDetalleEstado();
                nuevo.getIngresosAlmacen().setCodIngresoAlmacen(res.getInt("COD_INGRESO_ALMACEN"));
                nuevo.getIngresosAlmacen().setNroIngresoAlmacen(res.getInt("NRO_INGRESO_ALMACEN"));
                nuevo.getIngresosAlmacen().setFechaIngresoAlmacen(res.getTimestamp("FECHA_INGRESO_ALMACEN"));
                nuevo.getEstadosMaterial().setCodEstadoMaterial(res.getInt("COD_ESTADO_MATERIAL"));
                nuevo.getEstadosMaterial().setNombreEstadoMaterial(res.getString("NOMBRE_ESTADO_MATERIAL"));
                nuevo.setCantidadRestante(res.getFloat("cantidadRestante"));
                nuevo.getMateriales().setNombreMaterial(res.getString("NOMBRE_MATERIAL"));
                nuevo.getMateriales().setCodMaterial(res.getString("COD_MATERIAL"));
                nuevo.getMateriales().getUnidadesMedida().setCodUnidadMedida(res.getInt("COD_UNIDAD_MEDIDA"));
                nuevo.getMateriales().getUnidadesMedida().setAbreviatura(res.getString("ABREVIATURA"));
                nuevo.setLoteMaterialProveedor(res.getString("LOTE_MATERIAL_PROVEEDOR"));
                ingresosAlmacenDetalleEstadoList.add(nuevo);

            }
            res.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getCargarAgregarSolicitudSalidaLoteProovedor() {
        this.cargarEstadosMaterialSelectList();
        solicitudDetalleEstadoList = new ArrayList<IngresosAlmacenDetalle>();
        solicitudesSalidaLoteProovedorAgregar = new SolicitudesSalida();
        ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
        this.cargarCapitulosLoteProveedor(usuario);
        solicitudesSalidaLoteProovedorAgregar.getSolicitante().setNombrePersonal(usuario.getUsuarioModuloBean().getNombrePilaUsuarioGlobal());
        solicitudesSalidaLoteProovedorAgregar.getSolicitante().setApPaternoPersonal(usuario.getUsuarioModuloBean().getApPaternoUsuarioGlobal());
        solicitudesSalidaLoteProovedorAgregar.getSolicitante().setApMaternoPersonal(usuario.getUsuarioModuloBean().getApMaternoUsuarioGlobal());
        solicitudesSalidaLoteProovedorAgregar.getSolicitante().setCodPersonal(usuario.getUsuarioModuloBean().getCodUsuarioGlobal());
        solicitudesSalidaLoteProovedorAgregar.getAreaDestinoSalida().setCodAreaEmpresa("40");
        solicitudesSalidaLoteProovedorAgregar.getAreaDestinoSalida().setNombreAreaEmpresa("CONTROL DE CALIDAD");

        gruposList.clear();
        gruposList.add(new SelectItem("0", "-NINGUNO-"));
        //onchangeGrupoCapitulo();
        solicitudesSalidaAlmacenDetalleList.clear();
        return null;
    }

    /**
     * Creates a new instance of ManagedRegistroSolicitudSalidaAlmacen
     */
    public ManagedRegistroSolicitudSalidaAlmacen() {
        LOGGER = LogManager.getLogger("SolicitudSalidas");
    }

    public String getCargarContenidoSolicitudesSalidaAlmacen() {
        try {
            ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            Connection con = null;
            con = Util.openConnection(con);
            cargarGestiones(con);
            cargarTiposSalidaAlmacen2(con);
            cargarEstadosSalidaAlmacen(con);
            cargarAreasEmpresa(con);
            cargarAlmacenes(con);
            getSolicitudesSalidaBuscar().getGestiones().setCodGestion("0");
            getSolicitudesSalidaBuscar().setCodFormSalida(0);
            getSolicitudesSalidaBuscar().getTiposSalidasAlmacen().setCodTipoSalidaAlmacen(0);
            getSolicitudesSalidaBuscar().getEstadosSolicitudSalidasAlmacen().setCodEstadoSolicitudSalidaAlmacen(0);
            getSolicitudesSalidaBuscar().getSolicitante().setCodPersonal("0");
            getSolicitudesSalidaBuscar().getAreaDestinoSalida().setCodAreaEmpresa("0");
            setFechaInicio(null);
            setFechaFinal(null);
            getSolicitudesSalidaBuscar().setCodLoteProduccion("");
            getSolicitudesSalidaBuscar().setObsSolicitud("");

            getSolicitudesSalidaBuscar().getAlmacenes().setCodAlmacen(usuario.getAlmacenesGlobal().getCodAlmacen());
            getSolicitudesSalidaBuscar().setOrdenTrabajo("");

            con.close();

            solicitudesSalidaAlmacenList = this.cargarSolicitudesSalidaAlmacen();
            tiposSolicitudesSalidaAlmacenList = this.cargarTiposSalidaSolicitudAlmacen();
            estadosMaterialList = managedSolicitudSalidaAlmacen.cargarEstadosMaterial();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List cargarSolicitudesSalidaAlmacen() {
        List solicitudesSalidaAlmacenList = new ArrayList();
        try {
            Connection con = null;
            con = Util.openConnection(con);
            ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            StringBuilder consulta = new StringBuilder("select *")
                                            .append(" from ( select ROW_NUMBER() OVER (")
                                            .append(" ORDER BY vs.COD_FORM_SALIDA desc) as 'FILAS',vs.*")
                                                .append(" from VISTA_SOLICITUDES_SALIDA_ALMACEN vs ")
                                                .append(" where vs.COD_ESTADO_SOLICITUD_SALIDA_ALMACEN in(1,5)")
                                                        .append(" and vs.cod_estado_persona = 1 ")
                                                        .append(" and vs.solicitante = ").append(usuario.getUsuarioModuloBean().getCodUsuarioGlobal());
                                                if (!getSolicitudesSalidaBuscar().getGestiones().getCodGestion().equals("0")) {
                                                    consulta.append(" and vs.COD_GESTION= ").append(getSolicitudesSalidaBuscar().getGestiones().getCodGestion());

                                                }
                                                if (!getSolicitudesSalidaBuscar().getOrdenTrabajo().equals("")) {
                                                    consulta.append(" and vs.orden_trabajo ='").append(getSolicitudesSalidaBuscar().getOrdenTrabajo()).append("'");
                                                }
                                                if (getSolicitudesSalidaBuscar().getCodFormSalida() > 0) {
                                                    consulta.append(" and vs.COD_FORM_SALIDA = ").append(getSolicitudesSalidaBuscar().getCodFormSalida());
                                                }
                                                if (getSolicitudesSalidaBuscar().getTiposSalidasAlmacen().getCodTipoSalidaAlmacen() > 0) {
                                                    consulta.append(" and vs.COD_TIPO_SALIDA_ALMACEN = '").append(getSolicitudesSalidaBuscar().getTiposSalidasAlmacen().getCodTipoSalidaAlmacen()).append("'");
                                                }
                                                if (getSolicitudesSalidaBuscar().getEstadosSolicitudSalidasAlmacen().getCodEstadoSolicitudSalidaAlmacen() > 0) {
                                                    consulta.append(" and vs.COD_ESTADO_SOLICITUD_SALIDA_ALMACEN = '").append(getSolicitudesSalidaBuscar().getEstadosSolicitudSalidasAlmacen().getCodEstadoSolicitudSalidaAlmacen()).append("'");
                                                }
                                                if (!getSolicitudesSalidaBuscar().getSolicitante().getCodPersonal().equals("0")) {
                                                    consulta.append(" and vs.SOLICITANTE =").append(getSolicitudesSalidaBuscar().getSolicitante().getCodPersonal());

                                                }
                                                if (!getSolicitudesSalidaBuscar().getAreaDestinoSalida().getCodAreaEmpresa().equals("0")) {
                                                    consulta.append(" and vs.AREA_DESTINO_SALIDA =").append(getSolicitudesSalidaBuscar().getAreaDestinoSalida().getCodAreaEmpresa());
                                                }
                                                if (getFechaInicio() != null && getFechaFinal() != null) {
                                                    SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                                                    consulta.append(" and vs.FECHA_SOLICITUD BETWEEN '").append(df.format(getFechaInicio())).append("' and '").append(df.format(getFechaFinal())).append("'");
                                                }
                                                if (!getSolicitudesSalidaBuscar().getCodLoteProduccion().equals("")) {
                                                    consulta.append(" and vs.COD_LOTE_PRODUCCION= '").append(getSolicitudesSalidaBuscar().getCodLoteProduccion());
                                                }
                                                if (!getSolicitudesSalidaBuscar().getObsSolicitud().equals("")) {
                                                    consulta.append(" and vs.OBS_SOLICITUD like '%").append(getSolicitudesSalidaBuscar().getObsSolicitud()).append("%'");
                                                }
                                                if (getSolicitudesSalidaBuscar().getAlmacenes().getCodAlmacen() > 0) {
                                                    consulta.append(" and vs.COD_ALMACEN =").append(getSolicitudesSalidaBuscar().getAlmacenes().getCodAlmacen());
                                                }
                                                consulta.append(" ) AS listado")
                                            .append(" where  listado.filas between ").append(begin).append(" and ").append(end);
            LOGGER.debug(" consulta listado soliciud: " + consulta.toString());
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(consulta.toString());
            solicitudesSalidaAlmacenList.clear();
            while (rs.next()) {
                SolicitudesSalida solicitudesSalida = new SolicitudesSalida();
                solicitudesSalida.setSolicitudPorLoteProveedor(rs.getInt("SOLICITUD_POR_LOTE_PROVEEDOR") > 0);
                solicitudesSalida.setCodFormSalida(rs.getInt("COD_FORM_SALIDA"));
                solicitudesSalida.setOrdenTrabajo(rs.getString("orden_trabajo"));
                solicitudesSalida.setFechaSolicitud(rs.getTimestamp("FECHA_SOLICITUD"));
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
                solicitudesSalida.getMaquinaria().setNombreMaquina(rs.getString("nombre_maquina"));
                solicitudesSalida.getAreasInstalaciones().setNombreAreaInstalacion(rs.getString("nombre_area_instalacion"));
                solicitudesSalida.getAlmacenesDestino().setCodAlmacen(rs.getInt("COD_ALMACEN_DESTINO"));
                solicitudesSalida.getAlmacenesDestino().setNombreAlmacen(rs.getString("NOMBRE_ALMACEN_DESTINO"));
                solicitudesSalidaAlmacenList.add(solicitudesSalida);
                if (solicitudesSalida.getEstadosSolicitudSalidasAlmacen().getCodEstadoSolicitudSalidaAlmacen() == 4) {
                    solicitudesSalida.setColorFila("#F5A9A9");
                }
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

    public List cargarSolicitudesSalidaAlmacenMateriales() {
        List solicitudesSalidaAlmacenList = new ArrayList();
        try {
            Connection con = null;
            con = Util.openConnection(con);
            ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            String consulta = " select * from (select ROW_NUMBER() OVER (ORDER BY s.COD_FORM_SALIDA desc) as 'FILAS', s.COD_FORM_SALIDA,s.orden_trabajo ,s.FECHA_SOLICITUD,ae.COD_AREA_EMPRESA,ae.NOMBRE_AREA_EMPRESA "
                    + " ,t.COD_TIPO_SALIDA_ALMACEN,t.NOMBRE_TIPO_SALIDA_ALMACEN,p.NOMBRE_PILA,p.AP_PATERNO_PERSONAL,p.AP_MATERNO_PERSONAL,s.COD_LOTE_PRODUCCION, "
                    + " e.COD_ESTADO_SOLICITUD_SALIDA_ALMACEN,e.NOMBRE_ESTADO_SOLICITUD_SALIDA_ALMACEN,s.OBS_SOLICITUD "
                    + " from SOLICITUDES_SALIDA s  "
                    + " inner join GESTIONES gest on gest.COD_GESTION = s.COD_GESTION "
                    + " inner join TIPOS_SALIDAS_ALMACEN t on t.COD_TIPO_SALIDA_ALMACEN = s.COD_TIPO_SALIDA_ALMACEN "
                    + " inner join ESTADOS_SOLICITUD_SALIDAS_ALMACEN e on e.COD_ESTADO_SOLICITUD_SALIDA_ALMACEN = s.COD_ESTADO_SOLICITUD_SALIDA_ALMACEN "
                    + " inner join PERSONAL p on p.COD_PERSONAL = s.SOLICITANTE "
                    + " inner join AREAS_EMPRESA ae on ae.COD_AREA_EMPRESA = s.AREA_DESTINO_SALIDA "
                    + " left outer join INGRESOS_ALMACEN i on i.COD_INGRESO_ALMACEN = s.COD_INGRESO_ALMACEN"
                    + //inicio ale validar salidas
                    " where " + (!usuario.getUsuarioModuloBean().getCodUsuarioGlobal().equals("603") ? " s.solicitante = '" + usuario.getUsuarioModuloBean().getCodUsuarioGlobal() : "") + "' ";//and s.cod_estado_solicitud_salida_almacen not in(5)
            //final ale validar salidas
            //" where s.solicitante = '"+usuario.getUsuarioModuloBean().getCodUsuarioGlobal() +"' and s.cod_estado_solicitud_salida_almacen not in(5) ";
            if (!getSolicitudesSalidaBuscar().getGestiones().getCodGestion().equals("0")) {
                consulta += " and s.COD_GESTION= '" + getSolicitudesSalidaBuscar().getGestiones().getCodGestion() + "'";

            }
            if (!getSolicitudesSalidaBuscar().getOrdenTrabajo().equals("")) {
                consulta += " and s.orden_trabajo ='" + getSolicitudesSalidaBuscar().getOrdenTrabajo() + "'";
            }
            if (getSolicitudesSalidaBuscar().getCodFormSalida() > 0) {
                consulta += " and s.COD_FORM_SALIDA = '" + getSolicitudesSalidaBuscar().getCodFormSalida() + "'";
            }
            if (getSolicitudesSalidaBuscar().getTiposSalidasAlmacen().getCodTipoSalidaAlmacen() > 0) {
                consulta += " and s.COD_TIPO_SALIDA_ALMACEN = '" + getSolicitudesSalidaBuscar().getTiposSalidasAlmacen().getCodTipoSalidaAlmacen() + "'";
            }
            if (getSolicitudesSalidaBuscar().getEstadosSolicitudSalidasAlmacen().getCodEstadoSolicitudSalidaAlmacen() > 0) {
                consulta += " and e.COD_ESTADO_SOLICITUD_SALIDA_ALMACEN = '" + getSolicitudesSalidaBuscar().getEstadosSolicitudSalidasAlmacen().getCodEstadoSolicitudSalidaAlmacen() + "'";

            }
            if (!getSolicitudesSalidaBuscar().getSolicitante().getCodPersonal().equals("0")) {
                consulta += " and s.SOLICITANTE ='" + getSolicitudesSalidaBuscar().getSolicitante().getCodPersonal() + "'";

            }
            if (!getSolicitudesSalidaBuscar().getAreaDestinoSalida().getCodAreaEmpresa().equals("0")) {
                consulta += " and s.AREA_DESTINO_SALIDA ='" + getSolicitudesSalidaBuscar().getAreaDestinoSalida().getCodAreaEmpresa() + "'";
            }
            if (getFechaInicio() != null && getFechaFinal() != null) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                consulta += " and s.FECHA_SOLICITUD BETWEEN '" + df.format(getFechaInicio()) + "' and '" + df.format(getFechaFinal()) + "'";
            }

            if (!getSolicitudesSalidaBuscar().getCodLoteProduccion().equals("")) {
                consulta += " and s.COD_LOTE_PRODUCCION= '" + getSolicitudesSalidaBuscar().getCodLoteProduccion() + "'";
            }
            if (!getSolicitudesSalidaBuscar().getObsSolicitud().equals("")) {
                consulta += " and s.OBS_SOLICITUD like '%" + getSolicitudesSalidaBuscar().getObsSolicitud() + "%'";
            }
            if (getSolicitudesSalidaBuscar().getAlmacenes().getCodAlmacen() > 0) {
                consulta += " and s.COD_ALMACEN ='" + getSolicitudesSalidaBuscar().getAlmacenes().getCodAlmacen() + "'";
            }
            consulta += ") as listado_solicitudes_salida_almacen "
                    + " where filas between " + begin + " and " + end + "  ";
            System.out.println("consulta " + consulta);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(consulta);
            solicitudesSalidaAlmacenList.clear();
            while (rs.next()) {
                SolicitudesSalida solicitudesSalida = new SolicitudesSalida();
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
                solicitudesSalidaAlmacenList.add(solicitudesSalida);
                if (solicitudesSalida.getEstadosSolicitudSalidasAlmacen().getCodEstadoSolicitudSalidaAlmacen() == 4) {
                    solicitudesSalida.setColorFila("#F5A9A9");
                }
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

    public void cargarGestiones(Connection con) {
        try {

            String sql = "select cod_gestion,nombre_gestion from gestiones";
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(sql);
            getGestionesList().clear();
            getGestionesList().add(new SelectItem("0", "-TODOS-"));
            while (rs.next()) {
                getGestionesList().add(new SelectItem(rs.getString("cod_gestion"), rs.getString("nombre_gestion")));
            }
            if (rs != null) {
                rs.close();
                st.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cargarAlmacenes(Connection con) {
        try {

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
    public void cargarAlmacenes() throws SQLException {
        Connection con = null;
        try {
            con = Util.openConnection(con);
            String sql = "select a.COD_ALMACEN,a.NOMBRE_ALMACEN from ALMACENES a";
            sql += " where a.COD_ESTADO_REGISTRO=1 order by a.NOMBRE_ALMACEN";
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(sql);
            almacenesList.clear();
            while (rs.next()) {
                almacenesList.add(new SelectItem(rs.getString("COD_ALMACEN"), rs.getString("NOMBRE_ALMACEN")));
            }
            if (rs != null) {
                rs.close();
                st.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            con.close();
        }
    }

    public void cargarTiposSalidaAlmacen2(Connection con) {
        try {
            String sql = "  select t.COD_TIPO_SALIDA_ALMACEN,t.NOMBRE_TIPO_SALIDA_ALMACEN "
                    + " from TIPOS_SALIDAS_ALMACEN t "
                    + " where t.COD_ESTADO_REGISTRO = 1 order by t.NOMBRE_TIPO_SALIDA_ALMACEN";

            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(sql);
            getTiposSalidasList2().clear();
            getTiposSalidasList2().add(new SelectItem("0", "-TODOS-"));
            while (rs.next()) {
                getTiposSalidasList2().add(new SelectItem(rs.getInt("COD_TIPO_SALIDA_ALMACEN"), rs.getString("NOMBRE_TIPO_SALIDA_ALMACEN")));
            }
            if (rs != null) {
                rs.close();
                st.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cargarEstadosSalidaAlmacen(Connection con) {
        try {
            String sql = "  select e.NOMBRE_ESTADO_SOLICITUD_SALIDA_ALMACEN, e.COD_ESTADO_SOLICITUD_SALIDA_ALMACEN";
            sql += " from ESTADOS_SOLICITUD_SALIDAS_ALMACEN e where e.COD_ESTADO_REGISTRO=1 order by  e.NOMBRE_ESTADO_SOLICITUD_SALIDA_ALMACEN";

            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(sql);
            getEstadosSolicitudList().clear();
            getEstadosSolicitudList().add(new SelectItem("0", "-TODOS-"));
            while (rs.next()) {
                getEstadosSolicitudList().add(new SelectItem(rs.getInt("COD_ESTADO_SOLICITUD_SALIDA_ALMACEN"), rs.getString("NOMBRE_ESTADO_SOLICITUD_SALIDA_ALMACEN")));
            }
            if (rs != null) {
                rs.close();
                st.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List cargarAreasEmpresa(Connection con) {
        List areasEmpresalist = new ArrayList();
        try {
            String sql = "  select a.COD_AREA_EMPRESA,a.NOMBRE_AREA_EMPRESA  "
                    + " from AREAS_EMPRESA a  where a.COD_ESTADO_REGISTRO = 1 order by a.NOMBRE_AREA_EMPRESA";

            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(sql);
            getAreasEmpresaList2().clear();
            getAreasEmpresaList2().add(new SelectItem("0", "-TODOS-"));
            while (rs.next()) {
                getAreasEmpresaList2().add(new SelectItem(rs.getString("COD_AREA_EMPRESA"), rs.getString("NOMBRE_AREA_EMPRESA")));
            }
            if (rs != null) {
                rs.close();
                st.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return areasEmpresalist;
    }

    public String buscarSalidaAlmacenAction() {
        try {
            begin = 1;
            end = 10;
            solicitudesSalidaAlmacenList = this.cargarSolicitudesSalidaAlmacen();
            //this.cargarSalidasAlmacen();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List cargarTiposSalidaSolicitudAlmacen() {
        List tiposSolicitudSalidaAlmacenList = new ArrayList();
        tiposSolicitudSalidaAlmacenList.clear();
        tiposSolicitudSalidaAlmacenList.add(new SelectItem("1", "SOLICITUD MANUAL"));
        tiposSolicitudSalidaAlmacenList.add(new SelectItem("4", "SOLICITUD TRASPASO"));
        tiposSolicitudSalidaAlmacenList.add(new SelectItem("2", "POR PRODUCCION"));
        tiposSolicitudSalidaAlmacenList.add(new SelectItem("3", "POR REACONDICIONAMIENTO"));
        
        return tiposSolicitudSalidaAlmacenList;
    }

    public String seleccionarTipoSolicitudSalidaAlmacen() {
        direccionPagina="";
        System.out.println("codTipoSolicitudSalidaAlmacen :"+codTipoSolicitudSalidaAlmacen );
        switch(codTipoSolicitudSalidaAlmacen)
        {
            case 1:
            {
                solicitudesSalida = new SolicitudesSalida();
                solicitudesSalida.getTipoSolicitud().setCodTipoSolicitudSalida(COD_TIPO_SOLICITUD_MANUAL);
                codTipoSolicitudSalidaAlmacen = COD_TIPO_SOLICITUD_MANUAL;
                direccionPagina = "agregarSolicitudSalidaAlmacenManual.jsf";
                break;
            }
            case 2:
            {
                direccionPagina = "navegador_programa_periodo.jsf";
                break;
            }
            case 3:
            {
                direccionPagina = "agregarSolicitudSalidaReacondicionado.jsf";
                break;
            }
            case 4:{
                solicitudesSalida = new SolicitudesSalida();
                solicitudesSalida.getTipoSolicitud().setCodTipoSolicitudSalida(COD_TIPO_SOLICITUD_TRASPASO);
                codTipoSolicitudSalidaAlmacen = COD_TIPO_SOLICITUD_TRASPASO;
                direccionPagina = "agregarSolicitudSalidaAlmacenManual.jsf";
                break;
            }
        }
        return null;
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

    public String buscarMaterial_action() {
        try {
            System.out.println("entro al change");
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

    public String itemsSeleccionadosSalidaAlmacen() {
        String itemsSolicitudSalidaAlmacen = "-1";
        try {
            Iterator i = solicitudesSalidaAlmacenDetalleList.iterator();
            while (i.hasNext()) {
                SolicitudesSalidaDetalle solicitudesSalidasDetalleItem = (SolicitudesSalidaDetalle) i.next();
                itemsSolicitudSalidaAlmacen = itemsSolicitudSalidaAlmacen + "," + solicitudesSalidasDetalleItem.getMateriales().getCodMaterial();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemsSolicitudSalidaAlmacen;

    }

    public String agregarItem_action() {
        try {
            materialesBuscarList.clear();
            buscarMaterial = new Materiales();
            //onchangeGrupoCapitulo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String onChangeCapitulo() {
        capitulos_change();
        // onchangeGrupoCapitulo();
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
    // <editor-fold defaultstate="collapsed" desc="registro solicitud por reacondicionamiento">
    
        public String guardarSolicitudSalidaReacondicionamiento_action() 
        {
            Connection con = null;
            mensaje="";
            try 
            {
                con = Util.openConnection(con);
                con.setAutoCommit(false);
                ManagedAccesoSistema usuarioSession = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                StringBuilder consulta=new StringBuilder("select isnull(max(s.COD_FORM_SALIDA),0)+1 codFormSalida");
                                        consulta.append(" from SOLICITUDES_SALIDA s ");
                System.out.println("consulta obtener cod form salida "+consulta.toString());
                Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                ResultSet res=st.executeQuery(consulta.toString());
                if(res.next())solicitudesSalidaReacondicionamiento.setCodFormSalida(res.getInt("codFormSalida"));
                consulta = new StringBuilder(" INSERT INTO SOLICITUDES_SALIDA(COD_GESTION,  COD_FORM_SALIDA,  COD_TIPO_SALIDA_ALMACEN,  COD_ESTADO_SOLICITUD_SALIDA_ALMACEN,");
                                            consulta.append("  SOLICITANTE,  AREA_DESTINO_SALIDA,  FECHA_SOLICITUD,  COD_LOTE_PRODUCCION,  OBS_SOLICITUD,  ESTADO_SISTEMA,");
                                            consulta.append("  CONTROL_CALIDAD,  COD_INGRESO_ALMACEN,  COD_ALMACEN,  orden_trabajo,cod_compprod,cod_presentacion,cod_maquina,cod_area_instalacion) ");
                                    consulta.append(" VALUES(");
                                            consulta.append(usuarioSession.getGestionesGlobal().getCodGestion()).append(",");
                                            consulta.append(solicitudesSalidaReacondicionamiento.getCodFormSalida()).append(",");
                                            consulta.append("16,");//TIPO DE SALIDA REACONDICIONAMIENTO
                                            consulta.append("1,");//cod estado generado
                                            consulta.append(usuarioSession.getUsuarioModuloBean().getCodUsuarioGlobal()).append(",");
                                            consulta.append(solicitudesSalidaReacondicionamiento.getAreaDestinoSalida().getCodAreaEmpresa()).append(",");
                                            consulta.append("getdate(),");
                                            consulta.append("'',");//sin lote produccion
                                            consulta.append("?,");//observacion solicitud
                                            consulta.append("1,");//estado registrado
                                            consulta.append("0,");
                                            consulta.append(" 0,");
                                            consulta.append(usuarioSession.getAlmacenesGlobal().getCodAlmacen()).append(",");
                                            consulta.append("'',");//sin orden de trabajo
                                            consulta.append("0,");//sin producto
                                            consulta.append(solicitudesSalidaReacondicionamiento.getPresentacionesProducto().getCodPresentacion()).append(",");
                                            consulta.append("0,");// sin maquinaria
                                            consulta.append("0");//sin instalacion
                                    consulta.append(")");
                    System.out.println("consulta registrar solicitud reacondicionamiento " + consulta.toString());
                    PreparedStatement pst=con.prepareStatement(consulta.toString());
                    pst.setString(1,solicitudesSalidaReacondicionamiento.getObsSolicitud());
                    if(pst.executeUpdate()>0)System.out.println("se registro la solicitud");
                    
                    for(SolicitudesSalidaDetalle bean:solicitudesSalidaReacondicionamiento.getSolicitudesSalidaDetalleList())
                    {
                        consulta = new StringBuilder("INSERT INTO SOLICITUDES_SALIDA_DETALLE(COD_FORM_SALIDA,  COD_MATERIAL,  CANTIDAD,");
                                            consulta.append(" CANTIDAD_ENTREGADA,COD_UNIDAD_MEDIDA)");
                                    consulta.append(" VALUES (");
                                            consulta.append(solicitudesSalidaReacondicionamiento.getCodFormSalida()).append(",");
                                            consulta.append(bean.getMateriales().getCodMaterial()).append(",");
                                            consulta.append(bean.getCantidad()).append(",");
                                            consulta.append("0,");
                                            consulta.append(bean.getUnidadesMedida().getCodUnidadMedida());
                                    consulta.append(")");
                        System.out.println("consulta registrar detalle solicitud "+consulta.toString());
                        pst=con.prepareStatement(consulta.toString());
                        if(pst.executeUpdate()>0)System.out.println("se registro el detalle");
                            
                    }
                con.commit();
                mensaje="1";
            } 
            catch(SQLException ex)
            {
                try
                {
                con.rollback();
                }
                catch(SQLException ex1)
                {ex1.printStackTrace();}
                mensaje="Ocurrio un error al momento de registrar la solicitud";
                ex.printStackTrace();
            }
            catch (Exception e) {
                mensaje="Ocurrio un error al momento de registrar la solicitud";
                e.printStackTrace();
            }
            finally
            {
                try{
                    con.close();
                }
                catch(SQLException ex1)
                {ex1.printStackTrace();}
            }
            return null;
        }

        public String eliminarSolicitudDetalleReacond_action(){
            solicitudesSalidaReacondicionamiento.getSolicitudesSalidaDetalleList().remove(solicitudesSalidaDetalleEliminar);
            return null;
        }
        


        public String getCargarRegistroSolicitudSalidaReacondicionamiento()
        {
            solicitudesSalidaReacondicionamiento=new SolicitudesSalida();
            solicitudesSalidaReacondicionamiento.setSolicitudesSalidaDetalleList(new ArrayList<SolicitudesSalidaDetalle>());
            this.cargarPresentacionesProductoReacondicionamiento_action();
            return null;
        }
        public String codPresentacionSolicitudReacondicionamiento_change()
        {
            solicitudesSalidaReacondicionamiento.setSolicitudesSalidaDetalleList(new ArrayList<SolicitudesSalidaDetalle>());
            Connection con=null;
            try 
            {
                con = Util.openConnection(con);
                StringBuilder consulta = new StringBuilder("select distinct m.COD_MATERIAL,m.NOMBRE_MATERIAL,um.COD_UNIDAD_MEDIDA,um.ABREVIATURA")
                                                .append(" from FORMULA_MAESTRA fm")
                                                        .append(" inner join FORMULA_MAESTRA_DETALLE_ES fmdes on fmdes.COD_FORMULA_MAESTRA=fm.COD_FORMULA_MAESTRA")
                                                        .append(" inner join materiales m on m.COD_MATERIAL=fmdes.COD_MATERIAL")
                                                        .append(" inner join UNIDADES_MEDIDA um on um.COD_UNIDAD_MEDIDA=m.COD_UNIDAD_MEDIDA")
                                                .append(" where fm.COD_ESTADO_REGISTRO=1")
                                                        .append(" and fmdes.COD_PRESENTACION_PRODUCTO=").append(solicitudesSalidaReacondicionamiento.getPresentacionesProducto().getCodPresentacion())
                                                .append(" order by m.NOMBRE_MATERIAL");
                System.out.println("consulta cargar materiales presentacion "+consulta.toString());
                Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet res = st.executeQuery(consulta.toString());
                while (res.next()) {
                    SolicitudesSalidaDetalle bean=new SolicitudesSalidaDetalle();
                    bean.getMateriales().setCodMaterial(res.getString("COD_MATERIAL"));
                    bean.getMateriales().setNombreMaterial(res.getString("NOMBRE_MATERIAL"));
                    bean.getUnidadesMedida().setCodUnidadMedida(res.getInt("COD_UNIDAD_MEDIDA"));
                    bean.getUnidadesMedida().setAbreviatura(res.getString("ABREVIATURA"));
                    solicitudesSalidaReacondicionamiento.getSolicitudesSalidaDetalleList().add(bean);
                }
                consulta = new StringBuilder("select cp.COD_AREA_EMPRESA,ae.NOMBRE_AREA_EMPRESA")
                                    .append(" from COMPONENTES_PRESPROD cpp")
                                            .append(" inner join COMPONENTES_PROD cp on cp.COD_COMPPROD=cpp.COD_COMPPROD")
                                            .append(" inner join AREAS_EMPRESA ae on ae.COD_AREA_EMPRESA=cp.COD_AREA_EMPRESA")
                                    .append(" where cpp.COD_PRESENTACION=").append(solicitudesSalidaReacondicionamiento.getPresentacionesProducto().getCodPresentacion())
                                            .append(" and cpp.COD_ESTADO_REGISTRO=1")
                                            .append(" and cp.COD_ESTADO_COMPPROD=1");
                System.out.println(" consulta obtener area "+consulta.toString());
                res=st.executeQuery(consulta.toString());
                if(res.next())
                {
                    solicitudesSalidaReacondicionamiento.getAreaDestinoSalida().setCodAreaEmpresa(res.getString("COD_AREA_EMPRESA"));
                    solicitudesSalidaReacondicionamiento.getAreaDestinoSalida().setNombreAreaEmpresa(res.getString("NOMBRE_AREA_EMPRESA"));

                }
                
                solicitudesSalidaReacondicionamiento.getAreaDestinoSalida().setCodAreaEmpresa("84");
                solicitudesSalidaReacondicionamiento.getAreaDestinoSalida().setNombreAreaEmpresa("ACONDICIONAMIENTO");

                mensaje = "1";
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            } finally {
                try
                {
                    con.close();
                }
                catch(SQLException ex)
                {
                    ex.printStackTrace();
                }
            }
            return null;
        }
        private void cargarPresentacionesProductoReacondicionamiento_action()
        {
            Connection con=null;
            try 
            {
                con = Util.openConnection(con);
                StringBuilder consulta = new StringBuilder("select distinct  pp.cod_presentacion,pp.NOMBRE_PRODUCTO_PRESENTACION")
                                                .append(" from PRESENTACIONES_PRODUCTO pp")
                                                        .append(" inner join COMPONENTES_PRESPROD cpp on cpp.COD_PRESENTACION=pp.cod_presentacion")
                                                .append(" where cpp.COD_ESTADO_REGISTRO=1")
                                                        .append(" and pp.cod_estado_registro=1")
                                                .append(" order by pp.NOMBRE_PRODUCTO_PRESENTACION");
                System.out.println("consulta cargar presentaciones disponibles"+consulta.toString());
                Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet res = st.executeQuery(consulta.toString());
                presentacionesProductoSelectList = new ArrayList<SelectItem>();
                while (res.next()) 
                {
                    presentacionesProductoSelectList.add(new SelectItem(res.getString("cod_presentacion"),res.getString("NOMBRE_PRODUCTO_PRESENTACION")));

                }
                mensaje = "1";
            } 
            catch (SQLException ex) 
            {
                ex.printStackTrace();
            } 
            catch (NumberFormatException ex) 
            {
                ex.printStackTrace();
            }
            finally 
            {
                try
                {
                con.close();
                }catch(SQLException ex)
                {
                ex.printStackTrace();
                }

            }
        }
    //</editor-fold>

    public String getCargarContenidoRegistroSolicitudSalidaAlmacen() {
        try {
            programaProduccionBuscar = new ProgramaProduccion();
            areasEmpresaList = managedSalidaAlmacen.cargarAreasEmpresa();
            componentesProdList = managedSalidaAlmacen.cargarComponentesProd();
            //capitulosList = this.cargarCapitulos();
            if(solicitudesSalida.getAreaDestinoSalida().getCodAreaEmpresa().equals("71")||solicitudesSalida.getAreaDestinoSalida().getCodAreaEmpresa().equals("77")){
                capitulosList = this.cargarCapitulos();
            }
            else{
                capitulosList = this.cargarCapitulosAlmacen();
            }
            ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            solicitudesSalida.getSolicitante().setNombrePersonal(usuario.getUsuarioModuloBean().getNombrePilaUsuarioGlobal());
            solicitudesSalida.getSolicitante().setApPaternoPersonal(usuario.getUsuarioModuloBean().getApPaternoUsuarioGlobal());
            solicitudesSalida.getSolicitante().setApMaternoPersonal(usuario.getUsuarioModuloBean().getApMaternoUsuarioGlobal());
            solicitudesSalida.getSolicitante().setCodPersonal(usuario.getUsuarioModuloBean().getCodUsuarioGlobal());
            solicitudesSalida.setCodFormSalida(this.generaCodSolicitudSalidaAlmacen());
            solicitudesSalida.setCodLoteProduccion("");
            solicitudesSalida.getPresentacionesProducto().setCodPresentacion("0");
            solicitudesSalida.setComponentesProd(new ComponentesProd());
            gruposList.clear();
            gruposList.add(new SelectItem("0", "-NINGUNO-"));
            solicitudesSalidaAlmacenDetalleList.clear();
            //verificacion si existe algun objeto de donde se pueda realizar una salida de almacen
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            Map<String, Object> sessionMap = externalContext.getSessionMap();
            //ProgramaProduccion programaProduccion =(ProgramaProduccion)sessionMap.get("programaProduccion");
            solicitudMantenimiento = (SolicitudMantenimiento) sessionMap.get("solicitudMantenimiento");
            //solicitudesSalidaAlmacenDetalleList = (ArrayList)sessionMap.get("solicitudesSalidaDetalleList");

            programaProduccion = (ProgramaProduccion) sessionMap.get("programaProduccion");
            List materiaPrimaList = (ArrayList) sessionMap.get("materiaPrimaList");
            List empaquePrimarioList = (ArrayList) sessionMap.get("empaquePrimarioList");
            List empaqueSecundarioList = (ArrayList) sessionMap.get("empaqueSecundarioList");

            //solicitudesSalidaAlmacenDetalleList = solicitudesSalidaAlmacenDetalleList1;
            almacenSolicitudList = this.cargarAlmacenSolicitud();
            registroManual = true;

            System.out.println("cod almacen :::" + usuario.getAlmacenesGlobal().getCodAlmacen());

            if (programaProduccion != null) {
                registroManual = false;

                ingresosAlmacenDetalleEstado.getEstadosMaterial().setCodEstadoMaterial(2); //estado aprobado
                solicitudesSalida.setCodLoteProduccion(programaProduccion.getCodLoteProduccion());
                //salidasAlmacen.getComponentesProd().setCodCompprod(programaProduccion.getFormulaMaestra().getComponentesProd().getCodCompprod());
                solicitudesSalida.setAreaDestinoSalida(programaProduccion.getFormulaMaestra().getComponentesProd().getAreasEmpresa());
                solicitudesSalida.setComponentesProd(programaProduccion.getFormulaMaestra().getComponentesProd());
                this.cargaSalidasAlmacenProgramaProduccion(programaProduccion, materiaPrimaList, empaquePrimarioList, empaqueSecundarioList);
                //this.producto_changed();
                //tiposSalidasAlmacenList = this.cargarTiposSalidasAlmacenProduccion();
                solicitudesSalida.getPresentacionesProducto().setCodPresentacion(programaProduccion.getPresentacionesProducto().getCodPresentacion());
                sessionMap.put("programaProduccion", null);
            }
            if (solicitudMantenimiento != null) {
                registroManual = false;
                ingresosAlmacenDetalleEstado.getEstadosMaterial().setCodEstadoMaterial(2);
                String codAreaSolicitud = "";
                if (solicitudMantenimiento.getSolicitudProduccion() == 1) {
                    codAreaSolicitud = "PROD";
                }
                if (solicitudMantenimiento.getSolicitudProyecto() == 1) {
                    codAreaSolicitud = "PROY";
                }
                solicitudesSalida.setOrdenTrabajo(String.valueOf(solicitudMantenimiento.getNroOrdenTrabajo()));
                solicitudesSalida.setMaquinaria(solicitudMantenimiento.getMaquinaria());
                solicitudesSalida.setAreaDestinoSalida(solicitudMantenimiento.getAreasEmpresa());
                //sessionMap.put("solicitudesSalidaDetalle",null);
                //sessionMap.put("solicitudMantenimiento", null);
                this.cargarDetalleSolicitud();
            }
            this.cargarAlmacenDestinoTraspasoList();

            this.producto_changed();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void cargarDetalleSolicitud() {
        try {

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
            solicitudesSalidaAlmacenDetalleList.clear();
            while (rs.next()) {
                SolicitudesSalidaDetalle s = new SolicitudesSalidaDetalle();
                s.setCantidad(rs.getFloat("cantidad"));
                s.getMateriales().setCodMaterial(rs.getString("cod_material"));
                s.getMateriales().setNombreMaterial(rs.getString("nombre_material"));
                s.getUnidadesMedida().setCodUnidadMedida(rs.getInt("cod_unidad_medida"));
                s.getUnidadesMedida().setNombreUnidadMedida(rs.getString("nombre_unidad_medida"));
                s.getUnidadesMedida().setAbreviatura(rs.getString("abreviatura"));
                //s.setDescr(rs.getString("descripcion"));
                solicitudesSalidaAlmacenDetalleList.add(s);
            }
            rs.close();
            st.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getCargarContenidoRegistroSolicitudAlmacenes() {
        try {
            codTipoSolicitudSalidaAlmacen = 1;
            this.getCargarContenidoRegistroSolicitudSalidaAlmacen();
            solicitudesSalida.setAlmacenes(usuario.getAlmacenesGlobal());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void cargaSalidasAlmacenProgramaProduccion(ProgramaProduccion programaProduccion, List materiaPrimaList, List empaquePrimarioList, List empaqueSecundarioList) {
        try {
            solicitudesSalidaAlmacenDetalleList.clear();
            emptyListEmpaqueSecundario = false;
            Iterator i = materiaPrimaList.iterator();
            while (i.hasNext()) {
                FormulaMaestraDetalleMP formulaMaestraDetalleMP = (FormulaMaestraDetalleMP) i.next();
                if (formulaMaestraDetalleMP.getChecked().booleanValue() == true) {
                    SolicitudesSalidaDetalle solicitudesSalidasDetalleItem = new SolicitudesSalidaDetalle();
                    solicitudesSalidaDetalle.getMateriales().setMaterialProduccion(1);
                    solicitudesSalidasDetalleItem.getMateriales().setCodMaterial(formulaMaestraDetalleMP.getMateriales().getCodMaterial());
                    solicitudesSalidasDetalleItem.getMateriales().setNombreMaterial(formulaMaestraDetalleMP.getMateriales().getNombreMaterial());
                    solicitudesSalidasDetalleItem.setCantidad(formulaMaestraDetalleMP.getCantidad().floatValue());
                    solicitudesSalidasDetalleItem.getUnidadesMedida().setCodUnidadMedida(formulaMaestraDetalleMP.getUnidadesMedida().getCodUnidadMedida());
                    solicitudesSalidasDetalleItem.getUnidadesMedida().setAbreviatura(formulaMaestraDetalleMP.getUnidadesMedida().getAbreviatura());
                    solicitudesSalidasDetalleItem.setCantidadEntregada(this.cantidadEntregada(programaProduccion, solicitudesSalidasDetalleItem));
                    //this.etiquetasSalidaAlmacen(salidasAlmacenDetalleItem);
                    //salidasAlmacenDetalleItem.setEquivalencias(this.buscaEquivalencia(salidasAlmacenDetalleItem));
                    //salidasAlmacenDetalleItem.getEstadosMaterial().setCodEstadoMaterial(2); //estado aprobado
//                if(salidasAlmacenDetalleItem.getCantidadSalidaAlmacen()>salidasAlmacenDetalleItem.getCantidadDisponible()){
//                    salidasAlmacenDetalleItem.setColorFila("#FF0000");
//                }else{
//                    salidasAlmacenDetalleItem.setColorFila("#00FF00");
//                }
                    solicitudesSalidasDetalleItem.setCantidad(solicitudesSalidasDetalleItem.getCantidad() - solicitudesSalidasDetalleItem.getCantidadEntregada());
                    if (solicitudesSalidasDetalleItem.getCantidad() < 0) {
                        solicitudesSalidasDetalleItem.setCantidad(0);
                    }
                    solicitudesSalidasDetalleItem.setCantidadMaximaSolicitud(solicitudesSalidasDetalleItem.getCantidad() - solicitudesSalidasDetalleItem.getCantidadEntregada());
                    solicitudesSalidasDetalleItem.setColorFila("");
                    //this.detalleEtiquetasSalidaAlmacen(salidasAlmacenDetalleItem);

                    solicitudesSalidaAlmacenDetalleList.add(solicitudesSalidasDetalleItem);
                }
            }
            i = empaquePrimarioList.iterator();

            while (i.hasNext()) {
                FormulaMaestraDetalleEP formulaMaestraDetalleEP = (FormulaMaestraDetalleEP) i.next();
                if (formulaMaestraDetalleEP.getChecked().booleanValue() == true) {
                    SolicitudesSalidaDetalle solicitudesSalidasDetalleItem = new SolicitudesSalidaDetalle();
                    solicitudesSalidasDetalleItem.getMateriales().setMaterialProduccion(2);
                    solicitudesSalidasDetalleItem.getMateriales().setCodMaterial(formulaMaestraDetalleEP.getMateriales().getCodMaterial());
                    solicitudesSalidasDetalleItem.getMateriales().setNombreMaterial(formulaMaestraDetalleEP.getMateriales().getNombreMaterial());
                    solicitudesSalidasDetalleItem.setCantidad(formulaMaestraDetalleEP.getCantidad() > 0 ? formulaMaestraDetalleEP.getCantidad() : 0);
                    solicitudesSalidasDetalleItem.getUnidadesMedida().setCodUnidadMedida(formulaMaestraDetalleEP.getUnidadesMedida().getCodUnidadMedida());
                    solicitudesSalidasDetalleItem.getUnidadesMedida().setAbreviatura(formulaMaestraDetalleEP.getUnidadesMedida().getAbreviatura());
                    solicitudesSalidasDetalleItem.setCantidadEntregada(this.cantidadEntregada(programaProduccion, solicitudesSalidasDetalleItem));
                    //this.etiquetasSalidaAlmacen(salidasAlmacenDetalleItem);
                    //salidasAlmacenDetalleItem.setEquivalencias(this.buscaEquivalencia(salidasAlmacenDetalleItem));
                    //salidasAlmacenDetalleItem.getEstadosMaterial().setCodEstadoMaterial(2); //estado aprobado
//                if(salidasAlmacenDetalleItem.getCantidadSalidaAlmacen()>salidasAlmacenDetalleItem.getCantidadDisponible()){
//                    salidasAlmacenDetalleItem.setColorFila("#FF0000");
//                }else{
//                    salidasAlmacenDetalleItem.setColorFila("#00FF00");
//                }
                    solicitudesSalidasDetalleItem.setCantidad(solicitudesSalidasDetalleItem.getCantidad() - solicitudesSalidasDetalleItem.getCantidadEntregada());
                    solicitudesSalidasDetalleItem.setCantidadMaximaSolicitud(solicitudesSalidasDetalleItem.getCantidad() - solicitudesSalidasDetalleItem.getCantidadEntregada());
                    solicitudesSalidasDetalleItem.setColorFila("");
                    if (solicitudesSalidasDetalleItem.getCantidad() < 0) {
                        solicitudesSalidasDetalleItem.setCantidad(0);
                    }
                    //this.detalleEtiquetasSalidaAlmacen(salidasAlmacenDetalleItem);

                    solicitudesSalidaAlmacenDetalleList.add(solicitudesSalidasDetalleItem);
                }
            }

            i = empaqueSecundarioList.iterator();

            while (i.hasNext()) {
                FormulaMaestraDetalleES formulaMaestraDetalleES = (FormulaMaestraDetalleES) i.next();
                if (formulaMaestraDetalleES.getChecked().booleanValue() == true) {
                    emptyListEmpaqueSecundario = true;
                    SolicitudesSalidaDetalle solicitudesSalidasDetalleItem = new SolicitudesSalidaDetalle();
                    solicitudesSalidasDetalleItem.getMateriales().setMaterialProduccion(3);
                    solicitudesSalidasDetalleItem.getMateriales().setCodMaterial(formulaMaestraDetalleES.getMateriales().getCodMaterial());
                    solicitudesSalidasDetalleItem.getMateriales().setNombreMaterial(formulaMaestraDetalleES.getMateriales().getNombreMaterial());
                    solicitudesSalidasDetalleItem.setCantidad(formulaMaestraDetalleES.getCantidad() > 0 ? formulaMaestraDetalleES.getCantidad() : 0);
                    solicitudesSalidasDetalleItem.getUnidadesMedida().setCodUnidadMedida(formulaMaestraDetalleES.getUnidadesMedida().getCodUnidadMedida());
                    solicitudesSalidasDetalleItem.getUnidadesMedida().setAbreviatura(formulaMaestraDetalleES.getUnidadesMedida().getAbreviatura());
                    solicitudesSalidasDetalleItem.setCantidadEntregada(this.cantidadEntregada(programaProduccion, solicitudesSalidasDetalleItem));
                    //this.etiquetasSalidaAlmacen(salidasAlmacenDetalleItem);
                    //salidasAlmacenDetalleItem.setEquivalencias(this.buscaEquivalencia(salidasAlmacenDetalleItem));
                    //salidasAlmacenDetalleItem.getEstadosMaterial().setCodEstadoMaterial(2); //estado aprobado
//                if(salidasAlmacenDetalleItem.getCantidadSalidaAlmacen()>salidasAlmacenDetalleItem.getCantidadDisponible()){
//                    salidasAlmacenDetalleItem.setColorFila("#FF0000");
//                }else{
//                    salidasAlmacenDetalleItem.setColorFila("#00FF00");
//                }
                    solicitudesSalidasDetalleItem.setCantidad(solicitudesSalidasDetalleItem.getCantidad() - solicitudesSalidasDetalleItem.getCantidadEntregada());
                    solicitudesSalidasDetalleItem.setCantidadMaximaSolicitud(solicitudesSalidasDetalleItem.getCantidad() - solicitudesSalidasDetalleItem.getCantidadEntregada());
                    solicitudesSalidasDetalleItem.setColorFila("");
                    if (solicitudesSalidasDetalleItem.getCantidad() < 0) {
                        solicitudesSalidasDetalleItem.setCantidad(0);
                    }
                    //this.detalleEtiquetasSalidaAlmacen(salidasAlmacenDetalleItem);

                    solicitudesSalidaAlmacenDetalleList.add(solicitudesSalidasDetalleItem);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public float cantidadEntregada(ProgramaProduccion programaProduccion, SolicitudesSalidaDetalle solicitudesSalidaDetalle) {
        float cantidadEntregada = 0;
        try {
            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement();
            String consulta = " select sd.COD_MATERIAL, sum(sd.CANTIDAD_ENTREGADA) cantidad_entregada"
                    + " from SOLICITUDES_SALIDA s "
                    + " left outer join SOLICITUDES_SALIDA_DETALLE sd on sd.COD_FORM_SALIDA = s.COD_FORM_SALIDA "
                    + " where s.COD_LOTE_PRODUCCION = '" + programaProduccion.getCodLoteProduccion() + "' and sd.COD_MATERIAL = '" + solicitudesSalidaDetalle.getMateriales().getCodMaterial() + "' "
                    + " group by sd.COD_MATERIAL ";
            System.out.println("consulta " + consulta);
            ResultSet rs = st.executeQuery(consulta);
            if (rs.next()) {
                cantidadEntregada = rs.getFloat("cantidad_entregada");
            }
            rs.close();
            st.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cantidadEntregada;
    }

    public List cargarAlmacenSolicitud() {
        List almacenSolicitudList = new ArrayList();
        try {
            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement();
            String consulta = " select a.COD_ALMACEN,a.NOMBRE_ALMACEN from almacenes a where a.COD_ESTADO_REGISTRO = 1 order by a.NOMBRE_ALMACEN asc ";
            System.out.println("consulta " + consulta);
            ResultSet rs = st.executeQuery(consulta);
            while (rs.next()) {
                almacenSolicitudList.add(new SelectItem(rs.getString("cod_almacen"), rs.getString("nombre_almacen")));
            }
            rs.close();
            st.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return almacenSolicitudList;
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
    public String seleccionarMaterialAction() {
        ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
        salidasAlmacenDetalleAgregar = new SalidasAlmacenDetalle();
        Iterator i = materialesBuscarList.iterator();
        while (i.hasNext()) {
            Materiales materialesItem = (Materiales) i.next();
            materialesItem.setColorFila("");
        }
        materialSeleccionado.setColorFila("#FFCC99");
        salidasAlmacenDetalleAgregar.setMateriales(materialSeleccionado);
        DaoEstadosMaterial daoEstadosMaterial = new DaoEstadosMaterial(LOGGER);
        estadosMaterialExistenciaList = daoEstadosMaterial.listarExistencias(usuario.getAlmacenesGlobal().getCodAlmacen(), salidasAlmacenDetalleAgregar.getMateriales().getCodMaterial());
        float cantidadRestante = 0;
        for(EstadosMaterial bean :  estadosMaterialExistenciaList){
            if(bean.getEstadoSalidaPermitido()){
                cantidadRestante += bean.getCantidadExistente();
            }
        }
        salidasAlmacenDetalleAgregar.setUnidadesMedida(materialSeleccionado.getUnidadesMedida().clone());
        salidasAlmacenDetalleAgregar.setCantidadDisponible(cantidadRestante);
        salidasAlmacenDetalleAgregar.setEquivalencias(managedSolicitudSalidaAlmacen.buscaEquivalencia(salidasAlmacenDetalleAgregar));
        return null;
    }

    public String detallarCantidadSalida_action() {
        try {
            ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");

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
                salidasAlmacenDetalleAgregar.getMateriales().setNombreMaterial(rs.getString("NOMBRE_MATERIAL"));
                salidasAlmacenDetalleAgregar.getMateriales().getUnidadesMedida().setCodUnidadMedida(rs.getInt("COD_UNIDAD_MEDIDA"));
                salidasAlmacenDetalleAgregar.getUnidadesMedida().setCodUnidadMedida(rs.getInt("COD_UNIDAD_MEDIDA"));
                salidasAlmacenDetalleAgregar.getUnidadesMedida().setNombreUnidadMedida(rs.getString("NOMBRE_UNIDAD_MEDIDA"));
                salidasAlmacenDetalleAgregar.getUnidadesMedida().setAbreviatura(rs.getString("ABREVIATURA"));
            }
            DaoEstadosMaterial daoEstadosMaterial = new DaoEstadosMaterial(LOGGER);
            estadosMaterialExistenciaList = daoEstadosMaterial.listarExistencias(usuario.getAlmacenesGlobal().getCodAlmacen(), salidasAlmacenDetalleAgregar.getMateriales().getCodMaterial());
            float cantidadRestante = 0;
            for(EstadosMaterial bean :  estadosMaterialExistenciaList){
                if(bean.getEstadoSalidaPermitido()){
                    cantidadRestante += bean.getCantidadExistente();
                }
            }
            salidasAlmacenDetalleAgregar.setCantidadDisponible(cantidadRestante);
            salidasAlmacenDetalleAgregar.setEquivalencias(managedSolicitudSalidaAlmacen.buscaEquivalencia(salidasAlmacenDetalleAgregar));

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

    public String detallarCantidadSalida_action1() {
        try {
            ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");

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
            salidasAlmacenDetalleAgregar.setCantidadDisponible(Float.valueOf(form.format(managedSolicitudSalidaAlmacen.cantidadDisponibleMaterial(salidasAlmacenDetalleAgregar, ingresosAlmacenDetalleEstado, solicitudesSalida.getAlmacenes()))));

            System.out.println("cantidad_disponible : " + salidasAlmacenDetalleAgregar.getCantidadDisponible());
            salidasAlmacenDetalleAgregar.setEquivalencias(managedSolicitudSalidaAlmacen.buscaEquivalencia(salidasAlmacenDetalleAgregar));

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

    public String estadoMaterial_change1() {
        try {

            NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
            DecimalFormat form = (DecimalFormat) nf;
            form.applyPattern("#.###");

            salidasAlmacenDetalleAgregar.setCantidadDisponible(Float.valueOf(form.format(managedSolicitudSalidaAlmacen.cantidadDisponibleMaterial(salidasAlmacenDetalleAgregar, ingresosAlmacenDetalleEstado, solicitudesSalida.getAlmacenes()))));
            System.out.println("cantidad_disponible : " + salidasAlmacenDetalleAgregar.getCantidadDisponible());
            salidasAlmacenDetalleAgregar.setEquivalencias(this.buscaEquivalencia(salidasAlmacenDetalleAgregar));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String guardarAgregarSalidaAlmacenDetalle_action() {
        try {
            mensaje = "";
            /*
             if(salidasAlmacenDetalleAgregar.getCantidadDisponible()==0){
             mensaje = " no existe cantidad disponible ";
             return null;
             }
             if(salidasAlmacenDetalleAgregar.getCantidadSalidaAlmacen()>salidasAlmacenDetalleAgregar.getCantidadDisponible() ){
             mensaje = " la cantidad de salida de almacen es mayor a la cantidad disponible ";
             return null;
             }
             if(ingresosAlmacenDetalleEstado.getEstadosMaterial().getCodEstadoMaterial()!= 2 && ingresosAlmacenDetalleEstado.getEstadosMaterial().getCodEstadoMaterial()!=6){
             mensaje = "no se puede registrar salidas con materiales con el estado seleccionado ";
             return null;
             }*/

            salidasAlmacenDetalleAgregar.setChecked(false);
            //this.etiquetasSalidaAlmacen(salidasAlmacenDetalleAgregar);

            if (salidasAlmacenDetalleAgregar.getCantidadSalidaAlmacen() > salidasAlmacenDetalleAgregar.getCantidadDisponible()) {
                salidasAlmacenDetalleAgregar.setColorFila("#FF0000");
            } else {
                salidasAlmacenDetalleAgregar.setColorFila("#00FF00");
            }
            salidasAlmacenDetalleAgregar.setColorFila("");
            //this.detalleEtiquetasSalidaAlmacen(salidasAlmacenDetalleAgregar);
            //salidasAlmacenDetalleList.add(salidasAlmacenDetalleAgregar);
            //SolicitudesSalidaDetalle solicitudesSalidaDetalle = new SolicitudesSalidaDetalle();
            //solicitudesSalidaDetalle.setMateriales(salidasAlmacenDetalleAgregar.getMateriales());
            //solicitudesSalidaDetalle.setCantidad(salidasAlmacenDetalleAgregar.getCantidadSalidaAlmacen());
            //solicitudesSalidaDetalle.setUnidadesMedida(salidasAlmacenDetalleAgregar.getUnidadesMedida());
            SolicitudesSalidaDetalle solicitudesSalidaDetalle = new SolicitudesSalidaDetalle();
            solicitudesSalidaDetalle.setCantidad(salidasAlmacenDetalleAgregar.getCantidadSalidaAlmacen());
            solicitudesSalidaDetalle.setMateriales(salidasAlmacenDetalleAgregar.getMateriales());
            solicitudesSalidaDetalle.setUnidadesMedida(salidasAlmacenDetalleAgregar.getUnidadesMedida());
            solicitudesSalidaAlmacenDetalleList.add(solicitudesSalidaDetalle); //salidasAlmacenDetalleAgregar

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String editarCantidadSalidasAlmacenDetalle_action() {
        try {
            Iterator i = solicitudesSalidaAlmacenDetalleList.iterator();
            while (i.hasNext()) {
                SolicitudesSalidaDetalle solicitudesSalidaDetalleItem = (SolicitudesSalidaDetalle) i.next();
                if (solicitudesSalidaDetalleItem.getChecked().booleanValue() == true) {
                    solicitudesSalidaDetalle = solicitudesSalidaDetalleItem;
                    break;
                }
            }
//            if(salidasAlmacenDetalle.getEquivalencias().getValorEquivalencia()>0){
//                salidasAlmacenDetalle.setCantidadSalidaAlmacenEquivalente( salidasAlmacenDetalle.getCantidadSalidaAlmacen()/
//                        salidasAlmacenDetalle.getEquivalencias().getValorEquivalencia());
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String eliminarDetalle_action() {
        try {
            List auxList = new ArrayList();
            Iterator i = solicitudesSalidaAlmacenDetalleList.iterator();
            while (i.hasNext()) {
                SolicitudesSalidaDetalle solicitudesSalidaDetalleItem = (SolicitudesSalidaDetalle) i.next();
                if (solicitudesSalidaDetalleItem.getChecked() != true) {
                    auxList.add(solicitudesSalidaDetalleItem);
                }
            }
            solicitudesSalidaAlmacenDetalleList = auxList;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String guardarEdicionCantidadMaterial_action() {
        try {

            //this.detalleEtiquetasSalidaAlmacen(salidasAlmacenDetalle);
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

    public int generaCodSolicitudSalidaAlmacen() {
        int codSolicitud = 0;
        ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select isnull(max(s.COD_FORM_SALIDA),0)+1 cod_solicitud from SOLICITUDES_SALIDA s ";
            System.out.println("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            if (rs.next()) {
                codSolicitud = rs.getInt("cod_solicitud");
            }
            con.close();
            st.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return codSolicitud;
    }

    public String guardarSolicitudSalidaAlmacen_action() throws SQLException
    {
        Connection con = null;
        solicitudesSalida.setCodFormSalida(this.generaCodSolicitudSalidaAlmacen());
        mensaje  = "";
        if(solicitudesSalidaAlmacenDetalleList.size() == 0 )
        {
            mensaje = "Debe registrar al menos un item en la solicitud";
        }
        
        try {
            con = Util.openConnection(con);
            con.setAutoCommit(false);
            Statement st = con.createStatement();
            ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String codEstadoSolicitudSalida = "1";
            if ((!this.existeIngresoAcondProgProd(solicitudesSalida.getComponentesProd().getCodCompprod(), solicitudesSalida.getCodLoteProduccion())) && this.verificaMaterialEmpaqueSecundario(solicitudesSalida) == true) {
                codEstadoSolicitudSalida = "5";
                mensaje = "Se registro la salida con estado por aprobar, porque no se han generado ingresos a acondicionamiento del lote y el producto";
            }
            if ((!this.verificarSalidasEpMp(solicitudesSalida.getComponentesProd().getCodCompprod(), solicitudesSalida.getCodLoteProduccion())) && this.verificaMaterialEmpaqueSecundario(solicitudesSalida) == true) {
                codEstadoSolicitudSalida = "5";
                mensaje = "Se registro la salida con estado por aprobar, porque no se han generado salidas de empaque primario y materia prima para el lote y el producto";
                //return null;
            }
            codEstadoSolicitudSalida = "1";
            mensaje = "";
            boolean aprobarPorGI = false;
            if (registroManual && !solicitudesSalida.getCodLoteProduccion().equals("") && usuario.getAlmacenesGlobal().getCodAlmacen() != 14) {
                programaProduccion = new ProgramaProduccion();
                programaProduccion.setCodLoteProduccion("");
                this.cargarProgramaProduccionSolicitudSalida(solicitudesSalida.getCodLoteProduccion());
                if (!solicitudesSalida.getCodLoteProduccion().equals("") && programaProduccion.getCodLoteProduccion().equals("")) {
                    aprobarPorGI = true;
                    mensaje = "La solicitud se genero con el estado aprobar por GI,ya que el lote introducido no existe en programa de producción";
                }
            }
            if (solicitudesSalida.getAreaDestinoSalida().getCodAreaEmpresa().equals("0")) {
                mensaje = "no se selecciono un area/departamento";
            }
            if (!codEstadoSolicitudSalida.equals("5")) {
                if (solicitudesSalida.getCodLoteProduccion().equals("")) {
                    codEstadoSolicitudSalida = "1"; //5
                } else {
                    codEstadoSolicitudSalida = "1";
                }
            }
            if (!emptyListEmpaqueSecundario) {
                System.out.println("J: Sin materiales de ES (solicitudes).");
                if(!usuario.getUsuarioModuloBean().getCodUsuarioGlobal().equals("826")){
                    solicitudesSalida.getPresentacionesProducto().setCodPresentacion("0");
                }
                else{
                    System.out.println("J: solicitud georgina");
                }
            }
                
            StringBuilder consulta = new StringBuilder(" INSERT INTO SOLICITUDES_SALIDA(  COD_GESTION,  COD_FORM_SALIDA,  COD_TIPO_SALIDA_ALMACEN,  COD_ESTADO_SOLICITUD_SALIDA_ALMACEN,")
                                                        .append("  SOLICITANTE,  AREA_DESTINO_SALIDA,  FECHA_SOLICITUD,")
                                                        .append(" COD_LOTE_PRODUCCION,  OBS_SOLICITUD,  ESTADO_SISTEMA,")
                                                        .append("  CONTROL_CALIDAD,  COD_INGRESO_ALMACEN,  COD_ALMACEN,  orden_trabajo,")
                                                        .append(" cod_compprod,cod_presentacion,cod_maquina,cod_area_instalacion,COD_ALMACEN_DESTINO) ")
                                                .append("VALUES ('").append(usuario.getGestionesGlobal().getCodGestion()).append("','").append(solicitudesSalida.getCodFormSalida()).append("','").append(codTipoSolicitudSalidaAlmacen).append("',").append(codEstadoSolicitudSalida).append(",")
                                                        .append("'").append(usuario.getUsuarioModuloBean().getCodUsuarioGlobal()).append("',  '").append(solicitudesSalida.getAreaDestinoSalida().getCodAreaEmpresa()).append("', GETDATE(),")
                                                        .append("'" ).append(solicitudesSalida.getCodLoteProduccion()).append("', ?, 1,")
                                                        .append("0,  0, '").append((solicitudesSalida.getAlmacenes().getCodAlmacen() == 0 ? usuario.getAlmacenesGlobal().getCodAlmacen() : solicitudesSalida.getAlmacenes().getCodAlmacen())).append("' , '").append(solicitudesSalida.getOrdenTrabajo()).append("',")
                                                        .append("'").append(solicitudesSalida.getComponentesProd().getCodCompprod()).append("','").append(solicitudesSalida.getPresentacionesProducto().getCodPresentacion()).append("','").append(solicitudesSalida.getMaquinaria().getCodMaquina()).append("','").append(solicitudesSalida.getAreasInstalaciones().getCodAreaInstalacion()).append("',")
                                                        .append(solicitudesSalida.getAlmacenesDestino().getCodAlmacen())
                                                .append(")");
            LOGGER.debug("consulta registrar solicitud manual " +consulta.toString());
            PreparedStatement pst = con.prepareStatement(consulta.toString());
            pst.setString(1, solicitudesSalida.getObsSolicitud());LOGGER.info("p1: "+solicitudesSalida.getObsSolicitud());
            if(pst.executeUpdate() > 0)LOGGER.info("se registro la solicitud manual");
            consulta = new StringBuilder(" INSERT INTO SOLICITUDES_SALIDA_DETALLE(  COD_FORM_SALIDA,  COD_MATERIAL,")
                                        .append(" CANTIDAD,  CANTIDAD_ENTREGADA,COD_UNIDAD_MEDIDA)")
                                .append(" VALUES (")
                                        .append(solicitudesSalida.getCodFormSalida()).append(",")
                                        .append("?,")//codMaterial
                                        .append("?,")//cantidad
                                        .append("0,")//cantidad entregada
                                        .append("?")//codUnidadMedida
                                .append(")");
            LOGGER.debug("consulta registrar detalle rd"+consulta.toString());        
            pst = con.prepareStatement(consulta.toString());
            Iterator i = solicitudesSalidaAlmacenDetalleList.iterator();
            boolean porAprobar = false;
            while (i.hasNext()) {
                SolicitudesSalidaDetalle solicitudesSalidaDetalleItem = (SolicitudesSalidaDetalle) i.next();
                if (solicitudesSalidaDetalleItem.getCantidad() > 0) {
                    pst.setString(1,solicitudesSalidaDetalleItem.getMateriales().getCodMaterial());LOGGER.info("p1 rd : "+solicitudesSalidaDetalleItem.getMateriales().getCodMaterial());
                    pst.setFloat(2,solicitudesSalidaDetalleItem.getCantidad());LOGGER.info("p2 rd : "+solicitudesSalidaDetalleItem.getCantidad());
                    pst.setInt(3,solicitudesSalidaDetalleItem.getUnidadesMedida().getCodUnidadMedida());LOGGER.info("p3 rd : "+solicitudesSalidaDetalleItem.getUnidadesMedida().getCodUnidadMedida());
                    if(pst.executeUpdate() > 0)LOGGER.info("se registro el detalle");
                }
            }
            if (aprobarPorGI && usuario.getAlmacenesGlobal().getCodAlmacen() != 14) {
                consulta = new StringBuilder(" UPDATE SOLICITUDES_SALIDA")
                                .append(" SET COD_ESTADO_SOLICITUD_SALIDA_ALMACEN=5")
                                .append(" WHERE COD_FORM_SALIDA='").append(solicitudesSalida.getCodFormSalida()).append("' ");
                LOGGER.debug(" consulta solicitud salida aprobar gi " + consulta.toString());
                pst=con.prepareStatement(consulta.toString());
                if(pst.executeUpdate() > 0)LOGGER.info("se cambio a aprobar por gi");
            }
                
            mensaje = "1";
            con.commit();
        }catch(SQLException ex)
        {
            mensaje = "Ocurrio un error al momento de guardar la solicitud,verifique que no existan items duplicados e intente de nuevo";
            LOGGER.warn("error", ex);
            con.rollback();
        }
        catch (Exception e) {
            mensaje = "Ocurrio un error al momento de guardar la solicitud,verifique que no existan items duplicados e intente de nuevo";
            LOGGER.warn("error", e);
        }
        finally
        {
            con.close();
        }
        
        return null;
    }

    public String estadoMaterial_change() {
        try {
            ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");

            NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
            DecimalFormat form = (DecimalFormat) nf;
            form.applyPattern("#.###");

            salidasAlmacenDetalleAgregar.setCantidadDisponible(Float.valueOf(form.format(managedSolicitudSalidaAlmacen.cantidadDisponibleMaterial(salidasAlmacenDetalleAgregar, ingresosAlmacenDetalleEstado, usuario.getAlmacenesGlobal()))));
            System.out.println("cantidad_disponible : " + salidasAlmacenDetalleAgregar.getCantidadDisponible());
            salidasAlmacenDetalleAgregar.setEquivalencias(this.buscaEquivalencia(salidasAlmacenDetalleAgregar));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String siguiente_action() {
        super.next();
        // this.cargarContenidoIngresoAlmacenOrdenCompra();
        return "";
    }

    public String atras_action() {
        super.back();
        // this.cargarContenidoIngresoAlmacenOrdenCompra();
        return "";
    }

    public String verReporteSolicitudSalidaAlmacen_action() {
//        Iterator i = solicitudesSalidaAlmacenList.iterator();
        SolicitudesSalida solicitudesSalida = (SolicitudesSalida) solicitudesSalidaAlmacenDataTable.getRowData();
//        while(i.hasNext()){
//            solicitudesSalida = (SolicitudesSalida)i.next();
//            if(solicitudesSalida.getChecked().booleanValue()==true){
//                break;
//            }
//        }
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map map = externalContext.getSessionMap();
        map.put("solicitudesSalida", solicitudesSalida);
        return null;
    }

    public String anularSolicitudSalidaAlmacen_action() {
        int codEstadoSolicitudSalidaAnuladaUsuario=9;
        try {
            Iterator i = solicitudesSalidaAlmacenList.iterator();
            SolicitudesSalida solicitudesSalida = new SolicitudesSalida();
            while (i.hasNext()) {
                solicitudesSalida = (SolicitudesSalida) i.next();
                if (solicitudesSalida.getChecked().booleanValue() == true) {
                    break;
                }
            }
            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement();
            String consulta = " update solicitudes_salida set cod_estado_solicitud_salida_almacen = "+codEstadoSolicitudSalidaAnuladaUsuario+
                              " where cod_form_salida = '" + solicitudesSalida.getCodFormSalida() + "' ";
            System.out.println(" consulta " + consulta);
            st.executeUpdate(consulta);
            solicitudesSalidaAlmacenList = this.cargarSolicitudesSalidaAlmacen();

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
                    + " and c.COD_COMPPROD='" + solicitudesSalida.getComponentesProd().getCodCompprod() + "' ";
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

    public String getCargarContenidoAprobarSolicitudesSalidaAlmacen() {
        try {
            solicitudesSalidaAlmacenAprobarList = this.cargarAprobarSolicitudesSalidaAlmacen();
            tiposSolicitudesSalidaAlmacenList = this.cargarTiposSalidaSolicitudAlmacen();
            estadosMaterialList = managedSolicitudSalidaAlmacen.cargarEstadosMaterial();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List cargarAprobarSolicitudesSalidaAlmacen() {
        List solicitudesSalidaAlmacenList = new ArrayList();
        try {
            Connection con = null;
            con = Util.openConnection(con);
            ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            String consulta = " select * from (select ROW_NUMBER() OVER (ORDER BY s.COD_FORM_SALIDA desc) as 'FILAS', s.COD_FORM_SALIDA,s.orden_trabajo ,s.FECHA_SOLICITUD,ae.COD_AREA_EMPRESA,ae.NOMBRE_AREA_EMPRESA "
                    + " ,t.COD_TIPO_SALIDA_ALMACEN,t.NOMBRE_TIPO_SALIDA_ALMACEN,p.NOMBRE_PILA,p.AP_PATERNO_PERSONAL,p.AP_MATERNO_PERSONAL,s.COD_LOTE_PRODUCCION, "
                    + " e.COD_ESTADO_SOLICITUD_SALIDA_ALMACEN,e.NOMBRE_ESTADO_SOLICITUD_SALIDA_ALMACEN,s.OBS_SOLICITUD "
                    + " from SOLICITUDES_SALIDA s  "
                    + " inner join GESTIONES gest on gest.COD_GESTION = s.COD_GESTION "
                    + " inner join TIPOS_SALIDAS_ALMACEN t on t.COD_TIPO_SALIDA_ALMACEN = s.COD_TIPO_SALIDA_ALMACEN "
                    + " inner join ESTADOS_SOLICITUD_SALIDAS_ALMACEN e on e.COD_ESTADO_SOLICITUD_SALIDA_ALMACEN = s.COD_ESTADO_SOLICITUD_SALIDA_ALMACEN "
                    + " inner join PERSONAL p on p.COD_PERSONAL = s.SOLICITANTE "
                    + " inner join AREAS_EMPRESA ae on ae.COD_AREA_EMPRESA = s.AREA_DESTINO_SALIDA "
                    + " left outer join INGRESOS_ALMACEN i on i.COD_INGRESO_ALMACEN = s.COD_INGRESO_ALMACEN"
                    + " where  s.cod_estado_solicitud_salida_almacen in(5,6)) as listado_solicitudes_salida_almacen "
                    + " where filas between " + begin + " and " + end + "  ";
            System.out.println("consulta " + consulta);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(consulta);
            solicitudesSalidaAlmacenList.clear();
            while (rs.next()) {
                SolicitudesSalida solicitudesSalida = new SolicitudesSalida();
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
                solicitudesSalidaAlmacenList.add(solicitudesSalida);
                if (solicitudesSalida.getEstadosSolicitudSalidasAlmacen().getCodEstadoSolicitudSalidaAlmacen() == 4) {
                    solicitudesSalida.setColorFila("#F5A9A9");
                }
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

    public String aprobarSolicitudSalidaAlmacen_action() {
        try {
            Iterator i = solicitudesSalidaAlmacenAprobarList.iterator();
            SolicitudesSalida solicitudesSalida = new SolicitudesSalida();
            while (i.hasNext()) {
                solicitudesSalida = (SolicitudesSalida) i.next();
                if (solicitudesSalida.getChecked().booleanValue() == true) {
                    break;
                }
            }
            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement();
            String consulta = " update solicitudes_salida set cod_estado_solicitud_salida_almacen = '1' where cod_form_salida = '" + solicitudesSalida.getCodFormSalida() + "' ";
            System.out.println(" consulta " + consulta);
            st.executeUpdate(consulta);
            solicitudesSalidaAlmacenAprobarList = this.cargarAprobarSolicitudesSalidaAlmacen();
            st.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String actualizarDatos_action() {
        setMensaje("");
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = "delete from ORDENES_COMPRA";
            PreparedStatement pst = con.prepareStatement(consulta);
            setMensaje("ocurrio un error al actualizar las ordenes de compra");
            if (pst.executeUpdate() > 0) {
                System.out.println("se eliminaron los registro de ordenes de compra");
            }
            consulta = "insert into ORDENES_COMPRA select * from SARTORIUS.dbo.ORDENES_COMPRA";
            pst = con.prepareStatement(consulta);
            if (pst.executeUpdate() > 0) {
                System.out.println("se insertaron los registro de ordenes de compra");
            }
            consulta = "delete from ORDENES_COMPRA_DETALLE";
            pst = con.prepareStatement(consulta);
            if (pst.executeUpdate() > 0) {
                System.out.println("se eliminaron los registro de ordenes de compra detalle");
            }
            consulta = "insert into ORDENES_COMPRA_DETALLE select * from SARTORIUS.dbo.ORDENES_COMPRA_DETALLE";
            pst = con.prepareStatement(consulta);
            if (pst.executeUpdate() > 0) {
                System.out.println("se insertaron los registros de las ordenes de compra detalle");
            }
            consulta = "delete from INGRESOS_ACOND";
            pst = con.prepareStatement(consulta);
            setMensaje("ocurrio un error al actualizar los ingresos de acondicionamiento");
            if (pst.executeUpdate() > 0) {
                System.out.println("se eliminaron los registro de ingresos acond ");
            }
            consulta = "insert into INGRESOS_ACOND select * from sartorius.dbo.INGRESOS_ACOND";
            pst = con.prepareStatement(consulta);
            if (pst.executeUpdate() > 0) {
                System.out.println("se insertaron los registro de ingresos acond");
            }
            consulta = "delete from INGRESOS_DETALLEACOND";
            pst = con.prepareStatement(consulta);
            if (pst.executeUpdate() > 0) {
                System.out.println("se eliminaron los registro de ingresos detalle acond");
            }
            consulta = "insert into INGRESOS_DETALLEACOND select * from sartorius.dbo.INGRESOS_DETALLEACOND";
            pst = con.prepareStatement(consulta);
            if (pst.executeUpdate() > 0) {
                System.out.println("se insertaron los registro de ingresos detalle acond");
            }
            consulta = "delete from SALIDAS_ACOND";
            pst = con.prepareStatement(consulta);
            setMensaje("ocurrio un error al actualizar las salidas de acondicionamiento");
            if (pst.executeUpdate() > 0) {
                System.out.println("se eliminaron los registro de salidas acond");
            }
            consulta = "insert into SALIDAS_ACOND select * from sartorius.dbo.SALIDAS_ACOND";
            pst = con.prepareStatement(consulta);
            if (pst.executeUpdate() > 0) {
                System.out.println("se insertaron los registros de salidas acond");
            }
            consulta = "delete from SALIDAS_DETALLEINGRESOACOND";
            pst = con.prepareStatement(consulta);
            if (pst.executeUpdate() > 0) {
                System.out.println("se eliminaron los registro salidas detalle acond");
            }
            consulta = "insert into SALIDAS_DETALLEINGRESOACOND select * from sartorius.dbo.SALIDAS_DETALLEINGRESOACOND";
            pst = con.prepareStatement(consulta);
            if (pst.executeUpdate() > 0) {
                System.out.println("se insertaron los registros de salidas detalle acond");
            }
            consulta = "delete from PROGRAMA_PRODUCCION_INGRESOS_ACOND";
            pst = con.prepareStatement(consulta);
            setMensaje("ocurrio un error al actualizar el programa produccion de los ingresos de acondicionamiento");
            if (pst.executeUpdate() > 0) {
                System.out.println("se eliminaron los registro de programa produccion ingresos acond");
            }
            consulta = "insert into PROGRAMA_PRODUCCION_INGRESOS_ACOND select * from sartorius.dbo.PROGRAMA_PRODUCCION_INGRESOS_ACOND";
            pst = con.prepareStatement(consulta);
            if (pst.executeUpdate() > 0) {
                System.out.println("se insertaron los registro de programa produccion ingresos acond");
            }
            consulta = "delete from COMPONENTES_PROD";
            pst = con.prepareStatement(consulta);
            setMensaje("ocurrio un error al actualizar los componentes de los productos");
            if (pst.executeUpdate() > 0) {
                System.out.println("se eliminaron los registro de componentes prod");
            }
            consulta = "INSERT INTO COMPONENTES_PROD SELECT * FROM SARTORIUS.dbo.COMPONENTES_PROD";
            pst = con.prepareStatement(consulta);
            if (pst.executeUpdate() > 0) {
                System.out.println("se insertaron los registros de componentes prod");
            }
            consulta = "delete from PRESENTACIONES_PRODUCTO";
            pst = con.prepareStatement(consulta);
            setMensaje("ocurrio un error al actualizar las presentaciones de los productos");
            if (pst.executeUpdate() > 0) {
                System.out.println("se eliminaron los registro de presentaciones producto");
            }
            consulta = "INSERT INTO PRESENTACIONES_PRODUCTO SELECT * FROM SARTORIUS.dbo.PRESENTACIONES_PRODUCTO";
            pst = con.prepareStatement(consulta);
            if (pst.executeUpdate() > 0) {
                System.out.println("se insertaron loes registros de presentaciones producto");
            }
            consulta = "delete from COMPONENTES_PRESPROD";
            pst = con.prepareStatement(consulta);
            setMensaje("ocurrio un error al actualizar las componentes de una presentacion");
            if (pst.executeUpdate() > 0) {
                System.out.println("se eliminaron los registro de componentes presprod");
            }
            consulta = "INSERT INTO COMPONENTES_PRESPROD SELECT * FROM SARTORIUS.dbo.COMPONENTES_PRESPROD";
            pst = con.prepareStatement(consulta);
            if (pst.executeUpdate() > 0) {
                System.out.println("se insertaron los registros de componentes presprod ");
            }
            consulta = "delete from PROGRAMA_PRODUCCION";
            pst = con.prepareStatement(consulta);
            pst.executeUpdate();
            consulta = "INSERT INTO  PROGRAMA_PRODUCCION SELECT * FROM SARTORIUS.dbo.PROGRAMA_PRODUCCION";
            pst = con.prepareStatement(consulta);
            pst.executeUpdate();
            consulta = "delete from PROGRAMA_PRODUCCION_DETALLE";
            pst = con.prepareStatement(consulta);
            pst.executeUpdate();
            consulta = "INSERT INTO  PROGRAMA_PRODUCCION_DETALLE SELECT * FROM SARTORIUS.dbo.PROGRAMA_PRODUCCION_DETALLE";
            pst = con.prepareStatement(consulta);
            pst.executeUpdate();
            consulta = "delete from materiales";
            pst = con.prepareStatement(consulta);
            pst.executeUpdate();
            consulta = "insert into materiales SELECT * FROM SARTORIUS.dbo.materiales ";
            pst = con.prepareStatement(consulta);
            pst.executeUpdate();
            pst.close();
            con.close();
            setMensaje("Todos los datos han sido actualizados con exito");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    //inicio ale validacion salidas
    public boolean verificarSalidasEpMp(String codComprod, String codLote) {
        Connection con = null;
        boolean resultado = false;
        String consulta = "select top 1 s.* from SALIDAS_ALMACEN s inner join SALIDAS_ALMACEN_DETALLE sd on sd.COD_SALIDA_ALMACEN = s.COD_SALIDA_ALMACEN"
                + " inner join FORMULA_MAESTRA fm on fm.COD_COMPPROD = s.COD_PROD and fm.COD_ESTADO_REGISTRO = 1"
                + " inner join FORMULA_MAESTRA_DETALLE_MP fdmp on fdmp.COD_FORMULA_MAESTRA = fm.COD_FORMULA_MAESTRA "
                + " and fdmp.COD_MATERIAL = sd.COD_MATERIAL and fdmp.COD_UNIDAD_MEDIDA = sd.COD_UNIDAD_MEDIDA"
                + " where (s.COD_LOTE_PRODUCCION = '" + codLote + "' "
                + " or s.COD_LOTE_PRODUCCION in (select LPC.COD_LOTE_PRODUCCION "
                + " from LOTES_PRODUCCION_CONJUNTA LPC"
                + " where LPC.COD_LOTE_PRODUCCION_ASOCIADO='" + codLote + "'))"
                + " and s.COD_ESTADO_SALIDA_ALMACEN = 1 and s.ESTADO_SISTEMA = 1"
                + " and s.COD_PROD = '" + codComprod + "'";
        System.out.println("consulta verificar salidas MP " + consulta);
        try {
            con = Util.openConnection(con);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet res = st.executeQuery(consulta);
            if (res.next()) {
                resultado = true;
            } else {
                consulta = "select top 1 s.* from SALIDAS_ALMACEN s inner join SALIDAS_ALMACEN_DETALLE sd on sd.COD_SALIDA_ALMACEN = s.COD_SALIDA_ALMACEN"
                        + " inner join FORMULA_MAESTRA fm on fm.COD_COMPPROD = s.COD_PROD and fm.COD_ESTADO_REGISTRO = 1"
                        + " inner join FORMULA_MAESTRA_DETALLE_EP fmde on fmde.COD_FORMULA_MAESTRA=fm.COD_FORMULA_MAESTRA"
                        + " and fmde.COD_MATERIAL=sd.COD_MATERIAL and fmde.COD_UNIDAD_MEDIDA=sd.COD_UNIDAD_MEDIDA"
                        + " where s.COD_LOTE_PRODUCCION = '" + codLote + "' and s.COD_ESTADO_SALIDA_ALMACEN = 1 and s.ESTADO_SISTEMA = 1"
                        + " and s.COD_PROD = '" + codComprod + "'";
                System.out.println("consulta veficar salidas EP" + consulta);
                res = st.executeQuery(consulta);
                if (res.next()) {
                    resultado = true;
                } else {
                    resultado = false;
                }
            }

            res.close();
            st.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return resultado;
    }

    public boolean existeIngresoAcondProgProd(String codComprod, String codLoteProduccion) {
        Connection con1 = null;
        boolean existe = false;
        String consulta = "select top 1 * from PROGRAMA_PRODUCCION_INGRESOS_ACOND ppid inner join INGRESOS_ACOND ia"
                + " on ppid.COD_INGRESO_ACOND=ia.COD_INGRESO_ACOND where ia.COD_ESTADO_INGRESOACOND<>2 and "
                + " ppid.COD_COMPPROD='" + codComprod + "' and ppid.COD_LOTE_PRODUCCION='" + codLoteProduccion + "'";
        System.out.println("consulta verificar ingreso acond prog prod " + consulta);
        try {
            con1 = Util.openConnection(con1);
            Statement st = con1.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet res = st.executeQuery(consulta);
            existe = res.next();
            res.close();
            st.close();
            con1.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return existe;
    }

    public boolean verificaMaterialEmpaqueSecundario(SolicitudesSalida solicitudesSalida) {
        boolean tieneMaterialSecundario = false;
        try {
            Iterator i = solicitudesSalidaAlmacenDetalleList.iterator();
            String codMaterial = "";
            while (i.hasNext()) {
                SolicitudesSalidaDetalle solicitudesSalidaDetalle = (SolicitudesSalidaDetalle) i.next();
                codMaterial = codMaterial + "," + solicitudesSalidaDetalle.getMateriales().getCodMaterial();
            }
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select top 1 * from formula_maestra_detalle_es fd inner join formula_maestra f on f.cod_formula_maestra = fd.cod_formula_maestra"
                    + " where f.cod_compprod = '" + solicitudesSalida.getComponentesProd().getCodCompprod() + "' and fd.cod_material in (0" + codMaterial + ") and f.cod_estado_registro = 1 ";
            System.out.println(" consulta " + consulta);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(consulta);
            if (rs.next()) {
                tieneMaterialSecundario = true;
            }
            rs.close();
            st.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tieneMaterialSecundario;
    }

    public List cargarCapitulosPermitidos(ManagedAccesoSistema usuario) {
        List capitulosList = new ArrayList();
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select c.COD_CAPITULO,c.NOMBRE_CAPITULO  from CAPITULOS c inner join  CONFIGURACION_SOLICITUD_SALIDA_ALMACEN cssa on cssa.COD_CAPITULO = c.COD_CAPITULO where c.COD_ESTADO_REGISTRO = 1 and cssa.cod_personal = '" + usuario.getUsuarioModuloBean().getCodUsuarioGlobal() + "'";
            System.out.println("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            capitulosList.clear();
            capitulosList.add(new SelectItem("0", "-TODOS-"));
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

    public String getCargarContenidoRegistroSolicitudPersonalSalidaAlmacen() {
        try {
            areasEmpresaList = managedSalidaAlmacen.cargarAreasEmpresa();
            componentesProdList = managedSalidaAlmacen.cargarComponentesProd();
            ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            capitulosList = this.cargarCapitulosPermitidos(usuario);
            solicitudesSalida.getSolicitante().setNombrePersonal(usuario.getUsuarioModuloBean().getNombrePilaUsuarioGlobal());
            solicitudesSalida.getSolicitante().setApPaternoPersonal(usuario.getUsuarioModuloBean().getApPaternoUsuarioGlobal());
            solicitudesSalida.getSolicitante().setApMaternoPersonal(usuario.getUsuarioModuloBean().getApMaternoUsuarioGlobal());
            solicitudesSalida.getSolicitante().setCodPersonal(usuario.getUsuarioModuloBean().getCodUsuarioGlobal());
            solicitudesSalida.setCodFormSalida(this.generaCodSolicitudSalidaAlmacen());
            gruposList.clear();
            gruposList.add(new SelectItem("0", "-NINGUNO-"));
            solicitudesSalidaAlmacenDetalleList.clear();
            this.producto_changed();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String buscarMaterial_action1() {
        try {
            ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            System.out.println("entro al change");
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select m.COD_MATERIAL,m.NOMBRE_MATERIAL,gr.NOMBRE_GRUPO,cap.NOMBRE_CAPITULO,"
                    + " m.COD_UNIDAD_MEDIDA,u1.ABREVIATURA ABREVIATURA1 ,m.COD_UNIDAD_MEDIDA_COMPRA,u2.ABREVIATURA ABREVIATURA2  "
                    + " from MATERIALES m  "
                    + " inner join GRUPOS gr on gr.COD_GRUPO = m.COD_GRUPO "
                    + " inner join CAPITULOS cap on cap.COD_CAPITULO = gr.COD_CAPITULO "
                    + " inner join configuracion_solicitud_salida_almacen cf on cf.cod_capitulo = cap.cod_capitulo and cf.cod_personal = '" + usuario.getUsuarioModuloBean().getCodUsuarioGlobal() + "'"
                    + " inner join UNIDADES_MEDIDA u1 on u1.COD_UNIDAD_MEDIDA = m.COD_UNIDAD_MEDIDA "
                    + " inner join UNIDADES_MEDIDA u2 on u2.COD_UNIDAD_MEDIDA = m.COD_UNIDAD_MEDIDA_COMPRA  "
                    + " where m.NOMBRE_MATERIAL like '%" + buscarMaterial.getNombreMaterial() + "%' ";
            if (buscarMaterial.getGrupos().getCodGrupo() > 0) {
                consulta = consulta + " and gr.COD_GRUPO = '" + buscarMaterial.getGrupos().getCodGrupo() + "' ";
            }
            if (buscarMaterial.getGrupos().getCapitulos().getCodCapitulo() > 0) {
                consulta = consulta + " and cap.COD_CAPITULO = '" + buscarMaterial.getGrupos().getCapitulos().getCodCapitulo() + "' ";
            }
            consulta = consulta + " and m.COD_MATERIAL not in (" + this.itemsSeleccionadosSalidaAlmacen() + ")";
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

    public String guardarSolicitudPersonalSalidaAlmacen_action() {
        try {
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            Map<String, Object> sessionMap = externalContext.getSessionMap();
            Map<String, String> params = externalContext.getRequestParameterMap();
            solicitudesSalida.getEstadosSolicitudSalidasAlmacen().setCodEstadoSolicitudSalidaAlmacen(params.get("codEstadoSolicitudSalida") == null ? 1 : Integer.valueOf(params.get("codEstadoSolicitudSalida")));
            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement();
            ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            solicitudesSalida.setCodFormSalida(this.generaCodSolicitudSalidaAlmacen());
            //1 inicio alejandro
            mensaje = "";

            if (!solicitudesSalida.getAreaDestinoSalida().getCodAreaEmpresa().equals("0")) {
                System.out.println("cod " + solicitudesSalida.getAreaDestinoSalida().getCodAreaEmpresa());
                String consulta = " INSERT INTO SOLICITUDES_SALIDA(  COD_GESTION,  COD_FORM_SALIDA,  COD_TIPO_SALIDA_ALMACEN,  COD_ESTADO_SOLICITUD_SALIDA_ALMACEN,"
                        + "  SOLICITANTE,  AREA_DESTINO_SALIDA,  FECHA_SOLICITUD,  COD_LOTE_PRODUCCION,  OBS_SOLICITUD,  ESTADO_SISTEMA,"
                        + "  CONTROL_CALIDAD,  COD_INGRESO_ALMACEN,  COD_ALMACEN,  orden_trabajo,cod_compprod,cod_presentacion) "
                        + "VALUES (  '" + usuario.getGestionesGlobal().getCodGestion() + "','" + solicitudesSalida.getCodFormSalida() + "','2'"
                        + ",'" + solicitudesSalida.getEstadosSolicitudSalidasAlmacen().getCodEstadoSolicitudSalidaAlmacen() + "','" + usuario.getUsuarioModuloBean().getCodUsuarioGlobal() + "',  '" + solicitudesSalida.getAreaDestinoSalida().getCodAreaEmpresa() + "'"
                        + ", '" + sdf.format(new Date()) + "', '" + solicitudesSalida.getCodLoteProduccion() + "', '" + solicitudesSalida.getObsSolicitud() + "', 1,"
                        + "  0,  0, '" + usuario.getAlmacenesGlobal().getCodAlmacen() + "' , '" + solicitudesSalida.getOrdenTrabajo() + "'"
                        + ",'" + solicitudesSalida.getComponentesProd().getCodCompprod() + "','" + solicitudesSalida.getPresentacionesProducto().getCodPresentacion() + "'); ";
                System.out.println("consulta " + consulta);
                //1final alejandro
                st.executeUpdate(consulta);
                Iterator i = solicitudesSalidaAlmacenDetalleList.iterator();

                while (i.hasNext()) {
                    SolicitudesSalidaDetalle solicitudesSalidaDetalleItem = (SolicitudesSalidaDetalle) i.next();

                    consulta = " INSERT INTO SOLICITUDES_SALIDA_DETALLE(  COD_FORM_SALIDA,  COD_MATERIAL,  CANTIDAD,  CANTIDAD_ENTREGADA,"
                            + "  COD_UNIDAD_MEDIDA) VALUES (  '" + solicitudesSalida.getCodFormSalida() + "', '" + solicitudesSalidaDetalleItem.getMateriales().getCodMaterial() + "',  '" + solicitudesSalidaDetalleItem.getCantidad() + "',  0,'" + solicitudesSalidaDetalleItem.getUnidadesMedida().getCodUnidadMedida() + "'); ";
                    System.out.println("consulta " + consulta);
                    st.executeUpdate(consulta);
                    // if(solicitudesSalidaDetalleItem.getCantidad()>solicitudesSalidaDetalleItem.getCantidadMaximaSolicitud()){
                    //   porAprobar=true;
                    //}
                }
                //if(porAprobar==true){
                //  consulta = " update solicitudes_salida set cod_estado_solicitud_salida_almacen = '5' where cod_form_salida = '"+solicitudesSalida.getCodFormSalida()+"' ";
                // System.out.println("consulta"+consulta);
                //  st.executeUpdate(consulta);
                //}
                st.close();
                con.close();
                //inicio alejandro
            } else {
                mensaje = "no se selecciono un area/departamento";
                // System.out.println(mensaje);
            }
            //final alejandro
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //final ale validacion salidas
    public String imprimirSolicitudSalidaAlmacen_action() {
        try {
            String codFormSalida = "";
            Iterator i = solicitudesSalidaAlmacenList.iterator();
            SolicitudesSalida solicitudesSalida = new SolicitudesSalida();
            while (i.hasNext()) {
                solicitudesSalida = (SolicitudesSalida) i.next();
                if (solicitudesSalida.getChecked().booleanValue() == true) {
                    codFormSalida = codFormSalida + "," + String.valueOf(solicitudesSalida.getCodFormSalida());
                }
            }
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            Map<String, Object> sessionMap = externalContext.getSessionMap();
            sessionMap.put("codFormSalida", codFormSalida);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean cantidadSalidaManualNoPermitida(Connection con, SolicitudesSalidaDetalle solicitudSalidaDetalle) throws SQLException {
        boolean noPermitido = true;
        boolean noEncontrado = true;
        System.out.println("c i" + programaProduccion.getCantIngresoAcond() + " clo " + programaProduccion.getFormulaMaestra().getCantidadLote());
        double cantidadProrateo = 0;
        Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        String consulta = "select f.CANTIDAD from FORMULA_MAESTRA_DETALLE_MP f "
                + " where f.COD_FORMULA_MAESTRA='" + programaProduccion.getFormulaMaestra().getCodFormulaMaestra() + "'"
                + " and f.COD_MATERIAL='" + solicitudSalidaDetalle.getMateriales().getCodMaterial() + "'";
        System.out.println("consulta verificar MP " + consulta);
        ResultSet res = st.executeQuery(consulta);
        double cantidadFormula = 0d;
        double cantidadFormulaPorciento = 0d;
        if (res.next()) {
            cantidadProrateo = (Double.valueOf(programaProduccion.getCantidadLote().replace(",", "")) / Double.parseDouble(programaProduccion.getFormulaMaestra().getCantidadLote().replace(",", "")));
            cantidadFormula = res.getDouble("CANTIDAD");
            cantidadFormulaPorciento = cantidadFormula * 0.02;
            noPermitido = (((solicitudSalidaDetalle.getCantidad() + solicitudSalidaDetalle.getCantidadEntregada()) > (cantidadFormulaPorciento + cantidadFormula))
                    || ((solicitudSalidaDetalle.getCantidad() + solicitudSalidaDetalle.getCantidadEntregada()) < (cantidadFormula - cantidadFormulaPorciento)));
            noEncontrado = false;
        }
        if (noEncontrado) {
            consulta = "select f.CANTIDAD from FORMULA_MAESTRA_DETALLE_EP f "
                    + " where f.COD_FORMULA_MAESTRA='" + programaProduccion.getFormulaMaestra().getCodFormulaMaestra() + "'"
                    + " and f.COD_MATERIAL='" + solicitudSalidaDetalle.getMateriales().getCodMaterial() + "'";
            System.out.println("consulta verificar EP " + consulta);
            cantidadFormula = 0d;
            cantidadFormulaPorciento = 0d;
            res = st.executeQuery(consulta);
            if (res.next()) {
                cantidadProrateo = (Double.valueOf(programaProduccion.getCantidadLote().replace(",", "")) / Double.parseDouble(programaProduccion.getFormulaMaestra().getCantidadLote().replace(",", "")));
                cantidadFormula = res.getDouble("CANTIDAD");
                cantidadFormulaPorciento = cantidadFormula * 0.02;
                noPermitido = (((solicitudSalidaDetalle.getCantidad() + solicitudSalidaDetalle.getCantidadEntregada()) > (cantidadFormulaPorciento + cantidadFormula))
                        || ((solicitudSalidaDetalle.getCantidad() + solicitudSalidaDetalle.getCantidadEntregada()) < (cantidadFormula - cantidadFormulaPorciento)));
                noEncontrado = false;
            }
        }
        if (noEncontrado) {
            cantidadProrateo = (programaProduccion.getCantIngresoAcond() > 0 ? (programaProduccion.getCantIngresoAcond() / Double.parseDouble(programaProduccion.getFormulaMaestra().getCantidadLote().replace(",", ""))) : 0);
            consulta = "select f.CANTIDAD from FORMULA_MAESTRA_DETALLE_ES f "
                    + " where f.COD_FORMULA_MAESTRA='" + programaProduccion.getFormulaMaestra().getCodFormulaMaestra() + "'"
                    + " and f.COD_MATERIAL='" + solicitudSalidaDetalle.getMateriales().getCodMaterial() + "'";
            System.out.println("consulta verificar ES " + consulta);
            cantidadFormula = 0d;
            cantidadFormulaPorciento = 0d;
            res = st.executeQuery(consulta);
            if (res.next()) {
                cantidadFormula = ((res.getDouble("CANTIDAD") * cantidadProrateo) > 0 ? (res.getDouble("CANTIDAD") * cantidadProrateo) : res.getDouble("CANTIDAD"));
                cantidadFormulaPorciento = cantidadFormula * 0.02;
                noPermitido = (((solicitudSalidaDetalle.getCantidad() + solicitudSalidaDetalle.getCantidadEntregada()) > (cantidadFormulaPorciento + cantidadFormula))
                        || ((solicitudSalidaDetalle.getCantidad() + solicitudSalidaDetalle.getCantidadEntregada()) < (cantidadFormula - cantidadFormulaPorciento)));
                noEncontrado = false;
            }
        }
        res.close();
        st.close();

        return noPermitido;
    }

    private boolean cantidadSalidaNoPermitida(Connection con, SolicitudesSalidaDetalle solicitudSalidaDetalle) throws SQLException {
        boolean noPermitido = true;
        System.out.println("entro");
        if (solicitudSalidaDetalle.getMateriales().getMaterialProduccion() > 0 && solicitudSalidaDetalle.getMateriales().getMaterialMuestreo() < 4) {
            System.out.println("ci " + programaProduccion.getCantIngresoAcond() + " cl " + programaProduccion.getFormulaMaestra().getCantidadLote());
            double cantidadProrateo = programaProduccion.getCantIngresoAcond() / Double.parseDouble(programaProduccion.getFormulaMaestra().getCantidadLote().replace(",", ""));
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String consulta = "select f.CANTIDAD from " + (solicitudSalidaDetalle.getMateriales().getMaterialProduccion() == 1 ? "FORMULA_MAESTRA_DETALLE_MP"
                    : (solicitudSalidaDetalle.getMateriales().getMaterialProduccion() == 2 ? "FORMULA_MAESTRA_DETALLE_EP" : "FORMULA_MAESTRA_DETALLE_ES")) + " f"
                    + " where f.COD_FORMULA_MAESTRA='" + programaProduccion.getFormulaMaestra().getCodFormulaMaestra() + "'"
                    + " and f.COD_MATERIAL='" + solicitudSalidaDetalle.getMateriales().getCodMaterial() + "'";
            System.out.println("consulta cantidad formula maestra" + consulta);
            ResultSet res = st.executeQuery(consulta);
            double cantidadFormula = 0d;
            double cantidadFormulaPorciento = 0d;
            if (res.next()) {

                cantidadFormula = ((res.getDouble("CANTIDAD") * cantidadProrateo) > 0 ? (res.getDouble("CANTIDAD") * cantidadProrateo) : res.getDouble("CANTIDAD"));
                cantidadFormula = solicitudSalidaDetalle.getMateriales().getMaterialProduccion() == 3 ? this.redondear(cantidadFormula, 0) : this.redondear(cantidadFormula, 2);
                cantidadFormulaPorciento = cantidadFormula * 0.02;
                cantidadFormulaPorciento = solicitudSalidaDetalle.getMateriales().getMaterialProduccion() == 3 ? this.redondear(cantidadFormulaPorciento, 0) : this.redondear(cantidadFormulaPorciento, 2);
                System.out.println("cantidad 1 " + (solicitudSalidaDetalle.getCantidad() + solicitudSalidaDetalle.getCantidadEntregada()) + " cantidad por " + cantidadFormulaPorciento + " cs " + cantidadFormula);
                noPermitido = (((solicitudSalidaDetalle.getCantidad() + solicitudSalidaDetalle.getCantidadEntregada()) > (cantidadFormulaPorciento + cantidadFormula))
                        || ((solicitudSalidaDetalle.getCantidad() + solicitudSalidaDetalle.getCantidadEntregada()) < (cantidadFormula - cantidadFormulaPorciento)));
            }
            res.close();
            st.close();
        }
        return noPermitido;
    }

    public void cargarProgramaProduccionSolicitudSalida(String codLote) {
        try {
            Connection con1 = null;
            con1 = Util.openConnection(con1);
            Statement st = con1.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String consulta = "select isnull(SUM(id.CANT_INGRESO_PRODUCCION),0) as cantidadIngreso, pp.COD_LOTE_PRODUCCION,"
                    + " pp.COD_FORMULA_MAESTRA,pp.COD_COMPPROD,pp.COD_PROGRAMA_PROD,pp.COD_TIPO_PROGRAMA_PROD,fm.CANTIDAD_LOTE,pp.CANT_LOTE_PRODUCCION"
                    + " from PROGRAMA_PRODUCCION pp inner join FORMULA_MAESTRA fm on fm.COD_FORMULA_MAESTRA=pp.COD_FORMULA_MAESTRA left outer join  PROGRAMA_PRODUCCION_INGRESOS_ACOND ppia on "
                    + " ppia.COD_LOTE_PRODUCCION=pp.COD_LOTE_PRODUCCION and pp.COD_COMPPROD=ppia.COD_COMPPROD"
                    + " and pp.COD_FORMULA_MAESTRA=ppia.COD_FORMULA_MAESTRA and pp.COD_TIPO_PROGRAMA_PROD=ppia.COD_TIPO_PROGRAMA_PROD"
                    + " and pp.COD_PROGRAMA_PROD=ppia.COD_PROGRAMA_PROD left outer join INGRESOS_DETALLEACOND id on id.COD_COMPPROD=pp.COD_COMPPROD"
                    + " and id.COD_LOTE_PRODUCCION=ppia.COD_LOTE_PRODUCCION and ppia.COD_INGRESO_ACOND=id.COD_INGRESO_ACOND "
                    + " where pp.COD_LOTE_PRODUCCION = '" + codLote + "'"
                    + " and  pp.cod_estado_programa in (1, 2, 6, 7)"
                    + " group by fm.CANTIDAD_LOTE,pp.CANT_LOTE_PRODUCCION,pp.COD_LOTE_PRODUCCION,pp.COD_FORMULA_MAESTRA,pp.COD_COMPPROD,pp.COD_PROGRAMA_PROD,pp.COD_TIPO_PROGRAMA_PROD "
                    + " order by isnull(SUM(id.CANT_INGRESO_PRODUCCION), 0) desc";
            System.out.println("consulta buscar programa produccion salida manual " + consulta);
            ResultSet res = st.executeQuery(consulta);

            if (res.next()) {
                programaProduccion.getFormulaMaestra().setCodFormulaMaestra(res.getString("COD_FORMULA_MAESTRA"));
                programaProduccion.setCodProgramaProduccion(res.getString("COD_PROGRAMA_PROD"));
                programaProduccion.getFormulaMaestra().getComponentesProd().setCodCompprod(res.getString("COD_COMPPROD"));
                programaProduccion.getTiposProgramaProduccion().setCodTipoProgramaProd(res.getString("COD_TIPO_PROGRAMA_PROD"));
                programaProduccion.setCodLoteProduccion(res.getString("COD_LOTE_PRODUCCION"));
                programaProduccion.setCantidadLote(res.getString("CANT_LOTE_PRODUCCION"));
                programaProduccion.getFormulaMaestra().setCantidadLote(res.getString("CANTIDAD_LOTE"));
                programaProduccion.setCantIngresoAcond(res.getDouble("cantidadIngreso"));
            }
            res.close();
            st.close();
            con1.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List cargarMaquinarias(String codAreaEmpresa) {
        List maquinariaList = new ArrayList();
        try {

            String consulta = "select m.cod_maquina,m.NOMBRE_MAQUINA + '( '+ m.CODIGO +' ) ' nombre_maquina from maquinarias m where m.COD_ESTADO_REGISTRO = 1 and m.cod_area_empresa = '" + codAreaEmpresa + "'"
                    + " order by m.NOMBRE_MAQUINA ";
            System.out.println("consulta " + consulta);

            Connection con = null;
            con = Util.openConnection(con);

            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);

            maquinariaList.clear();
            //maquinariasList.add(new SelectItem("0","-TODOS-"));
            while (rs.next()) {
                maquinariaList.add(new SelectItem(rs.getString("cod_maquina"), rs.getString("nombre_maquina")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return maquinariaList;
    }

    public String area_change() {
        maquinariaList = this.cargarMaquinarias(solicitudesSalida.getAreaDestinoSalida().getCodAreaEmpresa());
        System.out.println("*J Area select: "+solicitudesSalida.getAreaDestinoSalida().getCodAreaEmpresa());
        if(solicitudesSalida.getAreaDestinoSalida().getCodAreaEmpresa().equals("71")||solicitudesSalida.getAreaDestinoSalida().getCodAreaEmpresa().equals("77")){
                capitulosList = this.cargarCapitulos();
            }
            else{
                capitulosList = this.cargarCapitulosAlmacen();
            }
        return null;
    }

    public double redondear(double numero, int decimales) {
        return Math.round(numero * Math.pow(10, decimales)) / Math.pow(10, decimales);
    }

    public List getSolicitudesSalidaAlmacenList() {
        return solicitudesSalidaAlmacenList;
    }

    public void setSolicitudesSalidaAlmacenList(List solicitudesSalidaAlmacenList) {
        this.solicitudesSalidaAlmacenList = solicitudesSalidaAlmacenList;
    }

    public List getTiposSolicitudesSalidaAlmacenList() {
        return tiposSolicitudesSalidaAlmacenList;
    }

    public void setTiposSolicitudesSalidaAlmacenList(List tiposSolicitudesSalidaAlmacenList) {
        this.tiposSolicitudesSalidaAlmacenList = tiposSolicitudesSalidaAlmacenList;
    }

    public int getCodTipoSolicitudSalidaAlmacen() {
        return codTipoSolicitudSalidaAlmacen;
    }

    public void setCodTipoSolicitudSalidaAlmacen(int codTipoSolicitudSalidaAlmacen) {
        this.codTipoSolicitudSalidaAlmacen = codTipoSolicitudSalidaAlmacen;
    }

    public String getDireccionPagina() {
        return direccionPagina;
    }

    public void setDireccionPagina(String direccionPagina) {
        this.direccionPagina = direccionPagina;
    }

    public List<SelectItem> getAlmacenDestinoTraspasoList() {
        return almacenDestinoTraspasoList;
    }

    public void setAlmacenDestinoTraspasoList(List<SelectItem> almacenDestinoTraspasoList) {
        this.almacenDestinoTraspasoList = almacenDestinoTraspasoList;
    }
    

    public List getAreasEmpresaList() {
        return areasEmpresaList;
    }

    public void setAreasEmpresaList(List areasEmpresaList) {
        this.areasEmpresaList = areasEmpresaList;
    }

    public SolicitudesSalida getSolicitudesSalida() {
        return solicitudesSalida;
    }

    public void setSolicitudesSalida(SolicitudesSalida solicitudesSalida) {
        this.solicitudesSalida = solicitudesSalida;
    }

    public List getCapitulosList() {
        return capitulosList;
    }

    public void setCapitulosList(List capitulosList) {
        this.capitulosList = capitulosList;
    }

    public Materiales getBuscarMaterial() {
        return buscarMaterial;
    }

    public void setBuscarMaterial(Materiales buscarMaterial) {
        this.buscarMaterial = buscarMaterial;
    }

    public List getGruposList() {
        return gruposList;
    }

    public void setGruposList(List gruposList) {
        this.gruposList = gruposList;
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

    public List getSolicitudesSalidaAlmacenDetalleList() {
        return solicitudesSalidaAlmacenDetalleList;
    }

    public void setSolicitudesSalidaAlmacenDetalleList(List solicitudesSalidaAlmacenDetalleList) {
        this.solicitudesSalidaAlmacenDetalleList = solicitudesSalidaAlmacenDetalleList;
    }

    public SalidasAlmacenDetalle getSalidasAlmacenDetalleAgregar() {
        return salidasAlmacenDetalleAgregar;
    }

    public void setSalidasAlmacenDetalleAgregar(SalidasAlmacenDetalle salidasAlmacenDetalleAgregar) {
        this.salidasAlmacenDetalleAgregar = salidasAlmacenDetalleAgregar;
    }

    public IngresosAlmacenDetalleEstado getIngresosAlmacenDetalleEstado() {
        return ingresosAlmacenDetalleEstado;
    }

    public void setIngresosAlmacenDetalleEstado(IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstado) {
        this.ingresosAlmacenDetalleEstado = ingresosAlmacenDetalleEstado;
    }

    public List getEstadosMaterialList() {
        return estadosMaterialList;
    }

    public void setEstadosMaterialList(List estadosMaterialList) {
        this.estadosMaterialList = estadosMaterialList;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public SalidasAlmacenDetalle getSalidasAlmacenDetalle() {
        return salidasAlmacenDetalle;
    }

    public void setSalidasAlmacenDetalle(SalidasAlmacenDetalle salidasAlmacenDetalle) {
        this.salidasAlmacenDetalle = salidasAlmacenDetalle;
    }

    public HtmlDataTable getSolicitudesSalidaAlmacenDataTable() {
        return solicitudesSalidaAlmacenDataTable;
    }

    public void setSolicitudesSalidaAlmacenDataTable(HtmlDataTable solicitudesSalidaAlmacenDataTable) {
        this.solicitudesSalidaAlmacenDataTable = solicitudesSalidaAlmacenDataTable;
    }

    public List getComponentesProdList() {
        return componentesProdList;
    }

    public void setComponentesProdList(List componentesProdList) {
        this.componentesProdList = componentesProdList;
    }

    public List getPresentacionesList() {
        return presentacionesList;
    }

    public void setPresentacionesList(List presentacionesList) {
        this.presentacionesList = presentacionesList;
    }

    public SolicitudesSalidaDetalle getSolicitudesSalidaDetalle() {
        return solicitudesSalidaDetalle;
    }

    public void setSolicitudesSalidaDetalle(SolicitudesSalidaDetalle solicitudesSalidaDetalle) {
        this.solicitudesSalidaDetalle = solicitudesSalidaDetalle;
    }

    public List<SelectItem> getAlmacenesList() {
        return almacenesList;
    }

    public void setAlmacenesList(List<SelectItem> almacenesList) {
        this.almacenesList = almacenesList;
    }

    public List<SelectItem> getAreasEmpresaList2() {
        return areasEmpresaList2;
    }

    public void setAreasEmpresaList2(List<SelectItem> areasEmpresaList2) {
        this.areasEmpresaList2 = areasEmpresaList2;
    }

    public List<SelectItem> getEstadosSolicitudList() {
        return estadosSolicitudList;
    }

    public void setEstadosSolicitudList(List<SelectItem> estadosSolicitudList) {
        this.estadosSolicitudList = estadosSolicitudList;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public List<SelectItem> getGestionesList() {
        return gestionesList;
    }

    public void setGestionesList(List<SelectItem> gestionesList) {
        this.gestionesList = gestionesList;
    }

    public SolicitudesSalida getSolicitudesSalidaBuscar() {
        return solicitudesSalidaBuscar;
    }

    public void setSolicitudesSalidaBuscar(SolicitudesSalida solicitudesSalidaBuscar) {
        this.solicitudesSalidaBuscar = solicitudesSalidaBuscar;
    }

    public List<SelectItem> getTiposSalidasList2() {
        return tiposSalidasList2;
    }

    public void setTiposSalidasList2(List<SelectItem> tiposSalidasList2) {
        this.tiposSalidasList2 = tiposSalidasList2;
    }

    public List getAlmacenSolicitudList() {
        return almacenSolicitudList;
    }

    public void setAlmacenSolicitudList(List almacenSolicitudList) {
        this.almacenSolicitudList = almacenSolicitudList;
    }

    public ManagedAccesoSistema getUsuario() {
        return usuario;
    }

    public void setUsuario(ManagedAccesoSistema usuario) {
        this.usuario = usuario;
    }

    public List getSolicitudesSalidaAlmacenAprobarList() {
        return solicitudesSalidaAlmacenAprobarList;
    }

    public void setSolicitudesSalidaAlmacenAprobarList(List solicitudesSalidaAlmacenAprobarList) {
        this.solicitudesSalidaAlmacenAprobarList = solicitudesSalidaAlmacenAprobarList;
    }

    public HtmlDataTable getSolicitudesSalidaAlmacenAprobarDataTable() {
        return solicitudesSalidaAlmacenAprobarDataTable;
    }

    public void setSolicitudesSalidaAlmacenAprobarDataTable(HtmlDataTable solicitudesSalidaAlmacenAprobarDataTable) {
        this.solicitudesSalidaAlmacenAprobarDataTable = solicitudesSalidaAlmacenAprobarDataTable;
    }

    public List getMaquinariaList() {
        return maquinariaList;
    }

    public void setMaquinariaList(List maquinariaList) {
        this.maquinariaList = maquinariaList;
    }

    public SolicitudesSalida getSolicitudesSalidaLoteProovedorAgregar() {
        return solicitudesSalidaLoteProovedorAgregar;
    }

    public void setSolicitudesSalidaLoteProovedorAgregar(SolicitudesSalida solicitudesSalidaLoteProovedorAgregar) {
        this.solicitudesSalidaLoteProovedorAgregar = solicitudesSalidaLoteProovedorAgregar;
    }

    public List<SelectItem> getMaterialesSelectList() {
        return materialesSelectList;
    }

    public void setMaterialesSelectList(List<SelectItem> materialesSelectList) {
        this.materialesSelectList = materialesSelectList;
    }

    public String getLoteProovedor() {
        return loteProovedor;
    }

    public void setLoteProovedor(String loteProovedor) {
        this.loteProovedor = loteProovedor;
    }

    public List<IngresosAlmacenDetalleEstado> getIngresosAlmacenDetalleEstadoList() {
        return ingresosAlmacenDetalleEstadoList;
    }

    public void setIngresosAlmacenDetalleEstadoList(List<IngresosAlmacenDetalleEstado> ingresosAlmacenDetalleEstadoList) {
        this.ingresosAlmacenDetalleEstadoList = ingresosAlmacenDetalleEstadoList;
    }

    public List<IngresosAlmacenDetalle> getSolicitudDetalleEstadoList() {
        return solicitudDetalleEstadoList;
    }

    public void setSolicitudDetalleEstadoList(List<IngresosAlmacenDetalle> solicitudDetalleEstadoList) {
        this.solicitudDetalleEstadoList = solicitudDetalleEstadoList;
    }

    public int getCodEstadoMaterial() {
        return codEstadoMaterial;
    }

    public void setCodEstadoMaterial(int codEstadoMaterial) {
        this.codEstadoMaterial = codEstadoMaterial;
    }

    public List<SelectItem> getEstadosMaterialSelectList() {
        return estadosMaterialSelectList;
    }

    public void setEstadosMaterialSelectList(List<SelectItem> estadosMaterialSelectList) {
        this.estadosMaterialSelectList = estadosMaterialSelectList;
    }

    public ProgramaProduccion getProgramaProduccionBuscar() {
        return programaProduccionBuscar;
    }

    public void setProgramaProduccionBuscar(ProgramaProduccion programaProduccionBuscar) {
        this.programaProduccionBuscar = programaProduccionBuscar;
    }

    public List<ProgramaProduccion> getProgramaProduccionList() {
        return programaProduccionList;
    }

    public void setProgramaProduccionList(List<ProgramaProduccion> programaProduccionList) {
        this.programaProduccionList = programaProduccionList;
    }

    public HtmlDataTable getProgramaProduccionDataTable() {
        return programaProduccionDataTable;
    }

    public void setProgramaProduccionDataTable(HtmlDataTable programaProduccionDataTable) {
        this.programaProduccionDataTable = programaProduccionDataTable;
    }

    public String buscarMaterialAlmacen_action() {
        try {
            Connection con = null;
            con = Util.openConnection(con);
            ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            StringBuilder consulta=new StringBuilder(" SET NOCOUNT ON DECLARE @codigosMaterialUtilizado TdatosIntegerRef");
                                consulta.append(" INSERT INTO @codigosMaterialUtilizado VALUES (0)");
                                Iterator i = solicitudesSalidaAlmacenDetalleList.iterator();
                                while (i.hasNext()) {
                                    SolicitudesSalidaDetalle solicitudesSalidasDetalleItem = (SolicitudesSalidaDetalle) i.next();
                                    consulta.append(",(").append(solicitudesSalidasDetalleItem.getMateriales().getCodMaterial()).append(")");
                                }
                                consulta.append("  SET NOCOUNT OFF");
                    consulta.append(" exec USP_GET_LISTA_MATERIALES_CONFIGURADOS_SOLICITUDES_SALIDA_ALMACEN ")
                            .append(usuario.getAlmacenesGlobal().getCodAlmacen()).append(",")
                            .append((buscarMaterial.getGrupos().getCapitulos().getCodCapitulo() == 0 ? "NULL" : buscarMaterial.getGrupos().getCapitulos().getCodCapitulo())).append(",")
                            .append(buscarMaterial.getGrupos().getCodGrupo() == 0 ? "NULL" : buscarMaterial.getGrupos().getCodGrupo()).append(",")
                            .append("'%").append(buscarMaterial.getNombreMaterial()).append("%',")
                            .append(solicitudesSalida.getAreaDestinoSalida().getCodAreaEmpresa()).append(",")
                            .append("@codigosMaterialUtilizado");
                    
            LOGGER.debug(" consulta materiales permitidos : " + consulta.toString());
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta.toString());
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

    public List cargarCapitulosAlmacen() {
        ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");

        List capitulosList = new ArrayList();
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " exec [USP_GET_LISTA_CAPITULOS_SOLICITUDES_SALIDA] " + usuario.getAlmacenesGlobal().getCodAlmacen();
            System.out.println("consulta capitulos: " + consulta);
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

    public List cargarGruposAlmacen(int codCapitulo) {
        List gruposList = new ArrayList();
        ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");

        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " SELECT GR.COD_GRUPO,GR.NOMBRE_GRUPO "
                    + " FROM GRUPOS GR "
                    + " inner join MATERIALES m on m.COD_GRUPO=GR.COD_GRUPO"
                    + " inner join CONFIGURACION_SOLICITUDES_SALIDA_ALMACEN CSA"
                    + " on CSA.cod_capitulo=GR.COD_CAPITULO "
                    + " or GR.COD_GRUPO=CSA.cod_GRUPO"
                    + " or csa.cod_material=m.cod_material"
                    + " WHERE GR.COD_CAPITULO = '" + codCapitulo + "' AND GR.COD_ESTADO_REGISTRO = 1"
                    + " and csa.cod_almacen=" + usuario.getAlmacenesGlobal().getCodAlmacen()
                    + " group by GR.COD_GRUPO,GR.NOMBRE_GRUPO";
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

    public String capitulos_changeAlmacen() {
        try {
            if(solicitudesSalida.getAreaDestinoSalida().getCodAreaEmpresa().equals("71")||solicitudesSalida.getAreaDestinoSalida().getCodAreaEmpresa().equals("77")){
            gruposList = this.cargarGrupos(buscarMaterial.getGrupos().getCapitulos().getCodCapitulo());
            }
            else{
            gruposList = this.cargarGruposAlmacen(buscarMaterial.getGrupos().getCapitulos().getCodCapitulo());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean emptyListEmpaqueSecundario = false;

    public boolean isEmptyListEmpaqueSecundario() {
        return emptyListEmpaqueSecundario;
    }

    public void setEmptyListEmpaqueSecundario(boolean emptyListEmpaqueSecundario) {
        this.emptyListEmpaqueSecundario = emptyListEmpaqueSecundario;
    }

    public String getCargarContenidoRegistroSolicitudSalidaAlmacenTraspaso() {
        try {
            programaProduccionBuscar = new ProgramaProduccion();
            areasEmpresaList = managedSalidaAlmacen.cargarAreasEmpresa();
            //capitulosList = this.cargarCapitulos();
            ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            solicitudesSalida.getSolicitante().setNombrePersonal(usuario.getUsuarioModuloBean().getNombrePilaUsuarioGlobal());
            solicitudesSalida.getSolicitante().setApPaternoPersonal(usuario.getUsuarioModuloBean().getApPaternoUsuarioGlobal());
            solicitudesSalida.getSolicitante().setApMaternoPersonal(usuario.getUsuarioModuloBean().getApMaternoUsuarioGlobal());
            solicitudesSalida.getSolicitante().setCodPersonal(usuario.getUsuarioModuloBean().getCodUsuarioGlobal());
            solicitudesSalida.setCodFormSalida(this.generaCodSolicitudSalidaAlmacen());
            solicitudesSalida.setCodLoteProduccion("");
            solicitudesSalida.getPresentacionesProducto().setCodPresentacion("0");
            solicitudesSalida.setComponentesProd(new ComponentesProd());

            solicitudesSalidaAlmacenDetalleList.clear();
            //verificacion si existe algun objeto de donde se pueda realizar una salida de almacen
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            Map<String, Object> sessionMap = externalContext.getSessionMap();
            //ProgramaProduccion programaProduccion =(ProgramaProduccion)sessionMap.get("programaProduccion");
            solicitudMantenimiento = (SolicitudMantenimiento) sessionMap.get("solicitudMantenimiento");
            //solicitudesSalidaAlmacenDetalleList = (ArrayList)sessionMap.get("solicitudesSalidaDetalleList");

            programaProduccion = (ProgramaProduccion) sessionMap.get("programaProduccion");

            String cod_explosion_empaque_secundario = String.valueOf(sessionMap.get("cod_explosion_empaque_secundario"));

            System.out.println("cod explosion " + cod_explosion_empaque_secundario);
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " exec PAA_EXPLOSION_EMPAQUE_SECUNDARIO_ALMACEN " + cod_explosion_empaque_secundario;
            System.out.println("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            while (rs.next()) {
                SolicitudesSalidaDetalle s = new SolicitudesSalidaDetalle();
                s.setCantidad(rs.getFloat("cantidad"));
                s.getMateriales().setCodMaterial(rs.getString("cod_material"));
                s.getMateriales().setNombreMaterial(rs.getString("nombre_material"));
                s.getUnidadesMedida().setCodUnidadMedida(rs.getInt("cod_unidad_medida"));
                s.getUnidadesMedida().setNombreUnidadMedida(rs.getString("nombre_unidad_medida"));
                s.getUnidadesMedida().setAbreviatura(rs.getString("abreviatura"));
                solicitudesSalidaAlmacenDetalleList.add(s);
            }
            rs.close();
            con.close();
            //solicitudesSalidaAlmacenDetalleList = solicitudesSalidaAlmacenDetalleList1;
            almacenSolicitudList = this.cargarAlmacenSolicitud();
            
            System.out.println("cod almacen :::" + usuario.getAlmacenesGlobal().getCodAlmacen()+" areas size: "+areasEmpresaList.size());

            codTipoSolicitudSalidaAlmacen = 2;

            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String guardarSolicitudSalidaAlmacenTraspaso_action() {
        try {
            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement();
            ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            solicitudesSalida.setCodFormSalida(this.generaCodSolicitudSalidaAlmacen());
            //1 inicio alejandro
            mensaje = "";
            //inicio ale validacion salidas
            String codEstadoSolicitudSalida = "1";


            //final ale validacion salidas
            if (!solicitudesSalida.getAreaDestinoSalida().getCodAreaEmpresa().equals("0")) {
                System.out.println("cod " + solicitudesSalida.getAreaDestinoSalida().getCodAreaEmpresa());
                String consulta = " INSERT INTO SOLICITUDES_SALIDA(  COD_GESTION,  COD_FORM_SALIDA,  COD_TIPO_SALIDA_ALMACEN,  COD_ESTADO_SOLICITUD_SALIDA_ALMACEN,"
                        + "  SOLICITANTE,  AREA_DESTINO_SALIDA,  FECHA_SOLICITUD,  COD_LOTE_PRODUCCION,  OBS_SOLICITUD,  ESTADO_SISTEMA,"
                        + "  CONTROL_CALIDAD,  COD_INGRESO_ALMACEN,  COD_ALMACEN,  orden_trabajo,cod_compprod,cod_presentacion,cod_maquina,cod_area_instalacion, cod_tipo_solicitud_salida) "
                        + "VALUES (  '" + usuario.getGestionesGlobal().getCodGestion() + "','" + solicitudesSalida.getCodFormSalida() + "','" + codTipoSolicitudSalidaAlmacen + "',";
                //inicio ale validacion salidas

                solicitudesSalida.getPresentacionesProducto().setCodPresentacion("0");

                consulta += " '" + codEstadoSolicitudSalida + "','" + usuario.getUsuarioModuloBean().getCodUsuarioGlobal() + "',  '" + solicitudesSalida.getAreaDestinoSalida().getCodAreaEmpresa() + "', '" + sdf.format(new Date()) + "', '" + solicitudesSalida.getCodLoteProduccion() + "', '" + solicitudesSalida.getObsSolicitud() + "', 1,"
                        + //final ale validacion salidas
                        "  0,  0, '" + (solicitudesSalida.getAlmacenes().getCodAlmacen() == 0 ? usuario.getAlmacenesGlobal().getCodAlmacen() : solicitudesSalida.getAlmacenes().getCodAlmacen()) + "' , '" + solicitudesSalida.getOrdenTrabajo() + "','" + solicitudesSalida.getComponentesProd().getCodCompprod() + "','" + solicitudesSalida.getPresentacionesProducto().getCodPresentacion() + "','" + solicitudesSalida.getMaquinaria().getCodMaquina() + "','" + solicitudesSalida.getAreasInstalaciones().getCodAreaInstalacion() + "',3); "; //usuario.getAlmacenesGlobal().getCodAlmacen()
                System.out.println("consulta *j* " + consulta);
                //1final alejandro
                st.executeUpdate(consulta);
                Iterator i = solicitudesSalidaAlmacenDetalleList.iterator();
                boolean porAprobar = false;
                while (i.hasNext()) {
                    SolicitudesSalidaDetalle solicitudesSalidaDetalleItem = (SolicitudesSalidaDetalle) i.next();
                    if (solicitudesSalidaDetalleItem.getCantidad() > 0) {
                        consulta = " INSERT INTO SOLICITUDES_SALIDA_DETALLE(  COD_FORM_SALIDA,  COD_MATERIAL,  CANTIDAD,  CANTIDAD_ENTREGADA,"
                                + "  COD_UNIDAD_MEDIDA) VALUES (  '" + solicitudesSalida.getCodFormSalida() + "', '" + solicitudesSalidaDetalleItem.getMateriales().getCodMaterial() + "',  '" + solicitudesSalidaDetalleItem.getCantidad() + "',  0,'" + solicitudesSalidaDetalleItem.getUnidadesMedida().getCodUnidadMedida() + "'); ";
                        System.out.println("consulta *j* " + consulta);
                        st.executeUpdate(consulta);
                    }

                }

                consulta = " INSERT INTO TRASPASOS_SOLICITUDES (COD_FORM_SALIDA,  COD_ALMACEN_DESTINO,  COD_TIPO_TRASPASO,  COD_ESTADO_SOLICITUD_TRASPASO,"
                                + " FECHA_REGISTRO) VALUES ('" + solicitudesSalida.getCodFormSalida() + "',  "
                        +traspasos_solicitud.getAlmacenDestino().getCodAlmacen()+", 1,1,GETDATE())"
                        ;
                System.out.println("consulta INSERT TRASPASO *J* " + consulta);
                st.executeUpdate(consulta);
                

                st.close();
                con.close();
                //inicio alejandro
            } else {
                mensaje = "no se selecciono un area/departamento";
                // System.out.println(mensaje);
            }
            //final alejandro
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    //----------------
    public String getCargarGenerarSalidaEmpaqueSecundario() throws SQLException{
        mensaje="";
        
        solicitudesSalida = new SolicitudesSalida();
        solicitudesSalida.getAreaDestinoSalida().setCodAreaEmpresa("76");
        solicitudesSalida.getAlmacenesDestino().setCodAlmacen(17);
        solicitudesSalida.setAlmacenes(usuario.getAlmacenesGlobal());
        this.getCargarContenidoRegistroSolicitudSalidaAlmacen();        
        cargarAlmacenes();
        codTipoSolicitudSalidaAlmacen = 3;
        Connection con = null;
        try {
            con = Util.openConnection(con);
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            Map<String, Object> sessionMap = externalContext.getSessionMap();
            codExplosionEmpaqueSecundarioAlmacen =  Integer.valueOf(sessionMap.get("codExplosionEmpaqueSecundarioAlmacen").toString());
                        
            String consulta = "exec PAA_EXPLOSION_EMPAQUE_SECUNDARIO_ALMACEN "+codExplosionEmpaqueSecundarioAlmacen;
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet res = st.executeQuery(consulta);
            while(res.next()){
                SolicitudesSalidaDetalle solicitudSalidaDetalle = new SolicitudesSalidaDetalle();
                solicitudSalidaDetalle.getMateriales().setCodMaterial(res.getString("COD_MATERIAL"));
                solicitudSalidaDetalle.getMateriales().setNombreMaterial(res.getString("NOMBRE_MATERIAL"));
                solicitudSalidaDetalle.getUnidadesMedida().setCodUnidadMedida(res.getInt("COD_UNIDAD_MEDIDA"));
                solicitudSalidaDetalle.getUnidadesMedida().setNombreUnidadMedida(res.getString("NOMBRE_UNIDAD_MEDIDA"));
                solicitudSalidaDetalle.getUnidadesMedida().setAbreviatura(res.getString("ABREVIATURA"));
                solicitudSalidaDetalle.setCantidad(res.getFloat("CANTIDAD"));
                solicitudSalidaDetalle.setChecked(true);
                solicitudesSalidaAlmacenDetalleList.add(solicitudSalidaDetalle);
            }
            
        } catch (Exception e) {
            mensaje = "Ocurrio un error al momento de guardar el registro";
            LOGGER.warn(e.getMessage());
        }finally {
            con.close();
        }
        return null;
    }
    
    public String guardarNuevoSolicitudSaidaAlmacenEs_action() throws SQLException{
        mensaje="";
        registradoCorrectamente = false;
        Connection con = null;
        try {
            con = Util.openConnection(con);
            con.setAutoCommit(false);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String separador = "";
            String codigosLote = "Lotes relacionados: ";
            StringBuilder consulta = new StringBuilder(" SELECT eesad.COD_LOTE_PRODUCCION")
                        .append(" FROM EXPLOSION_EMPAQUE_SECUNDARIO_ALMACEN_DETALLE eesad")
                        .append(" WHERE eesad.COD_EXPLOSION_EMPAQUE_SECUNDARIO_ALMACEN = ").append(codExplosionEmpaqueSecundarioAlmacen);           
            ResultSet res = st.executeQuery(consulta.toString());
            while(res.next()){
                codigosLote = codigosLote +separador+ res.getInt("COD_LOTE_PRODUCCION");
                separador = ", ";
            }
            solicitudesSalida.setObsSolicitud(solicitudesSalida.getObsSolicitud()+". "+codigosLote);
            int valor;         
            //GUARDAMOS LOS REGISTROS
            consulta = new StringBuilder(" INSERT INTO SOLICITUDES_SALIDA(  COD_GESTION,  COD_FORM_SALIDA,  COD_TIPO_SALIDA_ALMACEN,  COD_ESTADO_SOLICITUD_SALIDA_ALMACEN,")
                                .append(" SOLICITANTE,  AREA_DESTINO_SALIDA,  FECHA_SOLICITUD,")
                                .append(" COD_LOTE_PRODUCCION,  OBS_SOLICITUD,  ESTADO_SISTEMA, COD_ALMACEN_DESTINO,")
                                .append(" CONTROL_CALIDAD,  COD_INGRESO_ALMACEN,  COD_ALMACEN,  orden_trabajo,")
                                .append(" cod_compprod,cod_presentacion,cod_maquina,cod_area_instalacion) ")
                        .append(" VALUES ('").append(usuario.getGestionesGlobal().getCodGestion()).append("','").append(solicitudesSalida.getCodFormSalida()).append("','").append(codTipoSolicitudSalidaAlmacen).append("',").append(1).append(",")
                                .append(" '").append(usuario.getUsuarioModuloBean().getCodUsuarioGlobal()).append("',  '").append(solicitudesSalida.getAreaDestinoSalida().getCodAreaEmpresa()).append("', GETDATE(),")
                                .append(" '").append(solicitudesSalida.getCodLoteProduccion()).append("', ?, 1, '").append(solicitudesSalida.getAlmacenesDestino().getCodAlmacen()).append("',")
                                .append(" 0,  0, '").append(usuario.getAlmacenesGlobal().getCodAlmacen()).append("' , '").append(solicitudesSalida.getOrdenTrabajo()).append("',")
                                .append(" '").append(solicitudesSalida.getComponentesProd().getCodCompprod()).append("','").append(solicitudesSalida.getPresentacionesProducto().getCodPresentacion()).append("','").append(solicitudesSalida.getMaquinaria().getCodMaquina()).append("','").append(solicitudesSalida.getAreasInstalaciones().getCodAreaInstalacion()).append("'); ");
            LOGGER.debug("consulta registrar solicitud manual: " +consulta.toString());
            PreparedStatement pst = con.prepareStatement(consulta.toString());
            pst.setString(1, solicitudesSalida.getObsSolicitud());
            LOGGER.info("p1: "+solicitudesSalida.getObsSolicitud());
            if(pst.executeUpdate() > 0)LOGGER.info("se registro la solicitud manual");
            
            consulta = new StringBuilder(" INSERT INTO SOLICITUDES_SALIDA_DETALLE(  COD_FORM_SALIDA,  COD_MATERIAL,")
                                        .append(" CANTIDAD,  CANTIDAD_ENTREGADA,COD_UNIDAD_MEDIDA, PORCENTAJE_ADICIONADO)")
                                .append(" VALUES (")
                                        .append(solicitudesSalida.getCodFormSalida()).append(",")
                                        .append("?,")//codMaterial
                                        .append("?,")//cantidad
                                        .append("0,")//cantidad entregada
                                        .append("?,")//codUnidadMedida
                                        .append("?")//porcentajeAdicionado
                                .append(")");
            LOGGER.debug("consulta registrar detalle: "+consulta.toString());        
            pst = con.prepareStatement(consulta.toString());
            
            Iterator i = solicitudesSalidaAlmacenDetalleList.iterator();            
            while (i.hasNext()) {
                SolicitudesSalidaDetalle solicitudesSalidaDetalleItem = (SolicitudesSalidaDetalle) i.next();
                if (solicitudesSalidaDetalleItem.getChecked()) {
                    if (solicitudesSalidaDetalleItem.getCantidad() > 0) {
                        LOGGER.info("p2 cantidad solicitada: "+solicitudesSalidaDetalleItem.getCantidad());
                        if (solicitudesSalidaDetalleItem.isAdicionarPorcentaje()) {
                            LOGGER.info("p2 cantidad solicitada mas 2%: "+solicitudesSalidaDetalleItem.getCantidad()*(1.02f));
                            valor = (int)Math.ceil(solicitudesSalidaDetalleItem.getCantidad()*(1.02f));
                            solicitudesSalidaDetalleItem.setCantidad(valor);
                            solicitudesSalidaDetalleItem.setPorcentajeAdicionado(2f);
                        }
                        pst.setString(1,solicitudesSalidaDetalleItem.getMateriales().getCodMaterial());LOGGER.info("p1 codMaterial: "+solicitudesSalidaDetalleItem.getMateriales().getCodMaterial());
                        pst.setFloat(2,solicitudesSalidaDetalleItem.getCantidad());LOGGER.info("p2 cantidad total: "+solicitudesSalidaDetalleItem.getCantidad());
                        pst.setInt(3,solicitudesSalidaDetalleItem.getUnidadesMedida().getCodUnidadMedida());LOGGER.info("p3 codUnidad: "+solicitudesSalidaDetalleItem.getUnidadesMedida().getCodUnidadMedida());
                        pst.setFloat(4,solicitudesSalidaDetalleItem.getPorcentajeAdicionado());LOGGER.info("p4 porcentajeAdicionado: "+solicitudesSalidaDetalleItem.getPorcentajeAdicionado());
                        if(pst.executeUpdate() > 0)LOGGER.info("se registro el detalle");
                    }
                } 
            }
            con.commit();
            registradoCorrectamente = true;
            mensaje="Registrado correctamente.";
        } catch (Exception e) {
            mensaje = "Ocurrio un error al momento de guardar el registro";
            LOGGER.warn(e.getMessage());
        }finally{
            con.close();
        }
    
        return null;
    }
    
    public float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);       
        return bd.floatValue();
    }

    private TraspasosSolicitud traspasos_solicitud = new TraspasosSolicitud();

    public TraspasosSolicitud getTraspasos_solicitud() {
        return traspasos_solicitud;
    }
    
    

    public void setTraspasos_solicitud(TraspasosSolicitud traspasos_solicitud) {
        this.traspasos_solicitud = traspasos_solicitud;
    }

    public ProgramaProduccion getProgramaProduccion() {
        return programaProduccion;
    }

    public List<EstadosMaterial> getEstadosMaterialExistenciaList() {
        return estadosMaterialExistenciaList;
    }

    public void setEstadosMaterialExistenciaList(List<EstadosMaterial> estadosMaterialExistenciaList) {
        this.estadosMaterialExistenciaList = estadosMaterialExistenciaList;
    }

    public void setProgramaProduccion(ProgramaProduccion programaProduccion) {
        this.programaProduccion = programaProduccion;
    }

    public SolicitudesSalida getSolicitudesSalidaReacondicionamiento() {
        return solicitudesSalidaReacondicionamiento;
    }

    public void setSolicitudesSalidaReacondicionamiento(SolicitudesSalida solicitudesSalidaReacondicionamiento) {
        this.solicitudesSalidaReacondicionamiento = solicitudesSalidaReacondicionamiento;
    }

    public List<SelectItem> getPresentacionesProductoSelectList() {
        return presentacionesProductoSelectList;
    }

    public void setPresentacionesProductoSelectList(List<SelectItem> presentacionesProductoSelectList) {
        this.presentacionesProductoSelectList = presentacionesProductoSelectList;
    }

    public SolicitudesSalidaDetalle getSolicitudesSalidaDetalleEliminar() {
        return solicitudesSalidaDetalleEliminar;
    }

    public void setSolicitudesSalidaDetalleEliminar(SolicitudesSalidaDetalle solicitudesSalidaDetalleEliminar) {
        this.solicitudesSalidaDetalleEliminar = solicitudesSalidaDetalleEliminar;
    }

    public int getCodExplosionEmpaqueSecundarioAlmacen() {
        return codExplosionEmpaqueSecundarioAlmacen;
    }

    public void setCodExplosionEmpaqueSecundarioAlmacen(int codExplosionEmpaqueSecundarioAlmacen) {
        this.codExplosionEmpaqueSecundarioAlmacen = codExplosionEmpaqueSecundarioAlmacen;
    }

    public Materiales getMaterialSeleccionado() {
        return materialSeleccionado;
    }

    public void setMaterialSeleccionado(Materiales materialSeleccionado) {
        this.materialSeleccionado = materialSeleccionado;
    }

}
