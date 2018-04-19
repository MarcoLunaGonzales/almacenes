<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page language="java" %>
<%@ page import = "java.sql.*"%>
<%@ page import = "java.sql.DriverManager"%> 
<%@ page import = "java.sql.ResultSet"%> 
<%@ page import = "java.sql.Statement"%> 
<%@ page import="com.cofar.util.*" %>
<%@ page language="java" import="java.util.*" %>
<%@ page errorPage="ExceptionHandler.jsp" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%
  Locale locale = null;
            //el primer parametro es el idioma, el segundo es el país, siempre en MAYUSCULAS.
            // ejemplo: es “español”, en “ingles”, US “Estados Unidos”, MX “Mexico”
  locale = new Locale("en","US");
  Locale.setDefault(locale);    
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Reporte Almacenes Acond</title>
<link rel="stylesheet" href="dialog.css" type="text/css"/>

<link rel="STYLESHEET" type="text/css" href="ventas.css" />
     


  <!--<script src="../../js/jquery-1.12.4.js"></script>
  <script src="../../js/jquery-ui.js"></script>-->
  <script type="text/javascript">
  function guardarNuevoMaterial(celda,codMaterial)
  {
      var fila=celda.parentNode.parentNode;
      var valorConversion=fila.cells[3].getElementsByTagName("input")[0].value;
      var codUnidadMedida=fila.cells[4].getElementsByTagName("select")[0].value;
      if(parseFloat(valorConversion) == 0)
      {
          alert('debe registrar el valor de conversion');
          return false;
      }
      ajax = nuevoAjax();
    ajax.open("GET", "ajaxGuardarAgregarMaterial.jsf?codMaterial="+codMaterial+"&valorConversion="+valorConversion+"&codUnidadMedida="+codUnidadMedida+"&data="+(new Date()).getTime().toString(), true);

    ajax.onreadystatechange = function() {
        if (ajax.readyState == 4) {
            if(isNaN(ajax.responseText))
                alert(ajax.responseText);
            else
            {
                if(parseInt(ajax.responseText)==1)
                {
                    this.buscarMaterial();
                    alert('se registro el material');
                }
            }
            

        }
    }
    ajax.send(null);
      
  }
  function nuevoAjax() {
        var xmlhttp = false;
        try {
            xmlhttp = new ActiveXObject("Msxml2.XMLHTTP");
        } catch (e) {
            try {
                xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
            } catch (E) {
                xmlhttp = false;
            }
        }

        if (!xmlhttp && typeof XMLHttpRequest != 'undefined') {
            xmlhttp = new XMLHttpRequest();
        }

        return xmlhttp;
    } 
    function buscarMaterial()
    {
        var nombreMaterial=document.getElementById("nombreMaterialBuscar").value;
        if(nombreMaterial.length<3)
        {
            alert('Debe describir el nombre de material a buscar');
            return false;
        }
        else
        {
            ajax = nuevoAjax();
            ajax.open("GET", "ajaxMostrarMaterialesAgregar.jsf?nombreMaterial="+nombreMaterial+"&data="+(new Date()).getTime().toString(), true);

            ajax.onreadystatechange = function() {
                if (ajax.readyState == 4) {
                    document.getElementById("divTabla").innerHTML=ajax.responseText;
                    carga();
                }
            }
            ajax.send(null);
        }

    }
function procesarMes(anio,codMes)
{
    
    ajax = nuevoAjax();
    ajax.open("GET", "ajaxProcesosDatosMes.jsf?anio="+anio+"&codMes="+codMes+"&data="+(new Date()).getTime().toString(), true);
    ajax.onreadystatechange = function() {
        if (ajax.readyState == 4) {
            alert('Mes Procesado');
            window.location.reload();
           
        }
    }
    ajax.send(null);
}
function carga() {
    
    resul = document.getElementById('dataMateriales');
    
    ajax = nuevoAjax();
    ajax.open("POST", "ajaxListaKardexFELCN.jsf", true);
    ajax.onreadystatechange = function() {
        if (ajax.readyState == 4) {
            resul.innerHTML = ajax.responseText;
           
        }
    }
    ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    ajax.send("valor=0");	
    //alert("willrrr");
}    

