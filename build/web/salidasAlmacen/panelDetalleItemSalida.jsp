<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib prefix="a4j" uri="http://richfaces.org/a4j"%>
<%@taglib prefix="rich" uri="http://richfaces.org/rich"%>

<rich:modalPanel id="panelSalidaAlmacenDetalleEstado" minHeight="400"  minWidth="1020"
                    height="400" width="1020"
                    zindex="200"
                    headerClass="headerClassACliente"
                    resizeable="false" style="overflow :auto" >
       <f:facet name="header">
           <h:outputText value="<center>Detalle Item</center>" escape="false"/>
       </f:facet>
       <a4j:form id="form2">
       <h:panelGroup id="contenidoSalidaAlmacenDetalleEstado">
           <rich:panel headerClass="headerClassACliente">
               <center>
                   <h:panelGrid columns="4" >
                       <h:outputText value="Cantidad de Salida:" styleClass="outputText2" style="font-weight:bold" />
                       <h:outputText value="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalle.cantidadSalidaAlmacen}" styleClass="outputText2" id="cantTotalSalidaFisica" />
                       <h:outputText value="Item:" styleClass="outputText2" style="font-weight:bold"/>
                       <h:outputText value="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalle.materiales.nombreMaterial}" styleClass="outputText2" />
                   </h:panelGrid>
                   <h:messages styleClass="message" tooltip="true"/>
               </center>
           </rich:panel>
           <center>
               <table class="outputText1"><tr><td>Devoluciones</td><td style="background-color:#E0FFD6;border-color:black;border-style:solid;border-width:thin">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr></table>
           </center>
           <div style="overflow:auto;height:250px;text-align:center;border:1px solid #ccc">
               <rich:dataTable value="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalle.salidasAlmacenDetalleIngresoList}" var="data"
                       id="dataSalidaAlmacenDetalleIngreso"
                       onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                       onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                       headerClass="headerClassACliente cabeceraDetalle">
                   <f:facet name="header">
                       <rich:columnGroup>
                           <rich:column rowspan="2">
                               <h:outputText value="Fecha Venc."/>
                           </rich:column>
                           <rich:column rowspan="2">
                               <h:outputText value="Estado Material"/>
                           </rich:column>
                           <rich:column rowspan="2">
                               <h:outputText value="Nro. Ingreso"/>
                           </rich:column>
                           <rich:column rowspan="2">
                               <h:outputText value="Sección"/>
                           </rich:column>
                           <rich:column rowspan="2">
                               <h:outputText value="Etiqueta"/>
                           </rich:column>
                           <rich:column colspan="4" rendered="#{ManagedAccesoSistema.almacenesGlobal.aplicaGestionUbicaciones}">
                               <h:outputText value="Ubicación"/>
                           </rich:column>
                           <rich:column rowspan="2">
                               <h:outputText value="Empaque Secundario"/>
                           </rich:column>
                           <rich:column rowspan="2">
                               <h:outputText value="Lote Proveedor"/>
                           </rich:column>
                           <rich:column rowspan="2">
                               <h:outputText value="Cantidad Fisica"/>
                           </rich:column>
                           <rich:column rowspan="2">
                               <h:outputText value="Cantidad Disponible"/>
                           </rich:column>
                           <rich:column rowspan="2">
                               <h:outputText value="Cantidad a Sacar"/>
                           </rich:column>
                           <rich:column breakBefore="true" rendered="#{ManagedAccesoSistema.almacenesGlobal.aplicaGestionUbicaciones}">
                               <h:outputText value="Ambiente"/>
                           </rich:column>
                           <rich:column rendered="#{ManagedAccesoSistema.almacenesGlobal.aplicaGestionUbicaciones}">
                               <h:outputText value="Pasillo"/>
                           </rich:column>
                           <rich:column rendered="#{ManagedAccesoSistema.almacenesGlobal.aplicaGestionUbicaciones}">
                               <h:outputText value="Estante"/>
                           </rich:column>
                           <rich:column rendered="#{ManagedAccesoSistema.almacenesGlobal.aplicaGestionUbicaciones}">
                               <h:outputText value="Ubicación"/>
                           </rich:column>
                       </rich:columnGroup>
                   </f:facet>
                   <rich:column style="background-color:'#{data.colorFila}'">
                       <h:outputText  value="#{data.ingresosAlmacenDetalleEstado.fechaVencimiento}">
                           <f:convertDateTime pattern="MM/yyyy" />
                       </h:outputText>
                   </rich:column>
                   <rich:column style="background-color:'#{data.colorFila}'">
                       <h:outputText  value="#{data.ingresosAlmacenDetalleEstado.estadosMaterial.nombreEstadoMaterial}"/>
                   </rich:column>
                   <rich:column style="background-color:'#{data.colorFila}'">
                       <h:outputText  value="#{data.ingresosAlmacenDetalleEstado.ingresosAlmacen.nroIngresoAlmacen}"/>
                   </rich:column>
                   <rich:column style="background-color:'#{data.colorFila}'">
                       <h:outputText  value="#{data.ingresosAlmacenDetalleEstado.ingresosAlmacenDetalle.secciones.nombreSeccion}"/>
                   </rich:column>
                   <rich:column style="background-color:'#{data.colorFila}'">
                       <h:outputText  value="#{data.ingresosAlmacenDetalleEstado.etiqueta}"/>
                   </rich:column>
                   <rich:column style="background-color:'#{data.colorFila}'"
                                rendered="#{ManagedAccesoSistema.almacenesGlobal.aplicaGestionUbicaciones}">
                       <h:outputText  value="#{data.ingresosAlmacenDetalleEstado.estanteAlmacen.ambienteAlmacen.nombreAmbiente}"/>
                   </rich:column>
                   <rich:column style="background-color:'#{data.colorFila}'"
                                rendered="#{ManagedAccesoSistema.almacenesGlobal.aplicaGestionUbicaciones}">
                       <h:outputText  value="#{data.ingresosAlmacenDetalleEstado.estanteAlmacen.nombreEstante}"/>
                   </rich:column>
                   <rich:column style="background-color:'#{data.colorFila}'"
                                rendered="#{ManagedAccesoSistema.almacenesGlobal.aplicaGestionUbicaciones}">
                       <h:outputText  value="#{data.ingresosAlmacenDetalleEstado.fila}"/>
                   </rich:column>
                   <rich:column style="background-color:'#{data.colorFila}'"
                                rendered="#{ManagedAccesoSistema.almacenesGlobal.aplicaGestionUbicaciones}">
                       <h:outputText  value="#{data.ingresosAlmacenDetalleEstado.columna}"/>
                   </rich:column>
                   <rich:column style="background-color:'#{data.colorFila}'">
                       <h:outputText  value="#{data.ingresosAlmacenDetalleEstado.empaqueSecundarioExterno.nombreEmpaqueSecundarioExterno}"/>
                   </rich:column>
                   <rich:column style="background-color:'#{data.colorFila}'">
                       <h:outputText  value="#{data.ingresosAlmacenDetalleEstado.loteMaterialProveedor}"/>
                   </rich:column>
                   <rich:column style="background-color:'#{data.colorFila}'" styleClass="tdRight">
                       <h:outputText  value="#{data.cantidad}">
                           <f:convertNumber pattern="#,##0.00" locale="en"/>
                       </h:outputText>
                   </rich:column>
                   <rich:column style="background-color:'#{data.colorFila}'" styleClass="tdRight">
                       <h:outputText value="#{data.cantidadDisponible}">
                           <f:convertNumber pattern="#,##0.00" locale="en"/>
                       </h:outputText>
                   </rich:column>
                   <rich:column style="background-color:'#{data.colorFila}'">
                        <h:inputText styleClass="inputText" id="cantidadSacar"  value="#{data.cantidadSacar}"
                                     converterMessage="Debe registrar un número">
                            <f:validator validatorId="validatorDoubleRange"/>
                            <f:attribute name="minimum" value="0"/>
                            <f:attribute name="maximum" value="#{data.cantidadDisponible}"/>
                            <f:attribute name="disable" value="#{(empty param['pDetalleItemSalida:form2:btnGuardar'])}"/>
                        </h:inputText>
                       <h:message for="cantidadSacar" styleClass="message"/>
                        <h:inputHidden value="0" id="cantSacarAux"  />
                   </rich:column>

               </rich:dataTable>
           </div>

           
           <div align="center">
               <a4j:commandButton styleClass="btn" value="Registrar"
                                  id="btnGuardar"
                                  oncomplete="if('#{facesContext.maximumSeverity}'.length == 0){
                                                if(#{ManagedRegistroSalidaAlmacen.transaccionExitosa}){
                                                    Richfaces.hideModalPanel('panelSalidaAlmacenDetalleEstado');}
                                                }"
                                  action="#{ManagedRegistroSalidaAlmacen.guardarSalidasAlmacenDetalleIngreso_action}"
                                  reRender="contenidoSalidaAlmacenDetalleEstado,dataSalidasAlmacenDetalle"
                                  />
               <input type="button" value="Cancelar" class="btn" onclick="Richfaces.hideModalPanel('panelSalidaAlmacenDetalleEstado')" />
           </div>

       </h:panelGroup>

       </a4j:form>
</rich:modalPanel>