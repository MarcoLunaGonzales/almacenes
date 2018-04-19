/*
 * ManagedTipoCliente.java
 * Created on 19 de febrero de 2008, 16:50
 */

package com.cofar.web;

import com.cofar.bean.ComponentesProd;
import com.cofar.bean.AccionesTerapeuticas;
import com.cofar.bean.PresentacionesPrimarias;
import com.cofar.bean.PrincipiosActivosProducto;
import com.cofar.util.Util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.ResultDataModel;
import javax.faces.model.SelectItem;
import javax.servlet.jsp.jstl.sql.ResultSupport;
import org.ajax4jsf.component.html.HtmlAjaxCommandLink;

/**
 *
 *  @author Wilmer Manzaneda Chavez
 *  @company COFAR
 */
public class ManagedComponentesProducto extends ManagedBean{
    
    /** Creates a new instance of ManagedTipoCliente */
    private ComponentesProd componentesProdbean=new ComponentesProd();
    private List componentesProductoList=new ArrayList();
    private List componentesProductoEliminar=new ArrayList();
    private List componentesProductoNoEliminar=new ArrayList();
    private Connection con;
    private List productosList=new  ArrayList();
    private List detalleList=new  ArrayList();
    private List productosFormasFarList=new ArrayList();
    private List saboresProductoList = new ArrayList();
    private List envasesPrimariosList=new  ArrayList();
    private List coloresProductoList=new  ArrayList();
    private List areasEmpresaList=new ArrayList();
    private List estadoRegistro=new  ArrayList();
    private String codigo="";
    private String nombreProducto="";
    private boolean swEliminaSi;
    private boolean swEliminaNo;
    private List productosproceso=new ArrayList();
    private List presentacionesPrimariasList=new ArrayList();
    private String codigoPP="";
    private PresentacionesPrimarias presentacionesPrimarias=new PresentacionesPrimarias();
    private String nombreComProd="";
    private ResultDataModel resultado=new ResultDataModel();
    private List accionesTerapeuticasList=new ArrayList();
    private List estadosCompProdList=new ArrayList();
    private String accionTerapeutica ="";
    
