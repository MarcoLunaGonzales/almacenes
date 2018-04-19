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
                    var elements=document.getElementById('form2:dataIngresoAlmacenDetalleEstado');
                    var rowsElement=elements.rows;
                    var cantidadIngresoFisico = 0;
                    var totalImporteF=0;
                    for(var i=1;i<rowsElement.length;i++){
                        var cellsElement=rowsElement[i].cells;
                        var cel3=cellsElement[3];
                        var cantidadParcial=cel3.getElementsByTagName('input')[0];
                        if(cantidadParcial.type=='text'){
                            cantidadIngresoFisico=parseFloat(cantidadIngresoFisico)+parseFloat(cantidadParcial.value);
                        }
                    }
                    //alert(document.getElementById('form2:cantTotalIngresoFisico'));
                    //alert(cantidadIngresoFisico);
                    document.getElementById('form2:cantTotalIngresoFisico').value=Math.round(cantidadIngresoFisico*100)/100;
       }
       </script>
        </head>
        <body >
            <a4j:form id="form1">
                <div align="center">
                    <h:outputText value="#{ManagedIngresoAlmacenOrdenCompra.cargarAgregarIngresoAlmacenOrdenCompra}"/>
                    <br><br>
                    <rich:panel id="panelEstadoCuentas" styleClass="panelBuscar" style="top:50px;left:50px;width:700px;">
                    <f:facet name="header">
                        <h:outputText value="<div   onmouseover=\"this.style.cursor='move'\" onmousedown=\"comienzoMovimiento(event, 'form1:panelEstadoCuentas');\"  >Buscar<div   style=\"margin-left:550px;\"   onclick=\"document.getElementById('form1:panelEstadoCuentas').style.visibility='hidden';document.getElementById('panelsuper').style.visibility='hidden';\" onmouseover=\"this.style.cursor='hand'\"   >Cerrar</div> </div> "
                              escape="false" />
                    </f:facet>
                    </rich:panel>

                  <rich:panel style="width:80%" headerClass="headerClassACliente">
                            <f:facet name="header">
                                <h:outputText value="Registro de Ingresos a Almacen"  />
                            </f:facet>
                        <h:panelGrid columns="4" >
                            
                            <h:outputText value="Gestion:" styleClass="outputText2" />
                            <h:outputText value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacen.gestiones.nombreGestion}" styleClass="outputText1" />
                            <h:outputText value="Fecha de Ingreso:" styleClass="outputText2" />
                            <h:panelGroup>
                                <h:inputText readonly="true" value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacen.fechaIngresoAlmacen}"   styleClass="inputText2"  id="fechaIngresoAlmacen"  size="15" onblur="if(this.value!=''){valFecha(this);}" >
                                 <f:convertDateTime pattern="dd/MM/yyyy"   />
                                </h:inputText>                                
                            </h:panelGroup>

                            <h:outputText value="Nro Ingreso Almacen:" styleClass="outputText2" />
                            <h:outputText value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacen.nroIngresoAlmacen}" styleClass="outputText1" />

                            <h:outputText value="Nro Orden de Compra:" styleClass="outputText2" />
                            <h:outputText value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacen.ordenesCompra.nroOrdenCompra}" styleClass="outputText1" />

                            <h:outputText value="Tipo de Ingreso:" styleClass="outputText2" />
                                <h:selectOneMenu disabled="true" styleClass="inputText" value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacen.tiposIngresoAlmacen.codTipoIngresoAlmacen}"   >
                                   <f:selectItems value="#{ManagedIngresoAlmacenOrdenCompra.tiposIngresosAlmacenList}"  />
                                </h:selectOneMenu>
                            

                            <h:outputText value="Tipo de Orden de Compra:" styleClass="outputText2" />
                            <h:outputText value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacen.ordenesCompra.tiposCompra.codTipoCompra}" styleClass="outputText1" />

                            <h:outputText value="Proveedor:" styleClass="outputText2" />
                            <h:outputText value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacen.ordenesCompra.proveedores.nombreProveedor}" styleClass="outputText1" />

                            <h:outputText value="Origen de Proveedor:" styleClass="outputText2" />
                            <h:outputText value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacen.ordenesCompra.proveedores.paises.nombrePais}" styleClass="outputText1" />

                            <h:outputText value="representante:" styleClass="outputText2" />
                            <h:outputText value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacen.ordenesCompra.representantes.nombreRepresentante}" styleClass="outputText1" />


                            <h:outputText value="Estado Ingreso:" styleClass="outputText2" />
                            <h:selectOneMenu disabled="true" styleClass="inputText" value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacen.estadosIngresoAlmacen.codEstadoIngresoAlmacen}" >
                                    <f:selectItems value="#{ManagedIngresoAlmacenOrdenCompra.estadosIngresoAlmacenList}"  />
                            </h:selectOneMenu>

                        </h:panelGrid>
                    </rich:panel>
                    
                    
                    <rich:dataTable value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalleList}" var="data"
                                    id="dataIngresoAlmacenDetalle"
                                    onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                    onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                    headerClass="headerClassACliente" binding="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalleDataTable}"
                                    style="width:80%">
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value=""  />
                            </f:facet>
                            <h:outputText value="" />
                            <a4j:commandLink action="#{ManagedIngresoAlmacenOrdenCompra.detallarItem_action}" value="detallar"
                            onclick="Richfaces.showModalPanel('panelDetalleItem')" reRender="contenidoDetalleItem" >                                
                            </a4j:commandLink>
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
                            <h:outputText value="#{data.cantTotalIngreso}"  />
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
                                <h:outputText value="Observacion"  />
                            </f:facet>
                            <h:outputText value="0"  />
                        </h:column>
                    </rich:dataTable>

                    <%-- <ws:datascroller fordatatable="dataAreasEmpresa"  />--%>
                    <br>
                    <a4j:commandButton value="Guardar" styleClass="btn"                    
                    action="#{ManagedIngresoAlmacenOrdenCompra.guardarIngresoAlmacen_action}"
                    oncomplete="location='navegadorIngresosAlmacenOrdenCompra.jsf'"
                    />
                    <%--
                    <a4j:commandButton value="Agregar" styleClass="btn" onclick="javascript:Richfaces.showModalPanel('panelDetalleItem')" action="#{ManagedFrecuenciaMantenimientoMaquinaria.agregarFrecuencia_action}"
                    reRender="contenidoDetalleItem" />
                    <a4j:commandButton value="Eliminar"  styleClass="btn" onclick="javascript:if(confirm('Esta Seguro de Eliminar??')==false || validarSeleccion('form1:dataFrecuenciaMantenimientoMaquina')==false){return false;}"  action="#{ManagedFrecuenciaMantenimientoMaquinaria.eliminarFrecuenciaMantenimientoMaquina_action}"
                    reRender="dataFrecuenciaMantenimientoMaquina"/>
                    <a4j:commandButton value="Editar"  styleClass="btn" onclick="javascript:if(validarSeleccion('form1:dataFrecuenciaMantenimientoMaquina')==false){return false;}else{Richfaces.showModalPanel('panelEditarFrecuenciaMantenimientoMaquinaria')}"  action="#{ManagedFrecuenciaMantenimientoMaquinaria.editarFrecuenciaMantenimientoMaquina_action}"
                    reRender="contenidoEditarDetalleItem" />
                    --%>

                

               
                <!--cerrando la conexion-->
                <h:outputText value="#{ManagedFrecuenciaMantenimientoMaquinaria.closeConnection}"  />

            </a4j:form>

             <rich:modalPanel id="panelDetalleItem" minHeight="400"  minWidth="800"
                                     height="400" width="800"
                                     zindex="200"
                                     headerClass="headerClassACliente"
                                     resizeable="false" style="overflow :auto" >
                        <f:facet name="header">
                            <h:outputText value="Detalle Item"/>
                        </f:facet>
                        <a4j:form id="form2">
                        <h:panelGroup id="contenidoDetalleItem">
                            <div align="center">
                           <h:outputText value="Item:" styleClass="tituloCampo1" />
                           <h:outputText value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalle.materiales.nombreMaterial}" styleClass="outputText1" />
                           <h:panelGrid columns="9" id="contenidoIngresoAlmacenDetalle" headerClass="headerClassACliente" width="90%" style="border:1px solid #000000;" >
                                <f:facet name="header">
                                      <h:outputText value="Repetir Valores" />
                                </f:facet>
                                
                                <h:outputText value="Cantidad Segun Documento:" styleClass="tituloCampo1" />                                
                                <h:panelGroup>                                     
                                <h:inputText value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalle.cantTotalIngreso}" styleClass="inputText" size="5" id="cantTotalIngreso" onkeypress="valNum(event);" readonly="true" onkeyup="hallaValorEquivalente(this)" />
                                    <h:outputText value="[" styleClass="outputText1" />
                                    <h:outputText value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalle.ordenesCompraDetalle.unidadesMedida.abreviatura}" styleClass="outputText1" />
                                    <h:outputText value="]" styleClass="outputText1" />
                                </h:panelGroup>

                                <h:outputText value="Equivalente:" styleClass="tituloCampo1" />
                                <h:panelGroup>
                                    <h:inputText value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalle.cantEquivalente}" styleClass="inputText" size="5" readonly="true" onkeypress="valNum(event);" id="cantEquivalente" />
                                    <h:outputText value="[" styleClass="outputText1" />
                                    <h:outputText value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalle.unidadesMedidaEquivalencia.abreviatura}" styleClass="outputText1" />
                                    <h:outputText value="]" styleClass="outputText1" />
                                    <h:inputHidden value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalle.valorEquivalencia}" id="valorEquivalencia" />
                                </h:panelGroup>

                                <h:outputText value="Cant. Ingreso Fisico:" styleClass="tituloCampo1" />
                                <h:panelGroup>
                                    <h:inputText value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalle.cantTotalIngresoFisico}" id="cantTotalIngresoFisico"  styleClass="inputText" size="5" onkeypress="valNum(event);" readonly="true" />
                                    <h:outputText value="[" styleClass="outputText1" />
                                    <h:outputText value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalle.unidadesMedida.abreviatura}" styleClass="outputText1" />
                                    <h:outputText value="]" styleClass="outputText1" />
                                </h:panelGroup>

                                <h:outputText value="Nro de Empaques:" styleClass="tituloCampo1" />
                                
                                <h:inputText value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalle.nroUnidadesEmpaque}" styleClass="inputText" size="5"  />
                                <a4j:commandButton value="Generar Etiquetas" action="#{ManagedIngresoAlmacenOrdenCompra.generarEtiquetas_action}" styleClass="btn"  reRender="dataIngresoAlmacenDetalleEstado"  />
                                
                              </h:panelGrid>

                              <br/>
                               
                                
                             
                              <br/>

                              
                              <h:panelGrid columns="6" headerClass="headerClassACliente" width="90%" style="border:1px solid #000000;">
                                  <f:facet name="header">
                                      <h:outputText value="Repetir Valores" />
                                  </f:facet>


                                <h:outputText value="Cantidad Parcial:" styleClass="outputText1" />
                                <h:inputText value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalleEstado.cantidadParcial}" styleClass="inputText" size="10" onkeypress="valNum(event);" >
                                    
                                </h:inputText>

                                <h:outputText value="Lote Proveedor:" styleClass="outputText1" />
                                <h:inputText value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalleEstado.loteMaterialProveedor}" styleClass="inputText" size="10" onkeypress="valNum(event);" >
                                    
                                </h:inputText>

                                <h:outputText value="Unidad de Empaque:" styleClass="outputText1" />
                                <h:selectOneMenu value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalleEstado.empaqueSecundarioExterno.codEmpaqueSecundarioExterno}" styleClass="inputText" >
                                    <f:selectItems value="#{ManagedIngresoAlmacenOrdenCompra.empaqueSecundarioExternoList}"  />
                                </h:selectOneMenu>

                                <h:outputText value="Fecha Manufactura:" styleClass="outputText1" />
                                <rich:calendar   datePattern="dd/MM/yyyy" styleClass="inputText"
                                    value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalleEstado.fechaManufactura}"  enableManualInput="true" oninputblur="valFecha(this);" width="1" />
                                
                                <h:outputText value="Fecha de Reanalisis:" styleClass="outputText1" />
                                <rich:calendar   datePattern="dd/MM/yyyy" styleClass="inputText"
                                    value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalleEstado.fechaReanalisis}"  enableManualInput="true" oninputblur="valFecha(this);" width="1" />
                                    
                                <h:outputText value="Fecha de Vencimiento:" styleClass="outputText1" />
                                <rich:calendar   datePattern="dd/MM/yyyy" styleClass="inputText"
                                    value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalleEstado.fechaVencimiento}"  enableManualInput="true" oninputblur="valFecha(this);" width="1" />

                            </h:panelGrid>
                            <a4j:commandButton action="#{ManagedIngresoAlmacenOrdenCompra.repetirValoresIngresoAlmacenDetalleEstado_action}" reRender="dataIngresoAlmacenDetalleEstado,contenidoIngresoAlmacenDetalle" value="Repetir Valores" styleClass="btn" />
                            

                            <rich:dataTable value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalle.ingresosAlmacenDetalleEstadoList}" var="data"
                                    id="dataIngresoAlmacenDetalleEstado"
                                    onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                    onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                    headerClass="headerClassACliente">
                                <h:column>
                                    <f:facet name="header">
                                        <h:outputText value=""  />
                                    </f:facet>
                                    <h:outputText  value="#{data.etiqueta}"/>
                                </h:column>

                                <h:column>
                                    <f:facet name="header">
                                        <h:outputText value="Estado Item"  />
                                    </f:facet>
                                    <h:outputText  value="#{data.estadosMaterial.nombreEstadoMaterial}"/>
                                </h:column>

                                <h:column>
                                    <f:facet name="header">
                                        <h:outputText value="Empaque"  />
                                    </f:facet>
                                    <h:outputText value="#{data.empaqueSecundarioExterno.nombreEmpaqueSecundarioExterno}"  />
                                </h:column>

                                <h:column>
                                    <f:facet name="header">
                                        <h:outputText value="Cantidad Parcial"  />
                                    </f:facet>
                                    <h:inputText value="#{data.cantidadParcial}" styleClass="inputText" size="5"  onkeyup="hallaCantidadIngresoFisico()"  />
                                </h:column>
                                <h:column>
                                    <f:facet name="header">
                                        <h:outputText value="Fecha Manufactura"  />
                                    </f:facet>
                                    <h:inputText value="#{data.fechaManufactura}" styleClass="inputText" size="10"  onblur="valFecha(this);" onkeyup="hallaCantidadIngresoFisico()"  >
                                        <f:convertDateTime pattern="dd/MM/yyyy" />    
                                    </h:inputText>
                                    
                                </h:column>
                                <h:column>
                                    <f:facet name="header">
                                        <h:outputText value="Lote Proveedor"  />
                                    </f:facet>
                                    <h:outputText value="#{data.loteMaterialProveedor}"  />
                                </h:column>

                                <h:column>
                                    <f:facet name="header">
                                        <h:outputText value="Fecha Reanalisis"  />
                                    </f:facet>
                                    <h:inputText value="#{data.fechaReanalisis}" styleClass="inputText" size="10"  onblur="valFecha(this);" onkeyup="hallaCantidadIngresoFisico()"  >
                                        <f:convertDateTime pattern="dd/MM/yyyy" />
                                    </h:inputText>
                                </h:column>

                                 <h:column>
                                    <f:facet name="header">
                                        <h:outputText value="Fecha Vencimiento"  />
                                    </f:facet>
                                    <h:inputText value="#{data.fechaVencimiento}" styleClass="inputText" size="10"  onblur="valFecha(this);" onkeyup="hallaCantidadIngresoFisico()"  >
                                        <f:convertDateTime pattern="dd/MM/yyyy" />
                                    </h:inputText>
                                </h:column>

                                <h:column>
                                    <f:facet name="header">
                                        <h:outputText value="Obervaciones"  />
                                    </f:facet>
                                    <h:inputText value="#{data.observaciones}" size="20" styleClass="inputText"  />
                                </h:column>
                                
                            </rich:dataTable>
                            </h:panelGroup>
                            </div>


                                <div align="center">
                                <a4j:commandButton styleClass="btn" value="Registrar"                                                   
                                                   oncomplete="if(#{ManagedIngresoAlmacenOrdenCompra.mensaje!=''}){alert('#{ManagedIngresoAlmacenOrdenCompra.mensaje}')}else{Richfaces.hideModalPanel('panelDetalleItem')}"
                                                   action="#{ManagedIngresoAlmacenOrdenCompra.guardarDetalleItem_action}"
                                                   reRender="dataIngresoAlmacenDetalle"
                                                    />
                                <input type="button" value="Cancelar" class="btn" onclick="javascript:Richfaces.hideModalPanel('panelDetalleItem')" />
                                </div>
                        
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
                        <h:panelGroup id="contenidoEditarDetalleItem">
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
                   </div>
            </rich:modalPanel>
            


        </body>
    </html>

</f:view>

