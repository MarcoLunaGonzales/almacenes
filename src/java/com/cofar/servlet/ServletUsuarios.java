/*
 * ServletArbol.java
 *
 * Created on 20 de mayo de 2008, 16:53
 */

package com.cofar.servlet;
import com.cofar.web.ManagedAccesoSistema;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author levi
 * @version
 */
public class ServletUsuarios extends HttpServlet {
    
    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
  /*  private Connection con;
    public void init(ServletConfig config) throws ServletException{
        super.init(config);
        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxx");
        String driver=config.getServletContext().getInitParameter("driver");
        //String url=config.getServletContext().getInitParameter("url");
        String user=config.getServletContext().getInitParameter("user");
        String password=config.getServletContext().getInitParameter("password");
        String database=config.getServletContext().getInitParameter("database");
        String host=config.getServletContext().getInitParameter("host");
        try {
            String url="jdbc:sqlserver://"+host+";user="+user+";password="+password+";databaseName="+database;
   
   
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            con=DriverManager.getConnection("jdbc:odbc:rrhh","sa","n3td4t4");
   
   
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch(SQLException e1){
            e1.printStackTrace();
        }
    }
    /*public Connection connect(Connection con){
        if(con==null){
   
        }
   
    }*/
    
    private Connection con;
    private ServletConfig servletConfig;
    private void cerrarConexion(){
       try
        {
            if(con != null && !con.isClosed())
            {
                con.close();
            }
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
    }
    private void abrirConexion()throws SQLException{
        String driver = servletConfig.getServletContext().getInitParameter("driver");
        String uri = servletConfig.getServletContext().getInitParameter("uri");
        String user = servletConfig.getServletContext().getInitParameter("user");
        String password = servletConfig.getServletContext().getInitParameter("password");
        String database = servletConfig.getServletContext().getInitParameter("database");
        String host = servletConfig.getServletContext().getInitParameter("host");
        try {
            String url="";
            Class.forName(driver);
            if(!uri.equalsIgnoreCase("none")){
                url="jdbc:odbc:"+uri;
                System.out.println("url:"+url);
                con=DriverManager.getConnection(url,user,password);
            }else{
                url="jdbc:sqlserver://"+host+";user="+user+";password="+password+";databaseName="+database;
                System.out.println("url:"+url);
                con=DriverManager.getConnection(url);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch(SQLException e1){
            e1.printStackTrace();
        }
    }
    public void init(ServletConfig config)throws ServletException{
        super.init(config);
        this.servletConfig = config;
    }
    /**
     * Este metodo nos genera un organigrama
     */
    public void generar(String codigoPersonal,PrintWriter writer,ManagedAccesoSistema managed)
    {
        try
        {
            this.abrirConexion();
            StringBuilder consulta=new StringBuilder(" select va.COD_VENTANA,va.NOMBRE_VENTANA,va.URL_VENTANA,va.COD_VENTANAPADRE,");
                            consulta.append(" (select COUNT(*) from VENTANAS_ALMACEN va1 where va1.COD_VENTANAPADRE=va.COD_VENTANA) as cantidadPadre");
                            consulta.append(" from USUARIOS_ACCESOS_MODULOS_BACO u inner join VENTANAS_ALMACEN va on va.COD_VENTANA=u.CODIGO_VENTANA");
                            consulta.append( " where u.COD_MODULO=2 and va.COD_ESTADO_VENTANA = 1 and u.COD_PERSONAL='").append(codigoPersonal).append("'");
                            if(managed.getUsuarioModuloBean().getCodUsuarioGlobal().equals("1700")&&managed.getAlmacenesGlobal().getCodAlmacen()==14)
                                    consulta.append(" and va.COD_VENTANA not in (10,5,4,13,12,50,87,97,3,6,8,88,36)");
                            consulta.append(" group by va.COD_VENTANA,va.NOMBRE_VENTANA,va.URL_VENTANA,va.COD_VENTANAPADRE,va.ORDEN ");
                            consulta.append(" ORDER BY case when va.COD_VENTANAPADRE=1 and va.ORDEN > 0 then va.ORDEN else 10000");
                            
                            consulta.append(" end,va.NOMBRE_VENTANA");
            System.out.println("consulta ventanas "+consulta.toString());
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet res=st.executeQuery(consulta.toString());
            while(res.next())
            {
                writer.write("<treeNode hasChildNodes=\""+(res.getInt("cantidadPadre")>0?"true":"false")+"\"  nodeLabel=\""+res.getString("NOMBRE_VENTANA")+
                        "\" nodeLink=\""+res.getString("URL_VENTANA")+"\" nodeName=\""+res.getInt("COD_VENTANA")+"\" nodeParent=\""+res.getInt("COD_VENTANAPADRE")+"\" nodeIcon=\"../img/"+(res.getInt("cantidadPadre")>0?"folder.gif":"b.bmp")+"\"  />\n");
            }
            st.close();
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        finally{
            this.cerrarConexion();
        }
    }
    public void organigramaempresaDos(HttpServletRequest request, HttpServletResponse response) throws IOException{
/*        String codigo=request.getParameter("codigo");
        String nombreAreaEmpresaPrincipal="gabriela";
        codigo=(codigo==null)?"":codigo;*/
        String codTipoAlmacenVenta="", codigoPersonal="";
        Object obj=request.getSession().getAttribute("ManagedAccesoSistema");
        ManagedAccesoSistema var=(ManagedAccesoSistema)obj;
        if(obj!=null){
            
            //System.out.println("Tipo"+var.getCodigoTipoAlmacenVentaGlobal());;
            //System.out.println("Usuario"+ var.getUsuarioModuloBean().getCodUsuarioGlobal());
            //codTipoAlmacenVenta=var.getCodigoTipoAlmacenVentaGlobal();
            codigoPersonal=var.getUsuarioModuloBean().getCodUsuarioGlobal();
            System.out.println("cooooooooooooodigo:"+codigoPersonal);
            //System.out.println("codTipoAlmacenVenta:"+codTipoAlmacenVenta);
        }
        
        try {
            String codigo="1";
            response.setContentType("text/xml");
            PrintWriter writer=response.getWriter();
            writer.write("<?xml version=\"1.0\" encoding=\"iso-8859-1\"?>\n");
            writer.write("<tree>\n");
            writer.write("<iconElement iconMas=\"../img/1.gif\" iconMenos=\"../img/2.gif\" iconFin=\"../img/3.gif\" />\n");
            writer.write("<treeNode hasChildNodes=\"true\"  nodeLabel=\"MINKAPROD\" nodeLink=\"../filiales/navegador_filiales.jsf\" nodeName=\""+codigo+"\" nodeParent=\"root\" nodeIcon=\"../img/folder.gif\"  />\n");
            generar(codigoPersonal,writer,var);
            writer.write("</tree>");
            writer.close();
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void  generaMenuXml(String codigo, String codigoPersonal,String codTipoAlmacenVenta,PrintWriter writer) throws IOException{
        // System.out.println("codigo"+codigo);
        // System.out.println("cocodigoPersonal"+codigoPersonal);
        // System.out.println("codTipoAlmacenVenta"+codTipoAlmacenVenta);
        try {
            String sql1="select cod_ventana, nombre_ventana,url_ventana ";
            sql1+=" from ventanas_almacen";
            sql1+=" where cod_ventanapadre="+codigo+"" +
                    " order by orden asc";
            System.out.println("generaMenuXml:"+sql1);
            Statement st1=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs1=st1.executeQuery(sql1);
            rs1.last();
            int row=0;
            row=rs1.getRow();
            //System.out.println("row:"+row);
            rs1.first();
            for(int i=1;i<=row;i++){
                String codVentana=rs1.getString("cod_ventana");
                String nombreVentana=rs1.getString("nombre_ventana");
                String urlVentana=rs1.getString("url_ventana");
                String sql2=" select * ";
                sql2+=" from usuarios_accesos_modulos_baco ";
                sql2+=" where cod_modulo=2 ";
                sql2+=" and cod_personal="+codigoPersonal;
                sql2+=" and codigo_ventana="+codVentana+" and codigo_estado_ventana = 1";
                System.out.println("Usuarios Acceso:"+sql2);
                int filas=obtenerCantidad(codVentana);
                Statement st2=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                ResultSet rs2=st2.executeQuery(sql2);
                rs2.last();
                int row2=0;
                row2=rs2.getRow();
                //System.out.println("row:"+row2);
                rs2.first();
                if(rs2!=null){
                    rs2.close();
                    st2.close();
                }
                
                
                if(row2>0){
                    if(filas>0)
                        writer.write("<treeNode hasChildNodes=\"true\"  nodeLabel=\""+nombreVentana+"\" nodeLink=\""+urlVentana+"\" nodeName=\""+codVentana+"\" nodeParent=\""+codigo+"\" nodeIcon=\"../img/folder.gif\"  />\n");
                    else
                        writer.write("<treeNode hasChildNodes=\"false\"  nodeLabel=\""+nombreVentana+"\" nodeLink=\""+urlVentana+"\" nodeName=\""+codVentana+"\" nodeParent=\""+codigo+"\" nodeIcon=\"../img/b.bmp\"  />\n");
                }
                
                
                
                
                
                rs1.next();
                generaMenuXml(codVentana,codigoPersonal,codTipoAlmacenVenta,writer);
                
            }
            if(rs1!=null){
                rs1.close();
                st1.close();
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        
    }
    
    
    public int   obtenerCantidad(String codigo) throws IOException{
        int rows=0;
        try {
            String sql=" select  count(*)";
            sql+=" from ventanas_almacen ";
            sql+=" where cod_ventanapadre="+codigo;
            // System.out.println("sql1_areadependiente"+sql1);
            Statement st1=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs1=st1.executeQuery(sql);
            
            if(rs1.next()){
                rows=rs1.getInt(1);
            }
            
            if(rs1!=null){
                rs1.close();
                st1.close();
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rows;
        
        
    }
    
    public void  generaCadenaAreasEmpresa(String codigo,PrintWriter writer) throws IOException{
        
        try {
            String sql1=" select  adi.cod_area_inferior,ae.nombre_area_empresa ";
            sql1+=" from areas_dependientes_inmediatas adi, areas_empresa ae ";
            sql1+=" where adi.cod_area_empresa="+codigo;
            sql1+=" and  adi.cod_area_inferior=ae.cod_area_empresa";
            //System.out.println("sql1_areadependiente"+sql1);
            Statement st1=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs1=st1.executeQuery(sql1);
            writer.write("<ul>");
            
            while (rs1.next()){
                writer.write("<li>");
                String codigoarea=rs1.getString(1);
                String nombrearea=rs1.getString(2);
                /*writer.write("<span>"+codigoarea+"("+ nombrearea+")</span>");*/
                writer.write("<a href=\"detalle?codigo="+codigoarea+"\" >("+ nombrearea+")</a>");
                generaCadenaAreasEmpresa(codigoarea,writer);
                //System.out.println("cod_area_inferior INFERIOR"+rs1.getString("cod_area_inferior"));
                writer.write("</li>");
            }
            writer.write("</ul>");
            if(rs1!=null){
                rs1.close();
                st1.close();
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        
    }
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String organigramaempresa=request.getParameter("organigramaempresa");
        
        organigramaempresaDos(request,response);
        
    }
    
    /** Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        organigramaempresaDos(request,response);
    }
    
    /** Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Short description";
    }
    // </editor-fold>
    
}
