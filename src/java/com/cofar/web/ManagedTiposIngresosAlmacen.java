/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.web;

import com.cofar.bean.TiposIngresoAlmacen;
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

public class ManagedTiposIngresosAlmacen extends ManagedBean {

    private List<TiposIngresoAlmacen> listaTiposIngresosAlmacen= new ArrayList<TiposIngresoAlmacen>();
    private Connection con=null;
    private Statement st=null;
    private PreparedStatement pst=null;
    private String consulta="";
    private String codEstadoRegistro="";
    private TiposIngresoAlmacen nuevoTipoIngresoAlmacen= new TiposIngresoAlmacen();
    private TiposIngresoAlmacen editarTipoIngresoAlmacen= new TiposIngresoAlmacen();
    private String mensaje="";
    private int cantidadDependencias = 0;
    
    /** Creates a new instance of ManagedTiposIngresosAlmacen */
    public ManagedTiposIngresosAlmacen() {
    }
    public String getInitTiposIngresoAlmacen()
    {
        codEstadoRegistro="3";
        this.cargarTiposIngresoAlmacen();
        return null;
    }
    private void cargarTiposIngresoAlmacen()
    {
        try
        {
            con=Util.openConnection(con);
            st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            consulta="select tia.COD_TIPO_INGRESO_ALMACEN,tia.NOMBRE_TIPO_INGRESO_ALMACEN,tia.OBS_TIPO_INGRESO_ALMACEN "+
                      ", er.COD_ESTADO_REGISTRO,er.NOMBRE_ESTADO_REGISTRO"+
                      " from TIPOS_INGRESO_ALMACEN tia left outer join ESTADOS_REFERENCIALES er"+
                      " on tia.COD_ESTADO_REGISTRO=er.COD_ESTADO_REGISTRO ";
                      if(!codEstadoRegistro.equals("3"))
                      {
                      consulta+=" where tia.COD_ESTADO_REGISTRO='"+codEstadoRegistro+"'";
                      }
            consulta+=" order by tia.NOMBRE_TIPO_INGRESO_ALMACEN";
            System.out.println("consulta "+consulta);
            ResultSet res=st.executeQuery(consulta);
            listaTiposIngresosAlmacen.clear();
            while(res.next())
            {
                TiposIngresoAlmacen nuevo= new TiposIngresoAlmacen();
                nuevo.setCodTipoIngresoAlmacen(res.getInt("COD_TIPO_INGRESO_ALMACEN"));
                nuevo.setNombreTipoIngresoAlmacen(res.getString("NOMBRE_TIPO_INGRESO_ALMACEN"));
                nuevo.setObsTipoIngresoAlmacen(res.getString("OBS_TIPO_INGRESO_ALMACEN"));
                nuevo.getEstadoReferencial().setCodEstadoRegistro(res.getString("COD_ESTADO_REGISTRO"));
                nuevo.getEstadoReferencial().setNombreEstadoRegistro(res.getString("NOMBRE_ESTADO_REGISTRO"));
                listaTiposIngresosAlmacen.add(nuevo);
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
    public String editarTipoIngresoAlmacen_Action() throws SQLException
    {
        Connection con = null;
        cantidadDependencias = 0;
        mensaje = "";
        try {
            con = Util.openConnection(con);
            for(TiposIngresoAlmacen current:listaTiposIngresosAlmacen)
            {
                if(current.getChecked())
                {
                    editarTipoIngresoAlmacen=current;
                    break;

                }
            }
            
            StringBuilder consulta = new StringBuilder( " select COUNT(*) as CANTIDAD_REGISTRO")
                    .append(" from INGRESOS_ALMACEN ia")
                    .append(" where ia.COD_TIPO_INGRESO_ALMACEN = '").append(editarTipoIngresoAlmacen.getCodTipoIngresoAlmacen()).append("' ");
            System.out.println("consulta: " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta.toString());
            while(rs.next()){
                cantidadDependencias =  rs.getInt("CANTIDAD_REGISTRO");
            }
            if (cantidadDependencias != 0) {
                mensaje = "Este Tipo de Ingreso esta asociado a uno o más Ingresos de Almacén; por lo tanto, no puede ser modificado.";
            }
            
            st.close();  
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            con.close();
        }
        
        return null;
    }
    public String nuevoTipoIngresoAlmacen_Action()
    {
        nuevoTipoIngresoAlmacen= new TiposIngresoAlmacen();
        return null;
    }
    private boolean registroUsado(int codTipoIngresoAlmacen, String operacion)
    {
        boolean usado=false;
        try
        {
            con=Util.openConnection(con);
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            consulta="select top 1 * from INGRESOS_ALMACEN ia where ia.COD_TIPO_INGRESO_ALMACEN='"+codTipoIngresoAlmacen+"'";
            System.out.println("consulta "+consulta);
            ResultSet res=st.executeQuery(consulta);
            if(res.next())
            {
                usado=true;
                setMensaje("No se puede "+operacion+" el registro porque un ingreso a almacen lo esta utilizando");
            }
            res.close();
            st.close();
            con.close();
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        return usado;
    }
    public String onChange_EstadoRegistro()
    {
        this.cargarTiposIngresoAlmacen();
        return null;
    }
    public String guardarTipoIngresoAlmacen_Action()
    {

        try
        {
            con=Util.openConnection(con);
            st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            consulta="select ISNULL(MAX(tia.COD_TIPO_INGRESO_ALMACEN),0)+1 as cod from TIPOS_INGRESO_ALMACEN tia";
            ResultSet res=st.executeQuery(consulta);
            int cod=0;
            if(res.next())
            {
                cod=res.getInt("cod");
            }
            res.close();
            st.close();
            consulta="INSERT INTO TIPOS_INGRESO_ALMACEN(COD_TIPO_INGRESO_ALMACEN,"+
                     " NOMBRE_TIPO_INGRESO_ALMACEN, OBS_TIPO_INGRESO_ALMACEN, COD_ESTADO_REGISTRO)"+
                     "VALUES ('"+cod+"','"+nuevoTipoIngresoAlmacen.getNombreTipoIngresoAlmacen()+"',"+
                     "'"+nuevoTipoIngresoAlmacen.getObsTipoIngresoAlmacen()+"','"+nuevoTipoIngresoAlmacen.getEstadoReferencial().getCodEstadoRegistro()+"')";
            System.out.println("consulta "+consulta) ;
            pst=con.prepareStatement(consulta);
            if(pst.executeUpdate()>0)System.out.println("se inserto el tipo de ingreso almacen");
            pst.close();
            con.close();
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        this.cargarTiposIngresoAlmacen();
        return null;
    }
    public String guardarEdicionTipoIngresoAlmacen_Action()
    {
        setMensaje("");
        boolean register=true;
        if(editarTipoIngresoAlmacen.getEstadoReferencial().getCodEstadoRegistro().equals("2"))
        {
            register=!this.registroUsado(editarTipoIngresoAlmacen.getCodTipoIngresoAlmacen(),"editar");
        }
        if(register)
        {
            try
            {
                con=Util.openConnection(con);
                consulta="UPDATE TIPOS_INGRESO_ALMACEN SET "+
                         " NOMBRE_TIPO_INGRESO_ALMACEN = '"+editarTipoIngresoAlmacen.getNombreTipoIngresoAlmacen()+"',"+
                         " OBS_TIPO_INGRESO_ALMACEN = '"+editarTipoIngresoAlmacen.getObsTipoIngresoAlmacen()+"',"+
                         " COD_ESTADO_REGISTRO ='"+editarTipoIngresoAlmacen.getEstadoReferencial().getCodEstadoRegistro()+"'"+
                         " WHERE COD_TIPO_INGRESO_ALMACEN ='"+editarTipoIngresoAlmacen.getCodTipoIngresoAlmacen()+"'";
                System.out.println("consulta "+consulta);
                pst=con.prepareStatement(consulta);

                if(pst.executeUpdate()>0)System.out.println("se edito el registro");
                pst.close();
                con.close();

            }
            catch(SQLException ex)
            {
                ex.printStackTrace();
            }
        }
        this.cargarTiposIngresoAlmacen();
        return null;

    }
    public String eliminarTipoIngresoAlmacen_Action()
    {
        setMensaje("");
        for(TiposIngresoAlmacen current:listaTiposIngresosAlmacen)
        {
            if(current.getChecked())
            {
                if(!this.registroUsado(current.getCodTipoIngresoAlmacen(),"eliminar"))
                {
                    try
                    {
                        con=Util.openConnection(con);
                        consulta="DELETE FROM TIPOS_INGRESO_ALMACEN WHERE COD_TIPO_INGRESO_ALMACEN ='"+current.getCodTipoIngresoAlmacen()+"'";
                        System.out.println("consulta delete "+consulta);
                        pst=con.prepareStatement(consulta);

                        if(pst.executeUpdate()>0)System.out.println("se elimino el registro");
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
        this.cargarTiposIngresoAlmacen();
        return null;
    }

    public String getCodEstadoRegistro() {
        return codEstadoRegistro;
    }

    public void setCodEstadoRegistro(String codEstadoRegistro) {
        this.codEstadoRegistro = codEstadoRegistro;
    }

    public TiposIngresoAlmacen getEditarTipoIngresoAlmacen() {
        return editarTipoIngresoAlmacen;
    }

    public void setEditarTipoIngresoAlmacen(TiposIngresoAlmacen editarTipoIngresoAlmacen) {
        this.editarTipoIngresoAlmacen = editarTipoIngresoAlmacen;
    }

    public List<TiposIngresoAlmacen> getListaTiposIngresosAlmacen() {
        return listaTiposIngresosAlmacen;
    }

    public void setListaTiposIngresosAlmacen(List<TiposIngresoAlmacen> listaTiposIngresosAlmacen) {
        this.listaTiposIngresosAlmacen = listaTiposIngresosAlmacen;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public TiposIngresoAlmacen getNuevoTipoIngresoAlmacen() {
        return nuevoTipoIngresoAlmacen;
    }

    public void setNuevoTipoIngresoAlmacen(TiposIngresoAlmacen nuevoTipoIngresoAlmacen) {
        this.nuevoTipoIngresoAlmacen = nuevoTipoIngresoAlmacen;
    }

    public int getCantidadDependencias() {
        return cantidadDependencias;
    }

    public void setCantidadDependencias(int cantidadDependencias) {
        this.cantidadDependencias = cantidadDependencias;
    }



}
