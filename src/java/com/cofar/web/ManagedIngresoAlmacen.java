/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.web;

import com.cofar.bean.Almacenes;
import com.cofar.bean.Equivalencias;
import com.cofar.bean.EstadosMaterial;
import com.cofar.bean.IngresosAlmacen;
import com.cofar.bean.IngresosAlmacenDetalle;
import com.cofar.bean.IngresosAlmacenDetalleEstado;
import com.cofar.bean.Materiales;
import com.cofar.bean.IngresoTransaccionSolicitud;
import com.cofar.bean.SalidasAlmacen;import com.cofar.bean.Secciones;
import com.cofar.service.AssignCostService;import com.cofar.bean.TiposIngresoAlmacen;import com.cofar.dao.DaoGestiones;
import com.cofar.dao.DaoIngresosAlmacenDetalle;
import com.cofar.dao.DaoIngresosAlmacenDetalleEstado;
import com.cofar.dao.DaoMeses;
import com.cofar.service.AssignCostServiceMultiple;
import com.cofar.service.IngresoAlmacenService;
import com.cofar.service.ProveedoresService;
import com.cofar.service.TiposCompraService;
import com.cofar.service.TiposIngresoAlmacenService;
import com.cofar.service.impl.IngresosAlmacenImpl;
import com.cofar.service.impl.ProveedoresImpl;
import com.cofar.service.impl.TiposCompraImpl;
import com.cofar.service.impl.TiposIngresoAlmacenImpl;
import com.cofar.thread.ThreadCosteoByCodIngreso;
import com.cofar.thread.ThreadCosteoByOrdenCompra;
import com.cofar.util.Util;
import com.cofar.util.correos.EnvioCorreoNotificacionSolicitudIngresoAlmacen;
import com.cofar.util.correos.EnvioCorreoNotificacionValidacionIngresoAlmacen;
import java.sql.CallableStatement;
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
import java.util.Map;
import javax.faces.context.ExternalContext;
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

public class ManagedIngresoAlmacen extends ManagedBean {
    private static final int CANTIDAD_MESES_MINIMO_VENCIMIENTO = 3;
    private Logger LOGGER;
    private List<SelectItem> mesesSelectList = new ArrayList<>();
    IngresosAlmacenImpl ingresosAlmacenImpl = new IngresoAlmacenService();
    TiposIngresoAlmacenImpl tiposIngresoAlmacenImpl = new TiposIngresoAlmacenService();
    TiposCompraImpl tiposCompraImpl = new TiposCompraService();
    ProveedoresImpl proveedoresImpl = new ProveedoresService();

    List<IngresosAlmacen> ingresosAlmacenList = new ArrayList<IngresosAlmacen>();
    List tiposIngresosAlmacenList = new ArrayList();
    List tiposCompraList = new ArrayList();
    List proveedoresList = new ArrayList();
    private List<IngresosAlmacen> ingresosAlmacenValidarList = new ArrayList<IngresosAlmacen>();
    private List<SalidasAlmacen> salidasAsociadasAlmacenList = new ArrayList<SalidasAlmacen>();
    private boolean tipoSalidaAutomatico = false;
    private boolean salidaTieneDevolucion = false;
    private SalidasAlmacen salidaAsociadaGestionar = new SalidasAlmacen();
    ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
    IngresosAlmacen ingresosAlmacen = new IngresosAlmacen();
    List capitulosList = new ArrayList();
    List gruposList = new ArrayList();
    Materiales buscarMaterial = new  Materiales();
    List materialesBuscarList = new ArrayList();
    HtmlDataTable materialesBuscarDataTable = new HtmlDataTable();
    List ingresosAlmacenDetalleList = new ArrayList();
    HtmlDataTable ingresosAlmacenDetalleDataTable = new HtmlDataTable();
    HtmlDataTable ingresosAlmacenDetalleEditarDataTable = new HtmlDataTable();
    IngresosAlmacenDetalle ingresosAlmacenDetalle = new IngresosAlmacenDetalle();
    IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstado = new IngresosAlmacenDetalleEstado();
    List empaqueSecundarioExternoList = new ArrayList();
    String mensaje = "";
    //inicio alejandro
    private IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstadoSeleccionado;
    private List<SelectItem> materialesList= new ArrayList<SelectItem>();
    private List<SelectItem> gestionesList= new ArrayList<SelectItem>();
    private List<SelectItem> tiposIngresoAlmacenList2= new ArrayList<SelectItem>();
    private List<SelectItem> estadosIngresoAlmacenList= new ArrayList<SelectItem>();
    private List<SelectItem> proveedoresList2= new ArrayList<SelectItem>();
    private List<SelectItem> tiposCompraList2= new ArrayList<SelectItem>();
    private List<SelectItem> capitulosList2=new ArrayList<SelectItem>();
    private List<SelectItem> gruposList2=new ArrayList<SelectItem>();

    private String codMaterial="0";
    private String codGestion="0";
    private String codTipoIngreso="0";
    private String codEstadoIngreso="0";
    private String codProveedor="0";
    private String codTipoCompra="0";
    private String codCapitulo="0";
    private String codGrupo="0";
    private String nroIngresoAlmacen="";
    private String nroOrdenCompra="";
    private boolean fechas=false;
    private java.util.Date fechaInicio=new Date();
    private java.util.Date fechaFinal= new Date();
    List<SelectItem> ambienteAlmacenList = new ArrayList<SelectItem>();
    List<SelectItem> estanteAlmacenList = new ArrayList<SelectItem>();
    private boolean conDensidad = false;
    private boolean administradorAlmacen=false;
    private int indexEliminarDetalleIngreso = 0;
    //final alejandro
    
     // Variables Permisos
    private boolean permisoSolicitarAnular = false;
    private boolean permisoSolicitarEditar = false;
    private boolean permisoValidarAnular = false;
    private boolean permisoValidarEditar = false;   
    private boolean permisoAnularDirectamente = false;
    private boolean permisoEditarDirectamente = false;
    private boolean permisoEditarHoy = false;
    private boolean permisoAnularHoy = false;

    //SolicitudTransaccionIngreso
    private boolean ingresoEditable = false;    
    private boolean ingresoEditableValidandoConSalidas = false;    
    private IngresoTransaccionSolicitud ingresoTransaccionSolicitudGestionar = new IngresoTransaccionSolicitud();
    private IngresosAlmacen ingresoAlmacenBuscar = new IngresosAlmacen();
    private String motivoTransaccionIngreso = "";  
    
    
    /** Creates a new instance of ManagedIngresoAlmacen */
    public ManagedIngresoAlmacen() {
        LOGGER = LogManager.getLogger("Ingresos");
        cargarGestionActual();
    }
    
    public void cargarGestionActual(){
        DaoGestiones daoGestion = new DaoGestiones(LOGGER);
        codGestion = daoGestion.gestionActual().getCodGestion();
        LOGGER.info("COD_GESTION: "+codGestion);
        
    }
    
    public String getCargarIngresosAlmacen() throws SQLException{
        this.cargarPermisoPersonal();
        begin=1;
        end=numrows;
       // ingresosAlmacenImpl.listadoIngresosAlmacen(filaInicial, filaFinal);
        try {
        cargarPermisosEspecialesPersonal();
            //inicio alejandro
        tiposCompraList2=this.cargarTiposCompra2();
        capitulosList2=this.cargarCapitulos2();
        this.cargarGrupos();
        this.cargarTiposIngresoAlmacen2();
        this.cargarMateriales();
        this.cargarProveedores();
        codGestion=usuario.getGestionesGlobal().getCodGestion();
        this.cargarEstadosIngresoAlmacen();
        this.cargarGestiones();
        setFechaInicio(null);
        setFechaFinal(null);
        //final alejandro
        ingresosAlmacenList = listadoIngresosAlmacen(begin, end,usuario);
        this.cantidadfilas = ingresosAlmacenList.size();
        } catch (Exception e) {
            
            LOGGER.warn(e);
        }
        return null;
    }
    
    public String getCargarIngresosAlmacenValidar() throws SQLException{
        cargarPermisoPersonal();
        begin=1;
        end=numrows;
        try {
            ingresoAlmacenBuscar.getIngresoTransaccionSolicitud().setTipoRevision(2);
            cargarPermisosEspecialesPersonal();
            cargarIngresosAlmacenValidarList_action();
        
        
        } catch (Exception e) {
            LOGGER.warn(e);
        }
        return null;
    }

    public String atras_action() {
        super.back();
        ingresosAlmacenList = listadoIngresosAlmacen(begin, end,usuario);
        this.cantidadfilas = ingresosAlmacenList.size();
        return "";
    }
    public String siguiente_action() {
        super.next();
        ingresosAlmacenList = listadoIngresosAlmacen(begin, end,usuario);
        this.cantidadfilas = ingresosAlmacenList.size();

        return "";
    }
    public String ingresos_validar_atras_action() {
        super.back();
        cargarIngresosAlmacenValidarList_action();
        return "";
    }
    public String ingresos_validar_siguiente_action() {
        super.next();
        cargarIngresosAlmacenValidarList_action();
        return "";
    }
    protected void cargarPermisoPersonal() throws SQLException
    {
        administradorAlmacen=false;
        Connection con =null;
        try 
        {
            
            con=Util.openConnection(con);
            ManagedAccesoSistema managed=(ManagedAccesoSistema)Util.getSessionBean("ManagedAccesoSistema");
            StringBuilder consulta = new StringBuilder("select a.ADMINISTRADOR_ALMACEN");
                                    consulta.append(" from ALMACEN_HABILITADO_USUARIO a ");
                                    consulta.append(" where a.COD_ALMACEN=").append(managed.getAlmacenesGlobal().getCodAlmacen());
                                    consulta.append(" and a.COD_PERSONAL=").append(managed.getUsuarioModuloBean().getCodUsuarioGlobal());
            
            LOGGER.info("consulta verificar administrador sistema "+consulta);
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
            
            LOGGER.warn(ex);
        }
        finally{
            con.close();
        }
    }
    public String getCargarAgregarIngresosAlmacen() throws SQLException{
        DaoMeses daoMeses = new DaoMeses(this.LOGGER);
        mesesSelectList = daoMeses.listarMesCalendario();
        this.cargarPermisoPersonal();
        try {
            // inicio alejandro 3
            ingresosAlmacen= new IngresosAlmacen();
            ingresosAlmacenDetalleEstado= new IngresosAlmacenDetalleEstado();
            // final alejandro 3
            ingresosAlmacen.setGestiones(usuario.getGestionesGlobal());
            ingresosAlmacen.setFechaIngresoAlmacen(new Date());
            ingresosAlmacen.setNroIngresoAlmacen(obtieneNroIngresoAlmacen(usuario));

            tiposIngresosAlmacenList = cargarTiposIngresoAlmacen();
            ingresosAlmacen.getEstadosIngresoAlmacen().setCodEstadoIngresoAlmacen(1);
            tiposCompraList = cargarTiposCompra();
            proveedoresList = obtieneProveedores();
            capitulosList = this.cargarCapitulos();
            gruposList.add(new SelectItem("0", "-NINGUNO-"));
            this.cargarEmpaques();

            ingresosAlmacenDetalleList.clear();

            estanteAlmacenList = cargarEstanteAlmacen();
            ambienteAlmacenList = cargarAmbienteAlmacen();


        } catch (Exception e) {
            
            LOGGER.warn(e);
        }
        return null;
    }
    
    public void cargarIngresosAlmacenList(){
        ingresosAlmacenList = listadoIngresosAlmacen(begin, end,usuario);
    }
    
    public List listadoIngresosAlmacen(int filaInicial,int filaFinal,ManagedAccesoSistema usuario){
        List<IngresosAlmacen> ingresosAlmacenList = new ArrayList<IngresosAlmacen>();
        try {

            Connection con = null;
            con = Util.openConnection(con);

            StringBuilder consulta = new StringBuilder(" select * from (select ROW_NUMBER() OVER (ORDER BY via.NRO_INGRESO_ALMACEN desc,via.FECHA_INGRESO_ALMACEN desc) as 'FILAS',*")
                                                .append(" from VISTA_INGRESOS_ALMACEN via ")
                                                .append(" where via.COD_ALMACEN = '").append(usuario.getAlmacenesGlobal().getCodAlmacen()).append("' ");
            if(!nroIngresoAlmacen.equals("")){
                consulta.append(" and via.NRO_INGRESO_ALMACEN = ").append(nroIngresoAlmacen);
            }
            if(!nroOrdenCompra.equals("")){
                consulta.append(" and oc.NRO_ORDEN_COMPRA = ").append(nroOrdenCompra);
            }
            if(!codTipoIngreso.equals("0")){
                consulta.append(" and via.COD_TIPO_INGRESO_ALMACEN = ").append(codTipoIngreso);
            }
            if(!codEstadoIngreso.equals("0")){
                consulta.append("  and via.COD_ESTADO_INGRESO_ALMACEN =").append(codEstadoIngreso);
            }
            if(!codProveedor.equals("0")){
                consulta.append(" and via.COD_PROVEEDOR = ").append(codProveedor);
            }
            if(!codTipoCompra.equals("0")){
                consulta.append(" and via.COD_TIPO_COMPRA =").append(codTipoCompra);
            }
            if(!codGestion.equals("0")){
                consulta.append(" and via.COD_GESTION =").append(codGestion);

            }
            SimpleDateFormat sm= new SimpleDateFormat("yyyy/MM/dd");
            if(getFechaInicio()!=null){
                consulta.append(" and via.FECHA_INGRESO_ALMACEN > '").append(sm.format(fechaInicio)).append(" 00:00:00'");
            }
            if(getFechaFinal()!=null){
                consulta.append(" and via.FECHA_INGRESO_ALMACEN <'").append(sm.format(fechaFinal)).append(" 23:59:59' ");
            }
            if(!codCapitulo.equals("0")){
                if(!codGrupo.equals("0")){
                     if(!codMaterial.equals("0"))
                        {
                            consulta.append(" and via.COD_INGRESO_ALMACEN in(select iad.COD_INGRESO_ALMACEN from INGRESOS_ALMACEN_DETALLE iad where iad.COD_MATERIAL ='").append(codMaterial).append("') ");
                        }
                     else
                     {
                        consulta.append(" and via.COD_INGRESO_ALMACEN in(select iad.COD_INGRESO_ALMACEN from INGRESOS_ALMACEN_DETALLE iad where iad.COD_MATERIAL in(select m.COD_MATERIAL from MATERIALES m where m.COD_GRUPO ='").append(codGrupo).append("' )) ");
                     }
                      
                }
                else
                {
                    consulta.append(" and via.COD_INGRESO_ALMACEN in(select iad.COD_INGRESO_ALMACEN from INGRESOS_ALMACEN_DETALLE iad where iad.COD_MATERIAL in(select m.COD_MATERIAL from MATERIALES m where m.COD_GRUPO IN(select g.COD_GRUPO from GRUPOS g where g.COD_CAPITULO='").append(codCapitulo).append("') ))");
                }
                
            }


            consulta.append(" ) AS listado where FILAS BETWEEN ").append(filaInicial).append(" AND ").append(filaFinal);
            con = Util.openConnection(con);
            
            LOGGER.info("consulta listarIngresos: " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta.toString());
            ingresosAlmacenList.clear();
            while(rs.next()){
                IngresosAlmacen ingresosAlmacenItem = new IngresosAlmacen();
                ingresosAlmacenItem.getIngresoTransaccionSolicitud().setCodIngresoTransaccionSolicitud(rs.getInt("COD_INGRESO_TRANSACCION_SOLICITUD"));
                ingresosAlmacenItem.getIngresoTransaccionSolicitud().getEstadoTransaccionSolicitud().setCodEstadoTransaccionSolicitud(rs.getInt("COD_ESTADO_TRANSACCION_SOLICITUD"));
                ingresosAlmacenItem.getIngresoTransaccionSolicitud().getEstadoTransaccionSolicitud().setNombreEstadoTransaccionSolicitud(rs.getString("NOMBRE_ESTADO_TRANSACCION_SOLICITUD"));
                ingresosAlmacenItem.getIngresoTransaccionSolicitud().getPersonalValidador().setNombrePersonal(rs.getString("NOMBRE_VALIDADOR"));
                ingresosAlmacenItem.getIngresoTransaccionSolicitud().setObservacionValidador(rs.getString("OBSERVACION_VALIDADOR"));
                ingresosAlmacenItem.getIngresoTransaccionSolicitud().setFechaValidacion(rs.getTimestamp("FECHA_VALIDACION"));
                ingresosAlmacenItem.getIngresoTransaccionSolicitud().getTipoTransaccionSolicitudAlmacen().setNombreTipoTransaccionSolicitudAlmacen(motivoTransaccionIngreso); 
                ingresosAlmacenItem.getIngresoTransaccionSolicitud().getTipoTransaccionSolicitudAlmacen().setNombreTipoTransaccionSolicitudAlmacen(rs.getString("NOMBRE_TIPO_TRANSACCION_SOLICITUD_ALMACEN"));
                ingresosAlmacenItem.setNroIngresoAlmacen(rs.getInt("NRO_INGRESO_ALMACEN"));
                ingresosAlmacenItem.setCodIngresoAlmacen(rs.getInt("COD_INGRESO_ALMACEN"));
                ingresosAlmacenItem.getEstadosIngresoAlmacen().setCodEstadoIngresoAlmacen(rs.getInt("COD_ESTADO_INGRESO_ALMACEN"));
                ingresosAlmacenItem.getEstadosIngresoAlmacen().setNombreEstadoIngresoAlmacen(rs.getString("NOMBRE_ESTADO_INGRESO_ALMACEN"));
                //inicio ale orden de compra
                ingresosAlmacenItem.setFechaIngresoAlmacen(rs.getTimestamp("FECHA_INGRESO_ALMACEN"));
                // final ale order de compra
                //ingresosAlmacenItem.setFechaIngresoAlmacen(rs.getDate("FECHA_INGRESO_ALMACEN"));
                ingresosAlmacenItem.getTiposIngresoAlmacen().setCodTipoIngresoAlmacen(rs.getInt("COD_TIPO_INGRESO_ALMACEN"));
                ingresosAlmacenItem.getTiposIngresoAlmacen().setNombreTipoIngresoAlmacen(rs.getString("NOMBRE_TIPO_INGRESO_ALMACEN"));
                ingresosAlmacenItem.getTiposIngresoAlmacen().setEditable(rs.getInt("EDITABLE")==1);
                ingresosAlmacenItem.getTiposIngresoAlmacen().setAutomatico(rs.getInt("AUTOMATICO")==1);
                ingresosAlmacenItem.getOrdenesCompra().setNroOrdenCompra(rs.getInt("NRO_ORDEN_COMPRA"));
                ingresosAlmacenItem.getProveedores().setCodProveedor(rs.getString("COD_PROVEEDOR"));
                ingresosAlmacenItem.getProveedores().setNombreProveedor(rs.getString("NOMBRE_PROVEEDOR"));
                ingresosAlmacenItem.getProveedores().getPaises().setCodPais(rs.getInt("COD_PAIS"));
                ingresosAlmacenItem.getProveedores().getPaises().setNombrePais(rs.getString("NOMBRE_PAIS"));
                ingresosAlmacenItem.setObsIngresoAlmacen(rs.getString("OBS_INGRESO_ALMACEN"));
                ingresosAlmacenItem.getPersonal().setNombrePersonal(rs.getString("NOMBRE_PILA"));
                ingresosAlmacenItem.getPersonal().setApPaternoPersonal(rs.getString("AP_PATERNO_PERSONAL"));
                ingresosAlmacenItem.getPersonal().setApMaternoPersonal(rs.getString("AP_MATERNO_PERSONAL"));
                ingresosAlmacenItem.setCreditoFiscalSiNo(rs.getInt("credito_fiscal_si_no"));
                ingresosAlmacenItem.setNroDocumento(rs.getString("NRO_DOCUMENTO"));
                ingresosAlmacenItem.getOrdenesCompra().setCodOrdenCompra(rs.getInt("COD_ORDEN_COMPRA"));
				ingresosAlmacenItem.setTieneItemsRechazados(rs.getInt("TIENE_RECHAZADO")==1);
                ingresosAlmacenItem.getAlmacenes().setCodAlmacen(usuario.getAlmacenesGlobal().getCodAlmacen());
                ingresosAlmacenItem.getGestiones().setCodGestion(codGestion);                // inicio alejandro 2
                if(ingresosAlmacenItem.getEstadosIngresoAlmacen().getCodEstadoIngresoAlmacen()==2)
                
                {
                ingresosAlmacenItem.setColorFila("b");
                }
                // final alejandro 2
                ingresosAlmacenList.add(ingresosAlmacenItem);
            }
            cantidadfilas = ingresosAlmacenList.size();

        } catch (Exception e) {
            LOGGER.warn(e);
        }
        return ingresosAlmacenList;
    }
    
