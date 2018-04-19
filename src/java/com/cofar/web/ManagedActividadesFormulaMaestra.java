
/*
 * ManagedCartonesCorrugados.java
 * Created on 25 de Junio de 2008, 10:50
 */

package com.cofar.web;


import com.cofar.bean.ActividadesFormulaMaestra;
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
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem; 

/**
 *
 *  @author Wilson Choquehuanca Gonzales
 *  @company COFAR
 */
public class ManagedActividadesFormulaMaestra{
    
    private ActividadesFormulaMaestra ActividadesFormulaMaestrabean=new ActividadesFormulaMaestra();
    private List ActividadesFormulaMaestraList=new ArrayList();
    private List ActividadesFormulaMaestraAdicionarList=new ArrayList();
    private List ActividadesFormulaMaestraEditarList=new ArrayList();
    private List estadoRegistro=new ArrayList();
    private List actividadesFormulaMaestraEliminar=new ArrayList();
    private List actividadesFormulaMaestraNoEliminar=new ArrayList();
    private Connection con;
    private boolean swEliminaSi;
    private boolean swEliminaNo;
    private String codigo="";
    private List maquinariasList=new ArrayList();
    private List actividadesList=new ArrayList();
    private String nombreComProd="";
    private String codTipoActividadProduccion="";
    private List actividadesMaquinariaFormulaMaestraList=new ArrayList();
    
    public ManagedActividadesFormulaMaestra() {
        //cargarActividadFormulaMaestra();
    }
    public class ActividadesMaquinariaFormulaMaestra{
        private ActividadesFormulaMaestra actividadesFormulaMaestra = new ActividadesFormulaMaestra();
        private MaquinariaActividadesFormula maquinariaActividadesFormula = new MaquinariaActividadesFormula();
        private List maquinariasList = new ArrayList();
        private Boolean checked=false;
        
        public ActividadesFormulaMaestra getActividadesFormulaMaestra() {
            return actividadesFormulaMaestra;
        }

        public void setActividadesFormulaMaestra(ActividadesFormulaMaestra actividadesFormulaMaestra) {
            this.actividadesFormulaMaestra = actividadesFormulaMaestra;
        }

        public MaquinariaActividadesFormula getMaquinariaActividadesFormula() {
            return maquinariaActividadesFormula;
        }

        public void setMaquinariaActividadesFormula(MaquinariaActividadesFormula maquinariaActividadesFormula) {
            this.maquinariaActividadesFormula = maquinariaActividadesFormula;
        }

        public List getMaquinariasList() {
            return maquinariasList;
        }

        public void setMaquinariasList(List maquinariasList) {
            this.maquinariasList = maquinariasList;
        }

        public Boolean getChecked() {
            return checked;
        }

