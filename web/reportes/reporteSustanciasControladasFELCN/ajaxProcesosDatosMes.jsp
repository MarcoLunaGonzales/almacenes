<%@page import="java.text.SimpleDateFormat"%>
<%@ page import="net.sf.jasperreports.engine.*" %>
<%@ page import="net.sf.jasperreports.engine.util.*" %>
<%@ page import="net.sf.jasperreports.engine.export.*" %>
<%@ page import="net.sf.jasperreports.j2ee.servlets.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.Date" %>

<%@ page import="java.sql.Connection"%>
<%@ page import="com.cofar.util.*"%>
<%@ page import="java.io.*"%>

<%@ page import="java.sql.*"%>   

<% 
    String codMes = request.getParameter("codMes");
    String anio = request.getParameter("anio");
    SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
    System.out.println(anio+"/"+codMes+"/01");
    Date fechaReporte=sdf.parse(anio+"/"+codMes+"/01");
    try
    {
            Connection con=null;
            con=Util.openConnection(con);
            
            StringBuilder consulta=new StringBuilder("DELETE MATERIALES_EXITENCIA_DECLARADA_MES ");
                                        consulta.append(" where mes=").append(codMes);
                                                consulta.append(" and ANIO=").append(anio).append(";");
                                        consulta.append(" INSERT INTO MATERIALES_EXITENCIA_DECLARADA_MES(COD_MATERIAL,");
                                                consulta.append(" CANTIDAD_INGRESO, CANTIDAD_SALIDA, CANTIDAD_EXISTENCIA, ANIO, MES)");
                                        consulta.append(" select msc.cod_material,isnull(ingresos.cantidadIngreso,0)*msc.VALOR_CONVERSION,isnull(salidas.salidas_total_anterior,0)*msc.VALOR_CONVERSION,");
                                                consulta.append(" (med.CANTIDAD_EXISTENCIA-isnull(salidas.salidas_total_anterior,0)*msc.VALOR_CONVERSION+isnull(ingresos.cantidadIngreso,0)*msc.VALOR_CONVERSION),");
                                                consulta.append(" DATEPART(year,'").append(sdf.format(fechaReporte)).append("'),");
                                                consulta.append(" DATEPART(month,'").append(sdf.format(fechaReporte)).append("')");

                                        consulta.append(" from MATERIALES_SUSTANCIAS_CONTROLADAS msc");
                                                consulta.append(" left join (");
                                                 consulta.append(" SELECT sum(iad.cant_total_ingreso_fisico) as cantidadIngreso,msc.cod_material");
                                                 consulta.append(" FROM INGRESOS_ALMACEN ia");
                                                                consulta.append(" inner join INGRESOS_ALMACEN_DETALLE iad on ia.COD_INGRESO_ALMACEN=iad.COD_INGRESO_ALMACEN");
                                                        consulta.append(" inner join MATERIALES_SUSTANCIAS_CONTROLADAS msc on msc.cod_material=iad.COD_MATERIAL");
                                                 consulta.append(" where ia.cod_estado_ingreso_almacen = 1 ");
                                                                consulta.append(" and ia.estado_sistema = 1 ");
                                                        consulta.append(" and ia.cod_almacen = '1' ");
                                                      consulta.append(" and ia.fecha_ingreso_almacen between '").append(sdf.format(fechaReporte)).append(" 00:00' and  dateAdd(month,1,'").append(sdf.format(fechaReporte)).append(" 00:00')");
                                                 consulta.append(" group by msc.cod_material");
                                               consulta.append(" )as ingresos on ingresos.COD_MATERIAL=msc.cod_material");
                                                consulta.append(" left join (");
                                                 consulta.append(" select sum(sad.cantidad_salida_almacen) as salidas_total_anterior,sad.COD_MATERIAL");
                                                 consulta.append(" from SALIDAS_ALMACEN sa");
                                                        consulta.append(" inner join SALIDAS_ALMACEN_DETALLE sad on sa.COD_SALIDA_ALMACEN=sad.COD_SALIDA_ALMACEN");
                                                    consulta.append(" inner join MATERIALES_SUSTANCIAS_CONTROLADAS msc on msc.cod_material=sad.COD_MATERIAL");
                                                 consulta.append(" where sa.cod_estado_salida_almacen = 1 and");
                                                       consulta.append(" sa.estado_sistema = 1 and");
                                                       consulta.append(" sa.cod_almacen = '1' and");
                                                       consulta.append(" sa.fecha_salida_almacen between '").append(sdf.format(fechaReporte)).append(" 00:00' and  dateAdd(month,1,'").append(sdf.format(fechaReporte)).append(" 00:00')");
                                                 consulta.append(" group by sad.COD_MATERIAL");
                                             consulta.append(" )as salidas on salidas.COD_MATERIAL=msc.cod_material");
                                                 consulta.append(" left outer join MATERIALES_EXITENCIA_DECLARADA_MES med on med.COD_MATERIAL=msc.cod_material");
                                                        consulta.append(" AND med.MES=DATEPART(month,DATEADD(day,-1,'").append(sdf.format(fechaReporte)).append("'))");
                                                    consulta.append(" AND med.ANIO=DATEPART(year,DATEADD(day,-1,'").append(sdf.format(fechaReporte)).append("'))");
                System.out.println("consulta registrar resultados "+consulta.toString());
                PreparedStatement pst=con.prepareStatement(consulta.toString());
                if(pst.executeUpdate()>0)System.out.println("se registraron materiales");


    }
    catch(Exception ex)
    {
        ex.printStackTrace();
    }
           
%>



