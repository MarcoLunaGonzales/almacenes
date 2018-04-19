package salidasAlmacenProduccion;



<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page language="java" %>
<%@ page import = "java.sql.*"%>
<%@ page import = "java.sql.DriverManager"%> 
<%@ page import = "java.sql.ResultSet"%> 
<%@ page import = "java.sql.Statement"%> 
<%@ page import = "org.joda.time.DateTime"%>
<%@ page import="com.cofar.util.*" %>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%@ page import = "java.text.DecimalFormat"%> 
<%@ page import = "java.text.NumberFormat"%> 
<%@ page import = "java.util.Locale"%> 
<%! Connection con = null;
    String CadenaAreas = "";
    String areasDependientes = "";
    String sw = "0";
%>
<%!    public String generaCadenaAreasEmpresa(String codigo) {

        try {
            con = Util.openConnection(con);
            String sql1 = "select  cod_area_inferior from areas_dependientes_inmediatas ";
            sql1 += " where cod_area_empresa=" + codigo;
            System.out.println("sql1_areadependiente:" + sql1);
            Statement st1 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs1 = st1.executeQuery(sql1);

            while (rs1.next()) {
                CadenaAreas = CadenaAreas + "," + rs1.getString("cod_area_inferior");
                generaCadenaAreasEmpresa(rs1.getString("cod_area_inferior"));

            }
            if (rs1 != null) {
                rs1.close();
                st1.close();
            }
        } catch (SQLException s) {
            s.printStackTrace();
        }
        return CadenaAreas;
    }
%> 
<%
        con = Util.openConnection(con);
%>
<%!    public int numDiasMes(int mes, int anio) {
        int dias = 31;
        switch (mes) {
            case 2:
                if (bisiesto(anio)) {
                    dias = 29;
                } else {
                    dias = 28;
                }
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                dias = 30;
                break;
        }
        return dias;
    }
%>  

<%!    public String formatoFecha2(String fecha) {
        System.out.println("fechaaaaaaa" + fecha);
        String fechaFormatoVector[] = fecha.split("/");
        System.out.println("longitud" + fechaFormatoVector.length);
        fecha = fechaFormatoVector[1] + "/" + fechaFormatoVector[0] + "/" + fechaFormatoVector[2];
        return fecha;
    }
%>
<%!    public boolean bisiesto(int anio) {
        return ((anio % 4 == 0 && anio % 100 != 0) || (anio % 400 == 0));
    }
