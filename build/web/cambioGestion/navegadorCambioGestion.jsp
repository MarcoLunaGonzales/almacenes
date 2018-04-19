<%@ page import="com.cofar.web.ManagedAccesoSistema"%>
<%@ page import="java.sql.*"%>
<%@ page import="com.cofar.util.*"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%! Connection con = null;
%>
<%! String sql = "";
%>


<html>
    <head>
        <link rel="STYLESHEET" type="text/css" href="../css/ventas.css" />
        <script>
            function cambiarGestion(f){
                //var codigo=f.codGestion.value;
                //var nombreGestion=f.codGestion.selected;
                var codigo="";
                var nombreGestion="";
                var j=0;
                for(var i=0;i<=f.codGestion.options.length-1;i++)
                {	if(f.codGestion.options[i].selected)
                    {	codigo=f.codGestion.options[i].value;
                        nombreGestion=f.codGestion.options[i].innerHTML;
        				j++;
        			}
        		}                
                //location.href="navegadorCambioGestion.jsf?codGestion="+codigo;                
                location.href="navegadorCambioGestion.jsf?codGestion="+codigo+'&nombreGestion='+nombreGestion;
                alert('La nueva gestión de trabajo es: '+nombreGestion);
                //alert(window.parent.topFrame.location)
                window.parent.topFrame.location=window.parent.topFrame.location;
            }

            function actualizarCabecera(){
                alert(window.parent.topFrame.location)
                window.parent.topFrame.location=window.parent.topFrame.location;

            }
        </script>
    </head>
    <body>
        <form method="post" action="navegadorCambioGestion.jsf" name="form1">
            <%! 
                
            %>
            <div align="center">
                <table border="0" style="border:1px solid #000000"  border="0" class="tablaFiltroReporte" cellpadding="0"  cellspacing="0">
                    <tr class="headerClassACliente">
                        <td  colspan="3" >
                            <div align="center" class="outputText2">
                                Cambio de Gestión
                            </div>
                        </td>
                    </tr>

                    <tr class="outputText2">
                    <tr class="outputText2">
                        <td class="outputText2" height="25px">Gestión de Trabajo</td>
                        <td>::</td>
                        <%        
                        ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
                        String codGestionrecibida = request.getParameter("codGestion");
                        String nombreGestionrecibida = request.getParameter("nombreGestion");
                        String codGestionActiva = usuario.getGestionesGlobal().getCodGestion();
                        String nombreGestionActiva = usuario.getGestionesGlobal().getNombreGestion();
                        
                        System.out.println("codGestionActiva.................."+codGestionActiva);
                        System.out.println("codGestionrecibida.................."+codGestionrecibida);
                        System.out.println("nombreGestionActiva.................."+nombreGestionActiva);
                        if(codGestionrecibida!=null){
                            usuario.getGestionesGlobal().setCodGestion(codGestionrecibida);
                            usuario.getGestionesGlobal().setNombreGestion(nombreGestionrecibida);
                            nombreGestionActiva=nombreGestionrecibida;
                            System.out.println("valor1.................."+usuario.getGestionesGlobal().getCodGestion());
                            System.out.println("valor12.................."+usuario.getGestionesGlobal().getNombreGestion());


                         //cambiar fecha minimo gestion y fecha maximo gestion
                        String consulta = " select gest.COD_GESTION,gest.FECHA_INI,gest.FECHA_FIN from GESTIONES gest where gest.COD_GESTION = " + codGestionrecibida;
                        con = Util.openConnection(con);
                        Statement st00 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                        ResultSet rs00 = st00.executeQuery(consulta);
                        if(rs00.next()){
                            //usuario.(rs00.getDate("FECHA_INI"));
                            //usuario.setFechaMaximoGlobal(rs00.getDate("FECHA_FIN"));
                            usuario.getGestionesGlobal().setCodGestion(rs00.getString("COD_GESTION"));
                        }

                        }
                        %>
                        <td>
                            <input name="nombreGestionActiva" class="outputText3" value="<%=nombreGestionActiva%>" onfocus="actualizarCabecera();">
                        </td>
                    </tr>

                    <tr class="outputText2">
                        <td class="outputText2" height="25px">Gestiones</td>
                        <td>::</td>
                        <%
        try {
            con = Util.openConnection(con);
            System.out.println("con:::::::::::::" + con);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sql = "select cod_gestion,nombre_gestion from gestiones  order by nombre_gestion ";
            ResultSet rs = st.executeQuery(sql);
                        %>
                        <td>
                            <select name="codGestion" class="outputText3" onchange="cambiarGestion(this.form);" >
                            <%
                            String codGestion = "";                            
                            String nombreGestion = "";
                            while (rs.next()) {
                                codGestion = rs.getString("cod_gestion");
                                nombreGestion = rs.getString("nombre_gestion");
                                if (codGestion.equals(codGestionrecibida)) {
                                %>                      <option value="<%=codGestion%>" selected><%=nombreGestion%></option>
                                <%  } else {
                                %>                      <option value="<%=codGestion%>"><%=nombreGestion%></option>
                                <%  }
                            }%>
                            </select>
                            <%

        } catch (Exception e) {
        }
                            %>
                        </td>
                    </tr>
                </table>
            </div>
            <br>            
        </form>
        <script type="text/javascript" language="JavaScript"  src="../../js/dlcalendar.js"></script>
    </body>
</html>