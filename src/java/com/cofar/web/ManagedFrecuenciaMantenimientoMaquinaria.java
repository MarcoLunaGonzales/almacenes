/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.web;

import com.cofar.bean.FrecuenciasMantenimientoMaquina;
import com.cofar.bean.Maquinaria;
import com.cofar.bean.TiposPeriodo;
import java.sql.Connection;
import com.cofar.util.Util;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;


/**
 *
 * @author sistemas1
 */

public class ManagedFrecuenciaMantenimientoMaquinaria {

    private Connection con;
    /** Creates a new instance of ManagedFrecuenciaMantenimientoMaquinaria */
    private Maquinaria maquinaria= new Maquinaria();
    private List frecuenciaMantenimientoMaquinaList = new ArrayList();
    private List tiposPeriodoList = new ArrayList();
    private TiposPeriodo tiposPeriodo = new TiposPeriodo();
    
    private FrecuenciasMantenimientoMaquina frecuenciasMantenimientoMaquina = new FrecuenciasMantenimientoMaquina();

    public List getFrecuenciaMantenimientoMaquinaList() {
        return frecuenciaMantenimientoMaquinaList;
    }

    public void setFrecuenciaMantenimientoMaquinaList(List frecuenciaMantenimientoMaquinaList) {
        this.frecuenciaMantenimientoMaquinaList = frecuenciaMantenimientoMaquinaList;
    }

    public FrecuenciasMantenimientoMaquina getFrecuenciasMantenimientoMaquina() {
        return frecuenciasMantenimientoMaquina;
    }

    public void setFrecuenciasMantenimientoMaquina(FrecuenciasMantenimientoMaquina frecuenciasMantenimientoMaquina) {
        this.frecuenciasMantenimientoMaquina = frecuenciasMantenimientoMaquina;
    }

    public TiposPeriodo getTiposPeriodo() {
        return tiposPeriodo;
    }

    public void setTiposPeriodo(TiposPeriodo tiposPeriodo) {
        this.tiposPeriodo = tiposPeriodo;
    }

    public List getTiposPeriodoList() {
        return tiposPeriodoList;
    }

    public void setTiposPeriodoList(List tiposPeriodoList) {
        this.tiposPeriodoList = tiposPeriodoList;
    }
    