    public void cargarIngresosAlmacenValidarList_action(){
        try {

            Connection con = null;
            con = Util.openConnection(con);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            
            String consulta = " select * from (select ROW_NUMBER() OVER (ORDER BY sts.FECHA_SOLICITUD desc) as 'FILAS' " +
                    " ,ia.NRO_INGRESO_ALMACEN,ia.COD_INGRESO_ALMACEN,eia.COD_ESTADO_INGRESO_ALMACEN,eia.NOMBRE_ESTADO_INGRESO_ALMACEN, " +
                    " ia.FECHA_INGRESO_ALMACEN,t.COD_TIPO_INGRESO_ALMACEN,t.NOMBRE_TIPO_INGRESO_ALMACEN,oc.NRO_ORDEN_COMPRA,'' almacen_origen_traspaso, " +
                    " pr.COD_PROVEEDOR,pr.NOMBRE_PROVEEDOR,p.COD_PAIS,p.NOMBRE_PAIS,ia.OBS_INGRESO_ALMACEN,per.NOMBRE_PILA,per.AP_PATERNO_PERSONAL,per.AP_MATERNO_PERSONAL,ia.credito_fiscal_si_no,ia.NRO_DOCUMENTO " +
                    " , isnull(ia.COD_ORDEN_COMPRA,0) as COD_ORDEN_COMPRA, isnull(sts.COD_ESTADO_TRANSACCION_SOLICITUD,0) as COD_ESTADO_TRANSACCION_SOLICITUD, isnull(ets.NOMBRE_ESTADO_TRANSACCION_SOLICITUD,'') as NOMBRE_ESTADO_TRANSACCION_SOLICITUD" +
                    " , sts.NOMBRE_SOLICITANTE, sts.OBSERVACION_SOLICITANTE, sts.FECHA_VALIDACION, sts.FECHA_SOLICITUD, sts.COD_INGRESO_TRANSACCION_SOLICITUD, sts.NOMBRE_TIPO_TRANSACCION_SOLICITUD_ALMACEN " +
                    " , t.EDITABLE, t.AUTOMATICO " +
                    " from INGRESOS_ALMACEN ia " +
                    " left outer join  TIPOS_INGRESO_ALMACEN t on t.COD_TIPO_INGRESO_ALMACEN = ia.COD_TIPO_INGRESO_ALMACEN " +
                    " left outer join proveedores pr on pr.COD_PROVEEDOR = ia.COD_PROVEEDOR " +
                    " left outer join  paises p on pr.COD_PAIS = p.COD_PAIS " +
                    " left outer join ORDENES_COMPRA oc on oc.COD_ORDEN_COMPRA = ia.COD_ORDEN_COMPRA " +
                    " left outer join ESTADOS_INGRESO_ALMACEN eia on eia.COD_ESTADO_INGRESO_ALMACEN = ia.COD_ESTADO_INGRESO_ALMACEN " +
                    " left outer join TIPOS_COMPRA tc on tc.COD_TIPO_COMPRA = ia.COD_TIPO_COMPRA " +
                    " left outer join personal per on per.COD_PERSONAL = ia.COD_PERSONAL " +
                    " left outer join ESTADOS_INGRESOS_LIQUIDACION eil on eil.COD_ESTADO_INGRESO_LIQUIDACION = ia.COD_ESTADO_INGRESO_LIQUIDACION " +
                    " left join "
                    + " (   SELECT sts_a.COD_INGRESO_TRANSACCION_SOLICITUD, sts_a.COD_INGRESO_ALMACEN, COD_ESTADO_TRANSACCION_SOLICITUD, isnull(OBSERVACION_SOLICITANTE,'') as OBSERVACION_SOLICITANTE "
                        + " ,(ps.NOMBRES_PERSONAL + ' ' + ps.AP_PATERNO_PERSONAL + ' ' + ps.AP_MATERNO_PERSONAL) as NOMBRE_SOLICITANTE, FECHA_VALIDACION, FECHA_SOLICITUD"
                        + " , ttsa.NOMBRE_TIPO_TRANSACCION_SOLICITUD_ALMACEN "
                        + " FROM INGRESO_TRANSACCION_SOLICITUD sts_a"
                        + " inner join "
                        + " (   SELECT COD_INGRESO_ALMACEN, MAX(FECHA_SOLICITUD) AS MAX_FECHA_SOLICITUD"
                            + " FROM INGRESO_TRANSACCION_SOLICITUD"
                            + " GROUP BY COD_INGRESO_ALMACEN"
                        + " ) sts_group on sts_a.COD_INGRESO_ALMACEN = sts_group.COD_INGRESO_ALMACEN and sts_a.FECHA_SOLICITUD = sts_group.MAX_FECHA_SOLICITUD"
                        + " LEFT JOIN PERSONAL ps on ps.COD_PERSONAL = sts_a.COD_PERSONAL_SOLICITANTE"
                    + " LEFT JOIN TIPO_TRANSACCION_SOLICITUD_ALMACEN ttsa on ttsa.COD_TIPO_TRANSACCION_SOLICITUD_ALMACEN = sts_a.COD_TIPO_TRANSACCION_SOLICITUD_ALMACEN"
                    + " ) sts on sts.COD_INGRESO_ALMACEN = ia.COD_INGRESO_ALMACEN"
                    + " left join ESTADO_TRANSACCION_SOLICITUD ets on ets.COD_ESTADO_TRANSACCION_SOLICITUD = sts.COD_ESTADO_TRANSACCION_SOLICITUD "

                    + "  where ia.COD_ALMACEN = '"+usuario.getAlmacenesGlobal().getCodAlmacen()+"' "
                    + "  ";

            if (ingresoAlmacenBuscar.getIngresoTransaccionSolicitud().getTipoRevision() != 0) {
                String codigos = "";
                switch(ingresoAlmacenBuscar.getIngresoTransaccionSolicitud().getTipoRevision()){
                    case 1: codigos = "(1,2,3,4,5,6,7)"; 
                    break;
                    case 2: codigos = "(1,2)"; 
                    break;
                    case 3: codigos = "(3,4,7)"; 
                    break;
                    case 4: codigos = "(5,6)"; 
                    break;
                    default: codigos = "(1,2)";
                    break;
                }
                consulta += " and ets.COD_ESTADO_TRANSACCION_SOLICITUD in "+codigos;
            }
            
            if (ingresoAlmacenBuscar.getIngresoTransaccionSolicitud().getFechaValidacion()!= null || ingresoAlmacenBuscar.getIngresoTransaccionSolicitud().getFechaValidacionFin()!= null) {
                if (ingresoAlmacenBuscar.getIngresoTransaccionSolicitud().getFechaValidacion() == null) {
                    ingresoAlmacenBuscar.getIngresoTransaccionSolicitud().setFechaValidacion(ingresoAlmacenBuscar.getIngresoTransaccionSolicitud().getFechaValidacionFin());
                }
                if (ingresoAlmacenBuscar.getIngresoTransaccionSolicitud().getFechaValidacionFin() == null) {
                    ingresoAlmacenBuscar.getIngresoTransaccionSolicitud().setFechaValidacionFin(ingresoAlmacenBuscar.getIngresoTransaccionSolicitud().getFechaValidacion());
                }
                consulta += " and (CAST(FECHA_VALIDACION AS DATE) >= '"+sdf.format(ingresoAlmacenBuscar.getIngresoTransaccionSolicitud().getFechaValidacion())+"' AND CAST(FECHA_VALIDACION AS DATE) <= '"+sdf.format(ingresoAlmacenBuscar.getIngresoTransaccionSolicitud().getFechaValidacionFin())+"' ) ";
            }
            if (ingresoAlmacenBuscar.getIngresoTransaccionSolicitud().getFechaSolicitud()!= null || ingresoAlmacenBuscar.getIngresoTransaccionSolicitud().getFechaSolicitudFin()!= null) {
                if (ingresoAlmacenBuscar.getIngresoTransaccionSolicitud().getFechaSolicitud() == null) {
                    ingresoAlmacenBuscar.getIngresoTransaccionSolicitud().setFechaSolicitud(ingresoAlmacenBuscar.getIngresoTransaccionSolicitud().getFechaSolicitudFin());
                }
                if (ingresoAlmacenBuscar.getIngresoTransaccionSolicitud().getFechaSolicitudFin()== null) {
                    ingresoAlmacenBuscar.getIngresoTransaccionSolicitud().setFechaSolicitudFin(ingresoAlmacenBuscar.getIngresoTransaccionSolicitud().getFechaSolicitud());
                }
                consulta += " and (CAST(FECHA_SOLICITUD AS DATE) >= '"+sdf.format(ingresoAlmacenBuscar.getIngresoTransaccionSolicitud().getFechaSolicitud())+"' AND CAST(FECHA_SOLICITUD AS DATE) <= '"+sdf.format(ingresoAlmacenBuscar.getIngresoTransaccionSolicitud().getFechaSolicitudFin())+"' ) ";
            }

            consulta+=" ) AS listado where FILAS BETWEEN "+begin+" AND "+end+"   ";
            
            con = Util.openConnection(con);
            LOGGER.info("consulta listarIngresosValidar:" + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            ingresosAlmacenValidarList.clear();
            while(rs.next()){
                IngresosAlmacen ingresosAlmacenItem = new IngresosAlmacen();
                ingresosAlmacenItem.getIngresoTransaccionSolicitud().setCodIngresoTransaccionSolicitud(rs.getInt("COD_INGRESO_TRANSACCION_SOLICITUD"));
                ingresosAlmacenItem.getIngresoTransaccionSolicitud().getEstadoTransaccionSolicitud().setCodEstadoTransaccionSolicitud(rs.getInt("COD_ESTADO_TRANSACCION_SOLICITUD"));
                ingresosAlmacenItem.getIngresoTransaccionSolicitud().getEstadoTransaccionSolicitud().setNombreEstadoTransaccionSolicitud(rs.getString("NOMBRE_ESTADO_TRANSACCION_SOLICITUD"));
                ingresosAlmacenItem.getIngresoTransaccionSolicitud().getPersonalSolicitante().setNombrePersonal(rs.getString("NOMBRE_SOLICITANTE"));
                ingresosAlmacenItem.getIngresoTransaccionSolicitud().setObservacionSolicitante(rs.getString("OBSERVACION_SOLICITANTE"));
                ingresosAlmacenItem.getIngresoTransaccionSolicitud().setFechaValidacion(rs.getTimestamp("FECHA_VALIDACION"));
                ingresosAlmacenItem.getIngresoTransaccionSolicitud().setFechaSolicitud(rs.getTimestamp("FECHA_SOLICITUD"));
                ingresosAlmacenItem.getIngresoTransaccionSolicitud().getTipoTransaccionSolicitudAlmacen().setNombreTipoTransaccionSolicitudAlmacen(rs.getString("NOMBRE_TIPO_TRANSACCION_SOLICITUD_ALMACEN"));
                ingresosAlmacenItem.setNroIngresoAlmacen(rs.getInt("NRO_INGRESO_ALMACEN"));
                ingresosAlmacenItem.setCodIngresoAlmacen(rs.getInt("COD_INGRESO_ALMACEN"));
                ingresosAlmacenItem.getEstadosIngresoAlmacen().setCodEstadoIngresoAlmacen(rs.getInt("COD_ESTADO_INGRESO_ALMACEN"));
                ingresosAlmacenItem.getEstadosIngresoAlmacen().setNombreEstadoIngresoAlmacen(rs.getString("NOMBRE_ESTADO_INGRESO_ALMACEN"));
                ingresosAlmacenItem.setFechaIngresoAlmacen(rs.getTimestamp("FECHA_INGRESO_ALMACEN"));
                ingresosAlmacenItem.getTiposIngresoAlmacen().setCodTipoIngresoAlmacen(rs.getInt("COD_TIPO_INGRESO_ALMACEN"));
                ingresosAlmacenItem.getTiposIngresoAlmacen().setNombreTipoIngresoAlmacen(rs.getString("NOMBRE_TIPO_INGRESO_ALMACEN"));
                ingresosAlmacenItem.getTiposIngresoAlmacen().setEditable(rs.getInt("EDITABLE")==1);
                ingresosAlmacenItem.getTiposIngresoAlmacen().setAutomatico(rs.getInt("AUTOMATICO")==1);
                ingresosAlmacenItem.getOrdenesCompra().setNroOrdenCompra(rs.getInt("NRO_ORDEN_COMPRA"));
                ingresosAlmacenItem.getProveedores().setCodProveedor(rs.getString("COD_PROVEEDOR"));
                ingresosAlmacenItem.getProveedores().setNombreProveedor(rs.getString("NOMBRE_PROVEEDOR"));
                ingresosAlmacenItem.getProveedores().getPaises().setCodPais(rs.getInt("COD_PAIS"));
                ingresosAlmacenItem.getProveedores().getPaises().setNombrePais(rs.getString("NOMBRE_PAIS"));
                ingresosAlmacenItem.setObsIngresoAlmacen(rs.getString("OBS_INGRESO_ALMACEN"));
                ingresosAlmacenItem.getPersonal().setNombrePersonal(rs.getString("NOMBRE_PILA"));
                ingresosAlmacenItem.getPersonal().setApPaternoPersonal(rs.getString("AP_PATERNO_PERSONAL"));
                ingresosAlmacenItem.getPersonal().setApMaternoPersonal(rs.getString("AP_MATERNO_PERSONAL"));
                ingresosAlmacenItem.setCreditoFiscalSiNo(rs.getInt("credito_fiscal_si_no"));
                ingresosAlmacenItem.setNroDocumento(rs.getString("NRO_DOCUMENTO"));
                ingresosAlmacenItem.getOrdenesCompra().setCodOrdenCompra(rs.getInt("COD_ORDEN_COMPRA"));
                ingresosAlmacenItem.getAlmacenes().setCodAlmacen(usuario.getAlmacenesGlobal().getCodAlmacen());
                ingresosAlmacenItem.getGestiones().setCodGestion(codGestion);
                if(ingresosAlmacenItem.getEstadosIngresoAlmacen().getCodEstadoIngresoAlmacen()==2){
                    ingresosAlmacenItem.setColorFila("b");
                }
                ingresosAlmacenValidarList.add(ingresosAlmacenItem);
            }
            cantidadfilas = ingresosAlmacenValidarList.size();
        } catch (Exception e) {
            LOGGER.warn(e);
        }
    }

    public List obtieneProveedores() {
        List proveedoresList = new ArrayList();
        try {

            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select p.COD_PROVEEDOR,p.NOMBRE_PROVEEDOR from proveedores p where p.COD_ESTADO_REGISTRO = 1 order by p.NOMBRE_PROVEEDOR";

            ResultSet rs = null;
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            proveedoresList.clear();
            rs = st.executeQuery(consulta);
            proveedoresList.add(new SelectItem("0", "-NINGUNO-"));
            while (rs.next()) {
                proveedoresList.add(new SelectItem(rs.getString("COD_PROVEEDOR"), rs.getString("NOMBRE_PROVEEDOR")));
            }

            if (rs != null) {
                rs.close();
                st.close();
                rs = null;
                st = null;
            }

        } catch (Exception e) {

            LOGGER.warn(e);
        }
        return proveedoresList;
    }
     public List cargarTiposIngresoAlmacen() {
        List tiposIngresosAlmacenList = new ArrayList();
        try {

            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select t.COD_TIPO_INGRESO_ALMACEN,t.NOMBRE_TIPO_INGRESO_ALMACEN " +
                    " from TIPOS_INGRESO_ALMACEN t where t.COD_ESTADO_REGISTRO = 1 " +
                    
                    " and t.cod_tipo_ingreso_almacen not in (1,4,3,6,7,11,12,13) " +
                    " and t.cod_tipo_ingreso_almacen<>6 and t.cod_tipo_ingreso_almacen<>7 ";

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
            LOGGER.warn(e);
        }
        return tiposIngresosAlmacenList;
    }
     public List cargarAmbienteAlmacen() {
        List ambientesList = new ArrayList();
        try {

            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select cod_ambiente,nombre_ambiente from AMBIENTE_ALMACEN where cod_almacen = '"+usuario.getAlmacenesGlobal().getCodAlmacen()+"' ";
            LOGGER.info("consulta " + consulta);

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
            
            LOGGER.warn(e);
        }
        return ambientesList;
    }
     public List cargarEstanteAlmacen() {
        List estantesList = new ArrayList();
        try {

            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select cod_estante,nombre_estante from estante_ambiente where cod_ambiente = '"+ingresosAlmacenDetalleEstado.getEstanteAlmacen().getAmbienteAlmacen().getCodAmbiente()+"'  ";
            
            LOGGER.info("consulta " + consulta);

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
            
            LOGGER.warn(e);
        }
        return estantesList;
    }
    
    public String ambienteAlmacen_change(){
        estanteAlmacenList = cargarEstanteAlmacen();
        return null;
    }
     public int obtieneNroIngresoAlmacen(ManagedAccesoSistema usuario){
        int nroIngresoAlmacen = 0;
        try {
            Connection con = null;
            con = Util.openConnection(con);

            
            String consulta= " select (isnull(max(nro_ingreso_almacen),0)+1) nro_ingreso_almacen  from ingresos_almacen where cod_gestion='"+codGestion+"' " +
                     " and estado_sistema=1 and cod_almacen='"+usuario.getAlmacenesGlobal().getCodAlmacen()+"'  ";
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            if(rs.next()){
                nroIngresoAlmacen = rs.getInt("nro_ingreso_almacen");
            }
        } catch (Exception e) {
            
            LOGGER.warn(e);
        }
        return nroIngresoAlmacen;
    }
     public int obtieneNroIngresoAlmacen(ManagedAccesoSistema usuario,Almacenes almacenes){
        int nroIngresoAlmacen = 0;
        try {
            Connection con = null;
            con = Util.openConnection(con);

            
            String consulta= " select (isnull(max(nro_ingreso_almacen),0)+1) nro_ingreso_almacen  from ingresos_almacen where cod_gestion='"+codGestion+"' " +
                     " and estado_sistema=1 and cod_almacen='"+almacenes.getCodAlmacen()+"'  ";
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            if(rs.next()){
                nroIngresoAlmacen = rs.getInt("nro_ingreso_almacen");
            }
        } catch (Exception e) {
            
            LOGGER.warn(e);
        }
        return nroIngresoAlmacen;
    }


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
            
            LOGGER.warn(e);
        }
        return tiposIngresosAlmacenList;
    }
    public String proveedores_change(){
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select p.COD_PAIS,p.NOMBRE_PAIS from paises p inner join proveedores pr on pr.COD_PAIS = p.COD_PAIS " +
                              " where pr.COD_PROVEEDOR = '"+ingresosAlmacen.getProveedores().getCodProveedor()+"' ";
            
            LOGGER.info("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            if(rs.next()){
                ingresosAlmacen.getProveedores().getPaises().setCodPais(rs.getInt("COD_PAIS"));
                ingresosAlmacen.getProveedores().getPaises().setNombrePais(rs.getString("NOMBRE_PAIS"));
            }
            rs.close();
            con.close();

        } catch (Exception e) {
            
            LOGGER.warn(e);
        }
        return null;
    }
     public List cargarCapitulos() {
        List capitulosList = new ArrayList();
        try {

            Connection con = null;
            con = Util.openConnection(con);
            String consulta = "  select cod_capitulo, nombre_capitulo from capitulos where cod_estado_registro=1 ";
            LOGGER.info("consulta: " + consulta);
            ResultSet rs = null;
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            capitulosList.clear();
            rs = st.executeQuery(consulta);
            capitulosList.add(new SelectItem("0", "-NINGUNO-"));
            while (rs.next()) {
                capitulosList.add(new SelectItem(rs.getString("cod_capitulo"), rs.getString("nombre_capitulo")));
            }

            if (rs != null) {
                rs.close();
                st.close();
                rs = null;
                st = null;
            }

        } catch (Exception e) {
            
            LOGGER.warn(e);
        }
        return capitulosList;
    }
      public String capitulos_change(){
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " SELECT GR.COD_GRUPO,GR.NOMBRE_GRUPO FROM GRUPOS GR WHERE GR.COD_CAPITULO = '"+buscarMaterial.getGrupos().getCapitulos().getCodCapitulo()+"' AND GR.COD_ESTADO_REGISTRO = 1 ";
            
            LOGGER.info("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            gruposList.clear();
            gruposList.add(new SelectItem("0", "-NINGUNO-"));
            while(rs.next()){
                gruposList.add(new SelectItem(rs.getString("COD_GRUPO"), rs.getString("NOMBRE_GRUPO")));
            }
            rs.close();
            con.close();

        } catch (Exception e) {
            
            LOGGER.warn(e);
        }
        return null;
    }

    public String buscarMaterial_action(){
        try {
            Connection con = null;
            con = Util.openConnection(con);
            ManagedAccesoSistema managed = (ManagedAccesoSistema)Util.getSessionBean("ManagedAccesoSistema");
            String consulta = " select m.COD_MATERIAL,m.NOMBRE_MATERIAL,m.MATERIAL_PRODUCCION,m.MATERIAL_MUESTREO,gr.COD_GRUPO,gr.NOMBRE_GRUPO,cap.NOMBRE_CAPITULO, cap.COD_CAPITULO,cap.NOMBRE_CAPITULO," +
                                    " m.COD_UNIDAD_MEDIDA,u1.ABREVIATURA ABREVIATURA1 ,m.COD_UNIDAD_MEDIDA_COMPRA,u2.ABREVIATURA ABREVIATURA2,cantidad_maximamuestreo " +
                                    " ,isnull(gfv.APLICA_FECHA_VENCIMIENTO,0) as APLICA_FECHA_VENCIMIENTO"+
                            " from MATERIALES m  " +
                                    " inner join CONFIGURACION_SALIDA_MATERIAL_PERMITIDO csm on csm.MOVIMIENTO_ITEM=m.MOVIMIENTO_ITEM"+
                                    " and csm.COD_ESTADO_REGISTRO =m.COD_ESTADO_REGISTRO"+
                                    " and csm.COD_ALMACEN = "+managed.getAlmacenesGlobal().getCodAlmacen()+
                                    " inner join GRUPOS gr on gr.COD_GRUPO = m.COD_GRUPO " +
                                    " left outer join GRUPOS_FECHA_VENCIMIENTO gfv on gfv.COD_GRUPO = gr.COD_GRUPO"+
                                    " inner join CAPITULOS cap on cap.COD_CAPITULO = gr.COD_CAPITULO" +
                                    " inner join UNIDADES_MEDIDA u1 on u1.COD_UNIDAD_MEDIDA = m.COD_UNIDAD_MEDIDA " +
                                    " inner join UNIDADES_MEDIDA u2 on u2.COD_UNIDAD_MEDIDA = m.COD_UNIDAD_MEDIDA_COMPRA ";
                    if (ingresosAlmacen.getOrdenesCompra().getCodOrdenCompra() != 0) {
                        consulta += " left join ORDENES_COMPRA_DETALLE ocd on ocd.COD_MATERIAL = m.COD_MATERIAL ";
                    }
                    consulta += " where m.NOMBRE_MATERIAL like '%"+buscarMaterial.getNombreMaterial()+"%' " ;
                    if (ingresosAlmacen.getOrdenesCompra().getCodOrdenCompra() != 0) {
                        consulta += " and ocd.COD_ORDEN_COMPRA = '"+ingresosAlmacen.getOrdenesCompra().getCodOrdenCompra()+"'";
                    }
                    if(buscarMaterial.getGrupos().getCodGrupo()>0){
                        consulta=consulta+ " and gr.COD_GRUPO = '"+buscarMaterial.getGrupos().getCodGrupo()+"' " ;
                    }
                    if(buscarMaterial.getGrupos().getCapitulos().getCodCapitulo()>0){
                        consulta=consulta+ " and cap.COD_CAPITULO = '"+buscarMaterial.getGrupos().getCapitulos().getCodCapitulo()+"' " ;
                    }
                    consulta = consulta + " and m.COD_MATERIAL not in ("+this.itemsSeleccionadosIngresoAlmacen()+") ";
            LOGGER.info("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            materialesBuscarList.clear();
            while(rs.next()){
                Materiales materialesItem = new Materiales();
                materialesItem.setCodMaterial(rs.getString("COD_MATERIAL"));
                materialesItem.setNombreMaterial(rs.getString("NOMBRE_MATERIAL"));
                materialesItem.getGrupos().setCodGrupo(rs.getInt("COD_GRUPO"));
                materialesItem.getGrupos().setNombreGrupo(rs.getString("NOMBRE_GRUPO"));
                materialesItem.getGrupos().getCapitulos().setCodCapitulo(rs.getInt("COD_CAPITULO"));
                materialesItem.getGrupos().getCapitulos().setNombreCapitulo(rs.getString("NOMBRE_CAPITULO"));
                materialesItem.getUnidadesMedida().setCodUnidadMedida(rs.getInt("COD_UNIDAD_MEDIDA"));
                materialesItem.getUnidadesMedida().setAbreviatura(rs.getString("ABREVIATURA1"));
                materialesItem.getUnidadesMedidaCompra().setCodUnidadMedida(rs.getInt("COD_UNIDAD_MEDIDA_COMPRA"));
                materialesItem.getUnidadesMedidaCompra().setAbreviatura(rs.getString("ABREVIATURA2"));
                materialesItem.setMaterialProduccion(rs.getInt("MATERIAL_PRODUCCION"));
                materialesItem.setMaterialMuestreo(rs.getInt("MATERIAL_MUESTREO"));
                materialesItem.setCantidadMaximaMuestreo(rs.getFloat("cantidad_maximamuestreo"));
                materialesItem.getGrupos().getGruposFechaVencimiento().setAplicaFechaVencimiento(rs.getInt("APLICA_FECHA_VENCIMIENTO") > 0);
                materialesBuscarList.add(materialesItem);
            }
            rs.close();
            con.close();


        } catch (Exception e) {
            LOGGER.warn(e);
        }
        return null;
    }

     public String agregarItem_action(){
         try {
             materialesBuscarList.clear();
             buscarMaterial = new Materiales();
         } catch (Exception e) {
             
             LOGGER.warn(e);
         }
         return null;
     }


     public String itemsSeleccionadosIngresoAlmacen(){
         String itemsIngresosAlmacen = "-1";
         try {
             Iterator i = ingresosAlmacenDetalleList.iterator();
             while(i.hasNext()){
                 IngresosAlmacenDetalle ingresosAlmacenDetalleItem = (IngresosAlmacenDetalle)i.next();
                 itemsIngresosAlmacen = itemsIngresosAlmacen +","+ ingresosAlmacenDetalleItem.getMateriales().getCodMaterial();

             }
         } catch (Exception e) {
             
             LOGGER.warn(e);
         }
         return itemsIngresosAlmacen;

     }


    public String seleccionarMaterial_action(){

        try {


            // se agrega el detalle de ingreso almacen

            Materiales materiales = (Materiales) materialesBuscarDataTable.getRowData();
            IngresosAlmacenDetalle ingresosAlmacenDetalleItem = new IngresosAlmacenDetalle();
            ingresosAlmacenDetalleItem.setMateriales(materiales);
            ingresosAlmacenDetalleItem.getIngresosAlmacen().setCodIngresoAlmacen(ingresosAlmacen.getCodIngresoAlmacen());

            //se colocan los datos de unidad medida compra del material
            ingresosAlmacenDetalleItem.getOrdenesCompraDetalle().getUnidadesMedida().setCodUnidadMedida(materiales.getUnidadesMedidaCompra().getCodUnidadMedida());
            ingresosAlmacenDetalleItem.getOrdenesCompraDetalle().getUnidadesMedida().setAbreviatura(materiales.getUnidadesMedidaCompra().getAbreviatura());


            ingresosAlmacenDetalleItem.getUnidadesMedida().setCodUnidadMedida(materiales.getUnidadesMedida().getCodUnidadMedida());
            ingresosAlmacenDetalleItem.getUnidadesMedida().setAbreviatura(materiales.getUnidadesMedida().getAbreviatura());




            ingresosAlmacenDetalleItem.setUnidadesMedidaEquivalencia(materiales.getUnidadesMedida());

            ingresosAlmacenDetalleItem.setSecciones(this.buscaSeccionAlmacen(ingresosAlmacenDetalleItem,usuario));
            ingresosAlmacenDetalleItem.getMateriales().setGrupos(materiales.getGrupos());





            Equivalencias equivalencias = this.buscaEquivalencia(ingresosAlmacenDetalleItem);
            ingresosAlmacenDetalleItem.setValorEquivalencia(equivalencias.getValorEquivalencia());
            //ingresosAlmacenDetalleItem.setUnidadesMedida(equivalencias.getUnidadesMedida2());

            DateTime dateTime = new DateTime(ingresosAlmacenDetalleEstado.getFechaVencimiento());
            dateTime = dateTime.minusDays(1);
            ingresosAlmacenDetalleEstado.setFechaManufactura(dateTime.toDate());
            ingresosAlmacenDetalleList.add(ingresosAlmacenDetalleItem);


        } catch (Exception e) {
            
            LOGGER.warn(e);
        }
        return null;
    }
    public String guardarDetalleItem_action(){
        registradoCorrectamente = false;
        try {
            mensaje = "";
            if(ingresosAlmacenDetalle.getIngresosAlmacenDetalleEstadoList().isEmpty()){
                mensaje = "debe registrar el detalle de etiquetas";
                return null;
            }
            validarCantidadIngresoFisicoOrdenCompra_action();
            if(mensaje.compareTo("")!=0){
                return null;
            }
            IngresosAlmacenDetalle  ingresosAlmacenDetalleItem = ingresosAlmacenDetalle;
            ingresosAlmacenDetalleItem.setCantTotalIngresoFisico(ingresosAlmacenDetalle.getCantTotalIngresoFisico());
            float sumaCantidadesParciales = 0f;
            //if(this.validaDatosIngresoAcondicionamiento(ingresosAlmacenDetalleItem)==true){ //valida fechas
            //System.out.println("list size: "+ingresosAlmacenDetalle.getIngresosAlmacenDetalleEstadoList());
            Iterator i= ingresosAlmacenDetalle.getIngresosAlmacenDetalleEstadoList().iterator();
//                ingresosAlmacenDetalleItem.setCantTotalIngresoFisico(0);
            int index = 0;
            while(i.hasNext()){
                index ++;
                IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstadoItem = (IngresosAlmacenDetalleEstado)i.next();
                if(ingresosAlmacenDetalleEstadoItem.getCantidadParcial() <= 0){
                    mensaje = "No se puede registrar una cantidad menor o igual a 0 en la fila "+index;
                    return null;
                }
                sumaCantidadesParciales = sumaCantidadesParciales + ingresosAlmacenDetalleEstadoItem.getCantidadParcial();
               //ingresosAlmacenDetalleItem.setCantTotalIngresoFisico(ingresosAlmacenDetalleItem.getCantTotalIngresoFisico()+ingresosAlmacenDetalleEstadoItem.getCantidadParcial());
                ingresosAlmacenDetalleEstadoItem.setCantidadRestante(ingresosAlmacenDetalleEstadoItem.getCantidadParcial());
                //System.out.println("parcial_ "+ingresosAlmacenDetalleEstadoItem.getCantidadParcial());
            }
           //}
//            else{
//                return null;
//            }
            //System.out.println("total: "+ingresosAlmacenDetalleItem.getCantTotalIngresoFisico());
            if (ingresosAlmacen.getOrdenesCompra().getCodOrdenCompra() != 0) {
                float cantidadTotalActual = sumaCantidadesParciales + ingresosAlmacenDetalle.getOrdenesCompraDetalle().getSumaParcialCantidadesIngresosDetalles();
                if (cantidadTotalActual > ingresosAlmacenDetalle.getCantEquivalente()) {
                    mensaje="El Ingreso Fisico suma un total de: "+sumaCantidadesParciales+"["+ingresosAlmacenDetalle.getUnidadesMedida().getAbreviatura()+"], no puede superar la Cantidad Equivalente de Orden de Compra: "
                            +ingresosAlmacenDetalle.getCantEquivalente()+"["+ingresosAlmacenDetalle.getUnidadesMedidaEquivalencia().getAbreviatura()+"]";
                    if (ingresosAlmacenDetalle.getOrdenesCompraDetalle().getSumaParcialCantidadesIngresosDetalles() > 0.0f) {
                        mensaje+=". La suma de los anteriores Ingresos relacionados a esta Orden de Compra son: "+ingresosAlmacenDetalle.getOrdenesCompraDetalle().getSumaParcialCantidadesIngresosDetalles()+"["+ingresosAlmacenDetalle.getUnidadesMedida().getAbreviatura()+"]";
                    }
                    return null;
                }
            }
            ingresosAlmacenDetalle.setCantTotalIngresoFisico(sumaCantidadesParciales);
            registradoCorrectamente = true;
//            ingresosAlmacenDetalle.setCantTotalIngreso(sumaCantidadesParciales);
            //aqui las unidades de medida
        } catch (Exception e) {
            LOGGER.warn(e);
        }
        return null;
    }
    public boolean validaDatosIngresoAcondicionamiento(IngresosAlmacenDetalle ingresosAlmacenDetalleItem){
        boolean datosValidos  = true;
        try {

             DateTime fechaVencimiento =  new DateTime(new Date());
             fechaVencimiento = fechaVencimiento.plusMonths(6);
             fechaVencimiento = fechaVencimiento.dayOfMonth().withMaximumValue();

              SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
              Iterator i= ingresosAlmacenDetalle.getIngresosAlmacenDetalleEstadoList().iterator();
                 while(i.hasNext()){
                     IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstadoItem = (IngresosAlmacenDetalleEstado)i.next();
                     ingresosAlmacenDetalleEstadoItem.setFechaVencimiento(sdf.parse(sdf.format(ingresosAlmacenDetalleEstadoItem.getFechaVencimiento())));
                     ingresosAlmacenDetalleEstadoItem.setFechaManufactura(sdf.parse(sdf.format(ingresosAlmacenDetalleEstadoItem.getFechaManufactura())));
                    // System.out.println("fecha venc "+ingresosAlmacenDetalleEstadoItem.getFechaVencimiento());
                     // System.out.println("fecha rean "+ingresosAlmacenDetalleEstadoItem.getFechaReanalisis());
                     // System.out.println("comparacion "+ingresosAlmacenDetalleEstadoItem.getFechaVencimiento().compareTo(ingresosAlmacenDetalleEstadoItem.getFechaReanalisis()));
                     if(ingresosAlmacenDetalleEstadoItem.getFechaVencimiento().compareTo(ingresosAlmacenDetalleEstadoItem.getFechaReanalisis())>=0){ //getFechaMAnufactura()
                         mensaje = "La fecha de Manufactura debe ser menor a la fecha de Vencimiento ";
                         //System.out.println("mensaje"+mensaje);
                         datosValidos=false;
                         break;
                     }
//                     if(fechaVencimiento.toDate().compareTo(ingresosAlmacenDetalleEstadoItem.getFechaVencimiento())>0){
//                        mensaje =" En la fecha de vencimiento debe tener minimamente seis meses de vigencia ";
//                        break;
//                     }
                 }
          } catch (Exception e) {
              
              LOGGER.warn(e);
        }
             return datosValidos;
     }
    public String validaFechas_action(){
        try {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        ingresosAlmacenDetalleEstado.setFechaManufactura(sdf.parse(sdf.format(ingresosAlmacenDetalleEstado.getFechaManufactura())));
        ingresosAlmacenDetalleEstado.setFechaVencimiento(sdf.parse(sdf.format(ingresosAlmacenDetalleEstado.getFechaVencimiento())));
        System.out.println("entro al evento "+ingresosAlmacenDetalleEstado.getFechaManufactura()+" " + ingresosAlmacenDetalleEstado.getFechaVencimiento());
        mensaje = "";
        if(ingresosAlmacenDetalleEstado.getFechaManufactura().compareTo(ingresosAlmacenDetalleEstado.getFechaVencimiento())>=0){
            DateTime dateTime = new DateTime(ingresosAlmacenDetalleEstado.getFechaVencimiento());
            dateTime = dateTime.minusDays(1);
            ingresosAlmacenDetalleEstado.setFechaManufactura(dateTime.toDate());
            mensaje = "La fecha de Manufactura debe ser menor a la fecha de Vencimiento ";
        }
        } catch (Exception e) {
            
            LOGGER.warn(e);
        }
        return "";
    }
    public String validaFechas_action1(){
        try {
        //System.out.println("entro al evento "+ingresosAlmacenDetalleEstado.getFechaManufactura()+" " + ingresosAlmacenDetalleEstado.getFechaVencimiento());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        ingresosAlmacenDetalleEstado.setFechaManufactura(sdf.parse(sdf.format(ingresosAlmacenDetalleEstado.getFechaManufactura())));
        ingresosAlmacenDetalleEstado.setFechaVencimiento(sdf.parse(sdf.format(ingresosAlmacenDetalleEstado.getFechaVencimiento())));
        mensaje = "";
        if(ingresosAlmacenDetalleEstado.getFechaManufactura().compareTo(ingresosAlmacenDetalleEstado.getFechaVencimiento())>=0){
            //DateTime dateTime = new DateTime(ingresosAlmacenDetalleEstado.getFechaVencimiento());
            //dateTime = dateTime.minusDays(1);
            //ingresosAlmacenDetalleEstado.setFechaManufactura(dateTime.toDate());
            mensaje = "La fecha de Manufactura debe ser menor a la fecha de Vencimiento ";
        }
        } catch (Exception e) {
            
            LOGGER.warn(e);
        }
        return "";
    }
    public Secciones buscaSeccionAlmacen(IngresosAlmacenDetalle ingresosAlmacenDetalle,ManagedAccesoSistema usuario) {
        Secciones secciones = new Secciones();
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select distinct(s.cod_seccion) cod_seccion,s.nombre_seccion " +
                                    " from secciones s,secciones_detalle sd, almacenes a " +
                                    " where s.cod_seccion=sd.cod_seccion and s.cod_almacen='"+usuario.getAlmacenesGlobal().getCodAlmacen()+"' " +
                                    " and s.cod_estado_registro=1 and a.cod_almacen=s.cod_almacen  " +
                                    " and ((sd.cod_material='"+ingresosAlmacenDetalle.getMateriales().getCodMaterial()+"' and s.estado_sistema=1) " +
                                    " or (sd.cod_material=0 and sd.cod_grupo='"+ingresosAlmacenDetalle.getMateriales().getGrupos().getCodGrupo() +"') " +
                                    " or (sd.cod_material=0 and sd.cod_grupo=0 and sd.cod_capitulo='"+ ingresosAlmacenDetalle.getMateriales().getGrupos().getCapitulos().getCodCapitulo() +"')) ";
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            
            LOGGER.info("consulta " + consulta);
            if(rs.next()){
                secciones.setCodSeccion(rs.getInt("cod_seccion"));
                secciones.setNombreSeccion(rs.getString("nombre_seccion"));
            }
            rs.close();
            st.close();
            con.close();

        } catch (Exception e) {
            
            LOGGER.warn(e);
        }
        return secciones;
    }

    public Equivalencias buscaEquivalencia1(IngresosAlmacenDetalle ingresosAlmacenDetalle){
        Equivalencias equivalencias = new Equivalencias();
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select e.VALOR_EQUIVALENCIA from EQUIVALENCIAS e " +
                    " where (e.COD_UNIDAD_MEDIDA = '"+ingresosAlmacenDetalle.getOrdenesCompraDetalle().getUnidadesMedida().getCodUnidadMedida()+"' " +
                    " and e.COD_UNIDAD_MEDIDA2 = '"+ingresosAlmacenDetalle.getUnidadesMedida().getCodUnidadMedida()+"') " +
                    " or (e.COD_UNIDAD_MEDIDA = '"+ ingresosAlmacenDetalle.getUnidadesMedida().getCodUnidadMedida()+"' " +
                    " and e.COD_UNIDAD_MEDIDA2 = '"+ingresosAlmacenDetalle.getOrdenesCompraDetalle().getUnidadesMedida().getCodUnidadMedida()+"') ";

            
            LOGGER.info("consulta" + consulta);
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs=st.executeQuery(consulta);
            if(rs.next()){
                equivalencias.setValorEquivalencia(rs.getFloat("VALOR_EQUIVALENCIA"));
                equivalencias.getUnidadesMedida1().setAbreviatura(ingresosAlmacenDetalle.getOrdenesCompraDetalle().getUnidadesMedida().getAbreviatura());
                equivalencias.getUnidadesMedida2().setAbreviatura(ingresosAlmacenDetalle.getUnidadesMedida().getAbreviatura());
            }
            if(ingresosAlmacenDetalle.getUnidadesMedida().getCodUnidadMedida()==ingresosAlmacenDetalle.getOrdenesCompraDetalle().getUnidadesMedida().getCodUnidadMedida()){
                equivalencias.setValorEquivalencia(1);
                equivalencias.getUnidadesMedida2().setAbreviatura(ingresosAlmacenDetalle.getUnidadesMedida().getAbreviatura());
            }

            rs.close();
            st.close();
            con.close();
        } catch (Exception e) {
            
            LOGGER.warn(e);
        }
        return equivalencias;
    }
    public Equivalencias buscaEquivalencia(IngresosAlmacenDetalle ingresosAlmacenDetalle){
        Equivalencias equivalencias = new Equivalencias();
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select e.VALOR_EQUIVALENCIA from EQUIVALENCIAS e " +
                    " where (e.COD_UNIDAD_MEDIDA = '"+ingresosAlmacenDetalle.getOrdenesCompraDetalle().getUnidadesMedida().getCodUnidadMedida()+"' " +
                    " and e.COD_UNIDAD_MEDIDA2 = '"+ingresosAlmacenDetalle.getUnidadesMedida().getCodUnidadMedida()+"') " ;

            
            LOGGER.info("consulta" + consulta);
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs=st.executeQuery(consulta);
            if(rs.next()){
                equivalencias.setValorEquivalencia(rs.getFloat("VALOR_EQUIVALENCIA"));
                equivalencias.getUnidadesMedida1().setAbreviatura(ingresosAlmacenDetalle.getOrdenesCompraDetalle().getUnidadesMedida().getAbreviatura());
                equivalencias.getUnidadesMedida2().setAbreviatura(ingresosAlmacenDetalle.getUnidadesMedida().getAbreviatura());
            }
            //inicio alejandro 4
            else
            {
             consulta = " select e.VALOR_EQUIVALENCIA from EQUIVALENCIAS e  where " +
                    " (e.COD_UNIDAD_MEDIDA = '"+ ingresosAlmacenDetalle.getUnidadesMedida().getCodUnidadMedida()+"' " +
                    " and e.COD_UNIDAD_MEDIDA2 = '"+ingresosAlmacenDetalle.getOrdenesCompraDetalle().getUnidadesMedida().getCodUnidadMedida()+"') ";
             
             LOGGER.info("consulta "+consulta);
             rs=st.executeQuery(consulta);

             if(rs.next())
             {
                 equivalencias.setValorEquivalencia(1/rs.getFloat("VALOR_EQUIVALENCIA"));
                equivalencias.getUnidadesMedida1().setAbreviatura(ingresosAlmacenDetalle.getOrdenesCompraDetalle().getUnidadesMedida().getAbreviatura());
                equivalencias.getUnidadesMedida2().setAbreviatura(ingresosAlmacenDetalle.getUnidadesMedida().getAbreviatura());
             }
            }
            //final alejandro 4
            if(ingresosAlmacenDetalle.getUnidadesMedida().getCodUnidadMedida()==ingresosAlmacenDetalle.getOrdenesCompraDetalle().getUnidadesMedida().getCodUnidadMedida()){
                equivalencias.setValorEquivalencia(1);
                equivalencias.getUnidadesMedida2().setAbreviatura(ingresosAlmacenDetalle.getUnidadesMedida().getAbreviatura());
            }

            rs.close();
            st.close();
            con.close();
        } catch (Exception e) {
            
            LOGGER.warn(e);
        }
        return equivalencias;
    }
    public String detallarIngresosAlmacenDetalleEditarAction(){
         try {
             ingresosAlmacenDetalleEstado = new IngresosAlmacenDetalleEstado();
             DateTime fechaVencimiento =  new DateTime(ingresosAlmacenDetalleEstado.getFechaManufactura());
             fechaVencimiento = fechaVencimiento.plusMonths(6);
             fechaVencimiento = fechaVencimiento.dayOfMonth().withMaximumValue();
             Equivalencias equivalencias = this.buscaEquivalencia(ingresosAlmacenDetalle);
             float cantidadEquivalenteDocumento;
             if (ingresosAlmacenDetalle.getUnidadesMedida().getCodUnidadMedida()!=ingresosAlmacenDetalle.getOrdenesCompraDetalle().getUnidadesMedida().getCodUnidadMedida()) {
                 cantidadEquivalenteDocumento = ingresosAlmacenDetalle.getOrdenesCompraDetalle().getCantidadNeta()*equivalencias.getValorEquivalencia();
             }else{
                 cantidadEquivalenteDocumento = ingresosAlmacenDetalle.getOrdenesCompraDetalle().getCantidadNeta();
             }
             ingresosAlmacenDetalle.setCantEquivalente(cantidadEquivalenteDocumento);
             ingresosAlmacenDetalle.getUnidadesMedidaEquivalencia().setAbreviatura(equivalencias.getUnidadesMedida2().getAbreviatura());
             ingresosAlmacenDetalle.setValorEquivalencia(equivalencias.getValorEquivalencia());
             ingresosAlmacenDetalleEstado.setEstadosMaterial(this.estadoMaterial(ingresosAlmacenDetalle.getMateriales()));
             //ingresosAlmacenDetalleEstado.setFechaVencimiento(fechaVencimiento.toDate());
             
         } catch (Exception e) {
             
             LOGGER.warn(e);
         }
         return null;
     }
    public String detallarItem_action(){
         try {
             ingresosAlmacenDetalle = (IngresosAlmacenDetalle)ingresosAlmacenDetalleDataTable.getRowData();
             //aqui las unidades de medida
             ingresosAlmacenDetalleEstado = new IngresosAlmacenDetalleEstado();
             DateTime fechaVencimiento =  new DateTime(ingresosAlmacenDetalleEstado.getFechaManufactura());
             fechaVencimiento = fechaVencimiento.plusMonths(6);
             fechaVencimiento = fechaVencimiento.dayOfMonth().withMaximumValue();
             Equivalencias equivalencias = this.buscaEquivalencia(ingresosAlmacenDetalle);
             float cantidadEquivalenteDocumento;
             if (ingresosAlmacenDetalle.getUnidadesMedida().getCodUnidadMedida()!=ingresosAlmacenDetalle.getOrdenesCompraDetalle().getUnidadesMedida().getCodUnidadMedida()) {
                 cantidadEquivalenteDocumento = ingresosAlmacenDetalle.getOrdenesCompraDetalle().getCantidadNeta()*equivalencias.getValorEquivalencia();
             }else{
                 cantidadEquivalenteDocumento = ingresosAlmacenDetalle.getOrdenesCompraDetalle().getCantidadNeta();
             }
             ingresosAlmacenDetalle.setCantEquivalente(cantidadEquivalenteDocumento);
             ingresosAlmacenDetalle.getUnidadesMedidaEquivalencia().setAbreviatura(equivalencias.getUnidadesMedida2().getAbreviatura());
             ingresosAlmacenDetalle.setValorEquivalencia(equivalencias.getValorEquivalencia());
             ingresosAlmacenDetalleEstado.setEstadosMaterial(this.estadoMaterial(ingresosAlmacenDetalle.getMateriales()));
             //ingresosAlmacenDetalleEstado.setFechaVencimiento(fechaVencimiento.toDate());
             
         } catch (Exception e) {
             
             LOGGER.warn(e);
         }
         return null;
     }
    public String validarCantidadIngresoFisicoOrdenCompra_action() {
        mensaje="";
        System.out.println("---Oc:"+ingresosAlmacen.getOrdenesCompra().getCodOrdenCompra());
        if (ingresosAlmacen.getOrdenesCompra().getCodOrdenCompra() != 0) {
            float cantidadTotalActual = ingresosAlmacenDetalle.getCantTotalIngresoFisico() + ingresosAlmacenDetalle.getOrdenesCompraDetalle().getSumaParcialCantidadesIngresosDetalles();
            if (cantidadTotalActual > ingresosAlmacenDetalle.getCantEquivalente()) {
                mensaje="El Ingreso Fisico suma un total de: "+ingresosAlmacenDetalle.getCantTotalIngresoFisico()+"["+ingresosAlmacenDetalle.getUnidadesMedida().getAbreviatura()
                        +"], no puede superar la Cantidad Equivalente de Orden de Compra: "+ingresosAlmacenDetalle.getCantEquivalente()+"["+ingresosAlmacenDetalle.getUnidadesMedidaEquivalencia().getAbreviatura()+"]";
                if (ingresosAlmacenDetalle.getOrdenesCompraDetalle().getSumaParcialCantidadesIngresosDetalles() > 0.0f) {
                    mensaje+=". La suma de los anteriores Ingresos relacionados a esta Orden de Compra son: "+ingresosAlmacenDetalle.getOrdenesCompraDetalle().getSumaParcialCantidadesIngresosDetalles()+"["+ingresosAlmacenDetalle.getUnidadesMedida().getAbreviatura()+"]";
                }
            }   
        }
        return null;
    }
    public int cantidadMesesParaVencimiento(String fechaVencimientoMMYY){
        Connection con1 = null;
        int cantidadMesesVencimiento=0;
        try {
            con1 = Util.openConnection(con1);
            SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
            StringBuilder consulta = new StringBuilder("set dateformat dmy;")
                                                .append("select DATEDIFF(MONTH,'01/"+sdf.format(new Date())+"','01/"+fechaVencimientoMMYY+"');")
                                                .append("set dateformat ymd;");
            LOGGER.debug("consulta cargar diferencia fechas: "+consulta.toString());
            Statement st=con1.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet res=st.executeQuery(consulta.toString());
            while(res.next()){
                cantidadMesesVencimiento = res.getInt(1);
            }
        } catch (SQLException ex) {
            LOGGER.warn(ex);
        } catch (Exception ex) {
            LOGGER.warn(ex);
        } finally {
            this.cerrarConexion(con1);
        }
        return cantidadMesesVencimiento;
    }
    public String repetirValoresIngresoAlmacenDetalleEstado_action(){
        
        mensaje = "";
        try {
            /*if (this.cantidadMesesParaVencimiento(ingresosAlmacenDetalleEstado.getFechaVencimientoFormatoMMYY()) < this.CANTIDAD_MESES_MINIMO_VENCIMIENTO
                 && (ingresosAlmacenDetalle.getMateriales().getGrupos().getGruposFechaVencimiento().getAplicaFechaVencimiento() ||
                        ingresosAlmacenDetalle.getAplicaFechaVencimiento())) {
                mensaje = " En la fecha de vencimiento debe tener minimamente "+this.CANTIDAD_MESES_MINIMO_VENCIMIENTO+" meses de vigencia ";
                return null;
            }*/
            
            Iterator i = ingresosAlmacenDetalle.getIngresosAlmacenDetalleEstadoList().iterator();
            String nombreEmpaqueSecundario = this.buscaEmpaqueSecundarioExterno(ingresosAlmacenDetalleEstado.getEmpaqueSecundarioExterno().getCodEmpaqueSecundarioExterno());
            String nombreEstanteAmbiente = this.buscaEstanteAmbiente(ingresosAlmacenDetalleEstado.getEstanteAlmacen().getCodEstante());
            ingresosAlmacenDetalle.setCantTotalIngresoFisico(0);
            while(i.hasNext()){
                IngresosAlmacenDetalleEstado copia = (IngresosAlmacenDetalleEstado)i.next();
                copia.setFechaVencimientoFormatoMMYY(ingresosAlmacenDetalleEstado.getFechaVencimientoFormatoMMYY());
                copia.getEmpaqueSecundarioExterno().setCodEmpaqueSecundarioExterno(ingresosAlmacenDetalleEstado.getEmpaqueSecundarioExterno().getCodEmpaqueSecundarioExterno());
                copia.getEmpaqueSecundarioExterno().setNombreEmpaqueSecundarioExterno(nombreEmpaqueSecundario);
                copia.setCantidadParcial(ingresosAlmacenDetalleEstado.getCantidadParcial());
                if(conDensidad == true)copia.setCantidadParcial(ingresosAlmacenDetalleEstado.getCantidadParcial()*ingresosAlmacenDetalleEstado.getDensidad());
                copia.setLoteMaterialProveedor(ingresosAlmacenDetalleEstado.getLoteMaterialProveedor());
                copia.setFechaManufactura(ingresosAlmacenDetalleEstado.getFechaManufactura());
                copia.setFechaVencimiento(ingresosAlmacenDetalleEstado.getFechaVencimiento());
                copia.setFechaReanalisis(ingresosAlmacenDetalleEstado.getFechaReanalisis());
                copia.setTara(ingresosAlmacenDetalleEstado.getTara());
                copia.setLoteProveedorEncontradoIngresoAnterior(ingresosAlmacenDetalleEstado.isLoteProveedorEncontradoIngresoAnterior());
                copia.setEstadosMaterialSelectList(ingresosAlmacenDetalleEstado.getEstadosMaterialSelectList());
                copia.setDensidad(ingresosAlmacenDetalleEstado.getDensidad());
                if(copia.getChecked()==true){
                    copia.getEstanteAlmacen().setCodEstante(ingresosAlmacenDetalleEstado.getEstanteAlmacen().getCodEstante());
                    copia.getEstanteAlmacen().setNombreEstante(nombreEstanteAmbiente);
                    copia.setFila(ingresosAlmacenDetalleEstado.getFila());
                    copia.setColumna(ingresosAlmacenDetalleEstado.getColumna());
                }
                ingresosAlmacenDetalle.setCantTotalIngresoFisico(ingresosAlmacenDetalle.getCantTotalIngresoFisico()+copia.getCantidadParcial());
            }
        } catch (Exception e) {
            LOGGER.warn(e);
        }
        return null;
    }
    
    
    public String loteMaterialProveedorOnBlur() throws SQLException{
        ingresosAlmacenDetalleEstado.setMateriales(ingresosAlmacenDetalle.getMateriales());
        DaoIngresosAlmacenDetalleEstado daoDetalleEstado = new DaoIngresosAlmacenDetalleEstado(LOGGER);
        daoDetalleEstado.verificarLoteMaterialProveedorAnterior(ingresosAlmacenDetalleEstado);
        if(!ingresosAlmacenDetalleEstado.isLoteProveedorEncontradoIngresoAnterior())
        {
            ingresosAlmacenDetalleEstado.setEstadosMaterial(this.estadoMaterial(ingresosAlmacenDetalle.getMateriales()));
        }
        return null;
    }
    public String loteMaterialProveedorSeleccionadoOnBlur()
    {
        DaoIngresosAlmacenDetalleEstado daoDetalleEstado = new DaoIngresosAlmacenDetalleEstado(LOGGER);
        daoDetalleEstado.verificarLoteMaterialProveedorAnterior(ingresosAlmacenDetalleEstadoSeleccionado);
        if(!ingresosAlmacenDetalleEstadoSeleccionado.isLoteProveedorEncontradoIngresoAnterior())
        {
            ingresosAlmacenDetalleEstadoSeleccionado.setEstadosMaterial(this.estadoMaterial(ingresosAlmacenDetalle.getMateriales()));
        }
        return null;
    }

    public String generarEtiquetas_action(){
         try {
             ingresosAlmacenDetalle.getIngresosAlmacenDetalleEstadoList().clear();
             System.out.println("ingresosAlmacenDetalle.getNroUnidadesEmpaque()" + ingresosAlmacenDetalle.getNroUnidadesEmpaque());
             String nombreEmpaqueSecundario = this.buscaEmpaqueSecundarioExterno(ingresosAlmacenDetalleEstado.getEmpaqueSecundarioExterno().getCodEmpaqueSecundarioExterno());

             for(int i=1;i<=ingresosAlmacenDetalle.getNroUnidadesEmpaque();i++){
                 IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstadoItem = new IngresosAlmacenDetalleEstado();
                 ingresosAlmacenDetalleEstadoItem.setEtiqueta(i);
                 ingresosAlmacenDetalleEstadoItem.setEstadosMaterial(this.estadoMaterial(ingresosAlmacenDetalle.getMateriales()));
                 //ingresosAlmacenDetalleEstadoItem.getEstadosMaterial().setNombreEstadoMaterial("CUARENTENA");
                 ingresosAlmacenDetalleEstadoItem.getEmpaqueSecundarioExterno().setCodEmpaqueSecundarioExterno(ingresosAlmacenDetalleEstado.getEmpaqueSecundarioExterno().getCodEmpaqueSecundarioExterno());
                 ingresosAlmacenDetalleEstadoItem.getEmpaqueSecundarioExterno().setNombreEmpaqueSecundarioExterno(nombreEmpaqueSecundario);
                 ingresosAlmacenDetalleEstadoItem.setCantidadParcial(0);
                 ingresosAlmacenDetalleEstadoItem.setFechaManufactura(ingresosAlmacenDetalleEstado.getFechaManufactura());
                 ingresosAlmacenDetalleEstadoItem.setLoteMaterialProveedor("");
                 ingresosAlmacenDetalleEstadoItem.setFechaVencimiento(ingresosAlmacenDetalleEstado.getFechaVencimiento());
                 ingresosAlmacenDetalleEstadoItem.setFechaReanalisis(new Date());
                 ingresosAlmacenDetalleEstadoItem.setObservaciones("");
                 ingresosAlmacenDetalleEstadoItem.setMateriales(ingresosAlmacenDetalle.getMateriales());
                 ingresosAlmacenDetalle.getIngresosAlmacenDetalleEstadoList().add(ingresosAlmacenDetalleEstadoItem);
             }
         } catch (Exception e) {
             
             LOGGER.warn(e);
         }
         return null;
     }
    public EstadosMaterial estadoMaterial(Materiales material){
        EstadosMaterial estadosMaterial = new EstadosMaterial();
        try {
            if(material.getMaterialProduccion()==1 && material.getMaterialMuestreo()==1 && (ingresosAlmacen.getTiposIngresoAlmacen().getCodTipoIngresoAlmacen()!=8)){
                estadosMaterial.setCodEstadoMaterial(1);
                estadosMaterial.setNombreEstadoMaterial("CUARENTENA");
            }else{
                estadosMaterial.setCodEstadoMaterial(2);
                estadosMaterial.setNombreEstadoMaterial("APROBADO");
            }
        } catch (Exception e) {
            
            LOGGER.warn(e);
        }
        return estadosMaterial;
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
             
             LOGGER.warn(e);
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
             
             LOGGER.warn(e);
         }
         return nombreEstante;
     }

    public void cargarEmpaques() {
        try {
            Connection con = null;
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
                con.close();
            }

        } catch (Exception e) {
            
            LOGGER.warn(e);
        }
    }

    public boolean verificaTransaccionCerradaAlmacen(){


         boolean transaccionCerradaAlmacen = false;
         Date fechaActual = new Date();
         SimpleDateFormat sdf = new SimpleDateFormat("MM");
         try {
             Connection con = null;
             con = Util.openConnection(con);
           
             String consulta = " select * from ESTADOS_TRANSACCIONES_ALMACEN " +
                     " where cod_mes = '"+Integer.valueOf(sdf.format(fechaActual))+"'  " +
                     " and COD_GESTION = "+codGestion+
                     " and estado = 1 ";
             LOGGER.info("consulta " + consulta);
             Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             ResultSet rs = st.executeQuery(consulta);
             if(rs.next()){
                 transaccionCerradaAlmacen= true;
             }
             con.close();
         } catch (Exception e) {
             LOGGER.warn(e);
         }
         return transaccionCerradaAlmacen ;

     }
    public int generaCodIngresoAlmacen(){
         int codIngresoAlmacen = 0;
         try {
             Connection con = null;
             con = Util.openConnection(con);
                 String consulta ="  select (isnull(max(cod_ingreso_almacen),0)+1) cod_ingreso_almacen  " +
                         " from ingresos_almacen  ";
              
              LOGGER.info("consulta " + consulta);
             Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             ResultSet rs = st.executeQuery(consulta);
             if(rs.next()){
                 codIngresoAlmacen = rs.getInt("cod_ingreso_almacen");
             }

         } catch (Exception e) {
             
             LOGGER.warn(e);
         }
         return codIngresoAlmacen;
     }
    public boolean existeDetalleIngresoDetalleAlmacen(List ingresosAlmacenDetalleList){
        boolean existeDetalle = true;
        try {
            Iterator i = ingresosAlmacenDetalleList.iterator();
            while(i.hasNext()){
                IngresosAlmacenDetalle ingresosAlmacenDetalleItem = (IngresosAlmacenDetalle)i.next();
                System.out.println("incd "+ingresosAlmacenDetalleItem.getMateriales().getNombreMaterial());
                if(ingresosAlmacenDetalleItem.getIngresosAlmacenDetalleEstadoList().size()==0){
                    existeDetalle = false;
                    break;
                }

            }
        } catch (Exception e) {
            
            LOGGER.warn(e);
        }
        return existeDetalle;
    }

    public String guardarIngresoAlmacen_action(){
        Connection con = null;
         try {
             mensaje = "";
             con = Util.openConnection(con);
             SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
             if(this.verificaTransaccionCerradaAlmacen()==true){
                 this.mostrarMensajeTransaccionFallida("Las transacciones para este mes fueron cerradas ");
                 return null;
             }
             if(ingresosAlmacenDetalleList.size()==0){
                 this.mostrarMensajeTransaccionFallida(" No existe detalle de ingreso ");
                 return null;
             }
             if(existeDetalleIngresoDetalleAlmacen(ingresosAlmacenDetalleList)==false){
                 this.mostrarMensajeTransaccionFallida(" No existe detalle de Cada Item ");
                 return null;
             }
             if(ingresosAlmacen.getTiposIngresoAlmacen().getCodTipoIngresoAlmacen()!=8&&ingresosAlmacen.getTiposIngresoAlmacen().getCodTipoIngresoAlmacen()!=3&&ingresosAlmacen.getProveedores().getCodProveedor().equals("0")){
                 this.mostrarMensajeTransaccionFallida(" Registre el proveedor ");
                 return null;
             }
             
             // inicio alejandro 3
               Iterator j = ingresosAlmacenDetalleList.iterator();
               while(j.hasNext()){
                   IngresosAlmacenDetalle ingresosAlmacenDetalleItem = (IngresosAlmacenDetalle)j.next();
                   Iterator iterator = ingresosAlmacenDetalleItem.getIngresosAlmacenDetalleEstadoList().iterator();
                   while(iterator.hasNext()){
                         IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstadoItem = (IngresosAlmacenDetalleEstado)iterator.next();
                       if(ingresosAlmacenDetalleEstadoItem.getCantidadParcial()<=0){
                           this.mostrarMensajeTransaccionFallida(" Todos los detalles tienen que ser mayores que 0");
                           return null;
                       }

                   }
               }

             // final alejandro 3


             ingresosAlmacen.setCodIngresoAlmacen(this.generaCodIngresoAlmacen());
             //ingresosAlmacen.getTiposIngresoAlmacen().setCodTipoIngresoAlmacen(1);//con orden de compra
             ingresosAlmacen.getEstadosIngresoAlmacen().setCodEstadoIngresoAlmacen(1);
             ingresosAlmacen.setFechaIngresoAlmacen(new Date());
             ingresosAlmacen.setNroIngresoAlmacen(obtieneNroIngresoAlmacen(usuario));
             //ingresosAlmacen.setCreditoFiscalSiNo(1);


             ingresosAlmacen.getTiposDocumentos().setCodTipoDocumento(ingresosAlmacen.getCreditoFiscalSiNo()==1?"1":"2");
             //ingresosAlmacen.setTiposCompra(ordenesCompra.getTiposCompra());
             ingresosAlmacen.getPersonal().setCodPersonal(usuario.getUsuarioModuloBean().getCodUsuarioGlobal());
             ingresosAlmacen.getEstadosIngresosLiquidacion().setCodEstadoIngresoLiquidacion(2); //no liquidado
             ingresosAlmacen.setAlmacenes(usuario.getAlmacenesGlobal());



             //credito fiscal si no
             //proveedor
             //tipo compra
             ingresosAlmacen.setEstadoSistema(1);
             con.setAutoCommit(false);
             String consulta = " insert into ingresos_almacen (cod_ingreso_almacen,nro_ingreso_almacen,cod_tipo_ingreso_almacen, " +
                     " cod_orden_compra,cod_gestion,cod_estado_ingreso_almacen, " +
                     " credito_fiscal_si_no,cod_proveedor, " +
                     " cod_tipo_compra,estado_sistema,cod_almacen,cod_devolucion,fecha_ingreso_almacen, " +
                     " cod_tipo_documento,cod_personal,cod_estado_ingreso_liquidacion,obs_ingreso_almacen,NRO_DOCUMENTO)  " +
                     " values  ('"+ingresosAlmacen.getCodIngresoAlmacen()+"'," +
                     
                     " (select (isnull(max(nro_ingreso_almacen),0)+1) nro_ingreso_almacen  from ingresos_almacen where cod_gestion='"+codGestion+"' " +
                     " and estado_sistema=1 and cod_almacen='"+usuario.getAlmacenesGlobal().getCodAlmacen()+"'), " +
                     " '"+ingresosAlmacen.getTiposIngresoAlmacen().getCodTipoIngresoAlmacen()+"'," +
                     " '"+ingresosAlmacen.getOrdenesCompra().getCodOrdenCompra() +"'," +
                     
                     " '"+codGestion+"','"+ingresosAlmacen.getEstadosIngresoAlmacen().getCodEstadoIngresoAlmacen()+"'," +
                     " '"+ingresosAlmacen.getCreditoFiscalSiNo()+"', " +
                     " '"+ingresosAlmacen.getProveedores().getCodProveedor() +"', " +
                     " '"+ingresosAlmacen.getTiposCompra().getCodTipoCompra()+"'," +
                     " '"+ingresosAlmacen.getEstadoSistema() +"', " +
                     " '"+usuario.getAlmacenesGlobal().getCodAlmacen() +"',0 ," +
                     " '"+sdf.format(ingresosAlmacen.getFechaIngresoAlmacen())+"'," +
                     " '"+ingresosAlmacen.getTiposDocumentos().getCodTipoDocumento()+"'," +
                     " '"+ingresosAlmacen.getPersonal().getCodPersonal()+"', " +
                     " '"+ingresosAlmacen.getEstadosIngresosLiquidacion().getCodEstadoIngresoLiquidacion()+"','"+ingresosAlmacen.getObsIngresoAlmacen()+"','"+ingresosAlmacen.getNroDocumento()+"')  ";
               LOGGER.info("consulta " + consulta);
               Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
               st.executeUpdate(consulta);

               Iterator i = ingresosAlmacenDetalleList.iterator();
               while(i.hasNext()){
                   IngresosAlmacenDetalle ingresosAlmacenDetalleItem = (IngresosAlmacenDetalle)i.next();
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
                   
                    LOGGER.info("consulta " + consulta);
                    st.executeUpdate(consulta);
                    boolean registrarFechaVencimiento = (ingresosAlmacenDetalleItem.getMateriales().getGrupos().getGruposFechaVencimiento().getAplicaFechaVencimiento()
                                                                || ingresosAlmacenDetalleItem.getAplicaFechaVencimiento());
                    LOGGER.info("registrar fecha de vencimiento grupo: "+ingresosAlmacenDetalleItem.getMateriales().getGrupos().getGruposFechaVencimiento().getAplicaFechaVencimiento());
                    LOGGER.info("registrar fecha de vencimiento item: "+ingresosAlmacenDetalleItem.getAplicaFechaVencimiento());
                    Iterator iterator = ingresosAlmacenDetalleItem.getIngresosAlmacenDetalleEstadoList().iterator();
                    while(iterator.hasNext()){
                       IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstadoItem = (IngresosAlmacenDetalleEstado)iterator.next();
                       ingresosAlmacenDetalleEstadoItem.setCantidadRestante(ingresosAlmacenDetalleEstadoItem.getCantidadParcial());
                       //en caso de proveedor cofar deben ingresar como aprobados
                       if(ingresosAlmacen.getProveedores().getCodProveedor().equals("160"))
                       {
                           ingresosAlmacenDetalleEstadoItem.getEstadosMaterial().setCodEstadoMaterial(2);
                       }
                        String[] fechaSplit = (registrarFechaVencimiento ?ingresosAlmacenDetalleEstadoItem.getFechaVencimientoFormatoMMYY().split("/"):null);
                        consulta = " insert into ingresos_almacen_detalle_estado " +
                           " (etiqueta,cod_ingreso_almacen,cod_material,cod_estado_material, " +
                           " cod_empaque_secundario_externo,cantidad_parcial,cantidad_restante, " +
                           " fecha_vencimiento,lote_material_proveedor,lote_interno,fecha_manufactura, " +
                           " fecha_reanalisis,observaciones,obs_control_calidad,tara,cod_estante,fila,columna,densidad)values(" +
                           " '"+ingresosAlmacenDetalleEstadoItem.getEtiqueta()+"', " +
                           " '"+ingresosAlmacen.getCodIngresoAlmacen()+"'," +
                           " '"+ingresosAlmacenDetalleItem.getMateriales().getCodMaterial()+"'," +
                           " '"+ingresosAlmacenDetalleEstadoItem.getEstadosMaterial().getCodEstadoMaterial() +"'," +
                           " '"+ingresosAlmacenDetalleEstadoItem.getEmpaqueSecundarioExterno().getCodEmpaqueSecundarioExterno()+"'," +
                           " '"+ingresosAlmacenDetalleEstadoItem.getCantidadParcial()+"'," +
                           " '"+ingresosAlmacenDetalleEstadoItem.getCantidadRestante()+"'," +
                           (registrarFechaVencimiento? "dbo.FN_VENCIMIENTO_INGRESO_ALMACEN('"+fechaSplit[1]+"/"+fechaSplit[0]+"/01'),":" null,")+
                           " '"+ingresosAlmacenDetalleEstadoItem.getLoteMaterialProveedor()+"'," +
                           " '"+ingresosAlmacenDetalleEstadoItem.getLoteInterno()+"'," +
                           (ingresosAlmacenDetalleEstadoItem.getFechaManufactura()!=null?" '"+sdf.format(ingresosAlmacenDetalleEstadoItem.getFechaManufactura())+"',":"'',")+
                           (ingresosAlmacenDetalleEstadoItem.getFechaReanalisis()!=null?" '"+sdf.format(ingresosAlmacenDetalleEstadoItem.getFechaReanalisis()) +"',":"'',")+
                           " '"+ingresosAlmacenDetalleEstadoItem.getObservaciones()+"'," +
                           " '"+ingresosAlmacenDetalleEstadoItem.getObsControlCalidad()+"'," +
                           " '"+ingresosAlmacenDetalleEstadoItem.getTara()+"'," +
                           " '"+ingresosAlmacenDetalleEstadoItem.getEstanteAlmacen().getCodEstante()+"'," +
                           " '"+ingresosAlmacenDetalleEstadoItem.getFila()+"'," +
                           " '"+ingresosAlmacenDetalleEstadoItem.getColumna()+"','"+ingresosAlmacenDetalleEstadoItem.getDensidad()+"')  ";
                      LOGGER.info("consulta " + consulta);
                       st.executeUpdate(consulta);
                   }                               
               }
               //REGISTRO DE LOGS DEL NUEVO INGRESO
                consulta = " exec [PAA_REGISTRO_INGRESO_ALMACEN_LOG] " + ingresosAlmacen.getCodIngresoAlmacen() 
                    + " , "+ usuario.getUsuarioModuloBean().getCodUsuarioGlobal()
                    + " , 3";                    
                LOGGER.info("consulta ejecutar: " + consulta);            
                st.executeUpdate(consulta);        
               st.close();
               con.commit();
               this.mostrarMensajeTransaccionExitosa("Se registro satisfactoriamente el ingreso");
         } 
         catch(SQLException ex){
             this.mostrarMensajeTransaccionFallida("Ocurrio un error al momento de registrar el ingreso, intente de nuevo");
             LOGGER.warn("error", ex);
             try{
                con.rollback();
             }catch(SQLException lex){
                 LOGGER.warn(lex);
             }
         }
         catch (Exception e) {
             this.mostrarMensajeTransaccionFallida("Ocurrio un error al momento de registrar el ingreso, intente de nuevo");
             LOGGER.warn(e);
             try{
                con.rollback();
             }catch(SQLException lex){
                 LOGGER.warn(lex);
             }
         }
         finally{
             try{
                 con.close();
             }
             catch(Exception ex){
                 LOGGER.warn(ex);
             }
         }
         //actualización de costos
          if(ingresosAlmacen.getTiposIngresoAlmacen().getCodTipoIngresoAlmacen()!=2 && mensaje.equals("1")){
            //no se toma los ingreso sin orden de compra
            ThreadCosteoByOrdenCompra thread = new ThreadCosteoByOrdenCompra(ingresosAlmacenDetalleList,ingresosAlmacen);
            thread.start();
          }
         return null;
     }
    public void notificaMaterialMantenimiento(List ingresosAlmacenDetalleList){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            System.out.println("entro a notificar correo");
            if(ingresosAlmacen.getAlmacenes().getCodAlmacen()!=4){
                return;
            }
            Iterator i = ingresosAlmacenDetalleList.iterator();
            String codMaterial = "0";
            IngresosAlmacenDetalle ingresosAlmacenDetalle = new IngresosAlmacenDetalle();
            while(i.hasNext()){
                ingresosAlmacenDetalle = (IngresosAlmacenDetalle) i.next();
                codMaterial = codMaterial +","+ingresosAlmacenDetalle.getMateriales().getCodMaterial();
            }
            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement();
            String consulta = " select s.nro_orden_trabajo from SOLICITUDES_MANTENIMIENTO s" +
                    " inner join  SOLICITUDES_MANTENIMIENTO_DETALLE_MATERIALES sd on sd.COD_SOLICITUD_MANTENIMIENTO = s.COD_SOLICITUD_MANTENIMIENTO" +
                    " where sd.COD_MATERIAL  in("+codMaterial+") and s.COD_ESTADO_SOLICITUD_MANTENIMIENTO = 5 ";
            
            LOGGER.info("consulta " + consulta);
            ResultSet rs = st.executeQuery(consulta);
            boolean notificar = false;
            String nroOrdenTrabajo = "";
            while(rs.next()){
                nroOrdenTrabajo = nroOrdenTrabajo + rs.getString("nro_orden_trabajo");
                notificar = true;
            }
            String contenido = " <b>Se genero el siguiente ingreso a Almacen de Mantenimiento para la Orden de Trabajo Nro"+nroOrdenTrabajo+": </b> </b>  <br/> ";
            contenido = contenido + "<div align='center'><table border='0' style='text-align:left' style = 'font-family: Verdana, Arial, Helvetica, sans-serif;font-size: 10px;border : solid #000000 1px;' cellpadding='0' cellspacing='0'> "+
                    " <tr style='border : solid #000000 1px;'><td rowspan='4'></td><td><b> Nro Ingreso : </b></td> <td style='border : solid #000000 1px;'> "+ingresosAlmacen.getNroIngresoAlmacen()+" </td><td style='border : solid #000000 1px;'><b>Proveedor: </b></td> <td style='border : solid #000000 1px;'> "+ingresosAlmacen.getProveedores().getNombreProveedor()+" </td> </tr>"+
                    " <tr style='border : solid #000000 1px;'><td style='border : solid #000000 1px;'><b>Fecha: </b></td><td> "+ingresosAlmacen.getFechaIngresoAlmacen()+" </td> <td><b>Factura Nro.:</b></td> <td style='border : solid #000000 1px;'>"+ingresosAlmacen.getNroDocumento()+"</td> </tr>"+
                    " <tr style='border : solid #000000 1px;'><td style='border : solid #000000 1px;'><b>Tipo de Ingreso: </b></td> <td style='border : solid #000000 1px;'>"+ingresosAlmacen.getTiposIngresoAlmacen().getNombreTipoIngresoAlmacen()+"</td> <td style='border : solid #000000 1px;'><b> Tipo de Compra: </b></td> <td style='border : solid #000000 1px;'>"+ingresosAlmacen.getTiposCompra().getNombreTipoCompra()+"</td> </tr>"+
                    " <tr style='border : solid #000000 1px;'><td style='border : solid #000000 1px;'><b>Grupo:</b></td><td style='border : solid #000000 1px;'></td> <td style='border : solid #000000 1px;'><b>Nro. O.C.</b></td> <td style='border : solid #000000 1px;'>"+ingresosAlmacen.getOrdenesCompra().getNroOrdenCompra()+"</td> </tr> "+
                    " </table><br/> ";
            i= ingresosAlmacenDetalleList.iterator();
            ingresosAlmacenDetalle = new IngresosAlmacenDetalle();
            contenido =contenido + " <table style='border : solid #f2f2f2 1px;font-family: Verdana, Arial, Helvetica, sans-serif;" +
                            " font-size: 11px;' width='70%' cellpadding='0' cellspacing='0' > "+
                            " <tr style='border : solid #000000 1px;'><td style='border : solid #000000 1px;'> Codigo </td> <td style='border : solid #000000 1px;'> Item </td> " +
                            " <td style='border : solid #000000 1px;'> Cantidad </td> <td style='border : solid #000000 1px;'> Unid. </td></tr>";
            while(i.hasNext()){
                ingresosAlmacenDetalle = (IngresosAlmacenDetalle) i.next();
                contenido = contenido + " <tr style='border : solid #000000 1px;'><td style='border : solid #000000 1px;'> "+ingresosAlmacenDetalle.getMateriales().getCodigoAntoguo()+" </td> <td style='border : solid #000000 1px;'> "+ingresosAlmacenDetalle.getMateriales().getNombreMaterial()+" </td> " +
                            " <td style='border : solid #000000 1px;'> "+ingresosAlmacenDetalle.getCantTotalIngresoFisico()+" </td> <td style='border : solid #000000 1px;'> "+ingresosAlmacenDetalle.getUnidadesMedida().getAbreviatura()+" </td>" +
                            " </tr> ";

            }
            contenido = contenido + "</table></div> <br/><br/><br/> <b style = 'color:#0000FF'>Sistema Baco<b>";
            if(notificar){Util.enviarCorreo("1479",contenido,"Registro de Ingreso a Almacen","Notificacion");}
        } catch (Exception e) {
            
            LOGGER.warn(e);
        }
    }

   /* public void actualizaProgramaProduccion(IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstado,IngresosAlmacenDetalle ingresosAlmacenDetalle){
        try {
            if(ingresosAlmacenDetalle.getMateriales().getGrupos().getCodGrupo()==99){ //grupo de materiales semiprocesadas
            String consulta = "update programa_produccion  set cod_estado_programa = '6' where cod_lote_produccion = '"+ingresosAlmacenDetalleEstado.getLoteMaterialProveedor()+"'";
            
            LOGGER.info("consulta " + consulta);
            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement();
            st.executeUpdate(consulta);
            st.close();
            con.close();
            }
        } catch (Exception e) {
            
            LOGGER.warn(e);
        }
    }*/
    public boolean materialMuestreo(List ingresosAlmacenDetalleList){
        boolean materialMuestreo = false;
        Iterator i = ingresosAlmacenDetalleList.iterator();
        while(i.hasNext()){
            IngresosAlmacenDetalle ingresosAlmacenDetalle = (IngresosAlmacenDetalle) i.next();
            if(ingresosAlmacenDetalle.getMateriales().getMaterialMuestreo()>0){
                materialMuestreo = true;
                break;
            }
        }
        return materialMuestreo;
    }
    public String eliminarIngresoDetalleItemAction()
    {
        ingresosAlmacenDetalleList.remove(indexEliminarDetalleIngreso);
        return null;
    }
    public String eliminarItem_action(){
        try {
            Iterator i = ingresosAlmacenDetalleList.iterator();
            List ingresosAlmacenDetalleAux= new ArrayList();
            while(i.hasNext()){
                IngresosAlmacenDetalle ingresosAlmacenDetalleItem = (IngresosAlmacenDetalle)i.next();
                if(ingresosAlmacenDetalleItem.getChecked().booleanValue()==false){
                    ingresosAlmacenDetalleAux.add(ingresosAlmacenDetalleItem);
                }
            }
            ingresosAlmacenDetalleList.clear();
            ingresosAlmacenDetalleList = ingresosAlmacenDetalleAux;

        } catch (Exception e) {
            
            LOGGER.warn(e);
        }
        return null;
    }
    public String verReporteIngresoAlmacen_action(){
        Iterator i = ingresosAlmacenList.iterator();
        while(i.hasNext()){
            IngresosAlmacen ingresosAlmacenItem = (IngresosAlmacen)i.next();
            if(ingresosAlmacenItem.getChecked().booleanValue()==true){
                ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                Map<String,Object> sessionMap = externalContext.getSessionMap();
                sessionMap.put("ingresosAlmacen",ingresosAlmacenItem);
                break;
            }
        }
        return null;
    }
    public String editarIngresosAlmacen_action(){
        try {
            
            mensaje = "";
            if(this.tieneSalidasAlmacen()==true){
                 mensaje = "El ingreso no se puede modificar por que tiene salidas registradas.";
                 return null;
             }
             if(ingresosAlmacen.getTiposIngresoAlmacen().getCodTipoIngresoAlmacen()==6){
                 mensaje = "No se puede modificar un ingreso por devolucion.";
                 return null;
             }

        } catch (Exception e) {
            LOGGER.warn(e);
        }
        return null;
    }
    public String getCargarEditarIngresosAlmacen(){
        try {
            ingresoTransaccionSolicitudGestionar = new IngresoTransaccionSolicitud();
            ingresosAlmacenDetalleEstado= new IngresosAlmacenDetalleEstado();
            tiposIngresosAlmacenList = cargarTiposIngresoAlmacenEditar();
            tiposCompraList = cargarTiposCompra();
            proveedoresList = obtieneProveedores();
            capitulosList = this.cargarCapitulos();
            gruposList.add(new SelectItem("0", "-NINGUNO-"));
            this.cargarEmpaques();

            ingresosAlmacenDetalleList.clear();
            ingresosAlmacenDetalleList = this.cargarIngresosAlmacenDetalle(ingresosAlmacen);
            ambienteAlmacenList = this.cargarAmbienteAlmacen();

            //quitar los registros con cantidad restante 0
            Iterator i = ingresosAlmacenDetalleList.iterator();
            while(i.hasNext()){
                IngresosAlmacenDetalle iad = (IngresosAlmacenDetalle) i.next();
                Iterator i1 = iad.getIngresosAlmacenDetalleEstadoList().iterator();
                List list1 = new ArrayList();
                while(i1.hasNext()){
                    IngresosAlmacenDetalleEstado iax = (IngresosAlmacenDetalleEstado) i1.next();
                    if(iax.getCantidadRestante()>0.1){
                        System.out.println("entro al detalle estado");
                        list1.add(iax);
                    }
                }
                System.out.println("tamano de lista" + list1.size());
                iad.setIngresosAlmacenDetalleEstadoList(list1);
            }


            
            
        } catch (Exception e) {
            LOGGER.warn(e);
        }
        return null;
    }
    public List cargarIngresosAlmacenDetalle(IngresosAlmacen ingresosAlmacen){
        DaoIngresosAlmacenDetalle daoIngresosDetalle = new DaoIngresosAlmacenDetalle(LOGGER);
        return daoIngresosDetalle.listarPorIngresoAlmacen(ingresosAlmacen);
    }
    
    public List cargarTiposIngresoAlmacenEditar() {
        List tiposIngresosAlmacenList = new ArrayList();
        TiposIngresoAlmacen tipoIngresoAlmacen;
        try {

            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select t.COD_TIPO_INGRESO_ALMACEN,t.NOMBRE_TIPO_INGRESO_ALMACEN "
                            + " from TIPOS_INGRESO_ALMACEN t"
                            + " where t.COD_ESTADO_REGISTRO = 1 ";

            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            tiposIngresosAlmacenList.clear();
            ResultSet rs = st.executeQuery(consulta);
            while (rs.next()) {
                tiposIngresosAlmacenList.add(new SelectItem(rs.getString("COD_TIPO_INGRESO_ALMACEN"), rs.getString("NOMBRE_TIPO_INGRESO_ALMACEN")));
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception e) {

            LOGGER.warn(e);
        }
        return tiposIngresosAlmacenList;
    }
    
    public boolean tieneSalidasAlmacen(){


         boolean tieneSalidasAlmacen = false;
         Date fechaActual = new Date();
         SimpleDateFormat sdf = new SimpleDateFormat("MM");
         try {
             Connection con = null;
             con = Util.openConnection(con);
             String consulta = " select top 1 * from salidas_almacen_detalle_ingreso " +
                     " where cod_ingreso_almacen = '"+ingresosAlmacen.getCodIngresoAlmacen()+"'  ";
             System.out.println("consulta " + consulta);
             Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             ResultSet rs = st.executeQuery(consulta);
             if(rs.next()){
                 tieneSalidasAlmacen= true;
             }
                 
         } catch (Exception e) {
             LOGGER.warn(e);
         }
         return tieneSalidasAlmacen ;

     }
    public String guardarEditarIngresoAlmacen_action()throws SQLException{
         
        mensaje = "";
        this.transaccionExitosa = false;
        if(ingresosAlmacenDetalleList.isEmpty()){
            this.mostrarMensajeTransaccionFallida(" No existe detalle de ingreso ");
            return null;
        }
        if(existeDetalleIngresoDetalleAlmacen(ingresosAlmacenDetalleList)==false){
            this.mostrarMensajeTransaccionFallida(" No existe detalle de Cada Item ");
            return null;
        }
        if(ingresoTransaccionSolicitudGestionar.getObservacionValidador().length()==0){
            this.mostrarMensajeTransaccionFallida("Ingrese el motivo de la edición de este Ingreso.");
            return null;
        }
        
        Connection con = null;
        con = Util.openConnection(con);
        
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            con.setAutoCommit(false);
             
            String consulta;
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            
            // SE ACTUALIZA EL ESTADO DE LA TRANSACCION SI TIENE UNA SOLICITUD
            if (ingresosAlmacen.getIngresoTransaccionSolicitud().getEstadoTransaccionSolicitud().getCodEstadoTransaccionSolicitud() == 2) {
                //Actualizar INGRESO_TRANSACCION_SOLICITUD
                consulta = " UPDATE INGRESO_TRANSACCION_SOLICITUD"
                        + " SET COD_ESTADO_TRANSACCION_SOLICITUD = 7"
                        + " , COD_PERSONAL_VALIDADOR = "+ usuario.getUsuarioModuloBean().getCodUsuarioGlobal()
                        + " , OBSERVACION_VALIDADOR = '"+ ingresoTransaccionSolicitudGestionar.getObservacionValidador() +"' "
                        + " , FECHA_VALIDACION = GETDATE() "
                        + " WHERE COD_INGRESO_ALMACEN = " + ingresosAlmacen.getCodIngresoAlmacen()
                        + " AND COD_INGRESO_TRANSACCION_SOLICITUD = "
                        + " (   SELECT TOP 1 COD_INGRESO_TRANSACCION_SOLICITUD "
                            + " FROM INGRESO_TRANSACCION_SOLICITUD sts"
                            + " WHERE COD_INGRESO_ALMACEN = " + ingresosAlmacen.getCodIngresoAlmacen() + " ORDER BY FECHA_SOLICITUD DESC"
                        + " )";
                LOGGER.info("consulta actualizar: " + consulta);
                st.executeUpdate(consulta);
             }
            
            
            
            //INICIO PROCESO EDITAR INGRESO_SALIDA
            if (ingresosAlmacen.getTiposIngresoAlmacen().isEditable() && ingresoEditableValidandoConSalidas) {
                LOGGER.info("El ingreso es editable.");
                
                //SI TIENE SALIDAS ASOCIADAS LAS ANULAMOS
                if (!salidasAsociadasAlmacenList.isEmpty() ) {
                    for (SalidasAlmacen salidaAlmacen : salidasAsociadasAlmacenList) {
                        
                        //EJECUTAMOS SP 
                        consulta = " exec [USP_ANULAR_SALIDAS_ALMACEN] " + salidaAlmacen.getCodSalidaAlmacen()
                                    + ", " + usuario.getUsuarioModuloBean().getCodUsuarioGlobal() ;
                        LOGGER.info("consulta ejecutar: " + consulta);
                        CallableStatement callStatement = con.prepareCall(consulta);
                        callStatement.execute();
                        
                        //EJECUTAMOS SP DESPUES DE ANULAR
                        String motivoAnulacionSalida =  "Anulación de salida automática por edicion de Ingreso "+ingresosAlmacen.getNroIngresoAlmacen()+". ";
                        consulta = " exec [PAA_REGISTRO_SALIDA_ALMACEN_LOG] " + salidaAlmacen.getCodSalidaAlmacen() 
                                + " , "+ usuario.getUsuarioModuloBean().getCodUsuarioGlobal()
                                + " , 2"
                                + " , '"+motivoAnulacionSalida+"' ";
                        LOGGER.info("consulta ejecutar: " + consulta);            
                        callStatement = con.prepareCall(consulta);
                        callStatement.execute();
                    }                    
                }
                
                //inicio ale orden de compra
                consulta = " UPDATE INGRESOS_ALMACEN  SET "
                        + "CREDITO_FISCAL_SI_NO = '"+ingresosAlmacen.getCreditoFiscalSiNo()
                        +"', obs_ingreso_almacen = '"+ingresosAlmacen.getObsIngresoAlmacen()
                        +"', NRO_DOCUMENTO = '"+ingresosAlmacen.getNroDocumento()+"'" 
                        +", COD_TIPO_INGRESO_ALMACEN = '"+ingresosAlmacen.getTiposIngresoAlmacen().getCodTipoIngresoAlmacen()+ "'" +
                        " where COD_INGRESO_ALMACEN = '"+ingresosAlmacen.getCodIngresoAlmacen()+"'";
                LOGGER.info("consulta: " + consulta);
                st.executeUpdate(consulta);

                     
                consulta="select iad.COD_UNIDAD_MEDIDA,iad.CANT_TOTAL_INGRESO_FISICO,iad.COD_UNIDAD_MEDIDA,iad.COD_MATERIAL,"+
                                 " ocd.COD_UNIDAD_MEDIDA as codUnidadOC,ocd.CANTIDAD_INGRESO_ALMACEN"+
                                 " from INGRESOS_ALMACEN_DETALLE iad inner join ORDENES_COMPRA_DETALLE ocd on "+
                                 " iad.COD_MATERIAL=ocd.COD_MATERIAL"+
                                 " where iad.COD_INGRESO_ALMACEN='"+ingresosAlmacen.getCodIngresoAlmacen()+"'" +
                                 " and ocd.COD_ORDEN_COMPRA='"+ingresosAlmacen.getOrdenesCompra().getCodOrdenCompra()+"'";
                LOGGER.info("consulta buscar ordenes  ingresos oc a "+consulta);
                st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                ResultSet res=st.executeQuery(consulta);
                double cantidadTotalIngreso=0d;
                double cantidadIngresoAlmacen=0d;
                int codUnidadOC=0;
                int codUnidadIA=0;
                double valorEquivalencia=0d;
                Statement stDetalle=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                ResultSet resDetalle=null;
                PreparedStatement pst=null;
                while(res.next())
                {
                    codUnidadIA=res.getInt("COD_UNIDAD_MEDIDA");
                    cantidadTotalIngreso=res.getDouble("CANT_TOTAL_INGRESO_FISICO");
                    cantidadIngresoAlmacen=res.getDouble("CANTIDAD_INGRESO_ALMACEN");
                    codUnidadOC=res.getInt("codUnidadOC");
                    if(codUnidadIA!=codUnidadOC)
                    {
                        consulta=" select e.VALOR_EQUIVALENCIA from EQUIVALENCIAS e" +
                                " where e.COD_UNIDAD_MEDIDA='"+codUnidadIA+"' and e.COD_UNIDAD_MEDIDA2='"+codUnidadOC+"'";
                        LOGGER.info("consulta equi de IA a OC "+consulta);
                        resDetalle=stDetalle.executeQuery(consulta);
                        if(resDetalle.next())
                        {
                               valorEquivalencia=resDetalle.getDouble("VALOR_EQUIVALENCIA");
                               cantidadTotalIngreso=cantidadTotalIngreso*valorEquivalencia;
                        }
                        else
                        {
                            consulta=" select e.VALOR_EQUIVALENCIA from EQUIVALENCIAS e" +
                                " where e.COD_UNIDAD_MEDIDA='"+codUnidadOC+"' and e.COD_UNIDAD_MEDIDA2='"+codUnidadIA+"'";
                            LOGGER.info("consulta equi de Oc a Ia "+consulta);
                            resDetalle=stDetalle.executeQuery(consulta);
                            if(resDetalle.next())
                            {
                                valorEquivalencia=(1d/resDetalle.getDouble("VALOR_EQUIVALENCIA"));
                                cantidadTotalIngreso=cantidadTotalIngreso*valorEquivalencia;
                            }
                            else
                            {
                                System.out.println("no exista equivalencia defecto 1");
                            }

                        }
                    }
                    cantidadIngresoAlmacen-=cantidadTotalIngreso;
                    consulta="update ordenes_compra_detalle set cantidad_ingreso_almacen='"+cantidadIngresoAlmacen+"'"+
                                   " where cod_orden_compra='"+ingresosAlmacen.getOrdenesCompra().getCodOrdenCompra()+"'"+
                                   " and cod_material='"+res.getString("COD_MATERIAL")+"'";
                    LOGGER.info("consulta  update "+consulta);
                    pst=con.prepareStatement(consulta);
                    if(pst.executeUpdate()>0)System.out.println("se decremento la cantidad de ingresos orden de compra");

            
                }
                if(resDetalle != null)
                {resDetalle.close();
                }
                stDetalle.close();
                //final ale orden de compra

                consulta = " DELETE FROM INGRESOS_ALMACEN_DETALLE WHERE COD_INGRESO_ALMACEN = '"+ingresosAlmacen.getCodIngresoAlmacen()+"' ";
                LOGGER.info("consulta " + consulta);
                st.executeUpdate(consulta);
                consulta = " DELETE FROM INGRESOS_ALMACEN_DETALLE_ESTADO WHERE COD_INGRESO_ALMACEN = '"+ingresosAlmacen.getCodIngresoAlmacen()+"' ";
                LOGGER.info("consulta " + consulta);
                st.executeUpdate(consulta);

                //iteracion de detalle
                Iterator i = ingresosAlmacenDetalleList.iterator();

             
                while(i.hasNext()){
                    IngresosAlmacenDetalle ingresosAlmacenDetalleItem = (IngresosAlmacenDetalle)i.next();
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
                        LOGGER.info("consulta " + consulta);
                        st.executeUpdate(consulta);

                        boolean registrarFechaVencimiento = (ingresosAlmacenDetalleItem.getMateriales().getGrupos().getGruposFechaVencimiento().getAplicaFechaVencimiento()
                                                                || ingresosAlmacenDetalleItem.getAplicaFechaVencimiento());
                        Iterator i3 = ingresosAlmacenDetalleItem.getIngresosAlmacenDetalleEstadoList().iterator();
                        System.out.println("----parada 2");
                  
                    while(i3.hasNext()){
                        IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstadoItem = (IngresosAlmacenDetalleEstado) i3.next();
                        String[] fechaSplit = (registrarFechaVencimiento ?ingresosAlmacenDetalleEstadoItem.getFechaVencimientoFormatoMMYY().split("/"):null);
                        consulta = " insert into ingresos_almacen_detalle_estado " +
                        " (etiqueta,cod_ingreso_almacen,cod_material,cod_estado_material, " +
                        " cod_empaque_secundario_externo,cantidad_parcial,cantidad_restante, " +
                        " fecha_vencimiento,lote_material_proveedor,lote_interno,fecha_manufactura, " +
                        " fecha_reanalisis,observaciones,obs_control_calidad,cod_estante,fila,columna)values(" +
                        " '"+ingresosAlmacenDetalleEstadoItem.getEtiqueta()+"', " +
                        " '"+ingresosAlmacen.getCodIngresoAlmacen()+"'," +
                        " '"+ingresosAlmacenDetalleItem.getMateriales().getCodMaterial()+"'," +
                        " '"+ingresosAlmacenDetalleEstadoItem.getEstadosMaterial().getCodEstadoMaterial() +"'," +
                        " '"+ingresosAlmacenDetalleEstadoItem.getEmpaqueSecundarioExterno().getCodEmpaqueSecundarioExterno()+"'," +
                        " '"+ingresosAlmacenDetalleEstadoItem.getCantidadParcial()+"'," +
                        " '"+ingresosAlmacenDetalleEstadoItem.getCantidadRestante()+"'," +
                        (registrarFechaVencimiento? "DATEADD(minute, -  1, DATEADD(mm, 0, DATEADD(mm, DATEDIFF(mm, 0,'"+fechaSplit[1]+"/"+fechaSplit[0]+"/01')+1, 0))),":" null,")+
                        " '"+ingresosAlmacenDetalleEstadoItem.getLoteMaterialProveedor()+"'," +
                        " '"+ingresosAlmacenDetalleEstadoItem.getLoteInterno()+"'," +
                        " '"+sdf.format(ingresosAlmacenDetalleEstadoItem.getFechaManufactura())+"'," +
                        " '"+sdf.format(ingresosAlmacenDetalleEstadoItem.getFechaReanalisis()) +"'," +
                        " '"+ingresosAlmacenDetalleEstadoItem.getObservaciones()+"'," +
                        " '"+ingresosAlmacenDetalleEstadoItem.getObsControlCalidad()+"','"+ingresosAlmacenDetalleEstadoItem.getEstanteAlmacen().getCodEstante()+"','"+ingresosAlmacenDetalleEstadoItem.getFila()+"','"+ingresosAlmacenDetalleEstadoItem.getColumna()+"')  ";
                        LOGGER.info("consulta " + consulta);
                        st.executeUpdate(consulta);

                    }
                }

                       
                if (ingresosAlmacen.getOrdenesCompra().getCodOrdenCompra() > 0) {

                    //inicio ale orden compra
                     Iterator e= ingresosAlmacenDetalleList.iterator();

                       
                    while(e.hasNext())
                    {
                        IngresosAlmacenDetalle ingresosAlmacenDetalle = (IngresosAlmacenDetalle)e.next();
                        codUnidadIA=ingresosAlmacenDetalle.getUnidadesMedida().getCodUnidadMedida();
                        cantidadTotalIngreso=ingresosAlmacenDetalle.getCantTotalIngresoFisico();
                        consulta="select ocd.COD_UNIDAD_MEDIDA,ocd.CANTIDAD_INGRESO_ALMACEN"+
                          " from ORDENES_COMPRA_DETALLE ocd where ocd.COD_ORDEN_COMPRA='"+ingresosAlmacen.getOrdenesCompra().getCodOrdenCompra()+"'" +
                          " and ocd.COD_MATERIAL='"+ingresosAlmacenDetalle.getMateriales().getCodMaterial()+"'";
                        LOGGER.info("consulta: "+consulta);

                        res=st.executeQuery(consulta);
                        if(res.next())
                        {
                            codUnidadOC=res.getInt("COD_UNIDAD_MEDIDA");
                            cantidadIngresoAlmacen=res.getDouble("CANTIDAD_INGRESO_ALMACEN");
                        }
                        if(codUnidadIA!=codUnidadOC)
                        {
                            
                            consulta=" select e.VALOR_EQUIVALENCIA from EQUIVALENCIAS e" +
                                     " where e.COD_UNIDAD_MEDIDA='"+codUnidadIA+"' and e.COD_UNIDAD_MEDIDA2='"+codUnidadOC+"'";
                            LOGGER.info("consulta equi de IA a OC "+consulta);
                            res=st.executeQuery(consulta);
                            if(res.next())
                            {
                                   valorEquivalencia=res.getDouble("VALOR_EQUIVALENCIA");
                                   cantidadTotalIngreso=cantidadTotalIngreso*valorEquivalencia;
                            }
                            else
                            {
                                consulta=" select e.VALOR_EQUIVALENCIA from EQUIVALENCIAS e" +
                                    " where e.COD_UNIDAD_MEDIDA='"+codUnidadOC+"' and e.COD_UNIDAD_MEDIDA2='"+codUnidadIA+"'";
                                LOGGER.info("consulta equi de Oc a Ia "+consulta);
                                res=st.executeQuery(consulta);
                                if(res.next())
                                {
                                    valorEquivalencia=(1d/res.getDouble("VALOR_EQUIVALENCIA"));
                                    cantidadTotalIngreso=cantidadTotalIngreso*valorEquivalencia;
                             
                                }
                                else
                                {
                                    System.out.println("no exista equivalencia defecto 1");

                                }

                            }
                        }
                        cantidadIngresoAlmacen+=cantidadTotalIngreso;
                        consulta="update ordenes_compra_detalle set cantidad_ingreso_almacen='"+cantidadIngresoAlmacen+"'"+
                                 " where cod_orden_compra='"+ingresosAlmacen.getOrdenesCompra().getCodOrdenCompra()+"'"+
                                 " and cod_material='"+ingresosAlmacenDetalle.getMateriales().getCodMaterial()+"'";
                        
                        LOGGER.info("consulta update: "+consulta);
                        pst=con.prepareStatement(consulta);
                        if(pst.executeUpdate()>0)System.out.println("se incremento la cantidad de ingreso almacen de la orden de compra");
                  
                    }
                    consulta=" select ocd.CANTIDAD_INGRESO_ALMACEN,ocd.CANTIDAD_NETA" +
                             " from ORDENES_COMPRA_DETALLE ocd where ocd.COD_ORDEN_COMPRA='"+ingresosAlmacen.getOrdenesCompra().getCodOrdenCompra()+"'";
                    LOGGER.info("consulta detalle OC "+consulta);
                    boolean entregadoTotalmente=true;
                    res=st.executeQuery(consulta);
                    while(res.next())
                    {
                        if((res.getDouble("CANTIDAD_INGRESO_ALMACEN"))<(res.getDouble("CANTIDAD_NETA")))
                        {
                            entregadoTotalmente=false;
                        }
                    }
                    consulta="update ordenes_compra set cod_estado_compra='"+(entregadoTotalmente?"7":"6")+"' where cod_orden_compra='"+ingresosAlmacen.getOrdenesCompra().getCodOrdenCompra()+"'";
                    LOGGER.info("consulta update estado OC "+consulta);
                    pst=con.prepareStatement(consulta);
                    if(pst.executeUpdate()>0)System.out.println("se actualizo el estado de la OC");
                    res.close();
                    //final ale orden compra
                }
            }
            else{
                LOGGER.info("El ingreso NO es editable.");
                consulta = " UPDATE INGRESOS_ALMACEN  SET "
                        + "CREDITO_FISCAL_SI_NO = '"+ingresosAlmacen.getCreditoFiscalSiNo()
                        +"', obs_ingreso_almacen = '"+ingresosAlmacen.getObsIngresoAlmacen()
                        +"', NRO_DOCUMENTO = '"+ingresosAlmacen.getNroDocumento()+"'" 
                        +", COD_TIPO_INGRESO_ALMACEN = '"+ingresosAlmacen.getTiposIngresoAlmacen().getCodTipoIngresoAlmacen()+ "'" +
                        " where COD_INGRESO_ALMACEN = '"+ingresosAlmacen.getCodIngresoAlmacen()+"'";
                LOGGER.info("consulta: " + consulta);
                st.executeUpdate(consulta);
            } 
            //EJECUTAMOS SP DESPUES DE LOS CAMBIOS
            consulta = " exec [PAA_REGISTRO_INGRESO_ALMACEN_LOG] " + ingresosAlmacen.getCodIngresoAlmacen() 
                    + " , "+ usuario.getUsuarioModuloBean().getCodUsuarioGlobal()
                    + " , 1"
                    + " , '"+ingresoTransaccionSolicitudGestionar.getObservacionValidador()+"' ";
            LOGGER.info("consulta ejecutar: " + consulta);            
            st.executeUpdate(consulta);
            
            this.mostrarMensajeTransaccionExitosa("Se registro satisfactoriamente la edición del ingreso");
            con.commit();
            st.close();
            con.close();
            
        } catch (Exception e) {
            LOGGER.warn("error", e);
            this.mostrarMensajeTransaccionFallida("Ocurrio un error al momento de registrar la edición, intente de nuevo");
            con.rollback();
            
        }finally{
            this.cerrarConexion(con);
        }
        //ENVIAMOS CORREO
        if (this.transaccionExitosa && ingresosAlmacen.getIngresoTransaccionSolicitud().getEstadoTransaccionSolicitud().getCodEstadoTransaccionSolicitud() == 2) {
            EnvioCorreoNotificacionValidacionIngresoAlmacen correo = new EnvioCorreoNotificacionValidacionIngresoAlmacen(ingresosAlmacen.getCodIngresoAlmacen());
            correo.start();
        }
        
        ThreadCosteoByCodIngreso costeoIngreso = new ThreadCosteoByCodIngreso(ingresosAlmacen.getCodIngresoAlmacen(), 0, 0);
        costeoIngreso.start();
         
        return null;
     }
    
    public String anularIngresosAlmacen_action1(){
        try {
            IngresosAlmacen ingresosAlmacen = new IngresosAlmacen();
            Iterator i = ingresosAlmacenList.iterator();
            while(i.hasNext()){
                ingresosAlmacen = (IngresosAlmacen)i.next();
                if(ingresosAlmacen.getChecked().booleanValue()==true){
                    break;
                }
            }
            Connection con = null;
            con=Util.openConnection(con);
            Statement st = con.createStatement();
            String consulta = " update ingresos_almacen set cod_estado_ingreso_almacen = 2 where cod_ingreso_almacen = '"+ingresosAlmacen.getCodIngresoAlmacen()+"'";
            
            LOGGER.info("consulta " + consulta);
            st.executeUpdate(consulta);
            ingresosAlmacenList = listadoIngresosAlmacen(begin, end,usuario);
        } catch (Exception e) {
         
            LOGGER.warn(e);
        }
        return null;
    }
    public String cancelarDetalleItem_action(){
         try {
             ingresosAlmacenDetalle.getIngresosAlmacenDetalleEstadoList().clear();

         } catch (Exception e) {
            
             LOGGER.warn(e);
         }
         return null;
     }
    // inicio alejandro

    public List cargarCapitulos2() {
        List capitulosList = new ArrayList();
        try {

            Connection con = null;
            con = Util.openConnection(con);
            String consulta = "  select cod_capitulo, nombre_capitulo from capitulos where cod_estado_registro=1 and capitulo_almacen=1 ";

            ResultSet rs = null;
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            capitulosList.clear();
            rs = st.executeQuery(consulta);
            capitulosList.add(new SelectItem("0", "-TODOS-"));
            while (rs.next()) {
                capitulosList.add(new SelectItem(rs.getString("cod_capitulo"), rs.getString("nombre_capitulo")));
            }

            if (rs != null) {
                rs.close();
                st.close();
                rs = null;
                st = null;
            }

        } catch (Exception e) {
            
            LOGGER.warn(e);
        }
        return capitulosList;
    }
     public String buscarIngresoAlmacen_action(){
        try {
            begin = 1;
            end = 10;
            //this.getCargarIngresosAlmacen();
            ingresosAlmacenList = this.listadoIngresosAlmacen(begin, end, usuario);
            //this.cargarSalidasAlmacen();
        } catch (Exception e) {
            
            LOGGER.warn(e);
        }
        return null;
    }
    public String cargarGrupos(){
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " SELECT GR.COD_GRUPO,GR.NOMBRE_GRUPO FROM GRUPOS GR WHERE GR.COD_CAPITULO = '"+getCodCapitulo()+"' AND GR.COD_ESTADO_REGISTRO = 1 ";
            
            LOGGER.info("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            getGruposList2().clear();
            getGruposList2().add(new SelectItem("0", "-TODOS-"));
            while(rs.next()){
                getGruposList2().add(new SelectItem(rs.getString("COD_GRUPO"), rs.getString("NOMBRE_GRUPO")));
            }
            rs.close();
            con.close();
            materialesList.clear();
            materialesList.add(new SelectItem("0","-TODOS-"));
            codGrupo="0";
            codMaterial="0";

        } catch (Exception e) {
            
            LOGGER.warn(e);
        }
        return null;
    }

     public void cargarProveedores(){
         Connection con=null;
        try {

            String consulta = "select m.cod_proveedor, m.nombre_proveedor from proveedores m where m.cod_estado_registro=1 order by m.nombre_proveedor " ;
            LOGGER.info("consulta " + consulta);
            con = Util.openConnection(con);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);

            getProveedoresList2().clear();
            getProveedoresList2().add(new SelectItem("0","-TODOS-"));
            while(rs.next()){

                getProveedoresList2().add(new SelectItem(rs.getString(1),rs.getString(2)));

            }
            con.close();

        } catch (Exception e) {
			
            LOGGER.warn(e);
        }
    }

    public void cargarEstadosIngresoAlmacen(){
        Connection con=null;
        try {
            con=Util.openConnection(con);
            String sql="  select e.COD_ESTADO_SALIDA_ALMACEN,e.NOMBRE_ESTADO_SALIDA_ALMACEN  " +
                       " from ESTADOS_SALIDAS_ALMACEN e ";

            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs=st.executeQuery(sql);
            getEstadosIngresoAlmacenList().clear();
            getEstadosIngresoAlmacenList().add(new SelectItem("0", "-TODOS-"));
            while (rs.next()){
                getEstadosIngresoAlmacenList().add(new SelectItem(rs.getInt("COD_ESTADO_SALIDA_ALMACEN"),rs.getString("NOMBRE_ESTADO_SALIDA_ALMACEN")));
            }
            if(rs!=null){
                rs.close();
                st.close();
            }
        } catch (Exception e) {
            
            LOGGER.warn(e);
        }
    }
    public String  cargarMateriales(){
        Connection con=null;
        try {

            String consulta = "select m.COD_MATERIAL,m.NOMBRE_MATERIAL from MATERIALES m  where m.COD_ESTADO_REGISTRO=1 and m.COD_GRUPO='"+codGrupo+"' order by m.NOMBRE_MATERIAL " ;
            
            LOGGER.info("consulta " + consulta);



            con = Util.openConnection(con);

            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);

            getMaterialesList().clear();
            getMaterialesList().add(new SelectItem("0","-TODOS-"));
            while(rs.next()){
                Materiales materiales = new Materiales();
                materiales.setCodMaterial(rs.getString("COD_MATERIAL"));
                materiales.setNombreMaterial(rs.getString("NOMBRE_MATERIAL"));
                getMaterialesList().add(new SelectItem(rs.getString(1),rs.getString(2)));
                //materialesList.add(materiales);
            }
            codMaterial="0";

        } catch (Exception e) {
            
            LOGGER.warn(e);
        }
        return null;
    }
     public void cargarGestiones(){
        Connection con=null;
        try {
            con=Util.openConnection(con);
            String sql="select cod_gestion,nombre_gestion from gestiones";
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs=st.executeQuery(sql);
            getGestionesList().clear();
            gestionesList.add(new SelectItem("0","TODOS"));
            while (rs.next()){
                getGestionesList().add(new SelectItem(rs.getString("cod_gestion"), rs.getString("nombre_gestion")));
            }
            if(rs!=null){
                rs.close();
                st.close();
            }
        } catch (Exception e) {
            
            LOGGER.warn(e);
        }
    }

    public void cargarTiposIngresoAlmacen2(){
        Connection con=null;

        try {
            String sql="  select tia.NOMBRE_TIPO_INGRESO_ALMACEN, tia.COD_TIPO_INGRESO_ALMACEN " +
                    "from TIPOS_INGRESO_ALMACEN tia where tia.COD_ESTADO_REGISTRO=1";
                    con=Util.openConnection(con);
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs=st.executeQuery(sql);
            getTiposIngresoAlmacenList2().clear();
            getTiposIngresoAlmacenList2().add(new SelectItem("0", "-TODOS-"));
            while (rs.next()){
                getTiposIngresoAlmacenList2().add(new SelectItem(rs.getInt("COD_TIPO_INGRESO_ALMACEN"), rs.getString("NOMBRE_TIPO_INGRESO_ALMACEN")));
            }
            if(rs!=null){
                rs.close();
                st.close();
            }
            con.close();
        } catch (Exception e) {
            
            LOGGER.warn(e);
        }
    }
     public List cargarTiposCompra2() {

        List tiposIngresosAlmacenList = new ArrayList();

        try {

            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select t.COD_TIPO_COMPRA,t.NOMBRE_TIPO_COMPRA from TIPOS_COMPRA t where t.COD_ESTADO_REGISTRO = 1 ";

            ResultSet rs = null;
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            tiposIngresosAlmacenList.clear();
            tiposIngresosAlmacenList.add(new SelectItem("0","  TODOS  "));
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
            
            LOGGER.warn(e);
        }
        return tiposIngresosAlmacenList;
    }
    //final alejandro
     //inicio alejandro 2
    public boolean verificarPermisosUsuario(ManagedAccesoSistema verUsuario)
    {
        Connection con=null;
        try
        {
            con=Util.openConnection(con);
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            String consulta="select * from USUARIOS_PERMISOS_ESPECIALES ups where ups.COD_MODULO='"+verUsuario.getCodigoModulo()+"' and ups.COD_PERSONAL='"+verUsuario.getUsuarioModuloBean().getCodUsuarioGlobal()+"'";
            
            LOGGER.info("consulta "+consulta);

            ResultSet result=st.executeQuery(consulta);
            if(result.next())
            {
                 result.close();
                 st.close();
                 con.close();
                return true;
            }
            else
            {
                 result.close();
                 st.close();
                    con.close();
                return false;
            }


        }
        catch(SQLException ex)
        {
            
            LOGGER.warn(ex);

        }
        return false;
    }
    public boolean verificarIngresoConSalidas(IngresosAlmacen ingreso)
    {
        boolean resultado=true;
        Connection con=null;
        try
        {

            con=Util.openConnection(con);
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            String consulta="select * from salidas_almacen_detalle_ingreso" +
                    " where cod_ingreso_almacen='"+ingreso.getCodIngresoAlmacen()+"'and" +
                    " cod_salida_almacen in (select cod_salida_almacen from salidas_almacen" +
                    " where estado_sistema=1 and cod_estado_salida_almacen=1 and cod_almacen='"+ingreso.getAlmacenes().getCodAlmacen()+"')";
            
            LOGGER.info("consulta "+consulta);
            ResultSet rest=st.executeQuery(consulta);
            resultado=rest.next();
            rest.close();
            st.close();
            con.close();
        }
        catch(SQLException ex)
        {
            
            LOGGER.warn(ex);
        }
        return resultado;
    }
    public boolean  verificarTranasaccionCerrada(IngresosAlmacen ingreso)
    {
         boolean transaccionCerradaAlmacen = false;

         SimpleDateFormat sdf = new SimpleDateFormat("MM");
         try {
             Connection con = null;
             con = Util.openConnection(con);
             String consulta = " select * from estados_transacciones_almacen " +
                     
                     " where cod_gestion = '"+ingreso.getGestiones().getCodGestion()+"' " +
                     " and cod_mes = '"+Integer.valueOf(sdf.format(ingreso.getFechaIngresoAlmacen()))+"'  " +
                     " and estado=1 ";
             LOGGER.info(consulta);
             Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             ResultSet rs = st.executeQuery(consulta);
             if(rs.next()){
                 transaccionCerradaAlmacen= true;
             }
             rs.close();
             st.close();
             con.close();

         } catch (Exception e) {
             
             LOGGER.warn(e);
         }
         return transaccionCerradaAlmacen ;
    }
    public int devolucionAnulada(IngresosAlmacen ingresosAlmacen){
        int devolucionAnulada=0;
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta ="select ia.COD_INGRESO_ALMACEN,d.COD_ESTADO_DEVOLUCION from INGRESOS_ALMACEN ia inner join DEVOLUCIONES d on d.COD_DEVOLUCION = ia.COD_DEVOLUCION " +
                    " where ia.cod_ingreso_almacen = '"+ingresosAlmacen.getCodIngresoAlmacen()+"'";
            System.out.println("consulta " + consulta);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(consulta);
            if(rs.next()){
                devolucionAnulada=rs.getInt("cod_estado_devolucion");
            }
        } catch (Exception e) {
            
            LOGGER.warn(e);
        }
        return devolucionAnulada;
    }
    
    public String anularIngresosAlmacen_action(){
        
        try {
            ingresosAlmacen.getAlmacenes().setCodAlmacen(usuario.getAlmacenesGlobal().getCodAlmacen());
            
                        mensaje="";
            if(!this.verificarPermisosUsuario(usuario))
            {
                mensaje="usted no tiene permisos para anular ingresos de almacen";
                return null;
            }
            if(this.verificarIngresoConSalidas(ingresosAlmacen))
            {
                mensaje="Usted no puede  Anular el Ingreso porque se registraron Salidas del mismo";
                return null;
            }
            if(this.verificarIngresoConSolicitudesLoteProveedor(ingresosAlmacen))
            {
                mensaje="Usted no puede  Anular el Ingreso porque se registraron Solicitudes de Salidas por lote de proveedor del mismo.";
                return null;
            }
            if(ingresosAlmacen.getTiposIngresoAlmacen().getCodTipoIngresoAlmacen()==6 && this.devolucionAnulada(ingresosAlmacen)!=2)
            {
                mensaje="Para anular este ingreso debe anular su devolución";
                return null;
            }
            if(ingresosAlmacen.getTiposIngresoAlmacen().getCodTipoIngresoAlmacen()==3)
            {
                mensaje="El Ingreso por Traspaso no puede ser Anulado";
                return null;
             }
            if(this.verificarTranasaccionCerrada(ingresosAlmacen))
            {
                mensaje="Las Transacciones para la fecha de ingreso seleccionado fueron cerredas.";
                return null;
            }
            if(ingresosAlmacen.getEstadosIngresoAlmacen().getCodEstadoIngresoAlmacen()==2)
            {
                mensaje="No se puede anular el ingreso porque ya fue anulado anteriormente";
                return null;
            }

            Connection con = null;
            con=Util.openConnection(con);
            Statement st = con.createStatement();
            String consulta = " update ingresos_almacen set cod_estado_ingreso_almacen = 2 where cod_ingreso_almacen = '"+ingresosAlmacen.getCodIngresoAlmacen()+"'";
            LOGGER.info("consulta " + consulta);
            st.executeUpdate(consulta);
            //---------------------------------------ELIMINAR CONSULTA POR DEMAS
            consulta="select ia.COD_ORDEN_COMPRA from INGRESOS_ALMACEN  ia where ia.COD_INGRESO_ALMACEN='"+ingresosAlmacen.getCodIngresoAlmacen()+"'";
            LOGGER.info("consulta "+consulta);
            ResultSet result=st.executeQuery(consulta);
            int codOrden=0;
            while(result.next())
            {
                codOrden=result.getInt("COD_ORDEN_COMPRA");
            }
            
            System.out.println("---COD_ORDEN_COMPRA"+codOrden);
            System.out.println("---COD_ORDEN_COMPRA"+ingresosAlmacen.getOrdenesCompra().getCodOrdenCompra());
            if(ingresosAlmacen.getOrdenesCompra().getCodOrdenCompra() > 0)
            {
               List listaIngresos= this.cargarIngresosAlmacenDetalle(ingresosAlmacen);
               Iterator j=listaIngresos.iterator();
               while(j.hasNext())
               {

                   IngresosAlmacenDetalle current=(IngresosAlmacenDetalle)j.next();
                   float cantTotalIngreso=current.getCantTotalIngresoFisico();
                   consulta="select ocd.COD_UNIDAD_MEDIDA,ocd.CANTIDAD_INGRESO_ALMACEN from ordenes_compra_detalle ocd" +
                            
                           " where ocd.cod_orden_compra='"+ingresosAlmacen.getOrdenesCompra().getCodOrdenCompra()+"'and ocd.cod_material='"+current.getMateriales().getCodMaterial()+"'";
                   LOGGER.info("consulta "+consulta);
                   result=st.executeQuery(consulta);
                   int codUnidad2=0;
                   float cantIngresoAlmacen=0;
                   if(result.next())
                   {
                       codUnidad2=result.getInt("COD_UNIDAD_MEDIDA");
                       cantIngresoAlmacen=result.getFloat("CANTIDAD_INGRESO_ALMACEN");
                   }


                   if(codUnidad2!=current.getUnidadesMedida().getCodUnidadMedida())
                   {
                       consulta="select m.cod_material,m.nombre_material,m.cod_unidad_medida,m.PESO_ASOCIADO,m.COD_UNIDAD_MEDIDA_PESOASOCIADO, "+
                               "(select um.nombre_unidad_medida from unidades_medida um where um.cod_unidad_medida=m.cod_unidad_medida)as nombre_unidad_medida, "+
                                "(select um.nombre_unidad_medida from unidades_medida um where um.cod_unidad_medida=m.cod_unidad_medida_pesoasociado)as nombre_unidad_medida2 "+
                                "from materiales m where m.cod_material='"+current.getMateriales().getCodMaterial()+"'";
                       LOGGER.info("consulta "+consulta);
                       result=st.executeQuery(consulta);
                       result.next();
                       consulta="select count( distinct cod_tipo_medida) as cantidad from UNIDADES_MEDIDA where cod_unidad_medida in('"+current.getUnidadesMedida().getCodUnidadMedida()+"','"+codUnidad2+"')";
                       LOGGER.info("consulta "+consulta);
                       Statement st2=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                       ResultSet result2=st.executeQuery(consulta);
                       result2.next();
                       if((result2.getInt("cantidad")==2) &&(result.getFloat("PESO_ASOCIADO")>0) )
                       {
                           if(codUnidad2!=result.getInt("COD_UNIDAD_MEDIDA_PESOASOCIADO"))
                           {
                             
                                consulta = "select cod_unidad_medida,cod_unidad_medida2,valor_equivalencia from equivalencias"
                                   + "where cod_unidad_medida='" + current.getUnidadesMedida().getCodUnidadMedida() + "'and cod_unidad_medida2='" + codUnidad2 + "'and cod_estado_registro=1";
                                LOGGER.info("consulta " + consulta);
                                Statement st3 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                                ResultSet result3 = st3.executeQuery(consulta);
                                float valorEquivalencia = 0;
                                if (!result3.next()) {
                                    consulta = "select cod_unidad_medida,cod_unidad_medida2,valor_equivalencia from equivalencias"
                                            + "where cod_unidad_medida='" + codUnidad2 + "'and cod_unidad_medida2='" + current.getUnidadesMedida().getCodUnidadMedida() + "'and cod_estado_registro=1";
                                    LOGGER.info("consulta 2 " + consulta);
                                    result3 = st3.executeQuery(consulta);
                                    if (result3.next()) {
                                        valorEquivalencia = ((1 / result3.getFloat("valor_equivalencia")) * result.getFloat("PESO_ASOCIADO"));
                                        cantTotalIngreso = cantTotalIngreso * valorEquivalencia;
                                    }
                                } else {
                                    valorEquivalencia = (result3.getFloat("valor_equivalencia") * result.getFloat("PESO_ASOCIADO"));
                                    cantTotalIngreso = cantTotalIngreso * valorEquivalencia;

                                }
                                result3.close();
                                st3.close();

                           }

                       }
                       else
                       {
                           consulta="select valor_equivalencia from equivalencias" +
                                   " where cod_unidad_medida='"+current.getUnidadesMedida().getCodUnidadMedida()+"'and cod_unidad_medida2='"+codUnidad2+"'";
                           LOGGER.info("consulta "+consulta);
                           Statement st3=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                           ResultSet result3=st3.executeQuery(consulta);

                           if(result3.next())
                           {
                                float valorEquivalencia=result3.getFloat("valor_equivalencia");
                                cantTotalIngreso=cantTotalIngreso*valorEquivalencia;
                           }
                           else
                           {
                                 consulta="select valor_equivalencia from equivalencias" +
                                   " where cod_unidad_medida='"+codUnidad2+"'and cod_unidad_medida2='"+current.getUnidadesMedida().getCodUnidadMedida()+"'";
                                 LOGGER.info("consulta2 "+consulta);
                                 result3=st3.executeQuery(consulta);
                                 if(result3.next())
                                 {
                                     float valorEquivalencia=(1/result3.getFloat("valor_equivalencia"));
                                       cantTotalIngreso=cantTotalIngreso*valorEquivalencia;
                                 }

                           }
                           result3.close();
                           st3.close();

                       }

                   }
                   cantIngresoAlmacen=cantTotalIngreso;
                   consulta="update ordenes_compra_detalle set " +
                           "cantidad_ingreso_almacen=cantidad_ingreso_almacen-'"+cantIngresoAlmacen+"'" +
                           "where cod_orden_compra='"+ingresosAlmacen.getOrdenesCompra().getCodOrdenCompra()+"' and cod_material='"+current.getMateriales().getCodMaterial()+"'";
                   LOGGER.info("consulta "+consulta);
                   PreparedStatement pst=con.prepareStatement(consulta);
                   if(pst.executeUpdate()>0)System.out.println("se actualizo");

               }
               consulta="select ocd.CANTIDAD_INGRESO_ALMACEN from ordenes_compra_detalle ocd where ocd.cod_orden_compra='"+ingresosAlmacen.getOrdenesCompra().getCodOrdenCompra()+"'";
               LOGGER.info("consulta "+consulta);
               result=st.executeQuery(consulta);
               int contador=0;

               while(result.next())
               {
                   if(result.getFloat("CANTIDAD_INGRESO_ALMACEN")>0)
                   {
                       contador++;
                   }
               }
               consulta="update ordenes_compra set ";
               if(contador==0)
               {
                   consulta+="cod_estado_compra=5";
               }
               else
               {
                   consulta+="cod_estado_compra=6";
               }
               
               consulta+="where cod_orden_compra='"+ingresosAlmacen.getOrdenesCompra().getCodOrdenCompra()+"'";
               LOGGER.info("consulta "+consulta);
               PreparedStatement pst=con.prepareStatement(consulta);
               if(pst.executeUpdate()>0)System.out.println("se actualizo");
            }
            ingresosAlmacenList = listadoIngresosAlmacen(begin, end,usuario);
        } catch (Exception e) {
            LOGGER.warn(e);
        }
        //costeo
            AssignCostServiceMultiple costeo=new AssignCostServiceMultiple(Util.getPropertiesConnection());
            costeo.updateCostosMaterialByCodIngreso(ingresosAlmacen.getCodIngresoAlmacen());
        
        //fin costeo
        return null;
    }
    
    //final alejandro 2
    //inicio ale muestreo
    public String tipoIngreso_change()
    {
        Iterator i =ingresosAlmacenDetalleList.iterator();
        while(i.hasNext())
        {

             IngresosAlmacenDetalle ingresoAlmacenDetalle=(IngresosAlmacenDetalle)i.next();
             System.out.println("cod "+ingresosAlmacen.getTiposIngresoAlmacen().getCodTipoIngresoAlmacen()+" "+ingresosAlmacenDetalle.getMateriales().getMaterialProduccion()==1+"  "+ingresosAlmacenDetalle.getMateriales().getMaterialMuestreo());
            Iterator e= ingresoAlmacenDetalle.getIngresosAlmacenDetalleEstadoList().iterator();
            while(e.hasNext())
            {
                IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstado=(IngresosAlmacenDetalleEstado)e.next();
                System.out.println("cod "+ingresosAlmacen.getTiposIngresoAlmacen().getCodTipoIngresoAlmacen()+" "+ingresosAlmacenDetalle.getMateriales().getMaterialProduccion()==1+"  "+ingresosAlmacenDetalle.getMateriales().getMaterialMuestreo());
                if(ingresosAlmacenDetalle.getMateriales().getMaterialProduccion()==1&&ingresosAlmacenDetalle.getMateriales().getMaterialMuestreo()==1 && (ingresosAlmacen.getTiposIngresoAlmacen().getCodTipoIngresoAlmacen()!=8))
                {
                    ingresosAlmacenDetalleEstado.getEstadosMaterial().setCodEstadoMaterial(1);
                    ingresosAlmacenDetalleEstado.getEstadosMaterial().setNombreEstadoMaterial("CUARENTENA");
                }
                else
                {
                    ingresosAlmacenDetalleEstado.getEstadosMaterial().setCodEstadoMaterial(2);
                    ingresosAlmacenDetalleEstado.getEstadosMaterial().setNombreEstadoMaterial("APROBADO");
                }
            }
        }
        return null;
    }
    //final ale muestreo
    public String editarUbicacionIngresosAlmacen_action(){
        try {
            Iterator i = ingresosAlmacenList.iterator();
            while(i.hasNext()){
                ingresosAlmacen = (IngresosAlmacen) i.next();
                if(ingresosAlmacen.getChecked().booleanValue()==true){
                    break;
                }
            }
        } catch (Exception e) {
            LOGGER.warn(e);
        }
        return null;
    }
    public String guardarUbicacionIngresoAlmacen_action()throws SQLException{
         mensaje = "";
         Connection con = null;
         con = Util.openConnection(con);
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
         try {
             con.setAutoCommit(false);
//             if(salidasAlmacenDetalleList.size()==0){
//                 mensaje = " no existe detalle de salida ";
//                 return null;
//             }
             String consulta = "";
             Statement st = con.createStatement();

             //iteracion de detalle
                   Iterator i = ingresosAlmacenDetalleList.iterator();

                   while(i.hasNext()){
                       IngresosAlmacenDetalle ingresosAlmacenDetalleItem = (IngresosAlmacenDetalle)i.next();

                       Iterator i3 = ingresosAlmacenDetalleItem.getIngresosAlmacenDetalleEstadoList().iterator();


                       while(i3.hasNext()){
                           IngresosAlmacenDetalleEstado ia = (IngresosAlmacenDetalleEstado) i3.next();
                           consulta = " update ingresos_almacen_detalle_estado set cod_estante = '"+ia.getEstanteAlmacen().getCodEstante()+"' " +
                                   ", fila = '"+ia.getFila()+"',columna = '"+ia.getColumna()+"' where cod_ingreso_almacen = '"+ingresosAlmacen.getCodIngresoAlmacen()+"' and cod_material = '"+ingresosAlmacenDetalleItem.getMateriales().getCodMaterial()+"' and etiqueta = '"+ia.getEtiqueta()+"'    ";
                           LOGGER.info("consulta " + consulta);
                           st.executeUpdate(consulta);

                       }

                   }
                   //final ale orden compra
                   con.commit();
                   st.close();
                   con.close();
         } catch (Exception e) {
             LOGGER.warn(e);
             con.rollback();
         }finally{
             con.close();
         }
         return null;
     }
    public String repetirValores_action(){
         try {

             Iterator i = ingresosAlmacenDetalle.getIngresosAlmacenDetalleEstadoList().iterator();


             String nombreEstanteAmbiente = this.buscaEstanteAmbiente(ingresosAlmacenDetalleEstado.getEstanteAlmacen().getCodEstante());



             while(i.hasNext()){
                 IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstadoItem = (IngresosAlmacenDetalleEstado)i.next();

                 if(ingresosAlmacenDetalleEstadoItem.getChecked()==true){
                 ingresosAlmacenDetalleEstadoItem.getEmpaqueSecundarioExterno().setCodEmpaqueSecundarioExterno(ingresosAlmacenDetalleEstado.getEmpaqueSecundarioExterno().getCodEmpaqueSecundarioExterno());
                 
                     ingresosAlmacenDetalleEstadoItem.getEstanteAlmacen().setCodEstante(ingresosAlmacenDetalleEstado.getEstanteAlmacen().getCodEstante());
                     ingresosAlmacenDetalleEstadoItem.getEstanteAlmacen().setNombreEstante(nombreEstanteAmbiente);
                     ingresosAlmacenDetalleEstadoItem.setFila(ingresosAlmacenDetalleEstado.getFila());
                     ingresosAlmacenDetalleEstadoItem.setColumna(ingresosAlmacenDetalleEstado.getColumna());
                 }

             }
         } catch (Exception e) {
             LOGGER.warn(e);
         }
         return null;
     }
    public String detallarItem1_action(){
         try {
             ingresosAlmacenDetalle = (IngresosAlmacenDetalle)ingresosAlmacenDetalleEditarDataTable.getRowData();
             //aqui las unidades de medida
             ingresosAlmacenDetalleEstado = new IngresosAlmacenDetalleEstado();
             DateTime fechaVencimiento =  new DateTime(ingresosAlmacenDetalleEstado.getFechaManufactura());
             fechaVencimiento = fechaVencimiento.plusMonths(6);
             fechaVencimiento = fechaVencimiento.dayOfMonth().withMaximumValue();
             //ingresosAlmacenDetalleEstado.setFechaVencimiento(fechaVencimiento.toDate());
         } catch (Exception e) {
             LOGGER.warn(e);
         }
         return null;
     }

    public boolean isAdministradorAlmacen() {
        return administradorAlmacen;
    }

    public void setAdministradorAlmacen(boolean administradorAlmacen) {
        this.administradorAlmacen = administradorAlmacen;
    }
     public boolean verificarIngresoConSolicitudesLoteProveedor(IngresosAlmacen ingreso)
    {
        boolean resultado=true;
        Connection con=null;
        try
        {

            con=Util.openConnection(con);
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            String consulta="select * from SOLICITUDES_SALIDA_DETALLE_LOTE_PROVEEDOR"
                    + " where COD_INGRESO_ALMACEN=" +
                   ingreso.getCodIngresoAlmacen();
            LOGGER.info("consulta: "+consulta);
            ResultSet rest=st.executeQuery(consulta);
            resultado=rest.next();
            rest.close();
            st.close();
            con.close();
        }
        catch(SQLException ex)
        {
            
            LOGGER.warn(ex);
        }
        return resultado;
    }
 //-----------------------------------GENERAR SALIDA DE RECHAZADOS
    public String nuevaSalidaRechazados_action(){
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        sessionMap.put("codIngresoAlmacen",ingresosAlmacen.getCodIngresoAlmacen());

        return null;
    }
//- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - SOLICITUD TRANSACCIONES INGRESO ALMACEN
    public boolean verificarIngresoTieneSolicitudesPendientes()
    {
        boolean tieneSolicitudesPendientes = false;
        try
        {
            Connection con1 = null;
            con1 = Util.openConnection(con1);
            String consulta = " SELECT sts_a.COD_INGRESO_TRANSACCION_SOLICITUD"
                        + " FROM INGRESO_TRANSACCION_SOLICITUD sts_a"
                        + " INNER JOIN "
                        + " (   SELECT COD_INGRESO_ALMACEN, MAX(FECHA_SOLICITUD) AS MAX_FECHA_SOLICITUD"
                            + " FROM INGRESO_TRANSACCION_SOLICITUD"
                            + " GROUP BY COD_INGRESO_ALMACEN"
                        + " ) sts_group on sts_a.COD_INGRESO_ALMACEN = sts_group.COD_INGRESO_ALMACEN and sts_a.FECHA_SOLICITUD = sts_group.MAX_FECHA_SOLICITUD"
                        + " WHERE sts_a.COD_ESTADO_TRANSACCION_SOLICITUD in (1,2)"
                        + " AND sts_a.COD_INGRESO_ALMACEN = " + ingresosAlmacen.getCodIngresoAlmacen();
            LOGGER.info("consulta verificacion: " + consulta);
            Statement st = con1.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            
            if(rs.next()) {
                tieneSolicitudesPendientes = true;
            }  
            
            rs.close();
            st.close();
            con1.close();
        }
        catch(SQLException ex)
        {
            LOGGER.warn(ex);
        }
        return tieneSolicitudesPendientes;
    } 
     
     
     
    /**
     * Valida si el Ingreso puede ser Editable/Anulable
     * @return boolean
     */
     
    public boolean validacionesGeneralesEditarAnularIngreso() {
        
//        if (!administradorAlmacen) {
//            mensaje = "No se encuentra habilitado como administrador del almacen.";
//            return false;
//        }

        if(verificarIngresoTieneSolicitudesPendientes()) {
            mensaje = "El ingreso N° "+ingresosAlmacen.getNroIngresoAlmacen() +" ya tiene una solicitud pendiente, actualice el navegador y verifique los datos antes de intentarlo nuevamente.";
            return false;
        }  
        
        return true;    
    }
    
    /**
     * Valida SI el Ingreso ES EDITABLE
     * @return boolean
     */
    public boolean validacionesEditarIngreso() {
            
        if(verificaTransaccionCerradaAlmacen()){
            mensaje = "Las transacciones para este mes fueron cerradas ";
            return false;
        }
//        if(!salidasAsociadasAlmacenList.isEmpty()){
//            mensaje = "El ingreso no se puede modificar por que tiene salidas registradas ";
//            return false;
//        }
        
        return true;    
    }
    
    /**
     * Valida SI el Ingreso ES ANULABLE
     * @return boolean
     */
    public boolean validacionesAnularIngreso() {
        
        if(this.verificarIngresoConSolicitudesLoteProveedor(ingresosAlmacen))
        {
            mensaje="Usted no puede  Anular el Ingreso porque se registraron Solicitudes de Salidas por lote de proveedor del mismo.";
            return false;
        }

        if(ingresosAlmacen.getTiposIngresoAlmacen().getCodTipoIngresoAlmacen()==3)
        {
            mensaje="El Ingreso por Traspaso no puede ser Anulado";
            return false;
         }
        if(this.verificarTranasaccionCerrada(ingresosAlmacen))
        {
            mensaje="Las Transacciones para la fecha de ingreso seleccionado fueron cerredas.";
            return false;
        }
        if(ingresosAlmacen.getEstadosIngresoAlmacen().getCodEstadoIngresoAlmacen()==2)
        {
            mensaje="No se puede anular el ingreso porque ya fue anulado anteriormente";
            return false;
        }
//        if(tieneSalidasAlmacen()){
//            mensaje = "El ingreso no se puede modificar por que tiene salidas registradas ";
//            return false;
//        }
//        if(this.verificarIngresoConSalidas(ingresosAlmacen))
//        {
//            mensaje="Usted no puede Anular el Ingreso porque se registraron Salidas del mismo";
//            return false;
//        }
//        if(ingresosAlmacen.getTiposIngresoAlmacen().getCodTipoIngresoAlmacen()==6 && this.devolucionAnulada(ingresosAlmacen)!=2)
//        {
//            mensaje="Para anular este ingreso debe anular su devolución";
//            return false;
//        }                
        
        return true;    
    }
        
    public String nuevoIngresoTransaccionSolicitudEditar_action() {
        mensaje = "";
        ingresoEditable = false;
        ingresoEditableValidandoConSalidas = false;
        
        //--VALIDACIONES ANTES DE CREAR UNA NUEVA SOLICITUD
        if (!validacionesGeneralesEditarAnularIngreso()) {
            return null;
        }
        if (!validacionesEditarIngreso()) {
            return null;
        }
        //Verificamos si tiene salidas asociadas
        cargarSalidasAsociadasAlmacen();
        if (tipoSalidaAutomatico && !salidaTieneDevolucion) {
            ingresoEditableValidandoConSalidas = true;
        }         
        ingresoTransaccionSolicitudGestionar = new IngresoTransaccionSolicitud(); 
        ingresoEditable = true;
                
        return null;
    }
    
    public String nuevoIngresoTransaccionSolicitudAnular_action() {
        mensaje = "";
        ingresoEditable = false;
        ingresoEditableValidandoConSalidas = false;
        
        //VALIDACIONES SOLICITUD ANULAR
        if (!validacionesGeneralesEditarAnularIngreso()) {
            return null;
        }
        if (!validacionesAnularIngreso()) {
            return null;
        }
        //Verificamos si tiene salidas asociadas
        cargarSalidasAsociadasAlmacen();
        if (tipoSalidaAutomatico && !salidaTieneDevolucion) {
            ingresoEditableValidandoConSalidas = true;
        }
        ingresoEditable = true;
        ingresoTransaccionSolicitudGestionar = new IngresoTransaccionSolicitud();
        
        return null;
    }
    
    public String nuevoIngresoTransaccionSolicitudValidar_action() {
        mensaje = "";
        ingresoEditable = false;
        ingresoEditableValidandoConSalidas = false;
        
        //VALIDACIONES GENERALES  
        //--VALIDACIONES ANTES DE CREAR UNA NUEVA SOLICITUD
        if(verificaTransaccionCerradaAlmacen()){
            mensaje = "Las transacciones para este mes fueron cerradas ";
            return null;
        }
        
        //Si es solicitud para ANULAR
        if (ingresosAlmacen.getIngresoTransaccionSolicitud().getEstadoTransaccionSolicitud().getCodEstadoTransaccionSolicitud()==1) {
            
            if (!validacionesAnularIngreso()) {
                return null;
            }
        }
        
        //Si es solicitud para EDITAR, se edita directamente
        if (ingresosAlmacen.getIngresoTransaccionSolicitud().getEstadoTransaccionSolicitud().getCodEstadoTransaccionSolicitud()==2) {
            
            if (!validacionesEditarIngreso()) {
                return null;
            }
        }
        
        //Verificamos si tiene salidas asociadas
        cargarSalidasAsociadasAlmacen();
        if (tipoSalidaAutomatico && !salidaTieneDevolucion) {
            ingresoEditableValidandoConSalidas = true;
        } 
        ingresoEditable = true;       
        ingresoTransaccionSolicitudGestionar = new IngresoTransaccionSolicitud();
        return null;
    }
    
    public String nuevoIngresoTransaccionSolicitudRechazar_action() {
        mensaje = "";
        //Verificamos si tiene salidas asociadas
        cargarSalidasAsociadasAlmacen();
        ingresoTransaccionSolicitudGestionar = new IngresoTransaccionSolicitud();
        return null;
    }
     
    public String guardarSolicitudEditarIngresoAlmacen_action(){
        this.transaccionExitosa = false;
        Connection con1 = null;
        try {
            if (ingresoTransaccionSolicitudGestionar.getObservacionSolicitante().length()==0) {
                mensaje = "Ingrese la razón de la solicitud.";
                return null;
            }
            
            //VALIDACION, QUE NO EXISTAN SOLICITUDES PENDIENTES SOBRE ESTE INGRESO DE ALMACEN
            if(verificarIngresoTieneSolicitudesPendientes()) {
                mensaje = "El ingreso N° "+ingresosAlmacen.getNroIngresoAlmacen() +" ya tiene una solicitud pendiente, actualice el navegador y verifique los datos antes de intentarlo nuevamente.";
                return null;
            }                  
            con1 = Util.openConnection(con1);
            String consulta = " INSERT INTO INGRESO_TRANSACCION_SOLICITUD(COD_INGRESO_ALMACEN, COD_PERSONAL_SOLICITANTE, COD_TIPO_TRANSACCION_SOLICITUD_ALMACEN, FECHA_SOLICITUD, COD_ESTADO_TRANSACCION_SOLICITUD, OBSERVACION_SOLICITANTE)"
                    + " VALUES (" + ingresosAlmacen.getCodIngresoAlmacen()
                            + " , " + usuario.getUsuarioModuloBean().getCodUsuarioGlobal()
                            + " , 2" 
                            + " , GETDATE()"
                            + " , 2" 
                            + " , '" +ingresoTransaccionSolicitudGestionar.getObservacionSolicitante()+"'"
                    + " )";
            
            LOGGER.info("consulta nuevo INGRESO_TRANSACCION_SOLICITUD: " + consulta);
            Statement st = con1.createStatement();
            if(st.executeUpdate(consulta) > 0){
                this.mostrarMensajeTransaccionExitosa("Se registró correctamente la solicitud");
            }
            else{
                this.mostrarMensajeTransaccionFallida("Ocurrio un error el momento de registrar la solicitud, intente de nuevo");
            }
            st.close();
            con1.close();
        } catch (Exception e) {
            this.mostrarMensajeTransaccionFallida("Ocurrio un error el momento de registrar la solicitud, intente de nuevo");
            LOGGER.warn(e);
        }
        finally{
            this.cerrarConexion(con1);
        }
        if (this.transaccionExitosa) {
            cargarIngresosAlmacenList();
            EnvioCorreoNotificacionSolicitudIngresoAlmacen correo = new EnvioCorreoNotificacionSolicitudIngresoAlmacen(ingresosAlmacen.getCodIngresoAlmacen());
            correo.start();
        }
        return null;
    }
    
    public String guardarSolicitudAnularIngresoAlmacen_action(){
        
        mensaje = "";
        registradoCorrectamente = false ;
        
        try {
            
            if (ingresoTransaccionSolicitudGestionar.getObservacionSolicitante().length()==0) {
                mensaje = "Ingrese la razón de la solicitud.";
                return null;
            }
            
            //VALIDACION, QUE NO EXISTAN SOLICITUDES PENDIENTES SOBRE ESTE INGRESO DE ALMACEN
            if(verificarIngresoTieneSolicitudesPendientes()) {
                mensaje = "El ingreso N° "+ingresosAlmacen.getNroIngresoAlmacen() +" ya tiene una solicitud pendiente, actualice el navegador y verifique los datos antes de intentarlo nuevamente.";
                return null;
            }                 
            
            //CREAMOS NUEVA SOLICITUD
            ManagedAccesoSistema usuarioLogueado = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            Connection con1 = null;
            con1 = Util.openConnection(con1);
            String consulta = " INSERT INTO INGRESO_TRANSACCION_SOLICITUD(COD_INGRESO_ALMACEN, COD_PERSONAL_SOLICITANTE, COD_TIPO_TRANSACCION_SOLICITUD_ALMACEN, FECHA_SOLICITUD, COD_ESTADO_TRANSACCION_SOLICITUD, OBSERVACION_SOLICITANTE)"
                    + " VALUES (" + ingresosAlmacen.getCodIngresoAlmacen()
                    + " , " + usuarioLogueado.getUsuarioModuloBean().getCodUsuarioGlobal()
                    + " , 1" 
                    + " , GETDATE()"
                    + " , 1" 
                    + " , '" +ingresoTransaccionSolicitudGestionar.getObservacionSolicitante()+"'"
                    + " )";
            
            LOGGER.info("consulta nuevo INGRESO_TRANSACCION_SOLICITUD: " + consulta);
            Statement st = con1.createStatement();
            if(st.executeUpdate(consulta) > 0){
                mensaje = "Se registró correctamente la solicitud de Anulación";
                registradoCorrectamente = true;
            }
            else{
                mensaje = "Ocurrió un error. No se registró la solicitud.";
            }
            
            st.close();
            con1.close();
            
            cargarIngresosAlmacenList();
        } catch (Exception e) {
            mensaje = "Ocurrió un error. ";
            LOGGER.warn(e);
        }
        //ENVIAMOS CORREO
        if (registradoCorrectamente) {
            EnvioCorreoNotificacionSolicitudIngresoAlmacen correo = new EnvioCorreoNotificacionSolicitudIngresoAlmacen(ingresosAlmacen.getCodIngresoAlmacen());
            correo.start();
        }
        return null;
    }
    
    
    public String aprobarSolicitudAnularEditarIngresoAlmacen_action() throws SQLException {
        mensaje = "";
        registradoCorrectamente = false;
        Connection con1 = null;        
        try {
            con1 = Util.openConnection(con1);
            con1.setAutoCommit(false);
            ManagedAccesoSistema usuarioLogueado = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            String consulta;
            
            //APROBAR EDITAR
//            if (ingresosAlmacen.getIngresoTransaccionSolicitud().getEstadoTransaccionSolicitud().getCodEstadoTransaccionSolicitud() == 2) {
//                  
//                consulta = " update INGRESO_TRANSACCION_SOLICITUD"
//                        + " set FECHA_VALIDACION = GETDATE()"
//                        + " , COD_ESTADO_TRANSACCION_SOLICITUD = 4"
//                        + " , COD_PERSONAL_VALIDADOR = " + usuarioLogueado.getUsuarioModuloBean().getCodUsuarioGlobal()
//                        + " , OBSERVACION_VALIDADOR = '" + ingresoTransaccionSolicitudGestionar.getObservacionValidador() + "'"
//                        + " where COD_INGRESO_ALMACEN = " + ingresosAlmacen.getCodIngresoAlmacen()
//                        + " AND COD_ESTADO_TRANSACCION_SOLICITUD = 2"
//                        + " AND COD_INGRESO_TRANSACCION_SOLICITUD = " +ingresosAlmacen.getIngresoTransaccionSolicitud().getCodIngresoTransaccionSolicitud();
//                LOGGER.info("consulta realizar aprobacion: " + consulta);                
//                Statement st = con1.createStatement();
//                
//                if(st.executeUpdate(consulta)>0){
//                    LOGGER.info("Aprobacion realizada");
//                    mensaje = "Registrado correctamente.";
//                    registradoCorrectamente = true;
//                }
//                else{
//                    mensaje = "Ocurrió un error. No se registró la solicitud.";
//                }
//                
//            }
            
            // APROBAR ANULAR : anula directamente
            if (ingresosAlmacen.getIngresoTransaccionSolicitud().getEstadoTransaccionSolicitud().getCodEstadoTransaccionSolicitud() == 1 ) {
                
                Statement st = con1.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                
                //SI ES POR DEVOLUCION
                if (ingresosAlmacen.getTiposIngresoAlmacen().getCodTipoIngresoAlmacen() == 6) {
                    consulta = " SELECT d.COD_DEVOLUCION, d.COD_ESTADO_DEVOLUCION, d.COD_FORMULARIO_DEV "
                        + " FROM INGRESOS_ALMACEN ia "
                        + " INNER JOIN DEVOLUCIONES d on d.COD_DEVOLUCION = ia.COD_DEVOLUCION "
                        + " WHERE ia.cod_ingreso_almacen = '" + ingresosAlmacen.getCodIngresoAlmacen() + "' ";
                
                    LOGGER.info("consulta DEVOLUCIONES: " + consulta);                 
                    ResultSet rs = st.executeQuery(consulta);

                    Statement st1 = con1.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    if(rs.next()) {
                        if (rs.getInt("COD_ESTADO_DEVOLUCION") == 1) {

                            consulta = " UPDATE DEVOLUCIONES  "
                                    + " SET COD_ESTADO_DEVOLUCION = 2 "
                                    + " WHERE COD_DEVOLUCION = '" + rs.getInt("COD_DEVOLUCION") + "' ";
                            LOGGER.info("consulta : " + consulta);   
                            st1.executeUpdate(consulta);

                            consulta = " UPDATE SOLICITUD_DEVOLUCIONES  "
                                    + " SET COD_ESTADO_SOLICITUD_DEVOLUCION = 1 "
                                    + " WHERE COD_SOLICITUD_DEVOLUCION = '" + rs.getInt("COD_FORMULARIO_DEV") + "' ";
                            LOGGER.info("consulta : " + consulta);   
                            st1.executeUpdate(consulta);

                        }
                    }
                }
                
                //SI TIENE SALIDAS ASOCIADAS LAS ANULAMOS
                if (!salidasAsociadasAlmacenList.isEmpty()) {
                    for (SalidasAlmacen salidaAlmacen : salidasAsociadasAlmacenList) {
                        String motivoAnulacionSalida =  "Anulación de salida automática por anulacion de Ingreso "+ingresosAlmacen.getNroIngresoAlmacen()+". ";
                        
                        //EJECUTAMOS SP
                        consulta = " exec [USP_ANULAR_SALIDAS_ALMACEN] " + salidaAlmacen.getCodSalidaAlmacen()
                                    + ", " + usuarioLogueado.getUsuarioModuloBean().getCodUsuarioGlobal() ;
                        LOGGER.info("consulta ejecutar: " + consulta);
                        CallableStatement callStatement = con1.prepareCall(consulta);
                        callStatement.execute();
                        
                        //EJECUTAMOS SP DESPUES DE CAMBIO
                        consulta = " exec [PAA_REGISTRO_SALIDA_ALMACEN_LOG] " + salidaAlmacen.getCodSalidaAlmacen() 
                                + " , "+ usuarioLogueado.getUsuarioModuloBean().getCodUsuarioGlobal()
                                + " , 2"
                                + " , '"+motivoAnulacionSalida+"' ";
                        LOGGER.info("consulta ejecutar: " + consulta);            
                        callStatement = con1.prepareCall(consulta);
                        callStatement.execute();
                    }                    
                }
                
                // ACTUALIZAMOS INGRESO_TRANSACCION_SOLICITUD
                consulta = " UPDATE INGRESO_TRANSACCION_SOLICITUD"
                        + " SET FECHA_VALIDACION = GETDATE()"
                        + " , COD_ESTADO_TRANSACCION_SOLICITUD = 3"
                        + " , COD_PERSONAL_VALIDADOR = " + usuarioLogueado.getUsuarioModuloBean().getCodUsuarioGlobal()
                        + " , OBSERVACION_VALIDADOR = '" + ingresoTransaccionSolicitudGestionar.getObservacionValidador() + "'"
                        + " WHERE COD_INGRESO_ALMACEN = " + ingresosAlmacen.getCodIngresoAlmacen()
                        + " AND COD_ESTADO_TRANSACCION_SOLICITUD = 1"
                        + " AND COD_INGRESO_TRANSACCION_SOLICITUD = " +ingresosAlmacen.getIngresoTransaccionSolicitud().getCodIngresoTransaccionSolicitud();
                LOGGER.info("consulta realizar aprobacion: " + consulta);                
                if(st.executeUpdate(consulta)<1){
                    mensaje = "Alguien más hizo una solicitud sobre este registro. Verifique el registro antes de volver a intentarlo.";
                    throw new Exception("Alguien más hizo una solicitud sobre este registro.");
                }
                
                //EJECUTAMOS SP 
                consulta = " exec [USP_ANULAR_INGRESOS_ALMACEN] " + ingresosAlmacen.getCodIngresoAlmacen()
                        + ", " + usuarioLogueado.getUsuarioModuloBean().getCodUsuarioGlobal();
                LOGGER.info("consulta ejecutar: " + consulta);
                CallableStatement callStatement = con1.prepareCall(consulta);
                callStatement.execute();
                
                //EJECUTAMOS SP DESPUES CAMBIO
                consulta = " exec [PAA_REGISTRO_INGRESO_ALMACEN_LOG] " + ingresosAlmacen.getCodIngresoAlmacen() 
                        + " , "+ usuarioLogueado.getUsuarioModuloBean().getCodUsuarioGlobal()
                        + " , 2"
                        + " , '"+ingresoTransaccionSolicitudGestionar.getObservacionValidador()+"' ";
                LOGGER.info("consulta ejecutar: " + consulta);            
                callStatement = con1.prepareCall(consulta);
                callStatement.execute();
                
                mensaje = "Registrado correctamente.";
                registradoCorrectamente = true;
            }
            con1.commit();
        }catch (SQLException ex) {
            con1.rollback();
            LOGGER.warn(ex);
            mensaje = "" + ex.getMessage();
        }catch (Exception ex) {
            con1.rollback();
            LOGGER.warn(ex);
            mensaje = "Ocurrió un error.";
        }
        finally{
            con1.close();
        }
        //ENVIAMOS CORREO
        if (registradoCorrectamente) {
            EnvioCorreoNotificacionValidacionIngresoAlmacen correo = new EnvioCorreoNotificacionValidacionIngresoAlmacen(ingresosAlmacen.getCodIngresoAlmacen());
            correo.start();
        }
        //ACTUALIZAMOS LISTADO
        cargarIngresosAlmacenValidarList_action();
        ThreadCosteoByCodIngreso costeoIngreso = new ThreadCosteoByCodIngreso(ingresosAlmacen.getCodIngresoAlmacen(),0,0);
        costeoIngreso.start();
        return null;
    }
    
    public String rechazarSolicitudAnularEditarIngresoAlmacen_action() {
        try {
            mensaje = "";
            registradoCorrectamente = false;
            //Actualizamos INGRESO_TRANSACCION_SOLICITUD para solicitud EDITAR             
            String consulta = " update INGRESO_TRANSACCION_SOLICITUD"
                    + " set COD_ESTADO_TRANSACCION_SOLICITUD = " + (ingresosAlmacen.getIngresoTransaccionSolicitud().getEstadoTransaccionSolicitud().getCodEstadoTransaccionSolicitud() == 1 ? 5 : 6)
                    + " , COD_PERSONAL_VALIDADOR = " + usuario.getUsuarioModuloBean().getCodUsuarioGlobal()
                    + " , OBSERVACION_VALIDADOR = '" + ingresoTransaccionSolicitudGestionar.getObservacionValidador() + "'"
                    + " , FECHA_VALIDACION = GETDATE() " 
                    + " where COD_INGRESO_ALMACEN = " + ingresosAlmacen.getCodIngresoAlmacen()
                    + " AND COD_INGRESO_TRANSACCION_SOLICITUD = "
                    + " (   SELECT TOP 1 COD_INGRESO_TRANSACCION_SOLICITUD "
                    + " FROM INGRESO_TRANSACCION_SOLICITUD sts"
                    + " WHERE COD_INGRESO_ALMACEN = " + ingresosAlmacen.getCodIngresoAlmacen() + " ORDER BY FECHA_SOLICITUD DESC"
                    + " )";
            LOGGER.info("consulta: " + consulta);
            Connection con1 = null;
            con1 = Util.openConnection(con1);
            Statement st = con1.createStatement();
            if(st.executeUpdate(consulta)>0){
                mensaje = "Registrado correctamente.";
                registradoCorrectamente = true;
            }
            else{
                mensaje = "Ocurrio un error, no se guardó el registro.";
            }
            st.close();
            con1.close();
            
            //Enviamos correo
            if (registradoCorrectamente) {
                EnvioCorreoNotificacionValidacionIngresoAlmacen correo = new EnvioCorreoNotificacionValidacionIngresoAlmacen(ingresosAlmacen.getCodIngresoAlmacen());
                correo.start();
            }
            
            cargarIngresosAlmacenValidarList_action();
        } catch (Exception e) {
            LOGGER.warn(e);
            mensaje = "Ocurrió un error.";
        }
        return null;
    }
    
    public String anularDirectamenteIngresoAlmacenPorSP_action() throws SQLException{
        //VALIDACIONES 
        this.transaccionExitosa = false;
        if (ingresoTransaccionSolicitudGestionar.getObservacionValidador().length()==0) {
            this.mostrarMensajeTransaccionFallida("Ingrese el motivo para anular el Ingreso.");
            return null;
        }
        
        Connection con1 = null;
        con1 = Util.openConnection(con1);
        con1.setAutoCommit(false);
        ManagedAccesoSistema usuarioLogueado = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
        String consulta;
        try {
             
            Statement st = con1.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            //SI ES POR DEVOLUCION
            if (ingresosAlmacen.getTiposIngresoAlmacen().getCodTipoIngresoAlmacen() == 6){
                //Reiniciamos devoluciones
                consulta = " SELECT d.COD_DEVOLUCION, d.COD_ESTADO_DEVOLUCION, d.COD_FORMULARIO_DEV "
                    + " FROM INGRESOS_ALMACEN ia "
                    + " INNER JOIN DEVOLUCIONES d on d.COD_DEVOLUCION = ia.COD_DEVOLUCION "
                    + " WHERE ia.cod_ingreso_almacen = '" + ingresosAlmacen.getCodIngresoAlmacen() + "' ";
                LOGGER.info("consulta DEVOLUCIONES: " + consulta);                 
                ResultSet rs = st.executeQuery(consulta);

                Statement st1 = con1.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                if(rs.next()) {
                    if (rs.getInt("COD_ESTADO_DEVOLUCION") == 1) {

                        consulta = " UPDATE DEVOLUCIONES  "
                                + " SET COD_ESTADO_DEVOLUCION = 2 "
                                + " WHERE COD_DEVOLUCION = '" + rs.getInt("COD_DEVOLUCION") + "' ";
                        LOGGER.info("consulta : " + consulta);   
                        st1.executeUpdate(consulta);

                        consulta = " UPDATE SOLICITUD_DEVOLUCIONES  "
                                + " SET COD_ESTADO_SOLICITUD_DEVOLUCION = 1 "
                                + " WHERE COD_SOLICITUD_DEVOLUCION = '" + rs.getInt("COD_FORMULARIO_DEV") + "' ";
                        LOGGER.info("consulta : " + consulta);   
                        st1.executeUpdate(consulta);
                    }
                }
            }
            
            //SI TIENE SALIDAS ASOCIADAS LAS ANULAMOS
            if (!salidasAsociadasAlmacenList.isEmpty()) {
                for (SalidasAlmacen salidaAlmacen : salidasAsociadasAlmacenList) {
                    String motivoAnulacionSalida =  "Anulación de salida automática por anulacion de Ingreso "+ingresosAlmacen.getNroIngresoAlmacen()+". ";
                    
                    //EJECUTAMOS SP
                    consulta = " exec [USP_ANULAR_SALIDAS_ALMACEN] " + salidaAlmacen.getCodSalidaAlmacen()
                                + ", " + usuarioLogueado.getUsuarioModuloBean().getCodUsuarioGlobal() ;
                    LOGGER.info("consulta ejecutar: " + consulta);
                    CallableStatement callStatement = con1.prepareCall(consulta);
                    callStatement.execute();
                    
                    //EJECUTAMOS SP DESP CAMBIO
                    consulta = " exec [PAA_REGISTRO_SALIDA_ALMACEN_LOG] " + salidaAlmacen.getCodSalidaAlmacen() 
                            + " , "+ usuarioLogueado.getUsuarioModuloBean().getCodUsuarioGlobal()
                            + " , 2"
                            + " , '"+motivoAnulacionSalida+"' ";
                    LOGGER.info("consulta ejecutar: " + consulta);            
                    callStatement = con1.prepareCall(consulta);
                    callStatement.execute();
                }                    
            }

            //EJECUTAMOS SP
            consulta = " exec [USP_ANULAR_INGRESOS_ALMACEN] " + ingresosAlmacen.getCodIngresoAlmacen()
                    + ", " + usuarioLogueado.getUsuarioModuloBean().getCodUsuarioGlobal();
            LOGGER.info("consulta ejecutar: " + consulta);
            CallableStatement callStatement = con1.prepareCall(consulta);
            callStatement.execute();
            
            //EJECUTAMOS SP DESPUES DE CAMBIO
            consulta = " exec [PAA_REGISTRO_INGRESO_ALMACEN_LOG] " + ingresosAlmacen.getCodIngresoAlmacen() 
                    + " , "+ usuarioLogueado.getUsuarioModuloBean().getCodUsuarioGlobal()
                    + " , 2"
                    + " , '"+ingresoTransaccionSolicitudGestionar.getObservacionValidador()+"' ";
            LOGGER.info("consulta ejecutar: " + consulta);            
            callStatement = con1.prepareCall(consulta);
            callStatement.execute();

            this.mostrarMensajeTransaccionExitosa("Se registro satisfactoriamente la transacción");
            con1.commit();
        }catch (SQLException ex) {
            LOGGER.warn(ex);
            this.mostrarMensajeTransaccionFallida("Ocurrio un error al momento de registrar la transaccion, intente de nuevo"+ex.getMessage());
            con1.rollback();
        }catch (Exception ex) {
            
            LOGGER.warn(ex);
            con1.rollback();
            this.mostrarMensajeTransaccionFallida("Ocurrio un error al momento de registrar la transaccion, intente de nuevo");
        }
        finally{
            con1.close();
        }
        //ACTUALIZAMOS LISTADO
        if(transaccionExitosa){
            cargarIngresosAlmacenList();
            ThreadCosteoByCodIngreso costeoIngreso = new ThreadCosteoByCodIngreso(ingresosAlmacen.getCodIngresoAlmacen(),0,0);
            costeoIngreso.start();
        }
        return null;
    }
    
    //----------------------------------PERMISOS ESPECIALES---------------------------------------------------------------------------
    public void cargarPermisosEspecialesPersonal() throws SQLException {
        Connection con1 = null;        
        try {
            con1 = Util.openConnection(con1);
            String consulta
                    = " SELECT cpb.COD_TIPO_PERMISO_ESPECIAL_BACO "
                    + " FROM CONFIGURACION_PERMISO_ESPECIAL_BACO cpb"
                    + " WHERE cpb.COD_ALMACEN = " + usuario.getAlmacenesGlobal().getCodAlmacen()
                    + " AND cpb.COD_PERSONAL = " + usuario.getUsuarioModuloBean().getCodUsuarioGlobal();
            LOGGER.info("consulta listar permisosEspecialesPersonal: " + consulta);

            Statement st = con1.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            
            permisoAnularDirectamente = false;
            permisoEditarDirectamente = false;
            permisoSolicitarAnular = false;
            permisoSolicitarEditar = false;
            permisoValidarAnular = false;
            permisoValidarEditar = false;
            permisoEditarHoy = false;
            permisoAnularHoy = false;
            
            while (rs.next()) {
                
                switch(rs.getInt("COD_TIPO_PERMISO_ESPECIAL_BACO")){
                    case 7: permisoAnularDirectamente = true; 
                        break;
                    case 8: permisoEditarDirectamente = true; 
                        break;
                    case 9: permisoSolicitarAnular = true; 
                        break;
                    case 10: permisoSolicitarEditar = true; 
                        break;
                    case 11: permisoValidarAnular = true; 
                        break;
                    case 12: permisoValidarEditar = true; 
                        break;
                    case 14: permisoEditarHoy = true; 
                        break;
                    case 21: permisoAnularHoy = true; 
                        break;
                }
            }
            st.close();
            rs.close();

        }catch(SQLException e){
            LOGGER.warn(e);
        } 
        catch (Exception e) {
            LOGGER.warn(e);
        }
        finally{  
         con1.close();
        }
            
    }
    
    public void reiniciarCamposBusqueda_action() {
        ingresoAlmacenBuscar = new IngresosAlmacen();
        ingresoAlmacenBuscar.getIngresoTransaccionSolicitud().setTipoRevision(2);
            
    }
     
    public void cargarSalidasAsociadasAlmacen(){
        salidasAsociadasAlmacenList = new ArrayList<SalidasAlmacen>();
        tipoSalidaAutomatico = true;
        salidaTieneDevolucion = false;
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " SELECT distinct sadi.COD_SALIDA_ALMACEN, sa.COD_TIPO_SALIDA_ALMACEN, sa.NRO_SALIDA_ALMACEN " 
                    + " , sa.FECHA_SALIDA_ALMACEN, sa.COD_AREA_EMPRESA, sa.COD_PERSONAL"
                    + " , ae.NOMBRE_AREA_EMPRESA, tsa.NOMBRE_TIPO_SALIDA_ALMACEN"
                    + " , (p.NOMBRES_PERSONAL+' '+p.AP_PATERNO_PERSONAL+' '+p.AP_MATERNO_PERSONAL) as nombre_completo "
                    + " , (CASE WHEN d.COD_SALIDA_ALMACEN IS NOT NULL THEN '1' else '0' END) as tiene_salida "
                    + " , tsa.ANULABLE_AUTOMATICO"
                    + " FROM salidas_almacen_detalle_ingreso sadi "
                       + " left join SALIDAS_ALMACEN sa on sadi.COD_SALIDA_ALMACEN = sa.COD_SALIDA_ALMACEN "
                       + " left join AREAS_EMPRESA ae on sa.COD_AREA_EMPRESA = ae.COD_AREA_EMPRESA "
                       + " left join TIPOS_SALIDAS_ALMACEN tsa on sa.COD_TIPO_SALIDA_ALMACEN = tsa.COD_TIPO_SALIDA_ALMACEN "
                       + " left join PERSONAL p on sa.COD_PERSONAL = p.COD_PERSONAL"
                       + " left join (SELECT DISTINCT dev.COD_SALIDA_ALMACEN from DEVOLUCIONES dev where dev.COD_ESTADO_DEVOLUCION =1) d on sa.COD_SALIDA_ALMACEN = d.COD_SALIDA_ALMACEN"
                    + " WHERE sa.COD_ESTADO_SALIDA_ALMACEN = 1 "
                    + " AND cod_ingreso_almacen = '"+ingresosAlmacen.getCodIngresoAlmacen()+"'  "
                    + " AND cod_almacen = '"+ingresosAlmacen.getAlmacenes().getCodAlmacen()+"'  ";
                    
            LOGGER.info("consulta listarSalidasAsociadasIngreso: " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            while(rs.next()){
                SalidasAlmacen salidaAlmacen = new SalidasAlmacen();
                salidaAlmacen.setCodSalidaAlmacen(rs.getInt("COD_SALIDA_ALMACEN") );
                salidaAlmacen.setNroSalidaAlmacen(rs.getInt("NRO_SALIDA_ALMACEN") );
                salidaAlmacen.setFechaSalidaAlmacen(rs.getTimestamp("FECHA_SALIDA_ALMACEN") );
                salidaAlmacen.getAreasEmpresa().setCodAreaEmpresa(rs.getString("COD_AREA_EMPRESA"));   
                salidaAlmacen.getAreasEmpresa().setNombreAreaEmpresa(rs.getString("NOMBRE_AREA_EMPRESA"));   
                salidaAlmacen.getPersonal().setCodPersonal(rs.getString("COD_PERSONAL")); 
                salidaAlmacen.getPersonal().setNombrePersonal(rs.getString("nombre_completo")); 
                salidaAlmacen.getTiposSalidasAlmacen().setCodTipoSalidaAlmacen(rs.getInt("COD_TIPO_SALIDA_ALMACEN")); 
                salidaAlmacen.getTiposSalidasAlmacen().setNombreTipoSalidaAlmacen(rs.getString("NOMBRE_TIPO_SALIDA_ALMACEN")); 
                salidaAlmacen.getTiposSalidasAlmacen().setAnulableAutomatico(rs.getInt("ANULABLE_AUTOMATICO")==1); 
                salidaAlmacen.setTieneDevolucion(rs.getInt("tiene_salida")==1); 
                if (rs.getInt("ANULABLE_AUTOMATICO")!=1) {
                    tipoSalidaAutomatico = false;
                }
                if (salidaAlmacen.isTieneDevolucion()) {
                    salidaTieneDevolucion = true;
                }
                salidasAsociadasAlmacenList.add(salidaAlmacen);
            }
            con.close();
        } catch (Exception e) {
            LOGGER.warn(e);
        }

    }
    
   
    public String verReporteSalidaAlmacen_action() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        sessionMap.put("salidasAlmacen", salidaAsociadaGestionar);
        return null;
    }
    
    
    //------------------------------------REPORTE DE LOGS
    public String verReporteIngresoAlmacenLog_action(){
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String,Object> sessionMap = externalContext.getSessionMap();
        sessionMap.put("ingresosAlmacen",ingresosAlmacen);     
        return null;
    }
     
    //GETTERS AND SETTERS
    //<editor-fold defaultstate="collapsed" desc="getter and setter">

        public List<IngresosAlmacen> getIngresosAlmacenList() {
            return ingresosAlmacenList;
        }

        public void setIngresosAlmacenList(List<IngresosAlmacen> ingresosAlmacenList) {
            this.ingresosAlmacenList = ingresosAlmacenList;
        }

        public IngresosAlmacen getIngresosAlmacen() {
            return ingresosAlmacen;
        }

        public void setIngresosAlmacen(IngresosAlmacen ingresosAlmacen) {
            this.ingresosAlmacen = ingresosAlmacen;
        }

        public List getTiposCompraList() {
            return tiposCompraList;
        }

        public void setTiposCompraList(List tiposCompraList) {
            this.tiposCompraList = tiposCompraList;
        }

        public List getTiposIngresosAlmacenList() {
            return tiposIngresosAlmacenList;
        }

        public void setTiposIngresosAlmacenList(List tiposIngresosAlmacenList) {
            this.tiposIngresosAlmacenList = tiposIngresosAlmacenList;
        }

        public List getProveedoresList() {
            return proveedoresList;
        }

        public void setProveedoresList(List proveedoresList) {
            this.proveedoresList = proveedoresList;
        }

        public List<SelectItem> getMesesSelectList() {
            return mesesSelectList;
        }

        public void setMesesSelectList(List<SelectItem> mesesSelectList) {
            this.mesesSelectList = mesesSelectList;
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

        public List getGruposList() {
            return gruposList;
        }

        public void setGruposList(List gruposList) {
            this.gruposList = gruposList;
        }

        public List getMaterialesBuscarList() {
            return materialesBuscarList;
        }

        public void setMaterialesBuscarList(List materialesBuscarList) {
            this.materialesBuscarList = materialesBuscarList;
        }

        public HtmlDataTable getMaterialesBuscarDataTable() {
            return materialesBuscarDataTable;
        }

        public void setMaterialesBuscarDataTable(HtmlDataTable materialesBuscarDataTable) {
            this.materialesBuscarDataTable = materialesBuscarDataTable;
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

        public String getMensaje() {
            return mensaje;
        }

        public void setMensaje(String mensaje) {
            this.mensaje = mensaje;
        }

        public List<SelectItem> getCapitulosList2() {
            return capitulosList2;
        }

        public void setCapitulosList2(List<SelectItem> capitulosList2) {
            this.capitulosList2 = capitulosList2;
        }

        public String getCodCapitulo() {
            return codCapitulo;
        }

        public void setCodCapitulo(String codCapitulo) {
            this.codCapitulo = codCapitulo;
        }

        public String getCodEstadoIngreso() {
            return codEstadoIngreso;
        }

        public void setCodEstadoIngreso(String codEstadoIngreso) {
            this.codEstadoIngreso = codEstadoIngreso;
        }

        public String getCodGestion() {
            return codGestion;
        }

        public void setCodGestion(String codGestion) {
            this.codGestion = codGestion;
        }

        public String getCodGrupo() {
            return codGrupo;
        }

        public void setCodGrupo(String codGrupo) {
            this.codGrupo = codGrupo;
        }

        public String getCodMaterial() {
            return codMaterial;
        }

        public void setCodMaterial(String codMaterial) {
            this.codMaterial = codMaterial;
        }

        public String getCodProveedor() {
            return codProveedor;
        }

        public void setCodProveedor(String codProveedor) {
            this.codProveedor = codProveedor;
        }

        public String getCodTipoCompra() {
            return codTipoCompra;
        }

        public void setCodTipoCompra(String codTipoCompra) {
            this.codTipoCompra = codTipoCompra;
        }

        public String getCodTipoIngreso() {
            return codTipoIngreso;
        }

        public void setCodTipoIngreso(String codTipoIngreso) {
            this.codTipoIngreso = codTipoIngreso;
        }

        public List<SelectItem> getEstadosIngresoAlmacenList() {
            return estadosIngresoAlmacenList;
        }

        public void setEstadosIngresoAlmacenList(List<SelectItem> estadosIngresoAlmacenList) {
            this.estadosIngresoAlmacenList = estadosIngresoAlmacenList;
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

        public boolean isFechas() {
            return fechas;
        }

        public void setFechas(boolean fechas) {
            this.fechas = fechas;
        }

        public List<SelectItem> getGestionesList() {
            return gestionesList;
        }

        public void setGestionesList(List<SelectItem> gestionesList) {
            this.gestionesList = gestionesList;
        }

        public List<SelectItem> getGruposList2() {
            return gruposList2;
        }

        public void setGruposList2(List<SelectItem> gruposList2) {
            this.gruposList2 = gruposList2;
        }

        public List<SelectItem> getMaterialesList() {
            return materialesList;
        }

        public void setMaterialesList(List<SelectItem> materialesList) {
            this.materialesList = materialesList;
        }

        public String getNroIngresoAlmacen() {
            return nroIngresoAlmacen;
        }

        public void setNroIngresoAlmacen(String nroIngresoAlmacen) {
            this.nroIngresoAlmacen = nroIngresoAlmacen;
        }

        public String getNroOrdenCompra() {
            return nroOrdenCompra;
        }

        public void setNroOrdenCompra(String nroOrdenCompra) {
            this.nroOrdenCompra = nroOrdenCompra;
        }

        public List<SelectItem> getProveedoresList2() {
            return proveedoresList2;
        }

        public void setProveedoresList2(List<SelectItem> proveedoresList2) {
            this.proveedoresList2 = proveedoresList2;
        }

        public List<SelectItem> getTiposCompraList2() {
            return tiposCompraList2;
        }

        public void setTiposCompraList2(List<SelectItem> tiposCompraList2) {
            this.tiposCompraList2 = tiposCompraList2;
        }

        public List<SelectItem> getTiposIngresoAlmacenList2() {
            return tiposIngresoAlmacenList2;
        }

        public void setTiposIngresoAlmacenList2(List<SelectItem> tiposIngresoAlmacenList2) {
            this.tiposIngresoAlmacenList2 = tiposIngresoAlmacenList2;
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

        public HtmlDataTable getIngresosAlmacenDetalleEditarDataTable() {
            return ingresosAlmacenDetalleEditarDataTable;
        }

        public void setIngresosAlmacenDetalleEditarDataTable(HtmlDataTable ingresosAlmacenDetalleEditarDataTable) {
            this.ingresosAlmacenDetalleEditarDataTable = ingresosAlmacenDetalleEditarDataTable;
        }

        public boolean isConDensidad() {
            return conDensidad;
        }

        public void setConDensidad(boolean conDensidad) {
            this.conDensidad = conDensidad;
        }

        public boolean isIngresoEditable() {
            return ingresoEditable;
        }


        public void setIngresoEditable(boolean ingresoEditable) {
            this.ingresoEditable = ingresoEditable;
        }

        public IngresosAlmacenDetalleEstado getIngresosAlmacenDetalleEstadoSeleccionado() {
            return ingresosAlmacenDetalleEstadoSeleccionado;
        }

        public void setIngresosAlmacenDetalleEstadoSeleccionado(IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstadoSeleccionado) {
            this.ingresosAlmacenDetalleEstadoSeleccionado = ingresosAlmacenDetalleEstadoSeleccionado;
        }

        public IngresoTransaccionSolicitud getIngresoTransaccionSolicitudGestionar() {
            return ingresoTransaccionSolicitudGestionar;
        }

        public void setIngresoTransaccionSolicitudGestionar(IngresoTransaccionSolicitud ingresoTransaccionSolicitudGestionar) {
            this.ingresoTransaccionSolicitudGestionar = ingresoTransaccionSolicitudGestionar;
        }

        public boolean isPermisoSolicitarAnular() {
            return permisoSolicitarAnular;
        }

        public void setPermisoSolicitarAnular(boolean permisoSolicitarAnular) {
            this.permisoSolicitarAnular = permisoSolicitarAnular;
        }

        public boolean isPermisoSolicitarEditar() {
            return permisoSolicitarEditar;
        }

        public void setPermisoSolicitarEditar(boolean permisoSolicitarEditar) {
            this.permisoSolicitarEditar = permisoSolicitarEditar;
        }

        public boolean isPermisoValidarAnular() {
            return permisoValidarAnular;
        }

        public void setPermisoValidarAnular(boolean permisoValidarAnular) {
            this.permisoValidarAnular = permisoValidarAnular;
        }

        public boolean isPermisoValidarEditar() {
            return permisoValidarEditar;
        }

        public void setPermisoValidarEditar(boolean permisoValidarEditar) {
            this.permisoValidarEditar = permisoValidarEditar;
        }

        public boolean isPermisoAnularDirectamente() {
            return permisoAnularDirectamente;
        }

        public void setPermisoAnularDirectamente(boolean permisoAnularDirectamente) {
            this.permisoAnularDirectamente = permisoAnularDirectamente;
        }

        public boolean isPermisoEditarDirectamente() {
            return permisoEditarDirectamente;
        }

        public void setPermisoEditarDirectamente(boolean permisoEditarDirectamente) {
            this.permisoEditarDirectamente = permisoEditarDirectamente;
        }

        public List<IngresosAlmacen> getIngresosAlmacenValidarList() {
            return ingresosAlmacenValidarList;
        }

        public void setIngresosAlmacenValidarList(List<IngresosAlmacen> ingresosAlmacenValidarList) {
            this.ingresosAlmacenValidarList = ingresosAlmacenValidarList;
        }

        public IngresosAlmacen getIngresoAlmacenBuscar() {
            return ingresoAlmacenBuscar;
        }

        public void setIngresoAlmacenBuscar(IngresosAlmacen ingresoAlmacenBuscar) {
            this.ingresoAlmacenBuscar = ingresoAlmacenBuscar;
        }

        public String getMotivoTransaccionIngreso() {
            return motivoTransaccionIngreso;
        }

        public void setMotivoTransaccionIngreso(String motivoTransaccionIngreso) {
            this.motivoTransaccionIngreso = motivoTransaccionIngreso;
        }

        public List<SalidasAlmacen> getSalidasAsociadasAlmacenList() {
            return salidasAsociadasAlmacenList;
        }

        public void setSalidasAsociadasAlmacenList(List<SalidasAlmacen> salidasAsociadasAlmacenList) {
            this.salidasAsociadasAlmacenList = salidasAsociadasAlmacenList;
        }

        public boolean isTipoSalidaAutomatico() {
            return tipoSalidaAutomatico;
        }

        public void setTipoSalidaAutomatico(boolean tipoSalidaAutomatico) {
            this.tipoSalidaAutomatico = tipoSalidaAutomatico;
        }

        public SalidasAlmacen getSalidaAsociadaGestionar() {
            return salidaAsociadaGestionar;
        }

        public void setSalidaAsociadaGestionar(SalidasAlmacen salidaAsociadaGestionar) {
            this.salidaAsociadaGestionar = salidaAsociadaGestionar;
        }

        public boolean isSalidaTieneDevolucion() {
            return salidaTieneDevolucion;
        }

        public void setSalidaTieneDevolucion(boolean salidaTieneDevolucion) {
            this.salidaTieneDevolucion = salidaTieneDevolucion;
        }

        public boolean isIngresoEditableValidandoConSalidas() {
            return ingresoEditableValidandoConSalidas;
        }

        public void setIngresoEditableValidandoConSalidas(boolean ingresoEditableValidandoConSalidas) {
            this.ingresoEditableValidandoConSalidas = ingresoEditableValidandoConSalidas;
        }

        public boolean isPermisoEditarHoy() {
            return permisoEditarHoy;
        }

        public void setPermisoEditarHoy(boolean permisoEditarHoy) {
            this.permisoEditarHoy = permisoEditarHoy;
        }

        public int getSalidasAsociadasAlmacenListSize() {
            return salidasAsociadasAlmacenList!=null?salidasAsociadasAlmacenList.size():0;
        }

        public int getIndexEliminarDetalleIngreso() {
            return indexEliminarDetalleIngreso;
        }

        public void setIndexEliminarDetalleIngreso(int indexEliminarDetalleIngreso) {
            this.indexEliminarDetalleIngreso = indexEliminarDetalleIngreso;
        }

        public boolean isPermisoAnularHoy() {
            return permisoAnularHoy;
        }

        public void setPermisoAnularHoy(boolean permisoAnularHoy) {
            this.permisoAnularHoy = permisoAnularHoy;
        }
    //</editor-fold>

    
}
