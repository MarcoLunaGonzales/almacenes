/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.web;

import com.cofar.bean.DistribucionMaterialPersonal;
import com.cofar.bean.DistribucionMateriales;
import com.cofar.bean.Materiales;
import com.cofar.bean.SalidasAlmacen;
import com.cofar.util.Util;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.model.SelectItem;
import org.richfaces.component.html.HtmlDataTable;

/**
 *
 * @author hvaldivia
 */

public class ManagedDistribucionMaterial {
    private List salidasAlmacenList = new ArrayList();
    private HtmlDataTable salidasAlmacenDataTable = new HtmlDataTable();
    private SalidasAlmacen salidaAlmacenSeleccionado = new SalidasAlmacen();
    private List distribucionMaterialesList = new ArrayList();
    private List personalList = new ArrayList();
    private List distribucionList = new ArrayList();
    private DistribucionMateriales distribucionMaterialesSeleccionado = new DistribucionMateriales();
    private HtmlDataTable distribucionMaterialesDataTable = new HtmlDataTable();
    private List personalEntregaList = new ArrayList();
    /** Creates a new instance of ManagedDistribucionMaterial */
    public ManagedDistribucionMaterial() {
    }

    public List getSalidasAlmacenList() {
        return salidasAlmacenList;
    }

    public void setSalidasAlmacenList(List salidasAlmacenList) {
        this.salidasAlmacenList = salidasAlmacenList;
    }

    public List getDistribucionMaterialesList() {
        return distribucionMaterialesList;
    }

    public void setDistribucionMaterialesList(List distribucionMaterialesList) {
        this.distribucionMaterialesList = distribucionMaterialesList;
    }

    public SalidasAlmacen getSalidaAlmacenSeleccionado() {
        return salidaAlmacenSeleccionado;
    }

    public void setSalidaAlmacenSeleccionado(SalidasAlmacen salidaAlmacenSeleccionado) {
        this.salidaAlmacenSeleccionado = salidaAlmacenSeleccionado;
    }

    public HtmlDataTable getSalidasAlmacenDataTable() {
        return salidasAlmacenDataTable;
    }

    public void setSalidasAlmacenDataTable(HtmlDataTable salidasAlmacenDataTable) {
        this.salidasAlmacenDataTable = salidasAlmacenDataTable;
    }

    public List getPersonalList() {
        return personalList;
    }

    public void setPersonalList(List personalList) {
        this.personalList = personalList;
    }

    public List getDistribucionList() {
        return distribucionList;
    }

    public void setDistribucionList(List distribucionList) {
        this.distribucionList = distribucionList;
    }

    public HtmlDataTable getDistribucionMaterialesDataTable() {
        return distribucionMaterialesDataTable;
    }

    public void setDistribucionMaterialesDataTable(HtmlDataTable distribucionMaterialesDataTable) {
        this.distribucionMaterialesDataTable = distribucionMaterialesDataTable;
    }

    public DistribucionMateriales getDistribucionMaterialesSeleccionado() {
        return distribucionMaterialesSeleccionado;
    }

    public void setDistribucionMaterialesSeleccionado(DistribucionMateriales distribucionMaterialesSeleccionado) {
        this.distribucionMaterialesSeleccionado = distribucionMaterialesSeleccionado;
    }

    public List getPersonalEntregaList() {
        return personalEntregaList;
    }

