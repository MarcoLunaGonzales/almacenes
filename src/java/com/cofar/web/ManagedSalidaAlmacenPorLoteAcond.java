/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.web;

import com.cofar.bean.ConfiguracionSalidaAlmacenProduccion;
import com.cofar.bean.FormulaMaestraDetalleEP;
import com.cofar.bean.FormulaMaestraDetalleES;
import com.cofar.bean.FormulaMaestraDetalleMP;
import com.cofar.bean.IngresosAlmacenDetalleAcond;
import com.cofar.bean.ProgramaProduccion;
import com.cofar.util.Util;
import com.cofar.util.Utiles;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.richfaces.component.html.HtmlDataTable;

/**
 *
 * @author hvaldivia
 */

public class ManagedSalidaAlmacenPorLoteAcond {
    List lotesProductosSemiterminadosList = new ArrayList();
    HtmlDataTable lotesProductosSemiterminadosDataTable = new HtmlDataTable();
    ManagedProgramaProduccion managedProgramaProduccion = new ManagedProgramaProduccion();
    List configuracionSalidaMaterialProduccionPersonaList = new ArrayList();
    List materiaPrimaList = new ArrayList();
    List empaquePrimarioList = new ArrayList();
    List empaqueSecundarioList = new ArrayList();

    public List getLotesProductosSemiterminadosList() {
        return lotesProductosSemiterminadosList;
    }

    public void setLotesProductosSemiterminadosList(List lotesProductosSemiterminadosList) {
        this.lotesProductosSemiterminadosList = lotesProductosSemiterminadosList;
    }

    public HtmlDataTable getLotesProductosSemiterminadosDataTable() {
        return lotesProductosSemiterminadosDataTable;
    }

    public void setLotesProductosSemiterminadosDataTable(HtmlDataTable lotesProductosSemiterminadosDataTable) {
        this.lotesProductosSemiterminadosDataTable = lotesProductosSemiterminadosDataTable;
    }
    





    





