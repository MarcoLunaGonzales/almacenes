

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
            <%--inicio alejandro 2--%>
            <style>
                .b
                {

                    background-color:#FF0000;
                }
            </style>
            <%--final alejandro 2--%>
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
                    <h:outputText value="#{ManagedSalidaAlmacen.cargarContenidoSalidasAlmacen}"/>
                    <h:outputText styleClass="outputTextTitulo"  value="Salidas Almacen" />
                    <br>
                        <a4j:commandLink onclick="Richfaces.showModalPanel('panelBuscarSalidaAlmacen')">
                            <h:outputText value="Buscar" styleClass="outputText1" />
                            <h:graphicImage url="../img/buscar.png">
                        
                            </h:graphicImage>
                        </a4j:commandLink>
                        <br>
                    <rich:dataTable value="#{ManagedSalidaAlmacen.salidasAlmacenList}" var="data"
                                    id="dataSalidasAlmacen"
                                    onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                    onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                    headerClass="headerClassACliente" >
                        <rich:column styleClass ="#{data.colorFila}">
                            <f:facet name="header">
                                <h:outputText value=""  />
                            </f:facet>
                            <h:selectBooleanCheckbox value="#{data.checked}" rendered="#{data.estadosSalidasAlmacen.codEstadoSalidaAlmacen=='1' ||  data.estadosSalidasAlmacen.codEstadoSalidaAlmacen=='2' ||  data.estadosSalidasAlmacen.codEstadoSalidaAlmacen=='5' ||  data.estadosSalidasAlmacen.codEstadoSalidaAlmacen=='6'}"  />
                        </rich:column>
                        <rich:column styleClass ="#{data.colorFila}">
                            <f:facet name="header">
                                <h:outputText value="Nro Salida"  />
                            </f:facet>
                            <h:outputText  value="#{data.nroSalidaAlmacen}"  />
                        </rich:column>
                        <rich:column styleClass ="#{data.colorFila}">
                            <f:facet name="header">
                                <h:outputText value="Fecha Salida"  />
                            </f:facet>
                            <h:outputText value="#{data.fechaSalidaAlmacen}">
                                <f:convertDateTime pattern="dd/MM/yyyy HH:mm" />
                            </h:outputText>
                        </rich:column>
                        <rich:column styleClass ="#{data.colorFila}">
                            <f:facet name="header">
                                <h:outputText value="Nro de Lote"  />
                            </f:facet>
                            <h:outputText value="#{data.codLoteProduccion}"  />
                        </rich:column>
                        <rich:column styleClass ="#{data.colorFila}">
                            <f:facet name="header">
                                <h:outputText value="Nro O.T."  />
                            </f:facet>
                            <h:outputText value="#{data.ordenTrabajo}"  />
                        </rich:column>


                        <rich:column styleClass ="#{data.colorFila}" rendered="#{ManagedRegistroSalidaAlmacen.usuario.almacenesGlobal.codAlmacen != '14'}" >
                            <f:facet name="header">
                                <h:outputText value="Producto"  />
                            </f:facet>
                            <h:outputText value="#{data.componentesProd.nombreProdSemiterminado}"  />
                        </rich:column>

                        <rich:column styleClass ="#{data.colorFila}" rendered="#{ManagedRegistroSalidaAlmacen.usuario.almacenesGlobal.codAlmacen != '14'}">
                            <f:facet name="header">
                                <h:outputText value="Presentacion"  />
                            </f:facet>
                            <h:outputText value="#{data.presentacionesProducto.nombreProductoPresentacion}"  />
                        </rich:column>
                        <rich:column styleClass ="#{data.colorFila}" rendered="#{ManagedRegistroSalidaAlmacen.usuario.almacenesGlobal.codAlmacen == '14'}">
                            <f:facet name="header">
                                <h:outputText value="Maquinaria"  />
                            </f:facet>
                            <h:outputText value="#{data.maquinaria.nombreMaquina}"  />
                        </rich:column>

                        <rich:column styleClass ="#{data.colorFila}">
                            <f:facet name="header">
                                <h:outputText value="Responsable Salida"  />
                            </f:facet>
                            <h:outputText value="#{data.personal.nombrePersonal}"  />
                        </rich:column>

                          <rich:column styleClass ="#{data.colorFila}">
                            <f:facet name="header">
                                <h:outputText value="Area / Departamento"  />
                            </f:facet>
                            <h:outputText value="#{data.areasEmpresa.nombreAreaEmpresa}"  />
                          </rich:column>

                          <rich:column styleClass ="#{data.colorFila}">
                            <f:facet name="header">
                                <h:outputText value="Estado Salida"  />
                            </f:facet>
                            <h:outputText value="#{data.estadosSalidasAlmacen.nombreEstadoSalidaAlmacen}"  />
                            <h:outputText value="<br/>(#{data.estadosTransaccionSalida.nombreEstadoTransaccionSalida})" escape="false" rendered="#{data.estadosTransaccionSalida.nombreEstadoTransaccionSalida !=''}"  />
                         </rich:column>

                         <rich:column styleClass ="#{data.colorFila}">
                            <f:facet name="header">
                                <h:outputText value="Tipo Salida"  />
                            </f:facet>
                            <h:outputText value="#{data.tiposSalidasAlmacen.nombreTipoSalidaAlmacen}"  />
                         </rich:column>
                         <rich:column styleClass ="#{data.colorFila}">
                            <f:facet name="header">
                                <h:outputText value="Observaciones"  />
                            </f:facet>
                            <h:outputText value="#{data.obsSalidaAlmacen}"  />
                         </rich:column>
                         <%--h:column>
                            <f:facet name="header">
                                <h:outputText value=""  />
                            </f:facet>
                            <a4j:commandLink onclick="openPopup('navegadorDetalleSalidasAlmacen.jsf?codSalidaAlmacen=#{data.codSalidaAlmacen}&codAlmacen=#{data.almacenes.codAlmacen}')">
                                <h:graphicImage url="../img/areasdependientes.png" >
                                </h:graphicImage>
                            </a4j:commandLink>

                         </h:column--%>

                    </rich:dataTable>
                    
                     <h:panelGrid columns="2"  width="50" id="controles">
                            <h:commandLink  action="#{ManagedSalidaAlmacen.atras_action}"   rendered="#{ManagedSalidaAlmacen.begin!='1'}" >
                                <h:graphicImage url="../img/previous.gif"  style="border:0px solid red"   alt="PAGINA ANTERIOR"  />
                            </h:commandLink>
                            <h:commandLink  action="#{ManagedSalidaAlmacen.siguiente_action}"  rendered="#{ManagedSalidaAlmacen.cantidadfilas>='5'}">
                                <h:graphicImage url="../img/next.gif"  style="border:0px solid red"  alt="PAGINA SIGUIENTE" />
                            </h:commandLink>
                    </h:panelGrid>

                    

                    <%-- <ws:datascroller fordatatable="dataAreasEmpresa"  />--%>
                    <br>
                    <h:panelGroup rendered="#{ManagedSalidaAlmacen.administradorAlmacen}">
                        <a4j:commandButton value="Generar Salida Almacen" styleClass="btn"  onclick="location='agregarSalidaAlmacen.jsf'" rendered="#{ManagedSalidaAlmacen.administradorAlmacen}"  />
                        
                        <a4j:commandButton value="Editar" styleClass = "btn" action="#{ManagedSalidaAlmacen.editarSalidaAlmacen_action}" oncomplete="if(#{ManagedSalidaAlmacen.mensaje!=''}){alert('#{ManagedSalidaAlmacen.mensaje}');}else{location='editarSalidaAlmacen.jsf';}" />
                        <a4j:commandButton value="Anular" styleClass = "btn" action="#{ManagedSalidaAlmacen.anularSalidasAlmacen_action}"onclick="if(confirm('Esta seguro de anular esta salida?')==false){return false;}" oncomplete="if(#{ManagedSalidaAlmacen.mensaje!=''}){alert('#{ManagedSalidaAlmacen.mensaje}');}else {alert('La solicitud fue anulada');}" reRender="dataSalidasAlmacen" />
                        <a4j:commandButton value="Solicitud Anular" styleClass = "btn" onclick="if(editarItem('form1:dataSalidasAlmacen')){if(!confirm('Esta seguro se solicitar la anulacion?')){return false;}}else{return false;}"
                        action="#{ManagedSalidaAlmacen.solicitudAnularEditarSalidaAlmacen_action}" rendered="#{ManagedSalidaAlmacen.usuario.almacenesGlobal.codAlmacen eq '1'}"
                        oncomplete="if(#{ManagedSalidaAlmacen.mensaje!=''}){alert('#{ManagedSalidaAlmacen.mensaje}');}" reRender="dataSalidasAlmacen" >
                            <f:param name="codEstadoTransaccionSalida" value="1" />
                        </a4j:commandButton>
                        <a4j:commandButton value="Solicitud Editar" styleClass = "btn" action="#{ManagedSalidaAlmacen.solicitudAnularEditarSalidaAlmacen_action}"
                        onclick="if(editarItem('form1:dataSalidasAlmacen')){if(!confirm('Esta seguro se solicitar la edicion?')){return false;}}else{return false;}" rendered="#{ManagedSalidaAlmacen.usuario.almacenesGlobal.codAlmacen eq '1'}"
                        oncomplete="if(#{ManagedSalidaAlmacen.mensaje!=''}){alert('#{ManagedSalidaAlmacen.mensaje}');}" reRender="dataSalidasAlmacen" >
                            <f:param name="codEstadoTransaccionSalida" value="2" />
                        </a4j:commandButton>
                        <a4j:commandButton value="Modificar Fecha" styleClass = "btn" action="#{ManagedSalidaAlmacen.selecccionarSalidaFechaModificar}"
                        oncomplete="Richfaces.showModalPanel('panelModificarFechaSalida');"  reRender="contenidoAgregarMaterial"
                        onclick="if(!editarItem('form1:dataSalidasAlmacen')){return false;}"
                        rendered="#{ManagedAccesoSistema.usuarioModuloBean.codUsuarioGlobal eq '820'}"
                        />
                    </h:panelGroup>
