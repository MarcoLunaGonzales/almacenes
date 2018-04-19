<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib prefix="a4j" uri="http://richfaces.org/a4j"%>
<%@taglib prefix="rich" uri="http://richfaces.org/rich"%>
<rich:modalPanel id="panelEditarEstadoEtiquetaPorLoteProveedor" 
                         minHeight="400"  
                         minWidth="900"
                         height="400" 
                         width="900"
                         zindex="200"
                         headerClass="headerClassACliente"
                         resizeable="false" 
                         style="overflow :auto" >
            <f:facet name="header">
                <h:outputText value="Cambiar Estado de Materiales por Lote de Proveedor"/>
            </f:facet>
            <a4j:form id="form10">
                <h:panelGroup id="contenidoEditarEstadoMaterialEtiquetaPorLoteProveedor">
                    <h:panelGrid columns="6">
                        <h:outputText value="Fecha Ingreso" styleClass="outputText1" />
                        <h:outputText value="::" styleClass="outputText1" />
                        <h:outputText value="#{ManagedEstadosMaterial.ingresosAlmacen.fechaIngresoAlmacen}" styleClass="outputText1" />

                        <h:outputText value="Nro. Ingreso" styleClass="outputText1" />
                        <h:outputText value="::" styleClass="outputText1" />
                        <h:outputText value="#{ManagedEstadosMaterial.ingresosAlmacen.nroIngresoAlmacen}" styleClass="outputText1" />


                        <h:outputText value="Estado Ingreso" styleClass="outputText1" />
                        <h:outputText value="::" styleClass="outputText1" />
                        <h:outputText value="#{ManagedEstadosMaterial.ingresosAlmacen.estadosIngresoAlmacen.nombreEstadoIngresoAlmacen}" styleClass="outputText1" />


                        <h:outputText value="Tipo Ingreso Almacen" styleClass="outputText1" />
                        <h:outputText value="::" styleClass="outputText1" />
                        <h:outputText value="#{ManagedEstadosMaterial.ingresosAlmacen.tiposIngresoAlmacen.nombreTipoIngresoAlmacen}" styleClass="outputText1" />


                        <h:outputText value="Proveedor" styleClass="outputText1" />
                        <h:outputText value="::" styleClass="outputText1" />
                        <h:outputText value="#{ManagedEstadosMaterial.ingresosAlmacen.proveedores.nombreProveedor}" styleClass="outputText1" />



                    </h:panelGrid>
                    <h:panelGrid columns="6" id="panelDatos">
                        <h:outputText value="Fecha Vencimiento" styleClass="outputText1" rendered="#{ManagedEstadosMaterial.ingresosAlmacenDetalleEstado.ingresosAlmacenDetalleEstadoFechaVencimiento==null}"/>
                        <h:outputText value="Nueva Fecha de Vencimiento" styleClass="outputText1" rendered="#{ManagedEstadosMaterial.ingresosAlmacenDetalleEstado.ingresosAlmacenDetalleEstadoFechaVencimiento!=null}"/>
                        <h:outputText value="::" styleClass="outputText1" />
                        <h:outputText value="#{ManagedEstadosMaterial.ingresosAlmacenDetalleEstado.fechaVencimiento}" 
                                      styleClass="outputText1" 
                                      id="outfv">
                            <f:convertDateTime pattern="dd/MM/yyyy" />
                        </h:outputText>
                        <h:outputText value="Valoración % Control Calidad" 
                                      styleClass="outputText2" 
                                      style="visibility:hidden;"/>
                        <h:outputText value="::" styleClass="outputText1" style="visibility:hidden;"/>
                        <h:inputText  value="#{ManagedEstadosMaterial.valoracionCCPorcentual}" 
                                      styleClass="outputText2" 
                                      id="valoracionCC"
                                      style="visibility:hidden;"
                                      />
                        <h:outputText value="Obs Control de Calidad" styleClass="outputText1" />
                        <h:outputText value="::" styleClass="outputText1" />
                        <h:selectOneRadio value="#{ManagedEstadosMaterial.ingresosAlmacenDetalleEstado.obsControlCalidad}" styleClass="outputText2">
                            <f:selectItem  itemValue="1" itemLabel="Si"  />
                            <f:selectItem  itemValue="0" itemLabel="No" />
                        </h:selectOneRadio>

                        <h:outputText value="Observaciones" styleClass="outputText2" />
                        <h:outputText value="::" styleClass="outputText1" />
                        <h:inputTextarea  value="#{ManagedEstadosMaterial.ingresosAlmacenDetalleEstado.observaciones}" 
                                          styleClass="outputText2" 
                                          cols="35" 
                                          rows="3" />
                    </h:panelGrid>

                    <h:panelGrid columns="3">
                        <h:outputText value="Materiales del Ingreso" styleClass="outputText1" />
                        <h:outputText value="::" styleClass="outputText1" />
                        <h:selectOneMenu value="#{ManagedEstadosMaterial.codMaterialSelected}" 
                                         styleClass="inputText" 
                                         style="width:350px;"
                                         >
                            <f:selectItem itemValue="0" itemLabel="- Ninguno -"></f:selectItem>
                            <f:selectItems value="#{ManagedEstadosMaterial.materialesIngresoAlmacen}"  />
                            <a4j:support event="onchange" 
                                         action="#{ManagedEstadosMaterial.onChangeMaterialIngreso}" 
                                         reRender="lotesProveedor,dataIngresosAlmacenDetalleEstado,bottonesAprobacion,valoracionCC ,outfv, infv,panelDatos" 
                                         timeout="7200" />
                        </h:selectOneMenu>
                        <h:outputText value="Lotes de Proveedor" styleClass="outputText1" />
                        <h:outputText value="::" styleClass="outputText1" />
                        <h:selectOneMenu value="#{ManagedEstadosMaterial.loteProveedorSelected}" 
                                         styleClass="inputText" 
                                         id="lotesProveedor"
                                         style="width:350px;">
                            <f:selectItem itemValue="0" itemLabel="- Ninguno -"></f:selectItem>
                            <f:selectItems value="#{ManagedEstadosMaterial.lotesMaterial}"  />
                            <a4j:support event="onchange" 
                                         action="#{ManagedEstadosMaterial.onChangeLoteProveedor}" 
                                         reRender="dataIngresosAlmacenDetalleEstado,bottonesAprobacion,valoracionCC, outfv, infv,panelDatos" 
                                         timeout="7200" />
                        </h:selectOneMenu>
                    </h:panelGrid>
                    <br/>

                    <rich:dataTable value="#{ManagedEstadosMaterial.ingresosAlmacenDetalleEstadoList}"
                                    var="data"
                                    id="dataIngresosAlmacenDetalleEstado"
                                    onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                    onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                    headerClass="headerClassACliente" >

                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value="Lote"  />
                            </f:facet>
                            <h:outputText value="#{data.loteMaterialProveedor}"  />
                        </rich:column>

                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value="Fecha Ingreso"  />
                            </f:facet>
                            <h:outputText  value="#{data.ingresosAlmacenDetalle.ingresosAlmacen.fechaIngresoAlmacen}"  />
                        </rich:column>

                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value="Nro Ingreso"  />
                            </f:facet>
                            <h:outputText value="#{data.ingresosAlmacenDetalle.ingresosAlmacen.nroIngresoAlmacen}">
                            </h:outputText>
                        </rich:column>
                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value="Item"  />
                            </f:facet>
                            <h:outputText value="#{data.ingresosAlmacenDetalle.materiales.codMaterial}"  />
                        </rich:column>
                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value="Fecha Vencimiento"  />
                            </f:facet>
                            
                            <h:outputText value="#{data.fechaVencimiento}" >
                                <f:convertDateTime pattern="dd/MM/yyyy" />
                            </h:outputText>
                        </rich:column>
                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value="Etiqueta"  />
                            </f:facet>
                            <h:outputText value="#{data.etiqueta}"  />
                        </rich:column>

                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value="Cantidad Parcial"  />
                            </f:facet>
                            <h:outputText value="#{data.cantidadParcial}"  />
                        </rich:column>
                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value="Unid."  />
                            </f:facet>
                            <h:outputText value="#{data.ingresosAlmacenDetalle.unidadesMedida.nombreUnidadMedida}"  />
                        </rich:column>

                        <rich:column style="background-color: #{data.colorFila}">
                            <f:facet name="header">
                                <h:outputText value="Estado Material"  />
                            </f:facet>
                            <h:outputText value="#{data.estadosMaterial.nombreEstadoMaterial}"  />
                        </rich:column>

                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value="Empaque"  />
                            </f:facet>
                            <h:outputText value="#{data.empaqueSecundarioExterno.nombreEmpaqueSecundarioExterno}"  />
                        </rich:column>


                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value="Valoración Porcentual"  />
                            </f:facet>
                            <h:outputText value="#{data.valoracionCCPorcentual}"  />
                        </rich:column>
                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value="observaciones"  />
                            </f:facet>
                            <h:outputText value="#{data.observaciones}"  />
                        </rich:column>

                    </rich:dataTable>
                </h:panelGroup>
                <center>
                    <h:panelGroup id="bottonesAprobacion">
                        <h:outputText value="No Existen items de cuarentena para aprobación<br/>" rendered="#{ManagedEstadosMaterial.cantidadCuarentenaLoteProveedor eq 0}"
                                      styleClass="outputTextBold" style="color:red" escape="false"/>
                        <a4j:commandButton styleClass="btn" value="Aprobar Items en Cuarentena" reRender="contenidoEditarEstadoMaterialEtiquetaPorLoteProveedor,bottonesAprobacion"
                                            disabled="#{ManagedEstadosMaterial.cantidadCuarentenaLoteProveedor eq 0}"
                                            rendered="#{ManagedEstadosMaterial.permisoAprobacionMateriales}"
                                            action="#{ManagedEstadosMaterial.aprobarIngresoAlmacenLoteProveedorAction}"  
                                            oncomplete="if(#{ManagedEstadosMaterial.mensaje eq '1'}){alert('Se registro la aprobación de los items seleccionados');}
                                            else{alert('#{ManagedEstadosMaterial.mensaje}');}"/>

                        <a4j:commandButton styleClass="btn" value="Aprobar items por cuarentena por G.I."  reRender="contenidoEditarEstadoMaterialEtiquetaPorLoteProveedor,bottonesAprobacion"
                                            disabled="#{ManagedEstadosMaterial.cantidadCuarentenaLoteProveedor eq 0}"
                                            rendered="#{ManagedEstadosMaterial.permisoAprobacionMaterialesGI}"
                                            action="#{ManagedEstadosMaterial.aprobarGIIngresoAlmacenLoteProveedorAction}" 
                                            oncomplete="if(#{ManagedEstadosMaterial.mensaje eq '1'}){alert('Se registro la aprobación de los items seleccionados');}
                                            else{alert('#{ManagedEstadosMaterial.mensaje}');}"/>
                        
                        <a4j:commandButton styleClass="btn" value="Rechazar items"  reRender="contenidoEditarEstadoMaterialEtiquetaPorLoteProveedor,bottonesAprobacion"
                                           rendered="#{ManagedEstadosMaterial.permisoAprobacionMaterialesGI || ManagedEstadosMaterial.permisoAprobacionMateriales}"
                                           action="#{ManagedEstadosMaterial.rechazarIngresoAlmacenLoteProveedorAction}" 
                                            oncomplete="if(#{ManagedEstadosMaterial.mensaje eq '1'}){alert('Se registro la aprobación de los items seleccionados');}
                                            else{alert('#{ManagedEstadosMaterial.mensaje}');}"/>

                        <a4j:commandButton value="Cancelar" oncomplete="Richfaces.hideModalPanel('panelEditarEstadoEtiquetaPorLoteProveedor')"
                                           styleClass="btn"/>

                    </h:panelGroup>
                </center>

            </a4j:form>
        </rich:modalPanel>