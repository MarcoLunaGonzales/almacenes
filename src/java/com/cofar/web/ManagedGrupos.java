/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.web;

import com.cofar.bean.Grupos;
import com.cofar.dao.DaoCapitulos;
import com.cofar.dao.DaoEstadosReferenciales;
import com.cofar.dao.DaoGrupos;
import com.cofar.util.Util;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.model.SelectItem;
import org.apache.logging.log4j.LogManager;

/**
 *
 * @author hvaldivia
 */

public class ManagedGrupos extends ManagedBean{

    private List<Grupos> gruposList = new ArrayList();
    private Grupos grupos = new Grupos();
    private Grupos gruposCriterio = new Grupos();
    private List<SelectItem> capitulosSelectList = new ArrayList();
    private List<SelectItem> estadosReferencialesSelectList = new ArrayList();
    
    /** Creates a new instance of ManagedGrupos */
    public ManagedGrupos() {
        LOGGER = LogManager.getRootLogger();
    }
    public String getCargarContenidoGrupos(){
        DaoCapitulos daoCapitulos = new DaoCapitulos(LOGGER);
        capitulosSelectList = daoCapitulos.listarActivosSelectItem();
        this.cargarGruposList();
        DaoEstadosReferenciales daoEstadosReferenciales = new DaoEstadosReferenciales(LOGGER);
        estadosReferencialesSelectList = daoEstadosReferenciales.listarActivosRegistro();
        return null;
    }
    private void cargarGruposList() {
        DaoGrupos daoGrupos = new DaoGrupos(LOGGER);
        gruposList = daoGrupos.listar(gruposCriterio);
    }
    public String cargarAgregarGrupoAction(){
        grupos = new Grupos();
        return null;
    }
    
    public String guardarGrupoAction(){
        DaoGrupos daoGrupos = new DaoGrupos(this.LOGGER);
        if(daoGrupos.guardar(grupos)){
            this.mostrarMensajeTransaccionExitosa("Se registro satisfactoriamente el registro");
            this.cargarGruposList();
        }
        else{
            this.mostrarMensajeTransaccionFallida("Ocurrio un error al momento de registrar el grupo, intente de nuevo");
        }
        return null;
    }
    public String modificarGrupoAction(){
        DaoGrupos daoGrupos = new DaoGrupos(this.LOGGER);
        if(daoGrupos.modificar(grupos)){
            this.mostrarMensajeTransaccionExitosa("Se registro satisfactoriamente la edición del grupo");
            this.cargarGruposList();
        }
        else{
            this.mostrarMensajeTransaccionFallida("Ocurrio un error al momento de modificar el grupo, intente de nuevo");
        }
        return null;
    }
    public String eliminarGrupoAction(int codGrupo){
        DaoGrupos daoGrupos = new DaoGrupos(this.LOGGER);
        if(daoGrupos.eliminar(codGrupo)){
            this.mostrarMensajeTransaccionExitosa("Se elimino satisfactoriamente el grupo");
            this.cargarGruposList();
        }
        else{
            this.mostrarMensajeTransaccionFallida("Ocurrio un error al momento de eliminar el grupo, intente de nuevo");
        }
        return null;
    }
    //<editor-fold defaultstate="collapsed" desc="getter and setter">
        public List<Grupos> getGruposList() {
            return gruposList;
        }

        public void setGruposList(List<Grupos> gruposList) {
            this.gruposList = gruposList;
        }

        public Grupos getGrupos() {
            return grupos;
        }

        public void setGrupos(Grupos grupos) {
            this.grupos = grupos;
        }

        public Grupos getGruposCriterio() {
            return gruposCriterio;
        }

        public void setGruposCriterio(Grupos gruposCriterio) {
            this.gruposCriterio = gruposCriterio;
        }

        public List<SelectItem> getCapitulosSelectList() {
            return capitulosSelectList;
        }

        public void setCapitulosSelectList(List<SelectItem> capitulosSelectList) {
            this.capitulosSelectList = capitulosSelectList;
        }

        public List<SelectItem> getEstadosReferencialesSelectList() {
            return estadosReferencialesSelectList;
        }

        public void setEstadosReferencialesSelectList(List<SelectItem> estadosReferencialesSelectList) {
            this.estadosReferencialesSelectList = estadosReferencialesSelectList;
        }
    //</editor-fold>
}
