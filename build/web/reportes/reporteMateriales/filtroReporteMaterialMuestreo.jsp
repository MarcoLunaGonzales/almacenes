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
        <script src="../../js/general.js"></script>
        <script type="text/javascript">
          

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
                        document.getElementById("div_grupo").innerHTML = ajax.responseText;
                        document.getElementById("codGrupo").onchange = function(){ajaxMaterial(form1)};
                        desBloquearPantalla();
                    }
                }

                ajax.send(null);


            }
              function ajaxMaterial(f){
                var arrayCodCapitulo=new Array();
                var j=0;
                for(var i=0;i<=f.codCapitulo.options.length-1;i++)
                {	if(f.codCapitulo.options[i].selected)
                    {	arrayCodCapitulo[j]=f.codCapitulo.options[i].value;
                        j++;
                    }
                }
                var arrayCodGrupo=new Array();
                var j=0;
                var grupo=document.getElementById("codGrupo");
                //alert(grupo.innerHTML);
                for(var i=0;i<=grupo.options.length-1;i++)
                {	if(grupo.options[i].selected)
                    {	arrayCodGrupo[j]=grupo.options[i].value;
                        j++;
                    }
                }
                ajax = creaAjax();
                ajax.open("GET","ajaxMaterial.jsf?codCapitulo="+arrayCodCapitulo+"&codGrupo="+arrayCodGrupo,true);
                ajax.onreadystatechange=function(){
                    if (ajax.readyState==4) {
                        document.getElementById("divItem").innerHTML=ajax.responseText;
                        desBloquearPantalla();
                    }
                }

                ajax.send(null);


            }


            //cel.getElementsByTagName('input')[0].checked=true;
            function verReporte(f,nombreReporte){
               
                //capitulos


                var nombreCapitulo=new Array();
                var codCapitulo=new Array();
                //var arrayNombreAreaEmpresa=new Array();
                var j=0;
                for(var i=0;i<=f.codCapitulo.options.length-1;i++)
                {	if(f.codCapitulo.options[i].selected)
                    {	nombreCapitulo[j]=f.codCapitulo.options[i].innerHTML;
                        codCapitulo[j]=f.codCapitulo.options[i].value;
                        j++;
                    }
                }

                //grupo
                var nombreGrupo=new Array();
                var codGrupo=new Array();
                j=0;
                for(var i=0;i<=f.codGrupo.options.length-1;i++)
                {	if(f.codGrupo.options[i].selected)
                    {	codGrupo[j]=f.codGrupo.options[i].value;
                        nombreGrupo[j]=f.codGrupo.options[i].innerHTML;
                        j++;
                    }
                }
                 var nombreItem=new Array();
                var codItem=new Array();
                j=0;
                for(var i=0;i<=f.codItem.options.length-1;i++)
                {	if(f.codItem.options[i].selected)
                    {	codItem[j]=f.codItem.options[i].value;
                        nombreItem[j]=f.codItem.options[i].innerHTML;
                        j++;
                    }
                }

                f.nombreCapitulo.value = nombreCapitulo;
                f.nombreGrupo.value = nombreGrupo;
                f.codGrupoArray.value = codGrupo;
                f.codCapituloArray.value = codCapitulo;
                f.codMaterial.value=codItem;
                
                f.nombreMaterial.value=nombreItem;
                
                //Salert(f.codMaterial.value);
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
        <h3 align="center">Reporte de Materiales</h3>
        <form method="post" action="reporteExistenciasAlmacen.jsp" target="_blank" name="form1">
            <div align="center">
                <table class="tablaFiltroReporte" width="90%">
                    <thead>
                        <tr>
                            <td height="35px" colspan="6" >Introduzca Datos</td>

                        </tr>
                    </thead>
                    <tbody>

                        <tr class="outputText3" >
                            <td class="">Capitulos</td>
                            <td class="">::</td>

                                     <%
                                Connection con = null;
                               try {
                                con = Util.openConnection(con);
                                System.out.println("con:::::::::::::" + con);
                                Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);




                                String sql = " select c.COD_CAPITULO,c.NOMBRE_CAPITULO  from CAPITULOS c where c.COD_ESTADO_REGISTRO = 1 order by c.NOMBRE_CAPITULO";

                                System.out.println("sql filtro:" + sql);
                                ResultSet rs = st.executeQuery(sql);
                            %>
                            <td class="" width="15%">
                                <select name="codCapitulo" id="codCapitulo" multiple size="10"  class="outputText3" onchange="ajaxGrupo(form1);">
                                    <%


                                while (rs.next()) {
                                    out.print("<option value="+ rs.getString("COD_CAPITULO") +" >"+rs.getString("NOMBRE_CAPITULO")+"</option>");
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
                             <td class="">Grupo</td>
                            <td class="">::</td>
                            <td class="">
                                <div id="div_grupo">
                                    <select name="codGrupo" id="codGrupo" class="inputText" multiple size="10" style="width:200px;" onchange="ajaxMaterial(form1);">
                                    <option value="-1">-TODOS-</option>
                                </select>

                                </div>
                            </td>
                        </tr>
                        <tr class="outputText3">
                             <td class="">Item</td>
                            <td class="">::</td>
                            <td class="">
                                <div id="divItem">
                                    <select name="codItem" id="codItem" class="inputText" size="10" style="width:300px;" >
                                    <option value="-1">-TODOS-</option>
                                </select>

                                </div>
                            </td>
                        </tr>
                    </tbody>
                    <tfoot>
                        <tr>
                            <td colspan="6">
                                <input type="button" class="btn"  value="Ver Reporte" name="reporte" onclick="verReporte(form1,'reporteMaterialMuestreo.jsp')" />
                                <input type="reset" class="btn"  value="Limpiar " name="reporteL" />
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
            <input type="hidden" name="nombreMaterial" />
            <input type="hidden" name="nombreAlmacen" />
            <input type="hidden" name="nombreCapitulo" />
            <input type="hidden" name="nombreGrupo" />
            <input type="hidden" name="codGrupoArray" />
            <input type="hidden" name="codCapituloArray" />
            <input type="hidden" name="codProgramaProduccionPeriodo">
            <input type="hidden" name="nombreProgramaProduccionPeriodo">

            <input type="hidden" name="codCompProdArray">
            <input type="hidden" name="nombreCompProd">


            <input type="hidden" name="codAreaEmpresaP">
            <input type="hidden" name="nombreAreaEmpresaP">
            <input type="hidden" name="desdeFechaP">
            <input type="hidden" name="hastaFechaP">
            <input type="hidden" name="nombreMaterialP">
            <input type="hidden" name="numIngresoP">
            <input type="hidden" name="numOCP">



        </form>
    </body>
</html>