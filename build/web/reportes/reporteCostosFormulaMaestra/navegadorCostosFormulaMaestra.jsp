


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
       
        <script type="text/javascript">

          function ajaxAlmacen(f){
                var div_almacen;
                div_almacen=document.getElementById("div_almacen");

                ajax = creaAjax();

                ajax.open("GET","../ajaxAlmacen.jsf?codFilial="+(f.codFilial.value),true);
                ajax.onreadystatechange=function(){
                    if (ajax.readyState==4) {
                        div_almacen.innerHTML=ajax.responseText;
                        desBloquearPantalla();
                    }
                }
                
                ajax.send(null);


            }

             function ajaxGrupo(f){
                 //con los capitulos seleccionados

                //var div_producto=document.getElementById("div_producto");
                var arrayCodCapitulo=new Array();
                var j=0;
                for(var i=0;i<=f.codCapitulo.options.length-1;i++)
                {	if(f.codCapitulo.options[i].selected)
                    {	arrayCodCapitulo[j]=f.codCapitulo.options[i].value;
                        j++;
                    }
                }


                var div_grupo;
                div_grupo=document.getElementById("div_grupo");

                ajax=nuevoAjax();

                ajax.open("GET","ajaxGrupo.jsf?codCapitulo="+arrayCodCapitulo,true);
                ajax.onreadystatechange=function(){
                    if (ajax.readyState==4) {
                        div_grupo.innerHTML=ajax.responseText;
                    }
                }
                
                ajax.send(null);


            }

             function buscarMaterial(f){
                 //con los capitulos seleccionados

                //var div_producto=document.getElementById("div_producto");
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


                var div_material;
                div_material=document.getElementById("div_material");

                ajax=nuevoAjax();

                ajax.open("GET","ajaxMaterial.jsf?codCapitulo="+arrayCodCapitulo+"&codGrupo="+arrayCodGrupo+"&nombreMaterial="+nombreMaterial,true);
                ajax.onreadystatechange=function(){
                    if (ajax.readyState==4) {
                        div_material.innerHTML=ajax.responseText;
                    }
                }

                ajax.send(null);


            }
            //cel.getElementsByTagName('input')[0].checked=true;
            function click1(f){
                //alert(f.check.checked);
                if (f.check.checked==true){
                    f.fecha2.disabled=false;
                    f.fecha1.disabled=false;
                }else{
                    f.fecha2.disabled=true;
                    f.fecha1.disabled=true;
                }



            }
            function verReporte(f,nombreReporte){
               
                //capitulos

//alert("click ver reporte");
               
                //f.codMaterial.value = arrayMateriales;
                f.nombreFilial.value = f.codFilial.options[f.codFilial.selectedIndex].text
                f.nombreAlmacen.value = f.codAlmacen.options[f.codAlmacen.selectedIndex].text
                //f.nombreProveedor.value = nombreProveedor;
                
                /*if (f.check.checked==false){
                    f.fechaIni.value=0;
                    f.fechaFin.value=0;
                }else{
                    f.fechaIni.value=f.fecha1.value;
                    f.fechaFin.value=f.fecha2.value;
                }*/

               // alert(f.fechaInicial.value);
                //alert(f.fechaFinal.value);
                f.action = nombreReporte;
                f.submit();

            }

       function seleccionarTodo(){
       //alert('entro');
       var seleccionar_todo=document.getElementById('seleccionar_todo');
       var elements=document.getElementById('dataMateriales');

       if(seleccionar_todo.checked==true){
         //alert('entro por verdad');
            var rowsElement=elements.rows;
            for(var i=1;i<rowsElement.length;i++){
                var cellsElement=rowsElement[i].cells;
                var cel=cellsElement[0];
                if(cel.getElementsByTagName('input')[0].type=='checkbox'){
                    cel.getElementsByTagName('input')[0].checked=true;
                }
            }
       }
       else
       {//alert('entro por false');
            var rowsElement=elements.rows;
            for(var i=1;i<rowsElement.length;i++){
                var cellsElement=rowsElement[i].cells;
                var cel=cellsElement[0];
                if(cel.getElementsByTagName('input')[0].type=='checkbox'){
                    cel.getElementsByTagName('input')[0].checked=false;
                }
            }

       }
       return true;

    }



        </script>



    </head>
    <body>
        <h3 align="center">Reporte de Fórmulas Maestras con Costo</h3>
        <form method="post" action="reporteExistenciasAlmacen.jsp" target="_blank" name="form1">
            <div align="center">
                <table class="tablaFiltroReporte" width="90%">
                    <thead>
                        <tr>
                            <td height="35px" colspan="6" >Introduzca Datos</td>
                        </tr>
                    </thead>
                    <tbody>
                    <tr class="outputText3">
                        <td style="padding:7px" height="35px" class="">Filial</td>
                        <td style="padding:7px" class="">::</td>
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
                        <td style="padding:7px" class="">
                            <select style='color:fuchsia' id="codFilial" name="codFilial" class="outputText3" onchange="ajaxAlmacen(form1);">
                                <%
                            
                            out.print("<option value='-1'>-SELECCIONE UNA OPCION-</option>");
                            while (rs.next()) {
                                if(codFilial==rs.getInt("cod_filial")){
                                    out.print("<option value='"+ rs.getString("cod_filial") +"' selected >"+rs.getString("nombre_filial")+"</option>");
                                }else{
                                    out.print("<option value='"+ rs.getString("cod_filial") +"' >"+rs.getString("nombre_filial")+"</option>");
                                }
                                
                            }%>
                            </select>
                            <!--  <input type="checkbox"  onclick="selecccionarTodo(form1)" name="chk_todoTipo" >Todo-->
                            <%
                            rs.close();
                            st.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            %>
                        </td>
                        <td style="padding:7px" class="">Almacen</td>
                        <td style="padding:7px" class="">::</td>

                        <td style="padding:7px" class="">
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

                    </tr>

                    <tr class="outputText3">
                        <td height="40PX"  class="">Fecha Inicial</td>
                        <td class="">::</td>
                       
                         <%
                                SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy");
                         %>

                        <td class="">

                        <input type="text"  size="12"  value="<%=form.format(new Date()) %>" id="fechaInicial" name="fechaInicial" class="inputText">
                            <img id="imagenFecha1" src="../../img/fecha.bmp">
                            <DLCALENDAR tool_tip="Seleccione la Fecha"
                                        daybar_style="background-color: DBE1E7;
                                        font-family: verdana; color:000000;"navbar_style="background-color: 7992B7; color:ffffff;"
                                        input_element_id="fechaInicial" click_element_id="imagenFecha1">
                             </DLCALENDAR>
                         </td>
                        <td height="40PX"  class="">Fecha Final</td>
                        <td height="40PX"  class="">:</td>
                        <td class="">
                         

                         <input type="text"  size="12"  value="<%=form.format(new Date()) %>" id="fechaFinal" name="fechaFinal" class="inputText">
                            <img id="imagenFecha2" src="../../img/fecha.bmp">
                            <DLCALENDAR tool_tip="Seleccione la Fecha"
                                        daybar_style="background-color: DBE1E7;
                                        font-family: verdana; color:000000;"navbar_style="background-color: 7992B7; color:ffffff;"
                                        input_element_id="fechaFinal" click_element_id="imagenFecha2">
                             </DLCALENDAR>
                            
                        </td>
                                      

                    </tr>
                    </tbody>
                    <tfoot>
                        <tr>
                            <td colspan="6">
                                <input type="button" class="btn"  value="Ver Reporte" 
                                name="reporte" onclick="verReporte(form1,'reporteCostosFormulaMaestra.jsp')" />
          
                            </td>
                        </tr>
                    </tfoot>
                </table>
                


            </div>
            <br>
            <br>
            <center>
                
                <input type="hidden" name="codigosArea" id="codigosArea" />
            </center>
            <!--datos de referencia para el envio de datos via post-->

            <input type="hidden" name="codMaterial" />
            <input type="hidden" name="nombreFilial" />
            <input type="hidden" name="nombreAlmacen" />
            <input type="hidden" name="nombreCapitulo" />
            <input type="hidden" name="nombreGrupo" />
            <input type="hidden" name="codGrupoArray" />
            <input type="hidden" name="codCapituloArray" />
            <input type="hidden" name="codProveedorArray" />
            <input type="hidden" name="codProgramaProduccionPeriodo">
            <input type="hidden" name="nombreProgramaProduccionPeriodo">

            <input type="hidden" name="codCompProdArray">
            <input type="hidden" name="nombreCompProd">
            <input type="hidden" name="nombreMaterialP">

            <input type="hidden" name="codAreaEmpresaP">
            <input type="hidden" name="nombreAreaEmpresaP">
            <input type="hidden" name="desdeFechaP">
            <input type="hidden" name="hastaFechaP">
            <input type="hidden" name="fechaIni">
            <input type="hidden" name="fechaFin">



        </form>
        <script src="../../js/chosen.js"></script>
        <script type="text/javascript" language="JavaScript"  src="../../js/dlcalendar.js"></script>
    </body>
</html>