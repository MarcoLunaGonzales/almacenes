/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofar.web;

import com.cofar.bean.Almacenes;
import com.cofar.bean.Devoluciones;
import com.cofar.bean.DevolucionesDetalle;
import com.cofar.bean.DevolucionesDetalleEtiquetas;
import com.cofar.bean.IngresosAlmacen;
import com.cofar.bean.IngresosAlmacenDetalle;
import com.cofar.bean.IngresosAlmacenDetalleEstado;
import com.cofar.bean.SalidasAlmacen;
import com.cofar.bean.SalidasAlmacenDetalleIngreso;
import com.cofar.bean.Secciones;
import com.cofar.bean.SolicitudDevoluciones;
import com.cofar.bean.Traspasos;
import com.cofar.service.AssignCostServiceMultiple;
import com.cofar.thread.ThreadCosteoByCodIngreso;
import com.cofar.util.Util;
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
import org.richfaces.component.html.HtmlDataTable;

public class ManagedSolicitudDevoluciones extends ManagedBean {

    private Logger LOGGER;
    private static final Double COSTO_GRAMO_AGUA = 0.00058d;
    //conexion de la base de datos abrir y cerrar la misma en cada proceso
    private Connection con = null;
    private ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
    List devolucionesList = new ArrayList();
    SalidasAlmacen salidasAlmacenBuscar = new SalidasAlmacen();
    List componentesProdList = new ArrayList();
    List salidasAlmacenList = new ArrayList();
    List salidasAlmacenBuscarList = new ArrayList();
    int inicioFila = 0;
    int finalFila = 0;
    int cantidadFilasBuscar = 0;
    SalidasAlmacen salidasAlmacen = new SalidasAlmacen();
    List salidasAlmacenDetalleList = new ArrayList();
    Devoluciones devoluciones = new Devoluciones();
    List devolucionesDetalleList = new ArrayList();
    private List solicitudesDetalleList = new ArrayList();
    List devolucionesDetalleEtiquetasList = new ArrayList();
    HtmlDataTable devolucionesDetalleDataTable = new HtmlDataTable();
    DevolucionesDetalle devolucionesDetalle = new DevolucionesDetalle();
    String mensaje = "";
    Almacenes almacenesFrv = new Almacenes();
    HtmlDataTable salidasAlmacenDataTable = new HtmlDataTable();
    IngresosAlmacen ingresosAlmacen = new IngresosAlmacen();
    private String observacion = "";
    private int codSolicitud = 0;
    private List<SolicitudDevoluciones> solicitudesList = new ArrayList<SolicitudDevoluciones>();
    private List<SolicitudDevoluciones> solicitudesAprobarList = new ArrayList<SolicitudDevoluciones>();
    private SolicitudDevoluciones buscarSolicitud = new SolicitudDevoluciones();
    private SolicitudDevoluciones buscarSolicitudAprobar = new SolicitudDevoluciones();
    private List<SelectItem> estadosSolicitudesList = new ArrayList<SelectItem>();
    private List<SelectItem> productosList = new ArrayList<SelectItem>();
    private List<SelectItem> almacenesList = new ArrayList<SelectItem>();
    private java.util.Date fechaInicio = new Date();
    private java.util.Date fechafinal = new Date();
    private java.util.Date fechaInicio1 = new Date();
    private java.util.Date fechaFinal1 = new Date();
    private List<SelectItem> personalList = new ArrayList<SelectItem>();
    private SolicitudDevoluciones solicitudAprobar = new SolicitudDevoluciones();
    private List maquinariaList = new ArrayList();
    private boolean administradorAlmacen = false;
    private SolicitudDevoluciones solicitudDevolucionAnular;
    private boolean permisoDevolucionMateriaPrima = false;
    // <editor-fold defaultstate="collapsed" desc="frv de aspiradora">
        private Devoluciones devolucionFrvAspiradora = new Devoluciones();
        private List<SelectItem> areasEmpresaSelectList;
    //</editor-fold>
    // para solicitudes por lote proveedor
    private SalidasAlmacenDetalleIngreso salidasAlmacenDetalleIngresoBuscar = null;
    private Date fechaInicioSalida = new Date();
    private Date fechaFinalSalida = new Date();
    private HtmlDataTable salidasAlmacenLoteProveedorDataTable = null;
    private HtmlDataTable devolucionesDetalleProveedorDataTabla = new HtmlDataTable();
    private List<Integer> codigosSolicitudDevolucionGeneradas =new ArrayList<Integer>();
    private List<SelectItem> tipoFrvProcesoSelectList;
    // <editor-fold defaultstate="collapsed" desc="funcionalidad registro frv aspiradora">
        private void cargarPermisosEspecialesPersonal()
        {
            try {
                con = Util.openConnection(con);
                permisoDevolucionMateriaPrima = false;
                ManagedAccesoSistema managed = (ManagedAccesoSistema)Util.getSessionBean("ManagedAccesoSistema");
                StringBuilder consulta = new StringBuilder("select cpeb.COD_TIPO_PERMISO_ESPECIAL_BACO")
                                                    .append(" from CONFIGURACION_PERMISO_ESPECIAL_BACO cpeb")
                                                    .append(" where cpeb.COD_TIPO_PERMISO_ESPECIAL_BACO in (15)")
                                                            .append(" and cpeb.COD_PERSONAL =").append(managed.getUsuarioModuloBean().getCodUsuarioGlobal())
                                                            .append(" and cpeb.COD_ALMACEN =").append(managed.getAlmacenesGlobal().getCodAlmacen());
                LOGGER.debug(" consulta cargar permisos especiales "+consulta.toString());
                Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet res = st.executeQuery(consulta.toString());
                while (res.next()) 
                {
                    switch(res.getInt("COD_TIPO_PERMISO_ESPECIAL_BACO"))
                    {
                        case 15:
                        {
                            permisoDevolucionMateriaPrima = true;
                            break;
                        }
                    }
                }
                
            } catch (SQLException ex) {
                LOGGER.warn(ex.getMessage());
            } catch (NumberFormatException ex) {
                LOGGER.warn(ex.getMessage());
            } finally {
                try
                {
                    con.close();
                }
                catch(SQLException ex)
                {
                    LOGGER.warn(ex.getMessage());;
                }
            }
        }
        private void cargarAreasEmpresaGeneraFrvResidual()throws SQLException
        {
            try {
                con = Util.openConnection(con);
                StringBuilder consulta = new StringBuilder("select DISTINCT ae.COD_AREA_EMPRESA,ae.NOMBRE_AREA_EMPRESA")
                                                    .append(" from MATERIALES_FRV_RESIDUAL mfr")
                                                            .append(" inner join AREAS_EMPRESA ae on ae.COD_AREA_EMPRESA = mfr.COD_AREA_EMPRESA_DESTINO")
                                                    .append(" order by ae.NOMBRE_AREA_EMPRESA");
                LOGGER.debug("consulta cargar areas empresa frv residual "+consulta.toString());
                Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet res = st.executeQuery(consulta.toString());
                areasEmpresaSelectList  = new ArrayList<SelectItem>();
                while (res.next()) 
                {
                    areasEmpresaSelectList.add(new SelectItem(res.getString("COD_AREA_EMPRESA"),res.getString("NOMBRE_AREA_EMPRESA")));
                }
                
                mensaje = "1";
            } catch (SQLException ex) {
                LOGGER.warn(ex.getMessage());
            } catch (NumberFormatException ex) {
                LOGGER.warn(ex.getMessage());
            } finally {
                con.close();
            }
        }
        public String guardarSolicitudDevolucionFrvAspiradora()throws SQLException
        {
            try {
                con = Util.openConnection(con);
                con.setAutoCommit(false);
                codigosSolicitudDevolucionGeneradas = new ArrayList<Integer>();
                StringBuilder consulta = new StringBuilder("select isnull(max(sd.COD_SOLICITUD_DEVOLUCION),0)+1 as codSolicitud")
                                                .append(" from SOLICITUD_DEVOLUCIONES sd ");
                LOGGER.debug("consulta codigo solicitud "+consulta.toString());
                Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                ResultSet res = st.executeQuery(consulta.toString());
                int codSolicitudDevolucion = 0;
                if(res.next())codSolicitudDevolucion = res.getInt("codSolicitud");
                ManagedAccesoSistema managedAccesoSistema = (ManagedAccesoSistema)Util.getSessionBean("ManagedAccesoSistema");
                codigosSolicitudDevolucionGeneradas.add(codSolicitudDevolucion);
                consulta = new StringBuilder("INSERT INTO SOLICITUD_DEVOLUCIONES(COD_SOLICITUD_DEVOLUCION,COD_ESTADO_SOLICITUD_DEVOLUCION, FECHA_SOLICITUD,")
                                                .append(" COD_PERSONAL, OBSERVACION,COD_SALIDA_ALMACEN,COD_ALMACEN_DESTINO)")
                                                .append(" VALUES (")
                                                        .append(codSolicitudDevolucion).append(",")
                                                        .append("1,")//estado generado
                                                        .append("getdate(),")//fecha de solicitud
                                                        .append(managedAccesoSistema.getUsuarioModuloBean().getCodUsuarioGlobal()).append(",")
                                                        .append("?,")//observacion
                                                        .append("(select top 1 a.COD_SALIDA_ALMACEN_DESTINO_DEV ")
                                                        .append(" from MATERIALES_FRV_RESIDUAL a")
                                                        .append(" where a.COD_AREA_EMPRESA_DESTINO=").append(devolucionFrvAspiradora.getSalidasAlmacen().getAreasEmpresa().getCodAreaEmpresa()).append("),")//sin salida de almacen
                                                        .append("21")//frv no deducible
                                                .append(")");
                LOGGER.debug("consulta registrar solicitud frv aspiradora "+consulta.toString());
                PreparedStatement pst = con.prepareStatement(consulta.toString());
                pst.setString(1, devolucionFrvAspiradora.getObsDevolucion());LOGGER.info("p1 : "+devolucionFrvAspiradora.getObsDevolucion());
                if (pst.executeUpdate() > 0) LOGGER.info("se registro la solicitud de frv lavadora");
                for(DevolucionesDetalle bean : devolucionFrvAspiradora.getDevolucionesDetalleList())
                {
                    if(bean.getCantidadDevueltaFallados() > 0)
                    {
                        consulta = new StringBuilder(" INSERT INTO SOLICITUD_DEVOLUCIONES_DETALLE(COD_SOLICITUD_DEVOLUCION,COD_MATERIAL, CANTIDAD_DEVUELTA, CANTIDAD_DEVUELTA_FALLADOS,")
                                                    .append(" CANTIDAD_DEVUELTA_FALLADOS_PROVEEDOR, COD_UNIDAD_MEDIDA, COD_SALIDA_ALMACEN)")
                                            .append(" VALUES (")
                                                    .append(codSolicitudDevolucion).append(",")
                                                    .append(bean.getMateriales().getCodMaterial()).append(",")
                                                    .append("0,")//cantidad fallados
                                                    .append(bean.getCantidadDevueltaFallados()).append(",")
                                                    .append("0,")//fallados proveedor
                                                    .append(bean.getUnidadesMedida().getCodUnidadMedida()).append(",")
                                                    .append("0")
                                            .append(")");
                        LOGGER.debug("consulta registrar frv "+consulta.toString());
                        pst = con.prepareStatement(consulta.toString());
                        if(pst.executeUpdate()>0)LOGGER.info("se registro frv de aspiradora");
                        consulta = new StringBuilder(" INSERT INTO SOLICITUD_DEVOLUCIONES_DETALLE_ETIQUETAS(COD_SOLICITUD_DEVOLUCION, COD_INGRESO_ALMACEN, COD_MATERIAL, ETIQUETA,")
                                                    .append("CANTIDAD_DEVUELTA, CANTIDAD_DEVUELTA_FALLADOS,CANTIDAD_DEVUELTA_FALLADOS_PROVEEDOR)")
                                            .append("VALUES (")
                                                    .append(codSolicitudDevolucion).append(",")
                                                    .append("0,")
                                                    .append(bean.getMateriales().getCodMaterial()).append(",")
                                                    .append("1,")
                                                    .append("0,")//cantidad devuelta buenos
                                                    .append(bean.getCantidadDevueltaFallados()).append(",")
                                                    .append("0")
                                            .append(")");
                        LOGGER.debug("consulta registrar detalle etiqueta "+consulta.toString());
                        pst=con.prepareStatement(consulta.toString());
                        if(pst.executeUpdate()>0)LOGGER.info("se registro el detalle etiqueta");
                        
                    }
                }
                con.commit();
                mensaje = "1";
                pst.close();
            } catch (SQLException ex) {
                mensaje = "Ocurrio un error de datos al momento de guardar la desviacion, verifique la información e intente de nuevo";
                LOGGER.warn(ex.getMessage());
                con.rollback();
            } catch (NumberFormatException ex) {
                mensaje = "Ocurrio un error de datos al momento de guardar la desviacion, verifique la información e intente de nuevo";
                LOGGER.warn(ex.getMessage());
                con.rollback();
            } finally {
                con.close();
            }
            return null;
        }
        public String getCargarAgregarSolDevFrvAspiradora()throws SQLException
        {
            devolucionFrvAspiradora = new Devoluciones();
            devolucionFrvAspiradora.setDevolucionesDetalleList(new ArrayList<DevolucionesDetalle>());
            this.cargarAreasEmpresaGeneraFrvResidual();
            return null;
        }
        public String codAreaEmpresaFrvResidual_change()
        {
            devolucionFrvAspiradora.setDevolucionesDetalleList(new ArrayList<DevolucionesDetalle>());
            try {
                con = Util.openConnection(con);
                StringBuilder consulta = new StringBuilder("select m.COD_MATERIAL,m.NOMBRE_MATERIAL")
                                                        .append(" ,um.COD_UNIDAD_MEDIDA,um.ABREVIATURA")
                                                .append(" from MATERIALES_FRV_RESIDUAL mfr")
                                                .append(" inner join materiales m  on m.COD_MATERIAL=mfr.COD_MATERIAL")
                                                        .append(" inner join UNIDADES_MEDIDA um on um.COD_UNIDAD_MEDIDA=m.COD_UNIDAD_MEDIDA")
                                                .append(" where mfr.COD_AREA_EMPRESA_DESTINO = ").append(devolucionFrvAspiradora.getSalidasAlmacen().getAreasEmpresa().getCodAreaEmpresa())
                                                .append(" order by m.NOMBRE_MATERIAL");
                LOGGER.debug("consulta cargar materiales frv residual "+consulta.toString());
                Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet res = st.executeQuery(consulta.toString());
                
                while (res.next()) {
                    DevolucionesDetalle detalleFrv = new DevolucionesDetalle();
                    detalleFrv.getMateriales().setCodMaterial(res.getString("COD_MATERIAL"));
                    detalleFrv.getMateriales().setNombreMaterial(res.getString("NOMBRE_MATERIAL"));
                    detalleFrv.getUnidadesMedida().setCodUnidadMedida(res.getInt("COD_UNIDAD_MEDIDA"));
                    detalleFrv.getUnidadesMedida().setAbreviatura(res.getString("ABREVIATURA"));
                    devolucionFrvAspiradora.getDevolucionesDetalleList().add(detalleFrv);
                }
                
            } catch (SQLException ex) {
                LOGGER.warn(ex.getMessage());
            } catch (NumberFormatException ex) {
                LOGGER.warn(ex.getMessage());
            } finally {
                try
                {
                con.close();
                }catch(SQLException ex)
                {
                    LOGGER.warn(ex.getMessage());
                }
            }
            return null;
        }
    //</editor-fold>
    private void cargarTipoFrvProcesoSelectList()
    {
        
        Connection con=null;
        try {
            con = Util.openConnection(con);
            StringBuilder consulta = new StringBuilder("select tfp.COD_TIPO_FRV_PROCESO,tfp.NOMBRE_TIPO_FRV_PROCESO");
                                        consulta.append(" from TIPO_FRV_PROCESO tfp");
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet res = st.executeQuery(consulta.toString());
            tipoFrvProcesoSelectList = new ArrayList<SelectItem>();
            while (res.next()) {
                tipoFrvProcesoSelectList.add(new SelectItem(res.getInt("COD_TIPO_FRV_PROCESO"),res.getString("NOMBRE_TIPO_FRV_PROCESO")));
            }
        } catch (SQLException ex) {
            LOGGER.warn(ex.getMessage());
        } catch (NumberFormatException ex) {
            LOGGER.warn(ex.getMessage());
        } finally {
            try
            {
                con.close();
            }
            catch(SQLException ex)
            {
                LOGGER.warn(ex.getMessage());
            }
        }
    }
    public String mostrarBuscarSalidaSolicitud_action() {
        salidasAlmacenBuscarList.clear();
        return null;
    }

    public String mostrarBuscarSalidaLoteProveedor_action() {
        salidasAlmacenLoteProveedorDataTable = new HtmlDataTable();
        salidasAlmacenBuscarList.clear();
        salidasAlmacenDetalleIngresoBuscar = new SalidasAlmacenDetalleIngreso();
        return null;
    }

    public List getDevolucionesList() {
        return devolucionesList;
    }

    public void setDevolucionesList(List devolucionesList) {
        this.devolucionesList = devolucionesList;
    }

    public void cargarEstadosSolList() {
        Connection con = null;
        try {
            con = Util.openConnection(con);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String consulta = "select esd.COD_ESTADO_SOLICITUD_DEVOLUCION,esd.NOMBRE_ESTADO from ESTADOS_SOLICITUD_DEVOLUCION esd";
            ResultSet rest2 = st.executeQuery(consulta);
            getEstadosSolicitudesList().clear();
            getEstadosSolicitudesList().add(new SelectItem("0", "TODOS"));

            while (rest2.next()) {
                getEstadosSolicitudesList().add(new SelectItem(rest2.getString("COD_ESTADO_SOLICITUD_DEVOLUCION"), rest2.getString("NOMBRE_ESTADO")));
            }
            rest2.close();
            st.close();
            con.close();
        } catch (SQLException ex) {
            LOGGER.warn(ex.getMessage());
        }
    }

    public void cargarProductos() {
        Connection con = null;
        try {
            con = Util.openConnection(con);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String consulta = "select cp.nombre_prod_semiterminado,cp.COD_COMPPROD from COMPONENTES_PROD cp order by cp.nombre_prod_semiterminado ";
            ResultSet rest = st.executeQuery(consulta);
            getProductosList().clear();
            getProductosList().add(new SelectItem("0", "-TODOS-"));
            while (rest.next()) {
                getProductosList().add(new SelectItem(rest.getString("COD_COMPPROD"), rest.getString("nombre_prod_semiterminado")));
            }

        } catch (SQLException ex) {
            LOGGER.warn(ex.getMessage());
        }
    }

    public void cargarAlmacenes() {
        Connection con = null;
        try {
            con = Util.openConnection(con);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String consulta = "select a.COD_ALMACEN,a.NOMBRE_ALMACEN from ALMACENES a where a.COD_ESTADO_REGISTRO in(1) order by a.NOMBRE_ALMACEN";
            ResultSet rest = st.executeQuery(consulta);
            getAlmacenesList().clear();
            getAlmacenesList().add(new SelectItem(0, "-TODOS-"));
            while (rest.next()) {
                getAlmacenesList().add(new SelectItem(rest.getInt("COD_ALMACEN"), rest.getString("NOMBRE_ALMACEN")));
            }
        } catch (SQLException ex) {
            LOGGER.warn(ex.getMessage());
        }

    }

    public void cargarPersonal() {
        Connection con = null;
        try {
            con = Util.openConnection(con);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String consulta = "select p.COD_PERSONAL,(p.AP_PATERNO_PERSONAL+' '+p.AP_MATERNO_PERSONAL+' '+p.NOMBRE_PILA) as nombrePer from PERSONAL p order by p.AP_PATERNO_PERSONAL";
            ResultSet res = st.executeQuery(consulta);
            getPersonalList().clear();
            getPersonalList().add(new SelectItem("0", "-TODOS-"));
            while (res.next()) {
                getPersonalList().add(new SelectItem(res.getString("COD_PERSONAL"), res.getString("nombrePer")));
            }

        } catch (SQLException ex) {
            LOGGER.warn(ex.getMessage());
        }

    }

    public SalidasAlmacen getSalidasAlmacenBuscar() {
        return salidasAlmacenBuscar;
    }

    public void setSalidasAlmacenBuscar(SalidasAlmacen salidasAlmacenBuscar) {
        this.salidasAlmacenBuscar = salidasAlmacenBuscar;
    }

    public List getComponentesProdList() {
        return componentesProdList;
    }

    public void setComponentesProdList(List componentesProdList) {
        this.componentesProdList = componentesProdList;
    }

    public SalidasAlmacen getSalidasAlmacen() {
        return salidasAlmacen;
    }

    public void setSalidasAlmacen(SalidasAlmacen salidasAlmacen) {
        this.salidasAlmacen = salidasAlmacen;
    }

    public List getSalidasAlmacenList() {
        return salidasAlmacenList;
    }

    public void setSalidasAlmacenList(List salidasAlmacenList) {
        this.salidasAlmacenList = salidasAlmacenList;
    }

    public List getSalidasAlmacenBuscarList() {
        return salidasAlmacenBuscarList;
    }

    public void setSalidasAlmacenBuscarList(List salidasAlmacenBuscarList) {
        this.salidasAlmacenBuscarList = salidasAlmacenBuscarList;
    }

    public int getCantidadFilasBuscar() {
        return cantidadFilasBuscar;
    }

    public void setCantidadFilasBuscar(int cantidadFilasBuscar) {
        this.cantidadFilasBuscar = cantidadFilasBuscar;
    }

    public int getFinalFila() {
        return finalFila;
    }

    public void setFinalFila(int finalFila) {
        this.finalFila = finalFila;
    }

    public int getInicioFila() {
        return inicioFila;
    }

    public void setInicioFila(int inicioFila) {
        this.inicioFila = inicioFila;
    }

    public List getSalidasAlmacenDetalleList() {
        return salidasAlmacenDetalleList;
    }

    public void setSalidasAlmacenDetalleList(List salidasAlmacenDetalleList) {
        this.salidasAlmacenDetalleList = salidasAlmacenDetalleList;
    }

    public List getDevolucionesDetalleList() {
        return devolucionesDetalleList;
    }

    public void setDevolucionesDetalleList(List devolucionesDetalleList) {
        this.devolucionesDetalleList = devolucionesDetalleList;
    }

    public List getDevolucionesDetalleEtiquetasList() {
        return devolucionesDetalleEtiquetasList;
    }

    public void setDevolucionesDetalleEtiquetasList(List devolucionesDetalleEtiquetasList) {
        this.devolucionesDetalleEtiquetasList = devolucionesDetalleEtiquetasList;
    }

    public HtmlDataTable getDevolucionesDetalleDataTable() {
        return devolucionesDetalleDataTable;
    }

    public void setDevolucionesDetalleDataTable(HtmlDataTable devolucionesDetalleDataTable) {
        this.devolucionesDetalleDataTable = devolucionesDetalleDataTable;
    }

    public DevolucionesDetalle getDevolucionesDetalle() {
        return devolucionesDetalle;
    }

