/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cofar.web;
import com.cofar.bean.EstadosIngresoAcond;
import com.cofar.bean.IngresosAcondicionamiento;
import com.cofar.bean.IngresosAlmacenDetalleAcond;
import com.cofar.bean.IngresosdetalleCantidadPeso;
import com.cofar.bean.ProgramaProduccion;
import com.cofar.bean.ProgramaProduccionPeriodo;
import com.cofar.util.Util;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import org.richfaces.component.html.HtmlDataTable;
/**
 *
 * @author sistemas1
 */

public class ManagedCintasSeguridad {
    private Connection con;
    private List programaProduccionPeriodoList=new ArrayList();
    private List programaProduccionList=new ArrayList();
    private ProgramaProduccionPeriodo programaProduccionPeriodo = new ProgramaProduccionPeriodo();
    private ProgramaProduccion programaProduccion = new ProgramaProduccion();
    private List ingresosAcondList = new ArrayList();
    private IngresosAcondicionamiento  ingresosAcondicionamiento =  new IngresosAcondicionamiento();
    private List ingresosDetalleAcondList = new ArrayList();
    private List ingresosDetalleCantidadPesoList = new ArrayList();
    private IngresosAlmacenDetalleAcond ingresosAlmacenDetalleAcond = new IngresosAlmacenDetalleAcond();
    private IngresosdetalleCantidadPeso ingresosDetalleCantPeso = new IngresosdetalleCantidadPeso();
    private EstadosIngresoAcond estadosIngresoAcond = new EstadosIngresoAcond();
    private List estadosIngresoAcondList = new ArrayList();
    private List<ingresoDetalleAcondCantidadPeso> ingresoDetalleAcondCantidadPesoList = new ArrayList<ingresoDetalleAcondCantidadPeso>();
    private HtmlDataTable programaProduccionDataTable;
    
    NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
    DecimalFormat format = (DecimalFormat)nf;

    public class ingresoDetalleAcondCantidadPeso{
        IngresosAcondicionamiento ingresosAcondicionamiento = new IngresosAcondicionamiento();
        IngresosAlmacenDetalleAcond ingresosAlmacenDetalleAcond = new IngresosAlmacenDetalleAcond();
        List<IngresosdetalleCantidadPeso> ingresosDetalleCantPesoList = new ArrayList<IngresosdetalleCantidadPeso>();

        public IngresosAcondicionamiento getIngresosAcondicionamiento() {
            return ingresosAcondicionamiento;
        }

        public void setIngresosAcondicionamiento(IngresosAcondicionamiento ingresosAcondicionamiento) {
            this.ingresosAcondicionamiento = ingresosAcondicionamiento;
        }
        
        public IngresosAlmacenDetalleAcond getIngresosAlmacenDetalleAcond() {
            return ingresosAlmacenDetalleAcond;
        }

        public void setIngresosAlmacenDetalleAcond(IngresosAlmacenDetalleAcond ingresosAlmacenDetalleAcond) {
            this.ingresosAlmacenDetalleAcond = ingresosAlmacenDetalleAcond;
        }

        public List<IngresosdetalleCantidadPeso> getIngresosDetalleCantPesoList() {
            return ingresosDetalleCantPesoList;
        }

        public void setIngresosDetalleCantPesoList(List<IngresosdetalleCantidadPeso> ingresosDetalleCantPesoList) {
            this.ingresosDetalleCantPesoList = ingresosDetalleCantPesoList;
        }


    }

    /** Creates a new instance of ManagedCintasSeguridad */
    public ManagedCintasSeguridad() {
    }

    public List getProgramaProduccionPeriodoList() {
        return programaProduccionPeriodoList;
    }

    public void setProgramaProduccionPeriodoList(List programaProduccionPeriodoList) {
        this.programaProduccionPeriodoList = programaProduccionPeriodoList;
    }

