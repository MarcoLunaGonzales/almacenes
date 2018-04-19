<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib prefix="a4j" uri="http://richfaces.org/a4j"%>
<%@taglib prefix="rich" uri="http://richfaces.org/rich"%>
<f:view>

    <html>
        <head>
            <title>SISTEMA</title>
            <link rel="STYLESHEET" type="text/css" href="../css/ventas.css" />
            <link rel="STYLESHEET" type="text/css" href="../css/chosen.css" />
            <script type="text/javascript" src="../js/general.js" ></script>
            <script type="text/javascript" src="../js/chosen.js" ></script>

            <script type="text/javascript">
                function asignar(nametable) {

                    var count = 0;
                    var elements = document.getElementById(nametable);
                    var rowsElement = elements.rows;
                    for (var i = 1; i < rowsElement.length; i++) {
                        var cellsElement = rowsElement[i].cells;
                        var cel = cellsElement[0];
                        alert('hola');
                        if (cel.getElementsByTagName('input')[0].type == 'checkbox') {
                            if (cel.getElementsByTagName('input')[0].checked) {
                                count++;
                            }
                        }
                    }
                    if (count == 0) {
                        alert('No selecciono ningun Registro');
                        return false;
                    } else {
                        if (confirm('Desea Asignar como Area Raiz')) {
                            if (confirm('Esta seguro de Asignar como Area Raiz')) {
                                return true;
                            } else {
                                return false;
                            }
                        } else {
                            return false;
                        }

                    }

                }
                
                onerror = function () {
                    alert('existe un error al momento de cargar el registro, por favor recargue la pagina');
                }

                //final ale
                A4J.AJAX.onError = function (req, status, message) {
                    window.alert("Ocurrio un error: " + message);
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
        <body >
            <a4j:form id="form1">
                <div align="center">

                    <h:outputText value="#{ManagedRegistroSalidaTrazabilidad.cargarContenidoRegistroSalidaAlmacen}"/>
                    <h:outputText id="alertLote" style="font-size:22px;color:red" value="#{ManagedRegistroSalidaTrazabilidad.alertaSalidasLote}"  escape="false"  />
                    <br><br>

                    <div align="center" >
                        <h:outputText value="#{ManagedRegistroSalidaTrazabilidad.mensajeInyectables}" style="font-size:22px;color:red" />
                    </div>

                    <rich:panel headerClass="headerClassACliente">
                        <f:facet name="header">
                            <h:outputText value="Registro de Salidas a Almacén"  />
                        </f:facet>
                        <h:panelGrid columns="4" >
                            <h:outputText value="Nro Salida:" styleClass="outputTextBold" />
                            <h:outputText value="#{ManagedRegistroSalidaTrazabilidad.salidasAlmacen.nroSalidaAlmacen}" styleClass="outputText1" />

                            <h:outputText value="Area / Departamento:" styleClass="outputTextBold" />
                            <h:selectOneMenu  styleClass="chosen" 
                                              value="#{ManagedRegistroSalidaTrazabilidad.salidasAlmacen.areasEmpresa.codAreaEmpresa}" id="codAreaEmpresa" >
                                <f:selectItems value="#{ManagedRegistroSalidaTrazabilidad.areasEmpresaList}"  />
                            </h:selectOneMenu>

                            <h:outputText value="Tipo de Salida:" styleClass="outputTextBold" />
                            <h:selectOneMenu  id="tipoSalida" styleClass="inputText" value="#{ManagedRegistroSalidaTrazabilidad.salidasAlmacen.tiposSalidasAlmacen.codTipoSalidaAlmacen}" onchange="onChangeValue()">
                                <f:selectItem itemValue="14" itemLabel="Por Trazabilidad" />
                            </h:selectOneMenu>
                            <h:outputText value="Observaciones:" styleClass="outputTextBold" />
                            <h:inputTextarea  rows="5" cols="50" styleClass="outputText2" value="#{ManagedRegistroSalidaTrazabilidad.salidasAlmacen.obsSalidaAlmacen}" />
                        </h:panelGrid>
                    </rich:panel>

                    <h:outputText value="#{ManagedRegistroSalidaTrazabilidad.agregarItem_action}"></h:outputText>
                    <h:panelGrid>
                        <h:graphicImage url="../img/load.gif" id="progress" style="visibility:hidden" />
                    </h:panelGrid>
                    
                    <rich:panel id="panelAgregarItem" headerClass="headerClassACliente">
                        <f:facet name="header">
                            <h:outputText value="Detalle Item"/>                            
                        </f:facet>
                        <h:panelGroup id="contenidoAgregarItem">
                            <div align="center">
                                <h:panelGrid columns="4" id="contenidoIngresoAlmacenDetalle" headerClass="headerClassACliente" width="90%" style="border:1px solid #000000;" >
                                    <f:facet name="header">
                                        <h:outputText value="Buscar Item" />
                                    </f:facet>
                                    <h:outputText value="Capitulo:" styleClass="tituloCampo1" />
                                    <h:selectOneMenu value="#{ManagedRegistroSalidaTrazabilidad.buscarMaterial.grupos.capitulos.codCapitulo}" styleClass="inputText" >
                                        <f:selectItems value="#{ManagedRegistroSalidaTrazabilidad.capitulosList}"  />
                                        <a4j:support event="onchange" action="#{ManagedRegistroSalidaTrazabilidad.capitulos_change}" reRender="codGrupo" timeout="7200" />
                                    </h:selectOneMenu>

                                    <h:outputText value="Grupo:" styleClass="tituloCampo1" />
                                    <h:selectOneMenu value="#{ManagedRegistroSalidaTrazabilidad.buscarMaterial.grupos.codGrupo}" styleClass="inputText" id="codGrupo" >
                                        <f:selectItems value="#{ManagedRegistroSalidaTrazabilidad.gruposList}"  />
                                    </h:selectOneMenu>
                                    <h:outputText value="Item:" styleClass="tituloCampo1" />
                                    <h:inputText value="#{ManagedRegistroSalidaTrazabilidad.buscarMaterial.nombreMaterial}" styleClass="inputText" size="50"  />
                                    <a4j:commandButton value="Buscar" 
                                                       action="#{ManagedRegistroSalidaTrazabilidad.buscarMaterial_action}" 
                                                       styleClass="btn" reRender="dataBuscarMaterial"  timeout="7200" />
                                </h:panelGrid>
                                <br/>
                                <div style="overflow:auto;height:100px;width:90%;text-align:center;border: 1px solid #ccc">
                                    <rich:dataTable value="#{ManagedRegistroSalidaTrazabilidad.materialesBuscarList}" var="data"
                                                    id="dataBuscarMaterial" style="width:100%"
                                                    onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                                    onRowMouseOver="this.style.backgroundColor='#DDE3E4';"                                    
                                                    headerClass="headerClassACliente"
                                                    binding="#{ManagedRegistroSalidaTrazabilidad.materialesBuscarDataTable}" >


                                        <rich:column style="background-color: #{data.colorFila}">
                                            <f:facet name="header">
                                                <h:outputText value="Material"  />
                                            </f:facet>
                                            <a4j:commandLink  action="#{ManagedRegistroSalidaTrazabilidad.detallarCantidadSalida_action}" 
                                                              styleClass="#{data.colorFila}"  
                                                              reRender="nombreMaterialAgregar,detalleSalidaMaterial,dataBuscarMaterial" 
                                                              timeout="7200" >
                                                
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
                                        <h:outputText value="#{ManagedRegistroSalidaTrazabilidad.salidasAlmacenDetalleAgregar.materiales.nombreMaterial}" styleClass="outputText1" />
                                    </h:panelGroup>
                                </b>
                                <br/>


                                <h:panelGrid columns="1" 
                                             id="detalleSalidaMaterial" 
                                             headerClass="headerClassACliente" 
                                             style="width:100%" >
                                    <f:facet name="header" >
                                        <h:outputText value="Lotes Disponibles de Material" />
                                    </f:facet>
                                    <h:panelGrid columns="2">
                                        <rich:dataTable value="#{ManagedRegistroSalidaTrazabilidad.listLoteProveedor}" 
                                                        var="dataL"
                                                        id="dataLotes"
                                                        onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                                        onRowMouseOver="this.style.backgroundColor='#DDE3E4';"                                    
                                                        headerClass="headerClassACliente"
                                                        binding="#{ManagedRegistroSalidaTrazabilidad.lotesEncontrados}"
                                                        >


                                            <rich:column style="background-color: #{dataL.colorFila}">
                                                <f:facet name="header">
                                                    <h:outputText value="Lote Proveedor Origen" />
                                                </f:facet>
                                                <a4j:commandLink  action="#{ManagedRegistroSalidaTrazabilidad.verEtiquetasLote_action}" 
                                                                  styleClass="#{dataL.colorFila}"  
                                                                  reRender="etiquetas,dataLotes,loteDestino" 
                                                                  timeout="7200" >
                                                    <h:graphicImage url="../img/b.bmp"/>
                                                    <h:outputText  value="#{dataL.codLoteProveedor}" styleClass="outputText2" />
                                                </a4j:commandLink>
                                            </rich:column>


                                            <rich:column style="background-color: #{dataL.colorFila}" width="50px;">
                                                <f:facet name="header">
                                                    <h:outputText value="Cantidad Total Lote Proveedor"  />
                                                </f:facet>
                                                <h:outputText value="#{dataL.cantidad_total}" style="text-align:right;">
                                                </h:outputText>
                                            </rich:column>
                                            
                                            <rich:column style="background-color: #{dataL.colorFila};align-text:rigth;" >
                                                <f:facet name="header">
                                                    <h:outputText value="Cantidad Salida"  />
                                                </f:facet>
                                                <h:inputText value="#{dataL.cantidad_salida}"  styleClass="inputText"
                                                             id="cantidadSalida" converterMessage="Debe registrar un número"
                                                             rendered="#{dataL.colorFila ne ''}">
                                                    <f:validator validatorId="validatorDoubleRange"/>
                                                    <f:attribute name="minimum" value="0.01"/>
                                                    <f:attribute name="maximum" value="#{dataL.cantidad_total}"/>
                                                    <f:attribute name="disable" value="#{(empty param['form1:buttonGuardar'])}"/>
                                                </h:inputText>
                                                <br/>
                                                <h:message for="cantidadSalida" styleClass="message"/>
                                            </rich:column>

                                            <rich:column style="background-color: #{data.colorFila}">
                                                <f:facet name="header">
                                                    <h:outputText value="Unidad de Medida"  />
                                                </f:facet>
                                                <h:outputText value="#{dataL.unidadMedida.nombreUnidadMedida}"  />
                                            </rich:column>

                                        </rich:dataTable>
                                        <h:panelGrid columns="2">
                                            <h:outputText value="Lote Proveedor Destino" styleClass="tituloCampo1"></h:outputText>
                                            <h:panelGroup>
                                                <h:selectOneMenu value="#{ManagedRegistroSalidaTrazabilidad.lote_destino}" 
                                                                 id="loteDestino" requiredMessage="Debe seleccionar un lote proveedor destino"
                                                                 required="#{(not empty param['form1:buttonGuardar'])}"
                                                                 styleClass="inputText">
                                                    <f:selectItem  itemLabel="-NINGUNO-"></f:selectItem>
                                                    <f:selectItems value="#{ManagedRegistroSalidaTrazabilidad.listLoteProveedorDestino}"
                                                                   ></f:selectItems>
                                                </h:selectOneMenu>
                                                <h:message for="loteDestino" styleClass="message"/>
                                            </h:panelGroup>
                                        </h:panelGrid>
                                    </h:panelGrid>
                                </h:panelGrid>
                                <h:panelGrid id="etiquetas">
                                    <rich:dataTable value="#{ManagedRegistroSalidaTrazabilidad.listIngresosDetalleEstado}" 
                                                    var="dataE"
                                                    id="dataEtiquetas"
                                                    onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                                    onRowMouseOver="this.style.backgroundColor='#DDE3E4';"                                    
                                                    headerClass="headerClassACliente">


                                        <rich:column style="background-color: #{dataE.colorFila}">
                                            <f:facet name="header">
                                                <h:outputText value="Lote Proveedor"  />
                                            </f:facet>

                                            <h:outputText  value="#{dataE.loteMaterialProveedor}" styleClass="outputText2" />

                                        </rich:column>
                                        <rich:column style="background-color: #{dataE.colorFila}">
                                            <f:facet name="header">
                                                <h:outputText value="Nro. Ingreso"  />
                                            </f:facet>

                                            <h:outputText  value="#{dataE.ingresosAlmacen.nroIngresoAlmacen}" styleClass="outputText2" />

                                        </rich:column>
                                        <rich:column style="background-color: #{dataE.colorFila}">
                                            <f:facet name="header">
                                                <h:outputText value="Estado de Material"  />
                                            </f:facet>

                                            <h:outputText  value="#{dataE.estadosMaterial.nombreEstadoMaterial}" styleClass="outputText2" />

                                        </rich:column>
                                        <rich:column style="background-color: #{dataE.colorFila}">
                                            <f:facet name="header">
                                                <h:outputText value="Empaque Secundario"  />
                                            </f:facet>

                                            <h:outputText  value="#{dataE.empaqueSecundarioExterno.nombreEmpaqueSecundarioExterno}" styleClass="outputText2" />

                                        </rich:column>
                                        <rich:column style="background-color: #{dataE.colorFila}">
                                            <f:facet name="header">
                                                <h:outputText value="Etiqueta"  />
                                            </f:facet>

                                            <h:outputText  value="#{dataE.etiqueta}" styleClass="outputText2" />

                                        </rich:column>
                                        <rich:column style="background-color: #{data.colorFila}">
                                            <f:facet name="header">
                                                <h:outputText value="Fecha de Vencimiento"  />
                                            </f:facet>
                                            <h:outputText value="#{dataE.fechaVencimiento}">
                                                <f:convertDateTime pattern="MM/yyyy"/>
                                            </h:outputText>
                                        </rich:column>
                                        <rich:column style="background-color: #{data.colorFila}">
                                            <f:facet name="header">
                                                <h:outputText value="Cantidad Parcial"  />
                                            </f:facet>
                                            <h:outputText value="#{dataE.cantidadParcial}"  />
                                        </rich:column>
                                        <rich:column style="background-color: #{data.colorFila}">
                                            <f:facet name="header">
                                                <h:outputText value="Cantidad Restante"  />
                                            </f:facet>
                                            <h:outputText value="#{dataE.cantidadRestante}"  />
                                        </rich:column>
                                        <rich:column style="background-color: #{data.colorFila}">
                                            <f:facet name="header">
                                                <h:outputText value="Unidad de Medida"  />
                                            </f:facet>
                                            <h:outputText value="#{dataE.materiales.unidadesMedida.nombreUnidadMedida}"  />
                                        </rich:column>
                                    </rich:dataTable>
                                </h:panelGrid>
                            </h:panelGroup>
                        </div>



                    </rich:panel>
                    <div align="center">
                        <a4j:commandButton value="Generar Salida Trazabilidad" 
                                           styleClass="btn" 
                                           id="buttonGuardar" 
                                           reRender="detalleSalidaMaterial"
                                           action="#{ManagedRegistroSalidaTrazabilidad.guardarSalidaAlmacen_action}"
                                           onclick="if(confirm('Esta seguro de realizar la Salida de Almacen?')==false){return false;}"
                                           oncomplete="if('#{facesContext.maximumSeverity}'.length==0){mostrarMensajeTransaccionEvento(function(){window.location.reload();})}" 
                                           timeout="7200"
                                           />
                    </div>
                    <script type="text/javascript">
                        cargarChosen();
                    </script>
                    
                </a4j:form>
                <a4j:include viewId="/panelProgreso.jsp" />
                <a4j:include viewId="/message.jsp" />
        </body>
    </html>

</f:view>

