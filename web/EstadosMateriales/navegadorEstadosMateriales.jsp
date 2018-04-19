
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
                function valText()

                {
                    event.returnValue = false;
                 }
                 function asignColor()
                 {


                   var elements=document.getElementById('form1:dataEstadosMaterial');
                   var rowsElement=elements.rows;
                   for(var i=1;i<rowsElement.length;i++){
                    var cellsElement=rowsElement[i].cells;
                    var cel=cellsElement[0];
                    if(cel.getElementsByTagName('input')[0].type=='checkbox'){
                          if(cel.getElementsByTagName('input')[0].checked){
                           var valor=cellsElement[1].getElementsByTagName('input')[0];
                           //alert(valor.innerHTML);
                           var color=document.getElementById("formEditar:prueba2");
                           color.value=valor.value;
                           color.style.color=valor.value;
                           color.style.backgroundColor=valor.value;
                         }

                     }

                   }
                   
                }



            </script>
          
        </head>
        <body >
          <a4j:form id="form1">
                <div align="center">
                    <h:outputText value="#{ManagedGestEstadosMaterial.cargarListadoMateriales}"/>
                    <h:outputText styleClass="outputTextTitulo"  value="Estados de Material" />
                    <br><br>
                        <h:selectOneMenu value="#{ManagedGestEstadosMaterial.codEstadoRegistro}">
                            <f:selectItem itemValue="1" itemLabel="Activo"/>
                            <f:selectItem itemValue="2" itemLabel="No Activo"/>
                            <f:selectItem itemValue="3" itemLabel="Todos"/>
                            <a4j:support event="onchange" action="#{ManagedGestEstadosMaterial.onChangeEstadoReferencial}" reRender="dataEstadosMaterial"/>
                        </h:selectOneMenu>

                       <rich:dataTable value="#{ManagedGestEstadosMaterial.listaEstadosMaterial}"
                                    var="data"
                                    id="dataEstadosMaterial"
                                    onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                    onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                    headerClass="headerClassACliente" >
                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value=""  />
                            </f:facet>
                            <h:selectBooleanCheckbox  value="#{data.checked}"  />
                        </rich:column>
                       <%-- <rich:column style="background-color:#{data.codColorEstadoMaterial}">
                            <f:facet name="header">
                                <h:outputText value="Color"  />
                            </f:facet>
                            <h:outputText  value="#{data.codColorEstadoMaterial}" style="color:#{data.codColorEstadoMaterial}" />
                        </rich:column>--%>
                        <rich:column style="background-color:#{data.codColorEstadoMaterial}">
                            <f:facet name="header">
                                <h:outputText value="Color"  />
                            </f:facet>
                            <h:inputHidden value="#{data.codColorEstadoMaterial}"/>
                        </rich:column>
                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value="Nombre Estado"  />
                            </f:facet>
                            <h:outputText value="#{data.nombreEstadoMaterial}">
                            </h:outputText>
                        </rich:column>
                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value="Observacion"  />
                            </f:facet>
                            <h:outputText value="#{data.obsEstadoMaterial}"  />
                        </rich:column>
                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value="Estado Registro"  />
                            </f:facet>
                            <h:outputText value="#{data.estadoReferencial.nombreEstadoRegistro}"  />
                        </rich:column>

                    </rich:dataTable>






                    <br>
                        <a4j:commandButton value="Registrar" styleClass="btn" action="#{ManagedGestEstadosMaterial.nuevoEstadoMaterial_Action}" oncomplete="Richfaces.showModalPanel('PanelRegistrarEstadosMaterial')" reRender="contenidoRegistrarEstadosMateriales" />
                        <a4j:commandButton value="Editar" styleClass="btn" onclick="if(editarItem('form1:dataEstadosMaterial')==false){return false;};asignColor()" action="#{ManagedGestEstadosMaterial.editarEstadosMaterial_action}" oncomplete="Richfaces.showModalPanel('PanelEditarEstadoMaterial')" reRender="contenidoEditarEstadoMaterial" />
                        <a4j:commandButton value="Eliminar" styleClass="btn" onclick="if(confirm('Esta seguro de eliminar el registro?')){if(editarItem('form1:dataEstadosMaterial')==false){return false;}}else{return false;}"  action="#{ManagedGestEstadosMaterial.eliminarEstadoMaterial}" oncomplete="if(#{ManagedGestEstadosMaterial.mensaje!=''}){alert('#{ManagedGestEstadosMaterial.mensaje}');}else{alert('Se elimino el estado de material');}" reRender="dataEstadosMaterial"/>
                      

                </div>



            </a4j:form>
             <rich:modalPanel id="PanelRegistrarEstadosMaterial" minHeight="200"  minWidth="515"
                                     height="200" width="515"
                                     zindex="200"
                                     headerClass="headerClassACliente"
                                     resizeable="false" style="overflow :auto" >
                        <f:facet name="header">
                            <h:outputText value="Registro de Estados de Material"/>
                        </f:facet>
                        <a4j:form>
                        <h:panelGroup id="contenidoRegistrarEstadosMateriales">
                            <h:panelGrid columns="4">

                                <h:outputText value="Codigo" styleClass="outputText1" />
                                <h:inputText value="#{ManagedGestEstadosMaterial.nuevoEstadoMaterial.codEstadoMaterial}" disabled="true" styleClass="outputText1"/>

                                <h:outputText value="Nombre:" styleClass="outputText1" />
                                <h:inputText value="#{ManagedGestEstadosMaterial.nuevoEstadoMaterial.nombreEstadoMaterial}" styleClass="inputText1"/>

                                <h:outputText value="Estado de Registro :" styleClass="outputText1" />
                                <h:inputText value="Activo" styleClass="inputText1" disabled="true" />

                                 <h:outputText value="Observaciones:" styleClass="outputText1" />
                                 <h:inputTextarea value="#{ManagedGestEstadosMaterial.nuevoEstadoMaterial.obsEstadoMaterial}" rows="3"/>
                                 


                                
                                
                            </h:panelGrid>
                         
                                
                        </h:panelGroup>
                        
                        <script type="text/javascript" src="jscolor/jscolor.js" ></script>
                        <h:outputText value="Color:" styleClass="outputText1" />
                        <h:inputText id="prueba" value="#{ManagedGestEstadosMaterial.nuevoEstadoMaterial.codColorEstadoMaterial}" styleClass="color" onkeyup="valText()" onkeydown="valText()" onkeypress="valText()" />
                          <div align="center">
                                    <a4j:commandButton styleClass="btn" value="Registrar"  action="#{ManagedGestEstadosMaterial.registrarEstadoMaterial}"
                                    oncomplete="javascript:Richfaces.hideModalPanel('PanelRegistrarEstadosMaterial')"
                                     reRender="dataEstadosMaterial"/>
                                <input type="button" value="Cancelar" onclick="javascript:Richfaces.hideModalPanel('PanelRegistrarEstadosMaterial')" class="btn" />
                                </div>
                        </a4j:form>
                         
            </rich:modalPanel>

           <rich:modalPanel id="PanelEditarEstadoMaterial" minHeight="200"  minWidth="505"
                                     height="200" width="505"
                                     zindex="200"
                                     headerClass="headerClassACliente"
                                     resizeable="false" style="overflow :auto" >
                        <f:facet name="header">
                            <h:outputText value="Edición de Estado de Material"/>
                        </f:facet>
                        <a4j:form id="formEditar">
                        <h:panelGroup id="contenidoEditarEstadoMaterial">
                            <h:panelGrid columns="4">

                               <h:outputText value="Codigo" styleClass="outputText1" />
                               <h:inputText value="#{ManagedGestEstadosMaterial.editarEstadoMaterial.codEstadoMaterial}" disabled="true" styleClass="outputText1"/>

                                <h:outputText value="Nombre:" styleClass="outputText1" />
                                <h:inputText value="#{ManagedGestEstadosMaterial.editarEstadoMaterial.nombreEstadoMaterial}" styleClass="inputText1"/>

                                <h:outputText value="Estado de Registro :" styleClass="outputText1" />
                                <h:selectOneMenu value="#{ManagedGestEstadosMaterial.editarEstadoMaterial.estadoReferencial.codEstadoRegistro}">
                                    <f:selectItem itemValue="1" itemLabel="Activo"/>
                                    <f:selectItem itemValue="2" itemLabel="No Activo"/>
                                </h:selectOneMenu>

                                 <h:outputText value="Observaciones:" styleClass="outputText1" />
                                 <h:inputTextarea value="#{ManagedGestEstadosMaterial.editarEstadoMaterial.obsEstadoMaterial}" rows="3"/>

                            </h:panelGrid>

                            </h:panelGroup>
                         <h:outputText value="Color:" styleClass="outputText1" />
                            <h:inputText id="prueba2" value="#{ManagedGestEstadosMaterial.editarEstadoMaterial.codColorEstadoMaterial}" styleClass="color" onkeyup="valText()" onkeydown="valText()" onkeypress="valText()"/>

                                <div align="center">
                                <a4j:commandButton styleClass="btn" value="Guardar" onclick="if(confirm('Esta seguro de editar estado de material?')==false){return false;}"
                                action="#{ManagedGestEstadosMaterial.guargarEditarEstadoMaterial_Action}" oncomplete="if(#{ManagedGestEstadosMaterial.mensaje!=''}){alert('#{ManagedGestEstadosMaterial.mensaje}');}else{javascript:Richfaces.hideModalPanel('PanelEditarEstadoMaterial');}" reRender="dataEstadosMaterial" />
                                <input type="button" value="Cancelar" onclick="javascript:Richfaces.hideModalPanel('PanelEditarEstadoMaterial')" class="btn" />

                                </div>
                        </a4j:form>
            </rich:modalPanel>

             
                 
        </body>
    </html>

</f:view>

