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
        <script>
            function returnPage()
            {
                window.location.href='navegadorPerfilesUsuarios.jsf?data='+(new Date()).getTime().toString();;
            }
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
            function editarPerfil(codPerfil)
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
                ajax.open("POST","ajaxGuardarEdicioPerfilesUsuarios.jsf?nombrePerfil="+encodeURIComponent(document.getElementById("nombrePerfil").value)+
                                            "&codEstadoRegistro="+document.getElementById("codEstadoRegistro").value+
                                            "&codPerfil="+codPerfil+
                                            "&a="+(new Date()).getTime().toString(),true);
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
                            alert('Se guardo la edicion del perfil');
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
        </script>
        <style type='text/css'>
            li
            {
                padding: 3px;
            }
        </style>
    </head>
    <body> 
        
        <h3 align="center">Edición de Perfil de Usuario</h3>
        
        <form method="post" action=""  >
            
            
                
                <%
                    int codPerfil=Integer.valueOf(request.getParameter("codPerfil"));
                    Connection con=null;
                    
                    try
                    {
                        con=Util.openConnection(con);
                        StringBuilder consulta=new StringBuilder("select p.NOMBRE_PERFIL,p.COD_ESTADO_REGISTRO");
                                            consulta.append(" from PERFILES_USUARIOS_BACO p");
                                                    consulta.append(" where p.COD_PERFIL=").append(codPerfil);
                        Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                        ResultSet res=st.executeQuery(consulta.toString());
                        res.next();
                %>
                  <table class="tablaFiltroReporte" cellpading='0px' align='center' cellspacing='0px' width="50%">
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

                            <td class="outputTextBold">Perfil</td>
                            <td class="outputTextBold">::</td>
                            <td colspan="2">
                                <input name="nombrePerfil" id="nombrePerfil" type=text class="inputText" size="40" value="<%=(res.getString("NOMBRE_PERFIL"))%>" onkeypress="valMAY();">
                            </td>

                        </tr>
                        <tr>
                            <td class="outputTextBold">Estado Registro</td>
                            <td class="outputTextBold">::</td>
                            <td colspan="2" >
                                <select id='codEstadoRegistro' class='inputText' >
                                    <option value='1'>Activo</option>
                                    <option value='2'>No Activo</option>
                                </select>
                                <script type='text/javascript'>
                                    document.getElementById("codEstadoRegistro").value='<%=(res.getInt("COD_ESTADO_REGISTRO"))%>';
                                </script>
                            </td>
                        </tr>
                        <tr>
                            <td class="outputTextBold">Observación/Justificación</td>
                            <td class="outputTextBold">::</td>
                            <td colspan="2" >
                                <textarea  style="width:100%" id="justificacion" class="inputText"></textarea>
                            </td>
                        </tr>
                    </tbody>
                </table>
                <table class="tablaFiltroReporte" cellpading='0px' align='center' cellspacing='0px' width="50%">
                    <thead>
                        <tr>
                            <td>Accesos</td>
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
                    
                        consulta=new StringBuilder("select distinct va.ORDEN, va.COD_VENTANA,va.NOMBRE_VENTANA,va.URL_VENTANA,va.COD_VENTANAPADRE");
                                        consulta.append(" ,hijo.cantidadRegistros,pav.COD_VENTANA as registrado");
                                consulta.append(" from VENTANAS_ALMACEN va");
                                    consulta.append(" left outer join PERFIL_ACCESO_VENTANA_BACO pav on pav.COD_VENTANA=va.COD_VENTANA");
                                            consulta.append(" and pav.COD_PERFIL=").append(codPerfil);
                                    consulta.append(" left JOIN(");
                                            consulta.append(" select v.COD_VENTANAPADRE,count(*) as cantidadRegistros");
                                            consulta.append(" from VENTANAS_ALMACEN v group by v.COD_VENTANAPADRE");
                                    consulta.append(" ) as hijo on hijo.COD_VENTANAPADRE=va.COD_VENTANA");
                                consulta.append(" where va.COD_ESTADO_VENTANA=1");
                                consulta.append(" order by va.ORDEN");
                        System.out.println("consulta editar perfil "+consulta.toString());
                        
                        res=st.executeQuery(consulta.toString());
                        StringBuilder xml=new StringBuilder("");
                            xml.append("<tree>");
                            xml.append("<treeNode hasChildNodes=\"true\"  nodeLabel=\"BACO\"  nodeName=\""+1+"\" nodeParent=\"root\" check=\"1\"   />");
                            while(res.next())
                            {
                                xml.append("<treeNode hasChildNodes=\""+(res.getInt("cantidadRegistros")>0?"true":"false")+"\"  nodeLabel=\""+res.getString("NOMBRE_VENTANA")+
                                            "\" nodeName=\""+res.getInt("COD_VENTANA")+"\" nodeParent=\""+res.getInt("COD_VENTANAPADRE")+"\"  check=\""+(res.getInt("registrado")>0?1:0)+"\"/>");
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
            <center>
                <a class="btn" onclick="editarPerfil(<%=(codPerfil)%>)">Guardar</a>
                <a class="btn" onclick="returnPage()">Cancelar</a>
                
            </center>
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
                for(var i=0;i<treeNodes.length;i++)
                {
                    var nodeName=treeNodes[i].attributes[3];
                    var ul=document.createElement('ul');
                    ul.id='tree';
                    if(nodeName.nodeValue=='root')
                    {
                        document.getElementById("arbolDetalle").appendChild(ul);
                        var li=document.createElement('li');
                        var c=document.createElement("input");
                        c.type="checkbox";
                        c.checked=true;
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
                            c.checked=(parseInt(celdas[celda].attributes[4].nodeValue)>0);
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