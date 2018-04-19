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
                    window.open(url, 'DETALLE', 'top=50,left=200,width=800,height=600,scrollbars=1,resizable=1');
                }

                function hallaValorEquivalente(elemento) {
                    var valorEquivalencia = document.getElementById("form2:valorEquivalencia").value;
                    if (valorEquivalencia > 0) {
                        var resultado = elemento.value / valorEquivalencia;
                        document.getElementById("form2:cantEquivalente").value = resultado;
                    }
                }
                function hallaCantidadIngresoFisico() {
                    var elements = document.getElementById('form2:dataSalidaAlmacenDetalleIngreso');
                    var rowsElement = elements.rows;
                    var cantidadIngresoFisico = 0;
                    var totalImporteF = 0;
                    for (var i = 1; i < rowsElement.length; i++) {
                        var cellsElement = rowsElement[i].cells;
                        var cel3 = cellsElement[3];
                        var cantidadParcial = cel3.getElementsByTagName('input')[0];
                        if (cantidadParcial.type == 'text') {
                            cantidadIngresoFisico = parseFloat(cantidadIngresoFisico) + parseFloat(cantidadParcial.value);
                        }
                    }
                    //alert(document.getElementById('form2:cantTotalIngresoFisico'));
                    //alert(cantidadIngresoFisico);
                    document.getElementById('form2:cantTotalIngresoFisico').value = Math.round(cantidadIngresoFisico * 100) / 100;
                }
                function validaCantidad(obj) {
                    //var cantidadDisponible=parseFloat(obj.parentNode.parentNode.getElementsByTagName('td')[9].getElementsByTagName('input')[0].value);
                    //alert (obj.parentNode.parentNode.getElementsByTagName('td')[9].innerHTML);
                    //alert(obj.value.substr(0,obj.value.length-1));
                    var cantidadDisponible = obj.parentNode.parentNode.getElementsByTagName('td')[12].innerHTML;
                    if (obj.value > parseFloat(cantidadDisponible)) {
                        alert("la cantidad debe ser menor a la cantidad disponible");
                        obj.focus();
                        //obj.value = obj.value.substr(0,obj.value.length-1);
                        obj.value = cantidadDisponible;
                        return false;
                    }

                    if (this.hallaCantidadSalidaFisica() == false) {
                        alert(" el total no debe sobrepasar la cantidad total de salida ");
                        obj.focus();
                        var col13 = obj.parentNode.parentNode.getElementsByTagName('td')[13];
                        //obj.value = col10.getElementsByTagName("input")[1].value;
                        obj.value = 0;
                        //alert(col10.getElementsByTagName("input")[1].value);
                        return false;
                    }

                    return true;
                }

                function hallaCantidadSalidaFisica() {
                    var elements = document.getElementById('form2:dataSalidaAlmacenDetalleIngreso');
                    var rowsElement = elements.rows;
                    var cantidadSalidaFisica = 0;
                    var totalImporteF = 0;
                    for (var i = 1; i < rowsElement.length; i++) {
                        var cellsElement = rowsElement[i].cells;
                        var cel13 = cellsElement[13];
                        var cantidadParcial = cel13.getElementsByTagName('input')[0];
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


                function calculaEquivalencia(obj) {
                    valorEquivalencia = document.getElementById("form5:valorEquivalencia").value;
                    if (valorEquivalencia > 0) {
                        document.getElementById("form5:cantidadEquivalencia").value = obj.value / valorEquivalencia;
                    }
                }
                function calculaEquivalencia1(obj) {
                    valorEquivalencia = document.getElementById("form4:valorEquivalencia").value;
                    if (valorEquivalencia > 0) {
                        document.getElementById("form4:cantidadEquivalencia").value = obj.value / valorEquivalencia;
                    }
                }
                function validaCantidadSalidaAlmacenDetalle(obj) {
                    //cantidadDisponible
                    //alert("entro al valida cantidad");
                    //alert(document.getElementById("form5:cantidadSalidaAlmacen").value);
                    //alert(document.getElementById("form5:cantidadDisponible").innerHTML);
                    var cantidadSalidaAlmacen = document.getElementById("form5:cantidadSalidaAlmacen").value;
                    var cantidadDisponible = parseFloat(document.getElementById("form5:cantidadDisponible").innerHTML);

                    //alert (cantidadSalidaAlmacen);
                    //alert (cantidadDisponible);

                    if (cantidadSalidaAlmacen > cantidadDisponible) {
                        document.getElementById("form5:cantidadSalidaAlmacen").value = document.getElementById("form5:cantidadDisponible").innerHTML;
                        calculaEquivalencia(obj);
                        alert("la cantidad de salida de almacen no puede sobrepasar la cantidad disponible");
                        return false;
                    }
                    calculaEquivalencia(obj);
                    return true;
                }



                function validaCantidadSalidaAlmacenDetalle1(obj) {
                    //cantidadDisponible
                    //alert("entro al valida cantidad");
                    //alert(document.getElementById("form5:cantidadSalidaAlmacen").value);
                    //alert(document.getElementById("form5:cantidadDisponible").innerHTML);
                    var cantidadSalidaAlmacen = document.getElementById("form4:cantidadSalidaAlmacen").value;
                    var cantidadDisponible = parseFloat(document.getElementById("form4:cantidadDisponible").innerHTML);

                    //alert (cantidadSalidaAlmacen);
                    //alert (cantidadDisponible);

                    if (cantidadSalidaAlmacen > cantidadDisponible) {
                        document.getElementById("form4:cantidadSalidaAlmacen").value = document.getElementById("form4:cantidadDisponible").innerHTML;
                        calculaEquivalencia1(obj);
                        alert("la cantidad de salida de almacen no puede sobrepasar la cantidad disponible");
                        return false;
                    }
                    calculaEquivalencia1(obj);
                    return true;
                }
                function cantAuxSalida() {
                    var elements = document.getElementById('form2:dataSalidaAlmacenDetalleIngreso');
                    var rowsElement = elements.rows;
                    var cantidadSalidaFisica = 0;
                    var totalImporteF = 0;
                    for (var i = 1; i < rowsElement.length; i++) {
                        var cellsElement = rowsElement[i].cells;
                        var cel10 = cellsElement[10];
                        cel10.getElementsByTagName('input')[1].value = cel10.getElementsByTagName('input')[0].value;
                    }

                }
                A4J.AJAX.onError = function (req, status, message) {
                    window.alert("Ocurrio un error: " + message);
                }
                A4J.AJAX.onExpired = function (loc, expiredMsg) {
                    if (window.confirm("Ocurrio un error al momento realizar la transaccion Intente nuevamente \n Error: " + expiredMsg + " location: " + loc)) {
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

                    <h:outputText value="#{ManagedRegistroSalidaAlmacen.cargarContenidoEditarSalidasAlmacen}"/>
                    <br><br>

                    <rich:panel id="panelEstadoCuentas" styleClass="panelBuscar" style="top:50px;left:50px;width:700px;">
                        <f:facet name="header">
                            <h:outputText value="<div   onmouseover=\"this.style.cursor='move'\" onmousedown=\"comienzoMovimiento(event, 'form1:panelEstadoCuentas');\"  >Buscar<div   style=\"margin-left:550px;\"   onclick=\"document.getElementById('form1:panelEstadoCuentas').style.visibility='hidden';document.getElementById('panelsuper').style.visibility='hidden';\" onmouseover=\"this.style.cursor='hand'\"   >Cerrar</div> </div> "
                                          escape="false" />
                        </f:facet>
                    </rich:panel>

                    <rich:panel style="width:100%"  headerClass="headerClassACliente">
                        <f:facet name="header">
                            <h:outputText value="Editar Salidas de Almacen"  />
                        </f:facet>

                        <rich:panel >
                            <h:panelGrid columns="2" >
                                <h:outputText value="Motivo/Observación:" styleClass="outputText1Subtitle" />
                                <h:inputTextarea value="#{ManagedRegistroSalidaAlmacen.motivoEdicionSalida}" styleClass="inputText" cols="70" rows="2" />
                            </h:panelGrid>
                        </rich:panel>

                        <h:panelGrid columns="4" >

                            <h:outputText value="Nro Salida:" styleClass="outputText2" />
                            <h:outputText value="#{ManagedRegistroSalidaAlmacen.salidasAlmacen.nroSalidaAlmacen}" styleClass="outputText1" />

                            <h:outputText value="Tipo de Salida:" styleClass="outputText2" />
                            <h:selectOneMenu  styleClass="inputText" value="#{ManagedRegistroSalidaAlmacen.salidasAlmacen.tiposSalidasAlmacen.codTipoSalidaAlmacen}"
                                              disabled="#{ManagedRegistroSalidaAlmacen.salidasAlmacen.tiposSalidasAlmacen.automatico}">
                                <f:selectItems value="#{ManagedRegistroSalidaAlmacen.tiposSalidasAlmacenList}"  />
                            </h:selectOneMenu>
                            
                            <h:outputText value="Filial Destino:" styleClass="outputText2" rendered="#{ManagedRegistroSalidaAlmacen.salidasAlmacen.tiposSalidasAlmacen.codTipoSalidaAlmacen eq 3}"/>
                            <h:selectOneMenu id="codFilial" styleClass="inputText" value="#{ManagedRegistroSalidaAlmacen.traspasoGestionar.almacenDestino.filiales.codFilial}" 
                                             rendered="#{ManagedRegistroSalidaAlmacen.salidasAlmacen.tiposSalidasAlmacen.codTipoSalidaAlmacen eq 3}"
                                             disabled="#{!ManagedRegistroSalidaAlmacen.salidasAlmacen.tiposSalidasAlmacen.editable || ManagedRegistroSalidaAlmacen.salidaTieneIngresoTraspaso}">
                                <f:selectItems value="#{ManagedRegistroSalidaAlmacen.filialList}"  />
                                <a4j:support action="#{ManagedRegistroSalidaAlmacen.filial_change}" event="onchange" reRender="codAlmacenDestino" />
                            </h:selectOneMenu>
                            
                            <h:outputText value="Almacen Destino:" styleClass="outputText2" rendered="#{ManagedRegistroSalidaAlmacen.salidasAlmacen.tiposSalidasAlmacen.codTipoSalidaAlmacen eq 3}" />
                            <h:selectOneMenu  styleClass="inputText" value="#{ManagedRegistroSalidaAlmacen.traspasoGestionar.almacenDestino.codAlmacen}"  id="codAlmacenDestino"
                                              rendered="#{ManagedRegistroSalidaAlmacen.salidasAlmacen.tiposSalidasAlmacen.codTipoSalidaAlmacen eq 3}"
                                              disabled="#{!ManagedRegistroSalidaAlmacen.salidasAlmacen.tiposSalidasAlmacen.editable || ManagedRegistroSalidaAlmacen.salidaTieneIngresoTraspaso}">
                                <f:selectItems value="#{ManagedRegistroSalidaAlmacen.almacenDestinoList}"  />
                                <a4j:support action="#{ManagedRegistroSalidaAlmacen.almacenDestino_change}" event="onchange" reRender="codAreaDestino" />
                            </h:selectOneMenu>
                            
                            <h:outputText value="Area / Departamento:" styleClass="outputText2" />
                            <h:selectOneMenu  styleClass="inputText" value="#{ManagedRegistroSalidaAlmacen.salidasAlmacen.areasEmpresa.codAreaEmpresa}" id="codAreaEmpresa" 
                                              disabled="#{!ManagedRegistroSalidaAlmacen.salidasAlmacen.tiposSalidasAlmacen.editable || ManagedRegistroSalidaAlmacen.salidaTieneIngresoTraspaso}" >
                                <f:selectItems value="#{ManagedRegistroSalidaAlmacen.areasEmpresaList}"  />
                                <a4j:support event="onchange" action="#{ManagedRegistroSalidaAlmacen.nroLote_change}" reRender="codAreaEmpresa,codMaquinaria"
                                             oncomplete="if(#{ManagedRegistroSalidaAlmacen.mensaje!=''}){alert('#{ManagedRegistroSalidaAlmacen.mensaje}');}" />
                            </h:selectOneMenu>
                               
                            <h:outputText value="Nro Lote:" styleClass="outputText2" rendered="#{ManagedRegistroSalidaAlmacen.salidasAlmacen.tiposSalidasAlmacen.registraLote}" />
                            <h:inputText value="#{ManagedRegistroSalidaAlmacen.salidasAlmacen.codLoteProduccion}" styleClass="inputText" id="codLoteProduccion" disabled="true"
                                         rendered="#{ManagedRegistroSalidaAlmacen.salidasAlmacen.tiposSalidasAlmacen.registraLote}" >
                                <%--inicio ale validacion--%>
                                <a4j:support event="onkeyup" action="#{ManagedRegistroSalidaAlmacen.nroLote_change}" reRender="codAreaEmpresa,codProducto"
                                             oncomplete="if(#{ManagedRegistroSalidaAlmacen.mensaje!=''}){alert('#{ManagedRegistroSalidaAlmacen.mensaje}');}if(#{ManagedRegistroSalidaAlmacen.productoHabilitado==true}){document.getElementById('form1:codProducto').disabled=true;}else{document.getElementById('form1:codProducto').disabled=false;}"/>
                                <%--final ale validacion--%>
                            </h:inputText>

                            <%--  --%>

                            <h:outputText value="Producto:" styleClass="outputText2" rendered="#{ManagedRegistroSalidaAlmacen.usuario.almacenesGlobal.codAlmacen != '14' && ManagedRegistroSalidaAlmacen.salidasAlmacen.tiposSalidasAlmacen.registraProducto}" />
                            <h:outputText value="Maquinaria:" styleClass="outputText2"  rendered="#{ManagedRegistroSalidaAlmacen.usuario.almacenesGlobal.codAlmacen eq '14' && ManagedRegistroSalidaAlmacen.salidasAlmacen.tiposSalidasAlmacen.registraProducto}" />
                            <h:panelGroup id="producto" rendered="#{ManagedRegistroSalidaAlmacen.salidasAlmacen.tiposSalidasAlmacen.registraProducto}">
                                <h:selectOneMenu  styleClass="inputText" value="#{ManagedRegistroSalidaAlmacen.salidasAlmacen.componentesProd.codCompprod}" id="codProducto" rendered="#{ManagedRegistroSalidaAlmacen.usuario.almacenesGlobal.codAlmacen != '14'}" 
                                                  disabled="#{!ManagedRegistroSalidaAlmacen.salidasAlmacen.tiposSalidasAlmacen.editable}">
                                    <f:selectItems value="#{ManagedRegistroSalidaAlmacen.componentesProdList}"  />
                                    <a4j:support event="onchange" action="#{ManagedRegistroSalidaAlmacen.producto_changed}" reRender="presentacionProducto,producto" oncomplete="if(#{ManagedRegistroSalidaAlmacen.mensaje!=''}){alert('#{ManagedRegistroSalidaAlmacen.mensaje}');}" timeout="7200" />
                                </h:selectOneMenu>
                                <a4j:commandLink  action="#{ManagedRegistroSalidaAlmacen.detallarMateriales_action}" reRender="dataSalidasAlmacenDetalleEditar" timeout="7200" rendered="#{ManagedRegistroSalidaAlmacen.usuario.almacenesGlobal.codAlmacen != '14' && ManagedRegistroSalidaAlmacen.salidasAlmacen.tiposSalidasAlmacen.editable}">
                                    <h:graphicImage url="../img/actualizar1.jpg" />
                                </a4j:commandLink>

                                <h:selectOneMenu  styleClass="inputText" value="#{ManagedRegistroSalidaAlmacen.salidasAlmacen.maquinaria.codMaquina}"  id="codMaquinaria" rendered="#{ManagedRegistroSalidaAlmacen.usuario.almacenesGlobal.codAlmacen eq '14'}"  
                                                  disabled="#{!ManagedRegistroSalidaAlmacen.salidasAlmacen.tiposSalidasAlmacen.editable}">
                                    <f:selectItem itemValue="0" itemLabel="-NINGUNO-" />
                                    <f:selectItems value="#{ManagedRegistroSalidaAlmacen.maquinariasList}"  />
                                </h:selectOneMenu>
                            </h:panelGroup>

                            <h:outputText value="Presentacion:" styleClass="tituloCampo1" rendered="#{ManagedRegistroSalidaAlmacen.usuario.almacenesGlobal.codAlmacen != '14' && ManagedRegistroSalidaAlmacen.salidasAlmacen.tiposSalidasAlmacen.registraPresentacion}"  />
                            <h:selectOneMenu  styleClass="inputText" value="#{ManagedRegistroSalidaAlmacen.salidasAlmacen.presentacionesProducto.codPresentacion}"  id="presentacionProducto"  
                                              rendered="#{ManagedRegistroSalidaAlmacen.usuario.almacenesGlobal.codAlmacen != '14' && ManagedRegistroSalidaAlmacen.salidasAlmacen.tiposSalidasAlmacen.registraPresentacion}" 
                                              disabled="#{!ManagedRegistroSalidaAlmacen.salidasAlmacen.tiposSalidasAlmacen.editable}" >
                                <f:selectItems value="#{ManagedRegistroSalidaAlmacen.presentacionesList}"  />
                            </h:selectOneMenu>

                            <%--  --%>

                            <h:outputText value="Observaciones:" styleClass="outputText2" />
                            <h:inputTextarea  rows="5" cols="50" styleClass="outputText2" value="#{ManagedRegistroSalidaAlmacen.salidasAlmacen.obsSalidaAlmacen}" />

                            <h:outputText value="Orden de Trabajo:" styleClass="outputText2" />
                            <h:inputText value="#{ManagedRegistroSalidaAlmacen.salidasAlmacen.ordenTrabajo}" styleClass="inputText" 
                                         disabled="#{!ManagedRegistroSalidaAlmacen.salidasAlmacen.tiposSalidasAlmacen.editable}" />

                        </h:panelGrid>

                        <div align="center">
                            <a4j:commandButton value="Agregar Item" styleClass="btn"
                                               rendered="#{ManagedRegistroSalidaAlmacen.salidasAlmacen.tiposSalidasAlmacen.editable}"
                                               onclick="Richfaces.showModalPanel('panelAgregarItem')" action="#{ManagedRegistroSalidaAlmacen.agregarItem_action}"
                                               reRender="contenidoAgregarItem"
                                               />
                        </div>

                    </rich:panel>


                    <div style="overflow:auto;height:200px;width:80%;text-align:center">
                        <rich:dataTable value="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalleList}"
                                        var="data"
                                        id="dataSalidasAlmacenDetalle"
                                        onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                        onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                        headerClass="headerClassACliente"                                    
                                        style="width:100%" >

                            <rich:column>
                                <f:facet name="header">
                                    <h:outputText value=""  />
                                </f:facet>
                                <h:selectBooleanCheckbox value="#{data.checked}" />
                            </rich:column>

                            <rich:column style="background-color: #{data.colorFila}" >
                                <f:facet name="header">
                                    <h:outputText value=""  />
                                </f:facet>
                                <h:outputText value=""  />
                            </rich:column>

                            <rich:column>
                                <f:facet name="header">
                                    <h:outputText value="Item"  />
                                </f:facet>
                                <h:outputText  value="#{data.materiales.nombreMaterial}"/>
                            </rich:column>

                            <rich:column styleClass="tdRight">
                                <f:facet name="header">
                                    <h:outputText value="Cantidad"  />
                                </f:facet>
                                <h:outputText value="#{data.cantidadSalidaAlmacen}">
                                    <f:convertNumber pattern="#,##0.00" locale="en"/>
                                </h:outputText>
                            </rich:column>

                            <rich:column>
                                <f:facet name="header">
                                    <h:outputText value="Unid."   />
                                </f:facet>
                                <h:outputText value="#{data.unidadesMedida.abreviatura}"  />
                            </rich:column>

                            <rich:column styleClass="tdRight">
                                <f:facet name="header">
                                    <h:outputText value="Cant. Disp."   />
                                </f:facet>
                                <h:outputText value="#{data.cantidadDisponible}">
                                    <f:convertNumber pattern="#,##0.00" locale="en"/>
                                </h:outputText>
                            </rich:column>
                            <rich:column>
                                <f:facet name="header">
                                    <h:outputText value=""   />
                                </f:facet>
                                <%-- oncomplete="cantAuxSalida();" action="#{ManagedRegistroSalidaAlmacen.verDetalleEtiquetasSalidaAlmacen_action}" reRender="contenidoSalidaAlmacenDetalleEstado" --%>
                                <a4j:commandLink rendered="#{ManagedRegistroSalidaAlmacen.salidasAlmacen.tiposSalidasAlmacen.editable && data.ingresoAlmacenDetalleTraspaso.cantTotalIngresoFisico eq 0}" >
                                    <h:graphicImage url="../img/areasdependientes.png"></h:graphicImage>
                                    <a4j:support event="onclick" reRender="contenidoSalidaAlmacenDetalleEstado" action="" 
                                                 oncomplete="Richfaces.showModalPanel('panelSalidaAlmacenDetalleEstado')"  >
                                        <f:setPropertyActionListener value="#{data}" target="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalle}"/>
                                    </a4j:support>
                                </a4j:commandLink>
                                <h:outputText value="Ya fue ingresado a almacen destino" rendered="#{data.ingresoAlmacenDetalleTraspaso.cantTotalIngresoFisico != 0}" />

                            </rich:column>
                            <rich:column rendered="#{ManagedRegistroSalidaAlmacen.salidasAlmacen.tiposSalidasAlmacen.editable}">
                                <f:facet name="header">
                                    <h:outputText value="Editar"/>
                                </f:facet>
                                <a4j:commandButton value="Editar" 
                                                    styleClass="btn"
                                                    rendered="#{data.ingresoAlmacenDetalleTraspaso.cantTotalIngresoFisico eq 0}"
                                                    oncomplete="Richfaces.showModalPanel('panelModificarCantidadMaterial')"
                                                    action="#{ManagedRegistroSalidaAlmacen.editarCantidadSalidaAlmacenAction}" 
                                                    reRender="contenidoModificarCantidadMaterial"
                                                    timeout="7200" >
                                    <f:setPropertyActionListener target="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalle}"
                                                                 value="#{data}"/>
                                </a4j:commandButton>
                            </rich:column>
                            <rich:column rendered="#{ManagedRegistroSalidaAlmacen.salidasAlmacen.tiposSalidasAlmacen.editable}">
                                <f:facet name="header">
                                    <h:outputText value="Eliminar"/>
                                </f:facet>
                                <a4j:commandButton value="Eliminar"
                                                   rendered="#{data.ingresoAlmacenDetalleTraspaso.cantTotalIngresoFisico eq 0}"
                                                   styleClass="btn"
                                                   action="#{ManagedRegistroSalidaAlmacen.eliminarDetalleSalidaAlmacenAction}"
                                                   reRender="dataSalidasAlmacenDetalle" timeout="7200">
                                    <f:setPropertyActionListener target="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalle}"
                                                                 value="#{data}"/>
                                </a4j:commandButton>
                            </rich:column>

                        </rich:dataTable>
                    </div>
                    <rich:messages layout="table" />

                    <%-- <ws:datascroller fordatatable="dataAreasEmpresa"  />--%>
                    <br>
                    <a4j:commandButton value="Guardar" styleClass="btn"                    
                                       action="#{ManagedRegistroSalidaAlmacen.guardarEditarSalidasAlmacen_action}"
                                       onclick="if(confirm('¿Esta seguro de realizar los cambios en la Salida de Almacen?')==false){return false;}"
                                       oncomplete="mostrarMensajeTransaccionEvento(function(){redireccionar('navegadorSalidasAlmacen.jsf');})"
                                       />
                    <a4j:commandButton value="Cancelar" styleClass="btn" 
                                       onclick="if(confirm('¿Esta seguro de CANCELAR la Edición de la Salida de Almacen?')==false){return false;}"
                                       oncomplete="location='navegadorSalidasAlmacen.jsf'" 
                                       />
                    
                </a4j:form>
                <rich:modalPanel id="panelModificarCantidadMaterial" minHeight="180"  minWidth="650"
                                 height="180" width="650"
                                 zindex="200"
                                 headerClass="headerClassACliente"
                                 resizeable="false" style="overflow :auto" >
                    <f:facet name="header">
                        <h:outputText value="Modificar Cantidad Material"/>
                    </f:facet>
                    <a4j:form id="form4">
                        <h:panelGroup id="contenidoModificarCantidadMaterial">
                            <h:panelGrid columns="3">
                                <h:outputText value="Item" styleClass="outputText1" />
                                <h:outputText value="::" styleClass="outputText1" />
                                <h:outputText value="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalle.materiales.nombreMaterial}" styleClass="outputText1" />

                                <h:outputText value="Cantidad Disponible" styleClass="outputText1" />
                                <h:outputText value="::" styleClass="outputText1" />
                                <h:outputText value="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalle.cantidadDisponible}" id="cantidadDisponible"  styleClass="outputText1" />

                                <h:outputText value="Cantidad de Salida" styleClass="outputText1" />
                                <h:outputText value="::" styleClass="outputText1" />
                                <h:panelGroup>
                                    <h:inputText value="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalle.cantidadSalidaAlmacen}"  styleClass="inputText" id="cantidadSalidaAlmacen"  onkeypress="valNum(this)"  onkeyup="validaCantidadSalidaAlmacenDetalle1(this)" />
                                    <h:outputText value="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalle.equivalencias.unidadesMedida1.nombreUnidadMedida}" styleClass="outputText1" />
                                </h:panelGroup>

                                <h:outputText value="Equivalente" styleClass="outputText1" />
                                <h:outputText value="::" styleClass="outputText1" />
                                <h:panelGroup>
                                    <h:inputText value="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalle.cantidadSalidaAlmacenEquivalente}" readonly="true" id="cantidadEquivalencia" styleClass="inputText" />
                                    <h:outputText value="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalle.equivalencias.unidadesMedida2.nombreUnidadMedida}" styleClass="outputText1" />
                                    <h:inputHidden value="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalle.equivalencias.valorEquivalencia}" id="valorEquivalencia" />
                                </h:panelGroup>



                            </h:panelGrid>


                            <div align="center">

                                <a4j:commandButton styleClass="btn" value="Guardar"
                                                   action="#{ManagedRegistroSalidaAlmacen.guardarEdicionCantidadMaterial_action}"
                                                   reRender="dataSalidasAlmacenDetalle"  onclick="javascript:Richfaces.hideModalPanel('panelModificarCantidadMaterial')"
                                                   />

                                <input type="button" value="Cancelar" onclick="javascript:Richfaces.hideModalPanel('panelModificarCantidadMaterial')" class="btn" />
                            </div>
                        </h:panelGroup>
                    </a4j:form>
                </div>
            </rich:modalPanel>
            <a4j:include viewId="panelAgregarItemSalida.jsp" id="pAgregarItem"/>
            <a4j:include viewId="panelDetalleItemSalida.jsp" id="pDetalleItemSalida"/>
            <a4j:include viewId="/panelProgreso.jsp"/>
            <a4j:include viewId="/message.jsp"/>
            <a4j:loadScript src="/js/chosen.js" />
        </body>
    </html>

</f:view>

