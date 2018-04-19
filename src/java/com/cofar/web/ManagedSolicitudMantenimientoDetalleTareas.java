/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.web;

import com.cofar.bean.SolicitudMantenimiento;
import com.cofar.bean.SolicitudMantenimientoDetalleTareas;
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
import org.richfaces.component.html.HtmlDataTable;

/**
 *
 * @author hvaldivia
 */

public class ManagedSolicitudMantenimientoDetalleTareas {
    Connection con = null;
    SolicitudMantenimiento solicitudMantenimiento = new SolicitudMantenimiento();    
    SolicitudMantenimientoDetalleTareas solicitudMantenimientoDetalleTareas = new SolicitudMantenimientoDetalleTareas();
    SolicitudMantenimientoDetalleTareas solicitudMantenimientoDetalleTareasEditar = new SolicitudMantenimientoDetalleTareas();
    HtmlDataTable solicitudMantenimientoDetalleTareasDataTable = new HtmlDataTable();
    List solicitudMantenimientoDetalleTareasList = new ArrayList();
    List tiposTareasList  = new ArrayList();
    List personalList  = new ArrayList();
    List proovedorList = new ArrayList();
    String enteAsignado= "";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    private String[] codPersonalAsignar;
    String mensaje = "";

    public SolicitudMantenimiento getSolicitudMantenimiento() {
        return solicitudMantenimiento;
    }

    public void setSolicitudMantenimiento(SolicitudMantenimiento solicitudMantenimiento) {
        this.solicitudMantenimiento = solicitudMantenimiento;
    }

    public SolicitudMantenimientoDetalleTareas getSolicitudMantenimientoDetalleTareas() {
        return solicitudMantenimientoDetalleTareas;
    }

    public void setSolicitudMantenimientoDetalleTareas(SolicitudMantenimientoDetalleTareas solicitudMantenimientoDetalleTareas) {
        this.solicitudMantenimientoDetalleTareas = solicitudMantenimientoDetalleTareas;
    }

    public HtmlDataTable getSolicitudMantenimientoDetalleTareasDataTable() {
        return solicitudMantenimientoDetalleTareasDataTable;
    }

    public void setSolicitudMantenimientoDetalleTareasDataTable(HtmlDataTable solicitudMantenimientoDetalleTareasDataTable) {
        this.solicitudMantenimientoDetalleTareasDataTable = solicitudMantenimientoDetalleTareasDataTable;
    }

    public SolicitudMantenimientoDetalleTareas getSolicitudMantenimientoDetalleTareasEditar() {
        return solicitudMantenimientoDetalleTareasEditar;
    }

    public void setSolicitudMantenimientoDetalleTareasEditar(SolicitudMantenimientoDetalleTareas solicitudMantenimientoDetalleTareasEditar) {
        this.solicitudMantenimientoDetalleTareasEditar = solicitudMantenimientoDetalleTareasEditar;
    }

    public List getSolicitudMantenimientoDetalleTareasList() {
        return solicitudMantenimientoDetalleTareasList;
    }

    public void setSolicitudMantenimientoDetalleTareasList(List solicitudMantenimientoDetalleTareasList) {
        this.solicitudMantenimientoDetalleTareasList = solicitudMantenimientoDetalleTareasList;
    }

    public List getTiposTareasList() {
        return tiposTareasList;
    }

    public void setTiposTareasList(List tiposTareasList) {
        this.tiposTareasList = tiposTareasList;
    }

    public List getPersonalList() {
        return personalList;
    }

    public void setPersonalList(List personalList) {
        this.personalList = personalList;
    }

    public List getProovedorList() {
        return proovedorList;
    }

    public void setProovedorList(List proovedorList) {
        this.proovedorList = proovedorList;
    }

    public String getEnteAsignado() {
        return enteAsignado;
    }

    public void setEnteAsignado(String enteAsignado) {
        this.enteAsignado = enteAsignado;
    }

    public String[] getCodPersonalAsignar() {
        return codPersonalAsignar;
    }

