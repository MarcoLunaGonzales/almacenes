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
                    if(cel.getElementsByTagName('input').length>0&&cel.getElementsByTagName('input')[0].type=='checkbox'){
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
                    <h:outputText value="#{ManagedSolicitudSalidaAlmacen.cargarContenidoSolicitudesSalidaAlmacen}"/>
                    <h:outputText styleClass="outputTextTitulo"  value="Solicitudes Salidas Almacen" />
                    <br>
                        <a4j:commandLink onclick="Richfaces.showModalPanel('panelBuscarSalidaAlmacen')">
                            <h:outputText value="Buscar" styleClass="outputText1" />
                            <h:graphicImage url="../img/buscar.png">
                        
                            </h:graphicImage>
                        </a4j:commandLink>

                        <br>
                    
                    <rich:dataTable value="#{ManagedSolicitudSalidaAlmacen.solicitudesSalidaAlmacenList}" var="data"
                                    id="dataSalidasAlmacen"
                                    onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                    onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                    headerClass="headerClassACliente" binding="#{ManagedSolicitudSalidaAlmacen.solicitudesSalidaAlmacenDataTable}" >
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value=""  />
                            </f:facet>
                            <a4j:commandButton value="Generar Salida" styleClass="btn"
                                               rendered="#{(data.estadosSolicitudSalidasAlmacen.codEstadoSolicitudSalidaAlmacen eq 1
                                                            or data.estadosSolicitudSalidasAlmacen.codEstadoSolicitudSalidaAlmacen eq 3)
                                                            and ManagedSolicitudSalidaAlmacen.administradorAlmacen}"
                                               oncomplete="if(#{data.solicitudPorLoteProveedor}){window.location.href='agregarSalidaAlmacenPorLoteProveedor.jsf?data='+(new Date()).getTime().toString();}
                                                                else{window.location.href='agregarSalidaAlmacen.jsf?data='+(new Date()).getTime().toString()}"
                                               >
                                <f:setPropertyActionListener value="#{data}" target="#{ManagedSolicitudSalidaAlmacen.solicitudesSalida}"/>
                            </a4j:commandButton>
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Nro Solicitud"  />
                            </f:facet>
                            <h:outputText  value="#{data.codFormSalida}"  />
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="O.T."  />
                            </f:facet>
                            <h:outputText value="#{data.ordenTrabajo}">                                
                            </h:outputText>
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Fecha Sol."  />
                            </f:facet>
                            <h:outputText value="#{data.fechaSolicitud}"  >
                                <f:convertDateTime pattern="dd/MM/yyyy" />
                            </h:outputText>
                        </h:column>
                        <h:column rendered="#{ManagedSolicitudSalidaAlmacen.usuario.almacenesGlobal.codAlmacen!='14'}">
                            <f:facet name="header">
                                <h:outputText value="Producto"  />
                            </f:facet>
                            <h:outputText value="#{data.componentesProd.nombreProdSemiterminado}"  >
                            </h:outputText>
                        </h:column>
                        <h:column rendered="#{ManagedSolicitudSalidaAlmacen.usuario.almacenesGlobal.codAlmacen!='14'}">
                            <f:facet name="header">
                                <h:outputText value="Presentacion"  />
                            </f:facet>
                            <h:outputText value="#{data.presentacionesProducto.nombreProductoPresentacion}"  >
                            </h:outputText>
                        </h:column>
                        <h:column rendered="#{ManagedSolicitudSalidaAlmacen.usuario.almacenesGlobal.codAlmacen eq '14'}">
                            <f:facet name="header">
                                <h:outputText value="Maquinaria"  />
                            </f:facet>
                            <h:outputText value="#{data.maquinaria.nombreMaquina}"  >
                            </h:outputText>
                        </h:column>
                        <h:column rendered="#{ManagedSolicitudSalidaAlmacen.usuario.almacenesGlobal.codAlmacen eq '14'}">
                            <f:facet name="header">
                                <h:outputText value="Instalacion"  />
                            </f:facet>
                            <h:outputText value="#{data.areasInstalaciones.nombreAreaInstalacion}"  >
                            </h:outputText>
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Area"  />
                            </f:facet>
                            <h:outputText value="#{data.areaDestinoSalida.nombreAreaEmpresa}"  />
                        </h:column>


                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Tipo de Salida"  />
                            </f:facet>
                            <h:outputText value="#{data.tiposSalidasAlmacen.nombreTipoSalidaAlmacen}"  />
                            <h:outputText rendered="#{data.tiposSalidasAlmacen.codTipoSalidaAlmacen eq 3}" 
                                          value="<br/>Destino del traspaso : #{data.almacenesDestino.nombreAlmacen}" escape="false"/>
                        </h:column>
                        

                         <h:column>
                            <f:facet name="header">
                                <h:outputText value="Solicitante"  />
                            </f:facet>
                            <h:outputText value="#{data.solicitante.nombrePersonal}"  /> &nbsp;
                            <h:outputText value="#{data.solicitante.apPaternoPersonal}"  /> &nbsp;
                            <h:outputText value="#{data.solicitante.apMaternoPersonal}"  /> &nbsp;
                        </h:column>

                          <h:column>
                            <f:facet name="header">
                                <h:outputText value="Lote"  />
                            </f:facet>
                            <h:outputText value="#{data.codLoteProduccion}"  />
                          </h:column>

                          <h:column>
                            <f:facet name="header">
                                <h:outputText value="Estado Solicitud"  />
                            </f:facet>
                            <h:outputText value="#{data.estadosSolicitudSalidasAlmacen.nombreEstadoSolicitudSalidaAlmacen}"  />
                         </h:column>

                         <h:column>
                            <f:facet name="header">
                                <h:outputText value="Obs Salida"  />
                            </f:facet>
                            <h:outputText value="#{data.obsSolicitud}"  />
                         </h:column>
                        <rich:column style="background:#{data.colorFila}" rendered="#{ManagedSolicitudSalidaAlmacen.usuario.almacenesGlobal.codAlmacen != '14'&&(data.codLoteProduccion != '')}">
                            <f:facet name="header">
                                <h:outputText value=""  />
                            </f:facet>
                            <a4j:commandLink action="#{ManagedSolicitudSalidaAlmacen.verReporteSolicitudSalidaAlmacen_action}" oncomplete = "openPopup('../reportes/solicitudSalidaAlmacen/reporteDetalleSolicitudSolicitanteLoteProduccion.jsf')">
                                <h:graphicImage url="../img/report.png" />
                            </a4j:commandLink>
                         </rich:column>       
                         <rich:column style="background:#{data.colorFila}" rendered="#{ManagedSolicitudSalidaAlmacen.usuario.almacenesGlobal.codAlmacen!='4'&&ManagedSolicitudSalidaAlmacen.usuario.almacenesGlobal.codAlmacen!='14'&&(data.codLoteProduccion eq '')}">
                            <f:facet name="header">
                                <h:outputText value=""  />
                            </f:facet>
                            <a4j:commandLink action="#{ManagedSolicitudSalidaAlmacen.verReporteSolicitudSalidaAlmacen_action}" oncomplete = "openPopup('../reportes/solicitudSalidaAlmacen/reporteDetalleSolicitudSalidasAlmacen.jsf')">
                                <h:graphicImage url="../img/report.png" />
                            </a4j:commandLink>
                         </rich:column>
                         <rich:column style="background:#{data.colorFila}" rendered="#{ManagedSolicitudSalidaAlmacen.usuario.almacenesGlobal.codAlmacen eq '14'||ManagedSolicitudSalidaAlmacen.usuario.almacenesGlobal.codAlmacen eq '4'}" >
                            <f:facet name="header">
                                <h:outputText value=""  />
                            </f:facet>
                            <a4j:commandLink action="#{ManagedSolicitudSalidaAlmacen.verReporteSolicitudSalidaAlmacen_action}" oncomplete = "openPopup('../reportes/solicitudSalidaAlmacen/reporteDetalleSolicitudSalidasAlmacenMantenimiento.jsf')">
                                <h:graphicImage url="../img/report.png" />
                            </a4j:commandLink>
                         </rich:column>

                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Tipo de Solicitud de Salida"  />
                            </f:facet>
                            <h:outputText value="#{data.tipoSolicitud.nombreTipoSolicitudSalida}"  />
                        </h:column>
                        <f:facet name="footer">
                            <rich:columnGroup>
                                <rich:column colspan="14">
                                    <center>
                                        <h:panelGrid columns="2"  width="50" id="controles">
                                            <a4j:commandLink  action="#{ManagedSolicitudSalidaAlmacen.atras_action}" reRender="dataSalidasAlmacen,controles" rendered="#{ManagedSolicitudSalidaAlmacen.begin!='1'}" >
                                                   <h:graphicImage url="../img/previous.gif"  style="border:0px solid red"   alt="PAGINA ANTERIOR"  />
                                               </a4j:commandLink>
                                               <a4j:commandLink  action="#{ManagedSolicitudSalidaAlmacen.siguiente_action}" reRender="dataSalidasAlmacen,controles" rendered="#{ManagedSolicitudSalidaAlmacen.cantidadfilas>='5'}">
                                                   <h:graphicImage url="../img/next.gif"  style="border:0px solid red"  alt="PAGINA SIGUIENTE" />
                                               </a4j:commandLink>
                                        </h:panelGrid>
                                        </center>
                                </rich:column>
                            </rich:columnGroup>
                        </f:facet>
                    </rich:dataTable>
                    
                     
                    <br>
                    
                </div>
            </a4j:form>
            <rich:modalPanel id="panelBuscarSalidaAlmacen"  minHeight="250"  minWidth="900"
                                     height="250" width="900"
                                     zindex="200"
                                     headerClass="headerClassACliente"
                                     resizeable="false" style="overflow :auto" >
                        <f:facet name="header">
                            <h:outputText value="Buscar Solicitud Salida Almacen"/>
                        </f:facet>
                        <a4j:form>
                        <h:panelGroup id="contenidoBuscarSalidaAlmacen">
                            <h:panelGrid columns="4">
                                <h:outputText value="Nro Solicitud:" styleClass="outputText1" />
                                <h:inputText value="#{ManagedSolicitudSalidaAlmacen.solicitudesSalidaBuscar.codFormSalida}" styleClass="inputText"  onkeypress="valNum(event);">
                                </h:inputText>
                                
                                <h:outputText value="Nro OT:" styleClass="outputText1" />
                                <h:inputText value="#{ManagedSolicitudSalidaAlmacen.solicitudesSalidaBuscar.ordenTrabajo}" styleClass="inputText"  onkeypress="valNum(event);" />

                                <h:outputText value="fecha Solicitud:" styleClass="outputText1" />
                                <h:inputText value="#{ManagedSolicitudSalidaAlmacen.solicitudesSalidaBuscar.fechaSolicitud}" styleClass="inputText"  >
                                    <f:convertDateTime pattern="dd/MM/yyyy" />
                                </h:inputText>

                                <h:outputText value="Area:" styleClass="outputText1" />
                                <h:selectOneMenu value="#{ManagedSolicitudSalidaAlmacen.solicitudesSalidaBuscar.areaDestinoSalida.codAreaEmpresa}" styleClass="inputText" >
                                    <f:selectItems value="#{ManagedSolicitudSalidaAlmacen.areasEmpresaList}"  />
                                </h:selectOneMenu>

                                <h:outputText value="Tipo de Salida:" styleClass="outputText1" />
                                <h:selectOneMenu value="#{ManagedSolicitudSalidaAlmacen.solicitudesSalidaBuscar.tiposSalidasAlmacen.codTipoSalidaAlmacen}" styleClass="inputText" >
                                    <f:selectItems value="#{ManagedSolicitudSalidaAlmacen.tiposSalidasAlmacenList}"  />
                                </h:selectOneMenu>
                                
                                <h:outputText value="Solicitante:" styleClass="outputText1" />
                                <h:selectOneMenu value="#{ManagedSolicitudSalidaAlmacen.solicitudesSalidaBuscar.solicitante.codPersonal}" styleClass="inputText" >
                                    <f:selectItems value="#{ManagedSolicitudSalidaAlmacen.personalSolicitanteList}"  />
                                </h:selectOneMenu>
                                
                                <h:outputText value="Lote:" styleClass="outputText1" />
                                <h:inputText value="#{ManagedSolicitudSalidaAlmacen.solicitudesSalidaBuscar.codLoteProduccion}" styleClass="inputText"  onkeypress="valNum(event);" />
                                
                                <h:outputText value="Estado Solicitud:" styleClass="outputText1" />
                                <h:selectOneMenu value="#{ManagedSolicitudSalidaAlmacen.solicitudesSalidaBuscar.estadosSolicitudSalidasAlmacen.codEstadoSolicitudSalidaAlmacen}" styleClass="inputText" >
                                    <f:selectItems value="#{ManagedSolicitudSalidaAlmacen.estadosSolicitudSalidaList}"  />
                                </h:selectOneMenu>
                                <%--inicio ale buscar--%>
                                 <h:outputText value="Capitulo:" styleClass="outputText1" />
                                 <h:selectOneMenu  value="#{ManagedSolicitudSalidaAlmacen.materialBuscar.grupos.capitulos.codCapitulo}" styleClass="inputText" >
                                     <f:selectItems value="#{ManagedSolicitudSalidaAlmacen.listaCapitulos}"  />
                                     <a4j:support event="onchange" action="#{ManagedSolicitudSalidaAlmacen.onChangeCapitulo}"reRender="codMaterial"/>
                                </h:selectOneMenu>
                                <h:outputText value="Capitulo:" styleClass="outputText1" />
                                <h:selectOneMenu  value="#{ManagedSolicitudSalidaAlmacen.materialBuscar.grupos.codGrupo}" styleClass="inputText" id="codMaterial" >
                                    <f:selectItems value="#{ManagedSolicitudSalidaAlmacen.listaGrupos}"  />
                                    <a4j:support event="onchange" action="#{ManagedSolicitudSalidaAlmacen.onChangeGrupo}"reRender="codItem"/>
                                </h:selectOneMenu>
                                <h:outputText value="Item" styleClass="outputText1" />
                                <h:selectOneMenu  value="#{ManagedSolicitudSalidaAlmacen.materialBuscar.codMaterial}" styleClass="inputText" id="codItem" >
                                    <f:selectItems value="#{ManagedSolicitudSalidaAlmacen.listaItems}"  />

                                </h:selectOneMenu>
                                <%--final ale buscar--%>
                            </h:panelGrid>
                                <div align="center">
                                <a4j:commandButton styleClass="btn" value="Buscar" onclick="javascript:Richfaces.hideModalPanel('panelBuscarSalidaAlmacen');"
                                                   action="#{ManagedSolicitudSalidaAlmacen.buscarSolicitudesSalidaAlmacen_action}"
                                                   reRender="dataSalidasAlmacen,controles"
                                                    />
                                <input type="button" value="Cancelar" onclick="javascript:Richfaces.hideModalPanel('panelBuscarSalidaAlmacen')" class="btn" />
                                </div>
                        </h:panelGroup>
                        </a4j:form>
            </rich:modalPanel>
            <a4j:include viewId="/panelProgreso.jsp"/>

        </body>
    </html>

</f:view>

