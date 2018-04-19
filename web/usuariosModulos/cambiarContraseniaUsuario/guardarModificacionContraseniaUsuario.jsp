<%@page import="java.security.MessageDigest"%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page language="java" %>
<%@ page import = "java.sql.*"%>
<%@ page import = "java.sql.DriverManager"%> 
<%@ page import = "java.sql.ResultSet"%> 
<%@ page import = "java.sql.Statement"%> 
<%@ page import="com.cofar.util.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.GregorianCalendar" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%
    
    Connection con=null;
    String codPersonal=request.getParameter("codPersonal");
    String contrasena=request.getParameter("contraseniaNueva");
    String nombreUsuario = request.getParameter("nombreUsuario");
    String mensaje="";
    try
    {
        con=Util.openConnection(con);
        con.setAutoCommit(false);
        StringBuilder consulta=new StringBuilder(" update usuarios_modulos set");
                                            consulta.append(" NOMBRE_USUARIO = ?");
                                            consulta.append(" ,contrasena_usuario = ?");
                                            consulta.append(" ,FECHA_VENCIMIENTO = DATEADD(MONTH,3,GETDATE())");
                                    consulta.append(" WHERE COD_PERSONAL=").append(codPersonal);
        System.out.println("consulta cambiar contrasenia usuario "+consulta.toString());
        PreparedStatement pst=con.prepareStatement(consulta.toString());
        pst.setString(1,nombreUsuario);System.out.println("p1: "+nombreUsuario);
        pst.setString(2,contrasena);System.out.println("p2: "+contrasena);
        if(pst.executeUpdate()>0)System.out.println("se guardo el cambio de contrasenia");
        con.commit();
        mensaje="El cambio de contraseña se realizo de forma exitosa";
    }
    catch(Exception ex) 
    {
        mensaje="Ocurrio un error al momento de guardar el cambio de contraseña";
        ex.printStackTrace();
        con.rollback();
    }
    finally
    {
        con.close();
    }
%>
<html>
    
    <head>
        <link rel="STYLESHEET" type="text/css" href="../../css/ventas.css" />
        <script src="../../js/general.js"></script>
         <style type="text/css">
            .hint
            {
                font-weight: bold;
                display:none;
                position: absolute;
                border: 1px solid #eed3d7;
                color:#b94a48;
                width: 250px;
                text-align:left;
                z-index: 1000;
                font-size: 12px;
                background: #f2dede url(pointer.gif) no-repeat -10px 5px;
            }
            .tablaFiltroReporte
{
                font-family: Verdana, Arial, Helvetica, sans-serif;
                font-size: 12px;
                border:1px solid #bbbbbb;
            }
            .tablaFiltroReporte tr td
            {
                padding: 5px;
            }
            .tablaFiltroReporte thead tr td
            {
                color: white;
                border-bottom:1px solid #bbbbbb;
                text-align: center;
                font-weight: bold;
            }
            .tablaFiltroReporte tfoot tr td
            {
                text-align: center;
            }

        </style>
    </head>
    <body class="tdCenter">
        <center>
            <table class="tablaFiltroReporte" style="margin-top:20px" cellpadding="0" cellspacing="0">
                <thead>
                    <tr>
                        <td class="headerClassACliente">Mensaje</td>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>
                            <h3><%=(mensaje)%></h3>
                            <h3>ESTA CONTRASEÑA ES VÁLIDA PARA TODOS LOS SISTEMAS</h3>
                            <h4>Su nombre de usuario a partir de la fecha es: <%=(nombreUsuario)%></h4>
                            <h4>Su contraseña tiene una validez de 3 meses</h4>
                        </td>
                    </tr>
                    <tr>
                        <td class="tdCenter">
                            <a href="#" class="btn" onclick="window.location.href='cambiarContraseniaUsuario.jsf'">volver</a>
                        </td>
                    </tr>
                </tbody>

            </table>
        </center>
    </body>
</html>

