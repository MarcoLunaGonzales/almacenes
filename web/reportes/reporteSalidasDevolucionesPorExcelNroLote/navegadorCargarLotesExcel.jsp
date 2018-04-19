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
<%@ page  import="javazoom.upload.*,java.util.*,java.io.*" %>
<%@ page  import="jxl.*" %>
<%@ page  import="jxl.Sheet" %>
<%@ page  import="jxl.Workbook" %>
<%@ page import="java.text.*" %>
<script>
  
    function openPopup(codLotes){
                    window.open('reporteSalidasPorNroLoteExcel.jsf?codLotes=12','detalle'+Math.round((Math.random()*1000)),'top=50,left=200,width=800,height=500,scrollbars=1,resizable=1');
        }
</script>
<jsp:useBean id="upBean" scope="page" class="javazoom.upload.UploadBean" >
        <jsp:setProperty name="upBean" property="folderstore" />
    </jsp:useBean>
<%
SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy");
System.out.println("inicio");

SimpleDateFormat ff=new SimpleDateFormat("dd/MM/yyyy");

java.util.Date fecha=new java.util.Date();
Connection con=null;
con=Util.openConnection(con);
String path=application.getRealPath("");
String realpathX=path+"\\xls\\";
System.out.println(realpathX);%>

    <%
    String cf="0";
    String ca="0";
    String cp="0";
    String fechaInicio=form.format(new Date());
    String fechaFinal=form.format(new Date());
    String check="";
    boolean mostrar=false;
    Connection connection=null;
    connection=Util.openConnection(connection);


    if (MultipartFormDataRequest.isMultipartFormData(request)) {
        MultipartFormDataRequest mrequest = new MultipartFormDataRequest(request);
        System.out.println(request);

        String todo = null;
        if (mrequest != null) todo = mrequest.getParameter("todo");
        if ( (todo != null) && (todo.equalsIgnoreCase("upload")) ) {
            Hashtable files = mrequest.getFiles();
            if ( (files != null) && (!files.isEmpty()) ) {
                
               UploadFile file = (UploadFile) files.get("uploadfile");
            upBean.store(mrequest, "uploadfile");
            String codFilial=mrequest.getParameter("codFilial");
            String codAlmacen=mrequest.getParameter("codAlmacen");
            String codProducto=mrequest.getParameter("codProducto");
            String fecha1=mrequest.getParameter("fecha1");
            String fecha2=mrequest.getParameter("fecha2");
            String cod=mrequest.getParameter("check")==null?"2":"1";
            cf=codFilial;
            ca=codAlmacen;
            cp=codProducto;
            if(cod.equals("1"))
                {
            fechaInicio=fecha1;
            fechaFinal=fecha2;
            check="checked";
            //out.println("<script>");
            //out.println("document.getElementById('fecha2').disabled=false;");
            //out.println("document.getElementById('fecha1').disabled=false;");
            //out.println("</script>");
            }

            String  realpathY=realpathX+file.getFileName();
            Workbook work=Workbook.getWorkbook(file.getInpuStream());
            Sheet hoja=work.getSheet(0);
            int filas=hoja.getRows();
            int i=1;
            String codLotes="";
            
            
            List<String> lotes=new ArrayList<String>();
            while (i<filas) {
                if(!hoja.getCell(1,i).getContents().equals(""))lotes.add(hoja.getCell(1,i).getContents());
                i++;
            }
            session.setAttribute("listaLotes",lotes);
            session.setAttribute("codComprod",codProducto);
            session.setAttribute("codFilial",codFilial);
            session.setAttribute("codAlmacen",codAlmacen);
            
            //System.out.println("codLotes="+codLotes+"&codComprod="+codProducto+"&codFilial="+codFilial+"&codAlmacen="+codAlmacen+"&fechaInicio="+fecha1+"&fechaFinal="+fecha2+"&repFecha="+cod);
            out.println("<script>");
            
           //out.println("window.open('reporteSalidasPorNroLoteExcel.jsf?codComprod="+codProducto+"&codFilial="+codFilial+"&codAlmacen="+codAlmacen+"&fechaInicio="+fecha1+"&fechaFinal="+fecha2+"&repFecha="+cod+"&codLotes="+codLotes+"');");
           out.println("window.open('reporteSalidasPorNroLoteExcel.jsf?fechaInicio="+fecha1+"&fechaFinal="+fecha2+"&repFecha="+cod+"');");

           out.println("</script>");
       
    }
}
}


    %>



    <head>
        <link rel="STYLESHEET" type="text/css" href="../../../css/ventas.css" />
        <script>

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
        function validar(){
            var uploadfile=document.getElementById('uploadfile');
            if(uploadfile.value==''){
                alert('Por favor escoga el archivo para cargar');
                uploadfile.focus();
                return false;
            }
            var texto=uploadfile.value;
            texto=texto.substring(   texto.length-4    ,texto.length   );

            if(texto!='.xls'){
                  alert('El Archivo que escogio no esta en el formato excel');
                  return false;
            }


        }
        function inicio(){

           // window.opener.location.reload();
            window.close();
        }
        </script>
    </head>

    <%if(mostrar){%>
    <body bgcolor="#FFFFFF" text="#000000" onload="inicio();">
    <%}else{%>
    <body bgcolor="#FFFFFF" text="#000000" >
        <%}%>

        <form method="post" action="navegadorCargarLotesExcel.jsf" name="upform" enctype="multipart/form-data">
            <div align="center">
            <b>REPORTE SALIDAS Y DEVOLUCIONES POR NRO. DE LOTE A PARTIR DE EXCELcdcdcd</b>
            <br>
              <table border="0"  border="0" class="border" width="75%">
                    <tr class="headerClassACliente">
                        <td height="35px" colspan="6" >
                            <div class="outputText3" align="center">
                                Introduzca Datos
                            </div>
                        </td>

                    </tr>

                    <tr class="outputText3">
                        <td height="35px" class="">Filial</td>
                        <td class="">::</td>
                        <%
        String codProgramaProduccionPeriodoReq = "";
        if (request.getParameter("codProgramaProduccionPeriodo") != null) {
            codProgramaProduccionPeriodoReq = request.getParameter("codProgramaProduccionPeriodo");
        }
        try {
            con = Util.openConnection(con);
            System.out.println("con:::::::::::::" + con);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);




            String sql = " select cod_filial, nombre_filial from filiales where cod_estado_registro=1 ";

            System.out.println("sql filtro:" + sql);
            ResultSet rs = st.executeQuery(sql);
                        %>
                        <td class="">
                            <select style="color:blue"    name="codFilial" class="outputText3" onchange="ajaxAlmacen(upform);">
                                <%

                            out.print("<option value='0'>-SELECCIONE UNA OPCION-</option>");
                            while (rs.next()) {
                                if(rs.getString("cod_filial").equals(cf))
                                {
                                    out.print("<option value=" + rs.getString("cod_filial") + " selected >" + rs.getString("nombre_filial") + "</option>");
                                }
                                else
                                {
                                    out.print("<option value=" + rs.getString("cod_filial") + " >" + rs.getString("nombre_filial") + "</option>");
                                }
                            }%>
                            </select>
                            <!--  <input type="checkbox"  onclick="selecccionarTodo(upform)" name="chk_todoTipo" >Todo-->
                            <%
            rs.close();
            st.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
                            %>
                        </td>
                        <td class="">Almacen</td>
                        <td class="">::</td>

                        <td class="">
                            <div id="div_almacen">
                                <%
                                if(!cf.equals("0"))
                                    {
                                      String  consulta = " select cod_almacen, nombre_almacen from almacenes where cod_estado_registro=1 and cod_filial='"+cf+"' ";

                                        System.out.println("consulta Filial "+consulta);
                                        try{
                                        con=Util.openConnection(con);
                                        Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                                        ResultSet rs=st.executeQuery(consulta);
                                        out.println("<select name='codAlmacen'  class='inputText' >");
                                        out.println("<option value='0'>-TODOS-</option>");
                                        
                                        while(rs.next()){
                                            System.out.println("ca "+ca+" "+rs.getString("cod_almacen"));
                                            if(rs.getInt("cod_almacen")==Integer.valueOf(ca.trim()))
                                            {
                                                System.out.println("hi");
                                                out.println("<option value= "+rs.getString("cod_almacen")+" selected>"+rs.getString("nombre_almacen")+"</option>");
                                            }
                                            else
                                            {
                                                out.println("<option value= "+rs.getString("cod_almacen")+" >"+rs.getString("nombre_almacen")+"</option>");
                                            }
                                        }
                                        out.println("</select>");
                                        }
                                        catch(Exception ex)
                                                {ex.printStackTrace();}
                                    }
                                    else
                                    {
                                    %>
                                     <select style="color:blue"    name="codAlmacen" class="inputText" >
                                            <option value="0">-TODOS-</option>
                                     </select>
                                    <%
                                    }
                                %>
                               
                            </div>
                        </td>

                    </tr>


                    <tr class="outputText3">


                        <td class="">Producto </td>
                        <td class="">::</td>
                        <td class="">
                            <%

        try {
            con = Util.openConnection(con);
            //System.out.println("con:::::::::::::" + con);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            String sql = " select c.COD_COMPPROD,c.nombre_prod_semiterminado from COMPONENTES_PROD c order by c.nombre_prod_semiterminado          ";

            System.out.println("sql filtro:" + sql);
            ResultSet rs = st.executeQuery(sql);
                            %>

                            <select style="width:350px;color:blue" name="codProducto"  class="outputText3" >
                                <option value="0" >-TODOS-</option>
                                <%
                                                           while (rs.next()) {
                                                               if(rs.getString("COD_COMPPROD").equals(cp))
                                                               {
                                                                  out.print("<option value=" + rs.getString("COD_COMPPROD") + " selected >" + rs.getString("nombre_prod_semiterminado") + "</option>");
                                                               }
                                                               else
                                                               {
                                                                  out.print("<option value=" + rs.getString("COD_COMPPROD") + " >" + rs.getString("nombre_prod_semiterminado") + "</option>");
                                                               }
                                                           }%>
                            </select>
                            <%
            rs.close();
            st.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
                            %>

                            <%--input type="text" class="inputText" size="70" name="nombreMaterial" /--%>
                        </td>
                        <td  height="35px" class=""colspan="3"></td>
                        


                    </tr>

                    <tr class="outputText3">
                        <td height="35px" class="">Usar Fecha</td>
                        <td colspan="5" class=""><input type="checkbox" name="check" <%=check%>  onclick="click1(this.form)"></td>
                    </tr>

                    <tr class="outputText3">
                        <td  height="35px" class="">Fecha Inicio</td>
                        <td class="">::</td>


                        <td class="">

                   <input type="text" <%=(check.equals(""))?"disabled='true'":""%>  size="12" style="color:blue"   value="<%=fechaInicio%>" name="fecha1" id="fecha1" class="inputText">
                            <img id="imagenFecha1" src="../../../img/fecha.bmp">
                            <DLCALENDAR tool_tip="Seleccione la Fecha"
                                        daybar_style="background-color: DBE1E7;
                                        font-family: verdana; color:000000;"navbar_style="background-color: 7992B7; color:ffffff;"
                                        input_element_id="fecha1" click_element_id="imagenFecha1">
                            </DLCALENDAR>
                        </td>
                        <td  height="35px" class="">Fecha Final</td>
                        <td class="">::</td>
                        <td class="">
                            <input type="text"   size="12" style="color:blue" <%=(check.equals(""))?"disabled='true'":""%>    value="<%=fechaFinal%>" name="fecha2" class="inputText">
                            <img id="imagenFecha2" src="../../../img/fecha.bmp">
                            <DLCALENDAR tool_tip="Seleccione la Fecha"
                                        daybar_style="background-color: DBE1E7;
                                        font-family: verdana; color:000000;"navbar_style="background-color: 7992B7; color:ffffff;"
                                        input_element_id="fecha2" click_element_id="imagenFecha2">
                            </DLCALENDAR>


                        </td>
                    </tr>
                    <tr class="outputText3">
                         <td  height="35px" class="">Archivo Excel:</td>
                        <td colspan="5" >
                            <input type="file"       id="uploadfile" name="uploadfile" class="inputText"  value="Cargar Archivo" size="100">
                        </td>
                    </tr>
                    
                        
                          
                       
                </table>
                <br/>

                  <div class="outputText2" align="center">
                                <input type="submit" class="commandButton" value="Cargar" onclick="return validar();">
                                <input type="hidden" name="todo" value="upload">
                                

                            </div>
            </div>

        </form>
        <script type="text/javascript" language="JavaScript"  src="../../../js/dlcalendar.js"></script>
    </body>

</html>