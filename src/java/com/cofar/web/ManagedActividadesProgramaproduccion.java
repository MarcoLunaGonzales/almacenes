
/*
 * ManagedCartonesCorrugados.java
 * Created on 25 de Junio de 2008, 10:50
 */

package com.cofar.web;


import com.cofar.bean.ActividadesFormulaMaestra;
import com.cofar.bean.ActividadesProgramaProduccion;
import com.cofar.bean.SeguimientoProgramaProduccion;
import com.cofar.util.Util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
public class ManagedActividadesProgramaproduccion{
    
    private ActividadesProgramaProduccion actividadesProgramaProduccionbean=new ActividadesProgramaProduccion();
    private SeguimientoProgramaProduccion seguimientoProgramaProduccionbean=new SeguimientoProgramaProduccion();
    private List seguimientoProgramaProduccionList=new ArrayList();
    private List seguimientoProgramaProduccionAdicionarList=new ArrayList();
    private List seguimientoProgramaProduccionEditarList=new ArrayList();
    private List estadoRegistro=new ArrayList();
    private List seguimientoProgramaProduccionEliminar=new ArrayList();
    private List seguimientoProgramaProduccionNoEliminar=new ArrayList();
    private Connection con;
    private boolean swEliminaSi;
    private boolean swEliminaNo;
    private String codigo="";
    private String codigoProgramaProd="";
    private String codigoFormulaMaestra="";
    private String codigoCompProd="";
    private String codigoLoteProd="";
    private String codActividad="";
    private List actividadesList=new ArrayList();
    private List actividadesSeguimientoList=new ArrayList();
    private String nombreComProd="";

    private List maquinariasSeguimientoProgramaProduccionList=new ArrayList();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    private String codTipoActividadProduccion ="";
    
    public ManagedActividadesProgramaproduccion() {
        // cargarActividadFormulaMaestra();
    }
    /**
     * metodo que genera los codigos
     * correlativamente
     */
    public class ActividadesSeguimientoProgramaProduccion{
    private ActividadesFormulaMaestra actividadesFormulaMaestra = new ActividadesFormulaMaestra();
    private ActividadesProgramaProduccion actividadesProgramaProduccion = new ActividadesProgramaProduccion();
    private SeguimientoProgramaProduccion seguimientoProgramaProduccion = new SeguimientoProgramaProduccion();
    private Date fechaInicio=new Date(); //fechas de seguimiento de programa produccion
    private Date fechaFinal =new Date();
    private List maquinariasList = new ArrayList();

        public ActividadesFormulaMaestra getActividadesFormulaMaestra() {
            return actividadesFormulaMaestra;
        }

        public void setActividadesFormulaMaestra(ActividadesFormulaMaestra actividadesFormulaMaestra) {
            this.actividadesFormulaMaestra = actividadesFormulaMaestra;
        }
        public ActividadesProgramaProduccion getActividadesProgramaProduccion() {
            return actividadesProgramaProduccion;
        }

        public void setActividadesProgramaProduccion(ActividadesProgramaProduccion actividadesProgramaProduccion) {
            this.actividadesProgramaProduccion = actividadesProgramaProduccion;
        }

        public SeguimientoProgramaProduccion getSeguimientoProgramaProduccion() {
            return seguimientoProgramaProduccion;
        }

        public void setSeguimientoProgramaProduccion(SeguimientoProgramaProduccion seguimientoProgramaProduccion) {
            this.seguimientoProgramaProduccion = seguimientoProgramaProduccion;
        }

        public Date getFechaFinal() {
            return fechaFinal;
        }

        public void setFechaFinal(Date fechaFinal) {
            this.fechaFinal = fechaFinal;
        }

        public Date getFechaInicio() {
            return fechaInicio;
        }

        public void setFechaInicio(Date fechaInicio) {
            this.fechaInicio = fechaInicio;
        }

        public List getMaquinariasList() {
            return maquinariasList;
        }

        public void setMaquinariasList(List maquinariasList) {
            this.maquinariasList = maquinariasList;
        }
    }

    public String getObtenerCodigo(){
        
        String codPrograma=Util.getParameter("codigo");
        String codFormula=Util.getParameter("cod_formula");
        String codComProd=Util.getParameter("cod_comp_prod");
        String codLote=Util.getParameter("cod_lote");
        codTipoActividadProduccion=Util.getParameter("codTipoActividadProduccion")!=null?Util.getParameter("codTipoActividadProduccion"):"";
        
        System.out.println("cxxxxxxxxxxxxxxxxxxxxxxxod:"+codPrograma);
        if(codPrograma!=null){
            setCodigoProgramaProd(codPrograma);
        }
        if(codFormula!=null){
            setCodigoFormulaMaestra(codFormula);
        }
        if(codComProd!=null){
            setCodigoCompProd(codComProd);
        }
        if(codLote!=null){
            setCodigoLoteProd(codLote);
        }
        //cargarActividadProgramaProduccion();
        this.cargarActividadSeguimientoProgramaProduccion();
        cargarNombreComProd();
        return "";
    }
    
