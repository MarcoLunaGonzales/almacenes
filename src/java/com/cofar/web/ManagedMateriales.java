/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cofar.web;

import com.cofar.bean.Almacenes;
import com.cofar.bean.Materiales;
import com.cofar.bean.TiposMedida;
import com.cofar.util.Util;
import com.cofar.util.UtilMySql;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author hvaldivia
 */
public class ManagedMateriales {
    private Logger LOGGER;
    List capitulosList = new ArrayList();
    List gruposList = new ArrayList();
    List tiposMedidaList = new ArrayList();
    List unidadesMedidaList = new ArrayList();
    private List lineasApoyoList = new ArrayList();
    private List tiposApoyoList = new ArrayList();
    List unidadesMedidaCompraList = new ArrayList();
    List materialesList = new ArrayList();
    List gruposStockMaterialesList = new ArrayList();
    ManagedIngresoAlmacen managedIngresoAlmacen = new ManagedIngresoAlmacen();
    Materiales materiales = new Materiales();
    TiposMedida tiposMedida = new TiposMedida();
    List unidadMedidaPesoAsociadoList = new ArrayList();
    ManagedAccesoSistema usuario = (ManagedAccesoSistema) Util.getSessionBean("ManagedAccesoSistema");
    int start = 0;
    int end = 100000;
    String mensaje = "";
    List estadosRegistroList = new ArrayList();
    private String descripcionTransaccion = "";
    private boolean registradoExitosamente = false;
    private boolean permisoEditarDirectamenteMaterial=false;
    private List<Almacenes> almacenesExistenciaMaterial;

    

    /**
     * Creates a new instance of ManagedMateriales
     */
    public ManagedMateriales() {
        LOGGER = LogManager.getLogger("Materiales");
    }
    private void cargarAlmacenesExistenciaMaterial(String codMaterial){
        Connection con = null;
        try {
            con = Util.openConnection(con);
            StringBuilder consulta = new StringBuilder("select a.NOMBRE_ALMACEN,sum(iade.CANTIDAD_RESTANTE) as cantidadExistenciaAlmacen")
                                            .append(" from ALMACENES a")
                                                    .append(" inner join INGRESOS_ALMACEN ia on ia.COD_ALMACEN= a.COD_ALMACEN")
                                                    .append(" inner join INGRESOS_ALMACEN_DETALLE_ESTADO iade on iade.COD_INGRESO_ALMACEN=ia.COD_INGRESO_ALMACEN")
                                            .append(" where a.COD_ESTADO_REGISTRO=1")
                                                    .append(" and ia.COD_ESTADO_INGRESO_ALMACEN<>2")
                                                    .append(" and iade.CANTIDAD_RESTANTE > 0.009")
                                                    .append(" and iade.COD_MATERIAL =").append(codMaterial)
                                            .append(" group by a.NOMBRE_ALMACEN")
                                            .append(" order by a.NOMBRE_ALMACEN");
            LOGGER.debug("consulta cargar existencias almacen material : "+consulta.toString());
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet res=st.executeQuery(consulta.toString());
            almacenesExistenciaMaterial = new ArrayList<>();
            while(res.next()){
                Almacenes almacenes = new Almacenes();
                almacenes.setCantidadExistente(res.getDouble("cantidadExistenciaAlmacen"));
                almacenes.setNombreAlmacen(res.getString("NOMBRE_ALMACEN"));
                almacenesExistenciaMaterial.add(almacenes);
            }
            con.close();
        } catch (SQLException ex) {
            LOGGER.warn(ex.getMessage());
        } catch (Exception ex) {
            LOGGER.warn(ex.getMessage());
        } 
    }

    public String getCargarContenidoMateriales() {
        //materialesList = this.iniciaLista();
        this.cargarPermisosEspeciales();
        gruposList = this.iniciaLista();
        capitulosList = this.iniciaLista();
        tiposMedidaList = this.iniciaLista();
        unidadesMedidaList = this.iniciaLista();
        unidadesMedidaCompraList = this.iniciaLista();
        gruposStockMaterialesList = this.iniciaLista();
        unidadMedidaPesoAsociadoList = this.iniciaLista();
        estadosRegistroList = this.iniciaLista();
        estadosRegistroList.add(new SelectItem("1", "ACTIVO"));
        estadosRegistroList.add(new SelectItem("2", "NO ACTIVO"));
        //materialesList = this.cargarMateriales();
        capitulosList = managedIngresoAlmacen.cargarCapitulos();
        gruposList.clear();
        gruposList.add(new SelectItem("0", "-NINGUNO-"));
        tiposMedidaList = this.cargarTiposMedida();
        unidadMedidaPesoAsociadoList = this.cargarUnidadesMedida();
        gruposStockMaterialesList = this.cargarGrupoStockMaterial();
        capitulosList.remove(0);
        gruposStockMaterialesList.remove(0);
        materiales.getGrupos().getCapitulos().setCodCapitulo(2);
        //inicio ale edicion
        materiales.setNombreMaterial("");
        materiales.getEstadoRegistro().setCodEstadoRegistro("0");
        materialesList = this.cargarMateriales();
        //final ale edicion
        this.capitulosFiltro_change();
        
        return null;
    }

    public String capitulosFiltro_change() {
        try {
            materiales.getGrupos().setCodGrupo(0);
            this.capitulos_change();
            //materialesList = this.cargarMateriales();
        } catch (Exception e) {
            LOGGER.warn(e);
        }
        return null;
    }

    public String gruposFiltro_change() {
        try {
            materialesList = this.cargarMateriales();
        } catch (Exception e) {
            LOGGER.warn(e);
        }
        return null;
    }

    public String materialProduccion_change() {
        //System.out.println("entro al action" + materiales.getMaterialProduccion() + "  "+materiales.getMaterialMuestreo());
        if (materiales.getMaterialProduccion() == 0 || materiales.getMaterialMuestreo() == 0) {
            materiales.setCantidadMaximaMuestreo(0);
            materiales.setCantidadMinimaMuestreo(0);
        }
        return null;
    }

    public List iniciaLista() {
        List list = new ArrayList();
        list.add(new SelectItem("0", "-NINGUNO-"));
        return list;
    }

