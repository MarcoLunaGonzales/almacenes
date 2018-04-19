<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib prefix="a4j" uri="http://richfaces.org/a4j"%>
<%@taglib prefix="rich" uri="http://richfaces.org/rich"%>
<rich:modalPanel id="panelEditarEstadoEtiquetaPorIngresoAlmacen" minHeight="400"  minWidth="920"
                height="400" width="920"
                zindex="200" 
                headerClass="headerClassACliente"
                resizeable="false" style="overflow :auto" >
    <f:facet name="header">
        <h:outputText value="<center>Aprobar Material Cuarentena</center>" escape="false"/>
    </f:facet>
    <a4j:form id="form4">
        <h:panelGroup id="contenidoEditarEstadoMaterialEtiquetaPorIngresoAlmacen">
            <rich:panel headerClass="headerClassACliente">
                <f:facet name="header">
                    <h:outputText value="<center>Datos del ingreso<center>" escape="false"/>
                </f:facet>
                <h:panelGrid columns="9">
                    <h:outputText value="Fecha Ingreso" styleClass="outputTextBold" />
                    <h:outputText value="::" styleClass="outputTextBold" />
                    <h:outputText value="#{ManagedEstadosMaterial.ingresosAlmacen.fechaIngresoAlmacen}" styleClass="outputText2">
                        <f:convertDateTime pattern="dd/MM/yyyy HH:mm" locale="en"/>
                    </h:outputText>
                    <h:outputText value="Nro. Ingreso" styleClass="outputTextBold" />
                    <h:outputText value="::" styleClass="outputTextBold" />
                    <h:outputText value="#{ManagedEstadosMaterial.ingresosAlmacen.nroIngresoAlmacen}" styleClass="outputText2" />
                    <h:outputText value="Estado Ingreso" styleClass="outputTextBold" />
                    <h:outputText value="::" styleClass="outputTextBold" />
                    <h:outputText value="#{ManagedEstadosMaterial.ingresosAlmacen.estadosIngresoAlmacen.nombreEstadoIngresoAlmacen}" styleClass="outputText2" />
                    <h:outputText value="Tipo Ingreso Almacen" styleClass="outputTextBold" />
                    <h:outputText value="::" styleClass="outputTextBold" />
                    <h:outputText value="#{ManagedEstadosMaterial.ingresosAlmacen.tiposIngresoAlmacen.nombreTipoIngresoAlmacen}" styleClass="outputText2" />
                    <h:outputText value="Proveedor" styleClass="outputTextBold" />
                    <h:outputText value="::" styleClass="outputTextBold" />
                    <h:outputText value="#{ManagedEstadosMaterial.ingresosAlmacen.proveedores.nombreProveedor}" styleClass="outputText2" />
                    <h:outputText value="Valoración %<br/> Control Calidad" styleClass="outputTextBold" escape="false"/>
                    <h:outputText value="::" styleClass="outputTextBold" />
                    <h:inputText  value="#{ManagedEstadosMaterial.valoracionCCPorcentual}" styleClass="outputText2" />
               </h:panelGrid>
            </rich:panel>
           <br/>
           <center><div style="overflow:auto;text-align:center;height:70px;width:40%" >
                   <rich:dataTable value="#{ManagedEstadosMaterial.ingresosAlmacenDetalleMostrarList}"
                                   var="data"
                                   id="dataIngresosAlmacenDetalleMostrar"
                                   onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                   onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                   headerClass="headerClassACliente" style="overflow:auto;text-align:center;height:50px;" >
                        <rich:column>
                           <f:facet name="header">
                               <h:outputText value="Item"  />
                           </f:facet>
                           <h:outputText  value="#{data.materiales.nombreMaterial}"  />
                        </rich:column>

                       <rich:column>
                           <f:facet name="header">
                               <h:outputText value="Cantidad"  />
                           </f:facet>
                           <h:outputText value="#{data.cantTotalIngresoFisico}">
                           </h:outputText>
                       </rich:column>
                       <rich:column>
                           <f:facet name="header">
                               <h:outputText value="Unid."  />
                           </f:facet>
                           <h:outputText value="#{data.unidadesMedida.nombreUnidadMedida}">
                           </h:outputText>
                       </rich:column>
                   </rich:dataTable>
               </div></center>
           <div align="center">
               <h:outputText value="Seleccionar Todo" styleClass="outputTextBold"/>
               <h:selectBooleanCheckbox id="seleccionar_todo"  onclick="seleccionarTodo();"/>
           </div>
           <br/>

           <rich:dataTable value="#{ManagedEstadosMaterial.ingresosAlmacenDetalleEstadoList}"
                           var="data"
                           id="dataIngresosAlmacenDetalleEstado"
                           onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                           onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                           headerClass="headerClassACliente" >

               <rich:column>
                   <f:facet name="header">
                       <h:outputText value=""  />
                   </f:facet>
                   <h:selectBooleanCheckbox value="#{data.checked}" rendered="#{data.estadosMaterial.codEstadoMaterial eq 1}" >

                   </h:selectBooleanCheckbox>
               </rich:column>

               <rich:column>
                   <f:facet name="header">
                       <h:outputText value="Fecha Ingreso"  />
                   </f:facet>
                   <h:outputText  value="#{data.ingresosAlmacenDetalle.ingresosAlmacen.fechaIngresoAlmacen}">
                       <f:convertDateTime pattern="dd/MM/yyyy" locale="en"/>
                   </h:outputText>
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
                       <h:outputText value="Fecha Vencimiento"  />
                   </f:facet>
                   <h:outputText value="#{data.fechaVencimiento}">
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
                       <h:outputText value="Item"  />
                   </f:facet>
                   <h:outputText value="#{data.ingresosAlmacenDetalle.materiales.nombreMaterial}"  />
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
                       <h:outputText value="Lote"  />
                   </f:facet>
                   <h:outputText value="#{data.loteMaterialProveedor}"  />
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
        <br/>
        <div align="center">
            <a4j:commandButton styleClass="btn" value="Aprobar" reRender="dataIngresosAlmacen"
                                rendered="#{ManagedEstadosMaterial.permisoAprobacionMateriales}"
                                action="#{ManagedEstadosMaterial.aprobarIngresoAlmaceneDetalleEstadoAction}"  
                                onclick="if(validarAlMenosUno('modal4:form4:dataIngresosAlmacenDetalleEstado')==false){return false;}"
                                oncomplete="if(#{ManagedEstadosMaterial.mensaje eq '1'}){alert('Se registro la aprobación de los items seleccionados');Richfaces.hideModalPanel('panelEditarEstadoEtiquetaPorIngresoAlmacen');}
                                else{alert('#{ManagedEstadosMaterial.mensaje}');}"/>
            
            <a4j:commandButton styleClass="btn" value="Aprobar por G.I."  reRender="dataIngresosAlmacen"
                                rendered="#{ManagedEstadosMaterial.permisoAprobacionMaterialesGI}"
                                action="#{ManagedEstadosMaterial.aprobarGIIngresoAlmacenDetalleEstadoAction}" 
                                onclick="if(validarAlMenosUno('modal4:form4:dataIngresosAlmacenDetalleEstado')==false){return false;}"
                                oncomplete="if(#{ManagedEstadosMaterial.mensaje eq '1'}){alert('Se registro la aprobación de los items seleccionados');Richfaces.hideModalPanel('panelEditarEstadoEtiquetaPorIngresoAlmacen');}
                                else{alert('#{ManagedEstadosMaterial.mensaje}');}"/>
            
            <a4j:commandButton styleClass="btn" value="Rechazar" reRender="dataIngresosAlmacen"
                               rendered="#{ManagedEstadosMaterial.permisoAprobacionMateriales || ManagedEstadosMaterial.permisoAprobacionMaterialesGI}"
                               action="#{ManagedEstadosMaterial.rechazarIngresoAlmaceneDetalleEstadoAction}"  
                                onclick="if(validarAlMenosUno('modal4:form4:dataIngresosAlmacenDetalleEstado')==false){return false;}"
                                oncomplete="if(#{ManagedEstadosMaterial.mensaje eq '1'}){alert('Se registro el rechazo de los items seleccionados');Richfaces.hideModalPanel('panelEditarEstadoEtiquetaPorIngresoAlmacen');}
                                else{alert('#{ManagedEstadosMaterial.mensaje}');}"/>
            
            <a4j:commandButton value="Cancelar" oncomplete="Richfaces.hideModalPanel('panelEditarEstadoEtiquetaPorIngresoAlmacen')"
                               styleClass="btn"/>
        </div>


   </a4j:form>
</rich:modalPanel>