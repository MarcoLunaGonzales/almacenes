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
                    <h:outputText value="#{ManagedSalidaTraspasoAlmacen.cargarContenidoSalidasTraspasoAlmacen}"/>
                    <h:outputText styleClass="outputTextTitulo"  value="Salidas por Traspaso" />
                    <br/>
                        <a4j:commandLink onclick="Richfaces.showModalPanel('panelBuscarTraspasoAlmacen')">
                            <h:outputText value="Buscar" styleClass="outputText1" />
                            <h:graphicImage url="../img/buscar.png">

                            </h:graphicImage>
                        </a4j:commandLink>
                    <br/>
                    
                    <rich:dataTable value="#{ManagedSalidaTraspasoAlmacen.traspasosList}" var="data"
                                    id="dataSalidasAlmacen"
                                    onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                    onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                    headerClass="headerClassACliente" >
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
                            <h:outputText  value="#{data.salidasAlmacen.nroSalidaAlmacen}"  />
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Fecha Salida"  />
                            </f:facet>
                            <h:outputText  value="#{data.salidasAlmacen.fechaSalidaAlmacen}"  >
                                <f:convertDateTime pattern="dd/MM/yyyy hh:MM:ss" />
                            </h:outputText>
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Almacen Destino"  />
                            </f:facet>
                            <h:outputText value="#{data.almacenDestino.nombreAlmacen}"/>
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Tipo Salida"  />
                            </f:facet>
                            <h:outputText value="#{data.salidasAlmacen.tiposSalidasAlmacen.nombreTipoSalidaAlmacen}"  />
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Estado Salida"  />
                            </f:facet>
                            <h:outputText value="#{data.salidasAlmacen.estadosSalidasAlmacen.nombreEstadoSalidaAlmacen}"  />
                        </h:column>
                        <%--inicio ale traspaso--%>
                         <h:column>
                            <f:facet name="header">
                                <h:outputText value="Tipo Traspaso"  />
                            </f:facet>
                            <h:outputText value="#{data.tipoTraspaso.nombreTipoTraspaso}"  />
                        </h:column>
                        <%--final ale traspaso--%>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Observaciones"  />
                            </f:facet>
                            <h:outputText value="#{data.salidasAlmacen.obsSalidaAlmacen}"  />
                        </h:column>
                       
                    </rich:dataTable>
                    
                   <h:panelGrid columns="2"  width="50" id="controles">
                         <a4j:commandLink  action="#{ManagedSalidaTraspasoAlmacen.atras_action}" reRender="dataSalidasAlmacen,controles"  rendered="#{ManagedSalidaTraspasoAlmacen.begin!='1'}" >
                                <h:graphicImage url="../img/previous.gif"  style="border:0px solid red"   alt="PAGINA ANTERIOR"  />
                            </a4j:commandLink>
                            <a4j:commandLink  action="#{ManagedSalidaTraspasoAlmacen.siguiente_action}" reRender="dataSalidasAlmacen,controles" rendered="#{ManagedSalidaTraspasoAlmacen.cantidadfilas>='10'}">
                                <h:graphicImage url="../img/next.gif"  style="border:0px solid red"  alt="PAGINA SIGUIENTE" />
                            </a4j:commandLink>
                    </h:panelGrid>
                    

                    <%-- <ws:datascroller fordatatable="dataAreasEmpresa"  />--%>
                    <br>
                    <h:panelGroup rendered="#{ManagedSalidaTraspasoAlmacen.administradorAlmacen}">
                        <a4j:commandButton value="Generar Salida Traspaso" styleClass="btn" action="#{ManagedSalidaTraspasoAlmacen.generarTraspaso}" onclick="location='agregarSalidaTraspaso.jsf'"  />
                        <a4j:commandButton rendered="false" value="Anular" styleClass="btn" onclick="if(validarSeleccion('form1:dataSalidasAlmacen')==false){return false;}else{if(confirm('Seguro de Anular el Traspaso?'==true)){return true}}"
                        action="#{ManagedSalidaTraspasoAlmacen.anularSalidaAlmacenTraspaso_action}" oncomplete="if(#{ManagedSalidaTraspasoAlmacen.mensaje!=''}){alert('#{ManagedSalidaTraspasoAlmacen.mensaje}')}"
                        reRender="dataSalidasAlmacen" />
                        <a4j:commandButton value="Imprimir" styleClass = "btn" action="#{ManagedSalidaTraspasoAlmacen.verReporteSalidaAlmacenTraspaso_action}" oncomplete="openPopup('../reportes/reporteSalidasTraspasoAlmacen/reporteDetalleSalidasTraspasoAlmacen.jsp');" />
                    </h:panelGroup>
                    
                    <a4j:commandButton value="Imprimir con Costo Valorado" styleClass = "btn" action="#{ManagedSalidaTraspasoAlmacen.verReporteSalidaAlmacen_action}" oncomplete="openPopup('reporteSalidaAlmacenJasper.jsf?reporte=reporteSalidaCostoFRV.jasper');" rendered="#{ManagedSalidaTraspasoAlmacen.usuario.usuarioModuloBean.codUsuarioGlobal eq'1495'or ManagedSalidaTraspasoAlmacen.usuario.usuarioModuloBean.codUsuarioGlobal  eq '1893'}" />

                    <%--
                    <a4j:commandButton value="Generar Salida Almacen" styleClass="btn"  action="#{ManagedIngresoAlmacenOrdenCompra.generarIngresoAlmacen_action}"
                    reRender="contenidoFrecuenciaMantenimentoMaquinaria" oncomplete="if(#{ManagedIngresoAlmacenOrdenCompra.mensaje!=''}){alert('#{ManagedIngresoAlmacenOrdenCompra.mensaje}')}" />
                    --%>

                </div>

               
                <!--cerrando la conexion-->
                <h:outputText value="#{ManagedFrecuenciaMantenimientoMaquinaria.closeConnection}"  />

            </a4j:form>
            <%--inicio alejandro--%>
            <rich:modalPanel id="panelBuscarTraspasoAlmacen"  minHeight="250"  minWidth="800"
                                     height="250" width="800"
                                     zindex="200"
                                     headerClass="headerClassACliente"
                                     resizeable="false" style="overflow :auto" >
                        <f:facet name="header">
                            <h:outputText value="Buscar Salida por Traspaso"/>
                        </f:facet>
                        <a4j:form>
                        <h:panelGroup id="contenidoBuscarTraspasoAlmacen">
                            <h:panelGrid columns="4">



                                <h:outputText value="Gestion:" styleClass="outputText1" />
                                <h:selectOneMenu value="#{ManagedSalidaTraspasoAlmacen.traspasoBuscar.salidasAlmacen.gestiones.codGestion}" styleClass="inputText" >
                                    <f:selectItems value="#{ManagedSalidaTraspasoAlmacen.listaGestiones}"  />
                                </h:selectOneMenu>

                                <h:outputText value="Nro Salida:" styleClass="outputText1" />
                                <h:inputText value="#{ManagedSalidaTraspasoAlmacen.traspasoBuscar.salidasAlmacen.nroSalidaAlmacen}" styleClass="inputText" size="5" onkeypress="valNum(event);" />

                                 <h:outputText value="Filial:" styleClass="outputText2" />
                                 <h:selectOneMenu  styleClass="inputText" value="#{ManagedSalidaTraspasoAlmacen.traspasoBuscar.almacenDestino.filiales.codFilial}"   >
                                        <f:selectItems value="#{ManagedSalidaTraspasoAlmacen.filialList}"  />
                                        <a4j:support action="#{ManagedSalidaTraspasoAlmacen.cambio_filial}" event="onchange" reRender="codAlmacenDestino" />
                                 </h:selectOneMenu>
                                  <h:outputText value="Almacen Destino:" styleClass="outputText2" />
                                  <h:selectOneMenu  styleClass="inputText" value="#{ManagedSalidaTraspasoAlmacen.traspasoBuscar.almacenDestino.codAlmacen}"  id="codAlmacenDestino"  >
                                        <f:selectItems value="#{ManagedSalidaTraspasoAlmacen.almacenDestinoList}"  />

                                 </h:selectOneMenu>


                                 <h:outputText value="Fecha Inicio :" styleClass="outputText1" />
                                 <rich:calendar  datePattern="dd/MM/yyyy" styleClass="inputText" zindex="200" value="#{ManagedSalidaTraspasoAlmacen.fechaInicio1}" id="fechaInicio" enableManualInput="true" oninputblur="if(this.value!=''){valFecha(this)}else{return true;}" />

                              <h:outputText value="Fecha Final :" styleClass="outputText1" />
                              <rich:calendar  datePattern="dd/MM/yyyy" styleClass="inputText" zindex="200" value="#{ManagedSalidaTraspasoAlmacen.fechaFinal1}" id="fechaFinal" enableManualInput="true" oninputblur="if(this.value!=''){valFecha(this)}else{return true;}" />
                                <h:outputText value="Capitulo:" styleClass="outputText1" />
                                <h:selectOneMenu value="#{ManagedSalidaTraspasoAlmacen.materialBuscar.grupos.capitulos.codCapitulo}" styleClass="inputText" >
                                    <f:selectItems value="#{ManagedSalidaTraspasoAlmacen.capitulosList}"  />
                                    <a4j:support event="onchange" action="#{ManagedSalidaTraspasoAlmacen.cambio_capitulo}" reRender="codGrupo,codMaterial" />
                                </h:selectOneMenu>
                                 <h:outputText value="Grupo:" styleClass="outputText1" />
                                 <h:selectOneMenu value="#{ManagedSalidaTraspasoAlmacen.materialBuscar.grupos.codGrupo}" styleClass="inputText" id="codGrupo">
                                     <f:selectItems value="#{ManagedSalidaTraspasoAlmacen.listaGrupos}"  />
                                     <a4j:support event="onchange" action="#{ManagedSalidaTraspasoAlmacen.cambio_grupo}" reRender="codMaterial" />
                                </h:selectOneMenu>
                                  <h:outputText value="Item:" styleClass="outputText1" />
                                  <h:selectOneMenu value="#{ManagedSalidaTraspasoAlmacen.materialBuscar.codMaterial}" styleClass="inputText" id="codMaterial" >
                                      <f:selectItems value="#{ManagedSalidaTraspasoAlmacen.listaMateriales}"  />

                                </h:selectOneMenu>



                            </h:panelGrid>
                                <div align="center">
                                <a4j:commandButton styleClass="btn" value="Buscar" onclick="javascript:Richfaces.hideModalPanel('panelBuscarTraspasoAlmacen');"
                                action="#{ManagedSalidaTraspasoAlmacen.buscarTraspaso_action}" reRender="dataSalidasAlmacen,controles" />
                                <input type="button" value="Cancelar" onclick="javascript:Richfaces.hideModalPanel('panelBuscarTraspasoAlmacen')" class="btn" />
                                </div>
                        </h:panelGroup>
                        </a4j:form>
            </rich:modalPanel>
            <%--final alejandro--%>




        </body>
    </html>

</f:view>

