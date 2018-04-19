<%@page contentType="text/html; charset=ISO-8859-1"%>
<%@page pageEncoding="ISO-8859-1"%>
<%@ page language="java"%>
<%@ page import = "java.sql.*"%>
<%@ page import = "java.sql.DriverManager"%> 
<%@ page import = "java.sql.ResultSet"%> 
<%@ page import = "java.sql.Statement"%> 
<%@ page import="com.cofar.util.*" %>
<%@ page import="com.cofar.web.ManagedAccesoSistema"%>




<html>
    
    <head>
        <link rel="STYLESHEET" type="text/css" href="../css/ventas.css" />
        <script src="../js/general.js"></script>
        <script>
               function cancelar(){
                  // alert(codigo);
                   location='../usuarios/navegador_usuarios.jsf';
                }
                function editar(f){
                    var formulario=document.getElementById('upform');
                    var data=document.getElementById('codigo');
                    var i;
                    var j=0;
                    
                    codigo=new Array();
                    for(i=0;i<=f.length-1;i++)
                    {
                	if(f.elements[i].type=='checkbox')
                        {	if(f.elements[i].checked==true)
                                {	codigo[j]=f.elements[i].value;
                                	j=j+1;
                                }
                        }
                    }
                    if(j==0)
                    {	alert('Debe seleccionar almenos un Registro para ser Registrado.');
                    }
                    else
                    {   data.value=codigo;
                        //location="guardar_modificar_usuarios.jsf?codigo="+codigo+"&personal="+f.personal.value+"&usuario="+f.usuario.value+"&contrasena="+f.contrasena.value;
                         formulario.submit();   
                    	
                        
                       
                    }
                }      
    function areas(codigo){
            
        var ajax=creaAjax();
        var url='../personal_jsp/areaempresaajax.jsp?codigo='+codigo;
        url+='&pq='+(Math.random()*1000);
        ajax.open ('GET', url, true);
        ajax.onreadystatechange = function() {
        //alert(ajax.readyState);           
           if (ajax.readyState==1) {
 
           }else if(ajax.readyState==4){
                    if(ajax.status==200){
                      //alert(ajax.responseText);
                        var maindiv=document.getElementById('maindiv');
                       maindiv.innerHTML=ajax.responseText;
                      
                      
                        

                    }
          }
        }
        ajax.send(null);
    }
            var password=null;
            function guardar_modificaciones(f)
            {
                if(password==f.contrasenaAnt.value)
                    {

                        if(f.contrasena.value==f.contrasenaNueva.value)
                            {
                            if(password != f.contrasena.value)
                                {
                                if(validar_clave(f.contrasena.value))
                                    {
                                        f.submit();
                                    }
                                }
                                else
                                    {
                                        alert('Tiene que cambiar la contraseña');
                                    }
                            }
                            else
                                {
                                    alert('Las contraseñas no coinciden');
                                }
                    }
                    else
                        {
                            alert('La contaseña introducida es incorrecta');
                        }
            }
           function validar_clave(clave){
                            if(clave.length < 8){
                                           alert("La clave debe tener al menos 8 caracteres");
                                           return false;
                            }

                            if (clave.search('[a-z]')<0){
                                           alert("La clave debe tener al menos una letra minúscula");
                                           return false;
                            }
                            if (clave.search('[A-Z]')<0){
                                           alert("La clave debe tener al menos una letra mayúscula");
                                           return false;
                            }
                            var index=clave.search('[0-9]');
                            if(index<0)
                            {
                                alert("La clave debe tener al menos dos caracteres numericos");
                                return false;
                            }
                            else
                            {
                                var cadena=clave.substring(index+1,clave.length);
                                if(cadena.search('[0-9]')<0)
                                    {
                                        alert("La clave debe tener al menos dos caracteres numericos");
                                        return false;
                                    }
                            }
                            if (clave.search('[-_$@...]')<0){
                                           alert("La clave debe tener al menos un caracter especial");
                                           return false;
                            }
                            return true;
            }
            function guardarContra()
            {
                password=document.getElementById('contraAnterior').value;
                
            }
        /*}*/
        </script>
    </head>
    <body onload="guardarContra()">
        
        <%! 
        Connection con=null;
        String codVentanaHijo="";
        String generaArbol[]=new String[1000];
        int k=0;
        String codigo="";
        %>
        <%
        con=Util.openConnection(con);
        %>
        
        
        <br><br>
        <h3 align="center">Modificar Usuario Contraseña</h3>
        
        <form method="post" action="guardar_modificar_usuario_contrasena.jsp" name="upform"  id="upform">
            <div align="center">
                
                    <%
                    
                    codigo="";
                    codigo=request.getParameter("codigo");
                    Object obj=request.getSession().getAttribute("ManagedAccesoSistema");
                    ManagedAccesoSistema var=(ManagedAccesoSistema)obj;
                    String codigoUsuario=var.getUsuarioModuloBean().getCodUsuarioGlobal();
                    System.out.println("codigoUsuario:"+ codigoUsuario);
                    try{
                        String sql_aux=" select p.ap_paterno_personal,p.ap_materno_personal,p.nombres_personal," +
                                " um.nombre_usuario,um.contrasena_usuario" +
                                " from usuarios_modulos um,personal p"+
                                " where um.cod_personal=p.cod_personal and p.cod_personal='"+codigoUsuario+"'" +
                                " and um.cod_modulo=2";
                        
                        System.out.println("sql_aux:.........."+sql_aux);
                        Statement st_aux = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                        ResultSet rs_aux = st_aux.executeQuery(sql_aux);
                        String paterno="";
                        while (rs_aux.next()){
                            paterno=rs_aux.getString("ap_paterno_personal");
                            String materno=rs_aux.getString("ap_materno_personal");
                            String nombres=rs_aux.getString("nombres_personal");
                            String nombreUsuario=rs_aux.getString("nombre_usuario");
                            String contrasenaUsuario=rs_aux.getString("contrasena_usuario");
                    
                    %>
                    <input type="hidden" value="<%=contrasenaUsuario%>" id="contraAnterior" name="contraAnterior">
                    <input type="hidden" value="<%=nombreUsuario%>" id="usuario" name="usuario">
                        <table border="0" style="border:solid #cccccc 1px"  class="outputText2" align="center" width="33%">
                    <tr class="headerClassACliente">
                        <td  colspan="3" >
                            <div class="outputText2" align="center">
                                Introduzca Datos
                            </div>
                        </td>

                    </tr>
                    <tr>
                        <td>Nombre</td>
                        <td>::</td>
                        <td >
                            <input id="persona" name="persona" disabled="true" value="<%=paterno%> <%=materno%> <%=nombres%>" type=text class="inputText" size="35" style='text-transform:uppercase;'>
                        </td>
                    </tr>
                    <tr>
                        <td>Usuario</td>
                        <td>::</td>
                        <td >
                           <span> <%=nombreUsuario%></span>
                        </td>
                    </tr>
                    <tr>
                        <td>Contraseña Anterior</td>
                        <td>::</td>
                        <td >
                            <input id="contrasenaAnt" name="contrasenaAnt" value="" type="password" class="inputText" size="35"  >
                        </td>
                    </tr>
                    <tr>
                        <td> Nueva Contraseña</td>
                        <td>::</td>
                        <td >
                            <input id="contrasena" name="contrasena" value="" type=password class="inputText" size="35"  >
                        </td>
                    </tr>
                    <tr>
                        <td>Repita la nueva Contraseña</td>
                        <td>::</td>
                        <td >
                            <input id="contrasenaNueva" name="contrasenaNueva" value="" type=password class="inputText" size="35"  >
                        </td>
                    </tr> 
                    
                    <input type="hidden" id="personal" value="<%=codigo%>" name="personal">
                    <%  
                        }
                    } catch(Exception e) {
                    }  
                    %>
                </table>
                
            </div>
            <br>
            
            <center>
                <input type="hidden" value="<%=codigoUsuario%>" id="codigo_personal" name="codigo_personal">     
                <input type="button"   class="btn" size="35" value="Guardar" name="reporte" onclick="guardar_modificaciones(this.form)">
                <%--input type="button"   class="btn"  size="35" value="Cancelar" name="limpiar" onclick="cancelar();"--%>
            </center>
        </form>
        
    </body>
</html>