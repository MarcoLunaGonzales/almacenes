/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.web;

import com.cofar.bean.AbstractBean;
import com.cofar.bean.EstadosMaterial;

import com.cofar.util.Util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wchoquehuanca
 */

public class ManagedGestEstadosMaterial extends AbstractBean{
    private List<EstadosMaterial> listaEstadosMaterial= new ArrayList<EstadosMaterial>();
    private EstadosMaterial nuevoEstadoMaterial=new EstadosMaterial();
    private EstadosMaterial editarEstadoMaterial= new EstadosMaterial();
    private Connection con =null;
    private ResultSet res=null;
    private Statement st= null;
    private String codEstadoRegistro="3";
    private String mensaje="";

    /** Creates a new instance of ManagedGestEstadosMaterial */
    public ManagedGestEstadosMaterial() {
    }
    public String getCargarListadoMateriales()
    {
        System.out.println("cargando lista de estado de material'");

        this.cargarListaEstadosMaterial();
        return "";
    }
    private void cargarListaEstadosMaterial()
    {
        try{
            con=Util.openConnection(con);
            st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            String consulta="select er.NOMBRE_ESTADO_REGISTRO,em.COD_ESTADO_REGISTRO, em.COD_COLOR_ESTADO_MATERIAL," +
                            "em.COD_ESTADO_MATERIAL,em.NOMBRE_ESTADO_MATERIAL,em.OBS_ESTADO_MATERIAL "+
                            " from ESTADOS_MATERIAL em inner join ESTADOS_REFERENCIALES er on "+
                            "em.COD_ESTADO_REGISTRO=er.COD_ESTADO_REGISTRO";
           if(!codEstadoRegistro.equals("3"))
           {
                    consulta+=" where em.COD_ESTADO_REGISTRO='"+codEstadoRegistro+"'";
           }
            consulta+=" order by em.NOMBRE_ESTADO_MATERIAL";
           System.out.println("consulta "+consulta);
           res=st.executeQuery(consulta);
           listaEstadosMaterial.clear();
           while(res.next())
           {
               EstadosMaterial nuevo= new EstadosMaterial();
               nuevo.setCodColorEstadoMaterial(res.getString("COD_COLOR_ESTADO_MATERIAL"));
               nuevo.setCodEstadoMaterial(res.getInt("COD_ESTADO_MATERIAL"));
               nuevo.getEstadoReferencial().setCodEstadoRegistro(res.getString("COD_ESTADO_REGISTRO"));
               nuevo.getEstadoReferencial().setNombreEstadoRegistro(res.getString("NOMBRE_ESTADO_REGISTRO"));
               nuevo.setNombreEstadoMaterial(res.getString("NOMBRE_ESTADO_MATERIAL"));
               nuevo.setObsEstadoMaterial(res.getString("OBS_ESTADO_MATERIAL"));
               listaEstadosMaterial.add(nuevo);
           }
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }

    }
    public String nuevoEstadoMaterial_Action()
    {
        nuevoEstadoMaterial= new EstadosMaterial();
        String consulta="select ISNULL(MAX(em.COD_ESTADO_MATERIAL),0)+1 as contCod from ESTADOS_MATERIAL em ";
        
        try
        {
            con= Util.openConnection(con);
            st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            res=st.executeQuery(consulta);
            if(res.next())
            {
                nuevoEstadoMaterial.setCodEstadoMaterial(res.getInt("contCod"));
            }
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
    public String editarEstadosMaterial_action()
    {
        for(EstadosMaterial current:listaEstadosMaterial)
        {
            if(current.getChecked())
            {
                editarEstadoMaterial=current;
                return null;
            }
        }
        return null;
    }
    public String registrarEstadoMaterial()
    {
        String consulta="INSERT INTO ESTADOS_MATERIAL(COD_ESTADO_MATERIAL, NOMBRE_ESTADO_MATERIAL,"+
                        "OBS_ESTADO_MATERIAL, COD_ESTADO_REGISTRO,COD_COLOR_ESTADO_MATERIAL)"+
                        "VALUES ('"+nuevoEstadoMaterial.getCodEstadoMaterial()+"','"+nuevoEstadoMaterial.getNombreEstadoMaterial()+"','"+nuevoEstadoMaterial.getObsEstadoMaterial()+"',"+
                        " 1,'"+nuevoEstadoMaterial.getCodColorEstadoMaterial()+"')";
        try
        {   System.out.println("consulta "+consulta);

            con=Util.openConnection(con);
            PreparedStatement pst=con.prepareStatement(consulta);
            if(pst.executeUpdate()>0)System.out.println("se registro el estado de material");
            pst.close();
            con.close();
            this.cargarListaEstadosMaterial();
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();;
        }

        return null;
    }
    public String guargarEditarEstadoMaterial_Action()
    {
        try
        {
            con=Util.openConnection(con);
            String consulta="";
            boolean update1=true;
            setMensaje("");
            if(editarEstadoMaterial.getEstadoReferencial().getCodEstadoRegistro().equals("2"))
            {
            consulta="select top 1 * from INGRESOS_ALMACEN_DETALLE_ESTADO iade where iade.COD_ESTADO_MATERIAL ='"+editarEstadoMaterial.getCodEstadoMaterial()+"'";
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet res=st.executeQuery(consulta);
            
             if(res.next())
                    {
                        update1=false;
                        setMensaje("No se puede cambiar el estado del registro porque un ingreso a almacen lo esta utilizando");
                        return null;
                    }
                    consulta="select top 1 * from SALIDAS_ALMACEN_DETALLE sad where sad.COD_ESTADO_MATERIAL ='"+editarEstadoMaterial.getCodEstadoMaterial()+"'";
                    res=st.executeQuery(consulta);
                    System.out.println("consulta "+consulta);
                    if(res.next())
                    {
                        update1=false;
                        setMensaje("No se puede cambiar el estado del registro porque una salida de almacen lo esta utilizando");
                        return null;
                    }
                    res.close();
                    st.close();
            }
            if(update1)
            {
            consulta="UPDATE ESTADOS_MATERIAL SET "+
                            "NOMBRE_ESTADO_MATERIAL = '"+editarEstadoMaterial.getNombreEstadoMaterial()+"',"+
                            "OBS_ESTADO_MATERIAL = '"+editarEstadoMaterial.getObsEstadoMaterial()+"',"+
                            "COD_ESTADO_REGISTRO = '"+editarEstadoMaterial.getEstadoReferencial().getCodEstadoRegistro()+"',"+
                            "COD_COLOR_ESTADO_MATERIAL ='"+editarEstadoMaterial.getCodColorEstadoMaterial()+"'"+
                             "WHERE COD_ESTADO_MATERIAL = '"+editarEstadoMaterial.getCodEstadoMaterial()+"'";
            System.out.println("consulta "+consulta);
            PreparedStatement pst=con.prepareStatement(consulta);
            if(pst.executeUpdate()>0)System.out.println("se edito correctamente el estado del material");
            pst.close();
            
            }
            con.close();
            this.cargarListaEstadosMaterial();
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
    public String eliminarEstadoMaterial()
    {
        setMensaje("");
        for(EstadosMaterial current:this.listaEstadosMaterial)
        {
            if(current.getChecked())
            {
                Boolean register=true;
                String consulta="select top 1 * from INGRESOS_ALMACEN_DETALLE_ESTADO iade where iade.COD_ESTADO_MATERIAL ='"+current.getCodEstadoMaterial()+"'";
                try{
                    con=Util.openConnection(con);
                    Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    ResultSet res=st.executeQuery(consulta);
                    System.out.println("consulta "+consulta);
                    if(res.next())
                    {
                        register=false;
                        setMensaje("No se puede eliminar el estado de material porque un ingreso a almacen lo esta utilizando");
                        return null;
                    }
                    consulta="select top 1 * from SALIDAS_ALMACEN_DETALLE sad where sad.COD_ESTADO_MATERIAL ='"+current.getCodEstadoMaterial()+"'";
                    res=st.executeQuery(consulta);
                    System.out.println("consulta "+consulta);
                    if(res.next())
                    {
                        register=false;
                        setMensaje("No se puede eliminar el estado de material porque una salida de almacen lo esta utilizando");
                        return null;
                    }
                    res.close();
                    st.close();
                    if(register)
                    {
                        consulta="DELETE FROM ESTADOS_MATERIAL WHERE COD_ESTADO_MATERIAL = '"+current.getCodEstadoMaterial()+"'";
                        PreparedStatement pst=con.prepareStatement(consulta);
                        System.out.println("consulta delete "+consulta);
                        if(pst.executeUpdate()>0){
                            System.out.println("se elimino el estado material");
                            //
                        }

                        pst.close();
                    }
                    con.close();
                }
                catch(SQLException ex)
                {
                    ex.printStackTrace();
                }

            }
        }
        this.cargarListaEstadosMaterial();
        return null;
    }
    public String onChangeEstadoReferencial()
    {
        this.cargarListaEstadosMaterial();
        return null;
    }
    public String modificarEstadoMaterial()
    {
         String consulta="";
        try
        {
            con=Util.openConnection(con);
            PreparedStatement pst=con.prepareStatement(consulta);
            pst.close();
            con.close();
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();;
        }
        return null;
    }


    public String getCodEstadoRegistro() {
        return codEstadoRegistro;
    }

    public void setCodEstadoRegistro(String codEstadoRegistro) {
        this.codEstadoRegistro = codEstadoRegistro;
    }

    public EstadosMaterial getEditarEstadoMaterial() {
        return editarEstadoMaterial;
    }

    public void setEditarEstadoMaterial(EstadosMaterial editarEstadoMaterial) {
        this.editarEstadoMaterial = editarEstadoMaterial;
    }

    public List<EstadosMaterial> getListaEstadosMaterial() {
        return listaEstadosMaterial;
    }

    public void setListaEstadosMaterial(List<EstadosMaterial> listaEstadosMaterial) {
        this.listaEstadosMaterial = listaEstadosMaterial;
    }

    public EstadosMaterial getNuevoEstadoMaterial() {
        return nuevoEstadoMaterial;
    }

    public void setNuevoEstadoMaterial(EstadosMaterial nuevoEstadoMaterial) {
        this.nuevoEstadoMaterial = nuevoEstadoMaterial;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    

}
