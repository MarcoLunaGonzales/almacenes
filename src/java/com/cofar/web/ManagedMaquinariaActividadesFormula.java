
/*
 * ManagedCartonesCorrugados.java
 * Created on 25 de Junio de 2008, 10:50
 */

package com.cofar.web;


import com.cofar.bean.ActividadesFormulaMaestra;
import com.cofar.bean.ActividadesProduccion;
import com.cofar.bean.CartonesCorrugados;
import com.cofar.bean.MaquinariaActividadesFormula;
import com.cofar.util.Util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

/**
 *
 *  @author Wilson Choquehuanca Gonzales
 *  @company COFAR
 */
public class ManagedMaquinariaActividadesFormula{
    
    private MaquinariaActividadesFormula maquinariaActividadesFormulabean=new MaquinariaActividadesFormula();
    private List maquinariaActividadesFormulaList=new ArrayList();
    private List maquinariaActividadesFormulaAdicionarList=new ArrayList();
    private List maquinariaActividadesFormulaEditarList=new ArrayList();
    private List estadoRegistro=new ArrayList();
    private Connection con;
    private boolean swEliminaSi;
    private boolean swEliminaNo;
    private String codigo="";
    private List maquinariasList=new ArrayList();
    private List actividadesList=new ArrayList();
    private String nombreFormula="";
    private String nombreActividad="";
    private String codActividad="";
    private String codFormulaMaestra="";
    private float H_hombre=0;   //horas hombre
    private float H_maquina=0;  //horas maquina
    
    
    