<a4j:commandButton value="Imprimir" styleClass = "btn" action="#{ManagedSalidaAlmacen.verReporteSalidaAlmacen_action}" oncomplete="openPopup('reporteSalidaAlmacenJasper.jsf?reporte=reporteSalida.jasper');" rendered="#{ManagedSalidaAlmacen.usuario.almacenesGlobal.codAlmacen!='12' && ManagedSalidaAlmacen.usuario.almacenesGlobal.codAlmacen!='14'}" />
                        <a4j:commandButton value="Imprimir" styleClass = "btn" action="#{ManagedSalidaAlmacen.verReporteSalidaAlmacen_action}" oncomplete="openPopup('reporteSalidaAlmacenJasper.jsf?reporte=reporteSalidaAlmacenProveedor.jasper');" rendered="#{ManagedSalidaAlmacen.usuario.almacenesGlobal.codAlmacen=='12'}" />
                        <a4j:commandButton value="Imprimir Mantenimiento" styleClass = "btn" action="#{ManagedSalidaAlmacen.verReporteSalidaAlmacen_action}" oncomplete="openPopup('reporteSalidasAlmacenMantenimiento.jsf');" rendered="#{ManagedSalidaAlmacen.usuario.almacenesGlobal.codAlmacen=='14' || ManagedSalidaAlmacen.usuario.almacenesGlobal.codAlmacen=='4' }" />
                        <a4j:commandButton value="Imprimir con Maquinaria" styleClass = "btn" action="#{ManagedSalidaAlmacen.verReporteSalidaAlmacen_action}" oncomplete="openPopup('reporteSalidaAlmacenJasper.jsf?reporte=reporteSalidaConMaquinaria.jasper');" />






                        
                    <%--
                    <a4j:commandButton value="Generar Salida Almacen" styleClass="btn"  action="#{ManagedIngresoAlmacenOrdenCompra.generarIngresoAlmacen_action}"
                    reRender="contenidoFrecuenciaMantenimentoMaquinaria" oncomplete="if(#{ManagedIngresoAlmacenOrdenCompra.mensaje!=''}){alert('#{ManagedIngresoAlmacenOrdenCompra.mensaje}')}" />
                    --%>

                </div>

               
                <!--cerrando la conexion-->
                <h:outputText value="#{ManagedFrecuenciaMantenimientoMaquinaria.closeConnection}"  />

            </a4j:form>

            <rich:modalPanel id="panelModificarFechaSalida" minHeight="250"  minWidth="650"
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
                                <h:outputText value="Nro Salida" styleClass="outputText2" />
                                <h:outputText value="::" styleClass="outputText2" />
                                <h:outputText value="#{ManagedSalidaAlmacen.salidasAlmacenFecha.nroSalidaAlmacen}" styleClass="outputText2" />
                                <h:outputText value="Fecha" styleClass="outputText2" />
                                <h:outputText value="::" styleClass="outputText2" />
                                <rich:calendar value="#{ManagedSalidaAlmacen.salidasAlmacenFecha.fechaSalidaAlmacen}" styleClass="inputText"
                                datePattern="dd/MM/yyyy"/>

                            </h:panelGrid>



                                <div align="center">
                                <a4j:commandButton styleClass="btn" value="Guardar"
                                action="#{ManagedSalidaAlmacen.guardarCambiofechaSalida_action}"
                                oncomplete="if(#{ManagedRegistroSalidaAlmacen.mensaje!=''}){alert('#{ManagedRegistroSalidaAlmacen.mensaje}')}else{javascript:Richfaces.hideModalPanel('panelModificarFechaSalida')}"
                                            reRender="dataSalidasAlmacen,controles" timeout="7200"
                                                    />
                                <input type="button" value="Cancelar" onclick="javascript:Richfaces.hideModalPanel('panelModificarFechaSalida')" class="btn" />
                                </div>
                        </h:panelGroup>
                        </a4j:form>
            </rich:modalPanel>
            <rich:modalPanel id="panelBuscarSalidaAlmacen"  minHeight="220"  minWidth="1000"
                                     height="220" width="1000"
                                     zindex="200"
                                     headerClass="headerClassACliente"
                                     resizeable="false" style="overflow :auto" >
                        <f:facet name="header">
                            <h:outputText value="Buscar Salida Almacen"/>
                        </f:facet>
                        <a4j:form>
                        <h:panelGroup id="contenidoBuscarSalidaAlmacen">
                            <h:panelGrid columns="4">

                                <h:outputText value="Gestion:" styleClass="outputText1" />
                                <h:selectOneMenu value="#{ManagedSalidaAlmacen.salidasAlmacenBuscar.gestiones.codGestion}" styleClass="inputText" >
                                    <f:selectItems value="#{ManagedSalidaAlmacen.gestionesList}"  />
                                </h:selectOneMenu>

                                <h:outputText value="Nro Salida:" styleClass="outputText1" />
                                <h:inputText value="#{ManagedSalidaAlmacen.salidasAlmacenBuscar.nroSalidaAlmacen}" styleClass="inputText" size="5" onkeypress="valNum(event);" />
                                
                                <h:outputText value="Nro Lote:" styleClass="outputText1" />
                                <h:inputText value="#{ManagedSalidaAlmacen.salidasAlmacenBuscar.codLoteProduccion}" styleClass="inputText" size="5" onkeypress="valNum(event);" />

                                <h:outputText value="Tipo Salida:" styleClass="outputText1" />
                                <h:selectOneMenu value="#{ManagedSalidaAlmacen.salidasAlmacenBuscar.tiposSalidasAlmacen.codTipoSalidaAlmacen}" styleClass="inputText" >
                                    <f:selectItems value="#{ManagedSalidaAlmacen.tiposSalidaAlmacenList}"  />
                                </h:selectOneMenu>

                                


                                <h:outputText value="Fecha inicio Salida" styleClass="outputText1" />
                                <rich:calendar  datePattern="dd/MM/yyyy" styleClass="inputText" zindex="200" value="#{ManagedSalidaAlmacen.salidasAlmacenBuscar.fechaSalidaAlmacen}" id="fechaInicio" enableManualInput="true" oninputblur="if(this.value!=''){valFecha(this)}else{return true;}"  />

                                <h:outputText value="Estados Salida Almacen:" styleClass="outputText1" />
                                <h:selectOneMenu value="#{ManagedSalidaAlmacen.salidasAlmacenBuscar.estadosSalidasAlmacen.codEstadoSalidaAlmacen}" styleClass="inputText" >
                                    <f:selectItems value="#{ManagedSalidaAlmacen.estadosSalidaAlmacenList}"  />
                                </h:selectOneMenu>

                                <h:outputText value="Area Destino:" styleClass="outputText1" />
                                <h:selectOneMenu value="#{ManagedSalidaAlmacen.salidasAlmacenBuscar.areasEmpresa.codAreaEmpresa}" styleClass="inputText" >
                                    <f:selectItems value="#{ManagedSalidaAlmacen.areasEmpresalist}"  />
                                </h:selectOneMenu>

                                <h:outputText value="Producto:" styleClass="outputText1" />
                                <h:selectOneMenu value="#{ManagedSalidaAlmacen.salidasAlmacenBuscar.componentesProd.codCompprod}" styleClass="inputText" >
                                    <f:selectItems value="#{ManagedSalidaAlmacen.componentesProdList}"  />
                                </h:selectOneMenu>

                                <h:outputText value="Orden de Trabajo:" styleClass="outputText1" />
                                <h:inputText value="#{ManagedSalidaAlmacen.salidasAlmacenBuscar.ordenTrabajo}" styleClass="inputText" size="5" onkeypress="valNum(event);" />
                                
                                
                                
                            </h:panelGrid>
                                <div align="center">
                                <a4j:commandButton styleClass="btn" value="Buscar" onclick="javascript:Richfaces.hideModalPanel('panelBuscarSalidaAlmacen');"
                                                   action="#{ManagedSalidaAlmacen.buscarSalidaAlmacen_action}"
                                                   reRender="dataSalidasAlmacen,controles"
                                                    />
                                <input type="button" value="Cancelar" onclick="javascript:Richfaces.hideModalPanel('panelBuscarSalidaAlmacen')" class="btn" />
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