%> 
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
        <link rel="STYLESHEET" type="text/css" href="../css/ventas.css" />
        <style>

        </style>
        <script src="../js/general.js"></script>


        <script>

            function  nueva(f) {
                var location="filtroPresupuestos1.jsf?cod_tipo_incentivo_regional="+f.cod_tipo_incentivo_regional.value;
                //nuevaVentana=nuevaVentana=window.open(location, 'popUp',
                //alert(location);
                window.open(location, 'popUp');
                /*window.open(location, 'popUp',
                    "toolbar=no,location=no,menubar=yes,resizable=yes,status=yes,scrollbar=yes,"+
                        "width=600,height=350" );*/
            }

            function busqueda(f){
                var dependencias=document.getElementById('dependencias');
                var valor=dependencias.checked;
                //alert(f.sexo.value);
                location.href="navegadorPlanillaTributaria.jsp?cod_area_empresa="+f.cod_area_empresa.value+"&valor="+valor+"&cod_planilla_trib="+f.cod_planilla_trib.value;
            }
            /*function cargar(){
             document.getElementById('dependencias').checked=<%=request.getParameter("valor")%>;
          }*/
            function incluir_dependencias(f){
                var dependencias=document.getElementById('dependencias');
                var valor=dependencias.checked;
                //alert(f.sexo.value);
                //alert(dependencias.checked);
                //dependencias.checked=valor;
                location.href="navegadorPlanillaTributaria.jsp?cod_area_empresa="+f.cod_area_empresa.value+"&valor="+valor+"&cod_planilla_trib="+f.cod_planilla_trib.value;
                //alert(cod_area_empresa);
                //alert(valor);
            }
            function cancelar(){
                location.href="navegadorTipoIncentivoCobranza.jsf";
            }

            /**
             * calcular
             */
            function calcular(f) {
                codigo=f.cod_tipo_incentivo_regional.value;
                location="rptCalculoMontoComisionCatA.jsf?cod_tipo_incentivo_regional="+codigo+"&nombre_gestion="+f.nombre_gestion.value+"&nombre_mes="+f.nombre_mes.value;
            }

            function eliminar1(f){
                var count1=0;
                var elements1=document.getElementById(f);
                var rowsElement1=elements1.rows;
                //alert(rowsElement1.length);
                for(var i=1;i<rowsElement1.length;i++){
                    var cellsElement1=rowsElement1[i].cells;
                    var cel1=cellsElement1[0];
                    if(cel1.getElementsByTagName('input').length>0){
                        if(cel1.getElementsByTagName('input')[0].type=='checkbox'){
                            if(cel1.getElementsByTagName('input')[0].checked){
                                count1++;
                            }
                        }
                    }

                }
                //alert(count1);
                if(count1==0){
                    alert('No escogio ningun registro');
                    return false;
                }else{


                    if(confirm('Desea Eliminar el Registro')){
                        if(confirm('Esta seguro de Eliminar el Registro')){
                            var count=0;
                            var elements=document.getElementById(nametable);
                            var rowsElement=elements.rows;

                            for(var i=0;i<rowsElement.length;i++){
                                var cellsElement=rowsElement[i].cells;
                                var cel=cellsElement[0];
                                if(cel.getElementsByTagName('input').length>0){
                                    if(cel.getElementsByTagName('input')[0].type=='checkbox'){
                                        if(cel.getElementsByTagName('input')[0].checked){
                                            count++;
                                        }
                                    }
                                }

                            }
                            if(count==0){
                                //alert('No escogio ningun registro');
                                return false;
                            }
                            //var cantidadeliminar=document.getElementById('form1:cantidadeliminar');
                            //cantidadeliminar.value=count;
                            return true;
                        }else{
                            return false;
                        }
                    }else{
                        return false;
                    }
                }
            }



            function eliminar(f){
                //alert(f.check.checked);
                //alert(f.length);
                var codigo=new Array();
                var c=0;
                for(var i=0;i<f.length;i++){
                    if(f.elements[i].checked==true){
                        //alert('entroor');
                        codigo[c]=f.elements[i].value;
                        c++;
                    }
                }
                if(c==0){
                    alert('No escogio ningun registro');
                    return false;
                }else{
                    if(confirm('Desea Eliminar el Registro')){
                        if(confirm('Esta seguro de Eliminar el Registro')){
                            //alert(codigo);
                            location.href="guardarEliminarTipoIncentivoDetalle.jsf?cod_tipo_incentivo_regional="+f.cod_tipo_incentivo_regional.value+"&nombre_gestion="+f.nombre_gestion.value+"&nombre_mes="+f.nombre_mes.value+"&codigoTipo="+codigo;
                            //alert(codigo);

                        }else{
                            return false;
                        }
                    }else{
                        return false;
                    }

                }

            }
        </script>
    </head>
    <body >
        <form name="form1" action="filtroPresupuestos1.jsf" method="post" target="_blank" >

            <%!    public double redondear(double numero, int decimales) {
        return Math.round(numero * Math.pow(10, decimales)) / Math.pow(10, decimales);
    }
            %>
            <%
        String sql = "";
        String codigo = request.getParameter("codigo");

        System.out.println("codigo:" + codigo);

        String sql_sal = " select s.NRO_SALIDA_ALMACEN,s.COD_LOTE_PRODUCCION,s.FECHA_SALIDA_ALMACEN,c.nombre_prod_semiterminado,";
        sql_sal += " a.NOMBRE_AREA_EMPRESA,(select t.NOMBRE_TIPO_SALIDA_ALMACEN from TIPOS_SALIDAS_ALMACEN t where t.COD_TIPO_SALIDA_ALMACEN=s.COD_TIPO_SALIDA_ALMACEN )";
        sql_sal += " from SALIDAS_ALMACEN s ,AREAS_EMPRESA a, COMPONENTES_PROD c";
        sql_sal += " where s.COD_SALIDA_ALMACEN="+codigo+" and s.COD_AREA_EMPRESA=a.COD_AREA_EMPRESA and s.COD_PROD=c.COD_COMPPROD";
        System.out.println("sql_sal: " + sql_sal);
        Statement st_sal = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs_sal = st_sal.executeQuery(sql_sal);
        String nroSal = "";
        String lote = "";
        String fechaSal = "";
        String nomProd = "";
        String areaEmp = "";
        String tipoSal = "";
        while (rs_sal.next()) {
            nroSal = rs_sal.getString(1);
            lote = rs_sal.getString(2);
            fechaSal = rs_sal.getString(3);
            nomProd = rs_sal.getString(4);
            areaEmp = rs_sal.getString(5);
            tipoSal = rs_sal.getString(6);
        }



            %>

            <div align="center" class="outputText2">
            <br>

            <h2>SALIDA DE ALMACEN</h2>
            <br>
            <br>
            <p>Nro. Salida : <%=nroSal%>  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Lote : <%=lote%></p>
            <p>Fecha Salida : <%=fechaSal%> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  Producto : <%=nomProd%></p>
            <p>Area/Dpto. Destino  : <%=areaEmp%> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  Tipo Salida : <%=tipoSal%></p>
            <br><br>


            <table width="70%" align="center" class="outputText2" cellpadding="0" cellspacing="0">
                <tr class="headerClassACliente">

                    <th   height="35 px" align="center" style="border-left: solid #ffffff 1px;">Codigo</th>
                    <th  align="center" style="border-left: solid #ffffff 1px;">Item</th>
                    <th  align="center" style="border-left: solid #ffffff 1px;">Cantidad</th>
                    <th  align="center" style="border-left: solid #ffffff 1px;">
                        <table width="100%" align="center" class="outputText2" cellpadding="0" cellspacing="0">
                            <tr class="headerClassACliente">

                                <th width="60%" align="center" style="border-left: solid #ffffff 1px;">Lote</th>
                                <th width="40%" align="center" style="border-left: solid #ffffff 1px;">Cantidad</th>

                            </tr>
                        </table>
                    </th>

                    <th  align="center" style="border-left: solid #ffffff 1px;">Unid.</th>
                </tr>

                <%

        try {

            sql = " select m.CODIGO_ANTIGUO,m.NOMBRE_MATERIAL, id.LOTE_MATERIAL_PROVEEDOR,sd.CANTIDAD,id.COD_INGRESO_ALMACEN,sd.COD_SALIDA_ALMACEN";
            sql += " from materiales m ,  SALIDAS_ALMACEN_DETALLE_INGRESO sd,INGRESOS_ALMACEN_DETALLE_ESTADO id";
            sql += " where m.COD_MATERIAL=sd.COD_MATERIAL and sd.COD_SALIDA_ALMACEN="+codigo+"";
            sql += " and sd.CANTIDAD>0 and id.COD_INGRESO_ALMACEN=sd.COD_INGRESO_ALMACEN and id.ETIQUETA=sd.ETIQUETA";
            sql += " and id.COD_MATERIAL=m.COD_MATERIAL";

            sql = "select m.CODIGO_ANTIGUO,m.NOMBRE_MATERIAL, sd.CANTIDAD_SALIDA_ALMACEN,m.COD_MATERIAL,u.ABREVIATURA";
            sql += " from materiales m ,  SALIDAS_ALMACEN_DETALLE sd,UNIDADES_MEDIDA u";
            sql += " where m.COD_MATERIAL=sd.COD_MATERIAL and sd.COD_SALIDA_ALMACEN="+codigo+" and u.COD_UNIDAD_MEDIDA=sd.COD_UNIDAD_MEDIDA";

            System.out.println("sql" + sql);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(sql);
            int cont = 0;
            while (rs.next()) {
                %>
                <tr  class="border" title=" hola">

                    <td height="20px" style="border-bottom: solid #cccccc 1px;border-left: solid #cccccc 1px;;padding:5px;"><%=rs.getString(1)%></td>
                    <td style="border-bottom: solid #cccccc 1px;border-left: solid #cccccc 1px;padding:5px;" >&nbsp;<%=rs.getString(2)%></td>
                    <td style="border-bottom: solid #cccccc 1px;border-left: solid #cccccc 1px;padding:5px;" >&nbsp;<%=rs.getString(3)%></td>
                    <td style="border-bottom: solid #cccccc 1px;border-left: solid #cccccc 1px;padding:5px;" >
                        <table width="100%" align="center" class="outputText2" cellpadding="0" cellspacing="0">
                            <%

                                String sql_lote = "select s.CANTIDAD,i.LOTE_MATERIAL_PROVEEDOR";
                                sql_lote += " from SALIDAS_ALMACEN_DETALLE_INGRESO s,INGRESOS_ALMACEN_DETALLE_ESTADO i";
                                sql_lote += " where  s.CANTIDAD>0 and s.COD_INGRESO_ALMACEN=i.COD_INGRESO_ALMACEN and s.ETIQUETA=i.ETIQUETA";
                                sql_lote += " and s.COD_SALIDA_ALMACEN="+codigo+" and i.cod_material=" + rs.getString(4) + " and s.COD_MATERIAL=i.COD_MATERIAL";
                                System.out.println("sql_lote: " + sql_lote);
                                Statement st_lote = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                                ResultSet rs_lote = st_lote.executeQuery(sql_lote);
                                while (rs_lote.next()) {
                            %>
                            <tr>
                                <td width="60%" style="border-bottom: solid #cccccc 1px;border-left: solid #cccccc 1px;padding:5px;" >&nbsp;<%=rs_lote.getString(2)%></td>
                                <td width="40%" align="right" style="border-bottom: solid #cccccc 1px;border-left: solid #cccccc 1px;padding:5px;" >&nbsp;<%=rs_lote.getString(1)%></td>
                            </tr>
                            <%
                                }
                            %>
                        </table>
                    </td>
                    <td style="border-bottom: solid #cccccc 1px;border-left: solid #cccccc 1px;padding:5px;" ><%=rs.getString(5)%></td>
                </tr>
                <%
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

                %>



            </table


            <br><br>

        </form>
    </body>
</html>
