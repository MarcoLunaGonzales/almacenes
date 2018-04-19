/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.web;

import com.cofar.bean.Almacenes;

import com.cofar.util.Util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;

/**
 *
 * @author wchoquehuanca
 */

public class ManagedAlmacenes extends ManagedBean{

    private Connection con=null;
    private Statement st= null;
    private PreparedStatement pst=null;
    private String consulta="";
    private List<Almacenes> almacenesList= new ArrayList<Almacenes>();
    private List<SelectItem> filialesList= new ArrayList<SelectItem>();
    private Almacenes almacenEdicion= new Almacenes();
    private Almacenes almacenNuevo= new Almacenes();
    private String codRegistro="";
    private String mensaje="";
    /** Creates a new instance of ManagedAlmacenes */
    public ManagedAlmacenes() {
    }
    public String getInitAlmacenes()
    {
        codRegistro="3";
        this.cargarFiliales();
        return this.cargarAlmacenes();
    }
    public String cargarAlmacenes()
    {
        try
        {
            con=Util.openConnection(con);
            st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY) ;
            consulta="select f.NOMBRE_FILIAL,f.COD_FILIAL,a.NOMBRE_ALMACEN,a.COD_ALMACEN,a.OBS_ALMACEN,er.NOMBRE_ESTADO_REGISTRO,er.COD_ESTADO_REGISTRO"+
                     " from ALMACENES a left outer join FILIALES f on a.COD_FILIAL=f.COD_FILIAL"+
                     " left outer join ESTADOS_REFERENCIALES er on a.COD_ESTADO_REGISTRO=er.COD_ESTADO_REGISTRO";
            if(!codRegistro.equals("3"))
            {
                consulta+=" where  a.COD_ESTADO_REGISTRO ="+codRegistro;
            }
            consulta+=" order by a.NOMBRE_ALMACEN asc";
            System.out.println("consulta "+consulta);
            ResultSet res=st.executeQuery(consulta);
            almacenesList.clear();
            while(res.next())
            {
                Almacenes nuevo= new Almacenes();
                nuevo.getFiliales().setCodFilial(res.getInt("COD_FILIAL"));
                nuevo.getFiliales().setNombreFilial(res.getString("NOMBRE_FILIAL"));
                nuevo.setNombreAlmacen(res.getString("NOMBRE_ALMACEN"));
                nuevo.setCodAlmacen(res.getInt("COD_ALMACEN"));
                nuevo.setObsAlmacen(res.getString("OBS_ALMACEN"));
                nuevo.getEstadoReferencial().setCodEstadoRegistro(res.getString("COD_ESTADO_REGISTRO"));
                nuevo.getEstadoReferencial().setNombreEstadoRegistro(res.getString("NOMBRE_ESTADO_REGISTRO"));
                almacenesList.add(nuevo);
            }
            st.close();
            con.close();
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
    private void cargarFiliales()
    {
        try
        {
            con=Util.openConnection(con);
            st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            consulta="select f.COD_FILIAL,f.NOMBRE_FILIAL from FILIALES f where f.COD_ESTADO_REGISTRO=1 order by f.NOMBRE_FILIAL";
            ResultSet res=st.executeQuery(consulta);
            filialesList.clear();
            while(res.next())
            {
                filialesList.add(new SelectItem(res.getInt("COD_FILIAL"),res.getString("NOMBRE_FILIAL")));
            }
            res.close();
            st.close();
            con.close();
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
    }
    public String editarAlmacenAction()
    {
        for(Almacenes current:almacenesList)
        {
            if(current.getChecked())
            {
                almacenEdicion=current;
            }
        }
        return null;
    }
    public String nuevoAlmacen()
    {
        almacenNuevo= new Almacenes();
        return null;
    }
    public Boolean almacenUsado(int codAlmacen,String opcion)
    {
        setMensaje("");
        Boolean result=false;
        try
        {
            con=Util.openConnection(con);
            st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            consulta="select top 1 * from SECCIONES s where s.COD_ALMACEN="+codAlmacen;
            System.out.println("consulta "+consulta);

            ResultSet res=st.executeQuery(consulta);
            if(res.next())
            {
                result=true;
                setMensaje("No se puede "+opcion+" el almacen porque una sección lo esta utilizando");
            }
            consulta="select top 1 * from INGRESOS_ALMACEN ia where ia.COD_ALMACEN="+codAlmacen;
           System.out.println("consulta "+consulta);
            res=st.executeQuery(consulta);
            if(res.next())
            {
                result=true;
                setMensaje("No se puede "+opcion+" el almacen porque un ingreso almacen lo esta utilizando");
            }
             consulta="select top 1 * from SALIDAS_ALMACEN sa where sa.COD_ALMACEN="+codAlmacen;
            res=st.executeQuery(consulta);
            if(res.next())
            {
                result=true;
                setMensaje("No se puede "+opcion+" el almacen porque una salida almacen lo esta utilizando");
            }
            consulta="select top 1 * from ORDENES_COMPRA oc where oc.COD_ALMACEN_ENTREGA="+codAlmacen;
            res=st.executeQuery(consulta);
            if(res.next())
            {
                result=true;
                setMensaje("No se puede "+opcion+" el almacen porque una orden de compra lo esta utilizando");
            }
            res.close();
            st.close();
            con.close();
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        return result;
    }
    public String registrarAlmacenAction()
    {
        try
        {
            con=Util.openConnection(con);
            consulta="select ISNULL(MAX(a.COD_ALMACEN),0)+1 AS CODALMACEN from ALMACENES a ";
            st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet res=st.executeQuery(consulta);
            int cod=0;
            if(res.next())
            {
                cod=res.getInt("CODALMACEN");
            }
            res.close();
            st.close();
            consulta="INSERT INTO ALMACENES(COD_ALMACEN, COD_FILIAL, NOMBRE_ALMACEN, OBS_ALMACEN,COD_ESTADO_REGISTRO)"+
                     "VALUES ('"+cod+"','"+almacenNuevo.getFiliales().getCodFilial()+"','"+almacenNuevo.getNombreAlmacen()+"',"+
                     "'"+almacenNuevo.getObsAlmacen()+"',1)";
            System.out.println("consulta"+consulta);
            pst=con.prepareStatement(consulta);
            if(pst.executeUpdate()>0)System.out.println("se registro el almacen");
            pst.close();
            con.close();
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        this.cargarAlmacenes();
        return null;
    }
    public String guardarEdicionAccion()
    {
        Boolean edit=true;
        if(almacenEdicion.getEstadoReferencial().getCodEstadoRegistro().equals("2"))
            edit=!almacenUsado(almacenEdicion.getCodAlmacen(),"editar");
        if(edit)
        {
        try{
        
        con=Util.openConnection(con);
        consulta="UPDATE ALMACENES set NOMBRE_ALMACEN = '"+almacenEdicion.getNombreAlmacen()+"',"+
                 " OBS_ALMACEN = '"+almacenEdicion.getObsAlmacen()+"',COD_ESTADO_REGISTRO = '"+almacenEdicion.getEstadoReferencial().getCodEstadoRegistro()+"'"+
                 " WHERE COD_ALMACEN ='"+almacenEdicion.getCodAlmacen()+"'";
        System.out.println("consulta "+consulta);
        pst=con.prepareStatement(consulta);
        if(pst.executeUpdate()>0)System.out.println("edicion con exito");
        pst.close();
        con.close();
        }
        
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        }
        this.cargarAlmacenes();
        return null;
    }
    public String eliminarAlmacenAction()
    {
        for(Almacenes current:almacenesList)
        {
            if(current.getChecked())
            {
                if(!almacenUsado(current.getCodAlmacen(),"eliminar"))
                {
                    try{
                       con=Util.openConnection(con);
                       consulta="DELETE FROM ALMACENES WHERE COD_ALMACEN ="+current.getCodAlmacen();
                       System.out.println("consulta delete: "+consulta);
                       pst=con.prepareStatement(consulta);
                       if(pst.executeUpdate()>0)System.out.println("se elimino el almacen");
                       pst.close();
                       con.close();
                    }
                    catch(SQLException ex)
                    {
                        ex.printStackTrace();
                    }
                }
            }
        }
        this.cargarAlmacenes();
        return null;
    }
    public Almacenes getAlmacenEdicion() {
        return almacenEdicion;
    }

    public void setAlmacenEdicion(Almacenes almacenEdicion) {
        this.almacenEdicion = almacenEdicion;
    }

    public Almacenes getAlmacenNuevo() {
        return almacenNuevo;
    }

    public void setAlmacenNuevo(Almacenes almacenNuevo) {
        this.almacenNuevo = almacenNuevo;
    }

    public List<Almacenes> getAlmacenesList() {
        return almacenesList;
    }

    public void setAlmacenesList(List<Almacenes> almacenesList) {
        this.almacenesList = almacenesList;
    }

    public String getCodRegistro() {
        return codRegistro;
    }

    public void setCodRegistro(String codRegistro) {
        this.codRegistro = codRegistro;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List<SelectItem> getFilialesList() {
        return filialesList;
    }

    public void setFilialesList(List<SelectItem> filialesList) {
        this.filialesList = filialesList;
    }



}
