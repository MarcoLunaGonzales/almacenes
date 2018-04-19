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
                    window.open(url,'DETALLE','top=50,left=200,width=800,height=500,scrollbars=1,resizable=1');
        }
           function openPopup1(f,url){
                    //window.open(url,'DETALLE','top=50,left=200,width=800,height=600,scrollbars=1,resizable=1');
                    f.action=url;
                    f.submit();
                }
       </script>
        </head>
        <body >
            <a4j:form id="form1" >
                <div align="center">
                    <h:outputText value="#{ManagedIngresoTraspasoAlmacen.cargarContenidoTraspasos}"/>
                    <h:outputText styleClass="outputTextTitulo"  value="Traspasos Almacen" />
                    <br>

                        <a4j:commandLink onclick="Richfaces.showModalPanel('panelBuscarTraspaso')">
                            <h:outputText value="Buscar" styleClass="outputText1" />
                            <h:graphicImage url="../img/buscar.png">

                            </h:graphicImage>
                        </a4j:commandLink>

                        <br>

                    
                    <rich:dataTable value="#{ManagedIngresoTraspasoAlmacen.traspasosList}" var="data"
                                    id="dataSalidasAlmacen"
                                    onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                    onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                    headerClass="headerClassACliente" >                        
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value=""  />
                            </f:facet>
                            <h:selectBooleanCheckbox value="#{data.checked}"  />
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Nro Salida"  />
                            </f:facet>
                            <h:outputText  value="#{data.salidasAlmacen.nroSalidaAlmacen}"  />
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Fecha Traspaso"  />
                            </f:facet>
                            <h:outputText value="#{data.fechaTraspaso}">
                                <f:convertDateTime pattern="dd/MM/yyyy" />
                            </h:outputText>
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Almacen Origen"  />
                            </f:facet>
                            <h:outputText value="#{data.almacenOrigen.nombreAlmacen}"  />
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Estado Traspaso"  />
                            </f:facet>
                            <h:outputText value="#{data.estadosTraspaso.nombreEstadoTraspaso}"  />
                        </h:column>
                         <h:column>
                            <f:facet name="header">
                                <h:outputText value=""  />
                            </f:facet>
                            <a4j:commandLink onclick="openPopup('reporteTraspasoAlmacen.jsf?codSalidaAlmacen=#{data.salidasAlmacen.codSalidaAlmacen}&codAlmacenOrigen=#{data.almacenOrigen.codAlmacen}')">
                                <h:graphicImage url="../img/areasdependientes.png" >
                                </h:graphicImage>
                            </a4j:commandLink>
                            
                         </h:column>
                       
                    </rich:dataTable>
                    
                    <%-- <ws:datascroller fordatatable="dataAreasEmpresa"  />--%>
                    <br>
                    <h:panelGroup rendered="#{ManagedIngresoTraspasoAlmacen.administradorAlmacen}">
                        <a4j:commandButton value="Aceptar Traspaso" styleClass="btn" onclick="if(confirm('Esta Seguro de Aceptar el Traspaso?')==false){return false;}" action="#{ManagedIngresoTraspasoAlmacen.aceptarTraspaso_action}" oncomplete="if(#{ManagedIngresoTraspasoAlmacen.mensaje!=''}){alert('#{ManagedIngresoTraspasoAlmacen.mensaje}');}else{location='agregarIngresosAlmacenTraspaso.jsf';}"  />
                    </h:panelGroup>
                    <%--
                    <a4j:commandButton value="Generar Salida Almacen" styleClass="btn"  action="#{ManagedIngresoAlmacenOrdenCompra.generarIngresoAlmacen_action}"
                    reRender="contenidoFrecuenciaMantenimentoMaquinaria" oncomplete="if(#{ManagedIngresoAlmacenOrdenCompra.mensaje!=''}){alert('#{ManagedIngresoAlmacenOrdenCompra.mensaje}')}" />
                    --%>

                </div>

               
                <!--cerrando la conexion-->
                <h:outputText value="#{ManagedFrecuenciaMantenimientoMaquinaria.closeConnection}"  />

            </a4j:form>

 <%--inicio ale buscador--%>
            <rich:modalPanel id="panelBuscarTraspaso"  minHeight="220"  minWidth="1000"
                                     height="220" width="1000"
                                     zindex="200"
                                     headerClass="headerClassACliente"
                                     resizeable="false" style="overflow :auto" >
                        <f:facet name="header">
                            <h:outputText value="Buscar Traspaso Almacen"/>
                        </f:facet>
                        <a4j:form>
                        <h:panelGroup id="contenidoBuscarSalidaAlmacen">
                            <h:panelGrid columns="4">
                                <h:outputText value="Nro Salida:" styleClass="outputText1" />
                                <h:inputText value="#{ManagedIngresoTraspasoAlmacen.traspasoBuscar.salidasAlmacen.nroSalidaAlmacen}" styleClass="inputText" size="5" onkeypress="valNum(event);" />
                                <h:outputText value="Gestion:" styleClass="outputText1" />
                                <h:selectOneMenu value="#{ManagedIngresoTraspasoAlmacen.traspasoBuscar.salidasAlmacen.gestiones.codGestion}" styleClass="inputText" >
                                    <f:selectItems value="#{ManagedIngresoTraspasoAlmacen.gestionesList}"/>
                                 </h:selectOneMenu>

                                <h:outputText value="Fecha inicio Salida" styleClass="outputText1" />
                                <rich:calendar  datePattern="dd/MM/yyyy" styleClass="inputText" zindex="200" value="#{ManagedIngresoTraspasoAlmacen.fechaInicioBuscar}" id="fechaInicio" enableManualInput="true" oninputblur="if(this.value!=''){valFecha(this)}else{return true;}"  />
                                <h:outputText value="Fecha final Salida" styleClass="outputText1" />
                                <rich:calendar  datePattern="dd/MM/yyyy" styleClass="inputText" zindex="200" value="#{ManagedIngresoTraspasoAlmacen.fechaFinalBuscar}" id="fechaFinal" enableManualInput="true" oninputblur="if(this.value!=''){valFecha(this)}else{return true;}"  />
                                <h:outputText value="Almacen Origen:" styleClass="outputText1" />
                                <h:selectOneMenu value="#{ManagedIngresoTraspasoAlmacen.traspasoBuscar.almacenOrigen.codAlmacen}" styleClass="inputText" >
                                     <f:selectItems value="#{ManagedIngresoTraspasoAlmacen.almacenesList}"/>
                                 </h:selectOneMenu>
                                <h:outputText value="Capitulo:" styleClass="outputText1" />
                                <h:selectOneMenu value="#{ManagedIngresoTraspasoAlmacen.materialBuscar.grupos.capitulos.codCapitulo}" styleClass="inputText">
                                    <f:selectItems value="#{ManagedIngresoTraspasoAlmacen.capitulosList}"/>
                                    <a4j:support action="#{ManagedIngresoTraspasoAlmacen.capitulosTraspaso_change}" event="onchange" reRender="codGrupoTraspaso"/>
                                </h:selectOneMenu>
                                <h:outputText value="Grupo:" styleClass="outputText1" />
                                <h:selectOneMenu value="#{ManagedIngresoTraspasoAlmacen.materialBuscar.grupos.codGrupo}" styleClass="inputText" id="codGrupoTraspaso">
                                    <f:selectItems value="#{ManagedIngresoTraspasoAlmacen.gruposList}"/>
                                 </h:selectOneMenu>
                                 <h:outputText value="Item:" styleClass="outputText1"  />
                                <h:inputText value="#{ManagedIngresoTraspasoAlmacen.materialBuscar.nombreMaterial}" styleClass="inputText" size="30"  />


                            </h:panelGrid>
                                <div align="center">
                                <a4j:commandButton styleClass="btn" value="Buscar" onclick="javascript:Richfaces.hideModalPanel('panelBuscarTraspaso');"
                                action="#{ManagedIngresoTraspasoAlmacen.buscarTraspaso_Action}"
                                                   reRender="dataSalidasAlmacen,controles"
                                                    />
                                <input type="button" value="Cancelar" onclick="javascript:Richfaces.hideModalPanel('panelBuscarTraspaso')" class="btn" />
                                </div>
                        </h:panelGroup>
                        </a4j:form>
            </rich:modalPanel>




        </body>
    </html>

</f:view>

