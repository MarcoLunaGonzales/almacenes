<%@page contentType="text/html; charset=ISO-8859-1"%>
<%@page pageEncoding="ISO-8859-1"%>
<%@ page language="java"%>
<%@ page import = "java.sql.*"%>
<%@ page import = "java.sql.DriverManager"%> 
<%@ page import = "java.sql.ResultSet"%> 
<%@ page import = "java.sql.Statement"%> 
<%@ page import="com.cofar.util.*" %>
<html>
    <head>
        <meta http-equiv='Expires' content='0'>
        <meta http-equiv='Last-Modified' content='0'>
        <meta http-equiv='Cache-Control' content='no-cache, mustrevalidate'>
        <meta http-equiv='Pragma' content='no-cache'>

        <link rel="STYLESHEET" type="text/css" href="../../css/ventas.css" />
        <link rel="STYLESHEET" type="text/css" href="../../css/chosen.css" />
        <script src="../../js/general.js"></script>
        <style type="text/css">
            .coincidencia{
                background-color: #faffb1;
                border: 1px solid #f9e093;
            }
        </style>
        <script type="text/javascript">
            onerror=function(){
                alert('Ocurrio un error, intente de nuevo');
                window.location.reload();
            }
            function registrarUsuarioModulo()
            {
                var accesosEspeciales = document.getElementsByName("checkAccesoEspecial");
                var accesosHabilitados = new Array();
                for(var i = 0; i <accesosEspeciales.length ; i++){
                    if(accesosEspeciales[i].checked){
                        accesosHabilitados.push(accesosEspeciales[i].value);
                    }
                }
                var tablaAlmacenes = document.getElementById("tablaAlmacenes").getElementsByTagName("tbody")[0];
                var codigosAlmacen =  new Array();
                for(var i =0 ;i<tablaAlmacenes.rows.length;i+=2){
                    if(tablaAlmacenes.rows[i].cells[0].getElementsByTagName("input")[0].checked){
                        codigosAlmacen.push(tablaAlmacenes.rows[i].cells[0].getElementsByTagName("input")[0].value);
                        codigosAlmacen.push(tablaAlmacenes.rows[i].cells[1].getElementsByTagName("input")[0].checked ? 1 : 0);
                    }
                }
                ajax = creaAjax();
                ajax.open("POST" , "ajaxGuardarUsuarioModulo.jsf?nombreUsuario="+encodeURIComponent(document.getElementById("nombreUsuario").value)+
                                "&codPersonal="+document.getElementById("codPersonal").value+
                                "&codPerfil="+document.getElementById("codPerfil").value+
                                "&usuarioExistente="+document.getElementById("usuarioExistente").value+
                                "&codigosAlmacen="+codigosAlmacen+
                                "&a="+(new Date()).getTime().toString(),true);
                ajax.setRequestHeader('content-type', 'application/x-www-form-urlencoded;charset=UTF-8');
                ajax.onreadystatechange=function(){
                    if (ajax.readyState==4) {
                        if(ajax.responseText==null || ajax.responseText=='')
                        {
                            alert('No se puede conectar con el servidor, verfique su conexión a internet');
                            return false;
                        }
                        if(parseInt(ajax.responseText.split("\n").join(""))=='1')
                        {
                            alert('Se registro el usuario');
                            window.location.href='navegadorUsuariosModulos.jsf?confirm='+(new Date()).getTime().toString();
                            return true;
                        }
                        else
                        {
                            alert("Ocurrio un error al momento de registrar el usuario "+ajax.responseText.split("\n").join(""));
                            return false;
                        }

                    }
                };
                ajax.send("accesosHabilitados="+accesosHabilitados.toString());
            }
            function verificarUsuario(){
                var codPersonal = document.getElementById("codPersonal").value;
                ajax=creaAjax();
                ajax.open("GET" ,  "ajaxVerificarUsuarioCreado.jsf?codPersonal="+codPersonal+
                                    "&a="+(new Date()).getTime().toString(),true);
                ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                ajax.onreadystatechange=function(){
                    if (ajax.readyState==4) {
                        eval("var datosUsuario={"+ajax.responseText+"}");
                        document.getElementById("divNombreUsuario").innerHTML = datosUsuario.nombreUsuario;
                        document.getElementById("nombreUsuario").value = datosUsuario.nombreUsuario;
                        document.getElementById("usuarioExistente").value = datosUsuario.usuarioExistente;
                        document.getElementById("divContraseniaUsuario").innerHTML =datosUsuario.contrasenia;
                        desBloquearPantalla();
                    }
                };
                ajax.send(null);
            }
            var INDEX_CELDA_CHECK_HABILITADO = 0;
            var INDEX_CELDA_CHECK_ADMINISTRADOR = 1;
            function clickAdministrador(input){
                var filaregistro = input.parentNode.parentNode;
                if(input.checked == true){
                    filaregistro.cells[INDEX_CELDA_CHECK_HABILITADO].getElementsByTagName('input')[0].checked = true;
                }
                clickAlmacen(input);
            }
            function clickAlmacen(input){
                var filaregistro = input.parentNode.parentNode;
                var estiloDisplayA = ''
                if(filaregistro.cells[INDEX_CELDA_CHECK_HABILITADO].getElementsByTagName("input")[0].checked == true
                        || filaregistro.cells[INDEX_CELDA_CHECK_ADMINISTRADOR].getElementsByTagName("input")[0].checked == true){
                    estiloDisplayA ='';
                }
                else{
                    estiloDisplayA ='none';
                }
                document.getElementById("tablaPermisos"+filaregistro.cells[INDEX_CELDA_CHECK_HABILITADO].getElementsByTagName("input")[0].value).style.display = estiloDisplayA;
            }
            function buscarCeldaAgregar(input,fila){
                var tablaBuscar = input.parentNode.parentNode.parentNode.parentNode.getElementsByTagName("tbody")[0];
                var textoBuscar= input.value.toLowerCase();
                var encontrado=false;
                for (var i = 0 ; i < tablaBuscar.rows.length; i++)
                {
                    encontrado=false;
                    var label =tablaBuscar.rows[i].cells[fila].getElementsByTagName('label')[0];
                    label.innerHTML = label.innerHTML.replace('<b class="coincidencia">',"").replace("</b>","");
                    if ((tablaBuscar.rows[i].cells[0].getElementsByTagName("input")[0]!=null
                            && tablaBuscar.rows[i].cells[0].getElementsByTagName("input")[0].checked)
                            ||textoBuscar.length==0 || (label.innerHTML.toLowerCase().indexOf(textoBuscar) > -1))
                    {
                        label.innerHTML = label.innerHTML.toLowerCase().replace(textoBuscar,'<b class="coincidencia">'+textoBuscar+'</b>');
                        encontrado= true;
                    }

                    if(encontrado)
                    {
                        tablaBuscar.rows[i].style.display = '';
                    } else {
                        tablaBuscar.rows[i].style.display = 'none';
                    }
                }
            }
        </script>
    </head>
    <body onload="verificarUsuario()">
        <center>
        <h3>Registrar Usuario</h3>
        <form method="post" action="" name="upform" >
            <div align="center">
                <table border="0" style="border:solid #cccccc 1px"  class="tablaFiltroReporte" align="center" width="50%" cellpadding="0" cellspacing="0">
                    <thead>
                        <tr>
                            <td  colspan="3" >
                                Introduzca Datos
                            </td>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td class="outputTextBold">Personal</td>
                            <td class="outputTextBold">::</td>
                            <td>
                                <select class="inputText chosen" id="codPersonal"onchange='verificarUsuario()'>
                                    <option value="0" disabled="true">--Seleccione una opción--</option>
                                <%
                                Connection con=null;
                                try
                                {
                                    con=Util.openConnection(con);
                                    StringBuilder consulta=new StringBuilder("select p.COD_PERSONAL,p.AP_PATERNO_PERSONAL+' '+p.AP_MATERNO_PERSONAL+' '+p.NOMBRES_PERSONAL+' '+isnull(p.nombre2_personal,'') as nombrePersonal");
                                                            consulta.append(" from PERSONAL p");
                                                            consulta.append(" where p.cod_estado_persona = 1");
                                                            consulta.append(" and p.cod_personal not in(");
                                                                                        consulta.append(" select um.cod_personal");
                                                                                        consulta.append(" from USUARIOS_MODULOS um");
                                                                                        consulta.append(" where um.cod_modulo = 2");
                                                                  consulta.append(" )");
                                                            consulta.append(" order by 2");
                                    System.out.println("consulta personal sin usuario "+consulta.toString());
                                    Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                                    ResultSet res=st.executeQuery(consulta.toString());
                                    while(res.next())
                                    {
                                        out.println("<option value='"+res.getInt("COD_PERSONAL")+"'>"+res.getString("nombrePersonal")+"</option>");
                                    }
                                %>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td class="outputTextBold">Usuario</td>
                            <td class="outputTextBold">::</td>
                            <td><div id="divNombreUsuario"></div>
                                <input name="nombreUsuario" id="nombreUsuario" type="hidden"/>
                                <input name="usuarioExistente" id="usuarioExistente" type="hidden"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="outputTextBold">Contraseña</td>
                            <td class="outputTextBold">::</td>
                            <td><div id="divContraseniaUsuario"></div></td>
                        </tr>
                        <tr>
                            <td class="outputTextBold">Perfil</td>
                            <td class="outputTextBold">::</td>
                            <td>
                                <select id="codPerfil" class="inputText chosen">
                                    <%
                                        consulta=new StringBuilder(" select cod_perfil,nombre_perfil");
                                                    consulta.append(" from PERFILES_USUARIOS_BACO");
                                                    consulta.append(" where cod_estado_registro=1");
                                                    consulta.append(" order by nombre_perfil");
                                        System.out.println("consulta perfiles "+consulta.toString());
                                        res=st.executeQuery(consulta.toString());
                                        while(res.next())
                                        {
                                            out.println("<option value='"+res.getInt("cod_perfil")+"'>"+res.getString("nombre_perfil")+"</option>");
                                        }

                                    %>
                                </select>
                            </td>

                        </tr>
                        <tr>
                            <td colspan="3">
                                <table class="tablaFiltroReporte" id="tablaAlmacenes" cellpading="0" cellspacing="0"
                                       style="width:100%">
                                    <thead>
                                        <tr>
                                            <td>Habilitado</td>
                                            <td>Administrador</td>
                                            <td>Almacen</td>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <%
                                        consulta = new StringBuilder("select a.COD_ALMACEN,a.NOMBRE_ALMACEN")
                                                                .append(" from almacenes a ")
                                                                .append(" where a.COD_ESTADO_REGISTRO=1")
                                                                .append(" order by a.NOMBRE_ALMACEN");
                                        res=st.executeQuery(consulta.toString());
                                        while(res.next()){
                                            out.println("<tr>");
                                                out.println("<td><input type='checkbox' onchange='clickAlmacen(this);' value='"+res.getInt("COD_ALMACEN")+"'/></td>");
                                                out.println("<td><input type='checkbox' onchange='clickAdministrador(this)'/></td>");
                                                out.println("<td><span class='outputText2'>"+res.getString("NOMBRE_ALMACEN")+"</span></td>");
                                            out.println("</tr>");
                                            %>
                                            <tr>
                                                <td colspan="4">
                                                    <table class="tablaFiltroReporte" id="tablaPermisos<%=(res.getInt("COD_ALMACEN"))%>" style="width:100%;display:none">
                                                        <thead>
                                                            <tr>
                                                                <td>Habilitado</td>
                                                                <td>Permiso Especial<br/>
                                                                    <input type='text' onkeyup='buscarCeldaAgregar(this,1)' class='inputText'/>
                                                                </td>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <%
                                                                StringBuilder consultaAccesos = new StringBuilder("select tpeb.COD_TIPO_PERMISO_ESPECIAL_BACO,tpeb.NOMBRE_TIPO_PERMISO_ESPECIAL_BACO")
                                                                                                            .append(" from TIPO_PERMISO_ESPECIAL_BACO tpeb")
                                                                                                            .append(" order by tpeb.NOMBRE_TIPO_PERMISO_ESPECIAL_BACO");
                                                                Statement stAcceso = con.createStatement();
                                                                ResultSet resAcceso = stAcceso.executeQuery(consultaAccesos.toString());
                                                                while(resAcceso.next()){
                                                                    out.println("<tr>");
                                                                        out.println("<td class='tdCenter'>");
                                                                                out.println("<input type='checkbox' name='checkAccesoEspecial' value='"+res.getInt("COD_ALMACEN")+","+resAcceso.getInt("COD_TIPO_PERMISO_ESPECIAL_BACO")+"' id='check"+res.getInt("COD_ALMACEN")+"+"+resAcceso.getInt("COD_TIPO_PERMISO_ESPECIAL_BACO")+"'/>");
                                                                        out.println("</td>");
                                                                        out.println("<td><label for='check"+res.getInt("COD_ALMACEN")+"+"+resAcceso.getInt("COD_TIPO_PERMISO_ESPECIAL_BACO")+"'>"+resAcceso.getString("NOMBRE_TIPO_PERMISO_ESPECIAL_BACO")+"</label></td>");
                                                                    out.println("</tr>");
                                                                }
                                                            %>
                                                        </tbody>
                                                    </table>
                                                </td>
                                            </tr>
                                            <%
                                        }
                                    %>
                                    </tbody>
                                </table>
                            </td>
                        </tr>

                        <%  

                            } catch(Exception e) 
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

            </div>
            <br/>
                <a class="btn" onclick="registrarUsuarioModulo()">Guardar</a>
                <a class="btn" onclick="window.location.href='navegadorUsuariosModulos.jsf?cancel='+(new Date()).getTime().toString()">Cancelar</a>
            </center>
            <script src="../../js/chosen.js"></script>
        </form>
        
    </body>
</html>