<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page language="java" %>
<%@ page import = "java.sql.*"%>
<%@ page import = "java.sql.DriverManager"%> 
<%@ page import = "java.sql.ResultSet"%> 
<%@ page import = "java.sql.Statement"%> 
<%@ page import="com.cofar.util.*" %>



<html>
    <head>
        <link rel="STYLESHEET" type="text/css" href="../../css/ventas.css" />
        <script src="../../js/general.js"></script>
        <style type='text/css'>
            li
            {
                padding: 3px;
            }
        </style>
        <script>
            function nuevoAjax()
            {	
                var xmlhttp=false;
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
            function registrarPerfil()
            {
                var ventanas=document.getElementsByName("ventana");
                var codigosVentana=new Array();
                for(var i=0;i<ventanas.length;i++)
                {
                    if(ventanas[i].checked)
                    {
                        codigosVentana.push(ventanas[i].value);
                    }
                }
                ajax=nuevoAjax();
                ajax.open("POST","ajaxGuardarPerfilesUsuarios.jsf?nombrePerfil="+encodeURIComponent(document.getElementById("nombrePerfil").value)+"&a="+(new Date()).getTime().toString(),true);
                ajax.setRequestHeader('content-type', 'application/x-www-form-urlencoded;charset=UTF-8');
                ajax.onreadystatechange=function(){
                    if (ajax.readyState==4) {
                        if(ajax.responseText==null || ajax.responseText=='')
                        {
                            alert('No se puede conectar con el servidor, verfique su conexión a internet');
                            return false;
                        }
                        if(parseInt(ajax.responseText.split("\n").join(""))=='1')
                        {
                            alert('Se registraron los permisos');
                            returnPage();
                            return true;
                        }
                        else
                        {
                            alert("Ocurrio un error al momento de registrar los defectos "+ajax.responseText.split("\n").join(""));
                            return false;
                        }

                    }
                };
                ajax.send("codigosVentana="+codigosVentana);
            }
            function returnPage()
            {
                window.location.href='navegadorPerfilesUsuarios.jsf?data='+(new Date()).getTime().toString();;
            }
        </script>
    </head>
    <body> 
        
        <br><br>
        <h3 align="center">Registro de Perfil de Usuario</h3>
        
        <form method="post" action=""  >
            <table cellpading="0px" cellspacing="0px" class="tablaFiltroReporte" align="center" width="50%">
                <thead>
                    <tr>
                        <td  colspan="5" >
                            <div class="outputText2" align="center">
                                Introduzca Datos
                            </div>    
                        </td>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td class="outputText2">Perfil</td>
                        <td class="outputText2">::</td>
                        <td colspan="2">
                            <input name="nombrePerfil" id="nombrePerfil" type=text class="inputText" size="40" onkeypress="valMAY();">
                        </td>
                    </tr>
                    <tr>
                        <td class="outputText2">Estado Registro</td>
                        <td class="outputText2">::</td>
                        <td colspan="2">Activo</td>
                    </tr>
                </tbody>
            </table>
            <table  class="tablaFiltroReporte" cellspacing="0px" id="tablaAccesos" cellpading="0px" align="center" width="50%">
                <thead>
                    <tr>
                        <td class="tdCenter">Accesos</td>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>
                            <div id="arbolDetalle"></div>
                        </td>
                    </tr>
                </tbody>
            </table>
                    
                    
                    <%
                    Connection con=null;
                    try
                    {
                        StringBuilder consulta=new StringBuilder("select distinct va.ORDEN, va.COD_VENTANA,va.NOMBRE_VENTANA,va.URL_VENTANA,va.COD_VENTANAPADRE");
                                                        consulta.append(" ,hijo.cantidadRegistros");
                                                consulta.append(" from VENTANAS_ALMACEN va");
                                                    consulta.append(" left JOIN(");
                                                            consulta.append(" select v.COD_VENTANAPADRE,count(*) as cantidadRegistros");
                                                            consulta.append(" from VENTANAS_ALMACEN v group by v.COD_VENTANAPADRE");
                                                    consulta.append(" ) as hijo on hijo.COD_VENTANAPADRE=va.COD_VENTANA");
                                                    consulta.append(" and va.COD_ESTADO_VENTANA=1");
                                                consulta.append(" order by va.ORDEN");
                        con=Util.openConnection(con);
                        Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                        ResultSet res=st.executeQuery(consulta.toString());
                        StringBuilder xml=new StringBuilder("");
                            xml.append("<tree>");
                            xml.append("<treeNode hasChildNodes=\"true\"  nodeLabel=\"BACO\"  nodeName=\""+1+"\" nodeParent=\"root\"   />");
                            while(res.next())
                            {
                                xml.append("<treeNode hasChildNodes=\""+(res.getInt("cantidadRegistros")>0?"true":"false")+"\"  nodeLabel=\""+res.getString("NOMBRE_VENTANA")+
                                            "\" nodeName=\""+res.getInt("COD_VENTANA")+"\" nodeParent=\""+res.getInt("COD_VENTANAPADRE")+"\" />");
                            }
                            xml.append("</tree>");
                            out.println("<script type='text/javascript'>var text, parser, xmlDoc;text='"+xml.toString()+"';parser = new DOMParser();xmlDoc = parser.parseFromString(text,'text/xml');</script>");
                        
                    }
                    catch(Exception ex)
                    {
                        ex.printStackTrace();
                    }
                    finally
                    {
                        con.close();
                    }
                    %>
                    
        </div>
            <br>
             <div id='bottonesAcccion' class="barraBotones tdCenter">
                <a class="btn" onclick="registrarPerfil()">Guardar</a>
                <a class="btn" onclick="redireccionar('navegadorPerfilesUsuarios.jsf')">Cancelar</a>
            </div>
    <script type="text/javascript">
                
        function seleccionarTodo(li,valor)
        {
            var inputs=li.parentNode.getElementsByTagName("input");
            for(var indice in inputs)
            {
                inputs[indice].checked=valor;
            }
        }
        var treeNodes=  xmlDoc.getElementsByTagName('treeNode');
        for(var i=0;i<treeNodes.length;i++){
        var nodeName=treeNodes[i].attributes[3];
        var ul=document.createElement('ul');
        ul.id='tree';
        if(nodeName.nodeValue=='root')
        {
            document.getElementById("arbolDetalle").appendChild(ul);
            var li=document.createElement('li');
            var c=document.createElement("input");
            c.type="checkbox";
            var icon=document.createElement('img');
            icon.src='../../img/folder.gif';
            var a=document.createElement('span');
            a.className='node';
            a.innerHTML=treeNodes[i].attributes[1].nodeValue+'  ';
            var iconMas=document.createElement("img");
            iconMas.src='../../img/mas.png';
            iconMas.style.height='16px';
            iconMas.style.cursor='pointer';
            iconMas.onclick=function(){seleccionarTodo(this,true);}
            var iconMenos=document.createElement("img");
            iconMenos.src='../../img/menos.png';
            iconMenos.style.height='16px';
            iconMenos.style.cursor='pointer';
            iconMenos.onclick=function(){seleccionarTodo(this,false);}
            li.appendChild(c);
            li.appendChild(icon)
            li.appendChild(a);
            li.appendChild(iconMas);
            li.appendChild(iconMenos);
            
            li.id=treeNodes[i].attributes[2].nodeValue;
            ul.appendChild(li);
            detalle(li);
        }
    }
    function detalle(element)
    {
        var celdas=  xmlDoc.getElementsByTagName('treeNode');
        var ul=document.createElement('ul');
        for(var celda =1;celda<=celdas.length-1;celda++)
        {   
            if(celdas[celda].attributes[3].nodeValue==element.id)
            {
                element.appendChild(ul);
                var li=document.createElement('li');
                var c=document.createElement("input");
                c.name='ventana';
                c.value=celdas[celda].attributes[2].nodeValue;
                c.type="checkbox";
                c.id = "ventana"+celdas[celda].attributes[2].nodeValue;
                var a=document.createElement('label');
                a.setAttribute("for","ventana"+celdas[celda].attributes[2].nodeValue);
                a.innerHTML=celdas[celda].attributes[1].nodeValue+'&nbsp;&nbsp;&nbsp;&nbsp;';
                a.className='node';
                var icon=document.createElement('img');
                icon.src='../../img/'+(celdas[celda].attributes[0].nodeValue=='true'?'folder.gif':'b.bmp');
                li.appendChild(c);
                li.appendChild(icon);
                li.appendChild(a);
                li.id=celdas[celda].attributes[2].nodeValue;
                ul.appendChild(li);
                if(celdas[celda].attributes[0].nodeValue=='true')
                {
                    a.className='outputTextBold';
                    var iconMas=document.createElement("img");
                    iconMas.src='../../img/mas.png';
                    iconMas.style.height='16px';
                    iconMas.style.cursor='pointer';
                    iconMas.title='Habilitar todas las subventanas';
                    iconMas.onclick=function(){seleccionarTodo(this,true);}
                    var iconMenos=document.createElement("img");
                    iconMenos.src='../../img/menos.png';
                    iconMenos.style.height='16px';
                    iconMenos.style.cursor='pointer';
                    iconMenos.title='Deshabilitar todas las subventanas';
                    iconMenos.onclick=function(){seleccionarTodo(this,false);}
                    li.appendChild(iconMas);
                    li.appendChild(iconMenos);
                    detalle(li);
                }
                
            }
        }
    }
    
            </script>
        </form>
        
    </body>
</html>