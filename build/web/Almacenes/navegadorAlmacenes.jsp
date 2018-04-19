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


            </script>
          
        </head>
        <body >
          <a4j:form id="form1">
                <div align="center">
                    <h:outputText value="#{ManagedAlmacenes.initAlmacenes}"/>
                    <h:outputText styleClass="outputTextTitulo"  value="Almacenes" />
                    <br><br>
                        <h:selectOneMenu value="#{ManagedAlmacenes.codRegistro}">
                            <f:selectItem itemValue="1" itemLabel="Activo"/>
                            <f:selectItem itemValue="2" itemLabel="No Activo"/>
                            <f:selectItem itemValue="3" itemLabel="Todos"/>
                            <a4j:support event="onchange" action="#{ManagedAlmacenes.cargarAlmacenes}" reRender="dataAlmacenes"/>
                        </h:selectOneMenu>

                       <rich:dataTable value="#{ManagedAlmacenes.almacenesList}"
                                    var="data"
                                    id="dataAlmacenes"
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
                                <h:outputText value="Filial"  />
                            </f:facet>
                            <h:outputText value="#{data.filiales.nombreFilial}">
                            </h:outputText>
                        </rich:column>
                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value="Almacén"  />
                            </f:facet>
                            <h:outputText value="#{data.nombreAlmacen}"  />
                        </rich:column>
                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value="Descripción"  />
                            </f:facet>
                            <h:outputText value="#{data.obsAlmacen}"  />
                        </rich:column>
                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value="Estado de Registro"  />
                            </f:facet>
                            <h:outputText value="#{data.estadoReferencial.nombreEstadoRegistro}"  />
                        </rich:column>

                    </rich:dataTable>






                    <br>
                        <a4j:commandButton value="Registrar" styleClass="btn" action="#{ManagedAlmacenes.nuevoAlmacen}" oncomplete="Richfaces.showModalPanel('PanelRegistrarAlmacen')" reRender="contenidoRegistrarAlmacen" />
                        <a4j:commandButton value="Editar" styleClass="btn" onclick="if(editarItem('form1:dataAlmacenes')==false){return false;};" action="#{ManagedAlmacenes.editarAlmacenAction}" oncomplete="Richfaces.showModalPanel('PanelEditarAlmacen')" reRender="contenidoEditarAlmacen" />
                        <a4j:commandButton value="Eliminar" styleClass="btn" onclick="if(confirm('Esta seguro de eliminar el registro?')){if(editarItem('form1:dataAlmacenes')==false){return false;}}else{return false;}"  action="#{ManagedAlmacenes.eliminarAlmacenAction}" oncomplete="if(#{ManagedAlmacenes.mensaje!=''}){alert('#{ManagedAlmacenes.mensaje}');}else{alert('Se elimino el almacen');}" reRender="dataAlmacenes"/>

                </div>



            </a4j:form>
             <rich:modalPanel id="PanelRegistrarAlmacen" minHeight="180"  minWidth="560"
                                     height="180" width="560"
                                     zindex="180"
                                     headerClass="headerClassACliente"
                                     resizeable="false" style="overflow :auto" >
                        <f:facet name="header">
                            <h:outputText value="Registro de Almacen"/>
                        </f:facet>
                        <a4j:form>
                        <h:panelGroup id="contenidoRegistrarAlmacen">
                            <h:panelGrid columns="6">

                                <h:outputText value="Filial" styleClass="outputText1" />
                                 <h:outputText value="::" styleClass="outputText1" />
                                 <h:selectOneMenu value="#{ManagedAlmacenes.almacenNuevo.filiales.codFilial}" styleClass="inputText1">
                                    <f:selectItems value="#{ManagedAlmacenes.filialesList}"/>
                                 </h:selectOneMenu>

                                <h:outputText value="Almacen" styleClass="outputText1" />
                                <h:outputText value="::" styleClass="outputText1" />
                                <h:inputText value="#{ManagedAlmacenes.almacenNuevo.nombreAlmacen}" styleClass="inputText1"/>

                                <h:outputText value="Estado de Registro" styleClass="outputText1" />
                                <h:outputText value="::" styleClass="outputText1" />
                                <h:inputText value="Activo" styleClass="inputText1" disabled="true" />

                                 <h:outputText value="Observaciones:" styleClass="outputText1" />
                                 <h:outputText value="::" styleClass="outputText1" />
                                 <h:inputTextarea value="#{ManagedAlmacenes.almacenNuevo.obsAlmacen}" rows="3"/>
                                                              
                                
                            </h:panelGrid>
                         
                                
                        </h:panelGroup>
                        
                        
                        
                          <div align="center">
                              <a4j:commandButton styleClass="btn" value="Registrar"  action="#{ManagedAlmacenes.registrarAlmacenAction}"
                                    oncomplete="javascript:Richfaces.hideModalPanel('PanelRegistrarAlmacen')"
                                     reRender="dataAlmacenes"/>
                                <input type="button" value="Cancelar" onclick="javascript:Richfaces.hideModalPanel('PanelRegistrarAlmacen')" class="btn" />
                                </div>
                        </a4j:form>
                         
            </rich:modalPanel>

           <rich:modalPanel id="PanelEditarAlmacen" minHeight="180"  minWidth="560"
                                     height="180" width="560"
                                     zindex="180"
                                     headerClass="headerClassACliente"
                                     resizeable="false" style="overflow :auto" >
                        <f:facet name="header">
                            <h:outputText value="Edición de Almacen"/>
                        </f:facet>
                        <a4j:form id="formEditar">
                        <h:panelGroup id="contenidoEditarAlmacen">
                            <h:panelGrid columns="6">

                              <h:outputText value="Filial" styleClass="outputText1" />
                                 <h:outputText value="::" styleClass="outputText1" />
                                 <h:selectOneMenu value="#{ManagedAlmacenes.almacenEdicion.filiales.codFilial}" styleClass="inputText1" disabled="true">
                                    <f:selectItems value="#{ManagedAlmacenes.filialesList}"/>
                                 </h:selectOneMenu>
                                    <h:outputText value="Almacen" styleClass="outputText1" />
                                <h:outputText value="::" styleClass="outputText1" />
                                <h:inputText value="#{ManagedAlmacenes.almacenEdicion.nombreAlmacen}" styleClass="inputText1"/>

                                <h:outputText value="Estado de Registro" styleClass="outputText1" />
                                <h:outputText value="::" styleClass="outputText1" />
                                <h:selectOneMenu value="#{ManagedAlmacenes.almacenEdicion.estadoReferencial.codEstadoRegistro}"styleClass="inputText1">
                                    <f:selectItem itemValue="1" itemLabel="Activo"/>
                                    <f:selectItem itemValue="2" itemLabel="No Activo"/>
                                </h:selectOneMenu>

                                 <h:outputText value="Observaciones:" styleClass="outputText1" />
                                 <h:outputText value="::" styleClass="outputText1" />
                                 <h:inputTextarea value="#{ManagedAlmacenes.almacenEdicion.obsAlmacen}" rows="3"/>

                            </h:panelGrid>

                            </h:panelGroup>
                        
                                <div align="center">
                                <a4j:commandButton styleClass="btn" value="Guardar" onclick="if(confirm('Esta seguro de editar el Almacen?')==false){return false;}"
                                action="#{ManagedAlmacenes.guardarEdicionAccion}" oncomplete="if(#{ManagedAlmacenes.mensaje!=''}){alert('#{ManagedAlmacenes.mensaje}');}else{javascript:Richfaces.hideModalPanel('PanelEditarAlmacen');}" reRender="dataAlmacenes" />
                                <input type="button" value="Cancelar" onclick="javascript:Richfaces.hideModalPanel('PanelEditarAlmacen')" class="btn" />

                                </div>
                        </a4j:form>
            </rich:modalPanel>

             
                 
        </body>
    </html>

</f:view>

