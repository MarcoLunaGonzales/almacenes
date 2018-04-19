/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.web;

import com.cofar.bean.IngresosAlmacenDetalle;
import com.cofar.bean.IngresosAlmacenDetalleEstado;
import com.cofar.bean.Materiales;
import com.cofar.bean.SolicitudesSalida;
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
import javax.faces.model.SelectItem;
import org.joda.time.DateTime;

/**
 *
 * @author hvaldivia
 */

public class ManagedIngresoTraspasoAlmacen extends ManagedIngresoAlmacen {
    List traspasosList = new ArrayList();
    private ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
    Traspasos traspasos = new Traspasos();
    //inicio ale buscador
    private Traspasos traspasoBuscar= new Traspasos();
    private Date fechaInicioBuscar= new Date();
    private Date fechaFinalBuscar= new Date();
    private Materiales materialBuscar=new Materiales();
    private List<SelectItem> almacenesList= new ArrayList<SelectItem>();

    //final ale buscador


    public List getTraspasosList() {
        return traspasosList;
    }

    public void setTraspasosList(List traspasosList) {
        this.traspasosList = traspasosList;
    }


    public Traspasos getTraspasoBuscar() {
        return traspasoBuscar;
    }

    public void setTraspasoBuscar(Traspasos traspasoBuscar) {
        this.traspasoBuscar = traspasoBuscar;
    }

    public Date getFechaFinalBuscar() {
        return fechaFinalBuscar;
    }

    public void setFechaFinalBuscar(Date fechaFinalBuscar) {
        this.fechaFinalBuscar = fechaFinalBuscar;
    }

    public Date getFechaInicioBuscar() {
        return fechaInicioBuscar;
    }

    public void setFechaInicioBuscar(Date fechaInicioBuscar) {
        this.fechaInicioBuscar = fechaInicioBuscar;
    }

    public Materiales getMaterialBuscar() {
        return materialBuscar;
    }

    public void setMaterialBuscar(Materiales materialBuscar) {
        this.materialBuscar = materialBuscar;
    }

    public List<SelectItem> getAlmacenesList() {
        return almacenesList;
    }

    public void setAlmacenesList(List<SelectItem> almacenesList) {
        this.almacenesList = almacenesList;
    }



    
    

