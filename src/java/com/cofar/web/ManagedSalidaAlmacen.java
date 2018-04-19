/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofar.web;

import com.cofar.bean.SalidaTransaccionSolicitud;
import com.cofar.bean.SalidasAlmacen;
import com.cofar.bean.SalidasAlmacenDetalle;
import com.cofar.service.AssignCostServiceMultiple;
import com.cofar.thread.ThreadCosteoByCodSalida;
import com.cofar.util.Util;
import com.cofar.util.Utiles;
import com.cofar.util.correos.EnvioCorreoNotificacionSolicitudSalidaAlmacen;
import com.cofar.util.correos.EnvioCorreoNotificacionValidacionSalidaAlmacen;
import java.sql.CallableStatement;
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

/**
 *
 * @author hvaldivia
 */
public class ManagedSalidaAlmacen extends ManagedBean {

    private Logger LOGGER; 
    Connection con = null;
    List salidasAlmacenList = new ArrayList();
    SalidasAlmacen salidasAlmacenBuscar = new SalidasAlmacen();
    List gestionesList = new ArrayList();
    List tiposSalidaAlmacenList = new ArrayList();
    List estadosSalidaAlmacenList = new ArrayList();
    List areasEmpresalist = new ArrayList();
    List componentesProdList = new ArrayList();
    String mensaje = "";
    ManagedAccesoSistema usuario = new ManagedAccesoSistema();
    private Date horaOriginal = null;
    private SalidasAlmacen salidasAlmacenFecha = null;
    private SalidasAlmacen salidasAlmacenSeleccion = null; 
    private boolean administradorAlmacen = false;
    private SalidasAlmacen salidaAlmacenGestionar = new SalidasAlmacen();
    private SalidaTransaccionSolicitud salidaTransaccionSolicitudGestionar = new SalidaTransaccionSolicitud();
    private boolean salidaEditable = false ;
    private boolean salidaVisible = false ;
    private String motivoAnulacionSalida = "";
    
    // Variables Permisos
    private boolean permisoEditarEmpaquePrimarioLote = false;
    private boolean permisoSolicitarAnular = false;
    private boolean permisoSolicitarEditar = false;
    private boolean permisoValidarAnular = false;
    private boolean permisoValidarEditar = false;   
    private boolean permisoAnularDirectamente = false;
    private boolean permisoEditarDirectamente = false;
    private boolean permisoEditarHoy = false;
    private boolean permisoAnularHoy = false;
    
    /**
     * Creates a new instance of ManagedSalidaAlmacen
     */
    public ManagedSalidaAlmacen() {
        try {
            LOGGER = LogManager.getLogger("Salidas");
            con = (Util.openConnection(con));
            ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            salidasAlmacenBuscar.getGestiones().setCodGestion(usuario.getGestionesGlobal().getCodGestion());
            salidasAlmacenBuscar.setFechaSalidaAlmacen(null);
            areasEmpresalist = this.cargarAreasEmpresa();
            componentesProdList = this.cargarComponentesProd();
            this.cargarEstadosSalidaAlmacen();
            this.cargarGestiones();
            this.cargarTiposSalidaAlmacen();
        } catch (Exception e) {
            LOGGER.warn(e);
        }
    }

