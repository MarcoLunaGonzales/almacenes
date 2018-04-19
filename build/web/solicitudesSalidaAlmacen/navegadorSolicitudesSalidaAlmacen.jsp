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
                var i = 0;
                function openPopup(url) {
                    i++;
                    window.open(url, 'DETALLE' + i, 'top=50,left=200,width=800,height=500,scrollbars=1,resizable=1');
                }
                function openPopup1(f, url) {
                    //window.open(url,'DETALLE','top=50,left=200,width=800,height=600,scrollbars=1,resizable=1');
                    f.action = url;
                    f.submit();
                }
            </script>
        </head>
        <body>
            <a4j:form id="form1" >
                <div align="center">
                    <h:outputText value="#{ManagedRegistroSolicitudSalidaAlmacen.cargarContenidoSolicitudesSalidaAlmacen}"/>
                    <h:outputText styleClass="outputTextTitulo"  value="Solicitudes Salidas Almacen" />
                    <br>

                    <a4j:commandLink onclick="Richfaces.showModalPanel('panelBuscarSalidaAlmacen')">
                        <h:outputText value="Buscar" styleClass="outputText1" />
                        <h:graphicImage url="../img/buscar.png">

                        </h:graphicImage>
                    </a4j:commandLink>


                    <br>

                    <rich:dataTable value="#{ManagedRegistroSolicitudSalidaAlmacen.solicitudesSalidaAlmacenList}" var="data"
                                    id="dataSalidasAlmacen"
                                    onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                    onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                    headerClass="headerClassACliente" binding="#{ManagedRegistroSolicitudSalidaAlmacen.solicitudesSalidaAlmacenDataTable}" >
                        <rich:column style="background:#{data.colorFila}">
                            <f:facet name="header">
                                <h:outputText value=""  />
                            </f:facet>
                            <h:selectBooleanCheckbox value="#{data.checked}" rendered="#{data.estadosSolicitudSalidasAlmacen.codEstadoSolicitudSalidaAlmacen!='2' && data.estadosSolicitudSalidasAlmacen.codEstadoSolicitudSalidaAlmacen!='4'}" />
                        </rich:column>
                        <rich:column style="background:#{data.colorFila}">
                            <f:facet name="header">
                                <h:outputText value="Nro Solicitud"  />
                            </f:facet>
                            <h:outputText  value="#{data.codFormSalida}"  />
                        </rich:column>
                        <rich:column style="background:#{data.colorFila}">
                            <f:facet name="header">
                                <h:outputText value="O.T."  />
                            </f:facet>
                            <h:outputText value="#{data.ordenTrabajo}">
                                <f:convertDateTime pattern="dd/MM/yyyy" />
                            </h:outputText>
                        </rich:column>
                        <h:column rendered="#{ManagedSolicitudSalidaAlmacen.usuario.almacenesGlobal.codAlmacen eq '14'}">
                            <f:facet name="header">
                                <h:outputText value="Maquinaria"  />
                            </f:facet>
                            <h:outputText value="#{data.maquinaria.nombreMaquina}"  >
                            </h:outputText>
                        </h:column>
                        <h:column rendered="#{ManagedSolicitudSalidaAlmacen.usuario.almacenesGlobal.codAlmacen eq '14'}">
                            <f:facet name="header">
                                <h:outputText value="Instalacion"  />
                            </f:facet>
                            <h:outputText value="#{data.areasInstalaciones.nombreAreaInstalacion}"  >
                            </h:outputText>
                        </h:column>
                        <rich:column style="background:#{data.colorFila}">
                            <f:facet name="header">
                                <h:outputText value="Fecha Sol."  >
                                </h:outputText>
                            </f:facet>
                            <h:outputText value="#{data.fechaSolicitud}">
                                <f:convertDateTime pattern="dd/MM/yyyy" locale="en"/>
                            </h:outputText>
                        </rich:column>
                        <rich:column style="background:#{data.colorFila}">
                            <f:facet name="header">
                                <h:outputText value="Area"  />
                            </f:facet>
                            <h:outputText value="#{data.areaDestinoSalida.nombreAreaEmpresa}"  />
                        </rich:column>


                        <rich:column style="background:#{data.colorFila}">
                            <f:facet name="header">
                                <h:outputText value="Tipo de Salida"  />
                            </f:facet>
                            <h:outputText value="#{data.tiposSalidasAlmacen.nombreTipoSalidaAlmacen}"  />
                            <h:outputText value="<br/>Destino Traspaso:<br/>#{data.almacenesDestino.nombreAlmacen}"  escape="false"
                                          style="font-size:10px"
                                          rendered="#{data.almacenesDestino.codAlmacen > 0}"/>
                        </rich:column>


                        <rich:column style="background:#{data.colorFila}">
                            <f:facet name="header">
                                <h:outputText value="Solicitante"  />
                            </f:facet>
                            <h:outputText value="#{data.solicitante.nombrePersonal}"  /> &nbsp;
                            <h:outputText value="#{data.solicitante.apPaternoPersonal}"  /> &nbsp;
                            <h:outputText value="#{data.solicitante.apMaternoPersonal}"  /> &nbsp;
                        </rich:column>

                        <rich:column style="background:#{data.colorFila}">
                            <f:facet name="header">
                                <h:outputText value="Lote Produccion"  />
                            </f:facet>
                            <h:outputText value="#{data.codLoteProduccion}"  />
                        </rich:column>

                        <rich:column style="background:#{data.colorFila}">
                            <f:facet name="header">
                                <h:outputText value="Estado Solicitud"  />
                            </f:facet>
                            <h:outputText value="#{data.estadosSolicitudSalidasAlmacen.nombreEstadoSolicitudSalidaAlmacen}"  />
                        </rich:column>

                        <rich:column style="background:#{data.colorFila}">
                            <f:facet name="header">
                                <h:outputText value="Obs Salida"  />
                            </f:facet>
                            <h:outputText value="#{data.obsSolicitud}"  />
                        </rich:column>
                        <rich:column style="background:#{data.colorFila}" rendered="#{ManagedSolicitudSalidaAlmacen.usuario.almacenesGlobal.codAlmacen != '14'&&(!data.solicitudPorLoteProveedor)&&(data.codLoteProduccion eq '')}">
                            <f:facet name="header">
                                <h:outputText value=""  />
                            </f:facet>
                            <a4j:commandLink action="#{ManagedRegistroSolicitudSalidaAlmacen.verReporteSolicitudSalidaAlmacen_action}" oncomplete = "openPopup('../reportes/solicitudSalidaAlmacen/reporteDetalleSolicitudSolicitante.jsf')">
                                <h:graphicImage url="../img/report.png" />
                            </a4j:commandLink>
                        </rich:column>
                        <rich:column style="background:#{data.colorFila}" rendered="#{ManagedSolicitudSalidaAlmacen.usuario.almacenesGlobal.codAlmacen != '14'&&(data.codLoteProduccion != '')}">
                            <f:facet name="header">
                                <h:outputText value=""  />
                            </f:facet>
                            <a4j:commandLink action="#{ManagedRegistroSolicitudSalidaAlmacen.verReporteSolicitudSalidaAlmacen_action}" oncomplete = "openPopup('../reportes/solicitudSalidaAlmacen/reporteDetalleSolicitudSolicitanteLoteProduccion.jsf')">
                                <h:graphicImage url="../img/report.png" />
                            </a4j:commandLink>
                        </rich:column>   
                        <rich:column style="background:#{data.colorFila}" rendered="#{ManagedSolicitudSalidaAlmacen.usuario.almacenesGlobal.codAlmacen != '14'&&data.solicitudPorLoteProveedor}">
                            <f:facet name="header">
                                <h:outputText value=""  />
                            </f:facet>
                            <a4j:commandLink action="#{ManagedRegistroSolicitudSalidaAlmacen.verReporteSolicitudSalidaAlmacen_action}" oncomplete = "openPopup('../reportes/solicitudSalidaAlmacen/reporteDetalleSolicitudLoteProveedor.jsf')">
                                <h:graphicImage url="../img/report.png" />
                            </a4j:commandLink>
                        </rich:column>
                        <rich:column style="background:#{data.colorFila}" rendered="#{ManagedSolicitudSalidaAlmacen.usuario.almacenesGlobal.codAlmacen eq '14'}">
                            <f:facet name="header">
                                <h:outputText value=""  />
                            </f:facet>
                            <a4j:commandLink action="#{ManagedRegistroSolicitudSalidaAlmacen.verReporteSolicitudSalidaAlmacen_action}" oncomplete = "openPopup('../reportes/solicitudSalidaAlmacen/reporteDetalleSolicitudSalidasAlmacenMantenimiento.jsf')">
                                <h:graphicImage url="../img/reportes.JPG" />
                            </a4j:commandLink>
                        </rich:column>
                    </rich:dataTable>

                    <h:panelGrid columns="2"  width="50" id="controles">
                        <h:commandLink  action="#{ManagedRegistroSolicitudSalidaAlmacen.atras_action}"   rendered="#{ManagedRegistroSolicitudSalidaAlmacen.begin!='1'}" >
                            <h:graphicImage url="../img/previous.gif"  style="border:0px solid red"   alt="PAGINA ANTERIOR"  />
                        </h:commandLink>
                        <h:commandLink  action="#{ManagedRegistroSolicitudSalidaAlmacen.siguiente_action}"  rendered="#{ManagedRegistroSolicitudSalidaAlmacen.cantidadfilas>='5'}">
                            <h:graphicImage url="../img/next.gif"  style="border:0px solid red"  alt="PAGINA SIGUIENTE" />
                        </h:commandLink>
                    </h:panelGrid>



                    <%-- <ws:datascroller fordatatable="dataAreasEmpresa"  />--%>
                    <br>
                    <a4j:commandButton value="Registro Solicitud Salida Almacen" styleClass="btn" onclick="Richfaces.showModalPanel('panelSeleccionTipoSolicitudSalidaAlmacen')"  />
                    <h:panelGroup rendered="#{ManagedRegistroSolicitudSalidaAlmacen.usuario.codAreaEmpresaGlobal!='90' || !ManagedRegistroSolicitudSalidaAlmacen.usuario.codAreaEmpresaGlobal=='92' || !ManagedRegistroSolicitudSalidaAlmacen.usuario.codAreaEmpresaGlobal=='1' || !ManagedRegistroSolicitudSalidaAlmacen.usuario.codAreaEmpresaGlobal=='86'}">
                        <a4j:commandButton value="Solicitud por Lote Proovedor" styleClass="btn" onclick="window.location.href='agregarSolicitudPorLoteProovedor.jsf?data='+(new Date()).getTime().toString();"  />
                        <a4j:commandButton value="Registro Solicitud Otros Materiales" styleClass="btn" onclick="location = 'agregarSolicitudComplementariaSalidaAlmacen.jsf'"  rendered="false"/>
                    </h:panelGroup>
                    <%--h:panelGroup rendered="#{ManagedRegistroSolicitudSalidaAlmacen.usuario.codAreaEmpresaGlobal=='90' || ManagedRegistroSolicitudSalidaAlmacen.usuario.codAreaEmpresaGlobal=='92' || ManagedRegistroSolicitudSalidaAlmacen.usuario.codAreaEmpresaGlobal=='1' || ManagedRegistroSolicitudSalidaAlmacen.usuario.codAreaEmpresaGlobal=='86' || ManagedRegistroSolicitudSalidaAlmacen.usuario.codAreaEmpresaGlobal=='93'|| ManagedRegistroSolicitudSalidaAlmacen.usuario.codAreaEmpresaGlobal=='40' }">
                        <a4j:commandButton value="Registro Solicitud Almacenes Manual" styleClass="btn" onclick="location = 'agregarSolicitudSalidaAlmacenesManual.jsf'"  /> <%-- oncomplete="location='agregarSalidaAlmacen.jsf'" action="#{ManagedSolicitudSalidaAlmacen.generarSalidaAlmacen_action}" onclick="Richfaces.showModalPanel('panelSeleccionTipoSolicitudSalidaAlmacen')" >
                    </h:panelGroup--%>

                    <a4j:commandButton value="Anular" action="#{ManagedRegistroSolicitudSalidaAlmacen.anularSolicitudSalidaAlmacen_action}" reRender="dataSalidasAlmacen" styleClass="btn" />
                    <%--a4j:commandButton value="Imprimir" styleClass = "btn" action="#{ManagedSalidaAlmacen.verReporteSalidaAlmacen_action}" oncomplete="openPopup('../reportes/salidasAlmacen/reporteDetalleSalidasAlmacen.jsp');" /--%>
                    <%--a4j:commandButton value="Editar" styleClass = "btn" action="#{ManagedSalidaAlmacen.editarSalidaAlmacen_action}" oncomplete="if(#{ManagedSalidaAlmacen.mensaje!=''}){alert('#{ManagedSalidaAlmacen.mensaje}');}else{location='editarSalidaAlmacen.jsf';}" /--%>



                    <%--
                    <a4j:commandButton value="Generar Salida Almacen" styleClass="btn"  action="#{ManagedIngresoAlmacenOrdenCompra.generarIngresoAlmacen_action}"
                    reRender="contenidoFrecuenciaMantenimentoMaquinaria" oncomplete="if(#{ManagedIngresoAlmacenOrdenCompra.mensaje!=''}){alert('#{ManagedIngresoAlmacenOrdenCompra.mensaje}')}" />
                    --%>

                </div>

                <h:outputText value="#{ManagedFrecuenciaMantenimientoMaquinaria.closeConnection}"  />

            </a4j:form>

            <rich:modalPanel id="panelSeleccionTipoSolicitudSalidaAlmacen"  minHeight="140"  minWidth="320"
                             height="240" width="320"
                             zindex="200"
                             headerClass="headerClassACliente"
                             resizeable="false" style="overflow :auto" >
                <f:facet name="header">
                    <h:outputText value="Seleccione el Tipo de Solicitud Salida Almacen"/>
                </f:facet>
                <a4j:form>
                    <h:panelGroup id="contenidoSeleccionTipoSolicitudSalidaAlmacen">
                        <div align="center">
                            <rich:dataTable value="#{ManagedRegistroSolicitudSalidaAlmacen.tiposSolicitudesSalidaAlmacenList}"
                                            var="data" headerClass="headerClassACliente">
                                <rich:column>
                                    <f:facet name="header">
                                        <h:outputText value="Tipo de Salida"/>
                                    </f:facet>
                                    <a4j:commandLink value="#{data.label}" action="#{ManagedRegistroSolicitudSalidaAlmacen.seleccionarTipoSolicitudSalidaAlmacen}"
                                                     oncomplete="window.location.href='#{ManagedRegistroSolicitudSalidaAlmacen.direccionPagina}'">
                                        <f:setPropertyActionListener value="#{data.value}" target="#{ManagedRegistroSolicitudSalidaAlmacen.codTipoSolicitudSalidaAlmacen}"/>
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <br/>
                            <input type="button" value="Cancelar" onclick="javascript:Richfaces.hideModalPanel('panelSeleccionTipoSolicitudSalidaAlmacen')" class="btn" />
                        </div>
                    </h:panelGroup>
                </a4j:form>
            </rich:modalPanel>


            <rich:modalPanel id="panelBuscarSalidaAlmacen1"  minHeight="220"  minWidth="1000"
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

            <rich:modalPanel id="panelBuscarSalidaAlmacen"  minHeight="280"  minWidth="1000"
                             height="280" width="1000"
                             zindex="200"
                             headerClass="headerClassACliente"
                             resizeable="false" style="overflow :auto" >
                <f:facet name="header">
                    <h:outputText value="Buscar Salida Almacen"/>
                </f:facet>
                <a4j:form>
                    <%--inicio alejandro--%>
                    <h:panelGroup id="contenidoBuscarSalidaAlmacen">
                        <h:panelGrid columns="4">


                            <h:outputText value="Nro Solicitud:" styleClass="outputText1" />
                            <h:inputText value="#{ManagedRegistroSolicitudSalidaAlmacen.solicitudesSalidaBuscar.codFormSalida}" styleClass="inputText" size="5" onkeypress="valNum(event);" />
                            <h:outputText value="Orden de trabajo:" styleClass="outputText1" />
                            <h:inputText value="#{ManagedRegistroSolicitudSalidaAlmacen.solicitudesSalidaBuscar.ordenTrabajo}" styleClass="inputText" size="5" onkeypress="valNum(event);" />

                            <h:outputText value="Gestion:" styleClass="outputText1" />
                            <h:selectOneMenu value="#{ManagedRegistroSolicitudSalidaAlmacen.solicitudesSalidaBuscar.gestiones.codGestion}" styleClass="inputText" >
                                <f:selectItems value="#{ManagedRegistroSolicitudSalidaAlmacen.gestionesList}"  />
                            </h:selectOneMenu>

                            <h:outputText value="Tipo de solicitud:" styleClass="outputText1" />
                            <h:selectOneMenu value="#{ManagedRegistroSolicitudSalidaAlmacen.solicitudesSalidaBuscar.tiposSalidasAlmacen.codTipoSalidaAlmacen}" styleClass="inputText" >
                                <f:selectItems value="#{ManagedRegistroSolicitudSalidaAlmacen.tiposSalidasList2}"  />
                            </h:selectOneMenu>
                            <h:outputText value="Estado de solicitud:" styleClass="outputText1" />
                            <h:selectOneMenu value="#{ManagedRegistroSolicitudSalidaAlmacen.solicitudesSalidaBuscar.estadosSolicitudSalidasAlmacen.codEstadoSolicitudSalidaAlmacen}" styleClass="inputText" >
                                <f:selectItems value="#{ManagedRegistroSolicitudSalidaAlmacen.estadosSolicitudList}"  />
                            </h:selectOneMenu>
                            <h:outputText value="Area destino solicitud:" styleClass="outputText1" />
                            <h:selectOneMenu value="#{ManagedRegistroSolicitudSalidaAlmacen.solicitudesSalidaBuscar.areaDestinoSalida.codAreaEmpresa}" styleClass="inputText" >
                                <f:selectItems value="#{ManagedRegistroSolicitudSalidaAlmacen.areasEmpresaList2}"  />
                            </h:selectOneMenu>
                            <h:outputText value="Fecha inicio solicitud" styleClass="outputText1" />
                            <rich:calendar  datePattern="dd/MM/yyyy" styleClass="inputText" zindex="200" value="#{ManagedRegistroSolicitudSalidaAlmacen.fechaInicio}" id="fechaInicio" enableManualInput="true" oninputblur="if(this.value!=''){valFecha(this)}else{return true;}"  />
                            <h:outputText value="Fecha final solicitud" styleClass="outputText1" />
                            <rich:calendar  datePattern="dd/MM/yyyy" styleClass="inputText" zindex="200" value="#{ManagedRegistroSolicitudSalidaAlmacen.fechaFinal}" id="fechaFinal" enableManualInput="true" oninputblur="if(this.value!=''){valFecha(this)}else{return true;}"  />
                            <h:outputText value="Lote:" styleClass="outputText1" />
                            <h:inputText value="#{ManagedRegistroSolicitudSalidaAlmacen.solicitudesSalidaBuscar.codLoteProduccion}" styleClass="inputText" size="5" onkeypress="valNum(event);" />
                            <h:outputText value="Almacen:" styleClass="outputText1" />
                            <h:selectOneMenu value="#{ManagedRegistroSolicitudSalidaAlmacen.solicitudesSalidaBuscar.almacenes.codAlmacen}" styleClass="inputText" >
                                <f:selectItems value="#{ManagedRegistroSolicitudSalidaAlmacen.almacenesList}"  />
                            </h:selectOneMenu>
                            <h:outputText value="Observacion:" styleClass="outputText1" style="width:200px;"/>
                            <h:inputTextarea  rows="3" cols="50" styleClass="outputText2" value="#{ManagedRegistroSolicitudSalidaAlmacen.solicitudesSalidaBuscar.obsSolicitud}" />

                        </h:panelGrid>
                        <div align="center">

                            <a4j:commandButton styleClass="btn" value="Buscar" onclick="javascript:Richfaces.hideModalPanel('panelBuscarSalidaAlmacen');"
                                               action="#{ManagedRegistroSolicitudSalidaAlmacen.buscarSalidaAlmacenAction}"
                                               reRender="dataSalidasAlmacen,controles"
                                               />
                            <input type="button" value="Cancelar" onclick="javascript:Richfaces.hideModalPanel('panelBuscarSalidaAlmacen')" class="btn" />
                        </div>
                    </h:panelGroup>
                    <%--final alejandro--%>
                </a4j:form>
            </rich:modalPanel>


        </body>
    </html>

</f:view>