function adiciona() {
	//alert("CARGANDO");
   	resul = document.getElementById('dataMateriales');
        mat1=document.getElementById('material2').value;
        //for(i=0;i<=mat2.length())
            
	//mat1="mat2.length()";
        ajax = nuevoAjax();
    ajax.open("POST", "ajaxListaKardexFELCN.jsf", true);
    ajax.onreadystatechange = function() {
        if (ajax.readyState == 4) {
            resul.innerHTML = ajax.responseText;
        }
    }
    ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    ajax.send("valor=2&mat="+mat1);	
    alert("Se adicionó un material a la lista");
    location.reload();
} 
function elimina(value) {
    resul = document.getElementById('dataMateriales');
	//alert(value);
    //id = document.getElementById('ind').value;
    if(confirm("Esta seguro de eliminar el Material?"))
    {	ajax = nuevoAjax();
    	ajax.open("POST", "ajaxListaKardexFELCN.jsf", true);
    	ajax.onreadystatechange = function() {
        	if (ajax.readyState == 4) {
            	resul.innerHTML = ajax.responseText;
        	}
    	}
    	ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    	ajax.send("cod=" + value+"&valor=1");
        
        location.reload();
    }
	else
		return false;
}
  
   function verReportepdf()
   {   //alert("WILLY");
        document.getElementById("form2").action="reporteSustanciasControladasFELCNpdf.jsf";
        document.getElementById("form2").submit();
            
   }
   function verReportexls(anio,codMes)
   {   
        var direccion="reporteSustanciasControladasFELCNxls.jsf?anio="+anio+"&codMes="+codMes+"&data="+(new Date()).getTime().toString();
        openPopup(direccion);
   }
   function verReportepdfkardex(anio,codMes)
   {    
        var direccion="reporteSustanciasControladasFELCNkardex.jsf?anio="+anio+"&codMes="+codMes+"&data="+(new Date()).getTime().toString();
        openPopup(direccion);
   }
    function verReportekardexxls()
   {   
            document.getElementById("form2").action="reporteSustanciasControladasFELCNkardexXlsx.jsf";
            document.getElementById("form2").submit();
           
    }
    function openPopup(url){
                window.open(url,'detalle'+Math.round((Math.random()*1000)),'top=50,left=200,width=800,height=500,scrollbars=1,resizable=1');
    }
    function verReportepdfgeneral(anio,codMes)
    {       
        var direccion="reporteSustanciasControladasFELCNgeneral.jsf?anio="+anio+"&codMes="+codMes+"&data="+(new Date()).getTime().toString();
        openPopup(direccion);
    }
    function verReportepdfgeneralxls(anio,codMes)
    {       
        
        var direccion="reporteSustanciasControladasFELCNgeneralXlsx.jsf?anio="+anio+"&codMes="+codMes+"&data="+(new Date()).getTime().toString();
        openPopup(direccion);
    }
    function cambiaMes1()
    {   document.getElementById("fecha2").value=document.getElementById("fecha1").value; 
        
        //alert("cambido mes 1"+mes1);
    }
    function cambiaAno1()
    {   //alert(document.getElementById("ano2").value);
        document.getElementById("ano2").value=document.getElementById("ano1").value; 
        
    }
  </script>  
  <style type="text/css">
      .tablaMateriales
      {
          border-collapse: collapse;
      }
      .tablaMateriales td
      {
          border:1px solid #cccccc;
      }
  </style>
    </head>
    <body onload="carga();">
  
        
        <form method="get" target="_blank" id="form2" name="form2" action="">
    <table class="tablaFiltroReporte" cellpadding="0" cellspacing="0" align="center" width="70%">
        <thead> 
            <tr>
                <td colspan="3">Reporte Sustancias Controladas FELCN</td>
            </tr>
        </thead>   
        <tbody>            
            <tr>
                <td class="outputTextBold" width="13%"> Materiales Habilitados</td>
                <td class="outputTextBold" width="4%"> ::</td> 
                <td colspan="4">
                               
                    <div style="overflow:auto;text-align:center;height:200px;width:100% " id="main">
                    
                    <div id="div_material">
                       
                        <div id="dataMateriales"></div>
                        <!--<table id='dataMateriales' class='outputText0' style='border : solid #000000 0px; width: 100%' cellpadding='0' cellspacing='0' >
                                
                        </table>-->
                    </div>
                    </div>
                    <br>
                    <center> <a class='btn' id='myBtn'>ADICIONAR MATERIAL</a></center>
     
                </td>    
            </tr>
            <tr >
                <%
                     Calendar fecha = Calendar.getInstance();
                     int mesHoy = fecha.get(Calendar.MONTH)-1;
                      String[] Mes = new String[13];
                        Mes[0]="Enero";
                        Mes[1]="Febrero";
                        Mes[2]="Marzo";
                        Mes[3]="Abril";
                        Mes[4]="Mayo";
                        Mes[5]="Junio";
                        Mes[6]="Julio";
                        Mes[7]="Agosto";
                        Mes[8]="Septiembre";
                        Mes[9]="Octubre";
                        Mes[10]="Noviembre";
                        Mes[11]="Diciembre";
                        
                %>
                <td class="outputText2" style="font-weight:bold">Meses Procesados</td>
                <td class="outputText2" style="font-weight:bold">::</td>
                <td>
                    <table cellpading="0px" class="tablaReporte" cellspacing="0px">
                        <thead>
                            <tr>
                                <td>Año</td>
                                <td>Mes</td>
                                <td>Reporte General</td>
                                <td>Reporte General Excel</td>
                                <td>Reporte Detallado Excel</td>
                                <td>Reporte Kardex</td>
                                <td>Reprocesar Mes</td>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                try
                                {
                                    Connection con=null;
                                    con=Util.openConnection(con);
                                    SimpleDateFormat sdfAnio=new SimpleDateFormat("yyyy");
                                    SimpleDateFormat sdfMes=new SimpleDateFormat("MM");
                                    StringBuilder consulta=new StringBuilder("select m.COD_MES,m.NOMBRE_MES,med.ANIO")
                                                                    .append(" from MATERIALES_EXITENCIA_DECLARADA_MES med")
                                                                            .append(" inner join MESES M on med.MES=M.COD_MES")
                                                                    .append(" group by m.COD_MES,m.NOMBRE_MES,med.ANIO")
                                                                    .append(" order by med.ANIO,m.COD_MES");
                                    Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                                    ResultSet res=st.executeQuery(consulta.toString());
                                    
                                    while(res.next())
                                    {
                                        out.println("<tr>");
                                            out.println("<td>"+res.getInt("ANIO")+"</td>");
                                            out.println("<td>"+res.getString("NOMBRE_MES")+"</td>");
                                            out.println("<td><a class='btn' onclick='verReportepdfgeneral("+res.getInt("ANIO")+","+res.getInt("COD_MES")+")'>ver</a></td>");
                                            out.println("<td><a class='btn' onclick='verReportepdfgeneralxls("+res.getInt("ANIO")+","+res.getInt("COD_MES")+")'>ver</a></td>");
                                            out.println("<td><a class='btn' onclick='verReportexls("+res.getInt("ANIO")+","+res.getInt("COD_MES")+")'>ver</a></td>");
                                            out.println("<td><a class='btn' onclick='verReportepdfkardex("+res.getInt("ANIO")+","+res.getInt("COD_MES")+")'>ver</a></td>");
                                            if(!res.next())
                                            {
                                                res.previous();
                                                out.println("<td><a class='btn' onclick='procesarMes("+res.getInt("ANIO")+","+res.getInt("COD_MES")+")'>Reprocesar</a></td>");
                                            }
                                            else
                                            {
                                                out.println("<td>&nbsp;</td>");
                                                res.previous();
                                            }
                                            
                                        out.println("</tr>");
                                    }
                                    
                                 
                            %>
                        </tbody>
                    </table>
                </td>
            <tr>
                <td class="outputText2" style="font-weight:bold">Mes a generar</td>
                <td class="outputText2" style="font-weight:bold">::</td>
                <td>
                    <%
                            consulta=new StringBuilder("select top 1 DATEADD(MONTH,1,cast(CAST(m.ANIO as varchar)+'/'+cast(m.MES as varchar)+'/01' as datetime)) as fechaSiguiente")
                                                .append(" from MATERIALES_EXITENCIA_DECLARADA_MES m ")
                                                .append(" order by m.ANIO desc, m.MES desc");
                            res=st.executeQuery(consulta.toString());
                            SimpleDateFormat sdf=new SimpleDateFormat("MM/yyyy");
                            
                            if(res.next())
                            {
                                out.println(sdf.format(res.getTimestamp("fechaSiguiente")));
                                out.println("<a class='btn' onclick='procesarMes("+sdfAnio.format(res.getTimestamp("fechaSiguiente"))+","+
                                                sdfMes.format(res.getTimestamp("fechaSiguiente"))+")'>Generar Mes</a>");
                            }

                            con.close();
                        }
                        catch(SQLException ex)
                        {
                            ex.printStackTrace();
                        }
                    %>
                </td>
            </tr>
            
        </tr>
        </tbody>
    </table>   
    </form>  
                    <br>
    
    
   <!-- The Modal -->
