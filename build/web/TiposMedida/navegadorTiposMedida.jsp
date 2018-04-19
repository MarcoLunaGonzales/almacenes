package TiposMedida;


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
                    <h:outputText value="#{ManagedTiposDeMedida.initListaTiposMedida}"/>
                    <h:outputText styleClass="outputTextTitulo"  value="Tipos de Medida"   />
                    <br><br>
                        <h:selectOneMenu value="#{ManagedTiposDeMedida.codEstadoRegistro}">
                            <f:selectItem itemValue="1" itemLabel="Activo"/>
                            <f:selectItem itemValue="2" itemLabel="No Activo"/>
                            <f:selectItem itemValue="3" itemLabel="Todos"/>
                            <a4j:support event="onchange" action="#{ManagedTiposDeMedida.onChange_EstadoRegistro}" reRender="dataTiposDeMedida"/>
                        </h:selectOneMenu>

                       <rich:dataTable value="#{ManagedTiposDeMedida.listaTiposMedida}"
                                    var="data"
                                    id="dataTiposDeMedida"
                                    onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                    onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                    headerClass="headerClassACliente" >
                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value=""  />
                            </f:facet>
                            <h:selectBooleanCheckbox  value="#{data.checked}"  />
                        </rich:column>
                   
                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value="Tipo de Medida"  />
                            </f:facet>
                            <h:outputText value="#{data.nombre_tipo_medida}">
                            </h:outputText>
                        </rich:column>
                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value="Descripción"  />
                            </f:facet>
                            <h:outputText value="#{data.obs_tipo_medida}"  />
                        </rich:column>
                      
                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value="Estado de Registro"  />
                            </f:facet>
                            <h:outputText value="#{data.estadoReferencial.nombreEstadoRegistro}"  />
                        </rich:column>

                    </rich:dataTable>






                    <br>
                        <a4j:commandButton value="Registrar" styleClass="btn" action="#{ManagedTiposDeMedida.nuevoTipoMedida_Action}" oncomplete="Richfaces.showModalPanel('PanelRegistrarTiposDeMedida')" reRender="contenidoRegistrarTiposDeMedida" />
                        <a4j:commandButton value="Editar" styleClass="btn" onclick="if(editarItem('form1:dataTiposDeMedida')==false){return false;};" action="#{ManagedTiposDeMedida.editarTipoMedida_Action}" oncomplete="Richfaces.showModalPanel('PanelEditarTiposDeMedida')" reRender="contenidoEditarTiposDeMedida" />
                        <a4j:commandButton value="Eliminar" styleClass="btn" onclick="if(confirm('Esta seguro de eliminar el registro?')){if(editarItem('form1:dataTiposDeMedida')==false){return false;}}else{return false;}"  action="#{ManagedTiposDeMedida.eliminarTipoMedida_Action}" oncomplete="if(#{ManagedTiposDeMedida.mensaje!=''}){alert('#{ManagedTiposDeMedida.mensaje}');}else{alert('Se elimino el tipo de medida');}" reRender="dataTiposDeMedida"/>

                </div>



            </a4j:form>
             <rich:modalPanel id="PanelRegistrarTiposDeMedida" minHeight="180"  minWidth="560"
                                     height="180" width="560"
                                     zindex="180"
                                     headerClass="headerClassACliente"
                                     resizeable="false" style="overflow :auto" >
                        <f:facet name="header">
                            <h:outputText value="Registro de Tipos de Medida"/>
                        </f:facet>
                        <a4j:form>
                        <h:panelGroup id="contenidoRegistrarTiposDeMedida">
                            <h:panelGrid columns="6">

                                <h:outputText value="Nombre" styleClass="outputText1" />
                                 <h:outputText value="::" styleClass="outputText1" />
                                 <h:inputText value="#{ManagedTiposDeMedida.nuevoTipoMedida.nombre_tipo_medida}" styleClass="inputText" style="width:200px"/>
                                
                                <h:outputText value="Estado de Registro" styleClass="outputText1" />
                                <h:outputText value="::" styleClass="outputText1" />
                                <h:inputText value="Activo" styleClass="inputText" disabled="true" />

                                 <h:outputText value="Observaciones:" styleClass="outputText1" />
                                 <h:outputText value="::" styleClass="outputText1" />
                                 <h:inputTextarea value="#{ManagedTiposDeMedida.nuevoTipoMedida.obs_tipo_medida}" rows="3" styleClass="inputText" style="width:200px"/>
                                                              
                                
                            </h:panelGrid>
                         
                                
                        </h:panelGroup>
                        
                        
                        
                          <div align="center">
                              <a4j:commandButton styleClass="btn" value="Registrar"  action="#{ManagedTiposDeMedida.guardarNuevoTipoMedida_Action}"
                                    oncomplete="javascript:Richfaces.hideModalPanel('PanelRegistrarTiposDeMedida')"
                                     reRender="dataTiposDeMedida"/>
                                <input type="button" value="Cancelar" onclick="javascript:Richfaces.hideModalPanel('PanelRegistrarTiposDeMedida')" class="btn" />
                                </div>
                        </a4j:form>
                         
            </rich:modalPanel>

           <rich:modalPanel id="PanelEditarTiposDeMedida" minHeight="180"  minWidth="560"
                                     height="180" width="560"
                                     zindex="180"
                                     headerClass="headerClassACliente"
                                     resizeable="false" style="overflow :auto" >
                        <f:facet name="header">
                            <h:outputText value="Modificar Tipo de Medida"/>
                        </f:facet>
                        <a4j:form id="formEditar">
                        <h:panelGroup id="contenidoEditarTiposDeMedida">
                            <h:panelGrid columns="6">
                                <h:outputText value="Nombre" styleClass="outputText1" />
                                <h:outputText value="::" styleClass="outputText1" />
                                <h:inputText value="#{ManagedTiposDeMedida.editarTipoMedida.nombre_tipo_medida}" styleClass="inputText" style="width:200px"/>

                                <h:outputText value="Estado de Registro" styleClass="outputText1" />
                                <h:outputText value="::" styleClass="outputText1" />
                                <h:selectOneMenu value="#{ManagedTiposDeMedida.editarTipoMedida.estadoReferencial.codEstadoRegistro}"styleClass="inputText">
                                    <f:selectItem itemValue="1" itemLabel="Activo"/>
                                    <f:selectItem itemValue="2" itemLabel="No Activo"/>
                                </h:selectOneMenu>

                                 <h:outputText value="Observaciones:" styleClass="outputText1" />
                                 <h:outputText value="::" styleClass="outputText1" />
                                 <h:inputTextarea value="#{ManagedTiposDeMedida.editarTipoMedida.obs_tipo_medida}" rows="3" styleClass="inputText" style="width:200px"/>

                            </h:panelGrid>

                            </h:panelGroup>
                        
                                <div align="center">
                                <a4j:commandButton styleClass="btn" value="Guardar" onclick="if(confirm('Esta seguro de editar el Tipo de Medida?')==false){return false;}"
                                action="#{ManagedTiposDeMedida.guardarEditarTipoMedida_Action}" oncomplete="if(#{ManagedTiposDeMedida.mensaje!=''}){alert('#{ManagedTiposDeMedida.mensaje}');}else{javascript:Richfaces.hideModalPanel('PanelEditarTiposDeMedida');}" reRender="dataTiposDeMedida" />
                                <input type="button" value="Cancelar" onclick="javascript:Richfaces.hideModalPanel('PanelEditarTiposDeMedida')" class="btn" />

                                </div>
                        </a4j:form>
            </rich:modalPanel>

             
                 
        </body>
    </html>

</f:view>

