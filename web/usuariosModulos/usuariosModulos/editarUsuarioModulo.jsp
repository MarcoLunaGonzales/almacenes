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
        <link rel="STYLESHEET" type="text/css" href="../../css/ventas.css" />
        <link rel="STYLESHEET" type="text/css" href="../../css/chosen.css" />
        <script src="../../js/general.js"></script>
        <script type="text/javascript">
            onerror=function(){
                alert('Ocurrio un error, intente de nuevo');
                window.location.reload();
            };
            function editarUsuarioModulo(codPersonal)
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
                for(var i =0 ; i<tablaAlmacenes.rows.length ; i+=2){
                    if(tablaAlmacenes.rows[i].cells[0].getElementsByTagName("input")[0].checked){
                        codigosAlmacen.push(tablaAlmacenes.rows[i].cells[0].getElementsByTagName("input")[0].value);
                        codigosAlmacen.push(tablaAlmacenes.rows[i].cells[1].getElementsByTagName("input")[0].checked ? 1 : 0);
                    }
                }
                ajax=creaAjax();
                ajax.open("POST","ajaxGuardarEdicionUsuarioModulo.jsf?codPerfil="+document.getElementById("codPerfil").value+
                                "&codEstadoRegistro ="+document.getElementById("codEstadoRegistro").value+
                                "&codPersonal="+codPersonal+
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
    <body>
    <center>
        <h3>Editar Usuario</h3>
        
        <form method="post" action="" name="upform"  >
            <div align="center">
                <table border="0" style="border:solid #cccccc 1px"  class="tablaFiltroReporte"
                       align="center" width="50%" cellpadding="0" cellspacing="0">
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
                            <td class="outputText2">
                                
                                <%
                                String codPersonal=request.getParameter("codPersonal");
                                Connection con=null;
                                try
                                {
                                    con=Util.openConnection(con);
                                    StringBuilder consulta=new StringBuilder("select p.AP_PATERNO_PERSONAL+' '+p.AP_MATERNO_PERSONAL+' '+p.NOMBRES_PERSONAL+' '+p.nombre2_personal as nombrePersonal,");
                                                                    consulta.append(" um.NOMBRE_USUARIO,um.CONTRASENA_USUARIO,uam.COD_PERFIL")
                                                                            .append(" ,um.COD_ESTADO_REGISTRO");
                                                            consulta.append(" from USUARIOS_MODULOS um");
                                                                    consulta.append(" inner join personal p on p.COD_PERSONAL=um.COD_PERSONAL");
                                                                    consulta.append(" inner join USUARIOS_ACCESOS_MODULOS_BACO uam on uam.COD_PERSONAL=um.COD_PERSONAL");
                                                                    consulta.append(" and um.COD_MODULO=uam.COD_MODULO");
                                                            consulta.append(" where um.COD_PERSONAL=").append(codPersonal);
                                                                    consulta.append(" and um.COD_MODULO=2");
                                    System.out.println("consulta usuario"+consulta.toString());
                                    Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                                    ResultSet res=st.executeQuery(consulta.toString());
                                    res.next();
                                    out.println(res.getString("nombrePersonal"));
                                    int codPerfil=res.getInt("COD_PERFIL");
                                    int codEstadoRegistro = res.getInt("COD_ESTADO_REGISTRO");
                                %>
                            </td>
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
                                <script>
                                    document.getElementById("codPerfil").value=<%=(codPerfil)%>;
                                </script>
                            </td>
                                
                        </tr>
                         <tr>
                            <td class="outputTextBold">Estado</td>
                            <td class="outputTextBold">::</td>
                            <td class="outputText2">
                                <select id="codEstadoRegistro">
                                    <option value="1">Activo</option>
                                    <option value="2">No Activo</option>
                                </select>
                                <script>
                                    document.getElementById("codEstadoRegistro").value=<%=(codEstadoRegistro)%>;
                                </script>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="3">
                                <table id="tablaAlmacenes" class="tablaFiltroReporte"
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
                                            consulta = new StringBuilder("select a.COD_ALMACEN,a.NOMBRE_ALMACEN,isnull(ahu.COD_ALMACEN,0) as administrador,ahu.ADMINISTRADOR_ALMACEN")
                                                                .append(" from ALMACENES a")
                                                                        .append(" left outer join ALMACEN_HABILITADO_USUARIO ahu on ahu.COD_ALMACEN=a.COD_ALMACEN")
                                                                                .append(" and ahu.COD_PERSONAL = ").append(codPersonal)
                                                                .append(" where a.COD_ESTADO_REGISTRO = 1")
                                                                .append(" order by a.NOMBRE_ALMACEN");
                                            System.out.println("consulta almacenes habilitados "+consulta.toString());
                                            res = st.executeQuery(consulta.toString());
                                            while(res.next()){
                                                out.println("<tr>");
                                                    out.println("<td><input type='checkbox' onchange='clickAlmacen(this);' value='"+res.getInt("COD_ALMACEN")+"' "+(res.getInt("administrador") > 0 ? "checked" : "")+"/></td>");
                                                    out.println("<td><input type='checkbox' onchange='clickAdministrador(this)' "+(res.getInt("ADMINISTRADOR_ALMACEN") > 0 ? "checked" : "")+"/></td>");
                                                    out.println("<td><span class='outputText2'>"+res.getString("NOMBRE_ALMACEN")+"</span></td>");
                                                out.println("</tr>");
                                                %>
                                                <tr>
                                                    <td colspan="3">
                                                        <table class="tablaFiltroReporte" id="tablaPermisos<%=(res.getInt("COD_ALMACEN"))%>" style="width:100%;<%=(res.getInt("administrador") > 0 ? "" : "display:none")%>">
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
                                                                                                                        .append(",isnull(cpeb.COD_ALMACEN,0) as habilitado")
                                                                                                                .append(" from TIPO_PERMISO_ESPECIAL_BACO tpeb")
                                                                                                                        .append(" left outer join CONFIGURACION_PERMISO_ESPECIAL_BACO cpeb on cpeb.COD_TIPO_PERMISO_ESPECIAL_BACO= tpeb.COD_TIPO_PERMISO_ESPECIAL_BACO")
                                                                                                                                .append(" and cpeb.COD_PERSONAL = ").append(codPersonal)
                                                                                                                                .append(" and cpeb.COD_ALMACEN = ").append(res.getInt("COD_ALMACEN"))
                                                                                                                .append(" order by case when cpeb.COD_ALMACEN > 0 then 1 else 2 end,tpeb.NOMBRE_TIPO_PERMISO_ESPECIAL_BACO");
                                                                    System.out.println("consulta permisos especiales usuario : "+consultaAccesos.toString());
                                                                    Statement stAcceso = con.createStatement();
                                                                    ResultSet resAcceso = stAcceso.executeQuery(consultaAccesos.toString());
                                                                    while(resAcceso.next()){
                                                                        out.println("<tr>");
                                                                            out.println("<td class='tdCenter'>");
                                                                                    out.println("<input type='checkbox' name='checkAccesoEspecial' "+(resAcceso.getInt("habilitado") > 0 ? "checked" : "" )+" value='"+res.getInt("COD_ALMACEN")+","+resAcceso.getInt("COD_TIPO_PERMISO_ESPECIAL_BACO")+"' id='check"+res.getInt("COD_ALMACEN")+"+"+resAcceso.getInt("COD_TIPO_PERMISO_ESPECIAL_BACO")+"'/>");
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
            <br>
                <a class="btn" onclick="editarUsuarioModulo(<%=(codPersonal)%>)">Guardar</a>
                <a class="btn" onclick="window.location.href='navegadorUsuariosModulos.jsf?cancel='+(new Date()).getTime().toString()">Cancelar</a>
            </center>
            <script src="../../js/chosen.js"></script>
        </form>
        
    </body>
</html>