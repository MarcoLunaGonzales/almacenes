<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib prefix="a4j" uri="http://richfaces.org/a4j"%>
<%@taglib prefix="rich" uri="http://richfaces.org/rich"%>
    <rich:modalPanel id="panelAgregarItem" minHeight="400"  minWidth="800"
                        height="400" width="800"
                        zindex="100"
                        headerClass="headerClassACliente"
                        resizeable="false" style="overflow :auto" >
        <f:facet name="header">
            <h:outputText value="Detalle Item"/>
            <f:facet name="controls">
            <a4j:commandLink onclick="javascript:Richfaces.hideModalPanel('panelAgregarItem')" style="color: black" value="cerrar"></a4j:commandLink>
            </f:facet>
        </f:facet>
        <a4j:form id="form2">
            <center>
                <h:panelGroup id="contenidoAgregarItem">

                    <h:panelGrid columns="4" id="contenidoIngresoAlmacenDetalle" headerClass="headerClassACliente" width="90%" style="border:1px solid #000000;" >
                        <f:facet name="header">
                              <h:outputText value="Buscar Item" />
                        </f:facet>

                        <h:outputText value="Capitulo:" styleClass="tituloCampo1" />
                        <h:selectOneMenu value="#{ManagedIngresoAlmacen.buscarMaterial.grupos.capitulos.codCapitulo}" styleClass="inputText" >
                            <f:selectItems value="#{ManagedIngresoAlmacen.capitulosList}"  />
                            <a4j:support event="onchange" action="#{ManagedIngresoAlmacen.capitulos_change}" reRender="codGrupo" />
                        </h:selectOneMenu>

                        <h:outputText value="Grupo:" styleClass="tituloCampo1" />
                        <h:selectOneMenu value="#{ManagedIngresoAlmacen.buscarMaterial.grupos.codGrupo}" styleClass="inputText" id="codGrupo" >
                            <f:selectItems value="#{ManagedIngresoAlmacen.gruposList}"  />
                        </h:selectOneMenu>

                        <h:outputText value="Item:" styleClass="tituloCampo1" />

                        <h:inputText value="#{ManagedIngresoAlmacen.buscarMaterial.nombreMaterial}" styleClass="inputText" size="50"  />

                        <a4j:commandButton value="Buscar" action="#{ManagedIngresoAlmacen.buscarMaterial_action}" styleClass="btn"  reRender="dataBuscarMaterial" timeout="7200" />
                    </h:panelGrid>                             
                    <br/>

                    <rich:dataTable value="#{ManagedIngresoAlmacen.materialesBuscarList}" var="data"
                            id="dataBuscarMaterial"
                            onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                            onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                            headerClass="headerClassACliente"
                            binding="#{ManagedIngresoAlmacen.materialesBuscarDataTable}" >


                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Material"  />
                            </f:facet>
                            <a4j:commandLink  action="#{ManagedIngresoAlmacen.seleccionarMaterial_action}" styleClass="outputText2" onclick="Richfaces.hideModalPanel('panelAgregarItem')" reRender="dataIngresoAlmacenDetalle" >
                                <h:outputText  value="#{data.nombreMaterial}" styleClass="outputText2" />
                            </a4j:commandLink>

                        </h:column>

                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Grupo"  />
                            </f:facet>
                            <h:outputText  value="#{data.grupos.nombreGrupo}"/>
                        </h:column>

                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Capitulo"  />
                            </f:facet>
                            <h:outputText value="#{data.grupos.capitulos.nombreCapitulo}"  />
                        </h:column>

                    </rich:dataTable>
                </h:panelGroup>
                <input type="button" value="Cancelar" class="btn" onclick="javascript:Richfaces.hideModalPanel('panelAgregarItem')" />
            </center>
        </a4j:form>
    </rich:modalPanel>
