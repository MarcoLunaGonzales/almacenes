<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib prefix="a4j" uri="http://richfaces.org/a4j"%>
<%@taglib prefix="rich" uri="http://richfaces.org/rich"%>

<rich:modalPanel id="panelAgregarMaterial" minHeight="250"  minWidth="350"
                height="250" width="350"
                zindex="200"
                headerClass="headerClassACliente"
                resizeable="false" style="overflow :auto" >
    <f:facet name="header">
        <h:outputText value="Agregar Material"/>
    </f:facet>
    <a4j:form id="form3">
    <h:panelGroup id="contenidoAgregarMaterial">
        <h:panelGrid columns="3">
            <h:outputText value="Capitulo" styleClass="outputText1" />
            <h:outputText value="::" styleClass="outputText1" />
            <h:selectOneMenu value="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalleAgregar.materiales.grupos.capitulos.codCapitulo}" styleClass="inputText" >
                <f:selectItems value="#{ManagedRegistroSalidaAlmacen.capitulosList}" />
                <a4j:support event="onchange" action="#{ManagedRegistroSalidaAlmacen.cargarGrupos}" reRender="grupo" timeout="7200"  />
            </h:selectOneMenu>
            <h:outputText value="Grupo" styleClass="outputText1" />
            <h:outputText value="::" styleClass="outputText1" />
            <h:selectOneMenu value="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalleAgregar.materiales.grupos.codGrupo}" styleClass="inputText" id="grupo" >
                <f:selectItems value="#{ManagedRegistroSalidaAlmacen.gruposList}"  />
                <a4j:support event="onchange" action="#{ManagedRegistroSalidaAlmacen.grupos_change}" reRender="material" timeout="7200"  />
            </h:selectOneMenu>

            <h:outputText value="Material" styleClass="outputText1" />
            <h:outputText value="::" styleClass="outputText1" />
            <h:panelGroup>
                <h:selectOneMenu value="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalleAgregar.materiales.codMaterial}" styleClass="inputText" id="material" >
                    <f:selectItems value="#{ManagedRegistroSalidaAlmacen.materialesList}"  />
                </h:selectOneMenu>
                <a4j:commandLink  action="#{ManagedRegistroSalidaAlmacen.detallarCantidadSalida_action}" reRender="detalleSalidaMaterial" timeout="7200" >
                    <h:graphicImage url="../img/actualizar1.jpg" />
                </a4j:commandLink>
            </h:panelGroup>                                
        </h:panelGrid>

        <h:panelGrid columns="3" id="detalleSalidaMaterial" headerClass="headerClassACliente" style="width:100%" >
            <f:facet name="header" >
                <h:outputText value="Detalle de Salida de Material" />
            </f:facet>

            <h:outputText value="Nombre Material" styleClass="outputText1" />
            <h:outputText value="::" styleClass="outputText1" />                                
            <h:outputText value="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalleAgregar.materiales.nombreMaterial}" styleClass="outputText1" />

            <h:outputText value="Cantidad Disponible" styleClass="outputText1" />
            <h:outputText value="::" styleClass="outputText1" >
                <f:convertNumber pattern="###.00" locale="en" />
            </h:outputText>
            <h:outputText value="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalleAgregar.cantidadDisponible}" styleClass="outputText1" />

            <h:outputText value="Cantidad Salida" styleClass="outputText1" />
            <h:outputText value="::" styleClass="outputText1" />
            <h:panelGroup>
                <h:inputText value="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalleAgregar.cantidadSalidaAlmacen}" styleClass="inputText" onkeyup="calculaEquivalencia(this)" />
            <h:outputText value="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalleAgregar.equivalencias.unidadesMedida1.nombreUnidadMedida}" styleClass="outputText1" />
            </h:panelGroup>

            <h:outputText value="Equivalencia" styleClass="outputText1" />
            <h:outputText value="::" styleClass="outputText1" />
            <h:panelGroup>
                <h:inputHidden value="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalleAgregar.equivalencias.valorEquivalencia}" id="valorEquivalencia" />
                <h:inputText value="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalleAgregar.cantidadSalidaAlmacenEquivalente}" styleClass="inputText" id="cantidadEquivalencia" />
                <h:outputText value="#{ManagedRegistroSalidaAlmacen.salidasAlmacenDetalleAgregar.equivalencias.unidadesMedida2.nombreUnidadMedida}" styleClass="outputText1" />
            </h:panelGroup>

        </h:panelGrid>



            <div align="center">
            <a4j:commandButton styleClass="btn" value="Guardar"
            action="#{ManagedRegistroSalidaAlmacen.guardarAgregarSalidaAlmacenDetalle_action}"
            oncomplete="if(#{ManagedRegistroSalidaAlmacen.mensaje!=''}){alert('#{ManagedRegistroSalidaAlmacen.mensaje}')}else{javascript:Richfaces.hideModalPanel('panelAgregarMaterial')}"
                        reRender="dataSalidasAlmacenDetalle" timeout="7200"
                                />
            <input type="button" value="Cancelar" onclick="javascript:Richfaces.hideModalPanel('panelAgregarMaterial')" class="btn" />
            </div>
    </h:panelGroup>
 </a4j:form>
</rich:modalPanel>