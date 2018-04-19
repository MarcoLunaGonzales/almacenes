/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.web;

import com.cofar.bean.AmbienteAlmacen;
import com.cofar.bean.EstanteAlmacen;
import com.cofar.util.Util;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author USER
 */

public class ManagedEstantesAlmacen {
    List<EstanteAlmacen> estanteAlmacenList = new ArrayList<EstanteAlmacen>();
    EstanteAlmacen estanteAlmacen = new EstanteAlmacen();
    //EstanteAlmacen EstanteAlmacen = new EstanteAlmacen();
    ManagedAccesoSistema usuario = null;
    AmbienteAlmacen ambienteAlmacen = new AmbienteAlmacen();


    public EstanteAlmacen getEstanteAlmacen() {
        return estanteAlmacen;
    }

    public void setEstanteAlmacen(EstanteAlmacen estanteAlmacen) {
        this.estanteAlmacen = estanteAlmacen;
    }

    public List<EstanteAlmacen> getestanteAlmacenList() {
        return estanteAlmacenList;
    }

    public void setestanteAlmacenList(List<EstanteAlmacen> estanteAlmacenList) {
        this.estanteAlmacenList = estanteAlmacenList;
    }

    /** Creates a new instance of ManagedEstantesAlmacen */
    public ManagedEstantesAlmacen() {
    }
    public String getCargarEstanteAlmacen(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext ec = facesContext.getExternalContext();
        Map map = ec.getSessionMap();
        ambienteAlmacen = (AmbienteAlmacen)map.get("ambienteAlmacen");
        
        usuario=(ManagedAccesoSistema)Util.getSessionBean("ManagedAccesoSistema");
        this. listadoEstantesAlmacen();
        return null;
    }
    public void listadoEstantesAlmacen() {
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " SELECT A.COD_Estante,A.NOMBRE_Estante,A.OBSERVACION FROM ESTANTE_AMBIENTE A WHERE A.COD_AMBIENTE = '"+ambienteAlmacen.getCodAmbiente()+"' ";
            System.out.println("consulta " + consulta);

            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            estanteAlmacenList.clear();
            while (rs.next()) {
                EstanteAlmacen a = new EstanteAlmacen();
                a.setCodEstante(rs.getInt("cod_Estante"));
                a.setNombreEstante(rs.getString("nombre_Estante"));
                a.setObservacion(rs.getString("observacion"));
                estanteAlmacenList.add(a);
            }
            rs.close();
            st.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String agregarEstante_action(){
        try {
            
            estanteAlmacen = new EstanteAlmacen();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }





    public String guardarAgregarEstante_action(){
        try {

            //grupos.getEstadoReferencial().setCodEstadoRegistro("1");
            //grupos.setGrupoAlmacen(1);


            Connection con = null;
            con = Util.openConnection(con);

            String consulta = " INSERT INTO ESTANTE_AMBIENTE( COD_AMBIENTE,  COD_Estante,  NOMBRE_Estante,  OBSERVACION) " +
                    " VALUES (  '"+ambienteAlmacen.getCodAmbiente()+"',  (select isnull(max(cod_Estante),0)+1 from ESTANTE_AMBIENTE),  '"+estanteAlmacen.getNombreEstante()+"',  '"+estanteAlmacen.getObservacion()+"');  ";
            System.out.println("consulta" + consulta);

            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            st.executeUpdate(consulta);


            this.listadoEstantesAlmacen();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String eliminarEstante_action(){
        try {


            EstanteAlmacen a1 = new EstanteAlmacen();
            for(EstanteAlmacen a:estanteAlmacenList){
                if(a.getChecked()){
                    a1 = a;
                    break;
                }


            }

            Connection con = null;
            con = Util.openConnection(con);

            String consulta = " delete from ESTANTE_AMBIENTE where cod_Estante = '"+a1.getCodEstante()+"' ";
            System.out.println("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            st.executeUpdate(consulta);

            con.close();
            st.close();
            this.listadoEstantesAlmacen();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String editarEstante_action(){
        try {
            for(EstanteAlmacen a:estanteAlmacenList){
                if(a.getChecked()){
                    estanteAlmacen = a;
                    break;
                }


            }



        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String guardarEditarEstante(){
        try {

            Connection con = null;
            con = Util.openConnection(con);

            String consulta = "UPDATE ESTANTE_AMBIENTE  SET   NOMBRE_Estante = '"+estanteAlmacen.getNombreEstante()+"'," +
                    "  OBSERVACION = '"+estanteAlmacen.getObservacion()+"' WHERE COD_Estante = '"+estanteAlmacen.getCodEstante()+"'  ";
            System.out.println("consulta" + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            st.executeUpdate(consulta);

            st.close();
            con.close();


            this.listadoEstantesAlmacen();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



}
