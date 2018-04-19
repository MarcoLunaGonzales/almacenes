<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib prefix="a4j" uri="http://richfaces.org/a4j"%>
<%@taglib prefix="rich" uri="http://richfaces.org/rich"%>
<f:view>

    <html>
        <head>
            <title>SISTEMA</title>
            <link rel="STYLESHEET" type="text/css" href="../css/ventas.css" />
            <link rel="STYLESHEET" type="text/css" href="../css/chosen.css" />
            <script type="text/javascript" src="../js/general.js" ></script>
            <style type="text/css">
                .cabeceraDetalle{
                    background-color: #b9cadc !important;
                    background-image: none !important;
                }
                .cabeceraDetalle td{
                    border-color: #8895a2 !important;
                }
            </style>
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
                    var elements=document.getElementById('pDetalleItemSalida:form2:dataSalidaAlmacenDetalleIngreso');
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
           var cantidadDisponible =  obj.parentNode.parentNode.getElementsByTagName('td')[12].innerHTML;
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
               var col13 = obj.parentNode.parentNode.getElementsByTagName('td')[13];
               obj.value = col13.getElementsByTagName("input")[1].value;
               //alert(col10.getElementsByTagName("input")[1].value);
               return false;
           }
           
           return true;
       }
       
       function hallaCantidadSalidaFisica(){
                    var tabla = document.getElementById('pDetalleItemSalida:form2:dataSalidaAlmacenDetalleIngreso').getElementsByTagName("tbody")[0];
                    var cantidadSalidaFisica = 0;
                    var totalImporteF=0;
                    for(var i = 0 ; i < tabla.rows.length;i++){
                        var cantidadParcial = tabla.rows[i].cells[13].getElementsByTagName('input')[0];
                        if(cantidadParcial.type=='text'){
                            cantidadSalidaFisica=parseFloat(cantidadSalidaFisica)+parseFloat(cantidadParcial.value);
                        }
                    }
                    if(parseFloat(document.getElementById('form2:cantTotalSalidaFisica').innerHTML)<(Math.round(cantidadSalidaFisica*100)/100))
                    {
                        return false;
                    }
                    return true;
       }


       function calculaEquivalencia(obj){
           valorEquivalencia = document.getElementById("pAgregarItem:form5:valorEquivalencia").value;
           if(valorEquivalencia>0){
               document.getElementById("pAgregarItem:form5:cantidadEquivalencia").value = obj.value /valorEquivalencia;
           }
       }
       function calculaEquivalencia1(obj){
           valorEquivalencia = document.getElementById("form4:valorEquivalencia").value;
           if(valorEquivalencia>0){
               document.getElementById("form4:cantidadEquivalencia").value = obj.value /valorEquivalencia;
           }
       }
       function validaCantidadSalidaAlmacenDetalle(obj){
           var cantidadSalidaAlmacen = document.getElementById("pAgregarItem:form5:cantidadSalidaAlmacen").value;
           var cantidadDisponible = parseFloat(document.getElementById("pAgregarItem:form5:cantidadDisponible").innerHTML);
           if(cantidadSalidaAlmacen>cantidadDisponible){
               document.getElementById("pAgregarItem:form5:cantidadSalidaAlmacen").value = document.getElementById("pAgregarItem:form5:cantidadDisponible").innerHTML;
               calculaEquivalencia(obj);
               alert("la cantidad de salida de almacen no puede sobrepasar la cantidad disponible");               
               return false;
            }
           calculaEquivalencia(obj);
           return true;
       }



       function validaCantidadSalidaAlmacenDetalle1(obj){
           var cantidadSalidaAlmacen = document.getElementById("form4:cantidadSalidaAlmacen").value;
           var cantidadDisponible = parseFloat(document.getElementById("form4:cantidadDisponible").innerHTML);
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
           var elements=document.getElementById('pDetalleItemSalida:form2:dataSalidaAlmacenDetalleIngreso');
                    var rowsElement=elements.rows;
                    var cantidadSalidaFisica = 0;
                    var totalImporteF=0;
                    for(var i=1;i<rowsElement.length;i++){
                        var cellsElement=rowsElement[i].cells;
                        var cel13=cellsElement[13];
                        cel13.getElementsByTagName('input')[1].value = cel13.getElementsByTagName('input')[0].value ;
                    }
       }
       //inicio ale
        function onChangeValue()
        {
            var valor=document.getElementById("form1:tipoSalida");
            var button=document.getElementById("form1:butonOC");


            if((valor.value.toString())=='7')
            {
                button.style.visibility='visible';
            }
            else
            {
                button.style.visibility='hidden';
            }

        }
        function salidaNoPermitida()
        {
            if(document.getElementById("form1:codLoteProduccion").value!='' && document.getElementById("form1:codProducto").value =='0')
                {
                    alert('Debe seleccionar un producto para poder registrar la salida con lote');
                    return false;
                }
                if(document.getElementById("form1:codAreaEmpresa").value=='0')
                {
                    alert('Debe de seleccionar el Area/Departamento destino');
                    return false;
                }


           return true;
        }
        function verProgreso()
        {
            document.getElementById("form1:buttonGuardar").style.visibility='hidden';
            document.getElementById('form1:progress').style.visibility='visible';
        }
        function ocultarProgreso()
        {
            document.getElementById("form1:buttonGuardar").style.visibility='visible';
            document.getElementById('form1:progress').style.visibility='hidden';
        }
        onerror=function (){
        alert('existe un error al momento de cargar el registro, por favor recargue la pagina');
        }

        //final ale
        A4J.AJAX.onError = function(req,status,message){
            window.alert("Ocurrio un error: "+message);
            }
            A4J.AJAX.onExpired = function(loc,expiredMsg){
            if(window.confirm("Ocurrio un error al momento realizar la transaccion: "+expiredMsg+" location: "+loc)){
            return loc;
            } else {
            return false;
            }
            }
            function validarRegistroProducto()
            {
                if(!parseInt(document.getElementById("form1:codProducto").value)>0)
                {
                    alert('Debe seleccionar el producto');
                    return false;
                }
                return true;
            }
            function verificarCantidadReanalisis()
            {
                var tabla=document.getElementById("form1:dataSalidasAlmacenDetalle").getElementsByTagName("tbody")[0];
                for(var i=0;i<tabla.rows.length;i++)
                {
                    if((parseFloat(tabla.rows[i].cells[5].getElementsByTagName("span")[0].innerHTML)>0.01))
                    {
                        alert("Debe de hacer la salida del material no disponible");
                        return false;
                    }
                }
                return true;
            }
       </script>
       <style type="text/css">
           
       </style>
      

        </head>
        <body >
            <a4j:form id="form1">
                <div align="center">

                    <h:outputText value="#{ManagedRegistroSalidaAlmacen.cargarContenidoRegistroSalidaAlmacen}"/>
                    <h:outputText id="alertLote" style="font-size:22px;color:red" value="#{ManagedRegistroSalidaAlmacen.alertaSalidasLote}"  escape="false"  />
                    <br><br>
                        
                    <rich:panel id="panelEstadoCuentas" styleClass="panelBuscar" style="top:50px;left:50px;width:700px;">
                    <f:facet name="header">
                        <h:outputText value="<div   onmouseover=\"this.style.cursor='move'\" onmousedown=\"comienzoMovimiento(event, 'form1:panelEstadoCuentas');\"  >Buscar<div   style=\"margin-left:550px;\"   onclick=\"document.getElementById('form1:panelEstadoCuentas').style.visibility='hidden';document.getElementById('panelsuper').style.visibility='hidden';\" onmouseover=\"this.style.cursor='hand'\"   >Cerrar</div> </div> "
                              escape="false" />
                    </f:facet>
                    </rich:panel>
                    <div align="center" >
                        <h:outputText value="#{ManagedRegistroSalidaAlmacen.mensajeInyectables}" style="font-size:22px;color:red" />
                    </div>

                <rich:panel style="width:100%"  headerClass="headerClassACliente">
                            <f:facet name="header">
                                <h:outputText value="Registro de Salidas a Almacen"  />
                            </f:facet>
                        <h:panelGrid columns="4" >
                            <h:outputText value="Nro Salida:" styleClass="outputText2" />
                            <h:outputText value="#{ManagedRegistroSalidaAlmacen.salidasAlmacen.nroSalidaAlmacen}" styleClass="outputText1" />

                            <h:outputText value="Area / Departamento:" styleClass="outputText2" />
                            <h:selectOneMenu  styleClass="chosen" value="#{ManagedRegistroSalidaAlmacen.salidasAlmacen.areasEmpresa.codAreaEmpresa}" id="codAreaEmpresa" >
                                <f:selectItems value="#{ManagedRegistroSalidaAlmacen.areasEmpresaList}"  />
                                <a4j:support event="onchange" action="#{ManagedRegistroSalidaAlmacen.nroLote_change}" reRender="codAreaEmpresa,codMaquinaria"
                                            oncomplete="if(#{ManagedRegistroSalidaAlmacen.mensaje!=''}){alert('#{ManagedRegistroSalidaAlmacen.mensaje}');}" timeout="7200" />
                            </h:selectOneMenu>

                            <h:outputText value="Tipo de Salida:" styleClass="outputText2" />
                            <h:selectOneMenu  id="tipoSalida" styleClass="inputText" value="#{ManagedRegistroSalidaAlmacen.salidasAlmacen.tiposSalidasAlmacen.codTipoSalidaAlmacen}" onchange="onChangeValue()">
                                <f:selectItems value="#{ManagedRegistroSalidaAlmacen.tiposSalidasAlmacenList}"  />
                            </h:selectOneMenu>

                            <h:outputText value="Nro Lote:" styleClass="outputText2" />
                            <h:inputText value="#{ManagedRegistroSalidaAlmacen.salidasAlmacen.codLoteProduccion}" styleClass="inputText" id="codLoteProduccion"  >
                                <a4j:support event="onkeyup" action="#{ManagedRegistroSalidaAlmacen.nroLote_change}" reRender="codAreaEmpresa,producto,alertLote"
                                oncomplete="if(#{ManagedRegistroSalidaAlmacen.mensaje!=''}){alert('#{ManagedRegistroSalidaAlmacen.mensaje}');}if(#{ManagedRegistroSalidaAlmacen.productoHabilitado==true}){document.getElementById('form1:codProducto').disabled=true;}else{document.getElementById('form1:codProducto').disabled=false;}" status="none" timeout="7200" />
                                <%--
                                <a4j:support event="onblur" action="#{ManagedRegistroSalidaAlmacen.nroLote_onblur}" reRender="producto"
                                oncomplete="if(#{ManagedRegistroSalidaAlmacen.mensaje!=''}){alert('#{ManagedRegistroSalidaAlmacen.mensaje}');}" status="none" />
                                final ale validacion--%>
                            </h:inputText>
                            
                            <h:outputText value="Producto:" styleClass="outputText2" rendered="#{ManagedRegistroSalidaAlmacen.usuario.almacenesGlobal.codAlmacen != '14'}" />
                            <h:outputText value="Maquinaria:" styleClass="outputText2"  rendered="#{ManagedRegistroSalidaAlmacen.usuario.almacenesGlobal.codAlmacen eq '14'}" />
                            <h:panelGroup id="producto">
                                <h:selectOneMenu  styleClass="inputText" 
                                                  value="#{ManagedRegistroSalidaAlmacen.salidasAlmacen.componentesProd.codCompprod}" 
                                                  id="codProducto" 
                                                  rendered="#{ManagedRegistroSalidaAlmacen.usuario.almacenesGlobal.codAlmacen != '14'}" 
                                                  style="width:400px;">
                                    <f:selectItems value="#{ManagedRegistroSalidaAlmacen.componentesProdList}"  />
                                    <a4j:support event="onchange" action="#{ManagedRegistroSalidaAlmacen.producto_changed}" reRender="presentacionProducto,producto" oncomplete="if(#{ManagedRegistroSalidaAlmacen.mensaje!=''}){alert('#{ManagedRegistroSalidaAlmacen.mensaje}');}" timeout="7200" />
                                </h:selectOneMenu>
                                <a4j:commandLink  action="#{ManagedRegistroSalidaAlmacen.detallarMateriales_action}" reRender="dataSalidasAlmacenDetalle" timeout="7200" rendered="#{ManagedRegistroSalidaAlmacen.usuario.almacenesGlobal.codAlmacen != '14'}">
                                    <h:graphicImage url="../img/actualizar1.jpg" />
                                </a4j:commandLink>
                                
                                <h:selectOneMenu  styleClass="inputText" value="#{ManagedRegistroSalidaAlmacen.salidasAlmacen.maquinaria.codMaquina}"  id="codMaquinaria" rendered="#{ManagedRegistroSalidaAlmacen.usuario.almacenesGlobal.codAlmacen eq '14'}"  >
                                    <f:selectItem itemValue="0" itemLabel="-NINGUNO-" />
                                    <f:selectItems value="#{ManagedRegistroSalidaAlmacen.maquinariasList}"  />
                                </h:selectOneMenu>
                            </h:panelGroup>
                            
                            <h:outputText value="Presentacion:" styleClass="tituloCampo1" rendered="#{ManagedRegistroSalidaAlmacen.usuario.almacenesGlobal.codAlmacen != '14'}"  />
                            <h:selectOneMenu  styleClass="inputText" value="#{ManagedRegistroSalidaAlmacen.salidasAlmacen.presentacionesProducto.codPresentacion}"  id="presentacionProducto"  rendered="#{ManagedRegistroSalidaAlmacen.usuario.almacenesGlobal.codAlmacen != '14'}" >
                                <f:selectItems value="#{ManagedRegistroSalidaAlmacen.presentacionesList}"  />
                            </h:selectOneMenu>

                            

                            <h:outputText value="Observaciones:" styleClass="outputText2" />
                            <h:inputTextarea  rows="5" cols="50" styleClass="outputText2" 
                                              value="#{ManagedRegistroSalidaAlmacen.salidasAlmacen.obsSalidaAlmacen}" 
                                              style="width:400px;"/>

                            <h:outputText value="Orden de Trabajo:" styleClass="outputText2" />
                            <h:inputText value="#{ManagedRegistroSalidaAlmacen.salidasAlmacen.ordenTrabajo}" styleClass="inputText" />

                            
                             
                          
                        </h:panelGrid>
                    </rich:panel>
                    <center>
                        <h:panelGroup  rendered="#{!ManagedRegistroSalidaAlmacen.visible_salidaProduccion}" 
                                >
                             <a4j:commandButton value="Agregar Item" 
                                                styleClass="btn"
                                                action="#{ManagedRegistroSalidaAlmacen.agregarItem_action}"
                                                oncomplete="Richfaces.showModalPanel('panelAgregarItem')"
                                                reRender="contenidoAgregarItem"
                                                timeout="7200"
                                               
                             />

                             <h:panelGroup>
                                 
                             <a4j:commandButton action="#{ManagedRegistroSalidaAlmacen.cargarOC}" 
                                                style="visibility: hidden;"                                                 
                                                id="butonOC" 
                                                value="Asociar O C" 
                                                styleClass="btn"
                                                oncomplete="Richfaces.showModalPanel('panelAsociarOC')" 
                                                reRender="contenidoAsociarOC" 
                                                timeout="7200" />
                            </h:panelGroup>
                        </h:panelGroup>
                        </center>
                    <br />
                        <div style="overflow:auto;height:200px;width:80%;text-align:center;border:1px solid #ccc">
                       <rich:dataTable value="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalleList}"
                                    var="data"
                                    id="dataSalidasAlmacenDetalle"
                                    onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                    onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                    headerClass="headerClassACliente"      
                                    rowKeyVar="index"
                                    style="width:100%" 
                                    binding="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalleDataTable}" >
                       
                        <rich:column style="background-color: #{data.colorFila}" >
                            <f:facet name="header">
                                <h:outputText value=""  />
                            </f:facet>
                            <h:outputText value=""  />
                        </rich:column>

                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value="Item"  />
                            </f:facet>
                            <h:outputText  value="#{data.materiales.nombreMaterial}"/>
                        </rich:column>
                       
                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value="Cantidad"  />
                            </f:facet>
                            <h:outputText value="#{data.cantidadSalidaAlmacen}"  />
                        </rich:column>
                      
                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value="Unid."   />
                            </f:facet>
                            <h:outputText value="#{data.unidadesMedida.abreviatura}"  />
                        </rich:column>
                        
                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value="Cant. Disp."   />
                            </f:facet>
                            <h:outputText value="#{data.cantidadDisponible}"  />
                        </rich:column>
                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value="Cant.<br>no disponible" escape="false"   />
                            </f:facet>
                            <h:outputText value="#{data.cantidadExistenciaReanalisis}" styleClass="outputText2">
                                <f:convertNumber pattern="####.##" locale="en"/>
                            </h:outputText>
                        </rich:column>
                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value="Etiquetas"   />
                            </f:facet>
                            <a4j:commandLink onclick="Richfaces.showModalPanel('panelSalidaAlmacenDetalleEstado')"
                                             styleClass="btn"
                                             action="#{ManagedRegistroSalidaAlmacen.verDetalleEtiquetasSalidaAlmacen_action}" reRender="contenidoSalidaAlmacenDetalleEstado"
                                            oncomplete="cantAuxSalida();" timeout="7200" >
                                    <h:outputText value="Etiquetas"/>
                                    <h:graphicImage url="../img/areasdependientes.png">
                                    </h:graphicImage>
                            </a4j:commandLink>
                            
                        </rich:column>
                        <rich:column rendered="#{not ManagedRegistroSalidaAlmacen.visible_salidaProduccion}">
                            <f:facet name="header">
                                <h:outputText value="Editar"/>
                            </f:facet>
                            <a4j:commandButton value="Editar" 
                                                styleClass="btn"
                                                oncomplete="Richfaces.showModalPanel('panelModificarCantidadMaterial')"
                                                action="#{ManagedRegistroSalidaAlmacen.editarCantidadSalidaAlmacenAction}" 
                                                reRender="contenidoModificarCantidadMaterial"
                                                timeout="7200" >
                                <f:setPropertyActionListener target="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalle}"
                                                             value="#{data}"/>
                            </a4j:commandButton>
                        </rich:column>
                        <rich:column rendered="#{not ManagedRegistroSalidaAlmacen.visible_salidaProduccion}">
                            <f:facet name="header">
                                <h:outputText value="Eliminar"/>
                            </f:facet>
                            <a4j:commandButton value="Eliminar"
                                               styleClass="btn"
                                               action="#{ManagedRegistroSalidaAlmacen.eliminarDetalleSalidaAlmacenAction}"
                                               reRender="dataSalidasAlmacenDetalle" timeout="7200">
                                <f:setPropertyActionListener target="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalle}"
                                                             value="#{data}"/>
                            </a4j:commandButton>
                        </rich:column>

                    </rich:dataTable>
                    </div>
                    <rich:messages layout="table" />
                    <br>
                    <a4j:commandButton value="Guardar" styleClass="btn" id="buttonGuardar" rendered="#{ManagedRegistroSalidaAlmacen.administradorAlmacen}"
                                        action="#{ManagedRegistroSalidaAlmacen.guardarSalidaAlmacen_action}"
                                        onclick="if((verificarCantidadReanalisis()&&salidaNoPermitida()&&confirm('Esta seguro de realizar la Salida de Almacen?'))==false){return false;}else{verProgreso();}"
                                        oncomplete="mostrarMensajeTransaccionEvento(function(){verProgreso();redireccionar('navegadorSalidasAlmacen.jsf');})"/>
                    <h:panelGrid>
                          <h:graphicImage url="../img/load.gif" id="progress" style="visibility:hidden" />
                     </h:panelGrid>

            </a4j:form>

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
                                <h:outputText value="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalle.materiales.nombreMaterial}" styleClass="outputText1" />

                                <h:outputText value="Cantidad Disponible" styleClass="outputText1" />
                                <h:outputText value="::" styleClass="outputText1" />
                                <h:outputText value="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalle.cantidadDisponible}" id="cantidadDisponible"  styleClass="outputText1" />

                                <h:outputText value="Cantidad de Salida" styleClass="outputText1" />
                                <h:outputText value="::" styleClass="outputText1" />
                                <h:panelGroup>
                                    <h:inputText value="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalle.cantidadSalidaAlmacen}" 
                                                 styleClass="inputText" id="cantidadSalidaAlmacen"
                                                 converterMessage="Debe ingresar un numero valido"
                                                 onkeypress="valNum(this)">
                                        <f:validator validatorId="validatorDoubleRange"/>
                                        <f:attribute name="minimum" value="0.01"/>
                                        <f:attribute name="maximum" value="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalle.cantidadDisponible}"/>
                                        <f:attribute name="disable" value="#{(empty param['form4:btnGuardar'])}"/>
                                    </h:inputText>
                                    <h:message for="cantidadSalidaAlmacen" styleClass="message"/>
                                    <h:outputText value="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalle.equivalencias.unidadesMedida1.nombreUnidadMedida}" styleClass="outputText1" />
                                </h:panelGroup>

                                <h:outputText value="Equivalente" styleClass="outputText1" />
                                <h:outputText value="::" styleClass="outputText1" />
                                <h:panelGroup>
                                    <h:inputText value="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalle.cantidadSalidaAlmacenEquivalente}" readonly="true" id="cantidadEquivalencia" styleClass="inputText" />
                                <h:outputText value="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalle.equivalencias.unidadesMedida2.nombreUnidadMedida}" styleClass="outputText1" />
                                <h:inputHidden value="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalle.equivalencias.valorEquivalencia}" id="valorEquivalencia" />
                                </h:panelGroup>
                                
                                
                           
                            </h:panelGrid>
   

                                <div align="center">
                                    <a4j:commandButton styleClass="btn" value="Guardar" id="btnGuardar"
                                                        action="#{ManagedRegistroSalidaAlmacen.guardarEdicionCantidadMaterial_action}"
                                                        reRender="contenidoModificarCantidadMaterial,dataSalidasAlmacenDetalle" 
                                                        oncomplete="if('#{facesContext.maximumSeverity}'.length == 0){Richfaces.hideModalPanel('panelModificarCantidadMaterial')}"
                                                        timeout="7200"
                                    />
                                <input type="button" value="Cancelar" onclick="Richfaces.hideModalPanel('panelModificarCantidadMaterial')" class="btn" />
                                </div>
                        </h:panelGroup>
                        </a4j:form>
                        </div>
            </rich:modalPanel>


             
            <%--inicio ale--%>
             <rich:modalPanel id="panelAsociarOC" minHeight="380"  minWidth="980"
                                     height="380" width="990"
                                     zindex="200"
                                     headerClass="headerClassACliente"
                                     resizeable="false" style="overflow :auto" >
                        <f:facet name="header">
                            <h:outputText value="Asociar Salida Orden de Compra"/>
                        </f:facet>
                        <a4j:form id="formAsociar">
                        <h:panelGroup id="contenidoAsociarOC">
                            <h:panelGrid columns="9">
                                <h:outputText value="Proovedor" styleClass="outputText1"  />
                                <h:outputText value="::" styleClass="outputText1" />
                                <h:selectOneMenu style="width:210px" value="#{ManagedRegistroSalidaAlmacen.filtroOrdenCompra.proveedores.codProveedor}" styleClass="inputText" >
                                    <f:selectItems value="#{ManagedRegistroSalidaAlmacen.proveedoresList}" />

                                </h:selectOneMenu>
                                <h:outputText  value="Representante" styleClass="outputText1" />
                                <h:outputText value="::" styleClass="outputText1" />
                                <h:selectOneMenu style="width:210px" value="#{ManagedRegistroSalidaAlmacen.filtroOrdenCompra.representantes.codRepresentante}" styleClass="inputText" >
                                    <f:selectItems value="#{ManagedRegistroSalidaAlmacen.representantesList}" />

                                </h:selectOneMenu>
                                <h:outputText value="Responsable de compras" styleClass="outputText1" />
                                <h:outputText value="::" styleClass="outputText1" />
                                <h:selectOneMenu style="width:210px" value="#{ManagedRegistroSalidaAlmacen.filtroOrdenCompra.responsableCompras.codPersonal}" styleClass="inputText" >
                                    <f:selectItems value="#{ManagedRegistroSalidaAlmacen.personalList}" />

                                </h:selectOneMenu>
                                   <h:outputText value="Fecha Inicio " styleClass="outputText1" />
                                     <h:outputText value="::" styleClass="outputText1" />
                                     <rich:calendar  datePattern="dd/MM/yyyy" styleClass="inputText" zindex="200" value="#{ManagedRegistroSalidaAlmacen.fechaInicio}" id="fechaInicioOC" enableManualInput="true" oninputblur="if(this.value!=''){valFecha(this)}else{return true;}" >

                                    </rich:calendar>

                              <h:outputText value="Fecha Final " styleClass="outputText1" />
                                <h:outputText value="::" styleClass="outputText1" />
                                <rich:calendar  datePattern="dd/MM/yyyy" styleClass="inputText" zindex="200" value="#{ManagedRegistroSalidaAlmacen.fechaFinal}" id="fechaFinal" enableManualInput="true" oninputblur="if(this.value!=''){valFecha(this)}else{return true;}" >

                                    </rich:calendar>





                                <h:outputText value="Capitulo" styleClass="outputText1" />
                                <h:outputText value="::" styleClass="outputText1" />
                                <h:selectOneMenu style="width:210px" value="#{ManagedRegistroSalidaAlmacen.codCapitulo}" styleClass="inputText" id="capitulos" >
                                    <f:selectItems value="#{ManagedRegistroSalidaAlmacen.capitulosList}"  />
                                    <a4j:support event="onchange" action="#{ManagedRegistroSalidaAlmacen.grupos_OC}"  reRender="gruposOC" timeout="7200"/>
                                </h:selectOneMenu>
                                   <h:outputText value="Grupo" styleClass="outputText1" />
                                <h:outputText value="::" styleClass="outputText1" />
                                <h:selectOneMenu style="width:210px" value="#{ManagedRegistroSalidaAlmacen.codGrupo}" styleClass="inputText" id="gruposOC" >
                                    <f:selectItems value="#{ManagedRegistroSalidaAlmacen.gruposList}"  />
                                    <a4j:support action="#{ManagedRegistroSalidaAlmacen.materiales_OC}" event="onchange" reRender="materialesOC" timeout="7200"/>
                                </h:selectOneMenu>
                                   <h:outputText value="Material" styleClass="outputText1" />
                                <h:outputText value="::" styleClass="outputText1" />
                                <h:selectOneMenu style="width:210px" value="#{ManagedRegistroSalidaAlmacen.codMaterial}" styleClass="inputText" id="materialesOC" >
                                    <f:selectItems value="#{ManagedRegistroSalidaAlmacen.materialesList}"  />
                                </h:selectOneMenu>

                            </h:panelGrid>
                            <center>
                                <a4j:commandButton value="buscar" action="#{ManagedRegistroSalidaAlmacen.cargarOrdenesCompra}" styleClass="btn" reRender="dataOrdenCompra,dataDetalleOrdenCompra,controles" timeout="7200" />
                            </center>
                            <div style="overflow:auto;height:190px;width:100%;text-align:center">
                            <rich:dataTable value="#{ManagedRegistroSalidaAlmacen.ordenesCompraList}" var="data"
                                    id="dataOrdenCompra"
                                    onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                    onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                    headerClass="headerClassACliente"
                                    binding="#{ManagedRegistroSalidaAlmacen.ordenesCompraDataTable}"
                                     >
                                <rich:column>
                                <f:facet name="header">
                                    <h:outputText value=""/>
                                </f:facet>
                                <h:selectBooleanCheckbox value="#{data.checked}"/>
                                </rich:column>
                              <rich:column>
                                <f:facet name="header">
                                    <h:outputText value="Fecha Emisión"/>
                                </f:facet>
                                <h:outputText value="#{data.fechaEmision}" styleClass="outputText1">
                                    <f:convertDateTime pattern="dd/MM/yyyy"/>
                                </h:outputText>
                              </rich:column>
                              <rich:column>
                                <f:facet name="header">
                                    <h:outputText value="Nro Orden de Compra"/>
                                </f:facet>
                                <a4j:commandLink  styleClass="outputText1" value="#{data.nroOrdenCompra}" action="#{ManagedRegistroSalidaAlmacen.cargarDetalleOrdenCompra}" reRender="dataDetalleOrdenCompra" timeout="7200" >

                                </a4j:commandLink>
                              </rich:column>
                               <rich:column>
                                <f:facet name="header">
                                    <h:outputText value="Proovedor"/>
                                </f:facet>
                                <h:outputText value="#{data.proveedores.nombreProveedor}" styleClass="outputText1"/>
                              </rich:column>

                              <rich:column>
                                <f:facet name="header">
                                    <h:outputText value="Representante"/>
                                </f:facet>
                                <h:outputText value="#{data.representantes.nombreRepresentante}" styleClass="outputText1"/>
                              </rich:column>

                              <rich:column>
                                <f:facet name="header">
                                    <h:outputText value="Pais"/>
                                </f:facet>
                                <h:outputText value="#{data.proveedores.paises.nombrePais}" styleClass="outputText1"/>
                              </rich:column>

                              <rich:column>
                                <f:facet name="header">
                                    <h:outputText value="División"/>
                                </f:facet>
                                <h:outputText value="#{data.divisionCompras.nombreDivision}" styleClass="outputText1"/>
                              </rich:column>
                              <rich:column>
                                <f:facet name="header">
                                    <h:outputText value="Tipo de Compra"/>
                                </f:facet>
                                <h:outputText value="#{data.tiposCompra.nombreTipoCompra}" styleClass="outputText1"/>
                              </rich:column>
                              <rich:column>
                                <f:facet name="header">
                                    <h:outputText value="Responsable de Compra"/>
                                </f:facet>
                                <h:outputText value="#{data.responsableCompras.nombrePersonal}" styleClass="outputText1"/>
                              </rich:column>
                              <rich:column>
                                <f:facet name="header">
                                    <h:outputText value="Observaciones"/>
                                </f:facet>
                                <h:outputText value="#{data.obsOrdenCompra}" styleClass="outputText1"/>
                              </rich:column>



                              </rich:dataTable>
                            </div>
                              <center>
                              <h:panelGrid columns="2"  width="50" id="controles">
                                  <a4j:commandLink  action="#{ManagedRegistroSalidaAlmacen.atras_action}"   rendered="#{ManagedRegistroSalidaAlmacen.begin!='1'}" reRender="dataOrdenCompra,controles,dataDetalleOrdenCompra" timeout="7200" >
                                <h:graphicImage url="../img/previous.gif"  style="border:0px solid red"   alt="PAGINA ANTERIOR"  />
                            </a4j:commandLink>
                            <a4j:commandLink  action="#{ManagedRegistroSalidaAlmacen.siguiente_action}"  rendered="#{ManagedRegistroSalidaAlmacen.cantidadfilas>='5'}" reRender="dataOrdenCompra,controles,dataDetalleOrdenCompra" timeout="7200" >
                                <h:graphicImage url="../img/next.gif"  style="border:0px solid red"  alt="PAGINA SIGUIENTE" />
                            </a4j:commandLink>
                                </h:panelGrid>
                                </center>

                                <center>
                               <div style="overflow:auto;height:90px;width:100%;text-align:center">
                                <rich:dataTable value="#{ManagedRegistroSalidaAlmacen.ordenesCompraDetalleList}" var="data1"
                                    id="dataDetalleOrdenCompra"
                                    onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                    onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                    headerClass="headerClassACliente"
                                     >

                                 <rich:column>
                                  <f:facet name="header">
                                        <h:outputText value="Item"/>
                                   </f:facet>
                                  <h:outputText value="#{data1.materiales.nombreMaterial}" styleClass="outputText1"/>
                                  </rich:column>
                                   <rich:column>
                                  <f:facet name="header">
                                        <h:outputText value="Cantidad"/>
                                   </f:facet>
                                  <h:outputText value="#{data1.cantidadNeta}" styleClass="outputText1"/>
                                  </rich:column>
                                  <rich:column>
                                  <f:facet name="header">
                                        <h:outputText value="Unidad"/>
                                   </f:facet>
                                  <h:outputText value="#{data1.unidadesMedida.abreviatura}" styleClass="outputText1"/>
                                  </rich:column>
                                  <rich:column>
                                  <f:facet name="header">
                                        <h:outputText value="Precio Unitario"/>
                                   </f:facet>
                                  <h:outputText value="#{data1.precioUnitario}" styleClass="outputText1"/>
                                  </rich:column>
                                  <rich:column>
                                  <f:facet name="header">
                                        <h:outputText value="Subtotal"/>
                                   </f:facet>
                                  <h:outputText value="#{data1.precioTotal}" styleClass="outputText1"/>
                                  </rich:column>
                                  <rich:column>
                                  <f:facet name="header">
                                        <h:outputText value="Descripción"/>
                                   </f:facet>
                                  <h:outputText value="#{data1.descripcion}" styleClass="outputText1"/>
                                  </rich:column>

                            </rich:dataTable>
                            </div>
                            </center>
                            <div align="center">

                                <a4j:commandButton styleClass="btn" value="Aceptar" action="#{ManagedRegistroSalidaAlmacen.guardarAsociacionOC}"
                                oncomplete="javascript:Richfaces.hideModalPanel('panelAsociarOC');" onclick="if(editarItem('formAsociar:dataOrdenCompra')==false){return false}" timeout="7200"
                                             />
                                <input type="button" value="Cancelar" onclick="javascript:Richfaces.hideModalPanel('panelAsociarOC')" class="btn" />
                                </div>
                        </h:panelGroup>
                        </a4j:form>
            </rich:modalPanel>
        <a4j:include viewId="panelAgregarItemSalida.jsp" id="pAgregarItem"/>
        <a4j:include viewId="panelDetalleItemSalida.jsp" id="pDetalleItemSalida"/>
        <a4j:include viewId="/panelProgreso.jsp"/>
        <a4j:include viewId="/message.jsp"/>
        <a4j:loadScript src="/js/chosen.js" />
        <script type="text/javascript">
            cargarChosen();
        </script>
        </body>
    </html>

</f:view>


    