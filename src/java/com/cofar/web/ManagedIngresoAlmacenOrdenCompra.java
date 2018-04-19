/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofar.web;

import com.cofar.bean.Capitulos;
import com.cofar.bean.EstadosMaterial;
import com.cofar.bean.EstanteAlmacen;
import com.cofar.bean.Grupos;
import com.cofar.bean.IngresosAlmacen;
import com.cofar.bean.IngresosAlmacenDetalle;
import com.cofar.bean.IngresosAlmacenDetalleEstado;
import com.cofar.bean.Materiales;
import com.cofar.bean.OrdenesCompra;
import com.cofar.bean.OrdenesCompraDetalle;
import com.cofar.bean.Secciones;
import com.cofar.bean.SolicitudesSalida;
import com.cofar.service.AssignCostService;
import com.cofar.service.AssignCostServicePreLiquidacion;
import com.cofar.thread.ThreadCosteoByOrdenCompra;
import com.cofar.util.Util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.richfaces.component.html.HtmlDataTable;


/**
 *
 * @author hvaldivia
 */
public class ManagedIngresoAlmacenOrdenCompra extends ManagedBean {

    private Logger LOGGER;
    private IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstadoSeleccionado;
    Connection con = null;
    List ordenesCompraList = new ArrayList();
    private List materialesList = new ArrayList();
    private List proveedoresList = new ArrayList();
    private List capitulosList = new ArrayList();
    private List gruposList = new ArrayList();
    private List gestionesList = new ArrayList();
    private String mensaje = "";
    private int codOrdenCompra = 0;
    private int codProveedor = 0;
    private int codCapitulo = 0;
    private int codGrupo = 0;
    private int codMaterial = 0;
    private int codGestion = 0;
    private OrdenesCompra ordenesCompra = new OrdenesCompra();
    private IngresosAlmacen ingresosAlmacen = new IngresosAlmacen();
    private List tiposIngresosAlmacenList = new ArrayList();
    private List estadosIngresoAlmacenList = new ArrayList();
    private List ingresosAlmacenDetalleList = new ArrayList();
    private IngresosAlmacenDetalle ingresosAlmacenDetalle = new IngresosAlmacenDetalle();
    private HtmlDataTable ingresosAlmacenDetalleDataTable = new HtmlDataTable();
    private IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstado = new IngresosAlmacenDetalleEstado();
    private List ingresosAlmacenDetalleEstadoList = new ArrayList();
    private List empaqueSecundarioExternoList = new ArrayList();
    private ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
    //inicio alejandro
    private List<SelectItem> listaCapitulos = new ArrayList<SelectItem>();
    private List<SelectItem> listaGrupos = new ArrayList<SelectItem>();
    private List<Materiales> listaMateriales = new ArrayList<Materiales>();
    private Materiales materialEditar = new Materiales();
    private OrdenesCompra compraEditar = new OrdenesCompra();
    private boolean mostrar = false;
    private boolean mostrar2 = false;
    //final alejandro
    private List ordenesCompraDetalleList = new ArrayList();
    List<SelectItem> ambienteAlmacenList = new ArrayList<SelectItem>();
    List<SelectItem> estanteAlmacenList = new ArrayList<SelectItem>();
    private boolean administradorAlmacen=false;
    boolean conDensidad = false;
    

    

    

    

    /** Creates a new instance of ManagedIngresoAlmacenOrdenCompra */
    public ManagedIngresoAlmacenOrdenCompra() {
        LOGGER = LogManager.getLogger("IngresosOc");
    }
    public String loteMaterialProveedorSeleccionadoOnBlur()
    {
        this.verificarLoteMaterialProveedorAnterior(ingresosAlmacenDetalleEstadoSeleccionado);
        if(!ingresosAlmacenDetalleEstadoSeleccionado.isLoteProveedorEncontradoIngresoAnterior())
        {
            ingresosAlmacenDetalleEstadoSeleccionado.setEstadosMaterial(this.estadoMaterial(ingresosAlmacenDetalle.getMateriales()));
        }
        return null;
    }

