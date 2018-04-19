/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofar.web;

import com.cofar.bean.Materiales;
import com.cofar.bean.SalidasAlmacenDetalle;
import com.cofar.bean.SalidasAlmacenDetalleIngreso;
import com.cofar.bean.Traspasos;
import com.cofar.service.AssignCostService;
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

import java.util.Map;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.richfaces.component.html.HtmlDataTable;

/**
 *
 * @author hvaldivia
 */
public class ManagedSalidaTraspasoAlmacen extends ManagedBean {

    private Logger LOGGER;
    private static final int COD_TIPO_SALIDA_TRASPASO = 3 ;
    private List traspasosList = new ArrayList();
    private ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
    private Traspasos traspasosBuscar = new Traspasos();
    private List filialList = new ArrayList();
    private Traspasos traspasos = new Traspasos();
    private List almacenDestinoList = new ArrayList();
    private List areaDestinoList = new ArrayList();
    private List capitulosList = new ArrayList();
    private List gruposList = new ArrayList();
    private Materiales buscarMaterial = new Materiales();
    private List materialesBuscarList = new ArrayList();
    private List materialesEditarBuscarList = new ArrayList();
    private HtmlDataTable materialesBuscarDataTable = new HtmlDataTable();
    private HtmlDataTable materialesEditarBuscarDataTable = new HtmlDataTable();
    private List salidasAlmacenDetalleList = new ArrayList();
    private HtmlDataTable salidasAlmacenDetalleDataTable = new HtmlDataTable();
    SalidasAlmacenDetalle salidasAlmacenDetalle = new SalidasAlmacenDetalle();
    private boolean administradorAlmacen = false;

    private String mensaje = "";

    private Traspasos traspasosEditar = new Traspasos();
    //inicio alejandro
    private List salidasVerificar2 = new ArrayList();
    private Object salida = null;

    //final alejandro
    //inicio alejandro 2
    private Traspasos traspasoBuscar = new Traspasos();
    private Materiales materialBuscar = new Materiales();

    private List<SelectItem> listaGrupos = new ArrayList<SelectItem>();
    private List<SelectItem> listaMateriales = new ArrayList<SelectItem>();
    private List<SelectItem> listaGestiones = new ArrayList<SelectItem>();
    private java.util.Date fechaInicio1 = new Date();
    private java.util.Date fechaFinal1 = new Date();
    //final alejandro 2
    //inicio ale traspaso
    private boolean traspasoRechazados = false;
    //final ale traspaso
    private HtmlDataTable salidasAlmacenDetalleEditarDataTable = new HtmlDataTable();

    public List getTraspasosList() {
        return traspasosList;
    }

    public void setTraspasosList(List traspasosList) {
        this.traspasosList = traspasosList;
    }

    public List getFilialList() {
        return filialList;
    }

    public void setFilialList(List filialList) {
        this.filialList = filialList;
    }

    public Traspasos getTraspasos() {
        return traspasos;
    }

    public void setTraspasos(Traspasos traspasos) {
        this.traspasos = traspasos;
    }

    public List getAlmacenDestinoList() {
        return almacenDestinoList;
    }

    public void setAlmacenDestinoList(List almacenDestinoList) {
        this.almacenDestinoList = almacenDestinoList;
    }

    public List getAreaDestinoList() {
        return areaDestinoList;
    }

