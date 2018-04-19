/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofar.web;

import com.cofar.bean.Almacenes;
import com.cofar.bean.IngresosAlmacen;
import com.cofar.bean.IngresosAlmacenDetalle;
import com.cofar.bean.IngresosAlmacenDetalleEstado;
import com.cofar.bean.IngresosAlmacenDetalleEstadoFechaVencimiento;
import com.cofar.util.Util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
public class ManagedEstadosMaterial extends ManagedBean {

    // <editor-fold defaultstate="collapsed" desc="incremento de vida util">
        private List<Almacenes> almacenesExistenciaList;
        private List<IngresosAlmacenDetalleEstado> ingresosAlmacenDetalleEstadoVencimientoList;
        private IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstadoAprobarReanalisis;
        private IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstadoVencimientoBuscar = new IngresosAlmacenDetalleEstado();
        private int inicioVencimientoList = 0;
        private int finalVencimientoList = 20;
        private int cantidadRegistrosVencimientoList = 20;
    //</editor-fold>
    /**
     * Creates a new instance of ManagedEstadosMaterial
     */
    private int cantidadCuarentenaLoteProveedor = 0;
    public static Logger LOGGER;
    private static final int COD_ESTADO_MATERIAL_APROBADO_REANALISIS = 9;
    private List<IngresosAlmacen> ingresosAlmacenList = new ArrayList<IngresosAlmacen>();
    private List<SelectItem> estadosMaterialCambioSelectList = new ArrayList<SelectItem>();
    private ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
    private IngresosAlmacen ingresosAlmacen = new IngresosAlmacen();
    private List<IngresosAlmacenDetalle> ingresosAlmacenDetalleList = new ArrayList<IngresosAlmacenDetalle>();
    private List<IngresosAlmacenDetalleEstado> IngresosAlmacenDetalleEstadoList = new ArrayList<IngresosAlmacenDetalleEstado>();
    private IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstado = new IngresosAlmacenDetalleEstado();
    private List estadosMaterialList = new ArrayList();
    private int codEstadoMaterial = 0;
    public IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstadoBuscar = new IngresosAlmacenDetalleEstado();
    private List capitulosList = new ArrayList();
    private Date fechaInicial = new Date();
    private Date fechaFinal = new Date();
    private List gruposList = new ArrayList();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    private String mensaje = "";
    private HtmlDataTable ingresosAlmacenDetalleEstadoDataTable = new HtmlDataTable();
    private List<IngresosAlmacenDetalle> ingresosAlmacenDetalleMostrarList = new ArrayList<IngresosAlmacenDetalle>();
    private List<SelectItem> tiposIngresosList = new ArrayList<SelectItem>();
    private List<SelectItem> gestionesSelectList = new ArrayList<SelectItem>();
    private boolean permisoAprobacionMateriales = false;
    private boolean permisoAprobacionMaterialesGI = false;
    private boolean permisoAprobacionReanalisis = false;
    
    private void cargarPermisosPersonal()
    {
        Connection con1=null;
        try {
            con1 = Util.openConnection(con1);
            ManagedAccesoSistema managed = (ManagedAccesoSistema)Util.getSessionBean("ManagedAccesoSistema");
            StringBuilder consulta = new StringBuilder("select cp.COD_TIPO_PERMISO_ESPECIAL_BACO")
                        .append(" from CONFIGURACION_PERMISO_ESPECIAL_BACO cp")
                        .append(" WHERE cp.COD_PERSONAL =").append(managed.getUsuarioModuloBean().getCodUsuarioGlobal())
                                .append(" and cp.COD_ALMACEN = ").append(managed.getAlmacenesGlobal().getCodAlmacen());
            LOGGER.debug("consulta cargar permisos "+consulta.toString());
            Statement st = con1.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet res = st.executeQuery(consulta.toString());
            permisoAprobacionMateriales = false;
            permisoAprobacionMaterialesGI = false;
            permisoAprobacionReanalisis = false;
            while (res.next()) {
                switch(res.getInt("COD_TIPO_PERMISO_ESPECIAL_BACO"))
                {
                    case 16:
                    {
                        permisoAprobacionMateriales = true;
                        break;
                    }
                    case 17:
                    {
                        permisoAprobacionMaterialesGI = true;
                        break;
                    }
                    case 18:
                    {
                        permisoAprobacionReanalisis = true;
                        break;
                    }
                }
                
            }
            
            con1.close();
        } catch (SQLException ex) {
            LOGGER.warn(ex.getMessage());
        } catch (NumberFormatException ex) {
            LOGGER.warn(ex.getMessage());
        } 
    }
    // <editor-fold defaultstate="collapsed" desc="reanalisis">
        public String atrasListadoVencimiento_action() {
            
            inicioVencimientoList = inicioVencimientoList - cantidadRegistrosVencimientoList;
            finalVencimientoList = finalVencimientoList - cantidadRegistrosVencimientoList;
            this.cargarIngresosAlmacenDetalleEstadoVencimientoList();
            return null;
        }
        
        public String siguienteListadoVencimiento_action() {
            
            inicioVencimientoList = inicioVencimientoList + cantidadRegistrosVencimientoList;
            finalVencimientoList = finalVencimientoList + cantidadRegistrosVencimientoList;
            this.cargarIngresosAlmacenDetalleEstadoVencimientoList();
            return null;
        }
        
        public String guardarAprobarReanalisisMaterial_action()throws SQLException
        {
            mensaje = "";
            Connection con = null;
            try {
                con = Util.openConnection(con);
                con.setAutoCommit(false);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                StringBuilder consulta = new StringBuilder(" INSERT INTO INGRESOS_ALMACEN_DETALLE_ESTADO_FECHA_VENCIMIENTO(ETIQUETA,COD_INGRESO_ALMACEN, COD_MATERIAL, FECHA_VENCIMIENTO, FECHA_ACTUALIZACION)")
                                                    .append(" select iade.ETIQUETA,ia.COD_INGRESO_ALMACEN,iade.COD_MATERIAL,iade.FECHA_VENCIMIENTO,getdate()")
                                                    .append(" from INGRESOS_ALMACEN  ia")
                                                            .append(" inner join INGRESOS_ALMACEN_DETALLE_ESTADO iade on ia.COD_INGRESO_ALMACEN = iade.COD_INGRESO_ALMACEN")
                                                    .append(" where iade.COD_MATERIAL =").append(ingresosAlmacenDetalleEstadoAprobarReanalisis.getMateriales().getCodMaterial())
                                                            .append(" and iade.LOTE_MATERIAL_PROVEEDOR =?");
                LOGGER.debug("consulta guardar respaldo "+consulta.toString());
                PreparedStatement pst = con.prepareStatement(consulta.toString());
                pst.setString(1, ingresosAlmacenDetalleEstadoAprobarReanalisis.getLoteMaterialProveedor());LOGGER.info("p1 lote: "+ingresosAlmacenDetalleEstadoAprobarReanalisis.getLoteMaterialProveedor());
                if (pst.executeUpdate() > 0) LOGGER.info("se guardo el respaldo");
                consulta = new StringBuilder("update INGRESOS_ALMACEN_DETALLE_ESTADO  SET FECHA_VENCIMIENTO ='").append(sdf.format(ingresosAlmacenDetalleEstadoAprobarReanalisis.getFechaVencimiento1())).append(" 23:59'")
                                                .append(" ,COD_ESTADO_MATERIAL = (case when iade.COD_ESTADO_MATERIAL NOT IN (3,4) THEN ").append(COD_ESTADO_MATERIAL_APROBADO_REANALISIS).append(" ELSE IADE.COD_ESTADO_MATERIAL end)")
                                        .append(" from INGRESOS_ALMACEN  ia")
                                                .append(" inner join INGRESOS_ALMACEN_DETALLE_ESTADO iade on ia.COD_INGRESO_ALMACEN = iade.COD_INGRESO_ALMACEN")
                                        .append(" where iade.COD_MATERIAL =").append(ingresosAlmacenDetalleEstadoAprobarReanalisis.getMateriales().getCodMaterial())
                                        .append(" and iade.LOTE_MATERIAL_PROVEEDOR =?");
                LOGGER.debug("consulta aprobar reanalisis "+consulta.toString());
                pst = con.prepareStatement(consulta.toString());
                pst.setString(1, ingresosAlmacenDetalleEstadoAprobarReanalisis.getLoteMaterialProveedor());LOGGER.info("p1 lote: "+ingresosAlmacenDetalleEstadoAprobarReanalisis.getLoteMaterialProveedor());
                if (pst.executeUpdate() > 0) LOGGER.info("se actualizaron los ingresos del lote");
                
                con.commit();
                mensaje = "1";
                pst.close();
            } catch (SQLException ex) {
                mensaje = "Ocurrio un error de aprobar el reanalisis, verifique la información e intente de nuevo";
                LOGGER.warn(ex.getMessage());
                con.rollback();
            } catch (NumberFormatException ex) {
                mensaje = "Ocurrio un error de aprobar el reanalisis, verifique la información e intente de nuevo";
                LOGGER.warn(ex.getMessage());
                con.rollback();
            } finally {
                con.close();
            }
            if(mensaje.equals("1"))
            {
                this.cargarIngresosAlmacenDetalleEstadoVencimientoList();
            }
            return null;
        }
        public String aprobarReanalisisIngresosAlmacenDetalle_action()
        {
            Connection con =null;
            try {
                con = Util.openConnection(con);
                StringBuilder consulta = new StringBuilder("SELECT dbo.FN_VENCIMIENTO_INGRESO_ALMACEN(DATEADD(year,1,GETDATE())) as nuevaFechaVencimiento");
                LOGGER.debug("consulta NUEVA FECHA DE VENCIMIENTO "+consulta.toString());
                Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet res = st.executeQuery(consulta.toString());
                while (res.next()) {
                    ingresosAlmacenDetalleEstadoAprobarReanalisis.setFechaVencimiento1(res.getTimestamp("nuevaFechaVencimiento"));
                }
                consulta = new StringBuilder("select al.NOMBRE_ALMACEN,sum(iade.CANTIDAD_RESTANTE) as cantidadRestante")
                                    .append(" from ingresos_almacen ia")
                                            .append(" inner join ALMACENES al on al.COD_ALMACEN = ia.COD_ALMACEN ")
                                                .append(" and al.VERIFICAR_VENCIMIENTO = 1")
                                            .append(" inner join INGRESOS_ALMACEN_DETALLE_ESTADO iade on iade.COD_INGRESO_ALMACEN = ia.COD_INGRESO_ALMACEN")
                                    .append(" where ia.COD_ESTADO_INGRESO_ALMACEN not in (2)")
                                            .append(" and iade.COD_MATERIAL =").append(ingresosAlmacenDetalleEstadoAprobarReanalisis.getMateriales().getCodMaterial())
                                            .append(" and iade.LOTE_MATERIAL_PROVEEDOR = '").append(ingresosAlmacenDetalleEstadoAprobarReanalisis.getLoteMaterialProveedor()).append("'")
                                    .append(" group by al.NOMBRE_ALMACEN")
                                    .append(" having sum(iade.CANTIDAD_RESTANTE)>0.01")
                                    .append(" order by al.NOMBRE_ALMACEN");
                LOGGER.debug("consulta existencias por almacen "+consulta.toString());
                res = st.executeQuery(consulta.toString());
                almacenesExistenciaList = new ArrayList<Almacenes>();
                while(res.next())
                {
                    Almacenes almacenes = new Almacenes();
                    almacenes.setNombreAlmacen(res.getString("NOMBRE_ALMACEN"));
                    almacenes.setCantidadExistente(res.getDouble("cantidadRestante"));
                    almacenesExistenciaList.add(almacenes);
                }
                mensaje = "1";
                con.close();
            } catch (SQLException ex) {
                LOGGER.warn(ex.getMessage());
            } catch (NumberFormatException ex) {
                LOGGER.warn(ex.getMessage());
            } 
            return null;
        }
        public String getCargarIngresosAlmacenDetalleEstadoVencimiento()
        {
            this.cargarPermisosPersonal();
            this.cargarIngresosAlmacenDetalleEstadoVencimientoList();
            this.cargarEstadosMaterialList();
            return null;
        }
        public String buscarIngresosAlmacenDetalleEstadoVencimiento_action()
        {
            inicioVencimientoList = 0;
            finalVencimientoList = 20;
            this.cargarIngresosAlmacenDetalleEstadoVencimientoList();
            return null;
        }
        
