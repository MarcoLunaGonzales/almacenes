/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.web;

import com.cofar.bean.EstadosTransaccionesAlmacen;
import com.cofar.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.model.SelectItem;

/**
 *
 * @author aquispe
 */

public class ManagedEstadosTransaccionesAlmacen extends ManagedBean{

    private List<EstadosTransaccionesAlmacen> estadosTransaccionesAlmacenList= new ArrayList<EstadosTransaccionesAlmacen>();
    private Connection con =null;
    private String mensaje="";
    private List<SelectItem> mesesList= new ArrayList<SelectItem>();
    private List<SelectItem> gestionesList= new ArrayList<SelectItem>();
    private EstadosTransaccionesAlmacen estadosTransaccionesAlmacenACerrar= new EstadosTransaccionesAlmacen();
    /** Creates a new instance of ManagedEstadosTransaccionesAlmacen */
    public ManagedEstadosTransaccionesAlmacen() {
    }
    private void cargarMeses()
    {
        try
        {
            con=Util.openConnection(con);
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            String consulta="select m.COD_MES,m.NOMBRE_MES from meses m where m.COD_MES not in (13,14) order by m.COD_MES";
            ResultSet res=st.executeQuery(consulta);
            mesesList.clear();
            while(res.next())
            {
                mesesList.add(new SelectItem(res.getInt("COD_MES"),res.getString("NOMBRE_MES")));
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
    private void cargarGestiones()
    {
        try
        {
            con=Util.openConnection(con);
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            String consulta="select g.COD_GESTION,g.NOMBRE_GESTION from gestiones g order by g.COD_GESTION";
            ResultSet res=st.executeQuery(consulta);
            gestionesList.clear();
            while(res.next())
            {
                gestionesList.add(new SelectItem(res.getString("COD_GESTION"),res.getString("NOMBRE_GESTION")));
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
    public String getCargarEstadosTransaccionesAlmacen()
    {
        begin=0;
        end=10;
        this.cargarGestiones();
        this.cargarMeses();
        this.cargarEstadosTransaccionesAlmacen();
        cantidadfilas=estadosTransaccionesAlmacenList.size();
        return null;
    }
    private void cargarEstadosTransaccionesAlmacen()
    {
        try
        {
            String consulta="select * from (select ROW_NUMBER() OVER (ORDER BY g.COD_GESTION desc,m.ORDEN_MES desc) as 'FILAS'," +
                            " g.NOMBRE_GESTION,g.COD_GESTION,m.NOMBRE_MES,m.COD_MES,"+
                            " ISNULL(eta.COD_PERSONAL,0) AS COD_PERSONAL,ISNULL((p.AP_PATERNO_PERSONAL+' '+p.AP_MATERNO_PERSONAL+' '+p.NOMBRE_PILA),'') as nombrePersonal,"+
                            " eta.FECHA_CIERRE, et.COD_ESTADO_REGISTRO,et.NOMBRE_ESTADO_REGISTRO"+
                            " from ESTADOS_TRANSACCIONES_ALMACEN eta inner join meses m on m.COD_MES=eta.COD_MES"+
                            " inner join GESTIONES g on g.COD_GESTION=eta.COD_GESTION "+
                            " left outer join PERSONAL p on p.COD_PERSONAL=eta.COD_PERSONAL"+
                            " inner join ESTADOS_TRANSACCIONES et on et.COD_ESTADO_REGISTRO =eta.ESTADO "+
                            ") AS listado where FILAS BETWEEN "+begin+" AND "+end;
            System.out.println("consulta cargar "+consulta);
            con=Util.openConnection(con);
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet res=st.executeQuery(consulta);
            estadosTransaccionesAlmacenList.clear();
            while(res.next())
            {
                EstadosTransaccionesAlmacen bean= new EstadosTransaccionesAlmacen();
                bean.getGestion().setCodGestion(res.getString("COD_GESTION"));
                bean.getGestion().setNombreGestion(res.getString("NOMBRE_GESTION"));
                bean.getMes().setCodMes(res.getInt("COD_MES"));
                bean.getMes().setNombreMes(res.getString("NOMBRE_MES"));
                bean.getPersonal().setCodPersonal(res.getString("COD_PERSONAL"));
                bean.getPersonal().setNombrePersonal(res.getString("nombrePersonal"));
                bean.setFechaCierre(res.getTimestamp("FECHA_CIERRE"));
                bean.getEstado().setCodEstadoRegistro(res.getString("COD_ESTADO_REGISTRO"));
                bean.getEstado().setNombreEstadoRegistro(res.getString("NOMBRE_ESTADO_REGISTRO"));
                estadosTransaccionesAlmacenList.add(bean);
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
    public String atras_action()
    {
        end=begin;
        begin-=10;
        this.cargarEstadosTransaccionesAlmacen();
        cantidadfilas=estadosTransaccionesAlmacenList.size();
        return null;
    }
    public String siguiente_action()
    {
        begin=end;
        end+=10;
        this.cargarEstadosTransaccionesAlmacen();
        cantidadfilas=estadosTransaccionesAlmacenList.size();
        return null;
    }
    public String habilitarTransaccion_ActionJS()
    {
        this.cambiarEstadoTransaccionAlmacen("1",estadosTransaccionesAlmacenACerrar.getMes().getCodMes(),estadosTransaccionesAlmacenACerrar.getGestion().getCodGestion());
        begin=0;
        end=10;
        this.cargarEstadosTransaccionesAlmacen();
        cantidadfilas=estadosTransaccionesAlmacenList.size();
        return null;
    }
    public String habilitarEstadoTransaccionAlmacen_Action()
    {
        EstadosTransaccionesAlmacen current= new EstadosTransaccionesAlmacen();
        for(EstadosTransaccionesAlmacen bean:estadosTransaccionesAlmacenList)
        {
            if(bean.getChecked())
            {
                current=bean;
            }
        }
        this.cambiarEstadoTransaccionAlmacen("1",current.getMes().getCodMes(),current.getGestion().getCodGestion());
       
        this.cargarEstadosTransaccionesAlmacen();
        
        return null;
    }
    public String inhabilitarEstadoTransaccionAlmacen_Action()
    {
        EstadosTransaccionesAlmacen current= new EstadosTransaccionesAlmacen();
        for(EstadosTransaccionesAlmacen bean:estadosTransaccionesAlmacenList)
        {
            if(bean.getChecked())
            {
                current=bean;
            }
        }
        this.cambiarEstadoTransaccionAlmacen("2",current.getMes().getCodMes(),current.getGestion().getCodGestion());
       
        this.cargarEstadosTransaccionesAlmacen();
        
        return null;
    }
    private void cambiarEstadoTransaccionAlmacen(String codEstado,int codMes,String codGestion)
    {
        try
        {
            con=Util.openConnection(con);
            SimpleDateFormat sdf= new SimpleDateFormat("yyyy/MM/dd HH:mm");
            ManagedAccesoSistema user=(ManagedAccesoSistema)Util.getSessionBean("ManagedAccesoSistema");
            String consulta="UPDATE ESTADOS_TRANSACCIONES_ALMACEN SET "+
                            " ESTADO = '"+codEstado+"'"+
                            (codEstado.equals("1")?"  ,FECHA_CIERRE ='"+sdf.format(new Date())+"'":"")+
                            (codEstado.equals("1")?"  ,COD_PERSONAL ='"+user.getUsuarioModuloBean().getCodUsuarioGlobal()+"'":"")+
                            " WHERE COD_GESTION = '"+codGestion+"'"+
                            " and COD_MES = '"+codMes+"'";
            System.out.println("consulta update "+consulta);
            PreparedStatement pst=con.prepareStatement(consulta);
            if(pst.executeUpdate()>0)System.out.println("se ejecuto la actualizacion");
            pst.close();
            con.close();
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
    }
    public String cerrarTransacciones_Action()
    {   mensaje="";
        try
        {
            con=Util.openConnection(con);
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            String consulta="select top 1 eta.ESTADO from ESTADOS_TRANSACCIONES_ALMACEN eta" +
                            " where eta.COD_GESTION='"+estadosTransaccionesAlmacenACerrar.getGestion().getCodGestion()+"'" +
                            " and eta.COD_MES='"+estadosTransaccionesAlmacenACerrar.getMes().getCodMes()+"'";
            ResultSet res=st.executeQuery(consulta);
            if(res.next())
            {
                if(res.getString("ESTADO").equals("1"))
                    mensaje="1";
                else
                    mensaje="2";
            }
            if(mensaje.equals(""))
            {
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm");
                ManagedAccesoSistema user=(ManagedAccesoSistema)Util.getSessionBean("ManagedAccesoSistema");
                consulta="INSERT INTO ESTADOS_TRANSACCIONES_ALMACEN(COD_GESTION, COD_MES, FECHA_CIERRE"+
                         ", COD_PERSONAL, ESTADO)VALUES ('"+estadosTransaccionesAlmacenACerrar.getGestion().getCodGestion()+"'" +
                         ", '"+estadosTransaccionesAlmacenACerrar.getMes().getCodMes()+"','"+sdf.format(new Date())+"'," +
                         "'"+user.getUsuarioModuloBean().getCodUsuarioGlobal()+"', 1)";
                System.out.println("consulta cerrar "+consulta);
                PreparedStatement pst=con.prepareStatement(consulta);
                if(pst.executeUpdate()>0)mensaje="3";
                pst.close();
                begin=0;
                end=10;
                this.cargarEstadosTransaccionesAlmacen();
                cantidadfilas=estadosTransaccionesAlmacenList.size();
            }
            res.close();
            st.close();
            con.close();
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
    public String abrirTransaccionesFecha(Date fechaCierre)
    {
        String mensaje="";
        try
        {
            Connection con=null;
            con=Util.openConnection(con);
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
            SimpleDateFormat mes=new SimpleDateFormat("MM");
            String consulta="select g.COD_GESTION from GESTIONES g where '"+sdf.format(fechaCierre)+" 12:00:00' BETWEEN g.FECHA_INI and g.FECHA_FIN";
            ResultSet res=st.executeQuery(consulta);
            String codGestion="";
            if(res.next())
            {
                codGestion=res.getString("COD_GESTION");
            }
            consulta="select top 1 eta.ESTADO from ESTADOS_TRANSACCIONES_ALMACEN eta" +
                            " where eta.COD_GESTION='"+codGestion+"'" +
                            " and eta.COD_MES='"+mes.format(fechaCierre)+"'";
            System.out.println("consulta verificar reg "+consulta);
            res=st.executeQuery(consulta);
            SimpleDateFormat sdfNuevo=new SimpleDateFormat("yyyy/MM/dd HH:mm");
            ManagedAccesoSistema user=(ManagedAccesoSistema)Util.getSessionBean("ManagedAccesoSistema");
            if(res.next())
            {
                if(!res.getString("ESTADO").equals("2"))
                {
                    consulta="UPDATE ESTADOS_TRANSACCIONES_ALMACEN SET "+
                            " ESTADO = '2'"+
                            " WHERE COD_GESTION = '"+codGestion+"'"+
                            " and COD_MES = '"+mes.format(fechaCierre)+"'";
                    System.out.println("consulta abrir transacciones "+consulta);
                    PreparedStatement pst=con.prepareStatement(consulta);
                    if(pst.executeUpdate()>0){
                        System.out.println("Se actualizo el estado del cierre de transacciones a abierto");
                        mensaje="Se habilitaron las transacciones para la fecha";
                    }
                    pst.close();
                }
                else
                {
                    mensaje="Las transacciones para la fecha se encuentran habilitadas";
                }
            }
            else
            {
               mensaje="Las transacciones para la fecha se encuentran habilitadas";
            }
            res.close();
            st.close();
            con.close();
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        return mensaje;
    }

    public String cerrarTransaccionesFecha(Date fechaCierre)
    {
        String mensaje="";
        try
        {
            Connection con=null;
            con=Util.openConnection(con);
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
            SimpleDateFormat mes=new SimpleDateFormat("MM");
            String consulta="select g.COD_GESTION from GESTIONES g where '"+sdf.format(fechaCierre)+" 12:00:00' BETWEEN g.FECHA_INI and g.FECHA_FIN";
            ResultSet res=st.executeQuery(consulta);
            String codGestion="";
            if(res.next())
            {
                codGestion=res.getString("COD_GESTION");
            }
            consulta="select top 1 eta.ESTADO from ESTADOS_TRANSACCIONES_ALMACEN eta" +
                            " where eta.COD_GESTION='"+codGestion+"'" +
                            " and eta.COD_MES='"+mes.format(fechaCierre)+"'";
            System.out.println("consulta verificar reg "+consulta);
            res=st.executeQuery(consulta);
            SimpleDateFormat sdfNuevo=new SimpleDateFormat("yyyy/MM/dd HH:mm");
            ManagedAccesoSistema user=(ManagedAccesoSistema)Util.getSessionBean("ManagedAccesoSistema");
            if(res.next())
            {
                if(!res.getString("ESTADO").equals("1"))
                {
                    consulta="UPDATE ESTADOS_TRANSACCIONES_ALMACEN SET "+
                            " ESTADO = '1'"+
                            " ,FECHA_CIERRE ='"+sdfNuevo.format(new Date())+"'"+
                            " ,COD_PERSONAL ='"+user.getUsuarioModuloBean().getCodUsuarioGlobal()+"'"+
                            " WHERE COD_GESTION = '"+codGestion+"'"+
                            " and COD_MES = '"+mes.format(fechaCierre)+"'";
                    System.out.println("consulta cerrar transacciones "+consulta);
                    PreparedStatement pst=con.prepareStatement(consulta);
                    if(pst.executeUpdate()>0){
                        System.out.println("Se actualizo el estado del cierre de transacciones a cerrado");
                        mensaje="Se realizo el cierre de transacciones";
                    }
                    pst.close();
                }
                else
                {
                    mensaje="Las transacciones para la fecha se encuentran cerradas ";
                }
            }
            else
            {
                consulta="INSERT INTO ESTADOS_TRANSACCIONES_ALMACEN(COD_GESTION, COD_MES, FECHA_CIERRE"+
                         ", COD_PERSONAL, ESTADO)VALUES ('"+codGestion+"'" +
                         ", '"+mes.format(fechaCierre)+"','"+sdfNuevo.format(new Date())+"'," +
                         "'"+user.getUsuarioModuloBean().getCodUsuarioGlobal()+"' , 1)";
                System.out.println("consulta cerrar "+consulta);
                PreparedStatement pst=con.prepareStatement(consulta);
                if(pst.executeUpdate()>0)
                {
                    System.out.println("se registro el cierre de transacciones");
                    mensaje="Se realizo el cierre de transacciones";
                }
                pst.close();
            }
            res.close();
            st.close();
            con.close();
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        return mensaje;
    }

    public List<EstadosTransaccionesAlmacen> getEstadosTransaccionesAlmacenList() {
        return estadosTransaccionesAlmacenList;
    }

    public void setEstadosTransaccionesAlmacenList(List<EstadosTransaccionesAlmacen> estadosTransaccionesAlmacenList) {
        this.estadosTransaccionesAlmacenList = estadosTransaccionesAlmacenList;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List<SelectItem> getGestionesList() {
        return gestionesList;
    }

    public void setGestionesList(List<SelectItem> gestionesList) {
        this.gestionesList = gestionesList;
    }

    public List<SelectItem> getMesesList() {
        return mesesList;
    }

    public void setMesesList(List<SelectItem> mesesList) {
        this.mesesList = mesesList;
    }

    public EstadosTransaccionesAlmacen getEstadosTransaccionesAlmacenACerrar() {
        return estadosTransaccionesAlmacenACerrar;
    }

    public void setEstadosTransaccionesAlmacenACerrar(EstadosTransaccionesAlmacen estadosTransaccionesAlmacenACerrar) {
        this.estadosTransaccionesAlmacenACerrar = estadosTransaccionesAlmacenACerrar;
    }

    
}
