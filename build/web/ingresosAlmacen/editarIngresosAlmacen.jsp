
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
            <script>
                function getCodigo(cod_maquina,codigo){
                 //  alert(codigo);
                   location='../partes_maquinaria/navegador_partes_maquinaria.jsf?cod_maquina='+cod_maquina+'&codigo='+codigo;
                }

                function editarItem(nametable){
                   var count=0;
                   var elements=document.getElementById(nametable);
                   var rowsElement=elements.rows;
                   for(var i=1;i<rowsElement.length;i++){
                    var cellsElement=rowsElement[i].cells;
                    var cel=cellsElement[0];
                    if(cel.getElementsByTagName('input')[0].type=='checkbox'){
                          if(cel.getElementsByTagName('input')[0].checked){
                           count++;
                         }

                     }

                   }
                   if(count==1){
                      return true;
                   } else if(count==0){
                       alert('No escogio ningun registro');
                       return false;
                   }
                   else if(count>1){
                       alert('Solo puede escoger un registro');
                       return false;
                   }
                }


                function asignar(nametable){

                   var count=0;
                   var elements=document.getElementById(nametable);
                   var rowsElement=elements.rows;
                   for(var i=1;i<rowsElement.length;i++){
                    var cellsElement=rowsElement[i].cells;
                    var cel=cellsElement[0];
                    alert('hola');
                    if(cel.getElementsByTagName('input')[0].type=='checkbox'){
                          if(cel.getElementsByTagName('input')[0].checked){
                           count++;
                         }
                     }
                   }
                    if(count==0){
                       alert('No selecciono ningun Registro');
                       return false;
                   }else{
                       if(confirm('Desea Asignar como Area Raiz')){
                            if(confirm('Esta seguro de Asignar como Area Raiz')){
                                 return true;
                        }else{
                            return false;
                        }
                    }else{
                        return false;
                    }

                   }

                }
            function eliminar(nametable){
               var count1=0;
               var elements1=document.getElementById(nametable);
               var rowsElement1=elements1.rows;
               //alert(rowsElement1.length);
               for(var i=1;i<rowsElement1.length;i++){
                    var cellsElement1=rowsElement1[i].cells;
                    var cel1=cellsElement1[0];
                    if(cel1.getElementsByTagName('input').length>0){
                        if(cel1.getElementsByTagName('input')[0].type=='checkbox'){
                          if(cel1.getElementsByTagName('input')[0].checked){
                               count1++;
                           }
                        }
                    }

               }
               //alert(count1);
               if(count1==0){
                    alert('No escogio ningun registro');
                    return false;
               }else{

                            var count=0;
                            var elements=document.getElementById(nametable);
                            var rowsElement=elements.rows;

                            for(var i=0;i<rowsElement.length;i++){
                                var cellsElement=rowsElement[i].cells;
                                var cel=cellsElement[0];
                                if(cel.getElementsByTagName('input').length>0){
                                    if(cel.getElementsByTagName('input')[0].type=='checkbox'){
                                        if(cel.getElementsByTagName('input')[0].checked){
                                            count++;
                                        }
                                    }
                                }

                            }
                            if(count==0){
                            //alert('No escogio ningun registro');
                            return false;
                            }
                            //var cantidadeliminar=document.getElementById('form1:cantidadeliminar');
                            //cantidadeliminar.value=count;
                            return true;

                }
           }

           function validarSeleccion(nametable){
                   var count=0;
                   var elements=document.getElementById(nametable);
                   var rowsElement=elements.rows;
                   for(var i=1;i<rowsElement.length;i++){
                    var cellsElement=rowsElement[i].cells;
                    var cel=cellsElement[0];
                    if(cel.getElementsByTagName('input')[0].type=='checkbox'){
                          if(cel.getElementsByTagName('input')[0].checked){
                           count++;
                         }

                     }

                   }
                   if(count==1){
                      return true;
                   } else if(count==0){
                       alert('No escogio ningun registro');
                       return false;
                   }
                   else if(count>1){
                       alert('Solo puede escoger un registro');
                       return false;
                   }
                }
        function verManteminiento(cod_maquina,codigo){
                   location='../partes_maquinaria/navegador_partes_maquinaria.jsf?cod_maquina='+cod_maquina+'&codigo='+codigo;
        }
        function openPopup(url){
                    window.open(url,'DETALLE','top=50,left=200,width=800,height=600,scrollbars=1,resizable=1');
                }
                
        function hallaValorEquivalente(elemento){            
            var valorEquivalencia=document.getElementById("form2:valorEquivalencia").value;            
            if(valorEquivalencia>0){
            var resultado = elemento.value/valorEquivalencia;
            document.getElementById("form2:cantEquivalente").value = resultado;
            }
        }
        function hallaCantidadIngresoFisico(){
                    var elements=document.getElementById('form3:dataIngresoAlmacenDetalleEstado');
                    var rowsElement=elements.rows;
                    var cantidadIngresoFisico = 0;
                    var totalImporteF=0;
                    for(var i=1;i<rowsElement.length;i++){
                        var cellsElement=rowsElement[i].cells;
                        var cel3=cellsElement[4];
                        var cantidadParcial=cel3.getElementsByTagName('input')[0];
                        if (isNaN(cantidadParcial.value))
                        {  
                           alert('Introduzca sólo Números');
                           cantidadParcial.value = 0;
                        }
                        if(cantidadParcial.type=='text' && parseFloat(cantidadParcial.value)>0){
                            cantidadIngresoFisico=parseFloat(cantidadIngresoFisico)+parseFloat(cantidadParcial.value);
                        }
                    }
                    //alert(document.getElementById('form2:cantTotalIngresoFisico'));
                    //alert(cantidadIngresoFisico);
                    document.getElementById('form3:cantTotalIngresoFisico').value=Math.round(cantidadIngresoFisico*100)/100;
       }
       function valida_fechas(){
         var startDate = Date(document.getElementById("form3:fechaManufacturaInputDate").value);
         //var pubDate = ${''}.getSelectedDate();
         }
         function seleccionarTodo(nametable){
             var check = document.getElementById("form3:seleccionar_todo");
                   var count=0;
                   var elements=document.getElementById(nametable);
                   var rowsElement=elements.rows;
                   for(var i=1;i<rowsElement.length;i++){
                    var cellsElement=rowsElement[i].cells;
                    var cel=cellsElement[0];
                    if(cel.getElementsByTagName('input')[0].type=='checkbox'){
                          cel.getElementsByTagName('input')[0].checked=check.checked;
                     }
                   }
                }
          function seleccionarTodo_1(nombreTabla){
                    //alert('entro');
                    var seleccionar_todo=document.getElementById('form3:seleccionar_todo');
                    var elements=document.getElementById(nombreTabla);

                    
                        var rowsElement=elements.rows;
                        for(var i=1;i<rowsElement.length;i++){
                            var cellsElement=rowsElement[i].cells;
                            var cel=cellsElement[0];
                            if(cel.getElementsByTagName('input')[0].type=='checkbox'){
                                cel.getElementsByTagName('input')[0].checked=seleccionar_todo.checked;
                            }
                        }
                    
                }
