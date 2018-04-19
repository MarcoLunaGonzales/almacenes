/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.web;

import com.cofar.bean.TiposMedida;
import com.cofar.bean.UnidadesMedida;
import com.cofar.util.Util;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;

/**
 *
 * @author wchoquehuanca
 */

public class ManagedUnidadesMedida extends ManagedBean{
    Connection con=null;
    Statement st=null;
    ResultSet result=null;
    String consulta="";
    private List<UnidadesMedida> listaUnidades=new ArrayList<UnidadesMedida>();
    private UnidadesMedida nuevaUnidad= new UnidadesMedida();
    private UnidadesMedida editarUnidad=new UnidadesMedida();
    private List<SelectItem> listaTiposUnidades= new ArrayList<SelectItem>();
    private String mensaje="";
    private List<SelectItem> opciones= new ArrayList<SelectItem>();
    private int cantidadDependencias = 0;


    /** Creates a new instance of ManagedUnidadesMedida */
    public ManagedUnidadesMedida() {

    }
    public String getCargarListaUnidades()
    {
        this.cargarListaUnidades();
        this.cargarlistaTiposUnidades();
        opciones.clear();
        opciones.add(new SelectItem(0,"no"));
        opciones.add(new SelectItem(1, "si"));
        cantidadfilas=listaUnidades.size();
        return "";
    }
    private void cargarListaUnidades()
    {
      consulta="select * from (select ROW_NUMBER() OVER (ORDER BY tm.NOMBRE_TIPO_MEDIDA,um.NOMBRE_UNIDAD_MEDIDA asc) as 'FILAS', "+
                "um.ABREVIATURA,ISNULL((select e.NOMBRE_ESTADO_REGISTRO from ESTADOS_REFERENCIALES e where e.COD_ESTADO_REGISTRO=um.COD_ESTADO_REGISTRO),'') as nombreEstado, "+
                "tm.COD_TIPO_MEDIDA,um.COD_ESTADO_REGISTRO,tm.NOMBRE_TIPO_MEDIDA,um.COD_UNIDAD_MEDIDA,um.NOMBRE_UNIDAD_MEDIDA,um.OBS_UNIDAD_MEDIDA,um.UNIDAD_CLAVE "+
                "from UNIDADES_MEDIDA um inner join TIPOS_MEDIDA tm on um.COD_TIPO_MEDIDA=tm.COD_TIPO_MEDIDA where tm.COD_ESTADO_REGISTRO=1"+
                " ) AS listado where FILAS BETWEEN '"+begin+"' AND "+end;
      System.out.println("consulta "+consulta);
      try
      {
        con=Util.openConnection(con);
        st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        result=st.executeQuery(consulta);
        listaUnidades.clear();
        while(result.next())
        {
         UnidadesMedida insertar=new UnidadesMedida();
         insertar.setAbreviatura(result.getString("ABREVIATURA"));
         insertar.setCodUnidadMedida(result.getInt("COD_UNIDAD_MEDIDA"));
         insertar.getEstadoReferencial().setNombreEstadoRegistro(result.getString("nombreEstado"));
         insertar.getEstadoReferencial().setCodEstadoRegistro(result.getString("COD_ESTADO_REGISTRO"));
         insertar.setNombreUnidadMedida(result.getString("NOMBRE_UNIDAD_MEDIDA"));
         insertar.setObsUnidadMedida(result.getString("OBS_UNIDAD_MEDIDA"));
         insertar.getTiposMedida().setNombre_tipo_medida(result.getString("NOMBRE_TIPO_MEDIDA"));
         insertar.getTiposMedida().setCod_tipo_medida(result.getString("COD_TIPO_MEDIDA"));
         insertar.setUnidadClave(result.getInt("UNIDAD_CLAVE"));
         listaUnidades.add(insertar);
        }
        result.close();
        st.close();
        con.close();

      }
      catch(SQLException ex)
      {
          ex.printStackTrace();
      }


    }

