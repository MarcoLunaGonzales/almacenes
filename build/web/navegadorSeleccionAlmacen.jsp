<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib prefix="a4j" uri="http://richfaces.org/a4j"%>
<%@taglib prefix="rich" uri="http://richfaces.org/rich"%>
<f:view>

    <html>
        <head>
            <title>SISTEMA</title>
            <link rel="STYLESHEET" type="text/css" href="css/ventas.css" />
            <script type="text/javascript" src="js/general.js" ></script>
        </head>
        <body >
            <a4j:form id="form1">
                <div align="center">
                    <h:outputText value="#{ManagedAccesoSistema.cargarContenidoAlmacenes}"/>
                    <h:outputText styleClass="outputTextTitulo"  value="Seleccion de Almacen" />
                    <br><br>
                    
                    <rich:dataTable value="#{ManagedAccesoSistema.almacenesList}" var="data" 
                                    id="dataOrdenesCompra"
                                    onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                    onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                    headerClass="headerClassACliente">
                        <f:facet name="header">
                            <rich:columnGroup>
                                <rich:column>
                                    <h:outputText value=""/>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Nombre Almacen"/>
                                </rich:column>
                            </rich:columnGroup>
                        </f:facet>
                        <rich:column>
                            <a4j:commandLink action="#{ManagedAccesoSistema.seleccionarAlmacen_action}">
                                <f:setPropertyActionListener value="#{data}" target="#{ManagedAccesoSistema.almacenesGlobal}"/>
                                <h:graphicImage url="img/bien.png">
                                </h:graphicImage>
                            </a4j:commandLink>
                        </rich:column>
                        <rich:column>
                            <a4j:commandLink styleClass="outputText1" 
                                           action="#{ManagedAccesoSistema.seleccionarAlmacen_action}"
                                           value="#{data.nombreAlmacen}" >
                                <f:setPropertyActionListener value="#{data}" target="#{ManagedAccesoSistema.almacenesGlobal}"/>
                            </a4j:commandLink>
                        </rich:column>
                        
                    </rich:dataTable>
                </div>
            </a4j:form>
            <a4j:include viewId="panelProgreso.jsp"/>
             

        </body>
    </html>

</f:view>

