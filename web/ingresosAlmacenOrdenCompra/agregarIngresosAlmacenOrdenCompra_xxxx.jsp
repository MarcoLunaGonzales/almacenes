ingreso oc
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
            <style>
                .b
                {

                    background-color:#9ACD32;
                }
            </style>
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
       function validaFechas(input){
           var fechaManufactura = new Date(document.getElementById('form2:fechaManufacturaInputDate').value.toString());
           var fechaVencimiento = new Date(document.getElementById('form2:fechaVencimientoInputDate').value.toString());
           //alert(document.getElementById('form2:fechaManufacturaInputDate').value.toString());
           //alert(document.getElementById('form2:fechaVencimientoInputDate').value.toString());
           if(fechaManufactura>=fechaVencimiento){alert("La fecha de vencimiento debe ser mayor a la fecha de manufactura"); input.focus();}
       }
       //inicio ale orden de compra
       function validarRegistroDetalle(){
           var cantIngresoDocumento = document.getElementById("form2:cantEquivalente").value;
           var cantIngresoFisico = document.getElementById("form2:cantTotalIngresoFisico").value;

           if(parseFloat(cantIngresoDocumento)!=parseFloat(cantIngresoFisico)){
               if(confirm("la cantidad de ingreso difiere de la Cantidad Fisica ¿esta seguro de guardar este detalle?")==true){
                   return true;
               }
           }
           else
           {
                return true;
           }
           return false;
       }
       function seleccionarTodo(nametable){
             var check = document.getElementById("form2:seleccionar_todo");
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
       //final ale orden de compra
       onerror=function (){
        alert('existe un error al momento de cargar el registro, por favor recargue la pagina');
        }

        //final ale
            A4J.AJAX.onError = function(req,status,message){
            window.alert("Ocurrio un error: "+message);
            }
            A4J.AJAX.onExpired = function(loc,expiredMsg){
            if(window.confirm("Ocurrio un error al momento realizar la transaccion: "+expiredMsg+" location: "+loc)){
            return loc;
            } else {
            return false;
            }
            }
            var bPreguntar = true;
            window.onbeforeunload = preguntarAntesDeSalir;
            function preguntarAntesDeSalir()
            {
            if (bPreguntar)
            return "¿Seguro que quieres salir? Podría perder todos los cambios si no los GUARDO";
            }
            function hallaCantidadX(obj){
           //var cantidadDisponible=parseFloat(obj.parentNode.parentNode.getElementsByTagName('td')[9].getElementsByTagName('input')[0].value);
           //alert (obj.parentNode.parentNode.getElementsByTagName('td')[9].innerHTML);
           //alert(obj.value.substr(0,obj.value.length-1));
            var cantidadParcialRepetir = document.getElementById("form2:cantidadParcial").value;
            
            obj.parentNode.parentNode.getElementsByTagName('td')[4].getElementsByTagName("input")[0].value=cantidadParcialRepetir*obj.value;

            return true;
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
                    </f:facet>de
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

                            <h:outputText value="Proveedor:" styleClass="outputText2"  />
                            <h:outputText value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacen.ordenesCompra.proveedores.nombreProveedor}" styleClass="outputText1" />

                            <h:outputText value="Origen de Proveedor:" styleClass="outputText2" />
                            <h:outputText value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacen.ordenesCompra.proveedores.paises.nombrePais}" styleClass="outputText1" />

                            <h:outputText value="representante:" styleClass="outputText2" />
                            <h:outputText value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacen.ordenesCompra.representantes.nombreRepresentante}" styleClass="outputText1" />


                            <h:outputText value="Estado Ingreso:" styleClass="outputText2" />
                            <h:selectOneMenu disabled="true" styleClass="inputText" value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacen.estadosIngresoAlmacen.codEstadoIngresoAlmacen}" >
                                    <f:selectItems value="#{ManagedIngresoAlmacenOrdenCompra.estadosIngresoAlmacenList}"  />
                            </h:selectOneMenu>
                            <h:outputText value="Observacion:" styleClass="outputText2"  />
                            <h:inputTextarea value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacen.obsIngresoAlmacen}" styleClass="inputText2" cols="25" />
                            <h:outputText value="Nro Documento:" styleClass="outputText2"  />
                            <h:inputText value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacen.nroDocumento}" styleClass="inputText2" />
                            </h:panelGrid>
                    </rich:panel>
                    
                    
                    <rich:dataTable value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalleList}" var="data"
                                    id="dataIngresoAlmacenDetalle"
                                    onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                    onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                    headerClass="headerClassACliente" binding="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalleDataTable}"
                                    style="width:80%">
                        <%--rich:column>
                            <f:facet name="header">
                                <h:outputText value=""  />
                            </f:facet>
                            <h:selectBooleanCheckbox  value="#{data.checked}"/>
                        </rich:column--%>
                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value="Item"  />
                            </f:facet>
                            
                            <h:outputText  value="#{data.materiales.nombreMaterial}"/>
                            
                        </rich:column>
                       
                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value="Cant. Segun Documento"  />
                            </f:facet>
                            <h:outputText value="#{data.cantTotalIngreso}"  />
                        </rich:column>
                      
                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value="Unid."   />
                            </f:facet>
                            <h:outputText value="#{data.ordenesCompraDetalle.unidadesMedida.nombreUnidadMedida}"  />
                        </rich:column>
                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value="Cantidad Fisica"  />
                            </f:facet>
                            <h:outputText value="#{data.cantTotalIngresoFisico}"  />
                        </rich:column>
                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value="Unidades Empaque"  />
                            </f:facet>
                            <h:outputText value="#{data.unidadesMedida.nombreUnidadMedida}"  />
                        </rich:column>
                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value="Observacion"  />
                            </f:facet>
                            <h:outputText value="#{data.secciones.nombreSeccion}"  />
                        </rich:column>
                        <%--h:column>
                            <f:facet name="header">
                                <h:outputText value="Equivalencia"  />
                            </f:facet>
                            <h:outputText value="#{data.nroUnidadesEmpaque}"  />
                        </h:column--%>
                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value="Observacion"  />
                            </f:facet>
                            <h:outputText value="0"  />
                        </rich:column>
                        <rich:column styleClass="#{data.colorFila}">
                            <f:facet name="header">
                                <h:outputText value=""  />
                            </f:facet>
                            <h:outputText value="" />
                            <a4j:commandLink action="#{ManagedIngresoAlmacenOrdenCompra.detallarItem_action}" value="detallar"
                             reRender="contenidoDetalleItem,contenidoDetalleItem" timeout="30000"
                            oncomplete="onclick=Richfaces.showModalPanel('panelDetalleItem')"  ><%--  --%>
                            </a4j:commandLink>
                        </rich:column>
                    </rich:dataTable>

                    <%-- <ws:datascroller fordatatable="dataAreasEmpresa"  />--%>
                    <br>
                    <a4j:commandButton value="Guardar" styleClass="btn" onclick="if(confirm('esta seguro de registrar el ingreso?')==false){return false;}"
                    action="#{ManagedIngresoAlmacenOrdenCompra.guardarIngresoAlmacen_action}"
                    oncomplete="if(#{ManagedIngresoAlmacenOrdenCompra.mensaje!=''}){alert('#{ManagedIngresoAlmacenOrdenCompra.mensaje}')}else{ window.onbeforeunload = null;location='navegadorIngresosAlmacenOrdenCompra.jsf'}"
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

             <rich:modalPanel id="panelDetalleItem" minHeight="400"  minWidth="1100"
                                     height="400" width="1100"
                                     zindex="200"
                                     headerClass="headerClassACliente"
                                     resizeable="false" style="overflow :auto" >
                        <f:facet name="header">
                            <h:outputText value="Detalle Item"/>
                        </f:facet>
                        <a4j:form id="form2" timeout="30000" >
                        <h:panelGroup id="contenidoDetalleItem">
                            <div align="center">
                           <h:outputText value="Item:" styleClass="tituloCampo1" />
                           <h:outputText value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalle.materiales.nombreMaterial}" styleClass="tituloCampo1" />
                           <br/>
                           <br/>
                           <h:panelGrid columns="9" id="contenidoIngresoAlmacenDetalle" headerClass="headerClassACliente" width="100%" style="border:1px solid #000000;" >
                                <f:facet name="header">
                                      <h:outputText value="Etiquetas" />
                                </f:facet>
                                
                                <h:outputText value="Cantidad Segun Documento:" styleClass="outputText1" />
                                <h:panelGroup>                                     
                                    <h:inputText value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalle.cantTotalIngreso}" styleClass="inputText" size="5" id="cantTotalIngreso" onkeypress="valNum(event);" readonly="true" onkeyup="hallaValorEquivalente(this)" />
                                    <h:outputText value="[" styleClass="outputText1" />
                                    <h:outputText value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalle.ordenesCompraDetalle.unidadesMedida.abreviatura}" styleClass="outputText1" />
                                    <h:outputText value="]" styleClass="outputText1" />
                                </h:panelGroup>

                                <h:outputText value="Equivalente:" styleClass="outputText1" />
                                <h:panelGroup>
                                    <h:inputText value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalle.cantEquivalente}" styleClass="inputText" size="5" readonly="true" onkeypress="valNum(event);" id="cantEquivalente" />
                                    <h:outputText value="[" styleClass="outputText1" />
                                    <h:outputText value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalle.unidadesMedida.abreviatura}" styleClass="outputText1" />
                                    <h:outputText value="]" styleClass="outputText1" />
                                    <h:inputHidden value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalle.valorEquivalencia}" id="valorEquivalencia" />
                                </h:panelGroup>

                                <h:outputText value="Cant. Ingreso Fisico:" styleClass="outputText1" />
                                <h:panelGroup>
                                    <h:inputText value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalle.cantTotalIngresoFisico}" id="cantTotalIngresoFisico"  styleClass="inputText" size="5" onkeypress="valNum(event);" readonly="true" />
                                    <h:outputText value="[" styleClass="outputText1" />
                                    <h:outputText value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalle.unidadesMedida.abreviatura}" styleClass="outputText1" />
                                    <h:outputText value="]" styleClass="outputText1" />
                                </h:panelGroup>
                                

                                <h:outputText value="Nro de Empaques:" styleClass="outputText1" />
                                
                                <h:inputText value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalle.nroUnidadesEmpaque}" styleClass="inputText" onkeypress="valNum(event);" size="5"  />
                                <a4j:commandButton value="Generar Etiquetas" action="#{ManagedIngresoAlmacenOrdenCompra.generarEtiquetas_action}" styleClass="btn"  reRender="dataIngresoAlmacenDetalleEstado"  />
                                
                              </h:panelGrid>

                              <br/>
                               
                                
                             
                              <br/>

                              
                              <h:panelGrid columns="6" headerClass="headerClassACliente" width="100%" style="border:1px solid #000000;">
                                  <f:facet name="header">
                                      <h:outputText value="Repetir Valores" />
                                  </f:facet>


                                <h:outputText value="Cantidad Parcial:" styleClass="outputText1"  />
                                <h:inputText value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalleEstado.cantidadParcial}" styleClass="inputText" size="10" onkeypress="valNum(event);" id="cantidadParcial" >
                                    
                                </h:inputText>

                                <h:outputText value="Lote Proveedor:" styleClass="outputText1" />
                                <h:inputText value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalleEstado.loteMaterialProveedor}" styleClass="inputText" size="10"  > <%-- onkeypress="valNum(event);" --%>
                                    
                                </h:inputText>

                                <h:outputText value="Unidad de Empaque:" styleClass="outputText1" />
                                <h:selectOneMenu value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalleEstado.empaqueSecundarioExterno.codEmpaqueSecundarioExterno}" styleClass="inputText" >
                                    <f:selectItems value="#{ManagedIngresoAlmacenOrdenCompra.empaqueSecundarioExternoList}"  />
                                </h:selectOneMenu>

                                <%--h:outputText value="Fecha Manufactura:" styleClass="outputText1" />
                                <rich:calendar   datePattern="dd/MM/yyyy" styleClass="inputText"
                                    value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalleEstado.fechaManufactura}"  enableManualInput="true" oninputblur="valFecha(this);" width="1" />
                                --%>
                                
                                     <%--h:outputText value="Fecha de Vencimiento:" styleClass="outputText1" />
                                     <rich:calendar   datePattern="dd/MM/yyyy" styleClass="inputText"
                                    value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalleEstado.fechaVencimiento}"  enableManualInput="true" oninputblur="valFecha(this);" width="1" /--%>
                                        <%---h:outputText value="Fecha Manufactura:" styleClass="outputText1" />
                                        <rich:calendar datePattern="dd/MM/yyyy" styleClass="inputText" immediate="true"
                                        value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalleEstado.fechaManufactura}" ondateselect="document.getElementById('form2:fechaManufacturaInputDate').focus(); return true;" id="fechaManufactura" enableManualInput="true"  width="1" oninputblur="valFecha(this);">
                                                <%--a4j:support event="onchanged" /--%>                                                
                                                <%--a4j:support event="oninputblur" ajaxSingle="true" action="#{ManagedIngresoAlmacenOrdenCompra.validaFechas_action}" reRender="contenidoDetalleItem"
                                                oncomplete="if(#{ManagedIngresoAlmacenOrdenCompra.mensaje!=''}){alert('#{ManagedIngresoAlmacenOrdenCompra.mensaje}');document.getElementById('form2:fechaManufacturaInputDate').focus()}else{valFecha(document.getElementById('form2:fechaManufacturaInputDate'));}" /--%><%--oninputblur="validaFechas(this)"--%>
                                        <%--/rich:calendar--%>

                                        <h:outputText value="Fecha de Reanalisis:" styleClass="outputText1" />
                                        <rich:calendar datePattern="dd/MM/yyyy" styleClass="inputText"
                                         value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalleEstado.fechaReanalisis}"  enableManualInput="true" oninputblur="valFecha(this);" width="1" />


                                        <h:outputText value="Fecha de Vencimiento:" styleClass="outputText1" />
                                        <rich:calendar   datePattern="dd/MM/yyyy" styleClass="inputText" immediate="true"
                                        value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalleEstado.fechaVencimiento}"  ondateselect="document.getElementById('form2:fechaVencimientoInputDate').focus(); return true;" id="fechaVencimiento" enableManualInput="true"  width="1" oninputblur="valFecha(this);">
                                            <%--a4j:support event="oninputblur" ajaxSingle="true" action="#{ManagedIngresoAlmacenOrdenCompra.validaFechas_action1}"
                                                oncomplete="if(#{ManagedIngresoAlmacenOrdenCompra.mensaje!=''}){alert('#{ManagedIngresoAlmacenOrdenCompra.mensaje}');document.getElementById('form2:fechaVencimientoInputDate').focus()}else{valFecha(document.getElementById('form2:fechaVencimientoInputDate'));}" /--%>
                                         </rich:calendar>
                                         <h:outputText value="Tara:" styleClass="outputText1" />
                                         <h:inputText value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalleEstado.tara}" styleClass="inputText" size="10" onkeypress="valNum(event)" > <%-- onkeypress="valNum(event);" --%>
                                            </h:inputText>
                                            <h:outputText value="Ambiente:" styleClass="outputText1" />
                                        <h:selectOneMenu value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalleEstado.estanteAlmacen.ambienteAlmacen.codAmbiente}" styleClass="inputText" >
                                            <f:selectItem itemValue="0" itemLabel="-NINGUNO-" />
                                            <f:selectItems value="#{ManagedIngresoAlmacenOrdenCompra.ambienteAlmacenList}"  />
                                            <a4j:support event="onchange" reRender="estante" action="#{ManagedIngresoAlmacenOrdenCompra.ambienteAlmacen_change}" />
                                        </h:selectOneMenu>
                                        <h:outputText value="Pasillo:" styleClass="outputText1" />
                                        <h:selectOneMenu value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalleEstado.estanteAlmacen.codEstante}" styleClass="inputText" id="estante" >
                                            <f:selectItem itemValue="0" itemLabel="-NINGUNO-" />
                                            <f:selectItems value="#{ManagedIngresoAlmacenOrdenCompra.estanteAlmacenList}"  />
                                        </h:selectOneMenu>
                                        <h:outputText value="" />
                                        <h:outputText value="" />

                                        <h:outputText value="Estante:" styleClass="outputText1" />
                                        <h:inputText value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalleEstado.fila}" styleClass="inputText" size="10"  ><!--onkeypress="valNum(event);"-->
                                        </h:inputText>

                                        <h:outputText value="Ubicacion:" styleClass="outputText1" />
                                        <h:inputText value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalleEstado.columna}" styleClass="inputText" size="10"  ><!--onkeypress="valNum(event);"-->
                                        </h:inputText>
                                        <h:panelGroup>
                                        <h:selectBooleanCheckbox value="#{ManagedIngresoAlmacenOrdenCompra.conDensidad}"  />
                                        <h:outputText value="Densidad:" styleClass="outputText1" />
                                        <h:inputText value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalleEstado.densidad}" styleClass="inputText" size="10"  ><!--onkeypress="valNum(event);"-->
                                        </h:inputText>
                                        </h:panelGroup>

                            </h:panelGrid>
                            <br/>
                            <h:selectBooleanCheckbox id="seleccionar_todo"  onclick="seleccionarTodo('form2:dataIngresoAlmacenDetalleEstado');"/>


                            <a4j:commandButton action="#{ManagedIngresoAlmacenOrdenCompra.repetirValoresIngresoAlmacenDetalleEstado_action}" oncomplete="if(#{ManagedIngresoAlmacenOrdenCompra.mensaje!=''}){alert('#{ManagedIngresoAlmacenOrdenCompra.mensaje}')}" reRender="dataIngresoAlmacenDetalleEstado,contenidoIngresoAlmacenDetalle" value="Repetir Valores" styleClass="btn" />
                            <br/>
                            <br/>
                            <br/>

                            <rich:dataTable value="#{ManagedIngresoAlmacenOrdenCompra.ingresosAlmacenDetalle.ingresosAlmacenDetalleEstadoList}" var="data"
                                    id="dataIngresoAlmacenDetalleEstado"
                                    onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                    onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                    headerClass="headerClassACliente">
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
                                                <h:outputText value="Densidad"  />
                                            </f:facet>
                                            <h:inputText value="#{data.densidad}" styleClass="inputText" size="5" onkeyup="hallaCantidadX(this)" />
                                </h:column>
                                <h:column>
                                    <f:facet name="header">
                                        <h:outputText value="Tara"  />
                                    </f:facet>
                                    <h:inputText value="#{data.tara}" styleClass="inputText" size="5" />
                                </h:column>
                                <h:column>
                                    <f:facet name="header">
                                        <h:outputText value="Fecha Manufactura"  />
                                    </f:facet>
                                    <h:inputText value="#{data.fechaManufactura}" styleClass="inputText" size="10"  onblur="valFecha(this);"   ><%--  onkeyup="hallaCantidadIngresoFisico()"--%>
                                        <f:convertDateTime pattern="dd/MM/yyyy" />    
                                    </h:inputText>
                                </h:column>
                                <h:column>
                                    <f:facet name="header">
                                        <h:outputText value="Lote Proveedor"  />
                                    </f:facet>
                                    <h:inputText value="#{data.loteMaterialProveedor}" styleClass="inputText"   />
                                </h:column>
                                <%--h:column>
                                            <f:facet name="header">
                                                <h:outputText value="Estante"  />
                                            </f:facet>
                                            <h:outputText value="#{data.estanteAlmacen.nombreEstante}"  />
                                </h:column--%>

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
                                        <h:outputText value="Fecha Vencimiento"  />
                                    </f:facet>
                                    <h:inputText value="#{data.fechaVencimiento}" styleClass="inputText" size="10"  onblur="valFecha(this);"   ><%--  onkeyup="hallaCantidadIngresoFisico()"--%>
                                        <f:convertDateTime pattern="dd/MM/yyyy" />
                                    </h:inputText>
                                </h:column>
                                <h:column>
                                    <f:facet name="header">
                                        <h:outputText value="Pasillo"  />
                                    </f:facet>
                                    <h:outputText value="#{data.estanteAlmacen.nombreEstante}"  />
                                </h:column>
                                <h:column>
                                    <f:facet name="header">
                                        <h:outputText value="Estante"   />
                                    </f:facet>
                                    <h:inputText value="#{data.fila}" styleClass="inputText" size="2" />
                                </h:column>
                                <h:column>
                                    <f:facet name="header">
                                        <h:outputText value="Ubicacion"  />
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
                                                   oncomplete="if(#{ManagedIngresoAlmacenOrdenCompra.mensaje!=''}){alert('#{ManagedIngresoAlmacenOrdenCompra.mensaje}')}else{Richfaces.hideModalPanel('panelDetalleItem')}"
                                                   action="#{ManagedIngresoAlmacenOrdenCompra.guardarDetalleItem_action}"
                                                   reRender="dataIngresoAlmacenDetalle"
                                                   onclick="if(validarRegistroDetalle()==false){return false;}"
                                                    />
                               <a4j:commandButton styleClass="btn" value="Cancelar"
                               oncomplete="javascript:Richfaces.hideModalPanel('panelDetalleItem')"
                               action="#{ManagedIngresoAlmacenOrdenCompra.cancelarDetalleItem_action}"
                               reRender="dataIngresoAlmacenDetalle"
                                />
                                </div>
                        
                        </a4j:form>
            </rich:modalPanel>

            


        </body>
    </html>

</f:view>

