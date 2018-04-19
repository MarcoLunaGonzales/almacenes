/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.web;

import com.cofar.bean.EstanteAlmacen;
import com.cofar.bean.IngresosAlmacenDetalleEstado;
import com.cofar.bean.Materiales;

import com.cofar.util.Util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.richfaces.component.html.HtmlDataTable;

/**
 *
 * @author DASISAQ-
 */

public class ManagedCambioUbicacionIngresos extends ManagedBean{

    private Logger LOGGER;
    private Connection con=null;
    private List<SelectItem> ambientesList=new ArrayList<SelectItem>();
    private List<SelectItem> estantesList=new ArrayList<SelectItem>();
    private List<SelectItem> capitulosSelectList = new ArrayList<>();
    private List<SelectItem> gruposSelectList = new ArrayList<>();
    private List<IngresosAlmacenDetalleEstado> ingresosAlmacenDetalleEstadoList=new ArrayList<IngresosAlmacenDetalleEstado>();
    private List<IngresosAlmacenDetalleEstado> ingresosAlmacenDetalleEstadoModificarUbicacion=null;
    private IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstadoBuscar=new IngresosAlmacenDetalleEstado();
    private IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstadoEditar=null;
    private IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstadoSeleccionado;
    private IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstadoNuevaUbication=new IngresosAlmacenDetalleEstado();
    private String mensaje="";
    private int tipoMovimiento=1;
    private List<SelectItem> materialesSelectList=new ArrayList<SelectItem>();
    private boolean administradorAlmacen = false;

   

    
    /** Creates a new instance of ManagedCambioUbicacionIngresos */
    public ManagedCambioUbicacionIngresos() {
        LOGGER = LogManager.getRootLogger();
        ingresosAlmacenDetalleEstadoBuscar.getIngresosAlmacen().getAlmacenes().getFiliales().setCodFilial(1);
        ingresosAlmacenDetalleEstadoBuscar.getIngresosAlmacen().getAlmacenes().setCodAlmacen(1);
        ingresosAlmacenDetalleEstadoBuscar.getMateriales().setCodMaterial("0");
    }
    public String codGrupoOnChange(){
        this.cargarMaterialesSelectList();
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
            LOGGER.warn(ex);
        }
    }
    public String codCapituloOnChange(){
        try {
            con = Util.openConnection(con);
            StringBuilder consulta = new StringBuilder("select g.COD_GRUPO, g.NOMBRE_GRUPO")
                                                .append(" from GRUPOS g")
                                                .append(" where g.COD_CAPITULO =").append(ingresosAlmacenDetalleEstadoBuscar.getMateriales().getGrupos().getCapitulos().getCodCapitulo())
                                                .append(" order by g.NOMBRE_GRUPO ");
            LOGGER.debug("consulta cargar grupos capitulo: "+consulta.toString());
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet res=st.executeQuery(consulta.toString());
            gruposSelectList = new ArrayList<>();
            while(res.next()){
                gruposSelectList.add(new SelectItem(res.getInt("COD_GRUPO"),res.getString("NOMBRE_GRUPO")));
            }
          
          con.close();
        } catch (SQLException ex) {
            LOGGER.warn(ex.getMessage());
        } catch (NumberFormatException ex) {
            LOGGER.warn(ex.getMessage());
        } 
        ingresosAlmacenDetalleEstadoBuscar.getMateriales().getGrupos().setCodGrupo(0);
        codGrupoOnChange();
        return null;
    }
    private void cargarCapitulosSelectList(){
        Connection con = null;
        try {
            ManagedAccesoSistema managed = (ManagedAccesoSistema)Util.getSessionBean("ManagedAccesoSistema");
            con = Util.openConnection(con);
            StringBuilder consulta = new StringBuilder("select c.COD_CAPITULO,c.NOMBRE_CAPITULO")
                                            .append(" from capitulos c ")
                                                    .append(" inner join CAPITULOS_GESTION_UBICACION cgu on cgu.COD_CAPITULO = c.COD_CAPITULO")
                                            .append(" where cgu.COD_ALMACEN=").append(managed.getAlmacenesGlobal().getCodAlmacen())
                                            .append(" order by c.NOMBRE_CAPITULO");
            LOGGER.debug("consulta cargar capiutlos "+consulta.toString());
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet res=st.executeQuery(consulta.toString());
            capitulosSelectList = new ArrayList<>();
            while(res.next()){
                capitulosSelectList.add(new SelectItem(res.getInt("COD_CAPITULO"),res.getString("NOMBRE_CAPITULO")));
            }
            con.close();
        } catch (SQLException ex) {
          LOGGER.warn("error", ex);
        } catch (NumberFormatException ex) {
            LOGGER.warn("error", ex);
        } 
    }
    private void cargarMaterialesSelectList()
    {
        try {
            con = Util.openConnection(con);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ManagedAccesoSistema managed = (ManagedAccesoSistema)Util.getSessionBean("ManagedAccesoSistema");
            StringBuilder consulta = new StringBuilder("SELECT m.COD_MATERIAL,m.NOMBRE_MATERIAL ")
                              .append(" FROM  MATERIALES m ")
                                    .append(" INNER JOIN GRUPOS g on g.COD_GRUPO=m.COD_GRUPO ")
                                    .append(" inner join CAPITULOS c on c.COD_CAPITULO=g.COD_CAPITULO ")
                                    .append(" inner join CAPITULOS_GESTION_UBICACION cgu on cgu.COD_CAPITULO= c.COD_CAPITULO ")
                              .append(" where cgu.COD_ALMACEN = ").append(managed.getAlmacenesGlobal().getCodAlmacen());
                                    if(ingresosAlmacenDetalleEstadoBuscar.getMateriales().getGrupos().getCodGrupo() > 0){
                                        consulta.append(" and g.COD_GRUPO= ").append(ingresosAlmacenDetalleEstadoBuscar.getMateriales().getGrupos().getCodGrupo());
                                    }
                                    consulta.append(" and c.COD_ESTADO_REGISTRO=1 ")
                              .append(" order by m.NOMBRE_MATERIAL");
            LOGGER.debug("consulta materiales "+consulta.toString());
            ResultSet res = st.executeQuery(consulta.toString());
            materialesSelectList.clear();
            while (res.next()){
                materialesSelectList.add(new SelectItem(res.getString("COD_MATERIAL"),res.getString("NOMBRE_MATERIAL")));
            }
            res.close();
            st.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    
    private void cargarMaterialesSaldos()
    {
        try {
            con = Util.openConnection(con);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            lista_MaterialesSaldos=new ArrayList<IngresosAlmacenDetalleEstado>();
            String consulta = "SELECT m.COD_MATERIAL,m.NOMBRE_MATERIAL, ea.cod_estante, ea.nombre_estante, mus.fila, mus.columna"+
                              " FROM  MATERIALES m INNER JOIN GRUPOS g on g.COD_GRUPO=m.COD_GRUPO"+
                              " inner join CAPITULOS c on c.COD_CAPITULO=g.COD_CAPITULO"+
                              " inner join materiales_ubicacion_saldos mus on mus.cod_material=m.cod_material"+
                              " inner join ESTANTE_AMBIENTE ea on ea.cod_estante=mus.cod_estante"+
                              " where c.COD_ESTADO_REGISTRO=1"+
                              " order by m.NOMBRE_MATERIAL";
            ResultSet res = st.executeQuery(consulta);
            lista_MaterialesSaldos.clear();
            while (res.next()) 
            {
                IngresosAlmacenDetalleEstado reg=new IngresosAlmacenDetalleEstado();
                Materiales material=new Materiales();
                material.setCodMaterial(res.getString("cod_material"));
                material.setNombreMaterial(res.getString("nombre_material"));
                reg.setMateriales(material);
                EstanteAlmacen estante=new EstanteAlmacen();
                estante.setCodEstante(res.getInt("cod_estante"));
                estante.setNombreEstante(res.getString("nombre_estante"));
                reg.setEstanteAlmacen(estante);
                reg.setColumna(res.getString("columna"));
                reg.setFila(res.getString("fila"));
                lista_MaterialesSaldos.add(reg);
            }
            res.close();
            st.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    private List<IngresosAlmacenDetalleEstado>lista_MaterialesSaldos=new ArrayList<IngresosAlmacenDetalleEstado>();

    public List<IngresosAlmacenDetalleEstado> getLista_MaterialesSaldos() {
        return lista_MaterialesSaldos;
    }

    public void setLista_MaterialesSaldos(List<IngresosAlmacenDetalleEstado> lista_MaterialesSaldos) {
        this.lista_MaterialesSaldos = lista_MaterialesSaldos;
    }
    
    public String vaciarUbicacionIngresosAlmacenDetalleEstado_action()throws SQLException
    {
        mensaje = "";
        try {
            con = Util.openConnection(con);
            con.setAutoCommit(false);
            StringBuilder consulta = new StringBuilder("update INGRESOS_ALMACEN_DETALLE_ESTADO")
                                        .append(" set COD_ESTANTE = 0,")
                                                .append(" FILA='',")
                                                .append(" COLUMNA=''")
                                        .append(" where COD_MATERIAL=").append(ingresosAlmacenDetalleEstadoEditar.getMateriales().getCodMaterial())
                                                .append(" and COD_INGRESO_ALMACEN in  (")
                                                        .append(" select i.COD_INGRESO_ALMACEN")
                                                        .append(" from INGRESOS_ALMACEN i")
                                                        .append(" where i.COD_ALMACEN=").append(ingresosAlmacenDetalleEstadoBuscar.getIngresosAlmacen().getAlmacenes().getCodAlmacen())
                                                .append(")")
                                                .append(" and FILA='").append(ingresosAlmacenDetalleEstadoEditar.getFila()).append("'")
                                                .append(" and COLUMNA='").append(ingresosAlmacenDetalleEstadoEditar.getColumna()).append("'")
                                                .append(" and COD_ESTANTE='").append(ingresosAlmacenDetalleEstadoEditar.getEstanteAlmacen().getCodEstante()).append("'")
                                                .append(" and CANTIDAD_RESTANTE>0.01")
                                                .append(" and LOTE_MATERIAL_PROVEEDOR = '").append(ingresosAlmacenDetalleEstadoEditar.getLoteMaterialProveedor()).append("'");
            System.out.println("consulta cambiar estado "+consulta.toString());
            PreparedStatement pst = con.prepareStatement(consulta.toString());
            if(pst.executeUpdate()>0 )System.out.println("se cambio el estado");
            
            mensaje = "1";
            con.commit();
        } catch (SQLException ex) {
            mensaje = "Ocurrio un error al momento de quitar la ubicacion, intente de nuevo";
            con.rollback();
            ex.printStackTrace();
        } catch (NumberFormatException ex) {
            mensaje = "Ocurrio un error al momento de quitar la ubicacion, intente de nuevo";
            ex.printStackTrace();
        } finally {
            try
            {
                con.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        if(mensaje.equals("1"))
            this.buscarIngresosUbicacion_action();
        return null;
    }
    public String seleccionIngresoCambioItem_action()
    {
        
        System.out.println("ctrl editar: "+ingresosAlmacenDetalleEstadoEditar.getEstanteAlmacen().getAmbienteAlmacen().getCodAmbiente());
        ingresosAlmacenDetalleEstadoNuevaUbication.getEstanteAlmacen().getAmbienteAlmacen().setCodAmbiente(
        ingresosAlmacenDetalleEstadoEditar.getEstanteAlmacen().getAmbienteAlmacen().getCodAmbiente());
        ingresosAlmacenDetalleEstadoNuevaUbication.getEstanteAlmacen().setCodEstante(
        ingresosAlmacenDetalleEstadoEditar.getEstanteAlmacen().getCodEstante());
        ingresosAlmacenDetalleEstadoNuevaUbication.setFila(
        ingresosAlmacenDetalleEstadoEditar.getFila());
        ingresosAlmacenDetalleEstadoNuevaUbication.setColumna(
        ingresosAlmacenDetalleEstadoEditar.getColumna());
        System.out.println("ctrl ambiente: "+ingresosAlmacenDetalleEstadoNuevaUbication.getEstanteAlmacen().getAmbienteAlmacen().getCodAmbiente());
        this.onchangeAmbienteEditar_action();
        tipoMovimiento=1;
        return null;
    }
    public String codAmbienteIngresosAlmacenChange(){
        ingresosAlmacenDetalleEstadoSeleccionado.setEstantesSelectList(this.cargarEstantesAmbiente(ingresosAlmacenDetalleEstadoSeleccionado.getEstanteAlmacen().getAmbienteAlmacen().getCodAmbiente()));
        return null;
    }
    private void cargarIngresosDetalleEstadoSeccion()
    {
        try {
            con = Util.openConnection(con);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String consulta = "select i.COD_INGRESO_ALMACEN,isnull(a.NOMBRE_AMBIENTE,'SIN AMBIENTE') as NOMBRE_AMBIENTE,m.COD_MATERIAL,ISNULL(e.NOMBRE_ESTANTE,'SIN ESTANTE') AS NOMBRE_ESTANTE,i.COD_ESTANTE,i.FILA,ia.NRO_INGRESO_ALMACEN"+
                              " ,i.ETIQUETA,i.COLUMNA,m.NOMBRE_MATERIAL,a.COD_AMBIENTE,i.CANTIDAD_RESTANTE,i.lote_material_proveedor"+
                              " from INGRESOS_ALMACEN_DETALLE_ESTADO i inner join INGRESOS_ALMACEN ia on "+
                             " ia.COD_INGRESO_ALMACEN=i.COD_INGRESO_ALMACEN"+
                              " left outer join ESTANTE_AMBIENTE e on e.COD_ESTANTE = i.COD_ESTANTE"+
                              " left outer join AMBIENTE_ALMACEN a on a.COD_AMBIENTE = e.COD_AMBIENTE"+
                             " inner join materiales m on m.COD_MATERIAL = i.COD_MATERIAL"+
                             " where i.CANTIDAD_RESTANTE > 0.1 and"+
                             " ia.COD_ALMACEN = '"+ingresosAlmacenDetalleEstadoBuscar.getIngresosAlmacen().getAlmacenes().getCodAlmacen()+"' "+
                             " and isnull(a.COD_AMBIENTE,0) ="+ ingresosAlmacenDetalleEstadoEditar.getEstanteAlmacen().getAmbienteAlmacen().getCodAmbiente()+
                             " and isnull(i.COD_ESTANTE,0) = "+ingresosAlmacenDetalleEstadoEditar.getEstanteAlmacen().getCodEstante()+
                             " and isnull(i.FILA,'') = ?"+
                             " and isnull(i.COLUMNA,'') = ?"+
                             " and i.COD_MATERIAL='"+ingresosAlmacenDetalleEstadoEditar.getMateriales().getCodMaterial()+"'"+
                     " and ia.cod_estado_ingreso_almacen=1 "        
                    +" order by ia.NRO_INGRESO_ALMACEN,i.ETIQUETA";
            System.out.println("consulta cargar ingresos buscar "+consulta);
            PreparedStatement pst = con.prepareStatement(consulta);
            pst.setString(1, ingresosAlmacenDetalleEstadoEditar.getFila());System.out.println("p1: "+ingresosAlmacenDetalleEstadoEditar.getFila());
            pst.setString(2, ingresosAlmacenDetalleEstadoEditar.getColumna());System.out.println("p2: "+ingresosAlmacenDetalleEstadoEditar.getColumna());
            ResultSet res = pst.executeQuery();
            ingresosAlmacenDetalleEstadoModificarUbicacion=new ArrayList<IngresosAlmacenDetalleEstado>();
            while (res.next()) {
                IngresosAlmacenDetalleEstado nuevo=new IngresosAlmacenDetalleEstado();
                nuevo.getIngresosAlmacen().setCodIngresoAlmacen(res.getInt("COD_INGRESO_ALMACEN"));
                nuevo.getIngresosAlmacen().setNroIngresoAlmacen(res.getInt("NRO_INGRESO_ALMACEN"));
                nuevo.setEtiqueta(res.getInt("ETIQUETA"));
                nuevo.getEstanteAlmacen().getAmbienteAlmacen().setNombreAmbiente(res.getString("NOMBRE_AMBIENTE"));
                nuevo.getEstanteAlmacen().getAmbienteAlmacen().setCodAmbiente(res.getInt("COD_AMBIENTE"));
                nuevo.getEstanteAlmacen().setNombreEstante(res.getString("NOMBRE_ESTANTE"));
                nuevo.getEstanteAlmacen().setCodEstante(res.getInt("COD_ESTANTE"));
                nuevo.setFila(res.getString("FILA"));
                nuevo.setColumna(res.getString("COLUMNA"));
                nuevo.setCantidadRestante(res.getFloat("CANTIDAD_RESTANTE"));
                nuevo.setLoteMaterialProveedor(res.getString("LOTE_MATERIAL_PROVEEDOR"));
                nuevo.setEstantesSelectList(estantesList);
                ingresosAlmacenDetalleEstadoModificarUbicacion.add(nuevo);
            }
            res.close();
            st.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public String guardarCambioUbicacionIngresoAlmacen_action()throws SQLException
    {
        mensaje="";
        try {
            con = Util.openConnection(con);
            con.setAutoCommit(false);
            String consulta="";
            PreparedStatement pst=null;
            if(tipoMovimiento==1)
            {
                consulta = " update INGRESOS_ALMACEN_DETALLE_ESTADO"
                        + " set COD_ESTANTE='"+ingresosAlmacenDetalleEstadoNuevaUbication.getEstanteAlmacen().getCodEstante()+"',"+
                                  " FILA='"+ingresosAlmacenDetalleEstadoNuevaUbication.getFila()+"',COLUMNA='"+ingresosAlmacenDetalleEstadoNuevaUbication.getColumna()+"'"+
                                  " where COD_MATERIAL='"+ingresosAlmacenDetalleEstadoEditar.getMateriales().getCodMaterial()+"' and COD_INGRESO_ALMACEN in " +
                                  " ( select i.COD_INGRESO_ALMACEN from INGRESOS_ALMACEN i where i.COD_ALMACEN='"+ingresosAlmacenDetalleEstadoBuscar.getIngresosAlmacen().getAlmacenes().getCodAlmacen()+"')"+
                                  (ingresosAlmacenDetalleEstadoEditar.getEstanteAlmacen().getCodEstante()>0?" and FILA='"+ingresosAlmacenDetalleEstadoEditar.getFila()+"'" +
                                  " and COLUMNA='"+ingresosAlmacenDetalleEstadoEditar.getColumna()+"'" +
                                  " and COD_ESTANTE='"+ingresosAlmacenDetalleEstadoEditar.getEstanteAlmacen().getCodEstante()+"'":
                                  " and (COD_ESTANTE=0 or COD_ESTANTE is null)")+
                                  " and CANTIDAD_RESTANTE>0.1";
                System.out.println("consulta update ubicacion "+consulta);
                pst=con.prepareStatement(consulta);
                if(pst.executeUpdate()>0)System.out.println("se realizo el cambio");
            }
            else
            {
                for(IngresosAlmacenDetalleEstado bean:ingresosAlmacenDetalleEstadoModificarUbicacion)
                {
                    consulta="update INGRESOS_ALMACEN_DETALLE_ESTADO set  COD_ESTANTE='"+bean.getEstanteAlmacen().getCodEstante()+"',"+
                             " FILA='"+bean.getFila()+"',COLUMNA='"+bean.getColumna()+"'"//+",cod_ambiente='"+bean.getEstanteAlmacen().getAmbienteAlmacen().getCodAmbiente()+"'"
                            +" where COD_INGRESO_ALMACEN='"+bean.getIngresosAlmacen().getCodIngresoAlmacen()+"'"+
                             " and COD_MATERIAL='"+ingresosAlmacenDetalleEstadoEditar.getMateriales().getCodMaterial()+"'" +
                             " and ETIQUETA='"+bean.getEtiqueta()+"'";
                    System.out.println("consulta cambiar area ingresos "+consulta);
                    pst=con.prepareStatement(consulta);
                    if(pst.executeUpdate()>0)System.out.println("se registro el cambio para el ingreso");
                }
            }
            con.commit();
            mensaje="1";
            if(mensaje.equals("1"))
            {
                this.cargarIngresosUbicacion();
            }
            con.close();
        }
        catch (SQLException ex)
        {
            mensaje="Ocurrio un error al momento de realizar el cambio de ubicacion, intente de nuevo";
            con.rollback();
            con.close();
            ex.printStackTrace();
        }
        if(mensaje.equals("1"))
        {
            this.cargarIngresosUbicacion();
        }
        return null;
    }
    
    private void cargarAmbientes(int codAlmacen)
    {
        try {
            con = Util.openConnection(con);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String consulta = "select a.COD_AMBIENTE,a.NOMBRE_AMBIENTE from AMBIENTE_ALMACEN a" +
                              " where a.cod_almacen='"+codAlmacen+"'" +
                              "  order by a.NOMBRE_AMBIENTE";
            ResultSet res = st.executeQuery(consulta);
            ambientesList.clear();
            while (res.next()) {
                ambientesList.add(new SelectItem(res.getInt("COD_AMBIENTE"),res.getString("NOMBRE_AMBIENTE")));
            }
            res.close();
            st.close();
            con.close();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }
    public String  onchangeAmbiente_action()
    {
        estantesList = this.cargarEstantesAmbiente(ingresosAlmacenDetalleEstadoBuscar.getEstanteAlmacen().getAmbienteAlmacen().getCodAmbiente());
        ingresosAlmacenDetalleEstadoBuscar.getEstanteAlmacen().setCodEstante(0);
        return null;
    }
    private List<SelectItem> cargarEstantesAmbiente(int codAmbiente)
    {
        List<SelectItem> estantesSelectList = new ArrayList<>();
        try {
            con = Util.openConnection(con);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String consulta = "select ea.COD_ESTANTE,ea.NOMBRE_ESTANTE from ESTANTE_AMBIENTE ea" +
                              " where ea.COD_AMBIENTE='"+codAmbiente+"'"+
                              " order by ea.NOMBRE_ESTANTE";
            System.out.println("consulta estantes "+consulta );
            ResultSet res = st.executeQuery(consulta);
            
            while (res.next()) {
                estantesSelectList.add(new SelectItem(res.getInt("COD_ESTANTE"),res.getString("NOMBRE_ESTANTE")));
            }
            res.close();
            st.close();
            con.close();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return estantesSelectList;
    }
    public String onchangeTipoModificacionIngresos()
    {
        if(tipoMovimiento==2)
        {
            this.cargarIngresosDetalleEstadoSeccion();
        }
        return null;
    }
    public String onchangeAmbienteEditar_action()
    {
        System.out.println("cod ambiente J: "+ingresosAlmacenDetalleEstadoNuevaUbication.getEstanteAlmacen().getAmbienteAlmacen().getCodAmbiente());
        estantesList = this.cargarEstantesAmbiente(ingresosAlmacenDetalleEstadoNuevaUbication.getEstanteAlmacen().getAmbienteAlmacen().getCodAmbiente());
        return null;
    }
    public String getCargarNavegadorIngresosUbicacion()
    {
        this.cargarPermisoPersonal();
        ManagedAccesoSistema managed = (ManagedAccesoSistema)Util.getSessionBean("ManagedAccesoSistema");
        ingresosAlmacenDetalleEstadoBuscar.getIngresosAlmacen().getAlmacenes().setCodAlmacen(managed.getAlmacenesGlobal().getCodAlmacen());
        this.cargarCapitulosSelectList();
        this.cargarMaterialesSelectList();
        this.cargarAmbientes(ingresosAlmacenDetalleEstadoBuscar.getIngresosAlmacen().getAlmacenes().getCodAlmacen());
        estantesList =this.cargarEstantesAmbiente(ingresosAlmacenDetalleEstadoBuscar.getEstanteAlmacen().getAmbienteAlmacen().getCodAmbiente());
        if(ingresosAlmacenDetalleEstadoList.size()>0)this.cargarIngresosUbicacion();
        return null;
    }
    
    public String getCargarNavegadorSaldosUbicacion()
    {
        this.cargarMaterialesSelectList();
        estantesList = this.cargarEstantesAmbiente(4);
        if(ingresosAlmacenDetalleEstadoList.size()>0)this.cargarIngresosUbicacion();
        return null;
    }
    public String buscarIngresosUbicacion_action()
    {
        this.cargarIngresosUbicacion();
        return null;
    }
    
    private void cargarIngresosUbicacion()
    {
        try {
            con = Util.openConnection(con);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            StringBuilder consulta = new StringBuilder("select  * ")
                                                .append(" from VISTA_INGRESOS_ALMACEN_UBICACION viau ")
                                                .append(" where 1=1");  
                                                if(ingresosAlmacenDetalleEstadoBuscar.getIngresosAlmacen().getAlmacenes().getCodAlmacen() > 0)
                                                    consulta.append(" and viau.COD_ALMACEN = ").append(ingresosAlmacenDetalleEstadoBuscar.getIngresosAlmacen().getAlmacenes().getCodAlmacen());
                                                if(ingresosAlmacenDetalleEstadoBuscar.getEstanteAlmacen().getAmbienteAlmacen().getCodAmbiente() > 0)
                                                    consulta.append(" and viau.COD_AMBIENTE = '").append(ingresosAlmacenDetalleEstadoBuscar.getEstanteAlmacen().getAmbienteAlmacen().getCodAmbiente()).append("'");
                                                if(ingresosAlmacenDetalleEstadoBuscar.getEstanteAlmacen().getAmbienteAlmacen().getCodAmbiente() < 0)
                                                    consulta.append(" and (viau.COD_ESTANTE=0 or viau.COD_ESTANTE is null)");
                                                if(ingresosAlmacenDetalleEstadoBuscar.getEstanteAlmacen().getCodEstante() > 0)
                                                    consulta.append(" and viau.COD_ESTANTE = '").append(ingresosAlmacenDetalleEstadoBuscar.getEstanteAlmacen().getCodEstante()).append("'");
                                                if(!ingresosAlmacenDetalleEstadoBuscar.getFila().trim().equals(""))
                                                    consulta.append(" and viau.FILA = '").append(ingresosAlmacenDetalleEstadoBuscar.getFila()).append("'");
                                                if(!ingresosAlmacenDetalleEstadoBuscar.getColumna().trim().equals(""))
                                                    consulta.append(" and viau.COLUMNA = '").append(ingresosAlmacenDetalleEstadoBuscar.getColumna()).append("'");
                                                if(!ingresosAlmacenDetalleEstadoBuscar.getMateriales().getCodMaterial().equals("0"))
                                                    consulta.append(" and viau.COD_MATERIAL='").append(ingresosAlmacenDetalleEstadoBuscar.getMateriales().getCodMaterial()).append("'");
                                                if(ingresosAlmacenDetalleEstadoBuscar.getMateriales().getCodigoAntoguo().trim().length() != 0)
                                                    consulta.append(" and viau.COD_MATERIAL='").append(ingresosAlmacenDetalleEstadoBuscar.getMateriales().getCodigoAntoguo()).append("'");
                                                if(ingresosAlmacenDetalleEstadoBuscar.getMateriales().getGrupos().getCapitulos().getCodCapitulo() != 0)
                                                    consulta.append(" and viau.COD_CAPITULO = ").append(ingresosAlmacenDetalleEstadoBuscar.getMateriales().getGrupos().getCapitulos().getCodCapitulo());
                                                if(ingresosAlmacenDetalleEstadoBuscar.getMateriales().getGrupos().getCodGrupo() > 0)
                                                    consulta.append(" and viau.cod_grupo = ").append(ingresosAlmacenDetalleEstadoBuscar.getMateriales().getGrupos().getCodGrupo());
                                        consulta.append(" order by viau.nombre_ambiente,viau.NOMBRE_ESTANTE,viau.FILA,viau.COLUMNA,viau.nombre_material");
            System.out.println("consulta cargar ingresos "+consulta.toString());
            ResultSet res = st.executeQuery(consulta.toString());
            ingresosAlmacenDetalleEstadoList.clear();
            while (res.next())
            {
                IngresosAlmacenDetalleEstado nuevo=new IngresosAlmacenDetalleEstado();
                nuevo.getEstanteAlmacen().getAmbienteAlmacen().setNombreAmbiente(res.getString("NOMBRE_AMBIENTE"));
                nuevo.getEstanteAlmacen().getAmbienteAlmacen().setCodAmbiente(res.getInt("COD_AMBIENTE"));
                nuevo.getEstanteAlmacen().setNombreEstante(res.getString("NOMBRE_ESTANTE"));
                nuevo.getEstanteAlmacen().setCodEstante(res.getInt("COD_ESTANTE"));
                nuevo.setFila(res.getString("FILA"));
                nuevo.setColumna(res.getString("COLUMNA"));
                nuevo.getMateriales().setNombreMaterial(res.getString("NOMBRE_MATERIAL"));
                nuevo.getMateriales().setCodMaterial(res.getString("COD_MATERIAL"));
                nuevo.setCantidadRestante(res.getFloat("cantidad_restante"));
                nuevo.setLoteMaterialProveedor(res.getString("LOTE_MATERIAL_PROVEEDOR"));
                ingresosAlmacenDetalleEstadoList.add(nuevo);
            }
            res.close();
            st.close();
            con.close();
            System.out.println("lista canti J: "+ingresosAlmacenDetalleEstadoList.size());
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }


    public List<SelectItem> getAmbientesList() {
        return ambientesList;
    }

    public void setAmbientesList(List<SelectItem> ambientesList) {
        this.ambientesList = ambientesList;
    }

    public List<SelectItem> getEstantesList() {
        return estantesList;
    }

    public void setEstantesList(List<SelectItem> estantesList) {
        this.estantesList = estantesList;
    }


    public IngresosAlmacenDetalleEstado getIngresosAlmacenDetalleEstadoBuscar() {
        return ingresosAlmacenDetalleEstadoBuscar;
    }

    public void setIngresosAlmacenDetalleEstadoBuscar(IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstadoBuscar) {
        this.ingresosAlmacenDetalleEstadoBuscar = ingresosAlmacenDetalleEstadoBuscar;
    }

    public List<IngresosAlmacenDetalleEstado> getIngresosAlmacenDetalleEstadoList() {
        return ingresosAlmacenDetalleEstadoList;
    }

    public void setIngresosAlmacenDetalleEstadoList(List<IngresosAlmacenDetalleEstado> ingresosAlmacenDetalleEstadoList) {
        this.ingresosAlmacenDetalleEstadoList = ingresosAlmacenDetalleEstadoList;
    }

    public IngresosAlmacenDetalleEstado getIngresosAlmacenDetalleEstadoEditar() {
        return ingresosAlmacenDetalleEstadoEditar;
    }

    public void setIngresosAlmacenDetalleEstadoEditar(IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstadoEditar) {
        this.ingresosAlmacenDetalleEstadoEditar = ingresosAlmacenDetalleEstadoEditar;
    }

    public IngresosAlmacenDetalleEstado getIngresosAlmacenDetalleEstadoNuevaUbication() {
        return ingresosAlmacenDetalleEstadoNuevaUbication;
    }

    public void setIngresosAlmacenDetalleEstadoNuevaUbication(IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstadoNuevaUbication) {
        this.ingresosAlmacenDetalleEstadoNuevaUbication = ingresosAlmacenDetalleEstadoNuevaUbication;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public int getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(int tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public List<IngresosAlmacenDetalleEstado> getIngresosAlmacenDetalleEstadoModificarUbicacion() {
        return ingresosAlmacenDetalleEstadoModificarUbicacion;
    }

    public List<SelectItem> getCapitulosSelectList() {
        return capitulosSelectList;
    }

    public void setCapitulosSelectList(List<SelectItem> capitulosSelectList) {
        this.capitulosSelectList = capitulosSelectList;
    }

    public List<SelectItem> getGruposSelectList() {
        return gruposSelectList;
    }

    public void setGruposSelectList(List<SelectItem> gruposSelectList) {
        this.gruposSelectList = gruposSelectList;
    }
    

    public void setIngresosAlmacenDetalleEstadoModificarUbicacion(List<IngresosAlmacenDetalleEstado> ingresosAlmacenDetalleEstadoModificarUbicacion) {
        this.ingresosAlmacenDetalleEstadoModificarUbicacion = ingresosAlmacenDetalleEstadoModificarUbicacion;
    }

    public List<SelectItem> getMaterialesSelectList() {
        return materialesSelectList;
    }

    public void setMaterialesSelectList(List<SelectItem> materialesSelectList) {
        this.materialesSelectList = materialesSelectList;
    }

       HtmlDataTable saldosAlmacenDetalleDataTable = new HtmlDataTable(); 

    public HtmlDataTable getSaldosAlmacenDetalleDataTable() {
        return saldosAlmacenDetalleDataTable;
    }

    public void setSaldosAlmacenDetalleDataTable(HtmlDataTable saldosAlmacenDetalleDataTable) {
        this.saldosAlmacenDetalleDataTable = saldosAlmacenDetalleDataTable;
    }
    public String detallarItem_action(){
         try {
             ingresosAlmacenDetalleEstado = (IngresosAlmacenDetalleEstado)saldosAlmacenDetalleDataTable.getRowData();
             //aqui las unidades de medida
            
             
             
         } catch (Exception e) {
             e.printStackTrace();
         }
         return null;
     }
    
    private IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstado=new IngresosAlmacenDetalleEstado();

    public IngresosAlmacenDetalleEstado getIngresosAlmacenDetalleEstado() {
        return ingresosAlmacenDetalleEstado;
    }

    public void setIngresosAlmacenDetalleEstado(IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstado) {
        this.ingresosAlmacenDetalleEstado = ingresosAlmacenDetalleEstado;
    }

    public boolean isAdministradorAlmacen() {
        return administradorAlmacen;
    }

    public void setAdministradorAlmacen(boolean administradorAlmacen) {
        this.administradorAlmacen = administradorAlmacen;
    }

    public IngresosAlmacenDetalleEstado getIngresosAlmacenDetalleEstadoSeleccionado() {
        return ingresosAlmacenDetalleEstadoSeleccionado;
    }

    public void setIngresosAlmacenDetalleEstadoSeleccionado(IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstadoSeleccionado) {
        this.ingresosAlmacenDetalleEstadoSeleccionado = ingresosAlmacenDetalleEstadoSeleccionado;
    }
    
    
            
}