    public ManagedMaquinariaActividadesFormula() {
        
    }
    /**
     * metodo que genera los codigos
     * correlativamente
     */
    public String getObtenerCodigo(){
        
        String cod=Util.getParameter("codigo");
        String cod1=Util.getParameter("cod_formula");
        String cod2=Util.getParameter("cod_actividad");
        System.out.println("cxxxxxxxxxxxxxxxxxxxxxxxod:"+cod);
        if(cod!=null){
            setCodigo(cod);
        }
        if(cod1!=null){
            setCodFormulaMaestra(cod1);
        }
        if(cod2!=null){
            setCodActividad(cod2);
        }
        cargarMaquinariaActividadFormula();
        cargarFormulaMaestra();
        cargarActividades();
        return "";
        
        
    }
    public String cargarFormulaMaestra(){
        try {
            setCon(Util.openConnection(getCon()));
            String sql=" select cp.nombre_prod_semiterminado";
            sql+=" from COMPONENTES_PROD cp,PRODUCTOS p,FORMULA_MAESTRA fm";
            sql+=" where cp.COD_COMPPROD=fm.COD_COMPPROD and p.cod_prod=cp.COD_PROD";
            sql+=" and fm.COD_FORMULA_MAESTRA='"+codFormulaMaestra+"'";
            System.out.println("sql:-----------:"+sql);
            PreparedStatement st=getCon().prepareStatement(sql);
            ResultSet rs=st.executeQuery();
            while (rs.next())
                setNombreFormula(rs.getString(1));
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("nombreFormula:"+getNombreFormula());
        return  "";
    }
    public String cargarActividades(){
        try {
            setCon(Util.openConnection(getCon()));
            String sql=" select nombre_actividad";
            sql+=" from actividades_produccion";
            sql+=" where cod_actividad='"+codActividad+"'"; 
            System.out.println("sql:-----------:"+sql);
            PreparedStatement st=getCon().prepareStatement(sql);
            ResultSet rs=st.executeQuery();
            while (rs.next())
                setNombreActividad(rs.getString(1));
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("nombreActividad:"+getNombreActividad());
        return  "";
    }
    public String getCodigoMaquinariaActividades(){
        String codigo="1";
        try {
            setCon(Util.openConnection(getCon()));
            String sql="select max(COD_MAQUINARIA_ACTIVIDAD)+1 from MAQUINARIA_ACTIVIDADES_FORMULA";
            PreparedStatement st=getCon().prepareStatement(sql);
            System.out.println("sql:MAX:"+sql);
            ResultSet rs=st.executeQuery();
            while (rs.next())
                codigo=rs.getString(1);                
            if(codigo==null)
                codigo="1";            
            maquinariaActividadesFormulabean.setCodMaquinariaActividad(codigo);
            System.out.println("coiogogo:"+codigo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  "";
    }
    
  /*  public void cargarMaquinaria(String codigo,ActividadesFormulaMaestra bean){
        try {
            setCon(Util.openConnection(getCon()));
            String sql="select cod_maquina,nombre_maquina from maquinarias where cod_estado_registro=1";
            sql+=" order by nombre_maquina";
            System.out.println("sql_maquinarias:"+sql);
            ResultSet rs=null;
   
            Statement st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            if(!codigo.equals("")){
   
            } else{
                getMaquinariasList().clear();
                rs=st.executeQuery(sql);
                while (rs.next())
                    getMaquinariasList().add(new SelectItem(rs.getString(1),rs.getString(2)));
            }
            if(rs!=null){
                rs.close();st.close();
                rs=null;st=null;
            }
   
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/
    public void cargarMaquinaria(){
        try {
            setCon(Util.openConnection(getCon()));
            String sql="select cod_maquina,nombre_maquina from maquinarias where cod_estado_registro=1";
            sql+=" and cod_maquina <> all (select f.cod_maquina from MAQUINARIA_ACTIVIDADES_FORMULA f where f.COD_ACTIVIDAD_FORMULA='"+getCodigo()+"')";
            sql+=" order by nombre_maquina";
            System.out.println("sql_actividades:"+sql);
            System.out.println("cargar:"+sql);
            setCon(Util.openConnection(getCon()));
            Statement st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs=st.executeQuery(sql);
            maquinariaActividadesFormulaAdicionarList.clear();
            while (rs.next()){
                MaquinariaActividadesFormula bean=new MaquinariaActividadesFormula();
                bean.getMaquinaria().setCodMaquina(rs.getString(1));
                bean.getMaquinaria().setNombreMaquina(rs.getString(2));
                bean.setHorasMaquina("0");
                bean.setHorasHombre("0");
                maquinariaActividadesFormulaAdicionarList.add(bean);
            }
            if(rs!=null){
                rs.close();st.close();
                rs=null;st=null;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Metodo para cargar los datos en
     * el datatable
     */
    public void cargarMaquinariaActividadFormula(){
        try {            
            String sql="select ma.COD_MAQUINARIA_ACTIVIDAD,m.COD_MAQUINA,m.NOMBRE_MAQUINA,ma.HORAS_MAQUINA,ma.HORAS_HOMBRE" ;
            sql+=" from MAQUINARIAS m,MAQUINARIA_ACTIVIDADES_FORMULA ma" ;
            sql+=" where m.COD_MAQUINA=ma.COD_MAQUINA and ma.COD_ACTIVIDAD_FORMULA='"+getCodigo()+"'";
            sql+=" order by m.NOMBRE_MAQUINA asc";
            System.out.println("cargar:"+sql);
            setCon(Util.openConnection(getCon()));
            Statement st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs=st.executeQuery(sql);
            rs.last();
            int rows=rs.getRow();
            maquinariaActividadesFormulaList.clear();
            rs.first();
            setH_hombre(0);
            setH_maquina(0);
            for(int i=0;i<rows;i++){
                MaquinariaActividadesFormula bean=new MaquinariaActividadesFormula();
                bean.setCodMaquinariaActividad(rs.getString(1));
                bean.getMaquinaria().setCodMaquina(rs.getString(2));
                bean.getMaquinaria().setNombreMaquina(rs.getString(3));
                bean.setHorasMaquina(rs.getString(4));
                bean.setHorasHombre(rs.getString(5));
                
                setH_hombre(getH_hombre() + Float.parseFloat(bean.getHorasHombre().trim().length()==0?"0":bean.getHorasHombre()));
                setH_maquina(getH_maquina() + Float.parseFloat(bean.getHorasMaquina().trim().length()==0?"0":bean.getHorasMaquina()));

                
                maquinariaActividadesFormulaList.add(bean);
                rs.next();
            }
            setH_maquina(getH_maquina());
            setH_hombre(getH_hombre());
            if(rs!=null){
                rs.close();
                st.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public String Guardar(){
        clearMaquinariaActividadesFormula();
        cargarMaquinaria();
        //cargarMaquinaria("",null);
        return"actionAgregarMaquinariaActividadesFM";
    }
    
    public String actionEditar(){
        //cargarMaquinaria("",null);
        maquinariaActividadesFormulaEditarList.clear();
        Iterator i=maquinariaActividadesFormulaList.iterator();
        while (i.hasNext()){
            MaquinariaActividadesFormula bean=(MaquinariaActividadesFormula)i.next();
            if(bean.getChecked().booleanValue()){
                maquinariaActividadesFormulaEditarList.add(bean);
            }
        }
        return "actionEditarMaquinariaActividadesFM";
    }
    
    
    public void clearMaquinariaActividadesFormula(){
        maquinariaActividadesFormulabean.setHorasHombre("");
        maquinariaActividadesFormulabean.setHorasMaquina("");
        maquinariaActividadesFormulaAdicionarList.clear();
    }
    
    public String guardarActividadesFormulaMaestra(){
        try {
            Iterator i=maquinariaActividadesFormulaAdicionarList.iterator();
            while (i.hasNext()){
                MaquinariaActividadesFormula bean=(MaquinariaActividadesFormula)i.next();
                if(bean.getChecked().booleanValue()){
                    getCodigoMaquinariaActividades();
                    String sql="insert into MAQUINARIA_ACTIVIDADES_FORMULA(COD_MAQUINARIA_ACTIVIDAD,COD_MAQUINA,COD_ACTIVIDAD_FORMULA,HORAS_MAQUINA,HORAS_HOMBRE)values(";
                    sql+="'"+maquinariaActividadesFormulabean.getCodMaquinariaActividad()+"',";
                    sql+="'"+bean.getMaquinaria().getCodMaquina()+"',";
                    sql+="'"+getCodigo()+"',";
                    sql+="'"+bean.getHorasMaquina()+"','"+bean.getHorasHombre()+"')";
                    System.out.println("inset:"+sql);
                    setCon(Util.openConnection(getCon()));
                    PreparedStatement st=getCon().prepareStatement(sql);
                    int result=st.executeUpdate();
                }
            }
            cargarMaquinariaActividadFormula();
            clearMaquinariaActividadesFormula();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return "navegadorMaquinariaActividadesFM";
    }
    public String modificarMquinariaActividades(){
        try {
            Iterator i=maquinariaActividadesFormulaEditarList.iterator();
            while (i.hasNext()){
                MaquinariaActividadesFormula bean=(MaquinariaActividadesFormula)i.next();
                setCon(Util.openConnection(getCon()));
                String  sql="update MAQUINARIA_ACTIVIDADES_FORMULA set";
                sql+=" horas_maquina='"+bean.getHorasMaquina()+"',";
                sql+=" horas_hombre='"+bean.getHorasHombre()+"'";
                sql+=" where COD_MAQUINARIA_ACTIVIDAD="+bean.getCodMaquinariaActividad();
                sql+=" and cod_maquina='"+bean.getMaquinaria().getCodMaquina()+"'";
                System.out.println("modifi:"+sql);
                PreparedStatement st=getCon().prepareStatement(sql);
                int result=st.executeUpdate();
            }
            cargarMaquinariaActividadFormula();
        } catch (SQLException e) {
            e.printStackTrace();
        }        
        return "navegadorMaquinariaActividadesFM";
    }
    
    public String eliminarMaquinariaActividadesFormula(){
        try {
            Iterator i=maquinariaActividadesFormulaList.iterator();
            int result=0;
            while (i.hasNext()){
                MaquinariaActividadesFormula bean=(MaquinariaActividadesFormula)i.next();
                if(bean.getChecked().booleanValue()){
                    String sql = "delete from MAQUINARIA_ACTIVIDADES_FORMULA  ";
                           sql += " where COD_MAQUINARIA_ACTIVIDAD="+bean.getCodMaquinariaActividad();
                    System.out.println("deleteMAQUINARIA_ACTIVIDADES_FORMULA:sql:"+sql);
                    setCon(Util.openConnection(getCon()));
                    Statement st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                    result=result+st.executeUpdate(sql);
                }
            }
            if(result>0){
                cargarMaquinariaActividadFormula();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "navegadorMaquinariaActividadesFM";
    }
    public String Cancelar(){
        maquinariaActividadesFormulaList.clear();
        cargarMaquinariaActividadFormula();
        return "navegadorMaquinariaActividadesFM";
    }
    
    
    
//Metodo que cierra la conexion
    
    public String getCloseConnection() throws SQLException{
        if(getCon()!=null){
            getCon().close();
        }
        return "";
    }
    /**
     * Métodos de la Clase
     */
    
    public MaquinariaActividadesFormula getMaquinariaActividadesFormulabean() {
        return maquinariaActividadesFormulabean;
    }
    
    public void setMaquinariaActividadesFormulabean(MaquinariaActividadesFormula maquinariaActividadesFormulabean) {
        this.maquinariaActividadesFormulabean = maquinariaActividadesFormulabean;
    }
    
    public List getMaquinariaActividadesFormulaList() {
        return maquinariaActividadesFormulaList;
    }
    
    public void setMaquinariaActividadesFormulaList(List maquinariaActividadesFormulaList) {
        this.maquinariaActividadesFormulaList = maquinariaActividadesFormulaList;
    }
    
    public List getMaquinariaActividadesFormulaAdicionarList() {
        return maquinariaActividadesFormulaAdicionarList;
    }
    
    public void setMaquinariaActividadesFormulaAdicionarList(List maquinariaActividadesFormulaAdicionarList) {
        this.maquinariaActividadesFormulaAdicionarList = maquinariaActividadesFormulaAdicionarList;
    }
    
    public List getMaquinariaActividadesFormulaEditarList() {
        return maquinariaActividadesFormulaEditarList;
    }
    
    public void setMaquinariaActividadesFormulaEditarList(List maquinariaActividadesFormulaEditarList) {
        this.maquinariaActividadesFormulaEditarList = maquinariaActividadesFormulaEditarList;
    }
    
    public List getEstadoRegistro() {
        return estadoRegistro;
    }
    
    public void setEstadoRegistro(List estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }
    
    public Connection getCon() {
        return con;
    }
    
    public void setCon(Connection con) {
        this.con = con;
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
    
    public String getCodigo() {
        return codigo;
    }
    
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    public List getMaquinariasList() {
        return maquinariasList;
    }
    
    public void setMaquinariasList(List maquinariasList) {
        this.maquinariasList = maquinariasList;
    }
    
    public List getActividadesList() {
        return actividadesList;
    }
    
    public void setActividadesList(List actividadesList) {
        this.actividadesList = actividadesList;
    }
    
    public String getNombreFormula() {
        return nombreFormula;
    }
    
    public void setNombreFormula(String nombreFormula) {
        this.nombreFormula = nombreFormula;
    }
    
    public String getNombreActividad() {
        return nombreActividad;
    }
    
    public void setNombreActividad(String nombreActividad) {
        this.nombreActividad = nombreActividad;
    }
    
    public String getCodActividad() {
        return codActividad;
    }
    
    public void setCodActividad(String codActividad) {
        this.codActividad = codActividad;
    }
    
    public String getCodFormulaMaestra() {
        return codFormulaMaestra;
    }
    
    public void setCodFormulaMaestra(String codFormulaMaestra) {
        this.codFormulaMaestra = codFormulaMaestra;
    }

    public float getH_hombre() {
        return H_hombre;
    }

    public void setH_hombre(float H_hombre) {
        this.H_hombre = H_hombre;
    }

    public float getH_maquina() {
        return H_maquina;
    }

    public void setH_maquina(float H_maquina) {
        this.H_maquina = H_maquina;
    }

}
