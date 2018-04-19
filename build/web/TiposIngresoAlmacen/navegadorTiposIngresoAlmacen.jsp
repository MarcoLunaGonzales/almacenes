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
                    <h:outputText value="#{ManagedTiposIngresosAlmacen.initTiposIngresoAlmacen}"/>
                    <h:outputText styleClass="outputTextTitulo"  value="Tipos de Ingreso Almacen" />
                    <br><br>
                        <h:selectOneMenu value="#{ManagedTiposIngresosAlmacen.codEstadoRegistro}">
                            <f:selectItem itemValue="1" itemLabel="Activo"/>
                            <f:selectItem itemValue="2" itemLabel="No Activo"/>
                            <f:selectItem itemValue="3" itemLabel="Todos"/>
                            <a4j:support event="onchange" action="#{ManagedTiposIngresosAlmacen.onChange_EstadoRegistro}" reRender="dataTiposIngresoAlmacen"/>
                        </h:selectOneMenu>

                       <rich:dataTable value="#{ManagedTiposIngresosAlmacen.listaTiposIngresosAlmacen}"
                                    var="data"
                                    id="dataTiposIngresoAlmacen"
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
                            <h:outputText value="#{data.nombreTipoIngresoAlmacen}">
                            </h:outputText>
                        </rich:column>
                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value="Descripción"  />
                            </f:facet>
                            <h:outputText value="#{data.obsTipoIngresoAlmacen}"  />
                        </rich:column>
                      
                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value="Estado de Registro"  />
                            </f:facet>
                            <h:outputText value="#{data.estadoReferencial.nombreEstadoRegistro}"  />
                        </rich:column>

                    </rich:dataTable>






                    <br>
                        <a4j:commandButton value="Registrar" styleClass="btn" action="#{ManagedTiposIngresosAlmacen.nuevoTipoIngresoAlmacen_Action}" oncomplete="Richfaces.showModalPanel('PanelRegistrarTiposIngresoAlmacen')" reRender="contenidoRegistrarTiposIngresoAlmacen" />
                        <a4j:commandButton value="Editar" styleClass="btn" onclick="if(editarItem('form1:dataTiposIngresoAlmacen')==false){return false;};" action="#{ManagedTiposIngresosAlmacen.editarTipoIngresoAlmacen_Action}" oncomplete="if(#{ManagedTiposIngresosAlmacen.cantidadDependencias}==0){Richfaces.showModalPanel('PanelEditarTiposIngresoAlmacen')}else{alert('#{ManagedTiposIngresosAlmacen.mensaje}');}" reRender="contenidoEditarTiposIngresoAlmacen" />
                        <a4j:commandButton value="Eliminar" styleClass="btn" onclick="if(confirm('Esta seguro de eliminar el registro?')){if(editarItem('form1:dataTiposIngresoAlmacen')==false){return false;}}else{return false;}"  action="#{ManagedTiposIngresosAlmacen.eliminarTipoIngresoAlmacen_Action}" oncomplete="if(#{ManagedTiposIngresosAlmacen.mensaje!=''}){alert('#{ManagedTiposIngresosAlmacen.mensaje}');}else{alert('Se elimino el tipo de ingreso almacen');}" reRender="dataTiposIngresoAlmacen"/>

                </div>



            </a4j:form>
             <rich:modalPanel id="PanelRegistrarTiposIngresoAlmacen" minHeight="180"  minWidth="560"
                                     height="180" width="560"
                                     zindex="180"
                                     headerClass="headerClassACliente"
                                     resizeable="false" style="overflow :auto" >
                        <f:facet name="header">
                            <h:outputText value="Registro de Tipos de Ingreso Almacen"/>
                        </f:facet>
                        <a4j:form>
                        <h:panelGroup id="contenidoRegistrarTiposIngresoAlmacen">
                            <h:panelGrid columns="6">

                                <h:outputText value="Tipo de Salida" styleClass="outputText1" />
                                 <h:outputText value="::" styleClass="outputText1" />
                                 <h:inputText value="#{ManagedTiposIngresosAlmacen.nuevoTipoIngresoAlmacen.nombreTipoIngresoAlmacen}" styleClass="inputText" style="width:200px"/>
                                
                                <h:outputText value="Estado de Registro" styleClass="outputText1" />
                                <h:outputText value="::" styleClass="outputText1" />
                                <h:inputText value="Activo" styleClass="inputText" disabled="true" />

                                 <h:outputText value="Observaciones:" styleClass="outputText1" />
                                 <h:outputText value="::" styleClass="outputText1" />
                                 <h:inputTextarea value="#{ManagedTiposIngresosAlmacen.nuevoTipoIngresoAlmacen.obsTipoIngresoAlmacen}" rows="3" styleClass="inputText" style="width:200px"/>
                                                              
                                
                            </h:panelGrid>
                         
                                
                        </h:panelGroup>
                        
                        
                        
                          <div align="center">
                              <a4j:commandButton styleClass="btn" value="Registrar"  action="#{ManagedTiposIngresosAlmacen.guardarTipoIngresoAlmacen_Action}"
                                    oncomplete="javascript:Richfaces.hideModalPanel('PanelRegistrarTiposIngresoAlmacen')"
                                     reRender="dataTiposIngresoAlmacen"/>
                                <input type="button" value="Cancelar" onclick="javascript:Richfaces.hideModalPanel('PanelRegistrarTiposIngresoAlmacen')" class="btn" />
                                </div>
                        </a4j:form>
                         
            </rich:modalPanel>

           <rich:modalPanel id="PanelEditarTiposIngresoAlmacen" minHeight="180"  minWidth="560"
                                     height="180" width="560"
                                     zindex="180"
                                     headerClass="headerClassACliente"
                                     resizeable="false" style="overflow :auto" >
                        <f:facet name="header">
                            <h:outputText value="Edición de Tipos de Ingreso Almacen"/>
                        </f:facet>
                        <a4j:form id="formEditar">
                        <h:panelGroup id="contenidoEditarTiposIngresoAlmacen">
                            <h:panelGrid columns="6">
                                <h:outputText value="Tipo de Salida" styleClass="outputText1" />
                                <h:outputText value="::" styleClass="outputText1" />
                                <h:inputText value="#{ManagedTiposIngresosAlmacen.editarTipoIngresoAlmacen.nombreTipoIngresoAlmacen}" styleClass="inputText" style="width:200px"/>

                                <h:outputText value="Estado de Registro" styleClass="outputText1" />
                                <h:outputText value="::" styleClass="outputText1" />
                                <h:selectOneMenu value="#{ManagedTiposIngresosAlmacen.editarTipoIngresoAlmacen.estadoReferencial.codEstadoRegistro}"styleClass="inputText">
                                    <f:selectItem itemValue="1" itemLabel="Activo"/>
                                    <f:selectItem itemValue="2" itemLabel="No Activo"/>
                                </h:selectOneMenu>

                                 <h:outputText value="Observaciones:" styleClass="outputText1" />
                                 <h:outputText value="::" styleClass="outputText1" />
                                 <h:inputTextarea value="#{ManagedTiposIngresosAlmacen.editarTipoIngresoAlmacen.obsTipoIngresoAlmacen}" rows="3" styleClass="inputText" style="width:200px"/>

                            </h:panelGrid>

                            </h:panelGroup>
                        
                                <div align="center">
                                <a4j:commandButton styleClass="btn" value="Guardar" onclick="if(confirm('Esta seguro de editar el Tipo de Ingreso Almacen?')==false){return false;}"
                                action="#{ManagedTiposIngresosAlmacen.guardarEdicionTipoIngresoAlmacen_Action}" oncomplete="if(#{ManagedTiposIngresosAlmacen.mensaje!=''}){alert('#{ManagedTiposIngresosAlmacen.mensaje}');}else{javascript:Richfaces.hideModalPanel('PanelEditarTiposIngresoAlmacen');}" reRender="dataTiposIngresoAlmacen" />
                                <input type="button" value="Cancelar" onclick="javascript:Richfaces.hideModalPanel('PanelEditarTiposIngresoAlmacen')" class="btn" />

                                </div>
                        </a4j:form>
            </rich:modalPanel>

             
                 
        </body>
    </html>

</f:view>

