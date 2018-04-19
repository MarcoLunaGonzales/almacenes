
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
<%@ page import = "java.text.DecimalFormat"%>
<%@ page import = "java.text.NumberFormat"%>
<%@ page import = "java.util.Locale"%>
<html>
    <head>
        <link rel="STYLESHEET" type="text/css" href="../../../css/ventas.css" />
        <script src="../../../js/general.js"></script>
        <script type="text/javascript">


            function ajaxAlmacen(f){
                var div_almacen;
                div_almacen=document.getElementById("div_almacen");

                ajax=nuevoAjax();

                ajax.open("GET","ajaxAlmacen.jsf?codFilial="+(f.codFilial.value),true);
                ajax.onreadystatechange=function(){
                    if (ajax.readyState==4) {
                        div_almacen.innerHTML=ajax.responseText;
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

                ajax = creaAjax();

                ajax.open("GET","../../ajaxGrupo.jsf?codCapitulo="+arrayCodCapitulo+"&multiple=1&size=10",true);
                ajax.onreadystatechange=function(){
                    if (ajax.readyState==4) {
                        document.getElementById("div_grupo").innerHTML=ajax.responseText;
                        desBloquearPantalla();
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
                {
                    if(f.codCapitulo.options[i].selected)
                    {
                        arrayCodCapitulo[j]=f.codCapitulo.options[i].value;
                        j++;
                    }
                }
                if(j==0){
                    arrayCodCapitulo=0;
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
                if(j==0){
                    arrayCodGrupo=0;
                }
                var nombreMaterial = document.getElementById("nombreMaterial").value;
                if(nombreMaterial==''){
                    nombreMaterial=0;
                }
                

                var div_material;
                div_material=document.getElementById("div_material");

                ajax = creaAjax();
                //alert(nombreMaterial);
                //alert(arrayCodCapitulo);
                //alert(arrayCodGrupo);
                //alert("+ajaxMaterial.jsf?codCapitulo='"+arrayCodCapitulo+"'&codGrupo='"+arrayCodGrupo+"'&nombreMaterial="+nombreMaterial+"",true);

                ajax.open("GET","ajaxMaterial.jsf?codCapitulo="+arrayCodCapitulo+"&codGrupo="+arrayCodGrupo+"&nombreMaterial="+encodeURIComponent(nombreMaterial)+"&data="+(new Date()).getTime().toString(),true);
                ajax.onreadystatechange=function(){
                    if (ajax.readyState==4) {
                        div_material.innerHTML=ajax.responseText;
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
                if(j==0){
                    codCapitulo=0;
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
                if(j==0){
                    codGrupo=0;
                }
                //f.codMaterial.value = arrayMateriales;
                //f.nombreFilial.value = f.codFilial.options[f.codFilial.selectedIndex].text
                //f.nombreAlmacen.value = f.codAlmacen.options[f.codAlmacen.selectedIndex].text
                f.nombreCapitulo.value = nombreCapitulo;
                f.nombreGrupo.value = nombreGrupo;
                f.codGrupoArray.value = codGrupo;
                f.codCapituloArray.value = codCapitulo;

                f.nombreMaterialP.value = f.nombreMaterial.value;
                if(f.nombreMaterialP.value==''){
                    f.nombreMaterialP.value=0;
                }
                //f.numIngresoP.value = f.numIngreso.value;
                //f.numOCP.value = f.numOC.value;


                //alert(codCapitulo);
                //alert(codGrupo);
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
        <h3 align="center">Reporte de Vencimiento</h3>
        <form method="post" action="reporteExistenciasAlmacen.jsp" target="_blank" id="form1" name="form1">
            <div align="center">
                <table class="tablaFiltroReporte" width="90%">
                    <thead>
                        <tr>
                            <td height="35px" colspan="6" >Introduzca Datos</td>
                        </tr>
                    </thead>
                    <tbody>

                        <tr>
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
                                <select id="codCapitulo" name="codCapitulo" multiple size="10" onchange="ajaxGrupo(form1);">
                                    <%


                                        while (rs.next()) {
                                            out.print("<option value='" + rs.getString("COD_CAPITULO") + "' >" + rs.getString("NOMBRE_CAPITULO") + "</option>");
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
                                    <select id="codGrupo" name="codGrupo" class="inputText" size="10" style="width:200px;" >
                                        <option value="-1">-TODOS-</option>
                                    </select>

                                </div>
                            </td>
                        </tr>




                        <tr class="outputText3">
                            <td  height="35px" class="">Item</td>
                            <td class="">::</td>
                            <td class="">
                                <input type="text" style="color:blue;text-transform:uppercase" class="inputText" size="55" id="nombreMaterial" name="nombreMaterial" />
                            </td>
                            <td class="">
                                <input type="button" value="Buscar" class="outputText2" size="50" name="nombreMaterial1" onclick="buscarMaterial(form1)" />
                            </td>

                        </tr>
                        <tr>
                            <td colspan="6">
                                <div style="overflow:auto;text-align:center;height:200px;width:90% " id="main">
                                    <div id="div_material">
                                        <table id='dataMateriales' class='outputText0' style='border : solid #000000 1px;' cellpadding='0' cellspacing='0'  >
                                            <tr bgcolor="#cccccc">
                                                <th style="border-bottom:1px solid #cccccc;padding:7PX">Nro. Ingreso</th>
                                                <th style="border-bottom:1px solid #cccccc;padding:7PX">Fecha Ingreso</th>
                                                <th style="border-bottom:1px solid #cccccc;padding:7PX">Etiqueta</th>
                                                <th style="border-bottom:1px solid #cccccc;padding:7PX">Item</th>
                                                <th style="border-bottom:1px solid #cccccc;padding:7PX">Unidad</th>
                                                <th style="border-bottom:1px solid #cccccc;padding:7PX">Cantidad</th>
                                                <th style="border-bottom:1px solid #cccccc;padding:7PX">Costo</th>
                                                <th style="border-bottom:1px solid #cccccc;padding:7PX">Fecha Venc.</th>
                                                <th style="border-bottom:1px solid #cccccc;padding:7PX">Fecha Reanalisis</th>
                                                <th style="border-bottom:1px solid #cccccc;padding:7PX">Dias</th>
                                                <th style="border-bottom:1px solid #cccccc;padding:7PX">Lote Proveedor</th>
                                                <th style="border-bottom:1px solid #cccccc;padding:7PX">Proveedor</th>
                                            </tr>
                                            <%
                        try
                        {
                            SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                            String sql_reporte = "SELECT * FROM VISTA_REPORTE_FECHA_VENCIMIENTO V ORDER BY V.FECHA_VENCIMIENTO";
                            System.out.println("sql_reporte: " + sql_reporte);
                            SimpleDateFormat sdfMMyyyy =new SimpleDateFormat("MM/yyyy");
                            con = Util.openConnection(con);
                            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                            ResultSet rs = st.executeQuery(sql_reporte);
                            SimpleDateFormat f =new SimpleDateFormat("dd/MM/yyyy");
                            NumberFormat numeroformato = NumberFormat.getNumberInstance(Locale.ENGLISH);
                            DecimalFormat formato = (DecimalFormat) numeroformato;
                            formato.applyPattern("####.##;(####.##)");
                            while(rs.next()){
                            %>
                            <tr >
                                <td style="border-bottom:1px solid #cccccc;padding:7PX"><%= rs.getString("NRO_INGRESO_ALMACEN")%></td>
                                <td style="border-bottom:1px solid #cccccc;padding:7PX"><%=f.format(rs.getDate("FECHA_INGRESO_ALMACEN"))%></td>
                                <td style="border-bottom:1px solid #cccccc;padding:7PX"><%=rs.getString("ETIQUETA")%></td>
                                <td style="border-bottom:1px solid #cccccc;padding:7PX"><%=rs.getString("NOMBRE_MATERIAL")%></td>
                                <td style="border-bottom:1px solid #cccccc;padding:7PX"><%=rs.getString("ABREVIATURA")%></td>
                                <td style="border-bottom:1px solid #cccccc;padding:7PX;font-size:11px;color:blue"><%=formato.format(rs.getDouble("CANTIDAD_RESTANTE")) %></td>
                                <td style="border-bottom:1px solid #cccccc;padding:7PX;"><%=formato.format(rs.getDouble("CANTIDAD_RESTANTE")*rs.getDouble("costoMes")) %></td>
                                <td style="border-bottom:1px solid #cccccc;padding:7PX"><%=(rs.getDate("FECHA_VENCIMIENTO")!=null?sdfMMyyyy.format(rs.getDate("FECHA_VENCIMIENTO")):"")%></td>
                                <td style="border-bottom:1px solid #cccccc;padding:7PX"><%=(rs.getDate("FECHA_REANALISIS")!=null?f.format(rs.getDate("FECHA_REANALISIS")):"")%></td>
                                <td style="border-bottom:1px solid #cccccc;padding:7PX;color:red"><%=rs.getString("DiferenciaDias")%></td>
                                <td style="border-bottom:1px solid #cccccc;padding:7PX;"><%=rs.getString("LOTE_MATERIAL_PROVEEDOR")%></td>
                                <td style="border-bottom:1px solid #cccccc;padding:7PX;"><%=rs.getString("PROOVEDOR")%></td>

                            </tr>
                            <%
                        }

                        }
                        catch(SQLException ex)
                        {
                            ex.printStackTrace();
                        }
                                            %>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </td>
                        </tr>
                    </tbody>
                    <tfoot>
                        <tr>
                            <td colspan="6">
                                <input type="button" class="btn"  value="Reporte Por Item" name="reporte" onclick="verReporte(form1,'reporteFechaVencimiento.jsp')" />
                                <input type="reset" class="btn"  value="Limpiar " name="reporteL" />

                            </td>
                        </tr>
                    </tfoot>

                </table>
                <br>
                <br>

                



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
        <script type="text/javascript" language="JavaScript"  src="../../../js/dlcalendar.js"></script>
    </body>
</html>