    public ProgramaProduccionPeriodo getProgramaProduccionPeriodo() {
        return programaProduccionPeriodo;
    }

    public void setProgramaProduccionPeriodo(ProgramaProduccionPeriodo programaProduccionPeriodo) {
        this.programaProduccionPeriodo = programaProduccionPeriodo;
    }

    public List getProgramaProduccionList() {
        return programaProduccionList;
    }

    public void setProgramaProduccionList(List programaProduccionList) {
        this.programaProduccionList = programaProduccionList;
    }

    public List getIngresosAcondList() {
        return ingresosAcondList;
    }

    public void setIngresosAcondList(List ingresosAcondList) {
        this.ingresosAcondList = ingresosAcondList;
    }

    public List getIngresosDetalleAcondList() {
        return ingresosDetalleAcondList;
    }

    public void setIngresosDetalleAcondList(List ingresosDetalleAcondList) {
        this.ingresosDetalleAcondList = ingresosDetalleAcondList;
    }

    public List getIngresosDetalleCantidadPesoList() {
        return ingresosDetalleCantidadPesoList;
    }

    public void setIngresosDetalleCantidadPesoList(List ingresosDetalleCantidadPesoList) {
        this.ingresosDetalleCantidadPesoList = ingresosDetalleCantidadPesoList;
    }

    public EstadosIngresoAcond getEstadosIngresoAcond() {
        return estadosIngresoAcond;
    }

    public void setEstadosIngresoAcond(EstadosIngresoAcond estadosIngresoAcond) {
        this.estadosIngresoAcond = estadosIngresoAcond;
    }

    public List getEstadosIngresoAcondList() {
        return estadosIngresoAcondList;
    }

    public void setEstadosIngresoAcondList(List estadosIngresoAcondList) {
        this.estadosIngresoAcondList = estadosIngresoAcondList;
    }

    public List<ingresoDetalleAcondCantidadPeso> getIngresoDetalleAcondCantidadPesoList() {
        return ingresoDetalleAcondCantidadPesoList;
    }

    public void setIngresoDetalleAcondCantidadPesoList(List<ingresoDetalleAcondCantidadPeso> ingresoDetalleAcondCantidadPesoList) {
        this.ingresoDetalleAcondCantidadPesoList = ingresoDetalleAcondCantidadPesoList;
    }

    public HtmlDataTable getProgramaProduccionDataTable() {
        return programaProduccionDataTable;
    }

    public void setProgramaProduccionDataTable(HtmlDataTable programaProduccionDataTable) {
        this.programaProduccionDataTable = programaProduccionDataTable;
    }

  

