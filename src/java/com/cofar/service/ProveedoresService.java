/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.service;

import com.cofar.service.impl.ProveedoresImpl;
import com.cofar.util.Util;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.model.SelectItem;

/**
 *
 * @author hvaldivia
 */
public class ProveedoresService implements ProveedoresImpl {
     public List cargarProveedores() {
        List proveedoresList = new ArrayList();
        try {

            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select p.COD_PROVEEDOR,p.NOMBRE_PROVEEDOR from proveedores p where p.COD_ESTADO_REGISTRO = 1 ";

            ResultSet rs = null;
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            proveedoresList.clear();
            rs = st.executeQuery(consulta);
            while (rs.next()) {
                proveedoresList.add(new SelectItem(rs.getString("COD_PROVEEDOR"), rs.getString("NOMBRE_PROVEEDOR")));
            }

            if (rs != null) {
                rs.close();
                st.close();
                rs = null;
                st = null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return proveedoresList;
    }
     public static void main(String args[]) {
        Connection con = null;
        try{
        con = Util.openConnection(con);
        AssignCostServiceMultiple costos = new AssignCostServiceMultiple(Util.getPropertiesConnection());
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2016, 4, 01);
        /*Stack<IngresoCost> stackKardexMaterial = costos.getStackKardexMaterial(calendar.getTime(), new Date(), 1, 121);
        while (!stackKardexMaterial.empty()) {
            IngresoCost ingreso = stackKardexMaterial.pop();
            System.out.println("ingreso: " + ingreso.getCod_ingreso_almacen() + ", " + ingreso.getCod_tipo_ingreso_almacen() + ", " + ingreso.getFecha_ingreso_almacen());
            for (SalidaCost salida : ingreso.getLista_salidasCost()) {
                System.out.println("\t-->salida: " + salida.getCod_salida_almacen() + ", " + salida.getCod_tipo_salida_almacen() + ", " + salida.getFecha_salida_almacen());
            }
        }*/

        Calendar c1 = Calendar.getInstance();
        c1.setTime(new Date());
        /*System.out.println("INICIO. " + c1.getTime());
        List<AlmacenCost> listaAlmacenes = costos.getQueueAlmacenes(calendar.getTime(), new Date(), 121);
        while (!listaAlmacenes.isEmpty()) {
            AlmacenCost almacen = listaAlmacenes.remove(0);
            System.out.println("****ALMACEN**** " + almacen.getCod_almacen());
            Stack<IngresoCost> stackKardexMaterial1 = almacen.getStackIngresosCost();
            while (!stackKardexMaterial1.empty()) {
                IngresoCost ingreso = stackKardexMaterial1.pop();
                System.out.println("ingreso w: " + ingreso.getCod_ingreso_almacen() + ", " + ingreso.getCod_tipo_ingreso_almacen() + ", " + ingreso.getFecha_ingreso_almacen());
                for (SalidaCost salida : ingreso.getLista_salidasCost()) {
                    System.out.println("\t-->salida w: " + salida.getCod_salida_almacen() + ", " + salida.getCod_tipo_salida_almacen() + ", " + salida.getFecha_salida_almacen());
                }
            }
        }*/
        Calendar c2 = Calendar.getInstance();
        c2.setTime(new Date());
        System.out.println("INICIAL. " + c1.getTime());
        System.out.println("FINAL. " + c2.getTime());
        costos.updateCostosMaterial(calendar.getTime(), new Date(), 17252);
        //costos.updateCostosMaterialMultiple(calendar.getTime());
        Calendar c3 = Calendar.getInstance();
        c3.setTime(new Date());
        System.out.println("INICIAL update. " + c2.getTime());
        System.out.println("FINAL update. " + c3.getTime());
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
