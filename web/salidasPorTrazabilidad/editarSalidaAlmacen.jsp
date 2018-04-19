
asdfasdfasdfsad
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
       function validaCantidad(obj){
           //var cantidadDisponible=parseFloat(obj.parentNode.parentNode.getElementsByTagName('td')[9].getElementsByTagName('input')[0].value);
           //alert (obj.parentNode.parentNode.getElementsByTagName('td')[9].innerHTML);
           //alert(obj.value.substr(0,obj.value.length-1));
           var cantidadDisponible =  obj.parentNode.parentNode.getElementsByTagName('td')[12].innerHTML;
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
               var col13 = obj.parentNode.parentNode.getElementsByTagName('td')[13];
               //obj.value = col10.getElementsByTagName("input")[1].value;
               obj.value=0;
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
                        var cel13=cellsElement[13];
                        var cantidadParcial=cel13.getElementsByTagName('input')[0];
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
            A4J.AJAX.onError = function(req,status,message){
            window.alert("Ocurrio un error: "+message);
            }
            A4J.AJAX.onExpired = function(loc,expiredMsg){
            if(window.confirm("Ocurrio un error al momento realizar la transaccion Intente nuevamente \n Error: "+expiredMsg+" location: "+loc)){
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

                    <h:outputText value="#{ManagedRegistroSalidaAlmacen.cargarContenidoEditarSalidasAlmacen}"/>
                    <br><br>
                        
                    <rich:panel id="panelEstadoCuentas" styleClass="panelBuscar" style="top:50px;left:50px;width:700px;">
                    <f:facet name="header">
                        <h:outputText value="<div   onmouseover=\"this.style.cursor='move'\" onmousedown=\"comienzoMovimiento(event, 'form1:panelEstadoCuentas');\"  >Buscar<div   style=\"margin-left:550px;\"   onclick=\"document.getElementById('form1:panelEstadoCuentas').style.visibility='hidden';document.getElementById('panelsuper').style.visibility='hidden';\" onmouseover=\"this.style.cursor='hand'\"   >Cerrar</div> </div> "
                              escape="false" />
                    </f:facet>
                    </rich:panel>

                <rich:panel style="width:100%"  headerClass="headerClassACliente">
                            <f:facet name="header">
                                <h:outputText value="Editar Salidas de Almacen"  />
                            </f:facet>
                        <h:panelGrid columns="4" >
                            
                            
                            <h:outputText value="Nro Salida:" styleClass="outputText2" />
                            <h:outputText value="#{ManagedRegistroSalidaAlmacen.salidasAlmacen.nroSalidaAlmacen}" styleClass="outputText1" />

                            <h:outputText value="Area / Departamento:" styleClass="outputText2" />
                            <h:selectOneMenu  styleClass="inputText" value="#{ManagedRegistroSalidaAlmacen.salidasAlmacen.areasEmpresa.codAreaEmpresa}" id="codAreaEmpresa" >
                                <f:selectItems value="#{ManagedRegistroSalidaAlmacen.areasEmpresaList}"  />
                                <a4j:support event="onchange" action="#{ManagedRegistroSalidaAlmacen.nroLote_change}" reRender="codAreaEmpresa,codMaquinaria"
                                             oncomplete="if(#{ManagedRegistroSalidaAlmacen.mensaje!=''}){alert('#{ManagedRegistroSalidaAlmacen.mensaje}');}" />
                            </h:selectOneMenu>

                            <h:outputText value="Tipo de Salida:" styleClass="outputText2" />
                            <h:selectOneMenu  styleClass="inputText" value="#{ManagedRegistroSalidaAlmacen.salidasAlmacen.tiposSalidasAlmacen.codTipoSalidaAlmacen}"   >
                                <f:selectItems value="#{ManagedRegistroSalidaAlmacen.tiposSalidasAlmacenList}"  />
                            </h:selectOneMenu>

                            <h:outputText value="Nro Lote:" styleClass="outputText2" />
                            <h:inputText value="#{ManagedRegistroSalidaAlmacen.salidasAlmacen.codLoteProduccion}" styleClass="inputText" id="codLoteProduccion" >
                              <%--inicio ale validacion--%>
                              <a4j:support event="onkeyup" action="#{ManagedRegistroSalidaAlmacen.nroLote_change}" reRender="codAreaEmpresa,codProducto"
                                oncomplete="if(#{ManagedRegistroSalidaAlmacen.mensaje!=''}){alert('#{ManagedRegistroSalidaAlmacen.mensaje}');}if(#{ManagedRegistroSalidaAlmacen.productoHabilitado==true}){document.getElementById('form1:codProducto').disabled=true;}else{document.getElementById('form1:codProducto').disabled=false;}"/>
                              <%--final ale validacion--%>
                            </h:inputText>

                            <%--  --%>

                            <h:outputText value="Producto:" styleClass="outputText2" rendered="#{ManagedRegistroSalidaAlmacen.usuario.almacenesGlobal.codAlmacen != '14'}" />
                            <h:outputText value="Maquinaria:" styleClass="outputText2"  rendered="#{ManagedRegistroSalidaAlmacen.usuario.almacenesGlobal.codAlmacen eq '14'}" />
                            <h:panelGroup id="producto">
                                <h:selectOneMenu  styleClass="inputText" value="#{ManagedRegistroSalidaAlmacen.salidasAlmacen.componentesProd.codCompprod}" id="codProducto" rendered="#{ManagedRegistroSalidaAlmacen.usuario.almacenesGlobal.codAlmacen != '14'}" >
                                    <f:selectItems value="#{ManagedRegistroSalidaAlmacen.componentesProdList}"  />
                                    <a4j:support event="onchange" action="#{ManagedRegistroSalidaAlmacen.producto_changed}" reRender="presentacionProducto,producto" oncomplete="if(#{ManagedRegistroSalidaAlmacen.mensaje!=''}){alert('#{ManagedRegistroSalidaAlmacen.mensaje}');}" timeout="7200" />
                                </h:selectOneMenu>
                                <a4j:commandLink  action="#{ManagedRegistroSalidaAlmacen.detallarMateriales_action}" reRender="dataSalidasAlmacenDetalle" timeout="7200" rendered="#{ManagedRegistroSalidaAlmacen.usuario.almacenesGlobal.codAlmacen != '14'}">
                                    <h:graphicImage url="../img/actualizar1.jpg" />
                                </a4j:commandLink>

                                <h:selectOneMenu  styleClass="inputText" value="#{ManagedRegistroSalidaAlmacen.salidasAlmacen.maquinaria.codMaquina}"  id="codMaquinaria" rendered="#{ManagedRegistroSalidaAlmacen.usuario.almacenesGlobal.codAlmacen eq '14'}"  >
                                    <f:selectItem itemValue="0" itemLabel="-NINGUNO-" />
                                    <f:selectItems value="#{ManagedRegistroSalidaAlmacen.maquinariasList}"  />
                                </h:selectOneMenu>
                            </h:panelGroup>

                            <h:outputText value="Presentacion:" styleClass="tituloCampo1" rendered="#{ManagedRegistroSalidaAlmacen.usuario.almacenesGlobal.codAlmacen != '14'}"  />
                            <h:selectOneMenu  styleClass="inputText" value="#{ManagedRegistroSalidaAlmacen.salidasAlmacen.presentacionesProducto.codPresentacion}"  id="presentacionProducto"  rendered="#{ManagedRegistroSalidaAlmacen.usuario.almacenesGlobal.codAlmacen != '14'}" >
                                <f:selectItems value="#{ManagedRegistroSalidaAlmacen.presentacionesList}"  />
                            </h:selectOneMenu>

                            <%--  --%>

                            <h:outputText value="Observaciones:" styleClass="outputText2" />
                            <h:inputTextarea  rows="5" cols="50" styleClass="outputText2" value="#{ManagedRegistroSalidaAlmacen.salidasAlmacen.obsSalidaAlmacen}" />

                            <h:outputText value="Orden de Trabajo:" styleClass="outputText2" />
                            <h:inputText value="#{ManagedRegistroSalidaAlmacen.salidasAlmacen.ordenTrabajo}" styleClass="inputText" />

                            
                             

                             <a4j:commandButton value="Agregar Item" styleClass="btn"
                             onclick="Richfaces.showModalPanel('panelAgregarItem')" action="#{ManagedRegistroSalidaAlmacen.agregarItem_action}"
                             reRender="contenidoAgregarItem"
                              />

                             <h:panelGroup>
                             <a4j:commandButton value="Editar Item" styleClass="btn"
                             action="#{ManagedRegistroSalidaAlmacen.editarCantidadSalidasAlmacenDetalle_action}" reRender="contenidoModificarCantidadMaterial"
                             onclick="if(validarSeleccion('form1:dataSalidasAlmacenDetalle')==true){Richfaces.showModalPanel('panelModificarCantidadMaterial');}" />
                            

                               <a4j:commandButton value="Eliminar Item" styleClass="btn"
                               onclick="if(validarSeleccion('form1:dataSalidasAlmacenDetalle')==false){return false;}"

                             action="#{ManagedRegistroSalidaAlmacen.eliminarDetalle_action}" reRender="dataSalidasAlmacenDetalle" />
                             </h:panelGroup>
                             

                        </h:panelGrid>
                    </rich:panel>
                    
                    
                        <div style="overflow:auto;height:200px;width:80%;text-align:center">
                       <rich:dataTable value="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalleList}"
                                    var="data"
                                    id="dataSalidasAlmacenDetalle"
                                    onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                    onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                    headerClass="headerClassACliente"                                    
                                    style="width:100%" binding="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalleDataTable}" >
                       
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
                            <%-- oncomplete="cantAuxSalida();" action="#{ManagedRegistroSalidaAlmacen.verDetalleEtiquetasSalidaAlmacen_action}" reRender="contenidoSalidaAlmacenDetalleEstado" --%>
                            <a4j:commandLink onclick="Richfaces.showModalPanel('panelSalidaAlmacenDetalleEstado')" action="#{ManagedRegistroSalidaAlmacen.verDetalleEtiquetasSalidaAlmacen_action}" reRender="contenidoSalidaAlmacenDetalleEstado"
                                    >
                                    <h:graphicImage url="../img/areasdependientes.png">
                                        
                                    </h:graphicImage>
                            </a4j:commandLink>
                            
                        </rich:column>

                    </rich:dataTable>
                    </div>
                    <rich:messages layout="table" />

                    <%-- <ws:datascroller fordatatable="dataAreasEmpresa"  />--%>
                    <br>
                    <a4j:commandButton value="Guardar" styleClass="btn"                    
                    action="#{ManagedRegistroSalidaAlmacen.guardarEditarSalidasAlmacen_action}"
                    onclick="if(confirm('Esta seguro de realizar la Salida de Almacen?')==false){return false;}"
                    oncomplete="if(#{ManagedRegistroSalidaAlmacen.mensaje!=''}){alert('#{ManagedRegistroSalidaAlmacen.mensaje}')}else{location='navegadorSalidasAlmacen.jsf'}"                    
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
                                    <h:outputText value="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalle.cantidadSalidaAlmacen}" styleClass="outputText1" id="cantTotalSalidaFisica" />
                                    <h:outputText value="Item:" styleClass="outputText1" />
                                    <h:outputText value="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalle.materiales.nombreMaterial}" styleClass="outputText1" />
                                </h:panelGrid>
                            
                            <rich:dataTable value="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalle.salidasAlmacenDetalleIngresoList}" var="data"
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

                                <rich:column style="background-color:'#{data.colorFila}'">
                                    <f:facet name="header">
                                        <h:outputText value="Estante"  />
                                    </f:facet>
                                    <h:outputText  value="#{data.ingresosAlmacenDetalleEstado.estanteAlmacen.nombreEstante}"/>
                                </rich:column>
                                <rich:column style="background-color:'#{data.colorFila}'">
                                    <f:facet name="header">
                                        <h:outputText value="fila"  />
                                    </f:facet>
                                    <h:outputText  value="#{data.ingresosAlmacenDetalleEstado.fila}"/>
                                </rich:column>
                                <rich:column style="background-color:'#{data.colorFila}'">
                                    <f:facet name="header">
                                        <h:outputText value="columna"  />
                                    </f:facet>
                                    <h:outputText  value="#{data.ingresosAlmacenDetalleEstado.columna}"/>
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
                                    <h:inputText styleClass="inputText"  value="#{data.cantidadSacar}" onkeyup="validaCantidad(this);" onkeypress="valNumPositivo(event);"/>
                                    <%--h:inputHidden value="" id="cantSacarAux"  /--%>
                                </rich:column>

                            </rich:dataTable>
                            </div>
                            </h:panelGroup>


                                <div align="center">
                                <a4j:commandButton styleClass="btn" value="Registrar"
                                                   action="#{ManagedRegistroSalidaAlmacen.guardarSalidasAlmacenDetalleIngreso_action}"
                                                   reRender="dataSalidasAlmacenDetalle"
                                                   oncomplete="if(#{ManagedRegistroSalidaAlmacen.mensaje!=''}){alert('#{ManagedRegistroSalidaAlmacen.mensaje}')}else{javascript:Richfaces.hideModalPanel('panelSalidaAlmacenDetalleEstado')}"                                                   
                                                    />
                                <input type="button" value="Cancelar" class="btn" onclick="javascript:Richfaces.hideModalPanel('panelSalidaAlmacenDetalleEstado')" />
                                </div>
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
                                <h:selectOneMenu value="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalleAgregar.materiales.grupos.capitulos.codCapitulo}" styleClass="inputText" >
                                    <f:selectItems value="#{ManagedRegistroSalidaAlmacen.capitulosList}" />
                                    <a4j:support event="onchange" action="#{ManagedRegistroSalidaAlmacen.cargarGrupos}" reRender="grupo"  />
                                </h:selectOneMenu>




                                <h:outputText value="Grupo" styleClass="outputText1" />
                                <h:outputText value="::" styleClass="outputText1" />
                                <h:selectOneMenu value="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalleAgregar.materiales.grupos.codGrupo}" styleClass="inputText" id="grupo" >
                                    <f:selectItems value="#{ManagedRegistroSalidaAlmacen.gruposList}"  />
                                    <a4j:support event="onchange" action="#{ManagedRegistroSalidaAlmacen.grupos_change}" reRender="material"  />
                                </h:selectOneMenu>

                                <h:outputText value="Material" styleClass="outputText1" />
                                <h:outputText value="::" styleClass="outputText1" />
                                <h:panelGroup>
                                    <h:selectOneMenu value="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalleAgregar.materiales.codMaterial}" styleClass="inputText" id="material" >
                                        <f:selectItems value="#{ManagedRegistroSalidaAlmacen.materialesList}"  />
                                    </h:selectOneMenu>
                                    <a4j:commandLink  action="#{ManagedRegistroSalidaAlmacen.detallarCantidadSalida_action}" reRender="detalleSalidaMaterial">
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
                                <h:outputText value="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalleAgregar.materiales.nombreMaterial}" styleClass="outputText1" />

                                <h:outputText value="Cantidad Disponible" styleClass="outputText1" />
                                <h:outputText value="::" styleClass="outputText1" />                                
                                <h:outputText value="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalleAgregar.cantidadDisponible}" styleClass="outputText1" />

                                <h:outputText value="Cantidad Salida" styleClass="outputText1" />
                                <h:outputText value="::" styleClass="outputText1" />
                                <h:panelGroup>
                                    <h:inputText value="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalleAgregar.cantidadSalidaAlmacen}" styleClass="inputText" onkeypress="valNumPositivo(event);" onkeyup="calculaEquivalencia(this)" />
                                <h:outputText value="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalleAgregar.equivalencias.unidadesMedida1.nombreUnidadMedida}" styleClass="outputText1" />
                                </h:panelGroup>

                                <h:outputText value="Equivalencia" styleClass="outputText1" />
                                <h:outputText value="::" styleClass="outputText1" />
                                <h:panelGroup>
                                    <h:inputHidden value="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalleAgregar.equivalencias.valorEquivalencia}" id="valorEquivalencia" />
                                    <h:inputText value="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalleAgregar.cantidadSalidaAlmacenEquivalente}" styleClass="inputText" id="cantidadEquivalencia" />
                                    <h:outputText value="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalleAgregar.equivalencias.unidadesMedida2.nombreUnidadMedida}" styleClass="outputText1" />
                                </h:panelGroup>
                                
                            </h:panelGrid>

                            

                                <div align="center">
                                <a4j:commandButton styleClass="btn" value="Guardar"
                                action="#{ManagedRegistroSalidaAlmacen.guardarAgregarSalidaAlmacenDetalle_action}"
                                oncomplete="if(#{ManagedRegistroSalidaAlmacen.mensaje!=''}){alert('#{ManagedRegistroSalidaAlmacen.mensaje}')}else{javascript:Richfaces.hideModalPanel('panelAgregarMaterial')}"
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
                                <h:outputText value="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalle.materiales.nombreMaterial}" styleClass="outputText1" />

                                <h:outputText value="Cantidad Disponible" styleClass="outputText1" />
                                <h:outputText value="::" styleClass="outputText1" />
                                <h:outputText value="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalle.cantidadDisponible}" id="cantidadDisponible"  styleClass="outputText1" />

                                <h:outputText value="Cantidad de Salida" styleClass="outputText1" />
                                <h:outputText value="::" styleClass="outputText1" />
                                <h:panelGroup>
                                    <h:inputText value="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalle.cantidadSalidaAlmacen}"  styleClass="inputText" id="cantidadSalidaAlmacen"  onkeypress="valNum(this)"  onkeyup="validaCantidadSalidaAlmacenDetalle1(this)" />
                                <h:outputText value="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalle.equivalencias.unidadesMedida1.nombreUnidadMedida}" styleClass="outputText1" />
                                </h:panelGroup>

                                <h:outputText value="Equivalente" styleClass="outputText1" />
                                <h:outputText value="::" styleClass="outputText1" />
                                <h:panelGroup>
                                    <h:inputText value="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalle.cantidadSalidaAlmacenEquivalente}" readonly="true" id="cantidadEquivalencia" styleClass="inputText" />
                                <h:outputText value="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalle.equivalencias.unidadesMedida2.nombreUnidadMedida}" styleClass="outputText1" />
                                <h:inputHidden value="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalle.equivalencias.valorEquivalencia}" id="valorEquivalencia" />
                                </h:panelGroup>
                                
                                
                           
                            </h:panelGrid>
   

                                <div align="center">

                                <a4j:commandButton styleClass="btn" value="Guardar"
                                action="#{ManagedRegistroSalidaAlmacen.guardarEdicionCantidadMaterial_action}"
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
                                <h:selectOneMenu value="#{ManagedRegistroSalidaAlmacen.buscarMaterial.grupos.capitulos.codCapitulo}" styleClass="inputText" >
                                    <f:selectItems value="#{ManagedRegistroSalidaAlmacen.capitulosList}"  />
                                    <a4j:support event="onchange" action="#{ManagedRegistroSalidaAlmacen.capitulos_change}" reRender="codGrupo" />
                                </h:selectOneMenu>

                                <h:outputText value="Grupo:" styleClass="tituloCampo1" />
                                <h:selectOneMenu value="#{ManagedRegistroSalidaAlmacen.buscarMaterial.grupos.codGrupo}" styleClass="inputText" id="codGrupo" >
                                    <f:selectItems value="#{ManagedRegistroSalidaAlmacen.gruposList}"  />
                                </h:selectOneMenu>

                                <h:outputText value="Item:" styleClass="tituloCampo1" />

                                <h:inputText value="#{ManagedRegistroSalidaAlmacen.buscarMaterial.nombreMaterial}" styleClass="inputText" size="50"  />

                                <a4j:commandButton value="Buscar" action="#{ManagedRegistroSalidaAlmacen.buscarMaterial_action}" styleClass="btn"  reRender="dataBuscarMaterial"  />

                           </h:panelGrid>
                              <br/>
                            <div style="overflow:auto;height:200px;width:100%;text-align:center">
                            <rich:dataTable value="#{ManagedRegistroSalidaAlmacen.materialesBuscarList}" var="data"
                                    id="dataBuscarMaterial"
                                    onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                    onRowMouseOver="this.style.backgroundColor='#DDE3E4';"                                    
                                    headerClass="headerClassACliente"
                                    binding="#{ManagedRegistroSalidaAlmacen.materialesBuscarDataTable}" >


                                <rich:column style="background-color: #{data.colorFila}">
                                    <f:facet name="header">
                                        <h:outputText value="Material"  />
                                    </f:facet>
                                    <a4j:commandLink  action="#{ManagedRegistroSalidaAlmacen.detallarCantidadSalida_action}" styleClass="#{data.colorFila}"  reRender="nombreMaterialAgregar,detalleSalidaMaterial,dataBuscarMaterial" >
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
                             <h:outputText value="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalleAgregar.materiales.nombreMaterial}" styleClass="outputText1" />
                             </h:panelGroup>
                             </b>
                             <br/>


                            <h:panelGrid columns="11" id="detalleSalidaMaterial" headerClass="headerClassACliente" style="width:100%" >
                                <f:facet name="header" >
                                    <h:outputText value="Detalle de Salida de Material" />
                                </f:facet>
                                <h:outputText value="Estado Material" styleClass="outputText1" />
                                <h:selectOneMenu value="#{ManagedRegistroSalidaAlmacen.ingresosAlmacenDetalleEstado.estadosMaterial.codEstadoMaterial}" styleClass="inputText" >
                                    <f:selectItems value="#{ManagedRegistroSalidaAlmacen.estadosMaterialList}" />
                                    <a4j:support event="onchange" action="#{ManagedRegistroSalidaAlmacen.estadoMaterial_change}" reRender="nombreMaterialAgregar,detalleSalidaMaterial,dataBuscarMaterial" />
                                </h:selectOneMenu>


                                <h:outputText value="Cantidad Disponible: " styleClass="outputText1" />
                                <h:panelGroup>
                                    <h:outputText value="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalleAgregar.cantidadDisponible}"  id="cantidadDisponible" styleClass="outputText1" >
                                        
                                    </h:outputText>
                                    <h:outputText value="[" styleClass="outputText1" />
                                    <h:outputText value="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalleAgregar.unidadesMedida.abreviatura}" styleClass="outputText1" />
                                    <h:outputText value="]" styleClass="outputText1" />
                                </h:panelGroup>

                                <h:outputText value="Cantidad Salida: " styleClass="outputText1" />
                                <h:panelGroup>
                                    <h:inputText value="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalleAgregar.cantidadSalidaAlmacen}" styleClass="inputText" id="cantidadSalidaAlmacen" onkeypress="valNumPositivo(event);" onkeyup="validaCantidadSalidaAlmacenDetalle(this)" />
                                    <h:outputText value="[" styleClass="outputText1" />
                                    <h:outputText value="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalleAgregar.equivalencias.unidadesMedida1.abreviatura}" styleClass="outputText1" />
                                    <h:outputText value="]" styleClass="outputText1" />
                                </h:panelGroup>

                                <h:outputText value="Equivalencia: " styleClass="outputText1" />
                                <h:panelGroup>
                                    <h:inputHidden value="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalleAgregar.equivalencias.valorEquivalencia}" id="valorEquivalencia" />
                                    <h:inputText value="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalleAgregar.cantidadSalidaAlmacenEquivalente}" styleClass="inputText" id="cantidadEquivalencia" />
                                    <h:outputText value="[" styleClass="outputText1" />
                                    <h:outputText value="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalleAgregar.equivalencias.unidadesMedida2.abreviatura}" styleClass="outputText1" />
                                    <h:outputText value="]" styleClass="outputText1" />
                                </h:panelGroup>

                            </h:panelGrid>
                            
                            
                            


                            </h:panelGroup>
                            </div>

                                <div align="center">
                                <a4j:commandButton styleClass="btn" value="Guardar"
                                action="#{ManagedRegistroSalidaAlmacen.guardarAgregarSalidaAlmacenDetalle_action}"
                                oncomplete="if(#{ManagedRegistroSalidaAlmacen.mensaje!=''}){alert('#{ManagedRegistroSalidaAlmacen.mensaje}')}else{javascript:Richfaces.hideModalPanel('panelAgregarItem')}"
                                            reRender="dataSalidasAlmacenDetalle"
                                                    />

                                <input type="button" value="Cancelar" class="btn" onclick="javascript:Richfaces.hideModalPanel('panelAgregarItem')" />
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

