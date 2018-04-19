/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.web;

import com.cofar.bean.TiposSalidaAlmacenProduccion;
import com.cofar.bean.TiposSalidasAlmacen;
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

public class ManagedTiposSalidaAlmacen extends ManagedBean{

    private List<TiposSalidasAlmacen> listaTiposSalidaAlmacen= new ArrayList<TiposSalidasAlmacen>();
    private Connection con=null;
    private Statement st=null;
    private PreparedStatement pst=null;
    private String consulta="";
    private String estadoRegistro="";
    private TiposSalidasAlmacen nuevoTipoSalidaAlmacen= new TiposSalidasAlmacen();
    private TiposSalidasAlmacen editarTipoSalidaAlmacen= new TiposSalidasAlmacen();
    private String mensaje="";
    private int cantidadDependencias = 0;
    /** Creates a new instance of ManagedTiposSalidaAlmacen */
    public ManagedTiposSalidaAlmacen() {
    }
    public String  getInitTiposSalida()
    {
        estadoRegistro="3";
        this.cargarListaTiposSalida();
        
        return null;
    }
    private void cargarListaTiposSalida()
    {
        try{
            con=Util.openConnection(con);
            st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            consulta="select tsa.COD_TIPO_SALIDA_ALMACEN,tsa.NOMBRE_TIPO_SALIDA_ALMACEN, tsa.OBS_TIPO_SALIDA_ALMACEN,tsa.COD_ESTADO_REGISTRO,er.NOMBRE_ESTADO_REGISTRO "+
                      " from TIPOS_SALIDAS_ALMACEN tsa left outer join ESTADOS_REFERENCIALES er on tsa.COD_ESTADO_REGISTRO=er.COD_ESTADO_REGISTRO";

            if(!estadoRegistro.equals("3"))
            {
                consulta+=" where tsa.COD_ESTADO_REGISTRO='"+estadoRegistro+"'";
            }
             consulta+=" order by tsa.NOMBRE_TIPO_SALIDA_ALMACEN";

            System.out.println("consulta "+consulta);
            ResultSet res=st.executeQuery(consulta);
            listaTiposSalidaAlmacen.clear();
            while(res.next())
            {
                TiposSalidasAlmacen nuevo= new TiposSalidasAlmacen();
                nuevo.setCodTipoSalidaAlmacen(res.getInt("COD_TIPO_SALIDA_ALMACEN"));
                nuevo.setNombreTipoSalidaAlmacen(res.getString("NOMBRE_TIPO_SALIDA_ALMACEN"));
                nuevo.setObsTipoSalidaAlmacen(res.getString("OBS_TIPO_SALIDA_ALMACEN"));
                nuevo.getEstadoReferencial().setCodEstadoRegistro(res.getString("COD_ESTADO_REGISTRO"));
                nuevo.getEstadoReferencial().setNombreEstadoRegistro(res.getString("NOMBRE_ESTADO_REGISTRO"));
                listaTiposSalidaAlmacen.add(nuevo);
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
    public String nuevoTipoSalidaAlmacen_Action()
    {
            nuevoTipoSalidaAlmacen= new TiposSalidasAlmacen();
            return null;
    }
    public String editarTipoSalidaAlmacen_Action() throws SQLException
    {
        Connection con = null;
        mensaje = "";
        cantidadDependencias = 0;
        try {
            for(TiposSalidasAlmacen current:listaTiposSalidaAlmacen)
            {
                if(current.getChecked())
                {
                    editarTipoSalidaAlmacen=current;
                    break;
                }
            }
            con = Util.openConnection(con);
            StringBuilder consulta = new StringBuilder(" select COUNT(*) as CANTIDAD_REGISTRO");
                    consulta.append(" from SALIDAS_ALMACEN sa")
                            .append(" where sa.COD_TIPO_SALIDA_ALMACEN = '").append(editarTipoSalidaAlmacen.getCodTipoSalidaAlmacen()).append("' ");
            System.out.println("consulta " + consulta.toString());
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta.toString());
            while(rs.next()){
                cantidadDependencias = rs.getInt("CANTIDAD_REGISTRO");
            }
            if (cantidadDependencias != 0) {
                mensaje = "Este Tipo de Salida esta asociado a una o más Salidas de Almacén; por lo tanto, no puede ser modificado.";
            }
            st.close();
            
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            con.close();
        }
        
        return null;
    }
    public String guardarTipoSalidaAlmacen_Action()
    {
        try
        {
            con=Util.openConnection(con);
            consulta="select ISNULL(MAX(tsa.COD_TIPO_SALIDA_ALMACEN),0)+1 as cod from TIPOS_SALIDAS_ALMACEN tsa ";
            st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet res=st.executeQuery(consulta);
            int cod=0;
            if(res.next())
            {
                cod=res.getInt("cod");
            }
            res.close();
            consulta="INSERT INTO TIPOS_SALIDAS_ALMACEN(COD_TIPO_SALIDA_ALMACEN,NOMBRE_TIPO_SALIDA_ALMACEN, OBS_TIPO_SALIDA_ALMACEN, COD_ESTADO_REGISTRO) "+
                     "VALUES ('"+cod+"', '"+nuevoTipoSalidaAlmacen.getNombreTipoSalidaAlmacen()+"','"+nuevoTipoSalidaAlmacen.getObsTipoSalidaAlmacen()+"','"+nuevoTipoSalidaAlmacen.getEstadoReferencial().getCodEstadoRegistro()+"');";
            System.out.println("consulta "+consulta);
            pst=con.prepareStatement(consulta);
            if(pst.executeUpdate()>0)System.out.println("se inserto el detalle");
            pst.close();
            con.close();
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        this.cargarListaTiposSalida();
        return null;
    }
    private Boolean edicionUsada(int codTipoSalida,String tipoOperacion)
    {
        boolean result=false;
        setMensaje("");
        try
        {
            con=Util.openConnection(con);
            st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            consulta="select top 1 * from SALIDAS_ALMACEN sa where sa.COD_TIPO_SALIDA_ALMACEN='"+codTipoSalida+"'";
            System.out.println("consulta "+consulta);
            ResultSet res=st.executeQuery(consulta);
            if(res.next())
            {
                result=true;
                setMensaje("No se puede "+tipoOperacion+" el registro porque una salida de almacen lo esta utilizando ");
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
    public String guardarEdicionTipoSalidaAlmacen_Action()
    {
        boolean register=true;
        setMensaje("");
        if(editarTipoSalidaAlmacen.getEstadoReferencial().getCodEstadoRegistro().equals("2"))
        {
            register=!this.edicionUsada(editarTipoSalidaAlmacen.getCodTipoSalidaAlmacen(),"editar");
        }
        if(register)
        {
        try
        {
            con=Util.openConnection(con);
            consulta="UPDATE TIPOS_SALIDAS_ALMACEN SET "+
                     " NOMBRE_TIPO_SALIDA_ALMACEN ='"+editarTipoSalidaAlmacen.getNombreTipoSalidaAlmacen()+"',"+
                     " OBS_TIPO_SALIDA_ALMACEN = '"+editarTipoSalidaAlmacen.getObsTipoSalidaAlmacen()+"',"+
                     " COD_ESTADO_REGISTRO = '"+editarTipoSalidaAlmacen.getEstadoReferencial().getCodEstadoRegistro()+"'"+
                     " WHERE COD_TIPO_SALIDA_ALMACEN = '"+editarTipoSalidaAlmacen.getCodTipoSalidaAlmacen()+"'";
            System.out.println("consulta "+consulta);
            pst=con.prepareStatement(consulta);
            if(pst.executeUpdate()>0)System.out.println("se actualizo el detalle");
            pst.close();
            con.close();
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        }
            this.cargarListaTiposSalida();
        return null;
    }
    public String onChange_estadoRegistro()
    {
        this.cargarListaTiposSalida();
        return null;
    }
    public String eliminarTipoSalidaAlmacen_Action()
    {
        for(TiposSalidasAlmacen current:listaTiposSalidaAlmacen)
        {
            if(current.getChecked())
            {
                if(!this.edicionUsada(current.getCodTipoSalidaAlmacen(),"eliminar"))
                {
                    try
                     {
                        con=Util.openConnection(con);
                        consulta="DELETE FROM TIPOS_SALIDAS_ALMACEN WHERE   COD_TIPO_SALIDA_ALMACEN ='"+current.getCodTipoSalidaAlmacen()+"'";
                        System.out.println("consulta "+consulta);

                        pst=con.prepareStatement(consulta);
                        if(pst.executeUpdate()>0)System.out.println("se elimino el detalle");
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
        this.cargarListaTiposSalida();
        return null;
    }

    public TiposSalidasAlmacen getEditarTipoSalidaAlmacen() {
        return editarTipoSalidaAlmacen;
    }

    public void setEditarTipoSalidaAlmacen(TiposSalidasAlmacen editarTipoSalidaAlmacen) {
        this.editarTipoSalidaAlmacen = editarTipoSalidaAlmacen;
    }

    public List<TiposSalidasAlmacen> getListaTiposSalidaAlmacen() {
        return listaTiposSalidaAlmacen;
    }

    public void setListaTiposSalidaAlmacen(List<TiposSalidasAlmacen> listaTiposSalidaAlmacen) {
        this.listaTiposSalidaAlmacen = listaTiposSalidaAlmacen;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public TiposSalidasAlmacen getNuevoTipoSalidaAlmacen() {
        return nuevoTipoSalidaAlmacen;
    }

    public void setNuevoTipoSalidaAlmacen(TiposSalidasAlmacen nuevoTipoSalidaAlmacen) {
        this.nuevoTipoSalidaAlmacen = nuevoTipoSalidaAlmacen;
    }

    public String getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(String estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

    public int getCantidadDependencias() {
        return cantidadDependencias;
    }

    public void setCantidadDependencias(int cantidadDependencias) {
        this.cantidadDependencias = cantidadDependencias;
    }


}
