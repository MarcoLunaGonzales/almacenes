/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.web;

import com.cofar.bean.Materiales;
import com.cofar.bean.Secciones;
import com.cofar.bean.SeccionesDetalle;
import com.cofar.dao.DaoEstadosReferenciales;
import com.cofar.util.Util;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.model.SelectItem;

/**
 *
 * @author hvaldivia
 */

public class ManagedSecciones {

    List seccionesList = new ArrayList();
    Secciones secciones = new Secciones();
    List estadosReferencialesList = new ArrayList();
    ManagedCapitulos managedCapitulos = new ManagedCapitulos();
    List seccionesDetalleList = new ArrayList();
    String mensaje = "";
    List gruposList = new ArrayList();
    List capitulosList = new ArrayList();
    List materialesList = new ArrayList();
    ManagedRegistroSalidaAlmacen managedRegistroSalidaAlmacen = new ManagedRegistroSalidaAlmacen();
    SeccionesDetalle seccionesDetalle = new SeccionesDetalle();
    List filialesList = new ArrayList();
    List almacenesList = new ArrayList();
    
    
    
    
    public List getSeccionesList() {
        return seccionesList;
    }

    public void setSeccionesList(List seccionesList) {
        this.seccionesList = seccionesList;
    }

    public Secciones getSecciones() {
        return secciones;
    }

    public void setSecciones(Secciones secciones) {
        this.secciones = secciones;
    }

    public List getEstadosReferencialesList() {
        return estadosReferencialesList;
    }

    public void setEstadosReferencialesList(List estadosReferencialesList) {
        this.estadosReferencialesList = estadosReferencialesList;
    }

    public List getSeccionesDetalleList() {
        return seccionesDetalleList;
    }

