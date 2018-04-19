
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
<%@ page errorPage="ExceptionHandler.jsp" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<html>
    <head>
        <link rel="STYLESHEET" type="text/css" href="../../css/ventas.css" />
        <link rel="STYLESHEET" type="text/css" href="../../css/chosen.css" />
        <script src="../../js/general.js"></script>
        <script src="../../js/chosen.js"></script>
        <script type="text/javascript" >
            
            function ajaxAlmacen(f){
                ajax = creaAjax();

                ajax.open("GET","ajaxAlmacen.jsf?codFilial="+(f.codFilial.value),true);
                ajax.onreadystatechange=function(){
                    if (ajax.readyState==4) {
                        document.getElementById("div_almacen").innerHTML=ajax.responseText;
                        desBloquearPantalla();
                        f.codAlmacen.onchange();
                    }
                }
                
                ajax.send(null);


            }

            function ajaxGrupo(f){
                var arrayCodCapitulo=new Array();
                var j=0;
                for(var i=0;i<=f.codCapitulo.options.length-1;i++)
                {	if(f.codCapitulo.options[i].selected)
                    {	arrayCodCapitulo[j]=f.codCapitulo.options[i].value;
                        j++;
                    }
                }
                ajax = creaAjax();
                ajax.open("GET","../ajaxGrupo.jsf?codCapitulo="+arrayCodCapitulo+"&optionTodos=1",true);
                ajax.onreadystatechange=function(){
                    if (ajax.readyState==4) {
                        document.getElementById("div_grupo").innerHTML=ajax.responseText;
                        desBloquearPantalla();
                    }
                }
                
                ajax.send(null);


            }

            //cel.getElementsByTagName('input')[0].checked=true;
            function verReporte(f,nombreReporte){
                
                f.action = nombreReporte;
                f.submit();

            }

    function ajaxAmbienteAlmacenCapitulo(form){
        ajax = creaAjax();
        ajax.open("GET","../ajaxAmbienteAlmacen.jsf?codAlmacen="+form.codAlmacen.value+"&date="+(new Date()).getTime().toString(),true);
        ajax.onreadystatechange=function(){
            if (ajax.readyState==4) {
                document.getElementById("divAmbiente").innerHTML = ajax.responseText;
                desBloquearPantalla();
                ajaxCapitulo = creaAjax();
                ajaxCapitulo.open("GET","../ajaxCapitulosGestionUbicacion.jsf?codAlmacen="+form.codAlmacen.value+"&date="+(new Date()).getTime().toString(),true);
                ajaxCapitulo.onreadystatechange=function(){
                    if (ajaxCapitulo.readyState==4) {
                        document.getElementById("divCapitulo").innerHTML = ajaxCapitulo.responseText;
                        desBloquearPantalla();
                    }
                }
                ajaxCapitulo.send(null);
            }
        }

        ajax.send(null);
    }
        
        function ajaxPasillo(f){
                var div_pasillo;
                div_pasillo=document.getElementById("div_pasillo");

                ajax=creaAjax();
                
               
                ajax.open("GET","ajaxPasillo.jsf?codAmbiente="+(f.codAmbiente.value)+"&mat="+Math.random(),true);
                ajax.onreadystatechange=function(){
                    if (ajax.readyState==4) {
                        div_pasillo.innerHTML=ajax.responseText;
                        desBloquearPantalla();
                    }
                }
                ajax.send(null);
            }
            function recargar(){
                document.getElementById('form1').reset();
            }

        </script>



    </head>
    <body onbeforeunload="recargar()">
        <h3 align="center">Reporte de Existencias Por Ubicacion</h3>
        <form method="post" action="reporteExistenciasAlmacenEstanteUbicacion.jsf"  target="_blank" name="form1" id="form1">
            <div align="center">
                <table class="tablaFiltroReporte outputText2"  width="50%">
                    <thead>
                        <tr>
                            <td  colspan="6">Introduzca Datos</td>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td class="outputTextBold">Filial</td>
                            <td class="outputTextBold" >::</td>
                            <%
                                Connection con = null;
                 ManagedAccesoSistema bean1=(ManagedAccesoSistema)com.cofar.util.Util.getSessionBean("ManagedAccesoSistema");
               int codigoAlmacen=bean1.getAlmacenesGlobal().getCodAlmacen();
               System.out.println("codigoAlmacen:"+codigoAlmacen);

               String codProgramaProduccionPeriodoReq ="";
               if(request.getParameter("codProgramaProduccionPeriodo")!=null)
               {codProgramaProduccionPeriodoReq = request.getParameter("codProgramaProduccionPeriodo");}
               int codFilial=0;
               try {
                con = Util.openConnection(con);
                System.out.println("con:::::::::::::" + con);
                Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);


                String sql_filial="select a.COD_FILIAL from ALMACENES a where a.COD_ALMACEN="+codigoAlmacen+"";
                System.out.println("sql_filial:" + sql_filial);
                Statement st_filial = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs_filial = st_filial.executeQuery(sql_filial);
                codFilial=0;
                while(rs_filial.next()){
                    codFilial=rs_filial.getInt(1);
                }


                String sql = " select f.COD_FILIAL,f.NOMBRE_FILIAL from filiales f where f.COD_ESTADO_REGISTRO = 1";

                System.out.println("sql filtro:" + sql);
                ResultSet rs = st.executeQuery(sql);
                            %>
                            <td style="padding:8px">
                                <select style='color:fuchsia' name="codFilial"  onchange="ajaxAlmacen(form1);">
                                    <%
                                        while (rs.next()) {
                                            if(codFilial==rs.getInt("cod_filial")){
                                                out.print("<option value="+ rs.getString("cod_filial") +" selected >"+rs.getString("nombre_filial")+"</option>");
                                            }else{
                                                out.print("<option value="+ rs.getString("cod_filial") +" >"+rs.getString("nombre_filial")+"</option>");
                                            }
                                        }
                                    %>
                                </select>
                                <%
                                rs.close();
                                st.close();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                %>
                            </td>
                            <td class="outputTextBold">Almacen</td>
                            <td class="outputTextBold">::</td>

                            <td style="padding:8px">
                                <div id="div_almacen">
                                    <jsp:include page="ajaxAlmacen.jsp">
                                        <jsp:param name="codFilial" value="<%=(codFilial)%>"/>
                                        <jsp:param name="codAlmacenDefault" value="<%=(codigoAlmacen)%>"/>
                                    </jsp:include>
                                </div>
                            </td>

                        </tr>
                        <tr>
                            <td class="outputTextBold">Ambiente</td>
                            <td class="outputTextBold">::</td>
                            <td class="" style="padding:8px">
                                <div id="divAmbiente">
                                    <jsp:include page="../ajaxAmbienteAlmacen.jsp">
                                        <jsp:param value="<%=(codigoAlmacen)%>" name="codAlmacen"/>
                                    </jsp:include>
                                </div>
                            </td>
                            <td class="outputTextBold" >Pasillo</td>
                            <td class="outputTextBold" >::</td>
                            <td class="" style="padding:8px">
                                <div id="div_pasillo">
                                <select name="codPasillo" class="inputText"  style="width:200px;" >
                                    <option value="-1">-TODOS-</option>
                                </select>
                                </div>
                            </td>
                        </tr>
                        <tr >
                            <td class="outputTextBold" >Estante</td>
                            <td class="outputTextBold" >::</td>
                            <td class="" style="padding:8px" colspan="1" >
                                <input type="text" class="inputText" size="10" name="estante"  placeholder="Estante"/>
                            </td>
                            <td class="outputTextBold" >Ubicacion</td>
                            <td class="outputTextBold" >::</td>
                            <td class="" style="padding:8px" colspan="1" >
                                <input type="text" class="inputText" size="10" name="ubicacion" placeholder="Ubicación" />
                            </td>
                        </tr>
                        <tr>
                            <td class="outputTextBold" >Capitulo</td>
                            <td class="outputTextBold" >::</td>
                            <td>
                                <div id="divCapitulo">
                                    <jsp:include page="../ajaxCapitulosGestionUbicacion.jsp">
                                        <jsp:param value="<%=(codigoAlmacen)%>" name="codAlmacen"/>
                                    </jsp:include>
                                </div>
                                
                            </td>
                            <td class="outputTextBold">Grupo</td>
                            <td class="outputTextBold">::</td>
                            <td>
                                <div id="div_grupo">
                                    <select id="codGrupo" name="codGrupo" class="inputText">
                                        <option value="0">--TODOS--</option>
                                    </select>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="outputText2" style="font-weight: bold">Material</td>
                            <td class="outputText2" style="font-weight: bold">::</td>
                            <td>
                                <input type="text" style = "width: 100%" class="inputText" placeholder="Nombre Material" id="nombreMaterial" name="nombreMaterial"/>
                            </td>
                        </tr>
                    </tbody>
                    <tfoot>
                        <tr>
                            <td colspan="6">
                                <input type="submit" class="btn"  value="Ver Reporte" name="reporte"/>
                            </td>
                        </tr>
                    </tfoot>
                </table>
                </div>
            </div>
        </form>
        
        <script type="text/javascript">
            cargarChosen();
        </script>
    </body>
</html>