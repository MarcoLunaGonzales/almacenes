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

             function vaciar()
             {
                 var var1=document.getElementById('formRegistrar:nombreUnidad');
                 var1.value='';
                 var var2=document.getElementById('formRegistrar:nombreAbrev');
                 var2.value='';
                 var var3=document.getElementById('formRegistrar:obserNuevo');
                 var3.value='';
                 var var4=document.getElementById('formRegistrar:obserNuevo');
                 var4.value='';
                 var var5=document.getElementById('formRegistrar:claveUnidad1');
                 var5.value='0';
                 var var6=document.getElementById('formRegistrar:unidad1');
                 var6.value='4';

             }
            </script>
          
        </head>
        <body >
            <a4j:form id="form1">
                <div align="center">
                    <h:outputText value="#{ManagedUnidadesMedida.cargarListaUnidades}"/>
                    <h:outputText styleClass="outputTextTitulo"  value="Unidades de Medida" />
                    <br><br>
                    
                    <rich:dataTable value="#{ManagedUnidadesMedida.listaUnidades}"
                                    var="data"
                                    id="dataEquivalencias"
                                    onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                    onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                    headerClass="headerClassACliente" >
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value=""  />
                            </f:facet>
                            <h:selectBooleanCheckbox  value="#{data.checked}"  />
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Tipo de Medida"  />
                            </f:facet>
                            <h:outputText  value="#{data.tiposMedida.nombre_tipo_medida}"  />
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Unidad de Medida"  />
                            </f:facet>
                            <h:outputText value="#{data.nombreUnidadMedida}">
                            </h:outputText>
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Descripción"  />
                            </f:facet>
                            <h:outputText value="#{data.obsUnidadMedida}"  />
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Abreviatura"  />
                            </f:facet>
                            <h:outputText value="#{data.abreviatura}"  />
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Unidad Clave"  />
                            </f:facet>
                            <h:outputText value="si" rendered="#{data.unidadClave==1}" />
                            <h:outputText value="no" rendered="#{data.unidadClave==0}" />
                        </h:column>
                          <h:column>
                            <f:facet name="header">
                                <h:outputText value="Estado Registro"  />
                            </f:facet>
                            <h:outputText value="#{data.estadoReferencial.nombreEstadoRegistro}"  />
                        </h:column>

                    </rich:dataTable>
                    


                     <h:panelGrid columns="2"  width="50" id="controles">
                         <h:commandLink  action="#{ManagedUnidadesMedida.atras_action}"   rendered="#{ManagedUnidadesMedida.begin!='1'}"  >
                                <h:graphicImage url="../img/previous.gif"  style="border:0px solid red"   alt="PAGINA ANTERIOR"  />
                            </h:commandLink>
                            <h:commandLink  action="#{ManagedUnidadesMedida.siguiente_action}"  rendered="#{ManagedUnidadesMedida.cantidadfilas>'9'}">
                                <h:graphicImage url="../img/next.gif"  style="border:0px solid red"  alt="PAGINA SIGUIENTE" />
                            </h:commandLink>
                    </h:panelGrid>
                    

                   
                    <br>
                        <a4j:commandButton value="Registrar" styleClass="btn" action="#{ManagedUnidadesMedida.nuevaUnidad_action}" oncomplete="Richfaces.showModalPanel('PanelRegistrarUnidadMedida')" reRender="contenidoRegistrarUnidadMedida" />
                        <a4j:commandButton value="Editar" styleClass="btn" onclick="if(editarItem('form1:dataEquivalencias')==false){return false;}" action="#{ManagedUnidadesMedida.cargarUnidadEditar}" oncomplete="if(#{ManagedUnidadesMedida.cantidadDependencias==0}){Richfaces.showModalPanel('PanelEditarUnidadMedida')}else{alert('#{ManagedUnidadesMedida.mensaje}');}" reRender="contenidoEditarUnidadMedida"/>
                        <a4j:commandButton value="Eliminar" styleClass="btn" onclick="if(confirm('Esta seguro de eliminar la equivalencia?')){if(editarItem('form1:dataEquivalencias')==false){return false;}}else{return false;}"  action="#{ManagedUnidadesMedida.eliminarUnidad_action}"
                        oncomplete="if(#{ManagedUnidadesMedida.mensaje!=''}){alert('#{ManagedUnidadesMedida.mensaje}');}else{alert('se elimino la unidad de medida');}" reRender="dataEquivalencias,controles"/>

                   
                </div>

               
              
            </a4j:form>

             <rich:modalPanel id="PanelRegistrarUnidadMedida" minHeight="200"  minWidth="550"
                                     height="200" width="550"
                                     zindex="200"
                                     headerClass="headerClassACliente"
                                     resizeable="false" style="overflow :auto" >
                        <f:facet name="header">
                            <h:outputText value="Registro de Unidades de Medida"/>
                        </f:facet>
                        <a4j:form id="formRegistrar">
                        <h:panelGroup id="contenidoRegistrarUnidadMedida">
                            <h:panelGrid columns="4">

                                <h:outputText value="Tipo de Medida:" styleClass="outputText1" />
                                <h:selectOneMenu value="#{ManagedUnidadesMedida.nuevaUnidad.tiposMedida.cod_tipo_medida}" styleClass="inputText" id="unidad1">
                                    <f:selectItems value="#{ManagedUnidadesMedida.listaTiposUnidades}"/>
                               </h:selectOneMenu>
                                                         
                                <h:outputText value="Unidad de Medida :" styleClass="outputText1" />
                                <h:inputText value="#{ManagedUnidadesMedida.nuevaUnidad.nombreUnidadMedida}" styleClass="inputText" id="nombreUnidad"  />

                                 <h:outputText value="Estado Registro:" styleClass="outputText1" />
                                 <h:inputText value="Activo" styleClass="inputText" disabled="true" />
                                 <h:outputText value="Abreviatura :" styleClass="outputText1" />
                                 <h:inputText value="#{ManagedUnidadesMedida.nuevaUnidad.abreviatura}" styleClass="inputText"  id="nombreAbrev" />

                                
                                 <h:outputText value="Observaciones:" styleClass="outputText1" />
                                 <h:inputTextarea value="#{ManagedUnidadesMedida.nuevaUnidad.obsUnidadMedida}" rows="3" styleClass="inputText" id="obserNuevo"/>
                                 <h:outputText value="Unidad clave:" styleClass="outputText1" />
                                 <h:selectOneMenu value="#{ManagedUnidadesMedida.nuevaUnidad.unidadClave}" styleClass="inputText" id="claveUnidad1">

                                    <f:selectItems value="#{ManagedUnidadesMedida.opciones}"/>
                                 </h:selectOneMenu>
                                 
                                
                            </h:panelGrid>
                                <div align="center">
                                    <a4j:commandButton styleClass="btn" value="Registrar"  action="#{ManagedUnidadesMedida.registrarUnidad_action}"
                                    oncomplete="if(#{ManagedUnidadesMedida.mensaje!=''}){alert('#{ManagedUnidadesMedida.mensaje}');}else{alert('Se registro la unidad');javascript:Richfaces.hideModalPanel('PanelRegistrarUnidadMedida');}"
                                    reRender="dataEquivalencias,controles"/>
                                <input type="button" value="Limpiar" onclick="vaciar();" class="btn" />
                                <input type="button" value="Cancelar" onclick="javascript:Richfaces.hideModalPanel('PanelRegistrarUnidadMedida')" class="btn" />
                                </div>
                        </h:panelGroup>
                        </a4j:form>
            </rich:modalPanel>

           <rich:modalPanel id="PanelEditarUnidadMedida" minHeight="160"  minWidth="500"
                                     height="160" width="500"
                                     zindex="200"
                                     headerClass="headerClassACliente"
                                     resizeable="false" style="overflow :auto" >
                        <f:facet name="header">
                            <h:outputText value="Modificación de Unidades de Medida"/>
                        </f:facet>
                        <a4j:form id="formEditar">
                        <h:panelGroup id="contenidoEditarUnidadMedida">
                            <h:panelGrid columns="4">

                             <h:outputText value="Tipo de Medida:" styleClass="outputText1" />
                             <h:selectOneMenu value="#{ManagedUnidadesMedida.editarUnidad.codUnidadMedida}" styleClass="inputText" id="unidadeditar1" disabled="true">
                                 <f:selectItems value="#{ManagedUnidadesMedida.listaTiposUnidades}"/>

                                </h:selectOneMenu>                              
                                                        
                                <h:outputText value="Unidad de Medida:" styleClass="outputText1" />
                                <h:inputText value="#{ManagedUnidadesMedida.editarUnidad.nombreUnidadMedida}" styleClass="inputText"  />

                                 <h:outputText value="Estado:" styleClass="outputText1" />
                                 <h:selectOneMenu value="#{ManagedUnidadesMedida.editarUnidad.estadoReferencial.codEstadoRegistro}" styleClass="inputText" id="estado2">

                                    <f:selectItem itemLabel="activo" itemValue="1"/>
                                    <f:selectItem itemLabel="no activo" itemValue="2"/>
                                 </h:selectOneMenu>
                                   <h:outputText value="Observaciones:" styleClass="outputText1" />
                                   <h:inputTextarea value="#{ManagedUnidadesMedida.editarUnidad.obsUnidadMedida}" rows="3" styleClass="inputText"id="obsEditar"/>
                               <h:outputText value="Unidad clave:" styleClass="outputText1" />
                               <h:selectOneMenu value="#{ManagedUnidadesMedida.editarUnidad.unidadClave}" styleClass="inputText" id="claveUnidad1">

                                    <f:selectItems value="#{ManagedUnidadesMedida.opciones}"/>
                                 </h:selectOneMenu>

                              
                            </h:panelGrid>
                                <div align="center">
                                <a4j:commandButton styleClass="btn" value="Guardar" onclick="if(confirm('Esta seguro de editar la Unidad de Medida?')==false){return false;}"
                                action="#{ManagedUnidadesMedida.modificarUnidad_action}" oncomplete="if(#{ManagedUnidadesMedida.mensaje!=''}){alert('#{ManagedUnidadesMedida.mensaje}');}else{javascript:Richfaces.hideModalPanel('PanelEditarUnidadMedida');}" reRender="dataEquivalencias,controles" />
                                <input type="button" value="Cancelar" onclick="javascript:Richfaces.hideModalPanel('PanelEditarUnidadMedida')" class="btn" />
                                </div>
                        </h:panelGroup>
                        </a4j:form>
            </rich:modalPanel>

        </body>
    </html>

</f:view>

