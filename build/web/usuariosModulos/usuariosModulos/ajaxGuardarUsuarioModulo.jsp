<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page language="java" %>
<%@ page import = "java.sql.*"%>
<%@ page import = "java.sql.DriverManager"%> 
<%@ page import = "java.sql.ResultSet"%> 
<%@ page import = "java.sql.Statement"%> 
<%@ page import="com.cofar.util.*" %>

<%
    System.out.println("----------------------------------------INICIO REGISTRAR NUEVO USUARIO---------------------------------");
    String mensaje="";
    Connection con=null;
    int COD_MODULO_ALMACENES = 2;
    try
    {
        String codPersonal=request.getParameter("codPersonal");
        String nombreUsuario=request.getParameter("nombreUsuario");
        boolean usuarioExistente = request.getParameter("usuarioExistente").equals("1");
        String codPerfil=request.getParameter("codPerfil");
        String[] codigosAlmacen = request.getParameter("codigosAlmacen").split(",");
        String[] accesosHabilitados = request.getParameter("accesosHabilitados").split(",");
        System.out.println(codigosAlmacen);
        con=Util.openConnection(con);
        con.setAutoCommit(false);
        StringBuilder consulta=new StringBuilder("INSERT INTO USUARIOS_MODULOS(COD_PERSONAL, COD_MODULO, NOMBRE_USUARIO,");
                                consulta.append(" CONTRASENA_USUARIO, FECHA_VENCIMIENTO,COD_ESTADO_REGISTRO,COD_PERFIL)");
                                if(usuarioExistente){
                                    consulta.append("select top 1 um.COD_PERSONAL")
                                                    .append(",").append(COD_MODULO_ALMACENES)
                                                    .append(",um.NOMBRE_USUARIO")
                                                    .append(",um.CONTRASENA_USUARIO")
                                                    .append(",um.FECHA_VENCIMIENTO")
                                                    .append(",1")
                                                    .append(",").append(codPerfil)
                                            .append(" from USUARIOS_MODULOS um")
                                            .append(" where um.COD_PERSONAL = ").append(codPersonal);
                                }
                                else{
                                    consulta.append("select top 1 p.COD_PERSONAL")
                                                    .append(",").append(COD_MODULO_ALMACENES)
                                                    .append(",?")//nombre usuario
                                                    .append(",LOWER(convert(varchar(max),HashBytes('MD5',p.CI_PERSONAL),2))")
                                                    .append(",DATEADD(day,7,getdate())")
                                                    .append(",1")
                                                    .append(",").append(codPerfil)
                                            .append(" from personal p")
                                            .append(" where p.COD_PERSONAL =").append(codPersonal);
                                }
        System.out.println("consulta registrar usuarios"+consulta.toString());
        PreparedStatement pst=con.prepareStatement(consulta.toString());
        if(!usuarioExistente)
        {
            pst.setString(1,nombreUsuario);System.out.println("p1:"+nombreUsuario);
        }
        if(pst.executeUpdate()>0)System.out.println("Se registro el usuarios");
        
        consulta=new StringBuilder("INSERT INTO USUARIOS_ACCESOS_MODULOS_BACO(COD_PERSONAL, COD_MODULO, CODIGO_VENTANA,CODIGO_ESTADO_VENTANA, COD_PERFIL)");
                    consulta.append(" select ").append(codPersonal).append(" ,2,pava.COD_VENTANA,1,pava.COD_PERFIL");
                    consulta.append(" from PERFIL_ACCESO_VENTANA_BACO pava");
                    consulta.append(" where pava.COD_PERFIL=").append(codPerfil);
        System.out.println("consulta registrar accesos "+consulta.toString());
        pst=con.prepareStatement(consulta.toString());
        if(pst.executeUpdate()>0)System.out.println("se registro la ventana");
        consulta = new StringBuilder("INSERT INTO dbo.ALMACEN_HABILITADO_USUARIO(COD_PERSONAL, COD_ALMACEN, COD_ESTADO")
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
        for(int i = 0 ;i < accesosHabilitados.length && accesosHabilitados.length > 1; i+=2){
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
        mensaje="Ocurrio un error al momento de registrar el USUARIO";
        con.rollback();
    }
    catch(Exception ex) 
    {
        ex.printStackTrace();
        mensaje="Ocurrio un error al momento de registrar el USUARIO";
        con.rollback();
    }
    finally
    {
        con.close();
    }
    System.out.println("----------------------------------------FINAL REGISTRAR NUEVO USUARIO---------------------------------");
    out.clear();
    out.println(mensaje);
    
%>


