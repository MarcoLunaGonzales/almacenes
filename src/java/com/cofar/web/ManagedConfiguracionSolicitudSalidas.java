/*
 *To change this license header, choose License Headers in Project Properties.
 *To change this template file, choose Tools | Templates
 *and open the template in the editor.
 */
package com.cofar.web;

import com.cofar.bean.Capitulos;
import com.cofar.bean.CapitulosConfiguracionSolicitudSalida;
import com.cofar.bean.GruposConfiguracionSolicitudSalida;
import com.cofar.bean.MaterialesConfiguracionSolicitudSalida;
import com.cofar.util.Util;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.model.SelectItem;
import org.richfaces.component.html.HtmlDataTable;

/**
 *
 * @author Jaime Chura
 * @version BACO 2.0 date 05-11-2015 time 08:43:18 AM
 */
public class ManagedConfiguracionSolicitudSalidas {

    public String getCargarContenidoConfiguracion() {
        listCapitulos = cargarCapitulosAlmacen();
        return null;
    }

    private List<CapitulosConfiguracionSolicitudSalida> listCapitulos = new ArrayList<CapitulosConfiguracionSolicitudSalida>();

    public List<CapitulosConfiguracionSolicitudSalida> getListCapitulos() {
        return listCapitulos;
    }

    public void setListCapitulos(List<CapitulosConfiguracionSolicitudSalida> listCapitulos) {
        this.listCapitulos = listCapitulos;
    }

