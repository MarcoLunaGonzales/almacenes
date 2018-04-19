<%@page contentType="text/html"%>
<%@page pageEncoding="ISO-8859-1"%>
<%@ page language="java" %>
<%@ page import="com.cofar.web.*" %>
<%@ page import = "java.sql.*"%>
<%@ page import = "java.sql.DriverManager"%> 
<%@ page import = "java.sql.ResultSet"%> 
<%@ page import = "java.sql.Statement"%> 

<%@ page import = "java.text.*"%> 
<%@ page import="com.cofar.util.*" %>

<%@ page language="java" import="java.util.*" %>
<%@ page language="java" import="java.util.Date" %>
<%@ page import="com.cofar.web.*" %>


<%! Connection con=null;
%>
<%
//con=CofarConnection.getConnectionJsp();
con=Util.openConnection(con);

%>
<style type="text/css">
    .tituloCampo1{
    font-family: Verdana, Arial, Helvetica, sans-serif;
    font-size: 11px;
    font-weight: bold;
    }
    .outputText3{          
    font-family: Verdana, Arial, Helvetica, sans-serif;
    font-size: 11px;    
    }
    .inputText3{          
    font-family: Verdana, Arial, Helvetica, sans-serif;
    font-size: 11px;    
    }
    .commandButtonR{
    font-family: Verdana, Arial, Helvetica, sans-serif;
    font-size: 11px;
    width: 150px;
    height: 20px;
    background-repeat :repeat-x;
    
    background-image: url('../../img/bar3.png');
    }
</style>


<html>

    <head>
        <link rel="STYLESHEET" type="text/css" href="../../css/ventas.css" />
        <script src="../../js/general.js"></script>
        <script>
            function cancelar(){
                  // alert(codigo);
                   location='../personal_jsp/navegador_personal.jsf';
            }
            function cargarAlmacen(f){
            var codigo=f.codAreaEmpresa.value;
                location.href="filtro.jsf?codArea="+codigo;
            }
            function enviarForm(f){
                var arrayAlmacenes=new Array();
                var arrayNombreAlmacenes=new Array();
                var arrayAreasEmpresa=new Array();
                var arrayNombreAreas=new Array();
		var j=0;
		for(var i=0;i<=f.almacen.options.length-1;i++)
		{	if(f.almacen.options[i].selected)
			{	arrayAlmacenes[j]=f.almacen.options[i].value;
                                arrayNombreAlmacenes[j]=" "+f.almacen.options[i].innerHTML;
				j++;
			}
		}
                f.codAlmacen.value=arrayAlmacenes;
                f.nombreAlmacen.value=arrayNombreAlmacenes;
                 f.nombreAlmacen.value=arrayNombreAlmacenes;
                var k=0;
		for(var h=0;h<=f.areaEmpresa.options.length-1;h++)
		{	if(f.areaEmpresa.options[h].selected)
			{	arrayAreasEmpresa[k]=f.areaEmpresa.options[h].value;
                                arrayNombreAreas[k]=" "+f.areaEmpresa.options[h].innerHTML;
				k++;
			}
		}
                f.codArea.value=arrayAreasEmpresa;
                f.nombreArea.value= arrayNombreAreas;
                f.submit();
            }            
        </script>
    </head>
    <body><br>
        <h3 align="center">Existencias a una Fecha Acondicionamiento</h3>
        
        <form method="post" action="reporte.jsp" target="_blank" name="form1">
            <div align="center">
                <table border="0"  class="outputText2" style="border:1px solid #000000" cellspacing="0">
                    <tr class="headerClassACliente">
                        <td  colspan="3" >
                            <div class="outputText3" align="center">
                                Introduzca Datos
                            </div>    
                        </td>                        
                    </tr>                    
                    <tr class="outputText3">
                        <td>&nbsp;&nbsp;<b>Almacén</b></td>
                        <td>&nbsp;&nbsp;<b>::</b>&nbsp;&nbsp;</td>
                        <%
                        try{
                            
                            con=Util.openConnection(con);
                            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                            String sql="select COD_ALMACENACOND,NOMBRE_ALMACENACOND from ALMACENES_ACOND where COD_ESTADO_REGISTRO=1 ";
                            sql+=" order by NOMBRE_ALMACENACOND asc";
                            ResultSet rs = st.executeQuery(sql);
                        %> 
                        <td>
                            <select name="almacen" class="outputText3" size="6"> 
                                <%
                                String COD_ALMACENACOND="";
                                String NOMBRE_ALMACENACOND="";
                                while (rs.next()) {
                                    COD_ALMACENACOND=rs.getString("COD_ALMACENACOND");
                                    NOMBRE_ALMACENACOND=rs.getString("NOMBRE_ALMACENACOND");                                
                                %>                      <option value="<%=COD_ALMACENACOND%>"><%=NOMBRE_ALMACENACOND%></option>				    
                                <%                    }
                                %>                
                            </select>
                            <%
                            
                            } catch(Exception e) {
                            }               
                            %>            
                        </td>
                    </tr>                  
                     <tr class="outputText3">
                        <td>&nbsp;&nbsp;<b>Area empresa:</b></td>
                        <td>&nbsp;&nbsp;<b>::</b>&nbsp;&nbsp;</td>
                        <%
                        try{
                            
                            con=Util.openConnection(con);
                            Statement st1 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                            String sql1="select a.COD_AREA_EMPRESA, a.NOMBRE_AREA_EMPRESA from AREAS_EMPRESA a, AREAS_FABRICACION af where a.COD_AREA_EMPRESA=af.COD_AREA_FABRICACION";

                            ResultSet rs1 = st1.executeQuery(sql1);
                        %> 
                        <td>
                            <select name="areaEmpresa" class="outputText3" multiple size="6"> 
                                <%
                                String codAreaEmpresa="";
                                String nombreAreaEmpresa="";
                                while (rs1.next()) {
                                  codAreaEmpresa=rs1.getString("COD_AREA_EMPRESA");
                                  nombreAreaEmpresa=rs1.getString("NOMBRE_AREA_EMPRESA");
                                %>                      <option value="<%=codAreaEmpresa%>"><%=nombreAreaEmpresa%></option>				    
                                <%                    }
                                %>                
                            </select>
                            <%
                            
                            } catch(Exception e) {
                            }               
                            %>            
                        </td>
                    </tr> 
                    <input type="hidden" name="codAlmacen">
                    <input type="hidden" name="nombreAlmacen">
                    <input type="hidden" name="codArea">
                    <input type="hidden" name="nombreArea">
                </table>
                
            </div>
            <br>
            <center>
                <input type="button" class="commandButtonR" value="Ver Reporte" name="reporte" onclick="enviarForm(form1)">
                
            </center>
        </form>
        <script type="text/javascript" language="JavaScript"  src="../../js/dlcalendar.js"></script>
    </body>
</html>