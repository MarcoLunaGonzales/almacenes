<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib prefix="a4j" uri="http://richfaces.org/a4j"%>
<%@taglib prefix="rich" uri="http://richfaces.org/rich"%>
<f:view>
    
    <html>
        <head>
            <title>SISTEMA</title>
            <link rel="STYLESHEET" type="text/css" href="../css/ventas.css" />
            <script type="text/javascript" src="../js/general.js" ></script> 
        </head>
        <body >
            <a4j:form id="form1">
                <div align="center">
                    <h:outputText value="#{ManagedCapitulos.cargarContenidoCapitulos}"/>
                    <h:outputText styleClass="outputTextTitulo"  value="Listado de Capitulos" />
                    <br><br>

                    <rich:dataTable value="#{ManagedCapitulos.capitulosList}" var="capitulo"
                                    id="dataCapitulos"
                                    headerClass="headerClassACliente" >
                        <f:facet name="header">
                            <rich:columnGroup>
                                <rich:column>
                                    <h:outputText value="Nombre Capítulo"/>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Observación"/>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Estado"/>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Cantidad grupos relacionados"/>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Cantidad grupos relacionados activos"/>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Acciones"/>
                                </rich:column>
                            </rich:columnGroup>
                        </f:facet>
                        <rich:column>
                            <h:outputText value="#{capitulo.nombreCapitulo}"/>
                        </rich:column>
                        <rich:column>
                            <h:outputText value="#{capitulo.obsCapitulo}"  />
                        </rich:column>
                        <rich:column>
                            <h:outputText value="#{capitulo.estadoReferencial.nombreEstadoRegistro}"  />
                        </rich:column>
                        <rich:column>
                            <h:outputText value="#{capitulo.cantidadDatosRelacionados}"/>
                        </rich:column>
                        <rich:column>
                            <h:outputText value="#{capitulo.cantidadDatosRelacionadosActivos}"/>
                        </rich:column>
                        <rich:column>
                            <rich:dropDownMenu>
                                <f:facet name="label">
                                    <h:outputText value="Acciones"/>
                                </f:facet>
                                <rich:menuItem submitMode="ajax" value="Editar" >
                                    <a4j:support event="onclick" 
                                                 reRender="contenidoEditarCapitulos"
                                                 oncomplete="Richfaces.showModalPanel('panelEditarCapitulos')">
                                        <f:setPropertyActionListener value="#{capitulo}"
                                                                     target="#{ManagedCapitulos.capitulos}"/>
                                    </a4j:support>  
                                </rich:menuItem>
                                <rich:menuItem submitMode="ajax" value="Eliminar"
                                               rendered="#{capitulo.cantidadDatosRelacionados eq 0}" >
                                    <a4j:support event="onclick" 
                                                 action="#{ManagedCapitulos.eliminarCapituloAction(capitulo.codCapitulo)}"
                                                 oncomplete="mostrarMensajeTransaccion()"
                                                 reRender="dataCapitulos"/>
                                </rich:menuItem >
                                <rich:menuItem rendered="#{capitulo.cantidadDatosRelacionados > 0}">
                                    <h:outputText value="No se puede eliminar" style="color:red"/>
                                </rich:menuItem>
                                <rich:menuSeparator/>
                                <rich:menuItem submitMode="none" onclick="mostrarVentanaEmergente('reporteCapitulo.jsf?codCapitulo=#{capitulo.codCapitulo}')"> 
                                    <h:outputText value="Ver detalles log"/>                                
                                </rich:menuItem>
                            </rich:dropDownMenu>  
                        </rich:column>
                    </rich:dataTable>
                    <br>                    
                    <a4j:commandButton value="Agregar" styleClass="btn" 
                                        oncomplete="Richfaces.showModalPanel('panelAgregarCapitulos')"
                                        action="#{ManagedCapitulos.cargarAgregarCapituloAction()}"
                                        reRender="contenidolAgregarCapitulos" />
                </div>
            </a4j:form>
            
             <rich:modalPanel id="panelAgregarCapitulos" minHeight="180"  minWidth="400"
                                     height="180" width="400"
                                     zindex="200"
                                     headerClass="headerClassACliente"
                                     resizeable="false" style="overflow :auto" >
                    <f:facet name="header">
                        <h:outputText value="<center>Agregar Nuevo Capítulo</center>" escape="false"/>
                    </f:facet>
                    <a4j:form>
                        <h:panelGroup id="contenidolAgregarCapitulos">
                            <h:panelGrid columns="3" style="width:100%" id="panelAgregarCapitulo">
                                <h:outputText value="Nombre" styleClass="outputTextBold" />
                                <h:outputText value="::" styleClass="outputTextBold" />
                                <h:panelGroup>
                                    <h:inputText value="#{ManagedCapitulos.capitulos.nombreCapitulo}" styleClass="inputText"
                                                 style="width:100%"id="nombreCapitulo"
                                                 validatorMessage="El nombre debe tener al menos 4 caracteres"
                                                 required="true" requiredMessage="Debe registrar el nombre del capitulo">
                                        <f:validateLength minimum="4"/>
                                    </h:inputText>
                                    <h:message for="nombreCapitulo" styleClass="message"/>
                                </h:panelGroup>
                                <h:outputText value="Observacion" styleClass="outputTextBold" />
                                <h:outputText value="::" styleClass="outputTextBold" />
                                <h:inputTextarea value="#{ManagedCapitulos.capitulos.obsCapitulo}" styleClass="inputText" rows="2" 
                                                 style="width:100%"/>
                            </h:panelGrid>
                        <div align="center">
                            <a4j:commandButton styleClass="btn" value="Guardar"
                                               oncomplete="if('#{facesContext.maximumSeverity}'.length == 0){
                                                            mostrarMensajeTransaccionEvento(function(){Richfaces.hideModalPanel('panelAgregarCapitulos')})
                                                            }"
                                               action="#{ManagedCapitulos.guardarCapituloAction()}"
                                               reRender="dataCapitulos,panelAgregarCapitulo"/>                                
                            <input type="button" value="Cancelar" onclick="Richfaces.hideModalPanel('panelAgregarCapitulos')" class="btn" />
                        </div>
                    </h:panelGroup>
                </a4j:form>
            </rich:modalPanel>

            <rich:modalPanel id="panelEditarCapitulos" minHeight="200"  minWidth="500"
                                    height="200" width="500"
                                    zindex="200"
                                    headerClass="headerClassACliente"
                                    resizeable="false" style="overflow :auto" >
                <f:facet name="header">
                    <h:outputText value="<center>Editar Capitulo</center>" escape="false"/>
                </f:facet>
                <a4j:form>
                    <h:panelGroup id="contenidoEditarCapitulos" style="width:100%">
                        <h:panelGrid columns="3" style="width:100%">
                            <h:outputText value="Capitulo" styleClass="outputTextBold" />
                            <h:outputText value="::" styleClass="outputTextBold" />
                            <h:panelGroup rendered="#{ManagedCapitulos.capitulos.cantidadDatosRelacionados  eq 0}">
                                <h:inputText value="#{ManagedCapitulos.capitulos.nombreCapitulo}" 
                                             id="nombreCapitulo" required="true"
                                             requiredMessage="Debe registrar un nombre"
                                             validatorMessage="El nombre debe tener al menos 4 caracteres"
                                             styleClass="inputText" style="width:100%">
                                    <f:validateLength minimum="4"/>
                                </h:inputText>
                                <h:message for="nombreCapitulo" styleClass="message"/>
                            </h:panelGroup>
                            <h:outputText value="#{ManagedCapitulos.capitulos.nombreCapitulo}"
                                          rendered="#{ManagedCapitulos.capitulos.cantidadDatosRelacionados gt 0}"
                                          styleClass="outputText2"/>
                            <h:outputText value="Observacion" styleClass="outputTextBold" />
                            <h:outputText value="::" styleClass="outputTextBold" />
                            <h:inputTextarea value="#{ManagedCapitulos.capitulos.obsCapitulo}" 
                                             styleClass="inputText" rows="2" style="width:100%"  />
                            <h:outputText value="Estado" styleClass="outputTextBold" />
                            <h:outputText value="::" styleClass="outputTextBold" />
                            <h:selectOneMenu value="#{ManagedCapitulos.capitulos.estadoReferencial.codEstadoRegistro}"
                                             rendered="#{ManagedCapitulos.capitulos.cantidadDatosRelacionadosActivos eq 0}"
                                             styleClass="inputText" >
                                <f:selectItems value="#{ManagedCapitulos.estadosReferencialesList}"  />
                            </h:selectOneMenu>
                            <h:outputText value="#{ManagedCapitulos.capitulos.estadoReferencial.nombreEstadoRegistro}"
                                          rendered="#{ManagedCapitulos.capitulos.cantidadDatosRelacionadosActivos gt 0}"
                                          styleClass="outputText2"/>
                            </h:panelGrid>
                            <center>    
                                <a4j:commandButton styleClass="btn" value="Guardar"
                                                    action="#{ManagedCapitulos.modificarCapituloAction()}"
                                                    oncomplete="if('#{facesContext.maximumSeverity}'.length == 0){
                                                                mostrarMensajeTransaccionEvento(function(){Richfaces.hideModalPanel('panelEditarCapitulos')})
                                                                }"
                                                    reRender="dataCapitulos,contenidoEditarCapitulos"/>
                                <input type="button" value="Cancelar" onclick="Richfaces.hideModalPanel('panelEditarCapitulos')" class="btn" />
                            </center>
                        </h:panelGroup>
                        </a4j:form>
            </rich:modalPanel>
            <a4j:include viewId="/panelProgreso.jsp"/>
            <a4j:include viewId="/message.jsp"/>
        </body>
    </html>
    
</f:view>

