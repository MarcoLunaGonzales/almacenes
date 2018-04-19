
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
                function getCodigo(cod_maquina, codigo) {
                    //  alert(codigo);
                    location = '../partes_maquinaria/navegador_partes_maquinaria.jsf?cod_maquina=' + cod_maquina + '&codigo=' + codigo;
                }

                function editarItem(nametable) {
                    var count = 0;
                    var elements = document.getElementById(nametable);
                    var rowsElement = elements.rows;
                    for (var i = 1; i < rowsElement.length; i++) {
                        var cellsElement = rowsElement[i].cells;
                        var cel = cellsElement[0];
                        if (cel.getElementsByTagName('input')[0].type == 'checkbox') {
                            if (cel.getElementsByTagName('input')[0].checked) {
                                count++;
                            }

                        }

                    }
                    if (count == 1) {
                        return true;
                    } else if (count == 0) {
                        alert('No escogio ningun registro');
                        return false;
                    }
                    else if (count > 1) {
                        alert('Solo puede escoger un registro');
                        return false;
                    }
                }


                function asignar(nametable) {

                    var count = 0;
                    var elements = document.getElementById(nametable);
                    var rowsElement = elements.rows;
                    for (var i = 1; i < rowsElement.length; i++) {
                        var cellsElement = rowsElement[i].cells;
                        var cel = cellsElement[0];
                        alert('hola');
                        if (cel.getElementsByTagName('input')[0].type == 'checkbox') {
                            if (cel.getElementsByTagName('input')[0].checked) {
                                count++;
                            }
                        }
                    }
                    if (count == 0) {
                        alert('No selecciono ningun Registro');
                        return false;
                    } else {
                        if (confirm('Desea Asignar como Area Raiz')) {
                            if (confirm('Esta seguro de Asignar como Area Raiz')) {
                                return true;
                            } else {
                                return false;
                            }
                        } else {
                            return false;
                        }

                    }

                }
                function eliminar(nametable) {
                    var count1 = 0;
                    var elements1 = document.getElementById(nametable);
                    var rowsElement1 = elements1.rows;
                    //alert(rowsElement1.length);            
                    for (var i = 1; i < rowsElement1.length; i++) {
                        var cellsElement1 = rowsElement1[i].cells;
                        var cel1 = cellsElement1[0];
                        if (cel1.getElementsByTagName('input').length > 0) {
                            if (cel1.getElementsByTagName('input')[0].type == 'checkbox') {
                                if (cel1.getElementsByTagName('input')[0].checked) {
                                    count1++;
                                }
                            }
                        }

                    }
                    //alert(count1);
                    if (count1 == 0) {
                        alert('No escogio ningun registro');
                        return false;
                    } else {

                        var count = 0;
                        var elements = document.getElementById(nametable);
                        var rowsElement = elements.rows;

                        for (var i = 0; i < rowsElement.length; i++) {
                            var cellsElement = rowsElement[i].cells;
                            var cel = cellsElement[0];
                            if (cel.getElementsByTagName('input').length > 0) {
                                if (cel.getElementsByTagName('input')[0].type == 'checkbox') {
                                    if (cel.getElementsByTagName('input')[0].checked) {
                                        count++;
                                    }
                                }
                            }

                        }
                        if (count == 0) {
                            //alert('No escogio ningun registro');
                            return false;
                        }
                        //var cantidadeliminar=document.getElementById('form1:cantidadeliminar');
                        //cantidadeliminar.value=count;
                        return true;

                    }
                }

                function validarSeleccion(nametable) {
                    var count = 0;
                    var elements = document.getElementById(nametable);
                    var rowsElement = elements.rows;
                    for (var i = 1; i < rowsElement.length; i++) {
                        var cellsElement = rowsElement[i].cells;
                        var cel = cellsElement[0];
                        if (cel.getElementsByTagName('input')[0].type == 'checkbox') {
                            if (cel.getElementsByTagName('input')[0].checked) {
                                count++;
                            }

                        }

                    }
                    if (count == 1) {
                        return true;
                    } else if (count == 0) {
                        alert('No escogio ningun registro');
                        return false;
                    }
                    else if (count > 1) {
                        alert('Solo puede escoger un registro');
                        return false;
                    }
                }
                function verManteminiento(cod_maquina, codigo) {
                    location = '../partes_maquinaria/navegador_partes_maquinaria.jsf?cod_maquina=' + cod_maquina + '&codigo=' + codigo;
                }
            </script>
        </head>
        <body >
            <a4j:form id="form1">
                <div align="center">
                    <h:outputText value="#{ManagedConfiguracionSolicitudSalidas.cargarContenidoConfiguracion}"/>
                    <h:outputText styleClass="outputTextTitulo"  value="Listado de Capitulos" />
                    <br><br>

                    <rich:dataTable value="#{ManagedConfiguracionSolicitudSalidas.listCapitulos}" var="data"
                                    id="dataCapitulos"
                                    onRowMouseOut="this.style.backgroundColor='#FFFFFF';" 
                                    onRowMouseOver="this.style.backgroundColor='#DDE3E4';" 
                                    headerClass="headerClassACliente" 
                                    binding="#{ManagedConfiguracionSolicitudSalidas.capitulosSelect}"
                                    >
                        <rich:column width="25px;">
                            <f:facet name="header">
                                <h:outputText value=""  />
                            </f:facet>
                            <a4j:commandButton image="../img/buscar.png"  
                                               action="#{ManagedConfiguracionSolicitudSalidas.actionSelectCapitulo}"
                                               style="font-size:9px;text-align:left;"
                                               styleClass="btn" 
                                               oncomplete="Richfaces.showModalPanel('panelGrupos');"
                                               reRender="dataGrupos"
                                               >

                            </a4j:commandButton>
                        </rich:column>
                        <rich:column style="background-color:#{data.color}; text-align:left;">
                            <f:facet name="header">
                                <h:outputText value="Código"/>
                            </f:facet>
                            <h:outputText value="#{data.codCapitulo}"  
                                          >
                            </h:outputText>
                        </rich:column>

                        <rich:column style="background-color:#{data.color}; text-align:left;">
                            <f:facet name="header">
                                <h:outputText value="Capitulos"/>
                            </f:facet>
                            <h:outputText value="#{data.nombreCapitulo}"  
                                          >
                            </h:outputText>
                        </rich:column>

                        <rich:column >
                            <f:facet name="header">
                                <h:outputText value="Observaciones"  />
                            </f:facet>
                            <h:outputText value="#{data.capituloCompleto?'Capitulo habilitado completamente.':(data.capituloParcial?'Algunos grupos o materiales del capitulo se aceptan en las solicitudes de salida.':'Capitulo deshabilitado en las solicitudes de salida.')}"  />
                        </rich:column>



                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value="Habilitado en Almacén"  />
                            </f:facet>
                            <h:selectOneMenu value="#{data.capituloCompleto}"  >
                                <f:selectItem itemLabel="Habilitado" itemValue="true"></f:selectItem>
                                <f:selectItem itemLabel="Deshabilitado" itemValue="false"></f:selectItem>
                            </h:selectOneMenu>
                        </rich:column>

                    </rich:dataTable>
                    <%-- <ws:datascroller fordatatable="dataAreasEmpresa"  />--%>
                    <br>                    


                    <a4j:commandButton value="Guardar Cambios Capitulo(s)"  
                                       styleClass="btn" 
                                       onclick="javascript:if(confirm('Esta Seguro de Guardar los cambios??')==false ){return false;}"
                                       action="#{ManagedConfiguracionSolicitudSalidas.actionUpdateCapitulos}"
                                       reRender="form1"/>



                </div>

            </a4j:form>

            <rich:modalPanel id="panelGrupos" 
                             height="480" width="800"
                             zindex="200"
                             headerClass="headerClassACliente"
                             resizeable="false" style="overflow :auto" >
                <f:facet name="header">
                    <h:outputText value="Detalle de Grupos"/>
                </f:facet>
                <a4j:form id="form2">

                    <div style="overflow:auto;height:350px;width:100%;text-align:center">
                        <rich:dataTable value="#{ManagedConfiguracionSolicitudSalidas.listGrupos}" 
                                        var="data"
                                        id="dataGrupos"
                                        onRowMouseOut="this.style.backgroundColor='#FFFFFF';" 
                                        onRowMouseOver="this.style.backgroundColor='#DDE3E4';" 
                                        headerClass="headerClassACliente" 
                                        binding="#{ManagedConfiguracionSolicitudSalidas.gruposSelect}"

                                        >
                            <rich:column width="25px;">
                                <f:facet name="header">
                                    <h:outputText value=""  />
                                </f:facet>
                                <a4j:commandButton image="../img/buscar.png"  
                                                   action="#{ManagedConfiguracionSolicitudSalidas.actionSelectGrupo}"
                                                   style="font-size:9px;text-align:left;"
                                                   styleClass="btn" 
                                                   oncomplete="Richfaces.showModalPanel('panelMateriales');"
                                                   reRender="dataMateriales"
                                                   >

                                </a4j:commandButton>
                            </rich:column>
                            <rich:column style="background-color:#{data.color}; text-align:left;">
                                <f:facet name="header">
                                    <h:outputText value="Código"/>
                                </f:facet>
                                <h:outputText value="#{data.codGrupo}"  
                                              >
                                </h:outputText>
                            </rich:column>
                            <rich:column style="background-color:#{data.color}">
                                <f:facet name="header">
                                    <h:outputText value="Grupos"/>
                                </f:facet>

                                <h:outputText value="#{data.nombreGrupo}">
                                </h:outputText>
                            </rich:column>

                            <rich:column >
                                <f:facet name="header">
                                    <h:outputText value="Observaciones"  />
                                </f:facet>
                                <h:outputText value="#{data.grupoCompleto?'Grupo habilitado completamente.':(data.grupoParcial?'Algunos materiales del grupo se aceptan en las solicitudes de salida.':'Grupo deshabilitado en las solicitudes de salida.')}"  />
                            </rich:column>



                            <rich:column>
                                <f:facet name="header">
                                    <h:outputText value="Habilitado en Almacén"  />
                                </f:facet>
                                <h:selectOneMenu value="#{data.grupoCompleto}"  >
                                    <f:selectItem itemLabel="Habilitado" itemValue="true"></f:selectItem>
                                    <f:selectItem itemLabel="Deshabilitado" itemValue="false"></f:selectItem>
                                </h:selectOneMenu>
                            </rich:column>

                        </rich:dataTable>
                    </div>
                    <div align="center">
                        <a4j:commandButton value="Guardar Cambios Grupo(s)"  
                                           styleClass="btn" 
                                           onclick="javascript:if(confirm('Esta Seguro de Guardar los cambios??')==false ){return false;}"
                                           action="#{ManagedConfiguracionSolicitudSalidas.actionUpdateGrupos}"
                                           timeout="7200"
                                           reRender="dataGrupos, form1"/>                          
                        <input type="button" value="Cancelar" onclick="javascript:Richfaces.hideModalPanel('panelGrupos')" class="btn" />
                    </div>

                </a4j:form>
            </rich:modalPanel>

            <rich:modalPanel id="panelMateriales" 
                             height="480" 
                             width="800"
                             zindex="200"
                             headerClass="headerClassACliente"
                             resizeable="false" 
                             style="overflow :auto" >
                <f:facet name="header">
                    <h:outputText value="Detalle de Materiales"/>
                </f:facet>
                <a4j:form id="form3">
                    <div style="overflow:auto;height:350px;width:100%;text-align:center">
                        <rich:dataTable value="#{ManagedConfiguracionSolicitudSalidas.listMateriales}" 
                                        var="data"
                                        id="dataMateriales"
                                        onRowMouseOut="this.style.backgroundColor='#FFFFFF';" 
                                        onRowMouseOver="this.style.backgroundColor='#DDE3E4';" 
                                        headerClass="headerClassACliente" 

                                        >
                            <rich:column style="background-color:#{data.color}; text-align:left;">
                                <f:facet name="header">
                                    <h:outputText value="Código"/>
                                </f:facet>
                                <h:outputText value="#{data.codMaterial}"  
                                              >
                                </h:outputText>
                            </rich:column>
                            <rich:column style="background-color:#{data.color}">
                                <f:facet name="header">
                                    <h:outputText value="Materiales"/>
                                </f:facet>

                                <h:outputText value="#{data.nombreMaterial}">
                                </h:outputText>
                            </rich:column>

                            <rich:column >
                                <f:facet name="header">
                                    <h:outputText value="Observaciones"  />
                                </f:facet>
                                <h:outputText value="#{data.materialHabilitado?'Material habilitado.':'Material deshabilitado en las solicitudes de salida.'}"  />
                            </rich:column>
                            <rich:column>
                                <f:facet name="header">
                                    <h:outputText value="Habilitado en Almacén"  />
                                </f:facet>
                                <h:selectOneMenu value="#{data.materialHabilitado}"  >
                                    <f:selectItem itemLabel="Habilitado" itemValue="true"></f:selectItem>
                                    <f:selectItem itemLabel="Deshabilitado" itemValue="false"></f:selectItem>
                                </h:selectOneMenu>
                            </rich:column>

                        </rich:dataTable>
                    </div>
                    <div align="center">
                        <a4j:commandButton value="Guardar Cambios Material(s)"  styleClass="btn" 
                                           onclick="javascript:if(confirm('Esta Seguro de Guardar los cambios??')==false ){return false;}"
                                           action="#{ManagedConfiguracionSolicitudSalidas.actionUpdateMateriales}"
                                           reRender="dataMateriales, dataGrupos, dataCapitulos"
                                           timeout="7200"
                                           />                              
                        <input type="button" 
                               value="Cancelar" 
                               onclick="javascript:Richfaces.hideModalPanel('panelMateriales')" 
                               class="btn" />
                    </div>

                </a4j:form>
            </rich:modalPanel>

        </body>
    </html>

</f:view>