    public void setPersonalEntregaList(List personalEntregaList) {
        this.personalEntregaList = personalEntregaList;
    }
    


    
    public String getCargarSalidasAlmacen(){
        try {
            String consulta = " select s.NRO_SALIDA_ALMACEN,  s.COD_PERSONAL, s.COD_SALIDA_ALMACEN, s.COD_LOTE_PRODUCCION, g.NOMBRE_GESTION,s.COD_ESTADO_SALIDA_ALMACEN," +
                    " e.NOMBRE_ESTADO_SALIDA_ALMACEN,    s.orden_trabajo,    (       select p.ap_paterno_personal + ' ' + p.ap_materno_personal + " +
                    " ' ' + p.nombres_personal  from personal p where s.cod_personal = p.cod_personal   ) as nombre_personal, s.COD_TIPO_SALIDA_ALMACEN," +
                    " tsa.NOMBRE_TIPO_SALIDA_ALMACEN,     s.COD_AREA_EMPRESA,   a.NOMBRE_AREA_EMPRESA,  s.COD_PROD, c.nombre_prod_semiterminado,  s.COD_PRESENTACION," +
                    " prp.NOMBRE_PRODUCTO_PRESENTACION,    s.FECHA_SALIDA_ALMACEN,  s.OBS_SALIDA_ALMACEN,    s.COD_ALMACEN,(select count(*) from distribucion_material_personal d where d.cod_salida_almacen = s.cod_salida_almacen ) distribucion from SALIDAS_ALMACEN s inner join gestiones g on g.COD_GESTION = s.COD_GESTION" +
                    " inner join TIPOS_SALIDAS_ALMACEN tsa on tsa.COD_TIPO_SALIDA_ALMACEN = s.COD_TIPO_SALIDA_ALMACEN" +
                    " inner join AREAS_EMPRESA a on a.COD_AREA_EMPRESA = s.COD_AREA_EMPRESA" +
                    " inner join ESTADOS_SALIDAS_ALMACEN e on e.COD_ESTADO_SALIDA_ALMACEN = s.COD_ESTADO_SALIDA_ALMACEN" +
                    " left outer join COMPONENTES_PROD c on c.COD_COMPPROD = s.COD_PROD" +
                    " left outer join PRESENTACIONES_PRODUCTO prp on prp.cod_presentacion = s.COD_PRESENTACION" +
                    " where s.COD_SALIDA_ALMACEN in (select sd.COD_SALIDA_ALMACEN from SALIDAS_ALMACEN_DETALLE sd " +
                    " inner join materiales m on m.cod_material = sd.cod_material where m.cod_grupo = 36) and s.COD_GESTION = 9 ";
            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement();
            ResultSet rs  = st.executeQuery(consulta);
            salidasAlmacenList.clear();
            while(rs.next()){
                SalidasAlmacen salidasAlmacen = new SalidasAlmacen();
                salidasAlmacen.setNroSalidaAlmacen(rs.getInt("NRO_SALIDA_ALMACEN"));
                salidasAlmacen.getPersonal().setCodPersonal(rs.getString("COD_PERSONAL"));
                salidasAlmacen.setCodSalidaAlmacen(rs.getInt("COD_SALIDA_ALMACEN"));
                salidasAlmacen.setCodLoteProduccion(rs.getString("COD_LOTE_PRODUCCION"));
                salidasAlmacen.getGestiones().setNombreGestion(rs.getString("NOMBRE_GESTION"));
                salidasAlmacen.getEstadosSalidasAlmacen().setCodEstadoSalidaAlmacen(rs.getInt("COD_ESTADO_SALIDA_ALMACEN"));
                salidasAlmacen.getEstadosSalidasAlmacen().setNombreEstadoSalidaAlmacen(rs.getString("NOMBRE_ESTADO_SALIDA_ALMACEN"));
                salidasAlmacen.setOrdenTrabajo(rs.getString("orden_trabajo"));
                salidasAlmacen.getPersonal().setNombrePersonal(rs.getString("nombre_personal"));
                salidasAlmacen.getTiposSalidasAlmacen().setCodTipoSalidaAlmacen(rs.getInt("COD_TIPO_SALIDA_ALMACEN"));
                salidasAlmacen.getTiposSalidasAlmacen().setNombreTipoSalidaAlmacen(rs.getString("NOMBRE_TIPO_SALIDA_ALMACEN"));
                salidasAlmacen.getEstadosSalidasAlmacen().setCodEstadoSalidaAlmacen(rs.getInt("COD_ESTADO_SALIDA_ALMACEN"));
                salidasAlmacen.getEstadosSalidasAlmacen().setNombreEstadoSalidaAlmacen(rs.getString("NOMBRE_ESTADO_SALIDA_ALMACEN"));
                salidasAlmacen.getAreasEmpresa().setCodAreaEmpresa(rs.getString("COD_AREA_EMPRESA"));
                salidasAlmacen.getAreasEmpresa().setNombreAreaEmpresa(rs.getString("NOMBRE_AREA_EMPRESA"));
                salidasAlmacen.getComponentesProd().setCodCompprod(rs.getString("COD_PROD"));
                salidasAlmacen.getComponentesProd().setNombreProdSemiterminado(rs.getString("nombre_prod_semiterminado"));
                salidasAlmacen.getPresentacionesProducto().setCodPresentacion(rs.getString("COD_PRESENTACION"));
                salidasAlmacen.getPresentacionesProducto().setNombreProductoPresentacion(rs.getString("NOMBRE_PRODUCTO_PRESENTACION"));
                salidasAlmacen.getAlmacenes().setCodAlmacen(rs.getInt("COD_ALMACEN"));
                salidasAlmacen.setObsSalidaAlmacen(rs.getString("OBS_SALIDA_ALMACEN"));
                salidasAlmacenList.add(salidasAlmacen);
            }
        } catch (Exception e)  {
            e.printStackTrace();
        }
        return null;
    }
    public List distribucionSalidaAlmacen(String codMaterial){
        List distribucionMaterialesList = new ArrayList();
        try {
            //salidaAlmacenSeleccionado = (SalidasAlmacen)salidasAlmacenDataTable.getRowData();
            
            String consulta = " select p1.NOMBRE_PILA +' '+ p1.AP_PATERNO_PERSONAL + ' ' + p1.AP_PATERNO_PERSONAL nombre_personal,p1.cod_personal," +
                    " p2.NOMBRE_PILA +' '+ p2.AP_PATERNO_PERSONAL + ' ' + p2.AP_PATERNO_PERSONAL nombre_personal_recibe,p2.cod_personal cod_personal_recibe ,d.FECHA_DISTRIBUCION,d.OBSERVACIONES,d.cantidad" +
                    " from DISTRIBUCION_MATERIAL_PERSONAL d inner join personal p1 on p1.COD_PERSONAL = d.COD_PERSONAL" +
                    " inner join personal p2 on p2.COD_PERSONAL = d.COD_PERSONAL_ENTREGA" +
                    " where d.COD_SALIDA_ALMACEN = '"+salidaAlmacenSeleccionado.getCodSalidaAlmacen()+"' and d.cod_material = '"+codMaterial+"'";
            System.out.println(" consulta " + consulta );
            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(consulta);
            distribucionMaterialesList.clear();
            while(rs.next()){
                DistribucionMaterialPersonal distribucionMaterialPersonal = new DistribucionMaterialPersonal();
                distribucionMaterialPersonal.getPersonal().setNombrePersonal(rs.getString("nombre_personal"));
                distribucionMaterialPersonal.setFechaDistribucion(rs.getDate("fecha_distribucion"));
                distribucionMaterialPersonal.getPersonalEntrega().setNombrePersonal(rs.getString("nombre_personal_recibe"));
                distribucionMaterialPersonal.setFechaDistribucion(rs.getDate("fecha_distribucion"));
                distribucionMaterialPersonal.setObservaciones(rs.getString("observaciones"));
                distribucionMaterialPersonal.getPersonal().setCodPersonal(rs.getString("cod_personal"));
                distribucionMaterialPersonal.getPersonalEntrega().setCodPersonal(rs.getString("cod_personal_recibe"));
                distribucionMaterialPersonal.setCantidadEntregada(rs.getInt("cantidad"));
                distribucionMaterialesList.add(distribucionMaterialPersonal);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return distribucionMaterialesList;
    }
    public String verDistribucion_action(){
        try {
            salidaAlmacenSeleccionado = (SalidasAlmacen)salidasAlmacenDataTable.getRowData();
            this.cargarDistribucion();
            personalList = this.cargarPersonal("");
            personalEntregaList = this.cargarPersonal("1560,1478");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public void cargarDistribucion(){
        try {
            String consulta = " select m.COD_MATERIAL,m.NOMBRE_MATERIAL,sd.CANTIDAD_SALIDA_ALMACEN,u.abreviatura from SALIDAS_ALMACEN_DETALLE sd inner join materiales m on m.COD_MATERIAL = sd.COD_MATERIAL inner join unidades_medida u on u.cod_unidad_medida = m.cod_unidad_medida where sd.COD_SALIDA_ALMACEN = '"+salidaAlmacenSeleccionado.getCodSalidaAlmacen()+"' and m.COD_GRUPO = 36 ";
            System.out.println(" consulta " + consulta );
            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(consulta);
            distribucionList.clear();
            while(rs.next()){
                DistribucionMateriales distribucionMateriales = new DistribucionMateriales();
                distribucionMateriales.getMateriales().setNombreMaterial(rs.getString("nombre_material"));
                distribucionMateriales.getMateriales().setCodMaterial(rs.getString("cod_material"));
                distribucionMateriales.setCantidad(rs.getInt("cantidad_salida_almacen"));
                distribucionMateriales.getMateriales().getUnidadesMedida().setAbreviatura(rs.getString("abreviatura"));
                distribucionMateriales.setDistribucionMaterialesPersonal(this.distribucionSalidaAlmacen(rs.getString("cod_material")));
                distribucionList.add(distribucionMateriales);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List cargarPersonal(String codPersonal){
        List personalList = new ArrayList();
        try {
                String consulta  = " select p.COD_PERSONAL,p.AP_PATERNO_PERSONAL+' '+p.AP_MATERNO_PERSONAL+' '+p.NOMBRE_PILA nombre_personal from personal p where p.cod_area_empresa = 86 and p.COD_ESTADO_PERSONA =1 "+(!codPersonal.equals("")?" and p.cod_personal in ("+codPersonal+")":"");
                System.out.println(" consulta " + consulta );
                Connection con = null;
                con = Util.openConnection(con);
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(consulta);
                personalList.clear();
                while(rs.next()){
                    personalList.add(new SelectItem(rs.getString("cod_personal"),rs.getString("nombre_personal")));
                }
        } catch (Exception e) {
        }
        return personalList;
    }
    public String adicionarPersonal_action(){
        try {


        DistribucionMaterialPersonal distribucionMaterialPersonal = new DistribucionMaterialPersonal();
        distribucionMaterialesList.add(distribucionMaterialPersonal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public String eliminarPersonal_action(){
        Iterator i = distribucionMaterialesList.iterator();
        List aux = new ArrayList();
        while(i.hasNext()){
            DistribucionMaterialPersonal distribucionMaterialPersonal = (DistribucionMaterialPersonal) i.next();
            if(distribucionMaterialPersonal.getChecked()==false){
                aux.add(distribucionMaterialPersonal);
            }
        }
        distribucionMaterialesList.clear();
        distribucionMaterialesList = aux;
        return null;
    }
    public String guardarDistribucion_action(){
        try {
                Connection con = null;
                con = Util.openConnection(con);
                Statement st = con.createStatement();
                

                String consulta = " delete from distribucion_material_personal where cod_salida_almacen = '"+salidaAlmacenSeleccionado.getCodSalidaAlmacen()+"' ";
                System.out.println("consulta " + consulta );
                st.executeUpdate(consulta);
                Iterator i = distribucionMaterialesList.iterator();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                while(i.hasNext()){
                    DistribucionMaterialPersonal distribucionMaterialPersonal = (DistribucionMaterialPersonal) i.next();
                    consulta = " INSERT INTO DISTRIBUCION_MATERIAL_PERSONAL(  COD_SALIDA_ALMACEN,  COD_PERSONAL," +
                        "  FECHA_DISTRIBUCION,  COD_PERSONAL_ENTREGA,  OBSERVACIONES,CANTIDAD,cod_material)" +
                        " VALUES (  '"+salidaAlmacenSeleccionado.getCodSalidaAlmacen()+"', '"+distribucionMaterialPersonal.getPersonal().getCodPersonal()+"','"+sdf.format(distribucionMaterialPersonal.getFechaDistribucion())+"', '"+distribucionMaterialPersonal.getPersonalEntrega().getCodPersonal()+"' , '"+distribucionMaterialPersonal.getObservaciones()+"','"+distribucionMaterialPersonal.getCantidadEntregada()+"','"+distribucionMaterialesSeleccionado.getMateriales().getCodMaterial()+"'); ";
                    System.out.println("consulta " + consulta );
                    st.executeUpdate(consulta);
                }
                this.cargarDistribucion();
                
        } catch (Exception e) {
        }
        return null;
        
    }
    public String verDistribucionMateriales_action(){
        distribucionMaterialesSeleccionado = (DistribucionMateriales)distribucionMaterialesDataTable.getRowData();
        distribucionMaterialesList = this.distribucionSalidaAlmacen(distribucionMaterialesSeleccionado.getMateriales().getCodMaterial());
        return null;
    }
}

