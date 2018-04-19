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
            <script  type="text/javascript">
                function cogerId(obj){
                    alert(obj);


                }
                function getCodigoProcesoProduccion(codComProd,codigo,codFormula,codLote,codTipoActividadProduccion){
                 //  alert(codigo);
                   location='../actividades_programa_produccion/navegador_actividades_programa.jsf?codigo='+codigo+'&cod_formula='+codFormula+'&cod_comp_prod='+codComProd+'&cod_lote='+codLote+'&codTipoActividadProduccion='+codTipoActividadProduccion;
                }
                function getCodigoProcesoMicrobiologia(codComProd,codigo,codFormula,codLote,codTipoActividadProduccion){
                 //  alert(codigo);
                   location='../actividades_programa_produccion/navegador_actividades_programa.jsf?codigo='+codigo+'&cod_formula='+codFormula+'&cod_comp_prod='+codComProd+'&cod_lote='+codLote+'&codTipoActividadProduccion='+codTipoActividadProduccion;
                }
                function getCodigoProcesoControlDeCalidad(codComProd,codigo,codFormula,codLote,codTipoActividadProduccion){
                 //  alert(codigo);
                   location='../actividades_programa_produccion/navegador_actividades_programa.jsf?codigo='+codigo+'&cod_formula='+codFormula+'&cod_comp_prod='+codComProd+'&cod_lote='+codLote+'&codTipoActividadProduccion='+codTipoActividadProduccion;
                }
                function getCodigo(codigo,codFormula,nombre,cantidad,codComProd,codLote){

                    izquierda = (screen.width) ? (screen.width-300)/2 : 100
                    arriba = (screen.height) ? (screen.height-400)/2 : 200
                    //url='../configuracionReporte/navegadorConfiguracionReporteReplica.jsf?codigo='+cod+'';
                    url='detalle_navegador_programa_prod.jsf?codigo='+codigo+'&codFormula='+codFormula+'&nombre='+nombre+'&cantidad='+cantidad+'&cod_comp_prod='+codComProd+'&cod_lote='+codLote;
                    //alert(url);
                    opciones='toolbar=0,location=0,directories=0,status=0,menubar=0,scrollbars=1,resizable=1,width=700,height=400,left='+izquierda+ ',top=' + arriba + ''
                    window.open(url, 'popUp',opciones)

                }
                function getCodigo1(codComProd,codigo,codFormula,codLote){

                    izquierda = (screen.width) ? (screen.width-300)/2 : 100
                    arriba = (screen.height) ? (screen.height-400)/2 : 200
                    url='../barcode?number=1&location='+codComProd+"-"+codigo+"-"+codFormula+"-"+codLote;
                    //url='../codigo_barras.jsf?codigo='+codigo+'&codFormula='+codFormula+'&nombre='+nombre+'&cantidad='+cantidad+'&cod_comp_prod='+codComProd+'&cod_lote='+codLote;
                    //alert(url);
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
                    var elements=document.getElementById('form1:dataProgramaProduccion');

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

                function validarSeleccion(nametable){
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
                    }
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
               function seleccionarTodosMp()
                {
                    var tabla=document.getElementById("form1:dataFormulaMP").getElementsByTagName("tbody")[0];
                    var valor=document.getElementById("form1:dataFormulaMP:datoMp").checked;
                    for(var i=0;i<tabla.rows.length;i++)
                    {
                        tabla.rows[i].cells[0].getElementsByTagName("input")[0].checked=valor;
                    }
                }
                function seleccionarTodosEp()
                {
                    var tabla=document.getElementById("form1:dataEmpaquePrimario").getElementsByTagName("tbody")[0];
                    var valor=document.getElementById("form1:dataEmpaquePrimario:datoEp").checked;
                    for(var i=0;i<tabla.rows.length;i++)
                    {
                        tabla.rows[i].cells[0].getElementsByTagName("input")[0].checked=valor;
                    }
                }
                 function seleccionarTodosEs()
                {
                    var tabla=document.getElementById("form1:dataEmpaqueSecundario").getElementsByTagName("tbody")[0];
                    var valor=document.getElementById("form1:dataEmpaqueSecundario:datoEs").checked;
                    for(var i=0;i<tabla.rows.length;i++)
                    {
                        tabla.rows[i].cells[0].getElementsByTagName("input")[0].checked=valor;
                    }
                }
         </script>
        </head>
        <a4j:form id="form1">
                            <div align="center">
                            <h:outputText value="#{ManagedProgramaProduccion.cargarContenidoAgregarSalidaAlmacenProgramaProduccion}" />

                        <h:panelGroup id="contenidoSalidaAlmacenProgramaProduccion">
                            <h:panelGrid columns="2" styleClass="outputText2" >
                            <h:outputText value="Producto:" />
                            <h:outputText value="#{ManagedProgramaProduccion.programaProduccion.formulaMaestra.componentesProd.nombreProdSemiterminado}"  />
                            <h:outputText value="Versión:" />
                            <h:outputText value="#{ManagedProgramaProduccion.programaProduccion.nro_version}"  />
                            <h:outputText value="Tipo Programa Produccion:" />
                            <h:outputText value="#{ManagedProgramaProduccion.programaProduccion.tiposProgramaProduccion.nombreProgramaProd}"  />
                            <h:outputText value="Lote:" />
                            <h:outputText value="#{ManagedProgramaProduccion.programaProduccion.codLoteProduccion}"  />
                            <h:outputText value="Cantidad Lote:" />
                            <h:outputText value="#{ManagedProgramaProduccion.programaProduccion.cantidadLote}"  >
                                <f:convertNumber pattern="###.00" locale="en" />
                            </h:outputText>
                            <h:outputText value="Cantidad Ingreso Acond.:" />
                            <h:outputText value="#{ManagedProgramaProduccion.programaProduccion.cantIngresoAcond}"  >
                                <f:convertNumber pattern="###.00" locale="en" />
                            </h:outputText>

                            </h:panelGrid>
                            <h:panelGroup rendered="#{ManagedProgramaProduccion.configuracionSalidaAlmacenProduccion.tiposMaterial.codTipoMaterial=='1'}">
                            </h:panelGroup>
                            <h:panelGroup rendered="#{ManagedProgramaProduccion.permisoSolicitudMateriaPrima}">
                                <br>
                                <b>Materia Prima</b>
                                <br>
                                <rich:dataTable value="#{ManagedProgramaProduccion.materiaPrimaList}" 
                                                var="data" 
                                                id="dataFormulaMP"
                                                onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                                onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                                headerClass="headerClassACliente" width="80%">
                                    <h:column>
                                        <f:facet name="header">
                                            <h:selectBooleanCheckbox value="true" id="datoMp" onclick="seleccionarTodosMp();"/>

                                        </f:facet>
                                        <h:selectBooleanCheckbox value="#{data.checked}"  />
                                    </h:column>

                                    <h:column>
                                        <f:facet name="header">
                                            <h:outputText value="Material"  />
                                        </f:facet>
                                        <h:outputText value="#{data.materiales.nombreMaterial}"  />
                                    </h:column>
                                    <h:column>
                                        <f:facet name="header">
                                            <h:outputText value="Cantidad"  />
                                        </f:facet>
                                        <h:outputText value="#{data.cantidad}"  >
                                            <f:convertNumber pattern="###.00" locale="en" />
                                        </h:outputText>
                                    </h:column>
                                    <h:column>
                                        <f:facet name="header">
                                            <h:outputText value="Unidad Medida"  />
                                        </f:facet>
                                        <h:outputText value="#{data.unidadesMedida.nombreUnidadMedida}"  />
                                    </h:column>

                                </rich:dataTable>
                            </h:panelGroup>
                                <h:outputText value="No se encuentra configurado para solicitar Materia Prima"
                                              rendered="#{not ManagedProgramaProduccion.permisoSolicitudMateriaPrima}"
                                              styleClass="outputText2"
                                              style="color:red"/>
                            <br/>
                            <h:panelGroup rendered="#{ManagedProgramaProduccion.permisoSolicitudEmpaquePrimario}">
                                    <b> <h:outputText value="Presentacion Primaria: " styleClass="outputText1" /> </b>
                                    <h:selectOneMenu value="#{ManagedProgramaProduccion.presentacionesPrimarias.codPresentacionPrimaria}" styleClass="inputText" rendered="#{ManagedProgramaProduccion.tipoSalidaAlmacenProgramaProduccion=='1'}" >
                                        <f:selectItems value="#{ManagedProgramaProduccion.presentacionesPrimariasList}" />
                                        <a4j:support action="#{ManagedProgramaProduccion.empaquePrimario_change}" event="onchange" reRender="dataEmpaquePrimario" />
                                    </h:selectOneMenu>
                                    <br/>
                                    <br/>

                                    <rich:dataTable value="#{ManagedProgramaProduccion.empaquePrimarioList}" var="data" id="dataEmpaquePrimario"
                                                onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                                onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                                headerClass="headerClassACliente" width="80%">
                                        <h:column>
                                            <f:facet name="header">
                                                <h:selectBooleanCheckbox value="true" id="datoEp" onclick="seleccionarTodosEp();"/>

                                            </f:facet>
                                            <h:selectBooleanCheckbox value="#{data.checked}"  />
                                        </h:column>

                                        <h:column>
                                            <f:facet name="header">
                                                <h:outputText value="Material"  />
                                            </f:facet>
                                            <h:outputText value="#{data.materiales.nombreMaterial}"  />
                                        </h:column>
                                        <h:column>
                                            <f:facet name="header">
                                                <h:outputText value="Cantidad"  />
                                            </f:facet>
                                            <h:outputText value="#{data.cantidad}"  >
                                                <f:convertNumber pattern="###.00" locale="en" />
                                            </h:outputText>
                                        </h:column>
                                        <h:column>
                                            <f:facet name="header">
                                                <h:outputText value="Unidad Medida"  />
                                            </f:facet>
                                            <h:outputText value="#{data.unidadesMedida.nombreUnidadMedida}"  />
                                        </h:column>


                                </rich:dataTable>
                        </h:panelGroup>
                        <h:outputText value="No se encuentra configurado para solicitar Empaque Primario"
                                        rendered="#{not ManagedProgramaProduccion.permisoSolicitudEmpaquePrimario}"
                                        styleClass="outputText2"
                                        style="color:red"/>
                    <br/>
                        <h:panelGroup rendered="#{ManagedProgramaProduccion.permisoSolicitudEmpaqueSecundario}">
                            <b><h:outputText value="Presentacion Secundaria: " styleClass="outputText1" /></b>
                            <h:selectOneMenu value="#{ManagedProgramaProduccion.presentacionesProducto.codPresentacion}" styleClass="inputText" rendered="#{ManagedProgramaProduccion.tipoSalidaAlmacenProgramaProduccion=='1'}" >
                                  <f:selectItems value="#{ManagedProgramaProduccion.presentacionesSecundariasList}" />
                                  <a4j:support action="#{ManagedProgramaProduccion.empaqueSecundario_change}" event="onchange" reRender="dataEmpaqueSecundario" />
                            </h:selectOneMenu>
                            <br/>
                            <br/>
                                <rich:dataTable value="#{ManagedProgramaProduccion.empaqueSecundarioList}" var="data"
                                          id="dataEmpaqueSecundario"
                                          onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                          onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                          headerClass="headerClassACliente" width="80%">
                              <h:column>
                                  <f:facet name="header">
                                              <h:selectBooleanCheckbox value="true" id="datoEs" onclick="seleccionarTodosEs();"/>

                                          </f:facet>
                                  <h:selectBooleanCheckbox value="#{data.checked}"  />
                              </h:column>

                              <h:column>
                                  <f:facet name="header">
                                      <h:outputText value="Material"  />
                                  </f:facet>
                                  <h:outputText value="#{data.materiales.nombreMaterial}"  />
                              </h:column>
                              <h:column>
                                  <f:facet name="header">
                                      <h:outputText value="Cantidad"  />
                                  </f:facet>
                                  <h:outputText value="#{data.cantidad}">
                                      <f:convertNumber pattern="###.00" locale="en" />
                                  </h:outputText>
                              </h:column>
                              <h:column>
                                  <f:facet name="header">
                                      <h:outputText value="Unidad Medida"  />
                                  </f:facet>
                                  <h:outputText value="#{data.unidadesMedida.nombreUnidadMedida}"  />
                              </h:column>


                          </rich:dataTable>
                        </h:panelGroup>
                        <h:outputText value="No se encuentra configurado para solicitar Empaque Secundario"
                                        rendered="#{not ManagedProgramaProduccion.permisoSolicitudEmpaqueSecundario}"
                                        styleClass="outputText2"
                                        style="color:red"/>
                                              
                        </h:panelGroup>
                        <br/>
                        
                        <input type="button" value="Cancelar" class="btn" onclick="window.location='navegador_programa_produccion.jsf'" />
                        <h:commandButton value="Generar Solicitud Salidas Almacen" styleClass="btn" action="#{ManagedProgramaProduccion.generarSolicitudSalidasAlmacen_action}"  />
                        </div>
                        </a4j:form>
                       


            
        </body>
    </html>
    
</f:view>

