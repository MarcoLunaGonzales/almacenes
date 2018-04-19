/*
 * ManagedTipoCliente.java
 * Created on 19 de febrero de 2008, 16:50
 */
package com.cofar.web;

import com.cofar.bean.FormulaMaestra;
import com.cofar.bean.ComponentesProd;
import com.cofar.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import org.joda.time.DateTime;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.richfaces.component.html.HtmlDataTable;

/**
 *
 *  @author Wilmer Manzaneda Chavez
 *  @company COFAR
 */
public class ManagedFormulaMaestra {

    /** Creates a new instance of ManagedTipoCliente */
    private FormulaMaestra formulaMaestrabean = new FormulaMaestra();
    private FormulaMaestra formulaMaestrabeanReferencia = new FormulaMaestra(); //para guardar los datos antes de actualizar
    private List componentesProd = new ArrayList();
    private List formulaMaestraList = new ArrayList();
    private List formulaMaestraEliminar = new ArrayList();
    private List formulaMaestraNoEliminar = new ArrayList();
    private List areasEmpresaList = new ArrayList();
    private Connection con;
    private int cantidadeliminar = 0;
    private String codigo = "";
    private List lista = new ArrayList();
    private List estadoRegistro = new ArrayList();
    private boolean swEliminaSi;
    private boolean swEliminaNo;
    private HtmlDataTable formulaMaestraDataTable = new HtmlDataTable();

    public ManagedFormulaMaestra() {

        cargarFormulaMaestra();

    }

    public String getObtenerCodigo() {

        //String cod=Util.getParameter("codigo");
        formulaMaestraList.clear();
        cargarFormulaMaestra();
        return "";

    }

