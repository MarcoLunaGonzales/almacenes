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
                    <h:outputText value="#{ManagedEquivalenciasUnidadMedida.cargarListaEquivalencias}"/>
                    <h:outputText styleClass="outputTextTitulo"  value="Equivalencias" />
                    <br><br>
                    
                    <rich:dataTable value="#{ManagedEquivalenciasUnidadMedida.listaEquivalencias}"
                                    var="data"
                                    id="dataEquivalencias"
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
                                <h:outputText value="Unidad de Medida 1"  />
                            </f:facet>
                            <h:outputText  value="#{data.unidadesMedida1.nombreUnidadMedida}"  />
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Unidad de Medida 2"  />
                            </f:facet>
                            <h:outputText value="#{data.unidadesMedida2.nombreUnidadMedida}">
                            </h:outputText>
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Equivalencia"  />
                            </f:facet>
                            <h:outputText value="#{data.valorEquivalencia}"  />
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Estado"  />
                            </f:facet>
                            <h:outputText value="#{data.estadoReferencial.nombreEstadoRegistro}"  />
                        </h:column>

                    </rich:dataTable>
                    


                     <h:panelGrid columns="2"  width="50" id="controles">
                         <h:commandLink  action="#{ManagedEquivalenciasUnidadMedida.atras_action}"   rendered="#{ManagedEquivalenciasUnidadMedida.begin!='1'}"  >
                                <h:graphicImage url="../img/previous.gif"  style="border:0px solid red"   alt="PAGINA ANTERIOR"  />
                            </h:commandLink>
                            <h:commandLink  action="#{ManagedEquivalenciasUnidadMedida.siguiente_action}"  rendered="#{ManagedEquivalenciasUnidadMedida.cantidadfilas>'9'}">
                                <h:graphicImage url="../img/next.gif"  style="border:0px solid red"  alt="PAGINA SIGUIENTE" />
                            </h:commandLink>
                    </h:panelGrid>
                    

                   
                    <br>
                        <a4j:commandButton value="Registrar" styleClass="btn" action="#{ManagedEquivalenciasUnidadMedida.nuevaEquivalencia_action}" oncomplete="Richfaces.showModalPanel('PanelRegistrarEquivalencia')" reRender="contenidoRegistrarEquivalencia" />
                        <a4j:commandButton value="Editar" styleClass="btn" onclick="if(editarItem('form1:dataEquivalencias')==false){return false;}" action="#{ManagedEquivalenciasUnidadMedida.editarEquivalencia_action}" oncomplete="if(#{ManagedEquivalenciasUnidadMedida.mensaje==''}){Richfaces.showModalPanel('PanelEditarEquivalencia')}else{alert('#{ManagedEquivalenciasUnidadMedida.mensaje}');}" reRender="contenidoEditarEquivalencia"/>
                        <a4j:commandButton value="Eliminar" styleClass="btn" onclick="if(confirm('Esta seguro de eliminar la equivalencia?')){if(editarItem('form1:dataEquivalencias')==false){return false;}}else{return false;}"  action="#{ManagedEquivalenciasUnidadMedida.eliminarEquivalencia_action}" oncomplete="if(#{ManagedEquivalenciasUnidadMedida.mensaje!=''}){alert('#{ManagedEquivalenciasUnidadMedida.mensaje}');}else{alert('Se elimino la equivalencia');}" reRender="dataEquivalencias,controles"/>

                   
                </div>

               
              
            </a4j:form>

             <rich:modalPanel id="PanelRegistrarEquivalencia" minHeight="150"  minWidth="500"
                                     height="150" width="500"
                                     zindex="200"
                                     headerClass="headerClassACliente"
                                     resizeable="false" style="overflow :auto" >
                        <f:facet name="header">
                            <h:outputText value="Registro de tipos de equivalencia"/>
                        </f:facet>
                        <a4j:form>
                        <h:panelGroup id="contenidoRegistrarEquivalencia">
                            <h:panelGrid columns="4">
                                
                                <h:outputText value="Unidad de Medidad 1:" styleClass="outputText1" />
                                <h:selectOneMenu value="#{ManagedEquivalenciasUnidadMedida.nuevaEquivalencia.unidadesMedida1.codUnidadMedida}" styleClass="inputText" id="unidad1">
                                    <f:selectItems value="#{ManagedEquivalenciasUnidadMedida.listaUnidades1}"/>
                                    <a4j:support action="#{ManagedEquivalenciasUnidadMedida.onchangeEquivalencia}" event="onchange" reRender="unidad2"/>
                                   
                                </h:selectOneMenu>
                                <h:outputText value="Unidad de Medidad 2:" styleClass="outputText1" />
                                <h:selectOneMenu value="#{ManagedEquivalenciasUnidadMedida.nuevaEquivalencia.unidadesMedida2.codUnidadMedida}" styleClass="inputText" id="unidad2">
                                    <f:selectItems value="#{ManagedEquivalenciasUnidadMedida.listaUnidades2}"/>

                                </h:selectOneMenu>
                              
                                <h:outputText value="Equivalencia :" styleClass="outputText1" />
                                <h:inputText value="#{ManagedEquivalenciasUnidadMedida.nuevaEquivalencia.valorEquivalencia}" styleClass="inputText" size="6" onkeypress="valNum(event);" />

                                 <h:outputText value="Estado:" styleClass="outputText1" />
                                 <h:inputText value="Activo" styleClass="inputText" disabled="true" />


                            </h:panelGrid>
                                <div align="center">
                                <a4j:commandButton styleClass="btn" value="Registrar"  action="#{ManagedEquivalenciasUnidadMedida.guardarEquivalencias_action}"
                                oncomplete="if(#{ManagedEquivalenciasUnidadMedida.mensaje!=''}){alert('#{ManagedEquivalenciasUnidadMedida.mensaje}');}else{alert('Se registro la equivalencia');javascript:Richfaces.hideModalPanel('PanelRegistrarEquivalencia');}"
                                reRender="dataEquivalencias,controles"/>
                                <input type="button" value="Cancelar" onclick="javascript:Richfaces.hideModalPanel('PanelRegistrarEquivalencia')" class="btn" />
                                </div>
                        </h:panelGroup>
                        </a4j:form>
            </rich:modalPanel>

           <rich:modalPanel id="PanelEditarEquivalencia" minHeight="150"  minWidth="500"
                                     height="150" width="500"
                                     zindex="200"
                                     headerClass="headerClassACliente"
                                     resizeable="false" style="overflow :auto" >
                        <f:facet name="header">
                            <h:outputText value="Edición de equivalencia"/>
                        </f:facet>
                        <a4j:form>
                        <h:panelGroup id="contenidoEditarEquivalencia">
                            <h:panelGrid columns="4">

                             <h:outputText value="Unidad de Medidad 1:" styleClass="outputText1" />
                             <h:selectOneMenu value="#{ManagedEquivalenciasUnidadMedida.editarEquivalencia.unidadesMedida1.codUnidadMedida}" styleClass="inputText" id="unidadeditar1" disabled="true">
                                    <f:selectItems value="#{ManagedEquivalenciasUnidadMedida.listaUnidades1}"/>

                                </h:selectOneMenu>
                                
                                <h:outputText value="Unidad de Medidad 2:" styleClass="outputText1" />
                                <h:selectOneMenu value="#{ManagedEquivalenciasUnidadMedida.editarEquivalencia.unidadesMedida2.codUnidadMedida}" styleClass="inputText" id="unidadeditar2" disabled="true">
                                    <f:selectItems value="#{ManagedEquivalenciasUnidadMedida.listaUnidades2}"/>

                                </h:selectOneMenu>
                           
                                <h:outputText value="Equivalencia :" styleClass="outputText1" />
                                <h:inputText value="#{ManagedEquivalenciasUnidadMedida.editarEquivalencia.valorEquivalencia}" styleClass="inputText" size="6" onkeypress="valNum(event);" />

                                 <h:outputText value="Estado:" styleClass="outputText1" />
                                 <h:selectOneMenu value="#{ManagedEquivalenciasUnidadMedida.editarEquivalencia.estadoReferencial.codEstadoRegistro}" styleClass="inputText" id="estado2">

                                    <f:selectItem itemLabel="activo" itemValue="1"/>
                                    <f:selectItem itemLabel="no activo" itemValue="2"/>
                                 </h:selectOneMenu>
                            </h:panelGrid>
                                <div align="center">
                                <a4j:commandButton styleClass="btn" value="Guardar" onclick="if(confirm('Esta seguro de editar la equivalencia?')==false){return false;}"
                                action="#{ManagedEquivalenciasUnidadMedida.editarEquivalencias_action}" oncomplete="javascript:Richfaces.hideModalPanel('PanelEditarEquivalencia');" reRender="dataEquivalencias,controles" />
                                <input type="button" value="Cancelar" onclick="javascript:Richfaces.hideModalPanel('PanelEditarEquivalencia')" class="btn" />
                                </div>
                        </h:panelGroup>
                        </a4j:form>
            </rich:modalPanel>

        </body>
    </html>

</f:view>

