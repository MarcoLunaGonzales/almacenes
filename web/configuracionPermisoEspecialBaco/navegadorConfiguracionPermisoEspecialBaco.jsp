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
        <%--inicio alejandro 2--%>
        <style>
            .b
            {

                background-color:#FF0000;
            }
        </style>
        <%--final alejandro 2--%>
        <script>

            onerror=function (){
                alert('existe un error al momento de cargar el registro, por favor recargue la pagina');
            }
        </script>
    </head>
        <body >
            <a4j:form id="form1">
                <div align="center">
                    <h:outputText value="#{ManagedConfiguracionPermisoEspecialBaco.cargarConfiguracionPermisoEspecialBaco}"/>
                    
                    <h:outputText styleClass="outputTextTitulo"  value="ADMINISTRAR CONFIGURACIÓN DE PERMISOS ESPECIALES BACO" />
                    <br><br>
                    <rich:dataTable value="#{ManagedConfiguracionPermisoEspecialBaco.configuracionesPermisoEspecialBacoList}"
                                    var="data"
                                    id="dataConfiguracionPermisoEspecialBaco"
                                    onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                    onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                    headerClass="headerClassACliente" >

                        <rich:column styleClass ="#{data.colorFila}">
                            <f:facet name="header">
                                <h:outputText value="Nombre de Personal"  />
                            </f:facet>
                            <h:outputText  value="#{data.personal.nombreCompleto2}"  />
                        </rich:column>
                     
                        <rich:column styleClass ="#{data.colorFila}">
                            <f:facet name="header">
                                <h:outputText value="Almacenes Asignados"  />
                            </f:facet>
                            <h:outputText value="#{data.almacenes.nombreAlmacen}" />
                        </rich:column>          
                            
                        <rich:column styleClass ="#{data.colorFila}">
                            <f:facet name="header">
                                <h:outputText value="Acciones"  />
                            </f:facet>

                            <rich:dropDownMenu >
                                <f:facet name="label">
                                    <h:outputText value="Acciones"/>
                                </f:facet>
                                
                                <rich:menuItem submitMode="ajax" value="Editar"  >
                                    <a4j:support event="onclick" 
                                                 action="#{ManagedConfiguracionPermisoEspecialBaco.editarConfiguracionPerisoEspecialBaco_action}" 
                                                 oncomplete="Richfaces.showModalPanel('panelEditarConfiguracionPermisoEspecialBaco'); " 
                                                 reRender="contenidoEditarConfiguracionPermisoEspecialBaco"
                                                  >
                                        <f:setPropertyActionListener value="#{data}" target="#{ManagedConfiguracionPermisoEspecialBaco.configuracionPermisoEspecialBacoGestionar}"/>
                                    </a4j:support>  
                                </rich:menuItem>
                                
                                <rich:menuItem submitMode="ajax" value="Anular" >
                                    <a4j:support event="onclick" 
                                                 action="#{ManagedConfiguracionPermisoEspecialBaco.editarConfiguracionPerisoEspecialBaco_action}"
                                                 oncomplete="Richfaces.showModalPanel('panelEditarConfiguracionPermisoEspecialBaco'); " 
                                                 >
                                        <f:setPropertyActionListener value="#{data}" target="#{ManagedConfiguracionPermisoEspecialBaco.configuracionPermisoEspecialBacoGestionar}"/>
                                    </a4j:support>  
                                </rich:menuItem>
                                
                            </rich:dropDownMenu>  
                        </rich:column>                    

                    </rich:dataTable>
                    
                    

                    <%-- <ws:datascroller fordatatable="dataAreasEmpresa"  />--%>
                    <br>
                    <h:panelGroup>
                        <a4j:commandButton value="Asigna a Nuevo Personal"  
                                           action="#{ManagedConfiguracionPermisoEspecialBaco.nuevaConfiguracionPerisoEspecialBaco_action}"  
                                           styleClass="btn"  
                                           onclick="Richfaces.showModalPanel('panelNuevaConfiguracionPermisoEspecialBaco');"
                                           reRender="contenidoNuevaConfiguracionPermisoEspecialBaco"/>
                     
                    </h:panelGroup> 

                </div>
                

            </a4j:form>

            
            
            <rich:modalPanel id="panelNuevaConfiguracionPermisoEspecialBaco"  minHeight="250"  minWidth="750"
                             height="500" width="900"
                             zindex="200"
                             headerClass="headerClassACliente"
                             resizeable="false" style="overflow :auto" 
                              >
                <f:facet name="header">
                    <h:outputText value="<center>NUEVA CONFIGURACION DE PERMISO ESPECIAL</center>" escape="false"/>
                </f:facet>
                <a4j:form id="form2">
                    <h:panelGroup id="contenidoNuevaConfiguracionPermisoEspecialBaco">
                        <div align="center">
                            <h:panelGrid columns="3">

                                <h:outputText value="Nombre de Personal:" styleClass="outputText1Subtitle" />
                                <h:outputText value="::" styleClass="outputText2" style="width:50px" />
                                <h:selectOneMenu  styleClass="inputText" value="#{ManagedConfiguracionPermisoEspecialBaco.configuracionPermisoEspecialBacoGestionar.personal.codPersonal}"   >
                                    <f:selectItems value="#{ManagedConfiguracionPermisoEspecialBaco.personalList}"  />
                                </h:selectOneMenu>
                                
                            </h:panelGrid> 

                            <h:panelGroup >
                                <a4j:commandButton value="Agregar Permiso" 
                                                   styleClass="btn"
                                                   action="#{ManagedConfiguracionPermisoEspecialBaco.agregarPermisoEspecialBacoPersonal_action}"
                                                   reRender="dataConfiguracionesPermisoEspecialBacoPersonal">                                    
                                </a4j:commandButton>
                                <br><br>
                                
                                <rich:dataTable value="#{ManagedConfiguracionPermisoEspecialBaco.configuracionesPermisoEspecialBacoPersonalList}"
                                            var="data"
                                            id="dataConfiguracionesPermisoEspecialBacoPersonal"
                                            onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                            onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                            headerClass="headerClassACliente" 
                                            rowKeyVar="index">

                                    <rich:column styleClass ="#{data.colorFila}">
                                        <f:facet name="header">
                                            <h:outputText value="Almacen"  />
                                        </f:facet>
                                        <h:selectOneMenu  styleClass="inputText" value="#{data.almacenes.codAlmacen}"   >
                                            <f:selectItems value="#{ManagedConfiguracionPermisoEspecialBaco.almacenesList}"  />
                                        </h:selectOneMenu>
                                    </rich:column>

                                    <rich:column styleClass ="#{data.colorFila}">
                                        <f:facet name="header">
                                            <h:outputText value="Tipo de permiso"  />
                                        </f:facet>
                                        <h:selectOneMenu  styleClass="inputText" value="#{data.tipoPermisoEspecialBaco.codTipoPermisoEspecialBaco}"   >
                                            <f:selectItems value="#{ManagedConfiguracionPermisoEspecialBaco.tiposPermisoEspecialBacoList}"  />
                                        </h:selectOneMenu>
                                    </rich:column>

                                    <rich:column styleClass ="#{data.colorFila}">
                                        <f:facet name="header">
                                            <h:outputText value="Acciones"  />
                                        </f:facet>
                                        <a4j:commandButton value="Eliminar" styleClass="btn"  
                                                            action="#{ManagedConfiguracionPermisoEspecialBaco.quitarPermisoEspecialBacoPersonal_action}"
                                                            reRender="dataConfiguracionesPermisoEspecialBacoPersonal" >                                            
                                            <f:setPropertyActionListener value="#{index}" target="#{ManagedConfiguracionPermisoEspecialBaco.indicePermisoEspecialBaco}"/>
                                        </a4j:commandButton>
                                    </rich:column>

                                </rich:dataTable>
                                
                                <br/>
                                
                                <a4j:commandButton styleClass="btn" value="Registrar"
                                                   reRender="dataConfiguracionPermisoEspecialBaco"
                                                   oncomplete="alert('#{ManagedConfiguracionPermisoEspecialBaco.mensaje}'); if(#{ManagedConfiguracionPermisoEspecialBaco.registradoCorrectamente}){Richfaces.hideModalPanel('panelNuevaConfiguracionPermisoEspecialBaco');}"
                                                   action="#{ManagedConfiguracionPermisoEspecialBaco.guardarNuevoPermisoEspecialBaco_action}" >
                                </a4j:commandButton>
                                <input type="button" value="Cancelar" onclick="javascript:Richfaces.hideModalPanel('panelNuevaConfiguracionPermisoEspecialBaco')" class="btn" />
                        
                            </h:panelGroup>
                        </div> 
                    </h:panelGroup>
                </a4j:form>
            </rich:modalPanel>   
                                
                                
                                
            <rich:modalPanel id="panelEditarConfiguracionPermisoEspecialBaco"  minHeight="250"  minWidth="750"
                             height="500" width="900"
                             zindex="200"
                             headerClass="headerClassACliente"
                             resizeable="false" style="overflow :auto" >
                <f:facet name="header">
                    <h:outputText value="<center>EDITAR CONFIGURACION DE PERMISO ESPECIAL</center>" escape="false"/>
                </f:facet>
                <a4j:form id="form3">
                    <h:panelGroup id="contenidoEditarConfiguracionPermisoEspecialBaco">
                        <div align="center">
                            <h:panelGrid columns="3">

                                <h:outputText value="Nombre de Personal:" styleClass="outputText1Subtitle" />
                                <h:outputText value="::" styleClass="outputText2" style="width:50px" />
                                <h:outputText value="#{ManagedConfiguracionPermisoEspecialBaco.configuracionPermisoEspecialBacoGestionar.personal.nombreCompleto2}" styleClass="outputText2" style="width:50px" />
                                
                            </h:panelGrid> 

                            <h:panelGroup >
                                <a4j:commandButton value="Agregar Permiso" 
                                                   styleClass="btn"
                                                   action="#{ManagedConfiguracionPermisoEspecialBaco.agregarPermisoEspecialBacoPersonal_action}"
                                                   reRender="dataConfiguracionesPermisoEspecialBacoPersonalE" >                                    
                                </a4j:commandButton>
                                <br><br>
                                
                                <rich:dataTable value="#{ManagedConfiguracionPermisoEspecialBaco.configuracionesPermisoEspecialBacoPersonalList}"
                                            var="data"
                                            id="dataConfiguracionesPermisoEspecialBacoPersonalE"
                                            onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                            onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                            headerClass="headerClassACliente" 
                                            rowKeyVar="index"
                                            >

                                    <rich:column styleClass ="#{data.colorFila}">
                                        <f:facet name="header">
                                            <h:outputText value="Almacen"  />
                                        </f:facet>
                                        <h:selectOneMenu  styleClass="inputText" value="#{data.almacenes.codAlmacen}"   >
                                            <f:selectItems value="#{ManagedConfiguracionPermisoEspecialBaco.almacenesList}"  />
                                        </h:selectOneMenu>
                                    </rich:column>

                                    <rich:column styleClass ="#{data.colorFila}">
                                        <f:facet name="header">
                                            <h:outputText value="Tipo de permiso"  />
                                        </f:facet>
                                        <h:selectOneMenu  styleClass="inputText" value="#{data.tipoPermisoEspecialBaco.codTipoPermisoEspecialBaco}"   >
                                            <f:selectItems value="#{ManagedConfiguracionPermisoEspecialBaco.tiposPermisoEspecialBacoList}"  />
                                        </h:selectOneMenu>
                                    </rich:column>

                                    <rich:column styleClass ="#{data.colorFila}">
                                        <f:facet name="header">
                                            <h:outputText value="Acciones"  />
                                        </f:facet>
                                        <a4j:commandButton value="Eliminar" styleClass="btn"  
                                                            action="#{ManagedConfiguracionPermisoEspecialBaco.quitarPermisoEspecialBacoPersonal_action}"
                                                            reRender="dataConfiguracionesPermisoEspecialBacoPersonalE" >                                            
                                            <f:setPropertyActionListener value="#{index}" target="#{ManagedConfiguracionPermisoEspecialBaco.indicePermisoEspecialBaco}"/>
                                        </a4j:commandButton>
                                    </rich:column>

                                </rich:dataTable>

                                <br/>
                                
                                <a4j:commandButton styleClass="btn" value="Registrar"
                                                   reRender="dataConfiguracionPermisoEspecialBaco"
                                                   oncomplete="alert('#{ManagedConfiguracionPermisoEspecialBaco.mensaje}'); if(#{ManagedConfiguracionPermisoEspecialBaco.registradoCorrectamente}){Richfaces.hideModalPanel('panelEditarConfiguracionPermisoEspecialBaco');}"
                                                   action="#{ManagedConfiguracionPermisoEspecialBaco.guardarEditarPermisoEspecialBaco_action}" >
                                </a4j:commandButton>
                                <input type="button" value="Cancelar" onclick="javascript:Richfaces.hideModalPanel('panelEditarConfiguracionPermisoEspecialBaco')" class="btn" />
                        
                            </h:panelGroup>
                        </div>
                    </h:panelGroup>
                </a4j:form>
            </rich:modalPanel>   
                
                
                
            <a4j:status id="statusPeticion"
                        onstart="Richfaces.showModalPanel('ajaxLoadingModalBox',{width:100, top:100})"
                        onstop="Richfaces.hideModalPanel('ajaxLoadingModalBox')">
            </a4j:status>
            <rich:modalPanel id="ajaxLoadingModalBox" minHeight="100"
                             minWidth="200" height="80" width="400" zindex="200" onshow="window.focus();">

                <div align="center">
                    <h:graphicImage value="../img/load2.gif" />
                </div>
            </rich:modalPanel>

        </body>
    </html>

</f:view>
    
