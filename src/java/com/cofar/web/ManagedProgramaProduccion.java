/*
* ManagedTipoCliente.java
* Created on 19 de febrero de 2008, 16:50
 */
package com.cofar.web;

import com.cofar.bean.ConfiguracionSalidaAlmacenProduccion;
import com.cofar.bean.FormulaMaestraDetalleEP;
import com.cofar.bean.FormulaMaestraDetalleES;
import com.cofar.bean.FormulaMaestraDetalleMP;
import com.cofar.bean.PresentacionesPrimarias;
import com.cofar.bean.PresentacionesProducto;
import com.cofar.bean.ProgramaProduccion;

import com.cofar.bean.ProgramaProduccionPeriodo;
import com.cofar.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.Map;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.richfaces.component.html.HtmlDataTable;
import com.cofar.util.Utiles;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Wilmer Manzaneda Chavez
 * @company COFAR
 */
public class ManagedProgramaProduccion extends ManagedBean{

    /**
     * Creates a new instance of ManagedTipoCliente
     */
    private Logger LOGGER;
    private ProgramaProduccion programaProduccionbean = new ProgramaProduccion();
    private List<ProgramaProduccion> programaProduccionList = new ArrayList<ProgramaProduccion>();
    private List<ProgramaProduccionPeriodo> programaProduccionPeriodoList;
    private Connection con;
    //private HtmlDataTable programaProduccionDataTable = new HtmlDataTable();
    private String codProgramaProd = "";
    private String alertaSalidasLote = "";
    private List tiposMaterialList = new ArrayList();
    private List tiposMaterialAgregarList = new ArrayList();
    private List tiposMaterialSalidaAlmacenList = new ArrayList();
    private List presentacionesPrimariasList = new ArrayList();
    private List materiaPrimaList = new ArrayList();
    private List empaquePrimarioList = new ArrayList();
    private List empaqueSecundarioList = new ArrayList();
    private boolean administradorAlmacen = false;

    private List presentacionesSecundariasList = new ArrayList();

    ProgramaProduccion programaProduccion = new ProgramaProduccion();

    PresentacionesPrimarias presentacionesPrimarias = new PresentacionesPrimarias();
    PresentacionesProducto presentacionesProducto = new PresentacionesProducto();
    int tipoSalidaAlmacenProgramaProduccion = 0; // tipo de salida de programa produccion 1 por lote entero 2 por tipo de programa produccion
    List configuracionSalidasMaterialProduccionList = new ArrayList();
    List personalList = new ArrayList();
    List tiposSalidasMaterialList = new ArrayList();
    ConfiguracionSalidaAlmacenProduccion configuracionSalidaAlmacenProduccion = new ConfiguracionSalidaAlmacenProduccion();
    List configuracionSalidaMaterialProduccionPersonaList = new ArrayList();
    HtmlDataTable programaProduccionPeriodoDataTable = new HtmlDataTable();
    HtmlDataTable programaProduccionPeriodoDesviacionDataTable = new HtmlDataTable();
    ProgramaProduccion programaProduccionBuscar = new ProgramaProduccion();
    List programaProduccionSeleccionadoList = new ArrayList();
    int salidasAlmacenConDesviacion = 0;
    private boolean permisoSalidaMateriaPrima = false;
    private boolean permisoSalidaEmpaquePrimario = false;
    private boolean permisoSalidaEmpaqueSecundario = false;
    private boolean permisoSolicitudMateriaPrima = false;
    private boolean permisoSolicitudEmpaquePrimario = false;
    private boolean permisoSolicitudEmpaqueSecundario = false;
    private boolean salidaMateriaPrima = false;
    private boolean solicitudSalida = false;
    private boolean salidaEmpaquePrimarioSecundario = false;

    private void cargarPermisosEspecialesUsuario(){
        ManagedAccesoSistema managed = (ManagedAccesoSistema)Util.getSessionBean("ManagedAccesoSistema");
        try {
            con = Util.openConnection(con);
            StringBuilder consulta = new StringBuilder("select c.COD_TIPO_PERMISO_ESPECIAL_BACO")
                                            .append(" from CONFIGURACION_PERMISO_ESPECIAL_BACO c")
                                            .append(" where c.COD_PERSONAL=").append(managed.getUsuarioModuloBean().getCodUsuarioGlobal())
                                                    .append(" and c.COD_ALMACEN=").append(managed.getAlmacenesGlobal().getCodAlmacen())
                                                    .append(" and c.COD_TIPO_PERMISO_ESPECIAL_BACO in (23,24,25,26,27,28)");
            LOGGER.debug("consulta cargar permisos especiales: "+consulta.toString());
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet res=st.executeQuery(consulta.toString());
            permisoSalidaEmpaqueSecundario = false;
            permisoSalidaMateriaPrima = false;
            permisoSalidaEmpaquePrimario = false;
            permisoSolicitudMateriaPrima=false;
            permisoSolicitudEmpaquePrimario = false;
            permisoSolicitudEmpaqueSecundario = false;
            while(res.next()){
                switch(res.getInt("COD_TIPO_PERMISO_ESPECIAL_BACO")){
                    case 23:{
                        permisoSalidaMateriaPrima = true;
                        break;
                    }
                    case 24:{
                        permisoSalidaEmpaquePrimario = true;
                        break;
                    }
                    case 25:{
                        permisoSalidaEmpaqueSecundario = true;
                        break;
                    }
                    case 26:{
                        permisoSolicitudMateriaPrima = true;
                        break;
                    }
                    case 27:{
                        permisoSolicitudEmpaquePrimario= true;
                        break;
                    }
                    case 28:{
                        permisoSolicitudEmpaqueSecundario = true;
                        break;
                    }
                }
            }
        } catch (SQLException ex) {
            LOGGER.warn(ex.getMessage());
        } catch (Exception ex) {
            LOGGER.warn(ex.getMessage());
        } finally {
            this.cerrarConexion(con);
        }
    }
    
    public ManagedProgramaProduccion() {
        LOGGER = LogManager.getRootLogger();
        List programaProduccionPeriodoList = new ArrayList();
        HtmlDataTable programaProduccionPeriodoDataTable = new HtmlDataTable();
        ProgramaProduccion programaProduccionBuscar = new ProgramaProduccion();

        //cargarProgramaProduccion();        
    }

    public String getCodProgramaProd() {
        return codProgramaProd;
    }

