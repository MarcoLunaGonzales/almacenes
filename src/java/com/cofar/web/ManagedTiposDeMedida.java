/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.web;


import com.cofar.bean.TiposMedida;
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

public class ManagedTiposDeMedida extends ManagedBean{

    private List<TiposMedida> listaTiposMedida= new ArrayList<TiposMedida>();
    private TiposMedida nuevoTipoMedida= new TiposMedida();
    private TiposMedida editarTipoMedida= new TiposMedida();
    private Connection con=null;
    private Statement st=null;
    private PreparedStatement pst=null;
    private String consulta="";
    private String codEstadoRegistro="";
    private String mensaje="";
    /** Creates a new instance of ManagedTiposDeMedida */
    public ManagedTiposDeMedida() {
    }
    public String getInitListaTiposMedida()
    {
        this.codEstadoRegistro="3";
        this.cargarListaTiposMedida();
        return null;
    }
    private void cargarListaTiposMedida()
    {
        try
        {
            con=Util.openConnection(con);
            st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            consulta="select tm.COD_TIPO_MEDIDA,tm.NOMBRE_TIPO_MEDIDA,tm.OBS_TIPO_MEDIDA,"+
                     " er.COD_ESTADO_REGISTRO,er.NOMBRE_ESTADO_REGISTRO"+
                     " from TIPOS_MEDIDA tm left outer join ESTADOS_REFERENCIALES er"+
                     " on tm.COD_ESTADO_REGISTRO=er.COD_ESTADO_REGISTRO";
                    if(!codEstadoRegistro.equals("3"))
                    {
                     consulta+=" where tm.COD_ESTADO_REGISTRO='"+codEstadoRegistro+"'";
                    }
                    consulta+=" order by tm.NOMBRE_TIPO_MEDIDA asc";
            ResultSet res=st.executeQuery(consulta);
            listaTiposMedida.clear();

            while(res.next())
            {
                TiposMedida nuevo= new TiposMedida();
                nuevo.setCod_tipo_medida(res.getString("COD_TIPO_MEDIDA"));
                nuevo.setNombre_tipo_medida(res.getString("NOMBRE_TIPO_MEDIDA"));
                nuevo.setObs_tipo_medida(res.getString("OBS_TIPO_MEDIDA"));
                nuevo.getEstadoReferencial().setCodEstadoRegistro(res.getString("COD_ESTADO_REGISTRO"));
                nuevo.getEstadoReferencial().setNombreEstadoRegistro(res.getString("NOMBRE_ESTADO_REGISTRO"));
                listaTiposMedida.add(nuevo);
            }
            st.close();
            con.close();

        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
    }
    public String nuevoTipoMedida_Action()
    {
        nuevoTipoMedida= new TiposMedida();
        return null;
    }
    public String editarTipoMedida_Action()
    {
        for(TiposMedida current:listaTiposMedida)
        {
            if(current.getChecked())
            {
                editarTipoMedida=current;
            }
        }
       return null;
    }
    public String guardarNuevoTipoMedida_Action()
    {
        try
        {
            con=Util.openConnection(con);
            st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
           consulta="select ISNULL(MAX(tm.COD_TIPO_MEDIDA),0)+1 as cod from TIPOS_MEDIDA tm";
            ResultSet res=st.executeQuery(consulta);
            int cod=0;
            if(res.next())
            {
                cod=res.getInt("cod");
            }
            res.close();
            st.close();
            consulta="INSERT INTO TIPOS_MEDIDA(COD_TIPO_MEDIDA, NOMBRE_TIPO_MEDIDA, OBS_TIPO_MEDIDA,COD_ESTADO_REGISTRO)"+
                     "VALUES ('"+cod+"','"+nuevoTipoMedida.getNombre_tipo_medida()+"','"+nuevoTipoMedida.getObs_tipo_medida()+"','"+nuevoTipoMedida.getEstadoReferencial().getCodEstadoRegistro()+"')";
            System.out.println("consulta "+consulta);
            pst=con.prepareStatement(consulta);
            if(pst.executeUpdate()>0)System.out.println("se inserto el tipo de unidad de medida");
            pst.close();

            con.close();
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        this.cargarListaTiposMedida();
        return null;
    }
    private boolean registroUsado(int codTipoMedida,String operacion)
    {
        boolean usado=false;
        try
        {
            con=Util.openConnection(con);
            st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            consulta="select top 1 * from UNIDADES_MEDIDA um where um.COD_TIPO_MEDIDA='"+codTipoMedida+"'";
            ResultSet res=st.executeQuery(consulta);
            if(res.next())
            {
                usado=true;
                setMensaje("no se puede "+operacion+" el tipo de medida porque una unidad de medida lo esta utilizando");
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
    public String guardarEditarTipoMedida_Action()
    {
        setMensaje("");
        boolean register=true;
        if(editarTipoMedida.getEstadoReferencial().getCodEstadoRegistro().equals("2"))
        {
            register=!this.registroUsado(Integer.valueOf(editarTipoMedida.getCod_tipo_medida()),"editar");

        }
        if(register)
        {
            try
            {
                con=Util.openConnection(con);
                consulta="UPDATE TIPOS_MEDIDA SET NOMBRE_TIPO_MEDIDA = '"+editarTipoMedida.getNombre_tipo_medida()+"',"+
                         " OBS_TIPO_MEDIDA = '"+editarTipoMedida.getObs_tipo_medida()+"',"+
                         " COD_ESTADO_REGISTRO = '"+editarTipoMedida.getEstadoReferencial().getCodEstadoRegistro()+"'"+
                         " WHERE COD_TIPO_MEDIDA = '"+editarTipoMedida.getCod_tipo_medida()+"'";
                System.out.println("consulta "+consulta);
                pst=con.prepareStatement(consulta);
                if(pst.executeUpdate()>0)System.out.println("se edito el tipo de medida");
                pst.close();
                con.close();

            }
            catch(SQLException ex)
            {
                ex.printStackTrace();
            }
        }
        this.cargarListaTiposMedida();
        return null;
    }
    public String onChange_EstadoRegistro()
    {
        this.cargarListaTiposMedida();
        return null;
    }
    public String eliminarTipoMedida_Action()
    {
        setMensaje("");
        for(TiposMedida current:listaTiposMedida)
        {
            if(current.getChecked())
            {
                if(!this.registroUsado(Integer.valueOf(current.getCod_tipo_medida()),"eliminar"))
                {
                    try
                    {
                        con=Util.openConnection(con);
                        consulta="DELETE FROM TIPOS_MEDIDA WHERE COD_TIPO_MEDIDA = '"+current.getCod_tipo_medida()+"'";
                        System.out.println("consulta "+consulta);
                        pst=con.prepareStatement(consulta);
                        if(pst.executeUpdate()>0)System.out.println("se elimino el tipo de medida");
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
        this.cargarListaTiposMedida();
        return null;
    }

    public String getCodEstadoRegistro() {
        return codEstadoRegistro;
    }

    public void setCodEstadoRegistro(String codEstadoRegistro) {
        this.codEstadoRegistro = codEstadoRegistro;
    }

    public List<TiposMedida> getListaTiposMedida() {
        return listaTiposMedida;
    }

    public void setListaTiposMedida(List<TiposMedida> listaTiposMedida) {
        this.listaTiposMedida = listaTiposMedida;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public TiposMedida getNuevoTipoMedida() {
        return nuevoTipoMedida;
    }

    public void setNuevoTipoMedida(TiposMedida nuevoTipoMedida) {
        this.nuevoTipoMedida = nuevoTipoMedida;
    }

    public TiposMedida getEditarTipoMedida() {
        return editarTipoMedida;
    }

    public void setEditarTipoMedida(TiposMedida editarTipoMedida) {
        this.editarTipoMedida = editarTipoMedida;
    }


}