    public String getObtenerCodigoSeguimiento(){
        
        String codActividadPrograma=Util.getParameter("codigo");
        String codPrograma=Util.getParameter("cod_programa_prod");
        String codFormula=Util.getParameter("cod_formula_maestra");
        String codComProd=Util.getParameter("cod_com_prod");
        String codLote=Util.getParameter("cod_lote_prod");
        System.out.println("cxxxxxxxxxxxxxxxxxxxxxxxod:"+codPrograma);
        if(codPrograma!=null){
            setCodigoProgramaProd(codPrograma);
        }
        if(codFormula!=null){
            setCodigoFormulaMaestra(codFormula);
        }
        if(codComProd!=null){
            setCodigoCompProd(codComProd);
        }
        if(codLote!=null){
            setCodigoLoteProd(codLote);
        }
        
        if(codActividadPrograma!=null){
            setCodigo(codActividadPrograma);
        }
        
        cargarSeguimientoProgramaProduccion();
        cargarNombreComProd();
        return "";
    }
    public void cargarActividadProgramaProduccion(){
        try {
            
            String sql="select ap.COD_ACTIVIDAD,fm.COD_FORMULA_MAESTRA,ap.NOMBRE_ACTIVIDAD," ;
            sql+=" af.COD_ACTIVIDAD_PROGRAMA,af.orden_actividad" ;
            sql+=" from ACTIVIDADES_PRODUCCION ap,ACTIVIDADES_PROGRAMA_PRODUCCION af,FORMULA_MAESTRA fm";
            sql+=" where ap.COD_ACTIVIDAD = af.COD_ACTIVIDAD AND fm.COD_FORMULA_MAESTRA = af.COD_FORMULA_MAESTRA and ";
            sql+=" af.cod_formula_maestra='"+getCodigoFormulaMaestra()+"' and af.COD_ESTADO_ACTIVIDAD=1";
            sql+=" order by orden_actividad asc";
            System.out.println("cargar:"+sql);
            setCon(Util.openConnection(getCon()));
            Statement st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs=st.executeQuery(sql);
            rs.last();
            int rows=rs.getRow();
            getActividadesList().clear();
            rs.first();
            for(int i=0;i<rows;i++){
                ActividadesProgramaProduccion bean=new ActividadesProgramaProduccion();
                bean.getActividadesProduccion().setCodActividad(rs.getString(1));
                bean.getFormulaMaestra().setCodFormulaMaestra(rs.getString(2));
                bean.getActividadesProduccion().setNombreActividad(rs.getString(3));
                bean.setCodActividadPrograma(rs.getString(4));
                bean.setOrdenActividad(rs.getString(5));
                getActividadesList().add(bean);
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
    public void cargarActividadSeguimientoProgramaProduccion(){
        try {
            
            String sql="select ap.COD_ACTIVIDAD,fm.COD_FORMULA_MAESTRA,ap.NOMBRE_ACTIVIDAD," ;
            sql+=" af.COD_ACTIVIDAD_PROGRAMA,af.orden_actividad" ;
            sql+=" from ACTIVIDADES_PRODUCCION ap,ACTIVIDADES_PROGRAMA_PRODUCCION af,FORMULA_MAESTRA fm";
            sql+=" where ap.COD_ACTIVIDAD = af.COD_ACTIVIDAD AND fm.COD_FORMULA_MAESTRA = af.COD_FORMULA_MAESTRA and ";
            sql+=" af.cod_formula_maestra='"+getCodigoFormulaMaestra()+"' and af.COD_ESTADO_ACTIVIDAD=1 and ap.COD_TIPO_ACTIVIDAD_PRODUCCION="+codTipoActividadProduccion;
            sql+=" order by orden_actividad asc";

            sql =   " select ap.COD_ACTIVIDAD,fm.COD_FORMULA_MAESTRA,ap.NOMBRE_ACTIVIDAD, af.COD_ACTIVIDAD_FORMULA ,af.orden_actividad " +
                    " from ACTIVIDADES_PRODUCCION ap,ACTIVIDADES_FORMULA_MAESTRA af,FORMULA_MAESTRA fm " +
                    " where ap.COD_ACTIVIDAD = af.COD_ACTIVIDAD AND fm.COD_FORMULA_MAESTRA = af.COD_FORMULA_MAESTRA   " +
                    " and af.cod_formula_maestra='"+getCodigoFormulaMaestra()+"'   " +
                    " and ap.COD_TIPO_ACTIVIDAD_PRODUCCION= "+codTipoActividadProduccion+" " +
                    " order by orden_actividad asc ";

            System.out.println("cargar:"+sql);
            setCon(Util.openConnection(getCon()));
            Statement st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs=st.executeQuery(sql);
            rs.last();
            int rows=rs.getRow();
            actividadesSeguimientoList.clear();
            rs.first();
            for(int i=0;i<rows;i++){

                ActividadesSeguimientoProgramaProduccion actividadesSeguimientoProgProd = new ActividadesSeguimientoProgramaProduccion();
                //actividadesSeguimientoProgProd.getActividadesProgramaProduccion().getActividadesProduccion().setCodActividad(rs.getString("COD_ACTIVIDAD"));
                //actividadesSeguimientoProgProd.getActividadesProgramaProduccion().getFormulaMaestra().setCodFormulaMaestra(rs.getString("COD_FORMULA_MAESTRA"));
                //actividadesSeguimientoProgProd.getActividadesProgramaProduccion().getActividadesProduccion().setNombreActividad(rs.getString("NOMBRE_ACTIVIDAD"));
                //actividadesSeguimientoProgProd.getActividadesProgramaProduccion().setCodActividadPrograma(rs.getString("COD_ACTIVIDAD_PROGRAMA"));
                //actividadesSeguimientoProgProd.getActividadesProgramaProduccion().setOrdenActividad(rs.getString("orden_actividad"));

                actividadesSeguimientoProgProd.getActividadesFormulaMaestra().getActividadesProduccion().setCodActividad(rs.getString("COD_ACTIVIDAD"));
                actividadesSeguimientoProgProd.getActividadesFormulaMaestra().getFormulaMaestra().setCodFormulaMaestra(rs.getString("COD_FORMULA_MAESTRA"));
                actividadesSeguimientoProgProd.getActividadesFormulaMaestra().getActividadesProduccion().setNombreActividad(rs.getString("NOMBRE_ACTIVIDAD"));
                actividadesSeguimientoProgProd.getActividadesFormulaMaestra().setCodActividadFormula(rs.getString("COD_ACTIVIDAD_FORMULA"));
                actividadesSeguimientoProgProd.getActividadesFormulaMaestra().setOrdenActividad(rs.getString("orden_actividad"));


            String consulta="select top 1 s.COD_SEGUIMIENTO_PROGRAMA,s.HORAS_HOMBRE,s.HORAS_MAQUINA,s.FECHA_INICIO,s.FECHA_FINAL,s.HORA_INICIO,s.HORA_FINAL" +
                    ",(select isnull(M.COD_MAQUINA,'') from MAQUINARIAS M where s.COD_MAQUINA=M.COD_MAQUINA ) COD_MAQUINA" ;
            consulta+=" from SEGUIMIENTO_PROGRAMA_PRODUCCION s " ;
            consulta+=" where s.COD_ACTIVIDAD_PROGRAMA='"+actividadesSeguimientoProgProd.getActividadesFormulaMaestra().getCodActividadFormula() +"' and s.COD_PROGRAMA_PROD='"+codigoProgramaProd+"' and s.COD_COMPPROD='"+codigoCompProd+"'";
            consulta+=" and s.COD_FORMULA_MAESTRA='"+codigoFormulaMaestra+"' and s.COD_LOTE_PRODUCCION='"+codigoLoteProd+"' order by COD_SEGUIMIENTO_PROGRAMA desc";
            System.out.println("cargar:"+consulta);
            setCon(Util.openConnection(getCon()));
            Statement st1=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs1=st1.executeQuery(consulta);
            rs1.last();
            int filas=rs1.getRow();
            rs1.first();
            System.out.println("consulta del seguimiento programa produccion " +consulta);
            for(int j=0;j<filas;j++){
                actividadesSeguimientoProgProd.getSeguimientoProgramaProduccion().setCodSeguimientoPrograma(rs1.getString("COD_SEGUIMIENTO_PROGRAMA"));
                actividadesSeguimientoProgProd.getSeguimientoProgramaProduccion().setHorasHombre(rs1.getString("HORAS_HOMBRE"));
                actividadesSeguimientoProgProd.getSeguimientoProgramaProduccion().setHorasMaquina(rs1.getString("HORAS_MAQUINA"));
                String fechaAux[]=rs1.getString("FECHA_INICIO").split(" ");
                fechaAux=fechaAux[0].split("-");                
                actividadesSeguimientoProgProd.getSeguimientoProgramaProduccion().setFechaInicio(fechaAux[2]+"/"+fechaAux[1]+"/"+fechaAux[0]);
                actividadesSeguimientoProgProd.setFechaInicio(sdf.parse(fechaAux[0]+"/"+fechaAux[1]+"/"+fechaAux[2]));
                fechaAux=rs1.getString("FECHA_FINAL").split(" ");
                fechaAux=fechaAux[0].split("-");                
                actividadesSeguimientoProgProd.getSeguimientoProgramaProduccion().setFechaFinal(fechaAux[2]+"/"+fechaAux[1]+"/"+fechaAux[0]);                
                actividadesSeguimientoProgProd.setFechaFinal(sdf.parse(fechaAux[0]+"/"+fechaAux[1]+"/"+fechaAux[2]));
                actividadesSeguimientoProgProd.getSeguimientoProgramaProduccion().setHoraInicio(rs1.getString("HORA_INICIO"));
                actividadesSeguimientoProgProd.getSeguimientoProgramaProduccion().setHoraFinal(rs1.getString("HORA_FINAL"));
                actividadesSeguimientoProgProd.getSeguimientoProgramaProduccion().getMaquinaria().setCodMaquina(rs1.getString("COD_MAQUINA"));                
                rs1.next();
            }
                actividadesSeguimientoProgProd.setMaquinariasList(this.cargarMaquinariasActividad(codigoCompProd, codigoFormulaMaestra, actividadesSeguimientoProgProd.getActividadesFormulaMaestra().getActividadesProduccion().getCodActividad()));
                actividadesSeguimientoList.add(actividadesSeguimientoProgProd);
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
   public List cargarMaquinariasActividad(String codCompProd,String codFormulaMaestra,String codActividad){
       List maquinariasList = new ArrayList();
        try {
            maquinariasList.clear();
            setCon(Util.openConnection(getCon()));
            String sql=" SELECT M.COD_MAQUINA, M.NOMBRE_MAQUINA " +
                    " FROM FORMULA_MAESTRA FM INNER JOIN ACTIVIDADES_FORMULA_MAESTRA AFM  " +
                    " ON FM.COD_FORMULA_MAESTRA = AFM.COD_FORMULA_MAESTRA INNER JOIN MAQUINARIA_ACTIVIDADES_FORMULA MAF " +
                    " ON MAF.COD_ACTIVIDAD_FORMULA = AFM.COD_ACTIVIDAD_FORMULA INNER JOIN MAQUINARIAS M ON MAF.COD_MAQUINA = M.COD_MAQUINA " +
                    " WHERE FM.COD_COMPPROD ="+codCompProd+"  AND FM.COD_FORMULA_MAESTRA = '"+codFormulaMaestra+"'  AND AFM.COD_ACTIVIDAD = '"+codActividad+"'" ;

            System.out.println("select cargar maquinarias actividad:"+sql);
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

  /* public String guardarActividadesSeguimientoProgramaProduccion_action(){
        try {

             Iterator i= actividadesSeguimientoList.iterator();
        while (i.hasNext()){
            ActividadesSeguimientoProgramaProduccion actividadesSeguimientoProgProd = (ActividadesSeguimientoProgramaProduccion) i.next();
            //primero borrar los datos de seguimiento del producto
            String consulta = " delete from SEGUIMIENTO_PROGRAMA_PRODUCCION WHERE COD_PROGRAMA_PROD='"+codigoProgramaProd+"'  and COD_COMPPROD='"+codigoCompProd+ "'  " +
                    " and COD_FORMULA_MAESTRA='"+codigoFormulaMaestra+"' and COD_LOTE_PRODUCCION='"+codigoLoteProd+"' and COD_ACTIVIDAD_PROGRAMA='"+actividadesSeguimientoProgProd.getActividadesFormulaMaestra().getCodActividadFormula()+"'";
            setCon(Util.openConnection(getCon()));
            PreparedStatement st=getCon().prepareStatement(consulta);
            System.out.println("borrar seguimiento :"+consulta);
            int result=st.executeUpdate();
            
            //segundo insertar el nuevo seguimiento del producto
            String sql="insert into SEGUIMIENTO_PROGRAMA_PRODUCCION(COD_SEGUIMIENTO_PROGRAMA,COD_PROGRAMA_PROD,COD_COMPPROD,COD_FORMULA_MAESTRA,COD_LOTE_PRODUCCION,";
            sql+=" COD_ACTIVIDAD_PROGRAMA,HORAS_HOMBRE,HORAS_MAQUINA,FECHA_INICIO,FECHA_FINAL,HORA_INICIO,HORA_FINAL,COD_MAQUINA)values(";
            sql+="'"+this.obtenerCodigoSeguimientoPrograma()+"','"+codigoProgramaProd+"','"+codigoCompProd+"','"+codigoFormulaMaestra+"','"+codigoLoteProd+"',";
            sql+="'"+actividadesSeguimientoProgProd.getActividadesProgramaProduccion().getCodActividadPrograma()+"','"+actividadesSeguimientoProgProd.getSeguimientoProgramaProduccion().getHorasHombre()+"','"+actividadesSeguimientoProgProd.getSeguimientoProgramaProduccion().getHorasMaquina()+"',";
            sql+="'"+sdf.format(actividadesSeguimientoProgProd.getFechaInicio())+"','"+sdf.format(actividadesSeguimientoProgProd.getFechaFinal())+"',";
            sql+="'"+actividadesSeguimientoProgProd.getSeguimientoProgramaProduccion().getHoraInicio()+"','"+actividadesSeguimientoProgProd.getSeguimientoProgramaProduccion().getHoraFinal()+"','"+actividadesSeguimientoProgProd.getSeguimientoProgramaProduccion().getMaquinaria().getCodMaquina()+"')";

            sql="insert into SEGUIMIENTO_PROGRAMA_PRODUCCION(COD_SEGUIMIENTO_PROGRAMA,COD_PROGRAMA_PROD,COD_COMPPROD,COD_FORMULA_MAESTRA,COD_LOTE_PRODUCCION,";
            sql+=" COD_ACTIVIDAD_PROGRAMA,HORAS_HOMBRE,HORAS_MAQUINA,FECHA_INICIO,FECHA_FINAL,HORA_INICIO,HORA_FINAL,COD_MAQUINA)values(";
            sql+="'"+this.obtenerCodigoSeguimientoPrograma()+"','"+codigoProgramaProd+"','"+codigoCompProd+"','"+codigoFormulaMaestra+"','"+codigoLoteProd+"',";
            sql+="'"+actividadesSeguimientoProgProd.getActividadesFormulaMaestra().getCodActividadFormula()+"','"+actividadesSeguimientoProgProd.getSeguimientoProgramaProduccion().getHorasHombre()+"','"+actividadesSeguimientoProgProd.getSeguimientoProgramaProduccion().getHorasMaquina()+"',";
            sql+="'"+sdf.format(actividadesSeguimientoProgProd.getFechaInicio())+"','"+sdf.format(actividadesSeguimientoProgProd.getFechaFinal())+"',";
            sql+="'"+actividadesSeguimientoProgProd.getSeguimientoProgramaProduccion().getHoraInicio()+"','"+actividadesSeguimientoProgProd.getSeguimientoProgramaProduccion().getHoraFinal()+"','"+actividadesSeguimientoProgProd.getSeguimientoProgramaProduccion().getMaquinaria().getCodMaquina()+"')";
            //cuando guarde la actividad Pesaje y las horas hombre o las horas maquina sean mayor a cero se debe cambiar el estado
            // de Programa Produccion  a estado 'EN PROCESO'
            this.verificaProcesoPesaje(actividadesSeguimientoProgProd);

            System.out.println("insertar seguimiento :"+sql);
            PreparedStatement st1=getCon().prepareStatement(sql);
            int result1=st1.executeUpdate();
        }

        } catch (Exception e) {
            e.printStackTrace();
        }
        this.redireccionar("../programaProduccion/navegador_programa_produccion.jsf");
        return null;
    }*/
  /* public void verificaProcesoPesaje(ActividadesSeguimientoProgramaProduccion actividadesSeguimientoProgramaProduccion){
       try {
            if(actividadesSeguimientoProgramaProduccion.getActividadesFormulaMaestra().getActividadesProduccion().getCodActividad().equals("76")
                    && (Float.valueOf(actividadesSeguimientoProgramaProduccion.getSeguimientoProgramaProduccion().getHorasHombre())>0 ||
                        Float.valueOf(actividadesSeguimientoProgramaProduccion.getSeguimientoProgramaProduccion().getHorasMaquina())>0)){
                String consulta = " UPDATE PROGRAMA_PRODUCCION  SET COD_ESTADO_PROGRAMA = 7 WHERE COD_PROGRAMA_PROD = '" + codigoProgramaProd +"'"+
                    " AND COD_COMPPROD ='"+codigoCompProd+"' AND COD_FORMULA_MAESTRA ='"+codigoFormulaMaestra+"' AND COD_LOTE_PRODUCCION='"+codigoLoteProd+"' " +
                    " AND COD_ESTADO_PROGRAMA<>6 ";
                System.out.println("consulta cambio estado programa produccion " + consulta);

                setCon(Util.openConnection(getCon()));
                Statement st = con.createStatement();
                st.executeUpdate(consulta);
            }
       } catch (Exception e) {
           e.printStackTrace();
       }
   }*/

  
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

   public String obtenerCodigoSeguimientoPrograma(){
        String codigoSeguimientoPrograma="1";
        try {
            setCon(Util.openConnection(getCon()));
            String sql="select MAX(s.COD_SEGUIMIENTO_PROGRAMA)+1 from SEGUIMIENTO_PROGRAMA_PRODUCCION s";
            PreparedStatement st=getCon().prepareStatement(sql);
            System.out.println("sql:MAX:"+sql);
            ResultSet rs=st.executeQuery();
            while (rs.next())
                codigoSeguimientoPrograma=rs.getString(1);
            if(codigoSeguimientoPrograma==null)
                codigoSeguimientoPrograma="1";
            
            System.out.println("coiogogo:"+codigoSeguimientoPrograma);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  codigoSeguimientoPrograma;
    }

    public void cargarSeguimientoProgramaProduccion(){
        try {
            
            String sql="select s.COD_SEGUIMIENTO_PROGRAMA,s.HORAS_HOMBRE,s.HORAS_MAQUINA,s.FECHA_INICIO,s.FECHA_FINAL,s.HORA_INICIO,s.HORA_FINAL,M.COD_MAQUINA,M.NOMBRE_MAQUINA" ;
            sql+=" from SEGUIMIENTO_PROGRAMA_PRODUCCION s INNER JOIN MAQUINARIAS M on s.COD_MAQUINA=M.COD_MAQUINA " ;
            sql+=" where s.COD_ACTIVIDAD_PROGRAMA='"+codigo+"' and s.COD_PROGRAMA_PROD='"+codigoProgramaProd+"' and s.COD_COMPPROD='"+codigoCompProd+"'";
            sql+=" and s.COD_FORMULA_MAESTRA='"+codigoFormulaMaestra+"' and s.COD_LOTE_PRODUCCION='"+codigoLoteProd+"' ";
            System.out.println("cargar:"+sql);
            setCon(Util.openConnection(getCon()));
            Statement st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs=st.executeQuery(sql);
            rs.last();
            int rows=rs.getRow();
            seguimientoProgramaProduccionList.clear();
            rs.first();
            for(int i=0;i<rows;i++){
                SeguimientoProgramaProduccion bean=new SeguimientoProgramaProduccion();
                bean.setCodSeguimientoPrograma(rs.getString(1));
                bean.setHorasHombre(rs.getString(2));
                bean.setHorasMaquina(rs.getString(3));
                String fechaAux[]=rs.getString(4).split(" ");
                fechaAux=fechaAux[0].split("-");
                bean.setFechaInicio(fechaAux[2]+"/"+fechaAux[1]+"/"+fechaAux[0]);
                fechaAux=rs.getString(5).split(" ");
                fechaAux=fechaAux[0].split("-");
                bean.setFechaFinal(fechaAux[2]+"/"+fechaAux[1]+"/"+fechaAux[0]);
                bean.setHoraInicio(rs.getString(6));
                bean.setHoraFinal(rs.getString(7));
                bean.getMaquinaria().setCodMaquina(rs.getString("COD_MAQUINA"));
                bean.getMaquinaria().setNombreMaquina(rs.getString("NOMBRE_MAQUINA"));
                seguimientoProgramaProduccionList.add(bean);
                rs.next();
            }            
            if(rs!=null){
                rs.close();
                st.close();
            }
            codActividad= this.obtieneCodActividad();
            this.cargarMaquinarias();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String obtieneCodActividad(){
        String codigoActividad="";
        try {
            String sql="select APPR.COD_ACTIVIDAD FROM ACTIVIDADES_PROGRAMA_PRODUCCION APPR   " ;
            sql+=" where APPR.COD_ACTIVIDAD_PROGRAMA='"+codigo+"' ";            
            System.out.println("cargar cod_actividad:"+sql);
            setCon(Util.openConnection(getCon()));
            Statement st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs=st.executeQuery(sql);
            if(rs.next()){
                codigoActividad = rs.getString("COD_ACTIVIDAD");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return codigoActividad;
    }

    public String cargarNombreComProd(){
        try {
            setCon(Util.openConnection(getCon()));
            String sql=" select cp.nombre_prod_semiterminado";
            sql+=" from COMPONENTES_PROD cp,PRODUCTOS p,FORMULA_MAESTRA fm";
            sql+=" where cp.COD_COMPPROD=fm.COD_COMPPROD and p.cod_prod=cp.COD_PROD";
            sql+=" and fm.COD_FORMULA_MAESTRA='"+getCodigoFormulaMaestra()+"'";
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
    public String Guardar(){
        clearActividadesFormulaMaesra();
        return"actionAgregarSeguimientoPrograma";
    }
    public String clearActividadesFormulaMaesra(){
        SeguimientoProgramaProduccion e=new SeguimientoProgramaProduccion();
        seguimientoProgramaProduccionbean=e;
        return "";
    }
    
    public void cargarMaquinarias(){
        try {
            maquinariasSeguimientoProgramaProduccionList.clear();
            setCon(Util.openConnection(getCon()));
            String sql=" SELECT M.COD_MAQUINA, M.NOMBRE_MAQUINA " +
                    " FROM FORMULA_MAESTRA FM INNER JOIN ACTIVIDADES_FORMULA_MAESTRA AFM  " +
                    " ON FM.COD_FORMULA_MAESTRA = AFM.COD_FORMULA_MAESTRA INNER JOIN MAQUINARIA_ACTIVIDADES_FORMULA MAF " +
                    " ON MAF.COD_ACTIVIDAD_FORMULA = AFM.COD_ACTIVIDAD_FORMULA INNER JOIN MAQUINARIAS M ON MAF.COD_MAQUINA = M.COD_MAQUINA " +
                    " WHERE FM.COD_COMPPROD ="+codigoCompProd+"  AND FM.COD_FORMULA_MAESTRA = '"+codigoFormulaMaestra+"'  AND AFM.COD_ACTIVIDAD = '"+codActividad+"'" ;
            
            System.out.println("select cargarProductos:"+sql);
            ResultSet rs=null;
            
            Statement st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            rs=st.executeQuery(sql);
                if(rs.next()){
                    maquinariasSeguimientoProgramaProduccionList.add(new SelectItem(rs.getString("COD_MAQUINA"),rs.getString("NOMBRE_MAQUINA")));
                }            
            if(rs!=null){
                rs.close();st.close();
                rs=null;st=null;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String Cancelar(){
        
        return "navegadorSeguimientoPrograma";
    }
    public String getCodigoSeguimientoPrograma(){
        String codigo="1";
        try {
            setCon(Util.openConnection(getCon()));
            String sql="select MAX(s.COD_SEGUIMIENTO_PROGRAMA)+1 from SEGUIMIENTO_PROGRAMA_PRODUCCION s";
            PreparedStatement st=getCon().prepareStatement(sql);
            System.out.println("sql:MAX:"+sql);
            ResultSet rs=st.executeQuery();
            while (rs.next())
                codigo=rs.getString(1);
            if(codigo==null)
                codigo="1";
            
            seguimientoProgramaProduccionbean.setCodSeguimientoPrograma(codigo);
            System.out.println("coiogogo:"+codigo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  "";
    }
    public String guardarActividadesFormulaMaestra(){
        try {
            
            getCodigoSeguimientoPrograma();
            String fechaInicio[]=seguimientoProgramaProduccionbean.getFechaInicio().split("/");
            seguimientoProgramaProduccionbean.setFechaInicio(fechaInicio[2]+"/"+fechaInicio[1]+"/"+fechaInicio[0]);
            
            String fechaFinal[]=seguimientoProgramaProduccionbean.getFechaFinal().split("/");
            seguimientoProgramaProduccionbean.setFechaFinal(fechaFinal[2]+"/"+fechaFinal[1]+"/"+fechaFinal[0]);
            
            String sql="insert into SEGUIMIENTO_PROGRAMA_PRODUCCION(COD_SEGUIMIENTO_PROGRAMA,COD_PROGRAMA_PROD,COD_COMPPROD,COD_FORMULA_MAESTRA,COD_LOTE_PRODUCCION,";
            sql+=" COD_ACTIVIDAD_PROGRAMA,HORAS_HOMBRE,HORAS_MAQUINA,FECHA_INICIO,FECHA_FINAL,HORA_INICIO,HORA_FINAL,COD_MAQUINA)values(";
            sql+="'"+seguimientoProgramaProduccionbean.getCodSeguimientoPrograma()+"','"+codigoProgramaProd+"','"+codigoCompProd+"','"+codigoFormulaMaestra+"','"+codigoLoteProd+"',";
            sql+="'"+codigo+"','"+seguimientoProgramaProduccionbean.getHorasHombre()+"','"+seguimientoProgramaProduccionbean.getHorasMaquina()+"',";
            sql+="'"+seguimientoProgramaProduccionbean.getFechaInicio()+"','"+seguimientoProgramaProduccionbean.getFechaFinal()+"',";
            sql+="'"+seguimientoProgramaProduccionbean.getHoraInicio()+"','"+seguimientoProgramaProduccionbean.getHoraFinal()+"','"+seguimientoProgramaProduccionbean.getMaquinaria().getCodMaquina()+"')";
            System.out.println("inset:"+sql);
            setCon(Util.openConnection(getCon()));
            PreparedStatement st=getCon().prepareStatement(sql);
            int result=st.executeUpdate();
            
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        cargarSeguimientoProgramaProduccion();
        clearActividadesFormulaMaesra();
        return "navegadorSeguimientoPrograma";
    }
    public String actionEditar(){
        //cargarMaquinaria("",null);
        //seguimientoProgramaProduccionList.clear();
        Iterator i=seguimientoProgramaProduccionList.iterator();
        while (i.hasNext()){
            SeguimientoProgramaProduccion bean=(SeguimientoProgramaProduccion)i.next();
            if(bean.getChecked().booleanValue()){
                seguimientoProgramaProduccionbean=bean;
                this.cargarMaquinarias();
            }
            
        }
        return "actionEditarSeguimientoPrograma";
    }
    
    public String modificarSeguimientoPrograma(){
        try {
            String fechaInicio[]=seguimientoProgramaProduccionbean.getFechaInicio().split("/");
            seguimientoProgramaProduccionbean.setFechaInicio(fechaInicio[2]+"/"+fechaInicio[1]+"/"+fechaInicio[0]);
            
            String fechaFinal[]=seguimientoProgramaProduccionbean.getFechaFinal().split("/");
            seguimientoProgramaProduccionbean.setFechaFinal(fechaFinal[2]+"/"+fechaFinal[1]+"/"+fechaFinal[0]);
            
            setCon(Util.openConnection(getCon()));
            String  sql="update SEGUIMIENTO_PROGRAMA_PRODUCCION set";
            sql+=" HORAS_HOMBRE='"+seguimientoProgramaProduccionbean.getHorasHombre()+"',";
            sql+=" HORAS_MAQUINA='"+seguimientoProgramaProduccionbean.getHorasMaquina()+"',";
            sql+=" FECHA_INICIO='"+seguimientoProgramaProduccionbean.getFechaInicio()+"',";
            sql+=" FECHA_FINAL='"+seguimientoProgramaProduccionbean.getFechaFinal()+"',";
            sql+=" HORA_INICIO='"+seguimientoProgramaProduccionbean.getHoraInicio()+"',";
            sql+=" HORA_FINAL='"+seguimientoProgramaProduccionbean.getHoraFinal()+"'," +
                 " COD_MAQUINA= " + seguimientoProgramaProduccionbean.getMaquinaria().getCodMaquina()+ " ";
            sql+=" where COD_SEGUIMIENTO_PROGRAMA="+seguimientoProgramaProduccionbean.getCodSeguimientoPrograma();
            System.out.println("modifi:"+sql);
            PreparedStatement st=getCon().prepareStatement(sql);
            int result=st.executeUpdate();
            
            cargarSeguimientoProgramaProduccion();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return "navegadorSeguimientoPrograma";
    }
        public String eliminarSeguimientoPrograma(){
        try {
    
            Iterator i=seguimientoProgramaProduccionList.iterator();
            int result=0;
            while (i.hasNext()){
                SeguimientoProgramaProduccion bean=(SeguimientoProgramaProduccion)i.next();
                if(bean.getChecked().booleanValue()){
                    String sql="delete from SEGUIMIENTO_PROGRAMA_PRODUCCION  ";
                    sql+=" where COD_SEGUIMIENTO_PROGRAMA="+bean.getCodSeguimientoPrograma();;
                    System.out.println("delete SEGUIMIENTO_PROGRAMA_PRODUCCION:sql:"+sql);
                    setCon(Util.openConnection(getCon()));
                    Statement st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                    result=result+st.executeUpdate(sql);
                }
            }
    
            if(result>0){
                cargarSeguimientoProgramaProduccion();
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "navegadorSeguimientoPrograma";
    }
    
 /*   public String getCodigoActividadesFormulaMaestra(){
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
            sql+=" and cod_actividad <> all (select f.cod_actividad from ACTIVIDADES_FORMULA_MAESTRA f where f.cod_formula_maestra='"+getCodigo()+"')";
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
    
 /*   public String Guardar(){
        clearActividadesFormulaMaesra();
        cargarActividades();
        //cargarMaquinaria("",null);
        return"actionAgregarActividadesFM";
    }*/
    
    
   /* public String actionEditar(){
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
                    sql+="'"+getCodigo()+"',";
                    sql+="'"+bean.getOrdenActividad()+"')";
                    System.out.println("inset:"+sql);
                    setCon(Util.openConnection(getCon()));
                    PreparedStatement st=getCon().prepareStatement(sql);
                    int result=st.executeUpdate();
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
                    String sql="delete from actividades_formula_maestra  ";
                    sql+=" where cod_actividad_formula="+bean.getCodActividadFormula();;
                    System.out.println("deleteActividadesFORMULA:sql:"+sql);
                    setCon(Util.openConnection(getCon()));
                    Statement st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                    result=result+st.executeUpdate(sql);
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
    public String Cancelar(){
        ActividadesFormulaMaestraList.clear();
        cargarActividadFormulaMaestra();
        return "navegadorActividadesFM";
    }
    
    */
    
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
    
    public ActividadesProgramaProduccion getActividadesProgramaProduccionbean() {
        return actividadesProgramaProduccionbean;
    }
    
    public void setActividadesProgramaProduccionbean(ActividadesProgramaProduccion actividadesProgramaProduccionbean) {
        this.actividadesProgramaProduccionbean = actividadesProgramaProduccionbean;
    }
    
    public SeguimientoProgramaProduccion getSeguimientoProgramaProduccionbean() {
        return seguimientoProgramaProduccionbean;
    }
    
    public void setSeguimientoProgramaProduccionbean(SeguimientoProgramaProduccion seguimientoProgramaProduccionbean) {
        this.seguimientoProgramaProduccionbean = seguimientoProgramaProduccionbean;
    }
    
    public List getSeguimientoProgramaProduccionList() {
        return seguimientoProgramaProduccionList;
    }
    
    public void setSeguimientoProgramaProduccionList(List seguimientoProgramaProduccionList) {
        this.seguimientoProgramaProduccionList = seguimientoProgramaProduccionList;
    }
    
    public List getSeguimientoProgramaProduccionAdicionarList() {
        return seguimientoProgramaProduccionAdicionarList;
    }
    
    public void setSeguimientoProgramaProduccionAdicionarList(List seguimientoProgramaProduccionAdicionarList) {
        this.seguimientoProgramaProduccionAdicionarList = seguimientoProgramaProduccionAdicionarList;
    }
    
    public List getSeguimientoProgramaProduccionEditarList() {
        return seguimientoProgramaProduccionEditarList;
    }
    
    public void setSeguimientoProgramaProduccionEditarList(List seguimientoProgramaProduccionEditarList) {
        this.seguimientoProgramaProduccionEditarList = seguimientoProgramaProduccionEditarList;
    }
    
    public List getEstadoRegistro() {
        return estadoRegistro;
    }
    
    public void setEstadoRegistro(List estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }
    
    public List getSeguimientoProgramaProduccionEliminar() {
        return seguimientoProgramaProduccionEliminar;
    }
    
    public void setSeguimientoProgramaProduccionEliminar(List seguimientoProgramaProduccionEliminar) {
        this.seguimientoProgramaProduccionEliminar = seguimientoProgramaProduccionEliminar;
    }
    
    public List getSeguimientoProgramaProduccionNoEliminar() {
        return seguimientoProgramaProduccionNoEliminar;
    }
    
    public void setSeguimientoProgramaProduccionNoEliminar(List seguimientoProgramaProduccionNoEliminar) {
        this.seguimientoProgramaProduccionNoEliminar = seguimientoProgramaProduccionNoEliminar;
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
    
    public String getCodigoProgramaProd() {
        return codigoProgramaProd;
    }
    
    public void setCodigoProgramaProd(String codigoProgramaProd) {
        this.codigoProgramaProd = codigoProgramaProd;
    }
    
    public String getCodigoFormulaMaestra() {
        return codigoFormulaMaestra;
    }
    
    public void setCodigoFormulaMaestra(String codigoFormulaMaestra) {
        this.codigoFormulaMaestra = codigoFormulaMaestra;
    }
    
    public String getCodigoCompProd() {
        return codigoCompProd;
    }
    
    public void setCodigoCompProd(String codigoCompProd) {
        this.codigoCompProd = codigoCompProd;
    }
    
    public String getCodigoLoteProd() {
        return codigoLoteProd;
    }
    
    public void setCodigoLoteProd(String codigoLoteProd) {
        this.codigoLoteProd = codigoLoteProd;
    }
    
    public List getActividadesList() {
        return actividadesList;
    }
    
    public void setActividadesList(List actividadesList) {
        this.actividadesList = actividadesList;
    }
    
    public String getNombreComProd() {
        return nombreComProd;
    }
    
    public void setNombreComProd(String nombreComProd) {
        this.nombreComProd = nombreComProd;
    }

    public List getMaquinariasSeguimientoProgramaProduccionList() {
        return maquinariasSeguimientoProgramaProduccionList;
    }

    public void setMaquinariasSeguimientoProgramaProduccionList(List maquinariasSeguimientoProgramaProduccionList) {
        this.maquinariasSeguimientoProgramaProduccionList = maquinariasSeguimientoProgramaProduccionList;
    }

    public List getActividadesSeguimientoList() {
        return actividadesSeguimientoList;
    }

    public void setActividadesSeguimientoList(List actividadesSeguimientoList) {
        this.actividadesSeguimientoList = actividadesSeguimientoList;
    }
 
    
}
