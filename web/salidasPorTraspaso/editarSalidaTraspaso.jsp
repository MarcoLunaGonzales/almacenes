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
                function validar(nametable){

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
                    if(count==0){
                        alert('No selecciono ningun Registro');
                        return false;
                    }else{
                     if(count>1){
                        alert('solo se puede editar un Registro');
                        return false;
                    }
                    }
                    return true;
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
           return true;
       }
       function calculaEquivalencia(obj){
           valorEquivalencia = document.getElementById("form3:valorEquivalencia").value;
           if(valorEquivalencia>0){
               document.getElementById("form3:cantidadEquivalencia").value = obj.value /valorEquivalencia;
           }
       }
       function calculaEquivalencia1(obj){
           valorEquivalencia = document.getElementById("form4:valorEquivalencia").value;
           if(valorEquivalencia>0){
               document.getElementById("form4:cantidadEquivalencia").value = obj.value /valorEquivalencia;
           }
       }
       function validaCantidadSalidaAlmacenDetalle(){
           //cantidadDisponible
           //alert(document.getElementById("form4:cantidadSalidaAlmacen").value);
           //alert(document.getElementById("form4:cantidadDisponible").value);
           if(parseFloat(document.getElementById("form4:cantidadSalidaAlmacen").value)>parseFloat(document.getElementById("form4:cantidadDisponible").value)){
               alert("la cantidad de salida de almacen no puede sobrepasar la cantidad disponible");
              return false;
           }
           return true;
       }
       </script>
        </head>
        <body >
            <a4j:form id="form1">
                <div align="center">

                    <h:outputText value="#{ManagedSalidaTraspasoAlmacen.cargarEditarSalidasTraspasoAlmacen}"/>
                    <br><br>
                        
                    <rich:panel id="panelEstadoCuentas" styleClass="panelBuscar" style="top:50px;left:50px;width:700px;">
                    <f:facet name="header">
                        <h:outputText value="<div   onmouseover=\"this.style.cursor='move'\" onmousedown=\"comienzoMovimiento(event, 'form1:panelEstadoCuentas');\"  >Buscar<div   style=\"margin-left:550px;\"   onclick=\"document.getElementById('form1:panelEstadoCuentas').style.visibility='hidden';document.getElementById('panelsuper').style.visibility='hidden';\" onmouseover=\"this.style.cursor='hand'\"   >Cerrar</div> </div> "
                              escape="false" />
                    </f:facet>
                    </rich:panel>

                <rich:panel style="width:80%" headerClass="headerClassACliente">
                            <f:facet name="header">
                                <h:outputText value="Registro de Salidas de Almacen por Traspaso"  />
                            </f:facet>
                        <h:panelGrid columns="4" >
                            
                            
                            <h:outputText value="Gestion:" styleClass="outputText2" />
                            <h:outputText value="#{ManagedSalidaTraspasoAlmacen.traspasos.salidasAlmacen.gestiones.nombreGestion}" styleClass="outputText1" />

                            <h:outputText value="Nro Salida:" styleClass="outputText2" />
                            <h:outputText value="#{ManagedSalidaTraspasoAlmacen.traspasos.salidasAlmacen.nroSalidaAlmacen}" styleClass="outputText1" />

                            <h:outputText value="Filial:" styleClass="outputText2" />
                            <h:selectOneMenu  styleClass="inputText" value="#{ManagedSalidaTraspasoAlmacen.traspasos.almacenDestino.filiales.codFilial}"   >
                                <f:selectItems value="#{ManagedSalidaTraspasoAlmacen.filialList}"  />
                                <a4j:support action="#{ManagedSalidaTraspasoAlmacen.filial_change}" event="onchange" reRender="codAlmacenDestino" />
                            </h:selectOneMenu>
                            
                            <h:outputText value="Almacen Destino:" styleClass="outputText2" />
                            <h:selectOneMenu  styleClass="inputText" value="#{ManagedSalidaTraspasoAlmacen.traspasos.almacenDestino.codAlmacen}"  id="codAlmacenDestino"  >
                                <f:selectItems value="#{ManagedSalidaTraspasoAlmacen.almacenDestinoList}"  />
                                <a4j:support action="#{ManagedSalidaTraspasoAlmacen.almacenDestino_change}" event="onchange" reRender="codAreaDestino" />
                            </h:selectOneMenu>

                            <h:outputText value="Area Destino:" styleClass="outputText2" />
                            <h:selectOneMenu  styleClass="inputText" value="#{ManagedSalidaTraspasoAlmacen.traspasos.salidasAlmacen.areasEmpresa.codAreaEmpresa}" id="codAreaDestino"  >
                                <f:selectItems  value="#{ManagedSalidaTraspasoAlmacen.areaDestinoList}" />
                            </h:selectOneMenu>

                            <h:outputText value="Observaciones:" styleClass="outputText2" />
                            <h:inputTextarea  value="#{ManagedSalidaTraspasoAlmacen.traspasos.salidasAlmacen.obsSalidaAlmacen}" styleClass="outputText2" cols="50" rows="5" />

                        </h:panelGrid>
                    </rich:panel>
                    
                    
                        <div style="overflow:auto;height:200px;width:80%;text-align:center">
                       <rich:dataTable value="#{ManagedSalidaTraspasoAlmacen.salidasAlmacenDetalleList}"
                                    var="data"
                                    id="dataSalidasAlmacenDetalle"
                                    onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                    onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                    headerClass="headerClassACliente"                                    
                                    style="width:100%" binding="#{ManagedSalidaTraspasoAlmacen.salidasAlmacenDetalleEditarDataTable}"  >
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
                                <h:outputText value="Cantidad"  />
                            </f:facet>
                            <h:outputText value="#{data.cantidadSalidaAlmacen}"  />
                        </h:column>
                      
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Unid."   />
                            </f:facet>
                            <h:outputText value="#{data.unidadesMedida.abreviatura}"  />
                        </h:column>
                        
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Cant. Disp."   />
                            </f:facet>
                            <h:outputText value="#{data.cantidadDisponible}"  />
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value=""   />
                            </f:facet>
                            <a4j:commandLink onclick="Richfaces.showModalPanel('panelSalidaAlmacenDetalleEstado')" action="#{ManagedSalidaTraspasoAlmacen.verDetalleEtiquetasEdicionSalidaAlmacen_action}" reRender="contenidoSalidaAlmacenDetalleEstado" >
                                    <h:graphicImage url="../img/areasdependientes.png">

                                    </h:graphicImage>
                            </a4j:commandLink>

                        </h:column>

                    </rich:dataTable>
                    </div>
                    
                    

                    <%-- <ws:datascroller fordatatable="dataAreasEmpresa"  />--%>
                    <br>
                    <a4j:commandButton value="Guardar" styleClass="btn" rendered="false"
                    action="#{ManagedSalidaTraspasoAlmacen.guardarEditarTraspasoSalidasAlmacen_action1}"
                    oncomplete="if(#{ManagedSalidaTraspasoAlmacen.mensaje!=''}){alert('#{ManagedSalidaTraspasoAlmacen.mensaje}')}else{location='navegadorSalidasTraspaso.jsf'}"
                    />
                    <a4j:commandButton value="Guardar" styleClass="btn"
                    action="#{ManagedSalidaTraspasoAlmacen.guardarEditarTraspasoSalidasAlmacen_action2}"
                    
                    oncomplete="if(#{ManagedSalidaTraspasoAlmacen.mensaje!=''}){alert('#{ManagedSalidaTraspasoAlmacen.mensaje}')}else{location='navegadorSalidasTraspaso.jsf'}"
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
                <a4j:commandButton value="Agregar" styleClass="btn" rendered="false"
                    onclick="Richfaces.showModalPanel('panelAgregarItem')"
                    action="#{ManagedSalidaTraspasoAlmacen.busquedaEditarMaterial_action}"
                    reRender="contenidoAgregarItem"
                    />
                <a4j:commandButton value="Eliminar" styleClass="btn"
                    onclick="if(confirm('Esta seguro de eliminar?')==false){return false;}"
                    action="#{ManagedSalidaTraspasoAlmacen.eliminarIngresoDetalleAlmacen_action1}"
                    reRender="dataSalidasAlmacenDetalle"
                    />
                <a4j:commandButton value="Cancelar" styleClass="btn"
                    onclick="location = 'navegadorSalidasTraspaso.jsf'"
                    />
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
                        <a4j:form id="form4">
                        <h:panelGroup id="contenidoAgregarItem">
                          <div align="center">
                           <h:panelGrid columns="4" id="contenidoIngresoAlmacenDetalle" headerClass="headerClassACliente" width="90%" style="border:1px solid #000000;" >
                                <f:facet name="header">
                                      <h:outputText value="Buscar Item" />
                                </f:facet>

                                <h:outputText value="Capitulo:" styleClass="tituloCampo1" />
                                <h:selectOneMenu value="#{ManagedSalidaTraspasoAlmacen.buscarMaterial.grupos.capitulos.codCapitulo}" styleClass="inputText" >
                                    <f:selectItems value="#{ManagedSalidaTraspasoAlmacen.capitulosList}"  />
                                    <a4j:support event="onchange" action="#{ManagedSalidaTraspasoAlmacen.capitulos_change}" reRender="codGrupo" />
                                </h:selectOneMenu>

                                <h:outputText value="Grupo:" styleClass="tituloCampo1" />
                                <h:selectOneMenu value="#{ManagedSalidaTraspasoAlmacen.buscarMaterial.grupos.codGrupo}" styleClass="inputText" id="codGrupo" >
                                    <f:selectItems value="#{ManagedSalidaTraspasoAlmacen.gruposList}"  />
                                </h:selectOneMenu>

                                <h:outputText value="Item:" styleClass="tituloCampo1" />

                                <h:inputText value="#{ManagedSalidaTraspasoAlmacen.buscarMaterial.nombreMaterial}" styleClass="inputText" size="50"  />

                                <a4j:commandButton value="Buscar" action="#{ManagedSalidaTraspasoAlmacen.buscarEditarMaterial_action}" styleClass="btn"  reRender="dataBuscarMaterial"  />

                           </h:panelGrid>
                              <br/>

                            <rich:dataTable value="#{ManagedSalidaTraspasoAlmacen.materialesEditarBuscarList}" var="data"
                                    id="dataBuscarMaterial"
                                    onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                    onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                    headerClass="headerClassACliente"
                                    binding="#{ManagedSalidaTraspasoAlmacen.materialesEditarBuscarDataTable}"
                                     >


                                <h:column>
                                    <f:facet name="header">
                                        <h:outputText value="Material"  />
                                    </f:facet>
                                    <a4j:commandLink  action="#{ManagedSalidaTraspasoAlmacen.seleccionarEditarMaterial_action}" styleClass="outputText2" onclick="Richfaces.hideModalPanel('panelAgregarItem')" reRender="dataSalidasAlmacenDetalle" >
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
                            </div>
                            </h:panelGroup>
                            

                                <div align="center">
                                <input type="button" value="Cancelar" class="btn" onclick="javascript:Richfaces.hideModalPanel('panelAgregarItem')" />
                                </div>

                        </a4j:form>
            </rich:modalPanel>

            <rich:modalPanel id="panelSalidaAlmacenDetalleEstado" minHeight="400"  minWidth="1020"
                                     height="400" width="1020"
                                     zindex="200"
                                     headerClass="headerClassACliente"
                                     resizeable="false" style="overflow :auto" >
                        <f:facet name="header">
                            <h:outputText value="Detalle Item"/>
                        </f:facet>
                        <a4j:form id="form3">
                            <h:panelGroup id="contenidoSalidaAlmacenDetalleEstado">
                                <div style="overflow:auto;height:290px;text-align:center">

                                    <rich:dataTable value="#{ManagedSalidaTraspasoAlmacen.salidasAlmacenDetalle.salidasAlmacenDetalleIngresoList}" var="data"
                                            id="dataIngresoAlmacenDetalleEstado"
                                            onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                            onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                            headerClass="headerClassACliente">

                                        <h:column>
                                            <f:facet name="header">
                                                <h:outputText value="Fecha Venc."  />
                                            </f:facet>
                                            <h:outputText  value="#{data.ingresosAlmacenDetalleEstado.fechaVencimiento}">
                                                <f:convertDateTime pattern="dd/MM/yyyy" />
                                            </h:outputText>
                                        </h:column>

                                        <h:column>
                                            <f:facet name="header">
                                                <h:outputText value="Nombre Material"  />
                                            </f:facet>
                                            <h:outputText  value="#{data.ingresosAlmacenDetalleEstado.estadosMaterial.nombreEstadoMaterial}"/>
                                        </h:column>


                                        <h:column>
                                            <f:facet name="header">
                                                <h:outputText value="Nro. Ingreso"  />
                                            </f:facet>
                                            <h:outputText  value="#{data.ingresosAlmacenDetalleEstado.ingresosAlmacen.nroIngresoAlmacen}"/>
                                        </h:column>


                                        <h:column>
                                            <f:facet name="header">
                                                <h:outputText value="Seccion"  />
                                            </f:facet>
                                            <h:outputText  value="#{data.ingresosAlmacenDetalleEstado.ingresosAlmacenDetalle.secciones.nombreSeccion}"/>
                                        </h:column>

                                        <h:column>
                                            <f:facet name="header">
                                                <h:outputText value="Etiqueta"  />
                                            </f:facet>
                                            <h:outputText  value="#{data.ingresosAlmacenDetalleEstado.etiqueta}"/>
                                        </h:column>



                                        <h:column>
                                            <f:facet name="header">
                                                <h:outputText value="Empaque Secundario"  />
                                            </f:facet>
                                            <h:outputText  value="#{data.ingresosAlmacenDetalleEstado.empaqueSecundarioExterno.nombreEmpaqueSecundarioExterno}"/>
                                        </h:column>

                                        <h:column>
                                            <f:facet name="header">
                                                <h:outputText value="Lote Proveedor"  />
                                            </f:facet>
                                            <h:outputText  value="#{data.ingresosAlmacenDetalleEstado.loteMaterialProveedor}"/>
                                        </h:column>


                                        <h:column>
                                            <f:facet name="header">
                                                <h:outputText value="Costo"  />
                                            </f:facet>
                                            <h:outputText  value="#{data.ingresosAlmacenDetalleEstado.ingresosAlmacenDetalle.costoUnitario}"/>
                                        </h:column>

                                        <h:column>
                                            <f:facet name="header">
                                                <h:outputText value="Cantidad Fis."  />
                                            </f:facet>
                                            <h:outputText  value="#{data.cantidad}"/>
                                        </h:column>


                                        <h:column>
                                            <f:facet name="header">
                                                <h:outputText value="Cantidad Disponible"  />
                                            </f:facet>
                                            <h:outputText value="#{data.cantidadDisponible}"  />
                                        </h:column>

                                        <h:column>
                                            <f:facet name="header">
                                                <h:outputText value="Cant. a Sacar"  />
                                            </f:facet>
                                            <h:inputText styleClass="inputText"  value="#{data.cantidadSacar}" onkeyup="validaCantidad(this);" onkeypress="valNumPositivo(event);"/>                                    
                                        </h:column>

                                    </rich:dataTable>
                                </div>

                                <div align="center">
                                    <a4j:commandButton styleClass="btn" value="Registrar"
                                                       action="#{ManagedSalidaTraspasoAlmacen.guardarSalidasAlmacenDetalleIngreso_action}"
                                                       reRender="dataSalidasAlmacenDetalle"
                                                       rendered="false"

                                                       oncomplete="if(#{ManagedSalidaTraspasoAlmacen.mensaje!=''}){alert('#{ManagedSalidaTraspasoAlmacen.mensaje}')}else{javascript:Richfaces.hideModalPanel('panelSalidaAlmacenDetalleEstado')}"
                                                        />
                                    <input type="button" value="Cancelar" class="btn" onclick="javascript:Richfaces.hideModalPanel('panelSalidaAlmacenDetalleEstado')" />
                                </div>
                            </h:panelGroup>
                        </a4j:form>
            </rich:modalPanel>

            

          
             

            <a4j:status id="statusPeticion"
                                onstart="Richfaces.showModalPanel('ajaxLoadingModalBox',{width:100, top:100})"
                                onstop="Richfaces.hideModalPanel('ajaxLoadingModalBox')" >
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

