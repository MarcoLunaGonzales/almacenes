<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib prefix="a4j" uri="http://richfaces.org/a4j"%>
<%@taglib prefix="rich" uri="http://richfaces.org/rich"%>
<%@taglib prefix="ws" uri="http://isawi.org/ws"%>

<f:view>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>JSP Page</title>
        <link rel="STYLESHEET" type="text/css" href="../css/ventas.css" />
        <link rel="STYLESHEET" type="text/css" href="../css/chosen.css" />
        <script type="text/javascript" src="../js/general.js" ></script>
        <script>
            A4J.AJAX.onError = function(req,status,message){
            window.alert("Ocurrio un error "+message+" continue con su transaccion ");
            }
            A4J.AJAX.onExpired = function(loc,expiredMsg){
            if(window.confirm("Ocurrio un error al momento realizar la transaccion: "+expiredMsg+" location: "+loc)){
            return loc;
            } else {
            return false;
            }
            }
            
        </script>
        <style type="text/css">
            .tdRight
            {
                text-align: right;
            }
            .rich-table-headercell
            {
                color: black !important;
            }
        </style>
    </head>
    <body>
        <a4j:form id="form1">
            <h:outputText value="#{ManagedCambioUbicacionIngresos.cargarNavegadorIngresosUbicacion}"/>
            <div align="center">
                <h:outputText value="Cambio de Ubicación Ingresos Almacen" styleClass="outputText2" style="font-weight:bold" />  <br/><br/>
                <h:outputText value="El almacén #{ManagedAccesoSistema.almacenesGlobal.nombreAlmacen} no se encuentra configurado para la gestión de ubicaciones"
                              styleClass="outputTextBold" style="color:red;font-size:14px;"
                              rendered="#{not ManagedAccesoSistema.almacenesGlobal.aplicaGestionUbicaciones}"/>
                <rich:panel  headerClass="headerClassACliente" style="width:70%;" rendered="#{ManagedAccesoSistema.almacenesGlobal.aplicaGestionUbicaciones}">
                    <f:facet name="header">
                        <h:outputText value="Cambio de Ubicacion"/>
                    </f:facet>
                    <h:panelGrid columns="6">
                        <h:outputText value="Capitulo" styleClass="outputText2" style="font-weight:bold"/>
                        <h:outputText value="::" styleClass="outputText2" style="font-weight:bold"/>
                        <h:selectOneMenu value="#{ManagedCambioUbicacionIngresos.ingresosAlmacenDetalleEstadoBuscar.materiales.grupos.capitulos.codCapitulo}"
                                         styleClass="inputText chosen">
                            <f:selectItem itemLabel="--TODOS--" itemValue='0'/>
                            <f:selectItems value="#{ManagedCambioUbicacionIngresos.capitulosSelectList}"/>
                            <a4j:support event="onchange" action="#{ManagedCambioUbicacionIngresos.codCapituloOnChange}" reRender="codGrupo"/>
                        </h:selectOneMenu>
                        <h:outputText value="Grupo" styleClass="outputText2" style="font-weight:bold"/>
                        <h:outputText value="::" styleClass="outputText2" style="font-weight:bold"/>
                        <h:selectOneMenu value="#{ManagedCambioUbicacionIngresos.ingresosAlmacenDetalleEstadoBuscar.materiales.grupos.codGrupo}"
                                         styleClass="inputText chosen" id="codGrupo">
                            <f:selectItem itemLabel="--TODOS--" itemValue='0'/>
                            <f:selectItems value="#{ManagedCambioUbicacionIngresos.gruposSelectList}"/>
                            <a4j:support event="onchange" action="#{ManagedCambioUbicacionIngresos.codGrupoOnChange()}" reRender="codMaterial"/>
                        </h:selectOneMenu>
                        <h:outputText value="Material" styleClass="outputText2" style="font-weight:bold"/>
                        <h:outputText value="::" styleClass="outputText2" style="font-weight:bold"/>
                        <h:selectOneMenu style="width:350px" 
                                         value="#{ManagedCambioUbicacionIngresos.ingresosAlmacenDetalleEstadoBuscar.materiales.codMaterial}"
                                         id="codMaterial" 
                                         styleClass="inputText chosen">
                            <f:selectItem itemValue="0" itemLabel="--TODOS--"/>
                            <f:selectItems value="#{ManagedCambioUbicacionIngresos.materialesSelectList}"/>
                        </h:selectOneMenu>
                        <h:outputText value="Código" styleClass="outputText2" style="font-weight:bold"/>
                        <h:outputText value="::" styleClass="outputText2" style="font-weight:bold"/>
                        <h:inputText value="#{ManagedCambioUbicacionIngresos.ingresosAlmacenDetalleEstadoBuscar.materiales.codigoAntoguo}"
                                     styleClass="inputText"/>
                        <h:outputText value="Ambiente" styleClass="outputText2" style="font-weight:bold"/>
                        <h:outputText value="::" styleClass="outputText2" style="font-weight:bold"/>
                        <h:selectOneMenu value="#{ManagedCambioUbicacionIngresos.ingresosAlmacenDetalleEstadoBuscar.estanteAlmacen.ambienteAlmacen.codAmbiente}" id="codAmbiente" styleClass="inputText">
                            <f:selectItem itemLabel="--Seleccione una opción--" itemValue='0'/>
                            <f:selectItems value="#{ManagedCambioUbicacionIngresos.ambientesList}"/>
                            <f:selectItem itemLabel="Sin Ubicacion" itemValue='-1'/>
                            <a4j:support event="onchange" action="#{ManagedCambioUbicacionIngresos.onchangeAmbiente_action}" reRender="codEstante,estante,ubicacion"/>
                        </h:selectOneMenu>
                        <h:outputText value="Pasillo" styleClass="outputText2" style="font-weight:bold"/>
                        <h:outputText value="::" styleClass="outputText2" style="font-weight:bold"/>
                        <h:selectOneMenu disabled="#{ManagedCambioUbicacionIngresos.ingresosAlmacenDetalleEstadoBuscar.estanteAlmacen.ambienteAlmacen.codAmbiente eq -1}" value="#{ManagedCambioUbicacionIngresos.ingresosAlmacenDetalleEstadoBuscar.estanteAlmacen.codEstante}" id="codEstante" styleClass="inputText">
                            <f:selectItem itemLabel="--Seleccione una opción--" itemValue='0'/>
                            <f:selectItems value="#{ManagedCambioUbicacionIngresos.estantesList}"/>
                        </h:selectOneMenu>
                        <h:outputText value="Estante" styleClass="outputText2" style="font-weight:bold"/>
                        <h:outputText value="::" styleClass="outputText2" style="font-weight:bold"/>
                        <h:inputText id="estante" disabled="#{ManagedCambioUbicacionIngresos.ingresosAlmacenDetalleEstadoBuscar.estanteAlmacen.ambienteAlmacen.codAmbiente eq -1}" value="#{ManagedCambioUbicacionIngresos.ingresosAlmacenDetalleEstadoBuscar.fila}" styleClass="inputText"/>
                        <h:outputText value="Ubicacion" styleClass="outputText2" style="font-weight:bold"/>
                        <h:outputText value="::" styleClass="outputText2" style="font-weight:bold"/>
                        <h:inputText id="ubicacion" disabled="#{ManagedCambioUbicacionIngresos.ingresosAlmacenDetalleEstadoBuscar.estanteAlmacen.ambienteAlmacen.codAmbiente eq -1}" value="#{ManagedCambioUbicacionIngresos.ingresosAlmacenDetalleEstadoBuscar.columna}" styleClass="inputText"/>
                    </h:panelGrid>
                    <a4j:commandButton value="BUSCAR" action="#{ManagedCambioUbicacionIngresos.buscarIngresosUbicacion_action}" styleClass="btn" reRender="dataIngresosAlmacen" />
                    
                </rich:panel>

                
                        
                        

                            
                            <rich:dataTable value="#{ManagedCambioUbicacionIngresos.ingresosAlmacenDetalleEstadoList}"
                                            rendered="#{ManagedAccesoSistema.almacenesGlobal.aplicaGestionUbicaciones}"
                                            var="data" id="dataIngresosAlmacen" style="margin-top:1em !important"
                                            onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                            onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                            headerClass="headerClassACliente" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column>
                                            <h:outputText value="Ambiente"/>
                                        </rich:column>
                                        <rich:column>
                                            <h:outputText value="Pasillo"/>
                                        </rich:column>
                                        <rich:column>
                                            <h:outputText value="Estante"/>
                                        </rich:column>
                                        <rich:column>
                                            <h:outputText value="Ubicación"/>
                                        </rich:column>
                                        <rich:column>
                                            <h:outputText value="Material"/>
                                        </rich:column>
                                        <rich:column>
                                            <h:outputText value="Lote Proveedor"/>
                                        </rich:column>
                                        <rich:column>
                                            <h:outputText value="Cantidad"/>
                                        </rich:column>
                                        <rich:column>
                                            <h:outputText value="Acciones"/>
                                        </rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column>
                                    <h:outputText value="#{data.estanteAlmacen.ambienteAlmacen.nombreAmbiente}" />
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="#{data.estanteAlmacen.nombreEstante}" />
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="#{data.fila}" />
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="#{data.columna}" />
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="#{data.materiales.nombreMaterial}" />
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="#{data.loteMaterialProveedor}" />
                                </rich:column>
                                <rich:column styleClass="tdRight" >
                                    <h:outputText value="#{data.cantidadRestante}" >
                                        <f:convertNumber pattern="##,##0.00" locale="en"/>
                                    </h:outputText>
                                </rich:column>
                                <rich:column>
                                    <rich:dropDownMenu rendered="#{ManagedCambioUbicacionIngresos.administradorAlmacen}">
                                        <f:facet name="label">
                                            <h:panelGroup>
                                                <h:outputText value="Acciones"/>
                                            </h:panelGroup>
                                        </f:facet>
                                        <rich:menuItem  submitMode="none"  value="Cambiar Ubicación" icon="../img/icon.jpg"> 
                                            <a4j:support event="onclick" action="#{ManagedCambioUbicacionIngresos.seleccionIngresoCambioItem_action}"
                                                         reRender="contenidoCambiarUbicacion"
                                                         oncomplete="window.location.href='editarUbicacionIngresos.jsf?data='+(new Date()).getTime().toString();">
                                                <f:setPropertyActionListener target="#{ManagedCambioUbicacionIngresos.ingresosAlmacenDetalleEstadoEditar}" 
                                                                             value="#{data}"/>
                                            </a4j:support>
                                        </rich:menuItem>
                                        <rich:menuSeparator/>
                                        <rich:menuItem rendered="#{data.estanteAlmacen.codEstante > 0}" submitMode="none"  value="Quitar Ubicación" icon="../img/menos.png">
                                            <a4j:support event="onclick" action="#{ManagedCambioUbicacionIngresos.vaciarUbicacionIngresosAlmacenDetalleEstado_action}"
                                                         reRender="dataIngresosAlmacen"
                                                         oncomplete="if(#{ManagedCambioUbicacionIngresos.mensaje eq '1'}){alert('Transacción Exitosa');}else{alert('#{ManagedCambioUbicacionIngresos.mensaje}');}">
                                                <f:setPropertyActionListener target="#{ManagedCambioUbicacionIngresos.ingresosAlmacenDetalleEstadoEditar}" 
                                                                             value="#{data}"/>
                                            </a4j:support>
                                        </rich:menuItem>
                                    </rich:dropDownMenu>
                                </rich:column>
                                
                            </rich:dataTable>
            </div>
        </a4j:form>
        <rich:modalPanel id="panelCambiarUbicacion" minHeight="350"  minWidth="650"
                                     height="350" width="650"  zindex="200"  headerClass="headerClassACliente"
                                     resizeable="false" style="overflow :auto" >
                        <f:facet name="header" >
                            <h:outputText value="<center>Cambio de Ubicacion</center>" escape="false"/>
                        </f:facet>
                        <a4j:form id="form2">
                        <h:panelGroup id="contenidoCambiarUbicacion">
                            <rich:panel headerClass="headerClassACliente">
                                <f:facet name="header" >
                                    <h:outputText value="<center>Ubicacion Original</center>" escape="false"/>
                                </f:facet>
                                    <div align="center">
                                        <h:panelGrid columns="6"   >
                                            <h:outputText value="Ambiente" styleClass="outputText2" style="font-weight:bold" />
                                            <h:outputText value="::" styleClass="outputText2" style="font-weight:bold" />
                                            <h:outputText value="#{ManagedCambioUbicacionIngresos.ingresosAlmacenDetalleEstadoEditar.estanteAlmacen.ambienteAlmacen.nombreAmbiente}" styleClass="outputText2" />
                                            <h:outputText value="Pasillo" styleClass="outputText2" style="font-weight:bold" />
                                            <h:outputText value="::" styleClass="outputText2" style="font-weight:bold" />
                                            <h:outputText value="#{ManagedCambioUbicacionIngresos.ingresosAlmacenDetalleEstadoEditar.estanteAlmacen.nombreEstante}" styleClass="outputText2" />
                                            <h:outputText value="Estante" styleClass="outputText2" style="font-weight:bold" />
                                            <h:outputText value="::" styleClass="outputText2" style="font-weight:bold" />
                                            <h:outputText value="#{ManagedCambioUbicacionIngresos.ingresosAlmacenDetalleEstadoEditar.fila}" styleClass="outputText2" />
                                            <h:outputText value="Ubicacion" styleClass="outputText2" style="font-weight:bold" />
                                            <h:outputText value="::" styleClass="outputText2" style="font-weight:bold" />
                                            <h:outputText value="#{ManagedCambioUbicacionIngresos.ingresosAlmacenDetalleEstadoEditar.columna}" styleClass="outputText2" />
                                            <h:outputText value="Material" styleClass="outputText2" style="font-weight:bold" />
                                            <h:outputText value="::" styleClass="outputText2" style="font-weight:bold" />
                                            <h:outputText value="#{ManagedCambioUbicacionIngresos.ingresosAlmacenDetalleEstadoEditar.materiales.nombreMaterial}" styleClass="outputText2" />
                                            <h:outputText value="Cantidad Restante" styleClass="outputText2" style="font-weight:bold" />
                                            <h:outputText value="::" styleClass="outputText2" style="font-weight:bold" />
                                            <h:outputText value="#{ManagedCambioUbicacionIngresos.ingresosAlmacenDetalleEstadoEditar.cantidadRestante}" styleClass="outputText2">
                                                <f:convertNumber pattern="#####.##" locale="en"/>
                                            </h:outputText>
                                            <h:outputText value="Estado Material" styleClass="outputText2" style="font-weight:bold" />
                                            <h:outputText value="::" styleClass="outputText2" style="font-weight:bold" />
                                            <h:outputText value="#{ManagedCambioUbicacionIngresos.ingresosAlmacenDetalleEstadoEditar.estadosMaterial.nombreEstadoMaterial}" styleClass="outputText2" />
                                     </h:panelGrid>
                                     </div>
                                 </rich:panel>
                                 <rich:panel headerClass="headerClassACliente">
                                    <f:facet name="header" >
                                        <h:outputText value="<center>Nueva Ubicación</center>" escape="false"/>
                                    </f:facet>
                                        <div align="center">
                                            <h:panelGrid columns="3"  >
                                                <h:outputText value="Ambiente" styleClass="outputText2" style="font-weight:bold" />
                                                <h:outputText value="::" styleClass="outputText2" style="font-weight:bold" />
                                                <h:selectOneMenu value="#{ManagedCambioUbicacionIngresos.ingresosAlmacenDetalleEstadoNuevaUbication.estanteAlmacen.ambienteAlmacen.codAmbiente}" styleClass="inputText" style="width:250px" >
                                                    <f:selectItems value="#{ManagedCambioUbicacionIngresos.ambientesList}" />
                                                    <a4j:support action="#{ManagedCambioUbicacionIngresos.onchangeAmbienteEditar_action}" reRender="codEstante" event="onchange" />
                                                </h:selectOneMenu>
                                                <h:outputText value="Pasillo" styleClass="outputText2" style="font-weight:bold" />
                                                <h:outputText value="::" styleClass="outputText2" style="font-weight:bold" />
                                                <h:selectOneMenu value="#{ManagedCambioUbicacionIngresos.ingresosAlmacenDetalleEstadoNuevaUbication.estanteAlmacen.codEstante}" id="codEstante" styleClass="inputText" >
                                                    <f:selectItems value="#{ManagedCambioUbicacionIngresos.estantesList}" />
                                                </h:selectOneMenu>
                                                <h:outputText value="Estante" styleClass="outputText2" style="font-weight:bold" />
                                                <h:outputText value="::" styleClass="outputText2" style="font-weight:bold" />
                                                <h:inputText value="#{ManagedCambioUbicacionIngresos.ingresosAlmacenDetalleEstadoNuevaUbication.fila}" styleClass="inputText"/>
                                                <h:outputText value="Ubicacion" styleClass="outputText2" style="font-weight:bold" />
                                                <h:outputText value="::" styleClass="outputText2" style="font-weight:bold" />
                                                <h:inputText value="#{ManagedCambioUbicacionIngresos.ingresosAlmacenDetalleEstadoNuevaUbication.columna}" styleClass="inputText"/>
                                            </h:panelGrid>
                                        </div>
                                    </rich:panel>
                                    </h:panelGroup>
                                     <div align="center">
                                         <a4j:commandButton styleClass="btn" value="Guardar" reRender="dataIngresosAlmacen" timeout="7200"
                                                            action="#{ManagedCambioUbicacionIngresos.guardarCambioUbicacionIngresoAlmacen_action}"
                                                            oncomplete="if(#{ManagedCambioUbicacionIngresos.mensaje eq '1'}){alert('Se realizo el cambio de ubicación');Richfaces.hideModalPanel('panelCambiarUbicacion');}else{alert('#{ManagedCambioUbicacionIngresos.mensaje}');}"
                                                               onclick="if(!confirm('Esta seguro de guardar la informacion?')){return false;}"/>
                                            <input type="button" value="Cancelar" onclick="javascript:Richfaces.hideModalPanel('panelCambiarUbicacion')" class="btn"  />
                                    </div>
                        </a4j:form>
          </rich:modalPanel>
        <a4j:include viewId="/panelProgreso.jsp"/>
        
    </body>
</html>
</f:view>