</script>
</head>
<body >
            <a4j:form id="form1">
            <center>
                    <h:outputText value="#{ManagedIngresoAlmacen.cargarEditarIngresosAlmacen}"/>
                    <br/><br/>
                    <rich:panel id="panelEstadoCuentas" styleClass="panelBuscar" style="top:50px;left:50px;width:700px;">
                        <f:facet name="header">
                            <h:outputText value="<div   onmouseover=\"this.style.cursor='move'\" onmousedown=\"comienzoMovimiento(event, 'form1:panelEstadoCuentas');\"  >Buscar<div   style=\"margin-left:550px;\"   onclick=\"document.getElementById('form1:panelEstadoCuentas').style.visibility='hidden';document.getElementById('panelsuper').style.visibility='hidden';\" onmouseover=\"this.style.cursor='hand'\"   >Cerrar</div> </div> "
                                          escape="false" />
                        </f:facet>
                    </rich:panel>

                    <rich:panel style="width:80%" headerClass="headerClassACliente">
                        <f:facet name="header">
                            <h:outputText value="Edición de Ingresos a Almacen"  />
                        </f:facet>
                        
                        <rich:panel>
                            <h:panelGrid columns="2" >
                                <h:outputText value="Ingrese el motivo de la edición de este Ingreso:" styleClass="outputText2"/>
                                <h:inputTextarea value="#{ManagedIngresoAlmacen.ingresoTransaccionSolicitudGestionar.observacionValidador}" styleClass="inputText" cols="70" rows="2" />
                            </h:panelGrid>
                            
                            <h:panelGroup rendered="#{ManagedIngresoAlmacen.salidasAsociadasAlmacenListSize ne 0}" >
                                <h:outputText value="No se pueden Editar los items del Ingreso si tiene Salidas con Devoluciones: " styleClass="outputTextWarn" rendered="#{ManagedIngresoAlmacen.tipoSalidaAutomatico && ManagedIngresoAlmacen.salidaTieneDevolucion}"/>
                                <h:outputText value="No se pueden Editar los items del Ingreso si tiene Salidas asociadas, debe Anular primeramente las Salidas: " styleClass="outputTextWarn" rendered="#{!ManagedIngresoAlmacen.tipoSalidaAutomatico}"/>
                                <h:outputText value="*IMPORTANTE: Salidas automáticas del Ingreso que serán anuladas: " styleClass="outputText1" rendered="#{ManagedIngresoAlmacen.ingresoEditableValidandoConSalidas}"/>
                                <rich:dataTable value="#{ManagedIngresoAlmacen.salidasAsociadasAlmacenList}"
                                            var="dataSalida"
                                            id="dataSalidasAsociadasAlmacenE"
                                            onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                            onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                            headerClass="headerClassACliente" >

                                    <rich:column styleClass ="#{dataSalida.colorFila}">
                                        <f:facet name="header">
                                            <h:outputText value="Nro Salida Almacen"  />
                                        </f:facet>
                                        <h:outputText  value="#{dataSalida.nroSalidaAlmacen}"  />
                                    </rich:column>

                                    <rich:column styleClass ="#{dataSalida.colorFila}">
                                        <f:facet name="header">
                                            <h:outputText value="Fecha Salida Almacen"  />
                                        </f:facet>
                                        <h:outputText value="#{dataSalida.fechaSalidaAlmacen}"  >
                                            <f:convertDateTime pattern="dd/MM/yyyy HH:mm" />
                                        </h:outputText>
                                    </rich:column>

                                    <rich:column styleClass ="#{dataSalida.colorFila}">
                                        <f:facet name="header">
                                            <h:outputText value="Area Empresa"  />
                                        </f:facet>
                                        <h:outputText value="#{dataSalida.areasEmpresa.nombreAreaEmpresa}"  />
                                    </rich:column>

                                    <rich:column styleClass ="#{dataSalida.colorFila}">
                                        <f:facet name="header">
                                            <h:outputText value="Tipo Salida"  />
                                        </f:facet>
                                        <h:outputText value="#{dataSalida.tiposSalidasAlmacen.nombreTipoSalidaAlmacen}"  />
                                    </rich:column>

                                    <rich:column styleClass ="#{dataSalida.colorFila}">
                                        <f:facet name="header">
                                            <h:outputText value="Personal"  />
                                        </f:facet>
                                        <h:outputText value="#{dataSalida.personal.nombrePersonal}"  />
                                    </rich:column>

                                    <rich:column styleClass ="#{dataSalida.colorFila}">
                                        <f:facet name="header">
                                            <h:outputText value="Detalle"  />
                                        </f:facet>
                                        <a4j:commandLink action="#{ManagedIngresoAlmacen.verReporteSalidaAlmacen_action}" 
                                                         oncomplete="openPopup('../salidasAlmacen/reporteSalidaAlmacenJasper.jsf?reporte=../salidasAlmacen/reporteSalida.jasper');"
                                                         >
                                            <h:graphicImage url="../img/report.png" />
                                            <f:setPropertyActionListener value="#{dataSalida}" target="#{ManagedIngresoAlmacen.salidaAsociadaGestionar}"/>
                                        </a4j:commandLink>
                                    </rich:column>

                                </rich:dataTable>
                            </h:panelGroup>
                            
                        </rich:panel>

                        <h:panelGrid columns="4">

                            <h:outputText value="Gestion:" styleClass="outputText2" />
                            <h:outputText value="#{ManagedIngresoAlmacen.ingresosAlmacen.gestiones.nombreGestion}" styleClass="outputText1" />
                            <h:outputText value="Fecha de Ingreso:" styleClass="outputText2" />

                            <h:outputText value="#{ManagedIngresoAlmacen.ingresosAlmacen.fechaIngresoAlmacen}" styleClass="outputText2" >
                                <f:convertDateTime pattern="dd/MM/yyyy"   />
                            </h:outputText>


                            <h:outputText value="Nro Ingreso Almacen:" styleClass="outputText2" />
                            <h:outputText value="#{ManagedIngresoAlmacen.ingresosAlmacen.nroIngresoAlmacen}" styleClass="outputText1" />

                            <h:outputText value="Estado De Ingreso Almacen:" styleClass="outputText2" />
                            <h:outputText value="#{ManagedIngresoAlmacen.ingresosAlmacen.estadosIngresoAlmacen.nombreEstadoIngresoAlmacen}" styleClass="outputText2"/>
                            
                            <h:outputText value="Tipo de Ingreso:" styleClass="outputText2" />
                            <h:selectOneMenu  styleClass="inputText" value="#{ManagedIngresoAlmacen.ingresosAlmacen.tiposIngresoAlmacen.codTipoIngresoAlmacen}" disabled="#{ManagedIngresoAlmacen.ingresosAlmacen.tiposIngresoAlmacen.automatico}" >
                                <f:selectItems value="#{ManagedIngresoAlmacen.tiposIngresosAlmacenList}"  />
                            </h:selectOneMenu>

                            <h:outputText value="Tipo de Compra:" styleClass="outputText2" />
                            <h:selectOneMenu  styleClass="inputText" value="#{ManagedIngresoAlmacen.ingresosAlmacen.tiposCompra.codTipoCompra}" disabled="true">
                                <f:selectItems value="#{ManagedIngresoAlmacen.tiposCompraList}"  />
                            </h:selectOneMenu>

                            <h:outputText value="Origen Proveedor: " styleClass="outputText2" />
                            <h:outputText value="#{ManagedIngresoAlmacen.ingresosAlmacen.proveedores.paises.nombrePais}" styleClass="outputText2" id="nombrePaisProveedor" />

                            <h:outputText value="Proveedor:" styleClass="outputText2" />
                            <h:selectOneMenu  styleClass="inputText" value="#{ManagedIngresoAlmacen.ingresosAlmacen.proveedores.codProveedor}" style="width:100%" disabled="true" >
                                <f:selectItems value="#{ManagedIngresoAlmacen.proveedoresList}"  />
                                <a4j:support event="onchange" action="#{ManagedIngresoAlmacen.proveedores_change}" reRender="nombrePaisProveedor" />
                            </h:selectOneMenu>


                            <h:outputText value="Con Credito Fiscal:" styleClass="outputText2" />
                            <h:selectOneRadio value="#{ManagedIngresoAlmacen.ingresosAlmacen.creditoFiscalSiNo}" styleClass="outputText2" disabled="true">
                                <f:selectItem  itemValue="1" itemLabel="Si"  />
                                <f:selectItem  itemValue="0" itemLabel="No" />
                            </h:selectOneRadio>

                            <h:outputText value="Observaciones: " styleClass="outputText2" />
                            <h:inputTextarea  value="#{ManagedIngresoAlmacen.ingresosAlmacen.obsIngresoAlmacen}" styleClass="outputText2" cols="50" rows="5" />
                            <h:outputText value="Nro Documento: " styleClass="outputText2" />
                            <h:inputText value="#{ManagedIngresoAlmacen.ingresosAlmacen.nroDocumento}" styleClass="outputText2" disabled="#{!ManagedIngresoAlmacen.ingresosAlmacen.tiposIngresoAlmacen.editable}" />
                        </h:panelGrid>

                        <div align="center">
                            <a4j:commandButton value="Agregar Item" styleClass="btn"
                                               onclick="Richfaces.showModalPanel('panelAgregarItem')" action="#{ManagedIngresoAlmacen.agregarItem_action}"
                                               reRender="contenidoAgregarItem"
                                               rendered="#{ManagedIngresoAlmacen.ingresosAlmacen.tiposIngresoAlmacen.editable && ManagedIngresoAlmacen.ingresoEditableValidandoConSalidas}"
                                               />

                            <a4j:commandButton value="Eliminar Item" styleClass="btn"
                                               action="#{ManagedIngresoAlmacen.eliminarItem_action}"
                                               reRender="dataIngresoAlmacenDetalle"
                                               rendered="#{ManagedIngresoAlmacen.ingresosAlmacen.tiposIngresoAlmacen.editable && ManagedIngresoAlmacen.ingresoEditableValidandoConSalidas}"
                                               />
                        </div>

                    </rich:panel>




                    <rich:dataTable value="#{ManagedIngresoAlmacen.ingresosAlmacenDetalleList}" var="data"
                                    id="dataIngresoAlmacenDetalle"
                                    onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                    onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                    headerClass="headerClassACliente" 
                                    style="width:80%" >
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value=""  />
                            </f:facet>
                            <h:selectBooleanCheckbox value="#{data.checked}" />

                        </h:column>

                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Item"  />
                            </f:facet>
                            <h:outputText  value="#{data.materiales.nombreMaterial}"/>
                        </h:column>

                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Cant. Segun Documento"  />
                            </f:facet>
                            <h:outputText value="#{data.ordenesCompraDetalle.cantidadNeta}"  />
                        </h:column>
                        
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Unid."   />
                            </f:facet>
                            <h:outputText value="#{data.ordenesCompraDetalle.unidadesMedida.nombreUnidadMedida}"  />
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Cantidad Fisica"  />
                            </f:facet>
                            <h:outputText value="#{data.cantTotalIngresoFisico}"  />
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Unidades Empaque"  />
                            </f:facet>
                            <h:outputText value="#{data.unidadesMedida.nombreUnidadMedida}"  />
                        </h:column>
                                                
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Observacion"  />
                            </f:facet>
                            <h:outputText value="#{data.secciones.nombreSeccion}"  />
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value=""  />
                            </f:facet>
                            <h:outputText value="" />
                            <a4j:commandLink action="#{ManagedIngresoAlmacen.detallarIngresosAlmacenDetalleEditarAction}" 
                                             rendered="#{ManagedIngresoAlmacen.ingresosAlmacen.tiposIngresoAlmacen.editable && ManagedIngresoAlmacen.ingresoEditableValidandoConSalidas}"
                                             onclick="Richfaces.showModalPanel('panelDetalleItem')" reRender="contenidoDetalleItem" >
                                <h:graphicImage url="../img/areasdependientes.png" alt="detalle de item">

                                </h:graphicImage>
                                <f:setPropertyActionListener value="#{data}" target="#{ManagedIngresoAlmacen.ingresosAlmacenDetalle}"/>
                            </a4j:commandLink>
                        </h:column>

                    </rich:dataTable>

                    <br>


                    <a4j:commandButton value="Guardar" styleClass="btn"
                                       action="#{ManagedIngresoAlmacen.guardarEditarIngresoAlmacen_action}"
                                       oncomplete="mostrarMensajeTransaccionEvento(function(){redireccionar('navegadorIngresosAlmacen.jsf');});"
                                       />
                    <a4j:commandButton value="Cancelar" styleClass="btn" 
                                       onclick="if(confirm('¿Esta seguro de CANCELAR la Edición del Ingreso de Almacen?')==false){return false;}"
                                       oncomplete="location='navegadorIngresosAlmacen.jsf'" 
                                       />
                    </center>
                </a4j:form>

                <rich:modalPanel id="panelAgregarItem" minHeight="400"  minWidth="800"
                                 height="400" width="800"
                                 zindex="200"
                                 headerClass="headerClassACliente"
                                 resizeable="false" style="overflow :auto" >
                    <f:facet name="header">
                        <h:outputText value="Detalle Item"/>
                        <f:facet name="controls">
                            <a4j:commandLink onclick="javascript:Richfaces.hideModalPanel('panelAgregarItem')" style="color: black" value="cerrar"></a4j:commandLink>
                        </f:facet>
                    </f:facet>



                    <a4j:form id="form2">
                        <h:panelGroup id="contenidoAgregarItem">
                            <center>
                                <h:panelGrid columns="4" id="contenidoIngresoAlmacenDetalle" headerClass="headerClassACliente" width="90%" style="border:1px solid #000000;" >
                                    <f:facet name="header">
                                        <h:outputText value="Buscar Item" />
                                    </f:facet>

                                    <h:outputText value="Capitulo:" styleClass="tituloCampo1" />
                                    <h:selectOneMenu value="#{ManagedIngresoAlmacen.buscarMaterial.grupos.capitulos.codCapitulo}" styleClass="inputText" >
                                        <f:selectItems value="#{ManagedIngresoAlmacen.capitulosList}"  />
                                        <a4j:support event="onchange" action="#{ManagedIngresoAlmacen.capitulos_change}" reRender="codGrupo" />
                                    </h:selectOneMenu>

                                    <h:outputText value="Grupo:" styleClass="tituloCampo1" />
                                    <h:selectOneMenu value="#{ManagedIngresoAlmacen.buscarMaterial.grupos.codGrupo}" styleClass="inputText" id="codGrupo" >
                                        <f:selectItems value="#{ManagedIngresoAlmacen.gruposList}"  />
                                    </h:selectOneMenu>

                                    <h:outputText value="Item:" styleClass="tituloCampo1" />

                                    <h:inputText value="#{ManagedIngresoAlmacen.buscarMaterial.nombreMaterial}" styleClass="inputText" size="50"  />

                                    <a4j:commandButton value="Buscar" action="#{ManagedIngresoAlmacen.buscarMaterial_action}" styleClass="btn"  reRender="dataBuscarMaterial"  />

                                </h:panelGrid>                             
                                <br/>

                                <rich:dataTable value="#{ManagedIngresoAlmacen.materialesBuscarList}" var="data"
                                                id="dataBuscarMaterial"
                                                onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                                onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                                headerClass="headerClassACliente"
                                                binding="#{ManagedIngresoAlmacen.materialesBuscarDataTable}" >


                                    <h:column>
                                        <f:facet name="header">
                                            <h:outputText value="Material"  />
                                        </f:facet>
                                        <a4j:commandLink  action="#{ManagedIngresoAlmacen.seleccionarMaterial_action}" styleClass="outputText2" onclick="Richfaces.hideModalPanel('panelAgregarItem')" reRender="dataIngresoAlmacenDetalle" >
                                            <h:outputText  value="#{data.nombreMaterial}" styleClass="outputText2" />
                                        </a4j:commandLink>

                                    </h:column>

                                    <h:column>
                                        <f:facet name="header">
                                            <h:outputText value="Grupo"  />
                                        </f:facet>
                                        <h:outputText  value="#{data.grupos.nombreGrupo}"/>
                                    </h:column>

                                    <h:column>
                                        <f:facet name="header">
                                            <h:outputText value="Capitulo"  />
                                        </f:facet>
                                        <h:outputText value="#{data.grupos.capitulos.nombreCapitulo}"  />
                                    </h:column>

                                </rich:dataTable>
                                
                                <input type="button" value="Cancelar" class="btn" onclick="javascript:Richfaces.hideModalPanel('panelAgregarItem')" />
                            
                        </center>

                        </h:panelGroup>
                    </a4j:form>
                </rich:modalPanel>

                <a4j:include viewId="panelDetallarItem.jsp" id="detalleItem"/>
                <a4j:include viewId="/panelProgreso.jsp" />
                <a4j:include viewId="/message.jsp" />




        </body>
    </html>

</f:view>

