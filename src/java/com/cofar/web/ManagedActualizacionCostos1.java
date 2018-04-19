/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.web;

import com.cofar.bean.Almacenes;
import com.cofar.bean.Gestiones;
import com.cofar.bean.Materiales;
import com.cofar.bean.KardexItemMovimiento;
import com.cofar.bean.KardexMovimiento;
import com.cofar.bean.TipoCambiosMoneda;
import com.cofar.util.Util;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.joda.time.DateTime;

/**
 *
 * @author hvaldivia
 */

public class ManagedActualizacionCostos1 {
    List filialesList = new ArrayList();
    List almacenesList = new ArrayList();
    List capitulosList = new ArrayList();
    List gruposList = new ArrayList();
    Materiales materiales = new Materiales();
    ManagedSecciones managedSecciones = new ManagedSecciones();    
    Almacenes almacenes = new Almacenes();
    List materialesList = new ArrayList();
    ManagedRegistroSalidaAlmacen managedRegistroSalidaAlmacen = new ManagedRegistroSalidaAlmacen();
    ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
    Date fechaInicio = new Date();
    Date fechaFinal = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    SimpleDateFormat fh = new SimpleDateFormat("HH:mm:ss");
    List kardexItemMovimientoList = new ArrayList();
    Gestiones gestiones = new Gestiones();
    String[] codCapitulos;
    String[] codGrupos;
    String mensaje = "";
    List kardexMovimientoList = new ArrayList();
    
    
    public List getAlmacenesList() {
        return almacenesList;
    }

    public void setAlmacenesList(List almacenesList) {
        this.almacenesList = almacenesList;
    }

    public List getCapitulosList() {
        return capitulosList;
    }

    public void setCapitulosList(List capitulosList) {
        this.capitulosList = capitulosList;
    }

    public List getFilialesList() {
        return filialesList;
    }

    public void setFilialesList(List filialesList) {
        this.filialesList = filialesList;
    }

    public List getGruposList() {
        return gruposList;
    }

    public void setGruposList(List gruposList) {
        this.gruposList = gruposList;
    }

    public ManagedSecciones getManagedSecciones() {
        return managedSecciones;
    }

    public void setManagedSecciones(ManagedSecciones managedSecciones) {
        this.managedSecciones = managedSecciones;
    }

    public Materiales getMateriales() {
        return materiales;
    }

    public void setMateriales(Materiales materiales) {
        this.materiales = materiales;
    }

    public Almacenes getAlmacenes() {
        return almacenes;
    }

    public void setAlmacenes(Almacenes almacenes) {
        this.almacenes = almacenes;
    }

    public List getMaterialesList() {
        return materialesList;
    }

