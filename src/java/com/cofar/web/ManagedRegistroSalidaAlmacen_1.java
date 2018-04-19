/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.web;

import com.cofar.bean.Equivalencias;
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
import com.cofar.util.Util;
import com.cofar.util.Utiles;
import java.sql.Connection;
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
import org.richfaces.component.html.HtmlDataTable;

/**
 *
 * @author hvaldivia
 */

public class ManagedRegistroSalidaAlmacen_1 extends ManagedBean{
    
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
    private Materiales buscarMaterial = new  Materiales();
    private List materialesBuscarList = new ArrayList();
    private HtmlDataTable materialesBuscarDataTable = new HtmlDataTable();
    List estadosMaterialList = new ArrayList();
    IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstado = new IngresosAlmacenDetalleEstado();
    List salidasAlmacenDetalleAuxList = new ArrayList(); //esta lista almacenara los items que se editaran si se llega a guardar la edicion se tienen que restaurar las cantidades en los ingresos afectados
    //inicio ale
    private OrdenesCompra filtroOrdenCompra= new OrdenesCompra();
    private List<SelectItem> proveedoresList= new ArrayList<SelectItem>();
    private List<SelectItem> representantesList= new ArrayList<SelectItem>();
    private List<SelectItem> personalList= new ArrayList<SelectItem>();
    private Date fechaInicio= new Date();
    private Date fechaFinal= new Date();
   // private Materiales filtroMaterial= new Materiales();
    private List<OrdenesCompra> ordenesCompraList= new ArrayList<OrdenesCompra>();
    private List<OrdenesCompraDetalle> ordenesCompraDetalleList= new ArrayList<OrdenesCompraDetalle>();
    private String codCapitulo="0";
    private String codGrupo="0";
    private String codMaterial="0";
    private HtmlDataTable ordenesCompraDataTable= new HtmlDataTable();
    private boolean productoHabilitado=false;
    //final ale

    
    

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

    public List getTiposSalidasAlmacenList() {
        return tiposSalidasAlmacenList;
    }