    private void cargarPermisoPersonal() {
        administradorAlmacen = false;
        try {
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

    public String getCargarContenidoSalidasAlmacen() {
        begin = 1;
        end = numrows;
        this.cargarPermisoPersonal();
        try {
            this.cargarSalidasAlmacen();
            this.cargarPermisosEspecialesPersonal();
        } catch (Exception e) {
            LOGGER.warn(e);
        }
        return null;
    }

    public void cargarSalidasAlmacen() {
        try {
            usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

            String consulta
                    = "select * from (select ROW_NUMBER() OVER (ORDER BY s.FECHA_SALIDA_ALMACEN desc) as 'FILAS', s.NRO_SALIDA_ALMACEN,s.COD_PERSONAL, s.COD_SALIDA_ALMACEN, isnull(s.COD_LOTE_PRODUCCION,'') as COD_LOTE_PRODUCCION,g.NOMBRE_GESTION, "
                    + " s.COD_ESTADO_SALIDA_ALMACEN,e.NOMBRE_ESTADO_SALIDA_ALMACEN, isnull(s.orden_trabajo,'') as orden_trabajo, "
                    + " (select p.ap_paterno_personal+' '+ p.ap_materno_personal+' '+p.nombres_personal from personal p where s.cod_personal=p.cod_personal)as nombre_personal, "
                    + " s.COD_TIPO_SALIDA_ALMACEN,tsa.NOMBRE_TIPO_SALIDA_ALMACEN,s.COD_AREA_EMPRESA,a.NOMBRE_AREA_EMPRESA, "
                    + " s.COD_PROD,c.nombre_prod_semiterminado, isnull(s.COD_PRESENTACION,'') as COD_PRESENTACION, prp.NOMBRE_PRODUCTO_PRESENTACION,s.FECHA_SALIDA_ALMACEN,s.OBS_SALIDA_ALMACEN, "
                    + " s.COD_ALMACEN ,sts.COD_ESTADO_TRANSACCION_SOLICITUD,isnull(ets.NOMBRE_ESTADO_TRANSACCION_SOLICITUD,'') as NOMBRE_ESTADO_TRANSACCION_SOLICITUD,m.cod_maquina,m.nombre_maquina "
                    + " , NOMBRE_VALIDADOR, OBSERVACION_VALIDADOR, FECHA_VALIDACION, tsa.EDITABLE, tsa.AUTOMATICO, tsa.REGISTRA_LOTE, tsa.REGISTRA_PRESENTACION, tsa.REGISTRA_PRODUCTO"
                    + " from SALIDAS_ALMACEN s  "
                    + " inner join TIPOS_SALIDAS_ALMACEN tsa on tsa.COD_TIPO_SALIDA_ALMACEN = s.COD_TIPO_SALIDA_ALMACEN "
                    + " inner join ESTADOS_SALIDAS_ALMACEN e on e.COD_ESTADO_SALIDA_ALMACEN = s.COD_ESTADO_SALIDA_ALMACEN "
                    + " inner join AREAS_EMPRESA a on a.COD_AREA_EMPRESA = s.COD_AREA_EMPRESA "
                    + " left outer join COMPONENTES_PROD c on c.COD_COMPPROD = s.COD_PROD "
                    + " left outer join PRESENTACIONES_PRODUCTO prp on prp.cod_presentacion = s.COD_PRESENTACION "
                    + " inner join gestiones g on g.COD_GESTION = s.COD_GESTION "
                    + " left join maquinarias m on m.cod_maquina = s.cod_maquina "
                    + " left join "
                    + " (   SELECT sts_a.COD_SALIDA_ALMACEN, COD_ESTADO_TRANSACCION_SOLICITUD, isnull(OBSERVACION_VALIDADOR,'') as OBSERVACION_VALIDADOR "
                        + " ,(ps.NOMBRES_PERSONAL + ' ' + ps.AP_PATERNO_PERSONAL + ' ' + ps.AP_MATERNO_PERSONAL) as NOMBRE_VALIDADOR, FECHA_VALIDACION"
                        + " FROM SALIDA_TRANSACCION_SOLICITUD sts_a"
                        + " inner join "
                        + " (   SELECT COD_SALIDA_ALMACEN, MAX(FECHA_SOLICITUD) AS MAX_FECHA_SOLICITUD"
                            + " FROM SALIDA_TRANSACCION_SOLICITUD"
                            + " GROUP BY COD_SALIDA_ALMACEN"
                        + " ) sts_group on sts_a.COD_SALIDA_ALMACEN = sts_group.COD_SALIDA_ALMACEN and sts_a.FECHA_SOLICITUD = sts_group.MAX_FECHA_SOLICITUD"
                        + " LEFT JOIN PERSONAL ps on ps.COD_PERSONAL = sts_a.COD_PERSONAL_VALIDADOR"
                        + " where COD_ESTADO_TRANSACCION_SOLICITUD not in (7)"
                    + " ) sts on sts.COD_SALIDA_ALMACEN = s.COD_SALIDA_ALMACEN"
                    + " left join ESTADO_TRANSACCION_SOLICITUD ets on ets.COD_ESTADO_TRANSACCION_SOLICITUD = sts.COD_ESTADO_TRANSACCION_SOLICITUD "
                    + " where 0=0 and s.cod_almacen = '" + usuario.getAlmacenesGlobal().getCodAlmacen() + "' "
                    + " and 1=1 ";

            if (salidasAlmacenBuscar.getFechaSalidaAlmacen() != null) {
                consulta = consulta + " and s.FECHA_SALIDA_ALMACEN between '" + sdf.format(salidasAlmacenBuscar.getFechaSalidaAlmacen()) + " 00:00:00' "
                        + " and  '" + sdf.format(salidasAlmacenBuscar.getFechaSalidaAlmacen()) + " 23:59:59' ";
            }
            if (!salidasAlmacenBuscar.getGestiones().getCodGestion().equals("") && !salidasAlmacenBuscar.getGestiones().getCodGestion().equals("0")) {
                consulta = consulta + " and s.COD_GESTION = '" + salidasAlmacenBuscar.getGestiones().getCodGestion() + "' ";
            }
            if (salidasAlmacenBuscar.getNroSalidaAlmacen() > 0) {
                consulta = consulta + " and s.NRO_SALIDA_ALMACEN = '" + salidasAlmacenBuscar.getNroSalidaAlmacen() + "' ";
            }
            if (!salidasAlmacenBuscar.getCodLoteProduccion().equals("")) {
                consulta = consulta + " and s.COD_LOTE_PRODUCCION = '" + salidasAlmacenBuscar.getCodLoteProduccion() + "' ";
            }
            if (salidasAlmacenBuscar.getTiposSalidasAlmacen().getCodTipoSalidaAlmacen() > 0) {
                consulta = consulta + " and s.COD_TIPO_SALIDA_ALMACEN = '" + salidasAlmacenBuscar.getTiposSalidasAlmacen().getCodTipoSalidaAlmacen() + "' ";
            }
            if (salidasAlmacenBuscar.getEstadosSalidasAlmacen().getCodEstadoSalidaAlmacen() > 0) {
                consulta = consulta + " and s.COD_ESTADO_SALIDA_ALMACEN = '" + salidasAlmacenBuscar.getEstadosSalidasAlmacen().getCodEstadoSalidaAlmacen() + "' ";
            }
            if (!salidasAlmacenBuscar.getAreasEmpresa().getCodAreaEmpresa().equals("0") && !salidasAlmacenBuscar.getAreasEmpresa().getCodAreaEmpresa().equals("")) {
                consulta = consulta + " and s.COD_AREA_EMPRESA = '" + salidasAlmacenBuscar.getAreasEmpresa().getCodAreaEmpresa() + "' ";
            }
            if (!salidasAlmacenBuscar.getComponentesProd().getCodCompprod().equals("0") && !salidasAlmacenBuscar.getComponentesProd().getCodCompprod().equals("")) {
                consulta = consulta + " and s.COD_PROD =  '" + salidasAlmacenBuscar.getComponentesProd().getCodCompprod() + "' ";
            }
            if (!salidasAlmacenBuscar.getOrdenTrabajo().equals("")) {
                consulta = consulta + " and s.orden_trabajo = '" + salidasAlmacenBuscar.getOrdenTrabajo() + "' ";
            }
            consulta = consulta + " ) AS listado_ordenes_compra where FILAS BETWEEN " + begin + " AND " + end + "    ";

            LOGGER.info("consulta cargarSalidasAlmacen: " + consulta);

            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            salidasAlmacenList.clear();
            while (rs.next()) {
                SalidasAlmacen salidasAlmacen = new SalidasAlmacen();
                salidasAlmacen.getSalidaTransaccionSolicitud().getEstadoTransaccionSolicitud().setCodEstadoTransaccionSolicitud(rs.getInt("COD_ESTADO_TRANSACCION_SOLICITUD"));
                salidasAlmacen.getSalidaTransaccionSolicitud().getEstadoTransaccionSolicitud().setNombreEstadoTransaccionSolicitud(rs.getString("NOMBRE_ESTADO_TRANSACCION_SOLICITUD"));
                salidasAlmacen.getSalidaTransaccionSolicitud().getPersonalValidador().setNombrePersonal(rs.getString("NOMBRE_VALIDADOR"));
                salidasAlmacen.getSalidaTransaccionSolicitud().setObservacionValidador(rs.getString("OBSERVACION_VALIDADOR"));
                salidasAlmacen.getSalidaTransaccionSolicitud().setFechaValidacion(rs.getTimestamp("FECHA_VALIDACION"));
                salidasAlmacen.setNroSalidaAlmacen(rs.getInt("NRO_SALIDA_ALMACEN"));
                salidasAlmacen.getPersonal().setCodPersonal(rs.getString("COD_PERSONAL"));
                salidasAlmacen.setCodSalidaAlmacen(rs.getInt("COD_SALIDA_ALMACEN"));
                salidasAlmacen.setCodLoteProduccion(rs.getString("COD_LOTE_PRODUCCION"));
                salidasAlmacen.getGestiones().setNombreGestion(rs.getString("NOMBRE_GESTION"));
                salidasAlmacen.getEstadosSalidasAlmacen().setCodEstadoSalidaAlmacen(rs.getInt("COD_ESTADO_SALIDA_ALMACEN"));
                salidasAlmacen.getEstadosSalidasAlmacen().setNombreEstadoSalidaAlmacen(rs.getString("NOMBRE_ESTADO_SALIDA_ALMACEN"));
                salidasAlmacen.setOrdenTrabajo(rs.getString("orden_trabajo"));
                salidasAlmacen.getPersonal().setNombrePersonal(rs.getString("nombre_personal"));
                salidasAlmacen.getTiposSalidasAlmacen().setEditable(rs.getInt("EDITABLE")==1);
                salidasAlmacen.getTiposSalidasAlmacen().setAutomatico(rs.getInt("AUTOMATICO")==1);
                salidasAlmacen.getTiposSalidasAlmacen().setRegistraLote(rs.getInt("REGISTRA_LOTE")==1);
                salidasAlmacen.getTiposSalidasAlmacen().setRegistraPresentacion(rs.getInt("REGISTRA_PRESENTACION")==1);
                salidasAlmacen.getTiposSalidasAlmacen().setRegistraProducto(rs.getInt("REGISTRA_PRODUCTO")==1);
                salidasAlmacen.getTiposSalidasAlmacen().setCodTipoSalidaAlmacen(rs.getInt("COD_TIPO_SALIDA_ALMACEN"));
                salidasAlmacen.getTiposSalidasAlmacen().setNombreTipoSalidaAlmacen(rs.getString("NOMBRE_TIPO_SALIDA_ALMACEN"));
                salidasAlmacen.getEstadosSalidasAlmacen().setCodEstadoSalidaAlmacen(rs.getInt("COD_ESTADO_SALIDA_ALMACEN"));
                salidasAlmacen.getEstadosSalidasAlmacen().setNombreEstadoSalidaAlmacen(rs.getString("NOMBRE_ESTADO_SALIDA_ALMACEN"));
                salidasAlmacen.getAreasEmpresa().setCodAreaEmpresa(rs.getString("COD_AREA_EMPRESA"));
                salidasAlmacen.getAreasEmpresa().setNombreAreaEmpresa(rs.getString("NOMBRE_AREA_EMPRESA"));
                salidasAlmacen.getComponentesProd().setCodCompprod(rs.getString("COD_PROD"));
                salidasAlmacen.getComponentesProd().setNombreProdSemiterminado(rs.getString("nombre_prod_semiterminado"));
                salidasAlmacen.getPresentacionesProducto().setCodPresentacion(rs.getString("COD_PRESENTACION"));
                salidasAlmacen.getPresentacionesProducto().setNombreProductoPresentacion(rs.getString("NOMBRE_PRODUCTO_PRESENTACION"));
                salidasAlmacen.getAlmacenes().setCodAlmacen(rs.getInt("COD_ALMACEN"));
                //inicio ale validar
                salidasAlmacen.setFechaSalidaAlmacen(rs.getTimestamp("FECHA_SALIDA_ALMACEN"));
                salidasAlmacen.getMaquinaria().setCodMaquina(rs.getString("cod_maquina"));
                salidasAlmacen.getMaquinaria().setNombreMaquina(rs.getString("nombre_maquina"));
                //final ale validar
                //inicio alejandro 2
                if (salidasAlmacen.getEstadosSalidasAlmacen().getCodEstadoSalidaAlmacen() == 2) {
                    salidasAlmacen.setColorFila("b");
                }
                //final alejandro 2
                salidasAlmacen.setObsSalidaAlmacen(rs.getString("OBS_SALIDA_ALMACEN"));
                salidasAlmacenList.add(salidasAlmacen);
            }
            cantidadfilas = salidasAlmacenList.size();
            st.close();
            rs.close();

        } catch (Exception e) {
            LOGGER.warn(e);
        }
    }
    public void cargarPermisosEspecialesPersonal() {
        try {
            ManagedAccesoSistema usuarioLogueado = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");

            String consulta
                    = " SELECT cpb.COD_TIPO_PERMISO_ESPECIAL_BACO "
                    + " FROM CONFIGURACION_PERMISO_ESPECIAL_BACO cpb"
                    + " WHERE cpb.COD_ALMACEN = " + usuarioLogueado.getAlmacenesGlobal().getCodAlmacen()
                    + " AND cpb.COD_PERSONAL = " + usuarioLogueado.getUsuarioModuloBean().getCodUsuarioGlobal();

            LOGGER.info("consulta listar permisosEspecialesPersonal: " + consulta);

            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            
            permisoSolicitarAnular = false;
            permisoSolicitarEditar = false;
            permisoValidarAnular = false;
            permisoValidarEditar = false;
            permisoAnularDirectamente = false;
            permisoEditarDirectamente = false;
            permisoEditarHoy = false;
            permisoAnularHoy = false;
            permisoEditarEmpaquePrimarioLote = false;
            while (rs.next()) {
                
                switch(rs.getInt("COD_TIPO_PERMISO_ESPECIAL_BACO")){
                    case 1: permisoAnularDirectamente = true; 
                        break;
                    case 2: permisoEditarDirectamente = true; 
                        break;
                    case 3: permisoSolicitarAnular = true; 
                        break;
                    case 4: permisoSolicitarEditar = true; 
                        break;
                    case 5: permisoValidarAnular = true; 
                        break;
                    case 6: permisoValidarEditar = true; 
                        break;
                    case 13: permisoEditarHoy = true; 
                        break;
                    case 20: permisoAnularHoy = true; 
                        break;
                    case 22: permisoEditarEmpaquePrimarioLote = true;
                        break;
                }
            }
            
            st.close();
            rs.close();

        } catch (Exception e) {
            LOGGER.warn(e);
        }
    }

    public String nuevaSalidaTransaccionSolicitudEditar_action() throws SQLException{
        mensaje = "";
        salidaEditable = false;
        salidaVisible = false;
        //--VALIDACION, QUE NO EXISTAN SOLICITUDES PENDIENTES SOBRE ESTA SALIDA DE ALMACEN
        if (verificarSalidaTieneSolicitudesPendientes()) {
            mensaje="La salida tiene solicitudes pendientes.";
            return null;
        }
        if(verificaTransaccionCerradaSalidaAlmacen(salidaAlmacenGestionar)){
            mensaje="Las Transacciones para la fecha de salida seleccionada fueron cerredas.";
            return null;
        }
        if (salidaAlmacenGestionar.getTiposSalidasAlmacen().getCodTipoSalidaAlmacen() == 4 || salidaAlmacenGestionar.getTiposSalidasAlmacen().getCodTipoSalidaAlmacen() == 5 || salidaAlmacenGestionar.getTiposSalidasAlmacen().getCodTipoSalidaAlmacen() == 6) {
            mensaje = "Este registro no puede ser modificado, Ya que fue solicitado por Control de Calidad. ";
            salidaEditable = false;
            return null;
        }
        //COMIENZA PROCESO
        salidaVisible = true;
        salidaEditable = true;
        if (salidaAlmacenGestionar.getCodLoteProduccion().length() > 0 ) {    
            mensaje = "Las salidas con lote de produccion no se pueden modificar.";
            salidaEditable = false;
            
        }
        
        if (permisoEditarDirectamente || permisoEditarHoy) {
            editarSalidaAlmacen_action();
        }        

        salidaTransaccionSolicitudGestionar = new SalidaTransaccionSolicitud();
        return null;
    }
    
    public String nuevaSalidaTransaccionSolicitudAnular_action() throws SQLException{
        mensaje = "";
        salidaEditable = false;
        
        //--VALIDACION, QUE NO EXISTAN SOLICITUDES PENDIENTES SOBRE ESTA SALIDA DE ALMACEN
        if(verificarSalidaTieneSolicitudesPendientes()) {
            mensaje="La salida tiene solicitudes pendientes.";
            return null;
        }
        if(verificaTransaccionCerradaSalidaAlmacen(salidaAlmacenGestionar)){
            mensaje="Las Transacciones para la fecha de salida seleccionada fueron cerredas.";
            return null;
        }
//        if (salidaAlmacenGestionar.getCodLoteProduccion().length() > 0 ) {    -*-*-*-*-*-*-*-* VALIDACION IMPORTANTE!!!
//            mensaje = "Las salidas con lote de produccion no se eliminar.";
//            return null;
//        }
        salidaEditable = true;
        salidaTransaccionSolicitudGestionar = new SalidaTransaccionSolicitud();
        motivoAnulacionSalida = "";
        return null;
    }
    
    public String nuevaSalidaTransaccionSolicitudValidar_action(){
        mensaje = "";
        salidaEditable = false;
        salidaVisible = false;
        
        //VALIDACIONES GENERALES
        System.out.println("paso 1");
        if(verificaTransaccionCerradaSalidaAlmacen(salidaAlmacenGestionar)){
            mensaje="Las Transacciones para la fecha de salida seleccionada fueron cerredas.";
            return null;
        }
        System.out.println("paso 2");
        salidaEditable = true;
        salidaVisible = true;
        //Si es solicitud para Editar, se edita directamente
        if (salidaAlmacenGestionar.getSalidaTransaccionSolicitud().getEstadoTransaccionSolicitud().getCodEstadoTransaccionSolicitud()==2) {
            if (salidaAlmacenGestionar.getCodLoteProduccion().length() > 0 ) {    
                System.out.println("paso 3");
                mensaje = "Las salidas con lote de produccion no se pueden modificar.";
                salidaEditable = false;
            }
            System.out.println("paso 3.1");
            editarSalidaAlmacen_action(); 
        }
            System.out.println("paso 4");
        salidaTransaccionSolicitudGestionar = new SalidaTransaccionSolicitud();
        return null;
    }

    public String guardarSolicitudEditarSalidaAlmacen_action(){
        if (salidaTransaccionSolicitudGestionar.getObservacionSolicitante().length()==0) {
            this.mostrarMensajeTransaccionFallida("Ingrese la razón de la solicitud.");
            return null;
        }
        Connection con1 = null;
        this.transaccionExitosa = false;
        try {
            con1 = Util.openConnection(con1);
            String consulta = " SELECT sts_a.COD_SALIDA_TRANSACCION_SOLICITUD"
                        + " FROM SALIDA_TRANSACCION_SOLICITUD sts_a"
                        + " INNER JOIN "
                        + " (   SELECT COD_SALIDA_ALMACEN, MAX(FECHA_SOLICITUD) AS MAX_FECHA_SOLICITUD"
                            + " FROM SALIDA_TRANSACCION_SOLICITUD"
                            + " GROUP BY COD_SALIDA_ALMACEN"
                        + " ) sts_group on sts_a.COD_SALIDA_ALMACEN = sts_group.COD_SALIDA_ALMACEN and sts_a.FECHA_SOLICITUD = sts_group.MAX_FECHA_SOLICITUD"
                        + " WHERE sts_a.COD_ESTADO_TRANSACCION_SOLICITUD in (1,2)"
                        + " AND sts_a.COD_SALIDA_ALMACEN = " + salidaAlmacenGestionar.getCodSalidaAlmacen();
            LOGGER.info("consulta verificacion: " + consulta);
            Statement st = con1.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            if(rs.next())    {
                this.mostrarMensajeTransaccionFallida("La salida N° "+salidaAlmacenGestionar.getNroSalidaAlmacen()+" ya tiene una solicitud pendiente, actualice el navegador y verifique los datos antes de intentarlo nuevamente.");
                return null;
            }                 
            
            ManagedAccesoSistema usuarioLogueado = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            con.setAutoCommit(false);
            consulta = " INSERT INTO SALIDA_TRANSACCION_SOLICITUD(COD_SALIDA_ALMACEN, COD_PERSONAL_SOLICITANTE, COD_TIPO_TRANSACCION_SOLICITUD_ALMACEN, FECHA_SOLICITUD, COD_ESTADO_TRANSACCION_SOLICITUD, OBSERVACION_SOLICITANTE)"
                    + " VALUES (" + salidaAlmacenGestionar.getCodSalidaAlmacen()
                    + " , " + usuarioLogueado.getUsuarioModuloBean().getCodUsuarioGlobal()
                    + " , 2" 
                    + " , '" + sdf.format(new Date()) + "' "
                    + " , 2" 
                    + " , '" +salidaTransaccionSolicitudGestionar.getObservacionSolicitante()+"'"
                    + " )";
            
            LOGGER.info("consulta nuevo SALIDA_TRANSACCION_SOLICITUD: " + consulta);
            st = con1.createStatement();
            if(st.executeUpdate(consulta) > 0){
                this.mostrarMensajeTransaccionExitosa("Se registró correctamente la solicitud de Edición");
            }
            else{
                this.mostrarMensajeTransaccionFallida("Ocurrió un error. No se registró la solicitud.");
            }
            con.commit();
        } catch (Exception e) {
            LOGGER.warn(e);
            this.rollbackConexion(con1);
            this.mostrarMensajeTransaccionFallida("Ocurrió un error al momento de registrar la solicitud, intente de nuevo");
            
        }finally{
            this.cerrarConexion(con1);
        }
        if (this.transaccionExitosa) {
            this.cargarSalidasAlmacen();
            EnvioCorreoNotificacionSolicitudSalidaAlmacen correo = new EnvioCorreoNotificacionSolicitudSalidaAlmacen(salidaAlmacenGestionar.getCodSalidaAlmacen());
            correo.start();
        }
        return null;
    }
    
    public String guardarSolicitudAnularSalidaAlmacen_action() throws SQLException{
        Connection con1 = null;
        this.transaccionExitosa = false;
        if (salidaTransaccionSolicitudGestionar.getObservacionSolicitante().length()==0) {
            this.mostrarMensajeTransaccionFallida("Ingrese la razón de la solicitud.");
            return null;
        }
        try {
            con1 = Util.openConnection(con1);
            String consulta = " SELECT sts_a.COD_SALIDA_TRANSACCION_SOLICITUD"
                        + " FROM SALIDA_TRANSACCION_SOLICITUD sts_a"
                        + " INNER JOIN "
                        + " (   SELECT COD_SALIDA_ALMACEN, MAX(FECHA_SOLICITUD) AS MAX_FECHA_SOLICITUD"
                            + " FROM SALIDA_TRANSACCION_SOLICITUD"
                            + " GROUP BY COD_SALIDA_ALMACEN"
                        + " ) sts_group on sts_a.COD_SALIDA_ALMACEN = sts_group.COD_SALIDA_ALMACEN and sts_a.FECHA_SOLICITUD = sts_group.MAX_FECHA_SOLICITUD"
                        + " WHERE sts_a.COD_ESTADO_TRANSACCION_SOLICITUD in (1,2)"
                        + " AND sts_a.COD_SALIDA_ALMACEN = " + salidaAlmacenGestionar.getCodSalidaAlmacen();
            LOGGER.info("consulta verificacion: " + consulta);
            Statement st = con1.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            if(rs.next())    {
                this.mostrarMensajeTransaccionFallida("La salida N° "+salidaAlmacenGestionar.getNroSalidaAlmacen()+" ya tiene una solicitud pendiente, actualice el navegador y verifique los datos antes de intentarlo nuevamente.");
                return null;
            }                 
            
            con1.setAutoCommit(false);
            ManagedAccesoSistema usuarioLogueado = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
           
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            consulta = " INSERT INTO SALIDA_TRANSACCION_SOLICITUD(COD_SALIDA_ALMACEN, COD_PERSONAL_SOLICITANTE, COD_TIPO_TRANSACCION_SOLICITUD_ALMACEN, FECHA_SOLICITUD, COD_ESTADO_TRANSACCION_SOLICITUD, OBSERVACION_SOLICITANTE)"
                    + " VALUES (" + salidaAlmacenGestionar.getCodSalidaAlmacen()
                    + " , " + usuarioLogueado.getUsuarioModuloBean().getCodUsuarioGlobal()
                    + " , 1" 
                    + " , '" + sdf.format(new Date()) + "' "
                    + " , 1" 
                    + " , '" +salidaTransaccionSolicitudGestionar.getObservacionSolicitante()+"'"
                    + " )";
            
            LOGGER.info("consulta nuevo SALIDA_TRANSACCION_SOLICITUD: " + consulta);
            st = con1.createStatement();
            if(st.executeUpdate(consulta) > 0){
                this.mostrarMensajeTransaccionExitosa("Se registró correctamente la solicitud de Anulación");
            }
            else{
                this.mostrarMensajeTransaccionFallida("Ocurrió un error. No se registró la solicitud.");
            }
            con1.commit();
            
        } catch (Exception e) {
            this.mostrarMensajeTransaccionFallida("Ocurrió un error,favor notifique a sistemas ");
            LOGGER.warn(e);
        }finally{
            con1.close();
        }
        //ENVIAMOS CORREO
        if (this.transaccionExitosa) {
            this.cargarSalidasAlmacen();
            EnvioCorreoNotificacionSolicitudSalidaAlmacen correo = new EnvioCorreoNotificacionSolicitudSalidaAlmacen(salidaAlmacenGestionar.getCodSalidaAlmacen());
            correo.start();
        }
        return null;
    }

    public String aprobarSolicitudAnularEditarSalidaAlmacen_action() throws SQLException {
        Connection con1 = null;
        
        registradoCorrectamente = false;
        mensaje = "";
        ManagedAccesoSistema usuarioLogueado = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
        String consulta ;
        try {
            con1 = Util.openConnection(con1);
            con1.setAutoCommit(false);
            //APROBAR EDITAR
//            if (salidaAlmacenGestionar.getSalidaTransaccionSolicitud().getEstadoTransaccionSolicitud().getCodEstadoTransaccionSolicitud() == 2) {
//                  
//                consulta = " update SALIDA_TRANSACCION_SOLICITUD"
//                        + " set FECHA_VALIDACION = GETDATE()"
//                        + " , COD_ESTADO_TRANSACCION_SOLICITUD = 4"
//                        + " , COD_PERSONAL_VALIDADOR = " + usuarioLogueado.getUsuarioModuloBean().getCodUsuarioGlobal()
//                        + " , OBSERVACION_VALIDADOR = '" + salidaTransaccionSolicitudGestionar.getObservacionValidador() + "'"
//                        + " where COD_SALIDA_ALMACEN = " + salidaAlmacenGestionar.getCodSalidaAlmacen()
//                        + " AND COD_ESTADO_TRANSACCION_SOLICITUD = 2"
//                        + " AND COD_SALIDA_TRANSACCION_SOLICITUD = " +salidaAlmacenGestionar.getSalidaTransaccionSolicitud().getCodSalidaTransaccionSolicitud();
//                LOGGER.info("consulta realizar aprobacion: " + consulta);                
//                Statement st = con1.createStatement();
//                
//                if(st.executeUpdate(consulta)>0){
//                    System.out.println("Aprobacion realilzada");
//                    mensaje = "Registrado correctamente.";
//                    registradoCorrectamente = true;
//                }
//                else{
//                    mensaje = "Ocurrió un error. No se registró la solicitud.";
//                }
//            }
            
            // APROBAR ANULAR : anula directamente
            if (salidaAlmacenGestionar.getSalidaTransaccionSolicitud().getEstadoTransaccionSolicitud().getCodEstadoTransaccionSolicitud() == 1) {
                
                //VALIDACIONES
                if (!administradorAlmacen) {
                    mensaje = "No se encuentra habilitado como administrador del almacen.";
                    return null;
                }

                Statement st = con1.createStatement();
                                          
                // ACTUALIZAMOS SALIDA_TRANSACCION_SOLICITUD
                consulta = " UPDATE SALIDA_TRANSACCION_SOLICITUD"
                        + " SET FECHA_VALIDACION = GETDATE()"
                        + " , COD_ESTADO_TRANSACCION_SOLICITUD = 3"
                        + " , COD_PERSONAL_VALIDADOR = " + usuarioLogueado.getUsuarioModuloBean().getCodUsuarioGlobal()
                        + " , OBSERVACION_VALIDADOR = '" + salidaTransaccionSolicitudGestionar.getObservacionValidador() + "'"
                        + " WHERE COD_SALIDA_ALMACEN = " + salidaAlmacenGestionar.getCodSalidaAlmacen()
                        + " AND COD_ESTADO_TRANSACCION_SOLICITUD = 1"
                        + " AND COD_SALIDA_TRANSACCION_SOLICITUD = " +salidaAlmacenGestionar.getSalidaTransaccionSolicitud().getCodSalidaTransaccionSolicitud();
                LOGGER.info("consulta realizar aprobacion: " + consulta);                
                if(st.executeUpdate(consulta)<1){
                    mensaje = "Alguien más hizo una solicitud sobre este registro. Verifique el registro antes de volver a intentarlo.";
                    throw new Exception("Alguien más hizo una solicitud sobre este registro.");
                }
                 
                //EJECUTAMOS SP
                consulta = " exec [USP_ANULAR_SALIDAS_ALMACEN] " + salidaAlmacenGestionar.getCodSalidaAlmacen()
                        + ", " + usuarioLogueado.getUsuarioModuloBean().getCodUsuarioGlobal() ;
                LOGGER.info("consulta ejecutar: " + consulta);
                CallableStatement callStatement = con1.prepareCall(consulta);
                callStatement.execute();
                
                //EJECUTAMOS SP DESPUES DE CAMBIO
                consulta = " exec [PAA_REGISTRO_SALIDA_ALMACEN_LOG] " + salidaAlmacenGestionar.getCodSalidaAlmacen() 
                        + " , "+ usuarioLogueado.getUsuarioModuloBean().getCodUsuarioGlobal()
                        + " , 2"
                        + " , '" + salidaTransaccionSolicitudGestionar.getObservacionValidador() + "'";
                LOGGER.info("consulta ejecutar: " + consulta);            
                callStatement = con1.prepareCall(consulta);
                callStatement.execute();
                
                mensaje = "Registrado correctamente.";
                registradoCorrectamente = true;
            }
            con1.commit();
        }catch (SQLException ex) {
            con1.rollback();
            LOGGER.warn(ex);
            mensaje = "" + ex.getMessage();
        }catch (Exception ex) {
            con1.rollback();
            LOGGER.warn(ex);
            mensaje = "Ocurrió un error.";
        }
        finally{
            con1.close();
        }
        //ENVIAMOS CORREO
        if (registradoCorrectamente) {
            EnvioCorreoNotificacionValidacionSalidaAlmacen correo = new EnvioCorreoNotificacionValidacionSalidaAlmacen(salidaAlmacenGestionar.getCodSalidaAlmacen());
            correo.start();
        }
        //ACTUALIZAMOS LISTADO
        this.cargarSalidasAlmacenPorTransaccionSolicitud_action();
        ThreadCosteoByCodSalida costeoSalida = new ThreadCosteoByCodSalida(salidaAlmacenGestionar.getCodSalidaAlmacen());
        costeoSalida.start();
        return null;
    }

    public String rechazarSolicitudAnularEditarSalidaAlmacen_action() {
        try {
            mensaje = "";
            registradoCorrectamente = false;
            ManagedAccesoSistema usuarioLogueado = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            //Actualizamos SALIDA_TRANSACCION_SOLICITUD para solicitud EDITAR             
            String consulta = " update SALIDA_TRANSACCION_SOLICITUD"
                    + " set COD_ESTADO_TRANSACCION_SOLICITUD = " + (salidaAlmacenGestionar.getSalidaTransaccionSolicitud().getEstadoTransaccionSolicitud().getCodEstadoTransaccionSolicitud() == 1 ? 5 : 6)
                    + " , COD_PERSONAL_VALIDADOR = " + usuarioLogueado.getUsuarioModuloBean().getCodUsuarioGlobal()
                    + " , OBSERVACION_VALIDADOR = '" + salidaTransaccionSolicitudGestionar.getObservacionValidador() + "'"
                    + " , FECHA_VALIDACION = GETDATE() " 
                    + " where COD_SALIDA_ALMACEN = " + salidaAlmacenGestionar.getCodSalidaAlmacen()
                    + " AND COD_SALIDA_TRANSACCION_SOLICITUD = "
                    + " (   SELECT TOP 1 COD_SALIDA_TRANSACCION_SOLICITUD "
                    + " FROM SALIDA_TRANSACCION_SOLICITUD sts"
                    + " WHERE COD_SALIDA_ALMACEN = " + salidaAlmacenGestionar.getCodSalidaAlmacen() + " and COD_SALIDA_ALMACEN = sts.COD_SALIDA_ALMACEN ORDER BY FECHA_SOLICITUD DESC"
                    + " )";
            LOGGER.info("consulta: " + consulta);
            Connection con1 = null;
            con1 = Util.openConnection(con1);
            Statement st = con1.createStatement();
            if(st.executeUpdate(consulta)>0){
                mensaje = "Registrado correctamente.";
                registradoCorrectamente = true;
            }
            else{
                mensaje = "Ocurrio un error, no se guardó el registro.";
            }
            con1.close();
            //Enviamos correo
            if (registradoCorrectamente) {
                EnvioCorreoNotificacionValidacionSalidaAlmacen correo = new EnvioCorreoNotificacionValidacionSalidaAlmacen(salidaAlmacenGestionar.getCodSalidaAlmacen());
                correo.start();
            }
            
            this.cargarSalidasAlmacenPorTransaccionSolicitud_action();
        } catch (Exception e) {
            LOGGER.warn(e);
            mensaje = "Ocurrió un error.";
        }
        return null;
    }
    
    public String anularSalidaAlmacenSinSolicitud_action() throws SQLException {
        Connection con1 = null;
        con1 = Util.openConnection(con1);
        con1.setAutoCommit(false);
        ManagedAccesoSistema usuarioLogueado = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
        String consulta = "";
        registradoCorrectamente = false;
        try {
            mensaje = "";
            Statement st = con1.createStatement();
                                
            //VALIDACIONES
            if (!administradorAlmacen) {
                mensaje = "No se encuentra habilitado como administrador del almacen.";
                return null;
            }

//            consulta = "SELECT * "
//                    + " FROM USUARIOS_PERMISOS_ESPECIALES ups "
//                    + " WHERE ups.COD_MODULO = '" + usuarioLogueado.getCodigoModulo() + "' "
//                    + " AND ups.COD_PERSONAL = '" + usuarioLogueado.getUsuarioModuloBean().getCodUsuarioGlobal() + "'";
//            LOGGER.info("consulta " + consulta);
//            ResultSet result = st.executeQuery(consulta);
//            if (!result.next()) {
//                mensaje = "Usted no esta autorizado para anular la salida.";
//                return null;
//            }
            motivoAnulacionSalida = motivoAnulacionSalida.compareTo("") == 0 ? "Sin descripcion.":motivoAnulacionSalida;
            
            //EJECUTAMOS SP 
            consulta = " exec [USP_ANULAR_SALIDAS_ALMACEN] " + salidaAlmacenGestionar.getCodSalidaAlmacen()
                    + ", " + usuarioLogueado.getUsuarioModuloBean().getCodUsuarioGlobal() ;
            LOGGER.info("consulta ejecutar: " + consulta);
            CallableStatement callStatement = con1.prepareCall(consulta);
            callStatement.execute();

            //EJECUTAMOS SP DESPUES DE CAMBIO
            consulta = " exec [PAA_REGISTRO_SALIDA_ALMACEN_LOG] " + salidaAlmacenGestionar.getCodSalidaAlmacen() 
                    + " , "+ usuarioLogueado.getUsuarioModuloBean().getCodUsuarioGlobal()
                    + " , 2"
                    + " , '"+motivoAnulacionSalida+"' ";
            LOGGER.info("consulta ejecutar PAA_REGISTRO_SALIDA_ALMACEN_LOG: " + consulta);            
            callStatement = con1.prepareCall(consulta);
            callStatement.execute();

            mensaje = "Registrado correctamente.";
            con1.commit();
            registradoCorrectamente = true;
             
        }catch (SQLException ex) {
            con1.rollback();
            LOGGER.warn(ex);
            mensaje = "" + ex.getMessage();
        }catch (Exception ex) {
            con1.rollback();
            LOGGER.warn(ex);
            mensaje = "Ocurrió un error.";
        }
        finally{
            
            con1.close();
        }
           
        //ACTUALIZAMOS LISTADO
        cargarSalidasAlmacen();
        //costeando salida de almacen anulada
        ThreadCosteoByCodSalida costoSalida = new ThreadCosteoByCodSalida(salidaAlmacenGestionar.getCodSalidaAlmacen());
        costoSalida.start();
        
        return null;
    }
    
    public String getCargarContenidoSalidasAlmacenAprobarCambioEstado() {
        begin = 1;
        end = numrows;
        try {
//            salidasAlmacenBuscar = new SalidasAlmacen();
            salidasAlmacenBuscar.getSalidaTransaccionSolicitud().setTipoRevision(2);
            cargarPermisosEspecialesPersonal();
            cargarSalidasAlmacenPorTransaccionSolicitud_action();
            
        } catch (Exception e) {
            LOGGER.warn(e);
        }
        return null;
    }

    public void cargarSalidasAlmacenPorTransaccionSolicitud_action(){
        try {
            //Cargamos Permisos del Usuario Loguado para mostrar u ocultar opciones.
            this.cargarPermisoPersonal();
            usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

            String consulta
                    = " select * from (select ROW_NUMBER() OVER (ORDER BY FECHA_SOLICITUD desc) as 'FILAS', s.NRO_SALIDA_ALMACEN,s.COD_PERSONAL,s.COD_SALIDA_ALMACEN,isnull(s.COD_LOTE_PRODUCCION,'') as COD_LOTE_PRODUCCION,g.NOMBRE_GESTION, "
                    + " s.COD_ESTADO_SALIDA_ALMACEN,e.NOMBRE_ESTADO_SALIDA_ALMACEN, isnull(s.orden_trabajo,'') as orden_trabajo, "
                    + " (select p.ap_paterno_personal+' '+ p.ap_materno_personal+' '+p.nombres_personal from personal p where s.cod_personal=p.cod_personal)as nombre_personal, "
                    + " s.COD_TIPO_SALIDA_ALMACEN,tsa.NOMBRE_TIPO_SALIDA_ALMACEN,s.COD_AREA_EMPRESA,a.NOMBRE_AREA_EMPRESA, "
                    + " s.COD_PROD,c.nombre_prod_semiterminado, isnull(s.COD_PRESENTACION,'') as COD_PRESENTACION, prp.NOMBRE_PRODUCTO_PRESENTACION,s.FECHA_SALIDA_ALMACEN,s.OBS_SALIDA_ALMACEN, "
                    + " s.COD_ALMACEN,ets.COD_ESTADO_TRANSACCION_SOLICITUD,ets.NOMBRE_ESTADO_TRANSACCION_SOLICITUD, OBSERVACION_SOLICITANTE "
                    + " , NOMBRE_SOLICITANTE, NOMBRE_TIPO_TRANSACCION_SOLICITUD_ALMACEN, FECHA_SOLICITUD, FECHA_VALIDACION, COD_SALIDA_TRANSACCION_SOLICITUD "
                    + " , tsa.EDITABLE, tsa.AUTOMATICO, tsa.REGISTRA_LOTE, tsa.REGISTRA_PRESENTACION, tsa.REGISTRA_PRODUCTO  "
                    + " from SALIDAS_ALMACEN s  "
                    + " inner join TIPOS_SALIDAS_ALMACEN tsa on tsa.COD_TIPO_SALIDA_ALMACEN = s.COD_TIPO_SALIDA_ALMACEN "
                    + " inner join ESTADOS_SALIDAS_ALMACEN e on e.COD_ESTADO_SALIDA_ALMACEN = s.COD_ESTADO_SALIDA_ALMACEN "
                    + " inner join AREAS_EMPRESA a on a.COD_AREA_EMPRESA = s.COD_AREA_EMPRESA "
                    + " left outer join COMPONENTES_PROD c on c.COD_COMPPROD = s.COD_PROD "
                    + " left outer join PRESENTACIONES_PRODUCTO prp on prp.cod_presentacion = s.COD_PRESENTACION "
                    + " inner join gestiones g on g.COD_GESTION = s.COD_GESTION "
                    + " left join "
                    + " (   SELECT sts_a.COD_SALIDA_TRANSACCION_SOLICITUD , sts_a.COD_SALIDA_ALMACEN, COD_ESTADO_TRANSACCION_SOLICITUD, isnull(OBSERVACION_SOLICITANTE,'') as OBSERVACION_SOLICITANTE "
                        + " ,(ps.NOMBRES_PERSONAL+' '+ps.AP_PATERNO_PERSONAL+' '+ps.AP_MATERNO_PERSONAL) as NOMBRE_SOLICITANTE "
                        + " , ttsa.NOMBRE_TIPO_TRANSACCION_SOLICITUD_ALMACEN, FECHA_SOLICITUD, FECHA_VALIDACION "
                        + " FROM SALIDA_TRANSACCION_SOLICITUD sts_a"
                        + " inner join "
                        + " (   SELECT COD_SALIDA_ALMACEN, MAX(FECHA_SOLICITUD) AS MAX_FECHA_SOLICITUD"
                            + " FROM SALIDA_TRANSACCION_SOLICITUD"
                            + " GROUP BY COD_SALIDA_ALMACEN"
                        + " ) sts_group on sts_a.COD_SALIDA_ALMACEN = sts_group.COD_SALIDA_ALMACEN and sts_a.FECHA_SOLICITUD = sts_group.MAX_FECHA_SOLICITUD"
                        + " LEFT JOIN PERSONAL ps on ps.COD_PERSONAL = sts_a.COD_PERSONAL_SOLICITANTE"
                        + " LEFT JOIN TIPO_TRANSACCION_SOLICITUD_ALMACEN ttsa on ttsa.COD_TIPO_TRANSACCION_SOLICITUD_ALMACEN = sts_a.COD_TIPO_TRANSACCION_SOLICITUD_ALMACEN"
                        + " "
                    + " ) sts on sts.COD_SALIDA_ALMACEN = s.COD_SALIDA_ALMACEN"
                    + " left join ESTADO_TRANSACCION_SOLICITUD ets on ets.COD_ESTADO_TRANSACCION_SOLICITUD = sts.COD_ESTADO_TRANSACCION_SOLICITUD"
                    + " where 0=0 and s.cod_almacen = '" + usuario.getAlmacenesGlobal().getCodAlmacen() + "' " ;
            if (salidasAlmacenBuscar.getSalidaTransaccionSolicitud().getTipoRevision() != 0) {
                String codigos = "";
                switch(salidasAlmacenBuscar.getSalidaTransaccionSolicitud().getTipoRevision()){
                    case 1: codigos = "(1,2,3,4,5,6,7)"; 
                    break;
                    case 2: codigos = "(1,2)"; 
                    break;
                    case 3: codigos = "(3,4,7)"; 
                    break;
                    case 4: codigos = "(5,6)"; 
                    break;
                    default: codigos = "(1,2)";
                    break;
                }
                consulta += " and ets.COD_ESTADO_TRANSACCION_SOLICITUD in "+codigos;
            }
            if (salidasAlmacenBuscar.getSalidaTransaccionSolicitud().getFechaValidacion()!= null || salidasAlmacenBuscar.getSalidaTransaccionSolicitud().getFechaValidacionFin()!= null) {
                if (salidasAlmacenBuscar.getSalidaTransaccionSolicitud().getFechaValidacion() == null) {
                    salidasAlmacenBuscar.getSalidaTransaccionSolicitud().setFechaValidacion(salidasAlmacenBuscar.getSalidaTransaccionSolicitud().getFechaValidacionFin());
                }
                if (salidasAlmacenBuscar.getSalidaTransaccionSolicitud().getFechaValidacionFin() == null) {
                    salidasAlmacenBuscar.getSalidaTransaccionSolicitud().setFechaValidacionFin(salidasAlmacenBuscar.getSalidaTransaccionSolicitud().getFechaValidacion());
                }
                consulta += " and (CAST(FECHA_VALIDACION AS DATE) >= '"+sdf.format(salidasAlmacenBuscar.getSalidaTransaccionSolicitud().getFechaValidacion())+"' AND CAST(FECHA_VALIDACION AS DATE) <= '"+sdf.format(salidasAlmacenBuscar.getSalidaTransaccionSolicitud().getFechaValidacionFin())+"' ) ";
            }
            if (salidasAlmacenBuscar.getSalidaTransaccionSolicitud().getFechaSolicitud()!= null || salidasAlmacenBuscar.getSalidaTransaccionSolicitud().getFechaSolicitudFin()!= null) {
                if (salidasAlmacenBuscar.getSalidaTransaccionSolicitud().getFechaSolicitud() == null) {
                    salidasAlmacenBuscar.getSalidaTransaccionSolicitud().setFechaSolicitud(salidasAlmacenBuscar.getSalidaTransaccionSolicitud().getFechaSolicitudFin());
                }
                if (salidasAlmacenBuscar.getSalidaTransaccionSolicitud().getFechaSolicitudFin()== null) {
                    salidasAlmacenBuscar.getSalidaTransaccionSolicitud().setFechaSolicitudFin(salidasAlmacenBuscar.getSalidaTransaccionSolicitud().getFechaSolicitud());
                }
                consulta += " and (CAST(FECHA_SOLICITUD AS DATE) >= '"+sdf.format(salidasAlmacenBuscar.getSalidaTransaccionSolicitud().getFechaSolicitud())+"' AND CAST(FECHA_SOLICITUD AS DATE) <= '"+sdf.format(salidasAlmacenBuscar.getSalidaTransaccionSolicitud().getFechaSolicitudFin())+"' ) ";
            }
            consulta += " ) AS listado_ordenes_compra where FILAS BETWEEN " + begin + " AND " + end ;

            LOGGER.info("consulta cargarSalidasAlmacenPorTransaccionSolicitud: " + consulta);

            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            salidasAlmacenList.clear();
            while (rs.next()) {
                SalidasAlmacen salidasAlmacen = new SalidasAlmacen();
                salidasAlmacen.getSalidaTransaccionSolicitud().setCodSalidaTransaccionSolicitud(rs.getInt("COD_SALIDA_TRANSACCION_SOLICITUD"));
                salidasAlmacen.getSalidaTransaccionSolicitud().getEstadoTransaccionSolicitud().setCodEstadoTransaccionSolicitud(rs.getInt("COD_ESTADO_TRANSACCION_SOLICITUD"));
                salidasAlmacen.getSalidaTransaccionSolicitud().getEstadoTransaccionSolicitud().setNombreEstadoTransaccionSolicitud(rs.getString("NOMBRE_ESTADO_TRANSACCION_SOLICITUD"));
                salidasAlmacen.getSalidaTransaccionSolicitud().setFechaSolicitud(rs.getTimestamp("FECHA_SOLICITUD"));
                salidasAlmacen.getSalidaTransaccionSolicitud().setFechaValidacion(rs.getTimestamp("FECHA_VALIDACION"));
                salidasAlmacen.getSalidaTransaccionSolicitud().setObservacionSolicitante(rs.getString("OBSERVACION_SOLICITANTE"));
                salidasAlmacen.getSalidaTransaccionSolicitud().getPersonalSolicitante().setNombrePersonal(rs.getString("NOMBRE_SOLICITANTE"));
                salidasAlmacen.getSalidaTransaccionSolicitud().getTipoTransaccionSolicitudAlmacen().setNombreTipoTransaccionSolicitudAlmacen(rs.getString("NOMBRE_TIPO_TRANSACCION_SOLICITUD_ALMACEN"));
                salidasAlmacen.setNroSalidaAlmacen(rs.getInt("NRO_SALIDA_ALMACEN"));
                salidasAlmacen.getPersonal().setCodPersonal(rs.getString("COD_PERSONAL"));
                salidasAlmacen.setCodSalidaAlmacen(rs.getInt("COD_SALIDA_ALMACEN"));
                salidasAlmacen.setCodLoteProduccion(rs.getString("COD_LOTE_PRODUCCION"));
                salidasAlmacen.getGestiones().setNombreGestion(rs.getString("NOMBRE_GESTION"));
                salidasAlmacen.getEstadosSalidasAlmacen().setCodEstadoSalidaAlmacen(rs.getInt("COD_ESTADO_SALIDA_ALMACEN"));
                salidasAlmacen.getEstadosSalidasAlmacen().setNombreEstadoSalidaAlmacen(rs.getString("NOMBRE_ESTADO_SALIDA_ALMACEN"));
                salidasAlmacen.setOrdenTrabajo(rs.getString("orden_trabajo"));
                salidasAlmacen.getPersonal().setNombrePersonal(rs.getString("nombre_personal"));
                salidasAlmacen.getTiposSalidasAlmacen().setEditable(rs.getInt("EDITABLE")==1);
                salidasAlmacen.getTiposSalidasAlmacen().setAutomatico(rs.getInt("AUTOMATICO")==1);
                salidasAlmacen.getTiposSalidasAlmacen().setRegistraLote(rs.getInt("REGISTRA_LOTE")==1);
                salidasAlmacen.getTiposSalidasAlmacen().setRegistraPresentacion(rs.getInt("REGISTRA_PRESENTACION")==1);
                salidasAlmacen.getTiposSalidasAlmacen().setRegistraProducto(rs.getInt("REGISTRA_PRODUCTO")==1);
                salidasAlmacen.getTiposSalidasAlmacen().setCodTipoSalidaAlmacen(rs.getInt("COD_TIPO_SALIDA_ALMACEN"));
                salidasAlmacen.getTiposSalidasAlmacen().setNombreTipoSalidaAlmacen(rs.getString("NOMBRE_TIPO_SALIDA_ALMACEN"));
                salidasAlmacen.getEstadosSalidasAlmacen().setCodEstadoSalidaAlmacen(rs.getInt("COD_ESTADO_SALIDA_ALMACEN"));
                salidasAlmacen.getEstadosSalidasAlmacen().setNombreEstadoSalidaAlmacen(rs.getString("NOMBRE_ESTADO_SALIDA_ALMACEN"));
                salidasAlmacen.getAreasEmpresa().setCodAreaEmpresa(rs.getString("COD_AREA_EMPRESA"));
                salidasAlmacen.getAreasEmpresa().setNombreAreaEmpresa(rs.getString("NOMBRE_AREA_EMPRESA"));
                salidasAlmacen.getComponentesProd().setCodCompprod(rs.getString("COD_PROD"));
                salidasAlmacen.getComponentesProd().setNombreProdSemiterminado(rs.getString("nombre_prod_semiterminado"));
                salidasAlmacen.getPresentacionesProducto().setCodPresentacion(rs.getString("COD_PRESENTACION"));
                salidasAlmacen.getPresentacionesProducto().setNombreProductoPresentacion(rs.getString("NOMBRE_PRODUCTO_PRESENTACION"));
                salidasAlmacen.getAlmacenes().setCodAlmacen(rs.getInt("COD_ALMACEN"));
                //inicio ale validar
                salidasAlmacen.setFechaSalidaAlmacen(rs.getTimestamp("FECHA_SALIDA_ALMACEN"));
                //final ale validar
                //inicio alejandro 2
                if (salidasAlmacen.getEstadosSalidasAlmacen().getCodEstadoSalidaAlmacen() == 2) {
                    salidasAlmacen.setColorFila("b");
                }
                //final alejandro 2
                salidasAlmacen.setObsSalidaAlmacen(rs.getString("OBS_SALIDA_ALMACEN"));
                salidasAlmacenList.add(salidasAlmacen);
            }
            cantidadfilas = salidasAlmacenList.size();
            st.close();
            rs.close();

        } catch (Exception e) {
            LOGGER.warn(e);
        }
    
    }
    public void cargarSalidasAlmacenAprobarCambioEstado() {
        try {
            //Cargamos Permisos del Usuario Loguado para mostrar u ocultar opciones.
            this.cargarPermisoPersonal();
            usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

            String consulta
                    = " select * from (select ROW_NUMBER() OVER (ORDER BY s.NRO_SALIDA_ALMACEN desc) as 'FILAS', s.NRO_SALIDA_ALMACEN,s.COD_PERSONAL,s.COD_SALIDA_ALMACEN,isnull(s.COD_LOTE_PRODUCCION,'') as COD_LOTE_PRODUCCION,g.NOMBRE_GESTION, "
                    + " s.COD_ESTADO_SALIDA_ALMACEN,e.NOMBRE_ESTADO_SALIDA_ALMACEN,s.orden_trabajo, "
                    + " (select p.ap_paterno_personal+' '+ p.ap_materno_personal+' '+p.nombres_personal from personal p where s.cod_personal=p.cod_personal)as nombre_personal, "
                    + " s.COD_TIPO_SALIDA_ALMACEN,tsa.NOMBRE_TIPO_SALIDA_ALMACEN,s.COD_AREA_EMPRESA,a.NOMBRE_AREA_EMPRESA, "
                    + " s.COD_PROD,c.nombre_prod_semiterminado,s.COD_PRESENTACION,prp.NOMBRE_PRODUCTO_PRESENTACION,s.FECHA_SALIDA_ALMACEN,s.OBS_SALIDA_ALMACEN, "
                    + " s.COD_ALMACEN,ets.COD_ESTADO_TRANSACCION_SOLICITUD,ets.NOMBRE_ESTADO_TRANSACCION_SOLICITUD, OBSERVACION_SOLICITANTE "
                    + " , NOMBRE_SOLICITANTE, NOMBRE_TIPO_TRANSACCION_SOLICITUD_ALMACEN "
                    + " from SALIDAS_ALMACEN s  inner join TIPOS_SALIDAS_ALMACEN tsa on tsa.COD_TIPO_SALIDA_ALMACEN = s.COD_TIPO_SALIDA_ALMACEN "
                    + " inner join ESTADOS_SALIDAS_ALMACEN e on e.COD_ESTADO_SALIDA_ALMACEN = s.COD_ESTADO_SALIDA_ALMACEN "
                    + " inner join AREAS_EMPRESA a on a.COD_AREA_EMPRESA = s.COD_AREA_EMPRESA "
                    + " left outer join COMPONENTES_PROD c on c.COD_COMPPROD = s.COD_PROD "
                    + " left outer join PRESENTACIONES_PRODUCTO prp on prp.cod_presentacion = s.COD_PRESENTACION "
                    + " inner join gestiones g on g.COD_GESTION = s.COD_GESTION "
                    + " left join "
                    + " (   SELECT sts_a.COD_SALIDA_ALMACEN, COD_ESTADO_TRANSACCION_SOLICITUD, isnull(OBSERVACION_SOLICITANTE,'') as OBSERVACION_SOLICITANTE "
                        + " ,(ps.NOMBRES_PERSONAL+' '+ps.AP_PATERNO_PERSONAL+' '+ps.AP_MATERNO_PERSONAL) as NOMBRE_SOLICITANTE "
                        + " , ttsa.NOMBRE_TIPO_TRANSACCION_SOLICITUD_ALMACEN "
                        + " FROM SALIDA_TRANSACCION_SOLICITUD sts_a"
                        + " inner join "
                        + " (   SELECT COD_SALIDA_ALMACEN, MAX(FECHA_SOLICITUD) AS MAX_FECHA_SOLICITUD"
                            + " FROM SALIDA_TRANSACCION_SOLICITUD"
                            + " GROUP BY COD_SALIDA_ALMACEN"
                        + " ) sts_group on sts_a.COD_SALIDA_ALMACEN = sts_group.COD_SALIDA_ALMACEN and sts_a.FECHA_SOLICITUD = sts_group.MAX_FECHA_SOLICITUD"
                        + " LEFT JOIN PERSONAL ps on ps.COD_PERSONAL = sts_a.COD_PERSONAL_SOLICITANTE"
                        + " LEFT JOIN TIPO_TRANSACCION_SOLICITUD_ALMACEN ttsa on ttsa.COD_TIPO_TRANSACCION_SOLICITUD_ALMACEN = sts_a.COD_TIPO_TRANSACCION_SOLICITUD_ALMACEN"
                    + " ) sts on sts.COD_SALIDA_ALMACEN = s.COD_SALIDA_ALMACEN"
                    + " left join ESTADO_TRANSACCION_SOLICITUD ets on ets.COD_ESTADO_TRANSACCION_SOLICITUD = sts.COD_ESTADO_TRANSACCION_SOLICITUD"
                    + " where 0=0 and s.cod_almacen = '" + usuario.getAlmacenesGlobal().getCodAlmacen() + "' "
                    + " and ets.COD_ESTADO_TRANSACCION_SOLICITUD in (1,2) ) AS listado_ordenes_compra";

            LOGGER.info("consulta cargarSalidasAlmacen" + consulta);

            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            salidasAlmacenList.clear();
            while (rs.next()) {
                SalidasAlmacen salidasAlmacen = new SalidasAlmacen();
                salidasAlmacen.getSalidaTransaccionSolicitud().getEstadoTransaccionSolicitud().setCodEstadoTransaccionSolicitud(rs.getInt("COD_ESTADO_TRANSACCION_SOLICITUD"));
                salidasAlmacen.getSalidaTransaccionSolicitud().getEstadoTransaccionSolicitud().setNombreEstadoTransaccionSolicitud(rs.getString("NOMBRE_ESTADO_TRANSACCION_SOLICITUD"));
                salidasAlmacen.getSalidaTransaccionSolicitud().setObservacionSolicitante(rs.getString("OBSERVACION_SOLICITANTE"));
                salidasAlmacen.getSalidaTransaccionSolicitud().getPersonalSolicitante().setNombrePersonal(rs.getString("NOMBRE_SOLICITANTE"));
                salidasAlmacen.getSalidaTransaccionSolicitud().getTipoTransaccionSolicitudAlmacen().setNombreTipoTransaccionSolicitudAlmacen(rs.getString("NOMBRE_TIPO_TRANSACCION_SOLICITUD_ALMACEN"));
                salidasAlmacen.setNroSalidaAlmacen(rs.getInt("NRO_SALIDA_ALMACEN"));
                salidasAlmacen.getPersonal().setCodPersonal(rs.getString("COD_PERSONAL"));
                salidasAlmacen.setCodSalidaAlmacen(rs.getInt("COD_SALIDA_ALMACEN"));
                salidasAlmacen.setCodLoteProduccion(rs.getString("COD_LOTE_PRODUCCION"));
                salidasAlmacen.getGestiones().setNombreGestion(rs.getString("NOMBRE_GESTION"));
                salidasAlmacen.getEstadosSalidasAlmacen().setCodEstadoSalidaAlmacen(rs.getInt("COD_ESTADO_SALIDA_ALMACEN"));
                salidasAlmacen.getEstadosSalidasAlmacen().setNombreEstadoSalidaAlmacen(rs.getString("NOMBRE_ESTADO_SALIDA_ALMACEN"));
                salidasAlmacen.setOrdenTrabajo(rs.getString("orden_trabajo"));
                salidasAlmacen.getPersonal().setNombrePersonal(rs.getString("nombre_personal"));
                salidasAlmacen.getTiposSalidasAlmacen().setCodTipoSalidaAlmacen(rs.getInt("COD_TIPO_SALIDA_ALMACEN"));
                salidasAlmacen.getTiposSalidasAlmacen().setNombreTipoSalidaAlmacen(rs.getString("NOMBRE_TIPO_SALIDA_ALMACEN"));
                salidasAlmacen.getEstadosSalidasAlmacen().setCodEstadoSalidaAlmacen(rs.getInt("COD_ESTADO_SALIDA_ALMACEN"));
                salidasAlmacen.getEstadosSalidasAlmacen().setNombreEstadoSalidaAlmacen(rs.getString("NOMBRE_ESTADO_SALIDA_ALMACEN"));
                salidasAlmacen.getAreasEmpresa().setCodAreaEmpresa(rs.getString("COD_AREA_EMPRESA"));
                salidasAlmacen.getAreasEmpresa().setNombreAreaEmpresa(rs.getString("NOMBRE_AREA_EMPRESA"));
                salidasAlmacen.getComponentesProd().setCodCompprod(rs.getString("COD_PROD"));
                salidasAlmacen.getComponentesProd().setNombreProdSemiterminado(rs.getString("nombre_prod_semiterminado"));
                salidasAlmacen.getPresentacionesProducto().setCodPresentacion(rs.getString("COD_PRESENTACION"));
                salidasAlmacen.getPresentacionesProducto().setNombreProductoPresentacion(rs.getString("NOMBRE_PRODUCTO_PRESENTACION"));
                salidasAlmacen.getAlmacenes().setCodAlmacen(rs.getInt("COD_ALMACEN"));
                //inicio ale validar
                salidasAlmacen.setFechaSalidaAlmacen(rs.getTimestamp("FECHA_SALIDA_ALMACEN"));
                //final ale validar
                //inicio alejandro 2
                if (salidasAlmacen.getEstadosSalidasAlmacen().getCodEstadoSalidaAlmacen() == 2) {
                    salidasAlmacen.setColorFila("b");
                }
                //final alejandro 2
                salidasAlmacen.setObsSalidaAlmacen(rs.getString("OBS_SALIDA_ALMACEN"));
                salidasAlmacenList.add(salidasAlmacen);
            }
            cantidadfilas = salidasAlmacenList.size();
            st.close();
            rs.close();

        } catch (Exception e) {
            LOGGER.warn(e);
        }
    }

    public String siguiente_action() {
        super.next();
        cargarSalidasAlmacen();
        return "";
    }

    public String atras_action() {
        super.back();
        cargarSalidasAlmacen();
        return "";
    }
    public String salidas_validar_siguiente_action() {
        super.next();
        cargarSalidasAlmacenPorTransaccionSolicitud_action();
        return "";
    }

    public String salidas_validar_atras_action() {
        super.back();
        cargarSalidasAlmacenPorTransaccionSolicitud_action();
        return "";
    }
 
        public void reiniciarCamposBusqueda_action() {
        salidasAlmacenBuscar = new SalidasAlmacen();
        salidasAlmacenBuscar.getSalidaTransaccionSolicitud().setTipoRevision(2);
            
    }
    
    public void cargarGestiones() {
        try {
            String sql = "select cod_gestion,nombre_gestion from gestiones";
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(sql);
            gestionesList.clear();
            while (rs.next()) {
                gestionesList.add(new SelectItem(rs.getString("cod_gestion"), rs.getString("nombre_gestion")));
            }
            if (rs != null) {
                rs.close();
                st.close();
            }
        } catch (Exception e) {
            LOGGER.warn(e);
        }
    }

    public void cargarTiposSalidaAlmacen() {
        try {
            String sql = "  select t.COD_TIPO_SALIDA_ALMACEN,t.NOMBRE_TIPO_SALIDA_ALMACEN "
                    + " from TIPOS_SALIDAS_ALMACEN t "
                    + " where t.COD_ESTADO_REGISTRO = 1";

            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(sql);
            tiposSalidaAlmacenList.clear();
            tiposSalidaAlmacenList.add(new SelectItem("0", "-TODOS-"));
            while (rs.next()) {
                tiposSalidaAlmacenList.add(new SelectItem(rs.getInt("COD_TIPO_SALIDA_ALMACEN"), rs.getString("NOMBRE_TIPO_SALIDA_ALMACEN")));
            }
            if (rs != null) {
                rs.close();
                st.close();
            }
        } catch (Exception e) {
            LOGGER.warn(e);
        }
    }

    public void cargarEstadosSalidaAlmacen() {
        try {
            String sql = "  select e.COD_ESTADO_SALIDA_ALMACEN,e.NOMBRE_ESTADO_SALIDA_ALMACEN  "
                    + " from ESTADOS_SALIDAS_ALMACEN e ";

            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(sql);
            estadosSalidaAlmacenList.clear();
            estadosSalidaAlmacenList.add(new SelectItem("0", "-TODOS-"));
            while (rs.next()) {
                estadosSalidaAlmacenList.add(new SelectItem(rs.getInt("COD_ESTADO_SALIDA_ALMACEN"), rs.getString("NOMBRE_ESTADO_SALIDA_ALMACEN")));
            }
            if (rs != null) {
                rs.close();
                st.close();
            }
        } catch (Exception e) {
            LOGGER.warn(e);
        }
    }

    public List cargarAreasEmpresa() {
        List areasEmpresalist = new ArrayList();
        try {
            String sql = "  select a.COD_AREA_EMPRESA,a.NOMBRE_AREA_EMPRESA  "
                    + " from AREAS_EMPRESA a  where a.COD_ESTADO_REGISTRO = 1 order by a.nombre_area_empresa";

            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            System.out.println("sql *j* : " + sql);
            ResultSet rs = st.executeQuery(sql);
            areasEmpresalist.clear();
            areasEmpresalist.add(new SelectItem("0", "-TODOS-"));
            while (rs.next()) {
                areasEmpresalist.add(new SelectItem(rs.getString("COD_AREA_EMPRESA"), rs.getString("NOMBRE_AREA_EMPRESA")));
            }
            if (rs != null) {
                rs.close();
                st.close();
            }
        } catch (Exception e) {
            LOGGER.warn(e);
        }
        return areasEmpresalist;
    }

    public List cargarComponentesProd() {
        List componentesProdList = new ArrayList();
        try {
            String sql = " select cp.COD_COMPPROD,cp.nombre_prod_semiterminado  "
                    + " from COMPONENTES_PROD cp where cp.COD_ESTADO_COMPPROD=1 "
                    + " and cp.nombre_prod_semiterminado is not null and cp.nombre_prod_semiterminado != '' order by cp.nombre_prod_semiterminado asc";

            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(sql);
            componentesProdList.clear();
            componentesProdList.add(new SelectItem("0", "-TODOS-"));
            while (rs.next()) {
                componentesProdList.add(new SelectItem(rs.getString("COD_COMPPROD"), rs.getString("nombre_prod_semiterminado")));
            }
            if (rs != null) {
                rs.close();
                st.close();
            }
        } catch (Exception e) {
            LOGGER.warn(e);
        }
        return componentesProdList;
    }

    public String imprimir() {
        System.out.println("holas");
        return null;
    }

    public String guardarCambiofechaSalida_action() throws SQLException {

        mensaje = "";
        try {
            con.setAutoCommit(false);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm");
            String consulta = "update SALIDAS_ALMACEN set FECHA_SALIDA_ALMACEN='" + sdf.format(salidasAlmacenFecha.getFechaSalidaAlmacen()) + " " + sdf1.format(horaOriginal) + "'"
                    + " where COD_SALIDA_ALMACEN='" + salidasAlmacenFecha.getCodSalidaAlmacen() + "'";
            LOGGER.info("consulta guardar cambio fecha " + consulta);
            PreparedStatement pst = con.prepareStatement(consulta);
            if (pst.executeUpdate() > 0) {
                System.out.println("se cambio la fecha ");

            }
            con.commit();
            mensaje = "1";
            pst.close();

        } catch (SQLException ex) {
            con.rollback();
            mensaje = "Error,intente de nuevo";
            LOGGER.warn(ex);
        }
        if (mensaje.equals("1")) {
            this.cargarSalidasAlmacen();
        }
        return null;
    }

    public String buscarSalidaAlmacen_action() {
        try {
            System.out.println("1");
            begin = 1;
            end = 10;
            this.cargarSalidasAlmacen();
        } catch (Exception e) {
            LOGGER.warn(e);
        }
        return null;
    }

    public String registrarSalidaAlmacen_action() {
        try {
            Utiles.direccionar("");
        } catch (Exception e) {
            LOGGER.warn(e);
        }
        return null;
    }

    public String selecccionarSalidaFechaModificar() {

        Iterator i = salidasAlmacenList.iterator();
        SalidasAlmacen salidasAlmacen = new SalidasAlmacen();
        while (i.hasNext()) {
            salidasAlmacen = (SalidasAlmacen) i.next();
            if (salidasAlmacen.getChecked().booleanValue() == true) {
                salidasAlmacenFecha = salidasAlmacen;
                horaOriginal = (Date) salidasAlmacenFecha.getFechaSalidaAlmacen().clone();
            }
        }
        return null;
    }

    public String editarSalidaAlmacen_action() {
        try {            
            //LAS VALIDACIONES YA FUERON REALIZADAS
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            Map<String, Object> sessionMap = externalContext.getSessionMap();
            sessionMap.put("salidasAlmacenEditar", salidaAlmacenGestionar);
        } catch (Exception e) {
            mensaje = "Ocurrió un error. ";
            LOGGER.warn(e);
        }
        return null;
    }

    public boolean verificaTransaccionCerradaSalidaAlmacen(SalidasAlmacen salidasAlmacen){
        boolean transaccionCerradaAlmacen = false;
        Date fechaActual = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        Connection con1 = null;
        try {            
            con1 = Util.openConnection(con1);
            ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            String consulta = " select * from estados_transacciones_almacen e,salidas_almacen s "
                    + " where e.cod_gestion=s.cod_gestion "
                    + " and e.cod_mes = month(s.fecha_salida_almacen) and s.cod_salida_almacen = '" + salidasAlmacen.getCodSalidaAlmacen() + "' "
                    + " and e.estado=1 ";
            LOGGER.info("consulta " + consulta);
            Statement st = con1.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            if (rs.next()) {
                transaccionCerradaAlmacen = true;
            }

        }
        catch(SQLException ex)
        {
            LOGGER.warn("error", ex);
        }
        catch (Exception e) {
            LOGGER.warn(e);
        }
        finally{
            this.cerrarConexion(con1);
        }
        return transaccionCerradaAlmacen;
    }
    public String verReporteSalidaAlmacenAction(){
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        sessionMap.put("salidasAlmacen", salidasAlmacenSeleccion);
        return null;
    }

    public String verReporteSalidaAlmacen_action() {
        try {
            Iterator i = salidasAlmacenList.iterator();
            while (i.hasNext()) {
                SalidasAlmacen salidasAlmacen = (SalidasAlmacen) i.next();
                if (salidasAlmacen.getChecked().booleanValue() == true) {
                    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                    Map<String, Object> sessionMap = externalContext.getSessionMap();
                    sessionMap.put("salidasAlmacen", salidasAlmacen);
                    break;
                }
            }

        } catch (Exception e) {
            LOGGER.warn(e);
        }
        return null;
    }

    public String anularSalidasAlmacen_action1() {
        try {

            SalidasAlmacen salidasAlmacen = new SalidasAlmacen();
            Iterator i = salidasAlmacenList.iterator();
            while (i.hasNext()) {
                salidasAlmacen = (SalidasAlmacen) i.next();
                if (salidasAlmacen.getChecked().booleanValue() == true) {
                    break;
                }
            }
            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement();
            String consulta = " update salidas_almacen set cod_estado_salida_almacen = 2 where cod_salida_almacen = '" + salidasAlmacen.getCodSalidaAlmacen() + "'";
            LOGGER.info("consulta " + consulta);
            st.executeUpdate(consulta);
            this.cargarSalidasAlmacen();
        } catch (Exception e) {
            LOGGER.warn(e);
        }
        return null;
    }

    public boolean verificarDevolucionDeSalida(SalidasAlmacen salida) {
        Connection con = null;
        try {
            con = Util.openConnection(con);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String consulta = "select * from DEVOLUCIONES d where d.COD_SALIDA_ALMACEN ='" + salida.getCodSalidaAlmacen() + "'";
            System.out.println("con verificar " + consulta);
            ResultSet rst1 = st.executeQuery(consulta);
            if (rst1.first()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            LOGGER.warn(ex);
        }
        return true;
    }

    //final alejandro
    public String anularSalidasAlmacen_action() {
        this.cargarPermisoPersonal();
        if (!administradorAlmacen) {
            mensaje = "No se encuentra habilitado como administrador del almacen";
            return null;
        }
        SalidasAlmacen salidasAlmacen = new SalidasAlmacen();
        try {

            Iterator i = salidasAlmacenList.iterator();
            while (i.hasNext()) {
                salidasAlmacen = (SalidasAlmacen) i.next();
                if (salidasAlmacen.getChecked().booleanValue() == true) {
                    break;
                }
            }
            //inicio alejandro

            mensaje = "";
            if (this.verificaTransaccionCerradaSalidaAlmacen(salidasAlmacen)) {
                mensaje = "Las Transacciones para la fecha de salida seleccionada fueron cerredas";
                return null;
            }
            if (this.verificarDevolucionDeSalida(salidasAlmacen)) {
                mensaje = "Esta salida no puede anularse porque se realizó una devolución de esta salida.";
                return null;
            }
            if (salidasAlmacen.getEstadosSalidasAlmacen().getCodEstadoSalidaAlmacen() == 2) {
                mensaje = "La salida no se puede anular porque ya fue anulada";
                return null;
            }
            if ((salidasAlmacen.getEstadosTransaccionSalida().getCodEstadoTransaccionSalida() != 3)
                    && (usuario.getAlmacenesGlobal().getCodAlmacen() == 1)) {
                mensaje = "La operacion debe ser solicitada a GI";
                return null;
            }

            ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement();
            PreparedStatement pst = null;
            String consulta = "select * from USUARIOS_PERMISOS_ESPECIALES ups where ups.COD_MODULO='" + usuario.getCodigoModulo() + "' and ups.COD_PERSONAL='" + usuario.getUsuarioModuloBean().getCodUsuarioGlobal() + "'";
            LOGGER.info("consulta " + consulta);
            ResultSet result = st.executeQuery(consulta);

            if (!result.next()) {
                mensaje = "Usted no esta autorizado para anular la salida";
                return null;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            consulta = " update salidas_almacen set cod_estado_salida_almacen = 2,COD_ESTADO_TRANSACCION_SALIDA=0, COD_PERSONAL_ANULA='" + usuario.getUsuarioModuloBean().getCodUsuarioGlobal() + "',"
                    + " FECHA_ANULACION='" + sdf.format(new Date()) + "' where cod_salida_almacen = '" + salidasAlmacen.getCodSalidaAlmacen() + "'";
            LOGGER.info("consulta " + consulta);
            pst = con.prepareStatement(consulta);
            if (pst.executeUpdate() > 0) {
                System.out.println("se actualizo");
            }
            //this.actualizaProgramaProduccion(salidasAlmacen.getComponentesProd().getCodCompprod(), salidasAlmacen.getCodLoteProduccion());
            consulta = "select sa.COD_FORM_SALIDA from SALIDAS_ALMACEN sa where sa.COD_SALIDA_ALMACEN='" + salidasAlmacen.getCodSalidaAlmacen() + "'";
            result = st.executeQuery(consulta);
            int codForm = 0;
            if (result.next()) {
                codForm = result.getInt("COD_FORM_SALIDA");
            }
            ManagedRegistroSalidaAlmacen managed = new ManagedRegistroSalidaAlmacen();
            List<SalidasAlmacenDetalle> listadetalle = managed.listadoSalidasAlmacenDetalle(salidasAlmacen);
            for (SalidasAlmacenDetalle current : listadetalle) {
                consulta = "select sadi.CANTIDAD,sadi.ETIQUETA,sadi.COD_INGRESO_ALMACEN from salidas_almacen_detalle_ingreso sadi where sadi.cod_material='" + current.getMateriales().getCodMaterial() + "'and sadi.cod_salida_almacen='" + salidasAlmacen.getCodSalidaAlmacen() + "'";
                LOGGER.info("consulta " + consulta);
                Statement st1 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rest1 = st.executeQuery(consulta);
                String codIngreso = "";
                String etiqueta = "";
                float cantidad = 0;
                float cantidadTotal = 0;
                while (rest1.next()) {
                    codIngreso = rest1.getString("COD_INGRESO_ALMACEN");
                    etiqueta = rest1.getString("ETIQUETA");
                    cantidad = rest1.getFloat("CANTIDAD");

                    consulta = "update ingresos_almacen_detalle_estado set cantidad_restante=cantidad_restante+'" + cantidad + "'"
                            + "where cod_ingreso_almacen='" + codIngreso + "' and cod_material='" + current.getMateriales().getCodMaterial() + "' and etiqueta='" + etiqueta + "'";
                    LOGGER.info("consulta " + consulta);
                    pst = con.prepareStatement(consulta);
                    if (pst.executeUpdate() > 0) {
                        System.out.println("se actualizo el ingrso");
                    }
                    cantidadTotal = cantidadTotal + cantidad;
                }
                rest1.close();
                st1.close();
                salidasAlmacen.getCodFormSalida();
                consulta = "update solicitudes_salida_detalle set cantidad_entregada=cantidad_entregada-'" + cantidadTotal + "' where cod_form_salida='" + codForm + "' and cod_material='" + current.getMateriales().getCodMaterial() + "'";
                LOGGER.info("consulta " + consulta);
                pst = con.prepareStatement(consulta);
                if (pst.executeUpdate() > 0) {
                    System.out.println("se actualizo el registro");
                }

            }
            consulta = "select count(cantidad_entregada) as cantidad from solicitudes_salida_detalle where cantidad_entregada=0 and cod_form_salida='" + codForm + "'";
            result = st.executeQuery(consulta);
            int cont1 = 0;
            if (result.next()) {
                cont1 = result.getInt("cantidad");
            }
            consulta = "select count(cantidad_entregada) as cantidad from solicitudes_salida_detalle where cod_form_salida='" + codForm + "'";
            result = st.executeQuery(consulta);
            if (result.next()) {
                if (result.getInt("cantidad") == cont1) {
                    consulta = "update solicitudes_salida set cod_estado_solicitud_salida_almacen=1 where cod_form_salida='" + codForm + "'";

                } else {
                    consulta = "update solicitudes_salida set cod_estado_solicitud_salida_almacen=3 where cod_form_salida='" + codForm + "'";
                }
                LOGGER.info("consulta " + consulta);
                pst = con.prepareStatement(consulta);
                if (pst.executeUpdate() > 0) {
                    System.out.println("se actualizo la solicitud de salida");
                }
            }

            this.cargarSalidasAlmacen();

            //final alejandro
        } catch (Exception e) {
            LOGGER.warn(e);
        }
        //costeo
        try {
            AssignCostServiceMultiple costeo = new AssignCostServiceMultiple(Util.getPropertiesConnection());
            costeo.updateCostosMaterialByCodSalida(salidasAlmacen.getCodSalidaAlmacen());
        } catch (Exception e) {
            LOGGER.warn(e);
        }
        
        //fin costeo
        return null;
    }
    /* public int actualizaProgramaProduccion(String codCompProd,String codLoteProduccion){
     try {
     int salidasAlmacen = 0;
     String consulta = " select count(*) salidas_almacen from salidas_almacen s where s.cod_estado_salida_almacen = 1 and s.cod_almacen = 1 and cod_prod = '"+codCompProd+"' and cod_lote_produccion = '"+codLoteProduccion+"'";
     LOGGER.info("consulta " + consulta);
     Connection con = null;
     con = Util.openConnection(con);
     Statement st = con.createStatement();
     ResultSet rs = st.executeQuery(consulta);
     if(rs.next()){
     salidasAlmacen = rs.getInt("salidas_almacen");
     }
     if(salidasAlmacen==0){
     consulta = " update programa_produccion set cod_estado_programa = '2' where cod_lote_produccion = '"+codLoteProduccion +"' and cod_compprod = '"+codCompProd+"' ";
     LOGGER.info("consulta " + consulta);
     Statement st1 = con.createStatement();
     if(st1.executeUpdate(consulta)>0){
     System.out.println("se actualizo");
     }
     }
     } catch (Exception e) {
     LOGGER.warn(e);
     }
     return 0;
     }*/
    //--------------------------------------------------------------------------------
    public boolean verificarSalidaTieneSolicitudesPendientes() throws SQLException{
        boolean tieneSolicitudesPendientes = false;
        Connection con1 = null;
        try
        {
           
            con1 = Util.openConnection(con1);
            String consulta = " SELECT sts_a.COD_SALIDA_TRANSACCION_SOLICITUD"
                        + " FROM SALIDA_TRANSACCION_SOLICITUD sts_a"
                        + " INNER JOIN "
                        + " (   SELECT COD_SALIDA_ALMACEN, MAX(FECHA_SOLICITUD) AS MAX_FECHA_SOLICITUD"
                            + " FROM SALIDA_TRANSACCION_SOLICITUD"
                            + " GROUP BY COD_SALIDA_ALMACEN"
                        + " ) sts_group on sts_a.COD_SALIDA_ALMACEN = sts_group.COD_SALIDA_ALMACEN and sts_a.FECHA_SOLICITUD = sts_group.MAX_FECHA_SOLICITUD"
                        + " WHERE sts_a.COD_ESTADO_TRANSACCION_SOLICITUD in (1,2)"
                        + " AND sts_a.COD_SALIDA_ALMACEN = " + salidaAlmacenGestionar.getCodSalidaAlmacen();
            LOGGER.info("consulta verificacion: " + consulta);
            Statement st = con1.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            if(rs.next()) {
                tieneSolicitudesPendientes = true;
            } 
            rs.close();
            st.close();            
        }
        catch(SQLException ex)
        {
            LOGGER.warn(ex);
        }
        finally{
            con1.close();
        }
        return tieneSolicitudesPendientes;
    } 
    
    //------------------------------------REPORTE DE LOGS
    public String verReporteSalidaAlmacenLog_action(){
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String,Object> sessionMap = externalContext.getSessionMap();
        sessionMap.put("salidasAlmacen",salidaAlmacenGestionar);     
        return null;
    }
    
    public List getSalidasAlmacenList() {
        return salidasAlmacenList;
    }

    public void setSalidasAlmacenList(List salidasAlmacenList) {
        this.salidasAlmacenList = salidasAlmacenList;
    }

    public SalidasAlmacen getSalidasAlmacenBuscar() {
        return salidasAlmacenBuscar;
    }

    public void setSalidasAlmacenBuscar(SalidasAlmacen salidasAlmacenBuscar) {
        this.salidasAlmacenBuscar = salidasAlmacenBuscar;
    }

    public List getGestionesList() {
        return gestionesList;
    }

    public void setGestionesList(List gestionesList) {
        this.gestionesList = gestionesList;
    }

    public List getTiposSalidaAlmacenList() {
        return tiposSalidaAlmacenList;
    }

    public void setTiposSalidaAlmacenList(List tiposSalidaAlmacenList) {
        this.tiposSalidaAlmacenList = tiposSalidaAlmacenList;
    }

    public List getEstadosSalidaAlmacenList() {
        return estadosSalidaAlmacenList;
    }

    public void setEstadosSalidaAlmacenList(List estadosSalidaAlmacenList) {
        this.estadosSalidaAlmacenList = estadosSalidaAlmacenList;
    }

    public List getAreasEmpresalist() {
        return areasEmpresalist;
    }

    public void setAreasEmpresalist(List areasEmpresalist) {
        this.areasEmpresalist = areasEmpresalist;
    }

    public List getComponentesProdList() {
        return componentesProdList;
    }

    public void setComponentesProdList(List componentesProdList) {
        this.componentesProdList = componentesProdList;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public ManagedAccesoSistema getUsuario() {
        return usuario;
    }

    public void setUsuario(ManagedAccesoSistema usuario) {
        this.usuario = usuario;
    }

    public boolean isAdministradorAlmacen() {
        return administradorAlmacen;
    }

    public void setAdministradorAlmacen(boolean administradorAlmacen) {
        this.administradorAlmacen = administradorAlmacen;
    }

    public SalidasAlmacen getSalidasAlmacenFecha() {
        return salidasAlmacenFecha;
    }

    public void setSalidasAlmacenFecha(SalidasAlmacen salidasAlmacenFecha) {
        this.salidasAlmacenFecha = salidasAlmacenFecha;
    }

    public SalidasAlmacen getSalidaAlmacenGestionar() {
        return salidaAlmacenGestionar;
    }

    public void setSalidaAlmacenGestionar(SalidasAlmacen salidaAlmacenGestionar) {
        this.salidaAlmacenGestionar = salidaAlmacenGestionar;
    }

    public SalidaTransaccionSolicitud getSalidaTransaccionSolicitudGestionar() {
        return salidaTransaccionSolicitudGestionar;
    }

    public void setSalidaTransaccionSolicitudGestionar(SalidaTransaccionSolicitud salidaTransaccionSolicitudGestionar) {
        this.salidaTransaccionSolicitudGestionar = salidaTransaccionSolicitudGestionar;
    }

    public boolean isSalidaEditable() {
        return salidaEditable;
    }

    public void setSalidaEditable(boolean salidaEditable) {
        this.salidaEditable = salidaEditable;
    }

    public boolean isPermisoSolicitarAnular() {
        return permisoSolicitarAnular;
    }

    public void setPermisoSolicitarAnular(boolean permisoSolicitarAnular) {
        this.permisoSolicitarAnular = permisoSolicitarAnular;
    }

    public boolean isPermisoSolicitarEditar() {
        return permisoSolicitarEditar;
    }

    public void setPermisoSolicitarEditar(boolean permisoSolicitarEditar) {
        this.permisoSolicitarEditar = permisoSolicitarEditar;
    }

    public boolean isPermisoValidarAnular() {
        return permisoValidarAnular;
    }

    public void setPermisoValidarAnular(boolean permisoValidarAnular) {
        this.permisoValidarAnular = permisoValidarAnular;
    }

    public boolean isPermisoValidarEditar() {
        return permisoValidarEditar;
    }

    public void setPermisoValidarEditar(boolean permisoValidarEditar) {
        this.permisoValidarEditar = permisoValidarEditar;
    }

    public boolean isPermisoAnularDirectamente() {
        return permisoAnularDirectamente;
    }

    public void setPermisoAnularDirectamente(boolean permisoAnularDirectamente) {
        this.permisoAnularDirectamente = permisoAnularDirectamente;
    }

    public boolean isPermisoEditarDirectamente() {
        return permisoEditarDirectamente;
    }

    public void setPermisoEditarDirectamente(boolean permisoEditarDirectamente) {
        this.permisoEditarDirectamente = permisoEditarDirectamente;
    }

    public String getMotivoAnulacionSalida() {
        return motivoAnulacionSalida;
    }

    public void setMotivoAnulacionSalida(String motivoAnulacionSalida) {
        this.motivoAnulacionSalida = motivoAnulacionSalida;
    }

    public boolean isSalidaVisible() {
        return salidaVisible;
    }

    public void setSalidaVisible(boolean salidaVisible) {
        this.salidaVisible = salidaVisible;
    }

    public boolean isPermisoEditarHoy() {
        return permisoEditarHoy;
    }

    public void setPermisoEditarHoy(boolean permisoEditarHoy) {
        this.permisoEditarHoy = permisoEditarHoy;
    }

    public boolean isPermisoAnularHoy() {
        return permisoAnularHoy;
    }

    public void setPermisoAnularHoy(boolean permisoAnularHoy) {
        this.permisoAnularHoy = permisoAnularHoy;
    }

    public boolean isPermisoEditarEmpaquePrimarioLote() {
        return permisoEditarEmpaquePrimarioLote;
    }

    public void setPermisoEditarEmpaquePrimarioLote(boolean permisoEditarEmpaquePrimarioLote) {
        this.permisoEditarEmpaquePrimarioLote = permisoEditarEmpaquePrimarioLote;
    }

    public SalidasAlmacen getSalidasAlmacenSeleccion() {
        return salidasAlmacenSeleccion;
    }

    public void setSalidasAlmacenSeleccion(SalidasAlmacen salidasAlmacenSeleccion) {
        this.salidasAlmacenSeleccion = salidasAlmacenSeleccion;
    }
    
    
    

}
