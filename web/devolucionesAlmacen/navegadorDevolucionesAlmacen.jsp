
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
                .a2
                {
                    background-color:#DC143C;
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
       </script>
        </head>
        <body >
            <a4j:form id="form1">
                <div align="center">
                    <h:outputText value="#{ManagedDevolucionAlmacen.cargarContenidoDevolucionAlmacen}"/>
                    <h:outputText styleClass="outputTextTitulo"  value="Devoluciones" />
                    <br><br>
                       <%--inicio alejandro buscador--%>
                        <br>
                      <a4j:commandLink onclick="Richfaces.showModalPanel('panelBuscarDevolucion')">
                        <h:outputText value="Buscar Devolución" styleClass="outputText1" />
                        <h:graphicImage url="../img/buscar.png">

                        </h:graphicImage>
                    </a4j:commandLink>
                    <%--final alejandro buscador--%>

                    
                    <rich:dataTable value="#{ManagedDevolucionAlmacen.devolucionesList}"
                                    var="data"
                                    id="dataDevolucionesAlmacen"
                                    onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                    onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                    headerClass="headerClassACliente" >
                         <rich:column styleClass="a#{data.devoluciones.estadosDevoluciones.codEstadoDevolucion}">
                            <f:facet name="header">
                                <h:outputText value=""  />
                            </f:facet>
                            <h:selectBooleanCheckbox value="#{data.checked}" />
                        </rich:column>
                        <rich:column styleClass="a#{data.devoluciones.estadosDevoluciones.codEstadoDevolucion}">
                            <f:facet name="header">
                                <h:outputText value="Nro"  />
                            </f:facet>
                            <h:outputText  value="#{data.devoluciones.nroDevolucion}"  />
                        </rich:column>
                        <rich:column styleClass="a#{data.devoluciones.estadosDevoluciones.codEstadoDevolucion}">
                            <f:facet name="header">
                                <h:outputText value="Estado"  />
                            </f:facet>
                            <h:outputText value="#{data.devoluciones.estadosDevoluciones.nombreEstadoDevolucion}">
                            </h:outputText>
                        </rich:column>
                        <rich:column styleClass="a#{data.devoluciones.estadosDevoluciones.codEstadoDevolucion}">
                            <f:facet name="header">
                                <h:outputText value="Nro Salida"  />
                            </f:facet>
                            <h:outputText value="#{data.devoluciones.salidasAlmacen.nroSalidaAlmacen}"  />
                        </rich:column>
                        <rich:column styleClass="a#{data.devoluciones.estadosDevoluciones.codEstadoDevolucion}">
                            <f:facet name="header">
                                <h:outputText value="Producto"  />
                            </f:facet>
                            <h:outputText value="#{data.devoluciones.salidasAlmacen.componentesProd.nombreProdSemiterminado}"  />
                        </rich:column>

                        <rich:column styleClass="a#{data.devoluciones.estadosDevoluciones.codEstadoDevolucion}">
                            <f:facet name="header">
                                <h:outputText value="Nro Ingreso"   />
                            </f:facet>
                            <h:outputText value="#{data.nroIngresoAlmacen}"  />
                        </rich:column>

                        <rich:column styleClass="a#{data.devoluciones.estadosDevoluciones.codEstadoDevolucion}">
                            <f:facet name="header">
                                <h:outputText value="Fecha Ingreso"  />
                            </f:facet>
                            <h:outputText value="#{data.fechaIngresoAlmacen}"  >
                                <f:convertDateTime pattern="dd/MM/yyyy" />
                            </h:outputText>
                        </rich:column>

                        <rich:column styleClass="a#{data.devoluciones.estadosDevoluciones.codEstadoDevolucion}">
                            <f:facet name="header">
                                <h:outputText value="Almacen"  />
                            </f:facet>
                            <h:outputText value="#{data.almacenes.nombreAlmacen}"  />
                        </rich:column>
                        
                         <rich:column styleClass="a#{data.devoluciones.estadosDevoluciones.codEstadoDevolucion}">
                            <f:facet name="header">
                                <h:outputText value="Personal"  />
                            </f:facet>
                            <h:outputText value="#{data.salidasAlmacen.personal.nombrePersonal}"  />
                            &nbsp;
                            <h:outputText value="#{data.salidasAlmacen.personal.apPaternoPersonal}"  />
                            &nbsp;
                            <h:outputText value="#{data.salidasAlmacen.personal. apMaternoPersonal}"  />
                        </rich:column>
                        
                    </rich:dataTable>
                    <h:panelGrid columns="2"  width="50" id="controles">
                         <a4j:commandLink  action="#{ManagedDevolucionAlmacen.atras_action_1}" reRender="dataDevolucionesAlmacen,controles" rendered="#{ManagedDevolucionAlmacen.begin!='1'}" >
                                <h:graphicImage url="../img/previous.gif"  style="border:0px solid red"   alt="PAGINA ANTERIOR"  />
                            </a4j:commandLink>
                            <a4j:commandLink  action="#{ManagedDevolucionAlmacen.siguiente_action_1}" reRender="dataDevolucionesAlmacen,controles" rendered="#{ManagedDevolucionAlmacen.cantidadfilas>='5'}">
                                <h:graphicImage url="../img/next.gif"  style="border:0px solid red"  alt="PAGINA SIGUIENTE" />
                            </a4j:commandLink>
                    </h:panelGrid>
                    <a4j:commandButton value="Imprimir" action="#{ManagedDevolucionAlmacen.imprimirDevolucion_action}" oncomplete="openPopup('../reportes/devolucionesAlmacen/reporteDevolucionesAlmacen.jsf')" styleClass="btn" />
                    <a4j:commandButton value="Imprimir Con Formato" styleClass = "btn" action="#{ManagedDevolucionAlmacen.imprimirDevolucion_action}" oncomplete="openPopup('reporteDevolucionAlmacenJasper.jsf');" />
                    <%--a4j:commandButton value="Editar" action="#{ManagedDevolucionAlmacen.editarDevolucionesAlmacen_action}" oncomplete="location='editarDevolucionesAlmacen.jsf'" styleClass="btn" />
                    <a4j:commandButton value="Anular Devolución" onclick="if(confirm('Esta seguro de anular la devolución?')==false){return false;}" action="#{ManagedDevolucionAlmacen.anularDevolucionesAlmacen_action}" reRender="dataDevolucionesAlmacen" oncomplete="if('#{ManagedDevolucionAlmacen.mensaje}'!=''){alert('#{ManagedDevolucionAlmacen.mensaje}')}" styleClass="btn" />
                    <a4j:commandButton value="Generar Devolución" onclick="Richfaces.showModalPanel('panelBuscarSalidaAlmacen')" styleClass="btn" /--%>
                    <%-- <ws:datascroller fordatatable="dataAreasEmpresa"  />--%>
                    <br>

                      
                    <%--
                      <a4j:commandButton value="Generar Devolucion" styleClass="btn"  onclick="location='agregarDevolucionesAlmacen.jsf'" />


                    <a4j:commandButton value="Agregar" styleClass="btn" onclick="javascript:Richfaces.showModalPanel('panelAgregarFrecuenciaMantenimientoMaquinaria')" action="#{ManagedFrecuenciaMantenimientoMaquinaria.agregarFrecuencia_action}"
                    reRender="contenidoFrecuenciaMantenimentoMaquinaria" />
                    <a4j:commandButton value="Eliminar"  styleClass="btn" onclick="javascript:if(confirm('Esta Seguro de Eliminar??')==false || validarSeleccion('form1:dataFrecuenciaMantenimientoMaquina')==false){return false;}"  action="#{ManagedFrecuenciaMantenimientoMaquinaria.eliminarFrecuenciaMantenimientoMaquina_action}"
                    reRender="dataFrecuenciaMantenimientoMaquina"/>
                    <a4j:commandButton value="Editar"  styleClass="btn" onclick="javascript:if(validarSeleccion('form1:dataFrecuenciaMantenimientoMaquina')==false){return false;}else{Richfaces.showModalPanel('panelEditarFrecuenciaMantenimientoMaquinaria')}"  action="#{ManagedFrecuenciaMantenimientoMaquinaria.editarFrecuenciaMantenimientoMaquina_action}"
                    reRender="contenidoEditarFrecuenciaMantenimentoMaquinaria" />
                    --%>

                </div>

               
                <!--cerrando la conexion-->
                <h:outputText value="#{ManagedFrecuenciaMantenimientoMaquinaria.closeConnection}"  />

            </a4j:form>


            
            
             <rich:modalPanel id="panelBuscarSalidaAlmacen" minHeight="400"  minWidth="700"
                                     height="400" width="700"
                                     zindex="200"
                                     headerClass="headerClassACliente"
                                     resizeable="false" style="overflow :auto" >
                        <f:facet name="header">
                            <h:outputText value="Busqueda de Salida Almacen"/>
                        </f:facet>
                        <a4j:form id="form2">
                        <h:panelGroup id="contenidoBuscarSalidaAlmacen">
                            <div align="center">
                            <h:panelGrid columns="8">
                                
                                <h:outputText value="Nro Salida :" styleClass="outputText1"  />
                                <h:inputText value="#{ManagedDevolucionAlmacen.salidasAlmacenBuscar.nroSalidaAlmacen}"  id="nroSalida" styleClass="inputText" size="5" onkeypress="valNum(event);" />

                                <h:outputText value="Nro Lote :" styleClass="outputText1" />
                                <h:inputText value="#{ManagedDevolucionAlmacen.salidasAlmacenBuscar.codLoteProduccion}" id="nroLote" styleClass="inputText" size="5"  />

                                <h:outputText value="Producto :" styleClass="outputText1" />
                                <h:selectOneMenu value="#{ManagedDevolucionAlmacen.salidasAlmacenBuscar.componentesProd.codCompprod}" id="codCompProd" styleClass="inputText" style="width=290px" >
                                    <f:selectItems value="#{ManagedDevolucionAlmacen.componentesProdList}"  />
                                </h:selectOneMenu>
                                <a4j:commandButton styleClass="btn" value="Buscar" 
                                                   action="#{ManagedDevolucionAlmacen.buscarSalidaAlmacen_action}"
                                                   reRender="dataSalidasAlmacenBuscar,controlesBuscar"
                                                   onclick="valida()"
                                                    />
                            </h:panelGrid>
                            
                            <div style="overflow:auto;height:250px;width:80%;text-align:center">
                            <rich:dataTable value="#{ManagedDevolucionAlmacen.salidasAlmacenBuscarList}"
                                    var="data"
                                    id="dataSalidasAlmacenBuscar"
                                    onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                    onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                    headerClass="headerClassACliente" binding="#{ManagedDevolucionAlmacen.salidasAlmacenDataTable}" >



                                    <h:column>
                                        <f:facet name="header">
                                            <h:outputText value=""  />
                                        </f:facet>
                                        <h:selectBooleanCheckbox value="#{data.checked}" />
                                    </h:column>

                                  <h:column>
                                        <f:facet name="header">
                                            <h:outputText value="Nro Lote"  />
                                        </f:facet>
                                        <h:outputText  value="#{data.codLoteProduccion}"  />
                                    </h:column>
                                    <h:column>
                                        <f:facet name="header">
                                            <h:outputText value="Nro Salida"  />
                                        </f:facet>
                                        <h:outputText value="#{data.nroSalidaAlmacen}">
                                        </h:outputText>
                                    </h:column>
                                    <h:column>
                                        <f:facet name="header">
                                            <h:outputText value="Fecha Salida"  />
                                        </f:facet>
                                        <h:outputText value="#{data.fechaSalidaAlmacen}"  />
                                    </h:column>
                                    <h:column>
                                        <f:facet name="header">
                                            <h:outputText value="Tipo Salida"  />
                                        </f:facet>
                                        <h:outputText value="#{data.tiposSalidasAlmacen.nombreTipoSalidaAlmacen}"  />
                                    </h:column>

                                    <h:column>
                                        <f:facet name="header">
                                            <h:outputText value="Area Destino"  />
                                        </f:facet>
                                        <h:outputText value="#{data.areasEmpresa.nombreAreaEmpresa}"  />
                                    </h:column>

                                     <h:column>
                                        <f:facet name="header">
                                            <h:outputText value="Responsable"  />
                                        </f:facet>
                                        <h:outputText value="#{data.personal.nombrePersonal}"  />
                                        &nbsp;
                                        <h:outputText value="#{data.personal.apPaternoPersonal}"  />
                                        &nbsp;
                                        <h:outputText value="#{data.personal. apMaternoPersonal}"  />
                                    </h:column>

                                      <h:column>
                                        <f:facet name="header">
                                            <h:outputText value="Observaciones"  />
                                        </f:facet>
                                        <h:outputText value="#{data.obsSalidaAlmacen}"  />
                                      </h:column>
                                      <h:column>
                                        <f:facet name="header">
                                            <h:outputText value=""  />
                                        </f:facet>
                                        <a4j:commandLink action="#{ManagedDevolucionAlmacen.verReporteSalidaAlmacen_action}" oncomplete="openPopup('../reportes/salidasAlmacen/reporteDetalleSalidasAlmacen.jsp')" >
                                            <h:graphicImage url="../img/organigrama3.jpg" />
                                        </a4j:commandLink>
                                      </h:column>

                            </rich:dataTable>
                            </div>

                            <h:panelGrid columns="2"  width="50" id="controlesBuscar">
                                    <a4j:commandLink  action="#{ManagedDevolucionAlmacen.atras_action}" reRender="dataSalidasAlmacenBuscar,controlesBuscar"  rendered="#{ManagedDevolucionAlmacen.inicioFila!='1'}" >
                                        <h:graphicImage url="../img/previous.gif"  style="border:0px solid red"   alt="PAGINA ANTERIOR"  />
                                    </a4j:commandLink>
                                    <a4j:commandLink  action="#{ManagedDevolucionAlmacen.siguiente_action}" reRender="dataSalidasAlmacenBuscar,controlesBuscar" rendered="#{ManagedDevolucionAlmacen.cantidadFilasBuscar >='5'}">
                                        <h:graphicImage url="../img/next.gif"  style="border:0px solid red"  alt="PAGINA SIGUIENTE" />
                                    </a4j:commandLink>
                            </h:panelGrid>


                                <br/>
                                <a4j:commandButton styleClass="btn" value="Generar Devolucion"
                                                   onclick="if(validarSeleccion('form2:dataSalidasAlmacenBuscar')==false){return false;}"
                                                   action="#{ManagedDevolucionAlmacen.generarDevolucionSalidaAlmacen_action}" 
                                                   oncomplete="location='agregarDevolucionesAlmacen.jsf'"                                                   
                                                    />
                                <a4j:commandButton styleClass="btn" value="Cancelar"
                                onclick="Richfaces.hideModalPanel('panelBuscarSalidaAlmacen')"
                                                    />
                                </div>
                        </h:panelGroup>
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
            <%--inicio alejandro buscador--%>
             <rich:modalPanel id="panelBuscarDevolucion"  minHeight="300"  minWidth="1000"
                                     height="300" width="1000"
                                     zindex="200"
                                     headerClass="headerClassACliente"
                                     resizeable="false" style="overflow :auto" >
                        <f:facet name="header">
                            <h:outputText value="Buscar Devolución"/>
                        </f:facet>
                        <a4j:form>
                        <h:panelGroup id="contenidoBuscarDevolucion">
                            <h:panelGrid columns="4">


                                <h:outputText value="Nro Devolución:" styleClass="outputText1" />
                                <h:inputText value="#{ManagedDevolucionAlmacen.buscarDevolucion.nroDevolucion}" styleClass="inputText" size="5" onkeypress="valNum(event);" />

                                <h:outputText value="Nro Salida:" styleClass="outputText1" />
                                <h:inputText value="#{ManagedDevolucionAlmacen.buscarDevolucion.salidasAlmacen.nroSalidaAlmacen}" styleClass="inputText" size="5" onkeypress="valNum(event);" />
                                <h:outputText value="Nro Lote:" styleClass="outputText1" />
                                <h:inputText value="#{ManagedDevolucionAlmacen.buscarDevolucion.salidasAlmacen.codLoteProduccion}" styleClass="inputText" size="5"  />

                                <h:outputText value="Tipo de Salida:" styleClass="outputText1" />
                                <h:selectOneMenu value="#{ManagedDevolucionAlmacen.buscarDevolucion.salidasAlmacen.tiposSalidasAlmacen.codTipoSalidaAlmacen}" styleClass="inputText" >
                                    <f:selectItems value="#{ManagedDevolucionAlmacen.listaTiposSalida}"  />
                                </h:selectOneMenu>

                                 <h:outputText value="Fecha Inicio :" styleClass="outputText1" />
                                 <rich:calendar  datePattern="dd/MM/yyyy" styleClass="inputText" zindex="200" value="#{ManagedDevolucionAlmacen.fechaInicio}" id="fechaInicio" enableManualInput="true" oninputblur="if(this.value!=''){valFecha(this)}else{return true;}" />

                              <h:outputText value="Fecha Final :" styleClass="outputText1" />
                              <rich:calendar  datePattern="dd/MM/yyyy" styleClass="inputText" zindex="200" value="#{ManagedDevolucionAlmacen.fechaFinal}" id="fechaFinal" enableManualInput="true" oninputblur="if(this.value!=''){valFecha(this)}else{return true;}" />

                                <h:outputText value="Estado Devolución:" styleClass="outputText1" />
                                <h:selectOneMenu value="#{ManagedDevolucionAlmacen.buscarDevolucion.estadosDevoluciones.codEstadoDevolucion}" styleClass="inputText" >
                                    <f:selectItems value="#{ManagedDevolucionAlmacen.listaEstadosDevoluciones}"  />
                                </h:selectOneMenu>

                                <h:outputText value="Area Destino:" styleClass="outputText1" />
                                <h:selectOneMenu value="#{ManagedDevolucionAlmacen.buscarDevolucion.salidasAlmacen.areasEmpresa.codAreaEmpresa}" styleClass="inputText" >
                                    <f:selectItems value="#{ManagedDevolucionAlmacen.listaAreasEmpresa}"  />
                                </h:selectOneMenu>

                                 <h:outputText value="Capitulo : " styleClass="outputText1" />
                                 <h:selectOneMenu value="#{ManagedDevolucionAlmacen.buscarMaterial.grupos.capitulos.codCapitulo}" styleClass="inputText" >
                                     <f:selectItems value="#{ManagedDevolucionAlmacen.listaCapitulos}"  />
                                     <a4j:support event="onchange" action="#{ManagedDevolucionAlmacen.onChangeCapitulo}" reRender="grupos,productos"/>
                                </h:selectOneMenu>
                                 <h:outputText value="Grupo : " styleClass="outputText1" />
                                 <h:selectOneMenu value="#{ManagedDevolucionAlmacen.buscarMaterial.grupos.codGrupo}" styleClass="inputText" id="grupos">
                                     <f:selectItems value="#{ManagedDevolucionAlmacen.listagrupos}"  />
                                     <a4j:support event="onchange" action="#{ManagedDevolucionAlmacen.onChange_grupo}" reRender="productos"/>
                                </h:selectOneMenu>
                                 <h:outputText value="Item : " styleClass="outputText1" />
                                 <h:selectOneMenu value="#{ManagedDevolucionAlmacen.buscarMaterial.codMaterial}" styleClass="inputText" id="productos">
                                     <f:selectItems value="#{ManagedDevolucionAlmacen.listaMateriales}"  />
                                </h:selectOneMenu>



                            </h:panelGrid>
                                <div align="center">
                                <a4j:commandButton styleClass="btn" value="Buscar" onclick="javascript:Richfaces.hideModalPanel('panelBuscarDevolucion');"
                                action="#{ManagedDevolucionAlmacen.buscarDevolución_Action}" reRender="dataDevolucionesAlmacen,controles" />
                                <input type="button" value="Cancelar" onclick="javascript:Richfaces.hideModalPanel('panelBuscarDevolucion')" class="btn" />
                                </div>
                        </h:panelGroup>
                        </a4j:form>
            </rich:modalPanel>
            <%--final alejandro buscador--%>
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

