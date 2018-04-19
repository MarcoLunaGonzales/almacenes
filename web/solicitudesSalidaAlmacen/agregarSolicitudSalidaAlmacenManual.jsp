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
                    var elements=document.getElementById('form2:dataSalidaAlmacenDetalleIngreso');
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
       function validaCantidad(obj){
           //var cantidadDisponible=parseFloat(obj.parentNode.parentNode.getElementsByTagName('td')[9].getElementsByTagName('input')[0].value);
           //alert (obj.parentNode.parentNode.getElementsByTagName('td')[9].innerHTML);
           //alert(obj.value.substr(0,obj.value.length-1));
           var cantidadDisponible =  obj.parentNode.parentNode.getElementsByTagName('td')[9].innerHTML;
           if(obj.value>parseFloat(cantidadDisponible)){
              alert("la cantidad debe ser menor a la cantidad disponible");
              obj.focus();
              //obj.value = obj.value.substr(0,obj.value.length-1);
              obj.value = cantidadDisponible;
              return false;
           }
           
           if(this.hallaCantidadSalidaFisica()==false){
               alert(" el total no debe sobrepasar la cantidad total de salida ");
               obj.focus();
               var col10 = obj.parentNode.parentNode.getElementsByTagName('td')[10];
               obj.value = col10.getElementsByTagName("input")[1].value;
               //alert(col10.getElementsByTagName("input")[1].value);
               return false;
           }
           
           return true;
       }
       
       function hallaCantidadSalidaFisica(){
                    var elements=document.getElementById('form2:dataSalidaAlmacenDetalleIngreso');                    
                    var rowsElement=elements.rows;
                    var cantidadSalidaFisica = 0;
                    var totalImporteF=0;
                    for(var i=1;i<rowsElement.length;i++){
                        var cellsElement=rowsElement[i].cells;
                        var cel10=cellsElement[10];
                        var cantidadParcial=cel10.getElementsByTagName('input')[0];
                        if(cantidadParcial.type=='text'){
                            cantidadSalidaFisica=parseFloat(cantidadSalidaFisica)+parseFloat(cantidadParcial.value);
                        }
                    }

                    //alert(document.getElementById('form2:cantTotalIngresoFisico'));
                    //alert(cantidadIngresoFisico);
                    if(parseFloat(document.getElementById('form2:cantTotalSalidaFisica').innerHTML)<(Math.round(cantidadSalidaFisica*100)/100))
                        {
                            return false;
                        }

                      return true;
       }


       function calculaEquivalencia(obj){
           valorEquivalencia = document.getElementById("form5:valorEquivalencia").value;
           if(valorEquivalencia>0){
               document.getElementById("form5:cantidadEquivalencia").value = obj.value /valorEquivalencia;
           }
       }
       function calculaEquivalencia1(obj){
           valorEquivalencia = document.getElementById("form4:valorEquivalencia").value;
           if(valorEquivalencia>0){
               document.getElementById("form4:cantidadEquivalencia").value = obj.value /valorEquivalencia;
           }
       }
       function validaCantidadSalidaAlmacenDetalle(obj){
           //cantidadDisponible
           //alert("entro al valida cantidad");
           //alert(document.getElementById("form5:cantidadSalidaAlmacen").value);
           //alert(document.getElementById("form5:cantidadDisponible").innerHTML);
           var cantidadSalidaAlmacen = document.getElementById("form5:cantidadSalidaAlmacen").value;
           var cantidadDisponible = parseFloat(document.getElementById("form5:cantidadDisponible").innerHTML);

           //alert (cantidadSalidaAlmacen);
           //alert (cantidadDisponible);
           
           if(cantidadSalidaAlmacen>cantidadDisponible){
               document.getElementById("form5:cantidadSalidaAlmacen").value = document.getElementById("form5:cantidadDisponible").innerHTML;
               calculaEquivalencia(obj);
               alert("la cantidad de salida de almacen no puede sobrepasar la cantidad disponible");               
               return false;
            }
           calculaEquivalencia(obj);
           return true;
       }



       function validaCantidadSalidaAlmacenDetalle1(obj){
           //cantidadDisponible
           //alert("entro al valida cantidad");
           //alert(document.getElementById("form5:cantidadSalidaAlmacen").value);
           //alert(document.getElementById("form5:cantidadDisponible").innerHTML);
           var cantidadSalidaAlmacen = document.getElementById("form4:cantidadSalidaAlmacen").value;
           var cantidadDisponible = parseFloat(document.getElementById("form4:cantidadDisponible").innerHTML);
           
           //alert (cantidadSalidaAlmacen);
           //alert (cantidadDisponible);

           if(cantidadSalidaAlmacen>cantidadDisponible){
               document.getElementById("form4:cantidadSalidaAlmacen").value = document.getElementById("form4:cantidadDisponible").innerHTML;
               calculaEquivalencia1(obj);
               alert("la cantidad de salida de almacen no puede sobrepasar la cantidad disponible");
               return false;
            }
           calculaEquivalencia1(obj);
           return true;
       }
       function cantAuxSalida(){           
           var elements=document.getElementById('form2:dataSalidaAlmacenDetalleIngreso');
                    var rowsElement=elements.rows;
                    var cantidadSalidaFisica = 0;
                    var totalImporteF=0;
                    for(var i=1;i<rowsElement.length;i++){
                        var cellsElement=rowsElement[i].cells;
                        var cel10=cellsElement[10];
                        cel10.getElementsByTagName('input')[1].value = cel10.getElementsByTagName('input')[0].value ;
                    }

       }
       function calculaEquivalencia(){
       }
       function validadRegistroSolicitud()
       {
           var selectAreaEmpresa = document.getElementById("form1:codAreaEmpresa");
           if(parseInt(selectAreaEmpresa.value) <= 0)
           {
               selectAreaEmpresa.focus();
               alert('De seleccionar el area/departamento destino de la solicitud');
               return false;
           }
           var tablaSolicitud = document.getElementById("form1:dataSalidasAlmacenDetalleManual").getElementsByTagName("tbody")[0];
           for( var i = 0; i< tablaSolicitud.rows.length;i++)
           {
               var inputCantidadSolicitud = tablaSolicitud.rows[i].cells[2].getElementsByTagName("input")[0];
               if(isNaN(inputCantidadSolicitud.value))
               {
                   alert('Debe registrar un dato numerico en la cantidad');
                   inputCantidadSolicitud.focus();
                   return false;
               }
               if(parseFloat(inputCantidadSolicitud.value) <= 0)
               {
                   alert('Debe registrar una cantidad mayor a 0');
                   inputCantidadSolicitud.focus();
                   return false;
               }
           }
           return confirm('Esta seguro de realizar la Solicitud de Salida de Almacen?');
       }
       function validarCantidadSolicitudAgregar()
       {
           var inputCantidadSolicitudAgregar = document.getElementById("form5:cantidadSalidaAlmacen");
           if(isNaN(inputCantidadSolicitudAgregar.value))
           {
               alert('Debe registrar un valor numerico');
               inputCantidadSolicitudAgregar.focus();
               return false;
           }
           if(parseFloat(inputCantidadSolicitudAgregar.value) <= 0)
           {
               alert('Debe registrar una cantidad mayor a 0');
               inputCantidadSolicitudAgregar.focus();
               return false;
           }
           return true;
       }
       function verificarSeleccionAreaDestino(){
          return (parseInt( document.getElementById("form1:codAreaEmpresa").value ) > 0);
       }
   </script>
       
      

        </head>
        <body >
            <a4j:form id="form1">
                <div align="center">

                    <h:outputText value="#{ManagedRegistroSolicitudSalidaAlmacen.cargarContenidoRegistroSolicitudSalidaAlmacen}"/>
                    <br><br>
                        
                    <rich:panel id="panelEstadoCuentas" styleClass="panelBuscar" style="top:50px;left:50px;width:700px;">
                    <f:facet name="header">
                        <h:outputText value="<div   onmouseover=\"this.style.cursor='move'\" onmousedown=\"comienzoMovimiento(event, 'form1:panelEstadoCuentas');\"  >Buscar<div   style=\"margin-left:550px;\"   onclick=\"document.getElementById('form1:panelEstadoCuentas').style.visibility='hidden';document.getElementById('panelsuper').style.visibility='hidden';\" onmouseover=\"this.style.cursor='hand'\"   >Cerrar</div> </div> "
                              escape="false" />
                    </f:facet>
                    </rich:panel>

                <rich:panel style="width:100%"  headerClass="headerClassACliente">
                            <f:facet name="header">
                                <h:outputText value="Registro de Solicitud Salidas a Almacen"  />
                            </f:facet>
                    <h:panelGrid columns="4" id="datosSolicitudSalida">

                            <h:outputText value="Nro Solicitud:" styleClass="outputText2" />
                            <h:outputText value="#{ManagedRegistroSolicitudSalidaAlmacen.solicitudesSalida.codFormSalida}" styleClass="outputText2" />
                            <h:outputText value="Area / Departamento:" styleClass="outputText2" />
                            <h:outputText value="#{ManagedRegistroSolicitudSalidaAlmacen.solicitudesSalida.areaDestinoSalida.nombreAreaEmpresa}" styleClass="outputText2" rendered="#{ManagedRegistroSolicitudSalidaAlmacen.solicitudesSalida.codLoteProduccion!=''}"/>
                            <h:selectOneMenu  styleClass="inputText chosen" 
                                              disabled="#{ManagedRegistroSolicitudSalidaAlmacen.solicitudesSalidaAlmacenDetalleList.size() > 0 }"
                                              value="#{ManagedRegistroSolicitudSalidaAlmacen.solicitudesSalida.areaDestinoSalida.codAreaEmpresa}" id="codAreaEmpresa"  
                                              rendered="#{ManagedRegistroSolicitudSalidaAlmacen.solicitudesSalida.codLoteProduccion eq ''}">
                                <f:selectItems value="#{ManagedRegistroSolicitudSalidaAlmacen.areasEmpresaList}"  />
                                <a4j:support action="#{ManagedRegistroSolicitudSalidaAlmacen.area_change}" reRender="maquinaria" event="onchange"  />
                            </h:selectOneMenu>
                            

                            <h:outputText value="Solicitante:" styleClass="outputText2" />
                            <h:panelGroup>
                            <h:outputText styleClass="outputText2" value="#{ManagedRegistroSolicitudSalidaAlmacen.solicitudesSalida.solicitante.nombrePersonal}" /> &nbsp;                            
                            <h:outputText styleClass="outputText2" value="#{ManagedRegistroSolicitudSalidaAlmacen.solicitudesSalida.solicitante.apPaternoPersonal}" /> &nbsp;
                            <h:outputText styleClass="outputText2" value="#{ManagedRegistroSolicitudSalidaAlmacen.solicitudesSalida.solicitante.apMaternoPersonal}" />
                            </h:panelGroup>

                            
                            
                            <h:outputText value="Nro Lote:" styleClass="outputText2"  
                                          rendered="#{ManagedRegistroSolicitudSalidaAlmacen.usuario.almacenesGlobal.codAlmacen ne '14' && ManagedRegistroSolicitudSalidaAlmacen.solicitudesSalida.tipoSolicitud.codTipoSolicitudSalida eq 1}"/>
                            <a4j:commandLink oncomplete="Richfaces.showModalPanel('seleccionarLoteProduccion');" reRender="contenidoSeleccionarLoteProduccion" 
                                             rendered="#{ManagedRegistroSolicitudSalidaAlmacen.usuario.almacenesGlobal.codAlmacen ne '14' && ManagedRegistroSolicitudSalidaAlmacen.solicitudesSalida.tipoSolicitud.codTipoSolicitudSalida eq 1}">
                                <h:outputText value="#{ManagedRegistroSolicitudSalidaAlmacen.solicitudesSalida.codLoteProduccion}" rendered="#{ManagedRegistroSolicitudSalidaAlmacen.solicitudesSalida.codLoteProduccion ne ''}"/>
                                <h:outputText value="Seleccionar lote de producción" rendered="#{ManagedRegistroSolicitudSalidaAlmacen.solicitudesSalida.codLoteProduccion eq ''}"/>
                                <h:graphicImage url="../img/actualizar2.png" alt="Seleccionar Lote de Producción"/>
                            </a4j:commandLink>
                            <h:outputText value="Producto:" styleClass="outputText2"
                                          rendered="#{ManagedRegistroSolicitudSalidaAlmacen.usuario.almacenesGlobal.codAlmacen != '14'&&ManagedRegistroSolicitudSalidaAlmacen.solicitudesSalida.codLoteProduccion!=''}" />
                            <h:outputText value="#{ManagedRegistroSolicitudSalidaAlmacen.solicitudesSalida.componentesProd.nombreProdSemiterminado}" styleClass="inputText"  rendered="#{ManagedRegistroSolicitudSalidaAlmacen.usuario.almacenesGlobal.codAlmacen != '14'&&ManagedRegistroSolicitudSalidaAlmacen.solicitudesSalida.codLoteProduccion!=''}" />
                            <h:outputText value="Presentacion:" styleClass="outputText2"
                                          rendered="#{ManagedRegistroSolicitudSalidaAlmacen.usuario.almacenesGlobal.codAlmacen != '14'&&ManagedRegistroSolicitudSalidaAlmacen.solicitudesSalida.codLoteProduccion!=''}" />
                            <h:selectOneMenu  styleClass="inputText" value="#{ManagedRegistroSolicitudSalidaAlmacen.solicitudesSalida.presentacionesProducto.codPresentacion}" id="codPresentacion"
                                              rendered="#{ManagedRegistroSolicitudSalidaAlmacen.usuario.almacenesGlobal.codAlmacen != '14'&&ManagedRegistroSolicitudSalidaAlmacen.solicitudesSalida.codLoteProduccion!=''}" >
                                <f:selectItems value="#{ManagedRegistroSolicitudSalidaAlmacen.presentacionesList}"  />
                            </h:selectOneMenu>

                            <h:outputText value="Maquinaria:" styleClass="outputText2"
                                          rendered="#{ManagedRegistroSolicitudSalidaAlmacen.solicitudesSalida.tipoSolicitud.codTipoSolicitudSalida eq 1}"/>
                            <h:selectOneMenu value="#{ManagedRegistroSolicitudSalidaAlmacen.solicitudesSalida.maquinaria.codMaquina}" styleClass="outputText2" id="maquinaria"   
                                             rendered="#{ManagedRegistroSolicitudSalidaAlmacen.solicitudesSalida.tipoSolicitud.codTipoSolicitudSalida eq 1}">
                                <f:selectItems value="#{ManagedRegistroSolicitudSalidaAlmacen.maquinariaList}" />
                                <f:selectItem  itemValue="0" itemLabel="-NINGUNO-" />
                            </h:selectOneMenu>
                            <h:outputText value="Almacén Destino Traspaso:" styleClass="outputText2"
                                          rendered="#{ManagedRegistroSolicitudSalidaAlmacen.solicitudesSalida.tipoSolicitud.codTipoSolicitudSalida eq 3}"/>
                            <h:selectOneMenu value="#{ManagedRegistroSolicitudSalidaAlmacen.solicitudesSalida.almacenesDestino.codAlmacen}" styleClass="chosen" id="codAlmacenDestino"   
                                             rendered="#{ManagedRegistroSolicitudSalidaAlmacen.solicitudesSalida.tipoSolicitud.codTipoSolicitudSalida eq 3}">
                                <f:selectItems value="#{ManagedRegistroSolicitudSalidaAlmacen.almacenDestinoTraspasoList}" />
                            </h:selectOneMenu>
                            <h:outputText value="Observaciones:" styleClass="outputText2" />
                            <h:inputTextarea  rows="5" cols="50" styleClass="outputText2" value="#{ManagedRegistroSolicitudSalidaAlmacen.solicitudesSalida.obsSolicitud}" />
                            <h:outputText value="" />
                            <h:outputText value="" />

                       
                             <a4j:commandButton value="Agregar Item" styleClass="btn"
                                                onclick="if(verificarSeleccionAreaDestino()){Richfaces.showModalPanel('panelAgregarItem')}else{alert('Debe seleccionar el Area/ Departamento destino');}" action="#{ManagedRegistroSolicitudSalidaAlmacen.agregarItem_action}"
                                                reRender="contenidoAgregarItem"
                              />

                        </h:panelGrid>
                    </rich:panel>
                    <div style="overflow:auto;height:200px;width:80%;text-align:center">
                        <rich:dataTable value="#{ManagedRegistroSolicitudSalidaAlmacen.solicitudesSalidaAlmacenDetalleList}"
                                    var="data"
                                    id="dataSalidasAlmacenDetalleManual"
                                    onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                    onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                    headerClass="headerClassACliente"
                                    style="width:100%;color:black !important"  >
                         <f:facet name="header">
                             <rich:columnGroup>
                                 <rich:column>
                                     <h:outputText value=""/>
                                 </rich:column>
                                 <rich:column>
                                     <h:outputText value="Item"/>
                                 </rich:column>
                                 <rich:column>
                                     <h:outputText value="Cantidad solicitada"/>
                                 </rich:column>
                                 <rich:column>
                                     <h:outputText value="Cantidad Entregada"/>
                                 </rich:column>
                                 <rich:column>
                                     <h:outputText value="Unidad de<br/>Medida" escape="false"/>
                                 </rich:column>
                                 <rich:column>
                                     <h:outputText value="Eliminar"/>
                                 </rich:column>
                             </rich:columnGroup>
                         </f:facet> 
                        <rich:column style="background-color: #{data.colorFila}" >
                            <h:outputText value=""  />
                        </rich:column>
                        <rich:column>
                            <h:outputText  value="#{data.materiales.nombreMaterial}"/>
                        </rich:column>
                         <rich:column style="text-align:right">
                            <h:inputText value="#{data.cantidad}" styleClass="inputText" onkeypress="valNum(e)" />
                        </rich:column>
                        <rich:column>
                            <h:outputText value="#{data.cantidadEntregada}"  />
                        </rich:column>
                        <rich:column>
                            <h:outputText value="#{data.unidadesMedida.abreviatura}"  />
                        </rich:column>
                         <rich:column>
                             <a4j:commandButton value="Eliminar" 
                                                action="#{ManagedRegistroSolicitudSalidaAlmacen.eliminarDetalle_action}"
                                                reRender="dataSalidasAlmacenDetalleManual,datosSolicitudSalida" styleClass="btn">
                                 <f:setPropertyActionListener value="#{true}" target="#{data.checked}"/>
                             </a4j:commandButton>
                         </rich:column>
                    </rich:dataTable>
                    </div>
                    <br>
                    <a4j:commandButton value="Guardar" styleClass="btn"
                                        action="#{ManagedRegistroSolicitudSalidaAlmacen.guardarSolicitudSalidaAlmacen_action}"
                                        onclick="if(!validadRegistroSolicitud()){return false;}"
                                        oncomplete="if(#{ManagedRegistroSolicitudSalidaAlmacen.mensaje eq '1'}){alert('Se registro satisfactoriamente la solicitud');window.location.href = 'navegadorSolicitudesSalidaAlmacen.jsf' } else { alert('#{ManagedRegistroSolicitudSalidaAlmacen.mensaje}')}"
                                        />
                    <a4j:commandButton value="Volver" styleClass="btn"
                                       oncomplete="window.location.href = 'navegadorSolicitudesSalidaAlmacen.jsf?data='+(new Date()).getTime().toString();"/>
                    
               
            </a4j:form>

             <rich:modalPanel id="panelAgregarItem" minHeight="480"  minWidth="850"
                                     height="480" width="850"
                                     zindex="200"
                                     headerClass="headerClassACliente"
                                     resizeable="false" style="overflow :auto" >
                                         
                        <f:facet name="header">
                            <h:outputText value="Detalle Item"/>                            
                        </f:facet>

                        <a4j:form id="form5">
                        <h:panelGroup id="contenidoAgregarItem">
                          <div align="center">
                           <h:panelGrid columns="4" id="contenidoIngresoAlmacenDetalle" headerClass="headerClassACliente" width="90%" style="border:1px solid #000000;" >
                                <f:facet name="header">
                                      <h:outputText value="Buscar Item" />
                                </f:facet>
                                <h:outputText value="Capitulo:" styleClass="tituloCampo1" />
                                <h:selectOneMenu value="#{ManagedRegistroSolicitudSalidaAlmacen.buscarMaterial.grupos.capitulos.codCapitulo}" styleClass="inputText" >
                                    <f:selectItems value="#{ManagedRegistroSolicitudSalidaAlmacen.capitulosList}"  />
                                    <a4j:support event="onchange" action="#{ManagedRegistroSolicitudSalidaAlmacen.capitulos_changeAlmacen}" reRender="codGrupo" />
                                </h:selectOneMenu>
                                <h:outputText value="Grupo:" styleClass="tituloCampo1" />
                                <h:selectOneMenu value="#{ManagedRegistroSolicitudSalidaAlmacen.buscarMaterial.grupos.codGrupo}" styleClass="inputText" id="codGrupo" >
                                    <f:selectItems value="#{ManagedRegistroSolicitudSalidaAlmacen.gruposList}"  />
                                </h:selectOneMenu>
                                
                                <h:outputText value="Item:" styleClass="tituloCampo1" />

                                <h:inputText value="#{ManagedRegistroSolicitudSalidaAlmacen.buscarMaterial.nombreMaterial}" styleClass="inputText" size="50"  />

                                <a4j:commandButton value="Buscar" action="#{ManagedRegistroSolicitudSalidaAlmacen.buscarMaterialAlmacen_action}" styleClass="btn"  reRender="dataBuscarMaterial"  />

                           </h:panelGrid>
                            <div style="overflow:auto;height:150px;width:100%;text-align:center">
                            <rich:dataTable value="#{ManagedRegistroSolicitudSalidaAlmacen.materialesBuscarList}" var="data"
                                            id="dataBuscarMaterial" style="width:100%"
                                            onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                            onRowMouseOver="this.style.backgroundColor='#DDE3E4';"                                    
                                            headerClass="headerClassACliente">


                                <rich:column style="background-color: #{data.colorFila}">
                                    <f:facet name="header">
                                        <h:outputText value="Material"  />
                                    </f:facet>
                                    <a4j:commandLink  action="#{ManagedRegistroSolicitudSalidaAlmacen.seleccionarMaterialAction}"
                                                      styleClass="#{data.colorFila}"  
                                                      reRender="nombreMaterialAgregar,detalleSalidaMaterial,dataBuscarMaterial" >
                                        <h:outputText  value="#{data.nombreMaterial}" styleClass="outputText2" />
                                        <f:setPropertyActionListener value="#{data}" target="#{ManagedRegistroSolicitudSalidaAlmacen.materialSeleccionado}"/>
                                    </a4j:commandLink>
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
                            </div>
                             <b>
                             <h:panelGroup id="nombreMaterialAgregar">
                             <h:outputText value="Nombre Material: " styleClass="outputText1" />
                             <h:outputText value="#{ManagedRegistroSolicitudSalidaAlmacen.salidasAlmacenDetalleAgregar.materiales.nombreMaterial}" styleClass="outputText1" />
                             </h:panelGroup>
                             </b>
                             <br/>


                             <rich:panel id="detalleSalidaMaterial" headerClass="headerClassACliente" style="width:100%" >
                                <f:facet name="header" >
                                    <h:outputText value="Detalle de Salida de Material" />
                                </f:facet>
                                <table cellpading="0" cellspacing="0" class="tablaExistencia">
                                    <tr>
                                        <td class="header">Estado Material</td>
                                        <a4j:repeat value="#{ManagedRegistroSolicitudSalidaAlmacen.estadosMaterialExistenciaList}"  var="estado">
                                            <h:outputText value="<td class='center'>#{estado.nombreEstadoMaterial}</td>" escape="false"/>
                                        </a4j:repeat>
                                    </tr>
                                    <tr>
                                        <td class="header">Existencia</td>
                                        <a4j:repeat value="#{ManagedRegistroSolicitudSalidaAlmacen.estadosMaterialExistenciaList}"  var="estado">
                                            <h:outputText value="<td class='right #{estado.estadoSalidaPermitido?'existenciaValida':''}'>" escape="false"/>
                                                <h:outputText value="#{estado.cantidadExistente}">
                                                    <f:convertNumber pattern="0.00" locale="en"/>
                                                </h:outputText>
                                            <h:outputText value="</td>" escape="false"/>
                                        </a4j:repeat>
                                    </tr>
                                </table>
                                <h:panelGrid columns="7"  style="width:100%" >
                                <h:outputText value="Cantidad Disponible: " styleClass="outputText1" />
                                <h:panelGroup>
                                    <h:outputText value="#{ManagedRegistroSolicitudSalidaAlmacen.salidasAlmacenDetalleAgregar.cantidadDisponible}"  id="cantidadDisponible" styleClass="outputText1" >
                                        
                                    </h:outputText>
                                    <h:outputText value="[" styleClass="outputText1" />
                                    <h:outputText value="#{ManagedRegistroSolicitudSalidaAlmacen.salidasAlmacenDetalleAgregar.unidadesMedida.abreviatura}" styleClass="outputText1" />
                                    <h:outputText value="]" styleClass="outputText1" />
                                </h:panelGroup>

                                <h:outputText value="Cantidad Solicitud Salida: " styleClass="outputText1" />
                                <h:panelGrid columns="2">
                                    <h:panelGroup>
                                        <h:inputText value="#{ManagedRegistroSolicitudSalidaAlmacen.salidasAlmacenDetalleAgregar.cantidadSalidaAlmacen}"
                                                     styleClass="inputText" id="cantidadSalidaAlmacen">
                                            <f:validator validatorId="validatorDoubleRange"/>
                                            <f:attribute name="minimum" value="0.01"/>
                                            <f:attribute name="maximum" value="#{ManagedRegistroSolicitudSalidaAlmacen.salidasAlmacenDetalleAgregar.cantidadDisponible}"/>
                                            <f:attribute name="disable" value="#{(empty param['form5:btnGuardar'])}"/>
                                        </h:inputText>
                                        <h:message for="cantidadSalidaAlmacen" styleClass="message"/>
                                    </h:panelGroup>
                                    <h:panelGroup>
                                        <h:outputText value="[" styleClass="outputText1" />
                                        <h:outputText value="#{ManagedRegistroSolicitudSalidaAlmacen.salidasAlmacenDetalleAgregar.equivalencias.unidadesMedida1.abreviatura}" styleClass="outputText1" />
                                        <h:outputText value="]" styleClass="outputText1" />
                                    </h:panelGroup>
                                </h:panelGrid>

                                <h:outputText value="Equivalencia: " styleClass="outputText1" />
                                <h:panelGroup>
                                    <h:inputHidden value="#{ManagedRegistroSolicitudSalidaAlmacen.salidasAlmacenDetalleAgregar.equivalencias.valorEquivalencia}" id="valorEquivalencia" />
                                    <h:inputText value="#{ManagedRegistroSolicitudSalidaAlmacen.salidasAlmacenDetalleAgregar.cantidadSalidaAlmacenEquivalente}" styleClass="inputText" id="cantidadEquivalencia" onkeypress="valNum(event)"/>
                                    <h:outputText value="[" styleClass="outputText1" />
                                    <h:outputText value="#{ManagedRegistroSolicitudSalidaAlmacen.salidasAlmacenDetalleAgregar.equivalencias.unidadesMedida2.abreviatura}" styleClass="outputText1" />
                                    <h:outputText value="]" styleClass="outputText1" />
                                </h:panelGroup>

                            </h:panelGrid>
                            </rich:panel>
                            
                            

                            </div>
                        </h:panelGroup>
                        <div align="center">
                            <a4j:commandButton styleClass="btn" value="Guardar"
                                               id="btnGuardar"
                                                action="#{ManagedRegistroSolicitudSalidaAlmacen.guardarAgregarSalidaAlmacenDetalle_action}"
                                                oncomplete="if('#{facesContext.maximumSeverity}'.length == 0){Richfaces.hideModalPanel('panelAgregarItem');}"
                                                reRender="dataSalidasAlmacenDetalleManual,datosSolicitudSalida,detalleSalidaMaterial"/>

                            <input type="button" value="Cancelar" class="btn" onclick="javascript:Richfaces.hideModalPanel('panelAgregarItem')" />
                        </div>

                        </a4j:form>
            </rich:modalPanel>
            
            <rich:modalPanel id="seleccionarLoteProduccion" minHeight="180"  minWidth="650"
                                     height="180" width="650"
                                     zindex="200"
                                     headerClass="headerClassACliente"
                                     resizeable="false" style="overflow :auto" >
                        <f:facet name="header">
                            <h:outputText value="Seleccionar Lote de Producción"/>
                        </f:facet>
                        <a4j:form id="formSeleccionarLote">
                        <h:panelGroup id="contenidoSeleccionarLoteProduccion">
                            <h:panelGrid columns="4">
                                <h:outputText value="Lote" styleClass="outputText2" style="font-weight:bold"/>
                                <h:outputText value="::" styleClass="outputText2" style="font-weight:bold"/>
                                <h:inputText value="#{ManagedRegistroSolicitudSalidaAlmacen.programaProduccionBuscar.codLoteProduccion}" styleClass="inputText"/>
                                <a4j:commandButton  value="BUSCAR" action="#{ManagedRegistroSolicitudSalidaAlmacen.buscarLoteProduccion_action}" styleClass="btn"
                                                    reRender="dataLoteProduccion"/>
                                

                            </h:panelGrid>
                            <rich:dataTable value="#{ManagedRegistroSolicitudSalidaAlmacen.programaProduccionList}"
                                            var="data" id="dataLoteProduccion"
                                            binding="#{ManagedRegistroSolicitudSalidaAlmacen.programaProduccionDataTable}"
                                            headerClass="headerClassACliente"
                                            >
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column>
                                            <h:outputText value="Lote"/>
                                        </rich:column>
                                        <rich:column>
                                            <h:outputText value="Producto"/>
                                        </rich:column>
                                        <rich:column>
                                            <h:outputText value="Area Empresa"/>
                                        </rich:column>
                                        <rich:column>
                                            <h:outputText value="Tamaño lote Producción"/>
                                        </rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column>
                                    <a4j:commandLink reRender="datosSolicitudSalida" action="#{ManagedRegistroSolicitudSalidaAlmacen.seleccionLoteProduccion_action}"
                                                     oncomplete="javascript:Richfaces.hideModalPanel('seleccionarLoteProduccion');">
                                        <h:outputText value="#{data.codLoteProduccion}"/>
                                    </a4j:commandLink>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="#{data.formulaMaestra.componentesProd.nombreProdSemiterminado}"/>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="#{data.formulaMaestra.componentesProd.areasEmpresa.nombreAreaEmpresa}"/>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="#{data.cantidadLote}"/>
                                </rich:column>
                            </rich:dataTable>

                                <div align="center">
                                <input type="button" value="Cancelar" onclick="javascript:Richfaces.hideModalPanel('seleccionarLoteProduccion')" class="btn" />
                                </div>
                        </h:panelGroup>
                        </a4j:form>
            </rich:modalPanel>
            <a4j:include viewId="/panelProgreso.jsp"/>

         
      

        </body>
    </html>

</f:view>

