/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofar.web;

import com.cofar.bean.Capitulos;
import com.cofar.dao.DaoCapitulos;
import com.cofar.dao.DaoEstadosReferenciales;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;
import org.apache.logging.log4j.LogManager;

/**
 *
 * @author hvaldivia
 */
public class ManagedCapitulos extends ManagedBean{

    private List<Capitulos> capitulosList = new ArrayList<>();
    private Capitulos capitulos = new Capitulos();
    private List<SelectItem> estadosReferencialesList = new ArrayList();
    
    /** Creates a new instance of ManagedCapitulos */
    public ManagedCapitulos() {
        this.LOGGER = LogManager.getRootLogger();
    }

    public String getCargarContenidoCapitulos() {
        this.listadoCapitulos();
        DaoEstadosReferenciales daoEstados = new DaoEstadosReferenciales(this.LOGGER);
        estadosReferencialesList = daoEstados.listarActivosRegistro();
        return null;
    }

    public void listadoCapitulos() {
       DaoCapitulos daoCapitulos = new DaoCapitulos(LOGGER);
       capitulosList = daoCapitulos.listar(new Capitulos());
    }

    public String cargarAgregarCapituloAction() {
        capitulos = new Capitulos();
        return null;
    }

    public String guardarCapituloAction() {
        this.transaccionExitosa = false;
        DaoCapitulos daoCapitulos  = new DaoCapitulos(LOGGER);
        capitulos.setCapituloAlmacen(1);
        if(daoCapitulos.guardar(capitulos)){
            this.mostrarMensajeTransaccionExitosa("Se registro satisfactoriamente el capítulo");
            this.listadoCapitulos();
        }
        else{
            this.mostrarMensajeTransaccionFallida("Ocurrio un error al momento de registrar el capitulo, intente de nuevo");
        }
        return null;
    }


    public String modificarCapituloAction(){
        this.transaccionExitosa = false;
        DaoCapitulos daoCapitulos = new DaoCapitulos(LOGGER);
        if(daoCapitulos.modificar(capitulos)){
            this.mostrarMensajeTransaccionExitosa("Se registro satisfactoriamente la edición del capítulo");
            this.listadoCapitulos();
        }
        else{
            this.mostrarMensajeTransaccionFallida("Ocurrio un error al momento de registrar le edición del capítulo,intente de nuevo");
        }
        return null;
    }
    public String eliminarCapituloAction(int codCapitulo){
        this.transaccionExitosa = false;
        DaoCapitulos daoCapitulos = new DaoCapitulos(LOGGER);
        if(daoCapitulos.eliminar(codCapitulo)){
            this.mostrarMensajeTransaccionExitosa("Se elimino satisfactoriamente el capítulo");
            this.listadoCapitulos();
        }
        else{
            this.mostrarMensajeTransaccionFallida("Ocurrio un error al momento de eliminar el capítulo,intente de nuevo");
        }
        return null;
    }
    
    //<editor-fold defaultstate="collapsed" desc="getter and setter">
        public List<Capitulos> getCapitulosList() {
            return capitulosList;
        }

        public void setCapitulosList(List<Capitulos> capitulosList) {
            this.capitulosList = capitulosList;
        }

        public Capitulos getCapitulos() {
            return capitulos;
        }

        public void setCapitulos(Capitulos capitulos) {
            this.capitulos = capitulos;
        }

        public List<SelectItem> getEstadosReferencialesList() {
            return estadosReferencialesList;
        }

        public void setEstadosReferencialesList(List<SelectItem> estadosReferencialesList) {
            this.estadosReferencialesList = estadosReferencialesList;
        }
    
    //</editor-fold>

}
