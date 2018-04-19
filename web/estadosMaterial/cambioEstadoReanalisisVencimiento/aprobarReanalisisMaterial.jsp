<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib prefix="a4j" uri="http://richfaces.org/a4j"%>
<%@taglib prefix="rich" uri="http://richfaces.org/rich"%>
<rich:modalPanel id="panelAprobarReanalisisMaterial" minHeight="250"  minWidth="450"
                 height="250" width="450" zindex="50" style="overflow-y:auto"
                headerClass="headerClassACliente" resizeable="false">
    <f:facet name="header">
        <h:outputText value="<center>Aprobar Reanalisis de Material</center>" escape="false" />
    </f:facet>
    <a4j:form id="formAprobarReanalisisMaterial">
        <center>
            <h:panelGroup id="contenidoAprobarReanalisisMaterial">
                <h:panelGrid columns="3"  style="width:100%;background-color:white;border:1px solid black" headerClass="headerClassACliente">
                    <h:outputText value="Material" styleClass="outputTextBold"/>
                    <h:outputText value=":" styleClass="outputTextBold"/>
                    <h:outputText value="#{ManagedEstadosMaterial.ingresosAlmacenDetalleEstadoAprobarReanalisis.materiales.nombreMaterial}" 
                                  styleClass="outputText2"/>
                    <h:outputText value="Lote Proveedor" styleClass="outputTextBold"/>
                    <h:outputText value=":" styleClass="outputTextBold"/>
                    <h:outputText value="#{ManagedEstadosMaterial.ingresosAlmacenDetalleEstadoAprobarReanalisis.loteMaterialProveedor}" 
                                  styleClass="outputText2"/>
                    <h:outputText value="Existencia" styleClass="outputTextBold"/>
                    <h:outputText value=":" styleClass="outputTextBold"/>
                    <h:panelGroup>
                        <h:outputText value="#{ManagedEstadosMaterial.ingresosAlmacenDetalleEstadoAprobarReanalisis.cantidadRestante}" 
                                      styleClass="outputText2"/>
                        <h:outputText value=" (#{ManagedEstadosMaterial.ingresosAlmacenDetalleEstadoAprobarReanalisis.materiales.unidadesMedida.nombreUnidadMedida})" 
                                      styleClass="outputText2"/>
                    </h:panelGroup>
                    <h:outputText value="Fecha Vencimiento Actual" styleClass="outputTextBold"/>
                    <h:outputText value=":" styleClass="outputTextBold"/>
                    <h:outputText value="#{ManagedEstadosMaterial.ingresosAlmacenDetalleEstadoAprobarReanalisis.fechaVencimiento}" styleClass="outputText2">
                        <f:convertDateTime pattern="MM/yyyy" locale="en"/>
                    </h:outputText>
                    <h:outputText value="Nueva Fecha de Vencimiento" styleClass="outputTextBold"/>
                    <h:outputText value=":" styleClass="outputTextBold"/>
                    <h:outputText value="#{ManagedEstadosMaterial.ingresosAlmacenDetalleEstadoAprobarReanalisis.fechaVencimiento1}" styleClass="outputText2">
                        <f:convertDateTime pattern="MM/yyyy" locale="en"/>
                    </h:outputText>
                </h:panelGrid>
                <rich:dataTable value="#{ManagedEstadosMaterial.almacenesExistenciaList}"
                                var="almacenExistencia" headerClass="headerClassACliente">
                    <f:facet name="header">
                        <rich:columnGroup>
                            <rich:column>
                                <h:outputText value="Almacen"/>
                            </rich:column>
                            <rich:column>
                                <h:outputText value="Existencia"/>
                            </rich:column>
                        </rich:columnGroup>
                    </f:facet>
                        <rich:column>
                            <h:outputText value="#{almacenExistencia.nombreAlmacen}"/>
                        </rich:column>
                        <rich:column>
                            <h:outputText value="#{almacenExistencia.cantidadExistente}">
                                <f:convertNumber pattern="#,##0.00" locale="en"/>
                            </h:outputText>
                        </rich:column>
                    
                    
                </rich:dataTable>
                <br/>
                <a4j:commandButton value="Aprobar"  styleClass="btn" action="#{ManagedEstadosMaterial.guardarAprobarReanalisisMaterial_action}"
                                   reRender="dataIngresoVencimiento"
                                   oncomplete="if('#{ManagedEstadosMaterial.mensaje eq '1'}'){alert('Se registro la aprobación');Richfaces.hideModalPanel('panelAprobarReanalisisMaterial')}
                                   else{alert('#{ManagedEstadosMaterial.mensaje}')}"/>
                <a4j:commandButton value="Cancelar"  styleClass="btn" oncomplete="Richfaces.hideModalPanel('panelAprobarReanalisisMaterial')"/>
            </h:panelGroup>
        </center>
    </a4j:form>
</rich:modalPanel>