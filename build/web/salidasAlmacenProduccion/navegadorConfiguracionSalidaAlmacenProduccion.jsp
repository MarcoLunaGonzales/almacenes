<%-- 
    Document   : navegadorConfiguracionSalidaAlmacenProduccion
    Created on : 11-06-2012, 10:30:43 AM
    Author     : hvaldivia
--%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib prefix="a4j" uri="http://richfaces.org/a4j"%>
<%@taglib prefix="rich" uri="http://richfaces.org/rich"%>
<%@taglib prefix="ws" uri="http://isawi.org/ws"%>

<f:view>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>JSP Page</title>
        <link rel="STYLESHEET" type="text/css" href="../css/ventas.css" />
        <script type="text/javascript" src="../js/general.js" ></script>
        <script type="text/javascript">
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
                </script>
    </head>
    <body>
        <a4j:form id="form1">
            <div align="center">
            <h:outputText value="#{ManagedProgramaProduccion.cargarContenidoConfiguracionSalidasAlmacenProduccion}" />
                            <rich:dataTable value="#{ManagedProgramaProduccion.configuracionSalidasMaterialProduccionList}"
                                            var="data" id="dataConfiguracion"
                                            onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                            onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                            headerClass="headerClassACliente" width="40%">
                                <h:column>
                                    <f:facet name="header">
                                        <h:outputText value="" />
                                    </f:facet>
                                    <h:selectBooleanCheckbox value="#{data.checked}" />
                                </h:column>
                                <h:column>
                                    <f:facet name="header">
                                        <h:outputText value="Personal"  />
                                    </f:facet>
                                    <h:outputText value="#{data.personal.nombrePersonal}"  /> &nbsp;
                                    <h:outputText value="#{data.personal.apPaternoPersonal}"  />&nbsp;
                                    <h:outputText value="#{data.personal.apMaternoPersonal}"  />
                                </h:column>
                                <h:column>
                                    <f:facet name="header">
                                        <h:outputText value="Tipo de Material"  />
                                    </f:facet>
                                    <h:outputText value="#{data.tiposMaterial.nombreTipoMaterial}"  />
                                </h:column>
                                <h:column>
                                    <f:facet name="header">
                                        <h:outputText value="Tipo de Salida"  />
                                    </f:facet>
                                    <h:outputText value="#{data.tiposSalidaAlmacenProduccion.nombreTipoSalidaAlmacenProduccion}"  />
                                </h:column>
                                
                            </rich:dataTable>

                            <input type="button" value="Agregar" onclick="Richfaces.showModalPanel('panelAgregarConfiguracion')" class="btn" />
                            <%--<h:commandButton value="Guardar" action="#{ManagedProgramaProduccion.guardarTipoSalidaAlmacenProduccion_action}" styleClass="btn" />--%>
                            <h:commandButton value="Eliminar" action="#{ManagedProgramaProduccion.eliminarConfiguracionTiposSalidaAlmacenProduccion_action}"
                            onclick="if(eliminar('form1:dataConfiguracion')==false){return false;}" styleClass="btn" />
            </div>
        </a4j:form>

        <rich:modalPanel id="panelAgregarConfiguracion" minHeight="300"  minWidth="600"
                                     height="300" width="600"  zindex="200"  headerClass="headerClassACliente"
                                     resizeable="false" >
                        <f:facet name="header">
                            <h:outputText value="Agregar Configuracion"/>
                        </f:facet>
                        <a4j:form id="form2">
                            <h:panelGroup id="contenidoAgregarConfiguracion">
                            <div align="center">
                                <h:panelGrid columns="2" style="text-align:left">
                                    <h:outputText value="Personal :" styleClass="outputText1" />
                                    <h:selectOneMenu value="#{ManagedProgramaProduccion.configuracionSalidaAlmacenProduccion.personal.codPersonal}" styleClass="inputText" >
                                        <f:selectItems value="#{ManagedProgramaProduccion.personalList}" />
                                    </h:selectOneMenu>
                                    <h:outputText value="Tipo Material :" styleClass="outputText1" />
                                    <h:selectOneMenu value="#{ManagedProgramaProduccion.configuracionSalidaAlmacenProduccion.tiposMaterial.codTipoMaterial}" styleClass="inputText" >
                                        <f:selectItems value="#{ManagedProgramaProduccion.tiposMaterialList}" />
                                    </h:selectOneMenu>
                                    <h:outputText value="Tipo Salida Material :" styleClass="outputText1" />
                                    <h:selectOneMenu value="#{ManagedProgramaProduccion.configuracionSalidaAlmacenProduccion.tiposSalidaAlmacenProduccion.codTipoSalidaAlmacenProduccion}" styleClass="inputText" >
                                        <f:selectItems value="#{ManagedProgramaProduccion.tiposSalidasMaterialList}" />
                                    </h:selectOneMenu>
                                </h:panelGrid>
                        <br/>
                        
                        <a4j:commandButton styleClass="btn" value="Registrar"                                           
                                           action="#{ManagedProgramaProduccion.guardarConfiguracionTiposSalidaAlmacenProduccion_action}"
                                           reRender="dataConfiguracion" oncomplete="Richfaces.hideModalPanel('panelAgregarConfiguracion')"/>
                        <input type="button" value="Cancelar" onclick="javascript:Richfaces.hideModalPanel('panelAgregarConfiguracion')" class="btn" />
                        </div>
                        </h:panelGroup>
                        </a4j:form>
          </rich:modalPanel>
        
    </body>
</html>
</f:view>
