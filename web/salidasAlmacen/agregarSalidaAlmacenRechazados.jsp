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
                    var elements=document.getElementById('form2:dataSalidaAlmacenDetalleIngreso');                    
                    var rowsElement=elements.rows;
                    var cantidadSalidaFisica = 0;
                    var totalImporteF=0;
                    for(var i=1;i<rowsElement.length;i++){
                        var cellsElement=rowsElement[i].cells;
                        var cel13=cellsElement[13];
                        var cantidadParcial=cel13.getElementsByTagName('input')[0];
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
                    if((parseFloat(tabla.rows[i].cells[6].getElementsByTagName("span")[0].innerHTML)>0.01))
                    {
                        alert("Debe de hacer la salida del material no disponible");
                        return false;
                    }
                }
                return true;
            }
       </script>
       <style type="text/css">
           .tablaExistencia
           {
               border-collapse: collapse;
               border:1px solid #C0C0C0;
               width: 100%;
           }
           .tablaExistencia td
           {
               font-family: Arial, Verdana, sans-serif;
               font-size: 11px;
               border : 1px solid #C0C0C0;
               background-color: #fff;
               padding:3px;
           }
           .tablaExistencia .header
           {
               background-color: #EAF0F8;
               
           }
           .tablaExistencia .center
           {
               text-align: center;
           }
           .tablaExistencia .right
           {
               text-align: right;
           }
           .existenciaValida
           {
               background-color:#ccfabe !important;
           }
       </style>
      

        </head>
        <body >
            <a4j:form id="form1">

                    <h:outputText value="#{ManagedRegistroSalidaAlmacen.cargarContenidoRegistroSalidaAlmacenRechazados}"/>
                    
                    <div align="center" >
                        <h:outputText value="#{ManagedRegistroSalidaAlmacen.mensajeInyectables}" style="font-size:22px;color:red" />
                    </div>

                <rich:panel style="width:100%"  headerClass="headerClassACliente">
                    <f:facet name="header">
                        <h:outputText value="<center>REGISTRO SALIDA ALMACEN</center>" escape="false" />
                    </f:facet>
                        <div align="center">
                                
                            <h:panelGrid columns="4" >
                                <h:outputText value="Nro Salida:" styleClass="outputText2" />
                                <h:outputText value="#{ManagedRegistroSalidaAlmacen.salidasAlmacen.nroSalidaAlmacen}" styleClass="outputText2" />

                                <h:outputText value="Area / Departamento:" styleClass="outputText2" />
                                <h:outputText value="#{ManagedRegistroSalidaAlmacen.salidasAlmacen.areasEmpresa.nombreAreaEmpresa}" styleClass="outputText2" />

                                <h:outputText value="Tipo de Salida:" styleClass="outputText2" />
                                <h:outputText value="#{ManagedRegistroSalidaAlmacen.salidasAlmacen.tiposSalidasAlmacen.nombreTipoSalidaAlmacen}" styleClass="outputText2" />
                                
                                <h:outputText value="Observaciones:" styleClass="outputText2" />
                                <h:inputTextarea  rows="5" cols="50" styleClass="outputText2" 
                                                  value="#{ManagedRegistroSalidaAlmacen.salidasAlmacen.obsSalidaAlmacen}" 
                                                  style="width:400px;"/>

                            </h:panelGrid>
                        
                        </rich:panel>
                        <br />
                        </div>
                        <div align="center">
                            <h:outputText value="Seleccione los Items de los cuales desea generar la salida:" styleClass="outputText2" />
                        <div style="overflow:auto;height:200px;width:80%;text-align:center">
                            <rich:dataTable value="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalleList}"
                                            var="data"
                                            id="dataSalidasAlmacenDetalle"
                                            onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                            onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                            headerClass="headerClassACliente"                                    
                                            style="width:100%" 
                                            binding="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalleDataTable}" >

                                <rich:column>
                                    <f:facet name="header">
                                        <h:outputText value=""  />
                                    </f:facet>
                                    <h:selectBooleanCheckbox value="#{data.checked}" />
                                </rich:column>

                                <rich:column>
                                    <f:facet name="header">
                                        <h:outputText value="Items Rechazados"  />
                                    </f:facet>
                                    <h:outputText  value="#{data.materiales.nombreMaterial}"/>
                                </rich:column>

                                <rich:column>
                                    <f:facet name="header">
                                        <h:outputText value="Cantidad Salida"  />
                                    </f:facet>
                                    <h:outputText value="#{data.cantidadSalidaAlmacen}"  />
                                </rich:column>

                                <rich:column>
                                    <f:facet name="header">
                                        <h:outputText value="Unid."   />
                                    </f:facet>
                                    <h:outputText value="#{data.unidadesMedida.abreviatura}"  />
                                </rich:column>
                                                               
                            </rich:dataTable>
                        </div>
                        <rich:messages layout="table" />

                        <br/>
                        <a4j:commandButton value="Guardar" styleClass="btn" id="buttonGuardar" rendered="#{ManagedRegistroSalidaAlmacen.administradorAlmacen}"
                            action="#{ManagedRegistroSalidaAlmacen.guardarSalidaAlmacenRechazados_action}"
                            onclick="if(confirm('Esta seguro de realizar la Salida de Almacen?')==false){return false;}else{verProgreso();}"
                            oncomplete="ocultarProgreso();alert('#{ManagedRegistroSalidaAlmacen.mensaje}');if(#{ManagedRegistroSalidaAlmacen.registradoCorrectamente}){location='navegadorSalidasAlmacen.jsf'}" timeout="7200"
                        />
                        <a4j:commandButton value="Cancelar" styleClass="btn" 
                            onclick="if(confirm('¿Esta seguro de CANCELAR la Edición de la Salida de Almacen?')==false){return false;}else{verProgreso();}"
                            oncomplete="ocultarProgreso(); location='../ingresosAlmacen/navegadorIngresosAlmacen.jsf'" 
                        />
                        <h:panelGrid>
                              <h:graphicImage url="../img/load.gif" id="progress" style="visibility:hidden" />
                        </h:panelGrid>
                    
                    </div>
               
            </a4j:form>
                
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