    public void setMaterialesList(List materialesList) {
        this.materialesList = materialesList;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public List getKardexItemMovimientoList() {
        return kardexItemMovimientoList;
    }

    public void setKardexItemMovimientoList(List kardexItemMovimientoList) {
        this.kardexItemMovimientoList = kardexItemMovimientoList;
    }

    public String[] getCodCapitulos() {
        return codCapitulos;
    }

    public void setCodCapitulos(String[] codCapitulos) {
        this.codCapitulos = codCapitulos;
    }

    public String[] getCodGrupos() {
        return codGrupos;
    }

    public void setCodGrupos(String[] codGrupos) {
        this.codGrupos = codGrupos;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List getKardexMovimientoList() {
        return kardexMovimientoList;
    }

    public void setKardexMovimientoList(List kardexMovimientoList) {
        this.kardexMovimientoList = kardexMovimientoList;
    }


    
    

    

    /** Creates a new instance of ManagedActualizacionCostos */
    public ManagedActualizacionCostos1() {
    }

    public String getCargarReporteKardexExistenciaValorado(){
        try {
            filialesList = managedSecciones.cargarFiliales();
            almacenesList.clear();
            almacenesList.add(new SelectItem("0", "-NINGUNO-"));
            capitulosList = managedRegistroSalidaAlmacen.cargarCapitulos();            
            gruposList.clear();
            gruposList.add(new SelectItem("0","-NINGUNO-"));
            almacenes = new Almacenes();
            materiales = new Materiales();
            

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String filial_change(){
        almacenesList = managedSecciones.cargarAlmacenes(almacenes.getFiliales().getCodFilial());
        return null;
    }

    public String buscarMaterial_action(){
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select m.COD_MATERIAL,m.NOMBRE_MATERIAL, gr.COD_GRUPO,gr.NOMBRE_GRUPO,c.COD_CAPITULO,c.NOMBRE_CAPITULO,um.nombre_unidad_medida,um.abreviatura" +
                    " from MATERIALES m, GRUPOS gr, CAPITULOS c,ALMACENES a, " +
                    " SECCIONES s, SECCIONES_DETALLE sd,unidades_medida um " +
                    " where m.COD_GRUPO = gr.COD_GRUPO " +
                    " and gr.COD_CAPITULO = c.COD_CAPITULO " +
                    " and a.COD_ALMACEN = s.COD_ALMACEN " +
                    " and s.COD_SECCION = sd.COD_SECCION " +
                    " and ((m.COD_MATERIAL = sd.COD_MATERIAL)" +
                    " or(gr.COD_GRUPO = sd.COD_GRUPO "+(materiales.getGrupos().getCodGrupo()>0?" and gr.COD_GRUPO='"+materiales.getGrupos().getCodGrupo()+"'":"")+"  )" +
                    " or(c.COD_CAPITULO = sd.COD_CAPITULO "+(materiales.getGrupos().getCapitulos().getCodCapitulo()>0?" and c.COD_CAPITULO='"+materiales.getGrupos().getCapitulos().getCodCapitulo()+"'" :"")+" )) " +
                    " and a.COD_ALMACEN = "+almacenes.getCodAlmacen()+" " +
                    " and m.NOMBRE_MATERIAL like '"+materiales.getNombreMaterial()+"%' and m.cod_unidad_medida =um.cod_unidad_medida and m.MOVIMIENTO_ITEM = 1 and m.COD_ESTADO_REGISTRO = 1 " +
                    " order by m.NOMBRE_MATERIAL asc  ";
            
            System.out.println("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            materialesList.clear();
            while(rs.next()){
                Materiales materialesItem = new Materiales();
                materialesItem.setCodMaterial(rs.getString("COD_MATERIAL"));
                materialesItem.setNombreMaterial(rs.getString("NOMBRE_MATERIAL"));
                materialesItem.getGrupos().setCodGrupo(rs.getInt("COD_GRUPO"));
                materialesItem.getGrupos().setNombreGrupo(rs.getString("NOMBRE_GRUPO"));
                materialesItem.getGrupos().getCapitulos().setCodCapitulo(rs.getInt("COD_CAPITULO"));
                materialesItem.getGrupos().getCapitulos().setNombreCapitulo(rs.getString("NOMBRE_CAPITULO"));
                materialesItem.getUnidadesMedida().setNombreUnidadMedida(rs.getString("nombre_unidad_medida"));
                materialesItem.getUnidadesMedida().setAbreviatura(rs.getString("abreviatura"));
                materialesList.add(materialesItem);
            }
            

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public String capitulos_change(){
        try {
            gruposList = managedRegistroSalidaAlmacen.cargarGrupos(materiales.getGrupos().getCapitulos().getCodCapitulo());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
     public String capitulos_change1(){
        try {
            String capitulos = "";
            for(int i=0;i< codCapitulos.length;i++){
                capitulos = capitulos + codCapitulos[i]+"," ;                
                //System.out.println("elemento " + codCapitulos[i] );
            }
            capitulos = capitulos + "0";
            //capitulos = capitulos.substring(0,capitulos.length()-1);

                Connection con = null;
                con = Util.openConnection(con);
                String consulta = " SELECT GR.COD_GRUPO,GR.NOMBRE_GRUPO FROM GRUPOS GR WHERE GR.COD_CAPITULO in ("+ capitulos +") AND GR.COD_ESTADO_REGISTRO = 1 ";
                System.out.println("consulta " + consulta);
                Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs = st.executeQuery(consulta);
                gruposList.clear();
                //gruposList.add(new SelectItem("0", "-NINGUNO-"));
                while(rs.next()){
                    gruposList.add(new SelectItem(rs.getString("COD_GRUPO"), rs.getString("NOMBRE_GRUPO")));
                }
                rs.close();
                con.close();
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

     public String buscarMaterial_action1(){
        try {
            //obtener los capitulos y los grupos
             String capitulos = "";
            for(int i=0;i< codCapitulos.length;i++){
                capitulos = capitulos + codCapitulos[i]+"," ;
                //System.out.println("elemento " + codCapitulos[i] );
            }
             capitulos = capitulos + "0";
             
            String  grupos = "";
            for(int i=0;i< codGrupos.length;i++){
                 grupos= grupos  + codGrupos[i]+"," ;
                //System.out.println("elemento " + codCapitulos[i] );
            }
            grupos = grupos + "0";

            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select m.COD_MATERIAL,m.NOMBRE_MATERIAL, gr.COD_GRUPO,gr.NOMBRE_GRUPO,c.COD_CAPITULO,c.NOMBRE_CAPITULO  " +
                    " from MATERIALES m inner join GRUPOS gr on gr.COD_GRUPO = m.COD_GRUPO " +
                    " inner join CAPITULOS c on c.COD_CAPITULO = gr.COD_CAPITULO " +
                    " where m.NOMBRE_MATERIAL like '%"+materiales.getNombreMaterial()+"%' " +
                    " "+(codCapitulos.length>0?" and c.COD_CAPITULO in("+capitulos+") " :"")+"  " +
                    " "+(codGrupos.length>0?" and gr.COD_GRUPO in("+grupos+") " :"")+" " +
                    " order by m.NOMBRE_MATERIAL asc ";
            
            System.out.println("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            materialesList.clear();
            while(rs.next()){
                Materiales materialesItem = new Materiales();
                materialesItem.setCodMaterial(rs.getString("COD_MATERIAL"));
                materialesItem.setNombreMaterial(rs.getString("NOMBRE_MATERIAL"));
                materialesItem.getGrupos().setCodGrupo(rs.getInt("COD_GRUPO"));
                materialesItem.getGrupos().setNombreGrupo(rs.getString("NOMBRE_GRUPO"));
                materialesItem.getGrupos().getCapitulos().setCodCapitulo(rs.getInt("COD_CAPITULO"));
                materialesItem.getGrupos().getCapitulos().setNombreCapitulo(rs.getString("NOMBRE_CAPITULO"));
                materialesList.add(materialesItem);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    

    public String getCargarActualizacionCostos(){
        try {
            capitulosList = managedRegistroSalidaAlmacen.cargarCapitulos();
            capitulosList.remove(0);
            materialesList.clear();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public String actualizarCostos_action(){
        try {
            this.actualizarCostos();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void actualizarCostos(){
        try {
            // proceso de actualizacion de costos

            Iterator i = materialesList.iterator();
            Materiales materiales = new Materiales();
            while(i.hasNext()){
                Materiales materialesItem = (Materiales)i.next();
                if(materialesItem.getChecked().booleanValue()==true){
                    kardexItemMovimientoList.clear();
                    
                    materiales = materialesItem;
                    Connection con = null;
                    con = Util.openConnection(con);

//                    String consulta = " delete from kardex_item_movimiento where cod_persona= '"+usuario.getUsuarioModuloBean().getCodUsuarioGlobal()+"' ";
//                    System.out.println("consulta " + consulta);
//                    Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//                    st.executeUpdate(consulta);


                    Date fechaAnterior = new Date();
                    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy");
                    SimpleDateFormat sdf2 = new SimpleDateFormat("MM");
                    SimpleDateFormat sdf3 = new SimpleDateFormat("dd");



                    // seleccion del tipo de cambio del mes pasado o dia anterior
                    DateTime dt = new DateTime();
                    dt = dt.withYear(Integer.valueOf(sdf1.format(fechaInicio)));
                    dt = dt.withMonthOfYear(Integer.valueOf(sdf2.format(fechaInicio)));
                    dt = dt.withDayOfMonth(Integer.valueOf(sdf3.format(fechaInicio)));
                    DateTime dt1 = dt.minusDays(1);
                    fechaAnterior = dt1.toDate();
                    System.out.println("la fecha Anterior "  + fechaAnterior);
                    double ufvUltimoFechaAnterior = this.tipoCambio(fechaAnterior);

                    // se halla la el ultimo costo unitario segun la fecha de actualizacion para el item

                    double costoUnitarioActualizadoAnterior = 0;
                    Date fechaActualizadoAnterior = new Date();
                    double ufvActualizacionAnterior = 0;

                    String consulta = " SELECT top 1 sadi.fecha_actualizacion,  sadi.costo_salida_actualizado_final,(SELECT top 1 cambio FROM tipocambios_moneda " +
                            " where cod_moneda=4 and fecha<=sadi.FECHA_ACTUALIZACION order by fecha desc) ufv " +
                            " from SALIDAS_ALMACEN_DETALLE_INGRESO sadi, salidas_almacen sa " +
                            " where sadi.cod_material = '"+materiales.getCodMaterial()+"' and sadi.fecha_actualizacion <= '"+sdf.format(fechaAnterior)+" 23:59:59' " +
                            " and sa.cod_almacen = '"+usuario.getAlmacenesGlobal().getCodAlmacen()+"' AND sadi.costo_salida_actualizado_final > 0 " +
                            " and sadi.cod_salida_almacen = sa.cod_salida_almacen and sa.cod_estado_salida_almacen = 1 " +
                            " order by sadi.fecha_actualizacion desc ";
                    System.out.println("consulta " + consulta);
                    Statement st1 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    ResultSet rs1 = st1.executeQuery(consulta);
                    Date fechaActualizacionAnterior1 = new Date();
                    double costoActualizacionAnterior1 = 0;
                    double ufvAnterior1 = 0;
                    if(rs1.next()){
                        fechaActualizacionAnterior1 = rs1.getDate("fecha_actualizacion");
                        costoActualizacionAnterior1 = rs1.getDouble("costo_salida_actualizado_final");
                        ufvAnterior1 = rs1.getDouble("ufv");
                    }

                    consulta = " SELECT top 1 iade.fecha_actualizacion,  iade.costo_unitario_actualizado_final,(SELECT top 1 cambio FROM tipocambios_moneda " +
                            " where cod_moneda=4 and fecha<=iade.FECHA_ACTUALIZACION order by fecha desc) ufv  " +
                            " from INGRESOS_ALMACEN_DETALLE iade, ingresos_almacen ia " +
                            " where iade.cod_material = '"+materiales.getCodMaterial()+"' and iade.fecha_actualizacion <= '"+sdf.format(fechaAnterior)+" 23:59:59' " +
                            " and  ia.cod_almacen ='"+usuario.getAlmacenesGlobal().getCodAlmacen()+"' AND iade.costo_unitario_actualizado_final > 0 " +
                            " and iade.cod_ingreso_almacen = ia.cod_ingreso_almacen " +
                            " and ia.cod_estado_ingreso_almacen = 1 order by iade.fecha_actualizacion desc ";
                    System.out.println("consulta " + consulta);

                    Date fechaActualizacionAnterior2 = new Date();
                    double costoActualizacionAnterior2 = 0;
                    double ufvAnterior2 = 0;
                    Statement st2 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    ResultSet rs2 = st2.executeQuery(consulta);
                    if(rs1.next()){
                        fechaActualizacionAnterior2 = rs2.getDate("fecha_actualizacion");
                        costoActualizacionAnterior2 = rs2.getDouble("costo_unitario_actualizado_final");
                        ufvAnterior2 = rs2.getDouble("ufv");
                    }
                    if(fechaActualizacionAnterior1.compareTo(fechaActualizacionAnterior2)>0){
                        fechaActualizadoAnterior = fechaActualizacionAnterior1;
                        costoUnitarioActualizadoAnterior = costoActualizacionAnterior1 ;
                        ufvActualizacionAnterior = ufvAnterior1;
                    }else{
                        fechaActualizadoAnterior = fechaActualizacionAnterior2;
                        costoUnitarioActualizadoAnterior = costoActualizacionAnterior2 ;
                        ufvActualizacionAnterior = ufvAnterior2;
                    }

                    // hallar el costo unitario de la tabla de costos
                    consulta = " select top 1 c.COD_GESTION,  c.COD_MES,  c.COD_MATERIAL,  c.COD_ALMACEN,  c.COSTO_MATERIAL,  c.FECHA,  c.AJUSTE,(SELECT top 1 t.cambio FROM tipocambios_moneda t " +
                            " where t.cod_moneda=4 and t.fecha<=c.fecha order by t.fecha desc) ufv " +
                            " from COSTOS_MATERIAL_POR_MES c where c.cod_mes= MONTH('"+sdf.format(fechaAnterior)+"') " +
                            " and c.fecha<='"+sdf.format(fechaAnterior)+"' and c.cod_material='"+materiales.getCodMaterial()+"' " +
                            " and c.cod_almacen= '"+usuario.getAlmacenesGlobal().getCodAlmacen()+"' order by c.fecha desc ";
                    System.out.println("consulta " + consulta);
                    Statement st3 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    ResultSet rs3 = st3.executeQuery(consulta);
                    if(rs3.next()){
                        fechaActualizadoAnterior = rs3.getDate("FECHA") ;
                        costoUnitarioActualizadoAnterior = rs3.getDouble("COSTO_MATERIAL");
                        ufvActualizacionAnterior = rs3.getDouble("ufv");
                    }

                    //hallar el saldo anterior
                    double ingresoTotalAnterior = 0;
                    double salidaTotalAnterior = 0;
                    double saldoTotalAnterior = 0;


                    consulta = " SELECT sum(iad.cant_total_ingreso_fisico)as ingresos_total_anterior " +
                            " FROM ingresos_almacen_detalle iad,ingresos_almacen ia " +
                            " where iad.cod_ingreso_almacen=ia.cod_ingreso_almacen " +
                            " and ia.cod_estado_ingreso_almacen=1 " +
                            " and iad.cod_material='"+materiales.getCodMaterial() +"' " +
                            " and ia.cod_almacen='"+usuario.getAlmacenesGlobal().getCodAlmacen() +"' " +
                            " and ia.fecha_ingreso_almacen<='"+sdf.format(fechaAnterior)+" 23:59:59' " ;
                    System.out.println("consulta " + consulta);
                    Statement st4 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    ResultSet rs4 = st4.executeQuery(consulta);
                    if(rs4.next()){
                        ingresoTotalAnterior = rs4.getDouble("ingresos_total_anterior");
                    }


                    consulta = " select sum(sad.cantidad_salida_almacen)as salidas_total_anterior " +
                            " from salidas_almacen_detalle sad,salidas_almacen sa " +
                            "where sad.cod_salida_almacen=sa.cod_salida_almacen  " +
                            "and sa.cod_estado_salida_almacen=1 " +
                            "and sad.cod_material='"+materiales.getCodMaterial()+"'  " +
                            "and sa.cod_almacen='"+usuario.getAlmacenesGlobal().getCodAlmacen()+"'  " +
                            " and sa.fecha_salida_almacen<='"+sdf.format(fechaAnterior)+" 23:59:59'  ";
                    System.out.println("consulta " + consulta);
                    Statement st5 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    ResultSet rs5 = st5.executeQuery(consulta);
                    if(rs5.next()){
                        salidaTotalAnterior = rs5.getDouble("salidas_total_anterior");
                    }
                    saldoTotalAnterior = ingresoTotalAnterior - salidaTotalAnterior;

                    //float ufvPromedio = ufvActualizacionAnterior>0?ufvUltimoFechaAnterior/ufvActualizacionAnterior:0;
                    double montoAnteriorActualizado = saldoTotalAnterior*costoUnitarioActualizadoAnterior;

                    //se iteran los ingresos u se guardan en kardex de movimiento

                    consulta = " select * from (SELECT  ia.cod_almacen,iad.cod_ingreso_almacen,iad.costo_unitario," +
                            " iad.costo_promedio,ia.cod_tipo_ingreso_almacen,iad.cant_total_ingreso_fisico,0 cantidad_salida_almacen, " +
                            " iad.cod_material,um.abreviatura,(select sa.cod_lote_produccion from salidas_almacen sa, devoluciones d where d.cod_salida_almacen=sa.cod_salida_almacen and d.cod_devolucion=ia.cod_devolucion)as cod_lote_produccion, " +
                            " ia.nro_ingreso_almacen,tia.nombre_tipo_ingreso_almacen, " +
                            " ia.obs_ingreso_almacen,ia.fecha_ingreso_almacen,0 cod_area_empresa,0 cantidad_valorada,'Ingreso' tipo,costo_unitario*cant_total_ingreso_fisico debe," +
                            " (SELECT top 1 cambio FROM tipocambios_moneda where cod_moneda=4 and fecha<= ia.fecha_ingreso_almacen order by fecha desc) ufv " +
                            " FROM ingresos_almacen_detalle iad, ingresos_almacen ia,tipos_ingreso_almacen tia, unidades_medida um " +
                            " where iad.cod_ingreso_almacen=ia.cod_ingreso_almacen " +
                            " and ia.cod_estado_ingreso_almacen=1 and iad.cod_unidad_medida=um.cod_unidad_medida and ia.cod_tipo_ingreso_almacen=tia.cod_tipo_ingreso_almacen " +
                            " and iad.cod_material="+materiales.getCodMaterial()+" and ia.cod_almacen='"+usuario.getAlmacenesGlobal().getCodAlmacen()+"' " +
                            " and ia.fecha_ingreso_almacen>='"+sdf.format(fechaInicio)+" 00:00:00'  " +
                            " and ia.fecha_ingreso_almacen<='"+sdf.format(fechaFinal)+" 23:59:59' " +
                            " union all " +
                            " SELECT  sa.cod_almacen,sad.cod_salida_almacen,(select top 1 sadi.costo_salida from salidas_almacen_detalle_ingreso sadi where sadi.cod_salida_almacen=sad.cod_salida_almacen and sadi.cod_material=sad.cod_material)as costo_unitario,0 costo_promedio" +
                            " ,sa.cod_tipo_salida_almacen,0 cant_total_ingreso_fisico,sad.cantidad_salida_almacen " +
                            " ,sad.cod_material,um.abreviatura,sa.cod_lote_produccion " +
                            " ,sa.nro_salida_almacen,tsa.nombre_tipo_salida_almacen,sa.obs_salida_almacen " +
                            " ,sa.fecha_salida_almacen,sa.cod_area_empresa, " +
                            " (select sum(sadi.cantidad*sadi.costo_salida) from salidas_almacen_detalle_ingreso sadi " +
                            " where sadi.cod_salida_almacen=sad.cod_salida_almacen and sadi.cod_material=sad.cod_material)as cantidad_valorada,'Salida' tipo, 0 debe," +
                            "(SELECT top 1 cambio FROM tipocambios_moneda where cod_moneda=4 and fecha<= sa.fecha_salida_almacen order by fecha desc) ufv " +
                            " FROM salidas_almacen_detalle sad,salidas_almacen sa,tipos_salidas_almacen tsa,unidades_medida um " +
                            " where sad.cod_salida_almacen=sa.cod_salida_almacen " +
                            " and sa.cod_estado_salida_almacen=1 " +
                            " and sad.cod_unidad_medida=um.cod_unidad_medida " +
                            " and sa.cod_tipo_salida_almacen=tsa.cod_tipo_salida_almacen " +
                            " and sad.cod_material= '"+materiales.getCodMaterial()+"' and sa.cod_almacen='"+usuario.getAlmacenesGlobal().getCodAlmacen()+"' " +
                            " and sa.fecha_salida_almacen>='"+sdf.format(fechaInicio)+" 00:00:00' " +
                            " and sa.fecha_salida_almacen<='"+sdf.format(fechaFinal)+" 23:59:59' ) as tabla order by fecha_ingreso_almacen ";

                    System.out.println("consulta " + consulta);
                    Statement st6 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    ResultSet rs6 = st6.executeQuery(consulta);
                    int codReporte = 1;
                    kardexItemMovimientoList.clear();
                    while(rs6.next()){
                        KardexItemMovimiento kardexItemMovimiento = new KardexItemMovimiento();
                        kardexItemMovimiento.setCodReporte(codReporte);
                        kardexItemMovimiento.getPersonal().setCodPersonal(usuario.getUsuarioModuloBean().getCodUsuarioGlobal());
                        kardexItemMovimiento.getAlmacenes().setCodAlmacen(rs6.getInt("cod_almacen"));
                        kardexItemMovimiento.setTipo(rs6.getString("tipo"));
                        kardexItemMovimiento.setCodigo(rs6.getInt("cod_ingreso_almacen"));
                        kardexItemMovimiento.setNumero(rs6.getInt("nro_ingreso_almacen"));
                        kardexItemMovimiento.setFecha(rs6.getDate("fecha_ingreso_almacen"));
                        kardexItemMovimiento.getMateriales().setCodMaterial(rs6.getString("cod_material"));
                        kardexItemMovimiento.getUnidadesMedida().setAbreviatura(rs6.getString("abreviatura"));
                        kardexItemMovimiento.setCantidadIngreso(rs6.getDouble("cant_total_ingreso_fisico"));
                        kardexItemMovimiento.setCantidadSalida(rs6.getDouble("cantidad_salida_almacen"));
                        kardexItemMovimiento.setTipoIngresoSalida(rs6.getString("nombre_tipo_ingreso_almacen") );
                        kardexItemMovimiento.setObsIngresoSalida(rs6.getString("obs_ingreso_almacen"));
                        kardexItemMovimiento.setSaldo(0);
                        kardexItemMovimiento.setCostoUnitario(rs6.getDouble("costo_unitario"));
                        kardexItemMovimiento.setCostoPromedio(rs6.getDouble("costo_unitario"));
                        kardexItemMovimiento.setDebe(rs6.getDouble("debe"));
                        kardexItemMovimiento.setHaber(rs6.getDouble("cantidad_valorada"));
                        kardexItemMovimiento.setSaldoDinero(0);
                        kardexItemMovimiento.setTipoCambio(0);
                        //kardexItemMovimiento.setCostoPromedio(rs6.getDouble("costo_promedio"));
                        kardexItemMovimiento.setValorUfv(rs6.getDouble("ufv"));
                        kardexItemMovimiento.setCodTipoIngresoSalida(rs6.getInt("cod_tipo_ingreso_almacen"));
                        kardexItemMovimiento.setCodLoteProduccion(rs6.getString("cod_lote_produccion"));
                        kardexItemMovimiento.getAreasEmpresa().setCodAreaEmpresa(rs6.getString("cod_area_empresa"));
                        codReporte++;
                        kardexItemMovimientoList.add(kardexItemMovimiento);
                    }
                    //iteracion del detalle de kardex item movimiento
                    //float ufvFechaInicio = this.tipoCambio(fechaInicio);
                    Iterator i1 = kardexItemMovimientoList.iterator();


                    //primera actualizacion respecto a la anterior cantidad
                    //sacamos la primera fila del reporte
                    //System.out.println(" montoAnterior " +montoAnterior);
                    //System.out.println(" costoUnitarioActualizadoAnterior" + costoUnitarioActualizadoAnterior);
                    //System.out.println(" ufvActualizacionAnterior " + ufvActualizacionAnterior );
                    //System.out.println(" ufvUltimoFechaAnterior " + ufvUltimoFechaAnterior);

                    double saldoAnterior = 0;

                    saldoAnterior = saldoTotalAnterior;
                    //System.out.println("saldo:" + saldo);


                    double montoAnterior = saldoTotalAnterior*costoUnitarioActualizadoAnterior;
                    double ufvAnterior = ufvActualizacionAnterior;
                    double montoActualizado = 0;
                    double costoUnitario = costoUnitarioActualizadoAnterior;

                    System.out.println(" datos iniciales "  + " montoAnterior " + montoAnterior + " ufvAnterior " + ufvAnterior + " saldoAnterior " + saldoAnterior + "  costoUnitario " + costoUnitario);


                    Date fechaAnterior1 = fechaInicio;

                    List<KardexItemMovimiento> kardexItemMovimientoList1 = new ArrayList<KardexItemMovimiento>();
                    KardexItemMovimiento kardexItemMovimiento = new KardexItemMovimiento();
                    kardexItemMovimiento.setSaldo(saldoAnterior);
                    kardexItemMovimiento.setSaldoDinero(montoAnterior);
                    kardexItemMovimiento.setCostoUnitario(costoUnitario);
                    kardexItemMovimiento.setValorUfv(ufvAnterior);
                    kardexItemMovimiento.setAlmacenes(usuario.getAlmacenesGlobal());
                    kardexItemMovimiento.setMateriales(materiales);
                    
                    
                    while(i1.hasNext()){
                        kardexItemMovimiento = (KardexItemMovimiento)i1.next();


                        if(kardexItemMovimiento.getFecha().compareTo(fechaAnterior1)!=0){

                                double ufvPromedio = ufvAnterior>0?kardexItemMovimiento.getValorUfv()/ufvAnterior:0;
                                montoActualizado = montoAnterior*ufvPromedio;


                                // calculo del costo unitario
                                if(saldoAnterior>0.0001){
                                    costoUnitario = (montoAnterior/ saldoAnterior)*(ufvPromedio); //montoAnterior
                                }
                                //the same
                                if(saldoAnterior>0.0001){
                                    costoUnitario = (montoActualizado/ saldoAnterior); //montoAnterior
                                }



                                System.out.println("ufv1:" + kardexItemMovimiento.getValorUfv());
                                System.out.println("ufv2:" + ufvAnterior);
                                System.out.println("ufvPromedio:" + ufvPromedio);
                                System.out.println("montoActualizado:" + montoActualizado);
                                System.out.println("saldoAnterior  " + saldoAnterior);
                                System.out.println("costoUnitario  " + costoUnitario);

                                KardexItemMovimiento kardexItemMovimiento1 = new KardexItemMovimiento();
                                kardexItemMovimiento1.setSaldo(saldoAnterior);
                                kardexItemMovimiento1.setSaldoDinero(montoActualizado);
                                kardexItemMovimiento1.setValorUfv(kardexItemMovimiento.getValorUfv());
                                kardexItemMovimiento1.setCostoUnitario(costoUnitario);
                                kardexItemMovimiento1.setFecha(kardexItemMovimiento.getFecha());
                                kardexItemMovimiento1.setTipo("Act.");
                                kardexItemMovimiento1.setAlmacenes(kardexItemMovimiento.getAlmacenes());
                                kardexItemMovimiento1.setMateriales(kardexItemMovimiento.getMateriales());
                                kardexItemMovimiento1.setAreasEmpresa(kardexItemMovimiento.getAreasEmpresa());
                                kardexItemMovimiento1.setCostoPromedio(kardexItemMovimiento.getCostoUnitario());
                                kardexItemMovimiento1.setDiferenciaActualizado(montoAnterior*(ufvPromedio-1));

                                //proceso de actualizacion de costos
                                consulta = " update salidas_almacen_detalle_ingreso " +
                                        " set fecha_actualizacion = '"+sdf.format(kardexItemMovimiento1.getFecha())+" 00:00:00'," +
                                        "  costo_salida_actualizado = '"+kardexItemMovimiento1.getCostoUnitario()+"' " +
                                        " where cod_material = '"+materiales.getCodMaterial()+"' and cod_salida_almacen in (select cod_salida_almacen " +
                                        " from salidas_almacen " +
                                        " where fecha_salida_almacen >= '"+sdf.format(kardexItemMovimiento1.getFecha())+" 00:00:00' " +
                                        " and cod_almacen = '"+kardexItemMovimiento1.getAlmacenes().getCodAlmacen()+"' and fecha_salida_almacen <= '"+sdf.format(kardexItemMovimiento1.getFecha())+" 23:59:59'  ) ";
                                System.out.println("consulta " + consulta );
                                st1.executeUpdate(consulta);


                                consulta = " update ingresos_almacen_detalle set fecha_actualizacion='"+sdf.format(kardexItemMovimiento1.getFecha())+" 00:00:00'," +
                                           " costo_unitario_actualizado= '"+kardexItemMovimiento1.getCostoUnitario()+"'  " +
                                           " where cod_material= '"+materiales.getCodMaterial()+"' and cod_ingreso_almacen in (select cod_ingreso_almacen " +
                                           " from ingresos_almacen where fecha_ingreso_almacen>= '"+sdf.format(kardexItemMovimiento1.getFecha())+" 00:00:00' and cod_almacen='"+kardexItemMovimiento1.getAlmacenes().getCodAlmacen()+"' and fecha_ingreso_almacen<='"+sdf.format(kardexItemMovimiento1.getFecha())+" 23:59:59') ";
                                System.out.println("consulta " + consulta );
                                st1.executeUpdate(consulta);


                                kardexItemMovimientoList1.add(kardexItemMovimiento1);

                                fechaAnterior1 = kardexItemMovimiento.getFecha();

                                montoAnterior = redondear(montoActualizado);

                                //ufvAnterior = kardexItemMovimiento.getValorUfv();
                                //montoAnterior = montoActualizado;


                        }

                        /////--------------

                                ufvAnterior = kardexItemMovimiento.getValorUfv();
                                saldoAnterior = saldoAnterior + kardexItemMovimiento.getCantidadIngreso() - kardexItemMovimiento.getCantidadSalida();
                                // 8 ajuste

                                //if(kardexItemMovimiento.getCodTipoIngresoSalida()!=8){
                                    if(kardexItemMovimiento.getTipo().equals("Salida")){
                                        montoAnterior = redondear(montoAnterior + (kardexItemMovimiento.getCantidadIngreso() * costoUnitario) - (kardexItemMovimiento.getCantidadSalida()*costoUnitario));
                                        kardexItemMovimiento.setDebe(kardexItemMovimiento.getCantidadIngreso() * costoUnitario);
                                        kardexItemMovimiento.setHaber(kardexItemMovimiento.getCantidadSalida()*costoUnitario);
                                    }else{
                                        //el almacen no debe ser frv y debe ser devolucion
                                        //6 devolucion
                                        //por revisar
                                        System.out.println("tipo ingreso " +kardexItemMovimiento.getCodTipoIngresoSalida() +"  " + kardexItemMovimiento.getTipoIngresoSalida());
                                        if(this.esAlmacenFrv()==false && kardexItemMovimiento.getCodTipoIngresoSalida()==6){
                                            //devolucion
                                            //System.out.println("entro a la devolucion ");
                                            montoAnterior = redondear(montoAnterior + (kardexItemMovimiento.getCantidadIngreso() * costoUnitario) - (kardexItemMovimiento.getCantidadSalida()*costoUnitario));
                                            kardexItemMovimiento.setDebe(kardexItemMovimiento.getCantidadIngreso()*costoUnitario); // kardexItemMovimiento.getCostoUnitario()  costoUnitario getCantidadIngreso()  kardexItemMovimiento.getCostoUnitario()
                                            kardexItemMovimiento.setHaber(kardexItemMovimiento.getCantidadSalida()*costoUnitario); // kardexItemMovimiento.getCostoUnitario()  costoUnitario getCantidadIngreso() kardexItemMovimiento.getCostoUnitario()
                                        }else{
                                            // no es devolucion
                                            montoAnterior = redondear(montoAnterior + (kardexItemMovimiento.getCantidadIngreso() * kardexItemMovimiento.getCostoUnitario()) - (kardexItemMovimiento.getCantidadSalida()*kardexItemMovimiento.getCostoUnitario()));
                                            costoUnitario = saldoAnterior>0.0001?(montoAnterior / saldoAnterior):costoUnitario;
                                            //montoAnterior = redondear(montoAnterior + (kardexItemMovimiento.getCantidadIngreso() * costoUnitario) - (kardexItemMovimiento.getCantidadSalida()*costoUnitario));
                                            kardexItemMovimiento.setDebe(kardexItemMovimiento.getCantidadIngreso()*kardexItemMovimiento.getCostoUnitario()); // kardexItemMovimiento.getCostoUnitario()  costoUnitario getCantidadIngreso()
                                            kardexItemMovimiento.setHaber(kardexItemMovimiento.getCantidadSalida()*kardexItemMovimiento.getCostoUnitario()); // kardexItemMovimiento.getCostoUnitario()  costoUnitario getCantidadIngreso()
                                        }
                                        }
                                //}
                                System.out.println( "para el monto: montoAnterior " + costoUnitario+"  " + montoAnterior + "  " + (kardexItemMovimiento.getCantidadIngreso() * costoUnitario) + "  "  + (kardexItemMovimiento.getCantidadSalida()*costoUnitario));


                                kardexItemMovimiento.setCostoUnitario(costoUnitario);
                                kardexItemMovimiento.setSaldoDinero(montoAnterior);
                                kardexItemMovimiento.setSaldo(saldoAnterior);

                        //----------------
                        
                        //System.out.println("datos para saldo  ardexItemMovimiento.getCantidadIngreso() " + kardexItemMovimiento.getCantidadIngreso() + "  kardexItemMovimiento.getCantidadSalida() " + kardexItemMovimiento.getCantidadSalida());
                    }
                    // se debe ordenar la lista por orden de fecha
                    
                    

//                    SimpleDateFormat f=new SimpleDateFormat("dd/MM/yyyy");
//                   for(KardexItemMovimiento k:kardexItemMovimientoList1){
//                       System.out.println("K:"+f.format(k.getFecha()));
//
//                   }

                    //actualizacion ultima

                    //comparacion de fechas
                    //obtencion de datos de la ultima fecha para la actualizacion

                    if(fechaFinal.compareTo(kardexItemMovimiento.getFecha())!=0){
                        consulta = " select t.CAMBIO, t.FECHA,t.COD_MONEDA from TIPOCAMBIOS_MONEDA t  " +
                            " where t.FECHA = (select max(t1.FECHA) from TIPOCAMBIOS_MONEDA t1  " +
                            " where t1.FECHA between '"+sdf.format(fechaInicio)+"' and '"+sdf.format(fechaFinal)+"') and t.COD_MONEDA = 4 ";
                        System.out.println("consulta " + consulta);

                        Statement st8 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                        ResultSet rs8 = st8.executeQuery(consulta);
                        TipoCambiosMoneda tipoCambiosMoneda = new TipoCambiosMoneda();
                        if(rs8.next()){
                            tipoCambiosMoneda.setCambio(rs8.getDouble("CAMBIO"));
                            tipoCambiosMoneda.setFecha(rs8.getDate("FECHA"));
                            tipoCambiosMoneda.getMonedas().setCodMoneda(rs8.getString("COD_MONEDA"));
                        }
                                KardexItemMovimiento kardexItemMovimiento1 = new KardexItemMovimiento();
                                kardexItemMovimiento1.setCodReporte(codReporte);
                                kardexItemMovimiento1.setSaldo(kardexItemMovimiento.getSaldo());
                                kardexItemMovimiento1.setSaldoDinero(kardexItemMovimiento.getSaldoDinero()* tipoCambiosMoneda.getCambio()/kardexItemMovimiento.getValorUfv());
                                kardexItemMovimiento1.setSaldoDinero(Double.isNaN(kardexItemMovimiento1.getSaldoDinero())?0:kardexItemMovimiento1.getSaldoDinero());
                                kardexItemMovimiento1.setValorUfv(tipoCambiosMoneda.getCambio());
                                kardexItemMovimiento1.setCostoUnitario((kardexItemMovimiento.getSaldo()>0.0001?(kardexItemMovimiento.getSaldoDinero()/kardexItemMovimiento.getSaldo()):0)*(tipoCambiosMoneda.getCambio()/kardexItemMovimiento.getValorUfv()));
                                kardexItemMovimiento1.setCostoUnitario(Double.isNaN(kardexItemMovimiento1.getCostoUnitario())?0:kardexItemMovimiento1.getCostoUnitario());
                                kardexItemMovimiento1.setFecha(tipoCambiosMoneda.getFecha());
                                kardexItemMovimiento1.setDiferenciaActualizado(kardexItemMovimiento.getSaldoDinero()*((tipoCambiosMoneda.getCambio()/kardexItemMovimiento.getValorUfv())-1));
                                kardexItemMovimiento1.setDiferenciaActualizado(Double.isNaN(kardexItemMovimiento1.getDiferenciaActualizado())?0:kardexItemMovimiento1.getDiferenciaActualizado());
                                kardexItemMovimiento1.setMateriales(kardexItemMovimiento.getMateriales());
                                kardexItemMovimiento1.setAlmacenes(kardexItemMovimiento.getAlmacenes());
                                kardexItemMovimientoList.add(kardexItemMovimiento1);
                    }


                    i1 = kardexItemMovimientoList1.iterator();
                    while(i1.hasNext()){
                        KardexItemMovimiento itemMovimiento = (KardexItemMovimiento)i1.next();
                        itemMovimiento.setCodReporte(codReporte);
                        kardexItemMovimientoList.add(itemMovimiento);
                        codReporte++;
                    }
                    Collections.sort(kardexItemMovimientoList);
                    this.guardarCostoMaterialPorMesDetalle(kardexItemMovimientoList);
                  //break;
                }
            }


            mensaje = " Actualizacion Satisfactoria ";
            

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static final Comparator<KardexItemMovimiento> kardexItemMovimientoOrden =
                                        new Comparator<KardexItemMovimiento>() {
            public int compare(KardexItemMovimiento k1, KardexItemMovimiento k2) {
                System.out.println("el valor " + k1.getFecha().compareTo(k2.getFecha()));
                return k1.getFecha().compareTo(k2.getFecha());
            }
    };
    public void guardarCostoMaterialPorMesDetalle(List<KardexItemMovimiento> kardexItemMovimientoList){
        try {
            Connection con = null;
            con = Util.openConnection(con);
            KardexItemMovimiento itemMovimiento = new KardexItemMovimiento();
            //KardexItemMovimiento itemMovimiento = new KardexItemMovimiento();
            //DateTime dateTime = new DateTime();
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy");
            SimpleDateFormat sdf2 = new SimpleDateFormat("MM");
            SimpleDateFormat sdf3 = new SimpleDateFormat("dd");
            DateTime dt = new DateTime();
            
            // se obtiene la primera fecha de la lista ordenada
            for(KardexItemMovimiento itemMovimiento1:kardexItemMovimientoList){
                itemMovimiento=itemMovimiento1;
                break;
            }

            for(KardexItemMovimiento itemMovimiento1:kardexItemMovimientoList){
                if(itemMovimiento1.getFecha().compareTo(itemMovimiento.getFecha())!=0){
                    String consulta = " DELETE FROM COSTOS_MATERIAL_POR_MES_DETALLE WHERE COD_GESTION = (select gest.COD_GESTION from GESTIONES gest where '"+sdf.format(itemMovimiento.getFecha())+"' between gest.FECHA_INI and gest.FECHA_FIN)" +
                            " AND COD_MES=month('"+sdf.format(itemMovimiento.getFecha())+"') AND COD_MATERIAL = '"+itemMovimiento.getMateriales().getCodMaterial()+"'" +
                            " AND COD_ALMACEN = '"+itemMovimiento.getAlmacenes().getCodAlmacen()+"' AND FECHA = '"+sdf.format(itemMovimiento.getFecha())+"' ";
                System.out.println("consulta " + consulta);
                Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                st.executeUpdate(consulta);
                    consulta = " INSERT INTO COSTOS_MATERIAL_POR_MES_DETALLE " +
                    " (COD_GESTION, COD_MES,COD_MATERIAL,COD_ALMACEN,COSTO_MATERIAL, FECHA,AJUSTE) " +
                    " VALUES ( (select gest.COD_GESTION from GESTIONES gest where '"+sdf.format(itemMovimiento.getFecha())+"' between gest.FECHA_INI and gest.FECHA_FIN),month('"+sdf.format(itemMovimiento.getFecha())+"')," +
                    "'"+itemMovimiento.getMateriales().getCodMaterial()+"','"+itemMovimiento.getAlmacenes().getCodAlmacen()+"','"+itemMovimiento.getCostoUnitario()+"'," +
                    "'"+sdf.format(itemMovimiento.getFecha())+"',0); ";
                System.out.println("consulta " + consulta);                
                st.executeUpdate(consulta);
                itemMovimiento=itemMovimiento1;
                }
            }
            KardexItemMovimiento itemMovimiento1 = (KardexItemMovimiento)kardexItemMovimientoList.get(kardexItemMovimientoList.size()-1);
            String consulta = " DELETE FROM COSTOS_MATERIAL_POR_MES_DETALLE WHERE COD_GESTION = (select gest.COD_GESTION from GESTIONES gest where '"+sdf.format(itemMovimiento.getFecha())+"' between gest.FECHA_INI and gest.FECHA_FIN)" +
                            " AND COD_MES=month('"+sdf.format(itemMovimiento.getFecha())+"') AND COD_MATERIAL = '"+itemMovimiento.getMateriales().getCodMaterial()+"'" +
                            " AND COD_ALMACEN = '"+itemMovimiento.getAlmacenes().getCodAlmacen()+"' AND FECHA = '"+sdf.format(itemMovimiento.getFecha())+"' ";
                System.out.println("consulta " + consulta);
                Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                st.executeUpdate(consulta);
                consulta = " INSERT INTO COSTOS_MATERIAL_POR_MES_DETALLE " +
                    " (COD_GESTION, COD_MES,COD_MATERIAL,COD_ALMACEN,COSTO_MATERIAL, FECHA,AJUSTE) " +
                    " VALUES ( (select gest.COD_GESTION from GESTIONES gest where '"+sdf.format(itemMovimiento1.getFecha())+"' between gest.FECHA_INI and gest.FECHA_FIN),month('"+sdf.format(itemMovimiento1.getFecha())+"')," +
                    "'"+itemMovimiento1.getMateriales().getCodMaterial()+"','"+itemMovimiento1.getAlmacenes().getCodAlmacen()+"','"+itemMovimiento1.getCostoUnitario()+"'," +
                    "'"+sdf.format(itemMovimiento1.getFecha())+"',0); ";
                System.out.println("consulta " + consulta);
                //Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                st.executeUpdate(consulta);


            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean esAlmacenFrv(){
        boolean esAlmacenFrv = false;
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select * from ALMACENES_FRV a where a.COD_ALMACEN_FRV = '"+usuario.getAlmacenesGlobal().getCodAlmacen()+"' ";
            System.out.println("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            if(rs.next()){
                esAlmacenFrv = true;
            }
            st.close();
            rs.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return esAlmacenFrv;
    }


    public String generarActualizacion_action(){
        try {
            //break;
            Iterator i = materialesList.iterator();
            Materiales materiales = new Materiales();
            kardexMovimientoList.clear();
            while(i.hasNext()){
                Materiales materialesItem = (Materiales)i.next();
                System.out.println("iteracion --------------" + materialesItem.getChecked().booleanValue() );
                if(materialesItem.getChecked().booleanValue()==true){
                    materiales = materialesItem;
                    KardexMovimiento kardexMovimiento = new KardexMovimiento();
                    

                    Connection con = null;
                    con = Util.openConnection(con);
                    String consulta = " delete from kardex_item_movimiento where cod_persona= '"+usuario.getUsuarioModuloBean().getCodUsuarioGlobal()+"' ";
                    System.out.println("consulta " + consulta);
                    Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    st.executeUpdate(consulta);


                    Date fechaAnterior = new Date();
                    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy");
                    SimpleDateFormat sdf2 = new SimpleDateFormat("MM");
                    SimpleDateFormat sdf3 = new SimpleDateFormat("dd");



                    // seleccion del tipo de cambio del mes pasado o dia anterior
                    DateTime dt = new DateTime();
                    dt = dt.withYear(Integer.valueOf(sdf1.format(fechaInicio)));
                    dt = dt.withMonthOfYear(Integer.valueOf(sdf2.format(fechaInicio)));
                    dt = dt.withDayOfMonth(Integer.valueOf(sdf3.format(fechaInicio)));
                    DateTime dt1 = dt.minusDays(1);
                    fechaAnterior = dt1.toDate();
                    System.out.println("la fecha Anterior "  + fechaAnterior);
                    double ufvUltimoFechaAnterior = this.tipoCambio(fechaAnterior);

                    // se halla la el ultimo costo unitario segun la fecha de actualizacion para el item

                    double costoUnitarioActualizadoAnterior = 0;
                    Date fechaActualizadoAnterior = new Date();
                    double ufvActualizacionAnterior = 0;

                    consulta = " SELECT top 1 sadi.fecha_actualizacion,  sadi.costo_salida_actualizado_final,(SELECT top 1 cambio FROM tipocambios_moneda " +
                            " where cod_moneda=4 and fecha<=sadi.FECHA_ACTUALIZACION order by fecha desc) ufv " +
                            " from SALIDAS_ALMACEN_DETALLE_INGRESO sadi, salidas_almacen sa " +
                            " where sadi.cod_material = '"+materiales.getCodMaterial()+"' and sadi.fecha_actualizacion <= '"+sdf.format(fechaAnterior)+" 23:59:59' " +
                            " and sa.cod_almacen = '"+almacenes.getCodAlmacen()+"' AND sadi.costo_salida_actualizado_final > 0 " +
                            " and sadi.cod_salida_almacen = sa.cod_salida_almacen and sa.cod_estado_salida_almacen = 1 " +
                            " order by sadi.fecha_actualizacion desc ";
                    System.out.println("consulta " + consulta);
                    Statement st1 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    ResultSet rs1 = st1.executeQuery(consulta);
                    Date fechaActualizacionAnterior1 = new Date();
                    double costoActualizacionAnterior1 = 0;
                    double ufvAnterior1 = 0;
                    if(rs1.next()){
                        fechaActualizacionAnterior1 = rs1.getDate("fecha_actualizacion");
                        costoActualizacionAnterior1 = rs1.getDouble("costo_salida_actualizado_final");
                        ufvAnterior1 = rs1.getDouble("ufv");
                    }

                    consulta = " SELECT top 1 iade.fecha_actualizacion,  iade.costo_unitario_actualizado_final,(SELECT top 1 cambio FROM tipocambios_moneda " +
                            " where cod_moneda=4 and fecha<=iade.FECHA_ACTUALIZACION order by fecha desc) ufv  " +
                            " from INGRESOS_ALMACEN_DETALLE iade, ingresos_almacen ia " +
                            " where iade.cod_material = '"+materiales.getCodMaterial()+"' and iade.fecha_actualizacion <= '"+sdf.format(fechaAnterior)+" 23:59:59' " +
                            " and  ia.cod_almacen ='"+almacenes.getCodAlmacen()+"' AND iade.costo_unitario_actualizado_final > 0 " +
                            " and iade.cod_ingreso_almacen = ia.cod_ingreso_almacen " +
                            " and ia.cod_estado_ingreso_almacen = 1 order by iade.fecha_actualizacion desc ";
                    System.out.println("consulta " + consulta);

                    Date fechaActualizacionAnterior2 = new Date();
                    double costoActualizacionAnterior2 = 0;
                    double ufvAnterior2 = 0;
                    Statement st2 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    ResultSet rs2 = st2.executeQuery(consulta);
                    if(rs1.next()){
                        fechaActualizacionAnterior2 = rs2.getDate("fecha_actualizacion");
                        costoActualizacionAnterior2 = rs2.getDouble("costo_unitario_actualizado_final");
                        ufvAnterior2 = rs2.getDouble("ufv");
                    }
                    if(fechaActualizacionAnterior1.compareTo(fechaActualizacionAnterior2)>0){
                        fechaActualizadoAnterior = fechaActualizacionAnterior1;
                        costoUnitarioActualizadoAnterior = costoActualizacionAnterior1 ;
                        ufvActualizacionAnterior = ufvAnterior1;
                    }else{
                        fechaActualizadoAnterior = fechaActualizacionAnterior2;
                        costoUnitarioActualizadoAnterior = costoActualizacionAnterior2 ;
                        ufvActualizacionAnterior = ufvAnterior2;
                    }
                    //hallar el costos unitario de la tabla de costos detalle
                    consulta = " select top 1 c.COD_GESTION,  c.COD_MES,  c.COD_MATERIAL,  c.COD_ALMACEN,  c.COSTO_MATERIAL,  c.FECHA,  c.AJUSTE,(SELECT top 1 t.cambio FROM tipocambios_moneda t " +
                            " where t.cod_moneda=4 and t.fecha<=c.fecha order by t.fecha desc) ufv " +
                            " from COSTOS_MATERIAL_POR_MES_DETALLE c where c.cod_mes= MONTH('"+sdf.format(fechaAnterior)+"') " +
                            " and c.fecha<='"+sdf.format(fechaAnterior)+"' and c.cod_material='"+materiales.getCodMaterial()+"' " +
                            " and c.cod_almacen= '"+almacenes.getCodAlmacen()+"' order by c.fecha desc ";
                    System.out.println("consulta " + consulta);
                    Statement st10 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    ResultSet rs10 = st10.executeQuery(consulta);
                    if(rs10.next()){
                        fechaActualizadoAnterior = rs10.getDate("FECHA");
                        costoUnitarioActualizadoAnterior = rs10.getDouble("COSTO_MATERIAL");
                        ufvActualizacionAnterior = rs10.getDouble("ufv");
                    }
                    int filas = 0;
                    rs10.last();
                    filas = rs10.getRow();
                    rs10.first();
                    if(filas==0){
                    // hallar el costo unitario de la tabla de costos
                    consulta = " select top 1 c.COD_GESTION,  c.COD_MES,  c.COD_MATERIAL,  c.COD_ALMACEN,  c.COSTO_MATERIAL,  c.FECHA,  c.AJUSTE,(SELECT top 1 t.cambio FROM tipocambios_moneda t " +
                            " where t.cod_moneda=4 and t.fecha<=c.fecha order by t.fecha desc) ufv " +
                            " from COSTOS_MATERIAL_POR_MES c where c.cod_mes= MONTH('"+sdf.format(fechaAnterior)+"') " +
                            " and c.fecha<='"+sdf.format(fechaAnterior)+"' and c.cod_material='"+materiales.getCodMaterial()+"' " +
                            " and c.cod_almacen= '"+almacenes.getCodAlmacen()+"' order by c.fecha desc ";
                    System.out.println("consulta " + consulta);
                    Statement st3 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    ResultSet rs3 = st3.executeQuery(consulta);
                    if(rs3.next()){
                        fechaActualizadoAnterior = rs3.getDate("FECHA") ;
                        costoUnitarioActualizadoAnterior = rs3.getDouble("COSTO_MATERIAL");
                        ufvActualizacionAnterior = rs3.getDouble("ufv");
                    }
                    }

                    //hallar el saldo anterior
                    double ingresoTotalAnterior = 0;
                    double salidaTotalAnterior = 0;
                    double saldoTotalAnterior = 0;


                    consulta = " SELECT sum(iad.cant_total_ingreso_fisico)as ingresos_total_anterior " +
                            " FROM ingresos_almacen_detalle iad,ingresos_almacen ia " +
                            " where iad.cod_ingreso_almacen=ia.cod_ingreso_almacen " +
                            " and ia.cod_estado_ingreso_almacen=1 " +
                            " and iad.cod_material='"+materiales.getCodMaterial() +"' " +
                            " and ia.cod_almacen='"+almacenes.getCodAlmacen() +"' " +
                            " and ia.fecha_ingreso_almacen<='"+sdf.format(fechaAnterior)+" 23:59:59' " ;
                    System.out.println("consulta " + consulta);
                    Statement st4 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    ResultSet rs4 = st4.executeQuery(consulta);
                    if(rs4.next()){
                        ingresoTotalAnterior = rs4.getDouble("ingresos_total_anterior");
                    }


                    consulta = " select sum(sad.cantidad_salida_almacen)as salidas_total_anterior " +
                            " from salidas_almacen_detalle sad,salidas_almacen sa " +
                            "where sad.cod_salida_almacen=sa.cod_salida_almacen  " +
                            "and sa.cod_estado_salida_almacen=1 " +
                            "and sad.cod_material='"+materiales.getCodMaterial()+"'  " +
                            "and sa.cod_almacen='"+almacenes.getCodAlmacen()+"'  " +
                            " and sa.fecha_salida_almacen<='"+sdf.format(fechaAnterior)+" 23:59:59'  ";
                    System.out.println("consulta " + consulta);
                    Statement st5 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    ResultSet rs5 = st5.executeQuery(consulta);
                    if(rs5.next()){
                        salidaTotalAnterior = rs5.getDouble("salidas_total_anterior");
                    }
                    saldoTotalAnterior = ingresoTotalAnterior - salidaTotalAnterior;

                    //float ufvPromedio = ufvActualizacionAnterior>0?ufvUltimoFechaAnterior/ufvActualizacionAnterior:0;
                    double montoAnteriorActualizado = saldoTotalAnterior*costoUnitarioActualizadoAnterior;

                    //se iteran los ingresos u se guardan en kardex de movimiento

                    consulta = " select * from (SELECT  ia.cod_almacen,iad.cod_ingreso_almacen,iad.costo_unitario," +
                            " iad.costo_promedio,ia.cod_tipo_ingreso_almacen,iad.cant_total_ingreso_fisico,0 cantidad_salida_almacen, " +
                            " iad.cod_material,um.abreviatura,(select sa.cod_lote_produccion from salidas_almacen sa, devoluciones d where d.cod_salida_almacen=sa.cod_salida_almacen and d.cod_devolucion=ia.cod_devolucion)as cod_lote_produccion, " +
                            " ia.nro_ingreso_almacen,tia.nombre_tipo_ingreso_almacen, " +
                            " ia.obs_ingreso_almacen,ia.fecha_ingreso_almacen,0 cod_area_empresa,0 cantidad_valorada,'Ingreso' tipo,costo_unitario*cant_total_ingreso_fisico debe," +
                            " (SELECT top 1 cambio FROM tipocambios_moneda where cod_moneda=4 and fecha<= ia.fecha_ingreso_almacen order by fecha desc) ufv " +
                            " FROM ingresos_almacen_detalle iad, ingresos_almacen ia,tipos_ingreso_almacen tia, unidades_medida um " +
                            " where iad.cod_ingreso_almacen=ia.cod_ingreso_almacen " +
                            " and ia.cod_estado_ingreso_almacen=1 and iad.cod_unidad_medida=um.cod_unidad_medida and ia.cod_tipo_ingreso_almacen=tia.cod_tipo_ingreso_almacen " +
                            " and iad.cod_material="+materiales.getCodMaterial()+" and ia.cod_almacen='"+almacenes.getCodAlmacen()+"' " +
                            " and ia.fecha_ingreso_almacen>='"+sdf.format(fechaInicio)+" 00:00:00'  " +
                            " and ia.fecha_ingreso_almacen<='"+sdf.format(fechaFinal)+" 23:59:59' " +
                            " union all " +
                            " SELECT  sa.cod_almacen,sad.cod_salida_almacen,(select top 1 sadi.costo_salida from salidas_almacen_detalle_ingreso sadi where sadi.cod_salida_almacen=sad.cod_salida_almacen and sadi.cod_material=sad.cod_material)as costo_unitario,0 costo_promedio" +
                            " ,sa.cod_tipo_salida_almacen,0 cant_total_ingreso_fisico,sad.cantidad_salida_almacen " +
                            " ,sad.cod_material,um.abreviatura,sa.cod_lote_produccion " +
                            " ,sa.nro_salida_almacen,tsa.nombre_tipo_salida_almacen,sa.obs_salida_almacen " +
                            " ,sa.fecha_salida_almacen,sa.cod_area_empresa, " +
                            " (select sum(sadi.cantidad*sadi.costo_salida) from salidas_almacen_detalle_ingreso sadi " +
                            " where sadi.cod_salida_almacen=sad.cod_salida_almacen and sadi.cod_material=sad.cod_material)as cantidad_valorada,'Salida' tipo, 0 debe," +
                            "(SELECT top 1 cambio FROM tipocambios_moneda where cod_moneda=4 and fecha<= sa.fecha_salida_almacen order by fecha desc) ufv " +
                            " FROM salidas_almacen_detalle sad,salidas_almacen sa,tipos_salidas_almacen tsa,unidades_medida um " +
                            " where sad.cod_salida_almacen=sa.cod_salida_almacen " +
                            " and sa.cod_estado_salida_almacen=1 " +
                            " and sad.cod_unidad_medida=um.cod_unidad_medida " +
                            " and sa.cod_tipo_salida_almacen=tsa.cod_tipo_salida_almacen " +
                            " and sad.cod_material= '"+materiales.getCodMaterial()+"' and sa.cod_almacen='"+almacenes.getCodAlmacen()+"' " +
                            " and sa.fecha_salida_almacen>='"+sdf.format(fechaInicio)+" 00:00:00' " +
                            " and sa.fecha_salida_almacen<='"+sdf.format(fechaFinal)+" 23:59:59' ) as tabla order by fecha_ingreso_almacen ";

                    System.out.println("consulta " + consulta);
                    Statement st6 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    ResultSet rs6 = st6.executeQuery(consulta);
                    int codReporte = 1;
                    kardexItemMovimientoList.clear();
                    while(rs6.next()){
                        KardexItemMovimiento kardexItemMovimiento = new KardexItemMovimiento();
                        kardexItemMovimiento.setCodReporte(codReporte);
                        kardexItemMovimiento.getPersonal().setCodPersonal(usuario.getUsuarioModuloBean().getCodUsuarioGlobal());
                        kardexItemMovimiento.getAlmacenes().setCodAlmacen(rs6.getInt("cod_almacen"));
                        kardexItemMovimiento.setTipo(rs6.getString("tipo"));
                        kardexItemMovimiento.setCodigo(rs6.getInt("cod_ingreso_almacen"));
                        kardexItemMovimiento.setNumero(rs6.getInt("nro_ingreso_almacen"));
                        kardexItemMovimiento.setFecha(rs6.getDate("fecha_ingreso_almacen"));                        
                        kardexItemMovimiento.getFecha().setTime(rs6.getTimestamp("fecha_ingreso_almacen").getTime()); //codReporte consulta
                        kardexItemMovimiento.getMateriales().setCodMaterial(rs6.getString("cod_material"));
                        kardexItemMovimiento.getUnidadesMedida().setAbreviatura(rs6.getString("abreviatura"));
                        kardexItemMovimiento.setCantidadIngreso(rs6.getDouble("cant_total_ingreso_fisico"));
                        kardexItemMovimiento.setCantidadSalida(rs6.getDouble("cantidad_salida_almacen"));
                        kardexItemMovimiento.setTipoIngresoSalida(rs6.getString("nombre_tipo_ingreso_almacen") );
                        kardexItemMovimiento.setObsIngresoSalida(rs6.getString("obs_ingreso_almacen"));
                        kardexItemMovimiento.setSaldo(0);
                        kardexItemMovimiento.setCostoUnitario(rs6.getDouble("costo_unitario"));
                        kardexItemMovimiento.setCostoPromedio(rs6.getDouble("costo_unitario"));
                        kardexItemMovimiento.setDebe(rs6.getDouble("debe"));
                        kardexItemMovimiento.setHaber(rs6.getDouble("cantidad_valorada"));
                        kardexItemMovimiento.setSaldoDinero(0);
                        kardexItemMovimiento.setTipoCambio(0);
                        //kardexItemMovimiento.setCostoPromedio(rs6.getDouble("costo_promedio"));
                        kardexItemMovimiento.setValorUfv(rs6.getDouble("ufv"));
                        kardexItemMovimiento.setCodTipoIngresoSalida(rs6.getInt("cod_tipo_ingreso_almacen"));
                        kardexItemMovimiento.setCodLoteProduccion(rs6.getString("cod_lote_produccion"));
                        kardexItemMovimiento.getAreasEmpresa().setCodAreaEmpresa(rs6.getString("cod_area_empresa"));
                        codReporte++;
                        kardexItemMovimientoList.add(kardexItemMovimiento);
                    }
                    //iteracion del detalle de kardex item movimiento
                    //float ufvFechaInicio = this.tipoCambio(fechaInicio);
                    Iterator i1 = kardexItemMovimientoList.iterator();


                    //primera actualizacion respecto a la anterior cantidad
                    //sacamos la primera fila del reporte
                    //System.out.println(" montoAnterior " +montoAnterior);
                    //System.out.println(" costoUnitarioActualizadoAnterior" + costoUnitarioActualizadoAnterior);
                    //System.out.println(" ufvActualizacionAnterior " + ufvActualizacionAnterior );
                    //System.out.println(" ufvUltimoFechaAnterior " + ufvUltimoFechaAnterior);

                    double saldoAnterior = 0;

                    saldoAnterior = saldoTotalAnterior;
                    //System.out.println("saldo:" + saldo);


                    double montoAnterior = saldoTotalAnterior*costoUnitarioActualizadoAnterior;
                    double ufvAnterior = ufvActualizacionAnterior;
                    double montoActualizado = 0;
                    double costoUnitario = costoUnitarioActualizadoAnterior;

                    System.out.println(" datos iniciales "  + " montoAnterior " + montoAnterior + " ufvAnterior " + ufvAnterior + " saldoAnterior " + saldoAnterior + "  costoUnitario " + costoUnitario);
                   

                    kardexMovimiento.setMonto(montoAnterior);
                    kardexMovimiento.setUfv(ufvAnterior);
                    kardexMovimiento.setSaldo(saldoAnterior);
                    kardexMovimiento.setCostoUnitario(costoUnitario);
                    kardexMovimiento.setMateriales(materiales);
                    kardexMovimiento.setFechaInicio(fechaInicio);
                    kardexMovimiento.setFechaFinal(fechaFinal);
                    kardexMovimiento.setAlmacenes(almacenes);




                    
                    Date fechaAnterior1 =  fechaAnterior; //fechaInicio;//

                    List kardexItemMovimientoList1 = new ArrayList();
                    KardexItemMovimiento kardexItemMovimiento = new KardexItemMovimiento();
                    while(i1.hasNext()){
                        kardexItemMovimiento = (KardexItemMovimiento)i1.next();


                        if(!sdf.format(kardexItemMovimiento.getFecha()).equals(sdf.format(fechaAnterior1))){ //kardexItemMovimiento.getFecha().compareTo(fechaAnterior1)!=0
                                System.out.println("fecha comparadas " + kardexItemMovimiento.getFecha() + " " + fechaAnterior1);

                                double ufvPromedio =ufvAnterior>0?kardexItemMovimiento.getValorUfv()/ufvAnterior:0;
                                montoActualizado = montoAnterior*ufvPromedio;


                                // calculo del costo unitario
                                if(saldoAnterior>0.0001){
                                    costoUnitario = (montoAnterior/ saldoAnterior)*(ufvPromedio); //montoAnterior
                                }


                                System.out.println("ufv1:" + kardexItemMovimiento.getValorUfv());
                                System.out.println("ufv2:" + ufvAnterior);
                                System.out.println("ufvPromedio:" + ufvPromedio);
                                System.out.println("montoActualizado:" + montoActualizado);
                                System.out.println("saldoAnterior  " + saldoAnterior);
                                System.out.println("costoUnitario  " + costoUnitario);

                                KardexItemMovimiento kardexItemMovimiento1 = new KardexItemMovimiento();
                                kardexItemMovimiento1.setSaldo(saldoAnterior);
                                kardexItemMovimiento1.setSaldoDinero(montoActualizado);
                                kardexItemMovimiento1.setValorUfv(kardexItemMovimiento.getValorUfv());
                                kardexItemMovimiento1.setCostoUnitario(costoUnitario);
                                kardexItemMovimiento1.setFecha(kardexItemMovimiento.getFecha());
                                kardexItemMovimiento1.setTipo("Act.");
                                kardexItemMovimiento1.setAlmacenes(kardexItemMovimiento.getAlmacenes());
                                kardexItemMovimiento1.setMateriales(kardexItemMovimiento.getMateriales());
                                kardexItemMovimiento1.setAreasEmpresa(kardexItemMovimiento.getAreasEmpresa());
                                kardexItemMovimiento1.setCostoPromedio(kardexItemMovimiento.getCostoUnitario());
                                kardexItemMovimiento1.setDiferenciaActualizado(montoAnterior*(ufvPromedio-1));


                                kardexItemMovimientoList1.add(kardexItemMovimiento1);

                                fechaAnterior1 = kardexItemMovimiento.getFecha();

                                montoAnterior = redondear(montoActualizado);

                                //ufvAnterior = kardexItemMovimiento.getValorUfv();
                                //montoAnterior = montoActualizado;


                        }

                                ufvAnterior = kardexItemMovimiento.getValorUfv();
                                saldoAnterior = saldoAnterior + kardexItemMovimiento.getCantidadIngreso() - kardexItemMovimiento.getCantidadSalida();
                                // 8 ajuste

                                //if(kardexItemMovimiento.getCodTipoIngresoSalida()!=8){
                                    if(kardexItemMovimiento.getTipo().equals("Salida")){
                                        montoAnterior = redondear(montoAnterior + (kardexItemMovimiento.getCantidadIngreso() * costoUnitario) - (kardexItemMovimiento.getCantidadSalida()*costoUnitario));
                                        kardexItemMovimiento.setDebe(kardexItemMovimiento.getCantidadIngreso() * costoUnitario);
                                        kardexItemMovimiento.setHaber(kardexItemMovimiento.getCantidadSalida()*costoUnitario);
                                    }else{
                                        //el almacen no debe ser frv y debe ser devolucion
                                        //6 devolucion
                                        //por revisar
                                        System.out.println("tipo ingreso " +kardexItemMovimiento.getCodTipoIngresoSalida() +"  " + kardexItemMovimiento.getTipoIngresoSalida());
                                        if(this.esAlmacenFrv()==false && kardexItemMovimiento.getCodTipoIngresoSalida()==6){
                                            //devolucion
                                            //System.out.println("entro a la devolucion ");
                                            montoAnterior = redondear(montoAnterior + (kardexItemMovimiento.getCantidadIngreso() * costoUnitario) - (kardexItemMovimiento.getCantidadSalida()*costoUnitario));                                            
                                            kardexItemMovimiento.setDebe(kardexItemMovimiento.getCantidadIngreso()*costoUnitario); // kardexItemMovimiento.getCostoUnitario()  costoUnitario getCantidadIngreso()  kardexItemMovimiento.getCostoUnitario()
                                            kardexItemMovimiento.setHaber(kardexItemMovimiento.getCantidadSalida()*costoUnitario); // kardexItemMovimiento.getCostoUnitario()  costoUnitario getCantidadIngreso() kardexItemMovimiento.getCostoUnitario()
                                        }else{
                                            // no es devolucion
                                            montoAnterior = redondear(montoAnterior + (kardexItemMovimiento.getCantidadIngreso() * kardexItemMovimiento.getCostoUnitario()) - (kardexItemMovimiento.getCantidadSalida()*kardexItemMovimiento.getCostoUnitario()));
                                            costoUnitario = saldoAnterior>0.0001?(montoAnterior / saldoAnterior):costoUnitario;
                                            //montoAnterior = redondear(montoAnterior + (kardexItemMovimiento.getCantidadIngreso() * costoUnitario) - (kardexItemMovimiento.getCantidadSalida()*costoUnitario));
                                            kardexItemMovimiento.setDebe(kardexItemMovimiento.getCantidadIngreso()*kardexItemMovimiento.getCostoUnitario()); // kardexItemMovimiento.getCostoUnitario()  costoUnitario getCantidadIngreso()
                                            kardexItemMovimiento.setHaber(kardexItemMovimiento.getCantidadSalida()*kardexItemMovimiento.getCostoUnitario()); // kardexItemMovimiento.getCostoUnitario()  costoUnitario getCantidadIngreso()
                                        }                                        
                                        }
                                //}
                                System.out.println( "para el monto: montoAnterior " + costoUnitario+"  " + montoAnterior + "  " + (kardexItemMovimiento.getCantidadIngreso() * costoUnitario) + "  "  + (kardexItemMovimiento.getCantidadSalida()*costoUnitario));


                                kardexItemMovimiento.setCostoUnitario(costoUnitario);
                                kardexItemMovimiento.setSaldoDinero(montoAnterior);
                                kardexItemMovimiento.setSaldo(saldoAnterior);


                        //System.out.println("datos para saldo  ardexItemMovimiento.getCantidadIngreso() " + kardexItemMovimiento.getCantidadIngreso() + "  kardexItemMovimiento.getCantidadSalida() " + kardexItemMovimiento.getCantidadSalida());
                    }

                    i1 = kardexItemMovimientoList1.iterator();
                    while(i1.hasNext()){
                        KardexItemMovimiento itemMovimiento = (KardexItemMovimiento)i1.next();
                        itemMovimiento.setCodReporte(codReporte);
                        kardexItemMovimientoList.add(itemMovimiento);
                        codReporte++;
                    }
                    //actualizacion ultima

                    //comparacion de fechas
                    //obtencion de datos de la ultima fecha para la actualizacion

                    if(!sdf.format(fechaFinal).equals(sdf.format(kardexItemMovimiento.getFecha()))){
                        consulta = " select t.CAMBIO, t.FECHA,t.COD_MONEDA from TIPOCAMBIOS_MONEDA t  " +
                            " where t.FECHA = (select max(t1.FECHA) from TIPOCAMBIOS_MONEDA t1  " +
                            " where t1.FECHA between '"+sdf.format(fechaInicio)+"' and '"+sdf.format(fechaFinal)+"') and t.COD_MONEDA = 4 ";
                        System.out.println("consulta " + consulta);

                        Statement st8 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                        ResultSet rs8 = st8.executeQuery(consulta);
                        TipoCambiosMoneda tipoCambiosMoneda = new TipoCambiosMoneda();
                        if(rs8.next()){
                            tipoCambiosMoneda.setCambio(rs8.getDouble("CAMBIO"));
                            tipoCambiosMoneda.setFecha(rs8.getDate("FECHA"));
                            tipoCambiosMoneda.getMonedas().setCodMoneda(rs8.getString("COD_MONEDA"));
                        }
                                KardexItemMovimiento kardexItemMovimiento1 = new KardexItemMovimiento();
                                kardexItemMovimiento1.setCodReporte(codReporte);
                                kardexItemMovimiento1.setSaldo(kardexItemMovimiento.getSaldo());
                                kardexItemMovimiento1.setSaldoDinero(kardexItemMovimiento.getSaldoDinero()* tipoCambiosMoneda.getCambio()/kardexItemMovimiento.getValorUfv());
                                kardexItemMovimiento1.setSaldoDinero(Double.isNaN(kardexItemMovimiento1.getSaldoDinero())?0:kardexItemMovimiento1.getSaldoDinero());
                                kardexItemMovimiento1.setValorUfv(tipoCambiosMoneda.getCambio());
                                kardexItemMovimiento1.setCostoUnitario((kardexItemMovimiento.getSaldo()>0.0001?(kardexItemMovimiento.getSaldoDinero()/kardexItemMovimiento.getSaldo()):0)*(tipoCambiosMoneda.getCambio()/kardexItemMovimiento.getValorUfv()));
                                kardexItemMovimiento1.setCostoUnitario(Double.isNaN(kardexItemMovimiento1.getCostoUnitario())?0:kardexItemMovimiento1.getCostoUnitario());
                                kardexItemMovimiento1.setFecha(tipoCambiosMoneda.getFecha());
                                kardexItemMovimiento1.setDiferenciaActualizado(kardexItemMovimiento.getSaldoDinero()*((tipoCambiosMoneda.getCambio()/kardexItemMovimiento.getValorUfv())-1));
                                kardexItemMovimiento1.setDiferenciaActualizado(Double.isNaN(kardexItemMovimiento1.getDiferenciaActualizado())?0:kardexItemMovimiento1.getDiferenciaActualizado());
                                kardexItemMovimientoList.add(kardexItemMovimiento1);
                    }


                    consulta = " delete from kardex_item_movimiento  where cod_persona = '2' ";
                    st1.executeUpdate(consulta);
                      //iteramos los datos de kardex de movimiento y lo guardamos en la tabla kardex de movimiento
                   Iterator i2 = kardexItemMovimientoList.iterator();
                    while(i2.hasNext()){
                        KardexItemMovimiento kardexItemMovimiento1 = (KardexItemMovimiento)i2.next();
                        consulta = " INSERT INTO KARDEX_ITEM_MOVIMIENTO ( COD_REPORTE, COD_PERSONA, COD_ALMACEN,  TIPO,  CODIGO,  NUMERO,   FECHA,   COD_MATERIAL, " +
                                "  UNIDAD_MEDIDA,   CANTIDAD_INGRESO,  CANTIDAD_SALIDA,  TIPO_INGRESO_SALIDA,  OBS_INGRESO_SALIDA,  DESTINO_INGRESO_SALIDA,  SALDO,  CANTIDAD_RESTANTE_ANTERIOR," +
                                "  FECHA_INICIO_REPORTE,  FECHA_FINAL_REPORTE,  COD_LOTE_PRODUCCION,  COD_AREA_EMPRESA,   COSTO_UNITARIO,   DEBE,   HABER,  SALDO_DINERO,  TIPO_CAMBIO," +
                                "  COSTO_PROMEDIO,  VALOR_UFV,  DIFERENCIA_ACTUALIZADO,  COSTO_INGRESO,  COD_TIPO_INGRESO_SALIDA,  PRODUCTO,  cantidad_ingreso_cajas," +
                                "  cantidad_salida_cajas,  saldo_cajas,  CODIGO_STR) " +
                                "  VALUES ( '"+kardexItemMovimiento1.getCodReporte()+"','"+usuario.getUsuarioModuloBean().getCodUsuarioGlobal()+"', '"+kardexItemMovimiento1.getAlmacenes().getCodAlmacen()+"', " +
                                "  '"+ kardexItemMovimiento1.getTipo() +"', '"+kardexItemMovimiento1.getCodigo()+"', '"+kardexItemMovimiento1.getNumero()+"',  '"+sdf.format( kardexItemMovimiento1.getFecha())+" "+fh.format(kardexItemMovimiento1.getFecha())+"'," +
                                "  '"+kardexItemMovimiento1.getMateriales().getCodMaterial()+"', '"+kardexItemMovimiento1.getUnidadesMedida().getCodUnidadMedida()+"'  , " +
                                "  '"+kardexItemMovimiento1.getCantidadIngreso()+"' , '"+kardexItemMovimiento1.getCantidadSalida()+"'  , " +
                                "  '"+kardexItemMovimiento1.getTipoIngresoSalida()+"' , '"+kardexItemMovimiento1.getObsIngresoSalida() +"' , '"+kardexItemMovimiento1.getDestinoIngresoSalida() +"' ," +
                                "   '"+kardexItemMovimiento1.getSaldo()+"' , '"+kardexItemMovimiento1.getCantidadRestanteAnterior()+"'  , '"+ sdf.format(kardexItemMovimiento1.getFechaInicioReporte())+"'  , '"+ sdf.format( kardexItemMovimiento1.getFechaFinalReporte())+"',  " +
                                "  '"+kardexItemMovimiento1.getCodLoteProduccion()+"' , '"+kardexItemMovimiento1.getAreasEmpresa().getCodAreaEmpresa()+"' , '"+kardexItemMovimiento1.getCostoUnitario()+"' ," +
                                "  '"+kardexItemMovimiento1.getDebe()+"' , '"+kardexItemMovimiento1.getHaber()+"' , '"+kardexItemMovimiento1.getSaldoDinero()+"' , '"+kardexItemMovimiento1.getTipoCambio()+"' , '"+kardexItemMovimiento1.getCostoPromedio()+"' , '"+kardexItemMovimiento1.getValorUfv()+"'  ,  " +
                                "  '"+kardexItemMovimiento1.getDiferenciaActualizado()+"' , '"+kardexItemMovimiento1.getCostoIngreso()+"' , '"+kardexItemMovimiento1.getCodTipoIngresoSalida() +"' ," +
                                "  '"+kardexItemMovimiento1.getProducto()  +"' , '"+kardexItemMovimiento1.getCantidadIngresoCajas()+"' , '"+kardexItemMovimiento1.getCantidadSalidaCajas()+"', '0',  '0'); ";
                        System.out.println("consulta " + consulta);


                        st1.executeUpdate(consulta);
                    }
                    //se selecciona de la misma tabla de forma ordenada y se guarda en la lista general para mostrarlo en el reporte
                    consulta = " SELECT COD_REPORTE, COD_PERSONA, COD_ALMACEN, TIPO, CODIGO,  NUMERO,  FECHA,  COD_MATERIAL,  UNIDAD_MEDIDA,  CANTIDAD_INGRESO," +
                        "  CANTIDAD_SALIDA,  TIPO_INGRESO_SALIDA,  OBS_INGRESO_SALIDA,  DESTINO_INGRESO_SALIDA,  SALDO,  CANTIDAD_RESTANTE_ANTERIOR,  FECHA_INICIO_REPORTE," +
                        "  FECHA_FINAL_REPORTE,  COD_LOTE_PRODUCCION,  COD_AREA_EMPRESA,  COSTO_UNITARIO,  DEBE,  HABER,  SALDO_DINERO,  TIPO_CAMBIO,  COSTO_PROMEDIO," +
                        "  VALOR_UFV,  DIFERENCIA_ACTUALIZADO,  COSTO_INGRESO,  COD_TIPO_INGRESO_SALIDA,  PRODUCTO,  cantidad_ingreso_cajas," +
                        "  cantidad_salida_cajas,  saldo_cajas,  CODIGO_STR FROM  KARDEX_ITEM_MOVIMIENTO where cod_persona = '"+usuario.getUsuarioModuloBean().getCodUsuarioGlobal()+"' order by FECHA,CODIGO asc ; ";
                     System.out.println("consulta " + consulta);
                     Statement st9 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                     ResultSet rs9 = st9.executeQuery(consulta);
                     //se guarda el detalle para el reporte
                     kardexMovimiento.getKardexItemMovimientoList().clear();
                     while(rs9.next()){
                         KardexItemMovimiento kardexItemMovimiento2 = new KardexItemMovimiento();
                         kardexItemMovimiento2.setCodReporte(rs9.getInt("COD_REPORTE"));
                         kardexItemMovimiento2.getPersonal().setCodPersonal(rs9.getString("COD_PERSONA"));
                         kardexItemMovimiento2.getAlmacenes().setCodAlmacen(rs9.getInt("COD_ALMACEN"));
                         kardexItemMovimiento2.setTipo(rs9.getString("TIPO"));
                         kardexItemMovimiento2.setCodigo(rs9.getInt("CODIGO"));
                         kardexItemMovimiento2.setNumero(rs9.getInt("NUMERO"));
                         kardexItemMovimiento2.setFecha(rs9.getDate("FECHA"));
                         kardexItemMovimiento2.getMateriales().setCodMaterial(rs9.getString("COD_MATERIAL"));
                         kardexItemMovimiento2.getMateriales().getUnidadesMedida().setNombreUnidadMedida(rs9.getString("UNIDAD_MEDIDA"));
                         kardexItemMovimiento2.setCantidadIngreso(rs9.getDouble("CANTIDAD_INGRESO"));
                         kardexItemMovimiento2.setCantidadSalida(rs9.getDouble("CANTIDAD_SALIDA"));
                         kardexItemMovimiento2.setTipoIngresoSalida(rs9.getString("TIPO_INGRESO_SALIDA"));
                         kardexItemMovimiento2.setObsIngresoSalida(rs9.getString("OBS_INGRESO_SALIDA"));
                         kardexItemMovimiento2.setDestinoIngresoSalida(rs9.getString("DESTINO_INGRESO_SALIDA"));
                         kardexItemMovimiento2.setSaldo(rs9.getDouble("SALDO"));
                         kardexItemMovimiento2.setCantidadRestanteAnterior(rs9.getDouble("CANTIDAD_RESTANTE_ANTERIOR"));
                         kardexItemMovimiento2.setFechaInicioReporte(rs9.getDate("FECHA_INICIO_REPORTE"));
                         kardexItemMovimiento2.setFechaFinalReporte(rs9.getDate("FECHA_FINAL_REPORTE"));
                         kardexItemMovimiento2.setCodLoteProduccion(rs9.getString("COD_LOTE_PRODUCCION"));
                         kardexItemMovimiento2.getAreasEmpresa().setCodAreaEmpresa(rs9.getString("COD_AREA_EMPRESA"));
                         kardexItemMovimiento2.setCostoUnitario(rs9.getDouble("COSTO_UNITARIO"));
                         kardexItemMovimiento2.setDebe(rs9.getDouble("DEBE"));
                         kardexItemMovimiento2.setHaber(rs9.getDouble("HABER"));
                         kardexItemMovimiento2.setSaldoDinero(rs9.getDouble("SALDO_DINERO"));
                         kardexItemMovimiento2.setTipoCambio(rs9.getDouble("TIPO_CAMBIO"));
                         kardexItemMovimiento2.setCostoPromedio(rs9.getDouble("COSTO_PROMEDIO"));
                         kardexItemMovimiento2.setValorUfv(rs9.getDouble("VALOR_UFV"));
                         kardexItemMovimiento2.setDiferenciaActualizado(rs9.getDouble("DIFERENCIA_ACTUALIZADO"));
                         kardexItemMovimiento2.setCostoIngreso(rs9.getDouble("COSTO_INGRESO"));
                         kardexItemMovimiento2.setCodTipoIngresoSalida(rs9.getInt("COD_TIPO_INGRESO_SALIDA"));
                         kardexMovimiento.getKardexItemMovimientoList().add(kardexItemMovimiento2);
                     }
                     kardexMovimientoList.add(kardexMovimiento);
                        
             }
                
            }
            

             // guardamos los valores en el array de session
                    //FormulaMaestraDetalleFP formulaMaestraDetalleFPItem = (FormulaMaestraDetalleFP)formulaMaestrasDetalleFPDataTable.getRowData();
                    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                    Map<String, Object> sessionMap = externalContext.getSessionMap();
                    sessionMap.put("kardexMovimientoList",kardexMovimientoList);
                    
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
    public double redondear(double d) {
        DecimalFormat twoDForm = new DecimalFormat("#.####");
        return Double.valueOf(twoDForm.format(d).replace(",", "."));
    }
    public double tipoCambio(Date fecha){
        double tipoCambioAnterior = 0;
        try {            
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " SELECT top 1 cambio,fecha FROM tipocambios_moneda " +
                    " where cod_moneda=4 and fecha<='"+sdf.format(fecha)+"' order by fecha desc ";
            System.out.println("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            if(rs.next()){
                tipoCambioAnterior = rs.getDouble("cambio");
            }
            st.close();
            rs.close();
            con.close();



        } catch (Exception e) {
            e.printStackTrace();
        }
        return tipoCambioAnterior;
     
    }

}
