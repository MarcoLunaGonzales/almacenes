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
    try
    {
        String[] codigosVentanas=(request.getParameter("codigosVentana")).split(",");
        con=Util.openConnection(con);
        con.setAutoCommit(false);
        StringBuilder consulta=new StringBuilder("INSERT INTO PERFILES_USUARIOS_BACO(NOMBRE_PERFIL,COD_ESTADO_REGISTRO)");
                            consulta.append("values(");
                                    consulta.append("?,");
                                    consulta.append("1");
                            consulta.append(")");
        System.out.println("consulta registrar perfil "+consulta.toString());
        PreparedStatement pst=con.prepareStatement(consulta.toString(),PreparedStatement.RETURN_GENERATED_KEYS);
        pst.setString(1,request.getParameter("nombrePerfil"));
        if(pst.executeUpdate()>0)System.out.println("Se registor el perfil");
        ResultSet res=pst.getGeneratedKeys();
        int codPerfil=0;
        if(res.next())codPerfil=res.getInt(1);
        consulta=new StringBuilder(" insert into PERFIL_ACCESO_VENTANA_BACO(COD_PERFIL,COD_VENTANA)");
                    consulta.append(" values(");
                            consulta.append(codPerfil).append(",");
                            consulta.append("?");
                    consulta.append(")");
        pst=con.prepareStatement(consulta.toString());
        for(String codVentana:codigosVentanas)
        {
            pst.setString(1, codVentana);
            if(pst.executeUpdate()>0)System.out.println("Se registro la ventana "+codVentana);
        }
        
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


