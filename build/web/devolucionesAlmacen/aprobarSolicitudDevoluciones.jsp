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
                    } else if (count > 1) {
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
                    } else if (count > 1) {
                        alert('Solo puede escoger un registro');
                        return false;
                    }
                }
                function verManteminiento(cod_maquina, codigo) {
                    location = '../partes_maquinaria/navegador_partes_maquinaria.jsf?cod_maquina=' + cod_maquina + '&codigo=' + codigo;
                }
                function openPopup(url) {
                    window.open(url, 'DETALLE', 'top=50,left=200,width=800,height=600,scrollbars=1,resizable=1');
                }
                function valida() {
                    if (document.getElementById("form2:nroSalida").value == '') {
                        document.getElementById("form2:nroSalida").value = 0;
                    }
                    if (document.getElementById("form2:nroSalida").value == 0 && document.getElementById("form2:nroLote").value == '' && document.getElementById("form2:codCompProd").value == 0) {
                        alert('Seleccione un dato para la busqueda');
                        return false;
                    }

                    return true;

                }
                function validaCantidad(obj) {
                    //var cantidadDisponible=parseFloat(obj.parentNode.parentNode.getElementsByTagName('td')[9].getElementsByTagName('input')[0].value);
                    //alert (obj.parentNode.parentNode.getElementsByTagName('td')[9].innerHTML);
                    //alert(obj.value.substr(0,obj.value.length-1));
                    var cantidadDisponible = obj.parentNode.parentNode.getElementsByTagName('td')[2].innerHTML;
                    var cantidadEntregada = obj.parentNode.parentNode.getElementsByTagName('td')[3].innerHTML;
                    var cantidadBuenos = obj.parentNode.parentNode.getElementsByTagName('td')[4];
                    var cantidadFallados = obj.parentNode.parentNode.getElementsByTagName('td')[5];
                    var cantidadFalladosProveedor = obj.parentNode.parentNode.getElementsByTagName('td')[6];






                    //alert(cantidadBuenos.getElementsByTagName('input')[0].value);
                    //alert(cantidadFallados.getElementsByTagName('input')[0].value);

                    var cantidadBuenos1 = cantidadBuenos.getElementsByTagName('input')[0].value;
                    var cantidadFallados1 = cantidadFallados.getElementsByTagName('input')[0].value
                    var cantidadFalladosProveedor1 = cantidadFalladosProveedor.getElementsByTagName('input')[0].value

                    if ((parseFloat(cantidadBuenos1) + parseFloat(cantidadFallados1) + parseFloat(cantidadFalladosProveedor1)) > (cantidadDisponible - cantidadEntregada)) {
                        alert("la suma de cantidades no debe sobrepasar la cantidad disponible");
                        obj.focus();
                        obj.value = 0;
                    }

                    /*
                     if(obj.value>parseFloat(cantidadDisponible)){
                     alert("la cantidad debe ser menor a la cantidad disponible");
                     obj.focus();
                     //obj.value = obj.value.substr(0,obj.value.length-1);
                     obj.value = cantidadDisponible;
                     return false;
                     }
                     */

                    /*
                     if(this.hallaCantidadSalidaFisica()==false){
                     alert(" el total no debe sobrepasar la cantidad total de salida ");
                     obj.focus();
                     var col10 = obj.parentNode.parentNode.getElementsByTagName('td')[10];
                     obj.value = col10.getElementsByTagName("input")[1].value;
                     //alert(col10.getElementsByTagName("input")[1].value);
                     return false;
                     }
                     */

                    return true;
                }


                function hallaCantidadSalidaFisica() {
                    var elements = document.getElementById('form2:dataSalidaAlmacenDetalleIngreso');
                    var rowsElement = elements.rows;
                    var cantidadSalidaFisica = 0;
                    var totalImporteF = 0;
                    for (var i = 1; i < rowsElement.length; i++) {
                        var cellsElement = rowsElement[i].cells;
                        var cel10 = cellsElement[10];
                        var cantidadParcial = cel10.getElementsByTagName('input')[0];
                        if (cantidadParcial.type == 'text') {
                            cantidadSalidaFisica = parseFloat(cantidadSalidaFisica) + parseFloat(cantidadParcial.value);
                        }
                    }

                    //alert(document.getElementById('form2:cantTotalIngresoFisico'));
                    //alert(cantidadIngresoFisico);
                    if (parseFloat(document.getElementById('form2:cantTotalSalidaFisica').innerHTML) < (Math.round(cantidadSalidaFisica * 100) / 100))
                    {
                        return false;
                    }

                    return true;
                }




            </script>
            <style type="text/css">
                .dr-table-headercell
                {
                    color:#111 !important;
                    font-weight: bold;
                }
            </style>
        </head>
        <body >
            <a4j:form id="form1">
                <div align="center">
                    <h:outputText value="#{ManagedSolicitudDevoluciones.cargarDetalleAprobar}"/>
                    <h:outputText styleClass="outputTextTitulo"  value="Aprobar solicitud de Devolución" />
                    <br><br>


                    <rich:panel style="width:80%" headerClass="headerClassACliente">
                        <f:facet name="header">
                            <h:outputText value="Datos de Solicitud de Devolución Almacen"  />
                        </f:facet>
                        <h:panelGrid columns="4" >

                            <h:outputText value="Nro Solicitud:" styleClass="negrilla" />
                            <h:outputText value="#{ManagedSolicitudDevoluciones.solicitudAprobar.codSolicitudDevolucion}" styleClass="outputText1" />
                            <h:outputText value="Solicitante:" styleClass="negrilla" />
                            <h:outputText value="#{ManagedSolicitudDevoluciones.solicitudAprobar.personalSolicitante.nombrePersonal}" styleClass="outputText1" />
                            <h:outputText value="Fecha de Solicitud:" styleClass="negrilla" />
                            <h:outputText value="#{ManagedSolicitudDevoluciones.solicitudAprobar.fechaSolicitud}" styleClass="outputText1" >
                                <f:convertDateTime pattern="dd/MM/yyyy"/>
                            </h:outputText>

                            <h:outputText value="Nro Salida:" styleClass="negrilla" />
                            <h:outputText value="#{ManagedSolicitudDevoluciones.solicitudAprobar.salidaAlmacen.nroSalidaAlmacen}" styleClass="outputText1" />
                            <h:outputText value="Almacen:" styleClass="negrilla" />
                            <h:outputText value="#{ManagedSolicitudDevoluciones.solicitudAprobar.salidaAlmacen.almacenes.nombreAlmacen}" styleClass="outputText1" />


                            <h:outputText value="Nro Lote:" styleClass="negrilla" />
                            <h:outputText value="#{ManagedSolicitudDevoluciones.solicitudAprobar.salidaAlmacen.codLoteProduccion}" styleClass="outputText1" />

                            <h:outputText value="Producto:" styleClass="negrilla" />
                            <h:outputText  styleClass="outputText1" value="#{ManagedSolicitudDevoluciones.solicitudAprobar.salidaAlmacen.producto.nombreProducto}"   />

                            <h:outputText value="Generar salida por traspaso:" styleClass="negrilla" />
                            <h:selectOneMenu  styleClass="outputText1" 
                                              value="#{ManagedSolicitudDevoluciones.generaTraspaso}"   >
                                <f:selectItem itemLabel="NO" itemValue="false"></f:selectItem>
                                <f:selectItem itemLabel="SI" itemValue="true"></f:selectItem>
                                <a4j:support action="#{ManagedSolicitudDevoluciones.traspasoChanged}" 
                                             reRender="dataDevolucionesDetalleAlmacen, traspaso" 
                                             event="onchange" />
                            </h:selectOneMenu>


                        </h:panelGrid>
                        <br />
                        <h:panelGrid columns="2"  id="traspaso">
                            <f:facet name="header">
                                <h:outputText styleClass="negrilla" value="Detalle de la salida por traspaso" rendered="#{ManagedSolicitudDevoluciones.generaTraspaso}"></h:outputText>
                            </f:facet>
                            <h:outputText value="Almacen Destino:" styleClass="outputText2" rendered="#{ManagedSolicitudDevoluciones.generaTraspaso}"/>
                            
                            <h:selectOneMenu  styleClass="inputText" value="#{ManagedSolicitudDevoluciones.cod_almacen_destino}"  
                                              id="codAlmacenDestino" rendered="#{ManagedSolicitudDevoluciones.generaTraspaso}" >
                            
                                <f:selectItems value="#{ManagedSolicitudDevoluciones.cargaAlmacenDestino}"  />
                               
                            </h:selectOneMenu>

                            <h:outputText value="Area Destino:" styleClass="outputText2" rendered="#{ManagedSolicitudDevoluciones.generaTraspaso}"/>
                            <h:selectOneMenu  styleClass="inputText" value="#{ManagedSolicitudDevoluciones.cod_area_destino}" id="codAreaDestino" rendered="#{ManagedSolicitudDevoluciones.generaTraspaso}" >
                                <f:selectItems  value="#{ManagedSolicitudDevoluciones.cargarAreasEmpresa}" />
                            </h:selectOneMenu>
                        </h:panelGrid>
                    </rich:panel>
                    <br/>
                    <rich:dataTable value="#{ManagedSolicitudDevoluciones.solicitudesDetalleList}"
                                    var="data"
                                    id="dataDevolucionesDetalleAlmacen"
                                    onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                    onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                    headerClass="headerClassACliente"
                                    style="width:80%">
                        <f:facet name="header">
                            <rich:columnGroup>
                                <rich:column rendered="#{ManagedSolicitudDevoluciones.generaTraspaso}">
                                    <h:outputText value=""/>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Item"/>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Cantidad devuelta buenos"/>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Cantidad devuelta FRV"/>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Costo devolución FRV (Bs.)"/>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Cantidad devuelta FRV Proveedor"/>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Costo devolución FRV Proveedor (Bs.)"/>
                                </rich:column>
                            </rich:columnGroup>
                        </f:facet>
                        <rich:column rendered="#{ManagedSolicitudDevoluciones.generaTraspaso}">
                            <h:selectBooleanCheckbox value="#{data.checked}" />
                        </rich:column>
                        <rich:column>
                            <h:outputText value="#{data.materiales.nombreMaterial}" />
                        </rich:column>
                        <rich:column style="text-align:right">
                            <h:outputText value="#{data.cantidadDevuelta}"  >
                                <f:convertNumber pattern="#,##0.00" locale="en"/>
                            </h:outputText>
                        </rich:column>
                        <rich:column style="text-align:right">
                            <h:outputText value="#{data.cantidadDevueltaFallados}"  >
                                <f:convertNumber pattern="#,##0.00" locale="en"/>
                            </h:outputText>
                        </rich:column>
                        <rich:column style="text-align:right">
                            <h:outputText value="#{data.costoSolicitudDevolucionFrv}"  >
                                <f:convertNumber pattern="#,##0.00" locale="en"/>
                            </h:outputText>
                        </rich:column>
                        <rich:column style="text-align:right">
                            <h:outputText value="#{data.cantidadDevueltaFalladosProveedor}"  >
                                <f:convertNumber pattern="#,##0.00" locale="en"/>
                            </h:outputText>
                        </rich:column>
                        <rich:column style="text-align:right">
                            <h:outputText value="#{data.costoSolicitudDevolucionFrvProveedor}"  >
                                <f:convertNumber pattern="#,##0.00" locale="en"/>
                            </h:outputText>
                        </rich:column>
                        


                    </rich:dataTable>
                    <br>
                    <a4j:commandButton value="Aprobar" styleClass="btn"
                                       action="#{ManagedSolicitudDevoluciones.aprobarSolicitud_action}"
                                       oncomplete="if(#{ManagedSolicitudDevoluciones.mensaje eq '1'}){alert('Se aprobo la solicitud de devolución');window.location.href='navegadorAprobarSolicitudes.jsf?aprobado='+(new Date()).getTime().toString();}
                                       else{alert('#{ManagedSolicitudDevoluciones.mensaje}');}"
                                       onclick="if(confirm('Esta seguro de aprobar la solicitud?')==false){return false;}" />
                    <a4j:commandButton value="Cancelar" styleClass="btn"

                                       onclick="location='navegadorAprobarSolicitudes.jsf'" />
                </div>
            </a4j:form>

            <rich:modalPanel id="panelDevolucionAlmacenDetalleEtiquetas" minHeight="400"  minWidth="800"
                             height="400" width="800"
                             zindex="200"
                             headerClass="headerClassACliente"
                             resizeable="false" style="overflow :auto" >
                <f:facet name="header">
                    <h:outputText value=" Detalle de devolucion "/>
                </f:facet>
                <a4j:form>
                    <h:panelGroup id="contenidoDevolucionAlmacenDetalleEtiquetas">


                        <div style="overflow:auto;height:320px;text-align:center">
                            <rich:dataTable value="#{ManagedSolicitudDevoluciones.devolucionesDetalle.devolucionesDetalleEtiquetasList}"
                                            var="data"
                                            id="dataDevolucionesDetalleEtiquetasAlmacen"
                                            onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                            onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                            headerClass="headerClassACliente" >

                                <h:column>
                                    <f:facet name="header">
                                        <h:outputText value="Nro Ingreso"  />
                                    </f:facet>
                                    <h:outputText value="#{data.ingresosAlmacen.nroIngresoAlmacen}" />
                                </h:column>

                                <h:column>
                                    <f:facet name="header">
                                        <h:outputText value="Etiqueta"  />
                                    </f:facet>
                                    <h:outputText  value="#{data.etiqueta}"  />
                                </h:column>
                                <h:column>
                                    <f:facet name="header">
                                        <h:outputText value="Cantidad Salida"  />
                                    </f:facet>
                                    <h:outputText value="#{data.cantidadSalida}" >
                                    </h:outputText>
                                </h:column>
                                <h:column>
                                    <f:facet name="header">
                                        <h:outputText value="Cantidades Devueltas"  />
                                    </f:facet>
                                    <h:outputText value="#{data.cantidadesDevueltas}" >
                                    </h:outputText>
                                </h:column>
                                <h:column>
                                    <f:facet name="header">
                                        <h:outputText value="Cantidad Buenos"   />
                                    </f:facet>
                                    <h:inputText value="#{data.cantidadDevuelta}" styleClass="inputText" onkeypress="valNum(event);" onkeyup="validaCantidad(this);" />
                                </h:column>
                                <h:column>
                                    <f:facet name="header">
                                        <h:outputText value="Cantidad Fallados"   />
                                    </f:facet>
                                    <h:inputText value="#{data.cantidadFallados}" styleClass="inputText" onkeypress="valNum(event);" onkeyup="validaCantidad(this);"  />
                                </h:column>

                                <h:column>
                                    <f:facet name="header">
                                        <h:outputText value="Cantidad Fallados Proveedor"   />
                                    </f:facet>
                                    <h:inputText value="#{data.cantidadFalladosProveedor}" styleClass="inputText" onkeypress="valNum(event);" onkeyup="validaCantidad(this);"  />
                                </h:column>

                            </rich:dataTable>
                        </div>



                    </h:panelGroup>
                    <div align="center">
                        <a4j:commandButton styleClass="btn" value="Registrar"
                                           action="#{ManagedSolicitudDevoluciones.registrarDevolucionesDetalleEtiquetas_action}"
                                           onclick="Richfaces.hideModalPanel('panelDevolucionAlmacenDetalleEtiquetas')"
                                           reRender="dataDevolucionesDetalleAlmacen" />



                    </div>

                </a4j:form>

            </rich:modalPanel>


            <rich:modalPanel id="panelAgregarFrecuenciaMantenimientoMaquinaria" minHeight="150"  minWidth="400"
                             height="150" width="400"
                             zindex="200"
                             headerClass="headerClassACliente"
                             resizeable="false" style="overflow :auto" >
                <f:facet name="header">
                    <h:outputText value="Frecuencia Mantenimiento Maquinaria"/>
                </f:facet>
                <a4j:form>
                    <h:panelGroup id="contenidoFrecuenciaMantenimentoMaquinaria">
                        <h:panelGrid columns="3">
                            <h:outputText value="Tipo de Periodo" styleClass="outputText1" />
                            <h:outputText value="::" styleClass="outputText1" />
                            <h:selectOneMenu value="#{ManagedFrecuenciaMantenimientoMaquinaria.frecuenciasMantenimientoMaquina.tiposPeriodo.codTipoPeriodo}" styleClass="inputText" >
                                <f:selectItems value="#{ManagedFrecuenciaMantenimientoMaquinaria.tiposPeriodoList}"  />
                            </h:selectOneMenu>

                            <h:outputText value="Frecuencia" styleClass="outputText1" />
                            <h:outputText value="::" styleClass="outputText1" />
                            <h:inputText value="#{ManagedFrecuenciaMantenimientoMaquinaria.frecuenciasMantenimientoMaquina.horasFrecuencia}" styleClass="inputText" size="5" onkeypress="valNum(event);" />

                        </h:panelGrid>
                        <div align="center">
                            <a4j:commandButton styleClass="btn" value="Registrar" onclick="javascript:Richfaces.hideModalPanel('panelAgregarFrecuenciaMantenimientoMaquinaria');"
                                               action="#{ManagedFrecuenciaMantenimientoMaquinaria.registrarFrecuenciaMantenimientoMaquina_action}" reRender="dataFrecuenciaMantenimientoMaquina"
                                               />
                            <input type="button" value="Cancelar" onclick="javascript:Richfaces.hideModalPanel('panelAgregarFrecuenciaMantenimientoMaquinaria')" class="btn" />
                        </div>
                    </h:panelGroup>
                </a4j:form>
            </rich:modalPanel>





            <rich:modalPanel id="panelEditarFrecuenciaMantenimientoMaquinaria" minHeight="150"  minWidth="400"
                             height="150" width="400"
                             zindex="200"
                             headerClass="headerClassACliente"
                             resizeable="false" style="overflow :auto" >
                <f:facet name="header">
                    <h:outputText value="Editar Frecuencia Mantenimiento Maquinaria"/>
                </f:facet>
                <a4j:form>
                    <h:panelGroup id="contenidoEditarFrecuenciaMantenimentoMaquinaria">
                        <h:panelGrid columns="3">
                            <h:outputText value="Tipo de Periodo" styleClass="outputText1" />
                            <h:outputText value="::" styleClass="outputText1" />
                            <h:selectOneMenu value="#{ManagedFrecuenciaMantenimientoMaquinaria.frecuenciasMantenimientoMaquina.tiposPeriodo.codTipoPeriodo}" styleClass="inputText" >
                                <f:selectItems value="#{ManagedFrecuenciaMantenimientoMaquinaria.tiposPeriodoList}"  />
                            </h:selectOneMenu>

                            <h:outputText value="Frecuencia" styleClass="outputText1" />
                            <h:outputText value="::" styleClass="outputText1" />
                            <h:inputText value="#{ManagedFrecuenciaMantenimientoMaquinaria.frecuenciasMantenimientoMaquina.horasFrecuencia}" styleClass="inputText" size="5" />

                        </h:panelGrid>
                        <div align="center">
                            <a4j:commandButton styleClass="btn" value="Guardar" onclick="javascript:Richfaces.hideModalPanel('panelEditarFrecuenciaMantenimientoMaquinaria');"
                                               action="#{ManagedFrecuenciaMantenimientoMaquinaria.guardarEdicionFrecuenciaMantenimientoMaquina_action}" reRender="dataFrecuenciaMantenimientoMaquina"
                                               />
                            <input type="button" value="Cancelar" onclick="javascript:Richfaces.hideModalPanel('panelEditarFrecuenciaMantenimientoMaquinaria')" class="btn" />
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

