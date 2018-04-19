<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page language="java" %>
<%@ page import="com.cofar.web.*" %>
<%@ page import = "java.sql.*"%>
<%@ page import = "java.sql.DriverManager"%> 
<%@ page import = "java.sql.ResultSet"%> 
<%@ page import = "java.sql.Statement"%> 
<%@ page import="com.cofar.util.*" %>
<%@ page language="java" import="java.util.*" %>
<%@ page import="java.text.SimpleDateFormat" %>

<%@ page import="java.util.Date" %>

<html>
    <head>
        <link rel="STYLESHEET" type="text/css" href="../../css/ventas.css" />
        <script src="../../js/general.js"></script>
        <style type="text/css">
            .filtrarFechaInicio{
                border:1px solid #ccc;
                width: 230px;
                position: relative;
                bottom: -5px;
                background-color: #ccc;
                left: 3px;
                font-weight: bold;
                border: 1px solid #aaa;
            }
        </style>
        <script type="text/javascript" >
            function onChangeAplicaFechaInicio(){
                document.getElementById("tablaFechaInicio").style.display = (document.getElementById("fitrarFechaInicio").checked ? "":"none");
            }
            function verReporte(f)
            {
                var estados=new Array();
                var cont=0;
                for(var i=0;i<f.codEstadoMaterial.options.length;i++)
                    {
                        if(f.codEstadoMaterial.options[i].selected)
                        {
                            estados[cont]=f.codEstadoMaterial.options[i].value;
                            cont++;
                        }
                    }
                    f.estados.value=estados;
                var almacenes=new Array();
                cont=0;
                for(var j=0;j<f.codAlmacen.options.length;j++)
                    {
                        if(f.codAlmacen.options[j].selected)
                        {
                            almacenes[cont]=f.codAlmacen.options[j].value;
                            cont++;
                        }
                    }
                 var codCapitulos=new Array();
                 for(var i=0;i<f.codCapitulo.options.length;i++)
                 {
                    if(f.codCapitulo.options[i].selected)
                    {
                        codCapitulos[codCapitulos.length]=f.codCapitulo.options[i].value;
                    }
                 }
                 f.capitulo.value=codCapitulos;
                 var codGrupos=new Array();
                for(var j=0;j<f.codGrupo.options.length;j++){
                    if(f.codGrupo.options[j].selected){
                       codGrupos[codGrupos.length]=f.codGrupo.options[j].value;
                    }
                }
                if(!f.fitrarFechaInicio.checked){
                    f.fechaInicio.value='';
                    f.fechaFinal.value='';
                }
               f.grupo.value=codGrupos;
               f.almacenes.value=almacenes;
                f.submit();
            }

            function nuevoAjax()
            {	var xmlhttp=false;
                try {
                    xmlhttp = new ActiveXObject("Msxml2.XMLHTTP");
                } catch (e) {
                    try {
                        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
                    } catch (E) {
                        xmlhttp = false;
                    }
                }
                if (!xmlhttp && typeof XMLHttpRequest!="undefined") {
                    xmlhttp = new XMLHttpRequest();
                }
                return xmlhttp;
            }
            /****************** AJAX DISTRITOS ********************/
            function ajaxGrupos(f)
            {
                ajax = creaAjax();
                var codCapitulo=new Array();
                for(var i=0;i<f.codCapitulo.options.length;i++)
                    {
                        if(f.codCapitulo.options[i].selected)
                        {
                            codCapitulo[codCapitulo.length]=f.codCapitulo.options[i].value;
                        }
                    }
                var a=Math.random();
                ajax.open("GET","../ajaxGrupo.jsf?codCapitulo="+codCapitulo+"&multiple=1&b="+a,true);
                ajax.onreadystatechange=function(){
                    if (ajax.readyState==4) {
                        document.getElementById("divGrupo").innerHTML=ajax.responseText;
                        desBloquearPantalla();
                    }
                }
                ajax.send(null)
            }
        </script>



    </head>
    <body onload="onChangeAplicaFechaInicio()">
        <h3 align="center">REPORTE INGRESOS MATERIAL POR ESTADO</h3>
        <form method="post" action="reporteIngresosAlamacenEstadoMaterial.jsp" target="_blank" name="form1">
            
            <div align="center">
                <table class="tablaFiltroReporte" width="50%">
                    <thead>
                        <tr>
                            <td  colspan="3" >Introduzca Datos</td>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td class="outputTextBold">Estado Material</td>
                            <td class="outputTextBold">::</td>
                           <td>
                               <select id="codEstadoMaterial" class="inputText" multiple>
                                    <%
                                    try
                                    {
                                        Connection con=null;
                                        con=Util.openConnection(con);
                                        Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                                        String consulta="select em.COD_ESTADO_MATERIAL,em.NOMBRE_ESTADO_MATERIAL from ESTADOS_MATERIAL em where em.COD_ESTADO_REGISTRO=1";
                                        ResultSet res=st.executeQuery(consulta);
                                        while(res.next())
                                        {
                                            out.println("<option value='"+res.getString("COD_ESTADO_MATERIAL")+"'>"+res.getString("NOMBRE_ESTADO_MATERIAL")+"</option>");
                                        }

                                    %>
                               </select>
                            </td>
                        </tr>
                        <tr >
                            <td class="outputTextBold">Almacen</td>
                            <td class="outputTextBold">::</td>
                           <td>
                               <select id="codAlmacen" class="inputText" multiple>
                                 <%
                                        consulta="select a.COD_ALMACEN,a.NOMBRE_ALMACEN"+
                                                        " from almacenes a where a.COD_ESTADO_REGISTRO=1 order by a.NOMBRE_ALMACEN";
                                        res=st.executeQuery(consulta);
                                        while(res.next())
                                        {
                                            out.println("<option value='"+res.getString("COD_ALMACEN")+"'>"+res.getString("NOMBRE_ALMACEN")+"</option>");
                                        }
                                  %>
                               </select>
                            </td>
                        </tr>
                        <tr>
                            <td class="outputTextBold">Capitulo</td>
                            <td class="outputTextBold">::</td>
                           <td>
                               <select id="codCapitulo" class="inputText" multiple onchange="ajaxGrupos(form1)">
                                 <%
                                        consulta="select c.COD_CAPITULO,c.NOMBRE_CAPITULO from capitulos c where c.COD_ESTADO_REGISTRO=1 order by c.NOMBRE_CAPITULO";
                                        res=st.executeQuery(consulta);
                                        while(res.next())
                                        {
                                            out.println("<option value='"+res.getString("COD_CAPITULO")+"'>"+res.getString("NOMBRE_CAPITULO")+"</option>");
                                        }
                                        res.close();
                                        st.close();
                                        con.close();
                                   }
                                    catch(SQLException ex)
                                    {
                                        ex.printStackTrace();
                                    }
                                  %>
                               </select>
                            </td>
                        </tr>
                        <tr>
                            <td class="outputTextBold">Grupo</td>
                            <td class="outputTextBold">::</td>
                           <td>
                               <div id="divGrupo">
                                    <select id="codGrupo" class="inputText" multiple>

                                    </select>
                               </div>
                            </td>
                        </tr>
                         <tr>
                             <td colspan="3">
                                 <div class="filtrarFechaInicio">
                                     <input type="checkbox" checked="false" id="fitrarFechaInicio" name="fitrarFechaInicio"
                                            onchange="onChangeAplicaFechaInicio();"/>
                                     <label for="fitrarFechaInicio">Filtrar por Fecha de Ingreso</label>
                                 </div>
                                 <table cellpading="0" id="tablaFechaInicio" cellspacing="0" style="border:1px solid #ccc">
                                    <tr>
                                        <td class="outputTextBold">Fecha Inicio</td>
                                        <td class="outputTextBold">::</td>

                                         <%
                                                SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy");
                                                String[] fechaInicio=form.format(new Date()).split("/");

                                         %>
                                        <td>
                                            <input type="text"  size="12"  value="<%=("01/"+fechaInicio[1]+"/"+fechaInicio[2])%>" id="fechaInicio" name="fechaInicio" class="inputText">
                                            <img id="imagenFecha1" src="../../img/fecha.bmp">
                                            <DLCALENDAR tool_tip="Seleccione la Fecha"
                                                        daybar_style="background-color: DBE1E7;
                                                        font-family: verdana; color:000000;"navbar_style="background-color: 7992B7; color:ffffff;"
                                                        input_element_id="fechaInicio" click_element_id="imagenFecha1">
                                             </DLCALENDAR>
                                        </td>
                                        <td class="outputTextBold">Fecha Final</td>
                                        <td class="outputTextBold">::</td>
                                        <td >
                                            <input type="text"  size="12"  value="<%=form.format(new Date()) %>" id="fechaFinal" name="fechaFinal" class="inputText">
                                            <img id="imagenFechaFinal" src="../../img/fecha.bmp">
                                            <DLCALENDAR tool_tip="Seleccione la Fecha"
                                                        daybar_style="background-color: DBE1E7;
                                                        font-family: verdana; color:000000;"navbar_style="background-color: 7992B7; color:ffffff;"
                                                        input_element_id="fechaFinal" click_element_id="imagenFechaFinal">
                                             </DLCALENDAR>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </tbody>
                    <tfoot>
                        <tr>
                            <td colspan="6">
                                <input type="button" class="btn"  value="Ver Reporte" name="reporte" onclick="verReporte(form1)" />
                            </td>
                        </tr>
                    </tfoot>
                </table>
            </div>
            <center>
                <input type="hidden" name="estados" id="estados" />
                <input type="hidden" name="almacenes" id="almacenes" />
                <input type="hidden" name="capitulo" id="capitulo" />
                <input type="hidden" name="grupo" id="grupo" />
            </center>
            <!--datos de referencia para el envio de datos via post-->



        </form>
        <script type="text/javascript" language="JavaScript"  src="../../js/dlcalendar.js"></script>
    </body>
</html>