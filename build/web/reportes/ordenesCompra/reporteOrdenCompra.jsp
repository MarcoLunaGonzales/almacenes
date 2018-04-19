<%@ page import="net.sf.jasperreports.engine.*" %>
<%@ page import="net.sf.jasperreports.engine.util.*" %>
<%@ page import="net.sf.jasperreports.engine.export.*" %>
<%@ page import="net.sf.jasperreports.j2ee.servlets.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<%@ page import="com.cofar.util.*" %>
<%@ page import="com.cofar.web.*" %>
<%@ page import="java.sql.*,java.io.*" %>
<%@ page import="java.text.*" %>
<%! public String[] literal(String codigo, Connection con) {
//String literal="";
        double total = 0; 
        double subtotal = 0;
        double total_detalle = 0;
        String bs = "0";
        String sus = "0";
        int decimal = 0;
        int entero = 0;
        int sw_cargos_extra = 0;
        int personal = 0;
        String texto = "0";
        String titulo = "REQUERIMIENTO DE COMPRAS";

        String nulo = "";
        int sw = 0;
        String aprobado1 = "";
        String aprobado2 = "";
        String aprobado3 = "";
        String cargo1 = "";
        String cargo2 = "";
        String cargo3 = "";
        double tipo_cambio = 0;
        String fecha1 = "";
        String fecha2 = "";
        String fecha3 = "";
        String fecha_cambio_moneda = "";
        String descFechaEntrega = "";
        String fechaEntrega = "";
        int codCotizacion = 0;
        int codSolicitud = 0;
        String solicitante = "";
        String centroCostos = "";
        int cod_tipo_compra = 0;
        int cod_moneda = 0;
        String nombre_moneda = "";
        int codEstadoCompra = 0;
        SimpleDateFormat formato = new SimpleDateFormat("MM/dd/yyyy");
        int cod_personal1 = 0;
        int cod_personal2 = 0;
        int cod_personal3 = 0;
        String v[] = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
        try {
            String sql = "select o.FECHA_EMISION,o.cod_tipo_compra,o.cod_moneda, m.nombre_moneda,o.COD_ESTADO_COMPRA,DESC_FECHA_ENTREGA,FECHA_ENTREGA,O.COD_COTIZACION from ORDENES_COMPRA o, monedas m where m.cod_moneda=o.cod_moneda and o.cod_orden_compra=" + codigo;
            System.out.println(sql);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                fecha_cambio_moneda = formato.format(rs.getDate(1));
                cod_tipo_compra = rs.getInt(2);
                cod_moneda = rs.getInt(3);
                nombre_moneda = rs.getString(4);
                codEstadoCompra = rs.getInt(5);
                descFechaEntrega = rs.getString(6);
                fechaEntrega = rs.getString(7);
                codCotizacion = rs.getInt(8);
            }
            //saca los nombres de los que aprueban la orden de compra
            sql = "";
            sql = " select na.fecha_cambioestado_compra,na.nivel_aprobacion,p.cod_personal,  ";
            sql += " (select ga.abreviatura_grado_academico from grados_academicos ga where ga.cod_grado_academico=p.cod_grado_academico)as grado, p.NOMBRE_PILA+' '+p.AP_PATERNO_PERSONAL as personal,";
            sql += " (select c.descripcion_cargo from cargos c where c.codigo_cargo=p.codigo_cargo)as cargo,";
            sql += " isnull((select cod_personal from firmas_digitales f where f.cod_personal=p.cod_personal),0)as firma";
            sql += " from personal p,niveles_aprobacion_compra na where p.cod_personal=na.cod_personal and na.cod_orden_compra=" + codigo + " and na.estado_aprobacion_compra=1 order by nivel_aprobacion desc";
            System.out.println(sql);
            st = con.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                sw = sw + 1;
                if (sw == 1) {
                    if (!rs.getString(1).equals("")) {
                        fecha1 = rs.getString(1);
                    } else {
                        fecha1 = "";
                    }
                    aprobado1 = rs.getString(4) + " " + rs.getString(5);
                    cargo1 = rs.getString(6);
                    cod_personal1 = rs.getInt(7);
                }
                if (sw == 2) {
                    if (!rs.getString(1).equals("")) {
                        fecha2 = rs.getString(1);
                    } else {
                        fecha2 = "";
                    }
                    aprobado2 = rs.getString(4) + " " + rs.getString(5);
                    cargo2 = rs.getString(6);
                    cod_personal2 = rs.getInt(7);
                }
                if (sw == 3) {
                    if (!rs.getString(1).equals("")) {
                        java.util.Date fechaAux = rs.getDate(1);
                        fecha_cambio_moneda = formato.format(fechaAux);
                        fecha3 = formato.format(fechaAux);
                    } else {
                        fecha3 = "";
                    }
                    aprobado3 = rs.getString(4) + " " + rs.getString(5);
                    cargo3 = rs.getString(6);
                    cod_personal3 = rs.getInt(7);
                }
            }
            //************
            nulo = "";
            if (cod_tipo_compra < 3) {
                sql = "";
                sql = "select isnull(sum(precio_total),0)AS TOTAL from ordenes_compra_detalle where cod_orden_compra=" + codigo;
                System.out.println(sql);
                st = con.createStatement();
                rs = st.executeQuery(sql);
                while (rs.next()) {
                    total = rs.getDouble(1);
                }
                subtotal = total;
                sql = "";
                sql = "select isnull(sum(precio_cargoextra),0)as total from ordencompra_cargosextra where COD_TIPO_CARGO_EXTRA=1 and  cod_orden_compra=" + codigo;
                System.out.println(sql);
                st = con.createStatement();
                rs = st.executeQuery(sql);
                sw_cargos_extra = 0;
                while (rs.next()) {
                    sw_cargos_extra = 1;
                    total = total + rs.getDouble(1);
                    total_detalle = subtotal;
                }
                //**calcula si la moneda es en Bs. o $us
                if (cod_moneda == 1) {
                    bs = String.valueOf(total);
                    sus = "0";
                } else {
                    sql = "";
                    sql = "select top 1 cambio from tipocambios_moneda where cod_moneda=" + cod_moneda + " and fecha<='" + fecha_cambio_moneda + "' order by fecha desc";
                    System.out.println(sql);
                    st = con.createStatement();
                    rs = st.executeQuery(sql);
                    while (rs.next()) {
                        tipo_cambio = rs.getDouble(1);
                    }
                    sus = String.valueOf(total);
                    total = total * tipo_cambio;
                    bs = String.valueOf(total);
                }
                //*********
                //literales
                entero = (int) total;
                total = total - (double) entero;
                decimal = (int) Math.round(total * 100);

                NumberFormat nf1 = NumberFormat.getNumberInstance(Locale.ENGLISH);
                DecimalFormat form1 = (DecimalFormat) nf1;
                form1.applyPattern("#,###.00");
                String totalfinaltext = form1.format(entero);
                LiteralNumeral literal = new LiteralNumeral();
                texto = "Son: " + literal.billones((int) entero);
                if (decimal > 0) {
                    texto += " " + String.valueOf(decimal) + "/100 Boliviano(s)";
                } else {
                    texto += " 00/100 Boliviano(s)";
                }
                //*************
            } else {
                sql = "";
                sql = "select isnull(sum(precio_total),0)AS TOTAL from ordenes_compra_detalle where cod_orden_compra=" + codigo;
                System.out.println(sql);
                st = con.createStatement();
                rs = st.executeQuery(sql);
                while (rs.next()) {
                    total = rs.getDouble(1);
                }
                subtotal = total;
                sql = "";
                sql = "select isnull(sum(precio_cargoextra),0)as total from ordencompra_cargosextra where COD_TIPO_CARGO_EXTRA=1 and  cod_orden_compra=" + codigo;
                System.out.println(sql);
                st = con.createStatement();
                rs = st.executeQuery(sql);
                sw_cargos_extra = 0;
                while (rs.next()) {
                    sw_cargos_extra = 1;
                    total = total + rs.getDouble(1);
                    total_detalle = subtotal;
                }
                //**calcula si la moneda es en Bs. o $us
                if (cod_moneda == 1) {
                    bs = String.valueOf(total);
                    sus = "0";
                } else {
                    sus = String.valueOf(total);
                    bs = "0";
                }
                //literales
                entero = (int) total;
                total = total - (double) entero;
                decimal = (int) Math.round(total * 100);

                NumberFormat nf1 = NumberFormat.getNumberInstance(Locale.ENGLISH);
                DecimalFormat form1 = (DecimalFormat) nf1;
                form1.applyPattern("#,###.00");
                String totalfinaltext = form1.format(entero);
                LiteralNumeral literal = new LiteralNumeral();
                texto = "Son: " + literal.billones((int) entero);
                if (decimal > 0) {
                    texto += " " + String.valueOf(decimal) + "/100 " + nombre_moneda;
                } else {
                    texto += " 00/100 " + nombre_moneda;
                }
            }
            if (codEstadoCompra == 2 || codEstadoCompra == 5 || codEstadoCompra == 6 || codEstadoCompra == 7 || codEstadoCompra == 13 || codEstadoCompra == 14 || codEstadoCompra == 15 || codEstadoCompra == 16 || codEstadoCompra == 17 || codEstadoCompra == 18) {
                if (cod_tipo_compra == 2) {
                    titulo = "ORDEN DE PRESTACIÓN DE SERVICIOS";
                }
                if (cod_tipo_compra == 1) {
                    titulo = "ORDEN DE COMPRA LOCAL";
                }
                if (cod_tipo_compra == 3) {
                    titulo = "ORDEN DE COMPRA IMPORTACIÓN";
                }
                if (cod_tipo_compra == 5) {
                    titulo = "ORDEN DE COMPRA TRANSFERENCIA";
                }
                if (cod_tipo_compra == 4) {
                    titulo = "ORDEN DE COMPRA EN CONSIGNACION";
                }
                System.out.println("nombre: " + titulo);
            }

            // recupera el nombre del solicitante inicial
            solicitante = "";
            if (codCotizacion > 0) {
                sql = "";
                sql = "select isnull(cod_solicitud_compra,0)as cod_solicitud_compra from cotizaciones where cod_cotizacion=" + codCotizacion;
                System.out.println(sql);
                st = con.createStatement();
                rs = st.executeQuery(sql);
                while (rs.next()) {
                    codSolicitud = rs.getInt(1);
                }
                // saca la solicitud inicical
                if (codSolicitud > 0) {
                    int controlador = 1;
                    while (controlador == 1) {
                        sql = "";
                        sql = "select ds.cod_solicitud_compra_antigua from solicitudes_compra sc, dependencias_solicitud ds ";
                        sql += "where sc.cod_solicitud_compra=ds.cod_solicitud_compra and sc.cod_solicitud_compra=" + codSolicitud;
                        System.out.println(sql);
                        st = con.createStatement();
                        rs = st.executeQuery(sql);
                        controlador = 0;
                        while (rs.next()) {
                            codSolicitud = rs.getInt(1);
                            controlador = 1;
                        }
                    }
                    sql = "";
                    sql = "select (p.AP_PATERNO_PERSONAL +' '+ p.AP_MATERNO_PERSONAL  +' '+ p.NOMBRE_PILA) as solicitante from solicitudes_compra s, personal p where p.cod_personal=s.cod_personal and s.COD_SOLICITUD_COMPRA=" + codSolicitud;
                    System.out.println(sql);
                    st = con.createStatement();
                    rs = st.executeQuery(sql);
                    controlador = 0;
                    while (rs.next()) {
                        solicitante = rs.getString(1);
                        controlador = 1;
                    }
                }
                // identifica al solicitante original
            }
            // fin recupera el nombre del solicitante inicial

            // recupera el nombre del centro de costos
            centroCostos = "";
            if (!codigo.equals("0")) {
                sql = "";
                sql = "SELECT ISNULL(costos.NOMBRE_AREA_EMPRESA, '')"
                        + "FROM CC_COMPRAS_CRONOS compras INNER JOIN CC_COSTOS_CRONOS costos "
                        + "ON compras.COD_COSTOS_COMPRA=costos.COD_AREA_EMPRESA "
                        + "WHERE compras.COD_ORDEN_COMPRA =" + codigo;
                System.out.println(sql);
                st = con.createStatement();
                rs = st.executeQuery(sql);

                while (rs.next()) {
                    centroCostos = rs.getString(1);

                }
                // identifica al solicitante original
            }
            System.out.println("centroCostos:" + centroCostos);
            if (centroCostos.equals("")) {
                sql = "";
                sql = "SELECT ISNULL(areas.NOMBRE_AREA_EMPRESA, '') "
                        + " FROM DISTRIBUCION_GASTOS_COMPRAS gastos "
                        + " INNER JOIN AREAS_EMPRESA areas ON gastos.COD_DEPARTAMENTO = areas.COD_AREA_EMPRESA "
                        + " WHERE gastos.COD_ORDEN_COMPRA = " + codigo;
                System.out.println(sql);
                st = con.createStatement();
                rs = st.executeQuery(sql);

                while (rs.next()) {
                    centroCostos = rs.getString(1);

                }
                // identifica al solicitante original
            }
            System.out.println("centroCostos:" + centroCostos);

            centroCostos = (centroCostos == null ? "" : centroCostos);
            // fin recupera el nombre del solicitante inicial

            if (cod_tipo_compra == 3) {
                System.out.println("ENTRO POR 3");
                v[0] = String.valueOf(cod_tipo_compra);
                v[1] = texto;
                v[2] = bs;
                v[3] = sus;
                if (sw_cargos_extra == 1) {
                    nulo = "Total Detalle";
                    v[4] = String.valueOf(total_detalle);
                    v[5] = nulo;
                } else {
                    v[4] = nulo;
                    v[5] = nulo;
                }
                v[6] = aprobado1;
                v[7] = aprobado2;
                v[8] = aprobado3;
                v[9] = cargo1;
                v[10] = cargo2;
                v[11] = cargo3;
                v[12] = String.valueOf(cod_personal1);
                v[13] = String.valueOf(cod_personal2);
                v[14] = String.valueOf(cod_personal3);
                v[15] = String.valueOf(sw_cargos_extra);
                v[16] = fecha1;
                v[17] = fecha2;
                v[18] = fecha3;
                v[19] = titulo;
                v[20] = solicitante;
                v[21] = centroCostos == null ? "" : centroCostos;
            } else {
                if (cod_tipo_compra == 1 || cod_tipo_compra == 4 || cod_tipo_compra == 5) {
                    System.out.println("ENTRO POR 1");
                    v[0] = String.valueOf(cod_tipo_compra);
                    v[1] = texto;
                    if (!descFechaEntrega.equals("")) {
                        v[2] = descFechaEntrega;
                    } else {
                        v[2] = fechaEntrega;
                    }
                    v[3] = bs;
                    v[4] = sus;
                    v[5] = aprobado1;
                    v[6] = aprobado2;
                    v[7] = aprobado3;
                    v[8] = cargo1;
                    v[9] = cargo2;
                    v[10] = cargo3;
                    v[11] = String.valueOf(tipo_cambio);
                    v[12] = String.valueOf(cod_personal1);
                    v[13] = String.valueOf(cod_personal2);
                    v[14] = String.valueOf(cod_personal3);
                    if (sw_cargos_extra == 1) {
                        nulo = "Total Detalle";
                        v[15] = String.valueOf(total_detalle);
                        v[16] = nulo;
                    } else {
                        v[15] = nulo;
                        v[16] = nulo;
                    }
                    v[17] = String.valueOf(sw_cargos_extra);
                    v[18] = fecha1;
                    v[19] = fecha2;
                    v[20] = fecha3;
                    v[21] = titulo;
                    v[22] = solicitante;
                    v[23] = centroCostos;
                } else {
                    if (cod_tipo_compra == 2) {
                        System.out.println("ENTRO POR 2");
                        v[0] = String.valueOf(cod_tipo_compra);
                        v[1] = texto;
                        if (!descFechaEntrega.equals("")) {
                            v[2] = descFechaEntrega;
                        } else {
                            v[2] = fechaEntrega;
                        }
                        v[3] = bs;
                        v[4] = sus;
                        v[5] = aprobado1;
                        v[6] = aprobado2;
                        v[7] = aprobado3;
                        v[8] = cargo1;
                        v[9] = cargo2;
                        v[10] = cargo3;
                        v[11] = String.valueOf(tipo_cambio);
                        v[12] = String.valueOf(cod_personal1);
                        v[13] = String.valueOf(cod_personal2);
                        v[14] = String.valueOf(cod_personal3);
                        v[15] = fecha1;
                        v[16] = fecha2;
                        v[17] = fecha3;
                        if (sw_cargos_extra == 1) {
                            nulo = "Total Detalle";
                            v[18] = String.valueOf(total_detalle);
                            v[19] = nulo;
                        } else {
                            v[18] = nulo;
                            v[19] = nulo;
                        }
                        v[20] = String.valueOf(sw_cargos_extra);
                        v[21] = titulo;
                        v[22] = solicitante;
                        v[23] = centroCostos;
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return v;

    }
%>

<%
    try {
        Connection con = null;
        con = Util.openConnection(con);
        String codOrdenCompra = request.getParameter("codigo");
        String literal[] = literal(codOrdenCompra, con);
        String reporte = "";
        if (literal[0].equals("1") || literal[0].equals("4") || literal[0].equals("5")) {
            reporte = "ordenCompraLocal.jasper";
        } else {
            if (literal[0].equals("2")) {
                reporte = "ordenCompraPrestacion.jasper";
            } else {
                reporte = "ordenCompraImportacion.jasper";
            }
        }
        System.out.println(literal[0]);
        String reportFileName = application.getRealPath("reportes\\ordenesCompra\\" + reporte);
        String a = application.getRealPath("");
        System.out.println("parametro2VVVVVVVVVVVVVVVV:  " + a + " " + reportFileName);
        File reportFile = new File(reportFileName);
        if (!reportFile.exists()) {
            throw new JRRuntimeException("El Reporte no existe");
        }
        Map parameters = new HashMap();
        parameters.put("codOrdenCompra", Integer.parseInt(codOrdenCompra));
        parameters.put("SUBREPORT_DIR", a + "\\reportes\\ordenesCompra\\");
        System.out.println("codOrdenCompra  1   " + codOrdenCompra);

        System.out.println("literal[1] " + literal[1]);
        System.out.println("literal[1] " + literal[0]);
        if (literal[0].equals("3")) {
            parameters.put("texto", literal[1]);
            parameters.put("bs", Double.parseDouble(literal[2]));
            parameters.put("sus", Double.parseDouble(literal[3]));
            parameters.put("total_detalle", Double.parseDouble(literal[4]));
            parameters.put("nulo", literal[5]);
            parameters.put("aprobado1", literal[6]);
            parameters.put("aprobado2", literal[7]);
            parameters.put("aprobado3", literal[8]);
            parameters.put("cargo1", literal[9]);
            parameters.put("cargo2", literal[10]);
            parameters.put("cargo3", literal[11]);
            parameters.put("cod_personal1", Integer.parseInt(literal[12]));
            parameters.put("cod_personal2", Integer.parseInt(literal[13]));
            parameters.put("cod_personal3", Integer.parseInt(literal[14]));
            parameters.put("sw_cargos_extra", Integer.parseInt(literal[15]));
            parameters.put("fecha1", literal[16]);
            parameters.put("fecha2", literal[17]);
            parameters.put("fecha3", literal[18]);
            parameters.put("titulo", literal[19]);
            parameters.put("solicitante", literal[20]);
            parameters.put("centroCosto", literal[21]);
            parameters.put("dirLogoCofar", application.getRealPath("/img/cofar.png"));
        } else {
            if (literal[0].equals("1") || literal[0].equals("4") || literal[0].equals("5")) {
                parameters.put("texto", literal[1]);
                parameters.put("fechaEntrega", literal[2]);
                parameters.put("bs", Double.parseDouble(literal[3]));
                parameters.put("sus", Double.parseDouble(literal[4]));
                parameters.put("aprobado1", literal[5]);
                parameters.put("aprobado2", literal[6]);
                parameters.put("aprobado3", literal[7]);
                parameters.put("cargo1", literal[8]);
                parameters.put("cargo2", literal[9]);
                parameters.put("cargo3", literal[10]);
                parameters.put("tipo_cambio", literal[11]);
                parameters.put("cod_personal1", Integer.parseInt(literal[12]));
                parameters.put("cod_personal2", Integer.parseInt(literal[13]));
                parameters.put("cod_personal3", Integer.parseInt(literal[14]));
                parameters.put("total_detalle", Double.parseDouble(literal[15]));
                parameters.put("nulo", literal[16]);
                parameters.put("sw_cargos_extra", Integer.parseInt(literal[17]));
                parameters.put("fecha1", literal[18]);
                parameters.put("fecha2", literal[19]);
                parameters.put("fecha3", literal[20]);
                parameters.put("titulo", literal[21]);
                parameters.put("solicitante", literal[22]);
                parameters.put("centroCosto", literal[23]);
                parameters.put("dirLogoCofar", application.getRealPath("/img/cofar.png"));
            } else {
                if (literal[0].equals("2")) {
                    parameters.put("texto", literal[1]);
                    parameters.put("fechaEntrega", literal[2]);
                    parameters.put("bs", Double.parseDouble(literal[3]));
                    parameters.put("sus", Double.parseDouble(literal[4]));
                    parameters.put("aprobado1", literal[5]);
                    parameters.put("aprobado2", literal[6]);
                    parameters.put("aprobado3", literal[7]);
                    parameters.put("cargo1", literal[8]);
                    parameters.put("cargo2", literal[9]);
                    parameters.put("cargo3", literal[10]);
                    parameters.put("tipo_cambio", literal[11]);
                    parameters.put("cod_personal1", Integer.parseInt(literal[12]));
                    parameters.put("cod_personal2", Integer.parseInt(literal[13]));
                    parameters.put("cod_personal3", Integer.parseInt(literal[14]));
                    parameters.put("fecha1", literal[15]);
                    parameters.put("fecha2", literal[16]);
                    parameters.put("fecha3", literal[17]);
                    parameters.put("total_detalle", Double.parseDouble(literal[18]));
                    parameters.put("nulo", literal[19]);
                    parameters.put("sw_cargos_extra", Integer.parseInt(literal[20]));
                    parameters.put("titulo", literal[21]);
                    parameters.put("dirLogoCofar", application.getRealPath("/img/cofar.png"));
                    parameters.put("centroCosto", literal[23]);
                }
            }
        }
        System.out.println("los parametros del reporte : " + parameters);
        JasperPrint jasperPrint = JasperFillManager.fillReport(reportFileName, parameters, con);
        session.setAttribute(BaseHttpServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jasperPrint);

%>

<html>


    <%if (literal[1].equals("0")) {%>
    <body bgcolor="white" >
        No es posible imprimir este comprobante.
        <%} else {%>
    <body bgcolor="white" onload="location = '../../servlets/pdf'">
        <%}
            } catch (Exception e) {
                e.printStackTrace();
            }
        %>
    </body>
</html>


