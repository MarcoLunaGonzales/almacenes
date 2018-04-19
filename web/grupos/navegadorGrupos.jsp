
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
            <a4j:loadStyle src="../css/chosen.css"/>
        </head>
        <body >
            <a4j:form id="form1">
                <center>
                    <h3>Listado de Grupos</h3>
                    <h:outputText value="#{ManagedGrupos.cargarContenidoGrupos}"/>
                    <rich:dataTable value="#{ManagedGrupos.gruposList}" var="grupo"
                                    id="dataGrupos"
                                    headerClass="headerClassACliente" >
                        <f:facet name="header">
                            <rich:columnGroup>
                                <rich:column>
                                    <h:outputText value="Nombre Grupo"/>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Capítulo"/>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Estado"/>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Plan de Cuentas"/>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Cantidad<br/>materiales<br/>relacionados" escape="false"/>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Cantidad<br/> materiales<br/>relacionados activos"escape="false"/>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Acciones"/>
                                </rich:column>
                            </rich:columnGroup>
                        </f:facet>
                        <rich:column>
                            <h:outputText value="#{grupo.nombreGrupo}"/>
                        </rich:column>
                        <rich:column>
                            <h:outputText value="#{grupo.capitulos.nombreCapitulo}"  />
                        </rich:column>
                        <rich:column>
                            <h:outputText value="#{grupo.estadoReferencial.nombreEstadoRegistro}"  />
                        </rich:column>
                        <rich:column>
                            <h:outputText value="#{grupo.planDeCuentas.nombreCuenta}"  />
                        </rich:column>
                        <rich:column styleClass="tdRight">
                            <h:outputText value="#{grupo.cantidadDatosRelacionados}">
                                <f:convertNumber pattern="#,##0" locale="en"/>
                            </h:outputText>
                        </rich:column>
                        <rich:column styleClass="tdRight">
                            <h:outputText value="#{grupo.cantidadDatosRelacionadosActivos}">
                                <f:convertNumber pattern="#,##0" locale="en"/>
                            </h:outputText>
                        </rich:column>
                        <rich:column>
                            <rich:dropDownMenu>
                                <f:facet name="label">
                                    <h:outputText value="Acciones"/>
                                </f:facet>
                                <rich:menuItem submitMode="ajax" value="Editar" >
                                    <a4j:support event="onclick" 
                                                 reRender="contenidoEditarGrupos"
                                                 oncomplete="Richfaces.showModalPanel('panelEditarGrupos')">
                                        <f:setPropertyActionListener value="#{grupo}"
                                                                     target="#{ManagedGrupos.grupos}"/>
                                    </a4j:support>  
                                </rich:menuItem>
                                <rich:menuItem submitMode="ajax" value="Eliminar"
                                               rendered="#{grupo.cantidadDatosRelacionados eq 0}" >
                                    <a4j:support event="onclick" 
                                                 action="#{ManagedGrupos.eliminarGrupoAction(grupo.codGrupo)}"
                                                 oncomplete="mostrarMensajeTransaccion()"
                                                 reRender="dataGrupos"/>
                                </rich:menuItem>
                                <rich:menuItem rendered="#{grupo.cantidadDatosRelacionados gt 0}">
                                    <h:outputText value="No se puede eliminar" style="color:red"/>
                                </rich:menuItem>
                                <rich:menuSeparator/>
                                <rich:menuItem submitMode="none" onclick="mostrarVentanaEmergente('reporteGrupos.jsf?codGrupo=#{grupo.codGrupo}')"> 
                                    <h:outputText value="Ver detalles log"/>                                
                                </rich:menuItem>
                            </rich:dropDownMenu>  
                        </rich:column>
                    </rich:dataTable>
                                        
                    <a4j:commandButton value="Agregar" styleClass="btn" 
                                        onclick="Richfaces.showModalPanel('panelAgregarGrupos')"
                                        action="#{ManagedGrupos.cargarAgregarGrupoAction()}"
                                        reRender="contenidoAgregarGrupos" />
                    

                </center>
            </a4j:form>
            
             <rich:modalPanel id="panelAgregarGrupos" minHeight="250"  minWidth="600"
                                     height="250" width="600"
                                     zindex="200"
                                     headerClass="headerClassACliente"
                                     resizeable="false" style="overflow :auto" >
                        <f:facet name="header">
                            <h:outputText value="<center>Agregar Nuevo Grupo</center>" escape="false"/>
                        </f:facet>
                        <a4j:form>
                            <h:panelGroup id="contenidoAgregarGrupos">
                                <h:panelGrid columns="3" style="width:100%">
                                    <h:outputText value="Capitulo" styleClass="outputTextBold" />
                                    <h:outputText value="::" styleClass="outputTextBold" />
                                    <h:panelGroup>
                                        <h:selectOneMenu value="#{ManagedGrupos.grupos.capitulos.codCapitulo}" 
                                                         required="true" id="codCapitulo"
                                                         requiredMessage="Debe seleccionar una opción"
                                                         styleClass="chosen">
                                            <f:selectItem itemLabel="--SELECCIONE UNA OPCION--"/>
                                            <f:selectItems value="#{ManagedGrupos.capitulosSelectList}" />
                                        </h:selectOneMenu>
                                        <h:message for="codCapitulo" styleClass="message"/>
                                    </h:panelGroup>
                                    <h:outputText value="Grupo" styleClass="outputTextBold" />
                                    <h:outputText value="::" styleClass="outputTextBold" />
                                    <h:panelGroup>
                                        <h:inputText value="#{ManagedGrupos.grupos.nombreGrupo}" styleClass="inputText"
                                                     style="width:100%"id="nombreGrupo"
                                                     validatorMessage="El nombre debe tener al menos 4 caracteres"
                                                     required="true" requiredMessage="Debe registrar el nombre del grupo">
                                            <f:validateLength minimum="4"/>
                                        </h:inputText>
                                        <h:message for="nombreGrupo" styleClass="message"/>
                                    </h:panelGroup>
                                    <h:outputText value="Observaciones" styleClass="outputTextBold" />
                                    <h:outputText value="::" styleClass="outputTextBold" />
                                    <h:inputTextarea value="#{ManagedGrupos.grupos.obsGrupo}" styleClass="inputText" rows="2" style="width:100%"  />
                                </h:panelGrid>
                                <center>
                                    <a4j:commandButton styleClass="btn" value="Guardar"
                                               oncomplete="if('#{facesContext.maximumSeverity}'.length == 0){
                                                            mostrarMensajeTransaccionEvento(function(){Richfaces.hideModalPanel('panelAgregarGrupos')})
                                                            }"
                                               action="#{ManagedGrupos.guardarGrupoAction()}"
                                               reRender="dataGrupos,contenidoAgregarGrupos"/>   
                                    <input type="button" value="Cancelar" onclick="javascript:Richfaces.hideModalPanel('panelAgregarGrupos')" class="btn" />
                                </center>
                            </h:panelGroup>
                        </a4j:form>
            </rich:modalPanel>
            

            <rich:modalPanel id="panelEditarGrupos" minHeight="250"  minWidth="600"
                                     height="250" width="600"
                                     zindex="200"
                                     headerClass="headerClassACliente"
                                     resizeable="false" style="overflow :auto" >
                <f:facet name="header">
                    <h:outputText value="<center>Edición de grupo</center>" escape="false"/>
                </f:facet>
                <a4j:form>
                    <h:panelGroup id="contenidoEditarGrupos">
                        <h:panelGrid columns="3" style="width:100%">
                            <h:outputText value="Capítulo" styleClass="outputTextBold" />
                            <h:outputText value="::" styleClass="outputTextBold" />
                            <h:panelGroup rendered="#{ManagedGrupos.grupos.cantidadDatosRelacionados eq 0}">
                                <h:selectOneMenu value="#{ManagedGrupos.grupos.capitulos.codCapitulo}" 
                                                 required="true" id="codCapitulo"
                                                 requiredMessage="Debe seleccionar una opción"
                                                 styleClass="chosen">
                                    <f:selectItem itemLabel="--SELECCIONE UNA OPCION--"/>
                                    <f:selectItems value="#{ManagedGrupos.capitulosSelectList}" />
                                </h:selectOneMenu>
                                <h:message for="codCapitulo" styleClass="message"/>
                            </h:panelGroup>
                            <h:outputText value="#{ManagedGrupos.grupos.capitulos.nombreCapitulo}" 
                                          rendered="#{ManagedGrupos.grupos.cantidadDatosRelacionados gt 0}"
                                          styleClass="outputText2"/>

                            <h:outputText value="Nombre Grupo" styleClass="outputTextBold" />
                            <h:outputText value="::" styleClass="outputTextBold" />
                            <h:panelGroup rendered="#{ManagedGrupos.grupos.cantidadDatosRelacionados eq 0}">
                                <h:inputText value="#{ManagedGrupos.grupos.nombreGrupo}" styleClass="inputText"
                                             style="width:100%"id="nombreGrupo"
                                             validatorMessage="El nombre debe tener al menos 4 caracteres"
                                             required="true" requiredMessage="Debe registrar el nombre del grupo">
                                    <f:validateLength minimum="4"/>
                                </h:inputText>
                                <h:message for="nombreGrupo" styleClass="message"/>
                            </h:panelGroup>
                            <h:outputText value="#{ManagedGrupos.grupos.nombreGrupo}" 
                                          rendered="#{ManagedGrupos.grupos.cantidadDatosRelacionados gt 0}"
                                          styleClass="outputText2"/>

                            <h:outputText value="Observaciones" styleClass="outputTextBold" />
                            <h:outputText value="::" styleClass="outputTextBold" />
                            <h:inputTextarea value="#{ManagedGrupos.grupos.obsGrupo}"
                                             styleClass="inputText" rows="2" style="width:100%"/>
                            
                            <h:outputText value="Estado" styleClass="outputTextBold" />
                            <h:outputText value="::" styleClass="outputTextBold" />
                            <h:selectOneMenu value="#{ManagedGrupos.grupos.estadoReferencial.codEstadoRegistro}" styleClass="inputText"
                                             rendered="#{ManagedGrupos.grupos.cantidadDatosRelacionadosActivos eq 0}">
                                <f:selectItems value="#{ManagedGrupos.estadosReferencialesSelectList}" />
                            </h:selectOneMenu>
                            
                            <h:outputText value="#{ManagedGrupos.grupos.estadoReferencial.nombreEstadoRegistro}" 
                                          rendered="#{ManagedGrupos.grupos.cantidadDatosRelacionadosActivos gt 0}"
                                          styleClass="outputText2"/>
                            
                        </h:panelGrid>
                        <center>
                            <a4j:commandButton styleClass="btn" value="Guardar"
                                               oncomplete="if('#{facesContext.maximumSeverity}'.length == 0){
                                                            mostrarMensajeTransaccionEvento(function(){Richfaces.hideModalPanel('panelEditarGrupos')})
                                                            }"
                                               action="#{ManagedGrupos.modificarGrupoAction()}"
                                               reRender="dataGrupos,contenidoEditarGrupos"/>   
                            <input type="button" value="Cancelar" onclick="Richfaces.hideModalPanel('panelEditarGrupos')" class="btn" />
                        </center>
                    </h:panelGroup>
                </a4j:form>
            </rich:modalPanel>
        <a4j:include viewId="/panelProgreso.jsp"/>
        <a4j:include viewId="/message.jsp"/>
        <a4j:loadScript src="/js/chosen.js"/>
        </body>
    </html>
    
</f:view>

