

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
       </script>
        </head>
        <body >
            <a4j:form id="form1">
                <div align="center">
                    <h:outputText value="#{ManagedSecciones.cargarContenidoSecciones}"/>
                    <h:outputText styleClass="outputTextTitulo"  value="Listado de Secciones" />
                    <br><br>
                        <rich:panel id="panelEstadoCuentas" styleClass="panelBuscar" style="top:50px;left:50px;width:700px;">
                    <f:facet name="header">
                        <h:outputText value="<div   onmouseover=\"this.style.cursor='move'\" onmousedown=\"comienzoMovimiento(event, 'form1:panelEstadoCuentas');\"  >Buscar<div   style=\"margin-left:550px;\"   onclick=\"document.getElementById('form1:panelEstadoCuentas').style.visibility='hidden';document.getElementById('panelsuper').style.visibility='hidden';\" onmouseover=\"this.style.cursor='hand'\"   >Cerrar</div> </div> "
                              escape="false" />
                    </f:facet>
                    </rich:panel>

                    <rich:dataTable value="#{ManagedSecciones.seccionesList}" var="data"
                                    id="dataSecciones"
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
                                <h:outputText value="Seccion"  />
                            </f:facet>
                            <h:outputText value="#{data.nombreSeccion}"/>
                        </h:column>

                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Almacen"  />
                            </f:facet>
                            <h:outputText value="#{data.almacenes.nombreAlmacen}"  />
                        </h:column>
                        
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Filial"  />
                            </f:facet>
                            <h:outputText value="#{data.almacenes.filiales.nombreFilial}"  />
                        </h:column>

                        <h:column>
                            <f:facet name="header">
                                <h:outputText value=""  />
                            </f:facet>
                            <a4j:commandLink onclick="openPopup('navegadorDetalleSecciones.jsf?nombreSeccion=#{data.nombreSeccion} &codSeccion=#{data.codSeccion}')">
                                <h:graphicImage url="../img/areasdependientes.png" >
                                </h:graphicImage>
                            </a4j:commandLink>

                         </h:column>

                    </rich:dataTable>
                    <%-- <ws:datascroller fordatatable="dataAreasEmpresa"  />--%>
                    <br>                    
                    <a4j:commandButton value="Agregar" styleClass="btn" 
                    onclick="javascript:Richfaces.showModalPanel('panelAgregarSecciones')"
                    action="#{ManagedSecciones.agregarSecciones_action}"
                    reRender="contenidoAgregarSecciones" />
                    
                    <a4j:commandButton value="Eliminar"  styleClass="btn" 
                    onclick="javascript:if(confirm('Esta Seguro de Eliminar??')==false || validarSeleccion('form1:dataSecciones')==false){return false;}"
                    action="#{ManagedSecciones.eliminarSecciones_action}"
                    reRender="dataSecciones"
                    oncomplete="if(#{ManagedSecciones.mensaje!=''}){alert('#{ManagedSecciones.mensaje}')}"
                    />

                    <a4j:commandButton value="Editar"  styleClass="btn" 
                    onclick="javascript:if(validarSeleccion('form1:dataSecciones')==false){return false;}else{Richfaces.showModalPanel('panelEditarSecciones')}"
                    action="#{ManagedSecciones.editarSeccion_action}"
                    reRender="contenidoEditarSecciones" />

                </div>
                <!--cerrando la conexion-->
                <h:outputText value="#{ManagedFrecuenciaMantenimientoMaquinaria.closeConnection}"  />
                
            </a4j:form>
            
             



            <rich:modalPanel id="panelAgregarSecciones" minHeight="250"  minWidth="600"
                                     height="250" width="600"
                                     zindex="200"
                                     headerClass="headerClassACliente"
                                     resizeable="false" style="overflow :auto" >
                        <f:facet name="header">
                            <h:outputText value="Agregar Secciones"/>
                        </f:facet>
                        <a4j:form id="form5">


                        <h:panelGroup id="contenidoAgregarSecciones">

                                <h:panelGrid columns="3">
                                    <h:outputText value="Filial" styleClass="outputText1" />
                                    <h:outputText value="::" styleClass="outputText1" />
                                    <h:selectOneMenu value="#{ManagedSecciones.secciones.almacenes.filiales.codFilial}" styleClass="inputText">
                                        <f:selectItems value="#{ManagedSecciones.filialesList}" />
                                        <a4j:support action="#{ManagedSecciones.filial_change}" event="onchange" reRender="codAlmacen"  />
                                    </h:selectOneMenu>
                                    
                                    <h:outputText value="Almacen" styleClass="outputText1" />
                                    <h:outputText value="::" styleClass="outputText1" />
                                    <h:selectOneMenu value="#{ManagedSecciones.secciones.almacenes.codAlmacen}" styleClass="inputText" id="codAlmacen">
                                        <f:selectItems value="#{ManagedSecciones.almacenesList}" />
                                    </h:selectOneMenu>


                                    <h:outputText value="Nombre" styleClass="outputText1" />
                                    <h:outputText value="::" styleClass="outputText1" />
                                    <h:inputText value="#{ManagedSecciones.secciones.nombreSeccion}" styleClass="inputText" size="30"  />
                                    

                                    <h:outputText value="Observaciones" styleClass="outputText1" />
                                    <h:outputText value="::" styleClass="outputText1" />

                                    <h:inputTextarea value="#{ManagedSecciones.secciones.obsSeccion}" styleClass="inputText" rows="4" cols="40"  />

                                </h:panelGrid>
                                <br/>
                                <div align="center">
                                <a4j:commandButton styleClass="btn" value="Guardar"
                                onclick="javascript:Richfaces.hideModalPanel('panelAgregarSecciones');"
                                action="#{ManagedSecciones.guardarAgregarSecciones_action}"
                                reRender="dataSecciones"
                                                    />
                                <input type="button" value="Cancelar" onclick="javascript:Richfaces.hideModalPanel('panelAgregarSecciones')" class="btn" />
                                </div>
                        </h:panelGroup>
                        </a4j:form>
            </rich:modalPanel>
            

            <rich:modalPanel id="panelEditarSecciones" minHeight="450"  minWidth="600"
                                     height="450" width="600"
                                     zindex="200"
                                     headerClass="headerClassACliente"
                                     resizeable="false" style="overflow :auto" >
                        <f:facet name="header">
                            <h:outputText value="Editar Secciones"/>
                        </f:facet>
                        <a4j:form id="form4">


                        <h:panelGroup id="contenidoEditarSecciones">
                            
                                <h:panelGrid columns="3">
                                    <h:outputText value="Filial" styleClass="outputText1" />
                                    <h:outputText value="::" styleClass="outputText1" />
                                    <h:outputText value="#{ManagedSecciones.secciones.almacenes.filiales.nombreFilial}" styleClass="outputText1"  />

                                    <h:outputText value="Almacen" styleClass="outputText1" />
                                    <h:outputText value="::" styleClass="outputText1" />
                                    <h:outputText value="#{ManagedSecciones.secciones.almacenes.nombreAlmacen}" styleClass="outputText1"  />

                                    <h:outputText value="Nombre" styleClass="outputText1" />
                                    <h:outputText value="::" styleClass="outputText1" />
                                    <h:inputText value="#{ManagedSecciones.secciones.nombreSeccion}" styleClass="inputText" size="30"  />

                                    <h:outputText value="Estado" styleClass="outputText1" />
                                    <h:outputText value="::" styleClass="outputText1" />
                                    <h:selectOneMenu value="#{ManagedSecciones.secciones.estadoReferencial.codEstadoRegistro}" styleClass="inputText">
                                        <f:selectItems value="#{ManagedSecciones.estadosReferencialesList}" />
                                    </h:selectOneMenu>


                                    <h:outputText value="Observaciones" styleClass="outputText1" />
                                    <h:outputText value="::" styleClass="outputText1" />

                                    <h:inputTextarea value="#{ManagedSecciones.secciones.obsSeccion}" styleClass="inputText" rows="4" cols="40"  />

                                </h:panelGrid>

                                <div style="overflow:auto;height:200px;text-align:center">

                                <rich:dataTable value="#{ManagedSecciones.seccionesDetalleList}" var="data"
                                                id="dataSeccionesDetalle"
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
                                            <h:outputText value="Capitulo"  />
                                        </f:facet>
                                        <h:outputText value="#{data.capitulos.nombreCapitulo}"/>
                                    </h:column>
                                  

                                    <h:column>
                                        <f:facet name="header">
                                            <h:outputText value="Grupo"  />
                                        </f:facet>
                                        <h:outputText value="#{data.grupos.nombreGrupo}"  />
                                    </h:column>

                                    <h:column>
                                        <f:facet name="header">
                                            <h:outputText value="Item"  />
                                        </f:facet>
                                        <h:outputText value="#{data.materiales.nombreMaterial}"  />
                                    </h:column>

                                </rich:dataTable>
                                </div>
                                <div align="center">
                                <h:panelGrid columns="3" >
                                     <a4j:commandButton styleClass="btn" value="Agregar"
                                            onclick="javascript:Richfaces.showModalPanel('panelAgregarSeccionDetalle');"
                                            action="#{ManagedSecciones.agregarSeccionDetalle_action}"
                                            reRender="contenidoAgregarSeccionDetalle"
                                      />
                                      <a4j:commandButton styleClass="btn" value="Eliminar"
                                            onclick="if(confirm('Esta seguro de eliminar')==false){return false;}else{if(validarSeleccion('form4:dataSeccionesDetalle')==false){return false;}}"
                                            action="#{ManagedSecciones.eliminarSeccionesDetalle_action}"
                                            reRender="dataSeccionesDetalle"
                                            oncomplete="if(#{ManagedSecciones.mensaje!=''}){alert('#{ManagedSecciones.mensaje}')}"
                                      />

                                </h:panelGrid>
                                </div>

                                <br/>
                                
                                <div align="center">
                                <a4j:commandButton styleClass="btn" value="Guardar"
                                onclick="javascript:Richfaces.hideModalPanel('panelEditarSecciones');"
                                action="#{ManagedSecciones.guardarEditarSeccion_action}"
                                reRender="dataSecciones"
                                                    />
                                <input type="button" value="Cancelar" onclick="javascript:Richfaces.hideModalPanel('panelEditarSecciones')" class="btn" />
                                </div>
                        </h:panelGroup>
                        </a4j:form>
            </rich:modalPanel>
            

            <rich:modalPanel id="panelAgregarSeccionDetalle" minHeight="200"  minWidth="600"
                                     height="200" width="600"
                                     zindex="200"
                                     headerClass="headerClassACliente"
                                     resizeable="false" style="overflow :auto" >
                        <f:facet name="header">
                            <h:outputText value="Agregar Capitulos"/>
                        </f:facet>
                        <a4j:form>
                            <h:panelGroup id="contenidoAgregarSeccionDetalle">
                                <h:panelGrid columns="3">
                                    <h:outputText value="Capitulo" styleClass="outputText1" />
                                    <h:outputText value="::" styleClass="outputText1" />
                                    <h:selectOneMenu value="#{ManagedSecciones.seccionesDetalle.capitulos.codCapitulo}" styleClass="inputText">
                                        <f:selectItems value="#{ManagedSecciones.capitulosList}" />
                                        <a4j:support action="#{ManagedSecciones.capitulos_change}" reRender="codGrupo,codMaterial" event="onchange" />
                                    </h:selectOneMenu>

                                    <h:outputText value="Grupo" styleClass="outputText1" />
                                    <h:outputText value="::" styleClass="outputText1" />
                                    <h:selectOneMenu value="#{ManagedSecciones.seccionesDetalle.grupos.codGrupo}" id="codGrupo" styleClass="inputText">
                                        <f:selectItems value="#{ManagedSecciones.gruposList}" />
                                        <a4j:support action="#{ManagedSecciones.grupos_change}" reRender="codMaterial" event="onchange" />
                                    </h:selectOneMenu>
                                    
                                    
                                    <h:outputText value="Item" styleClass="outputText1" />
                                    <h:outputText value="::" styleClass="outputText1" />
                                    <h:selectOneMenu value="#{ManagedSecciones.seccionesDetalle.materiales.codMaterial}" id="codMaterial" styleClass="inputText">
                                        <f:selectItems value="#{ManagedSecciones.materialesList}" />
                                    </h:selectOneMenu>




                                </h:panelGrid>
                                    <div align="center">
                                    <a4j:commandButton styleClass="btn" value="Registrar"
                                                       onclick="if(confirm('Esta seguro de registrar?')==false){return false;}"                                                       
                                                       action="#{ManagedSecciones.guardarAgregarSeccionDetalle_action}"
                                                       reRender="dataSeccionesDetalle"
                                                       oncomplete="Richfaces.hideModalPanel('panelAgregarSeccionDetalle')"
                                                        />
                                    <input type="button" value="Cancelar" onclick="javascript:Richfaces.hideModalPanel('panelAgregarSeccionDetalle')" class="btn" />
                                    </div>
                            </h:panelGroup>
                        </a4j:form>
            </rich:modalPanel>



        </body>
    </html>
    
</f:view>

