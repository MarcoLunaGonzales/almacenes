/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.web;

import com.cofar.bean.AmbienteAlmacen;
import com.cofar.util.Util;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.richfaces.component.html.HtmlDataTable;

/**
 *
 * @author USER
 */
public class ManagedAmbientesAlmacen {
    List<AmbienteAlmacen> ambienteAlmacenList = new ArrayList<AmbienteAlmacen>();
    AmbienteAlmacen ambienteAlmacen = new AmbienteAlmacen();
    ManagedAccesoSistema usuario = null;
    HtmlDataTable ambienteAlmacenDataTable = new HtmlDataTable();


    public AmbienteAlmacen getAmbienteAlmacen() {
        return ambienteAlmacen;
    }

    public void setAmbienteAlmacen(AmbienteAlmacen ambienteAlmacen) {
        this.ambienteAlmacen = ambienteAlmacen;
    }

    public List<AmbienteAlmacen> getAmbienteAlmacenList() {
        return ambienteAlmacenList;
    }

    public void setAmbienteAlmacenList(List<AmbienteAlmacen> ambienteAlmacenList) {
        this.ambienteAlmacenList = ambienteAlmacenList;
    }

    public HtmlDataTable getAmbienteAlmacenDataTable() {
        return ambienteAlmacenDataTable;
    }

    public void setAmbienteAlmacenDataTable(HtmlDataTable ambienteAlmacenDataTable) {
        this.ambienteAlmacenDataTable = ambienteAlmacenDataTable;
    }

   

    


    

    /** Creates a new instance of ManagedAmbientesAlmacen */
    public ManagedAmbientesAlmacen() {
    }
    public String getCargarAmbienteAlmacen(){
        usuario = (ManagedAccesoSistema)Util.getSessionBean("ManagedAccesoSistema");
        this.listadoAmbientesAlmacen();
        return null;
    }
    public void listadoAmbientesAlmacen() {
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " SELECT A.COD_AMBIENTE,A.NOMBRE_AMBIENTE,A.OBSERVACION FROM AMBIENTE_ALMACEN A WHERE A.COD_ALMACEN = '"+usuario.getAlmacenesGlobal().getCodAlmacen()+"' ";
            System.out.println("consulta " + consulta);
            
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            ambienteAlmacenList.clear();
            while (rs.next()) {
                AmbienteAlmacen a = new AmbienteAlmacen();
                a.setCodAmbiente(rs.getInt("cod_ambiente"));
                a.setNombreAmbiente(rs.getString("nombre_ambiente"));
                a.setObservacion(rs.getString("observacion"));
                ambienteAlmacenList.add(a);
            }
            rs.close();
            st.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String agregarAmbiente_action(){
        try {
            ambienteAlmacen = new AmbienteAlmacen();
            

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    

    public String guardarAgregarAmbiente_action(){
        try {

            //grupos.getEstadoReferencial().setCodEstadoRegistro("1");
            //grupos.setGrupoAlmacen(1);


            Connection con = null;
            con = Util.openConnection(con);

           String consulta = " INSERT INTO AMBIENTE_ALMACEN(  COD_ALMACEN,  COD_AMBIENTE,  NOMBRE_AMBIENTE,  OBSERVACION) " +
                    " VALUES (  '"+usuario.getAlmacenesGlobal().getCodAlmacen()+"',  (select isnull(max(cod_ambiente),0)+1 from ambiente_almacen),  '"+ambienteAlmacen.getNombreAmbiente()+"',  '"+ambienteAlmacen.getObservacion()+"');  ";
            System.out.println("consulta" + consulta);

            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            st.executeUpdate(consulta);
            

            this.listadoAmbientesAlmacen();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String eliminarAmbiente_action(){
        try {
            
            
            AmbienteAlmacen a1 = new AmbienteAlmacen();
            for(AmbienteAlmacen a:ambienteAlmacenList){
                if(a.getChecked()){
                    a1 = a;
                    break;
                }


            }

            Connection con = null;
            con = Util.openConnection(con);

            String consulta = " delete from ambiente_almacen where cod_ambiente = '"+a1.getCodAmbiente()+"' ";
            System.out.println("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            st.executeUpdate(consulta);

            con.close();
            st.close();
            this.listadoAmbientesAlmacen();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String editarAmbiente_action(){
        try {
            for(AmbienteAlmacen a:ambienteAlmacenList){
                if(a.getChecked()){
                    ambienteAlmacen = a;
                    break;
                }


            }



        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String guardarEditarAmbiente(){
        try {
            
            Connection con = null;
            con = Util.openConnection(con);

            String consulta = "UPDATE AMBIENTE_ALMACEN  SET   NOMBRE_AMBIENTE = '"+ambienteAlmacen.getNombreAmbiente()+"'," +
                    "  OBSERVACION = '"+ambienteAlmacen.getObservacion()+"' WHERE COD_AMBIENTE = '"+ambienteAlmacen.getCodAmbiente()+"'  ";
            System.out.println("consulta" + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            st.executeUpdate(consulta);

            st.close();
            con.close();
            
            this.listadoAmbientesAlmacen();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public String verEstantesAmbiente_action(){
           AmbienteAlmacen ambienteAlmacen = (AmbienteAlmacen)ambienteAlmacenDataTable.getRowData();
           FacesContext facesContext = FacesContext.getCurrentInstance();
           ExternalContext ec = facesContext.getExternalContext();
           Map map = ec.getSessionMap();
           map.put("ambienteAlmacen", ambienteAlmacen);
           return null;
    }
}