    public void setTiposSalidasAlmacenList(List tiposSalidasAlmacenList) {
        this.tiposSalidasAlmacenList = tiposSalidasAlmacenList;
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

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
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

    public OrdenesCompra getFiltroOrdenCompra() {
        return filtroOrdenCompra;
    }

    public void setFiltroOrdenCompra(OrdenesCompra filtroOrdenCompra) {
        this.filtroOrdenCompra = filtroOrdenCompra;
    }

    public HtmlDataTable getOrdenesCompraDataTable() {
        return ordenesCompraDataTable;
    }

    public void setOrdenesCompraDataTable(HtmlDataTable ordenesCompraDataTable) {
        this.ordenesCompraDataTable = ordenesCompraDataTable;
    }

    public List<OrdenesCompraDetalle> getOrdenesCompraDetalleList() {
        return ordenesCompraDetalleList;
    }

    public void setOrdenesCompraDetalleList(List<OrdenesCompraDetalle> ordenesCompraDetalleList) {
        this.ordenesCompraDetalleList = ordenesCompraDetalleList;
    }

    public List<OrdenesCompra> getOrdenesCompraList() {
        return ordenesCompraList;
    }

    public void setOrdenesCompraList(List<OrdenesCompra> ordenesCompraList) {
        this.ordenesCompraList = ordenesCompraList;
    }

    public List<SelectItem> getPersonalList() {
        return personalList;
    }

    public void setPersonalList(List<SelectItem> personalList) {
        this.personalList = personalList;
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

    public boolean isProductoHabilitado() {
        return productoHabilitado;
    }

    public void setProductoHabilitado(boolean productoHabilitado) {
        this.productoHabilitado = productoHabilitado;
    }
    

    
    
    
    
    /** Creates a new instance of ManagedRegistroSalidaAlmacen */
    public ManagedRegistroSalidaAlmacen_1() {
    }
    public String getCargarContenidoRegistroSalidaAlmacen(){
        try {
            ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");


            setProductoHabilitado(false);
            salidasAlmacen = new SalidasAlmacen();
            salidasAlmacen.setNroSalidaAlmacen(this.generaNroIngresoAlmacen());
            salidasAlmacen.getGestiones().setCodGestion(usuario.getGestionesGlobal().getCodGestion());
            salidasAlmacen.getPersonal().setCodPersonal(usuario.getUsuarioModuloBean().getCodUsuarioGlobal());
            salidasAlmacen.getAlmacenes().setCodAlmacen(usuario.getAlmacenesGlobal().getCodAlmacen());
            salidasAlmacen.getEstadosSalidasAlmacen().setCodEstadoSalidaAlmacen(1);
            salidasAlmacen.setEstadoSistema(1);
            salidasAlmacen.getTiposSalidasAlmacen().setCodTipoSalidaAlmacen(1);//solicitud manual
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
            ProgramaProduccion programaProduccion =(ProgramaProduccion)sessionMap.get("programaProduccion");
            List materiaPrimaList = (ArrayList)sessionMap.get("materiaPrimaList");
            List empaquePrimarioList = (ArrayList)sessionMap.get("empaquePrimarioList");
            List empaqueSecundarioList = (ArrayList)sessionMap.get("empaqueSecundarioList");
            
            
            if(programaProduccion!=null){
                ingresosAlmacenDetalleEstado.getEstadosMaterial().setCodEstadoMaterial(2); //estado aprobado
                salidasAlmacen.setCodLoteProduccion(programaProduccion.getCodLoteProduccion());
                System.out.println("los valores comp prod" + programaProduccion.getFormulaMaestra().getComponentesProd().getCodCompprod());
                salidasAlmacen.getComponentesProd().setCodCompprod(programaProduccion.getFormulaMaestra().getComponentesProd().getCodCompprod());
                salidasAlmacen.setAreasEmpresa(programaProduccion.getFormulaMaestra().getComponentesProd().getAreasEmpresa());
                this.cargaSalidasAlmacenProgramaProduccion(programaProduccion,materiaPrimaList,empaquePrimarioList,empaqueSecundarioList);
                this.producto_changed();
                tiposSalidasAlmacenList = this.cargarTiposSalidasAlmacenProduccion();
                sessionMap.put("programaProduccion",null);
            }
            

            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void cargaSalidasAlmacenProgramaProduccion(ProgramaProduccion programaProduccion,List materiaPrimaList,List empaquePrimarioList,List empaqueSecundarioList){
        try {
            salidasAlmacenDetalleList.clear();
            Iterator i = materiaPrimaList.iterator();
            while(i.hasNext()){
                FormulaMaestraDetalleMP formulaMaestraDetalleMP = (FormulaMaestraDetalleMP) i.next();
                if(formulaMaestraDetalleMP.getChecked().booleanValue()==true){
                SalidasAlmacenDetalle salidasAlmacenDetalleItem = new SalidasAlmacenDetalle();
                salidasAlmacenDetalleItem.getMateriales().setCodMaterial(formulaMaestraDetalleMP.getMateriales().getCodMaterial());
                salidasAlmacenDetalleItem.getMateriales().setNombreMaterial(formulaMaestraDetalleMP.getMateriales().getNombreMaterial());
                salidasAlmacenDetalleItem.setCantidadSalidaAlmacen(formulaMaestraDetalleMP.getCantidad().floatValue());
                salidasAlmacenDetalleItem.getUnidadesMedida().setCodUnidadMedida(formulaMaestraDetalleMP.getUnidadesMedida().getCodUnidadMedida());
                salidasAlmacenDetalleItem.getUnidadesMedida().setAbreviatura(formulaMaestraDetalleMP.getUnidadesMedida().getAbreviatura());                
                this.etiquetasSalidaAlmacen(salidasAlmacenDetalleItem);
                salidasAlmacenDetalleItem.setEquivalencias(this.buscaEquivalencia(salidasAlmacenDetalleItem));
                salidasAlmacenDetalleItem.getEstadosMaterial().setCodEstadoMaterial(2); //estado aprobado
                if(salidasAlmacenDetalleItem.getCantidadSalidaAlmacen()>salidasAlmacenDetalleItem.getCantidadDisponible()){
                    salidasAlmacenDetalleItem.setColorFila("#FF0000");
                }else{
                    salidasAlmacenDetalleItem.setColorFila("#00FF00");
                }
                this.detalleEtiquetasSalidaAlmacen(salidasAlmacenDetalleItem);

                salidasAlmacenDetalleList.add(salidasAlmacenDetalleItem);
                }
            }
            i = empaquePrimarioList.iterator();
            
            while(i.hasNext()){
                FormulaMaestraDetalleEP formulaMaestraDetalleEP = (FormulaMaestraDetalleEP) i.next();
                if(formulaMaestraDetalleEP.getChecked().booleanValue()==true){
                SalidasAlmacenDetalle salidasAlmacenDetalleItem = new SalidasAlmacenDetalle();
                salidasAlmacenDetalleItem.getMateriales().setCodMaterial(formulaMaestraDetalleEP.getMateriales().getCodMaterial());
                salidasAlmacenDetalleItem.getMateriales().setNombreMaterial(formulaMaestraDetalleEP.getMateriales().getNombreMaterial());
                salidasAlmacenDetalleItem.setCantidadSalidaAlmacen(formulaMaestraDetalleEP.getCantidad());
                salidasAlmacenDetalleItem.getUnidadesMedida().setCodUnidadMedida(formulaMaestraDetalleEP.getUnidadesMedida().getCodUnidadMedida());
                salidasAlmacenDetalleItem.getUnidadesMedida().setAbreviatura(formulaMaestraDetalleEP.getUnidadesMedida().getAbreviatura());
                this.etiquetasSalidaAlmacen(salidasAlmacenDetalleItem);
                salidasAlmacenDetalleItem.setEquivalencias(this.buscaEquivalencia(salidasAlmacenDetalleItem));
                salidasAlmacenDetalleItem.getEstadosMaterial().setCodEstadoMaterial(2); //estado aprobado
                if(salidasAlmacenDetalleItem.getCantidadSalidaAlmacen()>salidasAlmacenDetalleItem.getCantidadDisponible()){
                    salidasAlmacenDetalleItem.setColorFila("#FF0000");
                }else{
                    salidasAlmacenDetalleItem.setColorFila("#00FF00");
                }
                this.detalleEtiquetasSalidaAlmacen(salidasAlmacenDetalleItem);

                salidasAlmacenDetalleList.add(salidasAlmacenDetalleItem);
                }
            }

            i = empaqueSecundarioList.iterator();

            while(i.hasNext()){
                FormulaMaestraDetalleES formulaMaestraDetalleES = (FormulaMaestraDetalleES) i.next();
                if(formulaMaestraDetalleES.getChecked().booleanValue()==true){
                SalidasAlmacenDetalle salidasAlmacenDetalleItem = new SalidasAlmacenDetalle();
                salidasAlmacenDetalleItem.getMateriales().setCodMaterial(formulaMaestraDetalleES.getMateriales().getCodMaterial());
                salidasAlmacenDetalleItem.getMateriales().setNombreMaterial(formulaMaestraDetalleES.getMateriales().getNombreMaterial());
                salidasAlmacenDetalleItem.setCantidadSalidaAlmacen(formulaMaestraDetalleES.getCantidad());
                salidasAlmacenDetalleItem.getUnidadesMedida().setCodUnidadMedida(formulaMaestraDetalleES.getUnidadesMedida().getCodUnidadMedida());
                salidasAlmacenDetalleItem.getUnidadesMedida().setAbreviatura(formulaMaestraDetalleES.getUnidadesMedida().getAbreviatura());
                this.etiquetasSalidaAlmacen(salidasAlmacenDetalleItem);
                salidasAlmacenDetalleItem.setEquivalencias(this.buscaEquivalencia(salidasAlmacenDetalleItem));
                salidasAlmacenDetalleItem.getEstadosMaterial().setCodEstadoMaterial(2); //estado aprobado
                if(salidasAlmacenDetalleItem.getCantidadSalidaAlmacen()>salidasAlmacenDetalleItem.getCantidadDisponible()){
                    salidasAlmacenDetalleItem.setColorFila("#FF0000");
                }else{
                    salidasAlmacenDetalleItem.setColorFila("#00FF00");
                }
                this.detalleEtiquetasSalidaAlmacen(salidasAlmacenDetalleItem);

                salidasAlmacenDetalleList.add(salidasAlmacenDetalleItem);
                }
            }
            


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public int generaNroIngresoAlmacen(){
        int nroIngresoAlmacen = 0;
        ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select isnull(MAX(nro_salida_almacen),0)+1 nro_salida_almacen from salidas_almacen " +
                    " where cod_gestion='"+usuario.getGestionesGlobal().getCodGestion()+"' " +
                    " and cod_almacen='"+usuario.getAlmacenesGlobal().getCodAlmacen()+"' and estado_sistema=1   ";
            System.out.println("consulta " + consulta);
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs=st.executeQuery(consulta);
            if(rs.next()){
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
     public void cargarAreasEmpresa(){
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String sql="  select a.COD_AREA_EMPRESA,a.NOMBRE_AREA_EMPRESA  " +
                    " from AREAS_EMPRESA a  where a.COD_ESTADO_REGISTRO = 1 order by a.NOMBRE_AREA_EMPRESA asc";

            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs=st.executeQuery(sql);
            areasEmpresaList.clear();
            areasEmpresaList.add(new SelectItem("0","-NINGUNO-"));
            while (rs.next()){
                areasEmpresaList.add(new SelectItem(rs.getString("COD_AREA_EMPRESA"), rs.getString("NOMBRE_AREA_EMPRESA")));
            }
            
            if(rs!=null){
                rs.close();
                st.close();
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     public void cargarTiposSalidasAlmacen(){
        try {
            Connection con = null;
            con = Util.openConnection(con);
            
            String sql=" select s.COD_TIPO_SALIDA_ALMACEN,s.NOMBRE_TIPO_SALIDA_ALMACEN  " +
                    " from tipos_salidas_almacen s where cod_tipo_salida_almacen<>3 " +
                    " and cod_tipo_salida_almacen<>2 and cod_tipo_salida_almacen<>4 " +
                    " and cod_tipo_salida_almacen<>5 and cod_tipo_salida_almacen<>6 " +
                    " and cod_tipo_salida_almacen<>9 " + //and cod_tipo_salida_almacen<>10
                    " and cod_tipo_salida_almacen<>11 and  cod_estado_registro=1 " +
                    " order by nombre_tipo_salida_almacen ";
            
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs=st.executeQuery(sql);
            tiposSalidasAlmacenList.clear();
            
            while (rs.next()){
                tiposSalidasAlmacenList.add(new SelectItem(rs.getString("COD_TIPO_SALIDA_ALMACEN"), rs.getString("NOMBRE_TIPO_SALIDA_ALMACEN")));
            }

            if(rs!=null){
                rs.close();
                st.close();
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     public List cargarTiposSalidasAlmacenProduccion(){
         List tiposSalidasAlmacenList = new ArrayList();
        try {
            Connection con = null;
            con = Util.openConnection(con);

            String sql=" select s.COD_TIPO_SALIDA_ALMACEN,s.NOMBRE_TIPO_SALIDA_ALMACEN  " +
                    " from tipos_salidas_almacen s where cod_tipo_salida_almacen in (1,7) " +
                    " order by cod_tipo_salida_almacen asc ";

            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs=st.executeQuery(sql);
            tiposSalidasAlmacenList.clear();

            while (rs.next()){
                tiposSalidasAlmacenList.add(new SelectItem(rs.getString("COD_TIPO_SALIDA_ALMACEN"), rs.getString("NOMBRE_TIPO_SALIDA_ALMACEN")));
            }

            if(rs!=null){
                rs.close();
                st.close();
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
         return tiposSalidasAlmacenList;
    }
     public void cargarComponentesProd(){
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String sql=" select cp.COD_COMPPROD,cp.nombre_prod_semiterminado  " +
                    " from COMPONENTES_PROD cp where cp.COD_ESTADO_COMPPROD=1 order by nombre_prod_semiterminado ";
            System.out.println("consulta " + sql);
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs=st.executeQuery(sql);
            componentesProdList.clear();
            componentesProdList.add(new SelectItem("0", "-NINGUNO-"));
            while (rs.next()){
                componentesProdList.add(new SelectItem(rs.getString("COD_COMPPROD"), rs.getString("nombre_prod_semiterminado")));
            }
            if(rs!=null){
                rs.close();
                st.close();
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     public String producto_changed(){
         try {
             this.nroLote_onblur();
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select p.cod_presentacion,p.NOMBRE_PRODUCTO_PRESENTACION  " +
                     " from PRESENTACIONES_PRODUCTO p,COMPONENTES_PRESPROD c  " +
                     " where p.COD_PRESENTACION=c.COD_PRESENTACION  " +
                     " and c.COD_COMPPROD='"+salidasAlmacen.getComponentesProd().getCodCompprod()+"' ";
                     System.out.println("consulta "+ consulta);
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs=st.executeQuery(consulta);
            presentacionesList.clear();
            presentacionesList.add(new SelectItem("0", "-NINGUNO-"));
            while (rs.next()){
                presentacionesList.add(new SelectItem(rs.getString("cod_presentacion"), rs.getString("NOMBRE_PRODUCTO_PRESENTACION")));
            }
            if(rs!=null){
                rs.close();
                st.close();
                con.close();
            }
         } catch (Exception e) {
             e.printStackTrace();
         }
         return null;
     }
     
     
     public String detallarMateriales_action(){
         try {
             
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " SELECT FMDMP.COD_MATERIAL,M.NOMBRE_MATERIAL,FMDMP.CANTIDAD,FMDMP.COD_UNIDAD_MEDIDA,U.ABREVIATURA  " +
                    " FROM FORMULA_MAESTRA FM " +
                    " INNER JOIN FORMULA_MAESTRA_DETALLE_MP FMDMP ON FM.COD_FORMULA_MAESTRA = FMDMP.COD_FORMULA_MAESTRA " +
                    " INNER JOIN UNIDADES_MEDIDA U ON U.COD_UNIDAD_MEDIDA = FMDMP.COD_UNIDAD_MEDIDA " +
                    " INNER JOIN MATERIALES M ON M.COD_MATERIAL = FMDMP.COD_MATERIAL " +
                    " WHERE FM.COD_COMPPROD = '"+salidasAlmacen.getComponentesProd().getCodCompprod()+"' ";
                    /*
                    " UNION ALL " +
                    " select  fmdep.COD_MATERIAL,m.NOMBRE_MATERIAL,fmdep.CANTIDAD,fmdep.COD_UNIDAD_MEDIDA,u.ABREVIATURA " +
                    " from FORMULA_MAESTRA fm " +
                    " inner join FORMULA_MAESTRA_DETALLE_EP fmdep on fm.COD_FORMULA_MAESTRA = fmdep.COD_FORMULA_MAESTRA " +
                    " inner join UNIDADES_MEDIDA u on u.COD_UNIDAD_MEDIDA = fmdep.COD_UNIDAD_MEDIDA " +
                    " inner join MATERIALES m on m.COD_MATERIAL = fmdep.COD_MATERIAL " +                    
                    " where fm.COD_COMPPROD = '"+salidasAlmacen.getComponentesProd().getCodCompprod()+"'" ;
                    */

            System.out.println("consulta "+ consulta);
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs=st.executeQuery(consulta);

            salidasAlmacenDetalleList.clear();
            

            while (rs.next()){
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
                if(salidasAlmacenDetalleItem.getCantidadSalidaAlmacen()>salidasAlmacenDetalleItem.getCantidadDisponible()){
                    salidasAlmacenDetalleItem.setColorFila("#FF0000");
                }else{
                    salidasAlmacenDetalleItem.setColorFila("#00FF00");
                }

                salidasAlmacenDetalleList.add(salidasAlmacenDetalleItem);
            }
            if(rs!=null){
                rs.close();
                st.close();
                con.close();
            }

         } catch (Exception e) {
             e.printStackTrace();
         }
         return null;
     }
     public float cantidadDisponibleMaterial(SalidasAlmacenDetalle salidasAlmacenDetalle){
         float cantidadDisponible = 0;
         try {
             ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
             Connection con = null;
             con = Util.openConnection(con);
             String consulta = " select iade.cantidad_parcial - " +
                     " ( select ISNULL(sum(sadi.cantidad), 0) " +
                     "  from SALIDAS_ALMACEN_DETALLE_INGRESO sadi " +
                     "  where sadi.cod_material = iade.cod_material and " +
                     "  sadi.cod_ingreso_almacen = iade.cod_ingreso_almacen and " +
                     "  sadi.etiqueta = iade.etiqueta and " +
                     "  cod_salida_almacen in ( " +
                     "  select cod_salida_almacen " +
                     "  from SALIDAS_ALMACEN " +
                     "  where cod_estado_salida_almacen = 1 and " +
                     "  estado_sistema = 1 and cod_almacen = '"+usuario.getAlmacenesGlobal().getCodAlmacen() +"' ) ) + " +
                     " ( select isnull(sum(cantidad_devuelta), 0) " +
                     "  from DEVOLUCIONES_DETALLE_ETIQUETAS dde " +
                     "  where dde.cod_material = iade.cod_material and " +
                     "  dde.cod_ingreso_almacen = iade.cod_ingreso_almacen and " +
                     "  dde.etiqueta = iade.etiqueta " +
                     "  and dde.cod_devolucion in ( " +
                     "  select cod_devolucion from DEVOLUCIONES where cod_estado_devolucion = 1 and " +
                     "  estado_sistema = 1 and cod_almacen = '"+usuario.getAlmacenesGlobal().getCodAlmacen()+"' ) ) as cantidad_r " +
                     "  from ingresos_almacen_detalle_estado iade,ingresos_almacen_detalle  iad,secciones s,almacenes a,ingresos_almacen ia " +
                     " WHERE a.cod_almacen='"+usuario.getAlmacenesGlobal().getCodAlmacen() +"'  and iade.cod_material=iad.cod_material  " +
                     " and iade.cod_ingreso_almacen=iad.cod_ingreso_almacen  " +
                     " and ia.estado_sistema=1 " +
                     " and ia.cod_ingreso_almacen=iad.cod_ingreso_almacen  " +
                     " and ia.cod_estado_ingreso_almacen=1  " +
                     " and ia.cod_almacen=a.cod_almacen " +
                     " and iad.cod_seccion=s.cod_seccion  " +
                     " and s.cod_almacen=a.cod_almacen " +
                     " and iade.cantidad_restante>0   " +
                     " and iad.cod_seccion=s.cod_seccion  " +
                     " and s.cod_almacen=a.cod_almacen and iade.COD_MATERIAL = "+salidasAlmacenDetalle.getMateriales().getCodMaterial()+" " +
                     " and iade.COD_ESTADO_MATERIAL = 2  " ;

            consulta = " select iade.cantidad_parcial - ( " +
                    "  select ISNULL(sum(sadi.cantidad), 0) from SALIDAS_ALMACEN_DETALLE_INGRESO sadi inner join SALIDAS_ALMACEN_DETALLE sad on sad.COD_SALIDA_ALMACEN = sadi.COD_SALIDA_ALMACEN" +
                    "  and sad.COD_MATERIAL = sadi.COD_MATERIAL inner join SALIDAS_ALMACEN s on s.COD_SALIDA_ALMACEN = sad.COD_SALIDA_ALMACEN " +
                    "  where sadi.cod_material = iade.cod_material and sadi.cod_ingreso_almacen = iade.cod_ingreso_almacen and  sadi.etiqueta = iade.etiqueta and " +
                    "  s.ESTADO_SISTEMA = 1 and s.COD_ALMACEN = '"+usuario.getAlmacenesGlobal().getCodAlmacen() +"'  and s.COD_ESTADO_SALIDA_ALMACEN = 1 ) + " +
                    " ( select isnull(sum(dde.cantidad_devuelta), 0) from DEVOLUCIONES_DETALLE_ETIQUETAS dde inner join DEVOLUCIONES_DETALLE dd on dd.COD_DEVOLUCION = dde.COD_DEVOLUCION " +
                    "  and dd.COD_MATERIAL = dde.COD_MATERIAL inner join DEVOLUCIONES d on d.COD_DEVOLUCION = dde.COD_DEVOLUCION  where dde.cod_material = iade.cod_material " +
                    "  and dde.cod_ingreso_almacen = iade.cod_ingreso_almacen and dde.etiqueta = iade.etiqueta " +
                    "  and d.COD_ESTADO_DEVOLUCION = 1 and d.ESTADO_SISTEMA = 1 and d.COD_ALMACEN = '"+usuario.getAlmacenesGlobal().getCodAlmacen()+"' ) as cantidad_r " +
                    " from ingresos_almacen_detalle_estado iade, ingresos_almacen_detalle iad, secciones s, almacenes a, ingresos_almacen ia " +
                    " WHERE a.cod_almacen = '"+usuario.getAlmacenesGlobal().getCodAlmacen() +"'  and iade.cod_material = iad.cod_material and iade.cod_ingreso_almacen = iad.cod_ingreso_almacen " +
                    " and ia.estado_sistema = 1 and ia.cod_ingreso_almacen = iad.cod_ingreso_almacen and ia.cod_estado_ingreso_almacen = 1 " +
                    " and ia.cod_almacen = a.cod_almacen and iad.cod_seccion = s.cod_seccion " +
                    " and s.cod_almacen = a.cod_almacen and iade.cantidad_restante > 0 and iad.cod_seccion = s.cod_seccion " +
                    " and s.cod_almacen = a.cod_almacen and iade.COD_MATERIAL = '"+salidasAlmacenDetalle.getMateriales().getCodMaterial()+"' and iade.COD_ESTADO_MATERIAL = '"+ingresosAlmacenDetalleEstado.getEstadosMaterial().getCodEstadoMaterial()+"'";
            consulta = " select sum(id.CANTIDAD_RESTANTE) cantidad_r from INGRESOS_ALMACEN i, INGRESOS_ALMACEN_DETALLE_ESTADO id " +
                    " where i.COD_INGRESO_ALMACEN=id.COD_INGRESO_ALMACEN and i.COD_ESTADO_INGRESO_ALMACEN=1 and " +
                    " id.COD_MATERIAL='"+salidasAlmacenDetalle.getMateriales().getCodMaterial()+"' and id.CANTIDAD_RESTANTE>0 and id.COD_ESTADO_MATERIAL='"+ingresosAlmacenDetalleEstado.getEstadosMaterial().getCodEstadoMaterial()+"' and i.COD_ALMACEN ='"+usuario.getAlmacenesGlobal().getCodAlmacen()+"'; ";
            
            System.out.println("consulta "+ consulta);
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs=st.executeQuery(consulta);
            while(rs.next()){
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

     public String verDetalleEtiquetasSalidaAlmacen_action(){
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


     public void etiquetasSalidaAlmacen(SalidasAlmacenDetalle salidasAlmacenDetalle){
         try {
             ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
             Connection con = null;
             con = Util.openConnection(con);
             String  consulta = " select 1 col, iade.cod_ingreso_almacen, iade.cod_material, iade.etiqueta,  iade.cod_estado_material, iade.cod_empaque_secundario_externo, " +
                     " iade.cantidad_parcial - ( select ISNULL(sum(sadi.cantidad), 0) from SALIDAS_ALMACEN_DETALLE_INGRESO sadi " +
                     " where sadi.cod_material = iade.cod_material and  sadi.cod_ingreso_almacen = iade.cod_ingreso_almacen and  sadi.etiqueta = iade.etiqueta and " +
                     " cod_salida_almacen in ( select cod_salida_almacen  from SALIDAS_ALMACEN   where cod_estado_salida_almacen = 1 and  estado_sistema = 1 and  cod_almacen = '"+usuario.getAlmacenesGlobal().getCodAlmacen()+"' " +
                     ") ) + ( select isnull(sum(cantidad_devuelta), 0)  from DEVOLUCIONES_DETALLE_ETIQUETAS dde  where dde.cod_material = iade.cod_material and dde.cod_ingreso_almacen = iade.cod_ingreso_almacen and " +
                     " dde.etiqueta = iade.etiqueta and dde.cod_devolucion in ( select cod_devolucion from DEVOLUCIONES where cod_estado_devolucion = 1 " +
                     " and estado_sistema = 1 and  cod_almacen = '"+usuario.getAlmacenesGlobal().getCodAlmacen()+"' ) ) as cantidad_r, iade.fecha_vencimiento, iade.lote_material_proveedor " +
                     " ,e.nombre_estado_material,ia.nro_ingreso_almacen,s.nombre_seccion,es.nombre_empaque_secundario_externo " +
                     " from ingresos_almacen_detalle_estado iade,  ingresos_almacen_detalle iad,  secciones s, almacenes a, ingresos_almacen ia,ESTADOS_MATERIAL e,EMPAQUES_SECUNDARIO_EXTERNO es " +
                     " WHERE a.cod_almacen = '"+usuario.getAlmacenesGlobal().getCodAlmacen()+"' and iade.cod_material = iad.cod_material and iade.cod_ingreso_almacen = iad.cod_ingreso_almacen and " +
                     " ia.estado_sistema = 1 and  ia.cod_ingreso_almacen = iad.cod_ingreso_almacen and ia.cod_estado_ingreso_almacen = 1 " +
                     " and ia.cod_almacen = a.cod_almacen and iad.cod_seccion = s.cod_seccion and s.cod_almacen = a.cod_almacen " +
                     " and iade.cantidad_restante > 0 and iad.cod_seccion = s.cod_seccion and s.cod_almacen = a.cod_almacen " +
                     " and iade.COD_MATERIAL = '"+salidasAlmacenDetalle.getMateriales().getCodMaterial() +"' " +
                     " and iade.COD_ESTADO_MATERIAL = 2  " +
                     " and e.cod_estado_material= iade.cod_estado_material  " +
                     " and es.cod_empaque_secundario_externo =  iade.cod_empaque_secundario_externo  ";


             consulta = "  select iade.cod_ingreso_almacen, iade.cod_material, iade.etiqueta,  iade.cod_estado_material, iade.cod_empaque_secundario_externo,iade.CANTIDAD_PARCIAL - " +
                    " (select isnull(SUM(sadi.CANTIDAD),0) from SALIDAS_ALMACEN_DETALLE_INGRESO sadi inner join SALIDAS_ALMACEN_DETALLE sad on sadi.COD_SALIDA_ALMACEN = sad.COD_SALIDA_ALMACEN and sadi.COD_MATERIAL = sad.COD_MATERIAL" +
                    " inner join SALIDAS_ALMACEN s on s.COD_SALIDA_ALMACEN = sad.COD_SALIDA_ALMACEN" +
                    " where sadi.COD_INGRESO_ALMACEN = iade.COD_INGRESO_ALMACEN and sadi.COD_MATERIAL = iade.COD_MATERIAL and sadi.ETIQUETA = iade.ETIQUETA" +
                    " and s.COD_ESTADO_SALIDA_ALMACEN = 1 and s.ESTADO_SISTEMA = 1 and s.COD_ALMACEN = '"+usuario.getAlmacenesGlobal().getCodAlmacen()+"') " +
                    " +(select isnull(SUM(dde.CANTIDAD_DEVUELTA),0) from DEVOLUCIONES_DETALLE_ETIQUETAS dde inner join DEVOLUCIONES_DETALLE dd on dd.COD_DEVOLUCION = dde.COD_DEVOLUCION and dd.COD_MATERIAL = dde.COD_MATERIAL" +
                    " inner join DEVOLUCIONES d on d.COD_DEVOLUCION = dd.COD_DEVOLUCION" +
                    " where  dde.COD_INGRESO_ALMACEN = iade.COD_INGRESO_ALMACEN and dde.COD_MATERIAL = iade.COD_MATERIAL and dde.ETIQUETA = iade.ETIQUETA" +
                    " and d.COD_ESTADO_DEVOLUCION = 1 and d.ESTADO_SISTEMA = 1 and d.COD_ALMACEN = '"+usuario.getAlmacenesGlobal().getCodAlmacen()+"') cantidad_r, iade.fecha_vencimiento, iade.lote_material_proveedor " +
                    " ,e.nombre_estado_material,ia.nro_ingreso_almacen,sec.nombre_seccion,ese.nombre_empaque_secundario_externo ," +
                    " (select sadi1.CANTIDAD from SALIDAS_ALMACEN_DETALLE_INGRESO sadi1 where sadi1.COD_INGRESO_ALMACEN=iade.COD_INGRESO_ALMACEN and  sadi1.COD_MATERIAL = iade.COD_MATERIAL and sadi1.ETIQUETA = iade.ETIQUETA" +
                    "  and sadi1.COD_SALIDA_ALMACEN = '"+salidasAlmacenDetalle.getSalidasAlmacen().getCodSalidaAlmacen()+"') cantidad_sacar" +
                    " from INGRESOS_ALMACEN_DETALLE_ESTADO iade " +
                    " inner join INGRESOS_ALMACEN_DETALLE iad on iad.COD_INGRESO_ALMACEN = iade.COD_INGRESO_ALMACEN and iad.COD_MATERIAL = iade.COD_MATERIAL" +
                    " inner join INGRESOS_ALMACEN ia on ia.COD_INGRESO_ALMACEN = iad.COD_INGRESO_ALMACEN" +
                    " inner join ALMACENES a on a.COD_ALMACEN = ia.COD_ALMACEN" +
                    " inner join ESTADOS_MATERIAL e on e.COD_ESTADO_MATERIAL = iade.COD_ESTADO_MATERIAL" +
                    " inner join EMPAQUES_SECUNDARIO_EXTERNO ese on ese.COD_EMPAQUE_SECUNDARIO_EXTERNO = iade.COD_EMPAQUE_SECUNDARIO_EXTERNO " +
                    " inner join secciones sec on sec.COD_SECCION = iad.COD_SECCION " +
                    " where a.COD_ALMACEN = '"+usuario.getAlmacenesGlobal().getCodAlmacen()+"'  and ia.COD_ESTADO_INGRESO_ALMACEN = 1 and ia.ESTADO_SISTEMA = 1  " +
                    //inicio ale validar
                    " and iade.CANTIDAD_RESTANTE>0 and iade.COD_ESTADO_MATERIAL in(2,6) " + //
                    // final ale validar
                    //" and iade.CANTIDAD_RESTANTE>0 and iade.COD_ESTADO_MATERIAL = '"+ingresosAlmacenDetalleEstado.getEstadosMaterial().getCodEstadoMaterial() +"' " + //
                    " and iade.COD_MATERIAL = '"+salidasAlmacenDetalle.getMateriales().getCodMaterial() +"' and ia.ESTADO_SISTEMA = 1 " +
                    " order by  iade.fecha_vencimiento asc,ia.nro_ingreso_almacen asc"; //and iade.COD_ESTADO_MATERIAL = 2
             consulta = "  select iade.cod_ingreso_almacen, iade.cod_material, iade.etiqueta,  iade.cod_estado_material, iade.cod_empaque_secundario_externo, " +
                    "  iade.CANTIDAD_RESTANTE cantidad_r, iade.fecha_vencimiento, iade.lote_material_proveedor " +
                    " ,e.nombre_estado_material,ia.nro_ingreso_almacen,sec.nombre_seccion,ese.nombre_empaque_secundario_externo ," +
                    " (select sadi1.CANTIDAD from SALIDAS_ALMACEN_DETALLE_INGRESO sadi1 where sadi1.COD_INGRESO_ALMACEN=iade.COD_INGRESO_ALMACEN and  sadi1.COD_MATERIAL = iade.COD_MATERIAL and sadi1.ETIQUETA = iade.ETIQUETA" +
                    "  and sadi1.COD_SALIDA_ALMACEN = '"+salidasAlmacenDetalle.getSalidasAlmacen().getCodSalidaAlmacen()+"') cantidad_sacar" +
                    " from INGRESOS_ALMACEN_DETALLE_ESTADO iade " +
                    " inner join INGRESOS_ALMACEN_DETALLE iad on iad.COD_INGRESO_ALMACEN = iade.COD_INGRESO_ALMACEN and iad.COD_MATERIAL = iade.COD_MATERIAL" +
                    " inner join INGRESOS_ALMACEN ia on ia.COD_INGRESO_ALMACEN = iad.COD_INGRESO_ALMACEN" +
                    " inner join ALMACENES a on a.COD_ALMACEN = ia.COD_ALMACEN" +
                    " inner join ESTADOS_MATERIAL e on e.COD_ESTADO_MATERIAL = iade.COD_ESTADO_MATERIAL" +
                    " inner join EMPAQUES_SECUNDARIO_EXTERNO ese on ese.COD_EMPAQUE_SECUNDARIO_EXTERNO = iade.COD_EMPAQUE_SECUNDARIO_EXTERNO " +
                    " inner join secciones sec on sec.COD_SECCION = iad.COD_SECCION " +
                    " where a.COD_ALMACEN = '"+usuario.getAlmacenesGlobal().getCodAlmacen()+"'  and ia.COD_ESTADO_INGRESO_ALMACEN = 1 and ia.ESTADO_SISTEMA = 1  " +
                    //inicio ale validar
                    " and iade.CANTIDAD_RESTANTE>0 and iade.COD_ESTADO_MATERIAL in(2,6) " + //
                    // final ale validar
                    //" and iade.CANTIDAD_RESTANTE>0 and iade.COD_ESTADO_MATERIAL = '"+ingresosAlmacenDetalleEstado.getEstadosMaterial().getCodEstadoMaterial() +"' " + //
                    " and iade.COD_MATERIAL = '"+salidasAlmacenDetalle.getMateriales().getCodMaterial() +"' and ia.ESTADO_SISTEMA = 1 " +
                    " order by  iade.fecha_vencimiento asc,ia.nro_ingreso_almacen asc"; //and iade.COD_ESTADO_MATERIAL = 2


            
            System.out.println("-consulta "+ consulta);
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs=st.executeQuery(consulta);
            salidasAlmacenDetalle.getSalidasAlmacenDetalleIngresoList().clear();
            salidasAlmacenDetalle.setCantidadDisponible(0);
            while(rs.next()){

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
                
                if(Utiles.redondear(salidasAlmacenDetalleIngreso.getCantidadDisponible(),2)>0){
                    salidasAlmacenDetalle.getSalidasAlmacenDetalleIngresoList().add(salidasAlmacenDetalleIngreso);
                    salidasAlmacenDetalle.setCantidadDisponible(salidasAlmacenDetalle.getCantidadDisponible()+salidasAlmacenDetalleIngreso.getCantidadDisponible());
                }
            }
            rs.close();
            st.close();
            con.close();
         
         } catch (Exception e) {
             e.printStackTrace();
         }         
     }
     
     public boolean verificaTransaccionCerradaAlmacen(){
         boolean transaccionCerradaAlmacen = false;
         Date fechaActual = new Date();
         SimpleDateFormat sdf = new SimpleDateFormat("MM");
         try {
             Connection con = null;
             con = Util.openConnection(con);
             ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
             String consulta = " select * from estados_transacciones_almacen " +
                     " where cod_gestion='"+usuario.getGestionesGlobal().getCodGestion()+"' " +
                     " and cod_mes = '"+Integer.valueOf(sdf.format(fechaActual))+"'  " +
                     " and estado=1 ";
             System.out.println("consulta " + consulta);
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
     public boolean detalleItems(List salidasAlmacenDetalleList){
         boolean itemsDetallados = true;
         try {
             Iterator i = salidasAlmacenDetalleList.iterator();
             while(i.hasNext()){
                 SalidasAlmacenDetalle salidasAlmacenDetalleItem= (SalidasAlmacenDetalle) i.next();
                 //System.out.println("size: " + salidasAlmacenDetalleItem.getSalidasAlmacenDetalleIngresoList().size());
                 if(salidasAlmacenDetalleItem.getSalidasAlmacenDetalleIngresoList().size()==0){
                     itemsDetallados = false;
                 }
             }

         } catch (Exception e) {
             e.printStackTrace();
         }
         return itemsDetallados;
     }
     public String guardarSalidaAlmacen_action()throws SQLException{
            Connection con = null;
            con = Util.openConnection(con);
         try {
             mensaje = "";
             
             con.setAutoCommit(false);             
             ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
             SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
             Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

             if(this.verificaTransaccionCerradaAlmacen()==true){
                 mensaje = "Las transacciones para este mes fueron cerradas ";
                 return null;
             }
             if(salidasAlmacenDetalleList.size()==0){
                 mensaje = " No existe detalle de salida ";
                 return null;
             }
             if(this.detalleItems(salidasAlmacenDetalleList)==false){
                 mensaje = " No existe detalle de items ";
                 return null;
             }
             if(this.validaCantidadSalidaCantidadDisponible(salidasAlmacenDetalleList)==false){
                 mensaje = " No existe cantidad de salida suficiente en uno o varios items ";
                 return null;
             }
             //inicio ale verificar salida
             if(salidasAlmacen.getAreasEmpresa().getCodAreaEmpresa().equals("0"))
             {
                 mensaje="Debe de seleccionar el Area/Departamento destino";
                 return null;
             }
             //final ale verificar salida
             
             
             salidasAlmacen.setCodSalidaAlmacen(this.generaCodSalidaAlmacen());
             salidasAlmacen.setNroSalidaAlmacen(this.generaNroIngresoAlmacen());
             
             
             String consulta = " INSERT INTO SALIDAS_ALMACEN ( COD_GESTION, COD_SALIDA_ALMACEN, COD_ORDEN_PESADA,  COD_FORM_SALIDA, COD_PROD, " +
                   "   COD_TIPO_SALIDA_ALMACEN,  COD_AREA_EMPRESA,   NRO_SALIDA_ALMACEN,   FECHA_SALIDA_ALMACEN,   OBS_SALIDA_ALMACEN, " +
                   "  ESTADO_SISTEMA,   COD_ALMACEN,   COD_ORDEN_COMPRA,   COD_PERSONAL,    COD_ESTADO_SALIDA_ALMACEN,   COD_LOTE_PRODUCCION, " +
                   "   COD_ESTADO_SALIDA_COSTO,   cod_prod_ant,   orden_trabajo,   COD_PRESENTACION ) VALUES ( " +
                   "  '"+salidasAlmacen.getGestiones().getCodGestion()+"', '"+salidasAlmacen.getCodSalidaAlmacen()+"', '"+salidasAlmacen.getCodOrdenRepesada()+"'," +
                   "  '"+salidasAlmacen.getCodFormSalida()+"','"+salidasAlmacen.getComponentesProd().getCodCompprod()+"', '"+salidasAlmacen.getTiposSalidasAlmacen().getCodTipoSalidaAlmacen()+"', " +
                   "   '"+salidasAlmacen.getAreasEmpresa().getCodAreaEmpresa()+"', '"+salidasAlmacen.getNroSalidaAlmacen()+"','"+ sdf.format(salidasAlmacen.getFechaSalidaAlmacen())+"'," +
                   "  '"+salidasAlmacen.getObsSalidaAlmacen()+"',  '"+salidasAlmacen.getEstadoSistema()+"','"+salidasAlmacen.getAlmacenes().getCodAlmacen()+"'," +
                   "  '"+salidasAlmacen.getOrdenesCompra().getCodOrdenCompra()+"', '"+salidasAlmacen.getPersonal().getCodPersonal()+"',  '"+salidasAlmacen.getEstadosSalidasAlmacen().getCodEstadoSalidaAlmacen()+"'," +
                   "  '"+salidasAlmacen.getCodLoteProduccion()+"','"+salidasAlmacen.getCodEstadoSalidaCosto()+"'," +
                   "  '"+salidasAlmacen.getCodProdAnt()+"', '"+salidasAlmacen.getOrdenTrabajo()+"', '"+salidasAlmacen.getPresentacionesProducto().getCodPresentacion()+"'); ";
                   System.out.println("consulta " + consulta);
                   st.executeUpdate(consulta);
                   //iteracion de detalle
                   Iterator i = salidasAlmacenDetalleList.iterator();

                   while(i.hasNext()){
                       SalidasAlmacenDetalle salidasAlmacenDetalleItem = (SalidasAlmacenDetalle)i.next();
                       consulta = " INSERT INTO SALIDAS_ALMACEN_DETALLE ( COD_SALIDA_ALMACEN, COD_MATERIAL, CANTIDAD_SALIDA_ALMACEN, COD_UNIDAD_MEDIDA, " +
                               "  COD_ESTADO_MATERIAL )  VALUES (   '"+salidasAlmacen.getCodSalidaAlmacen()+"', '"+salidasAlmacenDetalleItem.getMateriales().getCodMaterial() +"'," +
                               "  '"+salidasAlmacenDetalleItem.getCantidadSalidaAlmacen() +"',   '"+salidasAlmacenDetalleItem.getUnidadesMedida().getCodUnidadMedida() +"', " +
                               "   '"+salidasAlmacenDetalleItem.getEstadosMaterial().getCodEstadoMaterial()+"' ) ";
                               System.out.println("consulta " + consulta);
                               st.executeUpdate(consulta);
                       Iterator i1 = salidasAlmacenDetalleItem.getSalidasAlmacenDetalleIngresoList().iterator();


                       while(i1.hasNext()){
                           SalidasAlmacenDetalleIngreso salidasAlmacenDetalleIngreso = (SalidasAlmacenDetalleIngreso) i1.next();

                           if(salidasAlmacenDetalleIngreso.getCantidadSacar()>0){
                               consulta = "  INSERT INTO SALIDAS_ALMACEN_DETALLE_INGRESO ( COD_SALIDA_ALMACEN,  COD_MATERIAL, COD_INGRESO_ALMACEN,  ETIQUETA, " +
                               " COSTO_SALIDA,   FECHA_VENCIMIENTO,   CANTIDAD,   COSTO_SALIDA_ACTUALIZADO,   FECHA_ACTUALIZACION,   COSTO_SALIDA_ACTUALIZADO_FINAL )  " +
                               " VALUES ( '"+salidasAlmacen.getCodSalidaAlmacen()+"',   '"+salidasAlmacenDetalleItem.getMateriales().getCodMaterial() +"',   " +
                               " '"+salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getIngresosAlmacen().getCodIngresoAlmacen()+"', " +
                               " '"+salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getEtiqueta()  +"', '"+salidasAlmacenDetalleIngreso.getCostoSalida()+"', '"+salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getFechaVencimiento()+"', " +
                               " '"+salidasAlmacenDetalleIngreso.getCantidadSacar()+"', '"+salidasAlmacenDetalleIngreso.getCostoSalidaActualizado()+"', " +
                               " '"+ sdf.format(salidasAlmacenDetalleIngreso.getFechaActualizacion())+"',  '"+salidasAlmacenDetalleIngreso.getCostoSalidaActualizadoFinal()+"' ) ";
                               System.out.println("consulta " + consulta);
                               st.executeUpdate(consulta);
                               consulta = " update ingresos_almacen_detalle_estado set cantidad_restante=cantidad_restante - "+salidasAlmacenDetalleIngreso.getCantidadSacar()+" " +
                                       " where cod_material="+salidasAlmacenDetalleItem.getMateriales().getCodMaterial()+" and etiqueta="+salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getEtiqueta()+" " +
                                       " and cod_ingreso_almacen='"+salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getIngresosAlmacen().getCodIngresoAlmacen()+"' ";
                               System.out.println("consulta " + consulta );
                               st.executeUpdate(consulta);
                           }
                       }
                   }
                   con.commit();
                   
                   st.close();
                   con.close();
                   
         } catch (Exception e) {
             e.printStackTrace();
             con.rollback();
         }finally{
             con.close();
         }
         return null;
     }
     
     public boolean validaCantidadSalidaCantidadDisponible(List salidasAlmacenDetalleList){
         boolean cantidadValidada = true;
         try {
             Iterator i = salidasAlmacenDetalleList.iterator();
             while(i.hasNext()){
                 SalidasAlmacenDetalle salidasAlmacenDetalleItem = (SalidasAlmacenDetalle) i.next();
                 if(salidasAlmacenDetalleItem.getCantidadDisponible()<salidasAlmacenDetalleItem.getCantidadSalidaAlmacen()){
                     cantidadValidada = false;
                 }
                 
             }
             
         } catch (Exception e) {
             e.printStackTrace();
         }
         return cantidadValidada;
     }
     public int generaCodSalidaAlmacen(){
         int codSalidaAlmacen = 0;
         try {
             Connection con = null;
             con = Util.openConnection(con);
                 String consulta =" select isnull(max(s.COD_SALIDA_ALMACEN),0)+1 COD_SALIDA_ALMACEN from SALIDAS_ALMACEN s  ";
              System.out.println("consulta " + consulta);
             Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             ResultSet rs = st.executeQuery(consulta);
             if(rs.next()){
                 codSalidaAlmacen = rs.getInt("COD_SALIDA_ALMACEN");
             }

         } catch (Exception e) {
             e.printStackTrace();
         }
         return codSalidaAlmacen;
     }
    
     public String eliminarDetalle_action(){
         try {
             List auxList = new ArrayList();
             Iterator i = salidasAlmacenDetalleList.iterator();
             while(i.hasNext()){
                 SalidasAlmacenDetalle salidasAlmacenDetalleItem = (SalidasAlmacenDetalle)i.next();
                 if(salidasAlmacenDetalleItem.getChecked()!=true){
                     auxList.add(salidasAlmacenDetalleItem);
                 }
             }
             salidasAlmacenDetalleList = auxList;

         } catch (Exception e) {
             e.printStackTrace();
         }
         return null;
     }     

      public List cargarCapitulos(){
          List capitulosList = new ArrayList();
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select c.COD_CAPITULO,c.NOMBRE_CAPITULO  from CAPITULOS c where c.COD_ESTADO_REGISTRO = 1 ";

            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs=st.executeQuery(consulta);
            capitulosList.clear();
            capitulosList.add(new SelectItem("0", "-NINGUNO-"));
            while (rs.next()){
                capitulosList.add(new SelectItem(rs.getString("COD_CAPITULO"), rs.getString("NOMBRE_CAPITULO")));
            }
            if(rs!=null){
                rs.close();
                st.close();
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
          return capitulosList;
    }

      public String cargarGrupos(){
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select gr.COD_GRUPO,gr.NOMBRE_GRUPO from grupos gr where gr.COD_ESTADO_REGISTRO = 1 " +
                    " and gr.COD_CAPITULO = '"+ salidasAlmacenDetalleAgregar.getMateriales().getGrupos().getCapitulos().getCodCapitulo() +"' ";
            
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs=st.executeQuery(consulta);
            gruposList.clear();
            gruposList.add(new SelectItem("0", "-NINGUNO-"));
            while (rs.next()){
                gruposList.add(new SelectItem(rs.getString("COD_GRUPO"), rs.getString("NOMBRE_GRUPO")));
            }
            if(rs!=null){
                rs.close();
                st.close();
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
      public List cargarMateriales(int codGrupo){
          List materialesList = new ArrayList();
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select m.COD_MATERIAL,m.NOMBRE_MATERIAL from MATERIALES m where m.COD_ESTADO_REGISTRO = 1 " +
                    " and m.COD_GRUPO = '"+codGrupo+"' " +
                    " order by m.NOMBRE_MATERIAL ASC ";
            System.out.println("consulta" + consulta);
            
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs=st.executeQuery(consulta);
            materialesList.clear();
            materialesList.add(new SelectItem("0", "-NINGUNO-"));
            while (rs.next()){
                materialesList.add(new SelectItem(rs.getString("COD_MATERIAL"), rs.getString("NOMBRE_MATERIAL")));
            }
            if(rs!=null){
                rs.close();
                st.close();
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
          return materialesList;
    }
    public String grupos_change(){
        materialesList = this.cargarMateriales(salidasAlmacenDetalleAgregar.getMateriales().getGrupos().getCodGrupo());
        return null;
    }
    public String agregarSalidaAlmacenDetalle_action(){
          try {
              salidasAlmacenDetalleAgregar = new SalidasAlmacenDetalle();
              salidasAlmacenDetalleAgregar.getEstadosMaterial().setCodEstadoMaterial(2); //estado aprobado
              
          } catch (Exception e) {
              e.printStackTrace();
          }
          return null;
    }
    public String guardarAgregarSalidaAlmacenDetalle_action(){
          try {
              mensaje ="";
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
              }


              

              salidasAlmacenDetalleAgregar.setChecked(false);
              this.etiquetasSalidaAlmacen(salidasAlmacenDetalleAgregar);
              
              
              if(salidasAlmacenDetalleAgregar.getCantidadSalidaAlmacen()>salidasAlmacenDetalleAgregar.getCantidadDisponible()){
                    salidasAlmacenDetalleAgregar.setColorFila("#FF0000");
                }else{
                    salidasAlmacenDetalleAgregar.setColorFila("#00FF00");
                }
              this.detalleEtiquetasSalidaAlmacen(salidasAlmacenDetalleAgregar);
              salidasAlmacenDetalleList.add(salidasAlmacenDetalleAgregar);
              
              

          } catch (Exception e) {
              e.printStackTrace();
          }
          return null;
      }

    public void detalleEtiquetasSalidaAlmacen(SalidasAlmacenDetalle salidasAlmacenDetalleItem){
         try {
            
                   float cantidadSalidaAlmacen = salidasAlmacenDetalleItem.getCantidadSalidaAlmacen();
                   Iterator i =  salidasAlmacenDetalleItem.getSalidasAlmacenDetalleIngresoList().iterator();
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
             

         } catch (Exception e) {
             e.printStackTrace();
         }
     }
    public String detallarCantidadSalida_action(){
        try {

            

            salidasAlmacenDetalleAgregar = new SalidasAlmacenDetalle();

            NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
                DecimalFormat form = (DecimalFormat)nf;
                form.applyPattern("#.###");

            // resetear el estilo de las filas
            Iterator i = materialesBuscarList.iterator();
            while(i.hasNext()){
                Materiales materialesItem = (Materiales)i.next();
                materialesItem.setColorFila("");
            }



            Materiales materialesItem = (Materiales) materialesBuscarDataTable.getRowData();
            materialesItem.setColorFila("#FFCC99");
            salidasAlmacenDetalleAgregar.setMateriales(materialesItem);
            salidasAlmacenDetalleAgregar.getEstadosMaterial().setCodEstadoMaterial(2);//Aprobado

            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select m.COD_MATERIAL,m.NOMBRE_MATERIAL,u.COD_UNIDAD_MEDIDA,u.NOMBRE_UNIDAD_MEDIDA,u.ABREVIATURA " +
                    " from materiales m  " +
                    " inner join UNIDADES_MEDIDA u on m.COD_UNIDAD_MEDIDA = u.COD_UNIDAD_MEDIDA " +
                    " where m.cod_material = '"+salidasAlmacenDetalleAgregar.getMateriales().getCodMaterial()+"' ";
            System.out.println("consulta" + consulta);
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs=st.executeQuery(consulta);
            if(rs.next()){
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
            salidasAlmacenDetalleAgregar.setCantidadDisponible(Float.valueOf(form.format(this.cantidadDisponibleMaterial(salidasAlmacenDetalleAgregar))));
            
            System.out.println("cantidad_disponible : " +  salidasAlmacenDetalleAgregar.getCantidadDisponible());
            salidasAlmacenDetalleAgregar.setEquivalencias(this.buscaEquivalencia(salidasAlmacenDetalleAgregar));

            if(rs!=null){
                rs.close();
                st.close();
                con.close();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public Equivalencias buscaEquivalencia(SalidasAlmacenDetalle salidasAlmacenDetalle){
        Equivalencias equivalencias = new Equivalencias();
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select m.COD_MATERIAL,m.NOMBRE_MATERIAL,u.COD_UNIDAD_MEDIDA,u.NOMBRE_UNIDAD_MEDIDA NOMBRE_UNIDAD_MEDIDA,u.ABREVIATURA,u1.COD_UNIDAD_MEDIDA COD_UNIDAD_MEDIDA1,u1.NOMBRE_UNIDAD_MEDIDA NOMBRE_UNIDAD_MEDIDA1,u1.ABREVIATURA ABREVIATURA1,e.VALOR_EQUIVALENCIA " +
                    " from materiales m  " +
                    " inner join UNIDADES_MEDIDA u on m.COD_UNIDAD_MEDIDA = u.COD_UNIDAD_MEDIDA " +
                    " inner join TIPOS_MEDIDA ti on ti.cod_tipo_medida  = u.COD_TIPO_MEDIDA " +
                    " inner join UNIDADES_MEDIDA u1 on u1.COD_TIPO_MEDIDA = u.COD_TIPO_MEDIDA " +
                    " left outer join EQUIVALENCIAS e on (e.COD_UNIDAD_MEDIDA = u.COD_UNIDAD_MEDIDA and e.COD_UNIDAD_MEDIDA2 = u1.COD_UNIDAD_MEDIDA) or " +
                    " (e.COD_UNIDAD_MEDIDA2 = u.COD_UNIDAD_MEDIDA and e.COD_UNIDAD_MEDIDA = u1.COD_UNIDAD_MEDIDA) where u1.UNIDAD_CLAVE=1 " +
                    " and m.COD_MATERIAL = '"+salidasAlmacenDetalle.getMateriales().getCodMaterial() +"' ";
                    

            System.out.println("consulta" + consulta);
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs=st.executeQuery(consulta);
            if(rs.next()){
                equivalencias.setValorEquivalencia(rs.getFloat("VALOR_EQUIVALENCIA"));
                equivalencias.getUnidadesMedida1().setCodUnidadMedida(rs.getInt("COD_UNIDAD_MEDIDA"));
                equivalencias.getUnidadesMedida1().setNombreUnidadMedida(rs.getString("NOMBRE_UNIDAD_MEDIDA"));
                equivalencias.getUnidadesMedida1().setAbreviatura(rs.getString("ABREVIATURA"));
                equivalencias.getUnidadesMedida2().setCodUnidadMedida(rs.getInt("COD_UNIDAD_MEDIDA1"));
                equivalencias.getUnidadesMedida2().setNombreUnidadMedida(rs.getString("NOMBRE_UNIDAD_MEDIDA1"));
                equivalencias.getUnidadesMedida2().setAbreviatura(rs.getString("ABREVIATURA1"));

                if(equivalencias.getUnidadesMedida1().getCodUnidadMedida()==equivalencias.getUnidadesMedida2().getCodUnidadMedida()){
                    equivalencias.setValorEquivalencia(1);
                    
                }
                
                
            }
            

        } catch (Exception e) {
            e.printStackTrace();
        }
        return equivalencias;
    }
    
    public String guardarSalidasAlmacenDetalleIngreso_action(){
        try {
            //sumar los subtotales del detalle de ingreso
            //verificar que la sumatoria es igual a la cantidad de salida de almacen del material
            mensaje = "";
            Iterator i = salidasAlmacenDetalle.getSalidasAlmacenDetalleIngresoList().iterator();
            float cantidadSacar = 0;
            while(i.hasNext()){
                SalidasAlmacenDetalleIngreso salidasAlmacenDetalleEstado = (SalidasAlmacenDetalleIngreso) i.next();
                cantidadSacar = cantidadSacar+ salidasAlmacenDetalleEstado.getCantidadSacar();
            }
            if(cantidadSacar!= salidasAlmacenDetalle.getCantidadSalidaAlmacen()){
                mensaje = "la sumatoria de las cantidades parciales no es igual a la cantidad de salida";
                //devolver al estado original                
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public String editarCantidadSalidasAlmacenDetalle_action(){
        try {
            Iterator i = salidasAlmacenDetalleList.iterator();
            while(i.hasNext()){
                SalidasAlmacenDetalle salidasAlmacenDetalleItem = (SalidasAlmacenDetalle)i.next();
                if(salidasAlmacenDetalleItem.getChecked().booleanValue()==true){
                    salidasAlmacenDetalle = salidasAlmacenDetalleItem;
                    break;
                }
            }
            if(salidasAlmacenDetalle.getEquivalencias().getValorEquivalencia()>0){
                salidasAlmacenDetalle.setCantidadSalidaAlmacenEquivalente( salidasAlmacenDetalle.getCantidadSalidaAlmacen()/ 
                        salidasAlmacenDetalle.getEquivalencias().getValorEquivalencia());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public String guardarEdicionCantidadMaterial_action(){
          try {
              
              this.detalleEtiquetasSalidaAlmacen(salidasAlmacenDetalle);

              if(salidasAlmacenDetalle.getCantidadSalidaAlmacen()>salidasAlmacenDetalle.getCantidadDisponible()){
                    salidasAlmacenDetalle.setColorFila("#FF0000");
                }else{
                    salidasAlmacenDetalle.setColorFila("#00FF00");
                }
             
          } catch (Exception e) {
              e.printStackTrace();
          }
          return null;
      }


     public String capitulos_change(){
        try {
            gruposList = this.cargarGrupos(buscarMaterial.getGrupos().getCapitulos().getCodCapitulo());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

     public List cargarGrupos(int codCapitulo){
         List gruposList = new ArrayList();
         try {
                Connection con = null;
                con = Util.openConnection(con);
                String consulta = " SELECT GR.COD_GRUPO,GR.NOMBRE_GRUPO FROM GRUPOS GR WHERE GR.COD_CAPITULO = '"+codCapitulo+"' AND GR.COD_ESTADO_REGISTRO = 1 ";
                System.out.println("consulta " + consulta);
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
             e.printStackTrace();
         }
         return gruposList;
     }

     public String buscarMaterial_action(){
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select m.COD_MATERIAL,m.NOMBRE_MATERIAL,gr.NOMBRE_GRUPO,cap.NOMBRE_CAPITULO," +
                    " m.COD_UNIDAD_MEDIDA,u1.ABREVIATURA ABREVIATURA1 ,m.COD_UNIDAD_MEDIDA_COMPRA,u2.ABREVIATURA ABREVIATURA2  " +
                    " from MATERIALES m  " +
                    " inner join GRUPOS gr on gr.COD_GRUPO = m.COD_GRUPO " +
                    " inner join CAPITULOS cap on cap.COD_CAPITULO = gr.COD_CAPITULO" +
                    " inner join UNIDADES_MEDIDA u1 on u1.COD_UNIDAD_MEDIDA = m.COD_UNIDAD_MEDIDA " +
                    " inner join UNIDADES_MEDIDA u2 on u2.COD_UNIDAD_MEDIDA = m.COD_UNIDAD_MEDIDA_COMPRA  " +
                    " where m.NOMBRE_MATERIAL like '%"+buscarMaterial.getNombreMaterial()+"%' " ;
                    if(buscarMaterial.getGrupos().getCodGrupo()>0){
                        consulta=consulta+ " and gr.COD_GRUPO = '"+buscarMaterial.getGrupos().getCodGrupo()+"' " ;
                    }
                    if(buscarMaterial.getGrupos().getCapitulos().getCodCapitulo()>0){
                        consulta=consulta+ " and cap.COD_CAPITULO = '"+buscarMaterial.getGrupos().getCapitulos().getCodCapitulo()+"' " ;
                    }
                    consulta = consulta + " and m.COD_MATERIAL not in ("+this.itemsSeleccionadosSalidaAlmacen() +") ";
            System.out.println("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            materialesBuscarList.clear();
            while(rs.next()){
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

     public String itemsSeleccionadosSalidaAlmacen(){
         String itemsSalidaSalidaAlmacen = "-1";
         try {
             Iterator i = salidasAlmacenDetalleList.iterator();
             while(i.hasNext()){
                 SalidasAlmacenDetalle salidasAlmacenDetalleItem = (SalidasAlmacenDetalle)i.next();
                 itemsSalidaSalidaAlmacen = itemsSalidaSalidaAlmacen +","+ salidasAlmacenDetalleItem.getMateriales().getCodMaterial();


             }
         } catch (Exception e) {
             e.printStackTrace();
         }
         return itemsSalidaSalidaAlmacen;

     }

     public String seleccionarMaterial_action(){

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
            e.printStackTrace();
        }
        return null;
    }

     public String agregarItem_action(){
         try {
             materialesBuscarList.clear();
             buscarMaterial = new Materiales();
             ingresosAlmacenDetalleEstado = new IngresosAlmacenDetalleEstado();
             salidasAlmacenDetalleAgregar = new SalidasAlmacenDetalle();
         } catch (Exception e) {
             e.printStackTrace();
         }
         return null;
     }
     public String estadoMaterial_change(){
         try {

            NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
                DecimalFormat form = (DecimalFormat)nf;
                form.applyPattern("#.###");

             salidasAlmacenDetalleAgregar.setCantidadDisponible(Float.valueOf(form.format(this.cantidadDisponibleMaterial(salidasAlmacenDetalleAgregar))));

            System.out.println("cantidad_disponible : " +  salidasAlmacenDetalleAgregar.getCantidadDisponible());
            salidasAlmacenDetalleAgregar.setEquivalencias(this.buscaEquivalencia(salidasAlmacenDetalleAgregar));
             

         } catch (Exception e) {
             e.printStackTrace();
         }
         return null;
     }
     public List cargarEstadosMaterial(){
         List estadosMaterialList = new ArrayList();
         try {
                Connection con = null;
                con = Util.openConnection(con);
                String consulta = " select cod_estado_material, nombre_estado_material " +
                        " from estados_material where cod_estado_registro=1 and cod_estado_material<>7 ";
                
                System.out.println("consulta " + consulta);
                Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs = st.executeQuery(consulta);
                estadosMaterialList.clear();
                estadosMaterialList.add(new SelectItem("0", "-NINGUNO-"));
                while(rs.next()){
                    estadosMaterialList.add(new SelectItem(rs.getString("cod_estado_material"), rs.getString("nombre_estado_material")));
                }
                rs.close();
                con.close();

         } catch (Exception e) {
             e.printStackTrace();
         }
         return estadosMaterialList;
     }
     //inicio ale validacion
       public String verificarLote(){
         try {
                 Connection con = null;
                 con= Util.openConnection(con);
                 Statement st = con.createStatement();
                 String consulta = "select top 1 sa.COD_PROD from SALIDAS_ALMACEN sa where sa.COD_LOTE_PRODUCCION='"+salidasAlmacen.getCodLoteProduccion()+"' and len(sa.COD_LOTE_PRODUCCION)>0 and sa.estado_sistema = 1 and sa.cod_estado_salida_almacen = 1";

                 System.out.println("consulta verificar lote produccion" + consulta);
                 ResultSet rs = st.executeQuery(consulta);
                 setProductoHabilitado(false);
                 if(rs.next()){
                     salidasAlmacen.getComponentesProd().setCodCompprod(rs.getString("COD_PROD"));
                     setProductoHabilitado(true);
                 }
                 rs.close();
                 st.close();
                 con.close();

             //}
         } catch (Exception e) {
             e.printStackTrace();
         }
         return null;
     }
     //final ale validacion
     public String nroLote_change(){
         //inicio ale validacion
         mensaje="";
        if(salidasAlmacen.getCodLoteProduccion().length()==1)
                 {
            //final ale validacion
         try {
             //this.nroLote_onblur();
             mensaje = "";
             //if(salidasAlmacen.getCodLoteProduccion().length()==1){
                 Connection con = null;
                 con= Util.openConnection(con);
                 Statement st = con.createStatement();                 
                 String consulta = " select a.COD_AREA_EMPRESA,a.NOMBRE_AREA_EMPRESA,count(*) " +
                         " from FORMAS_FARMACEUTICAS_LOTE f  " +
                     " inner join componentes_prod cp on cp.COD_FORMA = f.COD_FORMA " +
                     " inner join AREAS_EMPRESA a on a.COD_AREA_EMPRESA = cp.COD_AREA_EMPRESA " +
                     " where f.COD_LOTE_FORMA = '"+salidasAlmacen.getCodLoteProduccion().substring(0,1)+"' " +
                     " group by a.cod_area_empresa, a.nombre_area_empresa order by count(*) desc ";
                 System.out.println("consulta lote forma farmaceutica area empresa " + consulta);
                 ResultSet rs = st.executeQuery(consulta);
                 if(rs.next()){
                     if(!rs.getString("COD_AREA_EMPRESA").equals(salidasAlmacen.getAreasEmpresa().getCodAreaEmpresa())){
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
             e.printStackTrace();
         }
        }
         this.verificarLote();
         return null;
     }
     public String nroLote_onblur(){
         try {
             mensaje = "";
             //if(salidasAlmacen.getCodLoteProduccion().length()==1){
                 Connection con = null;
                 con= Util.openConnection(con);
                 Statement st = con.createStatement();
                 String consulta = " select top 1 s.cod_prod,cp.nombre_prod_semiterminado from salidas_almacen s inner join componentes_prod cp on s.cod_prod = cp.cod_compprod where s.cod_lote_produccion = '"+salidasAlmacen.getCodLoteProduccion().trim()+"' and rtrim(ltrim(s.cod_lote_produccion))<>'' ";
                 System.out.println("consulta " + consulta);
                 ResultSet rs = st.executeQuery(consulta);
                 if(rs.next()){
                     if(!salidasAlmacen.getComponentesProd().getCodCompprod().equals(rs.getString("cod_prod"))){
                         salidasAlmacen.getComponentesProd().setCodCompprod(rs.getString("cod_prod"));
                         mensaje = "El codigo de lote corresponde al producto "+rs.getString("nombre_prod_semiterminado");
                     }
                     
                 }
                 rs.close();
                 st.close();
                 con.close();
             //}
         } catch (Exception e) {
             e.printStackTrace();
         }
         return null;
     }
     public String getCargarContenidoEditarSalidasAlmacen(){
         try {
             //inicio ale validacion
             setProductoHabilitado(false);
             //final ale validacion
             ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
             Map<String, Object> sessionMap = externalContext.getSessionMap();
             salidasAlmacen=(SalidasAlmacen)sessionMap.get("salidasAlmacenEditar");
             this.cargarAreasEmpresa();
             this.cargarComponentesProd();
             this.cargarTiposSalidasAlmacen();
             capitulosList = this.cargarCapitulos();
             gruposList.add(new SelectItem("0", "-NINGUNO-"));
             materialesList.add(new SelectItem("0", "-NINGUNO-"));
             presentacionesList.add(new SelectItem("0", "-NINGUNO-"));
             salidasAlmacenDetalleList.clear();
             estadosMaterialList = this.cargarEstadosMaterial();
             //cargado de informacion de salida de almacen
             salidasAlmacenDetalleList = this.listadoSalidasAlmacenDetalle(salidasAlmacen);
             salidasAlmacenDetalleAuxList =this.listadoSalidasAlmacenDetalle(salidasAlmacen); //una copia de seguridad
             //salidasAlmacenDetalleAuxList = (List)((ArrayList)salidasAlmacenDetalleList).clone() ;
             //salidasAlmacenDetalleAuxList = salidasAlmacenDetalleList.toArray();
             //salidasAlmacenDetalleAuxList = this.copiaLista(salidasAlmacenDetalleList);
             //salidasAlmacenDetalleAuxList.addAll(salidasAlmacenDetalleList);
             //Collections.copy(salidasAlmacenDetalleAuxList,salidasAlmacenDetalleList); //se debe tener los items originales para restaurar las cantidades en los ingresos afectados
         } catch (Exception e) {
             e.printStackTrace();
         }
         return null;
     }
     public List copiaLista(List lista){
         List listaCopia = new ArrayList();
         Iterator i1 = lista.iterator();
             while(i1.hasNext()){
                 SalidasAlmacenDetalle salidasAlmacenDetalleItem = new SalidasAlmacenDetalle();
                 salidasAlmacenDetalleItem = (SalidasAlmacenDetalle) i1.next();
                 listaCopia.add(salidasAlmacenDetalleItem.clone());
                 Iterator i2 = salidasAlmacenDetalleItem.getSalidasAlmacenDetalleIngresoList().iterator();
                 while(i2.hasNext()){
                     SalidasAlmacenDetalleIngreso salidasAlmacenDetalleIngresoItem = (SalidasAlmacenDetalleIngreso) i2.next();
                 }
             }
         return listaCopia;
     }
     public List listadoSalidasAlmacenDetalle(SalidasAlmacen salidasAlmacen){
        List salidasAlmacenDetalleList = new ArrayList();
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " SELECT COD_SALIDA_ALMACEN, COD_MATERIAL, CANTIDAD_SALIDA_ALMACEN, COD_UNIDAD_MEDIDA, COD_ESTADO_MATERIAL " +
                    " FROM SALIDAS_ALMACEN_DETALLE WHERE COD_SALIDA_ALMACEN = '"+salidasAlmacen.getCodSalidaAlmacen()+"'  ";

            consulta = " select s.COD_SALIDA_ALMACEN, s.COD_MATERIAL,mat.NOMBRE_MATERIAL, s.CANTIDAD_SALIDA_ALMACEN, s.COD_UNIDAD_MEDIDA,u.ABREVIATURA, s.COD_ESTADO_MATERIAL,e.NOMBRE_ESTADO_MATERIAL " +
                    " from SALIDAS_ALMACEN_DETALLE s  " +
                    " inner join MATERIALES mat on mat.COD_MATERIAL = s.COD_MATERIAL " +
                    " inner join UNIDADES_MEDIDA u on u.COD_UNIDAD_MEDIDA = s.COD_UNIDAD_MEDIDA " +
                    " inner join ESTADOS_MATERIAL e on e.COD_ESTADO_MATERIAL = s.COD_ESTADO_MATERIAL " +
                    " where s.COD_SALIDA_ALMACEN = '"+salidasAlmacen.getCodSalidaAlmacen()+"'  ";
            System.out.println("consulta" + consulta);

            System.out.println("consulta " + consulta);
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs=st.executeQuery(consulta);
            salidasAlmacenDetalleList.clear();
            while(rs.next()){
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
                //se debe colocar la cantidad disponible en almacen
                
                salidasAlmacenDetalleList.add(salidasAlmacenDetalle);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return salidasAlmacenDetalleList;
    }

     public List listadoSalidasAlmacenDetalleIngreso(SalidasAlmacenDetalle salidasAlmacenDetalle){
        List salidasAlmacenDetalleIngresoList = new ArrayList();
        ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " SELECT I.COD_INGRESO_ALMACEN,IAD.COD_MATERIAL,IADE.ETIQUETA,E.COD_ESTADO_MATERIAL,E.NOMBRE_ESTADO_MATERIAL,ESE.COD_EMPAQUE_SECUNDARIO_EXTERNO,ESE.NOMBRE_EMPAQUE_SECUNDARIO_EXTERNO, " +
                    " IADE.FECHA_VENCIMIENTO,I.NRO_INGRESO_ALMACEN,SEC.COD_SECCION,SEC.NOMBRE_SECCION,IADE.LOTE_MATERIAL_PROVEEDOR,S.CANTIDAD," +
                    " (IADE.cantidad_parcial - ( select ISNULL(sum(sadi.cantidad), 0) from SALIDAS_ALMACEN_DETALLE_INGRESO sadi " +
                    " INNER JOIN SALIDAS_ALMACEN_DETALLE SAD ON SAD.COD_SALIDA_ALMACEN = SADI.COD_SALIDA_ALMACEN AND SAD.COD_MATERIAL = SADI.COD_MATERIAL " +
                    " INNER JOIN SALIDAS_ALMACEN S ON SAD.COD_SALIDA_ALMACEN = S.COD_SALIDA_ALMACEN " +
                     " where sadi.cod_material = iade.cod_material and  sadi.cod_ingreso_almacen = iade.cod_ingreso_almacen and  sadi.etiqueta = iade.etiqueta and " +
                     " S.COD_ESTADO_SALIDA_ALMACEN = 1 AND S.ESTADO_SISTEMA = 1 AND S.COD_ALMACEN = '"+usuario.getAlmacenesGlobal().getCodAlmacen()+"' " +
                     ") ) + ( select isnull(sum(cantidad_devuelta), 0)  from DEVOLUCIONES_DETALLE_ETIQUETAS dde  where dde.cod_material = iade.cod_material and dde.cod_ingreso_almacen = iade.cod_ingreso_almacen and " +
                     " dde.etiqueta = iade.etiqueta and dde.cod_devolucion in ( select cod_devolucion from DEVOLUCIONES where cod_estado_devolucion = 1 " +
                     " and estado_sistema = 1 and  cod_almacen = '"+usuario.getAlmacenesGlobal().getCodAlmacen()+"' ) ) as cantidad_r " +
                    " FROM SALIDAS_ALMACEN_DETALLE_INGRESO S " +
                    " INNER JOIN INGRESOS_ALMACEN I ON I.COD_INGRESO_ALMACEN = S.COD_INGRESO_ALMACEN " +
                    " INNER JOIN INGRESOS_ALMACEN_DETALLE IAD ON IAD.COD_INGRESO_ALMACEN = I.COD_INGRESO_ALMACEN " +
                    " INNER JOIN INGRESOS_ALMACEN_DETALLE_ESTADO IADE ON  " +
                    "     IADE.COD_INGRESO_ALMACEN = IAD.COD_INGRESO_ALMACEN  " +
                    " AND IADE.COD_MATERIAL = IAD.COD_MATERIAL " +
                    " AND IADE.ETIQUETA = S.ETIQUETA " +
                    " AND IADE.COD_MATERIAL = S.COD_MATERIAL " +
                    " INNER JOIN ESTADOS_MATERIAL E ON E.COD_ESTADO_MATERIAL = IADE.COD_ESTADO_MATERIAL " +
                    " INNER JOIN EMPAQUES_SECUNDARIO_EXTERNO ESE ON ESE.COD_EMPAQUE_SECUNDARIO_EXTERNO = IADE.COD_EMPAQUE_SECUNDARIO_EXTERNO " +
                    " INNER JOIN SECCIONES SEC ON SEC.COD_SECCION = IAD.COD_SECCION " +
                    " WHERE S.COD_SALIDA_ALMACEN = '"+salidasAlmacenDetalle.getSalidasAlmacen().getCodSalidaAlmacen()+"' " +
                    " AND S.COD_MATERIAL = '"+salidasAlmacenDetalle.getMateriales().getCodMaterial()+"' ";

            consulta = " select 1 col, iade.cod_ingreso_almacen, iade.cod_material, iade.etiqueta,  iade.cod_estado_material, iade.cod_empaque_secundario_externo, " +
                     " iade.cantidad_parcial - ( select ISNULL(sum(sadi.cantidad), 0) from SALIDAS_ALMACEN_DETALLE_INGRESO sadi " +
                     " where sadi.cod_material = iade.cod_material and  sadi.cod_ingreso_almacen = iade.cod_ingreso_almacen and  sadi.etiqueta = iade.etiqueta and " +
                     " cod_salida_almacen in ( select cod_salida_almacen  from SALIDAS_ALMACEN   where cod_estado_salida_almacen = 1 and  estado_sistema = 1 and  cod_almacen = '"+usuario.getAlmacenesGlobal().getCodAlmacen()+"' " +
                     ") ) + ( select isnull(sum(cantidad_devuelta), 0)  from DEVOLUCIONES_DETALLE_ETIQUETAS dde  where dde.cod_material = iade.cod_material and dde.cod_ingreso_almacen = iade.cod_ingreso_almacen and " +
                     " dde.etiqueta = iade.etiqueta and dde.cod_devolucion in ( select cod_devolucion from DEVOLUCIONES where cod_estado_devolucion = 1 " +
                     " and estado_sistema = 1 and  cod_almacen = '"+usuario.getAlmacenesGlobal().getCodAlmacen()+"' ) ) as cantidad_r, iade.fecha_vencimiento, iade.lote_material_proveedor " +
                     " ,e.nombre_estado_material,ia.nro_ingreso_almacen,s.nombre_seccion,es.nombre_empaque_secundario_externo " +
                     " from ingresos_almacen_detalle_estado iade,  ingresos_almacen_detalle iad,  secciones s, almacenes a, ingresos_almacen ia,ESTADOS_MATERIAL e,EMPAQUES_SECUNDARIO_EXTERNO es " +
                     " WHERE a.cod_almacen = '"+usuario.getAlmacenesGlobal().getCodAlmacen()+"' and iade.cod_material = iad.cod_material and iade.cod_ingreso_almacen = iad.cod_ingreso_almacen and " +
                     " ia.estado_sistema = 1 and  ia.cod_ingreso_almacen = iad.cod_ingreso_almacen and ia.cod_estado_ingreso_almacen = 1 " +
                     " and ia.cod_almacen = a.cod_almacen and iad.cod_seccion = s.cod_seccion and s.cod_almacen = a.cod_almacen " +
                     " and iade.cantidad_restante > 0 and iad.cod_seccion = s.cod_seccion and s.cod_almacen = a.cod_almacen " +
                     " and iade.COD_MATERIAL = '"+salidasAlmacenDetalle.getMateriales().getCodMaterial() +"' " +
                     " and iade.COD_ESTADO_MATERIAL = 2  " +
                     " and e.cod_estado_material= iade.cod_estado_material  " +
                     " and es.cod_empaque_secundario_externo =  iade.cod_empaque_secundario_externo  ";

            consulta = "  select iade.cod_ingreso_almacen, iade.cod_material, iade.etiqueta,  iade.cod_estado_material, iade.cod_empaque_secundario_externo,iade.CANTIDAD_PARCIAL - " +
                    " (select isnull(SUM(sadi.CANTIDAD),0) from SALIDAS_ALMACEN_DETALLE_INGRESO sadi inner join SALIDAS_ALMACEN_DETALLE sad on sadi.COD_SALIDA_ALMACEN = sad.COD_SALIDA_ALMACEN and sadi.COD_MATERIAL = sad.COD_MATERIAL" +
                    " inner join SALIDAS_ALMACEN s on s.COD_SALIDA_ALMACEN = sad.COD_SALIDA_ALMACEN" +
                    " where sadi.COD_INGRESO_ALMACEN = iade.COD_INGRESO_ALMACEN and sadi.COD_MATERIAL = iade.COD_MATERIAL and sadi.ETIQUETA = iade.ETIQUETA" +
                    " and s.COD_ESTADO_SALIDA_ALMACEN = 1 and s.ESTADO_SISTEMA = 1 and s.COD_ALMACEN = '"+usuario.getAlmacenesGlobal().getCodAlmacen()+"') " +
                    " +(select isnull(SUM(dde.CANTIDAD_DEVUELTA),0) from DEVOLUCIONES_DETALLE_ETIQUETAS dde inner join DEVOLUCIONES_DETALLE dd on dd.COD_DEVOLUCION = dde.COD_DEVOLUCION and dd.COD_MATERIAL = dde.COD_MATERIAL" +
                    " inner join DEVOLUCIONES d on d.COD_DEVOLUCION = dd.COD_DEVOLUCION" +
                    " where  dde.COD_INGRESO_ALMACEN = iade.COD_INGRESO_ALMACEN and dde.COD_MATERIAL = iade.COD_MATERIAL and dde.ETIQUETA = iade.ETIQUETA" +
                    " and d.COD_ESTADO_DEVOLUCION = 1 and d.ESTADO_SISTEMA = 1 and d.COD_ALMACEN = '"+usuario.getAlmacenesGlobal().getCodAlmacen()+"') cantidad_r, iade.fecha_vencimiento, iade.lote_material_proveedor " +
                    " ,e.nombre_estado_material,ia.nro_ingreso_almacen,sec.nombre_seccion,ese.nombre_empaque_secundario_externo ," +
                    " (select sadi1.CANTIDAD from SALIDAS_ALMACEN_DETALLE_INGRESO sadi1 where sadi1.COD_INGRESO_ALMACEN=iade.COD_INGRESO_ALMACEN and  sadi1.COD_MATERIAL = iade.COD_MATERIAL and sadi1.ETIQUETA = iade.ETIQUETA" +
                    "  and sadi1.COD_SALIDA_ALMACEN = '"+salidasAlmacenDetalle.getSalidasAlmacen().getCodSalidaAlmacen()+"') cantidad_sacar" +
                    " from INGRESOS_ALMACEN_DETALLE_ESTADO iade " +
                    " inner join INGRESOS_ALMACEN_DETALLE iad on iad.COD_INGRESO_ALMACEN = iade.COD_INGRESO_ALMACEN and iad.COD_MATERIAL = iade.COD_MATERIAL" +
                    " inner join INGRESOS_ALMACEN ia on ia.COD_INGRESO_ALMACEN = iad.COD_INGRESO_ALMACEN" +
                    " inner join ALMACENES a on a.COD_ALMACEN = ia.COD_ALMACEN" +
                    " inner join ESTADOS_MATERIAL e on e.COD_ESTADO_MATERIAL = iade.COD_ESTADO_MATERIAL" +
                    " inner join EMPAQUES_SECUNDARIO_EXTERNO ese on ese.COD_EMPAQUE_SECUNDARIO_EXTERNO = iade.COD_EMPAQUE_SECUNDARIO_EXTERNO " +
                    " inner join secciones sec on sec.COD_SECCION = iad.COD_SECCION" +
                    " where iade.COD_ESTADO_MATERIAL in (2,6) and a.COD_ALMACEN = '"+usuario.getAlmacenesGlobal().getCodAlmacen()+"'  and ia.COD_ESTADO_INGRESO_ALMACEN = 1 and ia.ESTADO_SISTEMA = 1   " + //and iade.CANTIDAD_RESTANTE>0
                    " and iade.COD_MATERIAL = '"+salidasAlmacenDetalle.getMateriales().getCodMaterial() +"' and ia.ESTADO_SISTEMA = 1 " +
                    " order by  iade.fecha_vencimiento asc,ia.nro_ingreso_almacen asc "; //and iade.COD_ESTADO_MATERIAL = 2

            System.out.println("consulta " + consulta);
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs=st.executeQuery(consulta);
            salidasAlmacenDetalleIngresoList.clear();
            salidasAlmacenDetalle.setCantidadDisponible(0);
            while(rs.next()){
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
                salidasAlmacenDetalleIngreso.setCantidadDisponible(salidasAlmacenDetalleIngreso.getCantidadDisponible()+salidasAlmacenDetalleIngreso.getCantidadSacar());
                salidasAlmacenDetalleIngreso.setCantidad(salidasAlmacenDetalleIngreso.getCantidad()+salidasAlmacenDetalleIngreso.getCantidadSacar());                

                //salidasAlmacenDetalleIngresoList.add(salidasAlmacenDetalleIngreso);
                System.out.println(" cantidad disponible " + salidasAlmacenDetalleIngreso.getCantidadDisponible() + " cantidad sacar " + salidasAlmacenDetalleIngreso.getCantidadSacar() );
                if(salidasAlmacenDetalleIngreso.getCantidadDisponible()>0.001 && salidasAlmacenDetalleIngreso.getCantidadDisponible()-salidasAlmacenDetalleIngreso.getCantidadSacar()>=0){
                    salidasAlmacenDetalle.setCantidadDisponible(salidasAlmacenDetalle.getCantidadDisponible()+salidasAlmacenDetalleIngreso.getCantidadDisponible()); //se van sumando las cantidades parciales dispnibles al detalle de salida de almacen
                    salidasAlmacenDetalleIngresoList.add(salidasAlmacenDetalleIngreso);                    
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return salidasAlmacenDetalleIngresoList;
    }
     public String guardarEditarSalidasAlmacen_action()throws SQLException{
         mensaje = "";
         Connection con = null;
         con = Util.openConnection(con);
         try {
             con.setAutoCommit(false);
             if(salidasAlmacenDetalleList.size()==0){
                 mensaje = " no existe detalle de salida ";
                 return null;
             }
            
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            String consulta = " UPDATE SALIDAS_ALMACEN  SET  COD_TIPO_SALIDA_ALMACEN = '"+salidasAlmacen.getTiposSalidasAlmacen().getCodTipoSalidaAlmacen()+"'," +
                    "  COD_AREA_EMPRESA = '"+salidasAlmacen.getAreasEmpresa().getCodAreaEmpresa()+"'," +
                    "  OBS_SALIDA_ALMACEN = '"+salidasAlmacen.getObsSalidaAlmacen()+"'," +
                    "  COD_LOTE_PRODUCCION = '"+salidasAlmacen.getCodLoteProduccion()+"'," +
                    "  orden_trabajo = '"+salidasAlmacen.getOrdenTrabajo()+"',cod_prod = '"+salidasAlmacen.getComponentesProd().getCodCompprod()+"',COD_PRESENTACION = '"+salidasAlmacen.getPresentacionesProducto().getCodPresentacion()+"' " +
                    " WHERE   COD_SALIDA_ALMACEN = '"+salidasAlmacen.getCodSalidaAlmacen()+"'; ";
            System.out.println("consulta " + consulta);
            st.executeUpdate(consulta);
             //restaurar las cantidades en los detalles de ingresos afectados
             Iterator i1 = salidasAlmacenDetalleAuxList.iterator();
             while(i1.hasNext()){
                 SalidasAlmacenDetalle salidasAlmacenDetalleItem = (SalidasAlmacenDetalle)i1.next();
                 Iterator i2 = salidasAlmacenDetalleItem.getSalidasAlmacenDetalleIngresoList().iterator();
                 while(i2.hasNext()){
                     SalidasAlmacenDetalleIngreso salidasAlmacenDetalleIngresoItem = (SalidasAlmacenDetalleIngreso) i2.next();
                     if(salidasAlmacenDetalleIngresoItem.getCantidadSacar()>0 && salidasAlmacenDetalleIngresoItem.getCantidadDisponible()>0){
                     consulta = " update INGRESOS_ALMACEN_DETALLE_ESTADO set cantidad_restante = cantidad_restante + '"+salidasAlmacenDetalleIngresoItem.getCantidadSacar()+"' " +
                             " where COD_INGRESO_ALMACEN = '"+salidasAlmacenDetalleIngresoItem.getIngresosAlmacenDetalleEstado().getIngresosAlmacen().getCodIngresoAlmacen()+"' " +
                             " and COD_MATERIAL = '"+salidasAlmacenDetalleIngresoItem.getMateriales().getCodMaterial()+"' " +
                             " and ETIQUETA = '"+salidasAlmacenDetalleIngresoItem.getIngresosAlmacenDetalleEstado().getEtiqueta()+"' " +
                             "  ";
                     System.out.println("consulta " + consulta);
                     st.executeUpdate(consulta);
                     }
                 }
             }
            
             consulta = "";
             consulta = " DELETE FROM SALIDAS_ALMACEN_DETALLE WHERE COD_SALIDA_ALMACEN = '"+salidasAlmacen.getCodSalidaAlmacen()+"' ";
             System.out.println("consulta " + consulta);
             st.executeUpdate(consulta);
             consulta = " DELETE FROM SALIDAS_ALMACEN_DETALLE_INGRESO WHERE COD_SALIDA_ALMACEN = '"+salidasAlmacen.getCodSalidaAlmacen()+"' ";
             System.out.println("consulta " + consulta);
             st.executeUpdate(consulta);

             //iteracion de detalle
                   Iterator i = salidasAlmacenDetalleList.iterator();

                   while(i.hasNext()){
                       SalidasAlmacenDetalle salidasAlmacenDetalleItem = (SalidasAlmacenDetalle)i.next();
                       consulta = " INSERT INTO SALIDAS_ALMACEN_DETALLE ( COD_SALIDA_ALMACEN, COD_MATERIAL, CANTIDAD_SALIDA_ALMACEN, COD_UNIDAD_MEDIDA, " +
                               "  COD_ESTADO_MATERIAL )  VALUES (   '"+salidasAlmacen.getCodSalidaAlmacen()+"', '"+salidasAlmacenDetalleItem.getMateriales().getCodMaterial() +"'," +
                               "  '"+salidasAlmacenDetalleItem.getCantidadSalidaAlmacen() +"',   '"+salidasAlmacenDetalleItem.getUnidadesMedida().getCodUnidadMedida() +"', " +
                               "   '"+salidasAlmacenDetalleItem.getEstadosMaterial().getCodEstadoMaterial()+"' ) ";
                               System.out.println("consulta " + consulta);
                               st.executeUpdate(consulta);
                       Iterator i3 = salidasAlmacenDetalleItem.getSalidasAlmacenDetalleIngresoList().iterator();


                       while(i3.hasNext()){
                           SalidasAlmacenDetalleIngreso salidasAlmacenDetalleIngreso = (SalidasAlmacenDetalleIngreso) i3.next();

                           if(salidasAlmacenDetalleIngreso.getCantidadSacar()>0){
                               consulta = "  INSERT INTO SALIDAS_ALMACEN_DETALLE_INGRESO ( COD_SALIDA_ALMACEN,  COD_MATERIAL, COD_INGRESO_ALMACEN,  ETIQUETA, " +
                               " COSTO_SALIDA,   FECHA_VENCIMIENTO,   CANTIDAD,   COSTO_SALIDA_ACTUALIZADO,   FECHA_ACTUALIZACION,   COSTO_SALIDA_ACTUALIZADO_FINAL )  " +
                               " VALUES ( '"+salidasAlmacen.getCodSalidaAlmacen()+"',   '"+salidasAlmacenDetalleItem.getMateriales().getCodMaterial() +"',   " +
                               " '"+salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getIngresosAlmacen().getCodIngresoAlmacen()+"', " +
                               " '"+salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getEtiqueta()  +"', '"+salidasAlmacenDetalleIngreso.getCostoSalida()+"', '"+salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getFechaVencimiento()+"', " +
                               " '"+salidasAlmacenDetalleIngreso.getCantidadSacar()+"', '"+salidasAlmacenDetalleIngreso.getCostoSalidaActualizado()+"', " +
                               " null,  '"+salidasAlmacenDetalleIngreso.getCostoSalidaActualizadoFinal()+"' ) ";
                               System.out.println("consulta " + consulta);
                               st.executeUpdate(consulta);
                               
                               consulta = " update ingresos_almacen_detalle_estado set cantidad_restante=cantidad_restante - "+salidasAlmacenDetalleIngreso.getCantidadSacar()+" " +
                                       " where cod_material="+salidasAlmacenDetalleItem.getMateriales().getCodMaterial()+" and etiqueta="+salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getEtiqueta()+" " +
                                       " and cod_ingreso_almacen='"+salidasAlmacenDetalleIngreso.getIngresosAlmacenDetalleEstado().getIngresosAlmacen().getCodIngresoAlmacen()+"' ";
                               System.out.println("consulta " + consulta );
                               st.executeUpdate(consulta);
                           }
                       }
                   }
                   con.commit();
                   st.close();
                   con.close();
         } catch (Exception e) {
             e.printStackTrace();
             con.rollback();
         }finally{
             con.close();
         }
         return null;

     }
     //inicio alejandro orden de compra
     public String guardarAsociacionOC()
     {
         for(OrdenesCompra current:ordenesCompraList)
         {
             if(current.getChecked())
             {
                 salidasAlmacen.getOrdenesCompra().setCodOrdenCompra(current.getCodOrdenCompra());
             }
         }
         return null;
     }
     public String cargarDetalleOrdenCompra()
     {
         OrdenesCompra current=(OrdenesCompra)ordenesCompraDataTable.getRowData();
                 try{
                  String consulta="select ISNULL(m.NOMBRE_MATERIAL,'') AS MATERIAL,ocd.CANTIDAD_NETA,ISNULL(um.ABREVIATURA,'') AS ABREV,ocd.PRECIO_UNITARIO,(ocd.PRECIO_UNITARIO*ocd.CANTIDAD_NETA) as subtotal,ocd.DESCRIPCION "+
                                  " from ORDENES_COMPRA_DETALLE ocd left outer join MATERIALES m on ocd.COD_MATERIAL=m.COD_MATERIAL "+
                                  " left outer join UNIDADES_MEDIDA um on ocd.COD_UNIDAD_MEDIDA=um.COD_UNIDAD_MEDIDA"+
                                  " where ocd.COD_ORDEN_COMPRA="+current.getCodOrdenCompra();
                  System.out.println("consulta "+consulta);
                  Connection con=null;
                  con=Util.openConnection(con);
                  Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                  ResultSet res=st.executeQuery(consulta);
                  ordenesCompraDetalleList.clear();
                  while(res.next())
                  {
                      OrdenesCompraDetalle nuevo=new OrdenesCompraDetalle();
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
                 }
                 catch(SQLException ex)
                 {
                     ex.printStackTrace();
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
     public String cargarOrdenesCompra()
     {

         String consulta="";

         try{
             Connection con =null;
             con=Util.openConnection(con);
              consulta=" select * from (select ROW_NUMBER() OVER (ORDER BY oc.FECHA_EMISION desc) as 'FILAS'," +
                      " oc.COD_ORDEN_COMPRA,oc.FECHA_EMISION,oc.NRO_ORDEN_COMPRA,p.NOMBRE_PROVEEDOR,"+
                           "r.NOMBRE_REPRESENTANTE,pai.NOMBRE_PAIS,ae.NOMBRE_AREA_EMPRESA,tc.NOMBRE_TIPO_COMPRA,"+
                           "(per.AP_PATERNO_PERSONAL+' '+per.AP_MATERNO_PERSONAL+' '+per.NOMBRE_PILA) as perResponsable,oc.OBS_ORDEN_COMPRA"+
                           " from ORDENES_COMPRA oc left outer join PROVEEDORES p on oc.COD_PROVEEDOR=p.COD_PROVEEDOR"+
                            " left outer join PERSONAL per on oc.COD_RESPONSABLE_COMPRAS=per.COD_PERSONAL"+
                            " left outer join REPRESENTANTES r on oc.COD_REPRESENTANTE=r.COD_REPRESENTANTE"+
                            " left outer join paises pai on pai.COD_PAIS=p.COD_PAIS"+
                            " left outer join AREAS_EMPRESA ae on ae.COD_AREA_EMPRESA=oc.DIVISION"+
                            " left outer join TIPOS_COMPRA tc on tc.COD_TIPO_COMPRA=oc.COD_TIPO_COMPRA"+
                            " where oc.COD_GESTION IN(select top 1 g.COD_GESTION from GESTIONES g where g.GESTION_ESTADO=1)"+
                            " and oc.ESTADO_SISTEMA=1 ";
                 if(!codMaterial.equals("0"))
                 {
                     consulta+=" and oc.COD_ORDEN_COMPRA in (select ocd.COD_ORDEN_COMPRA from ORDENES_COMPRA_DETALLE ocd where ocd.COD_MATERIAL ='"+codMaterial+"') ";
                 }
                 else
                 {
                     if(!codGrupo.equals("0"))
                     {
                         consulta+=" and oc.COD_ORDEN_COMPRA in (select ocd.COD_ORDEN_COMPRA from ORDENES_COMPRA_DETALLE ocd where ocd.COD_MATERIAL in(select m.COD_MATERIAL from MATERIALES m where m.COD_GRUPO in ('"+codGrupo+"'))) ";
                     }
                     else
                     {
                         if(!codCapitulo.equals("0"))
                         {
                             consulta+=" and oc.COD_ORDEN_COMPRA in (select ocd.COD_ORDEN_COMPRA from ORDENES_COMPRA_DETALLE ocd where ocd.COD_MATERIAL in(select m.COD_MATERIAL from MATERIALES m where m.COD_GRUPO in (select gr.COD_GRUPO from grupos gr where gr.COD_CAPITULO in ('"+codCapitulo+"')))) ";
                         }
                     }
                 }
                 if(fechaInicio!=null && fechaFinal!=null)
                 {
                     SimpleDateFormat sdt= new SimpleDateFormat("yyyy/MM/dd");
                     consulta+=" and oc.FECHA_EMISION BETWEEN '"+sdt.format(fechaInicio)+" 00:00:00' and '"+sdt.format(fechaFinal)+" 23:59:59' ";
                 }
                 if(!filtroOrdenCompra.getProveedores().getCodProveedor().equals("0"))
                 {
                     consulta+=" and oc.COD_PROVEEDOR ='"+filtroOrdenCompra.getProveedores().getCodProveedor()+"' ";
                 }
                 if(!filtroOrdenCompra.getResponsableCompras().getCodPersonal().equals("0"))
                 {
                     consulta+=" and oc.COD_RESPONSABLE_COMPRAS ='"+filtroOrdenCompra.getResponsableCompras().getCodPersonal()+"' ";
                 }
                 if(filtroOrdenCompra.getRepresentantes().getCodRepresentante()!=0)
                 {
                     consulta+=" and oc.COD_REPRESENTANTE ='"+filtroOrdenCompra.getRepresentantes().getCodRepresentante()+"' ";
                 }
              ManagedAccesoSistema user=(ManagedAccesoSistema)Util.getSessionBean("ManagedAccesoSistema");
              consulta+=" and oc.COD_ALMACEN_ENTREGA='"+user.getAlmacenesGlobal().getCodAlmacen()+"'";

              consulta+=" ) AS listado_ordenes_compra where FILAS BETWEEN " + begin + " AND " + end;
              System.out.println("consulta "+consulta);
             Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
             ResultSet res=st.executeQuery(consulta);
             ordenesCompraList.clear();
             ordenesCompraDetalleList.clear();
             while(res.next())
             {
                 OrdenesCompra nuevoOrdenCompra=new OrdenesCompra();
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
             this.cantidadfilas=ordenesCompraList.size();
             res.close();
             st.close();
             con.close();
         }
         catch(SQLException ex)
         {
             ex.printStackTrace();
         }

         return null;
     }
     public String grupos_OC()
     {
         try{

         this.gruposList=this.cargarGrupos(Integer.valueOf(codCapitulo));
         }
         catch(Exception ex)
         {
             ex.printStackTrace();;
         }
         return null;
     }
     public String materiales_OC()
     {
         try{
         this.materialesList=this.cargarMateriales(Integer.parseInt(codGrupo));
         }
         catch(Exception ex)
         {
             ex.printStackTrace();
         }
         return null;
     }
     private void cargarPersonal()
     {
         try
         {
             Connection con=null;
              con=Util.openConnection(con);
              Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
              String consulta="select p.COD_PERSONAL,(p.AP_PATERNO_PERSONAL+' '+p.AP_MATERNO_PERSONAL+' '+p.NOMBRE_PILA) as nombre from PERSONAL p order by p.AP_PATERNO_PERSONAL,p.AP_MATERNO_PERSONAL,p.NOMBRE_PILA";
              ResultSet res=st.executeQuery(consulta);
              personalList.clear();
              personalList.add(new SelectItem("0","-TODOS-"));
              while(res.next())
              {
                  personalList.add(new SelectItem(res.getString("COD_PERSONAL"),res.getString("nombre")));
              }
              res.close();
              st.close();
              con.close();
         }
         catch(SQLException ex)
         {
             ex.printStackTrace();
         }
     }
     private void cargarRepresentantes()
     {
         try
         {
             Connection con=null;
             con=   Util.openConnection(con);
             Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
             String consulta="select r.COD_REPRESENTANTE,r.NOMBRE_REPRESENTANTE from REPRESENTANTES r where r.COD_ESTADO_REGISTRO=1 order by r.NOMBRE_REPRESENTANTE";
             ResultSet res=st.executeQuery(consulta);
             representantesList.clear();
             representantesList.add(new SelectItem(0,"-TODOS-"));
             while(res.next())
             {
                 representantesList.add(new SelectItem(res.getInt("COD_REPRESENTANTE"),res.getString("NOMBRE_REPRESENTANTE")));
             }
             res.close();
             st.close();
             con.close();

         }
         catch(SQLException ex)
         {
             ex.printStackTrace();
         }
     }
     public String cargarOC()
     {
         this.capitulosList= this.cargarCapitulos();
         this.codCapitulo="0";
         this.cargarProveedores();
         this.cargarPersonal();
         this.cargarRepresentantes();
         fechaInicio= null;
         fechaFinal= null;
         this.cargarRepresentantes();
         gruposList.clear();
         gruposList.add(new SelectItem("0","-NINGUNO-"));
         materialesList.clear();
         materialesList.add(new SelectItem("0","-NINGUNO-"));
         codGrupo="0";
         codMaterial="0";
         filtroOrdenCompra.getProveedores().setCodProveedor("0");
         filtroOrdenCompra.getRepresentantes().setCodRepresentante(0);
         filtroOrdenCompra.getResponsableCompras().setCodPersonal("0");
         this.cargarOrdenesCompra();
         return null;
     }
      public void cargarProveedores(){
        try {

            String consulta = "select m.cod_proveedor, m.nombre_proveedor from proveedores m where m.cod_estado_registro=1 order by m.nombre_proveedor " ;
            System.out.println("consulta " + consulta);


            Connection con=null;
            con = Util.openConnection(con);

            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);

            proveedoresList.clear();
            proveedoresList.add(new SelectItem("0","-TODOS-"));
            while(rs.next()){

                proveedoresList.add(new SelectItem(rs.getString(1),rs.getString(2)));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     //final alejandro orden de compra
}
