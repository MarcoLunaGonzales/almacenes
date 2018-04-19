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
            <style>
                .inputDisable
                {
                    border-color:white;
                    background-color:white;
                }
            </style>
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
                function changeAmbiente()
                {

                    var tabla = document.getElementById("form2:dataIngresosAlmacen");
                    var selectAmbiente = document.getElementById("form2:codAmbiente1");
                    var selectEstante = document.getElementById("form2:codEstante1");

                    for (var i = 1; i < tabla.rows.length; i++)
                    {
                        if (tabla.rows[i].cells[0].getElementsByTagName("input")[0].checked)
                        {
                            tabla.rows[i].cells[3].getElementsByTagName("span")[0].innerHTML = (selectAmbiente.options[selectAmbiente.selectedIndex].innerHTML);
                            tabla.rows[i].cells[3].getElementsByTagName("input")[0].value = (selectAmbiente.value);
                            tabla.rows[i].cells[4].getElementsByTagName("span")[0].innerHTML = (selectEstante.options[selectEstante.selectedIndex].innerHTML);
                            tabla.rows[i].cells[4].getElementsByTagName("input")[0].value = (selectEstante.value);
                            tabla.rows[i].cells[5].getElementsByTagName("input")[0].value = (document.getElementById("form2:fila1").value);
                            tabla.rows[i].cells[6].getElementsByTagName("input")[0].value = (document.getElementById("form2:columna1").value);
                        }
                    }
                }
                function verProgreso()
                {
                    document.getElementById("buttonsRegister").style.visibility = 'hidden';
                    document.getElementById('form2:progress').style.visibility = 'visible';
                }
                function ocultarProgreso()
                {
                    document.getElementById("buttonsRegister").style.visibility = 'visible';
                    document.getElementById('form2:progress').style.visibility = 'hidden';
                }
                function validarRegistro()
                {
                    var codEstante = document.getElementById("form2:codEstante");
                    if (codEstante != null)
                    {
                        if (parseInt(codEstante.value) > 0)
                        {
                            return true;
                        }
                        else
                        {
                            alert('No Selecciono ningun pasillo');
                            return false;
                        }
                    }
                    return true;
                }
            </script>
        </head>
        <body>
            <a4j:form id="form2">
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
                            <h:outputText value="Mover" styleClass="outputText2" style="font-weight:bold" />
                            <h:outputText value="::" styleClass="outputText2" style="font-weight:bold" />
                            <h:selectOneMenu value="#{ManagedCambioUbicacionIngresos.tipoMovimiento}" styleClass="inputText" style="width:250px" >
                                <f:selectItem itemValue='1' itemLabel="TODOS"/>
                                <f:selectItem itemValue='2' itemLabel="POR INGRESO"/>
                                <a4j:support action="#{ManagedCambioUbicacionIngresos.onchangeTipoModificacionIngresos}" reRender="codMOdal" event="onchange" />
                            </h:selectOneMenu>
                            <h:outputText />

                        </h:panelGrid>
                    </div>
                </rich:panel>
                <h:panelGroup  id="codMOdal" >
                    <rich:panel headerClass="headerClassACliente" style="margin-top:1em" rendered="#{ManagedCambioUbicacionIngresos.tipoMovimiento==2}"  >
                        <f:facet name="header" >
                            <h:outputText value="<center>Nueva Ubicación por Ingreso</center>" escape="false"/>
                        </f:facet>
                    <center>
                        <h:panelGrid columns="12"  rendered="false">
                            <h:outputText value="Ambiente" styleClass="outputText2" style="font-weight:bold" />
                            <h:outputText value="::" styleClass="outputText2" style="font-weight:bold" />
                            <h:selectOneMenu value="#{ManagedCambioUbicacionIngresos.ingresosAlmacenDetalleEstadoNuevaUbication.estanteAlmacen.ambienteAlmacen.codAmbiente}" styleClass="inputText" style="width:250px" id="codAmbiente1" >
                                <f:selectItem itemValue="0" itemLabel="NINGUNO" />
                                <f:selectItems value="#{ManagedCambioUbicacionIngresos.ambientesList}" />
                                <a4j:support action="#{ManagedCambioUbicacionIngresos.onchangeAmbienteEditar_action}" reRender="codEstante1" event="onchange" oncomplete="changeAmbiente()"/>
                            </h:selectOneMenu>
                            <h:outputText value="Pasillo" styleClass="outputText2" style="font-weight:bold" />
                            <h:outputText value="::" styleClass="outputText2" style="font-weight:bold" />
                            <h:selectOneMenu value="#{ManagedCambioUbicacionIngresos.ingresosAlmacenDetalleEstadoNuevaUbication.estanteAlmacen.codEstante}" id="codEstante1" onchange="changeAmbiente()" styleClass="inputText" >
                                <f:selectItem itemValue="0" itemLabel="SIN UBICACION" />
                                <f:selectItems value="#{ManagedCambioUbicacionIngresos.estantesList}" />
                            </h:selectOneMenu>
                            <h:outputText value="Estante" styleClass="outputText2" style="font-weight:bold" />
                            <h:outputText value="::" styleClass="outputText2" style="font-weight:bold" />
                            <h:inputText value="#{ManagedCambioUbicacionIngresos.ingresosAlmacenDetalleEstadoNuevaUbication.fila}" styleClass="inputText" id="fila1" onkeyup="changeAmbiente()"/>
                            <h:outputText value="Ubicacion" styleClass="outputText2" style="font-weight:bold" />
                            <h:outputText value="::" styleClass="outputText2" style="font-weight:bold" />
                            <h:inputText value="#{ManagedCambioUbicacionIngresos.ingresosAlmacenDetalleEstadoNuevaUbication.columna}" styleClass="inputText" id="columna1" onkeyup="changeAmbiente()"/>
                        </h:panelGrid>
                        <rich:dataTable value="#{ManagedCambioUbicacionIngresos.ingresosAlmacenDetalleEstadoModificarUbicacion}"
                                        var="data" id="dataIngresosAlmacen" style="margin-top:1em !important"
                                        onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                        onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                        headerClass="headerClassACliente" width="100%">
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="" />
                                </f:facet>
                                <h:selectBooleanCheckbox value="#{data.checked}" />
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="Nro Ingreso"  />
                                </f:facet>
                                <h:outputText value="#{data.ingresosAlmacen.nroIngresoAlmacen}" />
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="Lote Proveedor"  />
                                </f:facet>
                                <h:outputText value="#{data.loteMaterialProveedor}" />
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="Etiqueta"  />
                                </f:facet>
                                <h:outputText value="#{data.etiqueta}" />
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="Ambiente"  />
                                </f:facet>
                                <h:selectOneMenu value="#{data.estanteAlmacen.ambienteAlmacen.codAmbiente}">
                                    <f:selectItem itemValue='0' itemLabel="SIN AMBIENTE"/>
                                    <f:selectItems value="#{ManagedCambioUbicacionIngresos.ambientesList}"/>
                                    <a4j:support event="onchange" action="#{ManagedCambioUbicacionIngresos.codAmbienteIngresosAlmacenChange}"
                                                 reRender="dataIngresosAlmacen">
                                        <f:setPropertyActionListener value="#{data}" target="#{ManagedCambioUbicacionIngresos.ingresosAlmacenDetalleEstadoSeleccionado}"/>
                                    </a4j:support>
                                </h:selectOneMenu>
                                
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="Pasillo"  />
                                </f:facet>
                                <h:selectOneMenu value="#{data.estanteAlmacen.codEstante}" id="codEstante1" onchange="changeAmbiente()" styleClass="inputText" >
                                    <f:selectItem itemValue="0" itemLabel="SIN UBICACION" />
                                    <f:selectItems value="#{data.estantesSelectList}" />
                                </h:selectOneMenu>
                            </h:column>

                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="Estante"  />
                                </f:facet>
                                <h:inputText value="#{data.fila}" styleClass="inputText" />
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="Ubicacion"  />
                                </f:facet>
                                <h:inputText value="#{data.columna}"  styleClass="inputText" />
                            </h:column>
                            <h:column>
                                <f:facet name="header">
                                    <h:outputText value="Cantidad"  />
                                </f:facet>
                                <h:outputText value="#{data.cantidadRestante}" >
                                    <f:convertNumber pattern="#####.##" locale="en"/>
                                </h:outputText>
                            </h:column>

                        </rich:dataTable>
                        <rich
                </center>
            </rich:panel>
            <rich:panel headerClass="headerClassACliente" style="margin-top:1em" rendered="#{ManagedCambioUbicacionIngresos.tipoMovimiento==1}"  >
                <f:facet name="header" >
                    <h:outputText value="<center>Nueva Ubicación General</center>" escape="false"/>
                </f:facet>
                <div align="center">
                    <h:panelGrid columns="3"  >
                        <h:outputText value="Ambiente" styleClass="outputText2" style="font-weight:bold" />
                        <h:outputText value="::" styleClass="outputText2" style="font-weight:bold" />
                        <h:selectOneMenu value="#{ManagedCambioUbicacionIngresos.ingresosAlmacenDetalleEstadoNuevaUbication.estanteAlmacen.ambienteAlmacen.codAmbiente}" styleClass="inputText" style="width:250px" >
                            <f:selectItem itemValue="0" itemLabel="NINGUNO" />
                            <f:selectItems value="#{ManagedCambioUbicacionIngresos.ambientesList}" />
                            <a4j:support action="#{ManagedCambioUbicacionIngresos.onchangeAmbienteEditar_action}" reRender="codEstante" event="onchange" />
                        </h:selectOneMenu>
                        <h:outputText value="Pasillo" styleClass="outputText2" style="font-weight:bold" />
                        <h:outputText value="::" styleClass="outputText2" style="font-weight:bold" />
                        <h:selectOneMenu value="#{ManagedCambioUbicacionIngresos.ingresosAlmacenDetalleEstadoNuevaUbication.estanteAlmacen.codEstante}" id="codEstante" styleClass="inputText" >
                            <f:selectItem itemValue="0" itemLabel="--Seleccione una opcion--"/>
                            <f:selectItems value="#{ManagedCambioUbicacionIngresos.estantesList}"/>
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
        <div align="center" style="margin-top:1em;visibility:visible" id="buttonsRegister" >
            <a4j:commandButton styleClass="btn" value="Guardar" reRender="dataIngresosAlmacen" timeout="7200"
                               action="#{ManagedCambioUbicacionIngresos.guardarCambioUbicacionIngresoAlmacen_action}"
                               oncomplete="if(#{ManagedCambioUbicacionIngresos.mensaje eq '1'}){alert('Se realizo el cambio de ubicación');window.location.href='navegadorCambioUbicacionIngresos.jsf?cancel='+(new Date()).getTime().toString();}else{alert('#{ManagedCambioUbicacionIngresos.mensaje}');ocultarProgreso();}"
                               onclick="if(!confirm('Esta seguro de guardar la informacion?')){return false;}else{if(validarRegistro()){verProgreso();}else{return false;}}"/>
            <input type="button" value="Cancelar" onclick="window.location.href = 'navegadorCambioUbicacionIngresos.jsf?cancel=' + (new Date()).getTime().toString();" class="btn"  />
        </div>
        <center>
            <h:graphicImage url="../img/load.gif" id="progress" style="visibility:hidden" />
        </center>
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

</body>
</html>
</f:view>