    public void setSeccionesDetalleList(List seccionesDetalleList) {
        this.seccionesDetalleList = seccionesDetalleList;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List getCapitulosList() {
        return capitulosList;
    }

    public void setCapitulosList(List capitulosList) {
        this.capitulosList = capitulosList;
    }

    public List getGruposList() {
        return gruposList;
    }

    public void setGruposList(List gruposList) {
        this.gruposList = gruposList;
    }

    public SeccionesDetalle getSeccionesDetalle() {
        return seccionesDetalle;
    }

    public void setSeccionesDetalle(SeccionesDetalle seccionesDetalle) {
        this.seccionesDetalle = seccionesDetalle;
    }

    public List getMaterialesList() {
        return materialesList;
    }

    public void setMaterialesList(List materialesList) {
        this.materialesList = materialesList;
    }

    public List getAlmacenesList() {
        return almacenesList;
    }

    public void setAlmacenesList(List almacenesList) {
        this.almacenesList = almacenesList;
    }

    public List getFilialesList() {
        return filialesList;
    }

    public void setFilialesList(List filialesList) {
        this.filialesList = filialesList;
    }

   


    




    

    


    

   

 
    

    
    /** Creates a new instance of ManagedSecciones */
    public ManagedSecciones() {
    }
    public String getCargarContenidoSecciones(){
        try {
            this.listadoSecciones();
            DaoEstadosReferenciales daoEstadosReferenciales = new DaoEstadosReferenciales();
            estadosReferencialesList = daoEstadosReferenciales.listarActivosRegistro();
            capitulosList = managedRegistroSalidaAlmacen.cargarCapitulos();
            gruposList.add(new SelectItem("0","-NINGUNO-") );
            materialesList.add(new SelectItem("0","-NINGUNO-"));
            filialesList = this.cargarFiliales();
            almacenesList.clear();
            almacenesList.add(new SelectItem("0","-NINGUNO-"));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public String editarSeccion_action(){
        try {
            Iterator i = seccionesList.iterator();
            while(i.hasNext()){
                secciones = (Secciones)i.next();
                if(secciones.getChecked().booleanValue()==true){
                    break;
                }
            }
            this.listadoSeccionesDetalle();




        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void listadoSeccionesDetalle(){
        try {


            Connection con = null;
            con = Util.openConnection(con);
            //llenado del detalle
            String consulta = " select s.COD_SECCION,       c.COD_CAPITULO,       c.NOMBRE_CAPITULO,       g.COD_GRUPO,       g.NOMBRE_GRUPO," +
                    "       m.COD_MATERIAL,       m.NOMBRE_MATERIAL from SECCIONES_DETALLE s" +
                    "     left outer join CAPITULOS c on s.COD_CAPITULO = c.COD_CAPITULO" +
                    "     left outer join GRUPOS g on g.COD_CAPITULO = s.COD_CAPITULO and g.COD_GRUPO = s.COD_GRUPO " +
                    "     left outer join MATERIALES m on m.COD_MATERIAL = s.COD_MATERIAL and m.COD_GRUPO=g.COD_GRUPO " +
                    "     where s.COD_SECCION = '"+secciones.getCodSeccion()+"'   ";
            System.out.println("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            seccionesDetalleList.clear();
            while(rs.next()){
                SeccionesDetalle seccionesDetalle = new SeccionesDetalle();
                seccionesDetalle.getSecciones().setCodSeccion(rs.getInt("COD_SECCION"));
                seccionesDetalle.getCapitulos().setCodCapitulo(rs.getInt("COD_CAPITULO"));
                seccionesDetalle.getCapitulos().setNombreCapitulo(rs.getString("NOMBRE_CAPITULO"));
                seccionesDetalle.getGrupos().setCodGrupo(rs.getInt("COD_GRUPO"));
                seccionesDetalle.getGrupos().setNombreGrupo(rs.getString("NOMBRE_GRUPO"));
                seccionesDetalle.getMateriales().setCodMaterial(String.valueOf(rs.getInt("COD_MATERIAL")));
                seccionesDetalle.getMateriales().setNombreMaterial(rs.getString("NOMBRE_MATERIAL"));
                seccionesDetalleList.add(seccionesDetalle);
                
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
  
    public void listadoSecciones(){
        try {
            Connection con = null;
            con = Util.openConnection(con);

            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            String consulta = " select s.COD_SECCION,s.NOMBRE_SECCION,a.COD_ALMACEN,a.NOMBRE_ALMACEN,f.COD_FILIAL,f.NOMBRE_FILIAL,s.COD_ESTADO_REGISTRO,  OBS_SECCION" +
                    " from SECCIONES s  " +
                    " inner join ALMACENES a on s.COD_ALMACEN = a.COD_ALMACEN " +
                    " inner join FILIALES f on f.COD_FILIAL = a.COD_FILIAL " +
                    " where s.ESTADO_SISTEMA = 1 ";
            System.out.println("consulta: "+consulta);
            ResultSet rs = st.executeQuery(consulta);
            seccionesList.clear();
            while(rs.next()){
                Secciones secciones = new Secciones();
                secciones.setCodSeccion(rs.getInt("COD_SECCION"));
                secciones.setNombreSeccion(rs.getString("NOMBRE_SECCION"));
                secciones.setObsSeccion(rs.getString("OBS_SECCION"));
                secciones.getEstadoReferencial().setCodEstadoRegistro(rs.getString("COD_ESTADO_REGISTRO"));
                secciones.getAlmacenes().setCodAlmacen(rs.getInt("COD_ALMACEN"));
                secciones.getAlmacenes().setNombreAlmacen(rs.getString("NOMBRE_ALMACEN"));
                secciones.getAlmacenes().getFiliales().setCodFilial(rs.getInt("COD_FILIAL"));
                secciones.getAlmacenes().getFiliales().setNombreFilial(rs.getString("NOMBRE_FILIAL"));
                secciones.getAlmacenes().getFiliales().setNombreFilial(rs.getString("NOMBRE_FILIAL"));
                seccionesList.add(secciones);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String eliminarSeccionesDetalle_action(){
        try {
            mensaje="";
            SeccionesDetalle seccionesDetalleItem = new SeccionesDetalle();

            Iterator i = seccionesDetalleList.iterator();
            while(i.hasNext()){
                seccionesDetalleItem = (SeccionesDetalle)i.next();
                if(seccionesDetalleItem.getChecked().booleanValue()==true){
                    break;
                }
            }
            System.out.println("la seccion seleccionada " + seccionesDetalleItem.getSecciones().getCodSeccion());

            

            //SE DEBE VERIFICAR SI LA SECCION_DETALLE ESTA EN USO
            if(this.SeccionDetalleUsado(seccionesDetalleItem)==true){
                mensaje = " la seccion se esta usando en transacciones de ingreso ";
                return null;
            }
            


            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            
            String consulta = " DELETE FROM SECCIONES_DETALLE WHERE ISNULL(COD_SECCION,0)='"+seccionesDetalleItem.getSecciones().getCodSeccion()+"' " +
                    " AND ISNULL(COD_CAPITULO,0)='"+seccionesDetalleItem.getCapitulos().getCodCapitulo()+"' " +
                    " AND ISNULL(COD_GRUPO,0)='"+seccionesDetalleItem.getGrupos().getCodGrupo()+"' " +
                    " AND ISNULL(COD_MATERIAL,0) = '"+seccionesDetalleItem.getMateriales().getCodMaterial()+"' ";

            System.out.println("consulta" + consulta);
            
            st.executeUpdate(consulta);
            st.close();
            con.close();
            this.listadoSeccionesDetalle();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public boolean SeccionDetalleUsado(SeccionesDetalle seccionesDetalle){
        boolean seccionDetalleUsado = false;
        try {
            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            String consulta = " select top 1 * from INGRESOS_ALMACEN_DETALLE i " +
                    " inner join MATERIALES m on m.COD_MATERIAL = i.COD_MATERIAL " +
                    " inner join GRUPOS gr on gr.COD_GRUPO = m.COD_GRUPO " +
                    " where i.COD_SECCION = '"+seccionesDetalle.getSecciones().getCodSeccion()+"' " ;
            if(!seccionesDetalle.getMateriales().getCodMaterial().equals("0")){
                consulta = consulta + " and m.COD_MATERIAL = '"+seccionesDetalle.getMateriales().getCodMaterial()+"' " ;
            }
            if(seccionesDetalle.getGrupos().getCodGrupo()>0){
                consulta = consulta  + "  and gr.COD_GRUPO = '"+seccionesDetalle.getGrupos().getCodGrupo()+"' ";
            }
            if(seccionesDetalle.getCapitulos().getCodCapitulo()>0){
                consulta = consulta + " and gr.COD_CAPITULO = '"+seccionesDetalle.getCapitulos().getCodCapitulo()+"' ";
            }
            System.out.println("consulta "  + consulta);
            ResultSet rs = st.executeQuery(consulta);
            if(rs.next()){
                seccionDetalleUsado = true;
            }
            rs.close();
            st.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return seccionDetalleUsado;
    }

    public String capitulos_change(){
        try {
            gruposList = managedRegistroSalidaAlmacen.cargarGrupos(seccionesDetalle.getCapitulos().getCodCapitulo());
            materialesList = managedRegistroSalidaAlmacen.cargarMateriales(0);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public String agregarSeccionDetalle_action(){
        try {
            seccionesDetalle = new SeccionesDetalle();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public String grupos_change(){
        try {
            materialesList = managedRegistroSalidaAlmacen.cargarMateriales(Integer.valueOf(seccionesDetalle.getGrupos().getCodGrupo()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public String guardarAgregarSeccionDetalle_action(){
        try {

            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String consulta = " INSERT INTO dbo.SECCIONES_DETALLE (  COD_SECCION,  COD_CAPITULO,  COD_GRUPO,  COD_MATERIAL) " +
                    " VALUES ( '"+secciones.getCodSeccion()+"', '"+seccionesDetalle.getCapitulos().getCodCapitulo()+"',  " +
                    "'"+seccionesDetalle.getGrupos().getCodGrupo()+"' ,  '"+seccionesDetalle.getMateriales().getCodMaterial()+"'); ";
             System.out.println("consulta "  + consulta);
             st.executeUpdate(consulta);
             st.close();
             con.close();
             this.listadoSeccionesDetalle();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public String guardarEditarSeccion_action(){
        try {
            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String consulta = " UPDATE dbo.SECCIONES  SET    NOMBRE_SECCION = '"+secciones.getNombreSeccion()+"'," +
                    "  OBS_SECCION = '"+secciones.getObsSeccion()+"',  COD_ESTADO_REGISTRO = '"+secciones.getEstadoReferencial().getCodEstadoRegistro()+"' " +
                    "  WHERE   COD_SECCION = '"+secciones.getCodSeccion()+"'; ";

            System.out.println("consulta" + consulta);

            st.executeUpdate(consulta);

            st.close();
            con.close();

            this.listadoSecciones();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List cargarFiliales(){
          List filialesList = new ArrayList();
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select cod_filial, nombre_filial from filiales where cod_estado_registro=1 ";

            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs=st.executeQuery(consulta);
            filialesList.clear();
            filialesList.add(new SelectItem("0", "-NINGUNO-"));
            while (rs.next()){
                filialesList.add(new SelectItem(rs.getString("cod_filial"), rs.getString("nombre_filial")));
            }
            if(rs!=null){
                rs.close();
                st.close();
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
          return filialesList;
    }   

    public List cargarAlmacenes(int codFilial){
          List almacenesList = new ArrayList();
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select cod_almacen, nombre_almacen from almacenes where cod_estado_registro=1 and cod_filial='"+codFilial+"' ";

            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs=st.executeQuery(consulta);
            almacenesList.clear();
            almacenesList.add(new SelectItem("0", "-NINGUNO-"));
            while (rs.next()){
                almacenesList.add(new SelectItem(rs.getString("cod_almacen"), rs.getString("nombre_almacen")));
            }
            if(rs!=null){
                rs.close();
                st.close();
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
          return almacenesList;
    }
    public String filial_change(){
        almacenesList = this.cargarAlmacenes(secciones.getAlmacenes().getFiliales().getCodFilial());
        return null;
    }
    public String agregarSecciones_action(){
        try {
            secciones = new Secciones();

            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public String guardarAgregarSecciones_action(){
        try {
            secciones.setCodSeccion(this.generaCodSeccion());
            secciones.getEstadoReferencial().setCodEstadoRegistro("1");
            secciones.setEstadoSistema(1);
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " INSERT INTO SECCIONES(  COD_SECCION,  COD_ALMACEN,  NOMBRE_SECCION,  OBS_SECCION,  COD_ESTADO_REGISTRO," +
                    "  ESTADO_SISTEMA) VALUES ( '"+secciones.getCodSeccion()+"',  '"+secciones.getAlmacenes().getCodAlmacen()+"',  " +
                    "'"+secciones.getNombreSeccion()+"' ,  '"+secciones.getObsSeccion()+"',  '"+secciones.getEstadoReferencial().getCodEstadoRegistro()+"', '"+secciones.getEstadoSistema()+"'); ";
            System.out.println("consulta " + consulta);
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            st.executeUpdate(consulta);
            st.close();
            con.close();
            this.listadoSecciones();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public int generaCodSeccion(){
        int codSeccion = 0;
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select isnull(max(s.COD_SECCION),0)+1 COD_SECCION from SECCIONES s where s.COD_ESTADO_REGISTRO = 1 ";
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs=st.executeQuery(consulta);
            if(rs.next()){
                codSeccion = rs.getInt("COD_SECCION");
            }
            st.close();
            rs.close();
            con.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return codSeccion;
    }
    public String eliminarSecciones_action(){
        try {
            Secciones seccionesItem = new Secciones();

            Iterator i = seccionesList.iterator();
            while(i.hasNext()){
                seccionesItem = (Secciones)i.next();
                if(seccionesItem.getChecked().booleanValue()==true){
                    break;
                }
            }

            if(this.SeccionUsado(seccionesItem)){
                mensaje = " la seccion seleccionada esta siendo usada en ingresos de almacen ";
                return null;
            }

            Connection con = null;
            con = Util.openConnection(con);
            con.setAutoCommit(false);
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            //eliminar dependencias
            String consulta = " DELETE FROM dbo.SECCIONES_DETALLE WHERE COD_SECCION = '"+seccionesItem.getCodSeccion()+"' ";
            System.out.println("consulta " + consulta);
            st.executeUpdate(consulta);
            //eliminar maestro
            consulta = " DELETE FROM dbo.SECCIONES WHERE COD_SECCION = '"+seccionesItem.getCodSeccion()+"' ";
            System.out.println("consulta " + consulta);
            st.executeUpdate(consulta);            
            con.commit();
            st.close();
            con.close();

            
            


        } catch (Exception e) {
            e.printStackTrace();
        }
        this.listadoSecciones();
        return null;
    }

    public boolean SeccionUsado(Secciones secciones){
        boolean seccionUsado = false;
        try {
            Connection con = null;
            con = Util.openConnection(con);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            String consulta = " select top 1 * from INGRESOS_ALMACEN_DETALLE i " +
                    " inner join MATERIALES m on m.COD_MATERIAL = i.COD_MATERIAL " +
                    " inner join GRUPOS gr on gr.COD_GRUPO = m.COD_GRUPO " +
                    " where i.COD_SECCION = '"+secciones.getCodSeccion()+"' " ;
           
            System.out.println("consulta "  + consulta);
            ResultSet rs = st.executeQuery(consulta);
            if(rs.next()){
                seccionUsado = true;
            }
            rs.close();
            st.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return seccionUsado;
    }

}
