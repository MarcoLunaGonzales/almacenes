
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib prefix="a4j" uri="http://richfaces.org/a4j"%>
<%@taglib prefix="rich" uri="http://richfaces.org/rich"%>
<%@taglib prefix="ws" uri="http://isawi.org/ws"%>
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

            function seleccionarTodo() {
                var items = document.getElementById('modal4:form4:dataIngresosAlmacenDetalleEstado').getElementsByTagName("tbody")[0];

                    for (var i = 0; i < items.rows.length; i++) {
                        if(items.rows[i].cells[0].getElementsByTagName("input").length>0)
                        {
                            items.rows[i].cells[0].getElementsByTagName("input")[0].checked = document.getElementById('modal4:form4:seleccionar_todo').checked;
                        }
                    }

                return true;

            }
            function validarAlMenosUno(nametable) {

                var count = 0;

                var elements = document.getElementById(nametable);
                var rowsElement = elements.rows;
                for (var i = 1; i < rowsElement.length; i++) {
                    var cellsElement = rowsElement[i].cells;
                    var cel = cellsElement[0];
                    if (cel.getElementsByTagName('input').length > 0 && cel.getElementsByTagName('input')[0].type == 'checkbox') {
                        if (cel.getElementsByTagName('input')[0].checked) {
                            count++;
                        }

                    }

                }

                if (count == 0) {
                    alert('No escogio ningun registro');
                    return false;
                }

            }
        </script>
    </head>
    <body>
        <f:view>
            <a4j:form id="form1"> 
                <div align="center">
                    <h:outputText value="#{ManagedEstadosMaterial.cargarIngresosAlmacen}"/>
                    <h:outputText styleClass="outputTextTitulo"  value=" Cambiar Estado de Items " />
                    <br/>
                    <a4j:commandLink onclick="Richfaces.showModalPanel('panelBuscarIngresoAlmacen')">
                        <h:outputText value="Buscar Ingreso" styleClass="outputText1" />
                        <h:graphicImage url="../img/buscar.png">

                        </h:graphicImage>
                    </a4j:commandLink>

                    <br><br>

                    <rich:dataTable value="#{ManagedEstadosMaterial.ingresosAlmacenList}"
                                    var="data"
                                    id="dataIngresosAlmacen"
                                    onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                    onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                    headerClass="headerClassACliente" binding="#{ManagedEstadosMaterial.ingresosAlmacenDetalleEstadoDataTable}">
                        <f:facet name="header">
                            <rich:columnGroup>
                                <rich:column>
                                    <h:outputText value="Nro Ingreso Almacén"/>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Fecha Ingreso Almacén"/>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Tipo Ingreso Almacén"/>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Nro Orden Compra"/>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Proveedor"/>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Pais Proveedor"/>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Observaciones"/>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Personal"/>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Acciones"/>
                                </rich:column>
                            </rich:columnGroup>
                        </f:facet>
                        <rich:column>
                            <h:outputText  value="#{data.nroIngresoAlmacen}"  />
                        </rich:column>
                        <rich:column>
                            <h:outputText value="#{data.fechaIngresoAlmacen}"  >
                                <f:convertDateTime pattern="dd/MM/yyyy HH:mm" />
                            </h:outputText>
                        </rich:column>
                        <rich:column>
                            <h:outputText value="#{data.tiposIngresoAlmacen.nombreTipoIngresoAlmacen}"  />
                        </rich:column>
                        <rich:column>
                            <h:outputText value="#{data.ordenesCompra.nroOrdenCompra}"  />
                        </rich:column>
                        <rich:column>
                            <h:outputText value="#{data.proveedores.nombreProveedor}"  />
                        </rich:column>
                        <rich:column>
                            <h:outputText value="#{data.proveedores.paises.nombrePais}"  />
                        </rich:column>
                        <rich:column>
                            <h:outputText value="#{data.obsIngresoAlmacen}"  />
                        </rich:column>
                        <rich:column>
                            <h:outputText value="#{data.personal.nombrePersonal}"  />
                            &nbsp;
                            <h:outputText value="#{data.personal.apPaternoPersonal}"  />
                            &nbsp;
                            <h:outputText value="#{data.personal.apMaternoPersonal}"  />
                        </rich:column>
                        <rich:column>
                            <rich:dropDownMenu >
                                <f:facet name="label">
                                    <h:panelGroup>
                                        <h:outputText value="Acciones"/>
                                    </h:panelGroup>
                                </f:facet>
                                <rich:menuItem  submitMode="none"  value="Reporte">
                                    <a4j:support event="onclick" action="#{ManagedEstadosMaterial.verReporteIngresoAlmacen_action}"
                                                 oncomplete="openPopup('reporteIngresosEtiquetasAlmacen.jsf')">
                                    </a4j:support>
                                </rich:menuItem>
                                <rich:separator></rich:separator>
                                <rich:menuItem  submitMode="none"  value="Cambiar Estado Por Etiqueta" rendered="false" >
                                    <a4j:support event="onclick" action="#{ManagedEstadosMaterial.modificarEstadoItems_action}"
                                                 oncomplete="Richfaces.showModalPanel('panelEditarEstadoMaterialEtiqueta')"
                                                 reRender="contenidoEditarEstadoMaterialEtiqueta">
                                        <f:setPropertyActionListener target="#{ManagedEstadosMaterial.ingresosAlmacen}" 
                                                                     value="#{data}"/>
                                    </a4j:support>
                                </rich:menuItem>
                                <rich:menuItem  submitMode="none"  value="Cambiar Estado Por Ingreso" rendered="#{ManagedEstadosMaterial.permisoAprobacionMateriales || ManagedEstadosMaterial.permisoAprobacionMaterialesGI}">
                                    <a4j:support event="onclick" action="#{ManagedEstadosMaterial.modificarEstadoItems_action}"
                                                 oncomplete="Richfaces.showModalPanel('panelEditarEstadoEtiquetaPorIngresoAlmacen');document.getElementById('form4:seleccionar_todo').checked==false"
                                                 reRender="contenidoEditarEstadoMaterialEtiquetaPorIngresoAlmacen">
                                        <f:setPropertyActionListener target="#{ManagedEstadosMaterial.ingresosAlmacen}" 
                                                                     value="#{data}"/>
                                    </a4j:support>
                                </rich:menuItem>
                                <rich:menuItem  submitMode="none"  value="Cambiar Estado Por Lote de Proveedor" rendered="#{ManagedEstadosMaterial.permisoAprobacionMateriales || ManagedEstadosMaterial.permisoAprobacionMaterialesGI}">
                                    <a4j:support event="onclick" action="#{ManagedEstadosMaterial.modificarEstadoLoteProveedor_action}"
                                                 oncomplete="Richfaces.showModalPanel('panelEditarEstadoEtiquetaPorLoteProveedor');"
                                                 reRender="contenidoEditarEstadoMaterialEtiquetaPorLoteProveedor">
                                        <f:setPropertyActionListener target="#{ManagedEstadosMaterial.ingresosAlmacen}" 
                                                                     value="#{data}"/>
                                    </a4j:support>
                                </rich:menuItem>
                            </rich:dropDownMenu>
                        </rich:column>
                        <f:facet name="footer">
                            <rich:columnGroup>
                                <rich:column colspan="9" style="text-align:center">
                                    <a4j:commandLink  action="#{ManagedEstadosMaterial.atras_action}"   rendered="#{ManagedEstadosMaterial.begin ne 0}" reRender="dataIngresosAlmacen,controles">
                                        <h:graphicImage url="../img/previous.gif"  style="border:0px solid red"   alt="PAGINA ANTERIOR"  />
                                    </a4j:commandLink>
                                    <a4j:commandLink action="#{ManagedEstadosMaterial.siguiente_action}"  rendered="#{ManagedEstadosMaterial.cantidadfilas eq 10}" reRender = "dataIngresosAlmacen,controles">
                                        <h:graphicImage url="../img/next.gif"  style="border:0px solid red"  alt="PAGINA SIGUIENTE" />
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:columnGroup>
                        </f:facet>

                    </rich:dataTable>


                    <br>

                    
                </div>


            </a4j:form>

            <rich:modalPanel id="panelEditarEstadoMaterialEtiqueta" minHeight="400"  minWidth="900"
                             height="400" width="900"
                             zindex="200"
                             headerClass="headerClassACliente"
                             resizeable="false" style="overflow :auto" >
                <f:facet name="header">
                    <h:outputText value="Cambiar Estado de Material"/>
                </f:facet>
                <a4j:form id="formEditar">
                    <h:panelGroup id="contenidoEditarEstadoMaterialEtiqueta">
                        <h:panelGrid columns="6">
                            <h:outputText value="Fecha Ingreso" styleClass="outputText1" />
                            <h:outputText value="::" styleClass="outputText1" />
                            <h:outputText value="#{ManagedEstadosMaterial.ingresosAlmacen.fechaIngresoAlmacen}" styleClass="outputText1" />

                            <h:outputText value="Nro. Ingreso" styleClass="outputText1" />
                            <h:outputText value="::" styleClass="outputText1" />
                            <h:outputText value="#{ManagedEstadosMaterial.ingresosAlmacen.nroIngresoAlmacen}" styleClass="outputText1" />


                            <h:outputText value="Estado Ingreso" styleClass="outputText1" />
                            <h:outputText value="::" styleClass="outputText1" />
                            <h:outputText value="#{ManagedEstadosMaterial.ingresosAlmacen.estadosIngresoAlmacen.nombreEstadoIngresoAlmacen}" styleClass="outputText1" />


                            <h:outputText value="Tipo Ingreso Almacen" styleClass="outputText1" />
                            <h:outputText value="::" styleClass="outputText1" />
                            <h:outputText value="#{ManagedEstadosMaterial.ingresosAlmacen.tiposIngresoAlmacen.nombreTipoIngresoAlmacen}" styleClass="outputText1" />


                            <h:outputText value="Proveedor" styleClass="outputText1" />
                            <h:outputText value="::" styleClass="outputText1" />
                            <h:outputText value="#{ManagedEstadosMaterial.ingresosAlmacen.proveedores.nombreProveedor}" styleClass="outputText1" />


                        </h:panelGrid>

                        <br/>
                        <%--inicio ale mostrar--%>
                    <center><div style="overflow:auto;text-align:center;height:100px;width:40%" >
                            <rich:dataTable value="#{ManagedEstadosMaterial.ingresosAlmacenDetalleMostrarList}"
                                            var="data"
                                            id="dataIngresosAlmacenDetalleMostrar"
                                            onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                            onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                            headerClass="headerClassACliente" style="overflow:auto;text-align:center;height:100px;" >

                                <rich:column>
                                    <f:facet name="header">
                                        <h:outputText value="Item"  />
                                    </f:facet>
                                    <h:outputText  value="#{data.materiales.nombreMaterial}"  />
                                </rich:column>

                                <rich:column>
                                    <f:facet name="header">
                                        <h:outputText value="Cantidad"  />
                                    </f:facet>
                                    <h:outputText value="#{data.cantTotalIngresoFisico}">
                                    </h:outputText>
                                </rich:column>
                                <rich:column>
                                    <f:facet name="header">
                                        <h:outputText value="Unid."  />
                                    </f:facet>
                                    <h:outputText value="#{data.unidadesMedida.nombreUnidadMedida}">
                                    </h:outputText>
                                </rich:column>
                            </rich:dataTable>
                        </div></center>
                        <%--final ale mostrar--%>

                    <rich:dataTable value="#{ManagedEstadosMaterial.ingresosAlmacenDetalleEstadoList}"
                                    var="data"
                                    id="dataIngresosAlmacenDetalleEstado"
                                    onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                    onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                    headerClass="headerClassACliente" >

                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value=""  />
                            </f:facet>
                            <h:selectBooleanCheckbox value="#{data.checked}" >

                            </h:selectBooleanCheckbox>
                        </rich:column>

                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value="Fecha Ingreso"  />
                            </f:facet>
                            <h:outputText  value="#{data.ingresosAlmacenDetalle.ingresosAlmacen.fechaIngresoAlmacen}"  />
                        </rich:column>

                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value="Nro Ingreso"  />
                            </f:facet>
                            <h:outputText value="#{data.ingresosAlmacenDetalle.ingresosAlmacen.nroIngresoAlmacen}">
                            </h:outputText>
                        </rich:column>
                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value="Fecha Vencimiento"  />
                            </f:facet>
                            <h:outputText value="#{data.fechaVencimiento}"  />
                        </rich:column>
                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value="Etiqueta"  />
                            </f:facet>
                            <h:outputText value="#{data.etiqueta}"  />
                        </rich:column>
                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value="Item"  />
                            </f:facet>
                            <h:outputText value="#{data.ingresosAlmacenDetalle.materiales.nombreMaterial}"  />
                        </rich:column>
                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value="Cantidad Parcial"  />
                            </f:facet>
                            <h:outputText value="#{data.cantidadParcial}"  />
                        </rich:column>
                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value="Unid."  />
                            </f:facet>
                            <h:outputText value="#{data.ingresosAlmacenDetalle.unidadesMedida.nombreUnidadMedida}"  />
                        </rich:column>

                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value="Seccion"  />
                            </f:facet>
                            <h:outputText value="#{data.ingresosAlmacenDetalle.secciones.nombreSeccion}"  />
                        </rich:column>

                        <rich:column style="background-color: #{data.colorFila}">
                            <f:facet name="header">
                                <h:outputText value="Estado Material"  />
                            </f:facet>
                            <h:outputText value="#{data.estadosMaterial.nombreEstadoMaterial}"  />
                        </rich:column>

                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value="Empaque"  />
                            </f:facet>
                            <h:outputText value="#{data.empaqueSecundarioExterno.nombreEmpaqueSecundarioExterno}"  />
                        </rich:column>

                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value="Lote"  />
                            </f:facet>
                            <h:outputText value="#{data.loteMaterialProveedor}"  />
                        </rich:column>
                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value="Valoracion Porcentual"  />
                            </f:facet>
                            <h:outputText value="#{data.valoracionCCPorcentual}"  />
                        </rich:column>
                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value="observaciones"  />
                            </f:facet>
                            <h:outputText value="#{data.observaciones}"  />
                        </rich:column>

                    </rich:dataTable>
                </h:panelGroup>
                <div align="center">
                    <a4j:commandButton styleClass="btn" value="Cambiar Estado"
                                       onclick="if(validarAlMenosUno('formEditar:dataIngresosAlmacenDetalleEstado')==false){return false;}else{javascript:Richfaces.showModalPanel('panelEditarEstadoEtiqueta');}"
                                       action="#{ManagedEstadosMaterial.editarEtiqueta_action}"
                                       reRender="contenidoEditarEstadoEtiqueta"
                                       />
                    <input type="button" value="Aceptar" onclick="javascript:Richfaces.hideModalPanel('panelEditarEstadoMaterialEtiqueta')" class="btn" />
                </div>


            </a4j:form>
        </rich:modalPanel>

        <rich:modalPanel id="panelEditarEstadoEtiqueta" minHeight="340"  minWidth="800"
                         height="340" width="800"
                         zindex="300"
                         headerClass="headerClassACliente"
                         resizeable="false" style="overflow :auto" >
            <f:facet name="header">
                <h:outputText value="Editar Estado Etiqueta"/>
            </f:facet>
            <a4j:form>
                <h:panelGroup id="contenidoEditarEstadoEtiqueta">
                    <h:panelGrid columns="9">
                        <h:outputText value="Fecha Ingreso" styleClass="outputText1" />
                        <h:outputText value="::" styleClass="outputText1" />
                        <h:outputText value="#{ManagedEstadosMaterial.ingresosAlmacen.fechaIngresoAlmacen}" styleClass="outputText1" />

                        <h:outputText value="Nro. Ingreso" styleClass="outputText1" />
                        <h:outputText value="::" styleClass="outputText1" />
                        <h:outputText value="#{ManagedEstadosMaterial.ingresosAlmacen.nroIngresoAlmacen}" styleClass="outputText1" />

                        <h:outputText value="Etiqueta" styleClass="outputText1" />
                        <h:outputText value="::" styleClass="outputText1" />
                        <h:outputText value="#{ManagedEstadosMaterial.ingresosAlmacenDetalleEstado.etiqueta}" styleClass="outputText1" />

                        <h:outputText value="Empaque" styleClass="outputText1" />
                        <h:outputText value="::" styleClass="outputText1" />
                        <h:outputText value="#{ManagedEstadosMaterial.ingresosAlmacenDetalleEstado.empaqueSecundarioExterno.nombreEmpaqueSecundarioExterno}" styleClass="outputText1" />

                        <h:outputText value="Fecha Vencimiento" styleClass="outputText1" rendered="#{ManagedEstadosMaterial.ingresosAlmacenDetalleEstado.ingresosAlmacenDetalleEstadoFechaVencimiento==null}"/>
                        <h:outputText value="Nueva Fecha de Vencimiento" styleClass="outputText1" rendered="#{ManagedEstadosMaterial.ingresosAlmacenDetalleEstado.ingresosAlmacenDetalleEstadoFechaVencimiento!=null}"/>
                        <h:outputText value="::" styleClass="outputText1" />
                        <h:outputText value="#{ManagedEstadosMaterial.ingresosAlmacenDetalleEstado.fechaVencimiento}" rendered="#{ManagedEstadosMaterial.ingresosAlmacenDetalleEstado.ingresosAlmacenDetalleEstadoFechaVencimiento==null}" styleClass="outputText1" >
                            <f:convertDateTime pattern="dd/MM/yyyy" />
                        </h:outputText>
                        <h:inputText value="#{ManagedEstadosMaterial.ingresosAlmacenDetalleEstado.ingresosAlmacenDetalleEstadoFechaVencimiento.fechaVencimiento}" rendered="#{ManagedEstadosMaterial.ingresosAlmacenDetalleEstado.ingresosAlmacenDetalleEstadoFechaVencimiento!=null}" styleClass="inputText">
                            <f:convertDateTime pattern="dd/MM/yyyy"/>
                        </h:inputText>

                        <h:outputText value="Fecha Reanalisis" styleClass="outputText1" />
                        <h:outputText value="::" styleClass="outputText1" />
                        <h:outputText value="#{ManagedEstadosMaterial.ingresosAlmacenDetalleEstado.fechaReanalisis}" styleClass="outputText1" >
                            <f:convertDateTime pattern="dd/MM/yyyy" />
                        </h:outputText>

                        <h:outputText value="Nombre Material" styleClass="outputText1" />
                        <h:outputText value="::" styleClass="outputText1" />
                        <h:outputText value="#{ManagedEstadosMaterial.ingresosAlmacenDetalleEstado.ingresosAlmacenDetalle.materiales.nombreMaterial}" styleClass="outputText1" />

                        <h:outputText value="Estado Material" styleClass="outputText1" />
                        <h:outputText value="::" styleClass="outputText1" />
                        <h:selectOneMenu value="#{ManagedEstadosMaterial.ingresosAlmacenDetalleEstado.estadosMaterial.codEstadoMaterial}" styleClass="inputText" >
                            <f:selectItems value="#{ManagedEstadosMaterial.estadosMaterialCambioSelectList}"  />
                        </h:selectOneMenu>

                        <h:outputText value="Obs Control de Calidad" styleClass="outputText1" />
                        <h:outputText value="::" styleClass="outputText1" />
                        <h:selectOneRadio value="#{ManagedEstadosMaterial.ingresosAlmacenDetalleEstado.obsControlCalidad}" styleClass="outputText2">
                            <f:selectItem  itemValue="1" itemLabel="Si"  />
                            <f:selectItem  itemValue="0" itemLabel="No" />
                        </h:selectOneRadio>
                    </h:panelGrid>

                    <h:panelGrid columns="6">
                        <h:outputText value="Observaciones" styleClass="outputText2" />
                        <h:outputText value="::" styleClass="outputText1" />
                        <h:inputTextarea  value="#{ManagedEstadosMaterial.ingresosAlmacenDetalleEstado.observaciones}" styleClass="outputText2" cols="50" rows="5" />
                    </h:panelGrid>
                    <h:panelGrid columns="6">
                        <h:outputText value="Valoración % Control Calidad" styleClass="outputText2" />
                        <h:outputText value="::" styleClass="outputText1" />
                        <h:inputText  value="#{ManagedEstadosMaterial.ingresosAlmacenDetalleEstado.valoracionCCPorcentual}" styleClass="outputText2" />
                    </h:panelGrid>
                </h:panelGroup>
                <br/>
                <div align="center">
                    <a4j:commandButton styleClass="btn" value="Guardar" 
                                       action="#{ManagedEstadosMaterial.guardarModificarEstadoItem_action}"
                                       reRender="contenidoEditarEstadoMaterialEtiqueta"
                                       oncomplete="if(#{ManagedEstadosMaterial.mensaje eq '1'}){alert('Se registro el cambio de estado');javascript:Richfaces.hideModalPanel('panelEditarEstadoEtiqueta');}
                                       else{alert('#{ManagedEstadosMaterial.mensaje}');}"
                                       />
                    <a4j:commandButton styleClass="btn" value="Cancelar" onclick="javascript:Richfaces.hideModalPanel('panelEditarEstadoEtiqueta');"
                                       />
                </div>
            </a4j:form>
        </rich:modalPanel>





        


        

        <rich:modalPanel id="panelBuscarIngresoAlmacen"  minHeight="220"  minWidth="1000"
                         height="220" width="1000"
                         zindex="200"
                         headerClass="headerClassACliente"
                         resizeable="false" style="overflow :auto" >
            <f:facet name="header">
                <h:outputText value="Buscar Ingreso Almacen"/>
            </f:facet>
            <a4j:form>
                <h:panelGroup id="contenidoBuscarIngresoAlmacen">
                    <h:panelGrid columns="4">

                        <h:outputText value="Nro Ingreso:" styleClass="outputText1" />
                        <h:inputText value="#{ManagedEstadosMaterial.ingresosAlmacenDetalleEstadoBuscar.ingresosAlmacenDetalle.ingresosAlmacen.nroIngresoAlmacen}" styleClass="inputText" size="5" onkeypress="valNum(event);" />

                        <h:outputText value="Capitulo:"  styleClass="outputText1" />
                        <h:selectOneMenu styleClass="inputText" value="#{ManagedEstadosMaterial.ingresosAlmacenDetalleEstadoBuscar.ingresosAlmacenDetalle.materiales.grupos.capitulos.codCapitulo}"  >
                            <f:selectItems value="#{ManagedEstadosMaterial.capitulosList}"  />
                            <a4j:support event="onchange" action="#{ManagedEstadosMaterial.capitulo_changed}" reRender="codGrupo" />
                        </h:selectOneMenu>


                        <h:outputText value="Grupo:" styleClass="outputText1" />
                        <h:selectOneMenu value="#{ManagedEstadosMaterial.ingresosAlmacenDetalleEstadoBuscar.ingresosAlmacenDetalle.materiales.grupos.codGrupo}" styleClass="inputText" id="codGrupo" >
                            <f:selectItems value="#{ManagedEstadosMaterial.gruposList}"  />
                        </h:selectOneMenu>

                        <h:outputText value="Nombre:" styleClass="outputText1" />
                        <h:inputText value="#{ManagedEstadosMaterial.ingresosAlmacenDetalleEstadoBuscar.ingresosAlmacenDetalle.materiales.nombreMaterial}" styleClass="inputText" size="50"  >

                        </h:inputText> <%--onkeypress="valNum(event);"--%>


                        <h:outputText value="Fecha Inicial Ingreso:" styleClass="outputText1" />
                        <rich:calendar   datePattern="dd/MM/yyyy" styleClass="inputText"
                                         value="#{ManagedEstadosMaterial.fechaInicial}"  enableManualInput="true" oninputblur="if(this.value!=''){valFecha(this);}" width="1" />


                        <h:outputText value="Fecha Final Ingreso:" styleClass="outputText1" />
                        <rich:calendar   datePattern="dd/MM/yyyy" styleClass="inputText"
                                         value="#{ManagedEstadosMaterial.fechaFinal}"  enableManualInput="true" oninputblur="if(this.value!=''){valFecha(this);}" width="1" />

                        <h:outputText value="Estados Material:" styleClass="outputText1" />
                        <h:selectOneMenu value="#{ManagedEstadosMaterial.ingresosAlmacenDetalleEstadoBuscar.estadosMaterial.codEstadoMaterial}" styleClass="inputText" >
                            <f:selectItems value="#{ManagedEstadosMaterial.estadosMaterialList}"  />

                        </h:selectOneMenu>

                        <h:outputText value="Tipo Ingreso:" styleClass="outputText1" />
                        <h:selectOneMenu value="#{ManagedEstadosMaterial.ingresosAlmacenDetalleEstadoBuscar.ingresosAlmacen.tiposIngresoAlmacen.codTipoIngresoAlmacen}"  styleClass="inputText">
                            <f:selectItems value="#{ManagedEstadosMaterial.tiposIngresosList}"/>

                        </h:selectOneMenu>

                        <%--h:outputText value="Filial:" styleClass="outputText1" />
                        <h:outputText value="#{ManagedEstadosMaterial.ingresosAlmacenDetalleEstadoBuscar.ingresosAlmacenDetalle.ingresosAlmacen.almacenes.filiales.nombreFilial}" styleClass="outputText1" /--%>

                        <h:outputText value="Almacen:" styleClass="outputText1" />
                        <h:outputText value="#{ManagedEstadosMaterial.ingresosAlmacenDetalleEstadoBuscar.ingresosAlmacenDetalle.ingresosAlmacen.almacenes.nombreAlmacen}" styleClass="outputText1" />
                        <h:outputText value="Gestión:" styleClass="outputText1" />
                        <h:selectOneMenu value="#{ManagedEstadosMaterial.ingresosAlmacenDetalleEstadoBuscar.ingresosAlmacen.gestiones.codGestion}" styleClass="inputText">
                            <f:selectItem itemValue="0" itemLabel="TODOS"/>
                            <f:selectItems value="#{ManagedEstadosMaterial.gestionesSelectList}"/>
                        </h:selectOneMenu>
                        <h:outputText value="Lote Material:" styleClass="outputText1" />
                        <h:inputText value="#{ManagedEstadosMaterial.ingresosAlmacenDetalleEstadoBuscar.loteMaterialProveedor}" styleClass="inputText"/>
                    </h:panelGrid>
                    <div align="center">
                        <a4j:commandButton styleClass="btn" value="Buscar" onclick="" oncomplete="javascript:Richfaces.hideModalPanel('panelBuscarIngresoAlmacen');"
                                           action="#{ManagedEstadosMaterial.buscarIngresoAlmacen_action}"
                                           reRender="dataIngresosAlmacen"
                                           />
                        <input type="button" value="Cancelar" onclick="javascript:Richfaces.hideModalPanel('panelBuscarIngresoAlmacen')" class="btn" />
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
        <a4j:include viewId="editarEstadoEtiquetaPorIngresoAlmacen.jsp" id="modal4"/>
        <a4j:include viewId="editarEstadoEtiquetaPorLoteProveedor.jsp" id="modalEstadoLote"/>
    


        </f:view>
    </body>
</html>


