/*
 * ServletArbol.java
 *
 * Created on 20 de mayo de 2008, 16:53
 */

package com.cofar.servlet;

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
public class ServletArbol extends HttpServlet {
    
    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
   /* private Connection con;
    public void init(ServletConfig config) throws ServletException{
        super.init(config);
        String driver=config.getServletContext().getInitParameter("driver");
        String url=config.getServletContext().getInitParameter("url");
        String user=config.getServletContext().getInitParameter("user");
        String password=config.getServletContext().getInitParameter("password");
        try {
            Class.forName(driver);
            con=DriverManager.getConnection(url,user,password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch(SQLException e1){
            e1.printStackTrace();
    
        }
    
    }*/
    
    
    private Connection con;
    public void init(ServletConfig config) throws ServletException{
        super.init(config);
        String driver=config.getServletContext().getInitParameter("driver");
        String uri=config.getServletContext().getInitParameter("uri");
        String user=config.getServletContext().getInitParameter("user");
        String password=config.getServletContext().getInitParameter("password");
        String database=config.getServletContext().getInitParameter("database");
        String host=config.getServletContext().getInitParameter("host");
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
    
    
    
    
    public void organigramaempresaUno(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String codigo=request.getParameter("codigo");
        codigo=(codigo==null)?"":codigo;
        response.setContentType("text/html");
        PrintWriter writer=response.getWriter();
        writer.write("<html>");
        writer.write("<head>");
        writer.write("<style type=\"text/css\">");
        writer.write("ul{list-style-type:none;}");
        writer.write("span{font-family: tahoma;font-size:9px;}");
        writer.write("A:link {font-family:tahoma;font-size:9px;color:#000000;}");
        writer.write("A:active {font-family:tahoma;font-size:9px;color:#000000;} ");
        writer.write("A:hover {font-family:tahoma;font-weight:bold;color:#000000;} ");
        writer.write("</style>");
        writer.write("</head>");
        writer.write("<body>");
        writer.write("<div style=\"text-align:left\">");
        writer.write("<ul>");
        writer.write("<li>");
        writer.write("<span>Gerencial General</span>");
        generaCadenaAreasEmpresa(codigo,writer);
        writer.write("</li>");
        writer.write("</ul>");
        
        writer.write("</div>");
        writer.write("</body>");
        writer.write("</html>");
        
    }
    
    
    /**
     * Este metodo nos genera un organigrama
     */
    public void organigramaempresaDos(HttpServletRequest request, HttpServletResponse response) throws IOException{
        // String codigo=request.getParameter("codigo");
        String nombreAreaEmpresaPrincipal="";
        String codigo="";
        codigo=(codigo==null)?"":codigo;
        String sql2="";
        
        try {
            sql2="select cod_area_empresa from areas_empresa where area_raiz=1";
            Statement st2=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs2=st2.executeQuery(sql2);
            while (rs2.next()){
                codigo=(rs2.getString("cod_area_empresa"));
                System.out.println("Nuevo valor asignado a cod_area_empresa cabeza");
            }
            System.out.println("codigo="+codigo);
            
            String sql1=" select  nombre_area_empresa ";
            sql1+=" from  areas_empresa a ";
            sql1+=" where cod_area_empresa="+codigo;
            Statement st1=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs1=st1.executeQuery(sql1);
            while (rs1.next()){
                nombreAreaEmpresaPrincipal=(rs1.getString("nombre_area_empresa"));
            }
            
            //     nombreAreaEmpresaPrincipal
            ///
            response.setContentType("text/xml");
            PrintWriter writer=response.getWriter();
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            writer.write("<tree>");
            writer.write("<iconElement iconMas=\"../img/1.gif\" iconMenos=\"../img/2.gif\" iconFin=\"../img/3.gif\" />");
            writer.write("<treeNode hasChildNodes=\"true\"  nodeLabel=\""+nombreAreaEmpresaPrincipal+"\" nodeLink=\"../filiales/navegador_filiales.jsf\" nodeName=\""+codigo+"\" nodeParent=\"root\" nodeIcon=\"../img/organigrama3.jpg\"  />");
            generaCadenaAreasEmpresaXml(codigo,writer);
            writer.write("</tree>");
            ///
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        
        
    }
    
    public void  generaCadenaAreasEmpresaXml(String codigo,PrintWriter writer) throws IOException{
        
        try {
            String sql1=" select  adi.cod_area_inferior,ae.nombre_area_empresa ";
            sql1+=" from areas_dependientes_inmediatas adi, areas_empresa ae ";
            sql1+=" where adi.cod_area_empresa="+codigo;
            sql1+=" and  adi.cod_area_inferior=ae.cod_area_empresa";
            sql1+=" order by adi.nro_orden";
            System.out.println("sql1_areadependiente"+sql1);
            Statement st1=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs1=st1.executeQuery(sql1);
            
            rs1.last();
            int row=0;
            row=rs1.getRow();
            System.out.println("row:"+row);
            rs1.first();
            String has="";
            for(int i=1;i<=row;i++){
                String codigoarea=rs1.getString(1);
                String nombrearea=rs1.getString(2);
                
                int filas=obtenerCantidad(codigoarea);
                
                if(filas>0) {
                    writer.write("<treeNode hasChildNodes=\"true\"  nodeLabel=\""+nombrearea+"\" nodeLink=\"../filiales/navegador_filiales.jsf\" nodeName=\""+codigoarea+"\" nodeParent=\""+codigo+"\" nodeIcon=\"../img/organigrama4.jpg\"  />");
                }else{
                    writer.write("<treeNode hasChildNodes=\"false\"  nodeLabel=\""+nombrearea+"\" nodeLink=\"../filiales/navegador_filiales.jsf\" nodeName=\""+codigoarea+"\" nodeParent=\""+codigo+"\" nodeIcon=\"../img/organigrama4.jpg\"  />");
                }
                rs1.next();
                generaCadenaAreasEmpresaXml(codigoarea,writer);
                
            }
            /*while (rs1.next()){
                String codigoarea=rs1.getString(1);
                String nombrearea=rs1.getString(2);
                if(row>0) {
                    writer.write("<treeNode hasChildNodes=\"true\"  nodeLabel=\""+codigoarea+"\" nodeLink=\"../filiales/navegador_filiales.jsf\" nodeName=\""+codigoarea+"\" nodeParent=\""+codigo+"\" nodeIcon=\"../img/b.bmp\"  />");
                }else{
                    writer.write("<treeNode hasChildNodes=\"false\"  nodeLabel=\""+codigoarea+"\" nodeLink=\"../filiales/navegador_filiales.jsf\" nodeName=\""+codigoarea+"\" nodeParent=\""+codigo+"\" nodeIcon=\"../img/b.bmp\"  />");
                }
                generaCadenaAreasEmpresaXml(codigoarea,writer);
             
            }*/
            
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
            String sql=" select  count(adi.cod_area_inferior)";
            sql+=" from areas_dependientes_inmediatas adi, areas_empresa ae ";
            sql+=" where adi.cod_area_empresa="+codigo;
            sql+=" and  adi.cod_area_inferior=ae.cod_area_empresa";
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
            System.out.println("sql1_areadependiente"+sql1);
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
                System.out.println("cod_area_inferior INFERIOR"+rs1.getString("cod_area_inferior"));
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
        System.out.println("AQui Entro Gabriela");
        /*if(organigramaempresa.equals("organigrama1")){
            organigramaempresaUno(request,response);
        }else if(organigramaempresa.equals("organigrama2")){
            organigramaempresaDos(request,response);
        } else if(organigramaempresa.equals("organigrama3")){
            //organigramaempresaTres(request,response);
        }*/
        
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