        private void cargarIngresosAlmacenDetalleEstadoVencimientoList()
        {
            Connection con = null;
            try {
                con = Util.openConnection(con);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                StringBuilder consulta = new StringBuilder("select * from (select ROW_NUMBER() OVER ( ORDER BY m.NOMBRE_MATERIAL, iade.FECHA_VENCIMIENTO desc) as 'FILAS',")
                                                            .append(" iade.LOTE_MATERIAL_PROVEEDOR,m.COD_MATERIAL,m.NOMBRE_MATERIAL,iade.FECHA_VENCIMIENTO,");
                                                    consulta.append(" sum(iade.CANTIDAD_RESTANTE) as cantidadRestante");
                                                    consulta.append(" ,um.NOMBRE_UNIDAD_MEDIDA,em.NOMBRE_ESTADO_MATERIAL,iade.COD_ESTADO_MATERIAL");
                                        consulta.append(" from ingresos_almacen ia ");
                                                consulta.append(" inner join ALMACENES al on al.COD_ALMACEN=ia.COD_ALMACEN");
                                                        consulta.append(" and al.VERIFICAR_VENCIMIENTO = 1");
                                                consulta.append(" inner join INGRESOS_ALMACEN_DETALLE_ESTADO iade on iade.COD_INGRESO_ALMACEN = ia.COD_INGRESO_ALMACEN");
                                                consulta.append(" inner join materiales m on m.COD_MATERIAL = iade.COD_MATERIAL");
                                                consulta.append(" INNER JOIN grupos g ON g.COD_GRUPO= m.cod_grupo");
                                                consulta.append(" inner join ESTADOS_MATERIAL em on em.COD_ESTADO_MATERIAL =iade.COD_ESTADO_MATERIAL");
                                                consulta.append(" inner join UNIDADES_MEDIDA um on um.COD_UNIDAD_MEDIDA =m.COD_UNIDAD_MEDIDA");
                                        consulta.append(" where ia.COD_ESTADO_INGRESO_ALMACEN not in (2)");
                                                consulta.append(" and m.MATERIAL_MUESTREO = 1");
                                                consulta.append(" and g.COD_CAPITULO = 2");
                                                consulta.append(" and g.COD_GRUPO not in (5)");
                                                if(ingresosAlmacenDetalleEstadoVencimientoBuscar.getEstadosMaterial().getCodEstadoMaterial()>0)
                                                        consulta.append(" and iade.COD_ESTADO_MATERIAL =").append(ingresosAlmacenDetalleEstadoVencimientoBuscar.getEstadosMaterial().getCodEstadoMaterial());
                                                if(ingresosAlmacenDetalleEstadoVencimientoBuscar.getMateriales().getNombreMaterial().trim().length()>0)
                                                        consulta.append(" and m.NOMBRE_MATERIAL like '%").append(ingresosAlmacenDetalleEstadoVencimientoBuscar.getMateriales().getNombreMaterial()).append("%'");
                                                if(ingresosAlmacenDetalleEstadoVencimientoBuscar.getLoteMaterialProveedor().trim().length()>0)
                                                        consulta.append(" and iade.LOTE_MATERIAL_PROVEEDOR = '").append(ingresosAlmacenDetalleEstadoVencimientoBuscar.getLoteMaterialProveedor()).append("'");
                                                if(ingresosAlmacenDetalleEstadoVencimientoBuscar.getEstadosMaterial().getCodEstadoMaterial() == -1){
                                                        consulta.append(" and iade.FECHA_VENCIMIENTO between '").append(sdf.format(ingresosAlmacenDetalleEstadoVencimientoBuscar.getFechaVencimiento1())).append(" 00:00' and  '").append(sdf.format(ingresosAlmacenDetalleEstadoVencimientoBuscar.getFechaVencimiento2())).append(" 23:59'");
                                                        consulta.append(" and iade.COD_ESTADO_MATERIAL in (1,2,6,8)");
                                                }
                                        consulta.append(" group by iade.LOTE_MATERIAL_PROVEEDOR,m.COD_MATERIAL,m.NOMBRE_MATERIAL,iade.FECHA_VENCIMIENTO");
                                                consulta.append(",um.NOMBRE_UNIDAD_MEDIDA,em.NOMBRE_ESTADO_MATERIAL,iade.COD_ESTADO_MATERIAL");
                                        consulta.append(" HAVING sum(iade.CANTIDAD_RESTANTE)>0.1");
                                        consulta.append(") AS listado")
                                                .append(" where FILAS BETWEEN ").append(inicioVencimientoList).append(" AND ").append(finalVencimientoList);
                LOGGER.debug("consulta cargar listado reanalisis "+consulta.toString());
                Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet res = st.executeQuery(consulta.toString());
                ingresosAlmacenDetalleEstadoVencimientoList  = new ArrayList<IngresosAlmacenDetalleEstado>();
                while (res.next()) 
                {
                    IngresosAlmacenDetalleEstado detalle = new IngresosAlmacenDetalleEstado();
                    detalle.setLoteMaterialProveedor(res.getString("LOTE_MATERIAL_PROVEEDOR"));
                    detalle.getMateriales().setCodMaterial(res.getString("COD_MATERIAL"));
                    detalle.getMateriales().setNombreMaterial(res.getString("NOMBRE_MATERIAL"));
                    detalle.getMateriales().getUnidadesMedida().setNombreUnidadMedida(res.getString("NOMBRE_UNIDAD_MEDIDA"));
                    detalle.getEstadosMaterial().setNombreEstadoMaterial(res.getString("NOMBRE_ESTADO_MATERIAL"));
                    detalle.getEstadosMaterial().setCodEstadoMaterial(res.getInt("COD_ESTADO_MATERIAL"));
                    detalle.setFechaVencimiento(res.getTimestamp("FECHA_VENCIMIENTO"));
                    detalle.setCantidadRestante(res.getFloat("cantidadRestante"));
                    ingresosAlmacenDetalleEstadoVencimientoList.add(detalle);
                }
                
                mensaje = "1";
                con.close();
            } catch (SQLException ex) {
                LOGGER.warn(ex.getMessage());
            } catch (NumberFormatException ex) {
                LOGGER.warn(ex.getMessage());
            } 
        }
    //</editor-fold>
    public ManagedEstadosMaterial() {
        LOGGER = LogManager.getLogger("CambioEstadoMaterial");
        ingresosAlmacenDetalleEstadoVencimientoBuscar.getEstadosMaterial().setCodEstadoMaterial(5);
    }

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

    public List<IngresosAlmacenDetalle> getIngresosAlmacenDetalleList() {
        return ingresosAlmacenDetalleList;
    }

    public void setIngresosAlmacenDetalleList(List<IngresosAlmacenDetalle> ingresosAlmacenDetalleList) {
        this.ingresosAlmacenDetalleList = ingresosAlmacenDetalleList;
    }

    public List getIngresosAlmacenDetalleEstadoList() {
        return IngresosAlmacenDetalleEstadoList;
    }

    public void setIngresosAlmacenDetalleEstadoList(List IngresosAlmacenDetalleEstadoList) {
        this.IngresosAlmacenDetalleEstadoList = IngresosAlmacenDetalleEstadoList;
    }

    public IngresosAlmacenDetalleEstado getIngresosAlmacenDetalleEstado() {
        return ingresosAlmacenDetalleEstado;
    }

    public void setIngresosAlmacenDetalleEstado(IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstado) {
        this.ingresosAlmacenDetalleEstado = ingresosAlmacenDetalleEstado;
    }

    public List getEstadosMaterialList() {
        return estadosMaterialList;
    }

    public void setEstadosMaterialList(List estadosMaterialList) {
        this.estadosMaterialList = estadosMaterialList;
    }

    public int getCodEstadoMaterial() {
        return codEstadoMaterial;
    }

    public void setCodEstadoMaterial(int codEstadoMaterial) {
        this.codEstadoMaterial = codEstadoMaterial;
    }

    public IngresosAlmacenDetalleEstado getIngresosAlmacenDetalleEstadoBuscar() {
        return ingresosAlmacenDetalleEstadoBuscar;
    }

    public void setIngresosAlmacenDetalleEstadoBuscar(IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstadoBuscar) {
        this.ingresosAlmacenDetalleEstadoBuscar = ingresosAlmacenDetalleEstadoBuscar;
    }

    public List getCapitulosList() {
        return capitulosList;
    }

