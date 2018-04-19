/*
 * Util.java
 * Created on 11 de febrero de 2008, 16:53
 *
 */
package com.cofar.util;

import com.cofar.web.ManagedAccesoSistema;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Wilmer Manzaneda Chavez
 */
public class Util {

    /**
     * Creates a new instance of Util
     */
    private static String driver=FacesContext.getCurrentInstance().getExternalContext().getInitParameter("driver");
    // private static String url=FacesContext.getCurrentInstance().getExternalContext().getInitParameter("url");
    private static String user=FacesContext.getCurrentInstance().getExternalContext().getInitParameter("user");
     private static String password=FacesContext.getCurrentInstance().getExternalContext().getInitParameter("password");
     private static String host=FacesContext.getCurrentInstance().getExternalContext().getInitParameter("host");
     private static String database=FacesContext.getCurrentInstance().getExternalContext().getInitParameter("database");
     private static String uri=FacesContext.getCurrentInstance().getExternalContext().getInitParameter("uri");
     
   /* private static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

    private static String user = "sa";
    private static String password = "m0t1t4s@2009";
    private static String host = "172.16.10.21";
    private static String database = "sartorius";
    private static String uri = "none";
*/
    public Util() {

    }

    /**
     * Metodo que realiza la conexion a la base de datos nos retorna una
     * conexion
     *
     * @param Connecticon este parametro se refiere a la conexion actual
     * @return Connection con
     */
    public static Connection openConnection(Connection connection) throws SQLException {
        if (connection != null) {
            if (connection.isClosed()) {
                connection = getConnection();
            }
        } else {
            //HttpServletRequest request=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
            //Connection con=(Connection)request.getSession().getAttribute("connection");
            //if(con==null)
            connection = getConnection();
            //request.getSession().setAttribute("connection",connection);
        }
        return connection;
    }

    /**
     * Metodo que realiza la conexion a la base de datos nos retorna una
     * conexion
     *
     * @return Connection con
     */
    public static Properties getPropertiesConnection(){
        Properties properties = new Properties();
        properties.setProperty("user", user);
        properties.setProperty("host", host);
        properties.setProperty("password", password);
        properties.setProperty("database", database);
        properties.setProperty("driver", driver);
        return properties;
    }
    private static Connection getConnection() {
        Connection con = null;
        try {
            String url = "";
            Class.forName(driver);
            if (!uri.equalsIgnoreCase("none")) {
                url = "jdbc:odbc:" + uri;
                System.out.println("url:" + url);
                con = DriverManager.getConnection(url, user, password);
            } else {
                url = "jdbc:sqlserver://" + host + ";user=" + user + ";password=" + password + ";databaseName=" + database;
                //System.out.println("url:"+url);
                con = DriverManager.getConnection(url);
                Statement st1 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                st1.executeUpdate("set DATEFORMAT ymd");
                // System.out.println("pasosososososo");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return con;
    }

    /**
     * metodo que nos retorna un objeto que se encuentra en session
     *
     * @param namebean es el nombre del objeto que queremos que nos retorne
     */
    public static Object getSessionBean(String namebean) {
        Map map = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        return map.get(namebean);
    }

    /**
     * metodo que nos retorna un objeto que se encuentra en session
     *
     * @param nameobjectsession es el nombre del objeto que queremos que nos
     * retorne
     */

    public static Object getSession(String nameobjectsession) {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return request.getSession().getAttribute(nameobjectsession);
    }

    /**
     * metodo que nos retorna un String que se encuentra en session
     *
     * @param nameobjectsession es el nombre del objeto que queremos que nos
     * retorne
     */

    public static String getParameter(String name) {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return request.getParameter(name);
    }

    public static Date converterStringDate(String fecha) {
        String values[] = fecha.split("/");
        if (values.length == 0) {
            throw new java.lang.IllegalArgumentException();
        }
        String format = values[2] + "-" + values[1] + "-" + values[0];
        Date date = java.sql.Date.valueOf(format);
        return date;

    }

    public static void setSession(String name, Object obj) {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        request.setAttribute(name, obj);
    }

    public static void removeSession(String name) {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        request.getSession().removeAttribute(name);
    }

    public static String enviarCorreo(String codPersonal, String mensaje, String asunto, String enviadoPor) {
        try {
            System.setProperty("java.net.preferIPv4Stack", "true");
            //ManagedAccesoSistema bean1 = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            String consulta = " select nombre_correopersonal from correo_personal c where c.cod_personal in (" + codPersonal + ") ";
            System.out.println("consulta " + consulta);
            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(consulta);
            InternetAddress emails[] = new InternetAddress[1];
            if (rs.next()) {
                emails[0] = new InternetAddress(rs.getString("nombre_correopersonal"));
            }
            Properties props = new Properties();

            props.put("mail.smtp.host", "host2.cofar.com.bo");
            props.put("mail.transport.protocol", "smtp");
            
            props.put("mail.smtp.auth", "false");
            props.setProperty("mail.user", "traspasos@cofar.com.bo");
            props.setProperty("mail.password", "n3td4t4");

            Session mailSession = Session.getInstance(props, null);
            Message msg = new MimeMessage(mailSession);
            msg.setSubject(asunto);
            msg.setFrom(new InternetAddress("traspasos@cofar.com.bo", enviadoPor));
            msg.addRecipients(Message.RecipientType.TO, emails);

            String contenido = " <html> <head>  <title></title> "
                    + " <meta http-equiv='Content-Type' content='text/html; charset=windows-1252'> "
                    + " </head> "
                    + " <body>" + mensaje
                    + " </body> </html> ";
            msg.setContent(contenido, "text/html");
            System.setProperty("java.net.preferIPv4Stack", "true");
            javax.mail.Transport.send(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static String enviarCorreoBaco(String codPersonal, String mensaje, String asunto, String enviadoPor) {
        try {
            System.setProperty("java.net.preferIPv4Stack", "true");
            //ManagedAccesoSistema bean1 = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            String consulta = " select nombre_correopersonal from correo_personal c where c.cod_personal in (" + codPersonal + ") ";
            System.out.println("consulta " + consulta);
            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(consulta);
            
            List<InternetAddress>lista_emails=new ArrayList();
            while (rs.next()) {
                lista_emails.add(new InternetAddress(rs.getString("nombre_correopersonal")));
            }
            InternetAddress emails[];
            emails=lista_emails.toArray(new InternetAddress[lista_emails.size()]);
            System.out.println("size: "+emails.length);
            Properties props = new Properties();

            props.put("mail.smtp.host", "host2.cofar.com.bo");
            props.put("mail.transport.protocol", "smtp");
            
            props.put("mail.smtp.auth", "false");
            props.setProperty("mail.user", "traspasos@cofar.com.bo");
            props.setProperty("mail.password", "n3td4t4");

            Session mailSession = Session.getInstance(props, null);
            Message msg = new MimeMessage(mailSession);
            msg.setSubject(asunto);
            msg.setFrom(new InternetAddress("traspasos@cofar.com.bo", enviadoPor));
            msg.addRecipients(Message.RecipientType.TO, emails);

            String contenido = " <html> <head>  <title></title> "
                    + " <meta http-equiv='Content-Type' content='text/html; charset=windows-1252'> "
                    + " </head> "
                    + " <body>" + mensaje
                    + " </body> </html> ";
            msg.setContent(contenido, "text/html");
             System.setProperty("java.net.preferIPv4Stack", "true");
            javax.mail.Transport.send(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String args[]) {
        Util.enviarCorreoBaco("1242,1893,1858", "mensaje prueba", "test mail", "actualizacion costo");
    }
}
