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
        
        <script type="text/javascript">
            function ajaxAlmacen(f){
                ajax = creaAjax();

                ajax.open("GET","../ajaxAlmacen.jsf?codFilial="+(f.codFilial.value),true);
                ajax.onreadystatechange=function(){
                    if (ajax.readyState==4) {
                        document.getElementById("div_almacen").innerHTML=ajax.responseText;
                        desBloquearPantalla();
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
                ajax.open("GET","../ajaxGrupo.jsf?codCapitulo="+arrayCodCapitulo+"&multiple=1&size=10",true);
                ajax.onreadystatechange=function(){
                    if (ajax.readyState==4) {
                        document.getElementById("div_grupo").innerHTML=ajax.responseText;
                        desBloquearPantalla();
                    }
                }
                
                ajax.send(null);
            }
             function buscarMaterial(f){
                var arrayCodCapitulo=new Array();
                var j=0;
                for(var i=0;i<=f.codCapitulo.options.length-1;i++)
                {	if(f.codCapitulo.options[i].selected)
                    {	arrayCodCapitulo[j]=f.codCapitulo.options[i].value;
                        j++;
                    }
                }

                // grupos
                var arrayCodGrupo=new Array();
                j=0;
                for(var i=0;i<=f.codGrupo.options.length-1;i++)
                {	if(f.codGrupo.options[i].selected)
                    {	arrayCodGrupo[j]=f.codGrupo.options[i].value;
                        j++;
                    }
                }
                var nombreMaterial = document.getElementById("nombreMaterial").value;
                //var movimiento = document.getElementById("movimiento").value;//
                ajax = creaAjax();
               // ajax.open("GET","../ajaxMaterial.jsf?codCapitulo="+arrayCodCapitulo+"&codGrupo="+arrayCodGrupo+"&nombreMaterial="+encodeURIComponent(nombreMaterial)+"&movimientoItem="+movimiento,true);
                ajax.open("GET","../ajaxMaterial.jsf?codCapitulo="+arrayCodCapitulo+"&codGrupo="+arrayCodGrupo+"&nombreMaterial="+encodeURIComponent(nombreMaterial),true);
                ajax.onreadystatechange=function(){
                    if (ajax.readyState==4) {
                        document.getElementById("div_material").innerHTML = ajax.responseText;
                        desBloquearPantalla();
                    }
                }
                ajax.send(null);
            }
            //cel.getElementsByTagName('input')[0].checked=true;            
            function validar() {
                var elem = document.getElementById("dataMateriales");
                var n = elem.getElementsByTagName("input").length;
                var contSels = 0;
                for(var i = 0; i<n; i++) {
                    if(elem.getElementsByTagName("input")[i].checked) {
                        contSels++;
                    }
                }
                if(contSels===1) {
                    alert("ok");
                } else {
                    alert("Seleccione solo UNO, son " + contSels + ".");
                }
            }
            
            function verReporte(f,nombreReporte){
                // seleccionar los items de la tabla
                 var elements=document.getElementById('dataMateriales');
                 var rowsElement=elements.rows;                 
                 var arrayMateriales = new Array();
                 var j = 0;
                 var cont=0;
                for(var i=1;i<rowsElement.length;i++){
                        var cellsElement=rowsElement[i].cells;
                        var cel=cellsElement[0];
                        if(cel.getElementsByTagName('input')[0].type=='checkbox'){
                            if(cel.getElementsByTagName('input')[0].checked==true){
                                arrayMateriales[j]=cel.getElementsByTagName('input')[1].value==''?"0":cel.getElementsByTagName('input')[1].value;
                                j = j+1;
                                cont=cont+1;
                                //alert(arrayMateriales[i]);
                            }                            
                        }
                } 
                //alert(cont);
                //capitulos
                var nombreCapitulo=new Array();
                //var arrayNombreAreaEmpresa=new Array();
                j=0;
                for(var i=0;i<=f.codCapitulo.options.length-1;i++)
                {	if(f.codCapitulo.options[i].selected)
                    {	nombreCapitulo[j]=f.codCapitulo.options[i].innerHTML;
                        j++;
                    }
                }

                //grupo
                var nombreGrupo=new Array();
                j=0;
                for(var i=0;i<=f.codGrupo.options.length-1;i++)
                {	if(f.codGrupo.options[i].selected)
                    {	nombreGrupo[j]=f.codGrupo.options[i].innerHTML;
                        j++;
                    }
                }
                if(cont==1)
                {   f.codMaterial.value = arrayMateriales;
                    f.nombreFilial.value = f.codFilial.options[f.codFilial.selectedIndex].text
                    f.nombreAlmacen.value = f.codAlmacen.options[f.codAlmacen.selectedIndex].text
                    f.nombreCapitulo.value = nombreCapitulo;
                    f.nombreGrupo.value = nombreGrupo; 
                    f.action = nombreReporte;
                    f.submit();

                     //window.open("/reportes/reporteKardexValoradoNuevo/reporteKardexValorado.jsp?codMaterial="+arrayMateriales);
                }
                else            
                {    
                    if(cont==0)
                    {
                        alert("Debe selecionar un elemento")  
                    } 
                    else
                        alert("Solo puede seleccionar un campo") 
                }             
            }         

    </script>



    </head>
    <body>
        <h3 align="center">REPORTE KARDEX VALORADO</h3>
        <form method="post" action="reporteExistenciasAlmacen.jsp" target="_blank" name="form1">
            <div align="center">
                <table class="tablaFiltroReporte">
                    <thead>
                        <tr>
                            <td  colspan="6" >Introduzca Datos</td>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td style="padding:8px">Filial</td>
                            <td style="padding:8px">::</td>
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


                String sql = " select cod_filial, nombre_filial from filiales where cod_estado_registro=1 ";

                System.out.println("sql filtro:" + sql);
                ResultSet rs = st.executeQuery(sql);
                            %>
                            <td style="padding:8px">
                                <select style='color:fuchsia' id="codFilial" name="codFilial"  onchange="ajaxAlmacen(form1);">
                                    <%

                                out.print("<option value='-1'>-SELECCIONE UNA OPCION-</option>");

                                while (rs.next()) {
                                    if(codFilial==rs.getInt("cod_filial")){
                                        out.print("<option value='"+ rs.getString("cod_filial") +"' selected >"+rs.getString("nombre_filial")+"</option>");
                                    }else{
                                        out.print("<option value='"+ rs.getString("cod_filial") +"' >"+rs.getString("nombre_filial")+"</option>");
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
                            <td style="padding:8px">Almacen</td>
                            <td style="padding:8px">::</td>

                            <td style="padding:8px">
                                <div id="div_almacen">
                                <%
                                String sql_almacenes="select a.COD_ALMACEN,a.NOMBRE_ALMACEN from ALMACENES a where a.COD_FILIAL="+codFilial+" and a.COD_ESTADO_REGISTRO=1";
                                System.out.println("sql_almacenes:" + sql_almacenes);
                                Statement st_a = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                                ResultSet rs_a = st_a.executeQuery(sql_almacenes);
                                %>
                                <select style='color:fuchsia' id="codAlmacen" name="codAlmacen" class="inputText" >
                                    <option value="-1">-TODOS-</option>
                                    <%
                                    while (rs_a.next()) {
                                    if(codigoAlmacen==rs_a.getInt("COD_ALMACEN")){
                                        out.print("<option value='"+ rs_a.getString("cod_almacen") +"' selected >"+rs_a.getString("nombre_almacen")+"</option>");
                                    }else{
                                        out.print("<option value='"+ rs_a.getString("cod_almacen") +"' >"+rs_a.getString("nombre_almacen")+"</option>");
                                    }

                                }
                                    %>
                                </select>
                                </div>
                            </td>

                        </tr>

                        <tr  >
                            <td style="padding:8px">Capitulos</td>
                            <td style="padding:8px">::</td>

                                     <%
                               try {
                                con = Util.openConnection(con);
                                System.out.println("con:::::::::::::" + con);
                                Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                                String sql = " select c.COD_CAPITULO,c.NOMBRE_CAPITULO  from CAPITULOS c where c.COD_ESTADO_REGISTRO = 1 order by c.NOMBRE_CAPITULO";

                                System.out.println("sql filtro:" + sql);
                                ResultSet rs = st.executeQuery(sql);
                            %>
                            <td style="padding:8px">
                                <select id="codCapitulo" name="codCapitulo" multiple size="10"  onchange="ajaxGrupo(form1);">
                                <%
                                while (rs.next()) {
                                    out.print("<option value='"+ rs.getString("COD_CAPITULO") +"' >"+rs.getString("NOMBRE_CAPITULO")+"</option>");
                                }%>
                                </select>
                                <%
                                rs.close();
                                st.close();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                %>
                            </td>
                             <td style="padding:8px">Grupo</td>
                            <td style="padding:8px">::</td>
                            <td  style="padding:8px">
                                <div id="div_grupo">
                                    <select id="codGrupo" name="codGrupo" class="inputText" size="10" style="width:200px;" multiple>
                                    <option value="-1">-NINGUNO-</option>
                                </select>

                                </div>
                            </td>
                        </tr>
                        <tr >
                            <td style="padding:8px" style="padding:8px">Item</td>
                            <td style="padding:8px" style="padding:8px">::</td>
                            <td style="padding:8px" style="padding:8px" colspan="3" >
                                <input type="text" class="inputText" size="70" id="nombreMaterial" name="nombreMaterial"/>
                            </td>
                            <td style="padding:8px" style="padding:8px">
                                <input type="button" value="Buscar" class="btn" size="50" onclick="buscarMaterial(form1)" />
                            </td>
                        </tr>
                        <tr>
                            <td style="padding:8px" style="padding:8px">Fecha Inicio</td>
                            <td style="padding:8px" style="padding:8px">::</td>

                             <%
                                    SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy");
                             %>

                            <td style="padding:8px" style="padding:8px">
                            <input type="text"  size="12"  value="<%=form.format(new Date()) %>" id="fechaIni" name="fechaIni" class="inputText">
                                <img id="imagenFecha1" src="../../img/fecha.bmp">
                                <DLCALENDAR tool_tip="Seleccione la Fecha"
                                            daybar_style="background-color: DBE1E7;
                                            font-family: verdana; color:000000;"navbar_style="background-color: 7992B7; color:ffffff;"
                                            input_element_id="fechaIni" click_element_id="imagenFecha1">
                                 </DLCALENDAR>
                            </td>
                            <td style="padding:8px" style="padding:8px">Fecha Final</td>
                            <td style="padding:8px" style="padding:8px">::</td>
                            <td style="padding:8px" style="padding:8px">
                            <input type="text"  size="12"  value="<%=form.format(new Date()) %>" id="fechaFin" name="fechaFin" class="inputText">
                                <img id="imagenFechaFin" src="../../img/fecha.bmp">
                                <DLCALENDAR tool_tip="Seleccione la Fecha"
                                            daybar_style="background-color: DBE1E7;
                                            font-family: verdana; color:000000;"navbar_style="background-color: 7992B7; color:ffffff;"
                                            input_element_id="fechaFin" click_element_id="imagenFechaFin">
                                 </DLCALENDAR>
                            </td>
                        </tr>                       
                        <tr>
                            <td colspan="6" style="text-align: center">
                                <div style="overflow:auto;height:150px;margin-left:5%;width:90%;border: 1px solid #ccc" id="main">
                                    <div id="div_material">
                                        <table id='dataMateriales' class='outputText0' style='border : solid #cccccc 1px;' cellpadding='0' cellspacing='0'  >

                                        </table>
                                    </div>
                                </div>
                            </td>
                        </tr>
                    </tbody>
                    <tfoot>
                        <tr>
                            <td colspan="6">
                                <input type="hidden" name="formato" value=""/>
                                <input type="button" class="btn"  value="Ver Reporte Pdf" name="reporte" onclick="verReporte(form1,'reporteKardexValorado.jsf')" />
                                <input type="button" class="btn"  value="Ver Reporte Excel" name="reporte" onclick="verReporte(form1,'reporteKardexValoradoEXEL.jsf')" />
                            </td>
                        </tr>
                    </tfoot>
                </table> 
            </div>
            <br>
            <center>
                <input type="hidden" name="codigosArea" id="codigosArea" />
            </center>

            <input type="hidden" name="codMaterial" />
            <input type="hidden" name="nombreFilial" />
            <input type="hidden" name="nombreAlmacen" />
            <input type="hidden" name="nombreCapitulo" />
            <input type="hidden" name="nombreGrupo" />

            <input type="hidden" name="codProgramaProduccionPeriodo">
            <input type="hidden" name="nombreProgramaProduccionPeriodo">

            <input type="hidden" name="codCompProdArray">
            <input type="hidden" name="nombreCompProd">

            <input type="hidden" name="codAreaEmpresaP">
            <input type="hidden" name="nombreAreaEmpresaP">
            <input type="hidden" name="desdeFechaP">
            <input type="hidden" name="hastaFechaP">

        </form>
        <script type="text/javascript" language="JavaScript"  src="../../js/dlcalendar.js"></script>
    </body>
</html>