      public String atras_action() {
        super.back();
        this.cargarListaUnidades();
        this.cantidadfilas = listaUnidades.size();
        return "";
    }
    public String siguiente_action() {
        super.next();
        this.cargarListaUnidades();
        this.cantidadfilas = listaUnidades.size();

        return "";
    }
    public String nuevaUnidad_action()
    {
        nuevaUnidad=new UnidadesMedida();
        nuevaUnidad.getEstadoReferencial().setCodEstadoRegistro("1");
        return null;
    }
    private void cargarlistaTiposUnidades()
    {

        consulta="select tm.COD_TIPO_MEDIDA,tm.NOMBRE_TIPO_MEDIDA from TIPOS_MEDIDA tm where tm.COD_ESTADO_REGISTRO=1 order by tm.NOMBRE_TIPO_MEDIDA";
        try
        {
            con=Util.openConnection(con);
           st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
           result=st.executeQuery(consulta);
           listaTiposUnidades.clear();
           while(result.next())
           {
               listaTiposUnidades.add(new SelectItem(result.getString("COD_TIPO_MEDIDA"),result.getString("NOMBRE_TIPO_MEDIDA")));
           }
           result.close();
           st.close();
           con.close();
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
    }


     public String eliminarUnidad_action()
     {
         setMensaje("");
         PreparedStatement pst=null;
         UnidadesMedida unidad=new UnidadesMedida();
         for(UnidadesMedida curren:listaUnidades)
         {
             if(curren.getChecked())
             {
                 unidad=curren;
                 break;
             }
         }
         if(unidad.getUnidadClave()==1)
            {
             System.out.println("es una unidad clave");
                setMensaje("No se puede eliminar la unidad porque es una unidad clave");
                return null;
            }
         try
         {

             con=Util.openConnection(con);
             consulta="select TOP 1 m.COD_MATERIAL from MATERIALES m where m.COD_UNIDAD_MEDIDA " +
                     "in ('"+unidad.getCodUnidadMedida()+"')";
             System.out.println("consulta "+consulta);
             st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
             result=st.executeQuery(consulta);
             if(result.next())
             {
                 setMensaje("no se puede eliminar la unidad porque un material utiliza la unidad de medida");
                 result.close();
                 st.close();
                 con.close();
                 return null;
             }
             consulta="select top 1* from INGRESOS_ALMACEN_DETALLE iad where iad.COD_UNIDAD_MEDIDA " +
                     "IN('"+unidad.getCodUnidadMedida()+"')";
             System.out.println("consulta "+consulta);
             result=st.executeQuery(consulta);
             if(result.next())
             {
                  setMensaje("no se puede eliminar la unidad porque un ingreso a almacen lo utiliza");
                 result.close();
                 st.close();
                 con.close();
                 return null;
             }
             consulta="select top 1 * from SALIDAS_ALMACEN_DETALLE sad where sad.COD_UNIDAD_MEDIDA " +
                     "IN('"+unidad.getCodUnidadMedida()+"')";
              System.out.println("consulta "+consulta);
             result=st.executeQuery(consulta);
             if(result.next())
             {
                 setMensaje("no se puede eliminar la unidad porque una salida almacen lo utiliza");
                 result.close();
                 st.close();
                 con.close();
                 return null;
             }

             consulta="DELETE FROM UNIDADES_MEDIDA WHERE COD_UNIDAD_MEDIDA ='"+unidad.getCodUnidadMedida()+"'";
             System.out.println("consulta "+consulta);
             pst=con.prepareStatement(consulta);
             if(pst.executeUpdate()>0)System.out.println("se elimino la equivalencia ");
             pst.close();
             con.close();
         }

         catch(SQLException ex)
         {
             ex.printStackTrace();
         }
         this.cargarListaUnidades();
         this.cantidadfilas=listaUnidades.size();
         return null;
     }

    public String registrarUnidad_action()
    {   mensaje="";
        consulta="select ISNULL(MAX(u.COD_UNIDAD_MEDIDA),0)+1 as codigo from UNIDADES_MEDIDA u";
        System.out.println("consulta");
        try{
            con=Util.openConnection(con);
            st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            result=st.executeQuery(consulta);
            String codUnidad="";
            if(result.next())
            {
                codUnidad=result.getString("codigo");
            }
            consulta="select TOP 1 um.COD_UNIDAD_MEDIDA from UNIDADES_MEDIDA um " +
                    " where um.NOMBRE_UNIDAD_MEDIDA ='"+nuevaUnidad.getNombreUnidadMedida()+"'";
            System.out.println("consulta "+consulta);
            result=st.executeQuery(consulta);
            if(result.next())
            {
                mensaje="No se puede registrar la unidad porque el nombre ya esta registrado";
                result.close();
                st.close();
                con.close();
                return null;
            }
            result.close();
            st.close();
            consulta="INSERT INTO UNIDADES_MEDIDA(COD_UNIDAD_MEDIDA, COD_TIPO_MEDIDA,"+
                  "NOMBRE_UNIDAD_MEDIDA, OBS_UNIDAD_MEDIDA, COD_ESTADO_REGISTRO, ABREVIATURA,UNIDAD_CLAVE)"+
                  "VALUES ('"+codUnidad+"','"+nuevaUnidad.getTiposMedida().getCod_tipo_medida()+"','"+nuevaUnidad.getNombreUnidadMedida()+"',"+
                  "'"+nuevaUnidad.getObsUnidadMedida()+"','1','"+nuevaUnidad.getAbreviatura()+"','"+ nuevaUnidad.getUnidadClave()+"')";
          System.out.println("consulta "+consulta);
          PreparedStatement pst=con.prepareStatement(consulta);
          if(pst.executeUpdate()>0)System.out.println("se inserto la unidad");
          pst.close();
          con.close();
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        this.cargarListaUnidades();
        cantidadfilas=listaUnidades.size();
        return null;
    }
    public String cargarUnidadEditar() throws SQLException
    {
        Connection con = null;
        try {
            con = Util.openConnection(con);
            for(UnidadesMedida current:listaUnidades)
            {
                if(current.getChecked())
                {
                    editarUnidad=current;
                    break;
                }
            }
            StringBuilder consulta = new StringBuilder( " select COUNT(*) as CANTIDAD_REGISTRO")
                    .append(" from MATERIALES m ")
                    .append(" where m.COD_UNIDAD_MEDIDA = '").append(editarUnidad.getCodUnidadMedida()).append("' ");
            System.out.println("consulta: " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta.toString());
            while(rs.next()){
                cantidadDependencias =  rs.getInt("CANTIDAD_REGISTRO");
            }
            if (cantidadDependencias != 0) {
                mensaje = "Esta Unidad de Medida está asociada a uno o más Materiales; por lo tanto, no puede ser modificada.";
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
    public String modificarUnidad_action()
    {
        mensaje="";
        consulta="select TOP 1 um.COD_UNIDAD_MEDIDA from UNIDADES_MEDIDA um  " +
                " where um.NOMBRE_UNIDAD_MEDIDA ='"+editarUnidad.getNombreUnidadMedida()+"'" +
                "and um.COD_UNIDAD_MEDIDA not in('"+editarUnidad.getCodUnidadMedida()+"')";
        
        System.out.println("consulta "+consulta);
        try
        {
            con=Util.openConnection(con);
            st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            result=st.executeQuery(consulta);
            if(result.next())
            {
                mensaje="No se puede modificar la unidad porque el nuevo nombre ya esta registrado";
                 result.close();
                 st.close();
                 con.close();
                 return null;
             
            }
            consulta="UPDATE UNIDADES_MEDIDA SET NOMBRE_UNIDAD_MEDIDA = '"+editarUnidad.getNombreUnidadMedida()+"',"+
                  " OBS_UNIDAD_MEDIDA = '"+editarUnidad.getObsUnidadMedida()+"',"+
                  " COD_ESTADO_REGISTRO = '"+editarUnidad.getEstadoReferencial().getCodEstadoRegistro()+"',"+
                  " ABREVIATURA = '"+editarUnidad.getAbreviatura()+"',"+
                   " UNIDAD_CLAVE = '"+editarUnidad.getUnidadClave()+"'"+
                  " WHERE COD_UNIDAD_MEDIDA = '"+editarUnidad.getCodUnidadMedida()+"'";
            System.out.println("consulta "+consulta);
            PreparedStatement pst=con.prepareStatement(consulta);
            if(pst.executeUpdate()>0)System.out.println("se actualizo la unidad");
            pst.close();
            con.close();
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        this.cargarListaUnidades();
        cantidadfilas=listaUnidades.size();
        return null;
    }
    public UnidadesMedida getEditarUnidad() {
        return editarUnidad;
    }

    public void setEditarUnidad(UnidadesMedida editarUnidad) {
        this.editarUnidad = editarUnidad;
    }

    public List<SelectItem> getListaTiposUnidades() {
        return listaTiposUnidades;
    }

    public void setListaTiposUnidades(List<SelectItem> listaTiposUnidades) {
        this.listaTiposUnidades = listaTiposUnidades;
    }

    public List<UnidadesMedida> getListaUnidades() {
        return listaUnidades;
    }

    public void setListaUnidades(List<UnidadesMedida> listaUnidades) {
        this.listaUnidades = listaUnidades;
    }

    public UnidadesMedida getNuevaUnidad() {
        return nuevaUnidad;
    }

    public void setNuevaUnidad(UnidadesMedida nuevaUnidad) {
        this.nuevaUnidad = nuevaUnidad;
    }

    public List<SelectItem> getOpciones() {
        return opciones;
    }

    public void setOpciones(List<SelectItem> opciones) {
        this.opciones = opciones;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public int getCantidadDependencias() {
        return cantidadDependencias;
    }

    public void setCantidadDependencias(int cantidadDependencias) {
        this.cantidadDependencias = cantidadDependencias;
    }

}
