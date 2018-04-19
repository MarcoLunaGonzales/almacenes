/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.bean;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hvaldivia
 */
public class ConfiguracionSalidaAlmacenProduccion extends AbstractBean{
    TiposMaterial tiposMaterial = new TiposMaterial();
    TiposSalidaAlmacenProduccion tiposSalidaAlmacenProduccion= new TiposSalidaAlmacenProduccion();
    Personal personal = new Personal();

    public Personal getPersonal() {
        return personal;
    }

    public void setPersonal(Personal personal) {
        this.personal = personal;
    }

    public TiposMaterial getTiposMaterial() {
        return tiposMaterial;
    }

    public void setTiposMaterial(TiposMaterial tiposMaterial) {
        this.tiposMaterial = tiposMaterial;
    }

    public TiposSalidaAlmacenProduccion getTiposSalidaAlmacenProduccion() {
        return tiposSalidaAlmacenProduccion;
    }

    public void setTiposSalidaAlmacenProduccion(TiposSalidaAlmacenProduccion tiposSalidaAlmacenProduccion) {
        this.tiposSalidaAlmacenProduccion = tiposSalidaAlmacenProduccion;
    }
    
}
