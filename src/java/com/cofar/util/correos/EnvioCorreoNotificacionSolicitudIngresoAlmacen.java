/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofar.util.correos;

import com.cofar.util.Util;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 *
 * @author rcondori
 */
public class EnvioCorreoNotificacionSolicitudIngresoAlmacen extends Thread 
{
    private Logger LOGGER;
    int codIngresoAlmacen;
    private Connection con;
    
    public EnvioCorreoNotificacionSolicitudIngresoAlmacen(int codIngresoAlmacen) {
        LOGGER = LogManager.getLogger("Ingresos");
        this.codIngresoAlmacen = codIngresoAlmacen;
    }
    
    @Override
    public void run() 
    {
        try 
        {
            con=Util.openConnection(con);
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet res;
            
            SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy HH:mm");
            StringBuilder correoPersonal=
                new StringBuilder("<html>");
                    correoPersonal.append("<head>");
                        correoPersonal.append("<style>");
                            correoPersonal.append(".separador{width:100%;background-color:#93B5E2;font-size:3px;margin-top:3px;}");
                            correoPersonal.append(".sistema{color:white;font-size:22px;font-style:italic;font-weight:bold;}");
                            correoPersonal.append(".evento{color:white;font-size:14px;}");
                            correoPersonal.append("	body,table{font-family: Verdana, Arial, Helvetica, sans-serif;font-size:14px;}");
                            correoPersonal.append("	td{padding:4px;}");
                            correoPersonal.append("	.footer{font-size:10px;color:#aaaaaa;border:none !important;}");
                            correoPersonal.append("	.footer tr td{border:1px solid #bbbbbb;}");
                            correoPersonal.append(" .tablaDetalle{font-size:14px;}");
                            correoPersonal.append(" .tablaDetalle thead tr td{background-color:#93B5E2;color:white;font-weight:bold;}");
                            correoPersonal.append(" .tablaDetalle tr td{border:1px solid #bbbbbb;}");
                        correoPersonal.append("</style>");
                    correoPersonal.append("</head>");
                    correoPersonal.append("<body>");
                        correoPersonal.append("<div>");

                            //GENERAR DATOS DE LA INGRESO
                            
                            StringBuilder consulta = new StringBuilder("")
                                .append(" select ia.NRO_INGRESO_ALMACEN,ia.COD_INGRESO_ALMACEN, ia.FECHA_INGRESO_ALMACEN, ia.COD_ALMACEN ")
                                .append(" , t.NOMBRE_TIPO_INGRESO_ALMACEN, oc.NRO_ORDEN_COMPRA, pr.NOMBRE_PROVEEDOR, p.NOMBRE_PAIS ")
                                .append(" , isnull(sts.COD_ESTADO_TRANSACCION_SOLICITUD,0) as COD_ESTADO_TRANSACCION_SOLICITUD ")
                                .append(" , isnull(ets.NOMBRE_ESTADO_TRANSACCION_SOLICITUD,'') as NOMBRE_ESTADO_TRANSACCION_SOLICITUD ")
                                .append(" , sts.NOMBRE_SOLICITANTE, sts.OBSERVACION_SOLICITANTE, sts.FECHA_SOLICITUD ")
                                .append(" , sts.COD_INGRESO_TRANSACCION_SOLICITUD, sts.NOMBRE_TIPO_TRANSACCION_SOLICITUD_ALMACEN ")
                                .append(" , sts.COD_PERSONAL_SOLICITANTE ")
                                .append(" from INGRESOS_ALMACEN ia ")
                                .append(" left outer join  TIPOS_INGRESO_ALMACEN t on t.COD_TIPO_INGRESO_ALMACEN = ia.COD_TIPO_INGRESO_ALMACEN ")
                                .append(" left outer join proveedores pr on pr.COD_PROVEEDOR = ia.COD_PROVEEDOR ")
                                .append(" left outer join  paises p on pr.COD_PAIS = p.COD_PAIS ")
                                .append(" left outer join ORDENES_COMPRA oc on oc.COD_ORDEN_COMPRA = ia.COD_ORDEN_COMPRA ")
                                .append(" left outer join ESTADOS_INGRESO_ALMACEN eia on eia.COD_ESTADO_INGRESO_ALMACEN = ia.COD_ESTADO_INGRESO_ALMACEN ")
                                .append(" left outer join TIPOS_COMPRA tc on tc.COD_TIPO_COMPRA = ia.COD_TIPO_COMPRA ")
                                .append(" left join ")
                                .append(" (   SELECT sts_a.COD_INGRESO_TRANSACCION_SOLICITUD , sts_a.COD_INGRESO_ALMACEN, COD_ESTADO_TRANSACCION_SOLICITUD ")
                                    .append(" , isnull(OBSERVACION_SOLICITANTE,'') as OBSERVACION_SOLICITANTE")
                                    .append(" ,(ps.NOMBRES_PERSONAL+' '+ps.AP_PATERNO_PERSONAL+' '+ps.AP_MATERNO_PERSONAL) as NOMBRE_SOLICITANTE, sts_a.COD_PERSONAL_SOLICITANTE ")
                                    .append(" , ttsa.NOMBRE_TIPO_TRANSACCION_SOLICITUD_ALMACEN, FECHA_SOLICITUD")
                                    .append(" FROM INGRESO_TRANSACCION_SOLICITUD sts_a ")
                                    .append(" inner join ")
                                    .append(" (   SELECT COD_INGRESO_ALMACEN, MAX(FECHA_SOLICITUD) AS MAX_FECHA_SOLICITUD ")
                                        .append(" FROM INGRESO_TRANSACCION_SOLICITUD")
                                        .append(" GROUP BY COD_INGRESO_ALMACEN")
                                    .append(" ) sts_group on sts_a.COD_INGRESO_ALMACEN = sts_group.COD_INGRESO_ALMACEN and sts_a.FECHA_SOLICITUD = sts_group.MAX_FECHA_SOLICITUD ")
                                    .append(" LEFT JOIN PERSONAL ps on ps.COD_PERSONAL = sts_a.COD_PERSONAL_SOLICITANTE ")
                                    .append(" LEFT JOIN TIPO_TRANSACCION_SOLICITUD_ALMACEN ttsa on ttsa.COD_TIPO_TRANSACCION_SOLICITUD_ALMACEN = sts_a.COD_TIPO_TRANSACCION_SOLICITUD_ALMACEN ")
                                .append(" ) sts on sts.COD_INGRESO_ALMACEN = ia.COD_INGRESO_ALMACEN ")
                                .append(" left join ESTADO_TRANSACCION_SOLICITUD ets on ets.COD_ESTADO_TRANSACCION_SOLICITUD = sts.COD_ESTADO_TRANSACCION_SOLICITUD ")
                                .append(" where  ia.COD_INGRESO_ALMACEN = ").append(codIngresoAlmacen) ;
                            LOGGER.info("consulta INGRESOS_ALMACEN listar: "+consulta.toString());
                            res=st.executeQuery(consulta.toString());
                            int codAlmacen = 0;
                            int codPersonalSolicitante = 0;
                            if(res.next())
                            {
                                    correoPersonal.append("<table style='width:100%' cellpading='0' cellspacing='0'>");
                                        correoPersonal.append("<tr bgcolor='#93B5E2'>");
                                            correoPersonal.append("<td rowspan='2' style='border'>");
                                                correoPersonal.append("<span class='sistema'>BACO</span>");
                                            correoPersonal.append("</td>");
                                            correoPersonal.append("<td style='text-align:right'>");
                                                correoPersonal.append("<span class='evento'>Motivo:<b> Solicitud  para ").append(res.getString("NOMBRE_TIPO_TRANSACCION_SOLICITUD_ALMACEN")).append(" el ingreso N° ").append(res.getInt("NRO_INGRESO_ALMACEN")).append(".</b></span>");
                                            correoPersonal.append("</td>");
                                        correoPersonal.append("</tr>");
                                        correoPersonal.append("<tr bgcolor='#93B5E2'>");
                                            correoPersonal.append("<td style='text-align:right'>");
                                                correoPersonal.append("<span class='evento'>Fecha Notificación: <b>").append(sdf.format(new Date())).append("</b></span>");
                                            correoPersonal.append("</td>");
                                        correoPersonal.append("</tr>");
                                    correoPersonal.append("</table>");
                                correoPersonal.append("</div>");
                                correoPersonal.append("<div class='separador'>&nbsp;</div>");
                                correoPersonal.append("<div style='margin-left:15px'>");
                                    correoPersonal.append("<br>");
                                    correoPersonal.append("Estimad@ Usuari@:<br><br>");

                                    correoPersonal.append("Se comunica que se generó una solicitud para ").append(res.getString("NOMBRE_TIPO_TRANSACCION_SOLICITUD_ALMACEN").toUpperCase())
                                            .append(" el ingreso N° ").append(res.getInt("NRO_INGRESO_ALMACEN")).append(".");
                                    correoPersonal.append("<br><br>");

                                    SimpleDateFormat sdfIngreso = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                                    correoPersonal.append("<table style='margin-left:4em'>");
                                    correoPersonal.append("<tr><td><b>Solicitante</b></td><td><b>::</b></td><td>").append(res.getString("NOMBRE_SOLICITANTE")).append("</td></tr>");
                                    correoPersonal.append("<tr><td><b>Nro. Ingreso</b></td><td><b>::</b></td><td>").append(res.getInt("NRO_INGRESO_ALMACEN")).append("</td></tr>");
                                    correoPersonal.append("<tr><td><b>Fecha Ingreso</b></td><td><b>::</b></td><td>").append(sdfIngreso.format(res.getTimestamp("FECHA_INGRESO_ALMACEN"))).append("</td></tr>"); 
                                    correoPersonal.append("<tr><td><b>Tipo Ingreso</b></td><td><b>::</b></td><td>").append(res.getString("NOMBRE_TIPO_INGRESO_ALMACEN")).append("</td></tr>");
                                    correoPersonal.append("<tr><td><b>Nro. Orden Compra</b></td><td><b>::</b></td><td>").append(res.getString("NRO_ORDEN_COMPRA")!=null?res.getString("NRO_ORDEN_COMPRA"):"").append("</td></tr>");
                                    correoPersonal.append("<tr><td><b>Proveedor</b></td><td><b>::</b></td><td>").append(res.getString("NOMBRE_PROVEEDOR")!=null?res.getString("NOMBRE_PROVEEDOR"):"").append("</td></tr>");
                                    correoPersonal.append("<tr><td><b>Pais Proveedor</b></td><td><b>::</b></td><td>").append(res.getString("NOMBRE_PAIS")!=null?res.getString("NOMBRE_PAIS"):"").append("</td></tr>");
                                    correoPersonal.append("<tr><td><b>Observación Solicitante</b></td><td><b>::</b></td><td>").append("(").append(sdfIngreso.format(res.getTimestamp("FECHA_SOLICITUD"))).append(") ").append(res.getString("OBSERVACION_SOLICITANTE")).append("</td></tr>");
                                    correoPersonal.append("</table><br>");
                                codAlmacen = res.getInt("COD_ALMACEN");
                                codPersonalSolicitante = res.getInt("COD_PERSONAL_SOLICITANTE");
                            }
                            
                        //TERMINA GENERAR DATOS DE LA INGRESO
                         
                        correoPersonal.append("</div>");
                        correoPersonal.append("<div align='left'>");
                            correoPersonal.append("<table class='footer'>");
                                correoPersonal.append("<tr>");
                                    correoPersonal.append("<td bgcolor='#bbbbbb' class='evento'>BACO</td>");
                                    correoPersonal.append("<td >");
                                    correoPersonal.append("El presente correo tiene carácter puramente informativo.<br>");
                                    correoPersonal.append("No trate de responder al remitente.");
                                    correoPersonal.append("</td>");
                                correoPersonal.append("</tr>");
                            correoPersonal.append("<table>");
                        correoPersonal.append("</div>");
                    correoPersonal.append("</body>");
                correoPersonal.append("</html>");
                     LOGGER.debug("correo "+correoPersonal.toString());
                     System.setProperty("java.net.preferIPv4Stack" , "true");
                     Properties props = new Properties();
                     props.put("mail.smtp.host", "host2.cofar.com.bo");
                     props.put("mail.transport.protocol", "smtp");
                     props.put("mail.smtp.auth", "false");
                     props.setProperty("mail.user", "controlDeCambios@cofar.com.bo");
                     props.setProperty("mail.password", "105021ej");
                    
                    Session mailSession = Session.getInstance(props, null);
                    Message msg = new MimeMessage(mailSession);
                    msg.setSubject("Notificacion de Solicitud de Transacción de Ingreso Almacen");
                    msg.setFrom(new InternetAddress("controlDeCambios@cofar.com.bo", "BACO"));
                    
                    consulta=new StringBuilder(" select DISTINCT cp.nombre_correopersonal");
                             consulta.append(" from correo_personal cp");  
                             consulta.append(" left join CONFIGURACION_PERMISO_ESPECIAL_BACO cpeb on cp.COD_PERSONAL = cpeb.COD_PERSONAL");  
                             consulta.append(" where cpeb.COD_ALMACEN = ").append(codAlmacen);
                             consulta.append(" and cpeb.COD_TIPO_PERMISO_ESPECIAL_BACO in (11,12)");
                             consulta.append(" or cp.COD_PERSONAL = ").append(codPersonalSolicitante);
                             consulta.append(" order by cp.nombre_correopersonal");
                    res=st.executeQuery(consulta.toString());
                    List<InternetAddress> correos=new ArrayList<InternetAddress>();
                    while(res.next()){
                        correos.add(new InternetAddress(res.getString("nombre_correopersonal")));
                        LOGGER.info("envio correo: "+res.getString("nombre_correopersonal"));
                    }
                    
                    if(correos.size()>0)
                    {
                        LOGGER.debug("inicio envio correo notificacion solicitud de transaccion de ingreso de almacen");
                        msg.addRecipients(Message.RecipientType.TO,correos.toArray(new InternetAddress[correos.size()]));
                        
                        //COPIAS CORREO
                        consulta=new StringBuilder(" SELECT DISTINCT cp.nombre_correopersonal");
                             consulta.append(" FROM correo_personal cp ");  
                             consulta.append(" LEFT JOIN CONFIGURACION_ENVIO_CORREO_BACO mecb ON mecb.COD_PERSONAL = cp.COD_PERSONAL ");
                             consulta.append(" where mecb.COD_MOTIVO_ENVIO_CORREO_BACO = 8 ");
                             consulta.append(" order by cp.nombre_correopersonal");                                          
                        res = st.executeQuery(consulta.toString());
                        List<InternetAddress> correosCopia = new ArrayList<InternetAddress>();
                        while(res.next())
                        {
                            correosCopia.add(new InternetAddress(res.getString("nombre_correopersonal")));
                            LOGGER.info("envio correo copia: "+res.getString("nombre_correopersonal"));
                        }
                        
                        msg.addRecipients(Message.RecipientType.CC,correosCopia.toArray(new InternetAddress[correosCopia.size()]));
                        BodyPart mensaje = new MimeBodyPart();
                        mensaje.setContent(correoPersonal.toString(),"text/html");
                        MimeMultipart multiParte = new MimeMultipart();
                        multiParte.addBodyPart(mensaje);
                        msg.setContent(multiParte);
                        
                        System.setProperty("java.net.preferIPv4Stack" , "true");
                        javax.mail.Transport.send(msg);
                        LOGGER.debug("termino envio correo notificacion solicitud de transaccion de ingreso de almacen");
                    }
        }
        catch(SQLException ex)
        {
            LOGGER.warn("error", ex);
        }
        catch (MessagingException ex) 
        {
            LOGGER.warn("error", ex);
        } 
        catch (UnsupportedEncodingException ex) 
        {
            LOGGER.warn("error", ex);
        } finally
        {
            try
            {
                con.close();
            }
            catch(SQLException ex)
            {
                LOGGER.warn("error", ex);
            }
        }
    }

}
