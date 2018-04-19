package solicitudesSalidaAlmacen;


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
           
        </head>
        <body >
            <a4j:form id="form1" >
                <div align="center">
                    <rich:panel style="width:40%"  headerClass="headerClassACliente">
                        <f:facet name="header">
                    <h:outputText   value="Actualizar Datos" />
                    </f:facet>
                    <h:panelGrid columns="1">
                    <a4j:commandButton styleClass="btn" value="Actualizar Datos" action="#{ManagedRegistroSolicitudSalidaAlmacen.actualizarDatos_action}"
                    oncomplete="alert('#{ManagedRegistroSolicitudSalidaAlmacen.mensaje}')"/>
                    </h:panelGrid>
                    </rich:panel>
                </div>
             </a4j:form>
            


        </body>
    </html>

</f:view>

