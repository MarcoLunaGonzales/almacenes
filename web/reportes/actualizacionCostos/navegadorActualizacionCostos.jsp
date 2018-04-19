
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib prefix="a4j" uri="http://richfaces.org/a4j"%>
<%@taglib prefix="rich" uri="http://richfaces.org/rich"%>
<%@taglib prefix="ws" uri="http://isawi.org/ws"%>
<f:view>
    
    <html>
        <head>
            <title>SISTEMA</title>
            <link rel="STYLESHEET" type="text/css" href="../../css/ventas.css" />
            <link rel="STYLESHEET" type="text/css" href="../../css/chosen.css" />
            <script type="text/javascript" src="../../js/general.js" ></script>
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
                    window.open(url,'detalle'+Math.round((Math.random()*1000)),'top=50,left=200,width=800,height=500,scrollbars=1,resizable=1');
        }
        function seleccionarTodo(){
       //alert('entro');
       var seleccionar_todo=document.getElementById('form1:seleccionar_todo');
       var elements=document.getElementById('form1:dataMateriales');

       if(seleccionar_todo.checked==true){
         //alert('entro por verdad');
            var rowsElement=elements.rows;
            for(var i=1;i<rowsElement.length;i++){
                var cellsElement=rowsElement[i].cells;
                var cel=cellsElement[0];
                if(cel.getElementsByTagName('input')[0].type=='checkbox'){
                    cel.getElementsByTagName('input')[0].checked=true;
                }
            }
       }
       else
       {//alert('entro por false');
            var rowsElement=elements.rows;
            for(var i=1;i<rowsElement.length;i++){
                var cellsElement=rowsElement[i].cells;
                var cel=cellsElement[0];
                if(cel.getElementsByTagName('input')[0].type=='checkbox'){
                    cel.getElementsByTagName('input')[0].checked=false;
                }
            }

       }
       return true;
    }
    //inicio ale barra progreso
		var myVar=null;
        var cantidadProductos=0;
        function terminarProgressBar()
        {
            clearInterval(myVar);
            document.getElementById('imgCargar').style.visibility='hidden';
            document.getElementById('imgNormal').style.visibility='visible';

        }
        function iniciarProgressBar()
        {
            var tabla=document.getElementById("form1:dataMateriales");
            cantidadProductos=0;
            for(var fila=1;fila<tabla.rows.length;fila++)
            {

                if(tabla.rows[fila].cells[0].getElementsByTagName('input')[0].checked==true)
                 {
                     cantidadProductos++;
                 }
            }
            myVar=setInterval(function(){myTimer()},60000);
            document.getElementById('imgCargar').style.visibility='visible';
            document.getElementById('imgNormal').style.visibility='hidden';
            document.getElementById('imgBar').style.width='0px';
            document.getElementById('form1:cantidadProcesadaInput').innerHTML=0;
        }
        function myTimer()
        {
            mostrarCantidad();
            var cantidadProcesada=parseInt(document.getElementById('form1:cantidadProcesadaInput').innerHTML);
            var porciento=parseInt((cantidadProcesada/cantidadProductos)*100);
            document.getElementById('form1:porcientoProcesado').innerHTML= porciento+' %';
            document.getElementById('imgBar').style.width=(porciento*3)+'px';
        }
        function onLoad(){
            document.getElementById("form1:mensajeNotificacion").value = "";
            //onload="onLoad()"
        }
       </script>
        </head>
        <body  >
            <a4j:form id="form1">
                <div align="center">
                    <h:outputText value="#{ManagedActualizacionCostos.cargarActualizacionCostos}"/>
                    
                         <rich:panel style="width:80%" headerClass="headerClassACliente">
                      
                            <f:facet name="header">
                                <h:outputText value=" Actualizacion de Costos "/>
                            </f:facet>
                            
                             <h:panelGrid columns="6" >

                                            <h:outputText value="Capitulo" styleClass="outputText1" />
                                            <h:outputText value="::" styleClass="outputText1" />
                                            <h:selectManyListbox value="#{ManagedActualizacionCostos.codCapitulos}" styleClass="outputText2" size="10" >
                                                <f:selectItems value="#{ManagedActualizacionCostos.capitulosList}" />
                                                <a4j:support action="#{ManagedActualizacionCostos.capitulos_change1}" event="onchange" reRender="codGrupos"  />
                                            </h:selectManyListbox>


                                            <h:outputText value="Grupo" styleClass="outputText1" />
                                            <h:outputText value="::" styleClass="outputText1" />
                                            <h:selectManyListbox value="#{ManagedActualizacionCostos.codGrupos}" styleClass="outputText2" size="10" id="codGrupos" >
                                                <f:selectItems value="#{ManagedActualizacionCostos.gruposList}" />
                                            </h:selectManyListbox>

                                            <h:outputText value="Item" styleClass="outputText1" />
                                            <h:outputText value="::" styleClass="outputText1" />
                                            <h:inputText value="#{ManagedActualizacionCostos.materiales.nombreMaterial}" style="text-transform:uppercase;color:blue;" size="60" styleClass="inputText" />
                                            
                                            <h:outputText value="" styleClass="outputText1" />
                                            <h:outputText value="" styleClass="outputText1" />
                                            <a4j:commandButton value="Buscar"  styleClass="btn"
                                                                action="#{ManagedActualizacionCostos.buscarMaterial_action1}"
                                                                reRender="dataMateriales" />


                                             <h:outputText value="Fecha" styleClass="outputText1" />
                                            <h:outputText value="::" styleClass="outputText1" />

                                            <h:panelGroup>
                                                <h:panelGroup>
                                                    <h:inputText styleClass="outputText2" value="#{ManagedActualizacionCostos.fechaInicio}"   id="f_inicio" >
                                                        <f:convertDateTime pattern="dd/MM/yyyy" />
                                                    </h:inputText>
                                                    <h:graphicImage url="../../img/fecha.bmp"  id="imagenFinicio" />
                                                    <h:outputText value="<DLCALENDAR tool_tip=\"Seleccione la Fecha\"  daybar_style=\"background-color: DBE1E7;font-family: verdana; color:000000;\"    navbar_style=\"background-color: 7992B7; color:ffffff;\"  input_element_id=\"form1:f_inicio\" click_element_id=\"form1:imagenFinicio\"></DLCALENDAR>"  escape="false"  />
                                                </h:panelGroup>

                                                <h:panelGroup >
                                                    <h:inputText  styleClass="outputText2" value="#{ManagedActualizacionCostos.fechaFinal}" id="f_final">
                                                        <f:convertDateTime pattern="dd/MM/yyyy" />
                                                    </h:inputText>
                                                    <h:graphicImage url="../../img/fecha.bmp"  id="imagenFFinal" />
                                                    <h:outputText value="<DLCALENDAR tool_tip=\"Seleccione la Fecha\"  daybar_style=\"background-color: DBE1E7;font-family: verdana; color:000000;\"    navbar_style=\"background-color: 7992B7; color:ffffff;\"  input_element_id=\"form1:f_final\" click_element_id=\"form1:imagenFFinal\"></DLCALENDAR>"  escape="false"  />
                                                </h:panelGroup>
                                             </h:panelGroup>

                                            <h:outputText value="" styleClass="outputText1" />
                                            <h:outputText value="" styleClass="outputText1" />
                                            <h:outputText value="" styleClass="outputText1" />
                                            <h:outputText value="" styleClass="outputText1" />
                                            <h:outputText value="" styleClass="outputText1" />

                                            <h:panelGroup>
                                                <a4j:commandButton value="Cerrar Transacciones " style="color:blue;padding: 4px;border:1px solid navy" action="#{ManagedActualizacionCostos.cerrarTransaccionesFecha_Action}"
                                                oncomplete="alert('#{ManagedActualizacionCostos.mensaje}')" styleClass="btn"/>
                                                <a4j:commandButton value="Abrir Transacciones " style="color:blue;padding: 4px;border:1px solid navy" action="#{ManagedActualizacionCostos.abrirTransaccionesFecha_Action}"
                                                oncomplete="alert('#{ManagedActualizacionCostos.mensaje}')" styleClass="btn"/>
                                                <h:outputText id="mensajeNotificacion" value="#{ManagedActualizacionCostos.mensaje}" style="color:red;width:200px" />
                                            </h:panelGroup>
                           </h:panelGrid>
                   </rich:panel>

                   <h:selectBooleanCheckbox id="seleccionar_todo"  onclick="seleccionarTodo();"/>
                   <h:outputText styleClass="outputTextTitulo"  value="Seleccionar Todos" />
                   <div style="overflow:auto;text-align:center;height:150px;width:85%;border:1px solid #ccc" id="main">

                    <rich:dataTable value="#{ManagedActualizacionCostos.materialesList}" var="data"
                                    id="dataMateriales"
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
                                <h:outputText value="Material"  />
                            </f:facet>
                            <h:outputText value="#{data.nombreMaterial}"/>
                        </h:column>

                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Grupo"  />
                            </f:facet>
                            <h:outputText value="#{data.grupos.nombreGrupo}"  />
                        </h:column>
                        
                        <h:column>
                            <f:facet name="header">
                                <h:outputText value="Capitulo"  />
                            </f:facet>
                            <h:outputText value="#{data.grupos.capitulos.nombreCapitulo}"  />
                        </h:column>

                     

                    </rich:dataTable>
                    </div>
                    <a4j:jsFunction name="mostrarCantidad" reRender="cantidadProcesadaInput"/>
                   <h:outputText value="#{ManagedActualizacionCostos.cantidadProcesada}" id="cantidadProcesadaInput" style="visibility:hidden"/>
                   <br>

                   <%--a4j:commandButton value="Actualizar Costos"  styleClass="btn" onclick="iniciarProgressBar()"
                    action="#{ManagedActualizacionCostos.actualizarCostos_action}"
                    reRender="panelContenidoActualizacion" oncomplete = "if('#{ManagedActualizacionCostos.mensaje}'!=''){alert('#{ManagedActualizacionCostos.mensaje}');terminarProgressBar();}else{alert('Se actualizaron los costos');terminarProgressBar();}" /--%>
                    
                    <h:commandButton value="Actualizar Costos" style="color:blue;padding: 6px;border:1px solid navy"  styleClass="btn"
                    action="#{ManagedActualizacionCostos.actualizarCostos_action}" />&nbsp;&nbsp;


                    <a4j:commandButton value="Actualizar Costos Traspasos" style="color:blue;padding: 6px;border:1px solid navy"  styleClass="btn"
                    action="#{ManagedActualizacionCostos.actualizarCostos_actionTraspasos}"
                    reRender="panelContenidoActualizacion" />
                    
                    <%--a4j:commandButton value="Actualizar Costos"  styleClass="btn"
                    action="#{ManagedActualizacionCostos.actualizarCostos_action}"
                    reRender="panelContenidoActualizacion" oncomplete = "if('#{ManagedActualizacionCostos.mensaje}'!=''){alert('#{ManagedActualizacionCostos.mensaje}')}else{alert('Se actualizaron los costos')}" /--%>
                    <a4j:status id="statusPeticion"
                                onstart="Richfaces.showModalPanel('ajaxLoadingModalBox',{width:100, top:100})"
                                onstop="Richfaces.hideModalPanel('ajaxLoadingModalBox')">
                    </a4j:status>

                    <%--rich:modalPanel id="ajaxLoadingModalBox" minHeight="100"
                                     minWidth="200" height="80" width="400" zindex="100" onshow="window.focus();">

                        <div align="center">
                            <h:graphicImage value="../../img/load2.gif" />
                        </div>
                    </rich:modalPanel--%>
                    <rich:modalPanel id="ajaxLoadingModalBox" minHeight="100"
                                     minWidth="400" height="80" width="400" zindex="100" onshow="window.focus();">

                        <div align="center" id="imgNormal">
                            <h:graphicImage value="../../img/load2.gif" />
                        </div>

                        <div id="imgCargar" style="background-color:#FFFFFF;width:300px; max-height:30px;height:30px;visibility:hidden;border:1px solid #000000;left:50px;position:absolute" >
                                <div style="position:relative;background-image:url(../../img/barra.gif);width:150px;height:30px;left:0px" align="left" id="imgBar"></div>

                                 <center><b><h:outputText value="0%" id="porcientoProcesado"style="position:absolute;top:10px" styleClass="outputText2" /></b></center>

                        </div>

                    </rich:modalPanel>
                    
                        <h:panelGroup id="panelContenidoActualizacion"  > <%--rendered="false"--%>

                            <%--rich:dataTable value="#{ManagedActualizacionCostos.kardexItemMovimientoList}" var="data"
                                            id="dataKardexMovimiento"
                                            onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                            onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                            headerClass="headerClassACliente" >
                                <h:column>
                                    <f:facet name="header">
                                         <h:outputText value=""  />
                                    </f:facet>
                                    <h:outputText value="#{data.codReporte}" />
                                </h:column>
                                <h:column>
                                    <f:facet name="header">
                                        <h:outputText value="fecha"  />
                                    </f:facet>
                                    <h:outputText value="#{data.fecha}"  />
                                </h:column>
                                <h:column>
                                    <f:facet name="header">
                                        <h:outputText value="Nro Ingreso/Salida"  />
                                    </f:facet>
                                    <h:outputText value="#{data.numero}"  />
                                </h:column>
                                <h:column>
                                    <f:facet name="header">
                                        <h:outputText value="Motivo"  />
                                    </f:facet>
                                    <h:outputText value="#{data.tipo}"  />
                                </h:column>

                                <h:column>
                                    <f:facet name="header">
                                        <h:outputText value="Lote"  />
                                    </f:facet>
                                    <h:outputText value="#{data.codLoteProduccion}"  />
                                </h:column>

                                <h:column>
                                    <f:facet name="header">
                                        <h:outputText value="Entrada"  />
                                    </f:facet>
                                    <h:outputText value="#{data.cantidadIngreso}"  />
                                </h:column>
                                <h:column>
                                    <f:facet name="header">
                                        <h:outputText value="Salida"  />
                                    </f:facet>
                                    <h:outputText value="#{data.cantidadSalida}"  />
                                </h:column>

                                <h:column>
                                    <f:facet name="header">
                                        <h:outputText value="Saldo"  />
                                    </f:facet>
                                    <h:outputText value="#{data.saldo}"  />
                                </h:column>

                                <h:column>
                                    <f:facet name="header">
                                        <h:outputText value="Costo Unitario"  />
                                    </f:facet>
                                    <h:outputText value="#{data.costoUnitario}"  />
                                </h:column>

                                <h:column>
                                    <f:facet name="header">
                                        <h:outputText value="Debe"  />
                                    </f:facet>
                                    <h:outputText value="#{data.debe}"  />
                                </h:column>
                                <h:column>
                                    <f:facet name="header">
                                        <h:outputText value="Haber"  />
                                    </f:facet>
                                    <h:outputText value="#{data.haber}"  />
                                </h:column>
                                <h:column>
                                    <f:facet name="header">
                                        <h:outputText value="Saldo Monto"  />
                                    </f:facet>
                                    <h:outputText value="#{data.saldoDinero}"  />
                                </h:column>
                                <h:column>
                                    <f:facet name="header">
                                        <h:outputText value="ufv"  />
                                    </f:facet>
                                    <h:outputText value="#{data.valorUfv}"  />
                                </h:column>


                            </rich:dataTable--%>
                    </h:panelGroup>

                    <%-- <ws:datascroller fordatatable="dataAreasEmpresa"  />--%>
                    <br>                    
                 

                </div>
                
            </a4j:form>
            


        </body>
        <script src="../../js/chosen.js"></script>
        <script type="text/javascript" language="JavaScript"  src="../../js/dlcalendar.js"></script>
    </html>
    
</f:view>

