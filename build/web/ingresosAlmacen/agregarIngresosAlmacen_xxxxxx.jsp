asdadasdasd
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
                    var elements=document.getElementById('form3:dataIngresoAlmacenDetalleEstado');
                    var rowsElement=elements.rows;
                    var cantidadIngresoFisico = 0;
                    var totalImporteF=0;
                    for(var i=1;i<rowsElement.length;i++){
                        var cellsElement=rowsElement[i].cells;
                        var cel3=cellsElement[3];
                        var cantidadParcial=cel3.getElementsByTagName('input')[0];
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
       </script>
        </head>
        <body >
            <a4j:form id="form1">
                <div align="center">
                    <h:outputText value="#{ManagedIngresoAlmacen.cargarAgregarIngresosAlmacen}"/>
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
                            <h:outputText value="#{ManagedIngresoAlmacen.ingresosAlmacen.gestiones.nombreGestion}" styleClass="outputText1" />
                            <h:outputText value="Fecha de Ingreso:" styleClass="outputText2" />

                                 <h:outputText value="#{ManagedIngresoAlmacen.ingresosAlmacen.fechaIngresoAlmacen}" styleClass="outputText2" >
                                 <f:convertDateTime pattern="dd/MM/yyyy"   />
                                 </h:outputText>
                            

                            <h:outputText value="Nro Ingreso Almacen:" styleClass="outputText2" />
                            <h:outputText value="#{ManagedIngresoAlmacen.ingresosAlmacen.nroIngresoAlmacen}" styleClass="outputText1" />

                            <h:outputText value="Estado De Ingreso Almacen:" styleClass="outputText2" />
                            <h:outputText value="APROBADO" styleClass="outputText1" />

                            <h:outputText value="Tipo de Ingreso:" styleClass="outputText2" />
                            <h:selectOneMenu  styleClass="inputText" value="#{ManagedIngresoAlmacen.ingresosAlmacen.tiposIngresoAlmacen.codTipoIngresoAlmacen}"   >
                              <f:selectItems value="#{ManagedIngresoAlmacen.tiposIngresosAlmacenList}"  />
                              <a4j:support action="#{ManagedIngresoAlmacen.tipoIngreso_change}" event="onchange"/>
                            </h:selectOneMenu>

                            <h:outputText value="Tipo de Compra:" styleClass="outputText2" />
                            <h:selectOneMenu  styleClass="inputText" value="#{ManagedIngresoAlmacen.ingresosAlmacen.tiposCompra.codTipoCompra}"   >
                                <f:selectItems value="#{ManagedIngresoAlmacen.tiposCompraList}"  />
                            </h:selectOneMenu>

                            <h:outputText value="Origen Proveedor: " styleClass="outputText2" />
                            <h:outputText value="#{ManagedIngresoAlmacen.ingresosAlmacen.proveedores.paises.nombrePais}" styleClass="outputText2" id="nombrePaisProveedor" />

                            <h:outputText value="Proveedor:" styleClass="outputText2" />
                            <h:selectOneMenu  styleClass="inputText" value="#{ManagedIngresoAlmacen.ingresosAlmacen.proveedores.codProveedor}" style="width:100%"  >
                                <f:selectItems value="#{ManagedIngresoAlmacen.proveedoresList}"  />
                                <a4j:support event="onchange" action="#{ManagedIngresoAlmacen.proveedores_change}" reRender="nombrePaisProveedor" />
                            </h:selectOneMenu>


                            <h:outputText value="Con Credito Fiscal:" styleClass="outputText2" />
                            <h:selectOneRadio value="#{ManagedIngresoAlmacen.ingresosAlmacen.creditoFiscalSiNo}" styleClass="outputText2">
                                <f:selectItem  itemValue="1" itemLabel="Si"  />
                                <f:selectItem  itemValue="0" itemLabel="No" />
                            </h:selectOneRadio>

                            


                            <h:outputText value="Observaciones: " styleClass="outputText2" />
                            <h:inputTextarea  value="#{ManagedIngresoAlmacen.ingresosAlmacen.obsIngresoAlmacen}" styleClass="outputText2" cols="50" rows="5" />
                            <h:outputText value="Nro Documento: " styleClass="outputText2" />
                            <h:inputText value="#{ManagedIngresoAlmacen.ingresosAlmacen.nroDocumento}" styleClass="outputText2"/>
                        </h:panelGrid>
                    </rich:panel>

                    
                    
                    
                    <rich:dataTable value="#{ManagedIngresoAlmacen.ingresosAlmacenDetalleList}" var="data"
                                    id="dataIngresoAlmacenDetalle"
                                    onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                    onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                    headerClass="headerClassACliente" 
                                    style="width:80%" binding="#{ManagedIngresoAlmacen.ingresosAlmacenDetalleDataTable}" >
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
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value=""  />
                            </f:facet>
                            <h:outputText value="" />
                            <a4j:commandLink action="#{ManagedIngresoAlmacen.detallarItem_action}" 
                            onclick="Richfaces.showModalPanel('panelDetalleItem')" reRender="contenidoDetalleItem" >
                                <h:graphicImage url="../img/areasdependientes.png" alt="detalle de item">

                                </h:graphicImage>

                            </a4j:commandLink>
                        </h:column>
                        
                    </rich:dataTable>

                    <%-- <ws:datascroller fordatatable="dataAreasEmpresa"  />--%>
                    <br>

                     
                    <a4j:commandButton value="Guardar" styleClass="btn"                    
                    action="#{ManagedIngresoAlmacen.guardarIngresoAlmacen_action}"
                    oncomplete="if(#{ManagedIngresoAlmacen.mensaje!=''}){alert('#{ManagedIngresoAlmacen.mensaje}')} else {location='navegadorIngresosAlmacen.jsf'}"
                    timeout="7200"
                    />
                    
                    <a4j:commandButton value="Agregar" styleClass="btn"
                    onclick="Richfaces.showModalPanel('panelAgregarItem')" action="#{ManagedIngresoAlmacen.agregarItem_action}"
                    reRender="contenidoAgregarItem"
                    timeout="7200"
                    />
                    
                    <a4j:commandButton value="Eliminar" styleClass="btn"
                    action="#{ManagedIngresoAlmacen.eliminarItem_action}"
                    reRender="dataIngresoAlmacenDetalle"
                    timeout="7200"
                    />
                   
                    
                <!--cerrando la conexion-->
                <h:outputText value="#{ManagedFrecuenciaMantenimientoMaquinaria.closeConnection}"  />

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
                          <div align="center">
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
                                
                                <a4j:commandButton value="Buscar" action="#{ManagedIngresoAlmacen.buscarMaterial_action}" styleClass="btn"  reRender="dataBuscarMaterial" timeout="7200" />
                                
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
                            </h:panelGroup>
                            </div>
                            
                                <div align="center">                                
                                <input type="button" value="Cancelar" class="btn" onclick="javascript:Richfaces.hideModalPanel('panelAgregarItem')" />
                                </div>
                        
                        </a4j:form>
            </rich:modalPanel>
            
            <rich:modalPanel id="panelDetalleItem" minHeight="400"  minWidth="1100"
                                     height="400" width="1100"
                                     zindex="200"
                                     headerClass="headerClassACliente"
                                     resizeable="false" style="overflow :auto" >
                        <f:facet name="header">
                            <h:outputText value="Detalle Item"/>
                        </f:facet>
                        <a4j:form id="form3">
                                <h:panelGroup id="contenidoDetalleItem">
                                    <div align="center">                                   
                                    <h:outputText value="Item: " styleClass="tituloCampo1" />
                                    &nbsp;
                                    <h:outputText value="#{ManagedIngresoAlmacen.ingresosAlmacenDetalle.materiales.nombreMaterial}" styleClass="tituloCampo1" />
                                   
                                   <br/>
                                   <br/>

                                   <h:panelGrid columns="9" id="contenidoIngresoAlmacenDetalle" headerClass="headerClassACliente" width="100%" style="border:1px solid #000000;" >
                                        <f:facet name="header">
                                              <h:outputText value="Repetir Valores" />
                                        </f:facet>
                                        
                                        <h:outputText value="Cantidad Segun Documento:" styleClass="outputText1" />

                                        <h:panelGroup>
                                            <h:inputText value="#{ManagedIngresoAlmacen.ingresosAlmacenDetalle.cantTotalIngreso}" styleClass="inputText" size="5" id="cantTotalIngreso" onkeypress="valNum(event);" readonly="true" onkeyup="hallaValorEquivalente(this)" />
                                            <h:outputText value="[" styleClass="outputText1" />
                                            <h:outputText value="#{ManagedIngresoAlmacen.ingresosAlmacenDetalle.ordenesCompraDetalle.unidadesMedida.abreviatura}" styleClass="outputText1" />
                                            <h:outputText value="]" styleClass="outputText1" />
                                        </h:panelGroup>

                                        <h:outputText value="Equivalente:" styleClass="outputText1" />
                                        <h:panelGroup>
                                            <h:inputText value="#{ManagedIngresoAlmacen.ingresosAlmacenDetalle.cantEquivalente}" styleClass="inputText" size="5" readonly="true" onkeypress="valNum(event);" id="cantEquivalente" />
                                            <h:outputText value="[" styleClass="outputText1" />
                                            <h:outputText value="#{ManagedIngresoAlmacen.ingresosAlmacenDetalle.unidadesMedidaEquivalencia.abreviatura}" styleClass="outputText1" />
                                            <h:outputText value="]" styleClass="outputText1" />
                                            <h:inputHidden value="#{ManagedIngresoAlmacen.ingresosAlmacenDetalle.valorEquivalencia}" id="valorEquivalencia" />
                                        </h:panelGroup>

                                        <h:outputText value="Cant. Ingreso Fisico:" styleClass="outputText1" />
                                        <h:panelGroup>
                                            <h:inputText value="#{ManagedIngresoAlmacen.ingresosAlmacenDetalle.cantTotalIngresoFisico}" id="cantTotalIngresoFisico"  styleClass="inputText" size="5" onkeypress="valNum(event);" readonly="true" />
                                            <h:outputText value="[" styleClass="outputText1" />
                                            <h:outputText value="#{ManagedIngresoAlmacen.ingresosAlmacenDetalle.unidadesMedida.abreviatura}" styleClass="outputText1" />
                                            <h:outputText value="]" styleClass="outputText1" />
                                        </h:panelGroup>

                                        <h:outputText value="Nro de Empaques:" styleClass="outputText1" />

                                        <h:inputText value="#{ManagedIngresoAlmacen.ingresosAlmacenDetalle.nroUnidadesEmpaque}" styleClass="inputText" size="5"  />
                                        <a4j:commandButton value="Generar Etiquetas" action="#{ManagedIngresoAlmacen.generarEtiquetas_action}" styleClass="btn"  reRender="dataIngresoAlmacenDetalleEstado" timeout="7200" />
                                      </h:panelGrid>

                                      <br/>
                                      
                                      <h:panelGrid columns="6" headerClass="headerClassACliente" width="100%" style="border:1px solid #000000;">
                                          <f:facet name="header">
                                              <h:outputText value="Repetir Valores" />
                                          </f:facet>


                                        <h:outputText value="Cantidad Parcial:" styleClass="outputText1" />
                                        <h:inputText value="#{ManagedIngresoAlmacen.ingresosAlmacenDetalleEstado.cantidadParcial}" styleClass="inputText" size="10" onkeypress="valNumPositivo(event);" >

                                        </h:inputText>

                                        <h:outputText value="Lote Proveedor:" styleClass="outputText1" />
                                        <h:inputText value="#{ManagedIngresoAlmacen.ingresosAlmacenDetalleEstado.loteMaterialProveedor}" styleClass="inputText" size="10"  ><!--onkeypress="valNum(event);"-->
                                        </h:inputText>

                                        <h:outputText value="Unidad de Empaque:" styleClass="outputText1" />
                                        <h:selectOneMenu value="#{ManagedIngresoAlmacen.ingresosAlmacenDetalleEstado.empaqueSecundarioExterno.codEmpaqueSecundarioExterno}" styleClass="inputText" >
                                            <f:selectItems value="#{ManagedIngresoAlmacen.empaqueSecundarioExternoList}"  />
                                        </h:selectOneMenu>

                                        <h:outputText value="Fecha Manufactura:" styleClass="outputText1" />
                                        <rich:calendar datePattern="dd/MM/yyyy" styleClass="inputText" immediate="true" 
                                        value="#{ManagedIngresoAlmacen.ingresosAlmacenDetalleEstado.fechaManufactura}"   id="fechaManufactura" enableManualInput="true"  width="1" oninputblur="valFecha(this);">
                                                <%--a4j:support event="onchanged" /--%>
                                                <%--a4j:support event="oninputblur" ajaxSingle="true" action="#{ManagedIngresoAlmacen.validaFechas_action}" reRender="contenidoDetalleItem"
                                                oncomplete="if(#{ManagedIngresoAlmacen.mensaje!=''}){alert('#{ManagedIngresoAlmacen.mensaje}');document.getElementById('form3:fechaManufacturaInputDate').focus()}else{valFecha(document.getElementById('form3:fechaManufacturaInputDate'));}" / --%><%-- ondateselect="document.getElementById('form3:fechaManufacturaInputDate').focus(); return true;" --%>
                                        </rich:calendar>
                                        

                                        <%--h:outputText value="Fecha de Reanalisis:" styleClass="outputText1" />
                                        <rich:calendar   datePattern="dd/MM/yyyy" styleClass="inputText"
                                            value="#{ManagedIngresoAlmacen.ingresosAlmacenDetalleEstado.fechaReanalisis}"  enableManualInput="true" oninputblur="valFecha(this);" width="1" /--%>

                                        <h:outputText value="Fecha de Vencimiento:" styleClass="outputText1" />
                                        <rich:calendar   datePattern="dd/MM/yyyy" styleClass="inputText" immediate="true"
                                        value="#{ManagedIngresoAlmacen.ingresosAlmacenDetalleEstado.fechaVencimiento}"  id="fechaVencimiento" enableManualInput="true"  width="1" oninputblur="valFecha(this);">
                                            <%--a4j:support event="oninputblur" ajaxSingle="true" action="#{ManagedIngresoAlmacen.validaFechas_action1}"
                                                oncomplete="if(#{ManagedIngresoAlmacen.mensaje!=''}){alert('#{ManagedIngresoAlmacen.mensaje}');document.getElementById('form3:fechaVencimientoInputDate').focus()}else{valFecha(document.getElementById('form3:fechaVencimientoInputDate'));}" /--%><%-- ondateselect="document.getElementById('form3:fechaVencimientoInputDate').focus(); return true;" --%>
                                         </rich:calendar>
                                         <h:outputText value="Tara:" styleClass="outputText1" />
                                         <h:inputText value="#{ManagedIngresoAlmacen.ingresosAlmacenDetalleEstado.tara}" styleClass="inputText" size="10" onkeypress="valNum(event)"  ><!--onkeypress="valNum(event);"-->
                                        </h:inputText>
                                        <h:outputText value="Ambiente:" styleClass="outputText1" />
                                        <h:selectOneMenu value="#{ManagedIngresoAlmacen.ingresosAlmacenDetalleEstado.estanteAlmacen.ambienteAlmacen.codAmbiente}" styleClass="inputText" >
                                            <f:selectItem itemValue="0" itemLabel="-NINGUNO-" />
                                            <f:selectItems value="#{ManagedIngresoAlmacen.ambienteAlmacenList}"  />
                                            <a4j:support event="onchange" reRender="estante" action="#{ManagedIngresoAlmacen.ambienteAlmacen_change}" />
                                        </h:selectOneMenu>
                                        <h:outputText value="Estante:" styleClass="outputText1" />
                                        <h:selectOneMenu value="#{ManagedIngresoAlmacen.ingresosAlmacenDetalleEstado.estanteAlmacen.codEstante}" styleClass="inputText" id="estante" >
                                            <f:selectItem itemValue="0" itemLabel="-NINGUNO-" />
                                            <f:selectItems value="#{ManagedIngresoAlmacen.estanteAlmacenList}"  />
                                        </h:selectOneMenu>
                                        <h:outputText value="" />
                                        <h:outputText value="" />

                                        <h:outputText value="Fila:" styleClass="outputText1" />
                                        <h:inputText value="#{ManagedIngresoAlmacen.ingresosAlmacenDetalleEstado.fila}" styleClass="inputText" size="10"  ><!--onkeypress="valNum(event);"-->
                                        </h:inputText>
                                        
                                        <h:outputText value="Columna:" styleClass="outputText1" />
                                        <h:inputText value="#{ManagedIngresoAlmacen.ingresosAlmacenDetalleEstado.columna}" styleClass="inputText" size="10"  ><!--onkeypress="valNum(event);"-->
                                        </h:inputText>


                                    </h:panelGrid>
                                    <br/>

                                    <h:selectBooleanCheckbox id="seleccionar_todo"  onclick="seleccionarTodo('form3:dataIngresoAlmacenDetalleEstado');"/>
                                    <a4j:commandButton action="#{ManagedIngresoAlmacen.repetirValoresIngresoAlmacenDetalleEstado_action}" oncomplete="if(#{ManagedIngresoAlmacen.mensaje!=''}){alert('#{ManagedIngresoAlmacen.mensaje}')}" reRender="dataIngresoAlmacenDetalleEstado,contenidoIngresoAlmacenDetalle" value="Repetir Valores" styleClass="btn" timeout="7200" />
                                    <br/>
                                    
                                    <rich:dataTable value="#{ManagedIngresoAlmacen.ingresosAlmacenDetalle.ingresosAlmacenDetalleEstadoList}" var="data"
                                            id="dataIngresoAlmacenDetalleEstado"
                                            onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                            onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                            headerClass="headerClassACliente"
                                            width="100%" >

                                        <h:column>
                                            <f:facet name="header">
                                                <h:outputText value=""  />
                                            </f:facet>
                                            <h:selectBooleanCheckbox value="#{data.checked}"  />
                                            
                                        </h:column>
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
                                                <h:outputText value="Tara"  />
                                            </f:facet>
                                            <h:inputText value="#{data.tara}" styleClass="inputText" size="5"  onkeyup="hallaCantidadIngresoFisico()"  />
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
                                            <h:inputText value="#{data.loteMaterialProveedor}"  styleClass="inputText" />
                                        </h:column>

                                        <%--h:column>
                                            <f:facet name="header">
                                                <h:outputText value="Fecha Reanalisis"  />
                                            </f:facet>
                                            <h:inputText value="#{data.fechaReanalisis}" styleClass="inputText" size="10"  onblur="valFecha(this);" onkeyup="hallaCantidadIngresoFisico()"  >
                                                <f:convertDateTime pattern="dd/MM/yyyy" />
                                            </h:inputText>
                                        </h:column--%>

                                        <h:column>
                                            <f:facet name="header">
                                                <h:outputText value="Estante"  />
                                            </f:facet>
                                            <h:outputText value="#{data.estanteAlmacen.nombreEstante}"  />
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
                                                <h:outputText value="Estante"  />
                                            </f:facet>
                                            <h:outputText value="#{data.estanteAlmacen.nombreEstante}"  />
                                        </h:column>
                                        <h:column>
                                            <f:facet name="header">
                                                <h:outputText value="Fila"   />
                                            </f:facet>
                                            <h:inputText value="#{data.fila}" styleClass="inputText" size="2" />
                                        </h:column>
                                        <h:column>
                                            <f:facet name="header">
                                                <h:outputText value="Columna"  />
                                            </f:facet>
                                            <h:inputText value="#{data.columna}"  styleClass="inputText" size="2" />
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
                                               oncomplete="if(#{ManagedIngresoAlmacen.mensaje!=''}){alert('#{ManagedIngresoAlmacen.mensaje}')}else{Richfaces.hideModalPanel('panelDetalleItem')}"
                                               action="#{ManagedIngresoAlmacen.guardarDetalleItem_action}"
                                               reRender="dataIngresoAlmacenDetalle"
                                               timeout="7200"
                                                />
                               <a4j:commandButton styleClass="btn" value="Cancelar"
                               oncomplete="javascript:Richfaces.hideModalPanel('panelDetalleItem')"
                               action="#{ManagedIngresoAlmacen.cancelarDetalleItem_action}"
                               reRender="dataIngresoAlmacenDetalle"
                               timeout="7200"
                                />
                            
                            </div>
                        
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

