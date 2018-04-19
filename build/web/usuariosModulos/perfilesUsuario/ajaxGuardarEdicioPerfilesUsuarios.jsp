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
    int COD_MODULO_ALMACENES =2;
    try
    {
        String[] codigosVentanas=(request.getParameter("codigosVentana")).split(",");
        String codPerfil=request.getParameter("codPerfil");
        con=Util.openConnection(con);
        con.setAutoCommit(false);
        StringBuilder consulta=new StringBuilder("UPDATE PERFILES_USUARIOS_BACO");
                            consulta.append(" set NOMBRE_PERFIL=?");
                            consulta.append(" ,COD_ESTADO_REGISTRO=").append(request.getParameter("codEstadoRegistro"));
                            consulta.append(" where COD_PERFIL=").append(codPerfil);
        System.out.println("consulta update perfil "+consulta.toString());
        PreparedStatement pst=con.prepareStatement(consulta.toString(),PreparedStatement.RETURN_GENERATED_KEYS);
        pst.setString(1,request.getParameter("nombrePerfil"));
        if(pst.executeUpdate()>0)System.out.println("Se registro el perfil");
        consulta=new StringBuilder("DELETE PERFIL_ACCESO_VENTANA_BACO");
                consulta.append(" WHERE COD_PERFIL=").append(codPerfil);
        System.out.println("consulta eliminar detalle perfil "+consulta.toString());
        pst=con.prepareStatement(consulta.toString());
        if(pst.executeUpdate()>0)System.out.print("se eliminaron las ventanas de perfil");
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
        consulta=new StringBuilder(" select distinct u.COD_PERSONAL");
                    consulta.append(" from USUARIOS_ACCESOS_MODULOS_BACO u");
                    consulta.append(" where u.COD_MODULO= ").append(COD_MODULO_ALMACENES);
                        consulta.append(" and u.COD_PERFIL=").append(codPerfil);
        System.out.println("consulta cargar personal con perfil "+consulta.toString());
        Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        ResultSet res=st.executeQuery(consulta.toString());
        StringBuilder codigosPersonal=new StringBuilder("");
        while(res.next())
        {
            if(codigosPersonal.length()>0)codigosPersonal.append(",");
            codigosPersonal.append(res.getString("COD_PERSONAL"));
        }
        if(codigosPersonal.length()>0)
        {
            consulta=new StringBuilder(" delete USUARIOS_ACCESOS_MODULOS_BACO");
                        consulta.append(" where COD_MODULO=").append(COD_MODULO_ALMACENES);
                        consulta.append(" and COD_PERFIL=").append(codPerfil);
            System.out.println("consulta delete personal con perfil "+consulta.toString());
            pst=con.prepareStatement(consulta.toString());
            if(pst.executeUpdate()>0)System.out.println(" se eliminaron perfiles ");
            consulta=new StringBuilder("INSERT INTO USUARIOS_ACCESOS_MODULOS_BACO ");
                        consulta.append(" SELECT um.COD_PERSONAL,um.COD_MODULO,pava.COD_VENTANA,1,pava.COD_PERFIL");
                        consulta.append(" FROM PERFIL_ACCESO_VENTANA_BACO pava,");
                                consulta.append(" USUARIOS_MODULOS um");
                        consulta.append(" where um.COD_MODULO=").append(COD_MODULO_ALMACENES);
                                consulta.append(" and um.COD_PERSONAL in (").append(codigosPersonal.toString()).append(")");
                                consulta.append(" and pava.COD_PERFIL=").append(codPerfil);
            System.out.println("consulta registrar ventanas perfil "+consulta.toString());
            pst=con.prepareStatement(consulta.toString());
            if(pst.executeUpdate()>0)System.out.println(" se registro el acceso del personal ");

            
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


