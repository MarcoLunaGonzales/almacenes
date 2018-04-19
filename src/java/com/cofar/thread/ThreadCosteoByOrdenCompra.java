/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofar.thread;
import com.cofar.bean.IngresosAlmacen;
import com.cofar.bean.IngresosAlmacenDetalle;
import com.cofar.service.AssignCostService;
import com.cofar.service.AssignCostServiceMultiple;
import com.cofar.util.Util;
import java.util.List;

/**
 *
 * @author DASISAQ
 */
public class ThreadCosteoByOrdenCompra extends Thread{
    //propiedades de envio de correo
    List<Object> ingresosAlmacenDetalleList;
    IngresosAlmacen ingresosAlmacen;

    public ThreadCosteoByOrdenCompra(List<Object> ingresosAlmacenDetalleList, IngresosAlmacen ingresosAlmacen) {
        this.ingresosAlmacenDetalleList = ingresosAlmacenDetalleList;
        this.ingresosAlmacen = ingresosAlmacen;
    }

    
    
    @Override
    public void run() {
        try {
                AssignCostService assignCostService = new AssignCostService(Util.getPropertiesConnection());
                float ufv_actual = assignCostService.getUfvActual();
                for (Object detalle : ingresosAlmacenDetalleList) {
                    IngresosAlmacenDetalle ingresosAlmacenDetalle = (IngresosAlmacenDetalle) detalle;
                    boolean resultado=assignCostService.updateCostoUnitarioIngreso(ingresosAlmacen.getCodIngresoAlmacen(), ingresosAlmacen.getAlmacenes().getCodAlmacen(), ingresosAlmacen.getTiposIngresoAlmacen().getCodTipoIngresoAlmacen(), Integer.parseInt(ingresosAlmacenDetalle.getMateriales().getCodMaterial()), ingresosAlmacenDetalle.getCantTotalIngresoFisico(), ufv_actual);
                    if(resultado){
                        System.out.println("update ok -> "+ingresosAlmacenDetalle.getMateriales().getNombreMaterial());
                    }
                }
                System.out.println("Costos actualizado en ingreso OC= "+ingresosAlmacen.getCodIngresoAlmacen());
            } catch (Exception e) {
                System.out.println("Excepcion en costos");
                e.printStackTrace();
            }
    }
    
    
}
