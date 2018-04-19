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
            <script type="text/javascript" src="../js/general.js" ></script>
            <%--inicio alejandro 2--%>
            <style>
                .b
                {

                    background-color:#FF0000;
                }
            </style>
            <%--final alejandro 2--%>
            <script>
                function getCodigo(cod_maquina, codigo) {
                    //  alert(codigo);
                    location = '../partes_maquinaria/navegador_partes_maquinaria.jsf?cod_maquina=' + cod_maquina + '&codigo=' + codigo;
                }

                function editarItem(nametable) {
                    var count = 0;
                    var elements = document.getElementById(nametable);
                    var rowsElement = elements.rows;
                    for (var i = 1; i < rowsElement.length; i++) {
                        var cellsElement = rowsElement[i].cells;
                        var cel = cellsElement[0];
                        if (cel.getElementsByTagName('input')[0].type == 'checkbox') {
                            if (cel.getElementsByTagName('input')[0].checked) {
                                count++;
                            }

                        }

                    }
                    if (count == 1) {
                        return true;
                    } else if (count == 0) {
                        alert('No escogio ningun registro');
                        return false;
                    }
                    else if (count > 1) {
                        alert('Solo puede escoger un registro');
                        return false;
                    }
                }


                function asignar(nametable) {

                    var count = 0;
                    var elements = document.getElementById(nametable);
                    var rowsElement = elements.rows;
                    for (var i = 1; i < rowsElement.length; i++) {
                        var cellsElement = rowsElement[i].cells;
                        var cel = cellsElement[0];
                        alert('hola');
                        if (cel.getElementsByTagName('input')[0].type == 'checkbox') {
                            if (cel.getElementsByTagName('input')[0].checked) {
                                count++;
                            }
                        }
                    }
                    if (count == 0) {
                        alert('No selecciono ningun Registro');
                        return false;
                    } else {
                        if (confirm('Desea Asignar como Area Raiz')) {
                            if (confirm('Esta seguro de Asignar como Area Raiz')) {
                                return true;
                            } else {
                                return false;
                            }
                        } else {
                            return false;
                        }

                    }

                }
                function eliminar(nametable) {
                    var count1 = 0;
                    var elements1 = document.getElementById(nametable);
                    var rowsElement1 = elements1.rows;
                    //alert(rowsElement1.length);
                    for (var i = 1; i < rowsElement1.length; i++) {
                        var cellsElement1 = rowsElement1[i].cells;
                        var cel1 = cellsElement1[0];
                        if (cel1.getElementsByTagName('input').length > 0) {
                            if (cel1.getElementsByTagName('input')[0].type == 'checkbox') {
                                if (cel1.getElementsByTagName('input')[0].checked) {
                                    count1++;
                                }
                            }
                        }

                    }
                    //alert(count1);
                    if (count1 == 0) {
                        alert('No escogio ningun registro');
                        return false;
                    } else {

                        var count = 0;
                        var elements = document.getElementById(nametable);
                        var rowsElement = elements.rows;

                        for (var i = 0; i < rowsElement.length; i++) {
                            var cellsElement = rowsElement[i].cells;
                            var cel = cellsElement[0];
                            if (cel.getElementsByTagName('input').length > 0) {
                                if (cel.getElementsByTagName('input')[0].type == 'checkbox') {
                                    if (cel.getElementsByTagName('input')[0].checked) {
                                        count++;
                                    }
                                }
                            }

                        }
                        if (count == 0) {
                            //alert('No escogio ningun registro');
                            return false;
                        }
                        //var cantidadeliminar=document.getElementById('form1:cantidadeliminar');
                        //cantidadeliminar.value=count;
                        return true;

                    }
                }

                function validarSeleccion(nametable) {
                    var count = 0;
                    var elements = document.getElementById(nametable);
                    var rowsElement = elements.rows;
                    for (var i = 1; i < rowsElement.length; i++) {
                        var cellsElement = rowsElement[i].cells;
                        var cel = cellsElement[0];
                        if (cel.getElementsByTagName('input')[0].type == 'checkbox') {
                            if (cel.getElementsByTagName('input')[0].checked) {
                                count++;
                            }

                        }

                    }
                    if (count == 1) {
                        return true;
                    } else if (count == 0) {
                        alert('No escogio ningun registro');
                        return false;
                    }
                    else if (count > 1) {
                        alert('Solo puede escoger un registro');
                        return false;
                    }
                }
                function verManteminiento(cod_maquina, codigo) {
                    location = '../partes_maquinaria/navegador_partes_maquinaria.jsf?cod_maquina=' + cod_maquina + '&codigo=' + codigo;
                }
                function openPopup(url) {
                    window.open(url, 'DETALLE', 'top=50,left=200,width=800,height=500,scrollbars=1,resizable=1');
                }
                function openPopup1(f, url) {
                    //window.open(url,'DETALLE','top=50,left=200,width=800,height=600,scrollbars=1,resizable=1');
                    f.action = url;
                    f.submit();
                }
            </script>
        </head>
        <body >
            <a4j:form id="form1" >
                <div align="center">
                    <h:outputText value="#{ManagedSalidaAlmacen.cargarContenidoSalidasAlmacenAprobarCambioEstado}"/>
                    <h:outputText styleClass="outputTextTitulo"  value="VALIDAR SOLICITUDES DE EDITAR/ANULAR SALIDA" />
                    <br>
                    <%--a4j:commandLink onclick="Richfaces.showModalPanel('panelBuscarSalidaAlmacen')">
                        <h:outputText value="Buscar" styleClass="outputText1" />
                        <h:graphicImage url="../img/buscar.png">
                    
                        </h:graphicImage>
                    </a4j:commandLink--%>
                    <br>
                    <rich:panel style="width:50%" id="panelBusquedaIngresosValidar" headerClass="headerClassACliente">
                        <f:facet name="header">
                            <h:outputText value="Buscador" />
                        </f:facet>
                        <h:panelGrid columns="2">
                            <h:outputText value="Por Revisión: " styleClass="outputText1Subtitle" />
                            <h:selectOneMenu id="sorTipoRevision" value="#{ManagedSalidaAlmacen.salidasAlmacenBuscar.salidaTransaccionSolicitud.tipoRevision}" styleClass="inputText" >
                                <f:selectItem itemLabel="Todos" itemValue="1" />
                                <f:selectItem itemLabel="Pendientes" itemValue="2"/>
                                <f:selectItem itemLabel="Aprobados" itemValue="3"/>
                                <f:selectItem itemLabel="Rechazados" itemValue="4"/>
                            </h:selectOneMenu>
                        </h:panelGrid> 
                        <h:panelGrid columns="4">
                            <h:outputText value="Por Fecha Solicitud: DESDE: " styleClass="outputText1Subtitle" />
                            <rich:calendar id="txtFechaSolicitudInicio" datePattern="dd/MM/yyyy" styleClass="inputText" enableManualInput="false" oninputblur="if(this.value!=''){valFecha(this)}else{return true;}" value="#{ManagedSalidaAlmacen.salidasAlmacenBuscar.salidaTransaccionSolicitud.fechaSolicitud}" />
                            <h:outputText value="HASTA: " styleClass="outputText1Subtitle" />
                            <rich:calendar id="txtfechaSolicitudFin" datePattern="dd/MM/yyyy" styleClass="inputText" enableManualInput="false" oninputblur="if(this.value!=''){valFecha(this)}else{return true;}" value="#{ManagedSalidaAlmacen.salidasAlmacenBuscar.salidaTransaccionSolicitud.fechaSolicitudFin}"  />

                            <h:outputText value="Por Fecha Validación: DESDE: " styleClass="outputText1Subtitle" />
                            <rich:calendar id="txtfechaValidacionInicio" datePattern="dd/MM/yyyy" styleClass="inputText" enableManualInput="false" oninputblur="if(this.value!=''){valFecha(this)}else{return true;}" value="#{ManagedSalidaAlmacen.salidasAlmacenBuscar.salidaTransaccionSolicitud.fechaValidacion}" />
                            <h:outputText value="HASTA: " styleClass="outputText1Subtitle" />
                            <rich:calendar id="txtfechaValidacionFin" datePattern="dd/MM/yyyy" styleClass="inputText" enableManualInput="false" oninputblur="if(this.value!=''){valFecha(this)}else{return true;}" value="#{ManagedSalidaAlmacen.salidasAlmacenBuscar.salidaTransaccionSolicitud.fechaValidacionFin}" />
                        </h:panelGrid> 
                        <div align="center">
                            <a4j:commandButton styleClass="btn" value="Buscar"  action="#{ManagedSalidaAlmacen.cargarSalidasAlmacenPorTransaccionSolicitud_action}" reRender="dataSalidasAlmacen, controles" />
                            <a4j:commandButton styleClass="btn" value="Limpiar"  action="#{ManagedSalidaAlmacen.reiniciarCamposBusqueda_action}" reRender="panelBusquedaIngresosValidar" />
                            
                        </div>
                    </rich:panel>

                    <rich:dataTable value="#{ManagedSalidaAlmacen.salidasAlmacenList}" var="data"
                                    id="dataSalidasAlmacen"
                                    onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                    onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                    headerClass="headerClassACliente" >
                        <f:facet name="header">
                            <rich:columnGroup>
                                <rich:column colspan="10">
                                    <h:outputText value="DATOS DE LA SALIDA"/>
                                </rich:column>
                                <rich:column colspan="5">
                                    <h:outputText value="DATOS DE LA SOLICITUD"/>
                                </rich:column>
                                <rich:column breakBefore="true">
                                   <h:outputText value="Nro Salida"  />
                                </rich:column>
                                <rich:column>
                                   <h:outputText value="Fecha Salida"  />
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Nro de Lote"  />
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Nro O.T."  />
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Producto"  />
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Presentacion"  />
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Area / Departamento"  />
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Estado Salida"  />
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Tipo Salida"  />
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Observaciones"  />
                                </rich:column>
                                 <rich:column>
                                    <h:outputText value="Estado Transaccion Salida"  />
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Solicitante de Transacción"  />
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Fecha Solicitud"  />
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Fecha Validación"  />
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Acciones"  />
                                </rich:column>
                            </rich:columnGroup>

                        </f:facet>
                        <rich:column styleClass ="#{data.colorFila}">
                            <h:outputText  value="#{data.nroSalidaAlmacen}"  />
                        </rich:column>
                        
                        <rich:column styleClass ="#{data.colorFila}">
                            <h:outputText value="#{data.fechaSalidaAlmacen}">
                                <f:convertDateTime pattern="dd/MM/yyyy HH:mm" />
                            </h:outputText>
                        </rich:column>
                        
                        <rich:column styleClass ="#{data.colorFila}">
                            <h:outputText value="#{data.codLoteProduccion}"  />
                        </rich:column>
                        
                        <rich:column styleClass ="#{data.colorFila}">
                            <h:outputText value="#{data.ordenTrabajo}"  />
                        </rich:column>

                        <rich:column styleClass ="#{data.colorFila}">
                            <h:outputText value="#{data.componentesProd.nombreProdSemiterminado}"  />
                        </rich:column>

                        <rich:column styleClass ="#{data.colorFila}">
                            <h:outputText value="#{data.presentacionesProducto.nombreProductoPresentacion}"  />
                        </rich:column>

                        <rich:column styleClass ="#{data.colorFila}">
                            <h:outputText value="#{data.areasEmpresa.nombreAreaEmpresa}"  />
                        </rich:column>

                        <rich:column styleClass ="#{data.colorFila}">
                            <h:outputText value="#{data.estadosSalidasAlmacen.nombreEstadoSalidaAlmacen}"  />
                        </rich:column>

                        <rich:column styleClass ="#{data.colorFila}">
                            <h:outputText value="#{data.tiposSalidasAlmacen.nombreTipoSalidaAlmacen}"  />
                        </rich:column>

                        <rich:column styleClass ="#{data.colorFila}">
                            <h:outputText value="#{data.obsSalidaAlmacen}"  />
                        </rich:column>
                        
                        <rich:column styleClass ="#{data.colorFila}">
                            <h:outputText value="<span style='background-color: #{data.salidaTransaccionSolicitud.estadoTransaccionSolicitud.codEstadoTransaccionSolicitud==1?'#FCCCC1': data.salidaTransaccionSolicitud.estadoTransaccionSolicitud.codEstadoTransaccionSolicitud==2?'#7FFFD4':''} '>#{data.salidaTransaccionSolicitud.estadoTransaccionSolicitud.nombreEstadoTransaccionSolicitud}</span>" escape="false"  />
                        </rich:column>

                        <rich:column styleClass ="#{data.colorFila}">
                            <h:outputText value="#{data.salidaTransaccionSolicitud.personalSolicitante.nombrePersonal}" escape="false"  />
                            <h:outputText value=": <br/>#{data.salidaTransaccionSolicitud.observacionSolicitante}" escape="false" rendered="#{data.salidaTransaccionSolicitud.observacionSolicitante != ''}" />
                        </rich:column>
                        
                        <rich:column styleClass ="#{data.colorFila}">
                            <h:outputText value="#{data.salidaTransaccionSolicitud.fechaSolicitud}">
                                <f:convertDateTime pattern="dd/MM/yyyy HH:mm" />
                            </h:outputText>
                        </rich:column>
                        
                        <rich:column styleClass ="#{data.colorFila}">
                            <h:outputText value="#{data.salidaTransaccionSolicitud.fechaValidacion}">
                                <f:convertDateTime pattern="dd/MM/yyyy HH:mm" />
                            </h:outputText>
                        </rich:column>

                        <rich:column styleClass ="#{data.colorFila}">
                            <rich:dropDownMenu rendered="#{ManagedSalidaAlmacen.administradorAlmacen && (data.salidaTransaccionSolicitud.estadoTransaccionSolicitud.codEstadoTransaccionSolicitud==1 || data.salidaTransaccionSolicitud.estadoTransaccionSolicitud.codEstadoTransaccionSolicitud==2)}"  >
                                <f:facet name="label">
                                    <h:outputText value="Acciones"/>
                                </f:facet>

                                <rich:menuItem submitMode="ajax" value="Aprobar" rendered="#{ManagedSalidaAlmacen.permisoValidarAnular && ManagedSalidaAlmacen.permisoValidarEditar}">
                                    <a4j:support event="onclick" 
                                                 
                                                 action="#{ManagedSalidaAlmacen.nuevaSalidaTransaccionSolicitudValidar_action}"
                                                 oncomplete="if(#{ManagedSalidaAlmacen.mensaje ne ''}){alert('#{ManagedSalidaAlmacen.mensaje}')}else{if(#{ManagedSalidaAlmacen.salidaAlmacenGestionar.salidaTransaccionSolicitud.estadoTransaccionSolicitud.codEstadoTransaccionSolicitud == 1}){Richfaces.showModalPanel('panelAprobarTransaccionSolicitud')}else{location='editarSalidaAlmacen.jsf';}}" 
                                                 reRender="contenidoAprobarTransaccionSolicitud, txtTituloPanelAprobar" 
                                                 >
                                        <f:setPropertyActionListener value="#{data}" target="#{ManagedSalidaAlmacen.salidaAlmacenGestionar}"/>
                                    </a4j:support>    
                                </rich:menuItem>

                                <rich:menuItem submitMode="ajax" value="Denegar" rendered="#{ManagedSalidaAlmacen.permisoValidarAnular && ManagedSalidaAlmacen.permisoValidarEditar}">
                                    <a4j:support event="onclick" 
                                                 
                                                 action="#{ManagedSalidaAlmacen.nuevaSalidaTransaccionSolicitudValidar_action}"
                                                 oncomplete="Richfaces.showModalPanel('panelDenegarTransaccionSolicitud')" 
                                                 reRender="contenidoDenegarTransaccionSolicitud, txtTituloPanelDenegar "  >
                                        <f:setPropertyActionListener value="#{data}" target="#{ManagedSalidaAlmacen.salidaAlmacenGestionar}"/>
                                    </a4j:support>    
                                </rich:menuItem>
                            </rich:dropDownMenu>                                
                        </rich:column>  
                        <%--h:column>
                           <f:facet name="header">
                               <h:outputText value=""  />
                           </f:facet>
                           <a4j:commandLink onclick="openPopup('navegadorDetalleSalidasAlmacen.jsf?codSalidaAlmacen=#{data.codSalidaAlmacen}&codAlmacen=#{data.almacenes.codAlmacen}')">
                               <h:graphicImage url="../img/areasdependientes.png" >
                               </h:graphicImage>
                           </a4j:commandLink>

                         </h:column--%>

                    </rich:dataTable>
                    
                    <h:panelGrid columns="2"  width="50" id="controles">
                        <a4j:commandLink  action="#{ManagedSalidaAlmacen.salidas_validar_atras_action}" reRender="dataSalidasAlmacen,controles" timeout="7200"  rendered="#{ManagedSalidaAlmacen.begin!='1'}" >
                            <h:graphicImage url="../img/previous.gif"  style="border:0px solid red"   alt="PAGINA ANTERIOR"  />
                        </a4j:commandLink>
                        <a4j:commandLink  action="#{ManagedSalidaAlmacen.salidas_validar_siguiente_action}" reRender="dataSalidasAlmacen,controles" timeout="7200"  rendered="#{ManagedSalidaAlmacen.cantidadfilas>='10'}">
                            <h:graphicImage url="../img/next.gif"  style="border:0px solid red"  alt="PAGINA SIGUIENTE" />
                        </a4j:commandLink>
                    </h:panelGrid>



                    <%-- <ws:datascroller fordatatable="dataAreasEmpresa"  />--%>
                    <br>
                    <%--a4j:commandButton value="Generar Salida Almacen" styleClass="btn"  onclick="location='agregarSalidaAlmacen.jsf'"  />
                    <a4j:commandButton value="Imprimir" styleClass = "btn" action="#{ManagedSalidaAlmacen.verReporteSalidaAlmacen_action}" oncomplete="openPopup('reporteSalidaAlmacenJasper.jsf?reporte=reporteSalidaAlmacen.jasper');" rendered="#{ManagedSalidaAlmacen.usuario.almacenesGlobal.codAlmacen!='12'}" />
                    <a4j:commandButton value="Imprimir" styleClass = "btn" action="#{ManagedSalidaAlmacen.verReporteSalidaAlmacen_action}" oncomplete="openPopup('reporteSalidaAlmacenJasper.jsf?reporte=reporteSalidaAlmacenProveedor.jasper');" rendered="#{ManagedSalidaAlmacen.usuario.almacenesGlobal.codAlmacen=='12'}" />
                    <a4j:commandButton value="Imprimir Mantenimiento" styleClass = "btn" action="#{ManagedSalidaAlmacen.verReporteSalidaAlmacen_action}" oncomplete="openPopup('reporteSalidasAlmacenMantenimiento.jsf');" rendered="#{ManagedSalidaAlmacen.usuario.almacenesGlobal.codAlmacen=='4'}" />
                    <a4j:commandButton value="Editar" styleClass = "btn" action="#{ManagedSalidaAlmacen.editarSalidaAlmacen_action}" oncomplete="if(#{ManagedSalidaAlmacen.mensaje!=''}){alert('#{ManagedSalidaAlmacen.mensaje}');}else{location='editarSalidaAlmacen.jsf';}" />
                    <a4j:commandButton value="Anular" styleClass = "btn" action="#{ManagedSalidaAlmacen.anularSalidasAlmacen_action}"onclick="if(confirm('Esta seguro de anular esta salida?')==false){return false;}" oncomplete="if(#{ManagedSalidaAlmacen.mensaje!=''}){alert('#{ManagedSalidaAlmacen.mensaje}');}else {alert('La solicitud fue anulada');}" reRender="dataSalidasAlmacen" /--%>
                    <%--<a4j:commandButton value="Aprobar/Ejecutar Solicitud" styleClass = "btn" action="#{ManagedSalidaAlmacen.aprobarSolicitudAnularEditarSalidaAlmacen_action}"--%>
                    <!--oncomplete="if(# {ManagedSalidaAlmacen.mensaje!=''}){alert('# {ManagedSalidaAlmacen.mensaje}');}" reRender="dataSalidasAlmacen" onclick="if(validarSalidasSeleccionadas()){ if(confirm('¿Esta seguro de aprobar la solicitud?')==false){return false;} }else{return;}" >-->
                    <%--</a4j:commandButton>--%>
                    <%--
                    <a4j:commandButton value="Generar Salida Almacen" styleClass="btn"  action="#{ManagedIngresoAlmacenOrdenCompra.generarIngresoAlmacen_action}"
                    reRender="contenidoFrecuenciaMantenimentoMaquinaria" oncomplete="if(#{ManagedIngresoAlmacenOrdenCompra.mensaje!=''}){alert('#{ManagedIngresoAlmacenOrdenCompra.mensaje}')}" />
                    --%>

                </div>


                <!--cerrando la conexion-->
                <h:outputText value="#{ManagedFrecuenciaMantenimientoMaquinaria.closeConnection}"  />

            </a4j:form>


            <rich:modalPanel id="panelBuscarSalidaAlmacen"  minHeight="220"  minWidth="1000"
                             height="220" width="1000"
                             zindex="200"
                             headerClass="headerClassACliente"
                             resizeable="false" style="overflow :auto" >
                <f:facet name="header">
                    <h:outputText value="Buscar Salida Almacen"/>
                </f:facet>
                <a4j:form>
                    <h:panelGroup id="contenidoBuscarSalidaAlmacen">
                        <h:panelGrid columns="4">

                            <h:outputText value="Gestion:" styleClass="outputText1" />
                            <h:selectOneMenu value="#{ManagedSalidaAlmacen.salidasAlmacenBuscar.gestiones.codGestion}" styleClass="inputText" >
                                <f:selectItems value="#{ManagedSalidaAlmacen.gestionesList}"  />
                            </h:selectOneMenu>

                            <h:outputText value="Nro Salida:" styleClass="outputText1" />
                            <h:inputText value="#{ManagedSalidaAlmacen.salidasAlmacenBuscar.nroSalidaAlmacen}" styleClass="inputText" size="5" onkeypress="valNum(event);" />

                            <h:outputText value="Nro Lote:" styleClass="outputText1" />
                            <h:inputText value="#{ManagedSalidaAlmacen.salidasAlmacenBuscar.codLoteProduccion}" styleClass="inputText" size="5" onkeypress="valNum(event);" />

                            <h:outputText value="Tipo Salida:" styleClass="outputText1" />
                            <h:selectOneMenu value="#{ManagedSalidaAlmacen.salidasAlmacenBuscar.tiposSalidasAlmacen.codTipoSalidaAlmacen}" styleClass="inputText" >
                                <f:selectItems value="#{ManagedSalidaAlmacen.tiposSalidaAlmacenList}"  />
                            </h:selectOneMenu>




                            <h:outputText value="Fecha inicio Salida" styleClass="outputText1" />
                            <rich:calendar  datePattern="dd/MM/yyyy" styleClass="inputText" zindex="200" value="#{ManagedSalidaAlmacen.salidasAlmacenBuscar.fechaSalidaAlmacen}" id="fechaInicio" enableManualInput="true" oninputblur="if(this.value!=''){valFecha(this)}else{return true;}"  />

                            <h:outputText value="Estados Salida Almacen:" styleClass="outputText1" />
                            <h:selectOneMenu value="#{ManagedSalidaAlmacen.salidasAlmacenBuscar.estadosSalidasAlmacen.codEstadoSalidaAlmacen}" styleClass="inputText" >
                                <f:selectItems value="#{ManagedSalidaAlmacen.estadosSalidaAlmacenList}"  />
                            </h:selectOneMenu>

                            <h:outputText value="Area Destino:" styleClass="outputText1" />
                            <h:selectOneMenu value="#{ManagedSalidaAlmacen.salidasAlmacenBuscar.areasEmpresa.codAreaEmpresa}" styleClass="inputText" >
                                <f:selectItems value="#{ManagedSalidaAlmacen.areasEmpresalist}"  />
                            </h:selectOneMenu>

                            <h:outputText value="Producto:" styleClass="outputText1" />
                            <h:selectOneMenu value="#{ManagedSalidaAlmacen.salidasAlmacenBuscar.componentesProd.codCompprod}" styleClass="inputText" >
                                <f:selectItems value="#{ManagedSalidaAlmacen.componentesProdList}"  />
                            </h:selectOneMenu>

                            <h:outputText value="Orden de Trabajo:" styleClass="outputText1" />
                            <h:inputText value="#{ManagedSalidaAlmacen.salidasAlmacenBuscar.ordenTrabajo}" styleClass="inputText" size="5" onkeypress="valNum(event);" />



                        </h:panelGrid>
                        <div align="center">
                            <a4j:commandButton styleClass="btn" value="Buscar" onclick="javascript:Richfaces.hideModalPanel('panelBuscarSalidaAlmacen');"
                                               action="#{ManagedSalidaAlmacen.buscarSalidaAlmacen_action }"
                                               reRender="dataSalidasAlmacen,controles"
                                               />
                            <input type="button" value="Cancelar" onclick="javascript:Richfaces.hideModalPanel('panelBuscarSalidaAlmacen')" class="btn" />
                        </div>
                    </h:panelGroup>
                </a4j:form>
            </rich:modalPanel>

            <rich:modalPanel id="panelAprobarTransaccionSolicitud"  minHeight="240"  minWidth="700"
                             height="240" width="700"
                             zindex="200"
                             headerClass="headerClassACliente"
                             resizeable="false" style="overflow :auto" >
                <f:facet name="header">
                    <h:outputText id="txtTituloPanelAprobar" escape="false" value="<center>APROBAR SOLICITUD DE #{ManagedSalidaAlmacen.salidaAlmacenGestionar.salidaTransaccionSolicitud.tipoTransaccionSolicitudAlmacen.nombreTipoTransaccionSolicitudAlmacen}</center>"/>
                </f:facet>
                <a4j:form>
                    <h:panelGroup id="contenidoAprobarTransaccionSolicitud">
                        <div align="center">
                            <h:panelGrid columns="4">
                                <h:outputText value="Nro. Salida:" styleClass="outputText1Subtitle" />
                                <h:outputText value="#{ManagedSalidaAlmacen.salidaAlmacenGestionar.nroSalidaAlmacen}" styleClass="outputText1" style="width:50px" />
                                <h:outputText value="Fecha Salida:" styleClass="outputText1Subtitle" />
                                <h:outputText value="#{ManagedSalidaAlmacen.salidaAlmacenGestionar.fechaSalidaAlmacen}" styleClass="outputText1">
                                    <f:convertDateTime pattern="dd/MM/yyyy HH:mm" />
                                </h:outputText>

                                <h:outputText value="Nro. Lote:" styleClass="outputText1Subtitle" />
                                <h:outputText value="#{ManagedSalidaAlmacen.salidaAlmacenGestionar.codLoteProduccion}" styleClass="outputText1" />
                                <h:outputText value="Producto:" styleClass="outputText1Subtitle" />
                                <h:outputText value="#{ManagedSalidaAlmacen.salidaAlmacenGestionar.componentesProd.nombreProdSemiterminado}" styleClass="outputText1" />

                                <h:outputText value="Presentación:" styleClass="outputText1Subtitle" />
                                <h:outputText value="#{ManagedSalidaAlmacen.salidaAlmacenGestionar.presentacionesProducto.nombreProductoPresentacion}" styleClass="outputText1" />
                                <h:outputText value="Tipo Salida:" styleClass="outputText1Subtitle" />
                                <h:outputText value="#{ManagedSalidaAlmacen.salidaAlmacenGestionar.tiposSalidasAlmacen.nombreTipoSalidaAlmacen}" styleClass="outputText1" />
                            </h:panelGrid> 
                            <br/>
                            <h:panelGrid columns="1">
                                <h:outputText value="Motivo/Observación:" styleClass="outputText1Subtitle" />
                                <h:inputTextarea value="#{ManagedSalidaAlmacen.salidaTransaccionSolicitudGestionar.observacionValidador}" styleClass="inputText" cols="100" rows="4" />
                            </h:panelGrid>
                            <br/>
                            <a4j:commandButton styleClass="btn" value="Registrar Aprobación"
                                               oncomplete="alert('#{ManagedSalidaAlmacen.mensaje}'); if(#{ManagedSalidaAlmacen.registradoCorrectamente}){javascript:Richfaces.hideModalPanel('panelAprobarTransaccionSolicitud');}"
                                               reRender="dataSalidasAlmacen" 
                                               action="#{ManagedSalidaAlmacen.aprobarSolicitudAnularEditarSalidaAlmacen_action}" 
                                               timeout="72000">
                            </a4j:commandButton>
                            <input type="button" value="Cancelar" onclick="javascript:Richfaces.hideModalPanel('panelAprobarTransaccionSolicitud')" class="btn" />
                        </div>
                    </h:panelGroup>
                </a4j:form>
            </rich:modalPanel>    

            <rich:modalPanel id="panelDenegarTransaccionSolicitud"  minHeight="240"  minWidth="700"
                             height="240" width="700"
                             zindex="200"
                             headerClass="headerClassACliente"
                             resizeable="false" style="overflow :auto" >
                <f:facet name="header">
                    <h:outputText id="txtTituloPanelDenegar" escape="false" value="<center>DENEGAR SOLICITUD DE #{ManagedSalidaAlmacen.salidaAlmacenGestionar.salidaTransaccionSolicitud.tipoTransaccionSolicitudAlmacen.nombreTipoTransaccionSolicitudAlmacen}</center>"/>
                </f:facet>
                <a4j:form>
                    <h:panelGroup id="contenidoDenegarTransaccionSolicitud">
                        <div align="center">
                            <h:panelGrid columns="4">
                                <h:outputText value="Nro. Salida:" styleClass="outputText1Subtitle" />
                                <h:outputText value="#{ManagedSalidaAlmacen.salidaAlmacenGestionar.nroSalidaAlmacen}" styleClass="outputText1" style="width:50px" />
                                <h:outputText value="Fecha Salida:" styleClass="outputText1Subtitle" />
                                <h:outputText value="#{ManagedSalidaAlmacen.salidaAlmacenGestionar.fechaSalidaAlmacen}" styleClass="outputText1">
                                    <f:convertDateTime pattern="dd/MM/yyyy HH:mm" />
                                </h:outputText>

                                <h:outputText value="Nro. Lote:" styleClass="outputText1Subtitle" />
                                <h:outputText value="#{ManagedSalidaAlmacen.salidaAlmacenGestionar.codLoteProduccion}" styleClass="outputText1" />
                                <h:outputText value="Producto:" styleClass="outputText1Subtitle" />
                                <h:outputText value="#{ManagedSalidaAlmacen.salidaAlmacenGestionar.componentesProd.nombreProdSemiterminado}" styleClass="outputText1" />

                                <h:outputText value="Presentación:" styleClass="outputText1Subtitle" />
                                <h:outputText value="#{ManagedSalidaAlmacen.salidaAlmacenGestionar.presentacionesProducto.nombreProductoPresentacion}" styleClass="outputText1" />
                                <h:outputText value="Tipo Salida:" styleClass="outputText1Subtitle" />
                                <h:outputText value="#{ManagedSalidaAlmacen.salidaAlmacenGestionar.tiposSalidasAlmacen.nombreTipoSalidaAlmacen}" styleClass="outputText1" />
                            </h:panelGrid> 
                            <br/>
                            <h:panelGrid columns="1">
                                <h:outputText value="Motivo/Observación:" styleClass="outputText1Subtitle" />
                                <h:inputTextarea value="#{ManagedSalidaAlmacen.salidaTransaccionSolicitudGestionar.observacionValidador}" styleClass="inputText" cols="100" rows="4" />
                            </h:panelGrid>
                            <br/>
                            <a4j:commandButton styleClass="btn" value="Registrar Denegación"
                                               oncomplete="alert('#{ManagedSalidaAlmacen.mensaje}'); if(#{ManagedSalidaAlmacen.registradoCorrectamente}){javascript:Richfaces.hideModalPanel('panelDenegarTransaccionSolicitud');}"
                                               reRender="dataSalidasAlmacen"
                                               action="#{ManagedSalidaAlmacen.rechazarSolicitudAnularEditarSalidaAlmacen_action}" >
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