    private void cargarPermisoPersonal()
    {
        administradorAlmacen=false;
        try 
        {
            Connection con =null;
            con=Util.openConnection(con);
            ManagedAccesoSistema managed=(ManagedAccesoSistema)Util.getSessionBean("ManagedAccesoSistema");
            StringBuilder consulta = new StringBuilder("select a.ADMINISTRADOR_ALMACEN");
                                    consulta.append(" from ALMACEN_HABILITADO_USUARIO a ");
                                    consulta.append(" where a.COD_ALMACEN=").append(managed.getAlmacenesGlobal().getCodAlmacen());
                                    consulta.append(" and a.COD_PERSONAL=").append(managed.getUsuarioModuloBean().getCodUsuarioGlobal());
            System.out.println("consulta verificar administrador sistema "+consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet res = st.executeQuery(consulta.toString());
            while (res.next()) 
            {
                administradorAlmacen=(res.getInt("ADMINISTRADOR_ALMACEN")>0);
            }
            st.close();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }
    public String getCargarIngresoAlmacenOrdenCompra() {
        
        this.cargarPermisoPersonal();
        try {
            //begin = 1;
            //end = 10;

            this.codOrdenCompra = 0;
            this.cargarItems();
            this.cargarGestiones();
            this.cargarProveedores();
            this.cargarCapitulos();
            this.cargarGestiones();
            this.cargarGrupos();
            this.cargarContenidoIngresoAlmacenOrdenCompra();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void cargarItems() {
        try {

            String consulta = "select m.COD_MATERIAL, m.NOMBRE_MATERIAL from materiales m where m.MOVIMIENTO_ITEM=1 order by m.NOMBRE_MATERIAL ";
            System.out.println("consulta " + consulta);



            con = Util.openConnection(con);

            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);

            materialesList.clear();
            materialesList.add(new SelectItem("0", "    Todos      "));
            while (rs.next()) {
                Materiales materiales = new Materiales();
                materiales.setCodMaterial(rs.getString("COD_MATERIAL"));
                materiales.setNombreMaterial(rs.getString("NOMBRE_MATERIAL"));
                materialesList.add(new SelectItem(rs.getString(1), rs.getString(2)));
            //materialesList.add(materiales);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cargarProveedores() {
        try {

            String consulta = "select m.cod_proveedor, m.nombre_proveedor from proveedores m where m.cod_estado_registro=1 order by m.nombre_proveedor ";
            System.out.println("consulta " + consulta);



            con = Util.openConnection(con);

            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);

            proveedoresList.clear();
            proveedoresList.add(new SelectItem("0", "    Todos      "));
            while (rs.next()) {
                /*Materiales materiales = new Materiales();
                materiales.setCodMaterial(rs.getString("cod_proveedor"));
                materiales.setNombreMaterial(rs.getString("nombre_proveedor"));*/
                proveedoresList.add(new SelectItem(rs.getString(1), rs.getString(2)));
            //materialesList.add(materiales);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cargarCapitulos() {
        try {

            String consulta = "select m.cod_capitulo, m.nombre_capitulo from capitulos m where m.cod_estado_registro=1 order by m.nombre_capitulo ";
            System.out.println("consulta " + consulta);



            con = Util.openConnection(con);

            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);

            capitulosList.clear();
            capitulosList.add(new SelectItem("0", "    Todos      "));
            while (rs.next()) {
                /*Materiales materiales = new Materiales();
                materiales.setCodMaterial(rs.getString("cod_proveedor"));
                materiales.setNombreMaterial(rs.getString("nombre_proveedor"));*/
                capitulosList.add(new SelectItem(rs.getString(1), rs.getString(2)));
            //materialesList.add(materiales);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String capitulos_change() {
        try {
            cargarGrupos();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void cargarGrupos() {
        try {

            String consulta = "select m.cod_grupo, m.nombre_grupo from grupos m where m.cod_estado_registro=1 and m.cod_capitulo='" + codCapitulo + "' order by m.nombre_grupo ";
            System.out.println("consulta " + consulta);



            con = Util.openConnection(con);

            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);

            gruposList.clear();
            gruposList.add(new SelectItem("0", "    Todos      "));
            while (rs.next()) {
                /*Materiales materiales = new Materiales();
                materiales.setCodMaterial(rs.getString("cod_proveedor"));
                materiales.setNombreMaterial(rs.getString("nombre_proveedor"));*/
                gruposList.add(new SelectItem(rs.getString(1), rs.getString(2)));
            //materialesList.add(materiales);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cargarGestiones() {
        try {

            String consulta = "select cod_gestion,nombre_gestion from gestiones order by cod_gestion ";
            System.out.println("consulta " + consulta);
            con = Util.openConnection(con);

            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);

            gestionesList.clear();
            //gestionesList.add(new SelectItem("0","    Todos      "));
            while (rs.next()) {
                codGestion = Integer.parseInt(usuario.getGestionesGlobal().getCodGestion());
                gestionesList.add(new SelectItem(rs.getString(1), rs.getString(2)));
            // gestionesList.add(materiales);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String buscarProductosOnClickK() {
        System.out.println("codOrdenCompra:" + codOrdenCompra);
        //usuario1.getGestionesGlobal().setCodGestion(Integer.toString(codGestion)) ;
        System.out.println("usuario.getGestionesGlobal().setCodGestion():" + codGestion);
        begin = 0;
        end = 10;
        cargarContenidoIngresoAlmacenOrdenCompra();
        return null;
    }
    /*public String  buscarProductosOnClickK(){
    System.out.println("codOrdenCompra:"+codOrdenCompra);
    cargarContenidoIngresoAlmacenOrdenCompra();
    return null;
    }*/

    public void cargarContenidoIngresoAlmacenOrdenCompra() {
        try {

            String consulta = " select * from (select ROW_NUMBER() OVER (ORDER BY oc.nro_orden_compra desc ,oc.fecha_entrega desc) as 'FILAS'," +
                    " oc.cod_gestion,oc.cod_orden_compra,oc.nro_orden_compra,oc.cod_lugarentrega, " +
                    " oc.desc_fecha_entrega, oc.cod_proveedor,oc.cod_representante,oc.cod_pre_orden_compra, " +
                    " oc.cod_estado_compra,oc.cod_tipo_transporte,oc.cod_tipo_compra, " +
                    " oc.cod_condicion_precio,oc.cod_tipo_pago,oc.cod_moneda, " +
                    " oc.cod_cotizacion,oc.cod_mediopago,oc.cod_responsable_compras, " +
                    " oc.division,oc.fecha_emision,oc.fecha_entrega,oc.fecha_alerta,oc.fecha_despacho, " +
                    " oc.credito_fiscal_si_no,oc.obs_orden_compra,oc.estado_sistema, " +
                    " oc.emitir_chequeanombrede,oc.codigo_cuenta,oc.cod_almacen_entrega, " +
                    " oc.dias_terminospago,oc.cod_tipo_documento_terminospago, " +
                    " (select le.nombre_lugarentrega from condicionesprecio_lugaresentrega le where le.cod_lugarentrega=oc.cod_lugarentrega) as lugar_entrega, " +
                    " (select g.nombre_gestion from gestiones g where oc.cod_gestion=g.cod_gestion) as  nombre_gestion, " +
                    " (select p.nombre_proveedor from proveedores p where oc.cod_proveedor=p.cod_proveedor ) as  nombre_proveedor, " +
                    " (select pa.nombre_pais from proveedores p,paises pa  where oc.cod_proveedor=p.cod_proveedor and p.cod_pais=pa.cod_pais ) as  nombre_pais, " +
                    " (select r.nombre_representante from representantes r where oc.cod_representante=r.cod_representante )as nombre_representante, " +
                    " (select ec.nombre_estado_compra from estados_compra ec where oc.cod_estado_compra=ec.cod_estado_compra) as nombre_estado_compra, " +
                    " (select tt.nombre_tipo_transporte from tipos_transporte tt where oc.cod_tipo_transporte=tt.cod_tipo_transporte) as nombre_tipo_transporte, " +
                    " (select tc.nombre_tipo_compra from tipos_compra tc where oc.cod_tipo_compra=tc.cod_tipo_compra) as nombre_tipo_compra, " +
                    " (select cp.nombre_condicion_precio from condiciones_precio cp where oc.cod_condicion_precio=cp.cod_condicion_precio) as nombre_condicion_precio, " +
                    " (select tp.nombre_tipo_pago from tipos_pago tp where oc.cod_tipo_pago=tp.cod_tipo_pago) as nombre_tipo_pago, " +
                    " (select m.nombre_moneda from monedas m where oc.cod_moneda=m.cod_moneda) as nombre_moneda, " +
                    " (select mp.nombre_mediopago from medios_pago mp where oc.cod_mediopago=mp.cod_mediopago) as nombre_mediopago, " +
                    " (select ae.nombre_area_empresa from areas_empresa ae where oc.division=ae.cod_area_empresa) as  nombre_division, " +
                    " (select a.nombre_almacen from almacenes a where oc.cod_almacen_entrega=a.cod_almacen) as  nombre_almacen_entrega, " +
                    " (select p.ap_paterno_personal+' '+p.ap_materno_personal+' '+p.nombres_personal from personal p where p.cod_personal=oc.cod_responsable_compras) as  responsable_compras, " +
                    " (select tuc.nombre_tipo_ubicacion from tipos_ubicaciones_compra tuc,ubicaciones_compra uc where tuc.cod_tipo_ubicacion=uc.cod_tipo_ubicacion and uc.cod_orden_compra=oc.cod_orden_compra ) as  ubicacion_compra, " +
                    " (select tdc.nombre_tipo_documento from tipos_documentos_compras tdc where oc.cod_tipo_documento_terminospago=tdc.cod_tipo_documento) as  nombre_tipo_documento_terminospago " +
                    " from ordenes_compra oc where oc.cod_gestion='" + codGestion + "' ";
            if (codOrdenCompra != 0) {
                consulta += " and oc.nro_orden_compra like '%" + codOrdenCompra + "%' ";
            }

            if (codMaterial != 0) {
                consulta += " and oc.cod_orden_compra in (select od.cod_orden_compra from ordenes_compra_detalle od where od.cod_material=" + codMaterial + ")";
            }
            if (codProveedor != 0) {
                consulta += " and oc.cod_proveedor in (" + codProveedor + ")";
            }
            if (codCapitulo != 0) {
                consulta += " and oc.cod_orden_compra in (select od.cod_orden_compra from ordenes_compra_detalle od where od.cod_material in (select m.cod_material from materiales m where m.cod_grupo in (select c.cod_grupo from grupos c where c.cod_capitulo in (" + codCapitulo + "))))";
            }
            if (codGrupo != 0) {
                consulta += " and oc.cod_orden_compra in (select od.cod_orden_compra from ordenes_compra_detalle od where od.cod_material in (select m.cod_material from materiales m where m.cod_grupo in (" + codGrupo + ")))";
            }

            consulta += " and oc.cod_estado_compra in (4,5,6,7,13,15)  ) AS listado_ordenes_compra ";
            if (codOrdenCompra == 0) {
                consulta += " where FILAS BETWEEN " + begin + " AND " + end + "  ";
            }

            System.out.println("consulta " + consulta);



            con = Util.openConnection(con);

            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            ordenesCompraList.clear();
            cantidadfilas = 0;
            while (rs.next()) {
                OrdenesCompra ordenesCompraItem = new OrdenesCompra();
                ordenesCompraItem.getGestiones().setCodGestion(rs.getString("cod_gestion"));
                ordenesCompraItem.getGestiones().setNombreGestion(rs.getString("nombre_gestion"));
                ordenesCompraItem.setCodOrdenCompra(rs.getInt("cod_orden_compra"));
                ordenesCompraItem.setNroOrdenCompra(rs.getInt("nro_orden_compra"));
                ordenesCompraItem.getCondicionesPrecioLugaresEntrega().setCodLugarEntrega(rs.getInt("cod_lugarentrega"));
                ordenesCompraItem.getCondicionesPrecioLugaresEntrega().setNombreLugarEntrega(rs.getString("lugar_entrega"));
                ordenesCompraItem.setDescFechaEntrega(rs.getString("desc_fecha_entrega"));
                ordenesCompraItem.getProveedores().setCodProveedor(rs.getString("cod_proveedor"));
                ordenesCompraItem.getProveedores().setNombreProveedor(rs.getString("nombre_proveedor"));
                ordenesCompraItem.getProveedores().getPaises().setNombrePais(rs.getString("nombre_pais"));
                ordenesCompraItem.getRepresentantes().setCodRepresentante(rs.getInt("cod_representante"));
                ordenesCompraItem.getRepresentantes().setNombreRepresentante(rs.getString("nombre_representante"));
                ordenesCompraItem.getPreOrdenesCompra().setCodPreOrdenCompra(rs.getInt("cod_pre_orden_compra"));
                ordenesCompraItem.getEstadosCompra().setCodEstadoCompra(rs.getInt("cod_estado_compra"));
                ordenesCompraItem.getEstadosCompra().setNombreEstadoCompra(rs.getString("nombre_estado_compra"));
                ordenesCompraItem.getTiposTransporte().setCodTipoTransporte(rs.getInt("cod_tipo_transporte"));
                ordenesCompraItem.getTiposTransporte().setNombreTipoTransporte(rs.getString("nombre_tipo_transporte"));
                ordenesCompraItem.getTiposCompra().setCodTipoCompra(rs.getString("cod_tipo_compra"));
                ordenesCompraItem.getTiposCompra().setNombreTipoCompra(rs.getString("nombre_tipo_compra"));
                ordenesCompraItem.getTiposCondicionesPrecio().setCodCondicionPrecio(rs.getInt("cod_condicion_precio"));
                ordenesCompraItem.getTiposCondicionesPrecio().setNombreCondicionPrecio(rs.getString("nombre_condicion_precio"));
                ordenesCompraItem.getTiposPago().setCodTipoPago(rs.getInt("cod_tipo_pago"));
                ordenesCompraItem.getMonedas().setCodMoneda(rs.getString("cod_moneda"));
                ordenesCompraItem.getMonedas().setNombreMoneda(rs.getString("nombre_moneda"));
                ordenesCompraItem.setCodCotizacion(rs.getInt("cod_cotizacion"));
                ordenesCompraItem.getMediosPago().setCodMedioPago(rs.getInt("cod_mediopago"));
                ordenesCompraItem.getMediosPago().setNombreMedioPago(rs.getString("nombre_mediopago"));
                ordenesCompraItem.getResponsableCompras().setCodPersonal(rs.getString("cod_responsable_compras"));
                ordenesCompraItem.getResponsableCompras().setNombrePersonal(rs.getString("responsable_compras"));
                ordenesCompraItem.getDivisionCompras().setCodDivision(rs.getInt("division"));
                ordenesCompraItem.getDivisionCompras().setNombreDivision(rs.getString("nombre_division"));
                ordenesCompraItem.setFechaEmision(rs.getDate("fecha_emision"));
                ordenesCompraItem.setFechaEntrega(rs.getDate("fecha_entrega"));
                ordenesCompraItem.setFechaAlerta(rs.getDate("fecha_alerta"));
                ordenesCompraItem.setFechaDespacho(rs.getDate("fecha_despacho"));
                ordenesCompraItem.setCreditoFiscalSiNo(rs.getInt("credito_fiscal_si_no"));
                ordenesCompraItem.setObsOrdenCompra(rs.getString("obs_orden_compra"));
                ordenesCompraItem.setEmitirChequeaNombreDe(rs.getString("emitir_chequeanombrede"));
                ordenesCompraItem.getPlanDeCuentas().setCodCuenta(rs.getString("codigo_cuenta"));
                ordenesCompraItem.getAlmacenEntrega().setCodAlmacen(rs.getInt("cod_almacen_entrega"));
                ordenesCompraItem.getAlmacenEntrega().setNombreAlmacen(rs.getString("nombre_almacen_entrega"));
                ordenesCompraItem.setDiasTerminoPago(rs.getInt("dias_terminospago"));
                ordenesCompraItem.getTerminoPago().setCodTipoDocumento(rs.getString("cod_tipo_documento_terminospago"));
                ordenesCompraItem.getTerminoPago().setNombreTipoDocumento(rs.getString("nombre_tipo_documento_terminospago"));
                ordenesCompraItem.getCondicionesPrecioLugaresEntrega().setNombreLugarEntrega(rs.getString("lugar_entrega"));
                ordenesCompraItem.getTiposUbicacionesCompra().setNombreTipoUbicacion(rs.getString("ubicacion_compra"));
                ordenesCompraList.add(ordenesCompraItem);
            }
            cantidadfilas = ordenesCompraList.size();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String siguiente_action() {
        System.out.println("entro al action siguiente ");
        super.next();
        this.cargarContenidoIngresoAlmacenOrdenCompra();
        return "";
    }

    public String atras_action() {
        super.back();
        this.cargarContenidoIngresoAlmacenOrdenCompra();
        return "";
    }

    public boolean perteneceSeccionAlmacen(OrdenesCompraDetalle ordenesCompraDetalle) {
        boolean perteneceSeccion = false;
        try {
            ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            String consulta = " select distinct(s.cod_seccion) cod_seccion,s.nombre_seccion " +
                    " from secciones s,secciones_detalle sd, almacenes a " +
                    " where s.cod_seccion=sd.cod_seccion and s.cod_almacen='" + usuario.getAlmacenesGlobal().getCodAlmacen() + "' " +
                    " and s.cod_estado_registro=1 and a.cod_almacen=s.cod_almacen  " +
                    " and ((sd.cod_material='" + ordenesCompraDetalle.getMateriales().getCodMaterial() + "' and s.estado_sistema=1) " +
                    " or (sd.cod_material=0 and sd.cod_grupo='" + ordenesCompraDetalle.getMateriales().getGrupos().getCodGrupo() + "') " +
                    " or (sd.cod_material=0 and sd.cod_grupo=0 and sd.cod_capitulo='" + ordenesCompraDetalle.getMateriales().getGrupos().getCapitulos().getCodCapitulo() + "')) ";
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            if (rs.next()) {
                perteneceSeccion = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return perteneceSeccion;
    }

    public Secciones buscaSeccionAlmacen(IngresosAlmacenDetalle ingresosAlmacenDetalle) {
        Secciones secciones = new Secciones();
        try {
            ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            String consulta = " select distinct(s.cod_seccion) cod_seccion,s.nombre_seccion " +
                    " from secciones s,secciones_detalle sd, almacenes a " +
                    " where s.cod_seccion=sd.cod_seccion and s.cod_almacen='" + usuario.getAlmacenesGlobal().getCodAlmacen() + "' " +
                    " and s.cod_estado_registro=1 and a.cod_almacen=s.cod_almacen  " +
                    " and ((sd.cod_material='" + ingresosAlmacenDetalle.getMateriales().getCodMaterial() + "' and s.estado_sistema=1) " +
                    " or (sd.cod_material=0 and sd.cod_grupo='" + ingresosAlmacenDetalle.getMateriales().getGrupos().getCodGrupo() + "') " +
                    " or (sd.cod_material=0 and sd.cod_grupo=0 and sd.cod_capitulo='" + ingresosAlmacenDetalle.getMateriales().getGrupos().getCapitulos().getCodCapitulo() + "')) ";
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            if (rs.next()) {
                secciones.setCodSeccion(rs.getInt("cod_seccion"));
                secciones.setNombreSeccion(rs.getString("nombre_seccion"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return secciones;
    }

    public void cargarDetalleIngresoAlmacen() {
        try {
            ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            String consulta = " select ocd.cod_orden_compra,ocd.cod_material,ocd.cantidad_neta,ocd.precio_unitario,ocd.precio_total,ocd.cantidad_ingreso_almacen, " +
                    " (select g.cod_capitulo from grupos g, materiales m where ocd.cod_material=m.cod_material and m.cod_grupo=g.cod_grupo)as cod_capitulo, " +
                    " (select m.cod_grupo from materiales m where ocd.cod_material=m.cod_material)as cod_grupo,ocd.cod_material, " +
                    " (select m.material_almacen from materiales m where ocd.cod_material=m.cod_material)as material_almacen, " +
                    " (select m.nombre_material from materiales m where ocd.cod_material=m.cod_material)as nombre_material,ocd.cod_unidad_medida, " +
                    " (select um.abreviatura from unidades_medida um  where ocd.cod_unidad_medida=um.cod_unidad_medida)as abreviatura " +
                    " (select material_almacen from materiales where material_almacen=1 and cod_material=ocd.cod_material) material_almacen " +
                    " (select top 1 s.cod_seccion  " +
                    " from secciones s,secciones_detalle sd, almacenes a " +
                    " where s.cod_seccion=sd.cod_seccion and s.cod_almacen='" + usuario.getAlmacenesGlobal().getCodAlmacen() + "' " +
                    " and s.cod_estado_registro=1 and a.cod_almacen=s.cod_almacen  " +
                    " and ((sd.cod_material=ocd.cod_material and s.estado_sistema=1) " +
                    " or (sd.cod_material=0 and sd.cod_grupo=(select top 1 m.cod_grupo from materiales m where ocd.cod_material=m.cod_material)) " +
                    " or (sd.cod_material=0 and sd.cod_grupo=0 and sd.cod_capitulo=(select top 1 g.cod_capitulo from grupos g, materiales m where ocd.cod_material=m.cod_material and m.cod_grupo=g.cod_grupo)))) cod_seccion," +
                    " (select m.PESO_ASOCIADO from materiales m where m.cod_material = ocd.cod_material) PESO_ASOCIADO," +
                    " (select m.COD_UNIDAD_MEDIDA_PESOASOCIADO from materiales m where m.cod_material = ocd.cod_material) COD_UNIDAD_MEDIDA_PESOASOCIADO " +
                    " (select m.COD_UNIDAD_MEDIDA from materiales m where m.cod_material = ocd.cod_material) COD_UNIDAD_MEDIDA " +
                    " from ordenes_compra_detalle ocd where cod_orden_compra= '" + ordenesCompra.getCodOrdenCompra() + "' order by nombre_material ";

            consulta = " SELECT OCD.COD_ORDEN_COMPRA,OCD.COD_MATERIAL,OCD.CANTIDAD_NETA,OCD.PRECIO_UNITARIO,OCD.PRECIO_TOTAL,OCD.CANTIDAD_INGRESO_ALMACEN, " +
                    " C.COD_CAPITULO,GR.COD_GRUPO,M.MATERIAL_ALMACEN,M.NOMBRE_MATERIAL,OCD.COD_UNIDAD_MEDIDA COD_UNIDAD_MEDIDA_OC, " +
                    " (select 1 from materiales ma where ma.COD_MATERIAL = ocd.COD_MATERIAL and ma.COD_UNIDAD_MEDIDA = ocd.COD_UNIDAD_MEDIDA) CON_EQUIVALENCIA, " +
                    " UM.NOMBRE_UNIDAD_MEDIDA NOMBRE_UNIDAD_MEDIDA_MATERIAL,UM.ABREVIATURA ABREVIATURA_MATERIAL, M.PESO_ASOCIADO,M.COD_UNIDAD_MEDIDA_PESOASOCIADO, M.COD_UNIDAD_MEDIDA COD_UNIDAD_MEDIDA_MATERIAL,  " +
                    " UM1.COD_UNIDAD_MEDIDA COD_UNIDAD_MEDIDA_OC,UM1.NOMBRE_UNIDAD_MEDIDA NOMBRE_UNIDAD_MEDIDA_OC,UM1.ABREVIATURA ABREVIATURA_OC,M.MATERIAL_PRODUCCION,M.MATERIAL_MUESTREO,m.cantidad_maximamuestreo" +
                    " ,isnull(gfv.APLICA_FECHA_VENCIMIENTO,0)  as APLICA_FECHA_VENCIMIENTO"+
                    " FROM ORDENES_COMPRA_DETALLE OCD " +
                    " INNER JOIN MATERIALES M ON OCD.COD_MATERIAL = M.COD_MATERIAL " +
                    " INNER JOIN GRUPOS GR ON GR.COD_GRUPO = M.COD_GRUPO " +
                    " INNER JOIN CAPITULOS C ON C.COD_CAPITULO = GR.COD_CAPITULO  " +
                    " left outer join GRUPOS_FECHA_VENCIMIENTO gfv on gfv.COD_GRUPO = gr.COD_GRUPO"+
                    " INNER JOIN UNIDADES_MEDIDA UM ON UM.COD_UNIDAD_MEDIDA = M.COD_UNIDAD_MEDIDA " +
                    " INNER JOIN UNIDADES_MEDIDA UM1 ON UM1.COD_UNIDAD_MEDIDA = OCD.COD_UNIDAD_MEDIDA " +
                    " WHERE OCD.COD_ORDEN_COMPRA = '" + ordenesCompra.getCodOrdenCompra() + "' ";
            
            System.out.println("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            ordenesCompraDetalleList.clear();
            //iterar el detalle y agregar los items detalle al detalle para el almacen
            //filtrar items admitidos
            //realizar la validacion de items que si se pueden ingresar
            int contador = 1;
            ingresosAlmacenDetalleList.clear();
            while (rs.next()) {
                IngresosAlmacenDetalle ingresosAlmacenDetalleItem = new IngresosAlmacenDetalle();
                ingresosAlmacenDetalleItem.setIngresosAlmacen(ingresosAlmacen);
                ingresosAlmacenDetalleItem.getMateriales().setCodMaterial(rs.getString("cod_material"));
                ingresosAlmacenDetalleItem.getMateriales().setNombreMaterial(rs.getString("NOMBRE_MATERIAL"));
                ingresosAlmacenDetalleItem.getMateriales().getGrupos().setCodGrupo(rs.getInt("COD_GRUPO"));
                ingresosAlmacenDetalleItem.getMateriales().getGrupos().getGruposFechaVencimiento().setAplicaFechaVencimiento(rs.getInt("APLICA_FECHA_VENCIMIENTO")>0);
                ingresosAlmacenDetalleItem.getMateriales().getGrupos().getCapitulos().setCodCapitulo(rs.getInt("COD_CAPITULO"));
                //unidades medida segun documento
                ingresosAlmacenDetalleItem.getOrdenesCompraDetalle().getMateriales().getUnidadesMedida().setCodUnidadMedida(rs.getInt("COD_UNIDAD_MEDIDA_MATERIAL"));
                ingresosAlmacenDetalleItem.getOrdenesCompraDetalle().getMateriales().getUnidadesMedida().setNombreUnidadMedida(rs.getString("NOMBRE_UNIDAD_MEDIDA_MATERIAL"));

                ingresosAlmacenDetalleItem.getOrdenesCompraDetalle().getUnidadesMedida().setCodUnidadMedida(rs.getInt("COD_UNIDAD_MEDIDA_OC"));
                ingresosAlmacenDetalleItem.getOrdenesCompraDetalle().getUnidadesMedida().setNombreUnidadMedida(rs.getString("NOMBRE_UNIDAD_MEDIDA_OC"));
                ingresosAlmacenDetalleItem.getOrdenesCompraDetalle().getUnidadesMedida().setAbreviatura(rs.getString("ABREVIATURA_OC"));

                ingresosAlmacenDetalleItem.getUnidadesMedidaEquivalencia().setCodUnidadMedida(rs.getInt("COD_UNIDAD_MEDIDA_OC"));
                ingresosAlmacenDetalleItem.getUnidadesMedidaEquivalencia().setNombreUnidadMedida(rs.getString("NOMBRE_UNIDAD_MEDIDA_OC"));
                ingresosAlmacenDetalleItem.getUnidadesMedidaEquivalencia().setAbreviatura(rs.getString("ABREVIATURA_OC"));

                ingresosAlmacenDetalleItem.getUnidadesMedida().setCodUnidadMedida(rs.getInt("COD_UNIDAD_MEDIDA_MATERIAL"));
                ingresosAlmacenDetalleItem.getUnidadesMedida().setNombreUnidadMedida(rs.getString("NOMBRE_UNIDAD_MEDIDA_MATERIAL"));
                ingresosAlmacenDetalleItem.getUnidadesMedida().setAbreviatura(rs.getString("ABREVIATURA_MATERIAL"));


                ingresosAlmacenDetalleItem.getMateriales().setMaterialAlmacen(rs.getInt("MATERIAL_ALMACEN"));
                ingresosAlmacenDetalleItem.setCantTotalIngreso(rs.getFloat("cantidad_neta") - rs.getFloat("cantidad_ingreso_almacen"));

                ingresosAlmacenDetalleItem.setCantTotalIngresoFisico(0);
                ingresosAlmacenDetalleItem.setNroUnidadesEmpaque(0);
                ingresosAlmacenDetalleItem.setObservacion("");
                ingresosAlmacenDetalleItem.setSecciones(this.buscaSeccion(ingresosAlmacenDetalleItem));
                ingresosAlmacenDetalleItem.setValorEquivalencia(this.obtieneEquivalencia(ingresosAlmacenDetalleItem));
                //ingresosAlmacenDetalleItem.setCantEquivalente(ingresosAlmacenDetalleItem.getValorEquivalencia()>0?(ingresosAlmacenDetalleItem.getCantTotalIngreso()/ingresosAlmacenDetalleItem.getValorEquivalencia()):0);
                //inicio alejandro 5
                System.out.println("ingresosAlmacenDetalleItem.getValorEquivalencia() :"+ingresosAlmacenDetalleItem.getValorEquivalencia());
                if (ingresosAlmacenDetalleItem.getValorEquivalencia() > 0) {
                    ingresosAlmacenDetalleItem.setCantEquivalente(ingresosAlmacenDetalleItem.getCantTotalIngreso() / ingresosAlmacenDetalleItem.getValorEquivalencia());
                } else {
                    ingresosAlmacenDetalleItem.setCantEquivalente(ingresosAlmacenDetalleItem.getCantTotalIngreso() * this.obtieneEquivalencia2(ingresosAlmacenDetalleItem));
                }
                //final alejandro 5
                ingresosAlmacenDetalleItem.getMateriales().setMaterialProduccion(rs.getInt("MATERIAL_PRODUCCION"));
                ingresosAlmacenDetalleItem.getMateriales().setMaterialMuestreo(rs.getInt("MATERIAL_MUESTREO"));
                ingresosAlmacenDetalleItem.getMateriales().setCantidadMaximaMuestreo(rs.getFloat("cantidad_maximamuestreo"));
                ingresosAlmacenDetalleItem.getMateriales().setPesoAsociado(rs.getFloat("PESO_ASOCIADO"));
                ingresosAlmacenDetalleItem.getMateriales().getUnidadesMedidaPesoAsociado().setCodUnidadMedida(rs.getInt("COD_UNIDAD_MEDIDA_PESOASOCIADO"));

                System.out.println("ingresosAlmacenDetalleItem.getMateriales().setCodMaterial() :"+ingresosAlmacenDetalleItem.getMateriales().getCodMaterial());
                System.out.println("ingresosAlmacenDetalleItem.getMateriales().getPesoAsociado() :"+ingresosAlmacenDetalleItem.getMateriales().getPesoAsociado());
                System.out.println("ingresosAlmacenDetalleItem.getMateriales().getUnidadesMedidaPesoAsociado().getCodUnidadMedida() :"+ingresosAlmacenDetalleItem.getMateriales().getUnidadesMedidaPesoAsociado().getCodUnidadMedida());
                //si tiene unidad medida peso asociado
                if (ingresosAlmacenDetalleItem.getMateriales().getPesoAsociado() > 0 &&
                        ingresosAlmacenDetalleItem.getMateriales().getUnidadesMedidaPesoAsociado().getCodUnidadMedida() > 0) {

                    ingresosAlmacenDetalleItem.setValorEquivalencia(ingresosAlmacenDetalleItem.getMateriales().getPesoAsociado());
                    consulta = " select u.COD_UNIDAD_MEDIDA,u.NOMBRE_UNIDAD_MEDIDA,u.ABREVIATURA " +
                            " from UNIDADES_MEDIDA u " +
                            " where u.COD_UNIDAD_MEDIDA ='" + ingresosAlmacenDetalleItem.getMateriales().getUnidadesMedidaPesoAsociado().getCodUnidadMedida() + "' " +
                            " and u.COD_ESTADO_REGISTRO = 1 ";
                    System.out.println("consulta  " + consulta);
                    Statement st1 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    ResultSet rs1 = st1.executeQuery(consulta);
                    if (rs1.next()) {
                        ingresosAlmacenDetalleItem.getUnidadesMedidaEquivalencia().setCodUnidadMedida(rs1.getInt("COD_UNIDAD_MEDIDA"));
                        ingresosAlmacenDetalleItem.getUnidadesMedidaEquivalencia().setNombreUnidadMedida(rs1.getString("NOMBRE_UNIDAD_MEDIDA"));
                        ingresosAlmacenDetalleItem.getUnidadesMedidaEquivalencia().setAbreviatura(rs1.getString("ABREVIATURA"));
                    }
                }


//
//
//                 ingresosAlmacenDetalleItem.getOrdenesCompraDetalle().getUnidadesMedida().setCodUnidadMedida(rs.getInt("COD_UNIDAD_MEDIDA_OC"));
//                 ingresosAlmacenDetalleItem.getOrdenesCompraDetalle().getUnidadesMedida().setNombreUnidadMedida(rs.getString("NOMBRE_UNIDAD_MEDIDA_OC"));
//                 ingresosAlmacenDetalleItem.setNroUnidadesEmpaque(0);
//                 float cantidadIngreso = rs.getFloat("cantidad_neta") - rs.getFloat("cantidad_ingreso_almacen");
//                 ingresosAlmacenDetalleItem.getOrdenesCompraDetalle().setCantidadNeta(rs.getFloat("CANTIDAD_NETA"));
//                 ingresosAlmacenDetalleItem.setCantTotalIngresoFisico(0);
//                 ingresosAlmacenDetalleItem.setPrecioTotalMaterial(0);
//                 ingresosAlmacenDetalleItem.setPrecioUnitarioMaterial(0);
//                 ingresosAlmacenDetalleItem.setCostoUnitario(0);
//                 ingresosAlmacenDetalleItem.setSecciones(this.buscaSeccionAlmacen(ingresosAlmacenDetalleItem));

//                 ingresosAlmacenDetalleItem.getMateriales().setPesoAsociado(rs.getFloat("PESO_ASOCIADO"));
//                 ingresosAlmacenDetalleItem.getMateriales().getUnidadesMedidaPesoAsociado().setCodUnidadMedida(rs.getInt("COD_UNIDAD_MEDIDA_PESOASOCIADO"));
//                 ingresosAlmacenDetalleItem.getMateriales().getUnidadesMedida().setCodUnidadMedida(rs.getInt("COD_UNIDAD_MEDIDA_MATERIAL"));
//                 //obtencion de la equivalencia
//                 Equivalencias equivalencias = this.obtieneEquivalencia(ingresosAlmacenDetalleItem);
//                 //se cambiaran las unidades de acuerdo a validaciones
//                 //ingresosAlmacenDetalleItem.getOrdenesCompraDetalle().setUnidadesMedida(equivalencias.getUnidadesMedida1());
//                 ingresosAlmacenDetalleItem.setUnidadesMedida(equivalencias.getUnidadesMedida2());
//

                System.out.println("datos : " + rs.getFloat("cantidad_ingreso_almacen") + " " + rs.getFloat("cantidad_neta") + " " + rs.getInt("CON_EQUIVALENCIA") + " " + ingresosAlmacenDetalleItem.getMateriales().getMaterialAlmacen());
                if (ingresosAlmacenDetalleItem.getSecciones().getCodSeccion() > 0) {
                    if (((rs.getFloat("cantidad_ingreso_almacen") > 0 && rs.getFloat("cantidad_ingreso_almacen") < rs.getFloat("cantidad_neta")) || rs.getFloat("cantidad_ingreso_almacen") == 0) && ingresosAlmacenDetalleItem.getMateriales().getMaterialAlmacen() == 1) {
                        ingresosAlmacenDetalleList.add(ingresosAlmacenDetalleItem);
                    }
                }
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Secciones buscaSeccion(IngresosAlmacenDetalle ingresosAlmacenDetalle) {
        Secciones secciones = new Secciones();
        try {
            ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            String consulta = " select s.cod_seccion,s.nombre_seccion  " +
                    " from secciones s,secciones_detalle sd, almacenes a " +
                    " where s.cod_seccion=sd.cod_seccion and s.cod_almacen='" + usuario.getAlmacenesGlobal().getCodAlmacen() + "' " +
                    " and s.cod_estado_registro=1 and a.cod_almacen=s.cod_almacen  " +
                    " and ((sd.cod_material=" + ingresosAlmacenDetalle.getMateriales().getCodMaterial() + " and s.estado_sistema=1) " +
                    " or (sd.cod_material=0 and sd.cod_grupo= '" + ingresosAlmacenDetalle.getMateriales().getGrupos().getCodGrupo() + "') " +
                    " or (sd.cod_material=0 and sd.cod_grupo=0 and sd.cod_capitulo='" + ingresosAlmacenDetalle.getMateriales().getGrupos().getCapitulos().getCodCapitulo() + "')) ";

            System.out.println("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            if (rs.next()) {
                secciones.setCodSeccion(rs.getInt("cod_seccion"));
                secciones.setNombreSeccion(rs.getString("nombre_seccion"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return secciones;
    }

    public float obtieneEquivalencia1(IngresosAlmacenDetalle ingresosAlmacenDetalle) {
        float equivalencia = 0;
        String consulta = "";
        try {
            if (ingresosAlmacenDetalle.getMateriales().getPesoAsociado() > 0 &&
                    ingresosAlmacenDetalle.getMateriales().getUnidadesMedidaPesoAsociado().getCodUnidadMedida() > 0) {

                //por revisar
                consulta = " select e.valor_equivalencia " +
                        " from equivalencias e " +
                        " where ((e.cod_unidad_medida='" + ingresosAlmacenDetalle.getMateriales().getUnidadesMedida().getCodUnidadMedida() + "' " +
                        " and e.cod_unidad_medida2='" + ingresosAlmacenDetalle.getMateriales().getUnidadesMedidaPesoAsociado().getCodUnidadMedida() + "') or " +
                        " (e.cod_unidad_medida='" + ingresosAlmacenDetalle.getMateriales().getUnidadesMedidaPesoAsociado().getCodUnidadMedida() + "' " +
                        " and e.cod_unidad_medida2='" + ingresosAlmacenDetalle.getMateriales().getUnidadesMedida().getCodUnidadMedida() + "')) " +
                        " and e.cod_estado_registro=1 ";
                System.out.println("consulta " + consulta);
                Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs = st.executeQuery(consulta);
                if (rs.next()) {
                    equivalencia = rs.getFloat("valor_equivalencia");
                } else {
                }
            } else {
                consulta = " select e.valor_equivalencia " +
                        " from equivalencias e " +
                        " where ((e.cod_unidad_medida='" + ingresosAlmacenDetalle.getOrdenesCompraDetalle().getMateriales().getUnidadesMedida().getCodUnidadMedida() + "' " +
                        " and e.cod_unidad_medida2='" + ingresosAlmacenDetalle.getOrdenesCompraDetalle().getUnidadesMedida().getCodUnidadMedida() + "') or " +
                        " (e.cod_unidad_medida='" + ingresosAlmacenDetalle.getOrdenesCompraDetalle().getUnidadesMedida().getCodUnidadMedida() + "' " +
                        " and e.cod_unidad_medida2='" + ingresosAlmacenDetalle.getOrdenesCompraDetalle().getMateriales().getUnidadesMedida().getCodUnidadMedida() + "')) " +
                        " and e.cod_estado_registro=1 ";
                System.out.println("consulta " + consulta);

                Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs = st.executeQuery(consulta);

                if (rs.next()) {
                    equivalencia = rs.getFloat("valor_equivalencia");
                } else {
                }
            }
//            if(ingresosAlmacenDetalle.getMateriales().getUnidadesMedida().getCodUnidadMedida() == ingresosAlmacenDetalle.getMateriales().getUnidadesMedidaPesoAsociado().getCodUnidadMedida() ||
//                    ingresosAlmacenDetalle.getOrdenesCompraDetalle().getMateriales().getUnidadesMedida().getCodUnidadMedida()==ingresosAlmacenDetalle.getOrdenesCompraDetalle().getUnidadesMedida().getCodUnidadMedida()){
//                equivalencia = 1;
//            }

            //inicio alejandro remplazar esta parte con la correspondiente en el codigo de la funcion
            //if(ingresosAlmacenDetalle.getMateriales().getUnidadesMedida().getCodUnidadMedida() == ingresosAlmacenDetalle.getMateriales().getUnidadesMedidaPesoAsociado().getCodUnidadMedida() ||
            //      ingresosAlmacenDetalle.getOrdenesCompraDetalle().getMateriales().getUnidadesMedida().getCodUnidadMedida()== ingresosAlmacenDetalle.getOrdenesCompraDetalle().getUnidadesMedida().getCodUnidadMedida())
            if (ingresosAlmacenDetalle.getOrdenesCompraDetalle().getMateriales().getUnidadesMedida().getCodUnidadMedida() == ingresosAlmacenDetalle.getOrdenesCompraDetalle().getUnidadesMedida().getCodUnidadMedida()) {
                equivalencia = 1;
            }
        //final alejandro




        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("valor equivalencia : " + equivalencia);
        return equivalencia;
    }

    public float obtieneEquivalencia4(IngresosAlmacenDetalle ingresosAlmacenDetalle) {
        float equivalencia = 0;
        String consulta = "";
        try {
            if (ingresosAlmacenDetalle.getMateriales().getPesoAsociado() > 0 &&
                    ingresosAlmacenDetalle.getMateriales().getUnidadesMedidaPesoAsociado().getCodUnidadMedida() > 0) {

                //por revisar
                consulta = " select e.valor_equivalencia " +
                        " from equivalencias e " +
                        " where ((e.cod_unidad_medida='" + ingresosAlmacenDetalle.getMateriales().getUnidadesMedida().getCodUnidadMedida() + "' " +
                        " and e.cod_unidad_medida2='" + ingresosAlmacenDetalle.getMateriales().getUnidadesMedidaPesoAsociado().getCodUnidadMedida() + "') or " +
                        " (e.cod_unidad_medida='" + ingresosAlmacenDetalle.getMateriales().getUnidadesMedidaPesoAsociado().getCodUnidadMedida() + "' " +
                        " and e.cod_unidad_medida2='" + ingresosAlmacenDetalle.getMateriales().getUnidadesMedida().getCodUnidadMedida() + "')) " +
                        " and e.cod_estado_registro=1 ";
                System.out.println("consulta " + consulta);
                Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs = st.executeQuery(consulta);
                if (rs.next()) {
                    equivalencia = rs.getFloat("valor_equivalencia");
                } else {
                }
            } else {
                // inicio alejandro 4
                consulta = " select e.valor_equivalencia " +
                        " from equivalencias e " +
                        " where ((e.cod_unidad_medida='" + ingresosAlmacenDetalle.getOrdenesCompraDetalle().getMateriales().getUnidadesMedida().getCodUnidadMedida() + "' " +
                        " and e.cod_unidad_medida2='" + ingresosAlmacenDetalle.getOrdenesCompraDetalle().getUnidadesMedida().getCodUnidadMedida() + "')" +
                        " and e.cod_estado_registro=1 ";
                System.out.println("consulta " + consulta);

                Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs = st.executeQuery(consulta);

                if (rs.next()) {
                    equivalencia = rs.getFloat("valor_equivalencia");
                } else {
                    consulta = " select e.valor_equivalencia " +
                            " from equivalencias e where " +
                            " (e.cod_unidad_medida='" + ingresosAlmacenDetalle.getOrdenesCompraDetalle().getUnidadesMedida().getCodUnidadMedida() + "' " +
                            " and e.cod_unidad_medida2='" + ingresosAlmacenDetalle.getOrdenesCompraDetalle().getMateriales().getUnidadesMedida().getCodUnidadMedida() + "')) " +
                            " and e.cod_estado_registro=1 ";
                    System.out.println("consulta " + consulta);
                    rs = st.executeQuery(consulta);
                    if (rs.next()) {
                        equivalencia = Float.valueOf(1 / rs.getFloat("valor_equivalencia"));
                    }
                }
            //final alejandro
            }
//            if(ingresosAlmacenDetalle.getMateriales().getUnidadesMedida().getCodUnidadMedida() == ingresosAlmacenDetalle.getMateriales().getUnidadesMedidaPesoAsociado().getCodUnidadMedida() ||
//                    ingresosAlmacenDetalle.getOrdenesCompraDetalle().getMateriales().getUnidadesMedida().getCodUnidadMedida()==ingresosAlmacenDetalle.getOrdenesCompraDetalle().getUnidadesMedida().getCodUnidadMedida()){
//                equivalencia = 1;
//            }

            //inicio alejandro remplazar esta parte con la correspondiente en el codigo de la funcion
            //if(ingresosAlmacenDetalle.getMateriales().getUnidadesMedida().getCodUnidadMedida() == ingresosAlmacenDetalle.getMateriales().getUnidadesMedidaPesoAsociado().getCodUnidadMedida() ||
            //      ingresosAlmacenDetalle.getOrdenesCompraDetalle().getMateriales().getUnidadesMedida().getCodUnidadMedida()== ingresosAlmacenDetalle.getOrdenesCompraDetalle().getUnidadesMedida().getCodUnidadMedida())
            if (ingresosAlmacenDetalle.getOrdenesCompraDetalle().getMateriales().getUnidadesMedida().getCodUnidadMedida() == ingresosAlmacenDetalle.getOrdenesCompraDetalle().getUnidadesMedida().getCodUnidadMedida()) {
                equivalencia = 1;
            }
        //final alejandro




        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("valor equivalencia : " + equivalencia);
        return equivalencia;
    }
    //inicio alejandro 5

    public float obtieneEquivalencia(IngresosAlmacenDetalle ingresosAlmacenDetalle) {
        float equivalencia = 0;
        String consulta = "";
        try {
            if (ingresosAlmacenDetalle.getMateriales().getPesoAsociado() > 0 &&
                    ingresosAlmacenDetalle.getMateriales().getUnidadesMedidaPesoAsociado().getCodUnidadMedida() > 0) {

                //por revisar
                consulta = " select e.valor_equivalencia " +
                        " from equivalencias e " +
                        " where ((e.cod_unidad_medida='" + ingresosAlmacenDetalle.getMateriales().getUnidadesMedida().getCodUnidadMedida() + "' " +
                        " and e.cod_unidad_medida2='" + ingresosAlmacenDetalle.getMateriales().getUnidadesMedidaPesoAsociado().getCodUnidadMedida() + "') or " +
                        " (e.cod_unidad_medida='" + ingresosAlmacenDetalle.getMateriales().getUnidadesMedidaPesoAsociado().getCodUnidadMedida() + "' " +
                        " and e.cod_unidad_medida2='" + ingresosAlmacenDetalle.getMateriales().getUnidadesMedida().getCodUnidadMedida() + "')) " +
                        " and e.cod_estado_registro=1 ";
                System.out.println("consulta Peso asociado:" + consulta);
                Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs = st.executeQuery(consulta);
                System.out.println("holasss");
                if (rs.next()) {
                    equivalencia = rs.getFloat("valor_equivalencia");
                } else {
                }
            } else {
                // inicio alejandro 4
                
                consulta = " select e.valor_equivalencia " +
                        " from equivalencias e " +
                        " where (e.cod_unidad_medida='" + ingresosAlmacenDetalle.getOrdenesCompraDetalle().getMateriales().getUnidadesMedida().getCodUnidadMedida() + "' " +
                        " and e.cod_unidad_medida2='" + ingresosAlmacenDetalle.getOrdenesCompraDetalle().getUnidadesMedida().getCodUnidadMedida() + "')" +
                        " and e.cod_estado_registro=1 ";
                System.out.println("consulta :::::" + consulta);

                Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs = st.executeQuery(consulta);

                if (rs.next()) {
                    equivalencia = rs.getFloat("valor_equivalencia");
                } else {
                    equivalencia = 0;
                }
            //final alejandro
            }
//            if(ingresosAlmacenDetalle.getMateriales().getUnidadesMedida().getCodUnidadMedida() == ingresosAlmacenDetalle.getMateriales().getUnidadesMedidaPesoAsociado().getCodUnidadMedida() ||
//                    ingresosAlmacenDetalle.getOrdenesCompraDetalle().getMateriales().getUnidadesMedida().getCodUnidadMedida()==ingresosAlmacenDetalle.getOrdenesCompraDetalle().getUnidadesMedida().getCodUnidadMedida()){
//                equivalencia = 1;
//            }

            //inicio alejandro remplazar esta parte con la correspondiente en el codigo de la funcion
            //if(ingresosAlmacenDetalle.getMateriales().getUnidadesMedida().getCodUnidadMedida() == ingresosAlmacenDetalle.getMateriales().getUnidadesMedidaPesoAsociado().getCodUnidadMedida() ||
            //      ingresosAlmacenDetalle.getOrdenesCompraDetalle().getMateriales().getUnidadesMedida().getCodUnidadMedida()== ingresosAlmacenDetalle.getOrdenesCompraDetalle().getUnidadesMedida().getCodUnidadMedida())
            if (ingresosAlmacenDetalle.getOrdenesCompraDetalle().getMateriales().getUnidadesMedida().getCodUnidadMedida() == ingresosAlmacenDetalle.getOrdenesCompraDetalle().getUnidadesMedida().getCodUnidadMedida()) {
                equivalencia = 1;
            }
        //final alejandro




        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("valor equivalencia : " + equivalencia);
        return equivalencia;
    }
    //inicio ale

    public float obtieneEquivalencia2(IngresosAlmacenDetalle ingresosAlmacenDetalle) {
        float equivalencia = 0;
        String consulta = "";
        try {
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            consulta = " select e.valor_equivalencia " +
                    " from equivalencias e where " +
                    " (e.cod_unidad_medida='" + ingresosAlmacenDetalle.getOrdenesCompraDetalle().getUnidadesMedida().getCodUnidadMedida() + "' " +
                    " and e.cod_unidad_medida2='" + ingresosAlmacenDetalle.getOrdenesCompraDetalle().getMateriales().getUnidadesMedida().getCodUnidadMedida() + "') " +
                    " and e.cod_estado_registro=1 ";
            System.out.println("consulta " + consulta);
            ResultSet rs = st.executeQuery(consulta);
            if (rs.next()) {
                equivalencia = rs.getFloat("valor_equivalencia");
            }
            //por ordenes de rubal en caso de no encontrar ninguna equivalencia se establece una relacion de 1 a 1
            else
            {
                equivalencia=1;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.out.println("equivalencia 2" + equivalencia);
        return equivalencia;
    }
    //final alejandro 5

    public String generarIngresoAlmacen_action() {
        try {
            mensaje = "";
            ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            if (usuario.getAlmacenesGlobal().getCodAlmacen() != ordenesCompra.getAlmacenEntrega().getCodAlmacen()) {
                mensaje = "el almacen no corresponde al almacen actual";
                return null;
            }
            this.cargarPermisoPersonal();
            if(!this.administradorAlmacen){
                mensaje = "No puede realizar el ingreso ya que no es administrador del almacen actual";
                return null;
            }
            // verificar si los items ya se ingresaron en su totalidad
            this.verificarItemsIngresadosAlmacen(ordenesCompra, usuario);
            //this.verificarItemsCompras(ordenesCompra, usuario);
            //inicio alejandro
            if (mensaje.equals(" existen items que no pertenecen a la seccion ")) {
                String aux = getActionGenerarCambio();
            }
            //final alejandro
            if (!mensaje.equals("")) {
                return null;
            }




        //aqui el resto de las validaciones




        // Utiles.direccionar("agregarIngresosAlmacenOrdenCompra.jsf");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void verificarItemsIngresadosAlmacen(OrdenesCompra ordenesCompra, ManagedAccesoSistema usuario) {
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " SELECT OCD.COD_ORDEN_COMPRA,OCD.COD_MATERIAL,OCD.CANTIDAD_NETA,OCD.PRECIO_UNITARIO,OCD.PRECIO_TOTAL,OCD.CANTIDAD_INGRESO_ALMACEN,  " +
                    " C.COD_CAPITULO,GR.COD_GRUPO,M.MATERIAL_ALMACEN,M.NOMBRE_MATERIAL,OCD.COD_UNIDAD_MEDIDA COD_UNIDAD_MEDIDA_OC, " +
                    " UM.NOMBRE_UNIDAD_MEDIDA NOMBRE_UNIDAD_MEDIDA_MATERIAL,UM.ABREVIATURA ABREVIATURA_MATERIAL, M.PESO_ASOCIADO,M.COD_UNIDAD_MEDIDA_PESOASOCIADO,  " +
                    " M.COD_UNIDAD_MEDIDA COD_UNIDAD_MEDIDA_MATERIAL,   UM1.COD_UNIDAD_MEDIDA COD_UNIDAD_MEDIDA_OC,UM1.NOMBRE_UNIDAD_MEDIDA NOMBRE_UNIDAD_MEDIDA_OC, " +
                    " UM1.ABREVIATURA ABREVIATURA_OC, ( select top 1 s.cod_seccion " +
                    " from secciones s, secciones_detalle sd,  almacenes a " +
                    " where s.cod_seccion = sd.cod_seccion and  s.cod_almacen = '" + usuario.getAlmacenesGlobal().getCodAlmacen() + "' and  s.cod_estado_registro = 1 and " +
                    " a.cod_almacen = s.cod_almacen and ((sd.cod_material = m.COD_MATERIAL and  s.estado_sistema = 1) or " +
                    " (sd.cod_material = 0 and sd.cod_grupo = gr.COD_GRUPO) or (sd.cod_material = 0 and sd.cod_grupo = 0 and " +
                    " sd.cod_capitulo = c.COD_CAPITULO)) ) cod_seccion  " +
                    " FROM ORDENES_COMPRA_DETALLE OCD  INNER JOIN MATERIALES M ON OCD.COD_MATERIAL = M.COD_MATERIAL   " +
                    " INNER JOIN GRUPOS GR ON GR.COD_GRUPO = M.COD_GRUPO  INNER JOIN CAPITULOS C ON C.COD_CAPITULO = GR.COD_CAPITULO    " +
                    " INNER JOIN UNIDADES_MEDIDA UM ON UM.COD_UNIDAD_MEDIDA = M.COD_UNIDAD_MEDIDA   " +
                    " INNER JOIN UNIDADES_MEDIDA UM1 ON UM1.COD_UNIDAD_MEDIDA = OCD.COD_UNIDAD_MEDIDA   " +
                    " WHERE OCD.COD_ORDEN_COMPRA = '" + ordenesCompra.getCodOrdenCompra() + "'  ";
            System.out.println("consulta" + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            int contIngresosPendientes=0;
            int contIngresosSinSeccion=0;
            while (rs.next()) {
                //contando items que no pertenecen a la seccion
                if (rs.getInt("cod_seccion") == 0)
                {
                    contIngresosSinSeccion++;
                }
                //contando ingresos no ingresados en su totalidad
                if(rs.getFloat("cantidad_ingreso_almacen") <= rs.getFloat("cantidad_neta"))
                {
                    contIngresosPendientes++;
                }
                //validando que se encuentre registrado como material de almacen
                if(rs.getInt("MATERIAL_ALMACEN") == 0)
                {
                    mensaje+=(mensaje.equals("")?"":",")+"el item "+rs.getString("NOMBRE_MATERIAL")+" no se encuentra registrado como material de almacen";
                }
            }
            if(mensaje.equals(""))
            {
                //verificando si existen ingresos sin seccion
                if (contIngresosSinSeccion >0)
                {
                    mensaje = " existen items que no pertenecen a la seccion ";
                }
                else
                {
                    //verificando si existen ingresos pendientes de ingresar
                    if (contIngresosPendientes ==0)
                    {
                        mensaje = " los items fueron ingresados al almacen ";
                    }
                }
            }
           



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void verificarItemsCompras(OrdenesCompra ordenesCompra, ManagedAccesoSistema usuario) {
        int itemsIngresadosAlmacen = 1;
        int seccion = 1;
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " SELECT OCD.COD_ORDEN_COMPRA,OCD.COD_MATERIAL,OCD.CANTIDAD_NETA,OCD.PRECIO_UNITARIO,OCD.PRECIO_TOTAL,OCD.CANTIDAD_INGRESO_ALMACEN,  " +
                    " C.COD_CAPITULO,GR.COD_GRUPO,M.MATERIAL_ALMACEN,M.NOMBRE_MATERIAL,OCD.COD_UNIDAD_MEDIDA COD_UNIDAD_MEDIDA_OC, " +
                    " UM.NOMBRE_UNIDAD_MEDIDA NOMBRE_UNIDAD_MEDIDA_MATERIAL,UM.ABREVIATURA ABREVIATURA_MATERIAL, M.PESO_ASOCIADO,M.COD_UNIDAD_MEDIDA_PESOASOCIADO,  " +
                    " M.COD_UNIDAD_MEDIDA COD_UNIDAD_MEDIDA_MATERIAL,   UM1.COD_UNIDAD_MEDIDA COD_UNIDAD_MEDIDA_OC,UM1.NOMBRE_UNIDAD_MEDIDA NOMBRE_UNIDAD_MEDIDA_OC, " +
                    " UM1.ABREVIATURA ABREVIATURA_OC, ( select s.cod_seccion " +
                    " from secciones s, secciones_detalle sd,  almacenes a " +
                    " where s.cod_seccion = sd.cod_seccion and  s.cod_almacen = '" + usuario.getAlmacenesGlobal().getCodAlmacen() + "' and  s.cod_estado_registro = 1 and " +
                    " a.cod_almacen = s.cod_almacen and ((sd.cod_material = m.COD_MATERIAL and  s.estado_sistema = 1) or " +
                    " (sd.cod_material = 0 and sd.cod_grupo = gr.COD_GRUPO) or (sd.cod_material = 0 and sd.cod_grupo = 0 and " +
                    " sd.cod_capitulo = c.COD_CAPITULO)) ) cod_seccion  " +
                    " FROM ORDENES_COMPRA_DETALLE OCD  INNER JOIN MATERIALES M ON OCD.COD_MATERIAL = M.COD_MATERIAL   " +
                    " INNER JOIN GRUPOS GR ON GR.COD_GRUPO = M.COD_GRUPO  INNER JOIN CAPITULOS C ON C.COD_CAPITULO = GR.COD_CAPITULO    " +
                    " INNER JOIN UNIDADES_MEDIDA UM ON UM.COD_UNIDAD_MEDIDA = M.COD_UNIDAD_MEDIDA   " +
                    " INNER JOIN UNIDADES_MEDIDA UM1 ON UM1.COD_UNIDAD_MEDIDA = OCD.COD_UNIDAD_MEDIDA   " +
                    " WHERE OCD.COD_ORDEN_COMPRA = '" + ordenesCompra.getCodOrdenCompra() + "' and c.cod_capitulo in (16,19)";
            System.out.println("consulta" + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            if (rs.next()) {
                mensaje = " existen items que no pertenecen a la seccion ";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int deduceCodIngresoAlmacen() {
        int codIngresoAlmacen = 0;
        try {
            String consulta = " select isnull(max(cod_ingreso_almacen),0)+1 cod_ingreso_almacen from ingresos_almacen  ";
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            if (rs.next()) {
                codIngresoAlmacen = rs.getInt("cod_ingreso_almacen");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return codIngresoAlmacen;
    }
public static boolean isPostback() {
    FacesContext context = FacesContext.getCurrentInstance();
    return !context.getExternalContext().getRequestParameterMap().isEmpty();
}
    public String getCargarAgregarIngresoAlmacenOrdenCompra() {
        try {
            if(!isPostback()){
                
            }
            
            ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            ingresosAlmacen.setGestiones(usuario.getGestionesGlobal());
            ingresosAlmacen.setOrdenesCompra(ordenesCompra);
            ingresosAlmacen.getProveedores().setCodProveedor(ordenesCompra.getProveedores().getCodProveedor());
            ingresosAlmacen.getTiposIngresoAlmacen().setCodTipoIngresoAlmacen(1); //ingreso a almacen con orden de compra
            ingresosAlmacen.getEstadosIngresoAlmacen().setCodEstadoIngresoAlmacen(1);
            ingresosAlmacen.setNroIngresoAlmacen(this.generaNroIngresoAlmacen());

            this.cargarEstadosIngresoAlmacen();
            this.cargarTiposIngresoAlmacen();
            this.cargarDetalleIngresoAlmacen();
            this.cargarEmpaques();
            estanteAlmacenList = cargarEstanteAlmacen();
            ambienteAlmacenList = cargarAmbienteAlmacen();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void cargarTiposIngresoAlmacen() {
        try {
            con = Util.openConnection(con);
            String consulta = " select t.COD_TIPO_INGRESO_ALMACEN,t.NOMBRE_TIPO_INGRESO_ALMACEN from TIPOS_INGRESO_ALMACEN t where t.COD_ESTADO_REGISTRO = 1 ";

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
    }

    public void cargarEstadosIngresoAlmacen() {
        try {
            con = Util.openConnection(con);
            String consulta = " select e.COD_ESTADO_INGRESO_ALMACEN,e.NOMBRE_ESTADO_INGRESO_ALMACEN from ESTADOS_INGRESO_ALMACEN e ";

            ResultSet rs = null;
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            estadosIngresoAlmacenList.clear();
            rs = st.executeQuery(consulta);
            while (rs.next()) {
                estadosIngresoAlmacenList.add(new SelectItem(rs.getString("COD_ESTADO_INGRESO_ALMACEN"), rs.getString("NOMBRE_ESTADO_INGRESO_ALMACEN")));
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
    }

    public String detallarItem_action() {
        try {
            ingresosAlmacenDetalle = (IngresosAlmacenDetalle) ingresosAlmacenDetalleDataTable.getRowData();
            ingresosAlmacenDetalleEstado = new IngresosAlmacenDetalleEstado();
            ingresosAlmacenDetalleEstado.setEstadosMaterial(this.estadoMaterial(ingresosAlmacenDetalle.getMateriales()));
            DateTime fechaVencimiento = new DateTime(ingresosAlmacenDetalleEstado.getFechaManufactura());
            fechaVencimiento = fechaVencimiento.plusMonths(3);
            fechaVencimiento = fechaVencimiento.dayOfMonth().withMaximumValue();
            ingresosAlmacenDetalleEstado.setFechaVencimiento(fechaVencimiento.toDate());
            ingresosAlmacenDetalleEstado.setMateriales(ingresosAlmacenDetalle.getMateriales().clone());
            ingresosAlmacenDetalleEstado.setEstadosMaterial(this.estadoMaterial(ingresosAlmacenDetalle.getMateriales()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String guardarDetalleItem_action() {
        try {
            System.out.println("click guardar detalle");
            mensaje = "";
            if (ingresosAlmacenDetalle.getIngresosAlmacenDetalleEstadoList().size() == 0) {
                mensaje = "Usted no puede guardar el detalle de item si no existe ninguna etiqueta";
                return null;
            }
            ingresosAlmacenDetalle.setColorFila("b");
            IngresosAlmacenDetalle ingresosAlmacenDetalleItem = ingresosAlmacenDetalle;
            ingresosAlmacenDetalleItem.setCantTotalIngresoFisico(ingresosAlmacenDetalle.getCantTotalIngresoFisico());
            if (true) {
                Iterator i = ingresosAlmacenDetalle.getIngresosAlmacenDetalleEstadoList().iterator();
                ingresosAlmacenDetalleItem.setCantTotalIngresoFisico(0);
                while (i.hasNext()) {
                    IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstadoItem = (IngresosAlmacenDetalleEstado) i.next();
                    ingresosAlmacenDetalleItem.setCantTotalIngresoFisico(ingresosAlmacenDetalleItem.getCantTotalIngresoFisico() + ingresosAlmacenDetalleEstadoItem.getCantidadParcial());
                    //System.out.println("cant etiqueta: "+ingresosAlmacenDetalleEstadoItem.getCantidadParcial());
                }
            }
            ingresosAlmacenDetalle.setCantTotalIngreso(ingresosAlmacenDetalleItem.getCantTotalIngresoFisico());
            //verificando datos por lote de proveedor
            List<IngresosAlmacenDetalleEstado> listaVerificar = new ArrayList<IngresosAlmacenDetalleEstado>();
            int index = 0;
            for(IngresosAlmacenDetalleEstado bean: ingresosAlmacenDetalle.getIngresosAlmacenDetalleEstadoList())
            {   
                index++ ;
                if(bean.getCantidadParcial() <=0){
                    mensaje = "La cantidad parcial en la fila "+index+" no puede ser menor o igual a 0";
                    return null;
                }
                if(bean.getLoteMaterialProveedor().trim().length()>0)
                {
                    boolean encontrado = false;
                    for(IngresosAlmacenDetalleEstado bean2 : listaVerificar)
                    {
                        if(bean2.getLoteMaterialProveedor().trim().equals(bean.getLoteMaterialProveedor().trim()))
                        {
                            encontrado = true;
                            if( ! (bean2.getFechaVencimientoFormatoMMYY().equals(bean.getFechaVencimientoFormatoMMYY()) && 
                                    bean2.getEstadosMaterial().getCodEstadoMaterial() == bean.getEstadosMaterial().getCodEstadoMaterial()))
                            {
                                mensaje = "La etiqueta "+bean.getEtiqueta()+" difiere la fecha de vencimiento/estado de material de las demas etiquetas de lote "+bean.getLoteMaterialProveedor();
                            }
                        }
                    }
                    if(!encontrado)
                    {
                        listaVerificar.add(bean);
                    }
                }
            }
            //System.out.println("cant total; "+ingresosAlmacenDetalleItem.getCantTotalIngresoFisico());
            
        //aqui las unidades de medida
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean validaDatosIngresoAcondicionamiento(IngresosAlmacenDetalle ingresosAlmacenDetalleItem) {
        boolean datosValidos = true;
        DateTime fechaVencimiento = new DateTime(new Date());
        fechaVencimiento = fechaVencimiento.plusMonths(3);
        fechaVencimiento = fechaVencimiento.dayOfMonth().withMaximumValue();
        Iterator i = ingresosAlmacenDetalle.getIngresosAlmacenDetalleEstadoList().iterator();
        while (i.hasNext()) {
            IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstadoItem = (IngresosAlmacenDetalleEstado) i.next();
            /*if (ingresosAlmacenDetalleEstadoItem.getFechaManufactura().compareTo(ingresosAlmacenDetalleEstadoItem.getFechaVencimiento()) >= 0) {
                mensaje = "La fecha de Manufactura debe ser menor a la fecha de Vencimiento ";
                datosValidos = false;
                break;
            }*/
            if (fechaVencimiento.toDate().compareTo(ingresosAlmacenDetalleEstadoItem.getFechaVencimiento()) > 0
                    && (ingresosAlmacenDetalle.getMateriales().getGrupos().getGruposFechaVencimiento().getAplicaFechaVencimiento() ||
                        ingresosAlmacenDetalle.getAplicaFechaVencimiento())) {
                mensaje = " En la fecha de vencimiento debe tener minimamente tres meses de vigencia ";
                break;
            }
        }
        return datosValidos;
    }

    public String generarEtiquetas_action() {
        try {
            ingresosAlmacenDetalle.getIngresosAlmacenDetalleEstadoList().clear();
            String nombreEmpaqueSecundario = this.buscaEmpaqueSecundarioExterno(ingresosAlmacenDetalleEstado.getEmpaqueSecundarioExterno().getCodEmpaqueSecundarioExterno());
            for (int i = 1; i <= ingresosAlmacenDetalle.getNroUnidadesEmpaque(); i++) {
                IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstadoItem = new IngresosAlmacenDetalleEstado();
                ingresosAlmacenDetalleEstadoItem.setEtiqueta(i);
                ingresosAlmacenDetalleEstadoItem.setEstadosMaterial(this.estadoMaterial(ingresosAlmacenDetalle.getMateriales()));
                ingresosAlmacenDetalleEstadoItem.getEmpaqueSecundarioExterno().setCodEmpaqueSecundarioExterno(ingresosAlmacenDetalleEstado.getEmpaqueSecundarioExterno().getCodEmpaqueSecundarioExterno());
                ingresosAlmacenDetalleEstadoItem.getEmpaqueSecundarioExterno().setNombreEmpaqueSecundarioExterno(nombreEmpaqueSecundario);
                ingresosAlmacenDetalleEstadoItem.setCantidadParcial(0);
                ingresosAlmacenDetalleEstadoItem.setFechaManufactura(new Date());
                ingresosAlmacenDetalleEstadoItem.setLoteMaterialProveedor("");
                ingresosAlmacenDetalleEstadoItem.setFechaVencimiento(new Date());
                ingresosAlmacenDetalleEstadoItem.setFechaReanalisis(new Date());
                ingresosAlmacenDetalleEstadoItem.setObservaciones("");
                ingresosAlmacenDetalle.getIngresosAlmacenDetalleEstadoList().add(ingresosAlmacenDetalleEstadoItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public EstadosMaterial estadoMaterial(Materiales material) {
        EstadosMaterial estadosMaterial = new EstadosMaterial();
        LOGGER.debug("verificacion estado material "+material.getNombreMaterial());
        try {
            if (verificar(material.getCodMaterial())) {
                LOGGER.debug("estado cuarentena ");
                estadosMaterial.setCodEstadoMaterial(1);
                estadosMaterial.setNombreEstadoMaterial("CUARENTENA");
            } else {
                estadosMaterial.setCodEstadoMaterial(2);
                LOGGER.debug("estado aprobado");
                estadosMaterial.setNombreEstadoMaterial("APROBADO");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return estadosMaterial;
    }
    /*
    funcion que verifica un ingreso anterior
    */
    private void verificarLoteMaterialProveedorAnterior(IngresosAlmacenDetalleEstado ingresoEtiqueta)
    {
        int codCapituloMaterialPrima = 2;
        int codCapituloEmpaquePrimario = 3;
        SimpleDateFormat sdfMesAnio = new SimpleDateFormat("MM/yyyy");
        if(ingresoEtiqueta.getLoteMaterialProveedor().trim().length()>0
                && (ingresoEtiqueta.getMateriales().getGrupos().getCapitulos().getCodCapitulo() == codCapituloMaterialPrima
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
                                                            .append(" and iade.LOTE_MATERIAL_PROVEEDOR = '").append(ingresoEtiqueta.getLoteMaterialProveedor().trim()).append("'");
                LOGGER.debug("consulta verificar fecha de vencimiento "+consulta.toString());
                ResultSet res =st.executeQuery(consulta.toString());
                ingresoEtiqueta.setLoteProveedorEncontradoIngresoAnterior(false);
                if(res.next())
                {
                    ingresoEtiqueta.setLoteProveedorEncontradoIngresoAnterior(true);
                    ingresoEtiqueta.setFechaVencimiento(res.getTimestamp("FECHA_VENCIMIENTO"));
                    ingresoEtiqueta.setFechaReanalisis(res.getTimestamp("FECHA_REANALISIS"));
                    ingresoEtiqueta.setFechaVencimientoFormatoMMYY(sdfMesAnio.format(res.getTimestamp("FECHA_VENCIMIENTO")));
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
    
    public String loteMaterialProveedor_onBlur()throws SQLException
    {
        ingresosAlmacenDetalleEstado.setMateriales(ingresosAlmacenDetalle.getMateriales());
        this.verificarLoteMaterialProveedorAnterior(ingresosAlmacenDetalleEstado);
        if(!ingresosAlmacenDetalleEstado.isLoteProveedorEncontradoIngresoAnterior())
        {
            ingresosAlmacenDetalleEstado.setEstadosMaterial(this.estadoMaterial(ingresosAlmacenDetalle.getMateriales()));
        }
        return null;
    }

    public String repetirValoresIngresoAlmacenDetalleEstado_action() {
        try {
            ingresosAlmacenDetalleEstado.getEmpaqueSecundarioExterno().setNombreEmpaqueSecundarioExterno(this.buscaEmpaqueSecundarioExterno(ingresosAlmacenDetalleEstado.getEmpaqueSecundarioExterno().getCodEmpaqueSecundarioExterno()));
            ingresosAlmacenDetalleEstado.getEstanteAlmacen().setNombreEstante(this.buscaEstanteAmbiente(ingresosAlmacenDetalleEstado.getEstanteAlmacen().getCodEstante()));
            ingresosAlmacenDetalle.setCantTotalIngresoFisico(0);
            for(IngresosAlmacenDetalleEstado copia : ingresosAlmacenDetalle.getIngresosAlmacenDetalleEstadoList()){
                copia.setMateriales(ingresosAlmacenDetalleEstado.getMateriales().clone());
                copia.setEmpaqueSecundarioExterno(ingresosAlmacenDetalleEstado.getEmpaqueSecundarioExterno().clone());
                 copia.setCantidadParcial(ingresosAlmacenDetalleEstado.getCantidadParcial());
                 copia.setTara(ingresosAlmacenDetalleEstado.getTara());
                 copia.setLoteMaterialProveedor(ingresosAlmacenDetalleEstado.getLoteMaterialProveedor());
                 copia.setFechaManufactura(ingresosAlmacenDetalleEstado.getFechaManufactura());
                 copia.setFechaVencimiento(ingresosAlmacenDetalleEstado.getFechaVencimiento());
                 copia.setFechaReanalisis(ingresosAlmacenDetalleEstado.getFechaReanalisis());
                 copia.setEstadosMaterial(ingresosAlmacenDetalleEstado.getEstadosMaterial().clone());
                 copia.setEstadosMaterialSelectList(ingresosAlmacenDetalleEstado.getEstadosMaterialSelectList());
                 copia.setLoteProveedorEncontradoIngresoAnterior(ingresosAlmacenDetalleEstado.isLoteProveedorEncontradoIngresoAnterior());
                 copia.setFechaVencimientoFormatoMMYY(ingresosAlmacenDetalleEstado.getFechaVencimientoFormatoMMYY());
                if(conDensidad == true){
                    copia.setCantidadParcial(ingresosAlmacenDetalleEstado.getCantidadParcial()*ingresosAlmacenDetalleEstado.getDensidad());
                    copia.setDensidad(ingresosAlmacenDetalleEstado.getDensidad());
                }
                if(copia.getChecked()){
                    copia.setEstanteAlmacen(ingresosAlmacenDetalleEstado.getEstanteAlmacen().clone());
                    copia.setFila(ingresosAlmacenDetalleEstado.getFila());
                    copia.setColumna(ingresosAlmacenDetalleEstado.getColumna());
                }
                ingresosAlmacenDetalle.setCantTotalIngresoFisico(ingresosAlmacenDetalle.getCantTotalIngresoFisico() + copia.getCantidadParcial());
             }
            
        } catch (Exception e) {
            LOGGER.warn(e);
        }
        return null;
    }

    public String buscaEmpaqueSecundarioExterno(int codEmpaqueSecundario) {
        String nombreEmpaqueSecundario = "";
        try {
            Iterator i = empaqueSecundarioExternoList.iterator();
            while (i.hasNext()) {
                SelectItem selectItem = (SelectItem) i.next();
                if (Integer.valueOf(selectItem.getValue().toString()) == codEmpaqueSecundario) {
                    nombreEmpaqueSecundario = selectItem.getLabel().toString();
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return nombreEmpaqueSecundario;
    }
    public String buscaEstanteAmbiente(int codEstanteAmbiente){
         String nombreEstante = "";
         try {
             Iterator i = estanteAlmacenList.iterator();
             while(i.hasNext()){
                 SelectItem selectItem = (SelectItem)i.next();
                 if(Integer.valueOf(selectItem.getValue().toString())==codEstanteAmbiente){
                    nombreEstante = selectItem.getLabel().toString();
                     break;
                 }
             }
         } catch (Exception e) {
             e.printStackTrace();
         }
         return nombreEstante;
     }

    public void cargarEmpaques() {
        try {
            con = Util.openConnection(con);
            String consulta = " select e.COD_EMPAQUE_SECUNDARIO_EXTERNO,e.NOMBRE_EMPAQUE_SECUNDARIO_EXTERNO " +
                    " from EMPAQUES_SECUNDARIO_EXTERNO e where e.COD_ESTADO_REGISTRO = 1 ";

            ResultSet rs = null;
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            empaqueSecundarioExternoList.clear();
            rs = st.executeQuery(consulta);
            while (rs.next()) {
                empaqueSecundarioExternoList.add(new SelectItem(rs.getString("COD_EMPAQUE_SECUNDARIO_EXTERNO"), rs.getString("NOMBRE_EMPAQUE_SECUNDARIO_EXTERNO")));
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
    }

    public boolean verificaTransaccionCerradaAlmacen() {
        boolean transaccionCerradaAlmacen = false;
        Date fechaActual = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        try {
            ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            String consulta = " select * from estados_transacciones_almacen " +
                    " where cod_gestion='" + usuario.getGestionesGlobal().getCodGestion() + "' " +
                    " and cod_mes = '" + Integer.valueOf(sdf.format(fechaActual)) + "'  " +
                    " and estado=1 ";
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

    public int generaCodIngresoAlmacen() {
        int codIngresoAlmacen = 0;
        ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
        try {
            String consulta = "  select (isnull(max(cod_ingreso_almacen),0)+1) cod_ingreso_almacen  " +
                    " from ingresos_almacen  ";
            System.out.println("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            if (rs.next()) {
                codIngresoAlmacen = rs.getInt("cod_ingreso_almacen");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return codIngresoAlmacen;
    }

    public int generaNroIngresoAlmacen() {
        int codIngresoAlmacen = 0;
        ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
        try {
            String consulta = " select (isnull(max(nro_ingreso_almacen),0)+1) nro_ingreso_almacen  from ingresos_almacen where cod_gestion='" + usuario.getGestionesGlobal().getCodGestion() + "' " +
                    " and estado_sistema=1 and cod_almacen='" + usuario.getAlmacenesGlobal().getCodAlmacen() + "' ";
            System.out.println("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            if (rs.next()) {
                codIngresoAlmacen = rs.getInt("nro_ingreso_almacen");
            }
            st.close();
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return codIngresoAlmacen;
    }

    public boolean existeDetalleIngresoDetalleAlmacen(List ingresosAlmacenDetalleList) {
        boolean existeDetalle = true;
        boolean checkSeleccionado = false;
        try {
            Iterator i = ingresosAlmacenDetalleList.iterator();
            //inicio ale orden de compra
            //   while(i.hasNext()){
            //    IngresosAlmacenDetalle ingresosAlmacenDetalleItem = (IngresosAlmacenDetalle)i.next();

            // if(ingresosAlmacenDetalleItem.getChecked()==true && ingresosAlmacenDetalleItem.getIngresosAlmacenDetalleEstadoList().size()==0){
            //     existeDetalle = false;
            //    mensaje = " No existe detalle de los items seleccionados ";
            //   break;
            //  }
            //  }
            // i = ingresosAlmacenDetalleList.iterator();
            while (i.hasNext()) {
                IngresosAlmacenDetalle ingresosAlmacenDetalleItem = (IngresosAlmacenDetalle) i.next();
                if (ingresosAlmacenDetalleItem.getIngresosAlmacenDetalleEstadoList().size() > 0) {
                    //final ale orden de compra
                    checkSeleccionado = true;
                    break;
                }
            }
            if (checkSeleccionado == false) {
                mensaje = "No existe ningun item detallado";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return existeDetalle && checkSeleccionado;
    }

    public boolean verificar(String cod_material) throws SQLException {
        Connection con = null;
        con = Util.openConnection(con);
        boolean f=false;
        try {
            String consulta="select g.COD_GRUPO,g.COD_CAPITULO,m.MATERIAL_MUESTREO"+
                            " from materiales m inner join grupos g on g.COD_GRUPO=m.COD_GRUPO"+
                            " where m.COD_MATERIAL='"+cod_material+"'";
            consulta="select m.MATERIAL_MUESTREO, m.MATERIAL_PRODUCCION"+
                            " from materiales m "+
                            " where m.COD_MATERIAL='"+cod_material+"'";
            System.out.println("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet res = st.executeQuery(consulta);
            int codCapituloV=0;
            int codGrupoV=0;
            boolean materialMuestreo=true;
            int material_muestreo=0;
            int material_produccion=0;
            if (res.next()){
                material_muestreo=res.getInt("MATERIAL_MUESTREO");
                material_produccion=res.getInt("MATERIAL_PRODUCCION");
            }
            if(material_muestreo==1 && material_produccion==1)
            {
                f=true;
                System.out.println("entro true :"+material_produccion+", "+material_produccion);
            }
            else{
                f=false;
                System.out.println("entro false :"+material_produccion+", "+material_produccion);
            }
        } catch (Exception e) {
            e.printStackTrace();
            //con.rollback();
        } finally {
            con.close();
        }

        return f;
    }

    public String guardarIngresoAlmacen_action(){
        this.transaccionExitosa = false;
        LOGGER.debug("-----------------------------------------------INICIO REGISTRAR INGRESO POR ORDEN DE COMPRA-----------------------------");
        Connection con = null;
        mensaje = "";
        try {
            con = Util.openConnection(con);
            
            setMensaje("");
            ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            if (this.verificaTransaccionCerradaAlmacen() == true) {
                mensaje = "Las transacciones para este mes fueron cerradas ";
                return null;
            }
            if (existeDetalleIngresoDetalleAlmacen(ingresosAlmacenDetalleList) == false) {

                return null;
            }
            if(ingresosAlmacen.getProveedores().getCodProveedor().equals("0")){
                 mensaje = " Registre el proveedor ";
                 return null;
             }


            ingresosAlmacen.setCodIngresoAlmacen(this.generaCodIngresoAlmacen());
            ingresosAlmacen.setNroIngresoAlmacen(this.generaNroIngresoAlmacen());
            ingresosAlmacen.getTiposIngresoAlmacen().setCodTipoIngresoAlmacen(1);//con orden de compra
            ingresosAlmacen.getEstadosIngresoAlmacen().setCodEstadoIngresoAlmacen(1);
            ingresosAlmacen.setFechaIngresoAlmacen(new Date());
            ingresosAlmacen.setCreditoFiscalSiNo(ordenesCompra.getCreditoFiscalSiNo());


            ingresosAlmacen.getTiposDocumentos().setCodTipoDocumento(ingresosAlmacen.getCreditoFiscalSiNo() == 1 ? "1" : "2");
            ingresosAlmacen.getProveedores().setCodProveedor(ordenesCompra.getProveedores().getCodProveedor());
            ingresosAlmacen.setTiposCompra(ordenesCompra.getTiposCompra());
            ingresosAlmacen.getPersonal().setCodPersonal(usuario.getUsuarioModuloBean().getCodUsuarioGlobal());
            ingresosAlmacen.getEstadosIngresosLiquidacion().setCodEstadoIngresoLiquidacion(2); //no liquidado


            con.setAutoCommit(false);
            ingresosAlmacen.setEstadoSistema(1);
            String consulta = " insert into ingresos_almacen (cod_ingreso_almacen,nro_ingreso_almacen,cod_tipo_ingreso_almacen, " +
                    " cod_orden_compra,cod_gestion,cod_estado_ingreso_almacen, " +
                    " credito_fiscal_si_no,cod_proveedor, " +
                    " cod_tipo_compra,estado_sistema,cod_almacen,cod_devolucion,fecha_ingreso_almacen, " +
                    " cod_tipo_documento,cod_personal,cod_estado_ingreso_liquidacion,obs_ingreso_almacen,NRO_DOCUMENTO)  " +
                    " values  ('" + ingresosAlmacen.getCodIngresoAlmacen() + "'," +
                    " '" + ingresosAlmacen.getNroIngresoAlmacen() + "', " +
                    " '" + ingresosAlmacen.getTiposIngresoAlmacen().getCodTipoIngresoAlmacen() + "'," +
                    " '" + ingresosAlmacen.getOrdenesCompra().getCodOrdenCompra() + "'," +
                    " '" + usuario.getGestionesGlobal().getCodGestion() + "','" + ingresosAlmacen.getEstadosIngresoAlmacen().getCodEstadoIngresoAlmacen() + "'," +
                    " '" + ingresosAlmacen.getCreditoFiscalSiNo() + "', " +
                    " '" + ingresosAlmacen.getProveedores().getCodProveedor() + "', " +
                    " '" + ingresosAlmacen.getTiposCompra().getCodTipoCompra() + "'," +
                    " '" + ingresosAlmacen.getEstadoSistema() + "', " +
                    " '" + usuario.getAlmacenesGlobal().getCodAlmacen() + "',0 ," +
                    " '" + sdf.format(ingresosAlmacen.getFechaIngresoAlmacen()) + "'," +
                    " '" + ingresosAlmacen.getTiposDocumentos().getCodTipoDocumento() + "'," +
                    " '" + ingresosAlmacen.getPersonal().getCodPersonal() + "', " +
                    " '" + ingresosAlmacen.getEstadosIngresosLiquidacion().getCodEstadoIngresoLiquidacion() + "','" + ingresosAlmacen.getObsIngresoAlmacen() + "','" + ingresosAlmacen.getNroDocumento() + "')  ";
            LOGGER.debug(" consulta registrar ingreso oc" + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            st.executeUpdate(consulta);

            Iterator i = ingresosAlmacenDetalleList.iterator();
            while (i.hasNext()) {
                IngresosAlmacenDetalle ingresosAlmacenDetalleItem = (IngresosAlmacenDetalle) i.next();
                //inicio ale orden de compra
                if (ingresosAlmacenDetalleItem.getIngresosAlmacenDetalleEstadoList().size() > 0) {
                    //final ale orden de compra
                    consulta = " insert into ingresos_almacen_detalle (cod_material,cod_ingreso_almacen,cod_seccion,nro_unidades_empaque, " +
                            " cant_total_ingreso,cant_total_ingreso_fisico,cod_unidad_medida,precio_total_material, " +
                            " precio_unitario_material,costo_unitario,observacion,costo_promedio,precio_neto)" +
                            " values('" + ingresosAlmacenDetalleItem.getMateriales().getCodMaterial() + "'," +
                            " '" + ingresosAlmacen.getCodIngresoAlmacen() + "'," +
                            " '" + ingresosAlmacenDetalleItem.getSecciones().getCodSeccion() + "'," +
                            " '" + ingresosAlmacenDetalleItem.getNroUnidadesEmpaque() + "' ," +
                            " '" + ingresosAlmacenDetalleItem.getCantTotalIngreso() + "'," +
                            " '" + ingresosAlmacenDetalleItem.getCantTotalIngresoFisico() + "'," +
                            " '" + ingresosAlmacenDetalleItem.getUnidadesMedida().getCodUnidadMedida() + "'," +
                            " '" + ingresosAlmacenDetalleItem.getPrecioTotalMaterial() + "'," +
                            " '" + ingresosAlmacenDetalleItem.getPrecioUnitarioMaterial() + "'," +
                            " '" + ingresosAlmacenDetalleItem.getCostoUnitario() + "'," +
                            " '" + ingresosAlmacenDetalleItem.getObservacion() + "'," +
                            " '" + ingresosAlmacenDetalleItem.getCostoPromedio() + "'," +
                            " '" + ingresosAlmacenDetalleItem.getPrecioNeto() + "' ) ";
                    LOGGER.debug(" consulta registrar detalle oc" + consulta);
                    st.executeUpdate(consulta);
                    boolean registrarFechaVencimiento = (ingresosAlmacenDetalleItem.getMateriales().getGrupos().getGruposFechaVencimiento().getAplicaFechaVencimiento()
                                                                || ingresosAlmacenDetalleItem.getAplicaFechaVencimiento());
                    LOGGER.info("registrar fecha de vencimiento grupo: "+ingresosAlmacenDetalleItem.getMateriales().getGrupos().getGruposFechaVencimiento().getAplicaFechaVencimiento());
                    LOGGER.info("registrar fecha de vencimiento item: "+ingresosAlmacenDetalleItem.getAplicaFechaVencimiento());
                    Iterator iterator = ingresosAlmacenDetalleItem.getIngresosAlmacenDetalleEstadoList().iterator();
                    while (iterator.hasNext()) {
                        IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstadoItem = (IngresosAlmacenDetalleEstado) iterator.next();
                        ingresosAlmacenDetalleEstadoItem.setCantidadRestante(ingresosAlmacenDetalleEstadoItem.getCantidadParcial());
                        String[] fechaSplit = (registrarFechaVencimiento ?ingresosAlmacenDetalleEstadoItem.getFechaVencimientoFormatoMMYY().split("/"):null);
                        consulta = " insert into ingresos_almacen_detalle_estado " +
                                " (etiqueta,cod_ingreso_almacen,cod_material,cod_estado_material, " +
                                " cod_empaque_secundario_externo,cantidad_parcial,cantidad_restante, " +
                                " fecha_vencimiento,lote_material_proveedor,lote_interno,fecha_manufactura, " +
                                " fecha_reanalisis,observaciones,obs_control_calidad,tara,cod_estante,fila,columna,densidad)values(" +
                                " '" + ingresosAlmacenDetalleEstadoItem.getEtiqueta() + "', " +
                                " '" + ingresosAlmacen.getCodIngresoAlmacen() + "'," +
                                " '" + ingresosAlmacenDetalleItem.getMateriales().getCodMaterial() + "'," +
                                " '" + ingresosAlmacenDetalleEstadoItem.getEstadosMaterial().getCodEstadoMaterial() + "'," +
                                " '" + ingresosAlmacenDetalleEstadoItem.getEmpaqueSecundarioExterno().getCodEmpaqueSecundarioExterno() + "'," +
                                " '" + ingresosAlmacenDetalleEstadoItem.getCantidadParcial() + "'," +
                                " '" + ingresosAlmacenDetalleEstadoItem.getCantidadRestante() + "'," +
                                (registrarFechaVencimiento? "dbo.FN_VENCIMIENTO_INGRESO_ALMACEN('"+fechaSplit[1]+"/"+fechaSplit[0]+"/01'),":" null,")+
                                " '" + ingresosAlmacenDetalleEstadoItem.getLoteMaterialProveedor().trim()+ "'," +
                                " '" + ingresosAlmacenDetalleEstadoItem.getLoteInterno() + "'," +
                                " '" + sdf.format(ingresosAlmacenDetalleEstadoItem.getFechaManufactura()) + "'," +
                                " '" + sdf.format(ingresosAlmacenDetalleEstadoItem.getFechaReanalisis()) + "'," +
                                " '" + ingresosAlmacenDetalleEstadoItem.getObservaciones() + "'," +
                                " '" + ingresosAlmacenDetalleEstadoItem.getObsControlCalidad() + "'," +
                                " '" + ingresosAlmacenDetalleEstadoItem.getTara() + "' ," +
                                " '"+ingresosAlmacenDetalleEstadoItem.getEstanteAlmacen().getCodEstante()+"'," +
                                " '"+ingresosAlmacenDetalleEstadoItem.getFila()+"'," +
                                " '"+ingresosAlmacenDetalleEstadoItem.getColumna()+"','"+ingresosAlmacenDetalleEstadoItem.getDensidad()+"')  ";
                        this.actualizaOrdenCompraDetalle(ingresosAlmacenDetalleItem, ingresosAlmacenDetalleEstadoItem);
                        LOGGER.debug("consulta registra ingresos almacen detalle estado "+consulta);
                        st.executeUpdate(consulta);

                    }

                }
            }
            //REGISTRO DE LOGS DEL NUEVO INGRESO
            consulta = " exec [PAA_REGISTRO_INGRESO_ALMACEN_LOG] " + ingresosAlmacen.getCodIngresoAlmacen() 
                    + " , "+ usuario.getUsuarioModuloBean().getCodUsuarioGlobal()
                    + " , 3";                    
            LOGGER.info("consulta ejecutar: " + consulta);            
            PreparedStatement pst=con.prepareStatement(consulta);
            pst.executeUpdate();   
            
            if (materialMuestreo(ingresosAlmacenDetalleList) == true && materialMuestreo(ingresosAlmacenDetalleList) == false) { //para evitar que se generen solicitudes automaticas
                SolicitudesSalida solicitudesSalida = new SolicitudesSalida();
                solicitudesSalida.getSolicitante().setCodPersonal("303");
                solicitudesSalida.getAreaDestinoSalida().setCodAreaEmpresa("40");
                ManagedRegistroSolicitudSalidaAlmacen managedRegistroSolicitudSalidaAlmacen = new ManagedRegistroSolicitudSalidaAlmacen();
                usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
                sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                solicitudesSalida.setCodFormSalida(managedRegistroSolicitudSalidaAlmacen.generaCodSolicitudSalidaAlmacen());
                consulta = " INSERT INTO SOLICITUDES_SALIDA(  COD_GESTION,  COD_FORM_SALIDA,  COD_TIPO_SALIDA_ALMACEN,  COD_ESTADO_SOLICITUD_SALIDA_ALMACEN," +
                        "  SOLICITANTE,  AREA_DESTINO_SALIDA,  FECHA_SOLICITUD,  COD_LOTE_PRODUCCION,  OBS_SOLICITUD,  ESTADO_SISTEMA," +
                        "  CONTROL_CALIDAD,  COD_INGRESO_ALMACEN,  COD_ALMACEN,  orden_trabajo,cod_compprod,cod_presentacion) " +
                        "VALUES (  '" + usuario.getGestionesGlobal().getCodGestion() + "','" + solicitudesSalida.getCodFormSalida() + "',  2,  1," +
                        "  '" + solicitudesSalida.getSolicitante().getCodPersonal() + "',  '" + solicitudesSalida.getAreaDestinoSalida().getCodAreaEmpresa() + "', '" + sdf.format(new Date()) + "', '" + solicitudesSalida.getCodLoteProduccion() + "', '" + solicitudesSalida.getObsSolicitud() + "', 1," +
                        "  0,  0, '" + usuario.getAlmacenesGlobal().getCodAlmacen() + "' , '" + solicitudesSalida.getOrdenTrabajo() + "','" + solicitudesSalida.getComponentesProd().getCodCompprod() + "','" + solicitudesSalida.getPresentacionesProducto().getCodPresentacion() + "'); ";
                LOGGER.debug("consulta " + consulta);
                st.executeUpdate(consulta);
                i = ingresosAlmacenDetalleList.iterator();

                while (i.hasNext()) {
                    IngresosAlmacenDetalle ingresosAlmacenDetalle = (IngresosAlmacenDetalle) i.next();
                    if (ingresosAlmacenDetalle.getMateriales().getMaterialMuestreo() > 0) {
                        consulta = " INSERT INTO SOLICITUDES_SALIDA_DETALLE(  COD_FORM_SALIDA,  COD_MATERIAL,  CANTIDAD,  CANTIDAD_ENTREGADA," +
                                "  COD_UNIDAD_MEDIDA) VALUES (  '" + solicitudesSalida.getCodFormSalida() + "', '" + ingresosAlmacenDetalle.getMateriales().getCodMaterial() + "',  '" + ingresosAlmacenDetalle.getMateriales().getCantidadMaximaMuestreo() * ingresosAlmacenDetalle.getNroUnidadesEmpaque() + "',  0,'" + ingresosAlmacenDetalle.getUnidadesMedida().getCodUnidadMedida() + "'); ";
                        LOGGER.debug("consulta " + consulta);
                        st.executeUpdate(consulta);
                        Iterator i1 = ingresosAlmacenDetalle.getIngresosAlmacenDetalleEstadoList().iterator();
                        while (i1.hasNext()) {
                            IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstado = (IngresosAlmacenDetalleEstado) i1.next();
                            consulta = "  INSERT INTO SOLICITUDES_SALIDA_DETALLE_SOLICITUD(  COD_FORM_SALIDA,  COD_INGRESO_ALMACEN,  COD_MATERIAL," +
                                    "  ETIQUETA,  CANTIDAD) VALUES (  '" + solicitudesSalida.getCodFormSalida() + "','" + ingresosAlmacen.getCodIngresoAlmacen() + "' ,'" + ingresosAlmacenDetalle.getMateriales().getCodMaterial() + "'," +
                                    "'" + ingresosAlmacenDetalleEstado.getEtiqueta() + "', '" + ingresosAlmacenDetalle.getMateriales().getCantidadMaximaMuestreo() + "'); ";
                            st.executeUpdate(consulta);
                        }
                    }
                }

            }
            //Registro de salida automatica --Jaime
            String consultaSalidaAutomatica="exec [USP_INSERT_SALIDAS_AUTOMATICA_X_ORDENCOMPRA] "+ingresosAlmacen.getPersonal().getCodPersonal()+", "+usuario.getAlmacenesGlobal().getCodAlmacen()+", "+ingresosAlmacen.getCodIngresoAlmacen();
            LOGGER.debug("consulta J: " + consultaSalidaAutomatica);
            st.executeUpdate(consultaSalidaAutomatica);
            //Fin de registro de salida automatica --Jaime
            con.commit();
            this.mostrarMensajeTransaccionExitosa("Se registro satisfactoriamente el ingreso por Orden de Compra");
            
        }
        catch(SQLException ex){
            LOGGER.warn(ex);
            this.mostrarMensajeTransaccionFallida("Ocurrio un error al momento de registrar el ingreso por O.C., intente de nuevo");
            try{
                con.rollback();
            }
            catch(SQLException sel){
                LOGGER.warn(sel);
            }
        }
        catch (Exception e) {
            LOGGER.warn(e);
            this.mostrarMensajeTransaccionFallida("Ocurrio un error al momento de registrar el ingreso por O.C., intente de nuevo");
            try{
                con.rollback();
            }
            catch(SQLException sel){
                LOGGER.warn(sel);
            }
        } finally {
            try{
                con.close();
            }
            catch(SQLException ex){
            LOGGER.warn("error", ex);}
        }
        if(ingresosAlmacen.getTiposIngresoAlmacen().getCodTipoIngresoAlmacen()!=2 && this.transaccionExitosa){
            ThreadCosteoByOrdenCompra thread = new ThreadCosteoByOrdenCompra(ingresosAlmacenDetalleList, ingresosAlmacen);
            thread.start();
        }
        LOGGER.debug("-----------------------------------------------FINAL REGISTRAR INGRESO POR ORDEN DE COMPRA-----------------------------");
        LOGGER.debug("mensaje: "+mensaje);
        return null;
    }

    public boolean materialMuestreo(List ingresosAlmacenDetalleList) {
        boolean materialMuestreo = false;
        Iterator i = ingresosAlmacenDetalleList.iterator();
        while (i.hasNext()) {
            IngresosAlmacenDetalle ingresosAlmacenDetalle = (IngresosAlmacenDetalle) i.next();
            if (ingresosAlmacenDetalle.getMateriales().getMaterialMuestreo() > 0) {
                materialMuestreo = true;
                break;
            }
        }
        return materialMuestreo;
    }

    public void actualizaOrdenCompraDetalle(IngresosAlmacenDetalle ingresosAlmacenDetalleItem, IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstadoItem) throws SQLException, Exception {
        
            String consulta = " update ordenes_compra_detalle set " +
                    " cantidad_ingreso_almacen = (cantidad_ingreso_almacen + '" + ingresosAlmacenDetalleEstadoItem.getCantidadParcial() + "') " +
                    " where cod_orden_compra='" + ordenesCompra.getCodOrdenCompra() + "' " +
                    " and cod_material='" + ingresosAlmacenDetalleItem.getMateriales().getCodMaterial() + "'" +
                    "  ";
            //inicio alejandro 5
            consulta = " update ordenes_compra_detalle set " +
                    " cantidad_ingreso_almacen = (cantidad_ingreso_almacen +";

            if (ingresosAlmacenDetalleItem.getValorEquivalencia() > 0) {
                consulta += " '" + (ingresosAlmacenDetalleEstadoItem.getCantidadParcial() * ingresosAlmacenDetalleItem.getValorEquivalencia()) + "') ";
            } else {
                consulta += " '" + (ingresosAlmacenDetalleEstadoItem.getCantidadParcial() / this.obtieneEquivalencia2(ingresosAlmacenDetalleItem)) + "') ";
            }
            consulta += " where cod_orden_compra='" + ordenesCompra.getCodOrdenCompra() + "' " +
                    " and cod_material='" + ingresosAlmacenDetalleItem.getMateriales().getCodMaterial() + "'" +
                    "  ";
            //final alejandro 5
            //final alejandro
            LOGGER.debug("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            st.executeUpdate(consulta);

            consulta = " SELECT SUM(ocd.CANTIDAD_INGRESO_ALMACEN) CANTIDAD_INGRESO_ALMACEN,SUM(ocd.CANTIDAD_NETA) CANTIDAD_NETA " +
                    " FROM ORDENES_COMPRA_DETALLE OCD  WHERE OCD.COD_ORDEN_COMPRA = '" + ordenesCompra.getCodOrdenCompra() + "'  ";
            LOGGER.debug("consulta " + consulta);
            Statement st1 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st1.executeQuery(consulta);
            if (rs.next()) {
                if (rs.getFloat("CANTIDAD_INGRESO_ALMACEN") > 0 && rs.getFloat("CANTIDAD_INGRESO_ALMACEN") < rs.getFloat("CANTIDAD_NETA")) {
                    //INGRESADO PARCIALMENTE
                    consulta = " UPDATE ORDENES_COMPRA  SET COD_ESTADO_COMPRA = '6' where cod_orden_compra = '" + ordenesCompra.getCodOrdenCompra() + "'";
                } else if (rs.getFloat("CANTIDAD_INGRESO_ALMACEN") > 0 && rs.getFloat("CANTIDAD_INGRESO_ALMACEN") >= rs.getFloat("CANTIDAD_NETA")) {
                    consulta = " UPDATE ORDENES_COMPRA  SET COD_ESTADO_COMPRA = '7' where cod_orden_compra = '" + ordenesCompra.getCodOrdenCompra() + "'";
                }
                LOGGER.debug("consulta " + consulta);
                st.executeUpdate(consulta);
            }
            st.close();
            st1.close();
            rs.close();
        
    }

    //inicio alejandro
    public String getActionGenerarCambio() {
        Iterator i = ordenesCompraList.iterator();
        while (i.hasNext()) {
            OrdenesCompra ordenesCompraItem = (OrdenesCompra) i.next();
            if (ordenesCompraItem.getChecked().booleanValue() == true) {
                compraEditar = ordenesCompraItem;
            }
        }
        String consultaProductos = "select ocd.COD_MATERIAL from ordenes_compra_detalle ocd where ocd.cod_orden_compra= '" + compraEditar.getCodOrdenCompra() + "'";
        System.out.println("consulta para los productos " + consultaProductos);
        try {
            Connection con = null;
            con = Util.openConnection(con);
            Statement st65 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rest65 = st65.executeQuery(consultaProductos);
            getListaMateriales().clear();
            while (rest65.next()) {
                String consultaDetalles = "select ISNULL(m.CANTIDAD_MAXIMAMUESTREO,0) as cantidadmax,ISNULL(m.CANTIDAD_MINIMAMUESTREO,0) as cantidadmin, ISNULL(m.MATERIAL_PRODUCCION,'0') as produccion,ISNULL(m.MATERIAL_MUESTREO,'0') as muestreo, m.COD_MATERIAL,m.NOMBRE_MATERIAL,g.COD_GRUPO,g.NOMBRE_GRUPO,cp.COD_CAPITULO,cp.NOMBRE_CAPITULO";
                consultaDetalles += " from materiales m, GRUPOS g , CAPITULOS cp where m.COD_GRUPO=g.COD_GRUPO and g.COD_CAPITULO=cp.COD_CAPITULO and m.COD_MATERIAL='" + rest65.getString("COD_MATERIAL") + "'";
                Statement st76 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                System.out.println("consulta detalles " + consultaDetalles);
                ResultSet rest67 = st76.executeQuery(consultaDetalles);
                while (rest67.next()) {
                    Materiales nuevo = new Materiales();
                    nuevo.setCodMaterial(rest67.getString("COD_MATERIAL"));
                    nuevo.setNombreMaterial(rest67.getString("NOMBRE_MATERIAL"));
                    nuevo.setMaterialMuestreo(rest67.getInt("muestreo"));
                    nuevo.setMaterialProduccion(rest67.getInt("produccion"));
                    nuevo.setCantidadMaximaMuestreo(rest67.getFloat("cantidadmax"));
                    nuevo.setCantidadMinimaMuestreo(rest67.getFloat("cantidadmin"));
                    Capitulos nuevoCapit = new Capitulos();
                    nuevoCapit.setCodCapitulo(rest67.getInt("COD_CAPITULO"));
                    nuevoCapit.setNombreCapitulo(rest67.getString("NOMBRE_CAPITULO"));
                    Grupos nuevogrupo = new Grupos();
                    nuevogrupo.setCodGrupo(rest67.getInt("COD_GRUPO"));
                    nuevogrupo.setNombreGrupo(rest67.getString("NOMBRE_GRUPO"));
                    nuevogrupo.setCapitulos(nuevoCapit);
                    nuevo.setGrupos(nuevogrupo);
                    getListaMateriales().add(nuevo);

                }
                rest67.close();
                st76.close();
            }
            rest65.close();
            st65.close();
            String consultaCap = "select c.COD_CAPITULO,c.NOMBRE_CAPITULO from CAPITULOS c";
            Statement st76 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rest76 = st76.executeQuery(consultaCap);
            getListaCapitulos().clear();
            while (rest76.next()) {
                SelectItem nuevoInte = new SelectItem(rest76.getInt("COD_CAPITULO"), rest76.getString("NOMBRE_CAPITULO"));
                getListaCapitulos().add(nuevoInte);
            }
            rest76.close();
            st76.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return "";
    }

    public String guardarTodos() {
        for (Materiales current : listaMateriales) {
            String consultaUpdate = "UPDATE MATERIALES  SET ";
            consultaUpdate += " COD_GRUPO ='" + current.getGrupos().getCodGrupo() + "',MATERIAL_PRODUCCION = '" + current.getMaterialProduccion() + "',";
            consultaUpdate += " MATERIAL_MUESTREO = '" + current.getMaterialMuestreo() + "'";
            if (mostrar2) {
                consultaUpdate += ",CANTIDAD_MINIMAMUESTREO ='" + current.getCantidadMinimaMuestreo() + "'";
                consultaUpdate += ", CANTIDAD_MAXIMAMUESTREO = '" + current.getCantidadMaximaMuestreo() + "'";
            } else {
                consultaUpdate += ",CANTIDAD_MINIMAMUESTREO ='0'";
                consultaUpdate += ", CANTIDAD_MAXIMAMUESTREO = '0'";
            }
            consultaUpdate += " WHERE COD_MATERIAL ='" + current.getCodMaterial() + "'";
            System.out.println("consulta de actualizacion " + consultaUpdate);


        }
        return null;
    }

    public String guardarCambiosAction() {
        String consultaUpdate = "UPDATE MATERIALES  SET ";
        consultaUpdate += " COD_GRUPO ='" + materialEditar.getGrupos().getCodGrupo() + "',MATERIAL_PRODUCCION = '" + materialEditar.getMaterialProduccion() + "',";
        consultaUpdate += " MATERIAL_ALMACEN='1', MATERIAL_MUESTREO = '" + materialEditar.getMaterialMuestreo() + "'";
        if (mostrar2) {
            consultaUpdate += ",CANTIDAD_MINIMAMUESTREO ='" + materialEditar.getCantidadMinimaMuestreo() + "'";
            consultaUpdate += ", CANTIDAD_MAXIMAMUESTREO = '" + materialEditar.getCantidadMaximaMuestreo() + "'";
        } else {
            consultaUpdate += ",CANTIDAD_MINIMAMUESTREO ='0'";
            consultaUpdate += ", CANTIDAD_MAXIMAMUESTREO = '0'";
        }
        consultaUpdate += " WHERE COD_MATERIAL ='" + materialEditar.getCodMaterial() + "'";
        System.out.println("consulta de actualizacion " + consultaUpdate);
        try {
            con = Util.openConnection(con);

            PreparedStatement pst23 = con.prepareStatement(consultaUpdate);
            if (pst23.executeUpdate() > 0) {
                System.out.println("se cambio en la base de datos el material " + materialEditar.getNombreMaterial());
                String aux = getActionGenerarCambio();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public String cambiarBoolean() {
        if (materialEditar.getMaterialProduccion() == 1) {
            mostrar = true;
            System.out.println("es 1");
        } else {
            mostrar = false;
            System.out.println("es 0");
        }
        return cambiarBoolean2();
    }

    public String cambiarBoolean2() {
        if (materialEditar.getMaterialMuestreo() == 1) {
            setMostrar2(true);
            System.out.println("es 1 2");
        } else {
            mostrar2 = false;
            System.out.println("es 0 2");
        }
        return null;
    }

    public String seleccionarMaterial_action() throws SQLException {
        for (Materiales current : listaMateriales) {
            if (current.getChecked()) {
                materialEditar = current;
            }
        }
        if (materialEditar.getMaterialProduccion() == 1) {
            mostrar = true;
        } else {
            mostrar = false;
        }
        String con2 = cambiarBoolean();
        String consultaGrupos = "select gp.NOMBRE_GRUPO,gp.COD_GRUPO from GRUPOS gp where gp.COD_CAPITULO='" + materialEditar.getGrupos().getCapitulos().getCodCapitulo() + "'";
        System.out.println("consulta para grupos " + consultaGrupos);
        System.out.println("codigo muestreo " + materialEditar.getMaterialMuestreo());
        try {
            con = Util.openConnection(con);
            Statement st87 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rest87 = st87.executeQuery(consultaGrupos);
            listaGrupos.clear();
            while (rest87.next()) {
                SelectItem nuevoItem = new SelectItem(rest87.getInt("COD_GRUPO"), rest87.getString("NOMBRE_GRUPO"));
                listaGrupos.add(nuevoItem);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            con.close();
        }

        return null;
    }

    public String generarGrupos() {
        String consultaGrupos = "select gp.NOMBRE_GRUPO,gp.COD_GRUPO from GRUPOS gp where gp.COD_CAPITULO='" + materialEditar.getGrupos().getCapitulos().getCodCapitulo() + "'";
        System.out.println("consulta para grupos " + consultaGrupos);
        System.out.println("codigo muestreo " + materialEditar.getMaterialMuestreo());
        try {
            con = Util.openConnection(con);
            Statement st87 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rest87 = st87.executeQuery(consultaGrupos);
            listaGrupos.clear();
            while (rest87.next()) {
                SelectItem nuevoItem = new SelectItem(rest87.getInt("COD_GRUPO"), rest87.getString("NOMBRE_GRUPO"));
                listaGrupos.add(nuevoItem);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }
    //final alejandro

    public String validaFechas_action1() {
        try {
            //System.out.println("entro al evento "+ingresosAlmacenDetalleEstado.getFechaManufactura()+" " + ingresosAlmacenDetalleEstado.getFechaVencimiento());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            ingresosAlmacenDetalleEstado.setFechaManufactura(sdf.parse(sdf.format(ingresosAlmacenDetalleEstado.getFechaManufactura())));
            ingresosAlmacenDetalleEstado.setFechaVencimiento(sdf.parse(sdf.format(ingresosAlmacenDetalleEstado.getFechaVencimiento())));
            mensaje = "";
            if (ingresosAlmacenDetalleEstado.getFechaManufactura().compareTo(ingresosAlmacenDetalleEstado.getFechaVencimiento()) >= 0) {
                //DateTime dateTime = new DateTime(ingresosAlmacenDetalleEstado.getFechaVencimiento());
                //dateTime = dateTime.minusDays(1);
                //ingresosAlmacenDetalleEstado.setFechaManufactura(dateTime.toDate());
                mensaje = "La fecha de Manufactura debe ser menor a la fecha de Vencimiento ";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public String validaFechas_action() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            ingresosAlmacenDetalleEstado.setFechaManufactura(sdf.parse(sdf.format(ingresosAlmacenDetalleEstado.getFechaManufactura())));
            ingresosAlmacenDetalleEstado.setFechaVencimiento(sdf.parse(sdf.format(ingresosAlmacenDetalleEstado.getFechaVencimiento())));
            System.out.println("entro al evento " + ingresosAlmacenDetalleEstado.getFechaManufactura() + " " + ingresosAlmacenDetalleEstado.getFechaVencimiento());
            mensaje = "";
            if (ingresosAlmacenDetalleEstado.getFechaManufactura().compareTo(ingresosAlmacenDetalleEstado.getFechaVencimiento()) >= 0) {
                DateTime dateTime = new DateTime(ingresosAlmacenDetalleEstado.getFechaVencimiento());
                dateTime = dateTime.minusDays(1);
                ingresosAlmacenDetalleEstado.setFechaManufactura(dateTime.toDate());
                mensaje = "La fecha de Manufactura debe ser menor a la fecha de Vencimiento ";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public String cancelarDetalleItem_action() {
        try {
            ingresosAlmacenDetalle.getIngresosAlmacenDetalleEstadoList().clear();
//             mensaje = "";
//             IngresosAlmacenDetalle  ingresosAlmacenDetalleItem = ingresosAlmacenDetalle;
//             ingresosAlmacenDetalleItem.setCantTotalIngresoFisico(ingresosAlmacenDetalle.getCantTotalIngresoFisico());
//             if(this.validaDatosIngresoAcondicionamiento(ingresosAlmacenDetalleItem)==true){
//                 Iterator i= ingresosAlmacenDetalle.getIngresosAlmacenDetalleEstadoList().iterator();
//                 ingresosAlmacenDetalleItem.setCantTotalIngresoFisico(0);
//                 while(i.hasNext()){
//                     IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstadoItem = (IngresosAlmacenDetalleEstado)i.next();
//                     ingresosAlmacenDetalleItem.setCantTotalIngresoFisico(ingresosAlmacenDetalleItem.getCantTotalIngresoFisico()+ingresosAlmacenDetalleEstadoItem.getCantidadParcial());
//                 }
//             }
        //aqui las unidades de medida
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public List cargarAmbienteAlmacen() {
        List ambientesList = new ArrayList();
        try {

            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select cod_ambiente,nombre_ambiente from AMBIENTE_ALMACEN where cod_almacen = '"+usuario.getAlmacenesGlobal().getCodAlmacen()+"' ";
            System.out.println("consulta " + consulta);

            ResultSet rs = null;
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ambientesList.clear();
            rs = st.executeQuery(consulta);
            while (rs.next()) {
                ambientesList.add(new SelectItem(rs.getString("cod_ambiente"), rs.getString("nombre_ambiente")));
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
        return ambientesList;
    }
     public List cargarEstanteAlmacen() {
        List estantesList = new ArrayList();
        try {

            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select cod_estante,nombre_estante from estante_ambiente where cod_ambiente = '"+ingresosAlmacenDetalleEstado.getEstanteAlmacen().getAmbienteAlmacen().getCodAmbiente()+"'  ";
            System.out.println("consulta " + consulta);

            ResultSet rs = null;
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            estantesList.clear();
            rs = st.executeQuery(consulta);
            while (rs.next()) {
                estantesList.add(new SelectItem(rs.getString("cod_estante"), rs.getString("nombre_estante")));
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
        return estantesList;
    }
     public String ambienteAlmacen_change(){
           estanteAlmacenList = cargarEstanteAlmacen();
           return null;
       }
    
    //<editor-fold desc="getter and setter">
        
       public int getCodOrdenCompra() {
           return codOrdenCompra;
       }

       /**
        * @param codOrdenCompra the codOrdenCompra to set
        */
       public void setCodOrdenCompra(int codOrdenCompra) {
           this.codOrdenCompra = codOrdenCompra;
       }

       /**
        * @return the materialesList
        */
       public List getMaterialesList() {
           return materialesList;
       }

       /**
        * @param materialesList the materialesList to set
        */
       public void setMaterialesList(List materialesList) {
           this.materialesList = materialesList;
       }

       /**
        * @return the codMaterial
        */
       public int getCodMaterial() {
           return codMaterial;
       }

       /**
        * @param codMaterial the codMaterial to set
        */
       public void setCodMaterial(int codMaterial) {
           this.codMaterial = codMaterial;
       }

       /**
        * @return the gestionesList
        */
       public List getGestionesList() {
           return gestionesList;
       }

       /**
        * @param gestionesList the gestionesList to set
        */
       public void setGestionesList(List gestionesList) {
           this.gestionesList = gestionesList;
       }

        public IngresosAlmacenDetalleEstado getIngresosAlmacenDetalleEstadoSeleccionado() {
            return ingresosAlmacenDetalleEstadoSeleccionado;
        }

        public void setIngresosAlmacenDetalleEstadoSeleccionado(IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstadoSeleccionado) {
            this.ingresosAlmacenDetalleEstadoSeleccionado = ingresosAlmacenDetalleEstadoSeleccionado;
        }


       /**
        * @return the codGestion
        */
       public int getCodGestion() {
           return codGestion;
       }

       /**
        * @param codGestion the codGestion to set
        */
       public void setCodGestion(int codGestion) {
           this.codGestion = codGestion;
       }

       /**
        * @return the proveedoresList
        */
       public List getProveedoresList() {
           return proveedoresList;
       }

       /**
        * @param proveedoresList the proveedoresList to set
        */
       public void setProveedoresList(List proveedoresList) {
           this.proveedoresList = proveedoresList;
       }

       /**
        * @return the capitulosList
        */
       public List getCapitulosList() {
           return capitulosList;
       }

       /**
        * @param capitulosList the capitulosList to set
        */
       public void setCapitulosList(List capitulosList) {
           this.capitulosList = capitulosList;
       }

       /**
        * @return the gruposList
        */
       public List getGruposList() {
           return gruposList;
       }

       /**
        * @param gruposList the gruposList to set
        */
       public void setGruposList(List gruposList) {
           this.gruposList = gruposList;
       }

       /**
        * @return the codProveedor
        */
       public int getCodProveedor() {
           return codProveedor;
       }

       /**
        * @param codProveedor the codProveedor to set
        */
       public void setCodProveedor(int codProveedor) {
           this.codProveedor = codProveedor;
       }

       /**
        * @return the codCapitulo
        */
       public int getCodCapitulo() {
           return codCapitulo;
       }

       /**
        * @param codCapitulo the codCapitulo to set
        */
       public void setCodCapitulo(int codCapitulo) {
           this.codCapitulo = codCapitulo;
       }

       /**
        * @return the codGrupo
        */
       public int getCodGrupo() {
           return codGrupo;
       }

       /**
        * @param codGrupo the codGrupo to set
        */
       public void setCodGrupo(int codGrupo) {
           this.codGrupo = codGrupo;
       }

       public boolean isAdministradorAlmacen() {
           return administradorAlmacen;
       }

       public void setAdministradorAlmacen(boolean administradorAlmacen) {
           this.administradorAlmacen = administradorAlmacen;
       }
        public List getOrdenesCompraList() {
            return ordenesCompraList;
        }

        public void setOrdenesCompraList(List ordenesCompraList) {
            this.ordenesCompraList = ordenesCompraList;
        }

        public List getOrdenesCompraDetalleList() {
            return ordenesCompraDetalleList;
        }

        public void setOrdenesCompraDetalleList(List ordenesCompraDetalleList) {
            this.ordenesCompraDetalleList = ordenesCompraDetalleList;
        }

        public IngresosAlmacen getIngresosAlmacen() {
            return ingresosAlmacen;
        }

        public void setIngresosAlmacen(IngresosAlmacen ingresosAlmacen) {
            this.ingresosAlmacen = ingresosAlmacen;
        }

        public List getEstadosIngresoAlmacenList() {
            return estadosIngresoAlmacenList;
        }

        public void setEstadosIngresoAlmacenList(List estadosIngresoAlmacenList) {
            this.estadosIngresoAlmacenList = estadosIngresoAlmacenList;
        }

        public List getTiposIngresosAlmacenList() {
            return tiposIngresosAlmacenList;
        }

        public void setTiposIngresosAlmacenList(List tiposIngresosAlmacenList) {
            this.tiposIngresosAlmacenList = tiposIngresosAlmacenList;
        }

        public String getMensaje() {
            return mensaje;
        }

        public void setMensaje(String mensaje) {
            this.mensaje = mensaje;
        }

        public List getIngresosAlmacenDetalleList() {
            return ingresosAlmacenDetalleList;
        }

        public void setIngresosAlmacenDetalleList(List ingresosAlmacenDetalleList) {
            this.ingresosAlmacenDetalleList = ingresosAlmacenDetalleList;
        }

        public HtmlDataTable getIngresosAlmacenDetalleDataTable() {
            return ingresosAlmacenDetalleDataTable;
        }

        public void setIngresosAlmacenDetalleDataTable(HtmlDataTable ingresosAlmacenDetalleDataTable) {
            this.ingresosAlmacenDetalleDataTable = ingresosAlmacenDetalleDataTable;
        }

        public IngresosAlmacenDetalle getIngresosAlmacenDetalle() {
            return ingresosAlmacenDetalle;
        }

        public void setIngresosAlmacenDetalle(IngresosAlmacenDetalle ingresosAlmacenDetalle) {
            this.ingresosAlmacenDetalle = ingresosAlmacenDetalle;
        }

        public List getIngresosAlmacenDetalleEstadoList() {
            return ingresosAlmacenDetalleEstadoList;
        }

        public void setIngresosAlmacenDetalleEstadoList(List ingresosAlmacenDetalleEstadoList) {
            this.ingresosAlmacenDetalleEstadoList = ingresosAlmacenDetalleEstadoList;
        }

        public IngresosAlmacenDetalleEstado getIngresosAlmacenDetalleEstado() {
            return ingresosAlmacenDetalleEstado;
        }

        public void setIngresosAlmacenDetalleEstado(IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstado) {
            this.ingresosAlmacenDetalleEstado = ingresosAlmacenDetalleEstado;
        }

        public List getEmpaqueSecundarioExternoList() {
            return empaqueSecundarioExternoList;
        }

        public void setEmpaqueSecundarioExternoList(List empaqueSecundarioExternoList) {
            this.empaqueSecundarioExternoList = empaqueSecundarioExternoList;
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

        public List<Materiales> getListaMateriales() {
            return listaMateriales;
        }

        public void setListaMateriales(List<Materiales> listaMateriales) {
            this.listaMateriales = listaMateriales;
        }

        public Materiales getMaterialEditar() {
            return materialEditar;
        }

        public void setMaterialEditar(Materiales materialEditar) {
            this.materialEditar = materialEditar;
        }

        public boolean isMostrar() {
            return mostrar;
        }

        public void setMostrar(boolean mostrar) {
            this.mostrar = mostrar;
        }

        public boolean isMostrar2() {
            return mostrar2;
        }

        public void setMostrar2(boolean mostrar2) {
            this.mostrar2 = mostrar2;
        }

        public OrdenesCompra getOrdenesCompra() {
            return ordenesCompra;
        }

        public void setOrdenesCompra(OrdenesCompra ordenesCompra) {
            this.ordenesCompra = ordenesCompra;
        }
        

        public List<SelectItem> getAmbienteAlmacenList() {
            return ambienteAlmacenList;
        }

        public void setAmbienteAlmacenList(List<SelectItem> ambienteAlmacenList) {
            this.ambienteAlmacenList = ambienteAlmacenList;
        }

        public List<SelectItem> getEstanteAlmacenList() {
            return estanteAlmacenList;
        }

        public void setEstanteAlmacenList(List<SelectItem> estanteAlmacenList) {
            this.estanteAlmacenList = estanteAlmacenList;
        }

        public boolean isConDensidad() {
            return conDensidad;
        }

        public void setConDensidad(boolean conDensidad) {
            this.conDensidad = conDensidad;
        }
    //</editor-fold>
    
}
