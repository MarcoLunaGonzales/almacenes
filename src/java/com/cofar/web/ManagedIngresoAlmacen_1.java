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
import com.cofar.bean.Secciones;
import com.cofar.bean.SolicitudesSalida;
import com.cofar.service.IngresoAlmacenService;
import com.cofar.service.ProveedoresService;
import com.cofar.service.TiposCompraService;
import com.cofar.service.TiposIngresoAlmacenService;
import com.cofar.service.impl.IngresosAlmacenImpl;
import com.cofar.service.impl.ProveedoresImpl;
import com.cofar.service.impl.TiposCompraImpl;
import com.cofar.service.impl.TiposIngresoAlmacenImpl;
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
import org.joda.time.DateTime;
import org.richfaces.component.html.HtmlDataTable;

/**
 *
 * @author hvaldivia
 */

public class ManagedIngresoAlmacen_1 extends ManagedBean {
    
    IngresosAlmacenImpl ingresosAlmacenImpl = new IngresoAlmacenService();
    TiposIngresoAlmacenImpl tiposIngresoAlmacenImpl = new TiposIngresoAlmacenService();
    TiposCompraImpl tiposCompraImpl = new TiposCompraService();
    ProveedoresImpl proveedoresImpl = new ProveedoresService();
    
    List<IngresosAlmacen> ingresosAlmacenList = new ArrayList<IngresosAlmacen>();
    List tiposIngresosAlmacenList = new ArrayList();
    List tiposCompraList = new ArrayList();
    List proveedoresList = new ArrayList();
    ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
    IngresosAlmacen ingresosAlmacen = new IngresosAlmacen();
    List capitulosList = new ArrayList();
    List gruposList = new ArrayList();
    Materiales buscarMaterial = new  Materiales();
    List materialesBuscarList = new ArrayList();
    HtmlDataTable materialesBuscarDataTable = new HtmlDataTable();
    List ingresosAlmacenDetalleList = new ArrayList();
    HtmlDataTable ingresosAlmacenDetalleDataTable = new HtmlDataTable();
    IngresosAlmacenDetalle ingresosAlmacenDetalle = new IngresosAlmacenDetalle();
    IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstado = new IngresosAlmacenDetalleEstado();
    List empaqueSecundarioExternoList = new ArrayList();
    String mensaje = "";
    //inicio alejandro
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
    //final alejandro


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
    

