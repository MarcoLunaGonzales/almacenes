/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofar.service;
import com.cofar.bean.IngresosAlmacen;
import com.cofar.service.impl.*;
import com.cofar.util.Util;
import com.cofar.web.ManagedAccesoSistema;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author hvaldivia
 */
public class IngresoAlmacenService implements IngresosAlmacenImpl {
    
    
    public List listadoIngresosAlmacen(int filaInicial,int filaFinal,ManagedAccesoSistema usuario){
        List<IngresosAlmacen> ingresosAlmacenList = new ArrayList<IngresosAlmacen>();
        try {

            Connection con = null;
            con = Util.openConnection(con);

            String consulta = " select * from (select ROW_NUMBER() OVER (ORDER BY ia.FECHA_INGRESO_ALMACEN desc) as 'FILAS' " +
                    " ,ia.NRO_INGRESO_ALMACEN,ia.COD_INGRESO_ALMACEN,eia.COD_ESTADO_INGRESO_ALMACEN,eia.NOMBRE_ESTADO_INGRESO_ALMACEN, " +
                    " ia.FECHA_INGRESO_ALMACEN,t.COD_TIPO_INGRESO_ALMACEN,t.NOMBRE_TIPO_INGRESO_ALMACEN,oc.NRO_ORDEN_COMPRA,'' almacen_origen_traspaso, " +
                    " pr.COD_PROVEEDOR,pr.NOMBRE_PROVEEDOR,p.COD_PAIS,p.NOMBRE_PAIS,ia.OBS_INGRESO_ALMACEN,per.NOMBRE_PILA,per.AP_PATERNO_PERSONAL,per.AP_MATERNO_PERSONAL " +
                    " from INGRESOS_ALMACEN ia " +
                    " left outer join  TIPOS_INGRESO_ALMACEN t on t.COD_TIPO_INGRESO_ALMACEN = ia.COD_TIPO_INGRESO_ALMACEN " +
                    " left outer join proveedores pr on pr.COD_PROVEEDOR = ia.COD_PROVEEDOR " +
                    " left outer join  paises p on pr.COD_PAIS = p.COD_PAIS " +
                    " left outer join ORDENES_COMPRA oc on oc.COD_ORDEN_COMPRA = ia.COD_ORDEN_COMPRA " +
                    " left outer join ESTADOS_INGRESO_ALMACEN eia on eia.COD_ESTADO_INGRESO_ALMACEN = ia.COD_ESTADO_INGRESO_ALMACEN " +
                    " left outer join TIPOS_COMPRA tc on tc.COD_TIPO_COMPRA = ia.COD_TIPO_COMPRA " +
                    " left outer join personal per on per.COD_PERSONAL = ia.COD_PERSONAL " +
                    " left outer join ESTADOS_INGRESOS_LIQUIDACION eil on eil.COD_ESTADO_INGRESO_LIQUIDACION = ia.COD_ESTADO_INGRESO_LIQUIDACION " +
                    " where ia.COD_GESTION = '"+usuario.getGestionesGlobal().getCodGestion()+"' " +
                    " and ia.COD_ALMACEN = '"+usuario.getAlmacenesGlobal().getCodAlmacen()+"' ) AS listado where FILAS BETWEEN "+filaInicial+" AND "+filaFinal+"   ";
            
            con = Util.openConnection(con);
            System.out.println("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            ingresosAlmacenList.clear();            
            while(rs.next()){
                IngresosAlmacen ingresosAlmacen = new IngresosAlmacen();
                ingresosAlmacen.setNroIngresoAlmacen(rs.getInt("NRO_INGRESO_ALMACEN"));
                ingresosAlmacen.setCodIngresoAlmacen(rs.getInt("COD_INGRESO_ALMACEN"));
                ingresosAlmacen.getEstadosIngresoAlmacen().setCodEstadoIngresoAlmacen(rs.getInt("COD_ESTADO_INGRESO_ALMACEN"));
                ingresosAlmacen.getEstadosIngresoAlmacen().setNombreEstadoIngresoAlmacen(rs.getString("NOMBRE_ESTADO_INGRESO_ALMACEN"));
                ingresosAlmacen.setFechaIngresoAlmacen(rs.getDate("FECHA_INGRESO_ALMACEN"));
                ingresosAlmacen.getTiposIngresoAlmacen().setCodTipoIngresoAlmacen(rs.getInt("COD_TIPO_INGRESO_ALMACEN"));
                ingresosAlmacen.getTiposIngresoAlmacen().setNombreTipoIngresoAlmacen(rs.getString("NOMBRE_TIPO_INGRESO_ALMACEN"));
                ingresosAlmacen.getOrdenesCompra().setNroOrdenCompra(rs.getInt("NRO_ORDEN_COMPRA"));
                ingresosAlmacen.getProveedores().setCodProveedor(rs.getString("COD_PROVEEDOR"));
                ingresosAlmacen.getProveedores().setNombreProveedor(rs.getString("NOMBRE_PROVEEDOR"));
                ingresosAlmacen.getProveedores().getPaises().setCodPais(rs.getInt("COD_PAIS"));
                ingresosAlmacen.getProveedores().getPaises().setNombrePais(rs.getString("NOMBRE_PAIS"));
                ingresosAlmacen.setObsIngresoAlmacen(rs.getString("OBS_INGRESO_ALMACEN"));
                ingresosAlmacen.getPersonal().setNombrePersonal(rs.getString("NOMBRE_PILA"));
                ingresosAlmacen.getPersonal().setApPaternoPersonal(rs.getString("AP_PATERNO_PERSONAL"));
                ingresosAlmacen.getPersonal().setApMaternoPersonal(rs.getString("AP_MATERNO_PERSONAL"));
                ingresosAlmacenList.add(ingresosAlmacen);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ingresosAlmacenList;
    }
    
    public int obtieneNroIngresoAlmacen(ManagedAccesoSistema usuario){
        int nroIngresoAlmacen = 0;
        try {
            Connection con = null;
            con = Util.openConnection(con);
            
            String consulta= " select (isnull(max(nro_ingreso_almacen),0)+1) nro_ingreso_almacen  from ingresos_almacen where cod_gestion='"+usuario.getGestionesGlobal().getCodGestion()+"' " +
                     " and estado_sistema=1 and cod_almacen='"+usuario.getAlmacenesGlobal().getCodAlmacen()+"'  ";
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            if(rs.next()){
                nroIngresoAlmacen = rs.getInt("nro_ingreso_almacen");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nroIngresoAlmacen;
    }
    

}
