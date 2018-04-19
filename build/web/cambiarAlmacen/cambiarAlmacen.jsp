<%@page import="com.cofar.bean.Almacenes"%>
<%@page import="com.cofar.dao.DaoAlmacenHabilitadoUsuario"%>
<%@page contentType="text/html" pageEncoding="windows-1252"%>
<%@page import="com.cofar.web.ManagedAccesoSistema" %>
<%@page import="com.cofar.util.*" %>
<%@page import="java.sql.*" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
            <script type="text/javascript" src="../js/general.js"></script>
            <link rel="STYLESHEET" type="text/css" href="../css/ventas.css" />
            <script type="text/javascript">
                function cambiarGestion(f){
                    bloquearPantalla();
                    var codigo="";
                    var nombreGestion="";
                    var j=0;
                    for(var i=0;i<=f.codAlmacen.options.length-1;i++)
                    {	if(f.codAlmacen.options[i].selected)
                        {	
                            codigo=f.codAlmacen.options[i].value;
                            nombreGestion=f.codAlmacen.options[i].innerHTML;
                                            j++;
                                    }
                            }
                    location.href="cambiarAlmacen.jsf?codigo="+codigo+'&nombreGestion='+nombreGestion;
                    window.parent.topFrame.location = window.parent.topFrame.location;
                }

                function actualizarCabecera(){
                    window.parent.topFrame.location = window.parent.topFrame.location;
                }
            </script>
    </head>
    <body>
         <form method="post" action="cambiarAlmacen.jsf" name="form1">
            <%
            String codAlmacen=request.getParameter("codigo");
            String nombreAlmacen=request.getParameter("nombreGestion");
            ManagedAccesoSistema managed = (ManagedAccesoSistema)Util.getSessionBean("ManagedAccesoSistema");
            if(codAlmacen!=null){
                managed.cambiarAlmacenUsuario(Integer.valueOf(codAlmacen));
                out.println("<script type='text/javascript'>actualizarCabecera();</script>");
            }
            %>
            <div align="center">
                <table border="0" style="border:1px solid #000000"  border="0" class="tablaFiltroReporte" cellpadding="0"  cellspacing="0">
                    <tr class="headerClassACliente">
                        <td  colspan="3" >
                            <div align="center" class="outputText2">
                               <b> Cambio de Almacen </b>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="outputTextBold" height="25px">Almacen de trabajo</td>
                        <td class="outputTextBold">::</td>
                        <%
                           
                            nombreAlmacen = managed.getAlmacenesGlobal().getNombreAlmacen();
                            
                        %>
                        <td>
                            <span class="outputText2"><%=nombreAlmacen%></span>
                        </td>
                    </tr>

                    <tr class="outputText2">
                        <td class="outputTextBold" height="25px">Almacén</td>
                        <td class="outputTextBold">::</td>
                        <td>
                            <select  name="codAlmacen" class="outputText3" onchange="cambiarGestion(this.form);" >
                                <%
                                    DaoAlmacenHabilitadoUsuario daoAlmacenHabilitadoUsuario = new DaoAlmacenHabilitadoUsuario();
                                    for(Almacenes almacen : daoAlmacenHabilitadoUsuario.listarPorUsuario(Integer.valueOf(managed.getUsuarioModuloBean().getCodUsuarioGlobal()))){
                                        out.println("<option value='"+almacen.getCodAlmacen()+"' "+
                                                (managed.getAlmacenesGlobal().getCodAlmacen() == almacen.getCodAlmacen()?"selected":"")+">"+almacen.getNombreAlmacen()+"</option>");
                                    }
                                %>
                            </select>
                        </td>
                    </tr>
                </table>
            </div>
            <br>
        </form>
    </body>
</html>