    public void cargarEstadoRegistro(String codigo, FormulaMaestra bean) {
        try {
            setCon(Util.openConnection(getCon()));
            String sql = "select ecp.COD_ESTADO_COMPPROD, ecp.NOMBRE_ESTADO_COMPPROD from ESTADOS_COMPPROD ecp where ecp.COD_ESTADO_REGISTRO = 1";
            ResultSet rs = null;

            Statement st = getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            if (!codigo.equals("")) {
                sql += " and cod_estado_registro=" + codigo;
                System.out.println("update:" + sql);
                rs = st.executeQuery(sql);
                if (rs.next()) {
                    bean.getEstadoRegistro().setCodEstadoRegistro(rs.getString(1));
                    bean.getEstadoRegistro().setNombreEstadoRegistro(rs.getString(2));
                }
            } else {
                getEstadoRegistro().clear();
                rs = st.executeQuery(sql);
                while (rs.next()) {
                    getEstadoRegistro().add(new SelectItem(rs.getString(1), rs.getString(2)));
                }
            }
            if (rs != null) {
                rs.close();
                st.close();
                rs = null;
                st = null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getCodigoCliente() {
        String codigo = "1";
        try {
            setCon(Util.openConnection(getCon()));
            String sql = "select max(cod_formula_maestra)+1 from formula_maestra";
            PreparedStatement st = getCon().prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                codigo = rs.getString(1);
            }
            if (codigo == null) {
                codigo = "1";
            }

            getFormulaMaestrabean().setCodFormulaMaestra(codigo);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void cargarFormulaMaestra() {
        try {
            String sql = "select fm.cod_formula_maestra,fm.cod_compprod,fm.cantidad_lote,fm.estado_sistema,";
            sql += " cp.COD_ESTADO_COMPPROD,er.NOMBRE_ESTADO_COMPPROD,cp.nombre_prod_semiterminado";
            sql += " from formula_maestra fm, ESTADOS_COMPPROD er, componentes_prod cp ";
            sql += " WHERE fm.estado_sistema=1 and fm.cod_compprod=cp.cod_compprod and cp.COD_ESTADO_COMPPROD=er.COD_ESTADO_COMPPROD";
            if (!getFormulaMaestrabean().getEstadoRegistro().getCodEstadoRegistro().equals("") && !getFormulaMaestrabean().getEstadoRegistro().getCodEstadoRegistro().equals("3")) {
                sql += " AND cp.COD_ESTADO_COMPPROD=" + getFormulaMaestrabean().getEstadoRegistro().getCodEstadoRegistro();
            }
            if (!getFormulaMaestrabean().getComponentesProd().getAreasEmpresa().getCodAreaEmpresa().equals("") && !getFormulaMaestrabean().getComponentesProd().getAreasEmpresa().getCodAreaEmpresa().equals("0")) {
                sql += " and cp.cod_area_empresa=" + getFormulaMaestrabean().getComponentesProd().getAreasEmpresa().getCodAreaEmpresa();
            }
            sql += " order by cp.nombre_prod_semiterminado asc";
            System.out.println("sql:" + sql);
            setCon(Util.openConnection(getCon()));
            Statement st = getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(sql);
            rs.last();
            int rows = rs.getRow();
            formulaMaestraList.clear();
            rs.first();
            String cod = "";
            for (int i = 0; i < rows; i++) {
                FormulaMaestra bean = new FormulaMaestra();
                bean.setCodFormulaMaestra(rs.getString(1));
                bean.getComponentesProd().setCodCompprod(rs.getString(2));
                double cantidad = rs.getDouble(3);
                cantidad = redondear(cantidad, 3);
                NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
                DecimalFormat form = (DecimalFormat) nf;
                form.applyPattern("#,#00.0#");
                bean.setCantidadLote(form.format(cantidad));
                bean.getEstadoRegistro().setCodEstadoRegistro(rs.getString(5));
                bean.getEstadoRegistro().setNombreEstadoRegistro(rs.getString(6));
                bean.getComponentesProd().setNombreProdSemiterminado(rs.getString(7));
                formulaMaestraList.add(bean);
                rs.next();
            }

            if (rs != null) {
                rs.close();
                st.close();
            }
            cargarAreasEmpresa("", null);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
   public double redondear(double numero, int decimales) {
        return Math.round(numero * Math.pow(10, decimales)) / Math.pow(10, decimales);
    }

    public void cargarComponentesProd() {
        try {
            String sql = "select c.cod_compprod,c.nombre_prod_semiterminado" +
                    " from componentes_prod c,productos p" +
                    " where p.cod_prod=c.cod_prod and p.cod_estado_prod=1 " +
                    " order by nombre_prod_semiterminado asc";
            System.out.println("sql:" + sql);
            setCon(Util.openConnection(getCon()));
            Statement st = getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(sql);
            rs.last();
            int rows = rs.getRow();
            getComponentesProd().clear();
            rs.first();
            String cod = "";
            for (int i = 0; i < rows; i++) {
                //ComponentesProd bean=new ComponentesProd();
                //bean.setCodCompprod(rs.getString(1));
                //bean.setNombreProdSemiterminado(rs.getString(2));
                //componentesProd.add(bean);
                String nomCompProd=rs.getString(2);
                if(nomCompProd==null){
                    nomCompProd="";
                }
                componentesProd.add(new SelectItem(rs.getString(1), nomCompProd));
                rs.next();
            }

            if (rs != null) {
                rs.close();
                st.close();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String actionEditar() {
        System.out.println("Entro Editar");
        cargarEstadoRegistro("", null);
        cargarComponentesProd();
        Iterator i = getFormulaMaestraList().iterator();
        while (i.hasNext()) {
            FormulaMaestra bean = (FormulaMaestra) i.next();
            if (bean.getChecked().booleanValue()) {
                formulaMaestrabean = bean;
                formulaMaestrabean.setCantidadLote(formulaMaestrabean.getCantidadLote().replace(",",""));
                //guardado de datos de referencia para actualizar de acuerdo a esos datos
                formulaMaestrabeanReferencia.getComponentesProd().setCodCompprod(bean.getComponentesProd().getCodCompprod());
                formulaMaestrabeanReferencia.setCodFormulaMaestra(bean.getCodFormulaMaestra());
                
                //areasempresabean.getEstadoReferencial().setCodEstadoRegistro(bean.getCodEstadoRegistro());
                break;
            }
        }
        System.out.println("Entro Editar:" + formulaMaestrabean.getCodFormulaMaestra());
        return "actionEditarFormulaMaestra";
    }

    public void cargarAreasEmpresa(String codigo, FormulaMaestra bean) {
        try {
            setCon(Util.openConnection(getCon()));
            String sql = "select a.cod_area_empresa, a.nombre_area_empresa from areas_empresa a where a.cod_area_empresa " +
                    " in (select cod_area_fabricacion from areas_fabricacion) order by a.nombre_area_empresa";
            System.out.println(" sql:" + sql);
            ResultSet rs = null;

            Statement st = getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            if (!codigo.equals("")) {
            } else {
                areasEmpresaList.clear();
                rs = st.executeQuery(sql);
                areasEmpresaList.add(new SelectItem("0", "Seleccione una Opción"));
                while (rs.next()) {
                    areasEmpresaList.add(new SelectItem(rs.getString(1), rs.getString(2)));
                }
            }
          
            if (rs != null) {
                rs.close();
                st.close();
                rs = null;
                st = null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void guardarVersionFormulaMaestra(String codFormulaMaestra){
        try {
             int codVersion =0;
             int nroVersion = 0;
             con=Util.openConnection(con);
             String consulta = " select (isnull(max(COD_VERSION),0)+1) COD_VERSION from FORMULA_MAESTRA_VERSION ";
             System.out.println("sql:" + consulta);
             Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             ResultSet rs = st.executeQuery(consulta);
             if(rs.next()){
                 codVersion = rs.getInt("COD_VERSION");
             }
             /* nro version */
             consulta = " SELECT (isnull(max(NRO_VERSION),0)+1) NRO_VERSION FROM FORMULA_MAESTRA_VERSION WHERE COD_FORMULA_MAESTRA= " + codFormulaMaestra;
             System.out.println("sql:" + consulta);
             st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             rs = st.executeQuery(consulta);
             if(rs.next()){
                 nroVersion = rs.getInt("NRO_VERSION");
             }
             /* formula maestra */
             
             consulta = " INSERT INTO FORMULA_MAESTRA_VERSION(COD_VERSION, COD_FORMULA_MAESTRA, COD_COMPPROD, CANTIDAD_LOTE,ESTADO_SISTEMA,COD_ESTADO_REGISTRO,FECHA_MODIFICACION,NRO_VERSION)" +
                        " SELECT "+codVersion+", COD_FORMULA_MAESTRA, COD_COMPPROD, CANTIDAD_LOTE,ESTADO_SISTEMA,COD_ESTADO_REGISTRO,GETDATE(),"+nroVersion+"" +
                        " FROM FORMULA_MAESTRA WHERE COD_FORMULA_MAESTRA = " + codFormulaMaestra;
             st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             st.executeUpdate(consulta);

              /* MATERIA PRIMA */

             consulta = " INSERT INTO FORMULA_MAESTRA_DETALLE_MP_VERSION (COD_VERSION, COD_FORMULA_MAESTRA, COD_MATERIAL, CANTIDAD,  COD_UNIDAD_MEDIDA,  NRO_PREPARACIONES,FECHA_MODIFICACION) " +
                     " SELECT "+codVersion+",COD_FORMULA_MAESTRA, COD_MATERIAL, CANTIDAD,  COD_UNIDAD_MEDIDA,  NRO_PREPARACIONES, GETDATE() " +
                     " FROM FORMULA_MAESTRA_DETALLE_MP WHERE COD_FORMULA_MAESTRA = " + codFormulaMaestra ;
             System.out.println("consulta" + consulta);
             st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             st.executeUpdate(consulta);
              /* EMPAQUE PRIMARIO */
             consulta = " INSERT INTO FORMULA_MAESTRA_DETALLE_EP_VERSION (COD_VERSION,COD_FORMULA_MAESTRA,  COD_PRESENTACION_PRIMARIA,  COD_MATERIAL,  CANTIDAD,  COD_UNIDAD_MEDIDA,FECHA_MODIFICACION)" +
                     "  SELECT "+codVersion+",COD_FORMULA_MAESTRA, COD_PRESENTACION_PRIMARIA, COD_MATERIAL,CANTIDAD, COD_UNIDAD_MEDIDA, GETDATE()" +
                     "  FROM FORMULA_MAESTRA_DETALLE_EP WHERE COD_FORMULA_MAESTRA = " + codFormulaMaestra ;
             System.out.println("consulta" + consulta);
             st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             st.executeUpdate(consulta);

              /* EMPAQUE SECUNDARIO */

             consulta = " INSERT INTO FORMULA_MAESTRA_DETALLE_ES_VERSION (COD_VERSION,  COD_FORMULA_MAESTRA, COD_MATERIAL, CANTIDAD, COD_UNIDAD_MEDIDA, COD_PRESENTACION_PRODUCTO,FECHA_MODIFICACION)" +
                     " SELECT "+codVersion+",  COD_FORMULA_MAESTRA,  COD_MATERIAL,  CANTIDAD,  COD_UNIDAD_MEDIDA,  COD_PRESENTACION_PRODUCTO, GETDATE()" +
                     " FROM FORMULA_MAESTRA_DETALLE_ES WHERE COD_FORMULA_MAESTRA = " + codFormulaMaestra ;
             System.out.println("consulta" + consulta);
             st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             st.executeUpdate(consulta);
            /* MATERIAL REACTIVO */
            consulta = " INSERT INTO FORMULA_MAESTRA_DETALLE_MR_VERSION (COD_VERSION,  COD_FORMULA_MAESTRA,COD_MATERIAL, CANTIDAD,  COD_UNIDAD_MEDIDA,  NRO_PREPARACIONES,FECHA_MODIFICACION) " +
                     " SELECT "+codVersion+",  COD_FORMULA_MAESTRA,  COD_MATERIAL,  CANTIDAD,  COD_UNIDAD_MEDIDA,  NRO_PREPARACIONES, GETDATE()" +
                     " FROM FORMULA_MAESTRA_DETALLE_MR WHERE COD_FORMULA_MAESTRA = " + codFormulaMaestra ;
            System.out.println("consulta" + consulta);
             st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             st.executeUpdate(consulta);
             /* PRESENTACION PRIMARIA */

            consulta = " INSERT INTO PRESENTACIONES_PRIMARIAS_VERSION (COD_VERSION, COD_PRESENTACION_PRIMARIA, COD_COMPPROD, COD_ENVASEPRIM, CANTIDAD,FECHA_MODIFICACION) " +
                    " SELECT "+codVersion+",PP.COD_PRESENTACION_PRIMARIA, PP.COD_COMPPROD, PP.COD_ENVASEPRIM, PP.CANTIDAD, GETDATE()" +
                    " FROM PRESENTACIONES_PRIMARIAS  PP " +
                    " INNER JOIN COMPONENTES_PROD CP ON CP.COD_COMPPROD = PP.COD_COMPPROD " +
                    " INNER JOIN FORMULA_MAESTRA FM ON FM.COD_COMPPROD = CP.COD_COMPPROD " +
                    " WHERE FM.COD_FORMULA_MAESTRA = " + codFormulaMaestra;

            

            System.out.println("consulta" + consulta);
            st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            st.executeUpdate(consulta);
            /* PRESENTACION SECUNDARIA */
            consulta = " INSERT INTO PRESENTACIONES_PRODUCTO_VERSION(COD_VERSION, cod_presentacion,cod_prod, CANT_ENVASE_SECUNDARIO,  COD_ENVASESEC,  COD_ENVASETERCIARIO," +
                    " COD_LINEAMKT,  cantidad_presentacion,  cod_tipomercaderia,  COD_CARTON,  OBS_PRESENTACION,  cod_estado_registro,  cod_anterior,  NOMBRE_PRODUCTO_PRESENTACION," +
                    " cod_tipo_presentacion,  COD_LINEAMKT2,  COD_CATEGORIACOMPPROD,  prod_presupuestado,  prod_institucional,  ACCION_TERAPEUTICA,  COD_CATEGORIA) " +
                    " SELECT "+codVersion+",PRP.cod_presentacion,PRP.cod_prod,PRP.CANT_ENVASE_SECUNDARIO,PRP.COD_ENVASESEC,PRP.COD_ENVASETERCIARIO,PRP.COD_LINEAMKT, " +
                    " PRP.cantidad_presentacion,PRP.cod_tipomercaderia,PRP.COD_CARTON, PRP.OBS_PRESENTACION,PRP.cod_estado_registro,PRP.cod_anterior,PRP.NOMBRE_PRODUCTO_PRESENTACION, " +
                    " PRP.cod_tipo_presentacion,PRP.COD_LINEAMKT2,PRP.COD_CATEGORIACOMPPROD,PRP.prod_presupuestado,PRP.prod_institucional,PRP.ACCION_TERAPEUTICA, " +
                    " PRP.COD_CATEGORIA FROM FORMULA_MAESTRA FM" +
                    " INNER JOIN COMPONENTES_PROD CP ON FM.COD_COMPPROD = CP.COD_COMPPROD" +
                    " INNER JOIN COMPONENTES_PRESPROD C ON C.COD_COMPPROD = CP.COD_COMPPROD" +
                    " INNER JOIN PRESENTACIONES_PRODUCTO PRP ON PRP.cod_presentacion = C.COD_PRESENTACION" +
                    " WHERE FM.COD_FORMULA_MAESTRA = " + codFormulaMaestra;
            System.out.println("consulta" + consulta);
            st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            st.executeUpdate(consulta);
    /* COMPONENTES PRES PROD */
            consulta =" INSERT INTO COMPONENTES_PRESPROD_VERSION(COD_VERSION,COD_COMPPROD,COD_PRESENTACION,CANT_COMPPROD)" +
                    "  SELECT "+codVersion+",C.COD_COMPPROD,C.COD_PRESENTACION,C.CANT_COMPPROD" +
                    " FROM FORMULA_MAESTRA FM " +
                    " INNER JOIN COMPONENTES_PROD CP ON FM.COD_COMPPROD = CP.COD_COMPPROD " +
                    " INNER JOIN COMPONENTES_PRESPROD C ON C.COD_COMPPROD = CP.COD_COMPPROD " +
                    " WHERE FM.COD_FORMULA_MAESTRA = " + codFormulaMaestra  ;
            System.out.println("consulta" + consulta);
            st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            st.executeUpdate(consulta);
    /* COMPONENTES_PROD */
            consulta = " INSERT INTO COMPONENTES_PROD_VERSION (COD_VERSION, COD_COMPPROD, COD_PROD,  COD_FORMA,   COD_ENVASEPRIM,   COD_COLORPRESPRIMARIA," +
                    "   VOLUMENPESO_ENVASEPRIM,   CANTIDAD_COMPPROD,   COD_AREA_EMPRESA,  COD_SABOR,  volumenpeso_aproximado,  COD_COMPUESTOPROD, " +
                    "   nombre_prod_semiterminado,  NOMBRE_GENERICO,  REG_SANITARIO,  COD_LINEAMKT,  COD_CATEGORIACOMPPROD,  VIDA_UTIL,  FECHA_VENCIMIENTO_RS, " +
                    "    COD_ESTADO_COMPPROD)  SELECT "+codVersion+",CP.COD_COMPPROD, CP.COD_PROD,  CP.COD_FORMA,   CP.COD_ENVASEPRIM,   CP.COD_COLORPRESPRIMARIA, " +
                    "   CP.VOLUMENPESO_ENVASEPRIM,   CP.CANTIDAD_COMPPROD,   CP.COD_AREA_EMPRESA,  CP.COD_SABOR,  CP.volumenpeso_aproximado,  CP.COD_COMPUESTOPROD, " +
                    "   CP.nombre_prod_semiterminado,  CP.NOMBRE_GENERICO,  CP.REG_SANITARIO,  CP.COD_LINEAMKT,  CP.COD_CATEGORIACOMPPROD,  CP.VIDA_UTIL,  CP.FECHA_VENCIMIENTO_RS, " +
                    "    CP.COD_ESTADO_COMPPROD   FROM COMPONENTES_PROD CP   INNER JOIN FORMULA_MAESTRA FM ON FM.COD_COMPPROD = CP.COD_COMPPROD " +
                    " WHERE FM.COD_FORMULA_MAESTRA = " + codFormulaMaestra;
            System.out.println("consulta" + consulta);
             st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             st.executeUpdate(consulta);
    /* MATERIALES */
            consulta = "  INSERT INTO MATERIALES_VERSION(COD_VERSION,COD_MATERIAL, COD_GRUPO, COD_UNIDAD_MEDIDA, COD_UNIDAD_MEDIDA_COMPRA, NOMBRE_MATERIAL, STOCK_MAXIMO_MATERIAL, STOCK_MINIMO_MATERIAL," +
                    " STOCK_SEGURIDAD, FUNCION_MATERIAL, NOMBRE_COMERCIAL_MATERIAL, PESO_ASOCIADO, COD_UNIDAD_MEDIDA_PESOASOCIADO, CARACTERISTICAS_MATERIAL, TAMANIO_MATERIAL, " +
                    " TIPO_IMPRESION, OBS_MATERIAL, COD_ESTADO_REGISTRO, ACABADO, COLOR, CAPACIDAD, GRAMAJE, MATERIAL_ALMACEN, MATERIAL_MUESTREO, CANTIDAD_MINIMAMUESTREO, " +
                    " CANTIDAD_MAXIMAMUESTREO, MATERIAL_PRODUCCION, COD_GRUPO_STOCK_MATERIAL, COSTO_UNITARIO, CODIGO_ANTIGUO, MOVIMIENTO_ITEM, FECHA_DE_CREACION, COD_PERSONAL, " +
                    " TIEMPO_REPOSICION,  PREMIO,  COD_TIPO_PREMIO_PROMOCIONAL,  COD_MATERIAL_INSUMO) " +
                    " SELECT "+codVersion+", M.COD_MATERIAL, M.COD_GRUPO, M.COD_UNIDAD_MEDIDA, M.COD_UNIDAD_MEDIDA_COMPRA, M.NOMBRE_MATERIAL, M.STOCK_MAXIMO_MATERIAL, M.STOCK_MINIMO_MATERIAL, " +
                    " M.STOCK_SEGURIDAD, M.FUNCION_MATERIAL, M.NOMBRE_COMERCIAL_MATERIAL, M.PESO_ASOCIADO, M.COD_UNIDAD_MEDIDA_PESOASOCIADO, M.CARACTERISTICAS_MATERIAL, M.TAMANIO_MATERIAL, " +
                    " M.TIPO_IMPRESION, M.OBS_MATERIAL, M.COD_ESTADO_REGISTRO, M.ACABADO, M.COLOR, M.CAPACIDAD, M.GRAMAJE, M.MATERIAL_ALMACEN, M.MATERIAL_MUESTREO, M.CANTIDAD_MINIMAMUESTREO, " +
                    " M.CANTIDAD_MAXIMAMUESTREO, M.MATERIAL_PRODUCCION, M.COD_GRUPO_STOCK_MATERIAL, M.COSTO_UNITARIO, M.CODIGO_ANTIGUO, M.MOVIMIENTO_ITEM, M.FECHA_DE_CREACION, M.COD_PERSONAL, " +
                    " M.TIEMPO_REPOSICION,  M.PREMIO,  M.COD_TIPO_PREMIO_PROMOCIONAL,  M.COD_MATERIAL_INSUMO  " +
                    " FROM  MATERIALES M WHERE M.COD_MATERIAL IN (SELECT COD_MATERIAL FROM FORMULA_MAESTRA_DETALLE_MP WHERE COD_FORMULA_MAESTRA = " + codFormulaMaestra+")" +
                    " OR M.COD_MATERIAL IN (SELECT COD_MATERIAL FROM FORMULA_MAESTRA_DETALLE_EP WHERE COD_FORMULA_MAESTRA = " + codFormulaMaestra+")" +
                    " OR M.COD_MATERIAL IN (SELECT COD_MATERIAL FROM FORMULA_MAESTRA_DETALLE_ES WHERE COD_FORMULA_MAESTRA = " + codFormulaMaestra+")" +
                    " OR M.COD_MATERIAL IN (SELECT COD_MATERIAL  FROM FORMULA_MAESTRA_DETALLE_MR WHERE COD_FORMULA_MAESTRA = " + codFormulaMaestra+")" ;
            System.out.println("consulta" + consulta);
            st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             st.executeUpdate(consulta);
                        /* UNIDADES MEDIDA */
      consulta = " INSERT INTO UNIDADES_MEDIDA_VERSION(COD_VERSION,COD_UNIDAD_MEDIDA, COD_TIPO_MEDIDA, NOMBRE_UNIDAD_MEDIDA, OBS_UNIDAD_MEDIDA,COD_ESTADO_REGISTRO" +
              "  ,ABREVIATURA,UNIDAD_CLAVE )  SELECT "+codVersion+",U.COD_UNIDAD_MEDIDA, U.COD_TIPO_MEDIDA, U.NOMBRE_UNIDAD_MEDIDA, U.OBS_UNIDAD_MEDIDA,U.COD_ESTADO_REGISTRO,U.ABREVIATURA, " +
              " U.UNIDAD_CLAVE  FROM UNIDADES_MEDIDA U 	WHERE U.COD_UNIDAD_MEDIDA IN (SELECT F.COD_UNIDAD_MEDIDA " +
              " FROM FORMULA_MAESTRA_DETALLE_MP F WHERE F.COD_FORMULA_MAESTRA = " + codFormulaMaestra+") " +
              " OR U.COD_UNIDAD_MEDIDA IN (SELECT F.COD_UNIDAD_MEDIDA FROM FORMULA_MAESTRA_DETALLE_EP F WHERE F.COD_FORMULA_MAESTRA = " + codFormulaMaestra+") " +
              " OR U.COD_UNIDAD_MEDIDA IN (SELECT F.COD_UNIDAD_MEDIDA FROM FORMULA_MAESTRA_DETALLE_ES F WHERE F.COD_FORMULA_MAESTRA = " + codFormulaMaestra+") " +
              " OR U.COD_UNIDAD_MEDIDA IN (SELECT F.COD_UNIDAD_MEDIDA FROM FORMULA_MAESTRA_DETALLE_MR F WHERE F.COD_FORMULA_MAESTRA = " + codFormulaMaestra+") " ;
             System.out.println("consulta" + consulta);
            st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             st.executeUpdate(consulta);
                  /* ESTADOS COMP PROD */

      consulta = " INSERT INTO ESTADOS_COMPPROD_VERSION(COD_VERSION,COD_ESTADO_COMPPROD, NOMBRE_ESTADO_COMPPROD, OBS_ESTADO_COMPPROD, COD_ESTADO_REGISTRO) " +
              " SELECT "+codVersion+",EC.COD_ESTADO_COMPPROD, EC.NOMBRE_ESTADO_COMPPROD, EC.OBS_ESTADO_COMPPROD, EC.COD_ESTADO_REGISTRO " +
              " FROM ESTADOS_COMPPROD EC " +
              " INNER JOIN COMPONENTES_PROD CP ON CP.COD_ESTADO_COMPPROD = EC.COD_ESTADO_COMPPROD " +
              "	INNER JOIN FORMULA_MAESTRA FM ON FM.COD_COMPPROD = CP.COD_COMPPROD " +
              "	WHERE FM.COD_FORMULA_MAESTRA=" + codFormulaMaestra+" ";
              System.out.println("consulta" + consulta);
              st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             st.executeUpdate(consulta);
    /* ENVASES PRIMARIOS */

                 
      consulta = " INSERT INTO ENVASES_PRIMARIOS_VERSION(COD_VERSION,cod_envaseprim,nombre_envaseprim,obs_envaseprim,cod_estado_registro)" +
              " SELECT "+codVersion+",cod_envaseprim,nombre_envaseprim,obs_envaseprim,cod_estado_registro " +
              " from ENVASES_PRIMARIOS WHERE COD_ENVASEPRIM IN ( SELECT EP.COD_ENVASEPRIM " +
              "	FROM ENVASES_PRIMARIOS EP " +
              "	INNER JOIN PRESENTACIONES_PRIMARIAS  PP ON EP.cod_envaseprim = PP.COD_ENVASEPRIM " +
              "	INNER JOIN COMPONENTES_PROD CP ON CP.COD_COMPPROD = PP.COD_COMPPROD " +
              "	INNER JOIN FORMULA_MAESTRA FM ON FM.COD_COMPPROD = CP.COD_COMPPROD " +
              "	WHERE FM.COD_FORMULA_MAESTRA = " + codFormulaMaestra+" )";
              System.out.println("consulta" + consulta);
              st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             st.executeUpdate(consulta);
    /* MATERIA PRIMA FRACCIONES */

      consulta = " INSERT INTO FORMULA_MAESTRA_DETALLE_MP_FRACCIONES_VERSION (COD_VERSION, COD_FORMULA_MAESTRA, COD_MATERIAL,  COD_FORMULA_MAESTRA_FRACCIONES,  CANTIDAD)" +
              " SELECT "+codVersion+", FMF.COD_FORMULA_MAESTRA, FMF.COD_MATERIAL,  FMF.COD_FORMULA_MAESTRA_FRACCIONES,  FMF.CANTIDAD " +
              " FROM FORMULA_MAESTRA_DETALLE_MP FMD " +
              " INNER JOIN FORMULA_MAESTRA_DETALLE_MP_FRACCIONES FMF ON FMD.COD_FORMULA_MAESTRA = FMF.COD_FORMULA_MAESTRA " +
              " AND FMD.COD_MATERIAL = FMF.COD_MATERIAL  WHERE FMD.COD_FORMULA_MAESTRA = " + codFormulaMaestra+" ";
              System.out.println("consulta" + consulta);
              st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             st.executeUpdate(consulta);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String guardarFormulaMaestra() {

        try {
            String sql = "insert into formula_maestra(cod_formula_maestra,cod_compprod,cantidad_lote,estado_sistema,cod_estado_registro)values";
            sql += "(" + formulaMaestrabean.getCodFormulaMaestra() + "," + formulaMaestrabean.getComponentesProd().getCodCompprod();
            sql += "," + formulaMaestrabean.getCantidadLote() + ",1,1)";
            System.out.println("insert:" + sql);
            setCon(Util.openConnection(getCon()));
            PreparedStatement st = getCon().prepareStatement(sql);
            int result = st.executeUpdate();
            if (result > 0) {
                cargarFormulaMaestra();
                FormulaMaestra formulaMaestrabean = new FormulaMaestra();
            }
            System.out.println("result:" + result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        FormulaMaestra formulaMaestrabean = new FormulaMaestra();
        System.out.println("paso por aqui");
        return "navegadorFormulaMaestra";
    }

    public String modificarFormulaMaestra() {
         try{
            this.guardarVersionFormulaMaestra(codigo);
            setCon(Util.openConnection(getCon()));
            String consulta = " update componentes_prod set COD_ESTADO_COMPPROD="+getFormulaMaestrabean().getEstadoRegistro().getCodEstadoRegistro()+" where COD_COMPPROD = "+getFormulaMaestrabean().getComponentesProd().getCodCompprod()+" ";
            System.out.println(" consulta de actualizacion de componente: " +  consulta);
            PreparedStatement st = getCon().prepareStatement(consulta);
            st.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }


        try {
            setCon(Util.openConnection(getCon()));
            //actualizar la formula maestra
            String sql = "update formula_maestra set";
            //      sql+=" cod_filial='"+getAreasempresabean().getFiliales().getCodFilial()+"',";
            sql += " cod_compprod=" + getFormulaMaestrabean().getComponentesProd().getCodCompprod() + ",";
            sql += " cantidad_lote=" + Float.valueOf(getFormulaMaestrabean().getCantidadLote().replace(",", "")) + ",";
            sql += " cod_estado_registro=" + getFormulaMaestrabean().getEstadoRegistro().getCodEstadoRegistro() + "";
            sql += " where cod_formula_maestra=" + getFormulaMaestrabean().getCodFormulaMaestra();
            PreparedStatement st = getCon().prepareStatement(sql);
            //actualizar el estado de componente

            System.out.println("entro Modificar Formula Maestra : "+ sql);
            int result = st.executeUpdate();
            if (result > 0) {
                cargarFormulaMaestra();
                FormulaMaestra formulaMaestrabean = new FormulaMaestra();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
       

        cargarFormulaMaestra();
        return "navegadorFormulaMaestra";
    }

    public String Cancelar() {
        formulaMaestraList.clear();
        cargarFormulaMaestra();
        return "navegadorFormulaMaestra";
    }

public String eliminarFormulaMaestra() {
    
        try {
            con = Util.openConnection(con);
            Iterator i = formulaMaestraList.iterator();
            int result = 0;
            while (i.hasNext()) {
                FormulaMaestra bean = (FormulaMaestra) i.next();
                if (bean.getChecked().booleanValue()) {
                    this.guardarVersionFormulaMaestra(bean.getCodFormulaMaestra());
                    String sql = " delete from formula_maestra  ";
                    sql += " where cod_formula_maestra=" + bean.getCodFormulaMaestra();
                    System.out.println("deleteformula_maestra:sql:" + sql);                    
                    Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);                    
                    result = result + st.executeUpdate(sql);
                    
                    sql += " delete from formula_maestra_detalle_es  ";
                    sql += " where cod_formula_maestra=" + bean.getCodFormulaMaestra();
                    System.out.println("formula_maestra_ep:sql:" + sql);
                    st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    result = result + st.executeUpdate(sql);
                    
                    sql += " delete from formula_maestra_detalle_ep ";
                    sql += " where cod_formula_maestra=" + bean.getCodFormulaMaestra();
                    System.out.println("formula_maestra_detalle_ep:sql:" + sql);
                    st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    result = result + st.executeUpdate(sql);
                    
                    sql += " delete from formula_maestra_detalle_mp ";
                    sql += " where cod_formula_maestra=" + bean.getCodFormulaMaestra();
                    System.out.println("formula_maestra_detalle_mp:sql:" + sql);
                    st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);                    
                    result = result + st.executeUpdate(sql);
                    
                    sql += " delete from formula_maestra_detalle_mprom ";
                    sql += " where cod_formula_maestra=" + bean.getCodFormulaMaestra();
                    System.out.println("formula_maestra_detalle_mprom:sql:" + sql);
                    st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    result = result + st.executeUpdate(sql);
                    
              
                }
            }
            getFormulaMaestraEliminar().clear();
            if (result > 0) {
                cargarFormulaMaestra();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "navegadorFormulaMaestra";
    }

    /**
     * Metodo que cierra la conexion
     */
    public String getCloseConnection() throws SQLException {
        if (getCon() != null) {
            getCon().close();
        }
        return "";
    }

    public String actionagregar() {
        System.out.println("klfsdaf");
        cargarComponentesProd();
        FormulaMaestra formulaMaestrabean = new FormulaMaestra();
        return "actionAgregarFormulaMaestra";

    }

    /**********ESTADO REGISTRO****************/
    public void changeEvent(ValueChangeEvent event) {
        System.out.println("event:" + event.getNewValue());
        formulaMaestrabean.getEstadoRegistro().setCodEstadoRegistro(event.getNewValue().toString());
        cargarFormulaMaestra();
    }

    public void changeEvent1(ValueChangeEvent event) {
        System.out.println("event1:" + event.getNewValue());
        formulaMaestrabean.getComponentesProd().getAreasEmpresa().setCodAreaEmpresa(event.getNewValue().toString());
        cargarFormulaMaestra();
    }

    /* ------------------Métodos---------------------------*/
    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public FormulaMaestra getFormulaMaestrabean() {
        return formulaMaestrabean;
    }

    public void setFormulaMaestrabean(FormulaMaestra formulaMaestrabean) {
        this.formulaMaestrabean = formulaMaestrabean;
    }

    public List getFormulaMaestraEliminar() {
        return formulaMaestraEliminar;
    }

    public void setFormulaMaestraEliminar(List formulaMaestraEliminar) {
        this.formulaMaestraEliminar = formulaMaestraEliminar;
    }

    public List getFormulaMaestraNoEliminar() {
        return formulaMaestraNoEliminar;
    }

    public void setFormulaMaestraNoEliminar(List formulaMaestraNoEliminar) {
        this.formulaMaestraNoEliminar = formulaMaestraNoEliminar;
    }

    public int getCantidadeliminar() {
        return cantidadeliminar;
    }

    public void setCantidadeliminar(int cantidadeliminar) {
        this.cantidadeliminar = cantidadeliminar;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public List getLista() {
        return lista;
    }

    public void setLista(List lista) {
        this.lista = lista;
    }

    public List getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(List estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

    public boolean isSwEliminaSi() {
        return swEliminaSi;
    }

    public void setSwEliminaSi(boolean swEliminaSi) {
        this.swEliminaSi = swEliminaSi;
    }

    public boolean isSwEliminaNo() {
        return swEliminaNo;
    }

    public void setSwEliminaNo(boolean swEliminaNo) {
        this.swEliminaNo = swEliminaNo;
    }

    public List getFormulaMaestraList() {
        return formulaMaestraList;
    }

    public void setFormulaMaestraList(List formulaMaestraList) {
        this.formulaMaestraList = formulaMaestraList;
    }

    public List getComponentesProd() {
        return componentesProd;
    }

    public void setComponentesProd(List componentesProd) {
        this.componentesProd = componentesProd;
    }

    public List getAreasEmpresaList() {
        return areasEmpresaList;
    }

    public void setAreasEmpresaList(List areasEmpresaList) {
        this.areasEmpresaList = areasEmpresaList;
    }

    public HtmlDataTable getFormulaMaestraDataTable() {
        return formulaMaestraDataTable;
    }

    public void setFormulaMaestraDataTable(HtmlDataTable formulaMaestraDataTable) {
        this.formulaMaestraDataTable = formulaMaestraDataTable;
    }
    
}
