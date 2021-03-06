
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib prefix="a4j" uri="http://richfaces.org/a4j"%>
<%@taglib prefix="rich" uri="http://richfaces.org/rich"%>
<f:view>
    
    <html>
        <head>
            <title></title>
            <link rel="STYLESHEET" type="text/css" href="../css/ventas.css" /> 
            <script type="text/javascript" src="../js/general.js" ></script> 
            <script>
                function validar(){
                 alert();
                   var compronenteProd=document.getElementById('form1:compronenteProd');                  
                   if(compronenteProd.value==''){
                     alert('Por favor Seleccione un producto para su formula maestra.');
                     compronenteProd.focus();
                     return false;
                   }                   
                   return true;
                }
              
 
  
function validarFecha(){   
    //alert();
    var nombre=document.getElementById('form1:f_inicio');
    var Fecha= new String(nombre.value)   //Crea un string 
    var RealFecha= new Date()   //Para sacar la fecha de hoy   
    //Cadena A�o   
    var Ano= new String(Fecha.substring(Fecha.lastIndexOf("/")+1,Fecha.length))   
    //Cadena Mes   
    var Mes= new String(Fecha.substring(Fecha.indexOf("/")+1,Fecha.lastIndexOf("/")))   
    //Cadena D�a   
    var Dia= new String(Fecha.substring(0,Fecha.indexOf("/")))   
    //alert(Ano);
    //alert(Mes);
    //alert(Dia);
    //Valido el a�o   
    if (isNaN(Ano) || Ano.length<4 || parseFloat(Ano)<1900){   
            alert('A�o inv�lido');
            nombre.focus();
        return false   
    }   
    //Valido el Mes   
    if (isNaN(Mes) || parseFloat(Mes)<1 || parseFloat(Mes)>12){   
        alert('Mes inv�lido')  ;
        nombre.focus();
        return false   
    }   
    //Valido el Dia   
    if (isNaN(Dia) || parseInt(Dia, 10)<1 || parseInt(Dia, 10)>31){   
        alert('D�a inv�lido') ;
        nombre.focus();
        return false   
    }   
    if (Mes==4 || Mes==6 || Mes==9 || Mes==11 || Mes==2) {   
        if (Mes==2 && Dia > 28 || Dia>30) {   
            alert('D�a inv�lido') ;
            nombre.focus();
            return false   
        }   
    }  
    //alert('2');
    var nombre1=document.getElementById('form1:f_final');
    var Fecha1= new String(nombre1.value)   //Crea un string 
    //var RealFecha= new Date()   //Para sacar la fecha de hoy   
    //Cadena A�o   
    var Ano1= new String(Fecha1.substring(Fecha1.lastIndexOf("/")+1,Fecha1.length))   
    //Cadena Mes   
    var Mes1= new String(Fecha1.substring(Fecha1.indexOf("/")+1,Fecha1.lastIndexOf("/")))   
    //Cadena D�a   
    var Dia1= new String(Fecha1.substring(0,Fecha1.indexOf("/")))   
    //alert(Ano1);
    //alert(Mes1);
    //alert(Dia1);
    //Valido el a�o   
    if (isNaN(Ano1) || Ano1.length<4 || parseFloat(Ano1)<1900){   
            alert('A�o inv�lido')   ;
            nombre1.focus();
        return false   
    }   
    //Valido el Mes   
    if (isNaN(Mes1) || parseFloat(Mes1)<1 || parseFloat(Mes1)>12){   
        alert('Mes inv�lido');
        nombre1.focus();
        return false   
    }   
    //Valido el Dia   
    if (isNaN(Dia1) || parseInt(Dia1, 10)<1 || parseInt(Dia1, 10)>31){   
        alert('D�a inv�lido');
        nombre1.focus();
        return false   
    }   
    if (Mes1==4 || Mes1==6 || Mes1==9 || Mes1==11 || Mes1==2) {   
        if (Mes1==2 && Dia1 > 28 || Dia1>30) {   
            alert('D�a inv�lido');
            nombre1.focus();
            return false
            
        }   
    }   
       
  //para que envie los datos, quitar las  2 lineas siguientes   
  //alert("Fecha correcta.")   
  return true     
}
function redireccionar(){
    <%--alert('<%=request.getParameter("url")%>');--%>
    document.location = '<%=request.getParameter("url")%>';
}
</script>
        </head>
        <body >
            <h:form id="form1"  >
                
                <div align="center">
                    <br>
                    <h3 align="center">Editar Solicitud de Mantenimiento</h3>
                    <br>
                    
                    <h:panelGrid columns="3" width="65%" styleClass="panelgrid" headerClass="headerClassACliente">
                        <f:facet name="header" >
                            <h:outputText value="Introduzca Datos" styleClass="outputText2" style="color:#FFFFFF"   />
                        </f:facet>      
                        
                         
                        <h:outputText styleClass="outputText2" value="Area Empresa"  />
                        <h:outputText styleClass="outputText2" value="::"  />
                        <h:selectOneMenu  styleClass="inputText" value="#{ManagedSolicitudMantenimiento.solicitudMantenimientoEditar.areasEmpresa.codAreaEmpresa}"  >
                            <f:selectItems value="#{ManagedSolicitudMantenimiento.areasEmpresaList}"/>
                            <a4j:support action="#{ManagedSolicitudMantenimiento.areaEdicion_change}" event="onchange" reRender="codMaquinaria,codInstalacion" />
                        </h:selectOneMenu>


                        <h:outputText  value="" styleClass="outputText2"  />
                        <h:outputText value="" styleClass="outputText2"   />
                        <h:selectOneRadio value="#{ManagedSolicitudMantenimiento.valorAsignado}" id="asignado" styleClass="outputText2" >
                            <f:selectItem id="item1" itemLabel="Maquinaria" itemValue="maquinaria"/>
                            <f:selectItem id="item2" itemLabel="Instalacion" itemValue="instalacion" />
                            <a4j:support event="onclick" action="#{ManagedSolicitudMantenimiento.maquinaria_change}" reRender="codMaquinaria,codInstalacion"  />
                        </h:selectOneRadio>


                        <h:outputText styleClass="outputText2" value="Maquinaria"  />
                        <h:outputText styleClass="outputText2" value="::"  />
                        <h:selectOneMenu id="codMaquinaria"  styleClass="inputText" value="#{ManagedSolicitudMantenimiento.solicitudMantenimientoEditar.maquinaria.codMaquina}" disabled="#{ManagedSolicitudMantenimiento.valorAsignado!='maquinaria'}"  >
                            <f:selectItems value="#{ManagedSolicitudMantenimiento.maquinariasList}"/>
                        </h:selectOneMenu>

                        <h:outputText  styleClass="outputText2" value="Instalacion"  />
                        <h:outputText styleClass="outputText2" value="::"  />
                        <h:selectOneMenu id="codInstalacion" styleClass="inputText" value="#{ManagedSolicitudMantenimiento.solicitudMantenimientoEditar.modulosInstalaciones.areasInstalacionesModulos.areasInstalaciones.codAreaInstalacion}" disabled="#{ManagedSolicitudMantenimiento.valorAsignado!='instalacion'}" >
                            <f:selectItems value="#{ManagedSolicitudMantenimiento.areasInstalacionesList}" />
                            <a4j:support action="#{ManagedSolicitudMantenimiento.instalacionEditar_change}" event="onchange" reRender="codAreaInstalacionDetalle"/>
                        </h:selectOneMenu>
                          <h:outputText  styleClass="outputText2" value="Ambiente"  />
                        <h:outputText styleClass="outputText2" value="::"  />
                        <h:selectOneMenu id="codAreaInstalacionDetalle" styleClass="inputText" value="#{ManagedSolicitudMantenimiento.solicitudMantenimientoEditar .areasInstalacionesDetalle.codAreaInstalacionDetalle}" disabled="#{ManagedSolicitudMantenimiento.valorAsignado!='instalacion'}" >
                            <f:selectItems value="#{ManagedSolicitudMantenimiento.areasInstalacionesDetalleSelectList}" />
                        </h:selectOneMenu>
                        <h:outputText  styleClass="outputText2" value="Observaci�n"  />
                        <h:outputText styleClass="outputText2" value="::"  />
                        <h:inputTextarea cols="50" styleClass="inputText" value="#{ManagedSolicitudMantenimiento.solicitudMantenimientoEditar.obsSolicitudMantenimiento}" id="obs"  />



                        <h:outputText  styleClass="outputText2" value="Afecta a Produccion"  />
                        <h:outputText styleClass="outputText2" value="::"  />
                        <h:selectOneMenu  styleClass="inputText" value="#{ManagedSolicitudMantenimiento.solicitudMantenimientoEditar.afectaraProduccion}"  >
                            <f:selectItem itemLabel="SI" itemValue="1" />
                            <f:selectItem itemLabel="NO" itemValue="0" />
                        </h:selectOneMenu>

                        <h:outputText  styleClass="outputText2" value="Prioridad"  />
                        <h:outputText styleClass="outputText2" value="::"  />
                        <h:selectOneMenu  styleClass="inputText" value="#{ManagedSolicitudMantenimiento.solicitudMantenimientoEditar.tiposNivelSolicitudMantenimiento.codTipoNivelSolicitudMantenimiento}"  >
                            <f:selectItems value="#{ManagedSolicitudMantenimiento.tiposNivelSolicitudMantenimientoList}" />
                        </h:selectOneMenu>


                        <h:outputText  styleClass="outputText2" value="Fecha Emisi�n"  />
                        <h:outputText styleClass="outputText2" value="::"  />
                        <h:panelGroup>
                            <h:inputText styleClass="outputText2" value="#{ManagedSolicitudMantenimiento.solicitudMantenimientoEditar.fechaSolicitudMantenimiento}"  id="f_final" readonly="true" >
                                <f:convertDateTime pattern="dd/MM/yyyy hh:mm" />
                            </h:inputText>

                        </h:panelGroup>


                        <h:outputText  styleClass="outputText2" value="Estado"  />
                        <h:outputText styleClass="outputText2" value="::"  />
                        <h:inputText styleClass="inputText" value="SOLICITADA" id="estado" readonly="true"  />

                        <h:outputText  styleClass="outputText2" value="Tipo de Mantenimiento"  />
                        <h:outputText styleClass="outputText2" value="::"  />
                        <h:selectOneMenu value="#{ManagedSolicitudMantenimiento.solicitudMantenimientoEditar.tiposSolicitudMantenimiento.codTipoSolMantenimiento}" styleClass="inputText" >
                            <f:selectItems value="#{ManagedSolicitudMantenimiento.tiposSolicitudMantenimientoList}" />
                        </h:selectOneMenu>
                        <%--h:inputText styleClass="inputText" value="#{ManagedSolicitudMantenimiento.solicitudMantenimientobean.tiposSolicitudMantenimiento.nombreTipoSolMantenimiento}" readonly="true"  /--%>

                        <h:outputText  styleClass="outputText2" value="Solicitante"  />
                        <h:outputText styleClass="outputText2" value="::"  />
                        <h:inputText styleClass="inputText" value="#{ManagedSolicitudMantenimiento.solicitudMantenimientoEditar.personal_usuario.nombrePersonal} #{ManagedSolicitudMantenimiento.solicitudMantenimientoEditar.personal_usuario.apPaternoPersonal} #{ManagedSolicitudMantenimiento.solicitudMantenimientoEditar.personal_usuario.apMaternoPersonal}" readonly="true"
                        size="50" />

                    </h:panelGrid>
                    
                    <br>
                    
                    <br>
                    
                    <a4j:commandButton value="Guardar" styleClass="btn"  action="#{ManagedSolicitudMantenimiento.guardarEdicionSolicitudMantenimiento_action}" oncomplete="document.location='navegador_solicitud_mantenimiento.jsf'"  />
                    <input type="button" value="Cancelar" class="btn" onclick="window.location='navegador_solicitud_mantenimiento.jsf'"  /> <%--  action="#{ManagedSolicitudMantenimiento.cancelar}" --%>
                    <%-- onclick="return validarFecha();"  --%>
                </div>
                
            </h:form>
        </body>
        <script type="text/javascript" language="JavaScript"  src="../js/dlcalendar.js"></script>
        
    </html>
    
</f:view>