    public void setCodProgramaProd(String codProgramaProd) {
        this.codProgramaProd = codProgramaProd;
    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    /*
    public HtmlDataTable getProgramaProduccionDataTable() {
        return programaProduccionDataTable;
    }

    public void setProgramaProduccionDataTable(HtmlDataTable programaProduccionDataTable) {
        this.programaProduccionDataTable = programaProduccionDataTable;
    }*/
    public List<ProgramaProduccion> getProgramaProduccionList() {
        return programaProduccionList;
    }

    public void setProgramaProduccionList(List<ProgramaProduccion> programaProduccionList) {
        this.programaProduccionList = programaProduccionList;
    }

    public ProgramaProduccion getProgramaProduccionbean() {
        return programaProduccionbean;
    }

    public void setProgramaProduccionbean(ProgramaProduccion programaProduccionbean) {
        this.programaProduccionbean = programaProduccionbean;
    }

    public List getTiposMaterialList() {
        return tiposMaterialList;
    }

    public void setTiposMaterialList(List tiposMaterialList) {
        this.tiposMaterialList = tiposMaterialList;
    }

    public List getTiposMaterialSalidaAlmacenList() {
        return tiposMaterialSalidaAlmacenList;
    }

    public void setTiposMaterialSalidaAlmacenList(List tiposMaterialSalidaAlmacenList) {
        this.tiposMaterialSalidaAlmacenList = tiposMaterialSalidaAlmacenList;
    }

    public List getTiposMaterialAgregarList() {
        return tiposMaterialAgregarList;
    }

    public void setTiposMaterialAgregarList(List tiposMaterialAgregarList) {
        this.tiposMaterialAgregarList = tiposMaterialAgregarList;
    }

    public List getPresentacionesPrimariasList() {
        return presentacionesPrimariasList;
    }

    public void setPresentacionesPrimariasList(List presentacionesPrimariasList) {
        this.presentacionesPrimariasList = presentacionesPrimariasList;
    }

    public PresentacionesPrimarias getPresentacionesPrimarias() {
        return presentacionesPrimarias;
    }

    public void setPresentacionesPrimarias(PresentacionesPrimarias presentacionesPrimarias) {
        this.presentacionesPrimarias = presentacionesPrimarias;
    }

    public List getMateriaPrimaList() {
        return materiaPrimaList;
    }

    public void setMateriaPrimaList(List materiaPrimaList) {
        this.materiaPrimaList = materiaPrimaList;
    }

    public List getEmpaquePrimarioList() {
        return empaquePrimarioList;
    }

    public void setEmpaquePrimarioList(List empaquePrimarioList) {
        this.empaquePrimarioList = empaquePrimarioList;
    }

    public List getPresentacionesSecundariasList() {
        return presentacionesSecundariasList;
    }

    public void setPresentacionesSecundariasList(List presentacionesSecundariasList) {
        this.presentacionesSecundariasList = presentacionesSecundariasList;
    }

    public PresentacionesProducto getPresentacionesProducto() {
        return presentacionesProducto;
    }

    public void setPresentacionesProducto(PresentacionesProducto presentacionesProducto) {
        this.presentacionesProducto = presentacionesProducto;
    }

    public List getEmpaqueSecundarioList() {
        return empaqueSecundarioList;
    }

    public void setEmpaqueSecundarioList(List empaqueSecundarioList) {
        this.empaqueSecundarioList = empaqueSecundarioList;
    }

    public ProgramaProduccion getProgramaProduccion() {
        return programaProduccion;
    }

    public void setProgramaProduccion(ProgramaProduccion programaProduccion) {
        this.programaProduccion = programaProduccion;
    }

    public int getTipoSalidaAlmacenProgramaProduccion() {
        return tipoSalidaAlmacenProgramaProduccion;
    }

    public void setTipoSalidaAlmacenProgramaProduccion(int tipoSalidaAlmacenProgramaProduccion) {
        this.tipoSalidaAlmacenProgramaProduccion = tipoSalidaAlmacenProgramaProduccion;
    }

    public List getConfiguracionSalidasMaterialProduccionList() {
        return configuracionSalidasMaterialProduccionList;
    }

    public void setConfiguracionSalidasMaterialProduccionList(List configuracionSalidasMaterialProduccionList) {
        this.configuracionSalidasMaterialProduccionList = configuracionSalidasMaterialProduccionList;
    }

    public List getPersonalList() {
        return personalList;
    }

    public void setPersonalList(List personalList) {
        this.personalList = personalList;
    }

    public List getTiposSalidasMaterialList() {
        return tiposSalidasMaterialList;
    }

    public void setTiposSalidasMaterialList(List tiposSalidasMaterialList) {
        this.tiposSalidasMaterialList = tiposSalidasMaterialList;
    }

    public ConfiguracionSalidaAlmacenProduccion getConfiguracionSalidaAlmacenProduccion() {
        return configuracionSalidaAlmacenProduccion;
    }

    public void setConfiguracionSalidaAlmacenProduccion(ConfiguracionSalidaAlmacenProduccion configuracionSalidaAlmacenProduccion) {
        this.configuracionSalidaAlmacenProduccion = configuracionSalidaAlmacenProduccion;
    }

    public List getProgramaProduccionPeriodoList() {
        return programaProduccionPeriodoList;
    }

    public void setProgramaProduccionPeriodoList(List programaProduccionPeriodoList) {
        this.programaProduccionPeriodoList = programaProduccionPeriodoList;
    }

    public HtmlDataTable getProgramaProduccionPeriodoDataTable() {
        return programaProduccionPeriodoDataTable;
    }

    public void setProgramaProduccionPeriodoDataTable(HtmlDataTable programaProduccionPeriodoDataTable) {
        this.programaProduccionPeriodoDataTable = programaProduccionPeriodoDataTable;
    }

    public ProgramaProduccion getProgramaProduccionBuscar() {
        return programaProduccionBuscar;
    }

    public void setProgramaProduccionBuscar(ProgramaProduccion programaProduccionBuscar) {
        this.programaProduccionBuscar = programaProduccionBuscar;
    }

    public String getAlertaSalidasLote() {
        return alertaSalidasLote;
    }

    public void setAlertaSalidasLote(String alertaSalidasLote) {
        this.alertaSalidasLote = alertaSalidasLote;
    }

    public int getSalidasAlmacenConDesviacion() {
        return salidasAlmacenConDesviacion;
    }

    public void setSalidasAlmacenConDesviacion(int salidasAlmacenConDesviacion) {
        this.salidasAlmacenConDesviacion = salidasAlmacenConDesviacion;
    }

    public HtmlDataTable getProgramaProduccionPeriodoDesviacionDataTable() {
        return programaProduccionPeriodoDesviacionDataTable;
    }

    public void setProgramaProduccionPeriodoDesviacionDataTable(HtmlDataTable programaProduccionPeriodoDesviacionDataTable) {
        this.programaProduccionPeriodoDesviacionDataTable = programaProduccionPeriodoDesviacionDataTable;
    }
    private void cargarProgramaProduccionPeriodoList(){
        try {
            programaProduccionPeriodoList = new ArrayList<ProgramaProduccionPeriodo>();
            con = Util.openConnection(con);
            StringBuilder consulta = new StringBuilder("select * ")
                                        .append(" from VISTA_PROGRAMA_PRODUCCION_PERIODO vp")
                                        .append(" where vp.COD_ESTADO_PROGRAMA <> 4")
                                        .append(" order by vp.COD_PROGRAMA_PROD desc ");
            System.out.println("consulta cargar programa periodo: "+consulta.toString());
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet res=st.executeQuery(consulta.toString());
            while(res.next()){
                ProgramaProduccionPeriodo periodo = new ProgramaProduccionPeriodo();
                periodo.setCodProgramaProduccion(res.getString("COD_PROGRAMA_PROD"));
                periodo.setNombreProgramaProduccion(res.getString("NOMBRE_PROGRAMA_PROD"));
                periodo.setObsProgramaProduccion(res.getString("OBSERVACIONES"));
                periodo.getEstadoProgramaProduccion().setCodEstadoProgramaProd(res.getString("COD_ESTADO_PROGRAMA"));
                periodo.getEstadoProgramaProduccion().setNombreEstadoProgramaProd(res.getString("NOMBRE_ESTADO_PROGRAMA_PROD"));
                periodo.getTiposProduccion().setCodTipoProduccion(res.getInt("COD_TIPO_PRODUCCION"));
                periodo.getTiposProduccion().setNombreTipoProduccion(res.getString("NOMBRE_TIPO_PRODUCCION"));
                programaProduccionPeriodoList.add(periodo);
            }
        } catch (SQLException ex) {
            LOGGER.warn(ex.getMessage());
        } catch (Exception ex) {
            LOGGER.warn(ex.getMessage());
        } finally {
            this.cerrarConexion(con);
        }
    }

    public String getCargarSolicitudProgramaProduccion(){
        this.cargarPermisoPersonal();
        this.cargarPermisosEspecialesUsuario();
        setCodProgramaProd(programaProduccionbean.getCodProgramaProduccion());
        try {
            tiposSalidasMaterialList = this.cargarTiposSalidaMaterialList();
            configuracionSalidaMaterialProduccionPersonaList = this.cargarConfiguracionSalidaAlmacenProduccionPersona();
            for (Object dato : configuracionSalidaMaterialProduccionPersonaList) {
                ConfiguracionSalidaAlmacenProduccion valor = (ConfiguracionSalidaAlmacenProduccion) dato;
                if (valor.getTiposMaterial().getCodTipoMaterial() == 1) {
                    habilita_materiaPrimaSalidaProduccion = true;
                }
            }
        } catch (Exception ex) {
            LOGGER.warn("error", ex);
        }
        this.listadoProgramaProduccion();
        return null;
    }
    
    public String getCargarContenidoProgramaProduccion() {
        
        this.cargarPermisoPersonal();
        try {
            String codProgramaProdF = Util.getParameter("codProgramaProd");
            if (codProgramaProdF != null) {
                programaProduccionbean.setCodProgramaProduccion(codProgramaProdF);
                setCodProgramaProd(codProgramaProdF);
            }
            //this.listadoProgramaProduccion();
            //this.cargarTiposMaterial();
            tiposSalidasMaterialList = this.cargarTiposSalidaMaterialList();
            configuracionSalidaMaterialProduccionPersonaList = this.cargarConfiguracionSalidaAlmacenProduccionPersona();
            for (Object dato : configuracionSalidaMaterialProduccionPersonaList) {
                ConfiguracionSalidaAlmacenProduccion valor = (ConfiguracionSalidaAlmacenProduccion) dato;
                if (valor.getTiposMaterial().getCodTipoMaterial() == 1) {
                    habilita_materiaPrimaSalidaProduccion = true;

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void cargarTiposMaterial() {
        try {
            tiposMaterialList.clear();
            tiposMaterialList.add(new SelectItem(1, "MATERIAL PRIMARIO"));
            tiposMaterialList.add(new SelectItem(2, "MATERIAL EMPAQUE PRIMARIO"));
            tiposMaterialList.add(new SelectItem(3, "MATERIAL EMPAQUE SECUNDARIO"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String listadoProgramaProduccion() {
        //programaProduccionBuscar
        String codProgramaProdF = Util.getParameter("codProgramaProd");
        if (codProgramaProdF != null) {
            programaProduccionbean.setCodProgramaProduccion(codProgramaProdF);
            setCodProgramaProd(codProgramaProdF);
        }
        System.out.println("codProgramaProdF:::::::::::::::::" + codProgramaProdF);
        System.out.println("getCodProgramaProd:::::::::::::::::" + getCodProgramaProd());
        try {

            ManagedAccesoSistema accesoSistema = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            //Primero visualizar las areas de produccion las cuales puede visualizar el usuario
            String consulta = " SELECT UAPR.COD_AREA_EMPRESA FROM USUARIOS_AREA_PRODUCCION UAPR WHERE UAPR.COD_PERSONAL = " + accesoSistema.getUsuarioModuloBean().getCodUsuarioGlobal() + " ";
            setCon(Util.openConnection(getCon()));
            Statement st = getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            String codAreaEmpresa = "''";
            while (rs.next()) {
                codAreaEmpresa = codAreaEmpresa + "," + rs.getString("COD_AREA_EMPRESA");
            }

            String sql = " select * from ( "
                    + "select pp.cod_programa_prod,fm.cod_formula_maestra,pp.cod_lote_produccion,";
            sql += " pp.fecha_inicio,pp.fecha_final,pp.cod_estado_programa,pp.observacion,";
            sql += " cp.nombre_prod_semiterminado,cp.cod_compprod,fm.cantidad_lote"
                    + ",epp.NOMBRE_ESTADO_PROGRAMA_PROD,pp.cant_lote_produccion"
                    + ",tp.COD_TIPO_PROGRAMA_PROD,tp.NOMBRE_TIPO_PROGRAMA_PROD";
            sql += " ,ISNULL((SELECT C.NOMBRE_CATEGORIACOMPPROD"
                    + " FROM CATEGORIAS_COMPPROD C"
                    + " WHERE C.COD_CATEGORIACOMPPROD=cp.COD_CATEGORIACOMPPROD),'') categoria"
                    + ",pp.MATERIAL_TRANSITO "
                    + " ,(SELECT ISNULL(ae.NOMBRE_AREA_EMPRESA,'') FROM AREAS_EMPRESA ae WHERE ae.COD_AREA_EMPRESA =cp.COD_AREA_EMPRESA) NOMBRE_AREA_EMPRESA,cp.VIDA_UTIL,cp.COD_AREA_EMPRESA"
                    + " ,(select sum( ida.cant_ingreso_produccion) from programa_produccion_ingresos_acond ppria inner join ingresos_detalleacond ida "
                    + " on ida.cod_ingreso_acond = ppria.cod_ingreso_acond and ida.cod_lote_produccion = ppria.cod_lote_produccion and ida.cod_compprod = ppria.cod_compprod"
                    + " inner join INGRESOS_ACOND ia on ia.COD_INGRESO_ACOND = ida.COD_INGRESO_ACOND "
                    + " where ppria.cod_programa_prod = pp.cod_programa_prod and ppria.cod_compprod = pp.cod_compprod "
                    + " and ppria.cod_lote_produccion = pp.cod_lote_produccion "
                    + " and ppria.cod_tipo_programa_prod = pp.cod_tipo_programa_prod "
                    + " and ppria.cod_formula_maestra = pp.cod_formula_maestra and ia.COD_ESTADO_INGRESOACOND not in(2)) cant_ingreso_produccion,"
                    + "  isnull((select top 1 pf.COD_PROGRAMA_PROD"
                    + "  from PROGRAMA_PRODUCCION_DETALLE_FRACCIONES pf where"
                    + "  pf.COD_LOTE_PRODUCCION = pp.COD_LOTE_PRODUCCION"
                    + "  and pf.COD_COMPPROD = pp.COD_COMPPROD and pf.COD_FORMULA_MAESTRA= pp.COD_FORMULA_MAESTRA"
                    + "  and pf.COD_PROGRAMA_PROD = pp.COD_PROGRAMA_PROD and pf.COD_TIPO_PROGRAMA_PROD = pp.COD_TIPO_PROGRAMA_PROD),0) desv "
                    + " ,pp.COD_FORMULA_MAESTRA_VERSION,ppp.COD_TIPO_PRODUCCION,PP.COD_COMPPROD_VERSION, pp.cod_presentacion,"
                    + "(select cpv.NRO_VERSION from COMPONENTES_PROD_VERSION cpv where cpv.COD_COMPPROD=pp.COD_COMPPROD and cpv.COD_VERSION=pp.COD_COMPPROD_VERSION )"
                    + "as NRO_VERSION ";
            sql += " from programa_produccion pp,formula_maestra fm,componentes_prod cp,ESTADOS_PROGRAMA_PRODUCCION epp,TIPOS_PROGRAMA_PRODUCCION tp,PROGRAMA_PRODUCCION_PERIODO ppp "
                    + "            ";
            sql += " where  ppp.COD_PROGRAMA_PROD=pp.COD_PROGRAMA_PROD and pp.cod_formula_maestra=fm.cod_formula_maestra and cp.cod_compprod=fm.cod_compprod and epp.COD_ESTADO_PROGRAMA_PROD=pp.cod_estado_programa";
            sql += " and tp.COD_TIPO_PROGRAMA_PROD = pp.COD_TIPO_PROGRAMA_PROD  and pp.cod_estado_programa in (1,2,6,7)  ";
            if (!codProgramaProd.equals("")) {
                sql += " and pp.cod_programa_prod=" + codProgramaProd;
            }
            sql += " and cp.cod_area_empresa in (" + codAreaEmpresa + ")";
            if (!programaProduccionBuscar.getCodLoteProduccion().equals("")) {
                sql += " and pp.cod_lote_produccion='" + programaProduccionBuscar.getCodLoteProduccion() + "' ";
            }
            sql += ") as tabla where 0=0 " + (salidasAlmacenConDesviacion == 1 ? " and desv>0 " : " and desv=0 ") + "  order by nombre_prod_semiterminado";

            //and cp.cod_area_empresa in ("+accesoSistema.getCodAreaEmpresaGlobal() +")
            System.out.println("sql navegador:" + sql);
            setCon(Util.openConnection(getCon()));
            st = getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = st.executeQuery(sql);
            rs.last();
            int rows = rs.getRow();
            programaProduccionList.clear();
            rs.first();
            String cod = "";
            for (int i = 0; i < rows; i++) {
                ProgramaProduccion bean = new ProgramaProduccion();
                bean.setCodCompProdVersion(rs.getInt("COD_COMPPROD_VERSION"));
                bean.getTiposProduccion().setCodTipoProduccion(rs.getInt("COD_TIPO_PRODUCCION"));
                bean.setCodFormulaMaestraVersion(rs.getInt("COD_FORMULA_MAESTRA_VERSION"));
                bean.setCodProgramaProduccion(rs.getString(1));
                bean.getFormulaMaestra().setCodFormulaMaestra(rs.getString(2));
                bean.setCodLoteProduccion(rs.getString(3));
                bean.setCodLoteProduccionAnterior(rs.getString(3));
                String fechaInicio = "";
                /*String fechaInicioVector[]=fechaInicio.split(" ");
                fechaInicioVector=fechaInicioVector[0].split("-");
                fechaInicio=fechaInicioVector[2]+"/"+fechaInicioVector[1]+"/"+fechaInicioVector[0];
                bean.setFechaInicio(fechaInicio);*/
                String fechaFinal = "";
                /*String fechaFinalVector[]=fechaFinal.split(" ");
                fechaFinalVector=fechaFinalVector[0].split("-");
                fechaFinal=fechaFinalVector[2]+"/"+fechaFinalVector[1]+"/"+fechaFinalVector[0];
                bean.setFechaFinal(fechaFinal);*/
                bean.setCodEstadoPrograma(rs.getString(6));
                bean.setObservacion(rs.getString(7));
                bean.getFormulaMaestra().getComponentesProd().setNombreProdSemiterminado(rs.getString(8));
                bean.getFormulaMaestra().getComponentesProd().setCodCompprod(rs.getString(9));
                double cantidad = rs.getDouble(10);
                cantidad = redondear(cantidad, 3);
                NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
                DecimalFormat form = (DecimalFormat) nf;
                form.applyPattern("#,#00.0#");
                bean.getFormulaMaestra().setCantidadLote(form.format(cantidad));
                bean.getEstadoProgramaProduccion().setNombreEstadoProgramaProd(rs.getString(11));
                cantidad = rs.getDouble(12);
                cantidad = redondear(cantidad, 3);
                bean.getTiposProgramaProduccion().setCodTipoProgramaProd(rs.getString(13));
                bean.getTiposProgramaProduccion().setNombreProgramaProd(rs.getString(14));
                bean.getCategoriasCompProd().setNombreCategoriaCompProd(rs.getString(15));
                bean.setCantidadLote(form.format(cantidad));
                bean.getEstadoProgramaProduccion().setCodEstadoProgramaProd(rs.getString("cod_estado_programa"));
                bean.getFormulaMaestra().getComponentesProd().getAreasEmpresa().setNombreAreaEmpresa(rs.getString("NOMBRE_AREA_EMPRESA"));
                bean.getFormulaMaestra().getComponentesProd().getAreasEmpresa().setCodAreaEmpresa(rs.getString("COD_AREA_EMPRESA"));
                bean.getTiposProgramaProduccion().setCodTipoProgramaProd(rs.getString("COD_TIPO_PROGRAMA_PROD"));
                bean.getTiposProgramaProduccion().setNombreProgramaProd(rs.getString("NOMBRE_TIPO_PROGRAMA_PROD"));
                bean.setCantIngresoAcond(rs.getDouble("cant_ingreso_produccion"));
                //System.out.println("bean.setCantidadLote:"+bean.getCantidadLote());
                int materialTransito = rs.getInt(16);
                //System.out.println("materialTransito:"+materialTransito);
                if (materialTransito == 0) {
                    bean.setMaterialTransito("CON EXISTENCIA");
                    bean.setStyleClass("b");
                }
                if (materialTransito == 1) {
                    bean.setMaterialTransito("EN TRÁNSITO");
                    bean.setStyleClass("a");
                }
                bean.getFormulaMaestra().getComponentesProd().setVidaUtil(rs.getString("VIDA_UTIL") == null ? "0" : rs.getString("VIDA_UTIL"));
                bean.getPresentacionesProducto().setCodPresentacion(rs.getString("cod_presentacion"));
                bean.setNro_version(rs.getInt("nro_version"));
                programaProduccionList.add(bean);
                rs.next();
            }

            if (rs != null) {
                rs.close();
                st.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public double redondear(double numero, int decimales) {
        //System.out.println("redondear J: "+ numero+" round   "+new BigDecimal(String.valueOf(numero)).setScale(decimales, BigDecimal.ROUND_HALF_EVEN).doubleValue());

        return new BigDecimal(String.valueOf(numero)).setScale(decimales, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public double redondear(float numero, int decimales) {
        return new BigDecimal(String.valueOf(numero)).setScale(decimales, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * Metodo que cierra la conexion
     */
    public String getCloseConnection() throws SQLException {
        if (getCon() != null) {
            getCon().close();
        }
        return "";
    }

    public String generarSalidasAlmacen_action() {
        try {

            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            Map<String, Object> sessionMap = externalContext.getSessionMap();

            sessionMap.put("programaProduccion", programaProduccion);

            sessionMap.put("materiaPrimaList", materiaPrimaList);
            sessionMap.put("empaquePrimarioList", empaquePrimarioList);
            sessionMap.put("empaqueSecundarioList", empaqueSecundarioList);
            sessionMap.put("programaProduccionSeleccionadoList", programaProduccionSeleccionadoList);
            Utiles.direccionar("../salidasAlmacen/agregarSalidaAlmacenProgramaProduccion.jsf");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String generarSolicitudSalidasAlmacen_action() {
        try {

            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            Map<String, Object> sessionMap = externalContext.getSessionMap();
            sessionMap.put("programaProduccion", programaProduccion);

            sessionMap.put("materiaPrimaList", materiaPrimaList);
            sessionMap.put("empaquePrimarioList", empaquePrimarioList);
            sessionMap.put("empaqueSecundarioList", empaqueSecundarioList);
            Utiles.direccionar("agregarSolicitudSalidaAlmacenPP.jsf");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List cargarConfiguracionSalidaAlmacenProduccionPersona() {
        List configuracion = new ArrayList();
        try {
            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            String consulta = " select c.COD_PERSONAL,c.COD_TIPO_MATERIAL,c.COD_TIPO_SALIDA_ALMACEN_PRODUCCION "
                    + " from CONFIGURACION_SALIDA_ALMACEN_PRODUCCION c where c.COD_PERSONAL = '" + usuario.getUsuarioModuloBean().getCodUsuarioGlobal() + "' ";
            System.out.println("consulta " + consulta);
            ResultSet rs = st.executeQuery(consulta);
            while (rs.next()) {
                ConfiguracionSalidaAlmacenProduccion configuracionSalidaAlmacenProduccion = new ConfiguracionSalidaAlmacenProduccion();
                configuracionSalidaAlmacenProduccion.getPersonal().setCodPersonal(rs.getString("COD_PERSONAL"));
                configuracionSalidaAlmacenProduccion.getTiposMaterial().setCodTipoMaterial(rs.getInt("COD_TIPO_MATERIAL"));
                configuracionSalidaAlmacenProduccion.getTiposSalidaAlmacenProduccion().setCodTipoSalidaAlmacenProduccion(rs.getInt("COD_TIPO_SALIDA_ALMACEN_PRODUCCION"));
                configuracion.add(configuracionSalidaAlmacenProduccion);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return configuracion;
    }

    public int conMaterial(int codTipoMaterial,
            List configuracionSalidaMaterialProduccionPersonaList,
            ConfiguracionSalidaAlmacenProduccion configuracionSalidaAlmacenProduccion) {
        int codTipoMaterial1 = 0;
        Iterator i = configuracionSalidaMaterialProduccionPersonaList.iterator();
        while (i.hasNext()) {
            ConfiguracionSalidaAlmacenProduccion conf = (ConfiguracionSalidaAlmacenProduccion) i.next();
            System.out.println("valores comparados " + configuracionSalidaAlmacenProduccion.getTiposSalidaAlmacenProduccion().getCodTipoSalidaAlmacenProduccion()
                    + " " + conf.getTiposSalidaAlmacenProduccion().getCodTipoSalidaAlmacenProduccion() + " " + conf.getTiposMaterial().getCodTipoMaterial() + " " + codTipoMaterial);
            if (configuracionSalidaAlmacenProduccion.getTiposSalidaAlmacenProduccion().getCodTipoSalidaAlmacenProduccion()
                    == conf.getTiposSalidaAlmacenProduccion().getCodTipoSalidaAlmacenProduccion() && conf.getTiposMaterial().getCodTipoMaterial() == codTipoMaterial) {
                System.out.println("valores comparados 1 " + configuracionSalidaAlmacenProduccion.getTiposSalidaAlmacenProduccion().getCodTipoSalidaAlmacenProduccion()
                        + " " + conf.getTiposSalidaAlmacenProduccion().getCodTipoSalidaAlmacenProduccion() + " " + conf.getTiposMaterial().getCodTipoMaterial() + " " + codTipoMaterial);
                codTipoMaterial1 = 1;
            }
        }
        return codTipoMaterial1;
    }

    public void cargarSalidaAlmacenProduccionPorTipoProgramaProduccion() {
        try {
            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            String consulta = "";
            int codTipoMaterial = this.conMaterial(1, configuracionSalidaMaterialProduccionPersonaList, configuracionSalidaAlmacenProduccion); //con el tipo de material 1 materia prima
            materiaPrimaList.clear();
            double prorateo = programaProduccion.getCantIngresoAcond() / Double.parseDouble(programaProduccion.getFormulaMaestra().getCantidadLote().replace(",", ""));
            System.out.println("prorateo:---------->" + (Double.parseDouble(programaProduccion.getCantidadLote()) / Double.parseDouble(programaProduccion.getFormulaMaestra().getCantidadLote().replace(",", ""))));
            if (codTipoMaterial == 1) {
                //materia prima
                consulta = " select m.COD_MATERIAL,m.NOMBRE_MATERIAL,u.COD_UNIDAD_MEDIDA,u.NOMBRE_UNIDAD_MEDIDA,fmdmp.CANTIDAD  "
                        + " from FORMULA_MAESTRA fm  "
                        + " inner join FORMULA_MAESTRA_DETALLE_MP fmdmp on fm.COD_FORMULA_MAESTRA = fmdmp.COD_FORMULA_MAESTRA "
                        + " inner join MATERIALES m on m.COD_MATERIAL = fmdmp.COD_MATERIAL "
                        + " inner join UNIDADES_MEDIDA u on u.COD_UNIDAD_MEDIDA = fmdmp.COD_UNIDAD_MEDIDA "
                        + " where fm.COD_FORMULA_MAESTRA = '" + programaProduccion.getFormulaMaestra().getCodFormulaMaestra() + "' ";

                consulta = " select m.COD_MATERIAL,m.NOMBRE_MATERIAL,u.COD_UNIDAD_MEDIDA,u.NOMBRE_UNIDAD_MEDIDA,fmdmp.CANTIDAD CANTIDAD,pprd.CANTIDAD CANTIDAD_PPRD ,"
                        + " pprd.COD_UNIDAD_MEDIDA COD_UNIDAD_MEDIDA_PPRD,u1.NOMBRE_UNIDAD_MEDIDA NOMBRE_UNIDAD_MEDIDA_PPRD,u1.abreviatura"
                        + " from FORMULA_MAESTRA fm "
                        + " inner join FORMULA_MAESTRA_DETALLE_MP fmdmp on fm.COD_FORMULA_MAESTRA = fmdmp.COD_FORMULA_MAESTRA "
                        + " inner join MATERIALES m on m.COD_MATERIAL = fmdmp.COD_MATERIAL "
                        + " inner join UNIDADES_MEDIDA u on u.COD_UNIDAD_MEDIDA = fmdmp.COD_UNIDAD_MEDIDA"
                        + " inner join PROGRAMA_PRODUCCION_DETALLE pprd on pprd.COD_MATERIAL = fmdmp.COD_MATERIAL "
                        + " and fm.COD_FORMULA_MAESTRA = fmdmp.COD_FORMULA_MAESTRA"
                        + " inner join UNIDADES_MEDIDA u1 on u1.COD_UNIDAD_MEDIDA = pprd.COD_UNIDAD_MEDIDA"
                        + " where fm.COD_FORMULA_MAESTRA = '" + programaProduccion.getFormulaMaestra().getCodFormulaMaestra() + "' "
                        + " and pprd.COD_PROGRAMA_PROD = '" + programaProduccion.getCodProgramaProduccion() + "' "
                        + " and pprd.COD_TIPO_PROGRAMA_PROD = '" + programaProduccion.getTiposProgramaProduccion().getCodTipoProgramaProd() + "' "
                        + " and pprd.COD_LOTE_PRODUCCION = '" + programaProduccion.getCodLoteProduccion() + "' "
                        + " and pprd.COD_COMPPROD = '" + programaProduccion.getFormulaMaestra().getComponentesProd().getCodCompprod() + "' order by m.nombre_material asc";
                consulta = " select m.COD_MATERIAL,m.NOMBRE_MATERIAL,u.COD_UNIDAD_MEDIDA,u.NOMBRE_UNIDAD_MEDIDA,fmdmp.CANTIDAD,u.abreviatura"
                        + " from FORMULA_MAESTRA fm "
                        + " inner join FORMULA_MAESTRA_DETALLE_MP fmdmp on fmdmp.COD_FORMULA_MAESTRA = fm.COD_FORMULA_MAESTRA "
                        + " inner join materiales m on fmdmp.COD_MATERIAL = m.COD_MATERIAL "
                        + " inner join UNIDADES_MEDIDA u on u.COD_UNIDAD_MEDIDA = fmdmp.COD_UNIDAD_MEDIDA "
                        + " where fm.COD_FORMULA_MAESTRA = '" + programaProduccion.getFormulaMaestra().getCodFormulaMaestra() + "' ";

                System.out.println("consulta " + consulta);

                ResultSet rsMP = st.executeQuery(consulta);
                while (rsMP.next()) {
                    FormulaMaestraDetalleMP formulaMaestraDetalleMP = new FormulaMaestraDetalleMP();
                    formulaMaestraDetalleMP.setChecked(true);
                    formulaMaestraDetalleMP.getMateriales().setCodMaterial(rsMP.getString("COD_MATERIAL"));
                    formulaMaestraDetalleMP.getMateriales().setNombreMaterial(rsMP.getString("NOMBRE_MATERIAL"));
                    formulaMaestraDetalleMP.getUnidadesMedida().setCodUnidadMedida(rsMP.getInt("COD_UNIDAD_MEDIDA"));
                    formulaMaestraDetalleMP.getUnidadesMedida().setNombreUnidadMedida(rsMP.getString("NOMBRE_UNIDAD_MEDIDA"));
                    formulaMaestraDetalleMP.getUnidadesMedida().setAbreviatura(rsMP.getString("ABREVIATURA"));
                    //formulaMaestraDetalleMP.setCantidad(Float.valueOf(String.valueOf((rsMP.getDouble("CANTIDAD")*prorateo)>0?rsMP.getDouble("CANTIDAD")*prorateo:rsMP.getDouble("CANTIDAD"))));
                    formulaMaestraDetalleMP.setCantidad(rsMP.getDouble("CANTIDAD"));
                    materiaPrimaList.add(formulaMaestraDetalleMP);
                }
                rsMP.close();
            }
            String codEmpaque = "";
            String nombreEmpaque = "";
            codTipoMaterial = this.conMaterial(2, configuracionSalidaMaterialProduccionPersonaList, configuracionSalidaAlmacenProduccion); //con el tipo de material 2 material de empaque primario
            empaquePrimarioList.clear();
            if (codTipoMaterial == 1) {
                // cargar presentacionPrimaria
                consulta = " select fep.COD_FORMULA_MAESTRA,ep.nombre_envaseprim,ep.cod_envaseprim,pp.CANTIDAD,pp.cod_presentacion_primaria "
                        + " from FORMULA_MAESTRA fep,PRESENTACIONES_PRIMARIAS pp,ENVASES_PRIMARIOS ep  "
                        + " where PP.COD_COMPPROD=fep.COD_COMPPROD AND fep.COD_FORMULA_MAESTRA='" + programaProduccion.getFormulaMaestra().getCodFormulaMaestra() + "'  "
                        + " and ep.cod_envaseprim=pp.cod_envaseprim  "
                        + " and pp.cod_presentacion_primaria in (select pprd.cod_presentacion_primaria from programa_produccion_detalle pprd "
                        + " where pprd.cod_programa_prod = '" + programaProduccion.getCodProgramaProduccion() + "' "
                        + " and pprd.cod_tipo_programa_prod = '" + programaProduccion.getTiposProgramaProduccion().getCodTipoProgramaProd() + "' "
                        + " and pprd.cod_compprod = '" + programaProduccion.getFormulaMaestra().getComponentesProd().getCodCompprod() + "' "
                        + " and pprd.cod_lote_produccion = '" + programaProduccion.getCodLoteProduccion() + "' ) "
                        + " order by ep.nombre_envaseprim  ";

                consulta = " select mt.COD_MATERIAL,mt.NOMBRE_MATERIAL,um.COD_UNIDAD_MEDIDA,um.NOMBRE_UNIDAD_MEDIDA,fmdep.CANTIDAD CANTIDAD,ep.cod_envaseprim,ep.nombre_envaseprim,um.abreviatura "
                        + " from PROGRAMA_PRODUCCION_DETALLE pprd "
                        + " inner join FORMULA_MAESTRA fm on fm.COD_COMPPROD = pprd.COD_COMPPROD "
                        + " inner join materiales mt on mt.COD_MATERIAL = pprd.COD_MATERIAL "
                        + " inner join UNIDADES_MEDIDA um on um.COD_UNIDAD_MEDIDA = pprd.COD_UNIDAD_MEDIDA "
                        + " inner join FORMULA_MAESTRA_DETALLE_EP fmdep on fmdep.COD_FORMULA_MAESTRA = fm.COD_FORMULA_MAESTRA "
                        + " and fmdep.COD_MATERIAL = pprd.COD_MATERIAL "
                        + " inner join PRESENTACIONES_PRIMARIAS prp on prp.COD_PRESENTACION_PRIMARIA = fmdep.COD_PRESENTACION_PRIMARIA "
                        + " and prp.COD_TIPO_PROGRAMA_PROD = pprd.COD_TIPO_PROGRAMA_PROD   "
                        + " inner join ENVASES_PRIMARIOS ep on ep.cod_envaseprim = prp.COD_ENVASEPRIM "
                        + " where fm.COD_FORMULA_MAESTRA = '" + programaProduccion.getFormulaMaestra().getCodFormulaMaestra() + "' "
                        + " and pprd.COD_PROGRAMA_PROD = '" + programaProduccion.getCodProgramaProduccion() + "' "
                        + " and pprd.COD_TIPO_PROGRAMA_PROD = '" + programaProduccion.getTiposProgramaProduccion().getCodTipoProgramaProd() + "' "
                        + " and pprd.COD_LOTE_PRODUCCION = '" + programaProduccion.getCodLoteProduccion() + "' "
                        + " and pprd.COD_COMPPROD = '" + programaProduccion.getFormulaMaestra().getComponentesProd().getCodCompprod() + "'  ";

                System.out.println("consulta " + consulta);
                ResultSet rs = st.executeQuery(consulta);

                while (rs.next()) {
                    FormulaMaestraDetalleEP formulaMaestraDetalleEP = new FormulaMaestraDetalleEP();
                    formulaMaestraDetalleEP.setChecked(true);
                    formulaMaestraDetalleEP.getMateriales().setCodMaterial(rs.getString("COD_MATERIAL"));
                    formulaMaestraDetalleEP.getMateriales().setNombreMaterial(rs.getString("NOMBRE_MATERIAL"));
                    formulaMaestraDetalleEP.setCantidad(Float.valueOf(String.valueOf((rs.getDouble("CANTIDAD") * prorateo) > 0 ? rs.getDouble("CANTIDAD") * prorateo : rs.getDouble("CANTIDAD"))));
                    formulaMaestraDetalleEP.getUnidadesMedida().setCodUnidadMedida(rs.getInt("COD_UNIDAD_MEDIDA"));
                    formulaMaestraDetalleEP.getUnidadesMedida().setNombreUnidadMedida(rs.getString("NOMBRE_UNIDAD_MEDIDA"));
                    formulaMaestraDetalleEP.getUnidadesMedida().setAbreviatura(rs.getString("ABREVIATURA"));
                    codEmpaque = rs.getString("cod_envaseprim");
                    nombreEmpaque = rs.getString("nombre_envaseprim");
                    empaquePrimarioList.add(formulaMaestraDetalleEP);
                }
                //presentacionesPrimariasList.add(new SelectItem("0","-NINGUNO-"));
                presentacionesPrimariasList.clear();
                presentacionesPrimariasList.add(new SelectItem(codEmpaque, nombreEmpaque)); //rs.getString("cod_presentacion_primaria"),rs.getString("nombre_envaseprim") + " x " + rs.getInt("CANTIDAD")
                rs.close();
                //while(rs.next()){

                //}
            }
            codTipoMaterial = this.conMaterial(3, configuracionSalidaMaterialProduccionPersonaList, configuracionSalidaAlmacenProduccion); //con el tipo de material 3 material de empaque secundario
            empaqueSecundarioList.clear();
            if (codTipoMaterial == 1) {
                //cargar presentacionSecundaria
                consulta = " select pp.cod_presentacion,pp.NOMBRE_PRODUCTO_PRESENTACION, "
                        + " pp.cantidad_presentacion from formula_maestra fm  "
                        + " inner join COMPONENTES_PROD cp on cp.COD_COMPPROD = fm.COD_COMPPROD "
                        + " inner join COMPONENTES_PRESPROD c on c.COD_COMPPROD = cp.COD_COMPPROD "
                        + " inner join PRESENTACIONES_PRODUCTO pp on pp.cod_presentacion = c.COD_PRESENTACION "
                        + " inner join ENVASES_SECUNDARIOS es on es.COD_ENVASESEC = pp.COD_ENVASESEC "
                        + " where fm.COD_FORMULA_MAESTRA = '" + programaProduccion.getFormulaMaestra().getCodFormulaMaestra() + "' "
                        + " and pp.cod_presentacion in (select pprd.cod_presentacion_producto from programa_produccion_detalle pprd "
                        + " where pprd.cod_programa_prod = '" + programaProduccion.getCodProgramaProduccion() + "' "
                        + " and pprd.cod_tipo_programa_prod = '" + programaProduccion.getTiposProgramaProduccion().getCodTipoProgramaProd() + "' "
                        + " and pprd.cod_compprod = '" + programaProduccion.getFormulaMaestra().getComponentesProd().getCodCompprod() + "' "
                        + " and pprd.cod_lote_produccion = '" + programaProduccion.getCodLoteProduccion() + "' ) ";
                consulta = " select mt.COD_MATERIAL,mt.NOMBRE_MATERIAL,um.COD_UNIDAD_MEDIDA,um.NOMBRE_UNIDAD_MEDIDA,pprd.CANTIDAD,prp.cod_presentacion,prp.NOMBRE_PRODUCTO_PRESENTACION "
                        + " from PROGRAMA_PRODUCCION_DETALLE pprd "
                        + " inner join FORMULA_MAESTRA fm on fm.COD_COMPPROD = pprd.COD_COMPPROD "
                        + " inner join materiales mt on mt.COD_MATERIAL = pprd.COD_MATERIAL  "
                        + " inner join UNIDADES_MEDIDA um on um.COD_UNIDAD_MEDIDA = pprd.COD_UNIDAD_MEDIDA "
                        + " inner join FORMULA_MAESTRA_DETALLE_ES fmdes on fmdes.COD_FORMULA_MAESTRA = fm.COD_FORMULA_MAESTRA "
                        + " and fmdes.COD_MATERIAL = pprd.COD_MATERIAL "
                        + " inner join PRESENTACIONES_PRODUCTO prp on prp.cod_presentacion = fmdes.COD_PRESENTACION_PRODUCTO "
                        + " and prp.COD_TIPO_PROGRAMA_PROD = pprd.COD_TIPO_PROGRAMA_PROD "
                        + " where fm.COD_FORMULA_MAESTRA = '" + programaProduccion.getFormulaMaestra().getCodFormulaMaestra() + "' "
                        + " and pprd.COD_PROGRAMA_PROD = '" + programaProduccion.getCodProgramaProduccion() + "' "
                        + " and pprd.COD_TIPO_PROGRAMA_PROD = '" + programaProduccion.getTiposProgramaProduccion().getCodTipoProgramaProd() + "' "
                        + " and pprd.COD_LOTE_PRODUCCION = '" + programaProduccion.getCodLoteProduccion() + "' "
                        + " and pprd.COD_COMPPROD = '" + programaProduccion.getFormulaMaestra().getComponentesProd().getCodCompprod() + "' ";
                consulta = " select distinct mt.COD_MATERIAL, mt.NOMBRE_MATERIAL,   um.COD_UNIDAD_MEDIDA,   um.NOMBRE_UNIDAD_MEDIDA,    fmdes.CANTIDAD CANTIDAD,"
                        + "  prp.cod_presentacion,       prp.NOMBRE_PRODUCTO_PRESENTACION,       um.abreviatura"
                        + " from PROGRAMA_PRODUCCION_INGRESOS_ACOND pprd     inner join FORMULA_MAESTRA fm on fm.COD_COMPPROD = pprd.COD_COMPPROD     "
                        + " inner join FORMULA_MAESTRA_DETALLE_ES fmdes on fmdes.COD_FORMULA_MAESTRA =     fm.COD_FORMULA_MAESTRA"
                        + " inner join materiales mt on mt.COD_MATERIAL = fmdes.COD_MATERIAL     inner join UNIDADES_MEDIDA um on um.COD_UNIDAD_MEDIDA =  fmdes.COD_UNIDAD_MEDIDA"
                        + " inner join COMPONENTES_PRESPROD cprp on cprp.COD_COMPPROD = fm.COD_COMPPROD  and cprp.COD_TIPO_PROGRAMA_PROD = pprd.COD_TIPO_PROGRAMA_PROD"
                        + " inner join PRESENTACIONES_PRODUCTO prp on prp.cod_presentacion =    fmdes.COD_PRESENTACION_PRODUCTO and prp.cod_presentacion =  cprp.COD_PRESENTACION "
                        + " where fm.COD_FORMULA_MAESTRA = '" + programaProduccion.getFormulaMaestra().getCodFormulaMaestra() + "' "
                        + " and pprd.COD_PROGRAMA_PROD = '" + programaProduccion.getCodProgramaProduccion() + "' "
                        + " and pprd.COD_TIPO_PROGRAMA_PROD = '" + programaProduccion.getTiposProgramaProduccion().getCodTipoProgramaProd() + "' "
                        + " and pprd.COD_LOTE_PRODUCCION = '" + programaProduccion.getCodLoteProduccion() + "' "
                        + " and  pprd.COD_COMPPROD = '" + programaProduccion.getFormulaMaestra().getComponentesProd().getCodCompprod() + "' ";

                ResultSet rsPS = st.executeQuery(consulta);
                while (rsPS.next()) {
                    FormulaMaestraDetalleES formulaMaestraDetalleES = new FormulaMaestraDetalleES();
                    formulaMaestraDetalleES.setChecked(true);
                    formulaMaestraDetalleES.getMateriales().setCodMaterial(rsPS.getString("COD_MATERIAL"));
                    formulaMaestraDetalleES.getMateriales().setNombreMaterial(rsPS.getString("NOMBRE_MATERIAL"));
                    formulaMaestraDetalleES.setCantidad(Float.valueOf(String.valueOf(this.redondear((rsPS.getDouble("CANTIDAD") * prorateo) > 0 ? rsPS.getDouble("CANTIDAD") * prorateo : rsPS.getDouble("CANTIDAD"), 0))));
                    formulaMaestraDetalleES.getUnidadesMedida().setCodUnidadMedida(rsPS.getInt("COD_UNIDAD_MEDIDA"));
                    formulaMaestraDetalleES.getUnidadesMedida().setNombreUnidadMedida(rsPS.getString("NOMBRE_UNIDAD_MEDIDA"));
                    formulaMaestraDetalleES.getUnidadesMedida().setAbreviatura(rsPS.getString("ABREVIATURA"));
                    codEmpaque = rsPS.getString("cod_presentacion");
                    nombreEmpaque = rsPS.getString("NOMBRE_PRODUCTO_PRESENTACION");
                    empaqueSecundarioList.add(formulaMaestraDetalleES);
                }

                System.out.println("consulta " + consulta);
                presentacionesSecundariasList.clear();
                //presentacionesSecundariasList.add(new SelectItem("0","-NINGUNO-"));
                presentacionesSecundariasList.add(new SelectItem(codEmpaque, nombreEmpaque)); //rsPS.getString("cod_presentacion"),rsPS.getString("NOMBRE_PRODUCTO_PRESENTACION")
                //while(rsPS.next()){

                //}
                rsPS.close();
            }

            //empaquePrimarioList.clear();
            //empaqueSecundarioList.clear();
            st.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void agrupaProgramaProduccion(ProgramaProduccion programaProduccion, List programaProduccionList) {
        Iterator i = programaProduccionList.iterator();
        programaProduccion.getFormulaMaestra().setCodFormulaMaestra("0");
        programaProduccion.getFormulaMaestra().getComponentesProd().setCodCompprod("0");
        while (i.hasNext()) {
            ProgramaProduccion programaProduccionItem = (ProgramaProduccion) i.next();
            programaProduccion.getFormulaMaestra().setCodFormulaMaestra(programaProduccion.getFormulaMaestra().getCodFormulaMaestra() + "," + programaProduccionItem.getFormulaMaestra().getCodFormulaMaestra());
            programaProduccion.getFormulaMaestra().getComponentesProd().setCodCompprod(programaProduccion.getFormulaMaestra().getComponentesProd().getCodCompprod() + "," + programaProduccionItem.getFormulaMaestra().getComponentesProd().getCodCompprod());
            programaProduccion.getTiposProgramaProduccion().setCodTipoProgramaProd(programaProduccion.getTiposProgramaProduccion().getCodTipoProgramaProd() + "," + programaProduccionItem.getTiposProgramaProduccion().getCodTipoProgramaProd());
        }
    }

    public void cargarSalidaAlmacenProduccionPorTipoProgramaProduccion1() {
        try {
            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            String consulta = "";
            int codTipoMaterial = this.conMaterial(1, configuracionSalidaMaterialProduccionPersonaList, configuracionSalidaAlmacenProduccion); //con el tipo de material 1 materia prima
            materiaPrimaList.clear();
            double prorateo = programaProduccion.getCantIngresoAcond() / Double.parseDouble(programaProduccion.getFormulaMaestra().getCantidadLote().replace(",", "")); //prorateo para acond
            double prorateoMP = Double.parseDouble(programaProduccion.getCantidadLote().replace(",", "")) / Double.parseDouble(programaProduccion.getFormulaMaestra().getCantidadLote().replace(",", "")); //prorateo para acond
            //programaProduccion para la agrupacion de items
            ProgramaProduccion programaProduccionSeleccionados = new ProgramaProduccion();
            this.agrupaProgramaProduccion(programaProduccionSeleccionados, programaProduccionSeleccionadoList);
            if (codTipoMaterial == 1) {
                //materia prima
                consulta = " select m.COD_MATERIAL,m.NOMBRE_MATERIAL,u.COD_UNIDAD_MEDIDA,u.NOMBRE_UNIDAD_MEDIDA,fmdmp.CANTIDAD  "
                        + " from FORMULA_MAESTRA fm  "
                        + " inner join FORMULA_MAESTRA_DETALLE_MP fmdmp on fm.COD_FORMULA_MAESTRA = fmdmp.COD_FORMULA_MAESTRA "
                        + " inner join MATERIALES m on m.COD_MATERIAL = fmdmp.COD_MATERIAL "
                        + " inner join UNIDADES_MEDIDA u on u.COD_UNIDAD_MEDIDA = fmdmp.COD_UNIDAD_MEDIDA "
                        + " where fm.COD_FORMULA_MAESTRA = '" + programaProduccion.getFormulaMaestra().getCodFormulaMaestra() + "' ";

                consulta = " select m.COD_MATERIAL,m.NOMBRE_MATERIAL,u.COD_UNIDAD_MEDIDA,u.NOMBRE_UNIDAD_MEDIDA,fmdmp.CANTIDAD CANTIDAD,pprd.CANTIDAD CANTIDAD_PPRD ,"
                        + " pprd.COD_UNIDAD_MEDIDA COD_UNIDAD_MEDIDA_PPRD,u1.NOMBRE_UNIDAD_MEDIDA NOMBRE_UNIDAD_MEDIDA_PPRD,u1.abreviatura"
                        + " from FORMULA_MAESTRA fm "
                        + " inner join FORMULA_MAESTRA_DETALLE_MP fmdmp on fm.COD_FORMULA_MAESTRA = fmdmp.COD_FORMULA_MAESTRA "
                        + " inner join MATERIALES m on m.COD_MATERIAL = fmdmp.COD_MATERIAL "
                        + " inner join UNIDADES_MEDIDA u on u.COD_UNIDAD_MEDIDA = fmdmp.COD_UNIDAD_MEDIDA"
                        + " inner join PROGRAMA_PRODUCCION_DETALLE pprd on pprd.COD_MATERIAL = fmdmp.COD_MATERIAL "
                        + " and fm.COD_FORMULA_MAESTRA = fmdmp.COD_FORMULA_MAESTRA"
                        + " inner join UNIDADES_MEDIDA u1 on u1.COD_UNIDAD_MEDIDA = pprd.COD_UNIDAD_MEDIDA"
                        + " where fm.COD_FORMULA_MAESTRA = '" + programaProduccion.getFormulaMaestra().getCodFormulaMaestra() + "' "
                        + " and pprd.COD_PROGRAMA_PROD = '" + programaProduccion.getCodProgramaProduccion() + "' "
                        + " and pprd.COD_TIPO_PROGRAMA_PROD = '" + programaProduccion.getTiposProgramaProduccion().getCodTipoProgramaProd() + "' "
                        + " and pprd.COD_LOTE_PRODUCCION = '" + programaProduccion.getCodLoteProduccion() + "' "
                        + " and pprd.COD_COMPPROD = '" + programaProduccion.getFormulaMaestra().getComponentesProd().getCodCompprod() + "' order by m.nombre_material asc";
                consulta = " select m.COD_MATERIAL,m.NOMBRE_MATERIAL,u.COD_UNIDAD_MEDIDA,u.NOMBRE_UNIDAD_MEDIDA,sum(fmdmp.CANTIDAD*'" + prorateoMP + "') cantidad,u.abreviatura"
                        + " from FORMULA_MAESTRA fm "
                        + " inner join FORMULA_MAESTRA_DETALLE_MP fmdmp on fmdmp.COD_FORMULA_MAESTRA = fm.COD_FORMULA_MAESTRA "
                        + " inner join materiales m on fmdmp.COD_MATERIAL = m.COD_MATERIAL "
                        + " inner join UNIDADES_MEDIDA u on u.COD_UNIDAD_MEDIDA = fmdmp.COD_UNIDAD_MEDIDA "
                        + " where fm.COD_FORMULA_MAESTRA in (" + programaProduccionSeleccionados.getFormulaMaestra().getCodFormulaMaestra() + ")"
                        + " group by m.COD_MATERIAL,m.NOMBRE_MATERIAL,u.COD_UNIDAD_MEDIDA,u.NOMBRE_UNIDAD_MEDIDA,u.abreviatura";

                System.out.println("consulta " + consulta);

                ResultSet rsMP = st.executeQuery(consulta);
                while (rsMP.next()) {
                    FormulaMaestraDetalleMP formulaMaestraDetalleMP = new FormulaMaestraDetalleMP();
                    formulaMaestraDetalleMP.setChecked(true);
                    formulaMaestraDetalleMP.getMateriales().setCodMaterial(rsMP.getString("COD_MATERIAL"));
                    formulaMaestraDetalleMP.getMateriales().setNombreMaterial(rsMP.getString("NOMBRE_MATERIAL"));
                    formulaMaestraDetalleMP.getUnidadesMedida().setCodUnidadMedida(rsMP.getInt("COD_UNIDAD_MEDIDA"));
                    formulaMaestraDetalleMP.getUnidadesMedida().setNombreUnidadMedida(rsMP.getString("NOMBRE_UNIDAD_MEDIDA"));
                    formulaMaestraDetalleMP.getUnidadesMedida().setAbreviatura(rsMP.getString("ABREVIATURA"));
                    formulaMaestraDetalleMP.setCantidad((rsMP.getDouble("CANTIDAD")));
                    formulaMaestraDetalleMP.setCantidad(((rsMP.getDouble("CANTIDAD") * prorateoMP) > 0 ? rsMP.getDouble("CANTIDAD") * prorateoMP : rsMP.getDouble("CANTIDAD")));
                    materiaPrimaList.add(formulaMaestraDetalleMP);
                }
                rsMP.close();
            }
            String codEmpaque = "";
            String nombreEmpaque = "";
            codTipoMaterial = this.conMaterial(2, configuracionSalidaMaterialProduccionPersonaList, configuracionSalidaAlmacenProduccion); //con el tipo de material 2 material de empaque primario
            empaquePrimarioList.clear();
            if (codTipoMaterial == 1) {
                // cargar presentacionPrimaria
                consulta = " select fep.COD_FORMULA_MAESTRA,ep.nombre_envaseprim,ep.cod_envaseprim,pp.CANTIDAD,pp.cod_presentacion_primaria "
                        + " from FORMULA_MAESTRA fep,PRESENTACIONES_PRIMARIAS pp,ENVASES_PRIMARIOS ep  "
                        + " where PP.COD_COMPPROD=fep.COD_COMPPROD AND fep.COD_FORMULA_MAESTRA='" + programaProduccion.getFormulaMaestra().getCodFormulaMaestra() + "'  "
                        + " and ep.cod_envaseprim=pp.cod_envaseprim  "
                        + " and pp.cod_presentacion_primaria in (select pprd.cod_presentacion_primaria from programa_produccion_detalle pprd "
                        + " where pprd.cod_programa_prod = '" + programaProduccion.getCodProgramaProduccion() + "' "
                        + " and pprd.cod_tipo_programa_prod = '" + programaProduccion.getTiposProgramaProduccion().getCodTipoProgramaProd() + "' "
                        + " and pprd.cod_compprod = '" + programaProduccion.getFormulaMaestra().getComponentesProd().getCodCompprod() + "' "
                        + " and pprd.cod_lote_produccion = '" + programaProduccion.getCodLoteProduccion() + "' ) "
                        + " order by ep.nombre_envaseprim  ";

                consulta = " select mt.COD_MATERIAL,mt.NOMBRE_MATERIAL,um.COD_UNIDAD_MEDIDA,um.NOMBRE_UNIDAD_MEDIDA,sum(fmdep.CANTIDAD) CANTIDAD,um.abreviatura "
                        + //ep.cod_envaseprim,ep.nombre_envaseprim,
                        " from PROGRAMA_PRODUCCION_DETALLE pprd "
                        + " inner join FORMULA_MAESTRA fm on fm.COD_COMPPROD = pprd.COD_COMPPROD "
                        + " inner join materiales mt on mt.COD_MATERIAL = pprd.COD_MATERIAL "
                        + " inner join UNIDADES_MEDIDA um on um.COD_UNIDAD_MEDIDA = pprd.COD_UNIDAD_MEDIDA "
                        + " inner join FORMULA_MAESTRA_DETALLE_EP fmdep on fmdep.COD_FORMULA_MAESTRA = fm.COD_FORMULA_MAESTRA "
                        + " and fmdep.COD_MATERIAL = pprd.COD_MATERIAL "
                        + " inner join PRESENTACIONES_PRIMARIAS prp on prp.COD_PRESENTACION_PRIMARIA = fmdep.COD_PRESENTACION_PRIMARIA "
                        + " and prp.COD_TIPO_PROGRAMA_PROD = pprd.COD_TIPO_PROGRAMA_PROD   "
                        + " inner join ENVASES_PRIMARIOS ep on ep.cod_envaseprim = prp.COD_ENVASEPRIM "
                        + " where fm.COD_FORMULA_MAESTRA in(" + programaProduccionSeleccionados.getFormulaMaestra().getCodFormulaMaestra() + ")"
                        + " and pprd.COD_PROGRAMA_PROD = '" + programaProduccion.getCodProgramaProduccion() + "' "
                        + " and pprd.COD_TIPO_PROGRAMA_PROD in (" + programaProduccionSeleccionados.getTiposProgramaProduccion().getCodTipoProgramaProd() + ") "
                        + " and pprd.COD_LOTE_PRODUCCION = '" + programaProduccion.getCodLoteProduccion() + "' "
                        + " and pprd.COD_COMPPROD in (" + programaProduccionSeleccionados.getFormulaMaestra().getComponentesProd().getCodCompprod() + ")"
                        + " group by mt.COD_MATERIAL,mt.NOMBRE_MATERIAL,um.COD_UNIDAD_MEDIDA,um.NOMBRE_UNIDAD_MEDIDA,um.abreviatura ";

                System.out.println("consulta " + consulta);
                ResultSet rs = st.executeQuery(consulta);

                while (rs.next()) {
                    FormulaMaestraDetalleEP formulaMaestraDetalleEP = new FormulaMaestraDetalleEP();
                    formulaMaestraDetalleEP.setChecked(true);
                    formulaMaestraDetalleEP.getMateriales().setCodMaterial(rs.getString("COD_MATERIAL"));
                    formulaMaestraDetalleEP.getMateriales().setNombreMaterial(rs.getString("NOMBRE_MATERIAL"));
                    formulaMaestraDetalleEP.setCantidad(Float.valueOf(String.valueOf((rs.getDouble("CANTIDAD") * prorateo) > 0 ? rs.getDouble("CANTIDAD") * prorateo : rs.getDouble("CANTIDAD"))));
                    formulaMaestraDetalleEP.getUnidadesMedida().setCodUnidadMedida(rs.getInt("COD_UNIDAD_MEDIDA"));
                    formulaMaestraDetalleEP.getUnidadesMedida().setNombreUnidadMedida(rs.getString("NOMBRE_UNIDAD_MEDIDA"));
                    formulaMaestraDetalleEP.getUnidadesMedida().setAbreviatura(rs.getString("ABREVIATURA"));
                    //codEmpaque = rs.getString("cod_envaseprim");
                    //nombreEmpaque = rs.getString("nombre_envaseprim");
                    empaquePrimarioList.add(formulaMaestraDetalleEP);
                }
                //presentacionesPrimariasList.add(new SelectItem("0","-NINGUNO-"));
                presentacionesPrimariasList.clear();
                presentacionesPrimariasList.add(new SelectItem(codEmpaque, nombreEmpaque)); //rs.getString("cod_presentacion_primaria"),rs.getString("nombre_envaseprim") + " x " + rs.getInt("CANTIDAD")
                rs.close();
                //while(rs.next()){

                //}
            }
            codTipoMaterial = this.conMaterial(3, configuracionSalidaMaterialProduccionPersonaList, configuracionSalidaAlmacenProduccion); //con el tipo de material 3 material de empaque secundario
            empaqueSecundarioList.clear();
            if (codTipoMaterial == 1) {
                //cargar presentacionSecundaria
                consulta = " select pp.cod_presentacion,pp.NOMBRE_PRODUCTO_PRESENTACION, "
                        + " pp.cantidad_presentacion from formula_maestra fm  "
                        + " inner join COMPONENTES_PROD cp on cp.COD_COMPPROD = fm.COD_COMPPROD "
                        + " inner join COMPONENTES_PRESPROD c on c.COD_COMPPROD = cp.COD_COMPPROD "
                        + " inner join PRESENTACIONES_PRODUCTO pp on pp.cod_presentacion = c.COD_PRESENTACION "
                        + " inner join ENVASES_SECUNDARIOS es on es.COD_ENVASESEC = pp.COD_ENVASESEC "
                        + " where fm.COD_FORMULA_MAESTRA = '" + programaProduccion.getFormulaMaestra().getCodFormulaMaestra() + "' "
                        + " and pp.cod_presentacion in (select pprd.cod_presentacion_producto from programa_produccion_detalle pprd "
                        + " where pprd.cod_programa_prod = '" + programaProduccion.getCodProgramaProduccion() + "' "
                        + " and pprd.cod_tipo_programa_prod = '" + programaProduccion.getTiposProgramaProduccion().getCodTipoProgramaProd() + "' "
                        + " and pprd.cod_compprod = '" + programaProduccion.getFormulaMaestra().getComponentesProd().getCodCompprod() + "' "
                        + " and pprd.cod_lote_produccion = '" + programaProduccion.getCodLoteProduccion() + "' ) ";
                consulta = " select mt.COD_MATERIAL,mt.NOMBRE_MATERIAL,um.COD_UNIDAD_MEDIDA,um.NOMBRE_UNIDAD_MEDIDA,pprd.CANTIDAD,prp.cod_presentacion,prp.NOMBRE_PRODUCTO_PRESENTACION "
                        + " from PROGRAMA_PRODUCCION_DETALLE pprd "
                        + " inner join FORMULA_MAESTRA fm on fm.COD_COMPPROD = pprd.COD_COMPPROD "
                        + " inner join materiales mt on mt.COD_MATERIAL = pprd.COD_MATERIAL  "
                        + " inner join UNIDADES_MEDIDA um on um.COD_UNIDAD_MEDIDA = pprd.COD_UNIDAD_MEDIDA "
                        + " inner join FORMULA_MAESTRA_DETALLE_ES fmdes on fmdes.COD_FORMULA_MAESTRA = fm.COD_FORMULA_MAESTRA "
                        + " and fmdes.COD_MATERIAL = pprd.COD_MATERIAL "
                        + " inner join PRESENTACIONES_PRODUCTO prp on prp.cod_presentacion = fmdes.COD_PRESENTACION_PRODUCTO "
                        + " and prp.COD_TIPO_PROGRAMA_PROD = pprd.COD_TIPO_PROGRAMA_PROD "
                        + " where fm.COD_FORMULA_MAESTRA = '" + programaProduccion.getFormulaMaestra().getCodFormulaMaestra() + "' "
                        + " and pprd.COD_PROGRAMA_PROD = '" + programaProduccion.getCodProgramaProduccion() + "' "
                        + " and pprd.COD_TIPO_PROGRAMA_PROD = '" + programaProduccion.getTiposProgramaProduccion().getCodTipoProgramaProd() + "' "
                        + " and pprd.COD_LOTE_PRODUCCION = '" + programaProduccion.getCodLoteProduccion() + "' "
                        + " and pprd.COD_COMPPROD = '" + programaProduccion.getFormulaMaestra().getComponentesProd().getCodCompprod() + "' ";
                consulta = "select cod_material,nombre_material,cod_unidad_medida,nombre_unidad_medida,sum(cantidad) cantidad from( select distinct mt.COD_MATERIAL, mt.NOMBRE_MATERIAL,   um.COD_UNIDAD_MEDIDA,   um.NOMBRE_UNIDAD_MEDIDA,    fmdes.CANTIDAD CANTIDAD,"
                        + "  prp.cod_presentacion,       prp.NOMBRE_PRODUCTO_PRESENTACION,       um.abreviatura"
                        + " from PROGRAMA_PRODUCCION_INGRESOS_ACOND pprd     inner join FORMULA_MAESTRA fm on fm.COD_COMPPROD = pprd.COD_COMPPROD     "
                        + " inner join FORMULA_MAESTRA_DETALLE_ES fmdes on fmdes.COD_FORMULA_MAESTRA =     fm.COD_FORMULA_MAESTRA"
                        + " inner join materiales mt on mt.COD_MATERIAL = fmdes.COD_MATERIAL     inner join UNIDADES_MEDIDA um on um.COD_UNIDAD_MEDIDA =  fmdes.COD_UNIDAD_MEDIDA"
                        + " inner join COMPONENTES_PRESPROD cprp on cprp.COD_COMPPROD = fm.COD_COMPPROD  and cprp.COD_TIPO_PROGRAMA_PROD = pprd.COD_TIPO_PROGRAMA_PROD"
                        + " inner join PRESENTACIONES_PRODUCTO prp on prp.cod_presentacion =    fmdes.COD_PRESENTACION_PRODUCTO and prp.cod_presentacion =  cprp.COD_PRESENTACION "
                        + " where fm.COD_FORMULA_MAESTRA in (" + programaProduccionSeleccionados.getFormulaMaestra().getCodFormulaMaestra() + ") "
                        + " and pprd.COD_PROGRAMA_PROD = '" + programaProduccion.getCodProgramaProduccion() + "' "
                        + " and pprd.COD_TIPO_PROGRAMA_PROD in(" + programaProduccionSeleccionados.getTiposProgramaProduccion().getCodTipoProgramaProd() + ") "
                        + " and pprd.COD_LOTE_PRODUCCION = '" + programaProduccion.getCodLoteProduccion() + "' "
                        + " and  pprd.COD_COMPPROD in (" + programaProduccionSeleccionados.getFormulaMaestra().getComponentesProd().getCodCompprod() + ")"
                        + ") as tabla group by cod_material,nombre_material,cod_unidad_medida,nombre_unidad_medida";

                ResultSet rsPS = st.executeQuery(consulta);
                while (rsPS.next()) {
                    FormulaMaestraDetalleES formulaMaestraDetalleES = new FormulaMaestraDetalleES();
                    formulaMaestraDetalleES.setChecked(true);
                    formulaMaestraDetalleES.getMateriales().setCodMaterial(rsPS.getString("COD_MATERIAL"));
                    formulaMaestraDetalleES.getMateriales().setNombreMaterial(rsPS.getString("NOMBRE_MATERIAL"));
                    formulaMaestraDetalleES.setCantidad(Float.valueOf(String.valueOf(this.redondear((rsPS.getDouble("CANTIDAD") * prorateo) > 0 ? rsPS.getDouble("CANTIDAD") * prorateo : rsPS.getDouble("CANTIDAD"), 0))));
                    formulaMaestraDetalleES.getUnidadesMedida().setCodUnidadMedida(rsPS.getInt("COD_UNIDAD_MEDIDA"));
                    formulaMaestraDetalleES.getUnidadesMedida().setNombreUnidadMedida(rsPS.getString("NOMBRE_UNIDAD_MEDIDA"));
                    formulaMaestraDetalleES.getUnidadesMedida().setAbreviatura(rsPS.getString("ABREVIATURA"));
                    codEmpaque = rsPS.getString("cod_presentacion");
                    nombreEmpaque = rsPS.getString("NOMBRE_PRODUCTO_PRESENTACION");
                    empaqueSecundarioList.add(formulaMaestraDetalleES);
                }

                System.out.println("consulta " + consulta);
                presentacionesSecundariasList.clear();
                //presentacionesSecundariasList.add(new SelectItem("0","-NINGUNO-"));
                presentacionesSecundariasList.add(new SelectItem(codEmpaque, nombreEmpaque)); //rsPS.getString("cod_presentacion"),rsPS.getString("NOMBRE_PRODUCTO_PRESENTACION")
                //while(rsPS.next()){

                //}
                rsPS.close();
            }

            //empaquePrimarioList.clear();
            //empaqueSecundarioList.clear();
            st.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cargarSalidaAlmacenProduccionPorTipoProgramaProduccion2() {
        try {
            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            //iterar los productos seleccionados
            Iterator i = programaProduccionSeleccionadoList.iterator();
            materiaPrimaList.clear();
            empaquePrimarioList.clear();
            empaqueSecundarioList.clear();
            int count_reg = 0;
            alertaSalidasLote = "";
            ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            String nroSalidas = "";

            String sql_reg = "select s.NRO_SALIDA_ALMACEN,s.cod_salida_almacen from SALIDAS_ALMACEN s where s.COD_LOTE_PRODUCCION =  '" + programaProduccion.getCodLoteProduccion() + "' "
                    + //String sql_reg="select count (s.NRO_SALIDA_ALMACEN) from SALIDAS_ALMACEN s where s.COD_LOTE_PRODUCCION =  '"+programaProduccion.getCodLoteProduccion()+"' " +
                    " and s.COD_ESTADO_SALIDA_ALMACEN <> 2"
                    + " and s.COD_ESTADO_SALIDA_ALMACEN <> 2 and s.cod_gestion >=10"
                    + " and s.COD_ALMACEN='" + usuario.getAlmacenesGlobal().getCodAlmacen() + "'";

            System.out.println("sql_reg : " + sql_reg);
            ResultSet rsReg = st.executeQuery(sql_reg);
            while (rsReg.next()) {
                //count_reg=rsReg.getInt(1);
                //nroSalidas+=rsReg.getString(1)+",";
                nroSalidas += "<a style='cursor:hand;text-decoration:underline;color:blue;font-size:15px' target=\"_blank\" href=\"navegadorSalidasAlmacenReporte.jsf?codigo=" + rsReg.getString(2) + "\" >" + rsReg.getString(1) + "</a>,";
                count_reg++;
            }

            if (count_reg > 0) {
                alertaSalidasLote = "  YA SE REALIZARON SALIDAS PARA ESTE NRO DE LOTE !!!!  LAS SALIDAS SON LAS SIGUIENTES : " + nroSalidas + "";
            }
            
            while (i.hasNext()) {
                ProgramaProduccion programaProduccion = (ProgramaProduccion) i.next();
                System.out.println("pp" + programaProduccion.getTiposProduccion().getCodTipoProduccion());
                System.out.println("pp" + programaProduccion.getFormulaMaestra().getComponentesProd().getCodCompprod());
                String consulta = "";
                double prorateo = programaProduccion.getCantIngresoAcond() / Double.parseDouble(programaProduccion.getFormulaMaestra().getCantidadLote().replace(",", ""));
                double prorateoMP = Double.parseDouble(programaProduccion.getCantidadLote().replace(",", "")) / Double.parseDouble(programaProduccion.getFormulaMaestra().getCantidadLote().replace(",", "")); //prorateo para acond
                if ((permisoSalidaMateriaPrima && salidaMateriaPrima)
                        || (permisoSolicitudMateriaPrima && solicitudSalida)) {
                    //materia prima
                    if (programaProduccion.getTiposProduccion().getCodTipoProduccion() == 2) {
                        consulta = "select m.COD_MATERIAL,m.NOMBRE_MATERIAL,um.COD_UNIDAD_MEDIDA,um.NOMBRE_UNIDAD_MEDIDA,"
                                + " fmdp.CANTIDAD * '" + prorateoMP + "' cantidad,um.abreviatura,3 as NRO_DECIMALES_ALMACEN"
                                + " from FORMULA_MAESTRA fm inner join FORMULA_MAESTRA_DETALLE_MP fmdp on"
                                + " fm.COD_FORMULA_MAESTRA=fmdp.COD_FORMULA_MAESTRA"
                                + " inner join materiales m on m.COD_MATERIAL=fmdp.COD_MATERIAL"
                                + "  inner join UNIDADES_MEDIDA um on um.COD_UNIDAD_MEDIDA=fmdp.COD_UNIDAD_MEDIDA"
                                + " where fm.COD_FORMULA_MAESTRA='" + programaProduccion.getFormulaMaestra().getCodFormulaMaestra() + "'"
                                + " and m.cod_material not in (select cod_material from MATERIALES_SIN_SALIDAPRODUCCION)"
                                + " order by m.NOMBRE_MATERIAL";
                    } else {
                        consulta = "exec [USP_GET_LISTA_MATERIA_PRIMA_LOTE] '" + programaProduccion.getCodLoteProduccion()
                                    + "', " + programaProduccion.getCodProgramaProduccion()
                                    + ", " + programaProduccion.getTiposProgramaProduccion().getCodTipoProgramaProd()
                                    + ", " + programaProduccion.getFormulaMaestra().getComponentesProd().getCodCompprod();
                        
                    }
                    LOGGER.debug("consulta cargar materia prima: "+consulta);
                    ResultSet rsMP = st.executeQuery(consulta);
                    while (rsMP.next()) {
                        FormulaMaestraDetalleMP formulaMaestraDetalleMP = new FormulaMaestraDetalleMP();
                        formulaMaestraDetalleMP.setChecked(true);
                        formulaMaestraDetalleMP.getMateriales().setCodMaterial(rsMP.getString("COD_MATERIAL"));
                        formulaMaestraDetalleMP.getMateriales().setNombreMaterial(rsMP.getString("NOMBRE_MATERIAL"));
                        formulaMaestraDetalleMP.getUnidadesMedida().setCodUnidadMedida(rsMP.getInt("COD_UNIDAD_MEDIDA"));
                        formulaMaestraDetalleMP.getUnidadesMedida().setNombreUnidadMedida(rsMP.getString("NOMBRE_UNIDAD_MEDIDA"));
                        formulaMaestraDetalleMP.getUnidadesMedida().setAbreviatura(rsMP.getString("ABREVIATURA"));
                        formulaMaestraDetalleMP.setCantidad(rsMP.getDouble("CANTIDAD"));
                        formulaMaestraDetalleMP.setNroDecimalesAlmacen(rsMP.getInt("NRO_DECIMALES_ALMACEN"));
                        materiaPrimaList.add(formulaMaestraDetalleMP);
                    }
                    rsMP.close();
                }
                String codEmpaque = "";
                String nombreEmpaque = "";
                if ((permisoSalidaEmpaquePrimario && salidaEmpaquePrimarioSecundario)
                        || (permisoSolicitudEmpaquePrimario && solicitudSalida)) {
                    if (programaProduccion.getTiposProduccion().getCodTipoProduccion() == 2) {
                        consulta = "select m.COD_MATERIAL,m.NOMBRE_MATERIAL,um.COD_UNIDAD_MEDIDA,um.NOMBRE_UNIDAD_MEDIDA,"
                                + " fmdep.CANTIDAD CANTIDAD,ep.cod_envaseprim,ep.nombre_envaseprim,um.abreviatura,m.cod_grupo"
                                + " from PROGRAMA_PRODUCCION pp inner join FORMULA_MAESTRA f"
                                + " on pp.COD_FORMULA_MAESTRA=f.COD_FORMULA_MAESTRA and pp.COD_COMPPROD=f.COD_COMPPROD"
                                + " inner join FORMULA_MAESTRA_DETALLE_EP fmdep"
                                + " on f.COD_FORMULA_MAESTRA=fmdep.COD_FORMULA_MAESTRA"
                                + " inner join materiales m on m.COD_MATERIAL=fmdep.COD_MATERIAL"
                                + " inner join UNIDADES_MEDIDA um on um.COD_UNIDAD_MEDIDA=fmdep.COD_UNIDAD_MEDIDA"
                                + " inner join PRESENTACIONES_PRIMARIAS ppr on ppr.COD_PRESENTACION_PRIMARIA=fmdep.COD_PRESENTACION_PRIMARIA"
                                + " and ppr.COD_TIPO_PROGRAMA_PROD=pp.COD_TIPO_PROGRAMA_PROD"
                                + " inner join ENVASES_PRIMARIOS ep on ep.cod_envaseprim=ppr.COD_ENVASEPRIM"
                                + " where pp.COD_PROGRAMA_PROD='" + programaProduccion.getCodProgramaProduccion() + "'"
                                + " and pp.COD_TIPO_PROGRAMA_PROD='" + programaProduccion.getTiposProgramaProduccion().getCodTipoProgramaProd() + "'"
                                + " and pp.COD_LOTE_PRODUCCION='" + programaProduccion.getCodLoteProduccion() + "'"
                                + " and pp.COD_COMPPROD='" + programaProduccion.getFormulaMaestra().getComponentesProd().getCodCompprod() + "'"
                                + " and m.cod_material not in (select cod_material from MATERIALES_SIN_SALIDAPRODUCCION)"
                                + " order by m.NOMBRE_MATERIAL";
                    } else {
                    consulta = "exec [USP_GET_LISTA_EMPAQUE_PRIMARIO_LOTE] '" + programaProduccion.getCodLoteProduccion()
                                + "', " + programaProduccion.getCodProgramaProduccion()
                                + ", " + programaProduccion.getTiposProgramaProduccion().getCodTipoProgramaProd()
                                + ", " + programaProduccion.getFormulaMaestra().getComponentesProd().getCodCompprod();
                    }
                    LOGGER.debug("consulta empaque primario: "+consulta);
                    ResultSet rs = st.executeQuery(consulta);

                    while (rs.next()) {
                        FormulaMaestraDetalleEP formulaMaestraDetalleEP = new FormulaMaestraDetalleEP();
                        formulaMaestraDetalleEP.setChecked(true);
                        formulaMaestraDetalleEP.getMateriales().setCodMaterial(rs.getString("COD_MATERIAL"));
                        formulaMaestraDetalleEP.getMateriales().setNombreMaterial(rs.getString("NOMBRE_MATERIAL"));
                        //Double cantidad = rs.getDouble("cant_modif")>0?rs.getDouble("cant_modif"):rs.getDouble("cantidad");
                        //formulaMaestraDetalleEP.setCantidad(Float.valueOf(String.valueOf(this.redondear((cantidad*prorateoMP)>0?cantidad*prorateoMP:cantidad,2))));
                        formulaMaestraDetalleEP.setCantidad(rs.getFloat("CANTIDAD"));
                        formulaMaestraDetalleEP.getUnidadesMedida().setCodUnidadMedida(rs.getInt("COD_UNIDAD_MEDIDA"));
                        formulaMaestraDetalleEP.getUnidadesMedida().setNombreUnidadMedida(rs.getString("NOMBRE_UNIDAD_MEDIDA"));
                        formulaMaestraDetalleEP.getUnidadesMedida().setAbreviatura(rs.getString("ABREVIATURA"));
                        formulaMaestraDetalleEP.getMateriales().getGrupos().setCodGrupo(rs.getInt("cod_grupo"));
                        codEmpaque = rs.getString("cod_envaseprim");
                        nombreEmpaque = rs.getString("nombre_envaseprim");
                        empaquePrimarioList.add(formulaMaestraDetalleEP);
                    }
                    //presentacionesPrimariasList.add(new SelectItem("0","-NINGUNO-"));
                    presentacionesPrimariasList.clear();
                    presentacionesPrimariasList.add(new SelectItem(codEmpaque, nombreEmpaque)); //rs.getString("cod_presentacion_primaria"),rs.getString("nombre_envaseprim") + " x " + rs.getInt("CANTIDAD")
                    rs.close();
                    //while(rs.next()){

                    //}
                }
                if ((permisoSalidaEmpaqueSecundario && salidaEmpaquePrimarioSecundario)
                        || (permisoSolicitudEmpaqueSecundario && solicitudSalida)) {
                    consulta = "exec [USP_GET_LISTA_EMPAQUE_SECUNDARIO_LOTE] '" + programaProduccion.getCodLoteProduccion()
                            + "', " + programaProduccion.getCodProgramaProduccion()
                            + ", " + programaProduccion.getTiposProgramaProduccion().getCodTipoProgramaProd()
                            + ", " + programaProduccion.getFormulaMaestra().getComponentesProd().getCodCompprod();
                    System.out.println("consulta " + consulta);

                    ResultSet rsPS = st.executeQuery(consulta);
                    while (rsPS.next()) {
                        FormulaMaestraDetalleES formulaMaestraDetalleES = new FormulaMaestraDetalleES();
                        formulaMaestraDetalleES.setChecked(true);
                        formulaMaestraDetalleES.getMateriales().setCodMaterial(rsPS.getString("COD_MATERIAL"));
                        formulaMaestraDetalleES.getMateriales().setNombreMaterial(rsPS.getString("NOMBRE_MATERIAL"));
                        //Double cantidad = rsPS.getDouble("cant_modif")>0?rsPS.getDouble("cant_modif"):rsPS.getDouble("cantidad");
                        //formulaMaestraDetalleES.setCantidad(Float.valueOf(String.valueOf(this.redondear((cantidad*prorateo)>0?cantidad*prorateo:cantidad,0))));
                        formulaMaestraDetalleES.setCantidad(rsPS.getFloat("CANTIDAD"));
                        //formulaMaestraDetalleES.setCantidad(Float.valueOf(String.valueOf(this.redondear((rsPS.getDouble("CANTIDAD")*prorateoMP)>0?rsPS.getDouble("CANTIDAD")*prorateoMP:rsPS.getDouble("CANTIDAD"),0))));

                        formulaMaestraDetalleES.getUnidadesMedida().setCodUnidadMedida(rsPS.getInt("COD_UNIDAD_MEDIDA"));
                        formulaMaestraDetalleES.getUnidadesMedida().setNombreUnidadMedida(rsPS.getString("NOMBRE_UNIDAD_MEDIDA"));
                        formulaMaestraDetalleES.getUnidadesMedida().setAbreviatura(rsPS.getString("ABREVIATURA"));
                        codEmpaque = rsPS.getString("cod_presentacion");
                        nombreEmpaque = rsPS.getString("NOMBRE_PRODUCTO_PRESENTACION");
                        empaqueSecundarioList.add(formulaMaestraDetalleES);
                    }

                    System.out.println("consulta " + consulta);
                    //presentacionesSecundariasList.clear();
                    //presentacionesSecundariasList.add(new SelectItem("0","-NINGUNO-"));
                    presentacionesSecundariasList.add(new SelectItem(codEmpaque, nombreEmpaque)); //rsPS.getString("cod_presentacion"),rsPS.getString("NOMBRE_PRODUCTO_PRESENTACION")
                    //while(rsPS.next()){
                    programaProduccion = (ProgramaProduccion)programaProduccionSeleccionadoList.get(0);
                    //}
                    rsPS.close();
                }
            }

            //empaquePrimarioList.clear();
            //empaqueSecundarioList.clear();
            materiaPrimaList = this.agrupaMaterialesMP1(materiaPrimaList);
            empaquePrimarioList = this.agrupaMaterialesEP(empaquePrimarioList);

            st.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getCargarDetalleSolicitudDevolucionDesviacion() {
        try {
            configuracionSalidaAlmacenProduccion.getTiposSalidaAlmacenProduccion().setCodTipoSalidaAlmacenProduccion(1);
            salidasAlmacenConDesviacion = 1;
            configuracionSalidaMaterialProduccionPersonaList = this.cargarConfiguracionSalidaAlmacenProduccionPersona();

            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            //iterar los productos seleccionados
            Iterator i = programaProduccionSeleccionadoList.iterator();
            materiaPrimaList.clear();
            empaquePrimarioList.clear();
            empaqueSecundarioList.clear();
            int count_reg = 0;
            alertaSalidasLote = "";
            ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            String nroSalidas = "";

            String sql_reg = "select s.NRO_SALIDA_ALMACEN,s.cod_salida_almacen from SALIDAS_ALMACEN s where s.COD_LOTE_PRODUCCION =  '" + programaProduccion.getCodLoteProduccion() + "' "
                    + //String sql_reg="select count (s.NRO_SALIDA_ALMACEN) from SALIDAS_ALMACEN s where s.COD_LOTE_PRODUCCION =  '"+programaProduccion.getCodLoteProduccion()+"' " +
                    " and s.COD_ESTADO_SALIDA_ALMACEN <> 2"
                    + " and s.COD_ESTADO_SALIDA_ALMACEN <> 2 and s.cod_gestion>=10"
                    + " and s.COD_ALMACEN='" + usuario.getAlmacenesGlobal().getCodAlmacen() + "'";

            System.out.println("sql_reg : " + sql_reg);
            ResultSet rsReg = st.executeQuery(sql_reg);
            while (rsReg.next()) {
                //count_reg=rsReg.getInt(1);
                //nroSalidas+=rsReg.getString(1)+",";
                nroSalidas += "<a style='cursor:hand;text-decoration:underline;color:blue;font-size:15px' target=\"_blank\" href=\"navegadorSalidasAlmacenReporte.jsf?codigo=" + rsReg.getString(2) + "\" >" + rsReg.getString(1) + "</a>,";
                count_reg++;
            }

            if (count_reg > 0) {
                alertaSalidasLote = "  YA SE REALIZARON SALIDAS PARA ESTE NRO DE LOTE !!!!  LAS SALIDAS SON LAS SIGUIENTES : " + nroSalidas + "";
            }
            while (i.hasNext()) {

                ProgramaProduccion programaProduccion = (ProgramaProduccion) i.next();
                String consulta = "";
                int codTipoMaterial = this.conMaterial(1, configuracionSalidaMaterialProduccionPersonaList, configuracionSalidaAlmacenProduccion); //con el tipo de material 1 materia prima

                double prorateo = programaProduccion.getCantIngresoAcond() / Double.parseDouble(programaProduccion.getFormulaMaestra().getCantidadLote().replace(",", ""));
                double prorateoMP = Double.parseDouble(programaProduccion.getCantidadLote().replace(",", "")) / Double.parseDouble(programaProduccion.getFormulaMaestra().getCantidadLote().replace(",", "")); //prorateo para acond
                System.out.println("prorateo MP :" + prorateoMP);
                System.out.println("prorateo acond :" + prorateo);
                if (codTipoMaterial == 1) {
                    consulta = " select m.cod_material,m.NOMBRE_MATERIAL, u.NOMBRE_UNIDAD_MEDIDA, P.CANTIDAD,u.COD_UNIDAD_MEDIDA,u.abreviatura"
                            + " from PROGRAMA_PRODUCCION_DETALLE p"
                            + " inner join materiales m on m.COD_MATERIAL = p.COD_MATERIAL"
                            + " inner join grupos g on g.COD_GRUPO = m.COD_GRUPO"
                            + " inner join capitulos c on c.COD_CAPITULO = g.COD_CAPITULO and c.COD_CAPITULO in(2)"
                            + " inner join UNIDADES_MEDIDA u on u.COD_UNIDAD_MEDIDA = p.COD_UNIDAD_MEDIDA"
                            + " where p.COD_LOTE_PRODUCCION = '" + programaProduccion.getCodLoteProduccion() + "'"
                            + " and p.COD_TIPO_PROGRAMA_PROD = '" + programaProduccion.getTiposProgramaProduccion().getCodTipoProgramaProd() + "'"
                            + " and p.COD_ESTADO_REGISTRO = 1 and p.cod_tipo_programa_prod = '" + programaProduccion.getTiposProgramaProduccion().getCodTipoProgramaProd() + "'"
                            + //" and p.cod_formula_maestra = '"+programaProduccion.getFormulaMaestra().getCodFormulaMaestra()+"'" +
                            " and p.cod_compprod = '" + programaProduccion.getFormulaMaestra().getComponentesProd().getCodCompprod() + "'";

                    System.out.println("consulta mp" + consulta);

                    ResultSet rsPS = st.executeQuery(consulta);
                    while (rsPS.next()) {
                        FormulaMaestraDetalleMP formulaMaestraDetalleMP = new FormulaMaestraDetalleMP();
                        formulaMaestraDetalleMP.setChecked(true);
                        formulaMaestraDetalleMP.getMateriales().setCodMaterial(rsPS.getString("COD_MATERIAL"));
                        formulaMaestraDetalleMP.getMateriales().setNombreMaterial(rsPS.getString("NOMBRE_MATERIAL"));
                        formulaMaestraDetalleMP.setCantidad(this.redondear((rsPS.getDouble("CANTIDAD")) > 0 ? rsPS.getDouble("CANTIDAD") : rsPS.getDouble("CANTIDAD"), 0));
                        formulaMaestraDetalleMP.getUnidadesMedida().setCodUnidadMedida(rsPS.getInt("COD_UNIDAD_MEDIDA"));
                        formulaMaestraDetalleMP.getUnidadesMedida().setNombreUnidadMedida(rsPS.getString("NOMBRE_UNIDAD_MEDIDA"));
                        formulaMaestraDetalleMP.getUnidadesMedida().setAbreviatura(rsPS.getString("ABREVIATURA"));
                        materiaPrimaList.add(formulaMaestraDetalleMP);
                    }
                }
                codTipoMaterial = this.conMaterial(2, configuracionSalidaMaterialProduccionPersonaList, configuracionSalidaAlmacenProduccion); //con el tipo de material 1 materia prima
                if (codTipoMaterial == 1) {
                    consulta = " select m.cod_material,m.NOMBRE_MATERIAL, u.NOMBRE_UNIDAD_MEDIDA, P.CANTIDAD,u.COD_UNIDAD_MEDIDA,u.abreviatura"
                            + " from PROGRAMA_PRODUCCION_DETALLE p"
                            + " inner join materiales m on m.COD_MATERIAL = p.COD_MATERIAL"
                            + " inner join grupos g on g.COD_GRUPO = m.COD_GRUPO"
                            + " inner join capitulos c on c.COD_CAPITULO = g.COD_CAPITULO and c.COD_CAPITULO in(3)"
                            + " inner join UNIDADES_MEDIDA u on u.COD_UNIDAD_MEDIDA = p.COD_UNIDAD_MEDIDA"
                            + " where p.COD_LOTE_PRODUCCION = '" + programaProduccion.getCodLoteProduccion() + "'"
                            + " and p.COD_TIPO_PROGRAMA_PROD = '" + programaProduccion.getTiposProgramaProduccion().getCodTipoProgramaProd() + "'"
                            + " and p.COD_ESTADO_REGISTRO = 1 and p.cod_tipo_programa_prod = '" + programaProduccion.getTiposProgramaProduccion().getCodTipoProgramaProd() + "'"
                            + //" and p.cod_formula_maestra = '"+programaProduccion.getFormulaMaestra().getCodFormulaMaestra()+"'" +
                            " and p.cod_compprod = '" + programaProduccion.getFormulaMaestra().getComponentesProd().getCodCompprod() + "'";

                    System.out.println("consulta ep" + consulta);

                    ResultSet rsPS = st.executeQuery(consulta);
                    while (rsPS.next()) {
                        FormulaMaestraDetalleEP formulaMaestraDetalleEp = new FormulaMaestraDetalleEP();
                        formulaMaestraDetalleEp.setChecked(true);
                        formulaMaestraDetalleEp.getMateriales().setCodMaterial(rsPS.getString("COD_MATERIAL"));
                        formulaMaestraDetalleEp.getMateriales().setNombreMaterial(rsPS.getString("NOMBRE_MATERIAL"));
                        formulaMaestraDetalleEp.setCantidad(Float.valueOf(String.valueOf(this.redondear((rsPS.getDouble("CANTIDAD")) > 0 ? rsPS.getDouble("CANTIDAD") : rsPS.getDouble("CANTIDAD"), 0))));
                        formulaMaestraDetalleEp.getUnidadesMedida().setCodUnidadMedida(rsPS.getInt("COD_UNIDAD_MEDIDA"));
                        formulaMaestraDetalleEp.getUnidadesMedida().setNombreUnidadMedida(rsPS.getString("NOMBRE_UNIDAD_MEDIDA"));
                        formulaMaestraDetalleEp.getUnidadesMedida().setAbreviatura(rsPS.getString("ABREVIATURA"));
                        empaquePrimarioList.add(formulaMaestraDetalleEp);
                    }
                }
//                    if(codTipoMaterial==1){
//                        //materia prima
//                            consulta = " select m.COD_MATERIAL,m.NOMBRE_MATERIAL,u.COD_UNIDAD_MEDIDA,u.NOMBRE_UNIDAD_MEDIDA,fmdmp.CANTIDAD*'"+prorateoMP+"' cantidad,u.abreviatura" +
//                                    " from FORMULA_MAESTRA fm " +
//                                    " inner join FORMULA_MAESTRA_DETALLE_MP fmdmp on fmdmp.COD_FORMULA_MAESTRA = fm.COD_FORMULA_MAESTRA " +
//                                    " inner join materiales m on fmdmp.COD_MATERIAL = m.COD_MATERIAL " +
//                                    " inner join UNIDADES_MEDIDA u on u.COD_UNIDAD_MEDIDA = fmdmp.COD_UNIDAD_MEDIDA " +
//                                    " where fm.COD_FORMULA_MAESTRA = '"+programaProduccion.getFormulaMaestra().getCodFormulaMaestra()+"' ";
//                            /*
//                             * ,modificacion.cantidad*'"+prorateoMP+"' cant_modif
//                             " outer apply(select ppr.CANTIDAD  from PROGRAMA_PRODUCCION_DETALLE ppr where ppr.COD_COMPPROD = '"+programaProduccion.getFormulaMaestra().getComponentesProd().getCodCompprod()+"'" +
//                                    " and ppr.COD_LOTE_PRODUCCION = '"+programaProduccion.getCodLoteProduccion()+"' and ppr.COD_PROGRAMA_PROD = '"+programaProduccion.getCodProgramaProduccion()+"' and ppr.COD_TIPO_PROGRAMA_PROD = '"+programaProduccion.getTiposProgramaProduccion().getCodTipoProgramaProd()+"'" +
//                                    " and ppr.COD_MATERIAL = fmdmp.COD_MATERIAL) modificacion " +
//                             */
//
//                            System.out.println("consulta " + consulta);
//
//                            ResultSet rsMP = st.executeQuery(consulta);
//                            while(rsMP.next()){
//                                FormulaMaestraDetalleMP formulaMaestraDetalleMP = new FormulaMaestraDetalleMP();
//                                formulaMaestraDetalleMP.setChecked(true);
//                                formulaMaestraDetalleMP.getMateriales().setCodMaterial(rsMP.getString("COD_MATERIAL"));
//                                formulaMaestraDetalleMP.getMateriales().setNombreMaterial(rsMP.getString("NOMBRE_MATERIAL"));
//                                formulaMaestraDetalleMP.getUnidadesMedida().setCodUnidadMedida(rsMP.getInt("COD_UNIDAD_MEDIDA"));
//                                formulaMaestraDetalleMP.getUnidadesMedida().setNombreUnidadMedida(rsMP.getString("NOMBRE_UNIDAD_MEDIDA"));
//                                formulaMaestraDetalleMP.getUnidadesMedida().setAbreviatura(rsMP.getString("ABREVIATURA"));
//                                formulaMaestraDetalleMP.setCantidad(Float.valueOf(String.valueOf((rsMP.getDouble("CANTIDAD")*prorateo)>0?rsMP.getDouble("CANTIDAD")*prorateo:rsMP.getDouble("CANTIDAD"))));
//                                //formulaMaestraDetalleMP.setCantidad(Float.valueOf(String.valueOf((rsMP.getDouble("CANTIDAD")))));
//                                //Double cantidad = rsMP.getDouble("cant_modif")>0?rsMP.getDouble("cant_modif"):rsMP.getDouble("cantidad");
//                                //formulaMaestraDetalleMP.setCantidad(Float.valueOf(String.valueOf((this.redondear(cantidad,2)))));
//                                //formulaMaestraDetalleMP.setCantidad(Float.valueOf(String.valueOf((this.redondear(rsMP.getDouble("CANTIDAD"),2)))));
//
//                                System.out.println("el datosXXXXX" + formulaMaestraDetalleMP.getCantidad());
//                                materiaPrimaList.add(formulaMaestraDetalleMP);
//                            }
//                            rsMP.close();
//                    }
//                    String codEmpaque = "";
//                    String nombreEmpaque = "";
//                    codTipoMaterial = this.conMaterial(2,configuracionSalidaMaterialProduccionPersonaList,configuracionSalidaAlmacenProduccion); //con el tipo de material 2 material de empaque primario
//
//                    if(codTipoMaterial==1){
//                    // cargar presentacionPrimaria
//                    consulta = " select mt.COD_MATERIAL,mt.NOMBRE_MATERIAL,um.COD_UNIDAD_MEDIDA,um.NOMBRE_UNIDAD_MEDIDA,fmdep.CANTIDAD CANTIDAD,pprd.cantidad cant_modif,ep.cod_envaseprim,ep.nombre_envaseprim,um.abreviatura,mt.cod_grupo " +
//                            " from PROGRAMA_PRODUCCION_DETALLE pprd " +
//                            " inner join FORMULA_MAESTRA fm on fm.COD_COMPPROD = pprd.COD_COMPPROD " +
//                            " inner join materiales mt on mt.COD_MATERIAL = pprd.COD_MATERIAL " +
//                            " inner join UNIDADES_MEDIDA um on um.COD_UNIDAD_MEDIDA = pprd.COD_UNIDAD_MEDIDA " +
//                            " inner join FORMULA_MAESTRA_DETALLE_EP fmdep on fmdep.COD_FORMULA_MAESTRA = fm.COD_FORMULA_MAESTRA " +
//                            " and fmdep.COD_MATERIAL = pprd.COD_MATERIAL " +
//                            " inner join PRESENTACIONES_PRIMARIAS prp on prp.COD_PRESENTACION_PRIMARIA = fmdep.COD_PRESENTACION_PRIMARIA " +
//                            " and prp.COD_TIPO_PROGRAMA_PROD = pprd.COD_TIPO_PROGRAMA_PROD   " +
//                            " inner join ENVASES_PRIMARIOS ep on ep.cod_envaseprim = prp.COD_ENVASEPRIM" +
//                            " where fm.COD_FORMULA_MAESTRA = '"+programaProduccion.getFormulaMaestra().getCodFormulaMaestra()+"' " +
//                            " and pprd.COD_PROGRAMA_PROD = '"+programaProduccion.getCodProgramaProduccion()+"' " +
//                            " and pprd.COD_TIPO_PROGRAMA_PROD = '"+programaProduccion.getTiposProgramaProduccion().getCodTipoProgramaProd()+"' " +
//                            " and pprd.COD_LOTE_PRODUCCION = '"+programaProduccion.getCodLoteProduccion()+"' " +
//                            " and pprd.COD_COMPPROD = '"+programaProduccion.getFormulaMaestra().getComponentesProd().getCodCompprod()+"'  ";
//                    consulta = " select mt.COD_MATERIAL,mt.NOMBRE_MATERIAL,um.COD_UNIDAD_MEDIDA,um.NOMBRE_UNIDAD_MEDIDA,fmdep.CANTIDAD CANTIDAD,ep.cod_envaseprim,ep.nombre_envaseprim,um.abreviatura,mt.cod_grupo " +
//                            " from PROGRAMA_PRODUCCION ppr " +
//                            " inner join FORMULA_MAESTRA fm on fm.COD_COMPPROD = ppr.COD_COMPPROD and fm.cod_estado_registro = 1 " +
//                            " inner join FORMULA_MAESTRA_DETALLE_EP fmdep on fmdep.COD_FORMULA_MAESTRA = fm.COD_FORMULA_MAESTRA " +
//                            " inner join materiales mt on mt.COD_MATERIAL = fmdep.COD_MATERIAL " +
//                            " inner join UNIDADES_MEDIDA um on um.COD_UNIDAD_MEDIDA = fmdep.COD_UNIDAD_MEDIDA " +
//                            " inner join PRESENTACIONES_PRIMARIAS prp on prp.COD_PRESENTACION_PRIMARIA = fmdep.COD_PRESENTACION_PRIMARIA " +
//                            " and prp.COD_TIPO_PROGRAMA_PROD = ppr.COD_TIPO_PROGRAMA_PROD   " +
//                            " inner join ENVASES_PRIMARIOS ep on ep.cod_envaseprim = prp.COD_ENVASEPRIM" +
//                            " where fm.COD_FORMULA_MAESTRA = '"+programaProduccion.getFormulaMaestra().getCodFormulaMaestra()+"' " +
//                            " and ppr.COD_PROGRAMA_PROD = '"+programaProduccion.getCodProgramaProduccion()+"' " +
//                            " and ppr.COD_TIPO_PROGRAMA_PROD = '"+programaProduccion.getTiposProgramaProduccion().getCodTipoProgramaProd()+"' " +
//                            " and ppr.COD_LOTE_PRODUCCION = '"+programaProduccion.getCodLoteProduccion()+"' " +
//                            " and ppr.COD_COMPPROD = '"+programaProduccion.getFormulaMaestra().getComponentesProd().getCodCompprod()+"'  ";
//
//                    System.out.println("consulta "+ consulta);
//                    ResultSet rs=st.executeQuery(consulta);
//
//                    while(rs.next()){
//                        FormulaMaestraDetalleEP formulaMaestraDetalleEP = new FormulaMaestraDetalleEP();
//                        formulaMaestraDetalleEP.setChecked(true);
//                        formulaMaestraDetalleEP.getMateriales().setCodMaterial(rs.getString("COD_MATERIAL"));
//                        formulaMaestraDetalleEP.getMateriales().setNombreMaterial(rs.getString("NOMBRE_MATERIAL"));
//                        //Double cantidad = rs.getDouble("cant_modif")>0?rs.getDouble("cant_modif"):rs.getDouble("cantidad");
//                        //formulaMaestraDetalleEP.setCantidad(Float.valueOf(String.valueOf(this.redondear((cantidad*prorateoMP)>0?cantidad*prorateoMP:cantidad,2))));
//                        formulaMaestraDetalleEP.setCantidad(Float.valueOf(String.valueOf((rs.getDouble("CANTIDAD")*prorateo)>0?rs.getDouble("CANTIDAD")*prorateo:rs.getDouble("CANTIDAD"))));
//                        formulaMaestraDetalleEP.getUnidadesMedida().setCodUnidadMedida(rs.getInt("COD_UNIDAD_MEDIDA"));
//                        formulaMaestraDetalleEP.getUnidadesMedida().setNombreUnidadMedida(rs.getString("NOMBRE_UNIDAD_MEDIDA"));
//                        formulaMaestraDetalleEP.getUnidadesMedida().setAbreviatura(rs.getString("ABREVIATURA"));
//                        formulaMaestraDetalleEP.getMateriales().getGrupos().setCodGrupo(rs.getInt("cod_grupo"));
//                        codEmpaque = rs.getString("cod_envaseprim");
//                        nombreEmpaque = rs.getString("nombre_envaseprim");
//                        empaquePrimarioList.add(formulaMaestraDetalleEP);
//                    }
//                    //presentacionesPrimariasList.add(new SelectItem("0","-NINGUNO-"));
//                    presentacionesPrimariasList.clear();
//                    presentacionesPrimariasList.add(new SelectItem(codEmpaque,nombreEmpaque)); //rs.getString("cod_presentacion_primaria"),rs.getString("nombre_envaseprim") + " x " + rs.getInt("CANTIDAD")
//                    rs.close();
//                    //while(rs.next()){
//
//                    //}
//                    }
                codTipoMaterial = this.conMaterial(3, configuracionSalidaMaterialProduccionPersonaList, configuracionSalidaAlmacenProduccion); //con el tipo de material 3 material de empaque secundario

                if (codTipoMaterial == 1) {
                    //cargar presentacionSecundaria

                    consulta = " select distinct mt.COD_MATERIAL, mt.NOMBRE_MATERIAL,   um.COD_UNIDAD_MEDIDA,   um.NOMBRE_UNIDAD_MEDIDA,    fmdes.CANTIDAD CANTIDAD,"
                            + "  prp.cod_presentacion,       prp.NOMBRE_PRODUCTO_PRESENTACION,       um.abreviatura,modificacion.cantidad cant_modif"
                            + " from PROGRAMA_PRODUCCION pprd     inner join FORMULA_MAESTRA fm on fm.COD_COMPPROD = pprd.COD_COMPPROD     "
                            + " inner join FORMULA_MAESTRA_DETALLE_ES fmdes on fmdes.COD_FORMULA_MAESTRA =     fm.COD_FORMULA_MAESTRA"
                            + " inner join materiales mt on mt.COD_MATERIAL = fmdes.COD_MATERIAL     inner join UNIDADES_MEDIDA um on um.COD_UNIDAD_MEDIDA =  fmdes.COD_UNIDAD_MEDIDA"
                            + " inner join COMPONENTES_PRESPROD cprp on cprp.COD_COMPPROD = fm.COD_COMPPROD  and cprp.COD_TIPO_PROGRAMA_PROD = pprd.COD_TIPO_PROGRAMA_PROD and cprp.COD_ESTADO_REGISTRO = 1"
                            + " inner join PRESENTACIONES_PRODUCTO prp on prp.cod_presentacion =    fmdes.COD_PRESENTACION_PRODUCTO and prp.cod_presentacion =  cprp.COD_PRESENTACION "
                            + " outer apply(select ppr.CANTIDAD  from PROGRAMA_PRODUCCION_DETALLE ppr where ppr.COD_COMPPROD = '" + programaProduccion.getFormulaMaestra().getComponentesProd().getCodCompprod() + "'"
                            + " and ppr.COD_LOTE_PRODUCCION = '" + programaProduccion.getCodLoteProduccion() + "' and ppr.COD_PROGRAMA_PROD = '" + programaProduccion.getCodProgramaProduccion() + "' and ppr.COD_TIPO_PROGRAMA_PROD = '" + programaProduccion.getTiposProgramaProduccion().getCodTipoProgramaProd() + "'"
                            + " and ppr.COD_MATERIAL = fmdes.COD_MATERIAL) modificacion "
                            + " where fm.COD_FORMULA_MAESTRA = '" + programaProduccion.getFormulaMaestra().getCodFormulaMaestra() + "' "
                            + " and pprd.COD_PROGRAMA_PROD = '" + programaProduccion.getCodProgramaProduccion() + "' "
                            + " and pprd.COD_TIPO_PROGRAMA_PROD = '" + programaProduccion.getTiposProgramaProduccion().getCodTipoProgramaProd() + "' "
                            + " and pprd.COD_LOTE_PRODUCCION = '" + programaProduccion.getCodLoteProduccion() + "' "
                            + " and  pprd.COD_COMPPROD = '" + programaProduccion.getFormulaMaestra().getComponentesProd().getCodCompprod() + "' ";

                    consulta = " select m.cod_material,m.NOMBRE_MATERIAL, u.NOMBRE_UNIDAD_MEDIDA, P.CANTIDAD,u.COD_UNIDAD_MEDIDA,u.abreviatura"
                            + " from PROGRAMA_PRODUCCION_DETALLE p"
                            + " inner join materiales m on m.COD_MATERIAL = p.COD_MATERIAL"
                            + " inner join grupos g on g.COD_GRUPO = m.COD_GRUPO"
                            + " inner join capitulos c on c.COD_CAPITULO = g.COD_CAPITULO and c.COD_CAPITULO in(4,8)"
                            + " inner join UNIDADES_MEDIDA u on u.COD_UNIDAD_MEDIDA = p.COD_UNIDAD_MEDIDA"
                            + " where p.COD_LOTE_PRODUCCION = '" + programaProduccion.getCodLoteProduccion() + "'"
                            + " and p.COD_TIPO_PROGRAMA_PROD = '" + programaProduccion.getTiposProgramaProduccion().getCodTipoProgramaProd() + "'"
                            + " and p.COD_ESTADO_REGISTRO = 1 and p.cod_tipo_programa_prod = '" + programaProduccion.getTiposProgramaProduccion().getCodTipoProgramaProd() + "'"
                            + //" and p.cod_formula_maestra = '"+programaProduccion.getFormulaMaestra().getCodFormulaMaestra()+"'" +
                            " and p.cod_compprod = '" + programaProduccion.getFormulaMaestra().getComponentesProd().getCodCompprod() + "'";

                    System.out.println("consulta " + consulta);

                    ResultSet rsPS = st.executeQuery(consulta);
                    while (rsPS.next()) {
                        FormulaMaestraDetalleES formulaMaestraDetalleES = new FormulaMaestraDetalleES();
                        formulaMaestraDetalleES.setChecked(true);
                        formulaMaestraDetalleES.getMateriales().setCodMaterial(rsPS.getString("COD_MATERIAL"));
                        formulaMaestraDetalleES.getMateriales().setNombreMaterial(rsPS.getString("NOMBRE_MATERIAL"));
                        //Double cantidad = rsPS.getDouble("cant_modif")>0?rsPS.getDouble("cant_modif"):rsPS.getDouble("cantidad");
                        //formulaMaestraDetalleES.setCantidad(Float.valueOf(String.valueOf(this.redondear((cantidad*prorateo)>0?cantidad*prorateo:cantidad,0))));
                        formulaMaestraDetalleES.setCantidad(Float.valueOf(String.valueOf(this.redondear((rsPS.getDouble("CANTIDAD")) > 0 ? rsPS.getDouble("CANTIDAD") : rsPS.getDouble("CANTIDAD"), 0))));
                        formulaMaestraDetalleES.getUnidadesMedida().setCodUnidadMedida(rsPS.getInt("COD_UNIDAD_MEDIDA"));
                        formulaMaestraDetalleES.getUnidadesMedida().setNombreUnidadMedida(rsPS.getString("NOMBRE_UNIDAD_MEDIDA"));
                        formulaMaestraDetalleES.getUnidadesMedida().setAbreviatura(rsPS.getString("ABREVIATURA"));
                        //codEmpaque = rsPS.getString("cod_presentacion");
                        //nombreEmpaque = rsPS.getString("NOMBRE_PRODUCTO_PRESENTACION");
                        empaqueSecundarioList.add(formulaMaestraDetalleES);
                    }

                    System.out.println("consulta " + consulta);
                    //presentacionesSecundariasList.clear();
                    //presentacionesSecundariasList.add(new SelectItem("0","-NINGUNO-"));
                    //presentacionesSecundariasList.add(new SelectItem(codEmpaque,nombreEmpaque)); //rsPS.getString("cod_presentacion"),rsPS.getString("NOMBRE_PRODUCTO_PRESENTACION")
                    //while(rsPS.next()){

                    //}
                    rsPS.close();
                }
            }

            //empaquePrimarioList.clear();
            //empaqueSecundarioList.clear();
            materiaPrimaList = this.agrupaMaterialesMP1(materiaPrimaList);
            empaquePrimarioList = this.agrupaMaterialesEP(empaquePrimarioList);

            st.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getCargarSalidaAlmacenDesviacion() {
        try {
            configuracionSalidaAlmacenProduccion.getTiposSalidaAlmacenProduccion().setCodTipoSalidaAlmacenProduccion(1);
            configuracionSalidaMaterialProduccionPersonaList = this.cargarConfiguracionSalidaAlmacenProduccionPersona();

            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            Iterator i = programaProduccionSeleccionadoList.iterator();
            materiaPrimaList.clear();
            empaquePrimarioList.clear();
            empaqueSecundarioList.clear();
            int count_reg = 0;
            alertaSalidasLote = "";
            ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            String nroSalidas = "";

            String sql_reg = "select s.NRO_SALIDA_ALMACEN,s.cod_salida_almacen from SALIDAS_ALMACEN s where s.COD_LOTE_PRODUCCION =  '" + programaProduccion.getCodLoteProduccion() + "' "
                    + //String sql_reg="select count (s.NRO_SALIDA_ALMACEN) from SALIDAS_ALMACEN s where s.COD_LOTE_PRODUCCION =  '"+programaProduccion.getCodLoteProduccion()+"' " +
                    " and s.COD_ESTADO_SALIDA_ALMACEN <> 2"
                    + " and s.COD_ESTADO_SALIDA_ALMACEN <> 2 and s.cod_gestion >=10"
                    + " and s.COD_ALMACEN='" + usuario.getAlmacenesGlobal().getCodAlmacen() + "'";

            System.out.println("sql_reg : " + sql_reg);
            ResultSet rsReg = st.executeQuery(sql_reg);
            while (rsReg.next()) {
                //count_reg=rsReg.getInt(1);
                //nroSalidas+=rsReg.getString(1)+",";
                nroSalidas += "<a style='cursor:hand;text-decoration:underline;color:blue;font-size:15px' target=\"_blank\" href=\"navegadorSalidasAlmacenReporte.jsf?codigo=" + rsReg.getString(2) + "\" >" + rsReg.getString(1) + "</a>,";
                count_reg++;
            }

            if (count_reg > 0) {
                alertaSalidasLote = "  YA SE REALIZARON SALIDAS PARA ESTE NRO DE LOTE !!!!  LAS SALIDAS SON LAS SIGUIENTES : " + nroSalidas + "";
            }
            while (i.hasNext()) {
                ProgramaProduccion programaProduccion = (ProgramaProduccion) i.next();
                String consulta = "";
                int codTipoMaterial = this.conMaterial(1, configuracionSalidaMaterialProduccionPersonaList, configuracionSalidaAlmacenProduccion); //con el tipo de material 1 materia prima

                double prorateo = programaProduccion.getCantIngresoAcond() / Double.parseDouble(programaProduccion.getFormulaMaestra().getCantidadLote().replace(",", ""));
                double prorateoMP = Double.parseDouble(programaProduccion.getCantidadLote().replace(",", "")) / Double.parseDouble(programaProduccion.getFormulaMaestra().getCantidadLote().replace(",", "")); //prorateo para acond
                System.out.println("prorateo MP :" + prorateoMP);
                System.out.println("prorateo MP :" + codTipoMaterial);
                System.out.println("prorateo acond " + prorateo);
                if (codTipoMaterial == 1) {
                    //materia prima
                    consulta = " select m.COD_MATERIAL,m.NOMBRE_MATERIAL,u.COD_UNIDAD_MEDIDA,u.NOMBRE_UNIDAD_MEDIDA,fmdmp.CANTIDAD*'" + prorateoMP + "' cantidad,u.abreviatura"
                            + " from FORMULA_MAESTRA fm "
                            + " inner join FORMULA_MAESTRA_DETALLE_MP fmdmp on fmdmp.COD_FORMULA_MAESTRA = fm.COD_FORMULA_MAESTRA "
                            + " inner join materiales m on fmdmp.COD_MATERIAL = m.COD_MATERIAL "
                            + " inner join UNIDADES_MEDIDA u on u.COD_UNIDAD_MEDIDA = fmdmp.COD_UNIDAD_MEDIDA "
                            + " where fm.COD_FORMULA_MAESTRA = '" + programaProduccion.getFormulaMaestra().getCodFormulaMaestra() + "' ";
                    consulta = " select m.COD_MATERIAL,m.NOMBRE_MATERIAL,u.cod_unidad_medida,u.NOMBRE_UNIDAD_MEDIDA,sum(p.cantidad) cantidad,u.abreviatura"
                            + " from PROGRAMA_PRODUCCION_DETALLE ppd "
                            + " inner join PROGRAMA_PRODUCCION_DETALLE_FRACCIONES p on p.cod_programa_prod = ppd.cod_programa_prod and p.cod_compprod = ppd.cod_compprod and p.cod_material = ppd.cod_material and p.cod_lote_produccion = ppd.cod_lote_produccion and p.cod_tipo_programa_prod = ppd.cod_tipo_programa_prod "
                            + " inner join materiales m on m.COD_MATERIAL = p.COD_MATERIAL"
                            + " inner join grupos g on g.cod_grupo = m.cod_grupo "
                            + " inner join capitulos c on c.cod_capitulo = g.cod_capitulo "
                            + " inner join UNIDADES_MEDIDA u on u.COD_UNIDAD_MEDIDA = m.COD_UNIDAD_MEDIDA "
                            + " where p.COD_LOTE_PRODUCCION = '" + programaProduccion.getCodLoteProduccion() + "' and p.cod_compprod = '" + programaProduccion.getFormulaMaestra().getComponentesProd().getCodCompprod() + "' and p.cod_programa_prod = '" + programaProduccion.getCodProgramaProduccion() + "' and p.cod_tipo_programa_prod = '" + programaProduccion.getTiposProgramaProduccion().getCodTipoProgramaProd() + "'"
                            + " and c.cod_capitulo = 2 and ppd.cod_estado_registro = 1 and ppd.cod_tipo_material = 1 "
                            + " group by m.COD_MATERIAL,m.NOMBRE_MATERIAL,u.NOMBRE_UNIDAD_MEDIDA,u.cod_unidad_medida,u.abreviatura ";
                    /*
                             * ,modificacion.cantidad*'"+prorateoMP+"' cant_modif
                             " outer apply(select ppr.CANTIDAD  from PROGRAMA_PRODUCCION_DETALLE ppr where ppr.COD_COMPPROD = '"+programaProduccion.getFormulaMaestra().getComponentesProd().getCodCompprod()+"'" +
                                    " and ppr.COD_LOTE_PRODUCCION = '"+programaProduccion.getCodLoteProduccion()+"' and ppr.COD_PROGRAMA_PROD = '"+programaProduccion.getCodProgramaProduccion()+"' and ppr.COD_TIPO_PROGRAMA_PROD = '"+programaProduccion.getTiposProgramaProduccion().getCodTipoProgramaProd()+"'" +
                                    " and ppr.COD_MATERIAL = fmdmp.COD_MATERIAL) modificacion " +
                     */

                    System.out.println("consulta " + consulta);

                    ResultSet rsMP = st.executeQuery(consulta);
                    while (rsMP.next()) {
                        FormulaMaestraDetalleMP formulaMaestraDetalleMP = new FormulaMaestraDetalleMP();
                        formulaMaestraDetalleMP.setChecked(true);
                        formulaMaestraDetalleMP.getMateriales().setCodMaterial(rsMP.getString("COD_MATERIAL"));
                        formulaMaestraDetalleMP.getMateriales().setNombreMaterial(rsMP.getString("NOMBRE_MATERIAL"));
                        formulaMaestraDetalleMP.getUnidadesMedida().setCodUnidadMedida(rsMP.getInt("COD_UNIDAD_MEDIDA"));
                        formulaMaestraDetalleMP.getUnidadesMedida().setNombreUnidadMedida(rsMP.getString("NOMBRE_UNIDAD_MEDIDA"));
                        formulaMaestraDetalleMP.getUnidadesMedida().setAbreviatura(rsMP.getString("ABREVIATURA"));
                        formulaMaestraDetalleMP.setCantidad(rsMP.getDouble("CANTIDAD"));
                        //formulaMaestraDetalleMP.setCantidad(Float.valueOf(String.valueOf((rsMP.getDouble("CANTIDAD")))));
                        //Double cantidad = rsMP.getDouble("cant_modif")>0?rsMP.getDouble("cant_modif"):rsMP.getDouble("cantidad");
                        //formulaMaestraDetalleMP.setCantidad(Float.valueOf(String.valueOf((this.redondear(cantidad,2)))));
                        //formulaMaestraDetalleMP.setCantidad(Float.valueOf(String.valueOf((this.redondear(rsMP.getDouble("CANTIDAD"),2)))));

                        System.out.println("el datosXXXXX" + formulaMaestraDetalleMP.getCantidad());
                        materiaPrimaList.add(formulaMaestraDetalleMP);
                    }
                    rsMP.close();
                }
                String codEmpaque = "";
                String nombreEmpaque = "";
                codTipoMaterial = this.conMaterial(2, configuracionSalidaMaterialProduccionPersonaList, configuracionSalidaAlmacenProduccion); //con el tipo de material 2 material de empaque primario

                if (codTipoMaterial == 1) {
                    // cargar presentacionPrimaria
                    consulta = " select mt.COD_MATERIAL,mt.NOMBRE_MATERIAL,um.COD_UNIDAD_MEDIDA,um.NOMBRE_UNIDAD_MEDIDA,fmdep.CANTIDAD CANTIDAD,pprd.cantidad cant_modif,ep.cod_envaseprim,ep.nombre_envaseprim,um.abreviatura,mt.cod_grupo "
                            + " from PROGRAMA_PRODUCCION_DETALLE pprd "
                            + " inner join FORMULA_MAESTRA fm on fm.COD_COMPPROD = pprd.COD_COMPPROD "
                            + " inner join materiales mt on mt.COD_MATERIAL = pprd.COD_MATERIAL "
                            + " inner join UNIDADES_MEDIDA um on um.COD_UNIDAD_MEDIDA = pprd.COD_UNIDAD_MEDIDA "
                            + " inner join FORMULA_MAESTRA_DETALLE_EP fmdep on fmdep.COD_FORMULA_MAESTRA = fm.COD_FORMULA_MAESTRA "
                            + " and fmdep.COD_MATERIAL = pprd.COD_MATERIAL "
                            + " inner join PRESENTACIONES_PRIMARIAS prp on prp.COD_PRESENTACION_PRIMARIA = fmdep.COD_PRESENTACION_PRIMARIA "
                            + " and prp.COD_TIPO_PROGRAMA_PROD = pprd.COD_TIPO_PROGRAMA_PROD   "
                            + " inner join ENVASES_PRIMARIOS ep on ep.cod_envaseprim = prp.COD_ENVASEPRIM"
                            + " where fm.COD_FORMULA_MAESTRA = '" + programaProduccion.getFormulaMaestra().getCodFormulaMaestra() + "' "
                            + " and pprd.COD_PROGRAMA_PROD = '" + programaProduccion.getCodProgramaProduccion() + "' "
                            + " and pprd.COD_TIPO_PROGRAMA_PROD = '" + programaProduccion.getTiposProgramaProduccion().getCodTipoProgramaProd() + "' "
                            + " and pprd.COD_LOTE_PRODUCCION = '" + programaProduccion.getCodLoteProduccion() + "' "
                            + " and pprd.COD_COMPPROD = '" + programaProduccion.getFormulaMaestra().getComponentesProd().getCodCompprod() + "'  ";
                    consulta = " select mt.COD_MATERIAL,mt.NOMBRE_MATERIAL,um.COD_UNIDAD_MEDIDA,um.NOMBRE_UNIDAD_MEDIDA,fmdep.CANTIDAD CANTIDAD,ep.cod_envaseprim,ep.nombre_envaseprim,um.abreviatura,mt.cod_grupo "
                            + " from PROGRAMA_PRODUCCION ppr "
                            + " inner join FORMULA_MAESTRA fm on fm.COD_COMPPROD = ppr.COD_COMPPROD and fm.cod_estado_registro = 1 "
                            + " inner join FORMULA_MAESTRA_DETALLE_EP fmdep on fmdep.COD_FORMULA_MAESTRA = fm.COD_FORMULA_MAESTRA "
                            + " inner join materiales mt on mt.COD_MATERIAL = fmdep.COD_MATERIAL "
                            + " inner join UNIDADES_MEDIDA um on um.COD_UNIDAD_MEDIDA = fmdep.COD_UNIDAD_MEDIDA "
                            + " inner join PRESENTACIONES_PRIMARIAS prp on prp.COD_PRESENTACION_PRIMARIA = fmdep.COD_PRESENTACION_PRIMARIA "
                            + " and prp.COD_TIPO_PROGRAMA_PROD = ppr.COD_TIPO_PROGRAMA_PROD   "
                            + " inner join ENVASES_PRIMARIOS ep on ep.cod_envaseprim = prp.COD_ENVASEPRIM"
                            + " where fm.COD_FORMULA_MAESTRA = '" + programaProduccion.getFormulaMaestra().getCodFormulaMaestra() + "' "
                            + " and ppr.COD_PROGRAMA_PROD = '" + programaProduccion.getCodProgramaProduccion() + "' "
                            + " and ppr.COD_TIPO_PROGRAMA_PROD = '" + programaProduccion.getTiposProgramaProduccion().getCodTipoProgramaProd() + "' "
                            + " and ppr.COD_LOTE_PRODUCCION = '" + programaProduccion.getCodLoteProduccion() + "' "
                            + " and ppr.COD_COMPPROD = '" + programaProduccion.getFormulaMaestra().getComponentesProd().getCodCompprod() + "'  ";

                    consulta = " select m.COD_MATERIAL,m.NOMBRE_MATERIAL,u.cod_unidad_medida,u.NOMBRE_UNIDAD_MEDIDA,sum(cantidad) cantidad,u.abreviatura"
                            + " from PROGRAMA_PRODUCCION_DETALLE p"
                            + " inner join materiales m on m.COD_MATERIAL = p.COD_MATERIAL "
                            + " inner join UNIDADES_MEDIDA u on u.COD_UNIDAD_MEDIDA = m.COD_UNIDAD_MEDIDA"
                            + " inner join grupos g on g.COD_GRUPO = m.COD_GRUPO"
                            + " inner join capitulos c on c.COD_CAPITULO = g.COD_CAPITULO"
                            + " where p.COD_LOTE_PRODUCCION = '" + programaProduccion.getCodLoteProduccion() + "' and p.COD_COMPPROD = '" + programaProduccion.getFormulaMaestra().getComponentesProd().getCodCompprod() + "' "
                            + " and p.COD_TIPO_PROGRAMA_PROD = '" + programaProduccion.getTiposProgramaProduccion().getCodTipoProgramaProd() + "'"
                            + " and p.COD_PROGRAMA_PROD = '" + programaProduccion.getCodProgramaProduccion() + "' and c.COD_CAPITULO = 3"
                            + " group by m.COD_MATERIAL,m.NOMBRE_MATERIAL,u.NOMBRE_UNIDAD_MEDIDA,u.cod_unidad_medida,u.abreviatura ";
                    System.out.println("consulta " + consulta);
                    ResultSet rs = st.executeQuery(consulta);

                    while (rs.next()) {
                        FormulaMaestraDetalleEP formulaMaestraDetalleEP = new FormulaMaestraDetalleEP();
                        formulaMaestraDetalleEP.setChecked(true);
                        formulaMaestraDetalleEP.getMateriales().setCodMaterial(rs.getString("COD_MATERIAL"));
                        formulaMaestraDetalleEP.getMateriales().setNombreMaterial(rs.getString("NOMBRE_MATERIAL"));
                        //Double cantidad = rs.getDouble("cant_modif")>0?rs.getDouble("cant_modif"):rs.getDouble("cantidad");
                        //formulaMaestraDetalleEP.setCantidad(Float.valueOf(String.valueOf(this.redondear((cantidad*prorateoMP)>0?cantidad*prorateoMP:cantidad,2))));
                        formulaMaestraDetalleEP.setCantidad(rs.getFloat("CANTIDAD"));
                        formulaMaestraDetalleEP.getUnidadesMedida().setCodUnidadMedida(rs.getInt("COD_UNIDAD_MEDIDA"));
                        formulaMaestraDetalleEP.getUnidadesMedida().setNombreUnidadMedida(rs.getString("NOMBRE_UNIDAD_MEDIDA"));
                        formulaMaestraDetalleEP.getUnidadesMedida().setAbreviatura(rs.getString("ABREVIATURA"));
                        //formulaMaestraDetalleEP.getMateriales().getGrupos().setCodGrupo(rs.getInt("cod_grupo"));
                        //codEmpaque = rs.getString("cod_envaseprim");
                        //nombreEmpaque = rs.getString("nombre_envaseprim");
                        empaquePrimarioList.add(formulaMaestraDetalleEP);
                    }
                    //presentacionesPrimariasList.add(new SelectItem("0","-NINGUNO-"));
                    presentacionesPrimariasList.clear();
                    presentacionesPrimariasList.add(new SelectItem(codEmpaque, nombreEmpaque)); //rs.getString("cod_presentacion_primaria"),rs.getString("nombre_envaseprim") + " x " + rs.getInt("CANTIDAD")
                    rs.close();
                    //while(rs.next()){

                    //}
                }
                presentacionesSecundariasList.add(new SelectItem(codEmpaque, nombreEmpaque)); //rsPS.getString("cod_presentacion"),rsPS.getString("NOMBRE_PRODUCTO_PRESENTACION")
            }
            //empaque secundario

            if (this.conMaterial(3, configuracionSalidaMaterialProduccionPersonaList, configuracionSalidaAlmacenProduccion) == 1) {
                StringBuilder condiciones = new StringBuilder();
                Iterator e = programaProduccionSeleccionadoList.iterator();
                while (e.hasNext()) {
                    ProgramaProduccion programaProduccion = (ProgramaProduccion) e.next();
                    if (condiciones.length() > 0) {
                        condiciones.append(" OR ");
                    }
                    condiciones.append("( p.COD_LOTE_PRODUCCION = '").append(programaProduccion.getCodLoteProduccion()).append("'");
                    condiciones.append(" and p.COD_COMPPROD = '").append(programaProduccion.getFormulaMaestra().getComponentesProd().getCodCompprod()).append("' ");
                    condiciones.append(" and p.COD_TIPO_PROGRAMA_PROD = '").append(programaProduccion.getTiposProgramaProduccion().getCodTipoProgramaProd()).append("'");
                    condiciones.append(" and p.COD_PROGRAMA_PROD = '").append(programaProduccion.getCodProgramaProduccion()).append("')");
                }
                StringBuilder consulta = new StringBuilder("select m.COD_MATERIAL,m.NOMBRE_MATERIAL,u.cod_unidad_medida,u.NOMBRE_UNIDAD_MEDIDA,");
                consulta.append("sum(CEILING(cantidad)) cantidad,u.abreviatura");
                consulta.append(" from PROGRAMA_PRODUCCION_DETALLE p");
                consulta.append(" inner join materiales m on m.COD_MATERIAL = p.COD_MATERIAL and p.COD_TIPO_MATERIAL=3");
                consulta.append(" inner join UNIDADES_MEDIDA u on u.COD_UNIDAD_MEDIDA = m.COD_UNIDAD_MEDIDA");
                consulta.append(" inner join grupos g on g.COD_GRUPO = m.COD_GRUPO");
                consulta.append(" inner join capitulos c on c.COD_CAPITULO = g.COD_CAPITULO");
                consulta.append(" where ");
                consulta.append(condiciones.toString());
                consulta.append(" group by m.COD_MATERIAL,m.NOMBRE_MATERIAL,u.NOMBRE_UNIDAD_MEDIDA,u.cod_unidad_medida,u.abreviatura");
                System.out.println("consulta es" + consulta.toString());

                ResultSet rsPS = st.executeQuery(consulta.toString());
                while (rsPS.next()) {
                    FormulaMaestraDetalleES formulaMaestraDetalleES = new FormulaMaestraDetalleES();
                    formulaMaestraDetalleES.setChecked(true);
                    formulaMaestraDetalleES.getMateriales().setCodMaterial(rsPS.getString("COD_MATERIAL"));
                    formulaMaestraDetalleES.getMateriales().setNombreMaterial(rsPS.getString("NOMBRE_MATERIAL"));
                    //Double cantidad = rsPS.getDouble("cant_modif")>0?rsPS.getDouble("cant_modif"):rsPS.getDouble("cantidad");
                    //formulaMaestraDetalleES.setCantidad(Float.valueOf(String.valueOf(this.redondear((cantidad*prorateo)>0?cantidad*prorateo:cantidad,0))));
                    formulaMaestraDetalleES.setCantidad(Float.valueOf(String.valueOf(Math.ceil(rsPS.getDouble("CANTIDAD")))));
                    formulaMaestraDetalleES.getUnidadesMedida().setCodUnidadMedida(rsPS.getInt("COD_UNIDAD_MEDIDA"));
                    formulaMaestraDetalleES.getUnidadesMedida().setNombreUnidadMedida(rsPS.getString("NOMBRE_UNIDAD_MEDIDA"));
                    formulaMaestraDetalleES.getUnidadesMedida().setAbreviatura(rsPS.getString("ABREVIATURA"));
                    //codEmpaque = rsPS.getString("cod_presentacion");
                    //nombreEmpaque = rsPS.getString("NOMBRE_PRODUCTO_PRESENTACION");
                    empaqueSecundarioList.add(formulaMaestraDetalleES);
                }

                rsPS.close();

            }
            //empaquePrimarioList.clear();
            //empaqueSecundarioList.clear();
            materiaPrimaList = this.agrupaMaterialesMP1(materiaPrimaList);
            empaquePrimarioList = this.agrupaMaterialesEP(empaquePrimarioList);
            st.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List agrupaMaterialesMP(List materiaPrimaList) {
        Iterator i = materiaPrimaList.iterator();
        List materialesList = new ArrayList();
        while (i.hasNext()) {
            FormulaMaestraDetalleMP fmdMP = (FormulaMaestraDetalleMP) i.next();
            //materiaPrimaList.remove(fmdMP);
            //i.remove();
            //iterar y buscar los que se parecen
            Iterator i1 = materiaPrimaList.iterator();
            fmdMP.setCantidad(0d);
            while (i1.hasNext()) {
                FormulaMaestraDetalleMP fmdMP1 = (FormulaMaestraDetalleMP) i1.next();
                if (fmdMP.getMateriales().getCodMaterial().equals(fmdMP1.getMateriales().getCodMaterial())) {
                    fmdMP.setCantidad(fmdMP.getCantidad() + fmdMP1.getCantidad());
                    //materiaPrimaList.remove(fmdMP1); //remover el elemento parecido
                    materiaPrimaList.remove(fmdMP1);
                }
            }
            fmdMP.setCantidad(this.redondear(fmdMP.getCantidad(), 1));
            materialesList.add(fmdMP);
        }

        return materialesList;
    }

    public List agrupaMaterialesMP1(List materiaPrimaList) {
        //Iterator i = materiaPrimaList.iterator();
        List materialesList = new ArrayList();
        while (materiaPrimaList.size() > 0) {
            Iterator i = materiaPrimaList.iterator();
            if (i.hasNext()) {
                FormulaMaestraDetalleMP fmdMP = (FormulaMaestraDetalleMP) i.next();
                Iterator i1 = materiaPrimaList.iterator();
                Double cantidad = 0d;
                while (i1.hasNext()) {
                    FormulaMaestraDetalleMP fmdMP1 = (FormulaMaestraDetalleMP) i1.next();
                    if (fmdMP.getMateriales().getCodMaterial().equals(fmdMP1.getMateriales().getCodMaterial())) {
                        cantidad = cantidad + fmdMP1.getCantidad();
                        System.out.println("comparacion" + fmdMP1.getCantidad());
                        //materiaPrimaList.remove(fmdMP1); //remover el elemento parecido
                        //materiaPrimaList.remove(fmdMP1);
                        System.out.println("Material: ->" + fmdMP.getMateriales().getNombreMaterial() + ", cantidad: " + fmdMP.getCantidad());
                        i1.remove();
                    }
                }
                System.out.println("elemento adicionado");
                fmdMP.setCantidad(cantidad);
                fmdMP.setCantidad(this.redondear(fmdMP.getCantidad(), 2));
                materialesList.add(fmdMP);
            }

            //materiaPrimaList.remove(fmdMP);
            //i.remove();
            //iterar y buscar los que se parecen
        }

        return materialesList;
    }

    public static void main(String args[]) {
        Double var = 0.0543476d;
        ManagedProgramaProduccion mpp = new ManagedProgramaProduccion();
        System.out.println("valor " + mpp.redondear(var, 2));
        Double valor = 149998.87;
        System.out.println("valor " + 149998.87);
        String string = String.valueOf(valor);
        System.out.println("string " + string);
        float fl = valor.floatValue();
        System.out.println("float " + fl);
        BigDecimal bd = new BigDecimal(string);
        System.out.println("bd " + bd);
        float f2 = bd.floatValue();
        System.out.println("fbd " + f2);
        Float f3 = new Float(valor);
        System.out.println("F3 " + f3);
        double d1 = valor / fl;
        System.out.println("d1 " + d1);
        double d2 = fl / valor;
        System.out.println("d2 " + d2);
        float f4 = fl - (float) d1;
        float f5 = fl - (float) d2;
        System.out.println("dif " + f4 + "  " + f5);
        int entero = (int) (valor * 100);
        System.out.println("entero " + entero);
        float f6 = (float) entero / (float) 100;
        System.out.println("f6 " + f6);
        int enterof = Integer.parseInt(valor.toString());
        int decimal = entero % 100;
        Float f7 = new Float(String.valueOf(enterof) + "." + String.valueOf(decimal));
        float f8 = f7.floatValue();
        System.out.println("f8 " + f8);

    }

    public List agrupaMaterialesEP(List empaquePrimarioList) {
        //Iterator i = materiaPrimaList.iterator();
        List materialesList = new ArrayList();
        while (empaquePrimarioList.size() > 0) {
            Iterator i = empaquePrimarioList.iterator();
            if (i.hasNext()) {
                FormulaMaestraDetalleEP fmdMP = (FormulaMaestraDetalleEP) i.next();
                Iterator i1 = empaquePrimarioList.iterator();
                float cantidad = 0;
                while (i1.hasNext()) {
                    FormulaMaestraDetalleEP fmdMP1 = (FormulaMaestraDetalleEP) i1.next();
                    if (fmdMP.getMateriales().getCodMaterial().equals(fmdMP1.getMateriales().getCodMaterial())) {
                        cantidad = cantidad + fmdMP1.getCantidad();
                        System.out.println("comparacion" + fmdMP1.getCantidad());
                        //materiaPrimaList.remove(fmdMP1); //remover el elemento parecido
                        //materiaPrimaList.remove(fmdMP1);
                        i1.remove();
                    }
                }
                System.out.println("elemento adicionado");
                fmdMP.setCantidad(cantidad);
                fmdMP.setCantidad(Float.parseFloat(String.valueOf(this.redondear(fmdMP.getCantidad(), 2))));
                materialesList.add(fmdMP);
            }

            //materiaPrimaList.remove(fmdMP);
            //i.remove();
            //iterar y buscar los que se parecen
        }

        return materialesList;
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

    public void cargarSalidaAlmacenProduccionPorLoteProgramaProduccion() {
        try {
            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String consulta = "";
            materiaPrimaList.clear();
            if (permisoSalidaMateriaPrima) {

                consulta = " select m.COD_MATERIAL,  m.NOMBRE_MATERIAL,  sum(pprd.CANTIDAD) CANTIDAD,  u1.COD_UNIDAD_MEDIDA COD_UNIDAD_MEDIDA,"
                        + " u1.NOMBRE_UNIDAD_MEDIDA NOMBRE_UNIDAD_MEDIDA from FORMULA_MAESTRA_VERSION fm "
                        + " inner join FORMULA_MAESTRA_DETALLE_MP_VERSION fmdmp on fm.COD_FORMULA_MAESTRA = fmdmp.COD_FORMULA_MAESTRA "
                        + " AND fm.COD_VERSION=fmdmp.COD_VERSION"
                        + " inner join MATERIALES m on m.COD_MATERIAL = fmdmp.COD_MATERIAL "
                        + " inner join UNIDADES_MEDIDA u on u.COD_UNIDAD_MEDIDA = fmdmp.COD_UNIDAD_MEDIDA "
                        + " inner join PROGRAMA_PRODUCCION_DETALLE pprd on pprd.COD_MATERIAL = fmdmp.COD_MATERIAL "
                        + " and fm.COD_FORMULA_MAESTRA = fmdmp.COD_FORMULA_MAESTRA "
                        + " inner join UNIDADES_MEDIDA u1 on u1.COD_UNIDAD_MEDIDA = pprd.COD_UNIDAD_MEDIDA "
                        + " where fm.COD_VERSION='" + programaProduccion.getCodFormulaMaestraVersion() + "'"
                        + " fm.COD_FORMULA_MAESTRA = '" + programaProduccion.getFormulaMaestra().getCodFormulaMaestra() + "' "
                        + " and pprd.COD_PROGRAMA_PROD = '" + programaProduccion.getCodProgramaProduccion() + "' "
                        + " and  pprd.COD_LOTE_PRODUCCION = '" + programaProduccion.getCodLoteProduccion() + "' "
                        + " and pprd.COD_COMPPROD = '" + programaProduccion.getFormulaMaestra().getComponentesProd().getCodCompprod() + "'  "
                        + " group by fm.COD_FORMULA_MAESTRA,pprd.COD_PROGRAMA_PROD,pprd.COD_LOTE_PRODUCCION,"
                        + " pprd.COD_COMPPROD ,m.COD_MATERIAL,m.NOMBRE_MATERIAL,u1.COD_UNIDAD_MEDIDA,u1.NOMBRE_UNIDAD_MEDIDA ";
                System.out.println("consulta " + consulta);

                ResultSet rsMP = st.executeQuery(consulta);

                while (rsMP.next()) {
                    FormulaMaestraDetalleMP formulaMaestraDetalleMP = new FormulaMaestraDetalleMP();
                    formulaMaestraDetalleMP.setChecked(true);
                    formulaMaestraDetalleMP.getMateriales().setCodMaterial(rsMP.getString("COD_MATERIAL"));
                    formulaMaestraDetalleMP.getMateriales().setNombreMaterial(rsMP.getString("NOMBRE_MATERIAL"));
                    formulaMaestraDetalleMP.getUnidadesMedida().setCodUnidadMedida(rsMP.getInt("COD_UNIDAD_MEDIDA"));
                    formulaMaestraDetalleMP.getUnidadesMedida().setNombreUnidadMedida(rsMP.getString("NOMBRE_UNIDAD_MEDIDA"));
                    formulaMaestraDetalleMP.setCantidad(rsMP.getDouble("CANTIDAD"));
                    materiaPrimaList.add(formulaMaestraDetalleMP);
                }
                rsMP.close();
            }
            // cargar presentacionPrimaria
            String codEmpaque = "";
            String nombreEmpaque = "";
            empaquePrimarioList.clear();
            if (permisoSalidaEmpaquePrimario) {
                consulta = " select mt.COD_MATERIAL, mt.NOMBRE_MATERIAL, um.COD_UNIDAD_MEDIDA, um.NOMBRE_UNIDAD_MEDIDA, sum(pprd.CANTIDAD) CANTIDAD"
                        + " from PROGRAMA_PRODUCCION_DETALLE pprd "
                        + " inner join FORMULA_MAESTRA_VERSION fm on fm.COD_COMPPROD = pprd.COD_COMPPROD "
                        + " and fm.COD_FORMULA_MAESTRA=pprd.COD_FORMULA_MAESTRA"
                        + " inner join materiales mt on mt.COD_MATERIAL = pprd.COD_MATERIAL "
                        + " inner join UNIDADES_MEDIDA um on um.COD_UNIDAD_MEDIDA =  pprd.COD_UNIDAD_MEDIDA "
                        + " inner join FORMULA_MAESTRA_DETALLE_EP_VERSION fmdep on fmdep.COD_FORMULA_MAESTRA = fm.COD_FORMULA_MAESTRA "
                        + " and fmdep.COD_MATERIAL = pprd.COD_MATERIAL and fmdep.COD_VERSION=fm.COD_VERSION"
                        + " where fm.COD_VERSION='" + programaProduccion.getCodFormulaMaestraVersion() + "'"
                        + " fm.COD_FORMULA_MAESTRA = '" + programaProduccion.getFormulaMaestra().getCodFormulaMaestra() + "' "
                        + " and  pprd.COD_PROGRAMA_PROD = '" + programaProduccion.getCodProgramaProduccion() + "' "
                        + " and pprd.COD_LOTE_PRODUCCION = '" + programaProduccion.getCodLoteProduccion() + "' "
                        + " and pprd.COD_COMPPROD = '" + programaProduccion.getFormulaMaestra().getComponentesProd().getCodCompprod() + "' "
                        + " group by mt.COD_MATERIAL,mt.NOMBRE_MATERIAL,um.COD_UNIDAD_MEDIDA,um.NOMBRE_UNIDAD_MEDIDA ";

                System.out.println("consulta " + consulta);
                ResultSet rs = st.executeQuery(consulta);

                while (rs.next()) {
                    FormulaMaestraDetalleEP formulaMaestraDetalleEP = new FormulaMaestraDetalleEP();
                    formulaMaestraDetalleEP.setChecked(true);
                    formulaMaestraDetalleEP.getMateriales().setCodMaterial(rs.getString("COD_MATERIAL"));
                    formulaMaestraDetalleEP.getMateriales().setNombreMaterial(rs.getString("NOMBRE_MATERIAL"));
                    formulaMaestraDetalleEP.setCantidad(rs.getFloat("CANTIDAD"));
                    formulaMaestraDetalleEP.getUnidadesMedida().setCodUnidadMedida(rs.getInt("COD_UNIDAD_MEDIDA"));
                    formulaMaestraDetalleEP.getUnidadesMedida().setNombreUnidadMedida(rs.getString("NOMBRE_UNIDAD_MEDIDA"));
                    //codEmpaque = rs.getString("cod_envaseprim");
                    //nombreEmpaque = rs.getString("nombre_envaseprim");
                    empaquePrimarioList.add(formulaMaestraDetalleEP);
                }
                //presentacionesPrimariasList.add(new SelectItem("0","-NINGUNO-"));
                presentacionesPrimariasList.clear();
                presentacionesPrimariasList.add(new SelectItem(codEmpaque, nombreEmpaque)); //rs.getString("cod_presentacion_primaria"),rs.getString("nombre_envaseprim") + " x " + rs.getInt("CANTIDAD")
                //while(rs.next()){

                //}
                rs.close();
            }
            //cargar presentacionSecundaria
            empaqueSecundarioList.clear();
            if (permisoSalidaEmpaqueSecundario) {
                consulta = " select mt.COD_MATERIAL, mt.NOMBRE_MATERIAL,  um.COD_UNIDAD_MEDIDA, um.NOMBRE_UNIDAD_MEDIDA, sum(pprd.CANTIDAD) CANTIDAD"
                        + " from PROGRAMA_PRODUCCION_DETALLE pprd "
                        + " inner join FORMULA_MAESTRA_VERSION fm on fm.COD_COMPPROD = pprd.COD_COMPPROD "
                        + " and pprd.COD_FORMULA_MAESTRA=fm.COD_FORMULA_MAESTRA"
                        + "  inner join materiales mt on mt.COD_MATERIAL = pprd.COD_MATERIAL"
                        + "  inner join UNIDADES_MEDIDA um on um.COD_UNIDAD_MEDIDA = pprd.COD_UNIDAD_MEDIDA "
                        + "  inner join FORMULA_MAESTRA_DETALLE_ES_VERSION fmdes on fmdes.COD_FORMULA_MAESTRA = fm.COD_FORMULA_MAESTRA "
                        + " and fmdes.COD_MATERIAL = pprd.COD_MATERIAL  and fmdes.COD_VERSION=fm.COD_VERSION"
                        + " where fm.COD_VERSION='" + programaProduccion.getCodFormulaMaestraVersion() + "'"
                        + " fm.COD_FORMULA_MAESTRA = '" + programaProduccion.getFormulaMaestra().getCodFormulaMaestra() + "' "
                        + " and pprd.COD_PROGRAMA_PROD = '" + programaProduccion.getCodProgramaProduccion() + "' "
                        + " and pprd.COD_LOTE_PRODUCCION = '" + programaProduccion.getCodLoteProduccion() + "' "
                        + " and pprd.COD_COMPPROD = '" + programaProduccion.getFormulaMaestra().getComponentesProd().getCodCompprod() + "' "
                        + " group by mt.COD_MATERIAL,mt.NOMBRE_MATERIAL,um.COD_UNIDAD_MEDIDA,um.NOMBRE_UNIDAD_MEDIDA ";

                ResultSet rsPS = st.executeQuery(consulta);
                while (rsPS.next()) {
                    FormulaMaestraDetalleES formulaMaestraDetalleES = new FormulaMaestraDetalleES();
                    formulaMaestraDetalleES.setChecked(true);
                    formulaMaestraDetalleES.getMateriales().setCodMaterial(rsPS.getString("COD_MATERIAL"));
                    formulaMaestraDetalleES.getMateriales().setNombreMaterial(rsPS.getString("NOMBRE_MATERIAL"));
                    formulaMaestraDetalleES.setCantidad(rsPS.getFloat("CANTIDAD"));
                    formulaMaestraDetalleES.getUnidadesMedida().setCodUnidadMedida(rsPS.getInt("COD_UNIDAD_MEDIDA"));
                    formulaMaestraDetalleES.getUnidadesMedida().setNombreUnidadMedida(rsPS.getString("NOMBRE_UNIDAD_MEDIDA"));
                    //codEmpaque = rsPS.getString("cod_presentacion");
                    //nombreEmpaque = rsPS.getString("NOMBRE_PRODUCTO_PRESENTACION");
                    empaqueSecundarioList.add(formulaMaestraDetalleES);
                }
                System.out.println("consulta " + consulta);
                presentacionesSecundariasList.clear();
                //presentacionesSecundariasList.add(new SelectItem("0","-NINGUNO-"));
                presentacionesSecundariasList.add(new SelectItem(codEmpaque, nombreEmpaque)); //rsPS.getString("cod_presentacion"),rsPS.getString("NOMBRE_PRODUCTO_PRESENTACION")
                //while(rsPS.next()){

                //}
                //empaquePrimarioList.clear();
                //empaqueSecundarioList.clear();
                rsPS.close();
            }
            st.close();

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getCargarContenidoAgregarSalidaAlmacenProgramaProduccion() {
        this.cargarPermisosEspecialesUsuario();
        try {
            selectES = true;
            this.cargarSalidaAlmacenProduccionPorTipoProgramaProduccion2(); 


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String generarSalidaAlmacenes_action() {
        try {
            //buscar El programa produccion seleccionado

            for (ProgramaProduccion programaProduccionItem : programaProduccionList) {
                if (programaProduccionItem.getChecked().booleanValue() == true) {
                    programaProduccion = programaProduccionItem;
                    break;
                }
            }
            Utiles.direccionar("agregarSalidaAlmacenProgramaProduccion.jsf");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String generarSalidaAlmacenes_action1() {
        try {
            //buscar El programa produccion seleccionado
            programaProduccion = null;
            ProgramaProduccion programaProduccionR = null;
            programaProduccionSeleccionadoList.clear();
            for (ProgramaProduccion programaProduccionItem : programaProduccionList) {
                if (programaProduccionItem.getChecked().booleanValue() == true) {
                    programaProduccionSeleccionadoList.add(programaProduccionItem);
                    if (programaProduccionItem.getCodLoteProduccion().contains("R")) {
                        programaProduccionR = programaProduccionItem;
                    } else {
                        programaProduccion = programaProduccionItem;
                    }
                    //break;
                }
            }
            if (programaProduccion == null) {
                programaProduccion = programaProduccionR;
            }
            Utiles.direccionar("agregarSalidaAlmacenProgramaProduccion.jsf");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String generarSalidaAlmacenes_action_EP_ES() {
        habilita_materiaPrimaSalidaProduccion = false;
        salidaMateriaPrima = false;
        salidaEmpaquePrimarioSecundario = true;
        solicitudSalida = false;
        try {
            //buscar El programa produccion seleccionado
            programaProduccion = null;
            ProgramaProduccion programaProduccionR = null;
            programaProduccionSeleccionadoList.clear();
            for (ProgramaProduccion programaProduccionItem : programaProduccionList) {
                if (programaProduccionItem.getChecked().booleanValue() == true) {
                    programaProduccionSeleccionadoList.add(programaProduccionItem);
                    if (programaProduccionItem.getCodLoteProduccion().contains("R")) {
                        programaProduccionR = programaProduccionItem;
                    } else {
                        programaProduccion = programaProduccionItem;
                    }
                    //break;
                }
            }
            if (programaProduccion == null) {
                programaProduccion = programaProduccionR;
            }
            configuracionSalidaMaterialProduccionPersonaList = this.cargarConfiguracionSalidaAlmacenProduccionPersona();
            List configuracionSalidaMaterialProduccionPersonaListAux = new ArrayList();
            for (Object dato : configuracionSalidaMaterialProduccionPersonaList) {
                ConfiguracionSalidaAlmacenProduccion valor = (ConfiguracionSalidaAlmacenProduccion) dato;
                if (valor.getTiposMaterial().getCodTipoMaterial() == 1) {
                    habilita_materiaPrimaSalidaProduccion = true;
                } else {
                    configuracionSalidaMaterialProduccionPersonaListAux.add(dato);
                }
            }
            configuracionSalidaMaterialProduccionPersonaList = configuracionSalidaMaterialProduccionPersonaListAux;
            Utiles.direccionar("agregarSalidaAlmacenProgramaProduccion.jsf");

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }

    public String generarSalidaAlmacenes_action_MP() {
        salidaMateriaPrima = true;
        solicitudSalida = false;
        salidaEmpaquePrimarioSecundario = false;
        try {
            //buscar El programa produccion seleccionado
            programaProduccion = null;
            ProgramaProduccion programaProduccionR = null;
            programaProduccionSeleccionadoList.clear();
            for (ProgramaProduccion programaProduccionItem : programaProduccionList) {
                if (programaProduccionItem.getChecked().booleanValue() == true) {
                    programaProduccionSeleccionadoList.add(programaProduccionItem);
                    if (programaProduccionItem.getCodLoteProduccion().contains("R")) {
                        programaProduccionR = programaProduccionItem;
                    } else {
                        programaProduccion = programaProduccionItem;
                    }
                    //break;
                }
            }
            if (programaProduccion == null) {
                programaProduccion = programaProduccionR;
            }
            configuracionSalidaMaterialProduccionPersonaList = this.cargarConfiguracionSalidaAlmacenProduccionPersona();
            List configuracionSalidaMaterialProduccionPersonaListAux = new ArrayList();
            for (Object dato : configuracionSalidaMaterialProduccionPersonaList) {
                ConfiguracionSalidaAlmacenProduccion valor = (ConfiguracionSalidaAlmacenProduccion) dato;
                if (valor.getTiposMaterial().getCodTipoMaterial() == 1) {
                    habilita_materiaPrimaSalidaProduccion = true;
                    configuracionSalidaMaterialProduccionPersonaListAux.add(dato);
                }

            }
            configuracionSalidaMaterialProduccionPersonaList = configuracionSalidaMaterialProduccionPersonaListAux;
            mensajeMP = "";
            if (programaProduccionR != null) {
                mensajeMP = "NO se puede generar una salida de MP para un lote R.";
            } else {
                Utiles.direccionar("agregarSalidaAlmacenProgramaProduccion.jsf");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }

    public String generarSalidaAlmacenesDesviacion_action() {
        try {
            //buscar El programa produccion seleccionado
            programaProduccionSeleccionadoList.clear();
            for (ProgramaProduccion programaProduccionItem : programaProduccionList) {
                if (programaProduccionItem.getChecked().booleanValue() == true) {
                    programaProduccionSeleccionadoList.add(programaProduccionItem);
                    programaProduccion = programaProduccionItem;
                    //break;
                }
            }
            Utiles.direccionar("agregarSalidaAlmacenProgramaProduccionDesviacion.jsf");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String generarSolicitudSalidaAlmacenes_action() {
        solicitudSalida = true;
        salidaMateriaPrima = false;
        salidaEmpaquePrimarioSecundario = false;
        try {
            //buscar El programa produccion seleccionado

            for (ProgramaProduccion programaProduccionItem : programaProduccionList) {
                if (programaProduccionItem.getChecked().booleanValue() == true) {
                    programaProduccion = programaProduccionItem;
                    break;
                }
            }
            programaProduccionSeleccionadoList.clear();
            for (ProgramaProduccion programaProduccionItem : programaProduccionList) {
                if (programaProduccionItem.getChecked().booleanValue() == true) {
                    programaProduccionSeleccionadoList.add(programaProduccionItem);
                    programaProduccion = programaProduccionItem;
                    //break;
                }
            }
            Utiles.direccionar("agregarSolicitudSalidaAlmacenProgramaProduccion.jsf");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String generarSolicitudSalidaAlmacenesDesviacion_action() {
        try {
            //buscar El programa produccion seleccionado

            for (ProgramaProduccion programaProduccionItem : programaProduccionList) {
                if (programaProduccionItem.getChecked().booleanValue() == true) {
                    programaProduccion = programaProduccionItem;
                    break;
                }
            }
            programaProduccionSeleccionadoList.clear();
            for (ProgramaProduccion programaProduccionItem : programaProduccionList) {
                if (programaProduccionItem.getChecked().booleanValue() == true) {
                    programaProduccionSeleccionadoList.add(programaProduccionItem);
                    programaProduccion = programaProduccionItem;
                    //break;
                }
            }
            Utiles.direccionar("agregarSolicitudSalidaAlmacenProgramaProduccionDesviacion.jsf");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String empaquePrimario_change() {
        try {
            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String consulta = " select m.COD_MATERIAL,m.NOMBRE_MATERIAL,fmdep.CANTIDAD,u.COD_UNIDAD_MEDIDA,u.NOMBRE_UNIDAD_MEDIDA  "
                    + " from FORMULA_MAESTRA_DETALLE_EP fmdep  "
                    + " inner join materiales m on m.COD_MATERIAL = fmdep.COD_MATERIAL "
                    + " inner join UNIDADES_MEDIDA u on u.COD_UNIDAD_MEDIDA = fmdep.COD_UNIDAD_MEDIDA "
                    + " where fmdep.COD_FORMULA_MAESTRA = '" + programaProduccion.getFormulaMaestra().getCodFormulaMaestra() + "' "
                    + " and fmdep.COD_PRESENTACION_PRIMARIA = '" + presentacionesPrimarias.getCodPresentacionPrimaria() + "' "
                    + " order by m.NOMBRE_MATERIAL  ";
            consulta = " select m.COD_MATERIAL, m.NOMBRE_MATERIAL, fmdep.CANTIDAD, u.COD_UNIDAD_MEDIDA, u.NOMBRE_UNIDAD_MEDIDA, pprd.CANTIDAD CANTIDAD_PPRD,"
                    + " pprd.COD_UNIDAD_MEDIDA COD_UNIDAD_MEDIDA_PPRD,  u1.NOMBRE_UNIDAD_MEDIDA NOMBRE_UNIDAD_MEDIDA_PPRD "
                    + " from FORMULA_MAESTRA_DETALLE_EP fmdep "
                    + " inner join materiales m on m.COD_MATERIAL = fmdep.COD_MATERIAL "
                    + " inner join UNIDADES_MEDIDA u on u.COD_UNIDAD_MEDIDA = fmdep.COD_UNIDAD_MEDIDA "
                    + " inner join PROGRAMA_PRODUCCION_DETALLE pprd on pprd.COD_MATERIAL = fmdep.COD_MATERIAL      "
                    + " inner join UNIDADES_MEDIDA u1 on u1.COD_UNIDAD_MEDIDA = pprd.COD_UNIDAD_MEDIDA "
                    + " where fmdep.COD_FORMULA_MAESTRA = '" + programaProduccion.getFormulaMaestra().getCodFormulaMaestra() + "'  and "
                    + " fmdep.COD_PRESENTACION_PRIMARIA = '" + presentacionesPrimarias.getCodPresentacionPrimaria() + "'  "
                    + " and pprd.COD_PROGRAMA_PROD = '" + programaProduccion.getCodProgramaProduccion() + "' "
                    + " and pprd.COD_LOTE_PRODUCCION = '" + programaProduccion.getCodLoteProduccion() + "' "
                    + " and pprd.COD_COMPPROD = '" + programaProduccion.getFormulaMaestra().getComponentesProd().getCodCompprod() + "' "
                    + " and pprd.COD_TIPO_PROGRAMA_PROD = '" + programaProduccion.getTiposProgramaProduccion().getCodTipoProgramaProd() + "' "
                    + " order by m.NOMBRE_MATERIAL  ";

            System.out.println("consulta " + consulta);
            ResultSet rs = st.executeQuery(consulta);
            empaquePrimarioList.clear();
            while (rs.next()) {
                FormulaMaestraDetalleEP formulaMaestraDetalleEP = new FormulaMaestraDetalleEP();
                formulaMaestraDetalleEP.setChecked(true);
                formulaMaestraDetalleEP.getMateriales().setCodMaterial(rs.getString("COD_MATERIAL"));
                formulaMaestraDetalleEP.getMateriales().setNombreMaterial(rs.getString("NOMBRE_MATERIAL"));
                formulaMaestraDetalleEP.setCantidad(rs.getFloat("CANTIDAD_PPRD"));
                formulaMaestraDetalleEP.getUnidadesMedida().setCodUnidadMedida(rs.getInt("COD_UNIDAD_MEDIDA_PPRD"));
                formulaMaestraDetalleEP.getUnidadesMedida().setNombreUnidadMedida(rs.getString("NOMBRE_UNIDAD_MEDIDA_PPRD"));
                empaquePrimarioList.add(formulaMaestraDetalleEP);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String empaqueSecundario_change() {
        try {
            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String consulta = "  select m.COD_MATERIAL,m.NOMBRE_MATERIAL,u.COD_UNIDAD_MEDIDA,u.NOMBRE_UNIDAD_MEDIDA,fmdes.CANTIDAD  "
                    + " from FORMULA_MAESTRA_DETALLE_ES fmdes "
                    + " inner join MATERIALES m on m.COD_MATERIAL = fmdes.COD_MATERIAL "
                    + " inner join UNIDADES_MEDIDA u on u.COD_UNIDAD_MEDIDA = fmdes.COD_UNIDAD_MEDIDA "
                    + " where fmdes.COD_FORMULA_MAESTRA = '" + programaProduccion.getFormulaMaestra().getCodFormulaMaestra() + "' "
                    + " and fmdes.COD_PRESENTACION_PRODUCTO = '" + presentacionesProducto.getCodPresentacion() + "' ";

            consulta = " select m.COD_MATERIAL, m.NOMBRE_MATERIAL, u.COD_UNIDAD_MEDIDA, u.NOMBRE_UNIDAD_MEDIDA,  fmdes.CANTIDAD,  pprd.CANTIDAD CANTIDAD_PPRD,"
                    + " u1.COD_UNIDAD_MEDIDA COD_UNIDAD_MEDIDA_PPRD,u1.NOMBRE_UNIDAD_MEDIDA NOMBRE_UNIDAD_MEDIDA_PPRD   "
                    + " from FORMULA_MAESTRA_DETALLE_ES fmdes "
                    + " inner join MATERIALES m on m.COD_MATERIAL = fmdes.COD_MATERIAL "
                    + " inner join UNIDADES_MEDIDA u on u.COD_UNIDAD_MEDIDA =  fmdes.COD_UNIDAD_MEDIDA "
                    + " inner join PROGRAMA_PRODUCCION_DETALLE pprd on pprd.COD_MATERIAL = fmdes.COD_MATERIAL "
                    + " inner join UNIDADES_MEDIDA u1 on u1.COD_UNIDAD_MEDIDA = pprd.COD_UNIDAD_MEDIDA  "
                    + " where fmdes.COD_FORMULA_MAESTRA = '" + programaProduccion.getFormulaMaestra().getCodFormulaMaestra() + "'  "
                    + " and  fmdes.COD_PRESENTACION_PRODUCTO = '" + presentacionesProducto.getCodPresentacion() + "'   "
                    + " and pprd.COD_PROGRAMA_PROD = '" + programaProduccion.getCodProgramaProduccion() + "' "
                    + " and pprd.COD_LOTE_PRODUCCION = '" + programaProduccion.getCodLoteProduccion() + "' "
                    + " and pprd.COD_COMPPROD = '" + programaProduccion.getFormulaMaestra().getComponentesProd().getCodCompprod() + "' "
                    + " and pprd.COD_TIPO_PROGRAMA_PROD = '" + programaProduccion.getTiposProgramaProduccion().getCodTipoProgramaProd() + "' ";

            System.out.println("consulta " + consulta);
            ResultSet rs = st.executeQuery(consulta);
            empaqueSecundarioList.clear();
            while (rs.next()) {
                FormulaMaestraDetalleES formulaMaestraDetalleES = new FormulaMaestraDetalleES();
                formulaMaestraDetalleES.setChecked(true);
                formulaMaestraDetalleES.getMateriales().setCodMaterial(rs.getString("COD_MATERIAL"));
                formulaMaestraDetalleES.getMateriales().setNombreMaterial(rs.getString("NOMBRE_MATERIAL"));
                formulaMaestraDetalleES.setCantidad(rs.getFloat("CANTIDAD_PPRD"));
                formulaMaestraDetalleES.getUnidadesMedida().setCodUnidadMedida(rs.getInt("COD_UNIDAD_MEDIDA_PPRD"));
                formulaMaestraDetalleES.getUnidadesMedida().setNombreUnidadMedida(rs.getString("NOMBRE_UNIDAD_MEDIDA_PPRD"));

                empaqueSecundarioList.add(formulaMaestraDetalleES);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getCargarContenidoConfiguracionSalidasAlmacenProduccion() {
        try {
            this.cargarConfiguracionSalidaAlmacenProduccion();

            personalList = this.cargarPersonalList();
            tiposMaterialList = this.cargarTiposMaterialList();
            tiposSalidasMaterialList = this.cargarTiposSalidaMaterialList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void cargarConfiguracionSalidaAlmacenProduccion() {
        try {
            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String consulta = " select t.COD_TIPO_MATERIAL,t.NOMBRE_TIPO_MATERIAL,s.COD_TIPO_SALIDA_ALMACEN_PRODUCCION,s.NOMBRE_TIPO_SALIDA_ALMACEN_PRODUCCION,p.NOMBRE_PILA,p.AP_PATERNO_PERSONAL,p.AP_MATERNO_PERSONAL"
                    + " from TIPOS_MATERIAL t inner join   "
                    + " CONFIGURACION_SALIDA_ALMACEN_PRODUCCION s inner join personal p on p.cod_personal = s.cod_personal "
                    + " inner join TIPOS_SALIDA_ALMACEN_PRODUCCION t1  "
                    + " on t.COD_TIPO_MATERIAL = s.COD_TIPO_MATERIAL ";
            consulta = " select t.COD_TIPO_MATERIAL, t.NOMBRE_TIPO_MATERIAL, tsa.COD_TIPO_SALIDA_ALMACEN_PRODUCCION,tsa.NOMBRE_TIPO_SALIDA_ALMACEN_PRODUCCION,p.COD_PERSONAL,p.NOMBRE_PILA, p.AP_PATERNO_PERSONAL,p.AP_MATERNO_PERSONAL "
                    + " from CONFIGURACION_SALIDA_ALMACEN_PRODUCCION c inner join personal p on p.COD_PERSONAL = c.COD_PERSONAL "
                    + " inner join TIPOS_MATERIAL t on t.COD_TIPO_MATERIAL = c.COD_TIPO_MATERIAL "
                    + " inner join TIPOS_SALIDA_ALMACEN_PRODUCCION tsa on tsa.COD_TIPO_SALIDA_ALMACEN_PRODUCCION = c.COD_TIPO_SALIDA_ALMACEN_PRODUCCION  ";
            System.out.println("consulta " + consulta);
            ResultSet rs = st.executeQuery(consulta);
            configuracionSalidasMaterialProduccionList.clear();
            while (rs.next()) {
                ConfiguracionSalidaAlmacenProduccion configuracionSalidaAlmacenProduccion = new ConfiguracionSalidaAlmacenProduccion();
                configuracionSalidaAlmacenProduccion.getTiposMaterial().setCodTipoMaterial(rs.getInt("COD_TIPO_MATERIAL"));
                configuracionSalidaAlmacenProduccion.getTiposMaterial().setNombreTipoMaterial(rs.getString("NOMBRE_TIPO_MATERIAL"));
                configuracionSalidaAlmacenProduccion.getTiposSalidaAlmacenProduccion().setCodTipoSalidaAlmacenProduccion(rs.getInt("COD_TIPO_SALIDA_ALMACEN_PRODUCCION"));
                configuracionSalidaAlmacenProduccion.getTiposSalidaAlmacenProduccion().setNombreTipoSalidaAlmacenProduccion(rs.getString("NOMBRE_TIPO_SALIDA_ALMACEN_PRODUCCION"));
                configuracionSalidaAlmacenProduccion.getPersonal().setNombrePersonal(rs.getString("NOMBRE_PILA"));
                configuracionSalidaAlmacenProduccion.getPersonal().setApPaternoPersonal(rs.getString("AP_PATERNO_PERSONAL"));
                configuracionSalidaAlmacenProduccion.getPersonal().setApMaternoPersonal(rs.getString("AP_MATERNO_PERSONAL"));
                configuracionSalidaAlmacenProduccion.getPersonal().setCodPersonal(rs.getString("COD_PERSONAL"));
                //configuracionSalidaAlmacenProduccion.getTiposSalidaAlmacenProduccionList().add(new SelectItem("1","conjunta"));
                //configuracionSalidaAlmacenProduccion.getTiposSalidaAlmacenProduccionList().add(new SelectItem("2","por tipo"));
                //this.cargarConfiguracionTipoSalidaAlmacenProduccion(configuracionSalidaAlmacenProduccion);
                configuracionSalidasMaterialProduccionList.add(configuracionSalidaAlmacenProduccion);
            }
            rs.close();
            st.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List cargarPersonalList() {
        List personalList = new ArrayList();
        try {
            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String consulta = " select distinct p.COD_PERSONAL,p.NOMBRE_PILA,p.AP_PATERNO_PERSONAL,p.AP_MATERNO_PERSONAL "
                    + " from USUARIOS_MODULOS u inner join personal p on p.COD_PERSONAL = u.COD_PERSONAL "
                    + " where u.COD_MODULO in (2,6)  ";
            ResultSet rs = st.executeQuery(consulta);
            while (rs.next()) {
                personalList.add(new SelectItem(rs.getString("COD_PERSONAL"), rs.getString("NOMBRE_PILA") + " " + rs.getString("AP_PATERNO_PERSONAL") + " " + rs.getString("AP_MATERNO_PERSONAL")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return personalList;
    }

    public List cargarTiposMaterialList() {
        List tiposMaterialList = new ArrayList();
        try {
            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String consulta = " select t.COD_TIPO_MATERIAL,t.NOMBRE_TIPO_MATERIAL from TIPOS_MATERIAL t ";
            ResultSet rs = st.executeQuery(consulta);
            while (rs.next()) {
                tiposMaterialList.add(new SelectItem(rs.getString("COD_TIPO_MATERIAL"), rs.getString("NOMBRE_TIPO_MATERIAL")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return tiposMaterialList;
    }

    public List cargarTiposSalidaMaterialList() {
        List tiposSalidaMaterialList = new ArrayList();
        try {
            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String consulta = " select t.COD_TIPO_SALIDA_ALMACEN_PRODUCCION,t.NOMBRE_TIPO_SALIDA_ALMACEN_PRODUCCION from TIPOS_SALIDA_ALMACEN_PRODUCCION t where t.COD_TIPO_SALIDA_ALMACEN_PRODUCCION =1";
            ResultSet rs = st.executeQuery(consulta);
            while (rs.next()) {
                tiposSalidaMaterialList.add(new SelectItem(rs.getString("COD_TIPO_SALIDA_ALMACEN_PRODUCCION"), rs.getString("NOMBRE_TIPO_SALIDA_ALMACEN_PRODUCCION")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return tiposSalidaMaterialList;
    }

    public String guardarConfiguracionTiposSalidaAlmacenProduccion_action() {
        try {
            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String consulta = " INSERT INTO CONFIGURACION_SALIDA_ALMACEN_PRODUCCION( COD_PERSONAL,  COD_TIPO_MATERIAL,  COD_TIPO_SALIDA_ALMACEN_PRODUCCION) "
                    + " VALUES ( '" + configuracionSalidaAlmacenProduccion.getPersonal().getCodPersonal() + "', '" + configuracionSalidaAlmacenProduccion.getTiposMaterial().getCodTipoMaterial() + "',  '" + configuracionSalidaAlmacenProduccion.getTiposSalidaAlmacenProduccion().getCodTipoSalidaAlmacenProduccion() + "'); ";
            st.executeUpdate(consulta);
            st.close();
            con.close();
            this.cargarConfiguracionSalidaAlmacenProduccion();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String eliminarConfiguracionTiposSalidaAlmacenProduccion_action() {
        try {
            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            Iterator i = configuracionSalidasMaterialProduccionList.iterator();
            ConfiguracionSalidaAlmacenProduccion configuracionSalidaAlmacenProduccion = new ConfiguracionSalidaAlmacenProduccion();
            while (i.hasNext()) {
                configuracionSalidaAlmacenProduccion = (ConfiguracionSalidaAlmacenProduccion) i.next();
                if (configuracionSalidaAlmacenProduccion.getChecked().booleanValue() == true) {
                    String consulta = " DELETE FROM CONFIGURACION_SALIDA_ALMACEN_PRODUCCION WHERE  COD_PERSONAL = '" + configuracionSalidaAlmacenProduccion.getPersonal().getCodPersonal() + "' "
                            + " and  COD_TIPO_MATERIAL='" + configuracionSalidaAlmacenProduccion.getTiposMaterial().getCodTipoMaterial() + "' "
                            + " and  COD_TIPO_SALIDA_ALMACEN_PRODUCCION='" + configuracionSalidaAlmacenProduccion.getTiposSalidaAlmacenProduccion().getCodTipoSalidaAlmacenProduccion() + "' ";
                    System.out.println("consulta " + consulta);
                    st.executeUpdate(consulta);
                    st.close();
                    con.close();
                }
            }

            this.cargarConfiguracionSalidaAlmacenProduccion();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void cargarConfiguracionTipoSalidaAlmacenProduccion(ConfiguracionSalidaAlmacenProduccion configuracionSalidaAlmacenProduccion) {
        try {
            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String consulta = " select t.COD_TIPO_SALIDA_ALMACEN_PRODUCCION,t.NOMBRE_TIPO_SALIDA_ALMACEN_PRODUCCION,c.COD_TIPO_SALIDA_ALMACEN_PRODUCCION COD_TIPO_SALIDA_ALMACEN_PRODUCCION1 "
                    + " from TIPOS_SALIDA_ALMACEN_PRODUCCION t left outer join  "
                    + " CONFIGURACION_SALIDA_ALMACEN_PRODUCCION c on t.COD_TIPO_SALIDA_ALMACEN_PRODUCCION = c.COD_TIPO_SALIDA_ALMACEN_PRODUCCION "
                    + " and c.COD_TIPO_MATERIAL = '' and c.COD_TIPO_SALIDA_ALMACEN_PRODUCCION ='' ";
            ResultSet rs = st.executeQuery(consulta);
            while (rs.next()) {
                //configuracionSalidaAlmacenProduccion.getTiposSalidaAlmacenProduccionList().add(new SelectItem(rs.getString("COD_TIPO_SALIDA_ALMACEN_PRODUCCION"),rs.getString("NOMBRE_TIPO_SALIDA_ALMACEN_PRODUCCION")));
                if (rs.getInt("COD_TIPO_SALIDA_ALMACEN_PRODUCCION1") > 0) { //existe configuracion
                    configuracionSalidaAlmacenProduccion.getTiposSalidaAlmacenProduccion().setCodTipoSalidaAlmacenProduccion(rs.getInt("COD_TIPO_SALIDA_ALMACEN_PRODUCCION"));
                    // configuracionSalidaAlmacenProduccion.getTiposSalidaAlmacenProduccionRef().setCodTipoSalidaAlmacenProduccion(rs.getInt("COD_TIPO_SALIDA_ALMACEN_PRODUCCION"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String guardarTipoSalidaAlmacenProduccion_action() {
        try {
            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            Iterator i = configuracionSalidasMaterialProduccionList.iterator();
            String consulta = " delete from configuracion_salida_almacen_produccion ";
            System.out.println("consulta " + consulta);
            st.executeUpdate(consulta);
            while (i.hasNext()) {
                ConfiguracionSalidaAlmacenProduccion configuracionSalidaAlmacenProduccion = (ConfiguracionSalidaAlmacenProduccion) i.next();
                consulta = " INSERT INTO CONFIGURACION_SALIDA_ALMACEN_PRODUCCION( COD_TIPO_MATERIAL, COD_TIPO_SALIDA_ALMACEN_PRODUCCION) "
                        + " VALUES ('" + configuracionSalidaAlmacenProduccion.getTiposMaterial().getCodTipoMaterial() + "','" + configuracionSalidaAlmacenProduccion.getTiposSalidaAlmacenProduccion().getCodTipoSalidaAlmacenProduccion() + "'   ); ";
                System.out.println("consulta " + consulta);
                st.executeUpdate(consulta);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public String getCargarListadoProgramaProduccion(){
        this.cargarPermisosEspecialesUsuario();
        setCodProgramaProd(programaProduccionbean.getCodProgramaProduccion());
        this.cargarPermisoPersonal();
        try {
            tiposSalidasMaterialList = this.cargarTiposSalidaMaterialList();
            configuracionSalidaMaterialProduccionPersonaList = this.cargarConfiguracionSalidaAlmacenProduccionPersona();
            for (Object dato : configuracionSalidaMaterialProduccionPersonaList) {
                ConfiguracionSalidaAlmacenProduccion valor = (ConfiguracionSalidaAlmacenProduccion) dato;
                if (valor.getTiposMaterial().getCodTipoMaterial() == 1) {
                    habilita_materiaPrimaSalidaProduccion = true;
                }
            }
        } catch (Exception ex) {
            LOGGER.warn("error", ex);
        }
        this.listadoProgramaProduccion();
        return null;
    }

    public String seleccionarTipoSalidaAlmacen() {
        try {
            if (configuracionSalidaAlmacenProduccion.getTiposSalidaAlmacenProduccion().getCodTipoSalidaAlmacenProduccion() == 1) {
                //this.getCargarContenidoAgregarSalidaAlmacenProgramaProduccion();}
                //this.getCargarContenidoProgramaProduccion();
                salidasAlmacenConDesviacion = 0;
                this.listadoProgramaProduccion();
            } else {
                String codProgramaProdF = Util.getParameter("codProgramaProd");
                if (codProgramaProdF != null) {
                    programaProduccionbean.setCodProgramaProduccion(codProgramaProdF);
                    setCodProgramaProd(codProgramaProdF);
                }
                //cargar para tipo de salida agrupado por lote de produccion
                this.cargarProgramaProduccionPorLoteProduccion();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getCargarProgramaProduccionDesviacion() {
        this.cargarPermisoPersonal();
        try {
            configuracionSalidaAlmacenProduccion.getTiposSalidaAlmacenProduccion().setCodTipoSalidaAlmacenProduccion(2);//salida automatica
            salidasAlmacenConDesviacion = 1;
            this.listadoProgramaProduccion();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void cargarProgramaProduccionPorLoteProduccion() {
        try {

            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement();
            String consulta = " select ppr.cod_programa_prod,cp.COD_COMPPROD,cp.nombre_prod_semiterminado,sum(ppr.CANT_LOTE_PRODUCCION) CANT_LOTE_PRODUCCION,ppr.COD_LOTE_PRODUCCION,"
                    + " ae.NOMBRE_AREA_EMPRESA,ae.COD_AREA_EMPRESA,e.NOMBRE_ESTADO_PROGRAMA_PROD,ppr.cod_formula_maestra,ppr.COD_FORMULA_MAESTRA_VERSION"
                    + " from programa_produccion ppr "
                    + " inner join COMPONENTES_PROD cp on cp.COD_COMPPROD = ppr.COD_COMPPROD "
                    + " inner join AREAS_EMPRESA ae on ae.COD_AREA_EMPRESA = cp.COD_AREA_EMPRESA "
                    + " inner join ESTADOS_PROGRAMA_PRODUCCION e on e.COD_ESTADO_PROGRAMA_PROD = ppr.COD_ESTADO_PROGRAMA "
                    + " inner join TIPOS_PROGRAMA_PRODUCCION tppr on tppr.COD_TIPO_PROGRAMA_PROD = ppr.COD_TIPO_PROGRAMA_PROD "
                    + " where ppr.COD_ESTADO_PROGRAMA in (1,2,6,7) ";
            if (!codProgramaProd.equals("")) {
                consulta += " and ppr.cod_programa_prod=" + codProgramaProd;
            }
            if (!programaProduccionBuscar.getCodLoteProduccion().equals("")) {
                consulta += " and ppr.cod_lote_produccion='" + programaProduccionBuscar.getCodLoteProduccion() + "'";
            }
            consulta += " group by  cp.COD_COMPPROD,cp.nombre_prod_semiterminado,ppr.COD_LOTE_PRODUCCION, "
                    + " ae.NOMBRE_AREA_EMPRESA,ae.COD_AREA_EMPRESA,e.NOMBRE_ESTADO_PROGRAMA_PROD,ppr.cod_formula_maestra,ppr.cod_programa_prod,ppr.COD_FORMULA_MAESTRA_VERSION";
            System.out.println("consulta " + consulta);
            ResultSet rs = st.executeQuery(consulta);
            programaProduccionList.clear();
            while (rs.next()) {
                ProgramaProduccion programaProduccion = new ProgramaProduccion();
                programaProduccion.setCodFormulaMaestraVersion(rs.getInt("COD_FORMULA_MAESTRA_VERSION"));
                programaProduccion.getFormulaMaestra().getComponentesProd().setCodCompprod(rs.getString("COD_COMPPROD"));
                programaProduccion.getFormulaMaestra().getComponentesProd().setNombreProdSemiterminado(rs.getString("nombre_prod_semiterminado"));
                programaProduccion.setCantidadLote(rs.getString("CANT_LOTE_PRODUCCION"));
                programaProduccion.setCodLoteProduccion(rs.getString("COD_LOTE_PRODUCCION"));
                programaProduccion.getFormulaMaestra().getComponentesProd().getAreasEmpresa().setNombreAreaEmpresa(rs.getString("NOMBRE_AREA_EMPRESA"));
                programaProduccion.getFormulaMaestra().getComponentesProd().getAreasEmpresa().setCodAreaEmpresa(rs.getString("COD_AREA_EMPRESA"));
                programaProduccion.getEstadoProgramaProduccion().setNombreEstadoProgramaProd(rs.getString("NOMBRE_ESTADO_PROGRAMA_PROD"));
                programaProduccion.getFormulaMaestra().setCodFormulaMaestra(rs.getString("cod_formula_maestra"));
                programaProduccion.setCodProgramaProduccion(rs.getString("cod_programa_prod"));
                programaProduccionList.add(programaProduccion);
            }
            st.close();
            rs.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    public String getCargarContenidoProgramaProduccionPeriodo() {
        programaProduccionBuscar = new ProgramaProduccion();
        this.cargarProgramaProduccionPeriodoList();
        return null;
    }

    public String buscarProgramaProduccion_action() {
        this.listadoProgramaProduccion();
        return null;
    }

    public boolean isAdministradorAlmacen() {
        return administradorAlmacen;
    }

    public void setAdministradorAlmacen(boolean administradorAlmacen) {
        this.administradorAlmacen = administradorAlmacen;
    }

    private boolean selectES = true;

    public boolean isSelectES() {
        return selectES;
    }

    public void setSelectES(boolean selectES) {
        this.selectES = selectES;
    }

    public String onSelectES() {
        System.out.println("Click on select ESs");
        List empaqueSecundarioListT = new ArrayList();
        for (Object data : empaqueSecundarioList) {
            FormulaMaestraDetalleES reg = (FormulaMaestraDetalleES) data;
            reg.setChecked(selectES);
            empaqueSecundarioListT.add(reg);
        }
        empaqueSecundarioList = empaqueSecundarioListT;
        return null;
    }

    private String mensaje;

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String explosionEmpaqueSecundarioAlmacen_action() throws SQLException {
        mensaje = "";
        try {
            con = Util.openConnection(con);
            con.setAutoCommit(false);
            ManagedAccesoSistema managed = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");

            System.out.println("usuario J: " + managed.getUsuarioModuloBean().getCodUsuarioGlobal());
            StringBuilder consulta = new StringBuilder("INSERT INTO EXPLOSION_EMPAQUE_SECUNDARIO_ALMACEN(COD_PROGRAMA_PROD,FECHA_REGISTRO, COD_PERSONAL_REGISTRO)");
            consulta.append(" VALUES (");
            consulta.append(programaProduccionbean.getCodProgramaProduccion()).append(",");
            consulta.append("getdate(),");
            consulta.append(managed.getUsuarioModuloBean().getCodUsuarioGlobal());
            consulta.append(")");
            System.out.println("consulta registrar explosion es " + consulta.toString());
            PreparedStatement pst = con.prepareStatement(consulta.toString(), PreparedStatement.RETURN_GENERATED_KEYS);
            if (pst.executeUpdate() > 0) {
                System.out.println("se registro la explosion de es");
            }
            ResultSet res = pst.getGeneratedKeys();
            res.next();
            codExplosionEmpaqueSecundarioAlmacen = res.getInt(1);
            consulta = new StringBuilder("INSERT INTO EXPLOSION_EMPAQUE_SECUNDARIO_ALMACEN_DETALLE(COD_EXPLOSION_EMPAQUE_SECUNDARIO_ALMACEN, COD_LOTE_PRODUCCION, COD_COMPPROD,COD_TIPO_PROGRAMA_PROD)");
            consulta.append("VALUES (");
            consulta.append(res.getInt(1)).append(",");
            consulta.append("?,");//lote de produccion
            consulta.append("?,");//cod compprod
            consulta.append("?");//cod tipo programa prod
            consulta.append(")");
            System.out.println("consulta registrar detalle " + consulta.toString());
            pst = con.prepareStatement(consulta.toString());
            for (ProgramaProduccion bean : programaProduccionList) {
                if (bean.getChecked()) {
                    pst.setString(1, bean.getCodLoteProduccion());
                    System.out.println("p1:" + bean.getCodLoteProduccion());
                    pst.setInt(2, Integer.valueOf(bean.getFormulaMaestra().getComponentesProd().getCodCompprod()));
                    System.out.println("p2:" + bean.getFormulaMaestra().getComponentesProd().getCodCompprod());
                    pst.setInt(3, Integer.valueOf(bean.getTiposProgramaProduccion().getCodTipoProgramaProd()));
                    System.out.println("p3:" + bean.getTiposProgramaProduccion().getCodTipoProgramaProd());
                    pst.executeUpdate();
                }
            }
            con.commit();
            mensaje = "1";
        } catch (SQLException ex) {
            mensaje = "Ocurrio un error al momento de guardar el registro";
            System.out.println(ex.getMessage());
            con.rollback();
        } catch (Exception ex) {
            mensaje = "Ocurrio un error al momento de guardar el registro,verifique los datos introducidos";
            System.out.println(ex.getMessage());
        } finally {
            con.close();
        }
        return null;
    }
    
    public String generarSolicitudSalidaAlmacen_action() throws SQLException {
        mensaje = "";
        try {
            con = Util.openConnection(con);
            con.setAutoCommit(false);
            ManagedAccesoSistema managed = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");

            StringBuilder consulta = new StringBuilder("INSERT INTO EXPLOSION_EMPAQUE_SECUNDARIO_ALMACEN(COD_PROGRAMA_PROD,FECHA_REGISTRO, COD_PERSONAL_REGISTRO)");
            consulta.append(" VALUES (");
            consulta.append(programaProduccionbean.getCodProgramaProduccion()).append(",");
            consulta.append("getdate(),");
            consulta.append(managed.getUsuarioModuloBean().getCodUsuarioGlobal());
            consulta.append(")");
            System.out.println("consulta registrar explosion:" + consulta.toString());
            PreparedStatement pst = con.prepareStatement(consulta.toString(), PreparedStatement.RETURN_GENERATED_KEYS);
            if (pst.executeUpdate() > 0) {
                System.out.println("se registro la explosion de es");
            }
            ResultSet res = pst.getGeneratedKeys();
            res.next();
            codExplosionEmpaqueSecundarioAlmacen = res.getInt(1);
            consulta = new StringBuilder("INSERT INTO EXPLOSION_EMPAQUE_SECUNDARIO_ALMACEN_DETALLE(COD_EXPLOSION_EMPAQUE_SECUNDARIO_ALMACEN, COD_LOTE_PRODUCCION, COD_COMPPROD,COD_TIPO_PROGRAMA_PROD)");
            consulta.append("VALUES (");
            consulta.append(codExplosionEmpaqueSecundarioAlmacen).append(",");
            consulta.append("?,");//lote de produccion
            consulta.append("?,");//cod compprod
            consulta.append("?");//cod tipo programa prod
            consulta.append(")");
            System.out.println("consulta registrar detalle " + consulta.toString());
            pst = con.prepareStatement(consulta.toString());
            for (ProgramaProduccion bean : programaProduccionList) {
                if (bean.getChecked()) {
                    pst.setString(1, bean.getCodLoteProduccion());
                    System.out.println("p1:" + bean.getCodLoteProduccion());
                    pst.setInt(2, Integer.valueOf(bean.getFormulaMaestra().getComponentesProd().getCodCompprod()));
                    System.out.println("p2:" + bean.getFormulaMaestra().getComponentesProd().getCodCompprod());
                    pst.setInt(3, Integer.valueOf(bean.getTiposProgramaProduccion().getCodTipoProgramaProd()));
                    System.out.println("p3:" + bean.getTiposProgramaProduccion().getCodTipoProgramaProd());
                    pst.executeUpdate();
                }
            }
            con.commit();
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            Map<String, Object> sessionMap = externalContext.getSessionMap();
            sessionMap.put("codExplosionEmpaqueSecundarioAlmacen", codExplosionEmpaqueSecundarioAlmacen);
            
        } catch (Exception ex) {
            con.rollback();
            mensaje = "Ocurrio un error al momento de guardar el registro,verifique los datos introducidos";
            System.out.println(ex.getMessage());
        } finally {
            con.close();
        }
        return null;
    }
    
    

    private int codExplosionEmpaqueSecundarioAlmacen;

    public int getCodExplosionEmpaqueSecundarioAlmacen() {
        return codExplosionEmpaqueSecundarioAlmacen;
    }

    public void setCodExplosionEmpaqueSecundarioAlmacen(int codExplosionEmpaqueSecundarioAlmacen) {
        this.codExplosionEmpaqueSecundarioAlmacen = codExplosionEmpaqueSecundarioAlmacen;
    }

    public String generarSolicitudSalidasAlmacenTraspasoExplosionMateriales_action() {
        try {
            explosionEmpaqueSecundarioAlmacen_action();
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            Map<String, Object> sessionMap = externalContext.getSessionMap();
            sessionMap.put("programaProduccion", programaProduccion);
            sessionMap.put("cod_explosion_empaque_secundario", codExplosionEmpaqueSecundarioAlmacen);

            Utiles.direccionar("agregarSolicitudSalidaAlmacenTraspaso.jsf");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean habilita_materiaPrimaSalidaProduccion = false;

    public boolean isHabilita_materiaPrimaSalidaProduccion() {
        return habilita_materiaPrimaSalidaProduccion;
    }

    public void setHabilita_materiaPrimaSalidaProduccion(boolean habilita_materiaPrimaSalidaProduccion) {
        this.habilita_materiaPrimaSalidaProduccion = habilita_materiaPrimaSalidaProduccion;
    }
    private String mensajeMP = "";

    //<editor-fold defaultstate="collapsed" desc="getter and setter">

    public boolean isSalidaMateriaPrima() {
        return salidaMateriaPrima;
    }

    public void setSalidaMateriaPrima(boolean salidaMateriaPrima) {
        this.salidaMateriaPrima = salidaMateriaPrima;
    }

    public boolean isSolicitudSalida() {
        return solicitudSalida;
    }

    public void setSolicitudSalida(boolean solicitudSalida) {
        this.solicitudSalida = solicitudSalida;
    }

    public boolean isSalidaEmpaquePrimarioSecundario() {
        return salidaEmpaquePrimarioSecundario;
    }

    public void setSalidaEmpaquePrimarioSecundario(boolean salidaEmpaquePrimarioSecundario) {
        this.salidaEmpaquePrimarioSecundario = salidaEmpaquePrimarioSecundario;
    }
    
    

       

    public boolean isPermisoSolicitudMateriaPrima() {
        return permisoSolicitudMateriaPrima;
    }

    public void setPermisoSolicitudMateriaPrima(boolean permisoSolicitudMateriaPrima) {
        this.permisoSolicitudMateriaPrima = permisoSolicitudMateriaPrima;
    }

    public boolean isPermisoSolicitudEmpaquePrimario() {
        return permisoSolicitudEmpaquePrimario;
    }

    public void setPermisoSolicitudEmpaquePrimario(boolean permisoSolicitudEmpaquePrimario) {
        this.permisoSolicitudEmpaquePrimario = permisoSolicitudEmpaquePrimario;
    }

    public boolean isPermisoSolicitudEmpaqueSecundario() {
        return permisoSolicitudEmpaqueSecundario;
    }

    public void setPermisoSolicitudEmpaqueSecundario(boolean permisoSolicitudEmpaqueSecundario) {
        this.permisoSolicitudEmpaqueSecundario = permisoSolicitudEmpaqueSecundario;
    }
        


    
    
        public String getMensajeMP() {
            return mensajeMP;
        }

        public boolean isPermisoSalidaMateriaPrima() {
            return permisoSalidaMateriaPrima;
        }

        public void setPermisoSalidaMateriaPrima(boolean permisoSalidaMateriaPrima) {
            this.permisoSalidaMateriaPrima = permisoSalidaMateriaPrima;
        }

        public boolean isPermisoSalidaEmpaquePrimario() {
            return permisoSalidaEmpaquePrimario;
        }

        public void setPermisoSalidaEmpaquePrimario(boolean permisoSalidaEmpaquePrimario) {
            this.permisoSalidaEmpaquePrimario = permisoSalidaEmpaquePrimario;
        }

        public boolean isPermisoSalidaEmpaqueSecundario() {
            return permisoSalidaEmpaqueSecundario;
        }

        public void setPermisoSalidaEmpaqueSecundario(boolean permisoSalidaEmpaqueSecundario) {
            this.permisoSalidaEmpaqueSecundario = permisoSalidaEmpaqueSecundario;
        }

        public void setMensajeMP(String mensajeMP) {
            this.mensajeMP = mensajeMP;
        }
    //</editor-fold>
    

}
