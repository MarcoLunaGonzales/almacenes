
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
                    <h:outputText value="#{ManagedTiposSalidaAlmacen.initTiposSalida}"/>
                    <h:outputText styleClass="outputTextTitulo"  value="Tipos de Salida Almacen" />
                    <br><br>
                        <h:selectOneMenu value="#{ManagedTiposSalidaAlmacen.estadoRegistro}">
                            <f:selectItem itemValue="1" itemLabel="Activo"/>
                            <f:selectItem itemValue="2" itemLabel="No Activo"/>
                            <f:selectItem itemValue="3" itemLabel="Todos"/>
                            <a4j:support event="onchange" action="#{ManagedTiposSalidaAlmacen.onChange_estadoRegistro}" reRender="dataTiposSalidaAlmacen"/>
                        </h:selectOneMenu>

                       <rich:dataTable value="#{ManagedTiposSalidaAlmacen.listaTiposSalidaAlmacen}"
                                    var="data"
                                    id="dataTiposSalidaAlmacen"
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
                                <h:outputText value="Tipo de Salida"  />
                            </f:facet>
                            <h:outputText value="#{data.nombreTipoSalidaAlmacen}">
                            </h:outputText>
                        </rich:column>
                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value="Descripción"  />
                            </f:facet>
                            <h:outputText value="#{data.obsTipoSalidaAlmacen}"  />
                        </rich:column>
                      
                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value="Estado de Registro"  />
                            </f:facet>
                            <h:outputText value="#{data.estadoReferencial.nombreEstadoRegistro}"  />
                        </rich:column>

                    </rich:dataTable>






                    <br>
                        <a4j:commandButton value="Registrar" styleClass="btn" action="#{ManagedTiposSalidaAlmacen.nuevoTipoSalidaAlmacen_Action}" oncomplete="Richfaces.showModalPanel('PanelRegistrarTiposSalidaAlmacen')" reRender="contenidoRegistrarTiposSalidaAlmacen" />
                        <a4j:commandButton value="Editar" styleClass="btn" onclick="if(editarItem('form1:dataTiposSalidaAlmacen')==false){return false;};" action="#{ManagedTiposSalidaAlmacen.editarTipoSalidaAlmacen_Action}" oncomplete="if(#{ManagedTiposSalidaAlmacen.cantidadDependencias==0}){Richfaces.showModalPanel('PanelEditarTiposSalidaAlmacen')}else{alert('#{ManagedTiposSalidaAlmacen.mensaje}');}" reRender="contenidoEditarTiposSalidaAlmacen" />
                        <a4j:commandButton value="Eliminar" styleClass="btn" onclick="if(confirm('Esta seguro de eliminar el registro?')){if(editarItem('form1:dataTiposSalidaAlmacen')==false){return false;}}else{return false;}"  action="#{ManagedTiposSalidaAlmacen.eliminarTipoSalidaAlmacen_Action}" oncomplete="if(#{ManagedTiposSalidaAlmacen.mensaje!=''}){alert('#{ManagedTiposSalidaAlmacen.mensaje}');}else{alert('Se elimino el tipo de salida almacen');}" reRender="dataTiposSalidaAlmacen"/>

                </div>



            </a4j:form>
             <rich:modalPanel id="PanelRegistrarTiposSalidaAlmacen" minHeight="180"  minWidth="560"
                                     height="180" width="560"
                                     zindex="180"
                                     headerClass="headerClassACliente"
                                     resizeable="false" style="overflow :auto" >
                        <f:facet name="header">
                            <h:outputText value="Registro de Tipos de Salida Almacen"/>
                        </f:facet>
                        <a4j:form>
                        <h:panelGroup id="contenidoRegistrarTiposSalidaAlmacen">
                            <h:panelGrid columns="6">

                                <h:outputText value="Tipo de Salida" styleClass="outputText1" />
                                 <h:outputText value="::" styleClass="outputText1" />
                                 <h:inputText value="#{ManagedTiposSalidaAlmacen.nuevoTipoSalidaAlmacen.nombreTipoSalidaAlmacen}" styleClass="inputText" style="width:200px"/>
                                
                                <h:outputText value="Estado de Registro" styleClass="outputText1" />
                                <h:outputText value="::" styleClass="outputText1" />
                                <h:inputText value="Activo" styleClass="inputText" disabled="true" />

                                 <h:outputText value="Observaciones:" styleClass="outputText1" />
                                 <h:outputText value="::" styleClass="outputText1" />
                                 <h:inputTextarea value="#{ManagedTiposSalidaAlmacen.nuevoTipoSalidaAlmacen.obsTipoSalidaAlmacen}" rows="3" styleClass="inputText" style="width:200px"/>
                                                              
                                
                            </h:panelGrid>
                         
                                
                        </h:panelGroup>
                        
                        
                        
                          <div align="center">
                              <a4j:commandButton styleClass="btn" value="Registrar"  action="#{ManagedTiposSalidaAlmacen.guardarTipoSalidaAlmacen_Action}"
                                    oncomplete="javascript:Richfaces.hideModalPanel('PanelRegistrarTiposSalidaAlmacen')"
                                     reRender="dataTiposSalidaAlmacen"/>
                                <input type="button" value="Cancelar" onclick="javascript:Richfaces.hideModalPanel('PanelRegistrarTiposSalidaAlmacen')" class="btn" />
                                </div>
                        </a4j:form>
                         
            </rich:modalPanel>

           <rich:modalPanel id="PanelEditarTiposSalidaAlmacen" minHeight="180"  minWidth="560"
                                     height="180" width="560"
                                     zindex="180"
                                     headerClass="headerClassACliente"
                                     resizeable="false" style="overflow :auto" >
                        <f:facet name="header">
                            <h:outputText value="Edición de Tipos de Salida Almacen"/>
                        </f:facet>
                        <a4j:form id="formEditar">
                        <h:panelGroup id="contenidoEditarTiposSalidaAlmacen">
                            <h:panelGrid columns="6">
                                <h:outputText value="Tipo de Salida" styleClass="outputText1" />
                                <h:outputText value="::" styleClass="outputText1" />
                                <h:inputText value="#{ManagedTiposSalidaAlmacen.editarTipoSalidaAlmacen.nombreTipoSalidaAlmacen}" styleClass="inputText" style="width:200px"/>

                                <h:outputText value="Estado de Registro" styleClass="outputText1" />
                                <h:outputText value="::" styleClass="outputText1" />
                                <h:selectOneMenu value="#{ManagedTiposSalidaAlmacen.editarTipoSalidaAlmacen.estadoReferencial.codEstadoRegistro}"styleClass="inputText">
                                    <f:selectItem itemValue="1" itemLabel="Activo"/>
                                    <f:selectItem itemValue="2" itemLabel="No Activo"/>
                                </h:selectOneMenu>

                                 <h:outputText value="Observaciones:" styleClass="outputText1" />
                                 <h:outputText value="::" styleClass="outputText1" />
                                 <h:inputTextarea value="#{ManagedTiposSalidaAlmacen.editarTipoSalidaAlmacen.obsTipoSalidaAlmacen}" rows="3" styleClass="inputText" style="width:200px"/>

                            </h:panelGrid>

                            </h:panelGroup>
                        
                                <div align="center">
                                <a4j:commandButton styleClass="btn" value="Guardar" onclick="if(confirm('Esta seguro de editar el Tipo de Salida Almacen?')==false){return false;}"
                                action="#{ManagedTiposSalidaAlmacen.guardarEdicionTipoSalidaAlmacen_Action}" oncomplete="if(#{ManagedTiposSalidaAlmacen.mensaje!=''}){alert('#{ManagedTiposSalidaAlmacen.mensaje}');}else{javascript:Richfaces.hideModalPanel('PanelEditarTiposSalidaAlmacen');}" reRender="dataTiposSalidaAlmacen" />
                                <input type="button" value="Cancelar" onclick="javascript:Richfaces.hideModalPanel('PanelEditarTiposSalidaAlmacen')" class="btn" />

                                </div>
                        </a4j:form>
            </rich:modalPanel>

             
                 
        </body>
    </html>

</f:view>

