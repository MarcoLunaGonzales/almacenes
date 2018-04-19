<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib prefix="a4j" uri="http://richfaces.org/a4j"%>
<%@taglib prefix="rich" uri="http://richfaces.org/rich"%>
<%@taglib prefix="ws" uri="http://isawi.org/ws"%>
<f:view>

    <html>
        <head>
            <title>SISTEMA</title>
            <link rel="STYLESHEET" type="text/css" href="../css/ventas.css" />
            <%--inicio alejandro 2--%>
            <style>
                .b
                {

                    background-color:#FF0000;
                }
            </style>
            <%--final alejandro 2--%>
            <script type="text/javascript" src="../js/general.js" ></script>
            <script>
                
                
                onerror = function () {
                    alert('existe un error al momento de cargar el registro, por favor recargue la pagina');
                }
                A4J.AJAX.onError = function (req, status, message) {
                    window.alert("Ocurrio un error: " + message);
                }
                A4J.AJAX.onExpired = function (loc, expiredMsg) {
                    if (window.confirm("Ocurrio un error al momento realizar la transaccion: " + expiredMsg + " location: " + loc)) {
                        return loc;
                    } else {
                        return false;
                    }
                }
            </script>
        </head>
        <body >
            <a4j:form id="form1">
                <div align="center">
                    <h:outputText value="#{ManagedIngresoAlmacen.cargarIngresosAlmacenValidar}"/>
                    <h:outputText styleClass="outputTextTitulo"  value="VALIDAR SOLICITUDES DE EDITAR/ANULAR INGRESO" />
                    <br>
                    
                    <rich:panel style="width:50%" id="panelBusquedaIngresosValidar" headerClass="headerClassACliente">
                        <f:facet name="header">
                            <h:outputText value="Buscador" />
                        </f:facet>
                        <h:panelGrid columns="2">
                            <h:outputText value="Por Revisión: " styleClass="outputText1Subtitle" />
                            <h:selectOneMenu id="sorTipoRevision" value="#{ManagedIngresoAlmacen.ingresoAlmacenBuscar.ingresoTransaccionSolicitud.tipoRevision}" styleClass="inputText" >
                                <f:selectItem itemLabel="Todos" itemValue="1" />
                                <f:selectItem itemLabel="Pendientes" itemValue="2"/>
                                <f:selectItem itemLabel="Aprobados" itemValue="3"/>
                                <f:selectItem itemLabel="Rechazados" itemValue="4"/>
                            </h:selectOneMenu>
                        </h:panelGrid> 
                        <h:panelGrid columns="4">
                            <h:outputText value="Por Fecha Solicitud: DESDE: " styleClass="outputText1Subtitle" />
                            <rich:calendar id="txtFechaSolicitudInicio" datePattern="dd/MM/yyyy" styleClass="inputText" enableManualInput="false" oninputblur="if(this.value!=''){valFecha(this)}else{return true;}" value="#{ManagedIngresoAlmacen.ingresoAlmacenBuscar.ingresoTransaccionSolicitud.fechaSolicitud}" />
                            <h:outputText value="HASTA: " styleClass="outputText1Subtitle" />
                            <rich:calendar id="txtfechaSolicitudFin" datePattern="dd/MM/yyyy" styleClass="inputText" enableManualInput="false" oninputblur="if(this.value!=''){valFecha(this)}else{return true;}" value="#{ManagedIngresoAlmacen.ingresoAlmacenBuscar.ingresoTransaccionSolicitud.fechaSolicitudFin}"  />

                            <h:outputText value="Por Fecha Validación: DESDE: " styleClass="outputText1Subtitle" />
                            <rich:calendar id="txtfechaValidacionInicio" datePattern="dd/MM/yyyy" styleClass="inputText" enableManualInput="false" oninputblur="if(this.value!=''){valFecha(this)}else{return true;}" value="#{ManagedIngresoAlmacen.ingresoAlmacenBuscar.ingresoTransaccionSolicitud.fechaValidacion}" />
                            <h:outputText value="HASTA: " styleClass="outputText1Subtitle" />
                            <rich:calendar id="txtfechaValidacionFin" datePattern="dd/MM/yyyy" styleClass="inputText" enableManualInput="false" oninputblur="if(this.value!=''){valFecha(this)}else{return true;}" value="#{ManagedIngresoAlmacen.ingresoAlmacenBuscar.ingresoTransaccionSolicitud.fechaValidacionFin}" />
                        </h:panelGrid> 
                        <div align="center">
                            <a4j:commandButton styleClass="btn" value="Buscar"  action="#{ManagedIngresoAlmacen.cargarIngresosAlmacenValidarList_action}" reRender="dataIngresosAlmacen, controles" />
                            <a4j:commandButton styleClass="btn" value="Limpiar"  action="#{ManagedIngresoAlmacen.reiniciarCamposBusqueda_action}" reRender="panelBusquedaIngresosValidar" />
                            
                        </div>
                    </rich:panel>
                    
                    
                    <br>
                    
                    <rich:dataTable value="#{ManagedIngresoAlmacen.ingresosAlmacenValidarList}"
                                    var="data"
                                    id="dataIngresosAlmacen"
                                    onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                    onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                    headerClass="headerClassACliente" >
                        
                        <f:facet name="header">
                            <rich:columnGroup>
                                <rich:column colspan="9">
                                    <h:outputText value="DATOS DEL INGRESO"/>
                                </rich:column>
                                <rich:column colspan="5">
                                    <h:outputText value="DATOS DE LA SOLICITUD"/>
                                </rich:column>
                                <rich:column breakBefore="true">
                                   <h:outputText value="Nro Ingreso Almacen"  />
                                </rich:column>
                                <rich:column>
                                   <h:outputText value="Fecha Ingreso Almacen"  />
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Tipo Ingreso Almacen"  />
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Nro Orden Compra"  />
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Proveedor"  />
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Pais Proveedor"  />
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="observaciones"  />
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Personal"  />
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Estado Ingreso Almacen"  />
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Estado Solicitud"  />
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Solicitante"  />
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Fecha Solicitante"  />
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Fecha Validador"  />
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Acciones"  />
                                </rich:column>
                            </rich:columnGroup>
                        </f:facet>
                        
                        <rich:column styleClass ="#{data.colorFila}">
                            <h:outputText  value="#{data.nroIngresoAlmacen}"  />
                        </rich:column>
                        
                        <rich:column styleClass ="#{data.colorFila}">
                            <h:outputText value="#{data.fechaIngresoAlmacen}"  >
                                <f:convertDateTime pattern="dd/MM/yyyy HH:mm" />
                            </h:outputText>
                        </rich:column>
                        
                        <rich:column styleClass ="#{data.colorFila}">
                            <h:outputText value="#{data.tiposIngresoAlmacen.nombreTipoIngresoAlmacen}"  />
                        </rich:column>

                        <rich:column styleClass ="#{data.colorFila}">
                            <h:outputText value="#{data.ordenesCompra.nroOrdenCompra}"  />
                        </rich:column>

                        <rich:column styleClass ="#{data.colorFila}">
                            <h:outputText value="#{data.proveedores.nombreProveedor}"  />
                        </rich:column>

                        <rich:column styleClass ="#{data.colorFila}">
                            <h:outputText value="#{data.proveedores.paises.nombrePais}"  />
                        </rich:column>

                        <rich:column styleClass ="#{data.colorFila}">
                            <h:outputText value="#{data.obsIngresoAlmacen}"  />
                        </rich:column>

                        <rich:column styleClass ="#{data.colorFila}">
                            <h:outputText value="#{data.personal.nombrePersonal}"  />
                            &nbsp;
                            <h:outputText value="#{data.personal.apPaternoPersonal}"  />
                            &nbsp;
                            <h:outputText value="#{data.personal.apMaternoPersonal}"  />
                        </rich:column>

                        <rich:column styleClass ="#{data.colorFila}">
                            <h:outputText value="#{data.estadosIngresoAlmacen.nombreEstadoIngresoAlmacen}" />
                        </rich:column>    
                            
                        <rich:column styleClass ="#{data.colorFila}">
                            <h:outputText value="<br/><span style='background-color: #{data.ingresoTransaccionSolicitud.estadoTransaccionSolicitud.codEstadoTransaccionSolicitud==1?'#FCCCC1': data.ingresoTransaccionSolicitud.estadoTransaccionSolicitud.codEstadoTransaccionSolicitud==2?'#7FFFD4':''} '>(#{data.ingresoTransaccionSolicitud.estadoTransaccionSolicitud.nombreEstadoTransaccionSolicitud})</span>" escape="false" rendered="#{data.ingresoTransaccionSolicitud.estadoTransaccionSolicitud.nombreEstadoTransaccionSolicitud !=''}"  />
                        </rich:column>    
                            
                        <rich:column styleClass ="#{data.colorFila}">
                            <h:outputText value="#{data.ingresoTransaccionSolicitud.personalSolicitante.nombrePersonal}" />
                            <h:outputText value=": <br/>#{data.ingresoTransaccionSolicitud.observacionSolicitante}" escape="false" rendered="#{data.ingresoTransaccionSolicitud.observacionSolicitante != ''}" />
                        </rich:column>
                        
                        <rich:column styleClass ="#{data.colorFila}">
                            <h:outputText value="#{data.ingresoTransaccionSolicitud.fechaSolicitud}"  >
                                <f:convertDateTime pattern="dd/MM/yyyy HH:mm" />
                            </h:outputText>
                        </rich:column>
                        
                        <rich:column styleClass ="#{data.colorFila}">
                            <h:outputText value="#{data.ingresoTransaccionSolicitud.fechaValidacion}"  >
                                <f:convertDateTime pattern="dd/MM/yyyy HH:mm" />
                            </h:outputText>
                        </rich:column>

                        <rich:column styleClass ="#{data.colorFila}">
                            <rich:dropDownMenu rendered="#{ManagedIngresoAlmacen.administradorAlmacen && (data.ingresoTransaccionSolicitud.estadoTransaccionSolicitud.codEstadoTransaccionSolicitud==1 || data.ingresoTransaccionSolicitud.estadoTransaccionSolicitud.codEstadoTransaccionSolicitud==2)}"  >
                                <f:facet name="label">
                                    <h:outputText value="Acciones"/>
                                </f:facet>

                                <rich:menuItem submitMode="ajax" value="Aprobar" rendered="#{ManagedIngresoAlmacen.permisoValidarAnular && ManagedIngresoAlmacen.permisoValidarEditar}">
                                    <a4j:support event="onclick" 

                                                 action="#{ManagedIngresoAlmacen.nuevoIngresoTransaccionSolicitudValidar_action}"
                                                 oncomplete="if(#{ManagedIngresoAlmacen.ingresoEditable}){if(#{ManagedIngresoAlmacen.ingresosAlmacen.ingresoTransaccionSolicitud.estadoTransaccionSolicitud.codEstadoTransaccionSolicitud == 1}){Richfaces.showModalPanel('panelAprobarTransaccionSolicitud')}else{location='editarIngresosAlmacen.jsf';}}else{alert('#{ManagedIngresoAlmacen.mensaje}');} " 
                                                 reRender="contenidoAprobarTransaccionSolicitud, txtTituloPanelAprobar" 
                                                 >
                                        <f:setPropertyActionListener value="#{data}" target="#{ManagedIngresoAlmacen.ingresosAlmacen}"/>
                                    </a4j:support>    
                                </rich:menuItem>

                                <rich:menuItem submitMode="ajax" value="Denegar" rendered="#{ManagedIngresoAlmacen.permisoValidarAnular && ManagedIngresoAlmacen.permisoValidarEditar}">
                                    <a4j:support event="onclick" 

                                                 action="#{ManagedIngresoAlmacen.nuevoIngresoTransaccionSolicitudRechazar_action}"
                                                 oncomplete="Richfaces.showModalPanel('panelDenegarTransaccionSolicitud')" 
                                                 reRender="contenidoDenegarTransaccionSolicitud, txtTituloPanelDenegar "  >
                                        <f:setPropertyActionListener value="#{data}" target="#{ManagedIngresoAlmacen.ingresosAlmacen}"/>
                                    </a4j:support>    
                                </rich:menuItem>
                            </rich:dropDownMenu>                                
                        </rich:column>  

                    </rich:dataTable>

                    <h:panelGrid columns="2"  width="50" id="controles">
                        <a4j:commandLink  action="#{ManagedIngresoAlmacen.ingresos_validar_atras_action}" reRender="dataIngresosAlmacen,controles" timeout="7200"  rendered="#{ManagedIngresoAlmacen.begin!='1'}" >
                            <h:graphicImage url="../img/previous.gif"  style="border:0px solid red" alt="PAGINA ANTERIOR"  />
                        </a4j:commandLink>
                        <a4j:commandLink  action="#{ManagedIngresoAlmacen.ingresos_validar_siguiente_action}" reRender="dataIngresosAlmacen,controles" timeout="7200"  rendered="#{ManagedIngresoAlmacen.cantidadfilas>='10'}">
                            <h:graphicImage url="../img/next.gif"  style="border:0px solid red"  alt="PAGINA SIGUIENTE" />
                        </a4j:commandLink>
                    </h:panelGrid>

                </div>

            </a4j:form>

           

            <rich:modalPanel id="panelAprobarTransaccionSolicitud"  minHeight="240"  minWidth="750"
                             height="240" width="750"
                             zindex="200"
                             headerClass="headerClassACliente"
                             resizeable="false" style="overflow :auto" >
                <f:facet name="header">
                    <h:outputText id="txtTituloPanelAprobar" escape="false"
                                  value="<centrar>APROBAR SOLICITUD DE #{ManagedIngresoAlmacen.ingresosAlmacen.ingresoTransaccionSolicitud.tipoTransaccionSolicitudAlmacen.nombreTipoTransaccionSolicitudAlmacen}</centrar>"/>
                </f:facet>
                <a4j:form>
                    <h:panelGroup id="contenidoAprobarTransaccionSolicitud">
                        <div align="center">
                            <h:panelGrid columns="4">
                                <h:outputText value="Nro. Ingreso:" styleClass="outputText1Subtitle" />
                                <h:outputText value="#{ManagedIngresoAlmacen.ingresosAlmacen.nroIngresoAlmacen}" styleClass="outputText2" style="width:50px" />
                                <h:outputText value="Fecha Ingreso:" styleClass="outputText1Subtitle" />
                                <h:outputText value="#{ManagedIngresoAlmacen.ingresosAlmacen.fechaIngresoAlmacen}" styleClass="outputText2">
                                    <f:convertDateTime pattern="dd/MM/yyyy HH:mm" />
                                </h:outputText>

                                <h:outputText value="Tipo Ingreso:" styleClass="outputText1Subtitle" />
                                <h:outputText value="#{ManagedIngresoAlmacen.ingresosAlmacen.tiposIngresoAlmacen.nombreTipoIngresoAlmacen}" styleClass="outputText2" />
                                <h:outputText value="Nro. Orden Compra:" styleClass="outputText1Subtitle" />
                                <h:outputText value="#{ManagedIngresoAlmacen.ingresosAlmacen.ordenesCompra.nroOrdenCompra}" styleClass="outputText2" />

                                <h:outputText value="Proveedor:" styleClass="outputText1Subtitle" />
                                <h:outputText value="#{ManagedIngresoAlmacen.ingresosAlmacen.proveedores.nombreProveedor}" styleClass="outputText2" />
                                <h:outputText value="Pais Proveedor:" styleClass="outputText1Subtitle" />
                                <h:outputText value="#{ManagedIngresoAlmacen.ingresosAlmacen.proveedores.paises.nombrePais}" styleClass="outputText2" />
                            </h:panelGrid> 

                            <h:panelGroup rendered="#{ManagedIngresoAlmacen.salidasAsociadasAlmacenListSize ne 0}"> 
                                <br/>
                                <h:outputText value="*IMPORTANTE: Estas salidas automáticas, asociadas al Ingreso, serán anuladas juntamente con el Ingreso: " styleClass="outputText2" rendered="#{ManagedIngresoAlmacen.tipoSalidaAutomatico}"/>

                                <rich:dataTable value="#{ManagedIngresoAlmacen.salidasAsociadasAlmacenList}"
                                            var="data"
                                            id="dataSalidasAsociadasAlmacenAprobar"
                                            onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                            onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                            headerClass="headerClassACliente" >

                                    <rich:column styleClass ="#{data.colorFila}">
                                        <f:facet name="header">
                                            <h:outputText value="Nro Salida Almacen"  />
                                        </f:facet>
                                        <h:outputText  value="#{data.nroSalidaAlmacen}"  />
                                    </rich:column>

                                    <rich:column styleClass ="#{data.colorFila}">
                                        <f:facet name="header">
                                            <h:outputText value="Fecha Salida Almacen"  />
                                        </f:facet>
                                        <h:outputText value="#{data.fechaSalidaAlmacen}"  >
                                            <f:convertDateTime pattern="dd/MM/yyyy HH:mm" />
                                        </h:outputText>
                                    </rich:column>

                                    <rich:column styleClass ="#{data.colorFila}">
                                        <f:facet name="header">
                                            <h:outputText value="Area Empresa"  />
                                        </f:facet>
                                        <h:outputText value="#{data.areasEmpresa.nombreAreaEmpresa}"  />
                                    </rich:column>

                                    <rich:column styleClass ="#{data.colorFila}">
                                        <f:facet name="header">
                                            <h:outputText value="Tipo Salida"  />
                                        </f:facet>
                                        <h:outputText value="#{data.tiposSalidasAlmacen.nombreTipoSalidaAlmacen}"  />
                                    </rich:column>

                                    <rich:column styleClass ="#{data.colorFila}">
                                        <f:facet name="header">
                                            <h:outputText value="Personal"  />
                                        </f:facet>
                                        <h:outputText value="#{data.personal.nombrePersonal}"  />
                                    </rich:column>

                                   <rich:column styleClass ="#{data.colorFila}">
                                        <f:facet name="header">
                                            <h:outputText value="Detalle"  />
                                        </f:facet>
                                        <a4j:commandLink action="#{ManagedIngresoAlmacen.verReporteSalidaAlmacen_action}" 
                                                         oncomplete="openPopup('../salidasAlmacen/reporteSalidaAlmacenJasper.jsf?reporte=../salidasAlmacen/reporteSalida.jasper');"
                                                         >
                                            <h:graphicImage url="../img/report.png" />
                                            <f:setPropertyActionListener value="#{data}" target="#{ManagedIngresoAlmacen.salidaAsociadaGestionar}"/>
                                        </a4j:commandLink>
                                    </rich:column>

                                </rich:dataTable>

                            </h:panelGroup>
                            <br/>
                            <h:panelGrid columns="1">
                                <h:outputText value="Motivo/Observación:" styleClass="outputText1Subtitle"/>
                                <h:inputTextarea value="#{ManagedIngresoAlmacen.ingresoTransaccionSolicitudGestionar.observacionValidador}" styleClass="inputText" cols="100" rows="4" />
                            </h:panelGrid>
                            <br/>
                            <a4j:commandButton styleClass="btn" value="Registrar Aprobación"
                                               oncomplete="alert('#{ManagedIngresoAlmacen.mensaje}'); if(#{ManagedIngresoAlmacen.registradoCorrectamente}){javascript:Richfaces.hideModalPanel('panelAprobarTransaccionSolicitud');} "
                                               reRender="dataIngresosAlmacen"
                                               action="#{ManagedIngresoAlmacen.aprobarSolicitudAnularEditarIngresoAlmacen_action}" >
                            </a4j:commandButton>
                            <input type="button" value="Cancelar" onclick="javascript:Richfaces.hideModalPanel('panelAprobarTransaccionSolicitud')" class="btn" />
                        </div>
                    </h:panelGroup>
                </a4j:form>
            </rich:modalPanel>    
                        

            <rich:modalPanel id="panelDenegarTransaccionSolicitud"  minHeight="230"  minWidth="750"
                             height="230" width="750"
                             zindex="200"
                             headerClass="headerClassACliente"
                             resizeable="false" style="overflow :auto" >
                <f:facet name="header">
                    <h:outputText id="txtTituloPanelDenegar" escape="false"
                                  value="<center>DENEGAR SOLICITUD DE #{ManagedIngresoAlmacen.ingresosAlmacen.ingresoTransaccionSolicitud.tipoTransaccionSolicitudAlmacen.nombreTipoTransaccionSolicitudAlmacen}</center>"/>
                </f:facet>
                <a4j:form>
                    <h:panelGroup id="contenidoDenegarTransaccionSolicitud">
                        <div align="center">
                            <h:panelGrid columns="4">
                                <h:outputText value="Nro. Ingreso:" styleClass="outputText1Subtitle" />
                                <h:outputText value="#{ManagedIngresoAlmacen.ingresosAlmacen.nroIngresoAlmacen}" styleClass="outputText2" style="width:50px" />
                                <h:outputText value="Fecha Ingreso:" styleClass="outputText1Subtitle" />
                                <h:outputText value="#{ManagedIngresoAlmacen.ingresosAlmacen.fechaIngresoAlmacen}" styleClass="outputText2">
                                    <f:convertDateTime pattern="dd/MM/yyyy HH:mm" />
                                </h:outputText>

                                <h:outputText value="Tipo Ingreso:" styleClass="outputText1Subtitle" />
                                <h:outputText value="#{ManagedIngresoAlmacen.ingresosAlmacen.tiposIngresoAlmacen.nombreTipoIngresoAlmacen}" styleClass="outputText2" />
                                <h:outputText value="Nro. Orden Compra:" styleClass="outputText1Subtitle" />
                                <h:outputText value="#{ManagedIngresoAlmacen.ingresosAlmacen.ordenesCompra.nroOrdenCompra}" styleClass="outputText2" />

                                <h:outputText value="Proveedor:" styleClass="outputText1Subtitle" />
                                <h:outputText value="#{ManagedIngresoAlmacen.ingresosAlmacen.proveedores.nombreProveedor}" styleClass="outputText2" />
                                <h:outputText value="Pais Proveedor:" styleClass="outputText1Subtitle" />
                                <h:outputText value="#{ManagedIngresoAlmacen.ingresosAlmacen.proveedores.paises.nombrePais}" styleClass="outputText2" />
                            </h:panelGrid> 

                            <h:panelGroup rendered="#{ManagedIngresoAlmacen.salidasAsociadasAlmacenListSize ne 0}"> 
                                <br/>
                                <h:outputText value="Salidas automáticas asociadas al Ingreso: " styleClass="outputText2" rendered="#{ManagedIngresoAlmacen.tipoSalidaAutomatico}"/>

                                <rich:dataTable value="#{ManagedIngresoAlmacen.salidasAsociadasAlmacenList}"
                                            var="data"
                                            id="dataSalidasAsociadasAlmacenAprobar"
                                            onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                            onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                            headerClass="headerClassACliente" >

                                    <rich:column styleClass ="#{data.colorFila}">
                                        <f:facet name="header">
                                            <h:outputText value="Nro Salida Almacen"  />
                                        </f:facet>
                                        <h:outputText  value="#{data.nroSalidaAlmacen}"  />
                                    </rich:column>

                                    <rich:column styleClass ="#{data.colorFila}">
                                        <f:facet name="header">
                                            <h:outputText value="Fecha Salida Almacen"  />
                                        </f:facet>
                                        <h:outputText value="#{data.fechaSalidaAlmacen}"  >
                                            <f:convertDateTime pattern="dd/MM/yyyy HH:mm" />
                                        </h:outputText>
                                    </rich:column>

                                    <rich:column styleClass ="#{data.colorFila}">
                                        <f:facet name="header">
                                            <h:outputText value="Area Empresa"  />
                                        </f:facet>
                                        <h:outputText value="#{data.areasEmpresa.nombreAreaEmpresa}"  />
                                    </rich:column>

                                    <rich:column styleClass ="#{data.colorFila}">
                                        <f:facet name="header">
                                            <h:outputText value="Tipo Salida"  />
                                        </f:facet>
                                        <h:outputText value="#{data.tiposSalidasAlmacen.nombreTipoSalidaAlmacen}"  />
                                    </rich:column>

                                    <rich:column styleClass ="#{data.colorFila}">
                                        <f:facet name="header">
                                            <h:outputText value="Personal"  />
                                        </f:facet>
                                        <h:outputText value="#{data.personal.nombrePersonal}"  />
                                    </rich:column>

                                   <rich:column styleClass ="#{data.colorFila}">
                                        <f:facet name="header">
                                            <h:outputText value="Detalle"  />
                                        </f:facet>
                                        <a4j:commandLink action="#{ManagedIngresoAlmacen.verReporteSalidaAlmacen_action}" 
                                                         oncomplete="openPopup('../salidasAlmacen/reporteSalidaAlmacenJasper.jsf?reporte=../salidasAlmacen/reporteSalida.jasper');"
                                                         >
                                            <h:graphicImage url="../img/report.png" />
                                            <f:setPropertyActionListener value="#{data}" target="#{ManagedIngresoAlmacen.salidaAsociadaGestionar}"/>
                                        </a4j:commandLink>
                                    </rich:column>

                                </rich:dataTable>

                            </h:panelGroup>
                            <br/>
                            <h:panelGrid columns="1">
                                <h:outputText value="Motivo/Observación:" styleClass="outputText1Subtitle"/>
                                <h:inputTextarea value="#{ManagedIngresoAlmacen.ingresoTransaccionSolicitudGestionar.observacionValidador}" styleClass="inputText" cols="100" rows="4" />
                            </h:panelGrid>
                            <br/>
                            <a4j:commandButton styleClass="btn" value="Registrar Denegación"
                                               oncomplete="alert('#{ManagedIngresoAlmacen.mensaje}'); if(#{ManagedIngresoAlmacen.registradoCorrectamente}){javascript:Richfaces.hideModalPanel('panelDenegarTransaccionSolicitud');}"
                                               reRender="dataIngresosAlmacen"
                                               action="#{ManagedIngresoAlmacen.rechazarSolicitudAnularEditarIngresoAlmacen_action}" >
                            </a4j:commandButton>
                            <input type="button" value="Cancelar" onclick="javascript:Richfaces.hideModalPanel('panelDenegarTransaccionSolicitud')" class="btn" />
                        </div>
                    </h:panelGroup>
                </a4j:form>
            </rich:modalPanel>    
                        
            <a4j:status id="statusPeticion"
                        onstart="Richfaces.showModalPanel('ajaxLoadingModalBox',{width:100, top:100})"
                        onstop="Richfaces.hideModalPanel('ajaxLoadingModalBox')">
            </a4j:status>
            <rich:modalPanel id="ajaxLoadingModalBox" minHeight="100"
                             minWidth="200" height="80" width="400" zindex="200" onshow="window.focus();">

                <div align="center">
                    <h:graphicImage value="../img/load2.gif" />
                </div>
            </rich:modalPanel>

        </body>
    </html>

</f:view>

