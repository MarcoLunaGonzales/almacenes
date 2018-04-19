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
       function validaCantidad(obj,cantidadSacar){
           //var cantidadDisponible=parseFloat(obj.parentNode.parentNode.getElementsByTagName('td')[9].getElementsByTagName('input')[0].value);
           //alert (obj.parentNode.parentNode.getElementsByTagName('td')[9].innerHTML);
           //alert(obj.value.substr(0,obj.value.length-1));
           var cantidadDisponible =  obj.parentNode.parentNode.cells[9].innerHTML;
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
               obj.value = parseFloat(cantidadSacar);
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
                        var cel10=cellsElement[13];
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
           /*var elements=document.getElementById('form2:dataSalidaAlmacenDetalleIngreso');
                    var rowsElement=elements.rows;
                    var cantidadSalidaFisica = 0;
                    var totalImporteF=0;
                    for(var i=1;i<rowsElement.length;i++){
                        var cellsElement=rowsElement[i].cells;
                        var cel10=cellsElement[13];
                        cel10.getElementsByTagName('input')[1].value = cel10.getElementsByTagName('input')[0].value ;
                    }*/

       }
       function load1(){
           var a = document.getElementById("form1:producto");
           a.disabled = true;
       }
       function bloquearGuardar()
       {
           document.getElementById("progress").style.display='';
           document.getElementById("buttonGuardar").style.display='none';
       }
       function desBloquearGuardar()
       {
           document.getElementById("progress").style.display='none';
           document.getElementById("buttonGuardar").style.display='';
       }
       </script>

        </head>
        <body onload="load1()">
            <a4j:form id="form1">
                <div align="center">

                    <h:outputText value="#{ManagedSolicitudSalidaAlmacen.cargarContenidoRegistroSalidaAlmacen}"/>
                    <br><br>
                        
                    <rich:panel id="panelEstadoCuentas" styleClass="panelBuscar" style="top:50px;left:50px;width:700px;">
                    <f:facet name="header">
                        <h:outputText value="<div   onmouseover=\"this.style.cursor='move'\" onmousedown=\"comienzoMovimiento(event, 'form1:panelEstadoCuentas');\"  >Buscar<div   style=\"margin-left:550px;\"   onclick=\"document.getElementById('form1:panelEstadoCuentas').style.visibility='hidden';document.getElementById('panelsuper').style.visibility='hidden';\" onmouseover=\"this.style.cursor='hand'\"   >Cerrar</div> </div> "
                              escape="false" />
                    </f:facet>
                    </rich:panel>

                <rich:panel style="width:100%"  headerClass="headerClassACliente">
                            <f:facet name="header">
                                <h:outputText value="Registro de Salidas a Almacen"  />
                            </f:facet>
                            <h:panelGrid columns="4" id="panelContenidoSalidaAlmacen">
                            
                            
                            <h:outputText value="Nro Salida:" styleClass="outputText2" />
                            <h:outputText value="#{ManagedSolicitudSalidaAlmacen.salidasAlmacen.nroSalidaAlmacen}" styleClass="outputText1" />

                            <h:outputText value="Area / Departamento:" styleClass="outputText2" />
                            <h:selectOneMenu  styleClass="inputText" value="#{ManagedSolicitudSalidaAlmacen.salidasAlmacen.areasEmpresa.codAreaEmpresa}" id="codAreaEmpresa"
                                              disabled="#{ManagedSolicitudSalidaAlmacen.salidasAlmacen.tiposSalidasAlmacen.codTipoSalidaAlmacen eq '16'}">
                                <f:selectItems value="#{ManagedSolicitudSalidaAlmacen.areasEmpresaList}"  />
                                <a4j:support event="onchange" action="#{ManagedSolicitudSalidaAlmacen.nroLote_change}" reRender="panelContenidoSalidaAlmacen"
                                             oncomplete="if(#{ManagedSolicitudSalidaAlmacen.mensaje!=''}){alert('#{ManagedSolicitudSalidaAlmacen.mensaje}');}" />
                            </h:selectOneMenu>

                            <h:outputText value="Tipo de Salida:" styleClass="outputText2" />
                            <h:selectOneMenu  styleClass="inputText" value="#{ManagedSolicitudSalidaAlmacen.salidasAlmacen.tiposSalidasAlmacen.codTipoSalidaAlmacen}"  
                                              
                                              disabled="#{ManagedSolicitudSalidaAlmacen.salidasAlmacen.tiposSalidasAlmacen.codTipoSalidaAlmacen eq '16' || 
                                                            ManagedSolicitudSalidaAlmacen.salidasAlmacen.tiposSalidasAlmacen.codTipoSalidaAlmacen eq '3'}">
                                <f:selectItems value="#{ManagedSolicitudSalidaAlmacen.tiposSalidasAlmacenList}"  />
                            </h:selectOneMenu>

                            <h:outputText value="Nro Lote:" styleClass="outputText2" rendered="#{ManagedSolicitudSalidaAlmacen.usuario.almacenesGlobal.codAlmacen != '14'}" />
                            <h:outputText value="Instalacion:" styleClass="outputText2" rendered="#{ManagedSolicitudSalidaAlmacen.usuario.almacenesGlobal.codAlmacen eq '14'}" />
                            <h:panelGroup>
                            <h:outputText value="#{ManagedSolicitudSalidaAlmacen.salidasAlmacen.codLoteProduccion}" styleClass="outputText1" id="codLoteProduccion" rendered="#{ManagedSolicitudSalidaAlmacen.usuario.almacenesGlobal.codAlmacen != '14'}"/>
                            <h:outputText value="#{ManagedSolicitudSalidaAlmacen.salidasAlmacen.areasInstalaciones.nombreAreaInstalacion}" styleClass="outputText1" rendered="#{ManagedSolicitudSalidaAlmacen.usuario.almacenesGlobal.codAlmacen eq '14'}"/>
                            </h:panelGroup>

                            
                            
                            <h:outputText value="Producto:" styleClass="outputText2" rendered="#{ManagedSolicitudSalidaAlmacen.usuario.almacenesGlobal.codAlmacen != '14'}" />
                            
                            <h:panelGroup>
                                <h:selectOneMenu  styleClass="inputText" value="#{ManagedSolicitudSalidaAlmacen.salidasAlmacen.componentesProd.codCompprod}" id="producto" rendered="#{ManagedSolicitudSalidaAlmacen.usuario.almacenesGlobal.codAlmacen != '14'}">
                                    <f:selectItems value="#{ManagedSolicitudSalidaAlmacen.componentesProdList}"  />
                                    <a4j:support event="onchange" action="#{ManagedSolicitudSalidaAlmacen.producto_changed}" reRender="presentacionProducto" />
                                </h:selectOneMenu>
                                <a4j:commandLink  action="#{ManagedSolicitudSalidaAlmacen.detallarMateriales_action}" reRender="dataSalidasAlmacenDetalle" rendered="#{ManagedSolicitudSalidaAlmacen.usuario.almacenesGlobal.codAlmacen != '14'}">
                                    <h:graphicImage url="../img/actualizar1.jpg" />
                                </a4j:commandLink>
                                
                            </h:panelGroup>
                            <h:outputText value="Maquinaria:" styleClass="outputText2"  rendered="#{ManagedSolicitudSalidaAlmacen.solicitudesSalida.maquinaria.codMaquina != '0'}" />
                            <h:outputText  styleClass="outputText" value="#{ManagedSolicitudSalidaAlmacen.salidasAlmacen.maquinaria.nombreMaquina}"   rendered="#{ManagedSolicitudSalidaAlmacen.solicitudesSalida.maquinaria.codMaquina != '0'}" />
                            
                            <h:outputText value="Presentacion:" styleClass="tituloCampo1" rendered="#{ManagedSolicitudSalidaAlmacen.usuario.almacenesGlobal.codAlmacen != '14'}" />
                            <h:outputText value="#{ManagedSolicitudSalidaAlmacen.salidasAlmacen.presentacionesProducto.nombreProductoPresentacion}" styleClass="outputText2"
                                          rendered="#{ManagedSolicitudSalidaAlmacen.usuario.almacenesGlobal.codAlmacen != '14' && ManagedSolicitudSalidaAlmacen.salidasAlmacen.tiposSalidasAlmacen.codTipoSalidaAlmacen eq '16'}"/>
                            <h:selectOneMenu  styleClass="inputText" value="#{ManagedSolicitudSalidaAlmacen.salidasAlmacen.presentacionesProducto.codPresentacion}"  id="presentacionProducto" rendered="#{ManagedSolicitudSalidaAlmacen.usuario.almacenesGlobal.codAlmacen != '14' && ManagedSolicitudSalidaAlmacen.salidasAlmacen.tiposSalidasAlmacen.codTipoSalidaAlmacen != '16'}"  >
                                <f:selectItems value="#{ManagedSolicitudSalidaAlmacen.presentacionesList}"  />
                            </h:selectOneMenu>

                            <h:outputText value="Observaciones:" styleClass="outputText2" />
                            <h:inputTextarea  rows="5" cols="50" styleClass="outputText2" value="#{ManagedSolicitudSalidaAlmacen.salidasAlmacen.obsSalidaAlmacen}" />

                            <h:outputText value="Orden de Trabajo:" styleClass="outputText2" />
                            <h:inputText value="#{ManagedSolicitudSalidaAlmacen.salidasAlmacen.ordenTrabajo}" styleClass="inputText" />                            
                            <h:outputText value="Almacen Destino del traspaso : " styleClass="outputText2" 
                                          rendered="#{ManagedSolicitudSalidaAlmacen.salidasAlmacen.tiposSalidasAlmacen.codTipoSalidaAlmacen eq '3'}"/> 
                            <h:outputText value="#{ManagedSolicitudSalidaAlmacen.solicitudesSalida.almacenesDestino.nombreAlmacen}" styleClass="outputText2"
                                          rendered="#{ManagedSolicitudSalidaAlmacen.salidasAlmacen.tiposSalidasAlmacen.codTipoSalidaAlmacen eq '3'}"/> 

                             <a4j:commandButton value="Agregar Item" styleClass="btn"
                             onclick="Richfaces.showModalPanel('panelAgregarItem')" action="#{ManagedSolicitudSalidaAlmacen.agregarItem_action}"
                             reRender="contenidoAgregarItem"
                             rendered="#{ManagedSolicitudSalidaAlmacen.usuario.almacenesGlobal.codAlmacen eq '13'}"
                              />

                            
                        </h:panelGrid>
                    </rich:panel>
                    
                    
                        <div style="overflow:auto;height:200px;width:80%;text-align:center">
                       <rich:dataTable value="#{ManagedSolicitudSalidaAlmacen.salidasAlmacenDetalleList}"
                                    var="data"
                                    id="dataSalidasAlmacenDetalle"
                                    onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                    onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                    headerClass="headerClassACliente"                                    
                                    style="width:100%">
                        <rich:column style="background-color: #{data.colorFila}" >
                            <f:facet name="header">
                                <h:outputText value=""  />
                            </f:facet>
                            <h:outputText value=""  />
                        </rich:column>

                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value="Item"  />
                            </f:facet>
                            <h:outputText  value="#{data.materiales.nombreMaterial}"/>
                        </rich:column>
                       
                        <rich:column styleClass="tdRight">
                            <f:facet name="header">
                                <h:outputText value="Cantidad"  />
                            </f:facet>
                            <h:outputText value="#{data.cantidadSalidaAlmacen}">
                                <f:convertNumber pattern="#,##0.00" locale="en"/>
                            </h:outputText>
                        </rich:column>
                      
                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value="Unid."   />
                            </f:facet>
                            <h:outputText value="#{data.unidadesMedida.abreviatura}"  />
                        </rich:column>
                        
                        <rich:column styleClass="tdRight">
                            <f:facet name="header">
                                <h:outputText value="Cant. Disp."   />
                            </f:facet>
                            <h:outputText value="#{data.cantidadDisponible}">
                                <f:convertNumber pattern="#,##0.00" locale="en"/>
                            </h:outputText>
                        </rich:column>
                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value=""   />
                            </f:facet>
                            <a4j:commandLink reRender="contenidoSalidaAlmacenDetalleEstado"
                                            oncomplete="Richfaces.showModalPanel('panelSalidaAlmacenDetalleEstado');cantAuxSalida();">
                            <h:graphicImage url="../img/areasdependientes.png"/>
                            <f:setPropertyActionListener target="#{ManagedSolicitudSalidaAlmacen.salidasAlmacenDetalle}" value="#{data}"/>
                            </a4j:commandLink>
                        </rich:column>
                        <rich:column>
                             <f:facet name="header">
                                <h:outputText value="Editar"   />
                            </f:facet>
                             <a4j:commandLink action="#{ManagedSolicitudSalidaAlmacen.editarCantidadSalidasAlmacenDetalleAction}" 
                                                reRender="contenidoModificarCantidadMaterial"
                                                styleClass="btn"
                                                oncomplete="Richfaces.showModalPanel('panelModificarCantidadMaterial');">
                                 <h:outputText value="Editar"/>
                                 <f:setPropertyActionListener target="#{ManagedSolicitudSalidaAlmacen.salidasAlmacenDetalle}" value="#{data}"/>
                            </a4j:commandLink>
                        </rich:column>
                        <rich:column>
                             <f:facet name="header">
                                <h:outputText value="Eliminar"   />
                            </f:facet>
                             <a4j:commandLink action="#{ManagedSolicitudSalidaAlmacen.eliminarSalidaAlmacenDetalleAction}" 
                                              styleClass="btn"
                                                reRender="dataSalidasAlmacenDetalle">
                                 <h:outputText value="Eliminar"/>
                                 <f:setPropertyActionListener target="#{ManagedSolicitudSalidaAlmacen.salidasAlmacenDetalle}" value="#{data}"/>
                            </a4j:commandLink>
                        </rich:column>

                    </rich:dataTable>
                    </div>
                    <br>
                    <div id="buttonGuardar">
                        <a4j:commandButton value="Guardar" styleClass="btn" ondblclick="alert('No utilice doble click');return false;"
                        action="#{ManagedSolicitudSalidaAlmacen.guardarSalidaAlmacen_action}"
                        onclick="if(confirm('Esta seguro de realizar la Salida de Almacen?')==false){return false;}"
                        oncomplete="mostrarMensajeTransaccionEvento(function(){bloquearGuardar();redireccionar('navegadorSolicitudesSalidaAlmacen.jsf');})"
                        />
                        <a4j:commandButton value="Cancelar" styleClass="btn"
                                           oncomplete="window.location.href='navegadorSolicitudesSalidaAlmacen.jsf?data='+(new Date()).getTime().toString();"/>
                    </div>
                    <center><img style="display:none"  src="../img/load.gif" id="progress"></center>
            </a4j:form>

             <rich:modalPanel id="panelModificarCantidadMaterial" minHeight="180"  minWidth="650"
                                     height="180" width="650"
                                     zindex="200"
                                     headerClass="headerClassACliente"
                                     resizeable="false" style="overflow :auto" >
                        <f:facet name="header">
                            <h:outputText value="Modificar Cantidad Material"/>
                        </f:facet>
                        <a4j:form id="form4">
                        <h:panelGroup id="contenidoModificarCantidadMaterial">
                            <h:panelGrid columns="3">
                                <h:outputText value="Item" styleClass="outputText1" />
                                <h:outputText value="::" styleClass="outputText1" />
                                <h:outputText value="#{ManagedSolicitudSalidaAlmacen.salidasAlmacenDetalle.materiales.nombreMaterial}" styleClass="outputText1" />

                                <h:outputText value="Cantidad Disponible" styleClass="outputText1" />
                                <h:outputText value="::" styleClass="outputText1" />
                                <h:outputText value="#{ManagedSolicitudSalidaAlmacen.salidasAlmacenDetalle.cantidadDisponible}" id="cantidadDisponible"  styleClass="outputText1" />

                                <h:outputText value="Cantidad de Salida" styleClass="outputText1" />
                                <h:outputText value="::" styleClass="outputText1" />
                                <h:panelGroup>
                                    <h:inputText value="#{ManagedSolicitudSalidaAlmacen.salidasAlmacenDetalle.cantidadSalidaAlmacen}" 
                                                 styleClass="inputText" 
                                                 converterMessage="Debe registrar un numero"
                                                 id="cantidadSalidaAlmacen" >
                                        <f:validator validatorId="validatorDoubleRange"/>
                                        <f:attribute name="minimum" value="0.01"/>
                                        <f:attribute name="maximum" value="#{ManagedSolicitudSalidaAlmacen.salidasAlmacenDetalle.cantidadDisponible}"/>
                                        <f:attribute name="disable" value="#{(empty param['form4:btnGuardar'])}"/>
                                    </h:inputText>
                                    <h:message for="cantidadSalidaAlmacen" styleClass="message"/>
                                    
                                    <h:outputText value="#{ManagedSolicitudSalidaAlmacen.salidasAlmacenDetalle.equivalencias.unidadesMedida1.nombreUnidadMedida}" styleClass="inputText" >
                                        
                                    </h:outputText>
                                </h:panelGroup>

                                <h:outputText value="Equivalente" styleClass="outputText1" />
                                <h:outputText value="::" styleClass="outputText1" />
                                <h:panelGroup>
                                    <h:inputText value="#{ManagedSolicitudSalidaAlmacen.salidasAlmacenDetalle.cantidadSalidaAlmacenEquivalente}" readonly="true" id="cantidadEquivalencia" styleClass="inputText" />
                                    <h:outputText value="#{ManagedSolicitudSalidaAlmacen.salidasAlmacenDetalle.equivalencias.unidadesMedida2.nombreUnidadMedida}" styleClass="outputText1" />
                                    <h:inputHidden value="#{ManagedSolicitudSalidaAlmacen.salidasAlmacenDetalle.equivalencias.valorEquivalencia}" id="valorEquivalencia" />
                                </h:panelGroup>
                                
                                
                           
                            </h:panelGrid>
   

                                <div align="center">

                                <a4j:commandButton styleClass="btn" value="Guardar"
                                                   id="btnGuardar"
                                                action="#{ManagedSolicitudSalidaAlmacen.guardarEdicionCantidadMaterial_action}"
                                                reRender="dataSalidasAlmacenDetalle,contenidoModificarCantidadMaterial"  
                                                oncomplete="if('#{facesContext.maximumSeverity}'.length == 0){Richfaces.hideModalPanel('panelModificarCantidadMaterial')}"
                                  />
                                
                                <input type="button" value="Cancelar" onclick="javascript:Richfaces.hideModalPanel('panelModificarCantidadMaterial')" class="btn" />
                                </div>
                        </h:panelGroup>
                        </a4j:form>
                        </div>
            </rich:modalPanel>
            
            <a4j:include viewId="/panelProgreso.jsp"/>
            <a4j:include viewId="/message.jsp"/>
            <a4j:include viewId="panelDetalleItemSalida.jsp" id="pDetalleItemSalida"/>
         
      

        </body>
    </html>

</f:view>