    public void setDevolucionesDetalle(DevolucionesDetalle devolucionesDetalle) {
        this.devolucionesDetalle = devolucionesDetalle;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Almacenes getAlmacenesFrv() {
        return almacenesFrv;
    }

    public void setAlmacenesFrv(Almacenes almacenesFrv) {
        this.almacenesFrv = almacenesFrv;
    }

    public HtmlDataTable getSalidasAlmacenDataTable() {
        return salidasAlmacenDataTable;
    }

    public void setSalidasAlmacenDataTable(HtmlDataTable salidasAlmacenDataTable) {
        this.salidasAlmacenDataTable = salidasAlmacenDataTable;
    }

    /**
     * Creates a new instance of ManagedDevolucionAlmacen
     */
    public ManagedSolicitudDevoluciones() {
        LOGGER = LogManager.getLogger("SolicitudDevoluciones");
    }

    public String getCargarContenidoDevolucionAlmacen() {
        try {
            begin = 1;
            end = 10;

            getBuscarSolicitud().getEstadoSolicitudDevolucion().setCodEstado("0");
            this.cargarEstadosSolList();
            getBuscarSolicitud().getSalidaAlmacen().getProducto().setCodProducto("0");
            this.cargarProductos();
            getBuscarSolicitud().getSalidaAlmacen().getAlmacenes().setCodAlmacen(0);
            this.cargarAlmacenes();
            getBuscarSolicitud().setCodSolicitudDevolucion("");
            getBuscarSolicitud().getSalidaAlmacen().setNroSalidaAlmacen(0);
            getBuscarSolicitud().setFechaSolicitud(null);
            setFechaInicio(null);
            setFechafinal(null);
            this.listadoDevolucionesAlmacen();
            componentesProdList = this.listaComponentesProd();
            cantidadfilas = solicitudesList.size();
            this.cargarMaquinarias();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String buscarSolicitudDevolucionAction() {
        try {
            begin = 1;
            end = 10;
            this.listadoDevolucionesAlmacen();
            //this.cargarSalidasAlmacen();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String buscarSolicitudDevolucionAprobarAction() {
        try {
            begin = 1;
            end = 10;
            this.cargarListadoSolicitudesAprobar();
            //this.cargarSalidasAlmacen();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
            LOGGER.info("consulta verificar administrador sistema " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet res = st.executeQuery(consulta.toString());
            while (res.next()) {
                administradorAlmacen = (res.getInt("ADMINISTRADOR_ALMACEN") > 0);
            }
            st.close();
        } catch (SQLException ex) {
            LOGGER.warn(ex.getMessage());
        }
    }

    public String getCargarSolicitudesAprobar() {
        this.cargarPermisoPersonal();
        try {
            begin = 1;
            end = 10;
            getBuscarSolicitudAprobar().getEstadoSolicitudDevolucion().setCodEstado("0");
            this.cargarEstadosSolList();
            getBuscarSolicitudAprobar().getSalidaAlmacen().getProducto().setCodProducto("0");
            this.cargarProductos();
            getBuscarSolicitudAprobar().getSalidaAlmacen().getAlmacenes().setCodAlmacen(0);
            this.cargarAlmacenes();
            getBuscarSolicitudAprobar().getPersonalSolicitante().setCodPersonal("0");
            this.cargarPersonal();
            getBuscarSolicitudAprobar().setCodSolicitudDevolucion("");
            getBuscarSolicitudAprobar().getSalidaAlmacen().setNroSalidaAlmacen(0);
            getBuscarSolicitudAprobar().setFechaSolicitud(null);
            getBuscarSolicitudAprobar().getPersonalSolicitante().setCodPersonal("0");
            setFechaFinal1(null);
            setFechaInicio1(null);
            this.cargarListadoSolicitudesAprobar();

            // componentesProdList = this.listaComponentesProd();
            cantidadfilas = getSolicitudesAprobarList().size();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void listadoDevolucionesAlmacenAprob() {
        try {
            Connection con = null;
            con = Util.openConnection(con);
            SimpleDateFormat sdt = new SimpleDateFormat("yyyy/MM/dd");
            String consulta = "select * from (select ROW_NUMBER() OVER (order by sd.COD_SOLICITUD_DEVOLUCION desc) as 'FILAS',"
                    + "s.NRO_SALIDA_ALMACEN,cp.nombre_prod_semiterminado,a.NOMBRE_ALMACEN,"
                    + "(p.AP_PATERNO_PERSONAL+' '+p.AP_MATERNO_PERSONAL+' '+p.NOMBRE_PILA) as nombrePer,"
                    + "sd.COD_SOLICITUD_DEVOLUCION,esd.NOMBRE_ESTADO,sd.COD_ESTADO_SOLICITUD_DEVOLUCION,sd.COD_PERSONAL,sd.COD_SALIDA_ALMACEN,sd.FECHA_SOLICITUD,sd.OBSERVACION from "
                    + "SOLICITUD_DEVOLUCIONES sd inner join PERSONAL p on sd.COD_PERSONAL=p.COD_PERSONAL "
                    + "inner join SALIDAS_ALMACEN s on s.COD_SALIDA_ALMACEN=sd.COD_SALIDA_ALMACEN "
                    + "left outer join COMPONENTES_PROD cp on cp.COD_COMPPROD=s.COD_PROD "
                    + "inner join ALMACENES a on a.COD_ALMACEN=s.COD_ALMACEN "
                    + "inner join ESTADOS_SOLICITUD_DEVOLUCION esd on sd.COD_ESTADO_SOLICITUD_DEVOLUCION=esd.COD_ESTADO_SOLICITUD_DEVOLUCION"
                    + " where  a.cod_estado_registro = 1 ";
            if (!buscarSolicitud.getCodSolicitudDevolucion().equals("")) {
                consulta += " and sd.COD_SOLICITUD_DEVOLUCION ='" + buscarSolicitud.getCodSolicitudDevolucion() + "'";
            }
            if (!buscarSolicitud.getEstadoSolicitudDevolucion().getCodEstado().equals("0")) {
                consulta += " and sd.COD_ESTADO_SOLICITUD_DEVOLUCION='" + buscarSolicitud.getEstadoSolicitudDevolucion().getCodEstado() + "'";

            }
            if (fechaInicio != null && fechafinal != null) {
                consulta += " and sd.FECHA_SOLICITUD BETWEEN '" + sdt.format(fechaInicio) + " 00:00:00' and '" + sdt.format(fechafinal) + " 23:59:59' ";
            }
            if (buscarSolicitud.getSalidaAlmacen().getNroSalidaAlmacen() > 0) {
                consulta += " and s.NRO_SALIDA_ALMACEN ='" + buscarSolicitud.getSalidaAlmacen().getNroSalidaAlmacen() + "'";
            }
            if (buscarSolicitud.getSalidaAlmacen().getAlmacenes().getCodAlmacen() > 0) {
                consulta += " and s.COD_ALMACEN= '" + buscarSolicitud.getSalidaAlmacen().getAlmacenes().getCodAlmacen() + "'";
            }
            if (!buscarSolicitud.getSalidaAlmacen().getProducto().getCodProducto().equals("0")) {
                consulta += " and s.COD_PROD='" + buscarSolicitud.getSalidaAlmacen().getProducto().getCodProducto() + "'";

            }
            consulta += "  ) AS listado  ";
            LOGGER.info("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            getSolicitudesList().clear();
            while (rs.next()) {
                SolicitudDevoluciones nuevaSolicitud = new SolicitudDevoluciones();

                nuevaSolicitud.getSalidaAlmacen().setCodSalidaAlmacen(rs.getInt("COD_SALIDA_ALMACEN"));
                nuevaSolicitud.getSalidaAlmacen().setNroSalidaAlmacen(rs.getInt("NRO_SALIDA_ALMACEN"));
                nuevaSolicitud.getSalidaAlmacen().getProducto().setNombreProducto(rs.getString("nombre_prod_semiterminado"));
                nuevaSolicitud.getSalidaAlmacen().getAlmacenes().setNombreAlmacen(rs.getString("NOMBRE_ALMACEN"));
                nuevaSolicitud.setCodSolicitudDevolucion(rs.getString("COD_SOLICITUD_DEVOLUCION"));
                nuevaSolicitud.getEstadoSolicitudDevolucion().setCodEstado(rs.getString("COD_ESTADO_SOLICITUD_DEVOLUCION"));
                nuevaSolicitud.getEstadoSolicitudDevolucion().setNombreEstado(rs.getString("NOMBRE_ESTADO"));
                nuevaSolicitud.setFechaSolicitud(rs.getTimestamp("FECHA_SOLICITUD"));
                nuevaSolicitud.setObservacion(rs.getString("OBSERVACION"));
                nuevaSolicitud.getPersonalSolicitante().setCodPersonal(rs.getString("COD_PERSONAL"));
                nuevaSolicitud.getPersonalSolicitante().setNombrePersonal(rs.getString("nombrePer"));
                getSolicitudesList().add(nuevaSolicitud);
            }
            rs.close();
            st.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void listadoDevolucionesAlmacen() {
        try {
            Connection con = null;
            con = Util.openConnection(con);
            SimpleDateFormat sdt = new SimpleDateFormat("yyyy/MM/dd");
            String consulta = "select * from (select ROW_NUMBER() OVER (order by sd.COD_SOLICITUD_DEVOLUCION desc) as 'FILAS',"
                                    + "s.NRO_SALIDA_ALMACEN,cp.nombre_prod_semiterminado,a.NOMBRE_ALMACEN,"
                                    + "(p.AP_PATERNO_PERSONAL+' '+p.AP_MATERNO_PERSONAL+' '+p.NOMBRE_PILA) as nombrePer,"
                                    + "sd.COD_SOLICITUD_DEVOLUCION,esd.NOMBRE_ESTADO,sd.COD_ESTADO_SOLICITUD_DEVOLUCION,sd.COD_PERSONAL,sd.COD_SALIDA_ALMACEN,sd.FECHA_SOLICITUD,sd.OBSERVACION "
                                    +" ,sd.COD_ALMACEN_DESTINO,ad.NOMBRE_ALMACEN as nombreAlmacenDestino"
                                    + ", case when sd.COD_SOLICITUD_DEVOLUCION in (0";
                                    for(int codigo : codigosSolicitudDevolucionGeneradas)
                                        consulta += ","+codigo;
                                    consulta += ") then 1 else 0 end as ultimaSolicitud"
                            + " from  SOLICITUD_DEVOLUCIONES sd inner join PERSONAL p on sd.COD_PERSONAL=p.COD_PERSONAL "
                                    + " inner join SALIDAS_ALMACEN s on s.COD_SALIDA_ALMACEN=sd.COD_SALIDA_ALMACEN "
                                    + " left outer join COMPONENTES_PROD cp on cp.COD_COMPPROD=s.COD_PROD "
                                    + " inner join ALMACENES a on a.COD_ALMACEN=s.COD_ALMACEN "
                                    + " inner join ESTADOS_SOLICITUD_DEVOLUCION esd on sd.COD_ESTADO_SOLICITUD_DEVOLUCION=esd.COD_ESTADO_SOLICITUD_DEVOLUCION"
                                    + " inner join ALMACENES ad on ad.COD_ALMACEN=sd.COD_ALMACEN_DESTINO"
                            + " where sd.COD_PERSONAL='" + usuario.getUsuarioModuloBean().getCodUsuarioGlobal() + "'";
            if (!buscarSolicitud.getCodSolicitudDevolucion().equals("")) {
                consulta += " and sd.COD_SOLICITUD_DEVOLUCION ='" + buscarSolicitud.getCodSolicitudDevolucion() + "'";
            }
            if (!buscarSolicitud.getEstadoSolicitudDevolucion().getCodEstado().equals("0")) {
                consulta += " and sd.COD_ESTADO_SOLICITUD_DEVOLUCION='" + buscarSolicitud.getEstadoSolicitudDevolucion().getCodEstado() + "'";

            }
            if (fechaInicio != null && fechafinal != null) {
                consulta += " and sd.FECHA_SOLICITUD BETWEEN '" + sdt.format(fechaInicio) + " 00:00:00' and '" + sdt.format(fechafinal) + " 23:59:59' ";
            }
            if (buscarSolicitud.getSalidaAlmacen().getNroSalidaAlmacen() > 0) {
                consulta += " and s.NRO_SALIDA_ALMACEN ='" + buscarSolicitud.getSalidaAlmacen().getNroSalidaAlmacen() + "'";
            }
            if (buscarSolicitud.getSalidaAlmacen().getAlmacenes().getCodAlmacen() > 0) {
                consulta += " and s.COD_ALMACEN= '" + buscarSolicitud.getSalidaAlmacen().getAlmacenes().getCodAlmacen() + "'";
            }
            if (!buscarSolicitud.getSalidaAlmacen().getProducto().getCodProducto().equals("0")) {
                consulta += " and s.COD_PROD='" + buscarSolicitud.getSalidaAlmacen().getProducto().getCodProducto() + "'";

            }
            consulta += "  ) AS listado where FILAS BETWEEN " + begin + " AND " + end + "  ";
            LOGGER.info("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            getSolicitudesList().clear();
            while (rs.next()) {
                SolicitudDevoluciones nuevaSolicitud = new SolicitudDevoluciones();
                nuevaSolicitud.setChecked(rs.getInt("ultimaSolicitud")>0);
                nuevaSolicitud.getSalidaAlmacen().setCodSalidaAlmacen(rs.getInt("COD_SALIDA_ALMACEN"));
                nuevaSolicitud.getSalidaAlmacen().setNroSalidaAlmacen(rs.getInt("NRO_SALIDA_ALMACEN"));
                nuevaSolicitud.getSalidaAlmacen().getProducto().setNombreProducto(rs.getString("nombre_prod_semiterminado"));
                nuevaSolicitud.getSalidaAlmacen().getAlmacenes().setNombreAlmacen(rs.getString("NOMBRE_ALMACEN"));
                nuevaSolicitud.setCodSolicitudDevolucion(rs.getString("COD_SOLICITUD_DEVOLUCION"));
                nuevaSolicitud.getEstadoSolicitudDevolucion().setCodEstado(rs.getString("COD_ESTADO_SOLICITUD_DEVOLUCION"));
                nuevaSolicitud.getEstadoSolicitudDevolucion().setNombreEstado(rs.getString("NOMBRE_ESTADO"));
                nuevaSolicitud.setFechaSolicitud(rs.getTimestamp("FECHA_SOLICITUD"));
                nuevaSolicitud.setObservacion(rs.getString("OBSERVACION"));
                nuevaSolicitud.getPersonalSolicitante().setCodPersonal(rs.getString("COD_PERSONAL"));
                nuevaSolicitud.getPersonalSolicitante().setNombrePersonal(rs.getString("nombrePer"));
                nuevaSolicitud.getAlmacenDestino().setCodAlmacen(rs.getInt("COD_ALMACEN_DESTINO"));
                nuevaSolicitud.getAlmacenDestino().setNombreAlmacen(rs.getString("nombreAlmacenDestino"));
                getSolicitudesList().add(nuevaSolicitud);
            }
            rs.close();
            st.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String aprobarSolicitud() {
        for (SolicitudDevoluciones current : solicitudesAprobarList) {
            if (current.getChecked()) {
                solicitudAprobar = current;
                break;
            }
        }
        return null;
    }

    public String getCargarDetalleAprobar() {
        Connection con = null;
        try {
            con = Util.openConnection(con);
            String consulta = " select m.CODIGO_ANTIGUO,m.NOMBRE_MATERIAL,sdd.CANTIDAD_DEVUELTA,m.cod_material,sdd.cod_unidad_medida, "
                                    + "sdd.CANTIDAD_DEVUELTA_FALLADOS,sdd.CANTIDAD_DEVUELTA_FALLADOS_PROVEEDOR "
                                    + " ,sdd.COSTO_SOLICITUD_DEVOLUCION_FRV,sdd.COSTO_SOLICITUD_DEVOLUCION_FRV_PROVEEDOR"
                                + " from SOLICITUD_DEVOLUCIONES_DETALLE"
                                + " sdd INNER JOIN MATERIALES m on sdd.COD_MATERIAL=m.COD_MATERIAL"
                                + " where sdd.COD_SOLICITUD_DEVOLUCION='" + solicitudAprobar.getCodSolicitudDevolucion() + "'";
            LOGGER.info("consulta cargar materiales solicitud: " + consulta);
        
            
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rst = st.executeQuery(consulta);
            solicitudesDetalleList.clear();
            while (rst.next()) {
                DevolucionesDetalle nuevo = new DevolucionesDetalle();
                nuevo.getMateriales().setCodMaterial(rst.getString("cod_material"));
                nuevo.getUnidadesMedida().setCodUnidadMedida(rst.getInt("cod_unidad_medida"));
                nuevo.getMateriales().setNombreMaterial(rst.getString("NOMBRE_MATERIAL"));
                nuevo.setCantidadDevuelta(rst.getFloat("CANTIDAD_DEVUELTA"));
                nuevo.setCantidadDevueltaFallados(rst.getFloat("CANTIDAD_DEVUELTA_FALLADOS"));
                nuevo.setCantidadDevueltaFalladosProveedor(rst.getFloat("CANTIDAD_DEVUELTA_FALLADOS_PROVEEDOR"));
                nuevo.setCostoSolicitudDevolucionFrv(rst.getDouble("COSTO_SOLICITUD_DEVOLUCION_FRV"));
                nuevo.setCostoSolicitudDevolucionFrvProveedor(rst.getDouble("COSTO_SOLICITUD_DEVOLUCION_FRV_PROVEEDOR"));
                solicitudesDetalleList.add(nuevo);
            }

            rst.close();
            st.close();
            con.close();
        } catch (SQLException ex) {
            LOGGER.warn(ex.getMessage());
        }
        generaTraspaso = false;
        traspasoChanged();
        return "";
    }

    public void cargarListadoSolicitudesAprobar() {
        try {
            Connection con = null;
            con = Util.openConnection(con);
            SimpleDateFormat sdt = new SimpleDateFormat("yyyy/MM/dd");
            usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            String consulta = "select * from (select ROW_NUMBER() OVER (order by sd.COD_SOLICITUD_DEVOLUCION desc) as 'FILAS',"
                    + "s.COD_LOTE_PRODUCCION,s.NRO_SALIDA_ALMACEN,cp.nombre_prod_semiterminado,a.NOMBRE_ALMACEN,"
                    + "(p.AP_PATERNO_PERSONAL+' '+p.AP_MATERNO_PERSONAL+' '+p.NOMBRE_PILA) as nombrePer,"
                    + "sd.COD_SOLICITUD_DEVOLUCION,esd.NOMBRE_ESTADO,sd.COD_ESTADO_SOLICITUD_DEVOLUCION,sd.COD_PERSONAL,sd.COD_SALIDA_ALMACEN,sd.FECHA_SOLICITUD,sd.OBSERVACION,s.COD_PROD from "
                    + "SOLICITUD_DEVOLUCIONES sd inner join PERSONAL p on sd.COD_PERSONAL=p.COD_PERSONAL "
                    + "inner join SALIDAS_ALMACEN s on s.COD_SALIDA_ALMACEN=sd.COD_SALIDA_ALMACEN and s.cod_estado_salida_almacen=1 "
                    + "left outer join COMPONENTES_PROD cp on cp.COD_COMPPROD=s.COD_PROD "
                    + "inner join ALMACENES a on a.COD_ALMACEN=s.COD_ALMACEN "
                    + "inner join ESTADOS_SOLICITUD_DEVOLUCION esd on sd.COD_ESTADO_SOLICITUD_DEVOLUCION=esd.COD_ESTADO_SOLICITUD_DEVOLUCION"
                    + // " where sd.COD_ESTADO_SOLICITUD_DEVOLUCION not in(2)"+
                    " where sd.COD_ALMACEN_DESTINO='" + usuario.getAlmacenesGlobal().getCodAlmacen() + "'";
            if (!buscarSolicitudAprobar.getCodSolicitudDevolucion().equals("")) {
                consulta += " and sd.COD_SOLICITUD_DEVOLUCION ='" + buscarSolicitudAprobar.getCodSolicitudDevolucion() + "'";
            }
            if (!buscarSolicitudAprobar.getEstadoSolicitudDevolucion().getCodEstado().equals("0")) {
                consulta += " and sd.COD_ESTADO_SOLICITUD_DEVOLUCION='" + buscarSolicitudAprobar.getEstadoSolicitudDevolucion().getCodEstado() + "'";

            }
            if (fechaInicio1 != null && fechaFinal1 != null) {
                consulta += " and sd.FECHA_SOLICITUD BETWEEN '" + sdt.format(fechaInicio) + " 00:00:00' and '" + sdt.format(fechafinal) + " 23:59:59' ";
            }
            if (buscarSolicitudAprobar.getSalidaAlmacen().getNroSalidaAlmacen() > 0) {
                consulta += " and s.NRO_SALIDA_ALMACEN ='" + buscarSolicitudAprobar.getSalidaAlmacen().getNroSalidaAlmacen() + "'";
            }
            if (buscarSolicitudAprobar.getSalidaAlmacen().getAlmacenes().getCodAlmacen() > 0) {
                consulta += " and s.COD_ALMACEN= '" + buscarSolicitudAprobar.getSalidaAlmacen().getAlmacenes().getCodAlmacen() + "'";
            }
            if (!buscarSolicitudAprobar.getSalidaAlmacen().getProducto().getCodProducto().equals("0")) {
                consulta += " and s.COD_PROD='" + buscarSolicitudAprobar.getSalidaAlmacen().getProducto().getCodProducto() + "'";

            }
            if (!buscarSolicitudAprobar.getPersonalSolicitante().getCodPersonal().equals("0")) {
                consulta += " and sd.COD_PERSONAL='" + buscarSolicitudAprobar.getPersonalSolicitante().getCodPersonal() + "' ";
            }
            consulta += "  ) AS listado where FILAS BETWEEN " + begin + " AND " + end + "  ";
            LOGGER.info("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            getSolicitudesAprobarList().clear();
            while (rs.next()) {
                SolicitudDevoluciones nuevaSolicitud = new SolicitudDevoluciones();

                nuevaSolicitud.getSalidaAlmacen().setCodSalidaAlmacen(rs.getInt("COD_SALIDA_ALMACEN"));
                nuevaSolicitud.getSalidaAlmacen().setNroSalidaAlmacen(rs.getInt("NRO_SALIDA_ALMACEN"));
                nuevaSolicitud.getSalidaAlmacen().setCodLoteProduccion(rs.getString("COD_LOTE_PRODUCCION"));
                nuevaSolicitud.getSalidaAlmacen().getProducto().setNombreProducto(rs.getString("nombre_prod_semiterminado"));
                nuevaSolicitud.getSalidaAlmacen().getAlmacenes().setNombreAlmacen(rs.getString("NOMBRE_ALMACEN"));
                nuevaSolicitud.setCodSolicitudDevolucion(rs.getString("COD_SOLICITUD_DEVOLUCION"));
                nuevaSolicitud.getEstadoSolicitudDevolucion().setCodEstado(rs.getString("COD_ESTADO_SOLICITUD_DEVOLUCION"));
                nuevaSolicitud.getEstadoSolicitudDevolucion().setNombreEstado(rs.getString("NOMBRE_ESTADO"));
                nuevaSolicitud.setFechaSolicitud(rs.getTimestamp("FECHA_SOLICITUD"));
                nuevaSolicitud.setObservacion(rs.getString("OBSERVACION"));
                nuevaSolicitud.getPersonalSolicitante().setCodPersonal(rs.getString("COD_PERSONAL"));
                nuevaSolicitud.getPersonalSolicitante().setNombrePersonal(rs.getString("nombrePer"));
                nuevaSolicitud.getSalidaAlmacen().getProducto().setCodProducto(rs.getString("COD_PROD"));
                getSolicitudesAprobarList().add(nuevaSolicitud);
            }
            rs.close();
            st.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void codigoSolicitudDevolucion() {
        try {
            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet res1 = st.executeQuery("select ISNULL(MAX(sd.COD_SOLICITUD_DEVOLUCION),0)+1 as codigo from SOLICITUD_DEVOLUCIONES sd");
            if (res1.next()) {
                codSolicitud = res1.getInt("codigo");
            }
            res1.close();
            st.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getCargarAgregarDevolucionAlmacen() {
        try {
            //se carga los datos de devolucion
            Iterator i = salidaAlmacenList.iterator();
            devolucionesDetalleList.clear();
            while (i.hasNext()) {
                SalidasAlmacen s = (SalidasAlmacen) i.next();
                salidasAlmacen = s;
            }
            devolucionesDetalleList = this.cargarDetalleDevolucion(salidasAlmacen);

            devoluciones.setSalidasAlmacen(salidasAlmacen);

            this.codigoSolicitudDevolucion();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getCargarAgregarSolicitudDevolucionLoteProveedor() {
        LOGGER.info("cambios tipo solicitud");
        try {
            Iterator i = salidaAlmacenList.iterator();
            String consulta = " SELECT s.COD_SALIDA_ALMACEN,s.COD_MATERIAL, s.CANTIDAD_SALIDA_ALMACEN, s.COD_UNIDAD_MEDIDA, "
                    + "  s.COD_ESTADO_MATERIAL,m.NOMBRE_MATERIAL,u.NOMBRE_UNIDAD_MEDIDA,u.ABREVIATURA,e.NOMBRE_ESTADO_MATERIAL "
                    + "  FROM SALIDAS_ALMACEN_DETALLE s  "
                    + "  inner join MATERIALES m on m.COD_MATERIAL = s.COD_MATERIAL "
                    + "  inner join UNIDADES_MEDIDA u on u.COD_UNIDAD_MEDIDA = s.COD_UNIDAD_MEDIDA "
                    + "  inner join ESTADOS_MATERIAL e on e.COD_ESTADO_MATERIAL = s.COD_ESTADO_MATERIAL "
                    + "  where s.COD_SALIDA_ALMACEN = '" + devoluciones.getSalidasAlmacen().getCodSalidaAlmacen() + "'";
            LOGGER.info("consulta CARGAR DETALLE" + consulta);
            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            Statement stDetalle = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet resDetalle = null;
            devolucionesDetalleList.clear();
            while (rs.next()) {
                DevolucionesDetalle detalle = new DevolucionesDetalle();
                detalle.getDevoluciones().getSalidasAlmacen().setCodSalidaAlmacen(rs.getInt("COD_SALIDA_ALMACEN"));
                detalle.getMateriales().setCodMaterial(rs.getString("COD_MATERIAL"));
                detalle.getMateriales().setNombreMaterial(rs.getString("NOMBRE_MATERIAL"));
                detalle.setCantidadEntregada(rs.getFloat("CANTIDAD_SALIDA_ALMACEN"));
                detalle.setCantidadDevuelta(0);
                detalle.setCantidadDevueltaFallados(0);
                detalle.getUnidadesMedida().setCodUnidadMedida(rs.getInt("COD_UNIDAD_MEDIDA"));
                detalle.getUnidadesMedida().setAbreviatura(rs.getString("ABREVIATURA"));
                consulta = " select i.COD_INGRESO_ALMACEN,i.NRO_INGRESO_ALMACEN,s.ETIQUETA,s.CANTIDAD,s.COD_MATERIAL, s.FECHA_VENCIMIENTO,iade.LOTE_MATERIAL_PROVEEDOR,"
                        + " (select sum(dde.CANTIDAD_DEVUELTA+dde.CANTIDAD_FALLADOS+dde.cantidad_fallados_proveedor) from DEVOLUCIONES d "
                        + " "
                        + " inner join DEVOLUCIONES_DETALLE dd on dd.COD_DEVOLUCION = d.COD_DEVOLUCION "
                        + " inner join DEVOLUCIONES_DETALLE_ETIQUETAS dde on dde.COD_DEVOLUCION = dd.COD_DEVOLUCION "
                        + " and dde.COD_MATERIAL = dd.COD_MATERIAL "
                        + " where d.COD_ESTADO_DEVOLUCION = 1 "
                        + " and d.COD_SALIDA_ALMACEN =s.COD_SALIDA_ALMACEN "
                        + " and dd.COD_MATERIAL = s.COD_MATERIAL "
                        + " and dde.COD_INGRESO_ALMACEN = i.COD_INGRESO_ALMACEN "
                        + " and dde.ETIQUETA = s.ETIQUETA ) CANTIDADES_DEVUELTAS "
                        + "  from SALIDAS_ALMACEN_DETALLE_INGRESO s "
                        + " inner join INGRESOS_ALMACEN i on i.COD_INGRESO_ALMACEN = s.COD_INGRESO_ALMACEN "
                        + " inner join INGRESOS_ALMACEN_DETALLE_ESTADO iade on iade.COD_INGRESO_ALMACEN=i.COD_INGRESO_ALMACEN "
                        + " and iade.ETIQUETA=s.ETIQUETA"
                        + " and iade.COD_MATERIAL=s.COD_MATERIAL"
                        + " where s.COD_SALIDA_ALMACEN = '" + detalle.getDevoluciones().getSalidasAlmacen().getCodSalidaAlmacen() + "' "
                        + //salidasAlmacen.getCodSalidaAlmacen()
                        " and s.COD_MATERIAL = '" + detalle.getMateriales().getCodMaterial() + "'";
                LOGGER.info("consulta " + consulta);
                resDetalle = stDetalle.executeQuery(consulta);
                detalle.getDevolucionesDetalleEtiquetasList().clear();
                float cantidadTotalDevueltas = 0f;
                while (resDetalle.next()) {
                    DevolucionesDetalleEtiquetas devolucionesDetalleEtiquetas = new DevolucionesDetalleEtiquetas();
                    devolucionesDetalleEtiquetas.getIngresosAlmacen().setNroIngresoAlmacen(resDetalle.getInt("NRO_INGRESO_ALMACEN"));
                    devolucionesDetalleEtiquetas.getIngresosAlmacen().setCodIngresoAlmacen(resDetalle.getInt("COD_INGRESO_ALMACEN"));
                    devolucionesDetalleEtiquetas.getMateriales().setCodMaterial(resDetalle.getString("COD_MATERIAL"));
                    devolucionesDetalleEtiquetas.setEtiqueta(resDetalle.getInt("ETIQUETA"));
                    devolucionesDetalleEtiquetas.setCantidadSalida(resDetalle.getFloat("CANTIDAD"));
                    devolucionesDetalleEtiquetas.setFechaVencimiento(resDetalle.getDate("FECHA_VENCIMIENTO"));
                    devolucionesDetalleEtiquetas.setCantidadesDevueltas(resDetalle.getFloat("CANTIDADES_DEVUELTAS"));
                    devolucionesDetalleEtiquetas.setLoteMaterialProveedor(resDetalle.getString("LOTE_MATERIAL_PROVEEDOR"));
                    devolucionesDetalleEtiquetas.setCantidadDevuelta(resDetalle.getFloat("CANTIDAD") - resDetalle.getFloat("CANTIDADES_DEVUELTAS"));
                    if (devolucionesDetalleEtiquetas.getCantidadDevuelta() < 0.1) {
                        devolucionesDetalleEtiquetas.setCantidadDevuelta(0);
                    }
                    devolucionesDetalleEtiquetas.setCantidadFallados(0);
                    cantidadTotalDevueltas += ((resDetalle.getFloat("CANTIDAD") - resDetalle.getFloat("CANTIDADES_DEVUELTAS")) > 0 ? (resDetalle.getFloat("CANTIDAD") - resDetalle.getFloat("CANTIDADES_DEVUELTAS")) : 0);
                    devolucionesDetalleEtiquetas.setCantidadFalladosProveedor(0);
                    if (devolucionesDetalleEtiquetas.getCantidadSalida() > 0) {
                        detalle.getDevolucionesDetalleEtiquetasList().add(devolucionesDetalleEtiquetas);
                    }
                }
                detalle.setCantidadDevuelta(cantidadTotalDevueltas);
                devolucionesDetalleList.add(detalle);
            }
            ResultSet res1 = st.executeQuery("select ISNULL(MAX(sd.COD_SOLICITUD_DEVOLUCION),0)+1 as codigo from SOLICITUD_DEVOLUCIONES sd");
            if (res1.next()) {
                codSolicitud = res1.getInt("codigo");
            }
            res1.close();
            st.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getCargarAgregarDevolucionesAlmacen() {
        this.cargarPermisosEspecialesPersonal();
        this.cargarTipoFrvProcesoSelectList();
        try {
            salidasAlmacen=devoluciones.getSalidasAlmacen();
            devolucionesDetalleList.clear();
            devolucionesDetalleList.addAll(this.cargarDetalleDevolucion(devoluciones.getSalidasAlmacen()));
            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet res1 = st.executeQuery("select ISNULL(MAX(sd.COD_SOLICITUD_DEVOLUCION),0)+1 as codigo from SOLICITUD_DEVOLUCIONES sd");
            if (res1.next()) {
                codSolicitud = res1.getInt("codigo");
            }
            res1.close();
            st.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List cargarDetalleDevolucion(SalidasAlmacen salidasAlmacen) {
        List devolucionesDetalleList = new ArrayList();
        try {
            Connection con = null;
            con = Util.openConnection(con);
            //llenar el detalle de salida almacen
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            String consulta = " SELECT s.COD_SALIDA_ALMACEN,s.COD_MATERIAL, s.CANTIDAD_SALIDA_ALMACEN, s.COD_UNIDAD_MEDIDA, "
                                + "  s.COD_ESTADO_MATERIAL,m.NOMBRE_MATERIAL,u.NOMBRE_UNIDAD_MEDIDA,u.ABREVIATURA,e.NOMBRE_ESTADO_MATERIAL, "
                                + "  g.NOMBRE_GRUPO,g.COD_CAPITULO,costoMaterial.COSTO_MATERIAL"
                    + "  FROM SALIDAS_ALMACEN_DETALLE s  "
                            + " inner join MATERIALES m on m.COD_MATERIAL = s.COD_MATERIAL "
                            + " inner join GRUPOS g on g.COD_GRUPO = m.COD_GRUPO "
                            + " inner join UNIDADES_MEDIDA u on u.COD_UNIDAD_MEDIDA = s.COD_UNIDAD_MEDIDA "
                            + " inner join ESTADOS_MATERIAL e on e.COD_ESTADO_MATERIAL = s.COD_ESTADO_MATERIAL "
                            + " outer apply ("
                                    +" select top 1 cmm.COSTO_MATERIAL"
                                    +" from COSTOS_MATERIAL_POR_MES cmm"
                                        +" inner join MESES mes on mes.COD_MES = cmm.COD_MES"
                                    +" where cmm.COD_MATERIAL = s.COD_MATERIAL"
                                            +" and cmm.COSTO_MATERIAL > 0"
                                            +" and cmm.FECHA <  '"+sdf.format(salidasAlmacen.getFechaSalidaAlmacen())+"'"
                                    +" order by case when cmm.COD_ALMACEN = "+usuario.getAlmacenesGlobal().getCodAlmacen()+" then 1 else 2 END,"
                                            +" cmm.FECHA desc"
                            +" ) as costoMaterial"
                    + "  where s.COD_SALIDA_ALMACEN = '" + salidasAlmacen.getCodSalidaAlmacen() + "'";
            if(!permisoDevolucionMateriaPrima)
            {
                consulta += " and g.COD_CAPITULO <> 2";
            }
            if (usuario.getAlmacenesGlobal().getCodAlmacen() != 14 && salidasAlmacen.getCodLoteProduccion().length()>1) {
                consulta += " union  all"
                        + " SELECT '0',m.COD_MATERIAL, '0', m.COD_UNIDAD_MEDIDA, '0',m.NOMBRE_MATERIAL,u.NOMBRE_UNIDAD_MEDIDA,u.ABREVIATURA,'0',"
                        + " g.NOMBRE_GRUPO,g.COD_CAPITULO, 0 as COSTO_MATERIAL"
                        + " FROM MATERIALES m"
                        + " inner join GRUPOS g on g.COD_GRUPO = m.COD_GRUPO"
                        + " inner join UNIDADES_MEDIDA u on u.COD_UNIDAD_MEDIDA = m.COD_UNIDAD_MEDIDA"
                        + " where  m.COD_MATERIAL = 11887 ";
            }
            LOGGER.info("consulta CARGAR DETALLE" + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            devolucionesDetalleList.clear();
            while (rs.next()) {
                DevolucionesDetalle devolucionesDetalle = new DevolucionesDetalle();
                devolucionesDetalle.setSalidasAlmacen(salidasAlmacen);
                devolucionesDetalle.getDevoluciones().getSalidasAlmacen().setCodSalidaAlmacen(rs.getInt("COD_SALIDA_ALMACEN"));
                devolucionesDetalle.getMateriales().setCodMaterial(rs.getString("COD_MATERIAL"));
                devolucionesDetalle.getMateriales().setNombreMaterial(rs.getString("NOMBRE_MATERIAL"));
                devolucionesDetalle.getMateriales().getGrupos().getCapitulos().setCodCapitulo(rs.getInt("COD_CAPITULO"));
                devolucionesDetalle.setCantidadEntregada(rs.getFloat("CANTIDAD_SALIDA_ALMACEN"));
                devolucionesDetalle.setCantidadDevuelta(0);
                devolucionesDetalle.setCantidadDevueltaFallados(0);
                devolucionesDetalle.getUnidadesMedida().setCodUnidadMedida(rs.getInt("COD_UNIDAD_MEDIDA"));
                devolucionesDetalle.getUnidadesMedida().setAbreviatura(rs.getString("ABREVIATURA"));
                devolucionesDetalle.setDevolucionesDetalleEtiquetasList(this.listaDevolucionDetalleEtiquetas(devolucionesDetalle,rs.getDouble("COSTO_MATERIAL")));
                if (devolucionesDetalle.getMateriales().getCodMaterial().equals("11887")) {
                    devolucionesDetalle.setDevolucionesDetalleEtiquetasList(this.registroDevolucionDetalleEtiquetasFRV(salidasAlmacen.getCodLoteProduccion()));
                }
                devolucionesDetalleList.add(devolucionesDetalle);
            }
            rs.close();
            st.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return devolucionesDetalleList;
    }

    public List registroDevolucionDetalleEtiquetasFRV(String codLoteProduccion) {
        List detalleList = new ArrayList();
        DevolucionesDetalleEtiquetas d = new DevolucionesDetalleEtiquetas();
        Connection con=null;
        try 
        {
            con = Util.openConnection(con);
                StringBuilder consulta = new StringBuilder("select sum(sadi.CANTIDAD*sadi.COSTO_SALIDA_APL_ACTUALIZADO) as costoSalida,")
                                                        .append(" sum( sadi.CANTIDAD*(case when um.COD_TIPO_MEDIDA=2 then isnull(e.VALOR_EQUIVALENCIA,1) else isnull(e2.VALOR_EQUIVALENCIA,1)*isnull(mdcg.DENSIDAD,1) end)) as cantidadGramos")
                                                        .append(" ,g.COD_CAPITULO")
                                                .append(" from SALIDAS_ALMACEN sa")
                                                        .append(" inner join SALIDAS_ALMACEN_DETALLE_INGRESO sadi on sadi.COD_SALIDA_ALMACEN=sa.COD_SALIDA_ALMACEN")
                                                        .append(" inner join materiales m on m.COD_MATERIAL=sadi.COD_MATERIAL")
                                                        .append(" inner join unidades_medida um on um.COD_UNIDAD_MEDIDA=m.COD_UNIDAD_MEDIDA")
                                                        .append(" inner join grupos g on g.COD_GRUPO=m.COD_GRUPO")
                                                        .append(" left outer join MATERIALES_DENSIDAD_CONVERSION_GRAMOS mdcg on mdcg.COD_MATERIAL=M.COD_MATERIAL")
                                                        .append(" left outer join EQUIVALENCIAS e on e.COD_UNIDAD_MEDIDA=m.COD_UNIDAD_MEDIDA")
                                                                .append(" and e.COD_UNIDAD_MEDIDA2=7")
                                                                .append(" and e.COD_ESTADO_REGISTRO=1")
                                                        .append(" left outer join EQUIVALENCIAS e2 on e2.COD_UNIDAD_MEDIDA=m.COD_UNIDAD_MEDIDA")
                                                                .append(" and e2.COD_UNIDAD_MEDIDA2=2")
                                                                .append(" and e2.COD_ESTADO_REGISTRO=1")
                                                .append(" where sa.COD_ESTADO_SALIDA_ALMACEN=1")
                                                .append(" and sa.COD_LOTE_PRODUCCION='").append(codLoteProduccion).append("'")
                                                .append(" and um.COD_TIPO_MEDIDA in (1,2)")
                                                .append("group by g.COD_CAPITULO")
                                                .append(" order by g.COD_CAPITULO");
            LOGGER.info("consulta cargar costo frv"+consulta.toString());
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet res = st.executeQuery(consulta.toString());
            while (res.next()) {
                switch(res.getInt("COD_CAPITULO"))
                {
                    case 2:
                    {
                        Double cantidadGramos = res.getDouble("cantidadGramos");
                        Double costoTotalMaterial = res.getDouble("costoSalida");
                        consulta = new StringBuilder("select sum(f.CANTIDAD_UNITARIA_GRAMOS * p.CANT_LOTE_PRODUCCION) as cantidadAgua")
                                .append(" from programa_produccion p")
                                        .append(" inner join formula_maestra_detalle_mp_version f on f.COD_VERSION = p.COD_FORMULA_MAESTRA_VERSION")
                                                .append(" and f.COD_FORMULA_MAESTRA = p.COD_FORMULA_MAESTRA")
                                .append(" where p.COD_LOTE_PRODUCCION = '").append(codLoteProduccion).append("'")
                                        .append(" and f.COD_TIPO_MATERIAL_PRODUCCION = 1")
                                        .append(" and f.COD_MATERIAL in (5014,13216)");
                        LOGGER.debug("consulta cantidad agua recubrimiento "+consulta.toString());
                        Statement stGramos = con.createStatement();
                        ResultSet resGramos = stGramos.executeQuery(consulta.toString());
                        if(resGramos.next()){
                            cantidadGramos += resGramos.getDouble("cantidadAgua");
                            costoTotalMaterial += resGramos.getDouble("cantidadAgua") * COSTO_GRAMO_AGUA;
                            LOGGER.debug("se suma cantidad de agua "+resGramos.getDouble("cantidadAgua"));
                        }
                        d.setCostoFrvConMp(costoTotalMaterial/ cantidadGramos);
                        break;
                    }
                    case 3:
                    {
                        if(d.getCostoFrvConMp() > 0){
                            d.setCostoFrvConMpEp(d.getCostoFrvConMp()+res.getDouble("costoSalida")/res.getDouble("cantidadGramos"));
                        }
                        break;
                    }
                }
            }
            consulta = new StringBuilder("select sum(sadi.CANTIDAD*sadi.COSTO_SALIDA_APL_ACTUALIZADO) as costoSalida,")
                                        .append(" sum( sadi.CANTIDAD*(case when um.COD_TIPO_MEDIDA=2 then isnull(e.VALOR_EQUIVALENCIA,1) else isnull(e2.VALOR_EQUIVALENCIA,1)*isnull(mdcg.DENSIDAD,1) end)) as cantidadGramos")
                                .append(" from SALIDAS_ALMACEN sa")
                                        .append(" inner join SALIDAS_ALMACEN_DETALLE_INGRESO sadi on sadi.COD_SALIDA_ALMACEN=sa.COD_SALIDA_ALMACEN")
                                        .append(" inner join materiales m on m.COD_MATERIAL=sadi.COD_MATERIAL")
                                        .append(" inner join unidades_medida um on um.COD_UNIDAD_MEDIDA=m.COD_UNIDAD_MEDIDA")
                                        .append(" inner join grupos g on g.COD_GRUPO=m.COD_GRUPO")
                                        .append(" left outer join MATERIALES_DENSIDAD_CONVERSION_GRAMOS mdcg on mdcg.COD_MATERIAL=M.COD_MATERIAL")
                                        .append(" left outer join EQUIVALENCIAS e on e.COD_UNIDAD_MEDIDA=m.COD_UNIDAD_MEDIDA")
                                                .append(" and e.COD_UNIDAD_MEDIDA2=7")
                                                .append(" and e.COD_ESTADO_REGISTRO=1")
                                        .append(" left outer join EQUIVALENCIAS e2 on e2.COD_UNIDAD_MEDIDA=m.COD_UNIDAD_MEDIDA")
                                                .append(" and e2.COD_UNIDAD_MEDIDA2=2")
                                                .append(" and e2.COD_ESTADO_REGISTRO=1")
                                .append(" where sa.COD_ESTADO_SALIDA_ALMACEN=1")
                                .append(" and sa.COD_LOTE_PRODUCCION='").append(codLoteProduccion).append("'")
                                .append(" and sadi.COD_MATERIAL in ")
                                        .append(" (")
                                                .append(" select f.COD_MATERIAL")
                                                .append(" from programa_produccion p ")
                                                .append(" inner join formula_maestra_detalle_mp_version f on f.COD_VERSION=p.COD_FORMULA_MAESTRA_VERSION")
                                                .append(" and f.COD_FORMULA_MAESTRA=p.COD_FORMULA_MAESTRA")
                                                .append(" where p.COD_LOTE_PRODUCCION='").append(codLoteProduccion).append("'")
                                                .append(" and f.COD_TIPO_MATERIAL_PRODUCCION = 2")
                                        .append(" )")
                                .append(" and um.COD_TIPO_MEDIDA in (1,2)")
                                .append("group by g.COD_CAPITULO");
            LOGGER.debug("consulta frv recubrimiento "+consulta.toString());
            res= st.executeQuery(consulta.toString());
            Double costoSalida = 0d;
            Double cantidadGramos  = 0d;
            if(res.next())
            {  
                costoSalida = res.getDouble("costoSalida");
                cantidadGramos = res.getDouble("cantidadGramos");
            }
            consulta = new StringBuilder("select sum(f.CANTIDAD_UNITARIA_GRAMOS * p.CANT_LOTE_PRODUCCION) as cantidadAgua")
                                .append(" from programa_produccion p")
                                        .append(" inner join formula_maestra_detalle_mp_version f on f.COD_VERSION = p.COD_FORMULA_MAESTRA_VERSION")
                                                .append(" and f.COD_FORMULA_MAESTRA = p.COD_FORMULA_MAESTRA")
                                .append(" where p.COD_LOTE_PRODUCCION = '").append(codLoteProduccion).append("'")
                                        .append(" and f.COD_TIPO_MATERIAL_PRODUCCION = 2")
                                        .append(" and f.COD_MATERIAL in (5014,13216)");
            LOGGER.debug("consulta cantidad agua recubrimiento "+consulta.toString());
            res = st.executeQuery(consulta.toString());
            if(res.next())cantidadGramos += res.getDouble("cantidadAgua");
            if(cantidadGramos > 0)
            {
                d.setCostoFrvRecubrimiento(costoSalida/cantidadGramos);
            }
            d.getTipoFrvProceso().setCodTipoFrvProceso(1);
            mensaje = "1";
        } catch (SQLException ex) {
            LOGGER.warn(ex.getMessage());
        } catch (NumberFormatException ex) {
            LOGGER.warn(ex.getMessage());
        } finally {
            try
            {
                con.close();
            }
            catch(SQLException ex)
            {
                LOGGER.warn(ex.getMessage());
            }
        } 
        
        d.getMateriales().setCodMaterial("11887");
        detalleList.add(d);
        return detalleList;
    }

    public List listaDevolucionDetalleEtiquetas(DevolucionesDetalle devolucionesDetalle,Double ultimoCostoMaterial) {
        List devolucionesDetalleEtiquetasList = new ArrayList();
        try {
            int codSalidaAlmacen = salidasAlmacen.getCodSalidaAlmacen() == 0 ? devolucionesDetalle.getSalidasAlmacen().getCodSalidaAlmacen() : salidasAlmacen.getCodSalidaAlmacen();
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select i.COD_INGRESO_ALMACEN,i.NRO_INGRESO_ALMACEN,iade.LOTE_MATERIAL_PROVEEDOR,s.ETIQUETA,s.CANTIDAD,s.COD_MATERIAL, s.FECHA_VENCIMIENTO,"
                    + " (select sum(dde.CANTIDAD_DEVUELTA+dde.CANTIDAD_FALLADOS+dde.cantidad_fallados_proveedor) from DEVOLUCIONES d "
                    + " inner join DEVOLUCIONES_DETALLE dd on dd.COD_DEVOLUCION = d.COD_DEVOLUCION "
                    + " inner join DEVOLUCIONES_DETALLE_ETIQUETAS dde on dde.COD_DEVOLUCION = dd.COD_DEVOLUCION "
                    + " and dde.COD_MATERIAL = dd.COD_MATERIAL "
                    + " where d.COD_ESTADO_DEVOLUCION = 1 "
                    + " and d.COD_SALIDA_ALMACEN =s.COD_SALIDA_ALMACEN "
                    + " and dd.COD_MATERIAL = s.COD_MATERIAL "
                    + " and dde.COD_INGRESO_ALMACEN = i.COD_INGRESO_ALMACEN "
                    + " and dde.ETIQUETA = s.ETIQUETA ) CANTIDADES_DEVUELTAS "
                    + " ,"
                    + "         ("
                    + "          select ISNULL(sum(dde.CANTIDAD_DEVUELTA + dde.CANTIDAD_DEVUELTA_FALLADOS +"
                    + "          dde.CANTIDAD_DEVUELTA_FALLADOS_PROVEEDOR),0)"
                    + "          from SOLICITUD_DEVOLUCIONES d"
                    + "               inner join SOLICITUD_DEVOLUCIONES_DETALLE dd on dd.COD_SOLICITUD_DEVOLUCION ="
                    + "               d.COD_SOLICITUD_DEVOLUCION"
                    + "               inner join SOLICITUD_DEVOLUCIONES_DETALLE_ETIQUETAS dde on"
                    + "               dde.COD_SOLICITUD_DEVOLUCION = dd.COD_SOLICITUD_DEVOLUCION and dde.COD_MATERIAL ="
                    + "               dd.COD_MATERIAL\n"
                    + "          where d.COD_ESTADO_SOLICITUD_DEVOLUCION = 1 and"
                    + "                d.COD_SALIDA_ALMACEN = s.COD_SALIDA_ALMACEN and"
                    + "                dd.COD_MATERIAL = s.COD_MATERIAL and"
                    + "                dde.COD_INGRESO_ALMACEN = i.COD_INGRESO_ALMACEN and"
                    + "                dde.ETIQUETA = s.ETIQUETA"
                    + "        ) CANTIDADES_SOLICITUD_DEVOLUCION"
                    + " ,s.COSTO_SALIDA_APL_ACTUALIZADO,iad.cod_error_costo_apl,iad.COSTO_UNITARIO"
                    + " ,i.COD_ORDEN_COMPRA,oc.COD_TIPO_COMPRA,oc.COD_TIPO_TRANSPORTE,oc.COD_MONEDA"
                    + "  from SALIDAS_ALMACEN_DETALLE_INGRESO s "
                    + " inner join INGRESOS_ALMACEN i on i.COD_INGRESO_ALMACEN = s.COD_INGRESO_ALMACEN "
                    + "  INNER JOIN INGRESOS_ALMACEN_DETALLE_ESTADO iade on iade.COD_INGRESO_ALMACEN=s.COD_INGRESO_ALMACEN"
                            + " and s.COD_MATERIAL=iade.COD_MATERIAL and s.ETIQUETA=iade.ETIQUETA"
                    + " inner join INGRESOS_ALMACEN_DETALLE iad on iad.COD_INGRESO_ALMACEN = iade.COD_INGRESO_ALMACEN"
                            + " and iad.COD_MATERIAL = iade.COD_MATERIAL"
                    + " left outer join ordenes_compra oc on oc.COD_ORDEN_COMPRA =  i.COD_ORDEN_COMPRA"
                    + " where s.COD_SALIDA_ALMACEN = '" + devolucionesDetalle.getSalidasAlmacen().getCodSalidaAlmacen() + "' "
                    + //salidasAlmacen.getCodSalidaAlmacen()
                    " and s.COD_MATERIAL = '" + devolucionesDetalle.getMateriales().getCodMaterial() + "'";
            LOGGER.info("consulta  etiquetas " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            devolucionesDetalleEtiquetasList.clear();
            while (rs.next()) {
                DevolucionesDetalleEtiquetas devolucionesDetalleEtiquetas = new DevolucionesDetalleEtiquetas();
                devolucionesDetalleEtiquetas.getIngresosAlmacen().setNroIngresoAlmacen(rs.getInt("NRO_INGRESO_ALMACEN"));
                devolucionesDetalleEtiquetas.getIngresosAlmacen().setCodIngresoAlmacen(rs.getInt("COD_INGRESO_ALMACEN"));
                devolucionesDetalleEtiquetas.getMateriales().setCodMaterial(rs.getString("COD_MATERIAL"));
                devolucionesDetalleEtiquetas.setEtiqueta(rs.getInt("ETIQUETA"));
                devolucionesDetalleEtiquetas.setCantidadSalida(rs.getFloat("CANTIDAD"));
                devolucionesDetalleEtiquetas.setFechaVencimiento(rs.getDate("FECHA_VENCIMIENTO"));
                devolucionesDetalleEtiquetas.setCantidadesDevueltas(rs.getFloat("CANTIDADES_DEVUELTAS"));
                devolucionesDetalleEtiquetas.setLoteMaterialProveedor(rs.getString("LOTE_MATERIAL_PROVEEDOR"));
                devolucionesDetalleEtiquetas.setCantidad_solicitud_devolucion(rs.getFloat("CANTIDADES_SOLICITUD_DEVOLUCION"));
                devolucionesDetalleEtiquetas.setCantidadDevuelta(0);
                devolucionesDetalleEtiquetas.setCantidadFallados(0);
                devolucionesDetalleEtiquetas.setCostoSalidaAplActualizado(rs.getDouble("COSTO_SALIDA_APL_ACTUALIZADO") > 0 ? rs.getDouble("COSTO_SALIDA_APL_ACTUALIZADO") :  ultimoCostoMaterial);
                if(devolucionesDetalleEtiquetas.getCostoSalidaAplActualizado() == 0 && rs.getInt("cod_error_costo_apl") == 2)
                {
                    LOGGER.debug("no tiene costo actualizado");
                    if(rs.getDouble("COSTO_UNITARIO") > 0)
                    {
                        LOGGER.debug("se asigna costo unitario "+rs.getDouble("COSTO_UNITARIO"));
                        devolucionesDetalleEtiquetas.setCostoSalidaAplActualizado(rs.getDouble("COSTO_UNITARIO"));
                    }
                    else
                    {
                        if(rs.getInt("COD_ORDEN_COMPRA") > 0){
                            //<editor-fold defaultstate="collapsed" desc="costos de la o.c.">

                            consulta = "select ocd.PRECIO_TOTAL,sum(iad.CANT_TOTAL_INGRESO) as cantidadIngresada,"
                                                + " MAX(cambioMoneda.CAMBIO) as cambioMoneda,max(costoImportacion.FACTOR_COSTO) as FACTOR_COSTO"
                                        + " from ordenes_compra_detalle ocd "
                                                + " inner join INGRESOS_ALMACEN ia on ia.COD_ORDEN_COMPRA = ocd.COD_ORDEN_COMPRA"
                                                + " inner join INGRESOS_ALMACEN_DETALLE iad on iad.COD_INGRESO_ALMACEN = ia.COD_INGRESO_ALMACEN "
                                                        + " and iad.COD_MATERIAL = ocd.COD_MATERIAL"
                                                + " outer apply("
                                                    + " select top 1 tcm.CAMBIO"
                                                    + " from TIPOCAMBIOS_MONEDA tcm "
                                                    + " where tcm.COD_MONEDA="+rs.getInt("COD_MONEDA")
                                                    + " ORDER BY tcm.FECHA desc"
                                                + " ) as cambioMoneda"
                                                + " OUTER APPLY"
                                                + " ("
                                                        + " select top 1 ttci.FACTOR_COSTO"
                                                        + " from TIPOS_TRANSPORTE_COSTO_IMPORTACION ttci"
                                                        + " where ttci.COD_TIPO_TRANSPORTE = "+rs.getInt("COD_TIPO_TRANSPORTE")
                                                + " ) as costoImportacion"
                                        + " where ocd.COD_ORDEN_COMPRA = "+rs.getInt("COD_ORDEN_COMPRA")
                                                + " and ocd.COD_MATERIAL = "+rs.getInt("COD_MATERIAL")
                                        + " group by ocd.PRECIO_TOTAL";  
                            LOGGER.debug("consulta obtener datos de O.C. "+consulta);
                            Statement std = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                            ResultSet res = std.executeQuery(consulta);
                            if(res.next()){
                                Double costoOc = (( res.getDouble("PRECIO_TOTAL") * (rs.getInt("COD_MONEDA") == 1 ? 1d : res.getDouble("cambioMoneda")))/res.getDouble("cantidadIngresada"));
                                LOGGER.debug("costos oc sin transporte "+costoOc);
                                switch(rs.getInt("COD_TIPO_COMPRA"))
                                {
                                    case 1:// tipo  de compra local
                                    {
                                        LOGGER.debug("compra local costo "+costoOc);
                                        devolucionesDetalleEtiquetas.setCostoSalidaAplActualizado(costoOc);
                                        break;
                                    }
                                    case 3:// tipo  de compra importacion
                                    {
                                        costoOc = costoOc * res.getDouble("FACTOR_COSTO");
                                        LOGGER.debug("compra importacion"+costoOc);
                                        devolucionesDetalleEtiquetas.setCostoSalidaAplActualizado(costoOc);
                                        break;
                                    }
                                    default:
                                    {
                                        LOGGER.debug("no se encontro costo para el tipo de compra "+rs.getInt("COD_ORDEN_COMPRA"));
                                        devolucionesDetalleEtiquetas.setCostoFrvConMp(-1d);
                                    }
                                }
                            }
                            else{
                                LOGGER.debug("no existe oc del item");
                                devolucionesDetalleEtiquetas.setCostoFrvConMp(-1d);
                            }
                            //</editor-fold>
                        }
                        else{
                            LOGGER.debug("el ingreso no es por orden de compra");
                            devolucionesDetalleEtiquetas.setCostoFrvConMp(-1d);
                        }
                    }
                    
                }
                devolucionesDetalleEtiquetas.setCantidadFalladosProveedor(0);
                if (devolucionesDetalleEtiquetas.getCantidadSalida() > 0) {
                    devolucionesDetalleEtiquetasList.add(devolucionesDetalleEtiquetas);
                }
            }
            rs.close();
            st.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return devolucionesDetalleEtiquetasList;
    }

    public List listaComponentesProd() {
        List componentesProdList = new ArrayList();
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select cp.COD_COMPPROD , cp.nombre_prod_semiterminado from COMPONENTES_PROD cp where cp.cod_tipo_produccion = 1 order by cp.nombre_prod_semiterminado";
            LOGGER.info("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            componentesProdList.clear();
            componentesProdList.add(new SelectItem("0", "-NINGUNO-"));
            while (rs.next()) {
                componentesProdList.add(new SelectItem(rs.getString("COD_COMPPROD"), rs.getString("nombre_prod_semiterminado")));
            }
            rs.close();
            st.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return componentesProdList;
    }

    public String buscarSalidaAlmacenLoteProveedor_action() throws SQLException {
        Connection con = null;
        try {
            con = Util.openConnection(con);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String consulta = "select sa.COD_SALIDA_ALMACEN,sa.NRO_SALIDA_ALMACEN,sa.FECHA_SALIDA_ALMACEN,tsa.NOMBRE_TIPO_SALIDA_ALMACEN,"
                    + " ae.NOMBRE_AREA_EMPRESA,p.AP_PATERNO_PERSONAL,p.AP_MATERNO_PERSONAL,p.NOMBRES_PERSONAL,"
                    + " sa.OBS_SALIDA_ALMACEN"
                    + " from SALIDAS_ALMACEN sa left OUTER join SOLICITUDES_SALIDA ss on"
                    + " sa.COD_FORM_SALIDA=ss.COD_FORM_SALIDA"
                    + " left outer join TIPOS_SALIDAS_ALMACEN tsa on tsa.COD_TIPO_SALIDA_ALMACEN=sa.COD_TIPO_SALIDA_ALMACEN"
                    + " left outer join AREAS_EMPRESA ae on ae.COD_AREA_EMPRESA=sa.COD_AREA_EMPRESA"
                    + " left outer join PERSONAL p on p.COD_PERSONAL=sa.COD_PERSONAL"
                    + " where ss.SOLICITUD_POR_LOTE_PROVEEDOR=1"
                    + (salidasAlmacenDetalleIngresoBuscar.getIngresosAlmacenDetalleEstado().getLoteMaterialProveedor().equals("") ? ""
                    : " and ss.COD_FORM_SALIDA in ("
                    + " select ssdl.COD_FORM_SALIDA from SOLICITUDES_SALIDA_DETALLE_LOTE_PROVEEDOR ssdl where ssdl.COD_LOTE_PROVEEDOR='"
                    + salidasAlmacenDetalleIngresoBuscar.getIngresosAlmacenDetalleEstado().getLoteMaterialProveedor() + "'"
                    + " )")
                    + (salidasAlmacenDetalleIngresoBuscar.getSalidasAlmacen().getNroSalidaAlmacen() > 0
                            ? " and sa.NRO_SALIDA_ALMACEN='" + salidasAlmacenDetalleIngresoBuscar.getSalidasAlmacen().getNroSalidaAlmacen() + "'" : "")
                    + " order by sa.FECHA_SALIDA_ALMACEN desc";
            LOGGER.info("consulta salidas" + consulta);
            ResultSet res = st.executeQuery(consulta);
            salidasAlmacenBuscarList.clear();
            while (res.next()) {
                SalidasAlmacen salidasAlmacenItem = new SalidasAlmacen();
                salidasAlmacenItem.setCodSalidaAlmacen(res.getInt("COD_SALIDA_ALMACEN"));
                salidasAlmacenItem.setNroSalidaAlmacen(res.getInt("NRO_SALIDA_ALMACEN"));
                salidasAlmacenItem.setFechaSalidaAlmacen(res.getDate("FECHA_SALIDA_ALMACEN"));
                salidasAlmacenItem.getTiposSalidasAlmacen().setNombreTipoSalidaAlmacen(res.getString("NOMBRE_TIPO_SALIDA_ALMACEN"));
                salidasAlmacenItem.getAreasEmpresa().setNombreAreaEmpresa(res.getString("NOMBRE_AREA_EMPRESA"));
                salidasAlmacenItem.getPersonal().setNombrePersonal(res.getString("NOMBRES_PERSONAL"));
                salidasAlmacenItem.getPersonal().setApPaternoPersonal(res.getString("AP_PATERNO_PERSONAL"));
                salidasAlmacenItem.getPersonal().setApMaternoPersonal(res.getString("AP_MATERNO_PERSONAL"));
                salidasAlmacenItem.setObsSalidaAlmacen(res.getString("OBS_SALIDA_ALMACEN"));
                salidasAlmacenBuscarList.add(salidasAlmacenItem);
            }
            res.close();
            st.close();
            con.close();
        } catch (SQLException ex) {
            LOGGER.warn(ex.getMessage());
        }
        return null;
    }

    public String buscarSalidaAlmacen_action() {
        try {
            inicioFila = 1;
            finalFila = 10;
            this.listadoBuscarSalidaAlmacen();
            cantidadFilasBuscar = salidasAlmacenBuscarList.size();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String atras_action() {
        inicioFila = inicioFila - numrows;
        finalFila = finalFila - numrows;
        this.listadoBuscarSalidaAlmacen();
        cantidadFilasBuscar = salidasAlmacenBuscarList.size();
        return "";
    }

    public String siguiente_action() {
        inicioFila = inicioFila + numrows;
        finalFila = finalFila + numrows;
        this.listadoBuscarSalidaAlmacen();
        cantidadFilasBuscar = salidasAlmacenBuscarList.size();
        return "";
    }

    public void listadoBuscarSalidaAlmacen() {
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select * from (select ROW_NUMBER() OVER (order by s.FECHA_SALIDA_ALMACEN desc) as 'FILAS',"
                    + " s.COD_SALIDA_ALMACEN,s.COD_LOTE_PRODUCCION,s.NRO_SALIDA_ALMACEN,s.FECHA_SALIDA_ALMACEN, t.NOMBRE_TIPO_SALIDA_ALMACEN,a.NOMBRE_AREA_EMPRESA, "
                    + " p.AP_PATERNO_PERSONAL,p.AP_MATERNO_PERSONAL,p.NOMBRES_PERSONAL,s.OBS_SALIDA_ALMACEN, "
                    + " (select cp.nombre_prod_semiterminado from COMPONENTES_PROD cp where cp.COD_COMPPROD = s.COD_PROD) nombre_prod_semiterminado,"
                    + " (select prp.NOMBRE_PRODUCTO_PRESENTACION from PRESENTACIONES_PRODUCTO prp where prp.cod_presentacion = s.COD_PRESENTACION ) NOMBRE_PRODUCTO_PRESENTACION,"
                    + " s.orden_trabajo "
                    + " from SALIDAS_ALMACEN s  "
                    + " inner join TIPOS_SALIDAS_ALMACEN t on t.COD_TIPO_SALIDA_ALMACEN = s.COD_TIPO_SALIDA_ALMACEN "
                    + " inner join AREAS_EMPRESA a on a.COD_AREA_EMPRESA = s.COD_AREA_EMPRESA "
                    + " inner join PERSONAL p on p.COD_PERSONAL = s.COD_PERSONAL "
                    + "  "
                    + "  ";
            if (salidasAlmacenBuscar.getNroSalidaAlmacen() > 0) {
                consulta = consulta + " where s.NRO_SALIDA_ALMACEN = '" + salidasAlmacenBuscar.getNroSalidaAlmacen() + "' ";
            }
            if (!salidasAlmacenBuscar.getCodLoteProduccion().equals("")) {
                consulta = consulta + " and s.COD_LOTE_PRODUCCION = '" + salidasAlmacenBuscar.getCodLoteProduccion() + "' ";
            }
            if (!salidasAlmacenBuscar.getComponentesProd().getCodCompprod().equals("0") && !salidasAlmacenBuscar.getComponentesProd().getCodCompprod().equals("")) {
                consulta = consulta + " and s.COD_PROD = '" + salidasAlmacenBuscar.getComponentesProd().getCodCompprod() + "' ";
            }
            if (!salidasAlmacenBuscar.getMaquinaria().getCodMaquina().equals("0")) {
                consulta = consulta + " and s.cod_maquina = '" + salidasAlmacenBuscar.getMaquinaria().getCodMaquina() + "' ";
            }
            if (!salidasAlmacenBuscar.getOrdenTrabajo().equals("")) {
                consulta = consulta + " and s.orden_trabajo = '" + salidasAlmacenBuscar.getOrdenTrabajo() + "' ";
            }
            consulta = consulta + " and s.COD_ALMACEN = '" + usuario.getAlmacenesGlobal().getCodAlmacen() + "' "
                    + " and s.cod_estado_salida_almacen = 1 and s.estado_sistema = 1"
                    + " and s.COD_FORM_SALIDA not in ("
                    + " select ss.COD_FORM_SALIDA from SOLICITUDES_SALIDA ss where ss.SOLICITUD_POR_LOTE_PROVEEDOR=1)"
                    + ") AS listado where FILAS BETWEEN " + inicioFila + " AND " + finalFila + "  ";
            LOGGER.info("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            salidasAlmacenBuscarList.clear();
            while (rs.next()) {
                SalidasAlmacen salidasAlmacenItem = new SalidasAlmacen();
                salidasAlmacenItem.setCodSalidaAlmacen(rs.getInt("COD_SALIDA_ALMACEN"));
                salidasAlmacenItem.setCodLoteProduccion(rs.getString("COD_LOTE_PRODUCCION"));
                salidasAlmacenItem.setNroSalidaAlmacen(rs.getInt("NRO_SALIDA_ALMACEN"));
                salidasAlmacenItem.setFechaSalidaAlmacen(rs.getDate("FECHA_SALIDA_ALMACEN"));
                salidasAlmacenItem.getTiposSalidasAlmacen().setNombreTipoSalidaAlmacen(rs.getString("NOMBRE_TIPO_SALIDA_ALMACEN"));
                salidasAlmacenItem.getAreasEmpresa().setNombreAreaEmpresa(rs.getString("NOMBRE_AREA_EMPRESA"));
                salidasAlmacenItem.getPersonal().setNombrePersonal(rs.getString("NOMBRES_PERSONAL"));
                salidasAlmacenItem.getPersonal().setApPaternoPersonal(rs.getString("AP_PATERNO_PERSONAL"));
                salidasAlmacenItem.getPersonal().setApMaternoPersonal(rs.getString("AP_MATERNO_PERSONAL"));
                salidasAlmacenItem.setObsSalidaAlmacen(rs.getString("OBS_SALIDA_ALMACEN"));
                salidasAlmacenItem.getComponentesProd().setNombreProdSemiterminado(rs.getString("nombre_prod_semiterminado"));
                salidasAlmacenItem.getPresentacionesProducto().setNombreProductoPresentacion(rs.getString("NOMBRE_PRODUCTO_PRESENTACION"));
                salidasAlmacenItem.setOrdenTrabajo(rs.getString("orden_trabajo"));

                salidasAlmacenBuscarList.add(salidasAlmacenItem);
            }
            rs.close();
            st.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String generarDevolucionSalidaAlmacen_action() {
        try {
            Iterator i = salidasAlmacenBuscarList.iterator();
            while (i.hasNext()) {
                SalidasAlmacen salidasAlmacenItem = (SalidasAlmacen) i.next();
                if (salidasAlmacenItem.getChecked().booleanValue() == true) {
                    salidasAlmacen = salidasAlmacenItem;
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    List salidaAlmacenList = new ArrayList();

    public String generarDevolucionesSalidaAlmacenLoteProveedor_action() {
        try {
            salidaAlmacenList.clear();
            Iterator i = salidasAlmacenBuscarList.iterator();
            while (i.hasNext()) {
                SalidasAlmacen salidasAlmacenItem = (SalidasAlmacen) i.next();
                if (salidasAlmacenItem.getChecked().booleanValue() == true) {
                    devoluciones.setSalidasAlmacen(salidasAlmacenItem);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String generarDevolucionesSalidaAlmacen_action() {
        try {
            salidaAlmacenList.clear();
            Iterator i = salidasAlmacenBuscarList.iterator();
            while (i.hasNext()) {
                SalidasAlmacen salidasAlmacenItem = (SalidasAlmacen) i.next();
                if (salidasAlmacenItem.getChecked().booleanValue() == true) {
                    salidaAlmacenList.add(salidasAlmacenItem);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String verDevolucionesDetalleEtiquetas_action() {
        try {
            devolucionesDetalle = (DevolucionesDetalle) devolucionesDetalleDataTable.getRowData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String verDevolucionesDetalleProveedorEtiquetas_action() {
        try {
            devolucionesDetalle = (DevolucionesDetalle) devolucionesDetalleProveedorDataTabla.getRowData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public String registrarDevolucionesDetalleEtiquetasFrv_action() {
        Connection con=null;
        try 
        {
            con=Util.openConnection(con);
            Iterator i = devolucionesDetalle.getDevolucionesDetalleEtiquetasList().iterator();
            Double costoSolicitudDevolucion = 0d;
            float cantidadFallados=0;
            while (i.hasNext()) {
                DevolucionesDetalleEtiquetas devolucionesDetalleEtiquetas = (DevolucionesDetalleEtiquetas) i.next();
                Double costoFrv = 0d;
                switch(devolucionesDetalleEtiquetas.getTipoFrvProceso().getCodTipoFrvProceso())
                {
                    case 1:
                    {
                        costoFrv =  devolucionesDetalleEtiquetas.getCostoFrvConMp();
                        break;
                    }
                    case 2:
                    {
                        costoFrv =  devolucionesDetalleEtiquetas.getCostoFrvConMpEp();
                        break;
                    }
                    case 3:
                    {
                        costoFrv =  devolucionesDetalleEtiquetas.getCostoFrvRecubrimiento();
                        break;
                    }
                }
                costoSolicitudDevolucion += (devolucionesDetalleEtiquetas.getCantidadFallados() *
                                    (costoFrv));
                cantidadFallados += devolucionesDetalleEtiquetas.getCantidadFallados();
            }
            if(cantidadFallados > 0)
            {
                devolucionesDetalle.setCostoSolicitudDevolucionFrv(costoSolicitudDevolucion);
                StringBuilder consulta=new StringBuilder("select a.COD_ALMACEN,a.NOMBRE_ALMACEN")
                                                    .append(" from ALMACENES_FRV_DESTINO afd")
                                                            .append(" inner join ALMACENES a on a.COD_ALMACEN = afd.COD_ALMACEN")
                                                    .append(" where ").append(costoSolicitudDevolucion).append(" between afd.COSTO_MENOR and afd.COSTO_MAYOR");
                LOGGER.info("consulta obtener almacen destino frv "+consulta.toString());
                Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                ResultSet res=st.executeQuery(consulta.toString());
                if(res.next())
                {
                    devolucionesDetalle.getAlmacenDestinoFrv().setCodAlmacen(res.getInt("COD_ALMACEN"));
                    devolucionesDetalle.getAlmacenDestinoFrv().setNombreAlmacen(res.getString("NOMBRE_ALMACEN"));
                }
            }
            else
            {
                devolucionesDetalle.setAlmacenDestinoFrv(new Almacenes());
                devolucionesDetalle.setCostoSolicitudDevolucionFrv(0d);
            }
            devolucionesDetalle.setCantidadDevueltaFallados(cantidadFallados);
            
        } 
        catch(SQLException ex)
        {
            LOGGER.warn(ex.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                con.close();
            }
            catch(Exception ex)
            {
                LOGGER.warn(ex.getMessage());
            }
        }
        return null;
    }

    public String registrarDevolucionesDetalleEtiquetas_action() {
        Connection con=null;
        try {
            con=Util.openConnection(con);
            Iterator i = devolucionesDetalle.getDevolucionesDetalleEtiquetasList().iterator();

            float cantidadDevuelta = 0;
            float cantidadDevueltaFallados = 0;
            float cantidadDevueltaFalladosProveedor = 0;
            Double costoSolicitudDevolucionFrv = 0d;
            Double costoSolicitudDevolucionFrvProveedor = 0d;
            
            while (i.hasNext()) {
                DevolucionesDetalleEtiquetas devolucionesDetalleEtiquetas = (DevolucionesDetalleEtiquetas) i.next();
                costoSolicitudDevolucionFrv += (devolucionesDetalleEtiquetas.getCantidadFallados() * devolucionesDetalleEtiquetas.getCostoSalidaAplActualizado());
                costoSolicitudDevolucionFrvProveedor += (devolucionesDetalleEtiquetas.getCantidadFalladosProveedor() * devolucionesDetalleEtiquetas.getCostoSalidaAplActualizado());
                cantidadDevuelta = cantidadDevuelta + devolucionesDetalleEtiquetas.getCantidadDevuelta();
                cantidadDevueltaFallados = cantidadDevueltaFallados + devolucionesDetalleEtiquetas.getCantidadFallados();
                cantidadDevueltaFalladosProveedor = cantidadDevueltaFalladosProveedor + devolucionesDetalleEtiquetas.getCantidadFalladosProveedor();
            }
            if(cantidadDevueltaFallados > 0)
            {
                devolucionesDetalle.setCostoSolicitudDevolucionFrv(costoSolicitudDevolucionFrv);
                
                StringBuilder consulta=new StringBuilder("select a.COD_ALMACEN,a.NOMBRE_ALMACEN")
                                                    .append(" from ALMACENES_FRV_DESTINO afd")
                                                            .append(" inner join ALMACENES a on a.COD_ALMACEN = afd.COD_ALMACEN")
                                                    .append(" where afd.APLICA_FRV_PROVEEDOR=0 ")
                                                            .append(" and ").append(costoSolicitudDevolucionFrv).append(" between afd.COSTO_MENOR and afd.COSTO_MAYOR");
                LOGGER.info("consulta obtener almacen destino frv "+consulta.toString());
                Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                ResultSet res=st.executeQuery(consulta.toString());
                if(res.next())
                {
                    devolucionesDetalle.getAlmacenDestinoFrv().setCodAlmacen(res.getInt("COD_ALMACEN"));
                    devolucionesDetalle.getAlmacenDestinoFrv().setNombreAlmacen(res.getString("NOMBRE_ALMACEN"));
                }
            }
            else
            {
                devolucionesDetalle.setAlmacenDestinoFrv(new Almacenes());
                devolucionesDetalle.setCostoSolicitudDevolucionFrv(0d);
            }
            if(cantidadDevueltaFalladosProveedor > 0)
            {
                devolucionesDetalle.setCostoSolicitudDevolucionFrvProveedor(costoSolicitudDevolucionFrvProveedor);
                StringBuilder consulta=new StringBuilder("select a.COD_ALMACEN,a.NOMBRE_ALMACEN")
                                    .append(" from ALMACENES_FRV_DESTINO afd")
                                            .append(" inner join ALMACENES a on a.COD_ALMACEN = afd.COD_ALMACEN")
                                    .append(" where afd.APLICA_FRV_PROVEEDOR=1 ")
                                            .append(" and ").append(costoSolicitudDevolucionFrvProveedor).append(" between afd.COSTO_MENOR and afd.COSTO_MAYOR");
                LOGGER.info("consulta obtener almacen destino frv "+consulta.toString());
                Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                ResultSet res=st.executeQuery(consulta.toString());
                if(res.next())
                {
                    devolucionesDetalle.getAlmacenDestinoFrvProveedor().setCodAlmacen(res.getInt("COD_ALMACEN"));
                    devolucionesDetalle.getAlmacenDestinoFrvProveedor().setNombreAlmacen(res.getString("NOMBRE_ALMACEN"));
                }
            }
            else
            {
                devolucionesDetalle.setAlmacenDestinoFrvProveedor(new Almacenes());
                devolucionesDetalle.setCostoSolicitudDevolucionFrvProveedor(0d);
            }
            devolucionesDetalle.setCantidadDevuelta(cantidadDevuelta);
            devolucionesDetalle.setCantidadDevueltaFallados(cantidadDevueltaFallados);
            devolucionesDetalle.setCantidadDevueltaFalladosProveedor(cantidadDevueltaFalladosProveedor);

        } 
        catch(SQLException ex)
        {
            LOGGER.warn(ex.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                con.close();
            }
            catch(Exception ex)
            {
                LOGGER.warn(ex.getMessage());
            }
        }
        return null;
    }

    public String guardarDevolucionProveedor_action() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ManagedIngresoAlmacen managedIngresoAlmacen = new ManagedIngresoAlmacen();
            this.codigoSolicitudDevolucion();

            mensaje = "";
            if (this.verificaTransaccionCerradaAlmacen() == true) {
                mensaje = "Las transacciones para este mes fueron cerradas ";
                return null;
            }
            if (devolucionesDetalleList.size() == 0) {
                mensaje = " No existe detalle de devolucion ";
                return null;
            }
            if (existeDetalleDevolucionDetalleAlmacen(devolucionesDetalleList) == false) {
                mensaje = " No existe detalle de Cada Item ";
                return null;
            }
            if (this.cantidadesBuenas() == 0 && this.cantidadesFalladas() == 0 && this.cantidadesFalladasProveedor() == 0) {
                mensaje = " registre cantidades para su devolucion ";
                return null;
            }

            devoluciones.setCodDevolucion(this.generaCodDevolucionAlmacen(con));
            devoluciones.setEstadoSistema(1);
            devoluciones.getEstadosDevoluciones().setCodEstadoDevolucion(1);

            int codEstadoSolicitudDevolucion = 1;

            /**
             * String consulta = " INSERT INTO DEVOLUCIONES ( COD_DEVOLUCION,
             * NRO_DEVOLUCION, COD_FORMULARIO_DEV, COD_ALMACEN, " + "
             * COD_SALIDA_ALMACEN, COD_GESTION, COD_ESTADO_DEVOLUCION,
             * ESTADO_SISTEMA, OBS_DEVOLUCION, COD_SALIDA_ALMACEN_AUX) " + "
             * VALUES ( '"+devoluciones.getCodDevolucion()+"', (select
             * ISNULL(max(nro_devolucion),0)+1 from devoluciones " + " where
             * cod_gestion='"+usuario.getGestionesGlobal().getCodGestion()+"'
             * and
             * cod_almacen='"+usuario.getAlmacenesGlobal().getCodAlmacen()+"'
             * and estado_sistema=1 ) ,'"+devoluciones.getCodFormularioDev()+"',
             * " + " '"+usuario.getAlmacenesGlobal().getCodAlmacen()+"' ,
             * '"+devoluciones.getSalidasAlmacen().getCodSalidaAlmacen()+"' ,
             * '"+ usuario.getGestionesGlobal().getCodGestion()+"', " + "
             * '"+devoluciones.getEstadosDevoluciones().getCodEstadoDevolucion()+"','"+devoluciones.getEstadoSistema()+"',
             * '"+devoluciones.getObsDevolucion()+"',
             * '"+devoluciones.getCodSalidaAlmacenAux()+"' ); ";
             */
            String codRegSolicitud = "INSERT INTO SOLICITUD_DEVOLUCIONES(COD_SOLICITUD_DEVOLUCION,";
            codRegSolicitud += " COD_ESTADO_SOLICITUD_DEVOLUCION, FECHA_SOLICITUD, COD_PERSONAL, OBSERVACION,COD_SALIDA_ALMACEN,COD_ALMACEN_DESTINO) ";
            codRegSolicitud += " VALUES ('" + codSolicitud + "','" + codEstadoSolicitudDevolucion + "','" + sdf.format(new Date()) + "','" + usuario.getUsuarioModuloBean().getCodUsuarioGlobal() + "'";
            codRegSolicitud += " ,'" + getObservacion() + "','" + devoluciones.getSalidasAlmacen().getCodSalidaAlmacen() + "','"+usuario.getAlmacenesGlobal().getCodAlmacen()+"')";
            LOGGER.info("con registrar solicitud " + codRegSolicitud);
            st.executeUpdate(codRegSolicitud);

            Iterator i = devolucionesDetalleList.iterator();

            while (i.hasNext()) {
                DevolucionesDetalle devolucionesDetalleItem = (DevolucionesDetalle) i.next();
                if (devolucionesDetalleItem.getCantidadDevuelta() > 0 || devolucionesDetalleItem.getCantidadDevueltaFallados() > 0 || devolucionesDetalleItem.getCantidadDevueltaFalladosProveedor() > 0) {
                    String conRegDetalle = "INSERT INTO SOLICITUD_DEVOLUCIONES_DETALLE(COD_SOLICITUD_DEVOLUCION,";
                    conRegDetalle += "COD_MATERIAL, CANTIDAD_DEVUELTA, CANTIDAD_DEVUELTA_FALLADOS,CANTIDAD_DEVUELTA_FALLADOS_PROVEEDOR,COD_UNIDAD_MEDIDA,COD_SALIDA_ALMACEN)";
                    conRegDetalle += "VALUES ('" + codSolicitud + "'," + devolucionesDetalleItem.getMateriales().getCodMaterial() + ", ";
                    conRegDetalle += "'" + devolucionesDetalleItem.getCantidadDevuelta() + "','0'";
                    conRegDetalle += ",'0','" + devolucionesDetalleItem.getUnidadesMedida().getCodUnidadMedida() + "','" + devolucionesDetalleItem.getSalidasAlmacen().getCodSalidaAlmacen() + "')";
                    LOGGER.info("consulta detalle " + conRegDetalle);
                    st.executeUpdate(conRegDetalle);

                    Iterator i1 = devolucionesDetalleItem.getDevolucionesDetalleEtiquetasList().iterator();
                    while (i1.hasNext()) {
                        DevolucionesDetalleEtiquetas devolucionesDetalleEtiquetasItem = (DevolucionesDetalleEtiquetas) i1.next();
                        String conRegEtiquet = "INSERT INTO SOLICITUD_DEVOLUCIONES_DETALLE_ETIQUETAS(COD_SOLICITUD_DEVOLUCION,";
                        conRegEtiquet += " COD_INGRESO_ALMACEN, COD_MATERIAL, ETIQUETA,CANTIDAD_DEVUELTA, CANTIDAD_DEVUELTA_FALLADOS,";
                        conRegEtiquet += "CANTIDAD_DEVUELTA_FALLADOS_PROVEEDOR) VALUES ('" + codSolicitud + "',";
                        conRegEtiquet += "'" + devolucionesDetalleEtiquetasItem.getIngresosAlmacen().getCodIngresoAlmacen() + "','" + devolucionesDetalleEtiquetasItem.getMateriales().getCodMaterial() + "',";
                        conRegEtiquet += "'" + devolucionesDetalleEtiquetasItem.getEtiqueta() + "','" + devolucionesDetalleEtiquetasItem.getCantidadDevuelta() + "'";
                        conRegEtiquet += ",'" + devolucionesDetalleEtiquetasItem.getCantidadFallados() + "','" + devolucionesDetalleEtiquetasItem.getCantidadFalladosProveedor() + "')";
                        LOGGER.info("con reg etiqueta " + conRegEtiquet);
                        st.executeUpdate(conRegEtiquet);
                    }
                }
            }

            st.close();
            con.close();
            mensaje = "1";

        } catch (Exception e) {
            LOGGER.warn(e);
            mensaje = "Ocurrio un error al momento de registrar la solicitud de devolucion, intente de nuevo";
        }
        return null;
    }

    public String guardarSolicitudDevolucion_action()throws SQLException
    {
        if (this.verificaTransaccionCerradaAlmacen() == true) {
            mensaje = "Las transacciones para este mes fueron cerradas ";
            return null;
        }
        if (devolucionesDetalleList.size() == 0) {
            mensaje = " No existe detalle de devolucion ";
            return null;
        }
        if (existeDetalleDevolucionDetalleAlmacen(devolucionesDetalleList) == false) {
            mensaje = " No existe detalle de Cada Item ";
            return null;
        }
        if (this.cantidadesBuenas() == 0 && this.cantidadesFalladas() == 0 && this.cantidadesFalladasProveedor() == 0) {
            mensaje = " registre cantidades para su devolucion ";
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Connection con = null;
        try 
        {
            mensaje = "";
            con = Util.openConnection(con);
            con.setAutoCommit(false);
            StringBuilder consulta = new StringBuilder(" select ISNULL(MAX(sd.COD_SOLICITUD_DEVOLUCION),0)+1 as codigo from SOLICITUD_DEVOLUCIONES sd");
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet res =st.executeQuery(consulta.toString());
            int codigoSolicitud = 0;
            if(res.next())codigoSolicitud=res.getInt("codigo");
            int cantidadDevolucionBuenos = 0;
            List<Integer> codigosAlmacenFrv =new ArrayList<Integer>();
            codigosSolicitudDevolucionGeneradas =new ArrayList<Integer>();
            consulta=new StringBuilder(" INSERT INTO SOLICITUD_DEVOLUCIONES(COD_SOLICITUD_DEVOLUCION,")
                                        .append(" COD_ESTADO_SOLICITUD_DEVOLUCION, FECHA_SOLICITUD, COD_PERSONAL,")
                                        .append(" OBSERVACION,COD_SALIDA_ALMACEN,COD_ALMACEN_DESTINO )")
                                .append(" VALUES (")
                                        .append("?,")//codigo solicitud
                                        .append("?,")//cod estado solicitud
                                        .append("GETDATE(),")
                                        .append(usuario.getUsuarioModuloBean().getCodUsuarioGlobal()).append(",")//codigo de usuario
                                        .append("?,")//observaciones
                                        .append("?,")// codigo salida almacen
                                        .append("?")// COD aLMACEN DESTINO
                                .append(")");
            LOGGER.debug(" consulta registrar solicitud devolucion "+consulta.toString());
            PreparedStatement pstRegSolicitud = con.prepareStatement(consulta.toString());
            consulta = new StringBuilder("INSERT INTO SOLICITUD_DEVOLUCIONES_DETALLE(COD_SOLICITUD_DEVOLUCION,")
                                        .append("COD_MATERIAL, CANTIDAD_DEVUELTA, CANTIDAD_DEVUELTA_FALLADOS,")
                                        .append(" CANTIDAD_DEVUELTA_FALLADOS_PROVEEDOR,COD_UNIDAD_MEDIDA,")
                                        .append(" COD_SALIDA_ALMACEN,COSTO_SOLICITUD_DEVOLUCION_FRV,COSTO_SOLICITUD_DEVOLUCION_FRV_PROVEEDOR)")
                                .append(" VALUES (")
                                        .append("?,")//cod Solicitud
                                        .append("?,")//codigo material
                                        .append("?,")//cantidad devuelta
                                        .append("?,")//cantidad fallados
                                        .append("?,")//frv proveedor
                                        .append("?,")//unidad de medida
                                        .append("?,")//salida almacen
                                        .append("?,")//costo DEVOLUCIONES FALLADOS
                                        .append("?")//costo DEVOLUCION FALLADOS PROVEEDORES
                                .append(")");
            LOGGER.debug(" consulta registrar detalle "+consulta.toString());
            PreparedStatement pst = con.prepareStatement(consulta.toString());
            consulta=new StringBuilder("INSERT INTO SOLICITUD_DEVOLUCIONES_DETALLE_ETIQUETAS(COD_SOLICITUD_DEVOLUCION,")
                                        .append(" COD_INGRESO_ALMACEN, COD_MATERIAL, ETIQUETA,CANTIDAD_DEVUELTA, CANTIDAD_DEVUELTA_FALLADOS,CANTIDAD_DEVUELTA_FALLADOS_PROVEEDOR)")
                                .append(" VALUES(")
                                        .append("?,")//cod solicitud debvolucion
                                        .append("?,")//cod ingreso almacen
                                        .append("?,")//cod material
                                        .append("?,")//etiqueta
                                        .append("?,")//cantidad devuelta
                                        .append("?,")//cantidad devuelta fallados
                                        .append("?")//cantidad devuelta fallados proveedor
                                .append(")");
            LOGGER.debug(" consulta registrar etiquetas "+consulta.toString());
            PreparedStatement pstEtiqueta=con.prepareStatement(consulta.toString());
            Iterator i = devolucionesDetalleList.iterator();
            while (i.hasNext()) 
            {
                DevolucionesDetalle devolucionesDetalleItem = (DevolucionesDetalle) i.next();
                cantidadDevolucionBuenos += (devolucionesDetalleItem.getCantidadDevuelta() > 0 ? 1 : 0);
                if(devolucionesDetalleItem.getCantidadDevueltaFallados() > 0 || devolucionesDetalleItem.getCantidadDevueltaFalladosProveedor() > 0)
                {
                    boolean encontradoFrv =false;
                    boolean encontradoFrvProveedor = false;
                    for(int index = 0 ; index < codigosAlmacenFrv.size() ; index++)
                    {
                        encontradoFrv = codigosAlmacenFrv.get(index) == devolucionesDetalleItem.getAlmacenDestinoFrv().getCodAlmacen();
                        encontradoFrvProveedor = codigosAlmacenFrv.get(index) == devolucionesDetalleItem.getAlmacenDestinoFrvProveedor().getCodAlmacen();
                        if(encontradoFrv  || encontradoFrvProveedor)
                        {
                            LOGGER.info("--------------------Encontrado solicitud existente almacen: "+codigosAlmacenFrv.get(index)+" ,codigo solicitud: "+codigosSolicitudDevolucionGeneradas.get(index)+"----------------");
                            LOGGER.info("--------------------adicionando detalle frv----------------");
                            Double cantidadFrv = (encontradoFrv ? devolucionesDetalleItem.getCantidadDevueltaFallados() : 0d);
                            Double cantidadFrvProveedor = (encontradoFrvProveedor ? devolucionesDetalleItem.getCantidadDevueltaFalladosProveedor() : 0d);
                            Double costoFrv = (encontradoFrv ? devolucionesDetalleItem.getCostoSolicitudDevolucionFrv() : 0d);
                            Double costoFrvProveedor = (encontradoFrvProveedor ? devolucionesDetalleItem.getCostoSolicitudDevolucionFrvProveedor() : 0d);
                            pst.setInt(1,codigosSolicitudDevolucionGeneradas.get(index));LOGGER.info("p1: "+codigosSolicitudDevolucionGeneradas.get(index));
                            pst.setString(2,devolucionesDetalleItem.getMateriales().getCodMaterial());LOGGER.info("p2: "+devolucionesDetalleItem.getMateriales().getCodMaterial());
                            pst.setDouble(3,0);LOGGER.info("p3:0");
                            pst.setDouble(4,cantidadFrv);LOGGER.info("p4: "+cantidadFrv);
                            pst.setDouble(5,cantidadFrvProveedor);LOGGER.info("p5: "+cantidadFrvProveedor);
                            pst.setInt(6, devolucionesDetalleItem.getUnidadesMedida().getCodUnidadMedida());LOGGER.info("p6: "+devolucionesDetalleItem.getUnidadesMedida().getCodUnidadMedida());
                            pst.setInt(7, devolucionesDetalleItem.getSalidasAlmacen().getCodSalidaAlmacen());LOGGER.info("p7: "+devolucionesDetalleItem.getSalidasAlmacen().getCodSalidaAlmacen());
                            pst.setDouble(8,costoFrv);LOGGER.info("p8: "+costoFrv);
                            pst.setDouble(9,costoFrvProveedor);LOGGER.info("p9: "+costoFrvProveedor);
                            
                            if(pst.executeUpdate()>0)LOGGER.info("----------------se registro detalle FRV-----------------");
                            for(DevolucionesDetalleEtiquetas bean : (List<DevolucionesDetalleEtiquetas>)devolucionesDetalleItem.getDevolucionesDetalleEtiquetasList())
                            {
                                if(bean.getCantidadFallados() > 0 || bean.getCantidadFalladosProveedor() > 0)
                                {
                                    cantidadFrv = (encontradoFrv ? bean.getCantidadFallados() : 0d);
                                    cantidadFrvProveedor = (encontradoFrvProveedor ? bean.getCantidadFalladosProveedor() : 0d);
                                    LOGGER.info("---------------registrando devolucion etiquetas frv----------------------");
                                    pstEtiqueta.setInt(1,codigosSolicitudDevolucionGeneradas.get(index));LOGGER.info("p1: "+codigosSolicitudDevolucionGeneradas.get(index));
                                    pstEtiqueta.setInt(2,bean.getIngresosAlmacen().getCodIngresoAlmacen());LOGGER.info("p2: "+bean.getIngresosAlmacen().getCodIngresoAlmacen());
                                    pstEtiqueta.setString(3,devolucionesDetalleItem.getMateriales().getCodMaterial());LOGGER.info("p3: "+devolucionesDetalleItem.getMateriales().getCodMaterial());
                                    pstEtiqueta.setInt(4,bean.getEtiqueta());LOGGER.info("p4: "+bean.getEtiqueta());
                                    pstEtiqueta.setDouble(5,0d);LOGGER.info("p5: 0");
                                    pstEtiqueta.setDouble(6,cantidadFrv);LOGGER.info("p6: "+cantidadFrv);
                                    pstEtiqueta.setDouble(7,cantidadFrvProveedor);LOGGER.info("p7: "+cantidadFrvProveedor);
                                    if(pstEtiqueta.executeUpdate() > 0)LOGGER.info("-----------se registro devolucion etiqueta frv-----------------");
                                }
                            }
                            break;
                        }
                    }
                    
                    if(!encontradoFrv && devolucionesDetalleItem.getCantidadDevueltaFallados() > 0)
                    {
                        Double cantidadFrvProveedor = ((!encontradoFrvProveedor) &&
                                                        (devolucionesDetalleItem.getAlmacenDestinoFrv().getCodAlmacen() == devolucionesDetalleItem.getAlmacenDestinoFrvProveedor().getCodAlmacen()))?
                                                        devolucionesDetalleItem.getCantidadDevueltaFalladosProveedor() : 0d;
                        Double costoFrvProveedor = ((!encontradoFrvProveedor) &&
                                                        (devolucionesDetalleItem.getAlmacenDestinoFrv().getCodAlmacen() == devolucionesDetalleItem.getAlmacenDestinoFrvProveedor().getCodAlmacen()))?
                                                        devolucionesDetalleItem.getCostoSolicitudDevolucionFrvProveedor() : 0d;
                        encontradoFrvProveedor = (devolucionesDetalleItem.getAlmacenDestinoFrv().getCodAlmacen() == devolucionesDetalleItem.getAlmacenDestinoFrvProveedor().getCodAlmacen()) || (encontradoFrvProveedor);
                        
                        //registro cabecera solicitud
                        LOGGER.info("--------------------registrando solicitud frv----------------");
                        pstRegSolicitud.setInt(1,codigoSolicitud);LOGGER.info("p1 :"+codigoSolicitud);
                        pstRegSolicitud.setInt(2,1);LOGGER.info("p2: "+1);
                        pstRegSolicitud.setString(3,observacion);LOGGER.info("p3: "+observacion);
                        pstRegSolicitud.setInt(4,salidasAlmacen.getCodSalidaAlmacen());LOGGER.info("p4: "+salidasAlmacen.getCodSalidaAlmacen());
                        pstRegSolicitud.setInt(5,devolucionesDetalleItem.getAlmacenDestinoFrv().getCodAlmacen());LOGGER.info("p5: "+devolucionesDetalleItem.getAlmacenDestinoFrv().getCodAlmacen());
                        if(pstRegSolicitud.executeUpdate()>0)LOGGER.info("-----------se registro solicitud frv-------------");
                        
                        //registrando detalle frv
                        LOGGER.info("--------------------registrando detalle frv----------------");
                        pst.setInt(1,codigoSolicitud);LOGGER.info("p1: "+codigoSolicitud);
                        pst.setString(2,devolucionesDetalleItem.getMateriales().getCodMaterial());LOGGER.info("p2: "+devolucionesDetalleItem.getMateriales().getCodMaterial());
                        pst.setDouble(3,0);LOGGER.info("p3:0");
                        pst.setDouble(4,devolucionesDetalleItem.getCantidadDevueltaFallados());LOGGER.info("p4: "+devolucionesDetalleItem.getCantidadDevueltaFallados());
                        pst.setDouble(5,cantidadFrvProveedor);LOGGER.info("p5: "+cantidadFrvProveedor);
                        pst.setInt(6, devolucionesDetalleItem.getUnidadesMedida().getCodUnidadMedida());LOGGER.info("p6: "+devolucionesDetalleItem.getUnidadesMedida().getCodUnidadMedida());
                        pst.setInt(7, devolucionesDetalleItem.getSalidasAlmacen().getCodSalidaAlmacen());LOGGER.info("p7: "+devolucionesDetalleItem.getSalidasAlmacen().getCodSalidaAlmacen());
                        pst.setDouble(8,devolucionesDetalleItem.getCostoSolicitudDevolucionFrv());LOGGER.info("p8: "+devolucionesDetalleItem.getCostoSolicitudDevolucionFrv());
                        pst.setDouble(9,costoFrvProveedor);LOGGER.info("p9: "+costoFrvProveedor);
                        if(pst.executeUpdate()>0)LOGGER.info("----------------se registro detalle FRV-----------------");
                        for(DevolucionesDetalleEtiquetas bean : (List<DevolucionesDetalleEtiquetas>)devolucionesDetalleItem.getDevolucionesDetalleEtiquetasList())
                        {
                            if(bean.getCantidadFallados()>0 || bean.getCantidadFalladosProveedor() > 0)
                            {
                                LOGGER.info("---------------registrando devolucion etiquetas frv----------------------");
                                pstEtiqueta.setInt(1,codigoSolicitud);LOGGER.info("p1: "+codigoSolicitud);
                                pstEtiqueta.setInt(2,bean.getIngresosAlmacen().getCodIngresoAlmacen());LOGGER.info("p2: "+bean.getIngresosAlmacen().getCodIngresoAlmacen());
                                pstEtiqueta.setString(3,devolucionesDetalleItem.getMateriales().getCodMaterial());LOGGER.info("p3: "+devolucionesDetalleItem.getMateriales().getCodMaterial());
                                pstEtiqueta.setInt(4,bean.getEtiqueta());LOGGER.info("p4: "+bean.getEtiqueta());
                                pstEtiqueta.setDouble(5,0d);LOGGER.info("p5: 0");
                                pstEtiqueta.setDouble(6,bean.getCantidadFallados());LOGGER.info("p6:"+bean.getCantidadFallados());
                                pstEtiqueta.setDouble(7,(cantidadFrvProveedor > 0 ? bean.getCantidadFalladosProveedor() : 0d));LOGGER.info("p6:"+(cantidadFrvProveedor > 0 ? bean.getCantidadFalladosProveedor():0d));
                                if(pstEtiqueta.executeUpdate() > 0)LOGGER.info("-----------se registro devolucion etiqueta frv-----------------");
                            }
                        }
                        //registro llaves generadas
                        codigosAlmacenFrv.add(devolucionesDetalleItem.getAlmacenDestinoFrv().getCodAlmacen());
                        codigosSolicitudDevolucionGeneradas.add(codigoSolicitud);
                        codigoSolicitud ++;
                    }
                    
                    if(!encontradoFrvProveedor && devolucionesDetalleItem.getCantidadDevueltaFalladosProveedor() > 0)
                    {
                        //registro cabecera solicitud
                        LOGGER.info("--------------------registrando solicitud frv proveedor----------------");
                        pstRegSolicitud.setInt(1,codigoSolicitud);LOGGER.info("p1 :"+codigoSolicitud);
                        pstRegSolicitud.setInt(2,1);LOGGER.info("p2: "+1);
                        pstRegSolicitud.setString(3,observacion);LOGGER.info("p3: "+observacion);
                        pstRegSolicitud.setInt(4,salidasAlmacen.getCodSalidaAlmacen());LOGGER.info("p4: "+salidasAlmacen.getCodSalidaAlmacen());
                        pstRegSolicitud.setInt(5,devolucionesDetalleItem.getAlmacenDestinoFrvProveedor().getCodAlmacen());LOGGER.info("p5: "+devolucionesDetalleItem.getAlmacenDestinoFrvProveedor().getCodAlmacen());
                        if(pstRegSolicitud.executeUpdate()>0)LOGGER.info("-----------se registro solicitud frv-------------");
                        
                        //registrando detalle frv
                        LOGGER.info("--------------------registrando detalle frv----------------");
                        pst.setInt(1,codigoSolicitud);LOGGER.info("p1: "+codigoSolicitud);
                        pst.setString(2,devolucionesDetalleItem.getMateriales().getCodMaterial());LOGGER.info("p2: "+devolucionesDetalleItem.getMateriales().getCodMaterial());
                        pst.setDouble(3,0);LOGGER.info("p3:0");
                        pst.setDouble(4,0);LOGGER.info("p4: 0");
                        pst.setDouble(5,devolucionesDetalleItem.getCantidadDevueltaFalladosProveedor());LOGGER.info("p5: "+devolucionesDetalleItem.getCantidadDevueltaFalladosProveedor());
                        pst.setInt(6, devolucionesDetalleItem.getUnidadesMedida().getCodUnidadMedida());LOGGER.info("p6: "+devolucionesDetalleItem.getUnidadesMedida().getCodUnidadMedida());
                        pst.setInt(7, devolucionesDetalleItem.getSalidasAlmacen().getCodSalidaAlmacen());LOGGER.info("p7: "+devolucionesDetalleItem.getSalidasAlmacen().getCodSalidaAlmacen());
                        pst.setDouble(8,0);LOGGER.info("p8: 0");//costo frv
                        pst.setDouble(9,devolucionesDetalleItem.getCostoSolicitudDevolucionFrvProveedor());LOGGER.info("p9: "+devolucionesDetalleItem.getCostoSolicitudDevolucionFrvProveedor());
                        if(pst.executeUpdate()>0)LOGGER.info("----------------se registro detalle FRV-----------------");
                        for(DevolucionesDetalleEtiquetas bean : (List<DevolucionesDetalleEtiquetas>)devolucionesDetalleItem.getDevolucionesDetalleEtiquetasList())
                        {
                            if(bean.getCantidadFalladosProveedor()>0)
                            {
                                LOGGER.info("---------------registrando devolucion etiquetas frv----------------------");
                                pstEtiqueta.setInt(1,codigoSolicitud);LOGGER.info("p1: "+codigoSolicitud);
                                pstEtiqueta.setInt(2,bean.getIngresosAlmacen().getCodIngresoAlmacen());LOGGER.info("p2: "+bean.getIngresosAlmacen().getCodIngresoAlmacen());
                                pstEtiqueta.setString(3,devolucionesDetalleItem.getMateriales().getCodMaterial());LOGGER.info("p3: "+devolucionesDetalleItem.getMateriales().getCodMaterial());
                                pstEtiqueta.setInt(4,bean.getEtiqueta());LOGGER.info("p4: "+bean.getEtiqueta());
                                pstEtiqueta.setDouble(5,0d);LOGGER.info("p5: 0");
                                pstEtiqueta.setDouble(6,0);LOGGER.info("p6:0");
                                pstEtiqueta.setDouble(7, bean.getCantidadFalladosProveedor());LOGGER.info("p6:"+bean.getCantidadFalladosProveedor());
                                if(pstEtiqueta.executeUpdate() > 0)LOGGER.info("-----------se registro devolucion etiqueta frv-----------------");
                            }
                        }
                        //registro llaves generadas
                        codigosAlmacenFrv.add(devolucionesDetalleItem.getAlmacenDestinoFrvProveedor().getCodAlmacen());
                        codigosSolicitudDevolucionGeneradas.add(codigoSolicitud);
                        codigoSolicitud ++;
                    }
                }
            }
            // <editor-fold defaultstate="collapsed" desc="registro de buenos">
                if(cantidadDevolucionBuenos>0)
                {
                    LOGGER.info("--------------------registrando solicitud DEVOLUCION BUENOS----------------");
                    pstRegSolicitud.setInt(1,codigoSolicitud);LOGGER.info("p1: "+codigoSolicitud);
                    codigosSolicitudDevolucionGeneradas.add(codigoSolicitud);
                    pstRegSolicitud.setInt(2,1);LOGGER.info("p2" +codigoSolicitud);
                    pstRegSolicitud.setString(3,observacion);LOGGER.info("p3: "+observacion);
                    pstRegSolicitud.setInt(4,salidasAlmacen.getCodSalidaAlmacen());LOGGER.info("p4: "+salidasAlmacen.getCodSalidaAlmacen());
                    pstRegSolicitud.setInt(5,usuario.getAlmacenesGlobal().getCodAlmacen());LOGGER.info("p5: "+usuario.getAlmacenesGlobal().getCodAlmacen());
                    if(pstRegSolicitud.executeUpdate()>0)LOGGER.info("se registro solicitud buenos");
                    i = devolucionesDetalleList.iterator();
                    while (i.hasNext()) 
                    {
                        DevolucionesDetalle devolucionesDetalleItem = (DevolucionesDetalle) i.next();
                        if( devolucionesDetalleItem.getCantidadDevuelta() > 0 )
                        {
                            LOGGER.info("-------------------registrando devolucion buenos--------------");
                            pst.setInt(1,codigoSolicitud);LOGGER.info("p1: "+codigoSolicitud);
                            pst.setString(2,devolucionesDetalleItem.getMateriales().getCodMaterial());LOGGER.info("p2: "+devolucionesDetalleItem.getMateriales().getCodMaterial());
                            pst.setDouble(3,devolucionesDetalleItem.getCantidadDevuelta());LOGGER.info("p3: "+devolucionesDetalleItem.getCantidadDevuelta());
                            pst.setDouble(4,0);LOGGER.info("p4:0");//fallados
                            pst.setDouble(5,0);LOGGER.info("p5:0");//fallados proveedor
                            pst.setInt(6, devolucionesDetalleItem.getUnidadesMedida().getCodUnidadMedida());LOGGER.info("p6: "+devolucionesDetalleItem.getUnidadesMedida().getCodUnidadMedida());
                            pst.setInt(7, devolucionesDetalleItem.getSalidasAlmacen().getCodSalidaAlmacen());LOGGER.info("p7: "+devolucionesDetalleItem.getSalidasAlmacen().getCodSalidaAlmacen());
                            pst.setDouble(8,0);LOGGER.info("p8: 0");
                            pst.setDouble(9,0);LOGGER.info("p9: 0");
                            if(pst.executeUpdate()>0)LOGGER.info("-------------------se registro detalle buenos---------------------");
                            for(DevolucionesDetalleEtiquetas bean : (List<DevolucionesDetalleEtiquetas>)devolucionesDetalleItem.getDevolucionesDetalleEtiquetasList())
                            {
                                if(bean.getCantidadDevuelta()>0)
                                {
                                    LOGGER.info("---------------registrando devolucion etiquetas----------------------");
                                    pstEtiqueta.setInt(1,codigoSolicitud);LOGGER.info("p1: "+codigoSolicitud);
                                    pstEtiqueta.setInt(2,bean.getIngresosAlmacen().getCodIngresoAlmacen());LOGGER.info("p2: "+bean.getIngresosAlmacen().getCodIngresoAlmacen());
                                    pstEtiqueta.setString(3,devolucionesDetalleItem.getMateriales().getCodMaterial());LOGGER.info("p3: "+devolucionesDetalleItem.getMateriales().getCodMaterial());
                                    pstEtiqueta.setInt(4,bean.getEtiqueta());LOGGER.info("p4: "+bean.getEtiqueta());
                                    pstEtiqueta.setDouble(5,bean.getCantidadDevuelta());LOGGER.info("p5:"+bean.getCantidadDevuelta());
                                    pstEtiqueta.setDouble(6,0d);LOGGER.info("p6: 0");
                                    pstEtiqueta.setDouble(7,0d);LOGGER.info("p7: 0");
                                    if(pstEtiqueta.executeUpdate() > 0)LOGGER.info("-----------se registro devolucion etiqueta-----------------");
                                }
                            }
                        }
                    }
                }
            //</editor-fold>
            
            con.commit();
            st.close();

        } 
        catch(SQLException ex)
        {
            LOGGER.warn(ex.getMessage());
            con.rollback();
            mensaje="Ocurrio un error al momento de registrar la solicitud de devolucion";
        }
        catch (Exception e) {
            mensaje="Ocurrio un error al momento de registrar la solicitud de devolucion";
            e.printStackTrace();
        }
        finally
        {
            con.close();
        }
        return null;
    }

    public Date buscaFechaVencimiento(DevolucionesDetalle devolucionesDetalleItem) {
        Date fechaVencimiento = new Date();
        try {
            Iterator i = devolucionesDetalleItem.getDevolucionesDetalleEtiquetasList().iterator();
            while (i.hasNext()) {
                DevolucionesDetalleEtiquetas devolucionesDetalleEtiquetas = (DevolucionesDetalleEtiquetas) i.next();
                fechaVencimiento = devolucionesDetalleEtiquetas.getFechaVencimiento();
                break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return fechaVencimiento;
    }

    public int obtieneAlmacenFrv(ManagedAccesoSistema usuario, Connection con) {
        int codAlmacenFrv = 0;
        try {
            //con = null;
            //con = Util.openConnection(con);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String consulta = " select COD_ALMACEN_FRV from ALMACENES_FRV a where a.COD_ALMACEN = '" + usuario.getAlmacenesGlobal().getCodAlmacen() + "' ";
            LOGGER.info("consulta " + consulta);
            ResultSet rs = st.executeQuery(consulta);
            if (rs.next()) {
                codAlmacenFrv = rs.getInt("COD_ALMACEN_FRV");
            }
            st.close();
            rs.close();
            //con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return codAlmacenFrv;
    }

    public float cantidadesFalladas() {
        float cantidadesFalladas = 0;
        try {
            Iterator i = devolucionesDetalleList.iterator();
            while (i.hasNext()) {
                DevolucionesDetalle devolucionesDetalleItem = (DevolucionesDetalle) i.next();
                Iterator i1 = devolucionesDetalleItem.getDevolucionesDetalleEtiquetasList().iterator();
                while (i1.hasNext()) {
                    DevolucionesDetalleEtiquetas devolucionesDetalleEtiquetasItem = (DevolucionesDetalleEtiquetas) i1.next();
                    cantidadesFalladas = cantidadesFalladas + devolucionesDetalleEtiquetasItem.getCantidadFallados();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cantidadesFalladas;
    }

    public float cantidadesBuenas() {
        float cantidadesBuenas = 0;
        try {
            Iterator i = devolucionesDetalleList.iterator();
            while (i.hasNext()) {
                DevolucionesDetalle devolucionesDetalleItem = (DevolucionesDetalle) i.next();
                Iterator i1 = devolucionesDetalleItem.getDevolucionesDetalleEtiquetasList().iterator();
                while (i1.hasNext()) {
                    DevolucionesDetalleEtiquetas devolucionesDetalleEtiquetasItem = (DevolucionesDetalleEtiquetas) i1.next();
                    cantidadesBuenas = cantidadesBuenas + devolucionesDetalleEtiquetasItem.getCantidadDevuelta();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cantidadesBuenas;
    }

    public float cantidadesFalladasProveedor() {
        float cantidadesFalladas = 0;
        try {
            Iterator i = devolucionesDetalleList.iterator();
            while (i.hasNext()) {
                DevolucionesDetalle devolucionesDetalleItem = (DevolucionesDetalle) i.next();
                Iterator i1 = devolucionesDetalleItem.getDevolucionesDetalleEtiquetasList().iterator();
                while (i1.hasNext()) {
                    DevolucionesDetalleEtiquetas devolucionesDetalleEtiquetasItem = (DevolucionesDetalleEtiquetas) i1.next();
                    cantidadesFalladas = cantidadesFalladas + devolucionesDetalleEtiquetasItem.getCantidadFalladosProveedor();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cantidadesFalladas;
    }

    public int generaCodDevolucionAlmacen(Connection con) {
        int codSalidaAlmacen = 0;
        try {
            //con = null;
            //con = Util.openConnection(con);
            String consulta = " select isnull(max(d.COD_DEVOLUCION),0)+1 COD_DEVOLUCION from DEVOLUCIONES d ";
            LOGGER.info("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            if (rs.next()) {
                codSalidaAlmacen = rs.getInt("COD_DEVOLUCION");
            }
            //con.close();
            st.close();
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return codSalidaAlmacen;
    }

    public boolean verificaTransaccionCerradaAlmacen() {

        boolean transaccionCerradaAlmacen = false;
        Date fechaActual = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select * from estados_transacciones_almacen "
                    + " where cod_gestion='" + usuario.getGestionesGlobal().getCodGestion() + "' "
                    + " and cod_mes = '" + Integer.valueOf(sdf.format(fechaActual)) + "'  "
                    + " and estado=1 ";
            LOGGER.info("consulta " + consulta);
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

    public boolean existeDetalleDevolucionDetalleAlmacen(List devolucionesDetalleList) {
        boolean existeDetalle = true;
        try {
            Iterator i = devolucionesDetalleList.iterator();
            while (i.hasNext()) {
                DevolucionesDetalle devolucionesDetalleItem = (DevolucionesDetalle) i.next();
                if (devolucionesDetalleItem.getDevolucionesDetalleEtiquetasList().size() == 0) {
                    existeDetalle = false;
                    break;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return existeDetalle;
    }

    public String verReporteSalidaAlmacenLoteProveedor_action() {
        SalidasAlmacen salidasAlmacen = (SalidasAlmacen) salidasAlmacenLoteProveedorDataTable.getRowData();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext ec = facesContext.getExternalContext();
        Map map = ec.getSessionMap();
        map.put("salidasAlmacen", salidasAlmacen);
        return null;
    }

    public String verReporteSalidaAlmacen_action() {
        SalidasAlmacen salidasAlmacen = (SalidasAlmacen) salidasAlmacenDataTable.getRowData();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext ec = facesContext.getExternalContext();
        Map map = ec.getSessionMap();
        map.put("salidasAlmacen", salidasAlmacen);
        return null;
    }

    public String atras_action_1() {
        super.back();
        this.listadoDevolucionesAlmacen();
        cantidadfilas = solicitudesList.size();

        return "";
    }

    public String atras_action_2() {
        super.back();
        this.cargarListadoSolicitudesAprobar();
        cantidadfilas = getSolicitudesAprobarList().size();

        return "";
    }

    public String siguiente_action_1() {
        super.next();
        this.listadoDevolucionesAlmacen();
        cantidadfilas = solicitudesList.size();

        return "";
    }

    public String siguiente_action_2() {
        super.next();
        this.cargarListadoSolicitudesAprobar();
        cantidadfilas = getSolicitudesAprobarList().size();
        return "";
    }

    public String imprimirDevolucion_action() {
        Iterator i = devolucionesList.iterator();
        while (i.hasNext()) {
            IngresosAlmacen ingresosAlmacen = (IngresosAlmacen) i.next();
            if (ingresosAlmacen.getChecked().booleanValue() == true) {
                FacesContext facesContext = FacesContext.getCurrentInstance();
                ExternalContext context = facesContext.getExternalContext();
                Map map = context.getSessionMap();
                map.put("ingresosAlmacen", ingresosAlmacen);
                map.put("managedAccesoSistema", usuario);
                break;
            }
        }
        return null;
    }

    public String editarDevolucionesAlmacen_action() {
        try {
            Iterator i = devolucionesList.iterator();
            while (i.hasNext()) {
                IngresosAlmacen ingresosAlmacen1 = (IngresosAlmacen) i.next();
                if (ingresosAlmacen1.getChecked().booleanValue() == true) {
                    ingresosAlmacen = ingresosAlmacen1;
                    devoluciones = ingresosAlmacen.getDevoluciones();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getCargarEditarDevolucionesAlmacen() {
        try {
            String consulta = " select s.NRO_SALIDA_ALMACEN,a.NOMBRE_AREA_EMPRESA,t.NOMBRE_TIPO_SALIDA_ALMACEN,s.COD_LOTE_PRODUCCION, "
                    + " p.NOMBRE_PILA,p.AP_PATERNO_PERSONAL,p.AP_MATERNO_PERSONAL,s.FECHA_SALIDA_ALMACEN,s.OBS_SALIDA_ALMACEN,cp.nombre_prod_semiterminado,prp.NOMBRE_PRODUCTO_PRESENTACION,s.COD_FORM_SALIDA,s.cod_salida_almacen "
                    + " from SALIDAS_ALMACEN s inner join AREAS_EMPRESA a on a.COD_AREA_EMPRESA = s.COD_AREA_EMPRESA "
                    + " inner join TIPOS_SALIDAS_ALMACEN t on t.COD_TIPO_SALIDA_ALMACEN = s.COD_TIPO_SALIDA_ALMACEN "
                    + " inner join personal p on p.COD_PERSONAL = s.COD_PERSONAL "
                    + " left outer join COMPONENTES_PROD cp on cp.COD_COMPPROD = s.COD_PROD "
                    + " left outer join PRESENTACIONES_PRODUCTO prp on prp.cod_presentacion = s.COD_PRESENTACION"
                    + " where s.COD_SALIDA_ALMACEN = '" + ingresosAlmacen.getDevoluciones().getSalidasAlmacen().getCodSalidaAlmacen() + "' ";
            LOGGER.info("consulta " + consulta);
            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(consulta);
            if (rs.next()) {
                salidasAlmacen = new SalidasAlmacen();
                salidasAlmacen.setNroSalidaAlmacen(rs.getInt("NRO_SALIDA_ALMACEN"));
                salidasAlmacen.getAreasEmpresa().setNombreAreaEmpresa(rs.getString("NOMBRE_AREA_EMPRESA"));
                salidasAlmacen.getTiposSalidasAlmacen().setNombreTipoSalidaAlmacen(rs.getString("NOMBRE_TIPO_SALIDA_ALMACEN"));
                salidasAlmacen.setCodLoteProduccion(rs.getString("COD_LOTE_PRODUCCION"));
                salidasAlmacen.getPersonal().setNombrePersonal(rs.getString("NOMBRE_PILA"));
                salidasAlmacen.getPersonal().setApPaternoPersonal(rs.getString("AP_PATERNO_PERSONAL"));
                salidasAlmacen.getPersonal().setApMaternoPersonal(rs.getString("AP_MATERNO_PERSONAL"));
                salidasAlmacen.setFechaSalidaAlmacen(rs.getDate("FECHA_SALIDA_ALMACEN"));
                salidasAlmacen.setObsSalidaAlmacen(rs.getString("OBS_SALIDA_ALMACEN"));
                salidasAlmacen.getComponentesProd().setNombreProdSemiterminado(rs.getString("nombre_prod_semiterminado"));
                salidasAlmacen.getPresentacionesProducto().setNombreProductoPresentacion(rs.getString("nombre_producto_presentacion"));
                salidasAlmacen.setCodFormSalida(rs.getInt("cod_form_salida"));
                salidasAlmacen.setCodSalidaAlmacen(rs.getInt("cod_salida_almacen"));
            }
            rs.close();
            st.close();
            con.close();
            devolucionesDetalleList = this.cargarEditarDetalleDevolucion(salidasAlmacen);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List cargarEditarDetalleDevolucion(SalidasAlmacen salidasAlmacen) {
        List devolucionesDetalleList = new ArrayList();
        try {
            Connection con = null;
            con = Util.openConnection(con);
            //llenar el detalle de salida almacen
            String consulta = " SELECT s.COD_SALIDA_ALMACEN,s.COD_MATERIAL, s.CANTIDAD_SALIDA_ALMACEN, s.COD_UNIDAD_MEDIDA, "
                    + "  s.COD_ESTADO_MATERIAL,m.NOMBRE_MATERIAL,u.NOMBRE_UNIDAD_MEDIDA,u.ABREVIATURA,e.NOMBRE_ESTADO_MATERIAL "
                    + "  FROM SALIDAS_ALMACEN_DETALLE s  "
                    + "  inner join MATERIALES m on m.COD_MATERIAL = s.COD_MATERIAL "
                    + "  inner join UNIDADES_MEDIDA u on u.COD_UNIDAD_MEDIDA = s.COD_UNIDAD_MEDIDA "
                    + "  inner join ESTADOS_MATERIAL e on e.COD_ESTADO_MATERIAL = s.COD_ESTADO_MATERIAL "
                    + "  where s.COD_SALIDA_ALMACEN = '" + salidasAlmacen.getCodSalidaAlmacen() + "' ";
            LOGGER.info("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            devolucionesDetalleList.clear();
            while (rs.next()) {
                DevolucionesDetalle devolucionesDetalle = new DevolucionesDetalle();
                devolucionesDetalle.getDevoluciones().getSalidasAlmacen().setCodSalidaAlmacen(rs.getInt("COD_SALIDA_ALMACEN"));
                devolucionesDetalle.getMateriales().setCodMaterial(rs.getString("COD_MATERIAL"));
                devolucionesDetalle.getMateriales().setNombreMaterial(rs.getString("NOMBRE_MATERIAL"));
                devolucionesDetalle.setCantidadEntregada(rs.getFloat("CANTIDAD_SALIDA_ALMACEN"));
                devolucionesDetalle.setCantidadDevuelta(0);
                devolucionesDetalle.setCantidadDevueltaFallados(0);
                devolucionesDetalle.getUnidadesMedida().setCodUnidadMedida(rs.getInt("COD_UNIDAD_MEDIDA"));
                devolucionesDetalle.getUnidadesMedida().setAbreviatura(rs.getString("ABREVIATURA"));
                devolucionesDetalle.setDevolucionesDetalleEtiquetasList(this.listaEditarDevolucionDetalleEtiquetas(devolucionesDetalle));
                devolucionesDetalleList.add(devolucionesDetalle);
            }
            rs.close();
            st.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return devolucionesDetalleList;
    }

    public List listaEditarDevolucionDetalleEtiquetas(DevolucionesDetalle devolucionesDetalle) {
        List devolucionesDetalleEtiquetasList = new ArrayList();
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select i.COD_INGRESO_ALMACEN,i.NRO_INGRESO_ALMACEN,s.ETIQUETA,s.CANTIDAD,s.COD_MATERIAL, s.FECHA_VENCIMIENTO,"
                    + " (select dde.CANTIDAD_DEVUELTA from DEVOLUCIONES d "
                    + " inner join DEVOLUCIONES_DETALLE dd on dd.COD_DEVOLUCION = d.COD_DEVOLUCION "
                    + " inner join DEVOLUCIONES_DETALLE_ETIQUETAS dde on dde.COD_DEVOLUCION = dd.COD_DEVOLUCION "
                    + " and dde.COD_MATERIAL = dd.COD_MATERIAL "
                    + " where d.COD_ESTADO_DEVOLUCION = 1 "
                    + " and d.COD_SALIDA_ALMACEN =s.COD_SALIDA_ALMACEN "
                    + " and dd.COD_MATERIAL = s.COD_MATERIAL "
                    + " and dde.COD_INGRESO_ALMACEN = i.COD_INGRESO_ALMACEN "
                    + " and dde.ETIQUETA = s.ETIQUETA and d.cod_devolucion = '" + ingresosAlmacen.getDevoluciones().getCodDevolucion() + "' ) CANTIDAD_DEVUELTA,(select dde.CANTIDAD_FALLADOS from DEVOLUCIONES d "
                    + " inner join DEVOLUCIONES_DETALLE dd on dd.COD_DEVOLUCION = d.COD_DEVOLUCION "
                    + " inner join DEVOLUCIONES_DETALLE_ETIQUETAS dde on dde.COD_DEVOLUCION = dd.COD_DEVOLUCION "
                    + " and dde.COD_MATERIAL = dd.COD_MATERIAL "
                    + " where d.COD_ESTADO_DEVOLUCION = 1 "
                    + " and d.COD_SALIDA_ALMACEN =s.COD_SALIDA_ALMACEN "
                    + " and dd.COD_MATERIAL = s.COD_MATERIAL "
                    + " and dde.COD_INGRESO_ALMACEN = i.COD_INGRESO_ALMACEN "
                    + " and dde.ETIQUETA = s.ETIQUETA and d.cod_devolucion = '" + ingresosAlmacen.getDevoluciones().getCodDevolucion() + "' ) CANTIDAD_FALLADOS "
                    + " from SALIDAS_ALMACEN_DETALLE_INGRESO s "
                    + " inner join INGRESOS_ALMACEN i on i.COD_INGRESO_ALMACEN = s.COD_INGRESO_ALMACEN"
                    + " where s.COD_SALIDA_ALMACEN = '" + salidasAlmacen.getCodSalidaAlmacen() + "' "
                    + " and s.COD_MATERIAL = '" + devolucionesDetalle.getMateriales().getCodMaterial() + "'";
            LOGGER.info("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            devolucionesDetalleEtiquetasList.clear();
            while (rs.next()) {
                DevolucionesDetalleEtiquetas devolucionesDetalleEtiquetas = new DevolucionesDetalleEtiquetas();
                devolucionesDetalleEtiquetas.getIngresosAlmacen().setNroIngresoAlmacen(rs.getInt("NRO_INGRESO_ALMACEN"));
                devolucionesDetalleEtiquetas.getIngresosAlmacen().setCodIngresoAlmacen(rs.getInt("COD_INGRESO_ALMACEN"));
                devolucionesDetalleEtiquetas.getMateriales().setCodMaterial(rs.getString("COD_MATERIAL"));
                devolucionesDetalleEtiquetas.setEtiqueta(rs.getInt("ETIQUETA"));
                devolucionesDetalleEtiquetas.setCantidadSalida(rs.getFloat("CANTIDAD"));
                devolucionesDetalleEtiquetas.setFechaVencimiento(rs.getDate("FECHA_VENCIMIENTO"));
                devolucionesDetalleEtiquetas.setCantidadesDevueltas(0); //rs.getFloat("CANTIDADES_DEVUELTAS")
                devolucionesDetalleEtiquetas.setCantidadDevuelta(rs.getFloat("CANTIDAD_DEVUELTA"));
                devolucionesDetalleEtiquetas.setCantidadFallados(rs.getFloat("CANTIDAD_FALLADOS"));
                devolucionesDetalleEtiquetas.setCantidadDevueltaB(rs.getFloat("CANTIDAD_DEVUELTA"));
                devolucionesDetalleEtiquetas.setCantidadFalladosB(rs.getFloat("CANTIDAD_FALLADOS"));
                devolucionesDetalle.setCantidadDevuelta(devolucionesDetalle.getCantidadDevuelta() + devolucionesDetalleEtiquetas.getCantidadDevuelta());
                devolucionesDetalle.setCantidadDevueltaFallados(devolucionesDetalle.getCantidadDevueltaFallados() + devolucionesDetalleEtiquetas.getCantidadFallados());
                devolucionesDetalleEtiquetasList.add(devolucionesDetalleEtiquetas);
            }
            rs.close();
            st.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return devolucionesDetalleEtiquetasList;
    }

    public void cargarDetalleDevolucion() {
    }

    public String guardarEditarDevolucion_action() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ManagedIngresoAlmacen managedIngresoAlmacen = new ManagedIngresoAlmacen();

            if (this.verificaTransaccionCerradaAlmacen() == true) {
                mensaje = "Las transacciones para este mes fueron cerradas ";
                return null;
            }
            if (devolucionesDetalleList.size() == 0) {
                mensaje = " No existe detalle de devolucion ";
                return null;
            }
            if (existeDetalleDevolucionDetalleAlmacen(devolucionesDetalleList) == false) {
                mensaje = " No existe detalle de Cada Item ";
                return null;
            }
            if (this.cantidadesBuenas() == 0 && this.cantidadesFalladas() == 0) {
                mensaje = " registre cantidades para su devolucion ";
                return null;
            }

            //devoluciones.setCodDevolucion(this.generaCodDevolucionAlmacen());
            //devoluciones.setEstadoSistema(1);
            //devoluciones.getEstadosDevoluciones().setCodEstadoDevolucion(1);
//            String consulta = " INSERT INTO DEVOLUCIONES ( COD_DEVOLUCION,  NRO_DEVOLUCION, COD_FORMULARIO_DEV, COD_ALMACEN, " +
//                    "  COD_SALIDA_ALMACEN,  COD_GESTION,  COD_ESTADO_DEVOLUCION,  ESTADO_SISTEMA,  OBS_DEVOLUCION,  COD_SALIDA_ALMACEN_AUX) " +
//                    " VALUES ( '"+devoluciones.getCodDevolucion()+"', (select ISNULL(max(nro_devolucion),0)+1 from devoluciones  " +
//                    " where cod_gestion='"+usuario.getGestionesGlobal().getCodGestion()+"' and cod_almacen='"+usuario.getAlmacenesGlobal().getCodAlmacen()+"'  and estado_sistema=1  ) ,'"+devoluciones.getCodFormularioDev()+"',   " +
//                    " '"+usuario.getAlmacenesGlobal().getCodAlmacen()+"' , '"+devoluciones.getSalidasAlmacen().getCodSalidaAlmacen()+"'  ,   '"+ usuario.getGestionesGlobal().getCodGestion()+"', " +
//                    "  '"+devoluciones.getEstadosDevoluciones().getCodEstadoDevolucion()+"','"+devoluciones.getEstadoSistema()+"', '"+devoluciones.getObsDevolucion()+"',   '"+devoluciones.getCodSalidaAlmacenAux()+"' ); ";
//            LOGGER.info("consulta " + consulta);
//            st.executeUpdate(consulta);
            String consulta = "";

            consulta = " delete from devoluciones_detalle where cod_devolucion = '" + devoluciones.getCodDevolucion() + "' ";
            LOGGER.info("consulta " + consulta);
            st.executeUpdate(consulta);
            consulta = " delete from devoluciones_detalle_etiquetas where cod_devolucion = '" + devoluciones.getCodDevolucion() + "' ";
            LOGGER.info("consulta" + consulta);
            st.executeUpdate(consulta);
            Iterator i = devolucionesDetalleList.iterator();
            while (i.hasNext()) {
                DevolucionesDetalle devolucionesDetalleItem = (DevolucionesDetalle) i.next();
                if (devolucionesDetalleItem.getCantidadDevuelta() > 0) {
                    consulta = " INSERT INTO DEVOLUCIONES_DETALLE(  COD_DEVOLUCION,  COD_MATERIAL,  CANTIDAD_DEVUELTA,  COD_UNIDAD_MEDIDA) "
                            + " VALUES ( '" + devoluciones.getCodDevolucion() + "', '" + devolucionesDetalleItem.getMateriales().getCodMaterial() + "',  '" + devolucionesDetalleItem.getCantidadDevuelta() + "', '" + devolucionesDetalleItem.getUnidadesMedida().getCodUnidadMedida() + "'); ";
                    LOGGER.info("consulta " + consulta);
                    st.executeUpdate(consulta);

                    Iterator i1 = devolucionesDetalleItem.getDevolucionesDetalleEtiquetasList().iterator();
                    while (i1.hasNext()) {
                        DevolucionesDetalleEtiquetas devolucionesDetalleEtiquetasItem = (DevolucionesDetalleEtiquetas) i1.next();
                        if (devolucionesDetalleEtiquetasItem.getCantidadDevuelta() > 0) {
                            consulta = " INSERT INTO DEVOLUCIONES_DETALLE_ETIQUETAS(  COD_DEVOLUCION,  COD_INGRESO_ALMACEN,  COD_MATERIAL,  ETIQUETA,"
                                    + "  CANTIDAD_DEVUELTA,  CANTIDAD_FALLADOS) VALUES ( '" + devoluciones.getCodDevolucion() + "', '" + devolucionesDetalleEtiquetasItem.getIngresosAlmacen().getCodIngresoAlmacen() + "' ,"
                                    + "  '" + devolucionesDetalleEtiquetasItem.getMateriales().getCodMaterial() + "',  '" + devolucionesDetalleEtiquetasItem.getEtiqueta() + "',"
                                    + "  '" + devolucionesDetalleEtiquetasItem.getCantidadDevuelta() + "',  '" + devolucionesDetalleEtiquetasItem.getCantidadFallados() + "'); ";
                            LOGGER.info("consulta " + consulta);
                            st.executeUpdate(consulta);
                            consulta = " update ingresos_almacen_detalle_estado set cantidad_restante=cantidad_restante-" + devolucionesDetalleEtiquetasItem.getCantidadDevueltaB() + "+" + devolucionesDetalleEtiquetasItem.getCantidadDevuelta() + " "
                                    + " where cod_ingreso_almacen='" + devolucionesDetalleEtiquetasItem.getIngresosAlmacen().getCodIngresoAlmacen() + "' "
                                    + " and cod_material='" + devolucionesDetalleEtiquetasItem.getMateriales().getCodMaterial() + "' "
                                    + " and etiqueta='" + devolucionesDetalleEtiquetasItem.getEtiqueta() + "'";
                            LOGGER.info("consulta " + consulta);
                            st.executeUpdate(consulta);
                        }
                    }
                }
            }

            /* cod_ingreso_almacen,
                    cod_personal,
            cod_tipo_ingreso_almacen,
            cod_gestion,
            cod_estado_ingreso_almacen,
            cod_devolucion,
            fecha_ingreso_almacen,
            nro_ingreso_almacen,
            estado_sistema,
            cod_almacen,
            obs_ingreso_almacen
             */
            //consulta = " delete from ingresos_almacen where cod_devolucion = '"+devoluciones.getCodDevolucion()+"' ";
            //LOGGER.info("consulta " + consulta);
            //st.executeUpdate(consulta);
            IngresosAlmacen ingresosAlmacen = new IngresosAlmacen();
            //verificar si el ingreso sera de frv o por devolucion normal
            //debe guardar el la devolucion, el ingreso a almacen y el ingreso a frv si existe el almacen
            if (this.cantidadesBuenas() > 0) {
                consulta = " update ingresos_almacen set cod_estado_ingreso_almacen = 1 where cod_ingreso_almacen in (select cod_ingreso_almacen from ingresos_almacen where cod_devolucion = '" + devoluciones.getCodDevolucion() + "') and cod_tipo_ingreso_almacen = 6 ";
                LOGGER.info("consulta " + consulta);
                st.executeUpdate(consulta);
                ingresosAlmacen = this.recuperaIngresoAlmacen("6", con);

                if (ingresosAlmacen.getCodIngresoAlmacen() == 0) {
                    ingresosAlmacen.setCodIngresoAlmacen(managedIngresoAlmacen.generaCodIngresoAlmacen());
                    ingresosAlmacen.getTiposIngresoAlmacen().setCodTipoIngresoAlmacen(6);//por devolucion
                    ingresosAlmacen.getGestiones().setCodGestion(usuario.getGestionesGlobal().getCodGestion());
                    ingresosAlmacen.getEstadosIngresoAlmacen().setCodEstadoIngresoAlmacen(1);
                    ingresosAlmacen.setNroIngresoAlmacen(this.obtieneNroIngresoAlmacen(usuario, con));
                    ingresosAlmacen.setEstadoSistema(1);
                    ingresosAlmacen.getAlmacenes().setCodAlmacen(usuario.getAlmacenesGlobal().getCodAlmacen());
                    ingresosAlmacen.setObsIngresoAlmacen(devoluciones.getObsDevolucion());
                    ingresosAlmacen.getPersonal().setCodPersonal(usuario.getUsuarioModuloBean().getCodUsuarioGlobal());
                    ingresosAlmacen.setDevoluciones(devoluciones);
                    ingresosAlmacen.setFechaIngresoAlmacen(new Date());
                    consulta = " INSERT INTO INGRESOS_ALMACEN(  COD_INGRESO_ALMACEN,  COD_TIPO_INGRESO_ALMACEN,  COD_GESTION,"
                            + "  COD_ESTADO_INGRESO_ALMACEN,  COD_DEVOLUCION,  FECHA_INGRESO_ALMACEN,  OBS_INGRESO_ALMACEN,  NRO_INGRESO_ALMACEN,  ESTADO_SISTEMA,"
                            + "  COD_ALMACEN,  COD_PERSONAL ) "
                            + "  VALUES (  '" + ingresosAlmacen.getCodIngresoAlmacen() + "',  '" + ingresosAlmacen.getTiposIngresoAlmacen().getCodTipoIngresoAlmacen() + "', "
                            + "  '" + ingresosAlmacen.getGestiones().getCodGestion() + "',  '" + ingresosAlmacen.getEstadosIngresoAlmacen().getCodEstadoIngresoAlmacen() + "', "
                            + "  '" + ingresosAlmacen.getDevoluciones().getCodDevolucion() + "', '" + sdf.format(ingresosAlmacen.getFechaIngresoAlmacen()) + "',"
                            + "  '" + ingresosAlmacen.getObsIngresoAlmacen() + "',  '" + ingresosAlmacen.getNroIngresoAlmacen() + "', "
                            + "  '" + ingresosAlmacen.getEstadoSistema() + "','" + ingresosAlmacen.getAlmacenes().getCodAlmacen() + "','" + ingresosAlmacen.getPersonal().getCodPersonal() + "' ); ";
                    LOGGER.info("consulta " + consulta);
                    st.executeUpdate(consulta);
                }
                consulta = "delete from ingresos_almacen_detalle where cod_ingreso_almacen = '" + ingresosAlmacen.getCodIngresoAlmacen() + "' ";
                LOGGER.info(" consulta " + consulta);
                st.executeUpdate(consulta);
                i = devolucionesDetalleList.iterator();
                while (i.hasNext()) {
                    DevolucionesDetalle devolucionesDetalleItem = (DevolucionesDetalle) i.next();
                    if (devolucionesDetalleItem.getCantidadDevuelta() > 0) {
                        IngresosAlmacenDetalle ingresosAlmacenDetalleItem = new IngresosAlmacenDetalle();
                        ingresosAlmacenDetalleItem.setMateriales(devolucionesDetalleItem.getMateriales());
                        ingresosAlmacenDetalleItem.setCantTotalIngreso(devolucionesDetalleItem.getCantidadDevuelta());
                        ingresosAlmacenDetalleItem.setCantTotalIngresoFisico(devolucionesDetalleItem.getCantidadDevuelta());
                        ingresosAlmacenDetalleItem.setUnidadesMedida(devolucionesDetalleItem.getUnidadesMedida());

                        consulta = " insert into ingresos_almacen_detalle (cod_ingreso_almacen, cod_material,nro_unidades_empaque,cod_seccion,cant_total_ingreso,"
                                + " cant_total_ingreso_fisico,cod_unidad_medida,costo_promedio,costo_unitario,precio_neto,precio_unitario_material)"
                                + " values ('" + ingresosAlmacen.getCodIngresoAlmacen() + "','" + ingresosAlmacenDetalleItem.getMateriales().getCodMaterial() + "',"
                                + " 1,'" + managedIngresoAlmacen.buscaSeccionAlmacen(ingresosAlmacenDetalleItem, usuario).getCodSeccion() + "','" + ingresosAlmacenDetalleItem.getCantTotalIngreso() + "',"
                                + "'" + ingresosAlmacenDetalleItem.getCantTotalIngresoFisico() + "','" + ingresosAlmacenDetalleItem.getUnidadesMedida().getCodUnidadMedida() + "',"
                                + "'" + ingresosAlmacenDetalleItem.getCostoPromedio() + "','" + ingresosAlmacenDetalleItem.getCostoUnitario() + "','" + ingresosAlmacenDetalleItem.getPrecioNeto() + "',"
                                + "'" + ingresosAlmacenDetalleItem.getPrecioUnitarioMaterial() + "' ) ";
                        LOGGER.info("consulta " + consulta);
                        st.executeUpdate(consulta);
                    }
                }
            } else {
                consulta = " update ingresos_almacen set cod_estado_ingreso_almacen = 2 where cod_ingreso_almacen in (select cod_ingreso_almacen from ingresos_almacen where cod_devolucion = '" + devoluciones.getCodDevolucion() + "') and cod_tipo_ingreso_almacen = 6 ";
                LOGGER.info("consulta " + consulta);
                st.executeUpdate(consulta);
            }

            if (this.cantidadesFalladas() > 0) {
                if (this.obtieneAlmacenFrv(usuario, con) == 0) {
                    mensaje = "seleccionar_almacenFrv";
                    return null;
                } else {
                    consulta = " update ingresos_almacen set cod_estado_ingreso_almacen = 1 where cod_ingreso_almacen in (select cod_ingreso_almacen from ingresos_almacen where cod_devolucion = '" + devoluciones.getCodDevolucion() + "') and cod_tipo_ingreso_almacen = 7 ";
                    LOGGER.info("consulta " + consulta);
                    st.executeUpdate(consulta);

                    ingresosAlmacen = this.recuperaIngresoAlmacen("7", con);
                    if (ingresosAlmacen.getCodIngresoAlmacen() == 0) {
                        ingresosAlmacen.setCodIngresoAlmacen(managedIngresoAlmacen.generaCodIngresoAlmacen());
                        ingresosAlmacen.getTiposIngresoAlmacen().setCodTipoIngresoAlmacen(6);//por devolucion
                        ingresosAlmacen.getGestiones().setCodGestion(usuario.getGestionesGlobal().getCodGestion());
                        ingresosAlmacen.getEstadosIngresoAlmacen().setCodEstadoIngresoAlmacen(1);
                        ingresosAlmacen.setNroIngresoAlmacen(this.obtieneNroIngresoAlmacen(usuario, con));
                        ingresosAlmacen.setEstadoSistema(1);
                        ingresosAlmacen.getAlmacenes().setCodAlmacen(usuario.getAlmacenesGlobal().getCodAlmacen());
                        ingresosAlmacen.setObsIngresoAlmacen(devoluciones.getObsDevolucion());
                        ingresosAlmacen.getPersonal().setCodPersonal(usuario.getUsuarioModuloBean().getCodUsuarioGlobal());
                        ingresosAlmacen.setDevoluciones(devoluciones);
                        ingresosAlmacen.setFechaIngresoAlmacen(new Date());
                        consulta = " INSERT INTO INGRESOS_ALMACEN(  COD_INGRESO_ALMACEN,  COD_TIPO_INGRESO_ALMACEN,  COD_GESTION,"
                                + "  COD_ESTADO_INGRESO_ALMACEN,  COD_DEVOLUCION,  FECHA_INGRESO_ALMACEN,  OBS_INGRESO_ALMACEN,  NRO_INGRESO_ALMACEN,  ESTADO_SISTEMA,"
                                + "  COD_ALMACEN,  COD_PERSONAL ) "
                                + "  VALUES (  '" + ingresosAlmacen.getCodIngresoAlmacen() + "',  '" + ingresosAlmacen.getTiposIngresoAlmacen().getCodTipoIngresoAlmacen() + "', "
                                + "  '" + ingresosAlmacen.getGestiones().getCodGestion() + "',  '" + ingresosAlmacen.getEstadosIngresoAlmacen().getCodEstadoIngresoAlmacen() + "', "
                                + "  '" + ingresosAlmacen.getDevoluciones().getCodDevolucion() + "', '" + sdf.format(ingresosAlmacen.getFechaIngresoAlmacen()) + "',"
                                + "  '" + ingresosAlmacen.getObsIngresoAlmacen() + "',  '" + ingresosAlmacen.getNroIngresoAlmacen() + "', "
                                + "  '" + ingresosAlmacen.getEstadoSistema() + "','" + ingresosAlmacen.getAlmacenes().getCodAlmacen() + "','" + ingresosAlmacen.getPersonal().getCodPersonal() + "' ); ";
                        LOGGER.info("consulta " + consulta);
                        st.executeUpdate(consulta);
                    }

                    consulta = "delete from ingresos_almacen_detalle where cod_ingreso_almacen = '" + ingresosAlmacen.getCodIngresoAlmacen() + "' ";
                    LOGGER.info(" consulta " + consulta);
                    st.executeUpdate(consulta);
                    ManagedAccesoSistema usuarioFrv = new ManagedAccesoSistema();
                    usuarioFrv.getAlmacenesGlobal().setCodAlmacen(this.obtieneAlmacenFrv(usuario, con));
                    i = devolucionesDetalleList.iterator();
                    while (i.hasNext()) {
                        DevolucionesDetalle devolucionesDetalleItem = (DevolucionesDetalle) i.next();

                        if (devolucionesDetalleItem.getCantidadDevueltaFallados() > 0) {
                            IngresosAlmacenDetalle ingresosAlmacenDetalleItem = new IngresosAlmacenDetalle();
                            ingresosAlmacenDetalleItem.setIngresosAlmacen(ingresosAlmacen);
                            ingresosAlmacenDetalleItem.setMateriales(devolucionesDetalleItem.getMateriales());
                            ingresosAlmacenDetalleItem.setCantTotalIngreso(devolucionesDetalleItem.getCantidadDevueltaFallados());
                            ingresosAlmacenDetalleItem.setCantTotalIngresoFisico(devolucionesDetalleItem.getCantidadDevueltaFallados());
                            ingresosAlmacenDetalleItem.setUnidadesMedida(devolucionesDetalleItem.getUnidadesMedida());

                            consulta = " insert into ingresos_almacen_detalle (cod_ingreso_almacen, cod_material,nro_unidades_empaque,cod_seccion,cant_total_ingreso,"
                                    + " cant_total_ingreso_fisico,cod_unidad_medida,costo_promedio,costo_unitario,precio_neto,precio_unitario_material)"
                                    + " values ('" + ingresosAlmacen.getCodIngresoAlmacen() + "','" + ingresosAlmacenDetalleItem.getMateriales().getCodMaterial() + "',"
                                    + " 1,'" + managedIngresoAlmacen.buscaSeccionAlmacen(ingresosAlmacenDetalleItem, usuarioFrv).getCodSeccion() + "','" + ingresosAlmacenDetalleItem.getCantTotalIngreso() + "',"
                                    + "'" + ingresosAlmacenDetalleItem.getCantTotalIngresoFisico() + "','" + ingresosAlmacenDetalleItem.getUnidadesMedida().getCodUnidadMedida() + "',"
                                    + "'" + ingresosAlmacenDetalleItem.getCostoPromedio() + "','" + ingresosAlmacenDetalleItem.getCostoUnitario() + "','" + ingresosAlmacenDetalleItem.getPrecioNeto() + "',"
                                    + "'" + ingresosAlmacenDetalleItem.getPrecioUnitarioMaterial() + "' ) ";
                            LOGGER.info("consulta " + consulta);
                            st.executeUpdate(consulta);

                            consulta = "delete from ingresos_almacen_detalle_estado where cod_ingreso_almacen = '" + ingresosAlmacen.getCodIngresoAlmacen() + "' ";
                            LOGGER.info(" consulta " + consulta);
                            st.executeUpdate(consulta);
                            IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstadoItem = new IngresosAlmacenDetalleEstado();
                            ingresosAlmacenDetalleEstadoItem.setEtiqueta(1);
                            ingresosAlmacenDetalleEstadoItem.setIngresosAlmacenDetalle(ingresosAlmacenDetalleItem);
                            ingresosAlmacenDetalleEstadoItem.setMateriales(ingresosAlmacenDetalleItem.getMateriales());
                            ingresosAlmacenDetalleEstadoItem.getEstadosMaterial().setCodEstadoMaterial(5); //reanalisis
                            ingresosAlmacenDetalleEstadoItem.getEmpaqueSecundarioExterno().setCodEmpaqueSecundarioExterno(1);//empaque
                            ingresosAlmacenDetalleEstadoItem.setCantidadParcial(devolucionesDetalleItem.getCantidadDevueltaFallados());
                            ingresosAlmacenDetalleEstadoItem.setCantidadRestante(devolucionesDetalleItem.getCantidadDevueltaFallados());
                            ingresosAlmacenDetalleEstadoItem.setFechaVencimiento(this.buscaFechaVencimiento(devolucionesDetalleItem));

                            consulta = " insert into ingresos_almacen_detalle_estado(etiqueta,cod_ingreso_almacen,cod_material,cod_estado_material,cod_empaque_secundario_externo,cantidad_parcial,cantidad_restante,fecha_vencimiento) "
                                    + " values('" + ingresosAlmacenDetalleEstadoItem.getEtiqueta() + "','" + ingresosAlmacen.getCodIngresoAlmacen() + "',"
                                    + "'" + ingresosAlmacenDetalleEstadoItem.getMateriales().getCodMaterial() + "','" + ingresosAlmacenDetalleEstadoItem.getEstadosMaterial().getCodEstadoMaterial() + "',"
                                    + " '" + ingresosAlmacenDetalleEstadoItem.getEmpaqueSecundarioExterno().getCodEmpaqueSecundarioExterno() + "','" + ingresosAlmacenDetalleEstadoItem.getCantidadParcial() + "',"
                                    + " '" + ingresosAlmacenDetalleEstadoItem.getCantidadRestante() + "','" + sdf.format(ingresosAlmacenDetalleEstadoItem.getFechaVencimiento()) + "') ";

                            LOGGER.info("consulta " + consulta);
                            st.executeUpdate(consulta);
                        }
                    }
                }
            } else {
                consulta = " update ingresos_almacen set cod_estado_ingreso_almacen = 2 where cod_ingreso_almacen in (select cod_ingreso_almacen from ingresos_almacen where cod_devolucion = '" + devoluciones.getCodDevolucion() + "') and cod_tipo_ingreso_almacen = 7 ";
                LOGGER.info("consulta " + consulta);
                st.executeUpdate(consulta);
            }

            st.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public IngresosAlmacen recuperaIngresoAlmacen(String codTipoIngresoAlmacen, Connection con) {
        IngresosAlmacen ingresosAlmacen = new IngresosAlmacen();
        try {
            con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement();
            String consulta = " select i.COD_INGRESO_ALMACEN from INGRESOS_ALMACEN i where i.COD_DEVOLUCION = '" + devoluciones.getCodDevolucion() + "' and i.COD_TIPO_INGRESO_ALMACEN = '" + codTipoIngresoAlmacen + "' and i.COD_ESTADO_INGRESO_ALMACEN =1 ";
            ResultSet rs = st.executeQuery(consulta);

            if (rs.next()) {
                ingresosAlmacen.setCodIngresoAlmacen(rs.getInt("COD_INGRESO_ALMACEN"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ingresosAlmacen;
    }

    /**
     * @return the solicitudesList
     */
    public List<SolicitudDevoluciones> getSolicitudesList() {
        return solicitudesList;
    }

    /**
     * @param solicitudesList the solicitudesList to set
     */
    public void setSolicitudesList(List<SolicitudDevoluciones> solicitudesList) {
        this.solicitudesList = solicitudesList;
    }

    /**
     * @return the observacion
     */
    public String getObservacion() {
        return observacion;
    }

    /**
     * @param observacion the observacion to set
     */
    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    /**
     * @return the codSolicitud
     */
    public int getCodSolicitud() {
        return codSolicitud;
    }

    /**
     * @param codSolicitud the codSolicitud to set
     */
    public void setCodSolicitud(int codSolicitud) {
        this.codSolicitud = codSolicitud;
    }

    /**
     * @return the solicitudesAprobarList
     */
    public List<SolicitudDevoluciones> getSolicitudesAprobarList() {
        return solicitudesAprobarList;
    }

    /**
     * @param solicitudesAprobarList the solicitudesAprobarList to set
     */
    public void setSolicitudesAprobarList(List<SolicitudDevoluciones> solicitudesAprobarList) {
        this.solicitudesAprobarList = solicitudesAprobarList;
    }

    public int generaCodIngresoAlmacen(Connection con) {
        int codIngresoAlmacen = 0;
        try {
            //con = null;
            //con = Util.openConnection(con);
            String consulta = "  select (isnull(max(cod_ingreso_almacen),0)+1) cod_ingreso_almacen  "
                    + " from ingresos_almacen  ";
            LOGGER.info("consulta " + consulta);
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

    public int obtieneNroIngresoAlmacen(ManagedAccesoSistema usuario, Connection con) {
        int nroIngresoAlmacen = 0;
        try {
            //con = null;
            //con = Util.openConnection(con);

            String consulta = " select (isnull(max(nro_ingreso_almacen),0)+1) nro_ingreso_almacen  from ingresos_almacen where cod_gestion='" + usuario.getGestionesGlobal().getCodGestion() + "' "
                    + " and estado_sistema=1 and cod_almacen='" + usuario.getAlmacenesGlobal().getCodAlmacen() + "'  ";
            LOGGER.info("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            if (rs.next()) {
                nroIngresoAlmacen = rs.getInt("nro_ingreso_almacen");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nroIngresoAlmacen;
    }

    public Secciones buscaSeccionAlmacen(IngresosAlmacenDetalle ingresosAlmacenDetalle, ManagedAccesoSistema usuario, Connection con) {
        Secciones secciones = new Secciones();
        try {
            //Connection con = null;
            //con = Util.openConnection(con);
            String consulta = " select distinct(s.cod_seccion) cod_seccion,s.nombre_seccion "
                    + " from secciones s,secciones_detalle sd, almacenes a "
                    + " where s.cod_seccion=sd.cod_seccion and s.cod_almacen='" + usuario.getAlmacenesGlobal().getCodAlmacen() + "' "
                    + " and s.cod_estado_registro=1 and a.cod_almacen=s.cod_almacen  "
                    + " and ((sd.cod_material='" + ingresosAlmacenDetalle.getMateriales().getCodMaterial() + "' and s.estado_sistema=1) "
                    + " or (sd.cod_material=0 and sd.cod_grupo='" + ingresosAlmacenDetalle.getMateriales().getGrupos().getCodGrupo() + "') "
                    + " or (sd.cod_material=0 and sd.cod_grupo=0 and sd.cod_capitulo='" + ingresosAlmacenDetalle.getMateriales().getGrupos().getCapitulos().getCodCapitulo() + "')) ";
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            LOGGER.info("consulta " + consulta);
            if (rs.next()) {
                secciones.setCodSeccion(rs.getInt("cod_seccion"));
                secciones.setNombreSeccion(rs.getString("nombre_seccion"));
            }
            rs.close();
            st.close();
            //con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return secciones;
    }

    public String aprobarSolicitud_action() throws SQLException {
        mensaje = "";
        if (generaTraspaso) {
            boolean existSelected = false;
            for (Object solicitud : solicitudesDetalleList) {
                DevolucionesDetalle detalle = (DevolucionesDetalle) solicitud;
                if (detalle.isChecked() && detalle.getCantidadDevuelta() > 0&&!isMaterialEsteril(detalle.getMateriales().getCodMaterial())) {
                    existSelected = true;
                }
            }
            if (cod_almacen_destino == 0) {
                mensaje = "Seleccione un almacén de destino para la salida por traspaso.";
                return null;
            }
            if (cod_area_destino == 0) {
                mensaje = "Seleccione un área de destino para generar la salida por traspaso.";
                return null;
            }
            if (!existSelected) {
                mensaje = "Seleccione al menos un material con cantidad devuelta en buenos mayor a cero y que no corresponda a la configuración de materiales estériles para generar la salida por traspaso.";
                return null;
            }
        }

        int cod_ingreso_buenos = 0;
        int codIngresoNoEsteriles = 0;
        int codSalidaEsteriles = 0;

        Connection con = null;
        try {
            con = Util.openConnection(con);
            con.setAutoCommit(false);
            PreparedStatement pst;
            SolicitudDevoluciones registrar = solicitudAprobar;
            StringBuilder consulta = new StringBuilder("EXEC USP_APROBAR_SOLICITUD_DEVOLUCION_ALMACEN ")
                                            .append(solicitudAprobar.getCodSolicitudDevolucion()).append(",")
                                            .append(usuario.getUsuarioModuloBean().getCodUsuarioGlobal()).append(",")
                                            .append("?");//cod ingreso generado
            LOGGER.info("consulta aprobar solicitud devolucion "+consulta.toString());
            CallableStatement callAprobarSolicitud = con.prepareCall(consulta.toString());
            callAprobarSolicitud.registerOutParameter(1,java.sql.Types.INTEGER);//cod ingreso
            callAprobarSolicitud.execute();
            cod_ingreso_buenos=callAprobarSolicitud.getInt(1);
            //REGISTRO DE LOGS DEL NUEVO INGRESO
            consulta = new StringBuilder(" exec [PAA_REGISTRO_INGRESO_ALMACEN_LOG] ").append(cod_ingreso_buenos)  
                    .append(" , ").append(usuario.getUsuarioModuloBean().getCodUsuarioGlobal())
                    .append(" , 3");                    
            System.out.println("consulta ejecutar: " + consulta); 
            pst=con.prepareStatement(consulta.toString());
            pst.executeUpdate();    
            
            String query_esteriles = "exec USP_INSERT_OUT_ESTERIL_IN_NOESTERIL " + cod_ingreso_buenos+
                                        " ,?"+
                                        " ,?";
            LOGGER.info("query esteriles: " + query_esteriles);
            CallableStatement callInsert = con.prepareCall(query_esteriles);
            callInsert.registerOutParameter(1,java.sql.Types.INTEGER);
            callInsert.registerOutParameter(2,java.sql.Types.INTEGER);
            callInsert.execute();
            codSalidaEsteriles = callInsert.getInt(1);
            codIngresoNoEsteriles = callInsert.getInt(2);
            con.commit();
            mensaje = "1";
            this.cargarListadoSolicitudesAprobar1(con);

            pst.close();

            if (generaTraspaso) {
                guardarSalidaAlmacenTraspaso_action(cod_ingreso_buenos);
            }
            LOGGER.info("Proceso guardar traspaso.");
        } catch (SQLException ex) {
            mensaje = "Ocurrio un error al aprobar la solicitud intente de nuevo";
            LOGGER.warn(ex);
            con.rollback();
        } catch (Exception ex) {
            mensaje = "Ocurrio un error al aprobar la solicitud intente de nuevo";
            LOGGER.warn(ex);
            con.rollback();
        } finally {
            con.close();
        }
        
        ManagedAccesoSistema managed = (ManagedAccesoSistema)Util.getSessionBean("ManagedAccesoSistema");
        if (managed.getAlmacenesGlobal().getCodAlmacen() != 20
            && managed.getAlmacenesGlobal().getCodAlmacen() != 21
            && managed.getAlmacenesGlobal().getCodAlmacen() != 22) {
            ThreadCosteoByCodIngreso costeo = new ThreadCosteoByCodIngreso(cod_ingreso_buenos,codSalidaEsteriles,codIngresoNoEsteriles);
            costeo.start();
        }
        //fin actualización de costos
        return null;
    }

    public boolean verificarDevolucionTodasSalidasEpMp1(String codLote, String codCompProd, Connection con1) {
        boolean devolucionTotal = true;
        try {
            //Connection con1=null;
            //con1=Util.openConnection(con1);
            Statement st = con1.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String consulta = " select sa.COD_SALIDA_ALMACEN,sum(sad.CANTIDAD_SALIDA_ALMACEN) as cantSa,sad.COD_MATERIAL,"
                    + " (select sum(dde.CANTIDAD_DEVUELTA) from DEVOLUCIONES d inner join DEVOLUCIONES_DETALLE_ETIQUETAS dde"
                    + " on d.COD_DEVOLUCION=dde.COD_DEVOLUCION where dde.COD_MATERIAL=sad.COD_MATERIAL"
                    + " and d.COD_SALIDA_ALMACEN=sa.COD_SALIDA_ALMACEN and d.COD_ESTADO_DEVOLUCION not in (2)) as cantidadDevolucion"
                    + " from SALIDAS_ALMACEN sa inner join SALIDAS_ALMACEN_DETALLE sad "
                    + " on sa.COD_SALIDA_ALMACEN=sad.COD_SALIDA_ALMACEN"
                    + " where sa.COD_PROD = '" + codCompProd + "' and "
                    + " sa.COD_LOTE_PRODUCCION = '" + codLote + "' and"
                    + " sa.COD_ESTADO_SALIDA_ALMACEN = 1 and"
                    + " sa.ESTADO_SISTEMA = 1 and"
                    + " sa.cod_almacen in (1, 2) "
                    + /*" and sad.COD_MATERIAL in( "+
                                  " select fd.COD_MATERIAL from FORMULA_MAESTRA_DETALLE_EP fd inner join FORMULA_MAESTRA fm on"+
                                  " fd.COD_FORMULA_MAESTRA=fm.COD_FORMULA_MAESTRA where fm.COD_COMPPROD=sa.COD_PROD"+
                                 "  )"+*/ " and sad.COD_MATERIAL in("
                    + " select fmd.COD_MATERIAL from FORMULA_MAESTRA_DETALLE_MP fmd inner join FORMULA_MAESTRA f on "
                    + " f.COD_FORMULA_MAESTRA=fmd.COD_FORMULA_MAESTRA where f.COD_COMPPROD=sa.COD_PROD"
                    + " )"
                    + " group by sa.COD_SALIDA_ALMACEN,sad.COD_MATERIAL"
                    + " order by sad.COD_MATERIAL";
            LOGGER.info("consulta verificar cantidad cambiar lote " + consulta);
            ResultSet res = st.executeQuery(consulta);
            while (res.next()) {
                LOGGER.info("cod sal " + res.getString("COD_SALIDA_ALMACEN") + " cod mat " + res.getString("COD_MATERIAL") + " can s " + res.getDouble("cantSa") + " cant d " + res.getDouble("cantidadDevolucion"));
                if (res.getDouble("cantSa") > (res.getDouble("cantidadDevolucion") + 0.1))//adicion de tolerancia
                {
                    devolucionTotal = false;
                }
            }
            st.close();
            //con1.close();
        } catch (SQLException ex) {
            LOGGER.warn(ex.getMessage());
        }
        return devolucionTotal;
    }

    public void cargarListadoSolicitudesAprobar1(Connection con) {
        try {
            //Connection con = null;
            //con = Util.openConnection(con);
            SimpleDateFormat sdt = new SimpleDateFormat("yyyy/MM/dd");
            String consulta = "select * from (select ROW_NUMBER() OVER (order by sd.COD_SOLICITUD_DEVOLUCION desc) as 'FILAS',"
                    + "s.COD_LOTE_PRODUCCION,s.NRO_SALIDA_ALMACEN,cp.nombre_prod_semiterminado,a.NOMBRE_ALMACEN,"
                    + "(p.AP_PATERNO_PERSONAL+' '+p.AP_MATERNO_PERSONAL+' '+p.NOMBRE_PILA) as nombrePer,"
                    + "sd.COD_SOLICITUD_DEVOLUCION,esd.NOMBRE_ESTADO,sd.COD_ESTADO_SOLICITUD_DEVOLUCION,sd.COD_PERSONAL,sd.COD_SALIDA_ALMACEN,sd.FECHA_SOLICITUD,sd.OBSERVACION,s.COD_PROD from "
                    + "SOLICITUD_DEVOLUCIONES sd inner join PERSONAL p on sd.COD_PERSONAL=p.COD_PERSONAL "
                    + "inner join SALIDAS_ALMACEN s on s.COD_SALIDA_ALMACEN=sd.COD_SALIDA_ALMACEN "
                    + "left outer join COMPONENTES_PROD cp on cp.COD_COMPPROD=s.COD_PROD "
                    + "inner join ALMACENES a on a.COD_ALMACEN=s.COD_ALMACEN "
                    + "inner join ESTADOS_SOLICITUD_DEVOLUCION esd on sd.COD_ESTADO_SOLICITUD_DEVOLUCION=esd.COD_ESTADO_SOLICITUD_DEVOLUCION"
                    + // " where sd.COD_ESTADO_SOLICITUD_DEVOLUCION not in(2)"+
                    " where a.COD_ALMACEN ='" + usuario.getAlmacenesGlobal().getCodAlmacen() + "'";
            if (!buscarSolicitudAprobar.getCodSolicitudDevolucion().equals("")) {
                consulta += " and sd.COD_SOLICITUD_DEVOLUCION ='" + buscarSolicitudAprobar.getCodSolicitudDevolucion() + "'";
            }
            if (!buscarSolicitudAprobar.getEstadoSolicitudDevolucion().getCodEstado().equals("0")) {
                consulta += " and sd.COD_ESTADO_SOLICITUD_DEVOLUCION='" + buscarSolicitudAprobar.getEstadoSolicitudDevolucion().getCodEstado() + "'";

            }
            if (fechaInicio1 != null && fechaFinal1 != null) {
                consulta += " and sd.FECHA_SOLICITUD BETWEEN '" + sdt.format(fechaInicio) + " 00:00:00' and '" + sdt.format(fechafinal) + " 23:59:59' ";
            }
            if (buscarSolicitudAprobar.getSalidaAlmacen().getNroSalidaAlmacen() > 0) {
                consulta += " and s.NRO_SALIDA_ALMACEN ='" + buscarSolicitudAprobar.getSalidaAlmacen().getNroSalidaAlmacen() + "'";
            }
            if (buscarSolicitudAprobar.getSalidaAlmacen().getAlmacenes().getCodAlmacen() > 0) {
                consulta += " and s.COD_ALMACEN= '" + buscarSolicitudAprobar.getSalidaAlmacen().getAlmacenes().getCodAlmacen() + "'";
            }
            if (!buscarSolicitudAprobar.getSalidaAlmacen().getProducto().getCodProducto().equals("0")) {
                consulta += " and s.COD_PROD='" + buscarSolicitudAprobar.getSalidaAlmacen().getProducto().getCodProducto() + "'";

            }
            if (!buscarSolicitudAprobar.getPersonalSolicitante().getCodPersonal().equals("0")) {
                consulta += " and sd.COD_PERSONAL='" + buscarSolicitudAprobar.getPersonalSolicitante().getCodPersonal() + "' ";
            }
            consulta += "  ) AS listado where FILAS BETWEEN " + begin + " AND " + end + "  ";
            LOGGER.info("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            getSolicitudesAprobarList().clear();
            while (rs.next()) {
                SolicitudDevoluciones nuevaSolicitud = new SolicitudDevoluciones();

                nuevaSolicitud.getSalidaAlmacen().setCodSalidaAlmacen(rs.getInt("COD_SALIDA_ALMACEN"));
                nuevaSolicitud.getSalidaAlmacen().setNroSalidaAlmacen(rs.getInt("NRO_SALIDA_ALMACEN"));
                nuevaSolicitud.getSalidaAlmacen().setCodLoteProduccion(rs.getString("COD_LOTE_PRODUCCION"));
                nuevaSolicitud.getSalidaAlmacen().getProducto().setNombreProducto(rs.getString("nombre_prod_semiterminado"));
                nuevaSolicitud.getSalidaAlmacen().getAlmacenes().setNombreAlmacen(rs.getString("NOMBRE_ALMACEN"));
                nuevaSolicitud.setCodSolicitudDevolucion(rs.getString("COD_SOLICITUD_DEVOLUCION"));
                nuevaSolicitud.getEstadoSolicitudDevolucion().setCodEstado(rs.getString("COD_ESTADO_SOLICITUD_DEVOLUCION"));
                nuevaSolicitud.getEstadoSolicitudDevolucion().setNombreEstado(rs.getString("NOMBRE_ESTADO"));
                nuevaSolicitud.setFechaSolicitud(rs.getTimestamp("FECHA_SOLICITUD"));
                nuevaSolicitud.setObservacion(rs.getString("OBSERVACION"));
                nuevaSolicitud.getPersonalSolicitante().setCodPersonal(rs.getString("COD_PERSONAL"));
                nuevaSolicitud.getPersonalSolicitante().setNombrePersonal(rs.getString("nombrePer"));
                nuevaSolicitud.getSalidaAlmacen().getProducto().setCodProducto(rs.getString("COD_PROD"));
                getSolicitudesAprobarList().add(nuevaSolicitud);
            }
            //rs.close();
            //st.close();
            //con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getCargarContenidoDevolucionAlmacenAprobar() {
        try {
            begin = 1;
            end = 10;

            getBuscarSolicitud().getEstadoSolicitudDevolucion().setCodEstado("0");
            this.cargarEstadosSolList();
            getBuscarSolicitud().getSalidaAlmacen().getProducto().setCodProducto("0");
            this.cargarProductos();
            getBuscarSolicitud().getSalidaAlmacen().getAlmacenes().setCodAlmacen(0);
            this.cargarAlmacenes();
            getBuscarSolicitud().setCodSolicitudDevolucion("");
            getBuscarSolicitud().getSalidaAlmacen().setNroSalidaAlmacen(0);
            getBuscarSolicitud().setFechaSolicitud(null);
            setFechaInicio(null);
            setFechafinal(null);
            buscarSolicitud.getEstadoSolicitudDevolucion().setCodEstado("4"); //por aprobar Ing Ind
            this.listadoDevolucionesAlmacenAprob();
            componentesProdList = this.listaComponentesProd();
            cantidadfilas = solicitudesList.size();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String rechazarSolicitudDevolucion_action() throws SQLException {
        mensaje = "";
        Connection con = null;
        try {
            Iterator i = solicitudesList.iterator();
            SolicitudDevoluciones s = new SolicitudDevoluciones();
            while (i.hasNext()) {
                s = (SolicitudDevoluciones) i.next();
                if (s.getChecked()) {
                    break;
                }
            }
            con = Util.openConnection(con);
            con.setAutoCommit(false);
            Statement st = con.createStatement();
            String consulta = " update solicitud_devoluciones set cod_estado_solicitud_devolucion = '7'"
                    + " where cod_solicitud_devolucion = '" + s.getCodSolicitudDevolucion() + "' ";
            LOGGER.info("consulta " + consulta);
            st.executeUpdate(consulta);
            con.commit();
            mensaje = "1";

        } catch (SQLException ex) {
            con.rollback();
            LOGGER.warn(ex.getMessage());
            mensaje = "Ocurrio un error al momento de rechazar la solicitud,intente de nuevo";
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (mensaje.equals("1")) {
            this.getCargarContenidoDevolucionAlmacenAprobar();
        }

        return null;
    }

    public String aprobarSolicitudDevolucion_action() {
        try {
            Iterator i = solicitudesList.iterator();
            SolicitudDevoluciones s = new SolicitudDevoluciones();
            while (i.hasNext()) {
                s = (SolicitudDevoluciones) i.next();
                if (s.getChecked()) {
                    break;
                }
            }
            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement();
            String consulta = " update solicitud_devoluciones set cod_estado_solicitud_devolucion = '5' where cod_solicitud_devolucion = '" + s.getCodSolicitudDevolucion() + "' ";
            LOGGER.info("consulta " + consulta);
            st.executeUpdate(consulta);
            this.getCargarContenidoDevolucionAlmacenAprobar();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String anularSolicitudDevolucion_action() {
        LOGGER.debug("entro anulacion");
        mensaje = "";
        try {
            
            
            if (solicitudDevolucionAnular.getEstadoSolicitudDevolucion().getCodEstado().equals("5")) {
                mensaje = " la solicitud de devolucion no se puede anular, ya se encuentra aprobada por Ing. Ind. ";
                return null;
            }
            String consulta = " update solicitud_devoluciones"
                            + " set cod_estado_solicitud_devolucion = '6'"
                            + " where cod_solicitud_devolucion='" + solicitudDevolucionAnular.getCodSolicitudDevolucion() + "' ";
            LOGGER.info("consulta anular solicitud devolucion" + consulta);
            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement();
            st.executeUpdate(consulta);
            mensaje = "1";
            con.close();

        } catch (Exception e) {
            mensaje="Ocurrio un error al momento de anular la transaccion";
            e.printStackTrace();
        }
        if(mensaje.equals("1"))
        {
            this.listadoDevolucionesAlmacen();
        }
        
        return null;
    }

    public void cargarMaquinarias() {
        try {
            Connection con1 = null;
            con1 = Util.openConnection(con1);
            String sql = " select m.COD_MAQUINA,m.NOMBRE_MAQUINA,m.CODIGO from MAQUINARIAS m "
                    + " where m.cod_estado_registro = 1 "
                    + " order by m.NOMBRE_MAQUINA";
            LOGGER.info(" sql:" + sql);
            ResultSet rs = null;
            Statement st = con1.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            maquinariaList.clear();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                maquinariaList.add(new SelectItem(rs.getInt("cod_maquina"), rs.getString("nombre_maquina") + " " + rs.getString("codigo")));
            }
            rs.close();
            st.close();
            con1.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return the buscarSolicitud
     */
    public SolicitudDevoluciones getBuscarSolicitud() {
        return buscarSolicitud;
    }

    /**
     * @param buscarSolicitud the buscarSolicitud to set
     */
    public void setBuscarSolicitud(SolicitudDevoluciones buscarSolicitud) {
        this.buscarSolicitud = buscarSolicitud;
    }

    /**
     * @return the estadosSolicitudesList
     */
    public List<SelectItem> getEstadosSolicitudesList() {
        return estadosSolicitudesList;
    }

    /**
     * @param estadosSolicitudesList the estadosSolicitudesList to set
     */
    public void setEstadosSolicitudesList(List<SelectItem> estadosSolicitudesList) {
        this.estadosSolicitudesList = estadosSolicitudesList;
    }

    /**
     * @return the productosList
     */
    public List<SelectItem> getProductosList() {
        return productosList;
    }

    /**
     * @param productosList the productosList to set
     */
    public void setProductosList(List<SelectItem> productosList) {
        this.productosList = productosList;
    }

    /**
     * @return the almacenesList
     */
    public List<SelectItem> getAlmacenesList() {
        return almacenesList;
    }

    /**
     * @param almacenesList the almacenesList to set
     */
    public void setAlmacenesList(List<SelectItem> almacenesList) {
        this.almacenesList = almacenesList;
    }

    /**
     * @return the fechaInicio
     */
    public java.util.Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @param fechaInicio the fechaInicio to set
     */
    public void setFechaInicio(java.util.Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @return the fechafinal
     */
    public java.util.Date getFechafinal() {
        return fechafinal;
    }

    /**
     * @param fechafinal the fechafinal to set
     */
    public void setFechafinal(java.util.Date fechafinal) {
        this.fechafinal = fechafinal;
    }

    /**
     * @return the buscarSolicitudAprobar
     */
    public SolicitudDevoluciones getBuscarSolicitudAprobar() {
        return buscarSolicitudAprobar;
    }

    /**
     * @param buscarSolicitudAprobar the buscarSolicitudAprobar to set
     */
    public void setBuscarSolicitudAprobar(SolicitudDevoluciones buscarSolicitudAprobar) {
        this.buscarSolicitudAprobar = buscarSolicitudAprobar;
    }

    /**
     * @return the fechaInicio1
     */
    public java.util.Date getFechaInicio1() {
        return fechaInicio1;
    }

    /**
     * @param fechaInicio1 the fechaInicio1 to set
     */
    public void setFechaInicio1(java.util.Date fechaInicio1) {
        this.fechaInicio1 = fechaInicio1;
    }

    /**
     * @return the fechaFinal1
     */
    public java.util.Date getFechaFinal1() {
        return fechaFinal1;
    }

    /**
     * @param fechaFinal1 the fechaFinal1 to set
     */
    public void setFechaFinal1(java.util.Date fechaFinal1) {
        this.fechaFinal1 = fechaFinal1;
    }

    /**
     * @return the personalList
     */
    public List<SelectItem> getPersonalList() {
        return personalList;
    }

    /**
     * @param personalList the personalList to set
     */
    public void setPersonalList(List<SelectItem> personalList) {
        this.personalList = personalList;
    }

    /**
     * @return the solicitudAprobar
     */
    public SolicitudDevoluciones getSolicitudAprobar() {
        return solicitudAprobar;
    }

    /**
     * @param solicitudAprobar the solicitudAprobar to set
     */
    public void setSolicitudAprobar(SolicitudDevoluciones solicitudAprobar) {
        this.solicitudAprobar = solicitudAprobar;
    }

    /**
     * @return the solicitudesDetalleList
     */
    public List getSolicitudesDetalleList() {
        return solicitudesDetalleList;
    }

    /**
     * @param solicitudesDetalleList the solicitudesDetalleList to set
     */
    public void setSolicitudesDetalleList(List solicitudesDetalleList) {
        this.solicitudesDetalleList = solicitudesDetalleList;
    }

    public ManagedAccesoSistema getUsuario() {
        return usuario;
    }

    public void setUsuario(ManagedAccesoSistema usuario) {
        this.usuario = usuario;
    }

    public List getMaquinariaList() {
        return maquinariaList;
    }

    public void setMaquinariaList(List maquinariaList) {
        this.maquinariaList = maquinariaList;
    }

    public SalidasAlmacenDetalleIngreso getSalidasAlmacenDetalleIngresoBuscar() {
        return salidasAlmacenDetalleIngresoBuscar;
    }

    public void setSalidasAlmacenDetalleIngresoBuscar(SalidasAlmacenDetalleIngreso salidasAlmacenDetalleIngresoBuscar) {
        this.salidasAlmacenDetalleIngresoBuscar = salidasAlmacenDetalleIngresoBuscar;
    }

    public Date getFechaFinalSalida() {
        return fechaFinalSalida;
    }

    public void setFechaFinalSalida(Date fechaFinalSalida) {
        this.fechaFinalSalida = fechaFinalSalida;
    }

    public Date getFechaInicioSalida() {
        return fechaInicioSalida;
    }

    public void setFechaInicioSalida(Date fechaInicioSalida) {
        this.fechaInicioSalida = fechaInicioSalida;
    }

    public HtmlDataTable getSalidasAlmacenLoteProveedorDataTable() {
        return salidasAlmacenLoteProveedorDataTable;
    }

    public void setSalidasAlmacenLoteProveedorDataTable(HtmlDataTable salidasAlmacenLoteProveedorDataTable) {
        this.salidasAlmacenLoteProveedorDataTable = salidasAlmacenLoteProveedorDataTable;
    }

    public HtmlDataTable getDevolucionesDetalleProveedorDataTabla() {
        return devolucionesDetalleProveedorDataTabla;
    }

    public void setDevolucionesDetalleProveedorDataTabla(HtmlDataTable devolucionesDetalleProveedorDataTabla) {
        this.devolucionesDetalleProveedorDataTabla = devolucionesDetalleProveedorDataTabla;
    }

    public Devoluciones getDevoluciones() {
        return devoluciones;
    }

    public void setDevoluciones(Devoluciones devoluciones) {
        this.devoluciones = devoluciones;
    }

    public boolean isAdministradorAlmacen() {
        return administradorAlmacen;
    }

    public void setAdministradorAlmacen(boolean administradorAlmacen) {
        this.administradorAlmacen = administradorAlmacen;
    }

    public String verReporteIngresoAlmacen_action() {
        try {
            mensaje = "";
            Iterator i = solicitudesList.iterator();

            while (i.hasNext()) {
                SolicitudDevoluciones s = (SolicitudDevoluciones) i.next();
                if (s.getChecked()) {
                    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                    Map<String, Object> sessionMap = externalContext.getSessionMap();
                    sessionMap.put("solicitudDevolucion", s);
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean generaTraspaso = false;

    public boolean isGeneraTraspaso() {
        return generaTraspaso;
    }

    public void setGeneraTraspaso(boolean generaTraspaso) {
        this.generaTraspaso = generaTraspaso;
    }

    public String traspasoChanged() {
        LOGGER.info("valor cahnged: " + generaTraspaso);
        if (!generaTraspaso) {
            for (Object solicitud : solicitudesDetalleList) {
                DevolucionesDetalle detalle = (DevolucionesDetalle) solicitud;
                detalle.setChecked(false);
            }
        }
        for (Object solicitud : solicitudesDetalleList) {
            DevolucionesDetalle detalle = (DevolucionesDetalle) solicitud;
            LOGGER.info("----- " + detalle.isChecked());
        }
        return null;
    }

    private int cod_almacen_destino;
    private int cod_area_destino;

    public int getCod_almacen_destino() {
        return cod_almacen_destino;
    }

    public void setCod_almacen_destino(int cod_almacen_destino) {
        this.cod_almacen_destino = cod_almacen_destino;
    }

    public int getCod_area_destino() {
        return cod_area_destino;
    }

    public void setCod_area_destino(int cod_area_destino) {
        this.cod_area_destino = cod_area_destino;
    }

    public List<SelectItem> getCargarAreasEmpresa() {
        List areasEmpresalist = new ArrayList();
        try {
            String sql = "  select a.COD_AREA_EMPRESA,a.NOMBRE_AREA_EMPRESA  "
                    + " from AREAS_EMPRESA a  where a.COD_ESTADO_REGISTRO = 1 order by a.nombre_area_empresa";
            Connection con = null;
            con = (Util.openConnection(con));
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            LOGGER.info("sql *j* : " + sql);
            ResultSet rs = st.executeQuery(sql);
            areasEmpresalist.clear();
            areasEmpresalist.add(new SelectItem("0", "-NINGUNO-"));
            while (rs.next()) {
                areasEmpresalist.add(new SelectItem(rs.getString("COD_AREA_EMPRESA"), rs.getString("NOMBRE_AREA_EMPRESA")));
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

    public List<SelectItem> getCargaAlmacenDestino() {
        List almacenesList = new ArrayList();
        try {

            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select a.COD_ALMACEN,a.NOMBRE_ALMACEN from ALMACENES a where a.COD_ESTADO_REGISTRO = 1 ";

            ResultSet rs = null;
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            almacenesList.clear();
            rs = st.executeQuery(consulta);
            almacenesList.add(new SelectItem("0", "-NINGUNO-"));
            while (rs.next()) {
                almacenesList.add(new SelectItem(rs.getString("COD_ALMACEN"), rs.getString("NOMBRE_ALMACEN")));
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
        return almacenesList;
    }

    public String guardarSalidaAlmacenTraspaso_action(int cod_ingreso_almacen) {
        try {
            Connection con = null;
            con = Util.openConnection(con);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            ManagedSalidaTraspasoAlmacen managed = new ManagedSalidaTraspasoAlmacen();
            Traspasos traspasos = new Traspasos();
            traspasos.getSalidasAlmacen().setCodSalidaAlmacen(managed.generaCodSalidaAlmacen());
            traspasos.getSalidasAlmacen().setNroSalidaAlmacen(managed.generaNroSalidaAlmacen());
            traspasos.getEstadosTraspaso().setCodEstadoTraspaso(1);
            traspasos.setFechaTraspaso(new Date());
            traspasos.getSalidasAlmacen().getTiposSalidasAlmacen().setCodTipoSalidaAlmacen(3);
            traspasos.getSalidasAlmacen().getAreasEmpresa().setCodAreaEmpresa(String.valueOf(cod_area_destino));
            traspasos.getAlmacenDestino().setCodAlmacen(cod_almacen_destino);
            traspasos.getAlmacenOrigen().setCodAlmacen(usuario.getAlmacenesGlobal().getCodAlmacen());
            traspasos.getSalidasAlmacen().getGestiones().setCodGestion(usuario.getGestionesGlobal().getCodGestion());
            traspasos.getSalidasAlmacen().setObsSalidaAlmacen("Salida por traspaso generada por sistema.");
            managed.guardaTraspaso(traspasos);

            //generar los datos de traspaso
            String consulta = " INSERT INTO SALIDAS_ALMACEN ( COD_GESTION, COD_SALIDA_ALMACEN, COD_ORDEN_PESADA,  COD_FORM_SALIDA, COD_PROD, "
                    + "   COD_TIPO_SALIDA_ALMACEN,  COD_AREA_EMPRESA,   NRO_SALIDA_ALMACEN,   FECHA_SALIDA_ALMACEN,   OBS_SALIDA_ALMACEN, "
                    + "  ESTADO_SISTEMA,   COD_ALMACEN,   COD_ORDEN_COMPRA,   COD_PERSONAL,    COD_ESTADO_SALIDA_ALMACEN,   COD_LOTE_PRODUCCION, "
                    + "   COD_ESTADO_SALIDA_COSTO,   cod_prod_ant,   orden_trabajo,   COD_PRESENTACION ) VALUES ( "
                    + "  '" + traspasos.getSalidasAlmacen().getGestiones().getCodGestion() + "', '" + traspasos.getSalidasAlmacen().getCodSalidaAlmacen() + "', '" + traspasos.getSalidasAlmacen().getCodOrdenRepesada() + "',"
                    + "  '" + traspasos.getSalidasAlmacen().getCodFormSalida() + "','" + traspasos.getSalidasAlmacen().getComponentesProd().getCodCompprod() + "', '" + traspasos.getSalidasAlmacen().getTiposSalidasAlmacen().getCodTipoSalidaAlmacen() + "', "
                    + "   '" + traspasos.getSalidasAlmacen().getAreasEmpresa().getCodAreaEmpresa() + "', '" + traspasos.getSalidasAlmacen().getNroSalidaAlmacen() + "',getdate(),"
                    +//traspasos.getSalidasAlmacen().getFechaSalidaAlmacen()
                    "  '" + traspasos.getSalidasAlmacen().getObsSalidaAlmacen() + "',  '" + "1" + "','" + traspasos.getAlmacenOrigen().getCodAlmacen() + "',"
                    + "  '" + traspasos.getSalidasAlmacen().getOrdenesCompra().getCodOrdenCompra() + "', '" + usuario.getUsuarioModuloBean().getCodUsuarioGlobal() + "',  '" + "1" + "',"
                    + "  '" + traspasos.getSalidasAlmacen().getCodLoteProduccion() + "','" + traspasos.getSalidasAlmacen().getCodEstadoSalidaCosto() + "',"
                    + "  '" + traspasos.getSalidasAlmacen().getCodProdAnt() + "', '" + traspasos.getSalidasAlmacen().getOrdenTrabajo() + "', '" + traspasos.getSalidasAlmacen().getPresentacionesProducto().getCodPresentacion() + "'); ";
            LOGGER.info("consulta " + consulta);
            st.executeUpdate(consulta);
            //iteracion de detalle
            Iterator i = salidasAlmacenDetalleList.iterator();

            for (Object solicitud : solicitudesDetalleList) {

                DevolucionesDetalle _detalle = (DevolucionesDetalle) solicitud;
                if (_detalle.isChecked() && _detalle.getCantidadDevuelta() > 0&&!isMaterialEsteril(_detalle.getMateriales().getCodMaterial())) {
                    consulta = " INSERT INTO SALIDAS_ALMACEN_DETALLE ( COD_SALIDA_ALMACEN, COD_MATERIAL, CANTIDAD_SALIDA_ALMACEN, COD_UNIDAD_MEDIDA, "
                            + "  COD_ESTADO_MATERIAL )  VALUES (   '" + traspasos.getSalidasAlmacen().getCodSalidaAlmacen() + "', '" + _detalle.getMateriales().getCodMaterial() + "',"
                            + "  '" + _detalle.getCantidadDevuelta() + "',   '" + _detalle.getUnidadesMedida().getCodUnidadMedida() + "', "
                            + "   '" + "2" + "' ) ";//estado de material aprobado
                    LOGGER.info("consulta " + consulta);
                    st.executeUpdate(consulta);
                    /*antes de la independencia de las etiquetas de las devoluciones
                    consulta = "  INSERT INTO SALIDAS_ALMACEN_DETALLE_INGRESO ( COD_SALIDA_ALMACEN,  COD_MATERIAL, COD_INGRESO_ALMACEN,  ETIQUETA, "
                            + "   FECHA_VENCIMIENTO,   CANTIDAD)  "
                            + " select " + traspasos.getSalidasAlmacen().getCodSalidaAlmacen() + ", "
                            + _detalle.getMateriales().getCodMaterial() + ", " + "eti.cod_ingreso_almacen" + ", "
                            + " eti.etiqueta, iade.fecha_vencimiento, eti.cantidad_devuelta "
                            + " from ingresos_almacen ia "
                            + " inner join devoluciones d on ia.cod_devolucion=d.cod_devolucion"
                            + " inner join devoluciones_detalle_etiquetas eti on d.cod_devolucion=eti.cod_devolucion"
                            + " inner join ingresos_almacen_detalle_estado iade on iade.cod_ingreso_almacen=eti.cod_ingreso_almacen"
                            + " and iade.cod_material=eti.cod_material and iade.etiqueta=eti.etiqueta"
                            + " where ia.cod_ingreso_almacen=" + cod_ingreso_almacen;
                     */

                    consulta = "  INSERT INTO SALIDAS_ALMACEN_DETALLE_INGRESO ( COD_SALIDA_ALMACEN,  COD_MATERIAL, COD_INGRESO_ALMACEN,  ETIQUETA, "
                            + "   FECHA_VENCIMIENTO,   CANTIDAD)  "
                            + " select " + traspasos.getSalidasAlmacen().getCodSalidaAlmacen() + ", "
                            + _detalle.getMateriales().getCodMaterial() + ", " + "iade.cod_ingreso_almacen" + ", "
                            + " iade.etiqueta, iade.fecha_vencimiento, iade.cantidad_restante "
                            + " from  ingresos_almacen_detalle_estado iade "
                            + " where iade.cod_ingreso_almacen=" + cod_ingreso_almacen + " and iade.cod_material=" + _detalle.getMateriales().getCodMaterial();
                    LOGGER.info("consulta " + consulta);
                    st.executeUpdate(consulta);

                    // se actualiza las cantidad restante de ingreso_almacen_detalle_empaque
                    /*antes de la independencia de las etiquetas de las devoluciones
                    consulta = " update ingresos_almacen_detalle_estado set cantidad_restante= cantidad_restante- eti.cantidad_devuelta "
                            + " from ingresos_almacen ia "
                            + " inner join devoluciones d on ia.cod_devolucion=d.cod_devolucion"
                            + " inner join devoluciones_detalle_etiquetas eti on d.cod_devolucion=eti.cod_devolucion"
                            + " inner join ingresos_almacen_detalle_estado iade on iade.cod_ingreso_almacen=eti.cod_ingreso_almacen"
                            + " and iade.cod_material=eti.cod_material and iade.etiqueta=eti.etiqueta"
                            + " where ia.cod_ingreso_almacen=" + cod_ingreso_almacen;
                     */
                    consulta = " update ingresos_almacen_detalle_estado set cantidad_restante=0 "
                            + " where cod_ingreso_almacen=" + cod_ingreso_almacen + " and cod_material=" + _detalle.getMateriales().getCodMaterial();
                    LOGGER.info("consulta update detalle estado " + consulta);
                    st.executeUpdate(consulta);

                }
            }
            //EJECUTAMOS SP DESPUES DE REGISTRO
            consulta = " exec [PAA_REGISTRO_SALIDA_ALMACEN_LOG] " + traspasos.getSalidasAlmacen().getCodSalidaAlmacen() 
                    + " , "+ usuario.getUsuarioModuloBean().getCodUsuarioGlobal()
                    + " , 3";
            LOGGER.info("consulta ejecutar: " + consulta); 
            st.executeUpdate(consulta);
            st.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean isMaterialEsteril(String cod_material) {
        boolean res = false;
         Connection con=null;
        try {
            //con = null;
            //con = Util.openConnection(con);
            String consulta = "  select COD_MAT_EST_NO_EST from  MATERIALES_EST_NO_EST where COD_MATERIAL_ESTERIL=  "+cod_material;
            LOGGER.info("consulta esteril---> " + consulta);
           
            con=Util.openConnection(con);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            if (rs.next()) {
                res=true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            try {
                con.close();
            } catch (SQLException ex) {
                LOGGER.warn(ex.getMessage());
            }
        }
        return res;
    }

    
    // <editor-fold defaultstate="collapsed" desc="getter and setter">
    
        public List<SelectItem> getTipoFrvProcesoSelectList() {
            return tipoFrvProcesoSelectList;
        }

        public void setTipoFrvProcesoSelectList(List<SelectItem> tipoFrvProcesoSelectList) {
            this.tipoFrvProcesoSelectList = tipoFrvProcesoSelectList;
        }
        public Devoluciones getDevolucionFrvAspiradora() {
            return devolucionFrvAspiradora;
        }

        public boolean isPermisoDevolucionMateriaPrima() {
            return permisoDevolucionMateriaPrima;
        }

        public void setPermisoDevolucionMateriaPrima(boolean permisoDevolucionMateriaPrima) {
            this.permisoDevolucionMateriaPrima = permisoDevolucionMateriaPrima;
        }

        

        public void setDevolucionFrvAspiradora(Devoluciones devolucionFrvAspiradora) {
            this.devolucionFrvAspiradora = devolucionFrvAspiradora;
        }

        public SolicitudDevoluciones getSolicitudDevolucionAnular() {
            return solicitudDevolucionAnular;
        }

        public void setSolicitudDevolucionAnular(SolicitudDevoluciones solicitudDevolucionAnular) {
            this.solicitudDevolucionAnular = solicitudDevolucionAnular;
        }

        public List<SelectItem> getAreasEmpresaSelectList() {
            return areasEmpresaSelectList;
        }

        public void setAreasEmpresaSelectList(List<SelectItem> areasEmpresaSelectList) {
            this.areasEmpresaSelectList = areasEmpresaSelectList;
        }
        
    //</editor-fold>

    
    
    
}
