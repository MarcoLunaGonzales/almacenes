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
          
        </head>
        <body >
            <a4j:form id="form1">
                <div align="center">
                    <h:outputText value="#{ManagedMargenErrorAreasEmpresa.cargarToleranciaAreasEmpresa}"/>
                    <h:outputText styleClass="outputTextTitulo"  value="Margen de Tolerancia Por Area" />
                    <br><br>
                    
                    <rich:dataTable value="#{ManagedMargenErrorAreasEmpresa.toleranciaAreasEmpresaList}"
                                    var="data"
                                    id="dataMargenErrorAreasEmpresaList"
                                    onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                    onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                    headerClass="headerClassACliente" >
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value=""  />
                            </f:facet>
                            <h:selectBooleanCheckbox  value="#{data.checked}"  />
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Area Empresa"  />
                            </f:facet>
                            <h:outputText  value="#{data.areasEmpresa.nombreAreaEmpresa}"  />
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Tolerancia"  />
                            </f:facet>
                            <h:outputText value="#{data.tolerancia} %">
                            </h:outputText>
                        </h:column>
                      
                      
                    </rich:dataTable>
                   
                    <br>
                        <a4j:commandButton value="Registrar" styleClass="btn"  onclick="Richfaces.showModalPanel('PanelRegistrarMargenErrorAreaEmpresa')" reRender="contenidoRegistrarMargenErrorAreaEmpresa" />
                        <a4j:commandButton value="Editar" styleClass="btn" onclick="if(editarItem('form1:dataMargenErrorAreasEmpresaList')==false){return false;}" action="#{ManagedMargenErrorAreasEmpresa.editarMargenError_Action}" oncomplete="Richfaces.showModalPanel('PanelEditarMargenErrorAreaEmpresa')" reRender="contenidoEditarMargenErrorAreaEmpresa"/>
                        <a4j:commandButton value="Eliminar" styleClass="btn" onclick="if(confirm('Esta seguro de eliminar la tolerancia para el area empresa?')){if(editarItem('form1:dataMargenErrorAreasEmpresaList')==false){return false;}}else{return false;}"  action="#{ManagedMargenErrorAreasEmpresa.eliminarMargenErrorAreasEmpresa_Action}" oncomplete="alert('Se elimino la tolerancia para el area Empresa')" reRender="dataMargenErrorAreasEmpresaList"/>

                   
                </div>

               
              
            </a4j:form>

             <rich:modalPanel id="PanelRegistrarMargenErrorAreaEmpresa" minHeight="150"  minWidth="500"
                                     height="150" width="480"
                                     zindex="200"
                                     headerClass="headerClassACliente"
                                     resizeable="false" style="overflow :auto" >
                        <f:facet name="header">
                            <h:outputText value="Registro de Margen de Tolerancia"/>
                        </f:facet>
                        <a4j:form>
                        <h:panelGroup id="contenidoRegistrarMargenErrorAreaEmpresa">
                            <center>
                            <div align="left">
                               
                            <h:panelGrid columns="2">
                                 <h:outputText value="Area Empresa:" styleClass="outputText1" />
                                 <h:selectOneMenu value="#{ManagedMargenErrorAreasEmpresa.toleranciaAreasEmpresaNuevo.areasEmpresa.codAreaEmpresa}" styleClass="inputText" id="unidad1">
                                     <f:selectItems value="#{ManagedMargenErrorAreasEmpresa.areasEmpresaList}"/>
                                </h:selectOneMenu>                               
                                
                                <h:outputText value="Porciento Tolerancia:" styleClass="outputText1" />
                                <h:inputText value="#{ManagedMargenErrorAreasEmpresa.toleranciaAreasEmpresaNuevo.tolerancia}" styleClass="inputText" size="7" onkeypress="valNum(event);" />
                                
                              </h:panelGrid>
                            </div>
                            </center>
                                <div align="center">
                                    <a4j:commandButton styleClass="btn" value="Registrar"  action="#{ManagedMargenErrorAreasEmpresa.guardarMargenError_Action}"
                                oncomplete="alert('Se registro el margen de error para el area');javascript:Richfaces.hideModalPanel('PanelRegistrarMargenErrorAreaEmpresa');"
                                reRender="dataMargenErrorAreasEmpresaList"/>
                                <input type="button" value="Cancelar" onclick="javascript:Richfaces.hideModalPanel('PanelRegistrarMargenErrorAreaEmpresa')" class="btn" />
                                </div>
                        </h:panelGroup>
                        </a4j:form>
            </rich:modalPanel>

           <rich:modalPanel id="PanelEditarMargenErrorAreaEmpresa" minHeight="150"  minWidth="500"
                                     height="150" width="500"
                                     zindex="200"
                                     headerClass="headerClassACliente"
                                     resizeable="false" style="overflow :auto" >
                        <f:facet name="header">
                            <h:outputText value="Edición de Margen de Tolerancia"/>
                        </f:facet>
                        <a4j:form>
                        <h:panelGroup id="contenidoEditarMargenErrorAreaEmpresa">
                            <h:panelGrid columns="2">
                             
                             <h:outputText value="Area Empresa:" styleClass="outputText1" />
                             <h:outputText value="#{ManagedMargenErrorAreasEmpresa.toleranciaAreasEmpresaEditar.areasEmpresa.nombreAreaEmpresa}" styleClass="outputText1" />
                             <h:outputText value="Porciento Tolerancia:" styleClass="outputText1" />
                              
                              <h:inputText value="#{ManagedMargenErrorAreasEmpresa.toleranciaAreasEmpresaEditar.tolerancia}" styleClass="inputText" size="6" onkeypress="valNum(event);" />
                             

                             
                            </h:panelGrid>
                                <div align="center">
                                <a4j:commandButton styleClass="btn" value="Guardar" onclick="if(confirm('¿Esta seguro de editar el margen de Error?')==false){return false;}"
                                action="#{ManagedMargenErrorAreasEmpresa.guardarEditarMargenError_Action}" oncomplete="alert('Se edito la tolerancia');javascript:Richfaces.hideModalPanel('PanelEditarMargenErrorAreaEmpresa');" reRender="dataMargenErrorAreasEmpresaList" />
                                <input type="button" value="Cancelar" onclick="javascript:Richfaces.hideModalPanel('PanelEditarMargenErrorAreaEmpresa')" class="btn" />
                                </div>
                        </h:panelGroup>
                        </a4j:form>
            </rich:modalPanel>

        </body>
    </html>

</f:view>

