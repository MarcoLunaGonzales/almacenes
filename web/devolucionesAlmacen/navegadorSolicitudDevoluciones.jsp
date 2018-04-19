
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
                    window.open(url,'DETALLE'+Math.round((Math.random()*1000)),'top=50,left=200,width=800,height=600,scrollbars=1,resizable=1');
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
        function validaMantenimiento(){
            
                     if(document.getElementById("form2:nroSalida1").value==''){
                         document.getElementById("form2:nroSalida1").value = 0;
                     }
                     if(document.getElementById("form2:nroSalida1").value==0 && document.getElementById("form2:nroOT").value=='' && document.getElementById("form2:codMaquina").value==0    ){
                         alert('Seleccione un dato para la busqueda');
                         return false;
                     }
                     return true;
                }
        onerror=function (){
        alert('existe un error al momento de cargar el registro, por favor recargue la pagina');
        }

        //final ale
            A4J.AJAX.onError = function(req,status,message){
            window.alert("Ocurrio un error: "+message);
            }
            A4J.AJAX.onExpired = function(loc,expiredMsg){
            if(window.confirm("Ocurrio un error al momento realizar la transaccion: "+expiredMsg+" location: "+loc)){
            return loc;
            } else {
            return false;
            }
            }
                
       </script>
       <style type="text/css">
           .ultimaSolicitud
           {
                background-color:#e6f8e9;
                border:1px solid #5fc569 !important;
           }
           .anulado
           {
                background-color:#ffdddd;
                border:1px solid #d65656 !important;
           }
           .dr-table-headercell
           {
               color:#111 !important;
               font-weight: bold;
           }
       </style>
        </head>
        <body >
            <a4j:form id="form1">
                <div align="center">
                    <h:outputText value="#{ManagedSolicitudDevoluciones.cargarContenidoDevolucionAlmacen}"/>
                    <h:outputText styleClass="outputTextTitulo"  value="Solicitudes de devolución" />
                    <br>
                        <a4j:commandLink onclick="Richfaces.showModalPanel('panelBuscarSolicitudSalida')">
                        <h:outputText value="Buscar Solicitud" styleClass="outputText1" />
                        <h:graphicImage url="../img/buscar.png">

                        </h:graphicImage>
                        </a4j:commandLink>
                    <table cellpading="0" cellspacing="0">
                        <tr>
                            <td class="outputText2" style="font-weight: bold">Ultimas solicitudes generadas</td>
                            <td class="ultimaSolicitud" style="width:105px">&nbsp;</td>
                            <td class="outputText2" style="font-weight: bold">Solicitudes anuladas</td>
                            <td class="anulado" style="width:105px">&nbsp;</td>
                        </tr>
                    </table>
                    <br/>
                    <rich:dataTable value="#{ManagedSolicitudDevoluciones.solicitudesList}"
                                    var="data" 
                                    id="dataDevolucionesAlmacen"
                                    footerClass="headerClassACliente"
                                    headerClass="headerClassACliente" >
                        <f:facet name="header">
                            <rich:columnGroup>
                                <rich:column>
                                    <h:outputText value="Nro Solicitud"  />
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Estado"  />
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Solicitante"  />
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Fecha Solicitud"  />
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="nro Salida"  />
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Almacen"  />
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Producto"  />
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Almacen Destino"/>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Acciones"/>
                                </rich:column>
                            </rich:columnGroup>
                        </f:facet>
                        <rich:columnGroup styleClass="#{data.checked ? 'ultimaSolicitud' : ''} #{data.estadoSolicitudDevolucion.codEstado eq 6 ?'anulado' : ''}">
                            <rich:column>
                                <h:outputText  value="#{data.codSolicitudDevolucion}"  />
                            </rich:column>
                            <rich:column>
                                <h:outputText value="#{data.estadoSolicitudDevolucion.nombreEstado}"/>
                            </rich:column>
                            <rich:column>
                                <h:outputText value="#{data.personalSolicitante.nombrePersonal}"  />
                            </rich:column>
                            <rich:column>
                                <h:outputText value="#{data.fechaSolicitud}"  >
                                    <f:convertDateTime pattern="dd/MM/yyyy" />
                                </h:outputText>
                            </rich:column>
                            <rich:column>
                                <h:outputText value="#{data.salidaAlmacen.nroSalidaAlmacen}"  />
                            </rich:column>
                            <rich:column>
                                <h:outputText value="#{data.salidaAlmacen.almacenes.nombreAlmacen}"  />
                            </rich:column>
                            <rich:column>
                                <h:outputText value="#{data.salidaAlmacen.producto.nombreProducto}"  />
                            </rich:column>
                            <rich:column>
                                <h:outputText value="#{data.almacenDestino.nombreAlmacen}"/>
                            </rich:column>
                            <rich:column>
                                <rich:dropDownMenu >
                                    <f:facet name="label">
                                        <h:panelGroup>
                                            <h:outputText value="Acciones"/>
                                        </h:panelGroup>
                                    </f:facet>
                                    <rich:menuItem  submitMode="none"  value="Ver Solicitud" 
                                                    onclick="openPopup('../reportes/solicitudesDevoluciones/reporteDetalleSolitudesDevoluciones.jsp?&cod=#{data.codSolicitudDevolucion}')"/>
                                    <rich:menuItem  submitMode="none"  value="Imprimir Etiquetas" 
                                                    onclick="openPopup('reporteEtiquetas.jsf?codSolicitudDevolucion=#{data.codSolicitudDevolucion}');"/>
                                    <rich:menuItem  submitMode="none"  value="Imprimir Etiquetas Altura 209" 
                                                    onclick="openPopup('reporteEtiquetas_H209.jsf?codSolicitudDevolucion=#{data.codSolicitudDevolucion}');"/>
                                    <rich:menuItem  submitMode="none"  value="Anular" style="border-top:1px solid #aaa" rendered="#{data.estadoSolicitudDevolucion.codEstado eq 1}">
                                        <a4j:support event="onclick" reRender="dataDevolucionesAlmacen" 
                                                     action="#{ManagedSolicitudDevoluciones.anularSolicitudDevolucion_action}"
                                                     oncomplete="if(#{ManagedSolicitudDevoluciones.mensaje eq '1'}){alert('Se anulo Satisfactoriamente la solicitud')}else{alert('#{ManagedSolicitudDevoluciones.mensaje}')}" >
                                            <f:setPropertyActionListener target="#{ManagedSolicitudDevoluciones.solicitudDevolucionAnular}" value="#{data}"/>
                                        </a4j:support>
                                    </rich:menuItem>
                                    
                                </rich:dropDownMenu>
                            </rich:column>
                        </rich:columnGroup>
                        <f:facet name="footer">
                            <rich:columnGroup>
                                <rich:column colspan="9" style="padding:0px !important;text-align:center">
                                    <h:panelGrid columns="2"  width="50" id="controles">
                                        <a4j:commandLink  action="#{ManagedSolicitudDevoluciones.atras_action_1}" reRender="dataDevolucionesAlmacen,controles" rendered="#{ManagedSolicitudDevoluciones.begin!='1'}" >
                                                <h:graphicImage url="../img/previous.gif"  style="border:0px solid red"   alt="PAGINA ANTERIOR"  />
                                            </a4j:commandLink>
                                            <a4j:commandLink  action="#{ManagedSolicitudDevoluciones.siguiente_action_1}" reRender="dataDevolucionesAlmacen,controles" rendered="#{ManagedSolicitudDevoluciones.cantidadfilas>='5'}">
                                                <h:graphicImage url="../img/next.gif"  style="border:0px solid red"  alt="PAGINA SIGUIENTE" />
                                            </a4j:commandLink>
                                    </h:panelGrid>
                                </rich:column>
                            </rich:columnGroup>
                        </f:facet>
                    </rich:dataTable>
                    
                    <br/>
                    <a4j:commandButton value="Generar Solicitud" styleClass="btn" oncomplete="Richfaces.showModalPanel('panelBuscarSalidaAlmacen')"
                    reRender="contenidoBuscarSalidaAlmacen" action="#{ManagedSolicitudDevoluciones.mostrarBuscarSalidaSolicitud_action}"/>
                    <a4j:commandButton value="Generar Solicitud FRV Residual" styleClass="btn" oncomplete="window.location.href = 'agregarSolicitudDevolucionFrvAspiradora.jsf?data='+(new Date()).getTime().toString()"/>
                    <a4j:commandButton value="Solicitud Por Lote Proveedor" styleClass="btn" onclick="Richfaces.showModalPanel('panelBuscarSalidaLoteProveedor')"
                    action="#{ManagedSolicitudDevoluciones.mostrarBuscarSalidaLoteProveedor_action}" reRender="contenidoBuscarSalidaLoteProveedor"/>
                    <br>

                      
                </div>

               

            </a4j:form>

            <rich:modalPanel id="panelBuscarSalidaLoteProveedor" minHeight="400"  minWidth="700"
                                     height="400" width="700"
                                     zindex="200"
                                     headerClass="headerClassACliente"
                                     resizeable="false" style="overflow :auto" >
                        <f:facet name="header">
                            <h:outputText value="Busqueda de Salida Almacen"/>
                        </f:facet>
                        <a4j:form id="formContenidoLote">
                        <h:panelGroup id="contenidoBuscarSalidaLoteProveedor">
                            <div align="center">
                                <h:panelGrid columns="6">
                                    <h:outputText value="Nro Salida" styleClass="outputTextBold"  />
                                    <h:outputText value="::" styleClass="outputTextBold"  />
                                    <h:inputText value="#{ManagedSolicitudDevoluciones.salidasAlmacenDetalleIngresoBuscar.salidasAlmacen.nroSalidaAlmacen}"  
                                                 id="nroSalida1" styleClass="inputText" size="5" onkeypress="valNum(event);" />
                                    <h:outputText value="Lote Proveedor" styleClass="outputTextBold"  />
                                    <h:outputText value="::" styleClass="outputTextBold"  />
                                    <h:inputText value="#{ManagedSolicitudDevoluciones.salidasAlmacenDetalleIngresoBuscar.ingresosAlmacenDetalleEstado.loteMaterialProveedor}"  id="loteProveedor" styleClass="inputText" size="5" />
                                    
                            </h:panelGrid>
                                    <a4j:commandButton styleClass="btn" value="Buscar" action="#{ManagedSolicitudDevoluciones.buscarSalidaAlmacenLoteProveedor_action}"
                                           reRender="dataSalidasAlmacenBuscar,controlesBuscar"
                                                    />
                            <div style="overflow:auto;height:250px;width:80%;text-align:center">
                                <rich:dataTable value="#{ManagedSolicitudDevoluciones.salidasAlmacenBuscarList}"
                                    var="data"
                                    id="dataSalidasAlmacenBuscar"
                                    onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                    onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                    headerClass="headerClassACliente" binding="#{ManagedSolicitudDevoluciones.salidasAlmacenLoteProveedorDataTable}" >
                                    <h:column>
                                        <f:facet name="header">
                                            <h:outputText value=""  />
                                        </f:facet>
                                        <h:selectBooleanCheckbox value="#{data.checked}" />
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
                                        <a4j:commandLink action="#{ManagedSolicitudDevoluciones.verReporteSalidaAlmacenLoteProveedor_action}"
                                        oncomplete="openPopup('../reportes/salidasAlmacen/reporteDetalleSalidasAlmacen.jsp')" >
                                            <h:graphicImage url="../img/organigrama3.jpg" />
                                        </a4j:commandLink>
                                      </h:column>

                            </rich:dataTable>
                            </div>

                            <br/>
                                <a4j:commandButton styleClass="btn" value="Generar Solicitud"
								onclick="if(!editarItem('formContenidoLote:dataSalidasAlmacenBuscar')){return false;}"
                                action="#{ManagedSolicitudDevoluciones.generarDevolucionesSalidaAlmacenLoteProveedor_action}"
                                                   oncomplete="window.location.href='agregarSolicitudesDevolucionProveedor.jsf?data='+(new Date()).getTime().toString();"
                                                    />
                                <a4j:commandButton styleClass="btn" value="Cancelar"
                                onclick="Richfaces.hideModalPanel('panelBuscarSalidaLoteProveedor')"
                                                    />
                                </div>
                        </h:panelGroup>
                        </a4j:form>
            </rich:modalPanel>
            
            
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
                           <h:panelGrid columns="8" rendered="#{ManagedSolicitudDevoluciones.usuario.almacenesGlobal.codAlmacen != '14'}">
                                
                                <h:outputText value="Nro Salida :" styleClass="outputText1"  />
                                <h:inputText value="#{ManagedSolicitudDevoluciones.salidasAlmacenBuscar.nroSalidaAlmacen}"  id="nroSalida" styleClass="inputText" size="5" onkeypress="valNum(event);" />

                                <h:outputText value="Nro Lote :" styleClass="outputText1" />
                                <h:inputText value="#{ManagedSolicitudDevoluciones.salidasAlmacenBuscar.codLoteProduccion}" id="nroLote" styleClass="inputText" size="5"  />

                                <h:outputText value="Producto :" styleClass="outputText1" />
                                <h:selectOneMenu value="#{ManagedSolicitudDevoluciones.salidasAlmacenBuscar.componentesProd.codCompprod}" 
                                                 id="codCompProd" styleClass="inputText" style="width : 200px" >
                                    <f:selectItems value="#{ManagedSolicitudDevoluciones.componentesProdList}"  />
                                </h:selectOneMenu>
                                <a4j:commandButton styleClass="btn" value="Buscar" 
                                action="#{ManagedSolicitudDevoluciones.buscarSalidaAlmacen_action}"
                                                   reRender="dataSalidasAlmacenBuscar,controlesBuscar"
                                                   onclick="valida()"
                                                    />
                            </h:panelGrid>
                            <h:panelGrid columns="8" rendered="#{ManagedSolicitudDevoluciones.usuario.almacenesGlobal.codAlmacen eq '14'}">

                                <h:outputText value="Nro Salida :" styleClass="outputText1"  />
                                <h:inputText value="#{ManagedSolicitudDevoluciones.salidasAlmacenBuscar.nroSalidaAlmacen}"  id="nroSalida1" styleClass="inputText" size="5" onkeypress="valNum(event);" />

                                <h:outputText value="Nro OT :" styleClass="outputText1" />
                                <h:inputText value="#{ManagedSolicitudDevoluciones.salidasAlmacenBuscar.ordenTrabajo}" id="nroOT" styleClass="inputText" size="5" onkeypress="valNum(event);" />

                                <h:outputText value="Maquinaria :" styleClass="outputText1" />
                                <h:selectOneMenu value="#{ManagedSolicitudDevoluciones.salidasAlmacenBuscar.maquinaria.codMaquina}" id="codMaquina" styleClass="inputText" style="width=290px" >
                                    <f:selectItem itemValue="0" itemLabel="-NINGUNO-"  />
                                    <f:selectItems value="#{ManagedSolicitudDevoluciones.maquinariaList}"  />
                                </h:selectOneMenu>
                                <a4j:commandButton styleClass="btn" value="Buscar"
                                action="#{ManagedSolicitudDevoluciones.buscarSalidaAlmacen_action}"
                                                   reRender="dataSalidasAlmacenBuscar,controlesBuscar"
                                                   onclick="validaMantenimiento()"
                                                    />
                            </h:panelGrid>
                            
                            <div style="overflow:auto;height:250px;width:95%;text-align:center">
                                <rich:dataTable value="#{ManagedSolicitudDevoluciones.salidasAlmacenBuscarList}"
                                    var="data"
                                    id="dataSalidasAlmacenBuscar"
                                    onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                    onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                    headerClass="headerClassACliente" binding="#{ManagedSolicitudDevoluciones.salidasAlmacenDataTable}" >



                                    <h:column>
                                        <f:facet name="header">
                                            <h:outputText value=""  />
                                        </f:facet>
                                        <a4j:commandButton styleClass="btn" value="generar" oncomplete="window.location.href='agregarSolicitudesDevolucion.jsf?data='+(new Date()).getTime().toString();">
                                            <f:setPropertyActionListener value="#{data}" target="#{ManagedSolicitudDevoluciones.devoluciones.salidasAlmacen}"/>
                                        </a4j:commandButton>
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
                                <%--a4j:commandButton styleClass="btn" value="Generar Solicitud"
                                                   onclick="if(validarSeleccion('form2:dataSalidasAlmacenBuscar')==false){return false;}"
                                                   action="#{ManagedSolicitudDevoluciones.generarDevolucionSalidaAlmacen_action}"
                                                   oncomplete="location='agregarSolicitudDevoluciones.jsf'"
                                                    /--%>
                                <%--a4j:commandButton styleClass="btn" value="Generar Solicitud"
												onclick="if(!editarItem('form2:dataSalidasAlmacenBuscar')){return false;}"
                                                   action="#{ManagedSolicitudDevoluciones.generarDevolucionesSalidaAlmacen_action}"
                                                   oncomplete="location='agregarSolicitudesDevolucion.jsf'"
                                                    /--%>
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
             <rich:modalPanel id="panelBuscarSolicitudSalida"  minHeight="200"  minWidth="800"
                                     height="200" width="800"
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
                                <h:inputText value="#{ManagedSolicitudDevoluciones.buscarSolicitud.codSolicitudDevolucion}" styleClass="inputText" size="5" onkeypress="valNum(event);" />
                                   
                                <h:outputText value="Estado:" styleClass="outputText1" />
                                <h:selectOneMenu value="#{ManagedSolicitudDevoluciones.buscarSolicitud.estadoSolicitudDevolucion.codEstado}" styleClass="inputText" >
                                    <f:selectItems value="#{ManagedSolicitudDevoluciones.estadosSolicitudesList}"  />
                                </h:selectOneMenu>
                                 <h:outputText value="Fecha inicio solicitud" styleClass="outputText1" />
                                 <rich:calendar  datePattern="dd/MM/yyyy" styleClass="inputText" zindex="200" value="#{ManagedSolicitudDevoluciones.fechaInicio}" id="fechaInicio" enableManualInput="true" oninputblur="if(this.value!=''){valFecha(this)}else{return true;}"  />
                                 <h:outputText value="Fecha final solicitud" styleClass="outputText1" />
                                 <rich:calendar  datePattern="dd/MM/yyyy" styleClass="inputText" zindex="200" value="#{ManagedSolicitudDevoluciones.fechafinal}" id="fechaFinal" enableManualInput="true" oninputblur="if(this.value!=''){valFecha(this)}else{return true;}"  />

                                  <h:outputText value="Nro Salida:" styleClass="outputText1" />
                                  <h:inputText value="#{ManagedSolicitudDevoluciones.buscarSolicitud.salidaAlmacen.nroSalidaAlmacen}" styleClass="inputText" size="5" onkeypress="valNum(event);" />


                                <h:outputText value="Almacen:" styleClass="outputText1" />
                                <h:selectOneMenu value="#{ManagedSolicitudDevoluciones.buscarSolicitud.salidaAlmacen.almacenes.codAlmacen}" styleClass="inputText" style="width:230px">
                                    <f:selectItems value="#{ManagedSolicitudDevoluciones.almacenesList}"  />
                                </h:selectOneMenu>
                                 <h:outputText value="Producto:" styleClass="outputText1" />
                                 <h:selectOneMenu value="#{ManagedSolicitudDevoluciones.buscarSolicitud.salidaAlmacen.producto.codProducto}" styleClass="inputText" style="width:230px" >
                                     <f:selectItems value="#{ManagedSolicitudDevoluciones.productosList}"  />
                                </h:selectOneMenu>
                                
                            </h:panelGrid>
                                <div align="center">

                                <a4j:commandButton styleClass="btn" value="Buscar" onclick="javascript:Richfaces.hideModalPanel('panelBuscarSolicitudSalida');"
                                action="#{ManagedSolicitudDevoluciones.buscarSolicitudDevolucionAction}"
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

