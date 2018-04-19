
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
                    <h:outputText value="#{ManagedSolicitudDevoluciones.cargarSolicitudesAprobar}"/>
                    <h:outputText styleClass="outputTextTitulo"  value="Aprobar Solicitudes de devolución" />
                   <%-- <br><br>
                        <a4j:commandLink onclick="Richfaces.showModalPanel('panelBuscarSalidaAlmacen')">
                        <h:outputText value="Buscar Salida Almacen" styleClass="outputText1" />
                        <h:graphicImage url="../img/buscar.png">

                        </h:graphicImage>
                    </a4j:commandLink>
                    --%>
                    <br>
                        <a4j:commandLink onclick="Richfaces.showModalPanel('panelBuscarSolicitudSalida')">
                        <h:outputText value="Buscar Solicitud" styleClass="outputText1" />
                        <h:graphicImage url="../img/buscar.png">

                        </h:graphicImage>
                        </a4j:commandLink>

                    <rich:dataTable value="#{ManagedSolicitudDevoluciones.solicitudesAprobarList}"
                                    var="data"
                                    id="dataDevolucionesAlmacen"
                                    onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                    onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                    headerClass="headerClassACliente" >
                         <h:column>
                            <f:facet name="header">
                                <h:outputText value=" "  />
                            </f:facet>
                            <h:selectBooleanCheckbox value="#{data.checked}" rendered="#{data.estadoSolicitudDevolucion.codEstado=='1' || data.estadoSolicitudDevolucion.codEstado=='5'}"/>
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Nro Solicitud"  />
                            </f:facet>
                            <h:outputText  value="#{data.codSolicitudDevolucion}"  />
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Estado"  />
                            </f:facet>
                            <h:outputText value="#{data.estadoSolicitudDevolucion.nombreEstado}">
                            </h:outputText>
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Solicitante"  />
                            </f:facet>
                            <h:outputText value="#{data.personalSolicitante.nombrePersonal}"  />
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Fecha Solicitud"  />
                            </f:facet>
                            <h:outputText value="#{data.fechaSolicitud}"  >
                                <f:convertDateTime pattern="dd/MM/yyyy" />
                            </h:outputText>
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="nro Salida"  />
                            </f:facet>
                            <h:outputText value="#{data.salidaAlmacen.nroSalidaAlmacen}"  />
                        </h:column>
                         <h:column>
                            <f:facet name="header">
                                <h:outputText value="Almacen"  />
                            </f:facet>
                            <h:outputText value="#{data.salidaAlmacen.almacenes.nombreAlmacen}"  />
                          </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Lote"  />
                            </f:facet>
                            <h:outputText value="#{data.salidaAlmacen.codLoteProduccion}"  />
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Producto"  />
                            </f:facet>
                            <h:outputText value="#{data.salidaAlmacen.producto.nombreProducto}"  />
                        </h:column>
                        <h:column>
                                        <f:facet name="header">
                                            <h:outputText value=""  />
                                        </f:facet>
                                        <a4j:commandLink onclick="openPopup('../reportes/solicitudesDevoluciones/reporteDetalleSolitudesDevoluciones.jsp?&cod=#{data.codSolicitudDevolucion}')" >
                                            <h:graphicImage url="../img/organigrama3.jpg" />
                                        </a4j:commandLink>
                         </h:column>
                    </rich:dataTable>
                    <h:panelGrid columns="2"  width="50" id="controles">
                        <a4j:commandLink  action="#{ManagedSolicitudDevoluciones.atras_action_2}" reRender="dataDevolucionesAlmacen,controles" rendered="#{ManagedSolicitudDevoluciones.begin!='1'}" >
                                <h:graphicImage url="../img/previous.gif"  style="border:0px solid red"   alt="PAGINA ANTERIOR"  />
                            </a4j:commandLink>
                            <a4j:commandLink  action="#{ManagedSolicitudDevoluciones.siguiente_action_2}" reRender="dataDevolucionesAlmacen,controles" rendered="#{ManagedSolicitudDevoluciones.cantidadfilas>='5'}">
                                <h:graphicImage url="../img/next.gif"  style="border:0px solid red"  alt="PAGINA SIGUIENTE" />
                            </a4j:commandLink>
                    </h:panelGrid>
                   <%-- <a4j:commandButton value="Imprimir" action="#{ManagedSolicitudDevoluciones.imprimirDevolucion_action}" oncomplete="openPopup('../reportes/devolucionesAlmacen/reporteDevolucionesAlmacen.jsf')" styleClass="btn" />
                    <a4j:commandButton value="Editar" action="#{ManagedSolicitudDevoluciones.editarDevolucionesAlmacen_action}" oncomplete="location='editarDevolucionesAlmacen.jsf'" styleClass="btn" />
                  
                    <a4j:commandButton action="#{ManagedSolicitudDevoluciones.aprobarSolicitud_action}"  reRender="dataDevolucionesAlmacen,controles" onclick="if(confirm('Esta seguro de aprobar la solicitud?')){}else{return false;}" value="Aprobar Solicitud" styleClass="btn" />

                      --%>
                    <%-- <ws:datascroller fordatatable="dataAreasEmpresa"  />--%>
                    <h:panelGroup rendered="#{ManagedSolicitudDevoluciones.administradorAlmacen}">
                        <a4j:commandButton action="#{ManagedSolicitudDevoluciones.aprobarSolicitud}" oncomplete="location='aprobarSolicitudDevoluciones.jsf'" value="Aprobar Solicitud" styleClass="btn" />
                    </h:panelGroup>
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
                                <h:inputText value="#{ManagedSolicitudDevoluciones.salidasAlmacenBuscar.nroSalidaAlmacen}"  id="nroSalida" styleClass="inputText" size="5" onkeypress="valNum(event);" />

                                <h:outputText value="Nro Lote :" styleClass="outputText1" />
                                <h:inputText value="#{ManagedSolicitudDevoluciones.salidasAlmacenBuscar.codLoteProduccion}" id="nroLote" styleClass="inputText" size="5" onkeypress="valNum(event);" />

                                <h:outputText value="Producto :" styleClass="outputText1" />
                                <h:selectOneMenu value="#{ManagedSolicitudDevoluciones.salidasAlmacenBuscar.componentesProd.codCompprod}" id="codCompProd" styleClass="inputText" style="width=290px" >
                                    <f:selectItems value="#{ManagedSolicitudDevoluciones.componentesProdList}"  />
                                </h:selectOneMenu>
                                <a4j:commandButton styleClass="btn" value="Buscar" 
                                action="#{ManagedSolicitudDevoluciones.buscarSalidaAlmacen_action}"
                                                   reRender="dataSalidasAlmacenBuscar,controlesBuscar"
                                                   onclick="valida()"
                                                    />
                            </h:panelGrid>
                            
                            <div style="overflow:auto;height:250px;width:80%;text-align:center">
                                <rich:dataTable value="#{ManagedSolicitudDevoluciones.salidasAlmacenBuscarList}"
                                    var="data"
                                    id="dataSalidasAlmacenBuscar"
                                    onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                    onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                    headerClass="headerClassACliente"  >



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
                                        <h:outputText value="#{data.fechaSalidaAlmacen}" >
                                         <f:convertDateTime pattern="dd/MM/yyyy" />
                                        </h:outputText>
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
                                        <a4j:commandLink action="#{ManagedSolicitudDevoluciones.verReporteSalidaAlmacen_action}" oncomplete="openPopup('../reportes/salidasAlmacen/reporteDetalleSalidasAlmacen.jsp')" >
                                            <h:graphicImage url="../img/organigrama3.jpg" />
                                        </a4j:commandLink>
                                      </h:column>

                            </rich:dataTable>
                            </div>

                            <h:panelGrid columns="2"  width="50" id="controlesBuscar">
                                <a4j:commandLink  action="#{ManagedSolicitudDevoluciones.atras_action}" reRender="dataSalidasAlmacenBuscar,controlesBuscar"  rendered="#{ManagedDevolucionAlmacen.inicioFila!='1'}" >
                                        <h:graphicImage url="../img/previous.gif"  style="border:0px solid red"   alt="PAGINA ANTERIOR"  />
                                    </a4j:commandLink>
                                    <a4j:commandLink  action="#{ManagedSolicitudDevoluciones.siguiente_action}" reRender="dataSalidasAlmacenBuscar,controlesBuscar" rendered="#{ManagedDevolucionAlmacen.cantidadFilasBuscar >='5'}">
                                        <h:graphicImage url="../img/next.gif"  style="border:0px solid red"  alt="PAGINA SIGUIENTE" />
                                    </a4j:commandLink>
                            </h:panelGrid>


                                <br/>
                                <a4j:commandButton styleClass="btn" value="Generar Solicitud"
                                                   onclick="if(validarSeleccion('form2:dataSalidasAlmacenBuscar')==false){return false;}"
                                                   action="#{ManagedSolicitudDevoluciones.generarDevolucionSalidaAlmacen_action}"
                                                   oncomplete="location='agregarSolicitudDevoluciones.jsf'"
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
             <rich:modalPanel id="panelBuscarSolicitudSalida"  minHeight="200"  minWidth="750"
                                     height="200" width="750"
                                     zindex="200"
                                     headerClass="headerClassACliente"
                                     resizeable="false" style="overflow :auto" >
                        <f:facet name="header">
                            <h:outputText value="Buscar Solicitud Almacen"/>
                        </f:facet>
                        <a4j:form>
                        <%--inicio alejandro--%>
                        <h:panelGroup id="contenidoBuscarSolicitudAlmacen">
                            <h:panelGrid columns="4">


                                <h:outputText value="Nro Solicitud:" styleClass="outputText1" />
                                <h:inputText value="#{ManagedSolicitudDevoluciones.buscarSolicitudAprobar.codSolicitudDevolucion}" styleClass="inputText" size="5" onkeypress="valNum(event);" />

                                <h:outputText value="Estado:" styleClass="outputText1" />
                                <h:selectOneMenu value="#{ManagedSolicitudDevoluciones.buscarSolicitudAprobar.estadoSolicitudDevolucion.codEstado}" styleClass="inputText" >
                                    <f:selectItems value="#{ManagedSolicitudDevoluciones.estadosSolicitudesList}"  />
                                </h:selectOneMenu>
                                 <h:outputText value="Fecha inicio solicitud" styleClass="outputText1" />
                                 <rich:calendar  datePattern="dd/MM/yyyy" styleClass="inputText" zindex="200" value="#{ManagedSolicitudDevoluciones.fechaInicio1}" id="fechaInicio" enableManualInput="true" oninputblur="if(this.value!=''){valFecha(this)}else{return true;}"  />
                                 <h:outputText value="Fecha final solicitud" styleClass="outputText1" />
                                 <rich:calendar  datePattern="dd/MM/yyyy" styleClass="inputText" zindex="200" value="#{ManagedSolicitudDevoluciones.fechaFinal1}" id="fechaFinal" enableManualInput="true" oninputblur="if(this.value!=''){valFecha(this)}else{return true;}"  />

                                  <h:outputText value="Nro Salida:" styleClass="outputText1" />
                                  <h:inputText value="#{ManagedSolicitudDevoluciones.buscarSolicitudAprobar.salidaAlmacen.nroSalidaAlmacen}" styleClass="inputText" size="5" onkeypress="valNum(event);" />


                                <h:outputText value="Almacen:" styleClass="outputText1" />
                                <h:selectOneMenu value="#{ManagedSolicitudDevoluciones.buscarSolicitudAprobar.salidaAlmacen.almacenes.codAlmacen}" styleClass="inputText" style="width:230px">
                                    <f:selectItems value="#{ManagedSolicitudDevoluciones.almacenesList}"  />
                                </h:selectOneMenu>
                                 <h:outputText value="Producto:" styleClass="outputText1" />
                                 <h:selectOneMenu value="#{ManagedSolicitudDevoluciones.buscarSolicitudAprobar.salidaAlmacen.producto.codProducto}" styleClass="inputText" style="width:230px" >
                                     <f:selectItems value="#{ManagedSolicitudDevoluciones.productosList}"  />
                                </h:selectOneMenu>
                                 <h:outputText value="Personal Solicitante:" styleClass="outputText1" />
                                 <h:selectOneMenu value="#{ManagedSolicitudDevoluciones.buscarSolicitudAprobar.personalSolicitante.codPersonal}" styleClass="inputText" style="width:230px" >
                                     <f:selectItems value="#{ManagedSolicitudDevoluciones.personalList}"  />
                                </h:selectOneMenu>

                            </h:panelGrid>
                                <div align="center">

                                <a4j:commandButton styleClass="btn" value="Buscar" onclick="javascript:Richfaces.hideModalPanel('panelBuscarSolicitudSalida');"
                                action="#{ManagedSolicitudDevoluciones.buscarSolicitudDevolucionAprobarAction}"
                                                   reRender="dataDevolucionesAlmacen,controles"
                                                    />
                                <input type="button" value="Cancelar" onclick="javascript:Richfaces.hideModalPanel('panelBuscarSolicitudSalida')" class="btn" />
                                </div>
                        </h:panelGroup>
                         <%--final alejandro--%>
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

