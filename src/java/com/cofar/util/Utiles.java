/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofar.util;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author hvaldivia
 */
public class Utiles {

    public static String direccionar(String direccion) {
        try {

            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext ext = facesContext.getExternalContext();
            ext.redirect(direccion);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static float redondear(float Rval, int Rpl) {
        float redondeado = 0;
        try {
            float p = (float) Math.pow(10, Rpl);
            Rval = Rval * p;
            float tmp = Math.round(Rval);
            redondeado= (float) tmp / p;
        } catch (Exception e) {
        }
        return redondeado;
    }
    
}
