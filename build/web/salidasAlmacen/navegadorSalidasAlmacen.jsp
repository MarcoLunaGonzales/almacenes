

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib prefix="a4j" uri="http://richfaces.org/a4j"%>
<%@taglib prefix="rich" uri="http://richfaces.org/rich"%>
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
                function getCodigo(cod_maquina, codigo) {
                    //  alert(codigo);
                    location = '../partes_maquinaria/navegador_partes_maquinaria.jsf?cod_maquina=' + cod_maquina + '&codigo=' + codigo;
                }

                function editarItem(nametable) {
                    var count = 0;
                    var elements = document.getElementById(nametable);
                    var rowsElement = elements.rows;
                    for (var i = 1; i < rowsElement.length; i++) {
                        var cellsElement = rowsElement[i].cells;
                        var cel = cellsElement[0];
                        if (cel.getElementsByTagName('input')[0].type == 'checkbox') {
                            if (cel.getElementsByTagName('input')[0].checked) {
                                count++;
                            }

                        }

                    }
                    if (count == 1) {
                        return true;
                    } else if (count == 0) {
                        alert('No escogio ningun registro');
                        return false;
                    }
                    else if (count > 1) {
                        alert('Solo puede escoger un registro');
                        return false;
                    }
                }


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
                function eliminar(nametable) {
                    var count1 = 0;
                    var elements1 = document.getElementById(nametable);
                    var rowsElement1 = elements1.rows;
                    //alert(rowsElement1.length);
                    for (var i = 1; i < rowsElement1.length; i++) {
                        var cellsElement1 = rowsElement1[i].cells;
                        var cel1 = cellsElement1[0];
                        if (cel1.getElementsByTagName('input').length > 0) {
                            if (cel1.getElementsByTagName('input')[0].type == 'checkbox') {
                                if (cel1.getElementsByTagName('input')[0].checked) {
                                    count1++;
                                }
                            }
                        }

                    }
                    //alert(count1);
                    if (count1 == 0) {
                        alert('No escogio ningun registro');
                        return false;
                    } else {

                        var count = 0;
                        var elements = document.getElementById(nametable);
                        var rowsElement = elements.rows;

                        for (var i = 0; i < rowsElement.length; i++) {
                            var cellsElement = rowsElement[i].cells;
                            var cel = cellsElement[0];
                            if (cel.getElementsByTagName('input').length > 0) {
                                if (cel.getElementsByTagName('input')[0].type == 'checkbox') {
                                    if (cel.getElementsByTagName('input')[0].checked) {
                                        count++;
                                    }
                                }
                            }

                        }
                        if (count == 0) {
                            //alert('No escogio ningun registro');
                            return false;
                        }
                        //var cantidadeliminar=document.getElementById('form1:cantidadeliminar');
                        //cantidadeliminar.value=count;
                        return true;

                    }
                }

                function validarSeleccion(nametable) {
                    var count = 0;
                    var elements = document.getElementById(nametable);
                    var rowsElement = elements.rows;
                    for (var i = 1; i < rowsElement.length; i++) {
                        var cellsElement = rowsElement[i].cells;
                        var cel = cellsElement[0];
                        if (cel.getElementsByTagName('input')[0].type == 'checkbox') {
                            if (cel.getElementsByTagName('input')[0].checked) {
                                count++;
                            }

                        }

                    }
                    if (count == 1) {
                        return true;
                    } else if (count == 0) {
                        alert('No escogio ningun registro');
                        return false;
                    }
                    else if (count > 1) {
                        alert('Solo puede escoger un registro');
                        return false;
                    }
                }
                function verManteminiento(cod_maquina, codigo) {
                    location = '../partes_maquinaria/navegador_partes_maquinaria.jsf?cod_maquina=' + cod_maquina + '&codigo=' + codigo;
                }
                function openPopup(url) {
                    window.open(url, 'DETALLE', 'top=50,left=200,width=800,height=500,scrollbars=1,resizable=1');
                }
                function openPopup1(f, url) {
                    //window.open(url,'DETALLE','top=50,left=200,width=800,height=600,scrollbars=1,resizable=1');
                    f.action = url;
                    f.submit();
                }
                function validarSalidasSeleccionadas() {
                    var cantSeleccionados = 0;

                    var inputs = document.getElementsByTagName('input');

                    for (var i = 0; i < inputs.length; i++) {
                        if (inputs[i].type.toLowerCase() == 'checkbox') {
                            if (inputs[i].checked) {
                                cantSeleccionados++;
                            }
                        }
                    }
                    if (cantSeleccionados == 1) {
                        return true;
                    }
                    else if (cantSeleccionados < 1) {
                        alert('Debe seleccionar un registro.');
                        return false;
                    }
                    else if (cantSeleccionados > 1) {
                        alert('Debe seleccionar solamente un registro.');
                        return false;
                    }

                }
            </script>
        </head>
        <body >
            <a4j:form id="form1" >
                <div align="center">
                    <h:outputText value="#{ManagedSalidaAlmacen.cargarContenidoSalidasAlmacen}"/>
                    <h:outputText styleClass="outputTextTitulo"  value="GESTIONAR SALIDAS ALMACÉN" />
                    <br>
                    <a4j:commandLink onclick="Richfaces.showModalPanel('panelBuscarSalidaAlmacen')">
                        <h:outputText value="Buscar" styleClass="outputText2" />
                        <h:graphicImage url="../img/buscar.png">
                        </h:graphicImage>
                    </a4j:commandLink>
                    <br>
                    <rich:dataTable value="#{ManagedSalidaAlmacen.salidasAlmacenList}" var="data"
                                    id="dataSalidasAlmacen"
                                    onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                    onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                    headerClass="headerClassACliente" >
                        <f:facet name="header">
                            <rich:columnGroup>
                                <rich:column>
                                    <h:outputText value="Nro. Salida"/>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Fecha Salida"/>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Nro. de Lote"/>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Nro. de O.T."/>
                                </rich:column>
                                <rich:column rendered="#{ManagedRegistroSalidaAlmacen.usuario.almacenesGlobal.codAlmacen ne '14'}">
                                    <h:outputText value="Producto"/>
                                </rich:column>
                                <rich:column rendered="#{ManagedRegistroSalidaAlmacen.usuario.almacenesGlobal.codAlmacen ne '14'}">
                                    <h:outputText value="Presentación"/>
                                </rich:column>
                                <rich:column rendered="#{ManagedRegistroSalidaAlmacen.usuario.almacenesGlobal.codAlmacen eq '14'}">
                                    <h:outputText value="Maquinaria"/>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Responsable Salida"/>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Area / Departamento"/>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Estado Salida"/>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Tipo Salida"/>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Observaciones"/>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Acciones"/>
                                </rich:column>
                            </rich:columnGroup>
                        </f:facet>
                        <rich:column styleClass ="#{data.colorFila}">
                            <h:outputText  value="#{data.nroSalidaAlmacen}"  />
                        </rich:column>
                        <rich:column styleClass ="#{data.colorFila}">
                            <h:outputText value="#{data.fechaSalidaAlmacen}">
                                <f:convertDateTime pattern="dd/MM/yyyy HH:mm" />
                            </h:outputText>
                        </rich:column>
                        <rich:column styleClass ="#{data.colorFila}">
                            <h:outputText value="#{data.codLoteProduccion}"  />
                        </rich:column>
                        <rich:column styleClass ="#{data.colorFila}">
                            <h:outputText value="#{data.ordenTrabajo}"  />
                        </rich:column>

                        <rich:column styleClass ="#{data.colorFila}" rendered="#{ManagedRegistroSalidaAlmacen.usuario.almacenesGlobal.codAlmacen ne '14'}" >
                            <h:outputText value="#{data.componentesProd.nombreProdSemiterminado}"  />
                        </rich:column>

                        <rich:column styleClass ="#{data.colorFila}" rendered="#{ManagedRegistroSalidaAlmacen.usuario.almacenesGlobal.codAlmacen ne '14'}">
                            <h:outputText value="#{data.presentacionesProducto.nombreProductoPresentacion}"  />
                        </rich:column>
                        
                        <rich:column styleClass ="#{data.colorFila}" rendered="#{ManagedRegistroSalidaAlmacen.usuario.almacenesGlobal.codAlmacen eq '14'}">
                            <h:outputText value="#{data.maquinaria.nombreMaquina}"  />
                        </rich:column>

                        <rich:column styleClass ="#{data.colorFila}">
                            <h:outputText value="#{data.personal.nombrePersonal}"  />
                        </rich:column>

                        <rich:column styleClass ="#{data.colorFila}">
                            <h:outputText value="#{data.areasEmpresa.nombreAreaEmpresa}"  />
                        </rich:column>

                        <rich:column styleClass ="#{data.colorFila}">
                            <h:outputText value="#{data.estadosSalidasAlmacen.nombreEstadoSalidaAlmacen}"  />
                            <h:outputText value="<br/><span style='background-color:#9EFAC7' >(#{data.salidaTransaccionSolicitud.estadoTransaccionSolicitud.nombreEstadoTransaccionSolicitud})</span>" escape="false" rendered="#{data.salidaTransaccionSolicitud.estadoTransaccionSolicitud.nombreEstadoTransaccionSolicitud !=''}"  />
                        </rich:column>
                        <rich:column styleClass ="#{data.colorFila}">
                            <h:outputText value="#{data.tiposSalidasAlmacen.nombreTipoSalidaAlmacen}"  />
                        </rich:column>
                        
                        <rich:column styleClass ="#{data.colorFila}">
                            <h:outputText value="#{data.obsSalidaAlmacen}"  />
                        </rich:column>
                                               
                        <rich:column styleClass ="#{data.colorFila}">
                            <rich:dropDownMenu >
                                <f:facet name="label">
                                    <h:outputText value="Acciones"/>
                                </f:facet>
                                <rich:menuItem submitMode="ajax" value="Imprimir Nota Salida"  
                                               rendered="#{ManagedSalidaAlmacen.usuario.almacenesGlobal.codAlmacen!='12' && ManagedSalidaAlmacen.usuario.almacenesGlobal.codAlmacen!='14'}">
                                    <a4j:support event="onclick" 
                                                 action="#{ManagedSalidaAlmacen.verReporteSalidaAlmacenAction}"
                                                 oncomplete="openPopup('reporteSalidaAlmacenJasper.jsf?reporte=reporteSalida.jasper&data='+(new Date()).getTime().toString());" >
                                        <f:setPropertyActionListener value="#{data}" target="#{ManagedSalidaAlmacen.salidasAlmacenSeleccion}"/>
                                    </a4j:support>  
                                </rich:menuItem>
                                <rich:menuItem submitMode="ajax" value="Imprimir Mantenimiento" 
                                               rendered="#{ManagedSalidaAlmacen.usuario.almacenesGlobal.codAlmacen=='14' || ManagedSalidaAlmacen.usuario.almacenesGlobal.codAlmacen=='4' }">
                                    <a4j:support event="onclick" 
                                                 action="#{ManagedSalidaAlmacen.verReporteSalidaAlmacenAction}"
                                                 oncomplete="openPopup('reporteSalidasAlmacenMantenimiento.jsf');" >
                                        <f:setPropertyActionListener value="#{data}" target="#{ManagedSalidaAlmacen.salidasAlmacenSeleccion}"/>
                                    </a4j:support>  
                                </rich:menuItem>
                                <rich:menuItem submitMode="ajax" value="Imprimir con Maquinaria" 
                                               >
                                    <a4j:support event="onclick" 
                                                 action="#{ManagedSalidaAlmacen.verReporteSalidaAlmacenAction}"
                                                 oncomplete="openPopup('reporteSalidaAlmacenJasper.jsf?reporte=reporteSalidaConMaquinaria.jasper');">
                                        <f:setPropertyActionListener value="#{data}" target="#{ManagedSalidaAlmacen.salidasAlmacenSeleccion}"/>
                                    </a4j:support>  
                                </rich:menuItem>
                                <rich:menuItem submitMode="ajax" value="Reporte de Histórico"  >
                                    <a4j:support event="onclick" 
                                                 action="#{ManagedSalidaAlmacen.verReporteSalidaAlmacenLog_action}" 
                                                 oncomplete="openPopup('reporteSalidaAlmacenLog.jsf');" >
                                        <f:setPropertyActionListener value="#{data}" target="#{ManagedSalidaAlmacen.salidaAlmacenGestionar}"/>
                                    </a4j:support>  
                                </rich:menuItem>
                                <rich:menuSeparator/>
                                <rich:menuItem submitMode="ajax" value="Editar"  
                                               rendered="#{ManagedSalidaAlmacen.administradorAlmacen 
                                                           and data.estadosSalidasAlmacen.codEstadoSalidaAlmacen ne 2 
                                                           and (ManagedSalidaAlmacen.permisoEditarDirectamente or 
                                                                    (data.fechaCreacionHoy and ManagedSalidaAlmacen.permisoEditarHoy)) }"
                                               >
                                    <a4j:support event="onclick" 
                                                 action="#{ManagedSalidaAlmacen.nuevaSalidaTransaccionSolicitudEditar_action}"
                                                 oncomplete="if(#{ManagedSalidaAlmacen.salidaVisible}){location='editarSalidaAlmacen.jsf';}else{alert('#{ManagedSalidaAlmacen.mensaje}');}" 
                                                   >
                                        <f:setPropertyActionListener value="#{data}" target="#{ManagedSalidaAlmacen.salidaAlmacenGestionar}"/>
                                    </a4j:support>  
                                </rich:menuItem>
                                
                                <rich:menuItem submitMode="ajax" value="Anular"  
                                               rendered="#{ManagedSalidaAlmacen.administradorAlmacen 
                                                           and data.estadosSalidasAlmacen.codEstadoSalidaAlmacen ne 2 
                                                           and (ManagedSalidaAlmacen.permisoAnularDirectamente 
                                                                    or (data.fechaCreacionHoy and ManagedSalidaAlmacen.permisoAnularHoy)) }"
                                               >
                                    <a4j:support event="onclick" action="#{ManagedSalidaAlmacen.nuevaSalidaTransaccionSolicitudAnular_action}" reRender="contenidoAprobarAnularSalida"
                                                 oncomplete="if(#{ManagedSalidaAlmacen.salidaEditable}){Richfaces.showModalPanel('panelAprobarTransaccionSolicitudAnular');}else{alert('#{ManagedSalidaAlmacen.mensaje}');}">
                                        <f:setPropertyActionListener value="#{data}" target="#{ManagedSalidaAlmacen.salidaAlmacenGestionar}"/>
                                    </a4j:support>  
                                </rich:menuItem>
                                <rich:menuSeparator/>
                                <rich:menuItem submitMode="ajax" value="Solicitar Editar" 
                                               rendered="#{(data.salidaTransaccionSolicitud.estadoTransaccionSolicitud.codEstadoTransaccionSolicitud eq 0 
                                                           or data.salidaTransaccionSolicitud.estadoTransaccionSolicitud.codEstadoTransaccionSolicitud eq 5
                                                           or data.salidaTransaccionSolicitud.estadoTransaccionSolicitud.codEstadoTransaccionSolicitud eq 6) 
                                                           and data.estadosSalidasAlmacen.codEstadoSalidaAlmacen ne 2 
                                                           and ManagedSalidaAlmacen.permisoSolicitarEditar 
                                                           and !(data.fechaCreacionHoy && ManagedSalidaAlmacen.permisoEditarHoy)}" 
                                               >
                                    <a4j:support event="onclick" action="#{ManagedSalidaAlmacen.nuevaSalidaTransaccionSolicitudEditar_action}" reRender="contenidoTransaccionSolicitudEditar"
                                                 oncomplete="if(#{ManagedSalidaAlmacen.salidaVisible}){Richfaces.showModalPanel('panelTransaccionSolicitudEditar');}else{alert('#{ManagedSalidaAlmacen.mensaje}');}"  >
                                        <f:setPropertyActionListener value="#{data}" target="#{ManagedSalidaAlmacen.salidaAlmacenGestionar}"/>
                                    </a4j:support>
                                </rich:menuItem>

                                <rich:menuItem submitMode="ajax" value="Solicitar Anular" 
                                               rendered="#{(data.salidaTransaccionSolicitud.estadoTransaccionSolicitud.codEstadoTransaccionSolicitud eq 0 
                                                                or data.salidaTransaccionSolicitud.estadoTransaccionSolicitud.codEstadoTransaccionSolicitud eq 5 
                                                                or data.salidaTransaccionSolicitud.estadoTransaccionSolicitud.codEstadoTransaccionSolicitud eq 6)
                                                            and ManagedSalidaAlmacen.permisoSolicitarAnular 
                                                            and data.estadosSalidasAlmacen.codEstadoSalidaAlmacen ne 2 }" 
                                               >
                                    <a4j:support event="onclick" action="#{ManagedSalidaAlmacen.nuevaSalidaTransaccionSolicitudAnular_action}" reRender="contenidoTransaccionSolicitudAnular"
                                                 oncomplete="if(#{ManagedSalidaAlmacen.salidaEditable}){Richfaces.showModalPanel('panelTransaccionSolicitudAnular');}else{alert('#{ManagedSalidaAlmacen.mensaje}');} "  >
                                        <f:setPropertyActionListener value="#{data}" target="#{ManagedSalidaAlmacen.salidaAlmacenGestionar}"/>
                                    </a4j:support>
                                </rich:menuItem>
                                
                                
                            </rich:dropDownMenu>                                
                        </rich:column>
                        <f:facet name="footer">
                            <rich:columnGroup>
                                <rich:column colspan="12">
                                    <center>
                                        <h:panelGrid columns="3"  width="50" id="controles">
                                            <a4j:commandLink  action="#{ManagedSalidaAlmacen.atras_action}" reRender="dataSalidasAlmacen,controles" timeout="7200"  rendered="#{ManagedSalidaAlmacen.begin!='1'}" >
                                                <h:outputText value="Anterior"/>
                                                <h:graphicImage url="../img/previous.gif"  style="border:0px solid red"   alt="PAGINA ANTERIOR"  />
                                            </a4j:commandLink>
                                            <h:outputText value="  "/>
                                            <a4j:commandLink  action="#{ManagedSalidaAlmacen.siguiente_action}" reRender="dataSalidasAlmacen,controles" timeout="7200"  rendered="#{ManagedSalidaAlmacen.cantidadfilas>='10'}">
                                                <h:graphicImage url="../img/next.gif"  style="border:0px solid red"  alt="PAGINA SIGUIENTE" />
                                                <h:outputText value="Siguiente"/>
                                            </a4j:commandLink>
                                        </h:panelGrid>
                                    </center>
                                </rich:column>
                            </rich:columnGroup>
                        </f:facet>
                    </rich:dataTable>
                    <br>
                    <h:panelGroup rendered="#{ManagedSalidaAlmacen.administradorAlmacen}">
                        <a4j:commandButton value="Generar Salida Almacen" styleClass="btn"  onclick="location='agregarSalidaAlmacen.jsf'" rendered="#{ManagedSalidaAlmacen.administradorAlmacen}"  />
                        <a4j:commandButton value="Modificar Fecha" styleClass = "btn" action="#{ManagedSalidaAlmacen.selecccionarSalidaFechaModificar}"
                                           oncomplete="Richfaces.showModalPanel('panelModificarFechaSalida');"  reRender="contenidoAgregarMaterial"
                                           onclick="if(!editarItem('form1:dataSalidasAlmacen')){return false;}"
                                           rendered="#{ManagedAccesoSistema.usuarioModuloBean.codUsuarioGlobal eq '820'}"
                                           />
                    </h:panelGroup>
                    
                    

                    
                    



                    <%--
                    <a4j:commandButton value="Generar Salida Almacen" styleClass="btn"  action="#{ManagedIngresoAlmacenOrdenCompra.generarIngresoAlmacen_action}"
                    reRender="contenidoFrecuenciaMantenimentoMaquinaria" oncomplete="if(#{ManagedIngresoAlmacenOrdenCompra.mensaje!=''}){alert('#{ManagedIngresoAlmacenOrdenCompra.mensaje}')}" />
                    --%>

                </div>


                <!--cerrando la conexion-->
                <h:outputText value="#{ManagedFrecuenciaMantenimientoMaquinaria.closeConnection}"  />

            </a4j:form>

            <rich:modalPanel id="panelModificarFechaSalida" minHeight="250"  minWidth="650"
                             height="250" width="650"
                             zindex="200"
                             headerClass="headerClassACliente"
                             resizeable="false" style="overflow :auto" >
                <f:facet name="header">
                    <h:outputText value="Agregar Material"/>
                </f:facet>
                <a4j:form id="form3">
                    <h:panelGroup id="contenidoAgregarMaterial">
                        <h:panelGrid columns="3">
                            <h:outputText value="Nro Salida" styleClass="outputText2" />
                            <h:outputText value="::" styleClass="outputText2" />
                            <h:outputText value="#{ManagedSalidaAlmacen.salidasAlmacenFecha.nroSalidaAlmacen}" styleClass="outputText2" />
                            <h:outputText value="Fecha" styleClass="outputText2" />
                            <h:outputText value="::" styleClass="outputText2" />
                            <rich:calendar value="#{ManagedSalidaAlmacen.salidasAlmacenFecha.fechaSalidaAlmacen}" styleClass="inputText"
                                           datePattern="dd/MM/yyyy"/>

                        </h:panelGrid>



                        <div align="center">
                            <a4j:commandButton styleClass="btn" value="Guardar"
                                               action="#{ManagedSalidaAlmacen.guardarCambiofechaSalida_action}"
                                               oncomplete="if(#{ManagedRegistroSalidaAlmacen.mensaje!=''}){alert('#{ManagedRegistroSalidaAlmacen.mensaje}')}else{javascript:Richfaces.hideModalPanel('panelModificarFechaSalida')}"
                                               reRender="dataSalidasAlmacen,controles" timeout="7200"
                                               />
                            <input type="button" value="Cancelar" onclick="javascript:Richfaces.hideModalPanel('panelModificarFechaSalida')" class="btn" />
                        </div>
                    </h:panelGroup>
                </a4j:form>
            </rich:modalPanel>
            <rich:modalPanel id="panelBuscarSalidaAlmacen"  minHeight="220"  minWidth="1000"
                             height="220" width="1000"
                             zindex="200"
                             headerClass="headerClassACliente"
                             resizeable="false" style="overflow :auto" >
                <f:facet name="header">
                    <h:outputText value="Buscar Salida Almacen"/>
                </f:facet>
                <a4j:form>
                    <h:panelGroup id="contenidoBuscarSalidaAlmacen">
                        <h:panelGrid columns="4">

                            <h:outputText value="Gestion:" styleClass="outputText2" />
                            <h:selectOneMenu value="#{ManagedSalidaAlmacen.salidasAlmacenBuscar.gestiones.codGestion}" styleClass="inputText" >
                                <f:selectItems value="#{ManagedSalidaAlmacen.gestionesList}"  />
                            </h:selectOneMenu>

                            <h:outputText value="Nro Salida:" styleClass="outputText2" />
                            <h:inputText value="#{ManagedSalidaAlmacen.salidasAlmacenBuscar.nroSalidaAlmacen}" styleClass="inputText" size="5" onkeypress="valNum(event);" />

                            <h:outputText value="Nro Lote:" styleClass="outputText2" />
                            <h:inputText value="#{ManagedSalidaAlmacen.salidasAlmacenBuscar.codLoteProduccion}" styleClass="inputText" size="5" onkeypress="valNum(event);" />

                            <h:outputText value="Tipo Salida:" styleClass="outputText2" />
                            <h:selectOneMenu value="#{ManagedSalidaAlmacen.salidasAlmacenBuscar.tiposSalidasAlmacen.codTipoSalidaAlmacen}" styleClass="inputText" >
                                <f:selectItems value="#{ManagedSalidaAlmacen.tiposSalidaAlmacenList}"  />
                            </h:selectOneMenu>




                            <h:outputText value="Fecha inicio Salida" styleClass="outputText2" />
                            <rich:calendar  datePattern="dd/MM/yyyy" styleClass="inputText" zindex="200" value="#{ManagedSalidaAlmacen.salidasAlmacenBuscar.fechaSalidaAlmacen}" id="fechaInicio" enableManualInput="true" oninputblur="if(this.value!=''){valFecha(this)}else{return true;}"  />

                            <h:outputText value="Estados Salida Almacen:" styleClass="outputText2" />
                            <h:selectOneMenu value="#{ManagedSalidaAlmacen.salidasAlmacenBuscar.estadosSalidasAlmacen.codEstadoSalidaAlmacen}" styleClass="inputText" >
                                <f:selectItems value="#{ManagedSalidaAlmacen.estadosSalidaAlmacenList}"  />
                            </h:selectOneMenu>

                            <h:outputText value="Area Destino:" styleClass="outputText2" />
                            <h:selectOneMenu value="#{ManagedSalidaAlmacen.salidasAlmacenBuscar.areasEmpresa.codAreaEmpresa}" styleClass="inputText" >
                                <f:selectItems value="#{ManagedSalidaAlmacen.areasEmpresalist}"  />
                            </h:selectOneMenu>

                            <h:outputText value="Producto:" styleClass="outputText2" />
                            <h:selectOneMenu value="#{ManagedSalidaAlmacen.salidasAlmacenBuscar.componentesProd.codCompprod}" styleClass="inputText" >
                                <f:selectItems value="#{ManagedSalidaAlmacen.componentesProdList}"  />
                            </h:selectOneMenu>

                            <h:outputText value="Orden de Trabajo:" styleClass="outputText2" />
                            <h:inputText value="#{ManagedSalidaAlmacen.salidasAlmacenBuscar.ordenTrabajo}" styleClass="inputText" size="5" onkeypress="valNum(event);" />

                        </h:panelGrid>
                        <div align="center">
                            <a4j:commandButton styleClass="btn" value="Buscar" onclick="javascript:Richfaces.hideModalPanel('panelBuscarSalidaAlmacen');"
                                               action="#{ManagedSalidaAlmacen.buscarSalidaAlmacen_action}"
                                               reRender="dataSalidasAlmacen,controles"
                                               />
                            <input type="button" value="Cancelar" onclick="javascript:Richfaces.hideModalPanel('panelBuscarSalidaAlmacen')" class="btn" />
                        </div>
                    </h:panelGroup>
                </a4j:form>
            </rich:modalPanel>

            <rich:modalPanel id="panelTransaccionSolicitudEditar"  minHeight="240"  minWidth="700"
                             height="240" width="700"
                             zindex="200"
                             headerClass="headerClassACliente"
                             resizeable="false" style="overflow :auto">
                <f:facet name="header">
                    <h:outputText value="<center>SOLICITUD PARA EDITAR EL REGISTRO DE SALIDA</center>" escape="false"/>
                </f:facet>
                <a4j:form>
                    <h:panelGroup id="contenidoTransaccionSolicitudEditar">
                        <div align="center">
                            <h:panelGrid columns="4">
                                <h:outputText value="Nro. Salida:" styleClass="outputText1Subtitle" />
                                <h:outputText value="#{ManagedSalidaAlmacen.salidaAlmacenGestionar.nroSalidaAlmacen}" styleClass="outputText2" style="width:50px" />
                                <h:outputText value="Fecha Salida:" styleClass="outputText1Subtitle" />
                                <h:outputText value="#{ManagedSalidaAlmacen.salidaAlmacenGestionar.fechaSalidaAlmacen}" styleClass="outputText2">
                                    <f:convertDateTime pattern="dd/MM/yyyy HH:mm" />
                                </h:outputText>

                                <h:outputText value="Nro. Lote:" styleClass="outputText1Subtitle" />
                                <h:outputText value="#{ManagedSalidaAlmacen.salidaAlmacenGestionar.codLoteProduccion}" styleClass="outputText2" />
                                <h:outputText value="Producto:" styleClass="outputText1Subtitle" />
                                <h:outputText value="#{ManagedSalidaAlmacen.salidaAlmacenGestionar.componentesProd.nombreProdSemiterminado}" styleClass="outputText2" />

                                <h:outputText value="Presentación:" styleClass="outputText1Subtitle" />
                                <h:outputText value="#{ManagedSalidaAlmacen.salidaAlmacenGestionar.presentacionesProducto.nombreProductoPresentacion}" styleClass="outputText2" />
                                <h:outputText value="Tipo Salida:" styleClass="outputText1Subtitle" />
                                <h:outputText value="#{ManagedSalidaAlmacen.salidaAlmacenGestionar.tiposSalidasAlmacen.nombreTipoSalidaAlmacen}" styleClass="outputText2" />
                            </h:panelGrid> 
                            <br/>
                            <h:panelGroup style="width:100%">
                                <h:outputText value="Motivo/Observación:" styleClass="outputText1Subtitle" />
                                <h:inputTextarea value="#{ManagedSalidaAlmacen.salidaTransaccionSolicitudGestionar.observacionSolicitante}" styleClass="inputText" style="width:100%" rows="3" />
                            </h:panelGroup>
                            <br/>
                        
                            <a4j:commandButton styleClass="btn" value="Registrar Solicitud"
                                               oncomplete="mostrarMensajeTransaccionEvento(function(){Richfaces.hideModalPanel('panelTransaccionSolicitudEditar');})"
                                               reRender="dataSalidasAlmacen"
                                               action="#{ManagedSalidaAlmacen.guardarSolicitudEditarSalidaAlmacen_action}" >
                                <f:param name="codEstadoTransaccionSolicitud" value="2" />
                            </a4j:commandButton>
                            <input type="button" value="Cancelar" onclick="javascript:Richfaces.hideModalPanel('panelTransaccionSolicitudEditar')" class="btn" />
                        </div>
                    </h:panelGroup>
                </a4j:form>
            </rich:modalPanel>    

                        
            <rich:modalPanel id="panelTransaccionSolicitudAnular"  minHeight="240"  minWidth="700"
                             height="240" width="700"
                             zindex="200"
                             headerClass="headerClassACliente"
                             resizeable="false" style="overflow :auto">
                <f:facet name="header">
                    <h:outputText value="<center>SOLICITUD PARA ANULAR EL REGISTRO DE SALIDA</center>" escape="false"/>
                </f:facet>
                <a4j:form>
                    <h:panelGroup id="contenidoTransaccionSolicitudAnular">
                        <div align="center">
                            <h:panelGrid columns="4">
                                <h:outputText value="Nro. Salida:" styleClass="outputText1Subtitle" />
                                <h:outputText value="#{ManagedSalidaAlmacen.salidaAlmacenGestionar.nroSalidaAlmacen}" styleClass="outputText2" style="width:50px" />
                                <h:outputText value="Fecha Salida:" styleClass="outputText1Subtitle" />
                                <h:outputText value="#{ManagedSalidaAlmacen.salidaAlmacenGestionar.fechaSalidaAlmacen}" styleClass="outputText2">
                                    <f:convertDateTime pattern="dd/MM/yyyy HH:mm" />
                                </h:outputText>

                                <h:outputText value="Nro. Lote:" styleClass="outputText1Subtitle" />
                                <h:outputText value="#{ManagedSalidaAlmacen.salidaAlmacenGestionar.codLoteProduccion}" styleClass="outputText2" />
                                <h:outputText value="Producto:" styleClass="outputText1Subtitle" />
                                <h:outputText value="#{ManagedSalidaAlmacen.salidaAlmacenGestionar.componentesProd.nombreProdSemiterminado}" styleClass="outputText2" />

                                <h:outputText value="Presentación:" styleClass="outputText1Subtitle" />
                                <h:outputText value="#{ManagedSalidaAlmacen.salidaAlmacenGestionar.presentacionesProducto.nombreProductoPresentacion}" styleClass="outputText2" />
                                <h:outputText value="Tipo Salida:" styleClass="outputText1Subtitle" />
                                <h:outputText value="#{ManagedSalidaAlmacen.salidaAlmacenGestionar.tiposSalidasAlmacen.nombreTipoSalidaAlmacen}" styleClass="outputText2" />
                            </h:panelGrid> 
                            <br/>
                            <h:panelGrid style="width:100%">
                                <h:outputText value="Motivo/Observación:" styleClass="outputText1Subtitle" />
                                <h:inputTextarea value="#{ManagedSalidaAlmacen.salidaTransaccionSolicitudGestionar.observacionSolicitante}" styleClass="inputText"
                                                 style="width:100%" rows="3" />
                            </h:panelGrid>
                            <br/>
                            <a4j:commandButton styleClass="btn" value="Registrar Solicitud"
                                               oncomplete="mostrarMensajeTransaccionEvento(function(){Richfaces.hideModalPanel('panelTransaccionSolicitudAnular');})"
                                               reRender="dataSalidasAlmacen"
                                               action="#{ManagedSalidaAlmacen.guardarSolicitudAnularSalidaAlmacen_action}" >
                            </a4j:commandButton>
                            <input type="button" value="Cancelar" onclick="Richfaces.hideModalPanel('panelTransaccionSolicitudAnular')" class="btn" />
                        </div>
                    </h:panelGroup>
                </a4j:form>
            </rich:modalPanel>  
                        
            
            <rich:modalPanel id="panelAprobarTransaccionSolicitudAnular"  minHeight="240"  minWidth="700"
                             height="240" width="700"
                             zindex="200"
                             headerClass="headerClassACliente"
                             resizeable="false" style="overflow :auto">
                <f:facet name="header">
                    <h:outputText id="txtTituloPanelAprobar" value="<center>ANULAR SALIDA</center>" escape="false"/>
                </f:facet>
                <a4j:form>
                    <h:panelGroup id="contenidoAprobarAnularSalida">
                        <div align="center">
                            <h:panelGrid columns="4">
                                <h:outputText value="Nro. Salida:" styleClass="outputText1Subtitle" />
                                <h:outputText value="#{ManagedSalidaAlmacen.salidaAlmacenGestionar.nroSalidaAlmacen}" styleClass="outputText2" style="width:50px" />
                                <h:outputText value="Fecha Salida:" styleClass="outputText1Subtitle" />
                                <h:outputText value="#{ManagedSalidaAlmacen.salidaAlmacenGestionar.fechaSalidaAlmacen}" styleClass="outputText2">
                                    <f:convertDateTime pattern="dd/MM/yyyy HH:mm" />
                                </h:outputText>

                                <h:outputText value="Nro. Lote:" styleClass="outputText1Subtitle" />
                                <h:outputText value="#{ManagedSalidaAlmacen.salidaAlmacenGestionar.codLoteProduccion}" styleClass="outputText2" />
                                <h:outputText value="Producto:" styleClass="outputText1Subtitle" />
                                <h:outputText value="#{ManagedSalidaAlmacen.salidaAlmacenGestionar.componentesProd.nombreProdSemiterminado}" styleClass="outputText2" />

                                <h:outputText value="Presentación:" styleClass="outputText1Subtitle" />
                                <h:outputText value="#{ManagedSalidaAlmacen.salidaAlmacenGestionar.presentacionesProducto.nombreProductoPresentacion}" styleClass="outputText2" />
                                <h:outputText value="Tipo Salida:" styleClass="outputText1Subtitle" />
                                <h:outputText value="#{ManagedSalidaAlmacen.salidaAlmacenGestionar.tiposSalidasAlmacen.nombreTipoSalidaAlmacen}" styleClass="outputText2" />
                            </h:panelGrid> 
                            <br/>
                            <h:panelGrid columns="1">
                                <h:outputText value="Motivo/Observación:" styleClass="outputText1Subtitle"/>
                                <h:inputTextarea value="#{ManagedSalidaAlmacen.motivoAnulacionSalida}" styleClass="inputText" cols="100" rows="4" />
                            </h:panelGrid>
                            <br/>
                            <a4j:commandButton styleClass="btn" value="Registrar Anulación"
                                               oncomplete="alert('#{ManagedSalidaAlmacen.mensaje}'); if(#{ManagedSalidaAlmacen.registradoCorrectamente}){javascript:Richfaces.hideModalPanel('panelAprobarTransaccionSolicitudAnular');}"
                                               reRender="dataSalidasAlmacen"
                                               action="#{ManagedSalidaAlmacen.anularSalidaAlmacenSinSolicitud_action}" >
                            </a4j:commandButton>
                            <input type="button" value="Cancelar" onclick="javascript:Richfaces.hideModalPanel('panelAprobarTransaccionSolicitudAnular')" class="btn" />
                        </div>
                    </h:panelGroup>
                </a4j:form>
            </rich:modalPanel>   
                        
            <a4j:include viewId="/panelProgreso.jsp"/>
            <a4j:include viewId="/message.jsp"/>
        </body>
    </html>

</f:view>

