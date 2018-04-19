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
            <script type="text/javascript" src="../js/general.js" ></script>
            <script>
                A4J.AJAX.onError = function (req, status, message) {
                    window.alert("Ocurrio un error " + message + " continue con su transaccion ");
                }
                A4J.AJAX.onExpired = function (loc, expiredMsg) {
                    if (window.confirm("Ocurrio un error al momento realizar la transaccion: " + expiredMsg + " location: " + loc)) {
                        return loc;
                    } else {
                        return false;
                    }
                }

            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:outputText value="#{ManagedCambioUbicacionIngresos.cargarNavegadorSaldosUbicacion}"/>
                <div align="center">
                    <h:outputText value="Cambio de Ubicación Ingresos Almacen" styleClass="outputText2" style="font-weight:bold" />  <br/><br/>
                    <rich:panel  headerClass="headerClassACliente" style="top:50px;left:50px;width:700px;">
                        <f:facet name="header">
                            <h:outputText value="Cambio de Ubicacion"/>
                        </f:facet>
                        <h:panelGrid columns="6">
                            <h:outputText value="Filial" styleClass="outputText2" style="font-weight:bold"/>
                            <h:outputText value="::" styleClass="outputText2" style="font-weight:bold"/>
                            <h:selectOneMenu value="#{ManagedCambioUbicacionIngresos.ingresosAlmacenDetalleEstadoBuscar.ingresosAlmacen.almacenes.filiales.codFilial}" styleClass="inputText">
                                <f:selectItems value="#{ManagedCambioUbicacionIngresos.filialesList}"/>
                                <a4j:support event="onchange" action="#{ManagedCambioUbicacionIngresos.onchangeFilial_action}" reRender="codAlmacen,codAmbiente,codEstante"/>
                            </h:selectOneMenu>
                            <h:outputText value="Almacen" styleClass="outputText2" style="font-weight:bold"/>
                            <h:outputText value="::" styleClass="outputText2" style="font-weight:bold"/>
                            <h:selectOneMenu  value="#{ManagedCambioUbicacionIngresos.ingresosAlmacenDetalleEstadoBuscar.ingresosAlmacen.almacenes.codAlmacen}" id="codAlmacen" styleClass="inputText">

                                <f:selectItems value="#{ManagedCambioUbicacionIngresos.almacenesList}"/>

                                <a4j:support event="onchange" action="#{ManagedCambioUbicacionIngresos.onchangeAlmacen_action}" reRender="codAmbiente,codEstante"/>
                            </h:selectOneMenu>
                            <h:outputText value="Material" styleClass="outputText2" style="font-weight:bold"/>
                            <h:outputText value="::" styleClass="outputText2" style="font-weight:bold"/>
                            <h:selectOneMenu style="width:350px" value="#{ManagedCambioUbicacionIngresos.ingresosAlmacenDetalleEstadoBuscar.materiales.codMaterial}" id="codMaterial" styleClass="inputText">
                                <f:selectItem itemValue="0" itemLabel="--TODOS--"/>
                                <f:selectItems value="#{ManagedCambioUbicacionIngresos.materialesSelectList}"/>
                            </h:selectOneMenu>
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






                    <rich:dataTable value="#{ManagedCambioUbicacionIngresos.lista_MaterialesSaldos}"
                                    var="data" id="dataSaldosAlmacen" style="margin-top:1em !important"
                                    onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                    onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                    headerClass="headerClassACliente" width="100%"
                                    binding="#{ManagedCambioUbicacionIngresos.saldosAlmacenDetalleDataTable}">
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="" />
                            </f:facet>
                            <h:selectBooleanCheckbox value="#{data.checked}" />
                        </h:column>

                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Pasillo"  />
                            </f:facet>
                            <h:outputText value="#{data.estanteAlmacen.nombreEstante}" />
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Estante"  />
                            </f:facet>
                            <h:outputText value="#{data.fila}" />
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Ubicacion"  />
                            </f:facet>
                            <h:outputText value="#{data.columna}" />
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Material"  />
                            </f:facet>
                            <h:outputText value="#{data.materiales.nombreMaterial}" />
                        </h:column>

                        <h:column>
                            <f:facet name="header">
                                <h:outputText value=""  />
                            </f:facet>
                            <h:outputText value="" />
                            <a4j:commandLink action="#{ManagedCambioUbicacionIngresos.detallarItem_action}" 
                                             onclick="Richfaces.showModalPanel('panelDetalleItem')" reRender="contenidoDetalleItem" >
                                <h:graphicImage url="../img/areasdependientes.png" alt="detalle de item">

                                </h:graphicImage>

                            </a4j:commandLink>
                        </h:column>

                    </rich:dataTable>

                    <a4j:commandButton style="margin-top:1em !important;" value="Cambiar Ubicación" action="#{ManagedCambioUbicacionIngresos.seleccionIngresoCambioItem_action}" reRender="contenidoCambiarUbicacion" oncomplete="window.location.href='editarUbicacionIngresos.jsf?data='+(new Date()).getTime().toString();" styleClass="btn"
                                       onclick="if(!editarItem('form1:dataIngresosAlmacen')){return false;}"/>

                </div>
            </a4j:form>
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
                                        <f:convertNumber pattern="#####.##" locale="EN"/>
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
            <rich:modalPanel id="panelDetalleItem" minHeight="400"  minWidth="600"
                             height="400" width="600"
                             zindex="200"
                             headerClass="headerClassACliente"
                             resizeable="false" style="overflow :auto" >
                <f:facet name="header">
                    <h:outputText value="Detalle Item"/>
                </f:facet>
                <a4j:form id="form3">
                    <h:panelGroup id="contenidoDetalleItem">
                        <div align="center">                                   
                            <h:outputText value="Item: " styleClass="tituloCampo1" />
                            &nbsp;
                            <h:outputText value="#{ManagedIngresoAlmacen.ingresosAlmacenDetalle.materiales.nombreMaterial}" styleClass="tituloCampo1" />

                            <br/>
                            <br/>

                            <h:panelGrid columns="9" id="contenidoIngresoAlmacenDetalle" headerClass="headerClassACliente" width="100%" style="border:1px solid #000000;" >
                                <f:facet name="header">
                                    <h:outputText value="Repetir Valores" />
                                </f:facet>

                                <h:outputText value="Cantidad Segun Documento:" styleClass="outputText1" />

                                <h:panelGroup>
                                    <h:inputText value="#{ManagedIngresoAlmacen.ingresosAlmacenDetalle.cantTotalIngreso}" styleClass="inputText" size="5" id="cantTotalIngreso" onkeypress="valNum(event);" readonly="true" onkeyup="hallaValorEquivalente(this)" />
                                    <h:outputText value="[" styleClass="outputText1" />
                                    <h:outputText value="#{ManagedIngresoAlmacen.ingresosAlmacenDetalle.ordenesCompraDetalle.unidadesMedida.abreviatura}" styleClass="outputText1" />
                                    <h:outputText value="]" styleClass="outputText1" />
                                </h:panelGroup>

                                <h:outputText value="Equivalente:" styleClass="outputText1" />
                                <h:panelGroup>
                                    <h:inputText value="#{ManagedIngresoAlmacen.ingresosAlmacenDetalle.cantEquivalente}" styleClass="inputText" size="5" readonly="true" onkeypress="valNum(event);" id="cantEquivalente" />
                                    <h:outputText value="[" styleClass="outputText1" />
                                    <h:outputText value="#{ManagedIngresoAlmacen.ingresosAlmacenDetalle.unidadesMedidaEquivalencia.abreviatura}" styleClass="outputText1" />
                                    <h:outputText value="]" styleClass="outputText1" />
                                    <h:inputHidden value="#{ManagedIngresoAlmacen.ingresosAlmacenDetalle.valorEquivalencia}" id="valorEquivalencia" />
                                </h:panelGroup>

                                <h:outputText value="Cant. Ingreso Fisico:" styleClass="outputText1" />
                                <h:panelGroup>
                                    <h:inputText value="#{ManagedIngresoAlmacen.ingresosAlmacenDetalle.cantTotalIngresoFisico}" id="cantTotalIngresoFisico"  styleClass="inputText" size="5" onkeypress="valNum(event);" readonly="true" />
                                    <h:outputText value="[" styleClass="outputText1" />
                                    <h:outputText value="#{ManagedIngresoAlmacen.ingresosAlmacenDetalle.unidadesMedida.abreviatura}" styleClass="outputText1" />
                                    <h:outputText value="]" styleClass="outputText1" />
                                </h:panelGroup>

                                <h:outputText value="Nro de Empaques:" styleClass="outputText1" />

                                <h:inputText value="#{ManagedIngresoAlmacen.ingresosAlmacenDetalle.nroUnidadesEmpaque}" styleClass="inputText" size="5"  />
                                <a4j:commandButton value="Generar Etiquetas" action="#{ManagedIngresoAlmacen.generarEtiquetas_action}" styleClass="btn"  reRender="dataIngresoAlmacenDetalleEstado" timeout="7200" />
                            </h:panelGrid>

                            <br/>

                            <h:panelGrid columns="6" headerClass="headerClassACliente" width="100%" style="border:1px solid #000000;">
                                <f:facet name="header">
                                    <h:outputText value="Repetir Valores" />
                                </f:facet>


                                <h:outputText value="Cantidad Parcial:" styleClass="outputText1" />
                                <h:inputText value="#{ManagedIngresoAlmacen.ingresosAlmacenDetalleEstado.cantidadParcial}" styleClass="inputText" size="10" onkeypress="valNumPositivo(event);" >

                                </h:inputText>

                                <h:outputText value="Lote Proveedor:" styleClass="outputText1" />
                                <h:inputText value="#{ManagedIngresoAlmacen.ingresosAlmacenDetalleEstado.loteMaterialProveedor}" styleClass="inputText" size="10"  ><!--onkeypress="valNum(event);"-->
                                </h:inputText>

                                <h:outputText value="Unidad de Empaque:" styleClass="outputText1" />
                                <h:selectOneMenu value="#{ManagedIngresoAlmacen.ingresosAlmacenDetalleEstado.empaqueSecundarioExterno.codEmpaqueSecundarioExterno}" styleClass="inputText" >
                                    <f:selectItems value="#{ManagedIngresoAlmacen.empaqueSecundarioExternoList}"  />
                                </h:selectOneMenu>

                                <%--h:outputText value="Fecha Manufactura:" styleClass="outputText1" /--%>
                                <%--rich:calendar datePattern="dd/MM/yyyy" styleClass="inputText" immediate="true"
                                value="#{ManagedIngresoAlmacen.ingresosAlmacenDetalleEstado.fechaManufactura}"   id="fechaManufactura" enableManualInput="true"  width="1" oninputblur="valFecha(this);">
                                <%--a4j:support event="onchanged" /--%>
                                <%--a4j:support event="oninputblur" ajaxSingle="true" action="#{ManagedIngresoAlmacen.validaFechas_action}" reRender="contenidoDetalleItem"
                                oncomplete="if(#{ManagedIngresoAlmacen.mensaje!=''}){alert('#{ManagedIngresoAlmacen.mensaje}');document.getElementById('form3:fechaManufacturaInputDate').focus()}else{valFecha(document.getElementById('form3:fechaManufacturaInputDate'));}" / --%><%-- ondateselect="document.getElementById('form3:fechaManufacturaInputDate').focus(); return true;" --%>
                                <%--/rich:calendar--%>


                                <h:outputText value="Fecha de Reanalisis:" styleClass="outputText1" />
                                <rich:calendar   datePattern="dd/MM/yyyy" styleClass="inputText"
                                                 value="#{ManagedIngresoAlmacen.ingresosAlmacenDetalleEstado.fechaReanalisis}"  enableManualInput="true" oninputblur="valFecha(this);" width="1" />

                                <h:outputText value="Fecha de Vencimiento:" styleClass="outputText1" />
                                <rich:calendar   datePattern="dd/MM/yyyy" styleClass="inputText" immediate="true"
                                                 value="#{ManagedIngresoAlmacen.ingresosAlmacenDetalleEstado.fechaVencimiento}"  id="fechaVencimiento" enableManualInput="true"  width="1" oninputblur="valFecha(this);">
                                    <%--a4j:support event="oninputblur" ajaxSingle="true" action="#{ManagedIngresoAlmacen.validaFechas_action1}"
                                        oncomplete="if(#{ManagedIngresoAlmacen.mensaje!=''}){alert('#{ManagedIngresoAlmacen.mensaje}');document.getElementById('form3:fechaVencimientoInputDate').focus()}else{valFecha(document.getElementById('form3:fechaVencimientoInputDate'));}" /--%><%-- ondateselect="document.getElementById('form3:fechaVencimientoInputDate').focus(); return true;" --%>
                                </rich:calendar>
                                <h:outputText value="Tara:" styleClass="outputText1" />
                                <h:inputText value="#{ManagedIngresoAlmacen.ingresosAlmacenDetalleEstado.tara}" styleClass="inputText" size="10" onkeypress="valNum(event)"  ><!--onkeypress="valNum(event);"-->
                                </h:inputText>
                                <h:outputText value="Ambiente:" styleClass="outputText1" />
                                <h:selectOneMenu value="#{ManagedIngresoAlmacen.ingresosAlmacenDetalleEstado.estanteAlmacen.ambienteAlmacen.codAmbiente}" styleClass="inputText" >
                                    <f:selectItem itemValue="0" itemLabel="-NINGUNO-" />
                                    <f:selectItems value="#{ManagedIngresoAlmacen.ambienteAlmacenList}"  />
                                    <a4j:support event="onchange" reRender="estante" action="#{ManagedIngresoAlmacen.ambienteAlmacen_change}" />
                                </h:selectOneMenu>
                                <h:outputText value="Pasillo:" styleClass="outputText1" />
                                <h:selectOneMenu value="#{ManagedIngresoAlmacen.ingresosAlmacenDetalleEstado.estanteAlmacen.codEstante}" styleClass="inputText" id="estante" >
                                    <f:selectItem itemValue="0" itemLabel="-NINGUNO-" />
                                    <f:selectItems value="#{ManagedIngresoAlmacen.estanteAlmacenList}"  />
                                </h:selectOneMenu>
                                <%--h:outputText value="" />
                                <h:outputText value="" /--%>

                                <h:outputText value="Estante:" styleClass="outputText1" />
                                <h:inputText value="#{ManagedIngresoAlmacen.ingresosAlmacenDetalleEstado.fila}" styleClass="inputText" size="10"  ><!--onkeypress="valNum(event);"-->
                                </h:inputText>

                                <h:outputText value="Ubicacion:" styleClass="outputText1" />
                                <h:inputText value="#{ManagedIngresoAlmacen.ingresosAlmacenDetalleEstado.columna}" styleClass="inputText" size="10"  ><!--onkeypress="valNum(event);"-->
                                </h:inputText>


                            </h:panelGrid>
                            <br/>

                            <h:selectBooleanCheckbox id="seleccionar_todo"  onclick="seleccionarTodo('form3:dataIngresoAlmacenDetalleEstado');"/>
                            <a4j:commandButton action="#{ManagedIngresoAlmacen.repetirValoresIngresoAlmacenDetalleEstado_action}" oncomplete="if(#{ManagedIngresoAlmacen.mensaje!=''}){alert('#{ManagedIngresoAlmacen.mensaje}')}" reRender="dataIngresoAlmacenDetalleEstado,contenidoIngresoAlmacenDetalle" value="Repetir Valores" styleClass="btn" timeout="7200" />
                            <br/>

                            <rich:dataTable value="#{ManagedIngresoAlmacen.ingresosAlmacenDetalle.ingresosAlmacenDetalleEstadoList}" var="data"
                                            id="dataIngresoAlmacenDetalleEstado"
                                            onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                            onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                            headerClass="headerClassACliente"
                                            width="100%" >

                                <h:column>
                                    <f:facet name="header">
                                        <h:outputText value=""  />
                                    </f:facet>
                                    <h:selectBooleanCheckbox value="#{data.checked}"  />
                                </h:column>
                                <h:column>
                                    <f:facet name="header">
                                        <h:outputText value=""  />
                                    </f:facet>
                                    <h:outputText  value="#{data.etiqueta}"/>
                                </h:column>

                                <h:column>
                                    <f:facet name="header">
                                        <h:outputText value="Estado Item"  />
                                    </f:facet>
                                    <h:outputText  value="#{data.estadosMaterial.nombreEstadoMaterial}"/>
                                </h:column>

                                <h:column>
                                    <f:facet name="header">
                                        <h:outputText value="Empaque"  />
                                    </f:facet>
                                    <h:outputText value="#{data.empaqueSecundarioExterno.nombreEmpaqueSecundarioExterno}"  />
                                </h:column>

                                <h:column>
                                    <f:facet name="header">
                                        <h:outputText value="Cantidad Parcial"  />
                                    </f:facet>
                                    <h:inputText value="#{data.cantidadParcial}" styleClass="inputText" size="5"  onkeyup="hallaCantidadIngresoFisico()"  />
                                </h:column>
                                <h:column>
                                    <f:facet name="header">
                                        <h:outputText value="Tara"  />
                                    </f:facet>
                                    <h:inputText value="#{data.tara}" styleClass="inputText" size="5"  onkeyup="hallaCantidadIngresoFisico()"  />
                                </h:column>
                                <%--h:column>
                                    <f:facet name="header">
                                        <h:outputText value="Fecha Manufactura"  />
                                    </f:facet>
                                    <h:inputText value="#{data.fechaManufactura}" styleClass="inputText" size="10"  onblur="valFecha(this);" onkeyup="hallaCantidadIngresoFisico()"  >
                                        <f:convertDateTime pattern="dd/MM/yyyy" />
                                    </h:inputText>

                                        </h:column--%>
                                <h:column>
                                    <f:facet name="header">
                                        <h:outputText value="Lote Proveedor"  />
                                    </f:facet>
                                    <h:inputText value="#{data.loteMaterialProveedor}"  styleClass="inputText" />
                                </h:column>



                                <%--h:column>
                                    <f:facet name="header">
                                        <h:outputText value="Estante"  />
                                    </f:facet>
                                    <h:outputText value="#{data.estanteAlmacen.nombreEstante}"  />
                                </h:column--%>
                                <h:column>
                                    <f:facet name="header">
                                        <h:outputText value="Fecha Vencimiento"  />
                                    </f:facet>
                                    <h:inputText value="#{data.fechaVencimiento}" styleClass="inputText" size="10"  onblur="valFecha(this);" onkeyup="hallaCantidadIngresoFisico()"  >
                                        <f:convertDateTime pattern="dd/MM/yyyy" />
                                    </h:inputText>
                                </h:column>
                                <h:column>
                                    <f:facet name="header">
                                        <h:outputText value="Fecha Reanalisis"  />
                                    </f:facet>
                                    <h:inputText value="#{data.fechaReanalisis}" styleClass="inputText" size="10"  onblur="valFecha(this);" onkeyup="hallaCantidadIngresoFisico()"  >
                                        <f:convertDateTime pattern="dd/MM/yyyy" />
                                    </h:inputText>
                                </h:column>
                                <h:column>
                                    <f:facet name="header">
                                        <h:outputText value="Pasillo"  />
                                    </f:facet>
                                    <h:outputText value="#{data.estanteAlmacen.nombreEstante}"  />
                                </h:column>
                                <h:column>
                                    <f:facet name="header">
                                        <h:outputText value="Estante"   />
                                    </f:facet>
                                    <h:inputText value="#{data.fila}" styleClass="inputText" size="2" />
                                </h:column>
                                <h:column>
                                    <f:facet name="header">
                                        <h:outputText value="Ubicacion"  />
                                    </f:facet>
                                    <h:inputText value="#{data.columna}"  styleClass="inputText" size="2" />
                                </h:column>

                                <h:column>
                                    <f:facet name="header">
                                        <h:outputText value="Obervaciones"  />
                                    </f:facet>
                                    <h:inputText value="#{data.observaciones}" size="20" styleClass="inputText"  />
                                </h:column>

                            </rich:dataTable>
                        </h:panelGroup>
                    </div>

                    <div align="center">
                        <a4j:commandButton styleClass="btn" value="Registrar"
                                           oncomplete="Richfaces.hideModalPanel('panelDetalleItem')"
                                           action="#{ManagedIngresoAlmacen.guardarDetalleItem_action}"
                                           reRender="dataIngresoAlmacenDetalle"
                                           timeout="7200"
                                           /><%-- oncomplete="if(#{ManagedIngresoAlmacen.mensaje!=''}){alert('#{ManagedIngresoAlmacen.mensaje}')}else{Richfaces.hideModalPanel('panelDetalleItem')}" --%>
                        <a4j:commandButton styleClass="btn" value="Cancelar"
                                           oncomplete="javascript:Richfaces.hideModalPanel('panelDetalleItem')"
                                           action="#{ManagedIngresoAlmacen.cancelarDetalleItem_action}"
                                           reRender="dataIngresoAlmacenDetalle"
                                           timeout="7200"
                                           />

                    </div>

                </a4j:form>
            </rich:modalPanel>

        </body>
    </html>
</f:view>