    public void setCapitulosList(List capitulosList) {
        this.capitulosList = capitulosList;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public Date getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaInicial(Date fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public List getGruposList() {
        return gruposList;
    }

    public void setGruposList(List gruposList) {
        this.gruposList = gruposList;
    }

    public HtmlDataTable getIngresosAlmacenDetalleEstadoDataTable() {
        return ingresosAlmacenDetalleEstadoDataTable;
    }

    public void setIngresosAlmacenDetalleEstadoDataTable(HtmlDataTable ingresosAlmacenDetalleEstadoDataTable) {
        this.ingresosAlmacenDetalleEstadoDataTable = ingresosAlmacenDetalleEstadoDataTable;
    }

    public List<IngresosAlmacenDetalle> getIngresosAlmacenDetalleMostrarList() {
        return ingresosAlmacenDetalleMostrarList;
    }

    public void setIngresosAlmacenDetalleMostrarList(List<IngresosAlmacenDetalle> ingresosAlmacenDetalleMostrarList) {
        this.ingresosAlmacenDetalleMostrarList = ingresosAlmacenDetalleMostrarList;
    }

    public List<SelectItem> getTiposIngresosList() {
        return tiposIngresosList;
    }

    public void setTiposIngresosList(List<SelectItem> tiposIngresosList) {
        this.tiposIngresosList = tiposIngresosList;
    }

    public List<SelectItem> getGestionesSelectList() {
        return gestionesSelectList;
    }

    public void setGestionesSelectList(List<SelectItem> gestionesSelectList) {
        this.gestionesSelectList = gestionesSelectList;
    }

    private String cargarGestionesActiva() {
        String codGestionActiva = "0";
        try {
            Connection con1 = null;
            con1 = Util.openConnection(con1);
            Statement st = con1.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String consulta = "select g.COD_GESTION,g.NOMBRE_GESTION from GESTIONES g order by g.COD_GESTION";
            ResultSet res = st.executeQuery(consulta);
            gestionesSelectList.clear();
            while (res.next()) {
                gestionesSelectList.add(new SelectItem(res.getString("COD_GESTION"), res.getString("NOMBRE_GESTION")));
            }
            consulta = "select max(g.COD_GESTION) as gestionActiva from GESTIONES g where g.GESTION_ESTADO=1";
            res = st.executeQuery(consulta);
            if (res.next()) {
                codGestionActiva = res.getString("gestionActiva");
            }
            res.close();
            st.close();
            con1.close();
        } catch (SQLException ex) {
            LOGGER.warn(ex);
        }
        return codGestionActiva;
    }

    public String getCargarIngresosAlmacen() {
        this.cargarPermisosPersonal();
        // ingresosAlmacenImpl.listadoIngresosAlmacen(filaInicial, filaFinal);
        try {
            fechaInicial = null;
            fechaFinal = null;
            this.cargarEstadosMaterialCambio();
            estadosMaterialList = obtieneEstadosMaterial();
            this.cantidadfilas = ingresosAlmacenList.size();
            this.cargarCapitulos();
            gruposList.clear();
            gruposList.add(new SelectItem("0", "-NINGUNO-"));

            ingresosAlmacenDetalleEstadoBuscar.getIngresosAlmacen().getGestiones().setCodGestion(this.cargarGestionesActiva());
            ingresosAlmacenDetalleEstadoBuscar.getIngresosAlmacenDetalle().getIngresosAlmacen().getAlmacenes().setNombreAlmacen(usuario.getAlmacenesGlobal().getNombreAlmacen());
            ingresosAlmacenDetalleEstadoBuscar.getIngresosAlmacenDetalle().getIngresosAlmacen().getAlmacenes().setCodAlmacen(usuario.getAlmacenesGlobal().getCodAlmacen());
            ingresosAlmacenDetalleEstadoBuscar.getIngresosAlmacenDetalle().getIngresosAlmacen().getAlmacenes().getFiliales().setCodFilial(usuario.getAlmacenesGlobal().getFiliales().getCodFilial());
            ingresosAlmacenDetalleEstadoBuscar.getIngresosAlmacenDetalle().getIngresosAlmacen().getAlmacenes().getFiliales().setNombreFilial(usuario.getAlmacenesGlobal().getFiliales().getNombreFilial());
            ingresosAlmacenDetalleEstadoBuscar.getIngresosAlmacen().getTiposIngresoAlmacen().setCodTipoIngresoAlmacen(4);
            ingresosAlmacenDetalleEstadoBuscar.getEstadosMaterial().setCodEstadoMaterial(1);
            this.cargarTiposIngresosAlmacen();
            ingresosAlmacenList = listadoIngresosAlmacen(begin, end, usuario);
            this.cantidadfilas = ingresosAlmacenList.size();

        } catch (Exception e) {
            LOGGER.warn(e);
        }
        return null;
    }

    private void cargarTiposIngresosAlmacen() {
        try {
            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String consulta = "select tia.COD_TIPO_INGRESO_ALMACEN,tia.NOMBRE_TIPO_INGRESO_ALMACEN from TIPOS_INGRESO_ALMACEN tia where tia.COD_ESTADO_REGISTRO=1";
            ResultSet res = st.executeQuery(consulta);
            tiposIngresosList.clear();

            while (res.next()) {
                tiposIngresosList.add(new SelectItem(res.getInt("COD_TIPO_INGRESO_ALMACEN"), res.getString("NOMBRE_TIPO_INGRESO_ALMACEN")));
            }
            res.close();
            st.close();
            con.close();
        } catch (Exception ex) {
            LOGGER.warn(ex);
        }

    }

    public List listadoIngresosAlmacen(int filaInicial, int filaFinal, ManagedAccesoSistema usuario) {
        List<IngresosAlmacen> ingresosAlmacenList = new ArrayList<IngresosAlmacen>();

        try {

            Connection con = null;
            con = Util.openConnection(con);

            String consulta = "select * from ( select ROW_NUMBER() OVER (ORDER BY FECHA_INGRESO_ALMACEN desc) as 'FILAS'  ,* from ("
                    + " select ia.NRO_INGRESO_ALMACEN,ia.COD_INGRESO_ALMACEN, "
                    + " eia.COD_ESTADO_INGRESO_ALMACEN,eia.NOMBRE_ESTADO_INGRESO_ALMACEN,  ia.FECHA_INGRESO_ALMACEN,t.COD_TIPO_INGRESO_ALMACEN,t.NOMBRE_TIPO_INGRESO_ALMACEN, "
                    + " oc.NRO_ORDEN_COMPRA,'' almacen_origen_traspaso,  pr.COD_PROVEEDOR,pr.NOMBRE_PROVEEDOR,p.COD_PAIS,p.NOMBRE_PAIS,ia.OBS_INGRESO_ALMACEN, "
                    + " per.NOMBRE_PILA,per.AP_PATERNO_PERSONAL,per.AP_MATERNO_PERSONAL"
                    + " ,(select  sum(iade.CANTIDAD_PARCIAL) from INGRESOS_ALMACEN_DETALLE_ESTADO iade inner join materiales m on m.COD_MATERIAL=iade.COD_MATERIAL"
                    + " inner join grupos g on g.COD_GRUPO=m.COD_GRUPO"
                    + " where iade.COD_INGRESO_ALMACEN=ia.COD_INGRESO_ALMACEN"
                    + (ingresosAlmacenDetalleEstadoBuscar.getLoteMaterialProveedor().equals("") ? "" : " and iade.LOTE_MATERIAL_PROVEEDOR='" + ingresosAlmacenDetalleEstadoBuscar.getLoteMaterialProveedor() + "'")
                    + (ingresosAlmacenDetalleEstadoBuscar.getIngresosAlmacenDetalle().getMateriales().getGrupos().getCodGrupo() > 0 ? " and g.COD_GRUPO = '" + ingresosAlmacenDetalleEstadoBuscar.getIngresosAlmacenDetalle().getMateriales().getGrupos().getCodGrupo() + "'" : "")
                    + (ingresosAlmacenDetalleEstadoBuscar.getIngresosAlmacenDetalle().getMateriales().getGrupos().getCapitulos().getCodCapitulo() > 0 ? " and g.COD_CAPITULO = '" + ingresosAlmacenDetalleEstadoBuscar.getIngresosAlmacenDetalle().getMateriales().getGrupos().getCapitulos().getCodCapitulo() + "' " : "")
                    + (ingresosAlmacenDetalleEstadoBuscar.getIngresosAlmacenDetalle().getMateriales().getNombreMaterial().equals("") ? "" : " and m.NOMBRE_MATERIAL like  '%" + ingresosAlmacenDetalleEstadoBuscar.getIngresosAlmacenDetalle().getMateriales().getNombreMaterial() + "%'")
                    + ((ingresosAlmacenDetalleEstadoBuscar.getEstadosMaterial().getCodEstadoMaterial() > 0 && ingresosAlmacenDetalleEstadoBuscar.getEstadosMaterial().getCodEstadoMaterial() != 7) ? " and iade.COD_ESTADO_MATERIAL =  '" + ingresosAlmacenDetalleEstadoBuscar.getEstadosMaterial().getCodEstadoMaterial() + "'" : "")
                    + " ) restante"
                    + " from INGRESOS_ALMACEN ia  left outer join  TIPOS_INGRESO_ALMACEN t on t.COD_TIPO_INGRESO_ALMACEN = ia.COD_TIPO_INGRESO_ALMACEN "
                    + " left outer join proveedores pr on pr.COD_PROVEEDOR = ia.COD_PROVEEDOR   "
                    + " left outer join  paises p on pr.COD_PAIS = p.COD_PAIS   "
                    + " left outer join ORDENES_COMPRA oc on oc.COD_ORDEN_COMPRA = ia.COD_ORDEN_COMPRA   "
                    + " left outer join ESTADOS_INGRESO_ALMACEN eia on eia.COD_ESTADO_INGRESO_ALMACEN = ia.COD_ESTADO_INGRESO_ALMACEN   "
                    + " left outer join TIPOS_COMPRA tc on tc.COD_TIPO_COMPRA = ia.COD_TIPO_COMPRA   "
                    + " left outer join personal per on per.COD_PERSONAL = ia.COD_PERSONAL   "
                    + " where ia.cod_estado_ingreso_almacen <>2 and ia.COD_ALMACEN = '" + usuario.getAlmacenesGlobal().getCodAlmacen() + "'  "
                    + (ingresosAlmacenDetalleEstadoBuscar.getIngresosAlmacen().getGestiones().getCodGestion().equals("0") ? "" : " and ia.COD_GESTION='" + ingresosAlmacenDetalleEstadoBuscar.getIngresosAlmacen().getGestiones().getCodGestion() + "'")
                    + (ingresosAlmacenDetalleEstadoBuscar.getIngresosAlmacenDetalle().getIngresosAlmacen().getNroIngresoAlmacen() > 0
                            ? " and ia.NRO_INGRESO_ALMACEN='" + ingresosAlmacenDetalleEstadoBuscar.getIngresosAlmacenDetalle().getIngresosAlmacen().getNroIngresoAlmacen() + "'" : "")
                    + ((fechaInicial != null && fechaFinal != null) ? " and ia.FECHA_INGRESO_ALMACEN between '" + sdf.format(fechaInicial) + " 00:00' and '" + sdf.format(fechaFinal) + " 23:59'" : "")
                    + (ingresosAlmacenDetalleEstadoBuscar.getIngresosAlmacen().getTiposIngresoAlmacen().getCodTipoIngresoAlmacen() != 4
                            ? " and ia.COD_TIPO_INGRESO_ALMACEN='" + ingresosAlmacenDetalleEstadoBuscar.getIngresosAlmacen().getTiposIngresoAlmacen().getCodTipoIngresoAlmacen() + "' " : "")
                    + " ) as listado_1 where restante>0) as listado  where  FILAS BETWEEN " + filaInicial + " AND " + filaFinal + " ORDER BY FILAS ASC ";
            con = Util.openConnection(con);
            LOGGER.debug("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            ingresosAlmacenList.clear();
            while (rs.next()) {
                IngresosAlmacen ingresosAlmacenItem = new IngresosAlmacen();
                ingresosAlmacenItem.setNroIngresoAlmacen(rs.getInt("NRO_INGRESO_ALMACEN"));
                ingresosAlmacenItem.setCodIngresoAlmacen(rs.getInt("COD_INGRESO_ALMACEN"));
                ingresosAlmacenItem.getEstadosIngresoAlmacen().setCodEstadoIngresoAlmacen(rs.getInt("COD_ESTADO_INGRESO_ALMACEN"));
                ingresosAlmacenItem.getEstadosIngresoAlmacen().setNombreEstadoIngresoAlmacen(rs.getString("NOMBRE_ESTADO_INGRESO_ALMACEN"));
                ingresosAlmacenItem.setFechaIngresoAlmacen(rs.getTimestamp("FECHA_INGRESO_ALMACEN"));
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
                ingresosAlmacenList.add(ingresosAlmacenItem);

            }

        } catch (Exception e) {
            
            LOGGER.warn(e);
        }
        return ingresosAlmacenList;
    }

    public String atras_action() {
        super.back();
        ingresosAlmacenList = listadoIngresosAlmacen(begin, end, usuario);
        this.cantidadfilas = ingresosAlmacenList.size();
        return "";
    }

    public String siguiente_action() {
        super.next();
        ingresosAlmacenList = listadoIngresosAlmacen(begin, end, usuario);
        this.cantidadfilas = ingresosAlmacenList.size();

        return "";
    }

    public String modificarEstadoItems_action() {
        try {
            IngresosAlmacenDetalleEstadoList = this.listadoIngresosAlmacenDetalleEstado();
            this.cargarListadoIngresosAlmacenDetalle(ingresosAlmacen.getCodIngresoAlmacen());

        } catch (Exception e) {
            LOGGER.warn(e);
        }
        return null;
    }

    //inicio ale mostrar
    public void cargarListadoIngresosAlmacenDetalle(int codIngresoAlmacen) {
        try {
            Connection con1 = null;
            con1 = Util.openConnection(con1);
            String consulta = "select m.NOMBRE_MATERIAL,iad.CANT_TOTAL_INGRESO_FISICO,u.NOMBRE_UNIDAD_MEDIDA"
                    + " from INGRESOS_ALMACEN ia inner join INGRESOS_ALMACEN_DETALLE iad on ia.COD_INGRESO_ALMACEN ="
                    + " iad.COD_INGRESO_ALMACEN inner join materiales m on m.COD_MATERIAL = iad.COD_MATERIAL"
                    + " left outer join UNIDADES_MEDIDA u on u.COD_UNIDAD_MEDIDA = iad.COD_UNIDAD_MEDIDA"
                    + " where ia.COD_INGRESO_ALMACEN = '" + codIngresoAlmacen + "' order by iad.cod_material";
            LOGGER.debug("consulta detalle " + consulta);
            Statement st = con1.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet res = st.executeQuery(consulta);
            ingresosAlmacenDetalleMostrarList.clear();
            while (res.next()) {
                IngresosAlmacenDetalle bean = new IngresosAlmacenDetalle();
                bean.getMateriales().setNombreMaterial(res.getString("NOMBRE_MATERIAL"));
                bean.setCantTotalIngresoFisico(res.getFloat("CANT_TOTAL_INGRESO_FISICO"));
                bean.getUnidadesMedida().setNombreUnidadMedida(res.getString("NOMBRE_UNIDAD_MEDIDA"));
                ingresosAlmacenDetalleMostrarList.add(bean);
            }
            res.close();
            st.close();
            con1.close();
        } catch (Exception ex) {
            LOGGER.warn(ex);
        }
    }

    //final ale mostrar
    public List listadoIngresosAlmacenDetalleEstado() {
        List<IngresosAlmacenDetalleEstado> ingresosAlmacenDetalleEstadoList = new ArrayList<IngresosAlmacenDetalleEstado>();
        try {
            Connection con = null;
            con = Util.openConnection(con);
            //llenar el listado de Ingresos Almacen
            StringBuilder consulta = new StringBuilder(" select ia.FECHA_INGRESO_ALMACEN,ia.NRO_INGRESO_ALMACEN,iade.FECHA_VENCIMIENTO,iade.ETIQUETA,iad.COD_MATERIAL, m.NOMBRE_MATERIAL,iade.CANTIDAD_PARCIAL, ");
            consulta.append(" u.COD_UNIDAD_MEDIDA,u.NOMBRE_UNIDAD_MEDIDA,s.COD_SECCION,s.NOMBRE_SECCION,e.COD_ESTADO_MATERIAL,e.NOMBRE_ESTADO_MATERIAL,ese.NOMBRE_EMPAQUE_SECUNDARIO_EXTERNO,iade.LOTE_MATERIAL_PROVEEDOR,iade.OBSERVACIONES,iade.OBS_CONTROL_CALIDAD");
            consulta.append(",iade.cantidad_restante,gr.COD_CAPITULO, iade.VALORACION_CC_PORCENTUAL ");
            consulta.append(" from INGRESOS_ALMACEN ia ");
            consulta.append(" inner join INGRESOS_ALMACEN_DETALLE iad on ia.COD_INGRESO_ALMACEN = iad.COD_INGRESO_ALMACEN ");
            consulta.append(" left outer join INGRESOS_ALMACEN_DETALLE_ESTADO iade on iad.COD_INGRESO_ALMACEN = iade.COD_INGRESO_ALMACEN and iad.COD_MATERIAL = iade.COD_MATERIAL  ");
            consulta.append(" inner join materiales m on m.COD_MATERIAL = iad.COD_MATERIAL ");
            consulta.append(" left outer join SECCIONES s on iad.COD_SECCION = s.COD_SECCION ");
            consulta.append(" left outer join UNIDADES_MEDIDA u on u.COD_UNIDAD_MEDIDA = iad.COD_UNIDAD_MEDIDA ");
            consulta.append(" left outer join ESTADOS_MATERIAL e on e.COD_ESTADO_MATERIAL = iade.COD_ESTADO_MATERIAL ");
            consulta.append(" left outer join EMPAQUES_SECUNDARIO_EXTERNO ese on ese.COD_EMPAQUE_SECUNDARIO_EXTERNO = iade.COD_EMPAQUE_SECUNDARIO_EXTERNO ");
            consulta.append(" inner join grupos gr on gr.COD_GRUPO=m.COD_GRUPO");
            consulta.append(" where");
            consulta.append(" ia.COD_INGRESO_ALMACEN = '").append(ingresosAlmacen.getCodIngresoAlmacen()).append("'");
            if (ingresosAlmacenDetalleEstadoBuscar.getEstadosMaterial().getCodEstadoMaterial() != 7) {
                consulta.append(" and iade.COD_ESTADO_MATERIAL=").append(ingresosAlmacenDetalleEstadoBuscar.getEstadosMaterial().getCodEstadoMaterial());
            }
            consulta.append(" order by iade.LOTE_MATERIAL_PROVEEDOR,iade.etiqueta ");
            con = Util.openConnection(con);
            LOGGER.debug("consulta " + consulta.toString());
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta.toString());
            ingresosAlmacenDetalleEstadoList.clear();
            while (rs.next()) {
                IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstado = new IngresosAlmacenDetalleEstado();
                ingresosAlmacenDetalleEstado.getIngresosAlmacenDetalle().getIngresosAlmacen().setFechaIngresoAlmacen(rs.getDate("FECHA_INGRESO_ALMACEN"));
                ingresosAlmacenDetalleEstado.getIngresosAlmacenDetalle().getIngresosAlmacen().setNroIngresoAlmacen(rs.getInt("NRO_INGRESO_ALMACEN"));
                ingresosAlmacenDetalleEstado.setFechaVencimiento(rs.getDate("FECHA_VENCIMIENTO"));
                ingresosAlmacenDetalleEstado.setEtiqueta(rs.getInt("ETIQUETA"));
                ingresosAlmacenDetalleEstado.getIngresosAlmacenDetalle().getMateriales().setCodMaterial(rs.getString("COD_MATERIAL"));
                ingresosAlmacenDetalleEstado.getIngresosAlmacenDetalle().getMateriales().setNombreMaterial(rs.getString("NOMBRE_MATERIAL"));
                ingresosAlmacenDetalleEstado.setCantidadParcial(rs.getFloat("CANTIDAD_RESTANTE"));
                ingresosAlmacenDetalleEstado.getIngresosAlmacenDetalle().getUnidadesMedida().setNombreUnidadMedida(rs.getString("NOMBRE_UNIDAD_MEDIDA"));
                ingresosAlmacenDetalleEstado.getIngresosAlmacenDetalle().getSecciones().setNombreSeccion(rs.getString("NOMBRE_SECCION"));
                ingresosAlmacenDetalleEstado.getEstadosMaterial().setCodEstadoMaterial(rs.getInt("COD_ESTADO_MATERIAL"));
                ingresosAlmacenDetalleEstado.getEstadosMaterial().setNombreEstadoMaterial(rs.getString("NOMBRE_ESTADO_MATERIAL"));
                ingresosAlmacenDetalleEstado.getEmpaqueSecundarioExterno().setNombreEmpaqueSecundarioExterno(rs.getString("NOMBRE_EMPAQUE_SECUNDARIO_EXTERNO"));
                ingresosAlmacenDetalleEstado.setLoteMaterialProveedor(rs.getString("LOTE_MATERIAL_PROVEEDOR"));
                ingresosAlmacenDetalleEstado.setObservaciones(rs.getString("OBSERVACIONES"));
                ingresosAlmacenDetalleEstado.setObsControlCalidad(rs.getInt("OBS_CONTROL_CALIDAD"));
                ingresosAlmacenDetalleEstado.setValoracionCCPorcentual(rs.getFloat("VALORACION_CC_PORCENTUAL"));
                String color = rs.getInt("cod_estado_material") == 1 ? "#FFFF00" : "";//AMARILLO
                color = rs.getInt("cod_estado_material") == 2 ? "#009900" : color;//VERDE
                color = rs.getInt("cod_estado_material") == 3 ? "#FF0000" : color;//ROJIZO
                color = rs.getInt("cod_estado_material") == 4 ? "#FF0000" : color;//ROJIZO
                color = rs.getInt("cod_estado_material") == 5 ? "#FF0000" : color;//ROJIZO
                color = rs.getInt("cod_estado_material") == 6 ? "#0000FF" : color;//AZUL
                ingresosAlmacenDetalleEstado.setColorFila(color);
                if (ingresosAlmacenDetalleEstado.getEstadosMaterial().getCodEstadoMaterial() == 5 && rs.getInt("COD_CAPITULO") == 2) {
                    ingresosAlmacenDetalleEstado.setIngresosAlmacenDetalleEstadoFechaVencimiento(new IngresosAlmacenDetalleEstadoFechaVencimiento());
                    ingresosAlmacenDetalleEstado.getIngresosAlmacenDetalleEstadoFechaVencimiento().setFechaVencimiento(rs.getDate("FECHA_VENCIMIENTO"));
                }
                ingresosAlmacenDetalleEstadoList.add(ingresosAlmacenDetalleEstado);
                valoracionCCPorcentual = ingresosAlmacenDetalleEstado.getValoracionCCPorcentual();
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception e) {
            LOGGER.warn(e);
        }
        return ingresosAlmacenDetalleEstadoList;
    }

    public String editarEtiqueta_action() {
        try {
            for (IngresosAlmacenDetalleEstado bean : IngresosAlmacenDetalleEstadoList) {
                if (bean.getChecked()) {
                    ingresosAlmacenDetalleEstado = bean;
                    break;
                }
            }

        } catch (Exception e) {
            LOGGER.warn(e);
        }
        return null;
    }
    private void cargarEstadosMaterialList()
    {
        Connection con = null;
        try {
            con = Util.openConnection(con);
            StringBuilder consulta = new StringBuilder("SELECT E.COD_ESTADO_MATERIAL,E.NOMBRE_ESTADO_MATERIAL")
                                        .append(" FROM ESTADOS_MATERIAL E")
                                        .append(" WHERE E.COD_ESTADO_MATERIAL  NOT IN (7) ")
                                        .append(" order by E.NOMBRE_ESTADO_MATERIAL");
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet res = st.executeQuery(consulta.toString());
            estadosMaterialList = new ArrayList<SelectItem>();
            while (res.next()) {
                estadosMaterialList.add(new SelectItem(res.getInt("COD_ESTADO_MATERIAL"),res.getString("NOMBRE_ESTADO_MATERIAL")));
            }
            
            con.close();
        } catch (SQLException ex) {
            LOGGER.warn(ex.getMessage());
        } catch (NumberFormatException ex) {
            LOGGER.warn(ex.getMessage());
        } 
    }
    public List obtieneEstadosMaterial() {
        List estadosMaterialList = new ArrayList();
        try {

            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " SELECT E.COD_ESTADO_MATERIAL,E.NOMBRE_ESTADO_MATERIAL  FROM ESTADOS_MATERIAL E WHERE E.COD_ESTADO_REGISTRO = 1 ";

            ResultSet rs = null;
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            estadosMaterialList.clear();
            rs = st.executeQuery(consulta);
            //estadosMaterialList.add(new SelectItem("0", "-NINGUNO-"));
            while (rs.next()) {
                estadosMaterialList.add(new SelectItem(rs.getString("COD_ESTADO_MATERIAL"), rs.getString("NOMBRE_ESTADO_MATERIAL")));
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
        return estadosMaterialList;
    }

    private void cargarEstadosMaterialCambio() {

        try {

            Connection con = null;
            con = Util.openConnection(con);
            ManagedAccesoSistema managed = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            String consulta = " select count(*) as permiso from PERMISOS_APROBAR_MATERIAL_GI p where p.COD_PERSONAL='" + managed.getUsuarioModuloBean().getCodUsuarioGlobal() + "'";
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet res = st.executeQuery(consulta);
            res.next();
            consulta = "select e.COD_ESTADO_MATERIAL,e.NOMBRE_ESTADO_MATERIAL from ESTADOS_MATERIAL e where e.COD_ESTADO_REGISTRO=1"
                    + " and e.COD_ESTADO_MATERIAL not in (7" + (res.getInt("permiso") > 0 ? "" : ",6") + ")"
                    + " order by e.NOMBRE_ESTADO_MATERIAL";
            estadosMaterialCambioSelectList.clear();
            res = st.executeQuery(consulta);
            while (res.next()) {
                estadosMaterialCambioSelectList.add(new SelectItem(res.getString("COD_ESTADO_MATERIAL"), res.getString("NOMBRE_ESTADO_MATERIAL")));
            }
            res.close();
            st.close();

        } catch (Exception e) {
            LOGGER.warn(e);
        }

    }

    public String guardarModificarEstadoItem_action() throws SQLException {
        mensaje = "";
        Connection con1 = null;
        try {
            con1 = Util.openConnection(con1);
            con1.setAutoCommit(false);
            SimpleDateFormat sdfFecha = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            //capturamos el usuario activo
            ManagedAccesoSistema managed = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            StringBuilder consulta = new StringBuilder(" UPDATE INGRESOS_ALMACEN_DETALLE_ESTADO  ");
            consulta.append(" SET COD_ESTADO_MATERIAL = '").append(ingresosAlmacenDetalleEstado.getEstadosMaterial().getCodEstadoMaterial()).append("' ,  ");
            consulta.append(" OBSERVACIONES = '").append(ingresosAlmacenDetalleEstado.getObservaciones()).append("', ");
            consulta.append(" OBS_CONTROL_CALIDAD = '").append(ingresosAlmacenDetalleEstado.getObsControlCalidad()).append("',");
            //Jaime actualizacion de valoracion control de calidad
            consulta.append(" VALORACION_CC_PORCENTUAL = '").append(ingresosAlmacenDetalleEstado.getValoracionCCPorcentual()).append("',");
            //Fin actualizacion valoracion
            //en caso de cambio de fecha actualizamos la misma
            if (ingresosAlmacenDetalleEstado.getIngresosAlmacenDetalleEstadoFechaVencimiento() != null) {
                consulta.append(" FECHA_VENCIMIENTO='").append(sdfFecha.format(ingresosAlmacenDetalleEstado.getIngresosAlmacenDetalleEstadoFechaVencimiento().getFechaVencimiento())).append("',");
            }
            consulta.append(" FECHA_CAMBIO_ESTADO_MATERIAL='").append(sdf.format(new Date())).append("',");
            consulta.append(" COD_PERSONAL_CAMBIO_ESTADO='").append(managed.getUsuarioModuloBean().getCodUsuarioGlobal()).append("'");
            consulta.append(" WHERE COD_INGRESO_ALMACEN = '").append(ingresosAlmacen.getCodIngresoAlmacen()).append("' ");
            consulta.append(" AND COD_MATERIAL = '").append(ingresosAlmacenDetalleEstado.getIngresosAlmacenDetalle().getMateriales().getCodMaterial()).append("'");
            consulta.append(" AND ETIQUETA = '").append(ingresosAlmacenDetalleEstado.getEtiqueta()).append("' ");
            LOGGER.debug("consulta " + consulta.toString());
            PreparedStatement pst = con1.prepareStatement(consulta.toString());
            if (pst.executeUpdate() > 0) {
                LOGGER.debug("se cambiaron los detalle de la etiqueta");
            }
            //en caso de cambio de fecha guardamos un historico
            if (ingresosAlmacenDetalleEstado.getIngresosAlmacenDetalleEstadoFechaVencimiento() != null) {
                consulta = new StringBuilder("INSERT INTO INGRESOS_ALMACEN_DETALLE_ESTADO_FECHA_VENCIMIENTO(ETIQUETA,COD_INGRESO_ALMACEN, COD_MATERIAL, FECHA_VENCIMIENTO, FECHA_ACTUALIZACION)");
                consulta.append(" VALUES (");
                consulta.append(ingresosAlmacenDetalleEstado.getEtiqueta()).append(",");
                consulta.append(ingresosAlmacen.getCodIngresoAlmacen()).append(",");
                consulta.append(ingresosAlmacenDetalleEstado.getIngresosAlmacenDetalle().getMateriales().getCodMaterial()).append(",");
                consulta.append("'").append(sdfFecha.format(ingresosAlmacenDetalleEstado.getFechaVencimiento())).append("',");
                consulta.append("GETDATE()");
                consulta.append(")");
                LOGGER.debug("consulta guardar historico de la fecha de vencimiento" + consulta.toString());
                pst = con1.prepareStatement(consulta.toString());
                if (pst.executeUpdate() > 0) {
                    LOGGER.debug("se registro el historico de fecha de vencimiento");
                }
            }
            con1.commit();
            mensaje = "1";
            pst.close();
            con1.close();
        } catch (SQLException ex) {
            LOGGER.warn(ex);
            con1.rollback();
            mensaje = "Ocurrio un error al momento de guardar la informacion";
        } catch (Exception ex) {
            LOGGER.warn(ex);
            mensaje = "Ocurrio un error al momento de guardar la informacion";
        } finally {
            if (con1 != null) {
                con1.close();
            }
        }
        //si todas las transacciones terminaron bien se vuelve a cargar el listado
        if (mensaje.equals("1")) {
            IngresosAlmacenDetalleEstadoList = this.listadoIngresosAlmacenDetalleEstado();
        }
        return null;
    }
    private void cambiarEstadoIngresoAlmacenDetalleEstado(int codEstadoMaterialAprobacion) throws SQLException{
        Connection con1 = null;
        mensaje = "";
        try {
            SimpleDateFormat sdfFecha = new SimpleDateFormat("yyyy/MM/dd");
            con1 = Util.openConnection(con1);
            con1.setAutoCommit(false);
            PreparedStatement pst = null;
            ManagedAccesoSistema managed = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            for (IngresosAlmacenDetalleEstado bean : IngresosAlmacenDetalleEstadoList) 
            {
                if (bean.getChecked()) {
                    StringBuilder consulta = new StringBuilder(" UPDATE INGRESOS_ALMACEN_DETALLE_ESTADO  ");
                                                consulta.append(" SET COD_ESTADO_MATERIAL = '").append(codEstadoMaterialAprobacion).append("' ,  ");
                                                consulta.append(" OBSERVACIONES = '").append(bean.getObservaciones()).append("', ");
                                                consulta.append(" OBS_CONTROL_CALIDAD = '").append(bean.getObsControlCalidad()).append("',");
                                                //Jaime actualizacion de valoracion control de calidad
                                                consulta.append(" VALORACION_CC_PORCENTUAL = '").append(valoracionCCPorcentual).append("',");
                                                //Fin actualizacion valoracion                                          
                                                //en caso de cambio de fecha actualizamos la misma
                                                consulta.append(" FECHA_CAMBIO_ESTADO_MATERIAL=GETDATE(),");
                                                consulta.append(" COD_PERSONAL_CAMBIO_ESTADO='").append(managed.getUsuarioModuloBean().getCodUsuarioGlobal()).append("'");
                                                consulta.append(" WHERE COD_INGRESO_ALMACEN = '").append(ingresosAlmacen.getCodIngresoAlmacen()).append("' ");
                                                        consulta.append(" AND COD_MATERIAL = '").append(bean.getIngresosAlmacenDetalle().getMateriales().getCodMaterial()).append("'");
                                                        consulta.append(" AND ETIQUETA = '").append(bean.getEtiqueta()).append("' ");
                    LOGGER.info("consulta aprobar cambio de estado "+consulta.toString());
                    pst = con1.prepareStatement(consulta.toString());
                    if (pst.executeUpdate() > 0) LOGGER.info("Se cambiaron las etiquetas");
                    //en caso de cambio de fecha guardamos un historico
                    if (bean.getIngresosAlmacenDetalleEstadoFechaVencimiento() != null) {
                        consulta = new StringBuilder("INSERT INTO INGRESOS_ALMACEN_DETALLE_ESTADO_FECHA_VENCIMIENTO(ETIQUETA,COD_INGRESO_ALMACEN, COD_MATERIAL, FECHA_VENCIMIENTO, FECHA_ACTUALIZACION)");
                        consulta.append(" VALUES (");
                        consulta.append(bean.getEtiqueta()).append(",");
                        consulta.append(ingresosAlmacen.getCodIngresoAlmacen()).append(",");
                        consulta.append(bean.getIngresosAlmacenDetalle().getMateriales().getCodMaterial()).append(",");
                        consulta.append("'").append(sdfFecha.format(bean.getFechaVencimiento())).append("',");
                        consulta.append("GETDATE()");
                        consulta.append(")");
                        LOGGER.debug("consulta guardar historico de la fecha de vencimiento" + consulta.toString());
                        pst = con1.prepareStatement(consulta.toString());
                        if (pst.executeUpdate() > 0) {
                            LOGGER.debug("se registro el historico de fecha de vencimiento");
                        }
                    }
                }
            }
            con1.commit();
            if (pst != null) {
                pst.close();
            }
            mensaje = "1";
        } catch (SQLException ex) {
            LOGGER.warn(ex);
            con1.rollback();
        } catch (Exception ex) {
            LOGGER.warn(ex);
        } finally{con1.close();}
    }
    private void cambiarEstadoIngresoAlmacenDetalleEstadoLoteProveedor(int codEstadoMaterialAprobacion) throws SQLException{
        Connection con1 = null;
        mensaje = "";
        try {
            SimpleDateFormat sdfFecha = new SimpleDateFormat("yyyy/MM/dd");
            con1 = Util.openConnection(con1);
            con1.setAutoCommit(false);
            ManagedAccesoSistema managed = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            //llenar el listado de Ingresos Almacen
            StringBuilder consulta = new StringBuilder(" UPDATE INGRESOS_ALMACEN_DETALLE_ESTADO  ");
            consulta.append(" SET COD_ESTADO_MATERIAL = '").append(codEstadoMaterialAprobacion).append("' ,  ");
            consulta.append(" OBSERVACIONES = '").append(ingresosAlmacenDetalleEstado.getObservaciones()).append("', ");
            consulta.append(" OBS_CONTROL_CALIDAD = '").append(ingresosAlmacenDetalleEstado.getObsControlCalidad()).append("',");
            //Jaime actualizacion de valoracion control de calidad
            consulta.append(" VALORACION_CC_PORCENTUAL = '").append(valoracionCCPorcentual).append("',");
            //Fin actualizacion valoracion                                          
            //en caso de cambio de fecha actualizamos la misma
            consulta.append(" FECHA_CAMBIO_ESTADO_MATERIAL=").append("GETDATE()").append(",");
            consulta.append(" COD_PERSONAL_CAMBIO_ESTADO='").append(managed.getUsuarioModuloBean().getCodUsuarioGlobal()).append("'");
            consulta.append(" WHERE COD_INGRESO_ALMACEN = '").append(ingresosAlmacen.getCodIngresoAlmacen()).append("' ");
            consulta.append(" AND COD_MATERIAL = '").append(codMaterialSelected).append("'");
            consulta.append(" AND LOTE_MATERIAL_PROVEEDOR = '").append(loteProveedorSelected).append("' ");
            consulta.append(" AND COD_ESTADO_MATERIAL=1");
            LOGGER.debug("consulta aprobar material cuarentena por lote "+consulta.toString());
            PreparedStatement pst = con1.prepareStatement(consulta.toString());
            if(pst.executeUpdate()>0)LOGGER.info("se aprobo el material de cuarentena por lote");
            //en caso de cambio de fecha guardamos un historico
            con1.commit();
            if (pst != null) {
                pst.close();
            }
            mensaje = "1";
        } catch (SQLException ex) {
            LOGGER.warn(ex);
            con1.rollback();
            mensaje="ocurrio un error en la BD. Consulte con sistemas.";
        } catch (Exception ex) {
            LOGGER.warn(ex);
             mensaje="ocurrio un error en la al guardar. Consulte con sistemas.";
        } finally {
            if (con1 != null || !con1.isClosed()) {
                con1.close();
            }
        }
        if(mensaje.equals("1"))
        {
            this.onChangeMaterialIngreso();
        }
    }
    
    
    public String rechazarIngresoAlmacenLoteProveedorAction() throws SQLException
    {
        int codEstadoRechazado = 3;
        this.cambiarEstadoIngresoAlmacenDetalleEstadoLoteProveedor(codEstadoRechazado);
        return null;
    }
    
    public String aprobarIngresoAlmacenLoteProveedorAction() throws SQLException
    {
        int codEstadoAprobado = 2;
        this.cambiarEstadoIngresoAlmacenDetalleEstadoLoteProveedor(codEstadoAprobado);
        return null;
    }
    public String aprobarGIIngresoAlmacenLoteProveedorAction() throws SQLException
    {
        int codEstadoAprobadoGI = 6;
        this.cambiarEstadoIngresoAlmacenDetalleEstadoLoteProveedor(codEstadoAprobadoGI);
        return null;
    }
    
    
    public String aprobarLoteProveedorIngreso_action() throws SQLException {
        Connection con1 = null;
        mensaje = "";
        try {
            SimpleDateFormat sdfFecha = new SimpleDateFormat("yyyy/MM/dd");
            con1 = Util.openConnection(con1);
            con1.setAutoCommit(false);
            Statement pst = con1.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);;
            //usuario actual
            ManagedAccesoSistema managed = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            //llenar el listado de Ingresos Almacen
            StringBuilder consulta = new StringBuilder(" UPDATE INGRESOS_ALMACEN_DETALLE_ESTADO  ");
            consulta.append(" SET COD_ESTADO_MATERIAL = '").append(codEstadoMaterial).append("' ,  ");
            consulta.append(" OBSERVACIONES = '").append(ingresosAlmacenDetalleEstado.getObservaciones()).append("', ");
            consulta.append(" OBS_CONTROL_CALIDAD = '").append(ingresosAlmacenDetalleEstado.getObsControlCalidad()).append("',");
            //Jaime actualizacion de valoracion control de calidad
            consulta.append(" VALORACION_CC_PORCENTUAL = '").append(valoracionCCPorcentual).append("',");
            //Fin actualizacion valoracion                                          
            //en caso de cambio de fecha actualizamos la misma
            if (ingresosAlmacenDetalleEstado.getIngresosAlmacenDetalleEstadoFechaVencimiento() != null) {
                consulta.append(" FECHA_VENCIMIENTO='").append(sdfFecha.format(ingresosAlmacenDetalleEstado.getIngresosAlmacenDetalleEstadoFechaVencimiento().getFechaVencimiento())).append("',");
            }
            consulta.append(" FECHA_CAMBIO_ESTADO_MATERIAL=").append("GETDATE()").append(",");
            consulta.append(" COD_PERSONAL_CAMBIO_ESTADO='").append(managed.getUsuarioModuloBean().getCodUsuarioGlobal()).append("'");
            consulta.append(" WHERE COD_INGRESO_ALMACEN = '").append(ingresosAlmacen.getCodIngresoAlmacen()).append("' ");
            consulta.append(" AND COD_MATERIAL = '").append(codMaterialSelected).append("'");
            consulta.append(" AND LOTE_MATERIAL_PROVEEDOR = '").append(loteProveedorSelected).append("' ");
            LOGGER.debug("consulta " + consulta.toString());
           
            if (pst.executeUpdate(consulta.toString()) > 0) {
                LOGGER.debug("se cambiaron los detalle de la etiqueta");
            }
            //en caso de cambio de fecha guardamos un historico
            if (ingresosAlmacenDetalleEstado.getIngresosAlmacenDetalleEstadoFechaVencimiento() != null) {
                consulta = new StringBuilder("INSERT INTO INGRESOS_ALMACEN_DETALLE_ESTADO_FECHA_VENCIMIENTO(ETIQUETA,COD_INGRESO_ALMACEN, COD_MATERIAL, FECHA_VENCIMIENTO, FECHA_ACTUALIZACION)");
                consulta.append(" select etiqueta, cod_ingreso_almacen, COD_MATERIAL, FECHA_VENCIMIENTO, getdate() from INGRESOS_ALMACEN_DETALLE_ESTADO");
                consulta.append(" WHERE COD_INGRESO_ALMACEN = '").append(ingresosAlmacen.getCodIngresoAlmacen()).append("' ");
                consulta.append(" AND COD_MATERIAL = '").append(codMaterialSelected).append("'");
                consulta.append(" AND LOTE_MATERIAL_PROVEEDOR = '").append(loteProveedorSelected).append("' ");
                LOGGER.debug("consulta guardar historico de la fecha de vencimiento" + consulta.toString());
                
                if (pst.executeUpdate(consulta.toString()) > 0) {
                    LOGGER.debug("se registro el historico de fecha de vencimiento");
                }
            }
            con1.commit();
            if (pst != null) {
                pst.close();
            }
            mensaje = "1";
        } catch (SQLException ex) {
            LOGGER.warn(ex);
            con1.rollback();
            mensaje="ocurrio un error en la BD. Consulte con sistemas.";
        } catch (Exception ex) {
            LOGGER.warn(ex);
             mensaje="ocurrio un error en la al guardar. Consulte con sistemas.";
        } finally {
            if (con1 != null || !con1.isClosed()) {
                con1.close();
            }
        }
        if (mensaje.equals("1")) {
            IngresosAlmacenDetalleEstadoList = this.listadoIngresosAlmacenDetalleEstadoLoteProveedor();
        }
        return null;
    }
    public String rechazarIngresoAlmaceneDetalleEstadoAction() throws SQLException
    {
        int codEstadoRechazado = 3;
        this.cambiarEstadoIngresoAlmacenDetalleEstado(codEstadoRechazado);
        return null;
    }
    
    public String aprobarIngresoAlmaceneDetalleEstadoAction() throws SQLException
    {
        int codEstadoAprobado = 2;
        this.cambiarEstadoIngresoAlmacenDetalleEstado(codEstadoAprobado);
        return null;
    }
    public String aprobarGIIngresoAlmacenDetalleEstadoAction() throws SQLException
    {
        int codEstadoAprobadoGI = 6;
        this.cambiarEstadoIngresoAlmacenDetalleEstado(codEstadoAprobadoGI);
        return null;
    }
    
    public String guardarModificarEstadoItems_action() throws SQLException {
        
        
        return null;
    }

    private float valoracionCCPorcentual;

    public float getValoracionCCPorcentual() {
        return valoracionCCPorcentual;
    }

    public void setValoracionCCPorcentual(float valoracionCCPorcentual) {
        this.valoracionCCPorcentual = valoracionCCPorcentual;
    }

    public void cargarCapitulos() {
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select c.COD_CAPITULO,c.NOMBRE_CAPITULO  from CAPITULOS c where c.COD_ESTADO_REGISTRO = 1 ";

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
    }

    public String capitulo_changed() {
        try {

            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select gr.COD_GRUPO,gr.NOMBRE_GRUPO from grupos gr where gr.COD_ESTADO_REGISTRO = 1 "
                    + " and gr.COD_CAPITULO = '" + ingresosAlmacenDetalleEstadoBuscar.getIngresosAlmacenDetalle().getMateriales().getGrupos().getCapitulos().getCodCapitulo() + "' ";

            LOGGER.debug("consulta " + consulta);
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

    public String buscarIngresoAlmacen_action() {
        try {
            begin = 1;
            end = 10;
            ingresosAlmacenList = this.listadoIngresosAlmacen(begin, end, usuario);

        } catch (Exception e) {
            LOGGER.warn(e);
        }
        return null;
    }

    public String verReporteIngresoAlmacen_action() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map sessionMap = externalContext.getSessionMap();
        IngresosAlmacen ingresosAlmacen = (IngresosAlmacen) ingresosAlmacenDetalleEstadoDataTable.getRowData();
        sessionMap.put("ingresosAlmacen", ingresosAlmacen);
        return null;
    }

    public List<SelectItem> getEstadosMaterialCambioSelectList() {
        return estadosMaterialCambioSelectList;
    }

    public void setEstadosMaterialCambioSelectList(List<SelectItem> estadosMaterialCambioSelectList) {
        this.estadosMaterialCambioSelectList = estadosMaterialCambioSelectList;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    

    private List<SelectItem> materialesIngresoAlmacen = new ArrayList<SelectItem>();

    public List<SelectItem> getMaterialesIngresoAlmacen() {
        return materialesIngresoAlmacen;
    }

    public void setMaterialesIngresoAlmacen(List<SelectItem> materialesIngresoAlmacen) {
        this.materialesIngresoAlmacen = materialesIngresoAlmacen;
    }

    private List<SelectItem> lotesMaterial = new ArrayList<SelectItem>();

    public List<SelectItem> getLotesMaterial() {
        return lotesMaterial;
    }

    public void setLotesMaterial(List<SelectItem> lotesMaterial) {
        this.lotesMaterial = lotesMaterial;
    }

    public String modificarEstadoLoteProveedor_action() {
        try {
            IngresosAlmacenDetalleEstadoList = new ArrayList<IngresosAlmacenDetalleEstado>();
            lotesMaterial = new ArrayList<SelectItem>();
            this.cargarListadoMaterialesIngresosAlmacen(ingresosAlmacen.getCodIngresoAlmacen());
            codMaterialSelected=null;
            loteProveedorSelected=null;
            ingresosAlmacenDetalleEstado=new IngresosAlmacenDetalleEstado();
//this.cargarListadoLotesProveedor(ingresosAlmacen.getCodIngresoAlmacen(), codMaterialSelected);

        } catch (Exception e) {
            LOGGER.debug(e.getMessage());
        }
        return null;
    }

    private String codMaterialSelected;
    private String loteProveedorSelected;

    public String getCodMaterialSelected() {
        return codMaterialSelected;
    }

    public void setCodMaterialSelected(String codMaterialSelected) {
        this.codMaterialSelected = codMaterialSelected;
    }

    public String getLoteProveedorSelected() {
        return loteProveedorSelected;
    }

    public void setLoteProveedorSelected(String loteProveedorSelected) {
        this.loteProveedorSelected = loteProveedorSelected;
    }

    public void cargarListadoMaterialesIngresosAlmacen(int codIngresoAlmacen) {
        try {
            Connection con1 = null;
            con1 = Util.openConnection(con1);
            String consulta = "select iad.COD_MATERIAL, mat.NOMBRE_MATERIAL"
                    + " from INGRESOS_ALMACEN_DETALLE iad"
                    + " inner join MATERIALES mat on mat.COD_MATERIAL=iad.COD_MATERIAL"
                    + " where iad.COD_INGRESO_ALMACEN = '" + codIngresoAlmacen + "' order by iad.cod_material";
            LOGGER.debug("consulta materiales " + consulta);
            Statement st = con1.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet res = st.executeQuery(consulta);
            materialesIngresoAlmacen = new ArrayList<SelectItem>();
            while (res.next()) {
                SelectItem bean = new SelectItem();
                bean.setValue(res.getString("COD_MATERIAL"));
                bean.setLabel(res.getString("NOMBRE_MATERIAL"));
                materialesIngresoAlmacen.add(bean);
            }
            res.close();
            st.close();
            con1.close();
        } catch (Exception ex) {
            LOGGER.warn(ex);
        }
    }

    public void cargarListadoLotesProveedor(int codIngresoAlmacen, String codMaterial) {
        try {
            Connection con1 = null;
            con1 = Util.openConnection(con1);
            String consulta = "select distinct(LOTE_MATERIAL_PROVEEDOR) LOTE_MATERIAL_PROVEEDOR"
                    + " from INGRESOS_ALMACEN_DETALLE_ESTADO iade"
                    + " where COD_INGRESO_ALMACEN=" + codIngresoAlmacen
                    + " and COD_MATERIAL=" + codMaterial;
            LOGGER.debug("consulta lote proveedor " + consulta);
            Statement st = con1.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet res = st.executeQuery(consulta);
            lotesMaterial = new ArrayList<SelectItem>();
            while (res.next()) {
                SelectItem bean = new SelectItem();
                bean.setValue(res.getString("LOTE_MATERIAL_PROVEEDOR"));
                bean.setLabel(res.getString("LOTE_MATERIAL_PROVEEDOR"));
                lotesMaterial.add(bean);
            }
            res.close();
            st.close();
            con1.close();
        } catch (Exception ex) {
            LOGGER.warn(ex);
        }
    }

    public String onChangeMaterialIngreso() {
        cantidadCuarentenaLoteProveedor = 0;
        this.cargarListadoLotesProveedor(ingresosAlmacen.getCodIngresoAlmacen(), codMaterialSelected);
        if (lotesMaterial.size() == 1) {
            loteProveedorSelected = (String) lotesMaterial.get(0).getValue();
        }
        IngresosAlmacenDetalleEstadoList = this.listadoIngresosAlmacenDetalleEstadoLoteProveedor();
        return null;
    }

    public String onChangeLoteProveedor() {
        cantidadCuarentenaLoteProveedor = 0;
        IngresosAlmacenDetalleEstadoList = this.listadoIngresosAlmacenDetalleEstadoLoteProveedor();
        return null;
    }

    public List<IngresosAlmacenDetalleEstado> listadoIngresosAlmacenDetalleEstadoLoteProveedor() {
        List<IngresosAlmacenDetalleEstado> ingresosAlmacenDetalleEstadoList = new ArrayList<IngresosAlmacenDetalleEstado>();
        try {
            Connection con = null;
            con = Util.openConnection(con);
            //llenar el listado de Ingresos Almacen
            StringBuilder consulta = new StringBuilder(" select ia.FECHA_INGRESO_ALMACEN,ia.NRO_INGRESO_ALMACEN,iade.FECHA_VENCIMIENTO,iade.ETIQUETA,iad.COD_MATERIAL, m.NOMBRE_MATERIAL,iade.CANTIDAD_PARCIAL, ");
            consulta.append(" u.COD_UNIDAD_MEDIDA,u.NOMBRE_UNIDAD_MEDIDA,e.COD_ESTADO_MATERIAL,e.NOMBRE_ESTADO_MATERIAL,ese.NOMBRE_EMPAQUE_SECUNDARIO_EXTERNO,iade.LOTE_MATERIAL_PROVEEDOR,iade.OBSERVACIONES,iade.OBS_CONTROL_CALIDAD");
            consulta.append(",iade.cantidad_restante,gr.COD_CAPITULO, iade.VALORACION_CC_PORCENTUAL ");
            consulta.append(" from INGRESOS_ALMACEN ia ");
            consulta.append(" inner join INGRESOS_ALMACEN_DETALLE iad on ia.COD_INGRESO_ALMACEN = iad.COD_INGRESO_ALMACEN ");
            consulta.append(" left outer join INGRESOS_ALMACEN_DETALLE_ESTADO iade on iad.COD_INGRESO_ALMACEN = iade.COD_INGRESO_ALMACEN and iad.COD_MATERIAL = iade.COD_MATERIAL  ");
            consulta.append(" inner join materiales m on m.COD_MATERIAL = iad.COD_MATERIAL ");
            consulta.append(" left outer join UNIDADES_MEDIDA u on u.COD_UNIDAD_MEDIDA = iad.COD_UNIDAD_MEDIDA ");
            consulta.append(" left outer join ESTADOS_MATERIAL e on e.COD_ESTADO_MATERIAL = iade.COD_ESTADO_MATERIAL ");
            consulta.append(" left outer join EMPAQUES_SECUNDARIO_EXTERNO ese on ese.COD_EMPAQUE_SECUNDARIO_EXTERNO = iade.COD_EMPAQUE_SECUNDARIO_EXTERNO ");
            consulta.append(" inner join grupos gr on gr.COD_GRUPO=m.COD_GRUPO");
            consulta.append(" where");
            consulta.append(" ia.COD_INGRESO_ALMACEN = '").append(ingresosAlmacen.getCodIngresoAlmacen()).append("'");
            consulta.append(" and iade.cod_material = '").append(codMaterialSelected).append("'");
            consulta.append(" and iade.lote_material_proveedor = '").append(loteProveedorSelected).append("'");

            consulta.append(" order by iade.LOTE_MATERIAL_PROVEEDOR,iade.etiqueta ");
            con = Util.openConnection(con);
            LOGGER.debug("consulta JEstado " + consulta.toString());
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta.toString());
            ingresosAlmacenDetalleEstadoList.clear();
            cantidadCuarentenaLoteProveedor = 0;
            while (rs.next()) {
                cantidadCuarentenaLoteProveedor += (rs.getInt("COD_ESTADO_MATERIAL") == 1 ? 1 : 0 ); 
                IngresosAlmacenDetalleEstado registro = new IngresosAlmacenDetalleEstado();
                registro.getIngresosAlmacenDetalle().getIngresosAlmacen().setFechaIngresoAlmacen(rs.getDate("FECHA_INGRESO_ALMACEN"));
                registro.getIngresosAlmacenDetalle().getIngresosAlmacen().setNroIngresoAlmacen(rs.getInt("NRO_INGRESO_ALMACEN"));
                registro.setFechaVencimiento(rs.getDate("FECHA_VENCIMIENTO"));
                registro.setEtiqueta(rs.getInt("ETIQUETA"));
                registro.getIngresosAlmacenDetalle().getMateriales().setCodMaterial(rs.getString("COD_MATERIAL"));
                registro.getIngresosAlmacenDetalle().getMateriales().setNombreMaterial(rs.getString("NOMBRE_MATERIAL"));
                registro.setCantidadParcial(rs.getFloat("CANTIDAD_RESTANTE"));
                registro.getIngresosAlmacenDetalle().getUnidadesMedida().setNombreUnidadMedida(rs.getString("NOMBRE_UNIDAD_MEDIDA"));
                registro.getEstadosMaterial().setCodEstadoMaterial(rs.getInt("COD_ESTADO_MATERIAL"));
                registro.getEstadosMaterial().setNombreEstadoMaterial(rs.getString("NOMBRE_ESTADO_MATERIAL"));
                registro.getEmpaqueSecundarioExterno().setNombreEmpaqueSecundarioExterno(rs.getString("NOMBRE_EMPAQUE_SECUNDARIO_EXTERNO"));
                registro.setLoteMaterialProveedor(rs.getString("LOTE_MATERIAL_PROVEEDOR"));
                registro.setObservaciones(rs.getString("OBSERVACIONES"));
                registro.setObsControlCalidad(rs.getInt("OBS_CONTROL_CALIDAD"));
                registro.setValoracionCCPorcentual(rs.getFloat("VALORACION_CC_PORCENTUAL"));
                String color = rs.getInt("cod_estado_material") == 1 ? "#FFFF00" : "";//AMARILLO
                color = rs.getInt("cod_estado_material") == 2 ? "#009900" : color;//VERDE
                color = rs.getInt("cod_estado_material") == 3 ? "#FF0000" : color;//ROJIZO
                color = rs.getInt("cod_estado_material") == 4 ? "#FF0000" : color;//ROJIZO
                color = rs.getInt("cod_estado_material") == 5 ? "#FF0000" : color;//ROJIZO
                color = rs.getInt("cod_estado_material") == 6 ? "#0000FF" : color;//AZUL
                registro.setColorFila(color);
                if (registro.getEstadosMaterial().getCodEstadoMaterial() == 5 && rs.getInt("COD_CAPITULO") == 2) {
                    registro.setIngresosAlmacenDetalleEstadoFechaVencimiento(new IngresosAlmacenDetalleEstadoFechaVencimiento());
                    registro.getIngresosAlmacenDetalleEstadoFechaVencimiento().setFechaVencimiento(rs.getDate("FECHA_VENCIMIENTO"));
                    ingresosAlmacenDetalleEstado.setIngresosAlmacenDetalleEstadoFechaVencimiento(new IngresosAlmacenDetalleEstadoFechaVencimiento());
                }
                ingresosAlmacenDetalleEstadoList.add(registro);
                valoracionCCPorcentual = registro.getValoracionCCPorcentual();
                ingresosAlmacenDetalleEstado.setFechaVencimiento(registro.getFechaVencimiento());
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception e) {
            LOGGER.warn(e);
        }
        return ingresosAlmacenDetalleEstadoList;
    }
    // <editor-fold defaultstate="collapsed" desc="getter and setter">

        public List<Almacenes> getAlmacenesExistenciaList() {
            return almacenesExistenciaList;
        }

        public void setAlmacenesExistenciaList(List<Almacenes> almacenesExistenciaList) {
            this.almacenesExistenciaList = almacenesExistenciaList;
        }



        public int getCantidadCuarentenaLoteProveedor() {
            return cantidadCuarentenaLoteProveedor;
        }

        public void setCantidadCuarentenaLoteProveedor(int cantidadCuarentenaLoteProveedor) {
            this.cantidadCuarentenaLoteProveedor = cantidadCuarentenaLoteProveedor;
        }

        public boolean isPermisoAprobacionReanalisis() {
            return permisoAprobacionReanalisis;
        }

        public void setPermisoAprobacionReanalisis(boolean permisoAprobacionReanalisis) {
            this.permisoAprobacionReanalisis = permisoAprobacionReanalisis;
        }



        public boolean isPermisoAprobacionMateriales() {
            return permisoAprobacionMateriales;
        }

        public void setPermisoAprobacionMateriales(boolean permisoAprobacionMateriales) {
            this.permisoAprobacionMateriales = permisoAprobacionMateriales;
        }

        public boolean isPermisoAprobacionMaterialesGI() {
            return permisoAprobacionMaterialesGI;
        }

        public void setPermisoAprobacionMaterialesGI(boolean permisoAprobacionMaterialesGI) {
            this.permisoAprobacionMaterialesGI = permisoAprobacionMaterialesGI;
        }
 
        public IngresosAlmacenDetalleEstado getIngresosAlmacenDetalleEstadoAprobarReanalisis() {
            return ingresosAlmacenDetalleEstadoAprobarReanalisis;
        }

        public void setIngresosAlmacenDetalleEstadoAprobarReanalisis(IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstadoAprobarReanalisis) {
            this.ingresosAlmacenDetalleEstadoAprobarReanalisis = ingresosAlmacenDetalleEstadoAprobarReanalisis;
        }

        public int getInicioVencimientoList() {
            return inicioVencimientoList;
        }

        public void setInicioVencimientoList(int inicioVencimientoList) {
            this.inicioVencimientoList = inicioVencimientoList;
        }

        public int getFinalVencimientoList() {
            return finalVencimientoList;
        }

        public void setFinalVencimientoList(int finalVencimientoList) {
            this.finalVencimientoList = finalVencimientoList;
        }

        public int getCantidadRegistrosVencimientoList() {
            return cantidadRegistrosVencimientoList;
        }

        public void setCantidadRegistrosVencimientoList(int cantidadRegistrosVencimientoList) {
            this.cantidadRegistrosVencimientoList = cantidadRegistrosVencimientoList;
        }



        public IngresosAlmacenDetalleEstado getIngresosAlmacenDetalleEstadoVencimientoBuscar() {
            return ingresosAlmacenDetalleEstadoVencimientoBuscar;
        }

        public void setIngresosAlmacenDetalleEstadoVencimientoBuscar(IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstadoVencimientoBuscar) {
            this.ingresosAlmacenDetalleEstadoVencimientoBuscar = ingresosAlmacenDetalleEstadoVencimientoBuscar;
        }
        

        public int getIngresosAlmacenDetalleEstadoVencimientoListSize() {
            return (ingresosAlmacenDetalleEstadoVencimientoList != null? ingresosAlmacenDetalleEstadoVencimientoList.size() : 0);
        }

        public List<IngresosAlmacenDetalleEstado> getIngresosAlmacenDetalleEstadoVencimientoList() {
            return ingresosAlmacenDetalleEstadoVencimientoList;
        }

        public void setIngresosAlmacenDetalleEstadoVencimientoList(List<IngresosAlmacenDetalleEstado> ingresosAlmacenDetalleEstadoVencimientoList) {
            this.ingresosAlmacenDetalleEstadoVencimientoList = ingresosAlmacenDetalleEstadoVencimientoList;
        }

    //</editor-fold>

    

}
