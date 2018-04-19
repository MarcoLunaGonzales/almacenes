
/*
 * ManagedTipoCliente.java
 * Created on 19 de febrero de 2008, 16:50
 */
package com.cofar.web;

import com.cofar.bean.FormulaMaestraDetalleMP;
import com.cofar.bean.Maquinaria;
import com.cofar.bean.Materiales;
import com.cofar.bean.ProgramaProduccion;
import com.cofar.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import org.joda.time.DateTime;
import java.text.NumberFormat;

/**
 *
 *  @author Wilmer Manzaneda Chavez
 *  @company COFAR
 */
public class ManagedMaquinaria {

    /** Creates a new instance of ManagedTipoCliente */
    private Maquinaria maquinariabean = new Maquinaria();
    private List<Maquinaria> maquinariaList = new ArrayList<Maquinaria>();
    private List maquinariaEliminarList = new ArrayList();
    private List maquinariaNoEliminarList = new ArrayList();
    private Connection con;
    private String codigo = "";
    private boolean swEliminaSi;
    private boolean swEliminaNo;
    private List estadoRegistro = new ArrayList();
    private List tiposEquipoList = new ArrayList();
    private List GMPList = new ArrayList();
    private List areasMaquinaList = new ArrayList();

    public ManagedMaquinaria() {
        //cargarMaquinaria();
    }

    public String getObtenerCodigo() {
        //String cod=Util.getParameter("codigo");
        String cod = Util.getParameter("codigo");
        System.out.println("cxxxxxxxxxxxxxxxxxxxxxxxod:" + cod);
        if (cod != null) {
            setCodigo(cod);
        }
        //detalleProgramProd();
        return "";
    }

