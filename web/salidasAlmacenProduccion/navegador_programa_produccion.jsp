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
            <style  type="text/css">
                .a{
                    background-color : #F2F5A9;
                }
                .b{
                    background-color : #ffffff;
                }
                .columns{
                    border:0px solid red;
                }
                .simpleTogglePanel{
                    text-align:center;
                }
                .ventasdetalle{
                    font-size: 13px;
                    font-family: Verdana;
                }
                .preciosaprobados{
                    background-color:#33CCFF;
                }
                .enviado{
                    background-color:#FFFFCC;
                }
                .pasados{
                    background-color:#ADD797;
                }
                .pendiente{
                    background-color : #ADD797;
                }
                .leyendaColorAnulado{
                    background-color: #FF6666;
                }                
            </style>
            <script  type="text/javascript">
                function cogerId(obj) {
                    alert(obj);


                }
                function getCodigoProcesoProduccion(codComProd, codigo, codFormula, codLote, codTipoActividadProduccion) {
                    //  alert(codigo);
                    location = '../actividades_programa_produccion/navegador_actividades_programa.jsf?codigo=' + codigo + '&cod_formula=' + codFormula + '&cod_comp_prod=' + codComProd + '&cod_lote=' + codLote + '&codTipoActividadProduccion=' + codTipoActividadProduccion;
                }
                function getCodigoProcesoMicrobiologia(codComProd, codigo, codFormula, codLote, codTipoActividadProduccion) {
                    //  alert(codigo);
                    location = '../actividades_programa_produccion/navegador_actividades_programa.jsf?codigo=' + codigo + '&cod_formula=' + codFormula + '&cod_comp_prod=' + codComProd + '&cod_lote=' + codLote + '&codTipoActividadProduccion=' + codTipoActividadProduccion;
                }
                function getCodigoProcesoControlDeCalidad(codComProd, codigo, codFormula, codLote, codTipoActividadProduccion) {
                    //  alert(codigo);
                    location = '../actividades_programa_produccion/navegador_actividades_programa.jsf?codigo=' + codigo + '&cod_formula=' + codFormula + '&cod_comp_prod=' + codComProd + '&cod_lote=' + codLote + '&codTipoActividadProduccion=' + codTipoActividadProduccion;
                }
                function getCodigo(codigo, codFormula, nombre, cantidad, codComProd, codLote) {

                    izquierda = (screen.width) ? (screen.width - 300) / 2 : 100
                    arriba = (screen.height) ? (screen.height - 400) / 2 : 200
                    //url='../configuracionReporte/navegadorConfiguracionReporteReplica.jsf?codigo='+cod+'';
                    url = 'detalle_navegador_programa_prod.jsf?codigo=' + codigo + '&codFormula=' + codFormula + '&nombre=' + nombre + '&cantidad=' + cantidad + '&cod_comp_prod=' + codComProd + '&cod_lote=' + codLote;
                    //alert(url);
                    opciones = 'toolbar=0,location=0,directories=0,status=0,menubar=0,scrollbars=1,resizable=1,width=700,height=400,left=' + izquierda + ',top=' + arriba + ''
                    window.open(url, 'popUp', opciones)

                }
                function getCodigo1(codComProd, codigo, codFormula, codLote) {

                    izquierda = (screen.width) ? (screen.width - 300) / 2 : 100
                    arriba = (screen.height) ? (screen.height - 400) / 2 : 200
                    url = '../barcode?number=1&location=' + codComProd + "-" + codigo + "-" + codFormula + "-" + codLote;
                    //url='../codigo_barras.jsf?codigo='+codigo+'&codFormula='+codFormula+'&nombre='+nombre+'&cantidad='+cantidad+'&cod_comp_prod='+codComProd+'&cod_lote='+codLote;
                    //alert(url);
                    opciones = 'toolbar=0,location=0,directories=0,status=0,menubar=0,scrollbars=1,resizable=1,width=700,height=400,left=' + izquierda + ',top=' + arriba + ''
                    window.open(url, 'popUp', opciones)

                }
                function getCodigo2(codProgProd, codFormula, nombre, cantidad, codComProd, codLote) {

                    izquierda = (screen.width) ? (screen.width - 300) / 2 : 100
                    arriba = (screen.height) ? (screen.height - 400) / 2 : 200
                    //url='../configuracionReporte/navegadorConfiguracionReporteReplica.jsf?codigo='+cod+'';
                    url = 'imprimir_programa_prod_excel.jsf?codProgProd=' + codProgProd + '&codFormula=' + codFormula + '&nombre=' + nombre + '&cantidad=' + cantidad + '&cod_comp_prod=' + codComProd + '&cod_lote=' + codLote;
                    alert(url);
                    opciones = 'toolbar=0,location=0,directories=0,status=0,menubar=0,scrollbars=1,resizable=1,width=700,height=400,left=' + izquierda + ',top=' + arriba + ''
                    window.open(url, 'popUp', opciones)

                }
                function getCodigoReserva(codigo) {


                    location = '../reporteExplosionProductos/guardarReservaProgramaProd.jsf?codigo=' + codigo;


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


                        if (confirm('Desea Eliminar el Registro')) {
                            if (confirm('Esta seguro de Eliminar el Registro')) {
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
                            } else {
                                return false;
                            }
                        } else {
                            return false;
                        }
                    }
                }

                function reserva(nametable) {
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


                        if (confirm('Desea Realizar la Reserva del Producto')) {
                            if (confirm('Esta seguro de Realizar la Reserva ')) {
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
                            } else {
                                return false;
                            }
                        } else {
                            return false;
                        }
                    }
                }

                function eliminarReserva(nametable) {
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


                        if (confirm('Desea Eliminar la Reserva del Producto')) {
                            if (confirm('Esta seguro de Eliminar la Reserva')) {
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
                            } else {
                                return false;
                            }
                        } else {
                            return false;
                        }
                    }
                }

                function nuevoAjax()
                {
                    var xmlhttp = false;
                    try {
                        xmlhttp = new ActiveXObject("Msxml2.XMLHTTP");
                    } catch (e) {
                        try {
                            xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
                        } catch (E) {
                            xmlhttp = false;
                        }
                    }
                    if (!xmlhttp && typeof XMLHttpRequest != "undefined") {
                        xmlhttp = new XMLHttpRequest();
                    }
                    return xmlhttp;
                }
                function enviar(codigo, fecha_ini, fecha_fin) {
                    //alert();

                    var ajax = nuevoAjax();
                    var valores = 'codigos=' + codigo + '&fecha_inicio=' + fecha_ini + '&fecha_final=' + fecha_fin;
                    valores += '&pq=' + (Math.random() * 1000);

                    var url = '../reporteExplosionProductos/filtroReporteExplosion.jsf';



                    //alert(url);
                    ajax.open('POST', url, true);
                    ajax.onreadystatechange = function () {

                        if (ajax.readyState == 1) {

                        } else if (ajax.readyState == 4) {
                            // alert(ajax.status);
                            if (ajax.status == 200) {
                                //alert(ajax.responseText);
                                var mainGrupo = document.getElementById('panelCenter');
                                //mainGrupo.innerHTML=ajax.responseText;
                                document.write(ajax.responseText);

                                //f=0;
                                //Item(codigo,f);
                            }
                        }
                    }
                    ajax.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
                    ajax.send(valores);
                }


                function seleccionarTodo() {
                    //alert('entro');
                    var seleccionar_todo = document.getElementById('form1:seleccionar_todo');
                    var elements = document.getElementById('form1:dataProgramaProduccion');

                    if (seleccionar_todo.checked == true) {
                        //alert('entro por verdad');
                        var rowsElement = elements.rows;
                        for (var i = 1; i < rowsElement.length; i++) {
                            var cellsElement = rowsElement[i].cells;
                            var cel = cellsElement[0];
                            if (cel.getElementsByTagName('input')[0].type == 'checkbox') {
                                cel.getElementsByTagName('input')[0].checked = true;
                            }
                        }
                    }
                    else
                    {//alert('entro por false');
                        var rowsElement = elements.rows;
                        for (var i = 1; i < rowsElement.length; i++) {
                            var cellsElement = rowsElement[i].cells;
                            var cel = cellsElement[0];
                            if (cel.getElementsByTagName('input')[0].type == 'checkbox') {
                                cel.getElementsByTagName('input')[0].checked = false;
                            }
                        }

                    }
                    return true;

                }

                function validarSeleccion(nametable) {
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
                        alert('Seleccione al menos un registro.');
                        return false;
                    }
                    return true;
                }
                function seleccionarItem(nametable) {
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
                function seleccionarTipoSalida_action() {
                    window.location = 'navegador_programa_produccion.jsf?seleccionoTipoSalidaAlmacenProduccion=1';

                }
                function openPopup(url){
                       window.open(url,'detalle'+Math.round((Math.random()*1000)),'top=50,left=200,width=800,height=600,scrollbars=1,resizable=1');
                }

            </script>
        </head>
        
        <body onload="Richfaces.showModalPanel('panelTipoSalidaAlmacenProduccion');"  >
            <a4j:form id="form1"  oncomplete="Richfaces.showModalPanel('panelTipoSalidaAlmacenProduccion');" >
                
                <rich:panel id="panelEstadoCuentas" styleClass="panelBuscar" style="top:50px;left:50px;width:700px;">
                    <f:facet name="header">
                        <h:outputText value="<div   onmouseover=\"this.style.cursor='move'\" onmousedown=\"comienzoMovimiento(event, 'form1:panelEstadoCuentas');\"  >Buscar<div   style=\"margin-left:550px;\"   onclick=\"document.getElementById('form1:panelEstadoCuentas').style.visibility='hidden';document.getElementById('panelsuper').style.visibility='hidden';\" onmouseover=\"this.style.cursor='hand'\"   >Cerrar</div> </div> "
                                      escape="false" />
                    </f:facet>
                </rich:panel>
                <div align="center">

                    <h:outputText value="#{ManagedProgramaProduccion.cargarListadoProgramaProduccion}"  />
                    <h:outputText styleClass="outputTextTitulo"  value="Programas de Producción" />
                    <br> <br>
                    <h:selectBooleanCheckbox id="seleccionar_todo"  onchange="seleccionarTodo();"/>
                    <h:outputLabel value="Seleccionar Todos" styleClass="outputTextTitulo" for="seleccionar_todo"/>
                    <br> <br>

                    <div style="overflow:auto;text-align:center;height:350px;width:90%;border: 1px solid #ccc" id="main">
                        <h:panelGroup id="contenidoProgramaProduccion">
                            <center>
                                <rich:dataTable value="#{ManagedProgramaProduccion.programaProduccionList}" 
                                                var="data" style="width:100%"
                                                id="dataProgramaProduccion"
                                                onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                                onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                                headerClass="headerClassACliente" 
                                        >
                                    <rich:column styleClass="#{data.styleClass}">
                                        <f:facet name="header">
                                            <h:outputText value=""  />
                                        </f:facet>
                                        <h:selectBooleanCheckbox value="#{data.checked}" id="checkLote"  />

                                    </rich:column >

                                    <rich:column styleClass="#{data.styleClass}" >
                                        <f:facet name="header">
                                            <h:outputText value="Producto"  />
                                        </f:facet>
                                        <h:outputLabel value="#{data.formulaMaestra.componentesProd.nombreProdSemiterminado}" for="checkLote"/>
                                    </rich:column>
                                    <rich:column styleClass="#{data.styleClass}" >
                                        <f:facet name="header">
                                            <h:outputText value="Versión"  />
                                        </f:facet>
                                        <h:outputText value="#{data.nro_version}"  />
                                    </rich:column>
                                    <rich:column styleClass="#{data.styleClass}" >
                                        <f:facet name="header">
                                            <h:outputText value="Lote"  />
                                        </f:facet>
                                        <h:outputText value="#{data.cantidadLote}"  />
                                    </rich:column>
                                    <%--rich:column styleClass="#{data.styleClass}" >
                                        <f:facet name="header">
                                            <h:outputText value="Nro. de Lotes a Producir"  />
                                        </f:facet>
                                        <h:outputText value="#{data.cantidadLote}"  />
                                    </rich:column --%>
                                    <rich:column styleClass="#{data.styleClass}" >
                                        <f:facet name="header">
                                            <h:outputText value="Nro de Lote"  />
                                        </f:facet>
                                        <h:outputText value="#{data.codLoteProduccion}"  />
                                    </rich:column >
                                    <rich:column styleClass="#{data.styleClass}" rendered="#{ManagedProgramaProduccion.tipoSalidaAlmacenProgramaProduccion=='1'}" >
                                        <f:facet name="header">
                                            <h:outputText value="Fecha Inicio"  />
                                        </f:facet>
                                        <h:outputText value="#{data.fechaInicio}"  />
                                    </rich:column >
                                    <rich:column styleClass="#{data.styleClass}" rendered="#{ManagedProgramaProduccion.tipoSalidaAlmacenProgramaProduccion=='1'}" >
                                        <f:facet name="header">
                                            <h:outputText value="Fecha Final"  />
                                        </f:facet>
                                        <h:outputText value="#{data.fechaFinal}"  />
                                    </rich:column >
                                    <rich:column  styleClass="#{data.styleClass}" rendered="#{ManagedProgramaProduccion.tipoSalidaAlmacenProgramaProduccion=='1'}">
                                        <f:facet name="header">
                                            <h:outputText value="Tipo Programa Producción"  />
                                        </f:facet>
                                        <h:outputText value="#{data.tiposProgramaProduccion.nombreProgramaProd}" />
                                    </rich:column >
                                    <rich:column styleClass="#{data.styleClass}" rendered="#{ManagedProgramaProduccion.tipoSalidaAlmacenProgramaProduccion=='1'}"  >
                                        <f:facet name="header">
                                            <h:outputText value="Categoría"  />
                                        </f:facet>
                                        <h:outputText value="#{data.categoriasCompProd.nombreCategoriaCompProd}"  />
                                    </rich:column >


                                    <rich:column styleClass="#{data.styleClass}" >
                                        <f:facet name="header" >
                                            <h:outputText value="Estado Materia Prima"  />
                                        </f:facet>

                                        <h:outputText value="#{data.materialTransito}" id="n" />
                                    </rich:column>

                                    <rich:column styleClass="#{data.styleClass}" >
                                        <f:facet name="header" >
                                            <h:outputText value="Area"  />
                                        </f:facet>
                                        <h:outputText value="#{data.formulaMaestra.componentesProd.areasEmpresa.nombreAreaEmpresa}"/>
                                    </rich:column>

                                    <rich:column  styleClass="#{data.styleClass}" >
                                        <f:facet name="header">
                                            <h:outputText value="Observaciones"  />
                                        </f:facet>
                                        <h:outputText value="#{data.observacion}" />
                                    </rich:column >
                                    <rich:column  styleClass="#{data.styleClass}" >
                                        <f:facet name="header">
                                            <h:outputText value="Estado"  />
                                        </f:facet>
                                        <h:outputText value="#{data.estadoProgramaProduccion.nombreEstadoProgramaProd}" />
                                    </rich:column>    


                                </rich:dataTable>
                                
                            </center> 

                        </h:panelGroup>
                    </div>
                    <br/>
                    <a4j:commandButton  value=" Generar Salidas de Almacen MP"
                                          styleClass="btn"
                                          action="#{ManagedProgramaProduccion.generarSalidaAlmacenes_action_MP}"
                                          rendered="#{ManagedProgramaProduccion.permisoSalidaMateriaPrima}"
                                          onclick="if(seleccionarItem('form1:dataProgramaProduccion')==false){return false}"
                                          oncomplete="if(#{ManagedProgramaProduccion.mensajeMP!=''}){alert('#{ManagedProgramaProduccion.mensajeMP}')}"
                                          />
                    <h:commandButton  value=" Generar Salidas de Almacen EP-ES"
                                          styleClass="btn"
                                          rendered="#{ManagedProgramaProduccion.permisoSalidaEmpaquePrimario
                                                        or ManagedProgramaProduccion.permisoSalidaEmpaqueSecundario}"
                                          action="#{ManagedProgramaProduccion.generarSalidaAlmacenes_action_EP_ES}"
                                          />
                    <h:panelGroup rendered="#{ManagedProgramaProduccion.administradorAlmacen}">
                        <%-- onclick="if(seleccionarItem('form1:dataProgramaProduccion')==false){return false;}" --%>
                        <a4j:commandButton value="Empaque Secundario Necesario"    styleClass="btn"  action="#{ManagedProgramaProduccion.explosionEmpaqueSecundarioAlmacen_action}" 
                                           oncomplete="if(#{ManagedProgramaProduccion.mensaje eq '1'}){openPopup('impresionExplosionEs.jsf?codExplosionEmpaqueSecundarioAlmacen=#{ManagedProgramaProduccion.codExplosionEmpaqueSecundarioAlmacen}&data='+(new Date()).getTime().toString());}
                                           else {alert('Ocurrio un error al momento de realizar los calculos necesarios, favor comunicarse con sistemas')}" 
                                           />
                    </h:panelGroup>
                    <a4j:commandButton value="Generar Solicitud de Materiales de ES" styleClass="btn"  
                                       onclick="if(!validarSeleccion('form1:dataProgramaProduccion')){return false;}"
                                       action="#{ManagedProgramaProduccion.generarSolicitudSalidaAlmacen_action}" rendered="#{ManagedAccesoSistema.almacenesGlobal.codAlmacen eq '2'}"
                                       oncomplete="location='agregarSolicitudSalidaAlmacenEs.jsf'" 
                                    />
                    <a4j:commandButton value="Volver" styleClass="btn"
                                       oncomplete="redireccionar('navgador_programa_periodo.jsf')"/>
                </div>
                

                <a4j:status id="statusPeticion"
                            onstart="Richfaces.showModalPanel('ajaxLoadingModalBox',{width:100, top:100})"
                            onstop="Richfaces.hideModalPanel('ajaxLoadingModalBox')">
                </a4j:status>

                <rich:modalPanel id="ajaxLoadingModalBox" minHeight="100"
                                 minWidth="200" height="80" width="400" zindex="100" onshow="window.focus();">

                    <div align="center">
                        <h:graphicImage value="../img/load2.gif" />
                    </div>
                </rich:modalPanel>


            </a4j:form>


        </body>
    </html>

</f:view>

