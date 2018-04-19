/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofar.web;

import com.cofar.bean.Equivalencias;
import com.cofar.bean.EstadosMaterial;
import com.cofar.bean.FormulaMaestraDetalleEP;
import com.cofar.bean.FormulaMaestraDetalleES;
import com.cofar.bean.FormulaMaestraDetalleMP;
import com.cofar.bean.IngresosAlmacenDetalleEstado;
import com.cofar.bean.Materiales;
import com.cofar.bean.OrdenesCompra;
import com.cofar.bean.OrdenesCompraDetalle;
import com.cofar.bean.ProgramaProduccion;
import com.cofar.bean.SalidasAlmacen;
import com.cofar.bean.SalidasAlmacenDetalle;
import com.cofar.bean.SalidasAlmacenDetalleIngreso;
import com.cofar.bean.SolicitudMantenimiento;
import com.cofar.bean.Traspasos;
import com.cofar.dao.DaoEstadosMaterial;
import com.cofar.dao.DaoIngresosAlmacenDetalleEstado;
import com.cofar.dao.DaoSalidasAlmacenDetalleIngreso;
import com.cofar.service.AssignCostService;
import com.cofar.thread.ThreadCosteoByCodSalida;
import com.cofar.thread.ThreadCosteoSalidaAlmacen;
import com.cofar.util.Util;
import com.cofar.util.Utiles;
import com.cofar.util.correos.EnvioCorreoNotificacionValidacionSalidaAlmacen;
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
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.richfaces.component.html.HtmlDataTable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;/**
 *
 * @author hvaldivia
 */
public class ManagedRegistroSalidaAlmacen extends ManagedBean {
    
    private final static int COD_CAPITULO_EMPAQUE_PRIMARIO = 3;
    private boolean registradoCorrectamente = false;
    private int codIngresoAlmacenRechazados = 0;
    private boolean visible_salidaProduccion = false;
    private List<EstadosMaterial> estadosMaterialExistencia=new ArrayList<EstadosMaterial>();
    SalidasAlmacen salidasAlmacen = new SalidasAlmacen();
    List areasEmpresaList = new ArrayList();
    List tiposSalidasAlmacenList = new ArrayList();
    List componentesProdList = new ArrayList();
    List presentacionesList = new ArrayList();
    protected List salidasAlmacenDetalleList = new ArrayList();
    List salidasAlmacenDetalleIngresoList = new ArrayList();
    SalidasAlmacenDetalle salidasAlmacenDetalle = new SalidasAlmacenDetalle();
    HtmlDataTable salidasAlmacenDetalleDataTable = new HtmlDataTable();
    String mensaje = "";
    private String alertaSalidasLote = "";
    List capitulosList = new ArrayList();
    List gruposList = new ArrayList();
    SalidasAlmacenDetalle salidasAlmacenDetalleAgregar = new SalidasAlmacenDetalle();
    protected List materialesList = new ArrayList();
    protected Materiales buscarMaterial = new Materiales();
    protected List materialesBuscarList = new ArrayList();
    protected HtmlDataTable materialesBuscarDataTable = new HtmlDataTable();
    List estadosMaterialList = new ArrayList();
    IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstado = new IngresosAlmacenDetalleEstado();
    List salidasAlmacenDetalleAuxList = new ArrayList(); //esta lista almacenara los items que se editaran si se llega a guardar la edicion se tienen que restaurar las cantidades en los ingresos afectados

    private String motivoEdicionSalida = "";
    private boolean salidaTieneIngresoTraspaso = false;
    private List<SelectItem> almacenDestinoList = new ArrayList<SelectItem>();
    private List<SelectItem> filialList = new ArrayList<SelectItem>();
    private Traspasos traspasoGestionar = new Traspasos();
    
