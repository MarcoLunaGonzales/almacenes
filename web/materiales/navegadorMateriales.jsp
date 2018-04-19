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
            <script type="text/javascript">
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
                function actualizaValoresMuestreo(valor) {

                    if (document.getElementById("form4:cantidadMaximaMuestreo") != null) {
                        document.getElementById("form4:cantidadMaximaMuestreo").value = 0
                    }
                    ;
                    if (document.getElementById("form4:cantidadMinimaMuestreo") != null) {
                        document.getElementById("form4:cantidadMinimaMuestreo").value = 0
                    }
                    ;

                }
                function actualizaValoresMuestreo1(valor) {

                    if (document.getElementById("form2:cantidadMaximaMuestreo") != null) {
                        document.getElementById("form2:cantidadMaximaMuestreo").value = 0
                    }
                    ;
                    if (document.getElementById("form2:cantidadMinimaMuestreo") != null) {
                        document.getElementById("form2:cantidadMinimaMuestreo").value = 0
                    }
                    ;

                }
                function seleccionar(nametable) {
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
                    }
                    if (count1 > 1) {
                        alert('Seleccione solo un registro');
                        return false;
                    }
                    return true;
                }
                function verDetalleFormulasMaterial(codMaterial)
                {
                    window.open('reporteFormulaMaestraMaterial.jsf?codMaterial=' + codMaterial + '&data=' + (new Date()).getTime().toString(), 'detalle' + Math.round((Math.random() * 1000)), 'top=50,left=200,width=800,height=500,scrollbars=1,resizable=1');
                }
                function openPopup(url){
                    window.open(url,'Material','top=50,left=200,width=800,height=500,scrollbars=1,resizable=1');
        }
            </script>
        </head>
        <body>
            <a4j:form id="form1">

                <div align="center">
                    <h:outputText value="Materiales" />  <br/><br/>
                    <rich:panel id="panelEstadoCuentas" styleClass="panelBuscar" style="top:50px;left:50px;width:700px;">
                        <f:facet name="header">
                            <h:outputText value="<div   onmouseover=\"this.style.cursor='move'\" onmousedown=\"comienzoMovimiento(event, 'form1:panelEstadoCuentas');\"  >Buscar<div   style=\"margin-left:550px;\"   onclick=\"document.getElementById('form1:panelEstadoCuentas').style.visibility='hidden';document.getElementById('panelsuper').style.visibility='hidden';\" onmouseover=\"this.style.cursor='hand'\"   >Cerrar</div> </div> "
                                          escape="false" />
                        </f:facet>
                    </rich:panel>

                    <h:outputText value="#{ManagedMateriales.cargarContenidoMateriales}"  />
                    <%--h:outputText value="Capitulos :" styleClass="outputText1" />
                    <h:selectOneMenu value="#{ManagedMateriales.materiales.grupos.capitulos.codCapitulo}" styleClass="inputText" >
                        <f:selectItems value="#{ManagedMateriales.capitulosList}" />
                        <a4j:support event="onchange" action="#{ManagedMateriales.capitulosFiltro_change}" reRender="codCapituloFiltro,dataMateriales" />
                    </h:selectOneMenu>
                    <h:outputText value="Grupos :" styleClass="outputText1" />
                    <h:selectOneMenu value="#{ManagedMateriales.materiales.grupos.codGrupo}" styleClass="inputText"  id = "codCapituloFiltro">
                        <f:selectItems value="#{ManagedMateriales.gruposList}" />
                        <a4j:support event="onchange" action="#{ManagedMateriales.gruposFiltro_change}" reRender="dataMateriales" />
                    </h:selectOneMenu--%>
                    <%--inicio ale buscador--%>
                    <a4j:commandLink onclick="Richfaces.showModalPanel('panelBuscarMaterial')">
                        <h:outputText value="Buscar" styleClass="outputText1" />
                        <h:graphicImage url="../img/buscar.png">

                        </h:graphicImage>
                    </a4j:commandLink>
                    <%--final ale buscador--%>
                    <br /> <br />


                    <rich:dataTable value="#{ManagedMateriales.materialesList}"
                                    var="data" id="dataMateriales"
                                    onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                    onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                    headerClass="headerClassACliente" width="100%">
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Capitulo"  />
                            </f:facet>
                            <h:outputText value="#{data.grupos.capitulos.nombreCapitulo}" />                                    
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Grupo"  />
                            </f:facet>
                            <h:outputText value="#{data.grupos.nombreGrupo}"  />
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Material"  />
                            </f:facet>
                            <h:outputText value="#{data.nombreMaterial}"  />
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Codigo Material"  />
                            </f:facet>
                            <h:outputText value="#{data.codMaterial}"  />
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Nombre Comercial Material"  />
                            </f:facet>
                            <h:outputText value="#{data.nombreComercialMaterial}"  />
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Unidad Medida"  />
                            </f:facet>
                            <h:outputText value="#{data.unidadesMedida.nombreUnidadMedida}"  />
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Movimiento Item"  />
                            </f:facet>
                            <h:outputText value="SI" rendered="#{data.movimientoItem eq 1}" />
                            <h:outputText value="NO" rendered="#{data.movimientoItem eq 2}" />
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Estado"  />
                            </f:facet>
                            <h:outputText value="#{data.estadoRegistro.nombreEstadoRegistro}"  />
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Acciones"  />
                            </f:facet>
                            <rich:dropDownMenu >
                                <f:facet name="label">
                                    <h:outputText value="Acciones"/>
                                </f:facet>

                                <rich:menuItem submitMode="ajax" value="Editar" >
                                    <a4j:support event="onclick" 
                                                 action="#{ManagedMateriales.editarMaterial_action}"
                                                 oncomplete="Richfaces.showModalPanel('panelEditarMaterial')" 
                                                 reRender="contenidoEditarMaterial" 
                                                 >
                                        <f:setPropertyActionListener value="#{data}" target="#{ManagedMateriales.materiales}"/>
                                    </a4j:support>    
                                </rich:menuItem>

                                <rich:menuItem submitMode="ajax" value="Eliminar" >
                                    <a4j:support event="onclick" 
                                                 onsubmit="if(!confirm('¿Esta seguro de eliminar el material?')){return false;}"
                                                 action="#{ManagedMateriales.eliminarMaterial_action}"
                                                 oncomplete="if(#{ManagedMateriales.existTransacciones}){alert('#{ManagedMateriales.mensaje}');}else{Richfaces.showModalPanel('panelEliminarMaterial')}" 
                                                 reRender="contenidoEliminarMaterial"  >
                                        <f:setPropertyActionListener value="#{data}" target="#{ManagedMateriales.materiales}"/>
                                    </a4j:support>    
                                </rich:menuItem>
                                
                                <rich:menuItem submitMode="ajax" value="Imprimir" >
                                    <a4j:support event="onclick" 
                                                 action="#{ManagedMateriales.verReporteMaterialLog_action}"
                                                 oncomplete="openPopup('reporteMaterialLog.jsf');" 
                                                 reRender="contenidoEliminarMaterial"  >
                                        <f:setPropertyActionListener value="#{data}" target="#{ManagedMateriales.materiales}"/>
                                    </a4j:support>    
                                </rich:menuItem>
                                
                                <rich:menuItem submitMode="ajax" value="Editar SU" rendered="#{ManagedMateriales.permisoEditarDirectamenteMaterial}">
                                    <a4j:support event="onclick" 
                                                 action="#{ManagedMateriales.editarMaterialSU_action}"
                                                 oncomplete="Richfaces.showModalPanel('panelEditarMaterial')" 
                                                 reRender="contenidoEditarMaterial">
                                        <f:setPropertyActionListener value="#{data}" target="#{ManagedMateriales.materiales}"/>
                                    </a4j:support>    
                                </rich:menuItem>
                                

                            </rich:dropDownMenu> 
                        </h:column>

                    </rich:dataTable>

                    <a4j:commandButton value="Agregar" action="#{ManagedMateriales.agregarMaterial_action}" reRender="contenidoAgregarMaterial" oncomplete="Richfaces.showModalPanel('panelAgregarMaterial')" styleClass="btn"  />
                   
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

            <rich:modalPanel id="panelAgregarMaterial" minHeight="450"  minWidth="800"
                             height="500" width="800"  zindex="200"  headerClass="headerClassACliente"
                             resizeable="false" style="overflow :auto" >
                <f:facet name="header">
                    <h:outputText value="Agregar Material"/>
                </f:facet>
                <a4j:form id="form2">
                    <h:panelGroup id="contenidoAgregarMaterial">
                        <div align="center">
                            <h:panelGrid columns="4" style="text-align:left;width:100%" >
                                <h:outputText value="Capitulo :" styleClass="outputText1" />
                                <h:selectOneMenu value="#{ManagedMateriales.materiales.grupos.capitulos.codCapitulo}" styleClass="inputText" style="width:250px" >
                                    <f:selectItems value="#{ManagedMateriales.capitulosList}" />
                                    <a4j:support action="#{ManagedMateriales.capitulos_change}" reRender="codGrupo" event="onchange" />
                                </h:selectOneMenu>
                                <h:outputText value="Grupos :" styleClass="outputText1"  />
                                <h:selectOneMenu value="#{ManagedMateriales.materiales.grupos.codGrupo}" id="codGrupo" styleClass="inputText" >
                                    <f:selectItems value="#{ManagedMateriales.gruposList}" />
                                    <a4j:support action="#{ManagedMateriales.grupo_onchage}" reRender="contenidoAgregarMaterial" event="onchange" />
                                </h:selectOneMenu>
                            </h:panelGrid>
                            <h:panelGroup style="text-align:center;width:100%">
                                <h:outputText value="Nombre de Material :" styleClass="outputText1"  />
                                <h:inputText value="#{ManagedMateriales.materiales.nombreMaterial}" styleClass="inputText" size="40" style="font-size:15px;font-weight:bold;" />

                                <a4j:commandButton value="Buscar materiales similares" 
                                                   action="#{ManagedMateriales.buscarMaterial_action}" 
                                                   image="../img/buscar.png"
                                                   styleClass="btn"  
                                                   reRender="dataBuscarMaterial"  
                                                   timeout="7200" 

                                                   />


                                <rich:dataTable value="#{ManagedMateriales.materialesBuscarList}" var="data"
                                                id="dataBuscarMaterial"
                                                onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                                onRowMouseOver="this.style.backgroundColor='#DDE3E4';"                                    
                                                headerClass="headerClassACliente"
                                                >


                                    <rich:column style="background-color: #{data.colorFila}">
                                        <f:facet name="header">
                                            <h:outputText value="Materiales coincidentes"  />
                                        </f:facet>

                                        <h:outputText  value="#{data.nombreMaterial}" styleClass="outputText2" />

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
                                <h:outputText value="Nombre de Material para Compra :" styleClass="outputText1" />
                                <h:inputText value="#{ManagedMateriales.materiales.nombreComercialMaterial}" styleClass="inputText" size="40" />
                                <h:outputText value=""></h:outputText>
                                <h:outputText value=""></h:outputText>
                            </h:panelGroup>
                            <h:panelGrid columns="4" style="text-align:left;width:100%" >
                                <h:outputText value="Tipo Medida :" styleClass="outputText1" />
                                <h:selectOneMenu value="#{ManagedMateriales.materiales.unidadesMedida.tiposMedida.cod_tipo_medida}" styleClass="inputText" >
                                    <f:selectItems value="#{ManagedMateriales.tiposMedidaList}" />
                                    <a4j:support event="onchange" action="#{ManagedMateriales.tiposMedida_change}" reRender="codUnidadMedida" />
                                </h:selectOneMenu>
                                <h:outputText value="Unidad Medida :" styleClass="outputText1"  />
                                <h:selectOneMenu value="#{ManagedMateriales.materiales.unidadesMedida.codUnidadMedida}" styleClass="inputText" id="codUnidadMedida" >
                                    <f:selectItems value="#{ManagedMateriales.unidadesMedidaList}" />
                                </h:selectOneMenu>
                                <h:outputText value="Tipo Medida OC:" styleClass="outputText1" />
                                <h:selectOneMenu value="#{ManagedMateriales.materiales.unidadesMedidaCompra.tiposMedida.cod_tipo_medida}" styleClass="inputText" >
                                    <f:selectItems value="#{ManagedMateriales.tiposMedidaList}" />
                                    <a4j:support event="onchange" action="#{ManagedMateriales.tipoMedidaOC_change}" reRender="codUnidadMedidaCompra" />
                                </h:selectOneMenu>
                                <h:outputText value="Unidad Medida de Compra :" styleClass="outputText1"  />
                                <h:selectOneMenu value="#{ManagedMateriales.materiales.unidadesMedidaCompra.codUnidadMedida}" styleClass="inputText" id="codUnidadMedidaCompra" >
                                    <f:selectItems value="#{ManagedMateriales.unidadesMedidaCompraList}" />
                                </h:selectOneMenu>

                                <h:outputText value="Estado :" styleClass="outputText1" />
                                <h:inputText value="Activo" styleClass="inputText" readonly="true" />

                                <h:outputText value="Impresion :" styleClass="outputText1" />
                                <h:inputText value="#{ManagedMateriales.materiales.tipoImpresion}" styleClass="inputText" />
                                <h:outputText value="Acabado :" styleClass="outputText1" />
                                <h:inputText value="#{ManagedMateriales.materiales.acabado}" styleClass="inputText" />

                                <h:outputText value="Nro Colores Impresion :" styleClass="outputText1" />
                                <h:inputText value="#{ManagedMateriales.materiales.color}" styleClass="inputText" />
                                <h:outputText value="Tamaño de Item :" styleClass="outputText1" />
                                <h:inputText value="#{ManagedMateriales.materiales.tamanioMaterial}" styleClass="inputText" />

                                <h:outputText value="Gramaje :" styleClass="outputText1" />
                                <h:inputText value="#{ManagedMateriales.materiales.gramaje}" styleClass="inputText" />
                                <h:outputText value="Capacidad :" styleClass="outputText1" />
                                <h:inputText value="#{ManagedMateriales.materiales.capacidad}" styleClass="inputText" />


                                <h:outputText value="Peso Asociado :" styleClass="outputText1" />
                                <h:inputText value="#{ManagedMateriales.materiales.pesoAsociado}" styleClass="inputText" />
                                <h:outputText value="Unidad Medida Peso Asociado :" styleClass="outputText1" />
                                <h:selectOneMenu value="#{ManagedMateriales.materiales.unidadesMedidaPesoAsociado.codUnidadMedida}" styleClass="inputText" >
                                    <f:selectItems value="#{ManagedMateriales.unidadMedidaPesoAsociadoList}" />
                                </h:selectOneMenu>

                                <h:outputText value="Observacion" styleClass="outputText1" />

                                <h:inputTextarea rows="2" value="#{ManagedMateriales.materiales.obsMaterial}" />

                                <h:outputText value="" />
                                <h:outputText value="" />



                            </h:panelGrid>

                            <h:panelGrid columns="4" headerClass="headerClassACliente" style="width:100%">
                                <f:facet name="header">
                                    <h:outputText value="Datos Produccion" />
                                </f:facet>

                                <h:outputText value="Grupo Stock :" styleClass="outputText1" />
                                <h:selectOneMenu value="#{ManagedMateriales.materiales.gruposStockMateriales.codGrupoStockMaterial}" styleClass="inputText" >
                                    <f:selectItems value="#{ManagedMateriales.gruposStockMaterialesList}" />
                                </h:selectOneMenu>

                                <h:outputText value="Material de Produccion :" styleClass="outputText1" />
                                <h:selectOneRadio value="#{ManagedMateriales.materiales.materialProduccion}" styleClass="outputText1"  >
                                    <f:selectItem itemValue="1" itemLabel="si" />
                                    <f:selectItem itemValue="0" itemLabel="no" />
                                    <a4j:support event="onclick" action="#{ManagedMateriales.materialProduccion_change}" reRender="contenidoAgregarMaterial" oncomplete="actualizaValoresMuestreo1(#{ManagedMateriales.materiales.materialProduccion})" />
                                </h:selectOneRadio>


                                <h:outputText value="Material para muestreo :" styleClass="outputText1" id="codMaterialMuestreo"  rendered="#{ManagedMateriales.materiales.materialProduccion=='1'}" />
                                <h:selectOneRadio value="#{ManagedMateriales.materiales.materialMuestreo}" styleClass="outputText1" id="codMaterialMuestreo1" rendered="#{ManagedMateriales.materiales.materialProduccion=='1'}" >
                                    <f:selectItem itemValue="1" itemLabel="si" />
                                    <f:selectItem itemValue="0" itemLabel="no" />
                                    <a4j:support event="onclick" action="#{ManagedMateriales.materialProduccion_change}" reRender="contenidoAgregarMaterial" oncomplete="actualizaValoresMuestreo1(#{ManagedMateriales.materiales.materialMuestreo})" />
                                </h:selectOneRadio>


                                <h:outputText value="Cantidad Minima Muestreo :" styleClass="outputText1" rendered="#{ManagedMateriales.materiales.materialProduccion=='1' && ManagedMateriales.materiales.materialMuestreo=='1'}" />
                                <h:inputText value="#{ManagedMateriales.materiales.cantidadMinimaMuestreo}" styleClass="inputText" rendered="#{ManagedMateriales.materiales.materialProduccion=='1'&&ManagedMateriales.materiales.materialMuestreo=='1'}" id="cantidadMinimaMuestreo" />
                                <h:outputText value="Cantidad Maxima Muestreo :" styleClass="outputText1" rendered="#{ManagedMateriales.materiales.materialProduccion=='1'&&ManagedMateriales.materiales.materialMuestreo=='1'}"  />
                                <h:inputText value="#{ManagedMateriales.materiales.cantidadMaximaMuestreo}" styleClass="inputText"  rendered="#{ManagedMateriales.materiales.materialProduccion=='1'&&ManagedMateriales.materiales.materialMuestreo=='1'}" id="cantidadMaximaMuestreo" />

                                <h:outputText value="Item con Movimiento :" styleClass="outputText1" id="codMovimientoItem11"   />
                                <h:selectOneRadio value="#{ManagedMateriales.materiales.movimientoItem}" styleClass="outputText1" id="codMovimientoItem22"  >
                                    <f:selectItem itemValue='1' itemLabel="si" />
                                    <f:selectItem itemValue='2' itemLabel="no" />

                                </h:selectOneRadio>
                                
                                <h:outputText value="Material de Almacén :" styleClass="outputText1" id="codMaterialAlmacenItem11"   />
                                <h:selectOneRadio value="#{ManagedMateriales.materiales.materialAlmacen}" styleClass="outputText1" id="codMaterialAlmacen22"  >
                                    <f:selectItem itemValue='1' itemLabel="si" />
                                    <f:selectItem itemValue='0' itemLabel="no" />

                                </h:selectOneRadio>
                                <h:outputText value="Material Promocional:" styleClass="outputText1" id="codPremioItem11"   />
                                <h:selectOneRadio value="#{ManagedMateriales.materiales.premio}" styleClass="outputText1" id="codPremioItem22"  >
                                    <f:selectItem itemValue='1' itemLabel="si" />
                                    <f:selectItem itemValue='0' itemLabel="no" />

                                </h:selectOneRadio>
                                
                                <h:outputText value="Línea Material Apoyo:" styleClass="outputText1" rendered="#{ManagedMateriales.materiales.grupos.codGrupo=='12'}" />
                                <h:selectOneMenu value="#{ManagedMateriales.materiales.codTipoApoyoSeleccionado}" styleClass="inputText" rendered="#{ManagedMateriales.materiales.grupos.codGrupo=='12'}">
                                    <f:selectItems value="#{ManagedMateriales.lineasApoyoList}" />
                                </h:selectOneMenu>
                                <h:outputText value="Tipo Material Apoyo:" styleClass="outputText1" rendered="#{ManagedMateriales.materiales.grupos.codGrupo=='12'}"  />
                                <h:selectOneMenu value="#{ManagedMateriales.materiales.codLineaApoyoSeleccionado}" styleClass="inputText" rendered="#{ManagedMateriales.materiales.grupos.codGrupo=='12'}">
                                    <f:selectItems value="#{ManagedMateriales.tiposApoyoList}" />
                                </h:selectOneMenu>
                            </h:panelGrid>
                            <br/>

                            <a4j:commandButton styleClass="btn" value="Guardar"
                                               action="#{ManagedMateriales.guardarMaterial_action}"
                                               reRender="dataMateriales" 

                                               oncomplete="if(#{ManagedMateriales.mensaje eq '1'}){alert('Se guardo correctamente el material.');Richfaces.hideModalPanel('panelAgregarMaterial');}else
                                               {if(#{ManagedMateriales.mensaje eq '5'}){alert('Se produjo un error al guardar el material. Consultar con Sistemas.')}else{alert('#{ManagedMateriales.mensaje}');Richfaces.hideModalPanel('panelAgregarMaterial');}}"
                                               onclick="if(confirm('Esta seguro de guardar la informacion?')==false){return false;}"/>
                            <input type="button" value="Cancelar" onclick="javascript:Richfaces.hideModalPanel('panelAgregarMaterial')" class="btn"  />
                        </div>
                    </h:panelGroup>
                </a4j:form>
            </rich:modalPanel>

            <rich:modalPanel id="panelEditarMaterial" minHeight="450"  minWidth="800"
                             height="500" width="800"  zindex="200"  headerClass="headerClassACliente"
                             resizeable="false" style="overflow :auto" >
                <f:facet name="header">
                    <h:outputText value="Editar Material"/>
                </f:facet>
                <a4j:form id="form4">
                    <h:panelGroup id="contenidoEditarMaterial">
                        <div align="center">
                            <h:panelGrid columns="1">
                                <h:outputText value="Motivo/Observación de la Edición:" styleClass="outputText1" />
                                <h:inputTextarea value="#{ManagedMateriales.descripcionTransaccion}" styleClass="inputText" cols="100" rows="2" />
                            </h:panelGrid>
                            <h:panelGrid columns="4" style="text-align:left;width:100%">
                                <h:outputText value="Capitulo :" styleClass="outputText1"  />
                                <h:selectOneMenu value="#{ManagedMateriales.materiales.grupos.capitulos.codCapitulo}" 
                                                 styleClass="inputText" style="width:200px" disabled="#{ManagedMateriales.existTransacciones}">
                                    <f:selectItems value="#{ManagedMateriales.capitulosList}" />
                                    <a4j:support action="#{ManagedMateriales.capitulos_change}" reRender="codGrupo" event="onchange" />
                                </h:selectOneMenu>
                                <h:outputText value="Grupos :" styleClass="outputText1"  />
                                <h:selectOneMenu value="#{ManagedMateriales.materiales.grupos.codGrupo}" 
                                                 id="codGrupo" styleClass="inputText" disabled="#{ManagedMateriales.existTransacciones}">
                                    <f:selectItems value="#{ManagedMateriales.gruposList}" />
                                    <a4j:support action="#{ManagedMateriales.grupo_onchage}" reRender="panelDatosProduccion" event="onchange" />
                                </h:selectOneMenu>
                            </h:panelGrid>
                            <h:panelGroup style="text-align:center;width:100%">
                                <h:outputText value="Nombre de Material :" styleClass="outputText1"  />
                                <h:inputText value="#{ManagedMateriales.materiales.nombreMaterial}" 
                                             styleClass="inputText" size="40" 
                                             style="font-size:15px;font-weight:bold;" 
                                             disabled="#{ManagedMateriales.existTransacciones}"
                                             />

                                <a4j:commandButton value="Buscar materiales similares" 
                                                   action="#{ManagedMateriales.buscarMaterial_action}" 
                                                   image="../img/buscar.png"
                                                   styleClass="btn"  
                                                   reRender="dataBuscarMaterial"  
                                                   timeout="7200" 
                                                   disabled="#{ManagedMateriales.existTransacciones}"
                                                   />

                                <br />
                                <rich:dataTable value="#{ManagedMateriales.materialesBuscarList}" var="data"
                                                id="dataBuscarMaterial"
                                                onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                                onRowMouseOver="this.style.backgroundColor='#DDE3E4';"                                    
                                                headerClass="headerClassACliente"
                                                rendered="#{!ManagedMateriales.existTransacciones}"
                                                >


                                    <rich:column style="background-color: #{data.colorFila}">
                                        <f:facet name="header">
                                            <h:outputText value="Materiales coincidentes"  />
                                        </f:facet>

                                        <h:outputText  value="#{data.nombreMaterial}" styleClass="outputText2" />

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
                                <h:outputText value="Nombre de Material para Compra :" styleClass="outputText1" />
                                <h:inputText value="#{ManagedMateriales.materiales.nombreComercialMaterial}" 
                                             styleClass="inputText" size="40" 
                                             disabled="#{ManagedMateriales.existTransacciones}"/>
                                <h:outputText value=""></h:outputText>
                                <h:outputText value=""></h:outputText>
                            </h:panelGroup>
                            <h:panelGrid columns="4" style="text-align:left;width:100%" >
                                <h:outputText value="Tipo Medida OC:" styleClass="outputText1" />
                                <h:selectOneMenu value="#{ManagedMateriales.materiales.unidadesMedidaCompra.tiposMedida.cod_tipo_medida}" 
                                                 styleClass="inputText" disabled="#{ManagedMateriales.existTransacciones}">
                                    <f:selectItems value="#{ManagedMateriales.tiposMedidaList}" />
                                    <a4j:support event="onchange" action="#{ManagedMateriales.tipoMedidaOC_change}" reRender="codUnidadMedidaCompra" />
                                </h:selectOneMenu>
                                <h:outputText value="Unidad Medida de Compra :" styleClass="outputText1"  />
                                <h:selectOneMenu value="#{ManagedMateriales.materiales.unidadesMedidaCompra.codUnidadMedida}" 
                                                 styleClass="inputText" id="codUnidadMedidaCompra" 
                                                 disabled="#{ManagedMateriales.existTransacciones}">
                                    <f:selectItems value="#{ManagedMateriales.unidadesMedidaCompraList}" />
                                </h:selectOneMenu>
                                <h:outputText value="Tipo Medida :" styleClass="outputText1" />
                                <h:selectOneMenu value="#{ManagedMateriales.materiales.unidadesMedida.tiposMedida.cod_tipo_medida}" 
                                                 styleClass="inputText" 
                                                 disabled="#{ManagedMateriales.existTransacciones}">
                                    <f:selectItems value="#{ManagedMateriales.tiposMedidaList}" />
                                    <a4j:support event="onchange" action="#{ManagedMateriales.tiposMedida_change}" reRender="codUnidadMedida" />
                                </h:selectOneMenu>
                                <h:outputText value="Unidad Medida :" styleClass="outputText1"  />
                                <h:selectOneMenu value="#{ManagedMateriales.materiales.unidadesMedida.codUnidadMedida}" 
                                                 styleClass="inputText" id="codUnidadMedida" 
                                                 disabled="#{ManagedMateriales.existTransacciones}">
                                    <f:selectItems value="#{ManagedMateriales.unidadesMedidaList}" />
                                </h:selectOneMenu>


                                <h:outputText value="Estado :" styleClass="outputText1"  />
                                <h:panelGroup>
                                    <h:selectOneMenu value="#{ManagedMateriales.materiales.estadoRegistro.codEstadoRegistro}" styleClass="inputText" id="codEstadoRegistro"
                                                     disabled="#{ManagedMateriales.materiales.estadoRegistro.codEstadoRegistro eq '1' and ManagedMateriales.almacenesExistenciaMaterial.size() > 0}">
                                        <f:selectItems value="#{ManagedMateriales.estadosRegistroList}" />
                                    </h:selectOneMenu>
                                    <h:outputText value="<br/>El item no se puede inactivar ya que cuenta con la siguiente existencia:"
                                                  styleClass="outputText2" style="color:red" escape="false"
                                                  rendered="#{ManagedMateriales.materiales.estadoRegistro.codEstadoRegistro eq '1' and ManagedMateriales.almacenesExistenciaMaterial.size() > 0}"/>
                                    <rich:dataTable value="#{ManagedMateriales.almacenesExistenciaMaterial}"
                                                    rendered="#{ManagedMateriales.almacenesExistenciaMaterial.size() > 0}"
                                                    headerClass="headerClassACliente"
                                                    var="almacen">
                                        <f:facet name="header">
                                            <rich:columnGroup>
                                                <rich:column>
                                                    <h:outputText value="Almacen"/>
                                                </rich:column>
                                                <rich:column>
                                                    <h:outputText value="Cantidad Existencia"/>
                                                </rich:column>
                                            </rich:columnGroup>
                                        </f:facet>
                                        <rich:column>
                                            <h:outputText value="#{almacen.nombreAlmacen}"/>
                                        </rich:column>
                                        <rich:column style="text-align:right">
                                            <h:outputText value="#{almacen.cantidadExistente}">
                                                <f:convertNumber pattern="#,##0.00" locale="en"/>
                                            </h:outputText>
                                        </rich:column>
                                    </rich:dataTable>
                                </h:panelGroup>

                                <h:outputText value="Impresion :" styleClass="outputText1" />
                                <h:inputText value="#{ManagedMateriales.materiales.tipoImpresion}" styleClass="inputText" />
                                <h:outputText value="Acabado :" styleClass="outputText1" />
                                <h:inputText value="#{ManagedMateriales.materiales.acabado}" styleClass="inputText" />

                                <h:outputText value="Nro Colores Impresion :" styleClass="outputText1" />
                                <h:inputText value="#{ManagedMateriales.materiales.color}" styleClass="inputText" />
                                <h:outputText value="Tamaño de Item :" styleClass="outputText1" />
                                <h:inputText value="#{ManagedMateriales.materiales.tamanioMaterial}" styleClass="inputText" />

                                <h:outputText value="Gramaje :" styleClass="outputText1" />
                                <h:inputText value="#{ManagedMateriales.materiales.gramaje}" styleClass="inputText" />
                                <h:outputText value="Capacidad :" styleClass="outputText1" />
                                <h:inputText value="#{ManagedMateriales.materiales.capacidad}" styleClass="inputText" />


                                <h:outputText value="Peso Asociado :" styleClass="outputText1" />
                                <h:inputText value="#{ManagedMateriales.materiales.pesoAsociado}" styleClass="inputText" />
                                <h:outputText value="Unidad Medida Peso Asociado :" styleClass="outputText1" />
                                <h:selectOneMenu value="#{ManagedMateriales.materiales.unidadesMedidaPesoAsociado.codUnidadMedida}" styleClass="inputText" >
                                    <f:selectItems value="#{ManagedMateriales.unidadMedidaPesoAsociadoList}" />
                                </h:selectOneMenu>
                                <h:outputText value="Grupo Stock Material :" styleClass="outputText1" />
                                <h:selectOneMenu value="#{ManagedMateriales.materiales.gruposStockMateriales.codGrupoStockMaterial}" styleClass="inputText" >
                                    <f:selectItems value="#{ManagedMateriales.gruposStockMaterialesList}" />
                                </h:selectOneMenu>
                                <h:outputText value="Observacion" styleClass="outputText1" />
                                <h:inputTextarea rows="2" value="#{ManagedMateriales.materiales.obsMaterial}" styleClass="inputText" />
                            </h:panelGrid>

                            <h:panelGrid columns="4" headerClass="headerClassACliente" style="width:100%" id="panelDatosProduccion">
                                <f:facet name="header">
                                    <h:outputText value="Datos Produccion" />
                                </f:facet>

                                <h:outputText value="Grupo Stock :" styleClass="outputText1" />
                                <h:selectOneMenu value="#{ManagedMateriales.materiales.gruposStockMateriales.codGrupoStockMaterial}" styleClass="inputText" >
                                    <f:selectItems value="#{ManagedMateriales.gruposStockMaterialesList}" />
                                </h:selectOneMenu>

                                <h:outputText value="Material de Produccion :" styleClass="outputText1" />
                                <h:selectOneRadio value="#{ManagedMateriales.materiales.materialProduccion}" styleClass="outputText1" disabled="#{ManagedMateriales.existTransacciones}" >
                                    <f:selectItem itemValue="1" itemLabel="si" />
                                    <f:selectItem itemValue="0" itemLabel="no" />
                                    <a4j:support event="onclick" action="#{ManagedMateriales.materialProduccion_change}" reRender="contenidoEditarMaterial" oncomplete="actualizaValoresMuestreo(#{ManagedMateriales.materiales.materialMuestreo})" />
                                </h:selectOneRadio>

                                <h:outputText value="Material para muestreo :" styleClass="outputText1" id="codMaterialMuestreo"  rendered="#{ManagedMateriales.materiales.materialProduccion=='1'}" />
                                <h:selectOneRadio value="#{ManagedMateriales.materiales.materialMuestreo}" 
                                                  styleClass="outputText1" id="codMaterialMuestreo1" 
                                                  rendered="#{ManagedMateriales.materiales.materialProduccion=='1'}" 
                                                  disabled="#{ManagedMateriales.existTransacciones}">
                                    <f:selectItem itemValue="1" itemLabel="si" />
                                    <f:selectItem itemValue="0" itemLabel="no" />

                                    <a4j:support event="onclick" action="#{ManagedMateriales.materialProduccion_change}" reRender="contenidoEditarMaterial" oncomplete="actualizaValoresMuestreo(#{ManagedMateriales.materiales.materialMuestreo})" />
                                </h:selectOneRadio>
                                <h:outputText value="Cantidad Minima Muestreo :" styleClass="outputText1" rendered="#{ManagedMateriales.materiales.materialProduccion=='1' && ManagedMateriales.materiales.materialMuestreo=='1'}" />
                                <h:inputText value="#{ManagedMateriales.materiales.cantidadMinimaMuestreo}" styleClass="inputText" rendered="#{ManagedMateriales.materiales.materialProduccion=='1'&&ManagedMateriales.materiales.materialMuestreo=='1'}" id="cantidadMinimaMuestreo" />
                                <h:outputText value="Cantidad Maxima Muestreo :" styleClass="outputText1" rendered="#{ManagedMateriales.materiales.materialProduccion=='1'&&ManagedMateriales.materiales.materialMuestreo=='1'}"  />
                                <h:inputText value="#{ManagedMateriales.materiales.cantidadMaximaMuestreo}" styleClass="inputText"  rendered="#{ManagedMateriales.materiales.materialProduccion=='1'&&ManagedMateriales.materiales.materialMuestreo=='1'}" id="cantidadMaximaMuestreo" />
                                <h:outputText value="Item con Movimiento :" styleClass="outputText1" id="codMovimientoItem1"   />
                                <h:selectOneRadio value="#{ManagedMateriales.materiales.movimientoItem}" styleClass="outputText1" id="codMovimientoItem"  >
                                    <f:selectItem itemValue='1' itemLabel="si" />
                                    <f:selectItem itemValue='2' itemLabel="no" />

                                </h:selectOneRadio>
                                
                                <h:outputText value="Material de Almacén :" styleClass="outputText1" id="codMaterialAlmacenItem1"   />
                                <h:selectOneRadio value="#{ManagedMateriales.materiales.materialAlmacen}" styleClass="outputText1" id="codMaterialAlmacen"  >
                                    <f:selectItem itemValue='1' itemLabel="si" />
                                    <f:selectItem itemValue='0' itemLabel="no" />

                                </h:selectOneRadio>
                                <h:outputText value="Material Promocional :" styleClass="outputText1" id="codPremioItem1"   />
                                <h:selectOneRadio value="#{ManagedMateriales.materiales.premio}" styleClass="outputText1" id="codPremioItem"  >
                                    <f:selectItem itemValue='1' itemLabel="si" />
                                    <f:selectItem itemValue='0' itemLabel="no" />
                                </h:selectOneRadio>
                                
                                <h:outputText value="Línea Material Apoyo:" styleClass="outputText1" rendered="#{ManagedMateriales.materiales.grupos.codGrupo=='12'}" />
                                <h:selectOneMenu value="#{ManagedMateriales.materiales.codTipoApoyoSeleccionado}" styleClass="inputText" rendered="#{ManagedMateriales.materiales.grupos.codGrupo=='12'}">
                                    <f:selectItems value="#{ManagedMateriales.lineasApoyoList}" />
                                </h:selectOneMenu>
                                <h:outputText value="Tipo Material Apoyo:" styleClass="outputText1" rendered="#{ManagedMateriales.materiales.grupos.codGrupo=='12'}"  />
                                <h:selectOneMenu value="#{ManagedMateriales.materiales.codLineaApoyoSeleccionado}" styleClass="inputText" rendered="#{ManagedMateriales.materiales.grupos.codGrupo=='12'}">
                                    <f:selectItems value="#{ManagedMateriales.tiposApoyoList}" />
                                </h:selectOneMenu>
                            </h:panelGrid>


                            <br/>

                            <a4j:commandButton styleClass="btn" value="Guardar"
                                               action="#{ManagedMateriales.guardarEdicionMaterial_action}"
                                               reRender="dataMateriales" oncomplete="alert('#{ManagedMateriales.mensaje}');if(#{ManagedMateriales.registradoExitosamente}){Richfaces.hideModalPanel('panelEditarMaterial');}"
                                               onclick="if(confirm('Esta seguro de guardar la informacion?')==false){return false;}" />
                            <input type="button" value="Cancelar" onclick="javascript:Richfaces.hideModalPanel('panelEditarMaterial')" class="btn"  />
                        </div>
                    </h:panelGroup>
                </a4j:form>
            </rich:modalPanel>
            <%--inicio ale buscador--%>
            <rich:modalPanel id="panelBuscarMaterial" minHeight="150"  minWidth="800"
                             height="150" width="800"  zindex="201"  headerClass="headerClassACliente"
                             resizeable="false" style="overflow :auto" >
                <f:facet name="header">
                    <h:outputText value="Buscar Material"/>
                </f:facet>
                <a4j:form id="form6">
                    <h:panelGroup id="contenidoBuscarMaterial">
                        <div align="center">
                            <h:panelGrid columns="4" style="text-align:left;width:100%" >
                                <h:outputText value="Capitulos :" styleClass="outputText1" />
                                <h:selectOneMenu value="#{ManagedMateriales.materiales.grupos.capitulos.codCapitulo}" styleClass="inputText" >
                                    <f:selectItem itemValue="0" itemLabel="-NINGUNO-"/>
                                    <f:selectItems value="#{ManagedMateriales.capitulosList}" />
                                    <a4j:support event="onchange" action="#{ManagedMateriales.capitulosFiltro_change}" reRender="codCapituloFiltro" />
                                </h:selectOneMenu>
                                <h:outputText value="Grupos :" styleClass="outputText1" />
                                <h:selectOneMenu value="#{ManagedMateriales.materiales.grupos.codGrupo}" styleClass="inputText"  id = "codCapituloFiltro">
                                    <f:selectItem itemValue="0" itemLabel="-NINGUNO-"/>
                                    <f:selectItems value="#{ManagedMateriales.gruposList}" />
                                    <%--a4j:support event="onchange" action="#{ManagedMateriales.gruposFiltro_change}" reRender="dataMateriales" /--%>
                                </h:selectOneMenu>
                                <h:outputText value="Material :" styleClass="outputText1" />
                                <h:inputText value="#{ManagedMateriales.materiales.nombreMaterial}" styleClass="inputText"/>
                                <h:outputText value="Codigo Material :" styleClass="outputText1" />
                                <h:inputText value="#{ManagedMateriales.materiales.codMaterial}" styleClass="inputText"/>
                                <h:outputText value="Estado:" styleClass="outputText1" />
                                <h:selectOneMenu value="#{ManagedMateriales.materiales.estadoRegistro.codEstadoRegistro}" styleClass="inputText"  id = "codEstadoRegistro">
                                    <f:selectItem itemValue="0" itemLabel="-NINGUNO-"/>
                                    <f:selectItem itemValue="1" itemLabel="Activo"/>
                                    <f:selectItem itemValue="2" itemLabel="No Activo"/>

                                    <%--a4j:support event="onchange" action="#{ManagedMateriales.gruposFiltro_change}" reRender="dataMateriales" /--%>
                                </h:selectOneMenu>
                            </h:panelGrid>
                            <a4j:commandButton value="Buscar" action="#{ManagedMateriales.buscarMaterial_Action}" styleClass="btn" oncomplete="Richfaces.hideModalPanel('panelBuscarMaterial')" reRender="dataMateriales"/>
                            <a4j:commandButton value="Cancelar" onclick="Richfaces.hideModalPanel('panelBuscarMaterial')" styleClass="btn"/>

                        </div>
                    </h:panelGroup>
                </a4j:form>
            </rich:modalPanel>
            <%--final ale buscador--%>
            
            <%--eliminar Material --%>
            <rich:modalPanel id="panelEliminarMaterial" minHeight="120"  minWidth="800"
                             height="200" width="800"  zindex="201"  headerClass="headerClassACliente"
                             resizeable="false" style="overflow :auto" >
                <f:facet name="header">
                    <h:outputText value="Eliminar Material"/>
                </f:facet>
                <a4j:form id="form7">
                    <h:panelGroup id="contenidoEliminarMaterial">
                        <div align="center">
                            <h:panelGrid columns="4" style="text-align:left;width:100%" >
                                <h:outputText value="Capitulos :" styleClass="outputText1" />
                                <h:outputText value="#{ManagedMateriales.materiales.grupos.capitulos.nombreCapitulo}" styleClass="outputText1" />
                                <h:outputText value="Grupos :" styleClass="outputText1" />
                                <h:outputText value="#{ManagedMateriales.materiales.grupos.nombreGrupo}" styleClass="outputText1" />
                                
                                <h:outputText value="Material :" styleClass="outputText1" />
                                <h:outputText value="#{ManagedMateriales.materiales.nombreMaterial}" styleClass="outputText1" />
                                <h:outputText value="Codigo Material :" styleClass="outputText1" />
                                <h:outputText value="#{ManagedMateriales.materiales.codMaterial}" styleClass="outputText1" />    
                                
                                <h:outputText value="Nombre Comercial :" styleClass="outputText1" />
                                <h:outputText value="#{ManagedMateriales.materiales.nombreComercialMaterial}" styleClass="outputText1" />
                                <h:outputText value="Unidad Medida :" styleClass="outputText1" />
                                <h:outputText value="#{ManagedMateriales.materiales.unidadesMedida.nombreUnidadMedida}" styleClass="outputText1" />                                
                            </h:panelGrid>
                            <h:panelGrid columns="1" >
                                <h:outputText value="Motivo/Observación:" styleClass="outputText1" />
                                <h:inputTextarea value="#{ManagedMateriales.descripcionTransaccion}" styleClass="inputText" cols="100" rows="2" />
                            </h:panelGrid>
                            
                            <a4j:commandButton value="Guardar" 
                                               onclick="if(confirm('esta seguro de eliminar?')==false){return false;}" 
                                               action="#{ManagedMateriales.guardarEliminarMaterial_action}" styleClass="btn" 
                                               oncomplete="alert('#{ManagedMateriales.mensaje}');if(#{ManagedMateriales.registradoExitosamente}){Richfaces.hideModalPanel('panelEliminarMaterial')}" 
                                               reRender="dataMateriales"/>
                            <a4j:commandButton value="Cancelar" onclick="Richfaces.hideModalPanel('panelEliminarMaterial')" styleClass="btn"/>

                        </div>
                    </h:panelGroup>
                </a4j:form>
            </rich:modalPanel>
            
        </body>
    </html>
</f:view>
