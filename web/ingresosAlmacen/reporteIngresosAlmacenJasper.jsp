<%@ page import="net.sf.jasperreports.engine.*" %>
<%@ page import="net.sf.jasperreports.engine.util.*" %>
<%@ page import="net.sf.jasperreports.engine.export.*" %>
<%@ page import="net.sf.jasperreports.j2ee.servlets.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.sql.Connection"%>
<%@ page import="com.cofar.util.*"%>
<%@ page import="com.cofar.bean.*"%>
<%@ page import="java.io.*"%>
<%@ page import="java.sql.*"%>



<%
            IngresosAlmacen ingresosAlmacen = (IngresosAlmacen) request.getSession().getAttribute("ingresosAlmacen");

            Map parameters = new HashMap();


            parameters.put("codigo", ingresosAlmacen.getCodIngresoAlmacen());
            parameters.put("dirLogoCofar", application.getRealPath("/img/cofar.png"));
            String reportFileName = application.getRealPath("/ingresosAlmacen/reporteIngreso.jasper");
            Connection con = null;
            con = Util.openConnection(con);
            
            String consulta = " select gr.nombre_grupo from grupos gr where gr.cod_grupo in (select m.cod_grupo from ingresos_almacen_detalle iad inner join materiales m on m.cod_material = iad.cod_material where iad.cod_ingreso_almacen = '" + ingresosAlmacen.getCodIngresoAlmacen() + "') ";
            System.out.println("consulta" + consulta);

            Statement st3 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs3 = st3.executeQuery(consulta);
            String nombreGrupo = "";
            while (rs3.next()) {
                nombreGrupo = nombreGrupo + "," + rs3.getString("NOMBRE_GRUPO");
            }
            rs3.close();
            st3.close();

            String reportFileName2 = application.getRealPath("");
            reportFileName2 += "/ingresosAlmacen/";

            parameters.put("grupo", nombreGrupo);
            parameters.put("SUBREPORT_DIR", reportFileName2);

            JasperPrint jasperPrint = JasperFillManager.fillReport(reportFileName, parameters, con);
            session.setAttribute(BaseHttpServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jasperPrint);
            System.out.println(jasperPrint.getLocaleCode());

            con.close();

%>

<html>
    <body bgcolor="white" onload="location='../servlets/pdf'" >
    </body>
</html>



