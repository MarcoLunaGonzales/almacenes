<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib prefix="a4j" uri="http://richfaces.org/a4j"%>
<%@taglib prefix="rich" uri="http://richfaces.org/rich"%>
<%@ page import="java.sql.*" %>
<%@ page import="com.cofar.util.*" %>
<%@ page import="com.cofar.web.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<f:view>
<%! Connection con = null;
%>
<style type="text/css">
    .tituloCampo1{
        font-family: Verdana, Arial, Helvetica, sans-serif;
        font-size: 11px;
        font-weight: bold;
    }
    .outputText3{
        font-family: Verdana, Arial, Helvetica, sans-serif;
        font-size: 11px;
    }
    .inputText3{
        font-family: Verdana, Arial, Helvetica, sans-serif;
        font-size: 11px;
    }
    .commandButtonRene{
        font-family: Verdana, Arial, Helvetica, sans-serif;
        font-size: 11px;
        width: 150px;
        height: 20px;
        background-repeat :repeat-x;
        background-image: url('../img/bar3.png');
    }
    .tablaPrograma tbody tr td
    {
        padding: 6px;
        border-bottom: 1px solid #ddd !important;
    }
</style>
<html>
    <head>
        <link rel="STYLESHEET" type="text/css" href="../css/ventas.css" />
        <link rel="stylesheet" type="text/css" href="../css/style.css" />
        <script type="text/javascript" src="../js/general.js"></script>
    </head>
    <body>
        <a4j:form>
            <h:outputText value="#{ManagedProgramaProduccion.cargarContenidoProgramaProduccionPeriodo}"  />
                <center>
                    <STRONG STYLE="font-size:16px;color:#000000;">Programa Producción</STRONG>
                        <br/>
                        <a4j:commandLink onclick="Richfaces.showModalPanel('panelBuscarLoteProduccion')" >
                            <h:outputText value="Buscar Lote"/>
                            <h:graphicImage url="../img/buscar.png" />
                        </a4j:commandLink>
                        <h:panelGroup id="contenidoProgramaProduccion">
                            <rich:dataTable value="#{ManagedProgramaProduccion.programaProduccionPeriodoList}" var="data" id="dataProgramaProduccion"
                                        onRowMouseOut="this.style.backgroundColor='#FFFFFF';"
                                        onRowMouseOver="this.style.backgroundColor='#DDE3E4';"
                                        headerClass="headerClassACliente"
                                        style="width:90%">
                                <rich:column>
                                    <f:facet name="header">
                                        <h:outputText value="Programa Produccion"  />
                                    </f:facet>
                                    <a4j:commandLink styleClass="outputText2" oncomplete="redireccionar('navegador_programa_produccion.jsf')"
                                                     value="#{data.nombreProgramaProduccion}">
                                        <f:setPropertyActionListener value="#{data.codProgramaProduccion}" target="#{ManagedProgramaProduccion.programaProduccionbean.codProgramaProduccion}"/>
                                    </a4j:commandLink>
                                </rich:column>
                                <rich:column  >
                                    <f:facet name="header">
                                        <h:outputText value="Observacion"  />
                                    </f:facet>
                                    <h:outputText value="#{data.obsProgramaProduccion}"  />
                                </rich:column>
                                <rich:column  >
                                    <f:facet name="header">
                                        <h:outputText value="Estado"  />
                                    </f:facet>
                                    <h:outputText value="#{data.estadoProgramaProduccion.nombreEstadoProgramaProd}"  />
                                </rich:column>
                                <rich:column  >
                                    <f:facet name="header">
                                        <h:outputText value="Tipo de Produccion"  />
                                    </f:facet>
                                    <h:outputText value="#{data.tiposProduccion.nombreTipoProduccion}"  />
                                </rich:column>
                        </rich:dataTable>
                    </h:panelGroup>
            </center>
        </a4j:form>
            <rich:modalPanel id="panelBuscarLoteProduccion"  minHeight="120"  minWidth="300"
                                 height="120" width="300"
                                 zindex="200"
                                 headerClass="headerClassACliente"
                                 resizeable="false" style="overflow :auto" >
                    <f:facet name="header">
                        <h:outputText value=" Buscar Lote de Produccion "/>
                    </f:facet>
                    <a4j:form>
                    <h:panelGroup id="contenidoBuscarLoteProduccion">
                        <div align="center">
                        <h:panelGrid columns="2">
                            <h:outputText value="Lote :" styleClass="outputText2" />
                            <h:inputText value="#{ManagedProgramaProduccion.programaProduccionBuscar.codLoteProduccion}" styleClass="inputText2" />

                        </h:panelGrid>


                             <a4j:commandButton value="Aceptar" onclick = "redireccionar('navegador_programa_produccion.jsf')" styleClass="btn">
                                 <f:setPropertyActionListener value="" target="#{ManagedProgramaProduccion.programaProduccionbean.codProgramaProduccion}"/>
                                 <f:setPropertyActionListener value="" target="#{ManagedProgramaProduccion.codProgramaProd}"/>
                             </a4j:commandButton>
                             <input value="Cancelar" onclick="Richfaces.hideModalPanel('panelBuscarLoteProduccion')" class="btn" type="button" />


                            </div>
                    </h:panelGroup>
                </a4j:form>
            </rich:modalPanel>
        </body>
    </html>
</f:view>