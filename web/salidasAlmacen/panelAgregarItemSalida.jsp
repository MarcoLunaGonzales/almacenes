<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib prefix="a4j" uri="http://richfaces.org/a4j"%>
<%@taglib prefix="rich" uri="http://richfaces.org/rich"%>

<rich:modalPanel id="panelAgregarItem" minHeight="450"  minWidth="850"
                height="480" width="850"
                zindex="200"
                headerClass="headerClassACliente"
                resizeable="false" style="overflow :auto" >

   <f:facet name="header">
       <h:outputText value="<center>Agregar Item</center>" escape="false"/>                            
   </f:facet>

   <a4j:form id="form5">
       <h:panelGroup id="contenidoAgregarItem">
           <div align="center">
               <h:panelGrid columns="4" id="contenidoIngresoAlmacenDetalle" headerClass="headerClassACliente" width="90%" style="border:1px solid #000000;" >
                   <f:facet name="header">
                         <h:outputText value="Buscar Item" />
                   </f:facet>

                   <h:outputText value="Capitulo:" styleClass="tituloCampo1" />
                   <h:selectOneMenu value="#{ManagedRegistroSalidaAlmacen.buscarMaterial.grupos.capitulos.codCapitulo}" styleClass="inputText chosen" >
                       <f:selectItems value="#{ManagedRegistroSalidaAlmacen.capitulosList}"  />
                       <a4j:support event="onchange" action="#{ManagedRegistroSalidaAlmacen.capitulos_change}" reRender="codGrupo" timeout="7200" />
                   </h:selectOneMenu>

                   <h:outputText value="Grupo:" styleClass="tituloCampo1" />
                   <h:selectOneMenu value="#{ManagedRegistroSalidaAlmacen.buscarMaterial.grupos.codGrupo}" styleClass="inputText chosen" id="codGrupo" >
                       <f:selectItems value="#{ManagedRegistroSalidaAlmacen.gruposList}"  />
                   </h:selectOneMenu>

                   <h:outputText value="Item:" styleClass="tituloCampo1" />

                   <h:inputText value="#{ManagedRegistroSalidaAlmacen.buscarMaterial.nombreMaterial}" styleClass="inputText" size="50"  />

                   <a4j:commandButton value="Buscar" action="#{ManagedRegistroSalidaAlmacen.buscarMaterial_action}" styleClass="btn"  reRender="dataBuscarMaterial,detalleSalidaMaterial"  timeout="7200" />

               </h:panelGrid>
                 <br/>
               <div style="overflow:auto;height:160px;width:100%;text-align:center;border: 1px solid #ccc">
               <rich:dataTable value="#{ManagedRegistroSalidaAlmacen.materialesBuscarList}" var="data"
                               id="dataBuscarMaterial" style="width:100%"
                               onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                               onRowMouseOver="this.style.backgroundColor='#DDE3E4';"                                    
                               headerClass="headerClassACliente"
                               binding="#{ManagedRegistroSalidaAlmacen.materialesBuscarDataTable}" >


                   <rich:column style="background-color: #{data.colorFila}">
                       <f:facet name="header">
                           <h:outputText value="Material"  />
                       </f:facet>
                       <a4j:commandLink  action="#{ManagedRegistroSalidaAlmacen.detallarCantidadSalida_action}" styleClass="#{data.colorFila}"  reRender="nombreMaterialAgregar,detalleSalidaMaterial,dataBuscarMaterial" timeout="7200" >
                           <h:outputText  value="#{data.nombreMaterial}" styleClass="outputText2" />
                       </a4j:commandLink>
                   </rich:column>

                   <rich:column style="background-color: #{data.colorFila}">
                       <f:facet name="header">
                           <h:outputText value="Grupo"  />
                       </f:facet>
                       <h:outputText  value="#{data.grupos.nombreGrupo}"/>
                   </rich:column>

                   <rich:column style="background-color: #{data.colorFila}">
                       <f:facet name="header">
                           <h:outputText value="Capitulo"  />
                       </f:facet>
                       <h:outputText value="#{data.grupos.capitulos.nombreCapitulo}"  />
                   </rich:column>

               </rich:dataTable>
               </div>
               <br/>
               <b>
               <h:panelGroup id="nombreMaterialAgregar">
               <h:outputText value="Nombre Material: " styleClass="outputText1" />
               <h:outputText value="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalleAgregar.materiales.nombreMaterial}" styleClass="outputText1" />
               </h:panelGroup>
               </b>
               <br/>


               <rich:panel id="detalleSalidaMaterial" headerClass="headerClassACliente" style="width:100%">
                   <f:facet name="header" >
                       <h:outputText value="Detalle de Salida de Material" />
                   </f:facet>
                   <table cellpading="0" cellspacing="0" class="tablaExistencia">
                       <tr>
                           <td class="header">Estado Material</td>
                           <a4j:repeat value="#{ManagedRegistroSalidaAlmacen.estadosMaterialExistencia}"  var="estado">
                               <h:outputText value="<td class='center'>#{estado.nombreEstadoMaterial}</td>" escape="false"/>
                           </a4j:repeat>
                       </tr>
                       <tr>
                           <td class="header">Existencia</td>
                           <a4j:repeat value="#{ManagedRegistroSalidaAlmacen.estadosMaterialExistencia}"  var="estado">
                               <h:outputText value="<td class='right #{estado.estadoSalidaPermitido?'existenciaValida':''}'>" escape="false"/>
                                   <h:outputText value="#{estado.cantidadExistente}">
                                       <f:convertNumber pattern="0.00" locale="en"/>
                                   </h:outputText>
                               <h:outputText value="</td>" escape="false"/>
                           </a4j:repeat>
                       </tr>
                   </table>
                   <h:panelGrid columns="7"  style="width:100%" >
                       <h:outputText value="Cantidad Disponible: " styleClass="outputText1" />
                       <h:panelGroup>
                           <h:outputText value="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalleAgregar.cantidadDisponible}"  id="cantidadDisponible" styleClass="outputText1">
                               <f:convertNumber pattern="###.00" locale="en" />
                           </h:outputText>
                           <h:outputText value="[" styleClass="outputText1" />
                           <h:outputText value="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalleAgregar.unidadesMedida.abreviatura}" styleClass="outputText1" />
                           <h:outputText value="]" styleClass="outputText1" />
                       </h:panelGroup>

                       <h:outputText value="Cantidad Salida: " styleClass="outputText1" />
                       <h:panelGroup>
                               <h:inputText value="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalleAgregar.cantidadSalidaAlmacen}" 
                                            styleClass="inputText" id="cantidadSalidaAlmacen"
                                            converterMessage="Debe ingresar un numero valido"
                                            onkeyup="validaCantidadSalidaAlmacenDetalle(this)" >
                                   <f:validator validatorId="validatorDoubleRange"/>
                                   <f:attribute name="minimum" value="0.01"/>
                                   <f:attribute name="maximum" value="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalleAgregar.cantidadDisponible}"/>
                                   <f:attribute name="disable" value="#{(empty param['pAgregarItem:form5:btnGuardar'])}"/>
                               </h:inputText>
                               <h:message for="cantidadSalidaAlmacen" styleClass="message"/>
                       </h:panelGroup>
                       <h:outputText value="[#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalleAgregar.equivalencias.unidadesMedida1.abreviatura}]" styleClass="outputText2"/>

                       <h:outputText value="Equivalencia: " styleClass="outputText1" />
                       <h:panelGroup>
                           <h:inputHidden value="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalleAgregar.equivalencias.valorEquivalencia}" id="valorEquivalencia" />
                           <h:inputText value="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalleAgregar.cantidadSalidaAlmacenEquivalente}" styleClass="inputText" id="cantidadEquivalencia" onkeypress="valNum(event)" />
                           <h:outputText value="[" styleClass="outputText1" />
                           <h:outputText value="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalleAgregar.equivalencias.unidadesMedida2.abreviatura}" styleClass="outputText1" />
                           <h:outputText value="]" styleClass="outputText1" />
                       </h:panelGroup>
                   </h:panelGrid>    
                   <center>
                       <a4j:commandButton styleClass="btn" value="Guardar" id="btnGuardar"
                                          oncomplete="if('#{facesContext.maximumSeverity}'.length == 0){if(#{ManagedRegistroSalidaAlmacen.mensaje!=''}){alert('#{ManagedRegistroSalidaAlmacen.mensaje}')}else{javascript:Richfaces.hideModalPanel('panelAgregarItem')}}"
                                           action="#{ManagedRegistroSalidaAlmacen.guardarAgregarSalidaAlmacenDetalle_action}"
                                           reRender="detalleSalidaMaterial,dataSalidasAlmacenDetalle"  />

                       <input type="button" value="Cancelar" class="btn" onclick="javascript:Richfaces.hideModalPanel('panelAgregarItem')" />
                   </center>

               </rich:panel>
           </div>
       </h:panelGroup>

   </a4j:form>
</rich:modalPanel>