    public List cargarMateriales() {
        List materialesList = new ArrayList();
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select TOP 50 c.COD_CAPITULO,c.NOMBRE_CAPITULO,gr.COD_GRUPO,gr.NOMBRE_GRUPO,m.COD_MATERIAL,m.NOMBRE_MATERIAL,"
                    + " m.NOMBRE_COMERCIAL_MATERIAL,um.COD_UNIDAD_MEDIDA,um.NOMBRE_UNIDAD_MEDIDA, "
                    + " um1.COD_UNIDAD_MEDIDA COD_UNIDAD_MEDIDA_COMPRA,um1.COD_TIPO_MEDIDA as codTipoMedidaCompra,"
                    + "um1.NOMBRE_UNIDAD_MEDIDA NOMBRE_UNIDAD_MEDIDA_COMPRA,m.STOCK_MAXIMO_MATERIAL,m.STOCK_MINIMO_MATERIAL,m.STOCK_SEGURIDAD,"
                    + " m.TAMANIO_MATERIAL,m.TIPO_IMPRESION,m.ACABADO,m.CAPACIDAD,m.GRAMAJE,m.PESO_ASOCIADO, "
                    + " m.MATERIAL_PRODUCCION,m.MATERIAL_MUESTREO,m.CANTIDAD_MINIMAMUESTREO,m.CANTIDAD_MAXIMAMUESTREO,m.OBS_MATERIAL,"
                    + " e.NOMBRE_ESTADO_REGISTRO,t.COD_TIPO_MEDIDA,  m.COD_UNIDAD_MEDIDA_PESOASOCIADO,  m.COD_GRUPO_STOCK_MATERIAL "
                    + " ,er1.cod_estado_registro,er1.nombre_estado_registro,m.MOVIMIENTO_ITEM,m.premio, m.material_almacen"
                    + " from materiales m inner join grupos gr on gr.COD_GRUPO = m.COD_GRUPO "
                    + " inner join CAPITULOS c on c.COD_CAPITULO = gr.COD_CAPITULO  "
                    + " inner join ESTADOS_REFERENCIALES e on e.COD_ESTADO_REGISTRO = m.COD_ESTADO_REGISTRO "
                    + " left outer join UNIDADES_MEDIDA um on um.COD_UNIDAD_MEDIDA = m.COD_UNIDAD_MEDIDA "
                    + " left outer join UNIDADES_MEDIDA um1 on um1.COD_UNIDAD_MEDIDA = m.COD_UNIDAD_MEDIDA_COMPRA "
                    + " left outer join UNIDADES_MEDIDA um2 on um2.COD_UNIDAD_MEDIDA = m.COD_UNIDAD_MEDIDA_PESOASOCIADO "
                    + " left outer join TIPOS_MEDIDA t on t.COD_TIPO_MEDIDA = um.COD_TIPO_MEDIDA "
                    + " left outer join estados_referenciales er1 on er1.cod_estado_registro=m.cod_estado_registro"
                    + " where (m.material_almacen=1 OR m.material_almacen=0) "
                    + ((materiales.getEstadoRegistro().getCodEstadoRegistro().equals("0") || materiales.getEstadoRegistro().getCodEstadoRegistro() == null) ? ""
                            : " and m.COD_ESTADO_REGISTRO ='" + materiales.getEstadoRegistro().getCodEstadoRegistro() + "'");
            if (materiales.getGrupos().getCapitulos().getCodCapitulo() != 0) {
                consulta = consulta + " and c.cod_capitulo = '" + materiales.getGrupos().getCapitulos().getCodCapitulo() + "' ";
            }
            if (materiales.getGrupos().getCodGrupo() != 0) {
                consulta = consulta + " and gr.cod_grupo = '" + materiales.getGrupos().getCodGrupo() + "' ";
            }
            //inicio ale buscador
            if (!materiales.getNombreMaterial().equals("")) {
                consulta += " and m.NOMBRE_MATERIAL like '%" + materiales.getNombreMaterial() + "%'";
            }
            if (!materiales.getCodMaterial().equals("")) {
                consulta += " and m.COD_MATERIAL='" + materiales.getCodMaterial() + "'";
            }
            consulta += " order by m.fecha_de_creacion desc";
            //final ale buscador
            LOGGER.info("consulta" + consulta);
            ResultSet rs = null;
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = st.executeQuery(consulta);
            while (rs.next()) {
                Materiales materiales = new Materiales();
                materiales.setMovimientoItem(rs.getInt("MOVIMIENTO_ITEM"));
                materiales.getEstadoRegistro().setCodEstadoRegistro(rs.getString("cod_estado_registro"));
                materiales.getEstadoRegistro().setNombreEstadoRegistro(rs.getString("nombre_estado_registro"));
                materiales.getGrupos().getCapitulos().setCodCapitulo(rs.getInt("COD_CAPITULO"));
                materiales.getGrupos().getCapitulos().setNombreCapitulo(rs.getString("NOMBRE_CAPITULO"));
                materiales.getGrupos().setCodGrupo(rs.getInt("COD_GRUPO"));
                materiales.getGrupos().setNombreGrupo(rs.getString("NOMBRE_GRUPO"));
                materiales.setCodMaterial(rs.getString("COD_MATERIAL"));
                materiales.setNombreMaterial(rs.getString("NOMBRE_MATERIAL"));
                materiales.setNombreComercialMaterial(rs.getString("NOMBRE_COMERCIAL_MATERIAL"));
                materiales.getUnidadesMedida().setCodUnidadMedida(rs.getInt("COD_UNIDAD_MEDIDA"));
                materiales.getUnidadesMedida().setNombreUnidadMedida(rs.getString("NOMBRE_UNIDAD_MEDIDA"));
                materiales.setStockMaximoMaterial(rs.getDouble("STOCK_MAXIMO_MATERIAL"));
                materiales.setStockMinimoMaterial(rs.getDouble("STOCK_MINIMO_MATERIAL"));
                materiales.setStockSeguridad(rs.getDouble("STOCK_SEGURIDAD"));
                materiales.setTamanioMaterial(rs.getString("TAMANIO_MATERIAL"));
                materiales.setTipoImpresion(rs.getString("TIPO_IMPRESION"));
                materiales.setAcabado(rs.getString("ACABADO"));
                materiales.setCapacidad(rs.getString("CAPACIDAD"));
                materiales.setGramaje(rs.getString("GRAMAJE"));
                materiales.setPesoAsociado(rs.getFloat("PESO_ASOCIADO"));
                materiales.setMaterialProduccion(rs.getInt("MATERIAL_PRODUCCION"));
                materiales.setMaterialMuestreo(rs.getInt("MATERIAL_MUESTREO"));
                materiales.setCantidadMinimaMuestreo(rs.getFloat("CANTIDAD_MINIMAMUESTREO"));
                materiales.setCantidadMaximaMuestreo(rs.getFloat("CANTIDAD_MAXIMAMUESTREO"));
                materiales.setObsMaterial(rs.getString("OBS_MATERIAL"));
                materiales.getEstadoRegistro().setNombreEstadoRegistro(rs.getString("NOMBRE_ESTADO_REGISTRO"));
                materiales.getUnidadesMedida().getTiposMedida().setCod_tipo_medida(rs.getString("COD_TIPO_MEDIDA"));
                materiales.getUnidadesMedidaCompra().setCodUnidadMedida(rs.getInt("COD_UNIDAD_MEDIDA_COMPRA"));
                materiales.getUnidadesMedidaCompra().getTiposMedida().setCod_tipo_medida(rs.getString("codTipoMedidaCompra"));
                materiales.getUnidadesMedidaCompra().setNombreUnidadMedida(rs.getString("NOMBRE_UNIDAD_MEDIDA_COMPRA"));
                materiales.getUnidadesMedidaPesoAsociado().setCodUnidadMedida(rs.getInt("COD_UNIDAD_MEDIDA_PESOASOCIADO"));
                materiales.getGruposStockMateriales().setCodGrupoStockMaterial(rs.getInt("COD_GRUPO_STOCK_MATERIAL"));
                materiales.setPremio(rs.getInt("premio"));
                materiales.setMaterialAlmacen(rs.getInt("material_almacen"));
                materialesList.add(materiales);
            }
            rs.close();
            st.close();
            con.close();
        } catch (Exception e) {
            LOGGER.warn(e);
        }
        return materialesList;
    }

    //inicio ale buscador
    public String buscarMaterial_Action() {
        try {
            materialesList = this.cargarMateriales();
        } catch (Exception ex) {
            LOGGER.warn(ex);
        }
        return null;
    }

    // final ale buscador
    public String capitulos_change() {
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " SELECT GR.COD_GRUPO,GR.NOMBRE_GRUPO FROM GRUPOS GR WHERE GR.COD_CAPITULO = '" + materiales.getGrupos().getCapitulos().getCodCapitulo() + "' AND GR.COD_ESTADO_REGISTRO = 1 ";
            LOGGER.info("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            gruposList.clear();
            gruposList.add(new SelectItem("0", "-Seleccione-"));
            while (rs.next()) {
                gruposList.add(new SelectItem(rs.getString("COD_GRUPO"), rs.getString("NOMBRE_GRUPO")));
            }
            if (gruposList.size() == 0) {
                gruposList.add(new SelectItem("0", "-NINGUNO-"));
            }
            rs.close();
            con.close();

        } catch (Exception e) {
            LOGGER.warn(e);
        }
        return null;
    }

    public List cargarTiposMedida() {
        List tiposMedidaList = new ArrayList();
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select t.COD_TIPO_MEDIDA,t.NOMBRE_TIPO_MEDIDA from TIPOS_MEDIDA t where t.COD_ESTADO_REGISTRO = 1 ";
            LOGGER.info("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            tiposMedidaList.clear();
            tiposMedidaList.add(new SelectItem("0", "-NINGUNO-"));
            while (rs.next()) {
                tiposMedidaList.add(new SelectItem(rs.getString("COD_TIPO_MEDIDA"), rs.getString("NOMBRE_TIPO_MEDIDA")));
            }
            rs.close();
            con.close();

        } catch (Exception e) {
            LOGGER.warn(e);
        }
        return tiposMedidaList;
    }

    public List cargarUnidadesMedida(TiposMedida tiposMedida) {
        List unidadesMedidaList = new ArrayList();
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select u.COD_UNIDAD_MEDIDA,u.NOMBRE_UNIDAD_MEDIDA from UNIDADES_MEDIDA u where u.COD_TIPO_MEDIDA = '" + tiposMedida.getCod_tipo_medida() + "' "
                    + " and u.COD_ESTADO_REGISTRO = 1  ";
            LOGGER.info("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            unidadesMedidaList.clear();
            while (rs.next()) {
                unidadesMedidaList.add(new SelectItem(rs.getString("COD_UNIDAD_MEDIDA"), rs.getString("NOMBRE_UNIDAD_MEDIDA")));
            }
            if (unidadesMedidaList.size() == 0) {
                unidadesMedidaList.add(new SelectItem("0", "-NINGUNO-"));
            }
            rs.close();
            con.close();

        } catch (Exception e) {
            LOGGER.warn(e);
        }
        return unidadesMedidaList;
    }

    public List cargarUnidadesMedida() {
        List unidadesMedidaList = new ArrayList();
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select u.COD_UNIDAD_MEDIDA,u.NOMBRE_UNIDAD_MEDIDA from UNIDADES_MEDIDA u where  u.COD_ESTADO_REGISTRO = 1  ";
            LOGGER.info("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            unidadesMedidaList.clear();
            unidadesMedidaList.add(new SelectItem("0", "-NINGUNO-"));
            while (rs.next()) {
                unidadesMedidaList.add(new SelectItem(rs.getString("COD_UNIDAD_MEDIDA"), rs.getString("NOMBRE_UNIDAD_MEDIDA")));
            }
            rs.close();
            con.close();

        } catch (Exception e) {
            LOGGER.warn(e);
        }
        return unidadesMedidaList;
    }

    public List cargarGrupoStockMaterial() {
        List gruposStockMaterialList = new ArrayList();
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select gr.COD_GRUPO_STOCK_MATERIAL,gr.NOMBRE_GRUPO_STOCK_MATERIAL "
                    + " from GRUPOS_STOCK_MATERIALES gr where gr.COD_ESTADO_REGISTRO = 1   ";
            LOGGER.info("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            gruposStockMaterialList.clear();
            gruposStockMaterialList.add(new SelectItem("0", "-NINGUNO-"));
            while (rs.next()) {
                gruposStockMaterialList.add(new SelectItem(rs.getString("COD_GRUPO_STOCK_MATERIAL"), rs.getString("NOMBRE_GRUPO_STOCK_MATERIAL")));
            }
            rs.close();
            con.close();

        } catch (Exception e) {
            LOGGER.warn(e);
        }
        return gruposStockMaterialList;
    }

    public String tipoMedidaOC_change() {
        unidadesMedidaCompraList = this.cargarUnidadesMedida(materiales.getUnidadesMedidaCompra().getTiposMedida());
        return null;
    }

    public String tiposMedida_change() {
        unidadesMedidaList = this.cargarUnidadesMedida(materiales.getUnidadesMedida().getTiposMedida());

        return null;
    }

    public String guardarMaterial_action()throws SQLException {
        Connection con = null;
        registradoExitosamente = false;
        int codMaterial = 0;
        try {
            mensaje="";
            
            con = Util.openConnection(con);
            con.setAutoCommit(false);
            if(materiales.getNombreMaterial().trim().equals("")){
                mensaje="Debe colocar un nombre de material válido";
                return null;
            }
            String consulta_material="select * from materiales where nombre_material ='"+materiales.getNombreMaterial().trim()+"'";
            Statement statement_material=con.createStatement();
            ResultSet rs =statement_material.executeQuery(consulta_material);
            if(rs.next()){
                mensaje="Ya existe un material con un nombre idéntico. Favor coloque otro nombre de material.";
                return null;
            }
            if(materiales.getUnidadesMedida().getCodUnidadMedida()==0){
                mensaje="Debe seleccionar una unidad de medida.";
                return null;
            }
            if(materiales.getUnidadesMedidaCompra().getCodUnidadMedida()==0){
                mensaje="Debe seleccionar una unidad de medida de compra.";
                return null;
            }
            if(materiales.getGrupos().getCodGrupo()==0){
                mensaje="Debe seleccionar un grupo de material.";
                return null;
            }
            if(materiales.getGrupos().getCodGrupo()==12 && materiales.getCodLineaApoyoSeleccionado()==0){
                mensaje="Debe seleccionar una línea de Material de Apoyo.";
                return null;
            }
            if(materiales.getGrupos().getCodGrupo()==12 && materiales.getCodTipoApoyoSeleccionado()==0){
                mensaje="Debe seleccionar un tipo de Material de Apoyo.";
                return null;
            }
            
            materiales.setMovimientoItem(1);
            materiales.setMaterialAlmacen(1);
            
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            
            String consulta = "";
            consulta = " INSERT INTO MATERIALES(COD_GRUPO,COD_UNIDAD_MEDIDA,COD_UNIDAD_MEDIDA_COMPRA,NOMBRE_MATERIAL, STOCK_MAXIMO_MATERIAL, "
                    + "  STOCK_MINIMO_MATERIAL,  STOCK_SEGURIDAD,  FUNCION_MATERIAL,  NOMBRE_COMERCIAL_MATERIAL,  PESO_ASOCIADO,  COD_UNIDAD_MEDIDA_PESOASOCIADO,"
                    + "  CARACTERISTICAS_MATERIAL,  TAMANIO_MATERIAL,  TIPO_IMPRESION,  OBS_MATERIAL,  COD_ESTADO_REGISTRO,  ACABADO,  COLOR,  CAPACIDAD,  GRAMAJE,"
                    + "  MATERIAL_ALMACEN,  MATERIAL_MUESTREO,  CANTIDAD_MINIMAMUESTREO,  CANTIDAD_MAXIMAMUESTREO,  MATERIAL_PRODUCCION,  COD_GRUPO_STOCK_MATERIAL,"
                    + "  COSTO_UNITARIO,  CODIGO_ANTIGUO,  MOVIMIENTO_ITEM,  FECHA_DE_CREACION,  COD_PERSONAL,  TIEMPO_REPOSICION,  PREMIO,  COD_TIPO_PREMIO_PROMOCIONAL,"
                    + "  COD_MATERIAL_INSUMO,  COD_CATEGORIA,NOMBRE_CCC) "
                    + " VALUES ( '"+materiales.getGrupos().getCodGrupo() + "',  '" + materiales.getUnidadesMedida().getCodUnidadMedida() + "',"
                    + "  '" + materiales.getUnidadesMedidaCompra().getCodUnidadMedida() + "',  '" + materiales.getNombreMaterial() + "',  '" + materiales.getStockMaximoMaterial() + "',  '" + materiales.getStockMinimoMaterial() + "',"
                    + "  '" + materiales.getStockSeguridad() + "',  '" + materiales.getFuncionMaterial() + "',  '" + materiales.getNombreComercialMaterial() + "',  '" + materiales.getPesoAsociado() + "',  '" + materiales.getUnidadesMedidaPesoAsociado().getCodUnidadMedida() + "',  '" + materiales.getCaracteristicasMaterial() + "',"
                    + "  '" + materiales.getTamanioMaterial() + "',  '" + materiales.getTipoImpresion() + "',  '" + materiales.getObsMaterial() + "',  '1',  '" + materiales.getAcabado() + "',  '" + materiales.getColor() + "',  '" + materiales.getCapacidad() + "',  '" + materiales.getGramaje() + "',  '" 
                    + materiales.getMaterialAlmacen() + "',"
                    + "  '" + materiales.getMaterialMuestreo() + "',  '" + materiales.getCantidadMinimaMuestreo() + "',  '" + materiales.getCantidadMaximaMuestreo() + "',  '" + materiales.getMaterialProduccion() + "',  '" + materiales.getGruposStockMateriales().getCodGrupoStockMaterial() + "',  '" + materiales.getCostosUnitario() + "',"
                    + "  '" + materiales.getCodigoAntoguo() + "',  '" + materiales.getMovimientoItem() + "',  " +"GETDATE()" + ",  '" + usuario.getUsuarioModuloBean().getCodUsuarioGlobal() + "',  '" + materiales.getTiempoReposicion() + "',  '" + materiales.getPremio() + "',  '" + materiales.getTiposPremioPromocional().getCodTipoPremioPromocional() + "',"
                    + "  '" + materiales.getCodMaterialInsumo() + "',  '" + materiales.getCodCategoria() + "','" + materiales.getNombreMaterial() +"'); ";
            LOGGER.info("consulta: " + consulta); //se crea con estado activo
            PreparedStatement ps = con.prepareStatement(consulta, Statement.RETURN_GENERATED_KEYS);
            if (ps.executeUpdate() > 0) {
                System.out.println("se creo material");
            }
            ResultSet res = ps.getGeneratedKeys();
            res.next();
            codMaterial = res.getInt(1);
            
             //EJECUTAMOS SP EN LA CREACION
            consulta = " exec [PAA_REGISTRO_MATERIAL_LOG] " + codMaterial 
                    + " , "+ usuario.getUsuarioModuloBean().getCodUsuarioGlobal()
                    + " , 3";
            LOGGER.info("consulta PAA_REGISTRO_MATERIAL_LOG: " + consulta);  
            st.executeUpdate(consulta);
            con.commit();
            mensaje="1";
            registradoExitosamente = true;
            materialesList = this.cargarMateriales();
        } catch (Exception e) {
            con.rollback();
            LOGGER.warn(e);
            mensaje="5";   
        }
        finally{
            con.close();
        }
        //transaccion en Hermes
        Connection conMySql=null;
        try {
            
            //------------REGISTRO EN HERMES
            if(registradoExitosamente && materiales.getGrupos().getCodGrupo()==12)
            {
                conMySql=UtilMySql.openConnectionMySql();
                conMySql.setAutoCommit(false);
                StringBuilder consultaMySql=new StringBuilder("insert into material_apoyo");
                                        consultaMySql.append(" (codigo_material,descripcion_material,estado,cod_tipo_material,codigo_linea,url_imagen)");
                                        consultaMySql.append(" values( ");
                                        consultaMySql.append("'").append(codMaterial).append("'");
                                        consultaMySql.append(", '").append(materiales.getNombreMaterial()).append("'");
                                        consultaMySql.append(", 'Activo'");
                                        consultaMySql.append(", '").append(materiales.getCodTipoApoyoSeleccionado()).append("'");
                                        consultaMySql.append(", '").append(materiales.getCodLineaApoyoSeleccionado()).append("'");
                                        consultaMySql.append(", '')");
                LOGGER.info("consulta:"+consultaMySql.toString());                
                PreparedStatement pstMysql=conMySql.prepareStatement(consultaMySql.toString());
                if(pstMysql.executeUpdate()>0)LOGGER.info("se registro material_apoyo en hermes desde baco");
                conMySql.commit();
            }            
        } catch (Exception e) {
            conMySql.rollback();
            LOGGER.warn(e);
            mensaje = "AVISO IMPORTANTE: Se guardó correctamente el material en Baco, pero NO SE REGISTRÓ en el sistema Hermes. Consulte con Sistemas.";
        }
        finally{
            if(conMySql!=null)
                conMySql.close();
        }
        return null;
    }

    public String editarMaterial_action() throws SQLException {
        validarMaterialEditar();
        descripcionTransaccion ="";
        tiposMedida_change();
        tipoMedidaOC_change();
        capitulos_change();
        cargarDatosMaterialApoyo();
        //recupera datos en Hermes
        if (materiales.getGrupos().getCodGrupo()==12) {
            Connection conMySql=null;
            try {
                conMySql=UtilMySql.openConnectionMySql();
                StringBuilder consultaMySql =new StringBuilder("select cod_tipo_material, codigo_linea");
                                consultaMySql.append(" from material_apoyo");
                                consultaMySql.append(" where codigo_material = '").append(materiales.getCodMaterial()).append("'");
                LOGGER.info("consulta:"+consultaMySql.toString());   
                Statement st = conMySql.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet res = st.executeQuery(consultaMySql.toString());
                if (res.next()) {
                    materiales.setCodLineaApoyoSeleccionado(res.getInt("codigo_linea"));
                    materiales.setCodTipoApoyoSeleccionado(res.getInt("cod_tipo_material"));
                }
            } catch (Exception e) {
                LOGGER.warn(e);
            }
            finally{
                if(conMySql!=null)
                    conMySql.close();
            }
        }
        this.cargarAlmacenesExistenciaMaterial(materiales.getCodMaterial());
        return null;
    }
    
    public String editarMaterialSU_action() throws SQLException {
        existTransacciones = false;        
        descripcionTransaccion ="";
        tiposMedida_change();
        tipoMedidaOC_change();
        capitulos_change();
        cargarDatosMaterialApoyo();
        //recupera datos en Hermes
        if (materiales.getGrupos().getCodGrupo()==12) {
            Connection conMySql=null;
            try {
                conMySql=UtilMySql.openConnectionMySql();
                StringBuilder consultaMySql =new StringBuilder("select cod_tipo_material, codigo_linea");
                                consultaMySql.append(" from material_apoyo ");
                                consultaMySql.append(" where codigo_material = '").append(materiales.getCodMaterial()).append("'");
                LOGGER.info("consulta:"+consultaMySql.toString());   
                Statement st = conMySql.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet res = st.executeQuery(consultaMySql.toString());
                if (res.next()) {
                    materiales.setCodLineaApoyoSeleccionado(res.getInt("codigo_linea"));
                    materiales.setCodTipoApoyoSeleccionado(res.getInt("cod_tipo_material"));
                }
            } catch (Exception e) {
                LOGGER.warn(e);
            }
            finally{
                if(conMySql!=null)
                    conMySql.close();
            }
        }
        this.cargarAlmacenesExistenciaMaterial(materiales.getCodMaterial());
        return null;
    }

    public String agregarMaterial_action() {
        try {
            materiales = new Materiales();
            materiales.setMaterialProduccion(1);
            materiales.setMaterialAlmacen(1);
            materiales.getGruposStockMateriales().setCodGrupoStockMaterial(2); // grupo local
            materiales.setMovimientoItem(1);
            materialesBuscarList=new ArrayList<Materiales>();
            cargarDatosMaterialApoyo();
            
            
        } catch (Exception e) {
            LOGGER.warn(e);
        }
        return null;
    }

    public String guardarEdicionMaterial_action() throws SQLException {
        mensaje = "";
        registradoExitosamente = false;
        //validar
        
        if (descripcionTransaccion.equals("")) {
            mensaje="Ingrese un motivo u observación.";
            return null;
        }
        Materiales materialAnterior = new Materiales();
        Connection con = null;        
        try {   
            con = Util.openConnection(con);
            StringBuilder consulta;
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            
            //---------Recuperamos datos del material anterior
            consulta = new StringBuilder("Select COD_GRUPO ");
                            consulta.append(" from materiales m ");
                            consulta.append(" where m.cod_material = '").append(materiales.getCodMaterial()).append("'");
                            consulta.append("");
            ResultSet res = st.executeQuery(consulta.toString());
            if (res.next()) {
                materialAnterior.getGrupos().setCodGrupo(res.getInt("COD_GRUPO"));
            }
            //Registramos material
            
            consulta = new StringBuilder(" UPDATE MATERIALES");
            consulta.append(" SET   COD_GRUPO = '").append(materiales.getGrupos().getCodGrupo()).append("' ,  ");
            consulta.append("  COD_UNIDAD_MEDIDA = '").append(materiales.getUnidadesMedida().getCodUnidadMedida()).append("',");
            consulta.append("  COD_UNIDAD_MEDIDA_COMPRA = '").append(materiales.getUnidadesMedidaCompra().getCodUnidadMedida()).append("',  ");
            consulta.append("  NOMBRE_MATERIAL = '").append(materiales.getNombreMaterial()).append("',");
            consulta.append("  STOCK_MAXIMO_MATERIAL = '").append(materiales.getStockMaximoMaterial()).append("',  ");
            consulta.append("  STOCK_MINIMO_MATERIAL = '").append(materiales.getStockMinimoMaterial()).append("',");
            consulta.append("  STOCK_SEGURIDAD = '").append(materiales.getStockSeguridad()).append("', ");
            consulta.append("  FUNCION_MATERIAL = '").append(materiales.getFuncionMaterial()).append("',");
            consulta.append("  NOMBRE_COMERCIAL_MATERIAL = '").append(materiales.getNombreComercialMaterial()).append("',");
            consulta.append("  PESO_ASOCIADO = '").append(materiales.getPesoAsociado()).append("',");
            consulta.append("  COD_UNIDAD_MEDIDA_PESOASOCIADO = '").append(materiales.getUnidadesMedidaPesoAsociado().getCodUnidadMedida()).append("', ");
            consulta.append("  CARACTERISTICAS_MATERIAL = '").append(materiales.getCaracteristicasMaterial()).append("',");
            consulta.append("  TAMANIO_MATERIAL = '").append(materiales.getTamanioMaterial()).append("',");
            consulta.append("  TIPO_IMPRESION = '").append(materiales.getTipoImpresion()).append("',");
            consulta.append("  OBS_MATERIAL = '").append(materiales.getObsMaterial()).append("',");
            consulta.append("  COD_ESTADO_REGISTRO = '").append(materiales.getEstadoRegistro().getCodEstadoRegistro()).append("',");
            consulta.append("  ACABADO = '").append(materiales.getAcabado()).append("',");
            consulta.append("  COLOR = '").append(materiales.getColor()).append("',  ");
            consulta.append("  CAPACIDAD = '").append(materiales.getCapacidad()).append("',");
            consulta.append("  GRAMAJE = '").append(materiales.getGramaje()).append("',  ");
            consulta.append("  MATERIAL_MUESTREO = '").append(materiales.getMaterialMuestreo()).append("',"); //MATERIAL_ALMACEN = :MATERIAL_ALMACEN,
            consulta.append("  CANTIDAD_MINIMAMUESTREO = '").append(materiales.getCantidadMinimaMuestreo()).append("',  ");
            consulta.append("  CANTIDAD_MAXIMAMUESTREO = '").append(materiales.getCantidadMaximaMuestreo()).append("',");
            consulta.append("  MATERIAL_PRODUCCION = '").append(materiales.getMaterialProduccion()).append("',  ");
            consulta.append("  COD_GRUPO_STOCK_MATERIAL = '").append(materiales.getGruposStockMateriales().getCodGrupoStockMaterial()).append("',");
            consulta.append("  COSTO_UNITARIO = '").append(materiales.getCostosUnitario()).append("',  ");
            consulta.append("  CODIGO_ANTIGUO = '").append(materiales.getCodigoAntoguo()).append("'");
            consulta.append(" ,MOVIMIENTO_ITEM='").append(materiales.getMovimientoItem()).append("'");
            consulta.append(" ,PREMIO='").append(materiales.getPremio()).append("'");
            consulta.append(" ,MATERIAL_ALMACEN='").append(materiales.getMaterialAlmacen()).append("'");
            
            consulta.append(" WHERE   COD_MATERIAL = '").append(materiales.getCodMaterial()).append("' ; ");
            LOGGER.info("consulta" + consulta.toString());
            con.setAutoCommit(false);
            // Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            st.executeUpdate(consulta.toString());
            
            //EJECUTAMOS SP EN LA EDICION
            consulta = new StringBuilder(" exec [PAA_REGISTRO_MATERIAL_LOG] ").append(materiales.getCodMaterial()); 
                    consulta.append(" , ").append(usuario.getUsuarioModuloBean().getCodUsuarioGlobal());
                    consulta.append(" , 1");
                    consulta.append(" , '").append(descripcionTransaccion).append("'");
            LOGGER.info("consulta PAA_REGISTRO_MATERIAL_LOG: " + consulta.toString());            
            st.executeUpdate(consulta.toString());
            
            con.commit();
            mensaje = "Registrado correctamente.";
            registradoExitosamente = true;

        } catch (Exception e) {
            con.rollback();
            mensaje = "Ocurrio un error al momento de guardar la edicion,intente de nuevo";
            LOGGER.warn(e);
            
        }finally{
            con.close();
        }
        //transaccion en Hermes
        Connection conMySql=null;
        try {
            
            //------------REGISTRO EN HERMES
            
            if (registradoExitosamente && materiales.getGrupos().getCodGrupo()==12) {
                
                conMySql=UtilMySql.openConnectionMySql();
                conMySql.setAutoCommit(false);
                StringBuilder consultaMySql;
                if(materialAnterior.getGrupos().getCodGrupo() != 12 ) //Si cambio de grupo a Material de Apoyo
                {
                    consultaMySql=new StringBuilder("insert into material_apoyo");
                                            consultaMySql.append(" (codigo_material,descripcion_material,estado,cod_tipo_material,codigo_linea,url_imagen)");
                                            consultaMySql.append(" values( ");
                                            consultaMySql.append("'").append(materiales.getCodMaterial()).append("'");
                                            consultaMySql.append(", '").append(materiales.getNombreMaterial()).append("'");
                                            consultaMySql.append(", 'Activo'");
                                            consultaMySql.append(", '").append(materiales.getCodTipoApoyoSeleccionado()).append("'");
                                            consultaMySql.append(", '").append(materiales.getCodLineaApoyoSeleccionado()).append("'");
                                            consultaMySql.append(", '')");
                    LOGGER.info("consulta:"+consultaMySql.toString());   
                }  
                else { //Si continua siendo Material de Apoyo
                    consultaMySql=new StringBuilder("update material_apoyo");
                                consultaMySql.append(" set descripcion_material = '").append(materiales.getNombreMaterial()).append("'");
                                consultaMySql.append(", cod_tipo_material = '").append(materiales.getCodTipoApoyoSeleccionado()).append("'");
                                consultaMySql.append(", codigo_linea = '").append(materiales.getCodLineaApoyoSeleccionado()).append("'");
                                consultaMySql.append(" where codigo_material = '").append(materiales.getCodMaterial()).append("'");
                    LOGGER.info("consulta:"+consultaMySql.toString());   
                }
                PreparedStatement pstMysql=conMySql.prepareStatement(consultaMySql.toString());
                if(pstMysql.executeUpdate()>0)LOGGER.info("se registro material_apoyo en hermes desde baco");
                conMySql.commit();
            }
            
        } catch (Exception e) {
            conMySql.rollback();
            LOGGER.warn(e);
            mensaje = "AVISO IMPORTANTE: Se guardó correctamente el material en Baco, pero NO SE REGISTRÓ en el sistema Hermes. Consulte con Sistemas.";
        }
        finally{
            if(conMySql!=null)
                conMySql.close();
        }
        if (registradoExitosamente) {
            materialesList = this.cargarMateriales();
        }
        return null;
    }
    public boolean validarMaterial() throws SQLException {
        existTransacciones=false;
        mensaje="";
        Connection con = null;        
        try {
            con = Util.openConnection(con);
            mensaje = "El material no puede ser modificado ni eliminado porque esta siendo usado:";
            
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            
            String consulta = " select count(*) as CANTIDAD from ingresos_almacen_detalle where cod_material= '" + materiales.getCodMaterial() + "' ";
            LOGGER.info("consulta: " + consulta);
            ResultSet rs = st.executeQuery(consulta);
            rs.next();
            if (rs.getInt("CANTIDAD") > 0) {
                mensaje += "\n "+rs.getInt("CANTIDAD")+" veces en ingresos de almacen.";
                existTransacciones=true;
            }
            consulta = " select count(*) as CANTIDAD from salidas_almacen_detalle where cod_material= '" + materiales.getCodMaterial() + "' ";
            LOGGER.info("consulta: " + consulta);
            rs = st.executeQuery(consulta);
            rs.next();
            if (rs.getInt("CANTIDAD") > 0) {
                mensaje += "\n "+rs.getInt("CANTIDAD")+" veces en salidas de almacen.";
                existTransacciones=true;
            }
            consulta = " select count(*) as CANTIDAD from solicitudes_compra_detalle where cod_material= '" + materiales.getCodMaterial() + "' ";
            LOGGER.info("consulta: " + consulta);
            rs = st.executeQuery(consulta);
            rs.next();
            if (rs.getInt("CANTIDAD") > 0) {
                mensaje += "\n "+rs.getInt("CANTIDAD")+" veces en solicitud de Compra.";
                existTransacciones=true;
            }
            consulta = " select count(*) as CANTIDAD from cotizacion_detalle where cod_material= '" + materiales.getCodMaterial() + "' ";
            LOGGER.info("consulta: " + consulta);
            rs = st.executeQuery(consulta);
            rs.next();
            if (rs.getInt("CANTIDAD") > 0) {
                mensaje += "\n "+rs.getInt("CANTIDAD")+" veces en cotizacion.";
                existTransacciones=true;
            }
            consulta = " select count(*) as CANTIDAD from ordenes_compra_detalle where cod_material= '" + materiales.getCodMaterial() + "' ";
            LOGGER.info("consulta: " + consulta);
            rs = st.executeQuery(consulta);
            rs.next();
            if (rs.getInt("CANTIDAD") > 0) {
                mensaje += "\n "+rs.getInt("CANTIDAD")+" veces en ordenes de compra.";
                existTransacciones=true;
            }
            consulta = " select count(*) as CANTIDAD from ordenes_compra_detalle_auxiliar where cod_material= '" + materiales.getCodMaterial() + "' ";
            LOGGER.info("consulta: " + consulta);
            rs = st.executeQuery(consulta);
            rs.next();
            if (rs.getInt("CANTIDAD") > 0) {
                mensaje += "\n "+rs.getInt("CANTIDAD")+" veces en proceso de ordenes de compra.";
                existTransacciones=true;
            }            
            consulta = " select count(*) as CANTIDAD from permisos_aprobacion_compra where cod_material= '" + materiales.getCodMaterial() + "' ";
            LOGGER.info("consulta: " + consulta);
            rs = st.executeQuery(consulta);
            rs.next();
            if (rs.getInt("CANTIDAD") > 0) {
                mensaje += "\n "+rs.getInt("CANTIDAD")+" veces en permiso de compra.";
                existTransacciones=true;
            }    
            consulta = " select count(*) as CANTIDAD from secciones_detalle where cod_material= '" + materiales.getCodMaterial() + "' ";
            LOGGER.info("consulta: " + consulta);
            rs = st.executeQuery(consulta);
            rs.next();
            if (rs.getInt("CANTIDAD") > 0) {
                mensaje += "\n "+rs.getInt("CANTIDAD")+" veces en secciones.";
                existTransacciones=true;
            }    
            consulta = " select count(*) as CANTIDAD from secciones_detalleauxiliar where cod_material= '" + materiales.getCodMaterial() + "' ";
            LOGGER.info("consulta: " + consulta);
            rs = st.executeQuery(consulta);
            rs.next();
            if (rs.getInt("CANTIDAD") > 0) {
                mensaje += "\n "+rs.getInt("CANTIDAD")+" veces en secciones.";
                existTransacciones=true;
            }        
            consulta = "select count(*) as CANTIDAD from FORMULA_MAESTRA_DETALLE_MP_VERSION mpv where mpv.COD_MATERIAL = "+materiales.getCodMaterial();
            LOGGER.info("consulta: " + consulta);
            rs = st.executeQuery(consulta);
            rs.next();
            if (rs.getInt("CANTIDAD") > 0) {
                mensaje += "\n "+rs.getInt("CANTIDAD")+" veces en formulas maestras de materia prima.";
                existTransacciones=true;
            }
            
            consulta = "select count(*) as CANTIDAD from FORMULA_MAESTRA_DETALLE_EP_VERSION epv where epv.COD_MATERIAL = "+materiales.getCodMaterial();
            LOGGER.info("consulta: " + consulta);
            rs = st.executeQuery(consulta);
            rs.next();
            if (rs.getInt("CANTIDAD") > 0) {
                mensaje += "\n "+rs.getInt("CANTIDAD")+" veces en formulas maestras de empaque primario.";
                existTransacciones=true;
            }
            
            consulta = "select count(*) as CANTIDAD from FORMULA_MAESTRA_DETALLE_ES_VERSION esv where esv.COD_MATERIAL = "+materiales.getCodMaterial();
            LOGGER.info("consulta: " + consulta);
            rs = st.executeQuery(consulta);
            rs.next();
            if (rs.getInt("CANTIDAD") > 0) {
                mensaje += "\n "+rs.getInt("CANTIDAD")+" veces en formulas maestras de empaque secundario.";
                existTransacciones=true;
            }
            
            consulta = "select count(*) as CANTIDAD from FORMULA_MAESTRA_DETALLE_MR_VERSION mrv where mrv.COD_MATERIAL = "+materiales.getCodMaterial();
            LOGGER.info("consulta: " + consulta);
            rs = st.executeQuery(consulta);
            rs.next();
            if (rs.getInt("CANTIDAD") > 0) {
                mensaje += "\n "+rs.getInt("CANTIDAD")+" veces en formulas maestras de material reactivo.";
                existTransacciones=true;
            }
                        
        } catch (Exception e) {            
            LOGGER.warn(e);
            mensaje= "Ocurrio un error.";
        } finally{
            con.close();
        }
        return !existTransacciones;
    
    }
    
    public boolean validarMaterialEditar() throws SQLException {
        // Esta funcion tiene menos validaciones a petición de Karina el 26/10/2017
        existTransacciones=false;
        mensaje="";
        Connection con = null;        
        try {
            con = Util.openConnection(con);
            mensaje = "El material no puede ser modificado ni eliminado porque esta siendo usado:";
            
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            
            String consulta = " select count(*) as CANTIDAD from ingresos_almacen_detalle where cod_material= '" + materiales.getCodMaterial() + "' ";
            LOGGER.info("consulta: " + consulta);
            ResultSet rs = st.executeQuery(consulta);
            rs.next();
            if (rs.getInt("CANTIDAD") > 0) {
                mensaje += "\n "+rs.getInt("CANTIDAD")+" veces en ingresos de almacen.";
                existTransacciones=true;
            }            
            consulta = " select count(*) as CANTIDAD from ORDENES_COMPRA_DETALLE ocd inner join ORDENES_COMPRA oc on oc.COD_ORDEN_COMPRA = ocd.COD_ORDEN_COMPRA where oc.COD_ESTADO_COMPRA !=1 and ocd.cod_material= '" + materiales.getCodMaterial() + "' ";
            LOGGER.info("consulta: " + consulta);
            rs = st.executeQuery(consulta);
            rs.next();
            if (rs.getInt("CANTIDAD") > 0) {
                mensaje += "\n "+rs.getInt("CANTIDAD")+" veces en ordenes de compra.";
                existTransacciones=true;
            }         
            
            consulta = "select count(*) as CANTIDAD from FORMULA_MAESTRA_DETALLE_MP_VERSION mpv where mpv.COD_MATERIAL = "+materiales.getCodMaterial();
            LOGGER.info("consulta: " + consulta);
            rs = st.executeQuery(consulta);
            rs.next();
            if (rs.getInt("CANTIDAD") > 0) {
                mensaje += "\n "+rs.getInt("CANTIDAD")+" veces en formulas maestras de materia prima.";
                existTransacciones=true;
            }
            
            consulta = "select count(*) as CANTIDAD from FORMULA_MAESTRA_DETALLE_EP_VERSION epv where epv.COD_MATERIAL = "+materiales.getCodMaterial();
            LOGGER.info("consulta: " + consulta);
            rs = st.executeQuery(consulta);
            rs.next();
            if (rs.getInt("CANTIDAD") > 0) {
                mensaje += "\n "+rs.getInt("CANTIDAD")+" veces en formulas maestras de empaque primario.";
                existTransacciones=true;
            }
            
            consulta = "select count(*) as CANTIDAD from FORMULA_MAESTRA_DETALLE_ES_VERSION esv where esv.COD_MATERIAL = "+materiales.getCodMaterial();
            LOGGER.info("consulta: " + consulta);
            rs = st.executeQuery(consulta);
            rs.next();
            if (rs.getInt("CANTIDAD") > 0) {
                mensaje += "\n "+rs.getInt("CANTIDAD")+" veces en formulas maestras de empaque secundario.";
                existTransacciones=true;
            }
            
            consulta = "select count(*) as CANTIDAD from FORMULA_MAESTRA_DETALLE_MR_VERSION mrv where mrv.COD_MATERIAL = "+materiales.getCodMaterial();
            LOGGER.info("consulta: " + consulta);
            rs = st.executeQuery(consulta);
            rs.next();
            if (rs.getInt("CANTIDAD") > 0) {
                mensaje += "\n "+rs.getInt("CANTIDAD")+" veces en formulas maestras de material reactivo.";
                existTransacciones=true;
            }
                        
        } catch (Exception e) {            
            LOGGER.warn(e);
            mensaje= "Ocurrio un error.";
        } finally{
            con.close();
        }
        return !existTransacciones;
    
    }

    public String eliminarMaterial_action() throws SQLException {        
        if (!validarMaterial()) {
            return null;
        }        
        descripcionTransaccion ="";   
        return null;
    }
    public String guardarEliminarMaterial_action() throws SQLException {
        registradoExitosamente=false;
        mensaje = "";
        //validar
        if (!validarMaterial()) {
            return null;
        }
        if (descripcionTransaccion.equals("")) {
            mensaje="Ingrese un motivo u observación.";
            return null;
        }
        
        Connection con = null;
        try {
            con = Util.openConnection(con);   
            con.setAutoCommit(false);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);    
                    
            //EJECUTAMOS SP EN LA EDICION
            String consulta = " exec [PAA_REGISTRO_MATERIAL_LOG] " + materiales.getCodMaterial()
                    + " , " + usuario.getUsuarioModuloBean().getCodUsuarioGlobal()
                    + " , 2"
                    + " , '" + descripcionTransaccion + "'";
            LOGGER.info("consulta PAA_REGISTRO_MATERIAL_LOG: " + consulta);
            st.executeUpdate(consulta);
            //Eliminamos
            consulta = " delete from materiales where cod_material = '" + materiales.getCodMaterial() + "' ";
            LOGGER.info("consulta " + consulta);
            st.executeUpdate(consulta);
            
            con.commit();    
            
            mensaje = "Registrado correctamente.";
            registradoExitosamente=true;
            
        } catch (Exception e) {
            con.rollback();
            LOGGER.warn(e);
            mensaje = "Ocurrio un error.";
        }
        finally{
            con.close();
        }
        if (registradoExitosamente) {
            materialesList = this.cargarMateriales();
        }
        return null;
    }
    
     //----------Mostrar PDF Material
    public String verReporteMaterialLog_action(){
        try {
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            Map<String, Object> sessionMap = externalContext.getSessionMap();
            sessionMap.put("material",materiales);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    private boolean existTransacciones=false;

    public boolean isExistTransacciones() {
        return existTransacciones;
    }

    public void setExistTransacciones(boolean existTransacciones) {
        this.existTransacciones = existTransacciones;
    }
    public String buscarMaterial_action() {
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select m.COD_MATERIAL,m.NOMBRE_MATERIAL,gr.cod_grupo,gr.NOMBRE_GRUPO,cap.cod_capitulo,cap.NOMBRE_CAPITULO,"
                    + " m.COD_UNIDAD_MEDIDA,u1.ABREVIATURA ABREVIATURA1 ,m.COD_UNIDAD_MEDIDA_COMPRA,u2.ABREVIATURA ABREVIATURA2  "
                    + " from MATERIALES m  "
                    + " inner join GRUPOS gr on gr.COD_GRUPO = m.COD_GRUPO "
                    + " inner join CAPITULOS cap on cap.COD_CAPITULO = gr.COD_CAPITULO"
                    + " inner join UNIDADES_MEDIDA u1 on u1.COD_UNIDAD_MEDIDA = m.COD_UNIDAD_MEDIDA "
                    + " inner join UNIDADES_MEDIDA u2 on u2.COD_UNIDAD_MEDIDA = m.COD_UNIDAD_MEDIDA_COMPRA  "
                    + " where m.NOMBRE_MATERIAL like '%" + materiales.getNombreMaterial() + "%' ";
        
            LOGGER.info("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            materialesBuscarList.clear();
            while (rs.next()) {
                Materiales materialesItem = new Materiales();
                materialesItem.setCodMaterial(rs.getString("COD_MATERIAL"));
                materialesItem.setNombreMaterial(rs.getString("NOMBRE_MATERIAL"));
                materialesItem.getGrupos().setCodGrupo(rs.getInt("COD_GRUPO"));
                materialesItem.getGrupos().setNombreGrupo(rs.getString("NOMBRE_GRUPO"));
                materialesItem.getGrupos().getCapitulos().setNombreCapitulo(rs.getString("NOMBRE_CAPITULO"));
                materialesItem.getGrupos().getCapitulos().setCodCapitulo(rs.getInt("COD_CAPITULO"));
                materialesItem.getUnidadesMedida().setCodUnidadMedida(rs.getInt("COD_UNIDAD_MEDIDA"));
                materialesItem.getUnidadesMedida().setAbreviatura(rs.getString("ABREVIATURA1"));
                materialesItem.getUnidadesMedidaCompra().setCodUnidadMedida(rs.getInt("COD_UNIDAD_MEDIDA_COMPRA"));
                materialesItem.getUnidadesMedidaCompra().setAbreviatura(rs.getString("ABREVIATURA2"));

                materialesBuscarList.add(materialesItem);
            }
            rs.close();
            con.close();

        } catch (Exception e) {
            LOGGER.warn(e);
        }
        return null;
    }
    
    public String cargarPermisosEspeciales() {
        System.out.println("entra cargarPermisosEspeciales");
        permisoEditarDirectamenteMaterial= false;
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select cp.COD_TIPO_PERMISO_ESPECIAL_BACO "
                    +" from CONFIGURACION_PERMISO_ESPECIAL_BACO cp"
                    +" where cp.COD_TIPO_PERMISO_ESPECIAL_BACO = 19 "
                    +" and cp.COD_PERSONAL = '"+usuario.getUsuarioModuloBean().getCodUsuarioGlobal()+"' "
                    +"";
            LOGGER.info("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            if(rs.next()) {
                System.out.println("tiene permiso");
                permisoEditarDirectamenteMaterial= true;
            }
            rs.close();
            con.close();

        } catch (Exception e) {
            LOGGER.warn(e);
        }
        return null;
    }
    
    public String cargarDatosMaterialApoyo() {
        try {
            Connection con = null;
            con = Util.openConnection(con);
            String consulta = " select l.COD_LINEAINVENTARIOS, l.NOMBRE_LINEAINVENTARIOS from LINEAS_INVENTARIOS_HERMES l order by l.NOMBRE_LINEAINVENTARIOS asc";
            LOGGER.info("consulta " + consulta);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(consulta);
            lineasApoyoList.clear();
            lineasApoyoList.add(new SelectItem("0", "-NINGUNO-"));
            while (rs.next()) {
                lineasApoyoList.add(new SelectItem(rs.getString("COD_LINEAINVENTARIOS"), rs.getString("NOMBRE_LINEAINVENTARIOS")));
            }
            
            consulta = " select t.COD_TIPOMATERIALAPOYO, t.NOMBRE_TIPOMATERIAL from TIPO_MATERIAL_APOYO t order by t.NOMBRE_TIPOMATERIAL asc";
            LOGGER.info("consulta " + consulta);
            rs = st.executeQuery(consulta);
            tiposApoyoList.clear();
            tiposApoyoList.add(new SelectItem("0", "-NINGUNO-"));
            while (rs.next()) {
                tiposApoyoList.add(new SelectItem(rs.getString("COD_TIPOMATERIALAPOYO"), rs.getString("NOMBRE_TIPOMATERIAL")));
            }
            rs.close();
            con.close();

        } catch (Exception e) {
            LOGGER.warn(e);
        }
        return null;
    }
    public String grupo_onchage() {
        return null;
    }
    
    private List<Materiales>materialesBuscarList=new ArrayList<Materiales>();
    //<editor-fold defaultstate="collapsed" desc="getter and setter">

    
        public List<Materiales> getMaterialesBuscarList() {
            return materialesBuscarList;
        }

        public List<Almacenes> getAlmacenesExistenciaMaterial() {
            return almacenesExistenciaMaterial;
        }

        public void setAlmacenesExistenciaMaterial(List<Almacenes> almacenesExistenciaMaterial) {
            this.almacenesExistenciaMaterial = almacenesExistenciaMaterial;
        }

            public void setMaterialesBuscarList(List<Materiales> materialesBuscarList) {
            this.materialesBuscarList = materialesBuscarList;
        }

        public String getDescripcionTransaccion() {
            return descripcionTransaccion;
        }

        public void setDescripcionTransaccion(String descripcionTransaccion) {
            this.descripcionTransaccion = descripcionTransaccion;
        }

        public boolean isRegistradoExitosamente() {
            return registradoExitosamente;
        }

        public void setRegistradoExitosamente(boolean registradoExitosamente) {
            this.registradoExitosamente = registradoExitosamente;
        }

        public boolean isPermisoEditarDirectamenteMaterial() {
            return permisoEditarDirectamenteMaterial;
        }

        public void setPermisoEditarDirectamenteMaterial(boolean permisoEditarDirectamenteMaterial) {
            this.permisoEditarDirectamenteMaterial = permisoEditarDirectamenteMaterial;
        }

        public List getLineasApoyoList() {
            return lineasApoyoList;
        }

        public void setLineasApoyoList(List lineasApoyoList) {
            this.lineasApoyoList = lineasApoyoList;
        }

        public List getTiposApoyoList() {
            return tiposApoyoList;
        }

        public void setTiposApoyoList(List tiposApoyoList) {
            this.tiposApoyoList = tiposApoyoList;
        }
        public List getMaterialesList() {
            return materialesList;
        }

        public void setMaterialesList(List materialesList) {
            this.materialesList = materialesList;
        }

        public List getCapitulosList() {
            return capitulosList;
        }

        public void setCapitulosList(List capitulosList) {
            this.capitulosList = capitulosList;
        }

        public List getGruposList() {
            return gruposList;
        }

        public void setGruposList(List gruposList) {
            this.gruposList = gruposList;
        }

        public Materiales getMateriales() {
            return materiales;
        }

        public void setMateriales(Materiales materiales) {
            this.materiales = materiales;
        }

        public List getTiposMedidaList() {
            return tiposMedidaList;
        }

        public void setTiposMedidaList(List tiposMedidaList) {
            this.tiposMedidaList = tiposMedidaList;
        }

        public List getUnidadesMedidaCompraList() {
            return unidadesMedidaCompraList;
        }

        public void setUnidadesMedidaCompraList(List unidadesMedidaCompraList) {
            this.unidadesMedidaCompraList = unidadesMedidaCompraList;
        }

        public List getUnidadesMedidaList() {
            return unidadesMedidaList;
        }

        public void setUnidadesMedidaList(List unidadesMedidaList) {
            this.unidadesMedidaList = unidadesMedidaList;
        }

        public List getUnidadMedidaPesoAsociadoList() {
            return unidadMedidaPesoAsociadoList;
        }

        public void setUnidadMedidaPesoAsociadoList(List unidadMedidaPesoAsociadoList) {
            this.unidadMedidaPesoAsociadoList = unidadMedidaPesoAsociadoList;
        }

        public List getGruposStockMaterialesList() {
            return gruposStockMaterialesList;
        }

        public void setGruposStockMaterialesList(List gruposStockMaterialesList) {
            this.gruposStockMaterialesList = gruposStockMaterialesList;
        }

        public String getMensaje() {
            return mensaje;
        }

        public void setMensaje(String mensaje) {
            this.mensaje = mensaje;
        }

        public List getEstadosRegistroList() {
            return estadosRegistroList;
        }

        public void setEstadosRegistroList(List estadosRegistroList) {
            this.estadosRegistroList = estadosRegistroList;
        }
    
    //</editor-fold>
}