    public void setAreaDestinoList(List areaDestinoList) {
        this.areaDestinoList = areaDestinoList;
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
    public int getSalidasAlmacenDetalleListSize() {
        return (salidasAlmacenDetalleList != null ? salidasAlmacenDetalleList.size() : 0);
    }

    public List getSalidasAlmacenDetalleList() {
        return salidasAlmacenDetalleList;
    }

    public void setSalidasAlmacenDetalleList(List salidasAlmacenDetalleList) {
        this.salidasAlmacenDetalleList = salidasAlmacenDetalleList;
    }

    public HtmlDataTable getSalidasAlmacenDetalleDataTable() {
        return salidasAlmacenDetalleDataTable;
    }

    public void setSalidasAlmacenDetalleDataTable(HtmlDataTable salidasAlmacenDetalleDataTable) {
        this.salidasAlmacenDetalleDataTable = salidasAlmacenDetalleDataTable;
    }

    public SalidasAlmacenDetalle getSalidasAlmacenDetalle() {
        return salidasAlmacenDetalle;
    }

    public void setSalidasAlmacenDetalle(SalidasAlmacenDetalle salidasAlmacenDetalle) {
        this.salidasAlmacenDetalle = salidasAlmacenDetalle;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Traspasos getTraspasosEditar() {
        return traspasosEditar;
    }

    public void setTraspasosEditar(Traspasos traspasosEditar) {
        this.traspasosEditar = traspasosEditar;
    }

    public HtmlDataTable getMaterialesEditarBuscarDataTable() {
        return materialesEditarBuscarDataTable;
    }

    public void setMaterialesEditarBuscarDataTable(HtmlDataTable materialesEditarBuscarDataTable) {
        this.materialesEditarBuscarDataTable = materialesEditarBuscarDataTable;
    }

    public List getMaterialesEditarBuscarList() {
        return materialesEditarBuscarList;
    }

    public void setMaterialesEditarBuscarList(List materialesEditarBuscarList) {
        this.materialesEditarBuscarList = materialesEditarBuscarList;
    }

    public Date getFechaFinal1() {
        return fechaFinal1;
    }

    public void setFechaFinal1(Date fechaFinal1) {
        this.fechaFinal1 = fechaFinal1;
    }

    public Date getFechaInicio1() {
        return fechaInicio1;
    }

    public void setFechaInicio1(Date fechaInicio1) {
        this.fechaInicio1 = fechaInicio1;
    }

    public List<SelectItem> getListaGestiones() {
        return listaGestiones;
    }

    public void setListaGestiones(List<SelectItem> listaGestiones) {
        this.listaGestiones = listaGestiones;
    }

    public List<SelectItem> getListaGrupos() {
        return listaGrupos;
    }

    public void setListaGrupos(List<SelectItem> listaGrupos) {
        this.listaGrupos = listaGrupos;
    }

    public List<SelectItem> getListaMateriales() {
        return listaMateriales;
    }

    public void setListaMateriales(List<SelectItem> listaMateriales) {
        this.listaMateriales = listaMateriales;
    }

    public Materiales getMaterialBuscar() {
        return materialBuscar;
    }

    public void setMaterialBuscar(Materiales materialBuscar) {
        this.materialBuscar = materialBuscar;
    }

    public Object getSalida() {
        return salida;
    }

    public void setSalida(Object salida) {
        this.salida = salida;
    }

    public List getSalidasVerificar2() {
        return salidasVerificar2;
    }

    public void setSalidasVerificar2(List salidasVerificar2) {
        this.salidasVerificar2 = salidasVerificar2;
    }

    public Traspasos getTraspasoBuscar() {
        return traspasoBuscar;
    }

    public void setTraspasoBuscar(Traspasos traspasoBuscar) {
        this.traspasoBuscar = traspasoBuscar;
    }

    public Traspasos getTraspasosBuscar() {
        return traspasosBuscar;
    }

    public void setTraspasosBuscar(Traspasos traspasosBuscar) {
        this.traspasosBuscar = traspasosBuscar;
    }

    public ManagedAccesoSistema getUsuario() {
        return usuario;
    }

    public void setUsuario(ManagedAccesoSistema usuario) {
        this.usuario = usuario;
    }

    public HtmlDataTable getSalidasAlmacenDetalleEditarDataTable() {
        return salidasAlmacenDetalleEditarDataTable;
    }

    public void setSalidasAlmacenDetalleEditarDataTable(HtmlDataTable salidasAlmacenDetalleEditarDataTable) {
        this.salidasAlmacenDetalleEditarDataTable = salidasAlmacenDetalleEditarDataTable;
    }

    /**
     * Creates a new instance of ManagedSalidaTraspasoAlmacen
     */
    public ManagedSalidaTraspasoAlmacen() {
        LOGGER = LogManager.getLogger("Traspasos");
    }

    public String getCargarContenidoSalidasTraspasoAlmacen() {
        this.cargarPermisoPersonal();
        try {
            //inicio alejandro 2
            getMaterialBuscar().setCodMaterial("0");
            getMaterialBuscar().getGrupos().setCodGrupo(0);
            getMaterialBuscar().getGrupos().getCapitulos().setCodCapitulo(0);
            getTraspasoBuscar().getSalidasAlmacen().getGestiones().setCodGestion(usuario.getGestionesGlobal().getCodGestion());
            getTraspasoBuscar().getSalidasAlmacen().setNroSalidaAlmacen(0);
            getTraspasoBuscar().getAlmacenDestino().getFiliales().setCodFilial(0);
            getTraspasoBuscar().getAlmacenDestino().setCodAlmacen(0);
            setFechaInicio1(null);
            setFechaFinal1(null);
            cargarGestiones();
            begin = 1;
            end = 10;
            capitulosList = cargarCapitulos();
            listaMateriales.clear();
            listaMateriales.add(new SelectItem(0, "-TODOS-"));
            listaGrupos.clear();
            listaGrupos.add(new SelectItem(0, "-TODOS-"));

            //final alejandro 2
            filialList = this.cargaFiliales();
            almacenDestinoList = this.cargaAlmacenDestino();
            traspasosList = this.listadoSalidasTraspasoAlmacen();
            this.cantidadfilas = traspasosList.size();
        } catch (Exception e) {
            LOGGER.warn("error",e);
        }
        return null;
    }

    //inicio alejandro 2
    public String cambio_capitulo() {
        try {
            Connection con = null;
            con = Util.openConnection(con);
            LOGGER.debug("wentro al cambio");
            String consulta = " SELECT GR.COD_GRUPO,GR.NOMBRE_GRUPO FROM GRUPOS GR WHERE GR.COD_CAPITULO = '" + materialBuscar.getGrupos().getCapitulos().getCodCapitulo() + "' AND GR.COD_ESTADO_REGISTRO = 1 ORDER BY GR.NOMBRE_GRUPO asc";
            LOGGER.debug("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            getListaGrupos().clear();
            getListaGrupos().add(new SelectItem("0", "-TODOS-"));
            while (rs.next()) {
                getListaGrupos().add(new SelectItem(rs.getString("COD_GRUPO"), rs.getString("NOMBRE_GRUPO")));
            }
            listaMateriales.clear();
            listaMateriales.add(new SelectItem(0, "-TODOS-"));

            rs.close();
            st.close();
            con.close();

        } catch (Exception e) {
            LOGGER.warn("error",e);
        }
        return null;
    }

    public String cambio_grupo() {
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select m.NOMBRE_MATERIAL,m.COD_MATERIAL from MATERIALES m where m.COD_GRUPO ='" + materialBuscar.getGrupos().getCodGrupo() + "' and m.COD_ESTADO_REGISTRO=1 order by m.NOMBRE_MATERIAL asc";
            LOGGER.debug("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            getListaMateriales().clear();
            getListaMateriales().add(new SelectItem("0", "-TODOS-"));
            while (rs.next()) {
                getListaMateriales().add(new SelectItem(rs.getString("COD_MATERIAL"), rs.getString("NOMBRE_MATERIAL")));
            }
            rs.close();
            st.close();
            con.close();

        } catch (Exception e) {
            LOGGER.warn("error",e);
        }
        return null;
    }

    public void cargarGestiones() {
        Connection con = null;
        try {
            con = Util.openConnection(con);
            String sql = "select cod_gestion,nombre_gestion from gestiones order by cod_gestion";
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(sql);
            getListaGestiones().clear();
            getListaGestiones().add(new SelectItem("0", "-TODOS-"));
            while (rs.next()) {
                getListaGestiones().add(new SelectItem(rs.getString("cod_gestion"), rs.getString("nombre_gestion")));
            }
            if (rs != null) {
                rs.close();
                st.close();
                con.close();
            }
        } catch (Exception e) {
            LOGGER.warn("error",e);
        }
    }

    public String siguiente_action() {
        LOGGER.debug("entro al action siguiente ");
        super.next();
        // this.cargarContenidoIngresoAlmacenOrdenCompra();
        traspasosList = this.listadoSalidasTraspasoAlmacen();
        this.cantidadfilas = traspasosList.size();
        return "";
    }

    public String atras_action() {
        super.back();
        // this.cargarContenidoIngresoAlmacenOrdenCompra();
        traspasosList = this.listadoSalidasTraspasoAlmacen();
        this.cantidadfilas = traspasosList.size();
        return "";
    }

    public String buscarTraspaso_action() {
        try {
            begin = 1;
            end = 10;
            traspasosList = this.listadoSalidasTraspasoAlmacen();
            this.cantidadfilas = traspasosList.size();
            //this.cargarSalidasAlmacen();
        } catch (Exception e) {
            LOGGER.warn("error",e);
        }
        return null;
    }

    public String cambio_filial() {
        try {
            almacenDestinoList = this.listadoAlmacenDestino(traspasoBuscar);
            areaDestinoList.clear();
            areaDestinoList.add(new SelectItem("0", "-NINGUNO-"));

        } catch (Exception e) {
            LOGGER.warn("error",e);
        }
        return null;
    }

    //final alejandro 2
    public List listadoSalidasTraspasoAlmacen1() {
        List traspasosList = new ArrayList();
        try {

            String consulta = " select top 10 s.COD_SALIDA_ALMACEN,s.NRO_SALIDA_ALMACEN,s.FECHA_SALIDA_ALMACEN,s.OBS_SALIDA_ALMACEN,s.ESTADO_SISTEMA, "
                    + " gest.COD_GESTION,gest.NOMBRE_GESTION,p.COD_PERSONAL,p.NOMBRES_PERSONAL,p.AP_PATERNO_PERSONAL,p.AP_MATERNO_PERSONAL, "
                    + " e.COD_ESTADO_TRASPASO,e.NOMBRE_ESTADO_TRASPASO,esa.COD_ESTADO_SALIDA_ALMACEN,esa.NOMBRE_ESTADO_SALIDA_ALMACEN,tsa.COD_TIPO_SALIDA_ALMACEN,tsa.NOMBRE_TIPO_SALIDA_ALMACEN, "
                    + " a1.COD_ALMACEN COD_ALMACEN1,a1.NOMBRE_ALMACEN NOMBRE_ALMACEN1,a2.COD_ALMACEN COD_ALMACEN2,a2.NOMBRE_ALMACEN NOMBRE_ALMACEN2,"
                    + " f1.COD_FILIAL COD_FILIAL1,f1.NOMBRE_FILIAL NOMBRE_FILIAL1,f2.COD_FILIAL COD_FILIAL2,f2.NOMBRE_FILIAL NOMBRE_FILIAL2,s.cod_area_empresa,s.cod_form_salida "
                    + " from salidas_almacen s  "
                    + " inner join traspasos t on t.COD_SALIDA_ALMACEN = s.COD_SALIDA_ALMACEN "
                    + " inner join personal p on p.COD_PERSONAL = s.COD_PERSONAL "
                    + " inner join ESTADOS_TRASPASO e on e.COD_ESTADO_TRASPASO = t.COD_ESTADO_TRASPASO "
                    + " inner join gestiones gest on gest.COD_GESTION = s.COD_GESTION "
                    + " inner join ESTADOS_SALIDAS_ALMACEN esa on esa.COD_ESTADO_SALIDA_ALMACEN = s.COD_ESTADO_SALIDA_ALMACEN "
                    + " inner join TIPOS_SALIDAS_ALMACEN tsa on tsa.COD_TIPO_SALIDA_ALMACEN = s.COD_TIPO_SALIDA_ALMACEN "
                    + " inner join ALMACENES a1 on a1.COD_ALMACEN = t.COD_ALMACEN_ORIGEN "
                    + " inner join ALMACENES a2 on a2.COD_ALMACEN = t.COD_ALMACEN_DESTINO "
                    + " inner join FILIALES f1 on f1.COD_FILIAL = a1.COD_FILIAL "
                    + " inner join FILIALES f2 on f2.COD_FILIAL = a2.COD_FILIAL"
                    + " where s.ESTADO_SISTEMA = 1 and s.COD_TIPO_SALIDA_ALMACEN in(3,9) "
                    + " and s.COD_GESTION = '" + usuario.getGestionesGlobal().getCodGestion() + "' "
                    + " and t.COD_ESTADO_TRASPASO = '1' "
                    + " order by s.FECHA_SALIDA_ALMACEN desc,s.NRO_SALIDA_ALMACEN desc ";
            //select cod_almacen_frv from almacenes_frv where cod_almacen_frv
            Connection con = null;
            con = Util.openConnection(con);
            con = Util.openConnection(con);
            LOGGER.debug("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            traspasosList.clear();

            while (rs.next()) {
                Traspasos traspasos = new Traspasos();
                traspasos.getSalidasAlmacen().setCodSalidaAlmacen(rs.getInt("COD_SALIDA_ALMACEN"));
                traspasos.getSalidasAlmacen().setNroSalidaAlmacen(rs.getInt("NRO_SALIDA_ALMACEN"));
                traspasos.getSalidasAlmacen().setFechaSalidaAlmacen(rs.getDate("FECHA_SALIDA_ALMACEN"));
                traspasos.getSalidasAlmacen().setObsSalidaAlmacen(rs.getString("OBS_SALIDA_ALMACEN"));
                traspasos.getSalidasAlmacen().setEstadoSistema(rs.getInt("ESTADO_SISTEMA"));
                traspasos.getSalidasAlmacen().getGestiones().setCodGestion(rs.getString("COD_GESTION"));
                traspasos.getSalidasAlmacen().getGestiones().setNombreGestion(rs.getString("NOMBRE_GESTION"));
                traspasos.getSalidasAlmacen().getPersonal().setCodPersonal(rs.getString("COD_PERSONAL"));
                traspasos.getSalidasAlmacen().getPersonal().setNombrePersonal(rs.getString("NOMBRES_PERSONAL"));
                traspasos.getSalidasAlmacen().getPersonal().setApPaternoPersonal(rs.getString("AP_PATERNO_PERSONAL"));
                traspasos.getSalidasAlmacen().getPersonal().setApMaternoPersonal(rs.getString("AP_MATERNO_PERSONAL"));
                traspasos.getEstadosTraspaso().setCodEstadoTraspaso(rs.getInt("COD_ESTADO_TRASPASO"));
                traspasos.getEstadosTraspaso().setNombreEstadoTraspaso(rs.getString("NOMBRE_ESTADO_TRASPASO"));
                traspasos.getSalidasAlmacen().getEstadosSalidasAlmacen().setCodEstadoSalidaAlmacen(rs.getInt("COD_ESTADO_SALIDA_ALMACEN"));
                traspasos.getSalidasAlmacen().getEstadosSalidasAlmacen().setNombreEstadoSalidaAlmacen(rs.getString("NOMBRE_ESTADO_SALIDA_ALMACEN"));
                traspasos.getSalidasAlmacen().getTiposSalidasAlmacen().setCodTipoSalidaAlmacen(rs.getInt("COD_TIPO_SALIDA_ALMACEN"));
                traspasos.getSalidasAlmacen().getTiposSalidasAlmacen().setNombreTipoSalidaAlmacen(rs.getString("NOMBRE_TIPO_SALIDA_ALMACEN"));
                traspasos.getAlmacenOrigen().setCodAlmacen(rs.getInt("COD_ALMACEN1"));
                traspasos.getAlmacenOrigen().setNombreAlmacen(rs.getString("NOMBRE_ALMACEN1"));
                traspasos.getAlmacenDestino().setCodAlmacen(rs.getInt("COD_ALMACEN2"));
                traspasos.getAlmacenDestino().setNombreAlmacen(rs.getString("NOMBRE_ALMACEN2"));
                traspasos.getAlmacenOrigen().getFiliales().setCodFilial(rs.getInt("COD_FILIAL1"));
                traspasos.getAlmacenOrigen().getFiliales().setNombreFilial(rs.getString("NOMBRE_FILIAL1"));
                traspasos.getAlmacenDestino().getFiliales().setCodFilial(rs.getInt("COD_FILIAL2"));
                traspasos.getAlmacenDestino().getFiliales().setNombreFilial(rs.getString("NOMBRE_FILIAL2"));
                traspasos.getSalidasAlmacen().getAreasEmpresa().setCodAreaEmpresa(rs.getString("cod_area_empresa"));
                traspasos.getSalidasAlmacen().setCodFormSalida(rs.getInt("COD_FORM_SALIDA"));

                traspasosList.add(traspasos);

            }
            rs.close();
            st.close();
            con.close();

        } catch (Exception e) {
            LOGGER.warn("error",e);
        }
        return traspasosList;
    }

    public List listadoSalidasTraspasoAlmacen() {
        List traspasosList = new ArrayList();
        try {

            String consulta = "select * from (select ROW_NUMBER() OVER (ORDER BY s.FECHA_SALIDA_ALMACEN desc,s.NRO_SALIDA_ALMACEN desc ) as 'FILAS', "
                    + " s.COD_SALIDA_ALMACEN,s.NRO_SALIDA_ALMACEN,s.FECHA_SALIDA_ALMACEN,s.OBS_SALIDA_ALMACEN,s.ESTADO_SISTEMA, "
                    + " gest.COD_GESTION,gest.NOMBRE_GESTION,p.COD_PERSONAL,p.NOMBRES_PERSONAL,p.AP_PATERNO_PERSONAL,p.AP_MATERNO_PERSONAL, "
                    + " e.COD_ESTADO_TRASPASO,e.NOMBRE_ESTADO_TRASPASO,esa.COD_ESTADO_SALIDA_ALMACEN,esa.NOMBRE_ESTADO_SALIDA_ALMACEN,tsa.COD_TIPO_SALIDA_ALMACEN,tsa.NOMBRE_TIPO_SALIDA_ALMACEN, "
                    + " a1.COD_ALMACEN COD_ALMACEN1,a1.NOMBRE_ALMACEN NOMBRE_ALMACEN1,a2.COD_ALMACEN COD_ALMACEN2,a2.NOMBRE_ALMACEN NOMBRE_ALMACEN2,"
                    + " f1.COD_FILIAL COD_FILIAL1,f1.NOMBRE_FILIAL NOMBRE_FILIAL1,f2.COD_FILIAL COD_FILIAL2,f2.NOMBRE_FILIAL NOMBRE_FILIAL2,s.cod_area_empresa,s.cod_form_salida "
                    + //inicio ale traspaso
                    " ,ISNULL(tt.cod_tipo_traspaso,1) AS CTT,ISNULL(tt.nombre_tipo_traspaso,'NORMAL')  AS NTT"
                    + " from salidas_almacen s  "
                    + " inner join traspasos t on t.COD_SALIDA_ALMACEN = s.COD_SALIDA_ALMACEN "
                    + //inicio ale traspaso anular
                    " left outer join tipos_traspasos  tt on t.cod_tipo_traspaso=tt.cod_tipo_traspaso"
                    + //final ale traspaso anular
                    " inner join personal p on p.COD_PERSONAL = s.COD_PERSONAL "
                    + " inner join ESTADOS_TRASPASO e on e.COD_ESTADO_TRASPASO = t.COD_ESTADO_TRASPASO "
                    + " inner join gestiones gest on gest.COD_GESTION = s.COD_GESTION "
                    + " inner join ESTADOS_SALIDAS_ALMACEN esa on esa.COD_ESTADO_SALIDA_ALMACEN = s.COD_ESTADO_SALIDA_ALMACEN "
                    + " inner join TIPOS_SALIDAS_ALMACEN tsa on tsa.COD_TIPO_SALIDA_ALMACEN = s.COD_TIPO_SALIDA_ALMACEN "
                    + " inner join ALMACENES a1 on a1.COD_ALMACEN = t.COD_ALMACEN_ORIGEN "
                    + " inner join ALMACENES a2 on a2.COD_ALMACEN = t.COD_ALMACEN_DESTINO "
                    + " inner join FILIALES f1 on f1.COD_FILIAL = a1.COD_FILIAL "
                    + " inner join FILIALES f2 on f2.COD_FILIAL = a2.COD_FILIAL"
                    + " where s.ESTADO_SISTEMA = 1 and s.COD_TIPO_SALIDA_ALMACEN in(3,9) "
                    + //  " and s.COD_GESTION = '"+usuario.getGestionesGlobal().getCodGestion()+"' " +
                    " and t.COD_ESTADO_TRASPASO = '1' and t.cod_almacen_origen = '" + usuario.getAlmacenesGlobal().getCodAlmacen() + "'";
            if (!traspasoBuscar.getSalidasAlmacen().getGestiones().getCodGestion().equals("0")) {
                consulta += " and s.COD_GESTION= '" + getTraspasoBuscar().getSalidasAlmacen().getGestiones().getCodGestion() + "' ";

            }
            if (getTraspasoBuscar().getSalidasAlmacen().getNroSalidaAlmacen() > 0) {
                consulta += " and s.NRO_SALIDA_ALMACEN ='" + getTraspasoBuscar().getSalidasAlmacen().getNroSalidaAlmacen() + "' ";
            }
            if (getTraspasoBuscar().getAlmacenDestino().getFiliales().getCodFilial() > 0) {
                if (getTraspasoBuscar().getAlmacenDestino().getCodAlmacen() > 0) {
                    consulta += " and a2.COD_ALMACEN='" + getTraspasoBuscar().getAlmacenDestino().getCodAlmacen() + "' ";
                } else {
                    consulta += " and f2.COD_FILIAL = '" + getTraspasoBuscar().getAlmacenDestino().getFiliales().getCodFilial() + "' ";
                }
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            if (getFechaInicio1() != null && getFechaFinal1() != null) {
                consulta += " and s.FECHA_SALIDA_ALMACEN BETWEEN '" + sdf.format(getFechaInicio1()) + " 00:00:00' and '" + sdf.format(getFechaFinal1()) + " 23:59:59' ";

            }
            if (getMaterialBuscar().getGrupos().getCapitulos().getCodCapitulo() > 0) {
                if (getMaterialBuscar().getGrupos().getCodGrupo() > 0) {
                    if (!getMaterialBuscar().getCodMaterial().equals("0")) {
                        consulta += " and  s.COD_SALIDA_ALMACEN in (select sad.COD_SALIDA_ALMACEN from SALIDAS_ALMACEN_DETALLE sad where sad.COD_MATERIAL='" + getMaterialBuscar().getCodMaterial() + "') ";

                    } else {
                        consulta += " and s.COD_SALIDA_ALMACEN in (select sad.COD_SALIDA_ALMACEN from SALIDAS_ALMACEN_DETALLE sad where sad.COD_MATERIAL in (select m.COD_MATERIAL from MATERIALES m where m.COD_GRUPO ='" + getMaterialBuscar().getGrupos().getCodGrupo() + "'))";
                    }
                } else {
                    consulta += " and s.COD_SALIDA_ALMACEN in (select sad.COD_SALIDA_ALMACEN from SALIDAS_ALMACEN_DETALLE sad where sad.COD_MATERIAL in (select m.COD_MATERIAL from MATERIALES m where m.COD_GRUPO IN(select gr.COD_GRUPO from GRUPOS gr where gr.COD_CAPITULO ='" + getMaterialBuscar().getGrupos().getCapitulos().getCodCapitulo() + "')))";

                }
            }

            consulta += " ) AS listado where FILAS BETWEEN " + begin + " AND " + end + "   ";

            //select cod_almacen_frv from almacenes_frv where cod_almacen_frv
            Connection con = null;
            con = Util.openConnection(con);
            con = Util.openConnection(con);
            LOGGER.debug("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            traspasosList.clear();

            while (rs.next()) {
                Traspasos traspasos = new Traspasos();
                traspasos.getSalidasAlmacen().setCodSalidaAlmacen(rs.getInt("COD_SALIDA_ALMACEN"));
                traspasos.getSalidasAlmacen().setNroSalidaAlmacen(rs.getInt("NRO_SALIDA_ALMACEN"));
                traspasos.getSalidasAlmacen().setFechaSalidaAlmacen(rs.getDate("FECHA_SALIDA_ALMACEN"));
                traspasos.getSalidasAlmacen().setObsSalidaAlmacen(rs.getString("OBS_SALIDA_ALMACEN"));
                traspasos.getSalidasAlmacen().setEstadoSistema(rs.getInt("ESTADO_SISTEMA"));
                traspasos.getSalidasAlmacen().getGestiones().setCodGestion(rs.getString("COD_GESTION"));
                traspasos.getSalidasAlmacen().getGestiones().setNombreGestion(rs.getString("NOMBRE_GESTION"));
                traspasos.getSalidasAlmacen().getPersonal().setCodPersonal(rs.getString("COD_PERSONAL"));
                traspasos.getSalidasAlmacen().getPersonal().setNombrePersonal(rs.getString("NOMBRES_PERSONAL"));
                traspasos.getSalidasAlmacen().getPersonal().setApPaternoPersonal(rs.getString("AP_PATERNO_PERSONAL"));
                traspasos.getSalidasAlmacen().getPersonal().setApMaternoPersonal(rs.getString("AP_MATERNO_PERSONAL"));
                traspasos.getEstadosTraspaso().setCodEstadoTraspaso(rs.getInt("COD_ESTADO_TRASPASO"));
                traspasos.getEstadosTraspaso().setNombreEstadoTraspaso(rs.getString("NOMBRE_ESTADO_TRASPASO"));
                traspasos.getSalidasAlmacen().getEstadosSalidasAlmacen().setCodEstadoSalidaAlmacen(rs.getInt("COD_ESTADO_SALIDA_ALMACEN"));
                traspasos.getSalidasAlmacen().getEstadosSalidasAlmacen().setNombreEstadoSalidaAlmacen(rs.getString("NOMBRE_ESTADO_SALIDA_ALMACEN"));
                traspasos.getSalidasAlmacen().getTiposSalidasAlmacen().setCodTipoSalidaAlmacen(rs.getInt("COD_TIPO_SALIDA_ALMACEN"));
                traspasos.getSalidasAlmacen().getTiposSalidasAlmacen().setNombreTipoSalidaAlmacen(rs.getString("NOMBRE_TIPO_SALIDA_ALMACEN"));
                traspasos.getAlmacenOrigen().setCodAlmacen(rs.getInt("COD_ALMACEN1"));
                traspasos.getAlmacenOrigen().setNombreAlmacen(rs.getString("NOMBRE_ALMACEN1"));
                traspasos.getAlmacenDestino().setCodAlmacen(rs.getInt("COD_ALMACEN2"));
                traspasos.getAlmacenDestino().setNombreAlmacen(rs.getString("NOMBRE_ALMACEN2"));
                traspasos.getAlmacenOrigen().getFiliales().setCodFilial(rs.getInt("COD_FILIAL1"));
                traspasos.getAlmacenOrigen().getFiliales().setNombreFilial(rs.getString("NOMBRE_FILIAL1"));
                traspasos.getAlmacenDestino().getFiliales().setCodFilial(rs.getInt("COD_FILIAL2"));
                traspasos.getAlmacenDestino().getFiliales().setNombreFilial(rs.getString("NOMBRE_FILIAL2"));
                traspasos.getSalidasAlmacen().getAreasEmpresa().setCodAreaEmpresa(rs.getString("cod_area_empresa"));
                traspasos.getSalidasAlmacen().setCodFormSalida(rs.getInt("COD_FORM_SALIDA"));
                //inicio ale traspaso
                traspasos.getTipoTraspaso().setCodTipoTraspaso(rs.getString("CTT"));
                traspasos.getTipoTraspaso().setNombreTipoTraspaso(rs.getString("NTT"));
                //final ale traspaso
                traspasosList.add(traspasos);

            }
            rs.close();
            st.close();
            con.close();

        } catch (Exception e) {
            LOGGER.warn("error",e);
        }
        return traspasosList;
    }

    public String almacenDestino_change() {
        try {

            areaDestinoList = this.listaAreaDestino(traspasos);
        } catch (Exception e) {
            LOGGER.warn("error",e);
        }
        return null;
    }

    public String almacenDestinoEditar_change() {
        try {
            areaDestinoList = this.listaAreaDestino(traspasosEditar);
        } catch (Exception e) {
            LOGGER.warn("error",e);
        }
        return null;
    }

    public List listaAreaDestino(Traspasos traspasos) {
        List areaDestinoList = new ArrayList();
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select cod_almacen_frv from almacenes_frv where cod_almacen_frv= '" + traspasos.getAlmacenDestino().getCodAlmacen() + "' ";
            LOGGER.debug("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            if (rs.next()) {
                consulta = " select cod_area_empresa,nombre_area_empresa from  areas_empresa where cod_estado_registro=1 and (cod_area_empresa=98 or cod_area_empresa=99 or cod_area_empresa=100) order by nombre_area_empresa ";

            } else {
                consulta = " select cod_area_empresa,nombre_area_empresa from  areas_empresa where cod_estado_registro=1 and cod_area_empresa<>100  "
                        + " and cod_area_empresa<>98 and cod_area_empresa<>99 order by nombre_area_empresa ";

            }
            Statement st1 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            LOGGER.debug("consulta" + consulta);
            ResultSet rs1 = st1.executeQuery(consulta);
            areaDestinoList.clear();
            areaDestinoList.add(new SelectItem("0", "-NINGUNO-"));
            while (rs1.next()) {
                areaDestinoList.add(new SelectItem(rs1.getString("cod_area_empresa"), rs1.getString("nombre_area_empresa")));
            }
            st.close();
            st1.close();
            rs1.close();
            rs.close();
            con.close();

        } catch (Exception e) {
            LOGGER.warn("error",e);
        }
        return areaDestinoList;
    }

    public List cargaFiliales() {
        List filialesList = new ArrayList();
        try {

            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select f.COD_FILIAL,f.NOMBRE_FILIAL from filiales f where f.COD_ESTADO_REGISTRO = 1 ";

            ResultSet rs = null;
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            filialesList.clear();
            rs = st.executeQuery(consulta);
            filialesList.add(new SelectItem("0", "-NINGUNO-"));
            while (rs.next()) {
                filialesList.add(new SelectItem(rs.getString("COD_FILIAL"), rs.getString("NOMBRE_FILIAL")));
            }

            if (rs != null) {
                rs.close();
                st.close();
                rs = null;
                st = null;
            }

        } catch (Exception e) {
            LOGGER.warn("error",e);
        }
        return filialesList;
    }

    public List cargaAlmacenDestino() {
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
            LOGGER.warn("error",e);
        }
        return almacenesList;
    }

    public String getCargarAgregarSalidasTraspasoAlmacen() {
        try {

            //almacenDestinoList = this.cargaAlmacenDestino();
            traspasos = new Traspasos();
            traspasos.getSalidasAlmacen().getGestiones().setNombreGestion(usuario.getGestionesGlobal().getNombreGestion());
            traspasos.getSalidasAlmacen().getGestiones().setCodGestion(usuario.getGestionesGlobal().getCodGestion());
            traspasos.getSalidasAlmacen().setNroSalidaAlmacen(this.generaNroSalidaAlmacen());
            traspasos.getSalidasAlmacen().getTiposSalidasAlmacen().setCodTipoSalidaAlmacen(3); //por traspaso
            traspasos.getSalidasAlmacen().setEstadoSistema(1);
            traspasos.getSalidasAlmacen().getAlmacenes().setCodAlmacen(usuario.getAlmacenesGlobal().getCodAlmacen());
            traspasos.getSalidasAlmacen().getPersonal().setCodPersonal(usuario.getUsuarioModuloBean().getCodUsuarioGlobal());
            traspasos.getSalidasAlmacen().getEstadosSalidasAlmacen().setCodEstadoSalidaAlmacen(1);
            traspasos.getSalidasAlmacen().setCodEstadoSalidaCosto(1); //con costos

            filialList = this.cargaFiliales();
            almacenDestinoList.clear();
            almacenDestinoList.add(new SelectItem("0", "-NINGUNO-"));
            capitulosList = this.cargarCapitulos();
            gruposList.clear();
            gruposList.add(new SelectItem("0", "-NINGUNO-"));
            salidasAlmacenDetalleList.clear();
            traspasos.getAlmacenDestino().getFiliales().setCodFilial(0);
            this.filial_change();
            //inicio ale traspaso
            if (traspasoRechazados) {
                filialList.clear();
                filialList.add(new SelectItem("1", "CENTRAL LA PAZ"));
                filialList.add(new SelectItem("2", "QUINTANILLA"));
                almacenDestinoList.clear();
                almacenDestinoList.add(new SelectItem("12", "ALMACEN DE RECHAZADOS"));
                almacenDestinoList.add(new SelectItem("7", "ALMACEN FRV CENTRAL LA PAZ"));
                traspasos.getAlmacenDestino().setCodAlmacen(12);
                traspasos.getAlmacenDestino().getFiliales().setCodFilial(1);

                this.almacenDestino_change();
            }
            //final ale traspaso

        } catch (Exception e) {
            LOGGER.warn("error",e);
        }
        return null;
    }

    public String filialEditar_change() {
        try {
            almacenDestinoList = this.listadoAlmacenDestino(traspasosEditar);
            areaDestinoList.clear();
            areaDestinoList.add(new SelectItem("0", "-NINGUNO-"));

        } catch (Exception e) {
            LOGGER.warn("error",e);
        }
        return null;
    }

    public String filial_change() {
        try {
            if (traspasoRechazados) {
                almacenDestinoList.clear();
                if (traspasos.getAlmacenDestino().getFiliales().getCodFilial() == 1) {
                    almacenDestinoList.add(new SelectItem("12", "ALMACEN DE RECHAZADOS"));
                    almacenDestinoList.add(new SelectItem("7", "ALMACEN FRV CENTRAL LA PAZ"));
                } else {
                    almacenDestinoList.add(new SelectItem("12", "ALMACEN DE RECHAZADOS"));
                    almacenDestinoList.add(new SelectItem("8", "ALMACEN FRV QUINTANILLA"));
                }

                traspasos.getAlmacenDestino().setCodAlmacen(12);

            } else {
                almacenDestinoList = this.listadoAlmacenDestino(traspasos);
            }
            areaDestinoList.clear();
            areaDestinoList.add(new SelectItem("0", "-NINGUNO-"));

        } catch (Exception e) {
            LOGGER.warn("error",e);
        }
        return null;
    }

    public List listadoAlmacenDestino(Traspasos traspasos) {
        List almacenDestinoList = new ArrayList();
        try {

            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select cod_almacen,nombre_almacen from  almacenes where cod_filial='" + traspasos.getAlmacenDestino().getFiliales().getCodFilial() + "' and cod_almacen<>'" + usuario.getAlmacenesGlobal().getCodAlmacen() + "' ";
            LOGGER.debug("consulta " + consulta);
            ResultSet rs = null;
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            almacenDestinoList.clear();
            rs = st.executeQuery(consulta);
            almacenDestinoList.add(new SelectItem("0", "-NINGUNO-"));
            while (rs.next()) {
                almacenDestinoList.add(new SelectItem(rs.getString("cod_almacen"), rs.getString("nombre_almacen")));
            }

            if (rs != null) {
                rs.close();
                st.close();
                rs = null;
                st = null;
            }

        } catch (Exception e) {
            LOGGER.warn("error",e);
        }
        return almacenDestinoList;
    }

    public List cargarCapitulos() {
        List capitulosList = new ArrayList();
        try {

            Connection con = null;
            con = Util.openConnection(con);
            String consulta = "  select cod_capitulo, nombre_capitulo from capitulos where cod_estado_registro=1 and capitulo_almacen=1 ";

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
            LOGGER.warn("error",e);
        }
        return capitulosList;
    }

    public String capitulos_change() {
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " SELECT GR.COD_GRUPO,GR.NOMBRE_GRUPO FROM GRUPOS GR WHERE GR.COD_CAPITULO = '" + buscarMaterial.getGrupos().getCapitulos().getCodCapitulo() + "' AND GR.COD_ESTADO_REGISTRO = 1 ";
            LOGGER.debug("consulta " + consulta);
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
            LOGGER.warn("error",e);
        }
        return null;
    }

    public String buscarMaterial_action() {
        try {
            Connection con = null;
            con = Util.openConnection(con);
            ManagedAccesoSistema managed = (ManagedAccesoSistema)Util.getSessionBean("ManagedAccesoSistema");
            String consulta = " select m.COD_MATERIAL,m.NOMBRE_MATERIAL,gr.NOMBRE_GRUPO,cap.NOMBRE_CAPITULO,"
                    + " m.COD_UNIDAD_MEDIDA,u1.ABREVIATURA ABREVIATURA1 ,m.COD_UNIDAD_MEDIDA_COMPRA,u2.ABREVIATURA ABREVIATURA2  "
                    + " from MATERIALES m  "
                    + " inner join GRUPOS gr on gr.COD_GRUPO = m.COD_GRUPO "
                    + " inner join CAPITULOS cap on cap.COD_CAPITULO = gr.COD_CAPITULO"
                    + " inner join UNIDADES_MEDIDA u1 on u1.COD_UNIDAD_MEDIDA = m.COD_UNIDAD_MEDIDA "
                    + " inner join UNIDADES_MEDIDA u2 on u2.COD_UNIDAD_MEDIDA = m.COD_UNIDAD_MEDIDA_COMPRA  "
                    + " inner join CONFIGURACION_SALIDA_MATERIAL_PERMITIDO csmp on csmp.COD_ESTADO_REGISTRO = m.COD_ESTADO_REGISTRO"
                            + " and csmp.MOVIMIENTO_ITEM = m.MOVIMIENTO_ITEM"
                    + " and csmp.COD_ALMACEN = "+managed.getAlmacenesGlobal().getCodAlmacen()
                    + " where m.NOMBRE_MATERIAL like '%" + buscarMaterial.getNombreMaterial() + "%' ";
            if (buscarMaterial.getGrupos().getCodGrupo() > 0) {
                consulta = consulta + " and gr.COD_GRUPO = '" + buscarMaterial.getGrupos().getCodGrupo() + "' ";
            }
            if (buscarMaterial.getGrupos().getCapitulos().getCodCapitulo() > 0) {
                consulta = consulta + " and cap.COD_CAPITULO = '" + buscarMaterial.getGrupos().getCapitulos().getCodCapitulo() + "' ";
            }
            String materialesUsados = this.buscaMaterialesUsados();
            consulta = consulta + " and m.cod_material not in (" + materialesUsados + ") ";
            LOGGER.debug("consulta " + consulta);
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
            LOGGER.warn("error",e);
        }
        return null;
    }

    public String buscaMaterialesUsados() {
        String materialesUsados = "-1";
        try {
            Iterator i = salidasAlmacenDetalleList.iterator();
            while (i.hasNext()) {
                SalidasAlmacenDetalle salidasAlmacenDetalleItem = (SalidasAlmacenDetalle) i.next();
                materialesUsados = materialesUsados + "," + salidasAlmacenDetalleItem.getMateriales().getCodMaterial();
            }

        } catch (Exception e) {
            LOGGER.warn("error",e);

        }
        return materialesUsados;
    }

    public String seleccionarMaterial_action() {
        try {
            Materiales materiales = (Materiales) materialesBuscarDataTable.getRowData();
            SalidasAlmacenDetalle salidasAlmacenDetalle = new SalidasAlmacenDetalle();
            salidasAlmacenDetalle.setMateriales(materiales);

            salidasAlmacenDetalle.getUnidadesMedida().setCodUnidadMedida(materiales.getUnidadesMedida().getCodUnidadMedida());
            salidasAlmacenDetalle.getUnidadesMedida().setAbreviatura(materiales.getUnidadesMedida().getAbreviatura());
            salidasAlmacenDetalle.getEstadosMaterial().setCodEstadoMaterial(2); //estado aprobado
            this.etiquetasSalidaAlmacen(salidasAlmacenDetalle);
            salidasAlmacenDetalleList.add(salidasAlmacenDetalle);

        } catch (Exception e) {
            LOGGER.warn("error",e);
        }
        return null;
    }

    public String buscarEditarMaterial_action() {
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
            String materialesUsados = this.buscaMaterialesUsados();
            consulta = consulta + " and m.cod_material not in (" + materialesUsados + ") ";
            LOGGER.debug("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            materialesEditarBuscarList.clear();
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

                materialesEditarBuscarList.add(materialesItem);
            }
            rs.close();
            con.close();

        } catch (Exception e) {
            LOGGER.warn("error",e);
        }
        return null;
    }

    public String seleccionarEditarMaterial_action() {
        try {
            Materiales materiales = (Materiales) materialesEditarBuscarDataTable.getRowData();
            SalidasAlmacenDetalle salidasAlmacenDetalle = new SalidasAlmacenDetalle();
            salidasAlmacenDetalle.setMateriales(materiales);

            salidasAlmacenDetalle.getUnidadesMedida().setCodUnidadMedida(materiales.getUnidadesMedida().getCodUnidadMedida());
            salidasAlmacenDetalle.getUnidadesMedida().setAbreviatura(materiales.getUnidadesMedida().getAbreviatura());
            salidasAlmacenDetalle.getEstadosMaterial().setCodEstadoMaterial(2); //estado aprobado
            this.etiquetasSalidaAlmacen(salidasAlmacenDetalle);
            salidasAlmacenDetalleList.add(salidasAlmacenDetalle);

        } catch (Exception e) {
            LOGGER.warn("error",e);
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
            LOGGER.debug("consulta verificar administrador sistema " + consulta);
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

    public void etiquetasSalidaAlmacen(SalidasAlmacenDetalle salidasAlmacenDetalle) {
        try {
            Connection con = null;
            con = Util.openConnection(con);
            StringBuilder consulta = new StringBuilder("select 1 col,iade.cod_ingreso_almacen,iade.cod_material,iade.etiqueta,")
                                                        .append(" iade.cod_estado_material,iade.cod_empaque_secundario_externo,iade.cantidad_restante as cantidad_r,iade.fecha_vencimiento,")
                                                        .append(" iade.lote_material_proveedor,em.nombre_estado_material,ia.nro_ingreso_almacen,s.nombre_seccion,")
                                                        .append(" es.nombre_empaque_secundario_externo,")
                                                        .append("(select ea.nombre_estante from estante_ambiente ea where ea.cod_estante = iade.cod_Estante) nombre_estante,")
                                                .append(" iade.fila,iade.columna,iade.cod_Estante")
                                                .append(" from INGRESOS_ALMACEN ia")
                                                        .append(" inner join INGRESOS_ALMACEN_DETALLE iad on iad.COD_INGRESO_ALMACEN = ia.COD_INGRESO_ALMACEN")
                                                        .append(" inner join INGRESOS_ALMACEN_DETALLE_ESTADO iade on iade.COD_INGRESO_ALMACEN =iad.COD_INGRESO_ALMACEN")
                                                                    .append(" and iad.COD_MATERIAL =iade.COD_MATERIAL")
                                                        .append(" inner join ESTADOS_MATERIAL em on em.COD_ESTADO_MATERIAL = iade.COD_ESTADO_MATERIAL")
                                                        .append(" inner join EMPAQUES_SECUNDARIO_EXTERNO es on es.COD_EMPAQUE_SECUNDARIO_EXTERNO = iade.COD_EMPAQUE_SECUNDARIO_EXTERNO")
                                                        .append(" left outer join secciones s on s.COD_SECCION = iad.COD_SECCION")
                                                        .append(" left outer join ALMACENES a on a.COD_ALMACEN=s.COD_ALMACEN")
                                                        .append(" inner join CONFIGURACION_SALIDA_ESTADO_MATERIAL cse on cse.COD_ESTADO_MATERIAL=iade.COD_ESTADO_MATERIAL")
                                                .append(" where iad.COD_MATERIAL = ").append(salidasAlmacenDetalle.getMateriales().getCodMaterial())
                                                        .append(" and cse.COD_ALMACEN=").append(traspasos.getAlmacenDestino().getCodAlmacen())
                                                        .append(" and iade.CANTIDAD_RESTANTE>0")
                                                        .append(" and ia.COD_ESTADO_INGRESO_ALMACEN=1")
                                                        .append(" and ia.ESTADO_SISTEMA=1")
                                                        .append(" and ia.COD_ALMACEN = ").append(usuario.getAlmacenesGlobal().getCodAlmacen())
                                                .append("order by ia.NRO_INGRESO_ALMACEN,iade.ETIQUETA");
            LOGGER.debug("consulta etiquetas "+consulta.toString());
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta.toString());
            salidasAlmacenDetalle.getSalidasAlmacenDetalleIngresoList().clear();
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
                salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getEstanteAlmacen().setNombreEstante(rs.getString("nombre_estante"));
                salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getEstanteAlmacen().setCodEstante(rs.getInt("cod_Estante"));
                salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().setFila(rs.getString("fila"));
                salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().setColumna(rs.getString("columna"));

                salidasAlmacenDetalleIngreso.setCantidad(rs.getFloat("cantidad_r"));
                salidasAlmacenDetalleIngreso.setCantidadDisponible(rs.getFloat("cantidad_r"));

                if (salidasAlmacenDetalleIngreso.getCantidadDisponible() > 0) {
                    salidasAlmacenDetalle.getSalidasAlmacenDetalleIngresoList().add(salidasAlmacenDetalleIngreso);
                    //LOGGER.debug("asignacion del valor de cantidad disponible" + salidasAlmacenDetalle.getCantidadDisponible()+salidasAlmacenDetalleIngreso.getCantidadDisponible());
                    salidasAlmacenDetalle.setCantidadDisponible(salidasAlmacenDetalle.getCantidadDisponible() + salidasAlmacenDetalleIngreso.getCantidadDisponible());
                }
            }
            rs.close();
            st.close();
            con.close();

        } catch (Exception e) {
            LOGGER.warn("error",e);
        }
    }

    public String verDetalleEtiquetasEdicionSalidaAlmacen_action() {
        try {

            salidasAlmacenDetalle = (SalidasAlmacenDetalle) salidasAlmacenDetalleEditarDataTable.getRowData();
            float cantidadSalidaAlmacen = salidasAlmacenDetalle.getCantidadSalidaAlmacen();
            Iterator i = salidasAlmacenDetalle.getSalidasAlmacenDetalleIngresoList().iterator();
            /*
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
            LOGGER.warn("error",e);
        }
        return null;
    }

    public String verDetalleEtiquetasSalidaAlmacen_action() {
        try {

            salidasAlmacenDetalle = (SalidasAlmacenDetalle) salidasAlmacenDetalleDataTable.getRowData();
            float cantidadSalidaAlmacen = salidasAlmacenDetalle.getCantidadSalidaAlmacen();
            Iterator i = salidasAlmacenDetalle.getSalidasAlmacenDetalleIngresoList().iterator();
            /*
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
            LOGGER.warn("error",e);
        }
        return null;
    }

    public String guardarSalidasAlmacenDetalleIngreso_action() {
        try {
            //sumar los subtotales del detalle de ingreso
            //verificar que la sumatoria es igual a la cantidad de salida de almacen del material
            mensaje = "";
            Iterator i = salidasAlmacenDetalle.getSalidasAlmacenDetalleIngresoList().iterator();

            salidasAlmacenDetalle.setCantidadSalidaAlmacen(0);
            while (i.hasNext()) {
                SalidasAlmacenDetalleIngreso salidasAlmacenDetalleEstado = (SalidasAlmacenDetalleIngreso) i.next();
                salidasAlmacenDetalle.setCantidadSalidaAlmacen(salidasAlmacenDetalle.getCantidadSalidaAlmacen() + salidasAlmacenDetalleEstado.getCantidadSacar());
            }

        } catch (Exception e) {
            LOGGER.warn("error",e);
        }
        return null;
    }

    public boolean verificaTransaccionCerradaAlmacen(Date fecha, String codGestion) {

        boolean transaccionCerradaAlmacen = false;
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select * from estados_transacciones_almacen "
                    + " where cod_gestion='" + codGestion + "' "
                    + " and cod_mes = '" + Integer.valueOf(sdf.format(fecha)) + "'  "
                    + " and estado=1 ";
            LOGGER.debug("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            if (rs.next()) {
                transaccionCerradaAlmacen = true;
            }

        } catch (Exception e) {
            LOGGER.warn("error",e);
        }
        return transaccionCerradaAlmacen;

    }

    public boolean detalleItems(List salidasAlmacenDetalleList) {
        boolean itemsDetallados = true;
        try {
            Iterator i = salidasAlmacenDetalleList.iterator();
            while (i.hasNext()) {
                SalidasAlmacenDetalle salidasAlmacenDetalleItem = (SalidasAlmacenDetalle) i.next();
                LOGGER.debug("size: " + salidasAlmacenDetalleItem.getSalidasAlmacenDetalleIngresoList().size());
                if (salidasAlmacenDetalleItem.getSalidasAlmacenDetalleIngresoList().size() == 0) {
                    itemsDetallados = false;
                }
            }

        } catch (Exception e) {
            LOGGER.warn("error",e);
        }
        return itemsDetallados;
    }

    public String guardarSalidaTraspasoAction()throws SQLException {
        mensaje ="";
        LOGGER.debug("------------------------------------------INICIO REGISTRO SALIDA TRASPASO----------------------------");
        Connection con = null;
        try {
            con = Util.openConnection(con);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            Date fechaActual = new Date();
            if (this.verificaTransaccionCerradaAlmacen(fechaActual, usuario.getGestionesGlobal().getCodGestion()) == true) {
                mensaje = "Las transacciones para este mes fueron cerradas ";
                return null;
            }

            if (salidasAlmacenDetalleList.size() == 0) {
                mensaje = " No existe detalle de salida ";
                return null;
            }
            if (this.detalleItems(salidasAlmacenDetalleList) == false) {
                mensaje = " No existe detalle de items ";
                return null;
            }
            this.validaDetalleSalidaAlmacenTraspaso();
            if (!mensaje.equals("")) {
                return null;
            }
            
            traspasos.getSalidasAlmacen().setCodSalidaAlmacen(this.generaCodSalidaAlmacen());
            traspasos.getSalidasAlmacen().setNroSalidaAlmacen(this.generaNroSalidaAlmacen());
            traspasos.getEstadosTraspaso().setCodEstadoTraspaso(1);
            traspasos.setFechaTraspaso(new Date());
            traspasos.getAlmacenOrigen().setCodAlmacen(usuario.getAlmacenesGlobal().getCodAlmacen());
            con.setAutoCommit(false);
            
            StringBuilder consultaGuardar = new StringBuilder("INSERT INTO TRASPASOS ( COD_SALIDA_ALMACEN,  COD_ESTADO_TRASPASO,   COD_ALMACEN_ORIGEN,   COD_ALMACEN_DESTINO,")
                                                            .append(" FECHA_TRASPASO,   COD_INGRESO_ALMACEN,COD_TIPO_TRASPASO)")
                                                    .append(" VALUES (")
                                                            .append(traspasos.getSalidasAlmacen().getCodSalidaAlmacen()).append(",")
                                                            .append(traspasos.getEstadosTraspaso().getCodEstadoTraspaso()).append(",")
                                                            .append(traspasos.getAlmacenOrigen().getCodAlmacen()).append(",")
                                                            .append(traspasos.getAlmacenDestino().getCodAlmacen()).append(",")
                                                            .append("getdate(),")
                                                            .append("0,")
                                                            .append(traspasoRechazados ? "2" : "1")
                                                    .append(")");
            LOGGER.debug("consulta guardar traspaso "+consultaGuardar.toString());
            PreparedStatement pst = con.prepareStatement(consultaGuardar.toString());
            if(pst.executeUpdate() > 0)LOGGER.info("se registro el traspaso");
            consultaGuardar = new StringBuilder(" INSERT INTO SALIDAS_ALMACEN ( COD_GESTION, COD_SALIDA_ALMACEN, COD_ORDEN_PESADA,  COD_FORM_SALIDA, COD_PROD, ")
                                                .append("   COD_TIPO_SALIDA_ALMACEN,  COD_AREA_EMPRESA,   NRO_SALIDA_ALMACEN,   FECHA_SALIDA_ALMACEN,   OBS_SALIDA_ALMACEN, ")
                                                .append("  ESTADO_SISTEMA,   COD_ALMACEN,   COD_ORDEN_COMPRA,   COD_PERSONAL,    COD_ESTADO_SALIDA_ALMACEN,   COD_LOTE_PRODUCCION, ")
                                                .append("   COD_ESTADO_SALIDA_COSTO,   cod_prod_ant,   orden_trabajo,   COD_PRESENTACION )")
                                        .append(" VALUES ( ")
                                                .append("'").append(traspasos.getSalidasAlmacen().getGestiones().getCodGestion()).append("',")
                                                .append("'").append(traspasos.getSalidasAlmacen().getCodSalidaAlmacen()).append("',")
                                                .append("0,")
                                                .append("'").append(traspasos.getSalidasAlmacen().getCodFormSalida()).append("',")
                                                .append("0,")
                                                .append(COD_TIPO_SALIDA_TRASPASO).append(",")
                                                .append("'").append(traspasos.getSalidasAlmacen().getAreasEmpresa().getCodAreaEmpresa()).append("',")
                                                .append("'").append(traspasos.getSalidasAlmacen().getNroSalidaAlmacen()).append("',")
                                                .append("GETDATE(),")
                                                .append("?,")//observacion
                                                .append("'").append(traspasos.getSalidasAlmacen().getEstadoSistema()).append("',")
                                                .append("'").append(traspasos.getSalidasAlmacen().getAlmacenes().getCodAlmacen()).append("',")
                                                .append("0,")
                                                .append("'").append(traspasos.getSalidasAlmacen().getPersonal().getCodPersonal()).append("',")
                                                .append("'").append(traspasos.getSalidasAlmacen().getEstadosSalidasAlmacen().getCodEstadoSalidaAlmacen()).append("',")
                                                .append("'',")
                                                .append("'").append(traspasos.getSalidasAlmacen().getCodEstadoSalidaCosto()).append("',")
                                                .append("'").append(traspasos.getSalidasAlmacen().getCodProdAnt()).append("',")
                                                .append("'',")
                                                .append("0")
                                        .append(")");
            LOGGER.debug("consulta registrar salida" + consultaGuardar.toString());
            pst = con.prepareStatement(consultaGuardar.toString());
            pst.setString(1, traspasos.getSalidasAlmacen().getObsSalidaAlmacen());LOGGER.info("p1: "+traspasos.getSalidasAlmacen().getObsSalidaAlmacen());
            if(pst.executeUpdate() > 0)LOGGER.info("se registro salida");
            String consulta ="";
            Iterator i = salidasAlmacenDetalleList.iterator();
            while (i.hasNext()) {
                SalidasAlmacenDetalle salidasAlmacenDetalleItem = (SalidasAlmacenDetalle) i.next();
                consultaGuardar = new StringBuilder(" INSERT INTO SALIDAS_ALMACEN_DETALLE ( COD_SALIDA_ALMACEN, COD_MATERIAL, CANTIDAD_SALIDA_ALMACEN, COD_UNIDAD_MEDIDA, ")
                                                .append("  COD_ESTADO_MATERIAL ) ")
                                        .append(" VALUES (")
                                                .append("'").append(traspasos.getSalidasAlmacen().getCodSalidaAlmacen()).append("',")
                                                .append("'").append(salidasAlmacenDetalleItem.getMateriales().getCodMaterial()).append("',")
                                                .append("'").append(salidasAlmacenDetalleItem.getCantidadSalidaAlmacen()).append("',")
                                                .append("'").append(salidasAlmacenDetalleItem.getUnidadesMedida().getCodUnidadMedida()).append("',")
                                                .append("'").append(salidasAlmacenDetalleItem.getEstadosMaterial().getCodEstadoMaterial()).append("'")
                                        .append(")");
                LOGGER.debug("consulta registrar detalle salidas" + consultaGuardar.toString());
                pst = con.prepareStatement(consultaGuardar.toString());
                if(pst.executeUpdate() > 0)LOGGER.info("se registro el detalle");
                Iterator i1 = salidasAlmacenDetalleItem.getSalidasAlmacenDetalleIngresoList().iterator();
                int ix = 1;
                
                while (i1.hasNext()) {
                    SalidasAlmacenDetalleIngreso salidasAlmacenDetalleIngreso = (SalidasAlmacenDetalleIngreso) i1.next();

                    if (salidasAlmacenDetalleIngreso.getCantidadSacar() > 0) {
                        consulta = "  INSERT INTO SALIDAS_ALMACEN_DETALLE_INGRESO ( COD_SALIDA_ALMACEN,  COD_MATERIAL, COD_INGRESO_ALMACEN,  ETIQUETA, "
                                + " COSTO_SALIDA,   FECHA_VENCIMIENTO,   CANTIDAD,   COSTO_SALIDA_ACTUALIZADO,   FECHA_ACTUALIZACION,   COSTO_SALIDA_ACTUALIZADO_FINAL "
                                + " , COD_ESTANTE, FILA, COLUMNA)  "
                                + " VALUES ( '" + traspasos.getSalidasAlmacen().getCodSalidaAlmacen() + "',   '" + salidasAlmacenDetalleItem.getMateriales().getCodMaterial() + "',   "
                                + " '" + salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getIngresosAlmacen().getCodIngresoAlmacen() + "', "
                                + " '" + salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getEtiqueta() + "', '" + salidasAlmacenDetalleIngreso.getCostoSalida() + "',"
                                + (salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getFechaVencimiento() != null?" '" +sdf.format(salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getFechaVencimiento())+"', ":"null,")
                                + //salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getEtiqueta() debe restaurarse
                                " '" + salidasAlmacenDetalleIngreso.getCantidadSacar() + "', '" + salidasAlmacenDetalleIngreso.getCostoSalidaActualizado() + "', "
                                + " null,  '" + salidasAlmacenDetalleIngreso.getCostoSalidaActualizadoFinal() + "'"
                                + ",'" + salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getEstanteAlmacen().getCodEstante()+"'"
                                + ",'" + salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getFila()+"'"
                                + ",'" + salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getColumna()+"'"
                                + " ) ";

                        LOGGER.debug("consulta registrar detalle ingreso" + consulta);
                        pst =con.prepareStatement(consulta);
                        if(pst.executeUpdate() > 0)LOGGER.info(" se registro detalle ingreso");
                        
                        // se actualiza las cantidad restante de ingreso_almacen_detalle_empaque
                        consulta = " update ingresos_almacen_detalle_estado set cantidad_restante= cantidad_restante- '" + salidasAlmacenDetalleIngreso.getCantidadSacar() + "' "
                                + " where cod_material='" + salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getMateriales().getCodMaterial() + "' "
                                + " and etiqueta='" + salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getEtiqueta() + "' "
                                + " and cod_ingreso_almacen='" + salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getIngresosAlmacen().getCodIngresoAlmacen() + "' ";
                        LOGGER.debug("consulta reducir cantidad restante "+consulta);
                        pst =con.prepareStatement(consulta);
                        if(pst.executeUpdate() > 0)LOGGER.info(" se redujo la cantidad restante");

                    }
                    ix++;
                }
            }
            //EJECUTAMOS SP DESPUES DE REGISTRO
            consulta = " exec [PAA_REGISTRO_SALIDA_ALMACEN_LOG] " + traspasos.getSalidasAlmacen().getCodSalidaAlmacen() 
                    + " , "+ usuario.getUsuarioModuloBean().getCodUsuarioGlobal()
                    + " , 3";
            LOGGER.debug("consulta ejecutar: " + consulta); 
            st.executeUpdate(consulta);
            con.commit();
            st.close();
            mensaje = "1";
        } 
        catch(SQLException ex){
            mensaje = "Ocurrio un error al momento de guardar el registro, intente de nuevo";
            LOGGER.warn("error", ex);
            con.rollback();
        }
        catch (Exception e) {
            mensaje = "Ocurrio un error al momento de guardar el registro, intente de nuevo";
            LOGGER.warn("error",e);
        }
        finally{
            con.close();
        }
        LOGGER.debug("------------------------------------------TERMINO REGISTRO SALIDA TRASPASO----------------------------");
        //actualización de costos
        try {

            AssignCostService assignCostService = new AssignCostService(Util.getPropertiesConnection());
            float ufv_actual = assignCostService.getUfvActual();
            for (Object detalle : salidasAlmacenDetalleList) {
                SalidasAlmacenDetalle salidasAlmacenDetalle = (SalidasAlmacenDetalle) detalle;
                boolean resultado = assignCostService.updateCostoUnitarioSalida(traspasos.getSalidasAlmacen().getCodSalidaAlmacen(), traspasos.getSalidasAlmacen().getAlmacenes().getCodAlmacen(), traspasos.getSalidasAlmacen().getTiposSalidasAlmacen().getCodTipoSalidaAlmacen(), Integer.parseInt(salidasAlmacenDetalle.getMateriales().getCodMaterial()), salidasAlmacenDetalle.getCantidadSalidaAlmacen(), ufv_actual);
                if (resultado) {
                    LOGGER.debug("update ok -> " + salidasAlmacenDetalle.getMateriales().getNombreMaterial());
                }
            }
            LOGGER.debug("*J* Costos actualizado en salida traspaso = " + traspasos.getSalidasAlmacen().getCodSalidaAlmacen());
        } catch (Exception e) {
            LOGGER.debug("Excepcion en costos");
            LOGGER.warn("error",e);
        }
        //fin actualización de costos
        return null;
    }

    public void validaDetalleSalidaAlmacenTraspaso() {

        try {
            Iterator i = salidasAlmacenDetalleList.iterator();
            float cantidadSalida = 0;
            while (i.hasNext()) {
                SalidasAlmacenDetalle salidasAlmacenDetalleItem = (SalidasAlmacenDetalle) i.next();
                if (salidasAlmacenDetalleItem.getCantidadSalidaAlmacen() <= 0) {
                    mensaje = " existen items que no tienen cantidad de salida ";
                    break;
                }
            }

        } catch (Exception e) {
            LOGGER.warn("error",e);
        }

    }

    public void guardaTraspaso(Traspasos traspasos) {
        try {
            Connection con = null;
            con = Util.openConnection(con);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            String consulta = " INSERT INTO TRASPASOS ( COD_SALIDA_ALMACEN,  COD_ESTADO_TRASPASO,   COD_ALMACEN_ORIGEN,   COD_ALMACEN_DESTINO, "
                    + "  FECHA_TRASPASO,   COD_INGRESO_ALMACEN,COD_TIPO_TRASPASO)  VALUES (   '" + traspasos.getSalidasAlmacen().getCodSalidaAlmacen() + "',"
                    + " '" + traspasos.getEstadosTraspaso().getCodEstadoTraspaso() + "',   '" + traspasos.getAlmacenOrigen().getCodAlmacen() + "', "
                    + "  '" + traspasos.getAlmacenDestino().getCodAlmacen() + "','" + sdf.format(traspasos.getFechaTraspaso()) + "','" + traspasos.getIngresosAlmacen().getCodIngresoAlmacen() + "'," + (traspasoRechazados ? "2" : "1") + " ); ";
            LOGGER.debug("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            st.executeUpdate(consulta);
            st.close();
            con.close();

        } catch (Exception e) {
            LOGGER.warn("error",e);
        }
    }

    public int generaCodSalidaAlmacen() {
        int codSalidaAlmacen = 0;
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select isnull(max(s.COD_SALIDA_ALMACEN),0)+1 COD_SALIDA_ALMACEN from SALIDAS_ALMACEN s  ";
            LOGGER.debug("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            if (rs.next()) {
                codSalidaAlmacen = rs.getInt("COD_SALIDA_ALMACEN");
            }

        } catch (Exception e) {
            LOGGER.warn("error",e);
        }
        return codSalidaAlmacen;
    }

    public int generaNroSalidaAlmacen() {
        int nroIngresoAlmacen = 0;
        ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select isnull(MAX(nro_salida_almacen),0)+1 nro_salida_almacen from salidas_almacen "
                    + " where cod_gestion='" + usuario.getGestionesGlobal().getCodGestion() + "' "
                    + " and cod_almacen='" + usuario.getAlmacenesGlobal().getCodAlmacen() + "' and estado_sistema=1   ";
            LOGGER.debug("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            if (rs.next()) {
                nroIngresoAlmacen = rs.getInt("nro_salida_almacen");
            }
            con.close();
            st.close();
            rs.close();
        } catch (Exception e) {
            LOGGER.warn("error",e);
        }
        return nroIngresoAlmacen;
    }

    public String editarTraspaso_action() {
        try {
            mensaje = "";
            Iterator i = traspasosList.iterator();
            while (i.hasNext()) {
                Traspasos traspasosItem = (Traspasos) i.next();
                if (traspasosItem.getChecked().booleanValue() == true) {
                    traspasos = traspasosItem;
                    break;
                }
            }

            LOGGER.debug("datos :" + traspasos.getEstadosTraspaso().getCodEstadoTraspaso()
                    + " " + traspasos.getSalidasAlmacen().getEstadosSalidasAlmacen().getCodEstadoSalidaAlmacen() + " "
                    + traspasos.getSalidasAlmacen().getTiposSalidasAlmacen().getCodTipoSalidaAlmacen());
            //validaciones
            if (traspasos.getEstadosTraspaso().getCodEstadoTraspaso() == 2) { //traspaso aprobado
                mensaje = " el registro ya fue aprobado, en otro almacen  ";
                return null;
            }
            if (traspasos.getSalidasAlmacen().getEstadosSalidasAlmacen().getCodEstadoSalidaAlmacen() == 2) { //anulado
                mensaje = " la salida de almacen fue anulada  ";
                return null;
            }
            if (traspasos.getSalidasAlmacen().getTiposSalidasAlmacen().getCodTipoSalidaAlmacen() == 9) { //traspaso a frv
                mensaje = " no se puede modificar traspaso a frv  ";
                return null;
            }

            if (this.verificaTransaccionCerradaAlmacen(traspasos.getSalidasAlmacen().getFechaSalidaAlmacen(), traspasos.getSalidasAlmacen().getGestiones().getCodGestion()) == true) {
                mensaje = "Las transacciones para este mes fueron cerradas ";
                return null;
            }

        } catch (Exception e) {
            LOGGER.warn("error",e);
        }
        return null;
    }

    public String getCargarEditarSalidasTraspasoAlmacen() {
        try {
            almacenDestinoList = this.listadoAlmacenDestino(traspasos);
            areaDestinoList = this.listaAreaDestino(traspasos);
            salidasAlmacenDetalleList = this.listadoSalidasAlmacenDetalle(traspasos);
            capitulosList = this.cargarCapitulos();
            gruposList.clear();
            gruposList.add(new SelectItem("0", "-NINGUNO-"));
            materialesBuscarList.clear();
            materialesBuscarDataTable = new HtmlDataTable();
            //inicio alejandro

            salidasVerificar2 = this.listadoSalidasAlmacenDetalle(traspasos);
            // final alejandro

        } catch (Exception e) {
            LOGGER.warn("error",e);
        }
        return null;
    }

    public List listadoSalidasAlmacenDetalle(Traspasos traspasos) {
        List salidasAlmacenDetalleList = new ArrayList();
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " SELECT COD_SALIDA_ALMACEN, COD_MATERIAL, CANTIDAD_SALIDA_ALMACEN, COD_UNIDAD_MEDIDA, COD_ESTADO_MATERIAL "
                    + " FROM SALIDAS_ALMACEN_DETALLE WHERE COD_SALIDA_ALMACEN = '" + traspasos.getSalidasAlmacen().getCodSalidaAlmacen() + "'  ";

            consulta = " select s.COD_SALIDA_ALMACEN, s.COD_MATERIAL,mat.NOMBRE_MATERIAL, s.CANTIDAD_SALIDA_ALMACEN, s.COD_UNIDAD_MEDIDA,u.ABREVIATURA, s.COD_ESTADO_MATERIAL,e.NOMBRE_ESTADO_MATERIAL "
                    + " from SALIDAS_ALMACEN_DETALLE s  "
                    + " inner join MATERIALES mat on mat.COD_MATERIAL = s.COD_MATERIAL "
                    + " inner join UNIDADES_MEDIDA u on u.COD_UNIDAD_MEDIDA = s.COD_UNIDAD_MEDIDA "
                    + " inner join ESTADOS_MATERIAL e on e.COD_ESTADO_MATERIAL = s.COD_ESTADO_MATERIAL "
                    + " where s.COD_SALIDA_ALMACEN = '" + traspasos.getSalidasAlmacen().getCodSalidaAlmacen() + "'  ";
            LOGGER.debug("consulta" + consulta);

            LOGGER.debug("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            salidasAlmacenDetalleList.clear();
            while (rs.next()) {
                SalidasAlmacenDetalle salidasAlmacenDetalle = new SalidasAlmacenDetalle();
                salidasAlmacenDetalle.getSalidasAlmacen().setCodSalidaAlmacen(rs.getInt("COD_SALIDA_ALMACEN"));
                salidasAlmacenDetalle.getMateriales().setCodMaterial(rs.getString("COD_MATERIAL"));
                salidasAlmacenDetalle.getMateriales().setNombreMaterial(rs.getString("NOMBRE_MATERIAL"));
                salidasAlmacenDetalle.setCantidadSalidaAlmacen(rs.getFloat("CANTIDAD_SALIDA_ALMACEN"));
                salidasAlmacenDetalle.getUnidadesMedida().setCodUnidadMedida(rs.getInt("COD_UNIDAD_MEDIDA"));
                salidasAlmacenDetalle.getUnidadesMedida().setAbreviatura(rs.getString("ABREVIATURA"));
                salidasAlmacenDetalle.getEstadosMaterial().setCodEstadoMaterial(rs.getInt("COD_ESTADO_MATERIAL"));
                salidasAlmacenDetalle.getEstadosMaterial().setNombreEstadoMaterial(rs.getString("NOMBRE_ESTADO_MATERIAL"));
                salidasAlmacenDetalle.setSalidasAlmacenDetalleIngresoList(listadoSalidasAlmacenDetalleIngreso(salidasAlmacenDetalle));

                salidasAlmacenDetalleList.add(salidasAlmacenDetalle);
            }

        } catch (Exception e) {
            LOGGER.warn("error",e);
        }
        return salidasAlmacenDetalleList;
    }

    public List listadoSalidasAlmacenDetalleIngreso(SalidasAlmacenDetalle salidasAlmacenDetalle) {
        List salidasAlmacenDetalleIngresoList = new ArrayList();
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
                    + " ( select ISNULL(sum(sadi.cantidad), 0)  from SALIDAS_ALMACEN_DETALLE_INGRESO sadi"
                    + " inner join SALIDAS_ALMACEN_DETALLE sad on sad.COD_SALIDA_ALMACEN = sadi.COD_SALIDA_ALMACEN and sad.COD_MATERIAL = sadi.COD_MATERIAL "
                    + " inner join SALIDAS_ALMACEN s on s.COD_SALIDA_ALMACEN = sad.COD_SALIDA_ALMACEN"
                    + " where sadi.cod_material = iade.cod_material and sadi.cod_ingreso_almacen = iade.cod_ingreso_almacen "
                    + " and sadi.etiqueta = iade.etiqueta and s.ESTADO_SISTEMA = 1 and s.COD_ALMACEN = '" + usuario.getAlmacenesGlobal().getCodAlmacen() + "' and s.COD_ESTADO_SALIDA_ALMACEN = 1 ) + "
                    + " ( select isnull(sum(dde.cantidad_devuelta), 0) from DEVOLUCIONES_DETALLE_ETIQUETAS dde "
                    + " inner join DEVOLUCIONES_DETALLE dd on dd.COD_DEVOLUCION = dde.COD_DEVOLUCION and dd.COD_MATERIAL = dde.COD_MATERIAL "
                    + " inner join DEVOLUCIONES d on d.COD_DEVOLUCION = dde.COD_DEVOLUCION "
                    + " where dde.cod_material = iade.cod_material and dde.cod_ingreso_almacen = iade.cod_ingreso_almacen "
                    + " and dde.etiqueta = iade.etiqueta and d.COD_ESTADO_DEVOLUCION = 1 "
                    + " and d.ESTADO_SISTEMA = 1 and  d.COD_ALMACEN = '" + usuario.getAlmacenesGlobal().getCodAlmacen() + "') as cantidad_r "
                    + ",iade.fecha_vencimiento, iade.lote_material_proveedor "
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
                    + " where a.COD_ALMACEN = '" + usuario.getAlmacenesGlobal().getCodAlmacen() + "'  and ia.COD_ESTADO_INGRESO_ALMACEN = 1 and ia.ESTADO_SISTEMA = 1   "
                    + //and iade.CANTIDAD_RESTANTE>0
                    " and iade.COD_MATERIAL = '" + salidasAlmacenDetalle.getMateriales().getCodMaterial() + "' and ia.ESTADO_SISTEMA = 1 "
                    + " order by  iade.fecha_vencimiento asc "; //and iade.COD_ESTADO_MATERIAL = 2

            LOGGER.debug("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            salidasAlmacenDetalleIngresoList.clear();
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
                if (salidasAlmacenDetalleIngreso.getCantidadDisponible() - salidasAlmacenDetalleIngreso.getCantidadSacar() >= 0 && salidasAlmacenDetalleIngreso.getCantidadDisponible() > 0) {
                    salidasAlmacenDetalleIngresoList.add(salidasAlmacenDetalleIngreso);
                }

            }

        } catch (Exception e) {
            LOGGER.warn("error",e);
        }
        return salidasAlmacenDetalleIngresoList;
    }

    public String eliminarIngresoDetalleAlmacen_action() {
        try {

            Iterator i = salidasAlmacenDetalleList.iterator();
            List salidasAlmacenDetalleItemsList = new ArrayList();

            while (i.hasNext()) {
                SalidasAlmacenDetalle salidasAlmacenDetalleItem = (SalidasAlmacenDetalle) i.next();
                if (salidasAlmacenDetalleItem.getChecked().booleanValue() == false) {
                    salidasAlmacenDetalleItemsList.add(salidasAlmacenDetalleItem);
                }
            }
            salidasAlmacenDetalleList.clear();
            salidasAlmacenDetalleList = salidasAlmacenDetalleItemsList;
        } catch (Exception e) {
            LOGGER.warn("error",e);
        }
        return null;
    }

    public String eliminarIngresoDetalleAlmacen_action1() {
        Connection con = null;

        try {
            con = Util.openConnection(con);
            SalidasAlmacenDetalle salidasAlmacenDetalle = new SalidasAlmacenDetalle();

            Iterator i = salidasAlmacenDetalleList.iterator();
            while (i.hasNext()) {
                SalidasAlmacenDetalle salidasAlmacenDetalleItem = (SalidasAlmacenDetalle) i.next();
                if (salidasAlmacenDetalleItem.getChecked().booleanValue() == true) {
                    salidasAlmacenDetalle = salidasAlmacenDetalleItem;
                    break;
                }
            }

            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            String consulta2 = " update ingresos_almacen_detalle_estado set cantidad_restante= cantidad_restante+ sadi.cantidad "
                    + " from ingresos_almacen_detalle_estado iade "
                    + " inner join SALIDAS_ALMACEN_DETALLE_INGRESO sadi on"
                    + " iade.cod_material=sadi.cod_material"
                    + " and iade.etiqueta=sadi.etiqueta and iade.cod_ingreso_almacen=sadi.cod_ingreso_almacen"
                    + " where sadi.COD_SALIDA_ALMACEN = '" + salidasAlmacenDetalle.getSalidasAlmacen().getCodSalidaAlmacen() + "' "
                    + " AND sadi.COD_MATERIAL = '" + salidasAlmacenDetalle.getMateriales().getCodMaterial() + "' ";

            LOGGER.debug("consulta J" + consulta2);
            st.execute(consulta2);

            String consulta = " DELETE FROM SALIDAS_ALMACEN_DETALLE_INGRESO  WHERE  "
                    + " COD_SALIDA_ALMACEN = '" + salidasAlmacenDetalle.getSalidasAlmacen().getCodSalidaAlmacen() + "' "
                    + " AND COD_MATERIAL = '" + salidasAlmacenDetalle.getMateriales().getCodMaterial() + "' ";
            LOGGER.debug("consulta" + consulta);
            st.executeUpdate(consulta);

            consulta = " DELETE FROM SALIDAS_ALMACEN_DETALLE WHERE COD_SALIDA_ALMACEN = '" + salidasAlmacenDetalle.getSalidasAlmacen().getCodSalidaAlmacen() + "' "
                    + " AND COD_MATERIAL = '" + salidasAlmacenDetalle.getMateriales().getCodMaterial() + "'";
            LOGGER.debug("consulta" + consulta);
            st.execute(consulta);
            salidasAlmacenDetalle.setSalidasAlmacenDetalleIngresoList(listadoSalidasAlmacenDetalleIngreso(salidasAlmacenDetalle));
            salidasAlmacenDetalleList = this.listadoSalidasAlmacenDetalle(traspasos);

            st.close();
            con.close();
        } catch (Exception e) {
            LOGGER.warn("error",e);
            try {
                con.rollback();
            } catch (SQLException ex) {
                LOGGER.warn("error", ex);
            }
        }
        return null;
    }

    public String guardarEditarTraspasoSalidasAlmacen_action1() {
        try {
            if (salidasAlmacenDetalleList.size() == 0) {
                mensaje = " no existe detalle de salida ";
                return null;
            }

            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String consulta = "";
            consulta = " UPDATE SALIDAS_ALMACEN  SET COD_TIPO_SALIDA_ALMACEN = 3, "
                    + " COD_AREA_EMPRESA = '" + traspasos.getSalidasAlmacen().getAreasEmpresa().getCodAreaEmpresa() + "', "
                    + " OBS_SALIDA_ALMACEN = '" + traspasos.getSalidasAlmacen().getObsSalidaAlmacen() + "' "
                    + " WHERE  COD_SALIDA_ALMACEN =  " + traspasos.getSalidasAlmacen().getCodSalidaAlmacen();
            LOGGER.debug("consulta " + consulta);
            st.executeUpdate(consulta);

            st.close();
            con.close();

        } catch (Exception e) {
            LOGGER.warn("error",e);
        }
        return null;

    }

    public String guardarEditarTraspasoSalidasAlmacen_action() {
        try {
            if (salidasAlmacenDetalleList.size() == 0) {
                mensaje = " no existe detalle de salida ";
                return null;
            }

            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String consulta = "";
            consulta = " DELETE FROM SALIDAS_ALMACEN_DETALLE WHERE COD_SALIDA_ALMACEN = '" + traspasos.getSalidasAlmacen().getCodSalidaAlmacen() + "' ";
            LOGGER.debug("consulta " + consulta);
            st.executeUpdate(consulta);
            consulta = " DELETE FROM SALIDAS_ALMACEN_DETALLE_INGRESO WHERE COD_SALIDA_ALMACEN = '" + traspasos.getSalidasAlmacen().getCodSalidaAlmacen() + "' ";
            LOGGER.debug("consulta " + consulta);
            st.executeUpdate(consulta);
            consulta = " UPDATE TRASPASOS SET COD_ALMACEN_DESTINO = '" + traspasos.getAlmacenDestino().getCodAlmacen() + "' WHERE COD_SALIDA_ALMACEN = '" + traspasos.getSalidasAlmacen().getCodSalidaAlmacen() + "'";
            LOGGER.debug("consulta " + consulta);
            if (st.executeUpdate(consulta) > 0) {
                LOGGER.debug("se actualizo el traspaso");
            }

            //iteracion de detalle
            Iterator i = salidasAlmacenDetalleList.iterator();

            while (i.hasNext()) {
                SalidasAlmacenDetalle salidasAlmacenDetalleItem = (SalidasAlmacenDetalle) i.next();
                consulta = " INSERT INTO SALIDAS_ALMACEN_DETALLE ( COD_SALIDA_ALMACEN, COD_MATERIAL, CANTIDAD_SALIDA_ALMACEN, COD_UNIDAD_MEDIDA, "
                        + "  COD_ESTADO_MATERIAL )  VALUES (   '" + traspasos.getSalidasAlmacen().getCodSalidaAlmacen() + "', '" + salidasAlmacenDetalleItem.getMateriales().getCodMaterial() + "',"
                        + "  '" + salidasAlmacenDetalleItem.getCantidadSalidaAlmacen() + "',   '" + salidasAlmacenDetalleItem.getUnidadesMedida().getCodUnidadMedida() + "', "
                        + "   '" + salidasAlmacenDetalleItem.getEstadosMaterial().getCodEstadoMaterial() + "' ) ";
                LOGGER.debug("consulta " + consulta);
                st.executeUpdate(consulta);
                Iterator i1 = salidasAlmacenDetalleItem.getSalidasAlmacenDetalleIngresoList().iterator();

                while (i1.hasNext()) {
                    SalidasAlmacenDetalleIngreso salidasAlmacenDetalleIngreso = (SalidasAlmacenDetalleIngreso) i1.next();

                    if (salidasAlmacenDetalleIngreso.getCantidadSacar() > 0) {
                        consulta = "  INSERT INTO SALIDAS_ALMACEN_DETALLE_INGRESO ( COD_SALIDA_ALMACEN,  COD_MATERIAL, COD_INGRESO_ALMACEN,  ETIQUETA, "
                                + " COSTO_SALIDA,   FECHA_VENCIMIENTO,   CANTIDAD,   COSTO_SALIDA_ACTUALIZADO,   FECHA_ACTUALIZACION,   COSTO_SALIDA_ACTUALIZADO_FINAL )  "
                                + " VALUES ( '" + traspasos.getSalidasAlmacen().getCodSalidaAlmacen() + "',   '" + salidasAlmacenDetalleItem.getMateriales().getCodMaterial() + "',   "
                                + " '" + salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getIngresosAlmacen().getCodIngresoAlmacen() + "', "
                                + " '" + salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getEtiqueta() + "', '" + salidasAlmacenDetalleIngreso.getCostoSalida() + "', '" + salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getFechaVencimiento() + "', "
                                + " '" + salidasAlmacenDetalleIngreso.getCantidadSacar() + "', '" + salidasAlmacenDetalleIngreso.getCostoSalidaActualizado() + "', "
                                + " null,  '" + salidasAlmacenDetalleIngreso.getCostoSalidaActualizadoFinal() + "' ) ";

                        LOGGER.debug("consulta " + consulta);

                        st.executeUpdate(consulta);
                    }

                }
            }
            st.close();

            //inicio alejandro
            Iterator j = salidasVerificar2.iterator();
            Iterator k = salidasAlmacenDetalleList.iterator();

            SalidasAlmacenDetalle salidasAlma1 = new SalidasAlmacenDetalle();
            boolean next1 = true;
            while (j.hasNext()) {

                SalidasAlmacenDetalle salidasAlmacenDetalleItem = (SalidasAlmacenDetalle) j.next();//recuperando fila de la lista item
                if (k.hasNext() || !next1) {
                    if (next1) {
                        salidasAlma1 = (SalidasAlmacenDetalle) k.next();
                    }
                    // para verificar q en la lista no se aya eliminado algun elemento
                    if ((salidasAlmacenDetalleItem.getSalidasAlmacen().getCodSalidaAlmacen()
                            == salidasAlma1.getSalidasAlmacen().getCodSalidaAlmacen())
                            && (salidasAlmacenDetalleItem.getMateriales().getCodMaterial().equals(
                                    salidasAlma1.getMateriales().getCodMaterial()))) {
                        next1 = true;

                        Iterator j1 = salidasAlmacenDetalleItem.getSalidasAlmacenDetalleIngresoList().iterator();
                        Iterator k3 = salidasAlma1.getSalidasAlmacenDetalleIngresoList().iterator();

                        while ((j1.hasNext())) {
                            SalidasAlmacenDetalleIngreso salidasAlmacenDetalleIngreso = (SalidasAlmacenDetalleIngreso) j1.next();
                            SalidasAlmacenDetalleIngreso salidasAlmacenDeta2 = (SalidasAlmacenDetalleIngreso) k3.next();
                            float diferencia = salidasAlmacenDetalleIngreso.getCantidadSacar() - salidasAlmacenDeta2.getCantidadSacar();

                            if (diferencia != 0) {
                                LOGGER.debug("existe un item cambiado");
                                String consulta2 = " update ingresos_almacen_detalle_estado set cantidad_restante= cantidad_restante+ '" + diferencia + "' "
                                        + " where cod_material='" + salidasAlmacenDeta2.getMateriales().getCodMaterial() + "' "
                                        + " and etiqueta='" + salidasAlmacenDeta2.getIngresosAlmacenDetalleEstado().getEtiqueta() + "' "
                                        + " and cod_ingreso_almacen='" + salidasAlmacenDeta2.getIngresosAlmacenDetalleEstado().getIngresosAlmacen().getCodIngresoAlmacen() + "' ";
                                LOGGER.debug("con actualizar ingresos " + consulta2);
                                PreparedStatement pst1 = con.prepareStatement(consulta2);
                                if (pst1.executeUpdate() > 0) {
                                    LOGGER.debug("se actualizo");
                                }
                                pst1.close();
                            }

                        }
                    } //en caso de que se aya eliminado un item
                    else {
                        LOGGER.debug("Se elimino un item intermedio ");
                        next1 = false;
                        Iterator j1 = salidasAlmacenDetalleItem.getSalidasAlmacenDetalleIngresoList().iterator();
                        while ((j1.hasNext())) {
                            SalidasAlmacenDetalleIngreso salidasAlmacenDetalleIngreso = (SalidasAlmacenDetalleIngreso) j1.next();

                            if (salidasAlmacenDetalleIngreso.getCantidadSacar() > 0) {
                                String consulta2 = " update ingresos_almacen_detalle_estado set cantidad_restante= cantidad_restante+ '" + salidasAlmacenDetalleIngreso.getCantidadSacar() + "' "
                                        + " where cod_material='" + salidasAlmacenDetalleIngreso.getMateriales().getCodMaterial() + "' "
                                        + " and etiqueta='" + salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getEtiqueta() + "' "
                                        + " and cod_ingreso_almacen='" + salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getIngresosAlmacen().getCodIngresoAlmacen() + "' ";
                                LOGGER.debug("con actualizar ingresos " + consulta2);
                                PreparedStatement pst1 = con.prepareStatement(consulta2);
                                if (pst1.executeUpdate() > 0) {
                                    LOGGER.debug("se actualizo");
                                }
                                pst1.close();

                            }

                        }

                    }
                } else {
                    LOGGER.debug("se elimino un item al ultimo");
                    Iterator j1 = salidasAlmacenDetalleItem.getSalidasAlmacenDetalleIngresoList().iterator();
                    while ((j1.hasNext())) {
                        SalidasAlmacenDetalleIngreso salidasAlmacenDetalleIngreso = (SalidasAlmacenDetalleIngreso) j1.next();
                        if (salidasAlmacenDetalleIngreso.getCantidadSacar() > 0) {
                            String consulta2 = " update ingresos_almacen_detalle_estado set cantidad_restante= cantidad_restante+ '" + salidasAlmacenDetalleIngreso.getCantidadSacar() + "' "
                                    + " where cod_material='" + salidasAlmacenDetalleIngreso.getMateriales().getCodMaterial() + "' "
                                    + " and etiqueta='" + salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getEtiqueta() + "' "
                                    + " and cod_ingreso_almacen='" + salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getIngresosAlmacen().getCodIngresoAlmacen() + "' ";
                            LOGGER.debug("con actualizar ingresos " + consulta2);
                            PreparedStatement pst1 = con.prepareStatement(consulta2);
                            if (pst1.executeUpdate() > 0) {
                                LOGGER.debug("se actualizo");
                            }
                            pst1.close();

                        }

                    }
                }

            }
            //en caso de que aya mas elementos q los iniciales
            while (k.hasNext()) {
                LOGGER.debug("Existen nuevos detalles");
                salidasAlma1 = (SalidasAlmacenDetalle) k.next();
                // para verificar q en la lista no se aya eliminado algun elemento

                Iterator k3 = salidasAlma1.getSalidasAlmacenDetalleIngresoList().iterator();

                while ((k3.hasNext())) {
                    SalidasAlmacenDetalleIngreso salidasAlmacenDeta2 = (SalidasAlmacenDetalleIngreso) k3.next();

                    if (salidasAlmacenDeta2.getCantidadSacar() > 0) {

                        String consulta2 = " update ingresos_almacen_detalle_estado set cantidad_restante= cantidad_restante- '" + salidasAlmacenDeta2.getCantidadSacar() + "' "
                                + " where cod_material='" + salidasAlma1.getMateriales().getCodMaterial() + "' "
                                + " and etiqueta='" + salidasAlmacenDeta2.getIngresosAlmacenDetalleEstado().getEtiqueta() + "' "
                                + " and cod_ingreso_almacen='" + salidasAlmacenDeta2.getIngresosAlmacenDetalleEstado().getIngresosAlmacen().getCodIngresoAlmacen() + "' ";
                        LOGGER.debug("con actualizar ingresos " + consulta2);
                        PreparedStatement pst1 = con.prepareStatement(consulta2);
                        if (pst1.executeUpdate() > 0) {
                            LOGGER.debug("se actualizo");
                        }
                        pst1.close();
                    }

                }

            }

            // final alejandro
            con.close();

        } catch (Exception e) {
            LOGGER.warn("error",e);
        }
        return null;

    }

    public String busquedaMaterial_action() {
        try {
            materialesBuscarList.clear();
            buscarMaterial = new Materiales();
        } catch (Exception e) {
            LOGGER.warn("error",e);
        }
        return null;
    }

    public String busquedaEditarMaterial_action() {
        try {
            materialesEditarBuscarList.clear();
        } catch (Exception e) {
            LOGGER.warn("error",e);
        }
        return null;
    }
    public String eliminarMaterialTraspasoAction(int index){
        salidasAlmacenDetalleList.remove(index);
        return null;
    }

    public String eliminarMaterial_action() {
        try {
            List salidasAlmacenDetalleAux = new ArrayList();
            Iterator i = salidasAlmacenDetalleList.iterator();
            while (i.hasNext()) {
                SalidasAlmacenDetalle salidasAlmacenDetalleItem = (SalidasAlmacenDetalle) i.next();
                if (salidasAlmacenDetalleItem.getChecked().booleanValue() == false) {
                    salidasAlmacenDetalleAux.add(salidasAlmacenDetalleItem);
                }
            }
            salidasAlmacenDetalleList.clear();
            salidasAlmacenDetalleList = salidasAlmacenDetalleAux;

        } catch (Exception e) {
            LOGGER.warn("error",e);
        }
        return null;
    }

    public String guardarEditarTraspasoSalidasAlmacen_action2() {
        try {

            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String consulta = "";
            consulta = " UPDATE TRASPASOS SET COD_ALMACEN_DESTINO = '" + traspasos.getAlmacenDestino().getCodAlmacen() + "' WHERE COD_SALIDA_ALMACEN = '" + traspasos.getSalidasAlmacen().getCodSalidaAlmacen() + "'";
            LOGGER.debug("consulta " + consulta);
            if (st.executeUpdate(consulta) > 0) {
                LOGGER.debug("se actualizo el traspaso");
            }

            st.close();
            con.close();

        } catch (Exception e) {
            LOGGER.warn("error",e);
        }
        return null;

    }

    public String anularSalidaAlmacenTraspaso_action() {
        try {
            Iterator i = traspasosList.iterator();
            while (i.hasNext()) {
                Traspasos traspasosItem = (Traspasos) i.next();
                if (traspasosItem.getChecked().booleanValue() == true) {
                    traspasos = traspasosItem;
                    break;
                }
            }
            Date fechaActual = new Date();
            if (this.verificaTransaccionCerradaAlmacen(fechaActual, usuario.getGestionesGlobal().getCodGestion()) == true) {
                mensaje = "Las transacciones para este mes fueron cerradas ";
                return null;
            }
            if (this.verificaDevolucionTraspasoSalidaAlmacen(traspasos) == true) { //tiene devoluciones de la salida del traspaso
                mensaje = "No se puede anular por que se realizo una devolucion de esta salida";
                return null;
            }//anulacion de salida
            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            Statement stUpdate = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            String consulta = " update salidas_almacen set cod_estado_salida_almacen= '2' "
                    + " where cod_salida_almacen = '" + traspasos.getSalidasAlmacen().getCodSalidaAlmacen() + "' ";
            stUpdate.executeUpdate(consulta);
            LOGGER.debug("consulta " + consulta);
            consulta = " update traspasos set cod_estado_traspaso = '4' "
                    + "where cod_salida_almacen= '" + traspasos.getSalidasAlmacen().getCodSalidaAlmacen() + "' ";
            stUpdate.executeUpdate(consulta);
            //restaurar las cantidades del ingreso al que afecto
            consulta = " select sad.COD_SALIDA_ALMACEN,sadi.COD_MATERIAL,sadi.COD_INGRESO_ALMACEN,sadi.ETIQUETA,sadi.CANTIDAD "
                    + " from SALIDAS_ALMACEN_DETALLE sad  "
                    + " inner join SALIDAS_ALMACEN_DETALLE_INGRESO sadi on sadi.COD_MATERIAL = sad.COD_MATERIAL "
                    + " and sadi.ETIQUETA = sad.ETIQUETA and sadi.cod_salida_almacen = sad.cod_salida_almacen "
                    + " inner join SALIDAS_ALMACEN sa on sa.COD_SALIDA_ALMACEN = sad.COD_SALIDA_ALMACEN "
                    + " where sad.COD_SALIDA_ALMACEN = '" + traspasos.getSalidasAlmacen().getCodSalidaAlmacen() + "' ";
            consulta = " select sad.COD_SALIDA_ALMACEN,  sadi.COD_MATERIAL,       sadi.COD_INGRESO_ALMACEN,       sadi.ETIQUETA,"
                    + "       sadi.CANTIDAD from SALIDAS_ALMACEN_DETALLE_INGRESO sadi inner join	SALIDAS_ALMACEN_DETALLE sad on sad.COD_SALIDA_ALMACEN = sadi.COD_SALIDA_ALMACEN and sad.COD_MATERIAL = sadi.COD_MATERIAL "
                    + "    inner join SALIDAS_ALMACEN s on s.COD_SALIDA_ALMACEN = sad.COD_SALIDA_ALMACEN    where s.COD_SALIDA_ALMACEN = '" + traspasos.getSalidasAlmacen().getCodSalidaAlmacen() + "' ";

            LOGGER.debug("consulta " + consulta);
            ResultSet rs = st.executeQuery(consulta);
            while (rs.next()) {
                consulta = " update ingresos_almacen_detalle_estado set cantidad_restante=cantidad_restante+'" + rs.getDouble("CANTIDAD") + "'"
                        + " where cod_ingreso_almacen='" + rs.getInt("COD_INGRESO_ALMACEN") + "'  "
                        + " and cod_material='" + rs.getInt("COD_MATERIAL") + "'  and etiqueta='" + rs.getString("ETIQUETA") + "' ";
                LOGGER.debug("consulta " + consulta);
                stUpdate.executeUpdate(consulta);
            }
            //actualizar las solicitudes de salida almacen
            if (traspasos.getSalidasAlmacen().getCodFormSalida() > 0) { //tiene solicitudes_salida de almacen
            }
            if (traspasos.getSalidasAlmacen().getCodFormSalida() > 0) {//con solicitudes salida almacen
                Statement st1 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                consulta = "select m.COD_MATERIAL,m.NOMBRE_MATERIAL,gr.COD_GRUPO,gr.NOMBRE_GRUPO,c.COD_CAPITULO,c.NOMBRE_CAPITULO,um.COD_UNIDAD_MEDIDA,um.NOMBRE_UNIDAD_MEDIDA,sad.CANTIDAD_SALIDA_ALMACEN "
                        + " from SALIDAS_ALMACEN_DETALLE sad "
                        + " inner join MATERIALES m on m.COD_MATERIAL = sad.COD_MATERIAL "
                        + " inner join GRUPOS gr on m.COD_GRUPO = gr.COD_GRUPO "
                        + " inner join CAPITULOS c on c.COD_CAPITULO = gr.COD_CAPITULO "
                        + " inner join UNIDADES_MEDIDA um on um.COD_UNIDAD_MEDIDA = sad.COD_UNIDAD_MEDIDA "
                        + " where sad.COD_SALIDA_ALMACEN = '" + traspasos.getSalidasAlmacen().getCodSalidaAlmacen() + "'";
                LOGGER.debug("consulta " + consulta);
                ResultSet rs1 = st1.executeQuery(consulta);
                while (rs1.next()) { //13
                    consulta = " update solicitudes_salida_detalle "
                            + " set cantidad_entregada=cantidad_entregada-'" + rs1.getFloat("CANTIDAD_SALIDA_ALMACEN") + "' "
                            + " where cod_form_salida='" + traspasos.getSalidasAlmacen().getCodFormSalida() + "' "
                            + " and cod_material='" + rs1.getString("COD_MATERIAL") + "'  "; //actualizar las solicitudes de salida almacen
                    LOGGER.debug("consulta " + consulta);
                    stUpdate.executeUpdate(consulta);
                }
                consulta = " select "
                        + " (select count(*) from SOLICITUDES_SALIDA_DETALLE s where s.CANTIDAD_ENTREGADA = 0 and s.COD_FORM_SALIDA=s1.COD_FORM_SALIDA ) DETALLE_NO_ENTREGADO, "
                        + " (select count(*) from SOLICITUDES_SALIDA_DETALLE s where s.COD_FORM_SALIDA=s1.COD_FORM_SALIDA ) DETALLE "
                        + " from SOLICITUDES_SALIDA_DETALLE s1 where s1.COD_FORM_SALIDA = '" + traspasos.getSalidasAlmacen().getCodFormSalida() + "' ";
                LOGGER.debug("consulta " + consulta);
                Statement st2 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs2 = st2.executeQuery(consulta);
                if (rs2.next()) {
                    if (rs2.getInt("DETALLE_NO_ENTREGADO") == rs2.getInt("DETALLE")) {
                        consulta = " update solicitudes_salida set cod_estado_solicitud_salida_almacen=1 where cod_form_salida='" + traspasos.getSalidasAlmacen().getCodFormSalida() + "' ";
                        LOGGER.debug("consulta " + consulta);
                        stUpdate.executeUpdate(consulta);
                    } else {
                        consulta = " update solicitudes_salida set cod_estado_solicitud_salida_almacen=3 where cod_form_salida='" + traspasos.getSalidasAlmacen().getCodFormSalida() + "' ";
                        LOGGER.debug("consulta " + consulta);
                        stUpdate.executeUpdate(consulta);
                    }
                }
            }
            traspasosList = this.listadoSalidasTraspasoAlmacen();
            mensaje = "Anulacion Satisfactoria";
        } catch (Exception e) {
            LOGGER.warn("error",e);
        }
        return null;
    }

    public boolean verificaDevolucionTraspasoSalidaAlmacen(Traspasos traspasos) {

        boolean devolucionSalidaAlmacen = false;
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select top 1 * from devoluciones d where d.cod_salida_almacen = '" + traspasos.getSalidasAlmacen().getCodSalidaAlmacen() + "' ";
            LOGGER.debug("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            if (rs.next()) {
                devolucionSalidaAlmacen = true;
            }

        } catch (Exception e) {
            LOGGER.warn("error",e);
        }
        return devolucionSalidaAlmacen;

    }

    public String verReporteSalidaAlmacenTraspaso_action() {
        try {
            Iterator i = traspasosList.iterator();
            while (i.hasNext()) {
                Traspasos traspasos = (Traspasos) i.next();
                if (traspasos.getChecked().booleanValue() == true) {
                    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                    Map<String, Object> sessionMap = externalContext.getSessionMap();
                    sessionMap.put("traspasos", traspasos);
                    break;
                }
            }
        } catch (Exception e) {
            LOGGER.warn("error",e);
        }
        return null;
    }

    //inicio ale traspaso
    public String generarTraspasoRechazados() {
        traspasoRechazados = true;
        return null;
    }

    public String generarTraspaso() {
        traspasoRechazados = false;
        return null;
    }

    public boolean isTraspasoRechazados() {
        return traspasoRechazados;
    }

    public void setTraspasoRechazados(boolean traspasoRechazados) {
        this.traspasoRechazados = traspasoRechazados;
    }

    //final ale traspaso
    public boolean isAdministradorAlmacen() {
        return administradorAlmacen;
    }

    public void setAdministradorAlmacen(boolean administradorAlmacen) {
        this.administradorAlmacen = administradorAlmacen;
    }

    public String verReporteSalidaAlmacen_action() {
        try {
            Iterator i = traspasosList.iterator();
            while (i.hasNext()) {
                Traspasos salidasAlmacen = (Traspasos) i.next();
                if (salidasAlmacen.getChecked().booleanValue() == true) {
                    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                    Map<String, Object> sessionMap = externalContext.getSessionMap();
                    sessionMap.put("codSalidaAlmacen", salidasAlmacen.getSalidasAlmacen().getCodSalidaAlmacen());
                    break;
                }
            }

        } catch (Exception e) {
            LOGGER.warn("error",e);
        }
        return null;
    }
}
