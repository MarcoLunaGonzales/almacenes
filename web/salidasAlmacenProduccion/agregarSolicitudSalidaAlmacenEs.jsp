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
               
            function calcularCantidadSolicitar(index){
                index = index+1;
                var tabla=document.getElementById('form1:dataSalidasAlmacenDetalle');
                var filas=tabla.rows;
                var cantidad = 0;
                var valor = 0;
                var columnas=filas[index].cells;
                var celda=columnas[3];
                if(celda.getElementsByTagName('input')[0].type=='checkbox'){
                    cantidad = columnas[4].innerHTML;
                    if(celda.getElementsByTagName('input')[0].checked){
                        valor = parseFloat(cantidad) * parseFloat(1.02);
                        columnas[4].innerHTML = parseFloat(valor).toFixed(2);
                        columnas[5].innerHTML = Math.ceil(valor);
                    }
                    else{
                        valor = parseFloat(cantidad)/parseFloat(1.02);
                        columnas[4].innerHTML = parseFloat(valor).toFixed(0);
                        columnas[5].innerHTML = parseFloat(valor).toFixed(0);
                    }
               }
                
            }



           function validarSeleccionSSA(){
                var count=0;
                var elements=document.getElementById('form1:dataSalidasAlmacenDetalle');
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
                    alert('Debe seleccionar al menos un detalle.');
                    return false;
                }
                return true;
            }
    
   </script>
       
      

        </head>
        <body >
            <a4j:form id="form1">
                <div align="center">

                    <br><br>
                        
                    <rich:panel id="panelEstadoCuentas" styleClass="panelBuscar" style="top:50px;left:50px;width:700px;">
                    <f:facet name="header">
                        <h:outputText value="<div   onmouseover=\"this.style.cursor='move'\" onmousedown=\"comienzoMovimiento(event, 'form1:panelEstadoCuentas');\"  >Buscar<div   style=\"margin-left:550px;\"   onclick=\"document.getElementById('form1:panelEstadoCuentas').style.visibility='hidden';document.getElementById('panelsuper').style.visibility='hidden';\" onmouseover=\"this.style.cursor='hand'\"   >Cerrar</div> </div> "
                              escape="false" />
                    </f:facet>
                    </rich:panel>
                    <h:outputText value="#{ManagedRegistroSolicitudSalidaAlmacen.cargarGenerarSalidaEmpaqueSecundario}"  />

                <rich:panel style="width:100%"  headerClass="headerClassACliente">
                            <f:facet name="header">
                                <h:outputText value="Registro de Solicitud de Traspaso de Empaque Secundario"  />
                            </f:facet>
                        <h:panelGrid columns="4" >

                            <h:outputText value="Nro Solicitud:" styleClass="outputText2" />
                            <h:outputText value="#{ManagedRegistroSolicitudSalidaAlmacen.solicitudesSalida.codFormSalida}" styleClass="outputText2" />
                            <h:outputText value="Almacen Origen:" styleClass="outputText2" />
                            <h:outputText value="#{ManagedRegistroSolicitudSalidaAlmacen.solicitudesSalida.almacenes.nombreAlmacen}" styleClass="outputText2" />
                            
                            <h:outputText value="Area / Departamento:" styleClass="outputText2" />
                            <h:selectOneMenu  styleClass="inputText" value="#{ManagedRegistroSolicitudSalidaAlmacen.solicitudesSalida.areaDestinoSalida.codAreaEmpresa}" 
                                              id="codAreaEmpresa" disabled="true">
                                <f:selectItems value="#{ManagedRegistroSolicitudSalidaAlmacen.areasEmpresaList}"  />                                
                            </h:selectOneMenu>
                            <h:outputText value="Solicitante:" styleClass="outputText2" />
                            <h:panelGroup>
                                <h:outputText styleClass="outputText2" value="#{ManagedRegistroSolicitudSalidaAlmacen.solicitudesSalida.solicitante.nombreCompleto}" />
                            </h:panelGroup>
                            <h:outputText value="Almacen Destino del Traspaso:" styleClass="outputText2" />
                            <h:selectOneMenu  styleClass="inputText" value="#{ManagedRegistroSolicitudSalidaAlmacen.solicitudesSalida.almacenesDestino.codAlmacen}" 
                                              id="codAlmacenDestino" >                                
                                <f:selectItems value="#{ManagedRegistroSolicitudSalidaAlmacen.almacenesList}"  />                                
                            </h:selectOneMenu>
                            <h:outputText value="Observaciones:" styleClass="outputText2" />
                            <h:inputTextarea  rows="5" cols="50" styleClass="outputText2" value="#{ManagedRegistroSolicitudSalidaAlmacen.solicitudesSalida.obsSolicitud}" />
                            <h:outputText value="" />
                            <h:outputText value="" />

                        </h:panelGrid>
                    </rich:panel>

                     <rich:dataTable value="#{ManagedRegistroSolicitudSalidaAlmacen.solicitudesSalidaAlmacenDetalleList}"
                                    var="data"
                                    id="dataSalidasAlmacenDetalle"
                                    onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                    onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                    headerClass="headerClassACliente"
                                    style="width:100%" 
                                    rowKeyVar="index">

                       <rich:column>
                            <f:facet name="header">
                                <h:outputText value="Solicitar"  />
                            </f:facet>
                            <div align="center"><h:selectBooleanCheckbox value="#{data.checked}" /></div>
                        </rich:column>

                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value="Item"  />
                            </f:facet>
                            <h:outputText  value="#{data.materiales.nombreMaterial}"/>
                        </rich:column>
                         
                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value="Unid."   />
                            </f:facet>
                            <h:outputText value="#{data.unidadesMedida.nombreUnidadMedida}"  />
                        </rich:column>
                         
                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value="Adicionar el 2%"  />
                            </f:facet>
                            <div align="center"><h:selectBooleanCheckbox value="#{data.adicionarPorcentaje}"  onclick="calcularCantidadSolicitar(#{index})" /></div>
                        </rich:column>
                         
                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value="Cantidad"  />
                            </f:facet>
                            <h:outputText value="#{data.cantidad}"  />
                        </rich:column>
                        
                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value="Total Redondeado"  />
                            </f:facet>
                            <h:outputText value="#{data.cantidad}"  />
                        </rich:column>
                            
                    </rich:dataTable>
                    
                    

                    <br>
                    
                    <a4j:commandButton value="Guardar" styleClass="btn"
                    action="#{ManagedRegistroSolicitudSalidaAlmacen.guardarNuevoSolicitudSaidaAlmacenEs_action}"
                    onclick="if(!validarSeleccionSSA()){return false;}else{if(confirm('Esta seguro de realizar la Solicitud de Salida de Almacen?')==false){return false;}}"
                    oncomplete="alert('#{ManagedRegistroSolicitudSalidaAlmacen.mensaje}');if(#{ManagedRegistroSolicitudSalidaAlmacen.registradoCorrectamente}){location='../solicitudesSalidaAlmacen/navegadorSolicitudesSalidaAlmacen.jsf'}"
                    />
                    <input type="button" value="Cancelar" class="btn" onclick="location='navegador_programa_produccion.jsf'" />
                <h:outputText value="#{ManagedFrecuenciaMantenimientoMaquinaria.closeConnection}"  />

            </a4j:form>
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