    public ManagedFrecuenciaMantenimientoMaquinaria() {
    }
    public String getCargarFrecuenciaMantenimientoMaquinaria(){        
        maquinaria.setCodMaquina(Util.getParameter("cod_maquina")==null?"0":Util.getParameter("cod_maquina"));
        this.cargarFrecuenciasMantenimientoMaquinaria();
        return "";        
    }
    public void cargarFrecuenciasMantenimientoMaquinaria(){
        try {   
                con = Util.openConnection(con);
                String consulta = " SELECT F.COD_FRECUENCIA_MANTENIMIENTO_MAQUINA, F.COD_MAQUINA,  F.COD_TIPO_PERIODO,   F.HORAS_FRECUENCIA,TP.NOMBRE_TIPO_PERIODO " +
                        " FROM   FRECUENCIAS_MANTENIMIENTO_MAQUINA F INNER JOIN TIPOS_PERIODO TP " +
                        " ON TP.COD_TIPO_PERIODO = F.COD_TIPO_PERIODO  WHERE F.COD_MAQUINA = '"+maquinaria.getCodMaquina()+"';  ";
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(consulta);
                frecuenciaMantenimientoMaquinaList.clear();
                while(rs.next()){
                    FrecuenciasMantenimientoMaquina frecuenciasMantenimientoMaquinaItem = new FrecuenciasMantenimientoMaquina();
                    frecuenciasMantenimientoMaquinaItem.setCodFrecuencia(rs.getInt("COD_FRECUENCIA_MANTENIMIENTO_MAQUINA"));
                    frecuenciasMantenimientoMaquinaItem.getMaquinaria().setCodMaquina(rs.getString("COD_MAQUINA"));
                    frecuenciasMantenimientoMaquinaItem.getTiposPeriodo().setCodTipoPeriodo(rs.getInt("COD_TIPO_PERIODO"));
                    frecuenciasMantenimientoMaquinaItem.setHorasFrecuencia(rs.getFloat("HORAS_FRECUENCIA"));
                    frecuenciasMantenimientoMaquinaItem.getTiposPeriodo().setNombreTipoPeriodo(rs.getString("NOMBRE_TIPO_PERIODO"));
                    frecuenciaMantenimientoMaquinaList.add(frecuenciasMantenimientoMaquinaItem);
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String agregarFrecuencia_action(){
        try {
                frecuenciasMantenimientoMaquina = new FrecuenciasMantenimientoMaquina();
                this.cargarTiposPeriodo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String redireccionar(String direccion) {
        try {

            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext ext = facesContext.getExternalContext();
            ext.redirect(direccion);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public String registrarFrecuenciaMantenimientoMaquina_action(){
        try {
               con=Util.openConnection(con);
               String consulta = "INSERT INTO FRECUENCIAS_MANTENIMIENTO_MAQUINA (   COD_FRECUENCIA_MANTENIMIENTO_MAQUINA,   COD_MAQUINA,   COD_TIPO_PERIODO,   HORAS_FRECUENCIA )  " +
                       " VALUES ((select isnull(MAX(COD_FRECUENCIA_MANTENIMIENTO_MAQUINA),0) +1 from FRECUENCIAS_MANTENIMIENTO_MAQUINA),'"+maquinaria.getCodMaquina()+"'," +
                       " '"+frecuenciasMantenimientoMaquina.getTiposPeriodo().getCodTipoPeriodo()+"','"+frecuenciasMantenimientoMaquina.getHorasFrecuencia() +"' );";
               System.out.println("consulta" + consulta);

               Statement st = con.createStatement();
               st.executeUpdate(consulta);
               this.cargarFrecuenciasMantenimientoMaquinaria();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void cargarTiposPeriodo() {
        try {
            con = Util.openConnection(con);            
            String consulta = " SELECT  COD_TIPO_PERIODO, NOMBRE_TIPO_PERIODO, COD_ESTADO_REGISTRO FROM TIPOS_PERIODO  " +
                         " WHERE COD_ESTADO_REGISTRO = 1";            
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            tiposPeriodoList.clear();
            while(rs.next()){
                TiposPeriodo tiposPeriodoItem = new TiposPeriodo();
                tiposPeriodoItem.setCodTipoPeriodo(rs.getInt("COD_TIPO_PERIODO"));
                tiposPeriodoItem.setNombreTipoPeriodo(rs.getString("NOMBRE_TIPO_PERIODO"));
                tiposPeriodoList.add(new SelectItem(tiposPeriodoItem.getCodTipoPeriodo(), tiposPeriodoItem.getNombreTipoPeriodo()));
            }
            if (rs != null) {
                rs.close();
                st.close();
                rs = null;
                st = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String eliminarFrecuenciaMantenimientoMaquina_action(){
        try {
               con=Util.openConnection(con);
               String consulta = "";
               Iterator i = frecuenciaMantenimientoMaquinaList.iterator();
               while(i.hasNext()){
                   FrecuenciasMantenimientoMaquina frecuenciasMantenimientoMaquinaItem = (FrecuenciasMantenimientoMaquina)i.next();
                   if(frecuenciasMantenimientoMaquinaItem.getChecked().booleanValue()==true){
                       consulta = " DELETE FROM FRECUENCIAS_MANTENIMIENTO_MAQUINA  WHERE  COD_FRECUENCIA_MANTENIMIENTO_MAQUINA = '"+frecuenciasMantenimientoMaquinaItem.getCodFrecuencia()+"'; ";
                       break;
                   }
               }
               
               System.out.println("consulta" + consulta);
               Statement st = con.createStatement();
               st.executeUpdate(consulta);
               this.cargarFrecuenciasMantenimientoMaquinaria();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public String editarFrecuenciaMantenimientoMaquina_action(){
        try {
               con=Util.openConnection(con);               
               Iterator i = frecuenciaMantenimientoMaquinaList.iterator();
               while(i.hasNext()){
                   FrecuenciasMantenimientoMaquina frecuenciasMantenimientoMaquinaItem = (FrecuenciasMantenimientoMaquina)i.next();
                   if(frecuenciasMantenimientoMaquinaItem.getChecked().booleanValue()==true){
                       frecuenciasMantenimientoMaquina = frecuenciasMantenimientoMaquinaItem;
                       break;
                   }
               }
               this.cargarTiposPeriodo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public String guardarEdicionFrecuenciaMantenimientoMaquina_action(){
        try {
            con=Util.openConnection(con);
            String consulta = " UPDATE FRECUENCIAS_MANTENIMIENTO_MAQUINA  SET  " +
                    " COD_TIPO_PERIODO = '"+frecuenciasMantenimientoMaquina.getTiposPeriodo().getCodTipoPeriodo()+"', " +
                    " HORAS_FRECUENCIA = '"+frecuenciasMantenimientoMaquina.getHorasFrecuencia()+"' " +
                    " WHERE  COD_FRECUENCIA_MANTENIMIENTO_MAQUINA = '"+frecuenciasMantenimientoMaquina.getCodFrecuencia()+"' ";
             Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             st.executeUpdate(consulta);
             this.cargarFrecuenciasMantenimientoMaquinaria();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getCloseConnection() {
        try {
            if (con != null) {
            con.close();
        }
        return "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

