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
                    if(cel.getElementsByTagName('input').length>0&&cel.getElementsByTagName('input')[0].type=='checkbox'){
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
                    var elements=document.getElementById('form2:dataSalidaAlmacenDetalleIngreso');
                    var rowsElement=elements.rows;
                    var cantidadIngresoFisico = 0;
                    var totalImporteF=0;
                    for(var i=1;i<rowsElement.length;i++){
                        var cellsElement=rowsElement[i].cells;
                        var cel3=cellsElement[3];
                        var cantidadParcial=cel3.getElementsByTagName('input')[0];
                        if(cantidadParcial.type=='text'){
                            cantidadIngresoFisico=parseFloat(cantidadIngresoFisico)+parseFloat(cantidadParcial.value);
                        }
                    }
                    //alert(document.getElementById('form2:cantTotalIngresoFisico'));
                    //alert(cantidadIngresoFisico);
                    document.getElementById('form2:cantTotalIngresoFisico').value=Math.round(cantidadIngresoFisico*100)/100;
       }
       function validaCantidad(obj){
           //var cantidadDisponible=parseFloat(obj.parentNode.parentNode.getElementsByTagName('td')[9].getElementsByTagName('input')[0].value);
           //alert (obj.parentNode.parentNode.getElementsByTagName('td')[9].innerHTML);
           //alert(obj.value.substr(0,obj.value.length-1));
           var cantidadDisponible =  obj.parentNode.parentNode.getElementsByTagName('td')[9].innerHTML;
           if(obj.value>parseFloat(cantidadDisponible)){
              alert("la cantidad debe ser menor a la cantidad disponible");
              obj.focus();
              //obj.value = obj.value.substr(0,obj.value.length-1);
              obj.value = cantidadDisponible;
              return false;
           }
           
           if(this.hallaCantidadSalidaFisica()==false){
               alert(" el total no debe sobrepasar la cantidad total de salida ");
               obj.focus();
               var col10 = obj.parentNode.parentNode.getElementsByTagName('td')[10];
               obj.value = col10.getElementsByTagName("input")[1].value;
               //alert(col10.getElementsByTagName("input")[1].value);
               return false;
           }
           
           return true;
       }
       
       function hallaCantidadSalidaFisica(){
                    var elements=document.getElementById('form2:dataSalidaAlmacenDetalleIngreso');                    
                    var rowsElement=elements.rows;
                    var cantidadSalidaFisica = 0;
                    var totalImporteF=0;
                    for(var i=1;i<rowsElement.length;i++){
                        var cellsElement=rowsElement[i].cells;
                        var cel10=cellsElement[10];
                        var cantidadParcial=cel10.getElementsByTagName('input')[0];
                        if(cantidadParcial.type=='text'){
                            cantidadSalidaFisica=parseFloat(cantidadSalidaFisica)+parseFloat(cantidadParcial.value);
                        }
                    }

                    //alert(document.getElementById('form2:cantTotalIngresoFisico'));
                    //alert(cantidadIngresoFisico);
                    if(parseFloat(document.getElementById('form2:cantTotalSalidaFisica').innerHTML)<(Math.round(cantidadSalidaFisica*100)/100))
                        {
                            return false;
                        }

                      return true;
       }


       function calculaEquivalencia(obj){
           valorEquivalencia = document.getElementById("form5:valorEquivalencia").value;
           if(valorEquivalencia>0){
               document.getElementById("form5:cantidadEquivalencia").value = obj.value /valorEquivalencia;
           }
       }
       function calculaEquivalencia1(obj){
           valorEquivalencia = document.getElementById("form4:valorEquivalencia").value;
           if(valorEquivalencia>0){
               document.getElementById("form4:cantidadEquivalencia").value = obj.value /valorEquivalencia;
           }
       }
       function validaCantidadSalidaAlmacenDetalle(obj){
           //cantidadDisponible
           //alert("entro al valida cantidad");
           //alert(document.getElementById("form5:cantidadSalidaAlmacen").value);
           //alert(document.getElementById("form5:cantidadDisponible").innerHTML);
           var cantidadSalidaAlmacen = document.getElementById("form5:cantidadSalidaAlmacen").value;
           var cantidadDisponible = parseFloat(document.getElementById("form5:cantidadDisponible").innerHTML);

           //alert (cantidadSalidaAlmacen);
           //alert (cantidadDisponible);
           
           if(cantidadSalidaAlmacen>cantidadDisponible){
               document.getElementById("form5:cantidadSalidaAlmacen").value = document.getElementById("form5:cantidadDisponible").innerHTML;
               calculaEquivalencia(obj);
               alert("la cantidad de salida de almacen no puede sobrepasar la cantidad disponible");               
               return false;
            }
           calculaEquivalencia(obj);
           return true;
       }



       function validaCantidadSalidaAlmacenDetalle1(obj){
           //cantidadDisponible
           //alert("entro al valida cantidad");
           //alert(document.getElementById("form5:cantidadSalidaAlmacen").value);
           //alert(document.getElementById("form5:cantidadDisponible").innerHTML);
           var cantidadSalidaAlmacen = document.getElementById("form4:cantidadSalidaAlmacen").value;
           var cantidadDisponible = parseFloat(document.getElementById("form4:cantidadDisponible").innerHTML);
           
           //alert (cantidadSalidaAlmacen);
           //alert (cantidadDisponible);

           if(cantidadSalidaAlmacen>cantidadDisponible){
               document.getElementById("form4:cantidadSalidaAlmacen").value = document.getElementById("form4:cantidadDisponible").innerHTML;
               calculaEquivalencia1(obj);
               alert("la cantidad de salida de almacen no puede sobrepasar la cantidad disponible");
               return false;
            }
           calculaEquivalencia1(obj);
           return true;
       }
       function cantAuxSalida(){           
           var elements=document.getElementById('form2:dataSalidaAlmacenDetalleIngreso');
                    var rowsElement=elements.rows;
                    var cantidadSalidaFisica = 0;
                    var totalImporteF=0;
                    for(var i=1;i<rowsElement.length;i++){
                        var cellsElement=rowsElement[i].cells;
                        var cel10=cellsElement[10];
                        cel10.getElementsByTagName('input')[1].value = cel10.getElementsByTagName('input')[0].value ;
                    }

       }
       function calculaEquivalencia()
       {
           
       }
       function validarGuardar()
       {
           var tabla=document.getElementById("form5:dataBuscarMaterial");
           var cont=0;
           var codMaterial=0;
           for(var i=1;i<tabla.rows.length;i++)
           {
               tabla.rows[i].cells[7].getElementsByTagName("input")[0].style.backgroundColor='white';
               if(tabla.rows[i].cells[0].getElementsByTagName("input")[0].checked)
               {
                   if(codMaterial==0)
                   {
                        codMaterial=parseInt(tabla.rows[i].cells[2].getElementsByTagName("input")[0].value);
                   }
                   if(codMaterial!=parseInt(tabla.rows[i].cells[2].getElementsByTagName("input")[0].value))
                   {
                        alert('Todos los items seleccionados deben ser del mismo material');
                        return false;
                   }
                   if(parseFloat(tabla.rows[i].cells[7].getElementsByTagName("input")[0].value)<=0.1)
                   {
                        alert('Debe Registrar una cantidad mayor a 0');
                        tabla.rows[i].cells[7].getElementsByTagName("input")[0].style.backgroundColor='#FFC0CB';
                        tabla.rows[i].cells[7].getElementsByTagName("input")[0].focus();
                        return false;
                   }
                   if(parseFloat(tabla.rows[i].cells[6].getElementsByTagName("span")[0].innerHTML)<parseFloat(tabla.rows[i].cells[7].getElementsByTagName("input")[0].value))
                   {
                        alert('No puede solicitar una cantidad mayor a la existente');
                        tabla.rows[i].cells[7].getElementsByTagName("input")[0].style.backgroundColor='#FFC0CB';
                        tabla.rows[i].cells[7].getElementsByTagName("input")[0].focus();
                        return false;
                   }
                   cont++;
               }
           }
           if(cont==0)
           {
                alert('No realizo ningun detalle');
                return false;
           }
           return true;

       }
   </script>
       
      

        </head>
        <body >
            <a4j:form id="form1">
                <div align="center">

                    <h:outputText value="#{ManagedRegistroSolicitudSalidaAlmacen.cargarAgregarSolicitudSalidaLoteProovedor}"/>
                    <br><br>
                        
                    <rich:panel id="panelEstadoCuentas" styleClass="panelBuscar" style="top:50px;left:50px;width:700px;">
                    <f:facet name="header">
                        <h:outputText value="<div   onmouseover=\"this.style.cursor='move'\" onmousedown=\"comienzoMovimiento(event, 'form1:panelEstadoCuentas');\"  >Buscar<div   style=\"margin-left:550px;\"   onclick=\"document.getElementById('form1:panelEstadoCuentas').style.visibility='hidden';document.getElementById('panelsuper').style.visibility='hidden';\" onmouseover=\"this.style.cursor='hand'\"   >Cerrar</div> </div> "
                              escape="false" />
                    </f:facet>
                    </rich:panel>

                <rich:panel style="width:100%"  headerClass="headerClassACliente">
                            <f:facet name="header">
                                <h:outputText value="Registro de Solicitud Salidas a Almacen"  />
                            </f:facet>
                        <h:panelGrid columns="6" >

                            <h:outputText value="Area / Departamento" styleClass="outputTextBold" />
                            <h:outputText value="::" styleClass="outputTextBold" />
                            <h:outputText value="#{ManagedRegistroSolicitudSalidaAlmacen.solicitudesSalidaLoteProovedorAgregar.areaDestinoSalida.nombreAreaEmpresa}" styleClass="outputText2" />
                            <h:outputText value="Solicitante" styleClass="outputTextBold" />
                            <h:outputText value="::" styleClass="outputTextBold" />
                            <h:panelGroup>
                            <h:outputText styleClass="outputText2" value="#{ManagedRegistroSolicitudSalidaAlmacen.solicitudesSalidaLoteProovedorAgregar.solicitante.nombrePersonal}" /> &nbsp;
                            <h:outputText styleClass="outputText2" value="#{ManagedRegistroSolicitudSalidaAlmacen.solicitudesSalidaLoteProovedorAgregar.solicitante.apPaternoPersonal}" /> &nbsp;
                            <h:outputText styleClass="outputText2" value="#{ManagedRegistroSolicitudSalidaAlmacen.solicitudesSalidaLoteProovedorAgregar.solicitante.apMaternoPersonal}" />
                            </h:panelGroup>
                            <h:outputText value="Tipo de Solicitud de salida" styleClass="outputTextBold" />
                            <h:outputText value="::" styleClass="outputTextBold" />
                            <h:selectOneMenu   styleClass="outputText2" value="#{ManagedRegistroSolicitudSalidaAlmacen.solicitudesSalidaLoteProovedorAgregar.tipoSolicitud.codTipoSolicitudSalida}" >
                                <f:selectItem itemValue="13" itemLabel="POR MUESTREO"></f:selectItem>
                                <f:selectItem itemValue="15" itemLabel="POR VALIDACIÓN"></f:selectItem>
                            </h:selectOneMenu>
                            
                            <h:outputText value="Observaciones" styleClass="outputTextBold" />
                            <h:outputText value="::" styleClass="outputTextBold" />
                            <h:inputTextarea  rows="5" cols="50" styleClass="outputText2" value="#{ManagedRegistroSolicitudSalidaAlmacen.solicitudesSalidaLoteProovedorAgregar.obsSolicitud}" />
                            </h:panelGrid>

                       
                             <a4j:commandButton value="Agregar Item" styleClass="btn"
                             onclick="Richfaces.showModalPanel('panelAgregarItem')" action="#{ManagedRegistroSolicitudSalidaAlmacen.agregarIngresoAlmacenDetalleEstado_action}"
                             reRender="contenidoAgregarItem"
                              />

                             <h:panelGroup>
                             <a4j:commandButton value="Eliminar Item" styleClass="btn"
                               onclick="if(validarSeleccion('form1:dataSalidasAlmacenDetalle')==false){return false;}"

                             action="#{ManagedRegistroSolicitudSalidaAlmacen.eliminarSolicitudDetalle_action}" reRender="dataSalidasAlmacenDetalle" />
                             </h:panelGroup>
                            

                        
                    </rich:panel>

                    

                        <div style="overflow:auto;height:200px;width:80%;text-align:center">
                        <rich:dataTable value="#{ManagedRegistroSolicitudSalidaAlmacen.solicitudDetalleEstadoList}"
                                    var="data"
                                    id="dataSalidasAlmacenDetalle"
                                    onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                    onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                    headerClass="headerClassACliente"
                                    style="width:100%;margin-top:1em"  >
                        <f:facet name="header">
                            <rich:columnGroup >
                                
                                <rich:column>
                                    <h:outputText value=""  />
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Item"  style="color:black !important"/>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Cantidad"  style="color:black !important" />
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Nro Ingreso"  style="color:black !important" />
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Lote Proovedor"  style="color:black !important" />
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Estado"  style="color:black !important" />
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="Cantidad"  style="color:black !important" />
                                </rich:column>
                            </rich:columnGroup>
                        </f:facet>
                        
                        <rich:subTable var="subData" value="#{data.ingresosAlmacenDetalleEstadoList}" rowKeyVar="row">
                            <rich:column rendered="#{row eq 0}" rowspan="#{data.ingresosAlmacenDetalleEstadoListSize}">
                               <h:selectBooleanCheckbox value="#{data.checked}" />
                            </rich:column>
                            <rich:column rendered="#{row eq 0}" rowspan="#{data.ingresosAlmacenDetalleEstadoListSize}">
                               <h:outputText  value="#{data.materiales.nombreMaterial}"/>
                            </rich:column>

                            <rich:column rendered="#{row eq 0}" rowspan="#{data.ingresosAlmacenDetalleEstadoListSize}">
                               <h:outputText value="#{data.cantTotalIngreso}"  />
                            </rich:column>
                            <rich:column  >
                               <h:outputText value="#{subData.ingresosAlmacen.nroIngresoAlmacen}"  />
                            </rich:column>
                            <rich:column styleClass="#{subData.checked?'conExistencia':'sinExistencia'}" >
                               <h:outputText value="#{subData.loteMaterialProveedor}"  />
                            </rich:column>
                            <rich:column  styleClass="#{subData.checked?'conExistencia':'sinExistencia'}">
                               <h:outputText value="#{subData.estadosMaterial.nombreEstadoMaterial}"  />
                            </rich:column>
                            <rich:column styleClass="#{subData.checked?'conExistencia':'sinExistencia'}" >
                               <h:outputText value="#{subData.cantidadParcial}"  />
                            </rich:column>
                        </rich:subTable>

                    </rich:dataTable>
                    </div>
                    

                    <br>
                      
                    <a4j:commandButton value="Guardar" styleClass="btn"
                    action="#{ManagedRegistroSolicitudSalidaAlmacen.guardarSolicitudSalidaLoteProveedor_action}"
                    onclick="if(confirm('Esta seguro de realizar la Solicitud de Salida de Almacen?')==false){return false;}"
                      oncomplete="if(#{ManagedRegistroSolicitudSalidaAlmacen.mensaje eq '1'}){alert('Se registro la solicitud de salidas');window.location.href='navegadorSolicitudesSalidaAlmacen.jsf?data='+(new Date()).getTime().toString();}
                      else{alert('#{ManagedRegistroSolicitudSalidaAlmacen.mensaje}');}"
                    ><f:param name="codEstadoSolicitudSalida" value="6" /></a4j:commandButton>
                    <%--final ale validacion salidas--%>

               
                

            </a4j:form>

             <rich:modalPanel id="panelAgregarItem" minHeight="450"  minWidth="850"
                                     height="450" width="850"
                                     zindex="200"
                                     headerClass="headerClassACliente"
                                     resizeable="false" style="overflow :auto" >
                                         
                    <f:facet name="header">
                        <h:outputText value="Detalle Item"/>                            
                    </f:facet>

                    <a4j:form id="form5">
                        <h:panelGroup id="contenidoAgregarItem">
                            <div align="center">
                            <rich:panel headerClass="headerClassACliente">
                                <f:facet name="header">
                                      <h:outputText value="Buscar Item" />
                                </f:facet>

                                <h:panelGrid columns="4" id="contenidoIngresoAlmacenDetalle" headerClass="headerClassACliente" width="90%" style="border:1px solid #000000;" >

                                     <h:outputText value="Capitulo:" styleClass="outputTextBold" />
                                     <h:selectOneMenu value="#{ManagedRegistroSolicitudSalidaAlmacen.buscarMaterial.grupos.capitulos.codCapitulo}" styleClass="inputText" >
                                         <f:selectItems value="#{ManagedRegistroSolicitudSalidaAlmacen.capitulosList}"  />
                                         <a4j:support event="onchange" action="#{ManagedRegistroSolicitudSalidaAlmacen.onChangeCapitulo}" reRender="codGrupo,codMaterial" />
                                     </h:selectOneMenu>

                                     <h:outputText value="Grupo:" styleClass="outputTextBold" />
                                     <h:selectOneMenu value="#{ManagedRegistroSolicitudSalidaAlmacen.buscarMaterial.grupos.codGrupo}" styleClass="inputText" id="codGrupo" >
                                         <f:selectItems value="#{ManagedRegistroSolicitudSalidaAlmacen.gruposList}"  />

                                     </h:selectOneMenu>

                                     <h:outputText value="Item:" styleClass="outputTextBold" />
                                     <h:inputText value="#{ManagedRegistroSolicitudSalidaAlmacen.buscarMaterial.nombreMaterial}" style="width:300px" styleClass="inputText"/>
                                     <h:outputText value="Lote Proovedor:" styleClass="outputTextBold" />
                                     <h:inputText value="#{ManagedRegistroSolicitudSalidaAlmacen.loteProovedor}" styleClass="inputText" />
                                     <h:outputText value="Estado Material:" styleClass="outputTextBold" />
                                     <h:selectOneMenu value="#{ManagedRegistroSolicitudSalidaAlmacen.codEstadoMaterial}" styleClass="inputText" >
                                         <f:selectItem itemValue='0' itemLabel="--TODOS--"/>
                                         <f:selectItems value="#{ManagedRegistroSolicitudSalidaAlmacen.estadosMaterialSelectList}"/>
                                     </h:selectOneMenu>


                                </h:panelGrid>
                                <br/>
                                <center>
                                    <a4j:commandButton value="Buscar" action="#{ManagedRegistroSolicitudSalidaAlmacen.buscarIngresosEstado_action}" styleClass="btn"  reRender="dataBuscarMaterial"  />
                                </center>
                            </rich:panel>
                            <div style="overflow:auto;height:200px;width:100%;text-align:center;margin-top:1em">
                                <rich:dataTable value="#{ManagedRegistroSolicitudSalidaAlmacen.ingresosAlmacenDetalleEstadoList}" var="data"
                                    id="dataBuscarMaterial"
                                    onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                    onRowMouseOver="this.style.backgroundColor='#DDE3E4';"                                    
                                    headerClass="headerClassACliente">

                                <rich:column>
                                    <f:facet name="header">
                                        <h:outputText value=""  />
                                    </f:facet>
                                    <h:selectBooleanCheckbox value="#{data.checked}"/>
                                </rich:column>
                                <rich:column>
                                    <f:facet name="header">
                                        <h:outputText value="Nro Ingreso"  />
                                    </f:facet>
                                    <h:outputText  value="#{data.ingresosAlmacen.nroIngresoAlmacen}" styleClass="outputText2" />
                                </rich:column>
                               
                                <rich:column>
                                    <f:facet name="header">
                                        <h:outputText value="Material"  />
                                    </f:facet>
                                    <h:inputHidden value="#{data.materiales.codMaterial}"/>
                                    <h:outputText  value="#{data.materiales.nombreMaterial}" />
                                </rich:column>
                                 <rich:column>
                                    <f:facet name="header">
                                        <h:outputText value="Fecha Ingreso"  />
                                    </f:facet>
                                    <h:outputText  value="#{data.ingresosAlmacen.fechaIngresoAlmacen}" styleClass="outputText2">
                                        <f:convertDateTime pattern="dd/MM/yyy"/>
                                    </h:outputText>
                                </rich:column>
                                <rich:column>
                                    <f:facet name="header">
                                        <h:outputText value="Lote Proovedor"  />
                                    </f:facet>
                                    <h:outputText  value="#{data.loteMaterialProveedor}" />
                                </rich:column>
                                <rich:column>
                                    <f:facet name="header">
                                        <h:outputText value="Estado Material"  />
                                    </f:facet>
                                    <h:outputText  value="#{data.estadosMaterial.nombreEstadoMaterial}" styleClass="outputText2" />
                                </rich:column>
                                <rich:column>
                                    <f:facet name="header">
                                        <h:outputText value="Cantidad Restante"  />
                                    </f:facet>
                                    <h:outputText  value="#{data.cantidadRestante}" styleClass="outputText2" />
                                </rich:column>
                                <rich:column>
                                    <f:facet name="header">
                                        <h:outputText value="Cantidad a solicitar"  />
                                    </f:facet>
                                    <h:inputText  value="#{data.cantidadParcial}" onkeypress="valNum(event);" styleClass="inputText" />
                                </rich:column>
                                <rich:column>
                                    <f:facet name="header">
                                        <h:outputText value="Unidad"  />
                                    </f:facet>
                                    <h:outputText  value="#{data.materiales.unidadesMedida.abreviatura}" styleClass="outputText2" />
                                </rich:column>

                            </rich:dataTable>
                            </div>
                            
                        </div>
                        </h:panelGroup>
                        

                        <div align="center">
                            <a4j:commandButton styleClass="btn" value="Guardar"
                            onclick="if(!validarGuardar()){return false;}"
                            action="#{ManagedRegistroSolicitudSalidaAlmacen.adicionarDetalleLoteSalida_action}"
                            oncomplete="javascript:Richfaces.hideModalPanel('panelAgregarItem');"
                                        reRender="dataSalidasAlmacenDetalle"
                                                />

                            <input type="button" value="Cancelar" class="btn" onclick="javascript:Richfaces.hideModalPanel('panelAgregarItem')" />
                        </div>

                    </a4j:form>
            </rich:modalPanel>
            <!-- panel modificar cantidad de material  -->
             <rich:modalPanel id="panelModificarCantidadMaterial" minHeight="180"  minWidth="650"
                                     height="180" width="650"
                                     zindex="200"
                                     headerClass="headerClassACliente"
                                     resizeable="false" style="overflow :auto" >
                        <f:facet name="header">
                            <h:outputText value="Modificar Cantidad Material"/>
                        </f:facet>
                        <a4j:form id="form4">
                        <h:panelGroup id="contenidoModificarCantidadMaterial">
                            <h:panelGrid columns="3">
                                <h:outputText value="Item" styleClass="outputText1" />
                                <h:outputText value="::" styleClass="outputText1" />
                                <h:outputText value="#{ManagedRegistroSolicitudSalidaAlmacen.solicitudesSalidaDetalle.materiales.nombreMaterial}" styleClass="outputText1" />

                                <%--h:outputText value="Cantidad Disponible" styleClass="outputText1" />
                                <h:outputText value="::" styleClass="outputText1" />
                                <h:outputText value="#{ManagedRegistroSolicitudSalidaAlmacen.salidasAlmacenDetalle.cantidadDisponible}" id="cantidadDisponible"  styleClass="outputText1" /--%>

                                <h:outputText value="Cantidad de Salida" styleClass="outputText1" />
                                <h:outputText value="::" styleClass="outputText1" />
                                <h:panelGroup>
                                    <h:inputText value="#{ManagedRegistroSolicitudSalidaAlmacen.solicitudesSalidaDetalle.cantidad}"  styleClass="inputText" id="cantidadSalidaAlmacen"  onkeypress="valNum(this)" onkeyup="calculaEquivalencia1(this)" /><%-- onkeyup="validaCantidadSalidaAlmacenDetalle1(this)" --%>
                                    <h:outputText value="#{ManagedRegistroSolicitudSalidaAlmacen.solicitudesSalidaDetalle.unidadesMedida.nombreUnidadMedida}" styleClass="outputText1" />
                                </h:panelGroup>

                                

                            </h:panelGrid>


                                <div align="center">

                                <a4j:commandButton styleClass="btn" value="Guardar"
                                action="#{ManagedSolicitudSalidaAlmacen.guardarEdicionCantidadMaterial_action}"
                                reRender="dataSalidasAlmacenDetalle"  onclick="javascript:Richfaces.hideModalPanel('panelModificarCantidadMaterial')"
                                  />

                                <input type="button" value="Cancelar" onclick="javascript:Richfaces.hideModalPanel('panelModificarCantidadMaterial')" class="btn" />
                                </div>
                        </h:panelGroup>
                        </a4j:form>
                        </div>
            </rich:modalPanel>
            
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