    /** Creates a new instance of ManagedIngresoAlmacen */
    public ManagedIngresoAlmacen_1() {
    }
    public String getCargarIngresosAlmacen(){

       // ingresosAlmacenImpl.listadoIngresosAlmacen(filaInicial, filaFinal);
        try {
            
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
            e.printStackTrace();
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
    public String getCargarAgregarIngresosAlmacen(){
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
            

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public List listadoIngresosAlmacen(int filaInicial,int filaFinal,ManagedAccesoSistema usuario){
        List<IngresosAlmacen> ingresosAlmacenList = new ArrayList<IngresosAlmacen>();
        try {

            Connection con = null;
            con = Util.openConnection(con);

            String consulta = " select * from (select ROW_NUMBER() OVER (ORDER BY ia.NRO_INGRESO_ALMACEN desc,ia.FECHA_INGRESO_ALMACEN desc) as 'FILAS' " +
                    " ,ia.NRO_INGRESO_ALMACEN,ia.COD_INGRESO_ALMACEN,eia.COD_ESTADO_INGRESO_ALMACEN,eia.NOMBRE_ESTADO_INGRESO_ALMACEN, " +
                    " ia.FECHA_INGRESO_ALMACEN,t.COD_TIPO_INGRESO_ALMACEN,t.NOMBRE_TIPO_INGRESO_ALMACEN,oc.NRO_ORDEN_COMPRA,'' almacen_origen_traspaso, " +
                    " pr.COD_PROVEEDOR,pr.NOMBRE_PROVEEDOR,p.COD_PAIS,p.NOMBRE_PAIS,ia.OBS_INGRESO_ALMACEN,per.NOMBRE_PILA,per.AP_PATERNO_PERSONAL,per.AP_MATERNO_PERSONAL,ia.credito_fiscal_si_no,ia.NRO_DOCUMENTO " +
                    " ,ia.COD_ORDEN_COMPRA" +
                    " from INGRESOS_ALMACEN ia " +
                    " left outer join  TIPOS_INGRESO_ALMACEN t on t.COD_TIPO_INGRESO_ALMACEN = ia.COD_TIPO_INGRESO_ALMACEN " +
                    " left outer join proveedores pr on pr.COD_PROVEEDOR = ia.COD_PROVEEDOR " +
                    " left outer join  paises p on pr.COD_PAIS = p.COD_PAIS " +
                    " left outer join ORDENES_COMPRA oc on oc.COD_ORDEN_COMPRA = ia.COD_ORDEN_COMPRA " +
                    " left outer join ESTADOS_INGRESO_ALMACEN eia on eia.COD_ESTADO_INGRESO_ALMACEN = ia.COD_ESTADO_INGRESO_ALMACEN " +
                    " left outer join TIPOS_COMPRA tc on tc.COD_TIPO_COMPRA = ia.COD_TIPO_COMPRA " +
                    " left outer join personal per on per.COD_PERSONAL = ia.COD_PERSONAL " +
                    " left outer join ESTADOS_INGRESOS_LIQUIDACION eil on eil.COD_ESTADO_INGRESO_LIQUIDACION = ia.COD_ESTADO_INGRESO_LIQUIDACION " +
                    //" where ia.COD_GESTION = '"+usuario.getGestionesGlobal().getCodGestion()+"' " +
                    //" and ia.COD_ALMACEN = '"+usuario.getAlmacenesGlobal().getCodAlmacen()+"' ) AS listado where FILAS BETWEEN "+filaInicial+" AND "+filaFinal+"   ";

            //inicio alejandro
                //    " where ia.COD_GESTION = '"+usuario.getGestionesGlobal().getCodGestion()+"' " +
                    "  where ia.COD_ALMACEN = '"+usuario.getAlmacenesGlobal().getCodAlmacen()+"' ";

            if(!nroIngresoAlmacen.equals(""))
            {
                consulta+=" and ia.NRO_INGRESO_ALMACEN = "+nroIngresoAlmacen;
            }
            if(!nroOrdenCompra.equals(""))
            {
                consulta+=" and oc.NRO_ORDEN_COMPRA = "+nroOrdenCompra;
            }
            if(!codTipoIngreso.equals("0"))
            {
                consulta+=" and t.COD_TIPO_INGRESO_ALMACEN = "+codTipoIngreso;
            }
            if(!codEstadoIngreso.equals("0"))
            {
                consulta+="  and eia.COD_ESTADO_INGRESO_ALMACEN ="+codEstadoIngreso;
            }
            if(!codProveedor.equals("0"))
            {
                consulta+=" and pr.COD_PROVEEDOR = "+codProveedor;
            }
            if(!codTipoCompra.equals("0"))
            {
                consulta+=" and tc.COD_TIPO_COMPRA ="+codTipoCompra;
            }
            if(!codGestion.equals("0"))
            {
                consulta+=" and ia.COD_GESTION ="+codGestion;

            }
            SimpleDateFormat sm= new SimpleDateFormat("yyyy/MM/dd");
            if(getFechaInicio()!=null)
            {

                consulta+=" and ia.FECHA_INGRESO_ALMACEN > '"+sm.format(fechaInicio)+" 00:00:00'";
            }
            if(getFechaFinal()!=null)
            {
                consulta+=" and ia.FECHA_INGRESO_ALMACEN <'"+sm.format(fechaFinal)+" 23:59:59' ";
            }
            if(!codCapitulo.equals("0"))
            {
                if(!codGrupo.equals("0"))
                {
                     if(!codMaterial.equals("0"))
                        {
                            consulta+=" and ia.COD_INGRESO_ALMACEN in(select iad.COD_INGRESO_ALMACEN from INGRESOS_ALMACEN_DETALLE iad where iad.COD_MATERIAL ='"+codMaterial+"') ";
                        }
                     else
                     {
                        consulta+=" and ia.COD_INGRESO_ALMACEN in(select iad.COD_INGRESO_ALMACEN from INGRESOS_ALMACEN_DETALLE iad where iad.COD_MATERIAL in(select m.COD_MATERIAL from MATERIALES m where m.COD_GRUPO ='"+codGrupo+"' )) ";
                     }
                        //consulta +=" and oc.cod_orden_compra in (select od.cod_orden_compra from ordenes_compra_detalle od where od.cod_material in (select m.cod_material from materiales m where m.cod_grupo in ("+codGrupo+")))" ;

                }
                else
                {
               consulta+=" and ia.COD_INGRESO_ALMACEN in(select iad.COD_INGRESO_ALMACEN from INGRESOS_ALMACEN_DETALLE iad where iad.COD_MATERIAL in(select m.COD_MATERIAL from MATERIALES m where m.COD_GRUPO IN(select g.COD_GRUPO from GRUPOS g where g.COD_CAPITULO='"+codCapitulo+"') ))";
                }
                //consulta+=" and oc.cod_orden_compra in (select od.cod_orden_compra from ordenes_compra_detalle od where od.cod_material in (select m.cod_material from materiales m where m.cod_grupo in (select c.cod_grupo from grupos c where c.cod_capitulo in ("+codCapitulo+"))))" ;
            }


            consulta+=" ) AS listado where FILAS BETWEEN "+filaInicial+" AND "+filaFinal+"   ";
            System.out.println("codProveedor "+codProveedor);
            //final alejandro
            con = Util.openConnection(con);
            System.out.println("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            ingresosAlmacenList.clear();
            while(rs.next()){
                IngresosAlmacen ingresosAlmacenItem = new IngresosAlmacen();
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
                // inicio alejandro 2
                if(ingresosAlmacenItem.getEstadosIngresoAlmacen().getCodEstadoIngresoAlmacen()==2)
                {System.out.println("color");
                ingresosAlmacenItem.setColorFila("b");
                }
                // final alejandro 2
                ingresosAlmacenList.add(ingresosAlmacenItem);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ingresosAlmacenList;
    }

    public List obtieneProveedores() {
        List proveedoresList = new ArrayList();
        try {

            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select p.COD_PROVEEDOR,p.NOMBRE_PROVEEDOR from proveedores p where p.COD_ESTADO_REGISTRO = 1 ";

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
            e.printStackTrace();
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
                    " and t.cod_tipo_ingreso_almacen<>1 " +
                    " and t.cod_tipo_ingreso_almacen<>4 and t.cod_tipo_ingreso_almacen<>6 and t.cod_tipo_ingreso_almacen<>7 ";

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
        return tiposIngresosAlmacenList;
    }
     public int obtieneNroIngresoAlmacen(ManagedAccesoSistema usuario){
        int nroIngresoAlmacen = 0;
        try {
            Connection con = null;
            con = Util.openConnection(con);

            String consulta= " select (isnull(max(nro_ingreso_almacen),0)+1) nro_ingreso_almacen  from ingresos_almacen where cod_gestion='"+usuario.getGestionesGlobal().getCodGestion()+"' " +
                     " and estado_sistema=1 and cod_almacen='"+usuario.getAlmacenesGlobal().getCodAlmacen()+"'  ";
            System.out.println("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            if(rs.next()){
                nroIngresoAlmacen = rs.getInt("nro_ingreso_almacen");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nroIngresoAlmacen;
    }
     public int obtieneNroIngresoAlmacen(ManagedAccesoSistema usuario,Almacenes almacenes){
        int nroIngresoAlmacen = 0;
        try {
            Connection con = null;
            con = Util.openConnection(con);

            String consulta= " select (isnull(max(nro_ingreso_almacen),0)+1) nro_ingreso_almacen  from ingresos_almacen where cod_gestion='"+usuario.getGestionesGlobal().getCodGestion()+"' " +
                     " and estado_sistema=1 and cod_almacen='"+almacenes.getCodAlmacen()+"'  ";
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            if(rs.next()){
                nroIngresoAlmacen = rs.getInt("nro_ingreso_almacen");
            }
        } catch (Exception e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return tiposIngresosAlmacenList;
    }
    public String proveedores_change(){
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select p.COD_PAIS,p.NOMBRE_PAIS from paises p inner join proveedores pr on pr.COD_PAIS = p.COD_PAIS " +
                              " where pr.COD_PROVEEDOR = '"+ingresosAlmacen.getProveedores().getCodProveedor()+"' ";
            System.out.println("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            if(rs.next()){
                ingresosAlmacen.getProveedores().getPaises().setCodPais(rs.getInt("COD_PAIS"));
                ingresosAlmacen.getProveedores().getPaises().setNombrePais(rs.getString("NOMBRE_PAIS"));
            }
            rs.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
            e.printStackTrace();
        }
        return capitulosList;
    }
      public String capitulos_change(){
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " SELECT GR.COD_GRUPO,GR.NOMBRE_GRUPO FROM GRUPOS GR WHERE GR.COD_CAPITULO = '"+buscarMaterial.getGrupos().getCapitulos().getCodCapitulo()+"' AND GR.COD_ESTADO_REGISTRO = 1 ";
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
        return null;
    }

    public String buscarMaterial_action(){
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select m.COD_MATERIAL,m.NOMBRE_MATERIAL,m.MATERIAL_PRODUCCION,m.MATERIAL_MUESTREO,gr.COD_GRUPO,gr.NOMBRE_GRUPO,cap.NOMBRE_CAPITULO, cap.COD_CAPITULO,cap.NOMBRE_CAPITULO," +
                    " m.COD_UNIDAD_MEDIDA,u1.ABREVIATURA ABREVIATURA1 ,m.COD_UNIDAD_MEDIDA_COMPRA,u2.ABREVIATURA ABREVIATURA2,cantidad_maximamuestreo " +
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
                    consulta = consulta + " and m.MOVIMIENTO_ITEM = 1 and m.COD_ESTADO_REGISTRO = 1 and m.COD_MATERIAL not in ("+this.itemsSeleccionadosIngresoAlmacen()+") ";
            System.out.println("consulta " + consulta);
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
                materialesBuscarList.add(materialesItem);
            }
            rs.close();
            con.close();
            

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


     public String agregarItem_action(){
         try {
             materialesBuscarList.clear();
             buscarMaterial = new Materiales();
         } catch (Exception e) {
             e.printStackTrace();
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
             e.printStackTrace();
         }
         return itemsIngresosAlmacen;

     }


    public String seleccionarMaterial_action(){

        try {
            
            
            // se agrega el detalle de ingreso almacen
            
            Materiales materiales = (Materiales) materialesBuscarDataTable.getRowData();
            IngresosAlmacenDetalle ingresosAlmacenDetalleItem = new IngresosAlmacenDetalle();
            ingresosAlmacenDetalleItem.setMateriales(materiales);
            
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
            e.printStackTrace();
        }
        return null;
    }
    public String guardarDetalleItem_action(){
         try {

             
             
             mensaje = "";
             if(ingresosAlmacenDetalle.getIngresosAlmacenDetalleEstadoList().size()==0){
                 mensaje = "debe registrar el detalle de etiquetas";
                 return null;
             }
             IngresosAlmacenDetalle  ingresosAlmacenDetalleItem = ingresosAlmacenDetalle;
             ingresosAlmacenDetalleItem.setCantTotalIngresoFisico(ingresosAlmacenDetalle.getCantTotalIngresoFisico());
             if(this.validaDatosIngresoAcondicionamiento(ingresosAlmacenDetalleItem)==true){
                 Iterator i= ingresosAlmacenDetalle.getIngresosAlmacenDetalleEstadoList().iterator();
                 ingresosAlmacenDetalleItem.setCantTotalIngresoFisico(0);
                 while(i.hasNext()){
                     IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstadoItem = (IngresosAlmacenDetalleEstado)i.next();
                     ingresosAlmacenDetalleItem.setCantTotalIngresoFisico(ingresosAlmacenDetalleItem.getCantTotalIngresoFisico()+ingresosAlmacenDetalleEstadoItem.getCantidadParcial());
                     ingresosAlmacenDetalleEstadoItem.setCantidadRestante(ingresosAlmacenDetalleEstadoItem.getCantidadParcial());
                 }
             }
             ingresosAlmacenDetalleItem.setCantTotalIngreso(ingresosAlmacenDetalleItem.getCantTotalIngresoFisico());
             //aqui las unidades de medida
         } catch (Exception e) {
             e.printStackTrace();
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
                     if(ingresosAlmacenDetalleEstadoItem.getFechaVencimiento().compareTo(ingresosAlmacenDetalleEstadoItem.getFechaManufactura())<=0){
                         mensaje = "La fecha de Manufactura debe ser menor a la fecha de Vencimiento ";
                         datosValidos=false;
                         break;
                     }
//                     if(fechaVencimiento.toDate().compareTo(ingresosAlmacenDetalleEstadoItem.getFechaVencimiento())>0){
//                        mensaje =" En la fecha de vencimiento debe tener minimamente seis meses de vigencia ";
//                        break;
//                     }
                 }
          } catch (Exception e) {
              e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            System.out.println("consulta " + consulta);
            if(rs.next()){
                secciones.setCodSeccion(rs.getInt("cod_seccion"));
                secciones.setNombreSeccion(rs.getString("nombre_seccion"));
            }
            rs.close();
            st.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
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
            
            System.out.println("consulta" + consulta);
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
            e.printStackTrace();
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

            System.out.println("consulta" + consulta);
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
             System.out.println("consulta "+consulta);
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
            e.printStackTrace();
        }
        return equivalencias;
    }
    public String detallarItem_action(){
         try {
             ingresosAlmacenDetalle = (IngresosAlmacenDetalle)ingresosAlmacenDetalleDataTable.getRowData();
             //aqui las unidades de medida
             ingresosAlmacenDetalleEstado = new IngresosAlmacenDetalleEstado();
             DateTime fechaVencimiento =  new DateTime(ingresosAlmacenDetalleEstado.getFechaManufactura());
             fechaVencimiento = fechaVencimiento.plusMonths(6);
             fechaVencimiento = fechaVencimiento.dayOfMonth().withMaximumValue();
             //ingresosAlmacenDetalleEstado.setFechaVencimiento(fechaVencimiento.toDate());
         } catch (Exception e) {
             e.printStackTrace();
         }
         return null;
     }
    public String repetirValoresIngresoAlmacenDetalleEstado_action(){
         try {
             DateTime fechaVencimiento =  new DateTime(new Date());
             fechaVencimiento = fechaVencimiento.plusMonths(6);
             fechaVencimiento = fechaVencimiento.dayOfMonth().withMaximumValue();
             mensaje = "";
             if(ingresosAlmacenDetalleEstado.getFechaManufactura().compareTo(ingresosAlmacenDetalleEstado.getFechaVencimiento())>=0){
                 mensaje= " La fecha de vencimiento debe ser mayor a la fecha de Manufactura";
                 return null;
             }
//             if(fechaVencimiento.toDate().compareTo(ingresosAlmacenDetalleEstado.getFechaVencimiento())>0){
//                 mensaje =" En la fecha de vencimiento debe tener minimamente seis meses de vigencia ";
//                 return null;
//             }
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
                 ingresosAlmacenDetalleEstadoItem.setTara(ingresosAlmacenDetalleEstado.getTara());
                 ingresosAlmacenDetalle.setCantTotalIngresoFisico(ingresosAlmacenDetalle.getCantTotalIngresoFisico()+ingresosAlmacenDetalleEstadoItem.getCantidadParcial());
             }
         } catch (Exception e) {
             e.printStackTrace();
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
             e.printStackTrace();
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
            e.printStackTrace();
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
             e.printStackTrace();
         }
         return nombreEmpaqueSecundario;
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
    public int generaCodIngresoAlmacen(){
         int codIngresoAlmacen = 0;
         try {
             Connection con = null;
             con = Util.openConnection(con);
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
    public boolean existeDetalleIngresoDetalleAlmacen(List ingresosAlmacenDetalleList){
        boolean existeDetalle = true;
        try {
            Iterator i = ingresosAlmacenDetalleList.iterator();
            while(i.hasNext()){
                IngresosAlmacenDetalle ingresosAlmacenDetalleItem = (IngresosAlmacenDetalle)i.next();
                if(ingresosAlmacenDetalleItem.getIngresosAlmacenDetalleEstadoList().size()==0){
                    existeDetalle = false;
                    break;
                }
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return existeDetalle;
    }

    public String guardarIngresoAlmacen_action(){
         try {
             mensaje = "";
             Connection con = null;
             con = Util.openConnection(con);
             SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
             if(this.verificaTransaccionCerradaAlmacen()==true){
                 mensaje = "Las transacciones para este mes fueron cerradas ";
                 return null;
             }
             if(ingresosAlmacenDetalleList.size()==0){
                 mensaje = " No existe detalle de ingreso ";
                 return null;
             }
             if(existeDetalleIngresoDetalleAlmacen(ingresosAlmacenDetalleList)==false){
                 mensaje = " No existe detalle de Cada Item ";
                 return null;
             }
             // inicio alejandro 3
               Iterator j = ingresosAlmacenDetalleList.iterator();
               while(j.hasNext()){
                   IngresosAlmacenDetalle ingresosAlmacenDetalleItem = (IngresosAlmacenDetalle)j.next();
                   Iterator iterator = ingresosAlmacenDetalleItem.getIngresosAlmacenDetalleEstadoList().iterator();
                   while(iterator.hasNext()){
                         IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstadoItem = (IngresosAlmacenDetalleEstado)iterator.next();
                       if(ingresosAlmacenDetalleEstadoItem.getCantidadParcial()<=0)
                       {
                           mensaje="todos los detalles tienen que ser mayores que 0";
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

             String consulta = " insert into ingresos_almacen (cod_ingreso_almacen,nro_ingreso_almacen,cod_tipo_ingreso_almacen, " +
                     " cod_orden_compra,cod_gestion,cod_estado_ingreso_almacen, " +
                     " credito_fiscal_si_no,cod_proveedor, " +
                     " cod_tipo_compra,estado_sistema,cod_almacen,cod_devolucion,fecha_ingreso_almacen, " +
                     " cod_tipo_documento,cod_personal,cod_estado_ingreso_liquidacion,obs_ingreso_almacen,NRO_DOCUMENTO)  " +
                     " values  ('"+ingresosAlmacen.getCodIngresoAlmacen()+"'," +
                     " (select (isnull(max(nro_ingreso_almacen),0)+1) nro_ingreso_almacen  from ingresos_almacen where cod_gestion='"+usuario.getGestionesGlobal().getCodGestion()+"' " +
                     " and estado_sistema=1 and cod_almacen='"+usuario.getAlmacenesGlobal().getCodAlmacen()+"'), " +
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
                     " '"+ingresosAlmacen.getEstadosIngresosLiquidacion().getCodEstadoIngresoLiquidacion()+"','"+ingresosAlmacen.getObsIngresoAlmacen()+"','"+ingresosAlmacen.getNroDocumento()+"')  ";
               System.out.println("consulta " + consulta);
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
                           " fecha_reanalisis,observaciones,obs_control_calidad,tara,con_fecha_vencimiento,con_fecha_reanalisis)values(" +
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
                           " '"+ingresosAlmacenDetalleEstadoItem.getObsControlCalidad()+"'," +
                           " '"+ingresosAlmacenDetalleEstadoItem.getTara()+"','"+ingresosAlmacenDetalleEstadoItem.getConFechaVencimiento()+"','"+ingresosAlmacenDetalleEstadoItem.getConFechaReanalisis()+"')  ";
                      // this.actualizaProgramaProduccion(ingresosAlmacenDetalleEstadoItem,ingresosAlmacenDetalleItem);


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
               if(materialMuestreo(ingresosAlmacenDetalleList)==true &&(ingresosAlmacen.getTiposIngresoAlmacen().getCodTipoIngresoAlmacen()!=8)){
                   SolicitudesSalida solicitudesSalida = new SolicitudesSalida();
                   solicitudesSalida.getSolicitante().setCodPersonal("303");
                   solicitudesSalida.getAreaDestinoSalida().setCodAreaEmpresa("40");
                   ManagedRegistroSolicitudSalidaAlmacen managedRegistroSolicitudSalidaAlmacen = new ManagedRegistroSolicitudSalidaAlmacen();
                   ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
                    sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    solicitudesSalida.setCodFormSalida(managedRegistroSolicitudSalidaAlmacen.generaCodSolicitudSalidaAlmacen());
                    consulta = " INSERT INTO SOLICITUDES_SALIDA(  COD_GESTION,  COD_FORM_SALIDA,  COD_TIPO_SALIDA_ALMACEN,  COD_ESTADO_SOLICITUD_SALIDA_ALMACEN," +
                            "  SOLICITANTE,  AREA_DESTINO_SALIDA,  FECHA_SOLICITUD,  COD_LOTE_PRODUCCION,  OBS_SOLICITUD,  ESTADO_SISTEMA," +
                            "  CONTROL_CALIDAD,  COD_INGRESO_ALMACEN,  COD_ALMACEN,  orden_trabajo,cod_compprod,cod_presentacion) " +
                            "VALUES (  '"+usuario.getGestionesGlobal().getCodGestion()+"','"+solicitudesSalida.getCodFormSalida()+"',  2,  1," +
                            "  '"+solicitudesSalida.getSolicitante().getCodPersonal()+"',  '"+solicitudesSalida.getAreaDestinoSalida().getCodAreaEmpresa()+"', '"+sdf.format(new Date())+"', '"+solicitudesSalida.getCodLoteProduccion()+"', '"+solicitudesSalida.getObsSolicitud()+"', 1," +
                            "  0,  0, '"+usuario.getAlmacenesGlobal().getCodAlmacen()+"' , '"+solicitudesSalida.getOrdenTrabajo()+"','"+solicitudesSalida.getComponentesProd().getCodCompprod()+"','"+solicitudesSalida.getPresentacionesProducto().getCodPresentacion()+"'); ";
                    System.out.println("consulta "+consulta);
                    st.executeUpdate(consulta);
                    i = ingresosAlmacenDetalleList.iterator();

                    while(i.hasNext()){
                        IngresosAlmacenDetalle ingresosAlmacenDetalle = (IngresosAlmacenDetalle) i.next();
                        if(ingresosAlmacenDetalle.getMateriales().getMaterialMuestreo()>0){
                        consulta = " INSERT INTO SOLICITUDES_SALIDA_DETALLE(  COD_FORM_SALIDA,  COD_MATERIAL,  CANTIDAD,  CANTIDAD_ENTREGADA," +
                                "  COD_UNIDAD_MEDIDA) VALUES (  '"+solicitudesSalida.getCodFormSalida()+"', '"+ingresosAlmacenDetalle.getMateriales().getCodMaterial()+"',  '"+ingresosAlmacenDetalle.getMateriales().getCantidadMaximaMuestreo()*ingresosAlmacenDetalle.getNroUnidadesEmpaque()+"',  0,'"+ingresosAlmacenDetalle.getUnidadesMedida().getCodUnidadMedida()+"'); ";
                        System.out.println("consulta " + consulta);
                        st.executeUpdate(consulta);
                        Iterator i1 = ingresosAlmacenDetalle.getIngresosAlmacenDetalleEstadoList().iterator();
                        while(i1.hasNext()){
                            IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstado = (IngresosAlmacenDetalleEstado)i1.next();
                            consulta = "  INSERT INTO SOLICITUDES_SALIDA_DETALLE_SOLICITUD(  COD_FORM_SALIDA,  COD_INGRESO_ALMACEN,  COD_MATERIAL," +
                                    "  ETIQUETA,  CANTIDAD) VALUES (  '"+solicitudesSalida.getCodFormSalida()+"','"+ingresosAlmacen.getCodIngresoAlmacen()+"' ,'"+ingresosAlmacenDetalle.getMateriales().getCodMaterial()+"'," +
                                    "'"+ingresosAlmacenDetalleEstado.getEtiqueta()+"', '"+ingresosAlmacenDetalle.getMateriales().getCantidadMaximaMuestreo()+"'); ";
                            System.out.println("consulta " + consulta);
                            st.executeUpdate(consulta);
                        }
                        }
                   }
               }
               st.close();
               con.close();
               this.notificaMaterialMantenimiento(ingresosAlmacenDetalleList);
         } catch (Exception e) {
             e.printStackTrace();
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
            System.out.println("consulta " + consulta);
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
            e.printStackTrace();
        }
    }
    
   /* public void actualizaProgramaProduccion(IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstado,IngresosAlmacenDetalle ingresosAlmacenDetalle){
        try {
            if(ingresosAlmacenDetalle.getMateriales().getGrupos().getCodGrupo()==99){ //grupo de materiales semiprocesadas
            String consulta = "update programa_produccion  set cod_estado_programa = '6' where cod_lote_produccion = '"+ingresosAlmacenDetalleEstado.getLoteMaterialProveedor()+"'";
            System.out.println("consulta " + consulta);
            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement();
            st.executeUpdate(consulta);
            st.close();
            con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
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
            e.printStackTrace();
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
            Iterator i = ingresosAlmacenList.iterator();
            while(i.hasNext()){
                ingresosAlmacen = (IngresosAlmacen) i.next();
                if(ingresosAlmacen.getChecked().booleanValue()==true){
                    break;
                }
            }
            mensaje = "";
            if(this.tieneSalidasAlmacen()==true){
                 mensaje = "El ingreso no se puede modificar por que tiene salidas registradas ";
                 return null;
             }
             if(ingresosAlmacen.getTiposIngresoAlmacen().getCodTipoIngresoAlmacen()==6){
                 mensaje = "no se puede modificar un ingreso por devolucion";
                 return null;
             }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public String getCargarEditarIngresosAlmacen(){
        try {
            //ingresosAlmacen.setGestiones(usuario.getGestionesGlobal());
            //ingresosAlmacen.setFechaIngresoAlmacen(new Date());
            //ingresosAlmacen.setNroIngresoAlmacen(obtieneNroIngresoAlmacen(usuario));
            //inicio alejandro 3
            ingresosAlmacenDetalleEstado= new IngresosAlmacenDetalleEstado();
            // final alejandro 3
            tiposIngresosAlmacenList = cargarTiposIngresoAlmacenEditar();
            //ingresosAlmacen.getEstadosIngresoAlmacen().setCodEstadoIngresoAlmacen(1);
            tiposCompraList = cargarTiposCompra();
            proveedoresList = obtieneProveedores();
            capitulosList = this.cargarCapitulos();
            gruposList.add(new SelectItem("0", "-NINGUNO-"));
            this.cargarEmpaques();

            ingresosAlmacenDetalleList.clear();
            ingresosAlmacenDetalleList = this.cargarIngresosAlmacenDetalle(ingresosAlmacen);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public List cargarIngresosAlmacenDetalle(IngresosAlmacen ingresosAlmacen){
        List ingresosAlmacenDetalleList = new ArrayList();
        try {
            Connection con  = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement();
            String consulta = " select iad.COD_INGRESO_ALMACEN,m.COD_MATERIAL,m.NOMBRE_MATERIAL,s.COD_SECCION,s.NOMBRE_SECCION,iad.NRO_UNIDADES_EMPAQUE, " +
                    " iad.CANT_TOTAL_INGRESO,iad.CANT_TOTAL_INGRESO_FISICO,um.COD_UNIDAD_MEDIDA,um.NOMBRE_UNIDAD_MEDIDA,iad.PRECIO_TOTAL_MATERIAL, " +
                    " iad.PRECIO_UNITARIO_MATERIAL,iad.COSTO_UNITARIO,iad.observacion,iad.COSTO_PROMEDIO,iad.PRECIO_NETO  " +
                    " from INGRESOS_ALMACEN_DETALLE iad inner join materiales m on m.COD_MATERIAL = iad.COD_MATERIAL " +
                    " inner join SECCIONES s on s.COD_SECCION = iad.COD_SECCION " +
                    " inner join UNIDADES_MEDIDA um on um.COD_UNIDAD_MEDIDA = iad.COD_UNIDAD_MEDIDA " +
                    " where iad.COD_INGRESO_ALMACEN = '"+ingresosAlmacen.getCodIngresoAlmacen()+"' ";
            System.out.println("consulta " + consulta);
            ResultSet rs = st.executeQuery(consulta);
            ingresosAlmacenDetalleList.clear();
            while(rs.next()){
                IngresosAlmacenDetalle ingresosAlmacenDetalle = new  IngresosAlmacenDetalle();
                ingresosAlmacenDetalle.getIngresosAlmacen().setCodIngresoAlmacen(rs.getInt("COD_INGRESO_ALMACEN"));
                ingresosAlmacenDetalle.getMateriales().setCodMaterial(rs.getString("COD_MATERIAL"));
                ingresosAlmacenDetalle.getMateriales().setNombreMaterial(rs.getString("NOMBRE_MATERIAL"));
                ingresosAlmacenDetalle.getSecciones().setCodSeccion(rs.getInt("COD_SECCION"));
                ingresosAlmacenDetalle.getSecciones().setNombreSeccion(rs.getString("NOMBRE_SECCION"));
                ingresosAlmacenDetalle.setNroUnidadesEmpaque(rs.getInt("NRO_UNIDADES_EMPAQUE"));
                ingresosAlmacenDetalle.setCantTotalIngreso(rs.getFloat("CANT_TOTAL_INGRESO"));
                ingresosAlmacenDetalle.setCantTotalIngresoFisico(rs.getFloat("CANT_TOTAL_INGRESO_FISICO"));
                ingresosAlmacenDetalle.getUnidadesMedida().setCodUnidadMedida(rs.getInt("COD_UNIDAD_MEDIDA"));
                ingresosAlmacenDetalle.getUnidadesMedida().setNombreUnidadMedida(rs.getString("NOMBRE_UNIDAD_MEDIDA"));
                ingresosAlmacenDetalle.setPrecioTotalMaterial(rs.getFloat("PRECIO_TOTAL_MATERIAL"));
                ingresosAlmacenDetalle.setPrecioUnitarioMaterial(rs.getFloat("PRECIO_UNITARIO_MATERIAL"));
                ingresosAlmacenDetalle.setCostoUnitario(rs.getFloat("COSTO_UNITARIO"));
                ingresosAlmacenDetalle.setObservacion(rs.getString("observacion"));
                ingresosAlmacenDetalle.setCostoPromedio(rs.getFloat("COSTO_PROMEDIO"));
                ingresosAlmacenDetalle.setPrecioNeto(rs.getFloat("PRECIO_NETO"));
                ingresosAlmacenDetalle.setIngresosAlmacenDetalleEstadoList(this.cargarIngresosAlmacenDetalleEstado(ingresosAlmacenDetalle));
                ingresosAlmacenDetalleList.add(ingresosAlmacenDetalle);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ingresosAlmacenDetalleList;
    }
    public List cargarTiposIngresoAlmacenEditar() {
        List tiposIngresosAlmacenList = new ArrayList();
        try {

            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select t.COD_TIPO_INGRESO_ALMACEN,t.NOMBRE_TIPO_INGRESO_ALMACEN " +
                    " from TIPOS_INGRESO_ALMACEN t where t.COD_ESTADO_REGISTRO = 1 ";

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
        return tiposIngresosAlmacenList;
    }
    public List cargarIngresosAlmacenDetalleEstado(IngresosAlmacenDetalle ingresosAlmacenDetalle){
        List ingresosAlmacenDetalleEstadoList = new ArrayList();
        try {
            Connection con  = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement();
            String consulta = " select iad.COD_INGRESO_ALMACEN,m.COD_MATERIAL,m.NOMBRE_MATERIAL,s.COD_SECCION,s.NOMBRE_SECCION,iad.NRO_UNIDADES_EMPAQUE, " +
                    " iad.CANT_TOTAL_INGRESO,iad.CANT_TOTAL_INGRESO_FISICO,um.COD_UNIDAD_MEDIDA,um.NOMBRE_UNIDAD_MEDIDA,iad.PRECIO_TOTAL_MATERIAL, " +
                    " iad.PRECIO_UNITARIO_MATERIAL,iad.COSTO_UNITARIO,iad.observacion,iad.COSTO_PROMEDIO,iad.PRECIO_NETO  " +
                    " from INGRESOS_ALMACEN_DETALLE iad inner join materiales m on m.COD_MATERIAL = iad.COD_MATERIAL " +
                    " inner join SECCIONES s on s.COD_SECCION = iad.COD_SECCION " +
                    " inner join UNIDADES_MEDIDA um on um.COD_UNIDAD_MEDIDA = iad.COD_UNIDAD_MEDIDA " +
                    " where iad.COD_INGRESO_ALMACEN = '"+ingresosAlmacen.getCodIngresoAlmacen()+"' ";
            consulta = " select iade.COD_INGRESO_ALMACEN,iade.ETIQUETA,e.COD_ESTADO_MATERIAL,e.NOMBRE_ESTADO_MATERIAL,ese.COD_EMPAQUE_SECUNDARIO_EXTERNO,ese.NOMBRE_EMPAQUE_SECUNDARIO_EXTERNO, " +
                    " iade.CANTIDAD_PARCIAL,iade.CANTIDAD_RESTANTE,iade.FECHA_VENCIMIENTO,iade.LOTE_MATERIAL_PROVEEDOR,iade.LOTE_INTERNO,iade.FECHA_MANUFACTURA,iade.FECHA_REANALISIS,iade.OBSERVACIONES,iade.OBS_CONTROL_CALIDAD " +
                    " from INGRESOS_ALMACEN_DETALLE_ESTADO iade " +
                    " inner join ESTADOS_MATERIAL e on e.COD_ESTADO_MATERIAL = iade.COD_ESTADO_MATERIAL " +
                    " inner join EMPAQUES_SECUNDARIO_EXTERNO ese on ese.COD_EMPAQUE_SECUNDARIO_EXTERNO = iade.COD_EMPAQUE_SECUNDARIO_EXTERNO " +
                    " where iade.COD_INGRESO_ALMACEN = '"+ingresosAlmacenDetalle.getIngresosAlmacen().getCodIngresoAlmacen()+"' " +
                    " and iade.COD_MATERIAL = '"+ingresosAlmacenDetalle.getMateriales().getCodMaterial()+"' ";
            System.out.println("consulta " + consulta);
            ResultSet rs = st.executeQuery(consulta);
            ingresosAlmacenDetalleEstadoList.clear();
            while(rs.next()){
                IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstado = new IngresosAlmacenDetalleEstado();
                ingresosAlmacenDetalleEstado.getIngresosAlmacen().setCodIngresoAlmacen(rs.getInt("COD_INGRESO_ALMACEN"));
                ingresosAlmacenDetalleEstado.setEtiqueta(rs.getInt("ETIQUETA"));
                ingresosAlmacenDetalleEstado.getEstadosMaterial().setCodEstadoMaterial(rs.getInt("COD_ESTADO_MATERIAL"));
                ingresosAlmacenDetalleEstado.getEstadosMaterial().setNombreEstadoMaterial(rs.getString("NOMBRE_ESTADO_MATERIAL"));
                ingresosAlmacenDetalleEstado.getEmpaqueSecundarioExterno().setCodEmpaqueSecundarioExterno(rs.getInt("COD_EMPAQUE_SECUNDARIO_EXTERNO"));
                ingresosAlmacenDetalleEstado.getEmpaqueSecundarioExterno().setNombreEmpaqueSecundarioExterno(rs.getString("NOMBRE_EMPAQUE_SECUNDARIO_EXTERNO"));
                ingresosAlmacenDetalleEstado.setCantidadParcial(rs.getFloat("CANTIDAD_PARCIAL"));
                ingresosAlmacenDetalleEstado.setCantidadRestante(rs.getFloat("CANTIDAD_RESTANTE"));
                ingresosAlmacenDetalleEstado.setFechaVencimiento(rs.getDate("FECHA_VENCIMIENTO"));
                ingresosAlmacenDetalleEstado.setLoteMaterialProveedor(rs.getString("LOTE_MATERIAL_PROVEEDOR"));
                ingresosAlmacenDetalleEstado.setLoteInterno(rs.getString("LOTE_INTERNO"));
                ingresosAlmacenDetalleEstado.setFechaManufactura(rs.getDate("FECHA_MANUFACTURA"));
                ingresosAlmacenDetalleEstado.setFechaReanalisis(rs.getDate("FECHA_REANALISIS"));
                ingresosAlmacenDetalleEstado.setObservaciones(rs.getString("OBSERVACIONES"));
                ingresosAlmacenDetalleEstado.setObsControlCalidad(rs.getInt("OBS_CONTROL_CALIDAD"));
                ingresosAlmacenDetalleEstadoList.add(ingresosAlmacenDetalleEstado);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ingresosAlmacenDetalleEstadoList;
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
             e.printStackTrace();
         }
         return tieneSalidasAlmacen ;

     }
    public String guardarEditarIngresoAlmacen_action()throws SQLException{
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
             if(this.verificaTransaccionCerradaAlmacen()==true){
                 mensaje = "Las transacciones para este mes fueron cerradas ";
                 return null;
             }
             if(ingresosAlmacenDetalleList.size()==0){
                 mensaje = " No existe detalle de ingreso ";
                 return null;
             }
             if(existeDetalleIngresoDetalleAlmacen(ingresosAlmacenDetalleList)==false){
                 mensaje = " No existe detalle de Cada Item ";
                 return null;
             }
             if(this.tieneSalidasAlmacen()==true){
                 mensaje = "El ingreso no se puede modificar por que tiene salidas registradas ";
                 return null;
             }
             if(ingresosAlmacen.getTiposIngresoAlmacen().getCodTipoIngresoAlmacen()==6){
                 mensaje = "no se puede modificar un ingreso por devolucion";
                 return null;
             }
             //inicio ale orden de compra
             String consulta="select iad.COD_UNIDAD_MEDIDA,iad.CANT_TOTAL_INGRESO_FISICO,iad.COD_UNIDAD_MEDIDA,iad.COD_MATERIAL,"+
                             " ocd.COD_UNIDAD_MEDIDA as codUnidadOC,ocd.CANTIDAD_INGRESO_ALMACEN"+
                             " from INGRESOS_ALMACEN_DETALLE iad inner join ORDENES_COMPRA_DETALLE ocd on "+
                             " iad.COD_MATERIAL=ocd.COD_MATERIAL"+
                             " where iad.COD_INGRESO_ALMACEN='"+ingresosAlmacen.getCodIngresoAlmacen()+"'" +
                             " and ocd.COD_ORDEN_COMPRA='"+ingresosAlmacen.getOrdenesCompra().getCodOrdenCompra()+"'";
             System.out.println("consulta buscar ordenes  ingresos oc a "+consulta);
             Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
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
                     System.out.println("consulta equi de IA a OC "+consulta);
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
                         System.out.println("consulta equi de Oc a Ia "+consulta);
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
                 System.out.println("consulta  update "+consulta);
                 pst=con.prepareStatement(consulta);
                 if(pst.executeUpdate()>0)System.out.println("se decremento la cantidad de ingresos orden de compra");

             }
             resDetalle.close();
             stDetalle.close();
            //final ale orden de compra

            //st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            consulta = " UPDATE INGRESOS_ALMACEN  SET CREDITO_FISCAL_SI_NO = '"+ingresosAlmacen.getCreditoFiscalSiNo()+"',obs_ingreso_almacen = '"+ingresosAlmacen.getObsIngresoAlmacen()+"',NRO_DOCUMENTO='"+ingresosAlmacen.getNroDocumento()+"'" +
                    " where COD_INGRESO_ALMACEN = '"+ingresosAlmacen.getCodIngresoAlmacen()+"'";
  
            System.out.println("consulta " + consulta);
            st.executeUpdate(consulta);
             

             consulta = "";
             consulta = " DELETE FROM INGRESOS_ALMACEN_DETALLE WHERE COD_INGRESO_ALMACEN = '"+ingresosAlmacen.getCodIngresoAlmacen()+"' ";
             System.out.println("consulta " + consulta);
             st.executeUpdate(consulta);
             consulta = " DELETE FROM INGRESOS_ALMACEN_DETALLE_ESTADO WHERE COD_INGRESO_ALMACEN = '"+ingresosAlmacen.getCodIngresoAlmacen()+"' ";
             System.out.println("consulta " + consulta);
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
                           st.executeUpdate(consulta);
                        

                       Iterator i3 = ingresosAlmacenDetalleItem.getIngresosAlmacenDetalleEstadoList().iterator();


                       while(i3.hasNext()){
                           IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstadoItem = (IngresosAlmacenDetalleEstado) i3.next();
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
                           System.out.println("consulta " + consulta);
                           st.executeUpdate(consulta);
                           
                       }
                   }
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
                             System.out.println("consulta equi de IA a OC "+consulta);
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
                                 System.out.println("consulta equi de Oc a Ia "+consulta);
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
                        System.out.println("consulta update"+consulta);
                        pst=con.prepareStatement(consulta);
                        if(pst.executeUpdate()>0)System.out.println("se incremento la cantidad de ingreso almacen de la orden de compra");
                   }
                   consulta=" select ocd.CANTIDAD_INGRESO_ALMACEN,ocd.CANTIDAD_NETA" +
                            " from ORDENES_COMPRA_DETALLE ocd where ocd.COD_ORDEN_COMPRA='"+ingresosAlmacen.getOrdenesCompra().getCodOrdenCompra()+"'";
                   System.out.println("consulta detalle OC "+consulta);
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
                   System.out.println("consulta update estado OC "+consulta);
                   pst=con.prepareStatement(consulta);
                   if(pst.executeUpdate()>0)System.out.println("se actualizo el estado de la OC");
                   res.close();
                   //final ale orden compra
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
            System.out.println("consulta " + consulta);
            st.executeUpdate(consulta);
            ingresosAlmacenList = listadoIngresosAlmacen(begin, end,usuario);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public String cancelarDetalleItem_action(){
         try {
             ingresosAlmacenDetalle.getIngresosAlmacenDetalleEstadoList().clear();

         } catch (Exception e) {
             e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return null;
    }
    public String cargarGrupos(){
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " SELECT GR.COD_GRUPO,GR.NOMBRE_GRUPO FROM GRUPOS GR WHERE GR.COD_CAPITULO = '"+getCodCapitulo()+"' AND GR.COD_ESTADO_REGISTRO = 1 ";
            System.out.println("consulta " + consulta);
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
            e.printStackTrace();
        }
        return null;
    }

     public void cargarProveedores(){
         Connection con=null;
        try {

            String consulta = "select m.cod_proveedor, m.nombre_proveedor from proveedores m where m.cod_estado_registro=1 order by m.nombre_proveedor " ;
            System.out.println("consulta " + consulta);
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
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }
    public String  cargarMateriales(){
        Connection con=null;
        try {

            String consulta = "select m.COD_MATERIAL,m.NOMBRE_MATERIAL from MATERIALES m  where m.COD_ESTADO_REGISTRO=1 and m.COD_GRUPO='"+codGrupo+"' order by m.NOMBRE_MATERIAL " ;
            System.out.println("consulta " + consulta);



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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            System.out.println("consulta "+consulta);

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
            ex.printStackTrace();

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
    public boolean  verificarTranasaccionCerrada(IngresosAlmacen ingreso)
    {
         boolean transaccionCerradaAlmacen = false;

         SimpleDateFormat sdf = new SimpleDateFormat("MM");
         try {
             Connection con = null;
             con = Util.openConnection(con);
             String consulta = " select * from estados_transacciones_almacen " +
                     " where cod_gestion='"+ingreso.getGestiones().getCodGestion()+"' " +
                     " and cod_mes = '"+Integer.valueOf(sdf.format(ingreso.getFechaIngresoAlmacen()))+"'  " +
                     " and estado=1 ";
             Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             ResultSet rs = st.executeQuery(consulta);
             if(rs.next()){
                 transaccionCerradaAlmacen= true;
             }
             rs.close();
             st.close();
             con.close();

         } catch (Exception e) {
             e.printStackTrace();
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
            e.printStackTrace();
        }
        return devolucionAnulada;
    }
    public String anularIngresosAlmacen_action(){
        try {
            IngresosAlmacen ingresosAlmacen = new IngresosAlmacen();
            Iterator i = ingresosAlmacenList.iterator();
            while(i.hasNext()){
                ingresosAlmacen = (IngresosAlmacen)i.next();
                if(ingresosAlmacen.getChecked().booleanValue()==true){
                    break;
                }
            }
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
            System.out.println("consulta " + consulta);
            st.executeUpdate(consulta);
            consulta="select ia.COD_ORDEN_COMPRA from INGRESOS_ALMACEN  ia where ia.COD_INGRESO_ALMACEN='"+ingresosAlmacen.getCodIngresoAlmacen()+"'";
            System.out.println("consulta "+consulta);
            ResultSet result=st.executeQuery(consulta);
            int codOrden=0;
            while(result.next())
            {
                codOrden=result.getInt("COD_ORDEN_COMPRA");
            }

            if(codOrden>0)
            {
               List listaIngresos= this.cargarIngresosAlmacenDetalle(ingresosAlmacen);
               Iterator j=listaIngresos.iterator();
               while(j.hasNext())
               {

                   IngresosAlmacenDetalle current=(IngresosAlmacenDetalle)j.next();
                   float cantTotalIngreso=current.getCantTotalIngresoFisico();
                   consulta="select ocd.COD_UNIDAD_MEDIDA,ocd.CANTIDAD_INGRESO_ALMACEN from ordenes_compra_detalle ocd" +
                           " where ocd.cod_orden_compra='"+codOrden+"'and ocd.cod_material='"+current.getMateriales().getCodMaterial()+"'";
                   System.out.println("consulta "+consulta);
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
                       System.out.println("consulta "+consulta);
                       result=st.executeQuery(consulta);
                       result.next();
                       consulta="select count( distinct cod_tipo_medida) as cantidad from UNIDADES_MEDIDA where cod_unidad_medida in('"+current.getUnidadesMedida().getCodUnidadMedida()+"','"+codUnidad2+"')";
                       System.out.println("consulta "+consulta);
                       Statement st2=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                       ResultSet result2=st.executeQuery(consulta);
                       result2.next();
                       if((result2.getInt("cantidad")==2) &&(result.getFloat("PESO_ASOCIADO")>0) )
                       {
                           if(codUnidad2!=result.getInt("COD_UNIDAD_MEDIDA_PESOASOCIADO"))
                           {
                             consulta="select cod_unidad_medida,cod_unidad_medida2,valor_equivalencia from equivalencias"+
                                         "where cod_unidad_medida='"+current.getUnidadesMedida().getCodUnidadMedida()+"'and cod_unidad_medida2='"+codUnidad2+"'and cod_estado_registro=1";
                             System.out.println("consulta "+consulta);
                             Statement st3=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                            ResultSet result3=st3.executeQuery(consulta);
                            float valorEquivalencia=0;
                                 if(!result3.next())
                                 {
                                      consulta="select cod_unidad_medida,cod_unidad_medida2,valor_equivalencia from equivalencias"+
                                          "where cod_unidad_medida='"+codUnidad2+"'and cod_unidad_medida2='"+current.getUnidadesMedida().getCodUnidadMedida()+"'and cod_estado_registro=1";
                                     System.out.println("consulta 2 "+consulta);
                                        result3=st3.executeQuery(consulta);
                                     if(result3.next())
                                      {
                                         valorEquivalencia=((1/result3.getFloat("valor_equivalencia"))*result.getFloat("PESO_ASOCIADO"));
                                      cantTotalIngreso=cantTotalIngreso*valorEquivalencia;
                                     }
                                 }
                                else
                                {
                                 valorEquivalencia=(result3.getFloat("valor_equivalencia")*result.getFloat("PESO_ASOCIADO"));
                                  cantTotalIngreso=cantTotalIngreso*valorEquivalencia;

                                }
                            result3.close();
                            st3.close();

                           }

                       }
                       else
                       {
                           consulta="select valor_equivalencia from equivalencias" +
                                   " where cod_unidad_medida='"+current.getUnidadesMedida().getCodUnidadMedida()+"'and cod_unidad_medida2='"+codUnidad2+"'";
                           System.out.println("consulta "+consulta);
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
                                 System.out.println("consulta2 "+consulta);
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
                           "where cod_orden_compra='"+codOrden+"' and cod_material='"+current.getMateriales().getCodMaterial()+"'";
                   System.out.println("consulta "+consulta);
                   PreparedStatement pst=con.prepareStatement(consulta);
                   if(pst.executeUpdate()>0)System.out.println("se actualizo");

               }
               consulta="select ocd.CANTIDAD_INGRESO_ALMACEN from ordenes_compra_detalle ocd where ocd.cod_orden_compra='"+codOrden+"'";
               System.out.println("consulta "+consulta);
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
               consulta+="where cod_orden_compra='"+codOrden+"'";
               System.out.println("consulta "+consulta);
               PreparedStatement pst=con.prepareStatement(consulta);
               if(pst.executeUpdate()>0)System.out.println("se actualizo");
            }
            ingresosAlmacenList = listadoIngresosAlmacen(begin, end,usuario);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
}
