/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofar.thread;
import com.cofar.bean.SalidasAlmacen;
import com.cofar.bean.SalidasAlmacenDetalle;
import com.cofar.service.AssignCostService;
import com.cofar.service.AssignCostServiceMultiple;
import com.cofar.util.Util;
import java.util.List;

/**
 *
 * @author DASISAQ
 */
public class ThreadCosteoSalidaAlmacen extends Thread{
    //propiedades de envio de correo
    List<Object> salidasAlmacenDetalleList;
    SalidasAlmacen salidasAlmacen;

    public ThreadCosteoSalidaAlmacen(List<Object> salidasAlmacenDetalleList, SalidasAlmacen salidasAlmacen) {
        this.salidasAlmacenDetalleList = salidasAlmacenDetalleList;
        this.salidasAlmacen = salidasAlmacen;
    }
    
    @Override
    public void run() {
        try {

            AssignCostService assignCostService = new AssignCostService(Util.getPropertiesConnection());
            float ufv_actual = assignCostService.getUfvActual();
            for (Object detalle : salidasAlmacenDetalleList) {
                SalidasAlmacenDetalle salidasAlmacenDetalle = (SalidasAlmacenDetalle) detalle;
                boolean resultado = assignCostService.updateCostoUnitarioSalida(salidasAlmacen.getCodSalidaAlmacen(), salidasAlmacen.getAlmacenes().getCodAlmacen(), salidasAlmacen.getTiposSalidasAlmacen().getCodTipoSalidaAlmacen(), Integer.parseInt(salidasAlmacenDetalle.getMateriales().getCodMaterial()), salidasAlmacenDetalle.getCantidadSalidaAlmacen(), ufv_actual);
                if (resultado) {
                    System.out.println("update ok -> " + salidasAlmacenDetalle.getMateriales().getNombreMaterial());
                }
            }
            System.out.println("Costos actualizado en salida APL= " + salidasAlmacen.getCodSalidaAlmacen());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
}
