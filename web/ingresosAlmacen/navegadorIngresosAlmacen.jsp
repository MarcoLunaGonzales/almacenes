<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib prefix="a4j" uri="http://richfaces.org/a4j"%>
<%@taglib prefix="rich" uri="http://richfaces.org/rich"%>
<f:view>
    <html>
        <head>
            <title>SISTEMA</title>
            <link rel="STYLESHEET" type="text/css" href="../css/ventas.css"/>
            <script type="text/javascript" src="../js/general.js"></script>
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
                       window.open(url,'detalle'+Math.round((Math.random()*1000)),'top=50,left=200,width=800,height=600,scrollbars=1,resizable=1');
                }

        onerror=function (){
        alert('existe un error al momento de cargar el registro, por favor recargue la pagina');
        }
//       A4J.AJAX.onError = function(req,status,message){
//            window.alert("Ocurrio un error: "+message);
//            }
//            A4J.AJAX.onExpired = function(loc,expiredMsg){
//            if(window.confirm("Ocurrio un error al momento realizar la transaccion: "+expiredMsg+" location: "+loc)){
//            return loc;
//            } else {
//            return false;
//            }
//            }
       </script>
        </head>
        <body >
            <a4j:form id="form1">
                <div align="center">
                    <h:outputText value="#{ManagedIngresoAlmacen.cargarIngresosAlmacen}"/>
                    
                    <h:outputText styleClass="outputTextTitulo"  value="GESTIONAR INGRESOS ALMACÉN" />
                    <br>
                        <%--inicio alejandro--%>
                        <a4j:commandLink onclick="Richfaces.showModalPanel('panelBuscarIngresoAlmacen')">
                            
                            <h:outputText value="Buscar" styleClass="outputText2" />
                            <h:graphicImage url="../img/buscar.png">

                            </h:graphicImage>
                        </a4j:commandLink>
                        <%--final alejandro--%>
                    <br>
                    <%--  rendered="" --%>
                    <rich:dataTable value="#{ManagedIngresoAlmacen.ingresosAlmacenList}"
                                    var="data"
                                    id="dataIngresosAlmacen"
                                    onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                    onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                    headerClass="headerClassACliente" >

                        <rich:column styleClass ="#{data.colorFila}">
                            <f:facet name="header">
                                <h:outputText value=""  />
                            </f:facet>
                            <h:selectBooleanCheckbox  value="#{data.checked}"  />
                        </rich:column>
                        <rich:column styleClass ="#{data.colorFila}">
                            <f:facet name="header">
                                <h:outputText value="Nro Ingreso Almacen"  />
                            </f:facet>
                            <h:outputText  value="#{data.nroIngresoAlmacen}"  />
                        </rich:column>
                     
                        <rich:column styleClass ="#{data.colorFila}">
                            <f:facet name="header">
                                <h:outputText value="Estado Ingreso Almacen"  />
                            </f:facet>
                            <h:outputText value="#{data.estadosIngresoAlmacen.nombreEstadoIngresoAlmacen}">
                            </h:outputText>
                            <h:outputText value="<br/>(Tiene Rechazados)" style="background-color:#BAF9B1" rendered="#{data.tieneItemsRechazados}" escape="false"/>
                        </rich:column>                 <rich:column styleClass ="#{data.colorFila}">
                            <f:facet name="header">
                                <h:outputText value="Fecha Ingreso Almacen"  />
                            </f:facet>
                            <h:outputText value="#{data.fechaIngresoAlmacen}"  >
                                <f:convertDateTime pattern="dd/MM/yyyy HH:mm" />
                            </h:outputText>
                        </rich:column>
                       <rich:column styleClass ="#{data.colorFila}">
                            <f:facet name="header">
                                <h:outputText value="Tipo Ingreso Almacen"  />
                            </f:facet>
                            <h:outputText value="#{data.tiposIngresoAlmacen.nombreTipoIngresoAlmacen}"  />
                        </rich:column>

                       <rich:column styleClass ="#{data.colorFila}">
                            <f:facet name="header">
                                <h:outputText value="Nro Orden Compra"  />
                            </f:facet>
                            <h:outputText value="#{data.ordenesCompra.nroOrdenCompra}"  />
                        </rich:column>

                        <rich:column styleClass ="#{data.colorFila}">
                            <f:facet name="header">
                                <h:outputText value="Proveedor"  />
                            </f:facet>
                            <h:outputText value="#{data.proveedores.nombreProveedor}"  />
                        </rich:column>

                       <rich:column styleClass ="#{data.colorFila}">
                            <f:facet name="header">
                                <h:outputText value="Pais Proveedor"  />
                            </f:facet>
                            <h:outputText value="#{data.proveedores.paises.nombrePais}"  />
                        </rich:column>

                       <rich:column styleClass ="#{data.colorFila}">
                            <f:facet name="header">
                                <h:outputText value="observaciones"  />
                            </f:facet>
                            <h:outputText value="#{data.obsIngresoAlmacen}"  />
                        </rich:column>

                        
                        <rich:column styleClass ="#{data.colorFila}">
                            <f:facet name="header">
                                <h:outputText value="Personal"  />
                            </f:facet>
                            <h:outputText value="#{data.personal.nombrePersonal}"  />
                            &nbsp;
                            <h:outputText value="#{data.personal.apPaternoPersonal}"  />
                            &nbsp;
                            <h:outputText value="#{data.personal.apMaternoPersonal}"  />
                        </rich:column>
                        <rich:column styleClass ="#{data.colorFila}">
                            <f:facet name="header">
                                <h:outputText value="Estado Ingreso Almacen"  />
                            </f:facet>
                            <h:outputText value="#{data.estadosIngresoAlmacen.nombreEstadoIngresoAlmacen}" />
                            <h:outputText value="<br/>(Tiene Rechazados)" style="background-color:#BAF9B1" rendered="#{data.tieneItemsRechazados}" escape="false"/>
                            <h:outputText value="<br/><span style='background-color:#9EFAC7' >(#{data.ingresoTransaccionSolicitud.estadoTransaccionSolicitud.nombreEstadoTransaccionSolicitud})</span>" escape="false" rendered="#{data.ingresoTransaccionSolicitud.estadoTransaccionSolicitud.nombreEstadoTransaccionSolicitud !=''}"  />
                        </rich:column>    
                            
                        <rich:column styleClass ="#{data.colorFila}">
                            <f:facet name="header">
                                <h:outputText value="Acciones"  />
                            </f:facet>


                            <rich:dropDownMenu rendered="#{data.estadosIngresoAlmacen.codEstadoIngresoAlmacen != 2}" >
                                <f:facet name="label">
                                    <h:outputText value="Acciones"/>
                                </f:facet>
                                
                                <rich:menuItem submitMode="ajax" value="Editar"  rendered="#{ManagedIngresoAlmacen.administradorAlmacen && (ManagedIngresoAlmacen.permisoEditarDirectamente || (data.fechaCreacionHoy && ManagedIngresoAlmacen.permisoEditarHoy)) }"
                                               >
                                    <a4j:support event="onclick" 
                                                 action="#{ManagedIngresoAlmacen.nuevoIngresoTransaccionSolicitudEditar_action}" 
                                                 oncomplete="if(#{ManagedIngresoAlmacen.ingresoEditable}){location='editarIngresosAlmacen.jsf';}else{alert('#{ManagedIngresoAlmacen.mensaje}');} " 
                                                  >
                                        <f:setPropertyActionListener value="#{data}" target="#{ManagedIngresoAlmacen.ingresosAlmacen}"/>
                                    </a4j:support>  
                                </rich:menuItem>
                                
                                <rich:menuItem submitMode="ajax" value="Anular"  rendered="#{ManagedIngresoAlmacen.administradorAlmacen && (ManagedIngresoAlmacen.permisoAnularDirectamente || (data.fechaCreacionHoy && ManagedIngresoAlmacen.permisoAnularHoy)) }"
                                               >
                                    <a4j:support event="onclick" 
                                                 action="#{ManagedIngresoAlmacen.nuevoIngresoTransaccionSolicitudAnular_action}"
                                                 oncomplete="if(#{ManagedIngresoAlmacen.ingresoEditable}){Richfaces.showModalPanel('panelAprobarAnular');}else{alert('#{ManagedIngresoAlmacen.mensaje}');}" 
                                                 reRender="contenidoAprobarAnularIngreso" 
                                                 >
                                        <f:setPropertyActionListener value="#{data}" target="#{ManagedIngresoAlmacen.ingresosAlmacen}"/>
                                    </a4j:support>  
                                </rich:menuItem>
                                
                                <rich:menuItem submitMode="ajax" value="Solicitar Edición" 
                                               rendered="#{((data.ingresoTransaccionSolicitud.estadoTransaccionSolicitud.codEstadoTransaccionSolicitud eq 0 
                                                                    || data.ingresoTransaccionSolicitud.estadoTransaccionSolicitud.codEstadoTransaccionSolicitud eq 5 
                                                                    || data.ingresoTransaccionSolicitud.estadoTransaccionSolicitud.codEstadoTransaccionSolicitud eq 6) 
                                                                && ManagedIngresoAlmacen.permisoSolicitarEditar) && 
                                                                ! (data.fechaCreacionHoy && ManagedIngresoAlmacen.permisoEditarHoy)}" 
                                               >
                                    <a4j:support event="onclick" action="#{ManagedIngresoAlmacen.nuevoIngresoTransaccionSolicitudEditar_action}" reRender="contenidoTransaccionSolicitudEditar"
                                                 oncomplete="if(#{ManagedIngresoAlmacen.ingresoEditable}){Richfaces.showModalPanel('panelTransaccionSolicitudEditar');}else{alert('#{ManagedIngresoAlmacen.mensaje}');}"  >
                                        <f:setPropertyActionListener value="#{data}" target="#{ManagedIngresoAlmacen.ingresosAlmacen}"/>
                                    </a4j:support>
                                </rich:menuItem>

                                <rich:menuItem submitMode="ajax" value="Solicitar Anular" rendered="#{(data.ingresoTransaccionSolicitud.estadoTransaccionSolicitud.codEstadoTransaccionSolicitud eq 0 || data.ingresoTransaccionSolicitud.estadoTransaccionSolicitud.codEstadoTransaccionSolicitud eq 5 || data.ingresoTransaccionSolicitud.estadoTransaccionSolicitud.codEstadoTransaccionSolicitud eq 6) && ManagedIngresoAlmacen.permisoSolicitarAnular }" 
                                               >
                                    <a4j:support event="onclick" action="#{ManagedIngresoAlmacen.nuevoIngresoTransaccionSolicitudAnular_action}" reRender="contenidoTransaccionSolicitudAnular"
                                                 oncomplete="if(#{ManagedIngresoAlmacen.ingresoEditable}){Richfaces.showModalPanel('panelTransaccionSolicitudAnular');}else{alert('#{ManagedIngresoAlmacen.mensaje}');}"  >
                                        <f:setPropertyActionListener value="#{data}" target="#{ManagedIngresoAlmacen.ingresosAlmacen}"/>
                                    </a4j:support>
                                </rich:menuItem>
                                
                                <rich:menuItem submitMode="ajax" value="Generar Salida de Rechazados"  rendered="#{data.tieneItemsRechazados && ManagedIngresoAlmacen.administradorAlmacen}"
                                               >
                                    <a4j:support event="onclick" 
                                                 action="#{ManagedIngresoAlmacen.nuevaSalidaRechazados_action}" 
                                                 oncomplete="location='../salidasAlmacen/agregarSalidaAlmacenRechazados.jsf';" 
                                                  >
                                        <f:setPropertyActionListener value="#{data}" target="#{ManagedIngresoAlmacen.ingresosAlmacen}"/>
                                    </a4j:support>  
                                </rich:menuItem>

                                <rich:menuItem submitMode="ajax" value="Reporte Logs"  >
                                    <a4j:support event="onclick" 
                                                 action="#{ManagedIngresoAlmacen.verReporteIngresoAlmacenLog_action}" 
                                                 oncomplete="openPopup('impresionIngresoAlmacenLog.jsf');" >
                                        <f:setPropertyActionListener value="#{data}" target="#{ManagedIngresoAlmacen.ingresosAlmacen}"/>
                                    </a4j:support>  
                                </rich:menuItem>

                            </rich:dropDownMenu>  
                        </rich:column>                       
                    </rich:dataTable>
                    


                     
                    <h:panelGrid columns="2"  width="50" id="controles">
                        <a4j:commandLink  action="#{ManagedIngresoAlmacen.atras_action}" reRender="dataIngresosAlmacen,controles" timeout="7200"  rendered="#{ManagedIngresoAlmacen.begin!='1'}" >
                            <h:graphicImage url="../img/previous.gif"  style="border:0px solid red"   alt="PAGINA ANTERIOR"  />
                        </a4j:commandLink>
                        <a4j:commandLink  action="#{ManagedIngresoAlmacen.siguiente_action}" reRender="dataIngresosAlmacen,controles" timeout="7200"  rendered="#{ManagedIngresoAlmacen.cantidadfilas>='10'}">
                            <h:graphicImage url="../img/next.gif"  style="border:0px solid red"  alt="PAGINA SIGUIENTE" />
                        </a4j:commandLink>
                    </h:panelGrid>
                    
                    <br>
                    <h:panelGroup rendered="#{ManagedIngresoAlmacen.administradorAlmacen}">
                        <a4j:commandButton value="Generar Ingreso Almacen" styleClass="btn"  
                                           oncomplete="redireccionar('agregarIngresosAlmacen.jsf')" />
                        
                        <a4j:commandButton value="Editar Ubicacion" styleClass = "btn"
                                           action="#{ManagedIngresoAlmacen.editarUbicacionIngresosAlmacen_action}" oncomplete="location='editarUbicacionIngresosAlmacen.jsf'" 
                                           rendered="#{ManagedAccesoSistema.almacenesGlobal.aplicaGestionUbicaciones}"/>
                        <a4j:commandButton value="Imprimir Etiquetas Pesaje" styleClass = "btn" 
                                           action="#{ManagedIngresoAlmacen.verReporteIngresoAlmacen_action}" 
                                           oncomplete="openPopup('reporteEtiquetasPesaje.jsf');" />
                        <a4j:commandButton value="Detalle Pesaje" styleClass = "btn" 
                                           action="#{ManagedIngresoAlmacen.verReporteIngresoAlmacen_action}" 
                                           oncomplete="openPopup('reporteDetallePesaje.jsf');" />

                    </h:panelGroup> 
                    <a4j:commandButton value="Imprimir" styleClass = "btn" action="#{ManagedIngresoAlmacen.verReporteIngresoAlmacen_action}" oncomplete="openPopup('reporteIngresosAlmacenJasper.jsf');" />
                    <a4j:commandButton value="Imprimir Etiquetas" styleClass = "btn" action="#{ManagedIngresoAlmacen.verReporteIngresoAlmacen_action}" oncomplete="openPopup('reporteEtiquetas.jsf');" />
                                                

                    <%--
                    <a4j:commandButton value="Agregar" styleClass="btn" onclick="javascript:Richfaces.showModalPanel('panelAgregarFrecuenciaMantenimientoMaquinaria')" action="#{ManagedFrecuenciaMantenimientoMaquinaria.agregarFrecuencia_action}"
                    reRender="contenidoFrecuenciaMantenimentoMaquinaria" />
                    <a4j:commandButton value="Eliminar"  styleClass="btn" onclick="javascript:if(confirm('Esta Seguro de Eliminar??')==false || validarSeleccion('form1:dataFrecuenciaMantenimientoMaquina')==false){return false;}"  action="#{ManagedFrecuenciaMantenimientoMaquinaria.eliminarFrecuenciaMantenimientoMaquina_action}"
                    reRender="dataFrecuenciaMantenimientoMaquina"/>
                    <a4j:commandButton value="Editar"  styleClass="btn" onclick="javascript:if(validarSeleccion('form1:dataFrecuenciaMantenimientoMaquina')==false){return false;}else{Richfaces.showModalPanel('panelEditarFrecuenciaMantenimientoMaquinaria')}"  action="#{ManagedFrecuenciaMantenimientoMaquinaria.editarFrecuenciaMantenimientoMaquina_action}"
                    reRender="contenidoEditarFrecuenciaMantenimentoMaquinaria" />
                    --%>
                </div>

               
                <!--cerrando la conexion-->
                <h:outputText value="#{ManagedFrecuenciaMantenimientoMaquinaria.closeConnection}"  />

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
                                
                                <h:outputText value="Tipo de Periodo" styleClass="outputText2" />
                                <h:outputText value="::" styleClass="outputText2" />
                                <h:selectOneMenu value="#{ManagedFrecuenciaMantenimientoMaquinaria.frecuenciasMantenimientoMaquina.tiposPeriodo.codTipoPeriodo}" styleClass="inputText" >
                                    <f:selectItems value="#{ManagedFrecuenciaMantenimientoMaquinaria.tiposPeriodoList}"  />
                                </h:selectOneMenu>

                                
                                <h:outputText value="Frecuencia" styleClass="outputText2" />
                                <h:outputText value="::" styleClass="outputText2" />
                                <h:inputText value="#{ManagedFrecuenciaMantenimientoMaquinaria.frecuenciasMantenimientoMaquina.horasFrecuencia}" styleClass="inputText" size="5" onkeypress="valNum(event);" />

                            </h:panelGrid>
                                <br>
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
                                
                                <h:outputText value="Tipo de Periodo" styleClass="outputText2" />
                                <h:outputText value="::" styleClass="outputText2" />
                                <h:selectOneMenu value="#{ManagedFrecuenciaMantenimientoMaquinaria.frecuenciasMantenimientoMaquina.tiposPeriodo.codTipoPeriodo}" styleClass="inputText" >
                                    <f:selectItems value="#{ManagedFrecuenciaMantenimientoMaquinaria.tiposPeriodoList}"  />
                                </h:selectOneMenu>

                                
                                <h:outputText value="Frecuencia" styleClass="outputText2" />
                                <h:outputText value="::" styleClass="outputText2" />
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


            <rich:modalPanel id="panelTransaccionSolicitudEditar"  minHeight="250"  minWidth="750"
                             height="250" width="750"
                             zindex="200" 
                             headerClass="headerClassACliente"
                             resizeable="false" style="overflow :auto" >
                <f:facet name="header">
                    <h:outputText value="<center>SOLICITUD PARA EDITAR EL REGISTRO DE INGRESO</center>" escape="false"/>
                </f:facet>
                <a4j:form>
                    
                    <h:panelGroup id="contenidoTransaccionSolicitudEditar">     
                        <div align="center">
                            <h:panelGrid columns="4" >
                                <h:outputText value="Nro. Ingreso:" styleClass="outputText1Subtitle" />
                                <h:outputText value="#{ManagedIngresoAlmacen.ingresosAlmacen.nroIngresoAlmacen}" styleClass="outputText2" style="width:50px" />
                                <h:outputText value="Fecha Ingreso:" styleClass="outputText1Subtitle" />
                                <h:outputText value="#{ManagedIngresoAlmacen.ingresosAlmacen.fechaIngresoAlmacen}" styleClass="outputText2">
                                    <f:convertDateTime pattern="dd/MM/yyyy HH:mm" />
                                </h:outputText>

                                <h:outputText value="Tipo Ingreso:" styleClass="outputText1Subtitle" />
                                <h:outputText value="#{ManagedIngresoAlmacen.ingresosAlmacen.tiposIngresoAlmacen.nombreTipoIngresoAlmacen}" styleClass="outputText2" />
                                <h:outputText value="Nro. Orden Compra:" styleClass="outputText1Subtitle" />
                                <h:outputText value="#{ManagedIngresoAlmacen.ingresosAlmacen.ordenesCompra.nroOrdenCompra}" styleClass="outputText2" />

                                <h:outputText value="Proveedor:" styleClass="outputText1Subtitle" />
                                <h:outputText value="#{ManagedIngresoAlmacen.ingresosAlmacen.proveedores.nombreProveedor}" styleClass="outputText2" />
                                <h:outputText value="Pais Proveedor:" styleClass="outputText1Subtitle" />
                                <h:outputText value="#{ManagedIngresoAlmacen.ingresosAlmacen.proveedores.paises.nombrePais}" styleClass="outputText2" />
                            </h:panelGrid> 

                            <h:panelGroup rendered="#{ManagedIngresoAlmacen.salidasAsociadasAlmacenListSize ne 0}"> 
                                <br/>
                                <h:outputText value="No se puede Editar el Ingreso si tiene Salidas con Devoluciones: " styleClass="outputTextWarn" rendered="#{ManagedIngresoAlmacen.tipoSalidaAutomatico && ManagedIngresoAlmacen.salidaTieneDevolucion}"/>
                                <h:outputText value="No se puede Editar el Ingreso si tiene Salidas asociadas, debe Anular primeramente las Salidas: " styleClass="outputTextWarn" rendered="#{!ManagedIngresoAlmacen.tipoSalidaAutomatico}"/>
                                <h:outputText value="Salidas automáticas del Ingreso que serán anuladas: " styleClass="outputText2" rendered="#{ManagedIngresoAlmacen.tipoSalidaAutomatico && !ManagedIngresoAlmacen.salidaTieneDevolucion}"/>
                                <rich:dataTable value="#{ManagedIngresoAlmacen.salidasAsociadasAlmacenList}"
                                            var="data"
                                            id="dataSalidasAsociadasAlmacenSE"
                                            onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                            onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                            headerClass="headerClassACliente" >

                                    <rich:column styleClass ="#{data.colorFila}">
                                        <f:facet name="header">
                                            <h:outputText value="Nro Salida Almacen"  />
                                        </f:facet>
                                        <h:outputText  value="#{data.nroSalidaAlmacen}"  />
                                    </rich:column>

                                    <rich:column styleClass ="#{data.colorFila}">
                                        <f:facet name="header">
                                            <h:outputText value="Fecha Salida Almacen"  />
                                        </f:facet>
                                        <h:outputText value="#{data.fechaSalidaAlmacen}"  >
                                            <f:convertDateTime pattern="dd/MM/yyyy HH:mm" />
                                        </h:outputText>
                                    </rich:column>

                                    <rich:column styleClass ="#{data.colorFila}">
                                        <f:facet name="header">
                                            <h:outputText value="Area Empresa"  />
                                        </f:facet>
                                        <h:outputText value="#{data.areasEmpresa.nombreAreaEmpresa}"  />
                                    </rich:column>

                                    <rich:column styleClass ="#{data.colorFila}">
                                        <f:facet name="header">
                                            <h:outputText value="Tipo Salida"  />
                                        </f:facet>
                                        <h:outputText value="#{data.tiposSalidasAlmacen.nombreTipoSalidaAlmacen}"  />
                                    </rich:column>

                                    <rich:column styleClass ="#{data.colorFila}">
                                        <f:facet name="header">
                                            <h:outputText value="Personal"  />
                                        </f:facet>
                                        <h:outputText value="#{data.personal.nombrePersonal}"  />
                                    </rich:column>

                                   <rich:column styleClass ="#{data.colorFila}">
                                        <f:facet name="header">
                                            <h:outputText value="Detalle"  />
                                        </f:facet>
                                        <a4j:commandLink action="#{ManagedIngresoAlmacen.verReporteSalidaAlmacen_action}" 
                                                         oncomplete="openPopup('../salidasAlmacen/reporteSalidaAlmacenJasper.jsf?reporte=../salidasAlmacen/reporteSalida.jasper');"
                                                         >
                                            <h:graphicImage url="../img/report.png" />
                                            <f:setPropertyActionListener value="#{data}" target="#{ManagedIngresoAlmacen.salidaAsociadaGestionar}"/>
                                        </a4j:commandLink>
                                    </rich:column>

                                </rich:dataTable>

                            </h:panelGroup>
                            <br/>
                            <h:panelGroup rendered="#{ManagedIngresoAlmacen.tipoSalidaAutomatico && !ManagedIngresoAlmacen.salidaTieneDevolucion}"
                                          style="width:100%">
                                <h:outputText value="Motivo/Observación:" styleClass="outputText1Subtitle" />
                                <h:inputTextarea value="#{ManagedIngresoAlmacen.ingresoTransaccionSolicitudGestionar.observacionSolicitante}" styleClass="inputText" 
                                                 style="width:100%" rows="3" />
                            </h:panelGroup>
                            <br/>
                        
                            <a4j:commandButton styleClass="btn" value="#{ManagedIngresoAlmacen.salidasAsociadasAlmacenListSize ne 0?'Solicitar Editar Ingreso y Anular Salidas Automaticas':'Registrar Solicitud'}"
                                               oncomplete="mostrarMensajeTransaccionEvento(function(){Richfaces.hideModalPanel('panelTransaccionSolicitudEditar')});"
                                               reRender="dataIngresosAlmacen"
                                               rendered="#{ManagedIngresoAlmacen.tipoSalidaAutomatico && !ManagedIngresoAlmacen.salidaTieneDevolucion}"
                                               action="#{ManagedIngresoAlmacen.guardarSolicitudEditarIngresoAlmacen_action}" >
                            </a4j:commandButton>
                            <input type="button" value="Cancelar" onclick="javascript:Richfaces.hideModalPanel('panelTransaccionSolicitudEditar')" class="btn" />
                        </div>
                    </h:panelGroup>
                        
                </a4j:form>
            </rich:modalPanel>    
                        
                              
            <rich:modalPanel id="panelTransaccionSolicitudAnular"  minHeight="250"  minWidth="750"
                             height="250" width="750"
                             zindex="200"
                             headerClass="headerClassACliente"
                             resizeable="false" style="overflow :auto" >
                <f:facet name="header">
                    <h:outputText value="<center>SOLICITUD PARA ANULAR EL REGISTRO DE INGRESO</center>" escape="false"/>
                </f:facet>
                <a4j:form>
                    <h:panelGroup id="contenidoTransaccionSolicitudAnular">
                        <div align="center">
                            <h:panelGrid columns="4">

                                <h:outputText value="Nro. Ingreso:" styleClass="outputText1Subtitle" />
                                <h:outputText value="#{ManagedIngresoAlmacen.ingresosAlmacen.nroIngresoAlmacen}" styleClass="outputText2" style="width:50px" />
                                <h:outputText value="Fecha Ingreso:" styleClass="outputText1Subtitle" />
                                <h:outputText value="#{ManagedIngresoAlmacen.ingresosAlmacen.fechaIngresoAlmacen}" styleClass="outputText2">
                                    <f:convertDateTime pattern="dd/MM/yyyy HH:mm" />
                                </h:outputText>

                                <h:outputText value="Tipo Ingreso:" styleClass="outputText1Subtitle" />
                                <h:outputText value="#{ManagedIngresoAlmacen.ingresosAlmacen.tiposIngresoAlmacen.nombreTipoIngresoAlmacen}" styleClass="outputText2" />
                                <h:outputText value="Nro. Orden Compra:" styleClass="outputText1Subtitle" />
                                <h:outputText value="#{ManagedIngresoAlmacen.ingresosAlmacen.ordenesCompra.nroOrdenCompra}" styleClass="outputText2" />

                                <h:outputText value="Proveedor:" styleClass="outputText1Subtitle" />
                                <h:outputText value="#{ManagedIngresoAlmacen.ingresosAlmacen.proveedores.nombreProveedor}" styleClass="outputText2" />
                                <h:outputText value="Pais Proveedor:" styleClass="outputText1Subtitle" />
                                <h:outputText value="#{ManagedIngresoAlmacen.ingresosAlmacen.proveedores.paises.nombrePais}" styleClass="outputText2" />
                            </h:panelGrid> 

                            <h:panelGroup rendered="#{ManagedIngresoAlmacen.salidasAsociadasAlmacenListSize ne 0}">
                                <br/>
                                <h:outputText value="No se puede Anular el Ingreso si tiene Salidas con Devoluciones: " styleClass="outputTextWarn" rendered="#{ManagedIngresoAlmacen.tipoSalidaAutomatico && ManagedIngresoAlmacen.salidaTieneDevolucion}"/>
                                <h:outputText value="No se puede Anular el Ingreso si tiene Salidas asociadas, debe Anular primeramente las Salidas: " styleClass="outputTextWarn" rendered="#{!ManagedIngresoAlmacen.tipoSalidaAutomatico}"/>
                                <h:outputText value="Salidas automáticas del Ingreso que serán anuladas: " styleClass="outputText2" rendered="#{ManagedIngresoAlmacen.tipoSalidaAutomatico && !ManagedIngresoAlmacen.salidaTieneDevolucion}"/>
                                <rich:dataTable value="#{ManagedIngresoAlmacen.salidasAsociadasAlmacenList}"
                                            var="data"
                                            id="dataSalidasAsociadasAlmacenSA"
                                            onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                            onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                            headerClass="headerClassACliente" >

                                    <rich:column styleClass ="#{data.colorFila}">
                                        <f:facet name="header">
                                            <h:outputText value="Nro Salida Almacen"  />
                                        </f:facet>
                                        <h:outputText  value="#{data.nroSalidaAlmacen}"  />
                                    </rich:column>

                                    <rich:column styleClass ="#{data.colorFila}">
                                        <f:facet name="header">
                                            <h:outputText value="Fecha Salida Almacen"  />
                                        </f:facet>
                                        <h:outputText value="#{data.fechaSalidaAlmacen}"  >
                                            <f:convertDateTime pattern="dd/MM/yyyy HH:mm" />
                                        </h:outputText>
                                    </rich:column>

                                    <rich:column styleClass ="#{data.colorFila}">
                                        <f:facet name="header">
                                            <h:outputText value="Area Empresa"  />
                                        </f:facet>
                                        <h:outputText value="#{data.areasEmpresa.nombreAreaEmpresa}"  />
                                    </rich:column>

                                    <rich:column styleClass ="#{data.colorFila}">
                                        <f:facet name="header">
                                            <h:outputText value="Tipo Salida"  />
                                        </f:facet>
                                        <h:outputText value="#{data.tiposSalidasAlmacen.nombreTipoSalidaAlmacen}"  />
                                    </rich:column>

                                    <rich:column styleClass ="#{data.colorFila}">
                                        <f:facet name="header">
                                            <h:outputText value="Personal"  />
                                        </f:facet>
                                        <h:outputText value="#{data.personal.nombrePersonal}"  />
                                    </rich:column>

                                   <rich:column styleClass ="#{data.colorFila}">
                                        <f:facet name="header">
                                            <h:outputText value="Detalle"  />
                                        </f:facet>
                                        <a4j:commandLink action="#{ManagedIngresoAlmacen.verReporteSalidaAlmacen_action}" 
                                                         oncomplete="openPopup('../salidasAlmacen/reporteSalidaAlmacenJasper.jsf?reporte=../salidasAlmacen/reporteSalida.jasper');"
                                                         >
                                            <h:graphicImage url="../img/report.png" />
                                            <f:setPropertyActionListener value="#{data}" target="#{ManagedIngresoAlmacen.salidaAsociadaGestionar}"/>
                                        </a4j:commandLink>
                                    </rich:column>

                                </rich:dataTable>

                            </h:panelGroup>
                            <br/>
                            <h:panelGroup rendered="#{ManagedIngresoAlmacen.tipoSalidaAutomatico && !ManagedIngresoAlmacen.salidaTieneDevolucion}" style="width:100%">
                                <h:outputText value="Motivo/Observación:" styleClass="outputText1Subtitle" />
                                <h:inputTextarea value="#{ManagedIngresoAlmacen.ingresoTransaccionSolicitudGestionar.observacionSolicitante}" 
                                                 styleClass="inputText" style="width:100%" rows="4" />
                            </h:panelGroup>
                            <br/>
                            <a4j:commandButton styleClass="btn" value="#{ManagedIngresoAlmacen.salidasAsociadasAlmacenListSize ne 0?'Solicitar Anular Ingreso y Anular Salidas Automaticas':'Registrar Solicitud'}"
                                               oncomplete="if(#{ManagedIngresoAlmacen.registradoCorrectamente}){javascript:Richfaces.hideModalPanel('panelTransaccionSolicitudAnular'); alert('#{ManagedIngresoAlmacen.mensaje}');}else{alert('#{ManagedIngresoAlmacen.mensaje}');} "
                                               reRender="dataIngresosAlmacen"
                                               rendered="#{ManagedIngresoAlmacen.tipoSalidaAutomatico && !ManagedIngresoAlmacen.salidaTieneDevolucion}"
                                               action="#{ManagedIngresoAlmacen.guardarSolicitudAnularIngresoAlmacen_action}" >
                            </a4j:commandButton>
                            <input type="button" value="Cancelar" onclick="javascript:Richfaces.hideModalPanel('panelTransaccionSolicitudAnular')" class="btn" />
                        </div>
                    </h:panelGroup>
                </a4j:form>
            </rich:modalPanel>    
                        
                        
            <rich:modalPanel id="panelAprobarAnular"  minHeight="250"  minWidth="750"
                             height="250" width="750"
                             zindex="200"
                             headerClass="headerClassACliente"
                             resizeable="false" style="overflow :auto" >
                <f:facet name="header">
                    <h:outputText id="txtTituloPanelAprobar" value="<center>ANULAR INGRESO</center>" escape="false"/>
                </f:facet>
                <a4j:form>
                    <h:panelGroup id="contenidoAprobarAnularIngreso">
                        <div align="center">
                            <h:panelGrid columns="4">
                                <h:outputText value="Nro. Ingreso:" styleClass="outputText1Subtitle" />
                                <h:outputText value="#{ManagedIngresoAlmacen.ingresosAlmacen.nroIngresoAlmacen}" styleClass="outputText2" style="width:50px" />
                                <h:outputText value="Fecha Ingreso:" styleClass="outputText1Subtitle" />
                                <h:outputText value="#{ManagedIngresoAlmacen.ingresosAlmacen.fechaIngresoAlmacen}" styleClass="outputText2">
                                    <f:convertDateTime pattern="dd/MM/yyyy HH:mm" />
                                </h:outputText>

                                <h:outputText value="Tipo Ingreso:" styleClass="outputText1Subtitle" />
                                <h:outputText value="#{ManagedIngresoAlmacen.ingresosAlmacen.tiposIngresoAlmacen.nombreTipoIngresoAlmacen}" styleClass="outputText2" />
                                <h:outputText value="Nro. Orden Compra:" styleClass="outputText1Subtitle" />
                                <h:outputText value="#{ManagedIngresoAlmacen.ingresosAlmacen.ordenesCompra.nroOrdenCompra}" styleClass="outputText2" />

                                <h:outputText value="Proveedor:" styleClass="outputText1Subtitle" />
                                <h:outputText value="#{ManagedIngresoAlmacen.ingresosAlmacen.proveedores.nombreProveedor}" styleClass="outputText2" />
                                <h:outputText value="Pais Proveedor:" styleClass="outputText1Subtitle" />
                                <h:outputText value="#{ManagedIngresoAlmacen.ingresosAlmacen.proveedores.paises.nombrePais}" styleClass="outputText2" />
                            </h:panelGrid> 

                            <h:panelGroup rendered="#{ManagedIngresoAlmacen.salidasAsociadasAlmacenListSize ne 0}">
                                <br/>
                                <h:outputText value="No se puede Anular el Ingreso si tiene Salidas con Devoluciones: " styleClass="outputTextWarn" rendered="#{ManagedIngresoAlmacen.tipoSalidaAutomatico && ManagedIngresoAlmacen.salidaTieneDevolucion}"/>
                                <h:outputText value="No se puede Anular el Ingreso si tiene Salidas asociadas, debe Anular primeramente las Salidas: " styleClass="outputTextWarn" rendered="#{!ManagedIngresoAlmacen.tipoSalidaAutomatico}"/>
                                <h:outputText value="Salidas automáticas del Ingreso que serán anuladas: " styleClass="outputText2" rendered="#{ManagedIngresoAlmacen.tipoSalidaAutomatico && !ManagedIngresoAlmacen.salidaTieneDevolucion}"/>
                                <rich:dataTable value="#{ManagedIngresoAlmacen.salidasAsociadasAlmacenList}"
                                            var="data"
                                            id="dataSalidasAsociadasAlmacenA"
                                            onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                            onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                            headerClass="headerClassACliente" >

                                    <rich:column styleClass ="#{data.colorFila}">
                                        <f:facet name="header">
                                            <h:outputText value="Nro Salida Almacen"  />
                                        </f:facet>
                                        <h:outputText  value="#{data.nroSalidaAlmacen}"  />
                                    </rich:column>

                                    <rich:column styleClass ="#{data.colorFila}">
                                        <f:facet name="header">
                                            <h:outputText value="Fecha Salida Almacen"  />
                                        </f:facet>
                                        <h:outputText value="#{data.fechaSalidaAlmacen}"  >
                                            <f:convertDateTime pattern="dd/MM/yyyy HH:mm" />
                                        </h:outputText>
                                    </rich:column>

                                    <rich:column styleClass ="#{data.colorFila}">
                                        <f:facet name="header">
                                            <h:outputText value="Area Empresa"  />
                                        </f:facet>
                                        <h:outputText value="#{data.areasEmpresa.nombreAreaEmpresa}"  />
                                    </rich:column>

                                    <rich:column styleClass ="#{data.colorFila}">
                                        <f:facet name="header">
                                            <h:outputText value="Tipo Salida"  />
                                        </f:facet>
                                        <h:outputText value="#{data.tiposSalidasAlmacen.nombreTipoSalidaAlmacen}"  />
                                    </rich:column>

                                    <rich:column styleClass ="#{data.colorFila}">
                                        <f:facet name="header">
                                            <h:outputText value="Personal"  />
                                        </f:facet>
                                        <h:outputText value="#{data.personal.nombrePersonal}"  />
                                    </rich:column>

                                   <rich:column styleClass ="#{data.colorFila}">
                                        <f:facet name="header">
                                            <h:outputText value="Detalle"  />
                                        </f:facet>
                                        <a4j:commandLink action="#{ManagedIngresoAlmacen.verReporteSalidaAlmacen_action}" 
                                                         oncomplete="openPopup('../salidasAlmacen/reporteSalidaAlmacenJasper.jsf?reporte=../salidasAlmacen/reporteSalida.jasper');"
                                                         >
                                            <h:graphicImage url="../img/report.png" />
                                            <f:setPropertyActionListener value="#{data}" target="#{ManagedIngresoAlmacen.salidaAsociadaGestionar}"/>
                                        </a4j:commandLink>
                                    </rich:column>

                                </rich:dataTable>                            
                            </h:panelGroup>
                            <br/>
                            <h:panelGroup rendered="#{ManagedIngresoAlmacen.ingresoEditableValidandoConSalidas}" style="width:100%">
                                <h:outputText value="Motivo/Observación:" styleClass="outputText1Subtitle" />
                                <h:inputTextarea value="#{ManagedIngresoAlmacen.ingresoTransaccionSolicitudGestionar.observacionValidador}" 
                                                 styleClass="inputText" style="width:100%" rows="2" />
                            </h:panelGroup>
                            <br/>
                            <a4j:commandButton styleClass="btn" value="#{ManagedIngresoAlmacen.salidasAsociadasAlmacenListSize ne 0?'Anular Ingreso y Anular Salidas Automaticas':'Registrar Anulación'}"
                                               oncomplete="mostrarMensajeTransaccionEvento(function(){Richfaces.hideModalPanel('panelAprobarAnular');});"
                                               reRender="dataIngresosAlmacen"
                                               rendered="#{ManagedIngresoAlmacen.ingresoEditableValidandoConSalidas}"
                                               action="#{ManagedIngresoAlmacen.anularDirectamenteIngresoAlmacenPorSP_action}" >
                            </a4j:commandButton>
                            <input type="button" value="Cancelar" onclick="javascript:Richfaces.hideModalPanel('panelAprobarAnular')" class="btn" />
                        </div>
                            
                    </h:panelGroup>
                </a4j:form>
            </rich:modalPanel>              
                        
            <%--inicio alejandro--%>
            <rich:modalPanel id="panelBuscarIngresoAlmacen"  minHeight="300"  minWidth="1000"
                                     height="300" width="1000"
                                     zindex="200"
                                     headerClass="headerClassACliente"
                                     resizeable="false" style="overflow :auto" >
                        <f:facet name="header">
                            <h:outputText value="Buscar Ingreso Almacen"/>
                        </f:facet>
                        <a4j:form>
                        <h:panelGroup id="contenidoBuscarIngresoAlmacen">
                            <h:panelGrid columns="4">

                               
                                <h:outputText value="Nro de orden compra:" styleClass="outputText2" />
                                   <h:inputText value="#{ManagedIngresoAlmacen.nroOrdenCompra}" styleClass="inputText" size="5" onkeypress="valNum(event);" />

                                <h:outputText value="Nro Ingreso:" styleClass="outputText2" />
                                <h:inputText value="#{ManagedIngresoAlmacen.nroIngresoAlmacen}" styleClass="inputText" size="5" onkeypress="valNum(event);" />

                                <h:outputText value="Gestion:" styleClass="outputText2" />
                                <h:selectOneMenu value="#{ManagedIngresoAlmacen.codGestion}" styleClass="inputText" >
                                    <f:selectItems value="#{ManagedIngresoAlmacen.gestionesList}"  />
                                </h:selectOneMenu>
                                <h:outputText value="Estado Ingreso:" styleClass="outputText2" />
                                 <h:selectOneMenu value="#{ManagedIngresoAlmacen.codEstadoIngreso}" styleClass="inputText" >
                                     <f:selectItems value="#{ManagedIngresoAlmacen.estadosIngresoAlmacenList}"  />
                                </h:selectOneMenu>

                                <h:outputText value="Tipo Ingreso:" styleClass="outputText2" />
                                <h:selectOneMenu value="#{ManagedIngresoAlmacen.codTipoIngreso}" styleClass="inputText" >
                                    <f:selectItems value="#{ManagedIngresoAlmacen.tiposIngresoAlmacenList2}"  />
                                </h:selectOneMenu>
                                
                               
                               <h:outputText value="Proveedores:" styleClass="outputText2"/>
                                <h:selectOneMenu value="#{ManagedIngresoAlmacen.codProveedor}" styleClass="inputText" >
                                    <f:selectItems value="#{ManagedIngresoAlmacen.proveedoresList2}"  />
                                </h:selectOneMenu>
                   
                                

                                   
                                 
                                <h:outputText value="Fecha Inicio :" styleClass="outputText2" />
                                <rich:calendar  datePattern="dd/MM/yyyy" styleClass="inputText" zindex="200" value="#{ManagedIngresoAlmacen.fechaInicio}" id="fechaInicio" enableManualInput="true" oninputblur="if(this.value!=''){valFecha(this)}else{return true;}" />

                                <h:outputText value="Fecha Final :" styleClass="outputText2" />
                                <rich:calendar  datePattern="dd/MM/yyyy" styleClass="inputText" zindex="200" value="#{ManagedIngresoAlmacen.fechaFinal}" id="fechaFinal" enableManualInput="true" oninputblur="if(this.value!=''){valFecha(this)}else{return true;}" />

                                <h:outputText value="Tipos de compra:" styleClass="outputText2" />
                                <h:selectOneMenu value="#{ManagedIngresoAlmacen.codTipoCompra}" styleClass="inputText" >
                                    <f:selectItems value="#{ManagedIngresoAlmacen.tiposCompraList2}"  />
                                </h:selectOneMenu>

                                 <h:outputText value="Capitulo : " styleClass="outputText2" />
                                 <h:selectOneMenu value="#{ManagedIngresoAlmacen.codCapitulo}" styleClass="inputText" >
                                     <f:selectItems value="#{ManagedIngresoAlmacen.capitulosList2}"  />
                                     <a4j:support event="onchange" action="#{ManagedIngresoAlmacen.cargarGrupos}" reRender="grupos,productos"/>
                                </h:selectOneMenu>
                                 <h:outputText value="Grupo : " styleClass="outputText2" />
                                 <h:selectOneMenu value="#{ManagedIngresoAlmacen.codGrupo}" styleClass="inputText" id="grupos">
                                     <f:selectItems value="#{ManagedIngresoAlmacen.gruposList2}"  />
                                     <a4j:support event="onchange" action="#{ManagedIngresoAlmacen.cargarMateriales}" reRender="productos"/>
                                </h:selectOneMenu>
                                 <h:outputText value="Item : " styleClass="outputText2" />
                                 <h:selectOneMenu value="#{ManagedIngresoAlmacen.codMaterial}" styleClass="inputText" id="productos">
                                     <f:selectItems value="#{ManagedIngresoAlmacen.materialesList}"  />
                                </h:selectOneMenu>

                                

                            </h:panelGrid>
                                <div align="center">
                                <a4j:commandButton styleClass="btn" value="Buscar" onclick="javascript:Richfaces.hideModalPanel('panelBuscarIngresoAlmacen');"
                                action="#{ManagedIngresoAlmacen.buscarIngresoAlmacen_action}" reRender="dataIngresosAlmacen,controles" timeout="7200" />
                                <input type="button" value="Cancelar" onclick="javascript:Richfaces.hideModalPanel('panelBuscarIngresoAlmacen')" class="btn" />
                                </div>
                        </h:panelGroup>
                        </a4j:form>
            </rich:modalPanel>
            
            <a4j:include viewId="/panelProgreso.jsp" />
            <a4j:include viewId="/message.jsp" />

        </body>
    </html>

</f:view>
    
