
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
                function getCodigo(cod_maquina,codigo){
                 //  alert(codigo);
                   location='../partes_maquinaria/navegador_partes_maquinaria.jsf?cod_maquina='+cod_maquina+'&codigo='+codigo;
                }
                
                function editarItem(nametable){
                   var count=0;
                   var elements=document.getElementById(nametable);
                   var rowsElement=elements.rows;
                   for(var i=1;i<rowsElement.length;i++){
                    var cellsElement=rowsElement[i].cells;
                    var cel=cellsElement[0];
                    if(cel.getElementsByTagName('input')[0].type=='checkbox'){
                          if(cel.getElementsByTagName('input')[0].checked){
                           count++;
                         }
                         
                     }
                      
                   }
                   if(count==1){
                      return true;
                   } else if(count==0){
                       alert('No escogio ningun registro');
                       return false;
                   }
                   else if(count>1){
                       alert('Solo puede escoger un registro');
                       return false;
                   }                                      
                }


                function asignar(nametable){

                   var count=0;
                   var elements=document.getElementById(nametable);
                   var rowsElement=elements.rows;
                   for(var i=1;i<rowsElement.length;i++){
                    var cellsElement=rowsElement[i].cells;
                    var cel=cellsElement[0];
                    alert('hola');
                    if(cel.getElementsByTagName('input')[0].type=='checkbox'){
                          if(cel.getElementsByTagName('input')[0].checked){
                           count++;
                         }                         
                     }                      
                   }
                    if(count==0){
                       alert('No selecciono ningun Registro');
                       return false;
                   }else{
                       if(confirm('Desea Asignar como Area Raiz')){
                            if(confirm('Esta seguro de Asignar como Area Raiz')){
                                 return true;
                        }else{
                            return false;
                        }
                    }else{
                        return false;
                    }
                   
                   }
                   
                }          
            function eliminar(nametable){
               var count1=0;
               var elements1=document.getElementById(nametable);
               var rowsElement1=elements1.rows;
               //alert(rowsElement1.length);            
               for(var i=1;i<rowsElement1.length;i++){
                    var cellsElement1=rowsElement1[i].cells;
                    var cel1=cellsElement1[0];
                    if(cel1.getElementsByTagName('input').length>0){
                        if(cel1.getElementsByTagName('input')[0].type=='checkbox'){
                          if(cel1.getElementsByTagName('input')[0].checked){
                               count1++;
                           }
                        }
                    }
                    
               }
               //alert(count1);
               if(count1==0){
                    alert('No escogio ningun registro');
                    return false;
               }else{
                
                            var count=0;
                            var elements=document.getElementById(nametable);
                            var rowsElement=elements.rows;
                            
                            for(var i=0;i<rowsElement.length;i++){
                                var cellsElement=rowsElement[i].cells;
                                var cel=cellsElement[0];
                                if(cel.getElementsByTagName('input').length>0){
                                    if(cel.getElementsByTagName('input')[0].type=='checkbox'){
                                        if(cel.getElementsByTagName('input')[0].checked){
                                            count++;
                                        }
                                    }
                                }

                            }
                            if(count==0){
                            //alert('No escogio ningun registro');
                            return false;
                            }
                            //var cantidadeliminar=document.getElementById('form1:cantidadeliminar');
                            //cantidadeliminar.value=count;
                            return true;
                    
                }
           }                

           function validarSeleccion(nametable){
                   var count=0;
                   var elements=document.getElementById(nametable);
                   var rowsElement=elements.rows;
                   for(var i=1;i<rowsElement.length;i++){
                    var cellsElement=rowsElement[i].cells;
                    var cel=cellsElement[0];
                    if(cel.getElementsByTagName('input')[0].type=='checkbox'){
                          if(cel.getElementsByTagName('input')[0].checked){
                           count++;
                         }

                     }

                   }
                   if(count==1){
                      return true;
                   } else if(count==0){
                       alert('No escogio ningun registro');
                       return false;
                   }
                   else if(count>1){
                       alert('Solo puede escoger un registro');
                       return false;
                   }
                }
        function verManteminiento(cod_maquina,codigo){
                   location='../partes_maquinaria/navegador_partes_maquinaria.jsf?cod_maquina='+cod_maquina+'&codigo='+codigo;
        }
       </script>
        </head>
        <body >
            <center>
                
                </br>
                
            </center>
            <a4j:form id="form1" >
                <div align="center">
                    <h:outputText styleClass="outputTextTitulo"  value="Listado de Ambientes de Almacen" />
                    <br><br>
                    <h:outputText value="#{ManagedAmbientesAlmacen.cargarAmbienteAlmacen}"/>
                    <h:outputText value="El almac�n #{ManagedAccesoSistema.almacenesGlobal.nombreAlmacen} no se encuentra configurado para la gesti�n de ubicaciones"
                                  styleClass="outputTextBold" style="color:red;font-size:14px;"
                                  rendered="#{not ManagedAccesoSistema.almacenesGlobal.aplicaGestionUbicaciones}"/>
                    
                    
                    
                        <rich:dataTable value="#{ManagedAmbientesAlmacen.ambienteAlmacenList}" var="data"
                                    id="dataAmbientes"
                                    onRowMouseOut="this.style.backgroundColor='#FFFFFF';" 
                                    onRowMouseOver="this.style.backgroundColor='#DDE3E4';" 
                                    headerClass="headerClassACliente"
                                    binding="#{ManagedAmbientesAlmacen.ambienteAlmacenDataTable}">

                        <h:column>
                            <f:facet name="header">
                                <h:outputText value=""  />                                
                            </f:facet>
                            <h:selectBooleanCheckbox value="#{data.checked}"  />
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Ambiente"  />
                            </f:facet>
                            <h:outputText value="#{data.nombreAmbiente}"/>
                        </h:column>

                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="observacion"  />
                            </f:facet>
                            <h:outputText value="#{data.observacion}"  />
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value=""  />
                            </f:facet>
                            <a4j:commandLink  action="#{ManagedAmbientesAlmacen.verEstantesAmbiente_action}" oncomplete="location='../estanteAlmacen/navegadorEstantes.jsf'"    >
                            <h:graphicImage url="../img/areasdependientes.jpg"   />
                            </a4j:commandLink>
                        </h:column>
                    </rich:dataTable>
                    <br>       
                    <h:panelGroup rendered="#{ManagedAccesoSistema.almacenesGlobal.aplicaGestionUbicaciones}">
                        <a4j:commandButton value="Agregar" styleClass="btn" 
                        onclick="javascript:Richfaces.showModalPanel('panelAgregarAmbientes')"
                        action="#{ManagedAmbientesAlmacen.agregarAmbiente_action}"
                        reRender="contenidoAgregarAmbientes" />

                        <a4j:commandButton value="Eliminar"  styleClass="btn" 
                        onclick="javascript:if(confirm('Esta Seguro de Eliminar??')==false || validarSeleccion('form1:dataAmbientes')==false){return false;}"
                        action="#{ManagedAmbientesAlmacen.eliminarAmbiente_action}"
                        reRender="dataAmbientes"/>

                        <a4j:commandButton value="Editar"  styleClass="btn" 
                        onclick="javascript:if(validarSeleccion('form1:dataAmbientes')==false){return false;}else{Richfaces.showModalPanel('panelEditarAmbientes')}"
                        action="#{ManagedAmbientesAlmacen.editarAmbiente_action}"
                        reRender="contenidoEditarAmbientes" />
                    </h:panelGroup>

                </div>
                
            </a4j:form>
            
             <rich:modalPanel id="panelAgregarAmbientes" minHeight="200"  minWidth="600"
                                     height="200" width="600"
                                     zindex="200"
                                     headerClass="headerClassACliente"
                                     resizeable="false" style="overflow :auto" >
                        <f:facet name="header">
                            <h:outputText value="Agregar Capitulos"/>
                        </f:facet>
                        <a4j:form>
                            <h:panelGroup id="contenidoAgregarAmbientes">
                                <h:panelGrid columns="3">
                                    <h:outputText value="Nombre Ambiente" styleClass="outputText1" />
                                    <h:outputText value="::" styleClass="outputText1" />
                                    <h:inputText value="#{ManagedAmbientesAlmacen.ambienteAlmacen.nombreAmbiente}" styleClass="inputText" size="15"  />

                                    <h:outputText value="observacion" styleClass="outputText1" />
                                    <h:outputText value="::" styleClass="outputText1" />
                                    <h:inputTextarea value="#{ManagedAmbientesAlmacen.ambienteAlmacen.observacion}" styleClass="inputText" rows="4" cols="30"  />

                                </h:panelGrid>
                                    <div align="center">
                                    <a4j:commandButton styleClass="btn" value="Registrar"
                                                       onclick="javascript:Richfaces.hideModalPanel('panelAgregarAmbientes');"
                                                       action="#{ManagedAmbientesAlmacen.guardarAgregarAmbiente_action}"
                                                       reRender="dataAmbientes"
                                                        />
                                    <input type="button" value="Cancelar" onclick="javascript:Richfaces.hideModalPanel('panelAgregarAmbientes')" class="btn" />
                                    </div>
                            </h:panelGroup>
                        </a4j:form>
            </rich:modalPanel>
            

            <rich:modalPanel id="panelEditarAmbientes" minHeight="200"  minWidth="600"
                                     height="200" width="600"
                                     zindex="200"
                                     headerClass="headerClassACliente"
                                     resizeable="false" style="overflow :auto" >
                        <f:facet name="header">
                            <h:outputText value="Editar Ambientes"/>
                        </f:facet>
                        <a4j:form>


                        <h:panelGroup id="contenidoEditarAmbientes">
                            
                                <h:panelGrid columns="3">
                                    <h:outputText value="Nombre Ambiente" styleClass="outputText1" />
                                    <h:outputText value="::" styleClass="outputText1" />
                                    <h:inputText value="#{ManagedAmbientesAlmacen.ambienteAlmacen.nombreAmbiente}" styleClass="inputText" size="15"  />

                                    <h:outputText value="observacion" styleClass="outputText1" />
                                    <h:outputText value="::" styleClass="outputText1" />
                                    <h:inputTextarea value="#{ManagedAmbientesAlmacen.ambienteAlmacen.observacion}" styleClass="inputText" rows="4" cols="30"  />

                                   

                                </h:panelGrid>


                               
                            
                                <div align="center">
                                <a4j:commandButton styleClass="btn" value="Guardar"
                                onclick="javascript:Richfaces.hideModalPanel('panelEditarAmbientes');"
                                action="#{ManagedAmbientesAlmacen.guardarEditarAmbiente}"
                                reRender="dataAmbientes"
                                                    />
                                <input type="button" value="Cancelar" onclick="javascript:Richfaces.hideModalPanel('panelEditarAmbientes')" class="btn" />
                                </div>
                        </h:panelGroup>
                        </a4j:form>
            </rich:modalPanel>

        </body>
    </html>
    
</f:view>