    public ManagedComponentesProducto() {
        
    }
    public void cargarProductosProceso(){
        try {
            con=Util.openConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
    /**
     * metodo que genera los codigos
     * correlativamente
     */
    public String getObtenerCodigo(){
        
        String cod=Util.getParameter("codigo");
        System.out.println("cxxxxxxxxxxxxxxxxxxxxxxxod:"+cod);
        if(cod!=null){
            setCodigo(cod);
        }
        cargarComponentesProducto();
        cargarProductos("",null);
        cargarEstadoCompProd("",null);
        //cargarNombreProducto();
        
        return "";
        
    }
    public String getCodigoProductosVenta(){
        String codigo="1";
        try {
            setCon(Util.openConnection(getCon()));
            String sql="select max(cod_compprod)+1 from componentes_prod";
            PreparedStatement st=getCon().prepareStatement(sql);
            ResultSet rs=st.executeQuery();
            while (rs.next())
                codigo=rs.getString(1);
            if(codigo==null)
                codigo="1";
            
            getComponentesProdbean().setCodCompprod(codigo);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  "";
    }
    
    
    public String CancelarProducto(){
        return"CancelarProductos";
    }
    
    public void cargarProductos(String cod,ComponentesProd bean){
        try {
            setCon(Util.openConnection(getCon()));
            String sql="select cod_prod,nombre_prod from productos" +
                    " where cod_estado_prod=1" ;
            
            System.out.println("select ALL:"+sql);
            ResultSet rs=null;
            
            Statement st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            if(!cod.equals("")){
                sql+=" and cod_prod="+cod;
                System.out.println("update:"+sql);
                rs=st.executeQuery(sql);
                if(rs.next()){
                    bean.getProducto().setCodProducto(rs.getString(1));
                    bean.getProducto().setNombreProducto(rs.getString(2));
                }
            } else{
                sql+=" order by nombre_prod";
                productosList.clear();
                rs=st.executeQuery(sql);
                productosList.add(new SelectItem("0",""));
                while (rs.next())
                    productosList.add(new SelectItem(rs.getString(1),rs.getString(2)));
            }
            if(rs!=null){
                rs.close();st.close();
                rs=null;st=null;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void cargarEstadoCompProd(String cod,ComponentesProd bean){
        try {
            setCon(Util.openConnection(getCon()));
            String sql="select COD_ESTADO_COMPPROD,NOMBRE_ESTADO_COMPPROD from ESTADOS_COMPPROD WHERE COD_ESTADO_REGISTRO=1" ;
            
            System.out.println("select ALL:"+sql);
            ResultSet rs=null;
            
            Statement st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            if(!cod.equals("")){
                
            } else{
                sql+=" order by NOMBRE_ESTADO_COMPPROD";
                estadosCompProdList.clear();
                rs=st.executeQuery(sql);
                estadosCompProdList.add(new SelectItem("0","Todos"));
                while (rs.next())
                    estadosCompProdList.add(new SelectItem(rs.getString(1),rs.getString(2)));
            }
            if(rs!=null){
                rs.close();st.close();
                rs=null;st=null;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    public void cargarProductosFormasFar(String cod,ComponentesProd bean){
        try {
            setCon(Util.openConnection(getCon()));
            String sql="select cod_forma,nombre_forma from formas_farmaceuticas" +
                    " where cod_estado_registro=1" ;
            
            System.out.println("select ALL:"+sql);
            ResultSet rs=null;
            
            Statement st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            if(!cod.equals("")){
                sql+=" and  cod_forma="+cod;
                System.out.println("update:"+sql);
                rs=st.executeQuery(sql);
                if(rs.next()){
                    bean.getForma().setCodForma(rs.getString(1));
                    bean.getForma().setNombreForma(rs.getString(2));
                }
            } else{
                sql+=" order by nombre_forma";
                getProductosFormasFarList().clear();
                rs=st.executeQuery(sql);
                productosFormasFarList.add(new SelectItem("0",""));
                while (rs.next())
                    getProductosFormasFarList().add(new SelectItem(rs.getString(1),rs.getString(2)));
            }
            if(rs!=null){
                rs.close();st.close();
                rs=null;st=null;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void cargarSaborProducto(String cod,ComponentesProd bean){
        try {
            setCon(Util.openConnection(getCon()));
            String sql="select cod_sabor,nombre_sabor " +
                    " from sabores_producto where cod_estado_registro=1 " ;
            
            System.out.println("select ALL:"+sql);
            ResultSet rs=null;
            
            Statement st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            if(!cod.equals("")){
                sql+=" and cod_sabor="+cod;
                System.out.println("update:"+sql);
                rs=st.executeQuery(sql);
                if(rs.next()){
                    bean.getSaboresProductos().setCodSabor(rs.getString(1));
                    bean.getSaboresProductos().setNombreSabor(rs.getString(2));
                }
            } else{
                sql+=" order by nombre_sabor";
                getSaboresProductoList().clear();
                rs=st.executeQuery(sql);
                getSaboresProductoList().add(new SelectItem("0","       "));
                while (rs.next())
                    getSaboresProductoList().add(new SelectItem(rs.getString(1),rs.getString(2)));
            }
            if(rs!=null){
                rs.close();st.close();
                rs=null;st=null;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void cargarEnvasePrimario(String codigo,ComponentesProd bean){
        try {
            setCon(Util.openConnection(getCon()));
            String sql="select cod_envaseprim,nombre_envaseprim " +
                    "from envases_primarios where cod_estado_registro=1";
            ResultSet rs=null;
            
            Statement st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            if(!codigo.equals("")){
                sql+=" and cod_envaseprim="+codigo;
                System.out.println("update:"+sql);
                rs=st.executeQuery(sql);
                if(rs.next()){
                    bean.getEnvasesPrimarios().setCodEnvasePrim(rs.getString(1));
                    bean.getEnvasesPrimarios().setNombreEnvasePrim(rs.getString(2));
                }
            } else{
                sql+=" order by nombre_envaseprim";
                envasesPrimariosList.clear();
                rs=st.executeQuery(sql);
                envasesPrimariosList.add(new SelectItem("0",""));
                while (rs.next())
                    envasesPrimariosList.add(new SelectItem(rs.getString(1),rs.getString(2)));
            }
            if(rs!=null){
                rs.close();st.close();
                rs=null;st=null;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void cargarColorPresentacion(String codigo,ComponentesProd bean){
        try {
            setCon(Util.openConnection(getCon()));
            String sql="select cod_colorpresprimaria,nombre_colorpresprimaria " +
                    "from colores_presprimaria where cod_estado_registro=1";
            ResultSet rs=null;
            
            Statement st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            if(!codigo.equals("")){
                sql+=" and cod_colorpresprimaria="+codigo;
                System.out.println("update:"+sql);
                rs=st.executeQuery(sql);
                
                if(rs.next()){
                    bean.getColoresPresentacion().setCodColor(rs.getString(1));
                    bean.getColoresPresentacion().setNombreColor(rs.getString(2));
                }
            } else{
                sql+=" order by nombre_colorpresprimaria";
                getColoresProductoList().clear();
                rs=st.executeQuery(sql);
                getColoresProductoList().add(new SelectItem("0","      "));
                while (rs.next())
                    getColoresProductoList().add(new SelectItem(rs.getString(1),rs.getString(2)));
            }
            if(rs!=null){
                rs.close();st.close();
                rs=null;st=null;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void cargarAreasEmpresa(String codigo,ComponentesProd bean){
        try {
            setCon(Util.openConnection(getCon()));
            String sql="select cod_area_empresa,nombre_area_empresa" +
                    " from areas_empresa where cod_estado_registro=1";
            ResultSet rs=null;
            sql+=" and cod_area_empresa in(80,81,82,95)";
            Statement st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            if(!codigo.equals("")){
                sql+=" and cod_area_empresa="+codigo;
                System.out.println("update:"+sql);
                rs=st.executeQuery(sql);
                if(rs.next()){
                    bean.getAreasEmpresa().setCodAreaEmpresa(rs.getString(1));
                    bean.getAreasEmpresa().setNombreAreaEmpresa(rs.getString(2));
                }
            } else{
                sql+=" order by nombre_area_empresa";
                areasEmpresaList.clear();
                areasEmpresaList.add(new SelectItem("0",""));
                rs=st.executeQuery(sql);
                while (rs.next())
                    areasEmpresaList.add(new SelectItem(rs.getString(1),rs.getString(2)));
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
    
    public List obtenerPrincipioActivo(String codCompProd){
        
        
        List array=new ArrayList();
        try {
            
            String sql="select d.cod_principio_activo,d.concentracion," +
                    "p.nombre_principio_activo" +
                    " from principios_activos p,componentes_proddetalle d " +
                    " where d.cod_principio_activo=p.cod_principio_activo and " +
                    " d.cod_compprod="+codCompProd+" " ;
            
            System.out.println("sqlPRINCIPIOS:"+sql);
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs=st.executeQuery(sql);
            while (rs.next()){
                PrincipiosActivosProducto bean=new PrincipiosActivosProducto();
                bean.getPrincipiosActivos().setCodPrincipioActivo(rs.getString(1));
                bean.setConcentracion(rs.getString(2));
                bean.getPrincipiosActivos().setNombrePrincipioActivo(rs.getString(3));
                array.add(bean);
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return array;
    }
    public void cargarComponentesProducto(){
        try {
            String sql="select c.cod_compprod,c.cod_prod,c.cod_forma,c.cod_envaseprim," +
                    " c.cod_colorpresprimaria,c.volumenpeso_envaseprim,c.cantidad_compprod," +
                    " c.cod_area_empresa,c.cod_sabor,cod_compuestoprod," +
                    " c.nombre_prod_semiterminado,c.nombre_generico,c.reg_sanitario,c.vida_util,c.FECHA_VENCIMIENTO_RS," +
                    " c.COD_ESTADO_COMPPROD,e.NOMBRE_ESTADO_COMPPROD" +
                    " from componentes_prod c,ESTADOS_COMPPROD e" +
                    " where e.COD_ESTADO_COMPPROD=c.COD_ESTADO_COMPPROD";
            String codigo=componentesProdbean.getProducto().getCodProducto();
            codigo=(codigo.equals("")?"0":codigo);
            componentesProdbean.getProducto().setCodProducto(codigo);
            if(!componentesProdbean.getProducto().getCodProducto().equals("0"))
                sql+=" and c.cod_prod="+componentesProdbean.getProducto().getCodProducto();
            System.out.println("componentesProdbean.getEstadoCompProd().getCodEstadoCompProd():"+componentesProdbean.getEstadoCompProd().getCodEstadoCompProd());
            if(!componentesProdbean.getEstadoCompProd().getCodEstadoCompProd().equals("0")&& !componentesProdbean.getEstadoCompProd().getCodEstadoCompProd().equals(""))
                sql+=" and c.COD_ESTADO_COMPPROD="+componentesProdbean.getEstadoCompProd().getCodEstadoCompProd();
            
            sql+=" order by c.nombre_prod_semiterminado ";
            System.out.println("cargar:"+sql);
            con=Util.openConnection(con);
            Statement st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs=st.executeQuery(sql);
            rs.last();
            int rows=rs.getRow();
            componentesProductoList.clear();
            rs.first();
            String cod="";
            for(int i=0;i<rows;i++){
                ComponentesProd bean=new ComponentesProd();
                String codCompProd=rs.getString(1);
                List list=obtenerPrincipioActivo(codCompProd);
                bean.setCodCompprod(codCompProd);
                bean.getProducto().setCodProducto(rs.getString(2));
                cod=rs.getString(3);
                cod=(cod==null)?"":cod;
                //System.out.println("st xxx:"+cod);
                cargarProductosFormasFar(cod,bean);
                //bean.setConcentracionForma(rs.getString(4));
                cod=rs.getString(4);
                cod=(cod==null)?"":cod;
                //System.out.println("st xxx:"+cod);
                cargarEnvasePrimario(cod,bean);
                cod=rs.getString(5);
                cod=(cod==null)?"":cod;
                //System.out.println("st xxx:"+cod);
                cargarColorPresentacion(cod,bean);
                bean.setVolumenPesoEnvasePrim(rs.getString(6));
                bean.setCantidadCompprod(rs.getString(7));
                cod=rs.getString(8);
                cod=(cod==null)?"":cod;
                //System.out.println("st xxx:"+cod);
                cargarAreasEmpresa(cod,bean);
                cod=rs.getString(9);
                cod=(cod==null)?"":cod;
                //System.out.println("st xxx:"+cod);
                cargarSaborProducto(cod,bean);
                //bean.getProducto().setNombreProducto(rs.getString(10));
                bean.setCodcompuestoprod(rs.getString(10));
                bean.setNombreProdSemiterminado(rs.getString(11));
                bean.setNombreGenerico(rs.getString(12));
                bean.setRegSanitario(rs.getString(13));
                bean.setVidaUtil(rs.getString(14));
                
                bean.setFechaVencimientoRS(rs.getDate(15));
                if(bean.getCodcompuestoprod().equals("2"))
                    bean.setColumnStyle("codcompuestoprod");
                else
                    bean.setColumnStyle("nocodcompuestoprod");
                bean.getEstadoCompProd().setCodEstadoCompProd(rs.getString(16));
                bean.getEstadoCompProd().setNombreEstadoCompProd(rs.getString(17));
                bean.setPrincipiosList(list);
                componentesProductoList.add(bean);
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
    public String Guardar(){
        productosList.clear();
        componentesProdbean.getProducto().setCodProducto("0");
        componentesProdbean.getEstadoCompProd().setCodEstadoCompProd("0");
        clearComponentesProd();
        cargarColorPresentacion("",null);
        cargarProductosFormasFar("",null);
        cargarEnvasePrimario("",null);
        cargarSaborProducto("",null);
        cargarAreasEmpresa("",null);
        cargarProductos("",null);
        cargarEstadoCompProd("",null);
        detalleList.clear();
        
        return"actionAgregarComponentesProd";
    }
    public String actionEditar(){
        cargarColorPresentacion("",null);
        cargarProductosFormasFar("",null);
        cargarEnvasePrimario("",null);
        cargarSaborProducto("",null);
        cargarAreasEmpresa("",null);
        cargarProductos("",null);
        cargarEstadoCompProd("",null);
        
        Iterator i=getComponentesProductoList().iterator();
        while (i.hasNext()){
            ComponentesProd bean=(ComponentesProd)i.next();
            detalleList.clear();
            if(bean.getChecked().booleanValue()){
                try {
                    setComponentesProdbean(bean);
                    String codCompProd=componentesProdbean.getCodCompprod();
                    System.out.println("codCompProd:"+codCompProd);
                    
                    
                    String sql=" select a.cod_accion_terapeutica,a.nombre_accion_terapeutica" +
                            " from acciones_terapeuticas a,acciones_terapeuticas_producto d ";
                    sql+=" where d.cod_accion_terapeutica = a.cod_accion_terapeutica and" +
                            " d.cod_compprod="+codCompProd+"";
                    System.out.println("cargar:"+sql);
                    setCon(Util.openConnection(getCon()));
                    Statement st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                    ResultSet rs=st.executeQuery(sql);
                    int index=1;
                    while (rs.next()){
                        ComponentesProd bean1=new ComponentesProd();
                        bean1.getAccionesTerapeuticas().setCodAccionTerapeutica(rs.getString(1));
                        bean1.getAccionesTerapeuticas().setNombreAccionTerapeutica(rs.getString(2));
                        bean1.setCodTemp(index);
                        index++;
                        detalleList.add(bean1);
                    }
                } catch (SQLException s) {
                    s.printStackTrace();
                }
                break;
            }
            
        }
        return "actionEditarComponentesProd";
    }
    public String actionEliminar(){
        setSwEliminaSi(false);
        setSwEliminaNo(false);
        componentesProductoNoEliminar.clear();
        componentesProductoEliminar.clear();
        int bandera=0;
        Iterator i=componentesProductoList.iterator();
        try {
            while (i.hasNext()){
                ComponentesProd bean=(ComponentesProd)i.next();
                if(bean.getChecked().booleanValue()){
                    
                    String sql="select cod_compprod from ingresos_detalleacond " +
                            " where cod_compprod="+bean.getCodCompprod();
                    setCon(Util.openConnection(getCon()));
                    Statement st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                    ResultSet rs=st.executeQuery(sql);
                    rs.last();
                    if(rs.getRow()==0){
                        bandera=1;
                    }
                    if(bandera==1){
                        sql="select cod_compprod from salidas_detalleacond " +
                                " where cod_compprod="+bean.getCodCompprod();
                        setCon(Util.openConnection(getCon()));
                        st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                        rs=st.executeQuery(sql);
                        rs.last();
                        if(rs.getRow()==0){
                            bandera=1;
                        }else{
                            bandera=0;
                        }
                    }
                    if(bandera==1){
                        sql="select cod_compprod from salidas_detalleingresoacond " +
                                " where cod_compprod="+bean.getCodCompprod();
                        setCon(Util.openConnection(getCon()));
                        st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                        rs=st.executeQuery(sql);
                        rs.last();
                        if(rs.getRow()==0){
                            bandera=1;
                        }else{
                            bandera=0;
                        }
                    }
                    if(bandera==1){
                        sql="select cod_compprod from componentes_proddetalle " +
                                " where cod_compprod="+bean.getCodCompprod();
                        setCon(Util.openConnection(getCon()));
                        st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                        rs=st.executeQuery(sql);
                        rs.last();
                        if(rs.getRow()==0){
                            bandera=1;
                        }else{
                            bandera=0;
                        }
                    }
                    if(bandera==1){
                        sql="select cod_compprod from componentes_presprod " +
                                " where cod_compprod="+bean.getCodCompprod();
                        setCon(Util.openConnection(getCon()));
                        st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                        rs=st.executeQuery(sql);
                        rs.last();
                        if (rs.getRow()==0){
                            bandera=1;
                        }else{
                            bandera=0;
                        }
                    }
                    if(bandera==1){
                        sql=" SELECT S.COD_PROD FROM SALIDAS_ALMACEN S WHERE S.COD_PROD="+bean.getCodCompprod();
                        System.out.println("ENTRO ELIMINAR"+sql);
                        setCon(Util.openConnection(getCon()));
                        st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                        rs=st.executeQuery(sql);
                        rs.last();
                        if (rs.getRow()==0){
                            bandera=1;
                            System.out.println("ENTRO ELIMINAR");
                        } else{
                            bandera=0;
                            System.out.println("ENTRO no ELIMINAR");
                        }
                    }
                    
                    //FORZAMOS QUE INGRESE A ELIMINAR
                    //bandera=1;
                    if (bandera==1){
                        getComponentesProductoEliminar().add(bean);
                        setSwEliminaSi(true);
                        System.out.println("ENTRO ELIMINAR");
                    }else{
                        getComponentesProductoNoEliminar().add(bean);
                        setSwEliminaNo(true);
                        System.out.println("ENTRO NO ELIMINAR");
                    }
                    if(rs!=null){
                        rs.close();st.close();
                        rs=null;st=null;
                    }
                    
                    
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "eliminarComponentesProd";
    }
    
    public void clearComponentesProd(){
        getComponentesProdbean().setConcentracionForma("");
        getComponentesProdbean().setVolumenPesoEnvasePrim("");
        getComponentesProdbean().setCantidadCompprod("");
        
    }
    
    public String guardarComponentesProd(){
        System.out.println("entro en el evento...");
        System.out.println("componentesProdbean.codcompuestoprod:"+componentesProdbean.getCodcompuestoprod());
        getCodigoProductosVenta();
        try {
            SimpleDateFormat df=new SimpleDateFormat("yyyy/MM/dd");
            String fechaVencRS=df.format(getComponentesProdbean().getFechaVencimientoRS());
            String sql="insert into componentes_prod(cod_compprod,cod_prod,cod_forma," +
                    " cod_colorpresprimaria,volumenpeso_envaseprim,cantidad_compprod,cod_area_empresa," +
                    " cod_sabor,cod_compuestoprod,nombre_prod_semiterminado,nombre_generico,reg_sanitario," +
                    " vida_util,FECHA_VENCIMIENTO_RS,COD_ESTADO_COMPPROD) values(" ;
            sql+=" '"+getComponentesProdbean().getCodCompprod()+"'," ;
            sql+=" '"+getComponentesProdbean().getProducto().getCodProducto()+"'," ;
            sql+=" '"+getComponentesProdbean().getForma().getCodForma()+"'," ;
            //sql+=" '"+getComponentesProdbean().getConcentracionForma()+"'," ;
            //sql+=" '"+getComponentesProdbean().getEnvasesPrimarios().getCodEnvasePrim()+"'," ;
            sql+=" '"+getComponentesProdbean().getColoresPresentacion().getCodColor()+"'," ;
            sql+=" '"+getComponentesProdbean().getVolumenPesoEnvasePrim()+"'," ;
            sql+=" '"+getComponentesProdbean().getCantidadCompprod()+"'," ;
            sql+=" '"+getComponentesProdbean().getAreasEmpresa().getCodAreaEmpresa()+"'," ;
            sql+=" '"+getComponentesProdbean().getSaboresProductos().getCodSabor()+"'," ;
            sql+=" '"+getComponentesProdbean().getCodcompuestoprod()+"'," ;
            sql+=" '"+getComponentesProdbean().getNombreProdSemiterminado()+"'," ;
            sql+=" '"+getComponentesProdbean().getNombreGenerico()+"'," ;
            sql+=" '"+getComponentesProdbean().getRegSanitario()+"'," ;
            sql+=" '"+getComponentesProdbean().getVidaUtil()+"','"+fechaVencRS+"',1)" ;
            System.out.println("insert:"+sql);
            con=Util.openConnection(con);
            PreparedStatement st=con.prepareStatement(sql);
            int result=st.executeUpdate();
            
            if(componentesProdbean.getCodcompuestoprod().equals("2")){
                sql="update COMPONENTES_PROD set  cod_compuestoprod=2 where cod_prod="+componentesProdbean.getProducto().getCodProducto();
                System.out.println("COMPONENTES_PROD:"+sql);
                Statement st5=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                st5.executeUpdate(sql);
            }
            if(result>0){
                //cargarComponentesProducto();
                clearComponentesProd();
            }
            
            Iterator i=detalleList.iterator();
            int result1=0;
            sql="";
            while (i.hasNext()){
                ComponentesProd bean=(ComponentesProd)i.next();
                sql="insert into acciones_terapeuticas_producto(cod_compprod,cod_accion_terapeutica)" +
                        "  values(" ;
                sql+=" '"+componentesProdbean.getCodCompprod()+"'," ;
                sql+=" '"+bean.getAccionesTerapeuticas().getCodAccionTerapeutica()+"')" ;
                System.out.println("INSERTANDO acciones_terapeuticas_producto:"+sql);
                setCon(Util.openConnection(getCon()));
                PreparedStatement st1=getCon().prepareStatement(sql);
                result1=st1.executeUpdate();
            }
            
            if(result1>0){
                cargarComponentesProducto();
                clearComponentesProd();
            }
            
            System.out.println("result:"+result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        componentesProdbean=new ComponentesProd();
        return "navegadorComponentesProd";
    }
    public String modificarComponentesProd(){
        try {
            System.out.println("entrtrotortorotortorot*****:");
            setCon(Util.openConnection(getCon()));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            String  sql="update componentes_prod set";
            
            sql+=" cod_prod= '"+getComponentesProdbean().getProducto().getCodProducto()+"'," ;
            sql+="cod_forma= '"+getComponentesProdbean().getForma().getCodForma()+"'," ;
            //sql+="concentracion_forma= '"+getComponentesProdbean().getConcentracionForma()+"'," ;
            //sql+="cod_envaseprim= '"+getComponentesProdbean().getEnvasesPrimarios().getCodEnvasePrim()+"'," ;
            sql+="cod_colorpresprimaria= '"+getComponentesProdbean().getColoresPresentacion().getCodColor()+"'," ;
            sql+="volumenpeso_envaseprim='"+getComponentesProdbean().getVolumenPesoEnvasePrim()+"'," ;
            sql+="cantidad_compprod= '"+getComponentesProdbean().getCantidadCompprod()+"'," ;
            sql+="cod_area_empresa= '"+getComponentesProdbean().getAreasEmpresa().getCodAreaEmpresa()+"'," ;
            sql+="cod_sabor= '"+getComponentesProdbean().getSaboresProductos().getCodSabor()+"'," ;
            sql+="nombre_prod_semiterminado='"+getComponentesProdbean().getNombreProdSemiterminado()+"',";
            
            sql+="nombre_generico= '"+getComponentesProdbean().getNombreGenerico()+"'," ;
            sql+="reg_sanitario= '"+getComponentesProdbean().getRegSanitario()+"'," ;
            sql+="vida_util='"+getComponentesProdbean().getVidaUtil()+"',";
            sql+="COD_ESTADO_COMPPROD='"+getComponentesProdbean().getEstadoCompProd().getCodEstadoCompProd()+"'," +
                 " FECHA_VENCIMIENTO_RS = '"+sdf.format(getComponentesProdbean().getFechaVencimientoRS())+"' ";
            sql+=" where cod_compprod='"+getComponentesProdbean().getCodCompprod()+"'" ;
            System.out.println("update:"+sql);
            PreparedStatement st=getCon().prepareStatement(sql);
            int result=st.executeUpdate();
            if(result>0){
                cargarComponentesProducto();
            }
            sql="delete from componentes_proddetalle  ";
            sql+=" where cod_compprod="+componentesProdbean.getCodCompprod()+"  " ;
            System.out.println("deleteprincipio:sql:"+sql);
            setCon(Util.openConnection(getCon()));
            Statement st1=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            result=result+st1.executeUpdate(sql);
            /////////inserta componentes_proddedtalle/////////////
            sql="delete from acciones_terapeuticas_producto  ";
            sql+=" where cod_compprod="+componentesProdbean.getCodCompprod()+"  " ;
            System.out.println("deleteprincipio:sql:"+sql);
            setCon(Util.openConnection(getCon()));
            st1=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            result=result+st1.executeUpdate(sql);
            /////////inserta componentes_proddedtalle/////////////
            Iterator i=detalleList.iterator();
            int result1=0;
            sql="";
            while (i.hasNext()){
                ComponentesProd bean=(ComponentesProd)i.next();
                sql="insert into acciones_terapeuticas_producto(cod_compprod,cod_accion_terapeutica)" +
                        "  values(" ;
                sql+=" '"+componentesProdbean.getCodCompprod()+"'," ;
                sql+=" '"+bean.getAccionesTerapeuticas().getCodAccionTerapeutica()+"')" ;
                System.out.println("inset Componentes DetlleList22:"+sql);
                setCon(Util.openConnection(getCon()));
                PreparedStatement st2=getCon().prepareStatement(sql);
                result1=st2.executeUpdate();
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return "navegadorComponentesProd";
    }
    public String eliminarComponentesProd(){
        try {
            
            Iterator i=getComponentesProductoEliminar().iterator();
            int result=0;
            while (i.hasNext()){
                ComponentesProd bean=(ComponentesProd)i.next();
                setCon(Util.openConnection(getCon()));
                String sqlPresentacion="select cod_presentacion from componentes_presprod where cod_compprod="+bean.getCodCompprod();
                
                System.out.println("LISTADO PRESENTACIONES: "+sqlPresentacion);
                
                Statement stmPresentacion=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rsDelete=stmPresentacion.executeQuery(sqlPresentacion);
                Statement stmDelete=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                String sqlDelete="";
                while(rsDelete.next()){
                    String codPresentacion=rsDelete.getString("cod_presentacion");
                    sqlDelete="delete from presentaciones_producto where cod_presentacion="+codPresentacion;
                    System.out.println("DELETE PRESENTACIONES: "+sqlDelete);
                    
                    stmDelete.executeUpdate(sqlDelete);
                }
                sqlDelete="delete from componentes_presprod where cod_compprod="+bean.getCodCompprod();
                System.out.println("DELETE PRESENTACIONES COMPONENTES: "+sqlDelete);
                
                stmDelete.executeUpdate(sqlDelete);
                String sql="delete from componentes_proddetalle where cod_compprod="+bean.getCodCompprod();
                
                System.out.println("DELETE COMPONENTES DETALLE: "+sql);
                Statement st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                result=result+st.executeUpdate(sql);
                //////////////////////////////////
                sql="delete from componentes_prod where cod_compprod="+bean.getCodCompprod();
                System.out.println("DELETE COMPONENTES: "+sql);
                st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                result=result+st.executeUpdate(sql);
                ////////////////////////////////
                sql="delete from acciones_terapeuticas_producto where cod_compprod="+bean.getCodCompprod();
                System.out.println("DELETE acciones_terapeuticas_producto: "+sql);
                st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                result=result+st.executeUpdate(sql);
                
            }
            getComponentesProductoEliminar().clear();
            getComponentesProductoNoEliminar().clear();
            if(result>0){
                cargarComponentesProducto();
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "navegadorComponentesProd";
    }
    public String Cancelar(){
        componentesProductoList.clear();
        //cargarComponentesProducto();
        return "navegadorComponentesProd";
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
    
    public void buscarAccionesTerapeuticas(ValueChangeEvent e){
        Object o=e.getNewValue();
        if(o!=null){
            String codigo=o.toString();
            String sql="select cod_accion_terapeutica,nombre_accion_terapeutica" +
                    " from acciones_terapeuticas ";
            sql+=" where  nombre_accion_terapeutica like '%"+codigo+"%'";
            
            System.out.println("cargar:"+sql);
            try {
                setCon(Util.openConnection(getCon()));
                Statement st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                ResultSet rs=st.executeQuery(sql);
                resultado.setWrappedData(ResultSupport.toResult(rs));
                System.out.println("resultado:"+resultado);
            } catch (SQLException s) {
                s.printStackTrace();
            }
        }
    }
    public void cogerCodigo(ActionEvent g){
        Map map=(Map)resultado.getRowData();
        String cod_accion_terapeutica=map.get("cod_accion_terapeutica").toString();
        String nombre_accion_terapeutica=map.get("nombre_accion_terapeutica").toString();
        
        Iterator i=detalleList.iterator();
        System.out.println("cod_accion_terapeutica:"+cod_accion_terapeutica);
        System.out.println("nombre_accion_terapeutica:"+nombre_accion_terapeutica);
        int j=1;
        List array=new ArrayList();
        while (i.hasNext()){
            ComponentesProd bean=(ComponentesProd)i.next();
            if(j==getIndice()){
                bean.getAccionesTerapeuticas().setCodAccionTerapeutica(cod_accion_terapeutica);
                bean.getAccionesTerapeuticas().setNombreAccionTerapeutica(nombre_accion_terapeutica);
                bean.setCodTemp(j);
            }
            j++;
            array.add(bean);
        }
        detalleList.clear();
        detalleList=array;
        
    }
    
    
    private int indice=0;
    
    public void cogerId(ActionEvent event){
        HtmlAjaxCommandLink link=(HtmlAjaxCommandLink )event.getComponent();
        System.out.println("link.getData():"+link.getData());
        if(link.getData()!=null){
            setIndice(Integer.parseInt(link.getData().toString()));
            // clearWrapper();
        }
        
        System.out.println("indice:"+getIndice());
    }
    public String  mas(){
        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx:"+componentesProdbean.getCodcompuestoprod());
        System.out.println("entro mMAS");
        /*int size=detalleList.size();
        System.out.println("sizeeeeeeeeee:"+size);
        int codigo=1;
        if(size>0){
            size--;
            ComponentesProd obj=(ComponentesProd)detalleList.get(size);
            codigo=obj.getCodTemp();
            codigo++;
        }
        ComponentesProd bean=new ComponentesProd();
        bean.setCodTemp(codigo);
        detalleList.add(bean);*/
        
        int size=detalleList.size();
        size++;
        ComponentesProd bean=new ComponentesProd();
        bean.setCodTemp(size);
        detalleList.add(bean);
        return"";
    }
    public String menos(){
        //PrincipiosActivos bean =new PrincipiosActivos();
        int size=detalleList.size();
        size--;
        if(size>=0){
            detalleList.remove(size);
        }
        
        
        return"";
        
    }
    
    public void filtrarProductos(ValueChangeEvent event){
        
        Object obj=event.getNewValue();
        if(obj!=null){
            String codproducto=obj.toString();
            componentesProdbean.getProducto().setCodProducto(codproducto);
            
            //cargarComponentesProducto();
        }
    }
    
    public void filtrarEstadosCompProd(ValueChangeEvent event){
        
        Object obj=event.getNewValue();
        if(obj!=null){
            String cod_estado=obj.toString();
            componentesProdbean.getEstadoCompProd().setCodEstadoCompProd(cod_estado);
            //cargarComponentesProducto();
        }
    }
    
    
    public void obtenerRegistrado(ValueChangeEvent event){
        Object obj=event.getNewValue();
        if(obj!=null){
            String codproducto=obj.toString();
            if(!codproducto.equals("")){
                String sql="select count(*)  from COMPONENTES_PROD where cod_prod="+codproducto;
                System.out.println("obtenerRegistrado:sql:"+sql);
                try {
                    Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                    ResultSet rs=st.executeQuery(sql);
                    int count=0;
                    if(rs.next())
                        count=rs.getInt(1);
                    System.out.println("obtenerRegistrado:count:"+count);
                    rs.close();
                    st.close();
                    if(count>0) {
                        componentesProdbean.setColumnStyle("2");
                    }
                    
                    else{
                        componentesProdbean.setColumnStyle("1");
                    }
                    
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                
                
                
            }
            
        }
        
    }
    ///////////////////////////////////////////////////////clases
    
    public ComponentesProd getComponentesProdbean() {
        return componentesProdbean;
    }
    
    public void setComponentesProdbean(ComponentesProd componentesProdbean) {
        this.componentesProdbean = componentesProdbean;
    }
    
    public List getComponentesProductoList() {
        return componentesProductoList;
    }
    
    public void setComponentesProductoList(List componentesProductoList) {
        this.componentesProductoList = componentesProductoList;
    }
    
    public List getComponentesProductoEliminar() {
        return componentesProductoEliminar;
    }
    
    public void setComponentesProductoEliminar(List componentesProductoEliminar) {
        this.componentesProductoEliminar = componentesProductoEliminar;
    }
    
    public List getComponentesProductoNoEliminar() {
        return componentesProductoNoEliminar;
    }
    
    public void setComponentesProductoNoEliminar(List componentesProductoNoEliminar) {
        this.componentesProductoNoEliminar = componentesProductoNoEliminar;
    }
    
    public Connection getCon() {
        return con;
    }
    
    public void setCon(Connection con) {
        this.con = con;
    }
    
    public List getProductosList() {
        return productosList;
    }
    
    public void setProductosList(List productosList) {
        this.productosList = productosList;
    }
    
    public List getProductosFormasFarList() {
        return productosFormasFarList;
    }
    
    public void setProductosFormasFarList(List productosFormasFarList) {
        this.productosFormasFarList = productosFormasFarList;
    }
    
    public List getSaboresProductoList() {
        return saboresProductoList;
    }
    
    public void setSaboresProductoList(List saboresProductoList) {
        this.saboresProductoList = saboresProductoList;
    }
    
    public List getEnvasesPrimariosList() {
        return envasesPrimariosList;
    }
    
    public void setEnvasesPrimariosList(List envasesPrimariosList) {
        this.envasesPrimariosList = envasesPrimariosList;
    }
    
    public List getColoresProductoList() {
        return coloresProductoList;
    }
    
    public void setColoresProductoList(List coloresProductoList) {
        this.coloresProductoList = coloresProductoList;
    }
    
    public List getAreasEmpresaList() {
        return areasEmpresaList;
    }
    
    public void setAreasEmpresaList(List areasEmpresaList) {
        this.areasEmpresaList = areasEmpresaList;
    }
    
    public List getEstadoRegistro() {
        return estadoRegistro;
    }
    
    public void setEstadoRegistro(List estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }
    
    public String getCodigo() {
        return codigo;
    }
    
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    public String getNombreProducto() {
        return nombreProducto;
    }
    
    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
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
    
    public List getDetalleList() {
        return detalleList;
    }
    
    public void setDetalleList(List detalleList) {
        this.detalleList = detalleList;
    }
    
    public ResultDataModel getResultado() {
        return resultado;
    }
    
    public void setResultado(ResultDataModel resultado) {
        this.resultado = resultado;
    }
    
    public int getIndice() {
        return indice;
    }
    
    public void setIndice(int indice) {
        this.indice = indice;
    }
    
    public List getProductosproceso() {
        return productosproceso;
    }
    
    public void setProductosproceso(List productosproceso) {
        this.productosproceso = productosproceso;
    }
    
    public List getPresentacionesPrimariasList() {
        return presentacionesPrimariasList;
    }
    
    public void setPresentacionesPrimariasList(List presentacionesPrimariasList) {
        this.presentacionesPrimariasList = presentacionesPrimariasList;
    }

    public String getAccionTerapeutica() {
        return accionTerapeutica;
    }

    public void setAccionTerapeutica(String accionTerapeutica) {
        this.accionTerapeutica = accionTerapeutica;
    }
    

    public String getObtenerCodigoPresentacionesPrimarias(){
        String cod=Util.getParameter("codigo");
        System.out.println("presentaciones primariasssssssssssssssssssssssssss:"+cod);
        if(cod!=null){
            setCodigoPP(cod);
        }
        cargarPresenacionesPrimarias();
        cargarNombreComProd();
        return "";
    }
    
    public String getObtenerCodigoAccionesTerapeuticas(){
        String cod=Util.getParameter("codigo");
        System.out.println("acciones:"+cod);
        if(cod!=null){
            setCodigoPP(cod);
        }
        cargarAccionesTerapeuticas();
        cargarNombreComProd();
        return "";
    }
    public void cargarAccionesTerapeuticas(){
        try {
            accionesTerapeuticasList.clear();
            con=Util.openConnection(con);
            
            String sql=" select a.cod_accion_terapeutica,a.nombre_accion_terapeutica";
            sql+=" from acciones_terapeuticas a,acciones_terapeuticas_producto d";
            sql+=" where d.cod_accion_terapeutica = a.cod_accion_terapeutica and d.COD_COMPPROD = "+getCodigoPP();
            sql+=" order by nombre_accion_terapeutica asc";
            System.out.println("cargarSQLLLLLLLLLLLLLL:"+sql);
            Statement st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs=st.executeQuery(sql);
            while(rs.next()){
                AccionesTerapeuticas bean=new AccionesTerapeuticas();
                bean.setCodAccionTerapeutica(rs.getString(1));
                bean.setNombreAccionTerapeutica(rs.getString(2));
                accionesTerapeuticasList.add(bean);
            }
            if(rs!=null){
                rs.close();
                st.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public String cargarNombreComProd(){
        try {
            setCon(Util.openConnection(getCon()));
            String sql=" select cp.nombre_prod_semiterminado";
            sql+=" from COMPONENTES_PROD cp";
            sql+=" where cp.COD_COMPPROD='"+getCodigoPP()+"'";
            System.out.println("sql:-----------:"+sql);
            PreparedStatement st=getCon().prepareStatement(sql);
            ResultSet rs=st.executeQuery();
            while (rs.next()){
                setNombreComProd(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("nombreComProd:"+getNombreComProd());
        return  "";
    }
    public void cargarPresenacionesPrimarias(){
        try {
            presentacionesPrimariasList.clear();
            con=Util.openConnection(con);
            String sql="select p.COD_COMPPROD,(select cp.nombre_prod_semiterminado from COMPONENTES_PROD cp where cp.COD_COMPPROD = p.COD_COMPPROD) nombreProdSemiterminado";
            sql+=",p.COD_ENVASEPRIM,(select ep.nombre_envaseprim from ENVASES_PRIMARIOS ep where ep.cod_envaseprim = p.COD_ENVASEPRIM) as nombreEvasePrimaria";
            sql+=",p.CANTIDAD from PRESENTACIONES_PRIMARIAS p where p.COD_COMPPROD = "+getCodigoPP();
            sql+=" order by nombreProdSemiterminado asc";
            System.out.println("cargarSQLLLLLLLLLLLLLL:"+sql);
            Statement st=getCon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs=st.executeQuery(sql);
            while(rs.next()){
                PresentacionesPrimarias bean=new PresentacionesPrimarias();
                bean.getComponentesProd().setCodCompprod(rs.getString(1));
                bean.getComponentesProd().setNombreProdSemiterminado(rs.getString(2));
                bean.getEnvasesPrimarios().setCodEnvasePrim(rs.getString(3));
                bean.getEnvasesPrimarios().setNombreEnvasePrim(rs.getString(4));
                bean.setCantidad(rs.getInt(5));
                presentacionesPrimariasList.add(bean);
            }
            if(rs!=null){
                rs.close();
                st.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public String registrarPresentacionesPrimarias(){
        cargarEnvasePrimario("",null);
        return "registrarPresentacionesPrimarias";
    }
    public String cancelarPresentacionesPrimarias(){
        return "cancelarPresentacionesPrimarias";
    }
    public String guardarPresentacionesPrimarias(){
        System.out.println("1233333333333333333");
        try {
            String sql="insert into presentaciones_primarias(COD_PRESENTACION_PRIMARIA,COD_COMPPROD,COD_ENVASEPRIM,CANTIDAD) values(";
            sql+=" "+generarCodigo()+"," ;
            sql+=" '"+getCodigoPP()+"'," ;
            sql+=" "+getPresentacionesPrimarias().getEnvasesPrimarios().getCodEnvasePrim()+"," ;
            sql+=" "+getPresentacionesPrimarias().getCantidad()+")" ;
            System.out.println("insert Presentaciones primarias:"+sql);
            con=Util.openConnection(con);
            PreparedStatement st=con.prepareStatement(sql);
            int result=st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        componentesProdbean=new ComponentesProd();
        return "navegadorPresentacionesPrimarias";
    }
    public String cancelarPresentacionesPrimarias1(){
        return "navegadorPresentacionesPrimarias";
    }
    public String getCodigoPP() {
        return codigoPP;
    }
    
    public void setCodigoPP(String codigoPP) {
        this.codigoPP = codigoPP;
    }
    /**
     * -------------------------------------------------------------------------
     * GENERAR CODIGO
     * -------------------------------------------------------------------------
     **/
    public String generarCodigo() {
        String codigo = "";
        try {
            con = Util.openConnection(con);
            String sql = "select max(COD_PRESENTACION_PRIMARIA)+1 from presentaciones_primarias";
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                codigo = rs.getString(1);
            }
            if (codigo == null) {
                codigo="1";
            }
            if (rs != null) {
                rs.close();
                st.close();
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return codigo;
    }
    
    public PresentacionesPrimarias getPresentacionesPrimarias() {
        return presentacionesPrimarias;
    }
    public String actionEliminarPresentacionesPrimarias(){
        Iterator i=presentacionesPrimariasList.iterator();
        try {
            while (i.hasNext()){
                PresentacionesPrimarias bean=(PresentacionesPrimarias)i.next();
                if(bean.getChecked().booleanValue()){
                    String sql="delete from PRESENTACIONES_PRIMARIAS where COD_ENVASEPRIM='"+bean.getEnvasesPrimarios().getCodEnvasePrim()+"' and COD_COMPPROD="+bean.getComponentesProd().getCodCompprod();
                    System.out.println("delete :"+sql);
                    con=Util.openConnection(con);
                    Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                    ResultSet rs=st.executeQuery(sql);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        cargarPresenacionesPrimarias();
        return "navegadorPresentacionesPrimarias";
    }
    
    public void setPresentacionesPrimarias(PresentacionesPrimarias presentacionesPrimarias) {
        this.presentacionesPrimarias = presentacionesPrimarias;
    }
    
    public String getNombreComProd() {
        return nombreComProd;
    }
    
    public void setNombreComProd(String nombreComProd) {
        this.nombreComProd = nombreComProd;
    }
    
    public List getAccionesTerapeuticasList() {
        return accionesTerapeuticasList;
    }
    
    public void setAccionesTerapeuticasList(List accionesTerapeuticasList) {
        this.accionesTerapeuticasList = accionesTerapeuticasList;
    }
    
    public List getEstadosCompProdList() {
        return estadosCompProdList;
    }
    
    public void setEstadosCompProdList(List estadosCompProdList) {
        this.estadosCompProdList = estadosCompProdList;
    }
    
}