    public void setCodPersonalAsignar(String[] codPersonalAsignar) {
        this.codPersonalAsignar = codPersonalAsignar;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    
    /** Creates a new instance of ManagedSolicitudMantenimientoDetalleTareas */
    public ManagedSolicitudMantenimientoDetalleTareas() {
    }
    
    public String getCargarContenidoSolicitudMantenimientoDetalleTareas(){
        try {
            this.cargarSolicitudMantenimientoDetalleTareas();
            this.cargarPersonal();
            this.cargarProovedor();
            this.cargarTiposTarea();
            enteAsignado="interno";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void cargarSolicitudMantenimientoDetalleTareas(){
        try {
                ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                Map<String, Object> sessionMap = externalContext.getSessionMap();                
                solicitudMantenimiento=(SolicitudMantenimiento)sessionMap.get("solicitudMantenimiento");
                
                con = Util.openConnection(con);
                String consulta = "  select s.COD_SOLICITUD_MANTENIMIENTO,t.COD_TIPO_TAREA,t.NOMBRE_TIPO_TAREA," +
                        " s.COD_PERSONAL, (select p.NOMBRE_PILA from personal p where p.COD_PERSONAL = s.COD_PERSONAL) NOMBRE_PILA, " +
                        " (select p.AP_PATERNO_PERSONAL from personal p where p.COD_PERSONAL = s.COD_PERSONAL) AP_PATERNO_PERSONAL, " +
                        " (select p.AP_MATERNO_PERSONAL from personal p where p.COD_PERSONAL = s.COD_PERSONAL) AP_MATERNO_PERSONAL, " +
                        " s.DESCRIPCION,s.FECHA_INICIAL,s.FECHA_FINAL,s.COD_PROVEEDOR, " +
                        " (select pr.NOMBRE_PROVEEDOR from PROVEEDORES pr where pr.COD_PROVEEDOR = s.COD_PROVEEDOR)  NOMBRE_PROVEEDOR " +
                        " from SOLICITUDES_MANTENIMIENTO_DETALLE_TAREAS s    inner join  TIPOS_TAREA t on s.COD_TIPO_TAREA = t.COD_TIPO_TAREA  " +
                        " where s.COD_SOLICITUD_MANTENIMIENTO = '"+solicitudMantenimiento.getCodSolicitudMantenimiento()+"'  ";
                
                System.out.println("consulta" + consulta);
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(consulta);
                solicitudMantenimientoDetalleTareasList.clear();
                while(rs.next()){
                    SolicitudMantenimientoDetalleTareas solicitudMantenimientoDetalleTareasItem = new SolicitudMantenimientoDetalleTareas();

                    solicitudMantenimientoDetalleTareasItem.getSolicitudMantenimiento().setCodSolicitudMantenimiento(rs.getString("COD_SOLICITUD_MANTENIMIENTO"));
                    solicitudMantenimientoDetalleTareasItem.getTiposTarea().setCodTipoTarea(rs.getInt("COD_TIPO_TAREA"));
                    solicitudMantenimientoDetalleTareasItem.getTiposTarea().setNombreTipoTarea(rs.getString("NOMBRE_TIPO_TAREA"));
                    solicitudMantenimientoDetalleTareasItem.getPersonal().setCodPersonal(rs.getString("COD_PERSONAL"));
                    solicitudMantenimientoDetalleTareasItem.getPersonal().setNombrePersonal(rs.getString("NOMBRE_PILA"));
                    solicitudMantenimientoDetalleTareasItem.getPersonal().setApPaternoPersonal(rs.getString("AP_PATERNO_PERSONAL"));
                    solicitudMantenimientoDetalleTareasItem.getPersonal().setApMaternoPersonal(rs.getString("AP_MATERNO_PERSONAL"));
                    solicitudMantenimientoDetalleTareasItem.getProveedores().setCodProveedor(rs.getString("COD_PROVEEDOR"));
                    solicitudMantenimientoDetalleTareasItem.getProveedores().setNombreProveedor(rs.getString("NOMBRE_PROVEEDOR"));
                    solicitudMantenimientoDetalleTareasItem.setDescripcion(rs.getString("DESCRIPCION"));
                    solicitudMantenimientoDetalleTareasItem.setFechaInicial(rs.getDate("FECHA_INICIAL"));
                    solicitudMantenimientoDetalleTareasItem.getFechaInicial().setTime(rs.getTimestamp("FECHA_INICIAL").getTime());
                    //bean.getFechaSolicitudMantenimiento().setTime(rs.getTimestamp("FECHA_SOLICITUD_MANTENIMIENTO").getTime());
                    solicitudMantenimientoDetalleTareasItem.setFechaFinal(rs.getDate("FECHA_FINAL"));

                    solicitudMantenimientoDetalleTareasList.add(solicitudMantenimientoDetalleTareasItem);
                }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


//    public String registrarSolicitudMantenimientoDetalleTareas_action(){
//        try {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm");
//            con=Util.openConnection(con);
//
//
//
//            String consulta = " INSERT INTO SOLICITUDES_MANTENIMIENTO_DETALLE_TAREAS (  COD_SOLICITUD_MANTENIMIENTO,   COD_TIPO_TAREA,   DESCRIPCION,   COD_PERSONAL, " +
//                       "  FECHA_INICIAL,   FECHA_FINAL,   COD_PROVEEDOR, HORAS_HOMBRE )  VALUES ( " +
//                       "   '"+solicitudMantenimiento.getCodSolicitudMantenimiento()+"','"+solicitudMantenimientoDetalleTareas.getTiposTarea().getCodTipoTarea()+"', '"+solicitudMantenimientoDetalleTareas.getDescripcion()+"',  '"+solicitudMantenimientoDetalleTareas.getPersonal().getCodPersonal()+"', '"+ sdf.format(solicitudMantenimientoDetalleTareas.getFechaInicial())+"',  '"+ sdf.format(solicitudMantenimientoDetalleTareas.getFechaFinal())+"',  '"+solicitudMantenimientoDetalleTareas.getProveedores().getCodProveedor()+"' " +
//                       "   , '"+solicitudMantenimientoDetalleTareas.getHorasHombre()+"' )";
//
//             if(enteAsignado.equals("interno")){
//                 consulta = " INSERT INTO SOLICITUDES_MANTENIMIENTO_DETALLE_TAREAS (  COD_SOLICITUD_MANTENIMIENTO,   COD_TIPO_TAREA,   DESCRIPCION,   COD_PERSONAL, " +
//                       "  FECHA_INICIAL,   FECHA_FINAL,   COD_PROVEEDOR,  HORAS_HOMBRE )  VALUES ( " +
//                       "   '"+solicitudMantenimiento.getCodSolicitudMantenimiento()+"','"+solicitudMantenimientoDetalleTareas.getTiposTarea().getCodTipoTarea()+"', '"+solicitudMantenimientoDetalleTareas.getDescripcion()+"',  '"+solicitudMantenimientoDetalleTareas.getPersonal().getCodPersonal()+"', '"+sdf.format(solicitudMantenimientoDetalleTareas.getFechaInicial())+"',  '"+sdf.format(solicitudMantenimientoDetalleTareas.getFechaFinal())+"', 0  " +
//                       "   , '"+solicitudMantenimientoDetalleTareas.getHorasHombre()+"' )";
//
//             }
//            if(enteAsignado.equals("externo")){
//                     consulta = " INSERT INTO SOLICITUDES_MANTENIMIENTO_DETALLE_TAREAS (  COD_SOLICITUD_MANTENIMIENTO,   COD_TIPO_TAREA,   DESCRIPCION,   COD_PERSONAL, " +
//                       "  FECHA_INICIAL,   FECHA_FINAL,   COD_PROVEEDOR,   HORAS_HOMBRE )  VALUES ( " +
//                       "   '"+solicitudMantenimiento.getCodSolicitudMantenimiento()+"','"+solicitudMantenimientoDetalleTareas.getTiposTarea().getCodTipoTarea()+"', " +
//                       "'"+solicitudMantenimientoDetalleTareas.getDescripcion()+"',  0, '"+sdf.format(solicitudMantenimientoDetalleTareas.getFechaInicial())+"',  " +
//                       "'"+ sdf.format(solicitudMantenimientoDetalleTareas.getFechaFinal())+"',  '"+solicitudMantenimientoDetalleTareas.getProveedores().getCodProveedor()+"', " +
//                       " '"+solicitudMantenimientoDetalleTareas.getHorasHombre()+"' )";
//             }
//
//
//               System.out.println("consulta" + consulta);
//
//               Statement st = con.createStatement();
//               st.executeUpdate(consulta);
//               this.cargarSolicitudMantenimientoDetalleTareas();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
    public String registrarSolicitudMantenimientoDetalleTareas_action(){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm");
            con=Util.openConnection(con);

            for(int i=0;i<codPersonalAsignar.length;i++)
            {
                    String consulta = " INSERT INTO SOLICITUDES_MANTENIMIENTO_DETALLE_TAREAS (  COD_SOLICITUD_MANTENIMIENTO,   COD_TIPO_TAREA,   DESCRIPCION,   COD_PERSONAL, " +
                           "  FECHA_INICIAL,   FECHA_FINAL,   COD_PROVEEDOR, HORAS_HOMBRE,FECHA_ASIGNACION)  VALUES ( " +
                           "   '"+solicitudMantenimiento.getCodSolicitudMantenimiento()+"','"+solicitudMantenimientoDetalleTareas.getTiposTarea().getCodTipoTarea()+"', '"+solicitudMantenimientoDetalleTareas.getDescripcion()+"',  '"+codPersonalAsignar[i]+"', '"+ sdf.format(solicitudMantenimientoDetalleTareas.getFechaInicial())+"',  '"+ sdf.format(solicitudMantenimientoDetalleTareas.getFechaFinal())+"',  '"+solicitudMantenimientoDetalleTareas.getProveedores().getCodProveedor()+"' " +
                           "   , '"+solicitudMantenimientoDetalleTareas.getHorasHombre()+"','"+sdf.format(solicitudMantenimientoDetalleTareas.getFechaInicial())+"' )";

                     if(enteAsignado.equals("interno")){
                         consulta = " INSERT INTO SOLICITUDES_MANTENIMIENTO_DETALLE_TAREAS (  COD_SOLICITUD_MANTENIMIENTO,   COD_TIPO_TAREA,   DESCRIPCION,   COD_PERSONAL, " +
                               "  FECHA_INICIAL,   FECHA_FINAL,   COD_PROVEEDOR,  HORAS_HOMBRE,FECHA_ASIGNACION)  VALUES ( " +
                               "   '"+solicitudMantenimiento.getCodSolicitudMantenimiento()+"','"+solicitudMantenimientoDetalleTareas.getTiposTarea().getCodTipoTarea()+"', '"+solicitudMantenimientoDetalleTareas.getDescripcion()+"',  '"+codPersonalAsignar[i]+"', '"+sdf.format(solicitudMantenimientoDetalleTareas.getFechaInicial())+"',  '"+sdf.format(solicitudMantenimientoDetalleTareas.getFechaFinal())+"', 0  " +
                               "   , '"+solicitudMantenimientoDetalleTareas.getHorasHombre()+"','"+sdf.format(solicitudMantenimientoDetalleTareas.getFechaInicial())+"' )";

                     }
                    if(enteAsignado.equals("externo")){
                             consulta = " INSERT INTO SOLICITUDES_MANTENIMIENTO_DETALLE_TAREAS (  COD_SOLICITUD_MANTENIMIENTO,   COD_TIPO_TAREA,   DESCRIPCION,   COD_PERSONAL, " +
                               "  FECHA_INICIAL,   FECHA_FINAL,   COD_PROVEEDOR,   HORAS_HOMBRE ,FECHA_ASIGNACION)  VALUES ( " +
                               "   '"+solicitudMantenimiento.getCodSolicitudMantenimiento()+"','"+solicitudMantenimientoDetalleTareas.getTiposTarea().getCodTipoTarea()+"', " +
                               "'"+solicitudMantenimientoDetalleTareas.getDescripcion()+"',  0, '"+sdf.format(solicitudMantenimientoDetalleTareas.getFechaInicial())+"',  " +
                               "'"+ sdf.format(solicitudMantenimientoDetalleTareas.getFechaFinal())+"',  '"+solicitudMantenimientoDetalleTareas.getProveedores().getCodProveedor()+"', " +
                               " '"+solicitudMantenimientoDetalleTareas.getHorasHombre()+"','"+sdf.format(solicitudMantenimientoDetalleTareas.getFechaInicial())+"')";
                     }


                       System.out.println("consulta" + consulta);

                       Statement st = con.createStatement();
                       st.executeUpdate(consulta);
            }
               this.cargarSolicitudMantenimientoDetalleTareas();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public String agregarSolicitudMantenimientoDetalleTareas_action(){
        try {
                solicitudMantenimientoDetalleTareas = new SolicitudMantenimientoDetalleTareas();
                enteAsignado = "interno";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public String eliminarSolicitudMantenimientoDetalleTareas_action(){
        try {
               con=Util.openConnection(con);
               String consulta = "";
               Iterator i = solicitudMantenimientoDetalleTareasList.iterator();
               while(i.hasNext()){
                   SolicitudMantenimientoDetalleTareas solicitudMantenimientoDetalleTareasItem = (SolicitudMantenimientoDetalleTareas)i.next();
                   if(solicitudMantenimientoDetalleTareasItem.getChecked().booleanValue()==true){

                         consulta = " DELETE FROM SOLICITUDES_MANTENIMIENTO_DETALLE_TAREAS " +
                                " WHERE COD_SOLICITUD_MANTENIMIENTO='"+solicitudMantenimientoDetalleTareasItem.getSolicitudMantenimiento().getCodSolicitudMantenimiento()+"' " +
                                " AND COD_TIPO_TAREA ='"+solicitudMantenimientoDetalleTareasItem.getTiposTarea().getCodTipoTarea()+"'" +
                                " AND COD_PERSONAL = '"+solicitudMantenimientoDetalleTareasItem.getPersonal().getCodPersonal()+"' ";

                                System.out.println("consulta" + consulta) ;
                        Statement st = con.createStatement();
                        st = con.createStatement();
                        st.executeUpdate(consulta);

                       break;
                   }


               }
               this.cargarSolicitudMantenimientoDetalleTareas();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public int nroOrdenTrabajo(){
        int nroOrdenTrabajo = 0;
        try {
            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement();
            String consulta = " SELECT ISNULL(MAX(S.NRO_ORDEN_TRABAJO),0)+1 nroOrdenTrabajo FROM SOLICITUDES_MANTENIMIENTO S ";
            System.out.println("consulta " + consulta);
            ResultSet rs = st.executeQuery(consulta);
            if(rs.next()){
                nroOrdenTrabajo= rs.getInt("nroOrdenTrabajo");
            }
            con.close();
            st.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nroOrdenTrabajo;
    }
    public String generarSolicitudMantenimiento_action(){
        try {
            mensaje = "";
        if(solicitudMantenimientoDetalleTareasList.size()==0){
            return null;
        }
            if(solicitudMantenimiento.getNroOrdenTrabajo()>0)
            {
            return null;
            }
        String consulta = " UPDATE SOLICITUDES_MANTENIMIENTO " +
                    " SET COD_ESTADO_SOLICITUD_MANTENIMIENTO = '2' " +
                    " , FECHA_CAMBIO_ESTADOSOLICITUD ='"+sdf.format(new Date())+"'," +
                     " NRO_ORDEN_TRABAJO = (SELECT ISNULL(MAX(S.NRO_ORDEN_TRABAJO),0)+1 FROM SOLICITUDES_MANTENIMIENTO S WHERE S.COD_ESTADO_SOLICITUD_MANTENIMIENTO IN (2,4)) " +
                    " WHERE COD_SOLICITUD_MANTENIMIENTO = '"+solicitudMantenimiento.getCodSolicitudMantenimiento()+"'  " ;
        consulta = " UPDATE SOLICITUDES_MANTENIMIENTO " +
                    " SET COD_ESTADO_SOLICITUD_MANTENIMIENTO = '2' " +
                    " , FECHA_CAMBIO_ESTADOSOLICITUD ='"+sdf.format(new Date())+"'," +
                     " NRO_ORDEN_TRABAJO = '"+this.nroOrdenTrabajo()+"' " + //(SELECT ISNULL(MAX(S.NRO_ORDEN_TRABAJO),0)+1 FROM SOLICITUDES_MANTENIMIENTO S WHERE S.COD_ESTADO_SOLICITUD_MANTENIMIENTO IN (2,4))
                    " WHERE COD_SOLICITUD_MANTENIMIENTO = '"+solicitudMantenimiento.getCodSolicitudMantenimiento()+"'  " ;
        System.out.println("consulta " + consulta);
        Connection con = null;
        con = Util.openConnection(con);
        Statement st = con.createStatement();
        st.executeUpdate(consulta);
        mensaje = " se aprobo la solicitud de mantenimiento ";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String editarSolicitudMantenimientoDetalleTareas_action(){
        try {
               con=Util.openConnection(con);
               Iterator i = solicitudMantenimientoDetalleTareasList.iterator();
               while(i.hasNext()){
                   SolicitudMantenimientoDetalleTareas solicitudMantenimientoDetalleTareasItem = (SolicitudMantenimientoDetalleTareas) i.next();
                   if(solicitudMantenimientoDetalleTareasItem.getChecked().booleanValue()==true){
                       solicitudMantenimientoDetalleTareas =solicitudMantenimientoDetalleTareasItem;
                       solicitudMantenimientoDetalleTareasEditar.getSolicitudMantenimiento().setCodSolicitudMantenimiento(solicitudMantenimientoDetalleTareasItem.getSolicitudMantenimiento().getCodSolicitudMantenimiento());
                       solicitudMantenimientoDetalleTareasEditar.getTiposTarea().setCodTipoTarea(solicitudMantenimientoDetalleTareasItem.getTiposTarea().getCodTipoTarea());
                       solicitudMantenimientoDetalleTareasEditar.getPersonal().setCodPersonal(solicitudMantenimientoDetalleTareasItem.getPersonal().getCodPersonal());

                       if(!solicitudMantenimientoDetalleTareas.getPersonal().getCodPersonal().equals("0")){
                           enteAsignado="interno";
                       }
                       if(!solicitudMantenimientoDetalleTareas.getProveedores().getCodProveedor().equals("0")){
                           enteAsignado="externo";
                       }
                       System.out.println("enteAsignado " + enteAsignado);


                       break;
                   }
               }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public String guardarEdicionSolicitudMantenimientoDetalleTareas_action(){
        try {
            con=Util.openConnection(con);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm");


            String consulta = " UPDATE SOLICITUDES_MANTENIMIENTO_DETALLE_TAREAS  SET   " +
                    "  COD_TIPO_TAREA = '"+solicitudMantenimientoDetalleTareas.getTiposTarea().getCodTipoTarea()+"'," +
                    "  DESCRIPCION = '"+solicitudMantenimientoDetalleTareas.getDescripcion()+"', ";
                    if(enteAsignado.equals("interno")){
                        consulta += "  COD_PERSONAL = '"+solicitudMantenimientoDetalleTareas.getPersonal().getCodPersonal()+"'," +
                        "  COD_PROVEEDOR = 0, " ;
                    }else{
                        consulta += "  COD_PERSONAL = '0'," +
                        "  COD_PROVEEDOR = "+solicitudMantenimientoDetalleTareas.getProveedores().getCodProveedor()+", " ;
                    }
                    consulta += "  FECHA_INICIAL = '"+sdf.format(solicitudMantenimientoDetalleTareas.getFechaInicial())+"',  " +
                    "  FECHA_FINAL = '"+ sdf.format(solicitudMantenimientoDetalleTareas.getFechaFinal())+"'  " +
                    " ,FECHA_ASIGNACION = '"+sdf.format(solicitudMantenimientoDetalleTareas.getFechaInicial())+"' " +
                    "  WHERE  COD_SOLICITUD_MANTENIMIENTO = '"+solicitudMantenimientoDetalleTareasEditar.getSolicitudMantenimiento().getCodSolicitudMantenimiento()+"' " +
                    "  AND COD_TIPO_TAREA = '"+solicitudMantenimientoDetalleTareasEditar.getTiposTarea().getCodTipoTarea()+"'" +
                    "  AND COD_PERSONAL = '"+solicitudMantenimientoDetalleTareasEditar.getPersonal().getCodPersonal()+"'  ";

             
             System.out.println("consulta" + consulta);
             Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             st.executeUpdate(consulta);
             this.cargarSolicitudMantenimientoDetalleTareas();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    


    public String verSolicitudMantenimientoDetalleTareasMaterial_action(){
        try {
            SolicitudMantenimientoDetalleTareas SolicitudMantenimientoDetalleTareasItem = (SolicitudMantenimientoDetalleTareas)solicitudMantenimientoDetalleTareasDataTable.getRowData();
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            Map<String, Object> sessionMap = externalContext.getSessionMap();
            sessionMap.put("SolicitudMantenimientoDetalleTareas", SolicitudMantenimientoDetalleTareasItem);
            this.redireccionar("../SolicitudMantenimientoDetalleTareasMaterial/navegadorSolicitudMantenimientoDetalleTareasMaterial.jsf");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public String verSolicitudMantenimientoDetalleTareasMaquinaria_action(){
        try {
            SolicitudMantenimientoDetalleTareas SolicitudMantenimientoDetalleTareasItem = (SolicitudMantenimientoDetalleTareas)solicitudMantenimientoDetalleTareasDataTable.getRowData();
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            Map<String, Object> sessionMap = externalContext.getSessionMap();
            sessionMap.put("SolicitudMantenimientoDetalleTareas", SolicitudMantenimientoDetalleTareasItem);
            this.redireccionar("../SolicitudMantenimientoDetalleTareasMaquinaria/navegadorSolicitudMantenimientoDetalleTareasMaquinaria.jsf");
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

    public void cargarTiposTarea() {
        try {
            con = (Util.openConnection(con));
            String consulta = " SELECT COD_TIPO_TAREA,NOMBRE_TIPO_TAREA, COD_ESTADO_REGISTRO " +
                    " FROM TIPOS_TAREA WHERE COD_ESTADO_REGISTRO = 1 ";

            ResultSet rs = null;
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            tiposTareasList.clear();
            rs = st.executeQuery(consulta);
            while (rs.next()) {
                tiposTareasList.add(new SelectItem(rs.getString("COD_TIPO_TAREA"), rs.getString("NOMBRE_TIPO_TAREA")));
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

    public void cargarPersonal() {
        try {
            con = (Util.openConnection(con));
            String consulta = " select P.COD_PERSONAL,(P.NOMBRES_PERSONAL +' ' + P.AP_PATERNO_PERSONAL +' '+ P.AP_MATERNO_PERSONAL)  NOMBRES_PERSONAL " +
                    " from  personal P where P.cod_area_empresa=86 " +
                    " AND P.COD_ESTADO_PERSONA=1 ";

            ResultSet rs = null;

            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            personalList.clear();
            rs = st.executeQuery(consulta);
            while (rs.next()) {
                personalList.add(new SelectItem(rs.getString("COD_PERSONAL"), rs.getString("NOMBRES_PERSONAL")));
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

    public void cargarProovedor() {
        try {
            con = (Util.openConnection(con));
            String consulta = " SELECT PR.COD_PROVEEDOR,PR.NOMBRE_PROVEEDOR  from PROVEEDORES PR WHERE PR.COD_ESTADO_PROVEEDOR=1 AND PR.COD_ESTADO_REGISTRO=1 " +
                    " ORDER BY PR.NOMBRE_PROVEEDOR ASC ";


            ResultSet rs = null;

            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            proovedorList.clear();
            rs = st.executeQuery(consulta);
            while (rs.next()) {
                proovedorList.add(new SelectItem(rs.getString("COD_PROVEEDOR"), rs.getString("NOMBRE_PROVEEDOR")));
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


}
