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
                window.open(url,'DETALLE','top=50,left=200,width=800,height=600,scrollbars=1,resizable=1');
            }
            function seleccionarItem(nametable){
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


        </script>
    </head>
    <body >
        <a4j:form id="form1">
            <h:outputText value="#{ManagedIngresoAlmacenOrdenCompra.cargarIngresoAlmacenOrdenCompra}"/>
            
            <center>
                <rich:panel  headerClass="headerClassACliente" style="width:70%;">
                        <f:facet name="header">
                            <h:outputText styleClass="outputTextTitulo"  value="Gestionar Ingresos a traves de una Orden de Compra" />
                        </f:facet>

                    <h:outputText styleClass="outputTextTitulo"  value="Nro. OC : " />&nbsp;
                    <h:inputText
                        onkeypress="valMAY();" value="#{ManagedIngresoAlmacenOrdenCompra.codOrdenCompra}"  styleClass="inputText" size="10" id="productobuscar"  >
                    </h:inputText>&nbsp;&nbsp;&nbsp;
                    <h:outputText styleClass="outputTextTitulo"   value="Gestion :"  />&nbsp;
                    <h:selectOneMenu styleClass="inputText" value="#{ManagedIngresoAlmacenOrdenCompra.codGestion}" id="codGestion"  >
                        <f:selectItems value="#{ManagedIngresoAlmacenOrdenCompra.gestionesList}"  />
                    </h:selectOneMenu>&nbsp;&nbsp;&nbsp;

                    <h:outputText  styleClass="outputTextTitulo"   value="Material :"  />&nbsp;
                    <h:selectOneMenu styleClass="inputText" style="width:250px" value="#{ManagedIngresoAlmacenOrdenCompra.codMaterial}" id="codAlmacenActiva"  >
                        <f:selectItems value="#{ManagedIngresoAlmacenOrdenCompra.materialesList}"  />
                    </h:selectOneMenu>&nbsp;&nbsp;&nbsp;
                    <br>
                    <br>
                    <h:outputText styleClass="outputTextTitulo"   value="Proveedor :"  />&nbsp;
                    <h:selectOneMenu styleClass="inputText" style="width:250px" value="#{ManagedIngresoAlmacenOrdenCompra.codProveedor}" id="codProveedor"  >
                        <f:selectItems value="#{ManagedIngresoAlmacenOrdenCompra.proveedoresList}"  />
                    </h:selectOneMenu>&nbsp;&nbsp;&nbsp;
                    <h:outputText styleClass="outputTextTitulo"   value="Capitulo :"  />&nbsp;
                    <h:selectOneMenu styleClass="inputText" style="width:250px" value="#{ManagedIngresoAlmacenOrdenCompra.codCapitulo}"  id="codCapitulo"  >
                        <f:selectItems value="#{ManagedIngresoAlmacenOrdenCompra.capitulosList}"  />
                         <a4j:support action="#{ManagedIngresoAlmacenOrdenCompra.capitulos_change}" reRender="codGrupo" event="onchange" />
                    </h:selectOneMenu>&nbsp;&nbsp;&nbsp;
                    <h:outputText styleClass="outputTextTitulo"   value="Grupo :"  />&nbsp;

                    <h:selectOneMenu styleClass="inputText" style="width:250px" value="#{ManagedIngresoAlmacenOrdenCompra.codGrupo}" id="codGrupo"  >
                        <f:selectItems value="#{ManagedIngresoAlmacenOrdenCompra.gruposList}"  />
                    </h:selectOneMenu>&nbsp;&nbsp;&nbsp;

                    <a4j:commandButton value="Buscar" reRender="dataOrdenesCompra" styleClass="btn"   
                                       action="#{ManagedIngresoAlmacenOrdenCompra.buscarProductosOnClickK}"  />
                </rich:panel>
               
                <br><br>
                <rich:dataTable value="#{ManagedIngresoAlmacenOrdenCompra.ordenesCompraList}" var="data"
                                id="dataOrdenesCompra"
                                onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                headerClass="headerClassACliente" >
                    <f:facet name="header">
                        <rich:columnGroup>
                                <rich:column>
                                    <h:outputText value="Nro Orden de Compra"/>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Fecha Emisión"/>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Estados Orden de Compra"/>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Tipo de Compra"/>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Proveedor"/>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Almacen de Entrega"/>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Reporte"/>
                                </rich:column>
                                <rich:column rendered="#{ManagedIngresoAlmacenOrdenCompra.administradorAlmacen}">
                                    <h:outputText value="Generar Ingreso"/>
                                </rich:column>
                        </rich:columnGroup>
                    </f:facet>    
                    <rich:column>
                        <a4j:commandLink onclick="openPopup('../reportes/ordenesCompra/reporteOrdenCompra.jsf?codigo=#{data.codOrdenCompra}',this);">
                            <h:outputText  value="#{data.nroOrdenCompra}"  />
                        </a4j:commandLink>
                    </rich:column>
                    <rich:column>
                        <h:outputText value="#{data.fechaEmision}">
                            <f:convertDateTime pattern="dd/MM/yyyy" />
                        </h:outputText>
                    </rich:column>
                    <rich:column>
                        <h:outputText value="#{data.estadosCompra.nombreEstadoCompra}"  />
                    </rich:column>
                    <rich:column>
                        <h:outputText value="#{data.tiposCompra.nombreTipoCompra}"  />
                    </rich:column>
                    <rich:column>
                        <h:outputText value="#{data.proveedores.nombreProveedor}"  />
                    </rich:column>
                    <rich:column>
                        <h:outputText value="#{data.almacenEntrega.nombreAlmacen}"  />
                    </rich:column>
                    <rich:column>
                        <a4j:commandLink onclick="openPopup('navegadorReporteOrdenCompraDetalle.jsf?codOrdenCompra=#{data.codOrdenCompra}&codAlmacen=#{data.almacenEntrega.codAlmacen}')" >
                            <h:graphicImage url="../img/report.png" />
                        </a4j:commandLink>
                    </rich:column>
                    <rich:column rendered="#{ManagedIngresoAlmacenOrdenCompra.administradorAlmacen}">
                        <a4j:commandButton value="Generar Ingreso" styleClass="btn"  
                                           action="#{ManagedIngresoAlmacenOrdenCompra.generarIngresoAlmacen_action}"
                                           rendered="#{data.almacenEntrega.codAlmacen eq ManagedAccesoSistema.almacenesGlobal.codAlmacen}"
                                            reRender="contenidoFrecuenciaMantenimentoMaquinaria,dataBuscarMaterial" 
                                            oncomplete=" if(#{ManagedIngresoAlmacenOrdenCompra.mensaje!=' existen items que no pertenecen a la seccion '})
                                                    {if(#{ManagedIngresoAlmacenOrdenCompra.mensaje!=''}){alert('#{ManagedIngresoAlmacenOrdenCompra.mensaje}')}else{location = 'agregarIngresosAlmacenOrdenCompra.jsf'}}
                                                    else{if(confirm('existen items que no pertenecen a la seccion, desea cambiar el grupo y capitulo')){Richfaces.showModalPanel('panelAgregarItem')}}
                                                    ">
                            <f:setPropertyActionListener value="#{data}" target="#{ManagedIngresoAlmacenOrdenCompra.ordenesCompra}"/>
                        </a4j:commandButton>
                    </rich:column>
                    <f:facet name="footer">
                        <rich:columnGroup>
                            <rich:column colspan="8" style="text-align: center !important;">
                                <a4j:commandLink  action="#{ManagedIngresoAlmacenOrdenCompra.atras_action}"   
                                                  rendered="#{ManagedIngresoAlmacenOrdenCompra.begin > 9}" 
                                                  reRender="dataOrdenesCompra">
                                    <h:outputText value="Anterior"/>
                                    <h:graphicImage url="../img/previous.gif"  style="border:0px solid red"   alt="PAGINA ANTERIOR"  />
                                </a4j:commandLink>
                                <a4j:commandLink  action="#{ManagedIngresoAlmacenOrdenCompra.siguiente_action}" 
                                                  rendered="#{ManagedIngresoAlmacenOrdenCompra.cantidadfilas >= 9}"
                                                  reRender="dataOrdenesCompra"
                                                  style="margin-left:5px">
                                    <h:graphicImage url="../img/next.gif"  style="border:0px solid red"  alt="PAGINA SIGUIENTE" />
                                    <h:outputText value="Siguiente"/>
                                </a4j:commandLink>
                            </rich:column>
                        </rich:columnGroup>
                    </f:facet>
                                
                    
                </rich:dataTable>

            </center> 

        </a4j:form>

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
        
        <%--inicio alejandro--%>
             <rich:modalPanel id="panelEditarItem" minHeight="300"  minWidth="600"
                                     height="300" width="700"
                                     zindex="200"
                                     headerClass="headerClassACliente"
                                     resizeable="false" style="overflow:auto"  >
                        <f:facet name="header">
                            <h:outputText value="#{ManagedIngresoAlmacenOrdenCompra.materialEditar.nombreMaterial}"/>
                            <%--f:facet name="controls">
                            <a4j:commandLink onclick="javascript:Richfaces.hideModalPanel('panelEditarItem')" style="color: black" value="cerrar"></a4j:commandLink>
                            </f:facet--%>
                        </f:facet>



                        <a4j:form id="form3">
                        <h:panelGroup id="contenidoEditarItem">
                             <center>
                            <h:panelGrid columns="3">

                                <h:outputText value="Capitulo" styleClass="outputText1"/>
                                 <h:outputText value="::" styleClass="outputText1"/>
                                 <h:selectOneMenu value="#{ManagedIngresoAlmacenOrdenCompra.materialEditar.grupos.capitulos.codCapitulo}" styleClass="outputText2" >
                                     <f:selectItems value="#{ManagedIngresoAlmacenOrdenCompra.listaCapitulos}"/>
                                     <a4j:support event="onchange" action="#{ManagedIngresoAlmacenOrdenCompra.generarGrupos}" reRender="gruposID"   />
                                 </h:selectOneMenu >
                                   <h:outputText value="Grupo" styleClass="outputText1"/>
                                     <h:outputText value="::" styleClass="outputText1"/>
                                     <h:selectOneMenu value="#{ManagedIngresoAlmacenOrdenCompra.materialEditar.grupos.codGrupo}" id="gruposID" styleClass="outputText2">
                                         <f:selectItems value="#{ManagedIngresoAlmacenOrdenCompra.listaGrupos}"/>
                                      </h:selectOneMenu>
                            </h:panelGrid>
                            <h:panelGrid columns="3">
                            <rich:panel  headerClass="headerClassACliente">
                                <f:facet name="header">
                                    <h:outputText value="Item de producción"/>
                                </f:facet>
                                <h:selectOneRadio value="#{ManagedIngresoAlmacenOrdenCompra.materialEditar.materialProduccion}" styleClass="outputText2">
                                <f:selectItem itemValue="1" itemLabel="si" />

                                <f:selectItem itemValue="0" itemLabel="no" />
                                <a4j:support event="onclick" action="#{ManagedIngresoAlmacenOrdenCompra.cambiarBoolean}" reRender="panelMostrar1,datosMInimos"   />
                            </h:selectOneRadio>
                            </rich:panel>
                            <h:panelGroup id="panelMostrar1" >
                                <rich:panel rendered="#{ManagedIngresoAlmacenOrdenCompra.mostrar}" headerClass="headerClassACliente">
                                <f:facet name="header">
                                    <h:outputText value="Item para muestreo"/>
                                </f:facet>
                            <h:selectOneRadio value="#{ManagedIngresoAlmacenOrdenCompra.materialEditar.materialMuestreo}" styleClass="outputText2">
                                <f:selectItem itemValue="1" itemLabel="si" />
                               
                                <f:selectItem itemValue="0" itemLabel="no"/>
                                <a4j:support event="onclick" action="#{ManagedIngresoAlmacenOrdenCompra.cambiarBoolean2}" reRender="datosMInimos"   />


                            </h:selectOneRadio>
                            
                            </rich:panel>
                            
                            </h:panelGroup>
                            <h:panelGroup id="datosMInimos">
                            <rich:panel  rendered="#{ManagedIngresoAlmacenOrdenCompra.mostrar2}" headerClass="headerClassACliente">
                                  <f:facet name="header">
                                    <h:outputText value="cantidades"/>
                                </f:facet>
                                <h:panelGrid columns="4"   >
                                    <h:outputText value="Cant. Min. Muestreo" rendered="#{ManagedIngresoAlmacenOrdenCompra.mostrar2}" styleClass="outputText1" />
                                <h:inputText value="#{ManagedIngresoAlmacenOrdenCompra.materialEditar.cantidadMinimaMuestreo}" styleClass="outputText2" rendered="#{ManagedIngresoAlmacenOrdenCompra.mostrar2}" onkeypress="valNum(event);"/>
                                <h:outputText value="Cant. Max. Muestreo" rendered="#{ManagedIngresoAlmacenOrdenCompra.mostrar2}" styleClass="outputText1" />
                                <h:inputText value="#{ManagedIngresoAlmacenOrdenCompra.materialEditar.cantidadMaximaMuestreo}" styleClass="outputText2" rendered="#{ManagedIngresoAlmacenOrdenCompra.mostrar2}" onkeypress="valNum(event);"/>

                            </h:panelGrid>
                            </rich:panel>
                            </h:panelGroup>
                            </h:panelGrid>
                           
                            <a4j:commandButton value="Guardar" styleClass="btn"  action="#{ManagedIngresoAlmacenOrdenCompra.guardarCambiosAction}"
                            reRender="dataBuscarMaterial" oncomplete="Richfaces.hideModalPanel('panelEditarItem')"  />
                            <input  class="btn" type="button" value="Cancelar" onclick="Richfaces.hideModalPanel('panelEditarItem')" />
                            </center>

                            </h:panelGroup>


                        </a4j:form>
            </rich:modalPanel>

             <rich:modalPanel id="panelAgregarItem_inactivo" minHeight="400"  minWidth="800"
                                     height="400" width="800"
                                     zindex="200"
                                     headerClass="headerClassACliente"
                                     resizeable="false" style="overflow:auto"  onshow="#{ManagedIngresoAlmacenOrdenCompra.actionGenerarCambio}">
                        <f:facet name="header">
                            <h:outputText value="Materiales para editar"/>
                            <h:outputText value="#{ManagedIngresoAlmacenOrdenCompra.actionGenerarCambio}"/>
                            <%--f:facet name="controls">
                            <a4j:commandLink onclick="Richfaces.hideModalPanel('panelAgregarItem')" style="color: black" value="cerrar"></a4j:commandLink>
                            </f:facet--%>
                        </f:facet>



                        <a4j:form id="form2">
                            <center>
                        <h:panelGroup id="contenidoAgregarItem">



                            <rich:dataTable value="#{ManagedIngresoAlmacenOrdenCompra.listaMateriales}" var="data"
                                    id="dataBuscarMaterial"

                                    headerClass="headerClassACliente"
                                    >


                                        <rich:column>
                                    <f:facet name="header">
                                        <h:outputText value="  "  />
                                    </f:facet>
                                    <h:selectBooleanCheckbox value="#{data.checked}"/>

                                </rich:column>
                                <rich:column>
                                    <f:facet name="header">
                                        <h:outputText value="Material"  />
                                    </f:facet>
                                    <h:outputText value="#{data.nombreMaterial}"/>

                                </rich:column>

                                <rich:column>
                                    <f:facet name="header">
                                        <h:outputText value="Grupo"  />
                                    </f:facet>
                                    <h:outputText  value="#{data.grupos.nombreGrupo}"/>
                                </rich:column>

                                <rich:column>
                                    <f:facet name="header">
                                        <h:outputText value="Capitulo"  />
                                    </f:facet>
                                    <h:outputText value="#{data.grupos.capitulos.nombreCapitulo}"  />
                                </rich:column>


                            </rich:dataTable>
                            </h:panelGroup>

                            
                            <a4j:commandButton value="Editar" styleClass="btn"  action="#{ManagedIngresoAlmacenOrdenCompra.seleccionarMaterial_action}"
                            reRender="contenidoEditarItem" oncomplete="Richfaces.showModalPanel('panelEditarItem')"  />
                            <input  class="btn" type="button" value="Aceptar" onclick="Richfaces.hideModalPanel('panelAgregarItem')" />
                            </center>
                        </a4j:form>
            </rich:modalPanel>



        <a4j:include viewId="/panelProgreso.jsp"/>


    </body>
</html>

</f:view>

