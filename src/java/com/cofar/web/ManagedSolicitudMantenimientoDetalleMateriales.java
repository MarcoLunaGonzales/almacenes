/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.web;

import com.cofar.bean.SolicitudMantenimiento;
import com.cofar.bean.SolicitudMantenimientoDetalleMateriales;
import com.cofar.bean.SolicitudesCompra;
import com.cofar.bean.SolicitudesCompraDetalle;
import com.cofar.bean.SolicitudesSalida;
import com.cofar.bean.SolicitudesSalidaDetalle;
import com.cofar.util.Util;
import java.sql.Connection;
import java.sql.ResultSet;
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

/**
 *
 * @author hvaldivia
 */

public class ManagedSolicitudMantenimientoDetalleMateriales {
    Connection con = null;
    SolicitudMantenimiento solicitudMantenimiento = new SolicitudMantenimiento();
    List solicitudMantenimientoDetalleMaterialesList = new ArrayList();
    SolicitudMantenimientoDetalleMateriales solicitudMantenimientoDetalleMateriales = new SolicitudMantenimientoDetalleMateriales();
    SolicitudMantenimientoDetalleMateriales solicitudMantenimientoDetalleMaterialesEditar = new SolicitudMantenimientoDetalleMateriales();
    List materialesList = new ArrayList();
    SolicitudesSalida solicitudesSalida = new SolicitudesSalida();
    SolicitudesSalidaDetalle solicitudesSalidaDetalle = new SolicitudesSalidaDetalle();
    SolicitudesCompra solicitudesCompra = new SolicitudesCompra();
    SolicitudesCompraDetalle solicitudesCompraDetalle = new SolicitudesCompraDetalle();

    List solicitudesSalidaDetalleList = new ArrayList();
    List solicitudesCompraDetalleList = new ArrayList();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    String mensaje = "";
    


    public List getMaterialesList() {
        return materialesList;
    }

    public void setMaterialesList(List materialesList) {
        this.materialesList = materialesList;
    }

    public SolicitudMantenimiento getSolicitudMantenimiento() {
        return solicitudMantenimiento;
    }

    public void setSolicitudMantenimiento(SolicitudMantenimiento solicitudMantenimiento) {
        this.solicitudMantenimiento = solicitudMantenimiento;
    }

    public SolicitudMantenimientoDetalleMateriales getSolicitudMantenimientoDetalleMateriales() {
        return solicitudMantenimientoDetalleMateriales;
    }

    public void setSolicitudMantenimientoDetalleMateriales(SolicitudMantenimientoDetalleMateriales solicitudMantenimientoDetalleMateriales) {
        this.solicitudMantenimientoDetalleMateriales = solicitudMantenimientoDetalleMateriales;
    }

    public SolicitudMantenimientoDetalleMateriales getSolicitudMantenimientoDetalleMaterialesEditar() {
        return solicitudMantenimientoDetalleMaterialesEditar;
    }

    public void setSolicitudMantenimientoDetalleMaterialesEditar(SolicitudMantenimientoDetalleMateriales solicitudMantenimientoDetalleMaterialesEditar) {
        this.solicitudMantenimientoDetalleMaterialesEditar = solicitudMantenimientoDetalleMaterialesEditar;
    }

    public List getSolicitudMantenimientoDetalleMaterialesList() {
        return solicitudMantenimientoDetalleMaterialesList;
    }

    public void setSolicitudMantenimientoDetalleMaterialesList(List solicitudMantenimientoDetalleMaterialesList) {
        this.solicitudMantenimientoDetalleMaterialesList = solicitudMantenimientoDetalleMaterialesList;
    }

    public List getSolicitudesCompraDetalleList() {
        return solicitudesCompraDetalleList;
    }

    public void setSolicitudesCompraDetalleList(List solicitudesCompraDetalleList) {
        this.solicitudesCompraDetalleList = solicitudesCompraDetalleList;
    }

   

    public List getSolicitudesSalidaDetalleList() {
        return solicitudesSalidaDetalleList;
    }

