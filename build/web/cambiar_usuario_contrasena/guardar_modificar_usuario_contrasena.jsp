
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page language="java" %>
<%@ page import = "java.sql.*"%>
<%@ page import = "java.sql.DriverManager"%> 
<%@ page import = "java.sql.ResultSet"%> 
<%@ page import = "java.sql.Statement"%> 
<%@ page import="com.cofar.util.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.GregorianCalendar" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.text.SimpleDateFormat" %>



<%! Connection con=null;
String codPersonal="";
String [] arraydecodigos= new String[200];

%>
<%

%>
<%

System.out.println("entro");
String personal=request.getParameter("codigo_personal");
System.out.println("personal:"+personal);
String usuario=request.getParameter("usuario");
System.out.println("usuario:"+usuario);
String contrasena=request.getParameter("contrasena");
System.out.println("contrasena:"+contrasena);
con=Util.openConnection(con);
Statement stm= con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
Statement stm1 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

try{
     Date date=new Date();
    Calendar cal = new GregorianCalendar();
    cal.setTimeInMillis(date.getTime());
    cal.add(Calendar.MONTH,6);
    Date fechaVenc=new Date(cal.getTimeInMillis());
    SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
    String sql_1="update usuarios_modulos set" +
    " nombre_usuario='"+usuario+"'," +
    " contrasena_usuario='"+contrasena+"'," +
    " fecha_vencimiento='"+sdf.format(fechaVenc)+" 23:59:59'" +
    " where cod_personal='"+personal+"' and cod_modulo=2";
    System.out.println("Insertar usuario acceso m,odulos"+sql_1);
    int rs2=stm.executeUpdate(sql_1);
} catch(Exception e) {
    
}
String sql="update usuarios_modulos set";
sql+=" nombre_usuario='"+usuario+"'," +
        " contrasena_usuario='"+contrasena+"'" +
        " where cod_personal='"+personal+"' and cod_modulo=2";

System.out.println("update usuario"+sql);
int rs1=stm.executeUpdate(sql);
if(rs1>0){
    
}


%>
<script>
 
                   location='../cambiar_usuario_contrasena/modificar_usuario_contrasena.jsf';
</script>

