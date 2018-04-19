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
public class ThreadCosteoByCodIngreso extends Thread{
    //propiedades de envio de correo
    int codIngresoAlmacen;
    int codIngresoAlmacenNoEsteril;
    int codSalidaAlmacenEsteril;

    public ThreadCosteoByCodIngreso(int codIngresoAlmacen, int codSalidaAlmacenEsteril,int codIngresoAlmacenNoEsteril){
        this.codIngresoAlmacen = codIngresoAlmacen;
        this.codIngresoAlmacenNoEsteril = codIngresoAlmacenNoEsteril;
        this.codSalidaAlmacenEsteril = codSalidaAlmacenEsteril;
    }

    @Override
    public void run() {
        try {
            AssignCostServiceMultiple assignCostService = new AssignCostServiceMultiple(Util.getPropertiesConnection());
            assignCostService.updateCostosMaterialByCodIngreso(this.codIngresoAlmacen);
            if(this.codSalidaAlmacenEsteril > 0){
                assignCostService.updateCostosMaterialByCodSalida(this.codSalidaAlmacenEsteril);
            }
            if(this.codIngresoAlmacenNoEsteril > 0){
                assignCostService.updateCostosMaterialByCodIngreso(this.codIngresoAlmacenNoEsteril);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
}
