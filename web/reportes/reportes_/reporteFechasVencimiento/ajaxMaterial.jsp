package reportes.reporteFechasVencimiento;


<%@page contentType="text/xml"%>
<%@page import="com.cofar.util.CofarConnection"%>
<%@page import="java.sql.*"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.cofar.util.*"%>
<%@ page import = "java.text.DecimalFormat"%>
<%@ page import = "java.text.NumberFormat"%>
<%@ page import = "java.util.Locale"%>

<%@ page import="java.text.SimpleDateFormat" %>
<%
out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
SimpleDateFormat sdfMMyyyy =new SimpleDateFormat("MM/yyyy");
String codCapitulo=request.getParameter("codCapitulo")!="0"?request.getParameter("codCapitulo"):"";
String codGrupo=request.getParameter("codGrupo")!="0"?request.getParameter("codGrupo"):"";
String nombreMaterial=request.getParameter("nombreMaterial")!="0"?request.getParameter("nombreMaterial"):"";
System.out.println("codCapitulo:"+codCapitulo);
System.out.println("codGrupo:"+codGrupo);
System.out.println("nombreMaterial:"+nombreMaterial);

%>

<table id='dataMateriales' class='outputText0' style='border : solid #000000 1px;' cellpadding='0' cellspacing='0'  >
                            <tr bgcolor="#cccccc">
                                <th style="border-bottom:1px solid #cccccc;padding:7PX">Nro. Ingreso</th>
                                <th style="border-bottom:1px solid #cccccc;padding:7PX">Fecha Ingreso</th>
                                <th style="border-bottom:1px solid #cccccc;padding:7PX">Etiqueta</th>
                                <th style="border-bottom:1px solid #cccccc;padding:7PX">Item</th>
                                <th style="border-bottom:1px solid #cccccc;padding:7PX">Unidad</th>
                                <th style="border-bottom:1px solid #cccccc;padding:7PX">Cantidad</th>
                                <th style="border-bottom:1px solid #cccccc;padding:7PX">Fecha Venc.</th>
                                <th style="border-bottom:1px solid #cccccc;padding:7PX">Fecha Reanalisis</th>
                                <th style="border-bottom:1px solid #cccccc;padding:7PX">Dias</th>
                            </tr>
                            <%
        String sql_reporte = " SELECT M.NOMBRE_MATERIAL,IAD.FECHA_VENCIMIENTO,IAD.FECHA_REANALISIS,IAD.CANTIDAD_RESTANTE,DateDiff(day, IAD.FECHA_VENCIMIENTO, GETDATE()) as DiferenciaDias,";
        sql_reporte += " IA.NRO_INGRESO_ALMACEN,ia.FECHA_INGRESO_ALMACEN,iad.ETIQUETA,u.ABREVIATURA,ia.cod_ingreso_almacen,iad.cod_material,id.COD_UNIDAD_MEDIDA";
        sql_reporte += " FROM INGRESOS_ALMACEN IA, INGRESOS_ALMACEN_DETALLE_ESTADO IAD, MATERIALES M, UNIDADES_MEDIDA U, INGRESOS_ALMACEN_DETALLE ID";
        sql_reporte += " WHERE IA.COD_INGRESO_ALMACEN=ID.COD_INGRESO_ALMACEN AND IAD.COD_MATERIAL=M.COD_MATERIAL AND IAD.CANTIDAD_RESTANTE>0.01";
        sql_reporte += " AND IA.COD_ESTADO_INGRESO_ALMACEN=1 AND IA.ESTADO_SISTEMA=1 AND U.COD_UNIDAD_MEDIDA=ID.COD_UNIDAD_MEDIDA";
        sql_reporte += " AND IAD.COD_INGRESO_ALMACEN=ID.COD_INGRESO_ALMACEN AND IAD.COD_MATERIAL=ID.COD_MATERIAL and ia.COD_ALMACEN in(1,2)";
        if(!nombreMaterial.equals("0")){
            sql_reporte += " and m.nombre_material like '%"+nombreMaterial+"%'";
        }
        
        sql_reporte += " and m.COD_GRUPO in(SELECT COD_GRUPO FROM GRUPOS G WHERE cod_estado_registro=1";
        if(!codCapitulo.equals("0")){
            sql_reporte +="  and G.cod_capitulo="+codCapitulo+"";
        }
        if(!codGrupo.equals("0")){
           sql_reporte +="  and G.cod_grupo="+codGrupo+"";
        }
        
         sql_reporte +="  )";

        sql_reporte += " AND IAD.FECHA_VENCIMIENTO-180<= GETDATE() order by IAD.FECHA_VENCIMIENTO";
        System.out.println("sql_reporte jax: " + sql_reporte);
        Connection con=null;
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
                <td style="border-bottom:1px solid #cccccc;padding:7PX"><%=sdfMMyyyy.format(rs.getDate("FECHA_VENCIMIENTO"))%></td>
                <td style="border-bottom:1px solid #cccccc;padding:7PX"><%=f.format(rs.getDate("FECHA_REANALISIS"))%></td>
                <td style="border-bottom:1px solid #cccccc;padding:7PX;color:red"><%=rs.getString("DiferenciaDias")%>  </td>

            </tr>
            <%
        }
                            %>

                        </table>