<div id="myModal" class="modal">

  <!-- Modal content -->
  <div class="modal-content">
    <span id="close">x</span>
    <form method="post"> 
        
        <table class="tablaFiltroReporte" style="width:100%" cellpadding="0" cellspacing="0" align="center" >
        <thead>
            <tr><td colspan="3">Agregar Material para declaracion</td></tr>
        </thead>
            <tbody>
            <tr>
                <td class="outputTextBold"> Nombre Material</td>
                <td class="outputTextBold"> ::</td> 
                <td class="outputTextBold"><input value="" id="nombreMaterialBuscar"/></td> 
            </tr>
            <tr>
                <td colspan="3" style="text-align: center">
                    <a class="btn" onclick="buscarMaterial()">BUSCAR</a>
                </td>
            </tr>
                <td colspan="3" id="divTabla">
                    
                </td>    
            </tr>
            
        </tbody>
    </table> 
    <br>
    </form>
   </div>

</div>
                    
    <div id="editarSustancia" style=" display: none;" title="Editar Sustancia">
    <form method="post" name="f3"> 
        <input type="hidden" name="sustanciaId" value="" id="sustanciaId">
        <input type="text" name="sustanciaGrabar" value="" id="sustanciaGrabar" size="60"><br><br><br>
        <center><a class='btn' id="editar">GUARDAR</a></center>
    </form>
    </div>  
    <script>
	var modal = document.getElementById('myModal');

	var btn = document.getElementById("myBtn");

        var span = document.getElementById("close");
	btn.onclick = function() {
	    modal.style.display = "block";
	}

	// When the user clicks on <span> (x), close the modal
	span.onclick = function() {
    	modal.style.display = "none";
	}

	// When the user clicks anywhere outside of the modal, close it
	window.onclick = function(event) {
	    if (event.target == modal) {
	        modal.style.display = "none";
	    }
	}
//});
//************
 </script>
    </body>
</html>
