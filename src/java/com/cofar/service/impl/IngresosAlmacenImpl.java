/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.service.impl;

import com.cofar.web.ManagedAccesoSistema;
import java.util.List;

/**
 *
 * @author hvaldivia
 */
public interface IngresosAlmacenImpl {
    public List listadoIngresosAlmacen(int filaInicial,int filaFinal,ManagedAccesoSistema usuario);
    public int obtieneNroIngresoAlmacen(ManagedAccesoSistema usuario);
}
