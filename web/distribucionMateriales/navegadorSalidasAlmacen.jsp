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
                    <h:outputText value="#{ManagedDistribucionMaterial.cargarSalidasAlmacen}"/>
                    <h:outputText styleClass="outputTextTitulo"  value="Dotacion de Material a Personal" />
                    <rich:dataTable value="#{ManagedDistribucionMaterial.salidasAlmacenList}" var="data"
                                    id="dataSalidasAlmacen"
                                    onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                    onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                    headerClass="headerClassACliente" binding="#{ManagedDistribucionMaterial.salidasAlmacenDataTable}" >
                        
                        <rich:column styleClass ="#{data.colorFila}">
                            <f:facet name="header">
                                
                                <h:outputText value="Nro Salida"  />
                                
                            </f:facet>
                            <a4j:commandLink action="#{ManagedDistribucionMaterial.verDistribucion_action}" reRender="contenidoRegistraDistribucion" oncomplete="Richfaces.showModalPanel('PanelRegistrarDistribucion')">
                            <h:outputText  value="#{data.nroSalidaAlmacen}"  />
                            </a4j:commandLink>
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


                        <rich:column styleClass ="#{data.colorFila}">
                            <f:facet name="header">
                                <h:outputText value="Producto"  />
                            </f:facet>
                            <h:outputText value="#{data.componentesProd.nombreProdSemiterminado}"  />
                        </rich:column>

                        <rich:column styleClass ="#{data.colorFila}">
                            <f:facet name="header">
                                <h:outputText value="Presentacion"  />
                            </f:facet>
                            <h:outputText value="#{data.presentacionesProducto.nombreProductoPresentacion}"  />
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
                    
                     

                    

                    <%-- <ws:datascroller fordatatable="dataAreasEmpresa"  />--%>
                    <br>
                        





                        
                    <%--
                    <a4j:commandButton value="Generar Salida Almacen" styleClass="btn"  action="#{ManagedIngresoAlmacenOrdenCompra.generarIngresoAlmacen_action}"
                    reRender="contenidoFrecuenciaMantenimentoMaquinaria" oncomplete="if(#{ManagedIngresoAlmacenOrdenCompra.mensaje!=''}){alert('#{ManagedIngresoAlmacenOrdenCompra.mensaje}')}" />
                    --%>

                </div>

               
                <!--cerrando la conexion-->
                <h:outputText value="#{ManagedFrecuenciaMantenimientoMaquinaria.closeConnection}"  />

            </a4j:form>
            <rich:modalPanel id="PanelRegistrarDistribucionMaterial" minHeight="250"  minWidth="1000"
                                     height="250" width="1000"
                                     zindex="200"
                                     headerClass="headerClassACliente"
                                     resizeable="false" style="overflow :auto" >
                        <f:facet name="header">
                            <h:outputText value="Registro de Distribucion de Material"/>
                        </f:facet>
                        <a4j:form>
                        <h:panelGroup id="contenidoRegistrarDistribucionMateriales">
                            
                                <div style="overflow:auto;height:150px" align="center">
                                <rich:dataTable value="#{ManagedDistribucionMaterial.distribucionMaterialesList}" var="data"
                                        id="listadoDistribucion"
                                        onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                        onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                        headerClass="headerClassACliente"
                                        align="center"  >
                                            <h:column>
                                                <f:facet name="header">
                                                    <h:outputText value=""  />
                                                </f:facet>
                                                <h:selectBooleanCheckbox value="#{data.checked}"  />
                                            </h:column>
                                            <rich:column>
                                                <f:facet name="header">
                                                    <h:outputText value="Entregado por:"  />
                                                </f:facet>
                                                <h:selectOneMenu value="#{data.personalEntrega.codPersonal}" styleClass="inputText" style="width:220px">
                                                    <f:selectItems value="#{ManagedDistribucionMaterial.personalEntregaList}" />
                                                </h:selectOneMenu>
                                            </rich:column>
                                            <rich:column>
                                                <f:facet name="header">
                                                    <h:outputText value="Recepcionado por:"  />
                                                </f:facet>
                                                <h:selectOneMenu value="#{data.personal.codPersonal}" styleClass="inputText">
                                                    <f:selectItems value="#{ManagedDistribucionMaterial.personalList}" />
                                                </h:selectOneMenu>
                                            </rich:column>
                                            <rich:column>
                                                <f:facet name="header">
                                                    <h:outputText value="Cantidad"  />
                                                </f:facet>
                                                <h:inputText value="#{data.cantidadEntregada}" styleClass="inputText" size="2" />
                                                [<h:outputText value="#{ManagedDistribucionMaterial.distribucionMaterialesSeleccionado.materiales.unidadesMedida.abreviatura}" />]
                                            </rich:column>
                                            <rich:column>
                                                <f:facet name="header">
                                                    <h:outputText value="Fecha"  />
                                                </f:facet>
                                                <rich:calendar value="#{data.fechaDistribucion}" datePattern="dd/MM/yyyy"  enableManualInput="true"  />

                                            </rich:column>
                                            <rich:column>
                                                <f:facet name="header">
                                                    <h:outputText value="Observaciones"  />
                                                </f:facet>
                                                <h:inputText value="#{data.observaciones}" styleClass="inputText" size="20" />
                                            </rich:column>
                                </rich:dataTable>
                                </div>

                            
                        </h:panelGroup>
                        <div align="center">
                        <a4j:commandLink action="#{ManagedDistribucionMaterial.adicionarPersonal_action}" reRender="listadoDistribucion"  timeout="7200"  >
                            <h:graphicImage url="../img/mas.png"/>
                        </a4j:commandLink>
                        <a4j:commandLink action="#{ManagedDistribucionMaterial.eliminarPersonal_action}" reRender="listadoDistribucion"  timeout="7200">
                            <h:graphicImage url="../img/menos.png"/>
                        </a4j:commandLink>
                        <br/>
                        <a4j:commandButton  value="Guardar" action="#{ManagedDistribucionMaterial.guardarDistribucion_action}" styleClass="btn" reRender="contenidoRegistraDistribucion" oncomplete="Richfaces.hideModalPanel('PanelRegistrarDistribucionMaterial')" />
                        <a4j:commandButton  value="Cancelar" styleClass="btn" onclick="Richfaces.hideModalPanel('PanelRegistrarDistribucionMaterial')" />
                        </div>
                        

                        
                        </a4j:form>

            </rich:modalPanel>
            
            <rich:modalPanel id="PanelRegistrarDistribucion" minHeight="250"  minWidth="1000"
                                     height="250" width="1000"
                                     zindex="200"
                                     headerClass="headerClassACliente"
                                     resizeable="false" style="overflow :auto" >
                        <f:facet name="header">
                            <h:outputText value="Distribucion de Materiales"/>
                        </f:facet>
                        <a4j:form>
                        <h:panelGroup id="contenidoRegistraDistribucion">
                            
                            <div style="overflow:auto;height:150px" align="center">
                                
                                <rich:dataTable value="#{ManagedDistribucionMaterial.distribucionList}" var="data" id="dataTipoMercaderia"
                                    onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                    onRowMouseOver="this.style.backgroundColor='#CCDFFA';"
                                    headerClass="headerClassACliente" columnClasses="tituloCampo"
                                    binding="#{ManagedDistribucionMaterial.distribucionMaterialesDataTable}"
                                                >
                                    <rich:column>
                                        <f:facet name="header">
                                            <h:outputText value="Material"  />
                                        </f:facet>
                                        <a4j:commandLink action="#{ManagedDistribucionMaterial.verDistribucionMateriales_action}" reRender="contenidoRegistrarDistribucionMateriales" oncomplete="Richfaces.showModalPanel('PanelRegistrarDistribucionMaterial')">
                                            <h:outputText value="#{data.materiales.nombreMaterial}"  />
                                        </a4j:commandLink>
                                    </rich:column>

                                    <rich:column>
                                        <f:facet name="header">
                                            <h:outputText value="Cantidad"  />
                                        </f:facet>
                                        <h:outputText value="#{data.cantidad}" />
                                        [<h:outputText value="#{data.materiales.unidadesMedida.abreviatura}" />]

                                    </rich:column>
                                    <rich:column>
                                        <f:facet name="header">
                                            <h:outputText value="Distribucion"  />
                                        </f:facet>
                                        <rich:dataTable value="#{data.distribucionMaterialesPersonal}" var="data1" id="dataDistribucion" styleClass="d"
                                                onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                                onRowMouseOver="this.style.backgroundColor='#CCDFFA';" columnClasses="tituloCampo" >
                                            <rich:column>
                                                <f:facet name="header">
                                                    <h:outputText value="Entregado por:"  />
                                                </f:facet>
                                                <h:outputText value="#{data1.personalEntrega.nombrePersonal}" styleClass="outputText1" style="width:200px"/>
                                                    
                                            </rich:column>
                                            <rich:column>
                                                <f:facet name="header">
                                                    <h:outputText value="Recepcionado por:"  />
                                                </f:facet>
                                                <h:outputText value="#{data1.personal.nombrePersonal}" styleClass="outputText1"/>
                                            </rich:column>
                                            <rich:column>
                                                <f:facet name="header">
                                                    <h:outputText value="Cantidad"  />
                                                </f:facet>
                                                <h:outputText value="#{data1.cantidadEntregada}" styleClass="outputText1"  />
                                                [<h:outputText value="#{data.materiales.unidadesMedida.abreviatura}" />]
                                            </rich:column>
                                            <rich:column>
                                                <f:facet name="header">
                                                    <h:outputText value="Fecha"  />
                                                </f:facet>
                                                <h:outputText value="#{data1.fechaDistribucion}">
                                                    <f:convertDateTime pattern="dd/MM/yyyy" />
                                                </h:outputText>
                                            </rich:column>
                                            <rich:column>
                                                <f:facet name="header">
                                                    <h:outputText value="Observaciones"  />
                                                </f:facet>
                                                <h:outputText value="#{data1.observaciones}" />
                                            </rich:column>
                                        </rich:dataTable>
                                    </rich:column>
                                </rich:dataTable>
                                </div>
                            
                            <div align="center">
                            <a4j:commandButton value="Aceptar" styleClass="btn" onclick="Richfaces.hideModalPanel('PanelRegistrarDistribucion')"/>
                            </div>
                        </h:panelGroup>
                  </a4j:form>
            </rich:modalPanel>




            


            


        </body>
    </html>

</f:view>

