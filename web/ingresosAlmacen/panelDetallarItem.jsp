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
        <h:outputText value="Detalle Item"/>
    </f:facet>
    <a4j:form id="form3">
        <center>
            <h:panelGroup id="contenidoDetalleItem">
                <h:outputText value="Item: " styleClass="tituloCampo1" />
                <h:outputText value="#{ManagedIngresoAlmacen.ingresosAlmacenDetalle.materiales.nombreMaterial}" styleClass="tituloCampo1" />
                <br/>
                <h:panelGrid columns="9" id="contenidoIngresoAlmacenDetalle" headerClass="headerClassACliente" width="100%" style="border:1px solid #000000;" >
                    <f:facet name="header">
                          <h:outputText value="Repetir Valores" />
                    </f:facet>

                    <h:outputText value="Cantidad Segun Documento:" styleClass="outputText1" />

                    <h:panelGroup>
                        <h:inputText value="#{ManagedIngresoAlmacen.ingresosAlmacenDetalle.cantTotalIngreso}" styleClass="inputText" size="5" id="cantTotalIngreso" onkeypress="valNum(event);" readonly="true" onkeyup="hallaValorEquivalente(this)" />
                        <h:outputText value="[" styleClass="outputText1" />
                        <h:outputText value="#{ManagedIngresoAlmacen.ingresosAlmacenDetalle.ordenesCompraDetalle.unidadesMedida.abreviatura}" styleClass="outputText1" />
                        <h:outputText value="]" styleClass="outputText1" />
                    </h:panelGroup>

                    <h:outputText value="Equivalente:" styleClass="outputText1" />
                    <h:panelGroup>
                        <h:inputText value="#{ManagedIngresoAlmacen.ingresosAlmacenDetalle.cantEquivalente}" styleClass="inputText" size="5" readonly="true" onkeypress="valNum(event);" id="cantEquivalente" />
                        <h:outputText value="[" styleClass="outputText1" />
                        <h:outputText value="#{ManagedIngresoAlmacen.ingresosAlmacenDetalle.unidadesMedidaEquivalencia.abreviatura}" styleClass="outputText1" />
                        <h:outputText value="]" styleClass="outputText1" />
                        <h:inputHidden value="#{ManagedIngresoAlmacen.ingresosAlmacenDetalle.valorEquivalencia}" id="valorEquivalencia" />
                    </h:panelGroup>

                    <h:outputText value="Cant. Ingreso Fisico:" styleClass="outputText1" />
                    <h:panelGroup>
                        <h:inputText value="#{ManagedIngresoAlmacen.ingresosAlmacenDetalle.cantTotalIngresoFisico}" id="cantTotalIngresoFisico"  styleClass="inputText" size="5" onkeypress="valNum(event);" readonly="true" />
                        <h:outputText value="[" styleClass="outputText1" />
                        <h:outputText value="#{ManagedIngresoAlmacen.ingresosAlmacenDetalle.unidadesMedida.abreviatura}" styleClass="outputText1" />
                        <h:outputText value="]" styleClass="outputText1" />
                    </h:panelGroup>

                    <h:outputText value="Nro de Empaques:" styleClass="outputText1" />

                    <h:inputText value="#{ManagedIngresoAlmacen.ingresosAlmacenDetalle.nroUnidadesEmpaque}"
                                 styleClass="inputText" size="5" onkeypress="valNum(event);" onblur="validarEntero(this)"/>
                    <a4j:commandButton value="Generar Etiquetas" action="#{ManagedIngresoAlmacen.generarEtiquetas_action}" styleClass="btn"  reRender="dataIngresoAlmacenDetalleEstado" timeout="7200" />
                  </h:panelGrid>

                  <br/>
                <rich:panel id="datosRepetirValores" headerClass="headerClassACliente"
                            style="padding:0px !important;width:100%;border:1px solid #000000;">
                    <f:facet name="header">
                        <h:outputText value="Repetir Valores" />
                    </f:facet>
                    <h:panelGrid columns="6" width="100%"  id="panelValores">
                        <h:outputText value="Lote Proveedor:" styleClass="outputTextBold" />
                        <h:inputText value="#{ManagedIngresoAlmacen.ingresosAlmacenDetalleEstado.loteMaterialProveedor}" styleClass="inputText" size="10"  >
                            <a4j:support event="onblur" action="#{ManagedIngresoAlmacen.loteMaterialProveedorOnBlur}" reRender="panelFechaVencimiento"/>
                        </h:inputText>
                        <h:outputText value="Fecha de Vencimiento(MES/AÑO):" styleClass="outputTextBold" />
                        <h:panelGroup id="panelFechaVencimiento" 
                                      rendered="#{ManagedIngresoAlmacen.ingresosAlmacenDetalle.materiales.grupos.gruposFechaVencimiento.aplicaFechaVencimiento
                                                    || ManagedIngresoAlmacen.ingresosAlmacenDetalle.aplicaFechaVencimiento}">
                            <h:outputText   value="#{ManagedIngresoAlmacen.ingresosAlmacenDetalleEstado.fechaVencimiento}"
                                            styleClass="outputText2"
                                            rendered="#{ManagedIngresoAlmacen.ingresosAlmacenDetalleEstado.loteProveedorEncontradoIngresoAnterior}">
                                <f:convertDateTime pattern="MM/yyyy" locale="en"/>
                            </h:outputText>
                            <h:panelGroup rendered="#{!ManagedIngresoAlmacen.ingresosAlmacenDetalleEstado.loteProveedorEncontradoIngresoAnterior}">
                                <h:inputText value="#{ManagedIngresoAlmacen.ingresosAlmacenDetalleEstado.fechaVencimientoFormatoMMYY}" styleClass="inputText"
                                             id="fechaVencimiento" size="7">
                                    <f:validator validatorId="validatorVencimientoMMYYYY"/>
                                    <f:attribute name="disable" value="#{(empty param['detalleItem:form3:buttonRepetir'])}"/>
                                </h:inputText>
                                <br/>
                                <h:message for="fechaVencimiento" styleClass="message"/>
                            </h:panelGroup>
                        </h:panelGroup>
                        <h:panelGroup rendered="#{!(ManagedIngresoAlmacen.ingresosAlmacenDetalle.materiales.grupos.gruposFechaVencimiento.aplicaFechaVencimiento
                                                        || ManagedIngresoAlmacen.ingresosAlmacenDetalle.aplicaFechaVencimiento)}">
                            <h:outputText value="No Aplica" styleClass="outputTextBold" style="color:red" />

                        </h:panelGroup>

                        <h:outputText value="El item si aplica fecha de vencimiento" styleClass="outputTextBold" 
                                      rendered="#{!ManagedIngresoAlmacen.ingresosAlmacenDetalle.materiales.grupos.gruposFechaVencimiento.aplicaFechaVencimiento}"/>
                        <h:selectBooleanCheckbox value="#{ManagedIngresoAlmacen.ingresosAlmacenDetalle.aplicaFechaVencimiento}"
                                                 rendered="#{!ManagedIngresoAlmacen.ingresosAlmacenDetalle.materiales.grupos.gruposFechaVencimiento.aplicaFechaVencimiento}">
                            <a4j:support event="onclick" reRender="panelValores,dataIngresoAlmacenDetalleEstado"/>
                        </h:selectBooleanCheckbox>

                        <h:outputText value="" rendered="#{ManagedIngresoAlmacen.ingresosAlmacenDetalle.materiales.grupos.gruposFechaVencimiento.aplicaFechaVencimiento}"/>
                        <h:outputText value="" rendered="#{ManagedIngresoAlmacen.ingresosAlmacenDetalle.materiales.grupos.gruposFechaVencimiento.aplicaFechaVencimiento}"/>
                        
                        <h:outputText value="Cantidad Parcial:" styleClass="outputTextBold"  />
                        <h:inputText value="#{ManagedIngresoAlmacen.ingresosAlmacenDetalleEstado.cantidadParcial}" styleClass="inputText" size="10" onkeypress="valNumPositivo(event);" id="cantidadParcial" >
                        </h:inputText>
                        
                        <h:outputText value="Tara:" styleClass="outputTextBold" />
                        <h:inputText value="#{ManagedIngresoAlmacen.ingresosAlmacenDetalleEstado.tara}" styleClass="inputText" size="10" onkeypress="valNum(event)" > <%-- onkeypress="valNum(event);" --%>
                        </h:inputText>

                        <h:outputText value="Unidad de Empaque:" styleClass="outputTextBold" />
                        <h:selectOneMenu value="#{ManagedIngresoAlmacen.ingresosAlmacenDetalleEstado.empaqueSecundarioExterno.codEmpaqueSecundarioExterno}" styleClass="inputText" >
                            <f:selectItems value="#{ManagedIngresoAlmacen.empaqueSecundarioExternoList}"  />
                        </h:selectOneMenu>
                    </h:panelGrid>
                    <h:panelGrid columns="8" styleClass="tablaGestiones"
                                 rendered="#{ManagedAccesoSistema.almacenesGlobal.aplicaGestionUbicaciones}">
                        <f:facet name="header">
                            <h:outputText value="Gestión de ubicaciones" styleClass="outputText2"/>
                        </f:facet>
                        <h:outputText value="Ambiente:" styleClass="outputTextBold" />
                        <h:selectOneMenu value="#{ManagedIngresoAlmacen.ingresosAlmacenDetalleEstado.estanteAlmacen.ambienteAlmacen.codAmbiente}"
                                         styleClass="inputText">
                            <f:selectItem itemValue="0" itemLabel="-NINGUNO-" />
                            <f:selectItems value="#{ManagedIngresoAlmacen.ambienteAlmacenList}"  />
                            <a4j:support event="onchange" reRender="estante" action="#{ManagedIngresoAlmacen.ambienteAlmacen_change}" />
                        </h:selectOneMenu>
                        <h:outputText value="Pasillo:" styleClass="outputTextBold" />
                        <h:selectOneMenu value="#{ManagedIngresoAlmacen.ingresosAlmacenDetalleEstado.estanteAlmacen.codEstante}"
                                         styleClass="inputText" id="estante">
                            <f:selectItem itemValue="0" itemLabel="-NINGUNO-" />
                            <f:selectItems value="#{ManagedIngresoAlmacen.estanteAlmacenList}"  />
                        </h:selectOneMenu>

                        <h:outputText value="Estante:" styleClass="outputTextBold" />
                        <h:inputText value="#{ManagedIngresoAlmacen.ingresosAlmacenDetalleEstado.fila}" styleClass="inputText" size="10"  >
                        </h:inputText>
                        
                        <h:outputText value="Ubicacion:" styleClass="outputTextBold" />
                        <h:inputText value="#{ManagedIngresoAlmacen.ingresosAlmacenDetalleEstado.columna}" styleClass="inputText" size="10"  >
                        </h:inputText>
                    </h:panelGrid>
                        <h:selectBooleanCheckbox id="seleccionar_todo"  onclick="seleccionarTodo('detalleItem:form3:dataIngresoAlmacenDetalleEstado');"/>
                        <a4j:commandButton action="#{ManagedIngresoAlmacen.repetirValoresIngresoAlmacenDetalleEstado_action}"
                                           id="buttonRepetir"
                                           oncomplete="if(#{ManagedIngresoAlmacen.mensaje!=''}){alert('#{ManagedIngresoAlmacen.mensaje}')}" 
                                           reRender="dataIngresoAlmacenDetalleEstado,contenidoIngresoAlmacenDetalle,panelValores" 
                                           value="Repetir Valores" styleClass="btn" timeout="7200" />
                </rich:panel>
                <br/>
                <rich:dataTable value="#{ManagedIngresoAlmacen.ingresosAlmacenDetalle.ingresosAlmacenDetalleEstadoList}" var="data"
                        id="dataIngresoAlmacenDetalleEstado"
                        onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                        onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                        headerClass="headerClassACliente"
                        width="100%" >

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
                            <f:attribute name="disable" value="#{(empty param['detalleItem:form3:buttonGuardarDetalle'])}"/>
                        </h:inputText>
                        <h:message for="cantidadParcial" styleClass="message"/>
                    </h:column>
                    <h:column>
                        <f:facet name="header">
                            <h:outputText value="Tara"  />
                        </f:facet>
                        <h:inputText value="#{data.tara}" styleClass="inputText" size="5"  onkeyup="hallaCantidadIngresoFisico()" onkeypress="valNum(event)" />
                    </h:column>
                    <h:column>
                        <f:facet name="header">
                            <h:outputText value="Lote Proveedor"  />
                        </f:facet>
                        <h:inputText value="#{data.loteMaterialProveedor}" styleClass="inputText">
                           <a4j:support event="onblur" action="#{ManagedIngresoAlmacen.loteMaterialProveedorSeleccionadoOnBlur}"
                                        reRender="dataIngresoAlmacenDetalleEstado">
                               <f:setPropertyActionListener value="#{data}" target="#{ManagedIngresoAlmacen.ingresosAlmacenDetalleEstadoSeleccionado}"/>
                           </a4j:support>
                       </h:inputText>
                    </h:column>

                     <h:column rendered="#{ManagedIngresoAlmacen.ingresosAlmacenDetalle.materiales.grupos.gruposFechaVencimiento.aplicaFechaVencimiento
                                                    || ManagedIngresoAlmacen.ingresosAlmacenDetalle.aplicaFechaVencimiento}">
                        <f:facet name="header">
                            <h:outputText value="Fecha<br/>Vencimiento" escape="false" />
                        </f:facet>
                        <h:inputText value="#{data.fechaVencimientoFormatoMMYY}" 
                                     rendered="#{!data.loteProveedorEncontradoIngresoAnterior}" 
                                     id="fechaVencimientoDetalle"
                                     styleClass="inputText" size="8" >
                            <f:validator validatorId="validatorVencimientoMMYYYY"/>
                            <f:attribute name="disable" value="#{(empty param['detalleItem:form3:buttonGuardarDetalle'])}"/>
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
                           action="#{ManagedIngresoAlmacen.guardarDetalleItem_action}"
                           oncomplete="if('#{facesContext.maximumSeverity}'.length==0){if(#{ManagedIngresoAlmacen.registradoCorrectamente})
                                        {Richfaces.hideModalPanel('panelDetalleItem');}else{alert('#{ManagedIngresoAlmacen.mensaje}');}}"
                           reRender="dataIngresoAlmacenDetalleEstado,dataIngresoAlmacenDetalle"
                           timeout="7200"/>
           <a4j:commandButton styleClass="btn" value="Limpiar Detalle"
                            oncomplete="javascript:Richfaces.hideModalPanel('panelDetalleItem')"
                            action="#{ManagedIngresoAlmacen.cancelarDetalleItem_action}"
                            reRender="dataIngresoAlmacenDetalle"
                            timeout="7200"/>

        </center>

    </a4j:form>
</rich:modalPanel>