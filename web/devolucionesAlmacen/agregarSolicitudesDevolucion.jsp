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
                function valida(){                    
                     if(document.getElementById("form2:nroSalida").value==''){
                         document.getElementById("form2:nroSalida").value = 0;
                     }                     
                     if(document.getElementById("form2:nroSalida").value==0 && document.getElementById("form2:nroLote").value=='' && document.getElementById("form2:codCompProd").value==0    ){
                         alert('Seleccione un dato para la busqueda');
                         return false;
                     }

                     return true;
                
                }
         function valorPorDefecto(input)
         {
            if(input.value == '')
            {
                input.value='0';
            }
         }
         function validaCantidad(obj){
           var cantidadDisponible =  parseFloat(obj.parentNode.parentNode.cells[3].innerHTML.replace(",",""));
           var cantidadEntregada =  obj.parentNode.parentNode.getElementsByTagName('td')[4].innerHTML;
           var cantidadSolicitudPendiente=obj.parentNode.parentNode.getElementsByTagName('td')[5].innerHTML;
           var cantidadBuenos =  obj.parentNode.parentNode.getElementsByTagName('td')[6].getElementsByTagName("input")[0].value;
           var cantidadFallados = (obj.parentNode.parentNode.getElementsByTagName('td')[7] != null ? obj.parentNode.parentNode.getElementsByTagName('td')[7].getElementsByTagName("input")[0].value:0);
           var cantidadFalladosProveedor = (obj.parentNode.parentNode.getElementsByTagName('td')[8] !=null ?obj.parentNode.parentNode.getElementsByTagName('td')[8].getElementsByTagName("input")[0].value:0);
           
        if((parseFloat(cantidadBuenos)+ parseFloat(cantidadFallados)+parseFloat(cantidadFalladosProveedor))>(parseFloat(cantidadDisponible)-(parseFloat(cantidadEntregada)+parseFloat(cantidadSolicitudPendiente)))){
                alert("la suma de cantidades no debe sobrepasar la cantidad disponible");
                obj.focus();
                obj.value = 0;
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




       </script>
            <style type="text/css">
                .headerClassACliente td
                {
                    color: black !important;
                }
                .sinCosto
                {
                    color: red;
                    font-weight: bold;
                }
            </style>
        </head>
        <body >
            <a4j:form id="form1">
                <div align="center">
                    <h:outputText value="#{ManagedSolicitudDevoluciones.cargarAgregarDevolucionesAlmacen}"/>
                    <h:outputText styleClass="outputTextTitulo"  value="Agregar Solicitud de devolucion almacen" />
                    <br><br>
                        <rich:panel style="width:80%"  headerClass="headerClassACliente">
                            <f:facet name="header">
                                <h:outputText value="Registro de Devolucion a Almacen"  />
                            </f:facet>
                             <h:panelGrid columns="4" >
                                <h:outputText value="Observacion de la solicitud:" styleClass="outputText2" />
                                <h:inputTextarea value="#{ManagedSolicitudDevoluciones.observacion}" styleClass="outputText1" rows="3" style="width:150px"/>
                            </h:panelGrid>
                        </rich:panel>
                        <rich:panel style="width:80%" headerClass="headerClassACliente">
                            <f:facet name="header">
                                <h:outputText value="Datos de la salida Almacen"  />
                            </f:facet>
                            <h:panelGrid columns="4" >
                                <h:outputText value="Nro Salida:" styleClass="outputText2" />
                                <h:outputText value="#{ManagedSolicitudDevoluciones.salidasAlmacen.nroSalidaAlmacen}" styleClass="outputText1" />
                                <h:outputText value="Area / Departamento:" styleClass="outputText2" />
                                <h:outputText  styleClass="outputText1" value="#{ManagedSolicitudDevoluciones.salidasAlmacen.areasEmpresa.nombreAreaEmpresa}"  />
                                <h:outputText value="Tipo de Salida:" styleClass="outputText2" />
                                <h:outputText styleClass="outputText1" value="#{ManagedSolicitudDevoluciones.salidasAlmacen.tiposSalidasAlmacen.nombreTipoSalidaAlmacen}"   />
                                <h:outputText value="Nro Lote:" styleClass="outputText2" />
                                <h:outputText value="#{ManagedSolicitudDevoluciones.salidasAlmacen.codLoteProduccion}" styleClass="outputText1" />
                                <h:outputText value="Producto:" styleClass="outputText2" />                            
                                <h:outputText  styleClass="outputText1" value="#{ManagedSolicitudDevoluciones.salidasAlmacen.componentesProd.nombreProdSemiterminado}"   />
                                <h:outputText value="Presentacion:" styleClass="outputText2"  />
                                <h:outputText  styleClass="outputText1" value="#{ManagedSolicitudDevoluciones.salidasAlmacen.presentacionesProducto.nombreProductoPresentacion}"  id="presentacionProducto" />
                                <h:outputText value="Observaciones:" styleClass="outputText2" />
                                <h:outputText styleClass="outputText1" value="#{ManagedSolicitudDevoluciones.salidasAlmacen.obsSalidaAlmacen}" />
                                <h:outputText value="Orden de Trabajo:" styleClass="outputText2" />
                                <h:outputText value="#{ManagedSolicitudDevoluciones.salidasAlmacen.ordenTrabajo}" styleClass="outputText1" />
                            </h:panelGrid>
                        </rich:panel>
                    
                    <rich:dataTable value = "#{ManagedSolicitudDevoluciones.devolucionesDetalleList}"
                                    var = "data"
                                    id = "dataDevolucionesDetalleAlmacen"
                                    onRowMouseOut = "this.style.backgroundColor='#FFFFFF';"
                                    onRowMouseOver = "this.style.backgroundColor='#DDE3E4';"
                                    headerClass = "headerClassACliente"
                                    binding = "#{ManagedSolicitudDevoluciones.devolucionesDetalleDataTable}" style="width:80%">

                                    <rich:column>
                                        <f:facet name="header">
                                            <h:outputText value="Nro Salida"  />
                                        </f:facet>
                                        <h:outputText value="#{data.salidasAlmacen.nroSalidaAlmacen}" />
                                    </rich:column>
'                                    <rich:column>
                                        <f:facet name="header">
                                            <h:outputText value="Producto"  />
                                        </f:facet>
                                        <h:outputText value="#{data.salidasAlmacen.componentesProd.nombreProdSemiterminado}" />
                                    </rich:column>
                                    <rich:column>
                                        <f:facet name="header">
                                            <h:outputText value="Lote"  />
                                        </f:facet>
                                        <h:outputText value="#{data.salidasAlmacen.codLoteProduccion}" />
                                    </rich:column>

                                    <rich:column>
                                        <f:facet name="header">
                                            <h:outputText value="Item"  />
                                        </f:facet>
                                        <h:outputText value="#{data.materiales.nombreMaterial}" />
                                    </rich:column>

                                  <rich:column>
                                        <f:facet name="header">
                                            <h:outputText value="Cantidad Entregada"  />
                                        </f:facet>
                                        <h:outputText  value="#{data.cantidadEntregada}"  />
                                    </rich:column>
                                    <rich:column>
                                        <f:facet name="header">
                                            <h:outputText value="Cantidad Devuelta"  />
                                        </f:facet>
                                        <h:outputText value="#{data.cantidadDevuelta}"  >
                                        </h:outputText>
                                    </rich:column>
                                    <rich:column style="text-align:right">
                                        <f:facet name="header">
                                            <h:outputText value="Cantidad Devuelta FRV"  />
                                        </f:facet>
                                        <h:outputText value="#{data.cantidadDevueltaFallados}"  >
                                            <f:convertNumber pattern="#,##0.00" locale="en"/>
                                        </h:outputText>
                                        <br/>
                                        <h:outputText value="#{data.costoSolicitudDevolucionFrv}" rendered="#{data.costoSolicitudDevolucionFrv>0}">
                                            <f:convertNumber pattern="#,##0.00" locale="en"/>
                                        </h:outputText>
                                        <h:outputText value=" (Bs)" rendered="#{data.costoSolicitudDevolucionFrv>0}"/>
                                    </rich:column>
                                    <rich:column style="text-align:right">
                                        <f:facet name="header">
                                            <h:outputText value="Cantidad Devuelta FRV Proveedor"  />
                                        </f:facet>
                                        <h:outputText value="#{data.cantidadDevueltaFalladosProveedor}"  >
                                            <f:convertNumber pattern="#,##0.00" locale="en"/>
                                        </h:outputText>
                                        <br/>
                                        <h:outputText value="#{data.costoSolicitudDevolucionFrvProveedor}" rendered="#{data.costoSolicitudDevolucionFrvProveedor > 0}">
                                            <f:convertNumber pattern="#,##0.00" locale="en"/>
                                        </h:outputText>
                                        <h:outputText value=" (Bs)" rendered="#{data.costoSolicitudDevolucionFrvProveedor > 0}"/>
                                    </rich:column>
                                    
                                    <rich:column>
                                        <f:facet name="header">
                                            <h:outputText value="Unidades"  />
                                        </f:facet>
                                        <h:outputText value="#{data.unidadesMedida.abreviatura}"  />
                                    </rich:column>
                                    <rich:column>
                                        <f:facet name="header">
                                            <h:outputText value=""   />
                                        </f:facet>
                                        <a4j:commandLink onclick="Richfaces.showModalPanel('panelDevolucionAlmacenDetalleEtiquetas')"
                                                         rendered="#{data.materiales.codMaterial ne '11887'}"
                                        action="#{ManagedSolicitudDevoluciones.verDevolucionesDetalleEtiquetas_action}"
                                              reRender="contenidoDevolucionAlmacenDetalleEtiquetas"
                                              >
                                        <h:graphicImage url="../img/areasdependientes.png">
                                        </h:graphicImage>
                                        </a4j:commandLink>
                                        <a4j:commandLink onclick="Richfaces.showModalPanel('panelDevolucionFrv')"
                                                         rendered="#{data.materiales.codMaterial eq '11887'}"
                                        action="#{ManagedSolicitudDevoluciones.verDevolucionesDetalleEtiquetas_action}"
                                              reRender="contenidoDevolucionFrv"
                                              >
                                        <h:graphicImage url="../img/areasdependientes.png">

                                        </h:graphicImage>
                                        </a4j:commandLink>
                                    </rich:column>
                    </rich:dataTable>
                    



                    <br>
                        <a4j:commandButton value="Guardar" styleClass="btn"
                                           action="#{ManagedSolicitudDevoluciones.guardarSolicitudDevolucion_action}"
                                            oncomplete="if(#{ManagedSolicitudDevoluciones.mensaje!=''}){alert('#{ManagedSolicitudDevoluciones.mensaje}')}else{alert('la solicitud se registro exitosamente');location='navegadorSolicitudDevoluciones.jsf'}"
                                            onclick="if(confirm('Esta seguro de realizar la solicitud?')==false){return false;}" />
                        <a4j:commandButton value="Cancelar" styleClass="btn"
                                           oncomplete="window.location.href='navegadorSolicitudDevoluciones.jsf?data='+(new Date()).getTime().toString()"
                                           />
                    </div>

               

            </a4j:form>
            
            <rich:modalPanel id="panelDevolucionFrv" minHeight="160"  minWidth="500"
                                     height="160" width="500"
                                     zindex="200"
                                     headerClass="headerClassACliente"
                                     resizeable="false" style="overflow :auto" >
                <f:facet name="header">
                    <h:outputText value="<center>Detalle de devolucion</center>" escape="false"/>
                </f:facet>
                <a4j:form>
                    <h:panelGroup id="contenidoDevolucionFrv">
                        <rich:dataTable value="#{ManagedSolicitudDevoluciones.devolucionesDetalle.devolucionesDetalleEtiquetasList}"
                                        var="data"
                                        id="dataDetalleFrv"
                                        onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                        onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                        headerClass="headerClassACliente" >
                                <f:facet name="header">
                                    <rich:columnGroup >
                                        <rich:column>
                                            <h:outputText value="Material" styleClass="outputText2"/>
                                        </rich:column>
                                        <rich:column>
                                            <h:outputText value="Tipo Frv Proceso"/>
                                        </rich:column>
                                        <rich:column>
                                            <h:outputText value="Cantidad Frv Proceso (g)"/>
                                        </rich:column>
                                        
                                    </rich:columnGroup>
                                </f:facet>
                                    <rich:column style="text-align:center">
                                        <h:outputText value="#{ManagedSolicitudDevoluciones.devolucionesDetalle.materiales.nombreMaterial}" />
                                    </rich:column>
                                    <rich:column>
                                        <h:selectOneMenu value="#{data.tipoFrvProceso.codTipoFrvProceso}" styleClass="inputText">
                                            <f:selectItems value="#{ManagedSolicitudDevoluciones.tipoFrvProcesoSelectList}"/>
                                            <a4j:support event="onchange" reRender="dataDetalleFrv"/>
                                        </h:selectOneMenu>
                                        <br/>
                                        <h:outputText style="display:none" value="#{data.costoFrvConMp}" rendered="#{data.tipoFrvProceso.codTipoFrvProceso eq 1}"/>
                                        <h:outputText style="display:none" value="#{data.costoFrvConMpEp}" rendered="#{data.tipoFrvProceso.codTipoFrvProceso eq 2}"/>
                                        <h:outputText style="display:none" value="#{data.costoFrvRecubrimiento}" rendered="#{data.tipoFrvProceso.codTipoFrvProceso eq 3}"/>
                                    </rich:column>
                                    <rich:column>
                                        <h:inputText value="#{data.cantidadFallados}" styleClass="inputText" onkeypress="valNum(event);"
                                                     rendered="#{(data.costoFrvConMp>0 and data.tipoFrvProceso.codTipoFrvProceso eq 1 ) or 
                                                                 (data.costoFrvRecubrimiento>0 and data.tipoFrvProceso.codTipoFrvProceso eq 3 ) or 
                                                                 (data.costoFrvConMpEp > 0 and data.tipoFrvProceso.codTipoFrvProceso eq 2)}"/>
                                        <h:outputText value="NO SE CUENTA CON UN COSTO PARA ESTE FRV" styleClass="sinCosto"
                                                      rendered="#{(data.costoFrvConMp <= 0 and data.tipoFrvProceso.codTipoFrvProceso eq 1 ) or
                                                                  (data.costoFrvRecubrimiento<=0 and data.tipoFrvProceso.codTipoFrvProceso eq 3 ) or 
                                                                  (data.costoFrvConMpEp <= 0 and data.tipoFrvProceso.codTipoFrvProceso eq 2)}"/>
                                    </rich:column>
                            </rich:dataTable>
                                
                            </div>

                            

                        </h:panelGroup>
                        <div align="center">
                          <a4j:commandButton styleClass="btn" value="Registrar"
                          action="#{ManagedSolicitudDevoluciones.registrarDevolucionesDetalleEtiquetasFrv_action}"
                          oncomplete="Richfaces.hideModalPanel('panelDevolucionFrv')"
                          reRender="dataDevolucionesDetalleAlmacen" />

                         

                        </div>
                        
                        </a4j:form>

            </rich:modalPanel>

              <rich:modalPanel id="panelDevolucionAlmacenDetalleEtiquetas" minHeight="400"  minWidth="800"
                                     height="400" width="800"
                                     zindex="200"
                                     headerClass="headerClassACliente"
                                     resizeable="false" style="overflow :auto" >
                        <f:facet name="header">
                            <h:outputText value="<center>Detalle de devolucion</center>" escape="false"/>
                        </f:facet>
                        <a4j:form>
                        <h:panelGroup id="contenidoDevolucionAlmacenDetalleEtiquetas">
                            <center>
                                <rich:panel style="width:80%" headerClass="headerClassACliente">
                                    <f:facet name="header">
                                        <h:outputText value="Datos del Material a Devolver"/>
                                    </f:facet>
                                    <h:panelGrid columns="4" >
                                        <h:outputText value="Material: " styleClass="outputText2" style="font-weight:bold"/>
                                        <h:outputText value="#{ManagedSolicitudDevoluciones.devolucionesDetalle.materiales.nombreMaterial}" styleClass="outputText2"/>
                                        <h:outputText value="Unidad Medida: " styleClass="outputText2" style="font-weight:bold"/>
                                        <h:outputText value="#{ManagedSolicitudDevoluciones.devolucionesDetalle.unidadesMedida.abreviatura}" styleClass="outputText2"/>
                                    </h:panelGrid>
                                </rich:panel>
                            </center>
                            <div style="overflow:auto;height:220px;text-align:center;margin-top:8px">
                            <rich:dataTable value="#{ManagedSolicitudDevoluciones.devolucionesDetalle.devolucionesDetalleEtiquetasList}"
                                            var="data"
                                            id="dataDevolucionesDetalleEtiquetasAlmacen"
                                            onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                            onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                            headerClass="headerClassACliente" >
                                <f:facet name="header">
                                    <rich:columnGroup >
                                        <rich:column>
                                            <h:outputText value="Nro Ingreso" styleClass="outputText2"/>
                                        </rich:column>
                                        <rich:column>
                                            <h:outputText value="Lote Proveedor"/>
                                        </rich:column>
                                        <rich:column>
                                            <h:outputText value="Etiqueta"/>
                                        </rich:column>
                                        <rich:column>
                                            <h:outputText value="Cantidad Salida"/>
                                        </rich:column>
                                        <rich:column>
                                            <h:outputText value="Cantidad Devuelta"/>
                                        </rich:column>
                                        <rich:column>
                                            <h:outputText value="Otras Solicitudes Devolución"/>
                                        </rich:column>
                                        <rich:column>
                                            <h:outputText value="Cantidad Buenos"/>
                                        </rich:column>
                                        <rich:column rendered="#{ManagedSolicitudDevoluciones.devolucionesDetalle.materiales.grupos.capitulos.codCapitulo ne '2'}">
                                            <h:outputText value="Cantidad Frv"/>
                                        </rich:column>
                                        <rich:column rendered="#{ManagedSolicitudDevoluciones.devolucionesDetalle.materiales.grupos.capitulos.codCapitulo ne '2'}">
                                            <h:outputText value="Cantidad Frv Proveedor"/>
                                        </rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                    <rich:column style="text-align:center">
                                        <h:outputText value="#{data.ingresosAlmacen.nroIngresoAlmacen}" />
                                    </rich:column>
                                    <rich:column>
                                        <h:outputText  value="#{data.loteMaterialProveedor}"  />
                                    </rich:column>
                                    <rich:column>
                                        <h:outputText  value="#{data.etiqueta}"  />
                                    </rich:column>
                                    <rich:column style="text-align:right">
                                        <h:outputText value="#{data.cantidadSalida}">
                                            <f:convertNumber pattern="#,##0.00" locale="en"/>
                                        </h:outputText>
                                    </rich:column>
                                    <rich:column style="text-align:right">
                                        <h:outputText value="#{data.cantidadesDevueltas}">
                                            <f:convertNumber pattern="#,##0.00" locale="en"/>
                                        </h:outputText>
                                    </rich:column>
                                    <rich:column style="text-align:right">
                                        <h:outputText value="#{data.cantidad_solicitud_devolucion}">
                                            <f:convertNumber pattern="#,##0.00" locale="en"/>
                                        </h:outputText>
                                    </rich:column>
                                    <rich:column>
                                        <h:inputText size="12" value="#{data.cantidadDevuelta}" styleClass="inputText" onkeypress="valNum(event);" onblur="valorPorDefecto(this)" onkeyup="validaCantidad(this);" style="text-align:right"/>
                                    </rich:column>
                                    <rich:column rendered="#{ManagedSolicitudDevoluciones.devolucionesDetalle.materiales.grupos.capitulos.codCapitulo ne '2'}">
                                        <h:inputText size="12" value="#{data.cantidadFallados}" styleClass="inputText" onkeypress="valNum(event);" onblur="valorPorDefecto(this)" onkeyup="validaCantidad(this);" style="text-align:right"
                                                     rendered="#{data.costoSalidaAplActualizado > 0}"/>
                                        <h:outputText value="NO SE CUENTA CON UN COSTO PARA ESTE FRV" styleClass="sinCosto"
                                                      rendered="#{(data.costoSalidaAplActualizado eq 0)}"/>
                                        <h:outputText value="ES ITEM NO CUENTA CON UN HISTORICO FAVOR NOTIFICAR A COSTOS, COMPRAS" styleClass="sinCosto"
                                                      rendered="#{(data.costoSalidaAplActualizado < 0)}"/>
                                    </rich:column>
                                    <rich:column rendered="#{ManagedSolicitudDevoluciones.devolucionesDetalle.materiales.grupos.capitulos.codCapitulo ne '2'}">
                                        <h:inputText size="12" value="#{data.cantidadFalladosProveedor}" styleClass="inputText" onkeypress="valNum(event);" onblur="valorPorDefecto(this)" onkeyup="validaCantidad(this);" style="text-align:right"
                                                     rendered="#{data.costoSalidaAplActualizado > 0}"/>
                                        <h:outputText value="NO SE CUENTA CON UN COSTO PARA ESTE FRV" styleClass="sinCosto"
                                                      rendered="#{(data.costoSalidaAplActualizado eq 0)}"/>
                                        <h:outputText value="ES ITEM NO CUENTA CON UN HISTORICO FAVOR NOTIFICAR A COSTOS, COMPRAS" styleClass="sinCosto"
                                                      rendered="#{(data.costoSalidaAplActualizado < 0)}"/>
                                    </rich:column>
                                            
                            </rich:dataTable>
                                
                            </div>

                            

                        </h:panelGroup>
                        <div align="center">
                          <a4j:commandButton styleClass="btn" value="Registrar"
                          action="#{ManagedSolicitudDevoluciones.registrarDevolucionesDetalleEtiquetas_action}"
                          onclick="Richfaces.hideModalPanel('panelDevolucionAlmacenDetalleEtiquetas')"
                          reRender="dataDevolucionesDetalleAlmacen" />

                         

                        </div>
                        
                        </a4j:form>

            </rich:modalPanel>


             <rich:modalPanel id="panelAgregarFrecuenciaMantenimientoMaquinaria" minHeight="150"  minWidth="400"
                                     height="150" width="400"
                                     zindex="200"
                                     headerClass="headerClassACliente"
                                     resizeable="false" style="overflow :auto" >
                        <f:facet name="header">
                            <h:outputText value="Frecuencia Mantenimiento Maquinaria"/>
                        </f:facet>
                        <a4j:form>
                        <h:panelGroup id="contenidoFrecuenciaMantenimentoMaquinaria">
                            <h:panelGrid columns="3">
                                <h:outputText value="Tipo de Periodo" styleClass="outputText1" />
                                <h:outputText value="::" styleClass="outputText1" />
                                <h:selectOneMenu value="#{ManagedFrecuenciaMantenimientoMaquinaria.frecuenciasMantenimientoMaquina.tiposPeriodo.codTipoPeriodo}" styleClass="inputText" >
                                    <f:selectItems value="#{ManagedFrecuenciaMantenimientoMaquinaria.tiposPeriodoList}"  />
                                </h:selectOneMenu>

                                <h:outputText value="Frecuencia" styleClass="outputText1" />
                                <h:outputText value="::" styleClass="outputText1" />
                                <h:inputText value="#{ManagedFrecuenciaMantenimientoMaquinaria.frecuenciasMantenimientoMaquina.horasFrecuencia}" styleClass="inputText" size="5" onkeypress="valNum(event);" />

                            </h:panelGrid>
                                <div align="center">
                                <a4j:commandButton styleClass="btn" value="Registrar" onclick="javascript:Richfaces.hideModalPanel('panelAgregarFrecuenciaMantenimientoMaquinaria');"
                                                   action="#{ManagedFrecuenciaMantenimientoMaquinaria.registrarFrecuenciaMantenimientoMaquina_action}" reRender="dataFrecuenciaMantenimientoMaquina"
                                                    />
                                <input type="button" value="Cancelar" onclick="javascript:Richfaces.hideModalPanel('panelAgregarFrecuenciaMantenimientoMaquinaria')" class="btn" />
                                </div>
                        </h:panelGroup>
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
                        <h:panelGroup id="contenidoEditarFrecuenciaMantenimentoMaquinaria">
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