    //inicio ale
    private OrdenesCompra filtroOrdenCompra = new OrdenesCompra();
    private List<SelectItem> proveedoresList = new ArrayList<SelectItem>();
    private List<SelectItem> representantesList = new ArrayList<SelectItem>();
    private List<SelectItem> personalList = new ArrayList<SelectItem>();
    private Date fechaInicio = new Date();
    private Date fechaFinal = new Date();
    // private Materiales filtroMaterial= new Materiales();
    private List<OrdenesCompra> ordenesCompraList = new ArrayList<OrdenesCompra>();
    private List<OrdenesCompraDetalle> ordenesCompraDetalleList = new ArrayList<OrdenesCompraDetalle>();
    private String codCapitulo = "0";
    private String codGrupo = "0";
    private String codMaterial = "0";
    private HtmlDataTable ordenesCompraDataTable = new HtmlDataTable();
    private boolean productoHabilitado = false;
    private String mensajeInyectables = "";
    private List<SelectItem> maquinariasList = new ArrayList<SelectItem>();
    ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
    SolicitudMantenimiento solicitudMantenimiento = new SolicitudMantenimiento();
    private boolean salidaEditable = false;
    private ProgramaProduccion pproduccion = new ProgramaProduccion();
    private boolean emptyListEmpaqueSecundario = false;
    private HtmlDataTable salidasAlmacenDetalleDataTablePP = new HtmlDataTable();
    //final ale
    // variable que verifica si es administrador de sistema
    private boolean administradorAlmacen = false;

   

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
            LOGGER.warn(ex);
        }
    }

    /**
     * Creates a new instance of ManagedRegistroSalidaAlmacen
     */
    public ManagedRegistroSalidaAlmacen() {
        LOGGER = LogManager.getLogger("Salidas");
    }

    public String getCargarContenidoRegistroSalidaAlmacen() {
        visible_salidaProduccion = false;
        this.cargarPermisoPersonal();
        try {
            ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            alertaSalidasLote = "";
            setProductoHabilitado(false);

            salidasAlmacen = new SalidasAlmacen();
            salidasAlmacen.setNroSalidaAlmacen(this.generaNroIngresoAlmacen());
            salidasAlmacen.getGestiones().setCodGestion(usuario.getGestionesGlobal().getCodGestion());
            salidasAlmacen.getPersonal().setCodPersonal(usuario.getUsuarioModuloBean().getCodUsuarioGlobal());
            salidasAlmacen.getAlmacenes().setCodAlmacen(usuario.getAlmacenesGlobal().getCodAlmacen());
            salidasAlmacen.getEstadosSalidasAlmacen().setCodEstadoSalidaAlmacen(1);
            salidasAlmacen.setEstadoSistema(1);
            salidasAlmacen.getTiposSalidasAlmacen().setCodTipoSalidaAlmacen(1);//solicitud manual
            salidasAlmacen.getPresentacionesProducto().setCodPresentacion("0");
            this.cargarAreasEmpresa();
            this.cargarComponentesProd();
            this.cargarTiposSalidasAlmacen();
            capitulosList = this.cargarCapitulos();
            gruposList.add(new SelectItem("0", "-NINGUNO-"));
            materialesList.add(new SelectItem("0", "-NINGUNO-"));
            presentacionesList.add(new SelectItem("0", "-NINGUNO-"));
            salidasAlmacenDetalleList.clear();
            estadosMaterialList = this.cargarEstadosMaterial();

            //verificacion si existe algun objeto de donde se pueda realizar una salida de almacen
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            Map<String, Object> sessionMap = externalContext.getSessionMap();
            ProgramaProduccion programaProduccion = (ProgramaProduccion) sessionMap.get("programaProduccion");
            pproduccion = programaProduccion;
            List materiaPrimaList = (ArrayList) sessionMap.get("materiaPrimaList");
            List empaquePrimarioList = (ArrayList) sessionMap.get("empaquePrimarioList");
            List empaqueSecundarioList = (ArrayList) sessionMap.get("empaqueSecundarioList");
            List programaProduccionList = (ArrayList) sessionMap.get("programaProduccionSeleccionadoList");
            solicitudMantenimiento = (SolicitudMantenimiento) sessionMap.get("solicitudMantenimiento");

            if (programaProduccion != null) {
                visible_salidaProduccion = true;
                salidasAlmacen.getComponentesProd().getForma().setCodForma(codGrupo);
                ingresosAlmacenDetalleEstado.getEstadosMaterial().setCodEstadoMaterial(2); //estado aprobado
                salidasAlmacen.setCodLoteProduccion(programaProduccion.getCodLoteProduccion());
                LOGGER.info("los valores comp prod" + programaProduccion.getFormulaMaestra().getComponentesProd().getCodCompprod());
                salidasAlmacen.getComponentesProd().setCodCompprod(programaProduccion.getFormulaMaestra().getComponentesProd().getCodCompprod());
                LOGGER.info("cdcd" + salidasAlmacen.getComponentesProd().getCodCompprod());
                salidasAlmacen.setAreasEmpresa(programaProduccion.getFormulaMaestra().getComponentesProd().getAreasEmpresa());
                this.cargarProductos(salidasAlmacen, programaProduccionList);
                this.cargaSalidasAlmacenProgramaProduccion(programaProduccion, materiaPrimaList, empaquePrimarioList, empaqueSecundarioList);
                this.producto_changed();
                LOGGER.info("cdcd" + salidasAlmacen.getComponentesProd().getCodCompprod());
                tiposSalidasAlmacenList = this.cargarTiposSalidasAlmacenProduccion();
                salidasAlmacen.getTiposSalidasAlmacen().setCodTipoSalidaAlmacen(2);//salida automatica
                LOGGER.info("cod presentacion J: " + programaProduccion.getPresentacionesProducto().getCodPresentacion());
                salidasAlmacen.getPresentacionesProducto().setCodPresentacion(programaProduccion.getPresentacionesProducto().getCodPresentacion());
                sessionMap.put("programaProduccion", null);
                sessionMap.put("programaProduccionSeleccionadoList", null);
            }
            if (solicitudMantenimiento != null) {

                ingresosAlmacenDetalleEstado.getEstadosMaterial().setCodEstadoMaterial(2);
                salidasAlmacen.setOrdenTrabajo(String.valueOf(solicitudMantenimiento.getNroOrdenTrabajo()));
                salidasAlmacen.setMaquinaria(solicitudMantenimiento.getMaquinaria());
                salidasAlmacen.setAreasEmpresa(solicitudMantenimiento.getAreasEmpresa());
                salidasAlmacen.getTiposSalidasAlmacen().setCodTipoSalidaAlmacen(2);
                sessionMap.put("solicitudMantenimiento", null);
                sessionMap.put("programaProduccionSeleccionadoList", null);
                this.materialesSolicitudMantenimiento(solicitudMantenimiento);

                //sessionMap.put("solicitudesSalidaDetalle",null);
                //sessionMap.put("solicitudMantenimiento", null);
                //this.cargarDetalleSolicitud();
            }

        } catch (Exception e) {
            LOGGER.warn(e);
        }
        return null;
    }

    public void cargarProductos(SalidasAlmacen salidasAlmacen, List programaProduccionList) {
        Iterator i = programaProduccionList.iterator();
        if (i.hasNext()) {
            ProgramaProduccion programaProduccion = (ProgramaProduccion) i.next();
            salidasAlmacen.setComponentesProd(programaProduccion.getFormulaMaestra().getComponentesProd());
        }

        if (i.hasNext()) {
            ProgramaProduccion programaProduccion = (ProgramaProduccion) i.next();
            salidasAlmacen.setComponentesProd1(programaProduccion.getFormulaMaestra().getComponentesProd());
        }

    }

    @SuppressWarnings("CallToPrintStackTrace")
    public void materialesSolicitudMantenimiento(SolicitudMantenimiento solicitudMantenimiento) {
        try {

            String consulta = "select s.COD_SOLICITUD_MANTENIMIENTO,m.COD_MATERIAL,m.NOMBRE_MATERIAL,u.COD_UNIDAD_MEDIDA,u.NOMBRE_UNIDAD_MEDIDA,s.CANTIDAD,u.abreviatura "
                    + " from SOLICITUDES_MANTENIMIENTO_DETALLE_MATERIALES s"
                    + " inner join materiales m on m.COD_MATERIAL = s.COD_MATERIAL"
                    + " inner join UNIDADES_MEDIDA u on u.COD_UNIDAD_MEDIDA = s.COD_UNIDAD_MEDIDA"
                    + " where s.cod_solicitud_mantenimiento = '" + solicitudMantenimiento.getCodSolicitudMantenimiento() + "'  ";

            LOGGER.info("consulta " + consulta);
            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(consulta);
            while (rs.next()) {
                SalidasAlmacenDetalle salidasAlmacenDetalleItem = new SalidasAlmacenDetalle();
                salidasAlmacenDetalleItem.getMateriales().setCodMaterial(rs.getString("cod_material"));
                salidasAlmacenDetalleItem.getMateriales().setNombreMaterial(rs.getString("nombre_material"));
                salidasAlmacenDetalleItem.setCantidadSalidaAlmacen(rs.getFloat("cantidad"));
                salidasAlmacenDetalleItem.getUnidadesMedida().setCodUnidadMedida(rs.getInt("cod_unidad_medida"));
                salidasAlmacenDetalleItem.getUnidadesMedida().setAbreviatura(rs.getString("abreviatura"));
                this.etiquetasSalidaAlmacen(salidasAlmacenDetalleItem);
                salidasAlmacenDetalleItem.setEquivalencias(this.buscaEquivalencia(salidasAlmacenDetalleItem));
                salidasAlmacenDetalleItem.getEstadosMaterial().setCodEstadoMaterial(2); //estado aprobado
                if (salidasAlmacenDetalleItem.getCantidadSalidaAlmacen() > salidasAlmacenDetalleItem.getCantidadDisponible()) {
                    salidasAlmacenDetalleItem.setColorFila("#FF0000");
                } else {
                    salidasAlmacenDetalleItem.setColorFila("#00FF00");
                }

                this.detalleEtiquetasSalidaAlmacen(salidasAlmacenDetalleItem);

                if (salidasAlmacenDetalleItem.getMateriales().getGrupos().getCodGrupo() == 23 && salidasAlmacen.getCodLoteProduccion().substring(0, 1).equals("1")) {
                    this.detalleEtiquetasSalidaAlmacenInyectables(salidasAlmacenDetalleItem);
                } else {
                    this.detalleEtiquetasSalidaAlmacen(salidasAlmacenDetalleItem);
                }

                salidasAlmacenDetalleList.add(salidasAlmacenDetalleItem);
            }

        } catch (Exception e) {
            LOGGER.warn(e);
        }

    }

    public void cargaSalidasAlmacenProgramaProduccion(ProgramaProduccion programaProduccion, List materiaPrimaList, List empaquePrimarioList, List empaqueSecundarioList) {
        Connection con2= null;
        try {
            con2 = Util.openConnection(con2);
            Statement st = con2.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            StringBuilder consulta = new StringBuilder("select cpv.COD_FORMA")
                                            .append(" from programa_produccion pp")
                                                    .append(" inner join componentes_prod_Version cpv on cpv.COD_VERSION = pp.COD_COMPPROD_VERSION")
                                                            .append(" and cpv.COD_COMPPROD = pp.COD_COMPPROD")
                                            .append(" where pp.COD_LOTE_PRODUCCION =").append(programaProduccion.getCodLoteProduccion())
                                                    .append(" and pp.COD_COMPPROD =").append(programaProduccion.getFormulaMaestra().getComponentesProd().getCodCompprod())
                                                    .append(" and pp.COD_TIPO_PROGRAMA_PROD =").append(programaProduccion.getTiposProgramaProduccion().getCodTipoProgramaProd())
                                                    .append(" and pp.COD_PROGRAMA_PROD =").append(programaProduccion.getCodProgramaProduccion());
            System.out.println("consulta obtener forma farmaceutica producto "+consulta.toString());
            int codFormaFarmaceutica = 0;
            ResultSet res =st.executeQuery(consulta.toString());
            if(res.next())
            {
                codFormaFarmaceutica = res.getInt("COD_FORMA");
            }
            System.out.println("cod Forma "+codFormaFarmaceutica);
            mensajeInyectables = "";
            
            salidasAlmacenDetalleList.clear();

            emptyListEmpaqueSecundario = false;
            Iterator i = materiaPrimaList.iterator();
            while (i.hasNext()) {
                FormulaMaestraDetalleMP formulaMaestraDetalleMP = (FormulaMaestraDetalleMP) i.next();
                if (formulaMaestraDetalleMP.getChecked().booleanValue() == true) {
                    SalidasAlmacenDetalle salidasAlmacenDetalleItem = new SalidasAlmacenDetalle();
                    salidasAlmacenDetalleItem.getMateriales().setCodMaterial(formulaMaestraDetalleMP.getMateriales().getCodMaterial());
                    salidasAlmacenDetalleItem.getMateriales().setNombreMaterial(formulaMaestraDetalleMP.getMateriales().getNombreMaterial());
                    LOGGER.info("J MP: " + salidasAlmacenDetalleItem.getMateriales().getNombreMaterial() + "  " + formulaMaestraDetalleMP.getCantidad().floatValue() + " " + formulaMaestraDetalleMP.getCantidad());
                    salidasAlmacenDetalleItem.setCantidadSalidaAlmacen(formulaMaestraDetalleMP.getCantidad().floatValue());
                    salidasAlmacenDetalleItem.setCantidadSalidaProgProd(formulaMaestraDetalleMP.getCantidad());
                    salidasAlmacenDetalleItem.setTipo_material(2);
                    salidasAlmacenDetalleItem.getUnidadesMedida().setCodUnidadMedida(formulaMaestraDetalleMP.getUnidadesMedida().getCodUnidadMedida());
                    salidasAlmacenDetalleItem.getUnidadesMedida().setAbreviatura(formulaMaestraDetalleMP.getUnidadesMedida().getAbreviatura());
                    salidasAlmacenDetalleItem.setSalidasAlmacen(salidasAlmacen);
                    this.etiquetasSalidaAlmacen(salidasAlmacenDetalleItem);
                    salidasAlmacenDetalleItem.setEquivalencias(this.buscaEquivalencia(salidasAlmacenDetalleItem));
                    salidasAlmacenDetalleItem.getEstadosMaterial().setCodEstadoMaterial(2); //estado aprobado
                    this.cantidadReanalisisItemPrioritario(salidasAlmacenDetalleItem);
                    if (salidasAlmacenDetalleItem.getCantidadSalidaAlmacen() > salidasAlmacenDetalleItem.getCantidadDisponible() || salidasAlmacenDetalleItem.getCantidadExistenciaReanalisis() > 0) {
                        salidasAlmacenDetalleItem.setColorFila("#FF0000");
                    } else {
                        salidasAlmacenDetalleItem.setColorFila("#00FF00");
                    }
                    
                    if (salidasAlmacenDetalleItem.getMateriales().getGrupos().getCodGrupo() == 23 && codFormaFarmaceutica == 2) {
                        this.detalleEtiquetasSalidaAlmacenInyectables(salidasAlmacenDetalleItem);
                    } else {
                        //cambio de calculo de detalle de etiquetas para las salidas por produccion
                        this.detalleEtiquetasSalidaAlmacen(salidasAlmacenDetalleItem);
                    }

                    salidasAlmacenDetalleList.add(salidasAlmacenDetalleItem);
                }
            }
            i = empaquePrimarioList.iterator();

            while (i.hasNext()) {
                FormulaMaestraDetalleEP formulaMaestraDetalleEP = (FormulaMaestraDetalleEP) i.next();
                if (formulaMaestraDetalleEP.getChecked().booleanValue() == true) {
                    SalidasAlmacenDetalle salidasAlmacenDetalleItem = new SalidasAlmacenDetalle();
                    salidasAlmacenDetalleItem.getMateriales().setCodMaterial(formulaMaestraDetalleEP.getMateriales().getCodMaterial());
                    salidasAlmacenDetalleItem.getMateriales().setNombreMaterial(formulaMaestraDetalleEP.getMateriales().getNombreMaterial());
                    salidasAlmacenDetalleItem.getMateriales().setGrupos(formulaMaestraDetalleEP.getMateriales().getGrupos());
                    this.cantidadReanalisisItemPrioritario(salidasAlmacenDetalleItem);
                    salidasAlmacenDetalleItem.setCantidadSalidaAlmacen(formulaMaestraDetalleEP.getCantidad());
                    salidasAlmacenDetalleItem.getUnidadesMedida().setCodUnidadMedida(formulaMaestraDetalleEP.getUnidadesMedida().getCodUnidadMedida());
                    salidasAlmacenDetalleItem.getUnidadesMedida().setAbreviatura(formulaMaestraDetalleEP.getUnidadesMedida().getAbreviatura());
                    this.etiquetasSalidaAlmacen(salidasAlmacenDetalleItem);

                    salidasAlmacenDetalleItem.setEquivalencias(this.buscaEquivalencia(salidasAlmacenDetalleItem));
                    salidasAlmacenDetalleItem.getEstadosMaterial().setCodEstadoMaterial(2); //estado aprobado
                    if (salidasAlmacenDetalleItem.getCantidadSalidaAlmacen() > salidasAlmacenDetalleItem.getCantidadDisponible() || salidasAlmacenDetalleItem.getCantidadExistenciaReanalisis() > 0) {
                        salidasAlmacenDetalleItem.setColorFila("#FF0000");
                    } else {
                        salidasAlmacenDetalleItem.setColorFila("#00FF00");
                    }

                    if (salidasAlmacenDetalleItem.getMateriales().getGrupos().getCodGrupo() == 23 && codFormaFarmaceutica == 2) {
                        this.detalleEtiquetasSalidaAlmacenInyectables(salidasAlmacenDetalleItem);
                    } else {
                        //cambio de calculo de detalle de etiquetas para las salidas por produccion
                        this.detalleEtiquetasSalidaAlmacen(salidasAlmacenDetalleItem);
                    }

                    salidasAlmacenDetalleList.add(salidasAlmacenDetalleItem);
                }
            }

            i = empaqueSecundarioList.iterator();

            while (i.hasNext()) {
                FormulaMaestraDetalleES formulaMaestraDetalleES = (FormulaMaestraDetalleES) i.next();
                if (formulaMaestraDetalleES.getChecked().booleanValue() == true) {
                    emptyListEmpaqueSecundario = true;
                    SalidasAlmacenDetalle salidasAlmacenDetalleItem = new SalidasAlmacenDetalle();
                    salidasAlmacenDetalleItem.getMateriales().setCodMaterial(formulaMaestraDetalleES.getMateriales().getCodMaterial());
                    salidasAlmacenDetalleItem.getMateriales().setNombreMaterial(formulaMaestraDetalleES.getMateriales().getNombreMaterial());
                    salidasAlmacenDetalleItem.setCantidadSalidaAlmacen(formulaMaestraDetalleES.getCantidad());
                    salidasAlmacenDetalleItem.getUnidadesMedida().setCodUnidadMedida(formulaMaestraDetalleES.getUnidadesMedida().getCodUnidadMedida());
                    salidasAlmacenDetalleItem.getUnidadesMedida().setAbreviatura(formulaMaestraDetalleES.getUnidadesMedida().getAbreviatura());
                    this.etiquetasSalidaAlmacen(salidasAlmacenDetalleItem);
                    salidasAlmacenDetalleItem.setEquivalencias(this.buscaEquivalencia(salidasAlmacenDetalleItem));
                    salidasAlmacenDetalleItem.getEstadosMaterial().setCodEstadoMaterial(2); //estado aprobado
                    if (salidasAlmacenDetalleItem.getCantidadSalidaAlmacen() > salidasAlmacenDetalleItem.getCantidadDisponible()) {
                        salidasAlmacenDetalleItem.setColorFila("#FF0000");
                    } else {
                        salidasAlmacenDetalleItem.setColorFila("#00FF00");
                    }
                    //cambio de calculo de detalle de etiquetas para las salidas por produccion
                    this.detalleEtiquetasSalidaAlmacen(salidasAlmacenDetalleItem);
                    salidasAlmacenDetalleList.add(salidasAlmacenDetalleItem);
                }
            }

        } catch (Exception e) {
            LOGGER.warn(e);
        }
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
            LOGGER.info("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            if (rs.next()) {
                nroIngresoAlmacen = rs.getInt("nro_salida_almacen");
            }
            con.close();
            st.close();
            rs.close();
        } catch (Exception e) {
            LOGGER.warn(e);
        }
        return nroIngresoAlmacen;
    }

    public String verificaAreaProduccion(String codCompProd) {
        String areaProduccion = "0";
        //ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select c.COD_AREA_EMPRESA from COMPONENTES_PROD c where c.COD_COMPPROD= " + codCompProd + "";
            LOGGER.info("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            if (rs.next()) {
                areaProduccion = rs.getString(1);
            }
            con.close();
            st.close();
            rs.close();
        } catch (Exception e) {
            LOGGER.warn(e);
        }
        return areaProduccion;
    }

    public void cargarAreasEmpresa() {
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
            LOGGER.warn(e);
        }
    }

    public void cargarTiposSalidasAlmacen() {
        try {
            Connection con = null;
            con = Util.openConnection(con);

            String sql = " select s.COD_TIPO_SALIDA_ALMACEN,s.NOMBRE_TIPO_SALIDA_ALMACEN  "
                    + " from tipos_salidas_almacen s where cod_tipo_salida_almacen<>3 "
                    + " and cod_tipo_salida_almacen<>0 and cod_tipo_salida_almacen<>4 "
                    + " and cod_tipo_salida_almacen<>5 and cod_tipo_salida_almacen<>6 "
                    + " and cod_tipo_salida_almacen<>9 "
                    + //and cod_tipo_salida_almacen<>10
                    " and cod_tipo_salida_almacen<>11 and  cod_estado_registro=1 "
                    + " order by nombre_tipo_salida_almacen ";

            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(sql);
            tiposSalidasAlmacenList.clear();

            while (rs.next()) {
                tiposSalidasAlmacenList.add(new SelectItem(rs.getString("COD_TIPO_SALIDA_ALMACEN"), rs.getString("NOMBRE_TIPO_SALIDA_ALMACEN")));
            }

            if (rs != null) {
                rs.close();
                st.close();
                con.close();
            }
        } catch (Exception e) {
            LOGGER.warn(e);
        }
    }
    
    public void cargarTiposSalidasAlmacenEditar() {
        try {
            Connection con = null;
            con = Util.openConnection(con);

            String sql = " select s.COD_TIPO_SALIDA_ALMACEN,s.NOMBRE_TIPO_SALIDA_ALMACEN  "
                    + " from tipos_salidas_almacen s "
                    + " where cod_estado_registro=1 "
                    + " order by nombre_tipo_salida_almacen ";

            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(sql);
            tiposSalidasAlmacenList.clear();

            while (rs.next()) {
                tiposSalidasAlmacenList.add(new SelectItem(rs.getString("COD_TIPO_SALIDA_ALMACEN"), rs.getString("NOMBRE_TIPO_SALIDA_ALMACEN")));
            }

            if (rs != null) {
                rs.close();
                st.close();
                con.close();
            }
        } catch (Exception e) {
            LOGGER.warn(e);
        }
    }

    public List cargarTiposSalidasAlmacenProduccion() {
        List tiposSalidasAlmacenList = new ArrayList();
        try {
            Connection con = null;
            con = Util.openConnection(con);

            String sql = " select s.COD_TIPO_SALIDA_ALMACEN,s.NOMBRE_TIPO_SALIDA_ALMACEN  "
                    + " from tipos_salidas_almacen s where cod_tipo_salida_almacen in (2,7) "
                    + " order by cod_tipo_salida_almacen asc ";

            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(sql);
            tiposSalidasAlmacenList.clear();

            while (rs.next()) {
                tiposSalidasAlmacenList.add(new SelectItem(rs.getString("COD_TIPO_SALIDA_ALMACEN"), rs.getString("NOMBRE_TIPO_SALIDA_ALMACEN")));
            }

            if (rs != null) {
                rs.close();
                st.close();
                con.close();
            }
        } catch (Exception e) {
            LOGGER.warn(e);
        }
        return tiposSalidasAlmacenList;
    }

    public void cargarComponentesProd() {
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String sql = " select cp.COD_COMPPROD,cp.nombre_prod_semiterminado  "
                    + " from COMPONENTES_PROD cp where cp.COD_ESTADO_COMPPROD=1 "
                    + " and cp.nombre_prod_semiterminado is not null and cp.nombre_prod_semiterminado != '' order by nombre_prod_semiterminado ";
            LOGGER.info("consulta " + sql);
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
            LOGGER.warn(e);
        }
    }

    public String producto_changed() {
        try {
            if(salidasAlmacen.getAlmacenes().getCodAlmacen() == 1)
            {
                this.nroLote_onblur();
            }
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select p.cod_presentacion,p.NOMBRE_PRODUCTO_PRESENTACION  "
                    + " from PRESENTACIONES_PRODUCTO p,COMPONENTES_PRESPROD c  "
                    + " where p.COD_PRESENTACION=c.COD_PRESENTACION  "
                    + " and c.COD_COMPPROD='" + salidasAlmacen.getComponentesProd().getCodCompprod() + "' ";
            LOGGER.info("consulta " + consulta);
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
            LOGGER.warn(e);
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

            LOGGER.info("consulta " + consulta);
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
            LOGGER.warn(e);
        }
        return null;
    }

    public float cantidadDisponibleMaterial(SalidasAlmacenDetalle salidasAlmacenDetalle) {
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
            consulta = " select sum(id.CANTIDAD_RESTANTE) cantidad_r from INGRESOS_ALMACEN i, INGRESOS_ALMACEN_DETALLE_ESTADO id "
                    + " where i.COD_INGRESO_ALMACEN=id.COD_INGRESO_ALMACEN and i.COD_ESTADO_INGRESO_ALMACEN=1 and "
                    + " id.COD_MATERIAL='" + salidasAlmacenDetalle.getMateriales().getCodMaterial() + "' and id.CANTIDAD_RESTANTE>0 and id.COD_ESTADO_MATERIAL='" + ingresosAlmacenDetalleEstado.getEstadosMaterial().getCodEstadoMaterial() + "' and i.COD_ALMACEN ='" + usuario.getAlmacenesGlobal().getCodAlmacen() + "'; ";

            LOGGER.info("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            while (rs.next()) {
                cantidadDisponible = cantidadDisponible + rs.getFloat("cantidad_r");
            }
            rs.close();
            st.close();
            con.close();

        } catch (Exception e) {
            LOGGER.warn(e);
        }
        return cantidadDisponible;
    }

    public String verDetalleEtiquetasSalidaAlmacen_action() {
        try {
            if (pproduccion != null) {
                salidasAlmacenDetalle = (SalidasAlmacenDetalle) salidasAlmacenDetalleDataTablePP.getRowData();
            } else {
                salidasAlmacenDetalle = (SalidasAlmacenDetalle) salidasAlmacenDetalleDataTable.getRowData();
            }
        } catch (Exception e) {
            LOGGER.warn(e);
        }
        return null;
    }

    public void cantidadReanalisisItemPrioritario(SalidasAlmacenDetalle salidasAlmacenDetalle) {
        try {
            ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            Connection con = null;
            con = Util.openConnection(con);
            StringBuilder consulta = new StringBuilder("  select iade.COD_ESTADO_MATERIAL,iade.CANTIDAD_RESTANTE");
            consulta.append(" ,isnull(cse.COD_ESTADO_MATERIAL,0) as estadoPermitido");
            consulta.append(" from INGRESOS_ALMACEN_DETALLE_ESTADO iade");
            consulta.append(" inner join INGRESOS_ALMACEN_DETALLE iad on iad.COD_INGRESO_ALMACEN =");
            consulta.append(" iade.COD_INGRESO_ALMACEN and iad.COD_MATERIAL = iade.COD_MATERIAL");
            consulta.append(" inner join INGRESOS_ALMACEN ia on ia.COD_INGRESO_ALMACEN =iad.COD_INGRESO_ALMACEN");
            consulta.append(" inner join ALMACENES a on a.COD_ALMACEN = ia.COD_ALMACEN");
            consulta.append(" left outer join CONFIGURACION_SALIDA_ESTADO_MATERIAL cse on cse.COD_ESTADO_MATERIAL=iade.COD_ESTADO_MATERIAL");
                    consulta.append(" and cse.COD_ALMACEN=").append(usuario.getAlmacenesGlobal().getCodAlmacen());
            consulta.append(" where a.COD_ALMACEN =").append(usuario.getAlmacenesGlobal().getCodAlmacen());
            consulta.append(" and ia.COD_ESTADO_INGRESO_ALMACEN = 1");
            consulta.append(" and ia.ESTADO_SISTEMA = 1 and ia.ESTADO_SISTEMA = 1 and iade.CANTIDAD_RESTANTE > 0.009");
            consulta.append(" and iade.COD_MATERIAL =").append(salidasAlmacenDetalle.getMateriales().getCodMaterial());
            consulta.append(" and iade.COD_ESTADO_MATERIAL in (2, 6, 5,8)");
            consulta.append(" order by case");
            consulta.append(" when iade.CANTIDAD_PARCIAL <> iade.CANTIDAD_RESTANTE and iade.COD_ESTADO_MATERIAL in(5)");
            consulta.append(" then 1 else 2 end,");
            consulta.append(" iade.fecha_vencimiento");
            LOGGER.info("-consulta verificar material reanalisis " + consulta.toString());
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta.toString());
            salidasAlmacenDetalle.setCantidadExistenciaReanalisis(0d);
            while (rs.next()) {
                if (rs.getInt("COD_ESTADO_MATERIAL") == 5 && (rs.getInt("estadoPermitido") == 0)) {
                    salidasAlmacenDetalle.setCantidadExistenciaReanalisis(salidasAlmacenDetalle.getCantidadExistenciaReanalisis() + rs.getDouble("CANTIDAD_RESTANTE"));
                } else {
                    rs.last();
                    break;
                }
            }
            rs.close();
            st.close();
            con.close();

        } catch (Exception e) {
            LOGGER.warn(e);
        }
    }

    public void etiquetasSalidaAlmacen(SalidasAlmacenDetalle salidasAlmacenDetalle) {
            ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            salidasAlmacenDetalle.setSalidasAlmacen(salidasAlmacen);
            Connection con = null;
            DaoSalidasAlmacenDetalleIngreso daoEtiquetas = new DaoSalidasAlmacenDetalleIngreso(LOGGER);
            salidasAlmacenDetalle.setSalidasAlmacenDetalleIngresoList(daoEtiquetas.listarAgregar(salidasAlmacenDetalle));
            LOGGER.debug("cantidad restante: "+salidasAlmacenDetalle.getCantidadDisponible());
    }

    public void etiquetasSalidaAlmacenProduccion(SalidasAlmacenDetalle salidasAlmacenDetalle) {
        try {
            ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            Connection con = null;
            con = Util.openConnection(con);
            StringBuilder consulta = new StringBuilder("  select iade.cod_ingreso_almacen, iade.cod_material, iade.etiqueta,  iade.cod_estado_material, iade.cod_empaque_secundario_externo, ");
            consulta.append("  iade.CANTIDAD_RESTANTE cantidad_r, iade.fecha_vencimiento, iade.lote_material_proveedor ");
            consulta.append(" ,e.nombre_estado_material,ia.nro_ingreso_almacen,sec.nombre_seccion,ese.nombre_empaque_secundario_externo ,");
            consulta.append(" (select sadi1.CANTIDAD from SALIDAS_ALMACEN_DETALLE_INGRESO sadi1 where sadi1.COD_INGRESO_ALMACEN=iade.COD_INGRESO_ALMACEN and  sadi1.COD_MATERIAL = iade.COD_MATERIAL and sadi1.ETIQUETA = iade.ETIQUETA");
            consulta.append("  and sadi1.COD_SALIDA_ALMACEN = '").append(salidasAlmacenDetalle.getSalidasAlmacen().getCodSalidaAlmacen()).append("') cantidad_sacar,e1.cod_estante,e1.nombre_estante,iade.fila,iade.columna ");
            consulta.append(" , iade.cantidad_restante_valorada, iade.valoracion_cc_porcentual");
            consulta.append(" from INGRESOS_ALMACEN_DETALLE_ESTADO iade ");
            consulta.append(" inner join INGRESOS_ALMACEN_DETALLE iad on iad.COD_INGRESO_ALMACEN = iade.COD_INGRESO_ALMACEN and iad.COD_MATERIAL = iade.COD_MATERIAL");
            consulta.append(" inner join INGRESOS_ALMACEN ia on ia.COD_INGRESO_ALMACEN = iad.COD_INGRESO_ALMACEN");
            consulta.append(" inner join ALMACENES a on a.COD_ALMACEN = ia.COD_ALMACEN");
            consulta.append(" inner join ESTADOS_MATERIAL e on e.COD_ESTADO_MATERIAL = iade.COD_ESTADO_MATERIAL");
            consulta.append(" inner join EMPAQUES_SECUNDARIO_EXTERNO ese on ese.COD_EMPAQUE_SECUNDARIO_EXTERNO = iade.COD_EMPAQUE_SECUNDARIO_EXTERNO ");
            consulta.append(" inner join secciones sec on sec.COD_SECCION = iad.COD_SECCION");
            consulta.append(" left outer join estante_ambiente e1 on e1.cod_estante = iade.cod_estante ");
            consulta.append(" left outer join (select sum(sadi.CANTIDAD) as cantidadSalida, sadi.COD_MATERIAL,sadi.ETIQUETA,sadi.COD_INGRESO_ALMACEN");
            consulta.append(" from SALIDAS_ALMACEN_DETALLE_INGRESO sadi");
            consulta.append(" inner join SALIDAS_ALMACEN sa on sa.COD_SALIDA_ALMACEN =sadi.COD_SALIDA_ALMACEN ");
            consulta.append(" where sa.COD_TIPO_SALIDA_ALMACEN=13");//tipos de salidas almacen por muestreo
            consulta.append(" and sa.ESTADO_SISTEMA=1");
            consulta.append(" and sa.COD_ESTADO_SALIDA_ALMACEN=1");
            consulta.append(" group by sadi.COD_MATERIAL,sadi.ETIQUETA,sadi.COD_INGRESO_ALMACEN");
            consulta.append(" ) cantidadSalidaLoteP");
            consulta.append(" on cantidadSalidaLoteP.cod_material=iade.COD_MATERIAL ");
            consulta.append(" and cantidadSalidaLoteP.etiqueta=iade.ETIQUETA");
            consulta.append(" and cantidadSalidaLoteP.COD_INGRESO_ALMACEN=iade.COD_INGRESO_ALMACEN");

            consulta.append(" left outer join(");
            consulta.append(" select  sadi.COD_MATERIAL, sadi.COD_INGRESO_ALMACEN, sadi.ETIQUETA, SUM(sadi.CANTIDAD) cantidadSalida");
            consulta.append(" from SOLICITUDES_SALIDA sol");
            consulta.append(" inner join SALIDAS_ALMACEN sa");
            consulta.append(" on sa.COD_FORM_SALIDA=sol.COD_FORM_SALIDA");
            consulta.append(" inner join SALIDAS_ALMACEN_DETALLE_INGRESO sadi");
            consulta.append(" on sadi.COD_SALIDA_ALMACEN=sa.COD_SALIDA_ALMACEN");
            consulta.append(" where sol.SOLICITUD_POR_LOTE_PROVEEDOR=1");
            consulta.append(" and sa.COD_TIPO_SALIDA_ALMACEN<>13");
            consulta.append(" group by  sadi.COD_MATERIAL, sadi.COD_INGRESO_ALMACEN, sadi.ETIQUETA");
            consulta.append(" ) cantidadSolicitudLoteP");
            consulta.append(" on cantidadSolicitudLoteP.cod_material=iade.COD_MATERIAL ");
            consulta.append(" and cantidadSolicitudLoteP.etiqueta=iade.ETIQUETA");
            consulta.append(" and cantidadSolicitudLoteP.COD_INGRESO_ALMACEN=iade.COD_INGRESO_ALMACEN");

            consulta.append(" where a.COD_ALMACEN =").append(usuario.getAlmacenesGlobal().getCodAlmacen());
            consulta.append(" and ia.COD_ESTADO_INGRESO_ALMACEN = 1");
            consulta.append(" and ia.ESTADO_SISTEMA = 1");
            consulta.append(" and ia.ESTADO_SISTEMA = 1  ");
            consulta.append(" and iade.CANTIDAD_RESTANTE>0 ");
            consulta.append(" and iade.COD_MATERIAL = ").append(salidasAlmacenDetalle.getMateriales().getCodMaterial());
            if (((ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema")).getAlmacenesGlobal().getCodAlmacen() != 12 && ((ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema")).getAlmacenesGlobal().getCodAlmacen() != 8) {
                consulta.append(" and iade.COD_ESTADO_MATERIAL in(2,6,8) ");
            }
            consulta.append(" order by case when iade.CANTIDAD_PARCIAL<>(iade.CANTIDAD_RESTANTE+isnull(cantidadSalidaLoteP.cantidadSalida,0)"
                    + "+isnull(cantidadSolicitudLoteP.cantidadSalida,0)"
                    + ") then 1 else 2 end,iade.fecha_vencimiento  ");
            LOGGER.info("-consulta etiquetas " + consulta.toString());
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta.toString());
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
                salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getEstanteAlmacen().setCodEstante(rs.getInt("cod_estante"));
                salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getEstanteAlmacen().setNombreEstante(rs.getString("nombre_estante"));
                salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().setFila(rs.getString("fila"));
                salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().setColumna(rs.getString("columna"));

                salidasAlmacenDetalleIngreso.setCantidad(rs.getFloat("cantidad_r"));
                salidasAlmacenDetalleIngreso.setCantidadDisponible(rs.getFloat("cantidad_r"));
                //salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getMateriales().getGrupos().setCodGrupo(rs.getInt("cod_grupo"));
                //salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getIngresosAlmacenDetalle().getIngresosAlmacen().getDevoluciones().setCodDevolucion(rs.getInt("cod_devolucion"));
                //salidasAlmacenDetalleIngreso.setColorFila(rs.getInt("cod_devolucion")>0?"#E0FFD6":"");
                salidasAlmacenDetalleIngreso.setCantidadRestanteValorado(rs.getFloat("cantidad_restante_valorada"));
                salidasAlmacenDetalleIngreso.setValoracionCCPorcentual(rs.getFloat("valoracion_cc_porcentual"));
                if (Utiles.redondear(salidasAlmacenDetalleIngreso.getCantidadDisponible(), 2) > 0) {
                    salidasAlmacenDetalle.getSalidasAlmacenDetalleIngresoList().add(salidasAlmacenDetalleIngreso);
                    salidasAlmacenDetalle.setCantidadDisponible(salidasAlmacenDetalle.getCantidadDisponible() + salidasAlmacenDetalleIngreso.getCantidadDisponible());
                }
            }
            rs.close();
            st.close();
            con.close();

        } catch (SQLException ex) {
            LOGGER.warn(ex);
        } catch (Exception e) {
            LOGGER.warn(e);
        }
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
            LOGGER.info("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            if (rs.next()) {
                transaccionCerradaAlmacen = true;
            }

        } catch (Exception e) {
            LOGGER.warn(e);
        }
        return transaccionCerradaAlmacen;
    }

    public boolean detalleItems(List salidasAlmacenDetalleList) {
        boolean itemsDetallados = true;
        try {
            Iterator i = salidasAlmacenDetalleList.iterator();
            while (i.hasNext()) {
                SalidasAlmacenDetalle salidasAlmacenDetalleItem = (SalidasAlmacenDetalle) i.next();
                //LOGGER.info("size: " + salidasAlmacenDetalleItem.getSalidasAlmacenDetalleIngresoList().size());
                if (salidasAlmacenDetalleItem.getSalidasAlmacenDetalleIngresoList().size() == 0) {
                    itemsDetallados = false;
                }
            }

        } catch (Exception e) {
            LOGGER.warn(e);
        }
        return itemsDetallados;
    }

    public String guardarSalidaAlmacen_action(){
        this.transaccionExitosa = false;
        Connection con = null;
        try {
            con = Util.openConnection(con);
            ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            if (usuario.getAlmacenesGlobal().getCodAlmacen() == 2 && !materialesPermitido(salidasAlmacenDetalleList) && salidasAlmacen.getTiposSalidasAlmacen().getCodTipoSalidaAlmacen() != 8 && salidasAlmacen.getTiposSalidasAlmacen().getCodTipoSalidaAlmacen() != 10) {
                ManagedRegistroSolicitudSalidaAlmacen bean = new ManagedRegistroSolicitudSalidaAlmacen();
                if (!salidasAlmacen.getCodLoteProduccion().equals("")) {
                    if (!(bean.verificarSalidasEpMp(salidasAlmacen.getComponentesProd().getCodCompprod(), salidasAlmacen.getCodLoteProduccion()))) {
                        this.mostrarMensajeTransaccionFallida("No se pueden realizar salidas de este lote y producto porque no se han generado salidas de empaque primario y materia prima para el mismo");
                        return null;
                    }
                }
            }
            

            if (this.verificaTransaccionCerradaAlmacen() == true) {
                this.mostrarMensajeTransaccionFallida("Las transacciones para este mes fueron cerradas");
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
            //inicio ale verificar salida
            if (salidasAlmacen.getAreasEmpresa().getCodAreaEmpresa().equals("0")) {
                this.mostrarMensajeTransaccionFallida("Debe de seleccionar el Area/Departamento destino");
                return null;
            }
            LOGGER.debug("-------------------------------------------INICIO REGISTRAR SALIDA ALMACEN--------------------------------------------");
            con.setAutoCommit(false);
            salidasAlmacen.setCodSalidaAlmacen(this.generaCodSalidaAlmacen());
            salidasAlmacen.setNroSalidaAlmacen(this.generaNroIngresoAlmacen());
            if (!salidasAlmacen.getCodLoteProduccion().equals("")) {
                salidasAlmacen.getAreasEmpresa().setCodAreaEmpresa(this.verificaAreaProduccion(salidasAlmacen.getComponentesProd().getCodCompprod()));
                //verificar si el producto es de desarrollo si si entonces salidas por nota de entrega
                String consulta1 = "select c.COD_TIPO_PRODUCCION, c.PRODUCTO_SEMITERMINADO from COMPONENTES_PROD c where c.COD_COMPPROD='" + salidasAlmacen.getComponentesProd().getCodCompprod() + "'";
                ResultSet resProd = st.executeQuery(consulta1);
                if (resProd.next()) {
                    //if (resProd.getInt("COD_TIPO_PRODUCCION") == 2 || resProd.getInt("PRODUCTO_SEMITERMINADO") == 1) {

                    if (resProd.getInt("PRODUCTO_SEMITERMINADO") == 1) {
                        salidasAlmacen.getTiposSalidasAlmacen().setCodTipoSalidaAlmacen(7);//por nota de entrega
                    }
                }
            }
            if (!emptyListEmpaqueSecundario) {
                LOGGER.info("J: Sin materiales de ES.");
                salidasAlmacen.getPresentacionesProducto().setCodPresentacion("0");
            }
            String consulta = " INSERT INTO SALIDAS_ALMACEN ( COD_GESTION, COD_SALIDA_ALMACEN, COD_ORDEN_PESADA,  COD_FORM_SALIDA, COD_PROD, "
                    + "   COD_TIPO_SALIDA_ALMACEN,  COD_AREA_EMPRESA,   NRO_SALIDA_ALMACEN,   FECHA_SALIDA_ALMACEN,   OBS_SALIDA_ALMACEN, "
                    + "  ESTADO_SISTEMA,   COD_ALMACEN,   COD_ORDEN_COMPRA,   COD_PERSONAL,    COD_ESTADO_SALIDA_ALMACEN,   COD_LOTE_PRODUCCION, "
                    + "   COD_ESTADO_SALIDA_COSTO,   cod_prod_ant,   orden_trabajo,   COD_PRESENTACION,cod_prod1,cod_maquina) VALUES ( "
                    + "  '" + salidasAlmacen.getGestiones().getCodGestion() + "', '" + salidasAlmacen.getCodSalidaAlmacen() + "', '" + salidasAlmacen.getCodOrdenRepesada() + "',"
                    + "  '" + salidasAlmacen.getCodFormSalida() + "','" + salidasAlmacen.getComponentesProd().getCodCompprod() + "', '" + salidasAlmacen.getTiposSalidasAlmacen().getCodTipoSalidaAlmacen() + "', "
                    + "   '" + salidasAlmacen.getAreasEmpresa().getCodAreaEmpresa() + "', '" + salidasAlmacen.getNroSalidaAlmacen() + "'," + "getdate()" + ","
                    + "  '" + salidasAlmacen.getObsSalidaAlmacen() + "',  '" + salidasAlmacen.getEstadoSistema() + "','" + salidasAlmacen.getAlmacenes().getCodAlmacen() + "',"
                    + "  '" + salidasAlmacen.getOrdenesCompra().getCodOrdenCompra() + "', '" + salidasAlmacen.getPersonal().getCodPersonal() + "',  '" + salidasAlmacen.getEstadosSalidasAlmacen().getCodEstadoSalidaAlmacen() + "',"
                    + "  '" + salidasAlmacen.getCodLoteProduccion() + "','" + salidasAlmacen.getCodEstadoSalidaCosto() + "',"
                    + "  '" + salidasAlmacen.getCodProdAnt() + "', '" + salidasAlmacen.getOrdenTrabajo() + "', '" + salidasAlmacen.getPresentacionesProducto().getCodPresentacion() + "','" + salidasAlmacen.getComponentesProd1().getCodCompprod() + "','" + salidasAlmacen.getMaquinaria().getCodMaquina() + "'); ";
            LOGGER.info("consulta " + consulta); 
            PreparedStatement pst = con.prepareStatement(consulta);
            if(pst.executeUpdate() > 0)LOGGER.info("se registro la cabecera salida");
            //iteracion de detalle
            Iterator i = salidasAlmacenDetalleList.iterator();

            while (i.hasNext()) {
                SalidasAlmacenDetalle salidasAlmacenDetalleItem = (SalidasAlmacenDetalle) i.next();
                consulta = " INSERT INTO SALIDAS_ALMACEN_DETALLE ( COD_SALIDA_ALMACEN, COD_MATERIAL, CANTIDAD_SALIDA_ALMACEN, COD_UNIDAD_MEDIDA, "
                        + "  COD_ESTADO_MATERIAL )  VALUES (   '" + salidasAlmacen.getCodSalidaAlmacen() + "', '" + salidasAlmacenDetalleItem.getMateriales().getCodMaterial() + "',"
                        + "  '" + (salidasAlmacenDetalleItem.getTipo_material() != 2 ? salidasAlmacenDetalleItem.getCantidadSalidaAlmacen() : salidasAlmacenDetalleItem.getCantidadSalidaProgProd()) + "',   '" + salidasAlmacenDetalleItem.getUnidadesMedida().getCodUnidadMedida() + "', "
                        + "   '" + salidasAlmacenDetalleItem.getEstadosMaterial().getCodEstadoMaterial() + "' ) ";
                
                LOGGER.info("consulta registrar salida almacen detalle: " + consulta);       
                pst = con.prepareStatement(consulta);
                if(pst.executeUpdate() > 0 )LOGGER.info("se registro salidas almacen detalle");
                Iterator i1 = salidasAlmacenDetalleItem.getSalidasAlmacenDetalleIngresoList().iterator();

                while (i1.hasNext()) {
                    SalidasAlmacenDetalleIngreso salidasAlmacenDetalleIngreso = (SalidasAlmacenDetalleIngreso) i1.next();

                    if (salidasAlmacenDetalleIngreso.getCantidadSacar() > 0) {
                        consulta = "  INSERT INTO SALIDAS_ALMACEN_DETALLE_INGRESO ( COD_SALIDA_ALMACEN,  COD_MATERIAL, COD_INGRESO_ALMACEN,  ETIQUETA, "
                                + " COSTO_SALIDA,   FECHA_VENCIMIENTO,   CANTIDAD,   COSTO_SALIDA_ACTUALIZADO,   FECHA_ACTUALIZACION,   COSTO_SALIDA_ACTUALIZADO_FINAL "
                                + ", COD_ESTANTE, FILA, COLUMNA)  "
                                + " VALUES ( '" + salidasAlmacen.getCodSalidaAlmacen() + "',   '" + salidasAlmacenDetalleItem.getMateriales().getCodMaterial() + "',   "
                                + " '" + salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getIngresosAlmacen().getCodIngresoAlmacen() + "', "
                                + " '" + salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getEtiqueta() + "', '" + salidasAlmacenDetalleIngreso.getCostoSalida() + "',"
                                + (salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getFechaVencimiento() != null? "'" + salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getFechaVencimiento() + "',":"null,")
                                + " '" + (salidasAlmacenDetalleItem.getTipo_material() != 2 ? salidasAlmacenDetalleIngreso.getCantidadSacar() : salidasAlmacenDetalleIngreso.getCantidadProgProd()) + "', '" + salidasAlmacenDetalleIngreso.getCostoSalidaActualizado() + "', "
                                + " '" + sdf.format(salidasAlmacenDetalleIngreso.getFechaActualizacion()) + "',  '" + salidasAlmacenDetalleIngreso.getCostoSalidaActualizadoFinal() + "'"
                                + ",'" + salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getEstanteAlmacen().getCodEstante()
                                + "','" + salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getFila()
                                + "','" + salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getColumna()
                                + "' ) ";
                        LOGGER.info("consulta registrar salida almacen detalle ingreso: " + consulta);                        
                        pst = con.prepareStatement(consulta);
                        if(pst.executeUpdate() > 0)LOGGER.info("se registro salida almacen detalle ingreso");
                        
                        consulta = " update ingresos_almacen_detalle_estado set cantidad_restante=cantidad_restante - " + salidasAlmacenDetalleIngreso.getCantidadSacar() + " "
                                + " where cod_material=" + salidasAlmacenDetalleItem.getMateriales().getCodMaterial() + " and etiqueta=" + salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getEtiqueta() + " "
                                + " and cod_ingreso_almacen='" + salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getIngresosAlmacen().getCodIngresoAlmacen() + "' ";
                        LOGGER.info("consulta actualizar cantidad restante: " + consulta);
                        pst = con.prepareStatement(consulta);
                        if(pst.executeUpdate() > 0)LOGGER.info("se actualizo cantidad restante");
                    }
                }
            }
            //EJECUTAMOS SP DESPUES DE REGISTRO
            consulta = " exec [PAA_REGISTRO_SALIDA_ALMACEN_LOG] " + salidasAlmacen.getCodSalidaAlmacen() 
                    + " , "+ usuario.getUsuarioModuloBean().getCodUsuarioGlobal()
                    + " , 3";
            LOGGER.info("consulta registrar log: " + consulta); 
            pst = con.prepareStatement(consulta);
            if(pst.executeUpdate() > 0)LOGGER.info("se registro salida almacen log: ");
            
            int codEstadoProgramaProceso = 7;
            consulta = " UPDATE PROGRAMA_PRODUCCION  SET COD_ESTADO_PROGRAMA = "+codEstadoProgramaProceso
                    + " WHERE COD_LOTE_PRODUCCION= '" + salidasAlmacen.getCodLoteProduccion() + "' "
                    + " AND COD_ESTADO_PROGRAMA  in (1,2)";
            LOGGER.debug("consulta cambiar programa de produccion: "+consulta);
            pst = con.prepareStatement(consulta);
            if(pst.executeUpdate() > 0){
                LOGGER.info("se actualizo el programa de produccion");
                consulta = "select max(p.COD_PROGRAMA_PROD) as COD_PROGRAMA_PROD"
                        +" from programa_produccion p "
                        +" where p.COD_LOTE_PRODUCCION='"+salidasAlmacen.getCodLoteProduccion()+"'"
                        +" and p.COD_ESTADO_PROGRAMA ="+codEstadoProgramaProceso;
                Statement st1 = con.createStatement();
                ResultSet res1 = st.executeQuery(consulta);
                if(res1.next()){
                    consulta = "exec PAA_REGISTRO_PROGRAMA_PRODUCCION_LOG "
                                +"'" + salidasAlmacen.getCodLoteProduccion() + "',"
                                +res1.getInt("COD_PROGRAMA_PROD")+","
                                +salidasAlmacen.getPersonal().getCodPersonal()+","
                                +"1,"//edicion
                                +"0,"//codigo desviacion
                                +"'cambio a proceso por descarga de almacenes'";
                    LOGGER.debug("consulta registrar log: "+consulta);
                    pst = con.prepareCall(consulta);
                    pst.executeUpdate();
                }
            }
            
            
            consulta = "exec dbo.USP_UPDATE_INGRESOS_ALMACEN_DETALLE_ESTADO_SALDOS " + salidasAlmacen.getCodSalidaAlmacen();
            LOGGER.info(" consulta actualizar saldos: " + consulta);
            pst = con.prepareStatement(consulta);
            if(pst.executeUpdate() > 0)LOGGER.info("se actualizo cantidad");
            this.mostrarMensajeTransaccionExitosa("Se registro satisfactoriamente la salida");
            con.commit();
            st.close();
  
        }
        catch(SQLException ex){
            LOGGER.warn("error", ex);
            this.mostrarMensajeTransaccionFallida("Ocurrio un error al momento de registrar la salida, intente de nuevo");
            this.rollbackConexion(con);
        }
        catch (Exception e) {
            LOGGER.warn("error", e);
            this.mostrarMensajeTransaccionFallida("Ocurrio un error al momento de registrar la salida, intente de nuevo");
            this.rollbackConexion(con);
        } finally {
            this.cerrarConexion(con);
        }
        LOGGER.debug("-------------------------------------------TERMINO REGISTRAR SALIDA ALMACEN--------------------------------------------");
        if(this.transaccionExitosa){
            ThreadCosteoSalidaAlmacen thread = new ThreadCosteoSalidaAlmacen(salidasAlmacenDetalleList, salidasAlmacen);
            thread.start();
        }
        return null;
    }
   

    public void actualizaProgramaProduccion(SalidasAlmacen salidasAlmacen) {
        try {
            String consulta = "  UPDATE PROGRAMA_PRODUCCION  SET COD_ESTADO_PROGRAMA = 7 "
                    + " WHERE COD_LOTE_PRODUCCION= '" + salidasAlmacen.getCodLoteProduccion() + "' "
                    + " AND COD_ESTADO_PROGRAMA  in (1,2)  ";
            LOGGER.info("consulta " + consulta);
            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement();
            st.executeUpdate(consulta);
            
        } catch (Exception e) {
            LOGGER.warn(e);
        }
    }

    public boolean materialesPermitido(List salidasAlmacenDetalleList) {
        Iterator i = salidasAlmacenDetalleList.iterator();
        String codigos = "";
        if (i.hasNext()) {
            SalidasAlmacenDetalle current = (SalidasAlmacenDetalle) i.next();
            codigos += current.getMateriales().getCodMaterial();
        }
        while (i.hasNext()) {
            SalidasAlmacenDetalle current = (SalidasAlmacenDetalle) i.next();
            codigos += "," + current.getMateriales().getCodMaterial();
        }
        String consulta = "select top 1 * from materiales m inner join grupos g on m.COD_GRUPO=g.COD_GRUPO "
                + " inner join capitulos c on c.COD_CAPITULO=g.COD_CAPITULO where c.COD_CAPITULO not in(2,3,4) and m.COD_MATERIAL in (" + codigos + ")";
        LOGGER.info("consulta verificar material limpieza " + consulta);
        boolean permitido = false;
        try {
            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet res = st.executeQuery(consulta);
            permitido = res.next();
            res.close();
            st.close();
            con.close();
        } catch (SQLException ex) {
            LOGGER.warn(ex);
        }
        return permitido;
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
            LOGGER.warn(e);
        }
        return cantidadValidada;
    }

    public int generaCodSalidaAlmacen() {
        int codSalidaAlmacen = 0;
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select isnull(max(s.COD_SALIDA_ALMACEN),0)+1 COD_SALIDA_ALMACEN from SALIDAS_ALMACEN s  ";
            LOGGER.info("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            if (rs.next()) {
                codSalidaAlmacen = rs.getInt("COD_SALIDA_ALMACEN");
            }

        } catch (Exception e) {
            LOGGER.warn(e);
        }
        return codSalidaAlmacen;
    }

    public String eliminarDetalle_action() {
        mensaje="";
        try {
            List auxList = new ArrayList();
            Iterator i = salidasAlmacenDetalleList.iterator();
            while (i.hasNext()) {
                SalidasAlmacenDetalle salidasAlmacenDetalleItem = (SalidasAlmacenDetalle) i.next();
                if (salidasAlmacenDetalleItem.getChecked() && salidasAlmacenDetalleItem.getIngresoAlmacenDetalleTraspaso().getCantTotalIngresoFisico()!=0f) {
                    mensaje="Este registro no puede ser eliminado ya que tiene un ingreso generado en el almacen destino.";
                    return null;
                }
                if (salidasAlmacenDetalleItem.getChecked() != true) {
                    auxList.add(salidasAlmacenDetalleItem);
                }
            }
            salidasAlmacenDetalleList = auxList;

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
            String consulta = " select c.COD_CAPITULO,c.NOMBRE_CAPITULO  from CAPITULOS c where c.COD_ESTADO_REGISTRO = 1 order by c.NOMBRE_CAPITULO";

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
            LOGGER.warn(e);
        }
        return capitulosList;
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
            LOGGER.warn(e);
        }
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
            LOGGER.info("consulta" + consulta);

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
            LOGGER.warn(e);
        }
        return materialesList;
    }

    public String grupos_change() {
        materialesList = this.cargarMateriales(salidasAlmacenDetalleAgregar.getMateriales().getGrupos().getCodGrupo());
        return null;
    }

    public String agregarSalidaAlmacenDetalle_action() {
        try {
            salidasAlmacenDetalleAgregar = new SalidasAlmacenDetalle();
            salidasAlmacenDetalleAgregar.getEstadosMaterial().setCodEstadoMaterial(2); //estado aprobado

        } catch (Exception e) {
            LOGGER.warn(e);
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
            //inicio ale dejar validacion rechazados
            ManagedAccesoSistema user = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            if (user.getAlmacenesGlobal().getCodAlmacen() != 12 && user.getAlmacenesGlobal().getCodAlmacen() != 8 && user.getAlmacenesGlobal().getCodAlmacen() != 7) {
                if (ingresosAlmacenDetalleEstado.getEstadosMaterial().getCodEstadoMaterial() != 2 && ingresosAlmacenDetalleEstado.getEstadosMaterial().getCodEstadoMaterial() != 6) {
                    mensaje = "no se puede registrar salidas con materiales con el estado seleccionado ";
                    return null;
                }
            }

            salidasAlmacenDetalleAgregar.setChecked(false);
            salidasAlmacenDetalleAgregar.setSalidasAlmacen(salidasAlmacen);
            this.etiquetasSalidaAlmacen(salidasAlmacenDetalleAgregar);
            this.cantidadReanalisisItemPrioritario(salidasAlmacenDetalleAgregar);

            if (salidasAlmacenDetalleAgregar.getCantidadSalidaAlmacen() > salidasAlmacenDetalleAgregar.getCantidadDisponible() || salidasAlmacenDetalleAgregar.getCantidadExistenciaReanalisis() > 0) {
                salidasAlmacenDetalleAgregar.setColorFila("#FF0000");
            } else {
                salidasAlmacenDetalleAgregar.setColorFila("#00FF00");
            }
            LOGGER.info("cod grupo " + salidasAlmacenDetalleAgregar.getMateriales().getGrupos().getCodGrupo());
            LOGGER.info("->cod tipo salida "+salidasAlmacen.getTiposSalidasAlmacen().getCodTipoSalidaAlmacen());
            if (salidasAlmacenDetalleAgregar.getMateriales().getGrupos().getCodGrupo() == 23 && 
                    salidasAlmacen.getTiposSalidasAlmacen().getCodTipoSalidaAlmacen() != 8 &&
                    salidasAlmacen.getTiposSalidasAlmacen().getCodTipoSalidaAlmacen() != 7 &&
                    salidasAlmacen.getTiposSalidasAlmacen().getCodTipoSalidaAlmacen() != 17) {
                this.detalleEtiquetasSalidaAlmacenInyectables(salidasAlmacenDetalleAgregar);
            } else {
                this.detalleEtiquetasSalidaAlmacen(salidasAlmacenDetalleAgregar);
            }

            salidasAlmacenDetalleList.add(salidasAlmacenDetalleAgregar);

        } catch (Exception e) {
            LOGGER.warn(e);
        }
        return null;
    }

    public void detalleEtiquetasSalidaAlmacen(SalidasAlmacenDetalle salidasAlmacenDetalleItem) {
        try {

            float cantidadSalidaAlmacen = salidasAlmacenDetalleItem.getCantidadSalidaAlmacen();
            double cantidadSalidaProgProd = salidasAlmacenDetalleItem.getCantidadSalidaProgProd();
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

                if (cantidadSalidaProgProd > 0) {
                    if (cantidadSalidaProgProd >= salidasAlmacenDetalleIngreso.getCantidadDisponible()) {
                        salidasAlmacenDetalleIngreso.setCantidadProgProd(salidasAlmacenDetalleIngreso.getCantidadDisponible());
                        cantidadSalidaProgProd = cantidadSalidaProgProd - salidasAlmacenDetalleIngreso.getCantidadDisponible();
                    } else {
                        salidasAlmacenDetalleIngreso.setCantidadProgProd(cantidadSalidaProgProd);
                        cantidadSalidaProgProd = cantidadSalidaProgProd - salidasAlmacenDetalleIngreso.getCantidadDisponible();
                    }
                } else {
                    salidasAlmacenDetalleIngreso.setCantidadProgProd(0);
                }

            }

        } catch (Exception e) {
            LOGGER.warn(e);
        }
    }

    public void detalleEtiquetasSalidaAlmacenProduccion(SalidasAlmacenDetalle salidasAlmacenDetalleItem) {
        try {

            float cantidadSalidaAlmacen = salidasAlmacenDetalleItem.getCantidadSalidaAlmacen();
            //Iterator i = salidasAlmacenDetalleItem.getSalidasAlmacenDetalleIngresoList().iterator();
            for (SalidasAlmacenDetalleIngreso salidasAlmacenDetalleIngreso : (List<SalidasAlmacenDetalleIngreso>) salidasAlmacenDetalleItem.getSalidasAlmacenDetalleIngresoList()) {
                //SalidasAlmacenDetalleIngreso salidasAlmacenDetalleIngreso = (SalidasAlmacenDetalleIngreso) i.next();
                if (cantidadSalidaAlmacen > 0) {
                    if (cantidadSalidaAlmacen >= salidasAlmacenDetalleIngreso.getCantidadRestanteValorado()) {
                        salidasAlmacenDetalleIngreso.setCantidadSacar(salidasAlmacenDetalleIngreso.getCantidadDisponible());
                        cantidadSalidaAlmacen = cantidadSalidaAlmacen - salidasAlmacenDetalleIngreso.getCantidadRestanteValorado();
                    } else {
                        float cantidad_valorada = (cantidadSalidaAlmacen * (float) 100) / salidasAlmacenDetalleIngreso.getValoracionCCPorcentual();
                        //LOGGER.info("J cantidad_valorada_ "+cantidad_valorada);
                        salidasAlmacenDetalleIngreso.setCantidadSacar(cantidad_valorada);
                        cantidadSalidaAlmacen = cantidadSalidaAlmacen - salidasAlmacenDetalleIngreso.getCantidadDisponible();
                    }
                } else {
                    salidasAlmacenDetalleIngreso.setCantidadSacar(0);
                }
                //LOGGER.info("J cantidad sacar: "+salidasAlmacenDetalleIngreso.getCantidadSacar());
            }

        } catch (Exception e) {
            LOGGER.warn(e);
        }
    }

    public void detalleEtiquetasSalidaAlmacenInyectables(SalidasAlmacenDetalle salidasAlmacenDetalleItem) {
        try {

            float cantidadSalidaAlmacen = salidasAlmacenDetalleItem.getCantidadSalidaAlmacen();
            salidasAlmacenDetalleItem.setCantidadSalidaAlmacen(0);
            Iterator i = salidasAlmacenDetalleItem.getSalidasAlmacenDetalleIngresoList().iterator();
            while (i.hasNext()) {
                LOGGER.info("regoistro 21");
                SalidasAlmacenDetalleIngreso salidasAlmacenDetalleIngreso = (SalidasAlmacenDetalleIngreso) i.next();
                LOGGER.info("comparacionsssssssssss " + salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getLoteMaterialProveedor() + " " + salidasAlmacen.getCodLoteProduccion());
                if (salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getLoteMaterialProveedor().equals(salidasAlmacen.getCodLoteProduccion().split("-")[0])) {
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
                    salidasAlmacenDetalleItem.setCantidadSalidaAlmacen(salidasAlmacenDetalleItem.getCantidadSalidaAlmacen() + salidasAlmacenDetalleIngreso.getCantidadSacar());//actualizacion de la cantidad a sacar

                }
            }
            if (salidasAlmacenDetalleItem.getCantidadSalidaAlmacen() == 0) {

                mensajeInyectables = "no existen lotes para generar la salida ";

            }
        } catch (Exception e) {
            LOGGER.warn(e);
        }
    }

    public String detallarCantidadSalida_action() 
    {
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
            ManagedAccesoSistema usuarioAlmacen = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            DaoEstadosMaterial daoEstadosMaterial = new DaoEstadosMaterial(LOGGER);
            estadosMaterialExistencia = daoEstadosMaterial.listarExistencias(usuarioAlmacen.getAlmacenesGlobal().getCodAlmacen(), salidasAlmacenDetalleAgregar.getMateriales().getCodMaterial());
            float cantidadDisponible=0;
            for(EstadosMaterial bean : estadosMaterialExistencia){
                if(bean.getEstadoSalidaPermitido())
                {
                    cantidadDisponible += bean.getCantidadExistente();
                }
            }
            salidasAlmacenDetalleAgregar.setCantidadDisponible(cantidadDisponible);
            String consulta = " select m.COD_MATERIAL,m.NOMBRE_MATERIAL,u.COD_UNIDAD_MEDIDA,u.NOMBRE_UNIDAD_MEDIDA,u.ABREVIATURA "
                    + " from materiales m  "
                    + " inner join UNIDADES_MEDIDA u on m.COD_UNIDAD_MEDIDA = u.COD_UNIDAD_MEDIDA "
                    + " where m.cod_material = '" + salidasAlmacenDetalleAgregar.getMateriales().getCodMaterial() + "' ";
            LOGGER.info("consulta" + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            if (rs.next()) {
                salidasAlmacenDetalleAgregar.getMateriales().setNombreMaterial(rs.getString("NOMBRE_MATERIAL"));
                salidasAlmacenDetalleAgregar.getMateriales().getUnidadesMedida().setCodUnidadMedida(rs.getInt("COD_UNIDAD_MEDIDA"));
                salidasAlmacenDetalleAgregar.getUnidadesMedida().setCodUnidadMedida(rs.getInt("COD_UNIDAD_MEDIDA"));
                salidasAlmacenDetalleAgregar.getUnidadesMedida().setNombreUnidadMedida(rs.getString("NOMBRE_UNIDAD_MEDIDA"));
                salidasAlmacenDetalleAgregar.getUnidadesMedida().setAbreviatura(rs.getString("ABREVIATURA"));
            }

            ingresosAlmacenDetalleEstado.getEstadosMaterial().setCodEstadoMaterial(2); // en estado aprobado para la busqueda de cantidad disponible
            LOGGER.info("cantidad_disponible : " + salidasAlmacenDetalleAgregar.getCantidadDisponible());
            salidasAlmacenDetalleAgregar.setEquivalencias(this.buscaEquivalencia(salidasAlmacenDetalleAgregar));
            LOGGER.info("termino 1");
            if (rs != null) {
                rs.close();
                st.close();
                con.close();
            }

        } catch (Exception e) {
            LOGGER.warn(e);
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

            LOGGER.info("consulta" + consulta);
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
            LOGGER.warn(e);
        }
        return equivalencias;
    }

    public String guardarSalidasAlmacenDetalleIngreso_action() {
        this.transaccionExitosa = false;
        try {
            int materialesLoteInyectable = 1;
            Iterator i = salidasAlmacenDetalle.getSalidasAlmacenDetalleIngresoList().iterator();
            float cantidadSacar = 0;
            while (i.hasNext()) {
                SalidasAlmacenDetalleIngreso salidasAlmacenDetalleEstado = (SalidasAlmacenDetalleIngreso) i.next();
                cantidadSacar = cantidadSacar + salidasAlmacenDetalleEstado.getCantidadSacar();
                if (!salidasAlmacenDetalleEstado.getIngresosAlmacenDetalleEstado().getLoteMaterialProveedor().equals(salidasAlmacen.getCodLoteProduccion())
                        && salidasAlmacenDetalleEstado.getCantidadSacar() > 0 && salidasAlmacenDetalleEstado.getIngresosAlmacenDetalleEstado().getMateriales().getGrupos().getCodGrupo() == 23) {
                    materialesLoteInyectable = 0;
                }
                
            }
            if(salidasAlmacen.getCodLoteProduccion().length()<2 && salidasAlmacen.getTiposSalidasAlmacen().getCodTipoSalidaAlmacen() == 8)
            {
                LOGGER.info("no aplica regla de lote de ampolla a salidas por ajuste");
                materialesLoteInyectable = 1;
            }
            this.transaccionExitosa = true;
            if (cantidadSacar != salidasAlmacenDetalle.getCantidadSalidaAlmacen()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("la sumatoria de las cantidades parciales no es igual a la cantidad de salida"));
                this.transaccionExitosa = false;
            }
            if (materialesLoteInyectable == 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("existen lotes que no corresponden al lote de salida de almacén"));
                this.transaccionExitosa = false;
            }

        } catch (Exception e) {
            LOGGER.warn(e);
        }
        return null;
    }
    public String eliminarDetalleSalidaAlmacenAction(){
        salidasAlmacenDetalleList.remove(salidasAlmacenDetalle);
        return null;
    }
    public String editarCantidadSalidaAlmacenAction(){
        if (salidasAlmacenDetalle.getEquivalencias().getValorEquivalencia() > 0) {
            salidasAlmacenDetalle.setCantidadSalidaAlmacenEquivalente(salidasAlmacenDetalle.getCantidadSalidaAlmacen()
                    / salidasAlmacenDetalle.getEquivalencias().getValorEquivalencia());
        }
        return null;
    }

    public String editarCantidadSalidasAlmacenDetalle_action() {
        mensaje="";
        try {
            Iterator i = salidasAlmacenDetalleList.iterator();
            while (i.hasNext()) {
                SalidasAlmacenDetalle salidasAlmacenDetalleItem = (SalidasAlmacenDetalle) i.next();
                if (salidasAlmacenDetalleItem.getChecked() && salidasAlmacenDetalleItem.getIngresoAlmacenDetalleTraspaso().getCantTotalIngresoFisico() != 0f) {
                    mensaje="Este registro no puede ser editado ya que tiene un ingreso generado en el almacen destino.";
                    return null;
                }
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
            LOGGER.warn(e);
        }
        return null;
    }

    public String guardarEdicionCantidadMaterial_action() {
        LOGGER.info("fprma J" + salidasAlmacen.getComponentesProd().getForma().getCodForma());
        salidasAlmacenDetalle.setCantidadSalidaProgProd(salidasAlmacenDetalle.getCantidadSalidaAlmacen());
        try {
            if (salidasAlmacenDetalle.getMateriales().getGrupos().getCodGrupo() == 23 && salidasAlmacen.getTiposSalidasAlmacen().getCodTipoSalidaAlmacen() !=8 ) {
                int codForma = 0;
                try {
                    Connection con1 = null;
                    con1 = Util.openConnection(con1);
                    Statement st = con1.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    String consulta = "select cp.COD_FORMA from COMPONENTES_PROD cp where cp.COD_COMPPROD='" + salidasAlmacen.getComponentesProd().getCodCompprod() + "'";
                    LOGGER.info("consulta cargar forma " + consulta);
                    ResultSet res = st.executeQuery(consulta);
                    if (res.next()) {
                        codForma = res.getInt("COD_FORMA");
                    }
                    st.close();
                    con1.close();
                } catch (SQLException ex) {
                    LOGGER.warn(ex);
                }
                if (codForma == 2) {
                    this.detalleEtiquetasSalidaAlmacenInyectables(salidasAlmacenDetalle);
                } else {
                    this.detalleEtiquetasSalidaAlmacen(salidasAlmacenDetalle);
                }
            } else {
                LOGGER.info("else J " + salidasAlmacenDetalle.getCantidadSalidaAlmacen() + "  " + salidasAlmacenDetalle.getCantidadDisponible() + " " + salidasAlmacenDetalle.getCantidadSalidaProgProd());
                this.detalleEtiquetasSalidaAlmacen(salidasAlmacenDetalle);
            }

            if (salidasAlmacenDetalle.getCantidadSalidaAlmacen() > salidasAlmacenDetalle.getCantidadDisponible()) {
                salidasAlmacenDetalle.setColorFila("#FF0000");
            } else {
                salidasAlmacenDetalle.setColorFila("#00FF00");
            }

        } catch (Exception e) {
            LOGGER.warn(e);
        }
        return null;
    }

    public String capitulos_change() {
        try {
            gruposList = this.cargarGrupos(buscarMaterial.getGrupos().getCapitulos().getCodCapitulo());

        } catch (Exception e) {
            LOGGER.warn(e);
        }
        return null;
    }

    public List cargarGrupos(int codCapitulo) {
        List gruposList = new ArrayList();
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " SELECT GR.COD_GRUPO,GR.NOMBRE_GRUPO FROM GRUPOS GR WHERE GR.COD_CAPITULO = '" + codCapitulo + "' AND GR.COD_ESTADO_REGISTRO = 1 ";
            LOGGER.info("consulta " + consulta);
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
            LOGGER.warn(e);
        }
        return gruposList;
    }

    public String buscarMaterial_action() {
        estadosMaterialExistencia=new ArrayList<EstadosMaterial>();
        try {
            Connection con = null;
            con = Util.openConnection(con);
            ManagedAccesoSistema managed=(ManagedAccesoSistema)Util.getSessionBean("ManagedAccesoSistema");
            String consulta = " select m.COD_MATERIAL,m.NOMBRE_MATERIAL,gr.cod_grupo,gr.NOMBRE_GRUPO,cap.cod_capitulo,cap.NOMBRE_CAPITULO,"
                    + " m.COD_UNIDAD_MEDIDA,u1.ABREVIATURA ABREVIATURA1 ,m.COD_UNIDAD_MEDIDA_COMPRA,u2.ABREVIATURA ABREVIATURA2  "
                    + " from MATERIALES m  "
                    + " inner join GRUPOS gr on gr.COD_GRUPO = m.COD_GRUPO "
                    + " inner join CAPITULOS cap on cap.COD_CAPITULO = gr.COD_CAPITULO"
                    + " inner join UNIDADES_MEDIDA u1 on u1.COD_UNIDAD_MEDIDA = m.COD_UNIDAD_MEDIDA "
                    + " inner join UNIDADES_MEDIDA u2 on u2.COD_UNIDAD_MEDIDA = m.COD_UNIDAD_MEDIDA_COMPRA  "
                    + " inner join CONFIGURACION_SALIDA_MATERIAL_PERMITIDO csmp on csmp.COD_ESTADO_REGISTRO=m.COD_ESTADO_REGISTRO"
                        + " and csmp.MOVIMIENTO_ITEM=m.MOVIMIENTO_ITEM"
                        + " and csmp.COD_ALMACEN="+(managed.getAlmacenesGlobal().getCodAlmacen())
                    + " where m.NOMBRE_MATERIAL like '%" + buscarMaterial.getNombreMaterial() + "%' ";
                    if(salidasAlmacenDetalleList != null && salidasAlmacenDetalleList.size() > 0){
                        consulta += " and m.COD_MATERIAL NOT IN (0";
                        for(SalidasAlmacenDetalle detalle :(List<SalidasAlmacenDetalle>)salidasAlmacenDetalleList){
                            consulta+=","+detalle.getMateriales().getCodMaterial();
                        }
                        consulta+=")";
                    }
                        
            if (buscarMaterial.getGrupos().getCodGrupo() > 0) {
                consulta = consulta + " and gr.COD_GRUPO = '" + buscarMaterial.getGrupos().getCodGrupo() + "' ";
            }
            if (buscarMaterial.getGrupos().getCapitulos().getCodCapitulo() > 0) {
                consulta = consulta + "  and cap.COD_CAPITULO = '" + buscarMaterial.getGrupos().getCapitulos().getCodCapitulo() + "' ";
            }
            LOGGER.info("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            materialesBuscarList.clear();
            while (rs.next()) {
                Materiales materialesItem = new Materiales();
                materialesItem.setCodMaterial(rs.getString("COD_MATERIAL"));
                materialesItem.setNombreMaterial(rs.getString("NOMBRE_MATERIAL"));
                materialesItem.getGrupos().setCodGrupo(rs.getInt("COD_GRUPO"));
                materialesItem.getGrupos().setNombreGrupo(rs.getString("NOMBRE_GRUPO"));
                materialesItem.getGrupos().getCapitulos().setNombreCapitulo(rs.getString("NOMBRE_CAPITULO"));
                materialesItem.getGrupos().getCapitulos().setCodCapitulo(rs.getInt("COD_CAPITULO"));
                materialesItem.getUnidadesMedida().setCodUnidadMedida(rs.getInt("COD_UNIDAD_MEDIDA"));
                materialesItem.getUnidadesMedida().setAbreviatura(rs.getString("ABREVIATURA1"));
                materialesItem.getUnidadesMedidaCompra().setCodUnidadMedida(rs.getInt("COD_UNIDAD_MEDIDA_COMPRA"));
                materialesItem.getUnidadesMedidaCompra().setAbreviatura(rs.getString("ABREVIATURA2"));

                materialesBuscarList.add(materialesItem);
            }
            rs.close();
            con.close();

        } catch (Exception e) {
            LOGGER.warn(e);
        }
        return null;
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
            LOGGER.warn(e);
        }
        return itemsSalidaSalidaAlmacen;

    }

    public String seleccionarMaterial_action() {

        try {

            // se agrega el detalle de ingreso almacen
//
//            Materiales materiales = (Materiales) materialesBuscarDataTable.getRowData();
//            IngresosAlmacenDetalle ingresosAlmacenDetalleItem = new IngresosAlmacenDetalle();
//            ingresosAlmacenDetalleItem.setMateriales(materiales);
//
//            //se colocan los datos de unidad medida compra del material
//            ingresosAlmacenDetalleItem.getOrdenesCompraDetalle().getUnidadesMedida().setCodUnidadMedida(materiales.getUnidadesMedidaCompra().getCodUnidadMedida());
//            ingresosAlmacenDetalleItem.getOrdenesCompraDetalle().getUnidadesMedida().setAbreviatura(materiales.getUnidadesMedidaCompra().getAbreviatura());
//
//
//            ingresosAlmacenDetalleItem.getUnidadesMedida().setCodUnidadMedida(materiales.getUnidadesMedida().getCodUnidadMedida());
//            ingresosAlmacenDetalleItem.getUnidadesMedida().setAbreviatura(materiales.getUnidadesMedida().getAbreviatura());
//
//
//
//            ingresosAlmacenDetalleItem.setUnidadesMedidaEquivalencia(materiales.getUnidadesMedida());
//
//            ingresosAlmacenDetalleItem.setSecciones(this.buscaSeccionAlmacen(ingresosAlmacenDetalleItem));
//
//
//
//            Equivalencias equivalencias = this.buscaEquivalencia(ingresosAlmacenDetalleItem);
//            ingresosAlmacenDetalleItem.setValorEquivalencia(equivalencias.getValorEquivalencia());
//            //ingresosAlmacenDetalleItem.setUnidadesMedida(equivalencias.getUnidadesMedida2());
//
//
//            ingresosAlmacenDetalleList.add(ingresosAlmacenDetalleItem);
        } catch (Exception e) {
            LOGGER.warn(e);
        }
        return null;
    }

    public String agregarItem_action() {
        try {
            estadosMaterialExistencia=new ArrayList<EstadosMaterial>();
            materialesBuscarList.clear();
            buscarMaterial.setNombreMaterial("");
            ingresosAlmacenDetalleEstado = new IngresosAlmacenDetalleEstado();
            salidasAlmacenDetalleAgregar = new SalidasAlmacenDetalle();
        } catch (Exception e) {
            LOGGER.warn(e);
        }
        return null;
    }
    public String estadoMaterialAgregar_change() {
        try {

            NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
            DecimalFormat form = (DecimalFormat) nf;
            form.applyPattern("#.###");

            LOGGER.info("cantidad_disponible : " + salidasAlmacenDetalleAgregar.getCantidadDisponible());
            salidasAlmacenDetalleAgregar.setEquivalencias(this.buscaEquivalencia(salidasAlmacenDetalleAgregar));

        } catch (Exception e) {
            LOGGER.warn(e);
        }
        return null;
    }

    public String estadoMaterial_change() {
        try {

            NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
            DecimalFormat form = (DecimalFormat) nf;
            form.applyPattern("#.###");

            salidasAlmacenDetalleAgregar.setCantidadDisponible(Float.valueOf(form.format(this.cantidadDisponibleMaterial(salidasAlmacenDetalleAgregar))));

            LOGGER.info("cantidad_disponible : " + salidasAlmacenDetalleAgregar.getCantidadDisponible());
            salidasAlmacenDetalleAgregar.setEquivalencias(this.buscaEquivalencia(salidasAlmacenDetalleAgregar));

        } catch (Exception e) {
            LOGGER.warn(e);
        }
        return null;
    }

    public List cargarEstadosMaterial() {
        List estadosMaterialList = new ArrayList();
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select cod_estado_material, nombre_estado_material "
                    + " from estados_material where cod_estado_registro=1 and cod_estado_material<>7 ";

            LOGGER.info("consulta " + consulta);
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
            LOGGER.warn(e);
        }
        return estadosMaterialList;
    }

    //inicio ale validacion
    public String verificarLote() {
        try {
            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement();
            String consulta = "select top 1 sa.COD_PROD from SALIDAS_ALMACEN sa where sa.COD_LOTE_PRODUCCION='" + salidasAlmacen.getCodLoteProduccion() + "' and len(sa.COD_LOTE_PRODUCCION)>0 and sa.estado_sistema = 1 and sa.cod_estado_salida_almacen = 1";

            LOGGER.info("consulta verificar lote produccion" + consulta);
            ResultSet rs = st.executeQuery(consulta);
            setProductoHabilitado(false);
            if (rs.next()) {
                salidasAlmacen.getComponentesProd().setCodCompprod(rs.getString("COD_PROD"));
                setProductoHabilitado(true);
            }
            rs.close();
            st.close();
            con.close();

            //}
        } catch (Exception e) {
            LOGGER.warn(e);
        }
        return null;
    }

    public String verificarSalidasLote() {
        try {
            Connection con = null;
            con = Util.openConnection(con);
            int count_reg = 0;
            Statement st = con.createStatement();
            ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            setAlertaSalidasLote("");
            String nroSalidas = "";

            String sql_reg = "select s.NRO_SALIDA_ALMACEN,s.cod_salida_almacen from SALIDAS_ALMACEN s where s.COD_LOTE_PRODUCCION =  '" + salidasAlmacen.getCodLoteProduccion() + "' "
                    + //String sql_reg="select count (s.NRO_SALIDA_ALMACEN) from SALIDAS_ALMACEN s where s.COD_LOTE_PRODUCCION =  '"+programaProduccion.getCodLoteProduccion()+"' " +
                    " and s.COD_ESTADO_SALIDA_ALMACEN <> 2 and s.cod_gestion='" + usuario.getGestionesGlobal().getCodGestion() + "'"
                    + " and s.COD_ALMACEN='" + usuario.getAlmacenesGlobal().getCodAlmacen() + "'";
            LOGGER.info("sql_reg : " + sql_reg);
            ResultSet rsReg = st.executeQuery(sql_reg);
            while (rsReg.next()) {
                nroSalidas += "<a style='cursor:hand;text-decoration:underline;color:blue;font-size:15px' target=\"_blank\" href=\"navegadorSalidasAlmacenReporte.jsf?codigo=" + rsReg.getString(2) + "\" >" + rsReg.getString(1) + "</a>,";
                count_reg++;
            }
            LOGGER.info("count_reg :" + count_reg);
            if (count_reg > 0) {
                setAlertaSalidasLote("  YA SE REALIZARON SALIDAS PARA ESTE NRO DE LOTE !!!!  LAS SALIDAS SON LAS SIGUIENTES : " + nroSalidas + " ");
                LOGGER.info("alertaSalidasLote :" + getAlertaSalidasLote());
            }
            con.close();

            //}
        } catch (Exception e) {
            LOGGER.warn(e);
        }
        return null;
    }

    //final ale validacion
    public String nroLote_change() {
        //inicio ale validacion
        mensaje = "";
        //if(salidasAlmacen.getCodLoteProduccion().length()==1)
        //     {
        //final ale validacion
        try {
            //this.nroLote_onblur();
            mensaje = "";
            //if(salidasAlmacen.getCodLoteProduccion().length()==1){
            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement();
            String consulta = " select a.COD_AREA_EMPRESA,a.NOMBRE_AREA_EMPRESA,count(*) "
                    + " from FORMAS_FARMACEUTICAS_LOTE f  "
                    + " inner join componentes_prod cp on cp.COD_FORMA = f.COD_FORMA "
                    + " inner join AREAS_EMPRESA a on a.COD_AREA_EMPRESA = cp.COD_AREA_EMPRESA "
                    + " where f.COD_LOTE_FORMA = '" + salidasAlmacen.getCodLoteProduccion().substring(0, 1) + "' "
                    + " group by a.cod_area_empresa, a.nombre_area_empresa order by count(*) desc ";
            LOGGER.info("consulta lote forma farmaceutica area empresa " + consulta);
            ResultSet rs = st.executeQuery(consulta);
            if (rs.next()) {
                if (!rs.getString("COD_AREA_EMPRESA").equals(salidasAlmacen.getAreasEmpresa().getCodAreaEmpresa())) {
                    mensaje = "el codigo de lote pertenece al area de " + rs.getString("NOMBRE_AREA_EMPRESA");
                    salidasAlmacen.getAreasEmpresa().setCodAreaEmpresa(rs.getString("COD_AREA_EMPRESA"));
                    salidasAlmacen.getAreasEmpresa().setNombreAreaEmpresa(rs.getString("NOMBRE_AREA_EMPRESA"));
                }
            }
            rs.close();
            st.close();
            con.close();

            //}
        } catch (Exception e) {
            LOGGER.warn(e);
        }
        //  }
        this.cargarMaquinarias(salidasAlmacen.getAreasEmpresa().getCodAreaEmpresa());
        this.verificarLote();
        this.verificarSalidasLote();
        return null;
    }

    public String nroLote_onblur() {
        try {
            
            mensaje = "";
            //if(salidasAlmacen.getCodLoteProduccion().length()==1){
            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement();
            String consulta = " select top 1 isnull(s.cod_prod,'') as cod_prod,cp.nombre_prod_semiterminado "
                    + " from salidas_almacen s inner join componentes_prod cp on s.cod_prod = cp.cod_compprod"
                    + " where s.cod_lote_produccion = '" + salidasAlmacen.getCodLoteProduccion().trim() + "' and rtrim(ltrim(s.cod_lote_produccion))<>'' and s.cod_estado_salida_almacen = 1 and s.estado_sistema = 1"
                    + " and s.FECHA_SALIDA_ALMACEN>'2011/01/01'"+
                      " and s.COD_ALMACEN=1";
            LOGGER.info("consulta " + consulta);
            ResultSet rs = st.executeQuery(consulta);
            if (rs.next()) {
                if (!salidasAlmacen.getComponentesProd().getCodCompprod().equals(rs.getString("cod_prod"))) {
                    salidasAlmacen.getComponentesProd().setCodCompprod(rs.getString("cod_prod"));
                    mensaje = "El codigo de lote corresponde al producto " + rs.getString("nombre_prod_semiterminado");
                }

            }
            rs.close();
            st.close();
            con.close();
            //}
        } catch (Exception e) {
            LOGGER.warn(e);
        }
        return null;
    }

    public String getCargarContenidoEditarSalidasAlmacen() {
        try {
            salidaEditable = true;
            //inicio ale validacion
            setProductoHabilitado(false);
            //final ale validacion
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            Map<String, Object> sessionMap = externalContext.getSessionMap();
            salidasAlmacen = (SalidasAlmacen) sessionMap.get("salidasAlmacenEditar");
            if (salidasAlmacen.getCodLoteProduccion().length() > 0 ) {      
                salidaEditable = false;
            }
            salidasAlmacen.getTiposSalidasAlmacen().setEditable(salidaEditable && salidasAlmacen.getTiposSalidasAlmacen().isEditable());
            salidasAlmacen.getTiposSalidasAlmacen().setAutomatico(salidaEditable ? salidasAlmacen.getTiposSalidasAlmacen().isAutomatico():true);
            if (salidasAlmacen.getTiposSalidasAlmacen().getCodTipoSalidaAlmacen()==3) {
                cargarSalidaTraspaso(salidasAlmacen);
                cargarFilialList();
                cargarAlmacenDestinoLista(traspasoGestionar);
                cargarAreasEmpresaPorTraspaso(traspasoGestionar);
                
            }
            else{
                this.cargarAreasEmpresa();
            }
            this.cargarComponentesProd();
            this.cargarTiposSalidasAlmacenEditar();
            capitulosList = this.cargarCapitulos();
            gruposList.add(new SelectItem("0", "-NINGUNO-"));
            materialesList.add(new SelectItem("0", "-NINGUNO-"));
            presentacionesList.add(new SelectItem("0", "-NINGUNO-"));
            salidasAlmacenDetalleList.clear();
            estadosMaterialList = this.cargarEstadosMaterial();
            //cargado de informacion de salida de almacen
            salidasAlmacenDetalleList = this.listadoSalidasAlmacenDetalle(salidasAlmacen);
            salidasAlmacenDetalleAuxList = this.listadoSalidasAlmacenDetalle(salidasAlmacen); //una copia de seguridad
            this.nroLote_change();

            motivoEdicionSalida = "";
            //salidasAlmacenDetalleAuxList = (List)((ArrayList)salidasAlmacenDetalleList).clone() ;
            //salidasAlmacenDetalleAuxList = salidasAlmacenDetalleList.toArray();
            //salidasAlmacenDetalleAuxList = this.copiaLista(salidasAlmacenDetalleList);
            //salidasAlmacenDetalleAuxList.addAll(salidasAlmacenDetalleList);
            //Collections.copy(salidasAlmacenDetalleAuxList,salidasAlmacenDetalleList); //se debe tener los items originales para restaurar las cantidades en los ingresos afectados
        } catch (Exception e) {
            LOGGER.warn(e);
        }
        return null;
    }

    public List copiaLista(List lista) {
        List listaCopia = new ArrayList();
        Iterator i1 = lista.iterator();
        while (i1.hasNext()) {
            SalidasAlmacenDetalle salidasAlmacenDetalleItem = new SalidasAlmacenDetalle();
            salidasAlmacenDetalleItem = (SalidasAlmacenDetalle) i1.next();
            listaCopia.add(salidasAlmacenDetalleItem.clone());
            Iterator i2 = salidasAlmacenDetalleItem.getSalidasAlmacenDetalleIngresoList().iterator();
            while (i2.hasNext()) {
                SalidasAlmacenDetalleIngreso salidasAlmacenDetalleIngresoItem = (SalidasAlmacenDetalleIngreso) i2.next();
            }
        }
        return listaCopia;
    }

    public List listadoSalidasAlmacenDetalle(SalidasAlmacen salidasAlmacen) {
        List salidasAlmacenDetalleList = new ArrayList();
        salidaTieneIngresoTraspaso = false;
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " SELECT COD_SALIDA_ALMACEN, COD_MATERIAL, CANTIDAD_SALIDA_ALMACEN, COD_UNIDAD_MEDIDA, COD_ESTADO_MATERIAL "
                    + " FROM SALIDAS_ALMACEN_DETALLE WHERE COD_SALIDA_ALMACEN = '" + salidasAlmacen.getCodSalidaAlmacen() + "'  ";

            consulta = " select s.COD_SALIDA_ALMACEN, s.COD_MATERIAL,mat.NOMBRE_MATERIAL, s.CANTIDAD_SALIDA_ALMACEN, s.COD_UNIDAD_MEDIDA,u.ABREVIATURA, s.COD_ESTADO_MATERIAL,e.NOMBRE_ESTADO_MATERIAL "
                    + " , iad.COD_INGRESO_ALMACEN, iad.CANT_TOTAL_INGRESO_FISICO,g.COD_CAPITULO"
                    + " from SALIDAS_ALMACEN_DETALLE s  "
                    + " inner join MATERIALES mat on mat.COD_MATERIAL = s.COD_MATERIAL "
                    + " inner join GRUPOS g on g.COD_GRUPO = mat.COD_GRUPO"
                    + " inner join UNIDADES_MEDIDA u on u.COD_UNIDAD_MEDIDA = s.COD_UNIDAD_MEDIDA "
                    + " inner join ESTADOS_MATERIAL e on e.COD_ESTADO_MATERIAL = s.COD_ESTADO_MATERIAL "
                    + " left join TRASPASO_INGRESO ti on ti.COD_SALIDA_ALMACEN = s.COD_SALIDA_ALMACEN"
                    + " left join INGRESOS_ALMACEN_DETALLE iad on iad.COD_INGRESO_ALMACEN = ti.COD_INGRESO_ALMACEN and iad.COD_MATERIAL = s.COD_MATERIAL"
                    + " where s.COD_SALIDA_ALMACEN = '" + salidasAlmacen.getCodSalidaAlmacen() + "'  ";
            
					LOGGER.info("consulta" + consulta);
                        Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            salidasAlmacenDetalleList.clear();
            while (rs.next()) {
                SalidasAlmacenDetalle salidasAlmacenDetalle = new SalidasAlmacenDetalle();
                salidasAlmacenDetalle.getSalidasAlmacen().setCodSalidaAlmacen(rs.getInt("COD_SALIDA_ALMACEN"));
                salidasAlmacenDetalle.getMateriales().setCodMaterial(rs.getString("COD_MATERIAL"));
                salidasAlmacenDetalle.getMateriales().setNombreMaterial(rs.getString("NOMBRE_MATERIAL"));
                salidasAlmacenDetalle.getMateriales().getGrupos().getCapitulos().setCodCapitulo(rs.getInt("COD_CAPITULO"));
                salidasAlmacenDetalle.setCantidadSalidaAlmacen(rs.getFloat("CANTIDAD_SALIDA_ALMACEN"));
                salidasAlmacenDetalle.getUnidadesMedida().setCodUnidadMedida(rs.getInt("COD_UNIDAD_MEDIDA"));
                salidasAlmacenDetalle.getUnidadesMedida().setAbreviatura(rs.getString("ABREVIATURA"));
                salidasAlmacenDetalle.getEstadosMaterial().setCodEstadoMaterial(rs.getInt("COD_ESTADO_MATERIAL"));
                salidasAlmacenDetalle.getEstadosMaterial().setNombreEstadoMaterial(rs.getString("NOMBRE_ESTADO_MATERIAL"));
                salidasAlmacenDetalle.getIngresoAlmacenDetalleTraspaso().getIngresosAlmacen().setCodIngresoAlmacen(rs.getInt("COD_INGRESO_ALMACEN")); 
                salidasAlmacenDetalle.getIngresoAlmacenDetalleTraspaso().setCantTotalIngresoFisico(rs.getFloat("CANT_TOTAL_INGRESO_FISICO")); 
                salidasAlmacenDetalle.setSalidasAlmacenDetalleIngresoList(listadoSalidasAlmacenDetalleIngreso(salidasAlmacenDetalle));
                if (rs.getFloat("CANT_TOTAL_INGRESO_FISICO") > 0f) {
                    salidaTieneIngresoTraspaso = true;
                }
                //se debe colocar la cantidad disponible en almacen

                salidasAlmacenDetalleList.add(salidasAlmacenDetalle);
            }
        } catch (Exception e) {
            LOGGER.warn(e);
        }
        return salidasAlmacenDetalleList;
    }

    public List listadoSalidasAlmacenDetalleIngreso(SalidasAlmacenDetalle salidasAlmacenDetalle) {
        List salidasAlmacenDetalleIngresoList = new ArrayList();
        ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " SELECT I.COD_INGRESO_ALMACEN,IAD.COD_MATERIAL,IADE.ETIQUETA,E.COD_ESTADO_MATERIAL,E.NOMBRE_ESTADO_MATERIAL,ESE.COD_EMPAQUE_SECUNDARIO_EXTERNO,ESE.NOMBRE_EMPAQUE_SECUNDARIO_EXTERNO, "
                    + " IADE.FECHA_VENCIMIENTO,I.NRO_INGRESO_ALMACEN,SEC.COD_SECCION,SEC.NOMBRE_SECCION,IADE.LOTE_MATERIAL_PROVEEDOR,S.CANTIDAD,"
                    + " (IADE.cantidad_parcial - ( select ISNULL(sum(sadi.cantidad), 0) from SALIDAS_ALMACEN_DETALLE_INGRESO sadi "
                    + " INNER JOIN SALIDAS_ALMACEN_DETALLE SAD ON SAD.COD_SALIDA_ALMACEN = SADI.COD_SALIDA_ALMACEN AND SAD.COD_MATERIAL = SADI.COD_MATERIAL "
                    + " INNER JOIN SALIDAS_ALMACEN S ON SAD.COD_SALIDA_ALMACEN = S.COD_SALIDA_ALMACEN "
                    + " where sadi.cod_material = iade.cod_material and  sadi.cod_ingreso_almacen = iade.cod_ingreso_almacen and  sadi.etiqueta = iade.etiqueta and "
                    + " S.COD_ESTADO_SALIDA_ALMACEN = 1 AND S.ESTADO_SISTEMA = 1 AND S.COD_ALMACEN = '" + usuario.getAlmacenesGlobal().getCodAlmacen() + "' "
                    + ") ) + ( select isnull(sum(cantidad_devuelta), 0)  from DEVOLUCIONES_DETALLE_ETIQUETAS dde  where dde.cod_material = iade.cod_material and dde.cod_ingreso_almacen = iade.cod_ingreso_almacen and "
                    + " dde.etiqueta = iade.etiqueta and dde.cod_devolucion in ( select cod_devolucion from DEVOLUCIONES where cod_estado_devolucion = 1 "
                    + " and estado_sistema = 1 and  cod_almacen = '" + usuario.getAlmacenesGlobal().getCodAlmacen() + "' ) ) as cantidad_r "
                    + " FROM SALIDAS_ALMACEN_DETALLE_INGRESO S "
                    + " INNER JOIN INGRESOS_ALMACEN I ON I.COD_INGRESO_ALMACEN = S.COD_INGRESO_ALMACEN "
                    + " INNER JOIN INGRESOS_ALMACEN_DETALLE IAD ON IAD.COD_INGRESO_ALMACEN = I.COD_INGRESO_ALMACEN "
                    + " INNER JOIN INGRESOS_ALMACEN_DETALLE_ESTADO IADE ON  "
                    + "     IADE.COD_INGRESO_ALMACEN = IAD.COD_INGRESO_ALMACEN  "
                    + " AND IADE.COD_MATERIAL = IAD.COD_MATERIAL "
                    + " AND IADE.ETIQUETA = S.ETIQUETA "
                    + " AND IADE.COD_MATERIAL = S.COD_MATERIAL "
                    + " INNER JOIN ESTADOS_MATERIAL E ON E.COD_ESTADO_MATERIAL = IADE.COD_ESTADO_MATERIAL "
                    + " INNER JOIN EMPAQUES_SECUNDARIO_EXTERNO ESE ON ESE.COD_EMPAQUE_SECUNDARIO_EXTERNO = IADE.COD_EMPAQUE_SECUNDARIO_EXTERNO "
                    + " INNER JOIN SECCIONES SEC ON SEC.COD_SECCION = IAD.COD_SECCION "
                    + " WHERE S.COD_SALIDA_ALMACEN = '" + salidasAlmacenDetalle.getSalidasAlmacen().getCodSalidaAlmacen() + "' "
                    + " AND S.COD_MATERIAL = '" + salidasAlmacenDetalle.getMateriales().getCodMaterial() + "' ";

            consulta = " select 1 col, iade.cod_ingreso_almacen, iade.cod_material, iade.etiqueta,  iade.cod_estado_material, iade.cod_empaque_secundario_externo, "
                    + " iade.cantidad_parcial - ( select ISNULL(sum(sadi.cantidad), 0) from SALIDAS_ALMACEN_DETALLE_INGRESO sadi "
                    + " where sadi.cod_material = iade.cod_material and  sadi.cod_ingreso_almacen = iade.cod_ingreso_almacen and  sadi.etiqueta = iade.etiqueta and "
                    + " cod_salida_almacen in ( select cod_salida_almacen  from SALIDAS_ALMACEN   where cod_estado_salida_almacen = 1 and  estado_sistema = 1 and  cod_almacen = '" + usuario.getAlmacenesGlobal().getCodAlmacen() + "' "
                    + ") ) + ( select isnull(sum(cantidad_devuelta), 0)  from DEVOLUCIONES_DETALLE_ETIQUETAS dde  where dde.cod_material = iade.cod_material and dde.cod_ingreso_almacen = iade.cod_ingreso_almacen and "
                    + " dde.etiqueta = iade.etiqueta and dde.cod_devolucion in ( select cod_devolucion from DEVOLUCIONES where cod_estado_devolucion = 1 "
                    + " and estado_sistema = 1 and  cod_almacen = '" + usuario.getAlmacenesGlobal().getCodAlmacen() + "' ) ) as cantidad_r, iade.fecha_vencimiento, iade.lote_material_proveedor "
                    + " ,e.nombre_estado_material,ia.nro_ingreso_almacen,s.nombre_seccion,es.nombre_empaque_secundario_externo "
                    + " from ingresos_almacen_detalle_estado iade,  ingresos_almacen_detalle iad,  secciones s, almacenes a, ingresos_almacen ia,ESTADOS_MATERIAL e,EMPAQUES_SECUNDARIO_EXTERNO es "
                    + " WHERE a.cod_almacen = '" + usuario.getAlmacenesGlobal().getCodAlmacen() + "' and iade.cod_material = iad.cod_material and iade.cod_ingreso_almacen = iad.cod_ingreso_almacen and "
                    + " ia.estado_sistema = 1 and  ia.cod_ingreso_almacen = iad.cod_ingreso_almacen and ia.cod_estado_ingreso_almacen = 1 "
                    + " and ia.cod_almacen = a.cod_almacen and iad.cod_seccion = s.cod_seccion and s.cod_almacen = a.cod_almacen "
                    + " and iade.cantidad_restante > 0 and iad.cod_seccion = s.cod_seccion and s.cod_almacen = a.cod_almacen "
                    + " and iade.COD_MATERIAL = '" + salidasAlmacenDetalle.getMateriales().getCodMaterial() + "' "
                    + " and iade.COD_ESTADO_MATERIAL = 2  "
                    + " and e.cod_estado_material= iade.cod_estado_material  "
                    + " and es.cod_empaque_secundario_externo =  iade.cod_empaque_secundario_externo  ";

            consulta = "  select iade.cod_ingreso_almacen, iade.cod_material, iade.etiqueta,  iade.cod_estado_material, iade.cod_empaque_secundario_externo,iade.CANTIDAD_PARCIAL - "
                    + " (select isnull(SUM(sadi.CANTIDAD),0) from SALIDAS_ALMACEN_DETALLE_INGRESO sadi inner join SALIDAS_ALMACEN_DETALLE sad on sadi.COD_SALIDA_ALMACEN = sad.COD_SALIDA_ALMACEN and sadi.COD_MATERIAL = sad.COD_MATERIAL"
                    + " inner join SALIDAS_ALMACEN s on s.COD_SALIDA_ALMACEN = sad.COD_SALIDA_ALMACEN"
                    + " where sadi.COD_INGRESO_ALMACEN = iade.COD_INGRESO_ALMACEN and sadi.COD_MATERIAL = iade.COD_MATERIAL and sadi.ETIQUETA = iade.ETIQUETA"
                    + " and s.COD_ESTADO_SALIDA_ALMACEN = 1 and s.ESTADO_SISTEMA = 1 and s.COD_ALMACEN = '" + usuario.getAlmacenesGlobal().getCodAlmacen() + "') "
                    + " +(select isnull(SUM(dde.CANTIDAD_DEVUELTA),0) from DEVOLUCIONES_DETALLE_ETIQUETAS dde inner join DEVOLUCIONES_DETALLE dd on dd.COD_DEVOLUCION = dde.COD_DEVOLUCION and dd.COD_MATERIAL = dde.COD_MATERIAL"
                    + " inner join DEVOLUCIONES d on d.COD_DEVOLUCION = dd.COD_DEVOLUCION"
                    + " where  dde.COD_INGRESO_ALMACEN = iade.COD_INGRESO_ALMACEN and dde.COD_MATERIAL = iade.COD_MATERIAL and dde.ETIQUETA = iade.ETIQUETA"
                    + " and d.COD_ESTADO_DEVOLUCION = 1 and d.ESTADO_SISTEMA = 1 and d.COD_ALMACEN = '" + usuario.getAlmacenesGlobal().getCodAlmacen() + "') cantidad_r, iade.fecha_vencimiento, iade.lote_material_proveedor "
                    + " ,e.nombre_estado_material,ia.nro_ingreso_almacen,sec.nombre_seccion,ese.nombre_empaque_secundario_externo ,"
                    + " (select sadi1.CANTIDAD from SALIDAS_ALMACEN_DETALLE_INGRESO sadi1 where sadi1.COD_INGRESO_ALMACEN=iade.COD_INGRESO_ALMACEN and  sadi1.COD_MATERIAL = iade.COD_MATERIAL and sadi1.ETIQUETA = iade.ETIQUETA"
                    + "  and sadi1.COD_SALIDA_ALMACEN = '" + salidasAlmacenDetalle.getSalidasAlmacen().getCodSalidaAlmacen() + "') cantidad_sacar"
                    + " from INGRESOS_ALMACEN_DETALLE_ESTADO iade "
                    + " inner join INGRESOS_ALMACEN_DETALLE iad on iad.COD_INGRESO_ALMACEN = iade.COD_INGRESO_ALMACEN and iad.COD_MATERIAL = iade.COD_MATERIAL"
                    + " inner join INGRESOS_ALMACEN ia on ia.COD_INGRESO_ALMACEN = iad.COD_INGRESO_ALMACEN"
                    + " inner join ALMACENES a on a.COD_ALMACEN = ia.COD_ALMACEN"
                    + " inner join ESTADOS_MATERIAL e on e.COD_ESTADO_MATERIAL = iade.COD_ESTADO_MATERIAL"
                    + " inner join EMPAQUES_SECUNDARIO_EXTERNO ese on ese.COD_EMPAQUE_SECUNDARIO_EXTERNO = iade.COD_EMPAQUE_SECUNDARIO_EXTERNO "
                    + " inner join secciones sec on sec.COD_SECCION = iad.COD_SECCION"
                    + " where iade.COD_ESTADO_MATERIAL in (2,6,8) and a.COD_ALMACEN = '" + usuario.getAlmacenesGlobal().getCodAlmacen() + "'  and ia.COD_ESTADO_INGRESO_ALMACEN = 1 and ia.ESTADO_SISTEMA = 1   "
                    + //and iade.CANTIDAD_RESTANTE>0
                    " and iade.COD_MATERIAL = '" + salidasAlmacenDetalle.getMateriales().getCodMaterial() + "' and ia.ESTADO_SISTEMA = 1 "
                    + " order by  iade.fecha_vencimiento asc,ia.nro_ingreso_almacen asc "; //and iade.COD_ESTADO_MATERIAL = 2

            consulta = "select * from( select iade.cod_ingreso_almacen, iade.cod_material, iade.etiqueta,  iade.cod_estado_material, iade.cod_empaque_secundario_externo, "
                    + //iade.CANTIDAD_PARCIAL -
                    " iade.cantidad_restante cantidad_r, iade.fecha_vencimiento, iade.lote_material_proveedor "
                    + " ,e.nombre_estado_material,ia.nro_ingreso_almacen,sec.nombre_seccion,ese.nombre_empaque_secundario_externo ,"
                    + " (select sadi1.CANTIDAD from SALIDAS_ALMACEN_DETALLE_INGRESO sadi1 where sadi1.COD_INGRESO_ALMACEN=iade.COD_INGRESO_ALMACEN and  sadi1.COD_MATERIAL = iade.COD_MATERIAL and sadi1.ETIQUETA = iade.ETIQUETA"
                    + "  and sadi1.COD_SALIDA_ALMACEN = '" + salidasAlmacenDetalle.getSalidasAlmacen().getCodSalidaAlmacen() + "') cantidad_sacar,iade.cantidad_restante"
                    + " from INGRESOS_ALMACEN_DETALLE_ESTADO iade "
                    + " inner join INGRESOS_ALMACEN_DETALLE iad on iad.COD_INGRESO_ALMACEN = iade.COD_INGRESO_ALMACEN and iad.COD_MATERIAL = iade.COD_MATERIAL"
                    + " inner join INGRESOS_ALMACEN ia on ia.COD_INGRESO_ALMACEN = iad.COD_INGRESO_ALMACEN"
                    + " inner join ALMACENES a on a.COD_ALMACEN = ia.COD_ALMACEN"
                    + " inner join ESTADOS_MATERIAL e on e.COD_ESTADO_MATERIAL = iade.COD_ESTADO_MATERIAL"
                    + " inner join EMPAQUES_SECUNDARIO_EXTERNO ese on ese.COD_EMPAQUE_SECUNDARIO_EXTERNO = iade.COD_EMPAQUE_SECUNDARIO_EXTERNO "
                    + " inner join secciones sec on sec.COD_SECCION = iad.COD_SECCION"
                    + " where iade.COD_ESTADO_MATERIAL in (2,6,8) and a.COD_ALMACEN = '" + usuario.getAlmacenesGlobal().getCodAlmacen() + "'  and ia.COD_ESTADO_INGRESO_ALMACEN = 1 and ia.ESTADO_SISTEMA = 1 "
                    + //and iade.cantidad_restante>0
                    " and iade.COD_MATERIAL = '" + salidasAlmacenDetalle.getMateriales().getCodMaterial() + "' and ia.ESTADO_SISTEMA = 1 "
                    + " ) as tabla where (cantidad_restante+isnull(cantidad_sacar,0))>0  order by  fecha_vencimiento asc,nro_ingreso_almacen asc,etiqueta asc "; //and iade.COD_ESTADO_MATERIAL = 2

            LOGGER.info("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            salidasAlmacenDetalleIngresoList.clear();
            salidasAlmacenDetalle.setCantidadDisponible(0);
            while (rs.next()) {
                SalidasAlmacenDetalleIngreso salidasAlmacenDetalleIngreso = new SalidasAlmacenDetalleIngreso();

                salidasAlmacenDetalleIngreso.setSalidasAlmacen(salidasAlmacenDetalle.getSalidasAlmacen()); // para los datos basicos
                salidasAlmacenDetalleIngreso.setMateriales(salidasAlmacenDetalle.getMateriales());

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

                // se adiciona el material que se saco al disponible
                salidasAlmacenDetalleIngreso.setCantidadSacar(rs.getFloat("cantidad_sacar"));
                salidasAlmacenDetalleIngreso.setCantidadDisponible(salidasAlmacenDetalleIngreso.getCantidadDisponible() + salidasAlmacenDetalleIngreso.getCantidadSacar());
                salidasAlmacenDetalleIngreso.setCantidad(salidasAlmacenDetalleIngreso.getCantidad() + salidasAlmacenDetalleIngreso.getCantidadSacar());

                //salidasAlmacenDetalleIngresoList.add(salidasAlmacenDetalleIngreso);
                if (salidasAlmacenDetalleIngreso.getCantidadDisponible() > 0.001 && salidasAlmacenDetalleIngreso.getCantidadDisponible() - salidasAlmacenDetalleIngreso.getCantidadSacar() >= 0) {
                    salidasAlmacenDetalle.setCantidadDisponible(salidasAlmacenDetalle.getCantidadDisponible() + salidasAlmacenDetalleIngreso.getCantidadDisponible()); //se van sumando las cantidades parciales dispnibles al detalle de salida de almacen
                    salidasAlmacenDetalleIngresoList.add(salidasAlmacenDetalleIngreso);
                }
            }

        } catch (Exception e) {
            LOGGER.warn(e);
        }
        return salidasAlmacenDetalleIngresoList;
    }

    public String guardarEditarSalidasAlmacen_action(){
        this.transaccionExitosa = false;
        Connection con = null;
        try {
            con = Util.openConnection(con);
            String consulta = "";
            con.setAutoCommit(false);
            if (salidasAlmacenDetalleList.isEmpty()) {
                this.mostrarMensajeTransaccionFallida("No existe detalle de salida ");
                return null;
            }
            if (motivoEdicionSalida.length() == 0) {
                this.mostrarMensajeTransaccionFallida("Ingrese Motivo/Observacion.");
                return null;
            }
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ManagedAccesoSistema usuarioLogueado = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            
            //SI LA EDICION ES CON SOLICITUD
            if (salidasAlmacen.getSalidaTransaccionSolicitud().getEstadoTransaccionSolicitud().getCodEstadoTransaccionSolicitud() == 2 ) {
                
                //Actualizar SALIDA_TRANSACCION_SOLICITUD
                consulta = " update SALIDA_TRANSACCION_SOLICITUD"
                        + " set COD_ESTADO_TRANSACCION_SOLICITUD = 7"
                        + " , COD_PERSONAL_VALIDADOR = "+ usuarioLogueado.getUsuarioModuloBean().getCodUsuarioGlobal()
                        + " , OBSERVACION_VALIDADOR = '"+ motivoEdicionSalida +"' "
                        + " , FECHA_VALIDACION = GETDATE() "
                        + " where COD_SALIDA_ALMACEN = " + salidasAlmacen.getCodSalidaAlmacen()
                        + " AND COD_SALIDA_TRANSACCION_SOLICITUD = "
                        + " (   SELECT TOP 1 COD_SALIDA_TRANSACCION_SOLICITUD "
                            + " FROM SALIDA_TRANSACCION_SOLICITUD sts"
                            + " WHERE COD_SALIDA_ALMACEN = " + salidasAlmacen.getCodSalidaAlmacen() + " and COD_SALIDA_ALMACEN = sts.COD_SALIDA_ALMACEN ORDER BY FECHA_SOLICITUD DESC"
                        + " )";
                LOGGER.info("consulta actualizar: " + consulta);
                st.executeUpdate(consulta);
            }     
            
                        
            //ACTUALIZAR SALIDAS_ALMACEN
            st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            consulta = " UPDATE SALIDAS_ALMACEN  SET  COD_TIPO_SALIDA_ALMACEN = '" + salidasAlmacen.getTiposSalidasAlmacen().getCodTipoSalidaAlmacen() + "',"
                    + " COD_AREA_EMPRESA = '" + salidasAlmacen.getAreasEmpresa().getCodAreaEmpresa() + "',"
                    + " OBS_SALIDA_ALMACEN = '" + salidasAlmacen.getObsSalidaAlmacen() + "',"
                    + " COD_LOTE_PRODUCCION = '" + salidasAlmacen.getCodLoteProduccion() + "',"
                    + " orden_trabajo = '" + salidasAlmacen.getOrdenTrabajo() + "'"
                    + " ,cod_prod = " + salidasAlmacen.getComponentesProd().getCodCompprod() + " "
                    + " ,COD_PRESENTACION = '" + salidasAlmacen.getPresentacionesProducto().getCodPresentacion() + "' "
                    + " ,COD_ESTADO_TRANSACCION_SALIDA='0', COD_MAQUINA = " + salidasAlmacen.getMaquinaria().getCodMaquina() + " "
                    + " WHERE   COD_SALIDA_ALMACEN = '" + salidasAlmacen.getCodSalidaAlmacen() + "'; ";
            LOGGER.info("consulta actualizar: " + consulta);            st.executeUpdate(consulta);
            
            //Si la salida es por TRASPASO
            if (salidasAlmacen.getTiposSalidasAlmacen().getCodTipoSalidaAlmacen()==3) {
                consulta = " UPDATE TRASPASOS SET COD_ALMACEN_DESTINO = '" + traspasoGestionar.getAlmacenDestino().getCodAlmacen() 
                        + "' WHERE COD_SALIDA_ALMACEN = '" + salidasAlmacen.getCodSalidaAlmacen() + "'";
                LOGGER.info("consulta " + consulta);
                st.executeUpdate(consulta);            }
            ManagedSalidaAlmacen managedSalida = (ManagedSalidaAlmacen)Util.getSessionBean("ManagedSalidaAlmacen");
            if (salidasAlmacen.getTiposSalidasAlmacen().isEditable() || 
                    managedSalida.isPermisoEditarEmpaquePrimarioLote()) {
                LOGGER.info("Es editable.");
                //restaurar las cantidades en los detalles de ingresos afectados
                //nuevo query para restaurarlos detalles de ingresos afectados
                consulta = " UPDATE INGRESOS_ALMACEN_DETALLE_ESTADO"
                        + " SET CANTIDAD_RESTANTE = CANTIDAD_RESTANTE + sadi.CANTIDAD"
                                +",COD_ESTANTE=0"
                                +",FILA=''" 
                                +",COLUMNA=''"
                        + " FROM INGRESOS_ALMACEN_DETALLE_ESTADO iade"
                            + " inner join MATERIALES m on m.COD_MATERIAL = iade.COD_MATERIAL"
                            + " inner join GRUPOS g on g.COD_GRUPO = m.COD_GRUPO"
                            + " inner join INGRESOS_ALMACEN_DETALLE iad on iad.COD_INGRESO_ALMACEN = iade.COD_INGRESO_ALMACEN and iad.COD_MATERIAL = iade.COD_MATERIAL"
                            + " inner join INGRESOS_ALMACEN ia on ia.COD_INGRESO_ALMACEN = iad.COD_INGRESO_ALMACEN"
                            + " inner join ALMACENES a on a.COD_ALMACEN = ia.COD_ALMACEN"
                            + " left join SALIDAS_ALMACEN_DETALLE_INGRESO sadi on sadi.COD_INGRESO_ALMACEN = iade.COD_INGRESO_ALMACEN and sadi.COD_MATERIAL = iade.COD_MATERIAL and sadi.ETIQUETA = iade.ETIQUETA"
                        + " WHERE iade.COD_ESTADO_MATERIAL in (2, 6)"
                        + " and a.COD_ALMACEN = " + usuarioLogueado.getAlmacenesGlobal().getCodAlmacen()
                        + " and ia.COD_ESTADO_INGRESO_ALMACEN = 1 "
                        + " and ia.ESTADO_SISTEMA = 1"
                        + " and isnull(sadi.CANTIDAD, 0) > 0"
                        + " and sadi.COD_SALIDA_ALMACEN = '" + salidasAlmacen.getCodSalidaAlmacen() + "' ";
                        if ((!salidasAlmacen.getTiposSalidasAlmacen().isEditable()) &&
                                managedSalida.isPermisoEditarEmpaquePrimarioLote()){
                            consulta += " and g.COD_CAPITULO ="+COD_CAPITULO_EMPAQUE_PRIMARIO;
                        }
                LOGGER.info("consulta " + consulta);
                if(st.executeUpdate(consulta) > 0)LOGGER.info("se actualizaron registros"); 
                
                consulta = "delete SALIDAS_ALMACEN_DETALLE "
                        + " from SALIDAS_ALMACEN_DETALLE s"
                        + " inner join materiales m on m.COD_MATERIAL = s.COD_MATERIAL"
                        + " inner join grupos g on g.COD_GRUPO = m.COD_GRUPO"
                        + " where s.COD_SALIDA_ALMACEN ="+salidasAlmacen.getCodSalidaAlmacen();
                        if ((!salidasAlmacen.getTiposSalidasAlmacen().isEditable()) &&
                                managedSalida.isPermisoEditarEmpaquePrimarioLote()){
                            consulta += " and g.COD_CAPITULO ="+COD_CAPITULO_EMPAQUE_PRIMARIO;
                        }
                LOGGER.info("consulta " + consulta);
                if(st.executeUpdate(consulta) > 0)LOGGER.info("se elimino el detalle de salidas");
                
                consulta = " DELETE SALIDAS_ALMACEN_DETALLE_INGRESO"
                        + " FROM SALIDAS_ALMACEN_DETALLE_INGRESO s"
                                + " inner join materiales m on m.COD_MATERIAL = s.COD_MATERIAL"
                                + " inner join grupos g on g.COD_GRUPO = m.COD_GRUPO"
                        + " where s.COD_SALIDA_ALMACEN ="+salidasAlmacen.getCodSalidaAlmacen();
                        if ((!salidasAlmacen.getTiposSalidasAlmacen().isEditable()) &&
                                managedSalida.isPermisoEditarEmpaquePrimarioLote()){
                            consulta += " and g.COD_CAPITULO ="+COD_CAPITULO_EMPAQUE_PRIMARIO;
                        }
                LOGGER.info("consulta " + consulta);
                st.executeUpdate(consulta);
            
                //iteracion de detalle
                Iterator i = salidasAlmacenDetalleList.iterator();

            
 		while (i.hasNext()) {		
                    SalidasAlmacenDetalle salidasAlmacenDetalleItem = (SalidasAlmacenDetalle) i.next();
                    if(salidasAlmacen.getTiposSalidasAlmacen().isEditable() ||
                        (managedSalida.isPermisoEditarEmpaquePrimarioLote()  &&
                            salidasAlmacenDetalleItem.getMateriales().getGrupos().getCapitulos().getCodCapitulo() == COD_CAPITULO_EMPAQUE_PRIMARIO))
                    {
                        consulta = " INSERT INTO SALIDAS_ALMACEN_DETALLE ( COD_SALIDA_ALMACEN, COD_MATERIAL, CANTIDAD_SALIDA_ALMACEN, COD_UNIDAD_MEDIDA, "
                                + "  COD_ESTADO_MATERIAL )  VALUES (   '" + salidasAlmacen.getCodSalidaAlmacen() + "', '" + salidasAlmacenDetalleItem.getMateriales().getCodMaterial() + "',"
                                + "  '" + salidasAlmacenDetalleItem.getCantidadSalidaAlmacen() + "',   '" + salidasAlmacenDetalleItem.getUnidadesMedida().getCodUnidadMedida() + "', "
                                + "   '" + salidasAlmacenDetalleItem.getEstadosMaterial().getCodEstadoMaterial() + "' ) ";
                        LOGGER.info("consulta " + consulta);
                        st.executeUpdate(consulta);
                        Iterator i3 = salidasAlmacenDetalleItem.getSalidasAlmacenDetalleIngresoList().iterator();

                        while (i3.hasNext()) {
                            SalidasAlmacenDetalleIngreso salidasAlmacenDetalleIngreso = (SalidasAlmacenDetalleIngreso) i3.next();


                            if (salidasAlmacenDetalleIngreso.getCantidadSacar() > 0) {
                                    consulta = "  INSERT INTO SALIDAS_ALMACEN_DETALLE_INGRESO ( COD_SALIDA_ALMACEN,  COD_MATERIAL, COD_INGRESO_ALMACEN,  ETIQUETA, "
                                            + " COSTO_SALIDA,   FECHA_VENCIMIENTO,   CANTIDAD,   COSTO_SALIDA_ACTUALIZADO,   FECHA_ACTUALIZACION,   COSTO_SALIDA_ACTUALIZADO_FINAL )  "
                                            + " VALUES ( '" + salidasAlmacen.getCodSalidaAlmacen() + "',   '" + salidasAlmacenDetalleItem.getMateriales().getCodMaterial() + "',   "
                                            + " '" + salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getIngresosAlmacen().getCodIngresoAlmacen() + "', "
                                            + " '" + salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getEtiqueta() + "', '" + salidasAlmacenDetalleIngreso.getCostoSalida() + "', "
                                            +(salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getFechaVencimiento() != null ? "'" + salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getFechaVencimiento() + "'":"null")+", "
                                            + " '" + salidasAlmacenDetalleIngreso.getCantidadSacar() + "', '" + salidasAlmacenDetalleIngreso.getCostoSalidaActualizado() + "', "
                                            + " null,  '" + salidasAlmacenDetalleIngreso.getCostoSalidaActualizadoFinal() + "' ) ";
                                    LOGGER.info("consulta " + consulta);
                                    st.executeUpdate(consulta);

                                    consulta = " update ingresos_almacen_detalle_estado set cantidad_restante=cantidad_restante - " + salidasAlmacenDetalleIngreso.getCantidadSacar() + " "
                                            + " where cod_material=" + salidasAlmacenDetalleItem.getMateriales().getCodMaterial() + " and etiqueta=" + salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getEtiqueta() + " "
                                            + " and cod_ingreso_almacen='" + salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getIngresosAlmacen().getCodIngresoAlmacen() + "' ";
                                    LOGGER.info("consulta " + consulta);
                                    st.executeUpdate(consulta);
                            }                  
                        }
                    }
                }
            }
            else{
                LOGGER.info("No es editable.");
            }
            //EJECUTAMOS SP DESPUES CAMBIO
            consulta = " exec [PAA_REGISTRO_SALIDA_ALMACEN_LOG] " + salidasAlmacen.getCodSalidaAlmacen() 
                    + " , "+ usuarioLogueado.getUsuarioModuloBean().getCodUsuarioGlobal()
                    + " , 1"
                    + " , '"+motivoEdicionSalida+"' ";
            LOGGER.info("consulta ejecutar: " + consulta);            
            st.executeUpdate(consulta);
            
            con.commit();
            st.close();
            this.mostrarMensajeTransaccionExitosa("Se registro satisfactoriamente la edición");
        } catch (Exception e) {
            LOGGER.warn(e);
            this.rollbackConexion(con);
            this.mostrarMensajeTransaccionFallida("Ocurrio un error, no se guardó el registro.");
            registradoCorrectamente = false;
        } finally {
            this.cerrarConexion(con);
        }
        //ENVIAMOS CORREO
        if (this.transaccionExitosa && salidasAlmacen.getSalidaTransaccionSolicitud().getEstadoTransaccionSolicitud().getCodEstadoTransaccionSolicitud() == 2 ) {
            EnvioCorreoNotificacionValidacionSalidaAlmacen correo = new EnvioCorreoNotificacionValidacionSalidaAlmacen(salidasAlmacen.getCodSalidaAlmacen());
            correo.start();
        }
        if(this.transaccionExitosa){
            ThreadCosteoByCodSalida costeoSalida = new ThreadCosteoByCodSalida(salidasAlmacen.getCodSalidaAlmacen());
            costeoSalida.start();
        }
        return null;

    }

    //inicio alejandro orden de compra
    public String guardarAsociacionOC() {
        for (OrdenesCompra current : ordenesCompraList) {
            if (current.getChecked()) {
                salidasAlmacen.getOrdenesCompra().setCodOrdenCompra(current.getCodOrdenCompra());
            }
        }
        return null;
    }

    public String cargarDetalleOrdenCompra() {
        OrdenesCompra current = (OrdenesCompra) ordenesCompraDataTable.getRowData();
        try {
            String consulta = "select ISNULL(m.NOMBRE_MATERIAL,'') AS MATERIAL,ocd.CANTIDAD_NETA,ISNULL(um.ABREVIATURA,'') AS ABREV,ocd.PRECIO_UNITARIO,(ocd.PRECIO_UNITARIO*ocd.CANTIDAD_NETA) as subtotal,ocd.DESCRIPCION "
                    + " from ORDENES_COMPRA_DETALLE ocd left outer join MATERIALES m on ocd.COD_MATERIAL=m.COD_MATERIAL "
                    + " left outer join UNIDADES_MEDIDA um on ocd.COD_UNIDAD_MEDIDA=um.COD_UNIDAD_MEDIDA"
                    + " where ocd.COD_ORDEN_COMPRA=" + current.getCodOrdenCompra();
            LOGGER.info("consulta " + consulta);
            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet res = st.executeQuery(consulta);
            ordenesCompraDetalleList.clear();
            while (res.next()) {
                OrdenesCompraDetalle nuevo = new OrdenesCompraDetalle();
                nuevo.getMateriales().setNombreMaterial(res.getString("MATERIAL"));
                nuevo.setCantidadNeta(res.getFloat("CANTIDAD_NETA"));
                nuevo.getUnidadesMedida().setAbreviatura(res.getString("ABREV"));
                nuevo.setPrecioTotal(res.getFloat("subtotal"));
                nuevo.setPrecioUnitario(res.getFloat("PRECIO_UNITARIO"));
                nuevo.setDescripcion(res.getString("DESCRIPCION"));
                ordenesCompraDetalleList.add(nuevo);
            }
            res.close();
            st.close();
            con.close();
        } catch (SQLException ex) {
            LOGGER.warn(ex);
        }
        return null;
    }

    public String atras_action() {
        super.back();
        this.cargarOrdenesCompra();
        return null;
    }

    public String siguiente_action() {

        super.next();
        this.cargarOrdenesCompra();
        return null;
    }

    public String cargarOrdenesCompra() {

        String consulta = "";

        try {
            Connection con = null;
            con = Util.openConnection(con);
            consulta = " select * from (select ROW_NUMBER() OVER (ORDER BY oc.FECHA_EMISION desc) as 'FILAS',"
                    + " oc.COD_ORDEN_COMPRA,oc.FECHA_EMISION,oc.NRO_ORDEN_COMPRA,p.NOMBRE_PROVEEDOR,"
                    + "r.NOMBRE_REPRESENTANTE,pai.NOMBRE_PAIS,ae.NOMBRE_AREA_EMPRESA,tc.NOMBRE_TIPO_COMPRA,"
                    + "(per.AP_PATERNO_PERSONAL+' '+per.AP_MATERNO_PERSONAL+' '+per.NOMBRE_PILA) as perResponsable,oc.OBS_ORDEN_COMPRA"
                    + " from ORDENES_COMPRA oc left outer join PROVEEDORES p on oc.COD_PROVEEDOR=p.COD_PROVEEDOR"
                    + " left outer join PERSONAL per on oc.COD_RESPONSABLE_COMPRAS=per.COD_PERSONAL"
                    + " left outer join REPRESENTANTES r on oc.COD_REPRESENTANTE=r.COD_REPRESENTANTE"
                    + " left outer join paises pai on pai.COD_PAIS=p.COD_PAIS"
                    + " left outer join AREAS_EMPRESA ae on ae.COD_AREA_EMPRESA=oc.DIVISION"
                    + " left outer join TIPOS_COMPRA tc on tc.COD_TIPO_COMPRA=oc.COD_TIPO_COMPRA"
                    + " where oc.ESTADO_SISTEMA=1 ";
            if (!codMaterial.equals("0")) {
                consulta += " and oc.COD_ORDEN_COMPRA in (select ocd.COD_ORDEN_COMPRA from ORDENES_COMPRA_DETALLE ocd where ocd.COD_MATERIAL ='" + codMaterial + "') ";
            } else if (!codGrupo.equals("0")) {
                consulta += " and oc.COD_ORDEN_COMPRA in (select ocd.COD_ORDEN_COMPRA from ORDENES_COMPRA_DETALLE ocd where ocd.COD_MATERIAL in(select m.COD_MATERIAL from MATERIALES m where m.COD_GRUPO in ('" + codGrupo + "'))) ";
            } else if (!codCapitulo.equals("0")) {
                consulta += " and oc.COD_ORDEN_COMPRA in (select ocd.COD_ORDEN_COMPRA from ORDENES_COMPRA_DETALLE ocd where ocd.COD_MATERIAL in(select m.COD_MATERIAL from MATERIALES m where m.COD_GRUPO in (select gr.COD_GRUPO from grupos gr where gr.COD_CAPITULO in ('" + codCapitulo + "')))) ";
            }
            if (fechaInicio != null && fechaFinal != null) {
                SimpleDateFormat sdt = new SimpleDateFormat("yyyy/MM/dd");
                consulta += " and oc.FECHA_EMISION BETWEEN '" + sdt.format(fechaInicio) + " 00:00:00' and '" + sdt.format(fechaFinal) + " 23:59:59' ";
            }
            if (!filtroOrdenCompra.getProveedores().getCodProveedor().equals("0")) {
                consulta += " and oc.COD_PROVEEDOR ='" + filtroOrdenCompra.getProveedores().getCodProveedor() + "' ";
            }
            if (!filtroOrdenCompra.getResponsableCompras().getCodPersonal().equals("0")) {
                consulta += " and oc.COD_RESPONSABLE_COMPRAS ='" + filtroOrdenCompra.getResponsableCompras().getCodPersonal() + "' ";
            }
            if (filtroOrdenCompra.getRepresentantes().getCodRepresentante() != 0) {
                consulta += " and oc.COD_REPRESENTANTE ='" + filtroOrdenCompra.getRepresentantes().getCodRepresentante() + "' ";
            }
            ManagedAccesoSistema user = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            consulta += " and oc.COD_ALMACEN_ENTREGA='" + user.getAlmacenesGlobal().getCodAlmacen() + "'";

            consulta += " ) AS listado_ordenes_compra where FILAS BETWEEN " + begin + " AND " + end;
            LOGGER.info("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet res = st.executeQuery(consulta);
            ordenesCompraList.clear();
            ordenesCompraDetalleList.clear();
            while (res.next()) {
                OrdenesCompra nuevoOrdenCompra = new OrdenesCompra();
                nuevoOrdenCompra.setCodOrdenCompra(res.getInt("COD_ORDEN_COMPRA"));
                nuevoOrdenCompra.setNroOrdenCompra(res.getInt("NRO_ORDEN_COMPRA"));
                nuevoOrdenCompra.setFechaEmision(res.getDate("FECHA_EMISION"));
                nuevoOrdenCompra.getProveedores().setNombreProveedor(res.getString("NOMBRE_PROVEEDOR"));
                nuevoOrdenCompra.getRepresentantes().setNombreRepresentante(res.getString("NOMBRE_REPRESENTANTE"));
                nuevoOrdenCompra.getProveedores().getPaises().setNombrePais(res.getString("NOMBRE_PAIS"));
                nuevoOrdenCompra.getDivisionCompras().setNombreDivision(res.getString("NOMBRE_AREA_EMPRESA"));
                nuevoOrdenCompra.getTiposCompra().setNombreTipoCompra(res.getString("NOMBRE_TIPO_COMPRA"));
                nuevoOrdenCompra.getResponsableCompras().setNombrePersonal(res.getString("perResponsable"));
                nuevoOrdenCompra.setObsOrdenCompra(res.getString("OBS_ORDEN_COMPRA"));
                ordenesCompraList.add(nuevoOrdenCompra);
            }
            this.cantidadfilas = ordenesCompraList.size();
            res.close();
            st.close();
            con.close();
        } catch (SQLException ex) {
            LOGGER.warn(ex);
        }

        return null;
    }

    public String grupos_OC() {
        try {

            this.gruposList = this.cargarGrupos(Integer.valueOf(codCapitulo));
        } catch (Exception ex) {
            LOGGER.warn(ex);;
        }
        return null;
    }

    public String materiales_OC() {
        try {
            this.materialesList = this.cargarMateriales(Integer.parseInt(codGrupo));
        } catch (Exception ex) {
            LOGGER.warn(ex);
        }
        return null;
    }

    private void cargarPersonal() {
        try {
            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String consulta = "select p.COD_PERSONAL,(p.AP_PATERNO_PERSONAL+' '+p.AP_MATERNO_PERSONAL+' '+p.NOMBRE_PILA) as nombre from PERSONAL p order by p.AP_PATERNO_PERSONAL,p.AP_MATERNO_PERSONAL,p.NOMBRE_PILA";
            ResultSet res = st.executeQuery(consulta);
            personalList.clear();
            personalList.add(new SelectItem("0", "-TODOS-"));
            while (res.next()) {
                personalList.add(new SelectItem(res.getString("COD_PERSONAL"), res.getString("nombre")));
            }
            res.close();
            st.close();
            con.close();
        } catch (SQLException ex) {
            LOGGER.warn(ex);
        }
    }

    private void cargarRepresentantes() {
        try {
            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String consulta = "select r.COD_REPRESENTANTE,r.NOMBRE_REPRESENTANTE from REPRESENTANTES r where r.COD_ESTADO_REGISTRO=1 order by r.NOMBRE_REPRESENTANTE";
            ResultSet res = st.executeQuery(consulta);
            representantesList.clear();
            representantesList.add(new SelectItem(0, "-TODOS-"));
            while (res.next()) {
                representantesList.add(new SelectItem(res.getInt("COD_REPRESENTANTE"), res.getString("NOMBRE_REPRESENTANTE")));
            }
            res.close();
            st.close();
            con.close();

        } catch (SQLException ex) {
            LOGGER.warn(ex);
        }
    }

    public String cargarOC() {
        this.capitulosList = this.cargarCapitulos();
        this.codCapitulo = "0";
        this.cargarProveedores();
        this.cargarPersonal();
        this.cargarRepresentantes();
        fechaInicio = null;
        fechaFinal = null;
        this.cargarRepresentantes();
        gruposList.clear();
        gruposList.add(new SelectItem("0", "-NINGUNO-"));
        materialesList.clear();
        materialesList.add(new SelectItem("0", "-NINGUNO-"));
        codGrupo = "0";
        codMaterial = "0";
        filtroOrdenCompra.getProveedores().setCodProveedor("0");
        filtroOrdenCompra.getRepresentantes().setCodRepresentante(0);
        filtroOrdenCompra.getResponsableCompras().setCodPersonal("0");
        this.cargarOrdenesCompra();
        return null;
    }

    public void cargarProveedores() {
        try {

            String consulta = "select m.cod_proveedor, m.nombre_proveedor from proveedores m where m.cod_estado_registro=1 order by m.nombre_proveedor ";
            LOGGER.info("consulta " + consulta);

            Connection con = null;
            con = Util.openConnection(con);

            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);

            proveedoresList.clear();
            proveedoresList.add(new SelectItem("0", "-TODOS-"));
            while (rs.next()) {

                proveedoresList.add(new SelectItem(rs.getString(1), rs.getString(2)));

            }

        } catch (Exception e) {
            LOGGER.warn(e);
        }
    }

    public void cargarMaquinarias(String codAreaEmpresa) {
        try {

            String consulta = "select m.cod_maquina,m.NOMBRE_MAQUINA + '( '+ m.CODIGO +' ) ' nombre_maquina from maquinarias m where m.COD_ESTADO_REGISTRO = 1 and m.cod_area_empresa = '" + codAreaEmpresa + "'"
                    + " order by m.NOMBRE_MAQUINA ";
            LOGGER.info("consulta " + consulta);

            Connection con = null;
            con = Util.openConnection(con);

            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);

            maquinariasList.clear();
            //maquinariasList.add(new SelectItem("0","-TODOS-"));
            while (rs.next()) {
                maquinariasList.add(new SelectItem(rs.getString("cod_maquina"), rs.getString("nombre_maquina")));
            }

        } catch (Exception e) {
            LOGGER.warn(e);
        }
    }
    
    public String cargarAlmacenDestinoLista(Traspasos traspasos) {
        almacenDestinoList = new ArrayList();
        try {
            almacenDestinoList.add(new SelectItem("0", "-NINGUNO-"));
            
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select cod_almacen,nombre_almacen from  almacenes where cod_filial='" + traspasos.getAlmacenDestino().getFiliales().getCodFilial() + "' and cod_almacen<>'" + usuario.getAlmacenesGlobal().getCodAlmacen() + "' ";
            LOGGER.info("consulta cargarAlmacenDestinoLista: " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            while (rs.next()) {
                almacenDestinoList.add(new SelectItem(rs.getString("cod_almacen"), rs.getString("nombre_almacen")));
            }
            rs.close();
            st.close();
            con.close();
 		} catch (Exception e) {
            LOGGER.warn(e);
        }
        return null;
    }

public String cargarFilialList() {
        filialList = new ArrayList();
        try {
            filialList.add(new SelectItem("0", "-NINGUNO-"));
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select f.COD_FILIAL,f.NOMBRE_FILIAL from filiales f where f.COD_ESTADO_REGISTRO = 1 ";
            LOGGER.info("consulta cargaFilialList: " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);            
            while (rs.next()) {
                filialList.add(new SelectItem(rs.getString("COD_FILIAL"), rs.getString("NOMBRE_FILIAL")));
            }
            rs.close();
            st.close();
            con.close();

        } catch (Exception e) {
            LOGGER.warn(e);
        }
        return null;
    }

    public String filial_change() {
        cargarAlmacenDestinoLista(traspasoGestionar);
        cargarAreasEmpresaPorTraspaso(traspasoGestionar);
        
        return null;
    }
    
    public String cargarAreasEmpresaPorTraspaso(Traspasos traspasos) {
        areasEmpresaList = new ArrayList();
        try {
            areasEmpresaList.add(new SelectItem("0", "-NINGUNO-"));
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select cod_almacen_frv"
                    + " from almacenes_frv"
                    + " where cod_almacen_frv = '" + traspasos.getAlmacenDestino().getCodAlmacen() + "' ";
            LOGGER.info("consulta: " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            if (rs.next()) {
                consulta = " select cod_area_empresa, nombre_area_empresa "
                        + " from areas_empresa"
                        + " where cod_estado_registro = 1"
                        + " and (cod_area_empresa=98 or cod_area_empresa=99 or cod_area_empresa=100)"
                        + " order by nombre_area_empresa ";
            } else {
                consulta = " select cod_area_empresa, nombre_area_empresa"
                        + " from areas_empresa"
                        + " where cod_estado_registro=1 and cod_area_empresa<>100"
                        + " and cod_area_empresa<>98 and cod_area_empresa<>99"
                        + " order by nombre_area_empresa ";
            }
            LOGGER.info("consulta: " + consulta);
            Statement st1 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs1 = st1.executeQuery(consulta);
            
            while (rs1.next()) {
                areasEmpresaList.add(new SelectItem(rs1.getString("cod_area_empresa"), rs1.getString("nombre_area_empresa")));
            }
            st.close();
            st1.close();
            rs1.close();
            rs.close();
            con.close();

        } catch (Exception e) {
           LOGGER.warn(e);
        }
        return null;
    }
    
    //------------------------ SALIDAS DE ALMACEN POR RECHAZADOS -----------------------
    
    public String getCargarContenidoRegistroSalidaAlmacenRechazados() {
        
        this.cargarPermisoPersonal();
        try {
            ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            Map<String, Object> sessionMap = externalContext.getSessionMap();
            codIngresoAlmacenRechazados = (Integer) sessionMap.get("codIngresoAlmacen");
            salidasAlmacen = new SalidasAlmacen();
            
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " SELECT ae.NOMBRE_AREA_EMPRESA, tsa.NOMBRE_TIPO_SALIDA_ALMACEN "
                    + " FROM AREAS_EMPRESA ae"
                    + " left join TIPOS_SALIDAS_ALMACEN tsa on tsa.COD_TIPO_SALIDA_ALMACEN = 10"
                    + " WHERE COD_AREA_EMPRESA = 30";
            LOGGER.info("consulta: " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            salidasAlmacenDetalleList.clear();
            if (rs.next()) {
                salidasAlmacen.getAreasEmpresa().setNombreAreaEmpresa(rs.getString("NOMBRE_AREA_EMPRESA"));
                salidasAlmacen.getTiposSalidasAlmacen().setNombreTipoSalidaAlmacen(rs.getString("NOMBRE_TIPO_SALIDA_ALMACEN"));
            }
            rs.close();
            st.close();
            con.close();
            
            salidasAlmacen.setNroSalidaAlmacen(this.generaNroIngresoAlmacen());
            salidasAlmacen.getGestiones().setCodGestion(usuario.getGestionesGlobal().getCodGestion());
            salidasAlmacen.getPersonal().setCodPersonal(usuario.getUsuarioModuloBean().getCodUsuarioGlobal());
            salidasAlmacen.getAlmacenes().setCodAlmacen(usuario.getAlmacenesGlobal().getCodAlmacen());
            salidasAlmacen.getEstadosSalidasAlmacen().setCodEstadoSalidaAlmacen(1);
            salidasAlmacen.setEstadoSistema(1);
            salidasAlmacen.getTiposSalidasAlmacen().setCodTipoSalidaAlmacen(10);//devolucion al proveedor
            salidasAlmacen.getAreasEmpresa().setCodAreaEmpresa("30");//AREA COMPRAS
            
            cargarSalidaAlmacenDetalleListPorRechazados(codIngresoAlmacenRechazados);


        } catch (Exception e) {
            LOGGER.warn(e);
        }
        return null;
    }
    
    public String cargarSalidaAlmacenDetalleListPorRechazados(Integer codIngresoAlmacen){
    
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " SELECT iad.COD_MATERIAL, iad.COD_UNIDAD_MEDIDA, mat.NOMBRE_MATERIAL, iade.SUMA_CANTIDAD_RESTANTE"
                    + ", u.ABREVIATURA, u.NOMBRE_UNIDAD_MEDIDA, em.NOMBRE_ESTADO_MATERIAL, mat.COD_GRUPO, gr.COD_CAPITULO, mat.COD_UNIDAD_MEDIDA_COMPRA "
                    + " FROM INGRESOS_ALMACEN_DETALLE iad"
                    + " inner join MATERIALES mat on mat.COD_MATERIAL = iad.COD_MATERIAL"
                    + " inner join GRUPOS gr on gr.COD_GRUPO = mat.COD_GRUPO "
                    + " inner join UNIDADES_MEDIDA u on u.COD_UNIDAD_MEDIDA = iad.COD_UNIDAD_MEDIDA"
                    + " left join ESTADOS_MATERIAL em on em.COD_ESTADO_MATERIAL = 3"
                    + " inner join ("
                        + " SELECT COD_MATERIAL, SUM(CANTIDAD_RESTANTE) as SUMA_CANTIDAD_RESTANTE"
                        + " FROM INGRESOS_ALMACEN_DETALLE_ESTADO"
                        + " WHERE COD_INGRESO_ALMACEN = '"+codIngresoAlmacen+"'"
                        + " and COD_ESTADO_MATERIAL = 3"
                        + " and CANTIDAD_RESTANTE > 0.01"
                        + " group by COD_MATERIAL"
                    + " ) iade on iad.COD_MATERIAL = iade.COD_MATERIAL"
                    + " WHERE iad.COD_INGRESO_ALMACEN = '"+codIngresoAlmacen+"'";
            LOGGER.info("consulta: " + consulta);
    
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            salidasAlmacenDetalleList.clear();
            while (rs.next()) {
                SalidasAlmacenDetalle salidasAlmacenDetalle = new SalidasAlmacenDetalle();
                salidasAlmacenDetalle.getMateriales().setCodMaterial(rs.getString("COD_MATERIAL"));
                salidasAlmacenDetalle.getMateriales().setNombreMaterial(rs.getString("NOMBRE_MATERIAL"));
                salidasAlmacenDetalle.getMateriales().getUnidadesMedida().setCodUnidadMedida(rs.getInt("COD_UNIDAD_MEDIDA")); 
                salidasAlmacenDetalle.getMateriales().getGrupos().setCodGrupo(rs.getInt("COD_GRUPO"));
                salidasAlmacenDetalle.getMateriales().getGrupos().getCapitulos().setCodCapitulo(rs.getInt("COD_CAPITULO"));
                salidasAlmacenDetalle.getMateriales().getUnidadesMedidaCompra().setCodUnidadMedida(rs.getInt("COD_UNIDAD_MEDIDA_COMPRA"));
                salidasAlmacenDetalle.getEstadosMaterial().setCodEstadoMaterial(2);//Aprobado
                salidasAlmacenDetalle.setCantidadSalidaAlmacen(rs.getFloat("SUMA_CANTIDAD_RESTANTE"));
                salidasAlmacenDetalle.getUnidadesMedida().setCodUnidadMedida(rs.getInt("COD_UNIDAD_MEDIDA"));
                salidasAlmacenDetalle.getUnidadesMedida().setNombreUnidadMedida(rs.getString("NOMBRE_UNIDAD_MEDIDA"));
                salidasAlmacenDetalle.getUnidadesMedida().setAbreviatura(rs.getString("ABREVIATURA"));
                salidasAlmacenDetalle.getEstadosMaterial().setCodEstadoMaterial(3);
                salidasAlmacenDetalle.getEstadosMaterial().setNombreEstadoMaterial(rs.getString("NOMBRE_ESTADO_MATERIAL"));
                salidasAlmacenDetalleList.add(salidasAlmacenDetalle);
            }
            
            
            rs.close();
            st.close();
            con.close();
        } 
        catch (Exception e) {
            LOGGER.warn(e);
        }
        return null;
    }
    
     //-------- GUARDAR SALIDA DE RECHAZADOS
            
    public String guardarSalidaAlmacenRechazados_action() throws SQLException {
        registradoCorrectamente = false;
        mensaje = "";
        Connection con = null;
        con = Util.openConnection(con);
        try {
            
            con.setAutoCommit(false);
            ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            
            //Validando si tiene items seleccionados
            Iterator i = salidasAlmacenDetalleList.iterator();
            boolean salidaAlmacenDetalleSeleccionado = false;
            while (i.hasNext()) {
                SalidasAlmacenDetalle salidasAlmacenDetalleItem = (SalidasAlmacenDetalle) i.next();
                if (salidasAlmacenDetalleItem.getChecked()) {
                    salidaAlmacenDetalleSeleccionado = true;
                    break;
                }
            }
            if (!salidaAlmacenDetalleSeleccionado) {
                mensaje = " Seleccione al menos un detalle para generar la salida.";
                return null;
            }
            
                        
            salidasAlmacen.setCodSalidaAlmacen(this.generaCodSalidaAlmacen());
            salidasAlmacen.setNroSalidaAlmacen(this.generaNroIngresoAlmacen());
            
            String consulta = " INSERT INTO SALIDAS_ALMACEN ( COD_GESTION, COD_SALIDA_ALMACEN, COD_ORDEN_PESADA,  COD_FORM_SALIDA, COD_PROD, "
                    + "   COD_TIPO_SALIDA_ALMACEN,  COD_AREA_EMPRESA,   NRO_SALIDA_ALMACEN,   FECHA_SALIDA_ALMACEN,   OBS_SALIDA_ALMACEN, "
                    + "  ESTADO_SISTEMA,   COD_ALMACEN,   COD_ORDEN_COMPRA,   COD_PERSONAL,    COD_ESTADO_SALIDA_ALMACEN,   COD_LOTE_PRODUCCION, "
                    + "   COD_ESTADO_SALIDA_COSTO,   cod_prod_ant,   orden_trabajo,   COD_PRESENTACION,cod_prod1,cod_maquina) VALUES ( "
                    + "  '" + salidasAlmacen.getGestiones().getCodGestion() + "', '" + salidasAlmacen.getCodSalidaAlmacen() + "', '" + salidasAlmacen.getCodOrdenRepesada() + "',"
                    + "  '" + salidasAlmacen.getCodFormSalida() + "','" + salidasAlmacen.getComponentesProd().getCodCompprod() + "', '" + salidasAlmacen.getTiposSalidasAlmacen().getCodTipoSalidaAlmacen() + "', "
                    + "   '" + salidasAlmacen.getAreasEmpresa().getCodAreaEmpresa() + "', '" + salidasAlmacen.getNroSalidaAlmacen() + "'," + "getdate()" + ","
                    + "  '" + salidasAlmacen.getObsSalidaAlmacen() + "',  '" + salidasAlmacen.getEstadoSistema() + "','" + salidasAlmacen.getAlmacenes().getCodAlmacen() + "',"
                    + "  '" + salidasAlmacen.getOrdenesCompra().getCodOrdenCompra() + "', '" + salidasAlmacen.getPersonal().getCodPersonal() + "',  '" + salidasAlmacen.getEstadosSalidasAlmacen().getCodEstadoSalidaAlmacen() + "',"
                    + "  '" + salidasAlmacen.getCodLoteProduccion() + "','" + salidasAlmacen.getCodEstadoSalidaCosto() + "',"
                    + "  '" + salidasAlmacen.getCodProdAnt() + "', '" + salidasAlmacen.getOrdenTrabajo() + "', '" + salidasAlmacen.getPresentacionesProducto().getCodPresentacion() + "','" + salidasAlmacen.getComponentesProd1().getCodCompprod() + "','" + salidasAlmacen.getMaquinaria().getCodMaquina() + "'); ";
            LOGGER.info("consulta: " + consulta);
            st.executeUpdate(consulta);
            
            //iteracion de detalle
            i = salidasAlmacenDetalleList.iterator();
            while (i.hasNext()) {
                SalidasAlmacenDetalle salidasAlmacenDetalleItem = (SalidasAlmacenDetalle) i.next();
                if (salidasAlmacenDetalleItem.getChecked()) {
                    
                    consulta = " INSERT INTO SALIDAS_ALMACEN_DETALLE ( COD_SALIDA_ALMACEN, COD_MATERIAL, CANTIDAD_SALIDA_ALMACEN, COD_UNIDAD_MEDIDA, "
                        + "  COD_ESTADO_MATERIAL )  VALUES (   '" + salidasAlmacen.getCodSalidaAlmacen() + "', '" + salidasAlmacenDetalleItem.getMateriales().getCodMaterial() + "',"
                        + "  '" + salidasAlmacenDetalleItem.getCantidadSalidaAlmacen() + "', '" + salidasAlmacenDetalleItem.getUnidadesMedida().getCodUnidadMedida() + "', "
                        + "   '3' ) ";
                    LOGGER.info("consulta: " + consulta);
                    st.executeUpdate(consulta);

                    consulta = " INSERT INTO SALIDAS_ALMACEN_DETALLE_INGRESO ( COD_SALIDA_ALMACEN,  COD_MATERIAL, COD_INGRESO_ALMACEN,  ETIQUETA, "
                            + " COSTO_SALIDA,   FECHA_VENCIMIENTO,   CANTIDAD,   COSTO_SALIDA_ACTUALIZADO,   FECHA_ACTUALIZACION,   COSTO_SALIDA_ACTUALIZADO_FINAL "
                            + ", COD_ESTANTE, FILA, COLUMNA)  "
                            + " SELECT '" + salidasAlmacen.getCodSalidaAlmacen() + "', '" + salidasAlmacenDetalleItem.getMateriales().getCodMaterial() + "',   "
                            + " '" + codIngresoAlmacenRechazados + "', iade.ETIQUETA, 0, iade.FECHA_VENCIMIENTO, iade.CANTIDAD_RESTANTE, 0, null, 0, 0,'','' "
                            + " FROM INGRESOS_ALMACEN_DETALLE_ESTADO iade "
                            + " WHERE iade.CANTIDAD_RESTANTE > 0.01 "
                            + " and COD_ESTADO_MATERIAL = 3 "
                            + " and COD_MATERIAL = '"+ salidasAlmacenDetalleItem.getMateriales().getCodMaterial() +"'"
                            + " and COD_INGRESO_ALMACEN = '"+codIngresoAlmacenRechazados+"' ";
                    LOGGER.info("consulta: " + consulta);
                    st.executeUpdate(consulta);

                    consulta = " UPDATE INGRESOS_ALMACEN_DETALLE_ESTADO "
                            + " SET CANTIDAD_RESTANTE = 0 "
                            + " FROM INGRESOS_ALMACEN_DETALLE_ESTADO iade "
                            + " WHERE iade.CANTIDAD_RESTANTE > 0.01 "
                            + " and iade.COD_ESTADO_MATERIAL = 3 "
                            + " and COD_MATERIAL = '"+ salidasAlmacenDetalleItem.getMateriales().getCodMaterial() +"'"
                            + " and iade.COD_INGRESO_ALMACEN = '"+codIngresoAlmacenRechazados+"' ";
                    LOGGER.info("consulta: " + consulta);
                    st.executeUpdate(consulta);
                    
                }
            
            }
            //EJECUTAMOS SP DESPUES DE REGISTRO
            consulta = " exec [PAA_REGISTRO_SALIDA_ALMACEN_LOG] " + salidasAlmacen.getCodSalidaAlmacen() 
                    + " , "+ usuario.getUsuarioModuloBean().getCodUsuarioGlobal()
                    + " , 3";
            LOGGER.info("consulta ejecutar: " + consulta); 
            st.executeUpdate(consulta);
            
            registradoCorrectamente = true;  
            mensaje = "Registrado correctamente.";
            con.commit();
            st.close();
        } catch (Exception e) {
            LOGGER.warn(e);
            con.rollback();
            mensaje = "Ocurrió un error.";
        } finally {
            con.close();
        }        
        return null;
    }
    public String cargarSalidaTraspaso(SalidasAlmacen salidasAlmacen) {
        areasEmpresaList = new ArrayList();
        traspasoGestionar = new Traspasos();
        try {
            areasEmpresaList.add(new SelectItem("0", "-NINGUNO-"));
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select t.COD_ALMACEN_DESTINO, a.NOMBRE_ALMACEN, f.COD_FILIAL , f.NOMBRE_FILIAL"
                    + " from traspasos t"
                    + " left join ALMACENES a on a.COD_ALMACEN = t.COD_ALMACEN_DESTINO"
                    + " left join FILIALES f on f.COD_FILIAL = a.COD_FILIAL"
                    + " where cod_salida_almacen = '" + salidasAlmacen.getCodSalidaAlmacen() + "' ";
            LOGGER.info("consulta: " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            if (rs.next()) {
                traspasoGestionar.getAlmacenDestino().setCodAlmacen(rs.getInt("COD_ALMACEN_DESTINO"));
                traspasoGestionar.getAlmacenDestino().getFiliales().setCodFilial(rs.getInt("COD_FILIAL"));
            } 
            st.close();
            rs.close();
            con.close();

        } catch (Exception e) {
           LOGGER.warn(e);
        }
        return null;
    }
    
    public String almacenDestino_change() {
        cargarAreasEmpresaPorTraspaso(traspasoGestionar);
        return null;
    }
    /**
     * @return the alertaSalidasLote
     */
    public String getAlertaSalidasLote() {
        return alertaSalidasLote;
    }

    public void setAlertaSalidasLote(String alertaSalidasLote) {
        this.alertaSalidasLote = alertaSalidasLote;
    }
    

    public boolean isRegistradoCorrectamente() {
        return registradoCorrectamente;
    }

    public void setRegistradoCorrectamente(boolean registradoCorrectamente) {
        this.registradoCorrectamente = registradoCorrectamente;
    }

    public int getCodIngresoAlmacenRechazados() {
        return codIngresoAlmacenRechazados;
    }

    public void setCodIngresoAlmacenRechazados(int codIngresoAlmacenRechazados) {
        this.codIngresoAlmacenRechazados = codIngresoAlmacenRechazados;
    }

    public boolean isVisible_salidaProduccion() {
        return visible_salidaProduccion;
    }

    public void setVisible_salidaProduccion(boolean visible_salidaProduccion) {
        this.visible_salidaProduccion = visible_salidaProduccion;
    }

    public List<EstadosMaterial> getEstadosMaterialExistencia() {
        return estadosMaterialExistencia;
    }

    public void setEstadosMaterialExistencia(List<EstadosMaterial> estadosMaterialExistencia) {
        this.estadosMaterialExistencia = estadosMaterialExistencia;
    }

    public SalidasAlmacen getSalidasAlmacen() {
        return salidasAlmacen;
    }

    public void setSalidasAlmacen(SalidasAlmacen salidasAlmacen) {
        this.salidasAlmacen = salidasAlmacen;
    }

    public List getAreasEmpresaList() {
        return areasEmpresaList;
    }

    public void setAreasEmpresaList(List areasEmpresaList) {
        this.areasEmpresaList = areasEmpresaList;
    }

    public List getTiposSalidasAlmacenList() {
        return tiposSalidasAlmacenList;
    }

    public void setTiposSalidasAlmacenList(List tiposSalidasAlmacenList) {
        this.tiposSalidasAlmacenList = tiposSalidasAlmacenList;
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

    public List getSalidasAlmacenDetalleList() {
        return salidasAlmacenDetalleList;
    }

    public void setSalidasAlmacenDetalleList(List salidasAlmacenDetalleList) {
        this.salidasAlmacenDetalleList = salidasAlmacenDetalleList;
    }

    public List getSalidasAlmacenDetalleIngresoList() {
        return salidasAlmacenDetalleIngresoList;
    }

    public void setSalidasAlmacenDetalleIngresoList(List salidasAlmacenDetalleIngresoList) {
        this.salidasAlmacenDetalleIngresoList = salidasAlmacenDetalleIngresoList;
    }

    public SalidasAlmacenDetalle getSalidasAlmacenDetalle() {
        return salidasAlmacenDetalle;
    }

    public void setSalidasAlmacenDetalle(SalidasAlmacenDetalle salidasAlmacenDetalle) {
        this.salidasAlmacenDetalle = salidasAlmacenDetalle;
    }

    public HtmlDataTable getSalidasAlmacenDetalleDataTable() {
        return salidasAlmacenDetalleDataTable;
    }

    public void setSalidasAlmacenDetalleDataTable(HtmlDataTable salidasAlmacenDetalleDataTable) {
        this.salidasAlmacenDetalleDataTable = salidasAlmacenDetalleDataTable;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
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

    public SalidasAlmacenDetalle getSalidasAlmacenDetalleAgregar() {
        return salidasAlmacenDetalleAgregar;
    }

    public void setSalidasAlmacenDetalleAgregar(SalidasAlmacenDetalle salidasAlmacenDetalleAgregar) {
        this.salidasAlmacenDetalleAgregar = salidasAlmacenDetalleAgregar;
    }

    public List getMaterialesList() {
        return materialesList;
    }

    public void setMaterialesList(List materialesList) {
        this.materialesList = materialesList;
    }

    public Materiales getBuscarMaterial() {
        return buscarMaterial;
    }

    public void setBuscarMaterial(Materiales buscarMaterial) {
        this.buscarMaterial = buscarMaterial;
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

    public List getEstadosMaterialList() {
        return estadosMaterialList;
    }

    public void setEstadosMaterialList(List estadosMaterialList) {
        this.estadosMaterialList = estadosMaterialList;
    }

    public IngresosAlmacenDetalleEstado getIngresosAlmacenDetalleEstado() {
        return ingresosAlmacenDetalleEstado;
    }

    public void setIngresosAlmacenDetalleEstado(IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstado) {
        this.ingresosAlmacenDetalleEstado = ingresosAlmacenDetalleEstado;
    }

    public List getSalidasAlmacenDetalleAuxList() {
        return salidasAlmacenDetalleAuxList;
    }

    public void setSalidasAlmacenDetalleAuxList(List salidasAlmacenDetalleAuxList) {
        this.salidasAlmacenDetalleAuxList = salidasAlmacenDetalleAuxList;
    }

    public String getMotivoEdicionSalida() {
        return motivoEdicionSalida;
    }

    public void setMotivoEdicionSalida(String motivoEdicionSalida) {
        this.motivoEdicionSalida = motivoEdicionSalida;
    }

    public boolean isSalidaTieneIngresoTraspaso() {
        return salidaTieneIngresoTraspaso;
    }

    public void setSalidaTieneIngresoTraspaso(boolean salidaTieneIngresoTraspaso) {
        this.salidaTieneIngresoTraspaso = salidaTieneIngresoTraspaso;
    }

    public List<SelectItem> getAlmacenDestinoList() {
        return almacenDestinoList;
    }

    public void setAlmacenDestinoList(List<SelectItem> almacenDestinoList) {
        this.almacenDestinoList = almacenDestinoList;
    }

    public List<SelectItem> getFilialList() {
        return filialList;
    }

    public void setFilialList(List<SelectItem> filialList) {
        this.filialList = filialList;
    }

    public Traspasos getTraspasoGestionar() {
        return traspasoGestionar;
    }

    public void setTraspasoGestionar(Traspasos traspasoGestionar) {
        this.traspasoGestionar = traspasoGestionar;
    }

    public OrdenesCompra getFiltroOrdenCompra() {
        return filtroOrdenCompra;
    }

    public void setFiltroOrdenCompra(OrdenesCompra filtroOrdenCompra) {
        this.filtroOrdenCompra = filtroOrdenCompra;
    }

    public List<SelectItem> getProveedoresList() {
        return proveedoresList;
    }

    public void setProveedoresList(List<SelectItem> proveedoresList) {
        this.proveedoresList = proveedoresList;
    }

    public List<SelectItem> getRepresentantesList() {
        return representantesList;
    }

    public void setRepresentantesList(List<SelectItem> representantesList) {
        this.representantesList = representantesList;
    }

    public List<SelectItem> getPersonalList() {
        return personalList;
    }

    public void setPersonalList(List<SelectItem> personalList) {
        this.personalList = personalList;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public List<OrdenesCompra> getOrdenesCompraList() {
        return ordenesCompraList;
    }

    public void setOrdenesCompraList(List<OrdenesCompra> ordenesCompraList) {
        this.ordenesCompraList = ordenesCompraList;
    }

    public List<OrdenesCompraDetalle> getOrdenesCompraDetalleList() {
        return ordenesCompraDetalleList;
    }

    public void setOrdenesCompraDetalleList(List<OrdenesCompraDetalle> ordenesCompraDetalleList) {
        this.ordenesCompraDetalleList = ordenesCompraDetalleList;
    }

    public String getCodCapitulo() {
        return codCapitulo;
    }

    public void setCodCapitulo(String codCapitulo) {
        this.codCapitulo = codCapitulo;
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

    public HtmlDataTable getOrdenesCompraDataTable() {
        return ordenesCompraDataTable;
    }

    public void setOrdenesCompraDataTable(HtmlDataTable ordenesCompraDataTable) {
        this.ordenesCompraDataTable = ordenesCompraDataTable;
    }

    public boolean isProductoHabilitado() {
        return productoHabilitado;
    }

    public void setProductoHabilitado(boolean productoHabilitado) {
        this.productoHabilitado = productoHabilitado;
    }

    public String getMensajeInyectables() {
        return mensajeInyectables;
    }

    public void setMensajeInyectables(String mensajeInyectables) {
        this.mensajeInyectables = mensajeInyectables;
    }

    public List<SelectItem> getMaquinariasList() {
        return maquinariasList;
    }

    public void setMaquinariasList(List<SelectItem> maquinariasList) {
        this.maquinariasList = maquinariasList;
    }

    public SolicitudMantenimiento getSolicitudMantenimiento() {
        return solicitudMantenimiento;
    }

    public void setSolicitudMantenimiento(SolicitudMantenimiento solicitudMantenimiento) {
        this.solicitudMantenimiento = solicitudMantenimiento;
    }

    public boolean isSalidaEditable() {
        return salidaEditable;
    }

    public void setSalidaEditable(boolean salidaEditable) {
        this.salidaEditable = salidaEditable;
    }

    public boolean isAdministradorAlmacen() {
        return administradorAlmacen;
    }

    public void setAdministradorAlmacen(boolean administradorAlmacen) {
        this.administradorAlmacen = administradorAlmacen;
    }

    public ProgramaProduccion getPproduccion() {
        return pproduccion;
    }

    public void setPproduccion(ProgramaProduccion pproduccion) {
        this.pproduccion = pproduccion;
    }

    public boolean isEmptyListEmpaqueSecundario() {
        return emptyListEmpaqueSecundario;
    }

    public void setEmptyListEmpaqueSecundario(boolean emptyListEmpaqueSecundario) {
        this.emptyListEmpaqueSecundario = emptyListEmpaqueSecundario;
    }

    public HtmlDataTable getSalidasAlmacenDetalleDataTablePP() {
        return salidasAlmacenDetalleDataTablePP;
    }

    public void setSalidasAlmacenDetalleDataTablePP(HtmlDataTable salidasAlmacenDetalleDataTablePP) {
        this.salidasAlmacenDetalleDataTablePP = salidasAlmacenDetalleDataTablePP;
    }

    public ManagedAccesoSistema getUsuario() {
        return usuario;
    }

    public void setUsuario(ManagedAccesoSistema usuario) {
        this.usuario = usuario;
    }
    
    
    
            
}