    /** Creates a new instance of ManagedSalidaAlmacenPorLoteAcond */
    public ManagedSalidaAlmacenPorLoteAcond() {
    }
    public String getCargarContenidoLotesProductosSemiterminados(){
        try {
            configuracionSalidaMaterialProduccionPersonaList = managedProgramaProduccion.cargarConfiguracionSalidaAlmacenProduccionPersona();
        String consulta = " select c.COD_COMPPROD,c.nombre_prod_semiterminado, i.COD_LOTE_PRODUCCION," +
                " i.FECHA_VEN fecha_vencimiento,  i.CANT_RESTANTE,ii.cod_ingreso_acond" +
                " from INGRESOS_DETALLEACOND i,  INGRESOS_ACOND ii, COMPONENTES_PROD c" +
                " where i.COD_INGRESO_ACOND = ii.COD_INGRESO_ACOND and c.COD_AREA_EMPRESA in (81) " +
                " and  ii.COD_ALMACENACOND = 1 and ii.COD_ESTADO_INGRESOACOND not in (1, 2) " +
                " and c.COD_COMPPROD = i.COD_COMPPROD and i.CANT_RESTANTE > 0 " +
                " order by c.nombre_prod_semiterminado, i.COD_LOTE_PRODUCCION; ";
            System.out.println("consulta " +consulta);
        Connection con = null;
        con = Util.openConnection(con);
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(consulta);
        lotesProductosSemiterminadosList.clear();
        while(rs.next()){
            //System.out.println("iteracion");
            IngresosAlmacenDetalleAcond ingresosAlmacenDetalleAcond = new IngresosAlmacenDetalleAcond();
            ingresosAlmacenDetalleAcond.getComponentesProd().setCodCompprod(rs.getString("cod_compprod"));
            ingresosAlmacenDetalleAcond.getIngresosAcondicionamiento().setCodIngresoAcond(rs.getString("cod_ingreso_acond"));
            ingresosAlmacenDetalleAcond.getComponentesProd().setNombreProdSemiterminado(rs.getString("nombre_prod_semiterminado"));
            ingresosAlmacenDetalleAcond.setCodLoteProduccion(rs.getString("cod_lote_produccion"));
            ingresosAlmacenDetalleAcond.setFechaVencimiento(rs.getDate("fecha_vencimiento"));
            ingresosAlmacenDetalleAcond.setCantRestante(rs.getFloat("cant_restante"));
            System.out.println("verifica cantidad faltante" + this.verificaMaterialesCantidadFaltante(ingresosAlmacenDetalleAcond));
            if(this.verificaMaterialesCantidadFaltante(ingresosAlmacenDetalleAcond)==false){ingresosAlmacenDetalleAcond.setColorFila("#7CCF29");}else{ingresosAlmacenDetalleAcond.setColorFila("#E82D2D");}
            lotesProductosSemiterminadosList.add(ingresosAlmacenDetalleAcond);
        }
        } catch (Exception e) {
        }
        return null;
    }
    public double hallaProrateo(IngresosAlmacenDetalleAcond ingresosAlmacenDetalleAcond){
        double prorateo=0;
        try {


        String consulta = " select ppr.CANT_LOTE_PRODUCCION/fm.CANTIDAD_LOTE prorateo from programa_produccion ppr " +
                " inner join PROGRAMA_PRODUCCION_INGRESOS_ACOND ppria on ppria.COD_LOTE_PRODUCCION = ppr.COD_LOTE_PRODUCCION " +
                " and ppria.COD_COMPPROD = ppr.COD_COMPPROD and ppria.COD_FORMULA_MAESTRA = ppr.COD_FORMULA_MAESTRA " +
                " and ppria.COD_TIPO_PROGRAMA_PROD = ppr.COD_TIPO_PROGRAMA_PROD " +
                " inner join FORMULA_MAESTRA fm on fm.COD_FORMULA_MAESTRA = ppr.COD_FORMULA_MAESTRA " +
                " where ppria.COD_LOTE_PRODUCCION = '"+ingresosAlmacenDetalleAcond.getCodLoteProduccion()+"'" +
                " and ppria.COD_INGRESO_ACOND = '"+ingresosAlmacenDetalleAcond.getIngresosAcondicionamiento().getCodIngresoAcond()+"'" +
                " and ppria.COD_COMPPROD = '"+ingresosAlmacenDetalleAcond.getComponentesProd().getCodCompprod()+"'";
        System.out.println("consulta " + consulta);
        Connection con = null;
        con = Util.openConnection(con);
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(consulta);
        if(rs.next()){
            prorateo = rs.getDouble("prorateo");
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prorateo;

    }
    public String generarSalidaAlmacen_action(){
        try {
            ConfiguracionSalidaAlmacenProduccion configuracionSalidaAlmacenProduccion = new ConfiguracionSalidaAlmacenProduccion();
            configuracionSalidaAlmacenProduccion.getTiposSalidaAlmacenProduccion().setCodTipoSalidaAlmacenProduccion(1);
            Iterator i = lotesProductosSemiterminadosList.iterator();
            //System.out.println("longitud" + lotesProductosSemiterminadosList.size());
            IngresosAlmacenDetalleAcond ingresosAlmacenDetalleAcond = new IngresosAlmacenDetalleAcond();
            while(i.hasNext()){
                ingresosAlmacenDetalleAcond = (IngresosAlmacenDetalleAcond) i.next();
                //System.out.println("entro iteracion" + ingresosAlmacenDetalleAcond.getChecked().booleanValue());
                if(ingresosAlmacenDetalleAcond.getChecked().booleanValue()==true){
                    break;
                }
            }
            double prorateo = this.hallaProrateo(ingresosAlmacenDetalleAcond);
            Connection con = null;
            con = Util.openConnection(con);
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            int codTipoMaterial = 0;
            List salidasAlmacenDetalleList = new ArrayList();
            codTipoMaterial = managedProgramaProduccion.conMaterial(1,configuracionSalidaMaterialProduccionPersonaList,configuracionSalidaAlmacenProduccion);
            String  consulta = "";
            if(codTipoMaterial==1){
                    consulta = " select m.COD_MATERIAL,m.NOMBRE_MATERIAL,u.COD_UNIDAD_MEDIDA,u.NOMBRE_UNIDAD_MEDIDA,fmdmp.CANTIDAD,um.abreviatura" +
                            " from FORMULA_MAESTRA fm  " +
                            " inner join FORMULA_MAESTRA_DETALLE_MP fmdmp on fm.COD_FORMULA_MAESTRA = fmdmp.COD_FORMULA_MAESTRA " +
                            " inner join MATERIALES m on m.COD_MATERIAL = fmdmp.COD_MATERIAL " +
                            " inner join UNIDADES_MEDIDA u on u.COD_UNIDAD_MEDIDA = fmdmp.COD_UNIDAD_MEDIDA" +
                            " inner join programa_produccion ppr on ppr.cod_compprod = fm.cod_compprod inner join ingresos_detalleacond ida on ida.cod_lote_produccion = ppr.cod_lote_produccion and ida.cod_compprod = ppr.cod_compprod" +
                            " where ida.cod_compprod = '"+ingresosAlmacenDetalleAcond.getComponentesProd().getCodCompprod()+"'" +
                            " and ida.cod_lote_produccion = '"+ingresosAlmacenDetalleAcond.getCodLoteProduccion()+"' and ppr.cod_estado_programa_prod = 6";
                            System.out.println("consulta 0" + consulta);


                    ResultSet rsMP = st.executeQuery(consulta);
                    while(rsMP.next()){
                        FormulaMaestraDetalleMP formulaMaestraDetalleMP = new FormulaMaestraDetalleMP();
                        formulaMaestraDetalleMP.setChecked(true);
                        formulaMaestraDetalleMP.getMateriales().setCodMaterial(rsMP.getString("COD_MATERIAL"));
                        formulaMaestraDetalleMP.getMateriales().setNombreMaterial(rsMP.getString("NOMBRE_MATERIAL"));
                        formulaMaestraDetalleMP.getUnidadesMedida().setCodUnidadMedida(rsMP.getInt("COD_UNIDAD_MEDIDA"));
                        formulaMaestraDetalleMP.getUnidadesMedida().setNombreUnidadMedida(rsMP.getString("NOMBRE_UNIDAD_MEDIDA"));
                        formulaMaestraDetalleMP.getUnidadesMedida().setAbreviatura(rsMP.getString("ABREVIATURA"));
                        formulaMaestraDetalleMP.setCantidad(managedProgramaProduccion.redondear((rsMP.getDouble("CANTIDAD")*prorateo)>0?rsMP.getDouble("CANTIDAD")*prorateo:rsMP.getDouble("CANTIDAD"),0));
                        materiaPrimaList.add(formulaMaestraDetalleMP);
                    }
                    rsMP.close();
             }
            
            String codEmpaque = "";
            String nombreEmpaque = "";
            codTipoMaterial = managedProgramaProduccion.conMaterial(2,configuracionSalidaMaterialProduccionPersonaList,configuracionSalidaAlmacenProduccion); //con el tipo de material 2 material de empaque primario
            empaquePrimarioList.clear();
            if(codTipoMaterial==1){
            // cargar presentacionPrimaria
            
            consulta = " select m.COD_MATERIAL,m.NOMBRE_MATERIAL,um.COD_UNIDAD_MEDIDA,um.NOMBRE_UNIDAD_MEDIDA,fmdep.CANTIDAD,um.abreviatura" +
                    " from INGRESOS_DETALLEACOND i inner join programa_produccion ppr on ppr.COD_COMPPROD = i.COD_COMPPROD " +
                    " and ppr.COD_LOTE_PRODUCCION = i.COD_LOTE_PRODUCCION and ppr.COD_ESTADO_PROGRAMA = 6 inner join " +
                    " FORMULA_MAESTRA fm on fm.COD_FORMULA_MAESTRA = ppr.COD_FORMULA_MAESTRA " +
                    " inner join FORMULA_MAESTRA_DETALLE_EP fmdep " +
                    " on fm.COD_FORMULA_MAESTRA = fmdep.COD_FORMULA_MAESTRA inner join PRESENTACIONES_PRIMARIAS p on p.COD_COMPPROD = ppr.COD_COMPPROD " +
                    " and p.COD_TIPO_PROGRAMA_PROD = ppr.COD_TIPO_PROGRAMA_PROD " +
                    " inner join materiales m on m.COD_MATERIAL = fmdep.COD_MATERIAL " +
                    "inner join UNIDADES_MEDIDA um on um.COD_UNIDAD_MEDIDA = fmdep.COD_UNIDAD_MEDIDA  " +
                    " where ppr.COD_COMPPROD = '"+ingresosAlmacenDetalleAcond.getComponentesProd().getCodCompprod()+"'" +
                    " and ppr.COD_LOTE_PRODUCCION = '"+ingresosAlmacenDetalleAcond.getCodLoteProduccion()+"'" +
                    " and i.COD_INGRESO_ACOND = '"+ingresosAlmacenDetalleAcond.getIngresosAcondicionamiento().getCodIngresoAcond()+"' ";


            System.out.println("consulta 1"+ consulta);
            ResultSet rs=st.executeQuery(consulta);

            while(rs.next()){
                FormulaMaestraDetalleEP formulaMaestraDetalleEP = new FormulaMaestraDetalleEP();
                formulaMaestraDetalleEP.setChecked(true);
                formulaMaestraDetalleEP.getMateriales().setCodMaterial(rs.getString("COD_MATERIAL"));
                formulaMaestraDetalleEP.getMateriales().setNombreMaterial(rs.getString("NOMBRE_MATERIAL"));
                formulaMaestraDetalleEP.setCantidad(Float.valueOf(String.valueOf(managedProgramaProduccion.redondear((rs.getDouble("CANTIDAD")*prorateo)>0?rs.getDouble("CANTIDAD")*prorateo:rs.getDouble("CANTIDAD"),0))));
                formulaMaestraDetalleEP.getUnidadesMedida().setCodUnidadMedida(rs.getInt("COD_UNIDAD_MEDIDA"));
                formulaMaestraDetalleEP.getUnidadesMedida().setNombreUnidadMedida(rs.getString("NOMBRE_UNIDAD_MEDIDA"));
                formulaMaestraDetalleEP.getUnidadesMedida().setAbreviatura(rs.getString("ABREVIATURA"));
                //codEmpaque = rs.getString("cod_envaseprim");
                //nombreEmpaque = rs.getString("nombre_envaseprim");
                empaquePrimarioList.add(formulaMaestraDetalleEP);
            }
            //presentacionesPrimariasList.add(new SelectItem("0","-NINGUNO-"));
            //presentacionesPrimariasList.clear();
            //presentacionesPrimariasList.add(new SelectItem(codEmpaque,nombreEmpaque)); //rs.getString("cod_presentacion_primaria"),rs.getString("nombre_envaseprim") + " x " + rs.getInt("CANTIDAD")
            rs.close();
            //while(rs.next()){

            //}
            }
            codTipoMaterial = managedProgramaProduccion.conMaterial(3,configuracionSalidaMaterialProduccionPersonaList,configuracionSalidaAlmacenProduccion); //con el tipo de material 3 material de empaque secundario
            empaqueSecundarioList.clear();
            if(codTipoMaterial==1){
            //cargar presentacionSecundaria
            
            consulta = " select m.COD_MATERIAL,m.NOMBRE_MATERIAL,um.COD_UNIDAD_MEDIDA,um.NOMBRE_UNIDAD_MEDIDA,fmdes.CANTIDAD,um.abreviatura" +
                    " from PROGRAMA_PRODUCCION_INGRESOS_ACOND ppria " +
                    " inner join COMPONENTES_PRESPROD p on p.COD_COMPPROD = ppria.COD_COMPPROD " +
                    " and p.COD_TIPO_PROGRAMA_PROD = ppria.COD_TIPO_PROGRAMA_PROD " +
                    " inner join FORMULA_MAESTRA fm on fm.COD_FORMULA_MAESTRA = ppria.COD_FORMULA_MAESTRA " +
                    " inner join FORMULA_MAESTRA_DETALLE_ES fmdes " +
                    " on fm.COD_FORMULA_MAESTRA = fmdes.COD_FORMULA_MAESTRA and fmdes.COD_PRESENTACION_PRODUCTO = p.COD_PRESENTACION " +
                    " inner join materiales m on m.COD_MATERIAL = fmdes.COD_MATERIAL " +
                    " inner join UNIDADES_MEDIDA um on um.COD_UNIDAD_MEDIDA = fmdes.COD_UNIDAD_MEDIDA " +
                    " where ppria.COD_COMPPROD = '"+ingresosAlmacenDetalleAcond.getComponentesProd().getCodCompprod()+"'" +
                    " and ppria.COD_LOTE_PRODUCCION = '"+ingresosAlmacenDetalleAcond.getCodLoteProduccion()+"'" +
                    " and ppria.COD_INGRESO_ACOND = '"+ingresosAlmacenDetalleAcond.getIngresosAcondicionamiento().getCodIngresoAcond()+"' ";
            consulta = " select m.COD_MATERIAL, m.NOMBRE_MATERIAL, um.COD_UNIDAD_MEDIDA, um.NOMBRE_UNIDAD_MEDIDA, fmdes.CANTIDAD, " +
                    " um.abreviatura,p.COD_PRESENTACION " +
                    " from INGRESOS_DETALLEACOND i inner join programa_produccion ppr on ppr.COD_COMPPROD = i.COD_COMPPROD " +
                    " and ppr.COD_LOTE_PRODUCCION = i.COD_LOTE_PRODUCCION and ppr.COD_ESTADO_PROGRAMA = 6 " +
                    " inner join COMPONENTES_PRESPROD p on p.COD_COMPPROD = ppr.COD_COMPPROD " +
                    " and p.COD_TIPO_PROGRAMA_PROD = ppr.COD_TIPO_PROGRAMA_PROD" +
                    " inner join FORMULA_MAESTRA fm on fm.COD_FORMULA_MAESTRA = ppr.COD_FORMULA_MAESTRA " +
                    "     inner join FORMULA_MAESTRA_DETALLE_ES fmdes on fm.COD_FORMULA_MAESTRA = fmdes.COD_FORMULA_MAESTRA " +
                    " and fmdes.COD_PRESENTACION_PRODUCTO =  p.COD_PRESENTACION " +
                    " inner join materiales m on m.COD_MATERIAL = fmdes.COD_MATERIAL " +
                    " inner join UNIDADES_MEDIDA um on um.COD_UNIDAD_MEDIDA = fmdes.COD_UNIDAD_MEDIDA " +
                    " where ppr.COD_COMPPROD = '"+ingresosAlmacenDetalleAcond.getComponentesProd().getCodCompprod()+"' and ppr.COD_LOTE_PRODUCCION = '"+ingresosAlmacenDetalleAcond.getCodLoteProduccion()+"'" +
                    " and i.COD_INGRESO_ACOND = '"+ingresosAlmacenDetalleAcond.getIngresosAcondicionamiento().getCodIngresoAcond()+"' ";
            System.out.println("consulta 2" + consulta);

            ResultSet rsPS=st.executeQuery(consulta);
            while(rsPS.next()){
                FormulaMaestraDetalleES formulaMaestraDetalleES = new FormulaMaestraDetalleES();
                formulaMaestraDetalleES.setChecked(true);
                formulaMaestraDetalleES.getMateriales().setCodMaterial(rsPS.getString("COD_MATERIAL"));
                formulaMaestraDetalleES.getMateriales().setNombreMaterial(rsPS.getString("NOMBRE_MATERIAL"));
                formulaMaestraDetalleES.setCantidad(Float.valueOf(String.valueOf(managedProgramaProduccion.redondear((rsPS.getDouble("CANTIDAD")*prorateo)>0?rsPS.getDouble("CANTIDAD")*prorateo:rsPS.getDouble("CANTIDAD"),0))));
                formulaMaestraDetalleES.getUnidadesMedida().setCodUnidadMedida(rsPS.getInt("COD_UNIDAD_MEDIDA"));
                formulaMaestraDetalleES.getUnidadesMedida().setNombreUnidadMedida(rsPS.getString("NOMBRE_UNIDAD_MEDIDA"));
                formulaMaestraDetalleES.getUnidadesMedida().setAbreviatura(rsPS.getString("ABREVIATURA"));
                //codEmpaque = rsPS.getString("cod_presentacion");
                //nombreEmpaque = rsPS.getString("NOMBRE_PRODUCTO_PRESENTACION");
                System.out.println("se cargo empaque secundario");
                empaqueSecundarioList.add(formulaMaestraDetalleES);
            }

            System.out.println("consulta "+ consulta);
            //presentacionesSecundariasList.clear();
            //presentacionesSecundariasList.add(new SelectItem("0","-NINGUNO-"));
            //presentacionesSecundariasList.add(new SelectItem(codEmpaque,nombreEmpaque)); //rsPS.getString("cod_presentacion"),rsPS.getString("NOMBRE_PRODUCTO_PRESENTACION")
            //while(rsPS.next()){

            //}
            rsPS.close();
            }
            ProgramaProduccion programaProduccion = new ProgramaProduccion();
            programaProduccion.setCodLoteProduccion(ingresosAlmacenDetalleAcond.getCodLoteProduccion());
            programaProduccion.getFormulaMaestra().setComponentesProd(ingresosAlmacenDetalleAcond.getComponentesProd());

            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            Map<String, Object> sessionMap = externalContext.getSessionMap();
            sessionMap.put("programaProduccion", programaProduccion);

            sessionMap.put("materiaPrimaList", materiaPrimaList);
            sessionMap.put("empaquePrimarioList", empaquePrimarioList);
            sessionMap.put("empaqueSecundarioList", empaqueSecundarioList);
            Utiles.direccionar("agregarSolicitudSalidaAlmacen.jsf");

            //empaquePrimarioList.clear();
            //empaqueSecundarioList.clear();

            st.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public boolean verificaMaterialesCantidadFaltante(IngresosAlmacenDetalleAcond ingresosAlmacenDetalleAcond){
        boolean verificaMaterialesFaltante = false;
        try {
            ConfiguracionSalidaAlmacenProduccion configuracionSalidaAlmacenProduccion = new ConfiguracionSalidaAlmacenProduccion();
            configuracionSalidaAlmacenProduccion.getTiposSalidaAlmacenProduccion().setCodTipoSalidaAlmacenProduccion(1);
            ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");

            
            
            Connection con = null;
            con = Util.openConnection(con);
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            int codTipoMaterial = 0;
            
            codTipoMaterial = managedProgramaProduccion.conMaterial(1,configuracionSalidaMaterialProduccionPersonaList,configuracionSalidaAlmacenProduccion);
            String  consulta = "";
            int conUnionAll = 0;
            if(codTipoMaterial==1){
                    consulta = " select m.COD_MATERIAL,fmdmp.CANTIDAD," +
                            " ( select sum(iade.CANTIDAD_PARCIAL - " +
                            " (select isnull(SUM(sadi.CANTIDAD),0) from SALIDAS_ALMACEN_DETALLE_INGRESO sadi inner join SALIDAS_ALMACEN_DETALLE sad on sadi.COD_SALIDA_ALMACEN = sad.COD_SALIDA_ALMACEN and sadi.COD_MATERIAL = sad.COD_MATERIAL" +
                            " inner join SALIDAS_ALMACEN s on s.COD_SALIDA_ALMACEN = sad.COD_SALIDA_ALMACEN" +
                            " where sadi.COD_INGRESO_ALMACEN = iade.COD_INGRESO_ALMACEN and sadi.COD_MATERIAL = iade.COD_MATERIAL and sadi.ETIQUETA = iade.ETIQUETA" +
                            " and s.COD_ESTADO_SALIDA_ALMACEN = 1 and s.ESTADO_SISTEMA = 1 and s.COD_ALMACEN = '"+usuario.getAlmacenesGlobal().getCodAlmacen()+"') " +
                            " +(select isnull(SUM(dde.CANTIDAD_DEVUELTA),0) from DEVOLUCIONES_DETALLE_ETIQUETAS dde inner join DEVOLUCIONES_DETALLE dd on dd.COD_DEVOLUCION = dde.COD_DEVOLUCION and dd.COD_MATERIAL = dde.COD_MATERIAL" +
                            " inner join DEVOLUCIONES d on d.COD_DEVOLUCION = dd.COD_DEVOLUCION" +
                            " where  dde.COD_INGRESO_ALMACEN = iade.COD_INGRESO_ALMACEN and dde.COD_MATERIAL = iade.COD_MATERIAL and dde.ETIQUETA = iade.ETIQUETA" +
                            " and d.COD_ESTADO_DEVOLUCION = 1 and d.ESTADO_SISTEMA = 1 and d.COD_ALMACEN = '"+usuario.getAlmacenesGlobal().getCodAlmacen()+"')) cantidad_r, iade.fecha_vencimiento, iade.lote_material_proveedor " +
                            " ,e.nombre_estado_material,ia.nro_ingreso_almacen,sec.nombre_seccion,ese.nombre_empaque_secundario_externo " +
                            " from INGRESOS_ALMACEN_DETALLE_ESTADO iade " +
                            " inner join INGRESOS_ALMACEN_DETALLE iad on iad.COD_INGRESO_ALMACEN = iade.COD_INGRESO_ALMACEN and iad.COD_MATERIAL = iade.COD_MATERIAL" +
                            " inner join INGRESOS_ALMACEN ia on ia.COD_INGRESO_ALMACEN = iad.COD_INGRESO_ALMACEN" +
                            " inner join ALMACENES a on a.COD_ALMACEN = ia.COD_ALMACEN" +
                            " inner join ESTADOS_MATERIAL e on e.COD_ESTADO_MATERIAL = iade.COD_ESTADO_MATERIAL" +
                            " inner join EMPAQUES_SECUNDARIO_EXTERNO ese on ese.COD_EMPAQUE_SECUNDARIO_EXTERNO = iade.COD_EMPAQUE_SECUNDARIO_EXTERNO " +
                            " inner join secciones sec on sec.COD_SECCION = iad.COD_SECCION " +
                            " where a.COD_ALMACEN = '"+usuario.getAlmacenesGlobal().getCodAlmacen()+"'  and ia.COD_ESTADO_INGRESO_ALMACEN = 1 and ia.ESTADO_SISTEMA = 1  " +
                            " and iade.CANTIDAD_RESTANTE>0 and iade.COD_ESTADO_MATERIAL = '2' " + //
                            " and iade.COD_MATERIAL = m.cod_material and ia.ESTADO_SISTEMA = 1 " +
                            " ) cantidad_restante" +
                            " from FORMULA_MAESTRA fm  " +
                            " inner join FORMULA_MAESTRA_DETALLE_MP fmdmp on fm.COD_FORMULA_MAESTRA = fmdmp.COD_FORMULA_MAESTRA " +
                            " inner join MATERIALES m on m.COD_MATERIAL = fmdmp.COD_MATERIAL " +
                            " inner join UNIDADES_MEDIDA u on u.COD_UNIDAD_MEDIDA = fmdmp.COD_UNIDAD_MEDIDA" +
                            " inner join programa_produccion_ingresos_acond ppria on ppria.cod_compprod = fm.cod_compprod" +
                            " where ppria.cod_compprod = '"+ingresosAlmacenDetalleAcond.getComponentesProd().getCodCompprod()+"'" +
                            " and ppria.cod_lote_produccion = '"+ingresosAlmacenDetalleAcond.getCodLoteProduccion()+"' ";
                            System.out.println("consulta " + consulta);
                            conUnionAll=1;
             }
            codTipoMaterial = managedProgramaProduccion.conMaterial(2,configuracionSalidaMaterialProduccionPersonaList,configuracionSalidaAlmacenProduccion); //con el tipo de material 2 material de empaque primario
            empaquePrimarioList.clear();
            if(codTipoMaterial==1){
            // cargar presentacionPrimaria
                if(conUnionAll == 1){consulta = consulta + " union all ";}

            consulta =consulta + " select m.COD_MATERIAL,fmdep.CANTIDAD," +
                    " ( select sum(iade.CANTIDAD_PARCIAL - " +
                    " (select isnull(SUM(sadi.CANTIDAD),0) from SALIDAS_ALMACEN_DETALLE_INGRESO sadi inner join SALIDAS_ALMACEN_DETALLE sad on sadi.COD_SALIDA_ALMACEN = sad.COD_SALIDA_ALMACEN and sadi.COD_MATERIAL = sad.COD_MATERIAL" +
                    " inner join SALIDAS_ALMACEN s on s.COD_SALIDA_ALMACEN = sad.COD_SALIDA_ALMACEN" +
                    " where sadi.COD_INGRESO_ALMACEN = iade.COD_INGRESO_ALMACEN and sadi.COD_MATERIAL = iade.COD_MATERIAL and sadi.ETIQUETA = iade.ETIQUETA" +
                    " and s.COD_ESTADO_SALIDA_ALMACEN = 1 and s.ESTADO_SISTEMA = 1 and s.COD_ALMACEN = '"+usuario.getAlmacenesGlobal().getCodAlmacen()+"') " +
                    " +(select isnull(SUM(dde.CANTIDAD_DEVUELTA),0) from DEVOLUCIONES_DETALLE_ETIQUETAS dde inner join DEVOLUCIONES_DETALLE dd on dd.COD_DEVOLUCION = dde.COD_DEVOLUCION and dd.COD_MATERIAL = dde.COD_MATERIAL" +
                    " inner join DEVOLUCIONES d on d.COD_DEVOLUCION = dd.COD_DEVOLUCION" +
                    " where  dde.COD_INGRESO_ALMACEN = iade.COD_INGRESO_ALMACEN and dde.COD_MATERIAL = iade.COD_MATERIAL and dde.ETIQUETA = iade.ETIQUETA" +
                    " and d.COD_ESTADO_DEVOLUCION = 1 and d.ESTADO_SISTEMA = 1 and d.COD_ALMACEN = '"+usuario.getAlmacenesGlobal().getCodAlmacen()+"')) cantidad_r, iade.fecha_vencimiento, iade.lote_material_proveedor " +
                    " ,e.nombre_estado_material,ia.nro_ingreso_almacen,sec.nombre_seccion,ese.nombre_empaque_secundario_externo " +
                    " from INGRESOS_ALMACEN_DETALLE_ESTADO iade " +
                    " inner join INGRESOS_ALMACEN_DETALLE iad on iad.COD_INGRESO_ALMACEN = iade.COD_INGRESO_ALMACEN and iad.COD_MATERIAL = iade.COD_MATERIAL" +
                    " inner join INGRESOS_ALMACEN ia on ia.COD_INGRESO_ALMACEN = iad.COD_INGRESO_ALMACEN" +
                    " inner join ALMACENES a on a.COD_ALMACEN = ia.COD_ALMACEN" +
                    " inner join ESTADOS_MATERIAL e on e.COD_ESTADO_MATERIAL = iade.COD_ESTADO_MATERIAL" +
                    " inner join EMPAQUES_SECUNDARIO_EXTERNO ese on ese.COD_EMPAQUE_SECUNDARIO_EXTERNO = iade.COD_EMPAQUE_SECUNDARIO_EXTERNO " +
                    " inner join secciones sec on sec.COD_SECCION = iad.COD_SECCION " +
                    " where a.COD_ALMACEN = '"+usuario.getAlmacenesGlobal().getCodAlmacen()+"'  and ia.COD_ESTADO_INGRESO_ALMACEN = 1 and ia.ESTADO_SISTEMA = 1  " +
                    " and iade.CANTIDAD_RESTANTE>0 and iade.COD_ESTADO_MATERIAL = '2' " + //
                    " and iade.COD_MATERIAL = m.cod_material and ia.ESTADO_SISTEMA = 1 " +
                    " ) cantidad_restante" +
                    " from PROGRAMA_PRODUCCION_INGRESOS_ACOND ppria inner join  " +
                    " FORMULA_MAESTRA fm on fm.COD_FORMULA_MAESTRA = ppria.COD_FORMULA_MAESTRA " +
                    " inner join FORMULA_MAESTRA_DETALLE_EP fmdep " +
                    " on fm.COD_FORMULA_MAESTRA = fmdep.COD_FORMULA_MAESTRA inner join PRESENTACIONES_PRIMARIAS p on p.COD_COMPPROD = ppria.COD_COMPPROD " +
                    " and p.COD_TIPO_PROGRAMA_PROD = ppria.COD_TIPO_PROGRAMA_PROD " +
                    " inner join materiales m on m.COD_MATERIAL = fmdep.COD_MATERIAL " +
                    "inner join UNIDADES_MEDIDA um on um.COD_UNIDAD_MEDIDA = fmdep.COD_UNIDAD_MEDIDA  " +
                    " where ppria.COD_COMPPROD = '"+ingresosAlmacenDetalleAcond.getComponentesProd().getCodCompprod()+"'" +
                    " and ppria.COD_LOTE_PRODUCCION = '"+ingresosAlmacenDetalleAcond.getCodLoteProduccion()+"'" +
                    " and ppria.COD_INGRESO_ACOND = '"+ingresosAlmacenDetalleAcond.getIngresosAcondicionamiento().getCodIngresoAcond()+"' ";
                System.out.println("consulta " + consulta);
                    conUnionAll = 1;
            }
            codTipoMaterial = managedProgramaProduccion.conMaterial(3,configuracionSalidaMaterialProduccionPersonaList,configuracionSalidaAlmacenProduccion); //con el tipo de material 3 material de empaque secundario
            empaqueSecundarioList.clear();
            if(codTipoMaterial==1){
            //cargar presentacionSecundaria
                if(conUnionAll == 1){consulta = consulta + " union all ";}
            consulta =consulta+ " select m.COD_MATERIAL,fmdes.CANTIDAD," +
                    " (select sum(cantidad_r) from ( select iade.CANTIDAD_PARCIAL - " +
                    " (select isnull(SUM(sadi.CANTIDAD),0) from SALIDAS_ALMACEN_DETALLE_INGRESO sadi inner join SALIDAS_ALMACEN_DETALLE sad on sadi.COD_SALIDA_ALMACEN = sad.COD_SALIDA_ALMACEN and sadi.COD_MATERIAL = sad.COD_MATERIAL" +
                    " inner join SALIDAS_ALMACEN s on s.COD_SALIDA_ALMACEN = sad.COD_SALIDA_ALMACEN" +
                    " where sadi.COD_INGRESO_ALMACEN = iade.COD_INGRESO_ALMACEN and sadi.COD_MATERIAL = iade.COD_MATERIAL and sadi.ETIQUETA = iade.ETIQUETA" +
                    " and s.COD_ESTADO_SALIDA_ALMACEN = 1 and s.ESTADO_SISTEMA = 1 and s.COD_ALMACEN = '"+usuario.getAlmacenesGlobal().getCodAlmacen()+"') " +
                    " +(select isnull(SUM(dde.CANTIDAD_DEVUELTA),0) from DEVOLUCIONES_DETALLE_ETIQUETAS dde inner join DEVOLUCIONES_DETALLE dd on dd.COD_DEVOLUCION = dde.COD_DEVOLUCION and dd.COD_MATERIAL = dde.COD_MATERIAL" +
                    " inner join DEVOLUCIONES d on d.COD_DEVOLUCION = dd.COD_DEVOLUCION" +
                    " where  dde.COD_INGRESO_ALMACEN = iade.COD_INGRESO_ALMACEN and dde.COD_MATERIAL = iade.COD_MATERIAL and dde.ETIQUETA = iade.ETIQUETA" +
                    " and d.COD_ESTADO_DEVOLUCION = 1 and d.ESTADO_SISTEMA = 1 and d.COD_ALMACEN = '"+usuario.getAlmacenesGlobal().getCodAlmacen()+"') cantidad_r, iade.fecha_vencimiento, iade.lote_material_proveedor " +
                    " ,e.nombre_estado_material,ia.nro_ingreso_almacen,sec.nombre_seccion,ese.nombre_empaque_secundario_externo " +
                    " from INGRESOS_ALMACEN_DETALLE_ESTADO iade " +
                    " inner join INGRESOS_ALMACEN_DETALLE iad on iad.COD_INGRESO_ALMACEN = iade.COD_INGRESO_ALMACEN and iad.COD_MATERIAL = iade.COD_MATERIAL" +
                    " inner join INGRESOS_ALMACEN ia on ia.COD_INGRESO_ALMACEN = iad.COD_INGRESO_ALMACEN" +
                    " inner join ALMACENES a on a.COD_ALMACEN = ia.COD_ALMACEN" +
                    " inner join ESTADOS_MATERIAL e on e.COD_ESTADO_MATERIAL = iade.COD_ESTADO_MATERIAL" +
                    " inner join EMPAQUES_SECUNDARIO_EXTERNO ese on ese.COD_EMPAQUE_SECUNDARIO_EXTERNO = iade.COD_EMPAQUE_SECUNDARIO_EXTERNO " +
                    " inner join secciones sec on sec.COD_SECCION = iad.COD_SECCION " +
                    " where a.COD_ALMACEN = '"+usuario.getAlmacenesGlobal().getCodAlmacen()+"'  and ia.COD_ESTADO_INGRESO_ALMACEN = 1 and ia.ESTADO_SISTEMA = 1  " +
                    " and iade.CANTIDAD_RESTANTE>0 and iade.COD_ESTADO_MATERIAL = '2' " + //
                    " and iade.COD_MATERIAL = m.cod_material and ia.ESTADO_SISTEMA = 1 " +
                    " )as tabla ) cantidad_restante" +
                    " from PROGRAMA_PRODUCCION_INGRESOS_ACOND ppria " +
                    " inner join COMPONENTES_PRESPROD p on p.COD_COMPPROD = ppria.COD_COMPPROD " +
                    " and p.COD_TIPO_PROGRAMA_PROD = ppria.COD_TIPO_PROGRAMA_PROD " +
                    " inner join FORMULA_MAESTRA fm on fm.COD_FORMULA_MAESTRA = ppria.COD_FORMULA_MAESTRA " +
                    " inner join FORMULA_MAESTRA_DETALLE_ES fmdes " +
                    " on fm.COD_FORMULA_MAESTRA = fmdes.COD_FORMULA_MAESTRA and fmdes.COD_PRESENTACION_PRODUCTO = p.COD_PRESENTACION " +
                    " inner join materiales m on m.COD_MATERIAL = fmdes.COD_MATERIAL " +
                    " inner join UNIDADES_MEDIDA um on um.COD_UNIDAD_MEDIDA = fmdes.COD_UNIDAD_MEDIDA " +
                    " where ppria.COD_COMPPROD = '"+ingresosAlmacenDetalleAcond.getComponentesProd().getCodCompprod()+"'" +
                    " and ppria.COD_LOTE_PRODUCCION = '"+ingresosAlmacenDetalleAcond.getCodLoteProduccion()+"'" +
                    " and ppria.COD_INGRESO_ACOND = '"+ingresosAlmacenDetalleAcond.getIngresosAcondicionamiento().getCodIngresoAcond()+"' ";
                System.out.println("consulta " + consulta);
            }
            ResultSet rs = st.executeQuery(consulta);
            while(rs.next()){
                if(rs.getFloat("cantidad_restante")-rs.getFloat("cantidad")<0){
                    verificaMaterialesFaltante=true;
                    break;
                }
            }


            //empaquePrimarioList.clear();
            //empaqueSecundarioList.clear();

            st.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return verificaMaterialesFaltante;
    }
}
