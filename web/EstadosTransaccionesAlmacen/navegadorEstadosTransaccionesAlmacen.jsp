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
                function validarHabilitar(nametable){
                   var count=0;
                   var elements=document.getElementById(nametable);
                   var rowsElement=elements.rows;
                   for(var i=1;i<rowsElement.length;i++){
                    var cellsElement=rowsElement[i].cells;
                    var cel=cellsElement[0];
                    if(cel.getElementsByTagName('input')[0].type=='checkbox'){
                          if(cel.getElementsByTagName('input')[0].checked){
                           count++;
                           if(parseInt(cellsElement[1].getElementsByTagName('input')[0].value)!=2)
                               {
                                   alert('Solo se pueden cerrar transacciones habilitadas');
                                   return false;
                               }
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
                function validarInhabilitar(nametable){
                   if(confirm('Esta seguro de habilitar las transacciones')==true)
                       {
                                   var count=0;
                                   var elements=document.getElementById(nametable);
                                   var rowsElement=elements.rows;

                                   for(var i=1;i<rowsElement.length;i++){
                                    var cellsElement=rowsElement[i].cells;
                                    var cel=cellsElement[0];
                                    if(cel.getElementsByTagName('input')[0].type=='checkbox'){
                                          if(cel.getElementsByTagName('input')[0].checked){
                                           count++;
                                           if(parseInt(cellsElement[1].getElementsByTagName('input')[0].value)!=1)
                                               {
                                                   alert('Solo se pueden habilitar transacciones cerradas');
                                                   return false;
                                               }
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
                       else
                         {
                             return false;
                         }
                }
            </script>
        </head>
        <body >
            <a4j:form id="form1">
                <div align="center">
                    <h:outputText value="#{ManagedEstadosTransaccionesAlmacen.cargarEstadosTransaccionesAlmacen}"/>
                    <h:outputText styleClass="outputTextTitulo"  value="Estados Transacciones Almacen" />
                    <br><br>
                    
                    <rich:dataTable value="#{ManagedEstadosTransaccionesAlmacen.estadosTransaccionesAlmacenList}"
                                    var="data"
                                    id="dataEstadosTransaccionesAlmacen"
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
                                <h:outputText value="Gestión"  />
                            </f:facet>
                            <h:inputHidden value="#{data.estado.codEstadoRegistro}"/>
                            <h:outputText  value="#{data.gestion.nombreGestion}"  />
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Mes"  />
                            </f:facet>
                            <h:outputText value="#{data.mes.nombreMes}">
                            </h:outputText>
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Fecha Cierre"  />
                            </f:facet>
                            <h:outputText value="#{data.fechaCierre}"  >
                                <f:convertDateTime pattern="dd/MM/yyyy HH:mm"/>
                             </h:outputText>
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Nombre Personal"  />
                                
                            </f:facet>
                            <h:outputText value="#{data.personal.nombrePersonal}"  />
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Estado Transaccion"  />
                            </f:facet>
                            <h:outputText value="#{data.estado.nombreEstadoRegistro}"  />
                        </h:column>

                    </rich:dataTable>
                    


                     <h:panelGrid columns="2"  width="50" id="controles">
                         <a4j:commandLink  action="#{ManagedEstadosTransaccionesAlmacen.atras_action}"   rendered="#{ManagedEstadosTransaccionesAlmacen.begin!='0'}"  reRender="dataEstadosTransaccionesAlmacen,controles">
                                <h:graphicImage url="../img/previous.gif"  style="border:0px solid red"   alt="PAGINA ANTERIOR"  />
                            </a4j:commandLink>
                            <a4j:commandLink  action="#{ManagedEstadosTransaccionesAlmacen.siguiente_action}"  rendered="#{ManagedEstadosTransaccionesAlmacen.cantidadfilas>'9'}" reRender="dataEstadosTransaccionesAlmacen,controles">
                                <h:graphicImage url="../img/next.gif"  style="border:0px solid red"  alt="PAGINA SIGUIENTE" />
                            </a4j:commandLink>
                    </h:panelGrid>
                    

                   
                    <br>
                        <a4j:commandButton value="Agregar Cierre de transacciones" styleClass="btn" oncomplete="Richfaces.showModalPanel('PanelCerrarTransaccionesAlmacen')" />
                        <a4j:commandButton value="Habilitar transacciones" onclick="if(validarInhabilitar('form1:dataEstadosTransaccionesAlmacen')==false){return false;}" styleClass="btn" action="#{ManagedEstadosTransaccionesAlmacen.inhabilitarEstadoTransaccionAlmacen_Action}" reRender="dataEstadosTransaccionesAlmacen" />
                        <a4j:commandButton value="Cerrar transacciones" onclick="if(validarHabilitar('form1:dataEstadosTransaccionesAlmacen')==false){return false;}" styleClass="btn" action="#{ManagedEstadosTransaccionesAlmacen.habilitarEstadoTransaccionAlmacen_Action}" reRender="dataEstadosTransaccionesAlmacen"/>

                   
                </div>

               
              
            </a4j:form>
                
             <rich:modalPanel id="PanelCerrarTransaccionesAlmacen" minHeight="150"  minWidth="500"
                                     height="150" width="500"
                                     zindex="200"
                                     headerClass="headerClassACliente"
                                     resizeable="false" style="overflow :auto" >
                        <f:facet name="header">
                            <h:outputText value="Cierre de Transacciones Almacen"/>
                        </f:facet>
                        <a4j:form>
                            <a4j:jsFunction action="#{ManagedEstadosTransaccionesAlmacen.habilitarTransaccion_ActionJS}" name="cambiarEstado" oncomplete="alert('Se cerró las transacciones para la gestion y mes');javascript:Richfaces.hideModalPanel('PanelCerrarTransaccionesAlmacen');"
                            reRender="dataEstadosTransaccionesAlmacen,controles"/>
                            
                        <h:panelGroup id="contenidoCerrarTransaccionesAlmacen">
                            <div align="center">
                            <h:panelGrid columns="4">
                                
                                <h:outputText value="Gestion:" styleClass="outputText1" />
                                <h:selectOneMenu value="#{ManagedEstadosTransaccionesAlmacen.estadosTransaccionesAlmacenACerrar.gestion.codGestion}" styleClass="inputText" id="codGestion">
                                    <f:selectItems value="#{ManagedEstadosTransaccionesAlmacen.gestionesList}"/>
                                </h:selectOneMenu>
                                <h:outputText value="Mes:" styleClass="outputText1" />
                                <h:selectOneMenu value="#{ManagedEstadosTransaccionesAlmacen.estadosTransaccionesAlmacenACerrar.mes.codMes}" styleClass="inputText" id="codMes">
                                    <f:selectItems value="#{ManagedEstadosTransaccionesAlmacen.mesesList}"/>
                                </h:selectOneMenu>
                                </h:panelGrid>
                                
                                    
                                    <a4j:commandButton styleClass="btn" value="Cerrar Transacciones"  action="#{ManagedEstadosTransaccionesAlmacen.cerrarTransacciones_Action}"
                                    oncomplete="if(#{ManagedEstadosTransaccionesAlmacen.mensaje eq '3'}){alert('Se registro el cierre de transacción para la gestion y mes');javascript:Richfaces.hideModalPanel('PanelCerrarTransaccionesAlmacen')}
                                    else{if(#{ManagedEstadosTransaccionesAlmacen.mensaje eq '2'})
                                    {if(confirm('ya existe un registro para el mes y gestion abierto desea cerrarlo?')==true){cambiarEstado();}}else{alert('No se puede registrar el cierre para esta gestion y mes porque ya se encuentra cerrado')} }"
                                reRender="dataEstadosTransaccionesAlmacen,controles"/>
                                <input type="button" value="Cancelar" onclick="javascript:Richfaces.hideModalPanel('PanelCerrarTransaccionesAlmacen')" class="btn" />
                                </div>
                        </h:panelGroup>
                        </a4j:form>
            </rich:modalPanel>

           

        </body>
    </html>

</f:view>