    public String getCargarMaquinarias(){
        try {
            this.cargarMaquinaria();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public void cargarEstadoRegistro(String codigo, Maquinaria bean) {
        try {
            setCon(Util.openConnection(getCon()));
            String sql = "select cod_estado_registro,nombre_estado_registro from estados_referenciales where cod_estado_registro<>3";
            ResultSet rs = null;
            Statement st = getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            if (!codigo.equals("")) {
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

    public void cargarTiposEquipos(String codigo, Maquinaria bean) {
        try {
            setCon(Util.openConnection(getCon()));
            String sql = "select COD_TIPO_EQUIPO,NOMBRE_TIPO_EQUIPO from TIPOS_EQUIPOS_MAQUINARIA where cod_estado_registro=1";
            ResultSet rs = null;
            Statement st = getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            if (!codigo.equals("")) {
            } else {
                tiposEquipoList.clear();
                rs = st.executeQuery(sql);
                while (rs.next()) {
                    tiposEquipoList.add(new SelectItem(rs.getString(1), rs.getString(2)));
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

    public void cargarGMP(String codigo, Maquinaria bean) {
        GMPList.clear();
        GMPList.add(new SelectItem("0", "  "));
        GMPList.add(new SelectItem("1", "GMP"));
    }

    public String getCodigoMaquinaria() {
        String codigo = "1";
        try {
            setCon(Util.openConnection(getCon()));
            String sql = "select max(cod_maquina)+1 from maquinarias";
            PreparedStatement st = getCon().prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                codigo = rs.getString(1);
            }
            if (codigo == null) {
                codigo = "1";
            }
            maquinariabean.setCodMaquina(codigo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void cargarMaquinaria() {
        try {
            String sql = "select m.COD_MAQUINA,m.NOMBRE_MAQUINA,m.OBS_MAQUINA,m.fecha_compra,";
            sql += "e.NOMBRE_ESTADO_REGISTRO,m.codigo,m.COD_TIPO_EQUIPO,m.GMP,te.NOMBRE_TIPO_EQUIPO,m.COD_AREA_EMPRESA,ae.NOMBRE_AREA_EMPRESA ";
            sql += " from MAQUINARIAS m, ESTADOS_REFERENCIALES e, AREAS_EMPRESA ae ,TIPOS_EQUIPOS_MAQUINARIA te";
            sql += " where m.COD_ESTADO_REGISTRO<>0 and e.COD_ESTADO_REGISTRO=m.COD_ESTADO_REGISTRO";
            sql += " and m.COD_TIPO_EQUIPO=te.COD_TIPO_EQUIPO " +
                    " and m.COD_AREA_EMPRESA= ae.COD_AREA_EMPRESA ";
            if (!maquinariabean.getEstadoReferencial().getCodEstadoRegistro().equals("") && !maquinariabean.getEstadoReferencial().getCodEstadoRegistro().equals("3")) {
                sql += " and  m.cod_estado_registro=" + maquinariabean.getEstadoReferencial().getCodEstadoRegistro();
            }
            sql += " order by m.NOMBRE_MAQUINA";
            System.out.println("cargar maquinaria:" + sql);
            con = Util.openConnection(con);
            Statement st = getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(sql);
            rs.last();
            int rows = rs.getRow();
            maquinariaList.clear();
            rs.first();
            String cod = "";
            for (int i = 0; i < rows; i++) {
                Maquinaria bean = new Maquinaria();
                bean.setCodMaquina(rs.getString(1));
                bean.setNombreMaquina(rs.getString(2));
                bean.setObsMaquina(rs.getString(3));
                bean.setCodAreaMaquina(rs.getString(10));
                bean.setNombreAreaMaquina(rs.getString(11));
                String fecha = rs.getString(4);
                if (fecha == null) {
                    fecha = "";
                } else {
                    String fechaVector[] = fecha.split(" ");
                    fechaVector = fechaVector[0].split("-");
                    fecha = fechaVector[2] + "/" + fechaVector[1] + "/" + fechaVector[0];
                    if (fecha.equals("01/01/1900")) {
                        fecha = "";
                    }
                }
                bean.setFechaCompra(fecha);
                bean.getEstadoReferencial().setNombreEstadoRegistro(rs.getString(5));
                bean.setCodigo(rs.getString(6));
                bean.getTiposEquiposMaquinaria().setCodTipoEquipo(rs.getString(7));
                String gmp = rs.getString(8);
                if (gmp.equals("1")) {
                    bean.setGMP("GMP");
                } else {
                    bean.setGMP(" ");
                }
                bean.getTiposEquiposMaquinaria().setNombreTipoEquipo(rs.getString(9));
                maquinariaList.add(bean);
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
        cargarEstadoRegistro("", null);
        cargarTiposEquipos("", null);
        cargarListaAreas();
        cargarGMP("", null);
        Iterator i = maquinariaList.iterator();
        while (i.hasNext()) {
            Maquinaria bean = (Maquinaria) i.next();
            if (bean.getChecked().booleanValue()) {
                maquinariabean = bean;
                if (maquinariabean.getGMP().equals("GMP")) {
                    maquinariabean.setGMP("1");
                } else {
                    maquinariabean.setGMP("2");
                }
                break;
                
            }
        }
        return "actionEditarMaquina";
    }

    /*********actions* traer  *****/
    public String actionEliminar() {
        setSwEliminaSi(false);
        setSwEliminaNo(false);
        maquinariaNoEliminarList.clear();
        maquinariaEliminarList.clear();
        int bandera = 0;
        Iterator i = maquinariaList.iterator();
        while (i.hasNext()) {
            Maquinaria bean = (Maquinaria) i.next();
            if (bean.getChecked().booleanValue()) {
                try {
                    String sql = "select * from MAQUINARIA_AREA " +
                            " where cod_maquina=" + bean.getCodMaquina();
                    setCon(Util.openConnection(getCon()));
                    Statement st = getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    ResultSet rs = st.executeQuery(sql);
                    rs.last();
                    if (rs.getRow() == 0) {
                        bandera = 1;
                    }
                    if (bandera == 1) {
                        maquinariaEliminarList.add(bean);
                        setSwEliminaSi(true);
                        System.out.println("entrooooooooo eliminar");
                    } else {
                        maquinariaNoEliminarList.add(bean);
                        setSwEliminaNo(true);
                        System.out.println("entrooooooooo no eliminar");
                    }
                    if (rs != null) {
                        rs.close();
                        st.close();
                        rs = null;
                        st = null;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return "ActionEliminarMaquina";
    }

    /*********actions******/
    public void clearMaquinarias() {
        maquinariabean.setCodMaquina("");
        maquinariabean.setNombreMaquina("");
        maquinariabean.setObsMaquina("");
        maquinariabean.setFechaCompra("");
    }

    public String modificarMaquinarias() {
        try {
            setCon(Util.openConnection(getCon()));
            String fechaFinal = maquinariabean.getFechaCompra();
            if (!fechaFinal.equals("")) {
                String fechaFinalVector[] = fechaFinal.split("/");
                fechaFinal = fechaFinalVector[2] + "/" + fechaFinalVector[1] + "/" + fechaFinalVector[0];
            } else {
                fechaFinal = "1900/01/01";
            }
            //String fechaFinalVector[] = fechaFinal.split("/");
            //fechaFinal = fechaFinalVector[2] + "/" + fechaFinalVector[1] + "/" + fechaFinalVector[0];
            String sql = "update maquinarias set";
            sql += " nombre_maquina='" + maquinariabean.getNombreMaquina().toUpperCase() + "',";
            sql += " obs_maquina='" + maquinariabean.getObsMaquina() + "',";
            sql += " fecha_compra='" + fechaFinal + "',";
            sql += " codigo='" + maquinariabean.getCodigo() + "',";
            sql += " gmp='" + maquinariabean.getGMP() + "',";
            sql += " cod_tipo_equipo='" + maquinariabean.getTiposEquiposMaquinaria().getCodTipoEquipo() + "',";
            sql += " cod_estado_registro='" + maquinariabean.getEstadoReferencial().getCodEstadoRegistro() + "',";
            sql += " cod_area_empresa='" + maquinariabean.getCodAreaMaquina() + "'";
            sql += " where cod_maquina=" + maquinariabean.getCodMaquina();

            

            PreparedStatement st = getCon().prepareStatement(sql);
            int result = st.executeUpdate();
            if (result > 0) {
                cargarMaquinaria();
            }
            cargarMaquinaria();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        clearMaquinarias();
        return "navegadorMaquina";
    }

    public String Cancelar() {
        maquinariaList.clear();
        cargarMaquinaria();
        return "navegadorMaquina";
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
        cargarEstadoRegistro("", null);
        cargarTiposEquipos("", null);
        cargarGMP("", null);
        this.cargarListaAreas();
        return "actionAgregarMaquina";
    }

    public void cargarListaAreas() {
        try {
            setCon(Util.openConnection(getCon()));
            String sql = "SELECT AE.COD_AREA_EMPRESA,AE.NOMBRE_AREA_EMPRESA " +
                    " FROM AREAS_EMPRESA AE WHERE AE.COD_AREA_EMPRESA IN (SELECT AF.COD_AREA_FABRICACION FROM AREAS_FABRICACION AF)";
            sql = "SELECT AE.COD_AREA_EMPRESA,AE.NOMBRE_AREA_EMPRESA " +
                    " FROM AREAS_EMPRESA AE WHERE AE.COD_ESTADO_REGISTRO=1 ORDER BY AE.NOMBRE_AREA_EMPRESA ASC";

            ResultSet rs = null;
            Statement st = getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            if (!codigo.equals("")) {
            } else {
                areasMaquinaList.clear();
                rs = st.executeQuery(sql);
                while (rs.next()) {
                    areasMaquinaList.add(new SelectItem(rs.getString("COD_AREA_EMPRESA"), rs.getString("NOMBRE_AREA_EMPRESA")));
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

    /**********ESTADO REGISTRO****************/
    public String guardarMaquinarias() {
        try {
            getCodigoMaquinaria();
            String fechaFinal = maquinariabean.getFechaCompra();
            if (!fechaFinal.equals("")) {
                String fechaFinalVector[] = fechaFinal.split("/");
                fechaFinal = fechaFinalVector[2] + "/" + fechaFinalVector[1] + "/" + fechaFinalVector[0];
            } else {
                fechaFinal = "1900/01/01";
            }
            String sql = "insert into maquinarias(cod_maquina,nombre_maquina,obs_maquina," +
                    "fecha_compra,cod_estado_registro,codigo,COD_TIPO_EQUIPO,GMP,COD_AREA_EMPRESA)values(";
            sql += "'" + maquinariabean.getCodMaquina() + "',";
            sql += "'" + maquinariabean.getNombreMaquina().toUpperCase() + "',";
            sql += "'" + maquinariabean.getObsMaquina() + "',";
            sql += "'" + fechaFinal + "',1,'" + maquinariabean.getCodigo() + "',";
            sql += "'" + maquinariabean.getTiposEquiposMaquinaria().getCodTipoEquipo() + "'," + maquinariabean.getGMP() + "," +
                    "'" + maquinariabean.getCodAreaMaquina() + "')";

            System.out.println("insert:" + sql);
            setCon(Util.openConnection(getCon()));
            PreparedStatement st = getCon().prepareStatement(sql);
            int result = st.executeUpdate();
            if (result > 0) {
                cargarMaquinaria();
                clearMaquinarias();
            }
            System.out.println("result:" + result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "navegadorMaquina";
    }

    public String eliminarMaquinaria() {
        try {

            Iterator i = maquinariaEliminarList.iterator();
            int result = 0;
            while (i.hasNext()) {
                Maquinaria bean = (Maquinaria) i.next();
                String sql = "delete from maquinarias  ";
                sql += " where cod_maquina=" + bean.getCodMaquina();
                System.out.println("delete_maquinaria:sql:" + sql);
                setCon(Util.openConnection(getCon()));
                Statement st = getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                result = result + st.executeUpdate(sql);
            }
            maquinariaEliminarList.clear();
            maquinariaNoEliminarList.clear();
            if (result > 0) {
                cargarMaquinaria();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "navegadorMaquina";
    }
    /* ------------------Métodos---------------------------*/

    /**********ESTADO REGISTRO****************/
    public void changeEvent(ValueChangeEvent event) {
        System.out.println("event:" + event.getNewValue());
        maquinariabean.getEstadoReferencial().setCodEstadoRegistro(event.getNewValue().toString());
        cargarMaquinaria();
    }

    public double redondear(double numero, int decimales) {
        return Math.round(numero * Math.pow(10, decimales)) / Math.pow(10, decimales);
    }

    public static void main(String[] args) {
        int a1, m1, d1, a2, m2, d2;
        String fe_inicio = "05-1-2008";
        System.out.println("fe_inicio:" + fe_inicio);
        String fe_final = "05-31-2008";
        System.out.println("fe_final:" + fe_final);
        String[] Inicio = fe_inicio.split("-");
        String[] Final = fe_final.split("-");

        d1 = Integer.parseInt(Final[1]);
        m1 = Integer.parseInt(Final[0]);
        a1 = Integer.parseInt(Final[2]);
        d2 = Integer.parseInt(Inicio[1]);
        m2 = Integer.parseInt(Inicio[0]);
        a2 = Integer.parseInt(Inicio[2]);

        DateTime start = new DateTime(a2, m2, d2, 0, 0, 0, 0);
        System.out.println("start:" + start);
        DateTime end = new DateTime(a1, m1, d1, 0, 0, 0, 0);
        System.out.println("end:" + end);
        int count = 0;
        while (start.compareTo(end) <= 0) {
            if (start.getDayOfWeek() == 7) {
                count = count + 1;
            }
        }
        System.out.println("count :" + count);
    }

    public Maquinaria getMaquinariabean() {
        return maquinariabean;
    }

    public void setMaquinariabean(Maquinaria maquinariabean) {
        this.maquinariabean = maquinariabean;
    }

    public List<Maquinaria> getMaquinariaList() {
        return maquinariaList;
    }

    public void setMaquinariaList(List<Maquinaria> maquinariaList) {
        this.maquinariaList = maquinariaList;
    }

    public List getMaquinariaEliminarList() {
        return maquinariaEliminarList;
    }

    public void setMaquinariaEliminarList(List maquinariaEliminarList) {
        this.maquinariaEliminarList = maquinariaEliminarList;
    }

    public List getMaquinariaNoEliminarList() {
        return maquinariaNoEliminarList;
    }

    public void setMaquinariaNoEliminarList(List maquinariaNoEliminarList) {
        this.maquinariaNoEliminarList = maquinariaNoEliminarList;
    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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

    public List getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(List estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

    public List getTiposEquipoList() {
        return tiposEquipoList;
    }

    public void setTiposEquipoList(List tiposEquipoList) {
        this.tiposEquipoList = tiposEquipoList;
    }

    public List getGMPList() {
        return GMPList;
    }

    public void setGMPList(List GMPList) {
        this.GMPList = GMPList;
    }

    public List getAreasMaquinaList() {
        return areasMaquinaList;
    }

    public void setAreasMaquinaList(List areasMaquinaList) {
        this.areasMaquinaList = areasMaquinaList;
    }
}
