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
            <style type="text/css">
                .headerClassACliente td
                {
                    color: black !important;
                }
                .sinCosto
                {
                    color: red;
                    font-weight: bold;
                }
            </style>
            <script type="text/javascript">
                function validarRegistroSolicitudDevolucion()
                {
                    if(parseInt(document.getElementById("form1:codAreaSolicitudDestino").value) <= 0)
                    {
                        alert('Debe seleccionar el area de solicitud');
                        document.getElementById("form1:codAreaSolicitudDestino").focus();
                        return false;
                    }
                    var cantidadDatosConregistro=0;
                    var detalleSolicitud = document.getElementById("form1:dataDevolucionesDetalleAlmacen").getElementsByTagName("tbody")[0];
                    for(var index = 0; index < detalleSolicitud.rows.length ; index++)
                    {
                        cantidadDatosConregistro += (parseFloat(detalleSolicitud.rows[index].cells[1].getElementsByTagName("input")[0].value) > 0 ? 1:0);
                    }
                    if(cantidadDatosConregistro == 0)
                    {
                        alert('Debe detallar al menos un item para la solicitud');
                        return false;
                    }
                    return true;
                }
            </script>
        </head>
        <body >
            <a4j:form id="form1">
                <div align="center">
                    <h:outputText value="#{ManagedSolicitudDevoluciones.cargarAgregarSolDevFrvAspiradora}"/>
                    <h:outputText styleClass="outputTextTitulo"  value="Agregar Solicitud de devolucion de frv residual" />
                    <br><br>
                        <rich:panel style="width:80%"  headerClass="headerClassACliente">
                            <f:facet name="header">
                                <h:outputText value="Registro de Devolucion a Almacen"  />
                            </f:facet>
                             <h:panelGrid columns="2" >
                                 <h:outputText value="Area Solicitud:"  styleClass="outputText2" />
                                <h:selectOneMenu id="codAreaSolicitudDestino" value="#{ManagedSolicitudDevoluciones.devolucionFrvAspiradora.salidasAlmacen.areasEmpresa.codAreaEmpresa}"
                                                 styleClass="inputText">
                                    <f:selectItem itemValue="0" itemLabel="--Seleccione una opción--" />
                                    <f:selectItems value="#{ManagedSolicitudDevoluciones.areasEmpresaSelectList}"/>
                                    <a4j:support event="onchange" action="#{ManagedSolicitudDevoluciones.codAreaEmpresaFrvResidual_change}"
                                                 reRender="dataDevolucionesDetalleAlmacen"/>
                                </h:selectOneMenu>
                                <h:outputText value="Observacion de la solicitud:" styleClass="outputText2" />
                                <h:inputTextarea value="#{ManagedSolicitudDevoluciones.devolucionFrvAspiradora.obsDevolucion}" styleClass="outputText1" rows="3" style="width:150px"/>
                            </h:panelGrid>
                            <rich:dataTable value="#{ManagedSolicitudDevoluciones.devolucionFrvAspiradora.devolucionesDetalleList}"
                                            var="detalle"
                                            id="dataDevolucionesDetalleAlmacen"
                                            onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                            onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                            headerClass="headerClassACliente">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column>
                                            <h:outputText value="Material"/>
                                        </rich:column>
                                        <rich:column>
                                            <h:outputText value="Cantidad"/>
                                        </rich:column>
                                        <rich:column>
                                            <h:outputText value="Unidad de Medida"/>
                                        </rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                    <rich:column>
                                        <h:outputText value="#{detalle.materiales.nombreMaterial}"/>
                                    </rich:column>
                                    <rich:column>
                                        <h:inputText style="text-align:right" value="#{detalle.cantidadDevueltaFallados}" styleClass="inputText"/>
                                    </rich:column>
                                    <rich:column>
                                        <h:outputText value="#{detalle.unidadesMedida.abreviatura}"/>
                                    </rich:column>
                            </rich:dataTable>
                            <br/>
                            <a4j:commandButton value="Guardar" styleClass="btn"
                                               onclick="if(!validarRegistroSolicitudDevolucion()){return false}"
                                                action="#{ManagedSolicitudDevoluciones.guardarSolicitudDevolucionFrvAspiradora}"
                                                oncomplete="if(#{ManagedSolicitudDevoluciones.mensaje != '1'}){alert('#{ManagedSolicitudDevoluciones.mensaje}')}else{alert('la solicitud se registro exitosamente');location='navegadorSolicitudDevoluciones.jsf'}"/>
                                                
                             <a4j:commandButton value="Cancelar" styleClass="btn"
                                                oncomplete="window.location.href='navegadorSolicitudDevoluciones.jsf?data='+(new Date()).getTime().toString()"
                                                />
                    
                    </rich:panel>



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
        </body>
    </html>

</f:view>

