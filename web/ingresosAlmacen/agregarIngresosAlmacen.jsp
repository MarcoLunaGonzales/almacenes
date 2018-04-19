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
            <link rel="STYLESHEET" type="text/css" href="../css/chosen.css" />
            <script type="text/javascript" src="../js/general.js" ></script>
            <script type="text/javascript" src="../js/chosen.js" ></script>
            <style type="text/css">
                .tablaGestiones{
                    border: 1px solid #aaa;
                    border-collapse:  collapse;
                    width: 100%;
                }
                .tablaGestiones thead tr th{
                    background-color: #aaa;
                    color: white;
                }
                .tablaGestiones tbody tr td{
                    padding: 5px;
                }
            </style>
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
                    var elements=document.getElementById('detalleItem:form3:dataIngresoAlmacenDetalleEstado');
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
                    document.getElementById('detalleItem:form3:cantTotalIngresoFisico').value=Math.round(cantidadIngresoFisico*100)/100;
       }
       function valida_fechas(){
         var startDate = Date(document.getElementById("detalleItem:form3:fechaManufacturaInputDate").value);
         //var pubDate = ${''}.getSelectedDate();
         }
         function seleccionarTodo(nametable){
             var check = document.getElementById("detalleItem:form3:seleccionar_todo");
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
             var check = document.getElementById("detalleItem:form3:seleccionar_todo");
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
                            <h:selectOneMenu  styleClass="inputText chosen" value="#{ManagedIngresoAlmacen.ingresosAlmacen.proveedores.codProveedor}" style="width:100%"  >
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
                    
                    <a4j:commandButton value="Agregar Item" styleClass="btn"
                                    onclick="Richfaces.showModalPanel('panelAgregarItem')" action="#{ManagedIngresoAlmacen.agregarItem_action}"
                                    reRender="contenidoAgregarItem"
                                    timeout="7200"
                                    />
                    <br/>
                    <rich:dataTable value="#{ManagedIngresoAlmacen.ingresosAlmacenDetalleList}" var="data"
                                    id="dataIngresoAlmacenDetalle"
                                    onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                    onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                    headerClass="headerClassACliente"
                                    rowKeyVar="index"
                                    style="width:80%;margin-top:5px;" binding="#{ManagedIngresoAlmacen.ingresosAlmacenDetalleDataTable}" >
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Eliminar"  />
                            </f:facet>
                            <a4j:commandButton value="Eliminar" action="#{ManagedIngresoAlmacen.eliminarIngresoDetalleItemAction}"
                                               reRender="dataIngresoAlmacenDetalle" styleClass="btn">
                                <f:setPropertyActionListener value="#{index}" target="#{ManagedIngresoAlmacen.indexEliminarDetalleIngreso}"/>
                            </a4j:commandButton>
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
                            <h:outputText value="#{data.unidadesMedida.abreviatura}"  />
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

                    
                    <br>

                <h:panelGroup rendered="#{ManagedIngresoAlmacen.administradorAlmacen}">
                    <a4j:commandButton value="Guardar" styleClass="btn"                    
                    action="#{ManagedIngresoAlmacen.guardarIngresoAlmacen_action}"
                    oncomplete="mostrarMensajeTransaccionEvento(function(){redireccionar('navegadorIngresosAlmacen.jsf');})"/>
                    <a4j:commandButton value="Cancelar" styleClass="btn"
                                       oncomplete="window.location.href ='navegadorIngresosAlmacen.jsf?data='+(new Date()).getTime().toString()"/>
                </h:panelGroup>   
            </a4j:form>

             
            
            <a4j:include viewId="panelDetallarItem.jsp" id="detalleItem"/>
            <a4j:include viewId="/panelProgreso.jsp" />
            <a4j:include viewId="/message.jsp" />
            <a4j:include viewId="panelAgregarItem.jsp" />
            
        </body>
    </html>

</f:view>

