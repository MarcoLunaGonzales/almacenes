<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page language="java" %>
<%@ page import = "java.sql.*"%>
<%@ page import = "java.sql.DriverManager"%> 
<%@ page import = "java.sql.ResultSet"%> 
<%@ page import = "java.sql.Statement"%> 
<%@ page import="com.cofar.util.*" %>

<%
    String mensaje="";
    Connection con=null;
    int COD_MODULO_BACO = 2;
    try
    {
        String codPersonal=request.getParameter("codPersonal");
        con=Util.openConnection(con);
        con.setAutoCommit(false);
        
        StringBuilder consulta=new StringBuilder("delete USUARIOS_ACCESOS_MODULOS_BACO ");
                                consulta.append(" where COD_MODULO= ").append(COD_MODULO_BACO);
                                consulta.append(" and COD_PERSONAL=").append(codPersonal);
        System.out.println("consulta eliminar usuarios accesos modulos "+consulta.toString());
        PreparedStatement pst=con.prepareStatement(consulta.toString());
        if(pst.executeUpdate()>0)System.out.println("se eliminaron los accesos");
        
        consulta=new StringBuilder("delete USUARIOS_MODULOS ");
                    consulta.append(" where COD_MODULO = ").append(COD_MODULO_BACO);
                    consulta.append(" and COD_PERSONAL=").append(codPersonal);
        System.out.println("consulta eliminar usuarios modulos "+consulta.toString());
        pst=con.prepareStatement(consulta.toString());
        if(pst.executeUpdate()>0)System.out.println("se eliminaron los accesos");
        
        consulta = new StringBuilder("delete ALMACEN_HABILITADO_USUARIO")
                            .append(" where COD_PERSONAL=").append(codPersonal);
        System.out.println("consulta eliminar almacen habilitado "+consulta.toString());
        pst = con.prepareStatement(consulta.toString());
        if(pst.executeUpdate() > 0)System.out.println("se elimino almacen habilitado usuario");
        
        con.commit();
        mensaje="1";
        
    }
    catch(SQLException ex) 
    {
        ex.printStackTrace();
        mensaje="Ocurrio un error al momento de registrar el perfil";
    }
    catch(Exception ex) 
    {
        ex.printStackTrace();
        mensaje="Ocurrio un error al momento de registrar el perfil";
    }
    finally
    {
        con.close();
    }
    out.clear();
    out.println(mensaje);
%>


