/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.web;

import com.cofar.bean.Equivalencias;
import com.cofar.bean.EstadosMaterial;
import com.cofar.bean.IngresosAlmacen;
import com.cofar.bean.IngresosAlmacenDetalle;
import com.cofar.bean.IngresosAlmacenDetalleEstado;
import com.cofar.bean.Materiales;
import com.cofar.bean.OrdenesCompra;
import com.cofar.bean.OrdenesCompraDetalle;
import com.cofar.bean.Secciones;
import com.cofar.util.Util;
import com.cofar.util.Utiles;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.faces.component.UISelectItems;
import javax.faces.model.SelectItem;
import org.richfaces.component.html.HtmlDataTable;

/**
 *
 * @author hvaldivia
 */

public class ManagedIngresoAlmacenOrdenCompra2 extends ManagedBean{

    Connection con = null;
    List ordenesCompraList = new ArrayList();
    private List materialesList = new ArrayList();
    private List gestionesList = new ArrayList();
    private String mensaje = "";
    private int codOrdenCompra =0;
    private int codMaterial =0;
    private int codGestion =0;
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



    private List ordenesCompraDetalleList = new ArrayList();

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

    



    


    
    


    /** Creates a new instance of ManagedIngresoAlmacenOrdenCompra */
    public ManagedIngresoAlmacenOrdenCompra2() {
    }
    public String getCargarIngresoAlmacenOrdenCompra(){
        try {
            //begin = 1;
            //end = 10;
            this.cargarContenidoIngresoAlmacenOrdenCompra();
            this.codOrdenCompra=0;
            this.cargarItems();
            this.cargarGestiones();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
     public void cargarItems(){
        try {

            String consulta = "select m.COD_MATERIAL, m.NOMBRE_MATERIAL from materiales m where m.MOVIMIENTO_ITEM=1 order by m.NOMBRE_MATERIAL " ;
            System.out.println("consulta " + consulta);



            con = Util.openConnection(con);

            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);

            materialesList.clear();
            materialesList.add(new SelectItem("0","    Todos      "));
            while(rs.next()){
                Materiales materiales = new Materiales();
                materiales.setCodMaterial(rs.getString("COD_MATERIAL"));
                materiales.setNombreMaterial(rs.getString("NOMBRE_MATERIAL"));
                materialesList.add(new SelectItem(rs.getString(1),rs.getString(2)));
                //materialesList.add(materiales);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     public void cargarGestiones(){
        try {

            String consulta = "select cod_gestion,nombre_gestion from gestiones order by cod_gestion " ;
            System.out.println("consulta " + consulta);
            con = Util.openConnection(con);

            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);

            gestionesList.clear();
            //gestionesList.add(new SelectItem("0","    Todos      "));
            while(rs.next()){
                codGestion= Integer.parseInt(usuario.getGestionesGlobal().getCodGestion()) ;
                gestionesList.add(new SelectItem(rs.getString(1),rs.getString(2)));
               // gestionesList.add(materiales);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String buscarProductosOnClickK(){
        System.out.println("codOrdenCompra:"+codOrdenCompra);
        usuario.getGestionesGlobal().setCodGestion(Integer.toString(codGestion)) ;
        System.out.println("usuario.getGestionesGlobal().setCodGestion():"+usuario.getGestionesGlobal().getCodGestion());
        cargarContenidoIngresoAlmacenOrdenCompra();
        return null;
    }
    /*public String  buscarProductosOnClickK(){
        System.out.println("codOrdenCompra:"+codOrdenCompra);
        cargarContenidoIngresoAlmacenOrdenCompra();
        return null;
    }*/
    public void cargarContenidoIngresoAlmacenOrdenCompra(){
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
                    " from ordenes_compra oc where oc.cod_gestion='"+usuario.getGestionesGlobal().getCodGestion()+"' " ;
                    if(codOrdenCompra!=0) {
                        consulta +=" and oc.nro_orden_compra like '%"+codOrdenCompra+"%' " ;
                    }
               
                    if(codMaterial!=0) {
                        consulta +=" and oc.cod_orden_compra in (select od.cod_orden_compra from ordenes_compra_detalle od where od.cod_material="+codMaterial+")" ;
                    }
                    
                    consulta +=" and oc.cod_estado_compra in (4,5,6,7,13,15)  ) AS listado_ordenes_compra " ;
                    if(codOrdenCompra==0) {
                       consulta +=" where FILAS BETWEEN " + begin + " AND " + end + "  ";
                    }

                    System.out.println("consulta " + consulta);
            


            con = Util.openConnection(con);
            
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            ordenesCompraList.clear();
            cantidadfilas=0;
            while(rs.next()){
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
            cantidadfilas= ordenesCompraList.size();


        } catch (Exception e) {
            e.printStackTrace();
        }        
    }

     public String siguiente_action() {
         System.out.println("entro al action siguiente ");
        super.next();
       // this.cargarContenidoIngresoAlmacenOrdenCompra();
        return "";
    }

    public String atras_action() {
        super.back();
       // this.cargarContenidoIngresoAlmacenOrdenCompra();
        return "";
    }
    public boolean perteneceSeccionAlmacen(OrdenesCompraDetalle ordenesCompraDetalle) {
        boolean perteneceSeccion = false;
        try {
            ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            String consulta = " select distinct(s.cod_seccion) cod_seccion,s.nombre_seccion " +
                                    " from secciones s,secciones_detalle sd, almacenes a " +
                                    " where s.cod_seccion=sd.cod_seccion and s.cod_almacen='"+usuario.getAlmacenesGlobal().getCodAlmacen()+"' " +
                                    " and s.cod_estado_registro=1 and a.cod_almacen=s.cod_almacen  " +
                                    " and ((sd.cod_material='"+ordenesCompraDetalle.getMateriales().getCodMaterial()+"' and s.estado_sistema=1) " +
                                    " or (sd.cod_material=0 and sd.cod_grupo='"+ordenesCompraDetalle.getMateriales().getGrupos().getCodGrupo() +"') " +
                                    " or (sd.cod_material=0 and sd.cod_grupo=0 and sd.cod_capitulo='"+ ordenesCompraDetalle.getMateriales().getGrupos().getCapitulos().getCodCapitulo() +"')) ";
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            if(rs.next()){
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
                                    " where s.cod_seccion=sd.cod_seccion and s.cod_almacen='"+usuario.getAlmacenesGlobal().getCodAlmacen()+"' " +
                                    " and s.cod_estado_registro=1 and a.cod_almacen=s.cod_almacen  " +
                                    " and ((sd.cod_material='"+ingresosAlmacenDetalle.getMateriales().getCodMaterial()+"' and s.estado_sistema=1) " +
                                    " or (sd.cod_material=0 and sd.cod_grupo='"+ingresosAlmacenDetalle.getMateriales().getGrupos().getCodGrupo() +"') " +
                                    " or (sd.cod_material=0 and sd.cod_grupo=0 and sd.cod_capitulo='"+ ingresosAlmacenDetalle.getMateriales().getGrupos().getCapitulos().getCodCapitulo() +"')) ";
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            if(rs.next()){
                secciones.setCodSeccion(rs.getInt("cod_seccion"));
                secciones.setNombreSeccion(rs.getString("nombre_seccion"));                
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return secciones;
    }


    public void cargarDetalleIngresoAlmacen(){
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
                                    " where s.cod_seccion=sd.cod_seccion and s.cod_almacen='"+usuario.getAlmacenesGlobal().getCodAlmacen()+"' " +
                                    " and s.cod_estado_registro=1 and a.cod_almacen=s.cod_almacen  " +
                                    " and ((sd.cod_material=ocd.cod_material and s.estado_sistema=1) " +
                                    " or (sd.cod_material=0 and sd.cod_grupo=(select top 1 m.cod_grupo from materiales m where ocd.cod_material=m.cod_material)) " +
                                    " or (sd.cod_material=0 and sd.cod_grupo=0 and sd.cod_capitulo=(select top 1 g.cod_capitulo from grupos g, materiales m where ocd.cod_material=m.cod_material and m.cod_grupo=g.cod_grupo)))) cod_seccion," +
                        " (select m.PESO_ASOCIADO from materiales m where m.cod_material = ocd.cod_material) PESO_ASOCIADO," +
                        " (select m.COD_UNIDAD_MEDIDA_PESOASOCIADO from materiales m where m.cod_material = ocd.cod_material) COD_UNIDAD_MEDIDA_PESOASOCIADO " +
                        " (select m.COD_UNIDAD_MEDIDA from materiales m where m.cod_material = ocd.cod_material) COD_UNIDAD_MEDIDA " +                        
                        " from ordenes_compra_detalle ocd where cod_orden_compra= '"+ordenesCompra.getCodOrdenCompra()+"' order by nombre_material ";

            consulta = " SELECT OCD.COD_ORDEN_COMPRA,OCD.COD_MATERIAL,OCD.CANTIDAD_NETA,OCD.PRECIO_UNITARIO,OCD.PRECIO_TOTAL,OCD.CANTIDAD_INGRESO_ALMACEN, " +
                    " C.COD_CAPITULO,GR.COD_GRUPO,M.MATERIAL_ALMACEN,M.NOMBRE_MATERIAL,OCD.COD_UNIDAD_MEDIDA COD_UNIDAD_MEDIDA_OC, " +
                    " (select 1 from materiales ma where ma.COD_MATERIAL = ocd.COD_MATERIAL and ma.COD_UNIDAD_MEDIDA = ocd.COD_UNIDAD_MEDIDA) CON_EQUIVALENCIA, " +                    
                    " UM.NOMBRE_UNIDAD_MEDIDA NOMBRE_UNIDAD_MEDIDA_MATERIAL,UM.ABREVIATURA ABREVIATURA_MATERIAL, M.PESO_ASOCIADO,M.COD_UNIDAD_MEDIDA_PESOASOCIADO, M.COD_UNIDAD_MEDIDA COD_UNIDAD_MEDIDA_MATERIAL,  " +
                    " UM1.COD_UNIDAD_MEDIDA COD_UNIDAD_MEDIDA_OC,UM1.NOMBRE_UNIDAD_MEDIDA NOMBRE_UNIDAD_MEDIDA_OC,UM1.ABREVIATURA ABREVIATURA_OC " +
                    " FROM ORDENES_COMPRA_DETALLE OCD " +
                    " INNER JOIN MATERIALES M ON OCD.COD_MATERIAL = M.COD_MATERIAL " +
                    " INNER JOIN GRUPOS GR ON GR.COD_GRUPO = M.COD_GRUPO " +
                    " INNER JOIN CAPITULOS C ON C.COD_CAPITULO = GR.COD_CAPITULO  " +
                    " INNER JOIN UNIDADES_MEDIDA UM ON UM.COD_UNIDAD_MEDIDA = M.COD_UNIDAD_MEDIDA " +
                    " INNER JOIN UNIDADES_MEDIDA UM1 ON UM1.COD_UNIDAD_MEDIDA = OCD.COD_UNIDAD_MEDIDA " +
                    " WHERE OCD.COD_ORDEN_COMPRA = '"+ordenesCompra.getCodOrdenCompra()+"' ";

            System.out.println("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            ordenesCompraDetalleList.clear();
            //iterar el detalle y agregar los items detalle al detalle para el almacen
            //filtrar items admitidos
            //realizar la validacion de items que si se pueden ingresar
            int contador = 1;
            ingresosAlmacenDetalleList.clear();
             while (rs.next()){
                 IngresosAlmacenDetalle ingresosAlmacenDetalleItem = new IngresosAlmacenDetalle();
                 ingresosAlmacenDetalleItem.setIngresosAlmacen(ingresosAlmacen);
                 ingresosAlmacenDetalleItem.getMateriales().setCodMaterial(rs.getString("cod_material"));
                 ingresosAlmacenDetalleItem.getMateriales().setNombreMaterial(rs.getString("NOMBRE_MATERIAL"));                 
                 ingresosAlmacenDetalleItem.getMateriales().getGrupos().setCodGrupo(rs.getInt("COD_GRUPO"));
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
                 ingresosAlmacenDetalleItem.setCantEquivalente(ingresosAlmacenDetalleItem.getValorEquivalencia()>0?(ingresosAlmacenDetalleItem.getCantTotalIngreso()/ingresosAlmacenDetalleItem.getValorEquivalencia()):0);
                 

                 //si tiene unidad medida peso asociado
                 if(ingresosAlmacenDetalleItem.getMateriales().getPesoAsociado()>0 &&
                    ingresosAlmacenDetalleItem.getMateriales().getUnidadesMedidaPesoAsociado().getCodUnidadMedida()>0){
                    ingresosAlmacenDetalleItem.setValorEquivalencia(ingresosAlmacenDetalleItem.getMateriales().getPesoAsociado());
                    consulta = " select u.COD_UNIDAD_MEDIDA,u.NOMBRE_UNIDAD_MEDIDA,u.ABREVIATURA " +
                            " from UNIDADES_MEDIDA u " +
                            " where u.COD_UNIDAD_MEDIDA ='"+ingresosAlmacenDetalleItem.getMateriales().getUnidadesMedidaPesoAsociado().getCodUnidadMedida()+"' " +
                            " and u.COD_ESTADO_REGISTRO = 1 ";
                    System.out.println("consulta " + consulta);
                    Statement st1 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    ResultSet rs1 = st1.executeQuery(consulta);
                    if(rs1.next()){
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
                 
                 System.out.println("datos : " + rs.getFloat("cantidad_ingreso_almacen") + " " + rs.getFloat("cantidad_neta") + " "
                         + rs.getInt("CON_EQUIVALENCIA") + " " + ingresosAlmacenDetalleItem.getMateriales().getMaterialAlmacen() );
                 if(ingresosAlmacenDetalleItem.getSecciones().getCodSeccion()>0){
                     if(((rs.getFloat("cantidad_ingreso_almacen")>0 && rs.getFloat("cantidad_ingreso_almacen")<rs.getFloat("cantidad_neta"))
                        || rs.getFloat("cantidad_ingreso_almacen")==0)
                        && ingresosAlmacenDetalleItem.getMateriales().getMaterialAlmacen()==1){
                         ingresosAlmacenDetalleList.add(ingresosAlmacenDetalleItem);
                     }
                 }

                 
                 
                 
            }
        } catch (Exception e) {
            e.printStackTrace();
        }        
    }
    
    public Secciones buscaSeccion(IngresosAlmacenDetalle ingresosAlmacenDetalle){
        Secciones secciones = new Secciones();
        try {
            ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            String consulta = " select s.cod_seccion,s.nombre_seccion  " +
                                    " from secciones s,secciones_detalle sd, almacenes a " +
                                    " where s.cod_seccion=sd.cod_seccion and s.cod_almacen='"+usuario.getAlmacenesGlobal().getCodAlmacen()+"' " +
                                    " and s.cod_estado_registro=1 and a.cod_almacen=s.cod_almacen  " +
                                    " and ((sd.cod_material="+ ingresosAlmacenDetalle.getMateriales().getCodMaterial() +" and s.estado_sistema=1) " +
                                    " or (sd.cod_material=0 and sd.cod_grupo= '"+ingresosAlmacenDetalle.getMateriales().getGrupos().getCodGrupo()+"') " +
                                    " or (sd.cod_material=0 and sd.cod_grupo=0 and sd.cod_capitulo='"+ingresosAlmacenDetalle.getMateriales().getGrupos().getCapitulos().getCodCapitulo()+"')) " ;
            
            System.out.println("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            if(rs.next()){
                secciones.setCodSeccion(rs.getInt("cod_seccion"));
                secciones.setNombreSeccion(rs.getString("nombre_seccion"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return secciones;
    }


    public float obtieneEquivalencia(IngresosAlmacenDetalle ingresosAlmacenDetalle){
        float equivalencia = 0;
        String consulta = "";
        try {
            if(ingresosAlmacenDetalle.getMateriales().getPesoAsociado()>0 &&
                    ingresosAlmacenDetalle.getMateriales().getUnidadesMedidaPesoAsociado().getCodUnidadMedida()>0 ){

                //por revisar
                consulta = " select e.valor_equivalencia " +
                    " from equivalencias e " +
                    " where ((e.cod_unidad_medida='"+ ingresosAlmacenDetalle.getMateriales().getUnidadesMedida().getCodUnidadMedida()+"' " +
                    " and e.cod_unidad_medida2='"+ingresosAlmacenDetalle.getMateriales().getUnidadesMedidaPesoAsociado().getCodUnidadMedida() +"') or " +
                    " (e.cod_unidad_medida='"+ ingresosAlmacenDetalle.getMateriales().getUnidadesMedidaPesoAsociado().getCodUnidadMedida()  +"' " +
                    " and e.cod_unidad_medida2='"+ingresosAlmacenDetalle.getMateriales().getUnidadesMedida().getCodUnidadMedida()+"')) " +
                    " and e.cod_estado_registro=1 " ;
                System.out.println("consulta " + consulta);
                Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs = st.executeQuery(consulta);
                if(rs.next()){
                    equivalencia=rs.getFloat("valor_equivalencia");
                }else{
                    
                }
            }else{
                consulta = " select e.valor_equivalencia " +
                    " from equivalencias e " +
                    " where ((e.cod_unidad_medida='"+ ingresosAlmacenDetalle.getOrdenesCompraDetalle().getMateriales().getUnidadesMedida().getCodUnidadMedida()+"' " +
                    " and e.cod_unidad_medida2='"+ingresosAlmacenDetalle.getOrdenesCompraDetalle().getUnidadesMedida().getCodUnidadMedida()+"') or " +
                    " (e.cod_unidad_medida='"+ ingresosAlmacenDetalle.getOrdenesCompraDetalle().getUnidadesMedida().getCodUnidadMedida() +"' " +
                    " and e.cod_unidad_medida2='"+ingresosAlmacenDetalle.getOrdenesCompraDetalle().getMateriales().getUnidadesMedida().getCodUnidadMedida()+"')) " +
                    " and e.cod_estado_registro=1 " ;
                System.out.println("consulta " + consulta);

                Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs = st.executeQuery(consulta);

                if(rs.next()){
                    equivalencia=rs.getFloat("valor_equivalencia");
                }else{
                }
            }
            if(ingresosAlmacenDetalle.getMateriales().getUnidadesMedida().getCodUnidadMedida() == ingresosAlmacenDetalle.getMateriales().getUnidadesMedidaPesoAsociado().getCodUnidadMedida() ||
                    ingresosAlmacenDetalle.getOrdenesCompraDetalle().getMateriales().getUnidadesMedida().getCodUnidadMedida()==ingresosAlmacenDetalle.getOrdenesCompraDetalle().getUnidadesMedida().getCodUnidadMedida()){
                equivalencia = 1;
            }



        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("valor equivalencia : " + equivalencia);
        return equivalencia;
    }

    public String generarIngresoAlmacen_action(){
        try {
            mensaje = "";
            
              Iterator i = ordenesCompraList.iterator();
              while(i.hasNext()){
                OrdenesCompra ordenesCompraItem = (OrdenesCompra)i.next();
                if(ordenesCompraItem.getChecked().booleanValue()==true){
                    ordenesCompra = ordenesCompraItem;
                }
            }
            ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            if(usuario.getAlmacenesGlobal().getCodAlmacen()!=ordenesCompra.getAlmacenEntrega().getCodAlmacen()){
                mensaje = "el almacen no corresponde al almacen actual";
                return null;
            }
            // verificar si los items ya se ingresaron en su totalidad
            this.verificarItemsIngresadosAlmacen(ordenesCompra, usuario);
            if(!mensaje.equals("")){                
                return null;
            }




            //aqui el resto de las validaciones

            
              

          // Utiles.direccionar("agregarIngresosAlmacenOrdenCompra.jsf");
           
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void verificarItemsIngresadosAlmacen(OrdenesCompra ordenesCompra,ManagedAccesoSistema usuario){
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
                    " where s.cod_seccion = sd.cod_seccion and  s.cod_almacen = '"+usuario.getAlmacenesGlobal().getCodAlmacen()+"' and  s.cod_estado_registro = 1 and " +
                    " a.cod_almacen = s.cod_almacen and ((sd.cod_material = m.COD_MATERIAL and  s.estado_sistema = 1) or " +
                    " (sd.cod_material = 0 and sd.cod_grupo = gr.COD_GRUPO) or (sd.cod_material = 0 and sd.cod_grupo = 0 and " +
                    " sd.cod_capitulo = c.COD_CAPITULO)) ) cod_seccion  " +
                    " FROM ORDENES_COMPRA_DETALLE OCD  INNER JOIN MATERIALES M ON OCD.COD_MATERIAL = M.COD_MATERIAL   " +
                    " INNER JOIN GRUPOS GR ON GR.COD_GRUPO = M.COD_GRUPO  INNER JOIN CAPITULOS C ON C.COD_CAPITULO = GR.COD_CAPITULO    " +
                    " INNER JOIN UNIDADES_MEDIDA UM ON UM.COD_UNIDAD_MEDIDA = M.COD_UNIDAD_MEDIDA   " +
                    " INNER JOIN UNIDADES_MEDIDA UM1 ON UM1.COD_UNIDAD_MEDIDA = OCD.COD_UNIDAD_MEDIDA   " +
                    " WHERE OCD.COD_ORDEN_COMPRA = '"+ordenesCompra.getCodOrdenCompra()+"'  ";
            System.out.println("consulta" + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            while(rs.next()){
                  if(rs.getInt("cod_seccion") >0){
                     if(((rs.getFloat("cantidad_ingreso_almacen")>0 && rs.getFloat("cantidad_ingreso_almacen")<rs.getFloat("cantidad_neta"))
                        || rs.getFloat("cantidad_ingreso_almacen")==0)
                        && rs.getInt("MATERIAL_ALMACEN")==1){
                      itemsIngresadosAlmacen = 0;
                     }
                 }else{
                      seccion = 0;
                 }
            }
            if(seccion==0){
                mensaje = " existen items que no pertenecen a la seccion ";
            }else if(itemsIngresadosAlmacen == 1){
                mensaje = " los items fueron ingresados al almacen ";
            }

            
            
        } catch (Exception e) {
            e.printStackTrace();
        }        
    }
    
    public int deduceCodIngresoAlmacen(){
        int codIngresoAlmacen = 0;
        try {
            String consulta= " select isnull(max(cod_ingreso_almacen),0)+1 cod_ingreso_almacen from ingresos_almacen  ";
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            if(rs.next()){
                codIngresoAlmacen = rs.getInt("cod_ingreso_almacen");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return codIngresoAlmacen;
    }


    public String getCargarAgregarIngresoAlmacenOrdenCompra(){
        try {
            ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            ingresosAlmacen.setGestiones(usuario.getGestionesGlobal());
            ingresosAlmacen.setOrdenesCompra(ordenesCompra);
            
            ingresosAlmacen.getTiposIngresoAlmacen().setCodTipoIngresoAlmacen(1); //ingreso a almacen con orden de compra
            ingresosAlmacen.getEstadosIngresoAlmacen().setCodEstadoIngresoAlmacen(1);
            ingresosAlmacen.setNroIngresoAlmacen(this.generaNroIngresoAlmacen());

            this.cargarEstadosIngresoAlmacen();
            this.cargarTiposIngresoAlmacen();
            this.cargarDetalleIngresoAlmacen();
            this.cargarEmpaques();
            
            

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
     public String detallarItem_action(){
         try {             
             ingresosAlmacenDetalle = (IngresosAlmacenDetalle)ingresosAlmacenDetalleDataTable.getRowData();
             //aqui las unidades de medida
             ingresosAlmacenDetalleEstado = new IngresosAlmacenDetalleEstado();
             

             
         } catch (Exception e) {
             e.printStackTrace();
         }
         return null;
     }
     public String guardarDetalleItem_action(){
         try {
             mensaje = "";
             IngresosAlmacenDetalle  ingresosAlmacenDetalleItem = ingresosAlmacenDetalle;
             ingresosAlmacenDetalleItem.setCantTotalIngresoFisico(ingresosAlmacenDetalle.getCantTotalIngresoFisico());
             if(this.validaDatosIngresoAcondicionamiento(ingresosAlmacenDetalleItem)==true){
                 Iterator i= ingresosAlmacenDetalle.getIngresosAlmacenDetalleEstadoList().iterator();
                 ingresosAlmacenDetalleItem.setCantTotalIngresoFisico(0);
                 while(i.hasNext()){
                     IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstadoItem = (IngresosAlmacenDetalleEstado)i.next();
                     ingresosAlmacenDetalleItem.setCantTotalIngresoFisico(ingresosAlmacenDetalleItem.getCantTotalIngresoFisico()+ingresosAlmacenDetalleEstadoItem.getCantidadParcial());
                 }
             }
             //aqui las unidades de medida
         } catch (Exception e) {
             e.printStackTrace();
         }
         return null;
     }
     public boolean validaDatosIngresoAcondicionamiento(IngresosAlmacenDetalle ingresosAlmacenDetalleItem){
         boolean datosValidos  = true;
          Iterator i= ingresosAlmacenDetalle.getIngresosAlmacenDetalleEstadoList().iterator();             
             while(i.hasNext()){
                 IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstadoItem = (IngresosAlmacenDetalleEstado)i.next();
                 if(ingresosAlmacenDetalleEstadoItem.getFechaManufactura().compareTo(ingresosAlmacenDetalleEstadoItem.getFechaReanalisis())>=0 ||
                         ingresosAlmacenDetalleEstadoItem.getFechaReanalisis().compareTo(ingresosAlmacenDetalleEstadoItem.getFechaVencimiento() )>=0){
                     mensaje = "La fecha de Manufactura debe ser menor a la fecha de Reanalisis y la fecha de Reanalisis debe ser menor a la fecha de Vencimiento ";
                     datosValidos=false;
                     break;
                 }
             }
             return datosValidos;
     }
     




     public String generarEtiquetas_action(){
         try {
             ingresosAlmacenDetalle.getIngresosAlmacenDetalleEstadoList().clear();
             System.out.println("ingresosAlmacenDetalle.getNroUnidadesEmpaque()" + ingresosAlmacenDetalle.getNroUnidadesEmpaque());
             String nombreEmpaqueSecundario = this.buscaEmpaqueSecundarioExterno(ingresosAlmacenDetalleEstado.getEmpaqueSecundarioExterno().getCodEmpaqueSecundarioExterno());
             for(int i=1;i<=ingresosAlmacenDetalle.getNroUnidadesEmpaque();i++){
                 IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstadoItem = new IngresosAlmacenDetalleEstado();
                 ingresosAlmacenDetalleEstadoItem.setEtiqueta(i);
                 ingresosAlmacenDetalleEstadoItem.getEstadosMaterial().setCodEstadoMaterial(1); //estado cuatentena
                 ingresosAlmacenDetalleEstadoItem.getEstadosMaterial().setNombreEstadoMaterial("CUARENTENA");
                 ingresosAlmacenDetalleEstadoItem.getEmpaqueSecundarioExterno().setCodEmpaqueSecundarioExterno(ingresosAlmacenDetalleEstado.getEmpaqueSecundarioExterno().getCodEmpaqueSecundarioExterno());
                 ingresosAlmacenDetalleEstadoItem.getEmpaqueSecundarioExterno().setNombreEmpaqueSecundarioExterno(nombreEmpaqueSecundario);
                 ingresosAlmacenDetalleEstadoItem.setCantidadParcial(0);
                 ingresosAlmacenDetalleEstadoItem.setFechaManufactura(new  Date());
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
     public String repetirValoresIngresoAlmacenDetalleEstado_action(){
         try {
             Iterator i = ingresosAlmacenDetalle.getIngresosAlmacenDetalleEstadoList().iterator();
             String nombreEmpaqueSecundario = this.buscaEmpaqueSecundarioExterno(ingresosAlmacenDetalleEstado.getEmpaqueSecundarioExterno().getCodEmpaqueSecundarioExterno());
             ingresosAlmacenDetalle.setCantTotalIngresoFisico(0);
             while(i.hasNext()){
                 IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstadoItem = (IngresosAlmacenDetalleEstado)i.next();
                 ingresosAlmacenDetalleEstadoItem.getEmpaqueSecundarioExterno().setCodEmpaqueSecundarioExterno(ingresosAlmacenDetalleEstado.getEmpaqueSecundarioExterno().getCodEmpaqueSecundarioExterno());
                 ingresosAlmacenDetalleEstadoItem.getEmpaqueSecundarioExterno().setNombreEmpaqueSecundarioExterno(nombreEmpaqueSecundario);
                 ingresosAlmacenDetalleEstadoItem.setCantidadParcial(ingresosAlmacenDetalleEstado.getCantidadParcial());
                 ingresosAlmacenDetalleEstadoItem.setLoteMaterialProveedor(ingresosAlmacenDetalleEstado.getLoteMaterialProveedor());
                 ingresosAlmacenDetalleEstadoItem.setFechaManufactura(ingresosAlmacenDetalleEstado.getFechaManufactura());
                 ingresosAlmacenDetalleEstadoItem.setFechaVencimiento(ingresosAlmacenDetalleEstado.getFechaVencimiento());
                 ingresosAlmacenDetalleEstadoItem.setFechaReanalisis(ingresosAlmacenDetalleEstado.getFechaReanalisis());
                 ingresosAlmacenDetalle.setCantTotalIngresoFisico(ingresosAlmacenDetalle.getCantTotalIngresoFisico()+ingresosAlmacenDetalleEstadoItem.getCantidadParcial());
             }
         } catch (Exception e) {
             e.printStackTrace();
         }
         return null;
     }
     public String buscaEmpaqueSecundarioExterno(int codEmpaqueSecundario){
         String nombreEmpaqueSecundario = "";
         try {
             Iterator i = empaqueSecundarioExternoList.iterator();
             while(i.hasNext()){
                 SelectItem selectItem = (SelectItem)i.next();
                 if(Integer.valueOf(selectItem.getValue().toString())==codEmpaqueSecundario){
                    nombreEmpaqueSecundario = selectItem.getLabel().toString();
                     break;
                 }
             }
             
         } catch (Exception e) {
             e.printStackTrace();
         }
         return nombreEmpaqueSecundario;
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
     public boolean verificaTransaccionCerradaAlmacen(){
         boolean transaccionCerradaAlmacen = false;
         Date fechaActual = new Date();
         SimpleDateFormat sdf = new SimpleDateFormat("MM");
         try {
             ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");             
             String consulta = " select * from estados_transacciones_almacen " +
                     " where cod_gestion='"+usuario.getGestionesGlobal().getCodGestion()+"' " +
                     " and cod_mes = '"+Integer.valueOf(sdf.format(fechaActual))+"'  " +
                     " and estado=1 ";
             Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             ResultSet rs = st.executeQuery(consulta);
             if(rs.next()){
                 transaccionCerradaAlmacen= true;
             }

         } catch (Exception e) {
             e.printStackTrace();
         }
         return transaccionCerradaAlmacen ;

     }
     public int generaCodIngresoAlmacen(){
         int codIngresoAlmacen = 0;
         ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
         try {
                 String consulta ="  select (isnull(max(cod_ingreso_almacen),0)+1) cod_ingreso_almacen  " +
                         " from ingresos_almacen  ";
              System.out.println("consulta " + consulta);
             Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             ResultSet rs = st.executeQuery(consulta);
             if(rs.next()){
                 codIngresoAlmacen = rs.getInt("cod_ingreso_almacen");
             }

         } catch (Exception e) {
             e.printStackTrace();
         }
         return codIngresoAlmacen;
     }
     public int generaNroIngresoAlmacen(){
         int codIngresoAlmacen = 0;
         ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
         try {
                 String consulta =" select (isnull(max(nro_ingreso_almacen),0)+1) nro_ingreso_almacen  from ingresos_almacen where cod_gestion='"+usuario.getGestionesGlobal().getCodGestion()+"' " +
                     " and estado_sistema=1 and cod_almacen='"+usuario.getAlmacenesGlobal().getCodAlmacen()+"' ";
              System.out.println("consulta " + consulta);
             Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             ResultSet rs = st.executeQuery(consulta);
             if(rs.next()){
                 codIngresoAlmacen = rs.getInt("nro_ingreso_almacen");
             }
             st.close();
             rs.close();

         } catch (Exception e) {
             e.printStackTrace();
         }
         return codIngresoAlmacen;
     }
     public boolean existeDetalleIngresoDetalleAlmacen(List ingresosAlmacenDetalleList){
        boolean existeDetalle = true;
        boolean checkSeleccionado = false;
        try {
            Iterator i = ingresosAlmacenDetalleList.iterator();
            while(i.hasNext()){
                IngresosAlmacenDetalle ingresosAlmacenDetalleItem = (IngresosAlmacenDetalle)i.next();
                if(ingresosAlmacenDetalleItem.getChecked()==true && ingresosAlmacenDetalleItem.getIngresosAlmacenDetalleEstadoList().size()==0){
                    existeDetalle = false;
                    mensaje = " No existe detalle de los items seleccionados ";
                    break;
                }
            }
            i = ingresosAlmacenDetalleList.iterator();
            while(i.hasNext()){
                IngresosAlmacenDetalle ingresosAlmacenDetalleItem = (IngresosAlmacenDetalle)i.next();
                if(ingresosAlmacenDetalleItem.getChecked()==true ){
                    checkSeleccionado = true;
                    break;
                }
            }
            if(checkSeleccionado==false){mensaje="No se selecciono ningun item en el detalle";}
        } catch (Exception e) {
            e.printStackTrace();
        }
        return existeDetalle&&checkSeleccionado;
    }

     public String guardarIngresoAlmacen_action(){
         try {
             ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
             SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
             if(this.verificaTransaccionCerradaAlmacen()==true){
                 mensaje = "Las transacciones para este mes fueron cerradas ";
                 return null;
             }
             if(existeDetalleIngresoDetalleAlmacen(ingresosAlmacenDetalleList)==false){
                 
                 return null;
             }
             

             ingresosAlmacen.setCodIngresoAlmacen(this.generaCodIngresoAlmacen());
             ingresosAlmacen.getTiposIngresoAlmacen().setCodTipoIngresoAlmacen(1);//con orden de compra
             ingresosAlmacen.getEstadosIngresoAlmacen().setCodEstadoIngresoAlmacen(1);
             ingresosAlmacen.setFechaIngresoAlmacen(new Date());
             ingresosAlmacen.setCreditoFiscalSiNo(ordenesCompra.getCreditoFiscalSiNo());

             
             ingresosAlmacen.getTiposDocumentos().setCodTipoDocumento(ingresosAlmacen.getCreditoFiscalSiNo()==1?"1":"2");
             ingresosAlmacen.getProveedores().setCodProveedor(ordenesCompra.getProveedores().getCodProveedor());
             ingresosAlmacen.setTiposCompra(ordenesCompra.getTiposCompra());
             ingresosAlmacen.getPersonal().setCodPersonal(usuario.getUsuarioModuloBean().getCodUsuarioGlobal());
             ingresosAlmacen.getEstadosIngresosLiquidacion().setCodEstadoIngresoLiquidacion(2); //no liquidado
             
             

             //credito fiscal si no
             //proveedor
             //tipo compra
             ingresosAlmacen.setEstadoSistema(1);
             
             String consulta = " insert into ingresos_almacen (cod_ingreso_almacen,nro_ingreso_almacen,cod_tipo_ingreso_almacen, " +
                     " cod_orden_compra,cod_gestion,cod_estado_ingreso_almacen, " +
                     " credito_fiscal_si_no,cod_proveedor, " +
                     " cod_tipo_compra,estado_sistema,cod_almacen,cod_devolucion,fecha_ingreso_almacen, " +
                     " cod_tipo_documento,cod_personal,cod_estado_ingreso_liquidacion )  " +
                     " values  ('"+ingresosAlmacen.getCodIngresoAlmacen()+"'," +
                     " '"+ingresosAlmacen.getNroIngresoAlmacen()+"', " +
                     " '"+ingresosAlmacen.getTiposIngresoAlmacen().getCodTipoIngresoAlmacen()+"'," +
                     " '"+ingresosAlmacen.getOrdenesCompra().getCodOrdenCompra() +"'," +
                     " '"+usuario.getGestionesGlobal().getCodGestion()+"','"+ingresosAlmacen.getEstadosIngresoAlmacen().getCodEstadoIngresoAlmacen()+"'," +
                     " '"+ingresosAlmacen.getCreditoFiscalSiNo()+"', " +
                     " '"+ingresosAlmacen.getProveedores().getCodProveedor() +"', " +
                     " '"+ingresosAlmacen.getTiposCompra().getCodTipoCompra()+"'," +
                     " '"+ingresosAlmacen.getEstadoSistema() +"', " +
                     " '"+usuario.getAlmacenesGlobal().getCodAlmacen() +"',0 ," +
                     " '"+sdf.format(ingresosAlmacen.getFechaIngresoAlmacen())+"'," +
                     " '"+ingresosAlmacen.getTiposDocumentos().getCodTipoDocumento()+"'," +
                     " '"+ingresosAlmacen.getPersonal().getCodPersonal()+"', " +
                     " '"+ingresosAlmacen.getEstadosIngresosLiquidacion().getCodEstadoIngresoLiquidacion()+"')  ";
               System.out.println("consulta " + consulta);
               Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
               st.executeUpdate(consulta);

               Iterator i = ingresosAlmacenDetalleList.iterator();
               while(i.hasNext()){
                   IngresosAlmacenDetalle ingresosAlmacenDetalleItem = (IngresosAlmacenDetalle)i.next();
                   if(ingresosAlmacenDetalleItem.getChecked()==true){
                   consulta = " insert into ingresos_almacen_detalle (cod_material,cod_ingreso_almacen,cod_seccion,nro_unidades_empaque, " +
                           " cant_total_ingreso,cant_total_ingreso_fisico,cod_unidad_medida,precio_total_material, " +
                           " precio_unitario_material,costo_unitario,observacion,costo_promedio,precio_neto)" +
                           " values('"+ingresosAlmacenDetalleItem.getMateriales().getCodMaterial() +"'," +
                           " '"+ingresosAlmacen.getCodIngresoAlmacen()+"'," +
                           " '"+ingresosAlmacenDetalleItem.getSecciones().getCodSeccion()+"'," +
                           " '"+ingresosAlmacenDetalleItem.getNroUnidadesEmpaque()+"' ," +
                           " '"+ingresosAlmacenDetalleItem.getCantTotalIngreso()+"'," +
                           " '"+ingresosAlmacenDetalleItem.getCantTotalIngresoFisico() +"'," +
                           " '"+ingresosAlmacenDetalleItem.getUnidadesMedida().getCodUnidadMedida()+"'," +
                           " '"+ingresosAlmacenDetalleItem.getPrecioTotalMaterial()+"'," +
                           " '"+ingresosAlmacenDetalleItem.getPrecioUnitarioMaterial()+"'," +
                           " '"+ingresosAlmacenDetalleItem.getCostoUnitario()+"'," +
                           " '"+ingresosAlmacenDetalleItem.getObservacion()+"'," +
                           " '"+ingresosAlmacenDetalleItem.getCostoPromedio()+"'," +
                           " '"+ingresosAlmacenDetalleItem.getPrecioNeto()+"' ) ";
                   System.out.println("consulta " + consulta);
                   st.executeUpdate(consulta);
                   Iterator iterator = ingresosAlmacenDetalleItem.getIngresosAlmacenDetalleEstadoList().iterator();
                   while(iterator.hasNext()){
                       IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstadoItem = (IngresosAlmacenDetalleEstado)iterator.next();
                       ingresosAlmacenDetalleEstadoItem.setCantidadRestante(ingresosAlmacenDetalleEstadoItem.getCantidadParcial());
                       
                       consulta = " insert into ingresos_almacen_detalle_estado " +
                           " (etiqueta,cod_ingreso_almacen,cod_material,cod_estado_material, " +
                           " cod_empaque_secundario_externo,cantidad_parcial,cantidad_restante, " +
                           " fecha_vencimiento,lote_material_proveedor,lote_interno,fecha_manufactura, " +
                           " fecha_reanalisis,observaciones,obs_control_calidad)values(" +
                           " '"+ingresosAlmacenDetalleEstadoItem.getEtiqueta()+"', " +
                           " '"+ingresosAlmacen.getCodIngresoAlmacen()+"'," +
                           " '"+ingresosAlmacenDetalleItem.getMateriales().getCodMaterial()+"'," +
                           " '"+ingresosAlmacenDetalleEstadoItem.getEstadosMaterial().getCodEstadoMaterial() +"'," +
                           " '"+ingresosAlmacenDetalleEstadoItem.getEmpaqueSecundarioExterno().getCodEmpaqueSecundarioExterno()+"'," +
                           " '"+ingresosAlmacenDetalleEstadoItem.getCantidadParcial()+"'," +
                           " '"+ingresosAlmacenDetalleEstadoItem.getCantidadRestante()+"'," +
                           " '"+sdf.format(ingresosAlmacenDetalleEstadoItem.getFechaVencimiento())+"'," +
                           " '"+ingresosAlmacenDetalleEstadoItem.getLoteMaterialProveedor()+"'," +
                           " '"+ingresosAlmacenDetalleEstadoItem.getLoteInterno()+"'," +
                           " '"+sdf.format(ingresosAlmacenDetalleEstadoItem.getFechaManufactura())+"'," +
                           " '"+sdf.format(ingresosAlmacenDetalleEstadoItem.getFechaReanalisis()) +"'," +
                           " '"+ingresosAlmacenDetalleEstadoItem.getObservaciones()+"'," +
                           " '"+ingresosAlmacenDetalleEstadoItem.getObsControlCalidad()+"' )  ";
                          this.actualizaOrdenCompraDetalle(ingresosAlmacenDetalleItem ,ingresosAlmacenDetalleEstadoItem);
                          

                       //,  " +  " fecha_vencimiento1,fecha_reanalisis1,fecha_vencimiento2,fecha_reanalisis2
                       //
                       /*
                        ," +
                           " '"+sdf.format(ingresosAlmacenDetalleEstadoItem.getFechaVencimiento1())+"'," +
                           " '"+sdf.format(ingresosAlmacenDetalleEstadoItem.getFechaReanalisis1()) +"'," +
                           " '"+sdf.format(ingresosAlmacenDetalleEstadoItem.getFechaVencimiento2())+"'," +
                           " '"+sdf.format(ingresosAlmacenDetalleEstadoItem.getFechaReanalisis2())+"'
                        */

                       System.out.println("consulta " + consulta);
                       st.executeUpdate(consulta);                       
                       
                   }
                 }
               }

         } catch (Exception e) {
             e.printStackTrace();
         }
         return null;
     }
     public void actualizaOrdenCompraDetalle(IngresosAlmacenDetalle ingresosAlmacenDetalleItem,IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstadoItem){
         try {
             String consulta = " update ordenes_compra_detalle set " +
                     " cantidad_ingreso_almacen = (cantidad_ingreso_almacen + '"+ingresosAlmacenDetalleEstadoItem.getCantidadParcial()+"') " +
                     " where cod_orden_compra='"+ordenesCompra.getCodOrdenCompra()+"' " +
                     " and cod_material='"+ingresosAlmacenDetalleItem.getMateriales().getCodMaterial()+"'" +
                     "  ";
             System.out.println("consulta " +consulta);
             Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             st.executeUpdate(consulta);

             consulta = " SELECT SUM(ocd.CANTIDAD_INGRESO_ALMACEN) CANTIDAD_INGRESO_ALMACEN,SUM(ocd.CANTIDAD_NETA) CANTIDAD_NETA " +
                     " FROM ORDENES_COMPRA_DETALLE OCD  WHERE OCD.COD_ORDEN_COMPRA = '"+ordenesCompra.getCodOrdenCompra()+"'  ";
             System.out.println("consulta " + consulta);
             Statement st1 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             ResultSet rs = st1.executeQuery(consulta);
             if(rs.next()){
                 if(rs.getFloat("CANTIDAD_INGRESO_ALMACEN") > 0 && rs.getFloat("CANTIDAD_INGRESO_ALMACEN")<rs.getFloat("CANTIDAD_NETA")){
                     //INGRESADO PARCIALMENTE
                     consulta = " UPDATE ORDENES_COMPRA  SET COD_ESTADO_COMPRA = '6' ";
                 }else if(rs.getFloat("CANTIDAD_INGRESO_ALMACEN") > 0 && rs.getFloat("CANTIDAD_INGRESO_ALMACEN")>=rs.getFloat("CANTIDAD_NETA")){
                     consulta = " UPDATE ORDENES_COMPRA  SET COD_ESTADO_COMPRA = '7' ";
                 }
                 System.out.println("consulta " + consulta);
                 st.executeUpdate(consulta);                 
             }
             st.close();
             st1.close();
             rs.close();
         } catch (Exception e) {
             e.printStackTrace();
         }
     }

    /**
     * @return the codOrdenCompra
     */
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



}