    public String getCargarDatosCintasSeguridad(){
        try {
            format.applyPattern("#,###.00");
            this.cargaProgramaProduccionPeriodo();
            this.cargarEstadosIngresoAcond();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public void cargaProgramaProduccionPeriodo(){
        try {
            con=Util.openConnection(con);
            String consulta="select pprp.COD_PROGRAMA_PROD,pprp.NOMBRE_PROGRAMA_PROD from PROGRAMA_PRODUCCION_PERIODO pprp where pprp.COD_ESTADO_PROGRAMA in (1,2,6)" ;

            ResultSet rs=null;

            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            rs= st.executeQuery(consulta);
            programaProduccionPeriodoList.clear();
            programaProduccionPeriodoList.add(new SelectItem("0","-NINGUNO-"));
                while (rs.next())
                    programaProduccionPeriodoList.add(new SelectItem(rs.getString("COD_PROGRAMA_PROD"),rs.getString("NOMBRE_PROGRAMA_PROD")));
            
            if(rs!=null){
                rs.close();st.close();
                rs=null;st=null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cargarEstadosIngresoAcond(){
        try {
            con=Util.openConnection(con);
            String consulta="select eia.COD_ESTADO_INGRESOACOND,eia.NOMBRE_ESTADO_INGRESOACOND from ESTADOS_INGRESOSACOND eia where eia.COD_ESTADO_INGRESOACOND in (1,4)" ;

            ResultSet rs=null;

            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            rs= st.executeQuery(consulta);
            estadosIngresoAcondList.clear();
            estadosIngresoAcondList.add(new SelectItem("0","-TODOS-"));
                while (rs.next())
                    estadosIngresoAcondList.add(new SelectItem(rs.getString("COD_ESTADO_INGRESOACOND"),rs.getString("NOMBRE_ESTADO_INGRESOACOND")));

            if(rs!=null){
                rs.close();st.close();
                rs=null;st=null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String programaProduccionPeriodo_change(ActionEvent event) {
        try{
        System.out.println("cambio de programa de produccion periodo o estado de Lote "+programaProduccionPeriodo.getCodProgramaProduccion());

        ProgramaProduccion programaProd = new ProgramaProduccion();
        con=Util.openConnection(con);
        String consulta = " SELECT PPR.COD_PROGRAMA_PROD,CP.nombre_prod_semiterminado,PPR.COD_LOTE_PRODUCCION,PPR.CANT_LOTE_PRODUCCION, TPPR.NOMBRE_TIPO_PROGRAMA_PROD,EPPR.NOMBRE_ESTADO_PROGRAMA_PROD " +
                " FROM PROGRAMA_PRODUCCION PPR INNER JOIN COMPONENTES_PROD CP ON CP.COD_COMPPROD = PPR.COD_COMPPROD  " +
                " INNER JOIN TIPOS_PROGRAMA_PRODUCCION TPPR ON TPPR.COD_TIPO_PROGRAMA_PROD = PPR.COD_TIPO_PROGRAMA_PROD " +
                " INNER JOIN ESTADOS_PROGRAMA_PRODUCCION EPPR ON EPPR.COD_ESTADO_PROGRAMA_PROD = PPR.COD_ESTADO_PROGRAMA " +
                " WHERE PPR.COD_PROGRAMA_PROD = "+ programaProduccionPeriodo.getCodProgramaProduccion() +" " +
                " AND PPR.COD_LOTE_PRODUCCION IN ( " +
                " SELECT IDA.COD_LOTE_PRODUCCION FROM INGRESOS_ACOND IA INNER JOIN INGRESOS_DETALLEACOND IDA ON IA.COD_INGRESO_ACOND = IDA.COD_INGRESO_ACOND " +
                " WHERE IA.COD_ESTADO_INGRESOACOND IN ("+(estadosIngresoAcond.getCodEstadoIngresoAcond()==0?"1,4":estadosIngresoAcond.getCodEstadoIngresoAcond())+") )"; //(1,4)
         
         System.out.println("consulta Programa Produccion" + consulta);
         
         ResultSet rs=null;

            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            rs= st.executeQuery(consulta);
            programaProduccionList.clear();
                while (rs.next()){
                    programaProd = new ProgramaProduccion();
                    programaProd.setCodProgramaProduccion(rs.getString("COD_PROGRAMA_PROD"));
                    programaProd.getFormulaMaestra().getComponentesProd().setNombreProdSemiterminado(rs.getString("nombre_prod_semiterminado"));
                    programaProd.setCodLoteProduccion(rs.getString("COD_LOTE_PRODUCCION"));
                    programaProd.setCantidadLote(rs.getString("CANT_LOTE_PRODUCCION"));
                    programaProd.getTiposProgramaProduccion().setNombreProgramaProd(rs.getString("NOMBRE_TIPO_PROGRAMA_PROD"));
                    programaProd.getEstadoProgramaProduccion().setNombreEstadoProgramaProd(rs.getString("NOMBRE_ESTADO_PROGRAMA_PROD"));
                    programaProduccionList.add(programaProd);
                }                    

            if(rs!=null){
                rs.close();st.close();
                rs=null;st=null;
            }

        

        }catch(Exception e){
        e.printStackTrace();}
        return null;
    }

    


    public void cargarIngresosAcond(String codLoteProduccion) {
        try{
        System.out.println("cambio de programa de produccion periodo"+programaProduccionPeriodo.getCodProgramaProduccion());

        
        IngresosAcondicionamiento ingresosAcond = new IngresosAcondicionamiento();
        
        con=Util.openConnection(con);
        String consulta = " SELECT IA.COD_INGRESO_ACOND,IA.NRO_INGRESOACOND,TI.NOMBRE_TIPOINGRESOACOND,G.NOMBRE_GESTION,AL.NOMBRE_ALMACENACOND,EIA.NOMBRE_ESTADO_INGRESOACOND FROM INGRESOS_ACOND IA " +
                " INNER JOIN TIPOS_INGRESOACOND TI ON IA.COD_TIPOINGRESOACOND = TI.COD_TIPOINGRESOACOND " +
                " INNER JOIN GESTIONES G ON G.COD_GESTION = IA.COD_GESTION " +
                " INNER JOIN ALMACENES_ACOND AL ON AL.COD_ALMACENACOND = IA.COD_ALMACENACOND " +
                " INNER JOIN ESTADOS_INGRESOSACOND EIA ON EIA.COD_ESTADO_INGRESOACOND = IA.COD_ESTADO_INGRESOACOND  " +
                " WHERE IA.COD_INGRESO_ACOND IN (SELECT IDA.COD_INGRESO_ACOND FROM INGRESOS_DETALLEACOND IDA WHERE IDA.COD_LOTE_PRODUCCION IN ('"+codLoteProduccion+"')) " +
                " AND IA.COD_ESTADO_INGRESOACOND IN (1,4)";
        
         System.out.println("consulta Ingreso Acondicionamiento "+consulta);
         
         ResultSet rs=null;

            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            rs= st.executeQuery(consulta);
            ingresosAcondList.clear();
                while (rs.next()){
                    ingresosAcond = new IngresosAcondicionamiento();
                    ingresosAcond.setCodIngresoAcond(rs.getString("COD_INGRESO_ACOND"));
                    ingresosAcond.setNroIngresoAcond(rs.getInt("NRO_INGRESOACOND"));
                    ingresosAcond.getTipoIngresoAcond().setNombreTipoIngresoAcond(rs.getString("NOMBRE_TIPOINGRESOACOND"));
                    ingresosAcond.getGestion().setNombreGestion(rs.getString("NOMBRE_GESTION"));
                    ingresosAcond.getEstadosIngresoAcond().setNombreEstadoIngresoAcond(rs.getString("NOMBRE_ESTADO_INGRESOACOND"));
                    
                    
                    ingresosAcondList.add(ingresosAcond);
                }

            if(rs!=null){
                rs.close();st.close();
                rs=null;st=null;
            }



        }catch(Exception e){
        e.printStackTrace();
        }
    }

    private void cargarDetalleIngresosAcond(String codIngresoAcond){
        try {
            IngresosAlmacenDetalleAcond ingresosDetalleAcond = new IngresosAlmacenDetalleAcond();

        con=Util.openConnection(con);

        String consulta = " SELECT IDA.COD_INGRESO_ACOND,IDA.COD_COMPPROD, CP.nombre_prod_semiterminado,IDA.COD_LOTE_PRODUCCION,IDA.FECHA_VEN,IDA.CANT_INGRESO_PRODUCCION" +                    
                    " FROM INGRESOS_DETALLEACOND IDA INNER JOIN COMPONENTES_PROD CP ON IDA.COD_COMPPROD= CP.COD_COMPPROD " +
                    " WHERE IDA.COD_INGRESO_ACOND = "+codIngresoAcond+" " ;

         System.out.println("consulta Ingreso Acondicionamiento "+consulta);

         ResultSet rs=null;

            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            rs= st.executeQuery(consulta);
            
            ingresosDetalleAcondList.clear();
                while (rs.next()){
                    ingresosDetalleAcond = new IngresosAlmacenDetalleAcond();
                    ingresosDetalleAcond.getIngresosAcondicionamiento().setCodIngresoAcond(rs.getString("COD_INGRESO_ACOND"));
                    ingresosDetalleAcond.getComponentesProd().setCodCompprod(rs.getString("COD_COMPPROD"));
                    ingresosDetalleAcond.getComponentesProd().setNombreProdSemiterminado(rs.getString("nombre_prod_semiterminado"));
                    ingresosDetalleAcond.setCodLoteProduccion(rs.getString("COD_LOTE_PRODUCCION"));
                    ingresosDetalleAcond.setFechaVencimiento(rs.getDate("FECHA_VEN"));
                    ingresosDetalleAcond.setCantIngresoProduccion(format.format(rs.getInt("CANT_INGRESO_PRODUCCION")));                    
                    ingresosDetalleAcondList.add(ingresosDetalleAcond);
                }

            if(rs!=null){
                rs.close();st.close();
                rs=null;st=null;
            }



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String seleccionarProductoProduccion_action(){
        try{            
            
            ProgramaProduccion programaProd = new ProgramaProduccion();
            Iterator i= programaProduccionList.iterator();
            
            while (i.hasNext()){
                programaProd = (ProgramaProduccion)i.next();
                if(programaProd.getChecked().booleanValue()){
                    programaProduccion = programaProd;
                    //this.cargarIngresosAcond(programaProd.getCodLoteProduccion());
                    this.cargarIngresoDetalleCantidadPeso(programaProd.getCodLoteProduccion());                    
                    break;
                }
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public String seleccionarIngresoAcondicionamiento_action(){
        try {            
            IngresosAcondicionamiento ingresosAcond = new IngresosAcondicionamiento();
            Iterator i = ingresosAcondList.iterator();
            while(i.hasNext()){
                ingresosAcond = (IngresosAcondicionamiento)i.next();
                if(ingresosAcond.getChecked().booleanValue()){
                    ingresosAcondicionamiento = ingresosAcond;
                    this.cargarDetalleIngresosAcond(ingresosAcond.getCodIngresoAcond());
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String seleccionarDetalleIngresoAcondicionamiento_action(){
        try {
            IngresosAlmacenDetalleAcond ingresosDetalleAcond = new IngresosAlmacenDetalleAcond();
            
            Iterator i = ingresosDetalleAcondList.iterator();

            while(i.hasNext()){
                ingresosDetalleAcond = (IngresosAlmacenDetalleAcond)i.next();
                if(ingresosDetalleAcond.getChecked().booleanValue()){
                    ingresosAlmacenDetalleAcond = ingresosDetalleAcond;
                    this.cargarDetalleCantidadPeso(ingresosAlmacenDetalleAcond.getIngresosAcondicionamiento().getCodIngresoAcond());
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public String registrarCintasDeSeguridad_action(){
        try {
            ProgramaProduccion itemProgramaProduccion =  (ProgramaProduccion) programaProduccionDataTable.getRowData();
            this.cargarIngresoDetalleCantidadPeso(itemProgramaProduccion.getCodLoteProduccion());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void cargarDetalleCantidadPeso(String codIngresoAcond){
        try {
            con=Util.openConnection(con);
            
        String consulta = " select idcp.COD_INGRESODETALLE_CANTIDADPESO,idcp.COD_INGRESOVENTAS,idcp.COD_PRESENTACION,idcp.CANTIDAD,idcp.PESO,idcp.cod_envase,te.NOMBRE_ENVASE,idcp.NRO_CINTASEGURIDAD_1,idcp.NRO_CINTASEGURIDAD_2 " +
                " from INGRESODETALLE_CANTIDADPESO idcp inner join TIPOS_ENVASE te on idcp.cod_envase = te.COD_ENVASE " +
                " where idcp.COD_INGRESOVENTAS =  "+codIngresoAcond+" " ;

         System.out.println("consulta detalle Cantidad Peso "+consulta);

         ResultSet rs=null;

            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            rs= st.executeQuery(consulta);

            IngresosdetalleCantidadPeso ingresosDetalleCantidadPeso = new IngresosdetalleCantidadPeso();

            ingresosDetalleCantidadPesoList.clear();
            
                while (rs.next()){
                    ingresosDetalleCantidadPeso = new IngresosdetalleCantidadPeso();
                    ingresosDetalleCantidadPeso.setCodIngresoDetalleCantidadPeso(rs.getInt("COD_INGRESODETALLE_CANTIDADPESO"));
                    ingresosDetalleCantidadPeso.setCodIngresosVentas(rs.getInt("COD_INGRESOVENTAS"));
                    ingresosDetalleCantidadPeso.setCodPresentacion(rs.getInt("COD_PRESENTACION"));
                    ingresosDetalleCantidadPeso.setCantidad(rs.getFloat("CANTIDAD"));
                    ingresosDetalleCantidadPeso.setPeso(rs.getFloat("PESO"));
                    ingresosDetalleCantidadPeso.getTiposEnvase().setCodTipoEnvase(rs.getString("cod_envase"));
                    ingresosDetalleCantidadPeso.getTiposEnvase().setNombreTipoEnvase(rs.getString("NOMBRE_ENVASE"));                    
                    ingresosDetalleCantidadPeso.setNroCintaSeguridad1(rs.getInt("NRO_CINTASEGURIDAD_1"));
                    ingresosDetalleCantidadPeso.setNroCintaSeguridad2(rs.getInt("NRO_CINTASEGURIDAD_2"));
                    ingresosDetalleCantidadPesoList.add(ingresosDetalleCantidadPeso);
                }

            if(rs!=null){
                rs.close();st.close();
                rs=null;st=null;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String registrarCintasSeguridad_action(){
        try {
            //iterar y actualizar los registros con los datos
            IngresosdetalleCantidadPeso ingresosDetalleCantidadPeso = new IngresosdetalleCantidadPeso();
            Iterator i = ingresosDetalleCantidadPesoList.iterator();
            con=Util.openConnection(con);
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            String consulta = "";
            while(i.hasNext()){
                ingresosDetalleCantidadPeso = new IngresosdetalleCantidadPeso();
                ingresosDetalleCantidadPeso = (IngresosdetalleCantidadPeso)i.next();
                
                consulta = " update INGRESODETALLE_CANTIDADPESO SET NRO_CINTASEGURIDAD_1 = " + ingresosDetalleCantidadPeso.getNroCintaSeguridad1() +
                        " ,NRO_CINTASEGURIDAD_2 = " + ingresosDetalleCantidadPeso.getNroCintaSeguridad2() +
                        " where COD_INGRESOVENTAS = " + ingresosDetalleCantidadPeso.getCodIngresosVentas() +
                        " and COD_INGRESODETALLE_CANTIDADPESO = " + ingresosDetalleCantidadPeso.getCodIngresoDetalleCantidadPeso() +
                        " and COD_PRESENTACION = " + ingresosDetalleCantidadPeso.getCodPresentacion() +
                        " and cod_envase = " + ingresosDetalleCantidadPeso.getTiposEnvase().getCodTipoEnvase() ;
                
                System.out.println("consulta actualizacion cintas de seguridad " + consulta );                
                st.executeUpdate(consulta);                
            }
            if(st!= null){
                st.close();
                st=null;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void cargarIngresoDetalleCantidadPeso(String codLoteProduccion){
        try {

        IngresosAlmacenDetalleAcond ingresosDetalleAcond = new IngresosAlmacenDetalleAcond();
        IngresosAcondicionamiento ingresosAcond = new IngresosAcondicionamiento();
        
        con=Util.openConnection(con);

        String consulta = " SELECT IA.NRO_INGRESOACOND,IDA.COD_INGRESO_ACOND,IDA.COD_COMPPROD, CP.nombre_prod_semiterminado,IDA.COD_LOTE_PRODUCCION,IDA.FECHA_VEN,IDA.CANT_INGRESO_PRODUCCION" +
                    " FROM INGRESOS_ACOND IA " +
                    " INNER JOIN INGRESOS_DETALLEACOND IDA ON IA.COD_INGRESO_ACOND = IDA.COD_INGRESO_ACOND " +
                    " INNER JOIN COMPONENTES_PROD CP ON IDA.COD_COMPPROD= CP.COD_COMPPROD " +
                    " WHERE IDA.COD_LOTE_PRODUCCION = '"+codLoteProduccion+"' " ;
                    
         System.out.println("consulta Ingreso Acondicionamiento "+consulta);

         ResultSet rs=null;

            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            rs= st.executeQuery(consulta);
            ingresoDetalleAcondCantidadPesoList.clear();
                while (rs.next()){
                    ingresoDetalleAcondCantidadPeso ingresoDetalleAcondCantidadPeso = new ingresoDetalleAcondCantidadPeso();
                    ingresosAcond = new IngresosAcondicionamiento();
                    ingresosDetalleAcond = new IngresosAlmacenDetalleAcond();

                    ingresosAcond.setNroIngresoAcond(rs.getInt("NRO_INGRESOACOND"));
                    ingresosDetalleAcond.getIngresosAcondicionamiento().setCodIngresoAcond(rs.getString("COD_INGRESO_ACOND"));
                    ingresosDetalleAcond.getComponentesProd().setCodCompprod(rs.getString("COD_COMPPROD"));
                    ingresosDetalleAcond.getComponentesProd().setNombreProdSemiterminado(rs.getString("nombre_prod_semiterminado"));
                    ingresosDetalleAcond.setCodLoteProduccion(rs.getString("COD_LOTE_PRODUCCION"));
                    ingresosDetalleAcond.setFechaVencimiento(rs.getDate("FECHA_VEN"));
                    ingresosDetalleAcond.setCantIngresoProduccion(format.format(rs.getInt("CANT_INGRESO_PRODUCCION")));
                    ingresoDetalleAcondCantidadPeso.setIngresosAcondicionamiento(ingresosAcond);
                    ingresoDetalleAcondCantidadPeso.setIngresosAlmacenDetalleAcond(ingresosDetalleAcond);
                    //llenado del detalle de cantidad Peso                    
                    
                    String consultaDetalleCantidadPeso = " select idcp.COD_INGRESODETALLE_CANTIDADPESO,idcp.COD_INGRESOVENTAS,idcp.COD_PRESENTACION,idcp.CANTIDAD,idcp.PESO,idcp.cod_envase,te.NOMBRE_ENVASE,idcp.NRO_CINTASEGURIDAD_1,idcp.NRO_CINTASEGURIDAD_2 " +
                        " from INGRESODETALLE_CANTIDADPESO idcp inner join TIPOS_ENVASE te on idcp.cod_envase = te.COD_ENVASE " +
                        " where idcp.COD_INGRESOVENTAS =  "+ingresosDetalleAcond.getIngresosAcondicionamiento().getCodIngresoAcond()+" " ;

                    System.out.println("consulta detalle Cantidad Peso "+consultaDetalleCantidadPeso);
                    ResultSet rsDetalleCantidadPeso=null;
                    Statement stDetalleCantidadPeso=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                    rsDetalleCantidadPeso= stDetalleCantidadPeso.executeQuery(consultaDetalleCantidadPeso);
                    
                    IngresosdetalleCantidadPeso ingresosDetalleCantidadPeso = new IngresosdetalleCantidadPeso();
                    
                    List<IngresosdetalleCantidadPeso> ingresoDetalleCantidadPesoList = new ArrayList<IngresosdetalleCantidadPeso>();
                while (rsDetalleCantidadPeso.next()){
                    ingresosDetalleCantidadPeso = new IngresosdetalleCantidadPeso();
                    ingresosDetalleCantidadPeso.setCodIngresoDetalleCantidadPeso(rsDetalleCantidadPeso.getInt("COD_INGRESODETALLE_CANTIDADPESO"));
                    ingresosDetalleCantidadPeso.setCodIngresosVentas(rsDetalleCantidadPeso.getInt("COD_INGRESOVENTAS"));
                    ingresosDetalleCantidadPeso.setCodPresentacion(rsDetalleCantidadPeso.getInt("COD_PRESENTACION"));
                    ingresosDetalleCantidadPeso.setCantidad(rsDetalleCantidadPeso.getFloat("CANTIDAD"));
                    ingresosDetalleCantidadPeso.setPeso(rsDetalleCantidadPeso.getFloat("PESO"));
                    ingresosDetalleCantidadPeso.getTiposEnvase().setCodTipoEnvase(rsDetalleCantidadPeso.getString("cod_envase"));
                    ingresosDetalleCantidadPeso.getTiposEnvase().setNombreTipoEnvase(rsDetalleCantidadPeso.getString("NOMBRE_ENVASE"));
                    ingresosDetalleCantidadPeso.setNroCintaSeguridad1(rsDetalleCantidadPeso.getInt("NRO_CINTASEGURIDAD_1"));
                    ingresosDetalleCantidadPeso.setNroCintaSeguridad2(rsDetalleCantidadPeso.getInt("NRO_CINTASEGURIDAD_2"));
                    ingresoDetalleCantidadPesoList.add(ingresosDetalleCantidadPeso);
                }
             ingresoDetalleAcondCantidadPeso.setIngresosDetalleCantPesoList(ingresoDetalleCantidadPesoList);
             ingresoDetalleAcondCantidadPesoList.add(ingresoDetalleAcondCantidadPeso);
             
            if(rsDetalleCantidadPeso!=null){
                rsDetalleCantidadPeso.close();stDetalleCantidadPeso.close();
                rsDetalleCantidadPeso=null;stDetalleCantidadPeso=null;
            }                   
             
        }

            if(rs!=null){
                rs.close();st.close();
                rs=null;st=null;
            }



        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    public String guardarIngresoDetalleAcondCantidadPeso_action(){
        try {
            con=Util.openConnection(con);
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

            for(ingresoDetalleAcondCantidadPeso ingresoAcond:ingresoDetalleAcondCantidadPesoList){
                for(IngresosdetalleCantidadPeso ingresosDetalleCantidadPeso :ingresoAcond.ingresosDetalleCantPesoList){
                    String consulta = " update INGRESODETALLE_CANTIDADPESO SET NRO_CINTASEGURIDAD_1 = " + ingresosDetalleCantidadPeso.getNroCintaSeguridad1() +
                        " ,NRO_CINTASEGURIDAD_2 = " + ingresosDetalleCantidadPeso.getNroCintaSeguridad2() +
                        " where COD_INGRESOVENTAS = " + ingresosDetalleCantidadPeso.getCodIngresosVentas() +
                        " and COD_INGRESODETALLE_CANTIDADPESO = " + ingresosDetalleCantidadPeso.getCodIngresoDetalleCantidadPeso() +
                        " and COD_PRESENTACION = " + ingresosDetalleCantidadPeso.getCodPresentacion() +
                        " and cod_envase = " + ingresosDetalleCantidadPeso.getTiposEnvase().getCodTipoEnvase() ;
                    System.out.println("consulta update" + consulta);
                    st.executeUpdate(consulta);

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }
    


}
