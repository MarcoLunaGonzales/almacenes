 
        
        
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
public class EnvioCorreoNotificacionSolicitudSalidaAlmacen extends Thread 
{
    private Logger LOGGER;
    int codSalidaAlmacen;
    private Connection con;
    
    public EnvioCorreoNotificacionSolicitudSalidaAlmacen(int codSalidaAlmacen) {
        LOGGER = LogManager.getLogger("Salidas");
        this.codSalidaAlmacen = codSalidaAlmacen;
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

                            //GENERAR DATOS DE LA SALIDA
                            
                            StringBuilder consulta = new StringBuilder("")
                                .append(" select s.NRO_SALIDA_ALMACEN, s.FECHA_SALIDA_ALMACEN, isnull(s.COD_LOTE_PRODUCCION,'') AS COD_LOTE_PRODUCCION, s.COD_ALMACEN ")
                                .append(" , isnull(c.nombre_prod_semiterminado,'') AS nombre_prod_semiterminado, isnull(prp.NOMBRE_PRODUCTO_PRESENTACION, '') AS NOMBRE_PRODUCTO_PRESENTACION, tsa.NOMBRE_TIPO_SALIDA_ALMACEN ")
                                .append(" , ets.COD_ESTADO_TRANSACCION_SOLICITUD, ets.NOMBRE_ESTADO_TRANSACCION_SOLICITUD, OBSERVACION_SOLICITANTE")
                                .append(" , NOMBRE_SOLICITANTE, NOMBRE_TIPO_TRANSACCION_SOLICITUD_ALMACEN, FECHA_SOLICITUD, COD_SALIDA_TRANSACCION_SOLICITUD ")
                                .append(" , COD_PERSONAL_SOLICITANTE ")
                                .append(" from SALIDAS_ALMACEN s  inner join TIPOS_SALIDAS_ALMACEN tsa on tsa.COD_TIPO_SALIDA_ALMACEN = s.COD_TIPO_SALIDA_ALMACEN ")
                                .append(" left outer join COMPONENTES_PROD c on c.COD_COMPPROD = s.COD_PROD ")
                                .append(" left outer join PRESENTACIONES_PRODUCTO prp on prp.cod_presentacion = s.COD_PRESENTACION ")
                                .append(" left join ")
                                .append(" (   SELECT sts_a.COD_SALIDA_TRANSACCION_SOLICITUD , sts_a.COD_SALIDA_ALMACEN, COD_ESTADO_TRANSACCION_SOLICITUD ")
                                    .append(" , isnull(OBSERVACION_SOLICITANTE,'') as OBSERVACION_SOLICITANTE ")
                                    .append(" ,(ps.NOMBRES_PERSONAL+' '+ps.AP_PATERNO_PERSONAL+' '+ps.AP_MATERNO_PERSONAL) as NOMBRE_SOLICITANTE, sts_a.COD_PERSONAL_SOLICITANTE ")
                                    .append(" , ttsa.NOMBRE_TIPO_TRANSACCION_SOLICITUD_ALMACEN, FECHA_SOLICITUD ")
                                    .append(" FROM SALIDA_TRANSACCION_SOLICITUD sts_a ")
                                    .append(" inner join ")
                                    .append(" (   SELECT COD_SALIDA_ALMACEN, MAX(FECHA_SOLICITUD) AS MAX_FECHA_SOLICITUD ")
                                        .append(" FROM SALIDA_TRANSACCION_SOLICITUD")
                                        .append(" GROUP BY COD_SALIDA_ALMACEN")
                                    .append(" ) sts_group on sts_a.COD_SALIDA_ALMACEN = sts_group.COD_SALIDA_ALMACEN and sts_a.FECHA_SOLICITUD = sts_group.MAX_FECHA_SOLICITUD ")
                                    .append(" LEFT JOIN PERSONAL ps on ps.COD_PERSONAL = sts_a.COD_PERSONAL_SOLICITANTE ")
                                    .append(" LEFT JOIN TIPO_TRANSACCION_SOLICITUD_ALMACEN ttsa on ttsa.COD_TIPO_TRANSACCION_SOLICITUD_ALMACEN = sts_a.COD_TIPO_TRANSACCION_SOLICITUD_ALMACEN ")
                                .append(" ) sts on sts.COD_SALIDA_ALMACEN = s.COD_SALIDA_ALMACEN ")
                                .append(" left join ESTADO_TRANSACCION_SOLICITUD ets on ets.COD_ESTADO_TRANSACCION_SOLICITUD = sts.COD_ESTADO_TRANSACCION_SOLICITUD ")
                                .append(" where  s.COD_SALIDA_ALMACEN = ").append(codSalidaAlmacen) ;
                            LOGGER.info("consulta salida_almacen listar: "+consulta.toString());
                            res=st.executeQuery(consulta.toString());
                            int codPersonalSolicitante = 0;
                            int codAlmacen = 0;
                            if(res.next())
                            {
                                    correoPersonal.append("<table style='width:100%' cellpading='0' cellspacing='0'>");
                                        correoPersonal.append("<tr bgcolor='#93B5E2'>");
                                            correoPersonal.append("<td rowspan='2' style='border'>");
                                                correoPersonal.append("<span class='sistema'>BACO</span>");
                                            correoPersonal.append("</td>");
                                            correoPersonal.append("<td style='text-align:right'>");
                                                correoPersonal.append("<span class='evento'>Motivo:<b> Solicitud  para ").append(res.getString("NOMBRE_TIPO_TRANSACCION_SOLICITUD_ALMACEN")).append(" la salida N° ").append(res.getInt("NRO_SALIDA_ALMACEN")).append(".</b></span>");
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
                                            .append(" la salida N° ").append(res.getInt("NRO_SALIDA_ALMACEN")).append(".");
                                    correoPersonal.append("<br><br>");

                                    SimpleDateFormat sdfSalida = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                                    correoPersonal.append("<table style='margin-left:4em'>");
                                    correoPersonal.append("<tr><td><b>Solicitante</b></td><td><b>::</b></td><td>").append(res.getString("NOMBRE_SOLICITANTE")).append("</td></tr>");
                                    correoPersonal.append("<tr><td><b>Nro. Salida</b></td><td><b>::</b></td><td>").append(res.getInt("NRO_SALIDA_ALMACEN")).append("</td></tr>");
                                    correoPersonal.append("<tr><td><b>Fecha Salida</b></td><td><b>::</b></td><td>").append(sdfSalida.format(res.getTimestamp("FECHA_SALIDA_ALMACEN"))).append("</td></tr>");                                                        
                                    correoPersonal.append("<tr><td><b>Nro. Lote</b></td><td><b>::</b></td><td>").append(res.getString("COD_LOTE_PRODUCCION")).append("</td></tr>");
                                    correoPersonal.append("<tr><td><b>Producto</b></td><td><b>::</b></td><td>").append(res.getString("nombre_prod_semiterminado")).append("</td></tr>");
                                    correoPersonal.append("<tr><td><b>Presentación</b></td><td><b>::</b></td><td>").append(res.getString("NOMBRE_PRODUCTO_PRESENTACION")).append("</td></tr>");
                                    correoPersonal.append("<tr><td><b>Tipo Salida</b></td><td><b>::</b></td><td>").append(res.getString("NOMBRE_TIPO_SALIDA_ALMACEN")).append("</td></tr>");
                                    correoPersonal.append("<tr><td><b>Observación Solicitante</b></td><td><b>::</b></td><td>").append("(").append(sdfSalida.format(res.getTimestamp("FECHA_SOLICITUD"))).append(") ").append(res.getString("OBSERVACION_SOLICITANTE")).append("</td></tr>");
                                    correoPersonal.append("</table><br>");
                                codPersonalSolicitante = res.getInt("COD_PERSONAL_SOLICITANTE");
                                codAlmacen = res.getInt("COD_ALMACEN");
                            }
                            
                        //TERMINA GENERAR DATOS DE LA SALIDA
                         
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
                    msg.setSubject("Notificacion de Solicitud de Transacción de Salida Almacen");
                    msg.setFrom(new InternetAddress("controlDeCambios@cofar.com.bo", "BACO"));
                    
                    consulta=new StringBuilder(" select DISTINCT cp.nombre_correopersonal");
                             consulta.append(" from correo_personal cp");  
                             consulta.append(" left join CONFIGURACION_PERMISO_ESPECIAL_BACO cpeb on cp.COD_PERSONAL = cpeb.COD_PERSONAL");  
                             consulta.append(" where cpeb.COD_ALMACEN = ").append(codAlmacen);
                             consulta.append(" and cpeb.COD_TIPO_PERMISO_ESPECIAL_BACO in (5,6)");
                             consulta.append(" or cp.COD_PERSONAL = ").append(codPersonalSolicitante);
                             consulta.append(" order by cp.nombre_correopersonal");        
                    res=st.executeQuery(consulta.toString());
                    List<InternetAddress> correos=new ArrayList<InternetAddress>();
                    while(res.next())
                    {
                        correos.add(new InternetAddress(res.getString("nombre_correopersonal")));
                        LOGGER.info("envio correo: "+res.getString("nombre_correopersonal"));
                    }
                    
                    if(correos.size()>0)
                    {
                        LOGGER.debug("inicio envio correo notificacion solicitud de transaccion de salida de almacen");
                        msg.addRecipients(Message.RecipientType.TO,correos.toArray(new InternetAddress[correos.size()]));
                        
                        //COPIAS CORREO
                        consulta=new StringBuilder(" SELECT DISTINCT cp.nombre_correopersonal");
                             consulta.append(" FROM correo_personal cp ");  
                             consulta.append(" LEFT JOIN CONFIGURACION_ENVIO_CORREO_BACO mecb ON mecb.COD_PERSONAL = cp.COD_PERSONAL ");
                             consulta.append(" where mecb.COD_MOTIVO_ENVIO_CORREO_BACO = 6 ");
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
                        LOGGER.debug("termino envio correo notificacion solicitud de transaccion de salida de almacen");
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
        }finally
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
