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
               function openPopup(url){
                    window.open(url,'DETALLE','top=50,left=200,width=800,height=500,scrollbars=1,resizable=1');
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
                function validarSeleccion(nametable){
                   var count=0;
                   var elements=document.getElementById(nametable);
                   var rowsElement=elements.rows;
                   for(var i=1;i<rowsElement.length;i++){
                    var cellsElement=rowsElement[i].cells;
                    var cel=cellsElement[0];
                    if(cel.innerHTML!='')
                    {
                    if(cel.getElementsByTagName('input')[0].type=='checkbox'){
                          if(cel.getElementsByTagName('input')[0].checked){
                           count++;
                         }

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
       </script>
        <style type="text/css">
           .a1{
             background-color:rgb;
           }
           .a2{
             background-color:#F0E686;
           }
           .a3
           {
                background-color:#bff9bf;
           }
           .a4
           {
                background-color:#FFB6C1;
           }



       </style>
        </head>
        <body >
            <a4j:form id="form1">
                <div align="center">
                    <h:outputText value="#{ManagedDevolucionAlmacen.cargarProgramaProduccionDevolucionMaterial}"/>
                    <h:outputText styleClass="outputTextTitulo"  value="Agregar Devoluciones Almacen" />
                    <br><br>
                    <table id="table1" class="outputText2">
                        <tr>
                            <td class="a2" width="35px"></td>
                            <td>Ingresado Parcialmente</td>
                            <td class="a3" width="35px"></td>
                            <td>Ingresado Totalmente</td>
                            <td class="a4" width="35px"></td>
                            <td>Rechazado</td>
                        </tr>
                    </table>
                    <rich:dataTable value="#{ManagedDevolucionAlmacen.programaProdDevMatList}"
                                    var="data"
                                    id="dataDevolucionesDetalleAlmacen"
                                    onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                    onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                    headerClass="headerClassACliente"
                                    binding="#{ManagedDevolucionAlmacen.programaProdDevMatDataTable}" style="width:80%">
                                    <rich:column styleClass="a#{data.estadoProgramaProduccionDevolucion.codEstadoRegistro}">
                                        <f:facet name="header">
                                            <h:outputText value=""  />
                                        </f:facet>
                                        <h:selectBooleanCheckbox value="#{data.checked}" rendered="#{data.estadoProgramaProduccionDevolucion.codEstadoRegistro== 1||data.estadoProgramaProduccionDevolucion.codEstadoRegistro== 2}"/>
                                    </rich:column>
                                    <rich:column styleClass="a#{data.estadoProgramaProduccionDevolucion.codEstadoRegistro}">
                                        <f:facet name="header">
                                            <h:outputText value="Producto"  />
                                        </f:facet>
                                        <h:outputText value="#{data.programaProduccion.formulaMaestra.componentesProd.nombreProdSemiterminado}" />
                                    </rich:column>
                                    <rich:column styleClass="a#{data.estadoProgramaProduccionDevolucion.codEstadoRegistro}">
                                        <f:facet name="header">
                                            <h:outputText value="Fecha Registro"  />
                                        </f:facet>
                                        <h:outputText value="#{data.fechaRegistro}" >
                                            <f:convertDateTime pattern="dd/MM/yyyy HH:mm:ss"/>
                                            </h:outputText>
                                    </rich:column>

                                  <rich:column styleClass="a#{data.estadoProgramaProduccionDevolucion.codEstadoRegistro}">
                                        <f:facet name="header">
                                            <h:outputText value="Lote"  />
                                        </f:facet>
                                        <h:outputText  value="#{data.programaProduccion.codLoteProduccion}"  />
                                    </rich:column>
                                    <rich:column styleClass="a#{data.estadoProgramaProduccionDevolucion.codEstadoRegistro}">
                                        <f:facet name="header">
                                            <h:outputText value="Tipo Programa Producción"  />
                                        </f:facet>
                                        <h:outputText value="#{data.programaProduccion.tiposProgramaProduccion.nombreProgramaProd}"  />
                                        
                                    </rich:column>
                                    <rich:column styleClass="a#{data.estadoProgramaProduccionDevolucion.codEstadoRegistro}">
                                        <f:facet name="header">
                                            <h:outputText value="Area Produción"  />
                                        </f:facet>
                                        <h:outputText value="#{data.programaProduccion.formulaMaestra.componentesProd.areasEmpresa.nombreAreaEmpresa}"  >
                                        </h:outputText>
                                    </rich:column>
                                    <rich:column styleClass="a#{data.estadoProgramaProduccionDevolucion.codEstadoRegistro}">
                                        <f:facet name="header">
                                            <h:outputText value="Cantidad Enviada Acond."  />
                                        </f:facet>
                                        <h:outputText value="#{data.programaProduccion.cantIngresoAcond}"  >
                                        </h:outputText>
                                    </rich:column>
                                    <rich:column styleClass="a#{data.estadoProgramaProduccionDevolucion.codEstadoRegistro}">
                                        <f:facet name="header">
                                            <h:outputText value="Estado"  />
                                        </f:facet>
                                        <h:outputText value="#{data.estadoProgramaProduccionDevolucion.nombreEstadoRegistro}"  >
                                        </h:outputText>
                                    </rich:column>
                                    <rich:column styleClass="a#{data.estadoProgramaProduccionDevolucion.codEstadoRegistro}">
                                        <f:facet name="header">
                                            <h:outputText value=""   />
                                        </f:facet>
                                        <a4j:commandLink onclick="openPopup('reporteDetallaProgramaProduccionDevolucionMaterial.jsf?codComprod=#{data.programaProduccion.formulaMaestra.componentesProd.codCompprod}&codTipoProd=#{data.programaProduccion.tiposProgramaProduccion.codTipoProgramaProd}&codLote=#{data.programaProduccion.codLoteProduccion}&codForm=#{data.programaProduccion.formulaMaestra.codFormulaMaestra}&codProg=#{data.programaProduccion.codProgramaProduccion}&nombreProd=#{data.programaProduccion.formulaMaestra.componentesProd.nombreProdSemiterminado}&nomTipoProd=#{data.programaProduccion.tiposProgramaProduccion.nombreProgramaProd}&area=#{data.programaProduccion.formulaMaestra.componentesProd.areasEmpresa.nombreAreaEmpresa}&codProgProd=#{data.codProgramaProduccionDevolucionMaterial}')">
                                        <h:graphicImage url="../img/areasdependientes.png">

                                        </h:graphicImage>
                                        </a4j:commandLink>
                                    </rich:column>
                                    
                    </rich:dataTable>
                   <br>
                       <a4j:commandButton value="Rechazar Devolución" styleClass="btn" onclick="if(validarSeleccion('form1:dataDevolucionesDetalleAlmacen')==false){return false;}" action="#{ManagedDevolucionAlmacen.rechazarProgProdDevolucionMaterial}" oncomplete="if('#{ManagedDevolucionAlmacen.mensaje}'!=''){alert('#{ManagedDevolucionAlmacen.mensaje}');}" reRender="dataDevolucionesDetalleAlmacen"/>
                       <a4j:commandButton value="Generar Devolución" styleClass="btn" onclick="if(validarSeleccion('form1:dataDevolucionesDetalleAlmacen')==false){return false;}"  action="#{ManagedDevolucionAlmacen.generarDevolucionMaterial}" oncomplete="if('#{ManagedDevolucionAlmacen.mensaje}'!=''){alert('#{ManagedDevolucionAlmacen.mensaje}');}else{Richfaces.showModalPanel('panelBuscarSalidaAlmacen');}" reRender="contenidoBuscarSalidaAlmacen"/>




                </div>

               
                <!--cerrando la conexion-->
               

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
                            <h:panelGrid columns="4">

                              
                                <h:outputText value="Nro Lote :" styleClass="outputText1" />
                                <h:outputText value="#{ManagedDevolucionAlmacen.nuevaSalidaAlmacen.codLoteProduccion}" styleClass="outputText1" />

                                <h:outputText value="Producto :" styleClass="outputText1" />
                                <h:outputText value="#{ManagedDevolucionAlmacen.nuevaSalidaAlmacen.componentesProd.nombreProdSemiterminado}" styleClass="outputText1" />
                            </h:panelGrid>

                            <div style="overflow:auto;height:250px;width:80%;text-align:center">
                            <rich:dataTable value="#{ManagedDevolucionAlmacen.salidasAlmacenBuscarList}"
                                    var="data"
                                    id="dataSalidasAlmacenBuscar"
                                    onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                    onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                    headerClass="headerClassACliente" binding="#{ManagedDevolucionAlmacen.salidaAlmacenProgDevDataTable}" >
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
                                        <a4j:commandLink action="#{ManagedDevolucionAlmacen.verReporteSalidaAlmacenProgDev_action}" oncomplete="openPopup('../reportes/salidasAlmacen/reporteDetalleSalidasAlmacen.jsp')" >
                                            <h:graphicImage url="../img/organigrama3.jpg" />
                                        </a4j:commandLink>
                                      </h:column>

                            </rich:dataTable>
                            </div>

                                <br/>
                                <a4j:commandButton styleClass="btn" value="Generar Devolucion"
                                                   onclick="if(validarSeleccion('form2:dataSalidasAlmacenBuscar')==false){return false;}"
                                                   action="#{ManagedDevolucionAlmacen.generarDevolucionMaterial_Action}"
                                                   oncomplete="location='agregarDevolucionesAlmacenMaterial.jsf'"
                                                    />
                                <a4j:commandButton styleClass="btn" value="Cancelar"
                                onclick="Richfaces.hideModalPanel('panelBuscarSalidaAlmacen')"
                                                    />
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