    public List<CapitulosConfiguracionSolicitudSalida> cargarCapitulosAlmacen() {
        ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
        selectCapitulo = new CapitulosConfiguracionSolicitudSalida();
        selectGrupo = new GruposConfiguracionSolicitudSalida();
        List<CapitulosConfiguracionSolicitudSalida> capitulosList = new ArrayList<CapitulosConfiguracionSolicitudSalida>();
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " exec [USP_GET_LISTA_CAPITULOS_SOLICITUDES_SALIDA_ABM] " + usuario.getAlmacenesGlobal().getCodAlmacen();
            System.out.println("consulta capitulos: " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            capitulosList.clear();

            while (rs.next()) {
                CapitulosConfiguracionSolicitudSalida reg = new CapitulosConfiguracionSolicitudSalida();
                reg.setCodCapitulo(rs.getInt("COD_CAPITULO"));
                reg.setNombreCapitulo(rs.getString("NOMBRE_CAPITULO"));
                reg.setCapituloCompleto(rs.getString("rv") != null);
                reg.setCapituloParcial(rs.getString("rp") != null);
                reg.setColor(reg.isCapituloCompleto() ? "#CEF6E3" : (reg.isCapituloParcial() ? "#FEFCA9" : "#F6CED8"));
                capitulosList.add(reg);
            }
            if (rs != null) {
                rs.close();
                st.close();
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return capitulosList;
    }

    public String actionSelectCapitulo() {
        selectCapitulo = (CapitulosConfiguracionSolicitudSalida) capitulosSelect.getRowData();
        listGrupos = cargarGruposAlmacen(selectCapitulo.getCodCapitulo());
        return null;
    }

    private HtmlDataTable capitulosSelect = new HtmlDataTable();

    public HtmlDataTable getCapitulosSelect() {
        return capitulosSelect;
    }

    public void setCapitulosSelect(HtmlDataTable capitulosSelect) {
        this.capitulosSelect = capitulosSelect;
    }

    private HtmlDataTable gruposSelect = new HtmlDataTable();

    public HtmlDataTable getGruposSelect() {
        return gruposSelect;
    }

    public void setGruposSelect(HtmlDataTable gruposSelect) {
        this.gruposSelect = gruposSelect;
    }

    private List<GruposConfiguracionSolicitudSalida> listGrupos = new ArrayList<GruposConfiguracionSolicitudSalida>();

    public List<GruposConfiguracionSolicitudSalida> getListGrupos() {
        return listGrupos;
    }

    public void setListGrupos(List<GruposConfiguracionSolicitudSalida> ListGrupos) {
        this.listGrupos = ListGrupos;
    }

    public List<GruposConfiguracionSolicitudSalida> cargarGruposAlmacen(int codCapitulo) {
        ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
        selectGrupo = new GruposConfiguracionSolicitudSalida();
        List<GruposConfiguracionSolicitudSalida> gruposList = new ArrayList<GruposConfiguracionSolicitudSalida>();
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " exec [USP_GET_LISTA_GRUPOS_SOLICITUDES_SALIDA_ABM] "
                    + usuario.getAlmacenesGlobal().getCodAlmacen() + ", " + codCapitulo;
            System.out.println("consulta grupos: " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            gruposList.clear();

            while (rs.next()) {
                GruposConfiguracionSolicitudSalida reg = new GruposConfiguracionSolicitudSalida();
                reg.setCodGrupo(rs.getInt("COD_GRUPO"));
                reg.setNombreGrupo(rs.getString("NOMBRE_GRUPO"));
                reg.setGrupoCompleto(rs.getString("rv") != null);
                reg.setGrupoParcial(rs.getString("rp") != null);
                reg.setColor(reg.isGrupoCompleto() ? "#CEF6E3" : (reg.isGrupoParcial() ? "#FEFCA9" : "#F6CED8"));
                gruposList.add(reg);
            }
            if (rs != null) {
                rs.close();
                st.close();
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gruposList;
    }

    private List<MaterialesConfiguracionSolicitudSalida> listMateriales = new ArrayList<MaterialesConfiguracionSolicitudSalida>();

    public List<MaterialesConfiguracionSolicitudSalida> getListMateriales() {
        return listMateriales;
    }

    public void setListMateriales(List<MaterialesConfiguracionSolicitudSalida> listMateriales) {
        this.listMateriales = listMateriales;
    }

    public List<MaterialesConfiguracionSolicitudSalida> cargarMaterialesAlmacen(int codGrupo) {
        ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");

        List<MaterialesConfiguracionSolicitudSalida> materialesList = new ArrayList<MaterialesConfiguracionSolicitudSalida>();
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " exec [USP_GET_LISTA_MATERIALES_SOLICITUDES_SALIDA_ABM] "
                    + usuario.getAlmacenesGlobal().getCodAlmacen() + ", " + codGrupo;
            System.out.println("consulta materiales: " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            materialesList.clear();

            while (rs.next()) {
                MaterialesConfiguracionSolicitudSalida reg = new MaterialesConfiguracionSolicitudSalida();
                reg.setCodMaterial(rs.getInt("COD_MATERIAL"));
                reg.setNombreMaterial(rs.getString("NOMBRE_MATERIAL"));
                reg.setMaterialHabilitado(rs.getString("habilitado") != null);
                reg.setColor(reg.isMaterialHabilitado() ? "#CEF6E3" : "#F6CED8");
                materialesList.add(reg);
            }
            if (rs != null) {
                rs.close();
                st.close();
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return materialesList;
    }

    public String actionSelectGrupo() {
        selectGrupo = (GruposConfiguracionSolicitudSalida) gruposSelect.getRowData();
        listMateriales = cargarMaterialesAlmacen(selectGrupo.getCodGrupo());
        return null;
    }

    public String actionUpdateCapitulos() {
        Connection con = null;
        try {
            ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            con = Util.openConnection(con);

            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            String consulta = " DELETE FROM CONFIGURACION_SOLICITUDES_SALIDA_ALMACEN"
                    + " WHERE ( COD_CAPITULO<>0)"
                    + " and cod_almacen=" + usuario.getAlmacenesGlobal().getCodAlmacen();
            System.out.println("consulta delete: " + consulta);
            st.executeUpdate(consulta);
            for (CapitulosConfiguracionSolicitudSalida capitulosItem : listCapitulos) {
                if (capitulosItem.isCapituloCompleto()) {
                    consulta = "exec [USP_INSERT_CAPITULOS_SOLICITUDES_SALIDA_ABM] "
                            + usuario.getAlmacenesGlobal().getCodAlmacen() + "," + capitulosItem.getCodCapitulo();
                    System.out.println("consulta insert: " + consulta);
                    st.executeUpdate(consulta);
                }
            }

            st.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    Logger.getLogger(ManagedConfiguracionSolicitudSalidas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    public String actionUpdateGrupos() {
        Connection con = null;
        try {
            ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            con = Util.openConnection(con);

            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String consulta = " DELETE FROM CONFIGURACION_SOLICITUDES_SALIDA_ALMACEN "
                    + " WHERE cod_grupo in(select csa.cod_grupo "
                    + " FROM CONFIGURACION_SOLICITUDES_SALIDA_ALMACEN csa "
                    + " inner join grupos g on g.cod_grupo=csa.cod_grupo "
                    + " and csa.COD_GRUPO<>0 and csa.cod_almacen=" + usuario.getAlmacenesGlobal().getCodAlmacen()
                    + " and g.cod_capitulo =" + selectCapitulo.getCodCapitulo() + ")"
                    + " and COD_ALMACEN=" + usuario.getAlmacenesGlobal().getCodAlmacen();
            System.out.println("consulta delete: " + consulta);
            st.executeUpdate(consulta);
            for (GruposConfiguracionSolicitudSalida gruposItem : listGrupos) {
                if (gruposItem.isGrupoCompleto()) {
                    consulta = "exec [USP_INSERT_GRUPOS_SOLICITUDES_SALIDA_ABM] "
                            + usuario.getAlmacenesGlobal().getCodAlmacen() + "," + gruposItem.getCodGrupo();
                    System.out.println("consulta insert: " + consulta);
                    st.executeUpdate(consulta);
                }
            }
            listGrupos = cargarGruposAlmacen(selectCapitulo.getCodCapitulo());
            listCapitulos = cargarCapitulosAlmacen();
            st.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    Logger.getLogger(ManagedConfiguracionSolicitudSalidas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    public String actionUpdateMateriales() {
        Connection con = null;
        try {
            ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
            con = Util.openConnection(con);

            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String consulta = " DELETE FROM CONFIGURACION_SOLICITUDES_SALIDA_ALMACEN "
                    + " WHERE COD_MATERIAL in(select csa.COD_MATERIAL"
                    + " FROM CONFIGURACION_SOLICITUDES_SALIDA_ALMACEN csa "
                    + " inner join MATERIALES mat on mat.COD_MATERIAL=csa.COD_MATERIAL"
                    + " and csa.COD_MATERIAL<>0 and csa.cod_almacen=" + usuario.getAlmacenesGlobal().getCodAlmacen()
                    + " and mat.COD_GRUPO =" + selectGrupo.getCodGrupo() + ")"
                    + " and COD_ALMACEN=" + usuario.getAlmacenesGlobal().getCodAlmacen();
            System.out.println("consulta delete: " + consulta);
            st.executeUpdate(consulta);
            for (MaterialesConfiguracionSolicitudSalida material : listMateriales) {
                if (material.isMaterialHabilitado()) {
                    consulta = "exec [USP_INSERT_MATERIALES_SOLICITUDES_SALIDA_ABM] "
                            + usuario.getAlmacenesGlobal().getCodAlmacen() + "," + material.getCodMaterial();
                    System.out.println("consulta insert: " + consulta);
                    st.executeUpdate(consulta);
                }
            }
            listMateriales = cargarMaterialesAlmacen(selectGrupo.getCodGrupo());
            listGrupos = cargarGruposAlmacen(selectCapitulo.getCodCapitulo());
            listCapitulos = cargarCapitulosAlmacen();
            st.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    Logger.getLogger(ManagedConfiguracionSolicitudSalidas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    private CapitulosConfiguracionSolicitudSalida selectCapitulo = new CapitulosConfiguracionSolicitudSalida();
    private GruposConfiguracionSolicitudSalida selectGrupo = new GruposConfiguracionSolicitudSalida();

    public CapitulosConfiguracionSolicitudSalida getSelectCapitulo() {
        return selectCapitulo;
    }

    public void setSelectCapitulo(CapitulosConfiguracionSolicitudSalida selectCapitulo) {
        this.selectCapitulo = selectCapitulo;
    }

    public GruposConfiguracionSolicitudSalida getSelectGrupo() {
        return selectGrupo;
    }

    public void setSelectGrupo(GruposConfiguracionSolicitudSalida selectGrupo) {
        this.selectGrupo = selectGrupo;
    }

}
