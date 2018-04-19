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
import com.cofar.bean.Materiales;
import com.cofar.bean.ProgramaProduccionDevolucionMaterial;
import com.cofar.bean.SalidasAlmacen;
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
import org.richfaces.component.html.HtmlDataTable;

/**
 *
 * @author hvaldivia
 */

public class ManagedDevolucionAlmacen extends ManagedBean {
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
    List devolucionesDetalleEtiquetasList = new ArrayList();
    HtmlDataTable devolucionesDetalleDataTable = new HtmlDataTable();
    DevolucionesDetalle devolucionesDetalle = new DevolucionesDetalle();
    String mensaje = "";
    Almacenes almacenesFrv = new Almacenes();
    HtmlDataTable salidasAlmacenDataTable = new HtmlDataTable();
    IngresosAlmacen ingresosAlmacen = new IngresosAlmacen();
    // inicio alejandro buscador
    private Devoluciones buscarDevolucion= new Devoluciones();
    private Materiales buscarMaterial= new Materiales();
    private Date fechaInicio= new Date();
    private Date fechaFinal= new Date();
    private List<SelectItem> listaCapitulos= new ArrayList<SelectItem>();
    private List<SelectItem> listagrupos= new ArrayList<SelectItem>();
    private List<SelectItem> listaMateriales= new ArrayList<SelectItem>();
    private List<SelectItem> listaTiposSalida= new ArrayList<SelectItem>();
    private List<SelectItem> listaEstadosDevoluciones= new ArrayList<SelectItem>();
    private List<SelectItem> listaAreasEmpresa= new ArrayList<SelectItem>();
    //final alejandro buscador
    //inicio ale devoluciones prod
    private List<ProgramaProduccionDevolucionMaterial> programaProdDevMatList= new ArrayList<ProgramaProduccionDevolucionMaterial>();
    private HtmlDataTable programaProdDevMatDataTable= new HtmlDataTable();
    private ProgramaProduccionDevolucionMaterial currentProgProdDevMaterial= new ProgramaProduccionDevolucionMaterial();
    private ProgramaProduccionDevolucionMaterial currentProgProdDevEditar= new ProgramaProduccionDevolucionMaterial();

    private HtmlDataTable salidaAlmacenProgDevDataTable= new HtmlDataTable();
    private SalidasAlmacen nuevaSalidaAlmacen= new SalidasAlmacen();
    private ProgramaProduccionDevolucionMaterial progProdDevMatCurrent= new ProgramaProduccionDevolucionMaterial();
    private HtmlDataTable devolucionesProgProdDevMat= new HtmlDataTable();
    private List<DevolucionesDetalle> devolucionesDetalleMatList= new ArrayList<DevolucionesDetalle>();
    private DevolucionesDetalle devolucionesDetalleMat = new DevolucionesDetalle();
    private boolean  administradorAlmacen=false;
    private float cantidadDevuelta=0f;
    private float cantidadADevolver=0f;
    private float cantMalasDevueltas=0f;
    private float cantMalasADevolver=0f;
    //final ale devoluciones prod
    
    public List getDevolucionesList() {
        return devolucionesList;
    }