    public void setSolicitudesSalidaDetalleList(List solicitudesSalidaDetalleList) {
        this.solicitudesSalidaDetalleList = solicitudesSalidaDetalleList;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public SolicitudesCompra getSolicitudesCompra() {
        return solicitudesCompra;
    }

    public void setSolicitudesCompra(SolicitudesCompra solicitudesCompra) {
        this.solicitudesCompra = solicitudesCompra;
    }

    public SolicitudesSalida getSolicitudesSalida() {
        return solicitudesSalida;
    }

    public void setSolicitudesSalida(SolicitudesSalida solicitudesSalida) {
        this.solicitudesSalida = solicitudesSalida;
    }

    

    

    





    /** Creates a new instance of ManagedSolicitudMantenimientoDetalleMateriales */
    public ManagedSolicitudMantenimientoDetalleMateriales() {
    }


    public String getCargarContenidoSolicitudMantenimientoDetalleMateriales(){
        try {
             ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
             Map<String, Object> sessionMap = externalContext.getSessionMap();
             solicitudMantenimiento=(SolicitudMantenimiento)sessionMap.get("solicitudMantenimiento");
            this.cargarSolicitudMantenimientoDetalleMateriales();
            this.cargarMateriales();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void cargarSolicitudMantenimientoDetalleMateriales(){
        try {
               

                con = Util.openConnection(con);
                String  consulta = " select s.COD_SOLICITUD_MANTENIMIENTO,s.COD_MATERIAL,m.NOMBRE_MATERIAL,u.NOMBRE_UNIDAD_MEDIDA,s.CANTIDAD,s.DESCRIPCION " +
                        " from SOLICITUDES_MANTENIMIENTO_DETALLE_MATERIALES s " +
                        " inner join materiales m on s.COD_MATERIAL = m.COD_MATERIAL " +
                        " inner join UNIDADES_MEDIDA u on m.COD_UNIDAD_MEDIDA = u.COD_UNIDAD_MEDIDA " +
                        " where s.COD_SOLICITUD_MANTENIMIENTO = '"+solicitudMantenimiento.getCodSolicitudMantenimiento()+"' ";
                
                System.out.println("consulta" + consulta);
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(consulta);
                solicitudMantenimientoDetalleMaterialesList.clear();
                while(rs.next()){
                    SolicitudMantenimientoDetalleMateriales solicitudMantenimientoDetalleMaterialesItem = new SolicitudMantenimientoDetalleMateriales();

                    solicitudMantenimientoDetalleMaterialesItem.getSolicitudMantenimiento().setCodSolicitudMantenimiento(rs.getString("COD_SOLICITUD_MANTENIMIENTO"));
                    solicitudMantenimientoDetalleMaterialesItem.getMateriales().setCodMaterial(rs.getString("COD_MATERIAL"));
                    solicitudMantenimientoDetalleMaterialesItem.getMateriales().setNombreMaterial(rs.getString("NOMBRE_MATERIAL"));
                    solicitudMantenimientoDetalleMaterialesItem.setCantidad(rs.getInt("CANTIDAD"));
                    solicitudMantenimientoDetalleMaterialesItem.setDescripcion(rs.getString("DESCRIPCION"));
                    solicitudMantenimientoDetalleMaterialesItem.getMateriales().getUnidadesMedida().setNombreUnidadMedida(rs.getString("NOMBRE_UNIDAD_MEDIDA"));
                    solicitudMantenimientoDetalleMaterialesItem.setDisponible(this.obtenerDisponible(rs.getInt("COD_MATERIAL")));
                    solicitudMantenimientoDetalleMaterialesList.add(solicitudMantenimientoDetalleMaterialesItem);
                }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public String registrarSolicitudMantenimientoDetalleMateriales_action(){
        try {
            con=Util.openConnection(con);

            String consulta = " INSERT INTO SOLICITUDES_MANTENIMIENTO_DETALLE_MATERIALES (  COD_SOLICITUD_MANTENIMIENTO," +
                    "  COD_MATERIAL,  DESCRIPCION,  CANTIDAD) VALUES ('"+solicitudMantenimiento.getCodSolicitudMantenimiento()+"', " +
                    " '"+solicitudMantenimientoDetalleMateriales.getMateriales().getCodMaterial()+"', '"+solicitudMantenimientoDetalleMateriales.getDescripcion()+"',  " +
                    "'"+solicitudMantenimientoDetalleMateriales.getCantidad()+"'); ";
                    
               System.out.println("consulta" + consulta);

               Statement st = con.createStatement();
               st.executeUpdate(consulta);
               this.cargarSolicitudMantenimientoDetalleMateriales();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public String agregarSolicitudMantenimientoDetalleMateriales_action(){
        try {
                solicitudMantenimientoDetalleMateriales = new SolicitudMantenimientoDetalleMateriales();
                solicitudMantenimientoDetalleMateriales.setDescripcion(solicitudMantenimiento.getObsSolicitudMantenimiento());
                
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public String eliminarSolicitudMantenimientoDetalleMateriales_action(){
        try {
               con=Util.openConnection(con);
               String consulta = "";
               Iterator i = solicitudMantenimientoDetalleMaterialesList.iterator();
               while(i.hasNext()){
                   SolicitudMantenimientoDetalleMateriales solicitudMantenimientoDetalleMaterialesItem = (SolicitudMantenimientoDetalleMateriales)i.next();
                   if(solicitudMantenimientoDetalleMaterialesItem.getChecked().booleanValue()==true){
                   
                       
                         consulta = " DELETE FROM SOLICITUDES_MANTENIMIENTO_DETALLE_MATERIALES " +
                                 " WHERE COD_SOLICITUD_MANTENIMIENTO = '"+solicitudMantenimientoDetalleMaterialesItem.getSolicitudMantenimiento().getCodSolicitudMantenimiento()+"'" +
                                 " AND COD_MATERIAL = '"+solicitudMantenimientoDetalleMaterialesItem.getMateriales().getCodMaterial()+"'";
                                System.out.println("consulta" + consulta) ;
                        Statement st = con.createStatement();
                        st = con.createStatement();
                        st.executeUpdate(consulta);

                       break;
                   }


               }
               this.cargarSolicitudMantenimientoDetalleMateriales();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String editarSolicitudMantenimientoDetalleMateriales_action(){
        try {
               con=Util.openConnection(con);
               Iterator i = solicitudMantenimientoDetalleMaterialesList.iterator();
               while(i.hasNext()){
                   SolicitudMantenimientoDetalleMateriales solicitudMantenimientoDetalleMaterialesItem = (SolicitudMantenimientoDetalleMateriales) i.next();
                   if(solicitudMantenimientoDetalleMaterialesItem.getChecked().booleanValue()==true){
                       solicitudMantenimientoDetalleMateriales =solicitudMantenimientoDetalleMaterialesItem;
                       solicitudMantenimientoDetalleMaterialesEditar.getSolicitudMantenimiento().setCodSolicitudMantenimiento(solicitudMantenimientoDetalleMaterialesItem.getSolicitudMantenimiento().getCodSolicitudMantenimiento());
                       solicitudMantenimientoDetalleMaterialesEditar.getMateriales().setCodMaterial(solicitudMantenimientoDetalleMaterialesItem.getMateriales().getCodMaterial());

                       break;
                   }
               }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public String guardarEdicionSolicitudMantenimientoDetalleMateriales_action(){
        try {
            con=Util.openConnection(con);


            String consulta = " UPDATE SOLICITUDES_MANTENIMIENTO_DETALLE_MATERIALES  SET " +                            
                            " COD_MATERIAL = '"+solicitudMantenimientoDetalleMateriales.getMateriales().getCodMaterial()+"'," +
                            " DESCRIPCION = '"+solicitudMantenimientoDetalleMateriales.getDescripcion()+"'," +
                            " CANTIDAD = '"+solicitudMantenimientoDetalleMateriales.getCantidad()+"' " +
                            " WHERE " +
                            " COD_SOLICITUD_MANTENIMIENTO = '"+solicitudMantenimientoDetalleMaterialesEditar.getSolicitudMantenimiento().getCodSolicitudMantenimiento()+"' AND " +
                            " COD_MATERIAL = '"+solicitudMantenimientoDetalleMaterialesEditar.getMateriales().getCodMaterial()+"' ";
                    

             System.out.println("consulta" + consulta);
             Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             st.executeUpdate(consulta);
             this.cargarSolicitudMantenimientoDetalleMateriales();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    
    public String redireccionar(String direccion) {
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext ext = facesContext.getExternalContext();
            ext.redirect(direccion);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void cargarMateriales() {
        try {
            con = (Util.openConnection(con));
            String consulta = " select M.COD_MATERIAL,M.NOMBRE_MATERIAL from materiales M WHERE M.COD_GRUPO IN(" +
                    " select G.COD_GRUPO from grupos G WHERE G.COD_CAPITULO=22) " +
                    " ORDER BY M.NOMBRE_MATERIAL ASC ";
            System.out.println("consulta "+consulta);
            ResultSet rs = null;

            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            materialesList.clear();
            rs = st.executeQuery(consulta);
            while (rs.next()) {
                materialesList.add(new SelectItem(rs.getString("COD_MATERIAL"), rs.getString("NOMBRE_MATERIAL")));
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
    }

    public double obtenerDisponible(int codMaterial) {

        double disponible = 0;
        try {

            con = (Util.openConnection(con));
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            String consulta = " " +
                    " select DISTINCT m.cod_material, " +
                    " ISNULL((select SUM(iade.cantidad_parcial) " +
                    " from ingresos_almacen_detalle_estado iade,ingresos_almacen_detalle  iad,ingresos_almacen ia " +
                    " WHERE iade.cod_material=m.cod_material and ia.cod_estado_ingreso_almacen=1 and  " +
                    " iad.cod_ingreso_almacen=ia.cod_ingreso_almacen and ia.estado_sistema=1 and  " +
                    " iade.cod_material=iad.cod_material and iade.cod_ingreso_almacen=iad.cod_ingreso_almacen " +
                    " and ia.fecha_ingreso_almacen<=GETDATE() ) ,0) - " +
                    " ISNULL((select SUM(sadi.cantidad) " +
                    " from salidas_almacen_detalle sad,salidas_almacen_detalle_ingreso sadi,ingresos_almacen_detalle_estado iade, salidas_almacen sa " +
                    " WHERE sa.cod_salida_almacen=sad.cod_salida_almacen and sa.estado_sistema=1 and sa.cod_estado_salida_almacen=1 and " +
                    " sad.cod_salida_almacen=sadi.cod_salida_almacen and sad.cod_material=sadi.cod_material and " +
                    " sadi.cod_ingreso_almacen=iade.cod_ingreso_almacen and sadi.cod_material=iade.cod_material and sadi.ETIQUETA=iade.ETIQUETA " +
                    " and sad.cod_material=m.cod_material and sa.fecha_salida_almacen<=GETDATE() ) ,0)  + " +
                    " ISNULL( (select sum(iad.cant_total_ingreso_fisico) from DEVOLUCIONES d, ingresos_almacen ia,INGRESOS_ALMACEN_DETALLE iad " +
                    " where ia.cod_devolucion=d.cod_devolucion and ia.fecha_ingreso_almacen<=GETDATE() and d.cod_estado_devolucion=1 and d.estado_sistema=1 and ia.cod_estado_ingreso_almacen=1 " +
                    " and ia.cod_almacen=d.cod_almacen and ia.cod_ingreso_almacen=iad.cod_ingreso_almacen and iad.cod_material=m.cod_material) ,0) - " +
                    " ISNULL( (select SUM(iade.cantidad_restante) from ingresos_almacen_detalle_estado iade,ingresos_almacen_detalle  iad,ingresos_almacen ia " +
                    " WHERE iade.cod_material=m.cod_material and ia.cod_estado_ingreso_almacen=1 and iad.cod_ingreso_almacen=ia.cod_ingreso_almacen and ia.estado_sistema=1 and iade.cod_material=iad.cod_material and iade.cod_ingreso_almacen=iad.cod_ingreso_almacen " +
                    " and iade.cod_estado_material=1 and ia.fecha_ingreso_almacen<=GETDATE() ) ,0) - " +
                    " ISNULL( (select SUM(iade.cantidad_restante) from ingresos_almacen_detalle_estado iade,ingresos_almacen_detalle  iad,ingresos_almacen ia " +
                    " WHERE iade.cod_material=m.cod_material and ia.cod_estado_ingreso_almacen=1 and iad.cod_ingreso_almacen=ia.cod_ingreso_almacen and ia.estado_sistema=1 and iade.cod_material=iad.cod_material and iade.cod_ingreso_almacen=iad.cod_ingreso_almacen " +
                    " and iade.cod_estado_material=3 and ia.fecha_ingreso_almacen<=GETDATE() ) ,0) - " +
                    " ISNULL( (select SUM(iade.cantidad_restante)  from ingresos_almacen_detalle_estado iade,ingresos_almacen_detalle  iad,ingresos_almacen ia " +
                    " WHERE iade.cod_material=m.cod_material and ia.cod_estado_ingreso_almacen=1 and iad.cod_ingreso_almacen=ia.cod_ingreso_almacen and ia.estado_sistema=1 and iade.cod_material=iad.cod_material and iade.cod_ingreso_almacen=iad.cod_ingreso_almacen " +
                    " and iade.cod_estado_material=4 and ia.fecha_ingreso_almacen<=GETDATE() ) ,0) - " +
                    " ISNULL( (select SUM(iade.cantidad_restante) from ingresos_almacen_detalle_estado iade,ingresos_almacen_detalle  iad,ingresos_almacen ia " +
                    " WHERE iade.cod_material=m.cod_material  and ia.cod_estado_ingreso_almacen=1 and iad.cod_ingreso_almacen=ia.cod_ingreso_almacen  and ia.estado_sistema=1 and iade.cod_material=iad.cod_material and iade.cod_ingreso_almacen=iad.cod_ingreso_almacen " +
                    " and iade.cod_estado_material=5 and ia.fecha_ingreso_almacen<=GETDATE() ),0) AS DISPONIBLE, m.COD_UNIDAD_MEDIDA " +
                    " from materiales m,grupos g,capitulos c, SECCIONES s,SECCIONES_DETALLE sd,almacenes al " +
                    " where m.cod_grupo=g.cod_grupo  and g.cod_capitulo=c.cod_capitulo and  m.material_almacen=1 and m.COD_MATERIAL='" + codMaterial + "'";

            consulta = " select DISTINCT m.cod_material, " +
                    " ISNULL((select SUM(iade.cantidad_parcial) " +
                    " from ingresos_almacen_detalle_estado iade,ingresos_almacen_detalle  iad,ingresos_almacen ia " +
                    " WHERE iade.cod_material=m.cod_material and ia.cod_estado_ingreso_almacen=1 and  " +
                    " iad.cod_ingreso_almacen=ia.cod_ingreso_almacen and ia.estado_sistema=1 and  " +
                    " iade.cod_material=iad.cod_material and iade.cod_ingreso_almacen=iad.cod_ingreso_almacen " +
                    " and ia.fecha_ingreso_almacen<=GETDATE() and ia.COD_ALMACEN = al.COD_ALMACEN ) ,0) - " +
                    " ISNULL((select SUM(sadi.cantidad) " +
                    " from salidas_almacen_detalle sad,salidas_almacen_detalle_ingreso sadi,ingresos_almacen_detalle_estado iade, salidas_almacen sa " +
                    " WHERE sa.cod_salida_almacen=sad.cod_salida_almacen and sa.estado_sistema=1 and sa.cod_estado_salida_almacen=1 and " +
                    " sad.cod_salida_almacen=sadi.cod_salida_almacen and sad.cod_material=sadi.cod_material and " +
                    " sadi.cod_ingreso_almacen=iade.cod_ingreso_almacen and sadi.cod_material=iade.cod_material and sadi.ETIQUETA=iade.ETIQUETA " +
                    " and sad.cod_material=m.cod_material and sa.fecha_salida_almacen<=GETDATE() and sa.COD_ALMACEN = al.COD_ALMACEN ) ,0)  + " +
                    " ISNULL( (select sum(iad.cant_total_ingreso_fisico) from DEVOLUCIONES d, ingresos_almacen ia,INGRESOS_ALMACEN_DETALLE iad " +
                    " where ia.cod_devolucion=d.cod_devolucion and ia.fecha_ingreso_almacen<=GETDATE() and d.cod_estado_devolucion=1 and d.estado_sistema=1 and ia.cod_estado_ingreso_almacen=1 " +
                    " and ia.cod_almacen=d.cod_almacen and ia.cod_ingreso_almacen=iad.cod_ingreso_almacen and iad.cod_material=m.cod_material and ia.COD_ALMACEN = al.COD_ALMACEN) ,0) - " +
                    " ISNULL( (select SUM(iade.cantidad_restante) from ingresos_almacen_detalle_estado iade,ingresos_almacen_detalle  iad,ingresos_almacen ia " +
                    " WHERE iade.cod_material=m.cod_material and ia.cod_estado_ingreso_almacen=1 and iad.cod_ingreso_almacen=ia.cod_ingreso_almacen and ia.estado_sistema=1 and iade.cod_material=iad.cod_material and iade.cod_ingreso_almacen=iad.cod_ingreso_almacen " +
                    " and iade.cod_estado_material=1 and ia.fecha_ingreso_almacen<=GETDATE() and ia.COD_ALMACEN = al.COD_ALMACEN ) ,0) - " +
                    " ISNULL( (select SUM(iade.cantidad_restante) from ingresos_almacen_detalle_estado iade,ingresos_almacen_detalle  iad,ingresos_almacen ia " +
                    " WHERE iade.cod_material=m.cod_material and ia.cod_estado_ingreso_almacen=1 and iad.cod_ingreso_almacen=ia.cod_ingreso_almacen and ia.estado_sistema=1 and iade.cod_material=iad.cod_material and iade.cod_ingreso_almacen=iad.cod_ingreso_almacen  " +
                    " and iade.cod_estado_material=3 and ia.fecha_ingreso_almacen<=GETDATE() and ia.COD_ALMACEN = al.COD_ALMACEN ) ,0) - " +
                    " ISNULL( (select SUM(iade.cantidad_restante)  from ingresos_almacen_detalle_estado iade,ingresos_almacen_detalle  iad,ingresos_almacen ia " +
                    " WHERE iade.cod_material=m.cod_material and ia.cod_estado_ingreso_almacen=1 and iad.cod_ingreso_almacen=ia.cod_ingreso_almacen and ia.estado_sistema=1 and iade.cod_material=iad.cod_material and iade.cod_ingreso_almacen=iad.cod_ingreso_almacen " +
                    " and iade.cod_estado_material=4 and ia.fecha_ingreso_almacen<=GETDATE() and ia.COD_ALMACEN = al.COD_ALMACEN ) ,0) - " +
                    " ISNULL( (select SUM(iade.cantidad_restante) from ingresos_almacen_detalle_estado iade,ingresos_almacen_detalle  iad,ingresos_almacen ia " +
                    " WHERE iade.cod_material=m.cod_material  and ia.cod_estado_ingreso_almacen=1 and iad.cod_ingreso_almacen=ia.cod_ingreso_almacen  and ia.estado_sistema=1 and iade.cod_material=iad.cod_material and iade.cod_ingreso_almacen=iad.cod_ingreso_almacen " +
                    " and iade.cod_estado_material=5 and ia.fecha_ingreso_almacen<=GETDATE() and ia.COD_ALMACEN = al.COD_ALMACEN ),0) AS DISPONIBLE, m.COD_UNIDAD_MEDIDA  " +
                    " from materiales m,grupos g,capitulos c, SECCIONES s,SECCIONES_DETALLE sd,almacenes al " +
                    " where m.cod_grupo=g.cod_grupo  and g.cod_capitulo=c.cod_capitulo and  m.material_almacen=1 and m.COD_MATERIAL= '" + codMaterial + "' " +
                    " and al.COD_ALMACEN  = 4 ";
            

            System.out.println("consulta "+consulta);
            ResultSet rs = st.executeQuery(consulta);
            if (rs.next()) {
                disponible = rs.getDouble("DISPONIBLE");
            }

            if (rs != null) {
                rs.close();
                st.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return disponible;
    }
//    public String solicitudAlmacen_action(){
//        try {
//            ManagedAccesoSistema m = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
//            String codPersonal = m.getUsuarioModuloBean().getCodUsuarioGlobal();
//
//            int codSolicitudOrdenCompra = this.obtieneCodSolicitudOrdenCompra();
//            String consulta = " INSERT INTO SOLICITUDES_COMPRA( COD_SOLICITUD_COMPRA, COD_GESTION,   COD_TIPO_SOLICITUD_COMPRA,   COD_RESPONSABLE_COMPRAS,   COD_ESTADO_SOLICITUD_COMPRA, " +
//                    " ESTADO_SISTEMA,  COD_PERSONAL,   COD_AREA_EMPRESA,   FECHA_SOLICITUD_COMPRA,    OBS_SOLICITUD_COMPRA,   FECHA_ENVIO) " +
//                    " VALUES('" + codSolicitudOrdenCompra + "',  (SELECT G.COD_GESTION FROM GESTIONES G WHERE G.GESTION_ESTADO=1) ," +
//                    " '1', '0' , '1', '1','" + codPersonal + "','86', GETDATE() ,   NULL,   NULL)";
//            System.out.println("consulta "+consulta);
//            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//            st.executeUpdate(consulta);
//
//
//            //se iteran los materiales que tienen stock y se realiza la solicitud de almacen
//            Iterator i = solicitudMantenimientoDetalleMaterialesList.iterator();
//            while(i.hasNext()){
//                SolicitudMantenimientoDetalleMateriales solicitudMantenimientoDetalleMaterialesItem = (SolicitudMantenimientoDetalleMateriales)i.next();
//                if(solicitudMantenimientoDetalleMaterialesItem.getCantidad()>=solicitudMantenimientoDetalleMaterialesItem.getDisponible()){
//
//                    consulta = " INSERT INTO SOLICITUDES_COMPRA_DETALLE( COD_MATERIAL,   COD_SOLICITUD_COMPRA,   CANT_SOLICITADA,   COD_UNIDAD_MEDIDA, " +
//                            " OBS_MATERIAL_SOLICITUD )  VALUES( '" + solicitudMantenimientoDetalleMaterialesItem.getMateriales().getCodMaterial() + "'," +
//                            " '" + codSolicitudOrdenCompra + "', '" + itemIteracionMaterial.getCantidadSugerida() + "' , '" + itemIteracionMaterial.getCodUnidadMedida() + "',   NULL)";
//                    System.out.println("consulta "+consulta);
//                    st.executeUpdate(consulta);
//                }
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    
    public String registrarSolicitudAlmacen_action(){
        try {
            mensaje = "";

             //verificar que no pasen el disponible de almacenes
            Iterator i = solicitudesSalidaDetalleList.iterator();
            while(i.hasNext()){
                SolicitudesSalidaDetalle solicitudesSalidaDetalleItem = (SolicitudesSalidaDetalle)i.next();
                if(solicitudesSalidaDetalleItem.getCantidad()>solicitudesSalidaDetalleItem.getDisponible()){
                    mensaje = " Existen cantidades que sobrepasaron el disponible ";
                    return null;
                }
            }
             if(solicitudesSalidaDetalleList.size()==0){
                    mensaje = " no existen items para realizar la solicitud de almacen ";
                   return null;
            }



            String consulta = "INSERT INTO  SOLICITUDES_SALIDA( COD_GESTION,   COD_FORM_SALIDA,   COD_TIPO_SALIDA_ALMACEN,   COD_ESTADO_SOLICITUD_SALIDA_ALMACEN, " +
                    "  SOLICITANTE,   AREA_DESTINO_SALIDA,   FECHA_SOLICITUD,   COD_LOTE_PRODUCCION,   OBS_SOLICITUD,   ESTADO_SISTEMA,   CONTROL_CALIDAD,   COD_INGRESO_ALMACEN, " +
                    "  COD_ALMACEN,   orden_trabajo) VALUES(   " +
                    "  (SELECT G.COD_GESTION FROM GESTIONES G WHERE G.GESTION_ESTADO=1)," +
                    " '"+solicitudesSalida.getCodFormSalida()+"', " +
                    " '"+solicitudesSalida.getTiposSalidasAlmacen().getCodTipoSalidaAlmacen()+"'," +
                    "'"+solicitudesSalida.getEstadosSolicitudSalidasAlmacen().getCodEstadoSolicitudSalidaAlmacen()+"'," +
                    " '"+solicitudesSalida.getSolicitante().getCodPersonal()+"'," +
                    " '"+solicitudesSalida.getAreaDestinoSalida().getCodAreaEmpresa()+"',  " +
                    " '"+sdf.format(solicitudesSalida.getFechaSolicitud())+"', " +
                    " '"+solicitudesSalida.getCodLoteProduccion()+"'," +
                    " '"+solicitudesSalida.getObsSolicitud()+"'," +
                    " '"+solicitudesSalida.getEstadoSistema()+"', " +
                    " '"+solicitudesSalida.getControlCalidad()+"', " +
                    " '"+solicitudesSalida.getCodIngresoAlmacen()+"'," +
                    " '"+solicitudesSalida.getAlmacenes().getCodAlmacen()+"'," +
                    " '" + solicitudMantenimiento.getCodSolicitudMantenimiento() + "')   ";
                    
            System.out.println("consulta "+consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            st.executeUpdate(consulta);

           

            //se iteran los materiales que tienen stock y se realiza la solicitud de almacen
            i = solicitudesSalidaDetalleList.iterator();
            while(i.hasNext()){
                SolicitudesSalidaDetalle solicitudesSalidaDetalleItem = (SolicitudesSalidaDetalle)i.next();
                

                   consulta = " INSERT INTO " +
                        " SOLICITUDES_SALIDA_DETALLE(  COD_FORM_SALIDA,  COD_MATERIAL,   CANTIDAD,   CANTIDAD_ENTREGADA,   COD_UNIDAD_MEDIDA) " +
                        " VALUES('"+solicitudesSalida.getCodFormSalida() +"', " +
                        " '" + solicitudesSalidaDetalleItem.getMateriales().getCodMaterial() + "' , " +
                        " '"+solicitudesSalidaDetalleItem.getCantidad() +"','"+solicitudesSalidaDetalleItem.getCantidadEntregada()+"'," +
                        " '" + solicitudesSalidaDetalleItem.getMateriales().getUnidadesMedida().getCodUnidadMedida() + "') ";
                    System.out.println("consulta "+consulta);
                    st.executeUpdate(consulta);
                
            }
            //se actualiza la solicitud de mantenimiento con la solicitud de salida de almacenes

             consulta = " UPDATE SOLICITUDES_MANTENIMIENTO  SET " +
                    "  COD_FORM_SALIDA = '"+solicitudesSalida.getCodFormSalida()+"' " +
                    " WHERE  COD_SOLICITUD_MANTENIMIENTO = '"+solicitudMantenimiento.getCodSolicitudMantenimiento()+"' ";
             solicitudMantenimiento.getSolicitudesSalida().setCodFormSalida(solicitudesSalida.getCodFormSalida());
             System.out.println("consulta "+consulta);
             st.executeUpdate(consulta);
             

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int obtieneCodSolicitudOrdenCompra() {
        int codSolicitudCompra = 0;
        try {
            String consulta = " SELECT MAX(isnull(SC.COD_SOLICITUD_COMPRA,0))+1 COD_SOLICITUD_COMPRA FROM SOLICITUDES_COMPRA SC ";
            System.out.println("consulta "+consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            if (rs.next()) {
                codSolicitudCompra = rs.getInt("COD_SOLICITUD_COMPRA");
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
        return codSolicitudCompra;
    }
    public String verSolicitudAlmacen_action(){
        try {
            ManagedAccesoSistema m = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            String codPersonal = m.getUsuarioModuloBean().getCodUsuarioGlobal();
            
            // generacion de la cabecera de ingreso de solicitud a almacen

            solicitudesSalida = new SolicitudesSalida();
            solicitudesSalida.setCodFormSalida(this.obtieneCodSolicitudSalidaAlmacen());
            solicitudesSalida.getTiposSalidasAlmacen().setCodTipoSalidaAlmacen(1);
            solicitudesSalida.getEstadosSolicitudSalidasAlmacen().setCodEstadoSolicitudSalidaAlmacen(1);
            solicitudesSalida.getSolicitante().setCodPersonal(codPersonal);
            solicitudesSalida.getAreaDestinoSalida().setCodAreaEmpresa("86"); //area donde saldra los materiales
            solicitudesSalida.setFechaSolicitud(new Date());
            solicitudesSalida.setCodLoteProduccion("");
            solicitudesSalida.setObsSolicitud("SOLICITUD DE ORDEN DE TRABAJO Nro: "+solicitudMantenimiento.getNroOrdenTrabajo());
            solicitudesSalida.setEstadoSistema(1);
            solicitudesSalida.setControlCalidad(0);
            solicitudesSalida.setCodIngresoAlmacen(0);
            solicitudesSalida.getAlmacenes().setCodAlmacen(4); //se pide al almacen 4
            solicitudesSalida.setOrdenTrabajo(String.valueOf(solicitudMantenimiento.getNroOrdenTrabajo()));


            //se obtienen los items que tienen stock y se almacenan en la lista de ingresos a almacen
            Iterator i = solicitudMantenimientoDetalleMaterialesList.iterator();
            solicitudesSalidaDetalleList.clear();
            
            while(i.hasNext()){
                SolicitudMantenimientoDetalleMateriales solicitudMantenimientoDetalleMaterialesItem = (SolicitudMantenimientoDetalleMateriales)i.next();
                if(solicitudMantenimientoDetalleMaterialesItem.getCantidad()<=solicitudMantenimientoDetalleMaterialesItem.getDisponible()){
                    SolicitudesSalidaDetalle solicitudesSalidaDetalleItem = new SolicitudesSalidaDetalle();
                    solicitudesSalidaDetalleItem.getMateriales().setCodMaterial(solicitudMantenimientoDetalleMaterialesItem.getMateriales().getCodMaterial());
                    solicitudesSalidaDetalleItem.getMateriales().setNombreMaterial(solicitudMantenimientoDetalleMaterialesItem.getMateriales().getNombreMaterial());
                    solicitudesSalidaDetalleItem.setCantidad(solicitudMantenimientoDetalleMaterialesItem.getCantidad());
                    solicitudesSalidaDetalleItem.setUnidadesMedida(solicitudMantenimientoDetalleMaterialesItem.getMateriales().getUnidadesMedida());
                    solicitudesSalidaDetalleItem.setDisponible(solicitudMantenimientoDetalleMaterialesItem.getDisponible());
                    solicitudesSalidaDetalleList.add(solicitudesSalidaDetalleItem);                    
                }
            }

           
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public int obtieneCodSolicitudSalidaAlmacen() {
        int codSolicitudSalida = 0;
        try {
            String consulta = " SELECT (MAX(SS.COD_FORM_SALIDA)+1) AS COD_FORM_SALIDA FROM SOLICITUDES_SALIDA SS ";
            System.out.println("consulta "+consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            if (rs.next()) {
                codSolicitudSalida = rs.getInt("COD_FORM_SALIDA");
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
        return codSolicitudSalida;
    }

    public String verSolicitudCompra_action(){
        try {
            ManagedAccesoSistema m = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            String codPersonal = m.getUsuarioModuloBean().getCodUsuarioGlobal();

            // generacion de la cabecera de ingreso de solicitud a almacen

        
            solicitudesCompra = new SolicitudesCompra();
            solicitudesCompra.setCodSolicitudCompra(this.obtieneCodSolicitudOrdenCompra());
            solicitudesCompra.getTiposSolicitudCompra().setCodTipoSolicitudCompra(1); //tipo compra
            solicitudesCompra.getResponsableCompras().setCodPersonal("0");
            solicitudesCompra.getEstadosSolicitudCompra().setCodEstadoSolicitudCompra(1);
            solicitudesCompra.setEstadoSistema(1);
            solicitudesCompra.getPersonal().setCodPersonal(codPersonal);
            solicitudesCompra.getAreasEmpresa().setCodAreaEmpresa(m.getCodAreaEmpresaGlobal());
            solicitudesCompra.setFechaSolicitudCompra(new Date());
            solicitudesCompra.setObsSolicitudCompra("");
            solicitudesCompra.setFechaEnvio(null);


            //se obtienen los items que tienen stock y se almacenan en la lista de ingresos a almacen
            Iterator i = solicitudMantenimientoDetalleMaterialesList.iterator();
            solicitudesCompraDetalleList.clear();

            while(i.hasNext()){
                SolicitudMantenimientoDetalleMateriales solicitudMantenimientoDetalleMaterialesItem = (SolicitudMantenimientoDetalleMateriales)i.next();
                if(solicitudMantenimientoDetalleMaterialesItem.getCantidad()>solicitudMantenimientoDetalleMaterialesItem.getDisponible()){

                    SolicitudesCompraDetalle solicitudesCompraDetalleItem = new SolicitudesCompraDetalle();
                    solicitudesCompraDetalleItem.getMateriales().setCodMaterial(solicitudMantenimientoDetalleMaterialesItem.getMateriales().getCodMaterial());
                    solicitudesCompraDetalleItem.getMateriales().setNombreMaterial(solicitudMantenimientoDetalleMaterialesItem.getMateriales().getNombreMaterial());
                    solicitudesCompraDetalleItem.setCantSolicitada(solicitudMantenimientoDetalleMaterialesItem.getCantidad()-Float.valueOf(String.valueOf(solicitudMantenimientoDetalleMaterialesItem.getDisponible())));
                    solicitudesCompraDetalleItem.setUnidadesMedida(solicitudMantenimientoDetalleMaterialesItem.getMateriales().getUnidadesMedida());
                    solicitudesCompraDetalleList.add(solicitudesCompraDetalleItem);
                    
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String registrarSolicitudCompra_action(){
        try {
            mensaje = "";
             //verificar que no pasen el disponible de almacenes
            
            if(solicitudesCompraDetalleList.size()==0){
                    mensaje = " no existen items para realizar la solicitud de compra ";
                   return null;                
            }
            
            

            String consulta = " INSERT INTO dbo.SOLICITUDES_COMPRA( COD_SOLICITUD_COMPRA, COD_GESTION,   COD_TIPO_SOLICITUD_COMPRA,   COD_RESPONSABLE_COMPRAS,   COD_ESTADO_SOLICITUD_COMPRA, " +
                    " ESTADO_SISTEMA,  COD_PERSONAL,   COD_AREA_EMPRESA,   FECHA_SOLICITUD_COMPRA,    OBS_SOLICITUD_COMPRA,   FECHA_ENVIO) " +
                    " VALUES('" + solicitudesCompra.getCodSolicitudCompra() + "',  " +
                    " (SELECT G.COD_GESTION FROM GESTIONES G WHERE G.GESTION_ESTADO=1) ," +
                    " '"+solicitudesCompra.getTiposSolicitudCompra().getCodTipoSolicitudCompra()+"'," +
                    " '"+solicitudesCompra.getResponsableCompras().getCodPersonal()+"' , " +
                    " '"+solicitudesCompra.getEstadosSolicitudCompra().getCodEstadoSolicitudCompra() +"' , " +
                    " '"+solicitudesCompra.getEstadoSistema() +"' , " +
                    " '"+solicitudesCompra.getPersonal().getCodPersonal() +"' , " +
                    " '"+solicitudesCompra.getAreasEmpresa().getCodAreaEmpresa() +"', " +
                    " '"+sdf.format(solicitudesCompra.getFechaSolicitudCompra()) +"'," +
                    " '"+solicitudesCompra.getObsSolicitudCompra() + "'," +
                    " NULL )";
            

            System.out.println("consulta "+consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            st.executeUpdate(consulta);


            //se iteran los materiales que tienen stock y se realiza la solicitud de almacen
            Iterator i = solicitudesCompraDetalleList.iterator();
            while(i.hasNext()){
                
                   SolicitudesCompraDetalle solicitudesCompraDetalleItem =  (SolicitudesCompraDetalle) i.next();
                    consulta = " INSERT INTO SOLICITUDES_COMPRA_DETALLE( COD_MATERIAL,   COD_SOLICITUD_COMPRA,   CANT_SOLICITADA,   COD_UNIDAD_MEDIDA, " +
                        " OBS_MATERIAL_SOLICITUD )  VALUES( '" + solicitudesCompraDetalleItem.getMateriales().getCodMaterial() + "'," +
                        " '" + solicitudesCompra.getCodSolicitudCompra() + "'," +
                        " '" + solicitudesCompraDetalleItem.getCantSolicitada() + "' ," +
                        " '" + solicitudesCompraDetalleItem.getUnidadesMedida().getCodUnidadMedida() + "', " +
                        " '" + solicitudesCompraDetalleItem.getObsMaterialSolicitud()+"')";
                    System.out.println("consulta "+consulta);
                    st.executeUpdate(consulta);

            }
            
            //se actualiza la solicitud de mantenimiento con la solicitud de salida de almacenes

             consulta = " UPDATE SOLICITUDES_MANTENIMIENTO  SET " +
                    "  COD_SOLICITUD_COMPRA = '"+solicitudesCompra.getCodSolicitudCompra()+"' " +
                    " WHERE  COD_SOLICITUD_MANTENIMIENTO = '"+solicitudMantenimiento.getCodSolicitudMantenimiento()+"' ";
             solicitudMantenimiento.getSolicitudesSalida().setCodFormSalida(solicitudesSalida.getCodFormSalida());
             System.out.println("consulta "+consulta);
             st.executeUpdate(consulta);
             solicitudMantenimiento.getSolicitudesCompra().setCodSolicitudCompra(solicitudesCompra.getCodSolicitudCompra());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    

}