        public void setChecked(Boolean checked) {
            this.checked = checked;
        }

       
    }
    /**
     * metodo que genera los codigos
     * correlativamente
     */
    public String getObtenerCodigo(){
        
        String cod=Util.getParameter("codigo");
        if(Util.getParameter("codTipoActividadProduccion")!=null){
            codTipoActividadProduccion=Util.getParameter("codTipoActividadProduccion");
        }

        System.out.println("cxxxxxxxxxxxxxxxxxxxxxxxod:"+cod);
        System.out.println("codTipoActividadProduccion"+codTipoActividadProduccion);
        if(cod!=null){
            setCodigo(cod);
        }
        cargarActividadFormulaMaestra();
        cargarNombreComProd();
        //this.cargarActividadMaquinariaFormulaMaestra();
        return "";
    }    
    public String cargarNombreComProd(){
        try {
            setCon(Util.openConnection(getCon()));
            String sql=" select cp.nombre_prod_semiterminado";
            sql+=" from COMPONENTES_PROD cp,PRODUCTOS p,FORMULA_MAESTRA fm";
            sql+=" where cp.COD_COMPPROD=fm.COD_COMPPROD and p.cod_prod=cp.COD_PROD";
            sql+=" and fm.COD_FORMULA_MAESTRA='"+getCodigo()+"'";
            System.out.println("sql:-----------:"+sql);
            PreparedStatement st=getCon().prepareStatement(sql);
            ResultSet rs=st.executeQuery();
            while (rs.next())
                setNombreComProd(rs.getString(1));
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("nombreComProd:"+getNombreComProd());
        return  "";
    }
    public String getCodigoActividadesFormulaMaestra(){
        String codigo="1";
        try {
            setCon(Util.openConnection(getCon()));
            String sql="select max(cod_actividad_formula)+1 from actividades_formula_maestra";
            PreparedStatement st=getCon().prepareStatement(sql);
            System.out.println("sql:MAX:"+sql);
            ResultSet rs=st.executeQuery();
            while (rs.next())
                codigo=rs.getString(1);
            if(codigo==null)
                codigo="1";
            
            ActividadesFormulaMaestrabean.setCodActividadFormula(codigo);
            System.out.println("coiogogo:"+codigo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  "";
    }
    
    public void cargarMaquinaria(String codigo,ActividadesFormulaMaestra bean){
        try {
            setCon(Util.openConnection(getCon()));
            String sql="select cod_maquina,nombre_maquina from maquinarias where cod_estado_registro=1";
            sql+=" order by nombre_maquina";
            System.out.println("sql_maquinarias:"+sql);
            ResultSet rs=null;
            
            Statement st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            if(!codigo.equals("")){
                
            } else{
                maquinariasList.clear();
                rs=st.executeQuery(sql);
                while (rs.next())
                    maquinariasList.add(new SelectItem(rs.getString(1),rs.getString(2)));
            }
            if(rs!=null){
                rs.close();st.close();
                rs=null;st=null;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void cargarActividades(){
        try {
            setCon(Util.openConnection(getCon()));
            String sql="select cod_actividad,nombre_actividad from actividades_produccion where cod_estado_registro=1";
            sql+=" and cod_actividad <> all (select f.cod_actividad from ACTIVIDADES_FORMULA_MAESTRA f where f.cod_formula_maestra='"+codigo+"')" +
                 " and cod_tipo_actividad_produccion=" + codTipoActividadProduccion;
            System.out.println("sql_actividades:"+sql);
            System.out.println("cargar:"+sql);
            setCon(Util.openConnection(getCon()));
            Statement st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs=st.executeQuery(sql);
            ActividadesFormulaMaestraAdicionarList.clear();
            while (rs.next()){
                ActividadesFormulaMaestra bean=new ActividadesFormulaMaestra();
                bean.getActividadesProduccion().setCodActividad(rs.getString(1));
                bean.getActividadesProduccion().setNombreActividad(rs.getString(2));
                ActividadesFormulaMaestraAdicionarList.add(bean);
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
    public void cargarActividadFormulaMaestra(){
        try {
            //se cargan las actividades de programa produccion
                        
            String sql="select ap.COD_ACTIVIDAD,fm.COD_FORMULA_MAESTRA,ap.NOMBRE_ACTIVIDAD," ;
            sql+=" af.cod_actividad_formula,af.orden_actividad" ;
            sql+=" from ACTIVIDADES_PRODUCCION ap,ACTIVIDADES_FORMULA_MAESTRA af,FORMULA_MAESTRA fm";
            sql+=" where ap.COD_ACTIVIDAD = af.COD_ACTIVIDAD AND fm.COD_FORMULA_MAESTRA = af.COD_FORMULA_MAESTRA and ";
            sql+=" af.cod_formula_maestra='"+getCodigo()+"' and ap.COD_TIPO_ACTIVIDAD_PRODUCCION="+codTipoActividadProduccion;
            sql+=" order by orden_actividad asc";
            System.out.println("cargar:"+sql);
            setCon(Util.openConnection(getCon()));
            Statement st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs=st.executeQuery(sql);
            rs.last();
            int rows=rs.getRow();
            getActividadesFormulaMaestraList().clear();
            rs.first();
            for(int i=0;i<rows;i++){
                ActividadesFormulaMaestra bean=new ActividadesFormulaMaestra();
                bean.getActividadesProduccion().setCodActividad(rs.getString(1));
                bean.getFormulaMaestra().setCodFormulaMaestra(rs.getString(2));
                bean.getActividadesProduccion().setNombreActividad(rs.getString(3));
                bean.setCodActividadFormula(rs.getString(4));
                bean.setOrdenActividad(rs.getString(5));
                getActividadesFormulaMaestraList().add(bean);
                rs.next();
            }
            
            if(rs!=null){
                rs.close();
                st.close();
            }
            
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void cargarActividadMaquinariaFormulaMaestra(){
        try {
            //se cargan las actividades de programa produccion

            String sql="select ap.COD_ACTIVIDAD,fm.COD_FORMULA_MAESTRA,ap.NOMBRE_ACTIVIDAD," ;
            sql+=" af.cod_actividad_formula,af.orden_actividad" ;
            sql+=" from ACTIVIDADES_PRODUCCION ap,ACTIVIDADES_FORMULA_MAESTRA af,FORMULA_MAESTRA fm";
            sql+=" where ap.COD_ACTIVIDAD = af.COD_ACTIVIDAD AND fm.COD_FORMULA_MAESTRA = af.COD_FORMULA_MAESTRA and ";
            sql+=" af.cod_formula_maestra='"+getCodigo()+"' and ap.COD_TIPO_ACTIVIDAD_PRODUCCION="+codTipoActividadProduccion;
            sql+=" order by orden_actividad asc";
            System.out.println("cargar:"+sql);
            setCon(Util.openConnection(getCon()));
            Statement st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs=st.executeQuery(sql);
            rs.last();
            int rows=rs.getRow();
            actividadesMaquinariaFormulaMaestraList.clear();
            rs.first();
            for(int i=0;i<rows;i++){
                ActividadesMaquinariaFormulaMaestra actividadesMaquinariaFM =new ActividadesMaquinariaFormulaMaestra();
                actividadesMaquinariaFM.getActividadesFormulaMaestra().getActividadesProduccion().setCodActividad(rs.getString("COD_ACTIVIDAD"));
                actividadesMaquinariaFM.getActividadesFormulaMaestra().getFormulaMaestra().setCodFormulaMaestra(rs.getString("COD_FORMULA_MAESTRA"));
                actividadesMaquinariaFM.getActividadesFormulaMaestra().getActividadesProduccion().setNombreActividad(rs.getString("NOMBRE_ACTIVIDAD"));
                actividadesMaquinariaFM.getActividadesFormulaMaestra().setCodActividadFormula(rs.getString("cod_actividad_formula"));
                actividadesMaquinariaFM.getActividadesFormulaMaestra().setOrdenActividad(rs.getString("orden_actividad"));

                String  consulta=" select ma.COD_MAQUINARIA_ACTIVIDAD,(select isnull(M.COD_MAQUINA,'') from MAQUINARIAS M where ma.COD_MAQUINA=M.COD_MAQUINA) COD_MAQUINA, ma.HORAS_MAQUINA,ma.HORAS_HOMBRE " ;
                        consulta+=" from MAQUINARIA_ACTIVIDADES_FORMULA ma" ;
                        consulta+=" where ma.COD_ACTIVIDAD_FORMULA='"+actividadesMaquinariaFM.getActividadesFormulaMaestra().getCodActividadFormula()+"'";                        
                        System.out.println("cargar:"+consulta);
                        setCon(Util.openConnection(getCon()));
                        Statement st1=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                        ResultSet rs1=st1.executeQuery(consulta);
                        rs1.last();
                        int rows1=rs1.getRow();            
                        rs1.first();            
                        for(int j=0;j<rows1;j++){
                            actividadesMaquinariaFM.getMaquinariaActividadesFormula().getActividadesFormulaMaestra().setCodActividadFormula(actividadesMaquinariaFM.getActividadesFormulaMaestra().getCodActividadFormula());
                            actividadesMaquinariaFM.getMaquinariaActividadesFormula().setCodMaquinariaActividad(rs1.getString("COD_MAQUINARIA_ACTIVIDAD"));
                            actividadesMaquinariaFM.getMaquinariaActividadesFormula().getMaquinaria().setCodMaquina(rs1.getString("COD_MAQUINA"));
                            //actividadesMaquinariaFM.getMaquinariaActividadesFormula().getMaquinaria().setNombreMaquina(rs1.getString("NOMBRE_MAQUINA"));
                            actividadesMaquinariaFM.getMaquinariaActividadesFormula().setHorasHombre(rs1.getString("HORAS_HOMBRE"));
                            actividadesMaquinariaFM.getMaquinariaActividadesFormula().setHorasMaquina(rs1.getString("HORAS_MAQUINA"));
                            rs1.next();
                        }
                        
                    if(rs1!=null){
                      rs1.close();
                      st1.close();
                     }
                actividadesMaquinariaFM.setMaquinariasList(this.cargarMaquinariasActividadFormulaMaestra());
                actividadesMaquinariaFormulaMaestraList.add(actividadesMaquinariaFM);
                rs.next();
            }

            if(rs!=null){
                rs.close();
                st.close();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String guardarActividadesMaquinariaFormulaMaestra_action(){
        try {
             Iterator i=actividadesMaquinariaFormulaMaestraList.iterator();
             setCon(Util.openConnection(getCon()));
           

            while (i.hasNext()){
                ActividadesMaquinariaFormulaMaestra actividadesMaquinariaFormMaest = (ActividadesMaquinariaFormulaMaestra) i.next();                
                String consulta=" delete from MAQUINARIA_ACTIVIDADES_FORMULA where COD_MAQUINARIA_ACTIVIDAD= '"+actividadesMaquinariaFormMaest.getMaquinariaActividadesFormula().getCodMaquinariaActividad()+"'" +
                        " and COD_ACTIVIDAD_FORMULA='" + actividadesMaquinariaFormMaest.getActividadesFormulaMaestra().getCodActividadFormula()+"'";
                        
                        PreparedStatement st=getCon().prepareStatement(consulta);
                        System.out.println("consulta delete:"+consulta);
                        int result=st.executeUpdate();
                        
                        String sql="insert into MAQUINARIA_ACTIVIDADES_FORMULA(COD_MAQUINARIA_ACTIVIDAD,COD_MAQUINA,COD_ACTIVIDAD_FORMULA,HORAS_MAQUINA,HORAS_HOMBRE)values(";
                        sql+="'"+this.obtenerCodigoMaquinariaActividadesFormulaMaestra()+"',";
                        sql+="'"+actividadesMaquinariaFormMaest.getMaquinariaActividadesFormula().getMaquinaria().getCodMaquina()+"',";
                        sql+="'"+actividadesMaquinariaFormMaest.getActividadesFormulaMaestra().getCodActividadFormula()+"',";
                        sql+="'"+actividadesMaquinariaFormMaest.getMaquinariaActividadesFormula().getHorasMaquina()+"','"+actividadesMaquinariaFormMaest.getMaquinariaActividadesFormula().getHorasHombre()+"')";
                        System.out.println("inset:"+sql);
                        
                        st=getCon().prepareStatement(sql);
                        int result1=st.executeUpdate();
                        if(result>0 && result1>0){
                            st.close();
                        }

            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.redireccionar("../formula_maestra/navegador_formula_maestra.jsf");
        return null;
    }
    public String redireccionar(String direccion) {
        try {

            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext ext = facesContext.getExternalContext();
            ext.redirect(direccion);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String obtenerCodigoMaquinariaActividadesFormulaMaestra(){
        String codigoMaquinariaActividadesFormulaMaestra="1";
        try {
            setCon(Util.openConnection(getCon()));
            String sql="select MAX(maf.COD_MAQUINARIA_ACTIVIDAD)+1  from MAQUINARIA_ACTIVIDADES_FORMULA maf";
            PreparedStatement st=getCon().prepareStatement(sql);
            System.out.println("sql:MAX:"+sql);
            ResultSet rs=st.executeQuery();
            if(rs.next())
                codigoMaquinariaActividadesFormulaMaestra=rs.getString(1);

            if(codigoMaquinariaActividadesFormulaMaestra==null)
                codigoMaquinariaActividadesFormulaMaestra="1";

            System.out.println("coiogogo:"+codigoMaquinariaActividadesFormulaMaestra);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  codigoMaquinariaActividadesFormulaMaestra;
    }

    public List cargarMaquinariasActividadFormulaMaestra(){
       List maquinariasList = new ArrayList();
        try {
            maquinariasList.clear();
            setCon(Util.openConnection(getCon()));
            String sql=" SELECT M.COD_MAQUINA, M.NOMBRE_MAQUINA " +
                    " FROM MAQUINARIAS M  ";

            System.out.println("select cargarMaquinarias:"+sql);
            ResultSet rs=null;

            Statement st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            rs=st.executeQuery(sql);
                  maquinariasList.add(new SelectItem("0","-NINGUNO-"));
                while(rs.next()){
                    maquinariasList.add(new SelectItem(rs.getString("COD_MAQUINA"),rs.getString("NOMBRE_MAQUINA")));
                }
            if(rs!=null){
                rs.close();st.close();
                rs=null;st=null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
       return maquinariasList;
    }
    public String Guardar(){
        clearActividadesFormulaMaesra();
        cargarActividades();
        //cargarMaquinaria("",null);
        return"actionAgregarActividadesFM";
    }
    
    
    public String actionEditar(){
        //cargarMaquinaria("",null);
        ActividadesFormulaMaestraEditarList.clear();
        Iterator i=ActividadesFormulaMaestraList.iterator();
        while (i.hasNext()){
            ActividadesFormulaMaestra bean=(ActividadesFormulaMaestra)i.next();
            if(bean.getChecked().booleanValue()){
                ActividadesFormulaMaestraEditarList.add(bean);
            }
            
        }
        return "actionEditarActividadesFM";
    }
    
    
    public void clearActividadesFormulaMaesra(){
        
        getActividadesFormulaMaestraAdicionarList().clear();
    }
    
    public String guardarActividadesFormulaMaestra(){
        try {
            Iterator i=getActividadesFormulaMaestraAdicionarList().iterator();
            while (i.hasNext()){
                ActividadesFormulaMaestra bean=(ActividadesFormulaMaestra)i.next();
                if(bean.getChecked().booleanValue()){
                    getCodigoActividadesFormulaMaestra();
                    String sql="insert into actividades_formula_maestra(cod_actividad_formula,cod_actividad,cod_formula_maestra,orden_actividad)values(";
                    sql+="'"+ActividadesFormulaMaestrabean.getCodActividadFormula()+"',";
                    sql+="'"+bean.getActividadesProduccion().getCodActividad()+"',";
                    sql+="'"+codigo+"',";
                    sql+="'"+bean.getOrdenActividad()+"')";
                    System.out.println("inset:"+sql);
                    setCon(Util.openConnection(getCon()));
                    PreparedStatement st=getCon().prepareStatement(sql);
                    int result=st.executeUpdate();
                    
                    sql="insert into ACTIVIDADES_PROGRAMA_PRODUCCION(COD_ACTIVIDAD_PROGRAMA,cod_actividad,cod_formula_maestra,orden_actividad,COD_ESTADO_ACTIVIDAD)values(";
                    sql+="'"+ActividadesFormulaMaestrabean.getCodActividadFormula()+"',";
                    sql+="'"+bean.getActividadesProduccion().getCodActividad()+"',";
                    sql+="'"+codigo+"',";
                    sql+="'"+bean.getOrdenActividad()+"',1)";
                    System.out.println("inset ACTIVIDADES_PROGRAMA_PRODUCCION:"+sql);
                    setCon(Util.openConnection(getCon()));
                    st=getCon().prepareStatement(sql);
                    result=st.executeUpdate();
                    
                    
                }
            }
            cargarActividadFormulaMaestra();
            clearActividadesFormulaMaesra();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return "navegadorActividadesFM";
    }
    public String modificarActividadesFormulaMaestra(){
        try {
            Iterator i=getActividadesFormulaMaestraEditarList().iterator();
            while (i.hasNext()){
                ActividadesFormulaMaestra bean=(ActividadesFormulaMaestra)i.next();
                setCon(Util.openConnection(getCon()));
                String  sql="update actividades_formula_maestra set";
                sql+=" orden_actividad='"+bean.getOrdenActividad()+"'";
                sql+=" where cod_actividad_formula="+bean.getCodActividadFormula();
                System.out.println("modifi:"+sql);
                PreparedStatement st=getCon().prepareStatement(sql);
                int result=st.executeUpdate();
            }
            cargarActividadFormulaMaestra();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "navegadorActividadesFM";
    }
    
    public String eliminarActividadesProduccion(){
        try {
            
            Iterator i=ActividadesFormulaMaestraList.iterator();
            int result=0;
            while (i.hasNext()){
                ActividadesFormulaMaestra bean=(ActividadesFormulaMaestra)i.next();
                if(bean.getChecked().booleanValue()){
                    String sql = "delete from actividades_formula_maestra  ";
                           sql+= " where cod_actividad_formula="+bean.getCodActividadFormula();;
                    System.out.println("deleteActividadesFORMULA:sql:"+sql);
                    setCon(Util.openConnection(getCon()));
                    Statement st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                    result=result+st.executeUpdate(sql);
                    
                    sql="UPDATE ACTIVIDADES_PROGRAMA_PRODUCCION SET COD_ESTADO_ACTIVIDAD=2 ";
                    sql+=" where COD_ACTIVIDAD_PROGRAMA="+bean.getCodActividadFormula();
                    System.out.println("UPDATE ACTIVIDADES_PROGRAMA_PRODUCCION:sql:"+sql);
                    st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                    result=result + st.executeUpdate(sql);
                }
            }
            if(result>0){
                cargarActividadFormulaMaestra();
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "navegadorActividadesFM";
    }

     public String eliminarActividadesFormulaMaestra_action(){
        try {
            System.out.println("Entro a eliminar el componente");
            Iterator i=actividadesMaquinariaFormulaMaestraList.iterator();
            int result=0;
            while (i.hasNext()){
                ActividadesMaquinariaFormulaMaestra actividadesMaquinariaFormulaMaestra = (ActividadesMaquinariaFormulaMaestra)i.next();
                //ActividadesFormulaMaestra bean=(ActividadesFormulaMaestra)i.next();
                System.out.println(" el estado del check "+actividadesMaquinariaFormulaMaestra.getChecked().booleanValue());
                if(actividadesMaquinariaFormulaMaestra.getChecked().booleanValue()){

                    String sql = "delete from actividades_formula_maestra  ";
                           sql+= " where cod_actividad_formula="+ actividadesMaquinariaFormulaMaestra.getActividadesFormulaMaestra().getCodActividadFormula()+ "" ;
                           
                    System.out.println("deleteActividadesFORMULA:sql:"+sql);
                    setCon(Util.openConnection(getCon()));
                    Statement st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                    result=result+st.executeUpdate(sql);

                    sql = "delete from MAQUINARIA_ACTIVIDADES_FORMULA where cod_actividad_formula ="+ actividadesMaquinariaFormulaMaestra.getActividadesFormulaMaestra().getCodActividadFormula()+ "" ;

                    System.out.println("deleteActividadesFORMULA:sql:"+sql);
                    setCon(Util.openConnection(getCon()));
                    st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                    result=result+st.executeUpdate(sql);

                    
                    //delete from MAQUINARIA_ACTIVIDADES_FORMULA where cod_actividad_formula =
                    
                    sql=" UPDATE ACTIVIDADES_PROGRAMA_PRODUCCION SET COD_ESTADO_ACTIVIDAD=2 ";
                    sql+=" where COD_ACTIVIDAD_PROGRAMA="+actividadesMaquinariaFormulaMaestra.getActividadesFormulaMaestra().getCodActividadFormula();
                    //

                    //System.out.println("UPDATE ACTIVIDADES_PROGRAMA_PRODUCCION:sql:"+sql);
                    //st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                    //result=result + st.executeUpdate(sql);
                }
            }
            if(result>0){
                cargarActividadFormulaMaestra();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "navegadorActividadesFM";
    }
    public String Cancelar(){
        //ActividadesFormulaMaestraList.clear();
        //cargarActividadFormulaMaestra();
        return "navegadorActividadesFM";
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
    
    public ActividadesFormulaMaestra getActividadesFormulaMaestrabean() {
        return ActividadesFormulaMaestrabean;
    }
    
    public void setActividadesFormulaMaestrabean(ActividadesFormulaMaestra ActividadesFormulaMaestrabean) {
        this.ActividadesFormulaMaestrabean = ActividadesFormulaMaestrabean;
    }
    
    public List getActividadesFormulaMaestraList() {
        return ActividadesFormulaMaestraList;
    }
    
    public void setActividadesFormulaMaestraList(List ActividadesFormulaMaestraList) {
        this.ActividadesFormulaMaestraList = ActividadesFormulaMaestraList;
    }
    
    public List getActividadesFormulaMaestraAdicionarList() {
        return ActividadesFormulaMaestraAdicionarList;
    }
    
    public void setActividadesFormulaMaestraAdicionarList(List ActividadesFormulaMaestraAdicionarList) {
        this.ActividadesFormulaMaestraAdicionarList = ActividadesFormulaMaestraAdicionarList;
    }
    
    public List getEstadoRegistro() {
        return estadoRegistro;
    }
    
    public void setEstadoRegistro(List estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }
    
    public List getActividadesFormulaMaestraEliminar() {
        return actividadesFormulaMaestraEliminar;
    }
    
    public void setActividadesFormulaMaestraEliminar(List actividadesFormulaMaestraEliminar) {
        this.actividadesFormulaMaestraEliminar = actividadesFormulaMaestraEliminar;
    }
    
    public List getActividadesFormulaMaestraNoEliminar() {
        return actividadesFormulaMaestraNoEliminar;
    }
    
    public void setActividadesFormulaMaestraNoEliminar(List actividadesFormulaMaestraNoEliminar) {
        this.actividadesFormulaMaestraNoEliminar = actividadesFormulaMaestraNoEliminar;
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
    
    public List getActividadesFormulaMaestraEditarList() {
        return ActividadesFormulaMaestraEditarList;
    }
    
    public void setActividadesFormulaMaestraEditarList(List ActividadesFormulaMaestraEditarList) {
        this.ActividadesFormulaMaestraEditarList = ActividadesFormulaMaestraEditarList;
    }
    
    public String getNombreComProd() {
        return nombreComProd;
    }
    
    public void setNombreComProd(String nombreComProd) {
        this.nombreComProd = nombreComProd;
    }

    public List getActividadesMaquinariaFormulaMaestraList() {
        return actividadesMaquinariaFormulaMaestraList;
    }

    public void setActividadesMaquinariaFormulaMaestraList(List actividadesMaquinariaFormulaMaestraList) {
        this.actividadesMaquinariaFormulaMaestraList = actividadesMaquinariaFormulaMaestraList;
    }
    
    
    
}
