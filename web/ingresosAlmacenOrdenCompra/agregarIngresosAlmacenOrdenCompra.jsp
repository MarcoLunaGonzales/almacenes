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
            
            <style type="text/css">
                .b{
                    background-color:#9ACD32;
                }
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
                    var elements=document.getElementById('modalDetalleItem:form2:dataIngresoAlmacenDetalleEstado');
                    var rowsElement=elements.rows;
                    var cantidadIngresoFisico = 0;
                    var totalImporteF=0;
                    for(var i=1;i<rowsElement.length;i++){
                        var cellsElement=rowsElement[i].cells;
                        var cel4=cellsElement[4];
                        var cantidadParcial=cel4.getElementsByTagName('input')[0];
                        if(cantidadParcial.type=='text'){
                            cantidadIngresoFisico=parseFloat(cantidadIngresoFisico)+parseFloat(cantidadParcial.value);
                        }
                    }
                    document.getElementById('modalDetalleItem:form2:cantTotalIngresoFisico').value=Math.round(cantidadIngresoFisico*100)/100;
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
           
           var cantIngresoDocumento = document.getElementById("modalDetalleItem:form2:cantEquivalente").value;
           var cantIngresoFisico = document.getElementById("modalDetalleItem:form2:cantTotalIngresoFisico").value;
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
             var check = document.getElementById("modalDetalleItem:form2:seleccionar_todo");
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
                            onclick="Richfaces.showModalPanel('panelDetalleItem')" reRender="contenidoDetalleItem,contenidoDetalleItem" timeout="30000" >
                            </a4j:commandLink>
                        </rich:column>
                    </rich:dataTable>

                    <br>
                    <a4j:commandButton value="Guardar" styleClass="btn" onclick="if(confirm('esta seguro de registrar el ingreso?')==false){return false;}"
                    action="#{ManagedIngresoAlmacenOrdenCompra.guardarIngresoAlmacen_action}"
                    oncomplete="mostrarMensajeTransaccionEvento(function(){window.onbeforeunload = null;redireccionar('navegadorIngresosAlmacenOrdenCompra.jsf');})"
                    />
                   
                    
                    <a4j:commandButton value="Cancelar" styleClass="btn" oncomplete="window.location.href='navegadorIngresosAlmacenOrdenCompra.jsf?data='+(new Date()).getTime().toString()"/>
                    
            </a4j:form>
            
            <a4j:include viewId="panelDetallarItemOrdenCompra.jsp" id="modalDetalleItem"/>
            <a4j:include viewId="/panelProgreso.jsp" />
            <a4j:include viewId="/message.jsp" />

        </body>
    </html>

</f:view>

