
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib prefix="a4j" uri="http://richfaces.org/a4j"%>
<%@taglib prefix="rich" uri="http://richfaces.org/rich"%>
<%@taglib prefix="ws" uri="http://isawi.org/ws"%>
<f:view>

    <html>

        <head>

            <title>SISTEMA</title>
            <link rel="STYLESHEET" type="text/css" href="../../css/ventas.css" />
            <script type="text/javascript" src="../../js/general.js" ></script>
            <style type="text/css">
                .tdRight{
                    text-align: right;
                }
            </style>
        </head>
        <body >
            <a4j:form id="form1"> 
                <div align="center">
                    <h:outputText value="#{ManagedEstadosMaterial.cargarIngresosAlmacenDetalleEstadoVencimiento}"/>
                    <h:outputText styleClass="outputTextTitulo"  value="Materiales Reanalisis" />
                    <br>
                    <rich:panel headerClass="headerClassACliente" style="width:80%">
                        <f:facet name="header">
                            <h:outputText value="Buscador"/>
                        </f:facet>
                        <h:panelGrid columns="6" id="filtroBuscador">
                            <h:outputText value="Material" styleClass="outputTextBold"/>
                            <h:outputText value=":" styleClass="outputTextBold"/>
                            <h:inputText value="#{ManagedEstadosMaterial.ingresosAlmacenDetalleEstadoVencimientoBuscar.materiales.nombreMaterial}"
                                         styleClass="inputText"/>

                            <h:outputText value="Lote Proveedor" styleClass="outputTextBold"/>
                            <h:outputText value=":" styleClass="outputTextBold"/>
                            <h:inputText value="#{ManagedEstadosMaterial.ingresosAlmacenDetalleEstadoVencimientoBuscar.loteMaterialProveedor}"
                                         styleClass="inputText"/>

                            <h:outputText value="Estado Material" styleClass="outputTextBold"/>
                            <h:outputText value=":" styleClass="outputTextBold"/>
                            <h:selectOneMenu value="#{ManagedEstadosMaterial.ingresosAlmacenDetalleEstadoVencimientoBuscar.estadosMaterial.codEstadoMaterial}"
                                             styleClass="inputText">
                                <f:selectItem itemValue='-1' itemLabel="Proximo a vencer en un rango de fechas"/>
                                <f:selectItems value="#{ManagedEstadosMaterial.estadosMaterialList}"/>
                                <a4j:support event="onchange" reRender="filtroBuscador"/>
                            </h:selectOneMenu>
                            <h:outputText value="" styleClass="outputTextBold"/>
                            <h:outputText value="" styleClass="outputTextBold"/>
                            <h:outputText value="" styleClass="outputTextBold"/>
                            <h:outputText value="Fecha Inicio" styleClass="outputTextBold" rendered="#{ManagedEstadosMaterial.ingresosAlmacenDetalleEstadoVencimientoBuscar.estadosMaterial.codEstadoMaterial eq -1}"/>
                            <h:outputText value=":" styleClass="outputTextBold" rendered="#{ManagedEstadosMaterial.ingresosAlmacenDetalleEstadoVencimientoBuscar.estadosMaterial.codEstadoMaterial eq -1}"/>
                            <rich:calendar value="#{ManagedEstadosMaterial.ingresosAlmacenDetalleEstadoVencimientoBuscar.fechaVencimiento1}" rendered="#{ManagedEstadosMaterial.ingresosAlmacenDetalleEstadoVencimientoBuscar.estadosMaterial.codEstadoMaterial eq -1}"
                                           datePattern="dd/MM/yyyy" enableManualInput="false"/>
                            <h:outputText value="Fecha Final" styleClass="outputTextBold" rendered="#{ManagedEstadosMaterial.ingresosAlmacenDetalleEstadoVencimientoBuscar.estadosMaterial.codEstadoMaterial eq -1}"/>
                            <h:outputText value=":" styleClass="outputTextBold " rendered="#{ManagedEstadosMaterial.ingresosAlmacenDetalleEstadoVencimientoBuscar.estadosMaterial.codEstadoMaterial eq -1}"/>
                            <rich:calendar value="#{ManagedEstadosMaterial.ingresosAlmacenDetalleEstadoVencimientoBuscar.fechaVencimiento2}" rendered="#{ManagedEstadosMaterial.ingresosAlmacenDetalleEstadoVencimientoBuscar.estadosMaterial.codEstadoMaterial eq -1}"
                                           datePattern="dd/MM/yyyy" enableManualInput="false"/>
                        </h:panelGrid>
                            <a4j:commandButton value="Buscar" styleClass="btn" reRender="dataIngresoVencimiento"
                                           action="#{ManagedEstadosMaterial.buscarIngresosAlmacenDetalleEstadoVencimiento_action}"
                                           />
                    </rich:panel>
                        <br/>
                    <rich:dataTable value="#{ManagedEstadosMaterial.ingresosAlmacenDetalleEstadoVencimientoList}"
                                    var="ingreso"
                                    id="dataIngresoVencimiento"
                                    onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                    onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                    headerClass="headerClassACliente" >
                        <f:facet name="header">
                            <rich:columnGroup>
                                <rich:column>
                                    <h:outputText value="Material"/>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Lote Proveedor"/>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Fecha de Vencimiento"/>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Cantidad Existencia"/>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Unidad de Medida"/>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Estado"/>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Acciones"/>
                                </rich:column>
                            </rich:columnGroup>
                        </f:facet>
                        <rich:column>
                            <h:outputText value="#{ingreso.materiales.nombreMaterial}"/>
                        </rich:column>
                        <rich:column>
                            <h:outputText value="#{ingreso.loteMaterialProveedor}"/>
                        </rich:column>
                        <rich:column styleClass="tdRight">
                            <h:outputText value="#{ingreso.fechaVencimiento}">
                                <f:convertDateTime pattern="MM/yyyy" locale="en"/>
                            </h:outputText>
                        </rich:column>
                        <rich:column styleClass="tdRight">
                            <h:outputText value="#{ingreso.cantidadRestante}">
                                <f:convertNumber locale="en" pattern="#,##0.00"/>
                            </h:outputText>
                        </rich:column>
                        <rich:column>
                            <h:outputText value="#{ingreso.materiales.unidadesMedida.nombreUnidadMedida}"/>
                        </rich:column>
                        <rich:column>
                            <h:outputText value="#{ingreso.estadosMaterial.nombreEstadoMaterial}"/>
                        </rich:column>
                        <rich:column>
                            <rich:dropDownMenu >
                                <f:facet name="label">
                                    <h:panelGroup>
                                        <h:outputText value="Acciones"/>
                                    </h:panelGroup>
                                </f:facet>
                                            
                                <rich:menuItem  submitMode="none"  value="Aprobar" 
                                                rendered="#{(ingreso.estadosMaterial.codEstadoMaterial eq 5 
                                                                or ingreso.estadosMaterial.codEstadoMaterial eq 8)
                                                            and ManagedEstadosMaterial.permisoAprobacionReanalisis}">
                                    <a4j:support event="onclick" action="#{ManagedEstadosMaterial.aprobarReanalisisIngresosAlmacenDetalle_action}"
                                                 oncomplete="Richfaces.showModalPanel('panelAprobarReanalisisMaterial')"
                                                 reRender="contenidoAprobarReanalisisMaterial">
                                        <f:setPropertyActionListener target="#{ManagedEstadosMaterial.ingresosAlmacenDetalleEstadoAprobarReanalisis}" 
                                                                     value="#{ingreso}"/>
                                    </a4j:support>
                                </rich:menuItem>
                                
                            </rich:dropDownMenu>
                        </rich:column>
                        <f:facet name="footer">
                            <rich:columnGroup>
                                <rich:column colspan="7" style="text-align:center">
                                    <a4j:commandLink  action = "#{ManagedEstadosMaterial.atrasListadoVencimiento_action}" reRender = "dataIngresoVencimiento" 
                                                      timeout = "7200" rendered="#{ManagedEstadosMaterial.inicioVencimientoList > 0}">
                                        <h:graphicImage url="../../img/previous.gif"  style="border:0px solid red" />
                                        <h:outputText value="Anterior"/>
                                    </a4j:commandLink>
                                    <a4j:commandLink action = "#{ManagedEstadosMaterial.siguienteListadoVencimiento_action}" style = "margin-left:16px" reRender = "dataIngresoVencimiento" 
                                                     timeout = "7200" rendered="#{ManagedEstadosMaterial.ingresosAlmacenDetalleEstadoVencimientoListSize >= 20}">
                                        <h:outputText value="Siguiente"/>
                                        <h:graphicImage url="../../img/next.gif"  style="border:0px solid red" />
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:columnGroup>
                        </f:facet>
                    </rich:dataTable>
                </div>

            </a4j:form>
            <a4j:status id="statusPeticion" onstart="Richfaces.showModalPanel('ajaxLoadingModalBox',{width:100, top:100})"
                        onstop="Richfaces.hideModalPanel('ajaxLoadingModalBox');">
            </a4j:status>

            <rich:modalPanel id="ajaxLoadingModalBox" minHeight="100"
                             minWidth="200" height="80" width="400" zindex="100" onshow="window.focus();">
                <div align="center">
                    <h:graphicImage value="/img/load2.gif" />
                </div>
            </rich:modalPanel>
            <a4j:include viewId="aprobarReanalisisMaterial.jsp"/>
            
    </body>
</html>

</f:view>


