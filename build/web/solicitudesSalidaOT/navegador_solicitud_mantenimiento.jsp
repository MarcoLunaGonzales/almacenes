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
            <style  type="text/css">
                .a{
                background-color : #F2F5A9;
                }
                .b{
                background-color : #ffffff;
                }
                .columns{
                border:0px solid red;
                }
                .simpleTogglePanel{
                text-align:center;
                }
                .ventasdetalle{
                font-size: 13px;
                font-family: Verdana;
                }
                .preciosaprobados{
                background-color:#33CCFF;
                }
                .enviado{
                background-color:#FFFFCC;
                }
                .pasados{
                background-color:#ADD797;
                }
                .pendiente{
                background-color : #ADD797;
                }
                .leyendaColorAnulado{
                background-color: #FF6666;
                }
            </style>
            <script>
                function cogerId(obj){
                    alert(obj);


                }
                function getCodigo(codigoSolicitud){
                    izquierda = (screen.width) ? (screen.width-300)/2 : 100
                    arriba = (screen.height) ? (screen.height-400)/2 : 200
                    //url='../configuracionReporte/navegadorConfiguracionReporteReplica.jsf?codigo='+cod+'';
                    url='navegador_aprobar_solicitud_mantenimiento.jsf?codigo='+codigoSolicitud;
                    //alert(url);
                    opciones='toolbar=0,location=0,directories=0,status=0,menubar=0,scrollbars=1,resizable=1,width=700,height=400,left='+izquierda+ ',top=' + arriba + ''
                    window.open(url, 'popUp',opciones)
                }
                
                function getOrdenPedido(codigoSolicitud){
                    izquierda = (screen.width) ? (screen.width-300)/2 : 100
                    arriba = (screen.height) ? (screen.height-400)/2 : 200
                    url='../ordenMateriales/agregar_Orden_Materiales.jsf?codigo='+codigoSolicitud;
                    opciones='toolbar=0,location=0,directories=0,status=0,menubar=0,scrollbars=1,resizable=1,width=700,height=400,left='+izquierda+ ',top=' + arriba + ''
                    window.open(url, 'popUp',opciones)
                }    
                
                   
                function getCodigo1(codComProd,codigo,codFormula,codLote){
                    izquierda = (screen.width) ? (screen.width-300)/2 : 100
                    arriba = (screen.height) ? (screen.height-400)/2 : 200
                    url='../barcode?number=1&location='+codComProd+"-"+codigo+"-"+codFormula+"-"+codLote;
                    //url='../codigo_barras.jsf?codigo='+codigo+'&codFormula='+codFormula+'&nombre='+nombre+'&cantidad='+cantidad+'&cod_comp_prod='+codComProd+'&cod_lote='+codLote;
                    alert(url);
                    opciones='toolbar=0,location=0,directories=0,status=0,menubar=0,scrollbars=1,resizable=1,width=700,height=400,left='+izquierda+ ',top=' + arriba + ''
                    window.open(url, 'popUp',opciones)
                }
                
                function getCodigo2(codProgProd,codFormula,nombre,cantidad,codComProd,codLote){

                    izquierda = (screen.width) ? (screen.width-300)/2 : 100
                    arriba = (screen.height) ? (screen.height-400)/2 : 200
                    //url='../configuracionReporte/navegadorConfiguracionReporteReplica.jsf?codigo='+cod+'';
                    url='imprimir_programa_prod_excel.jsf?codProgProd='+codProgProd+'&codFormula='+codFormula+'&nombre='+nombre+'&cantidad='+cantidad+'&cod_comp_prod='+codComProd+'&cod_lote='+codLote;
                    alert(url);
                    opciones='toolbar=0,location=0,directories=0,status=0,menubar=0,scrollbars=1,resizable=1,width=700,height=400,left='+izquierda+ ',top=' + arriba + ''
                    window.open(url, 'popUp',opciones)

                }
                function getCodigoReserva(codigo){


                    location='../reporteExplosionProductos/guardarReservaProgramaProd.jsf?codigo='+codigo;


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


                        if(confirm('Desea Eliminar el Registro')){
                            if(confirm('Esta seguro de Eliminar el Registro')){
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
                            }else{
                                return false;
                            }
                        }else{
                            return false;
                        }
                    }
                }
                
                function reserva(nametable){
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


                        if(confirm('Desea Realizar la Reserva del Producto')){
                            if(confirm('Esta seguro de Realizar la Reserva ')){
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
                            }else{
                                return false;
                            }
                        }else{
                            return false;
                        }
                    }
                }

                 function eliminarReserva(nametable){
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


                        if(confirm('Desea Eliminar la Reserva del Producto')){
                            if(confirm('Esta seguro de Eliminar la Reserva')){
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
                            }else{
                                return false;
                            }
                        }else{
                            return false;
                        }
                    }
                }
                
                function nuevoAjax()
                {	var xmlhttp=false;
                    try {
                        xmlhttp = new ActiveXObject("Msxml2.XMLHTTP");
                    } catch (e) {
                        try {
                            xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
                        } catch (E) {
                            xmlhttp = false;
                        }
                    }
                    if (!xmlhttp && typeof XMLHttpRequest!="undefined") {
                        xmlhttp = new XMLHttpRequest();
                    }
                    return xmlhttp;
                }
                function enviar(codigo,fecha_ini,fecha_fin){
                    //alert();

                    var ajax=nuevoAjax();
                    var valores='codigos='+codigo+'&fecha_inicio='+fecha_ini+'&fecha_final='+fecha_fin;
                    valores+='&pq='+(Math.random()*1000);

                    var url='../reporteExplosionProductos/filtroReporteExplosion.jsf';



                    //alert(url);
                    ajax.open ('POST', url, true);
                    ajax.onreadystatechange = function() {

                        if (ajax.readyState==1) {

                        }else if(ajax.readyState==4){
                            // alert(ajax.status);
                            if(ajax.status==200){
                                //alert(ajax.responseText);
                                var mainGrupo=document.getElementById('panelCenter');
                                //mainGrupo.innerHTML=ajax.responseText;
                                document.write(ajax.responseText);

                                //f=0;
                                //Item(codigo,f);
                            }
                        }
                    }
                    ajax.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
                    ajax.send(valores);
                }


                function seleccionarTodo(){
                    //alert('entro');
                    var seleccionar_todo=document.getElementById('form1:seleccionar_todo');
                    var elements=document.getElementById('form1:dataSolicitudMantenimiento');

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


        </script>
        </head>
        <body >
            
            
            <a4j:form id="form1">
                <div align="center">
                    <h:outputText value="#{ManagedSolicitudMantenimiento.cargarSolicitudMantenimiento}"  />
                    <h3 align="center">Solicitudes de Mantenimiento</h3>
                    
                    
                    <%--h:outputText styleClass="outputTextTitulo"  value="Estado::" />
                    <h:selectOneMenu value="#{ManagedSolicitudMantenimiento.solicitudMantenimientobean.estadoSolicitudMantenimiento.codEstadoSolicitudMantenimiento}" styleClass="inputText"
                                     >
                        <f:selectItems value="#{ManagedSolicitudMantenimiento.estadosSolicitudMantenimientoList}"  />
                        <a4j:support event="onchange"  reRender="dataSolicitudMantenimiento,controles" action="#{ManagedSolicitudMantenimiento.estadoSolicitudMantenimiento_change}" />
                    </h:selectOneMenu--%>

                  

                        <a4j:commandLink onclick="Richfaces.showModalPanel('panelBuscarSolicitudMantenimiento')">
                            <h:outputText value="Buscar" styleClass="outputText1" />
                            <h:graphicImage url="../img/buscar.png">

                            </h:graphicImage>
                        </a4j:commandLink>
                    
                    <div style="overflow:auto;height:350px">
                    <rich:dataTable value="#{ManagedSolicitudMantenimiento.solicitudMantenimientoList}" var="data" id="dataSolicitudMantenimiento"
                                    onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                    onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                    headerClass="headerClassACliente">
                        <rich:column  >
                            <f:facet name="header">
                                <h:outputText value=""  />
                            </f:facet>
                            <h:selectBooleanCheckbox value="#{data.checked}" rendered="#{data.estadoSolicitudMantenimiento.codEstadoSolicitudMantenimiento=='1' ||data.estadoSolicitudMantenimiento.codEstadoSolicitudMantenimiento=='4'}"  />
                        </rich:column >
                        <rich:column style="" >
                            <f:facet name="header">
                                <h:outputText value=""  />
                            </f:facet>
                            <h:graphicImage url="../img/go_up_red.png" width="25px" height="25px" rendered="#{data.tiposNivelSolicitudMantenimiento.codTipoNivelSolicitudMantenimiento=='1'}" />
                            <h:graphicImage url="../img/go_up_orange.png" width="25px" height="25px" rendered="#{data.tiposNivelSolicitudMantenimiento.codTipoNivelSolicitudMantenimiento=='2'}" />
                            <h:graphicImage url="../img/go_up.png" width="25px" height="25px" rendered="#{data.tiposNivelSolicitudMantenimiento.codTipoNivelSolicitudMantenimiento=='3'}" />

                            

                        </rich:column >

                        <rich:column  >
                            <f:facet name="header">
                                <h:outputText value="Nro Solicitud"  />
                            </f:facet>
                            <h:outputText value="#{data.codSolicitudMantenimiento}"  />
                        </rich:column >
                        <rich:column  >
                            <f:facet name="header">
                                <h:outputText value="Nro O.T."  />
                            </f:facet>
                            <h:outputText value="#{data.nroOrdenTrabajo}" rendered="#{data.nroOrdenTrabajo>'0'}" />
                        </rich:column >
                        
                        
                        <rich:column >
                            <f:facet name="header">
                                <h:outputText value="Solicitante"  />
                            </f:facet>
                            <h:outputText value="#{data.personal_usuario.nombrePersonal}" /> &nbsp;
                            <h:outputText value="#{data.personal_usuario.apPaternoPersonal}" /> &nbsp;
                            <h:outputText value="#{data.personal_usuario.apMaternoPersonal}" />

                        </rich:column >
                        <rich:column >
                            <f:facet name="header">
                                <h:outputText value="Area Empresa"  />
                            </f:facet>
                            <h:outputText value="#{data.areasEmpresa.nombreAreaEmpresa}" />


                        </rich:column >
                        <rich:column  >
                            <f:facet name="header">
                                <h:outputText value="Fecha Emisi�n"  />
                            </f:facet>
                            <h:outputText value="#{data.fechaSolicitudMantenimiento}"  >
                                <f:convertDateTime pattern="dd/MM/yyyy hh:mm:ss" />
                            </h:outputText>
                        </rich:column >
                        <rich:column  >
                            <f:facet name="header">
                                <h:outputText value="Maquinaria"  />
                            </f:facet>
                            <h:outputText value="#{data.maquinaria.nombreMaquina}"  />
                        </rich:column >
                        <rich:column  >
                            <f:facet name="header">
                                <h:outputText value="Parte de Maquinaria"  />
                            </f:facet>
                            <h:outputText value="#{data.partesMaquinaria.nombreParteMaquina}"  />
                        </rich:column >
                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value="Instalacion"  />
                            </f:facet>
                            <h:outputText value="#{data.modulosInstalaciones.areasInstalacionesModulos.areasInstalaciones.nombreAreaInstalacion}"  />
                        </rich:column >
                         <rich:column>
                            <f:facet name="header">
                                <h:outputText value="Ambiente"  />
                            </f:facet>
                            <h:outputText value="#{data.areasInstalacionesDetalle.nombreAreaInstalacionDetalle}"  />
                        </rich:column >
                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value="Modulo"  />
                            </f:facet>
                            <h:outputText value="#{data.modulosInstalaciones.nombreModuloInstalacion}"  />
                        </rich:column >

                        <%--rich:column styleClass="#{data.styleClass}" >
                            <f:facet name="header">
                                <h:outputText value="Nro. de Lotes a Producir"  />
                            </f:facet>
                            <h:outputText value="#{data.cantidadLote}"  />
                        </rich:column --%>
                        
                        <rich:column style ="#{data.afectaraProduccion==1?'background-color:#F5A9A9':''}" >
                            <f:facet name="header">
                                <h:outputText value="Afectara Produccion"  />
                            </f:facet>
                            <h:outputText value="SI" rendered="#{data.afectaraProduccion==1}"   />
                            <h:outputText value="NO" rendered="#{data.afectaraProduccion==0}" />
                        </rich:column >
                        <rich:column >
                            <f:facet name="header">
                                <h:outputText value="Observaciones"  />
                            </f:facet>
                            <h:outputText value="#{data.obsSolicitudMantenimiento}" />
                        </rich:column >
                        <rich:column style="background-color : #F2F5A9"  >
                            <f:facet name="header">
                                <h:outputText value="Estado Solicitud"  />
                            </f:facet>
                            <h:outputText value="#{data.estadoSolicitudMantenimiento.nombreEstadoSolicitudMantenimiento}"  />
                        </rich:column >
                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value="Tipo de Solicitud"  />
                            </f:facet>
                            <h:outputText value="#{data.tiposSolicitudMantenimiento.nombreTipoSolMantenimiento}"  />
                        </rich:column >
                        <rich:column>
                            <f:facet name="header">
                                <h:outputText value=" Descripcion de Estado "  />
                            </f:facet>
                            <h:outputText value="#{data.descripcionEstado}"  />
                        </rich:column >


                                             
                           
                    </rich:dataTable>
                    </div>
                    <h:panelGrid columns="2"  width="50" id="controles">
                        <a4j:commandLink  action="#{ManagedSolicitudMantenimiento.atras_action1}" reRender="dataSolicitudMantenimiento,controles"  rendered="#{ManagedSolicitudMantenimiento.begin!='1'}" >
                            <h:graphicImage url="../img/previous.gif"  style="border:0px solid red"   alt="PAGINA ANTERIOR"  />
                        </a4j:commandLink>
                        <a4j:commandLink  action="#{ManagedSolicitudMantenimiento.siguiente_action1}" reRender="dataSolicitudMantenimiento,controles" rendered="#{ManagedSolicitudMantenimiento.cantidadfilas>='10'}">
                            <h:graphicImage url="../img/next.gif"  style="border:0px solid red"  alt="PAGINA SIGUIENTE" />
                        </a4j:commandLink>
                    </h:panelGrid>

                 
                    <h:commandButton value="Agregar"   styleClass="btn"  actionListener="#{ManagedSolicitudMantenimiento.actionSave}">
                        <f:attribute name="url" value="navegador_solicitud_mantenimiento.jsf" />
                    </h:commandButton>
                    <a4j:commandButton value="Editar" styleClass="btn" action="#{ManagedSolicitudMantenimiento.editarSolicitudMantenimiento_action}" oncomplete="window.location='editar_solicitud_mantenimiento.jsf'"/>

                    <a4j:commandButton value="Cerrar" styleClass="btn" action="#{ManagedSolicitudMantenimiento.cerrarSolicitudMantenimiento_action}" onclick="if(confirm('esta seguro de cerrar la Solicitud de Mantenimiento?')==false){return false;}"
                    rendered="#{ManagedAccesoSistema.codAreaEmpresaGlobal eq '86'}" reRender="dataSolicitudMantenimiento" />
                    <a4j:commandButton value="Correo" styleClass="btn" action="#{ManagedSolicitudMantenimiento.enviarCorreoPrueba_action}"
                    reRender="dataSolicitudMantenimiento" />

                </div>
                <!--cerrando la conexion-->
                <h:outputText value="#{ManagedProgramaProduccion.closeConnection}"  />
                
            </a4j:form>
            <rich:modalPanel id="panelBuscarSolicitudMantenimiento"  minHeight="300"  minWidth="1000"
                                     height="300" width="1000"
                                     zindex="200"
                                     headerClass="headerClassACliente"
                                     resizeable="false" style="overflow :auto" >
                        <f:facet name="header">
                            <h:outputText value="Buscar Solicitud Mantenimiento"/>
                        </f:facet>
                        <a4j:form>
                        <h:panelGroup id="contenidoBuscarSolicitudMantenimiento">
                            <h:panelGrid columns="4">


                                <h:outputText value="Nro de Solicitud:" styleClass="outputText1" />
                                <h:inputText value="#{ManagedSolicitudMantenimiento.solicitudMantenimientoBuscar.codSolicitudMantenimiento}" styleClass="inputText" size="5" onkeypress="valNum(event);" />

                                <h:outputText value="Nro O.T.:" styleClass="outputText1" />
                                <h:inputText value="#{ManagedSolicitudMantenimiento.solicitudMantenimientoBuscar.nroOrdenTrabajo}" styleClass="inputText" size="5" onkeypress="valNum(event);" />
                                <h:outputText value="Solicitante:" styleClass="outputText1" />
                                <h:selectOneMenu value="#{ManagedSolicitudMantenimiento.solicitudMantenimientoBuscar.personal_usuario.codPersonal}" styleClass="inputText" >
                                    <f:selectItems value="#{ManagedSolicitudMantenimiento.solicitanteList}"  />
                                </h:selectOneMenu>
                                <h:outputText value="Area Empresa:" styleClass="outputText1" />
                                <h:selectOneMenu value="#{ManagedSolicitudMantenimiento.solicitudMantenimientoBuscar.areasEmpresa.codAreaEmpresa}" styleClass="inputText" >
                                    <f:selectItems value="#{ManagedSolicitudMantenimiento.areasEmpresaBuscadorList}"  />
                                </h:selectOneMenu>


                                <h:outputText value="fecha inicio Solicitud:" styleClass="outputText1" />
                                <rich:calendar datePattern="dd/MM/yyyy" styleClass="inputText" immediate="true"
                                        value="#{ManagedSolicitudMantenimiento.fechaInicio}"   id="fechaInicio" enableManualInput="true"  width="1" />


                                <h:outputText value="fecha final Solicitud:" styleClass="outputText1" />
                                <rich:calendar datePattern="dd/MM/yyyy" styleClass="inputText" immediate="true"
                                      value="#{ManagedSolicitudMantenimiento.fechaFinal}"   id="fechaFinal" enableManualInput="true"  width="1" />

                                <h:outputText value="Maquinaria:" styleClass="outputText1" />
                                <h:selectOneMenu value="#{ManagedSolicitudMantenimiento.solicitudMantenimientoBuscar.maquinaria.codMaquina}" styleClass="inputText" style="width:280px">
                                    <f:selectItems value="#{ManagedSolicitudMantenimiento.maquinariasList}"  />
                                </h:selectOneMenu>

                                <h:outputText value="Instalacion:" styleClass="outputText1" />
                                <h:selectOneMenu value="#{ManagedSolicitudMantenimiento.solicitudMantenimientoBuscar.modulosInstalaciones.codModuloInstalacion}" styleClass="inputText" style="width:280">
                                    <f:selectItems value="#{ManagedSolicitudMantenimiento.instalacionesList}"  />
                                </h:selectOneMenu>

                                <h:outputText value="afecta a produccion:" styleClass="outputText1" />
                                <h:selectOneMenu value="#{ManagedSolicitudMantenimiento.solicitudMantenimientoBuscar.afectaraProduccion}" styleClass="inputText" >
                                    <f:selectItem itemLabel="-TODOS-" itemValue='2' />
                                    <f:selectItem itemLabel="SI" itemValue='1' />
                                    <f:selectItem itemLabel="NO" itemValue='0' />
                                </h:selectOneMenu>

                                <h:outputText value="Estado:" styleClass="outputText1" />
                                <h:selectOneMenu value="#{ManagedSolicitudMantenimiento.solicitudMantenimientoBuscar.estadoSolicitudMantenimiento.codEstadoSolicitudMantenimiento}" styleClass="inputText" >
                                    <f:selectItems value="#{ManagedSolicitudMantenimiento.estadoSolicitudMantenimientoList}" />
                                </h:selectOneMenu>




                            </h:panelGrid>
                                <div align="center">
                                <a4j:commandButton styleClass="btn" value="Buscar" onclick="javascript:Richfaces.hideModalPanel('panelBuscarSolicitudMantenimiento');"
                                action="#{ManagedSolicitudMantenimiento.buscarSolicitudMantenimiento_action}" reRender="dataSolicitudMantenimiento" />
                                <input type="button" value="Cancelar" onclick="javascript:Richfaces.hideModalPanel('panelBuscarSolicitudMantenimiento')" class="btn" />
                                </div>
                        </h:panelGroup>
                        </a4j:form>
            </rich:modalPanel>
            
                <table style="background-color:red">

                </table>

            
        </body>
    </html>
    
</f:view>

