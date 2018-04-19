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
        <body >
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
                                <h:outputText value="Registro de Salidas a Almacen por Traspaso"  />
                            </f:facet>
                            <h:panelGrid columns="4" id="panelContenidoSalidaAlmacen">
                            
                            
                            <h:outputText value="Nro Salida:" styleClass="outputText2" />
                            <h:outputText value="#{ManagedSolicitudSalidaAlmacen.salidasAlmacen.nroSalidaAlmacen}" styleClass="outputText1" />

                            

                            <h:outputText value="Tipo de Salida:" styleClass="outputText2" />
                            <h:selectOneMenu  styleClass="inputText" value="#{ManagedSolicitudSalidaAlmacen.salidasAlmacen.tiposSalidasAlmacen.codTipoSalidaAlmacen}"  >
                                <f:selectItem itemLabel="POR TRASPASO" itemValue="3"  />
                            </h:selectOneMenu>

                            <h:outputText value="Almacén Destino Traspaso:" styleClass="outputText2" />
                            <h:selectOneMenu  styleClass="inputText" 
                                              value="#{ManagedSolicitudSalidaAlmacen.traspasos_solicitud.almacenDestino.codAlmacen}" 
                                              id="codAlmacenSolicitud" disabled="true">
                                <f:selectItems value="#{ManagedSolicitudSalidaAlmacen.almacenesList}"  />
                            </h:selectOneMenu>

                            
                            <h:outputText value="Area / Departamento:" styleClass="outputText2"  />
                            <h:selectOneMenu  styleClass="inputText" 
                                              value="#{ManagedSolicitudSalidaAlmacen.salidasAlmacen.areasEmpresa.codAreaEmpresa}" 
                                              id="codAreaEmpresa" 
                                              disabled="true"
                                              >
                                <f:selectItems value="#{ManagedSolicitudSalidaAlmacen.areasEmpresaList}"  />
                                <a4j:support event="onchange" action="#{ManagedSolicitudSalidaAlmacen.nroLote_change}" reRender="panelContenidoSalidaAlmacen"
                                             oncomplete="if(#{ManagedSolicitudSalidaAlmacen.mensaje!=''}){alert('#{ManagedSolicitudSalidaAlmacen.mensaje}');}" />
                            </h:selectOneMenu>
                           

                            <h:outputText value="Observaciones:" styleClass="outputText2" />
                            <h:inputTextarea  rows="5" cols="50" styleClass="outputText2" value="#{ManagedSolicitudSalidaAlmacen.salidasAlmacen.obsSalidaAlmacen}" />

                         

                             <h:panelGroup>
                             <a4j:commandButton value="Editar Item" styleClass="btn"
                             action="#{ManagedSolicitudSalidaAlmacen.editarCantidadSalidasAlmacenDetalle_action}" reRender="contenidoModificarCantidadMaterial"
                             oncomplete="if(validarSeleccion('form1:dataSalidasAlmacenDetalle')==true){Richfaces.showModalPanel('panelModificarCantidadMaterial');}" />
                            

                               <a4j:commandButton value="Eliminar Item" styleClass="btn"
                               oncomplete="if(validarSeleccion('form1:dataSalidasAlmacenDetalle')==false){return false;}"

                             action="#{ManagedSolicitudSalidaAlmacen.eliminarDetalle_action}" reRender="dataSalidasAlmacenDetalle" />
                             </h:panelGroup>

                        </h:panelGrid>
                    </rich:panel>
                    
                    
                        <div style="overflow:auto;height:200px;width:80%;text-align:center">
                       <rich:dataTable value="#{ManagedSolicitudSalidaAlmacen.salidasAlmacenDetalleList}"
                                    var="data"
                                    id="dataSalidasAlmacenDetalle"
                                    onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                    onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                    headerClass="headerClassACliente"                                    
                                    style="width:100%" binding="#{ManagedSolicitudSalidaAlmacen.salidasAlmacenDetalleDataTable}" >
                       
                       <rich:column>
                            <f:facet name="header">
                                <h:outputText value=""  />
                            </f:facet>
                            <h:selectBooleanCheckbox value="#{data.checked}" />
                        </rich:column>

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
                       
                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value="Cantidad"  />
                            </f:facet>
                            <h:outputText value="#{data.cantidadSalidaAlmacen}"  />
                        </rich:column>
                      
                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value="Unid."   />
                            </f:facet>
                            <h:outputText value="#{data.unidadesMedida.abreviatura}"  />
                        </rich:column>
                        
                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value="Cant. Disp."   />
                            </f:facet>
                            <h:outputText value="#{data.cantidadDisponible}"  />
                        </rich:column>
                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value=""   />
                            </f:facet>
                            <%-- action="#{ManagedSolicitudSalidaAlmacen.verDetalleEtiquetasSalidaAlmacen_action}" reRender="contenidoSalidaAlmacenDetalleEstado" --%>
                            <a4j:commandLink action="#{ManagedSolicitudSalidaAlmacen.verDetalleEtiquetasSalidaAlmacen_action}"
                            reRender="contenidoSalidaAlmacenDetalleEstado"
                                    oncomplete="Richfaces.showModalPanel('panelSalidaAlmacenDetalleEstado');cantAuxSalida();">
                                    <h:graphicImage url="../img/areasdependientes.png">
                                        
                                    </h:graphicImage>
                            </a4j:commandLink>
                            
                        </rich:column>

                    </rich:dataTable>
                    </div>
                    <rich:messages layout="table" />

                    <%-- <ws:datascroller fordatatable="dataAreasEmpresa"  />--%>
                    <br>
                    <div id="buttonGuardar">
                        <a4j:commandButton value="Guardar" styleClass="btn" ondblclick="alert('No utilice doble click');return false;"
                        action="#{ManagedSolicitudSalidaAlmacen.guardarSalidaAlmacen_action}"
                        onclick="bloquearGuardar();if(confirm('Esta seguro de realizar la Salida de Almacen?')==false){desBloquearGuardar();return false;}"
                        oncomplete="if(#{ManagedSolicitudSalidaAlmacen.mensaje!=''}){alert('#{ManagedSolicitudSalidaAlmacen.mensaje}');desBloquearGuardar();}else{window.location.href='navegadorSolicitudesSalidaAlmacen.jsf?data='+(new Date()).getTime().toString();}"
                        />
                    </div>
                    <center><img style="display:none"  src="../img/load.gif" id="progress"></center>
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

             <rich:modalPanel id="panelSalidaAlmacenDetalleEstado" minHeight="400"  minWidth="1020"
                                     height="400" width="1020"
                                     zindex="200"
                                     headerClass="headerClassACliente"
                                     resizeable="false" style="overflow :auto" >
                        <f:facet name="header">
                            <h:outputText value="Detalle Item"/>
                        </f:facet>
                        <a4j:form id="form2">
                        <h:panelGroup id="contenidoSalidaAlmacenDetalleEstado">
                            <div style="overflow:auto;height:290px;text-align:center">
                                <h:panelGrid columns="4" >
                                    <h:outputText value="Cantidad de Salida:" styleClass="outputText1" />
                                    <h:outputText value="#{ManagedSolicitudSalidaAlmacen.salidasAlmacenDetalle.cantidadSalidaAlmacen}" styleClass="outputText1" id="cantTotalSalidaFisica" />
                                    <h:outputText value="Item:" styleClass="outputText1" />
                                    <h:outputText value="#{ManagedSolicitudSalidaAlmacen.salidasAlmacenDetalle.materiales.nombreMaterial}" styleClass="outputText1" />
                                </h:panelGrid>
                                <rich:dataTable value="#{ManagedSolicitudSalidaAlmacen.salidasAlmacenDetalle.salidasAlmacenDetalleIngresoList}" var="data"
                                    id="dataSalidaAlmacenDetalleIngreso"
                                    onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                    onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                    headerClass="headerClassACliente">
                                         <rich:column>
                                            <f:facet name="header">
                                                <h:outputText value="Fecha Venc."  />
                                            </f:facet>
                                            <h:outputText  value="#{data.ingresosAlmacenDetalleEstado.fechaVencimiento}">
                                                <f:convertDateTime pattern="dd/MM/yyyy" />
                                            </h:outputText>
                                        </rich:column>
                                        <rich:column>
                                    <f:facet name="header">
                                        <h:outputText value="Estado Material"  />
                                    </f:facet>
                                    <h:outputText  value="#{data.ingresosAlmacenDetalleEstado.estadosMaterial.nombreEstadoMaterial}"/>
                                </rich:column>


                                <rich:column>
                                    <f:facet name="header">
                                        <h:outputText value="Nro. Ingreso"  />
                                    </f:facet>
                                    <h:outputText  value="#{data.ingresosAlmacenDetalleEstado.ingresosAlmacen.nroIngresoAlmacen}"/>
                                </rich:column>


                                <rich:column>
                                    <f:facet name="header">
                                        <h:outputText value="Seccion"  />
                                    </f:facet>
                                    <h:outputText  value="#{data.ingresosAlmacenDetalleEstado.ingresosAlmacenDetalle.secciones.nombreSeccion}"/>
                                </rich:column>

                                <rich:column>
                                    <f:facet name="header">
                                        <h:outputText value="Etiqueta"  />
                                    </f:facet>
                                    <h:outputText  value="#{data.ingresosAlmacenDetalleEstado.etiqueta}"/>
                                </rich:column>

                                <rich:column>
                                    <f:facet name="header">
                                        <h:outputText value="Empaque Secundario"  />
                                    </f:facet>
                                    <h:outputText  value="#{data.ingresosAlmacenDetalleEstado.empaqueSecundarioExterno.nombreEmpaqueSecundarioExterno}"/>
                                </rich:column>

                                <rich:column>
                                    <f:facet name="header">
                                        <h:outputText value="Lote Proveedor"  />
                                    </f:facet>
                                    <h:outputText  value="#{data.ingresosAlmacenDetalleEstado.loteMaterialProveedor}"/>
                                </rich:column>


                                <rich:column>
                                    <f:facet name="header">
                                        <h:outputText value="Costo"  />
                                    </f:facet>
                                    <h:outputText  value="#{data.ingresosAlmacenDetalleEstado.ingresosAlmacenDetalle.costoUnitario}"/>
                                </rich:column>


                                <rich:column>
                                    <f:facet name="header">
                                        <h:outputText value="Pasillo"  />
                                    </f:facet>
                                    <h:outputText  value="#{data.ingresosAlmacenDetalleEstado.estanteAlmacen.nombreEstante}"/>
                                </rich:column>
                                <rich:column>
                                    <f:facet name="header">
                                        <h:outputText value="Estante"  />
                                    </f:facet>
                                    <h:outputText  value="#{data.ingresosAlmacenDetalleEstado.fila}"/>
                                </rich:column>
                                <rich:column>
                                    <f:facet name="header">
                                        <h:outputText value="Ubicacion"  />
                                    </f:facet>
                                    <h:outputText  value="#{data.ingresosAlmacenDetalleEstado.columna}"/>
                                </rich:column>

                                <rich:column>
                                    <f:facet name="header">
                                        <h:outputText value="Cantidad Fis."  />
                                    </f:facet>
                                    <h:outputText  value="#{data.cantidad}"/>
                                </rich:column>


                                <rich:column>
                                    <f:facet name="header">
                                        <h:outputText value="Cantidad Disponible"  />
                                    </f:facet>
                                    <h:outputText value="#{data.cantidadDisponible}"  />
                                </rich:column>
                                <rich:column>
                                    <f:facet name="header">
                                        <h:outputText value="Cant. a Sacar"  />
                                    </f:facet>
                                    <h:inputText value="#{data.cantidadSacar}"  styleClass="inputText" onkeyup="validaCantidad(this,#{data.cantidadSacar});" onkeypress="valNumPositivo(event);" /><%--onkeyup="validaCantidad(this);" onkeypress="valNum(event);"--%>
                                    
                                </rich:column>
                                </rich:dataTable>
                            </div>
                             <div align="center">
                                 <a4j:commandButton styleClass="btn" value="Registrar"
                                action="#{ManagedSolicitudSalidaAlmacen.guardarSalidasAlmacenDetalleIngreso_action}"
                                                   reRender="dataSalidasAlmacenDetalle"
                                                   oncomplete="if(#{ManagedSolicitudSalidaAlmacen.mensaje!=''}){alert('#{ManagedSolicitudSalidaAlmacen.mensaje}')}else{javascript:Richfaces.hideModalPanel('panelSalidaAlmacenDetalleEstado')}"
                                                    />
                                <input type="button" value="Cancelar" class="btn" onclick="javascript:Richfaces.hideModalPanel('panelSalidaAlmacenDetalleEstado')" />
                                </div>
                        </h:panelGroup>
                        </a4j:form>
            </rich:modalPanel>

            

            <rich:modalPanel id="panelAgregarMaterial" minHeight="250"  minWidth="650"
                                     height="250" width="650"
                                     zindex="200"
                                     headerClass="headerClassACliente"
                                     resizeable="false" style="overflow :auto" >
                        <f:facet name="header">
                            <h:outputText value="Agregar Material"/>
                        </f:facet>
                        <a4j:form id="form3">
                        <h:panelGroup id="contenidoAgregarMaterial">
                            <h:panelGrid columns="3">
                                <h:outputText value="Capitulo" styleClass="outputText1" />
                                <h:outputText value="::" styleClass="outputText1" />
                                <h:selectOneMenu value="#{ManagedSolicitudSalidaAlmacen.salidasAlmacenDetalleAgregar.materiales.grupos.capitulos.codCapitulo}" styleClass="inputText" >
                                    <f:selectItems value="#{ManagedSolicitudSalidaAlmacen.capitulosList}" />
                                    <a4j:support event="onchange" action="#{ManagedSolicitudSalidaAlmacen.cargarGrupos}" reRender="grupo"  />
                                </h:selectOneMenu>




                                <h:outputText value="Grupo" styleClass="outputText1" />
                                <h:outputText value="::" styleClass="outputText1" />
                                <h:selectOneMenu value="#{ManagedSolicitudSalidaAlmacen.salidasAlmacenDetalleAgregar.materiales.grupos.codGrupo}" styleClass="inputText" id="grupo" >
                                    <f:selectItems value="#{ManagedSolicitudSalidaAlmacen.gruposList}"  />
                                    <a4j:support event="onchange" action="#{ManagedSolicitudSalidaAlmacen.grupos_change}" reRender="material"  />
                                </h:selectOneMenu>

                                <h:outputText value="Material" styleClass="outputText1" />
                                <h:outputText value="::" styleClass="outputText1" />
                                <h:panelGroup>
                                    <h:selectOneMenu value="#{ManagedSolicitudSalidaAlmacen.salidasAlmacenDetalleAgregar.materiales.codMaterial}" styleClass="inputText" id="material" >
                                        <f:selectItems value="#{ManagedSolicitudSalidaAlmacen.materialesList}"  />
                                    </h:selectOneMenu>
                                    <a4j:commandLink  action="#{ManagedSolicitudSalidaAlmacen.detallarCantidadSalida_action}" reRender="detalleSalidaMaterial">
                                        <h:graphicImage url="../img/actualizar1.jpg" />
                                    </a4j:commandLink>
                                </h:panelGroup>                                
                            </h:panelGrid>

                            <h:panelGrid columns="3" id="detalleSalidaMaterial" headerClass="headerClassACliente" style="width:100%" >
                                <f:facet name="header" >
                                    <h:outputText value="Detalle de Salida de Material" />
                                </f:facet>
                                
                                <h:outputText value="Nombre Material" styleClass="outputText1" />
                                <h:outputText value="::" styleClass="outputText1" />                                
                                <h:outputText value="#{ManagedSolicitudSalidaAlmacen.salidasAlmacenDetalleAgregar.materiales.nombreMaterial}" styleClass="outputText1" />

                                <h:outputText value="Cantidad Disponible" styleClass="outputText1" />
                                <h:outputText value="::" styleClass="outputText1" />                                
                                <h:outputText value="#{ManagedSolicitudSalidaAlmacen.salidasAlmacenDetalleAgregar.cantidadDisponible}" styleClass="outputText1" />

                                <h:outputText value="Cantidad Salida" styleClass="outputText1" />
                                <h:outputText value="::" styleClass="outputText1" />
                                <h:panelGroup>
                                    <h:inputText value="#{ManagedSolicitudSalidaAlmacen.salidasAlmacenDetalleAgregar.cantidadSalidaAlmacen}" styleClass="inputText" onkeyup="calculaEquivalencia(this)" />
                                <h:outputText value="#{ManagedSolicitudSalidaAlmacen.salidasAlmacenDetalleAgregar.equivalencias.unidadesMedida1.nombreUnidadMedida}" styleClass="outputText1" />
                                </h:panelGroup>

                                <h:outputText value="Equivalencia" styleClass="outputText1" />
                                <h:outputText value="::" styleClass="outputText1" />
                                <h:panelGroup>
                                    <h:inputHidden value="#{ManagedSolicitudSalidaAlmacen.salidasAlmacenDetalleAgregar.equivalencias.valorEquivalencia}" id="valorEquivalencia" />
                                    <h:inputText value="#{ManagedSolicitudSalidaAlmacen.salidasAlmacenDetalleAgregar.cantidadSalidaAlmacenEquivalente}" styleClass="inputText" id="cantidadEquivalencia" />
                                    <h:outputText value="#{ManagedSolicitudSalidaAlmacen.salidasAlmacenDetalleAgregar.equivalencias.unidadesMedida2.nombreUnidadMedida}" styleClass="outputText1" />
                                </h:panelGroup>
                                
                            </h:panelGrid>

                            

                                <div align="center">
                                <a4j:commandButton styleClass="btn" value="Guardar"
                                action="#{ManagedSolicitudSalidaAlmacen.guardarAgregarSalidaAlmacenDetalle_action}"
                                oncomplete="if(#{ManagedSolicitudSalidaAlmacen.mensaje!=''}){alert('#{ManagedSolicitudSalidaAlmacen.mensaje}')}else{javascript:Richfaces.hideModalPanel('panelAgregarMaterial')}"
                                            reRender="dataSalidasAlmacenDetalle"
                                                    />
                                <input type="button" value="Cancelar" onclick="javascript:Richfaces.hideModalPanel('panelAgregarMaterial')" class="btn" />
                                </div>
                        </h:panelGroup>
                        </a4j:form>
            </rich:modalPanel>
            
            
            <!-- panel modificar cantidad de material  -->
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
                                    <h:inputText value="#{ManagedSolicitudSalidaAlmacen.salidasAlmacenDetalle.cantidadSalidaAlmacen}"  styleClass="inputText" id="cantidadSalidaAlmacen"  onkeypress="valNumPositivo(event);"  onkeyup="validaCantidadSalidaAlmacenDetalle1(this)" />
                                <h:outputText value="#{ManagedSolicitudSalidaAlmacen.salidasAlmacenDetalle.equivalencias.unidadesMedida1.nombreUnidadMedida}" styleClass="outputText1" />
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
                                action="#{ManagedSolicitudSalidaAlmacen.guardarEdicionCantidadMaterial_action}"
                                reRender="dataSalidasAlmacenDetalle"  onclick="javascript:Richfaces.hideModalPanel('panelModificarCantidadMaterial')"
                                  />
                                
                                <input type="button" value="Cancelar" onclick="javascript:Richfaces.hideModalPanel('panelModificarCantidadMaterial')" class="btn" />
                                </div>
                        </h:panelGroup>
                        </a4j:form>
                        </div>
            </rich:modalPanel>


             <rich:modalPanel id="panelAgregarItem" minHeight="450"  minWidth="850"
                                     height="450" width="850"
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
                                <h:selectOneMenu value="#{ManagedSolicitudSalidaAlmacen.buscarMaterial.grupos.capitulos.codCapitulo}" styleClass="inputText" >
                                    <f:selectItems value="#{ManagedSolicitudSalidaAlmacen.capitulosList}"  />
                                    <a4j:support event="onchange" action="#{ManagedSolicitudSalidaAlmacen.capitulos_change}" reRender="codGrupo" />
                                </h:selectOneMenu>

                                <h:outputText value="Grupo:" styleClass="tituloCampo1" />
                                <h:selectOneMenu value="#{ManagedSolicitudSalidaAlmacen.buscarMaterial.grupos.codGrupo}" styleClass="inputText" id="codGrupo" >
                                    <f:selectItems value="#{ManagedSolicitudSalidaAlmacen.gruposList}"  />
                                </h:selectOneMenu>

                                <h:outputText value="Item:" styleClass="tituloCampo1" />

                                <h:inputText value="#{ManagedSolicitudSalidaAlmacen.buscarMaterial.nombreMaterial}" styleClass="inputText" size="50"  />

                                <a4j:commandButton value="Buscar" action="#{ManagedSolicitudSalidaAlmacen.buscarMaterial_action}" styleClass="btn"  reRender="dataBuscarMaterial"  />

                           </h:panelGrid>
                              <br/>
                            <div style="overflow:auto;height:200px;width:100%;text-align:center">
                            <rich:dataTable value="#{ManagedSolicitudSalidaAlmacen.materialesBuscarList}" var="data"
                                    id="dataBuscarMaterial"
                                    onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                    onRowMouseOver="this.style.backgroundColor='#DDE3E4';"                                    
                                    headerClass="headerClassACliente"
                                    binding="#{ManagedSolicitudSalidaAlmacen.materialesBuscarDataTable}" >


                                <rich:column style="background-color: #{data.colorFila}">
                                    <f:facet name="header">
                                        <h:outputText value="Material"  />
                                    </f:facet>
                                    <a4j:commandLink  action="#{ManagedSolicitudSalidaAlmacen.detallarCantidadSalida_action}" styleClass="#{data.colorFila}"  reRender="nombreMaterialAgregar,detalleSalidaMaterial,dataBuscarMaterial" >
                                        <h:outputText  value="#{data.nombreMaterial}" styleClass="outputText2" />
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
                            <br/>
                             <b>
                             <h:panelGroup id="nombreMaterialAgregar">
                             <h:outputText value="Nombre Material: " styleClass="outputText1" />
                             <h:outputText value="#{ManagedSolicitudSalidaAlmacen.salidasAlmacenDetalleAgregar.materiales.nombreMaterial}" styleClass="outputText1" />
                             </h:panelGroup>
                             </b>
                             <br/>


                            <h:panelGrid columns="11" id="detalleSalidaMaterial" headerClass="headerClassACliente" style="width:100%" >
                                <f:facet name="header" >
                                    <h:outputText value="Detalle de Salida de Material" />
                                </f:facet>
                                <h:outputText value="Estado Material" styleClass="outputText1" />
                                <h:selectOneMenu value="#{ManagedSolicitudSalidaAlmacen.ingresosAlmacenDetalleEstado.estadosMaterial.codEstadoMaterial}" styleClass="inputText" >
                                    <f:selectItems value="#{ManagedSolicitudSalidaAlmacen.estadosMaterialList}" />
                                    <a4j:support event="onchange" action="#{ManagedSolicitudSalidaAlmacen.estadoMaterial_change}" reRender="nombreMaterialAgregar,detalleSalidaMaterial,dataBuscarMaterial" />
                                </h:selectOneMenu>


                                <h:outputText value="Cantidad Disponible: " styleClass="outputText1" />
                                <h:panelGroup>
                                    <h:outputText value="#{ManagedSolicitudSalidaAlmacen.salidasAlmacenDetalleAgregar.cantidadDisponible}"  id="cantidadDisponible" styleClass="outputText1" >
                                        
                                    </h:outputText>
                                    <h:outputText value="[" styleClass="outputText1" />
                                    <h:outputText value="#{ManagedSolicitudSalidaAlmacen.salidasAlmacenDetalleAgregar.unidadesMedida.abreviatura}" styleClass="outputText1" />
                                    <h:outputText value="]" styleClass="outputText1" />
                                </h:panelGroup>

                                <h:outputText value="Cantidad Salida: " styleClass="outputText1" />
                                <h:panelGroup>
                                    <h:inputText value="#{ManagedSolicitudSalidaAlmacen.salidasAlmacenDetalleAgregar.cantidadSalidaAlmacen}" styleClass="inputText" id="cantidadSalidaAlmacen" onkeypress="valNum(this)" onkeyup="validaCantidadSalidaAlmacenDetalle(this)" />
                                    <h:outputText value="[" styleClass="outputText1" />
                                    <h:outputText value="#{ManagedSolicitudSalidaAlmacen.salidasAlmacenDetalleAgregar.equivalencias.unidadesMedida1.abreviatura}" styleClass="outputText1" />
                                    <h:outputText value="]" styleClass="outputText1" />
                                </h:panelGroup>

                                <h:outputText value="Equivalencia: " styleClass="outputText1" />
                                <h:panelGroup>
                                    <h:inputHidden value="#{ManagedSolicitudSalidaAlmacen.salidasAlmacenDetalleAgregar.equivalencias.valorEquivalencia}" id="valorEquivalencia" />
                                    <h:inputText value="#{ManagedSolicitudSalidaAlmacen.salidasAlmacenDetalleAgregar.cantidadSalidaAlmacenEquivalente}" styleClass="inputText" id="cantidadEquivalencia" />
                                    <h:outputText value="[" styleClass="outputText1" />
                                    <h:outputText value="#{ManagedSolicitudSalidaAlmacen.salidasAlmacenDetalleAgregar.equivalencias.unidadesMedida2.abreviatura}" styleClass="outputText1" />
                                    <h:outputText value="]" styleClass="outputText1" />
                                </h:panelGroup>

                            </h:panelGrid>
                            
                            
                            


                            </h:panelGroup>
                            </div>

                                <div align="center">
                                <a4j:commandButton styleClass="btn" value="Guardar"
                                action="#{ManagedSolicitudSalidaAlmacen.guardarAgregarSalidaAlmacenDetalle_action}"
                                oncomplete="if(#{ManagedSolicitudSalidaAlmacen.mensaje!=''}){alert('#{ManagedSolicitudSalidaAlmacen.mensaje}')}else{javascript:Richfaces.hideModalPanel('panelAgregarItem')}"
                                            reRender="dataSalidasAlmacenDetalle"
                                                    />

                                <input type="button" value="Cancelar" class="btn" onclick="javascript:Richfaces.hideModalPanel('panelAgregarItem')" />
                                </div>

                        </a4j:form>
            </rich:modalPanel>
             

            <a4j:status id="statusPeticion"
                                onstart="Richfaces.showModalPanel('ajaxLoadingModalBox',{width:100, top:100})"
                                onstop="Richfaces.hideModalPanel('ajaxLoadingModalBox')"
                                >
           </a4j:status>
          <rich:modalPanel id="ajaxLoadingModalBox" minHeight="100"
                                     minWidth="200" height="80" width="400" zindex="200" onshow="window.focus();">

                        <div align="center">
                            <h:graphicImage value="../img/load2.gif" /><br/>
                            <h:outputText value="Procesando..." />
                        </div>
          </rich:modalPanel>

         
      

        </body>
    </html>

</f:view>

