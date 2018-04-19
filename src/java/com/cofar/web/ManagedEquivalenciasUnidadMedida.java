/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.web;

import com.cofar.bean.Equivalencias;
import com.cofar.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;
//import org.eclipse.jdt.internal.compiler.ast.ThisReference;


/**
 *
 * @author wchoquehuanca
 */

public class ManagedEquivalenciasUnidadMedida extends  ManagedBean{

    private List<Equivalencias> listaEquivalencias= new ArrayList<Equivalencias>();
    private String consulta="";
    private Statement st=null;
    private Connection con=null;
    private ResultSet result=null;
    private PreparedStatement pst=null;
    private Equivalencias nuevaEquivalencia= new  Equivalencias();
    private Equivalencias editarEquivalencia=new Equivalencias();
    private List<SelectItem> listaUnidades1= new ArrayList<SelectItem>();
    private List<SelectItem> listaUnidades2=new ArrayList<SelectItem>();
    private String mensaje="";
    /** Creates a new instance of ManagedEquivalenciasUnidadMedida */
    public ManagedEquivalenciasUnidadMedida() {
    }

    public String getCargarListaEquivalencias()
    {
        this.cargarEquivalencias();
        this.cantidadfilas=listaEquivalencias.size();
        
        return "";
    }
    public void cargarEquivalencias()
    {
         consulta="select * from (select ROW_NUMBER() OVER (ORDER BY um1.NOMBRE_UNIDAD_MEDIDA,um2.NOMBRE_UNIDAD_MEDIDA asc) as 'FILAS',"+
            "um1.NOMBRE_UNIDAD_MEDIDA as unidad1,e.COD_UNIDAD_MEDIDA,um2.NOMBRE_UNIDAD_MEDIDA as unidad2,e.COD_UNIDAD_MEDIDA2, "+
            "ISNULL((select er.NOMBRE_ESTADO_REGISTRO from ESTADOS_REFERENCIALES er where er.COD_ESTADO_REGISTRO=e.COD_ESTADO_REGISTRO),'') as nombreEstado,e.COD_ESTADO_REGISTRO, "+
            "e.VALOR_EQUIVALENCIA from EQUIVALENCIAS e inner join UNIDADES_MEDIDA um1 on e.COD_UNIDAD_MEDIDA=um1.COD_UNIDAD_MEDIDA "+
            "inner join UNIDADES_MEDIDA um2 on e.COD_UNIDAD_MEDIDA2=um2.COD_UNIDAD_MEDIDA "+
            ") AS listado where FILAS BETWEEN "+begin+" AND "+end;
         System.out.println("consulta "+consulta);
        try{
            con=Util.openConnection(con);
            st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            result=st.executeQuery(consulta);
            listaEquivalencias.clear();
            while(result.next())
            {
                Equivalencias nuevaEquivalencia=new Equivalencias();
                nuevaEquivalencia.getUnidadesMedida1().setNombreUnidadMedida(result.getString("unidad1"));
                nuevaEquivalencia.getUnidadesMedida1().setCodUnidadMedida(result.getInt("COD_UNIDAD_MEDIDA"));
                nuevaEquivalencia.getUnidadesMedida2().setNombreUnidadMedida(result.getString("unidad2"));
                nuevaEquivalencia.getUnidadesMedida2().setCodUnidadMedida(result.getInt("COD_UNIDAD_MEDIDA2"));
                nuevaEquivalencia.getEstadoReferencial().setNombreEstadoRegistro(result.getString("nombreEstado"));
                nuevaEquivalencia.getEstadoReferencial().setCodEstadoRegistro(result.getString("COD_ESTADO_REGISTRO"));
                nuevaEquivalencia.setValorEquivalencia(result.getFloat("VALOR_EQUIVALENCIA"));
                listaEquivalencias.add(nuevaEquivalencia);
            }
            System.out.println("cantidad"+listaEquivalencias.size());
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
        this.cargarEquivalencias();
        this.cantidadfilas = listaEquivalencias.size();
        return "";
    }
    public String siguiente_action() {
        super.next();
        this.cargarEquivalencias();
        this.cantidadfilas = listaEquivalencias.size();

        return "";
    }

    public String cargarUnidades1()
    {
       consulta="select u.COD_UNIDAD_MEDIDA,u.NOMBRE_UNIDAD_MEDIDA from UNIDADES_MEDIDA u order by u.NOMBRE_UNIDAD_MEDIDA";
       System.out.println("consulta "+consulta);
       try
       {
           con=Util.openConnection(con);
           st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
           result=st.executeQuery(consulta);

           getListaUnidades1().clear();
           while(result.next())
           {
               getListaUnidades1().add(new SelectItem(result.getInt("COD_UNIDAD_MEDIDA"),result.getString("NOMBRE_UNIDAD_MEDIDA")));

           }
           
           result.close();
           st.close();
           con.close();
           System.out.println("catnidad "+listaUnidades1.size());
 
       }
       catch(SQLException ex)
       {
           ex.printStackTrace();
       }
       return null;
    }
    public String nuevaEquivalencia_action()
    {
        nuevaEquivalencia.getUnidadesMedida1().setCodUnidadMedida(1);
       
        nuevaEquivalencia.setValorEquivalencia(0);
        nuevaEquivalencia.getEstadoReferencial().setCodEstadoRegistro("1");
        System.out.println("cantidad "+listaEquivalencias.size());
        this.cargarUnidades1();
        this.onchangeEquivalencia();
        return null;
    }
    public String editarEquivalencia_action() throws SQLException
    {
        mensaje ="";
        for(Equivalencias current:getListaEquivalencias())
        {
           if(current.getChecked())
            {
              
               editarEquivalencia=current;
               break;
            }
        }
        try {
            con = Util.openConnection(con);
            consulta="select TOP 1 m.COD_MATERIAL from MATERIALES m where m.COD_UNIDAD_MEDIDA " +
                     "in ('"+editarEquivalencia.getUnidadesMedida1().getCodUnidadMedida()+"'," +
                     "'"+editarEquivalencia.getUnidadesMedida2().getCodUnidadMedida()+"')";
            System.out.println("consulta "+consulta);
            st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            result=st.executeQuery(consulta);
            if(result.next())
            {
                mensaje="no se puede modificar la equivalencia porque un material utiliza la equivalencia";
               
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            con.close();
        }
            
        
        this.cargarUnidades1();
        this.onchangeEquivalencia2();
        return null;
    }
     public String onchangeEquivalencia()
     {
         cargarUnidades2(nuevaEquivalencia);
         return null;
     }
     public String onchangeEquivalencia2()
     {
         cargarUnidades2(editarEquivalencia);
         return null;
     }
     public String guardarEquivalencias_action()
     {
         consulta="select * from EQUIVALENCIAS e " +
                 "where  e.COD_UNIDAD_MEDIDA='"+nuevaEquivalencia.getUnidadesMedida1().getCodUnidadMedida()+"'" +
                 " and e.COD_UNIDAD_MEDIDA2='"+nuevaEquivalencia.getUnidadesMedida2().getCodUnidadMedida()+"'";
         mensaje="";
         System.out.println("constulta"+consulta);
         try
         {      boolean registrar=true;
             con=Util.openConnection(con);
             st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
             result=st.executeQuery(consulta);
             if(result.next())
             {
                 mensaje="no se puede registrar la equivalencia porque ya existe una exquivalencia entre las dos unidades";
                registrar=false;
             }
             else
             {
                 consulta="select * from EQUIVALENCIAS e " +
                 "where  e.COD_UNIDAD_MEDIDA='"+nuevaEquivalencia.getUnidadesMedida2().getCodUnidadMedida()+"'" +
                 " and e.COD_UNIDAD_MEDIDA2='"+nuevaEquivalencia.getUnidadesMedida1().getCodUnidadMedida()+"'";
                 System.out.println("consulta "+consulta);
                 result=st.executeQuery(consulta);
                 if(result.next())
                 {
                      mensaje="no se puede registrar la equivalencia porque ya existe una exquivalencia entre las dos unidades";
                      registrar=false;
                 }
             }
             if(registrar)
             {
             consulta="INSERT INTO EQUIVALENCIAS (COD_UNIDAD_MEDIDA,COD_UNIDAD_MEDIDA2,VALOR_EQUIVALENCIA,COD_ESTADO_REGISTRO ) "+
                "VALUES ('"+nuevaEquivalencia.getUnidadesMedida1().getCodUnidadMedida()+"','"+nuevaEquivalencia.getUnidadesMedida2().getCodUnidadMedida()+"',"+
                "'"+nuevaEquivalencia.getValorEquivalencia()+"','1')";
             System.out.println("consulta "+consulta);
              pst=con.prepareStatement(consulta);
              if(pst.executeUpdate()>0)System.out.println("se inserto la equivalencia");
              pst.close();
                this.cargarEquivalencias();
                 this.cantidadfilas=listaEquivalencias.size();
             }
             else
             {System.out.println("existia un registro anterior");}
             result.close();
             st.close();
             con.close();

         }
         catch(SQLException ex)
         {
             ex.printStackTrace();
         }
       
         return null;
     }

     public String editarEquivalencias_action()
     {
                consulta="UPDATE EQUIVALENCIAS SET VALOR_EQUIVALENCIA = '"+editarEquivalencia.getValorEquivalencia()+"',"+
                  "COD_ESTADO_REGISTRO = '"+editarEquivalencia.getEstadoReferencial().getCodEstadoRegistro()+"'"+
                   " WHERE COD_UNIDAD_MEDIDA = '"+editarEquivalencia.getUnidadesMedida1().getCodUnidadMedida()+"' and " +
                   "COD_UNIDAD_MEDIDA2 = '"+editarEquivalencia.getUnidadesMedida2().getCodUnidadMedida()+"'";
                System.out.println("consulta "+consulta);
         try
         {
            con=Util.openConnection(con);
                pst=con.prepareStatement(consulta);
                if(pst.executeUpdate()>0)System.out.println("se actualizo la equivalencia");
            pst.close();
            con.close();
            this.cargarEquivalencias();
            

         }
         catch(SQLException ex)
         {
             ex.printStackTrace();
         }

         return null;
     }
     public String eliminarEquivalencia_action()
     {
         setMensaje("");
         Equivalencias eq=new Equivalencias();
         for(Equivalencias curren:listaEquivalencias)
         {
             if(curren.getChecked())
             {
                 eq=curren;
             }
         }
         try
         {

             con=Util.openConnection(con);
             consulta="select TOP 1 m.COD_MATERIAL from MATERIALES m where m.COD_UNIDAD_MEDIDA " +
                     "in ('"+eq.getUnidadesMedida1().getCodUnidadMedida()+"'," +
                     "'"+eq.getUnidadesMedida2().getCodUnidadMedida()+"')";
             System.out.println("consulta "+consulta);
             st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
             result=st.executeQuery(consulta);
             if(result.next())
             {
                 setMensaje("no se puede eliminar la equivalencia porque un material utiliza la equivalencia");
                 result.close();
                 st.close();
                 con.close();
                 return null;
             }
             consulta="select top 1* from INGRESOS_ALMACEN_DETALLE iad where iad.COD_UNIDAD_MEDIDA " +
                     "IN('"+eq.getUnidadesMedida1().getCodUnidadMedida()+"','"+eq.getUnidadesMedida2().getCodUnidadMedida()+"')";
             System.out.println("consulta "+consulta);
             result=st.executeQuery(consulta);
             if(result.next())
             {
                  setMensaje("no se puede eliminar la equivalencia porque un ingreso a almacen lo utiliza");
                 result.close();
                 st.close();
                 con.close();
                 return null;
             }
             consulta="select top 1 * from SALIDAS_ALMACEN_DETALLE sad where sad.COD_UNIDAD_MEDIDA " +
                     "IN('"+eq.getUnidadesMedida1().getCodUnidadMedida()+"','"+eq.getUnidadesMedida2().getCodUnidadMedida()+"')";
             result=st.executeQuery(consulta);
             if(result.next())
             {
                 setMensaje("no se puede eliminar la equivalencia porque una salida almacen lo utiliza");
                 result.close();
                 st.close();
                 con.close();
                 return null;
             }
             
             consulta="DELETE FROM EQUIVALENCIAS WHERE " +
                     "COD_UNIDAD_MEDIDA='"+eq.getUnidadesMedida1().getCodUnidadMedida()+"' " +
                     "and COD_UNIDAD_MEDIDA2='"+eq.getUnidadesMedida2().getCodUnidadMedida()+"'";
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
         cargarEquivalencias();
         this.cantidadfilas=listaEquivalencias.size();
         return null;
     }
    public void cargarUnidades2(Equivalencias e)
    {
        consulta="select u.COD_UNIDAD_MEDIDA,u.NOMBRE_UNIDAD_MEDIDA from UNIDADES_MEDIDA u " +
                "where u.COD_TIPO_MEDIDA in " +
                "(select un.COD_TIPO_MEDIDA from UNIDADES_MEDIDA un where un.COD_UNIDAD_MEDIDA='"+e.getUnidadesMedida1().getCodUnidadMedida()+"') " +
                " and u.COD_UNIDAD_MEDIDA NOT IN('"+e.getUnidadesMedida1().getCodUnidadMedida()+"') order by u.NOMBRE_UNIDAD_MEDIDA";
        System.out.println("consulta "+consulta);
        try
        {
            con=Util.openConnection(con);
            st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            result=st.executeQuery(consulta);
            listaUnidades2.clear();
            while(result.next())
            {
                listaUnidades2.add(new SelectItem(result.getInt("COD_UNIDAD_MEDIDA"),result.getString("NOMBRE_UNIDAD_MEDIDA")));
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
    public List<Equivalencias> getListaEquivalencias() {
        return listaEquivalencias;
    }

    public void setListaEquivalencias(List<Equivalencias> listaEquivalencias) {
        this.listaEquivalencias = listaEquivalencias;
    }

    public Equivalencias getEditarEquivalencia() {
        return editarEquivalencia;
    }

    public void setEditarEquivalencia(Equivalencias editarEquivalencia) {
        this.editarEquivalencia = editarEquivalencia;
    }

    public Equivalencias getNuevaEquivalencia() {
        return nuevaEquivalencia;
    }

    public void setNuevaEquivalencia(Equivalencias nuevaEquivalencia) {
        this.nuevaEquivalencia = nuevaEquivalencia;
    }

    public List<SelectItem> getListaUnidades1() {
        return listaUnidades1;
    }

    public void setListaUnidades1(List<SelectItem> listaUnidades1) {
        this.listaUnidades1 = listaUnidades1;
    }

    public List<SelectItem> getListaUnidades2() {
        return listaUnidades2;
    }

    public void setListaUnidades2(List<SelectItem> listaUnidades2) {
        this.listaUnidades2 = listaUnidades2;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }




}
