<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page language="java" %>
<%@ page import = "java.sql.*"%>
<%@ page import = "java.sql.DriverManager"%> 
<%@ page import = "java.sql.ResultSet"%> 
<%@ page import = "java.sql.Statement"%> 
<%@ page import="com.cofar.util.*" %>

<%
    System.out.println("----------------------------------------INICIO EDITAR USUARIO---------------------------------");
    String mensaje="";
    Connection con=null;
    int COD_MODULO_BACO = 2;
    try
    {
        String codPersonal=request.getParameter("codPersonal");
        String codPerfil=request.getParameter("codPerfil");
        String codEstadoRegistro = request.getParameter("codEstadoRegistro");
        String[] codigosAlmacen = request.getParameter("codigosAlmacen").split(",");
        String[] accesosHabilitados = request.getParameter("accesosHabilitados").split(",");
        con=Util.openConnection(con);
        con.setAutoCommit(false);
        StringBuilder consulta=new StringBuilder("UPDATE USUARIOS_MODULOS");
                                consulta.append(" set COD_PERFIL = ").append(codPerfil)
                                            .append(" , COD_ESTADO_REGISTRO = ").append(codEstadoRegistro);
                               consulta.append(" WHERE COD_PERSONAL=").append(codPersonal);
                                        consulta.append(" and COD_MODULO=").append(COD_MODULO_BACO);
        System.out.println("consulta UPDATE usuario"+consulta.toString());
        PreparedStatement pst=con.prepareStatement(consulta.toString());
        if(pst.executeUpdate()>0)System.out.println("Se GUARDO LA EDICION DE USUARIO");
        
        consulta=new StringBuilder("DELETE USUARIOS_ACCESOS_MODULOS_BACO ");
                    consulta.append("where COD_PERSONAL=").append(codPersonal);
                    consulta.append(" and COD_MODULO=").append(COD_MODULO_BACO);
        System.out.println("consulta delete accesos "+consulta.toString());
        pst=con.prepareStatement(consulta.toString());
        if(pst.executeUpdate()>0)System.out.println("se elimino usuarios accesos modulos");
        
        consulta=new StringBuilder("INSERT INTO USUARIOS_ACCESOS_MODULOS_BACO(COD_PERSONAL, COD_MODULO, CODIGO_VENTANA,CODIGO_ESTADO_VENTANA, COD_PERFIL)");
                    consulta.append(" select ").append(codPersonal).append(" ,").append(COD_MODULO_BACO).append(",pava.COD_VENTANA,1,pava.COD_PERFIL");
                    consulta.append(" from PERFIL_ACCESO_VENTANA_BACO pava");
                    consulta.append(" where pava.COD_PERFIL=").append(codPerfil);
        System.out.println("consulta registrar accesos "+consulta.toString());
        pst = con.prepareStatement(consulta.toString());
        if(pst.executeUpdate() > 0)System.out.println("se registro la ventana");
        
        consulta = new StringBuilder("delete ALMACEN_HABILITADO_USUARIO")
                            .append(" where COD_PERSONAL=").append(codPersonal);
        System.out.println("consulta eliminar almacen habilitado "+consulta.toString());
        pst = con.prepareStatement(consulta.toString());
        if(pst.executeUpdate() > 0)System.out.println("se elimino almacen habilitado usuario");
        
        consulta = new StringBuilder("INSERT INTO ALMACEN_HABILITADO_USUARIO(COD_PERSONAL, COD_ALMACEN, COD_ESTADO")
                                    .append(", ADMINISTRADOR_ALMACEN, PREDETERMINADO)")
                            .append(" VALUES (")
                                    .append(codPersonal).append(",")
                                    .append("?,")//cod almacen
                                    .append("1,")//estado
                                    .append("?,")//administrador
                                    .append("0")//predeterminado
                            .append(")");
        System.out.println("consulta almacen habilitado "+consulta.toString());
        pst = con.prepareStatement(consulta.toString());
        for(int i =0 ; i < codigosAlmacen.length && codigosAlmacen.length > 1 ; i+=2){
            pst.setString(1,codigosAlmacen[i]);
            pst.setString(2,codigosAlmacen[i+1]);
            if(pst.executeUpdate() > 0)System.out.println("se registro el almacen");
        }
        
        consulta = new StringBuilder("DELETE CONFIGURACION_PERMISO_ESPECIAL_BACO")
                        .append(" where COD_PERSONAL=").append(codPersonal);
        System.out.println("consulta eliminar permisos si existieran : "+consulta.toString());
        pst = con.prepareStatement(consulta.toString());
        if(pst.executeUpdate() > 0)System.out.println("Se eliminaron anteriores accesos");
        
        consulta = new StringBuilder("INSERT INTO CONFIGURACION_PERMISO_ESPECIAL_BACO(COD_ALMACEN,COD_TIPO_PERMISO_ESPECIAL_BACO, COD_PERSONAL)")
                            .append(" VALUES (")
                                    .append("?,")//cod almacen
                                    .append("?,")//cod tipo permiso especial
                                    .append(codPersonal)
                            .append(")");
        System.out.println("consulta registrar permiso especial :"+consulta.toString());
        pst = con.prepareStatement(consulta.toString());
        for(int i = 0 ;i < accesosHabilitados.length && accesosHabilitados.length > 1 ; i+=2){
            pst.setString(1,accesosHabilitados[i]);System.out.println("p1: "+accesosHabilitados[i]);
            pst.setString(2,accesosHabilitados[i+1]);System.out.println("p1: "+accesosHabilitados[i+1]);
            if(pst.executeUpdate() > 0)System.out.println("se registro configuracion permiso");
        }
        con.commit();
        mensaje="1";
 
    }
    catch(SQLException ex) 
    {
        ex.printStackTrace();
        mensaje="Ocurrio un error al momento de registrar el perfil";
        con.rollback();
    }
    catch(Exception ex) 
    {
        ex.printStackTrace();
        mensaje="Ocurrio un error al momento de registrar el perfil";
        con.rollback();
    }
    finally
    {
        con.close();
    }
    out.clear();
    out.println(mensaje);
    System.out.println("----------------------------------------TERMINO EDITAR USUARIO---------------------------------");
%>


