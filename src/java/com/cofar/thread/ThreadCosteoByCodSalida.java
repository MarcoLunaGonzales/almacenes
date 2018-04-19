/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofar.thread;
import com.cofar.service.AssignCostServiceMultiple;
import com.cofar.util.Util;

/**
 *
 * @author DASISAQ
 */
public class ThreadCosteoByCodSalida extends Thread{
    //propiedades de envio de correo
    int codSalidaAlmacen;
    
    public ThreadCosteoByCodSalida(int codSalidaAlmacen){
        this.codSalidaAlmacen = codSalidaAlmacen;
        
    }

    @Override
    public void run() {
        try {
            AssignCostServiceMultiple assignCostService = new AssignCostServiceMultiple(Util.getPropertiesConnection());
            assignCostService.updateCostosMaterialByCodSalida(this.codSalidaAlmacen);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
}