    public void setDevolucionesList(List devolucionesList) {
        this.devolucionesList = devolucionesList;
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

    public Devoluciones getBuscarDevolucion() {
        return buscarDevolucion;
    }

    public void setBuscarDevolucion(Devoluciones buscarDevolucion) {
        this.buscarDevolucion = buscarDevolucion;
    }

    public Materiales getBuscarMaterial() {
        return buscarMaterial;
    }

    public void setBuscarMaterial(Materiales buscarMaterial) {
        this.buscarMaterial = buscarMaterial;
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

    public List<SelectItem> getListaAreasEmpresa() {
        return listaAreasEmpresa;
    }

    public void setListaAreasEmpresa(List<SelectItem> listaAreasEmpresa) {
        this.listaAreasEmpresa = listaAreasEmpresa;
    }

    public List<SelectItem> getListaCapitulos() {
        return listaCapitulos;
    }

    public void setListaCapitulos(List<SelectItem> listaCapitulos) {
        this.listaCapitulos = listaCapitulos;
    }

    public List<SelectItem> getListaEstadosDevoluciones() {
        return listaEstadosDevoluciones;
    }

    public void setListaEstadosDevoluciones(List<SelectItem> listaEstadosDevoluciones) {
        this.listaEstadosDevoluciones = listaEstadosDevoluciones;
    }

    public List<SelectItem> getListaMateriales() {
        return listaMateriales;
    }

    public void setListaMateriales(List<SelectItem> listaMateriales) {
        this.listaMateriales = listaMateriales;
    }

    public List<SelectItem> getListaTiposSalida() {
        return listaTiposSalida;
    }

    public void setListaTiposSalida(List<SelectItem> listaTiposSalida) {
        this.listaTiposSalida = listaTiposSalida;
    }

    public List<SelectItem> getListagrupos() {
        return listagrupos;
    }

    public void setListagrupos(List<SelectItem> listagrupos) {
        this.listagrupos = listagrupos;
    }

    public ProgramaProduccionDevolucionMaterial getCurrentProgProdDevEditar() {
        return currentProgProdDevEditar;
    }

    public void setCurrentProgProdDevEditar(ProgramaProduccionDevolucionMaterial currentProgProdDevEditar) {
        this.currentProgProdDevEditar = currentProgProdDevEditar;
    }

    public ProgramaProduccionDevolucionMaterial getCurrentProgProdDevMaterial() {
        return currentProgProdDevMaterial;
    }

    public void setCurrentProgProdDevMaterial(ProgramaProduccionDevolucionMaterial currentProgProdDevMaterial) {
        this.currentProgProdDevMaterial = currentProgProdDevMaterial;
    }

    public DevolucionesDetalle getDevolucionesDetalleMat() {
        return devolucionesDetalleMat;
    }

    public void setDevolucionesDetalleMat(DevolucionesDetalle devolucionesDetalleMat) {
        this.devolucionesDetalleMat = devolucionesDetalleMat;
    }

    public List<DevolucionesDetalle> getDevolucionesDetalleMatList() {
        return devolucionesDetalleMatList;
    }

    public void setDevolucionesDetalleMatList(List<DevolucionesDetalle> devolucionesDetalleMatList) {
        this.devolucionesDetalleMatList = devolucionesDetalleMatList;
    }

    public HtmlDataTable getDevolucionesProgProdDevMat() {
        return devolucionesProgProdDevMat;
    }

    public void setDevolucionesProgProdDevMat(HtmlDataTable devolucionesProgProdDevMat) {
        this.devolucionesProgProdDevMat = devolucionesProgProdDevMat;
    }

    public SalidasAlmacen getNuevaSalidaAlmacen() {
        return nuevaSalidaAlmacen;
    }

    public void setNuevaSalidaAlmacen(SalidasAlmacen nuevaSalidaAlmacen) {
        this.nuevaSalidaAlmacen = nuevaSalidaAlmacen;
    }

    public ProgramaProduccionDevolucionMaterial getProgProdDevMatCurrent() {
        return progProdDevMatCurrent;
    }

    public void setProgProdDevMatCurrent(ProgramaProduccionDevolucionMaterial progProdDevMatCurrent) {
        this.progProdDevMatCurrent = progProdDevMatCurrent;
    }

    public HtmlDataTable getProgramaProdDevMatDataTable() {
        return programaProdDevMatDataTable;
    }

    public void setProgramaProdDevMatDataTable(HtmlDataTable programaProdDevMatDataTable) {
        this.programaProdDevMatDataTable = programaProdDevMatDataTable;
    }

    public List<ProgramaProduccionDevolucionMaterial> getProgramaProdDevMatList() {
        return programaProdDevMatList;
    }

    public void setProgramaProdDevMatList(List<ProgramaProduccionDevolucionMaterial> programaProdDevMatList) {
        this.programaProdDevMatList = programaProdDevMatList;
    }

    public HtmlDataTable getSalidaAlmacenProgDevDataTable() {
        return salidaAlmacenProgDevDataTable;
    }

    public void setSalidaAlmacenProgDevDataTable(HtmlDataTable salidaAlmacenProgDevDataTable) {
        this.salidaAlmacenProgDevDataTable = salidaAlmacenProgDevDataTable;
    }

    /** Creates a new instance of ManagedDevolucionAlmacen */
    public ManagedDevolucionAlmacen() {
    }
    public String getCargarContenidoDevolucionAlmacen(){
        try {
            begin = 1;
            end = 10;
            //inicio alejandro buscador
            this.initBuscador();
            //final alejandro buscador
            this.listadoDevolucionesAlmacen();
            componentesProdList = this.listaComponentesProd();
            cantidadfilas = devolucionesList.size();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public void listadoDevolucionesAlmacen(){
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = "select * from (select ROW_NUMBER() OVER (order by d.NRO_DEVOLUCION desc) as 'FILAS', d.COD_DEVOLUCION," +
                    " d.NRO_DEVOLUCION,e.COD_ESTADO_DEVOLUCION,e.NOMBRE_ESTADO_DEVOLUCION,d.OBS_DEVOLUCION, " +
                    " i.COD_ALMACEN,cp.nombre_prod_semiterminado,s.NRO_SALIDA_ALMACEN,s.COD_LOTE_PRODUCCION, " +
                    " i.NRO_INGRESO_ALMACEN,i.FECHA_INGRESO_ALMACEN,i.COD_INGRESO_ALMACEN,a.NOMBRE_ALMACEN,f.NOMBRE_FILIAL,p.AP_PATERNO_PERSONAL, " +
                    " p.AP_MATERNO_PERSONAL,p.NOMBRES_PERSONAL,s.cod_salida_almacen " +
                    " from DEVOLUCIONES d  " +
                    " inner join SALIDAS_ALMACEN s on s.COD_SALIDA_ALMACEN = d.COD_SALIDA_ALMACEN " +
                    " inner join INGRESOS_ALMACEN i on i.COD_DEVOLUCION = d.COD_DEVOLUCION " +
                    " inner join ESTADOS_DEVOLUCIONES e on e.COD_ESTADO_DEVOLUCION = d.COD_ESTADO_DEVOLUCION " +
                    " inner join ALMACENES a on a.COD_ALMACEN = i.COD_ALMACEN " +
                    " inner join FILIALES f on f.COD_FILIAL = a.COD_FILIAL " +
                    " inner join PERSONAL p on p.COD_PERSONAL = i.COD_PERSONAL " +
                    " left outer join COMPONENTES_PROD cp on cp.COD_COMPPROD = s.COD_PROD " +
                    " where  " + //d.COD_ESTADO_DEVOLUCION = 1 and d.COD_GESTION = '"+usuario.getGestionesGlobal().getCodGestion()+"'
                    " d.COD_ALMACEN = '"+usuario.getAlmacenesGlobal().getCodAlmacen()+"'  and i.ESTADO_SISTEMA = 1 and d.ESTADO_SISTEMA = 1 ";
            //inicio alejandro buscador
                    if(fechaInicio!=null&&fechaFinal!=null)
                    {
                        SimpleDateFormat sdf= new SimpleDateFormat("yyyy/MM/dd");
                        consulta+=" and i.FECHA_INGRESO_ALMACEN BETWEEN '"+sdf.format(fechaInicio)+" 00:00:00' and '"+sdf.format(fechaFinal)+" 23:59:59'";
                    }
                    if(buscarDevolucion.getNroDevolucion()>0)
                    {
                        consulta+=" and d.NRO_DEVOLUCION='"+buscarDevolucion.getNroDevolucion()+"'";
                    }
                    if(buscarDevolucion.getSalidasAlmacen().getNroSalidaAlmacen()>0)
                    {
                        consulta+=" and s.NRO_SALIDA_ALMACEN='"+buscarDevolucion.getSalidasAlmacen().getNroSalidaAlmacen()+"'";
                    }
                    if(!buscarDevolucion.getSalidasAlmacen().getCodLoteProduccion().equals(""))
                    {
                        consulta+=" and s.COD_LOTE_PRODUCCION='"+buscarDevolucion.getSalidasAlmacen().getCodLoteProduccion()+"'";
                    }
                    if(buscarDevolucion.getSalidasAlmacen().getTiposSalidasAlmacen().getCodTipoSalidaAlmacen()>0)
                    {
                        consulta+=" and s.COD_TIPO_SALIDA_ALMACEN='"+buscarDevolucion.getSalidasAlmacen().getTiposSalidasAlmacen().getCodTipoSalidaAlmacen()+"'";
                    }
                    if(buscarDevolucion.getEstadosDevoluciones().getCodEstadoDevolucion()>0)
                    {
                        consulta+=" and d.COD_ESTADO_DEVOLUCION='"+buscarDevolucion.getEstadosDevoluciones().getCodEstadoDevolucion()+"'";
                    }
                    if(!buscarDevolucion.getSalidasAlmacen().getAreasEmpresa().getCodAreaEmpresa().equals("0"))
                    {
                        consulta+=" and i.COD_ALMACEN='"+buscarDevolucion.getSalidasAlmacen().getAreasEmpresa().getCodAreaEmpresa()+"'";
                    }
                    if(buscarMaterial.getGrupos().getCapitulos().getCodCapitulo()>0)
                    {
                        if(buscarMaterial.getGrupos().getCodGrupo()>0)
                        {
                            if(!buscarMaterial.getCodMaterial().equals("0"))
                            {
                                consulta+=" and d.COD_DEVOLUCION in(select dd.COD_DEVOLUCION from DEVOLUCIONES_DETALLE dd where dd.COD_MATERIAL IN('"+buscarMaterial.getCodMaterial()+"'))";
                            }
                            else
                            {
                                consulta+=" and d.COD_DEVOLUCION in (select dd.COD_DEVOLUCION from DEVOLUCIONES_DETALLE dd where dd.COD_MATERIAL IN(select m.COD_MATERIAL from MATERIALES m where m.COD_GRUPO in ('"+buscarMaterial.getGrupos().getCodGrupo()+"')) )";
                            }

                        }
                        else
                        {
                            consulta+=" and d.COD_DEVOLUCION in (select dd.COD_DEVOLUCION from DEVOLUCIONES_DETALLE dd where dd.COD_MATERIAL IN(select m.COD_MATERIAL from MATERIALES m where m.COD_GRUPO in (select g.COD_GRUPO from GRUPOS g where g.COD_CAPITULO in ('"+buscarMaterial.getGrupos().getCapitulos().getCodCapitulo()+"'))) )";
                        }
                    }

                    //final alejandro buscador
                    consulta = consulta + "  ) AS listado where FILAS BETWEEN "+begin +" AND "+end+"  ";
            System.out.println("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            devolucionesList.clear();
            while(rs.next()){
                Devoluciones devoluciones = new Devoluciones();
                IngresosAlmacen ingresosAlmacen = new IngresosAlmacen();
                ingresosAlmacen.setCodIngresoAlmacen(rs.getInt("COD_INGRESO_ALMACEN"));
                ingresosAlmacen.setNroIngresoAlmacen(rs.getInt("NRO_INGRESO_ALMACEN"));
                ingresosAlmacen.setFechaIngresoAlmacen(rs.getDate("FECHA_INGRESO_ALMACEN"));
                ingresosAlmacen.getDevoluciones().setCodDevolucion(rs.getInt("COD_DEVOLUCION"));
                ingresosAlmacen.getDevoluciones().setNroDevolucion(rs.getInt("NRO_DEVOLUCION"));
                ingresosAlmacen.getDevoluciones().getEstadosDevoluciones().setCodEstadoDevolucion(rs.getInt("COD_ESTADO_DEVOLUCION"));
                ingresosAlmacen.getDevoluciones().getEstadosDevoluciones().setNombreEstadoDevolucion(rs.getString("NOMBRE_ESTADO_DEVOLUCION"));
                ingresosAlmacen.getDevoluciones().setObsDevolucion(rs.getString("OBS_DEVOLUCION"));
                ingresosAlmacen.getAlmacenes().setCodAlmacen(rs.getInt("COD_ALMACEN"));
                ingresosAlmacen.getAlmacenes().setNombreAlmacen(rs.getString("NOMBRE_ALMACEN"));
                ingresosAlmacen.getAlmacenes().getFiliales().setNombreFilial(rs.getString("NOMBRE_FILIAL"));
                ingresosAlmacen.getSalidasAlmacen().getPersonal().setApPaternoPersonal(rs.getString("AP_PATERNO_PERSONAL"));
                ingresosAlmacen.getSalidasAlmacen().getPersonal().setApMaternoPersonal(rs.getString("AP_MATERNO_PERSONAL"));
                ingresosAlmacen.getSalidasAlmacen().getPersonal().setNombrePersonal(rs.getString("NOMBRES_PERSONAL"));
                ingresosAlmacen.getSalidasAlmacen().setCodLoteProduccion(rs.getString("COD_LOTE_PRODUCCION"));
                ingresosAlmacen.getDevoluciones().getSalidasAlmacen().getComponentesProd().setNombreProdSemiterminado(rs.getString("nombre_prod_semiterminado"));
                ingresosAlmacen.getDevoluciones().getSalidasAlmacen().setNroSalidaAlmacen(rs.getInt("NRO_SALIDA_ALMACEN"));
                ingresosAlmacen.getDevoluciones().getSalidasAlmacen().setCodSalidaAlmacen(rs.getInt("COD_SALIDA_ALMACEN"));
                devolucionesList.add(ingresosAlmacen);
            }
            rs.close();
            st.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //inicio ale anular devolucion
    public String anularDevolucionesAlmacen_action_1(){
        setMensaje("");
        IngresosAlmacen anularDevolucion= new IngresosAlmacen();
        String consulta="";
        try {
            Iterator i = devolucionesList.iterator();
             while(i.hasNext()){
                IngresosAlmacen ingresosAlmacen1 = (IngresosAlmacen)i.next();
                if(ingresosAlmacen1.getChecked().booleanValue()==true){
                    anularDevolucion= ingresosAlmacen1;
                    //devoluciones = ingresosAlmacen.getDevoluciones();
                    }
                }
                if(anularDevolucion.getDevoluciones().getEstadosDevoluciones().getCodEstadoDevolucion()==2)
                {
                    setMensaje("Esta devolución ya está anulada");
                    return null;
                }
                if(this.verificarIngresoConSalidas(anularDevolucion))
                {
                mensaje="Usted no puede  Anular el Ingreso correspondiente a la devolucion porque se registraron Salidas del mismo";
                return null;
                }
                SimpleDateFormat sdt= new SimpleDateFormat("MM");
                consulta="select * from estados_transacciones_almacen where cod_gestion=(select top 1 ia.COD_GESTION from INGRESOS_ALMACEN ia where ia.COD_INGRESO_ALMACEN='"+anularDevolucion.getCodIngresoAlmacen()+"')"+
                        " and cod_mes='"+sdt.format(anularDevolucion.getFechaIngresoAlmacen())+"' and estado=1";
                System.out.println("consulta verificar transaccion cerrada "+consulta);
                Connection con1=null;
                con1=Util.openConnection(con1);
                Statement st=con1.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY) ;
                ResultSet res=st.executeQuery(consulta);
                if(res.next())
                {
                    setMensaje("Las transacciones para la fecha de devolución han sido cerradas");
                    res.close();
                    st.close();
                    con1.close();
                    return null;
                }
                consulta="select dde.CANTIDAD_DEVUELTA"+
                         " from devoluciones_detalle_etiquetas dde left outer join INGRESOS_ALMACEN_DETALLE_ESTADO iade"+
                         " on dde.COD_INGRESO_ALMACEN=iade.COD_INGRESO_ALMACEN and dde.COD_MATERIAL=iade.COD_MATERIAL"+
                         " and dde.ETIQUETA=iade.ETIQUETA where dde.COD_DEVOLUCION='"+anularDevolucion.getDevoluciones().getCodDevolucion()+"' " +
                         " and dde.CANTIDAD_DEVUELTA>0 and ISNULL(iade.CANTIDAD_RESTANTE,0)<dde.CANTIDAD_DEVUELTA";
                System.out.println("consulta verificar existencia de cantidad en los ingresos "+consulta);
                res=st.executeQuery(consulta);
                if(res.next())
                {
                    setMensaje("Esta devolución no puede ser anulada porque se realizaron salidas con las cantidades devueltas");
                    res.close();
                    st.close();
                    con1.close();
                    return null;
                }
                consulta="update devoluciones set cod_estado_devolucion=2 where cod_devolucion='"+anularDevolucion.getDevoluciones().getCodDevolucion()+"'";
                System.out.println("consulta anular devolucion "+consulta);
                PreparedStatement pst=con1.prepareStatement(consulta);
                if(pst.executeUpdate()>0)System.out.println("Se anulo la devolución del almacen");

                consulta="update INGRESOS_ALMACEN set cod_estado_ingreso_almacen = 2 where cod_devolucion ='"+anularDevolucion.getDevoluciones().getCodDevolucion()+"'";
                System.out.println("consulta anular devolucion "+consulta);
                pst=con1.prepareStatement(consulta);
                if(pst.executeUpdate()>0)System.out.println("Se anularon ingresos almacen");
                
                consulta="select ppd.COD_PROGRAMA_PRODUCCION_DEVOLUCION_MATERIAL"+
                         " from PROGRAMA_PRODUCCION_DEVOLUCION ppd where ppd.COD_DEVOLUCION='"+anularDevolucion.getDevoluciones().getCodDevolucion()+"'";
                System.out.println("consulta buscar programa produccion devolucion "+consulta);
                res=st.executeQuery(consulta);
                ProgramaProduccionDevolucionMaterial devolverProgProdDevMat= new ProgramaProduccionDevolucionMaterial();
                if(res.next())
                {
                    devolverProgProdDevMat.setCodProgramaProduccionDevolucionMaterial(res.getString("COD_PROGRAMA_PRODUCCION_DEVOLUCION_MATERIAL"));
                }
                consulta="select dde.COD_INGRESO_ALMACEN,dde.ETIQUETA,dde.COD_MATERIAL,dde.CANTIDAD_DEVUELTA from DEVOLUCIONES_DETALLE_ETIQUETAS dde where dde.COD_DEVOLUCION='"+anularDevolucion.getDevoluciones().getCodDevolucion()+"'";
                System.out.println("consulta detalla  a devolver "+consulta);
                res=st.executeQuery(consulta);
                while(res.next())
                {
                    consulta="update ingresos_almacen_detalle_estado set cantidad_restante=cantidad_restante-'"+res.getDouble("CANTIDAD_DEVUELTA")+"'" +
                            " where cod_ingreso_almacen='"+res.getString("COD_INGRESO_ALMACEN")+"' and cod_material='"+res.getInt("COD_MATERIAL")+"' and etiqueta='"+res.getString("ETIQUETA")+"'";
                    System.out.println("consulta update ingresos "+consulta);
                    pst=con1.prepareStatement(consulta);
                    if(pst.executeUpdate()>0)System.out.println("se actualizo la cantidad del ingtreso");
                    if(!devolverProgProdDevMat.getCodProgramaProduccionDevolucionMaterial().equals(""))
                    {
                        consulta="UPDATE PROGRAMA_PRODUCCION_DEVOLUCION_MATERIAL_DETALLE SET CANTIDAD_BUENOS_ENTREGADOS = CANTIDAD_BUENOS_ENTREGADOS - '"+res.getDouble("CANTIDAD_DEVUELTA")+"'"+
                                // ", CANTIDAD_MALOS_ENTREGADOS = CANTIDAD_MALOS_ENTREGADOS - '"++"' " +
                                 " WHERE COD_PROGRAMA_PRODUCCION_DEVOLUCION_MATERIAL = '"+devolverProgProdDevMat.getCodProgramaProduccionDevolucionMaterial()+"' and COD_MATERIAL = '"+res.getInt("COD_MATERIAL")+"'";
                        System.out.println("consulta update "+consulta);
                        pst=con1.prepareStatement(consulta);
                        if(pst.executeUpdate()>0)System.out.println("se devolvio la cantidad a programa produccion devolucion material ");
                    }
                }
                if(!devolverProgProdDevMat.getCodProgramaProduccionDevolucionMaterial().equals(""))
                {
                        consulta="select top 1 * from PROGRAMA_PRODUCCION_DEVOLUCION_MATERIAL_DETALLE ppdmd"+
                        " where ppdmd.COD_PROGRAMA_PRODUCCION_DEVOLUCION_MATERIAL='"+devolverProgProdDevMat.getCodProgramaProduccionDevolucionMaterial()+"' and "+
                        " (ppdmd.CANTIDAD_BUENOS-ppdmd.CANTIDAD_BUENOS_ENTREGADOS)>0 and "+
                        " (ppdmd.CANTIDAD_MALOS-ppdmd.CANTIDAD_MALOS_ENTREGADOS)>0";

                        res=st.executeQuery(consulta);
                        if(res.next())
                        {
                            consulta="UPDATE PROGRAMA_PRODUCCION_DEVOLUCION_MATERIAL SET COD_ESTADO_PROGRAMA_PRODUCCION_DEVOLUCION = 2"+
                                    " WHERE COD_PROGRAMA_PRODUCCION_DEVOLUCION_MATERIAL = '"+devolverProgProdDevMat.getCodProgramaProduccionDevolucionMaterial()+"'";
                        }
                        else
                        {
                            consulta="UPDATE PROGRAMA_PRODUCCION_DEVOLUCION_MATERIAL SET COD_ESTADO_PROGRAMA_PRODUCCION_DEVOLUCION = 3"+
                                     " WHERE COD_PROGRAMA_PRODUCCION_DEVOLUCION_MATERIAL = '"+devolverProgProdDevMat.getCodProgramaProduccionDevolucionMaterial()+"'";
                        }
                        pst=con1.prepareStatement(consulta);
                        if(pst.executeUpdate()>0)System.out.println("se actualizo el estado del programa produccion devolucion material "+devolverProgProdDevMat.getCodProgramaProduccionDevolucionMaterial());

                }
                pst.close();
                res.close();
                st.close();
                con1.close();
                setMensaje("Se anulo la devolución de almacen");
                this.listadoDevolucionesAlmacen();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
        public String anularDevolucionesAlmacen_action(){
        setMensaje("");
        IngresosAlmacen anularDevolucion= new IngresosAlmacen();
        String consulta="";
        try {
            Iterator i = devolucionesList.iterator();
             while(i.hasNext()){
                IngresosAlmacen ingresosAlmacen1 = (IngresosAlmacen)i.next();
                if(ingresosAlmacen1.getChecked().booleanValue()==true){
                    anularDevolucion= ingresosAlmacen1;
                    //devoluciones = ingresosAlmacen.getDevoluciones();

                }
                }
                if(anularDevolucion.getDevoluciones().getEstadosDevoluciones().getCodEstadoDevolucion()==2)
                {
                    setMensaje("Esta devolución ya está anulada");
                    return null;
                }
                SimpleDateFormat sdt= new SimpleDateFormat("MM");
                consulta="select * from estados_transacciones_almacen where cod_gestion=(select top 1 ia.COD_GESTION from INGRESOS_ALMACEN ia where ia.COD_INGRESO_ALMACEN='"+anularDevolucion.getCodIngresoAlmacen()+"')"+
                        "and cod_mes='"+sdt.format(anularDevolucion.getFechaIngresoAlmacen())+"' and estado=1";
                System.out.println("consulta verificar transaccion cerrada "+consulta);
                Connection con1=null;
                con1=Util.openConnection(con1);
                Statement st=con1.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY) ;
                ResultSet res=st.executeQuery(consulta);
                if(res.next())
                {
                    setMensaje("Las transacciones para la fecha de devolución han sido cerradas");
                    res.close();
                    st.close();
                    con1.close();
                    return null;
                }
                consulta="select dde.CANTIDAD_DEVUELTA"+
                         " from devoluciones_detalle_etiquetas dde left outer join INGRESOS_ALMACEN_DETALLE_ESTADO iade"+
                         " on dde.COD_INGRESO_ALMACEN=iade.COD_INGRESO_ALMACEN and dde.COD_MATERIAL=iade.COD_MATERIAL"+
                         " and dde.ETIQUETA=iade.ETIQUETA where dde.COD_DEVOLUCION='"+anularDevolucion.getDevoluciones().getCodDevolucion()+"' " +
                         " and dde.CANTIDAD_DEVUELTA>0 and ISNULL(iade.CANTIDAD_RESTANTE,0)<dde.CANTIDAD_DEVUELTA";
                System.out.println("consulta verificar existencia de cantidad en los ingresos "+consulta);
                res=st.executeQuery(consulta);
                if(res.next())
                {
                    setMensaje("Esta devolución no puede ser anulada porque se realizaron salidas con las cantidades devueltas");
                    res.close();
                    st.close();
                    con1.close();
                    return null;
                }
                consulta="update devoluciones set cod_estado_devolucion=2 where cod_devolucion='"+anularDevolucion.getDevoluciones().getCodDevolucion()+"'";
                System.out.println("consulta anular devolucion "+consulta);
                PreparedStatement pst=con1.prepareStatement(consulta);
                if(pst.executeUpdate()>0)System.out.println("Se anulo la devolución del almacen");
                consulta="update INGRESOS_ALMACEN set cod_estado_ingreso_almacen = 2 where cod_devolucion = '"+anularDevolucion.getDevoluciones().getCodDevolucion()+"'";
                System.out.println("consulta anular ingreso almacen "+consulta);
                PreparedStatement pst1=con1.prepareStatement(consulta);
                if(pst1.executeUpdate()>0)System.out.println("Se anulo el ingreso del almacen");
                consulta="select ppd.COD_PROGRAMA_PRODUCCION_DEVOLUCION_MATERIAL"+
                         " from PROGRAMA_PRODUCCION_DEVOLUCION ppd where ppd.COD_DEVOLUCION='"+anularDevolucion.getDevoluciones().getCodDevolucion()+"'";
                System.out.println("consulta buscar programa produccion devolucion "+consulta);
                res=st.executeQuery(consulta);
                ProgramaProduccionDevolucionMaterial devolverProgProdDevMat= new ProgramaProduccionDevolucionMaterial();
                if(res.next())
                {
                    devolverProgProdDevMat.setCodProgramaProduccionDevolucionMaterial(res.getString("COD_PROGRAMA_PRODUCCION_DEVOLUCION_MATERIAL"));
                }
                consulta="select dde.COD_INGRESO_ALMACEN,dde.ETIQUETA,dde.COD_MATERIAL,dde.CANTIDAD_DEVUELTA from DEVOLUCIONES_DETALLE_ETIQUETAS dde where dde.COD_DEVOLUCION='"+anularDevolucion.getDevoluciones().getCodDevolucion()+"'";
                System.out.println("consulta detalla  a devolver "+consulta);
                res=st.executeQuery(consulta);
                while(res.next())
                {
                    consulta="update ingresos_almacen_detalle_estado set cantidad_restante=cantidad_restante-'"+res.getDouble("CANTIDAD_DEVUELTA")+"'" +
                            " where cod_ingreso_almacen='"+res.getString("COD_INGRESO_ALMACEN")+"' and cod_material='"+res.getInt("COD_MATERIAL")+"' and etiqueta='"+res.getString("ETIQUETA")+"'";
                    System.out.println("consulta update ingresos "+consulta);
                    pst=con1.prepareStatement(consulta);
                    if(pst.executeUpdate()>0)System.out.println("se actualizo la cantidad del ingtreso");
                    if(!devolverProgProdDevMat.getCodProgramaProduccionDevolucionMaterial().equals(""))
                    {
                        consulta="UPDATE PROGRAMA_PRODUCCION_DEVOLUCION_MATERIAL_DETALLE SET CANTIDAD_BUENOS_ENTREGADOS = CANTIDAD_BUENOS_ENTREGADOS - '"+res.getDouble("CANTIDAD_DEVUELTA")+"'"+
                                // ", CANTIDAD_MALOS_ENTREGADOS = CANTIDAD_MALOS_ENTREGADOS - '"++"' " +
                                 " WHERE COD_PROGRAMA_PRODUCCION_DEVOLUCION_MATERIAL = '"+devolverProgProdDevMat.getCodProgramaProduccionDevolucionMaterial()+"' and COD_MATERIAL = '"+res.getInt("COD_MATERIAL")+"'";
                        System.out.println("consulta update "+consulta);
                        pst=con1.prepareStatement(consulta);
                        if(pst.executeUpdate()>0)System.out.println("se devolvio la cantidad a programa produccion devolucion material ");

                    }
                }
                if(!devolverProgProdDevMat.getCodProgramaProduccionDevolucionMaterial().equals(""))
                {
                        consulta="select top 1 * from PROGRAMA_PRODUCCION_DEVOLUCION_MATERIAL_DETALLE ppdmd"+
                        " where ppdmd.COD_PROGRAMA_PRODUCCION_DEVOLUCION_MATERIAL='"+devolverProgProdDevMat.getCodProgramaProduccionDevolucionMaterial()+"' and "+
                        " (ppdmd.CANTIDAD_BUENOS-ppdmd.CANTIDAD_BUENOS_ENTREGADOS)>0 and "+
                        " (ppdmd.CANTIDAD_MALOS-ppdmd.CANTIDAD_MALOS_ENTREGADOS)>0";

                        res=st.executeQuery(consulta);
                        if(res.next())
                        {
                            consulta="UPDATE PROGRAMA_PRODUCCION_DEVOLUCION_MATERIAL SET COD_ESTADO_PROGRAMA_PRODUCCION_DEVOLUCION = 2"+
                                    " WHERE COD_PROGRAMA_PRODUCCION_DEVOLUCION_MATERIAL = '"+devolverProgProdDevMat.getCodProgramaProduccionDevolucionMaterial()+"'";
                        }
                        else
                        {
                            consulta="UPDATE PROGRAMA_PRODUCCION_DEVOLUCION_MATERIAL SET COD_ESTADO_PROGRAMA_PRODUCCION_DEVOLUCION = 3"+
                                     " WHERE COD_PROGRAMA_PRODUCCION_DEVOLUCION_MATERIAL = '"+devolverProgProdDevMat.getCodProgramaProduccionDevolucionMaterial()+"'";
                        }
                        pst=con1.prepareStatement(consulta);
                        if(pst.executeUpdate()>0)System.out.println("se actualizo el estado del programa produccion devolucion material "+devolverProgProdDevMat.getCodProgramaProduccionDevolucionMaterial());

                }
                pst.close();
                res.close();
                st.close();
                con1.close();
                setMensaje("Se anulo la devolución de almacen");
                this.listadoDevolucionesAlmacen();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
            System.out.println("consulta "+consulta);
            ResultSet rest=st.executeQuery(consulta);
            resultado=rest.next();
            rest.close();
            st.close();
            con.close();
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        return resultado;
    }
    //final ale anular devolucion
    public String getCargarAgregarDevolucionAlmacen(){
        try {
            //se carga los datos de devolucion             
            devoluciones.setSalidasAlmacen(salidasAlmacen);
            devolucionesDetalleList = this.cargarDetalleDevolucion(salidasAlmacen);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public List cargarDetalleDevolucion(SalidasAlmacen salidasAlmacen){
        List devolucionesDetalleList = new ArrayList();
        try {
            Connection con = null;
            con = Util.openConnection(con);
            //llenar el detalle de salida almacen
            String consulta = " SELECT s.COD_SALIDA_ALMACEN,s.COD_MATERIAL, s.CANTIDAD_SALIDA_ALMACEN, s.COD_UNIDAD_MEDIDA, " +
                    "  s.COD_ESTADO_MATERIAL,m.NOMBRE_MATERIAL,u.NOMBRE_UNIDAD_MEDIDA,u.ABREVIATURA,e.NOMBRE_ESTADO_MATERIAL " +
                    "  FROM SALIDAS_ALMACEN_DETALLE s  " +
                    "  inner join MATERIALES m on m.COD_MATERIAL = s.COD_MATERIAL " +
                    "  inner join UNIDADES_MEDIDA u on u.COD_UNIDAD_MEDIDA = s.COD_UNIDAD_MEDIDA " +
                    "  inner join ESTADOS_MATERIAL e on e.COD_ESTADO_MATERIAL = s.COD_ESTADO_MATERIAL " +
                    "  where s.COD_SALIDA_ALMACEN = '"+salidasAlmacen.getCodSalidaAlmacen()+"' ";
            System.out.println("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            devolucionesDetalleList.clear();
            while(rs.next()){
                DevolucionesDetalle devolucionesDetalle = new DevolucionesDetalle();
                devolucionesDetalle.getDevoluciones().getSalidasAlmacen().setCodSalidaAlmacen(rs.getInt("COD_SALIDA_ALMACEN"));
                devolucionesDetalle.getMateriales().setCodMaterial(rs.getString("COD_MATERIAL"));
                devolucionesDetalle.getMateriales().setNombreMaterial(rs.getString("NOMBRE_MATERIAL"));
                devolucionesDetalle.setCantidadEntregada(rs.getFloat("CANTIDAD_SALIDA_ALMACEN"));
                devolucionesDetalle.setCantidadDevuelta(0);
                devolucionesDetalle.setCantidadDevueltaFallados(0);
                devolucionesDetalle.getUnidadesMedida().setCodUnidadMedida(rs.getInt("COD_UNIDAD_MEDIDA"));
                devolucionesDetalle.getUnidadesMedida().setAbreviatura(rs.getString("ABREVIATURA"));
                devolucionesDetalle.setDevolucionesDetalleEtiquetasList(this.listaDevolucionDetalleEtiquetas(devolucionesDetalle));
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
    public List listaDevolucionDetalleEtiquetas(DevolucionesDetalle devolucionesDetalle){
        List devolucionesDetalleEtiquetasList = new ArrayList();
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select i.COD_INGRESO_ALMACEN,i.NRO_INGRESO_ALMACEN,s.ETIQUETA,s.CANTIDAD,s.COD_MATERIAL, s.FECHA_VENCIMIENTO," +
                    " (select sum(ISNULL(dde.CANTIDAD_DEVUELTA,0)+ISNULL(dde.CANTIDAD_FALLADOS,0)+ISNULL(dde.cantidad_fallados_proveedor,0)) from DEVOLUCIONES d " +
                    " inner join DEVOLUCIONES_DETALLE dd on dd.COD_DEVOLUCION = d.COD_DEVOLUCION " +
                    " inner join DEVOLUCIONES_DETALLE_ETIQUETAS dde on dde.COD_DEVOLUCION = dd.COD_DEVOLUCION " +
                    " and dde.COD_MATERIAL = dd.COD_MATERIAL " +
                    " where d.COD_ESTADO_DEVOLUCION = 1 " +
                    " and d.COD_SALIDA_ALMACEN =s.COD_SALIDA_ALMACEN " +
                    " and dd.COD_MATERIAL = s.COD_MATERIAL " +
                    " and dde.COD_INGRESO_ALMACEN = i.COD_INGRESO_ALMACEN " +
                    " and dde.ETIQUETA = s.ETIQUETA ) CANTIDADES_DEVUELTAS " +
                    "  from SALIDAS_ALMACEN_DETALLE_INGRESO s " +
                    " inner join INGRESOS_ALMACEN i on i.COD_INGRESO_ALMACEN = s.COD_INGRESO_ALMACEN " +
                    " where s.COD_SALIDA_ALMACEN = '"+salidasAlmacen.getCodSalidaAlmacen()+"' and s.cantidad>0" +
                    " and s.COD_MATERIAL = '"+devolucionesDetalle.getMateriales().getCodMaterial()+"'";
            System.out.println("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            devolucionesDetalleEtiquetasList.clear();
            while(rs.next()){
                DevolucionesDetalleEtiquetas devolucionesDetalleEtiquetas = new DevolucionesDetalleEtiquetas();
                devolucionesDetalleEtiquetas.getIngresosAlmacen().setNroIngresoAlmacen(rs.getInt("NRO_INGRESO_ALMACEN"));
                devolucionesDetalleEtiquetas.getIngresosAlmacen().setCodIngresoAlmacen(rs.getInt("COD_INGRESO_ALMACEN"));
                devolucionesDetalleEtiquetas.getMateriales().setCodMaterial(rs.getString("COD_MATERIAL"));                
                devolucionesDetalleEtiquetas.setEtiqueta(rs.getInt("ETIQUETA"));
                devolucionesDetalleEtiquetas.setCantidadSalida(rs.getFloat("CANTIDAD"));
                devolucionesDetalleEtiquetas.setFechaVencimiento(rs.getDate("FECHA_VENCIMIENTO"));
                devolucionesDetalleEtiquetas.setCantidadesDevueltas(rs.getFloat("CANTIDADES_DEVUELTAS"));
                devolucionesDetalleEtiquetas.setCantidadDevuelta(0);
                devolucionesDetalleEtiquetas.setCantidadFallados(0);
                devolucionesDetalleEtiquetas.setCantidadFalladosProveedor(0);
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
    public List listaComponentesProd(){
        List componentesProdList = new ArrayList();
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select cp.COD_COMPPROD , cp.nombre_prod_semiterminado from COMPONENTES_PROD cp ";
            System.out.println("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            componentesProdList.clear();
            componentesProdList.add(new SelectItem("0", "-NINGUNO-"));
            while(rs.next()){
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
    public String buscarSalidaAlmacen_action(){
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
        inicioFila=inicioFila-numrows;
        finalFila=finalFila-numrows;        
        this.listadoBuscarSalidaAlmacen();
        cantidadFilasBuscar = salidasAlmacenBuscarList.size();
        return "";
    }

    public String siguiente_action() {
        inicioFila=inicioFila+numrows;
        finalFila=finalFila+numrows;
        this.listadoBuscarSalidaAlmacen();
        cantidadFilasBuscar = salidasAlmacenBuscarList.size();
        return "";
    }

    public void listadoBuscarSalidaAlmacen(){
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select * from (select ROW_NUMBER() OVER (order by s.NRO_SALIDA_ALMACEN desc) as 'FILAS'," +
                    " s.COD_SALIDA_ALMACEN,s.COD_LOTE_PRODUCCION,s.NRO_SALIDA_ALMACEN,s.FECHA_SALIDA_ALMACEN, t.NOMBRE_TIPO_SALIDA_ALMACEN,a.NOMBRE_AREA_EMPRESA, " +
                    " p.AP_PATERNO_PERSONAL,p.AP_MATERNO_PERSONAL,p.NOMBRES_PERSONAL,s.OBS_SALIDA_ALMACEN, " +
                    " (select cp.nombre_prod_semiterminado from COMPONENTES_PROD cp where cp.COD_COMPPROD = s.COD_PROD) nombre_prod_semiterminado," +
                    " (select prp.NOMBRE_PRODUCTO_PRESENTACION from PRESENTACIONES_PRODUCTO prp where prp.cod_presentacion = s.COD_PRESENTACION ) NOMBRE_PRODUCTO_PRESENTACION," +
                    " s.orden_trabajo,s.COD_PROD" +
                    " from SALIDAS_ALMACEN s  " +
                    " inner join TIPOS_SALIDAS_ALMACEN t on t.COD_TIPO_SALIDA_ALMACEN = s.COD_TIPO_SALIDA_ALMACEN " +
                    " inner join AREAS_EMPRESA a on a.COD_AREA_EMPRESA = s.COD_AREA_EMPRESA " +
                    " inner join PERSONAL p on p.COD_PERSONAL = s.COD_PERSONAL " +
                    //inicio ale doble
                    " where s.COD_ESTADO_SALIDA_ALMACEN <>2 " +

                    "  ";
                    if(salidasAlmacenBuscar.getNroSalidaAlmacen()>0){
                    consulta = consulta + " and s.NRO_SALIDA_ALMACEN = '"+salidasAlmacenBuscar.getNroSalidaAlmacen()+"' " ;
                    //final ale doble
                    }
                    if(!salidasAlmacenBuscar.getCodLoteProduccion().equals("")){
                    consulta = consulta + " and s.COD_LOTE_PRODUCCION = '"+salidasAlmacenBuscar.getCodLoteProduccion()+"' " ;
                    }
                    if(!salidasAlmacenBuscar.getComponentesProd().getCodCompprod().equals("0")){
                    consulta = consulta + " and s.COD_PROD = '"+salidasAlmacenBuscar.getComponentesProd().getCodCompprod()+"' " ;
                    }
                    consulta = consulta + " and s.COD_ALMACEN = '"+usuario.getAlmacenesGlobal().getCodAlmacen()+"' " +
                            " ) AS listado where FILAS BETWEEN "+inicioFila +" AND "+finalFila+"  ";
            System.out.println("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            salidasAlmacenBuscarList.clear();
            while(rs.next()){
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
                salidasAlmacenItem.getComponentesProd().setCodCompprod(rs.getString("COD_PROD"));
                salidasAlmacenBuscarList.add(salidasAlmacenItem);
            }
            rs.close();
            st.close();
            con.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String generarDevolucionSalidaAlmacen_action(){
        try {
            Iterator i = salidasAlmacenBuscarList.iterator();
            while(i.hasNext()){
                SalidasAlmacen salidasAlmacenItem = (SalidasAlmacen)i.next();
                if(salidasAlmacenItem.getChecked().booleanValue()==true){
                    salidasAlmacen = salidasAlmacenItem ;
                    break;
                }
            }
            



        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public String verDevolucionesDetalleEtiquetas_action(){
        try {
            devolucionesDetalle = (DevolucionesDetalle)devolucionesDetalleDataTable.getRowData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String registrarDevolucionesDetalleEtiquetas_action(){
        try {
            Iterator i = devolucionesDetalle.getDevolucionesDetalleEtiquetasList().iterator();
            
            
            float cantidadDevuelta = 0;
            float cantidadDevueltaFallados = 0;
            float cantidadDevueltaFalladosProveedor = 0;
            Double costoSolicitudDevolucion=0d;
            while(i.hasNext()){
                DevolucionesDetalleEtiquetas devolucionesDetalleEtiquetas = (DevolucionesDetalleEtiquetas)i.next();                
                costoSolicitudDevolucion += (devolucionesDetalleEtiquetas.getCantidadFallados());
                cantidadDevuelta = cantidadDevuelta + devolucionesDetalleEtiquetas.getCantidadDevuelta();
                cantidadDevueltaFallados = cantidadDevueltaFallados + devolucionesDetalleEtiquetas.getCantidadFallados();
                cantidadDevueltaFalladosProveedor = cantidadDevueltaFalladosProveedor + devolucionesDetalleEtiquetas.getCantidadFalladosProveedor();
            }
            devolucionesDetalle.setCostoSolicitudDevolucion(costoSolicitudDevolucion);
            devolucionesDetalle.setCantidadDevuelta(cantidadDevuelta);
            devolucionesDetalle.setCantidadDevueltaFallados(cantidadDevueltaFallados);
            devolucionesDetalle.setCantidadDevueltaFalladosProveedor(cantidadDevueltaFalladosProveedor);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public String guardarDevolucion_action(){
        try {
            //inicio ale doble
            setMensaje("");
            //final ale doble
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ManagedIngresoAlmacen managedIngresoAlmacen = new ManagedIngresoAlmacen();



             if(this.verificaTransaccionCerradaAlmacen()==true){
                 mensaje = "Las transacciones para este mes fueron cerradas ";
                 return null;
             }
             if(devolucionesDetalleList.size()==0){
                 mensaje = " No existe detalle de devolucion ";
                 return null;
             }
             if(existeDetalleDevolucionDetalleAlmacen(devolucionesDetalleList)==false){
                 mensaje = " No existe detalle de Cada Item ";
                 return null;
             }
             if(this.cantidadesBuenas()==0 && this.cantidadesFalladas()==0 && this.cantidadesFalladasProveedor()==0){
                 mensaje = " registre cantidades para su devolucion ";
                 return null;
             }

            
            devoluciones.setCodDevolucion(this.generaCodDevolucionAlmacen());
            devoluciones.setEstadoSistema(1);
            devoluciones.getEstadosDevoluciones().setCodEstadoDevolucion(1);

            String consulta = " INSERT INTO DEVOLUCIONES ( COD_DEVOLUCION,  NRO_DEVOLUCION, COD_FORMULARIO_DEV, COD_ALMACEN, " +
                    "  COD_SALIDA_ALMACEN,  COD_GESTION,  COD_ESTADO_DEVOLUCION,  ESTADO_SISTEMA,  OBS_DEVOLUCION,  COD_SALIDA_ALMACEN_AUX) " +
                    " VALUES ( '"+devoluciones.getCodDevolucion()+"', (select ISNULL(max(nro_devolucion),0)+1 from devoluciones  " +
                    " where cod_gestion='"+usuario.getGestionesGlobal().getCodGestion()+"' and cod_almacen='"+usuario.getAlmacenesGlobal().getCodAlmacen()+"'  and estado_sistema=1  ) ,'"+devoluciones.getCodFormularioDev()+"',   " +
                    " '"+usuario.getAlmacenesGlobal().getCodAlmacen()+"' , '"+devoluciones.getSalidasAlmacen().getCodSalidaAlmacen()+"'  ,   '"+ usuario.getGestionesGlobal().getCodGestion()+"', " +
                    "  '"+devoluciones.getEstadosDevoluciones().getCodEstadoDevolucion()+"','"+devoluciones.getEstadoSistema()+"', '"+devoluciones.getObsDevolucion()+"',   '"+devoluciones.getCodSalidaAlmacenAux()+"' ); ";
            System.out.println("consulta " + consulta);
            st.executeUpdate(consulta);

            Iterator i = devolucionesDetalleList.iterator();
            
            while(i.hasNext()){
                DevolucionesDetalle devolucionesDetalleItem = (DevolucionesDetalle)i.next();
                if(devolucionesDetalleItem.getCantidadDevuelta()>0 || devolucionesDetalleItem.getCantidadDevueltaFallados()>0 || devolucionesDetalleItem.getCantidadDevueltaFalladosProveedor()>0){
                consulta = " INSERT INTO DEVOLUCIONES_DETALLE(  COD_DEVOLUCION,  COD_MATERIAL,  CANTIDAD_DEVUELTA,  COD_UNIDAD_MEDIDA) " +
                        " VALUES (   '"+devoluciones.getCodDevolucion()+"', '"+devolucionesDetalleItem.getMateriales().getCodMaterial()+"',  '"+devolucionesDetalleItem.getCantidadDevuelta()+"', '"+devolucionesDetalleItem.getUnidadesMedida().getCodUnidadMedida()+"'); ";
                System.out.println("consulta " + consulta);
                st.executeUpdate(consulta);

                Iterator i1 = devolucionesDetalleItem.getDevolucionesDetalleEtiquetasList().iterator();
                 while(i1.hasNext()){
                    DevolucionesDetalleEtiquetas devolucionesDetalleEtiquetasItem = (DevolucionesDetalleEtiquetas)i1.next();
                    if(devolucionesDetalleEtiquetasItem.getCantidadDevuelta()>0 || devolucionesDetalleEtiquetasItem.getCantidadFalladosProveedor()>0 || devolucionesDetalleEtiquetasItem.getCantidadFallados()>0){
                    consulta = " INSERT INTO DEVOLUCIONES_DETALLE_ETIQUETAS(  COD_DEVOLUCION,  COD_INGRESO_ALMACEN,  COD_MATERIAL,  ETIQUETA," +
                            "  CANTIDAD_DEVUELTA,  CANTIDAD_FALLADOS) VALUES ( '"+devoluciones.getCodDevolucion() +"', '"+devolucionesDetalleEtiquetasItem.getIngresosAlmacen().getCodIngresoAlmacen() +"' ," +
                            "  '"+devolucionesDetalleEtiquetasItem.getMateriales().getCodMaterial()+"',  '"+devolucionesDetalleEtiquetasItem.getEtiqueta()+"'," +
                            "  '"+devolucionesDetalleEtiquetasItem.getCantidadDevuelta()+"',  '"+devolucionesDetalleEtiquetasItem.getCantidadFallados()+"'); ";
                    System.out.println("consulta " + consulta);
                    st.executeUpdate(consulta);
                    consulta = " update ingresos_almacen_detalle_estado set cantidad_restante=cantidad_restante+"+devolucionesDetalleEtiquetasItem.getCantidadDevuelta()+" " +
                            " where cod_ingreso_almacen='"+devolucionesDetalleEtiquetasItem.getIngresosAlmacen().getCodIngresoAlmacen()+"' " +
                            " and cod_material='"+devolucionesDetalleEtiquetasItem.getMateriales().getCodMaterial()+"' " +
                            " and etiqueta='"+devolucionesDetalleEtiquetasItem.getEtiqueta()+"'";
                    System.out.println("consulta " + consulta);
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

            IngresosAlmacen ingresosAlmacen = new IngresosAlmacen();
            


            
             //verificar si el ingreso sera de frv o por devolucion normal
            //debe guardar el la devolucion, el ingreso a almacen y el ingreso a frv si existe el almacen

            if(this.cantidadesBuenas()>0){
                
                ingresosAlmacen.setCodIngresoAlmacen(managedIngresoAlmacen.generaCodIngresoAlmacen());
                ingresosAlmacen.getTiposIngresoAlmacen().setCodTipoIngresoAlmacen(6);//por devolucion
                ingresosAlmacen.getGestiones().setCodGestion(usuario.getGestionesGlobal().getCodGestion());
                ingresosAlmacen.getEstadosIngresoAlmacen().setCodEstadoIngresoAlmacen(1);
                ingresosAlmacen.setNroIngresoAlmacen(managedIngresoAlmacen.obtieneNroIngresoAlmacen(usuario));
                ingresosAlmacen.setEstadoSistema(1);
                ingresosAlmacen.getAlmacenes().setCodAlmacen(usuario.getAlmacenesGlobal().getCodAlmacen());
                ingresosAlmacen.setObsIngresoAlmacen(devoluciones.getObsDevolucion());
                ingresosAlmacen.getPersonal().setCodPersonal(usuario.getUsuarioModuloBean().getCodUsuarioGlobal());
                ingresosAlmacen.setDevoluciones(devoluciones);
                ingresosAlmacen.setFechaIngresoAlmacen(new Date());
                
                 consulta = " INSERT INTO INGRESOS_ALMACEN(  COD_INGRESO_ALMACEN,  COD_TIPO_INGRESO_ALMACEN,  COD_GESTION," +
                        "  COD_ESTADO_INGRESO_ALMACEN,  COD_DEVOLUCION,  FECHA_INGRESO_ALMACEN,  OBS_INGRESO_ALMACEN,  NRO_INGRESO_ALMACEN,  ESTADO_SISTEMA," +
                        "  COD_ALMACEN,  COD_PERSONAL ) " +
                        "  VALUES (  '"+ingresosAlmacen.getCodIngresoAlmacen()+"',  '"+ingresosAlmacen.getTiposIngresoAlmacen().getCodTipoIngresoAlmacen()+"', " +
                        "  '"+ingresosAlmacen.getGestiones().getCodGestion()+"',  '"+ingresosAlmacen.getEstadosIngresoAlmacen().getCodEstadoIngresoAlmacen()+"', " +
                        "  '"+ingresosAlmacen.getDevoluciones().getCodDevolucion()+"', '"+sdf.format(ingresosAlmacen.getFechaIngresoAlmacen())+"'," +
                        "  '"+ingresosAlmacen.getObsIngresoAlmacen()+"',  '"+ingresosAlmacen.getNroIngresoAlmacen()+"', " +
                        "  '"+ingresosAlmacen.getEstadoSistema()+"','"+ingresosAlmacen.getAlmacenes().getCodAlmacen()+"','"+ingresosAlmacen.getPersonal().getCodPersonal()+"' ); ";
                System.out.println("consulta " + consulta);
                st.executeUpdate(consulta);
                i = devolucionesDetalleList.iterator();
                while(i.hasNext()){
                    DevolucionesDetalle devolucionesDetalleItem = (DevolucionesDetalle)i.next();
                    if(devolucionesDetalleItem.getCantidadDevuelta()>0){
                    IngresosAlmacenDetalle ingresosAlmacenDetalleItem = new IngresosAlmacenDetalle();
                    ingresosAlmacenDetalleItem.setMateriales(devolucionesDetalleItem.getMateriales());
                    ingresosAlmacenDetalleItem.setCantTotalIngreso(devolucionesDetalleItem.getCantidadDevuelta());
                    ingresosAlmacenDetalleItem.setCantTotalIngresoFisico(devolucionesDetalleItem.getCantidadDevuelta());
                    ingresosAlmacenDetalleItem.setUnidadesMedida(devolucionesDetalleItem.getUnidadesMedida());

                    consulta = " insert into ingresos_almacen_detalle (cod_ingreso_almacen, cod_material,nro_unidades_empaque,cod_seccion,cant_total_ingreso," +
                            " cant_total_ingreso_fisico,cod_unidad_medida,costo_promedio,costo_unitario,precio_neto,precio_unitario_material)" +
                            " values ('"+ingresosAlmacen.getCodIngresoAlmacen()+"','"+ingresosAlmacenDetalleItem.getMateriales().getCodMaterial()+"'," +
                            " 1,'"+managedIngresoAlmacen.buscaSeccionAlmacen(ingresosAlmacenDetalleItem,usuario).getCodSeccion() +"','"+ingresosAlmacenDetalleItem.getCantTotalIngreso()+"'," +
                            "'"+ingresosAlmacenDetalleItem.getCantTotalIngresoFisico()+"','"+ingresosAlmacenDetalleItem.getUnidadesMedida().getCodUnidadMedida()+"'," +
                            "'"+ingresosAlmacenDetalleItem.getCostoPromedio()+"','"+ingresosAlmacenDetalleItem.getCostoUnitario()+"','"+ingresosAlmacenDetalleItem.getPrecioNeto()+"'," +
                            "'"+ingresosAlmacenDetalleItem.getPrecioUnitarioMaterial()+"' ) ";
                    System.out.println("consulta " + consulta);
                    st.executeUpdate(consulta);
                    }
                }
            }

            if(this.cantidadesFalladas()>0){
                if(this.obtieneAlmacenFrv(usuario)==0){
                    mensaje = "seleccionar_almacenFrv";
                    return null;
                  }else{
                    //ingreso a frv
                    ingresosAlmacen.setCodIngresoAlmacen(managedIngresoAlmacen.generaCodIngresoAlmacen());
                    ingresosAlmacen.getTiposIngresoAlmacen().setCodTipoIngresoAlmacen(7);//por devolucion
                    ingresosAlmacen.getGestiones().setCodGestion(usuario.getGestionesGlobal().getCodGestion());
                    ingresosAlmacen.getEstadosIngresoAlmacen().setCodEstadoIngresoAlmacen(1);
                    ingresosAlmacen.setEstadoSistema(1);
                    ingresosAlmacen.getAlmacenes().setCodAlmacen(this.obtieneAlmacenFrv(usuario));
                    ingresosAlmacen.setNroIngresoAlmacen(managedIngresoAlmacen.obtieneNroIngresoAlmacen(usuario,ingresosAlmacen.getAlmacenes()));
                    ingresosAlmacen.setObsIngresoAlmacen(devoluciones.getObsDevolucion());
                    ingresosAlmacen.getPersonal().setCodPersonal(usuario.getUsuarioModuloBean().getCodUsuarioGlobal());
                    ingresosAlmacen.setDevoluciones(devoluciones);
                    ingresosAlmacen.setFechaIngresoAlmacen(new Date());

                    ManagedAccesoSistema usuarioFrv = new ManagedAccesoSistema();
                    usuarioFrv.getAlmacenesGlobal().setCodAlmacen(this.obtieneAlmacenFrv(usuario));
                    
                    consulta = " INSERT INTO INGRESOS_ALMACEN(  COD_INGRESO_ALMACEN,  COD_TIPO_INGRESO_ALMACEN,  COD_GESTION," +
                            "  COD_ESTADO_INGRESO_ALMACEN,  COD_DEVOLUCION,  FECHA_INGRESO_ALMACEN,  OBS_INGRESO_ALMACEN,  NRO_INGRESO_ALMACEN,  ESTADO_SISTEMA," +
                            "  COD_ALMACEN,  COD_PERSONAL ) " +
                            "  VALUES (  '"+ingresosAlmacen.getCodIngresoAlmacen()+"',  '"+ingresosAlmacen.getTiposIngresoAlmacen().getCodTipoIngresoAlmacen()+"', " +
                            "  '"+ingresosAlmacen.getGestiones().getCodGestion()+"',  '"+ingresosAlmacen.getEstadosIngresoAlmacen().getCodEstadoIngresoAlmacen()+"', " +
                            "  '"+ingresosAlmacen.getDevoluciones().getCodDevolucion()+"', '"+sdf.format(ingresosAlmacen.getFechaIngresoAlmacen())+"'," +
                            "  '"+ingresosAlmacen.getObsIngresoAlmacen()+"',  '"+ingresosAlmacen.getNroIngresoAlmacen()+"', " +
                            "  '"+ingresosAlmacen.getEstadoSistema()+"','"+ingresosAlmacen.getAlmacenes().getCodAlmacen()+"','"+ingresosAlmacen.getPersonal().getCodPersonal()+"' ); ";
                    System.out.println("consulta " + consulta);
                    st.executeUpdate(consulta);
                    i = devolucionesDetalleList.iterator();
                    while(i.hasNext()){
                        DevolucionesDetalle devolucionesDetalleItem = (DevolucionesDetalle)i.next();
                        
                        if(devolucionesDetalleItem.getCantidadDevueltaFallados()>0){
                        IngresosAlmacenDetalle ingresosAlmacenDetalleItem = new IngresosAlmacenDetalle();
                        ingresosAlmacenDetalleItem.setIngresosAlmacen(ingresosAlmacen);
                        ingresosAlmacenDetalleItem.setMateriales(devolucionesDetalleItem.getMateriales());
                        ingresosAlmacenDetalleItem.setCantTotalIngreso(devolucionesDetalleItem.getCantidadDevueltaFallados());
                        ingresosAlmacenDetalleItem.setCantTotalIngresoFisico(devolucionesDetalleItem.getCantidadDevueltaFallados());
                        ingresosAlmacenDetalleItem.setUnidadesMedida(devolucionesDetalleItem.getUnidadesMedida());

                        consulta = " insert into ingresos_almacen_detalle (cod_ingreso_almacen, cod_material,nro_unidades_empaque,cod_seccion,cant_total_ingreso," +
                                " cant_total_ingreso_fisico,cod_unidad_medida,costo_promedio,costo_unitario,precio_neto,precio_unitario_material)" +
                                " values ('"+ingresosAlmacen.getCodIngresoAlmacen()+"','"+ingresosAlmacenDetalleItem.getMateriales().getCodMaterial()+"'," +
                                " 1,'"+managedIngresoAlmacen.buscaSeccionAlmacen(ingresosAlmacenDetalleItem,usuarioFrv).getCodSeccion() +"','"+ingresosAlmacenDetalleItem.getCantTotalIngreso()+"'," +
                                "'"+ingresosAlmacenDetalleItem.getCantTotalIngresoFisico()+"','"+ingresosAlmacenDetalleItem.getUnidadesMedida().getCodUnidadMedida()+"'," +
                                "'"+ingresosAlmacenDetalleItem.getCostoPromedio()+"','"+ingresosAlmacenDetalleItem.getCostoUnitario()+"','"+ingresosAlmacenDetalleItem.getPrecioNeto()+"'," +
                                "'"+ingresosAlmacenDetalleItem.getPrecioUnitarioMaterial()+"' ) ";
                        System.out.println("consulta " + consulta);
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
                        
                        consulta = " insert into ingresos_almacen_detalle_estado(etiqueta,cod_ingreso_almacen,cod_material,cod_estado_material,cod_empaque_secundario_externo,cantidad_parcial,cantidad_restante,fecha_vencimiento) " +
                                " values('"+ingresosAlmacenDetalleEstadoItem.getEtiqueta()+"','"+ingresosAlmacen.getCodIngresoAlmacen()+"'," +
                                "'"+ingresosAlmacenDetalleEstadoItem.getMateriales().getCodMaterial()+"','"+ingresosAlmacenDetalleEstadoItem.getEstadosMaterial().getCodEstadoMaterial() +"'," +
                                " '"+ingresosAlmacenDetalleEstadoItem.getEmpaqueSecundarioExterno().getCodEmpaqueSecundarioExterno()+"','"+ingresosAlmacenDetalleEstadoItem.getCantidadParcial()+"'," +
                                " '"+ingresosAlmacenDetalleEstadoItem.getCantidadRestante()+"','"+sdf.format(ingresosAlmacenDetalleEstadoItem.getFechaVencimiento())+"') ";

                        System.out.println("consulta " + consulta);
                        st.executeUpdate(consulta);
                        }
                        
                    }
                    
                  }
            }
            
            if(this.cantidadesFalladasProveedor()>0){
               
                    //ingreso a rechazados
                    ingresosAlmacen.setCodIngresoAlmacen(managedIngresoAlmacen.generaCodIngresoAlmacen());
                    ingresosAlmacen.getTiposIngresoAlmacen().setCodTipoIngresoAlmacen(7);//por devolucion
                    ingresosAlmacen.getGestiones().setCodGestion(usuario.getGestionesGlobal().getCodGestion());
                    ingresosAlmacen.getEstadosIngresoAlmacen().setCodEstadoIngresoAlmacen(1);
                    ingresosAlmacen.setEstadoSistema(1);
                    ingresosAlmacen.getAlmacenes().setCodAlmacen(12);
                    ingresosAlmacen.setNroIngresoAlmacen(managedIngresoAlmacen.obtieneNroIngresoAlmacen(usuario,ingresosAlmacen.getAlmacenes()));
                    ingresosAlmacen.setObsIngresoAlmacen(devoluciones.getObsDevolucion());
                    ingresosAlmacen.getPersonal().setCodPersonal(usuario.getUsuarioModuloBean().getCodUsuarioGlobal());
                    ingresosAlmacen.setDevoluciones(devoluciones);
                    ingresosAlmacen.setFechaIngresoAlmacen(new Date());

                    ManagedAccesoSistema usuarioFrv = new ManagedAccesoSistema();
                    usuarioFrv.getAlmacenesGlobal().setCodAlmacen(12);//almacen de rechazados
                    
                    consulta = " INSERT INTO INGRESOS_ALMACEN(  COD_INGRESO_ALMACEN,  COD_TIPO_INGRESO_ALMACEN,  COD_GESTION," +
                            "  COD_ESTADO_INGRESO_ALMACEN,  COD_DEVOLUCION,  FECHA_INGRESO_ALMACEN,  OBS_INGRESO_ALMACEN,  NRO_INGRESO_ALMACEN,  ESTADO_SISTEMA," +
                            "  COD_ALMACEN,  COD_PERSONAL ) " +
                            "  VALUES (  '"+ingresosAlmacen.getCodIngresoAlmacen()+"',  '"+ingresosAlmacen.getTiposIngresoAlmacen().getCodTipoIngresoAlmacen()+"', " +
                            "  '"+ingresosAlmacen.getGestiones().getCodGestion()+"',  '"+ingresosAlmacen.getEstadosIngresoAlmacen().getCodEstadoIngresoAlmacen()+"', " +
                            "  '"+ingresosAlmacen.getDevoluciones().getCodDevolucion()+"', '"+sdf.format(ingresosAlmacen.getFechaIngresoAlmacen())+"'," +
                            "  '"+ingresosAlmacen.getObsIngresoAlmacen()+"',  '"+ingresosAlmacen.getNroIngresoAlmacen()+"', " +
                            "  '"+ingresosAlmacen.getEstadoSistema()+"','"+ingresosAlmacen.getAlmacenes().getCodAlmacen()+"','"+ingresosAlmacen.getPersonal().getCodPersonal()+"' ); ";
                    System.out.println("consulta " + consulta);
                    st.executeUpdate(consulta);
                    i = devolucionesDetalleList.iterator();
                    while(i.hasNext()){
                        DevolucionesDetalle devolucionesDetalleItem = (DevolucionesDetalle)i.next();
                        
                        if(devolucionesDetalleItem.getCantidadDevueltaFalladosProveedor()>0){
                        IngresosAlmacenDetalle ingresosAlmacenDetalleItem = new IngresosAlmacenDetalle();
                        ingresosAlmacenDetalleItem.setIngresosAlmacen(ingresosAlmacen);
                        ingresosAlmacenDetalleItem.setMateriales(devolucionesDetalleItem.getMateriales());
                        ingresosAlmacenDetalleItem.setCantTotalIngreso(devolucionesDetalleItem.getCantidadDevueltaFalladosProveedor());
                        ingresosAlmacenDetalleItem.setCantTotalIngresoFisico(devolucionesDetalleItem.getCantidadDevueltaFalladosProveedor());
                        ingresosAlmacenDetalleItem.setUnidadesMedida(devolucionesDetalleItem.getUnidadesMedida());

                        consulta = " insert into ingresos_almacen_detalle (cod_ingreso_almacen, cod_material,nro_unidades_empaque,cod_seccion,cant_total_ingreso," +
                                " cant_total_ingreso_fisico,cod_unidad_medida,costo_promedio,costo_unitario,precio_neto,precio_unitario_material)" +
                                " values ('"+ingresosAlmacen.getCodIngresoAlmacen()+"','"+ingresosAlmacenDetalleItem.getMateriales().getCodMaterial()+"'," +
                                " 1,'"+managedIngresoAlmacen.buscaSeccionAlmacen(ingresosAlmacenDetalleItem,usuarioFrv).getCodSeccion() +"','"+ingresosAlmacenDetalleItem.getCantTotalIngreso()+"'," +
                                "'"+ingresosAlmacenDetalleItem.getCantTotalIngresoFisico()+"','"+ingresosAlmacenDetalleItem.getUnidadesMedida().getCodUnidadMedida()+"'," +
                                "'"+ingresosAlmacenDetalleItem.getCostoPromedio()+"','"+ingresosAlmacenDetalleItem.getCostoUnitario()+"','"+ingresosAlmacenDetalleItem.getPrecioNeto()+"'," +
                                "'"+ingresosAlmacenDetalleItem.getPrecioUnitarioMaterial()+"' ) ";
                        System.out.println("consulta " + consulta);
                        st.executeUpdate(consulta);

                        IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstadoItem = new IngresosAlmacenDetalleEstado();
                        ingresosAlmacenDetalleEstadoItem.setEtiqueta(1);
                        ingresosAlmacenDetalleEstadoItem.setIngresosAlmacenDetalle(ingresosAlmacenDetalleItem);
                        ingresosAlmacenDetalleEstadoItem.setMateriales(ingresosAlmacenDetalleItem.getMateriales());
                        ingresosAlmacenDetalleEstadoItem.getEstadosMaterial().setCodEstadoMaterial(3); //rechazado
                        ingresosAlmacenDetalleEstadoItem.getEmpaqueSecundarioExterno().setCodEmpaqueSecundarioExterno(1);//empaque
                        ingresosAlmacenDetalleEstadoItem.setCantidadParcial(devolucionesDetalleItem.getCantidadDevueltaFalladosProveedor());
                        ingresosAlmacenDetalleEstadoItem.setCantidadRestante(devolucionesDetalleItem.getCantidadDevueltaFalladosProveedor());
                        ingresosAlmacenDetalleEstadoItem.setFechaVencimiento(this.buscaFechaVencimiento(devolucionesDetalleItem));
                        
                        consulta = " insert into ingresos_almacen_detalle_estado(etiqueta,cod_ingreso_almacen,cod_material,cod_estado_material,cod_empaque_secundario_externo,cantidad_parcial,cantidad_restante,fecha_vencimiento) " +
                                " values('"+ingresosAlmacenDetalleEstadoItem.getEtiqueta()+"','"+ingresosAlmacen.getCodIngresoAlmacen()+"'," +
                                "'"+ingresosAlmacenDetalleEstadoItem.getMateriales().getCodMaterial()+"','"+ingresosAlmacenDetalleEstadoItem.getEstadosMaterial().getCodEstadoMaterial() +"'," +
                                " '"+ingresosAlmacenDetalleEstadoItem.getEmpaqueSecundarioExterno().getCodEmpaqueSecundarioExterno()+"','"+ingresosAlmacenDetalleEstadoItem.getCantidadParcial()+"'," +
                                " '"+ingresosAlmacenDetalleEstadoItem.getCantidadRestante()+"','"+sdf.format(ingresosAlmacenDetalleEstadoItem.getFechaVencimiento())+"') ";

                        System.out.println("consulta " + consulta);
                        st.executeUpdate(consulta);
                        }
                        
                    }
                    
                  
            }
           /* if((!devoluciones.getSalidasAlmacen().getCodLoteProduccion().equals(""))&&
               (Integer.valueOf(devoluciones.getSalidasAlmacen().getComponentesProd().getCodCompprod())>0))
            {
                  if(this.verificarDevolucionTodasSalidasEpMp(devoluciones.getSalidasAlmacen().getCodLoteProduccion(),devoluciones.getSalidasAlmacen().getComponentesProd().getCodCompprod()))
                  {
                       consulta="update PROGRAMA_PRODUCCION set COD_ESTADO_PROGRAMA=2  where " +
                                      " COD_LOTE_PRODUCCION ='"+devoluciones.getSalidasAlmacen().getCodLoteProduccion()+"' " +
                                      " and COD_COMPPROD='"+devoluciones.getSalidasAlmacen().getComponentesProd().getCodCompprod()+"'";
                      System.out.println("consulta updatye "+consulta);
                      if(st.executeUpdate(consulta)>0)System.out.println("se actualizo el estado del programa produccion");
                  }
            }*/
            st.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        //actualización de costos
            try {
                Connection con = null;
                con = Util.openConnection(con);
            con.setAutoCommit(false);
            
            String consulta="select iad.cant_total_ingreso_fisico, iad.cod_ingreso_almacen, iad.cod_material,ia.cod_almacen"
                    + "  from ingresos_almacen ia inner join ingresos_almacen_detalle iad"
                    + " on ia.cod_ingreso_almacen=iad.cod_ingreso_almacen"
                    + " where ia.cod_devolucion="+devoluciones.getCodDevolucion();
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                System.out.println("query "+consulta);
            ResultSet rs=st.executeQuery(consulta);
                AssignCostService assignCostService = new AssignCostService(Util.getPropertiesConnection());
                float ufv_actual = assignCostService.getUfvActual();
                
                while (rs.next()) {
                    boolean resultado=assignCostService.updateCostoUnitarioIngreso(rs.getInt("cod_ingreso_almacen"), rs.getInt("cod_almacen"), 6, rs.getInt("cod_material"), rs.getFloat("cant_total_ingreso_fisico"), ufv_actual);
                    if(resultado){
                        System.out.println("update ok -> "+rs.getInt("cod_material"));
                    }
                }
                System.out.println("Costos actualizado en devolucion(D) = "+devoluciones.getCodDevolucion());
            } catch (Exception e) {
                System.out.println("Excepcion en costos");
                e.printStackTrace();
            }
            //fin actualización de costos
        return null;
    }
    public boolean verificarDevolucionTodasSalidasEpMp(String codLote,String codCompProd)
    {
        boolean devolucionTotal=true;   
        try
        {
            Connection con1=null;
            con1=Util.openConnection(con1);
            Statement st=con1.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            String consulta=" select sa.COD_SALIDA_ALMACEN,sum(sad.CANTIDAD_SALIDA_ALMACEN) as cantSa,sad.COD_MATERIAL,"+
                            " (select sum(dde.CANTIDAD_DEVUELTA) from DEVOLUCIONES d inner join DEVOLUCIONES_DETALLE_ETIQUETAS dde"+
                            " on d.COD_DEVOLUCION=dde.COD_DEVOLUCION where dde.COD_MATERIAL=sad.COD_MATERIAL"+
                            " and d.COD_SALIDA_ALMACEN=sa.COD_SALIDA_ALMACEN and d.COD_ESTADO_DEVOLUCION not in (2)) as cantidadDevolucion"+
                            " from SALIDAS_ALMACEN sa inner join SALIDAS_ALMACEN_DETALLE sad "+
                            " on sa.COD_SALIDA_ALMACEN=sad.COD_SALIDA_ALMACEN"+
                            " where sa.COD_PROD = '"+codCompProd+"' and "+
                                  " sa.COD_LOTE_PRODUCCION = '"+codLote+"' and"+
                                  " sa.COD_ESTADO_SALIDA_ALMACEN = 1 and"+
                                  " sa.ESTADO_SISTEMA = 1 and"+
                                  " sa.cod_almacen in (1, 2) "+
                                  /*" and sad.COD_MATERIAL in( "+
                                  " select fd.COD_MATERIAL from FORMULA_MAESTRA_DETALLE_EP fd inner join FORMULA_MAESTRA fm on"+
                                  " fd.COD_FORMULA_MAESTRA=fm.COD_FORMULA_MAESTRA where fm.COD_COMPPROD=sa.COD_PROD"+
                                 "  )"+*/
                                  " and sad.COD_MATERIAL in("+
                                  " select fmd.COD_MATERIAL from FORMULA_MAESTRA_DETALLE_MP fmd inner join FORMULA_MAESTRA f on "+
                                  " f.COD_FORMULA_MAESTRA=fmd.COD_FORMULA_MAESTRA where f.COD_COMPPROD=sa.COD_PROD"+
                                 " )"+
                                  " group by sa.COD_SALIDA_ALMACEN,sad.COD_MATERIAL"+
                            " order by sad.COD_MATERIAL";
            System.out.println("consulta verificar cantidad cambiar lote "+consulta);
            ResultSet res=st.executeQuery(consulta);
            while(res.next())
            {
                System.out.println("cod sal "+res.getString("COD_SALIDA_ALMACEN")+" cod mat "+res.getString("COD_MATERIAL")+" can s "+res.getDouble("cantSa")+" cant d "+res.getDouble("cantidadDevolucion"));
                if(res.getDouble("cantSa")>(res.getDouble("cantidadDevolucion")+0.1))//adicion de tolerancia
                {
                    devolucionTotal=false;
                }
            }
            st.close();
            con1.close();
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        return devolucionTotal;
    }
    public Date buscaFechaVencimiento(DevolucionesDetalle devolucionesDetalleItem){
        Date fechaVencimiento= new Date();
        try {
            Iterator i= devolucionesDetalleItem.getDevolucionesDetalleEtiquetasList().iterator();
            while(i.hasNext()){
                DevolucionesDetalleEtiquetas devolucionesDetalleEtiquetas = (DevolucionesDetalleEtiquetas)i.next();
                fechaVencimiento = devolucionesDetalleEtiquetas.getFechaVencimiento();
                break;
            }           
            

        } catch (Exception e) {
            e.printStackTrace();
        }
        return fechaVencimiento;
    }

    public int obtieneAlmacenFrv(ManagedAccesoSistema usuario){
        int codAlmacenFrv = 0;
        try {
            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String consulta = " select COD_ALMACEN_FRV from ALMACENES_FRV a where a.COD_ALMACEN = '"+usuario.getAlmacenesGlobal().getCodAlmacen()+"' ";
            System.out.println("consulta " + consulta);
            ResultSet rs = st.executeQuery(consulta);
            if(rs.next()){
                codAlmacenFrv = rs.getInt("COD_ALMACEN_FRV");
            }
            st.close();
            rs.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return codAlmacenFrv;
    }

    public float cantidadesFalladas(){
        float cantidadesFalladas = 0;
        try {
            Iterator i = devolucionesDetalleList.iterator();
            while(i.hasNext()){
                DevolucionesDetalle devolucionesDetalleItem = (DevolucionesDetalle)i.next();
                Iterator i1 = devolucionesDetalleItem.getDevolucionesDetalleEtiquetasList().iterator();
                while(i1.hasNext()){
                    DevolucionesDetalleEtiquetas devolucionesDetalleEtiquetasItem = (DevolucionesDetalleEtiquetas)i1.next();
                    cantidadesFalladas = cantidadesFalladas + devolucionesDetalleEtiquetasItem.getCantidadFallados();
                }                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cantidadesFalladas;
    }
    public float cantidadesBuenas(){
        float cantidadesBuenas = 0;
        try {
            Iterator i = devolucionesDetalleList.iterator();
            while(i.hasNext()){
                DevolucionesDetalle devolucionesDetalleItem = (DevolucionesDetalle)i.next();
                Iterator i1 = devolucionesDetalleItem.getDevolucionesDetalleEtiquetasList().iterator();
                while(i1.hasNext()){
                    DevolucionesDetalleEtiquetas devolucionesDetalleEtiquetasItem = (DevolucionesDetalleEtiquetas)i1.next();
                    cantidadesBuenas = cantidadesBuenas + devolucionesDetalleEtiquetasItem.getCantidadDevuelta();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cantidadesBuenas;
    }
    public float cantidadesFalladasProveedor(){
        float cantidadesFalladas = 0;
        try {
            Iterator i = devolucionesDetalleList.iterator();
            while(i.hasNext()){
                DevolucionesDetalle devolucionesDetalleItem = (DevolucionesDetalle)i.next();
                Iterator i1 = devolucionesDetalleItem.getDevolucionesDetalleEtiquetasList().iterator();
                while(i1.hasNext()){
                    DevolucionesDetalleEtiquetas devolucionesDetalleEtiquetasItem = (DevolucionesDetalleEtiquetas)i1.next();
                    cantidadesFalladas = cantidadesFalladas + devolucionesDetalleEtiquetasItem.getCantidadFalladosProveedor();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cantidadesFalladas;
    }



    public int generaCodDevolucionAlmacen(){
         int codSalidaAlmacen = 0;
         try {
             Connection con = null;
             con = Util.openConnection(con);
             String consulta =" select isnull(max(d.COD_DEVOLUCION),0)+1 COD_DEVOLUCION from DEVOLUCIONES d ";
             System.out.println("consulta " + consulta);
             Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             ResultSet rs = st.executeQuery(consulta);
             if(rs.next()){
                 codSalidaAlmacen = rs.getInt("COD_DEVOLUCION");
             }
             con.close();
             st.close();
             rs.close();

         } catch (Exception e) {
             e.printStackTrace();
         }
         return codSalidaAlmacen;
     }

    public boolean verificaTransaccionCerradaAlmacen(){


         boolean transaccionCerradaAlmacen = false;
         Date fechaActual = new Date();
         SimpleDateFormat sdf = new SimpleDateFormat("MM");
         try {
             Connection con = null;
             con = Util.openConnection(con);
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

     public boolean existeDetalleDevolucionDetalleAlmacen(List devolucionesDetalleList){
        boolean existeDetalle = true;
        try {
            Iterator i = devolucionesDetalleList.iterator();
            while(i.hasNext()){
                DevolucionesDetalle devolucionesDetalleItem = (DevolucionesDetalle)i.next();
                if(devolucionesDetalleItem.getDevolucionesDetalleEtiquetasList().size()==0){
                    existeDetalle = false;
                    break;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return existeDetalle;
    }
     public String verReporteSalidaAlmacen_action(){
         SalidasAlmacen salidasAlmacen = (SalidasAlmacen)salidasAlmacenDataTable.getRowData();
         FacesContext facesContext = FacesContext.getCurrentInstance();
         ExternalContext ec = facesContext.getExternalContext();
         Map map = ec.getSessionMap();
         map.put("salidasAlmacen",salidasAlmacen);
         return null;
     }
     public String atras_action_1() {
         super.back();
         this.listadoDevolucionesAlmacen();
         cantidadfilas = devolucionesList.size();


       
        return "";
    }

    public String siguiente_action_1() {
        super.next();
        this.listadoDevolucionesAlmacen();
        cantidadfilas=devolucionesList.size();
        
        return "";
    }
    public String imprimirDevolucion_action(){
        Iterator i = devolucionesList.iterator();
        while(i.hasNext()){
            IngresosAlmacen ingresosAlmacen = (IngresosAlmacen)i.next();
            if(ingresosAlmacen.getChecked().booleanValue()==true){
                FacesContext facesContext = FacesContext.getCurrentInstance();
                ExternalContext context = facesContext.getExternalContext();
                Map map = context.getSessionMap();
                map.put("ingresosAlmacen",ingresosAlmacen);
                map.put("managedAccesoSistema",usuario);
                break;
            }
        }
        return null;
    }
    public String editarDevolucionesAlmacen_action(){
        try {
            Iterator i = devolucionesList.iterator();
        while(i.hasNext()){
            IngresosAlmacen ingresosAlmacen1 = (IngresosAlmacen)i.next();
            if(ingresosAlmacen1.getChecked().booleanValue()==true){
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
    public String getCargarEditarDevolucionesAlmacen(){
       try{
            String consulta = " select s.NRO_SALIDA_ALMACEN,a.NOMBRE_AREA_EMPRESA,t.NOMBRE_TIPO_SALIDA_ALMACEN,s.COD_LOTE_PRODUCCION, " +
                    " p.NOMBRE_PILA,p.AP_PATERNO_PERSONAL,p.AP_MATERNO_PERSONAL,s.FECHA_SALIDA_ALMACEN,s.OBS_SALIDA_ALMACEN,cp.nombre_prod_semiterminado,prp.NOMBRE_PRODUCTO_PRESENTACION,s.COD_FORM_SALIDA,s.cod_salida_almacen " +
                    " from SALIDAS_ALMACEN s inner join AREAS_EMPRESA a on a.COD_AREA_EMPRESA = s.COD_AREA_EMPRESA " +
                    " inner join TIPOS_SALIDAS_ALMACEN t on t.COD_TIPO_SALIDA_ALMACEN = s.COD_TIPO_SALIDA_ALMACEN " +
                    " inner join personal p on p.COD_PERSONAL = s.COD_PERSONAL " +
                    " left outer join COMPONENTES_PROD cp on cp.COD_COMPPROD = s.COD_PROD " +
                    " left outer join PRESENTACIONES_PRODUCTO prp on prp.cod_presentacion = s.COD_PRESENTACION" +
                    " where s.COD_SALIDA_ALMACEN = '"+ingresosAlmacen.getDevoluciones().getSalidasAlmacen().getCodSalidaAlmacen()+"' ";
            System.out.println("consulta " + consulta);
            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(consulta);
            if(rs.next()){
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
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public List cargarEditarDetalleDevolucion(SalidasAlmacen salidasAlmacen){
        List devolucionesDetalleList = new ArrayList();
        try {
            Connection con = null;
            con = Util.openConnection(con);
            //llenar el detalle de salida almacen
            String consulta = " SELECT s.COD_SALIDA_ALMACEN,s.COD_MATERIAL, s.CANTIDAD_SALIDA_ALMACEN, s.COD_UNIDAD_MEDIDA, " +
                    "  s.COD_ESTADO_MATERIAL,m.NOMBRE_MATERIAL,u.NOMBRE_UNIDAD_MEDIDA,u.ABREVIATURA,e.NOMBRE_ESTADO_MATERIAL " +
                    "  FROM SALIDAS_ALMACEN_DETALLE s  " +
                    "  inner join MATERIALES m on m.COD_MATERIAL = s.COD_MATERIAL " +
                    "  inner join UNIDADES_MEDIDA u on u.COD_UNIDAD_MEDIDA = s.COD_UNIDAD_MEDIDA " +
                    "  inner join ESTADOS_MATERIAL e on e.COD_ESTADO_MATERIAL = s.COD_ESTADO_MATERIAL " +
                    "  where s.COD_SALIDA_ALMACEN = '"+salidasAlmacen.getCodSalidaAlmacen()+"' ";
            System.out.println("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            devolucionesDetalleList.clear();
            while(rs.next()){
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
    public List listaEditarDevolucionDetalleEtiquetas(DevolucionesDetalle devolucionesDetalle){
        List devolucionesDetalleEtiquetasList = new ArrayList();
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select i.COD_INGRESO_ALMACEN,i.NRO_INGRESO_ALMACEN,s.ETIQUETA,s.CANTIDAD,s.COD_MATERIAL, s.FECHA_VENCIMIENTO," +
                    " (select dde.CANTIDAD_DEVUELTA from DEVOLUCIONES d " +
                    " inner join DEVOLUCIONES_DETALLE dd on dd.COD_DEVOLUCION = d.COD_DEVOLUCION " +
                    " inner join DEVOLUCIONES_DETALLE_ETIQUETAS dde on dde.COD_DEVOLUCION = dd.COD_DEVOLUCION " +
                    " and dde.COD_MATERIAL = dd.COD_MATERIAL " +
                    " where d.COD_ESTADO_DEVOLUCION = 1 " +
                    " and d.COD_SALIDA_ALMACEN =s.COD_SALIDA_ALMACEN " +
                    " and dd.COD_MATERIAL = s.COD_MATERIAL " +
                    " and dde.COD_INGRESO_ALMACEN = i.COD_INGRESO_ALMACEN " +
                    " and dde.ETIQUETA = s.ETIQUETA and d.cod_devolucion = '"+ingresosAlmacen.getDevoluciones().getCodDevolucion()+"' ) CANTIDAD_DEVUELTA,(select dde.CANTIDAD_FALLADOS from DEVOLUCIONES d " +
                    " inner join DEVOLUCIONES_DETALLE dd on dd.COD_DEVOLUCION = d.COD_DEVOLUCION " +
                    " inner join DEVOLUCIONES_DETALLE_ETIQUETAS dde on dde.COD_DEVOLUCION = dd.COD_DEVOLUCION " +
                    " and dde.COD_MATERIAL = dd.COD_MATERIAL " +
                    " where d.COD_ESTADO_DEVOLUCION = 1 " +
                    " and d.COD_SALIDA_ALMACEN =s.COD_SALIDA_ALMACEN " +
                    " and dd.COD_MATERIAL = s.COD_MATERIAL " +
                    " and dde.COD_INGRESO_ALMACEN = i.COD_INGRESO_ALMACEN " +
                    " and dde.ETIQUETA = s.ETIQUETA and d.cod_devolucion = '"+ingresosAlmacen.getDevoluciones().getCodDevolucion()+"' ) CANTIDAD_FALLADOS " +
                    " from SALIDAS_ALMACEN_DETALLE_INGRESO s " +
                    " inner join INGRESOS_ALMACEN i on i.COD_INGRESO_ALMACEN = s.COD_INGRESO_ALMACEN" +                    
                    " where s.COD_SALIDA_ALMACEN = '"+salidasAlmacen.getCodSalidaAlmacen()+"' " +
                    " and s.COD_MATERIAL = '"+devolucionesDetalle.getMateriales().getCodMaterial()+"'";
            System.out.println("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            devolucionesDetalleEtiquetasList.clear();
            while(rs.next()){
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
                devolucionesDetalle.setCantidadDevuelta(devolucionesDetalle.getCantidadDevuelta()+devolucionesDetalleEtiquetas.getCantidadDevuelta());
                devolucionesDetalle.setCantidadDevueltaFallados(devolucionesDetalle.getCantidadDevueltaFallados()+devolucionesDetalleEtiquetas.getCantidadFallados());
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
    public void cargarDetalleDevolucion(){
    }
    public String guardarEditarDevolucion_action(){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ManagedIngresoAlmacen managedIngresoAlmacen = new ManagedIngresoAlmacen();



             if(this.verificaTransaccionCerradaAlmacen()==true){
                 mensaje = "Las transacciones para este mes fueron cerradas ";
                 return null;
             }
             if(devolucionesDetalleList.size()==0){
                 mensaje = " No existe detalle de devolucion ";
                 return null;
             }
             if(existeDetalleDevolucionDetalleAlmacen(devolucionesDetalleList)==false){
                 mensaje = " No existe detalle de Cada Item ";
                 return null;
             }
             if(this.cantidadesBuenas()==0 && this.cantidadesFalladas()==0){
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
//            System.out.println("consulta " + consulta);
//            st.executeUpdate(consulta);
            String consulta = "";

            consulta = " delete from devoluciones_detalle where cod_devolucion = '"+devoluciones.getCodDevolucion()+"' ";
            System.out.println("consulta " + consulta);
            st.executeUpdate(consulta);
            consulta = " delete from devoluciones_detalle_etiquetas where cod_devolucion = '"+devoluciones.getCodDevolucion()+"' ";
            System.out.println("consulta"  + consulta);
            st.executeUpdate(consulta);
            Iterator i = devolucionesDetalleList.iterator();
            while(i.hasNext()){
                DevolucionesDetalle devolucionesDetalleItem = (DevolucionesDetalle)i.next();
                if(devolucionesDetalleItem.getCantidadDevuelta()>0){
                consulta = " INSERT INTO DEVOLUCIONES_DETALLE(  COD_DEVOLUCION,  COD_MATERIAL,  CANTIDAD_DEVUELTA,  COD_UNIDAD_MEDIDA) " +
                        " VALUES ( '"+devoluciones.getCodDevolucion()+"', '"+devolucionesDetalleItem.getMateriales().getCodMaterial()+"',  '"+devolucionesDetalleItem.getCantidadDevuelta()+"', '"+devolucionesDetalleItem.getUnidadesMedida().getCodUnidadMedida()+"'); ";
                System.out.println("consulta " + consulta);
                st.executeUpdate(consulta);

                Iterator i1 = devolucionesDetalleItem.getDevolucionesDetalleEtiquetasList().iterator();
                 while(i1.hasNext()){
                    DevolucionesDetalleEtiquetas devolucionesDetalleEtiquetasItem = (DevolucionesDetalleEtiquetas)i1.next();
                    if(devolucionesDetalleEtiquetasItem.getCantidadDevuelta()>0){
                    consulta = " INSERT INTO DEVOLUCIONES_DETALLE_ETIQUETAS(  COD_DEVOLUCION,  COD_INGRESO_ALMACEN,  COD_MATERIAL,  ETIQUETA," +
                            "  CANTIDAD_DEVUELTA,  CANTIDAD_FALLADOS) VALUES ( '"+devoluciones.getCodDevolucion() +"', '"+devolucionesDetalleEtiquetasItem.getIngresosAlmacen().getCodIngresoAlmacen() +"' ," +
                            "  '"+devolucionesDetalleEtiquetasItem.getMateriales().getCodMaterial()+"',  '"+devolucionesDetalleEtiquetasItem.getEtiqueta()+"'," +
                            "  '"+devolucionesDetalleEtiquetasItem.getCantidadDevuelta()+"',  '"+devolucionesDetalleEtiquetasItem.getCantidadFallados()+"'); ";
                    System.out.println("consulta " + consulta);
                    st.executeUpdate(consulta);
                    consulta = " update ingresos_almacen_detalle_estado set cantidad_restante=cantidad_restante-"+devolucionesDetalleEtiquetasItem.getCantidadDevueltaB()+"+"+devolucionesDetalleEtiquetasItem.getCantidadDevuelta()+" " +
                            " where cod_ingreso_almacen='"+devolucionesDetalleEtiquetasItem.getIngresosAlmacen().getCodIngresoAlmacen()+"' " +
                            " and cod_material='"+devolucionesDetalleEtiquetasItem.getMateriales().getCodMaterial()+"' " +
                            " and etiqueta='"+devolucionesDetalleEtiquetasItem.getEtiqueta()+"'";
                    System.out.println("consulta " + consulta);
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
            //System.out.println("consulta " + consulta);
            //st.executeUpdate(consulta);
            
            
            IngresosAlmacen ingresosAlmacen = new IngresosAlmacen();
             //verificar si el ingreso sera de frv o por devolucion normal
            //debe guardar el la devolucion, el ingreso a almacen y el ingreso a frv si existe el almacen
            if(this.cantidadesBuenas()>0){
                consulta = " update ingresos_almacen set cod_estado_ingreso_almacen = 1 where cod_ingreso_almacen in (select cod_ingreso_almacen from ingresos_almacen where cod_devolucion = '"+devoluciones.getCodDevolucion()+"') and cod_tipo_ingreso_almacen = 6 ";
                System.out.println("consulta " + consulta);
                st.executeUpdate(consulta);
                ingresosAlmacen = this.recuperaIngresoAlmacen(devoluciones, "6");
                
                if(ingresosAlmacen.getCodIngresoAlmacen()==0){
                    ingresosAlmacen.setCodIngresoAlmacen(managedIngresoAlmacen.generaCodIngresoAlmacen());
                    ingresosAlmacen.getTiposIngresoAlmacen().setCodTipoIngresoAlmacen(6);//por devolucion
                    ingresosAlmacen.getGestiones().setCodGestion(usuario.getGestionesGlobal().getCodGestion());
                    ingresosAlmacen.getEstadosIngresoAlmacen().setCodEstadoIngresoAlmacen(1);
                    ingresosAlmacen.setNroIngresoAlmacen(managedIngresoAlmacen.obtieneNroIngresoAlmacen(usuario));
                    ingresosAlmacen.setEstadoSistema(1);
                    ingresosAlmacen.getAlmacenes().setCodAlmacen(usuario.getAlmacenesGlobal().getCodAlmacen());
                    ingresosAlmacen.setObsIngresoAlmacen(devoluciones.getObsDevolucion());
                    ingresosAlmacen.getPersonal().setCodPersonal(usuario.getUsuarioModuloBean().getCodUsuarioGlobal());
                    ingresosAlmacen.setDevoluciones(devoluciones);
                    ingresosAlmacen.setFechaIngresoAlmacen(new Date());
                    consulta = " INSERT INTO INGRESOS_ALMACEN(  COD_INGRESO_ALMACEN,  COD_TIPO_INGRESO_ALMACEN,  COD_GESTION," +
                        "  COD_ESTADO_INGRESO_ALMACEN,  COD_DEVOLUCION,  FECHA_INGRESO_ALMACEN,  OBS_INGRESO_ALMACEN,  NRO_INGRESO_ALMACEN,  ESTADO_SISTEMA," +
                        "  COD_ALMACEN,  COD_PERSONAL ) " +
                        "  VALUES (  '"+ingresosAlmacen.getCodIngresoAlmacen()+"',  '"+ingresosAlmacen.getTiposIngresoAlmacen().getCodTipoIngresoAlmacen()+"', " +
                        "  '"+ingresosAlmacen.getGestiones().getCodGestion()+"',  '"+ingresosAlmacen.getEstadosIngresoAlmacen().getCodEstadoIngresoAlmacen()+"', " +
                        "  '"+ingresosAlmacen.getDevoluciones().getCodDevolucion()+"', '"+sdf.format(ingresosAlmacen.getFechaIngresoAlmacen())+"'," +
                        "  '"+ingresosAlmacen.getObsIngresoAlmacen()+"',  '"+ingresosAlmacen.getNroIngresoAlmacen()+"', " +
                        "  '"+ingresosAlmacen.getEstadoSistema()+"','"+ingresosAlmacen.getAlmacenes().getCodAlmacen()+"','"+ingresosAlmacen.getPersonal().getCodPersonal()+"' ); ";
                    System.out.println("consulta " + consulta);
                    st.executeUpdate(consulta);
                }
                consulta = "delete from ingresos_almacen_detalle where cod_ingreso_almacen = '"+ingresosAlmacen.getCodIngresoAlmacen()+"' ";
                System.out.println(" consulta " + consulta);
                st.executeUpdate(consulta);
                i = devolucionesDetalleList.iterator();
                while(i.hasNext()){
                    DevolucionesDetalle devolucionesDetalleItem = (DevolucionesDetalle)i.next();
                    if(devolucionesDetalleItem.getCantidadDevuelta()>0){
                    IngresosAlmacenDetalle ingresosAlmacenDetalleItem = new IngresosAlmacenDetalle();
                    ingresosAlmacenDetalleItem.setMateriales(devolucionesDetalleItem.getMateriales());
                    ingresosAlmacenDetalleItem.setCantTotalIngreso(devolucionesDetalleItem.getCantidadDevuelta());
                    ingresosAlmacenDetalleItem.setCantTotalIngresoFisico(devolucionesDetalleItem.getCantidadDevuelta());
                    ingresosAlmacenDetalleItem.setUnidadesMedida(devolucionesDetalleItem.getUnidadesMedida());

                    consulta = " insert into ingresos_almacen_detalle (cod_ingreso_almacen, cod_material,nro_unidades_empaque,cod_seccion,cant_total_ingreso," +
                            " cant_total_ingreso_fisico,cod_unidad_medida,costo_promedio,costo_unitario,precio_neto,precio_unitario_material)" +
                            " values ('"+ingresosAlmacen.getCodIngresoAlmacen()+"','"+ingresosAlmacenDetalleItem.getMateriales().getCodMaterial()+"'," +
                            " 1,'"+managedIngresoAlmacen.buscaSeccionAlmacen(ingresosAlmacenDetalleItem,usuario).getCodSeccion() +"','"+ingresosAlmacenDetalleItem.getCantTotalIngreso()+"'," +
                            "'"+ingresosAlmacenDetalleItem.getCantTotalIngresoFisico()+"','"+ingresosAlmacenDetalleItem.getUnidadesMedida().getCodUnidadMedida()+"'," +
                            "'"+ingresosAlmacenDetalleItem.getCostoPromedio()+"','"+ingresosAlmacenDetalleItem.getCostoUnitario()+"','"+ingresosAlmacenDetalleItem.getPrecioNeto()+"'," +
                            "'"+ingresosAlmacenDetalleItem.getPrecioUnitarioMaterial()+"' ) ";
                    System.out.println("consulta " + consulta);
                    st.executeUpdate(consulta);
                    }
                }
            }else{
                consulta = " update ingresos_almacen set cod_estado_ingreso_almacen = 2 where cod_ingreso_almacen in (select cod_ingreso_almacen from ingresos_almacen where cod_devolucion = '"+devoluciones.getCodDevolucion()+"') and cod_tipo_ingreso_almacen = 6 ";
                System.out.println("consulta " + consulta);
                st.executeUpdate(consulta);
            }

            if(this.cantidadesFalladas()>0){
                if(this.obtieneAlmacenFrv(usuario)==0){
                    mensaje = "seleccionar_almacenFrv";
                    return null;
                  }else{
                    consulta = " update ingresos_almacen set cod_estado_ingreso_almacen = 1 where cod_ingreso_almacen in (select cod_ingreso_almacen from ingresos_almacen where cod_devolucion = '"+devoluciones.getCodDevolucion()+"') and cod_tipo_ingreso_almacen = 7 ";
                    System.out.println("consulta " + consulta);
                    st.executeUpdate(consulta);

                    ingresosAlmacen = this.recuperaIngresoAlmacen(devoluciones, "7");
                    if(ingresosAlmacen.getCodIngresoAlmacen()==0){
                    ingresosAlmacen.setCodIngresoAlmacen(managedIngresoAlmacen.generaCodIngresoAlmacen());
                    ingresosAlmacen.getTiposIngresoAlmacen().setCodTipoIngresoAlmacen(6);//por devolucion
                    ingresosAlmacen.getGestiones().setCodGestion(usuario.getGestionesGlobal().getCodGestion());
                    ingresosAlmacen.getEstadosIngresoAlmacen().setCodEstadoIngresoAlmacen(1);
                    ingresosAlmacen.setNroIngresoAlmacen(managedIngresoAlmacen.obtieneNroIngresoAlmacen(usuario));
                    ingresosAlmacen.setEstadoSistema(1);
                    ingresosAlmacen.getAlmacenes().setCodAlmacen(usuario.getAlmacenesGlobal().getCodAlmacen());
                    ingresosAlmacen.setObsIngresoAlmacen(devoluciones.getObsDevolucion());
                    ingresosAlmacen.getPersonal().setCodPersonal(usuario.getUsuarioModuloBean().getCodUsuarioGlobal());
                    ingresosAlmacen.setDevoluciones(devoluciones);
                    ingresosAlmacen.setFechaIngresoAlmacen(new Date());
                    consulta = " INSERT INTO INGRESOS_ALMACEN(  COD_INGRESO_ALMACEN,  COD_TIPO_INGRESO_ALMACEN,  COD_GESTION," +
                        "  COD_ESTADO_INGRESO_ALMACEN,  COD_DEVOLUCION,  FECHA_INGRESO_ALMACEN,  OBS_INGRESO_ALMACEN,  NRO_INGRESO_ALMACEN,  ESTADO_SISTEMA," +
                        "  COD_ALMACEN,  COD_PERSONAL ) " +
                        "  VALUES (  '"+ingresosAlmacen.getCodIngresoAlmacen()+"',  '"+ingresosAlmacen.getTiposIngresoAlmacen().getCodTipoIngresoAlmacen()+"', " +
                        "  '"+ingresosAlmacen.getGestiones().getCodGestion()+"',  '"+ingresosAlmacen.getEstadosIngresoAlmacen().getCodEstadoIngresoAlmacen()+"', " +
                        "  '"+ingresosAlmacen.getDevoluciones().getCodDevolucion()+"', '"+sdf.format(ingresosAlmacen.getFechaIngresoAlmacen())+"'," +
                        "  '"+ingresosAlmacen.getObsIngresoAlmacen()+"',  '"+ingresosAlmacen.getNroIngresoAlmacen()+"', " +
                        "  '"+ingresosAlmacen.getEstadoSistema()+"','"+ingresosAlmacen.getAlmacenes().getCodAlmacen()+"','"+ingresosAlmacen.getPersonal().getCodPersonal()+"' ); ";
                    System.out.println("consulta " + consulta);
                    st.executeUpdate(consulta);
                }




                    consulta = "delete from ingresos_almacen_detalle where cod_ingreso_almacen = '"+ingresosAlmacen.getCodIngresoAlmacen()+"' ";
                    System.out.println(" consulta " + consulta);
                    st.executeUpdate(consulta);
                    ManagedAccesoSistema usuarioFrv = new ManagedAccesoSistema();
                    usuarioFrv.getAlmacenesGlobal().setCodAlmacen(this.obtieneAlmacenFrv(usuario));
                    i = devolucionesDetalleList.iterator();
                    while(i.hasNext()){
                        DevolucionesDetalle devolucionesDetalleItem = (DevolucionesDetalle)i.next();

                        if(devolucionesDetalleItem.getCantidadDevueltaFallados()>0){
                        IngresosAlmacenDetalle ingresosAlmacenDetalleItem = new IngresosAlmacenDetalle();
                        ingresosAlmacenDetalleItem.setIngresosAlmacen(ingresosAlmacen);
                        ingresosAlmacenDetalleItem.setMateriales(devolucionesDetalleItem.getMateriales());
                        ingresosAlmacenDetalleItem.setCantTotalIngreso(devolucionesDetalleItem.getCantidadDevueltaFallados());
                        ingresosAlmacenDetalleItem.setCantTotalIngresoFisico(devolucionesDetalleItem.getCantidadDevueltaFallados());
                        ingresosAlmacenDetalleItem.setUnidadesMedida(devolucionesDetalleItem.getUnidadesMedida());

                        consulta = " insert into ingresos_almacen_detalle (cod_ingreso_almacen, cod_material,nro_unidades_empaque,cod_seccion,cant_total_ingreso," +
                                " cant_total_ingreso_fisico,cod_unidad_medida,costo_promedio,costo_unitario,precio_neto,precio_unitario_material)" +
                                " values ('"+ingresosAlmacen.getCodIngresoAlmacen()+"','"+ingresosAlmacenDetalleItem.getMateriales().getCodMaterial()+"'," +
                                " 1,'"+managedIngresoAlmacen.buscaSeccionAlmacen(ingresosAlmacenDetalleItem,usuarioFrv).getCodSeccion() +"','"+ingresosAlmacenDetalleItem.getCantTotalIngreso()+"'," +
                                "'"+ingresosAlmacenDetalleItem.getCantTotalIngresoFisico()+"','"+ingresosAlmacenDetalleItem.getUnidadesMedida().getCodUnidadMedida()+"'," +
                                "'"+ingresosAlmacenDetalleItem.getCostoPromedio()+"','"+ingresosAlmacenDetalleItem.getCostoUnitario()+"','"+ingresosAlmacenDetalleItem.getPrecioNeto()+"'," +
                                "'"+ingresosAlmacenDetalleItem.getPrecioUnitarioMaterial()+"' ) ";
                        System.out.println("consulta " + consulta);
                        st.executeUpdate(consulta);



                        consulta = "delete from ingresos_almacen_detalle_estado where cod_ingreso_almacen = '"+ingresosAlmacen.getCodIngresoAlmacen()+"' ";
                        System.out.println(" consulta " + consulta);
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

                        consulta = " insert into ingresos_almacen_detalle_estado(etiqueta,cod_ingreso_almacen,cod_material,cod_estado_material,cod_empaque_secundario_externo,cantidad_parcial,cantidad_restante,fecha_vencimiento) " +
                                " values('"+ingresosAlmacenDetalleEstadoItem.getEtiqueta()+"','"+ingresosAlmacen.getCodIngresoAlmacen()+"'," +
                                "'"+ingresosAlmacenDetalleEstadoItem.getMateriales().getCodMaterial()+"','"+ingresosAlmacenDetalleEstadoItem.getEstadosMaterial().getCodEstadoMaterial() +"'," +
                                " '"+ingresosAlmacenDetalleEstadoItem.getEmpaqueSecundarioExterno().getCodEmpaqueSecundarioExterno()+"','"+ingresosAlmacenDetalleEstadoItem.getCantidadParcial()+"'," +
                                " '"+ingresosAlmacenDetalleEstadoItem.getCantidadRestante()+"','"+ingresosAlmacenDetalleEstadoItem.getFechaVencimiento()+"') ";

                        System.out.println("consulta " + consulta);
                        st.executeUpdate(consulta);
                        }
                    }
                  }
            }else{
                consulta = " update ingresos_almacen set cod_estado_ingreso_almacen = 2 where cod_ingreso_almacen in (select cod_ingreso_almacen from ingresos_almacen where cod_devolucion = '"+devoluciones.getCodDevolucion()+"') and cod_tipo_ingreso_almacen = 7 ";
                System.out.println("consulta " + consulta);
                st.executeUpdate(consulta);
            }



            st.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public IngresosAlmacen recuperaIngresoAlmacen(Devoluciones devoluciones,String codTipoIngresoAlmacen){
        IngresosAlmacen ingresosAlmacen = new IngresosAlmacen();
        try {
            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement();
                String consulta = " select i.COD_INGRESO_ALMACEN from INGRESOS_ALMACEN i where i.COD_DEVOLUCION = '"+devoluciones.getCodDevolucion()+"' and i.COD_TIPO_INGRESO_ALMACEN = '"+codTipoIngresoAlmacen+"' and i.COD_ESTADO_INGRESO_ALMACEN =1 ";
                ResultSet rs = st.executeQuery(consulta);

                if(rs.next()){
                ingresosAlmacen.setCodIngresoAlmacen(rs.getInt("COD_INGRESO_ALMACEN"));
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ingresosAlmacen;
    }
    //inicio alejandro buscador
    private void initBuscador()
    {
        this.cargarCapitulos();
        buscarDevolucion= new Devoluciones();
        buscarMaterial= new Materiales();
        this.fechaInicio=null;
        this.fechaFinal=null;
        buscarDevolucion.getSalidasAlmacen().getAreasEmpresa().setCodAreaEmpresa("0");
        this.onChangeCapitulo();
        this.onChange_grupo();
        this.cargarTiposSalidas();
        this.cargarEstadosDevoluciones();
        this.cargarAreasEmpresa();
    }
    private void cargarAreasEmpresa()
    {
        try
        {
            Connection con=null;
            con=Util.openConnection(con);
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            String consulta="select a.COD_ALMACEN,a.NOMBRE_ALMACEN from ALMACENES a order by a.NOMBRE_ALMACEN";
            ResultSet res=st.executeQuery(consulta);
            listaAreasEmpresa.clear();
            listaAreasEmpresa.add(new SelectItem("0","--TODOS--"));
            while(res.next())
            {
                listaAreasEmpresa.add(new SelectItem(res.getString("COD_ALMACEN"),res.getString("NOMBRE_ALMACEN")));
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
    private void cargarEstadosDevoluciones()
    {
        try
        {
            Connection con =null;
            con=Util.openConnection(con);
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            String consulta="select ed.COD_ESTADO_DEVOLUCION,ed.NOMBRE_ESTADO_DEVOLUCION from ESTADOS_DEVOLUCIONES ed order by ed.NOMBRE_ESTADO_DEVOLUCION";
            ResultSet res=st.executeQuery(consulta);
            listaEstadosDevoluciones.clear();
            listaEstadosDevoluciones.add(new SelectItem(0,"--TODOS--"));
            while(res.next())
            {
                listaEstadosDevoluciones.add(new SelectItem(res.getInt("COD_ESTADO_DEVOLUCION"),res.getString("NOMBRE_ESTADO_DEVOLUCION")));
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
    private void cargarTiposSalidas()
    {
        try
        {
            Connection con=null;
            con=Util.openConnection(con);
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            String consulta="select tsa.COD_TIPO_SALIDA_ALMACEN,tsa.NOMBRE_TIPO_SALIDA_ALMACEN from TIPOS_SALIDAS_ALMACEN tsa order by tsa.NOMBRE_TIPO_SALIDA_ALMACEN";
            ResultSet res=st.executeQuery(consulta);
            listaTiposSalida.clear();
            listaTiposSalida.add(new SelectItem(0,"--TODOS--"));
            while(res.next())
            {
                listaTiposSalida.add(new SelectItem(res.getInt("COD_TIPO_SALIDA_ALMACEN"),res.getString("NOMBRE_TIPO_SALIDA_ALMACEN")));
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
    private void cargarCapitulos()
    {
        try
        {
            Connection con= null;
            con=Util.openConnection(con);
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            String consulta="select c.COD_CAPITULO,c.NOMBRE_CAPITULO from capitulos c where c.COD_ESTADO_REGISTRO=1 order by c.NOMBRE_CAPITULO";
            ResultSet res=st.executeQuery(consulta);

            listaCapitulos.clear();
            listaCapitulos.add(new SelectItem(0,"--TODOS--"));
            while(res.next())
            {
                listaCapitulos.add(new SelectItem(res.getInt("COD_CAPITULO"), res.getString("NOMBRE_CAPITULO")));
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
    public String buscarDevolución_Action()
    {
        this.begin = 1;
        this.end = 10;
        this.listadoDevolucionesAlmacen();
        return null;
    }
    public String onChangeCapitulo()
    {
        try
        {
            Connection con=null;
            con=Util.openConnection(con);
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            String consulta="select g.COD_GRUPO,g.NOMBRE_GRUPO from GRUPOS g where g.COD_CAPITULO='"+buscarMaterial.getGrupos().getCapitulos().getCodCapitulo()+"' order by g.NOMBRE_GRUPO";
            System.out.println("consulta "+consulta);
            ResultSet res=st.executeQuery(consulta);
            listagrupos.clear();
            listagrupos.add(new SelectItem(0,"--TODOS--"));
            while(res.next())
            {
                listagrupos.add(new SelectItem(res.getInt("COD_GRUPO"),res.getString("NOMBRE_GRUPO")));

            }
            listaMateriales.clear();
            listaMateriales.add(new SelectItem("0","--TODOS--"));
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
    public String onChange_grupo()
    {
        try
        {
            Connection con=null;
            con=Util.openConnection(con);
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            String consulta="select m.COD_MATERIAL,m.NOMBRE_MATERIAL from MATERIALES m where m.COD_GRUPO='"+buscarMaterial.getGrupos().getCodGrupo()+"' order by m.NOMBRE_MATERIAL";
            System.out.println("consulta "+consulta);
            ResultSet res=st.executeQuery(consulta);
            listaMateriales.clear();
            listaMateriales.add(new SelectItem("0","--TODOS--"));
            while(res.next())
            {
                listaMateriales.add(new SelectItem(res.getString("COD_MATERIAL"),res.getString("NOMBRE_MATERIAL")));
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
    //final alejandro buscador
    //inicio ale devoluciones prod
    public String getCargarProgramaProduccionDevolucionMaterial()
    {

        try
        {
            String consulta="select ppdm.fecha_registro,ppdm.COD_PROGRAMA_PRODUCCION_DEVOLUCION_MATERIAL,ppdm.COD_ESTADO_PROGRAMA_PRODUCCION_DEVOLUCION,eppd.NOMBRE_ESTADO_PROGRAMA_PRODUCCION_DEVOLUCION," +
                            " ppdm.COD_PROGRAMA_PROD,cp.nombre_prod_semiterminado,ppdm.COD_LOTE_PRODUCCION,ppdm.COD_COMPPROD,ppdm.COD_TIPO_PROGRAMA_PROD," +
                            " tpp.NOMBRE_TIPO_PROGRAMA_PROD,ae.NOMBRE_AREA_EMPRESA,ida.CANT_TOTAL_INGRESO,ppdm.COD_FORMULA_MAESTRA"+
                            " from PROGRAMA_PRODUCCION_DEVOLUCION_MATERIAL ppdm inner join TIPOS_PROGRAMA_PRODUCCION tpp"+
                            " on tpp.COD_TIPO_PROGRAMA_PROD=ppdm.COD_TIPO_PROGRAMA_PROD inner join COMPONENTES_PROD cp "+
                            " on cp.COD_COMPPROD=ppdm.COD_COMPPROD inner join AREAS_EMPRESA ae on ae.COD_AREA_EMPRESA=cp.COD_AREA_EMPRESA"+
                            " inner join PROGRAMA_PRODUCCION_INGRESOS_ACOND ppia on "+
                            " ppia.COD_COMPPROD=ppdm.COD_COMPPROD and ppia.COD_FORMULA_MAESTRA=ppdm.COD_FORMULA_MAESTRA"+
                            " and ppia.COD_LOTE_PRODUCCION=ppdm.COD_LOTE_PRODUCCION and ppia.COD_PROGRAMA_PROD=ppdm.COD_PROGRAMA_PROD"+
                            " and ppia.COD_TIPO_PROGRAMA_PROD=ppdm.COD_TIPO_PROGRAMA_PROD and ppia.COD_TIPO_ENTREGA_ACOND=2"+
                            " inner join INGRESOS_ACOND ia on ia.COD_INGRESO_ACOND=ppia.COD_INGRESO_ACOND "+
                            " inner join INGRESOS_DETALLEACOND ida on ia.COD_INGRESO_ACOND=ida.COD_INGRESO_ACOND and ida.COD_COMPPROD=ppdm.COD_COMPPROD" +
                            " inner join ESTADOS_PROGRAMA_PRODUCCION_DEVOLUCION eppd on eppd.COD_ESTADO_PROGRAMA_PRODUCCION_DEVOLUCION=ppdm.COD_ESTADO_PROGRAMA_PRODUCCION_DEVOLUCION" +
                            " group by ppdm.fecha_registro,ppdm.COD_PROGRAMA_PRODUCCION_DEVOLUCION_MATERIAL,ppdm.COD_ESTADO_PROGRAMA_PRODUCCION_DEVOLUCION,eppd.NOMBRE_ESTADO_PROGRAMA_PRODUCCION_DEVOLUCION," +
                            " ppdm.COD_PROGRAMA_PROD,cp.nombre_prod_semiterminado,ppdm.COD_LOTE_PRODUCCION,ppdm.COD_COMPPROD,ppdm.COD_TIPO_PROGRAMA_PROD," +
                            " tpp.NOMBRE_TIPO_PROGRAMA_PROD,ae.NOMBRE_AREA_EMPRESA,ida.CANT_TOTAL_INGRESO,ppdm.COD_FORMULA_MAESTRA" +
                            " order by ppdm.fecha_registro desc,cp.nombre_prod_semiterminado asc ";
            System.out.println("consulta cargar "+consulta);
            Connection con1=null;
            con1=Util.openConnection(con1);
            Statement st=con1.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet res=st.executeQuery(consulta);
            programaProdDevMatList.clear();
            while(res.next())
            {
                ProgramaProduccionDevolucionMaterial bean= new ProgramaProduccionDevolucionMaterial();
                bean.getProgramaProduccion().getFormulaMaestra().setCodFormulaMaestra(res.getString("COD_FORMULA_MAESTRA"));
                bean.getProgramaProduccion().getFormulaMaestra().getComponentesProd().setCodCompprod(res.getString("COD_COMPPROD"));
                bean.getProgramaProduccion().getFormulaMaestra().getComponentesProd().setNombreProdSemiterminado(res.getString("nombre_prod_semiterminado"));
                bean.getProgramaProduccion().setCodLoteProduccion(res.getString("COD_LOTE_PRODUCCION"));
                bean.getProgramaProduccion().getTiposProgramaProduccion().setCodTipoProgramaProd(res.getString("COD_TIPO_PROGRAMA_PROD"));
                bean.getProgramaProduccion().getTiposProgramaProduccion().setNombreProgramaProd(res.getString("NOMBRE_TIPO_PROGRAMA_PROD"));
                bean.getProgramaProduccion().getFormulaMaestra().getComponentesProd().getAreasEmpresa().setNombreAreaEmpresa(res.getString("NOMBRE_AREA_EMPRESA"));
                bean.getProgramaProduccion().setCantIngresoAcond(res.getDouble("CANT_TOTAL_INGRESO"));
                bean.getProgramaProduccion().setCodProgramaProduccion(res.getString("COD_PROGRAMA_PROD"));
                bean.getEstadoProgramaProduccionDevolucion().setCodEstadoRegistro(res.getString("COD_ESTADO_PROGRAMA_PRODUCCION_DEVOLUCION"));
                bean.getEstadoProgramaProduccionDevolucion().setNombreEstadoRegistro(res.getString("NOMBRE_ESTADO_PROGRAMA_PRODUCCION_DEVOLUCION"));
                bean.setCodProgramaProduccionDevolucionMaterial(res.getString("COD_PROGRAMA_PRODUCCION_DEVOLUCION_MATERIAL"));
                bean.setFechaRegistro(res.getTimestamp("fecha_registro"));
                programaProdDevMatList.add(bean);
            }
            res.close();
            st.close();
            con1.close();
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
    public String rechazarProgProdDevolucionMaterial()
    {
        setMensaje("");
        for(ProgramaProduccionDevolucionMaterial bean: programaProdDevMatList)
        {
            if(bean.getChecked())
            {
                if(Integer.valueOf(bean.getEstadoProgramaProduccionDevolucion().getCodEstadoRegistro())==1)
                {
                    try
                    {
                    Connection con1=null;
                    con1=Util.openConnection(con1);
                    String consulta="UPDATE PROGRAMA_PRODUCCION_DEVOLUCION_MATERIAL SET COD_ESTADO_PROGRAMA_PRODUCCION_DEVOLUCION = 4"+
                                      " WHERE  COD_PROGRAMA_PRODUCCION_DEVOLUCION_MATERIAL = '"+bean.getCodProgramaProduccionDevolucionMaterial()+"'";
                      System.out.println("Consulta anular devolucion "+consulta);
                      PreparedStatement pst=con1.prepareStatement(consulta);
                      if(pst.executeUpdate()>0)System.out.println("se rechazo la devolucion generada por el jefe de area");
                        pst.close();
                        con1.close();
                        setMensaje("Se anulo el registro satisfactoriamente");
                    }
                    catch(SQLException ex)
                    {
                        ex.printStackTrace();
                    }
                }
                else
                {
                    setMensaje("Solo se pueden anular registros generados");
                    return null;
                }
            }
        }

        return this.getCargarProgramaProduccionDevolucionMaterial();
    }

    public void cargarSalidasDevolucionMaterial()
    {
        try {
            ManagedAccesoSistema user=(ManagedAccesoSistema)Util.getSessionBean("ManagedAccesoSistema");
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = "select s.COD_SALIDA_ALMACEN,s.COD_LOTE_PRODUCCION,s.NRO_SALIDA_ALMACEN,s.FECHA_SALIDA_ALMACEN, t.NOMBRE_TIPO_SALIDA_ALMACEN,a.NOMBRE_AREA_EMPRESA, " +
                    " p.AP_PATERNO_PERSONAL,p.AP_MATERNO_PERSONAL,p.NOMBRES_PERSONAL,s.OBS_SALIDA_ALMACEN, " +
                    " (select cp.nombre_prod_semiterminado from COMPONENTES_PROD cp where cp.COD_COMPPROD = s.COD_PROD) nombre_prod_semiterminado," +
                    " (select prp.NOMBRE_PRODUCTO_PRESENTACION from PRESENTACIONES_PRODUCTO prp where prp.cod_presentacion = s.COD_PRESENTACION ) NOMBRE_PRODUCTO_PRESENTACION," +
                    " s.orden_trabajo " +
                    " from SALIDAS_ALMACEN s" +
                    " inner join salidas_almacen_detalle sad on sad.cod_salida_almacen = s.cod_salida_almacen " +
                    " inner join TIPOS_SALIDAS_ALMACEN t on t.COD_TIPO_SALIDA_ALMACEN = s.COD_TIPO_SALIDA_ALMACEN " +
                    " inner join AREAS_EMPRESA a on a.COD_AREA_EMPRESA = s.COD_AREA_EMPRESA " +
                    " inner join PERSONAL p on p.COD_PERSONAL = s.COD_PERSONAL " +
                    " where s.COD_ESTADO_SALIDA_ALMACEN <>2 and s.COD_LOTE_PRODUCCION = '"+nuevaSalidaAlmacen.getCodLoteProduccion()+"' "+
                    " and s.COD_PROD = '"+nuevaSalidaAlmacen.getComponentesProd().getCodCompprod()+"' " +
                    " and s.COD_ALMACEN='"+user.getAlmacenesGlobal().getCodAlmacen()+"'" +
                    " and sad.cod_material in(select cod_material from PROGRAMA_PRODUCCION_DEVOLUCION_MATERIAL_DETALLE where COD_PROGRAMA_PRODUCCION_DEVOLUCION_MATERIAL = '"+progProdDevMatCurrent.getCodProgramaProduccionDevolucionMaterial()+"') " ;

            System.out.println("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            salidasAlmacenBuscarList.clear();
            while(rs.next()){
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
    public String generarDevolucionMaterial()
    {
        nuevaSalidaAlmacen= new SalidasAlmacen();
         setMensaje("");
          for(ProgramaProduccionDevolucionMaterial current:programaProdDevMatList)
          {
              if(current.getChecked())
              {
                  progProdDevMatCurrent=current;
                  nuevaSalidaAlmacen.setCodLoteProduccion(current.getProgramaProduccion().getCodLoteProduccion());
                  nuevaSalidaAlmacen.getComponentesProd().setNombreProdSemiterminado(current.getProgramaProduccion().getFormulaMaestra().getComponentesProd().getNombreProdSemiterminado());
                  nuevaSalidaAlmacen.getComponentesProd().setCodCompprod(current.getProgramaProduccion().getFormulaMaestra().getComponentesProd().getCodCompprod());
              }
          }

          this.cargarSalidasDevolucionMaterial();
        return null;
    }
   public String verReporteSalidaAlmacenProgDev_action(){
         SalidasAlmacen salidasAlmacen = (SalidasAlmacen)salidaAlmacenProgDevDataTable.getRowData();
         FacesContext facesContext = FacesContext.getCurrentInstance();
         ExternalContext ec = facesContext.getExternalContext();
         Map map = ec.getSessionMap();
         map.put("salidasAlmacen",salidasAlmacen);
         return null;
   }
   public String generarDevolucionMaterial_Action()
   {
        try {
            Iterator i = salidasAlmacenBuscarList.iterator();
            while(i.hasNext()){
                SalidasAlmacen salidasAlmacenItem = (SalidasAlmacen)i.next();
                if(salidasAlmacenItem.getChecked().booleanValue()==true){
                    nuevaSalidaAlmacen = salidasAlmacenItem ;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

       return null;
   }
   public String getCargarAgregarDevolucionAlmacenMaterial(){
        try {
            //se carga los datos de devolucion
            devoluciones.setSalidasAlmacen(nuevaSalidaAlmacen);
            devolucionesDetalleMatList = this.cargarDetalleDevolucionMaterial();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
   private List cargarDetalleDevolucionMaterial()
   {
        List devolucionesDetalleList = new ArrayList();
        try {
            Connection con = null;
            con = Util.openConnection(con);

            String consulta = " SELECT s.COD_SALIDA_ALMACEN,s.COD_MATERIAL,s.CANTIDAD_SALIDA_ALMACEN,  s.COD_UNIDAD_MEDIDA,"+
                              " s.COD_ESTADO_MATERIAL,m.NOMBRE_MATERIAL,u.NOMBRE_UNIDAD_MEDIDA,u.ABREVIATURA,e.NOMBRE_ESTADO_MATERIAL,"+
                              " (ppdmd.CANTIDAD_BUENOS-ppdmd.CANTIDAD_BUENOS_ENTREGADOS) as cantBuenosRestantes,"+
                              " (ppdmd.CANTIDAD_MALOS-ppdmd.CANTIDAD_MALOS_ENTREGADOS) as cantMalosRestantes"+
                              " FROM SALIDAS_ALMACEN_DETALLE s inner join MATERIALES m on m.COD_MATERIAL = s.COD_MATERIAL"+
                              " inner join UNIDADES_MEDIDA u on u.COD_UNIDAD_MEDIDA = s.COD_UNIDAD_MEDIDA"+
                              " inner join ESTADOS_MATERIAL e on e.COD_ESTADO_MATERIAL = s.COD_ESTADO_MATERIAL"+
                              " inner join SALIDAS_ALMACEN sa on sa.COD_SALIDA_ALMACEN = s.COD_SALIDA_ALMACEN"+
                              " inner join PROGRAMA_PRODUCCION_DEVOLUCION_MATERIAL ppdm on ppdm.COD_LOTE_PRODUCCION = sa.COD_LOTE_PRODUCCION and ppdm.COD_COMPPROD ="+
                              " sa.COD_PROD inner join PROGRAMA_PRODUCCION_DEVOLUCION_MATERIAL_DETALLE ppdmd on "+
                              " ppdm.COD_PROGRAMA_PRODUCCION_DEVOLUCION_MATERIAL=ppdmd.COD_PROGRAMA_PRODUCCION_DEVOLUCION_MATERIAL"+
                              " and ppdmd.COD_MATERIAL=m.COD_MATERIAL where ppdm.COD_PROGRAMA_PRODUCCION_DEVOLUCION_MATERIAL='"+progProdDevMatCurrent.getCodProgramaProduccionDevolucionMaterial()+"' and"+
                              " s.COD_SALIDA_ALMACEN = '"+nuevaSalidaAlmacen.getCodSalidaAlmacen()+"' and((ppdmd.CANTIDAD_BUENOS-ppdmd.CANTIDAD_BUENOS_ENTREGADOS)>0 or(ppdmd.CANTIDAD_MALOS-ppdmd.CANTIDAD_MALOS_ENTREGADOS)>0)";
            System.out.println("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            devolucionesDetalleList.clear();

            while(rs.next()){
                DevolucionesDetalle devolucionesDetalle = new DevolucionesDetalle();
                devolucionesDetalle.getDevoluciones().getSalidasAlmacen().setCodSalidaAlmacen(rs.getInt("COD_SALIDA_ALMACEN"));
                devolucionesDetalle.getMateriales().setCodMaterial(rs.getString("COD_MATERIAL"));
                devolucionesDetalle.getMateriales().setNombreMaterial(rs.getString("NOMBRE_MATERIAL"));
                devolucionesDetalle.setCantidadEntregada(rs.getFloat("CANTIDAD_SALIDA_ALMACEN"));
                devolucionesDetalle.setCantidadDevuelta(rs.getFloat("cantBuenosRestantes"));
                devolucionesDetalle.setCantidadDevueltaFallados(rs.getFloat("cantMalosRestantes"));
                devolucionesDetalle.setCantidadDevueltaFalladosProveedor(0);
                devolucionesDetalle.getUnidadesMedida().setCodUnidadMedida(rs.getInt("COD_UNIDAD_MEDIDA"));
                devolucionesDetalle.getUnidadesMedida().setAbreviatura(rs.getString("ABREVIATURA"));
                cantidadADevolver=0f;
                cantMalasADevolver=0f;
                devolucionesDetalle.setDevolucionesDetalleEtiquetasList(this.listaDevolucionDetalleEtiquetasMaterial(devolucionesDetalle));
                devolucionesDetalle.setCantidadDevuelta(cantidadADevolver);
                devolucionesDetalle.setCantidadDevueltaFallados(cantMalasADevolver);
                devolucionesDetalle.setCantidadDevueltaRef(cantidadADevolver);
                devolucionesDetalle.setCantidadDevueltaFalladosRef(cantMalasADevolver);
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
   public List listaDevolucionDetalleEtiquetasMaterial(DevolucionesDetalle devolucionesDetalle){
        List devolucionesDetalleEtiquetasList = new ArrayList();
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select i.COD_INGRESO_ALMACEN,i.NRO_INGRESO_ALMACEN,s.ETIQUETA,s.CANTIDAD,s.COD_MATERIAL, s.FECHA_VENCIMIENTO," +
                    " (select sum(ISNULL(dde.CANTIDAD_DEVUELTA,0)+ISNULL(dde.CANTIDAD_FALLADOS,0)+ISNULL(dde.cantidad_fallados_proveedor,0)) from DEVOLUCIONES d " +
                    " inner join DEVOLUCIONES_DETALLE dd on dd.COD_DEVOLUCION = d.COD_DEVOLUCION " +
                    " inner join DEVOLUCIONES_DETALLE_ETIQUETAS dde on dde.COD_DEVOLUCION = dd.COD_DEVOLUCION " +
                    " and dde.COD_MATERIAL = dd.COD_MATERIAL " +
                    " where d.COD_ESTADO_DEVOLUCION = 1 " +
                    " and d.COD_SALIDA_ALMACEN =s.COD_SALIDA_ALMACEN " +
                    " and dd.COD_MATERIAL = s.COD_MATERIAL " +
                    " and dde.COD_INGRESO_ALMACEN = i.COD_INGRESO_ALMACEN " +
                    " and dde.ETIQUETA = s.ETIQUETA ) CANTIDADES_DEVUELTAS " +
                    "  from SALIDAS_ALMACEN_DETALLE_INGRESO s " +
                    " inner join INGRESOS_ALMACEN i on i.COD_INGRESO_ALMACEN = s.COD_INGRESO_ALMACEN " +
                    " where s.COD_SALIDA_ALMACEN = '"+nuevaSalidaAlmacen.getCodSalidaAlmacen()+"' and s.cantidad>0" +
                    " and s.COD_MATERIAL = '"+devolucionesDetalle.getMateriales().getCodMaterial()+"'";
            System.out.println("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            devolucionesDetalleEtiquetasList.clear();
            float cantidad=devolucionesDetalle.getCantidadDevuelta();
            float cantidadMalos=devolucionesDetalle.getCantidadDevueltaFallados();
            while(rs.next()){
                DevolucionesDetalleEtiquetas devolucionesDetalleEtiquetas = new DevolucionesDetalleEtiquetas();
                devolucionesDetalleEtiquetas.getIngresosAlmacen().setNroIngresoAlmacen(rs.getInt("NRO_INGRESO_ALMACEN"));
                devolucionesDetalleEtiquetas.getIngresosAlmacen().setCodIngresoAlmacen(rs.getInt("COD_INGRESO_ALMACEN"));
                devolucionesDetalleEtiquetas.getMateriales().setCodMaterial(rs.getString("COD_MATERIAL"));
                devolucionesDetalleEtiquetas.setEtiqueta(rs.getInt("ETIQUETA"));
                devolucionesDetalleEtiquetas.setCantidadSalida(rs.getFloat("CANTIDAD"));
                devolucionesDetalleEtiquetas.setFechaVencimiento(rs.getDate("FECHA_VENCIMIENTO"));
                devolucionesDetalleEtiquetas.setCantidadesDevueltas(rs.getFloat("CANTIDADES_DEVUELTAS"));

                if((devolucionesDetalleEtiquetas.getCantidadSalida()-devolucionesDetalleEtiquetas.getCantidadesDevueltas())>cantidad)
                {
                    System.out.println("cantidad malos "+cantidadMalos);
                    devolucionesDetalleEtiquetas.setCantidadDevuelta(cantidad);
                    cantidad=0;
                    if((devolucionesDetalleEtiquetas.getCantidadSalida()-devolucionesDetalleEtiquetas.getCantidadesDevueltas()-devolucionesDetalleEtiquetas.getCantidadDevuelta())>cantidadMalos)
                    {
                        devolucionesDetalleEtiquetas.setCantidadFallados(cantidadMalos);
                        cantidadMalos=0;
                    }
                    else
                    {
                        cantidadMalos-=(devolucionesDetalleEtiquetas.getCantidadSalida()-devolucionesDetalleEtiquetas.getCantidadesDevueltas()-devolucionesDetalleEtiquetas.getCantidadDevuelta());
                        devolucionesDetalleEtiquetas.setCantidadFallados((devolucionesDetalleEtiquetas.getCantidadSalida()-devolucionesDetalleEtiquetas.getCantidadesDevueltas())-devolucionesDetalleEtiquetas.getCantidadDevuelta());
                    }
                }
                else
                {
                    cantidad-=(devolucionesDetalleEtiquetas.getCantidadSalida()-devolucionesDetalleEtiquetas.getCantidadesDevueltas());
                    devolucionesDetalleEtiquetas.setCantidadDevuelta((devolucionesDetalleEtiquetas.getCantidadSalida()-devolucionesDetalleEtiquetas.getCantidadesDevueltas()));
                }
                //System.out.println("cantidad "+cantidad);
                cantidadADevolver+=devolucionesDetalleEtiquetas.getCantidadDevuelta();
                cantMalasADevolver+=devolucionesDetalleEtiquetas.getCantidadFallados();
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
    public String verDevolucionesDetalleEtiquetasMat_action(){
        try {
            devolucionesDetalleMat = (DevolucionesDetalle)devolucionesProgProdDevMat.getRowData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public String registrarDevolucionesDetalleEtiquetasMat_action(){
        try {
            Iterator i = devolucionesDetalleMat.getDevolucionesDetalleEtiquetasList().iterator();
             float cantidadDevuelta = 0;
             float cantidadFallados=0;
             while(i.hasNext()){
                DevolucionesDetalleEtiquetas devolucionesDetalleEtiquetas = (DevolucionesDetalleEtiquetas)i.next();
                cantidadDevuelta = cantidadDevuelta + devolucionesDetalleEtiquetas.getCantidadDevuelta();
                cantidadFallados=cantidadFallados+devolucionesDetalleEtiquetas.getCantidadFallados();
               }
            devolucionesDetalleMat.setCantidadDevuelta(cantidadDevuelta);
            devolucionesDetalleMat.setCantidadDevueltaFallados(cantidadFallados);
            devolucionesDetalleMat.setCantidadDevueltaFalladosProveedor(0);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public String guardarDevolucionMaterial_action(){

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Connection con = null;
            con = Util.openConnection(con);
            PreparedStatement pst=null;
            //
            setMensaje("");
            usuario=(ManagedAccesoSistema)Util.getSessionBean("ManagedAccesoSistema");
             if(this.verificaTransaccionCerradaAlmacen()==true){
                 mensaje = "Las transacciones para este mes fueron cerradas ";
                 return null;
             }
             if(devolucionesDetalleMatList.size()==0){
                 mensaje = " No existe detalle de devolucion ";
                 return null;
             }
             if(existeDetalleDevolucionDetalleAlmacen(devolucionesDetalleMatList)==false){
                 mensaje = " No existe detalle de Cada Item ";
                 return null;
             }
            for(DevolucionesDetalle current:devolucionesDetalleMatList)
            {
                if(current.getCantidadDevuelta()>current.getCantidadDevueltaRef())
                {
                    setMensaje("No se puede registrar porque el producto "+current.getMateriales().getNombreMaterial()+" sobrepasa la cantidad de buenos a devolver registrada por el jefe de area que es "+current.getCantidadDevueltaRef());
                    return null;
                }
                if(current.getCantidadDevueltaFallados()>current.getCantidadDevueltaFalladosRef())
                {
                    setMensaje("No se puede registrar porque el producto "+current.getMateriales().getNombreMaterial()+" sobrepasa la cantidad de malos a devolver registrada por el jefe de area que es "+current.getCantidadDevueltaFalladosRef());
                    return null;
                }
            }
            devoluciones.setCodDevolucion(this.generaCodDevolucionAlmacen());
            devoluciones.setEstadoSistema(1);
            devoluciones.getEstadosDevoluciones().setCodEstadoDevolucion(1);

            String consulta = " INSERT INTO DEVOLUCIONES ( COD_DEVOLUCION,  NRO_DEVOLUCION, COD_FORMULARIO_DEV, COD_ALMACEN, " +
                    "  COD_SALIDA_ALMACEN,  COD_GESTION,  COD_ESTADO_DEVOLUCION,  ESTADO_SISTEMA,  OBS_DEVOLUCION,  COD_SALIDA_ALMACEN_AUX) " +
                    " VALUES ( '"+devoluciones.getCodDevolucion()+"', (select ISNULL(max(nro_devolucion),0)+1 from devoluciones  " +
                    " where cod_gestion='"+usuario.getGestionesGlobal().getCodGestion()+"' and cod_almacen='"+usuario.getAlmacenesGlobal().getCodAlmacen()+"'  and estado_sistema=1  ) ,'"+devoluciones.getCodFormularioDev()+"',   " +
                    " '"+usuario.getAlmacenesGlobal().getCodAlmacen()+"' , '"+nuevaSalidaAlmacen.getCodSalidaAlmacen()+"'  ,   '"+ usuario.getGestionesGlobal().getCodGestion()+"', " +
                    "  '"+devoluciones.getEstadosDevoluciones().getCodEstadoDevolucion()+"','"+devoluciones.getEstadoSistema()+"', 'Generado a partir de una devolución de jefe de area',   '"+devoluciones.getCodSalidaAlmacenAux()+"' ); ";
            System.out.println("consulta " + consulta);
            pst=con.prepareStatement(consulta);
            if(pst.executeUpdate()>0)System.out.println("se registro la cabecera de la devolución");

            Iterator i = devolucionesDetalleMatList.iterator();
            double cantidadBuenas=0;
            double cantidadFalladas=0;
            while(i.hasNext()){
                DevolucionesDetalle devolucionesDetalleItem = (DevolucionesDetalle)i.next();
                if(devolucionesDetalleItem.getCantidadDevuelta()>0)
                {
                consulta = " INSERT INTO DEVOLUCIONES_DETALLE(  COD_DEVOLUCION,  COD_MATERIAL,  CANTIDAD_DEVUELTA,  COD_UNIDAD_MEDIDA) " +
                        " VALUES (   '"+devoluciones.getCodDevolucion()+"', '"+devolucionesDetalleItem.getMateriales().getCodMaterial()+"', " +
                        " '"+devolucionesDetalleItem.getCantidadDevuelta()+"', '"+devolucionesDetalleItem.getUnidadesMedida().getCodUnidadMedida()+"'); ";
                System.out.println("consulta " + consulta);
                pst=con.prepareStatement(consulta);
                cantidadBuenas+=devolucionesDetalleItem.getCantidadDevuelta();
                cantidadFalladas+=devolucionesDetalleItem.getCantidadDevueltaFallados();
                if(pst.executeUpdate()>0)System.out.println("se registro el detalle de la devolución");
                Iterator i1 = devolucionesDetalleItem.getDevolucionesDetalleEtiquetasList().iterator();
                 while(i1.hasNext()){
                    DevolucionesDetalleEtiquetas devolucionesDetalleEtiquetasItem = (DevolucionesDetalleEtiquetas)i1.next();
                    if(devolucionesDetalleEtiquetasItem.getCantidadDevuelta()>0 ){
                    consulta = " INSERT INTO DEVOLUCIONES_DETALLE_ETIQUETAS(  COD_DEVOLUCION,  COD_INGRESO_ALMACEN,  COD_MATERIAL,  ETIQUETA," +
                            "  CANTIDAD_DEVUELTA,  CANTIDAD_FALLADOS,cantidad_fallados_proveedor) VALUES ( '"+devoluciones.getCodDevolucion() +"', '"+devolucionesDetalleEtiquetasItem.getIngresosAlmacen().getCodIngresoAlmacen() +"' ," +
                            "  '"+devolucionesDetalleEtiquetasItem.getMateriales().getCodMaterial()+"',  '"+devolucionesDetalleEtiquetasItem.getEtiqueta()+"'," +
                            "  '"+devolucionesDetalleEtiquetasItem.getCantidadDevuelta()+"','"+devolucionesDetalleEtiquetasItem.getCantidadFallados()+"','0'); ";
                    System.out.println("consulta " + consulta);
                    pst=con.prepareStatement(consulta);
                    if(pst.executeUpdate()>0)System.out.println("se registro el detalle etiquetas");
                    consulta = " update ingresos_almacen_detalle_estado set cantidad_restante=cantidad_restante+"+devolucionesDetalleEtiquetasItem.getCantidadDevuelta()+" " +
                            " where cod_ingreso_almacen='"+devolucionesDetalleEtiquetasItem.getIngresosAlmacen().getCodIngresoAlmacen()+"' " +
                            " and cod_material='"+devolucionesDetalleEtiquetasItem.getMateriales().getCodMaterial()+"' " +
                            " and etiqueta='"+devolucionesDetalleEtiquetasItem.getEtiqueta()+"'";
                    System.out.println("consulta " + consulta);
                    pst=con.prepareStatement(consulta);
                    if(pst.executeUpdate()>0)System.out.println("se actualizo el ingreso almacen detalle");
                    }
                 }
                }
            }

            IngresosAlmacen ingresosAlmacen = new IngresosAlmacen();
            ManagedIngresoAlmacen managedIngresoAlmacen = new ManagedIngresoAlmacen();
            if(cantidadBuenas>0){
                ingresosAlmacen.setCodIngresoAlmacen(managedIngresoAlmacen.generaCodIngresoAlmacen());
                ingresosAlmacen.getTiposIngresoAlmacen().setCodTipoIngresoAlmacen(6);//por devolucion
                ingresosAlmacen.getGestiones().setCodGestion(usuario.getGestionesGlobal().getCodGestion());
                ingresosAlmacen.getEstadosIngresoAlmacen().setCodEstadoIngresoAlmacen(1);
                ingresosAlmacen.setNroIngresoAlmacen(managedIngresoAlmacen.obtieneNroIngresoAlmacen(usuario));
                ingresosAlmacen.setEstadoSistema(1);
                ingresosAlmacen.getAlmacenes().setCodAlmacen(usuario.getAlmacenesGlobal().getCodAlmacen());
                ingresosAlmacen.setObsIngresoAlmacen(devoluciones.getObsDevolucion());
                ingresosAlmacen.getPersonal().setCodPersonal(usuario.getUsuarioModuloBean().getCodUsuarioGlobal());
                ingresosAlmacen.setDevoluciones(devoluciones);
                ingresosAlmacen.setFechaIngresoAlmacen(new Date());

                 consulta = " INSERT INTO INGRESOS_ALMACEN(  COD_INGRESO_ALMACEN,  COD_TIPO_INGRESO_ALMACEN,  COD_GESTION," +
                        "  COD_ESTADO_INGRESO_ALMACEN,  COD_DEVOLUCION,  FECHA_INGRESO_ALMACEN,  OBS_INGRESO_ALMACEN,  NRO_INGRESO_ALMACEN,  ESTADO_SISTEMA," +
                        "  COD_ALMACEN,  COD_PERSONAL ) " +
                        "  VALUES (  '"+ingresosAlmacen.getCodIngresoAlmacen()+"',  '"+ingresosAlmacen.getTiposIngresoAlmacen().getCodTipoIngresoAlmacen()+"', " +
                        "  '"+ingresosAlmacen.getGestiones().getCodGestion()+"',  '"+ingresosAlmacen.getEstadosIngresoAlmacen().getCodEstadoIngresoAlmacen()+"', " +
                        "  '"+ingresosAlmacen.getDevoluciones().getCodDevolucion()+"', '"+sdf.format(ingresosAlmacen.getFechaIngresoAlmacen())+"'," +
                        "  '"+ingresosAlmacen.getObsIngresoAlmacen()+"',  '"+ingresosAlmacen.getNroIngresoAlmacen()+"', " +
                        "  '"+ingresosAlmacen.getEstadoSistema()+"','"+ingresosAlmacen.getAlmacenes().getCodAlmacen()+"','"+ingresosAlmacen.getPersonal().getCodPersonal()+"' ); ";
                System.out.println("consulta " + consulta);
                pst=con.prepareStatement(consulta);
                if(pst.executeUpdate()>0)System.out.println("se registro el ingreso almacen");
                i = devolucionesDetalleMatList.iterator();
                while(i.hasNext()){
                    DevolucionesDetalle devolucionesDetalleItem = (DevolucionesDetalle)i.next();
                    if(devolucionesDetalleItem.getCantidadDevuelta()>0){
                    IngresosAlmacenDetalle ingresosAlmacenDetalleItem = new IngresosAlmacenDetalle();
                    ingresosAlmacenDetalleItem.setMateriales(devolucionesDetalleItem.getMateriales());
                    ingresosAlmacenDetalleItem.setCantTotalIngreso(devolucionesDetalleItem.getCantidadDevuelta());
                    ingresosAlmacenDetalleItem.setCantTotalIngresoFisico(devolucionesDetalleItem.getCantidadDevuelta());
                    ingresosAlmacenDetalleItem.setUnidadesMedida(devolucionesDetalleItem.getUnidadesMedida());

                    consulta = " insert into ingresos_almacen_detalle (cod_ingreso_almacen, cod_material,nro_unidades_empaque,cod_seccion,cant_total_ingreso," +
                            " cant_total_ingreso_fisico,cod_unidad_medida,costo_promedio,costo_unitario,precio_neto,precio_unitario_material)" +
                            " values ('"+ingresosAlmacen.getCodIngresoAlmacen()+"','"+ingresosAlmacenDetalleItem.getMateriales().getCodMaterial()+"'," +
                            " 1,'"+managedIngresoAlmacen.buscaSeccionAlmacen(ingresosAlmacenDetalleItem,usuario).getCodSeccion() +"','"+ingresosAlmacenDetalleItem.getCantTotalIngreso()+"'," +
                            "'"+ingresosAlmacenDetalleItem.getCantTotalIngresoFisico()+"','"+ingresosAlmacenDetalleItem.getUnidadesMedida().getCodUnidadMedida()+"'," +
                            "'"+ingresosAlmacenDetalleItem.getCostoPromedio()+"','"+ingresosAlmacenDetalleItem.getCostoUnitario()+"','"+ingresosAlmacenDetalleItem.getPrecioNeto()+"'," +
                            "'"+ingresosAlmacenDetalleItem.getPrecioUnitarioMaterial()+"' ) ";
                    System.out.println("consulta " + consulta);
                    pst=con.prepareStatement(consulta);
                    if(pst.executeUpdate()>0)System.out.println("se inserto el ingreso almacen detalle");

                    }
                }
            }
            if(cantidadFalladas>0){
                if(this.obtieneAlmacenFrv(usuario)==0){
                    mensaje = "seleccionar_almacenFrv";
                    return null;
                  }else{
                    //ingreso a frv
                    ingresosAlmacen.setCodIngresoAlmacen(managedIngresoAlmacen.generaCodIngresoAlmacen());
                    ingresosAlmacen.getTiposIngresoAlmacen().setCodTipoIngresoAlmacen(7);//por devolucion
                    ingresosAlmacen.getGestiones().setCodGestion(usuario.getGestionesGlobal().getCodGestion());
                    ingresosAlmacen.getEstadosIngresoAlmacen().setCodEstadoIngresoAlmacen(1);
                    ingresosAlmacen.setNroIngresoAlmacen(managedIngresoAlmacen.obtieneNroIngresoAlmacen(usuario));
                    ingresosAlmacen.setEstadoSistema(1);
                    ingresosAlmacen.getAlmacenes().setCodAlmacen(this.obtieneAlmacenFrv(usuario));
                    ingresosAlmacen.setObsIngresoAlmacen(devoluciones.getObsDevolucion());
                    ingresosAlmacen.getPersonal().setCodPersonal(usuario.getUsuarioModuloBean().getCodUsuarioGlobal());
                    ingresosAlmacen.setDevoluciones(devoluciones);
                    ingresosAlmacen.setFechaIngresoAlmacen(new Date());

                    ManagedAccesoSistema usuarioFrv = new ManagedAccesoSistema();
                    usuarioFrv.getAlmacenesGlobal().setCodAlmacen(this.obtieneAlmacenFrv(usuario));

                    consulta = " INSERT INTO INGRESOS_ALMACEN(  COD_INGRESO_ALMACEN,  COD_TIPO_INGRESO_ALMACEN,  COD_GESTION," +
                            "  COD_ESTADO_INGRESO_ALMACEN,  COD_DEVOLUCION,  FECHA_INGRESO_ALMACEN,  OBS_INGRESO_ALMACEN,  NRO_INGRESO_ALMACEN,  ESTADO_SISTEMA," +
                            "  COD_ALMACEN,  COD_PERSONAL ) " +
                            "  VALUES (  '"+ingresosAlmacen.getCodIngresoAlmacen()+"',  '"+ingresosAlmacen.getTiposIngresoAlmacen().getCodTipoIngresoAlmacen()+"', " +
                            "  '"+ingresosAlmacen.getGestiones().getCodGestion()+"',  '"+ingresosAlmacen.getEstadosIngresoAlmacen().getCodEstadoIngresoAlmacen()+"', " +
                            "  '"+ingresosAlmacen.getDevoluciones().getCodDevolucion()+"', '"+sdf.format(ingresosAlmacen.getFechaIngresoAlmacen())+"'," +
                            "  '"+ingresosAlmacen.getObsIngresoAlmacen()+"',  '"+ingresosAlmacen.getNroIngresoAlmacen()+"', " +
                            "  '"+ingresosAlmacen.getEstadoSistema()+"','"+ingresosAlmacen.getAlmacenes().getCodAlmacen()+"','"+ingresosAlmacen.getPersonal().getCodPersonal()+"' ); ";
                    System.out.println("consulta " + consulta);
                    pst=con.prepareStatement(consulta);
                    if(pst.executeUpdate()>0)System.out.println("se registro el ingreso en frv");
                    i = devolucionesDetalleMatList.iterator();
                    while(i.hasNext()){
                        DevolucionesDetalle devolucionesDetalleItem = (DevolucionesDetalle)i.next();

                        if(devolucionesDetalleItem.getCantidadDevueltaFallados()>0){
                        IngresosAlmacenDetalle ingresosAlmacenDetalleItem = new IngresosAlmacenDetalle();
                        ingresosAlmacenDetalleItem.setIngresosAlmacen(ingresosAlmacen);
                        ingresosAlmacenDetalleItem.setMateriales(devolucionesDetalleItem.getMateriales());
                        ingresosAlmacenDetalleItem.setCantTotalIngreso(devolucionesDetalleItem.getCantidadDevueltaFallados());
                        ingresosAlmacenDetalleItem.setCantTotalIngresoFisico(devolucionesDetalleItem.getCantidadDevueltaFallados());
                        ingresosAlmacenDetalleItem.setUnidadesMedida(devolucionesDetalleItem.getUnidadesMedida());

                        consulta = " insert into ingresos_almacen_detalle (cod_ingreso_almacen, cod_material,nro_unidades_empaque,cod_seccion,cant_total_ingreso," +
                                " cant_total_ingreso_fisico,cod_unidad_medida,costo_promedio,costo_unitario,precio_neto,precio_unitario_material)" +
                                " values ('"+ingresosAlmacen.getCodIngresoAlmacen()+"','"+ingresosAlmacenDetalleItem.getMateriales().getCodMaterial()+"'," +
                                " 1,'"+managedIngresoAlmacen.buscaSeccionAlmacen(ingresosAlmacenDetalleItem,usuarioFrv).getCodSeccion() +"','"+ingresosAlmacenDetalleItem.getCantTotalIngreso()+"'," +
                                "'"+ingresosAlmacenDetalleItem.getCantTotalIngresoFisico()+"','"+ingresosAlmacenDetalleItem.getUnidadesMedida().getCodUnidadMedida()+"'," +
                                "'"+ingresosAlmacenDetalleItem.getCostoPromedio()+"','"+ingresosAlmacenDetalleItem.getCostoUnitario()+"','"+ingresosAlmacenDetalleItem.getPrecioNeto()+"'," +
                                "'"+ingresosAlmacenDetalleItem.getPrecioUnitarioMaterial()+"' ) ";
                        System.out.println("consulta " + consulta);
                        pst=con.prepareStatement(consulta);
                        if(pst.executeUpdate()>0)System.out.println("se registro ingreso almacen detalle en frv");
                        IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstadoItem = new IngresosAlmacenDetalleEstado();
                        ingresosAlmacenDetalleEstadoItem.setEtiqueta(1);
                        ingresosAlmacenDetalleEstadoItem.setIngresosAlmacenDetalle(ingresosAlmacenDetalleItem);
                        ingresosAlmacenDetalleEstadoItem.setMateriales(ingresosAlmacenDetalleItem.getMateriales());
                        ingresosAlmacenDetalleEstadoItem.getEstadosMaterial().setCodEstadoMaterial(5); //reanalisis
                        ingresosAlmacenDetalleEstadoItem.getEmpaqueSecundarioExterno().setCodEmpaqueSecundarioExterno(1);//empaque
                        ingresosAlmacenDetalleEstadoItem.setCantidadParcial(devolucionesDetalleItem.getCantidadDevueltaFallados());
                        ingresosAlmacenDetalleEstadoItem.setCantidadRestante(devolucionesDetalleItem.getCantidadDevueltaFallados());
                        ingresosAlmacenDetalleEstadoItem.setFechaVencimiento(this.buscaFechaVencimiento(devolucionesDetalleItem));

                        consulta = " insert into ingresos_almacen_detalle_estado(etiqueta,cod_ingreso_almacen,cod_material,cod_estado_material,cod_empaque_secundario_externo,cantidad_parcial,cantidad_restante,fecha_vencimiento) " +
                                " values('"+ingresosAlmacenDetalleEstadoItem.getEtiqueta()+"','"+ingresosAlmacen.getCodIngresoAlmacen()+"'," +
                                "'"+ingresosAlmacenDetalleEstadoItem.getMateriales().getCodMaterial()+"','"+ingresosAlmacenDetalleEstadoItem.getEstadosMaterial().getCodEstadoMaterial() +"'," +
                                " '"+ingresosAlmacenDetalleEstadoItem.getEmpaqueSecundarioExterno().getCodEmpaqueSecundarioExterno()+"','"+ingresosAlmacenDetalleEstadoItem.getCantidadParcial()+"'," +
                                " '"+ingresosAlmacenDetalleEstadoItem.getCantidadRestante()+"','"+ingresosAlmacenDetalleEstadoItem.getFechaVencimiento()+"') ";

                        System.out.println("consulta " + consulta);
                        pst=con.prepareStatement(consulta);
                        if(pst.executeUpdate()>0)System.out.println("se registro ingreso almacen detalle estado en frv");
                        }

                    }

                  }
            }

            for(DevolucionesDetalle current: devolucionesDetalleMatList)
            {
                if(current.getCantidadDevuelta()>0||current.getCantidadDevueltaFallados()>0)
                {
                    consulta="UPDATE PROGRAMA_PRODUCCION_DEVOLUCION_MATERIAL_DETALLE SET CANTIDAD_BUENOS_ENTREGADOS =CANTIDAD_BUENOS_ENTREGADOS+ '"+current.getCantidadDevuelta()+"',"+
                             " CANTIDAD_MALOS_ENTREGADOS =CANTIDAD_MALOS_ENTREGADOS+ '"+current.getCantidadDevueltaFallados()+"' " +
                             " WHERE COD_PROGRAMA_PRODUCCION_DEVOLUCION_MATERIAL ='"+progProdDevMatCurrent.getCodProgramaProduccionDevolucionMaterial()+"'" +
                             " and COD_MATERIAL ='"+current.getMateriales().getCodMaterial()+"'";
                    System.out.println("consulta update progProdDevMat"+consulta);
                    pst=con.prepareStatement(consulta);
                    if(pst.executeUpdate()>0)System.out.println("se modifico el detalle");
                }
            }

            consulta=" INSERT INTO PROGRAMA_PRODUCCION_DEVOLUCION(COD_PROGRAMA_PRODUCCION_DEVOLUCION_MATERIAL, COD_DEVOLUCION)"+
                     " VALUES ('"+progProdDevMatCurrent.getCodProgramaProduccionDevolucionMaterial()+"','"+devoluciones.getCodDevolucion()+"')";
            System.out.println("consulta insert programa produccion devolucion "+consulta);
            pst=con.prepareStatement(consulta);
            if(pst.executeUpdate()>0)System.out.println("se inserto el programa produccion devolución");
            consulta="select top 1 * from PROGRAMA_PRODUCCION_DEVOLUCION_MATERIAL_DETALLE ppdmd"+
                      " where ppdmd.COD_PROGRAMA_PRODUCCION_DEVOLUCION_MATERIAL='"+progProdDevMatCurrent.getCodProgramaProduccionDevolucionMaterial()+"' and "+
                      " (ppdmd.CANTIDAD_BUENOS-ppdmd.CANTIDAD_BUENOS_ENTREGADOS)>0 and "+
                      " (ppdmd.CANTIDAD_MALOS-ppdmd.CANTIDAD_MALOS_ENTREGADOS)>0";
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet res=st.executeQuery(consulta);
            if(res.next())
            {
                consulta="UPDATE PROGRAMA_PRODUCCION_DEVOLUCION_MATERIAL SET COD_ESTADO_PROGRAMA_PRODUCCION_DEVOLUCION = 2"+
                         " WHERE COD_PROGRAMA_PRODUCCION_DEVOLUCION_MATERIAL = '"+progProdDevMatCurrent.getCodProgramaProduccionDevolucionMaterial()+"'";
            }
            else
            {
                consulta="UPDATE PROGRAMA_PRODUCCION_DEVOLUCION_MATERIAL SET COD_ESTADO_PROGRAMA_PRODUCCION_DEVOLUCION = 3"+
                         " WHERE COD_PROGRAMA_PRODUCCION_DEVOLUCION_MATERIAL = '"+progProdDevMatCurrent.getCodProgramaProduccionDevolucionMaterial()+"'";
            }
            res.close();
            st.close();
            pst=con.prepareStatement(consulta);
            if(pst.executeUpdate()>0)System.out.println("se cambio el estado a la devolucion de programa produccion");
            pst.close();
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }

    public boolean isAdministradorAlmacen() {
        return administradorAlmacen;
    }

    public void setAdministradorAlmacen(boolean administradorAlmacen) {
        this.administradorAlmacen = administradorAlmacen;
    }
    
    
    
}