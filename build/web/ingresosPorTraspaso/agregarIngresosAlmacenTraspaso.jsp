
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
                
        function hallaValorEquivalente(elemento){            
            var valorEquivalencia=document.getElementById("form2:valorEquivalencia").value;            
            if(valorEquivalencia>0){
            var resultado = elemento.value/valorEquivalencia;
            document.getElementById("form2:cantEquivalente").value = resultado;
            }
        }
        function hallaCantidadIngresoFisico(){
                    var elements=document.getElementById('form3:dataIngresoAlmacenDetalleEstado');
                    var rowsElement=elements.rows;
                    var cantidadIngresoFisico = 0;
                    var totalImporteF=0;
                    for(var i=1;i<rowsElement.length;i++){
                        var cellsElement=rowsElement[i].cells;
                        var cel3=cellsElement[3];
                        var cantidadParcial=cel3.getElementsByTagName('input')[0];
                        if(cantidadParcial.type=='text' && parseFloat(cantidadParcial.value)>0){
                            cantidadIngresoFisico=parseFloat(cantidadIngresoFisico)+parseFloat(cantidadParcial.value);
                        }
                    }
                    //alert(document.getElementById('form2:cantTotalIngresoFisico'));
                    //alert(cantidadIngresoFisico);
                    document.getElementById('form3:cantTotalIngresoFisico').value=Math.round(cantidadIngresoFisico*100)/100;
       }
       function seleccionarTodo(nametable){
             var check = document.getElementById("form3:seleccionar_todo");
                   var count=0;
                   var elements=document.getElementById(nametable);
                   var rowsElement=elements.rows;
                   for(var i=1;i<rowsElement.length;i++){
                    var cellsElement=rowsElement[i].cells;
                    var cel=cellsElement[0];
                    if(cel.getElementsByTagName('input')[0].type=='checkbox'){
                          cel.getElementsByTagName('input')[0].checked=check.checked;
                     }
                   }
                }
                function iniciarProgreso()
                {
                    document.getElementById("botones").style.display='none';
                    document.getElementById('form1:progress').style.display='';
                }
                function terminarProgreso()
                {
                    document.getElementById("botones").style.display='';
                    document.getElementById('form1:progress').style.display='none';
                }
       </script>
        </head>
        <body >
            <a4j:form id="form1">
                <div align="center">
                    <h:outputText value="#{ManagedIngresoTraspasoAlmacen.cargarAgregarIngresosAlmacenTraspaso}"/>
                    <br><br>
                        <rich:panel id="panelEstadoCuentas" styleClass="panelBuscar" style="top:50px;left:50px;width:700px;">
                    <f:facet name="header">
                        <h:outputText value="<div   onmouseover=\"this.style.cursor='move'\" onmousedown=\"comienzoMovimiento(event, 'form1:panelEstadoCuentas');\"  >Buscar<div   style=\"margin-left:550px;\"   onclick=\"document.getElementById('form1:panelEstadoCuentas').style.visibility='hidden';document.getElementById('panelsuper').style.visibility='hidden';\" onmouseover=\"this.style.cursor='hand'\"   >Cerrar</div> </div> "
                              escape="false" />
                    </f:facet>
                    </rich:panel>
                  

                  <rich:panel style="width:80%" headerClass="headerClassACliente">
                            <f:facet name="header">
                                <h:outputText value="Registro de Traspaso a Almacen"  />
                            </f:facet>
                            

                        <h:panelGrid columns="4" >
                            
                            <h:outputText value="Gestion:" styleClass="outputText2" />
                            <h:outputText value="#{ManagedIngresoTraspasoAlmacen.ingresosAlmacen.gestiones.nombreGestion}" styleClass="outputText1" />
                            <h:outputText value="Fecha de Ingreso:" styleClass="outputText2" />

                                 <h:outputText value="#{ManagedIngresoTraspasoAlmacen.ingresosAlmacen.fechaIngresoAlmacen}" styleClass="outputText2" >
                                 <f:convertDateTime pattern="dd/MM/yyyy"   />
                                 </h:outputText>
                            

                            <h:outputText value="Nro Ingreso Almacen:" styleClass="outputText2" />
                            <h:outputText value="#{ManagedIngresoTraspasoAlmacen.ingresosAlmacen.nroIngresoAlmacen}" styleClass="outputText1" />

                            <h:outputText value="Estado De Ingreso Almacen:" styleClass="outputText2" />
                            <h:outputText value="APROBADO" styleClass="outputText1" />

                            <h:outputText value="Tipo de Ingreso:" styleClass="outputText2" />
                            <h:outputText value="TRASPASO" styleClass="outputText2" />
                            
                            <h:outputText rendered="false" value="Tipo de Compra:" styleClass="outputText2" />
                            <h:selectOneMenu rendered="false"  styleClass="inputText" value="#{ManagedIngresoTraspasoAlmacen.ingresosAlmacen.tiposCompra.codTipoCompra}"   >
                                <f:selectItems value="#{ManagedIngresoTraspasoAlmacen.tiposCompraList}"  />
                            </h:selectOneMenu>

                            <h:outputText rendered="false" value="Origen Proveedor: " styleClass="outputText2" />
                            <h:outputText rendered="false" value="#{ManagedIngresoTraspasoAlmacen.ingresosAlmacen.proveedores.paises.nombrePais}" styleClass="outputText2" id="nombrePaisProveedor" />

                            <h:outputText rendered="false" value="Proveedor:" styleClass="outputText2" />
                            <h:selectOneMenu  rendered="false" styleClass="inputText" value="#{ManagedIngresoTraspasoAlmacen.ingresosAlmacen.proveedores.codProveedor}"   >
                                <f:selectItems value="#{ManagedIngresoTraspasoAlmacen.proveedoresList}"  />
                                <a4j:support event="onchange" action="#{ManagedIngresoTraspasoAlmacen.proveedores_change}" reRender="nombrePaisProveedor" />
                            </h:selectOneMenu>


                            <h:outputText rendered="false" value="Con Credito Fiscal:" styleClass="outputText2" />
                            <h:selectOneRadio rendered="false" value="#{ManagedIngresoTraspasoAlmacen.ingresosAlmacen.creditoFiscalSiNo}" styleClass="outputText2">
                                <f:selectItem  itemValue="1" itemLabel="Si"  />
                                <f:selectItem  itemValue="0" itemLabel="No" />
                            </h:selectOneRadio>

                            


                            <h:outputText value="Observaciones: " styleClass="outputText2" />
                            <h:inputTextarea  value="#{ManagedIngresoTraspasoAlmacen.ingresosAlmacen.obsIngresoAlmacen}" styleClass="outputText2" cols="50" rows="5" />

                        </h:panelGrid>
                    </rich:panel>

                    
                    
                    
                    <rich:dataTable value="#{ManagedIngresoTraspasoAlmacen.ingresosAlmacenDetalleList}" var="data"
                                    id="dataIngresoAlmacenDetalle"
                                    onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                    onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                    headerClass="headerClassACliente" 
                                    style="width:80%" binding="#{ManagedIngresoTraspasoAlmacen.ingresosAlmacenDetalleDataTable}" >
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value=""  />
                            </f:facet>
                            <h:selectBooleanCheckbox value="#{data.checked}" />
                            
                        </h:column>
                        
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Item"  />
                            </f:facet>
                            <h:outputText  value="#{data.materiales.nombreMaterial}"/>
                        </h:column>
                       
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Cant. Segun Documento"  />
                            </f:facet>
                            <h:outputText value="#{data.cantTotalIngreso}"  />
                        </h:column>
                      
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Unid."   />
                            </f:facet>
                            <h:outputText value="#{data.ordenesCompraDetalle.unidadesMedida.nombreUnidadMedida}"  />
                        </h:column>

                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Cantidad Enviada de Traspaso"  />
                            </f:facet>
                            <h:outputText value="#{data.cantEnviadaTraspaso}"  />
                        </h:column>
                        
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Unidades"  />
                            </f:facet>
                            <h:outputText value="#{data.unidadesMedidaTraspaso.nombreUnidadMedida}"  />
                        </h:column>

                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Cantidad Fisica"  />
                            </f:facet>
                            <h:outputText value="#{data.cantTotalIngresoFisico}"  />
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Unidades Empaque"  />
                            </f:facet>
                            <h:outputText value="#{data.unidadesMedida.nombreUnidadMedida}"  />
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Observacion"  />
                            </f:facet>
                            <h:outputText value="#{data.secciones.nombreSeccion}"  />
                        </h:column>
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Observacion"  />
                            </f:facet>
                            <h:outputText value="0"  />
                        </h:column>
                        <h:column rendered="false">
                            <f:facet name="header">
                                <h:outputText value=""  />
                            </f:facet>
                            <h:outputText value="" />
                            <a4j:commandLink action="#{ManagedIngresoTraspasoAlmacen.detallarItem_action}"
                            onclick="Richfaces.showModalPanel('panelDetalleItem')" reRender="contenidoDetalleItem" >
                                <h:graphicImage url="../img/areasdependientes.png" alt="detalle de item">

                                </h:graphicImage>

                            </a4j:commandLink>
                        </h:column>

                    </rich:dataTable>

                    <%-- <ws:datascroller fordatatable="dataAreasEmpresa"  />--%>
                    <br>

                    <div id="botones">
                    <a4j:commandButton value="Guardar" styleClass="btn"
                    onclick="if(confirm('Esta seguro de aceptar el traspaso?')==false){return false;}else{iniciarProgreso();}"
                    action="#{ManagedIngresoTraspasoAlmacen.guardarIngresoAlmacenTraspaso_action}"
                    oncomplete="if(#{ManagedIngresoTraspasoAlmacen.mensaje eq 1}){alert('Se acepto el traspaso');window.location.href='navegadorTraspaso.jsf?dataA='+(new Date()).getTime().toString();}else{alert('#{ManagedIngresoTraspasoAlmacen.mensaje}');terminarProgreso()} "
                    />
                    
                    <a4j:commandButton value="Agregar" styleClass="btn"
                    onclick="Richfaces.showModalPanel('panelAgregarItem')" action="#{ManagedIngresoTraspasoAlmacen.agregarItem_action}"
                    reRender="contenidoAgregarItem" rendered="false"
                    />
                    
                    <a4j:commandButton value="Eliminar" styleClass="btn"
                    action="#{ManagedIngresoTraspasoAlmacen.eliminarItem_action}"
                    reRender="dataIngresoAlmacenDetalle"
                    />
                    </div>
                    <h:panelGrid>
                          <h:graphicImage url="../img/load.gif" id="progress" style="display:none" />
                     </h:panelGrid>
                    
                

            </a4j:form>

             <rich:modalPanel id="panelAgregarItem" minHeight="400"  minWidth="800"
                                     height="400" width="800"
                                     zindex="200"
                                     headerClass="headerClassACliente"
                                     resizeable="false" style="overflow :auto" >
                        <f:facet name="header">
                            <h:outputText value="Detalle Item"/>
                            <f:facet name="controls">
                            <a4j:commandLink onclick="javascript:Richfaces.hideModalPanel('panelAgregarItem')" style="color: black" value="cerrar"></a4j:commandLink>
                            </f:facet>
                        </f:facet>
                        
                        
                        
                        <a4j:form id="form2">
                        <h:panelGroup id="contenidoAgregarItem">
                          <div align="center">
                           <h:panelGrid columns="4" id="contenidoIngresoAlmacenDetalle" headerClass="headerClassACliente" width="90%" style="border:1px solid #000000;" >
                                <f:facet name="header">
                                      <h:outputText value="Buscar Item" />
                                </f:facet>

                                <h:outputText value="Capitulo:" styleClass="tituloCampo1" />
                                <h:selectOneMenu value="#{ManagedIngresoTraspasoAlmacen.buscarMaterial.grupos.capitulos.codCapitulo}" styleClass="inputText" >
                                    <f:selectItems value="#{ManagedIngresoTraspasoAlmacen.capitulosList}"  />
                                    <a4j:support event="onchange" action="#{ManagedIngresoTraspasoAlmacen.capitulos_change}" reRender="codGrupo" />
                                </h:selectOneMenu>

                                <h:outputText value="Grupo:" styleClass="tituloCampo1" />
                                <h:selectOneMenu value="#{ManagedIngresoTraspasoAlmacen.buscarMaterial.grupos.codGrupo}" styleClass="inputText" id="codGrupo" >
                                    <f:selectItems value="#{ManagedIngresoTraspasoAlmacen.gruposList}"  />
                                </h:selectOneMenu>

                                <h:outputText value="Item:" styleClass="tituloCampo1" />

                                <h:inputText value="#{ManagedIngresoTraspasoAlmacen.buscarMaterial.nombreMaterial}" styleClass="inputText" size="50"  />
                                
                                <a4j:commandButton value="Buscar" action="#{ManagedIngresoTraspasoAlmacen.buscarMaterial_action}" styleClass="btn"  reRender="dataBuscarMaterial"  />
                                
                           </h:panelGrid>                             
                              <br/>

                            <rich:dataTable value="#{ManagedIngresoTraspasoAlmacen.materialesBuscarList}" var="data"
                                    id="dataBuscarMaterial"
                                    onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                    onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                    headerClass="headerClassACliente"
                                    binding="#{ManagedIngresoTraspasoAlmacen.materialesBuscarDataTable}" >


                                <h:column>
                                    <f:facet name="header">
                                        <h:outputText value="Material"  />
                                    </f:facet>
                                    <a4j:commandLink  action="#{ManagedIngresoTraspasoAlmacen.seleccionarMaterial_action}" styleClass="outputText2" onclick="Richfaces.hideModalPanel('panelAgregarItem')" reRender="dataIngresoAlmacenDetalle" >
                                        <h:outputText  value="#{data.nombreMaterial}" styleClass="outputText2" />
                                    </a4j:commandLink>

                                </h:column>

                                <h:column>
                                    <f:facet name="header">
                                        <h:outputText value="Grupo"  />
                                    </f:facet>
                                    <h:outputText  value="#{data.grupos.nombreGrupo}"/>
                                </h:column>

                                <h:column>
                                    <f:facet name="header">
                                        <h:outputText value="Capitulo"  />
                                    </f:facet>
                                    <h:outputText value="#{data.grupos.capitulos.nombreCapitulo}"  />
                                </h:column>
                                
                            </rich:dataTable>
                            </h:panelGroup>
                            </div>
                            
                                <div align="center">                                
                                <input type="button" value="Cancelar" class="btn" onclick="javascript:Richfaces.hideModalPanel('panelAgregarItem')" />
                                </div>
                        
                        </a4j:form>
            </rich:modalPanel>
            
            <rich:modalPanel id="panelDetalleItem" minHeight="400"  minWidth="1000"
                                     height="400" width="1000"
                                     zindex="200"
                                     headerClass="headerClassACliente"
                                     resizeable="false" style="overflow :auto" >
                        <f:facet name="header">
                            <h:outputText value="Detalle Item"/>
                        </f:facet>
                        <a4j:form id="form3">
                                <h:panelGroup id="contenidoDetalleItem">
                                    <div align="center">                                   
                                    <h:outputText value="Item: " styleClass="tituloCampo1" />
                                    &nbsp;
                                    <h:outputText value="#{ManagedIngresoTraspasoAlmacen.ingresosAlmacenDetalle.materiales.nombreMaterial}" styleClass="tituloCampo1" />
                                   
                                   <br/>
                                   <br/>

                                   <h:panelGrid columns="9" id="contenidoIngresoAlmacenDetalle" headerClass="headerClassACliente" width="100%" style="border:1px solid #000000;" >
                                        <f:facet name="header">
                                              <h:outputText value="Repetir Valores" />
                                        </f:facet>
                                        <h:outputText value="Cantidad Segun Documento:" styleClass="outputText1" />

                                        <h:panelGroup>
                                            <h:inputText value="#{ManagedIngresoTraspasoAlmacen.ingresosAlmacenDetalle.cantTotalIngreso}" styleClass="inputText" size="5" id="cantTotalIngreso" onkeypress="valNum(event);" readonly="true" onkeyup="hallaValorEquivalente(this)" />
                                            <h:outputText value="[" styleClass="outputText1" />
                                            <h:outputText value="#{ManagedIngresoTraspasoAlmacen.ingresosAlmacenDetalle.ordenesCompraDetalle.unidadesMedida.abreviatura}" styleClass="outputText1" />
                                            <h:outputText value="]" styleClass="outputText1" />
                                        </h:panelGroup>

                                        <h:outputText value="Equivalente:" styleClass="outputText1" />
                                        <h:panelGroup>
                                            <h:inputText value="#{ManagedIngresoTraspasoAlmacen.ingresosAlmacenDetalle.cantEquivalente}" styleClass="inputText" size="5" readonly="true" onkeypress="valNum(event);" id="cantEquivalente" />
                                            <h:outputText value="[" styleClass="outputText1" />
                                            <h:outputText value="#{ManagedIngresoTraspasoAlmacen.ingresosAlmacenDetalle.unidadesMedidaEquivalencia.abreviatura}" styleClass="outputText1" />
                                            <h:outputText value="]" styleClass="outputText1" />
                                            <h:inputHidden value="#{ManagedIngresoTraspasoAlmacen.ingresosAlmacenDetalle.valorEquivalencia}" id="valorEquivalencia" />
                                        </h:panelGroup>

                                        <h:outputText value="Cant. Ingreso Fisico:" styleClass="outputText1" />
                                        <h:panelGroup>
                                            <h:inputText value="#{ManagedIngresoTraspasoAlmacen.ingresosAlmacenDetalle.cantTotalIngresoFisico}" id="cantTotalIngresoFisico"  styleClass="inputText" size="5" onkeypress="valNum(event);" readonly="true" />
                                            <h:outputText value="[" styleClass="outputText1" />
                                            <h:outputText value="#{ManagedIngresoTraspasoAlmacen.ingresosAlmacenDetalle.unidadesMedida.abreviatura}" styleClass="outputText1" />
                                            <h:outputText value="]" styleClass="outputText1" />
                                        </h:panelGroup>

                                        <h:outputText value="Nro de Empaques:" styleClass="outputText1" />

                                        <h:inputText value="#{ManagedIngresoTraspasoAlmacen.ingresosAlmacenDetalle.nroUnidadesEmpaque}" styleClass="inputText" size="5"  />
                                        <a4j:commandButton value="Generar Etiquetas" action="#{ManagedIngresoTraspasoAlmacen.generarEtiquetas_action}" styleClass="btn"  reRender="dataIngresoAlmacenDetalleEstado"  />
                                      </h:panelGrid>

                                      <br/>
                                      
                                      <h:panelGrid columns="6" headerClass="headerClassACliente" width="100%" style="border:1px solid #000000;">
                                          <f:facet name="header">
                                              <h:outputText value="Repetir Valores" />
                                          </f:facet>


                                        <h:outputText value="Cantidad Parcial:" styleClass="outputText1" />
                                        <h:inputText value="#{ManagedIngresoTraspasoAlmacen.ingresosAlmacenDetalleEstado.cantidadParcial}" styleClass="inputText" size="10" onkeypress="valNum(event);" >

                                        </h:inputText>

                                        <h:outputText value="Lote Proveedor:" styleClass="outputText1" />
                                        <h:inputText value="#{ManagedIngresoTraspasoAlmacen.ingresosAlmacenDetalleEstado.loteMaterialProveedor}" styleClass="inputText" size="10" onkeypress="valNum(event);" >

                                        </h:inputText>

                                        <h:outputText value="Unidad de Empaque:" styleClass="outputText1" />
                                        <h:selectOneMenu value="#{ManagedIngresoTraspasoAlmacen.ingresosAlmacenDetalleEstado.empaqueSecundarioExterno.codEmpaqueSecundarioExterno}" styleClass="inputText" >
                                            <f:selectItems value="#{ManagedIngresoTraspasoAlmacen.empaqueSecundarioExternoList}"  />
                                        </h:selectOneMenu>

                                        <h:outputText value="Fecha Manufactura:" styleClass="outputText1" />
                                        <rich:calendar   datePattern="dd/MM/yyyy" styleClass="inputText"
                                            value="#{ManagedIngresoTraspasoAlmacen.ingresosAlmacenDetalleEstado.fechaManufactura}"  enableManualInput="true" oninputblur="valFecha(this);" width="1" />

                                        <h:outputText value="Fecha de Reanalisis:" styleClass="outputText1" />
                                        <rich:calendar   datePattern="dd/MM/yyyy" styleClass="inputText"
                                            value="#{ManagedIngresoTraspasoAlmacen.ingresosAlmacenDetalleEstado.fechaReanalisis}"  enableManualInput="true" oninputblur="valFecha(this);" width="1" />

                                        <h:outputText value="Fecha de Vencimiento:" styleClass="outputText1" />
                                        <rich:calendar   datePattern="dd/MM/yyyy" styleClass="inputText"
                                            value="#{ManagedIngresoTraspasoAlmacen.ingresosAlmacenDetalleEstado.fechaVencimiento}"  enableManualInput="true" oninputblur="valFecha(this);" width="1" />
                                            <h:outputText value="Ambiente:" styleClass="outputText1" />
                                        <h:selectOneMenu value="#{ManagedIngresoTraspasoAlmacen.ingresosAlmacenDetalleEstado.estanteAlmacen.ambienteAlmacen.codAmbiente}" styleClass="inputText" >
                                            <f:selectItem itemValue="0" itemLabel="-NINGUNO-" />
                                            <f:selectItems value="#{ManagedIngresoTraspasoAlmacen.ambienteAlmacenList}"  />
                                            <a4j:support event="onchange" reRender="estante" action="#{ManagedIngresoTraspasoAlmacen.ambienteAlmacen_change}" />
                                        </h:selectOneMenu>
                                        <h:outputText value="Estante:" styleClass="outputText1" />
                                        <h:selectOneMenu value="#{ManagedIngresoTraspasoAlmacen.ingresosAlmacenDetalleEstado.estanteAlmacen.codEstante}" styleClass="inputText" id="estante" >
                                            <f:selectItem itemValue="0" itemLabel="-NINGUNO-" />
                                            <f:selectItems value="#{ManagedIngresoTraspasoAlmacen.estanteAlmacenList}"  />
                                        </h:selectOneMenu>
                                            <h:outputText value="" />
                                        <h:outputText value="" />

                                        <h:outputText value="Fila:" styleClass="outputText1" />
                                        <h:inputText value="#{ManagedIngresoTraspasoAlmacen.ingresosAlmacenDetalleEstado.fila}" styleClass="inputText" size="10"  ><!--onkeypress="valNum(event);"-->
                                        </h:inputText>

                                        <h:outputText value="Columna:" styleClass="outputText1" />
                                        <h:inputText value="#{ManagedIngresoTraspasoAlmacen.ingresosAlmacenDetalleEstado.columna}" styleClass="inputText" size="10"  ><!--onkeypress="valNum(event);"-->
                                        </h:inputText>
                                    </h:panelGrid>
                                    <br/>

                                    <a4j:commandButton action="#{ManagedIngresoTraspasoAlmacen.repetirValoresIngresoAlmacenDetalleEstado_action}" oncomplete="if(#{ManagedIngresoTraspasoAlmacen.mensaje!=''}){alert('#{ManagedIngresoTraspasoAlmacen.mensaje}')}" reRender="dataIngresoAlmacenDetalleEstado,contenidoIngresoAlmacenDetalle" value="Repetir Valores" styleClass="btn" />
                                    <h:selectBooleanCheckbox id="seleccionar_todo"  onclick="seleccionarTodo('form3:dataIngresoAlmacenDetalleEstado');"/>
                                    
                                    <br/>
                                    <%-- ,contenidoIngresoAlmacenDetalle --%>

                                    <rich:dataTable value="#{ManagedIngresoTraspasoAlmacen.ingresosAlmacenDetalle.ingresosAlmacenDetalleEstadoList}" var="data"
                                            id="dataIngresoAlmacenDetalleEstado"
                                            onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                            onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                            headerClass="headerClassACliente"
                                            width="100%" >

                                                <h:column>
                                            <f:facet name="header">
                                                <h:outputText value=""  />
                                            </f:facet>
                                            <h:selectBooleanCheckbox value="#{data.checked}"  />

                                        </h:column>
                                        <h:column>
                                            <f:facet name="header">
                                                <h:outputText value=""  />
                                            </f:facet>
                                            <h:outputText  value="#{data.etiqueta}"/>
                                        </h:column>

                                        <h:column>
                                            <f:facet name="header">
                                                <h:outputText value="Estado Item"  />
                                            </f:facet>
                                            <h:outputText  value="#{data.estadosMaterial.nombreEstadoMaterial}"/>
                                        </h:column>

                                        <h:column>
                                            <f:facet name="header">
                                                <h:outputText value="Empaque"  />
                                            </f:facet>
                                            <h:outputText value="#{data.empaqueSecundarioExterno.nombreEmpaqueSecundarioExterno}"  />
                                        </h:column>

                                        <h:column>
                                            <f:facet name="header">
                                                <h:outputText value="Cantidad Parcial"  />
                                            </f:facet>
                                            <h:inputText value="#{data.cantidadParcial}" styleClass="inputText" size="5"  onkeyup="hallaCantidadIngresoFisico()"  />
                                        </h:column>
                                        <h:column>
                                            <f:facet name="header">
                                                <h:outputText value="Fecha Manufactura"  />
                                            </f:facet>
                                            <h:inputText value="#{data.fechaManufactura}" styleClass="inputText" size="10"  onblur="valFecha(this);" onkeyup="hallaCantidadIngresoFisico()"  >
                                                <f:convertDateTime pattern="dd/MM/yyyy" />
                                            </h:inputText>

                                        </h:column>
                                        <h:column>
                                            <f:facet name="header">
                                                <h:outputText value="Lote Proveedor"  />
                                            </f:facet>
                                            <h:outputText value="#{data.loteMaterialProveedor}"  />
                                        </h:column>

                                        <h:column>
                                            <f:facet name="header">
                                                <h:outputText value="Fecha Reanalisis"  />
                                            </f:facet>
                                            <h:inputText value="#{data.fechaReanalisis}" styleClass="inputText" size="10"  onblur="valFecha(this);" onkeyup="hallaCantidadIngresoFisico()"  >
                                                <f:convertDateTime pattern="dd/MM/yyyy" />
                                            </h:inputText>
                                        </h:column>

                                         <h:column>
                                            <f:facet name="header">
                                                <h:outputText value="Fecha Vencimiento"  />
                                            </f:facet>
                                            <h:inputText value="#{data.fechaVencimiento}" styleClass="inputText" size="10"  onblur="valFecha(this);" onkeyup="hallaCantidadIngresoFisico()"  >
                                                <f:convertDateTime pattern="dd/MM/yyyy" />
                                            </h:inputText>
                                        </h:column>
                                        <h:column>
                                            <f:facet name="header">
                                                <h:outputText value="Estante"  />
                                            </f:facet>
                                            <h:outputText value="#{data.estanteAlmacen.nombreEstante}"  />
                                        </h:column>
                                        <h:column>
                                            <f:facet name="header">
                                                <h:outputText value="Fila"   />
                                            </f:facet>
                                            <h:inputText value="#{data.fila}" styleClass="inputText" size="2" />
                                        </h:column>
                                        <h:column>
                                            <f:facet name="header">
                                                <h:outputText value="Columna"  />
                                            </f:facet>
                                            <h:inputText value="#{data.columna}"  styleClass="inputText" size="2" />
                                        </h:column>

                                        <h:column>
                                            <f:facet name="header">
                                                <h:outputText value="Obervaciones"  />
                                            </f:facet>
                                            <h:inputText value="#{data.observaciones}" size="20" styleClass="inputText"  />
                                        </h:column>

                                    </rich:dataTable>
                                    </h:panelGroup>
                            </div>

                            <div align="center">
                            <a4j:commandButton styleClass="btn" value="Registrar"
                                               oncomplete="if(#{ManagedIngresoTraspasoAlmacen.mensaje!=''}){alert('#{ManagedIngresoTraspasoAlmacen.mensaje}')}else{Richfaces.hideModalPanel('panelDetalleItem')}"
                                               action="#{ManagedIngresoTraspasoAlmacen.guardarDetalleItem_action}"
                                               reRender="dataIngresoAlmacenDetalle"
                                                />
                            <input type="button" value="Cancelar" class="btn" onclick="javascript:Richfaces.hideModalPanel('panelDetalleItem')" />
                            </div>
                        
                        </a4j:form>
            </rich:modalPanel>




        </body>
    </html>

</f:view>

