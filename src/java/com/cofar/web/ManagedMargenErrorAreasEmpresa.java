/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.web;


import com.cofar.bean.ToleranciaAreasEmpresa;
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
 * @author aquispe
 */

public class ManagedMargenErrorAreasEmpresa extends ManagedBean {

    private List<ToleranciaAreasEmpresa> toleranciaAreasEmpresaList= new ArrayList<ToleranciaAreasEmpresa>();
    private List<SelectItem> areasEmpresaList= new ArrayList<SelectItem>();
    private Connection con=null;
    private Statement st=null;
    private ResultSet res=null;
    private ToleranciaAreasEmpresa toleranciaAreasEmpresaEditar= new ToleranciaAreasEmpresa();
    private ToleranciaAreasEmpresa toleranciaAreasEmpresaNuevo= new ToleranciaAreasEmpresa();
    /** Creates a new instance of ManagedMargenErrorAreasEmpresa */
    public ManagedMargenErrorAreasEmpresa() {
    }
    public String getCargarToleranciaAreasEmpresa()
    {
        this.cargarAreasEmpresaNoRegistradas();
        this.cargarToleranciaAreasEmpresa();
        return null;
    }
    private void cargarAreasEmpresaNoRegistradas()
    {
        try
        {
            con=Util.openConnection(con);
            st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            String consulta="select ae.COD_AREA_EMPRESA,ae.NOMBRE_AREA_EMPRESA"+
                            " from AREAS_EMPRESA ae where ae.COD_AREA_EMPRESA in ("+
                            " select cp.COD_AREA_EMPRESA from COMPONENTES_PROD cp) and ae.COD_AREA_EMPRESA not in ("+
                            " select tae.COD_AREA_EMPRESA from TOLERANCIA_AREAS_EMPRESA tae)";
            System.out.println("consulta cargarAreasEmpresaNoRegistradas: "+consulta);
            res=st.executeQuery(consulta);
            areasEmpresaList.clear();
            while(res.next())
            {
                areasEmpresaList.add(new SelectItem(res.getString("COD_AREA_EMPRESA"),res.getString("NOMBRE_AREA_EMPRESA")));
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
    private void cargarToleranciaAreasEmpresa()
    {
        try
        {
            con=Util.openConnection(con);
            st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            String consulta="select ae.NOMBRE_AREA_EMPRESA,ae.COD_AREA_EMPRESA,tae.PORCIENTO_TOLERANCIA"+
                            " from TOLERANCIA_AREAS_EMPRESA tae inner join AREAS_EMPRESA ae "+
                            " on ae.COD_AREA_EMPRESA=tae.COD_AREA_EMPRESA order by ae.NOMBRE_AREA_EMPRESA";
            System.out.println("consulta cargarToleranciaAreasEmpresa: "+consulta);
            res=st.executeQuery(consulta);
            toleranciaAreasEmpresaList.clear();
            while(res.next())
            {
                ToleranciaAreasEmpresa bean= new ToleranciaAreasEmpresa();
                bean.getAreasEmpresa().setCodAreaEmpresa(res.getString("COD_AREA_EMPRESA"));
                bean.getAreasEmpresa().setNombreAreaEmpresa(res.getString("NOMBRE_AREA_EMPRESA"));
                bean.setTolerancia(res.getDouble("PORCIENTO_TOLERANCIA")*100d);
                toleranciaAreasEmpresaList.add(bean);
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
    public String guardarMargenError_Action()
    {
        try
        {
          con=Util.openConnection(con);
          String consulta="INSERT INTO TOLERANCIA_AREAS_EMPRESA(COD_AREA_EMPRESA, PORCIENTO_TOLERANCIA)"+
                           " VALUES ('"+toleranciaAreasEmpresaNuevo.getAreasEmpresa().getCodAreaEmpresa()+"','"+(toleranciaAreasEmpresaNuevo.getTolerancia()/100d)+"');";
          System.out.println("consulta guardar "+consulta);
          PreparedStatement pst=con.prepareStatement(consulta);
          if(pst.executeUpdate()>0)System.out.println("se inserto satifactoriamente");
          pst.close();
          con.close();
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        this.cargarToleranciaAreasEmpresa();
        this.cargarAreasEmpresaNoRegistradas();
        return null;
    }
    public String editarMargenError_Action()
    {
        for(ToleranciaAreasEmpresa bean:toleranciaAreasEmpresaList)
        {
            if(bean.getChecked())
            {
                toleranciaAreasEmpresaEditar=bean;
                return null;
            }
        }
        return null;
    }
    public String guardarEditarMargenError_Action()
    {
        try
        {
            con=Util.openConnection(con);
            String consulta="UPDATE TOLERANCIA_AREAS_EMPRESA"+
                            " SET PORCIENTO_TOLERANCIA = '"+(toleranciaAreasEmpresaEditar.getTolerancia()/100d)+"'"+
                            " WHERE COD_AREA_EMPRESA ='"+toleranciaAreasEmpresaEditar.getAreasEmpresa().getCodAreaEmpresa()+"'";
            System.out.println("consulta guardar edicion "+consulta);
            PreparedStatement pst=con.prepareStatement(consulta);
            if(pst.executeUpdate()>0)System.out.println("Se edito correctamente");
            pst.close();
            con.close();
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        this.cargarToleranciaAreasEmpresa();
        return null;
    }
    public String eliminarMargenErrorAreasEmpresa_Action()
    {
        for(ToleranciaAreasEmpresa current:toleranciaAreasEmpresaList)
        {
            if(current.getChecked())
            {
                try{

                    con=Util.openConnection(con);
                    String consulta="DELETE FROM TOLERANCIA_AREAS_EMPRESA "+
                                    " WHERE COD_AREA_EMPRESA = '"+current.getAreasEmpresa().getCodAreaEmpresa()+"'";
                    System.out.println("consulta eliminar "+consulta);
                    PreparedStatement pst=con.prepareStatement(consulta);

                    if(pst.executeUpdate()>0)System.out.println("se elimino el registro de margen de error areas empresa");
                    pst.close();
                    con.close();
                }
                catch(SQLException ex)
                {
                    ex.printStackTrace();
                }
            }
        }
        this.cargarAreasEmpresaNoRegistradas();
        this.cargarToleranciaAreasEmpresa();
        return null;

    }

    public List<SelectItem> getAreasEmpresaList() {
        return areasEmpresaList;
    }

    public void setAreasEmpresaList(List<SelectItem> areasEmpresaList) {
        this.areasEmpresaList = areasEmpresaList;
    }

    public ToleranciaAreasEmpresa getToleranciaAreasEmpresaEditar() {
        return toleranciaAreasEmpresaEditar;
    }

    public void setToleranciaAreasEmpresaEditar(ToleranciaAreasEmpresa toleranciaAreasEmpresaEditar) {
        this.toleranciaAreasEmpresaEditar = toleranciaAreasEmpresaEditar;
    }

    public List<ToleranciaAreasEmpresa> getToleranciaAreasEmpresaList() {
        return toleranciaAreasEmpresaList;
    }

    public void setToleranciaAreasEmpresaList(List<ToleranciaAreasEmpresa> toleranciaAreasEmpresaList) {
        this.toleranciaAreasEmpresaList = toleranciaAreasEmpresaList;
    }

    public ToleranciaAreasEmpresa getToleranciaAreasEmpresaNuevo() {
        return toleranciaAreasEmpresaNuevo;
    }

    public void setToleranciaAreasEmpresaNuevo(ToleranciaAreasEmpresa toleranciaAreasEmpresaNuevo) {
        this.toleranciaAreasEmpresaNuevo = toleranciaAreasEmpresaNuevo;
    }

   

}
