<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib prefix="a4j" uri="http://richfaces.org/a4j"%>
<%@taglib prefix="rich" uri="http://richfaces.org/rich"%>
<rich:modalPanel id="panelDetalleItem" minHeight="400"  minWidth="1100"
                height="400" width="1100"
                zindex="200"
                headerClass="headerClassACliente"
                resizeable="false" style="overflow :auto" >
    <f:facet name="header">
        <h:outputText value="<center>Detalle Item</center>" escape="false"/>
    </f:facet>
    <a4j:form id="form2" >
        <center>
        <h:panelGroup id="contenidoDetalleItem">
            <h:outputText value="Item:" styleClass="outputTextBold" />
            <h:outputText value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalle.materiales.nombreMaterial}" styleClass="outputText2" />
            <h:panelGrid columns="9" id="contenidoIngresoAlmacenDetalle" headerClass="headerClassACliente" width="100%" style="border:1px solid #000000;" >
                <f:facet name="header">
                    <h:outputText value="Etiquetas" />
                </f:facet>
                <h:outputText value="Cantidad Segun Documento:" styleClass="outputTextBold" />
                <h:panelGroup>                                     
                   <h:inputText value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalle.cantTotalIngreso}" styleClass="inputText" size="5" id="cantTotalIngreso" onkeypress="valNum(event);" readonly="true" onkeyup="hallaValorEquivalente(this)" />
                   <h:outputText value="[" styleClass="outputText2" />
                   <h:outputText value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalle.ordenesCompraDetalle.unidadesMedida.abreviatura}" styleClass="outputText2" />
                   <h:outputText value="]" styleClass="outputText2" />
                </h:panelGroup>
                <h:outputText value="Equivalente:" styleClass="outputTextBold" />
                <h:panelGroup>
                    <h:inputText value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalle.cantEquivalente}" styleClass="inputText" size="5" readonly="true" onkeypress="valNum(event);" id="cantEquivalente" />
                    <h:outputText value="[" styleClass="outputText1" />
                    <h:outputText value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalle.unidadesMedida.abreviatura}" styleClass="outputText1" />
                    <h:outputText value="]" styleClass="outputText1" />
                    <h:inputHidden value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalle.valorEquivalencia}" id="valorEquivalencia" />
                </h:panelGroup>
                <h:outputText value="Cant. Ingreso Fisico:" styleClass="outputTextBold" />
                <h:panelGroup>
                    <h:inputText value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalle.cantTotalIngresoFisico}" id="cantTotalIngresoFisico"  styleClass="inputText" size="5" onkeypress="valNum(event);" readonly="true" />
                    <h:outputText value="[" styleClass="outputText1" />
                    <h:outputText value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalle.unidadesMedida.abreviatura}" styleClass="outputText1" />
                    <h:outputText value="]" styleClass="outputText1" />
                </h:panelGroup>
                <h:outputText value="Nro de Empaques:" styleClass="outputTextBold" />
                <h:inputText value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalle.nroUnidadesEmpaque}" styleClass="inputText"  onkeypress="valNum(event);" size="5"  />
                <a4j:commandButton value="Generar Etiquetas" action="#{ManagedIngresoAlmacenOrdenCompra.generarEtiquetas_action}" styleClass="btn"  reRender="dataIngresoAlmacenDetalleEstado"  />
            </h:panelGrid>
            <br/>
            <rich:panel id="datosRepetirValores" headerClass="headerClassACliente" style="padding:0px !important;width:100%;border:1px solid #000000;">
                <f:facet name="header">
                    <h:outputText value="Repetir Valores" />
                </f:facet>
                <h:panelGrid columns="6" width="100%" id="panelValores">
                    <h:outputText value="Lote Proveedor:" styleClass="outputTextBold" />
                    <h:inputText value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalleEstado.loteMaterialProveedor}" styleClass="inputText" size="10">
                        <a4j:support event="onblur" action="#{ManagedIngresoAlmacenOrdenCompra.loteMaterialProveedor_onBlur}"
                                     reRender="panelFechaVencimiento,panelEstadoMaterial"/>
                    </h:inputText>
                    <h:outputText value="Fecha de Vencimiento:" styleClass="outputTextBold" />
                    <h:panelGroup id="panelFechaVencimiento" 
                                  rendered="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalle.materiales.grupos.gruposFechaVencimiento.aplicaFechaVencimiento
                                                || ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalle.aplicaFechaVencimiento}">
                        <h:outputText  value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalleEstado.fechaVencimientoFormatoMMYY}"
                                      styleClass="outputText2"
                                      rendered="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalleEstado.loteProveedorEncontradoIngresoAnterior}">
                            <f:convertDateTime pattern="dd/MM/yyyy" locale="en"/>
                        </h:outputText>
                        <h:panelGroup rendered="#{!ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalleEstado.loteProveedorEncontradoIngresoAnterior}">
                            <h:inputText value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalleEstado.fechaVencimientoFormatoMMYY}" styleClass="inputText"
                                         id="fechaVencimiento" size="7">
                                <f:validator validatorId="validatorVencimientoMMYYYY"/>
                                <f:attribute name="disable" value="#{(empty param['modalDetalleItem:form2:buttonRepetir'])}"/>
                            </h:inputText>
                            <br/>
                            <h:message for="fechaVencimiento" styleClass="message"/>
                        </h:panelGroup>
                    </h:panelGroup>
                    <h:panelGroup rendered="#{!(ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalle.materiales.grupos.gruposFechaVencimiento.aplicaFechaVencimiento
                                                    || ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalle.aplicaFechaVencimiento)}">
                        <h:outputText value="No Aplica" styleClass="outputTextBold" style="color:red" />
                        
                    </h:panelGroup>
                    
                    <h:outputText value="El item si aplica fecha de vencimiento" styleClass="outputTextBold" rendered="#{!ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalle.materiales.grupos.gruposFechaVencimiento.aplicaFechaVencimiento}"/>
                    <h:selectBooleanCheckbox value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalle.aplicaFechaVencimiento}"
                                             rendered="#{!ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalle.materiales.grupos.gruposFechaVencimiento.aplicaFechaVencimiento}">
                        <a4j:support event="onclick" reRender="panelValores,dataIngresoAlmacenDetalleEstado"/>
                    </h:selectBooleanCheckbox>
                    
                    <h:outputText value="" rendered="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalle.materiales.grupos.gruposFechaVencimiento.aplicaFechaVencimiento}"/>
                    <h:outputText value="" rendered="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalle.materiales.grupos.gruposFechaVencimiento.aplicaFechaVencimiento}"/>
                    
                    <h:outputText value="Cantidad Parcial:" styleClass="outputTextBold"  />
                    <h:inputText value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalleEstado.cantidadParcial}" styleClass="inputText" size="10" onkeypress="valNum(event);" id="cantidadParcial" >
                    </h:inputText>
                    <h:outputText value="Tara:" styleClass="outputTextBold" />
                    <h:inputText value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalleEstado.tara}" styleClass="inputText" size="10" onkeypress="valNum(event)" > <%-- onkeypress="valNum(event);" --%>
                    </h:inputText>
                    <h:panelGroup>
                        <h:selectBooleanCheckbox value="#{ManagedIngresoAlmacenOrdenCompra.conDensidad}"  />
                        <h:outputText value="Densidad:" styleClass="outputTextBold" />
                    </h:panelGroup>
                    <h:inputText value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalleEstado.densidad}" styleClass="inputText" size="10"  >
                    </h:inputText>
                    <h:outputText value="Unidad de Empaque:" styleClass="outputTextBold" />
                    <h:selectOneMenu value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalleEstado.empaqueSecundarioExterno.codEmpaqueSecundarioExterno}" styleClass="inputText" >
                        <f:selectItems value="#{ManagedIngresoAlmacenOrdenCompra.empaqueSecundarioExternoList}"  />
                    </h:selectOneMenu>
                    <h:outputText value="Estado Material" styleClass="outputTextBold" />
                    <h:panelGroup id="panelEstadoMaterial">
                        <h:outputText value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalleEstado.estadosMaterial.nombreEstadoMaterial}" styleClass="outputText2" rendered="#{! ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalleEstado.loteProveedorEncontradoIngresoAnterior}"/>
                        <h:selectOneMenu value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalleEstado.estadosMaterial.codEstadoMaterial}" styleClass="inputText" rendered="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalleEstado.loteProveedorEncontradoIngresoAnterior}">
                            <f:selectItems value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalleEstado.estadosMaterialSelectList}"  />
                        </h:selectOneMenu>
                    </h:panelGroup>
                    <h:outputText value="" styleClass="outputTextBold" />
                    <h:outputText value="" styleClass="outputTextBold" />
                    
                </h:panelGrid>
                <h:panelGrid columns="8" styleClass="tablaGestiones"
                             rendered="#{ManagedAccesoSistema.almacenesGlobal.aplicaGestionUbicaciones}">
                    <f:facet name="header">
                        <h:outputText value="Gestión de ubicaciones" styleClass="outputText2"/>
                    </f:facet>
                    <h:outputText value="Ambiente:" styleClass="outputTextBold" />
                    <h:selectOneMenu value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalleEstado.estanteAlmacen.ambienteAlmacen.codAmbiente}" styleClass="inputText" >
                       <f:selectItem itemValue="0" itemLabel="-NINGUNO-" />
                       <f:selectItems value="#{ManagedIngresoAlmacenOrdenCompra.ambienteAlmacenList}"  />
                       <a4j:support event="onchange" reRender="estante" action="#{ManagedIngresoAlmacenOrdenCompra.ambienteAlmacen_change}" />
                    </h:selectOneMenu>
                    <h:outputText value="Pasillo:" styleClass="outputTextBold" />
                    <h:selectOneMenu value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalleEstado.estanteAlmacen.codEstante}" styleClass="inputText" id="estante" >
                       <f:selectItem itemValue="0" itemLabel="-NINGUNO-" />
                       <f:selectItems value="#{ManagedIngresoAlmacenOrdenCompra.estanteAlmacenList}"  />
                    </h:selectOneMenu>
                    <h:panelGrid columns="2">
                        <h:outputText value="Estante:" styleClass="outputTextBold" />
                        <h:inputText value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalleEstado.fila}" styleClass="inputText" size="10"  ><!--onkeypress="valNum(event);"-->
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid columns="2">
                        <h:outputText value="Ubicacion:" styleClass="outputTextBold" />
                        <h:inputText value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalleEstado.columna}" styleClass="inputText" size="10"  ><!--onkeypress="valNum(event);"-->
                        </h:inputText>
                    </h:panelGrid>
                </h:panelGrid>
                    <center>
                        <h:selectBooleanCheckbox id="seleccionar_todo"  onclick="seleccionarTodo('modalDetalleItem:form2:dataIngresoAlmacenDetalleEstado');"/>
                        <a4j:commandButton action="#{ManagedIngresoAlmacenOrdenCompra.repetirValoresIngresoAlmacenDetalleEstado_action}" 
                                           id="buttonRepetir"
                                            oncomplete="if(#{ManagedIngresoAlmacenOrdenCompra.mensaje!=''}){alert('#{ManagedIngresoAlmacenOrdenCompra.mensaje}')}" 
                                            reRender="dataIngresoAlmacenDetalleEstado,contenidoIngresoAlmacenDetalle,datosRepetirValores" 
                                            value="Repetir Valores" 
                                            styleClass="btn" />
                    </center>
            </rich:panel>


               <br/>

               <rich:dataTable value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalle.ingresosAlmacenDetalleEstadoList}" var="data"
                       id="dataIngresoAlmacenDetalleEstado"
                       onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                       onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                       headerClass="headerClassACliente">
                   <h:column>
                       <f:facet name="header">
                           <h:outputText value=""  />
                       </f:facet>
                       <h:selectBooleanCheckbox value="#{data.checked}"  />

                   </h:column>
                   <h:column>
                       <f:facet name="header">
                           <h:outputText value=""  />
                       </f:facet>
                       <h:outputText  value="#{data.etiqueta}"/>
                   </h:column>

                   <h:column>
                       <f:facet name="header">
                           <h:outputText value="Estado Item"  />
                       </f:facet>
                       <h:outputText  value="#{data.estadosMaterial.nombreEstadoMaterial}" rendered="#{!data.loteProveedorEncontradoIngresoAnterior}"/>
                       <h:selectOneMenu value="#{data.estadosMaterial.codEstadoMaterial}" styleClass="inputText" rendered="#{data.loteProveedorEncontradoIngresoAnterior}">
                           <f:selectItems value="#{data.estadosMaterialSelectList}"  />
                       </h:selectOneMenu>
                       
                   </h:column>

                   <h:column>
                       <f:facet name="header">
                           <h:outputText value="Empaque"  />
                       </f:facet>
                       <h:outputText value="#{data.empaqueSecundarioExterno.nombreEmpaqueSecundarioExterno}"  />
                   </h:column>

                   <h:column>
                       <f:facet name="header">
                           <h:outputText value="Cantidad Parcial"  />
                       </f:facet>
                       <h:inputText value="#{data.cantidadParcial}" styleClass="inputText" size="5" 
                                     converterMessage="Debe registrar un número válido"
                                     id="cantidadParcial"
                                     onkeyup="hallaCantidadIngresoFisico()" >
                            <f:validator validatorId="validatorDoubleRange"/>
                            <f:attribute name="minimum" value="0.01"/>
                            <f:attribute name="disable" value="#{(empty param['modalDetalleItem:form2:buttonGuardarDetalle'])}"/>
                        </h:inputText>
                        <h:message for="cantidadParcial" styleClass="message"/>
                   </h:column>
                   <h:column>
                        <f:facet name="header">
                            <h:outputText value="Densidad"  />
                        </f:facet>
                        <h:inputText value="#{data.densidad}" styleClass="inputText" size="5" onkeyup="hallaCantidadX(this)" />
                   </h:column>
                   <h:column>
                       <f:facet name="header">
                           <h:outputText value="Tara"  />
                       </f:facet>
                       <h:inputText value="#{data.tara}" styleClass="inputText" size="5" />
                   </h:column>
                   <h:column>
                       <f:facet name="header">
                           <h:outputText value="Fecha Manufactura"  />
                       </f:facet>
                       <h:inputText value="#{data.fechaManufactura}" styleClass="inputText" size="10"  onblur="valFecha(this);"   ><%--  onkeyup="hallaCantidadIngresoFisico()"--%>
                           <f:convertDateTime pattern="dd/MM/yyyy" />    
                       </h:inputText>
                   </h:column>
                   <h:column>
                       <f:facet name="header">
                           <h:outputText value="Lote Proveedor"  />
                       </f:facet>
                       <h:inputText value="#{data.loteMaterialProveedor}" styleClass="inputText">
                           <a4j:support event="onblur" action="#{ManagedIngresoAlmacenOrdenCompra.loteMaterialProveedorSeleccionadoOnBlur}"
                                        reRender="dataIngresoAlmacenDetalleEstado">
                               <f:setPropertyActionListener value="#{data}" target="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalleEstadoSeleccionado}"/>
                           </a4j:support>
                       </h:inputText>
                   </h:column>
                   <h:column rendered="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalle.materiales.grupos.gruposFechaVencimiento.aplicaFechaVencimiento
                                                || ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalle.aplicaFechaVencimiento}">
                       <f:facet name="header">
                           <h:outputText value="Fecha Vencimiento"  />
                       </f:facet>
                        <h:inputText value="#{data.fechaVencimientoFormatoMMYY}" 
                                     rendered="#{!data.loteProveedorEncontradoIngresoAnterior}" 
                                     id="fechaVencimientoDetalle"
                                     styleClass="inputText" size="8" >
                            <f:validator validatorId="validatorVencimientoMMYYYY"/>
                            <f:attribute name="disable" value="#{(empty param['modalDetalleItem:form2:buttonGuardarDetalle'])}"/>
                        </h:inputText>
                         <h:message for="fechaVencimientoDetalle" styleClass="message"/>
                        <h:outputText value="#{data.fechaVencimientoFormatoMMYY}" rendered="#{data.loteProveedorEncontradoIngresoAnterior}" />
                        
                   </h:column>
                   <h:column rendered="#{ManagedAccesoSistema.almacenesGlobal.aplicaGestionUbicaciones}">
                       <f:facet name="header">
                           <h:outputText value="Pasillo"  />
                       </f:facet>
                       <h:outputText value="#{data.estanteAlmacen.nombreEstante}"  />
                   </h:column>
                   <h:column rendered="#{ManagedAccesoSistema.almacenesGlobal.aplicaGestionUbicaciones}">
                       <f:facet name="header">
                           <h:outputText value="Estante"   />
                       </f:facet>
                       <h:inputText value="#{data.fila}" styleClass="inputText" size="2" />
                   </h:column>
                   <h:column rendered="#{ManagedAccesoSistema.almacenesGlobal.aplicaGestionUbicaciones}">
                       <f:facet name="header">
                           <h:outputText value="Ubicacion"  />
                       </f:facet>
                       <h:inputText value="#{data.columna}"  styleClass="inputText" size="2" />
                   </h:column>

                   <h:column>
                       <f:facet name="header">
                           <h:outputText value="Obervaciones"  />
                       </f:facet>
                       <h:inputText value="#{data.observaciones}" size="20" styleClass="inputText"  />
                   </h:column>

               </rich:dataTable>
               </h:panelGroup>
                   <a4j:commandButton styleClass="btn" value="Registrar"   
                                      id="buttonGuardarDetalle"
                                      oncomplete="if('#{facesContext.maximumSeverity}'.length==0){if(#{ManagedIngresoAlmacenOrdenCompra.mensaje!=''}){alert('#{ManagedIngresoAlmacenOrdenCompra.mensaje}')}else{Richfaces.hideModalPanel('panelDetalleItem')}}"
                                      action="#{ManagedIngresoAlmacenOrdenCompra.guardarDetalleItem_action}"
                                      reRender="dataIngresoAlmacenDetalle,dataIngresoAlmacenDetalleEstado"
                                      onclick="if(validarRegistroDetalle()==false){return false;}"
                                       />
                  <a4j:commandButton styleClass="btn" value="Limpiar Detalle"
                  oncomplete="Richfaces.hideModalPanel('panelDetalleItem')"
                  action="#{ManagedIngresoAlmacenOrdenCompra.cancelarDetalleItem_action}"
                  reRender="dataIngresoAlmacenDetalle"
                   />
        </center>
    </a4j:form>
</rich:modalPanel>