    /** Creates a new instance of ManagedIngresoTraspasoAlmacen */
    public ManagedIngresoTraspasoAlmacen() {
    }

     
    public String getCargarContenidoTraspasos() throws SQLException{
        this.cargarPermisoPersonal();
        try {
            //inicio ale buscador
            usuario=(ManagedAccesoSistema)Util.getSessionBean("ManagedAccesoSistema");
            fechaInicioBuscar=null;
            fechaFinalBuscar=null;
            materialBuscar= new Materiales();
            capitulosList=cargarCapitulos();
            gruposList.clear();
            gruposList.add(new SelectItem(0,"-TODOS-"));
            cargarGestiones();
            cargarAlmacenes();
            traspasoBuscar.getSalidasAlmacen().getGestiones().setCodGestion(usuario.getGestionesGlobal().getCodGestion());
            traspasoBuscar.getSalidasAlmacen().getGestiones().setCodGestion("0");
            traspasoBuscar.getAlmacenOrigen().setCodAlmacen(0);
            //final ale buscador
            this.cargarTraspasos();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void cargarTraspasos(){
        try {
            Connection con = null;
            con = Util.openConnection(con);
            //inicio ale buscador
            SimpleDateFormat sdf= new SimpleDateFormat("yyyy/MM/dd");
            //final ale buscador
            String consulta = " select s.COD_SALIDA_ALMACEN,s.NRO_SALIDA_ALMACEN,t.FECHA_TRASPASO,a.COD_ALMACEN,a.NOMBRE_ALMACEN,e.NOMBRE_ESTADO_TRASPASO " +
                    " from SALIDAS_ALMACEN s  " +
                    " inner join TRASPASOS t on t.COD_SALIDA_ALMACEN = s.COD_SALIDA_ALMACEN " +
                    " inner join ALMACENES a on a.COD_ALMACEN = t.COD_ALMACEN_ORIGEN " +
                    " inner join ESTADOS_TRASPASO e on e.COD_ESTADO_TRASPASO = t.COD_ESTADO_TRASPASO " +
                   //inicio ale buscador
                    " where  (t.COD_ESTADO_TRASPASO = 1 or t.COD_ESTADO_TRASPASO = 3 ) "
                    +"and t.cod_salida_almacen in( select distinct(t.COD_SALIDA_ALMACEN)" +
" from TRASPASOS t" +
" inner join SALIDAS_ALMACEN_DETALLE sad " +
" on t.COD_SALIDA_ALMACEN=sad.COD_SALIDA_ALMACEN" +
" left join (" +
" select COD_SALIDA_ALMACEN, COD_MATERIAL " +
" from TRASPASO_INGRESO ti " +
" inner join INGRESOS_ALMACEN_DETALLE iad" +
" on ti.COD_INGRESO_ALMACEN=iad.COD_INGRESO_ALMACEN" +
" )tin on sad.COD_SALIDA_ALMACEN=tin.COD_SALIDA_ALMACEN" +
" and sad.COD_MATERIAL=tin.COD_MATERIAL" +
" where tin.COD_SALIDA_ALMACEN is null) ";
                    
            
            if(!traspasoBuscar.getSalidasAlmacen().getGestiones().getCodGestion().equals("0"))
                    {
                        consulta+=" and  s.COD_GESTION = '"+traspasoBuscar.getSalidasAlmacen().getGestiones().getCodGestion()+"' " ;
                    }
                    if(traspasoBuscar.getAlmacenOrigen().getCodAlmacen()!=0)
                    {
                        consulta+=" and t.COD_ALMACEN_ORIGEN='"+traspasoBuscar.getAlmacenOrigen().getCodAlmacen()+"'";
                    }
                    if(traspasoBuscar.getSalidasAlmacen().getNroSalidaAlmacen()>0)
                    {
                        consulta+=" and s.NRO_SALIDA_ALMACEN='"+traspasoBuscar.getSalidasAlmacen().getNroSalidaAlmacen()+"'";
                    }
                    if(fechaInicioBuscar!=null&&fechaFinalBuscar!=null)
                    {
                        consulta+=" and s.FECHA_SALIDA_ALMACEN BETWEEN '"+sdf.format(fechaInicioBuscar)+" 00:00:00' and '"+sdf.format(fechaFinalBuscar)+" 23:59:59'";
                    }
                    if(materialBuscar.getGrupos().getCapitulos().getCodCapitulo()>0||materialBuscar.getGrupos().getCodGrupo()>0||(!materialBuscar.getNombreMaterial().equals("")))
                    {
                        consulta+=" and s.COD_SALIDA_ALMACEN IN (select sad.COD_SALIDA_ALMACEN from SALIDAS_ALMACEN_DETALLE sad inner join MATERIALES m on m.COD_MATERIAL=sad.COD_MATERIAL"+
                                  " inner join GRUPOS g on g.COD_GRUPO=m.COD_GRUPO where 1=1"+
                                  (materialBuscar.getNombreMaterial().equals("")?"":" and m.NOMBRE_MATERIAL like '%"+materialBuscar.getNombreMaterial()+"%'")+
                                  (materialBuscar.getGrupos().getCodGrupo()==0?"":" and m.COD_GRUPO='"+materialBuscar.getGrupos().getCodGrupo()+"'")+
                                  (materialBuscar.getGrupos().getCapitulos().getCodCapitulo()==0?"":" and g.COD_CAPITULO='"+materialBuscar.getGrupos().getCapitulos().getCodCapitulo()+"'")+")";
                    }

                    consulta+=" and t.COD_ALMACEN_DESTINO = '"+usuario.getAlmacenesGlobal().getCodAlmacen()+"' and s.COD_ESTADO_SALIDA_ALMACEN<>2" +
                    " order by t.FECHA_TRASPASO asc ";
                    System.out.println("consulta " + consulta);
                    //final ale buscador

            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            
            traspasosList.clear();
            while(rs.next()){
                Traspasos traspasos = new Traspasos();
                traspasos.getSalidasAlmacen().setCodSalidaAlmacen(rs.getInt("COD_SALIDA_ALMACEN"));
                traspasos.getSalidasAlmacen().setNroSalidaAlmacen(rs.getInt("NRO_SALIDA_ALMACEN"));
                traspasos.setFechaTraspaso(rs.getDate("FECHA_TRASPASO"));
                traspasos.getAlmacenOrigen().setCodAlmacen(rs.getInt("COD_ALMACEN"));
                traspasos.getAlmacenOrigen().setNombreAlmacen(rs.getString("NOMBRE_ALMACEN"));
                traspasos.getEstadosTraspaso().setNombreEstadoTraspaso(rs.getString("NOMBRE_ESTADO_TRASPASO"));
                traspasosList.add(traspasos);
            }
            rs.close();
            st.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     //inicio ale buscador
     public String buscarTraspaso_Action()
     {
         this.cargarTraspasos();
         return null;
     }
     public void cargarAlmacenes()
     {
         try
         {
             Connection con=null;
             con =Util.openConnection(con);
             Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
             String consulta="select a.COD_ALMACEN,a.NOMBRE_ALMACEN from ALMACENES a where a.COD_ESTADO_REGISTRO=1 order by a.NOMBRE_ALMACEN";
             ResultSet res=st.executeQuery(consulta);
             almacenesList.clear();
             almacenesList.add(new SelectItem(0,"-TODOS-"));
             while(res.next())
             {
                 almacenesList.add(new SelectItem(res.getInt("COD_ALMACEN"),res.getString("NOMBRE_ALMACEN")));
             }
             res.close();
             st.close();
             con.close();
         }
         catch(Exception ex)
         {
             ex.printStackTrace();
         }
     }
     public String capitulosTraspaso_change()
     {
         try
         {
             Connection con =null;
             con=Util.openConnection(con);
             Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
             String consulta="select g.NOMBRE_GRUPO,g.COD_GRUPO from grupos g where g.COD_CAPITULO='"+materialBuscar.getGrupos().getCapitulos().getCodCapitulo()+"' order by g.NOMBRE_GRUPO";
             System.out.println("consulta change cap "+consulta);
             ResultSet res=st.executeQuery(consulta);
             gruposList.clear();
             gruposList.add(new SelectItem(0,"-TODOS-"));
             while(res.next())
             {
                 gruposList.add(new SelectItem(res.getInt("COD_GRUPO"),res.getString("NOMBRE_GRUPO")));
             }
             res.close();
             st.close();
             con.close();
         }
         catch(Exception ex)
         {
             ex.printStackTrace();
         }
         return null;
     }
     //final ale buscador

    public String aceptarTraspaso_action(){
        try {
            mensaje = "";
            Iterator i = traspasosList.iterator();
            while(i.hasNext()){
                Traspasos traspasosItem = (Traspasos)i.next();
                if(traspasosItem.getChecked().booleanValue()==true){
                    traspasos= traspasosItem;
                    break;
                }
            }
            //validacion para el traspaso si los items corresponden al almacen
            if(this.validaItemsAlmacen(traspasos)==false){
                mensaje = " todos los materiales deben estar admitidos en el almacen ";
                return null;
            }

            //llenado de datos de ingreso a almacen a partir de la salida de almacen
            


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void llenaIngresoAlmacenTraspaso(Traspasos traspasos){
        try {
            Connection con = null;
            con = Util.openConnection(con);
            //llenado del detalle
            String consulta = " SELECT s.COD_SALIDA_ALMACEN,s.COD_MATERIAL,s.CANTIDAD_SALIDA_ALMACEN,s.COD_UNIDAD_MEDIDA,s.COD_ESTADO_MATERIAL, " +
                    " m.NOMBRE_MATERIAL,u.COD_UNIDAD_MEDIDA,u.NOMBRE_UNIDAD_MEDIDA,u.ABREVIATURA,e.NOMBRE_ESTADO_MATERIAL," +
                    " ( select top 1 s.cod_seccion cod_seccion " +
                                    " from secciones s,secciones_detalle sd, almacenes a " +
                                    " where s.cod_seccion=sd.cod_seccion and s.cod_almacen='"+usuario.getAlmacenesGlobal().getCodAlmacen()+"' " +
                                    " and s.cod_estado_registro=1 and a.cod_almacen=s.cod_almacen  " +
                                    " and ((sd.cod_material=m.COD_MATERIAL and s.estado_sistema=1) " +
                                    " or (sd.cod_material=0 and sd.cod_grupo=gr.COD_GRUPO) " +
                                    " or (sd.cod_material=0 and sd.cod_grupo=0 and sd.cod_capitulo=c.COD_CAPITULO)) ) seccion " +
                    " FROM SALIDAS_ALMACEN_DETALLE s " +
                    " inner join MATERIALES m on m.COD_MATERIAL = s.COD_MATERIAL " +
                    " inner join GRUPOS gr on gr.COD_GRUPO = m.COD_GRUPO" +
                    " inner join CAPITULOS c on c.COD_CAPITULO = gr.COD_CAPITULO " +
                    " inner join UNIDADES_MEDIDA u on u.COD_UNIDAD_MEDIDA = s.COD_UNIDAD_MEDIDA " +
                    " inner join ESTADOS_MATERIAL e on e.COD_ESTADO_MATERIAL = s.COD_ESTADO_MATERIAL " +
                    " where s.COD_SALIDA_ALMACEN = '"+traspasos.getSalidasAlmacen().getCodSalidaAlmacen()+"' "
                    + " and s.cod_material not in (select iad.cod_material "
                    + "from ingresos_almacen_detalle iad inner join traspaso_ingreso ti"
                    + " on ti.cod_ingreso_almacen=iad.cod_ingreso_almacen"
                    + " where ti.COD_SALIDA_ALMACEN = '"+traspasos.getSalidasAlmacen().getCodSalidaAlmacen()+"' )";
            System.out.println("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            ingresosAlmacenDetalleList.clear();
            while(rs.next()){
                IngresosAlmacenDetalle ingresosAlmacenDetalleItem = new IngresosAlmacenDetalle();
                ingresosAlmacenDetalleItem.getMateriales().setCodMaterial(rs.getString("COD_MATERIAL"));
                ingresosAlmacenDetalleItem.getMateriales().setNombreMaterial(rs.getString("NOMBRE_MATERIAL"));
                ingresosAlmacenDetalleItem.setCantTotalIngresoFisico(rs.getFloat("CANTIDAD_SALIDA_ALMACEN"));
                ingresosAlmacenDetalleItem.getUnidadesMedida().setCodUnidadMedida(rs.getInt("COD_UNIDAD_MEDIDA"));
                ingresosAlmacenDetalleItem.getUnidadesMedida().setAbreviatura(rs.getString("ABREVIATURA"));
                ingresosAlmacenDetalleItem.getUnidadesMedida().setNombreUnidadMedida(rs.getString("NOMBRE_UNIDAD_MEDIDA"));                
                ingresosAlmacenDetalleItem.setIngresosAlmacenDetalleEstadoList(this.llenaIngresoAlmacenDetalleEstado(traspasos,ingresosAlmacenDetalleItem));
                ingresosAlmacenDetalleItem.setCantEnviadaTraspaso(rs.getFloat("CANTIDAD_SALIDA_ALMACEN"));
                ingresosAlmacenDetalleItem.getUnidadesMedidaTraspaso().setCodUnidadMedida(rs.getInt("COD_UNIDAD_MEDIDA"));
                ingresosAlmacenDetalleItem.getUnidadesMedidaTraspaso().setAbreviatura(rs.getString("ABREVIATURA"));
                ingresosAlmacenDetalleItem.getUnidadesMedidaTraspaso().setNombreUnidadMedida(rs.getString("NOMBRE_UNIDAD_MEDIDA"));
                ingresosAlmacenDetalleItem.getSecciones().setCodSeccion(rs.getInt("seccion"));
                
                ingresosAlmacenDetalleList.add(ingresosAlmacenDetalleItem);
            }
            st.close();
            rs.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public List llenaIngresoAlmacenDetalleEstado(Traspasos traspasos, IngresosAlmacenDetalle ingresosAlmacenDetalle){
        List ingresosAlmacenDetalleEstadoList = new ArrayList();
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " SELECT s.ETIQUETA,e.COD_ESTADO_MATERIAL,e.NOMBRE_ESTADO_MATERIAL,s.CANTIDAD,i.FECHA_MANUFACTURA,i.LOTE_MATERIAL_PROVEEDOR,i.FECHA_VENCIMIENTO,i.FECHA_REANALISIS,i.OBSERVACIONES, " +
                    " ese.COD_EMPAQUE_SECUNDARIO_EXTERNO,ese.NOMBRE_EMPAQUE_SECUNDARIO_EXTERNO " +
                    "FROM SALIDAS_ALMACEN_DETALLE_INGRESO s " +
                    " inner join INGRESOS_ALMACEN_DETALLE_ESTADO i on s.COD_MATERIAL = i.COD_MATERIAL and s.ETIQUETA = i.ETIQUETA and s.COD_INGRESO_ALMACEN = i.COD_INGRESO_ALMACEN " +
                    " inner join ESTADOS_MATERIAL e on e.COD_ESTADO_MATERIAL = i.COD_ESTADO_MATERIAL " +
                    " inner join EMPAQUES_SECUNDARIO_EXTERNO ese on ese.COD_EMPAQUE_SECUNDARIO_EXTERNO = i.COD_EMPAQUE_SECUNDARIO_EXTERNO " +
                    " where s.COD_SALIDA_ALMACEN = '"+traspasos.getSalidasAlmacen().getCodSalidaAlmacen()+"' " +
                    " and s.COD_MATERIAL = '"+ingresosAlmacenDetalle.getMateriales().getCodMaterial()+"'";
            
            System.out.println("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            ingresosAlmacenDetalleEstadoList.clear();
            //int cont = 1;//temporal
            int cont = 1 ;
            while(rs.next()){
                IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstadoItem = new IngresosAlmacenDetalleEstado();
                ingresosAlmacenDetalleEstadoItem.setEtiqueta(rs.getInt("ETIQUETA"));
                //ingresosAlmacenDetalleEstadoItem.setEtiqueta(cont);
                //ingresosAlmacenDetalleEstadoItem.setEtiqueta(cont);
                ingresosAlmacenDetalleEstadoItem.getEstadosMaterial().setCodEstadoMaterial(rs.getInt("COD_ESTADO_MATERIAL"));
                ingresosAlmacenDetalleEstadoItem.getEstadosMaterial().setNombreEstadoMaterial(rs.getString("NOMBRE_ESTADO_MATERIAL"));
                ingresosAlmacenDetalleEstadoItem.setCantidadParcial(rs.getFloat("CANTIDAD"));
                ingresosAlmacenDetalleEstadoItem.setLoteMaterialProveedor(rs.getString("LOTE_MATERIAL_PROVEEDOR"));
                
                ingresosAlmacenDetalleEstadoItem.getEmpaqueSecundarioExterno().setCodEmpaqueSecundarioExterno(rs.getInt("COD_EMPAQUE_SECUNDARIO_EXTERNO"));
                ingresosAlmacenDetalleEstadoItem.getEmpaqueSecundarioExterno().setNombreEmpaqueSecundarioExterno(rs.getString("NOMBRE_EMPAQUE_SECUNDARIO_EXTERNO"));
                ingresosAlmacenDetalleEstadoItem.setFechaManufactura(rs.getDate("FECHA_MANUFACTURA"));
                ingresosAlmacenDetalleEstadoItem.setFechaReanalisis(rs.getDate("FECHA_REANALISIS"));
                ingresosAlmacenDetalleEstadoItem.setFechaVencimiento(rs.getDate("FECHA_VENCIMIENTO"));
                ingresosAlmacenDetalleEstadoItem.setObservaciones(rs.getString("OBSERVACIONES"));
                if(ingresosAlmacenDetalleEstadoItem.getCantidadParcial()>0){
                    ingresosAlmacenDetalleEstadoList.add(ingresosAlmacenDetalleEstadoItem);
                    //cont++;//temporal
                    cont ++;
                }
            }
            

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ingresosAlmacenDetalleEstadoList;
    }

    public boolean validaItemsAlmacen(Traspasos traspasos){
        //si no cumplen todos los items no se registra el ingreso

        boolean validaItems = true;
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select s.COD_SALIDA_ALMACEN,m.COD_MATERIAL , m.NOMBRE_MATERIAL,gr.COD_GRUPO," +
                             " gr.NOMBRE_GRUPO,c.COD_CAPITULO,c.NOMBRE_CAPITULO,u.COD_UNIDAD_MEDIDA,u.NOMBRE_UNIDAD_MEDIDA,s.CANTIDAD_SALIDA_ALMACEN, " +
                             " ( select top 1 s.cod_seccion cod_seccion " +
                                    " from secciones s,secciones_detalle sd, almacenes a " +
                                    " where s.cod_seccion=sd.cod_seccion and s.cod_almacen='"+usuario.getAlmacenesGlobal().getCodAlmacen()+"' " +
                                    " and s.cod_estado_registro=1 and a.cod_almacen=s.cod_almacen  " +
                                    " and ((sd.cod_material=m.COD_MATERIAL and s.estado_sistema=1) " +
                                    " or (sd.cod_material=0 and sd.cod_grupo=gr.COD_GRUPO) " +
                                    " or (sd.cod_material=0 and sd.cod_grupo=0 and sd.cod_capitulo=c.COD_CAPITULO)) ) seccion " +
                             " from SALIDAS_ALMACEN_DETALLE s  " +
                             " inner join MATERIALES m on m.COD_MATERIAL = s.COD_MATERIAL " +
                             " inner join GRUPOS gr on gr.COD_GRUPO = m.COD_GRUPO " +
                             " inner join CAPITULOS c on c.COD_CAPITULO = gr.COD_CAPITULO " +
                             " inner join UNIDADES_MEDIDA u on u.COD_UNIDAD_MEDIDA = s.COD_UNIDAD_MEDIDA " +
                             " where s.COD_SALIDA_ALMACEN = '"+traspasos.getSalidasAlmacen().getCodSalidaAlmacen()+"' ";
            System.out.println("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);

            while(rs.next()){
                System.out.println("la seccion " + rs.getInt("seccion") );
                if(rs.getInt("seccion")==0){
                    validaItems = false;
                    break;
                }
            }

            st.close();
            rs.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return validaItems;
    }


     public String getCargarAgregarIngresosAlmacenTraspaso(){
        try {
            ingresosAlmacen.setGestiones(usuario.getGestionesGlobal());
            ingresosAlmacen.setFechaIngresoAlmacen(new Date());
            ingresosAlmacen.setNroIngresoAlmacen(obtieneNroIngresoAlmacen(usuario));
            ingresosAlmacen.getTiposIngresoAlmacen().setCodTipoIngresoAlmacen(3);
            ingresosAlmacen.getTiposCompra().setCodTipoCompra("0");
            ingresosAlmacen.setCreditoFiscalSiNo(0);
            tiposIngresosAlmacenList = cargarTiposIngresoAlmacen();
            ingresosAlmacen.getEstadosIngresoAlmacen().setCodEstadoIngresoAlmacen(1);
            tiposCompraList = cargarTiposCompra();
            proveedoresList = obtieneProveedores();
            capitulosList = this.cargarCapitulos();
            gruposList.add(new SelectItem("0", "-NINGUNO-"));
            this.cargarEmpaques();

            ingresosAlmacenDetalleList.clear();

            this.llenaIngresoAlmacenTraspaso(traspasos);
            ingresosAlmacen.getTiposIngresoAlmacen().setCodTipoIngresoAlmacen(3);
            this.ambienteAlmacenList = this.cargarAmbienteAlmacen();
            this.estanteAlmacenList = this.cargarEstanteAlmacen();

            //cargar los datos concernientes a ingreso a almacen por traspaso

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
     public String guardarIngresoAlmacenTraspaso_action()throws SQLException
     {
         Connection con=null;
         mensaje="";
         try 
         {
             StringBuilder consulta=new StringBuilder("select count(*) as cantidadTraspasosAceptados");
                                    consulta.append(" from TRASPASOS t");
                                    consulta.append(" where t.COD_SALIDA_ALMACEN=").append(traspasos.getSalidasAlmacen().getCodSalidaAlmacen());
                                    consulta.append(" and t.COD_INGRESO_ALMACEN>0");
             System.out.println("consulta verificar si el traspaso ya fue ingresado "+consulta.toString());
             con=Util.openConnection(con);
             con.setAutoCommit(false);
             Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
             ResultSet res=st.executeQuery(consulta.toString());
             int cantidadIngresos=0;
             if(res.next())cantidadIngresos=res.getInt("cantidadTraspasosAceptados");
             //if(cantidadIngresos==0) Jaime -- ingresan todos
             if(cantidadIngresos>=0)
             {
                 SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                 // <editor-fold defaultstate="collapsed" desc="verificando validaciones de ingreso">
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
                     if(ingresosAlmacen.getTiposIngresoAlmacen().getCodTipoIngresoAlmacen()!=8&&ingresosAlmacen.getTiposIngresoAlmacen().getCodTipoIngresoAlmacen()!=3&&ingresosAlmacen.getProveedores().getCodProveedor().equals("0")){
                         mensaje = " Registre el proveedor ";
                         return null;
                     }
                     Iterator j = ingresosAlmacenDetalleList.iterator();
                       while(j.hasNext())
                       {
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
                 //</editor-fold>
                 // <editor-fold defaultstate="collapsed" desc="generando ingresos por traspaso">
                 consulta=new StringBuilder("select (isnull(max(cod_ingreso_almacen),0)+1) cod_ingreso_almacen  ");
                             consulta.append(" from ingresos_almacen ");
                 res=st.executeQuery(consulta.toString());
                 if(res.next())ingresosAlmacen.setCodIngresoAlmacen(res.getInt("cod_ingreso_almacen"));
                 ingresosAlmacen.getEstadosIngresoAlmacen().setCodEstadoIngresoAlmacen(1);
                 ingresosAlmacen.setFechaIngresoAlmacen(new Date());
                 consulta=new StringBuilder("select isnull(max(ia.NRO_INGRESO_ALMACEN),0)+1 as NroIngreso");
                            consulta.append(" from INGRESOS_ALMACEN ia");
                            consulta.append(" where ia.COD_ALMACEN=").append(usuario.getAlmacenesGlobal().getCodAlmacen());
                                consulta.append(" and ia.COD_GESTION=").append(usuario.getGestionesGlobal().getCodGestion());
                 res=st.executeQuery(consulta.toString());
                 if(res.next())ingresosAlmacen.setNroIngresoAlmacen(res.getInt("NroIngreso"));
                 ingresosAlmacen.getTiposDocumentos().setCodTipoDocumento(ingresosAlmacen.getCreditoFiscalSiNo()==1?"1":"2");
                 ingresosAlmacen.getPersonal().setCodPersonal(usuario.getUsuarioModuloBean().getCodUsuarioGlobal());
                 ingresosAlmacen.getEstadosIngresosLiquidacion().setCodEstadoIngresoLiquidacion(2); //no liquidado
                 ingresosAlmacen.setAlmacenes(usuario.getAlmacenesGlobal());
                 ingresosAlmacen.setEstadoSistema(1);
                 consulta = new StringBuilder(" insert into ingresos_almacen (cod_ingreso_almacen,nro_ingreso_almacen,cod_tipo_ingreso_almacen, cod_orden_compra,cod_gestion,cod_estado_ingreso_almacen,");
                         consulta.append(" credito_fiscal_si_no,cod_proveedor,cod_tipo_compra,estado_sistema,cod_almacen,cod_devolucion,fecha_ingreso_almacen,");
                         consulta.append(" cod_tipo_documento,cod_personal,cod_estado_ingreso_liquidacion,obs_ingreso_almacen,NRO_DOCUMENTO)  ");
                         consulta.append(" values  (");
                             consulta.append(ingresosAlmacen.getCodIngresoAlmacen()).append(",");
                             consulta.append(ingresosAlmacen.getNroIngresoAlmacen()).append(",");
                             consulta.append("3,");//ingreso por traspaso
                             consulta.append("0,");//orden de compra 0 por ser ingreso por traspaso
                             consulta.append(usuario.getGestionesGlobal().getCodGestion()).append(",");
                             consulta.append(ingresosAlmacen.getEstadosIngresoAlmacen().getCodEstadoIngresoAlmacen()).append(",");
                             consulta.append("'").append(ingresosAlmacen.getCreditoFiscalSiNo()).append("', ");
                             consulta.append("'").append(ingresosAlmacen.getProveedores().getCodProveedor()).append("', ");
                             consulta.append("'").append(ingresosAlmacen.getTiposCompra().getCodTipoCompra()).append("',");
                             consulta.append("'").append(ingresosAlmacen.getEstadoSistema()).append("', " );
                             consulta.append(usuario.getAlmacenesGlobal().getCodAlmacen()).append(",");
                             consulta.append(" 0 ,");
                             consulta.append("'").append(sdf.format(ingresosAlmacen.getFechaIngresoAlmacen())).append("',");
                             consulta.append("'").append(ingresosAlmacen.getTiposDocumentos().getCodTipoDocumento()).append("',");
                             consulta.append("'").append(ingresosAlmacen.getPersonal().getCodPersonal()).append("', ");
                             consulta.append("'").append(ingresosAlmacen.getEstadosIngresosLiquidacion().getCodEstadoIngresoLiquidacion()).append("',");
                             consulta.append("'").append(ingresosAlmacen.getObsIngresoAlmacen()).append("',");
                             consulta.append("'").append(ingresosAlmacen.getNroDocumento()).append("'");
                         consulta.append(")");
                   System.out.println("consulta guardar ingreso" + consulta.toString());
                   PreparedStatement pst=con.prepareStatement(consulta.toString());
                   if(pst.executeUpdate()>0)System.out.println("se registro el ingreso");
                   Iterator i = ingresosAlmacenDetalleList.iterator();
                   while(i.hasNext())
                   {
                       IngresosAlmacenDetalle ingresosAlmacenDetalleItem = (IngresosAlmacenDetalle)i.next();
                       consulta =new StringBuilder(" insert into ingresos_almacen_detalle (cod_material,cod_ingreso_almacen,cod_seccion,nro_unidades_empaque, ");
                               consulta.append(" cant_total_ingreso,cant_total_ingreso_fisico,cod_unidad_medida,precio_total_material, precio_unitario_material,costo_unitario,observacion,costo_promedio,precio_neto)");
                               consulta.append(" values(");
                                   consulta.append("'").append(ingresosAlmacenDetalleItem.getMateriales().getCodMaterial()).append("',");
                                   consulta.append(ingresosAlmacen.getCodIngresoAlmacen()).append(",");
                                   consulta.append(" '").append(ingresosAlmacenDetalleItem.getSecciones().getCodSeccion()).append("',");
                                   consulta.append(" '").append(ingresosAlmacenDetalleItem.getNroUnidadesEmpaque()).append("',");
                                   consulta.append(" '").append(ingresosAlmacenDetalleItem.getCantTotalIngreso()).append("',");
                                   consulta.append(" '").append(ingresosAlmacenDetalleItem.getCantTotalIngresoFisico()).append("',");
                                   consulta.append(" '").append(ingresosAlmacenDetalleItem.getUnidadesMedida().getCodUnidadMedida()).append("'," );
                                   consulta.append(" '").append(ingresosAlmacenDetalleItem.getPrecioTotalMaterial()).append("',");
                                   consulta.append(" '").append(ingresosAlmacenDetalleItem.getPrecioUnitarioMaterial()).append("',");
                                   consulta.append(" '").append(ingresosAlmacenDetalleItem.getCostoUnitario()).append("',");
                                   consulta.append(" '").append(ingresosAlmacenDetalleItem.getObservacion()).append("',");
                                   consulta.append(" '").append(ingresosAlmacenDetalleItem.getCostoPromedio()).append("',");
                                   consulta.append(" '").append(ingresosAlmacenDetalleItem.getPrecioNeto()).append("'");
                               consulta.append(")");
                       System.out.println("consulta registrar detalle " + consulta.toString());
                       pst=con.prepareStatement(consulta.toString());
                       if(pst.executeUpdate()>0)System.out.println("se registro el detalle");
                       Iterator iterator = ingresosAlmacenDetalleItem.getIngresosAlmacenDetalleEstadoList().iterator();
                       int etiqueta=1;
                       while(iterator.hasNext())
                       {
                           IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstadoItem = (IngresosAlmacenDetalleEstado)iterator.next();
                           ingresosAlmacenDetalleEstadoItem.setCantidadRestante(ingresosAlmacenDetalleEstadoItem.getCantidadParcial());
                           //en caso de proveedor cofar deben ingresar como aprobados
                           if(ingresosAlmacen.getProveedores().getCodProveedor().equals("160"))
                           {
                               System.out.println("proveedor cofar");
                               ingresosAlmacenDetalleEstadoItem.getEstadosMaterial().setCodEstadoMaterial(2);
                           }

                           consulta = new StringBuilder(" insert into ingresos_almacen_detalle_estado (etiqueta,cod_ingreso_almacen,cod_material,cod_estado_material, cod_empaque_secundario_externo,cantidad_parcial,cantidad_restante, ");
                                       consulta.append(" fecha_vencimiento,lote_material_proveedor,lote_interno,fecha_manufactura,fecha_reanalisis,observaciones,obs_control_calidad,tara,cod_estante,fila,columna,densidad, etiqueta_salida)");
                                       consulta.append(" values(");
                                           consulta.append(" '").append(etiqueta).append("', ");
                                           consulta.append(ingresosAlmacen.getCodIngresoAlmacen()).append(",");
                                           consulta.append(" '").append(ingresosAlmacenDetalleItem.getMateriales().getCodMaterial()).append("',");
                                           consulta.append(" '").append(ingresosAlmacenDetalleEstadoItem.getEstadosMaterial().getCodEstadoMaterial()).append("',");
                                           consulta.append(" '").append(ingresosAlmacenDetalleEstadoItem.getEmpaqueSecundarioExterno().getCodEmpaqueSecundarioExterno()).append("',");
                                           consulta.append(" '").append(ingresosAlmacenDetalleEstadoItem.getCantidadParcial()).append("',");
                                           consulta.append(" '").append(ingresosAlmacenDetalleEstadoItem.getCantidadRestante()).append("',");
                                           if(ingresosAlmacenDetalleEstadoItem.getFechaVencimiento() != null)
                                                consulta.append(" '").append(sdf.format(ingresosAlmacenDetalleEstadoItem.getFechaVencimiento())).append("',");
                                           else
                                               consulta.append("null,");
                                           consulta.append(" '").append(ingresosAlmacenDetalleEstadoItem.getLoteMaterialProveedor()).append("',");
                                           consulta.append(" '").append(ingresosAlmacenDetalleEstadoItem.getLoteInterno()).append("',");
                                           consulta.append("'").append(ingresosAlmacenDetalleEstadoItem.getFechaManufactura()!=null?sdf.format(ingresosAlmacenDetalleEstadoItem.getFechaManufactura()):"").append("',");
                                           consulta.append("'").append(ingresosAlmacenDetalleEstadoItem.getFechaReanalisis()!=null?sdf.format(ingresosAlmacenDetalleEstadoItem.getFechaReanalisis()):"").append("',");
                                           consulta.append(" '").append(ingresosAlmacenDetalleEstadoItem.getObservaciones()).append("',");
                                           consulta.append(" '").append(ingresosAlmacenDetalleEstadoItem.getObsControlCalidad()).append("',");
                                           consulta.append(" '").append(ingresosAlmacenDetalleEstadoItem.getTara()).append("',");
                                           consulta.append(" '").append(ingresosAlmacenDetalleEstadoItem.getEstanteAlmacen().getCodEstante()).append("',");
                                           consulta.append(" '").append(ingresosAlmacenDetalleEstadoItem.getFila()).append("',");
                                           consulta.append(" '").append(ingresosAlmacenDetalleEstadoItem.getColumna()).append("',");
                                           consulta.append("'").append(ingresosAlmacenDetalleEstadoItem.getDensidad()).append("',");
                                           consulta.append(ingresosAlmacenDetalleEstadoItem.getEtiqueta());
                                       consulta.append(")");
                           System.out.println("consulta registrar etiqueta" + consulta.toString());
                           pst=con.prepareStatement(consulta.toString());
                           if(pst.executeUpdate()>0)System.out.println("se registro la etiqueta");
                           etiqueta++;
                       }
                   }
                //REGISTRO DE LOGS DEL NUEVO INGRESO
                consulta = new StringBuilder(" exec [PAA_REGISTRO_INGRESO_ALMACEN_LOG] ").append(ingresosAlmacen.getCodIngresoAlmacen())  
                        .append(" , ").append(usuario.getUsuarioModuloBean().getCodUsuarioGlobal())
                        .append(" , 3");                    
                System.out.println("consulta ejecutar: " + consulta); 
                pst=con.prepareStatement(consulta.toString());
                pst.executeUpdate();       
                 //</editor-fold>
                 consulta = new StringBuilder(" update traspasos");
                             consulta.append(" set cod_ingreso_almacen = ").append(ingresosAlmacen.getCodIngresoAlmacen());
                             consulta.append(" where cod_salida_almacen =").append(traspasos.getSalidasAlmacen().getCodSalidaAlmacen());
                 System.out.println("consulta registrar traspaso" + consulta.toString());
                 pst=con.prepareStatement(consulta.toString());
                 if(pst.executeUpdate()>0)System.out.println("se actualizo el traspaso");
                 // <editor-fold defaultstate="collapsed" desc="verificando la cantidad ingresada del traspaso">
                 consulta = new StringBuilder(" INSERT INTO OBSERVACIONES_TRASPASO ( COD_SALIDA_ALMACEN, COD_MATERIAL, CANTIDAD_SALIDA_ORIGEN,CANTIDAD_INGRESO_DESTINO, OBSERVACIONES )");
                            consulta.append("VALUES ( ").append(traspasos.getSalidasAlmacen().getCodSalidaAlmacen()).append(",?,?,?,'')");
                 pst=con.prepareStatement(consulta.toString());
                 consulta = new StringBuilder(" select s.CANTIDAD_SALIDA_ALMACEN,i.CANT_TOTAL_INGRESO_FISICO,s.COD_MATERIAL ");
                             consulta.append(" from TRASPASOS t ");
                                 consulta.append(" inner join SALIDAS_ALMACEN_DETALLE s on s.COD_SALIDA_ALMACEN = t.COD_SALIDA_ALMACEN ");
                                 consulta.append(" inner join INGRESOS_ALMACEN_DETALLE i on i.COD_INGRESO_ALMACEN = t.COD_INGRESO_ALMACEN and s.COD_MATERIAL = i.COD_MATERIAL ");
                             consulta.append(" where s.COD_SALIDA_ALMACEN =").append(traspasos.getSalidasAlmacen().getCodSalidaAlmacen());
                             consulta.append(" and i.COD_INGRESO_ALMACEN = ").append(ingresosAlmacen.getCodIngresoAlmacen());
                 System.out.println("consulta cantidad ingresos vs salida por traspaso" + consulta.toString());
                 res=st.executeQuery(consulta.toString());
                 boolean observacionTraspaso=false;
                 while(res.next())
                 {
                     if(res.getFloat("CANTIDAD_SALIDA_ALMACEN")!=res.getFloat("CANT_TOTAL_INGRESO_FISICO"))
                     {
                         pst.setInt(1,res.getInt("COD_MATERIAL"));
                         pst.setDouble(2,res.getDouble("CANTIDAD_SALIDA_ALMACEN"));
                         pst.setDouble(3,res.getDouble("CANT_TOTAL_INGRESO_FISICO"));
                         if(pst.executeUpdate()>0)System.out.println("se registro un observacion por traspaso");
                         observacionTraspaso=true;              
                     }
                 }
                 consulta=new StringBuilder(" update traspasos");
                            //consulta.append(" set cod_estado_traspaso=").append(observacionTraspaso?3:2);
                 consulta.append(" set cod_estado_traspaso=").append("3");
                            consulta.append(" where cod_salida_almacen=").append(traspasos.getSalidasAlmacen().getCodSalidaAlmacen());
                 System.out.println("consulta actualizar estado traspaso" + consulta.toString());
                 pst=con.prepareStatement(consulta.toString());
                 if(pst.executeUpdate()>0)System.out.println("se actualizo el estado del traspaso");
                 //</editor-fold>
                 Statement statement=con.createStatement();
                 String insertTraspasoIngreso="insert into traspaso_ingreso values("+traspasos.getSalidasAlmacen().getCodSalidaAlmacen()+","+ingresosAlmacen.getCodIngresoAlmacen()+",GETDATE())";
                 System.out.println("Consulta traspaso J: "+insertTraspasoIngreso);
                 statement.execute(insertTraspasoIngreso);
                 con.commit();
                 mensaje="1";
             }
             else
             {
                 con.setAutoCommit(true);
                 mensaje="El traspaso ya fue aceptado con anterioridad";    
             }
             res.close();
             st.close();
            
             
         }
         catch(SQLException ex)
         {
             con.rollback();
             mensaje="Ocurrio un error al momento de registrar el traspaso,intente de nuevo";
             ex.printStackTrace();
         }
         catch (Exception e)
         {
            mensaje="Ocurrio un error al momento de registrar el traspaso,intente de nuevo";
            e.printStackTrace();
         }
         finally
         {
             con.close();
         }
         //actualización de costos
            try {
                
                AssignCostService assignCostService = new AssignCostService(Util.getPropertiesConnection());
                float ufv_actual = assignCostService.getUfvActual();
                for (Object detalle : ingresosAlmacenDetalleList) {
                    IngresosAlmacenDetalle ingresosAlmacenDetalle = (IngresosAlmacenDetalle) detalle;
                    boolean resultado=assignCostService.updateCostoUnitarioIngreso(ingresosAlmacen.getCodIngresoAlmacen(), ingresosAlmacen.getAlmacenes().getCodAlmacen(), ingresosAlmacen.getTiposIngresoAlmacen().getCodTipoIngresoAlmacen(), Integer.parseInt(ingresosAlmacenDetalle.getMateriales().getCodMaterial()), ingresosAlmacenDetalle.getCantTotalIngresoFisico(), ufv_actual);
                    if(resultado){
                        System.out.println("update ok -> "+ingresosAlmacenDetalle.getMateriales().getNombreMaterial());
                    }
                }
                System.out.println("Costos actualizado en ingreso = "+ingresosAlmacen.getCodIngresoAlmacen());
            } catch (Exception e) {
                System.out.println("Excepcion en costos");
                e.printStackTrace();
            }
            //fin actualización de costos
         return null;
     }
     public void actualizaTraspaso() throws SQLException{
         Connection con  = null;
         try {             
             int coincidenCantidades = 1;
             con = Util.openConnection(con);
             con.setAutoCommit(false);
             String consulta = " select s.CANTIDAD_SALIDA_ALMACEN,i.CANT_TOTAL_INGRESO_FISICO,s.COD_MATERIAL " +
                     " from TRASPASOS t " +
                     " inner join SALIDAS_ALMACEN_DETALLE s on s.COD_SALIDA_ALMACEN = t.COD_SALIDA_ALMACEN " +
                     " inner join INGRESOS_ALMACEN_DETALLE i on i.COD_INGRESO_ALMACEN = t.COD_INGRESO_ALMACEN and s.COD_MATERIAL = i.COD_MATERIAL " +
                     " where s.COD_SALIDA_ALMACEN = '"+traspasos.getSalidasAlmacen().getCodSalidaAlmacen()+"' " +
                     " and i.COD_INGRESO_ALMACEN = '"+ingresosAlmacen.getCodIngresoAlmacen()+"'  ";
             System.out.println("consulta " + consulta);
             Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             Statement st1 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             ResultSet rs =  st.executeQuery(consulta);
             while(rs.next()){
                 if(rs.getFloat("CANTIDAD_SALIDA_ALMACEN")!=rs.getFloat("CANT_TOTAL_INGRESO_FISICO")){
                     consulta = " INSERT INTO OBSERVACIONES_TRASPASO ( COD_SALIDA_ALMACEN, COD_MATERIAL, CANTIDAD_SALIDA_ORIGEN, " +
                             " CANTIDAD_INGRESO_DESTINO, OBSERVACIONES ) VALUES ( '"+traspasos.getSalidasAlmacen().getCodSalidaAlmacen()+"', " +
                             " '"+rs.getInt("COD_MATERIAL")+"', '"+rs.getFloat("CANTIDAD_SALIDA_ALMACEN")+"', " +
                             " '"+rs.getFloat("CANT_TOTAL_INGRESO_FISICO")+"',  '' ) ";
                     System.out.println("consulta " + consulta);
                     st1.executeUpdate(consulta);
                     coincidenCantidades = 0;                     
                 }
             }
             if(coincidenCantidades==0){
                 consulta = " update traspasos set cod_estado_traspaso = 3 where cod_salida_almacen = '"+traspasos.getSalidasAlmacen().getCodSalidaAlmacen()+"' and cod_ingreso_almacen = '"+ingresosAlmacen.getCodIngresoAlmacen()+"'  "; //observado
             }else{
                 consulta = " update traspasos set cod_estado_traspaso = 2 where cod_salida_almacen = '"+traspasos.getSalidasAlmacen().getCodSalidaAlmacen()+"' and cod_ingreso_almacen = '"+ingresosAlmacen.getCodIngresoAlmacen()+"'  "; //sin observaciones
             }
             st1.executeUpdate(consulta);
             System.out.println("consulta " + consulta);
             con.commit();

             st.executeUpdate(consulta);
             rs.close();
             st.close();

         } catch (Exception e) {
             e.printStackTrace();
             con.rollback();
         }finally{
             con.close();
         }
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

             
             String nombreEstanteAmbiente = this.buscaEstanteAmbiente(ingresosAlmacenDetalleEstado.getEstanteAlmacen().getCodEstante());


             ingresosAlmacenDetalle.setCantTotalIngresoFisico(0);
             while(i.hasNext()){
                 IngresosAlmacenDetalleEstado ingresosAlmacenDetalleEstadoItem = (IngresosAlmacenDetalleEstado)i.next();
                 
                 if(ingresosAlmacenDetalleEstadoItem.getChecked()==true){
                     ingresosAlmacenDetalleEstadoItem.getEstanteAlmacen().setCodEstante(ingresosAlmacenDetalleEstado.getEstanteAlmacen().getCodEstante());
                     ingresosAlmacenDetalleEstadoItem.getEstanteAlmacen().setNombreEstante(nombreEstanteAmbiente);
                     ingresosAlmacenDetalleEstadoItem.setFila(ingresosAlmacenDetalleEstado.getFila());
                     ingresosAlmacenDetalleEstadoItem.setColumna(ingresosAlmacenDetalleEstado.getColumna());
                 }
                 ingresosAlmacenDetalle.setCantTotalIngresoFisico(ingresosAlmacenDetalle.getCantTotalIngresoFisico()+ingresosAlmacenDetalleEstadoItem.getCantidadParcial());
             }
         } catch (Exception e) {
             e.printStackTrace();
         }
         return null;
     }
     
     
}
