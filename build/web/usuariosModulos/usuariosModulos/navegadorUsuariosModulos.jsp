<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page language="java" %>
<%@ page import = "java.sql.*"%>
<%@ page import = "java.sql.DriverManager"%> 
<%@ page import = "java.sql.ResultSet"%> 
<%@ page import = "java.sql.Statement"%> 
<%@ page import="com.cofar.util.*" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="STYLESHEET" type="text/css" href="../../css/ventas.css" />
        <script src="../../js/general.js"></script>
        <style type="text/css">
            td{
                border-bottom: 1px solid #bbb !important;
                border-right: 1px solid #bbb !important;
            }
            p{
                font-size: 8px;
            }
        </style>
        <script>
            function detalleAreas(codPersonal)
            {
                window.location.href='agregarEditarUsuariosAreaProduccion.jsf?codPersonal='+codPersonal+'&data='+(new Date()).getTime().toString();
                
            }
            function editarUsuarioModulo(codPersonal)
            {
                window.location.href='editarUsuarioModulo.jsf?codPersonal='+codPersonal+
                                      '&random='+(new Date()).getTime().toString();
                
            }
            function eliminarUsuarioModulo(codPersonal)
            {
                if(!confirm('Esta seguro de eliminar el usuario?'))
                    return false;
                ajax=creaAjax();
                ajax.open("GET","ajaxEliminarUsuarioModulo.jsf?codPersonal="+codPersonal+
                                            "&a="+(new Date()).getTime().toString(),true);
                ajax.onreadystatechange=function(){
                    if (ajax.readyState==4) {
                        if(ajax.responseText==null || ajax.responseText=='')
                        {
                            alert('No se puede conectar con el servidor, verfique su conexión a internet');
                            return false;
                        }
                        if(parseInt(ajax.responseText.split("\n").join(""))=='1')
                        {
                            alert('Se elimino el usuario');
                            window.location.reload();
                            return true;
                        }
                        else
                        {
                            alert("Ocurrio un error al momento de eliminar el usuario; "+ajax.responseText.split("\n").join(""));
                            return false;
                        }

                    }
                }
                ajax.send(null);

            }
                   
        </script>
    </head>
    <body>
        <form method="post" action="registrar_usuario_perfil.jsp" name="upform"  >
            <center>
            <h3>Listado de Usuarios BACO</h3>
            
            
            <table width="100%" align="center" class="tablaFiltroReporte" id="tablaUsuariosModulos" cellpadding="0" cellspacing="0">
                <thead>
                    <tr>
                        <td><b>Nombre</b></td>
                        <td><b>Cargo</b></td>
                        <td><b>Usuario</b></td>
                        <td><b>Perfil</b></td>
                        <td><b>Almacenes</b></td>
                        <td><b>Editar</b></td>
                    </tr>
                </thead>
                <tbody>
                
                <%
                    Connection con=null;
                    String codPerfil="";
                    int COD_MODULO_BACO = 2;
                try
                {
                    StringBuilder consulta=new StringBuilder("select distinct p.COD_PERSONAL,(p.ap_paterno_personal+' '+p.ap_materno_personal+' '+p.nombres_personal+' '+p.nombre2_personal) as nombrePersonal,");
                                                    consulta.append(" pua.NOMBRE_PERFIL,um.CONTRASENA_USUARIO,um.NOMBRE_USUARIO,c.DESCRIPCION_CARGO,pua.cod_perfil,");
                                                    consulta.append(" STUFF((select p= a.NOMBRE_ALMACEN+case when ah.ADMINISTRADOR_ALMACEN=1 then ' (ADMINISTRADOR)' else '' end ")
                                                            .append(" from ALMACEN_HABILITADO_USUARIO ah")
                                                            .append(" inner join ALMACENES a on a.COD_ALMACEN = ah.COD_ALMACEN")
                                                        .append(" where ah.COD_PERSONAL=uam.COD_PERSONAL")
                                                        .append(" FOR XML PATH('') ")
                                                    .append(" ),1,0,'') as nombreAlmacen");
                                             consulta.append(" from USUARIOS_ACCESOS_MODULOS_BACO uam ");
                                                     consulta.append(" inner join PERFILES_USUARIOS_BACO pua on pua.COD_PERFIL=uam.COD_PERFIL");
                                                 consulta.append(" inner join USUARIOS_MODULOS um on um.COD_PERSONAL=uam.COD_PERSONAL");
                                                        consulta.append(" and um.COD_MODULO=uam.COD_MODULO");
                                                 consulta.append(" inner join PERSONAL p on p.COD_PERSONAL=um.COD_PERSONAL");
                                                 consulta.append(" inner join CARGOS c on c.CODIGO_CARGO=p.CODIGO_CARGO");
                                             consulta.append(" where um.COD_MODULO=").append(COD_MODULO_BACO);
                                             consulta.append(" order by 2");
                    con=Util.openConnection(con);
                    System.out.println(consulta.toString());
                    Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                    ResultSet res=st.executeQuery(consulta.toString());
                    while(res.next())
                    {
                        out.println("<tr>");
                                out.println("<td class='outputText2'>"+res.getString("nombrePersonal")+"</td>");
                                out.println("<td class='outputText2'>"+res.getString("DESCRIPCION_CARGO")+"</td>");
                                out.println("<td class='outputText2'>"+res.getString("NOMBRE_USUARIO")+"</td>");
                                out.println("<td class='outputText2'>"+res.getString("NOMBRE_PERFIL")+"</td>");
                                out.println("<td class='outputText2'>"+res.getString("nombreAlmacen")+"</td>");
                                out.println("<td><a class='btn' onclick='editarUsuarioModulo("+res.getInt("COD_PERSONAL")+")'>Editar</a></td>");
                        out.println("</tr>");
                    }
                } 
                catch(Exception e) 
                {
                    e.printStackTrace();
                }
                finally
                {
                    con.close();
                }
                %>
                </tbody>
            </table>

            <br>
                <a class="btn" onclick="window.location.href='agregarUsuarioModulo.jsf?data='+(new Date()).getTime().toString()">Agregar</a>
            </center>
        </form>
    </body>
</html>
